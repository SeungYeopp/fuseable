package preCapstone.fuseable.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AESEncryptor {

    public static String alg = "AES/CBC/PKCS5Padding";
    private final String key = "01234567890123456789012345678901";




    public String encrypt(String plainText) throws Exception {
        return Base64.getEncoder().encodeToString(this.encrypt(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String encText) throws Exception {
        return new String(this.decrypt(Base64.getDecoder().decode(encText)), StandardCharsets.UTF_8);
    }

    private byte[] encrypt(byte[] plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText);
    }

    private byte[] decrypt(byte[] cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(),alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(cipherText);
    }


}
