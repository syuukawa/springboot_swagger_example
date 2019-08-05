package guru.springframework.nervos.controllers;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import guru.springframework.domain.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bitcoinj.crypto.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.nervos.ckb.address.AddressUtils;
import org.nervos.ckb.crypto.secp256k1.Sign;
import org.nervos.ckb.utils.Network;
import org.nervos.ckb.utils.Numeric;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ckb/chain")
@Api(value = "/ckb/chain", description = "CKB Chain Operaction")
public class CKBChainController {

//
//    private String nodeUrl = "http://localhost:8114";
////    @Autowired
////    private NervosUrl nervosUrl;
//
//    @ApiOperation(value = "Returns the information about a block by hash")
//    @RequestMapping(value = "/get_block/{blockHash}", method = RequestMethod.GET, produces = "application/json")
//    public Object get_block(@PathVariable String blockHash) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_block", new Object[]{blockHash}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Get block by number", response = Product.class)
//    @RequestMapping(value = "/block_number/{blockNumber}", method = RequestMethod.GET)
//    public Object block_number(@PathVariable String blockNumber) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("block_number", new Object[]{blockNumber}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Returns the hash of a block in the best-block-chain by block number; block of No.0 is the genesis block.")
//    @RequestMapping(value = "/get_block_hash/{blockNumber}", method = RequestMethod.GET, produces = "application/json")
//    public Object get_block_hash(@PathVariable String blockNumber) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_block_hash", new Object[]{blockNumber}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    //    lock_hash - Cell lock script hash
////    from - Start block number
////    to - End block number
//    @ApiOperation(value = "Returns the information about cells collection by the hash of lock script.")
//    @RequestMapping(value = "/get_cells_by_lock_hash/{lock_hash}/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
//    public Object get_cells_by_lock_hash(@PathVariable String lock_hash, @PathVariable String from, @PathVariable String to) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_cells_by_lock_hash", new Object[]{lock_hash, from, to}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Returns the information about the current epoch..")
//    @RequestMapping(value = "/get_current_epoch", method = RequestMethod.GET, produces = "application/json")
//    public Object get_current_epoch() {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_current_epoch", new Object[]{}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Return the information corresponding the given epoch number...")
//    @RequestMapping(value = "/get_epoch_by_number/{number}", method = RequestMethod.GET, produces = "application/json")
//    public Object get_epoch_by_number(@PathVariable String number) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_epoch_by_number", new Object[]{number}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Returns the information about a block header by hash..")
//    @RequestMapping(value = "/get_header/{hash}", method = RequestMethod.GET, produces = "application/json")
//    public Object get_header(@PathVariable String hash) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_header", new Object[]{hash}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Returns the information about a block header by block number..")
//    @RequestMapping(value = "/get_header_by_number/{number}", method = RequestMethod.GET, produces = "application/json")
//    public Object get_header_by_number(@PathVariable String number) {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_header_by_number", new Object[]{number}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//
//    //TODO 参数传递的问题；多层级结构
////    @ApiOperation(value = "Returns the information about a cell by out_point. If <block_hash> is not specific, returns the cell if it is live. If <block_hash> is specified, return the live cell only if the corresponding block contain this cell")
////    @RequestMapping(value = "/get_live_cell/{block_hash}/{index}/{tx_hash}", method = RequestMethod.GET, produces = "application/json")
////    public Object get_live_cell(@PathVariable String block_hash, @PathVariable String index, @PathVariable String tx_hash) {
////        Object result = null;
////        Map<String, String> headers = new HashMap<String, String>(1);
////        headers.put("Content-Type", "application/json");
////
////        try {
////            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nervosUrl.getServerUrl()), headers);
////
////            if (!StringUtils.isEmpty(block_hash)) {
////                result = client.invoke("get_live_cell", new Object[]{"block_hash":block_hash, "cell":{
////                    "index":index, "tx_hash":tx_hash
////                }},Object.class);
////            }
////
////        } catch (Throwable throwable) {
////            throwable.printStackTrace();
////        }
////        System.out.println(result);
////        return result;
////    }
//
//    @ApiOperation(value = "Returns the number of blocks in the longest blockchain..")
//    @RequestMapping(value = "/get_tip_block_number/", method = RequestMethod.GET, produces = "application/json")
//    public Object get_tip_block_number() {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_tip_block_number", new Object[]{}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    @ApiOperation(value = "Returns the information about the tip header of the longest..")
//    @RequestMapping(value = "/get_tip_header", method = RequestMethod.GET, produces = "application/json")
//    public Object get_tip_header() {
//        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(nodeUrl), headers);
//            result = client.invoke("get_tip_header", new Object[]{}, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);
//        return result;
//    }

