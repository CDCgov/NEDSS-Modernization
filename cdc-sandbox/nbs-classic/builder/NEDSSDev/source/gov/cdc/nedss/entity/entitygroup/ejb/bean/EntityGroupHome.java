package gov.cdc.nedss.entity.entitygroup.ejb.bean;
// -- Java Code Generation Process --
// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.entitygroup.vo.*;
/**
* Name:		EntityGroupHome.java
* Description:	This is the EntityGroupHome interface that is used for the creation/location
*               of EntityGroupEJB.  The client of this EJB uses these methods for
*               locating an EJB or to create new one.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @version	1.0
*/
public interface EntityGroupHome extends javax.ejb.EJBHome
{


    /**
     * Description: Called by the client to find an EJB bean instance, usually find by primary key.
     * @param entityGroupUid  Long
     * @return EntityGroup
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @roseuid 3BD4A7710229
     * @J2EE_METHOD  --  findByPrimaryKey

     */
    public EntityGroup findByPrimaryKey  (Long entityGroupUid)
                throws RemoteException,
                       FinderException,
                       EJBException,
                       NEDSSSystemException;

    /**
     * Description: Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     * @param entityGroupVO : the EntityGroupVO object
     * @return EntityGroup
     * @throws CreateException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws RemoteException
     * @roseuid 3BD4A7710279
     * @J2EE_METHOD  --  create
     */
    public EntityGroup create (EntityGroupVO entityGroupVO)
                throws
                CreateException,
                EJBException,
                NEDSSSystemException,
                RemoteException;
}
