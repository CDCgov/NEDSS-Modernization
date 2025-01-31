package gov.cdc.nedss.entity.entitygroup.ejb.bean;


import java.rmi.RemoteException;

import javax.ejb.EJBObject;
import javax.ejb.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.entitygroup.vo.*;
/**
* Name:		EntityGroup.java
* Description:	This is the EntityGroup interface that is used for the inserting/reading/updating/deleting
*               of EntityGroup records in the database.  This class shows all the business methods
*               that a client of this EJB can use.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @version	1.0
*/



public interface EntityGroup extends javax.ejb.EJBObject
{

  /**
     * Description: This method sets(creates or updates) the EntityGroupVO. This method is called
     * by the clients of entityGroupEJB.
     * @param entityGroupVO  EntityGroupVO
     * @throws RemoteException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BFA873201C2
     * @J2EE_METHOD  --  setEntityGroup
     *
     */
    public void setEntityGroupVO    (EntityGroupVO entityGroupVO) throws java.rmi.RemoteException, NEDSSConcurrentDataException;

  /**
     * Description: This method gets the EntityGroupVO object. This method is called by the clients
     * of entityGroupEJB.
     * @return EntityGroupVO
     * @throws RemoteException
     * @roseuid 3BFA87AB009A
     * @J2EE_METHOD  --  getEntityGroupVO
     */
    public EntityGroupVO getEntityGroupVO () throws java.rmi.RemoteException;
}