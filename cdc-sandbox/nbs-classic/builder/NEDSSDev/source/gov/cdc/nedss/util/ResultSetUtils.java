/**
 * Title: ResultSetUtils utility class.
 * Description: A utility to work with result set. Given class name to populate data
 * it puts data in proper class members and create map of such objects.
 * An Example is given in main to demonstrate it's use.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author sdesai
 * @version 1.0
 */

package gov.cdc.nedss.util;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.io.*;

import com.sas.rmi.Logger;

import gov.cdc.nedss.systemservice.exception.*;

/**
 *
 */
public class ResultSetUtils {
	//For logging
	static final LogUtils logger = new LogUtils(ResultSetUtils.class.getName());

	private boolean verbose = false; // indicates whether commments should be
									 // printed to the console

	private boolean standardizeNames = false; // indicates whether method names
											  // should follow naming //
											  // standards --- note: this flag
											  // insures backward compatibility

	private Map<Object, Object> classMap = new HashMap<Object, Object>();

	private StringUtils stringUtils = new StringUtils();

	private String query = null;
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	public ResultSetUtils() {

	}

	/**
	 * Set the verbose flag
	 *
	 * @param isVerbose
	 *            the flag indicating whether commments should be printed to the
	 *            console
	 */
	public void setVerbose(boolean isVerbose) {
		verbose = isVerbose;
	}

	/**
	 * Set the standardize name flag
	 *
	 * @param standardize
	 *            the flag indicating whether method names should follow naming
	 *            standards
	 */
	public void setStandardizeNames(boolean standardize) {
		standardizeNames = standardize;
	}

	/**
	 * Maps the data returned in a ResultSet to a List<Object>
	 *
	 * @param resultSet
	 *            the result set
	 * @param metaData
	 *            the metaData representing the result set
	 * @param beanClass
	 *            the class of the beans that will comprise the list
	 * @param list
	 *            the list to add beans to --- if this is null, a new list will
	 *            be created
	 * @param isVerbose
	 *            sets the verbose flag to true to enable printing comments to
	 *            the console
	 * @return a list of beans of type beanClass populated with data --- the
	 *         list will be empty if the resultSet contained no rows
	 * @throws ResultSetUtilsException
	 */
	public List<Object> mapRsToBeanList(ResultSet resultSet,
			ResultSetMetaData metaData, Class beanClass, List<Object> list,
			boolean isVerbose) throws ResultSetUtilsException {
		verbose = isVerbose;
		return mapRsToBeanList(resultSet, metaData, beanClass, list);
	}

	/**
	 * Maps the data returned in a ResultSet to a List<Object>
	 *
	 * @param resultSet
	 *            the result set
	 * @param metaData
	 *            the metaData representing the result set
	 * @param beanClass
	 *            the class of the beans that will comprise the list
	 * @param list
	 *            the list to add beans to --- if this is null, a new list will
	 *            be created
	 * @return a list of beans of type beanClass populated with data --- the
	 *         list will be empty if the resultSet contained no rows
	 * @throws ResultSetUtilsException
	 */
	public List<Object> mapRsToBeanList(ResultSet resultSet,
			ResultSetMetaData metaData, Class beanClass, List<Object> list)
			throws ResultSetUtilsException {
		//If we haven't been passed a list, create a new one
		if (list == null) {
			list = new ArrayList<Object> ();
		}

		//If one or the other is null, we cannot proceed
		if (resultSet == null || metaData == null) {
			if (verbose) {
				logger.debug("Warning: ResultSet = " + resultSet
						+ ", MetaData is " + metaData);
			}
			return list;
		}

		Object bean;
		try {
			if (verbose) {
				logger
						.debug("ResultSet columns : "
								+ metaData.getColumnCount());
				// printMethodNames(beanClass);
			}
			while (resultSet.next()) {
				bean = createBeanFromClass(beanClass);
				updateBean(bean, resultSet, metaData, beanClass);
				list.add(bean);
			}
		} catch (SQLException se) {
			logger.fatal("", se);
			throw new ResultSetUtilsException(se.toString()
					+ "  See stdout for StackTrace");
		}
		return list;
	}

