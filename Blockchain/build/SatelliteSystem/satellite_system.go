// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package satellite

import (
	"errors"
	"math/big"
	"strings"

	ethereum "github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/event"
)

// Reference imports to suppress errors if they are not otherwise used.
var (
	_ = errors.New
	_ = big.NewInt
	_ = strings.NewReader
	_ = ethereum.NotFound
	_ = bind.Bind
	_ = common.Big1
	_ = types.BloomLookup
	_ = event.NewSubscription
	_ = abi.ConvertType
)

// SatelliteMetaData contains all meta data concerning the Satellite contract.
var SatelliteMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_requiredConfirmations\",\"type\":\"uint256\"},{\"internalType\":\"address[]\",\"name\":\"_initialSatellites\",\"type\":\"address[]\"},{\"internalType\":\"address[]\",\"name\":\"_validators\",\"type\":\"address[]\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"satellite\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"enumSatelliteSystem.SatelliteAction\",\"name\":\"action\",\"type\":\"uint8\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"alertId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"timestamp\",\"type\":\"uint256\"}],\"name\":\"ActionTriggered\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"alertId\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"confirmer\",\"type\":\"address\"}],\"name\":\"AlertConfirmed\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"alertId\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"alertType\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"latitude\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"longitude\",\"type\":\"uint256\"}],\"name\":\"AlertSubmitted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"alertId\",\"type\":\"bytes32\"}],\"name\":\"AlertValidated\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_validator\",\"type\":\"address\"}],\"name\":\"addValidator\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"alertIds\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"alerts\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"alertType\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"latitude\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"longitude\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"timestamp\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"confirmations\",\"type\":\"uint256\"},{\"internalType\":\"bool\",\"name\":\"validated\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"_alertId\",\"type\":\"bytes32\"}],\"name\":\"confirmAlert\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"_alertId\",\"type\":\"bytes32\"}],\"name\":\"getAlertConfirmers\",\"outputs\":[{\"internalType\":\"address[]\",\"name\":\"\",\"type\":\"address[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getAlertCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_satellite\",\"type\":\"address\"}],\"name\":\"registerSatellite\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"registeredSatellites\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_validator\",\"type\":\"address\"}],\"name\":\"removeValidator\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"requiredConfirmations\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"_alertType\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"_latitude\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_longitude\",\"type\":\"uint256\"}],\"name\":\"submitAlert\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_satellite\",\"type\":\"address\"},{\"internalType\":\"enumSatelliteSystem.SatelliteAction\",\"name\":\"_action\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"_alertId\",\"type\":\"bytes32\"}],\"name\":\"triggerAction\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_newValue\",\"type\":\"uint256\"}],\"name\":\"updateRequiredConfirmations\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"validators\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561000f575f5ffd5b506040516122fc3803806122fc83398181016040528101906100319190610343565b826001819055505f5f90505b82518110156100c257600160045f85848151811061005e5761005d6103cb565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff021916908315150217905550808060010191505061003d565b505f5f90505b815181101561014c5760015f5f8484815181106100e8576100e76103cb565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555080806001019150506100c8565b505050506103f8565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b61017881610166565b8114610182575f5ffd5b50565b5f815190506101938161016f565b92915050565b5f5ffd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6101e38261019d565b810181811067ffffffffffffffff82111715610202576102016101ad565b5b80604052505050565b5f610214610155565b905061022082826101da565b919050565b5f67ffffffffffffffff82111561023f5761023e6101ad565b5b602082029050602081019050919050565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61027d82610254565b9050919050565b61028d81610273565b8114610297575f5ffd5b50565b5f815190506102a881610284565b92915050565b5f6102c06102bb84610225565b61020b565b905080838252602082019050602084028301858111156102e3576102e2610250565b5b835b8181101561030c57806102f8888261029a565b8452602084019350506020810190506102e5565b5050509392505050565b5f82601f83011261032a57610329610199565b5b815161033a8482602086016102ae565b91505092915050565b5f5f5f6060848603121561035a5761035961015e565b5b5f61036786828701610185565b935050602084015167ffffffffffffffff81111561038857610387610162565b5b61039486828701610316565b925050604084015167ffffffffffffffff8111156103b5576103b4610162565b5b6103c186828701610316565b9150509250925092565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b611ef7806104055f395ff3fe608060405234801561000f575f5ffd5b50600436106100e8575f3560e01c806382e717f71161008a578063bce9252d11610064578063bce9252d14610230578063ccf07b6f14610266578063e2bcb68714610296578063fa52c7d8146102c6576100e8565b806382e717f7146101da578063b9ee0ecd146101f8578063bc437ea514610214576100e8565b80634c8142a9116100c65780634c8142a9146101565780634d238c8e1461017257806357baf2d61461018e57806381b2db50146101be576100e8565b80632b1e6478146100ec57806340411fc81461011c57806340a141ff1461013a575b5f5ffd5b61010660048036038101906101019190611283565b6102f6565b6040516101139190611307565b60405180910390f35b6101246105a2565b604051610131919061132f565b60405180910390f35b610154600480360381019061014f91906113a2565b6105ae565b005b610170600480360381019061016b91906113a2565b61068c565b005b61018c600480360381019061018791906113a2565b61076c565b005b6101a860048036038101906101a391906113a2565b61084b565b6040516101b591906113e7565b60405180910390f35b6101d860048036038101906101d39190611400565b610868565b005b6101e26108fa565b6040516101ef919061132f565b60405180910390f35b610212600480360381019061020d9190611478565b610900565b005b61022e600480360381019061022991906114c8565b610ac7565b005b61024a600480360381019061024591906114c8565b610e97565b60405161025d9796959493929190611562565b60405180910390f35b610280600480360381019061027b9190611400565b610f86565b60405161028d9190611307565b60405180910390f35b6102b060048036038101906102ab91906114c8565b610fa6565b6040516102bd919061168d565b60405180910390f35b6102e060048036038101906102db91906113a2565b611045565b6040516102ed91906113e7565b60405180910390f35b5f60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610380576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610377906116f7565b60405180910390fd5b5f338585854260405160200161039a9594939291906117b4565b6040516020818303038152906040528051906020012090506040518061010001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018581526020018481526020014281526020015f81526020015f151581526020015f67ffffffffffffffff81111561041a5761041961112c565b5b6040519080825280602002602001820160405280156104485781602001602082028036833780820191505090505b5081525060025f8381526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010190816104b69190611a0b565b5060408201518160020155606082015181600301556080820151816004015560a0820151816005015560c0820151816006015f6101000a81548160ff02191690831515021790555060e082015181600701908051906020019061051a929190611061565b50905050600381908060018154018082558091505060019003905f5260205f20015f90919091909150553373ffffffffffffffffffffffffffffffffffffffff16817f0de20b51b5905e09a080a027d931ab48f5d06e20e1ef027c541bcf69b8cd6c7687878760405161058f93929190611ada565b60405180910390a3809150509392505050565b5f600380549050905090565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610636576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161062d90611b60565b60405180910390fd5b5f5f5f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610714576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161070b90611b60565b60405180910390fd5b600160045f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff166107f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107eb90611b60565b60405180910390fd5b60015f5f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b6004602052805f5260405f205f915054906101000a900460ff1681565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff166108f0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108e790611b60565b60405180910390fd5b8060018190555050565b60015481565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610988576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161097f90611b60565b60405180910390fd5b60025f8281526020019081526020015f206006015f9054906101000a900460ff166109e8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109df90611bc8565b60405180910390fd5b60045f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610a71576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a6890611c30565b60405180910390fd5b808373ffffffffffffffffffffffffffffffffffffffff167fcef4123d1571ac7af64f14d12d1a2ba0fd84c5faa30676e42541bb5980362e338442604051610aba929190611cc1565b60405180910390a3505050565b60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610b50576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b47906116f7565b60405180910390fd5b60025f8281526020019081526020015f206006015f9054906101000a900460ff1615610bb1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ba890611d32565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff1660025f8381526020019081526020015f205f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610c51576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c4890611d9a565b60405180910390fd5b5f5f90505b60025f8381526020019081526020015f2060070180549050811015610d3d573373ffffffffffffffffffffffffffffffffffffffff1660025f8481526020019081526020015f206007018281548110610cb257610cb1611db8565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610d30576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d2790611e2f565b60405180910390fd5b8080600101915050610c56565b5060025f8281526020019081526020015f206005015f815480929190610d6290611e7a565b919050555060025f8281526020019081526020015f2060070133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff16817ff262571dcf418715bcbb5ae5cd9be1ad0c4a636132a229ce6cffe53abf63290760405160405180910390a360015460025f8381526020019081526020015f206005015410610e9457600160025f8381526020019081526020015f206006015f6101000a81548160ff021916908315150217905550807fb451c13ad4e11b923d4d71188e1e1aaa55c88e5189e0d7c2535436a1fcd6a15f60405160405180910390a25b50565b6002602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001018054610edb9061183b565b80601f0160208091040260200160405190810160405280929190818152602001828054610f079061183b565b8015610f525780601f10610f2957610100808354040283529160200191610f52565b820191905f5260205f20905b815481529060010190602001808311610f3557829003601f168201915b505050505090806002015490806003015490806004015490806005015490806006015f9054906101000a900460ff16905087565b60038181548110610f95575f80fd5b905f5260205f20015f915090505481565b606060025f8381526020019081526020015f2060070180548060200260200160405190810160405280929190818152602001828054801561103957602002820191905f5260205f20905b815f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311610ff0575b50505050509050919050565b5f602052805f5260405f205f915054906101000a900460ff1681565b828054828255905f5260205f209081019282156110d7579160200282015b828111156110d6578251825f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509160200191906001019061107f565b5b5090506110e491906110e8565b5090565b5b808211156110ff575f815f9055506001016110e9565b5090565b5f604051905090565b5f5ffd5b5f5ffd5b5f5ffd5b5f5ffd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6111628261111c565b810181811067ffffffffffffffff821117156111815761118061112c565b5b80604052505050565b5f611193611103565b905061119f8282611159565b919050565b5f67ffffffffffffffff8211156111be576111bd61112c565b5b6111c78261111c565b9050602081019050919050565b828183375f83830152505050565b5f6111f46111ef846111a4565b61118a565b9050828152602081018484840111156112105761120f611118565b5b61121b8482856111d4565b509392505050565b5f82601f83011261123757611236611114565b5b81356112478482602086016111e2565b91505092915050565b5f819050919050565b61126281611250565b811461126c575f5ffd5b50565b5f8135905061127d81611259565b92915050565b5f5f5f6060848603121561129a5761129961110c565b5b5f84013567ffffffffffffffff8111156112b7576112b6611110565b5b6112c386828701611223565b93505060206112d48682870161126f565b92505060406112e58682870161126f565b9150509250925092565b5f819050919050565b611301816112ef565b82525050565b5f60208201905061131a5f8301846112f8565b92915050565b61132981611250565b82525050565b5f6020820190506113425f830184611320565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61137182611348565b9050919050565b61138181611367565b811461138b575f5ffd5b50565b5f8135905061139c81611378565b92915050565b5f602082840312156113b7576113b661110c565b5b5f6113c48482850161138e565b91505092915050565b5f8115159050919050565b6113e1816113cd565b82525050565b5f6020820190506113fa5f8301846113d8565b92915050565b5f602082840312156114155761141461110c565b5b5f6114228482850161126f565b91505092915050565b60028110611437575f5ffd5b50565b5f813590506114488161142b565b92915050565b611457816112ef565b8114611461575f5ffd5b50565b5f813590506114728161144e565b92915050565b5f5f5f6060848603121561148f5761148e61110c565b5b5f61149c8682870161138e565b93505060206114ad8682870161143a565b92505060406114be86828701611464565b9150509250925092565b5f602082840312156114dd576114dc61110c565b5b5f6114ea84828501611464565b91505092915050565b6114fc81611367565b82525050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f61153482611502565b61153e818561150c565b935061154e81856020860161151c565b6115578161111c565b840191505092915050565b5f60e0820190506115755f83018a6114f3565b8181036020830152611587818961152a565b90506115966040830188611320565b6115a36060830187611320565b6115b06080830186611320565b6115bd60a0830185611320565b6115ca60c08301846113d8565b98975050505050505050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b61160881611367565b82525050565b5f61161983836115ff565b60208301905092915050565b5f602082019050919050565b5f61163b826115d6565b61164581856115e0565b9350611650836115f0565b805f5b83811015611680578151611667888261160e565b975061167283611625565b925050600181019050611653565b5085935050505092915050565b5f6020820190508181035f8301526116a58184611631565b905092915050565b7f4f6e6c79207265676973746572656420736174656c6c697465730000000000005f82015250565b5f6116e1601a8361150c565b91506116ec826116ad565b602082019050919050565b5f6020820190508181035f83015261170e816116d5565b9050919050565b5f8160601b9050919050565b5f61172b82611715565b9050919050565b5f61173c82611721565b9050919050565b61175461174f82611367565b611732565b82525050565b5f81905092915050565b5f61176e82611502565b611778818561175a565b935061178881856020860161151c565b80840191505092915050565b5f819050919050565b6117ae6117a982611250565b611794565b82525050565b5f6117bf8288611743565b6014820191506117cf8287611764565b91506117db828661179d565b6020820191506117eb828561179d565b6020820191506117fb828461179d565b6020820191508190509695505050505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061185257607f821691505b6020821081036118655761186461180e565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026118c77fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261188c565b6118d1868361188c565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61190c61190761190284611250565b6118e9565b611250565b9050919050565b5f819050919050565b611925836118f2565b61193961193182611913565b848454611898565b825550505050565b5f5f905090565b611950611941565b61195b81848461191c565b505050565b5b8181101561197e576119735f82611948565b600181019050611961565b5050565b601f8211156119c3576119948161186b565b61199d8461187d565b810160208510156119ac578190505b6119c06119b88561187d565b830182611960565b50505b505050565b5f82821c905092915050565b5f6119e35f19846008026119c8565b1980831691505092915050565b5f6119fb83836119d4565b9150826002028217905092915050565b611a1482611502565b67ffffffffffffffff811115611a2d57611a2c61112c565b5b611a37825461183b565b611a42828285611982565b5f60209050601f831160018114611a73575f8415611a61578287015190505b611a6b85826119f0565b865550611ad2565b601f198416611a818661186b565b5f5b82811015611aa857848901518255600182019150602085019450602081019050611a83565b86831015611ac55784890151611ac1601f8916826119d4565b8355505b6001600288020188555050505b505050505050565b5f6060820190508181035f830152611af2818661152a565b9050611b016020830185611320565b611b0e6040830184611320565b949350505050565b7f4f6e6c792076616c696461746f72732063616e2063616c6c00000000000000005f82015250565b5f611b4a60188361150c565b9150611b5582611b16565b602082019050919050565b5f6020820190508181035f830152611b7781611b3e565b9050919050565b7f416c657274206e6f742076616c696461746564000000000000000000000000005f82015250565b5f611bb260138361150c565b9150611bbd82611b7e565b602082019050919050565b5f6020820190508181035f830152611bdf81611ba6565b9050919050565b7f496e76616c696420736174656c6c6974650000000000000000000000000000005f82015250565b5f611c1a60118361150c565b9150611c2582611be6565b602082019050919050565b5f6020820190508181035f830152611c4781611c0e565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602160045260245ffd5b60028110611c8c57611c8b611c4e565b5b50565b5f819050611c9c82611c7b565b919050565b5f611cab82611c8f565b9050919050565b611cbb81611ca1565b82525050565b5f604082019050611cd45f830185611cb2565b611ce16020830184611320565b9392505050565b7f416c65727420616c72656164792076616c6964617465640000000000000000005f82015250565b5f611d1c60178361150c565b9150611d2782611ce8565b602082019050919050565b5f6020820190508181035f830152611d4981611d10565b9050919050565b7f43616e6e6f7420636f6e6669726d206f776e20616c65727400000000000000005f82015250565b5f611d8460188361150c565b9150611d8f82611d50565b602082019050919050565b5f6020820190508181035f830152611db181611d78565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f416c726561647920636f6e6669726d65640000000000000000000000000000005f82015250565b5f611e1960118361150c565b9150611e2482611de5565b602082019050919050565b5f6020820190508181035f830152611e4681611e0d565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611e8482611250565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611eb657611eb5611e4d565b5b60018201905091905056fea2646970667358221220c45939901829daf1529ce05a735d5bd68e02cd696839d17f79daceb732609ea564736f6c634300081d0033",
}

