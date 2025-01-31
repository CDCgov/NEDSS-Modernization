package gov.cdc.nedss.systemservice.ejb.dbauthejb.util;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjTypeDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpTypeDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthPermSetDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthProgAreaAdminDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserRoleDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.PermissionSet;
import gov.cdc.nedss.systemservice.nbssecurity.RealizedRole;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * Name:  SecureUtil.java
 * Description: Utility methods new database based Security
 * Copyright:	Copyright (c) 2011
 * Company: 	CSC
 * @author	Gregory Tucker
 * @version	1.0
 */
public class SecureUtil {
	static final LogUtils logger = new LogUtils(SecureUtil.class.getName());
	DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
	private int numberOfPermissionSets =0;
	
	/**
	* Get the NBS Security Obj for the Userid
	* @param String UserId
	* @param String UserPassword (not implemented)
	* @return NBS Security Object
	*/
	public NBSSecurityObj getSecurityObjectForUserIdPassword(String userId, String password)
	throws NEDSSSystemException {
			 
		 Long secureUserUid = null;
		 UserProfile userProfile = new UserProfile();
		 Collection<Object> permissionSetCollection = null;
		 
		 secureUserUid = buildUserProfile(userId, userProfile);
		 if (secureUserUid == null) {
			 logger.debug("Database lookup failed for user: "+userId);
			 return null;
		 }
		 permissionSetCollection = buildPermissionSetCollectionForUser(secureUserUid);
		 if (permissionSetCollection == null) {
			 logger.warn("No permission sets found for user: "+userId + "??");
			 return null;
		 }
		 NBSSecurityObj nbsSecurityObj = new NBSSecurityObj(userProfile, permissionSetCollection);
		 return nbsSecurityObj;
	}
	
	/**
	* build the securityObj.theUserProfile for the user uid
	* from the secureUserDT, programAreaAdmin, permission set tables
	* @param Long secureUserUid
	* @return userUid and updated userProfile
	* 
	*/	 
	public Long buildUserProfile(String userId, UserProfile userProfile) {
		
		AuthUserDT secureUserDT = null;
		 try {
			 secureUserDT = secureDAOImpl.selectSecureUserDT(userId);
			 if (secureUserDT == null) {
				 logger.debug("Database lookup failed for user: "+userId);
				 return null;
			 }
			 
		} catch(NEDSSSystemException sysex) {
			logger.warn("System error while building security obj, exception = " + sysex.getMessage());
			logger.warn("for user: "+userId);
			sysex.printStackTrace();
			return null;
		}
		catch(Exception ex)
		{
			logger.warn("General error while building security obj, exception = " + ex.getMessage());
			logger.warn("for user: "+userId);
			ex.printStackTrace();
			return null;
		}
		
		User newUser = new User();
		userProfile.setTheUser(newUser);
		userProfile.theUser.setUserID(secureUserDT.getUserId()); //always present
		userProfile.theUser.setReportingFacilityUid(secureUserDT.getExternalOrgUid());
		userProfile.theUser.setProviderUid(secureUserDT.getProviderUid());
		if (secureUserDT.getUserFirstNm() != null)
			userProfile.theUser.setFirstName(secureUserDT.getUserFirstNm());
		if (secureUserDT.getUserLastNm() != null)
			userProfile.theUser.setLastName(secureUserDT.getUserLastNm());
		if (secureUserDT.getNedssEntryId() != null)
			userProfile.theUser.setEntryID(secureUserDT.getNedssEntryId().toString());
		if (secureUserDT.getUserComments() != null) //legacy, not used
			userProfile.theUser.setComments(secureUserDT.getUserComments());
		if (secureUserDT.getMasterSecAdminInd() != null) //maybe Y/N can't find use
			userProfile.theUser.setMsa(secureUserDT.getMasterSecAdminInd());
		//userProfile.theUser.setFacilityDetails(); //not being called anywhere
		if (secureUserDT.getProgAreaAdminInd() != null)
			userProfile.theUser.setPaa(secureUserDT.getProgAreaAdminInd()); //denorm
		
		if (secureUserDT.getUserPassword() != null)
			userProfile.theUser.setPassword(secureUserDT.getUserPassword());
		//userProfile.theUser.setReadOnly(); not in use
		if (secureUserDT.getUserType() != null)  //internalUser or externalUser
			userProfile.theUser.setUserType(secureUserDT.getUserType());
		userProfile.theUser.setStatus(secureUserDT.getRecordStatusCd());
		userProfile.theUser.setJurisdictionDerivationInd(secureUserDT.getJurisdictionDerivationInd());

		String progAreaAdminStr = getProgAreaAdminDelimitedString(secureUserDT.getAuthUserUid());
		if (progAreaAdminStr != null)
			userProfile.theUser.setPaaProgramArea(progAreaAdminStr);
		//denormalized adminUserTypes for some reason
		String adminUserTypesStr = "";
		if (userProfile.theUser.getMsa() != null && 
				userProfile.theUser.getMsa().equalsIgnoreCase("T")) 
		{
			adminUserTypesStr = adminUserTypesStr.concat("MSA");
			if (userProfile.theUser.getPaa() != null && 
				userProfile.theUser.getPaa().equalsIgnoreCase("T"))
				adminUserTypesStr = adminUserTypesStr.concat("-PAA");
		} else if (userProfile.theUser.getPaa() != null && 
				userProfile.theUser.getPaa().equalsIgnoreCase("T"))
			adminUserTypesStr = adminUserTypesStr.concat("PAA");
			if (adminUserTypesStr != null)
			userProfile.theUser.setAdminUserType(adminUserTypesStr);
		//built the realizedRole leaving out the byte array for now...
		buildTheRealizedRoleCollection(userProfile);
		return(secureUserDT.getAuthUserUid());
	}
	/**
	* build the buildTheRealizedRoleCollection
	* Note that thePermissionSet is not filled in here
	* It is set when the security object is created to one of 
	* the permission sets in the permission set collection.
	* @param Long secureUserUid
	* @return userUid and updated userProfile
	* 
	*/	
	Collection<Object> buildTheRealizedRoleCollection(UserProfile userProfile) {
		AuthUserRoleDT secureUserRoleDT = null;
		Collection<Object> secureUserRoleList = null;
		String userId = userProfile.theUser.getUserID();
		 try {
			 secureUserRoleList = secureDAOImpl.selectSecureUserRoleDT(userId);
			 if (secureUserRoleList == null) {
				 logger.debug("Database lookup failed for user security roles. ");
				 return null;
			 }
			 
		} catch(NEDSSSystemException sysex) {
			logger.warn("System error while building user role list, exception = " + sysex.getMessage());
//			logger.warn("for user: "+userId);
			sysex.printStackTrace();
			return null;
		}
		catch(Exception ex)
		{
			logger.warn("General error while building user role list, exception = " + ex.getMessage());
//			logger.warn("for user: "+userId);
			ex.printStackTrace();
			return null;
		}
		
		//arraylist of RealizedRole objects
		Collection<Object> legacyRealisedRoleCollection = new ArrayList<Object> ();  //NBS legacy permission set
		Iterator<Object> it = secureUserRoleList.iterator();
		while (it.hasNext()) {
			secureUserRoleDT  = (AuthUserRoleDT) it.next();
			RealizedRole realizedRole = new RealizedRole();
			//Name
			if 	(secureUserRoleDT.getAuthRoleNm()!= null)
				realizedRole.setRoleName(secureUserRoleDT.getAuthRoleNm());
			if 	(secureUserRoleDT.getProgAreaCd() != null)
				realizedRole.setProgramAreaCode(secureUserRoleDT.getProgAreaCd());
			if  (secureUserRoleDT.getJurisdictionCd() != null)
				realizedRole.setJurisdictionCode(secureUserRoleDT.getJurisdictionCd());
			if (secureUserRoleDT.getRoleGuestInd() != null &&
					secureUserRoleDT.getRoleGuestInd().equalsIgnoreCase("T")) {
				realizedRole.setGuest(true);
				realizedRole.setGuestString("Y");
			}
			if (secureUserRoleDT.getReadOnlyInd() != null &&
					secureUserRoleDT.getReadOnlyInd().equalsIgnoreCase("T")) 
				realizedRole.setReadOnly(true);
			legacyRealisedRoleCollection.add(realizedRole);
		}
		userProfile.setTheRealizedRoleCollection(legacyRealisedRoleCollection);
		return legacyRealisedRoleCollection;
	}
	
