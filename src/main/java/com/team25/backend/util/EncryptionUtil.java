package com.team25.backend.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class EncryptionUtil {

    // 카드 정보 암호화
    public static String encryptCardData(String plainData, String secretKey) throws Exception {
        String key = secretKey.substring(0, 16); // AES128 사용
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(plainData.getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(encrypted));
        return bytesToHex(encrypted);
    }

    // SignData 생성
    public static String generateSignData(String... params) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param);
        }
        return sha256Hex(sb.toString());
    }

    private static String sha256Hex(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