	/**
	 * Maps the data returned in a ResultSet to a Map
	 *
	 * @param resultSet
	 *            the result set
	 * @param metaData
	 *            the metaData representing the result set
	 * @param beanClass
	 *            the class of the beans that will comprise the map
	 * @param keyMethodName
	 *            the name of the method that returns the key to the bean
	 * @param map
	 *            the map to add beans to --- if this is null, a new map will be
	 *            created
	 * @param isVerbose
	 *            sets the verbose flag to true to enable printing comments to
	 *            the console
	 * @return a map of beans of type beanClass populated with data --- the map
	 *         will be empty if the resultSet contained no rows
	 * @throws ResultSetUtilsException
	 */
	public Map<Object, Object> mapRsToBeanMap(ResultSet resultSet, ResultSetMetaData metaData,
			Class beanClass, String keyMethodName, Map<Object, Object> map, boolean isVerbose)
			throws ResultSetUtilsException {
		verbose = isVerbose;

		return mapRsToBeanMap(resultSet, metaData, beanClass, keyMethodName,
				map);
	}

	/**
	 * Maps the data returned in a ResultSet to a Map
	 *
	 * @param resultSet
	 *            the result set
	 * @param metaData
	 *            the metaData representing the result set
	 * @param beanClass
	 *            the class of the beans that will comprise the map
	 * @param keyMethodName
	 *            the name of the method that returns the key to the bean
	 * @param map
	 *            the map to add beans to --- if this is null, a new map will be
	 *            created
	 * @return a map of beans of type beanClass populated with data --- the map
	 *         will be empty if the resultSet contained no rows
	 * @throws ResultSetUtilsException
	 */
	public Map<Object, Object> mapRsToBeanMap(ResultSet resultSet, ResultSetMetaData metaData,
			Class<?> beanClass, String keyMethodName, Map<Object, Object> map)
			throws ResultSetUtilsException {
		if (map == null) {
			map = new HashMap<Object, Object>();
		}
		if (resultSet == null || metaData == null) {
			if (verbose) {
				logger.debug("Warning: ResultSet = " + resultSet
						+ ", MetaData is " + metaData);
			}
			return map;
		}
		try {
			int rows = 1;
			if (verbose) {
				logger.debug("keyMethodName : " + keyMethodName + " in "
						+ beanClass.getName());
				logger
						.debug("ResultSet columns : "
								+ metaData.getColumnCount());
				printMethodNames(beanClass);
			}
			Method keyMethod = getKeyMethod(beanClass, keyMethodName);
			while (resultSet.next()) {
				if (verbose) {
					//System.out.print("Row : " + rows++ + " ");
				}
				Object bean = createBeanFromClass(beanClass);
				updateBean(bean, resultSet, metaData, beanClass);
				String key = getKey(bean, keyMethod);
				Object object = map.put(key, bean);
				if (object != null) {
					logger.debug("Warning: Duplicate key found in map: " + key
							+ " for bean " + beanClass);
				}
			}
		} catch (SQLException se) {
			logger.fatal("", se);
			throw new ResultSetUtilsException(se.toString()
					+ "  See stdout for StackTrace");
		}
		return map;
	}

	/**
	 * Maps a ResultSet to an instance of the beanClass. Intended for result
	 * sets which contain only one row. If the ResultSet contains more than one
	 * row, a bean containing the first row's data will be returned.
	 *
	 * @param resultSet
	 *            the ResultSet
	 * @param metaData
	 *            the ResultSetMetaData
	 * @param beanClass
	 *            the class of the bean to instantiate
	 * @param isVerbose
	 *            sets the verbose flag to true to enable printing comments to
	 *            the console
	 * @return an object of type beanClass populated with data, of null if the
	 *         ResultSet contained no rows
	 */
	public Object mapRsToBean(ResultSet resultSet, ResultSetMetaData metaData,
			Class beanClass, boolean isVerbose) throws ResultSetUtilsException {
		verbose = isVerbose;
		return mapRsToBean(resultSet, metaData, beanClass);
	}

	/**
	 * Maps a ResultSet to an instance of the beanClass. Intended for result
	 * sets which contain only one row. If the ResultSet contains more than one
	 * row, a bean containing the first row's data will be returned.
	 *
	 * @param resultSet
	 *            the ResultSet
	 * @param metaData
	 *            the ResultSetMetaData
	 * @param beanClass
	 *            the class of the bean to instantiate
	 * @return an object of type beanClass populated with data, of null if the
	 *         ResultSet contained no rows
	 */
	public Object mapRsToBean(ResultSet resultSet, ResultSetMetaData metaData,
			Class beanClass) throws ResultSetUtilsException {
		Object bean = createBeanFromClass(beanClass);
		addRsToBean(resultSet, metaData, bean);
		return bean;
	}