	/**
	 * build theprogram area admin rights string for the user uid
	 * used by the security object theUser.paaProgramArea
	 * i.e. GCD|HEP|BMIRD|
	 * @param Long secureUserUid
	 * @return delimited string
	 * 
	 */
	String getProgAreaAdminDelimitedString(Long secureUserUid) {
		String progAreaAdminDelimitedStr = "";
		Collection<Object> userAdminList;
		 try {
			 userAdminList = secureDAOImpl.selectSecureProgramAreaAdminList(secureUserUid);
			 if (userAdminList== null) {
				 logger.debug("No admin rights found for user uid: "+secureUserUid.toString());
				 return null;
			 }
			 
		} catch(NEDSSSystemException sysex) {
			logger.error("System error while selecting user PA admin rights, exception = " + sysex);
			sysex.printStackTrace();
			return null;
		}
		catch(Exception ex)
		{
			logger.error("General error while selecting user PA admin rights, exception = " + ex);
			ex.printStackTrace();
			return null;
		}
		
		Iterator<Object> it = userAdminList.iterator();
		while (it.hasNext()) {
			AuthProgAreaAdminDT secureProgramAreaAdminDT  = (AuthProgAreaAdminDT) it.next();
			if 	(secureProgramAreaAdminDT.getProgAreaCd() != null) {
				if (progAreaAdminDelimitedStr.isEmpty())
					progAreaAdminDelimitedStr = progAreaAdminDelimitedStr.concat(secureProgramAreaAdminDT.getProgAreaCd()+"|");
				else
					progAreaAdminDelimitedStr = progAreaAdminDelimitedStr.concat(secureProgramAreaAdminDT.getProgAreaCd()+"|");
			}
		}
		return progAreaAdminDelimitedStr;
	}
	/**
	 * Get the distinct permission sets for the  user uid
	 * ends up in the securityObj.thePermissionSetColletion()
	 * @param Long secureUserUid
	 * @return permissionSetCollection
	 * 
	 */
	Collection<Object> buildPermissionSetCollectionForUser(Long secureUserUid)
	{
	 Collection<Object> dbPermissionSetCollection	 = null;
	 try {
		 dbPermissionSetCollection = secureDAOImpl.getDistinctPermissionSetsForUser(secureUserUid);
		 if (dbPermissionSetCollection == null) {
			 logger.error("Error: No permission sets found for User - UID=: "+secureUserUid.toString());
			 return null;
		 }
		
	} catch(NEDSSSystemException sysex) {
		logger.warn("System error while retrieving permission sets for user, exception = " + sysex);
		sysex.printStackTrace();
		return null;
	}
	catch(Exception ex)
	{
		logger.warn("General error while retrieving permission sets for user");
		ex.printStackTrace();
		return null;
	}
	
	Collection<Object> legacyPermissionSetCollection = new ArrayList<Object> ();  //NBS legacy permission set
	Iterator<Object> it = dbPermissionSetCollection.iterator();
	while (it.hasNext()) {
		AuthPermSetDT securePermissionSetDT  = (AuthPermSetDT) it.next();
		PermissionSet permSet = new PermissionSet();
		permSet.setRoleName(securePermissionSetDT
				.getPermSetNm() != null ? securePermissionSetDT
				.getPermSetNm() : "");

		permSet.setDescription(securePermissionSetDT
				.getPermSetDesc() != null ? securePermissionSetDT
				.getPermSetDesc() : "");

		permSet.setReadOnly(securePermissionSetDT
				.getSysDefinedPermSetInd() != null
				&& securePermissionSetDT.getSysDefinedPermSetInd()
						.equals("T") ? true : false);
		
		//get the byte array of permissions that goes into the permission set
		//values will be 0 for most, 1,2,3 if set
		permSet.setBusinessObjectOperations(getBusinessObjectOperationsByteArray(securePermissionSetDT.getAuthPermSetUid()));
		legacyPermissionSetCollection.add(permSet);
	}
	
	return legacyPermissionSetCollection;
}
	
	
	/**
	* getBusinessObjectOperationsByteArray
	* Each object has a number of operations. If the user can perform the operation 
	* on the object it is set to 1. If the guest 2. if Both 3. Zero=no right to perform.
	* So, Reporting object selectFilterCriteriaPrivate operation is byte 477.
	* @param Long permissionSetUid
	* @return byteArray with permissions set to zero, one, two or three
	* 
	*/	 
	
