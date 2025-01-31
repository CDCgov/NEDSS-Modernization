package gov.cdc.nedss.systemservice.ejb.dbauthejb.bean;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.PermissionSet;
import gov.cdc.nedss.systemservice.nbssecurity.RealizedRole;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NbsDbPropertyUtil;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.nio.file.*;

import static java.nio.file.StandardCopyOption.*;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjTypeDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpTypeDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthPermSetDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthProgAreaAdminDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserRoleDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.NBSConfigurationDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.util.SecureUtil;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.dao.UserProfileDAOImpl;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.NBSConfigurationDAO;
/**
 *@Name:  NBSDbAuthEJB.java
 *@Description: Refactored code
 * @Description: NBS dababase security Object
 * @Copyright:    Copyright (c) 2011
 * @Company: CSC
 * @author Greg Tucker
 * @version 1.0
 * @Company: 	SAIC
 * @author	Pradeep K Sharma/2012
 * @version	4.4.1
 */
public class DbAuthEJB  implements SessionBean {
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils (DbAuthEJB.class.getName());
	PropertyUtil propertyUtil = PropertyUtil.getInstance();


	/**
	 * Insert a new User record
	 * @param securityObj and SecureUserDT
	 * @return UID if successful
	 * @throws EJBException, RemoteException
	 */	
	public  Long createSecureUser(NBSSecurityObj nbsSecurityObj, AuthUserDT secureUserDT) throws
	EJBException, RemoteException
	{
		try {
			//user must be Master Security Admin and a Prog Area Admin
			if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
				throw new NEDSSSystemException(
						"Sorry but you do not have Security Administration permission.");
			}

			try {
				DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
				secureUserDT = secureDAOImpl.insertSecureUserDT(secureUserDT);
			}
			catch(NEDSSSystemException se2) {
				String errorString = "NBSDbSecurityEJB.createSecureUser System Exception:  "+ se2.getMessage();
				logger.error(errorString);
				throw new EJBException(errorString);
			}
			catch(Exception e) {
				String errorString = "NBSDbSecurityEJB.createSecureUser General: :  "+ e.getMessage();
				logger.error(errorString);
				throw new EJBException(errorString);
			}
			return secureUserDT.getAuthUserUid(); //return only the new UID
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	} //CreateSecureUser


	/**
	 * Insert a new Business Object Access Right
	 * UID for Bus Obj Type and Perm Set must be present.
	 * @param securityObj and SecureBusinessObjectRightDT
	 * @return UID if successful
	 * @throws EJBException, RemoteException
	 */	
	public  Long createSecureBusinessObjectRight(NBSSecurityObj nbsSecurityObj, AuthBusObjRtDT secureBusinessObjectRightDT) throws
	EJBException, RemoteException
	{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureBusinessObjectRightDT = secureDAOImpl.insertAuthBusObjRightDT(secureBusinessObjectRightDT);
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.createBusinesObjectRight System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.createSecureBusinessObjectRight General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return secureBusinessObjectRightDT.getAuthBusObjRtUid(); //return only the new UID
	} //createSecureBusinessObjectRight


	/**
	 * Insert a new Business Operation on Object Right
	 * UID for Bus Obj  and Bus Op Type must be present.
	 * @param securityObj and SecureBusinessObjectRightDT
	 * @return UID if successful
	 * @throws EJBException, RemoteException
	 */	
	public  Long createSecureBusinessOperationRight(NBSSecurityObj nbsSecurityObj, AuthBusOpRtDT secureBusinessOperationRightDT) throws
	EJBException, RemoteException
	{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureBusinessOperationRightDT = secureDAOImpl.insertSecureBusinessOperationRightDT(secureBusinessOperationRightDT);
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.createBusinesOperationRight System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.createSecureBusinessObjectRight General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return secureBusinessOperationRightDT.getAuthBusObjRtUid(); //return only the new UID
	} //createSecureBusinessOperationRight

