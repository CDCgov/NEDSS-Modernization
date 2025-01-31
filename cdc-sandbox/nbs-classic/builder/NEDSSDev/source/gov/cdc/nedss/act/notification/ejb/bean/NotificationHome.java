//
// -- Java Code Generation Process --

package gov.cdc.nedss.act.notification.ejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.notification.dt.*;

public interface NotificationHome extends javax.ejb.EJBHome
{

    /**
     * @J2EE_METHOD  --  findByPrimaryKey
     * Called by the client to find an EJB bean instance, usually find by primary key.
     * @param Long primaryKey
     * @return Notification, a instance of notification
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Notification findByPrimaryKey( Long primaryKey )
          throws
		  	RemoteException,
			FinderException,
			EJBException,
			NEDSSSystemException;


    /**
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     * @param NotificationVO
     * @return Notification, a instance of notification
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Notification create( NotificationVO vo )
    	throws
			RemoteException,
			CreateException,
			DuplicateKeyException,
			EJBException,
			NEDSSSystemException;

}
