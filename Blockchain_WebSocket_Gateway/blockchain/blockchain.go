package blockchain

import (
    "context"
    "fmt"
    "math/big"
    "time"
    "sync"
    "log"
    "runtime"

    "github.com/ethereum/go-ethereum/common"
    "github.com/ethereum/go-ethereum/core/types"
    "github.com/ethereum/go-ethereum/ethclient"
    "github.com/ethereum/go-ethereum/rpc"
)

type BlockTransaction struct {
    Hash  string  `json:"hash"`
    From  string  `json:"from"`
    To    string  `json:"to"`
    Value float64 `json:"value"`
}

type Block struct {
    Number           uint64             `json:"number"`
    Hash             string             `json:"hash"`
    ParentHash       string             `json:"parentHash"`
    Sha3Uncles       string             `json:"sha3uncles"`
    TransactionRoot  string             `json:"transactionRoot"`
    Timestamp        time.Time          `json:"timestamp"`
    Validator        string             `json:"validator"`
    Size             uint64             `json:"size"`
    GasUsed          uint64             `json:"gasUsed"`
    GasLimit         uint64             `json:"gasLimit"`
    Transactions     []BlockTransaction `json:"transactions"`
    TransactionCount int                `json:"transactionCount"`
    TotalFees        float64            `json:"totalFees"`
}

type BlockFetcher struct {
    Client     *ethclient.Client
    RPCClient  *rpc.Client
}

type MiningController struct {
    clients []*rpc.Client
    mu      sync.Mutex
}

func NewBlockFetcher(nodeURL string) (*BlockFetcher, error) {
    client, err := ethclient.Dial(nodeURL)
    if err != nil {
        return nil, fmt.Errorf("failed to connect to Ethereum node: %v", err)
    }

    rpcClient, err := rpc.Dial(nodeURL)
    if err != nil {
        return nil, fmt.Errorf("failed to connect to Ethereum RPC: %v", err)
    }

    return &BlockFetcher{
        Client:    client,
        RPCClient: rpcClient,
    }, nil
}

func (bf *BlockFetcher) GetLatestBlocks(ctx context.Context, count int) ([]Block, error) {
    var blocks []Block

    header, err := bf.Client.HeaderByNumber(ctx, nil)
    if err != nil {
        return nil, fmt.Errorf("failed to get latest block header: %v", err)
    }

    currentBlockNum := header.Number.Uint64()

    for i := 0; i < count; i++ {
        blockNum := big.NewInt(0).SetUint64(currentBlockNum - uint64(i))
        block, err := bf.GetBlockByNumber(ctx, blockNum)
        if err != nil {
            return nil, fmt.Errorf("failed to get block %d: %v", blockNum, err)
        }
        blocks = append(blocks, *block)
    }

    return blocks, nil
}

func (bf *BlockFetcher) GetBlockByNumber(ctx context.Context, number *big.Int) (*Block, error) {
    block, err := bf.Client.BlockByNumber(ctx, number)
    if err != nil {
        return nil, fmt.Errorf("failed to get block %d: %v", number, err)
    }

    totalFees := calculateTotalFees(block)
    txs := convertTransactions(block.Transactions())

    /**
      *  Validator / Signer fetching using clique_getSigner
      */
    var validator string
    err = bf.RPCClient.CallContext(ctx, &validator, "clique_getSigner", block.Hash().Hex())
    if err != nil {
        log.Printf("Failed to fetch validator for block %d: %v", number, err)
        validator = "0x0000000000000000000000000000000000000000"
    }

    return &Block{
        Number:           block.NumberU64(),
        Hash:             block.Hash().Hex(),
        ParentHash:       block.ParentHash().Hex(),
        Sha3Uncles:       block.UncleHash().Hex(),
        TransactionRoot:  block.TxHash().Hex(),
        Timestamp:        time.Unix(int64(block.Time()), 0),
        Validator:        validator,
        Size:             block.Size(),
        GasUsed:          block.GasUsed(),
        GasLimit:         block.GasLimit(),
        Transactions:     txs,
        TransactionCount: len(block.Transactions()),
        TotalFees:        totalFees,
    }, nil
}

func (bf *BlockFetcher) GetValidators(ctx context.Context) ([]string, error) {
    var validators []string
    err := bf.RPCClient.CallContext(ctx, &validators, "clique_getSigners")
    if err != nil {
        return nil, fmt.Errorf("failed to fetch validators: %v", err)
    }
    return validators, nil
}

