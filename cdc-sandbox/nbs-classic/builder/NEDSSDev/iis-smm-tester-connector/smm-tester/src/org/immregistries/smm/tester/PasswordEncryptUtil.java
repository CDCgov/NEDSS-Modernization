package org.immregistries.smm.tester;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordEncryptUtil {
  private static final byte[] KEY = {(byte) 0x1A, (byte) 0x1F, (byte) 0x12, (byte) 0x1B,
      (byte) 0x1C, (byte) 0x14, (byte) 0x14, (byte) 0x19, (byte) 0x19, (byte) 0x1E, (byte) 0x1F,
      (byte) 0x10, (byte) 0x16, (byte) 0x1E, (byte) 0x1D, (byte) 0x13, (byte) 0x1D, (byte) 0x1C,
      (byte) 0x1B, (byte) 0x1A, (byte) 0x19, (byte) 0x18, (byte) 0x17, (byte) 0x17,};
  private static final byte[] IV = {(byte) 0x0, (byte) 0x1A, (byte) 0x20, (byte) 0x1, (byte) 0x7,
      (byte) 0x9, (byte) 0x10, (byte) 0x1E};

  public static final String PREPEND = "PASSWORD_HIDDEN_BY_SMM|";

  public static String encrypt(String plainText) throws Exception {
    while (plainText.length() % 8 != 0) {
      plainText += ' ';
    }
    SecretKeySpec key = new SecretKeySpec(KEY, "DESede");
    IvParameterSpec ivSpec = new IvParameterSpec(IV);
    Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
    byte[] er = cipher.doFinal(plainText.getBytes());
    byte[] er2 = Base64.encodeBase64(er);
    String encryptedResult = new String(er2);

    return PREPEND + encryptedResult;
  }

  public static String decrypt(String encryptedText) throws Exception {
    if (encryptedText.startsWith(PREPEND)) {
      encryptedText = encryptedText.substring(PREPEND.length());
      byte[] et = Base64.decodeBase64(encryptedText.getBytes());

      SecretKeySpec key = new SecretKeySpec(KEY, "DESede");
      IvParameterSpec ivSpec = new IvParameterSpec(IV);
      Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
      cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
      String plainText = new String(cipher.doFinal(et));
      return plainText.trim();
    } else {
      return encryptedText;
    }
  }

}
