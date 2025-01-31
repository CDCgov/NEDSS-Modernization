package gov.cdc.nedss.localfields.ejb.localfieldsejb.bean;



import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * LocalFieldsEJB is the bean for the LocalFields Session Enterprise Bean.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFieldsEJB.java
 * Sep 4, 2008
 * @version
 */
public class LocalFieldsEJB implements SessionBean {

  static final LogUtils logger = new LogUtils(LocalFieldsEJB.class.getName());

  public LocalFieldsEJB() {
  }

  public void ejbCreate() {
  }

  public void ejbRemove() {
  }

  public void ejbActivate() {
  }

  public void ejbPassivate() {
  }

  public void setSessionContext(SessionContext sc) {
  }


  /**
   * processLDFMetaDataRequest makes appropriate DAO Call and returns the Result as an Object.
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
  public Collection<?>  processLDFMetaDataRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException {

  	Collection<?>  returnedColl = null;    	
		Object aDAO = null;
		try {
			Class<?> daoClass = Class.forName(daoName); 
			aDAO = daoClass.newInstance();
			Map<?,?> methodMap = NedssUtils.getMethods(daoClass);			
			Method method = (Method)methodMap.get(sMethod);
          ArrayList<Object> objAr = new ArrayList<Object> ();
          if(oParams != null)
          {
              for(int i = 0; i < oParams.length; i++)
              {
                  logger.debug("processLDFMetaDataRequest - oParams = " + oParams[i]);
                  objAr.add(oParams[i]);
              }
          }			
          Object obj = method.invoke(aDAO, objAr.toArray());			
          returnedColl = (Collection<?>) obj;
			
		} catch (ClassNotFoundException err) {
			logger.error("ClassNotFoundException in processLDFMetaDataRequest: " + err.getMessage(),err);
			throw new java.rmi.RemoteException(err.getMessage(),err);
		} catch (NEDSSSystemException nse) {
			logger.error("NEDSSSystemException in processLDFMetaDataRequest: " + nse.getMessage(),nse);
			throw new java.rmi.RemoteException(nse.getMessage(),nse);		
		} catch (Exception e) {
			logger.error("Error in processLDFMetaDataRequest: " + e.getMessage(),e);
			throw new java.rmi.RemoteException(e.getCause() + e.getMessage(),e );
		}	
		
  	
  	return returnedColl;
  }  
  
}
