/*
 * Created on Jan 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.group.ejb.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.group.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author xzheng
 *
 * The business interface handling defined field and subform groups.
 *
 */
public interface DefinedFieldSubformGroupBusinessInterface {

	/** Retrieves the group associated with the business uid.
	 *
	 * @param businessUid The business object uid associated with the returned group.
	 * @param secObj The NBSSecurity object associated with the user.
	 * @return The group.
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup getGroup(Long businessUid, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

	/** The method that creates the business object and group relationship.
	 * If the group has not been persisted, it will be persisted too.
	 *
	 * @param businessUid The business object uid associated with the group.
	 * @param group The group.
	 * @param secObj The NBSSecurity object associated with the user.
	 * @return The business object group relationship uid.
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Long createBusinessObjectGroupRelationship(Long businessUid,  List<Object> ldfUids, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

	public void createBusinessObjectGroupRelationship(List<Object>businessUids,  List<Object> ldfUids, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

	public Long createBusinessObjectGroupRelationship(Long businessUid,  Long groupUid, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

	/** The method that updates the business object and group relationship.
	 * If the group has not been persisted, it will be persisted too.
	 *
	 * @param businessUid The business object uid associated with the group.
	 * @param group The group.
	 * @param secObj The NBSSecurity object associated with the user.
	 * @return The business object group relationship uid.
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public void updateBusinessObjectGroupRelationship(Long businessUid,  List<Object> ldfUids, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

	public void updateBusinessObjectGroupRelationshipWithGroupUid(Long businessUid,  Long groupUid, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

	public Long createGroup(List<Object>ldfUids, NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException;

}
