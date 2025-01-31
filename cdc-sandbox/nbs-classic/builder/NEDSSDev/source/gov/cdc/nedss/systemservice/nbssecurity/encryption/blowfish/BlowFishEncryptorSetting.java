/*
 * Created on Apr 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish;

import gov.cdc.nedss.systemservice.nbssecurity.encryption.Encryptor;
import gov.cdc.nedss.systemservice.nbssecurity.encryption.EncryptorSetting;

import java.io.Serializable;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlowFishEncryptorSetting
	implements Serializable, EncryptorSetting {

	private static final long serialVersionUID = 1L;

	private static final String INTERNAL_KEY = "Ned$$r0ckS";
	public static final String TYPE = "BLOWFISH";
	private String input = "atlc$c";

	public BlowFishEncryptorSetting() {
		updateKey(input);
	}
	
	/**
	 * @param string
	 */
	public String getKey() {
		return input;
	}

	public synchronized void updateKey(String value) {
		input = BlowFishEncryptor.encryptF(value, INTERNAL_KEY);
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.systemservice.nbssecurity.encryption.EncryptorSetting#createEncryptor()
	 */
	public Encryptor createEncryptor() {
		return new BlowFishEncryptor(this);
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.systemservice.nbssecurity.encryption.EncryptorSetting#getEncryptionType()
	 */
	public String getEncryptionType() {
		return TYPE;
	}

}