// SatelliteABI is the input ABI used to generate the binding from.
// Deprecated: Use SatelliteMetaData.ABI instead.
var SatelliteABI = SatelliteMetaData.ABI

// SatelliteBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use SatelliteMetaData.Bin instead.
var SatelliteBin = SatelliteMetaData.Bin

// DeploySatellite deploys a new Ethereum contract, binding an instance of Satellite to it.
func DeploySatellite(auth *bind.TransactOpts, backend bind.ContractBackend, _requiredConfirmations *big.Int, _initialSatellites []common.Address, _validators []common.Address) (common.Address, *types.Transaction, *Satellite, error) {
	parsed, err := SatelliteMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(SatelliteBin), backend, _requiredConfirmations, _initialSatellites, _validators)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &Satellite{SatelliteCaller: SatelliteCaller{contract: contract}, SatelliteTransactor: SatelliteTransactor{contract: contract}, SatelliteFilterer: SatelliteFilterer{contract: contract}}, nil
}

// Satellite is an auto generated Go binding around an Ethereum contract.
type Satellite struct {
	SatelliteCaller     // Read-only binding to the contract
	SatelliteTransactor // Write-only binding to the contract
	SatelliteFilterer   // Log filterer for contract events
}

// SatelliteCaller is an auto generated read-only Go binding around an Ethereum contract.
type SatelliteCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SatelliteTransactor is an auto generated write-only Go binding around an Ethereum contract.
type SatelliteTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SatelliteFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type SatelliteFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SatelliteSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type SatelliteSession struct {
	Contract     *Satellite        // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// SatelliteCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type SatelliteCallerSession struct {
	Contract *SatelliteCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts    // Call options to use throughout this session
}

// SatelliteTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type SatelliteTransactorSession struct {
	Contract     *SatelliteTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts    // Transaction auth options to use throughout this session
}

// SatelliteRaw is an auto generated low-level Go binding around an Ethereum contract.
type SatelliteRaw struct {
	Contract *Satellite // Generic contract binding to access the raw methods on
}

// SatelliteCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type SatelliteCallerRaw struct {
	Contract *SatelliteCaller // Generic read-only contract binding to access the raw methods on
}

// SatelliteTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type SatelliteTransactorRaw struct {
	Contract *SatelliteTransactor // Generic write-only contract binding to access the raw methods on
}

// NewSatellite creates a new instance of Satellite, bound to a specific deployed contract.
func NewSatellite(address common.Address, backend bind.ContractBackend) (*Satellite, error) {
	contract, err := bindSatellite(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &Satellite{SatelliteCaller: SatelliteCaller{contract: contract}, SatelliteTransactor: SatelliteTransactor{contract: contract}, SatelliteFilterer: SatelliteFilterer{contract: contract}}, nil
}

// NewSatelliteCaller creates a new read-only instance of Satellite, bound to a specific deployed contract.
func NewSatelliteCaller(address common.Address, caller bind.ContractCaller) (*SatelliteCaller, error) {
	contract, err := bindSatellite(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &SatelliteCaller{contract: contract}, nil
}

// NewSatelliteTransactor creates a new write-only instance of Satellite, bound to a specific deployed contract.
func NewSatelliteTransactor(address common.Address, transactor bind.ContractTransactor) (*SatelliteTransactor, error) {
	contract, err := bindSatellite(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &SatelliteTransactor{contract: contract}, nil
}

// NewSatelliteFilterer creates a new log filterer instance of Satellite, bound to a specific deployed contract.
func NewSatelliteFilterer(address common.Address, filterer bind.ContractFilterer) (*SatelliteFilterer, error) {
	contract, err := bindSatellite(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &SatelliteFilterer{contract: contract}, nil
}

// bindSatellite binds a generic wrapper to an already deployed contract.
func bindSatellite(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := SatelliteMetaData.GetAbi()
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, *parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Satellite *SatelliteRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Satellite.Contract.SatelliteCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Satellite *SatelliteRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Satellite.Contract.SatelliteTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Satellite *SatelliteRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Satellite.Contract.SatelliteTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Satellite *SatelliteCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Satellite.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Satellite *SatelliteTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Satellite.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Satellite *SatelliteTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Satellite.Contract.contract.Transact(opts, method, params...)
}

// AlertIds is a free data retrieval call binding the contract method 0xccf07b6f.
//
// Solidity: function alertIds(uint256 ) view returns(bytes32)
func (_Satellite *SatelliteCaller) AlertIds(opts *bind.CallOpts, arg0 *big.Int) ([32]byte, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "alertIds", arg0)

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// AlertIds is a free data retrieval call binding the contract method 0xccf07b6f.
//
// Solidity: function alertIds(uint256 ) view returns(bytes32)
func (_Satellite *SatelliteSession) AlertIds(arg0 *big.Int) ([32]byte, error) {
	return _Satellite.Contract.AlertIds(&_Satellite.CallOpts, arg0)
}

// AlertIds is a free data retrieval call binding the contract method 0xccf07b6f.
//
// Solidity: function alertIds(uint256 ) view returns(bytes32)
func (_Satellite *SatelliteCallerSession) AlertIds(arg0 *big.Int) ([32]byte, error) {
	return _Satellite.Contract.AlertIds(&_Satellite.CallOpts, arg0)
}

// Alerts is a free data retrieval call binding the contract method 0xbce9252d.
//
// Solidity: function alerts(bytes32 ) view returns(address sender, string alertType, uint256 latitude, uint256 longitude, uint256 timestamp, uint256 confirmations, bool validated)
func (_Satellite *SatelliteCaller) Alerts(opts *bind.CallOpts, arg0 [32]byte) (struct {
	Sender        common.Address
	AlertType     string
	Latitude      *big.Int
	Longitude     *big.Int
	Timestamp     *big.Int
	Confirmations *big.Int
	Validated     bool
}, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "alerts", arg0)

	outstruct := new(struct {
		Sender        common.Address
		AlertType     string
		Latitude      *big.Int
		Longitude     *big.Int
		Timestamp     *big.Int
		Confirmations *big.Int
		Validated     bool
	})
	if err != nil {
		return *outstruct, err
	}

	outstruct.Sender = *abi.ConvertType(out[0], new(common.Address)).(*common.Address)
	outstruct.AlertType = *abi.ConvertType(out[1], new(string)).(*string)
	outstruct.Latitude = *abi.ConvertType(out[2], new(*big.Int)).(**big.Int)
	outstruct.Longitude = *abi.ConvertType(out[3], new(*big.Int)).(**big.Int)
	outstruct.Timestamp = *abi.ConvertType(out[4], new(*big.Int)).(**big.Int)
	outstruct.Confirmations = *abi.ConvertType(out[5], new(*big.Int)).(**big.Int)
	outstruct.Validated = *abi.ConvertType(out[6], new(bool)).(*bool)

	return *outstruct, err

}

// Alerts is a free data retrieval call binding the contract method 0xbce9252d.
//
// Solidity: function alerts(bytes32 ) view returns(address sender, string alertType, uint256 latitude, uint256 longitude, uint256 timestamp, uint256 confirmations, bool validated)
func (_Satellite *SatelliteSession) Alerts(arg0 [32]byte) (struct {
	Sender        common.Address
	AlertType     string
	Latitude      *big.Int
	Longitude     *big.Int
	Timestamp     *big.Int
	Confirmations *big.Int
	Validated     bool
}, error) {
	return _Satellite.Contract.Alerts(&_Satellite.CallOpts, arg0)
}

// Alerts is a free data retrieval call binding the contract method 0xbce9252d.
//
// Solidity: function alerts(bytes32 ) view returns(address sender, string alertType, uint256 latitude, uint256 longitude, uint256 timestamp, uint256 confirmations, bool validated)
func (_Satellite *SatelliteCallerSession) Alerts(arg0 [32]byte) (struct {
	Sender        common.Address
	AlertType     string
	Latitude      *big.Int
	Longitude     *big.Int
	Timestamp     *big.Int
	Confirmations *big.Int
	Validated     bool
}, error) {
	return _Satellite.Contract.Alerts(&_Satellite.CallOpts, arg0)
}

// GetAlertConfirmers is a free data retrieval call binding the contract method 0xe2bcb687.
//
// Solidity: function getAlertConfirmers(bytes32 _alertId) view returns(address[])
func (_Satellite *SatelliteCaller) GetAlertConfirmers(opts *bind.CallOpts, _alertId [32]byte) ([]common.Address, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "getAlertConfirmers", _alertId)

	if err != nil {
		return *new([]common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new([]common.Address)).(*[]common.Address)

	return out0, err

}

// GetAlertConfirmers is a free data retrieval call binding the contract method 0xe2bcb687.
//
// Solidity: function getAlertConfirmers(bytes32 _alertId) view returns(address[])
func (_Satellite *SatelliteSession) GetAlertConfirmers(_alertId [32]byte) ([]common.Address, error) {
	return _Satellite.Contract.GetAlertConfirmers(&_Satellite.CallOpts, _alertId)
}

// GetAlertConfirmers is a free data retrieval call binding the contract method 0xe2bcb687.
//
// Solidity: function getAlertConfirmers(bytes32 _alertId) view returns(address[])
func (_Satellite *SatelliteCallerSession) GetAlertConfirmers(_alertId [32]byte) ([]common.Address, error) {
	return _Satellite.Contract.GetAlertConfirmers(&_Satellite.CallOpts, _alertId)
}

// GetAlertCount is a free data retrieval call binding the contract method 0x40411fc8.
//
// Solidity: function getAlertCount() view returns(uint256)
func (_Satellite *SatelliteCaller) GetAlertCount(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "getAlertCount")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// GetAlertCount is a free data retrieval call binding the contract method 0x40411fc8.
//
// Solidity: function getAlertCount() view returns(uint256)
func (_Satellite *SatelliteSession) GetAlertCount() (*big.Int, error) {
	return _Satellite.Contract.GetAlertCount(&_Satellite.CallOpts)
}

// GetAlertCount is a free data retrieval call binding the contract method 0x40411fc8.
//
// Solidity: function getAlertCount() view returns(uint256)
func (_Satellite *SatelliteCallerSession) GetAlertCount() (*big.Int, error) {
	return _Satellite.Contract.GetAlertCount(&_Satellite.CallOpts)
}

// RegisteredSatellites is a free data retrieval call binding the contract method 0x57baf2d6.
//
// Solidity: function registeredSatellites(address ) view returns(bool)
func (_Satellite *SatelliteCaller) RegisteredSatellites(opts *bind.CallOpts, arg0 common.Address) (bool, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "registeredSatellites", arg0)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// RegisteredSatellites is a free data retrieval call binding the contract method 0x57baf2d6.
//
// Solidity: function registeredSatellites(address ) view returns(bool)
func (_Satellite *SatelliteSession) RegisteredSatellites(arg0 common.Address) (bool, error) {
	return _Satellite.Contract.RegisteredSatellites(&_Satellite.CallOpts, arg0)
}

// RegisteredSatellites is a free data retrieval call binding the contract method 0x57baf2d6.
//
// Solidity: function registeredSatellites(address ) view returns(bool)
func (_Satellite *SatelliteCallerSession) RegisteredSatellites(arg0 common.Address) (bool, error) {
	return _Satellite.Contract.RegisteredSatellites(&_Satellite.CallOpts, arg0)
}

// RequiredConfirmations is a free data retrieval call binding the contract method 0x82e717f7.
//
// Solidity: function requiredConfirmations() view returns(uint256)
func (_Satellite *SatelliteCaller) RequiredConfirmations(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "requiredConfirmations")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// RequiredConfirmations is a free data retrieval call binding the contract method 0x82e717f7.
//
// Solidity: function requiredConfirmations() view returns(uint256)
func (_Satellite *SatelliteSession) RequiredConfirmations() (*big.Int, error) {
	return _Satellite.Contract.RequiredConfirmations(&_Satellite.CallOpts)
}

// RequiredConfirmations is a free data retrieval call binding the contract method 0x82e717f7.
//
// Solidity: function requiredConfirmations() view returns(uint256)
func (_Satellite *SatelliteCallerSession) RequiredConfirmations() (*big.Int, error) {
	return _Satellite.Contract.RequiredConfirmations(&_Satellite.CallOpts)
}

// Validators is a free data retrieval call binding the contract method 0xfa52c7d8.
//
// Solidity: function validators(address ) view returns(bool)
func (_Satellite *SatelliteCaller) Validators(opts *bind.CallOpts, arg0 common.Address) (bool, error) {
	var out []interface{}
	err := _Satellite.contract.Call(opts, &out, "validators", arg0)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// Validators is a free data retrieval call binding the contract method 0xfa52c7d8.
//
// Solidity: function validators(address ) view returns(bool)
func (_Satellite *SatelliteSession) Validators(arg0 common.Address) (bool, error) {
	return _Satellite.Contract.Validators(&_Satellite.CallOpts, arg0)
}

// Validators is a free data retrieval call binding the contract method 0xfa52c7d8.
//
// Solidity: function validators(address ) view returns(bool)
func (_Satellite *SatelliteCallerSession) Validators(arg0 common.Address) (bool, error) {
	return _Satellite.Contract.Validators(&_Satellite.CallOpts, arg0)
}

// AddValidator is a paid mutator transaction binding the contract method 0x4d238c8e.
//
// Solidity: function addValidator(address _validator) returns()
func (_Satellite *SatelliteTransactor) AddValidator(opts *bind.TransactOpts, _validator common.Address) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "addValidator", _validator)
}

// AddValidator is a paid mutator transaction binding the contract method 0x4d238c8e.
//
// Solidity: function addValidator(address _validator) returns()
func (_Satellite *SatelliteSession) AddValidator(_validator common.Address) (*types.Transaction, error) {
	return _Satellite.Contract.AddValidator(&_Satellite.TransactOpts, _validator)
}

// AddValidator is a paid mutator transaction binding the contract method 0x4d238c8e.
//
// Solidity: function addValidator(address _validator) returns()
func (_Satellite *SatelliteTransactorSession) AddValidator(_validator common.Address) (*types.Transaction, error) {
	return _Satellite.Contract.AddValidator(&_Satellite.TransactOpts, _validator)
}

// ConfirmAlert is a paid mutator transaction binding the contract method 0xbc437ea5.
//
// Solidity: function confirmAlert(bytes32 _alertId) returns()
func (_Satellite *SatelliteTransactor) ConfirmAlert(opts *bind.TransactOpts, _alertId [32]byte) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "confirmAlert", _alertId)
}

