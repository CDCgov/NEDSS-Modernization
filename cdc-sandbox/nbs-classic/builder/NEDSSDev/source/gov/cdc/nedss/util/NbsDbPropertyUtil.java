package gov.cdc.nedss.util;

import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;

/**
 * Utility Class to access database for system property level settings. 
 * @author Pradeep Kumar Shamra
 *
 */
public class NbsDbPropertyUtil {
	static final LogUtils logger = new LogUtils(NbsDbPropertyUtil.class.getName());

	private static NbsDbPropertyUtil instance = null;
	private static final Boolean isDbUser;
	  static{
	 		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
	 		isDbUser=new Boolean(secureDAOImpl.doesDBUserExist());
				logger.debug("NbsDbPropertyUtil: The property settings for isDbUser is "+isDbUser);
				
	  }
	  public static Boolean getUserDBCheck(){
		  return  isDbUser;
	  }
	  public synchronized static NbsDbPropertyUtil getInstance() {

			if (instance == null) {
				instance = new NbsDbPropertyUtil();
			}
				return instance;
		}
	  
}
