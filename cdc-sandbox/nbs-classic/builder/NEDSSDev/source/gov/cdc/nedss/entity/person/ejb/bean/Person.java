/**
* Name:		Remote interface for Person Enterprise Bean
* Description:	The bean is an entity bean for identifying a person
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.person.ejb.bean;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.exception.*;


/**
 * Name:		Remote interface for Person Enterprise Bean
 * Description:	The bean is an entity bean for identifying a person
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 *
 * @author Brent Chen & NEDSS Development Team
 * @version 1.0
 */
public interface Person extends EJBObject
{
    /**
     * This method is used to retrieve a person's PersonVO.
     *
     * @exception RemoteException
     * @return PersonVO
     */
    public PersonVO getPersonVO() throws RemoteException;

    /**
     * This method is used to store the data in a PersonVO to the database.
     *
     * @param pvo PersonVO
     * @exception RemoteException
     * @exception NEDSSConcurrentDataException
     */
    public void setPersonVO(PersonVO pvo) throws RemoteException, NEDSSConcurrentDataException;

    /**
     * This method is used to retrieve a person's personDT info.
     *
     * @exception RemoteException
     * @return PersonDT
     */
    public PersonDT getPersonInfo() throws RemoteException;

    /**
     * This method is used to retrieve a person's personNames.
     *
     * @exception RemoteException
     * @return Collection
     */
    public Collection<Object>  getPersonNames() throws RemoteException;

    /**
     * This method is used to retrieve a person's personRaces.
     *
     * @exception RemoteException
     * @return Collection
     */
    public Collection<Object>  getPersonRaces() throws RemoteException;

    /**
     * This method is used to retrieve a person's locators.  ie Tele, postal.
     *
     * @exception RemoteException
     * @return Collection
     */
    public Collection<Object>  getLocators() throws RemoteException;

    /**
     * This method is used to retrieve a person's EthnicGroups.
     *
     * @exception RemoteException
     * @return Collection
     */
    public Collection<Object>  getPersonEthnicGroups() throws RemoteException;

    /**
     * This method is used to retrieve a person's EntityIDs.
     *
     * @exception RemoteException
     * @return Collection
     */
    public Collection<Object>  getPersonIDs() throws RemoteException;

	

}//end of Person interface
