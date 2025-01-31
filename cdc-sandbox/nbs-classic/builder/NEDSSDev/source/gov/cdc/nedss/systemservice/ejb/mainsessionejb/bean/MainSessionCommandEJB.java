package gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.exception.NedssAppLogException;
import gov.cdc.nedss.systemservice.dao.SecurityLogDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuth;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuthHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.SecurityLogDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

/**
 *  Main Session Command
 */
public class MainSessionCommandEJB implements SessionBean
{

    static final LogUtils logger = new LogUtils(MainSessionCommandEJB.class.getName());
    private SessionContext cntx;
    private NBSSecurityObj nbsSecObj;
    private final boolean DEBUG = true;
    private String userName;
    private String password;
    private String sessionId;

    /**
     *  Default constructor.
     */
    public MainSessionCommandEJB()
    {
    }

    public void setSessionContext(SessionContext cntx)
    {
        this.cntx = cntx;
    }

    public void ejbActivate()
    {
    }

    public void ejbCreate() throws RemoteException, CreateException
    {
    }

    public void ejbCreate(String uname, String pword, String sessionId, String pRemoteAddress, String pRemoteHost) throws CreateException, RemoteException, NEDSSAppException
    {
        try {
			logger.debug("Starts MainSessionCommand's ejbCreate()");
			logger.debug("********#User login from :"+pRemoteAddress);
			this.userName = uname;
			this.password = pword;
			this.sessionId = sessionId;
			String uFirstName = uname;
			String uLastName = null;
			Long nedssEntryId = new Long(-1);
			// According to new requirement, state web server will authenticate user
			// and password is not required here.
			if(uname != null)  /*&& pword != null*/
			{
			    if(nbsSecObj == null)
			    {
			    	 
			        try
			        {
			            nbsSecObj = nbsSecurityLogin(uname, pword);
			           
			            if(nbsSecObj == null)
			            {
			                logSecurity(uname,null, sessionId, pRemoteAddress, pRemoteHost, NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_FAILED, nedssEntryId);
			            }
			            else
			            {
			            	uFirstName = nbsSecObj.getTheUserProfile().getTheUser().getFirstName();
			            	uLastName = nbsSecObj.getTheUserProfile().getTheUser().getLastName();
			            	nedssEntryId = new Long(nbsSecObj.getTheUserProfile().getTheUser().getEntryID());
			                logSecurity(uFirstName, uLastName, sessionId, pRemoteAddress, pRemoteHost, NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_SUCCESS, nedssEntryId);
			            }
			        }
			        catch(NEDSSAppException nae)
			        {
			            logger.fatal("Can not create MainSessionControl, login() fails", nae);
			            logSecurity(uFirstName, uLastName, sessionId, pRemoteAddress, pRemoteHost, NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_FAILED, nedssEntryId);
			            throw new CreateException(nae.toString());
			        }
			    }
			}
			else
			{
			    logger.fatal("Incorrect inputs, user name is " + uname + " password is " + pword);
			    logSecurity(uFirstName, uLastName, sessionId, pRemoteAddress, pRemoteHost, NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_FAILED, nedssEntryId);
			    throw new CreateException("Insufficient input, user name is " + uname + " password is " + pword);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    public void ejbPassivate()
    {
    }

    public void ejbRemove()
    {
        logger.error("MainSessionCommandEJB is removed");
        cntx = null;
    }

    public void logout(String uFirstNm, String uLastNm, String pSessionID, String pRemoteAddress, String pRemoteHost, Long nedssEntryId) throws RemoteException, NEDSSAppException
    {
        logSecurity(uFirstNm, uLastNm, pSessionID, pRemoteAddress, pRemoteHost, NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGOUT, nedssEntryId);
    }

    public NBSSecurityObj nbsSecurityLogin(String username, String password) throws RemoteException, NEDSSAppException
    {
        logger.debug("Inside nbsSecurityLogin");
        NedssUtils utils = new NedssUtils();
        DbAuth nbsAuth = null;
        String sBeanName = JNDINames.NBS_DB_SECURITY_EJB;
        Object obj = utils.lookupBean(sBeanName);
        logger.debug("obj:" + obj.toString());
        try
        {
        	DbAuthHome home = (DbAuthHome)PortableRemoteObject.narrow(obj, DbAuthHome.class);
            nbsAuth =  home.create();
            nbsSecObj = nbsAuth.Login(username);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            cntx.setRollbackOnly();
            logger.fatal("MainControl - There is an error : " + e.getMessage(), e);
            throw new NEDSSAppException(e.toString(), e);
        }
        return nbsSecObj;
    }

    public ArrayList<Object>  processRequest(String sBeanJndiName, String sMethod, Object[] oParams) throws NEDSSAppException,NedssAppLogException, RemoteException, CreateException, javax.naming.NamingException, java.lang.reflect.InvocationTargetException, java.lang.IllegalAccessException, NEDSSConcurrentDataException, javax.ejb.EJBException
    {
        try {
			logger.debug("VALUE OF THE NBSSECOBJ IS: " + nbsSecObj);
			if(nbsSecObj == null)
			{
			    logger.debug("nbsSecObj is NULL!!!!!");
			    throw new NEDSSAppException("security object is null in processRequest()");
			}	
			else
			{
			    logger.debug("\n\nStart\nMainSessionCommandEJB in ProcessRequest");
			    logger.debug("MainControl - In process request - sBeanJndiName = " + sBeanJndiName);
			    logger.debug("MainControl - In process request - sMethod = " + sMethod);
			    ArrayList<Object>  objAr = new ArrayList<Object> ();
			    if(oParams != null)
			    {
			        for(int i = 0; i < oParams.length; i++)
			        {
			            logger.debug("MainControl - In process request - oParams = " + oParams[i]);
			            objAr.add(oParams[i]);
			        }
			    }
			    objAr.add(nbsSecObj);
			    Object findObject = null;
			    EJBObject remoteClass = null;
			    ArrayList<Object>  arraylist = new ArrayList<Object> ();
			    EDXActivityLogDT dt = new EDXActivityLogDT();
			    try
			    {
			        NedssUtils nu = new NedssUtils();
			        findObject = (Object)nu.lookupBean(sBeanJndiName);
			        EJBHome ejbHome = (EJBHome)PortableRemoteObject.narrow(findObject, EJBHome.class);
			        EJBMetaData metaData = ejbHome.getEJBMetaData();
			        Class<?> homeClass = metaData.getHomeInterfaceClass();
			        ejbHome = (EJBHome)PortableRemoteObject.narrow(ejbHome, homeClass);
			        Map<String, Object> methodMap = getMethods(ejbHome.getClass());
			        boolean throwMethodNotFound = true;
			        if(methodMap.get("create") != null && methodMap.get("create").toString() != null)
			        {
			            Method method = (Method)methodMap.get("create");
			            remoteClass = (EJBObject)method.invoke(findObject, (Object[])null);
			            methodMap = getMethods(remoteClass.getClass());
			            throwMethodNotFound = false;
			        }
			        if(methodMap.get(sMethod) != null && methodMap.get(sMethod).toString() != null)
			        {
			            Method method = (Method)methodMap.get(sMethod);
			            Object returnedObj = method.invoke(remoteClass, objAr.toArray());
			            arraylist.add(returnedObj);
			            throwMethodNotFound = false;
			        }
			        if(throwMethodNotFound) {
			          throw new java.lang.NoSuchMethodException("No such method "+sMethod);
			        }

			    }
			    catch(InvocationTargetException ie)
			    {
			        String err = ie.getTargetException().toString();
			        logger.error("Target exception is: " + err);
			        if(err.indexOf("NEDSSConcurrentDataException") != -1)
			        {
			            logger.error("Throwing Concurrence Exception.");
			            cntx.setRollbackOnly();
			            throw new NEDSSAppConcurrentDataException("ERR109");
			        }
			        else if(err.indexOf(NEDSSConstants.NBS_APP_EXCEPTION_NO_ROLLBACK) != -1)
			        {
			            logger.error("Throwing NEDSSAppException for NBS_APP_EXCEPTION_NO_ROLLBACK");
			            throw new NEDSSAppException(err);
			        }
			        if(err.toString().indexOf("NedssAppLogException") != -1)
			        {
			            logger.error("Throwing Concurrence Exception.");
			            cntx.setRollbackOnly();
			            throw new NedssAppLogException(((NedssAppLogException) ie.getTargetException()).getEDXActivityLogDT());
			        }
			        
			        //Handle the NEDSSDeDuplication in a special way, it does not require context being
			        //invoked for setRollBackOnly... - DVC 06/30/2003
			        else if(err.indexOf("NEDSSDeduplicationException") != -1)
			        {
			            logger.error("Handling a NEDSSDeduplicationException");
			            throw new NEDSSAppException("Error in Mainsession command: " + err );
			        }
			        else
			        {
			            logger.error("Throwing NEDSSAppException.");
			            try {
			              cntx.setRollbackOnly();
			            } catch(Exception e) {
			              logger.error("Error setting RollbackOnly");
			              e.printStackTrace();
			            }
			            throw new NEDSSAppException("Error in Mainsession command: " + err, ie );

			        }
			    }
			            
			    catch(Exception e)
			    {
			        logger.fatal(e.getMessage(), e);
			        logger.debug(e.toString() + " : manisession command dataconcurrency catch: " + e.getClass());
			        if(e.toString().indexOf("NEDSSConcurrentDataException") != -1)
			        {
			            logger.fatal("Throwing Concurrence Exception.");
			            cntx.setRollbackOnly();
			            throw new NEDSSAppConcurrentDataException(e.toString(), e);
			        }
			        else if(e.toString().indexOf(NEDSSConstants.NBS_APP_EXCEPTION_NO_ROLLBACK) != -1)
			        {
			            logger.fatal("Throwing NEDSSAppException for NBS_APP_EXCEPTION_NO_ROLLBACK"+e.getMessage(),e);
			            throw new NEDSSAppException(e.toString(), e );
			        }
			        if(e.toString().indexOf("NedssAppLogException") != -1)
			        {
			            logger.fatal("Throwing Concurrence Exception."+e.getMessage(),e);
			            cntx.setRollbackOnly();
			            throw new NedssAppLogException(((NedssAppLogException)e).getEDXActivityLogDT());
			        }
			        
			        else
			        {
			            logger.fatal("Throwing NEDSSAppException."+e.getMessage(),e);
			            //cntx.setRollbackOnly();
			            throw new NEDSSAppException(e.getMessage(), e);
			        }
			    }
			    return arraylist;
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    public String test() throws RemoteException
    {
        return ("All looks fine.");
    }

    // get the methods in the class, put them in a HashMap, return the Map
    protected Map<String, Object> getMethods(Class<?> beanClass) throws NEDSSAppException
    {
        try {
			Method[] gettingMethods = beanClass.getMethods();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for(int i = 0; i < gettingMethods.length; i++)
			{
			    Method method = (Method)gettingMethods[i];
			    String methodName = method.getName();
			    resultMap.put(methodName, method);
			}
			return resultMap;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    /**
     *  Logs security-related events.
     *  @param pUserID the username.
     *  @param pSessionID the session ID.
     *  @param pRemoteAddress the user's IP address.
     *  @param pRemoteHost the user's computer name.
     *  @param pEventType LOGIN_SUCCESS=1, LOGIN_FAILED=2, LOGOUT=3.
     * @throws NEDSSAppException 
     */
    private void logSecurity(String uFirstNm, String uLastNm, String pSessionID, String pRemoteAddress, String pRemoteHost, int pEventType, Long nedssEntryId) throws NEDSSAppException
    {
        try
        {
            String s = null;
            if(pEventType == NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_SUCCESS)
            {
                s = NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_SUCCESS_STRING;
            }
            if(pEventType == NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGOUT)
            {
                s = NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGOUT_STRING;
            }
            if(s == null)
            {
                s = NEDSSConstants.SECURITY_LOG_EVENT_TYPE_LOGIN_FAILED_STRING;
            }
            SecurityLogDT dt = new SecurityLogDT();
            dt.setSessionId(pSessionID);
            dt.setFirstNm(uFirstNm);
            dt.setLastNm(uLastNm);
            dt.setNedssEntryId(nedssEntryId);
            dt.setEventTypeCd(s);
            dt.setRemoteAddress(pRemoteAddress);
            dt.setRemoteHost(pRemoteHost);
            dt.setItNew(true);
            SecurityLogDAOImpl dao = new SecurityLogDAOImpl();
            dao.create(dt);
        }
        catch(Exception ex)
        {
            logger.fatal(ex.getMessage(), ex);
            throw new NEDSSAppException(ex.getMessage(), ex);
        }
    }

}