// ConfirmAlert is a paid mutator transaction binding the contract method 0xbc437ea5.
//
// Solidity: function confirmAlert(bytes32 _alertId) returns()
func (_Satellite *SatelliteSession) ConfirmAlert(_alertId [32]byte) (*types.Transaction, error) {
	return _Satellite.Contract.ConfirmAlert(&_Satellite.TransactOpts, _alertId)
}

// ConfirmAlert is a paid mutator transaction binding the contract method 0xbc437ea5.
//
// Solidity: function confirmAlert(bytes32 _alertId) returns()
func (_Satellite *SatelliteTransactorSession) ConfirmAlert(_alertId [32]byte) (*types.Transaction, error) {
	return _Satellite.Contract.ConfirmAlert(&_Satellite.TransactOpts, _alertId)
}

// RegisterSatellite is a paid mutator transaction binding the contract method 0x4c8142a9.
//
// Solidity: function registerSatellite(address _satellite) returns()
func (_Satellite *SatelliteTransactor) RegisterSatellite(opts *bind.TransactOpts, _satellite common.Address) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "registerSatellite", _satellite)
}

// RegisterSatellite is a paid mutator transaction binding the contract method 0x4c8142a9.
//
// Solidity: function registerSatellite(address _satellite) returns()
func (_Satellite *SatelliteSession) RegisterSatellite(_satellite common.Address) (*types.Transaction, error) {
	return _Satellite.Contract.RegisterSatellite(&_Satellite.TransactOpts, _satellite)
}

