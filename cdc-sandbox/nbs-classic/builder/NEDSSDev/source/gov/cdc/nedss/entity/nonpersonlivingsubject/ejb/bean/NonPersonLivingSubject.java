/**
* Name:		Remote interface for NonPersonLivingSubject Enterprise Bean
* Description:	The bean is an entity bean for identifying a NonPersonLivingSubject
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean;

// Import Statements
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import javax.ejb.*;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.*;
import gov.cdc.nedss.exception.*;

public interface NonPersonLivingSubject extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3BD49B3D0173
     * This method is used to retrieve a nonPersonLivingSubject's NonPersonLivingSubjectVO.
     * @J2EE_METHOD  --  getNonPersonLivingSubjectVO
     * @throws java.rmi.RemoteException
     */
  public NonPersonLivingSubjectVO getNonPersonLivingSubjectVO    () throws java.rmi.RemoteException;

    /**
     * @roseuid 3BD49B5F01F4
     * This method is used to store the data in a NonPersonLivingSubjectVO to the database.
     * @J2EE_METHOD  --  setNonPersonLivingSubjectVO
     * @param nonPersonLivingSubjectVO    the NonPersonLivingSubjectVO
     * @throws java.rmi.RemoteException
     * @throws NEDSSConcurrentDataException
     */
    public void setNonPersonLivingSubjectVO
                (NonPersonLivingSubjectVO nonPersonLivingSubjectVO)
                 throws java.rmi.RemoteException,
                 NEDSSConcurrentDataException;
}