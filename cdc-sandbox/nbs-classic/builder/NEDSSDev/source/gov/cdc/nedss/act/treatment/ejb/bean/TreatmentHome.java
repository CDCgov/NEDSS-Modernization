/**
* Name:		    Home interface for Treatment Enterprise Bean
* Description:	    The bean is an entity bean
* Copyright:	    Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Nedss Development Team
* @version	    1.1
*/

package gov.cdc.nedss.act.treatment.ejb.bean;

import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


public interface TreatmentHome extends EJBHome
{

    /**
     * Creates a Treatment object.
     */
    public Treatment create(TreatmentVO tvo)
          throws RemoteException, CreateException,
          NEDSSSystemException;


    /**
     * Finds by the primary key (the Treatment unique NEDSS ID).
     */
    public Treatment findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of TreatmentEJB Home interface
