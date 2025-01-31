package gov.cdc.nedss.systemservice.ejb.nbschartsejb.bean;


import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class NBSChartEJB implements SessionBean
{
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(NBSChartEJB.class.getName());
	 
    public NBSChartEJB()
    {
    }

    public void setSessionContext(SessionContext cntx) throws EJBException, RemoteException
    {
    }

    public void ejbActivate()
    {
    }

    public void ejbCreate() throws RemoteException, CreateException
    {
    }
    public void ejbPassivate()
    {
    }

    public void ejbRemove()
    {
        logger.error("NBSChartEJB is removed");
    }	
	
    /**
     * processChartRequest makes appropriate DAO Call and returns the Result as an Object.
     * @param daoName
     * @param sMethod
     * @param oParams
     * @param nbsSecurityObj
     * @return java.lang.Object
     * @throws NEDSSAppException
     * @throws RemoteException
     * @throws CreateException
     * @throws javax.naming.NamingException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.IllegalAccessException
     * @throws NEDSSConcurrentDataException
     * @throws javax.ejb.EJBException
     */
    public Collection<?> processNBSChartRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException {

    	Collection<?> returnedColl = null;    	
		Object aDAO = null;
		try {
			Class<?> daoClass = Class.forName(daoName); 
			aDAO = daoClass.newInstance();
			Map<Object, Object> methodMap = getMethods(daoClass);			
			Method method = (Method)methodMap.get(sMethod);
            ArrayList<Object>  objAr = new ArrayList<Object> ();
            if(oParams != null)
            {
                for(int i = 0; i < oParams.length; i++)
                {
                    logger.debug("processChartRequest - oParams = " + oParams[i]);
                    objAr.add(oParams[i]);
                }
            }			
			Object obj = method.invoke(aDAO, objAr.toArray());			
			returnedColl = (ArrayList<?> ) obj;
			
		} catch (ClassNotFoundException err) {
			logger.fatal("ClassNotFoundException in processChartRequest: " + err.getMessage(),err);
			throw new java.rmi.RemoteException(err.getMessage(), err);
		} catch (NEDSSSystemException nse) {
			logger.fatal("NEDSSSystemException in processChartRequest: " + nse.getMessage());
			throw new java.rmi.RemoteException(nse.getMessage(), nse);		
		} catch (Exception e) {
			logger.fatal("Error in processChartRequest: " + e.getMessage());
			throw new java.rmi.RemoteException(e.getCause() + e.getMessage(), e );
		}	
		
    	
    	return returnedColl;
    }
    
    
    protected Map<Object, Object> getMethods(Class<?> daoClass)
    {
        Method[] gettingMethods = daoClass.getMethods();
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        for(int i = 0; i < gettingMethods.length; i++)
        {
            Method method = (Method)gettingMethods[i];
            String methodName = method.getName();
            resultMap.put(methodName, method);
        }
        return resultMap;
    }

}