// RegisterSatellite is a paid mutator transaction binding the contract method 0x4c8142a9.
//
// Solidity: function registerSatellite(address _satellite) returns()
func (_Satellite *SatelliteTransactorSession) RegisterSatellite(_satellite common.Address) (*types.Transaction, error) {
	return _Satellite.Contract.RegisterSatellite(&_Satellite.TransactOpts, _satellite)
}

// RemoveValidator is a paid mutator transaction binding the contract method 0x40a141ff.
//
// Solidity: function removeValidator(address _validator) returns()
func (_Satellite *SatelliteTransactor) RemoveValidator(opts *bind.TransactOpts, _validator common.Address) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "removeValidator", _validator)
}

// RemoveValidator is a paid mutator transaction binding the contract method 0x40a141ff.
//
// Solidity: function removeValidator(address _validator) returns()
func (_Satellite *SatelliteSession) RemoveValidator(_validator common.Address) (*types.Transaction, error) {
	return _Satellite.Contract.RemoveValidator(&_Satellite.TransactOpts, _validator)
}

// RemoveValidator is a paid mutator transaction binding the contract method 0x40a141ff.
//
// Solidity: function removeValidator(address _validator) returns()
func (_Satellite *SatelliteTransactorSession) RemoveValidator(_validator common.Address) (*types.Transaction, error) {
	return _Satellite.Contract.RemoveValidator(&_Satellite.TransactOpts, _validator)
}