func calculateTotalFees(block *types.Block) float64 {
    total := big.NewInt(0)
    for _, tx := range block.Transactions() {
        fee := new(big.Int).Mul(tx.GasPrice(), new(big.Int).SetUint64(tx.Gas()))
        total.Add(total, fee)
    }
    weiPerEth := new(big.Float).SetInt(big.NewInt(1e18))
    feeEth := new(big.Float).Quo(new(big.Float).SetInt(total), weiPerEth)
    f, _ := feeEth.Float64()
    return f
}

func convertTransactions(txs types.Transactions) []BlockTransaction {
    result := make([]BlockTransaction, len(txs))
    for i, tx := range txs {
        from, err := types.Sender(types.LatestSignerForChainID(tx.ChainId()), tx)
        if err != nil {
            from = common.HexToAddress("0x0000000000000000000000000000000000000000")
        }

        var to string
        if tx.To() != nil {
            to = tx.To().Hex()
        } else {
            to = "0x0000000000000000000000000000000000000000"
        }

        weiPerEth := new(big.Float).SetInt(big.NewInt(1e18))
        valueEth := new(big.Float).Quo(new(big.Float).SetInt(tx.Value()), weiPerEth)
        value, _ := valueEth.Float64()

        result[i] = BlockTransaction{
            Hash:  tx.Hash().Hex(),
            From:  from.Hex(),
            To:    to,
            Value: value,
        }
    }
    return result
}

/****************************************** MiningController methods ******************************************/
/**************************************************************************************************************/

func NewMiningController(nodeURLs []string) (*MiningController, error) {
    mc := &MiningController{}
    for _, url := range nodeURLs {
        client, err := rpc.Dial(url)
        if err != nil {
            return nil, err
        }
        mc.clients = append(mc.clients, client)
    }
    return mc, nil
}

func (mc *MiningController) ToggleMining(ctx context.Context, start bool) ([]bool, error) {
    mc.mu.Lock()
    defer mc.mu.Unlock()

    var results []bool
    for _, client := range mc.clients {
        var result bool
        method := "miner_stop"
        if start {
            method = "miner_start"
        }
        err := client.CallContext(ctx, &result, method)
        if err != nil {
            return nil, err
        }
        results = append(results, result)
    }
    return results, nil
}

func (mc *MiningController) GetMiningStatus(ctx context.Context) ([]bool, error) {
    mc.mu.Lock()
    defer mc.mu.Unlock()

    var statuses []bool
    for _, client := range mc.clients {
        var isMining bool
        err := client.CallContext(ctx, &isMining, "eth_mining")
        if err != nil {
            return nil, err
        }
        statuses = append(statuses, isMining)
    }
    return statuses, nil
}

func (bf *BlockFetcher) GetNetworkMetrics(ctx context.Context) (map[string]interface{}, error) {
    metrics := make(map[string]interface{})


    blockTime, err := bf.calculateAverageBlockTime(ctx)
    if err != nil {
        return nil, fmt.Errorf("failed to calculate average block time: %v", err)
    }
    metrics["averageBlockTime"] = blockTime


    latestBlock, err := bf.Client.BlockByNumber(ctx, nil)
    if err != nil {
        return nil, fmt.Errorf("failed to fetch latest block: %v", err)
    }
    metrics["difficulty"] = latestBlock.Difficulty().String()


    var hashrate string
    err = bf.RPCClient.CallContext(ctx, &hashrate, "eth_hashrate")
    if err != nil {
        return nil, fmt.Errorf("failed to fetch network hashrate: %v", err)
    }
    metrics["hashrate"] = hashrate


    latency := bf.calculateNetworkLatency()
    metrics["latency"] = latency


    var memStats runtime.MemStats
    runtime.ReadMemStats(&memStats)
    metrics["memoryUsage"] = fmt.Sprintf("%.2f MB", float64(memStats.Alloc)/1024/1024)

    return metrics, nil
}


func (bf *BlockFetcher) calculateAverageBlockTime(ctx context.Context) (float64, error) {
    latestBlock, err := bf.Client.BlockByNumber(ctx, nil)
    if err != nil {
        return 0, err
    }

    previousBlockNumber := new(big.Int).Sub(latestBlock.Number(), big.NewInt(25))
    previousBlock, err := bf.Client.BlockByNumber(ctx, previousBlockNumber)
    if err != nil {
        return 0, err
    }

    timeDiff := latestBlock.Time() - previousBlock.Time()
    averageBlockTime := float64(timeDiff) / 100.0
    return averageBlockTime / 60.0, nil
}

func (bf *BlockFetcher) calculateNetworkLatency() float64 {
    //* (INCOMING: ping to the node) *//
    /** FOR NOW its is mocked by 50ms **/
    return 50.0
}