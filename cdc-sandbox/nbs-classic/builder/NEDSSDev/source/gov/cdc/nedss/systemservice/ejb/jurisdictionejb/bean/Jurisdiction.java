/**
 * Title:        Jurisdiction
 * Description:  NEDSS Jursidiction Session Bean Remote Interface
 *
 * Copyright:    Copyright (c) 2003
 * Company: 	 Computer Sciences Corporation
 * @author       12/28/2001 Chris Hanson & NEDSS Development Team
 * @modified     12/28/2001 Chris Hanson
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean;

// Import Statements
import java.rmi.*;
import javax.ejb.*;
import java.util.*;

// gov.cdc.nedss.* imports
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public interface Jurisdiction extends EJBObject {

   /**
	* Resolves what the Jurisdiction is for a give subject or provider for a the stated lab report.
	*
	* @exception RemoteException EJB Remote Exception
	*/
	public HashMap<Object,Object> resolveLabReportJurisdiction(PersonVO subject, PersonVO provider) throws RemoteException;

   /**
	* Sets the log level.
	*
	* @exception RemoteException EJB Remote Exception
	*/
	public Collection<Object>  findJurisdictionForPatient(PersonVO subject) throws RemoteException;

   /**
	* Sets the log level.
	*
	* @exception RemoteException EJB Remote Exception
	*/
	public Collection<Object>  findJurisdictionForProvider(PersonVO provider) throws RemoteException;

    /**
     * Sets the log level.
     *
     * @exception RemoteException EJB Remote Exception
     */
    //Added by CD specifically for DM , should be taken out when the method above this is adjusted
    //with NBSecObj... - CD 10/31/2003
    public Collection<Object>  dmFindJurisdictionForPatient(PersonVO subject, NBSSecurityObj nbsSecurityObj) throws RemoteException;
}
	