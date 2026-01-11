package main

import (
	"log"
	"sync"

	"github.com/ethereum/go-ethereum/rpc"
)

func startMining(nodeURL string, wg *sync.WaitGroup) {
	defer wg.Done()

	client, err := rpc.Dial(nodeURL)
	if err != nil {
		log.Printf("Failed to connect to node at %s: %v", nodeURL, err)
		return
	}
	defer client.Close()

	var result interface{}
	err = client.Call(&result, "miner_start") // <- no arguments here
	if err != nil {
		log.Printf("Failed to start mining on node %s: %v", nodeURL, err)
		return
	}

	log.Printf("Mining started on node %s", nodeURL)
}

func main() {
	// List of RPC endpoints for the 5 nodes
	nodeURLs := []string{
		"http://localhost:8545",
		"http://localhost:8546",
		"http://localhost:8547",
		"http://localhost:8548",
		"http://localhost:8549",
	}

	var wg sync.WaitGroup

	for _, url := range nodeURLs {
		wg.Add(1)
		go startMining(url, &wg)
	}

	wg.Wait()
	log.Println("Mining started on all nodes.")
}
