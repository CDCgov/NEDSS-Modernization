/**
 * Title: NEDSSVOUtils.java
 * Description: A Java utility class for comparing two Value Objects excluding non-compare
 * methods derived by parsing external Config file with a SAX parser
 * Copyright:    Copyright (c) 2003
 * Company: CSC
 * @author: Nedss Development Team
 */

package gov.cdc.nedss.util;

import org.xml.sax.helpers.DefaultHandler;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

public class NEDSSVOUtils extends DefaultHandler {

	private String VO_ELEMENT_TAG = "vo";
	private String VO_NAME_ATTR_TAG = "name";
	private String VO_CLASSNAME_ATTR_TAG = "classname";
	private String COMPARISON_FIELD_ELEMENT_TAG = "comparison-field";
	private String COMPARISON_FIELD_NAME_ATTR_TAG = "name";
	private String COMPARISON_FIELD_SORT_BY_FIELD_ATTR_TAG = "sort-by-field";
	private String startElementName = null;
	private Map<Object,Object> voCopmapareFields = null;
	private List<Object> getterList = null;
	private Map<Object,Object> getterFieldMap = null;
	private static NEDSSVOUtils instance =
		new NEDSSVOUtils(NEDSSConstants.NEDSSVO_CONFIG);

	/**
	 * getInstance() returns the NEDSSVOUtils instance
	 * @return NEDSSVOUtils
	 */
	public static NEDSSVOUtils getInstance() {
		return instance;
	}

	/**
	 *  Constructor with a String argument for parsing config file
	 */
	private NEDSSVOUtils(String filename) {

		SAXParserFactory f = SAXParserFactory.newInstance();
		SAXParser p = null;
		DefaultHandler h = this;

		try {
			voCopmapareFields = new HashMap<Object,Object>();
			p = f.newSAXParser();
			p.parse(new File(filename), h);
		} catch (Throwable x) {
			x.printStackTrace();
			voCopmapareFields = null;
		}
	} //NEDSSVOUtils

