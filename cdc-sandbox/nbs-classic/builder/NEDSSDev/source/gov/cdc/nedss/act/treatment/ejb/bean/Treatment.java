/**
* Name:		    Remote interface for Treatment EJB
* Description:	    The bean is an entity bean for identifying a treatment
* Copyright:	    Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Nedss Development Team
* @version	    1.1
*/

package gov.cdc.nedss.act.treatment.ejb.bean;

import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface Treatment extends EJBObject
{

     /**
     * Gets and sets TreatmentVO containing parameters mapping to all treatment's BMP fields.
     */

    public TreatmentVO getTreatmentVO() throws RemoteException;
    public void setTreatmentVO(TreatmentVO pvo) throws RemoteException, NEDSSConcurrentDataException;


}