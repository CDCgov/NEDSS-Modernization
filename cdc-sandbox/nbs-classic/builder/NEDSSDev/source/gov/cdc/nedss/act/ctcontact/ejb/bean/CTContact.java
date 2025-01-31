/**
 * Title: CTContact  Class
 * Description: Remote Interface class for CTContactEJB 
 * Copyright:    Copyright (c) 2009
 * Company: CSC
 * @author Pradeep Sharma
 * @version 3.1
 */

package gov.cdc.nedss.act.ctcontact.ejb.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.ctcontact.vo.*;
/**
 *
 */
public interface CTContact extends javax.ejb.EJBObject
{
    public CTContactVO getCTContactVO    () throws java.rmi.RemoteException;
    public void setCTContactVO (CTContactVO ctcontactVO)
								throws java.rmi.RemoteException,
								NEDSSConcurrentDataException;
}