// SubmitAlert is a paid mutator transaction binding the contract method 0x2b1e6478.
//
// Solidity: function submitAlert(string _alertType, uint256 _latitude, uint256 _longitude) returns(bytes32)
func (_Satellite *SatelliteTransactor) SubmitAlert(opts *bind.TransactOpts, _alertType string, _latitude *big.Int, _longitude *big.Int) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "submitAlert", _alertType, _latitude, _longitude)
}

// SubmitAlert is a paid mutator transaction binding the contract method 0x2b1e6478.
//
// Solidity: function submitAlert(string _alertType, uint256 _latitude, uint256 _longitude) returns(bytes32)
func (_Satellite *SatelliteSession) SubmitAlert(_alertType string, _latitude *big.Int, _longitude *big.Int) (*types.Transaction, error) {
	return _Satellite.Contract.SubmitAlert(&_Satellite.TransactOpts, _alertType, _latitude, _longitude)
}

// SubmitAlert is a paid mutator transaction binding the contract method 0x2b1e6478.
//
// Solidity: function submitAlert(string _alertType, uint256 _latitude, uint256 _longitude) returns(bytes32)
func (_Satellite *SatelliteTransactorSession) SubmitAlert(_alertType string, _latitude *big.Int, _longitude *big.Int) (*types.Transaction, error) {
	return _Satellite.Contract.SubmitAlert(&_Satellite.TransactOpts, _alertType, _latitude, _longitude)
}

// TriggerAction is a paid mutator transaction binding the contract method 0xb9ee0ecd.
//
// Solidity: function triggerAction(address _satellite, uint8 _action, bytes32 _alertId) returns()
func (_Satellite *SatelliteTransactor) TriggerAction(opts *bind.TransactOpts, _satellite common.Address, _action uint8, _alertId [32]byte) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "triggerAction", _satellite, _action, _alertId)
}

// TriggerAction is a paid mutator transaction binding the contract method 0xb9ee0ecd.
//
// Solidity: function triggerAction(address _satellite, uint8 _action, bytes32 _alertId) returns()
func (_Satellite *SatelliteSession) TriggerAction(_satellite common.Address, _action uint8, _alertId [32]byte) (*types.Transaction, error) {
	return _Satellite.Contract.TriggerAction(&_Satellite.TransactOpts, _satellite, _action, _alertId)
}

// TriggerAction is a paid mutator transaction binding the contract method 0xb9ee0ecd.
//
// Solidity: function triggerAction(address _satellite, uint8 _action, bytes32 _alertId) returns()
func (_Satellite *SatelliteTransactorSession) TriggerAction(_satellite common.Address, _action uint8, _alertId [32]byte) (*types.Transaction, error) {
	return _Satellite.Contract.TriggerAction(&_Satellite.TransactOpts, _satellite, _action, _alertId)
}

// UpdateRequiredConfirmations is a paid mutator transaction binding the contract method 0x81b2db50.
//
// Solidity: function updateRequiredConfirmations(uint256 _newValue) returns()
func (_Satellite *SatelliteTransactor) UpdateRequiredConfirmations(opts *bind.TransactOpts, _newValue *big.Int) (*types.Transaction, error) {
	return _Satellite.contract.Transact(opts, "updateRequiredConfirmations", _newValue)
}

// UpdateRequiredConfirmations is a paid mutator transaction binding the contract method 0x81b2db50.
//
// Solidity: function updateRequiredConfirmations(uint256 _newValue) returns()
func (_Satellite *SatelliteSession) UpdateRequiredConfirmations(_newValue *big.Int) (*types.Transaction, error) {
	return _Satellite.Contract.UpdateRequiredConfirmations(&_Satellite.TransactOpts, _newValue)
}

