/**
 * Title: Observation class
 * Description: Remote Interface of Observation Enterprise bean of type Act.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author nmallela
 * @version 1.0
 */
package gov.cdc.nedss.act.observation.ejb.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

import java.rmi.RemoteException;

import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
/**
 *
 */
public interface Observation extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3BD84738002B
     * @J2EE_METHOD  --  getObservationVO
     * A public method that returns the ObservationVO
     * @return ObservationVO - fetches the value object ObservationVO
     * @throws java.rmi.RemoteException
     */
    public ObservationVO getObservationVO    () throws java.rmi.RemoteException;

    	
    /**
     * @roseuid 3BD847F101D5
     * @J2EE_METHOD  --  setObservationVO
     * A public method that takes a new / dirty ObservationVO as parameter and Creates / Updates it.
     * @param ObservationVO - observationVO either new or dirty
     * @throws java.rmi.RemoteException, NEDSSConcurrentDataException
     */
    public void setObservationVO (ObservationVO observationVO)
								throws java.rmi.RemoteException,
								NEDSSConcurrentDataException;
}