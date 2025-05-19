package com.example.jeeHamlaoui.Blockchain_Service.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class SatelliteSystem extends Contract {
    public static final String BINARY = "608060405234801561000f575f5ffd5b506040516122fc3803806122fc83398181016040528101906100319190610343565b826001819055505f5f90505b82518110156100c257600160045f85848151811061005e5761005d6103cb565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff021916908315150217905550808060010191505061003d565b505f5f90505b815181101561014c5760015f5f8484815181106100e8576100e76103cb565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555080806001019150506100c8565b505050506103f8565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b61017881610166565b8114610182575f5ffd5b50565b5f815190506101938161016f565b92915050565b5f5ffd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6101e38261019d565b810181811067ffffffffffffffff82111715610202576102016101ad565b5b80604052505050565b5f610214610155565b905061022082826101da565b919050565b5f67ffffffffffffffff82111561023f5761023e6101ad565b5b602082029050602081019050919050565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61027d82610254565b9050919050565b61028d81610273565b8114610297575f5ffd5b50565b5f815190506102a881610284565b92915050565b5f6102c06102bb84610225565b61020b565b905080838252602082019050602084028301858111156102e3576102e2610250565b5b835b8181101561030c57806102f8888261029a565b8452602084019350506020810190506102e5565b5050509392505050565b5f82601f83011261032a57610329610199565b5b815161033a8482602086016102ae565b91505092915050565b5f5f5f6060848603121561035a5761035961015e565b5b5f61036786828701610185565b935050602084015167ffffffffffffffff81111561038857610387610162565b5b61039486828701610316565b925050604084015167ffffffffffffffff8111156103b5576103b4610162565b5b6103c186828701610316565b9150509250925092565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b611ef7806104055f395ff3fe608060405234801561000f575f5ffd5b50600436106100e8575f3560e01c806382e717f71161008a578063bce9252d11610064578063bce9252d14610230578063ccf07b6f14610266578063e2bcb68714610296578063fa52c7d8146102c6576100e8565b806382e717f7146101da578063b9ee0ecd146101f8578063bc437ea514610214576100e8565b80634c8142a9116100c65780634c8142a9146101565780634d238c8e1461017257806357baf2d61461018e57806381b2db50146101be576100e8565b80632b1e6478146100ec57806340411fc81461011c57806340a141ff1461013a575b5f5ffd5b61010660048036038101906101019190611283565b6102f6565b6040516101139190611307565b60405180910390f35b6101246105a2565b604051610131919061132f565b60405180910390f35b610154600480360381019061014f91906113a2565b6105ae565b005b610170600480360381019061016b91906113a2565b61068c565b005b61018c600480360381019061018791906113a2565b61076c565b005b6101a860048036038101906101a391906113a2565b61084b565b6040516101b591906113e7565b60405180910390f35b6101d860048036038101906101d39190611400565b610868565b005b6101e26108fa565b6040516101ef919061132f565b60405180910390f35b610212600480360381019061020d9190611478565b610900565b005b61022e600480360381019061022991906114c8565b610ac7565b005b61024a600480360381019061024591906114c8565b610e97565b60405161025d9796959493929190611562565b60405180910390f35b610280600480360381019061027b9190611400565b610f86565b60405161028d9190611307565b60405180910390f35b6102b060048036038101906102ab91906114c8565b610fa6565b6040516102bd919061168d565b60405180910390f35b6102e060048036038101906102db91906113a2565b611045565b6040516102ed91906113e7565b60405180910390f35b5f60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610380576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610377906116f7565b60405180910390fd5b5f338585854260405160200161039a9594939291906117b4565b6040516020818303038152906040528051906020012090506040518061010001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018581526020018481526020014281526020015f81526020015f151581526020015f67ffffffffffffffff81111561041a5761041961112c565b5b6040519080825280602002602001820160405280156104485781602001602082028036833780820191505090505b5081525060025f8381526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010190816104b69190611a0b565b5060408201518160020155606082015181600301556080820151816004015560a0820151816005015560c0820151816006015f6101000a81548160ff02191690831515021790555060e082015181600701908051906020019061051a929190611061565b50905050600381908060018154018082558091505060019003905f5260205f20015f90919091909150553373ffffffffffffffffffffffffffffffffffffffff16817f0de20b51b5905e09a080a027d931ab48f5d06e20e1ef027c541bcf69b8cd6c7687878760405161058f93929190611ada565b60405180910390a3809150509392505050565b5f600380549050905090565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610636576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161062d90611b60565b60405180910390fd5b5f5f5f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610714576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161070b90611b60565b60405180910390fd5b600160045f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff166107f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107eb90611b60565b60405180910390fd5b60015f5f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b6004602052805f5260405f205f915054906101000a900460ff1681565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff166108f0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108e790611b60565b60405180910390fd5b8060018190555050565b60015481565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610988576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161097f90611b60565b60405180910390fd5b60025f8281526020019081526020015f206006015f9054906101000a900460ff166109e8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109df90611bc8565b60405180910390fd5b60045f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610a71576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a6890611c30565b60405180910390fd5b808373ffffffffffffffffffffffffffffffffffffffff167fcef4123d1571ac7af64f14d12d1a2ba0fd84c5faa30676e42541bb5980362e338442604051610aba929190611cc1565b60405180910390a3505050565b60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610b50576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b47906116f7565b60405180910390fd5b60025f8281526020019081526020015f206006015f9054906101000a900460ff1615610bb1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ba890611d32565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff1660025f8381526020019081526020015f205f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610c51576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c4890611d9a565b60405180910390fd5b5f5f90505b60025f8381526020019081526020015f2060070180549050811015610d3d573373ffffffffffffffffffffffffffffffffffffffff1660025f8481526020019081526020015f206007018281548110610cb257610cb1611db8565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610d30576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d2790611e2f565b60405180910390fd5b8080600101915050610c56565b5060025f8281526020019081526020015f206005015f815480929190610d6290611e7a565b919050555060025f8281526020019081526020015f2060070133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff16817ff262571dcf418715bcbb5ae5cd9be1ad0c4a636132a229ce6cffe53abf63290760405160405180910390a360015460025f8381526020019081526020015f206005015410610e9457600160025f8381526020019081526020015f206006015f6101000a81548160ff021916908315150217905550807fb451c13ad4e11b923d4d71188e1e1aaa55c88e5189e0d7c2535436a1fcd6a15f60405160405180910390a25b50565b6002602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001018054610edb9061183b565b80601f0160208091040260200160405190810160405280929190818152602001828054610f079061183b565b8015610f525780601f10610f2957610100808354040283529160200191610f52565b820191905f5260205f20905b815481529060010190602001808311610f3557829003601f168201915b505050505090806002015490806003015490806004015490806005015490806006015f9054906101000a900460ff16905087565b60038181548110610f95575f80fd5b905f5260205f20015f915090505481565b606060025f8381526020019081526020015f2060070180548060200260200160405190810160405280929190818152602001828054801561103957602002820191905f5260205f20905b815f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311610ff0575b50505050509050919050565b5f602052805f5260405f205f915054906101000a900460ff1681565b828054828255905f5260205f209081019282156110d7579160200282015b828111156110d6578251825f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509160200191906001019061107f565b5b5090506110e491906110e8565b5090565b5b808211156110ff575f815f9055506001016110e9565b5090565b5f604051905090565b5f5ffd5b5f5ffd5b5f5ffd5b5f5ffd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6111628261111c565b810181811067ffffffffffffffff821117156111815761118061112c565b5b80604052505050565b5f611193611103565b905061119f8282611159565b919050565b5f67ffffffffffffffff8211156111be576111bd61112c565b5b6111c78261111c565b9050602081019050919050565b828183375f83830152505050565b5f6111f46111ef846111a4565b61118a565b9050828152602081018484840111156112105761120f611118565b5b61121b8482856111d4565b509392505050565b5f82601f83011261123757611236611114565b5b81356112478482602086016111e2565b91505092915050565b5f819050919050565b61126281611250565b811461126c575f5ffd5b50565b5f8135905061127d81611259565b92915050565b5f5f5f6060848603121561129a5761129961110c565b5b5f84013567ffffffffffffffff8111156112b7576112b6611110565b5b6112c386828701611223565b93505060206112d48682870161126f565b92505060406112e58682870161126f565b9150509250925092565b5f819050919050565b611301816112ef565b82525050565b5f60208201905061131a5f8301846112f8565b92915050565b61132981611250565b82525050565b5f6020820190506113425f830184611320565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61137182611348565b9050919050565b61138181611367565b811461138b575f5ffd5b50565b5f8135905061139c81611378565b92915050565b5f602082840312156113b7576113b661110c565b5b5f6113c48482850161138e565b91505092915050565b5f8115159050919050565b6113e1816113cd565b82525050565b5f6020820190506113fa5f8301846113d8565b92915050565b5f602082840312156114155761141461110c565b5b5f6114228482850161126f565b91505092915050565b60028110611437575f5ffd5b50565b5f813590506114488161142b565b92915050565b611457816112ef565b8114611461575f5ffd5b50565b5f813590506114728161144e565b92915050565b5f5f5f6060848603121561148f5761148e61110c565b5b5f61149c8682870161138e565b93505060206114ad8682870161143a565b92505060406114be86828701611464565b9150509250925092565b5f602082840312156114dd576114dc61110c565b5b5f6114ea84828501611464565b91505092915050565b6114fc81611367565b82525050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f61153482611502565b61153e818561150c565b935061154e81856020860161151c565b6115578161111c565b840191505092915050565b5f60e0820190506115755f83018a6114f3565b8181036020830152611587818961152a565b90506115966040830188611320565b6115a36060830187611320565b6115b06080830186611320565b6115bd60a0830185611320565b6115ca60c08301846113d8565b98975050505050505050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b61160881611367565b82525050565b5f61161983836115ff565b60208301905092915050565b5f602082019050919050565b5f61163b826115d6565b61164581856115e0565b9350611650836115f0565b805f5b83811015611680578151611667888261160e565b975061167283611625565b925050600181019050611653565b5085935050505092915050565b5f6020820190508181035f8301526116a58184611631565b905092915050565b7f4f6e6c79207265676973746572656420736174656c6c697465730000000000005f82015250565b5f6116e1601a8361150c565b91506116ec826116ad565b602082019050919050565b5f6020820190508181035f83015261170e816116d5565b9050919050565b5f8160601b9050919050565b5f61172b82611715565b9050919050565b5f61173c82611721565b9050919050565b61175461174f82611367565b611732565b82525050565b5f81905092915050565b5f61176e82611502565b611778818561175a565b935061178881856020860161151c565b80840191505092915050565b5f819050919050565b6117ae6117a982611250565b611794565b82525050565b5f6117bf8288611743565b6014820191506117cf8287611764565b91506117db828661179d565b6020820191506117eb828561179d565b6020820191506117fb828461179d565b6020820191508190509695505050505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061185257607f821691505b6020821081036118655761186461180e565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026118c77fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261188c565b6118d1868361188c565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61190c61190761190284611250565b6118e9565b611250565b9050919050565b5f819050919050565b611925836118f2565b61193961193182611913565b848454611898565b825550505050565b5f5f905090565b611950611941565b61195b81848461191c565b505050565b5b8181101561197e576119735f82611948565b600181019050611961565b5050565b601f8211156119c3576119948161186b565b61199d8461187d565b810160208510156119ac578190505b6119c06119b88561187d565b830182611960565b50505b505050565b5f82821c905092915050565b5f6119e35f19846008026119c8565b1980831691505092915050565b5f6119fb83836119d4565b9150826002028217905092915050565b611a1482611502565b67ffffffffffffffff811115611a2d57611a2c61112c565b5b611a37825461183b565b611a42828285611982565b5f60209050601f831160018114611a73575f8415611a61578287015190505b611a6b85826119f0565b865550611ad2565b601f198416611a818661186b565b5f5b82811015611aa857848901518255600182019150602085019450602081019050611a83565b86831015611ac55784890151611ac1601f8916826119d4565b8355505b6001600288020188555050505b505050505050565b5f6060820190508181035f830152611af2818661152a565b9050611b016020830185611320565b611b0e6040830184611320565b949350505050565b7f4f6e6c792076616c696461746f72732063616e2063616c6c00000000000000005f82015250565b5f611b4a60188361150c565b9150611b5582611b16565b602082019050919050565b5f6020820190508181035f830152611b7781611b3e565b9050919050565b7f416c657274206e6f742076616c696461746564000000000000000000000000005f82015250565b5f611bb260138361150c565b9150611bbd82611b7e565b602082019050919050565b5f6020820190508181035f830152611bdf81611ba6565b9050919050565b7f496e76616c696420736174656c6c6974650000000000000000000000000000005f82015250565b5f611c1a60118361150c565b9150611c2582611be6565b602082019050919050565b5f6020820190508181035f830152611c4781611c0e565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602160045260245ffd5b60028110611c8c57611c8b611c4e565b5b50565b5f819050611c9c82611c7b565b919050565b5f611cab82611c8f565b9050919050565b611cbb81611ca1565b82525050565b5f604082019050611cd45f830185611cb2565b611ce16020830184611320565b9392505050565b7f416c65727420616c72656164792076616c6964617465640000000000000000005f82015250565b5f611d1c60178361150c565b9150611d2782611ce8565b602082019050919050565b5f6020820190508181035f830152611d4981611d10565b9050919050565b7f43616e6e6f7420636f6e6669726d206f776e20616c65727400000000000000005f82015250565b5f611d8460188361150c565b9150611d8f82611d50565b602082019050919050565b5f6020820190508181035f830152611db181611d78565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f416c726561647920636f6e6669726d65640000000000000000000000000000005f82015250565b5f611e1960118361150c565b9150611e2482611de5565b602082019050919050565b5f6020820190508181035f830152611e4681611e0d565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611e8482611250565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611eb657611eb5611e4d565b5b60018201905091905056fea2646970667358221220c45939901829daf1529ce05a735d5bd68e02cd696839d17f79daceb732609ea564736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDVALIDATOR = "addValidator";

    public static final String FUNC_ALERTIDS = "alertIds";

    public static final String FUNC_ALERTS = "alerts";

    public static final String FUNC_CONFIRMALERT = "confirmAlert";

    public static final String FUNC_GETALERTCONFIRMERS = "getAlertConfirmers";

    public static final String FUNC_GETALERTCOUNT = "getAlertCount";

    public static final String FUNC_REGISTERSATELLITE = "registerSatellite";

    public static final String FUNC_REGISTEREDSATELLITES = "registeredSatellites";

    public static final String FUNC_REMOVEVALIDATOR = "removeValidator";

    public static final String FUNC_REQUIREDCONFIRMATIONS = "requiredConfirmations";

    public static final String FUNC_SUBMITALERT = "submitAlert";

    public static final String FUNC_TRIGGERACTION = "triggerAction";

    public static final String FUNC_UPDATEREQUIREDCONFIRMATIONS = "updateRequiredConfirmations";

    public static final String FUNC_VALIDATORS = "validators";

    public static final Event ACTIONTRIGGERED_EVENT = new Event("ActionTriggered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ALERTCONFIRMED_EVENT = new Event("AlertConfirmed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ALERTSUBMITTED_EVENT = new Event("AlertSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ALERTVALIDATED_EVENT = new Event("AlertValidated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    @Deprecated
    protected SatelliteSystem(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SatelliteSystem(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SatelliteSystem(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SatelliteSystem(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ActionTriggeredEventResponse> getActionTriggeredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACTIONTRIGGERED_EVENT, transactionReceipt);
        ArrayList<ActionTriggeredEventResponse> responses = new ArrayList<ActionTriggeredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ActionTriggeredEventResponse typedResponse = new ActionTriggeredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.satellite = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.action = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ActionTriggeredEventResponse getActionTriggeredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACTIONTRIGGERED_EVENT, log);
        ActionTriggeredEventResponse typedResponse = new ActionTriggeredEventResponse();
        typedResponse.log = log;
        typedResponse.satellite = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.action = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ActionTriggeredEventResponse> actionTriggeredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getActionTriggeredEventFromLog(log));
    }

    public Flowable<ActionTriggeredEventResponse> actionTriggeredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACTIONTRIGGERED_EVENT));
        return actionTriggeredEventFlowable(filter);
    }

    public static List<AlertConfirmedEventResponse> getAlertConfirmedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ALERTCONFIRMED_EVENT, transactionReceipt);
        ArrayList<AlertConfirmedEventResponse> responses = new ArrayList<AlertConfirmedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AlertConfirmedEventResponse typedResponse = new AlertConfirmedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.confirmer = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AlertConfirmedEventResponse getAlertConfirmedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ALERTCONFIRMED_EVENT, log);
        AlertConfirmedEventResponse typedResponse = new AlertConfirmedEventResponse();
        typedResponse.log = log;
        typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.confirmer = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<AlertConfirmedEventResponse> alertConfirmedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAlertConfirmedEventFromLog(log));
    }

    public Flowable<AlertConfirmedEventResponse> alertConfirmedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ALERTCONFIRMED_EVENT));
        return alertConfirmedEventFlowable(filter);
    }

    public static List<AlertSubmittedEventResponse> getAlertSubmittedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ALERTSUBMITTED_EVENT, transactionReceipt);
        ArrayList<AlertSubmittedEventResponse> responses = new ArrayList<AlertSubmittedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AlertSubmittedEventResponse typedResponse = new AlertSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.alertType = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.latitude = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.longitude = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AlertSubmittedEventResponse getAlertSubmittedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ALERTSUBMITTED_EVENT, log);
        AlertSubmittedEventResponse typedResponse = new AlertSubmittedEventResponse();
        typedResponse.log = log;
        typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.alertType = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.latitude = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.longitude = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<AlertSubmittedEventResponse> alertSubmittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAlertSubmittedEventFromLog(log));
    }

    public Flowable<AlertSubmittedEventResponse> alertSubmittedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ALERTSUBMITTED_EVENT));
        return alertSubmittedEventFlowable(filter);
    }

    public static List<AlertValidatedEventResponse> getAlertValidatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ALERTVALIDATED_EVENT, transactionReceipt);
        ArrayList<AlertValidatedEventResponse> responses = new ArrayList<AlertValidatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AlertValidatedEventResponse typedResponse = new AlertValidatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AlertValidatedEventResponse getAlertValidatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ALERTVALIDATED_EVENT, log);
        AlertValidatedEventResponse typedResponse = new AlertValidatedEventResponse();
        typedResponse.log = log;
        typedResponse.alertId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AlertValidatedEventResponse> alertValidatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAlertValidatedEventFromLog(log));
    }

    public Flowable<AlertValidatedEventResponse> alertValidatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ALERTVALIDATED_EVENT));
        return alertValidatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addValidator(String _validator) {
        final Function function = new Function(
                FUNC_ADDVALIDATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _validator)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> alertIds(BigInteger param0) {
        final Function function = new Function(FUNC_ALERTIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> alerts(
            byte[] param0) {
        final Function function = new Function(FUNC_ALERTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> confirmAlert(byte[] _alertId) {
        final Function function = new Function(
                FUNC_CONFIRMALERT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_alertId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAlertConfirmers(byte[] _alertId) {
        final Function function = new Function(FUNC_GETALERTCONFIRMERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_alertId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getAlertCount() {
        final Function function = new Function(FUNC_GETALERTCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerSatellite(String _satellite) {
        final Function function = new Function(
                FUNC_REGISTERSATELLITE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _satellite)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> registeredSatellites(String param0) {
        final Function function = new Function(FUNC_REGISTEREDSATELLITES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeValidator(String _validator) {
        final Function function = new Function(
                FUNC_REMOVEVALIDATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _validator)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> requiredConfirmations() {
        final Function function = new Function(FUNC_REQUIREDCONFIRMATIONS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> submitAlert(String _alertType,
            BigInteger _latitude, BigInteger _longitude) {
        final Function function = new Function(
                FUNC_SUBMITALERT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_alertType), 
                new org.web3j.abi.datatypes.generated.Uint256(_latitude), 
                new org.web3j.abi.datatypes.generated.Uint256(_longitude)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> triggerAction(String _satellite,
            BigInteger _action, byte[] _alertId) {
        final Function function = new Function(
                FUNC_TRIGGERACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _satellite), 
                new org.web3j.abi.datatypes.generated.Uint8(_action), 
                new org.web3j.abi.datatypes.generated.Bytes32(_alertId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateRequiredConfirmations(
            BigInteger _newValue) {
        final Function function = new Function(
                FUNC_UPDATEREQUIREDCONFIRMATIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> validators(String param0) {
        final Function function = new Function(FUNC_VALIDATORS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static SatelliteSystem load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new SatelliteSystem(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SatelliteSystem load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SatelliteSystem(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SatelliteSystem load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new SatelliteSystem(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SatelliteSystem load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SatelliteSystem(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SatelliteSystem> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger _requiredConfirmations,
            List<String> _initialSatellites, List<String> _validators) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requiredConfirmations), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_initialSatellites, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_validators, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(SatelliteSystem.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<SatelliteSystem> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            BigInteger _requiredConfirmations, List<String> _initialSatellites,
            List<String> _validators) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requiredConfirmations), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_initialSatellites, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_validators, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(SatelliteSystem.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SatelliteSystem> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger _requiredConfirmations,
            List<String> _initialSatellites, List<String> _validators) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requiredConfirmations), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_initialSatellites, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_validators, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(SatelliteSystem.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SatelliteSystem> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            BigInteger _requiredConfirmations, List<String> _initialSatellites,
            List<String> _validators) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requiredConfirmations), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_initialSatellites, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_validators, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(SatelliteSystem.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ActionTriggeredEventResponse extends BaseEventResponse {
        public String satellite;

        public byte[] alertId;

        public BigInteger action;

        public BigInteger timestamp;
    }

    public static class AlertConfirmedEventResponse extends BaseEventResponse {
        public byte[] alertId;

        public String confirmer;
    }

    public static class AlertSubmittedEventResponse extends BaseEventResponse {
        public byte[] alertId;

        public String sender;

        public String alertType;

        public BigInteger latitude;

        public BigInteger longitude;
    }

    public static class AlertValidatedEventResponse extends BaseEventResponse {
        public byte[] alertId;
    }
}
