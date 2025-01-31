package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding;

import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.LogUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This is a utility class to dynamically load and populate bean data.
 * 
 * @author Pradeep Kumar Sharma
 * 
 */
public class DynamicBeanBinding {
	static final LogUtils logger = new LogUtils(
			DynamicBeanBinding.class.getName());
	private static Map<Object, Object> beanMethodMap = new HashMap<Object, Object>();


	
	
	/**
	 * 
	 * @param questionMap
	 * @param bean 
	 * @param answerMap
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public static void transferBeanValueForDT(NbsQuestionMetadata nbsQuestionMetadata,
			Object bean, String answerText, String prefix)
			throws Exception {
		try {
			if(nbsQuestionMetadata!=null){
				String dataLoc = nbsQuestionMetadata.getDataLocation() == null ? "" : nbsQuestionMetadata
						.getDataLocation();
				if (dataLoc.toUpperCase().startsWith(prefix + ".")) {
					int pos = dataLoc.indexOf(".");
					if (pos == -1) {
						throw new Exception(
								"Data Location for: "
										+ nbsQuestionMetadata.getQuestionIdentifier()
										+ " cannot be without '.' between Table Name and Table Column");
					}
					String colNm = dataLoc.substring(pos + 1);
						DynamicBeanBinding.populateBean(bean, colNm, answerText);
				}
			} 
		} catch (Exception e) {
			logger.error("DynamicBeanBinding.transferBeanValueForDT: Error while traferring values to bean and exception is raised: "
					+ nbsQuestionMetadata);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param questionMap
	 * @param bean 
	 * @param answerMap
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public static boolean transferBeanValues(Map<Object, Object> questionMap,
			Object bean, Map<Object, Object> answerMap, String prefix)
			throws Exception {
		boolean isBeanAvailableinMetadata = false;
		try {
			Iterator<Object> iter = questionMap.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				//if (answerMap.get(key) != null) {
					//In order to delete the previous value in case is a disabled in the front end (answer = null)
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap 
							.get(key);
					if(dt!=null){
					String dataLoc = dt.getDataLocation() == null ? "" : dt
							.getDataLocation();
					if (dataLoc.toUpperCase().startsWith(prefix + ".")) {
						int pos = dataLoc.indexOf(".");
						if (pos == -1) {
							throw new Exception(
									"Data Location for: "
											+ dt.getQuestionIdentifier()
											+ " cannot be without '.' between Table Name and Table Column");
						}
						isBeanAvailableinMetadata = true;
						String colNm = dataLoc.substring(pos + 1);
						String colVal = answerMap.get(key) == null ? ""
								: (String) answerMap.get(key);
						if (colVal != null && colNm != null)
							DynamicBeanBinding
									.populateBean(bean, colNm, colVal);
					}
				} else {
					continue;
				}

			}
		} catch (Exception e) {
			logger.error("DynamicBeanBinding.transferBeanValues: Error while traferring values to bean and exception is raised: "
					+ e);
			throw new Exception(e);
		}
		return isBeanAvailableinMetadata;
	}

	/**
	 * _populateBeanFromMap recieves API call from different PageRender
	 * Components and populates the attribute(DATA_LOCATION) values from
	 * answerMap
	 * 
	 * @param questionMap
	 * @param bean
	 * @param answerMap
	 * @param prefix
	 * @throws Exception
	 */
	public static void populateBeanFromMap(Map<Object, Object> questionMap,
			Object bean, Map<Object, Object> answerMap, String prefix)
			throws Exception {

		try {
			Iterator<Object> iter = questionMap.keySet().iterator();
			while (iter.hasNext()) {
				NbsQuestionMetadata dt = (NbsQuestionMetadata) iter.next();
				String key = dt.getQuestionIdentifier();
				String dataLoc = dt.getDataLocation() == null ? "" : dt
						.getDataLocation();
				// prefix can be any constant from RenderConstants.java
				if (dataLoc.startsWith(prefix)) {
					int pos = dataLoc.indexOf(".");
					if (pos == -1) {
						throw new Exception(
								"Data Location for: "
										+ dt.getQuestionIdentifier()
										+ " cannot be without '.' between Table Name and Table Column");
					}
					String colNm = dataLoc.substring(pos + 1);
					String colVal = answerMap.get(key) == null ? ""
							: (String) answerMap.get(key);
					if (!colVal.equals(""))
						DynamicBeanBinding.populateBean(bean, colNm, colVal);
				}

			}
		} catch (Exception e) {
			logger.error("DynamicBeanBinding.populateBeanFromMap: Error while populating value from map  to bean and exception is raised: "
					+ e);
			throw new Exception(e);
		}
	}

