package gov.cdc.nedss.entity.person.ejb.bean;


import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import java.rmi.RemoteException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;
import javax.ejb.*;
import java.util.Collection;
import java.util.Date;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.exception.*;



/**
 * Name:		Home interface for Person Enterprise Bean
 * Description:	The bean is an entity bean
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 *
 * @author Brent Chen & NEDSS Development Team
 * @version 1.0
 */
public interface PersonHome extends EJBHome
{
    /**
     * This method is used to create a new personEJB object.
     *
     * @exception RemoteException
     * @exception CreateException
     * @exception DuplicateKeyException
     * @exception EJBException
     * @exception NEDSSSystemException
     * @param pvo PersonVO
     * @return Person
     */
    public Person create(PersonVO pvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException;


    /**
     * This method is used to find a PersonEJB object by its primary key.
     *
     * @exception RemoteException
     * @exception FinderException
     * @exception EJBException
     * @exception NEDSSSystemException
     * @param pk Long
     * @return Person
     */
    public Person findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

    /**
     * Will return a collection of remote objects where the person_parent_uid is
     * associated with each remote object in the collection.
     * @param personParentUid Long
     * @return Collection<Object>
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
	public Collection<Object> findByPersonParentUid(Long personParentUid) throws RemoteException, FinderException, EJBException, NEDSSSystemException;

        /**
         *Will return a collection of remote objects where the groupNbr is
         * associated with each remote object in the collection.
         * @param groupNbr
         * @return
         * @throws RemoteException
         * @throws FinderException
         * @throws EJBException
         * @throws NEDSSSystemException
         */
    public Collection<Object> findByGroup(Long groupNbr) throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of EBPersonHome interface