// UpdateRequiredConfirmations is a paid mutator transaction binding the contract method 0x81b2db50.
//
// Solidity: function updateRequiredConfirmations(uint256 _newValue) returns()
func (_Satellite *SatelliteTransactorSession) UpdateRequiredConfirmations(_newValue *big.Int) (*types.Transaction, error) {
	return _Satellite.Contract.UpdateRequiredConfirmations(&_Satellite.TransactOpts, _newValue)
}

// SatelliteActionTriggeredIterator is returned from FilterActionTriggered and is used to iterate over the raw logs and unpacked data for ActionTriggered events raised by the Satellite contract.
type SatelliteActionTriggeredIterator struct {
	Event *SatelliteActionTriggered // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *SatelliteActionTriggeredIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SatelliteActionTriggered)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(SatelliteActionTriggered)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *SatelliteActionTriggeredIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SatelliteActionTriggeredIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SatelliteActionTriggered represents a ActionTriggered event raised by the Satellite contract.
type SatelliteActionTriggered struct {
	Satellite common.Address
	Action    uint8
	AlertId   [32]byte
	Timestamp *big.Int
	Raw       types.Log // Blockchain specific contextual infos
}

// FilterActionTriggered is a free log retrieval operation binding the contract event 0xcef4123d1571ac7af64f14d12d1a2ba0fd84c5faa30676e42541bb5980362e33.
//
// Solidity: event ActionTriggered(address indexed satellite, uint8 action, bytes32 indexed alertId, uint256 timestamp)
func (_Satellite *SatelliteFilterer) FilterActionTriggered(opts *bind.FilterOpts, satellite []common.Address, alertId [][32]byte) (*SatelliteActionTriggeredIterator, error) {

	var satelliteRule []interface{}
	for _, satelliteItem := range satellite {
		satelliteRule = append(satelliteRule, satelliteItem)
	}

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}

	logs, sub, err := _Satellite.contract.FilterLogs(opts, "ActionTriggered", satelliteRule, alertIdRule)
	if err != nil {
		return nil, err
	}
	return &SatelliteActionTriggeredIterator{contract: _Satellite.contract, event: "ActionTriggered", logs: logs, sub: sub}, nil
}

