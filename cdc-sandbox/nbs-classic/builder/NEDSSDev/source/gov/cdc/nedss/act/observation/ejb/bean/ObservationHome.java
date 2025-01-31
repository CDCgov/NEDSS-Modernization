/**
 * Title: ObservationHome class
 * Description: Home Interface of Observation Enterprise bean of type Act.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author nmallela
 * @version 1.0
 */
package gov.cdc.nedss.act.observation.ejb.bean;

import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.observation.vo.*;
/**
 *
 */
public interface ObservationHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3BD05AEA014F
     * @J2EE_METHOD  --  findByPrimaryKey
     * Called by the client to find an EJB bean instance, usually find by primary key.
     * @param java.lang.Long primaryKey - long value used to find EJB instance
     * @return Observation - remote reference  returned by the method
     * @throws RemoteException, FinderException, EJBException, NEDSSSystemException
     */
    public Observation findByPrimaryKey    (Long primaryKey)
                throws RemoteException, FinderException, EJBException, NEDSSSystemException;

    /**
     * @roseuid 3BD05AEA019F
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...)
     * @param ObservationVO obVO
     * @return Observation - remote reference  returned by the method
     * @throws java.rmi.RemoteException, javax.ejb.CreateException, NEDSSSystemException
     */
    public Observation create    (ObservationVO obVO)
                throws java.rmi.RemoteException, javax.ejb.CreateException, NEDSSSystemException;
}
