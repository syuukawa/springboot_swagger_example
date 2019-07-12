package guru.springframework.utils;


import org.apache.tomcat.util.buf.HexUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransUtils {

/*    public static String doubleNumberToDex(double value){
        long decimal = 1000000000000000000L;

        long a = (long) (value*decimal);
        String temp =  Long.toHexString(a);

        return "0x"+temp;
    }*/

    public static BigInteger stringValueToEthBigInt(String value){
        long decimal = 1000000000000000000L;
        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal newValue = bigDecimal.multiply(BigDecimal.valueOf(decimal));
        return newValue.toBigInteger();
    }

    public static String doubleNumberToDex(BigDecimal value){
        long decimal = 1000000000000000000L;
        BigDecimal newValue = value.multiply(BigDecimal.valueOf(decimal));
        BigInteger ammount = newValue.toBigInteger();

        String temp =  HexUtils.toHexString(ammount.toByteArray());
        return "0x"+temp;
    }

    private static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }


    public static void main(String args[]){

        //0x098bca5a00
        System.out.println(doubleNumberToDex(new BigDecimal("0.000000041")));

        //0x5208
        System.out.println(doubleNumberToDex(new BigDecimal("0.000000041")));
        //0x016345785d8a0000
        System.out.println(doubleNumberToDex(  new BigDecimal("0.000000041")));


        BigInteger a = new BigInteger("0x000000098bca5a00".substring(2), 16);

        BigInteger b = new BigInteger("0x098bca5a00".substring(2), 16);

        System.out.println(a.equals(b));


        System.out.println(doubleNumberToDex( new BigDecimal("0")));
    }
}
