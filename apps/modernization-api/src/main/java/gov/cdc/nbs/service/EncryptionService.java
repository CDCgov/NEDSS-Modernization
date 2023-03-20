package gov.cdc.nbs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.exception.EncryptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptionService {
    @Value("${nbs.security.parameterSecret}")
    private String secret;

    @Autowired
    private ObjectMapper mapper;

    private final SecureRandom random = new SecureRandom();
    public static final int SALT_LENGTH = 16;

    public String handleEncryption(Object object) {
        try {
            // generate random salt
            var salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // serialize object
            var serialized = mapper.writeValueAsBytes(object);

            // initialize cipher with salt
            var cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // parameter spec for GCM
            var gcmParameterSpec = new GCMParameterSpec(SALT_LENGTH * 8, salt);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(secret.getBytes(), "AES"),
                    gcmParameterSpec);

            // encrypt object
            byte[] encrypted = cipher.doFinal(serialized);

            // concatenate salt + encrypted bytes
            var saltAndEncryptedBytes = ByteBuffer.allocate(encrypted.length + salt.length)
                    .put(salt)
                    .put(encrypted)
                    .array();

            // base64 encode
            return Base64.getEncoder().encodeToString(saltAndEncryptedBytes);
        } catch (Exception e) {
            throw new EncryptionException("Failed to perform encryption");
        }
    }

    public Object handleDecryption(String encryptedString) {
        try {
            // decode Base64 to bytes
            byte[] decoded = Base64.getDecoder().decode(encryptedString);

            // extract salt from first 16 bytes
            var salt = Arrays.copyOfRange(decoded, 0, SALT_LENGTH);

            // extract content from remaining bytes
            var decodedContent = Arrays.copyOfRange(decoded, SALT_LENGTH, decoded.length);

            // initialize cipher with salt
            var cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // parameter spec for GCM
            var gcmParameterSpec = new GCMParameterSpec(SALT_LENGTH * 8, salt);

            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(secret.getBytes(), "AES"),
                    gcmParameterSpec);

            // decrypt content
            var serialized = cipher.doFinal(decodedContent);

            // deserialize object
            return mapper.readValue(serialized, Object.class);
        } catch (Exception e) {
            throw new EncryptionException("Failed to decrypt provied string.");
        }
    }
}
