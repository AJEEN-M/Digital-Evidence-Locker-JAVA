package com.del.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.*;
import java.security.Key;

public class FileEncryptor {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = "MySecretKey12345".getBytes(); // 16-byte key

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGO);
    }

    public static void encryptFile(String filePath) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] data = Files.readAllBytes(Paths.get(filePath));
        byte[] encrypted = cipher.doFinal(data);
        Files.write(Paths.get(filePath), encrypted);
    }

    public static void decryptFile(String filePath) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] data = Files.readAllBytes(Paths.get(filePath));
        byte[] decrypted = cipher.doFinal(data);
        Files.write(Paths.get(filePath), decrypted);
    }
}
