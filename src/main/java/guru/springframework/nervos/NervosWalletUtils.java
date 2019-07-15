package guru.springframework.nervos;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.crypto.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class NervosWalletUtils {

    //ETH Path 前缀
    private static String NEROVS_TYPE = "m/309'/0'/0'/0/";

    //前缀
    private static final String PREFIX = "0x";

    //助记词种子
    private static final byte[] SEED = null;

    //测试环境和正式环境的设定
    private static TestNet3Params mainnetParams = TestNet3Params.get();
//    static  MainNetParams mainnetParams = MainNetParams.get();

    //根据私钥获取Nervos的地址
    public static String getAddressFromPrivateKey(String privateKey) {

        ECKey key;
        BigInteger privKey = Base58.decodeToBigInteger(privateKey);
        key = ECKey.fromPrivate(privKey);
        System.out.println("Address from private key is: " + LegacyAddress.fromKey(mainnetParams, key).toString());
        return null;
    }


    //    public static String generateBtcAddress(String wordsList, String passwordBtc, int index) throws CipherException, UnreadableWalletException {
//
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordBtc);
//        String path = NEROVS_TYPE + index;
//        ECKeyPair keyPair = generateKeyPair(wordsList, path, mnemonic);
//
//        /*  RegTestParams MainNetParams*/
//        String address = DeterministicKey.
//                fromPrivate(keyPair.getPrivateKey().toByteArray()).toAddress(mainnetParams).toBase58();
//
//        ECKey ecKey = ECKey.fromPrivate(keyPair.getPrivateKey().toByteArray());
//        System.out.println("bitcoin PrivateKey :" + ecKey.getPrivateKeyAsWiF(MainNetParams.get()));
//        System.out.println("bitcoin publicKey :" + ecKey.getPublicKeyAsHex());
//        System.out.println("bitcoin:" + address);
//
//        return address;
//    }
//
//
//    public static List<String> batchGenerateBtcAddress(String wordsList, String passwordBtc, int index, int limit) throws CipherException, UnreadableWalletException {
//        List<String> addressList = new ArrayList<>();
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordBtc);
//        for (int i = index; i < index + limit; i++) {
//            String path = NEROVS_TYPE + i;
//            ECKeyPair keyPair = generateKeyPair(wordsList, path, mnemonic);
//
//            /*  RegTestParams MainNetParams*/
//            String address = DeterministicKey.
//                    fromPrivate(keyPair.getPrivateKey().toByteArray()).toAddress(mainnetParams).toBase58();
//
//            ECKey ecKey = ECKey.fromPrivate(keyPair.getPrivateKey().toByteArray());
//            System.out.println("bitcoin PrivateKey :" + ecKey.getPrivateKeyAsWiF(MainNetParams.get()));
//            System.out.println("bitcoin publicKey :" + ecKey.getPublicKeyAsHex());
//            System.out.println("bitcoin:" + address);
//            addressList.add(address);
//        }
//
//        return addressList;
//    }
//
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
//        Web3Client web3Client = new Web3Client();

        //加密密码
        String PASSWORD_ETH = "123456789";

        //助记词
        String wordsList = "visa immune silly edit typical first demand baby evoke cabbage false cousin kitten poem mass";

        getAddressFromPrivateKey("dc240692d9d2a66bc52c2373835feba68f11b5e934f8de7d2d8919df1b1cd385\n" +
                "118401af440845783dc12e28834179a58855c9ed42c09495f480bdac666ca51c");

    }
}
