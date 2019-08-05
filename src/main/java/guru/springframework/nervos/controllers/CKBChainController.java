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
@RequestMapping("/ckb")
@Api(value = "/ckb", description = "CKB TestNet Address ")
public class CKBChainController {

    private static String CKB_TYPE = "M/44H/309H/0H/0/";

    private static String passwordCKB = "12345678";

//    private static String wordsList = "visa immune silly edit typical first demand baby evoke cabbage false cousin kitten poem mass";

    private static final byte[] SEED = null;


    private static TestNet3Params testNetParams = TestNet3Params.get();
//    private static MainNetParams mainnetParams = MainNetParams.get();

    @ApiOperation(value = "CKB的测试网地址，私钥生成")
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
//            response.setPrivateKey("0x" + childKey0.getPrivateKeyAsWiF(testNetParams));
            response.setPrivateKey("0x" + childKey0.getPrivateKeyAsHex());
//            response.setPrivKey("0x" + childKey0.getPrivKey().toString());

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
