package gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish;


import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.PropertyUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * Utility class to encrypt and decrypt the data. 
 * @author Pradeep Kumar Sharma
 *
 */
public class EncryptorDecryptor {
	private static PropertyUtil propertyUtil =PropertyUtil.getInstance();
	private static EncryptorDecryptor instance = null;
	private static String encryptionKey = null;
	private static String updatedEncryptionKey = null;
	private static boolean encryptionUpdated= false;
	static{
		encryptionKey=propertyUtil.getSecureEncryptionKey();
		updatedEncryptionKey=propertyUtil.getSecureNewEncryptionKey();
	}
	
	public EncryptorDecryptor getInstance(){
		if(instance==null){
			instance = new EncryptorDecryptor();
		}
		synchronized(instance){
			return instance;
		}
	}
	
	private EncryptorDecryptor(){
	}
	public static boolean getEncryptionUpdated(){
		return encryptionUpdated;
	}
	public static String encryptWithNewKey(String plainText) throws NEDSSSystemException {
		encryptionUpdated = true;
		String encodedString =encryptWithKey(plainText, updatedEncryptionKey);
		return encodedString;
	}
	public static void updateEncryptionKey() {
		encryptionKey =updatedEncryptionKey;
	}
	public static String encrypt(String plainText) throws NEDSSSystemException, NEDSSSystemException {
		if(encryptionUpdated)
			throw new NEDSSSystemException("EncryptorDecryptor.encrypt Encryption process has been updated. Please restart the server!!!");
		return encryptWithKey(plainText, encryptionKey);
	}
	
	public static String decrypt(String encryptedText) throws NEDSSSystemException {
		return decryptWithKey(encryptedText, encryptionKey);
	}

	static String encryptWithKey(String plainText, String encryptionKey) throws NEDSSSystemException {
		try {
			if(encryptionKey==null || encryptionKey.trim().equalsIgnoreCase(""))
				return plainText;
			SecretKeySpec keyspec = new SecretKeySpec(encryptionKey.getBytes("UTF8"), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			String newText=plainText.toLowerCase();
			byte[] plaintext = newText.getBytes("UTF8");
			byte[] encrypted = cipher.doFinal(plaintext);
			byte[] encodedBytes = Base64.encodeBase64(encrypted);
			String encodedStr = new String(encodedBytes);
			return encodedStr;
			
		} catch (Exception e) {
			throw new NEDSSSystemException("There exists exception at encryptWithKey method. Please check!!!!" + e);
		}
	}
	static String decryptWithKey(String encryptedText, String encryptionKey) throws NEDSSSystemException {

		try {
			if(encryptionKey==null || encryptionKey.trim().equalsIgnoreCase(""))
				return encryptedText;
			SecretKeySpec keyspec =new SecretKeySpec(encryptionKey.getBytes("UTF8"), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			byte[] encodedArray = Base64.decodeBase64(encryptedText);
			byte[] plaintext = cipher.doFinal(encodedArray);
			String plainStr = new String(plaintext);
			return plainStr;
		} catch (Exception e) {
			throw new NEDSSSystemException("There exists exception at decryptWithKey method. Please check!!!!" + e);
		}
	}
	}