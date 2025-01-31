/**
* Name:		    Home interface for Interview Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2013
* Company: 	    Leidos
* @author	    NEDSS Development Team
* @version	    1.0
*/
package gov.cdc.nedss.act.interview.ejb.bean;


import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.interview.vo.InterviewVO;


public interface InterviewHome extends EJBHome
{

    /**
     * Creates a Interview object.
     */
    public Interview create(InterviewVO pvo)
          throws RemoteException, CreateException,
          NEDSSSystemException;


    /**
     * Finds by the primary key (the Interview unique NEDSS ID).
     */
    public Interview findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of EJBInterviewHome interface