	/**
	 * Maps a ResultsSet to a bean object. Intended for Results Sets which
	 * contain only one row. If the Results Set contains more than one row, the
	 * first row's data will be set in the bean.
	 *
	 * @param resultSet
	 *            the ResultSet.
	 * @param metaData
	 *            the ResultSetMetaData.
	 * @param bean
	 *            the bean to set values in.
	 */
	public void addRsToBean(ResultSet resultSet, ResultSetMetaData metaData,
			Object bean) throws ResultSetUtilsException {
		if (resultSet == null || metaData == null || bean == null) {
			if (verbose) {
				logger.debug("Warning: ResultSet: " + resultSet
						+ ", MetaData: " + metaData + ", Bean: " + bean);
			}
			return;
		}
		try {
			Class beanClass = bean.getClass();
			if (verbose) {
				logger
						.debug("ResultSet columns : "
								+ metaData.getColumnCount());
				printMethodNames(beanClass);
			}
			if (resultSet.next()) {
				updateBean(bean, resultSet, metaData, beanClass);
			}
		} catch (SQLException se) {
			logger.fatal("", se);
			throw new ResultSetUtilsException(se.toString()
					+ "  See stdout for StackTrace");
		}
	}

	/**
	 * Print the method names defined in the beanClass.
	 *
	 * @param beanClass
	 *            the Class of the bean being created
	 */
	private void printMethodNames(Class beanClass) {
		try {
			Method[] methods = beanClass.getMethods();
			logger.debug("Methods in " + beanClass.getName());
			for (int j = 0; j < methods.length; j++) {
				Method method = methods[j];
				Class[] types = method.getParameterTypes();
				String parameterTypes = "";
				for (int k = 0; k < types.length; k++) {
					parameterTypes = parameterTypes + types[k].getName() + " ";
				}
				//System.out.print(" Signature: " + method.getName());
				logger.debug(" --- Parameter types: " + parameterTypes);
			}
		} catch (Exception e) {
			logger.fatal("Error retrieving meta data about bean class", e);

		}
	}

	/* Helper methods */

	/**
	 * Create the bean from the beanClass.
	 *
	 * @param beanClass
	 *            the Class of the bean being created
	 * @return the bean being created
	 * @throws ResultSetUtilsException
	 */
	private Object createBeanFromClass(Class beanClass)
			throws ResultSetUtilsException {
		try {
			return beanClass.newInstance();
		} catch (IllegalAccessException iae) {
			logger.fatal("", iae);
			throw new ResultSetUtilsException(iae.toString()
					+ "  See stdout for StackTrace");
		} catch (InstantiationException ie) {
			logger.fatal("", ie);
			throw new ResultSetUtilsException(ie.toString()
					+ "  See stdout for StackTrace");
		}
	}

	/**
	 * Return the key of the bean
	 *
	 * @param bean
	 *            the bean being created
	 * @param keyMethod
	 *            the method defined in the bean which returns the key
	 * @return the key of the bean being created
	 * @throws ResultSetUtilsException
	 */
	private String getKey(Object bean, Method keyMethod)
			throws ResultSetUtilsException {
		try {
			return keyMethod.invoke(bean, (Object[])null).toString();
		} catch (IllegalAccessException iae) {
			logger.fatal("", iae);
			throw new ResultSetUtilsException(iae.toString()
					+ "  See stdout for StackTrace");
		} catch (InvocationTargetException ite) {
			logger.fatal("", ite);
			throw new ResultSetUtilsException(ite.toString()
					+ "  See stdout for StackTrace");
		}
	}

	/**
	 * Return the method defined in the bean which returns the key
	 *
	 * @param beanClass
	 *            the class of the bean being created
	 * @param keyMethodName
	 *            the name of the method defined in the bean which returns the
	 *            key
	 * @return the method defined in the bean which returns the key
	 * @throws ResultSetUtilsException
	 */
	private Method getKeyMethod(Class beanClass, String keyMethodName)
			throws ResultSetUtilsException {
		try { //This should be a "getter" method that takes no arguments
			return beanClass.getMethod(keyMethodName, (Class[])null);
		} catch (NoSuchMethodException nsme) {
			logger.fatal("", nsme);
			throw new ResultSetUtilsException(nsme.toString()
					+ "  See stdout for StackTrace");
		}
	}

