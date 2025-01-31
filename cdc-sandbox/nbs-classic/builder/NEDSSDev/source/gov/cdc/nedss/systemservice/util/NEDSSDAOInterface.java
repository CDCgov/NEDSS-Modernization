/**
* Name:        NEDSSDAOInterface.java
* Description:    This is the interface for all DAO objects used in NEDSS.
*               The class is a data access object for accessing data storage.
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Brent Chen & NEDSS Development Team
* @version    1.0
*/
package gov.cdc.nedss.systemservice.util;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DisplayList;

import java.util.Collection;


public interface NEDSSDAOInterface
{

    /**
     * A method to be implemented for inserting a data record into database
     * @param obj the object to be inserted
     * @return a long uid
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public long create(Object obj)
                throws NEDSSDAOSysException, NEDSSSystemException,
                       NEDSSSystemException;

    /**
     * A method to be implemented for inserting data records into database
     * @param coll the objects to be inserted
     * @return a long uid
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public long create(Collection<Object> coll)
                throws NEDSSDAOSysException, NEDSSSystemException,
                       NEDSSSystemException;

    /**
     * A method to be implemented for inserting a data record into database
     * @param aUID a long uid
     * @param obj the object to be inserted
     * @return a long uid
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public long create(long aUID, Object obj)
                throws NEDSSDAOSysException, NEDSSSystemException,
                       NEDSSSystemException;

    /**
     * A method to be implemented for inserting data records into database
     * @param aUID a long uid
     * @param coll the objects to be inserted
     * @return  a long uid
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public long create(long aUID, Collection<Object>  coll)
                throws NEDSSDAOSysException, NEDSSSystemException,
                       NEDSSSystemException;

    /**
     * A method to be implemented for retrieving a data record from database
     * @param aUID a long representing a uid
     * @return an Object object
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Object loadObject(long aUID)
                      throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A method to be implemented for retrieving data records from database
     * @param aUID a long representing a uid
     * @return a Collection<Object>  of data record objects
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object>  load(long aUID)
                    throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A method to be implemented for updating a data record to database
     * @param obj an Object object to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A method to be implemented for updating a data record to database
     * @param aUID a long representing an uid
     * @param obj an Object object to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(long aUID, Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A method to be implemented for updating data records to database
     * @param coll a Collection<Object>  of data records to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Collection<Object> coll)
               throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A method to be implemented for updating data records to database
     * @param aUID a long representing an uid
     * @param coll a Collection<Object>  of data records to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(long aUID, Collection<Object>  coll)
               throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A method to be implemented for deleting data records from database
     * @param aUID a long representing a uid
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void remove(long aUID)
                throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A finder method to be implemented for finding data records based on a primary key
     * @param pk a long number representing a primary key
     * @return a Long object representing the primary key for the found data record
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long pk)
                          throws NEDSSDAOSysException, NEDSSSystemException;

    /**
     * A finder method to be implemented for finding data records based on some key words
     * @param obj the object containing the search criteria (key words)
     * @param cacheNumber an int number defining the number of records returned
     * @param fromIndex an int number defined the starting index of the returned data records
     * @return a List<Object> object containing the data record returned
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public DisplayList findByKeyWords(Object obj, int cacheNumber,
                                      int fromIndex)
                               throws NEDSSDAOSysException,
                                      NEDSSSystemException;
} //end of NEDSSDAOInterface class