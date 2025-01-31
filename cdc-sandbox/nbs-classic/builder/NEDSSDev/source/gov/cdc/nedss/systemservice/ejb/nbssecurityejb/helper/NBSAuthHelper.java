package gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper;

/**
 * <p>Title: NBSAuthHelper </p>
 * <p>Description: This is a helper class for NBSAuthEJB to do security authentication and authorization.
 * It also contains the functionalites to access directory server, such as add/update users,
 * add/update/delete permission sets</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: CSC</p>
 * @author Ning Peng
 * @version 1.1.3 SP1
 * @updated Pradeep Sharma
 * @version 4.4.1
 */

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.util.SecureUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.util.LogUtils;

public class NBSAuthHelper {
  static final LogUtils logger = new LogUtils(NBSAuthHelper.class.getName());

  
  public NBSAuthHelper() {
	  
  }

  /**
   * This method searchs a user in Directory Server by userID and construct user by its attributes
   * @param userID
   * @return
   * @throws NEDSSSystemException
   */
  public User getUser(Long NEDSSEntryID) throws
	  NEDSSSystemException {

		SecureUtil secureUtil = new SecureUtil();
    	//TODO:  Add getSecurityObjForNEDSSEntryID to SecureUtil
		NBSSecurityObj secObj = secureUtil.getSecurityObjectForUserIdPassword(null, null);
		return secObj.getTheUserProfile().getTheUser();
  }
  
  /**
	 * This method searchs a user in Directory Server by userID and construct
	 * user by its attributes
	 * 
	 * @param userID
	 * @return
	 * @throws NEDSSSystemException
	 */
	public String getUserName(Long NEDSSEntryID) throws NEDSSSystemException {

			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			AuthUserDT secureUser = secureDAOImpl.selectSecureUserDT(NEDSSEntryID);
			return secureUser.getUserFirstNm() + " " + secureUser.getUserLastNm();
	}
}