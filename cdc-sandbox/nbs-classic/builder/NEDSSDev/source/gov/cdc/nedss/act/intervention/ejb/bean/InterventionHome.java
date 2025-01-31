/**
* Name:		    Home interface for Intervention Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma & NEDSS Development Team
* @version	    1.0
*/
package gov.cdc.nedss.act.intervention.ejb.bean;


import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import java.rmi.RemoteException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;
import javax.ejb.*;
import java.util.Collection;
import java.util.Date;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.act.intervention.dt.*;


public interface InterventionHome extends EJBHome
{

    /**
     * Creates a Intervention object.
     */
    public Intervention create(InterventionVO pvo)
          throws RemoteException, CreateException,
          NEDSSSystemException;


    /**
     * Finds by the primary key (the Intervention unique NEDSS ID).
     */
    public Intervention findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of EJBInterventionHome interface