// WatchActionTriggered is a free log subscription operation binding the contract event 0xcef4123d1571ac7af64f14d12d1a2ba0fd84c5faa30676e42541bb5980362e33.
//
// Solidity: event ActionTriggered(address indexed satellite, uint8 action, bytes32 indexed alertId, uint256 timestamp)
func (_Satellite *SatelliteFilterer) WatchActionTriggered(opts *bind.WatchOpts, sink chan<- *SatelliteActionTriggered, satellite []common.Address, alertId [][32]byte) (event.Subscription, error) {

	var satelliteRule []interface{}
	for _, satelliteItem := range satellite {
		satelliteRule = append(satelliteRule, satelliteItem)
	}

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}

	logs, sub, err := _Satellite.contract.WatchLogs(opts, "ActionTriggered", satelliteRule, alertIdRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SatelliteActionTriggered)
				if err := _Satellite.contract.UnpackLog(event, "ActionTriggered", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseActionTriggered is a log parse operation binding the contract event 0xcef4123d1571ac7af64f14d12d1a2ba0fd84c5faa30676e42541bb5980362e33.
//
// Solidity: event ActionTriggered(address indexed satellite, uint8 action, bytes32 indexed alertId, uint256 timestamp)
func (_Satellite *SatelliteFilterer) ParseActionTriggered(log types.Log) (*SatelliteActionTriggered, error) {
	event := new(SatelliteActionTriggered)
	if err := _Satellite.contract.UnpackLog(event, "ActionTriggered", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SatelliteAlertConfirmedIterator is returned from FilterAlertConfirmed and is used to iterate over the raw logs and unpacked data for AlertConfirmed events raised by the Satellite contract.
type SatelliteAlertConfirmedIterator struct {
	Event *SatelliteAlertConfirmed // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *SatelliteAlertConfirmedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SatelliteAlertConfirmed)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(SatelliteAlertConfirmed)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *SatelliteAlertConfirmedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SatelliteAlertConfirmedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SatelliteAlertConfirmed represents a AlertConfirmed event raised by the Satellite contract.
type SatelliteAlertConfirmed struct {
	AlertId   [32]byte
	Confirmer common.Address
	Raw       types.Log // Blockchain specific contextual infos
}

// FilterAlertConfirmed is a free log retrieval operation binding the contract event 0xf262571dcf418715bcbb5ae5cd9be1ad0c4a636132a229ce6cffe53abf632907.
//
// Solidity: event AlertConfirmed(bytes32 indexed alertId, address indexed confirmer)
func (_Satellite *SatelliteFilterer) FilterAlertConfirmed(opts *bind.FilterOpts, alertId [][32]byte, confirmer []common.Address) (*SatelliteAlertConfirmedIterator, error) {

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}
	var confirmerRule []interface{}
	for _, confirmerItem := range confirmer {
		confirmerRule = append(confirmerRule, confirmerItem)
	}

	logs, sub, err := _Satellite.contract.FilterLogs(opts, "AlertConfirmed", alertIdRule, confirmerRule)
	if err != nil {
		return nil, err
	}
	return &SatelliteAlertConfirmedIterator{contract: _Satellite.contract, event: "AlertConfirmed", logs: logs, sub: sub}, nil
}

// WatchAlertConfirmed is a free log subscription operation binding the contract event 0xf262571dcf418715bcbb5ae5cd9be1ad0c4a636132a229ce6cffe53abf632907.
//
// Solidity: event AlertConfirmed(bytes32 indexed alertId, address indexed confirmer)
func (_Satellite *SatelliteFilterer) WatchAlertConfirmed(opts *bind.WatchOpts, sink chan<- *SatelliteAlertConfirmed, alertId [][32]byte, confirmer []common.Address) (event.Subscription, error) {

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}
	var confirmerRule []interface{}
	for _, confirmerItem := range confirmer {
		confirmerRule = append(confirmerRule, confirmerItem)
	}

	logs, sub, err := _Satellite.contract.WatchLogs(opts, "AlertConfirmed", alertIdRule, confirmerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SatelliteAlertConfirmed)
				if err := _Satellite.contract.UnpackLog(event, "AlertConfirmed", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseAlertConfirmed is a log parse operation binding the contract event 0xf262571dcf418715bcbb5ae5cd9be1ad0c4a636132a229ce6cffe53abf632907.
//
// Solidity: event AlertConfirmed(bytes32 indexed alertId, address indexed confirmer)
func (_Satellite *SatelliteFilterer) ParseAlertConfirmed(log types.Log) (*SatelliteAlertConfirmed, error) {
	event := new(SatelliteAlertConfirmed)
	if err := _Satellite.contract.UnpackLog(event, "AlertConfirmed", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SatelliteAlertSubmittedIterator is returned from FilterAlertSubmitted and is used to iterate over the raw logs and unpacked data for AlertSubmitted events raised by the Satellite contract.
type SatelliteAlertSubmittedIterator struct {
	Event *SatelliteAlertSubmitted // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *SatelliteAlertSubmittedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SatelliteAlertSubmitted)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(SatelliteAlertSubmitted)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *SatelliteAlertSubmittedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SatelliteAlertSubmittedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SatelliteAlertSubmitted represents a AlertSubmitted event raised by the Satellite contract.
type SatelliteAlertSubmitted struct {
	AlertId   [32]byte
	Sender    common.Address
	AlertType string
	Latitude  *big.Int
	Longitude *big.Int
	Raw       types.Log // Blockchain specific contextual infos
}

// FilterAlertSubmitted is a free log retrieval operation binding the contract event 0x0de20b51b5905e09a080a027d931ab48f5d06e20e1ef027c541bcf69b8cd6c76.
//
// Solidity: event AlertSubmitted(bytes32 indexed alertId, address indexed sender, string alertType, uint256 latitude, uint256 longitude)
func (_Satellite *SatelliteFilterer) FilterAlertSubmitted(opts *bind.FilterOpts, alertId [][32]byte, sender []common.Address) (*SatelliteAlertSubmittedIterator, error) {

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}
	var senderRule []interface{}
	for _, senderItem := range sender {
		senderRule = append(senderRule, senderItem)
	}

	logs, sub, err := _Satellite.contract.FilterLogs(opts, "AlertSubmitted", alertIdRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &SatelliteAlertSubmittedIterator{contract: _Satellite.contract, event: "AlertSubmitted", logs: logs, sub: sub}, nil
}

// WatchAlertSubmitted is a free log subscription operation binding the contract event 0x0de20b51b5905e09a080a027d931ab48f5d06e20e1ef027c541bcf69b8cd6c76.
//
// Solidity: event AlertSubmitted(bytes32 indexed alertId, address indexed sender, string alertType, uint256 latitude, uint256 longitude)
func (_Satellite *SatelliteFilterer) WatchAlertSubmitted(opts *bind.WatchOpts, sink chan<- *SatelliteAlertSubmitted, alertId [][32]byte, sender []common.Address) (event.Subscription, error) {

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}
	var senderRule []interface{}
	for _, senderItem := range sender {
		senderRule = append(senderRule, senderItem)
	}

	logs, sub, err := _Satellite.contract.WatchLogs(opts, "AlertSubmitted", alertIdRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SatelliteAlertSubmitted)
				if err := _Satellite.contract.UnpackLog(event, "AlertSubmitted", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseAlertSubmitted is a log parse operation binding the contract event 0x0de20b51b5905e09a080a027d931ab48f5d06e20e1ef027c541bcf69b8cd6c76.
//
// Solidity: event AlertSubmitted(bytes32 indexed alertId, address indexed sender, string alertType, uint256 latitude, uint256 longitude)
func (_Satellite *SatelliteFilterer) ParseAlertSubmitted(log types.Log) (*SatelliteAlertSubmitted, error) {
	event := new(SatelliteAlertSubmitted)
	if err := _Satellite.contract.UnpackLog(event, "AlertSubmitted", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SatelliteAlertValidatedIterator is returned from FilterAlertValidated and is used to iterate over the raw logs and unpacked data for AlertValidated events raised by the Satellite contract.
type SatelliteAlertValidatedIterator struct {
	Event *SatelliteAlertValidated // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *SatelliteAlertValidatedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SatelliteAlertValidated)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(SatelliteAlertValidated)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *SatelliteAlertValidatedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SatelliteAlertValidatedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SatelliteAlertValidated represents a AlertValidated event raised by the Satellite contract.
type SatelliteAlertValidated struct {
	AlertId [32]byte
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterAlertValidated is a free log retrieval operation binding the contract event 0xb451c13ad4e11b923d4d71188e1e1aaa55c88e5189e0d7c2535436a1fcd6a15f.
//
// Solidity: event AlertValidated(bytes32 indexed alertId)
func (_Satellite *SatelliteFilterer) FilterAlertValidated(opts *bind.FilterOpts, alertId [][32]byte) (*SatelliteAlertValidatedIterator, error) {

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}

	logs, sub, err := _Satellite.contract.FilterLogs(opts, "AlertValidated", alertIdRule)
	if err != nil {
		return nil, err
	}
	return &SatelliteAlertValidatedIterator{contract: _Satellite.contract, event: "AlertValidated", logs: logs, sub: sub}, nil
}

// WatchAlertValidated is a free log subscription operation binding the contract event 0xb451c13ad4e11b923d4d71188e1e1aaa55c88e5189e0d7c2535436a1fcd6a15f.
//
// Solidity: event AlertValidated(bytes32 indexed alertId)
func (_Satellite *SatelliteFilterer) WatchAlertValidated(opts *bind.WatchOpts, sink chan<- *SatelliteAlertValidated, alertId [][32]byte) (event.Subscription, error) {

	var alertIdRule []interface{}
	for _, alertIdItem := range alertId {
		alertIdRule = append(alertIdRule, alertIdItem)
	}

	logs, sub, err := _Satellite.contract.WatchLogs(opts, "AlertValidated", alertIdRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SatelliteAlertValidated)
				if err := _Satellite.contract.UnpackLog(event, "AlertValidated", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseAlertValidated is a log parse operation binding the contract event 0xb451c13ad4e11b923d4d71188e1e1aaa55c88e5189e0d7c2535436a1fcd6a15f.
//
// Solidity: event AlertValidated(bytes32 indexed alertId)
func (_Satellite *SatelliteFilterer) ParseAlertValidated(log types.Log) (*SatelliteAlertValidated, error) {
	event := new(SatelliteAlertValidated)
	if err := _Satellite.contract.UnpackLog(event, "AlertValidated", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
