/**
 * Title: Session EJB Class for uidgenerator.
 * Description: This class is the EJB for Uidgenerator session bean.
 * Copyright: Copyright (c) 2001
 * Company: csc
 * @author: every one in development :)
 */

package gov.cdc.nedss.systemservice.ejb.uidgenerator.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;

import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;
import javax.ejb.*;
import javax.rmi.*;
import javax.naming.*;

public class UidgeneratorEJB  extends BMPBase implements SessionBean
{

    static final LogUtils logger = new LogUtils(UidgeneratorEJB.class.getName());
    private SessionContext cntx;

    public UidgeneratorEJB() {
    }

    /**
     *
     * @param cntx
     */
    public void setSessionContext(SessionContext cntx) {
        this.cntx = cntx;
    }

    /**
     */
    public void ejbActivate() {
    }

    /**
     *
     * @throws RemoteException
     * @throws CreateException
     */
    public void ejbCreate()
                   throws RemoteException, CreateException {
    }

    /**
     */
    public void ejbPassivate() {
    }

    /**
     */
    public void ejbRemove() {
        cntx = null;
    }

   /**
    * @param theClass
    * @param cacheCount
    * @return java.lang.String[]
    * @throws java.lang.Exception
    */
   public HashMap<Object,Object> getLocalID(String theClass, short cacheCount) throws Exception, RemoteException, CreateException
   {
        String strID = null;
        Connection dbConnection = null;
        CallableStatement callableStmt = null;
        HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
        StringUtils parser = new StringUtils();

        try
        {
            dbConnection = getConnection();
            callableStmt = dbConnection.prepareCall("{call GetUid(?, ?, ?, ?, ?, ?, ?)}");

            logger.debug("Register the output parameters for " + theClass + " at an increment of " + cacheCount);
            int i = 1;

            callableStmt.setString(i++, theClass);
            callableStmt.setShort(i++, cacheCount);

            callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);

            boolean result = callableStmt.execute();

            hashMap.put("currentUID", callableStmt.getString(3));
            hashMap.put("maxUID", callableStmt.getString(4));
            hashMap.put("uidPrefixCd", callableStmt.getString(6));
            hashMap.put("uidSuffixCd", callableStmt.getString(7));

            return hashMap;
        }
        catch(Exception ex)
        {
            logger.fatal("Error while initializing a UID tracker for " + theClass + ", try again..."+ex.getMessage(), ex);
            throw new Exception(ex.getMessage(),ex);
        }
        finally
        {
            closeCallableStatement(callableStmt);
            releaseConnection(dbConnection);
        }
   }

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String test() throws RemoteException
    {
        return ("All looks fine.");
    }
}