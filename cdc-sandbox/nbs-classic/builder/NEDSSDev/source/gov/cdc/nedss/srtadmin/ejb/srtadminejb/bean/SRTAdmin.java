package gov.cdc.nedss.srtadmin.ejb.srtadminejb.bean;


import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;

/**
 * 
 * @author nmallela
 *
 */
public interface SRTAdmin extends EJBObject
{

	/**
	 * 
	 * @param daoName
	 * @param sMethod
	 * @param oParams
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws EJBException
	 * @throws NEDSSSystemException
	 * @throws FinderException
	 * @throws CreateException
	 */
    public Collection<Object>  processSRTAdminRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException;
    public Boolean checkVADSInSystem( CodeSetDT dt, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public void importValueSet( PhinVadsSystemVO phinVadsVo, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException;
}