	/**
	 * Insert a new Business Operation on Object Right
	 * UID for Bus Obj  and Bus Op Type must be present.
	 * @param securityObj and SecureBusinessObjectRightDT
	 * @return UID if successful
	 * @throws EJBException, RemoteException
	 */	
	public  Long createSecureUserRole(NBSSecurityObj nbsSecurityObj, AuthUserRoleDT secureUserRoleDT) throws
	EJBException, RemoteException
	{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureUserRoleDT = secureDAOImpl.insertSecureUserRoleDT(secureUserRoleDT);
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.createSecureUserRole System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.createSecureUserRole General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return secureUserRoleDT.getAuthUserRoleUid(); //return only the new UID
	} //createSecureUserRole


	/**
	 * Select a Secure User using the Userid
	 * @param String userid i.e. PKS
	 * @return SecureUserDT
	 * @throws EJBException, RemoteException
	 */	
	public  AuthUserDT selectSecureUser(NBSSecurityObj nbsSecurityObj, String userId) throws
	EJBException, RemoteException
	{
		AuthUserDT secureUserDT = null;
		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureUserDT = secureDAOImpl.selectSecureUserDT(userId);
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.selectSecureUser System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.selectSecureUser General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return secureUserDT;
	} //select User Rec using UserId

	/**
	 * Get the Security Object for the specified UserId and Password.
	 * Note: Password is currently not implemented.
	 * @param none
	 * @return NBSSecurity Object
	 * @throws EJBException, RemoteException
	 */
	public  NBSSecurityObj getSecurityObjectForUseridPassword(String userId, String password) throws
	EJBException, RemoteException
	{
		NBSSecurityObj nbsSecurityObj = null;
		try {
			SecureUtil secureUtil = new SecureUtil();
			nbsSecurityObj = secureUtil.getSecurityObjectForUserIdPassword(userId, password);
			//nbsSecurityObj = secureUtil.getSecurityObjectForUserIdPassword("pks", "pks");
		} catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecruityEJB.getSecurityObjectForUseridPassword System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}

		catch(Exception e) {
			String errorString = "NBSDbSecruityEJB.getSecurityObjectForUseridPassword General Exception:  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return nbsSecurityObj; //return sec obj
	}  //getSecurityObjectForUseridPassword()
	