	public byte[] getBusinessObjectOperationsByteArray(Long securePermissionSetUid) {
		//get the size needed for the byte array
		int businessObjectOperationsSize = 0;
		int numberOfOperations = 0;
		 try {
		     businessObjectOperationsSize = secureDAOImpl.getBusinessObjectOperationsSize();
		     numberOfOperations = secureDAOImpl.getBusinessOperationsSize();
		} catch(NEDSSSystemException sysex) {
				logger.warn("System error while getting Business Object Operations Size, exception = " + sysex);
				sysex.printStackTrace();
				return null;
		}
		catch(Exception ex)
		{
				logger.warn("System error while getting Business Object Operations Size");
				ex.printStackTrace();
				return null;
		}		
		
		// this array will end up in businessObjectOperations in the nbsSecurityObj
		byte[] byteArray = new byte[businessObjectOperationsSize];
		//byte array is initialized to zero
		//set permissions that are present for each object present
		Collection<Object> businessObjectCollection	 = null;
		 try {
			 businessObjectCollection = secureDAOImpl.selectBusinessObjectsForPermissionSet(securePermissionSetUid);
			 if (businessObjectCollection== null) {
				 logger.error("Error: No Business Objects found for PermissionSet??: "+securePermissionSetUid.toString());
				 return null;
			 }
			
		} catch(NEDSSSystemException sysex) {
			logger.warn("System error while retrieving bus objs for permission set, exception = " + sysex);
			sysex.printStackTrace();
			return null;
		}
		catch(Exception ex)
		{
			logger.warn("General error while retrieving business objects for a permission set");
			ex.printStackTrace();
			return null;
		}
		//for each operation, set the values into the byte array

		Iterator<Object> it = businessObjectCollection.iterator();
		while (it.hasNext()) {
			AuthBusObjRtDT secureBusinessObjectDT  = (AuthBusObjRtDT) it.next();
		 try {
			 Collection<Object> operationsCollection = secureDAOImpl.selectOperationsForBusinessObject(secureBusinessObjectDT.getAuthBusObjRtUid());
			 if (operationsCollection== null) {
				 continue;
			 }
			 Iterator<Object> opIt = operationsCollection.iterator();
				while (opIt.hasNext()) {
					int arrayOffset = 0;
					
					//the byte array is around 950 bytes
					//there is space for all the objects and operation rights
					//find out the offset in the array where we need to plug the permission
					AuthBusOpRtDT secureBusinessOperationDT  = (AuthBusOpRtDT) opIt.next();
					int objOffset = secureBusinessObjectDT.getOperationSequence().intValue();
					if (objOffset != 0)
						objOffset = objOffset * (numberOfOperations + 1);
					int opOffset = secureBusinessOperationDT.getOperationSequence().intValue();
					arrayOffset = objOffset + opOffset;
					
					int rightVal = 0;
					if (secureBusinessOperationDT.getBusOpUserRt() != null &&
							secureBusinessOperationDT.getBusOpUserRt().equalsIgnoreCase("T"))
						rightVal = rightVal + 1; //user has permission
					if (secureBusinessOperationDT.getBusOpGuestRt() != null &&
							secureBusinessOperationDT.getBusOpGuestRt().equalsIgnoreCase("T"))
						rightVal = rightVal + 2; //guest has permission
					byteArray[arrayOffset] = (byte) rightVal; //store the permission into the byte array
				}
		} catch(NEDSSSystemException sysex) {
			logger.warn("System error while retrieving permission sets for user, exception = " + sysex);
			sysex.printStackTrace();
			return null;
		}
		catch(Exception ex)
		{
			logger.warn("General error while retrieving permission sets for user");
			ex.printStackTrace();
			return null;
		}		
		} //it.hasNext
		return byteArray;
	}
	
