/*
 * Created on May 3, 2004
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

import javax.rmi.PortableRemoteObject;

import java.util.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbssecurity.encryption.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;

public class BlowFishKeyManager {

	private NBSSecurityObj securityObj = null;
	/**
	 * Constructor
	 * @throws java.lang.Exception
	 */
	public BlowFishKeyManager() {
	}

	public static void main(String[] args) throws Exception {
		String output = null;
		String keyPhrase = null;

		if (!(args.length == 2 && args[0].equalsIgnoreCase("-username"))
			&& !(args.length == 4
				&& args[0].equalsIgnoreCase("-username")
				&& args[2].equalsIgnoreCase("-newkeyphrase"))) {
			System.out.println(
				"Usage: BlowFishKeyManager -username <username>");
			System.out.println(
				"   Or: BlowFishKeyManager -username <username> -newkeyphrase <key phrase>");
			return;
		}
		if (args.length == 4)
			keyPhrase = args[3];

		BlowFishKeyManager mgr = new BlowFishKeyManager();
		boolean securityCheck = mgr.checkPermission(args[1]);
		if (!securityCheck) {
			System.out.println(
				"You do not have permission to run the utility.");
			System.exit(0);
		}

		Map<Object, Object> settings =
			EncryptionService.getInstance().getEncryptorSettings();
		if (settings == null) {
			System.out.println(
				"You have not configured the encryption service.  Please check NEDSS.properties.");
			System.exit(0);
		}
		
		BlowFishEncryptorSetting blowfishSetting = null;
		String key = null;
		for (Iterator<?> iter = settings.keySet().iterator(); iter.hasNext();) {
			key = (String)iter.next();
			EncryptorSetting setting = (EncryptorSetting) settings.get(key);
			if(setting != null && (setting instanceof BlowFishEncryptorSetting)){
				blowfishSetting = (BlowFishEncryptorSetting)setting;
				break;
			}
			
		}
		
		if (blowfishSetting == null) {
			System.out.println(
				"You did not configure the encryption service to use blowfish algorithm.");
			System.exit(0);
		}

		if (keyPhrase == null) {
			// display 
//			System.out.println(
//				"Your current secret key is: "
//					+ blowfishSetting.getKey());
			System.exit(0);
		} else {
			BlowFishEncryptorSetting newSetting =
				new BlowFishEncryptorSetting();
			newSetting.updateKey(keyPhrase);
			EncryptionService.getInstance().updateEncryptorSetting(key, newSetting);
			System.out.println("We have updated your secret key.");
//			System.out.println(
//				"The new key is: "
//					+ ((BlowFishEncryptorSetting) newSetting).getKey());
			System.out.println(
				"Please restart your app server to use the new secret key.");
			System.exit(0);
		}

	}

	private boolean checkPermission(String userName) {
		securityObj = getNBSSecurity(userName, "");
		if (securityObj == null) {
			return false;
		}
		return securityObj.isUserMSA();
	}

	private NBSSecurityObj getNBSSecurity(String userName, String passWord) {
		NBSSecurityObj securityObj = null;
		try {

			MainSessionCommandHome msCommandHome = null;
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			msCommandHome =
				(MainSessionCommandHome) PortableRemoteObject.narrow(
					nedssUtils.lookupBean(sBeanName),
					MainSessionCommandHome.class);
			MainSessionCommand mainSessionCommand = msCommandHome.create();
			securityObj =
				mainSessionCommand.nbsSecurityLogin(userName, passWord);
		} catch (Exception e) {
			System.out.println(" Error in calling mainsessionCommand  " + e);
		}
		return securityObj;
	}
}
