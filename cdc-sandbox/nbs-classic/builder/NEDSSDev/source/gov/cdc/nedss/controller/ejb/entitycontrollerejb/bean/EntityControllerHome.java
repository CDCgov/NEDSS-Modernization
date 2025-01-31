//
// -- Java Code Generation Process --

package gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;
/**
 * Title: EntityControllerHome Interface
 * Description: This is the home interface representing the life cycle methods of EntityControllerEJB.
 * EntityController is a session bean that is used to access entities(Person, organization,
 * material, EntityGroup, NonPersonLivingSubject) and their dependent objects(like person name, person race etc).
 * Copyright:    Copyright (c) 2002
 * Company: Computer Sciences Corporation
 * @version 1.0
 */
public interface EntityControllerHome extends javax.ejb.EJBHome
{

    /**
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     * @throws RemoteException
     * @throws CreateException
     * @roseuid 3BEFB6F0002F
     * @J2EE_METHOD  --  create
     */
    public EntityController create    ()
                throws java.rmi.RemoteException, javax.ejb.CreateException;
}