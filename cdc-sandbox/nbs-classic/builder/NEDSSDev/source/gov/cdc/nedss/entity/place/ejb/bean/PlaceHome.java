//
// -- Java Code Generation Process --

package gov.cdc.nedss.entity.place.ejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.place.vo.*;
import gov.cdc.nedss.entity.place.dt.*;

public interface PlaceHome extends javax.ejb.EJBHome
{

    /**
     * Called by the client to find an EJB bean instance, usually find by primary key.
     * @roseuid 3BD0261C00E8
     * @J2EE_METHOD  --  findByPrimaryKey
     * @param primaryKey
	* @throws RemoteException
	* @throws FinderException
	* @throws EJBException
	* @throws NEDSSSystemException
     */
    public Place findByPrimaryKey(Long primaryKey)
		throws
		RemoteException,
		FinderException,
		EJBException,
		NEDSSSystemException;
    /**
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
	* @roseuid 3BD0261C0138
     * @J2EE_METHOD  --  create
     * @param placeVO
	* @throws RemoteException
	* @throws CreateException
	* @throws DuplicateKeyException
	* @throws EJBException
	* @throws NEDSSSystemException
     */
    public Place create(PlaceVO placeVO)
		throws
		RemoteException,
		CreateException,
		DuplicateKeyException,
		EJBException,
		NEDSSSystemException;
}
