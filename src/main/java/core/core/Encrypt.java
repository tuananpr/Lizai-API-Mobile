package core.core;

import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;

public class Encrypt {

    public static String pubKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAscz/Z4VncYVOl2u/K/iC\n" +
            "w09L/9cHFYn9SwbBvDACKJMyhD/k+m33MpLpw3CcRr0LjXr2Nllw2h/+a9qJUbK/\n" +
            "/3usWaU4ITNVIDyWU/Y6+V9eTK7nPkRcGrjUK5iCil7WsBH+xb32n3rvkK/QM2qV\n" +
            "/Dp56nvu5gp90wB180RX2S4bfZaBNTOxRE/yxtbyxa7PZoOQF9jkcQtHZCqhIcWG\n" +
            "k58mV5xONwCV2gKlEKtwanlCq//MwNhZ9ztxkLWAobivje+jLtblIUqeRUirxm2f\n" +
            "aFub47zG1KD+/KelRhP5mS1WQVq5sNcYzkfoSlyJP+wqPuOOO6rV1A0s1739dpfv\n" +
            "TwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    private static String randomPass(int n) {


        return null;

    }

    public static PublicKey getPublicKeyFromStr(String primaryKeyStr) {
        // Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(primaryKeyStr));
        String line;


        try {
            while ((line = rdr.readLine()) != null) {
                pkcs8Lines.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PUBLIC KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PUBLIC KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

        // Base64 decode the result


        byte[] pkcs8EncodedBytes = org.bouncycastle.util.encoders.Base64.decode(pkcs8Pem);

        // extract the private key

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String encrypt(byte[] data, Key key, String type) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(type);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static byte[] decrypt(byte[] data, Key key, String type) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static Map<String, Object> encryptRequest(String requestBody) {

        byte[] pass = "chuoi24kytubatky-autoapi".getBytes();

        DESedeKeySpec dks = null;
        Map<String, Object> body = null;

        try {
            dks = new DESedeKeySpec(pass);
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            //SecretKey secretKey = new SecretKeySpec(pass, 0, pass.length, "DES");
            PublicKey publicKey = getPublicKeyFromStr(pubKey);

            // Encrypt
            String d = encrypt(requestBody.getBytes(), secretKey, secretKey.getAlgorithm());
            String k = encrypt(pass, publicKey, "RSA/ECB/OAEPPadding");

            // Request
            body = new HashMap<>();
            body.put("d", d);
            body.put("k", k);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static Response getDecryptRS(Response responseEncrypt){
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.clone(responseEncrypt);
        responseBuilder.setBody(Base64.getDecoder().decode(responseEncrypt.path("d").toString()));
        return responseBuilder.build();
    }

}
