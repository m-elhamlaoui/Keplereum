package websocket

import (
    "context"
    "encoding/json"
    "log"
    "net/http"
    "strings"
    "sync"

    "github.com/ethereum/go-ethereum/core/types"
    "github.com/gorilla/websocket"
    "github.com/sch0penheimer/eth-ws-server/blockchain"
)

type WSHandler struct {
    blockFetcher     *blockchain.BlockFetcher
    miningController *blockchain.MiningController
    clients          map[*Client]bool
    subscriptions    map[*Client]bool // Tracks whether a client is subscribed to new blocks
    register         chan *Client
    unregister       chan *Client
    broadcast        chan []byte
    mu               sync.Mutex
}

type Client struct {
    conn *websocket.Conn
    send chan []byte
}

type WSMessage struct {
    Type    string          `json:"type"`
    Payload json.RawMessage `json:"payload"`
}

type LatestBlocksRequest struct {
    Count int `json:"count"`
}

type MiningRequest struct {
    Start bool `json:"start"`
}

func NewWSHandler(blockFetcher *blockchain.BlockFetcher, miningController *blockchain.MiningController) *WSHandler {
    h := &WSHandler{
        blockFetcher:     blockFetcher,
        miningController: miningController,
        clients:          make(map[*Client]bool),
        subscriptions:    make(map[*Client]bool),
        register:         make(chan *Client),
        unregister:       make(chan *Client),
        broadcast:        make(chan []byte),
    }
    go h.run()
    go h.watchNewBlocks()
    return h
}

func (h *WSHandler) HandleConnections(w http.ResponseWriter, r *http.Request) {
    upgrader := websocket.Upgrader{
        CheckOrigin: func(r *http.Request) bool {
            return true
        },
    }
    conn, err := upgrader.Upgrade(w, r, nil)
    if err != nil {
        log.Printf("Error upgrading connection: %v", err)
        return
    }
    client := &Client{
        conn: conn,
        send: make(chan []byte, 256),
    }
    h.register <- client

    go h.writePump(client)
    go h.readPump(client)
}

func (h *WSHandler) writePump(client *Client) {
    defer func() {
        h.unregister <- client
        client.conn.Close()
    }()
    for {
        select {
        case message, ok := <-client.send:
            if !ok {
                return
            }
            if err := client.conn.WriteMessage(websocket.TextMessage, message); err != nil {
                return
            }
        }
    }
}

func (h *WSHandler) readPump(client *Client) {
    defer func() {
        h.unregister <- client
        client.conn.Close()
    }()
    for {
        _, message, err := client.conn.ReadMessage()
        if err != nil {
            log.Printf("Error reading message: %v", err)
            break
        }
        var msg WSMessage
        if err := json.Unmarshal(message, &msg); err != nil {
            log.Printf("Invalid message format: %v", err)
            sendError(client.conn, "invalid message format")
            continue
        }
        switch strings.ToLower(msg.Type) {
        case "latestblocks":
            h.handleLatestBlocks(client.conn, msg)
        case "miningstatus":
            h.handleMiningStatus(client.conn)
        case "togglemining":
            h.handleToggleMining(client.conn, msg)
        case "subscribe":
            h.handleSubscription(client)
        default:
            sendError(client.conn, "unknown message type")
        }
    }
}

func (h *WSHandler) handleSubscription(client *Client) {
    h.mu.Lock()
    defer h.mu.Unlock()

    if h.subscriptions[client] {
        h.subscriptions[client] = false
        log.Printf("Client unsubscribed from new blocks: %v", client.conn.RemoteAddr())
    } else {
        h.subscriptions[client] = true
        log.Printf("Client subscribed to new blocks: %v", client.conn.RemoteAddr())
    }

    
    response := map[string]interface{}{
        "type":    "subscribe",
        "status":  h.subscriptions[client],
        "message": "Subscription status updated",
    }
    if err := client.conn.WriteJSON(response); err != nil {
        log.Printf("Error sending subscription confirmation: %v", err)
    }
}

func (h *WSHandler) handleLatestBlocks(conn *websocket.Conn, msg WSMessage) {
    var req LatestBlocksRequest
    if err := json.Unmarshal(msg.Payload, &req); err != nil {
        sendError(conn, "invalid request format")
        return
    }

    if req.Count <= 0 || req.Count > 20 {
        req.Count = 6
    }

    blocks, err := h.blockFetcher.GetLatestBlocks(context.Background(), req.Count)
    if err != nil {
        log.Printf("Error fetching latest blocks: %v", err)
        sendError(conn, "failed to fetch blocks")
        return
    }

    metrics, err := h.blockFetcher.GetNetworkMetrics(context.Background())
    if err != nil {
        log.Printf("Error fetching network metrics: %v", err)
        sendError(conn, "failed to fetch network metrics")
        return
    }

    response := map[string]interface{}{
        "type":       "latestBlocks",
        "data":       blocks,
        "metrics":    metrics,
    }

    if err := conn.WriteJSON(response); err != nil {
        log.Printf("Error sending blocks and metrics: %v", err)
    }
}

