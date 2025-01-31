/**
* Name:		Home interface for NonPersonLivingSubjectHome Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/
package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.*;

public interface NonPersonLivingSubjectHome extends javax.ejb.EJBHome
{
    /**
     * @roseuid 3BD41C000383
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     * @param nonPersonLivingSubjectVO    NonPersonLivingSubjectVO
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSSystemException
     */

    public NonPersonLivingSubject create    (NonPersonLivingSubjectVO nonPersonLivingSubjectVO)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSAppException;
    /**
     * @roseuid 3BD41C00031F
     * @J2EE_METHOD  --  findByPrimaryKey
     * Called by the client to find an EJB bean instance, usually find by primary key.
     * @param primaryKey   Long
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSAppException
     */
    public NonPersonLivingSubject findByPrimaryKey (Long primaryKey)
          throws RemoteException, FinderException, EJBException, NEDSSAppException;

}