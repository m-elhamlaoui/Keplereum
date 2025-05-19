package main

import (
	"context"
	"fmt"
	"log"
	"math/big"
	"time"

	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/ethereum/go-ethereum/rpc"
)

type CliqueSnapshot struct {
	Recents map[uint64]common.Address `json:"recents"`
}

type CliqueAPI struct {
	client *rpc.Client
}

func (c *CliqueAPI) GetSnapshot(blockNumber *big.Int) (*CliqueSnapshot, error) {
	var snapshot CliqueSnapshot
	err := c.client.Call(&snapshot, "clique_getSnapshot", toBlockNumArg(blockNumber))
	if err != nil {
		return nil, err
	}
	return &snapshot, nil
}

func toBlockNumArg(number *big.Int) string {
	if number == nil {
		return "latest"
	}
	return fmt.Sprintf("0x%x", number)
}

func getBlockSigner(clique *CliqueAPI, blockNumber uint64) (common.Address, error) {
	blockParam := big.NewInt(int64(blockNumber))
	snapshot, err := clique.GetSnapshot(blockParam)
	if err != nil {
		return common.Address{}, fmt.Errorf("failed to get snapshot for block %d: %v", blockNumber, err)
	}

	if signer, ok := snapshot.Recents[blockNumber]; ok {
		log.Printf("Block %d was signed by %s", blockNumber, signer.Hex())
		return signer, nil
	}

	return common.Address{}, fmt.Errorf("no signer found for block %d", blockNumber)
}

func monitorNewBlocks(clique *CliqueAPI, eth *ethclient.Client) {
	log.Println("Starting block signer monitor...")

	// Check existing blocks first
	lastBlock, err := eth.BlockNumber(context.Background())
	if err != nil {
		log.Fatalf("Failed to get current block number: %v", err)
	}

	for i := uint64(0); i <= lastBlock; i++ {
		_, err := getBlockSigner(clique, i)
		if err != nil {
			log.Printf("Warning: %v", err)
		}
	}

	// Start polling for new blocks
	pollForNewBlocks(clique, eth, lastBlock)
}

func pollForNewBlocks(clique *CliqueAPI, eth *ethclient.Client, lastCheckedBlock uint64) {
	ticker := time.NewTicker(5 * time.Second)
	defer ticker.Stop()

	for range ticker.C {
		currentBlock, err := eth.BlockNumber(context.Background())
		if err != nil {
			log.Printf("Error getting current block number: %v", err)
			continue
		}

		// Check for new blocks
		for i := lastCheckedBlock + 1; i <= currentBlock; i++ {
			_, err := getBlockSigner(clique, i)
			if err != nil {
				log.Printf("Warning: %v", err)
			}
		}

		lastCheckedBlock = currentBlock
	}
}

func main() {
	// Connect to the Ethereum node
	rpcClient, err := rpc.Dial("http://localhost:8545")
	if err != nil {
		log.Fatalf("Failed to connect to the Ethereum client: %v", err)
	}
	defer rpcClient.Close()

	ethClient := ethclient.NewClient(rpcClient)

	// Initialize Clique API
	clique := &CliqueAPI{client: rpcClient}

	monitorNewBlocks(clique, ethClient)
}
