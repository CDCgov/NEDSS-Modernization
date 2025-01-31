/**
* Name:		    Remote interface for Intervention EJB
* Description:	The bean is an entity bean for identifying a intervention
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma
* @version	    1.0
*/
package gov.cdc.nedss.act.intervention.ejb.bean;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.act.intervention.dt.*;



public interface Intervention extends EJBObject
{

     /**
     * Gets and sets InterventionVO containing parameters mapping to all intervention's BMP fields.
     */

    public InterventionVO getInterventionVO() throws RemoteException;
    public void setInterventionVO(InterventionVO pvo) throws RemoteException, NEDSSConcurrentDataException;


}