/*
 * Created on Jan 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package gov.cdc.nedss.ldf.group.ejb.bean;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.group.*;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.rmi.*;
import java.util.*;

import javax.ejb.*;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldSubformGroupEJB
	implements SessionBean, DefinedFieldSubformGroupBusinessInterface {

	//For logging
	static final LogUtils logger =
		new LogUtils(DefinedFieldSubformGroupEJB.class.getName());
	private SessionContext cntx;

	public DefinedFieldSubformGroupEJB() {
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
		this.cntx = sc;
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#getGroup(java.lang.Long, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup getGroup(Long businessUid, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			return helper.getGroup(businessUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#setBusinessObjectGroupRelationship(java.lang.Long, gov.cdc.nedss.ldf.group.DefiendFieldSubformGroup, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public Long createBusinessObjectGroupRelationship(Long businessUid, List<Object> ldfUids, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			return helper.createBusinessObjectGroupRelationship(businessUid, ldfUids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#setBusinessObjectGroupRelationship(java.lang.Long, gov.cdc.nedss.ldf.group.DefiendFieldSubformGroup, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public void createBusinessObjectGroupRelationship(List<Object>businessUids, List<Object> ldfUids, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			if(businessUids != null) {
				Iterator keyIter = keyIter = businessUids.iterator();
				while (keyIter.hasNext()) {
					createBusinessObjectGroupRelationship((Long) keyIter.next(), ldfUids, secObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#updateBusinessObjectGroupRelationship(java.lang.Long, java.util.List, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public void updateBusinessObjectGroupRelationship(Long businessUid, List<Object> ldfUids, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			helper.updateBusinessObjectGroupRelationship(businessUid, ldfUids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}


	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#createBusinessObjectGroupRelationship(java.lang.Long, java.lang.Long, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public Long createBusinessObjectGroupRelationship(Long businessUid, Long groupUid, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			return helper.createBusinessObjectGroupRelationship(businessUid, groupUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#updateBusinessObjectGroupRelationshipWithGroupUid(java.lang.Long, java.lang.Long, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public void updateBusinessObjectGroupRelationshipWithGroupUid(Long businessUid, Long groupUid, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			helper.updateBusinessObjectGroupRelationship(businessUid, groupUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}

	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupBusinessInterface#createGroup(java.util.List, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
	 */
	public Long createGroup(List<Object>ldfUids, NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
		try {
			DefinedFieldSubformGroupHelper helper = new DefinedFieldSubformGroupHelper();
			return helper.createGroup(ldfUids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}

	}

}
