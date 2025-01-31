package gov.cdc.nedss.proxy.ejb.entityproxyejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;
/**
 * Title:        EntityProxyHome.java
 * Description:  This is Home Interface for EntityProxyEJB.
 * Copyright:    Copyright (c) 2001
 * Company:      csc
 * @author:      Nedss Development Team
 */
public interface EntityProxyHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3C4C47810247
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public EntityProxy create    ()
                throws java.rmi.RemoteException, javax.ejb.CreateException;
}