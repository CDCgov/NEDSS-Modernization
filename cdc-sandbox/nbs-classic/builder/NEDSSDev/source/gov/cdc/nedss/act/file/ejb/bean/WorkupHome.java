/**
* Name:		Home interface for Workup Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.file.ejb.bean;


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
import gov.cdc.nedss.act.file.vo.*;
import gov.cdc.nedss.act.file.dt.*;
import gov.cdc.nedss.util.*;




public interface WorkupHome extends EJBHome
{
    /**
     * Creates a Workup object.
     */
    public Workup create(WorkupVO pvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException;


    /**
     * Finds by the primary key (the person unique NEDSS ID).
     */
    public Workup findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of EBWorkupHome interface
