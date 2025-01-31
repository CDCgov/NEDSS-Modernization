/**
 * Title:        Material
 * Description:  Material Interface
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

import java.rmi.RemoteException;
import gov.cdc.nedss.exception.*;

import javax.ejb.EJBObject;
import javax.ejb.*;



// gov.cdc.nedss.* imports
import gov.cdc.nedss.entity.material.vo.*;

public interface Material extends javax.ejb.EJBObject {

    /**
     * @roseuid 3BD6BD7402BF
     * @J2EE_METHOD  --  setMaterialVO
     *
     * Sets the value of materialVO
     *
     * @return            Uid of the Material value object that was set
     *
     */
    public void setMaterialVO (MaterialVO materialVO)
      throws
        RemoteException,NEDSSConcurrentDataException;

    /**
     * @roseuid 3BD6BD9002CA
     * @J2EE_METHOD  --  getMaterialVO
     *
     * Gets the value of materialVO
     *
     * @return            Material value object that was set
     *
     * @exception RemoteException If a remote exception happens.
     *
     */
    public MaterialVO getMaterialVO ()
      throws
        RemoteException;
}