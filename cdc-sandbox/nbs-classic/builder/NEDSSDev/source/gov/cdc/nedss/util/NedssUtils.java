// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved
// *** Generated Source File ***

/**
 * Title: nedss utility class.
 * Description: At present it reads property files and returns query, when asked for.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author sdesai
 * @version 1.0
 */

package gov.cdc.nedss.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.JsonObject;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
 

public class NedssUtils
{
    static final LogUtils                      logger                = new LogUtils((NedssUtils.class).getName());                                               
    protected static boolean                   DEBUG_MODE            = true;                                                                                  
    protected static Properties                ps                    = null;
    static Map<Object, Object>                 beanMap               = new HashMap<Object, Object>();
    protected static Hashtable<Object, Object> ht                    = null;
    protected static DataSource                dataSource            = null;

    private static Hashtable<Object, Object>   homes                 = new Hashtable<Object, Object>();

    public static final String                 DATA_SOURCE_REFERENCE = "java:jboss/datasources/NedssDataSource";
    
    public NedssUtils()
    {
    }

    public static String getQuery(String qryName)
    { try {
		// get query string for given query name
		    logger.info("Starts getQuery() for a givin string name.");
		    if (ps == null)
		    {
		        NedssUtils utils = new NedssUtils();
		    }
		    String qry = (String) ps.get(qryName);
		    logger.info("Done getQuery() - return a query: " + qry.toString());
		    return qry;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("qryName: " + qryName + ", " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
    }

    /*
     * // used to get a connection to the database //this is for testing
     * purposes only
     * 
     * public Connection getTestConnection(){
     * System.out.println("using getTestConnection()");
     * 
     * Connection conn = null;
     * 
     * try{ // for testing using silverstream drivers
     * Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver"); conn =
     * DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "nbs_ods", "ods");
     * // for testing using oracle thin drives //
     * Class.forName("oracle.jdbc.driver.OracleDriver"); // conn =
     * DriverManager.getConnection("jdbc:oracle:thin:@atldev4:1521:nbsdev",
     * "nbs_odse", "ods"); // for testiong using microsoft drivers (ask brent
     * for ms jar files) //
     * Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver"); // conn =
     * DriverManager.getConnection(
     * "jdbc:microsoft:sqlserver://ATLDEV2:1433;DatabaseName=nbs_odse2",
     * "nbs_ods", "ods");
     * 
     * } catch (ClassNotFoundException cnf) {
     * logger.fatal("Can not load Database Driver", cnf); } catch (SQLException
     * se) { logger.fatal("Can not get a connection", se); }
     * logger.info("Done getTestCOnnection() return: " + conn.toString());
     * return conn; }
     */

    private void putHome(String JndiName, Object objref)
    {
        homes.put(JndiName, objref);
    }

    private Object getHome(String JndiName)
    {
        return homes.get(JndiName);
    }

    /**
     * This method helps to lookup an EJB in SilverStream by giving its
     * JNDIname. It returns an object which can be later casted to EJB home
     * interface. Example: Object beanObj = lookupBean("EJBPerson"); Object
     * objremote = PortableRemoteObject.narrow(beanObj,
     * gov.cdc.nedss.cdm.ejb.person.bean.PersonHome.class); PersonHome home =
     * (PersonHome)objremote; Person person = home.create();
     */

    public Object lookupBean(String JndiName) throws NEDSSSystemException
    {
        logger.info("Starts lookupBean(JndiName)...");
        Object objref = null;
        InitialContext jndiCntx = null;
            try
            {
                objref = getHome(JndiName);
                if (objref == null)
                {
                    logger.info("Home ref not found for: " + JndiName + "  trying to get new one. ");
                    if (ht == null)
                    {
                    	PropertyUtil propertyUtil = PropertyUtil.getInstance();
                        ht = new Properties();
                        ht.put(Context.INITIAL_CONTEXT_FACTORY, propertyUtil.getInitialContextFactory());
                        ht.put(Context.PROVIDER_URL, propertyUtil.getProviderURL());
                        ht.put(Context.URL_PKG_PREFIXES, propertyUtil.getUrlPkgPrefixes());
                        jndiCntx = new InitialContext(ht);
                        logger.warn("Created InitialContext, ht=" + ht);
                    }
                    else
                    {
                        jndiCntx = new InitialContext(ht);
                    }
                    objref = jndiCntx.lookup(JndiName);
                    if (objref == null)
                    {
                        // //##!!
                        // System.out.println("Can not find EJB by JNDI name.");
                        logger.warn("Can not find EJB by JNDI name.");
                    }
                    putHome(JndiName, objref);
                }
                else
                    logger.info("Obj ref found for: " + JndiName);
            }
            catch (Exception e)
            {
                // e.printStackTrace();
                logger.fatal("Can not find EJB by JNDI name: " + JndiName + ", " + e.getMessage(),e);
                throw new NEDSSSystemException(e.getMessage(),e);
            }
            finally
            {
                closeContext(jndiCntx);
            }
        return objref;
    }

    /*
     * From the given class find all getter methods.
     * 
     * @param Class
     * 
     * @return Map<Object,Object> of method names
     */

    public Map<Object, Object> getGettingMethods(Class<?> beanClass)
    {
        try {
			logger.info("Starts getGettingMethods()...");
			Method[] gettingMethods = beanClass.getDeclaredMethods();
			Map<Object, Object> resultMap = new HashMap<Object, Object>();
			for (int i = 0; i < gettingMethods.length; i++)
			{
			    Method method = gettingMethods[i];
			    String methodName = method.getName();

			    if (methodName.startsWith("get"))
			    {

			        methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
			        // ##!! System.out.println(" ---- get methodName ++++  = " +
			        // methodName);
			        resultMap.put(methodName, method);
			    }
			}
			logger.info("Done getGettingMethods() - return: " + resultMap.toString());
			return resultMap;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.fatal("SecurityException occured in NedssUtils.getGettingMethods: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /*
     * From the given class find all getter methods and put them in map.
     * 
     * @param Class
     * 
     * @return Map<Object,Object> of method names
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getCachedMethods(Class<?> beanClass)
    {
        try {
			logger.info("Starts getCachedMethods()...");
			Map<Object, Object> methods = (Map<Object, Object>) beanMap.get(beanClass);
			if (methods == null)
			{
			    methods = getGettingMethods(beanClass);
			    beanMap.put(beanClass, methods);
			}
			logger.info("Done getCachedMethods() - return: " + methods.toString());
			return methods;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.getCachedMethods: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /*
     * Compare two objects of a given class for equality.
     * 
     * @param Object1
     * 
     * @param Object2 to be comapred
     * 
     * @param class of which both objects are instants.
     * 
     * @return boolean. true if both objects aer equal, false if not.
     */

    public boolean equals(Object obj1, Object obj2, Class<?> clazz)
    {
        try {
			logger.info("Starts equals( Object obj1, Object obj2, Class clazz)...");
			if (!clazz.isInstance(obj1))
			{
			    logger.debug("obj1 is not of type: " + clazz.getName());
			    return false;
			}
			if (!clazz.isInstance(obj2))
			{
			    logger.debug("obj2 is not of type: " + clazz.getName());
			    return false;
			}
			Map<Object, Object> methodMap = getCachedMethods(clazz);
			Iterator<Object> keyIt = methodMap.keySet().iterator();
			while (keyIt.hasNext())
			{
			    String key = (String) keyIt.next();
			    Method method = (Method) methodMap.get(key);
			    try
			    {
			        Object o1 = method.invoke(obj1, (Object[]) null);
			        Object o2 = method.invoke(obj2, (Object[]) null);
			        if (o1 != null && o1.toString().trim().equals(""))
			            o1 = null;
			        if (o1 == null && o2 == null)
			            continue;
			        if (o1 == null && o2.toString().trim().equals(""))
			            continue;
			        if (!(o1 != null && o1.equals(o2)))
			        {
			            logger.debug("Variables are different in : " + method.getName());
			            logger.debug("Objects are different. value1: " + o1 + " value2: " + o2 + " [" + o2 + "]");
			            return false;
			        }
			        else
			        {
			            logger.debug("Objects are same. Object 1 is :" + o1);
			        }
			    }
			    catch (Exception e)
			    {
			        logger.fatal("Fails equalitity test of two objects: " + e.getMessage(),e);
			    }
			}
			logger.info("Done equals() - return: true");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.equals: Object1: " + obj1 + ", Object2: " + obj2 + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }
    
    public boolean isEqual(Object obj1, Object obj2)
    {
        try {
			return(  ( obj1 != null && obj2 != null && obj1.equals(obj2) ) || ( obj1  == null && obj2 == null) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.isEqual: Object1: " + obj1 + ", Object2: " + obj2 + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    // get the methods in the class, put them in a HashMap, return the Map
    public static Map<String, Object> getMethods(Class<?> beanClass)
    {
        try {
			logger.info("Starts getMethods()...");
			Method[] gettingMethods = beanClass.getMethods();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for (int i = 0; i < gettingMethods.length; i++)
			{
			    Method method = gettingMethods[i];
			    String methodName = method.getName();
			    resultMap.put(methodName, method);
			}
			logger.info("Done getMethods() - return: " + resultMap.toString());
			return resultMap;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.getMethods: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /**
     * Sort an array of array of object using a comparator constructed with the
     * methodName arg. All the elements of the array are assumed to be of same
     * type
     */
    @SuppressWarnings("unchecked")
    public void sortObjectByColumn(String methodName, Collection<Object> objects)
    {
        try {
			logger.debug(" in sort by " + methodName);
			Object[] array = objects.toArray();
			Object obj = array[0];
			ByMethodValueComparator methodComp = new ByMethodValueComparator(obj.getClass());
			try
			{
			    methodComp.setValueGetterMethod(methodName);
			}
			catch (NoSuchMethodException nsme)
			{
			    logger.fatal("No can sort: " + nsme.toString(), nsme);
			    return;
			}
			Arrays.sort(array, methodComp);
			objects.clear();
			for (int i = 0; i < array.length; i++)
			{

			    objects.add(array[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.sortObjectByColumn: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /**
     * Sort an array of array of object using a comparator constructed with the
     * methodName arg. All the elements of the array are assumed to be of same
     * type
     */
    @SuppressWarnings("unchecked")
    public void sortObjectByColumn(String methodName, Collection<Object> objects, boolean direction)
    {
        try {
			logger.debug(" in sort by " + methodName);
			Object[] array = objects.toArray();
			Object obj = array[0];
			ByMethodValueComparator methodComp = new ByMethodValueComparator(obj.getClass());
			try
			{
			    methodComp.ascending(direction);
			    methodComp.setValueGetterMethod(methodName);
			}
			catch (NoSuchMethodException nsme)
			{
			    logger.fatal("No can sort: " + nsme.toString(), nsme);
			    return;
			}
			Arrays.sort(array, methodComp);
			objects.clear();
			for (int i = 0; i < array.length; i++)
			{

			    objects.add(array[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.sortObjectByColumn: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /**
     * sortObjectByColumnPatientSearch(): method created for fixing the ASCII sorting issue in Patient search.
     * Once there's a new global method for all the tables, this method will be removed.
     * @param methodName
     * @param objects
     * @param direction
     */
    @SuppressWarnings("unchecked")
    public void sortObjectByColumnGeneric(String methodName, Collection<Object> objects, boolean direction)
    {
        try {
			logger.debug(" in sort by " + methodName);
			Object[] array = objects.toArray();
			Object obj = array[0];
			ByMethodValueComparatorPatientSearch methodComp = new ByMethodValueComparatorPatientSearch(obj.getClass());
			try
			{
			    methodComp.ascending(direction);
			    methodComp.setValueGetterMethod(methodName);
			}
			catch (NoSuchMethodException nsme)
			{
			    logger.fatal("No can sort: " + nsme.toString(), nsme);
			    return;
			}
			Arrays.sort(array, methodComp);
			objects.clear();
			for (int i = 0; i < array.length; i++)
			{

			    objects.add(array[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.sortObjectByColumn: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /* this is test code for Oracle only */
    /*
     * public String getOpenCursor(){
     * 
     * Connection dbConnection = null; PreparedStatement pstmt = null; ResultSet
     * rs = null; try{ String sql = "select count(*) from V$Open_cursor " ; //
     * String sql = "select @@connections"; dbConnection = getConnection();
     * pstmt = dbConnection.prepareStatement(sql); rs = pstmt.executeQuery();
     * rs.next(); return rs.getString(1); }catch(Exception e){
     * logger.error("Error in getting open cursors"); e.printStackTrace();
     * 
     * } finally{ closeStatement(pstmt); releaseConnection(dbConnection); }
     * return "0"; }
     */

    public static void main(String[] args)
    {
        // NedssUtils nu = new NedssUtils();
        // String str = nu.getOpenCursor();
        // //##!! System.out.println("Open cursor are: " + str);
        try
        {
            NedssUtils nu = new NedssUtils();
            // SRTMapDAOImpl dao = new SRTMapDAOImpl();
            // String str = dao.getCodeDescTxt( "ACR",
            // SRTSQLQuery.CODE_DESC_TXT_SQL,SRTSQLQuery.CODE_DESC_TXT_ORACLE_SQL);
            // System.out.println("Str is: " + str);
            String sBeanJndiName = JNDINames.SRT_CACHE_EJB;
            Object obj = nu.lookupBean(sBeanJndiName);
            System.out.println("Object is: " + obj);
            SRTMapHome srtHome = (SRTMapHome) PortableRemoteObject.narrow(obj, SRTMapHome.class);
            SRTMap srt = srtHome.create();
            System.out.println("calling method");
            ArrayList<Object> list = srt.getLDFPages();
            System.out.println("list is: " + list);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.fatal("Exception occured in NedssUtils.main: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
        }
    }

    /**
     * Determines if the business object is an Act or Entity.
     * 
     * @param businessObjLookupName
     *            name of the business object
     * 
     * @return String with value of "ENTITY", "ACT", or NULL.
     */
    public String isActOrEntity(String businessObjLookupName)
    {
        try {
			// Create the list of Entities
			ArrayList<String> entities = new ArrayList<String>();
			entities.add(NBSBOLookup.PATIENT);
			entities.add(NBSBOLookup.ORGANIZATION);

			// Create the list of Acts
			ArrayList<String> acts = new ArrayList<String>();
			acts.add(NBSBOLookup.INVESTIGATION);
			acts.add(NBSBOLookup.INTERVENTIONVACCINERECORD);
			// acts.add(NedssBOLookup.CASEDEFINITION); I don't think this is
			// considered an ACT. See Lisa Branic
			// Lisa claimed it is an algorithm
			acts.add(NBSBOLookup.OBSERVATIONLABREPORT);
			acts.add(NBSBOLookup.OBSERVATIONMORBIDITYREPORT);
			acts.add(NBSBOLookup.OBSERVATIONGENERICOBSERVATION);
			acts.add(NBSBOLookup.NOTIFICATION);
			// acts.add(NedssBOLookup.INTERVENTION); //This hasn't been added yet.
			// FEEL FREE TO ADD MORE WHEN NECESSARY.

			if (entities.contains(businessObjLookupName))
			{
			    return NEDSSConstants.CLASSTYPE_ENTITY;
			}
			if (acts.contains(businessObjLookupName))
			{
			    return NEDSSConstants.CLASSTYPE_ACT;
			}
			// It will return null if the business object is not found in either the
			// entity or act lists.
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.isActOrEntity: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    private void closeContext(InitialContext ctx)
    {
        try {
			try
			{
			    if (ctx != null)
			        ctx.close();
			}
			catch (Exception e)
			{
			    e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.closeContext: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

    }
    
    public static JsonObject toJson(Object obj, String[] propNames)
    {
        JsonObject jobj = new JsonObject();
        try
        {
            for(String name : propNames )
                jobj.addProperty(name, BeanUtils.getProperty(obj, name) ); 
        }
        catch(Exception e)
        {
        	logger.fatal("Exception occured in NedssUtils.toJson: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
        }
        
        return jobj;
    }
	
	public static Map getNbsAnswerMapsWithQIds(Map answerMap) throws NEDSSSystemException
    {
        try {
			Map returnMap = new HashMap();
			List<String> keys = new ArrayList<String>();
			
			if (answerMap != null && answerMap.size() > 0)
			{
			    Iterator<Object> iter = answerMap.keySet().iterator();
			    while (iter.hasNext())
			    {
			        Long key = (Long) iter.next();
			        keys.add( key.toString() ); 
			    }
			    String[] keyArr = new String[keys.size()];
			    keys.toArray(keyArr);
			    NBSQuestionDAOImpl qDao = new NBSQuestionDAOImpl();
			    List<NbsQuestionDT> qList = qDao.findNBSQuestions( StringUtils.combine(keyArr, ",") );
			    iter = answerMap.keySet().iterator();
			    while (iter.hasNext())
			    {
			        Long key = (Long) iter.next(); 
			        for(NbsQuestionDT q : qList )
			        {
			            if( q.getNbsQuestionUid().equals(key))
			            { 
			                returnMap.put( q.getQuestionIdentifier(), answerMap.get(key));
			                break;
			            }
			        } 
			    }
			}
			return returnMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in NedssUtils.toJson: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }
	
	/**
	 * translateSpecialCharacterUrl(): method created for translating special characters like %, &, #, +, etc., that were causing issues in the url (like Refine Search on
	 * Search Resulted Test) to the corresponding percent-enconding
	 * @param specialCharacter
	 * @return
	 */
	
	public static String translateSpecialCharacterUrl(String specialCharacter){
		
		switch (specialCharacter){
		
		case "%": 
				specialCharacter="%25";//Percent-encoding
			break;
		case "&": 
				specialCharacter="%26";//Percent-encoding
			break;
		case "#": 
				specialCharacter="%23";//Percent-encoding
			break;
		case "+": 
				specialCharacter="%2B";//Percent-encoding
			break;
		default:
			break;
		}
		
		return specialCharacter;	
	}
	
	
	public static char parse(final char input) {
	    final int digit = (int) input;
	    char output = '\0';
	    
	   
	   
	    for (int i = 48; i <= 58; ++i) {  // From 0 to 9 and :
	      if (digit == i) {
	        output = (char) i;
	      }
	    }

	    for (int i = 64; i <= 90; ++i) {  	    // @ and From A to Z
	      if (digit == i) {
	        output = (char) i;
	      }
	    }

	  
	    for (int i = 97; i <= 122; ++i) {     // From a to z
	      if (digit == i) {
	        output = (char) i;
	      }
	    }

	    
	    for (int i = 45; i <= 47; ++i) {   // - . /
	      if (digit == i) {
	        output = (char) i;
	      }
	    }

	
	    if (digit == 92) {       // \
	      output = (char) 92;
	    }

	    
	    if (digit == 95) {    // _
	      output = (char) 95;
	    }

	    
	    if (digit == 32) {   // space
	      output = (char) 32;
	    }
	    
	    return output;
	  }
	
	
	public static String CleanStringPath(final String input) {
	    final StringBuilder output = new StringBuilder();
	    if (input != null) {
	      for (int i = 0; i < input.length(); ++i) {
	        output.append(parse(input.charAt(i)));
	      }
	    }
	    return output.toString();
    }
	
	
	public static boolean isLocalPath(String path) {
	    return path.startsWith("/") && !path.startsWith("//");
	}

} // end class