func (h *WSHandler) handleMiningStatus(conn *websocket.Conn) {
    statuses, err := h.miningController.GetMiningStatus(context.Background())
    if err != nil {
        log.Printf("Error fetching mining status: %v", err)
        sendError(conn, "failed to fetch mining status")
        return
    }

    response := map[string]interface{}{
        "type": "miningStatus",
        "data": statuses,
    }
    if err := conn.WriteJSON(response); err != nil {
        log.Printf("Error sending mining status response: %v", err)
    }
}

func (h *WSHandler) handleToggleMining(conn *websocket.Conn, msg WSMessage) {
    var req MiningRequest
    if err := json.Unmarshal(msg.Payload, &req); err != nil {
        sendError(conn, "invalid request format")
        return
    }

    results, err := h.miningController.ToggleMining(context.Background(), req.Start)
    if err != nil {
        log.Printf("Error toggling mining: %v", err)
        sendError(conn, "failed to toggle mining")
        return
    }

    response := map[string]interface{}{
        "type": "toggleMining",
        "data": results,
    }
    if err := conn.WriteJSON(response); err != nil {
        log.Printf("Error sending toggle mining response: %v", err)
    }
}

func (h *WSHandler) watchNewBlocks() {
    for {
        headers := make(chan *types.Header)
        sub, err := h.blockFetcher.Client.SubscribeNewHead(context.Background(), headers)
        if err != nil {
            log.Printf("Failed to subscribe to new blocks: %v", err)
            continue
        }
        defer sub.Unsubscribe()

        log.Println("Subscribed to new block headers")

        for {
            select {
            case header := <-headers:
                log.Printf("New block header received: %v", header.Number)

                block, err := h.blockFetcher.GetBlockByNumber(context.Background(), header.Number)
                if err != nil {
                    log.Printf("Error fetching block details: %v", err)
                    continue
                }

                metrics, err := h.blockFetcher.GetNetworkMetrics(context.Background())
                if err != nil {
                    log.Printf("Error fetching network metrics: %v", err)
                    continue
                }

                response := map[string]interface{}{
                    "type":    "newBlock",
                    "data":    block,
                    "metrics": metrics,
                }

                msg, err := json.Marshal(response)
                if err != nil {
                    log.Printf("Error marshaling new block: %v", err)
                    continue
                }

                h.mu.Lock()
                for client, subscribed := range h.subscriptions {
                    if subscribed {
                        select {
                        case client.send <- msg:
                            log.Printf("New block sent to client: %v", client.conn.RemoteAddr())
                        default:
                            log.Printf("Client not ready to receive new blocks: %v", client.conn.RemoteAddr())
                        }
                    }
                }
                h.mu.Unlock()
            case err := <-sub.Err():
                log.Printf("Block subscription error: %v", err)
                break
            }
        }
    }
}

func (h *WSHandler) run() {
    for {
        select {
        case client := <-h.register:
            h.mu.Lock()
            h.clients[client] = true
            h.subscriptions[client] = false
            h.mu.Unlock()
            log.Printf("Client registered: %v", client.conn.RemoteAddr())
        case client := <-h.unregister:
            h.mu.Lock()
            if _, ok := h.clients[client]; ok {
                delete(h.clients, client)
                delete(h.subscriptions, client)
                close(client.send)
                log.Printf("Client unregistered: %v", client.conn.RemoteAddr())
            }
            h.mu.Unlock()
        case message := <-h.broadcast:
            h.mu.Lock()
            for client := range h.clients {
                select {
                case client.send <- message:
                default:
                    close(client.send)
                    delete(h.clients, client)
                    log.Printf("Client removed due to unresponsiveness: %v", client.conn.RemoteAddr())
                }
            }
            h.mu.Unlock()
        }
    }
}

func sendError(conn *websocket.Conn, message string) {
    errMsg := map[string]interface{}{
        "type": "error",
        "data": map[string]string{
            "message": message,
        },
    }
    if err := conn.WriteJSON(errMsg); err != nil {
        log.Printf("Error sending error message: %v", err)
    }
}