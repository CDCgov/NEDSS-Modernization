package gov.cdc.nedss.systemservice.ejb.dbauthejb.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuth;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuthHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import javax.rmi.PortableRemoteObject;
/**
 * 
 * @author Pradeep Kumar Sharma
 *Utility class to update security Key
 */
public class SecureUpdateEncryption {
	public static void main (String args[]){
		try {
			
			String defaultUser = null;
			if(args.length == 1){
				defaultUser = args[0];
			} else {
				System.out.println("Usage :  SecureUpdateEncryption.bat [nbs_user_id] "); 
				System.exit(-1); 
			}
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			Object objref = nedssUtils.lookupBean(sBeanName);
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();
			
			NBSSecurityObj nbsSecurityObj = msCommand.nbsSecurityLogin( defaultUser, defaultUser);
			if(nbsSecurityObj==null){
				System.out.println("The user \""+defaultUser+"\" does not exists. Please check!!!");
				System.exit(-1); 
			}
			String encryptionkey =PropertyUtil.getInstance().getSecureNewEncryptionKey();
			String oldEncryptionkey =PropertyUtil.getInstance().getSecureEncryptionKey();
			String akey="";
			if(encryptionkey==null){
				akey=NEDSSConstants.SECURE_KEY_NOT_DEFINED;
				System.out.println("SecureUpdateEncryption:  encryption update process cannot proceed due to error: "+
					"\n\n\nNEDSS.properties do not contain any key for SECURE_NEW_ENCRYPTION_KEY. " +
					"\nPlease insert SECURE_NEW_ENCRYPTION_KEY in NEDSS.properties.\n\n\n");
				System.exit(-1); 
			}else if(oldEncryptionkey==null){
				akey=NEDSSConstants.SECURE_KEY_NOT_DEFINED;
				System.out.println("SecureUpdateEncryption:  encryption update process cannot proceed due to error: "+
					"\n\n\nNEDSS.properties do not contain any key for SECURE_ENCRYPTION_KEY. " +
					"\nPlease insert SECURE_ENCRYPTION_KEY in NEDSS.properties.\n\n\n");
				System.exit(-1); 
			}
			else if(encryptionkey.equalsIgnoreCase(oldEncryptionkey)){
				System.out.println("SecureUpdateEncryption:  encryption update process cannot proceed due to error: "+
						"\n\n\nSECURE_NEW_ENCRYPTION_KEY and SECURE_ENCRYPTION_KEY are identical in  NEDSS.properties. No Encryption is require." +
						"\nPlease check these keys in Nedss.properties if you want to update the encryption process.\n\n\n");
					System.exit(-1); 
				
			}
			else if(encryptionkey.trim().equals("")){
			
				akey=NEDSSConstants.SECURE_VAL_NOT_DEFINED;
				System.out.println("There exists no value for SECURE_ENCRYPTION_KEY in Nedss.propertes."+
						"\n\nIf you WANT to encrypt USERID :PLEASE click \"Enter\"."+
						"\n\nIf you DO NOT WANT to encrypt USERID: PLEASE slect \"N\" or \"No\"");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
			    String userInput = br.readLine(); 
			    if(userInput!=null && userInput.trim().equalsIgnoreCase("N")|| userInput!=null && userInput.trim().equalsIgnoreCase("No")){
			    	System.out.println("\n\nYou have selected USERID not to be encrypted.");
			    }else{
			     	System.out.println("\n\nPlease provide value for SECURE_ENCRYPTION_KEY in Nedss.propertes and run the SecureLDAPMigration.bat process again.");
					System.exit(-1); 
				}
			}
			else{
				//The key is defined. The encryption process will work with default key.
				akey=NEDSSConstants.SECURE_PROP_DEFINED;
			}
			Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.NBS_DB_SECURITY_EJB);
			DbAuthHome secureHome = (DbAuthHome) PortableRemoteObject.narrow(theLookedUpObject, DbAuthHome.class);
			DbAuth dbSecurity = secureHome.create();
			dbSecurity.updateUserIdWithNewKey( nbsSecurityObj);
		}
		catch (Exception e) {
			System.out.println("UpdateEncryptionWithNewKey.main Exception thrown "+ e.getMessage());
			System.out.println("UpdateEncryptionWithNewKey.Exception raised!" + e.getCause());
			e.printStackTrace();
		}

}

}
