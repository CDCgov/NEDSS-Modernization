//
/**
* Name:		Home interface for Organization Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2002
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.organization.ejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.exception.*;

public interface OrganizationHome extends javax.ejb.EJBHome
{
   /**
   * @roseuid 3BCF43120021
   * @J2EE_METHOD  --  create
   * Called by the client to create an EJB bean instance. It requires a matching pair in
   * the bean class, i.e. ejbCreate(...).
   * @param organizationVO - the OrganizationVO
   * @return Organization     the Organization
   * @throws RemoteException
   * @throws CreateException
   * @throws DuplicateKeyException
   * @throws EJBException
   * @throws NEDSSSystemException
   */
    public Organization create(OrganizationVO organizationVO)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException;
    /**
     * @roseuid 3BCF431103AF
     * @J2EE_METHOD  --  findByPrimaryKey
     * Called by the client to find an EJB bean instance, usually find by primary key.
     * @param primaryKey
     * @return Organization
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Organization findByPrimaryKey(Long primaryKey)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}
