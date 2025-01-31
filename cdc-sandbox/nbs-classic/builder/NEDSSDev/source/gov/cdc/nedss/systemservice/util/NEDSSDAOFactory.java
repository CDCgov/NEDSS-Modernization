/**
* Name:        NEDSSDAOFactory.java
* Description:    This class is used to instantiates a particular subclass implementing
*               the DAO methods based on the information obtained from the
*               deployment descriptor.
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Brent Chen & NEDSS Development Team
* @version    1.0
*/
package gov.cdc.nedss.systemservice.util;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.util.LogUtils;


public class NEDSSDAOFactory
{

    //For logging
    static final LogUtils logger = new LogUtils(NEDSSDAOFactory.class.getName());

    /**
     * A factory method used to obtain a data access object by name
     * @param aDAOClassName the name of the data access object (DAO)
     * @return an Object object representing the data access object to be returned
     * @throws NEDSSDAOSysException
     */
    public static Object getDAO(String aDAOClassName)
                         throws NEDSSDAOSysException
    {

        Object aDAO = null;

        try
        {
            aDAO = (Object)Class.forName(aDAOClassName).newInstance();
        }
        catch (Exception e)
        {
            logger.fatal(
                    "NamingException while" +
                    " getting DAO type in NEDSSDAOFactory.getDAO: \n", e);
            throw new NEDSSDAOSysException(e.getMessage());
        }

        return aDAO;
    }
} //end of NEDSSDAOFactory class