    public AuthUserDT updateUser(NBSSecurityObj nbsSecObj, User user) {
		
		
		AuthUserDT secureUserDT = new AuthUserDT();
		
		if(nbsSecObj.getEntryID() != null){
			//secureUserDT.setAddUserId(Long.valueOf(nbsSecObj.getEntryID()));
			secureUserDT.setLastChgUserId(Long.valueOf(nbsSecObj.getEntryID()));
		}
		if(user.getStatus()!=null && user.getStatus().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_Active))
			secureUserDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		else if(user.getStatus()!=null && user.getStatus().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_Inactive))
			secureUserDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

		String adminUserType = user.getAdminUserType();
		if (adminUserType != null) {
			if (adminUserType.contains("MSA")) {
				secureUserDT.setMasterSecAdminInd(NEDSSConstants.TRUE);
			} else {
				secureUserDT.setMasterSecAdminInd(NEDSSConstants.FALSE);
			}
			if (adminUserType.contains("PAA")) {
				secureUserDT.setProgAreaAdminInd(NEDSSConstants.TRUE);
			} else {
				secureUserDT.setProgAreaAdminInd(NEDSSConstants.FALSE);
			}
		} else {
			secureUserDT.setMasterSecAdminInd(NEDSSConstants.FALSE);
			secureUserDT.setProgAreaAdminInd(NEDSSConstants.FALSE);
		}
		
		secureUserDT.setUserComments(user.getComments());
		secureUserDT.setUserFirstNm(user.getFirstName());
		secureUserDT.setUserLastNm(user.getLastName());
		
		secureUserDT.setUserId(user.getUserID());
		// this should not be set for Update
		//if (user.getEntryID() != null && !user.getEntryID().equals("")) {
		//	secureUserDT.setNedssEntryId(Long.parseLong(user.getEntryID()));
		//}	
		secureUserDT.setUserType(user.getUserType());
		
		secureUserDT.setJurisdictionDerivationInd(user.getJurisdictionDerivationInd());
		
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
		try {
			//get the secureUserId
			AuthUserDT secureUserDT1 = new AuthUserDT();
			
			secureUserDT1 = secureDAOImpl.selectSecureUserDT(user.getUserID());
			secureUserDT.setAuthUserUid(secureUserDT1.getAuthUserUid());
			secureUserDT.setNedssEntryId(secureUserDT1.getNedssEntryId());

			java.util.Date startDate= new java.util.Date();
			Timestamp lastChgTime = new Timestamp(startDate.getTime()); 
			secureUserDT.setLastChgTime(lastChgTime);
			
			if(secureUserDT.getRecordStatusCd()==null)
				secureUserDT.setRecordStatusCd(secureUserDT1.getRecordStatusCd());
			if(secureUserDT.getRecordStatusTime()==null)
				secureUserDT.setRecordStatusTime(secureUserDT1.getRecordStatusTime());
			if(secureUserDT.getAddUserId()==null)
				secureUserDT.setAddUserId(secureUserDT1.getAddUserId());
			if(secureUserDT.getAddTime()==null)
				secureUserDT.setAddTime(secureUserDT1.getAddTime());
			secureUserDT.setExternalOrgUid(user.getReportingFacilityUid());
			secureUserDT.setProviderUid(user.getProviderUid());
			secureUserDT = secureDAOImpl.updateSecureUserDT(secureUserDT);
			 if (secureUserDT == null) {
				 logger.error("Error: Unable to create user record , userID =" + user.getUserID());
				 return null;
			 }
			} catch(NEDSSSystemException sysex) {
				logger.warn("System error while inserting permission set  for userID = " + user.getUserID() +", exception = " + sysex);
				sysex.printStackTrace();
				return null;
			}
			catch(Exception ex)
			{
				logger.warn("General error while inserting permission set  for userID = " + user.getUserID() + ", exception = " + ex.getMessage());
				ex.printStackTrace();
				return null;
			}
			// return the UID for the user
			return secureUserDT;
	}
    
    
	public void updatePAAdminAndRolesForUser(UserProfile userProfile, AuthUserDT authUserDT) {
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
		User user = userProfile.getTheUser();
		

		String paaProgramAreaPipedString = user.getPaaProgramArea();
		secureDAOImpl.deleteProgramAreaAdmin(authUserDT.getAuthUserUid());
		if (paaProgramAreaPipedString != null && paaProgramAreaPipedString.trim().length() > 0) {
			String[] parsedPAList = paaProgramAreaPipedString.split("\\|");
			if (parsedPAList != null) {
				for (int i=0;i < parsedPAList.length; i++) {
					String paaString = parsedPAList[i];
					AuthProgAreaAdminDT secureProgramAreaAdminDT = new AuthProgAreaAdminDT();
					secureProgramAreaAdminDT.setProgAreaCd(paaString);
					secureProgramAreaAdminDT.setLastChgUserId(authUserDT.getLastChgUserId());
					secureProgramAreaAdminDT.setLastChgTime(authUserDT.getLastChgTime());
					secureProgramAreaAdminDT.setRecordStatusTime(authUserDT.getRecordStatusTime());
					secureProgramAreaAdminDT.setRecordStatusCd(authUserDT.getRecordStatusCd());
					secureProgramAreaAdminDT.setAddTime(authUserDT.getAddTime());
					secureProgramAreaAdminDT.setAddUserId(authUserDT.getAddUserId());
					secureProgramAreaAdminDT.setAuthUserUid(authUserDT.getAuthUserUid());
					secureProgramAreaAdminDT.setAuthUserInd("P");
					
					//delete the secureProgram area and re insert the record. 
					secureDAOImpl.insertProgramAreaAdminDT(secureProgramAreaAdminDT);
				}
			}

		}
		
		Collection<Object> realizedRoleCollection = userProfile.getTheRealizedRoleCollection();
		secureDAOImpl.deleteSecureUserRole(authUserDT.getAuthUserUid());
		if (realizedRoleCollection != null) {
			Iterator<Object> rrIter = realizedRoleCollection.iterator();
			while (rrIter.hasNext()) {
				RealizedRole realizedRole = (RealizedRole)rrIter.next();
				//First find permission set to associate via role
				Long securePermissionSetUid = secureDAOImpl.doesPermissionSetNameExist(realizedRole.getRoleName());
				if (securePermissionSetUid != null && realizedRole.isItDelete() != true) {
					AuthUserRoleDT secureUserRole = new AuthUserRoleDT();
					
					secureUserRole.setAuthUserUid(authUserDT.getAuthUserUid());
					secureUserRole.setAuthPermSetUid(securePermissionSetUid);
					secureUserRole.setAddUserId(authUserDT.getAuthUserUid());
					secureUserRole.setLastChgUserId(authUserDT.getLastChgUserId());
					secureUserRole.setRecordStatusCd(authUserDT.getRecordStatusCd());
					secureUserRole.setAddTime(authUserDT.getAddTime());
					secureUserRole.setLastChgUserId(authUserDT.getLastChgUserId());
					secureUserRole.setLastChgTime(authUserDT.getLastChgTime());
					secureUserRole.setRecordStatusTime(authUserDT.getRecordStatusTime());
					secureUserRole.setAuthRoleNm(realizedRole.getRoleName());
					secureUserRole.setProgAreaCd(realizedRole.getProgramAreaCode());
					if(realizedRole.getJurisdictionCode() != null)
						secureUserRole.setJurisdictionCd(realizedRole.getJurisdictionCode().toUpperCase());
					if (realizedRole.getGuest()) {
						secureUserRole.setRoleGuestInd(NEDSSConstants.TRUE);
					} else {
						secureUserRole.setRoleGuestInd(NEDSSConstants.FALSE);
					}
					
					if (realizedRole.getReadOnly()) {
						secureUserRole.setReadOnlyInd(NEDSSConstants.TRUE);
					} else {
						secureUserRole.setReadOnlyInd(NEDSSConstants.FALSE);
					}
					
					secureUserRole.setDispSeqNbr(realizedRole.getSeqNum());
					//delete the secureProgram area and re insert the record. 
					secureDAOImpl.insertSecureUserRoleDT(secureUserRole);
				} else {
					//TODO:  Throw exception for missing permission set?
				}
			}
		}
		
	}
	
	public Long updatePermissionSet(PermissionSet permSet) {
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
	
		AuthPermSetDT securePermissionSetDT = new AuthPermSetDT();
		securePermissionSetDT.setPermSetNm(permSet.getRoleName());
		if (permSet.getDescription() != null)
			securePermissionSetDT.setPermSetDesc(permSet.getDescription());
		if (permSet.getReadOnly() == true)
			securePermissionSetDT.setSysDefinedPermSetInd("T");
		//if the name starts with NEDSS it should be readonly
		if (permSet.getRoleName().startsWith("NEDSS"))
			securePermissionSetDT.setSysDefinedPermSetInd("T");
		
		Long securePermissionSetUid = null;
		
		try {
			AuthPermSetDT securePermissionSetDT1 = new AuthPermSetDT();
			//getting the permissionsetUid and setting it to the securePermissionSETDT
			securePermissionSetDT1 = secureDAOImpl.getPermissionSetDT(securePermissionSetDT.getPermSetNm());			
			securePermissionSetDT.setAuthPermSetUid(securePermissionSetDT1.getAuthPermSetUid());
			securePermissionSetDT.setAddTime(securePermissionSetDT1.getAddTime());
			securePermissionSetDT.setLastChgTime(securePermissionSetDT1.getLastChgTime());
			securePermissionSetDT.setLastChgUserId(securePermissionSetDT1.getLastChgUserId());
			securePermissionSetDT.setRecordStatusCd(securePermissionSetDT1.getRecordStatusCd());
			securePermissionSetDT.setRecordStatusTime(securePermissionSetDT1.getRecordStatusTime());
			securePermissionSetDT.setAddUserId(securePermissionSetDT1.getAddUserId());
		
			
			securePermissionSetUid = secureDAOImpl.updateSecurePermissionSetDT(securePermissionSetDT);
		 if (securePermissionSetDT == null) {
			 logger.error("Error: Unable to create permission set record   "+permSet.getRoleName());
			 return null;
		 }
		} catch(NEDSSSystemException sysex) {
			logger.warn("System error while inserting permission set , exception = " + sysex);
			sysex.printStackTrace();
			return null;
		}
		catch(Exception ex)
		{
			logger.warn("General error while inserting permission set  " + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
		// return the UID for the business objects
		return securePermissionSetDT.getAuthPermSetUid();
	}
	
	               
	public boolean updateBusinessObjectsAndOperationsForPermissionSet(Long permSetUid, byte[] busObjOps, Long userId) {
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
		int busObjOffsetSize = 0;
		Collection<Object> businessObjectTypeList = new ArrayList<Object> ();//AuthBusinessObjectTypeDT
		Collection<Object> operationsList = new ArrayList<Object> ();//AuthBusinessOperationTypeDT
		
		try {
			businessObjectTypeList = secureDAOImpl.selectAllBusinessObjectTypes();
			if (businessObjectTypeList == null) {
			 logger.error("Error: No business Object types ?? ");
			 return false;
			}
			operationsList = secureDAOImpl.selectAllBusinessOperationTypes();
			if (operationsList == null) {
				 logger.error("Error: No business operation types ?? ");
				 return false;
			}
			busObjOffsetSize = secureDAOImpl.getBusinessOperationsSize();
		 
		} catch(NEDSSSystemException sysex) {
			logger.warn("System error while getting business objects and operations list, exception = " + sysex);
			sysex.printStackTrace();
			return false;
		}
		catch(Exception ex)
		{
			logger.warn("General error selecting object types and operation types  " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		
		secureDAOImpl.deleteSecureBusinessOperationRightWithPermSetUid(permSetUid);
		
		secureDAOImpl.deleteSecureBusinessObjectRightWithPermSetUid(permSetUid);
		
		//walk through the byte array incrementing the object and operation looking for each operation
		//when one is not zero, store it
		//we're assuming that no offsets are skipped
		
		int curByte = 0;
		Iterator<Object> objIter = businessObjectTypeList.iterator();
		
		while (objIter.hasNext()) {
			AuthBusObjTypeDT curObjTypeDT = (AuthBusObjTypeDT) objIter.next();
			Long objRightUid = null;
			Iterator<Object> opIter = operationsList.iterator();
			while (opIter.hasNext()) {
				if (busObjOps.length < (curByte-1)) {
					logger.error("Permissions Error during Migration: Exceeded size of byte array but operations remain??");
					continue;
				}
				try{
				int permissionVal = (int) busObjOps[curByte];
				curByte = curByte + 1;  //setup for next time
				AuthBusOpTypeDT curOpTypeDT = (AuthBusOpTypeDT) opIter.next();
				//skip any logically deleted at the object or operation level
				if (permissionVal != 0 && curOpTypeDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)
						&& curObjTypeDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					//if this is the 1st time for this object - store a new one
					if (objRightUid == null)
					objRightUid = storeNewObjectRight(curObjTypeDT, permSetUid, userId);
					//store the permission
					curOpTypeDT.setOperationSequence(curObjTypeDT.getOperationSequence());
					storeNewOperationRight(objRightUid,curOpTypeDT,permissionVal, userId);
				}
				}catch(ArrayIndexOutOfBoundsException ex){
					//This is caused due to an extra object 'SRT' which is not a part of UI, but is in NBSBOLookup, 
					//This object will be needed in the Auth_bus_obj_type table to migrate the data correctly from LDAP, but for UI edit of permission set we need to ignore this object.
					break;
				} //not zero
			} //next op	
		}//another obj
		// return true if successful
		return true;
	}

	/**
	* Create a perm set record in the database
	* @param legacy permSet from LDAP
	* @return - Long uid of SecurePermissionSet rec 
	 * @throws NEDSSAppException 
	*/
	public Long createPermissionSet(PermissionSet permSet, Long userId ) throws NEDSSAppException {
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
	
		AuthPermSetDT securePermissionSetDT = new AuthPermSetDT();
		securePermissionSetDT.setPermSetNm(permSet.getRoleName());
		if (permSet.getDescription() != null)
			securePermissionSetDT.setPermSetDesc(permSet.getDescription());
		if (permSet.getReadOnly() == true)
			securePermissionSetDT.setSysDefinedPermSetInd("T");
		//if the name starts with NEDSS it should be readonly
		if (permSet.getRoleName().startsWith("NEDSS"))
			securePermissionSetDT.setSysDefinedPermSetInd("T");
		securePermissionSetDT.setAddUserId(userId);
		securePermissionSetDT.setLastChgUserId(userId);
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		securePermissionSetDT.setAddTime(time);
		securePermissionSetDT.setLastChgTime(time);
		securePermissionSetDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		securePermissionSetDT.setRecordStatusTime(time);
		
		
		try {
			numberOfPermissionSets=numberOfPermissionSets+1;
			
		 securePermissionSetDT = secureDAOImpl.insertSecurePermissionSetDT(securePermissionSetDT);
		 if (securePermissionSetDT == null) {
				logger.error("SecureUtil.createPermissionSet.Exception thrown :securePermissionSetDT is -"+ securePermissionSetDT + "\n userId is "+userId + "\n permSet is "+permSet);
				String error = "SecureUtil.createPermissionSet.General error selecting object types and securePermissionSetDT-" + securePermissionSetDT;
				logger.fatal(error);
				throw new NEDSSAppException(error);
		 }
		} catch(NEDSSSystemException sysex) {
			logger.error("SecureUtil.createPermissionSet.NEDSSSystemException thrown :securePermissionSetDT is -"+ securePermissionSetDT.toString() + "\n userId is "+userId + "\n permSet is "+permSet);
			String error = "SecureUtil.createPermissionSet.NEDSSSystemException thrown -" + sysex;
			logger.fatal(error);
			throw new NEDSSAppException(error);
		}
		catch(Exception ex)
		{
			logger.error("SecureUtil.createPermissionSet.NEDSSSystemException thrown :securePermissionSetDT is -"+ securePermissionSetDT.toString() + "\n userId is "+userId + "\n permSet is "+permSet);
			String error = "SecureUtil.createPermissionSet.NEDSSSystemException thrown -" + ex;
			logger.fatal(error);
			throw new NEDSSAppException(error);
		}
		// return the UID for the business objects
		return securePermissionSetDT.getAuthPermSetUid();
	}
	public int getNumberOfPermissionSets() {
		return numberOfPermissionSets;
	}

	public void setNumberOfPermissionSets(int numberOfPermissionSets) {
		this.numberOfPermissionSets = numberOfPermissionSets;
	}
	
	/**
	* Read the byte array and create the rights records from the data that is present
	* Note: we skip all zero rights bytes of course
	* @param permSetUid to add recs to
	* @param byte array with permissions set
	* @return - boolean - success or fail 
	 * @throws NEDSSAppException 
	*/
	public boolean createBusinessObjectsAndOperationsForPermissionSet(Long permSetUid, byte[] busObjOps, Long userId) throws NEDSSAppException {
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
		int busObjOffsetSize = 0;
		Collection<Object> businessObjectTypeList = new ArrayList<Object> ();//SecureBusinessObjectTypeDT
		Collection<Object> operationsList = new ArrayList<Object> ();//SecureBusinessOperationTypeDT
		
		try {
			businessObjectTypeList = secureDAOImpl.selectAllBusinessObjectTypes();
			if (businessObjectTypeList == null) {
				logger.error("SecureUtil.createBusinessObjectsAndOperationsForPermissionSet businessObjectTypeList is null for permSetUid  -"+ permSetUid + "\n userId is "+userId);
				String error = "SecureUtil.createBusinessObjectsAndOperationsForPermissionSet businessObjectTypeList is" + businessObjectTypeList;
				logger.fatal(error);
				throw new NEDSSAppException(error);


			}
			operationsList = secureDAOImpl.selectAllBusinessOperationTypes();
			if (operationsList == null) {
				logger.error("SecureUtil.createBusinessObjectsAndOperationsForPermissionSet :permSetUid is -"+ permSetUid + "\n userId is "+userId);
				String error = "SecureUtil.createBusinessObjectsAndOperationsForPermissionSet: General error selecting object types and operation types  - and operationsList is" + operationsList;
				logger.fatal(error);
				throw new NEDSSAppException(error);
			}
			busObjOffsetSize = secureDAOImpl.getBusinessOperationsSize();
			logger.debug("busObjOffsetSize is "+ busObjOffsetSize);
		 
		} catch(NEDSSSystemException sysex) {
			logger.error("SecureUtil.createBusinessObjectsAndOperationsForPermissionSet: NEDSSSystemException thrown :permSetUid is -"+ permSetUid + "\n userId is "+userId);
			String error ="SecureUtil.createBusinessObjectsAndOperationsForPermissionSet: General error selecting object types and operation types  -" + sysex;
			logger.fatal(error);
			throw new NEDSSAppException(error);

		}
		catch(Exception ex)
		{
			logger.error("SecureUtil.createBusinessObjectsAndOperationsForPermissionSet: Exception thrown :permSetUid is -"+ permSetUid + "\n userId is "+userId);
			String error = "SecureUtil.createBusinessObjectsAndOperationsForPermissionSet: General error selecting object types and operation types  -" + ex;
			logger.fatal(error);
			throw new NEDSSAppException(error);

		}
	
		//walk through the byte array incrementing the object and operation looking for each operation
		//when one is not zero, store it
		//we're assuming that no offsets are skipped
		int curByte = 0;
		Iterator<Object> objIter = businessObjectTypeList.iterator();
		while (objIter.hasNext()) {
			AuthBusObjTypeDT curObjTypeDT = (AuthBusObjTypeDT) objIter.next();
			Long objRightUid = null;
			Iterator<Object> opIter = operationsList.iterator();
			while (opIter.hasNext()) {
				if (busObjOps.length < (curByte-1)) {
					logger.error("SecureUtil.createBusinessObjectsAndOperationsForPermissionSet: Permissions Error during Migration: Exceeded size of byte array but operations remain!!");
					continue;
				}
				int permissionVal = (int) busObjOps[curByte];
				curByte = curByte + 1;  //setup for next time
				AuthBusOpTypeDT curOpTypeDT = (AuthBusOpTypeDT) opIter.next();
				//skip any logically deleted at the object or operation level
				if (permissionVal != 0 && curOpTypeDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)
						&& curObjTypeDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					//if this is the 1st time for this object - store a new one
					if (objRightUid == null) 
						objRightUid = storeNewObjectRight(curObjTypeDT, permSetUid, userId);
					//store the permission
					curOpTypeDT.setOperationSequence(curObjTypeDT.getOperationSequence());
					storeNewOperationRight(objRightUid,curOpTypeDT,permissionVal, userId);
				} //not zero
			} //next op	
		}//another obj
		// return true if successful
		return true;
	}
	/**
	* The object has one or more operations - store the object to tie to
	* @param objectTypeDT
	* @param permission set to tie to
	* @return - new object right UID
	*/	
	boolean storeNewOperationRight(Long objectRightUid, AuthBusOpTypeDT curOpTypeDT, int permissionVal, Long userId)
	{
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
		AuthBusOpRtDT secureBusinessOperationRightDT = new AuthBusOpRtDT();
		secureBusinessOperationRightDT.setAuthBusObjRtUid(objectRightUid);
		secureBusinessOperationRightDT.setAuthBusOpTypeUid(curOpTypeDT.getAuthBusOpTypeUid());
		if (userId != null)
			secureBusinessOperationRightDT.setAddUserId(userId);
		if (permissionVal == 1 || permissionVal  == 3)
			secureBusinessOperationRightDT.setBusOpUserRt("T");
		if (permissionVal == 2 || permissionVal  == 3)
			secureBusinessOperationRightDT.setBusOpGuestRt("T");
		
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		secureBusinessOperationRightDT.setAddTime(time);
		secureBusinessOperationRightDT.setAddUserId(userId);
		secureBusinessOperationRightDT.setLastChgTime(time);
		secureBusinessOperationRightDT.setLastChgUserId(userId);
		secureBusinessOperationRightDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		secureBusinessOperationRightDT.setRecordStatusTime(time);
		secureBusinessOperationRightDT.setOperationSequence(curOpTypeDT.getOperationSequence());
		
		try {
			secureBusinessOperationRightDT = secureDAOImpl.insertSecureBusinessOperationRightDT(secureBusinessOperationRightDT);
			
			if (secureBusinessOperationRightDT == null) {
			String error = "SecureUtil.storeNewPermission: Exception error storing new Permission set right  as secureBusinessOperationRightDT is " + secureBusinessOperationRightDT;
			logger.fatal(error);
			throw new NEDSSSystemException(error);

			}
		 
		} catch(NEDSSSystemException sysex) {
			String error = "SecureUtil.storeNewPermission: NEDSSSystemException error storing new Permission set right  " + sysex;
			logger.fatal(error);
			throw new NEDSSSystemException(error);
		}
		catch(Exception ex)
		{
			String error = "SecureUtil.storeNewPermission: General error storing new Permission set right  " + ex;
			logger.fatal(error);
			throw new NEDSSSystemException(error);
		}
		return true;
	}
	/**
	* The object has one or more operations - store the object to tie to
	* @param objectTypeDT
	* @param permission set to tie to
	* @return - new object right UID
	*/	
	public Long storeNewObjectRight(AuthBusObjTypeDT objectTypeDT, Long permissionSetUid, Long userId) {
		DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
		AuthBusObjRtDT secureBusinessObjectRightDT = new AuthBusObjRtDT();
		//just a couple of fields..
		secureBusinessObjectRightDT.setAuthBusObjTypeUid(objectTypeDT.getAuthBusObjTypeUid());
		secureBusinessObjectRightDT.setAuthPermSetUid(permissionSetUid);
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		secureBusinessObjectRightDT.setAddTime(time);
		secureBusinessObjectRightDT.setAddUserId(userId);
		secureBusinessObjectRightDT.setLastChgTime(time);
		secureBusinessObjectRightDT.setLastChgUserId(userId);
		secureBusinessObjectRightDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		secureBusinessObjectRightDT.setRecordStatusTime(time);
		secureBusinessObjectRightDT.setOperationSequence(objectTypeDT.getOperationSequence());
		try {
			secureBusinessObjectRightDT = secureDAOImpl.insertAuthBusObjRightDT(secureBusinessObjectRightDT);
			if (secureBusinessObjectRightDT == null) {
				String error = "SecureUtil.storeNewObjectRight:- Error: Can't add business object right  with secureBusinessObjectRightDT"+ secureBusinessObjectRightDT;
				logger.fatal(error);
				throw new NEDSSSystemException(error);
			}
		} catch(NEDSSSystemException sysex) {
			logger.error("SecureUtil.storeNewObjectRight: NEDSSSystemException thrown :secureBusinessObjectRightDT is _"+ secureBusinessObjectRightDT.toString());
			String error = "SecureUtil.storeNewObjectRight: NEDSSSystemException storing object Right  "+ sysex;
			logger.fatal(error);
			throw new NEDSSSystemException(error);
		}
		catch(Exception ex)	{
			logger.error("SecureUtil.storeNewObjectRight: Exception thrown :secureBusinessObjectRightDT is _"+ secureBusinessObjectRightDT.toString());
			String error = "SecureUtil.storeNewObjectRight: General error storing object Right  "+ ex;
			logger.fatal(error);
			throw new NEDSSSystemException(error);
		}
	return secureBusinessObjectRightDT.getAuthBusObjRtUid();
	}

	public Long createUser(NBSSecurityObj nbsSecObj, User user) throws NEDSSAppException {
		try {
			AuthUserDT secureUserDT = new AuthUserDT();
			java.util.Date dateTime = new java.util.Date();
			Timestamp time = new Timestamp(dateTime.getTime());
			secureUserDT.setAddTime(time);
			secureUserDT.setAddUserId(Long.valueOf(nbsSecObj.getEntryID()));
			secureUserDT.setLastChgTime(time);
			secureUserDT.setLastChgUserId(Long.valueOf(nbsSecObj.getEntryID()));
			if(user.getStatus()!=null)
				secureUserDT.setRecordStatusCd((user.getStatus().toUpperCase()));
			else 
				secureUserDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			String adminUserType = user.getAdminUserType();
			if (adminUserType != null) {
				if (adminUserType.contains("MSA")) {
					secureUserDT.setMasterSecAdminInd(NEDSSConstants.TRUE);
				} else {
					secureUserDT.setMasterSecAdminInd(NEDSSConstants.FALSE);
				}
				if (adminUserType.contains("PAA")) {
					secureUserDT.setProgAreaAdminInd(NEDSSConstants.TRUE);
				} else {
					secureUserDT.setProgAreaAdminInd(NEDSSConstants.FALSE);
				}
			} else {
				secureUserDT.setMasterSecAdminInd(NEDSSConstants.FALSE);
				secureUserDT.setProgAreaAdminInd(NEDSSConstants.FALSE);
			}
			
			secureUserDT.setExternalOrgUid(user.getReportingFacilityUid());
			secureUserDT.setProviderUid(user.getProviderUid());
			secureUserDT.setUserComments(user.getComments());
			secureUserDT.setUserFirstNm(user.getFirstName());
			secureUserDT.setUserLastNm(user.getLastName());
			secureUserDT.setJurisdictionDerivationInd(user.getJurisdictionDerivationInd());
			secureUserDT.setUserId(user.getUserID());
			// this is for moving it from LDAP and and the else part will help while adding a new user
			if (user.getEntryID() != null && !user.getEntryID().equals("")) {
				secureUserDT.setNedssEntryId(Long.parseLong(user.getEntryID()));
			}else{
				Long entryUid  = null;
				 try {
				      UidGeneratorHelper uidGen = new UidGeneratorHelper();
				      entryUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE);
				    }
				    catch (Exception e) {
					      logger.error("SecureUtil.createUser Error getting UID for User");
				    	throw new NEDSSAppException("createUser:- unable to get the entryUid for User");
				    }
				    secureUserDT.setNedssEntryId(entryUid);
				
			}	
			secureUserDT.setUserType(user.getUserType());
			
			
			
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			try {
				secureUserDT = secureDAOImpl.insertSecureUserDT(secureUserDT);
				if (secureUserDT == null) {
					 logger.error("SecureUtil.createUser: Error: Unable to create user record , userID =" + user.getUserID());
					 throw new NEDSSAppException("createUser:- unable to create user record as secureUserDT is null");
				 }
				} catch(NEDSSSystemException sysex) {
					logger.warn("SecureUtil.createUser: System error while inserting permission set  for userID = " + user.getUserID() +", exception = " + sysex);
					 throw new NEDSSAppException("createUser:- unable to create user record"+sysex);
				}
				catch(Exception ex)
				{
					logger.warn("SecureUtil.createUser: General error while inserting permission set  for userID = " + user.getUserID() + ", exception = " + ex.getMessage());
					 throw new NEDSSAppException("createUser:- unable to create user record"+ex);
				}
			
				try {
					secureDAOImpl.updateSecurityLog(secureUserDT);
				} catch (Exception e) {
					logger.warn("SecureUtil.createUser: General error while updating security log for userID = " + user.getUserID() + ", exception = " + e.getMessage());
					 throw new NEDSSAppException("createUser:- unable to create user record"+e);
							
				}
				// return the UID for the user
				return secureUserDT.getAuthUserUid();
		} catch (Exception e) {
			 throw new NEDSSAppException("SecureUtil.createUser: createUser:- unable to create user record"+e);
		}
	}
	
	public void createPAAdminAndRolesForUser(NBSSecurityObj nbsSecObj, UserProfile userProfile, Long secureUserUid) throws NEDSSAppException {
		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			User user = userProfile.getTheUser();
			
			String paaProgramAreaPipedString = user.getPaaProgramArea();
			if (paaProgramAreaPipedString != null && paaProgramAreaPipedString.trim().length() > 0) {
				String[] parsedPAList = paaProgramAreaPipedString.split("\\|");
				if (parsedPAList != null) {
					for (int i=0;i < parsedPAList.length; i++) {
						String paaString = parsedPAList[i];
						AuthProgAreaAdminDT secureProgramAreaAdminDT = new AuthProgAreaAdminDT();
						secureProgramAreaAdminDT.setAuthProgAreaAdminUid(secureUserUid);
						secureProgramAreaAdminDT.setAuthUserUid(secureUserUid);
						secureProgramAreaAdminDT.setProgAreaCd(paaString);
						if(nbsSecObj.getEntryID() != null){
							secureProgramAreaAdminDT.setAddUserId(Long.valueOf(nbsSecObj.getEntryID()));
							secureProgramAreaAdminDT.setLastChgUserId(Long.valueOf(nbsSecObj.getEntryID()));
						}
						secureProgramAreaAdminDT.setAuthUserInd("P");
						secureProgramAreaAdminDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						java.util.Date dateTime = new java.util.Date();
						Timestamp time = new Timestamp(dateTime.getTime());
						secureProgramAreaAdminDT.setAddTime(time);
						secureProgramAreaAdminDT.setLastChgTime(time);
						secureProgramAreaAdminDT.setAddUserId(Long.valueOf(nbsSecObj.getEntryID()));
						secureProgramAreaAdminDT.setLastChgUserId(Long.valueOf(nbsSecObj.getEntryID()));
						secureProgramAreaAdminDT.setRecordStatusTime(time);
						secureDAOImpl.insertProgramAreaAdminDT(secureProgramAreaAdminDT);
					}
				}

			}
			
			Collection<Object> realizedRoleCollection = userProfile.getTheRealizedRoleCollection();
			if (realizedRoleCollection != null) {
				Iterator<Object> rrIter = realizedRoleCollection.iterator();
				while (rrIter.hasNext()) {
					RealizedRole realizedRole = (RealizedRole)rrIter.next();
					//First find permission set to associate via role
					Long securePermissionSetUid = secureDAOImpl.doesPermissionSetNameExist(realizedRole.getRoleName());
					if (securePermissionSetUid != null) {
						AuthUserRoleDT secureUserRole = new AuthUserRoleDT();
						java.util.Date dateTime = new java.util.Date();
						Timestamp time = new Timestamp(dateTime.getTime());
						secureUserRole.setAddTime(time);
						secureUserRole.setLastChgTime(time);
						secureUserRole.setAddUserId(Long.valueOf(nbsSecObj.getEntryID()));
						secureUserRole.setLastChgUserId(Long.valueOf(nbsSecObj.getEntryID()));
						secureUserRole.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						secureUserRole.setRecordStatusTime(time);
						
						
						secureUserRole.setAuthUserUid(secureUserUid);
						secureUserRole.setAuthPermSetUid(securePermissionSetUid);
						secureUserRole.setAuthRoleNm(realizedRole.getRoleName());
						if(nbsSecObj.getEntryID() != null){
							secureUserRole.setAddUserId(Long.valueOf(nbsSecObj.getEntryID()));
							secureUserRole.setLastChgUserId(Long.valueOf(nbsSecObj.getEntryID()));
						}
						secureUserRole.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

						secureUserRole.setProgAreaCd(realizedRole.getProgramAreaCode());
						if(realizedRole.getJurisdictionCode() != null)
							secureUserRole.setJurisdictionCd(realizedRole.getJurisdictionCode().toUpperCase());
						if (realizedRole.getGuest()) {
							secureUserRole.setRoleGuestInd(NEDSSConstants.TRUE);
						} else {
							secureUserRole.setRoleGuestInd(NEDSSConstants.FALSE);
						}
						
						if (realizedRole.getReadOnly()) {
							secureUserRole.setReadOnlyInd(NEDSSConstants.TRUE);
						} else {
							secureUserRole.setReadOnlyInd(NEDSSConstants.FALSE);
						}
						
						secureUserRole.setDispSeqNbr(realizedRole.getSeqNum());

						secureUserRole = secureDAOImpl.insertSecureUserRoleDT(secureUserRole);
					} else {
						logger.debug("SecureUtil.createPAAdminAndRolesForUser unable to find permission set associated to  role :- "+realizedRole.getRoleName());
					}
				}
			}
		} catch (Exception e) {
        	String error = ("SecureUtil.createPAAdminAndRolesForUser:- unable to create user record"+ e.getMessage());
			logger.fatal(error);
			throw new NEDSSAppException("error"+ e);
		}
	}

}

