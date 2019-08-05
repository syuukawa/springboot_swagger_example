package guru.springframework.nervos.controllers;

import lombok.Data;

@Data
public class CKBResponse {

    private String address;

    private String privateKey;

//    private String privateHashKey;

//    private String privKey;

    private String Blake160;


}
