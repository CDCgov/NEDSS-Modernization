package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;

import java.util.Map;


/**
 * PageUtils is a utility helper class for Page Management Builder Rendering Module
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PageUtils.java
 * Jan 14, 2010
 * @version 1.0
 * @version 4.5
 * @updated by: Pradeep Sharma
 * <p>Company: SAIC</p>  
 */
public class PageUtils {

	public static final LogUtils logger = new LogUtils(PageUtils.class.getName());
	
	/**
	 * populateBean populates the metadata relevant colNm to the Bean Object and returns
	 * @param bean
	 * @param colNm
	 * @throws Exception
	 * @deprecated Use {@link DynamicBeanBinding#populateBean(Object,String,String)} instead
	 */
	public static void populateBean(Object bean, String colNm, String colVal) throws Exception {
		DynamicBeanBinding.populateBean(bean, colNm, colVal);
	}
	

	/**
	 * _populateBeanFromMap recieves API call from different PageRender Components and populates 
	 * the attribute(DATA_LOCATION) values from answerMap 
	 * @param questionMap
	 * @param bean
	 * @param answerMap
	 * @param prefix
	 * @throws Exception
	 * @deprecated Use {@link DynamicBeanBinding#populateBeanFromMap(Map,Object,Map,String)} instead
	 */
	public static void populateBeanFromMap(Map<Object, Object> questionMap, Object bean, Map<Object, Object> answerMap, String prefix) throws Exception {
		DynamicBeanBinding.populateBeanFromMap(questionMap, bean, answerMap,
				prefix);
	}	
	
	/**
	 * @deprecated Use {@link DynamicBeanBinding#transferBeanValues(Map<Object, Object>,Object,Map<Object, Object>,String)} instead
	 */
	public static boolean transferBeanValues(Map<Object, Object> questionMap, Object bean, Map<Object, Object>  answerMap, String prefix) throws Exception {
		return DynamicBeanBinding.transferBeanValues(questionMap, bean,
				answerMap, prefix);
	}	
	
}
