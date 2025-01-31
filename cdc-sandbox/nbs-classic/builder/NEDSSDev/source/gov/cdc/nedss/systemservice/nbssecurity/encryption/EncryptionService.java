/*
 * Created on Apr 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.nbssecurity.encryption;

import gov.cdc.nedss.util.*;

import java.io.*;
import java.security.*;
import java.util.*;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EncryptionService {

	//For logging
	static final LogUtils logger =
		new LogUtils(EncryptionService.class.getName());

	private Map<Object,Object> encryptorSettings = null;
	private static final String SETTING_FILE_NAME =
		PropertyUtil.propertiesDir + "encryption.settings";

	/** unique instance */
	private static EncryptionService sInstance = null;

	/** 
	 * Private constuctor
	 */
	private EncryptionService() {
		if (Security.getProvider("SunJCE") == null) {
			try {
				Security.addProvider(
					(Provider) Class
						.forName("com.sun.crypto.provider.SunJCE")
						.newInstance());
			} catch (Exception ex) {
				logger.error("Cannot install provider: " + ex.getMessage());
			}
		}

		// get the setting properties first
		PropertyUtil propUtil = PropertyUtil.getInstance();
		Map settingStrings = propUtil.getEncryptionSettings();

		// update settings
		encryptorSettings = readSettings();
		if (settingStrings == null || settingStrings.isEmpty()) {
			// no encryption settings defined
			encryptorSettings = new HashMap<Object,Object>();
			writeSettings(encryptorSettings);
		} else {
			try {
				boolean needsUpdate = false;
				Map tmpMap = new HashMap<Object,Object>();
				for (Iterator<Object> iter = settingStrings.keySet().iterator();
					iter.hasNext();
					) {
					String element = (String) iter.next();
					Class tmpClass =
						Class.forName((String) settingStrings.get(element));
					tmpMap.put(element, tmpClass.newInstance());
					if (encryptorSettings == null
						|| encryptorSettings.get(element) == null
						|| !encryptorSettings.get(element).getClass().equals(tmpClass)) {
						// we donot have this before
						needsUpdate = true;
					}
				}

				if (needsUpdate) {
					encryptorSettings = tmpMap;
					writeSettings(encryptorSettings);
				}
			} catch (Exception e) {
				logger.error(
					"Cannot initialize encryption service " + e.getMessage());
				encryptorSettings = new HashMap<Object,Object>();
				writeSettings(encryptorSettings);
			}
		}

	}

	private void writeSettings(Map settings) {
		if (settings == null)
			return;
		// wrtie to serialized objects
		try {
			ObjectOutputStream out =
				new ObjectOutputStream(new FileOutputStream(SETTING_FILE_NAME));
			out.writeObject(settings);
		} catch (Exception e1) {
			logger.error(
				"Can not persist encryption setting due to " + e1.getMessage());
		}

	}

	private Map<Object,Object> readSettings() {

		// read from searialized object first
		try {
			ObjectInputStream in =
				new ObjectInputStream(new FileInputStream(SETTING_FILE_NAME));
			return (Map) in.readObject();
		} catch (Exception e) {
			logger.error(
				"Can not read encryption setting due to " + e.getMessage());
			return null;
		}
	}

	public synchronized void updateEncryptorSetting(
		String key,
		EncryptorSetting setting) {

		if (setting == null) {
			return;
		}

		if (encryptorSettings != null && key != null) {
			encryptorSettings.put(key, setting);
			writeSettings(encryptorSettings);
		}
	}

	/** 
	 * Get the unique instance of this class.
	 */
	public static synchronized EncryptionService getInstance() {

		if (sInstance == null) {
			sInstance = new EncryptionService();
		}

		return sInstance;

	}

	/**
	 * Please add method description.
	 * @return
	 */
	public Encryptor getEncryptor(String key) {
		if (encryptorSettings != null && key != null) {
			EncryptorSetting setting =
				(EncryptorSetting) encryptorSettings.get(key);
			if (setting != null) {
				return setting.createEncryptor();
			}
		}
		return null;
	}

	/**
	 * Please add method description.
	 * @return
	 */
	public Map<Object,Object> getEncryptorSettings() {
		return Collections.unmodifiableMap(encryptorSettings);
	}

}
