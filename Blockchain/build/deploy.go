package main

import (
	"context"
	"crypto/ecdsa"
	"fmt"
	"log"
	"math/big"
	"os"

	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/keystore"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/crypto"
	"github.com/ethereum/go-ethereum/ethclient"

	satellite "github.com/mdn753/development-platform-api-tologists/Blockchain/build/satelliteSystem" // Adjust to match your import path
)

func main() {
	// Connect to local Ethereum node
	client, err := ethclient.Dial("http://localhost:8545")
	if err != nil {
		log.Fatal("Failed to connect to Ethereum node:", err)
	}

	// Load keystore file and decrypt the private key
	keyJSON, err := os.ReadFile("C:\\Users\\Legion\\development-platform-api-tologists\\Blockchain\\geth-nodes\\node1\\keystore\\UTC--2025-03-20T00-27-07.449348769Z--e6427a00f96f7ca5688f1de97ba66c21daf77afe")
	if err != nil {
		log.Fatal("Failed to read keystore file:", err)
	}

	password := "123456789"
	key, err := keystore.DecryptKey(keyJSON, password)
	if err != nil {
		log.Fatal("Failed to decrypt keystore:", err)
	}
	privateKey := key.PrivateKey

	// Derive public key and address
	publicKey := privateKey.Public()
	publicKeyECDSA, ok := publicKey.(*ecdsa.PublicKey)
	if !ok {
		log.Fatal("Invalid public key type")
	}
	fromAddress := crypto.PubkeyToAddress(*publicKeyECDSA)

	// Get nonce and gas price
	nonce, err := client.PendingNonceAt(context.Background(), fromAddress)
	if err != nil {
		log.Fatal("Failed to get nonce:", err)
	}
	gasPrice, err := client.SuggestGasPrice(context.Background())
	if err != nil {
		log.Fatal("Failed to get gas price:", err)
	}

	// Create authenticated transactor
	chainID := big.NewInt(12345) // replace with your actual chain ID
	auth, err := bind.NewKeyedTransactorWithChainID(privateKey, chainID)
	if err != nil {
		log.Fatal("Failed to create transactor:", err)
	}
	auth.Nonce = big.NewInt(int64(nonce))
	auth.Value = big.NewInt(0)      // No ETH sent
	auth.GasLimit = uint64(5000000) // Set gas limit
	auth.GasPrice = gasPrice

	// Define constructor arguments
	requiredConfirmations := big.NewInt(3)
	initialSatellites := []common.Address{
		common.HexToAddress("0xE6427A00f96f7CA5688f1DE97ba66c21dAF77AFe"),
		common.HexToAddress("0x8ae4132bC3a5349245E5cb5DF336F7bd1C312c44"),
		common.HexToAddress("0x586C6A428dFa031a56360E634cD8d87EA38441C1"),
		common.HexToAddress("0xE680C674B7EE03080C8c11099D499C68895088cB"),
		common.HexToAddress("0x90501ed9E1D4466512a09Fe41157A1fad6Ed76e7"),
	}
	initialValidators := []common.Address{
		common.HexToAddress("0xE6427A00f96f7CA5688f1DE97ba66c21dAF77AFe"),
		common.HexToAddress("0x8ae4132bC3a5349245E5cb5DF336F7bd1C312c44"),
		common.HexToAddress("0x586C6A428dFa031a56360E634cD8d87EA38441C1"),
		common.HexToAddress("0xE680C674B7EE03080C8c11099D499C68895088cB"),
		common.HexToAddress("0x90501ed9E1D4466512a09Fe41157A1fad6Ed76e7"),
	}

	// Deploy the contract with constructor parameters
	address, tx, instance, err := satellite.DeploySatellite(
		auth,
		client,
		requiredConfirmations,
		initialSatellites,
		initialValidators,
	)
	if err != nil {
		log.Fatalf("Failed to deploy contract: %v", err)
	}

	// Output deployment result
	fmt.Printf("✅ Contract deployed at: %s\n", address.Hex())
	fmt.Printf("📦 Transaction hash: %s\n", tx.Hash().Hex())

	_ = instance // You can use instance to interact with contract functions
}
