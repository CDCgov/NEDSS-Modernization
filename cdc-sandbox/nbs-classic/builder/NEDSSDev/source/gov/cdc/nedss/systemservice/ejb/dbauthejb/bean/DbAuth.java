package gov.cdc.nedss.systemservice.ejb.dbauthejb.bean;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthPermSetDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserRoleDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.PermissionSet;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;

/**
 * Title: NBSDbSecurity
 * Description: Database User Security EJB Interface Interface
 * Copyright:    Copyright (c) 2011
 * Company: CSC
 * @author Greg Tucker
* @Company: 	SAIC
 * @author	Pradeep K Sharma/2012
 * @version since NBS Release 4.4.1
 */
public interface  DbAuth  extends EJBObject {
	public  Long createSecureUser(NBSSecurityObj nbsSecurityObj, AuthUserDT secureUserDT) throws EJBException, RemoteException;
	 public  void createSecurePermissionSet(PermissionSet  permSet, Long userId,  NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	public  Long createSecureBusinessObjectRight(NBSSecurityObj nbsSecurityObj, AuthBusObjRtDT secureBusinessObjectRightDT) throws EJBException, RemoteException;
	public  Long createSecureBusinessOperationRight(NBSSecurityObj nbsSecurityObj, AuthBusOpRtDT secureBusinessOperationRightDT) throws EJBException, RemoteException;
	public  Long createSecureUserRole(NBSSecurityObj nbsSecurityObj, AuthUserRoleDT secureUserRoleDT) throws EJBException, RemoteException;
	public  AuthUserDT selectSecureUser(NBSSecurityObj nbsSecurityObj, String userId) throws EJBException, RemoteException;
	public  NBSSecurityObj getSecurityObjectForUseridPassword(String userId, String password) throws EJBException, RemoteException;
	public  Collection<Object> getPermissionSetDTList(NBSSecurityObj nbsSecurityObj) throws	EJBException, RemoteException;
	public PermissionSet getPermissionSet(String permissionSetName,NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException;
    public  void updateSecurePermissionSet(AuthPermSetDT securePermissionSetDT,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	public  Collection<Object> getSecureUserDTList(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	public void addUser(UserProfile userProfile, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException;
	public Boolean doesUserExist(String userID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException;
	public java.util.Collection<Object> getPermissionSetList(NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException;
	public java.util.Collection<Object>  getPermissionSets(NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException;
	public UserProfile getUser(String userID, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException;
	public Boolean permissionSetNameExists(String permissionSetName, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException;
	public void setUser(UserProfile userProfile, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,	NEDSSSystemException;
	public void setPermissionSet(PermissionSet permissionSet, Long userId, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException;
	public void populateUserProfiles(String destinationFlag, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException;
	public int updateUserIdWithNewKey(NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSAppException;
	public NBSSecurityObj Login(String userID) throws java.rmi.RemoteException,  javax.ejb.EJBException, NEDSSSystemException;
	public Map<String, String> getConfigList() throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException;
	public Map<String,ArrayList<String>> updateConfigListFromProperties(Properties properties, String preProd, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException;
	public Map<String,ArrayList<String>> recreateNBSProperiesFileFromConfiguration(String preProd, NBSSecurityObj nbsSecObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSSystemException;

}

