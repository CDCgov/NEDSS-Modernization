/**
* Name:		Remote interface for Workup Enterprise Bean
* Description:	The bean is an entity bean for identifying a workup
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.file.ejb.bean;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.file.vo.*;
import gov.cdc.nedss.act.file.dt.*;
import gov.cdc.nedss.util.*;



public interface Workup extends EJBObject
{
    /**
     * Gets and sets WorkupVO containing parameters mapping to all workup's BMP fields.
     */

    public WorkupVO getWorkupVO() throws RemoteException;
    public void setWorkupVO(WorkupVO pvo) throws RemoteException;

    /**
     * Gets WorkupInfoVO containing parameters mapping to workup table.
     */

    public WorkupDT getWorkupInfo() throws RemoteException;

    /**
     * Gets locators
     */
    public Collection<Object>  getLocators() throws RemoteException;


    /**
     * Gets a collection of workup ids
     */
    public Collection<Object>  getWorkupIDs() throws RemoteException;

}//end of Workup interface