	/**
	 *  hasVOFieldChanged method for comparing two Value Objects
	 *  @param AbstractVO the first VO in comparision
	 *  @param AbstractVO the second VO in comparision
	 *  @return boolean
	 *  @throws NEDSSAppException
	 */
	public boolean hasVOFieldChanged(AbstractVO vo1, AbstractVO vo2)
		throws NEDSSAppException {

		//as per shannon, return true and tbd afterwards.
		return true;
		/*
		        if (vo1 == null || vo2 == null) {
		            throw new NEDSSAppException("Invalid Arguments: null pointer");
		        }
		
		        if(vo1.equals(vo2)) {
		            // check default equals first
		            return false;
		        }
		
		        if (voCopmapareExcludedFields == null) {
		            throw new NEDSSAppException("Failed to initialize NEDSSVOUtils.");
		        }
		
		        //System.out.println("voCopmapareExcludedFields = " + voCopmapareExcludedFields.toString());
		
		        Class clazz1 = vo1.getClass();
		        Class clazz2 = vo2.getClass();
		
		        if (!clazz1.equals(clazz2)) {
		                throw new NEDSSAppException("Invalid Arguments: arguments of different types");
		        }
		
		        // start to work
		        try {
		
		            Method[] gettingMethods = clazz1.getDeclaredMethods();
		            // get the excluded getter list
		            List<Object> aList = (List)voCopmapareExcludedFields.get(clazz1.getName());
		            System.out.println("clazz1.getName() = " + clazz1.getName());
		
		            for (int i = 0; i < gettingMethods.length; i++) {
		
		                Method method = (Method) gettingMethods[i];
		                String methodName = method.getName();
		                //System.out.println("method Name = " + methodName);
		
		                if (methodName.startsWith("get")) {
		
		                    if(aList != null && aList.contains(methodName)) {
		                            // this is the excluded one
		                            continue;
		                    }
		                    Class[] paraTypes = method.getParameterTypes();
		
		                    if(paraTypes.length != 0) {
		                      continue;
		                    }
		                    //System.out.println("method = " + method);
		                    Object field1 = method.invoke(vo1, null);
		                    Object field2 = method.invoke(vo2, null);
		
		                    //System.out.println("field1 = " + field1);
		                    //System.out.println("field2 = " + field2);
		
		                    if(field1 == null && field2 == null) {
		                      continue;
		                    } else if(field1 == null && field2 != null) {
		                      return true;
		                    } else if(field1 != null && field2 == null) {
		                      return true;
		                    } else if (field1 instanceof Collection) {
		                        // collection field loop through and compare by position
		                        Object[] array1 = ((Collection<Object>) field1).toArray();
		                        Object[] array2 = ((Collection<Object>) field2).toArray();
		                        if(array1.length != array2.length) {
		                                // no equal length
		                                return true;
		                        }
		                        for (int j = 0; j < array1.length; j++) {
		                                AbstractVO tmpVO1 = (AbstractVO) array1[j];
		                                AbstractVO tmpVO2 = (AbstractVO) array2[j];
		                                if (hasVOFieldChanged(tmpVO1, tmpVO2)) {
		                                        return true;
		                                }
		                        }
		                      } else if (field1 instanceof AbstractVO) {
		                              // for DTs compare them again
		                              if (hasVOFieldChanged((AbstractVO) field1,(AbstractVO) field2)) {
		                                      return true;
		                              }
		
		                      } else if (field1 instanceof Timestamp) {
		                            // compare date part only
		                            long date1 = (((Timestamp)field1).getTime() + TimeZone.getDefault().getRawOffset()) / 86400000L;
		                            long date2 = (((Timestamp)field2).getTime() + TimeZone.getDefault().getRawOffset()) / 86400000L;
		                            if(date1 != date2) {
		                                    return true;
		                            }
		                      } else {
		                            // simple field
		                            if(!field1.equals(field2)) {
		                                    return true;
		                            }
		                      }
		
		                  }
		              }
		
		        } catch (IllegalAccessException e) {
		                throw new NEDSSAppException("Can not access the fields for compared objects.");
		        } catch (InvocationTargetException e) {
		                // this should never happen
		                throw new NEDSSAppException("Method does not exist.");
		        }
		
		      //return false;
		*/

	} //hasVOFieldChanged