    private static String CKB_TYPE = "M/44H/309H/0H/0/";

    private static String passwordCKB = "12345678";

//    private static String wordsList = "visa immune silly edit typical first demand baby evoke cabbage false cousin kitten poem mass";

    private static final byte[] SEED = null;


    private static TestNet3Params testNetParams = TestNet3Params.get();
//    private static MainNetParams mainnetParams = MainNetParams.get();

    @ApiOperation(value = "获取CKB的测试网地址；私钥；等信息")
    @RequestMapping(value = "/getCKBTestNetAddress/", method = RequestMethod.GET, produces = "application/json")
    public CKBResponse getCKBAddress() {

        CKBResponse response = new CKBResponse();

        try {

            Map<String, String> headers = new HashMap<String, String>(1);
            headers.put("Content-Type", "application/json");

            //http://www.netkiller.cn/blockchain/ethereum/web3j/web3j.mnemonic.html
            //TODO Java生成助记词
            //17.16.2. 助记词导出公钥和私钥
            String passphrase = "";
            SecureRandom secureRandom = new SecureRandom();
            long creationTimeSeconds = System.currentTimeMillis() / 1000;
            DeterministicSeed deterministic = new DeterministicSeed(secureRandom, 128, passphrase);
            List<String> wordsList = deterministic.getMnemonicCode();


            DeterministicSeed deterministicSeed = new DeterministicSeed(wordsList, SEED, passwordCKB, 0L);
//            System.out.println("BIP39 seed: " + deterministicSeed.toHexString());

            /**生成根私钥 root private key*/
            DeterministicKey rootPrivateKey = HDKeyDerivation.createMasterPrivateKey(deterministicSeed.getSeedBytes());

            /**根私钥进行 priB58编码*/
            String priv = rootPrivateKey.serializePrivB58(testNetParams);
//            System.out.println("BIP32 Root Key: " + priv);

            /**由根私钥生成HD钱包*/
            DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(rootPrivateKey);
            /**定义父路径*/
            List<ChildNumber> parsePath = HDUtils.parsePath(CKB_TYPE);

            /**由父路径,派生出子私钥*/

            DeterministicKey childKey0 = deterministicHierarchy.deriveChild(parsePath, true, true, new ChildNumber(0));

//            System.out.println("private Key:  " + childKey0.getPrivateKeyAsWiF(testNetParams));
//            System.out.println("private Hash Key:  " + childKey0.getPrivateKeyAsHex()); //测试用的privateKey
//            System.out.println("privKey:  " + childKey0.getPrivKey());
            response.setPrivateKey("0x" + childKey0.getPrivateKeyAsWiF(testNetParams));
            response.setPrivateHashKey("0x" + childKey0.getPrivateKeyAsHex());
            response.setPrivKey("0x" + childKey0.getPrivKey().toString());

            //TODO 生成私钥之后去CKB的Test环境去生成钱包地址；
            AddressUtils utils = new AddressUtils(Network.TESTNET);
            String privateHashKey = childKey0.getPrivateKeyAsHex();
            String publicKey = Sign.publicKeyFromPrivate(Numeric.toBigInt(privateHashKey), true).toString(16);
//            System.out.println("address :  " + utils.generateFromPublicKey(publicKey)); //测试用的privateKey
            String address = utils.generateFromPublicKey(publicKey);
            response.setAddress(address);
            String blake160 = utils.getBlake160FromAddress(address);
            response.setBlake160(blake160);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return response;
    }

}
