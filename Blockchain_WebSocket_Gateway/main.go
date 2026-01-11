package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
	"strings"

	"github.com/gorilla/mux"
	"github.com/sch0penheimer/eth-ws-server/blockchain"
	"github.com/sch0penheimer/eth-ws-server/websocket"
)

func main() {
	nodeCount := flag.Int("nodes", 0, "Total number of nodes")
	nodeAddresses := flag.String("addresses", "", "Comma-separated list of node IP addresses")
	nodePorts := flag.String("ports", "", "Comma-separated list of node ports")
	flag.Parse()

	if *nodeCount <= 0 {
		log.Fatal("Invalid or missing node count. Use the --nodes flag to specify the total number of nodes.")
	}
	if *nodeAddresses == "" || *nodePorts == "" {
		log.Fatal("Node addresses and ports must be provided. Use --addresses and --ports flags.")
	}

	addressList := strings.Split(*nodeAddresses, ",")
	portList := strings.Split(*nodePorts, ",")

	if len(addressList) != *nodeCount || len(portList) != *nodeCount {
		log.Fatalf("The number of addresses (%d) and ports (%d) must match the node count (%d).", len(addressList), len(portList), *nodeCount)
	}

	nodeURLs := make([]string, *nodeCount)
	for i := 0; i < *nodeCount; i++ {
		if i == 0 {
			nodeURLs[i] = fmt.Sprintf("ws://%s:%s", addressList[i], portList[i])
		} else {
			nodeURLs[i] = fmt.Sprintf("http://%s:%s", addressList[i], portList[i])
		}
	}

	log.Printf("Using the following node URLs: %v", nodeURLs)

	// Initialize the mining controller
	miningController, err := blockchain.NewMiningController(nodeURLs)
	if err != nil {
		log.Fatalf("Failed to initialize mining controller: %v", err)
	}

	// Initialize the blockchain client and WebSocket handler
	blockFetcher, err := blockchain.NewBlockFetcher(nodeURLs[0])
	if err != nil {
		log.Fatalf("Failed to initialize blockchain client: %v", err)
	}
	wsHandler := websocket.NewWSHandler(blockFetcher, miningController)

	// Set up the HTTP server
	r := mux.NewRouter()
	r.HandleFunc("/ws", wsHandler.HandleConnections)
	r.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte("OK"))
	})

	// Add CORS middleware
	r.Use(func(next http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.Header().Set("Access-Control-Allow-Origin", "*")
			w.Header().Set("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
			w.Header().Set("Access-Control-Allow-Headers", "Content-Type")
			next.ServeHTTP(w, r)
		})
	})

	log.Println("Server starting on :8080")
	log.Fatal(http.ListenAndServe(":8080", r))
}
