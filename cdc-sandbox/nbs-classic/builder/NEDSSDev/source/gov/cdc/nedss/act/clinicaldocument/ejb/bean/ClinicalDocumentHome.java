/**
* Name:		Home interface for ClinicalDocument Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.clinicaldocument.ejb.bean;


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
import gov.cdc.nedss.act.clinicaldocument.vo.*;
import gov.cdc.nedss.act.clinicaldocument.dt.*;

public interface ClinicalDocumentHome extends EJBHome
{
    /**
     * Creates a ClinicalDocument object.
     */
    public ClinicalDocument create(ClinicalDocumentVO pvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException;


    /**
     * Finds by the primary key (the person unique NEDSS ID).
     */
    public ClinicalDocument findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of EBClinicalDocumentHome interface
