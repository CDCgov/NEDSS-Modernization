/*
 * Created on Apr 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import gov.cdc.nedss.systemservice.nbssecurity.encryption.Encryptor;
import gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish.*;
import gov.cdc.nedss.util.LogUtils;

import java.security.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.crypto.*;
import javax.crypto.spec.*;
//import sun.misc.*;
import org.apache.commons.codec.binary.Base64;



class BlowFishEncryptor implements Encryptor {

	private BlowFishEncryptorSetting setting = null;
	private static final String DEFAULT_KEY = "password";
	static final LogUtils logger = new LogUtils(BlowFishEncryptor.class.getName());

	BlowFishEncryptor(BlowFishEncryptorSetting setting) {
		this.setting = setting;
	}

	public String encryptF(String plainText) {
		if (setting == null)
			return encryptF(plainText, DEFAULT_KEY);
		else
			return encryptF(plainText, setting.getKey());
	}
	public String decryptF(String encryptedText) {
		if (setting == null)
			return decryptF(encryptedText, DEFAULT_KEY);
		else
			return decryptF(encryptedText, setting.getKey());
	}

	static String encryptF(String plainText, String key) {

		try {
//			System.out.println("plain text is : " + plainText);
			//	  pass the key string as the key value for the encryption
			//logger.info("in encryptF String: " + plainText);
			SecretKeySpec keyspec =
				new SecretKeySpec(key.getBytes("UTF8"), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			logger.info("cipher Initialized in encryptF");
			byte[] plaintext = plainText.getBytes("UTF8");
				//	  byte[] plaintext = plainText.getBytes();
			byte[] encrypted = cipher.doFinal(plaintext);
//			System.out.println("in encrypt" + encrypted.toString());
			//	  encode the encrypted string using base64
			//String encodedStr = new BASE64Encoder().encode(encrypted);//using Commons Codec library for Base64 Encoder.
			byte[] encodedBytes = Base64.encodeBase64(encrypted);
			logger.info("encrypted using base64");
			String encodedStr = new String(encodedBytes);

			//	  encodedStr = URLEncoder.encode(encodedStr);
			//System.out.println("encrypted using base64 " + encodedStr);
			return encodedStr;
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Encryption Error");
			return "Encryption Error" + e;
		}
	}
	static String decryptF(String encryptedText, String key) {

		try {
//			System.out.println("Encrypted text is : " + encryptedText);
			//	  pass the key string as the key value for the encryption
			SecretKeySpec keyspec =
				new SecretKeySpec(key.getBytes("UTF8"), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			logger.info("cipher Initialized in decryptF");
			//byte[] encodedArray =
				//   new BASE64Decoder().decodeBuffer(encryptedText);
			      //   new Base64.decodeBase64(encryptedText.getBytes());
			byte[] encodedArray = Base64.decodeBase64(encryptedText);
			logger.info("decrypted using base64");
			byte[] plaintext = cipher.doFinal(encodedArray);
			String plainStr = new String(plaintext);
			logger.info("in decrypt: " + plainStr);
			//System.out.println("in decrypt: " + new String(plaintext));
			return plainStr;
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Decryption Error");
			return "Decryption Error" + e;
		}
	}

	public static void main(String[] args) {
		BlowFishEncryptor testBlowfish = new BlowFishEncryptor(null);
		String encodedStr = testBlowfish.encryptF("xzheng");
		String tmpString = null;

		try {
			tmpString = URLDecoder.decode("kDutj6CNeFw=", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String plainStr = testBlowfish.decryptF(tmpString);
	}
}