	/**
	 * Return the bean after it's been populated from the data in the result set
	 *
	 * @param bean
	 *            the bean being created
	 * @param resultSet
	 *            the result set
	 * @param metaData
	 *            the metaData of the result set
	 * @param beanClass
	 *            the class of the bean being created
	 * @return the bean after it's been populated from the data in the result
	 *         set
	 * @throws ResultSetUtilsException
	 */
	private void updateBean(Object bean, ResultSet resultSet,
			ResultSetMetaData metaData, Class beanClass)
			throws ResultSetUtilsException {
		Map<Object, Object> methodMap = getMethodMap(beanClass);
		try {
			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				String columnName = metaData.getColumnName(i);
				Object columnValue = resultSet.getObject(i);
				if (columnValue != null) {
					Class columnValueClass = columnValue.getClass();
					// this check is for sql server. If string is blank put it
					// as null
					if (columnValueClass.getName().equalsIgnoreCase(
							"java.lang.String")
							&& ((String) columnValue).trim().length() == 0)
						columnValue = null;

					//if (columnValueClass.getName().equalsIgnoreCase(
					//		"java.math.BigDecimal")
					//		&& PropertyUtil.getDatabaseServerType() != null
					//		&& (!PropertyUtil.getDatabaseServerType()
					//				.equalsIgnoreCase(NEDSSConstants.ORACLE_ID))) // for
																				  // SQL
																				  // server
					//{ //logger.debug("Class name changed from bigdeciaml to
					  // double");
					//	columnValueClass = Class.forName("java.math.BigDecimal");
					//}
					String methodName = getSetterName(columnName);
					Map<Object, Object> parmMethodMap = (Map<Object, Object>) methodMap.get(methodName);
					if (parmMethodMap == null) {
						logger.debug("Warn: no method in " + beanClass
								+ " with name " + methodName);
					} else {
						Method method = getMethodForClass(parmMethodMap,
								columnValueClass);
						if (method == null
								&& columnValueClass.getName().equalsIgnoreCase(
										"java.lang.Long")) // for
						{
							columnValueClass = Class
									.forName("java.lang.Integer");
						} // if
						if (columnValueClass.getName().equalsIgnoreCase(
								"java.lang.Short")
							) //For SQL Server
						{
						columnValueClass = Class.forName("java.lang.Integer");
						}
						method = getMethodForClass(parmMethodMap,
								columnValueClass);
						if (method == null) {
							logger.debug("Warning: no method in " + beanClass
									+ " for column " + columnName
									+ " with parameter type "
									+ columnValueClass);
						} else {
							//logger.debug("Invoking " + methodName + " with "
							// + columnValue + " of type " + columnValueClass);
							Object[] args = getArgs(
									method.getParameterTypes()[0], columnValue);
							try {
								method.invoke(bean, args);
							} catch (IllegalArgumentException ie) {
								logger
										.debug("IllegalArgumentException thrown.");
								// ie.printStackTrace();
								Object val = null;
								{
									if (method.getParameterTypes()[0]
											.equals(Double.class)
											&& columnValue instanceof java.math.BigDecimal)
										val = new Double(
												((java.math.BigDecimal) columnValue)
														.doubleValue());
									if (method.getParameterTypes()[0]
																	.equals(Integer.class)
																	&& columnValue instanceof java.lang.Short)
																val = new Integer(
																		((java.lang.Short) columnValue)
																				.intValue());
								}
								Object[] arg = { val };
								try {
									method.invoke(bean, arg);
								} catch (IllegalArgumentException iie) {
									logger
											.warn("Illegal argument in resultset.");
								} // catch
							} // catch
						} // else
					}// else
				}// if
			}// for
		} catch (Exception e) {
			logger.fatal("", e);
			throw new ResultSetUtilsException(e.toString()
					+ "  See stdout for StackTrace");
		}
		//if (beanClass != null && beanClass.getName() != null && methodName != null)
		//	logger.debug("Completed updating bean " +beanClass.getName());
	}

	private Method getMethodForClass(Map<Object, Object> map, Class type) {
		Method method = (Method) map.get(type);
		if (method == null) {
			//There may be a method with an argument that is either a
			// superclass
			//or interface implemented by the passed type. Try to find one in
			//the map. If found, add the new type to the map with the same
			// method.
			Iterator<Object> it = map.keySet().iterator();
			while (it.hasNext()) {
				Class key = (Class) it.next();
				if (key.isAssignableFrom(type)) {
					method = (Method) map.get(key);
					if (verbose) {
						logger.debug("Adding " + method.getName()
								+ " with parameter type " + type);
					}
					map.put(type, method);
					return method;
				}
			}
		}
		return method;
	}

	/**
	 * Get the map of setter methods for the bean. If the map doesn't exist yet,
	 * create the map and store it away.
	 */
	private Map<Object, Object> getMethodMap(Class beanClass) throws ResultSetUtilsException {
		try {
			Map<Object, Object> methodMap = (Map<Object, Object>) classMap.get(beanClass);
			if (methodMap == null) {
				if (verbose) {
					logger.debug("/nCreating method map for " + beanClass);
				}
				methodMap = new HashMap<Object, Object>();
				//Filter the methods of the class, selecting only the "setter"
				// methods.
				Method[] methods = beanClass.getMethods();
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					String methodName = method.getName();
					Object[] parmTypes = method.getParameterTypes();
					//For each method, there is a submap keyed by the parameter
					// type of that method.
					//This should allow a bean to have overloaded "set" methods
					// and still let the
					//utils invoke the correct method for the result set
					if (methodName.startsWith("set") && parmTypes.length == 1) {
						if (verbose) {
							logger.debug("Adding " + methodName
									+ " with parameter type " + parmTypes[0]);
						}
						Map<Object, Object> parmMethodMap = (Map<Object, Object>) methodMap.get(methodName);
						if (parmMethodMap == null) {
							parmMethodMap = new HashMap<Object, Object>();
							methodMap.put(methodName, parmMethodMap);
						}
						parmMethodMap.put(parmTypes[0], method);
						//Kludge: for Booleans/booleans, put the method in for
						// a
						//parm type of String. This allows us to map columns
						// that
						//hold "Y" or "N" to Booleans/booleans in beans
						if (parmTypes[0].equals(Boolean.class)
								|| parmTypes[0].equals(Boolean.TYPE))
							parmMethodMap.put(String.class, method);
					
						if (verbose) {
							logger.debug("Adding " + methodName
									+ " with parameter type String");
						}

					}
				}
				if (verbose) {
					logger.debug("");
				}
				classMap.put(beanClass, methodMap);
			}
			return methodMap;
		} catch (Exception e) {
			logger.fatal("", e);
			throw new ResultSetUtilsException(e.toString()
					+ "  See stdout for StackTrace");
		}
	}

	private Object[] getArgs(Class parmType, Object columnValue) {
		//Most parms should translate without problem. Just check for
		//exceptional cases
		Object val = columnValue;
		if ((parmType.equals(Boolean.class) || parmType.equals(Boolean.TYPE))
				&& columnValue instanceof String) {
			val = new Boolean(((String) columnValue).equalsIgnoreCase("Y"));
		}
		if (parmType.equals(Long.class)
				&& columnValue instanceof java.math.BigDecimal)
			val = new Long(((java.math.BigDecimal) columnValue).longValue());
		Object[] result = { val };
		return result;
	}

	/**
	 *
	 */
	private String getSetterName(String columnName) {
		if (standardizeNames) {
			return stringUtils.setterMethodName(columnName);
		} else {
			return "set" + Character.toUpperCase(columnName.charAt(0))
					+ columnName.substring(1);
		}
	}

	/*
	 *
	 *
	 */
	public static void main(String[] args) {
		/*
		 * Connection conn = null; ResultSet rs = null; PropertyUtil propUtil =
		 * new PropertyUtil(NEDSSConstants.PROPERTY_FILE); try{
		 * //Class.forName("oracle.jdbc.driver.OracleDriver");
		 * Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver"); conn =
		 * DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "nbs_ods",
		 * "ods"); // conn = DriverManager.getConnection
		 * ("jdbc:oracle:thin:@atldev_4:1521:nbsdev", "nbs_odsc2", "ods");
		 *
		 * String sql = "SELECT observation_uid \"observationUid\",
		 * obs_value_numeric_seq \"obsValueNumericSeq\", numeric_value_1
		 * \"numericValue1\", numeric_value_2 \"numericValue2\", numeric_unit_cd
		 * \"numericUnitCd\", "+ " separator_cd \"separatorCd\" FROM
		 * obs_value_numeric WHERE observation_uid = 605264207"; Statement stmt =
		 * conn.createStatement(); rs = stmt.executeQuery(sql); ResultSetUtils
		 * rsu = new ResultSetUtils(); Map<Object, Object> rmap = null; rmap =
		 * rsu.mapRsToBeanMap(rs,rs.getMetaData(),ObsValueNumericDT.class,"getObservationUid" ,
		 * rmap); //##!! System.out.println("map length is: " + rmap.size()); }
		 * catch (ClassNotFoundException cnf) { logger.fatal("Can not load
		 * Database Driver", cnf); } catch (Exception se) {
		 *
		 * //##!! System.out.println(se); logger.fatal("Can not get a
		 * connection", se); } finally{ try{ rs.close(); conn.close();
		 * }catch(Exception e){
		 *  } }
		 */

	}
}