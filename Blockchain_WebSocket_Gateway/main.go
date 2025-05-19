package main

import (
	"flag"
    "fmt"
	"log"
	"net/http"
	"github.com/gorilla/mux"
	"github.com/sch0penheimer/eth-ws-server/blockchain"
	"github.com/sch0penheimer/eth-ws-server/websocket"
)

func main() {
	hostFlag := flag.String("host", "", "Host IP address of the Ethereum node cluster (e.g., 192.168.240.222)")
	flag.Parse()

    if *hostFlag == "" {
        log.Fatal("No host IP address provided. Use the --host flag to specify the host IP.")
    }

	/** Dynamically format the node URLs using the host IP :
	 *  In my private Ethereum PoA Blockchain my 
	 *	connecting node was setuped with WebSocket instead of HTTP, like the rest of the nodes. 
	 */
    nodeURLs := []string{
        fmt.Sprintf("ws://%s:8545", *hostFlag),
        fmt.Sprintf("http://%s:8546", *hostFlag),
        fmt.Sprintf("http://%s:8547", *hostFlag),
        fmt.Sprintf("http://%s:8548", *hostFlag),
        fmt.Sprintf("http://%s:8549", *hostFlag),
    }

    log.Printf("Using the following WebSocket node URLs: %v", nodeURLs)

	miningController, err := blockchain.NewMiningController(nodeURLs)
	if err != nil {
		log.Fatalf("Failed to initialize mining controller: %v", err)
	}

	//* Initializing the blockchain client && the WS Handler *//
	blockFetcher, err := blockchain.NewBlockFetcher(nodeURLs[0])
	if err != nil {
		log.Fatalf("Failed to initialize blockchain client: %v", err)
	}
	wsHandler := websocket.NewWSHandler(blockFetcher, miningController)

	
	r := mux.NewRouter()
	r.HandleFunc("/ws", wsHandler.HandleConnections)
	r.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte("OK"))
	})


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
