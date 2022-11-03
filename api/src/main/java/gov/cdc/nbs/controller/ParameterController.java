package gov.cdc.nbs.controller;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.cdc.nbs.exception.EncryptionException;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import io.swagger.annotations.ApiImplicitParam;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Value("${nbs.security.parameterSecret}")
    private String secret;

    @Autowired
    private ObjectMapper mapper;

    private final SecureRandom random = new SecureRandom();

    @PostMapping("/encrypt")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public String encrypt(@RequestBody PatientFilter object) {
        try {
            // generate random salt
            var salt = new byte[16];
            random.nextBytes(salt);

            // serialize object
            var serialized = mapper.writeValueAsBytes(object);

            // initialize cipher with salt
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(secret.getBytes(), "AES"),
                    new IvParameterSpec(salt));

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

    @PostMapping("/decrypt")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public PatientFilter decrypt(@RequestBody String encryptedString) {
        // decode / decrypt / deserialize
        try {
            // decode Base64 to bytes
            byte[] decoded = Base64.getDecoder().decode(encryptedString);

            // extract salt from first 16 bytes
            var salt = Arrays.copyOfRange(decoded, 0, 16);

            // extract content from remaining bytes
            var decodedContent = Arrays.copyOfRange(decoded, 16, decoded.length);

            // initialize cipher with salt
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(secret.getBytes(), "AES"),
                    new IvParameterSpec(salt));

            // decrypt content
            var serialized = cipher.doFinal(decodedContent);

            // deserialize object
            var object = mapper.readValue(serialized, PatientFilter.class);
            return object;
        } catch (Exception e) {
            throw new EncryptionException("Failed to decrypt provied string.");
        }
    }
}