	/**
	 * populateBean populates the metadata relevant colNm to the Bean Object and
	 * returns
	 * 
	 * @param bean
	 * @param colNm
	 * @throws Exception
	 */
	public static void populateBean(Object bean, String colNm, String colVal)
			throws Exception {

		try {
			
			//final SimpleDateFormat DATE_STORE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

			String methodName = getSetterName(colNm);
			Map<Object, Object> methodMap = getMethods(bean.getClass());

			Method method = (Method) methodMap.get(methodName);
            if(method==null){
            	logger.warn("Coluld not find the method name for column: '"+colNm+"' in bean: '"+bean.getClass()+"'");
            	return;
            }
			Object[] parmTypes = method.getParameterTypes();
			String pType = ((Class<?>) parmTypes[0]).getName();
			Object[] arg = { "" };
			Object[] nullArg = null;

			if (colVal!=null && !colVal.equals("")) {
				if (pType.equalsIgnoreCase("java.sql.Timestamp")) {

					Timestamp ts = new Timestamp(new SimpleDateFormat("MM/dd/yyyy") 
							.parse(colVal).getTime());
					arg[0] = ts;

				} else if (pType.equalsIgnoreCase("java.lang.String")) {
					arg[0] = colVal;

				} else if (pType.equalsIgnoreCase("java.lang.Long")) {
					arg[0] = Long.valueOf(colVal);
					

				} else if (pType.equalsIgnoreCase("java.lang.Integer")) {
					arg[0] = Integer.valueOf(colVal);

				} else if (pType.equalsIgnoreCase("java.math.BigDecimal")) {
					arg[0] = BigDecimal.valueOf(Long.valueOf(colVal).longValue());

				} else if (pType.equalsIgnoreCase("boolean")) {
					arg[0] = colVal;
				}
			}else {
				arg[0] = nullArg;
			}
			try {
				if(colVal==null) {
					Object[] nullargs = { null };
					method.invoke(bean, nullargs);
			}else				
				method.invoke(bean, arg);
				logger.debug("Successfully called methodName for bean " + bean
						+ " with value " + colVal);
			} catch (Exception e) {
				logger.error("DynamicBeanBinding.populateBean: Error while invoking value to bean and exception is raised: "
						+ e);
				throw new Exception(e);
			}
		} catch (Exception e) {
			logger.error("DynamicBeanBinding.populateBean: Error while populating value to bean and exception is raised: "
					+ e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param daoClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Map<Object, Object> getMethods(Class<?> beanClass)
			throws Exception {
		try {
			if (beanMethodMap.get(beanClass) == null) {
				Method[] gettingMethods = beanClass.getMethods();
				Map<Object, Object> resultMap = new HashMap<Object, Object>();
				for (int i = 0; i < gettingMethods.length; i++) {
					Method method = (Method) gettingMethods[i];
					String methodName = method.getName();
					Object[] parmTypes = method.getParameterTypes();
					if (methodName.startsWith("set") && parmTypes.length == 1)
						resultMap.put(methodName, method);
				}
				beanMethodMap.put(beanClass, resultMap);
			}
			return (Map<Object, Object>) beanMethodMap.get(beanClass);
		} catch (SecurityException e) {
			logger.error("DynamicBeanBinding.getMethods: Error while gettingMethod exception is raised: "
					+ e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	private static String getSetterName(String columnName) throws Exception {
		try {
			StringBuffer sb = new StringBuffer("set");
			StringTokenizer st = new StringTokenizer(columnName, "_");
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				s = s.substring(0, 1).toUpperCase()
						+ s.substring(1).toLowerCase();
				sb.append(s);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("DynamicBeanBinding.getSetterName: Error while settingMethod and exception is raised: "
					+ e);
			throw new Exception(e);
		}
	}

	@SuppressWarnings({ "unused" })
	public static void transferBeanValuesForView(Map<Object, Object> questionMap, Object bean, Map<Object, Object> answerMap, String prefix) throws Exception {
		
		Iterator<Object> iter = questionMap.keySet().iterator();
		while(iter.hasNext()) {
			Object key = iter.next();
			if(answerMap.get(key)!=null){
			
			NbsQuestionMetadata dt = (NbsQuestionMetadata)questionMap.get(key);
			//String key = dt.getQuestionIdentifier();
			String dataLoc = dt.getDataLocation() == null ? "" : dt.getDataLocation();
			//prefix can be any constant from RenderConstants.java
			if(dataLoc.startsWith(prefix)) {
				int pos = dataLoc.indexOf(".");
				if(pos == -1) {
					throw new Exception("Data Location for: " + dt.getQuestionIdentifier() + " cannot be without '.' between Table Name and Table Column");
				}
				Method[] methdsArr=bean.getClass().getMethods();
				for(int i = 0; i < methdsArr.length; i++){
					
					String colNm = dataLoc.substring(pos + 1);
					String colVal = answerMap.get(key) == null ? "" : (String) answerMap.get(key);
					if(!colVal.equals("")){
						//populateBean(bean, colNm, colVal);
					}
				}
			}
			}else{
				continue;
			}
			
		}
	}

	/**
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public static String getGetterName(String columnName) throws Exception {
		try {
			logger.debug("columnName : "+columnName);
			
			StringBuffer sb = new StringBuffer("get");
			StringTokenizer st = new StringTokenizer(columnName, "_");
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				s = s.substring(0, 1).toUpperCase()
						+ s.substring(1).toLowerCase();
				sb.append(s);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("DynamicBeanBinding.getSetterName: Error while getGetterName and exception is raised: columnName: "+columnName+", "
					+ e.getMessage(),e);
			throw new Exception(e);
		}
	}
	
	
	
	/**
	 * @param bean
	 * @param methodName
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static String getValueForMethod(Object bean, String methodName, String className) throws Exception{
		String value = null;
		try{
			Class myClass = Class.forName(className);
			Method method = myClass.getMethod(methodName, new Class[] {});
				Object obj = method.invoke(bean, new Object[] {});
				if(obj!=null){
					 value = (String)obj;
					 if(value.length()>0){
						 return value;
					 }
			}
		}catch(Exception ex){
			logger.fatal("Error in getQuestionFieldValue in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return value;
	}
	
	public static Object getValueObjectForMethod(Object bean, String methodName, String className) throws Exception{
		Object value = null;
		try{
			Class myClass = Class.forName(className);
			Method method = myClass.getMethod(methodName, new Class[] {});
			value = method.invoke(bean, new Object[] {});
		}catch(Exception ex){
			logger.fatal("Error in getQuestionFieldValue in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return value;
	}
	
	
}
