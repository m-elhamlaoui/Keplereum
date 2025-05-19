// First part: Load the contract ABI and bytecode
import fs from 'fs';
import web3 from 'web3';

const contractABI = JSON.parse(fs.readFileSync('Blockchain\\build\\SatelliteSystem.abi'));
const contractBytecode = fs.readFileSync('Blockchain\\build\\SatelliteSystem.bin').toString();

// Create contract instance
const contract = new web3.eth.Contract(contractABI);

// Set up account and gas price
const deployAccount = '0xe6427a00f96f7ca5688f1de97ba66c21daf77afe';
web3.eth.getGasPrice().then(price => {
    const gasPrice = price;
    console.log('Current gas price:', gasPrice);
    
    // Your existing code for deployment
    contract.deploy({ data: contractBytecode })
        .estimateGas({ from: deployAccount })
        .then(gas => {
            console.log('Estimated gas: ', gas);
            // Now deploy using the estimated gas
            contract.deploy({ data: contractBytecode })
                .send({ from: deployAccount, gas: gas, gasPrice: gasPrice })
                .on('receipt', console.log)
                .on('error', console.error);
        })
        .catch(console.error);
});