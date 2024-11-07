package com.team25.backend.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class EncryptionUtil {

    @Value("${billing.secretKey}")
    private String secretKey;

    // SignData 생성
    public String generateSignData(String... params) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param);
        }
        return sha256Hex(sb.toString());
    }

    private String sha256Hex(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String encrypt(String plainText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);

        return encryptedBase64;
    }

    public String decrypt(String cipherText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);

        String decryptedString = new String(decrypted, "UTF-8");
        return decryptedString;
    }
}
