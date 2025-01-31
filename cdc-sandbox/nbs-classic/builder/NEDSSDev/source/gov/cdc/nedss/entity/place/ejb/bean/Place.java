//
// -- Java Code Generation Process --

package gov.cdc.nedss.entity.place.ejb.bean;

// Import Statements
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.place.vo.*;
import gov.cdc.nedss.entity.place.dt.*;

public interface Place extends javax.ejb.EJBObject
{
    /**
     *  get the placeVO
	*  @throws RemoteException
     */
    public PlaceVO getPlaceVO()
      throws
        RemoteException;
    /**
     *  set the placeVO
	*  @throws RemoteException
	*  @throws NEDSSConcurrentDataException
     */
    public void setPlaceVO(PlaceVO placeVO)
      throws
      RemoteException, NEDSSConcurrentDataException;

    /**
     *  get the placeDT
	*  @throws RemoteException
     */

    public PlaceDT getPlaceInfo()
      throws
        RemoteException;
}