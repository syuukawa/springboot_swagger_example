package guru.springframework.eth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Slf4j
@Service("ethWeb3j")
public class Web3Client {

	private Web3j web3j;

	public Web3j getWeb3j(){
		if(web3j==null){
			web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/e3956092a87a462c9f44cd945d4a8856"));
		}
		return web3j;
	}
}
