package guru.springframework.nervos;


import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CkbJsonRpc {


    //测试 main 函数
    public static void main(String args[]) {

        Object result = null;
//        Map<String, String> headers = new HashMap<String, String>(1);
//        headers.put("Content-Type", "application/json");
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://localhost:8114"), headers);
//            result = client.invoke("get_block_by_number", new Object[] {"1024" }, Object.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println(result);

        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("Content-Type", "application/json");
        try {
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://localhost:8114"), headers);
            result = client.invoke("get_block", new Object[] {"0x8cd9308ff9e262e8972d11e0825f2b332900f56eaf8757d087642f12d6a59979" }, Object.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(result);
    }
}
