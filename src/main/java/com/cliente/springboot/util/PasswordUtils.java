package com.cliente.springboot.util;

import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtils {
    private static final String HMAC_SHA512 = "HmacSHA512";

    public static byte[] generateSalt() {
        byte[] salt = new byte[64];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static byte[] createPasswordHash(String password, byte[] salt) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt, HMAC_SHA512);
            Mac mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);
            return mac.doFinal(password.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
}
