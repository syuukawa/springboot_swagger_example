package guru.springframework.eth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class EthWalletUtils {

    //ETH Path 前缀
    private static String ETH_TYPE = "m/44'/60'/0'/0/0";


    //前缀
    private static final String PREFIX = "0x";

    //助记词种子
    private static final byte[] SEED = null;

    //测试环境和正式环境的设定
    private static TestNet3Params mainnetParams = TestNet3Params.get();
//    static  MainNetParams mainnetParams = MainNetParams.get();

    @Resource
    @Qualifier("ethWeb3j")
    private Web3Client web3Client;

    //1 - 根据助记词和密码批量生成以太地址
    public static List<String> batchGenerateEthAddress(String wordsList, String passwordEth, int index, int limit) throws CipherException, UnreadableWalletException {
        List<String> addressList = new ArrayList<>();
        String[] temp = wordsList.split(" ");
        byte[] seedKeyBytes = generateSeedKey(temp, passwordEth);
        for (int i = index; i < index + limit; i++) {
            String path = ETH_TYPE + i;
            ECKeyPair keyPair = generateKeyPair(wordsList, path, seedKeyBytes);
            String address = PREFIX + Keys.getAddress(keyPair);
            System.out.println("address:" + address);

//            ECKey ecKey = ECKey.fromPrivate(keyPair.getPrivateKey().toByteArray());]
//            System.out.println("ETH PrivateKey :" + ecKey.getPrivateKeyAsHex());
//            System.out.println("ETH publicKey :" + ecKey.getPublicKeyAsHex());

            addressList.add(address);
        }
        return addressList;
    }

    //根据index和助记词生成以太私钥
    public static String generateEthPrivateKeyByIndex(String wordsList, String passwordEth, int index) throws CipherException, UnreadableWalletException {

        String[] temp = wordsList.split(" ");
        byte[] mnemonic = generateSeedKey(temp, passwordEth);

        String path = ETH_TYPE + index;
        ECKeyPair keyPair = generateKeyPair(wordsList, path, mnemonic);
//        String address = Keys.getAddress(keyPair);
//        System.out.println("address:" + address);

        ECKey ecKey = ECKey.fromPrivate(keyPair.getPrivateKey().toByteArray());
        System.out.println("ETH PrivateKey :" + PREFIX + ecKey.getPrivateKeyAsHex());
//        System.out.println("ETH publicKey :" + PREFIX + ecKey.getPublicKeyAsHex());

        return PREFIX + ecKey.getPrivateKeyAsHex();
    }


    //1：根据助记词生成种子
    private static byte[] generateSeedKey(String[] mnemonic, String password) throws UnreadableWalletException, CipherException {
        String mnemonicStr = StringUtils.join(mnemonic, " ");
        byte[] seedBytes = MnemonicUtils.generateSeed(mnemonicStr, password);
        System.out.println("seed:" + HexUtils.toHexString(seedBytes));
        return seedBytes;
    }

    //2：根据种子和Path生成生成 ECKeyPair
    private static ECKeyPair generateKeyPair(String wordsList, String coinTypePath, byte[] seedBytes) throws UnreadableWalletException {
        DeterministicSeed deterministicSeed =
                new DeterministicSeed(wordsList, seedBytes, "", 0L);
        Wallet w = Wallet.fromSeed(MainNetParams.get(), deterministicSeed);

        if (verifyPath(coinTypePath)) {
            String[] pathArray = coinTypePath.split("/");

            DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);

            for (int i = 1; i < pathArray.length; i++) {
                ChildNumber childNumber;
                if (pathArray[i].endsWith("'")) {
                    int number = Integer.parseInt(pathArray[i].substring(0,
                            pathArray[i].length() - 1));
                    childNumber = new ChildNumber(number, true);
                } else {
                    int number = Integer.parseInt(pathArray[i]);
                    childNumber = new ChildNumber(number, false);
                }
                dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
            }

			/*String address = dkKey.toAddress(MainNetParams.get()).toBase58();
			System.out.println("address:"+address);*/
            //System.out.println("eth prikey:"+ HexUtils.toHexString(dkKey.getPrivKey().toByteArray()));
            //System.out.println("btc pri:"+ Base58.encode(dkKey.getPrivKey().toByteArray()));
            return ECKeyPair.create(dkKey.getPrivKeyBytes());
        }
        return null;
    }

    //第二种根据Index生成私钥的方式
    public static String getPrivateKeyByIndex(String wordsList, String passwordEth, Integer index) throws UnreadableWalletException {

        String privateKeyReturn = null;

        DeterministicSeed deterministicSeed = new DeterministicSeed(wordsList, SEED, passwordEth, 0L);
        System.out.println("BIP39 seed: " + deterministicSeed.toHexString());

        /**生成根私钥 root private key*/
        DeterministicKey rootPrivateKey = HDKeyDerivation.createMasterPrivateKey(deterministicSeed.getSeedBytes());
        System.out.println("BIP32 rootPrivateKey" + rootPrivateKey);
//        System.out.println("publicKey"  + rootPrivateKey.serializePublic(mainnetParams));

        /**根私钥进行 priB58编码*/
//        String priv = rootPrivateKey.serializePrivB58(mainnetParams);
//        System.out.println("BIP32 Root Key: "+  priv);

        /**由根私钥生成HD钱包*/
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(rootPrivateKey);
        /**定义父路径*/
        List<ChildNumber> parsePath = HDUtils.parsePath("M/44H/60H/0H/0/");

        // 循环生成100个账号
        DeterministicKey accountKey0 = deterministicHierarchy.get(parsePath, true, true);
//        System.out.println("BIP32 Extended Private Key: " + accountKey0.serializePrivB58(mainnetParams));
//        System.out.println("BIP32 Extended Public Key: " + accountKey0.serializePubB58(mainnetParams));


//        System.out.println("子类账号 : ");
        DeterministicKey childKey = null;
        ECKeyPair childEcKeyPair = null;

        //TODO 循环生成10个账号；10的数量可以从配置文件获取
//        for(int index = 1; index <10 ;index ++){
        /**由父路径,派生出子私钥*/
        childKey = HDKeyDerivation.deriveChildKey(accountKey0, index);
//        DeterministicKey childKey0 = deterministicHierarchy.deriveChild(parsePath, true, true, new ChildNumber(0));
//        System.out.println("BIP32 extended 0 private key:{}" + childKey0.serializePrivB58(mainnetParams));
//        System.out.println("BIP32 extended 0 public key:{}"+ childKey0.serializePubB58(mainnetParams));
//            childEcKeyPair = ECKeyPair.create(childKey.getPrivKeyBytes());
//            System.out.println("address:  " + PREFIX + Keys.getAddress(childEcKeyPair));
//            System.out.println("public key:  " + PREFIX + childKey.getPublicKeyAsHex());
//            System.out.println("private key: "+  PREFIX + childKey.getPrivateKeyAsHex());
//            System.out.println("      ");
//            System.out.println("      ");
//        }
        privateKeyReturn = PREFIX + childKey.getPrivateKeyAsHex();

        return privateKeyReturn;
    }


    /**
     * 根据交易地址获取获取Nonce 【Address为to的地址】
     *
     * @param to//转账的钱包地址
     * @param nonce//获取到的交易次数
     * @param gasPrice
     * @param gasLimit
     * @param value           //转账的值
     * @return: https://blog.csdn.net/wypeng2010/article/details/81357381
     */
    public static BigInteger getNonce(String address) {
        Web3Client web3Client = new Web3Client();
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3Client.getWeb3j().
                    ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        log.info("转入地址，address = " + address);
        log.info("转入地址，address nonce= " + nonce);
        return nonce;
    }

    /**
     * 离线签名eth
     *
     * @param to//转账的钱包地址
     * @param nonce//获取到的交易次数
     * @param gasPrice
     * @param gasLimit
     * @param value           //转账的值
     * @return: https://blog.csdn.net/wypeng2010/article/details/81357381
     */
    public static String signedTransactionData(String privateKey, String from, String to, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit) {

//        BigInteger GAS_PRICEFee = BigInteger.valueOf(1_000_000_000L);
        BigInteger gasFee = gasPrice.multiply(gasLimit); // 手续费

        //调用的是kovan测试环境，这里使用的是infura这个客户端
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/e3956092a87a462c9f44cd945d4a8856"));
        BigInteger sendValue = null;
        try {
            sendValue = web3j.ethGetBalance(from, DefaultBlockParameterName.LATEST).send().getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("gasFee ：：：" + gasFee);
        System.out.println("账户余额 ：：  ：" + sendValue);
        sendValue = sendValue.subtract(gasFee);
        System.out.println("账户发送金额   ：" + sendValue);

        Credentials credentials = Credentials.create(privateKey);
        //        Credentials credentials = WalletUtils.loadCredentials(
        //                "123",
        //                "src/main/resources/UTC--2018-03-01T05-53-37.043Z--d1c82c71cc567d63fd53d5b91dcac6156e5b96b3");

        //创建交易，这里是转0.5个以太币
//        BigInteger valueInt = Convert.toWei(value, Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce, gasPrice, gasLimit, to, sendValue);

        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        return hexValue;
    }


    //验证Path的合法性
    private static boolean verifyPath(String path) {
        if (!path.startsWith("m") && !path.startsWith("M")) {
            //参数非法
            return false;
        }
        String[] pathArray = path.split("/");
        if (pathArray.length <= 1) {
            //内容不对
            return false;
        }
        return true;
    }


    //测试 main 函数
    public static void main(String args[]) {
        Web3Client web3Client = new Web3Client();

        //加密密码
        String PASSWORD_ETH = "123456";

        //助记词
        String wordsList = "visa immune silly edit typical first demand baby evoke cabbage false cousin kitten poem mass";

        //1 - 批量生成以太地址
//            batchGenerateEthAddress(wordsList, PASSWORD_ETH, 0, 10);

        //2 - 生成私钥
//            generateEthPrivateKeyByIndex(wordsList, PASSWORD_ETH, 0);

        //3 - 签名交易测试
        //TODO
        String txHash = null;

        //设置需要的矿工费
        BigInteger GAS_PRICE = BigInteger.valueOf(7_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(30_000);
        String addressFrom = "0x09A1f94690Df952C9a35836d944653af5223D32D";
        String signPrivateKey = "0x0512197046b7126bdfeb591f397cf08bb10b1933d921a2aed121ef792bf5c77e";
        String addressTo = "0x33Dd8BEa61484E04159139fcFC8822267A07FCB1";
        BigInteger nonce = getNonce(addressFrom);

        String hexValue = signedTransactionData(signPrivateKey, addressFrom, addressTo, nonce, GAS_PRICE, GAS_LIMIT);
        //发送交易
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = web3Client.getWeb3j().ethSendRawTransaction(hexValue).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txHash = ethSendTransaction.getTransactionHash();

        if (txHash == null) {
            log.info("EthSendTransaction error = " + ethSendTransaction.getError().getMessage());
            return;
        }
        System.out.println("txHash----------" + txHash);
        System.out.println("End------------------------------");

    }
}
