/**
 * Title: CTContactEJB  Class
 * Description: Home interface class for CTContactEJB
 * Copyright:    Copyright (c) 2009
 * Company: CSC
 * @author Pradeep Sharma
 * @version 3.1
 */

package gov.cdc.nedss.act.ctcontact.ejb.bean;

import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
public interface CTContactHome extends javax.ejb.EJBHome
{
    public CTContact findByPrimaryKey    (Long primaryKey)
                throws RemoteException, FinderException, EJBException, NEDSSSystemException;
    public CTContact create    (CTContactVO obVO)
                throws java.rmi.RemoteException, javax.ejb.CreateException, NEDSSSystemException;
}