	public boolean hasVOFieldChangedAlt(AbstractVO vo1, AbstractVO vo2)
		throws NEDSSAppException {

		if (vo1 == null || vo2 == null) {
			throw new NEDSSAppException("Invalid Arguments: null pointer");
		}

		if (vo1.equals(vo2)) {
			// check default equals first
			return false;
		}

		if (voCopmapareFields == null) {
			throw new NEDSSAppException("Failed to initialize NEDSSVOUtils.");
		}

		//System.out.println("voCopmapareExcludedFields = " + voCopmapareExcludedFields.toString());

		Class<?> clazz1 = vo1.getClass();
		Class<?> clazz2 = vo2.getClass();

		if (!clazz1.equals(clazz2)) {
			throw new NEDSSAppException("Invalid Arguments: arguments of different types");
		}

		// start to work
		try {

			// get the compare field list
			List<Object> aList = (List<Object>) voCopmapareFields.get(clazz1.getName());
			System.out.println("clazz1.getName() = " + clazz1.getName());

			for (int i = 0; i < aList.size(); i++) {

				String methodName =
					(String) ((Map<Object,Object>) aList.get(i)).get(
						COMPARISON_FIELD_NAME_ATTR_TAG);
				//System.out.println("method Name = " + methodName);

				if (!methodName.endsWith("Sndx")
					&& !methodName.endsWith("Uid")
					&& methodName.startsWith("get")
					&& !methodName.equalsIgnoreCase("getUid")
					&& !methodName.endsWith("LocalId")
					&& !methodName.equalsIgnoreCase("getAddTime")
					&& !methodName.equalsIgnoreCase("getPersonParentUid")
					&& !methodName.equalsIgnoreCase("getAddUserId")
					&& !methodName.equalsIgnoreCase("getLastChgTime")
					&& !methodName.equalsIgnoreCase("getLastChgReasonCd")
					&& !methodName.equalsIgnoreCase("getVersionCtrlNbr")
					&& !methodName.equalsIgnoreCase("getLastChgUserId")
					&& !methodName.equalsIgnoreCase("getLastChgTime")
					&& !methodName.equalsIgnoreCase("getPersonUid")) {

					Method method = clazz1.getMethod(methodName, (Class[])null);
					Class<?>[] paraTypes = method.getParameterTypes();

					if (paraTypes.length != 0) {
						continue;
					}
					//System.out.println("method = " + method);
					Object field1 = method.invoke(vo1, (Object[])null);
					Object field2 = method.invoke(vo2, (Object[])null);

					//System.out.println("field1 = " + field1);
					//System.out.println("field2 = " + field2);

					if (field1 == null && field2 == null) {
						continue;
					} else if (field1 == null && field2 != null) {
						return true;
					} else if (field1 != null && field2 == null) {
						return true;
					} else if (field1 instanceof Collection<?>) {
						// collection field loop through and compare by position
						ArrayList<Object> list1 = new ArrayList<Object>(((Collection<Object>) field1));
						ArrayList<Object> list2 = new ArrayList(((Collection<Object>) field1));
						if (list1.size() != list2.size()) {
							// no equal length
							return true;
						}
						String sortByFieldName =
							(String) ((Map) aList.get(i)).get(
								COMPARISON_FIELD_SORT_BY_FIELD_ATTR_TAG);
						if (sortByFieldName != null
							&& !sortByFieldName.trim().equals("")) {
							list1 = sort(list1, sortByFieldName);
							list1 = sort(list2, sortByFieldName);
						}

						for (int j = 0; j < list1.size(); j++) {
							AbstractVO tmpVO1 = (AbstractVO) list1.get(j);
							AbstractVO tmpVO2 = (AbstractVO) list2.get(j);
							if (hasVOFieldChangedAlt(tmpVO1, tmpVO2)) {
								return true;
							}
						}
					} else if (field1 instanceof AbstractVO) {
						// for DTs compare them again
						if (hasVOFieldChangedAlt((AbstractVO) field1,
							(AbstractVO) field2)) {
							return true;
						}

					} else if (field1 instanceof Timestamp) {
						// compare date part only
						long date1 =
							(((Timestamp) field1).getTime()
								+ TimeZone.getDefault().getRawOffset())
								/ 86400000L;
						long date2 =
							(((Timestamp) field2).getTime()
								+ TimeZone.getDefault().getRawOffset())
								/ 86400000L;
						if (date1 != date2) {
							return true;
						}
					} else {
						// simple field
						if (!field1.equals(field2)) {
							return true;
						}
					}

				}
			}

		} catch (IllegalAccessException e) {
			throw new NEDSSAppException("Can not access the fields for compared objects.");
		} catch (InvocationTargetException e) {
			// this should never happen
			throw new NEDSSAppException("Method does not exist.");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	} //hasVOFieldChangedAlt

	private ArrayList<Object> sort(ArrayList<Object>  vos, String sortByFieldName) {
		//TODO: implement sort algorithm
		return vos;
	}
	/**
	 *  startElement method receives notification of the start of an element
	 *  @param ns URI
	 *  @param lName localName
	 *  @param qName qualified Name with prefix
	 *  @param a specified or default attributes
	 *  @throws SAXException
	 */

	public void startElement(
		String ns,
		String lName,
		String qName,
		Attributes a)
		throws SAXException {

		startElementName = lName != "" ? lName : qName;
		if (startElementName.equals(VO_ELEMENT_TAG)) {
			// create a new entry to the map
			getterList = new ArrayList<Object> ();
		}
		if (startElementName.equals(COMPARISON_FIELD_ELEMENT_TAG)) {
			// create a new entry to the map
			getterFieldMap = new HashMap<Object,Object>();
			getterList.add(getterFieldMap);
		}

		// go through attribute
		if (a != null) {
			for (int j = 0; j < a.getLength(); j++) {
				String attrName =
					a.getLocalName(j) != "" ? a.getLocalName(j) : a.getQName(j);

				// deal with attributes
				if (startElementName.equals(VO_ELEMENT_TAG)) {
					if (attrName.equals(VO_CLASSNAME_ATTR_TAG)) {
						//System.out.println("a.getValue(j) = " + a.getValue(j));
						voCopmapareFields.put(a.getValue(j), getterList);
					}
				}
				if (startElementName.equals(COMPARISON_FIELD_ELEMENT_TAG)) {
					if (attrName.equals(COMPARISON_FIELD_NAME_ATTR_TAG)) {
						//System.out.println("a.getValue(j) = " + a.getValue(j));
						getterFieldMap.put(
							COMPARISON_FIELD_NAME_ATTR_TAG,
							a.getValue(j));
					}
					if (attrName
						.equals(COMPARISON_FIELD_SORT_BY_FIELD_ATTR_TAG)) {
						//System.out.println("a.getValue(j) = " + a.getValue(j));
						getterFieldMap.put(
							COMPARISON_FIELD_SORT_BY_FIELD_ATTR_TAG,
							a.getValue(j));
					}
				}

			}
		}
	} //startElement

	/**
	 *  cloneVO method for cloning a Value Objects
	 *  @param AbstractVO the  VO to clone
	 *  @return cloned ValueObject
	 *  @throws NEDSSAppException
	 */
	public AbstractVO cloneVO(AbstractVO vo) throws NEDSSAppException {

		Class clazz = vo.getClass();
		AbstractVO newVO = null;

		try {

			newVO = (AbstractVO) clazz.newInstance();
			Method[] gettingMethods = clazz.getDeclaredMethods();

			for (int i = 0; i < gettingMethods.length; i++) {

				Method method = (Method) gettingMethods[i];
				String methodName = method.getName();

				if (methodName.startsWith("get")) {

					//System.out.println(methodName);
					Class[] types = method.getParameterTypes();
					if (types.length != 0) {
						continue;
					}

					Object field = method.invoke(vo, (Object[])null);
					if (field == null) {
						// default should be null for a new instance, so do nothing
						continue;
					}

					// find the set method name
					String setMethodName = "set" + methodName.substring(3);
					Method setMethod = null;
					try {
						Class<?>[] paraTypes = { method.getReturnType()};
						setMethod = clazz.getMethod(setMethodName, paraTypes);
					} catch (NoSuchMethodException e) {
						continue;
					}
					if (field instanceof Collection<?>) {
						ArrayList<Object> aList = new ArrayList<Object> ();
						for (Iterator<Object> iter = ((Collection<Object>) field).iterator();
							iter.hasNext();
							) {
							AbstractVO element = (AbstractVO) iter.next();
							aList.add(cloneVO(element));
						}
						Object[] para = { aList };
						setMethod.invoke(newVO, para);
					} else if (field instanceof AbstractVO) {
						Object[] para = { cloneVO((AbstractVO) field)};
						setMethod.invoke(newVO, para);
					} else {
						// simple field
						Object[] para = { field };
						setMethod.invoke(newVO, para);
					}
				}
			}

		} catch (InstantiationException e) {
			throw new NEDSSAppException("Can not create new instance for the cloned object.");
		} catch (IllegalAccessException e) {
			throw new NEDSSAppException("Can not access the fields for cloned object.");
		} catch (InvocationTargetException e) {
			// this should never happen
			throw new NEDSSAppException("Method does not exist.");
		}
		return newVO;

	} //cloneVO
	
	public static void main(String[] args) {
		NEDSSVOUtils utils = NEDSSVOUtils.getInstance();
	}
} //NEDSSVOUtils