	/**
	 * get the permission setDT List from the Secure PErmission Set DT      
	 * @param nbsSecurityObj
	 * @return
	 * @throws EJBException
	 * @throws RemoteException
	 */
	public  Collection<Object> getPermissionSetDTList(NBSSecurityObj nbsSecurityObj) throws
	EJBException, RemoteException{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		Collection<Object> securePermissionDTColl = new ArrayList<Object>();

		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			securePermissionDTColl = secureDAOImpl.getPermissionSetDTList();
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.getPermissionSetDTList System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.getPermissionSetDTList General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return securePermissionDTColl; //return only the new UID
	} //getPermissionSetDTList
	/**
	 * Get the permission set details for a specific permission set
	 * @param permissionSetName
	 * @param nbsSecObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public PermissionSet getPermissionSet(String permissionSetName,
			NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException {

		try {
			if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
				throw new NEDSSSystemException(
						"You do not have Security Administration permission.");
			}

			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			AuthPermSetDT securePermissionSetDT = new AuthPermSetDT();
			PermissionSet permSet = new PermissionSet();
			try {
				securePermissionSetDT = secureDAOImpl
						.getPermissionSetDT(permissionSetName);
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

			} catch (Exception e){
				logger.error("Error in getPermissionSet mehod "+e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			// getting the business objects and the operations for the permission set 
			try{				
				//get the byte array of permissions that goes into the permission set
				//values will be 0 for most, 1,2,3 if set
				SecureUtil util = new SecureUtil();
				permSet.setBusinessObjectOperations(util.getBusinessObjectOperationsByteArray(securePermissionSetDT.getAuthPermSetUid()));


			}catch (Exception e){
				logger.fatal("Error in getPermissionSet method "+e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			return permSet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public  void updateSecurePermissionSet(AuthPermSetDT securePermissionSetDT,NBSSecurityObj nbsSecurityObj) throws
	EJBException, RemoteException
	{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureDAOImpl.updateSecurePermissionSetDT(securePermissionSetDT);
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.updateSecurePermissionSet System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.updateSecurePermissionSet General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}

	} //updateSecurePermissionSet


	public  Collection<Object> getSecureUserDTList(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		Collection<Object> secureUSerDTColl = new ArrayList<Object>();

		try {
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureUSerDTColl = secureDAOImpl.getSecureUserDTList();
		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.getPermissionSetDTList System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.getPermissionSetDTList General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}
		return secureUSerDTColl; //return only the new UID
	} //getPermissionSetDTList

	/**
	 * Getting the permission set Names(Not the Objects) from the NBS Database
	 * @param nbsSecObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public java.util.Collection<Object> getPermissionSetList(NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException{

		if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"You do not have Security Administration permission.");
		}
		Collection<Object> permissionSetNameColl = new ArrayList<Object>();
		try{
			Collection<Object> permissionsetColl = getPermissionSetDTList(nbsSecObj);
			if(permissionsetColl != null){
				Iterator<Object> iter = permissionsetColl.iterator();
				while(iter.hasNext()){
					AuthPermSetDT securePermissionSetDT = (AuthPermSetDT)iter.next();
					permissionSetNameColl.add(securePermissionSetDT.getPermSetNm());
				}
			}
		}catch(Exception ex){
			logger.error("Error while getting the PermissionSetList " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return permissionSetNameColl;
	}
	/**
	 * getting the PermissionSet Objects from the NBSDatabase
	 * @param nbsSecObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public java.util.Collection<Object>  getPermissionSets(NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException {

		if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"You do not have Security Administration permission.");
		}

		Collection<Object>  permSets = new ArrayList<Object> ();
		try {
			//Step1, get the collection of permissionSetNames
			Collection<Object>  permSetNames = getPermissionSetList(nbsSecObj);
			String permSetName = null;
			Iterator<Object>  iter = permSetNames.iterator();
			//Step2, iterate each permission set, get the PermissionSet object containing the obj-operation matrix
			while (iter.hasNext()) {
				PermissionSet permSet = new PermissionSet();
				permSetName = (String) iter.next();
				permSet = getPermissionSet(permSetName, nbsSecObj);
				permSets.add(permSet);
			}
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return permSets;

	}
	public Boolean doesUserExist(String userID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException {
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}
		Long secureUserUid = null;
		try{
			DbAuthDAOImpl daoImpl = new DbAuthDAOImpl();
			secureUserUid = daoImpl.doesUserExist(userID, false);

		}catch(Exception e){
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException("Error is checking the existing User Id"+e.getMessage(),e);
		}

		if (secureUserUid != null) {
			return new Boolean("true");
		}
		else {
			return new Boolean("false");
		}

	}

	public Boolean permissionSetNameExists(String permissionSetName, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException {
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}
		Long secureUserUid = null;
		try{
			DbAuthDAOImpl daoImpl = new DbAuthDAOImpl();
			secureUserUid = daoImpl.doesPermissionSetNameExist(permissionSetName);

		}catch(Exception e){
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException("Error is checking the existing Permission Set"+e.getMessage(),e);
		}

		if (secureUserUid != null) {
			return new Boolean("true");
		}
		else {
			return new Boolean("false");
		}

	}

	public UserProfile getUser(String userID, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException,NEDSSSystemException{
		UserProfile userProfile = new UserProfile();
		SecureUtil util =  new SecureUtil();
		try{
			util.buildUserProfile(userID,userProfile);
		}catch(Exception ex){
			logger.fatal("Error in building the user Profile"+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(),ex);
		}

		return userProfile;
	}
	/**
	 * This method is for updating the user - called on  Edit Submit the user   
	 * @param userProfile
	 * @param nbsSecObj
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public void setUser(UserProfile userProfile, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException,	NEDSSSystemException {

		if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"You do not have Security Administration permission.");
		}
		try {
			SecureUtil util = new SecureUtil();
			AuthUserDT authUserDT= util.updateUser(nbsSecObj, userProfile.getTheUser());    			
			// Inserting the User Roles for that user
			util.updatePAAdminAndRolesForUser(userProfile, authUserDT);
		}catch (Exception ex){
			logger.fatal("Error in updating the user to Secure_User table in method :setUser()"+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(),ex);
		}
	}

	public void setPermissionSet(PermissionSet permissionSet,Long userId,
			NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}
		try{
			SecureUtil util = new SecureUtil();
			//insert to secure permission set  
			Long securePermissionSetUid = util.updatePermissionSet(permissionSet);
			//run through the array and create the business objects and operations that are present
			util.updateBusinessObjectsAndOperationsForPermissionSet(securePermissionSetUid, permissionSet.getBusinessObjectOperations(), userId);
		}catch(Exception e){
			logger.fatal("Error in updating the PermissionSet "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e); 
		}

	}
	  public void populateUserProfiles(String destinationFlag, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,
	  NEDSSSystemException{

    Collection<Object>  userColl = new ArrayList<Object> ();
    CachedDropDowns.resetCachedValues(NEDSSConstants.Nedss_Entry_Id);
    
    try {
      // Retrieve the user collection
	  userColl = getSecureUserDTList(nbsSecurityObj);

	  // process the user list
      processUserList(destinationFlag, userColl);
    }
    catch (Exception e) {
      logger.fatal("Error in populateUserProfiles: " + e.getMessage(),e);
      e.printStackTrace();
      throw new NEDSSSystemException(e.getMessage(),e);
    }
  }
  
  private void processUserList(String destinationFlag, Collection<Object>  users) 
  	throws Exception{
	try {
		UserProfileDAOImpl profileDAO = new UserProfileDAOImpl();
		
		if(destinationFlag.equalsIgnoreCase("ODS") || 
			destinationFlag.equalsIgnoreCase("ODS_AND_HTML")){

				// delete all the user profiles
				profileDAO.deleteUserProfiles();
				
				Iterator<Object> i = users.iterator();
				
				while(i.hasNext()){
					AuthUserDT secureUserDT = (AuthUserDT)i.next();
					
					// insert user profile record
					profileDAO.insertUserProfile(secureUserDT.getNedssEntryId()+"",
							secureUserDT.getUserFirstNm(),
							secureUserDT.getUserLastNm());
				}
		}

		if(destinationFlag.equalsIgnoreCase("HTML") || 
			destinationFlag.equalsIgnoreCase("ODS_AND_HTML")){

			BufferedWriter out = null; 
			try {
				String fileName = PropertyUtil.nedssDir + "UserProfile" + File.separator + "UserProfile.html" ;
				
				out = new BufferedWriter(new FileWriter(fileName));
				out.write("<html lang=\"en\">\n");
				out.write("<title>NBS User Profile</title>\n");
				out.write("<body><table border=\"2\">");
				out.write("\t<tr>\n");
				out.write("\t\t<th>Entry Id</th>\n");
				out.write("\t\t<th>First Name</th>\n");
				out.write("\t\t<th>Last Name</th>\n");
				out.write("\t</tr>\n");

				Iterator<Object> i = users.iterator();
				while(i.hasNext()){
					out.write("\t<tr>\n");
					AuthUserDT secureUserDT = (AuthUserDT)i.next();
					if(secureUserDT.getNedssEntryId() != null){
						out.write("\t\t<td>");
						out.write(secureUserDT.getNedssEntryId()+"");
						out.write("</td>\n");
					} else {
						out.write("\t\t<td></td>\n");
					}

					if(secureUserDT.getUserFirstNm() != null){
						out.write("\t\t<td>");
						out.write(secureUserDT.getUserFirstNm());
						out.write("</td>\n");
					} else {
						out.write("\t\t<td></td>\n");
					}

					if(secureUserDT.getUserLastNm() != null){
						out.write("\t\t<td>");
						out.write(secureUserDT.getUserLastNm());
						out.write("</td>\n");
					} else {
						out.write("\t\t<td></td>\n");
					}

					out.write("\t</tr>\n");
				}
				out.write("</table></body>\n");
				out.write("</html>\n");
			} catch (IOException e) {
			} finally {
					if(out != null){
						out.close();						
					}
			}
		}
	}
	catch (Exception e) {
	  logger.error("Error in getUserList: " + e.getMessage(),e);
	  e.printStackTrace();
	  throw new NEDSSSystemException(e.getMessage(), e);
	}
  }



  public int  updateUserIdWithNewKey( NBSSecurityObj nbsSecurityObj)
		  throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSAppException{
	  try {
		DbAuthDAOImpl dbAuthDAOImpl =  new DbAuthDAOImpl();
		  String newKey = propertyUtil.getSecureNewEncryptionKey();
		  String oldKey = propertyUtil.getSecureEncryptionKey();
			if(newKey==null) 
				throw new NEDSSAppException("Please check, there exists no updated key!");
			if(newKey.length()==0){
				//throw new NEDSSAppException("Please check, Key must be a combination of numbers and (or) charcters. Also key must have a length of 8 or 16.");
				logger.debug("The user will not be encrypted.");
			}
			else if(newKey.length()!=16){
				throw new NEDSSAppException("Please check, Key must be a combination of numbers and (or) charcters. Also key must have a length of 8 or 16.");
			}
			if(newKey.intern().equals(oldKey.intern()))
					throw new NEDSSAppException("Please check, you need a different key to update user Id with new secure Key.");
			int numberOfUsers =dbAuthDAOImpl.updateUserIdWithNewKey(newKey);
		  return numberOfUsers;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }
	
	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
	}
	private boolean authenticateUser(String userID) throws NEDSSSystemException {

		  try {
			if (NbsDbPropertyUtil.getUserDBCheck()) {
				  DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
				  if (secureDAOImpl.doesUserExist(userID, true) != null) {
					  return true;
				  }
				  else {
					  return false;
				  }
			  }
			  return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/**
	 * This method authenticate user and create user's security profile
	 * 
	 * @J2EE_METHOD -- Login
	 */
	public NBSSecurityObj Login(String userID) throws java.rmi.RemoteException,
			javax.ejb.EJBException, NEDSSSystemException {
		NBSSecurityObj nbsSecObj = null;
		try {
			if (this.authenticateUser(userID)) {
				SecureUtil secureUtil = new SecureUtil();
				nbsSecObj = secureUtil.getSecurityObjectForUserIdPassword(
						userID, null);
			}
			return nbsSecObj;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("NBSAuthEJB.login Error: "
					+ e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/**
	 * Insert a new Permission Set to NBS database
	 * @param securityObj and SecurePermissionSetDT
	 * @return UID if successful
	 * @throws EJBException, RemoteException
	 */	
	public  void createSecurePermissionSet(PermissionSet  permSet, Long userId,  NBSSecurityObj nbsSecurityObj) throws
	EJBException, RemoteException, NEDSSAppException
	{
		//user must be Master Security Admin and a Prog Area Admin
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		try {
			SecureUtil util = new SecureUtil();
			//insert to secure permission set  
			Long securePermissionSetUid = util.createPermissionSet(permSet, userId);
			//run through the array and create the business objects and operations that are present
			util.createBusinessObjectsAndOperationsForPermissionSet(securePermissionSetUid, permSet.getBusinessObjectOperations(), userId);   	 

		}
		catch(NEDSSSystemException se2) {
			String errorString = "NBSDbSecurityEJB.createSecurePermissionSet System Exception:  "+ se2.getMessage();
			logger.fatal(errorString,se2);
			throw new EJBException(errorString,se2);
		}
		catch(Exception e) {
			String errorString = "NBSDbSecurityEJB.createSecurePermissionSet General: :  "+ e.getMessage();
			logger.fatal(errorString,e);
			throw new EJBException(errorString,e);
		}

	} //CreateSecurePermissionSet
	
	/**
	 * This method add the user to the NBS Database
	 * @param userProfile
	 * @param nbsSecObj
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public void addUser(UserProfile userProfile, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException {
		if ( (!nbsSecurityObj.isUserMSA()) && (!nbsSecurityObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry but you do not have Security Administration permission.");
		}

		try {
			SecureUtil util = new SecureUtil();
			Long secureUserUid = util.createUser(nbsSecurityObj, userProfile.getTheUser());    			
			// Inserting the User Roles for that user
			util.createPAAdminAndRolesForUser(nbsSecurityObj,userProfile,secureUserUid);
		}catch (Exception ex){
			logger.fatal("Error in adding the user to Secure_User table in method :addUser()"+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * The NEDSS.property file was converted to a database table. Get the active values in the table.
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public Map<String, String> getConfigList() throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException {
		NBSConfigurationDAO nBSConfigurationDAO = new NBSConfigurationDAO();
		return(nBSConfigurationDAO.getConfigList());
	}
	
	/**
	 * 
	 * @param properties - java.util.Properties;
	 * @param preProd - prerun or production
	 * @param nbsSecObj
	 * @return Map<String,ArrayList<String> of errorList, updatedItemList and insertedItemList
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public Map<String,ArrayList<String>>  updateConfigListFromProperties(Properties properties, String preProd, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException {
		if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry, only users with Security administration permissions are allowed to update a Configuration item.");
		}
		Map<String,ArrayList<String>> returnMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> insertedItemList = new ArrayList<String>();
		ArrayList<String> updatedItemList = new ArrayList<String>();
		ArrayList<String> errorList = new ArrayList<String>();
		//get Id for last updated user
		Long entryId = new Long(Long.parseLong(nbsSecObj.getEntryID()));
		//get current configuration
		NBSConfigurationDAO nBSConfigurationDAO = new NBSConfigurationDAO();
		Map<String,String> currentConfig = nBSConfigurationDAO.getAllConfigList(); //get Active and Inactive
		Collection<Object> configDTColl = nBSConfigurationDAO.getConfigDTCollection(false);
		Iterator propIter = properties.keySet().iterator();
		while (propIter.hasNext()) {
			String propKey = (String) propIter.next();
			String propVal = properties.getProperty(propKey).trim();
			if (currentConfig.containsKey(propKey)) {
				String configVal = currentConfig.get(propKey);
				if (propVal.equals(configVal))
					continue;
				Integer nextVersionCtrlNbr = findNextVersionCtrlNbr(propKey, configDTColl);
				if (preProd.equalsIgnoreCase("production")) {
					try {
						String errFeedbackMsg = nBSConfigurationDAO.updateConfigValue(propKey, propVal, entryId, nextVersionCtrlNbr);
						if (errFeedbackMsg != null && !errFeedbackMsg.isEmpty())
							errorList.add(errFeedbackMsg);
						} catch (NEDSSDAOSysException e) {
							errorList.add("Error updating "+propKey+" to <" + propVal+ "> exception occurred:\n"+e.getMessage());
							continue;
						}
				} //if production, Note: if pretest we don't actually update
				updatedItemList.add(propKey);
			} else {
				if (preProd.equalsIgnoreCase("production")) {
					try {
						String errFeedbackMsg = nBSConfigurationDAO.insertMissingConfigValue(propKey, propVal, entryId);
						if (errFeedbackMsg != null && !errFeedbackMsg.isEmpty())
							errorList.add(errFeedbackMsg);
						} catch (NEDSSDAOSysException e) {
							errorList.add("Error inserting "+propKey+" of <" + propVal+ "> exception occurred:\n"+e.getMessage());
							continue;
						}
				} //if production, Note: if pretest we don't actually insert anything
				
				insertedItemList.add(propKey);
			}
		}
		if (!updatedItemList.isEmpty())
			returnMap.put("updatedItemList",updatedItemList);
		if (!insertedItemList.isEmpty())
			returnMap.put("insertedItemList",insertedItemList);
		if (!errorList.isEmpty())
			returnMap.put("errorList",errorList);
			
		return(returnMap);
	}

	/**
	 * Find the next version control nbr for a paticular key
	 * @param propKey
	 * @param configDTColl
	 * @return Next Version Control Number
	 */
	private Integer findNextVersionCtrlNbr(String propKey,
			Collection<Object> configDTColl) {
		Integer nextVersionCtrlNbr = new Integer(1);
		Iterator<Object> configCollIter = configDTColl.iterator();
		while (configCollIter.hasNext()) {
			NBSConfigurationDT configDT = (NBSConfigurationDT) configCollIter.next();
			if (configDT.getConfigKey() != null && configDT.getConfigKey().equals(propKey)) {
				if (configDT.getVersionCtrlNbr() != null)
					nextVersionCtrlNbr = new Integer(configDT.getVersionCtrlNbr().intValue() + 1);
				break;
			}
		}
		return nextVersionCtrlNbr;
	}
	
	//recreateNBSProperiesFileFromConfiguration
	/**
	 * This method is not used. It creates a NEDSS.properties file from NBS_configuration.
	 * It can be called by specifying recreate instead of updateFromProperty. This fact is hidden from the user.
	 * @param preProd
	 * @param nbsSecObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public Map<String,ArrayList<String>>  recreateNBSProperiesFileFromConfiguration(String preProd, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException {
		if ( (!nbsSecObj.isUserMSA()) && (!nbsSecObj.isUserPAA())) {
			throw new NEDSSSystemException(
					"Sorry, only users with Security administration permissions are allowed to create a new NEDSS.properties file from NBS_configuration.");
		}
		Map<String,ArrayList<String>> returnMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> createdList = new ArrayList<String>();
		ArrayList<String> errorList = new ArrayList<String>();
		//get Id for last updated user
		Long entryId = new Long(Long.parseLong(nbsSecObj.getEntryID()));
		//get current configuration
		NBSConfigurationDAO nBSConfigurationDAO = new NBSConfigurationDAO();
		Collection<Object> configDTColl = nBSConfigurationDAO.getConfigDTCollection(true);
		if (configDTColl.isEmpty()) {
			errorList.add("NBSConfiguration table is empty. Can't create NEDSS.properties from an empty table.");
		} else if (configDTColl.size() < 50) {
			errorList.add("NBSConfiguration table only has "+configDTColl.size()+"entries. Is this correct?? Aborting..");
		}
			
	    String nedssDir = new StringBuffer(System.getProperty("nbs.dir"))
		.append(File.separator)
				.toString()
					.intern();

	    String propertiesDir = new StringBuffer(nedssDir)
	    	.append("Properties")
	    		.append(File.separator)
	    			.toString()
	    				.intern();

	    String propertyFilePath= propertiesDir + "NEDSS.properties";
	    
	    if (checkSourceFile(propertyFilePath))
	    	logger.info("recreateNBSProperiesFileFromConfiguration: NEDSS.properties file exists");
	    if (createBackupFile(propertyFilePath))
	    	logger.info("recreateNBSProperiesFileFromConfiguration: backup NEDSS.properties file successfully created..");
	    else {
	    	errorList.add("Error creating NEDSS.properties backup file. Aborting.");
	    	return returnMap;
	    }
	    try {
	    	File propertyFile = new File(propertyFilePath);
			BufferedWriter outStream = new BufferedWriter(new FileWriter(propertyFile));
			Date date = new Date();
			String todaysDate= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
			outStream.write("#NEDSS.properties created from NBS_configuration table on "+todaysDate);
			outStream.newLine();
			outStream.newLine();
			Iterator configIter = configDTColl.iterator();
			while (configIter.hasNext()) {
				NBSConfigurationDT configDT = (NBSConfigurationDT) configIter.next();
				outStream.write(configDT.getConfigKey()+"="+(configDT.getConfigValue() == null ? "" : configDT.getConfigValue()));
				outStream.newLine();
			}
            if (outStream != null) {
            	outStream.flush();
            	outStream.close();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
		returnMap.put("errorList",errorList);
			
		return(returnMap);
	}
	/**
	 * This method is not used. It created a backup file of the current NEDSS.properties named NEDSS.properties_bak201702231445.			
	 * @param sourceFileName
	 * @return
	 */
	static Boolean createBackupFile(String sourceFileName) {
		
		Date date = new Date();
		String todaysDate= new SimpleDateFormat("yyyyMMddHHmm").format(date);
		String backupFileName = sourceFileName + "bkup"+todaysDate+".txt";
		try {
			Path file = Paths.get(sourceFileName).toRealPath();
			File backupFile = new File(backupFileName);
			if(!backupFile.exists()) {
				backupFile.createNewFile();
			}
			Path back = Paths.get(backupFileName).toRealPath();
			Files.copy(file, back, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
				logger.debug("Error: NEDSS.properties backup file "+backupFileName+" exception during create = "+e.getMessage());
				return false;
		}
		logger.debug("NEDSS.properties backup file "+backupFileName+" successfully created.");
	return true;
	}

	static Boolean checkSourceFile(String sourceFileName) {
		
		try {
			Path file = Paths.get(sourceFileName).toRealPath();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		}
	return true;
	}
	
}
