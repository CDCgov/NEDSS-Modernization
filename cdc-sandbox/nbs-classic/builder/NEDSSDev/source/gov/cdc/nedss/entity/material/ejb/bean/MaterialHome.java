/**
 * Title:        MaterialEJB
 * Description:  Material Entity EJB
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       11/06/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     11/16/2001 Sohrab Jahani
 * @version      1.0.0
 */

//
// Original code was made by:
// -- Java Code Generation Process --

package gov.cdc.nedss.entity.material.ejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.material.vo.*;


public interface MaterialHome extends javax.ejb.EJBHome {

    /**
     * @roseuid 3BD4A60D0173
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     *
     * @exception FinderException EJB Finder Exception
     * @exception RemoteException EJB Remote Exception
     */
    public Material create (MaterialVO materialVO)
      throws
        CreateException,
        DuplicateKeyException,
        EJBException,
        NEDSSSystemException,
        RemoteException;


    /**
     * @roseuid 3BD4A60D0119
     * @J2EE_METHOD  --  findByPrimaryKey
     * Called by the client to find an EJB bean instance, usually find by primary key.
     *
     * @param materialUid Primary Key to search for Material
     *
     * @exception FinderException EJB Finder Exception
     * @exception RemoteException EJB Remote Exception
     */
    public Material findByPrimaryKey (Long materialUid)
      throws  RemoteException,
              FinderException,
              EJBException,
              NEDSSSystemException;

}
