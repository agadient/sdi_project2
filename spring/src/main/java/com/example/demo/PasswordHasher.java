package com.example.demo;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class PasswordHasher {
    static Integer SALT_LEN = 32;
    static String CHARACTERS = "ABCDEF0123456789";
    static SecureRandom rand = new SecureRandom();

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public PasswordHashAndSalt hashPassword(String password) {

        PasswordHashAndSalt newPasswordHashAndSalt = new PasswordHashAndSalt();
        char[] text = new char[SALT_LEN];
        for (int i = 0; i < SALT_LEN; i++) {
            text[i] = CHARACTERS.charAt(rand.nextInt(CHARACTERS.length()));
        }
        newPasswordHashAndSalt.setSalt(new String(text));
        String toHash = password+newPasswordHashAndSalt.getSalt();
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            return new PasswordHashAndSalt();
        }
        byte[] hash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
        newPasswordHashAndSalt.setHashedPassword(new String(bytesToHex(hash)));
        return newPasswordHashAndSalt;
    }

    public Boolean checkPassword(String hash, String salt, String password) {
        String toHash = password+salt;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            return false;
        }
        byte[] tryHashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
        String tryHash = new String(bytesToHex(tryHashBytes));
        return hash.equals(tryHash);
    }

    public String generateCookieToken() {
        char[] text = new char[SALT_LEN];
        for (int i = 0; i < SALT_LEN; i++) {
            text[i] = CHARACTERS.charAt(rand.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }



    public class PasswordHashAndSalt {
        private String salt;
        private String hashedPassword;

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }

        public void setHashedPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
        }
    }

}
