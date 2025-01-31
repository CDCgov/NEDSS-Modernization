/**
* Name:		Remote interface for ClinicalDocument Enterprise Bean
* Description:	The bean is an entity bean for identifying a clinicaldocument
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.clinicaldocument.ejb.bean;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.clinicaldocument.vo.*;
import gov.cdc.nedss.act.clinicaldocument.dt.*;


public interface ClinicalDocument extends EJBObject
{
    /**
     * Gets and sets ClinicalDocumentVO containing parameters mapping to all clinicaldocument's BMP fields.
     */

    public ClinicalDocumentVO getClinicalDocumentVO() throws RemoteException;
    public void setClinicalDocumentVO(ClinicalDocumentVO pvo) throws RemoteException, NEDSSConcurrentDataException;

    /**
     * Gets ClinicalDocumentInfoVO containing parameters mapping to clinicaldocument table.
     */

    public ClinicalDocumentDT getClinicalDocumentInfo() throws RemoteException, NEDSSConcurrentDataException;;

    /**
     * Gets locators
     */
    public Collection<Object>  getLocators() throws RemoteException;


    /**
     * Gets a collection of clinicaldocument ids
     */
    public Collection<Object>  getClinicalDocumentIDs() throws RemoteException, NEDSSConcurrentDataException;;

}//end of ClinicalDocument interface
