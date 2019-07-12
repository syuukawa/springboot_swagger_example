//package guru.springframework.utils;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.tomcat.util.buf.HexUtils;
//import org.bitcoinj.crypto.*;
//import org.bitcoinj.params.MainNetParams;
//import org.bitcoinj.params.RegTestParams;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.wallet.DeterministicSeed;
//import org.bitcoinj.wallet.UnreadableWalletException;
//import org.bitcoinj.wallet.Wallet;
//import org.web3j.crypto.CipherException;
//import org.web3j.crypto.ECKeyPair;
//import org.web3j.crypto.Keys;
//import org.web3j.crypto.MnemonicUtils;
//
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class HdWalletUtils {
//
//    private static String ETH_TYPE = "m/44'/60'/0'/0/0";
//
//    private static String BTC_TYPE = "m/44'/0'/0'/0/0";
//
//    private static String EOS_TYPE = "m/44'/194'/0'/0/0";
//
//    private static String passwordBtc = "12345679";
//
//    private static String passwordEth = "123456";
//
//    private static String passwordEos = "123456";
//
//    private static String wordsList = "visa immune silly edit typical first demand baby evoke cabbage false cousin kitten poem mass";
//    private static final String PREFIX = "0x";
//    private static final byte[] SEED = null;
//
//    private static TestNet3Params mainnetParams = TestNet3Params.get();
////    static  MainNetParams mainnetParams = MainNetParams.get();
//
//    public static void testBtc() throws CipherException, UnreadableWalletException {
//        List<String> list = batchGenerateBtcAddress(0, 100);
//        for (String add : list) {
//            System.out.println(add);
//        }
//    }
//
//    public static void testRegTestBtc() throws CipherException, UnreadableWalletException {
//        List<String> list = batchGenerateTestBtcAddress(0, 100);
//        for (String add : list) {
//            System.out.println(add);
//        }
//    }
//
//
//    public static void testEth() throws CipherException, UnreadableWalletException {
//        List<String> list = batchGenerateEthAddress(0, 100);
//        for (String add : list) {
//            System.out.println(add);
//        }
//    }
//
//    public static void main(String args[]) {
//        try {
//
////			testBtc();
//            //testRegTestBtc();
//            //testEos();
//            batchGenerateEthAddress(0, 10);
////            getPrivateKeyByIndex(1);
//            System.out.println("------------------------------");
//
//            //testEth();
//        } catch (CipherException e) {
//            e.printStackTrace();
//        } catch (UnreadableWalletException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String generateMainEthAddress() throws CipherException, UnreadableWalletException {
//
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordEth);
//        ECKeyPair keyPair = generateKeyPair(ETH_TYPE, mnemonic);
//        String address = Keys.getAddress(keyPair);
//        return address;
//    }
//
//    public static List<String> batchGenerateEthAddress(int index, int limit) throws CipherException, UnreadableWalletException {
//        List<String> addressList = new ArrayList<>();
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordEth);
//        for (int i = index; i < index + limit; i++) {
//            String path = "m/44'/60'/0'/0/" + i;
//            ECKeyPair keyPair = generateKeyPair(path, mnemonic);
//            String address = Keys.getAddress(keyPair);
//            System.out.println("address:" + address);
//            addressList.add(address);
//        }
//        return addressList;
//    }
//
//    public static String generateBtcAddress() throws CipherException, UnreadableWalletException {
//
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordBtc);
//
//        ECKeyPair keyPair = generateKeyPair(BTC_TYPE, mnemonic);
//
//        String address = DeterministicKey.
//                fromPrivate(keyPair.getPrivateKey().toByteArray()).toAddress(MainNetParams.get()).toBase58();
//
//        System.out.println("address:" + address);
//        return address;
//    }
//
//    public static List<String> batchGenerateBtcAddress(int index, int limit) throws CipherException, UnreadableWalletException {
//        List<String> addressList = new ArrayList<>();
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordBtc);
//        for (int i = index; i < index + limit; i++) {
//            String path = "m/44'/0'/0'/0/" + i;
//            ECKeyPair keyPair = generateKeyPair(path, mnemonic);
//
//            /*	RegTestParams MainNetParams*/
//            String address = DeterministicKey.
//                    fromPrivate(keyPair.getPrivateKey().toByteArray()).toAddress(MainNetParams.get()).toBase58();
//            System.out.println("bitcoin:" + address);
//            addressList.add(address);
//        }
//
//        return addressList;
//    }
//
//
//    public static List<String> batchGenerateTestBtcAddress(int index, int limit) throws CipherException, UnreadableWalletException {
//        List<String> addressList = new ArrayList<>();
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordBtc);
//        for (int i = index; i < index + limit; i++) {
//            String path = "m/44'/1'/0'/0/" + i;
//            ECKeyPair keyPair = generateKeyPair(path, mnemonic);
//
//            /*	RegTestParams MainNetParams*/
//            String address = DeterministicKey.
//                    fromPrivate(keyPair.getPrivateKey().toByteArray()).toAddress(RegTestParams.get()).toBase58();
//            System.out.println("bitcoin:" + address);
//            addressList.add(address);
//        }
//
//        return addressList;
//    }
//
//    /**
//     * 生成单条eos地址
//     *
//     * @return
//     * @throws CipherException
//     * @throws UnreadableWalletException
//     */
//    public static String generateEosAddress() throws CipherException, UnreadableWalletException {
//
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordEos);
//
//        ECKeyPair keyPair = generateKeyPair(EOS_TYPE, mnemonic);
//
//        byte[] compressed = Secp256k1Util.toCompressedPublicKey(keyPair.getPrivateKey().toByteArray());
//        byte[] checksum = HashUtil.ripeMd160AsBytes(compressed);
//        byte[] address = ByteUtil.concat(compressed, Arrays.copyOfRange(checksum, 0, 4));
//        return "EOS" + Base58Util.encode(address);
//    }
//
//    /**
//     * 批量生成eos地址
//     *
//     * @param index
//     * @param limit
//     * @return
//     * @throws CipherException
//     * @throws UnreadableWalletException
//     */
//    public static List<String> batchGenerateEosAddress(int index, int limit) throws CipherException, UnreadableWalletException {
//        List<String> addressList = new ArrayList<>();
//        String[] temp = wordsList.split(" ");
//        byte[] mnemonic = generateSeedKey(temp, passwordEos);
//        for (int i = index; i < index + limit; i++) {
//            String path = "m/44'/194'/0'/0/" + i;
//            ECKeyPair keyPair = generateKeyPair(path, mnemonic);
//
//            byte[] compressed = Secp256k1Util.toCompressedPublicKey(keyPair.getPrivateKey().toByteArray());
//            byte[] checksum = HashUtil.ripeMd160AsBytes(compressed);
//            byte[] address = ByteUtil.concat(compressed, Arrays.copyOfRange(checksum, 0, 4));
//            addressList.add("EOS" + Base58Util.encode(address));
//        }
//
//        return addressList;
//    }
//
//    public static void testEos() throws CipherException, UnreadableWalletException {
//        List<String> list = batchGenerateEosAddress(0, 100);
//        for (String add : list) {
//            System.out.println(add);
//        }
//    }
//
//
//    private static byte[] generateSeedKey(String[] mnemonic, String password) throws UnreadableWalletException, CipherException {
//        String mnemonicStr = StringUtils.join(mnemonic, " ");
//        byte[] seedBytes = MnemonicUtils.generateSeed(mnemonicStr, password);
//        System.out.println("seed:" + HexUtils.toHexString(seedBytes));
//        return seedBytes;
//    }
//
//    private static ECKeyPair generateKeyPair(String coinTypePath, byte[] seedBytes) throws UnreadableWalletException {
//        DeterministicSeed deterministicSeed =
//                new DeterministicSeed(wordsList, seedBytes, "", 0L);
//        Wallet w = Wallet.fromSeed(MainNetParams.get(), deterministicSeed);
//
//        if (verifyPath(coinTypePath)) {
//            String[] pathArray = coinTypePath.split("/");
//
//            DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
//
//            for (int i = 1; i < pathArray.length; i++) {
//                ChildNumber childNumber;
//                if (pathArray[i].endsWith("'")) {
//                    int number = Integer.parseInt(pathArray[i].substring(0,
//                            pathArray[i].length() - 1));
//                    childNumber = new ChildNumber(number, true);
//                } else {
//                    int number = Integer.parseInt(pathArray[i]);
//                    childNumber = new ChildNumber(number, false);
//                }
//                dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
//            }
//
//			/*String address = dkKey.toAddress(MainNetParams.get()).toBase58();
//			System.out.println("address:"+address);*/
//            //System.out.println("eth prikey:"+ HexUtils.toHexString(dkKey.getPrivKey().toByteArray()));
//            //System.out.println("btc pri:"+ Base58.encode(dkKey.getPrivKey().toByteArray()));
//            return ECKeyPair.create(dkKey.getPrivKeyBytes());
//
//        }
//
//        return null;
//    }
//
//    private static boolean verifyPath(String path) {
//        if (!path.startsWith("m") && !path.startsWith("M")) {
//            //参数非法
//            return false;
//        }
//        String[] pathArray = path.split("/");
//        if (pathArray.length <= 1) {
//            //内容不对
//            return false;
//        }
//        return true;
//    }
//
//
//
//    public static String getPrivateKeyByIndex(Integer index) throws UnreadableWalletException {
//
//        String privateKeyReturn  = null;
//
//        DeterministicSeed deterministicSeed = new DeterministicSeed(wordsList, SEED, passwordEth, 0L);
//        System.out.println("BIP39 seed: "+ deterministicSeed.toHexString());
//
//        /**生成根私钥 root private key*/
//        DeterministicKey rootPrivateKey = HDKeyDerivation.createMasterPrivateKey(deterministicSeed.getSeedBytes());
//        System.out.println("BIP32 rootPrivateKey"+  rootPrivateKey);
////        System.out.println("publicKey"  + rootPrivateKey.serializePublic(mainnetParams));
//
//        /**根私钥进行 priB58编码*/
////        String priv = rootPrivateKey.serializePrivB58(mainnetParams);
////        System.out.println("BIP32 Root Key: "+  priv);
//
//        /**由根私钥生成HD钱包*/
//        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(rootPrivateKey);
//        /**定义父路径*/
//        List<ChildNumber> parsePath = HDUtils.parsePath("M/44H/60H/0H/0/");
//
//        // 循环生成100个账号
//        DeterministicKey accountKey0 = deterministicHierarchy.get(parsePath, true, true);
////        System.out.println("BIP32 Extended Private Key: " + accountKey0.serializePrivB58(mainnetParams));
////        System.out.println("BIP32 Extended Public Key: " + accountKey0.serializePubB58(mainnetParams));
//
//
////        System.out.println("子类账号 : ");
//        DeterministicKey childKey = null;
//        ECKeyPair childEcKeyPair = null;
//
//        //TODO 循环生成10个账号；10的数量可以从配置文件获取
////        for(int index = 1; index <10 ;index ++){
//            /**由父路径,派生出子私钥*/
//            childKey = HDKeyDerivation.deriveChildKey(accountKey0, index);
////        DeterministicKey childKey0 = deterministicHierarchy.deriveChild(parsePath, true, true, new ChildNumber(0));
////        System.out.println("BIP32 extended 0 private key:{}" + childKey0.serializePrivB58(mainnetParams));
////        System.out.println("BIP32 extended 0 public key:{}"+ childKey0.serializePubB58(mainnetParams));
////            childEcKeyPair = ECKeyPair.create(childKey.getPrivKeyBytes());
////            System.out.println("address:  " + PREFIX + Keys.getAddress(childEcKeyPair));
////            System.out.println("public key:  " + PREFIX + childKey.getPublicKeyAsHex());
////            System.out.println("private key: "+  PREFIX + childKey.getPrivateKeyAsHex());
////            System.out.println("      ");
////            System.out.println("      ");
////        }
//        privateKeyReturn = PREFIX + childKey.getPrivateKeyAsHex();
//
//        return  privateKeyReturn;
//    }
//}
