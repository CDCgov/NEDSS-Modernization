package gov.cdc.nedss.webapp.nbs.queue;



import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class GenericQueueUtil {
	
	static final LogUtils logger = new LogUtils(GenericQueueUtil.class.getName());
	QueueUtil queueUtil = new QueueUtil();
	PropertyUtil propertyUtil= PropertyUtil.getInstance();
	static final String DATE_FILTER_TYPE="0";
	static final String TEXT_FILTER_TYPE="1";
	static final String MULTISELECT_FILTER_TYPE="2";
	
	
	/**
	 * initializeDropdowns: generic method for initializing the dropdowns based on the type of dropdown and the method to get the values from the form
	 * @param elementColls
	 * @param queueCollection
	 * @return
	 */
	public ArrayList<ArrayList<Object>> initializeDropdowns(Collection<Object>  elementColls, QueueDT queueDT, String className){
		

		ArrayList<Object> dropdown;
		ArrayList<ArrayList<Object>> dropdowns = new ArrayList<ArrayList<Object>>();;
		
		String previousMethod="";
    	QueueColumnDT result;
    	int size = queueDT.getQueueDTSize();
    	
    	for(int i=1; i<=size; i++){
    		try{

				result=queueDT.getColumn(i);
	
				String filterType = result.getFilterType();
				String method2 = result.getMethodFromElement();
				String multipleValues = result.getMultipleValues();
				
				if(filterType!=null){
					
					
					if(filterType==DATE_FILTER_TYPE){//date
						if(method2!=previousMethod){
							dropdown=new ArrayList<Object>();
							dropdown=getDropdownDate(elementColls);
							dropdowns.add(dropdown);
						}
						previousMethod=method2;
					}else
						if(filterType==MULTISELECT_FILTER_TYPE){//multiselect
							if(method2!=previousMethod){
								dropdown=new ArrayList<Object>();
								if(multipleValues=="true")
									dropdown=getDropdownMultiselectValues(elementColls, method2, className);
								else
									dropdown=getDropdownMultiselect(elementColls, method2, className);
								
								dropdowns.add(dropdown);
							}
							previousMethod=method2;
						}
				}
			
    		}catch(Exception e){
    			logger.error("Error in File: GenericQueueUtil.java Method: initializeDropdowns: " + e.toString(),e);
    		}
    	}
	
	
	return dropdowns;
    	
    	
    	
	}
	
	/**
	 * getDropdownMultiselect: get the values for the dropdown of type multiselect
	 * @param elementColls
	 * @param method
	 * @return
	 */
	
	private ArrayList<Object> getDropdownMultiselect(Collection<Object> elementColls, String method, String className){
		

		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		try{
			if (elementColls != null) {
				Iterator<Object>  iter = elementColls.iterator();
				
				Class _class = Class.forName(className);
				
				while (iter.hasNext()) {
	
					AbstractVO elementSummaryVO = (AbstractVO) _class.cast(iter.next()); 
					
					String result = null;
					try{
						Class tClass = elementSummaryVO.getClass();
						Method gs1Method = tClass.getMethod(method, new Class[] {});
						result =  (String) gs1Method.invoke(elementSummaryVO, new Object[] {});
						
					}catch(Exception e){
		    			logger.error("Error in File: GenericQueueUtil.java Method: getDropdownMultiselect: " + e.toString());
		                e.printStackTrace();
		    			
		    		}
					
					if (result!= null && result.trim().length()>0) {
						invMap.put(result,result);
					}
					if(result == null || result.trim().equals("")){
						invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
	
			}
		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: getDropdownMultiselect: " + e.toString(), e);
		}
		return queueUtil.getUniqueValueFromMap(invMap);

		
	}

	/**
	 * getDropdownMultiselectValues: get the values for the dropdown of type multiselect with more than one value, like Resulted test
	 * @param elementColls
	 * @param method
	 * @return
	 */
	
	private ArrayList<Object> getDropdownMultiselectValues(Collection<Object>  elementColls, String method, String className){
		Map<Object, Object>  rTestMap = new HashMap<Object,Object>();
		try{
			
			if (elementColls != null) {
				Iterator<Object>  iter = elementColls.iterator();
				ArrayList<String> result = new ArrayList<String>();
				
				Class _class = Class.forName(className);
				
				while (iter.hasNext()) {
	
					AbstractVO elementSummaryVO = (AbstractVO) _class.cast(iter.next()); 
					
					try{
						Class tClass = elementSummaryVO.getClass();
						Method gs1Method = tClass.getMethod(method, new Class[] {});
						result = (ArrayList<String>) gs1Method.invoke(elementSummaryVO, new Object[] {});
					}catch(Exception e){
		    			logger.error("Error in File: GenericQueueUtil.java Method: getDropdownMultiselectValues: " + e.toString());
		                e.printStackTrace();
		    			
		    		}
					
					for(int i=0; result!=null && i<result.size(); i++){
						if(result!= null && result.get(i)!=null){
						
							rTestMap = getTestsfromObs(result.get(i),rTestMap);
						}
						if(result == null || result.get(i)==null || result.get(i).trim().equals("")){
							rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
						}
					}
					if(result!=null && result.size()==0)
						rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					
				}
			}
		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: getDropdownMultiselectValues: " + e.toString(), e);
		}
		return queueUtil.getUniqueValueFromMap(rTestMap); 
	}
	
	
	
	/**
	 * getDropdownDate: get the values for the dropdown of type date
	 * @param elementColls
	 * @return
	 */
		
	private ArrayList<Object> getDropdownDate(Collection<Object> elementColls){
		ArrayList<Object> filterColl = new ArrayList<Object>();
		try{
	        DropDownCodeDT cdDT = new DropDownCodeDT();
	        cdDT.setKey(NEDSSConstants.DATE_BLANK_KEY);
	        cdDT.setValue(NEDSSConstants.BLANK_VALUE);
	        filterColl.add(cdDT);
	        ArrayList<Object> list = CachedDropDowns.getCodedValueOrderdByNbsUid("NBS_DATE_FILTER");
	        filterColl.addAll(list);
	        
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: getDropdownDate: " + ex.toString(), ex);
		}
		return filterColl;
    }
		   
	/**
	 * getFilteredQueue: method for filtering based on the type (text, date, multiselect) and sort method
	 * @param elementSummaryVOs
	 * @param searchCriteriaMap
	 * @param queueCollection
	 * @return
	 */
		
	private Collection<Object>  getFilteredQueue(Collection<Object>  elementSummaryVOs,
			Map<Object, Object>  searchCriteriaMap, QueueDT queueDT, String className) {
		try{
			int size = queueDT.getQueueDTSize();
			
			for(int i=1;i<=size; i++){
				
				String filterType = queueDT.getColumn(i).getFilterType();
				String dropdownProperty =  queueDT.getColumn(i).getDropdownProperty();
				String filterText = null;
				String[] filterColumn;
				
				Map<Object, Object>  filterMap = new HashMap<Object,Object>();
				
				if(filterType!=null)//Like PA hidden
				switch (filterType){
				
				
				case DATE_FILTER_TYPE://date
					filterColumn = (String[]) searchCriteriaMap.get(dropdownProperty);
					if (filterColumn != null && filterColumn.length > 0)
						filterMap = queueUtil.getMapFromStringArray(filterColumn);
					
					if(filterMap != null && filterMap.size()>0)
						elementSummaryVOs = filterQueueByDate(elementSummaryVOs,filterMap, queueDT.getColumn(i).getSortNameMethod(), className);
					
					
					break;
				
				case TEXT_FILTER_TYPE://text
					if(searchCriteriaMap.get(dropdownProperty+"_FILTER_TEXT")!=null)
						filterText = (String) searchCriteriaMap.get(dropdownProperty+"_FILTER_TEXT");
					
					
					if(filterText!= null)
						elementSummaryVOs = filterByText(elementSummaryVOs, filterText, queueDT.getColumn(i).getSortNameMethod(), className);
					
					
					break;
					
				case MULTISELECT_FILTER_TYPE://multiselect
					filterColumn = (String[]) searchCriteriaMap.get(dropdownProperty);
					String method = queueDT.getColumn(i).getMethodFromElement();
					if (filterColumn != null && filterColumn.length > 0)
					filterMap = queueUtil.getMapFromStringArray(filterColumn);
					if(filterMap!=null){
					String multipleValues = queueDT.getColumn(i).getMultipleValues();
					
					if (filterMap != null && filterMap.size()>0)
						if(multipleValues!=null && multipleValues.equalsIgnoreCase("true"))
							elementSummaryVOs = filterQueueByMultiSelectValues(elementSummaryVOs,filterMap, method, className);
						else
							elementSummaryVOs = filterQueueByMultiSelect(
								elementSummaryVOs, filterMap, method, className);
					}
					break;
					
					
				}
			}
    	
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: getFilteredQueue: " + ex.toString(), ex);
		}
		return elementSummaryVOs;
		
	}
	
	/**
	 * filterByText: method for filtering by text
	 * @param elementSummaryVOs
	 * @param filterByText
	 * @param methodToInvoke
	 * @return
	 */
	private Collection<Object>  filterByText(
			Collection<Object>  elementSummaryVOs, String filterByText,String methodToInvoke, String className) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
			if (elementSummaryVOs != null) {
				Iterator<Object> iter = elementSummaryVOs.iterator();
				Class _class = Class.forName(className);
				
				while (iter.hasNext()) {
	
					Object result = null;
	
					AbstractVO elementSummaryVO = (AbstractVO) _class.cast(iter.next()); 
					Class tClass = elementSummaryVO.getClass();
					Method gs1Method = tClass.getMethod(methodToInvoke, new Class[] {});
					result = (String) gs1Method.invoke(elementSummaryVO, new Object[] {});
					if(result!=null){
						
						if(result.toString().toUpperCase().contains(filterByText.toUpperCase()))
							newInvColl.add(elementSummaryVO);
					}
				}
			}
		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: filterByText: " + e.toString(), e);
		}
		return newInvColl;
	}
	

	
	/**
	 * filterQueueByMultiSelect(): method for filtering by multiselect with a single value
	 * @param elementColls
	 * @param filterMap
	 * @param methodToInvoke
	 * @return
	 */
	private Collection<Object>  filterQueueByMultiSelect(
			Collection<Object>  elementColls, Map<Object,Object> filterMap, String methodToInvoke, String className) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
			if (elementColls != null) {
				Iterator<Object>  iter = elementColls.iterator();
				Class _class = Class.forName(className);
				while (iter.hasNext()) {
	
					AbstractVO elementSummaryVO = (AbstractVO) _class.cast(iter.next()); 
					String result=null;
				try{
					
					Class tClass = elementSummaryVO.getClass();
					Method gs1Method = tClass.getMethod(methodToInvoke, new Class[] {});
					result = (String) gs1Method.invoke(elementSummaryVO, new Object[] {});
					
				}catch(InvocationTargetException e1){
					
					logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByMultiSelect: " + e1.toString());
		            e1.printStackTrace();
					
				}catch(Exception e){
					logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByMultiSelect: " + e.toString());
		            e.printStackTrace();
					
				}
				
					if (result!= null
							&& filterMap != null
							&& filterMap.containsKey(result)) {
						newInvColl.add(elementSummaryVO);
					}
					if(result == null || result.equals("")){
						if(filterMap != null && filterMap.containsKey(NEDSSConstants.BLANK_KEY)){//Change it to BLANK_VALUE??
							newInvColl.add(elementSummaryVO);
						}
					}
	
				}
	
			}
		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByMultiSelect: " + e.toString(), e);
		}
		return newInvColl;
	}
	
	
	/**
	 * filterQueueByMultiSelectValues(): method for filtering by multiselect with more than one vlaue, like Resulted test.
	 * @param elementColls
	 * @param testMap
	 * @param methodToInvoke
	 * @return
	 */
	private Collection<Object>  filterQueueByMultiSelectValues(
			Collection<Object>  elementColls, Map<Object,Object> testMap, String methodToInvoke, String className) {
		Collection<Object>  newObsColl = new ArrayList<Object> ();
		try{
			if (elementColls != null) {
				Iterator<Object>  iter = elementColls.iterator();
				Class _class = Class.forName(className);
				while (iter.hasNext()) {
					AbstractVO elementSummaryVO = (AbstractVO) _class.cast(iter.next()); 
					ArrayList<String> result = null;
					
					try{
					Class tClass = elementSummaryVO.getClass();
					Method gs1Method = tClass.getMethod(methodToInvoke, new Class[] {});
					result = (ArrayList<String>)gs1Method.invoke(elementSummaryVO, new Object[] {});
					}catch(InvocationTargetException e1){
						logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByMultiSelectValues: " + e1.toString());
			            e1.printStackTrace();
						
					}catch(Exception e){
		    			logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByMultiSelectValues: " + e.toString());
		                e.printStackTrace();
		    			
		    		}
					
					if (result!= null
							&& testMap != null){
					
						
						boolean found = false;
						for(int i=0; i<result.size() && !found;i++){
						
								String test = result.get(i);
								if(test!=null) {
								if(testMap.containsKey(test)){
									newObsColl.add(elementSummaryVO);
									found=true;
									break;
								
								}
								}
								if(result.get(i)==null || result.get(i).trim().equals("")){
									if(testMap != null && testMap.containsKey(NEDSSConstants.BLANK_KEY)){
										newObsColl.add(elementSummaryVO);
										found=true;
										break;
									}
								}
						}
	
					}if(result==null || result.size() == 0){
						if(testMap != null && testMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newObsColl.add(elementSummaryVO);
						}
					}
				}
	
			}
		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByMultiSelectValues: " + e.toString(), e);
		}
		return newObsColl;

	}
	
	/**
	 * filterQueueByDate: method for filtering by date
	 * @param elementColls
	 * @param dateMap
	 * @param methodToInvoke
	 * @return
	 */
	
	private Collection<Object>  filterQueueByDate(
			Collection<Object>  elementColls, Map<Object,Object> dateMap, String methodToInvoke, String className) {
		Map<Object, Object>  newObsMap = new HashMap<Object,Object>();
		String strDateKey = null;
		try{
			if (elementColls != null) {
				Iterator<Object>  iter = elementColls.iterator();
				Class _class = Class.forName(className);
				
				Integer key = 1;
				while (iter.hasNext()) {
	
					AbstractVO elementSummaryVO = (AbstractVO) _class.cast(iter.next()); 
					Object date=null;
					try{					
						Class tClass = elementSummaryVO.getClass();
						Method gs1Method = tClass.getMethod(methodToInvoke, new Class[] {});
						date =  gs1Method.invoke(elementSummaryVO, new Object[] {});
						
					}catch(Exception e){
						logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByDate: " + e.toString());
			            e.printStackTrace();
					}
				
					if (date!= null && dateMap != null
							&& (dateMap.size()>0 )) {
						
						java.sql.Timestamp time=null;
						time = (Timestamp)date;
						Collection<Object>  dateSet = dateMap.keySet();
						if(dateSet != null){
							Iterator<Object>  iSet = dateSet.iterator();
						while (iSet.hasNext()){
							 strDateKey = (String)iSet.next();
							if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
	                    	   if(queueUtil.isDateinRange(time,strDateKey)){
	                    		   newObsMap.put(key.toString(), elementSummaryVO);
	                    	   }	
	                           		
							}  
	                       }
						  }
						}
			
					if(date == null || date.equals("")){
						if(dateMap != null && (dateMap.containsKey(NEDSSConstants.BLANK_KEY)) ||(dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)) ){
							newObsMap.put(key.toString(), elementSummaryVO);//??
						}
					}
					key++;
	
				}
			} 	

		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: filterQueueByDate: " + e.toString(), e);
		}
		return queueUtil.convertMaptoColl(newObsMap);

	}
	
	private Map<Object,Object> getTestsfromObs(String tests, Map<Object,Object> rTestMap){
		try{
			String[] strArr = tests.split("<BR>");
	
			if(strArr != null){ 
				for(int i=0;i<strArr.length;i++){
					rTestMap.put(strArr[i],strArr[i]);
				}
			}
		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: getTestsfromObs: " + e.toString(), e);
		}
		return rTestMap;
	}
	
	/**
	 * filterQueue(): general method for filtering. This is the one called from the load java file
	 * @param genericForm
	 * @param request
	 * @return
	 */
	public Collection<Object>  filterQueue(GenericForm genericForm, HttpServletRequest request, String className) {
		
		Collection<Object>  elementSummaryVOs = new ArrayList<Object> ();

		String sortOrderParam = null;
		
		try {
			
			Map<Object, Object>  searchCriteriaMap = genericForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> elemSummaryVOs = (ArrayList<Object> ) genericForm.getElementColl();

			QueueDT queueDT = genericForm.getQueueDT();
			elementSummaryVOs = getFilteredQueue(elemSummaryVOs, searchCriteriaMap, queueDT, className);

			ArrayList<String[]> criteriaMap = new ArrayList();
			ArrayList<String> constantMap = new ArrayList();
			ArrayList<String> criteriaMapText = new ArrayList();
			ArrayList<String> backendMapText = new ArrayList();
			
			
			for(int i=1; i<=queueDT.getQueueDTSize(); i++){
				QueueColumnDT column = queueDT.getColumn(i);
				
				String dropdownProperty = column.getDropdownProperty();
				String filterType = column.getFilterType();
				String backendId = column.getBackendId();
				String constantCount = column.getConstantCount();
				
				if(filterType!=null){
				if(filterType.equalsIgnoreCase(DATE_FILTER_TYPE) || filterType.equalsIgnoreCase(MULTISELECT_FILTER_TYPE)){
					criteriaMap.add( (String[]) searchCriteriaMap.get(dropdownProperty));
					constantMap.add(constantCount);
				}
				else{//Text
					
					String filter = null;
	    			if(searchCriteriaMap.get(dropdownProperty+"_FILTER_TEXT")!=null){
	    				filter = (String) searchCriteriaMap.get(dropdownProperty+"_FILTER_TEXT");
	    				request.setAttribute(backendId, filter);
	    				criteriaMapText.add(filter);
	    				backendMapText.add(backendId);
	    			}
				}
			}
			}
			
			
			ArrayList<Integer> count = new ArrayList();
			
			for(int i=0; i<criteriaMap.size();i++){
				String[] criteria = criteriaMap.get(i);
				count.add(new Integer(criteria == null  ? 0 : criteria.length));
			}
			
			boolean countBoolean=true;
			
			for(int i=0; i<count.size();i++){
				countBoolean = countBoolean && count.get(i).equals(genericForm.getAttributeMap().get(constantMap.get(i)));
			}

			if(countBoolean && (criteriaMapText==null || criteriaMapText.size()==0))
			{
				String sortMethod = getSortMethod(request, genericForm);
				String direction = getSortDirection(request, genericForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<Object, Object>  sColl =  genericForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) genericForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = getSortCriteria(direction, sortMethod, genericForm.getQueueDT());
				}				
				Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				genericForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}
			
			Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
			String sortMethod = getSortMethod(request, genericForm);
			String direction = getSortDirection(request, genericForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object>  sColl =  genericForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) genericForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = getSortCriteria(direction, sortMethod, genericForm.getQueueDT());
			}
			

	
			int index=0;
			int indexText=0;
			String srchCriteria = null;
			
			for(int i=1; i<=queueDT.getQueueDTSize(); i++){
				QueueColumnDT queue = queueDT.getColumn(i);
				
				String filterType = queue.getFilterType();
				String backendId = queue.getBackendId();
				String methodToInvoke = queue.getMethodGeneralFromForm();
				String filterByConstant = queue.getFilterByConstant();
				ArrayList<Object> list;
				
				if(filterType!=null){
				if(filterType.equalsIgnoreCase(DATE_FILTER_TYPE) || filterType.equalsIgnoreCase(MULTISELECT_FILTER_TYPE)){
					
					Class tClass = genericForm.getClass();
					Method gs1Method = tClass.getMethod(methodToInvoke, new Class[] {});
					list =  (ArrayList<Object>)gs1Method.invoke(genericForm, new Object[] {});
					
					String[] value = criteriaMap.get(index);
					
					srchCriteria = queueUtil.getSearchCriteria(list, value, filterByConstant);
					index++;
					
	    			if(srchCriteria != null)
	    				searchCriteriaColl.put(backendId, srchCriteria);
	    			
					
				}else{//text
					if(criteriaMapText.size()!=0 && indexText<criteriaMapText.size()){			
    					String filterText =criteriaMapText.get(indexText);
    					String backend = backendMapText.get(indexText);
    					if(backendId.equalsIgnoreCase(backend)){
    						searchCriteriaColl.put(backend, filterText);
    						indexText++;
    					}
					}
					
				}
			}
			}
						
			searchCriteriaColl.put("sortSt", sortOrderParam);
			genericForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: filterQueue: " + e.toString(), e);
		}
		return elementSummaryVOs;
	}
	
	/**
	 * filterQueue(): general method for filtering. This is the one called from the load java file
	 * @param genericForm
	 * @param request
	 * @return
	 */
	public Collection<Object>  filterQueueWithDefaultCodedFilter(GenericForm genericForm, HttpServletRequest request, String className, Map<Object, Object> defaultFilter) {
		
		Collection<Object>  elementSummaryVOs = new ArrayList<Object> ();

		String sortOrderParam = null;
		
		try {
			
			Map<Object, Object>  searchCriteriaMap = genericForm.getSearchCriteriaArrayMap();
			
			logger.debug("searchCriteriaMap::::::::"+searchCriteriaMap);
			
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> elemSummaryVOs = (ArrayList<Object> ) genericForm.getElementColl();

			QueueDT queueDT = genericForm.getQueueDT();
			elementSummaryVOs = getFilteredQueue(elemSummaryVOs, searchCriteriaMap, queueDT, className);

			ArrayList<String[]> criteriaMap = new ArrayList();
			ArrayList<String> constantMap = new ArrayList();
			ArrayList<String> criteriaMapText = new ArrayList();
			ArrayList<String> backendMapText = new ArrayList();
			
			
			for(int i=1; i<=queueDT.getQueueDTSize(); i++){
				QueueColumnDT column = queueDT.getColumn(i);
				
				String dropdownProperty = column.getDropdownProperty();
				String filterType = column.getFilterType();
				String backendId = column.getBackendId();
				String constantCount = column.getConstantCount();
				
				if(filterType!=null){
				if(filterType.equalsIgnoreCase(DATE_FILTER_TYPE) || filterType.equalsIgnoreCase(MULTISELECT_FILTER_TYPE)){
					criteriaMap.add( (String[]) defaultFilter.get(dropdownProperty));
					constantMap.add(constantCount);
				}
				else{//Text
					
					String filter = null;
	    			if(defaultFilter.get(dropdownProperty+"_FILTER_TEXT")!=null){
	    				filter = (String) defaultFilter.get(dropdownProperty+"_FILTER_TEXT");
	    				request.setAttribute(backendId, filter);
	    				criteriaMapText.add(filter);
	    				backendMapText.add(backendId);
	    			}
				}
			}
			}
			
			
			ArrayList<Integer> count = new ArrayList();
			
			for(int i=0; i<criteriaMap.size();i++){
				String[] criteria = criteriaMap.get(i);
				count.add(new Integer(criteria == null  ? 0 : criteria.length));
			}
			
			boolean countBoolean=true;
			
			for(int i=0; i<count.size();i++){
				countBoolean = countBoolean && count.get(i).equals(genericForm.getAttributeMap().get(constantMap.get(i)));
			}

			/*if(countBoolean && (criteriaMapText==null || criteriaMapText.size()==0))
			{
				String sortMethod = getSortMethod(request, genericForm);
				String direction = getSortDirection(request, genericForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<Object, Object>  sColl =  genericForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) genericForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = getSortCriteria(direction, sortMethod, genericForm.getQueueDT());
				}				
				Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				genericForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}*/
			
			Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
			String sortMethod = getSortMethod(request, genericForm);
			String direction = getSortDirection(request, genericForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object>  sColl =  genericForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) genericForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = getSortCriteria(direction, sortMethod, genericForm.getQueueDT());
			}
			
	
			
			int index=0;
			int indexText=0;
			String srchCriteria = null;
			
			for(int i=1; i<=queueDT.getQueueDTSize(); i++){
				QueueColumnDT queue = queueDT.getColumn(i);
				
				String filterType = queue.getFilterType();
				String backendId = queue.getBackendId();
				String methodToInvoke = queue.getMethodGeneralFromForm();
				String filterByConstant = queue.getFilterByConstant();
				ArrayList<Object> list;
				
				if(filterType!=null){
				if(filterType.equalsIgnoreCase(DATE_FILTER_TYPE) || filterType.equalsIgnoreCase(MULTISELECT_FILTER_TYPE)){
					
					Class tClass = genericForm.getClass();
					Method gs1Method = tClass.getMethod(methodToInvoke, new Class[] {});
					list =  (ArrayList<Object>)gs1Method.invoke(genericForm, new Object[] {});
					
					String[] value = criteriaMap.get(index);
					
					srchCriteria = queueUtil.getSearchCriteria(list, value, filterByConstant);
					index++;
					
	    			if(srchCriteria != null)
	    				searchCriteriaColl.put(backendId, srchCriteria);
	    			
					
				}else{//text
					if(criteriaMapText.size()!=0 && indexText<criteriaMapText.size()){			
    					String filterText =criteriaMapText.get(indexText);
    					String backend = backendMapText.get(indexText);
    					if(backendId.equalsIgnoreCase(backend)){
    						searchCriteriaColl.put(backend, filterText);
    						indexText++;
    					}
					}
					
				}
			}
			}
						
			searchCriteriaColl.put("sortSt", sortOrderParam);
			genericForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		}catch(Exception e){
			logger.error("Error in File: GenericQueueUtil.java Method: filterQueue: " + e.toString(), e);
		}
		return elementSummaryVOs;
	}
	
	/*private Collection<Object> getFilteredQueueWithDefaultCodedFilter(Collection<Object> elementSummaryVOs,
			Map<Object, Object> defaultFilter, QueueDT queueDT, String className) {
		// TODO Auto-generated method stub
		try{
			int size = queueDT.getQueueDTSize();
			
			for(int i=1;i<=size; i++){
				
				String filterType = queueDT.getColumn(i).getFilterType();
				String dropdownProperty =  queueDT.getColumn(i).getDropdownProperty();
				String filterText = null;
				String[] filterColumn;
				
				Map<Object, Object>  filterMap = new HashMap<Object,Object>();
				
				if(filterType!=null)//Like PA hidden
				switch (filterType){
				
				
				case DATE_FILTER_TYPE://date
					filterColumn = (String[]) defaultFilter.get(dropdownProperty);
					if (filterColumn != null && filterColumn.length > 0)
						filterMap = queueUtil.getMapFromStringArray(filterColumn);
					
					if(filterMap != null && filterMap.size()>0)
						elementSummaryVOs = filterQueueByDate(elementSummaryVOs,filterMap, queueDT.getColumn(i).getSortNameMethod(), className);
					
					
					break;
				
				case TEXT_FILTER_TYPE://text
					if(defaultFilter.get(dropdownProperty+"_FILTER_TEXT")!=null)
						filterText = (String) defaultFilter.get(dropdownProperty+"_FILTER_TEXT");
					
					
					if(filterText!= null)
						elementSummaryVOs = filterByText(elementSummaryVOs, filterText, queueDT.getColumn(i).getSortNameMethod(), className);
					
					
					break;
					
				case MULTISELECT_FILTER_TYPE://multiselect
					filterColumn = (String[]) defaultFilter.get(dropdownProperty);
					String method = queueDT.getColumn(i).getMethodFromElement();
					if (filterColumn != null && filterColumn.length > 0)
					filterMap = queueUtil.getMapFromStringArray(filterColumn);
					if(filterMap!=null){
					String multipleValues = queueDT.getColumn(i).getMultipleValues();
					
					if (filterMap != null && filterMap.size()>0)
						if(multipleValues!=null && multipleValues.equalsIgnoreCase("true"))
							elementSummaryVOs = filterQueueByMultiSelectValues(elementSummaryVOs,filterMap, method, className);
						else
							elementSummaryVOs = filterQueueByMultiSelect(
								elementSummaryVOs, filterMap, method, className);
					}
					break;
					
					
				}
			}
    	
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: getFilteredQueue: " + ex.toString(), ex);
		}
		return elementSummaryVOs;
		
	}*/

	/**
	 * getSortCriteria(): method for getting the sort criteria based on the method and the column name
	 * @param sortOrder
	 * @param methodName
	 * @param queueCollection
	 * @return
	 */
	
	private String getSortCriteria(String sortOrder, String methodName, QueueDT queueDT){
		String sortOrdrStr = null;
		String method="";
		String title="";
		boolean found = false;
		try{
			int size=queueDT.getQueueDTSize();
			
			if(methodName != null) {
				
				
				for(int i=1; i<=size; i++){
					method=queueDT.getColumn(i).getSortNameMethod();
					title=queueDT.getColumn(i).getColumnName();
					
					if(methodName.equals(method)){
						sortOrdrStr=title;
						found =true;
					}
					
				}
				
				if(!found)
					sortOrdrStr = "Date Received";//Generic??
			
			}
			if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr+" in ascending order ";
			else if(sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr+" in descending order ";
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: getSortCriteria: " + ex.toString(), ex);
		}
		return sortOrdrStr;
	}
	

	
	public void sortQueue(GenericForm genericForm, Collection<Object>  elementVOColl, boolean existing, HttpServletRequest request, String sortMethodName) {
		try{
			// Retrieve sort-order and sort-direction from displaytag params
			String sortMethod = getSortMethod(request, genericForm);
			String direction = getSortDirection(request, genericForm);
			boolean invDirectionFlag = true;
			if (direction != null && direction.equals("2"))
				invDirectionFlag = false;
	
			//Read from properties file to determine default sort order
			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					sortMethod = sortMethodName;
					invDirectionFlag = true;
					
			} 
			//else 	invDirectionFlag =!invDirectionFlag;

		
			NedssUtils util = new NedssUtils();
			if (sortMethod != null && elementVOColl != null
					&& elementVOColl.size() > 0) {
				//updateQueueBeforeSorting(elementVOColl);
				util.sortObjectByColumnGeneric(sortMethod,
						(Collection<Object>) elementVOColl, invDirectionFlag);
				//updateQueueAfterSorting(elementVOColl);
			}
			//Changed method sortObjectByColumn to sortObjectByColumnPatientSearch : TO change sorting order alphabetically instead of ASCII-code
			
			if(!existing) {
				//Finally put sort criteria in form
				String sortOrderParam = getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod, genericForm.getQueueDT());
				Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				genericForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			}
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: sortQueue: " + ex.toString(), ex);
		}
	}
	
	/**
	 * getSortMethod(): 
	 * @param request
	 * @param genericForm
	 * @return
	 * @throws NEDSSAppException 
	 */
	
	private String getSortMethod(HttpServletRequest request, GenericForm genericForm) throws NEDSSAppException {
		try{
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			} else{
				return genericForm.getAttributeMap().get("methodName") == null ? null : (String) genericForm.getAttributeMap().get("methodName");
			}
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: getSortMethod: " + ex.toString(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}

	/**
	 * getSortDirection:
	 * @param request
	 * @param genericForm
	 * @return
	 * @throws NEDSSAppException 
	 */
	private String getSortDirection(HttpServletRequest request, GenericForm genericForm) throws NEDSSAppException {
		try{
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			} else{
				return genericForm.getAttributeMap().get("sortOrder") == null ? "1": (String) genericForm.getAttributeMap().get("sortOrder");
			}
		}catch(Exception ex){
			logger.error("Error in File: GenericQueueUtil.java Method: getSortDirection: " + ex.toString(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}
	/**
	 * updateQueueBeforeSorting:
	 * @param elementVOColls
	 */
	private void updateQueueBeforeSorting(Collection<Object>  elementVOColls){
    	if(elementVOColls != null){
    		Iterator<Object> iter = elementVOColls.iterator();
    		/*while (iter.hasNext()) {
    			GenericSummaryDisplayVO obsVOColl = (GenericSummaryDisplayVO) iter.next();
    			if (obsVOColl.getTestsStringNoLnk() == null || (obsVOColl.getTestsStringNoLnk() != null && obsVOColl.getTestsStringNoLnk().equals(""))) {
    				obsVOColl.setTestsStringNoLnk("ZZZZZ");
    				obsVOColl.setTestsStringPrint("ZZZZZ");
    			}
    		}	*/
    	}
    	}
	
	/**
	 * updateQueueAfterSorting
	 * @param elementVOColls
	 */
    	private void updateQueueAfterSorting(Collection<Object>  elementVOColls){
    		if(elementVOColls != null){
        		Iterator<Object> iter = elementVOColls.iterator();
        		/*while (iter.hasNext()) {
        			GenericSummaryDisplayVO obsVOColl = (GenericSummaryDisplayVO) iter.next();
        			if (obsVOColl.getTestsStringNoLnk() != null && obsVOColl.getTestsStringNoLnk().equals("ZZZZZ")) {
        				obsVOColl.setTestsStringNoLnk("");
        				obsVOColl.setTestsStringPrint("");
        			}
        		}	*/
        	}
    	}
    	
    	/**
    	 * setQueueCount:
    	 * @param genericForm
    	 * @param queueCollection
    	 */
    	public void setQueueCount(GenericForm genericForm, ArrayList<QueueColumnDT> queueCollection){
    		try{
	    		Map<Object, Object> map = genericForm.getAttributeMap();
	    		
	    		for(int i=0; i<queueCollection.size(); i++){
	    			QueueColumnDT queue = queueCollection.get(i);
	    			
	    			String constantCount=queue.getConstantCount();
	    			String method=queue.getMethodGeneralFromForm();
	    			String filterType = queue.getFilterType();
	    			
	    			if(filterType!=null && (filterType==DATE_FILTER_TYPE || filterType==MULTISELECT_FILTER_TYPE)){
	    				try{
						Class tClass = genericForm.getClass();
						Method gs1Method = tClass.getMethod(method, new Class[] {});
						ArrayList<Object> result = (ArrayList<Object>) gs1Method.invoke(genericForm, new Object[] {});
						
						int size=result.size();
						map.put(constantCount, new Integer (size));
						
	    				}catch(Exception e){
	    	    			logger.error("Error in File: GenericQueueUtil.java Method: setQueueCount: " + e.toString());
	    	                e.printStackTrace();
	    	    			
	    	    		}
	    				
	    				
	    			}
	    		}
    		}catch(Exception ex){
    			logger.error("Error in File: GenericQueueUtil.java Method: setQueueCount: " + ex.toString(), ex);
    		}
    	}
    	
    	/**
    	 * getColumnString: get the information from each column to be stored in the queueString
    	 * @param queueString
    	 * @param column
    	 * @return
    	 */
        private String getColumnString(String queueString, QueueColumnDT column){
        	try{
	    		queueString += "columnId:" +column.getColumnId()+",";
	    		queueString += "columnName:" +column.getColumnName()+",";
	    		//queueString += "columnPropertyName:" +result.getColumnPropertyName()+",";
	    		queueString += "mediaHtmlProperty:" +column.getMediaHtmlProperty()+",";
	    		queueString += "mediaPdfProperty:" +column.getMediaPdfProperty()+",";
	    		queueString += "mediaCsvProperty:" +column.getMediaCsvProperty()+",";
	    		queueString += "backendId:" +column.getBackendId()+",";
	    		queueString += "defaultOrder:" +column.getDefaultOrder()+",";
	    		queueString += "sortable:" +column.getSortable()+",";
	    		queueString += "sortNameMethod:" +column.getSortNameMethod()+",";
	    		queueString += "media:" +column.getMedia()+",";
	    		queueString += "columnStyle:" +column.getColumnStyle()+",";
	    		queueString += "filterType:" +column.getFilterType()+",";
	    		queueString += "dropdownProperty:" +column.getDropdownProperty()+",";
	    		queueString += "dropdownStyleId:" +column.getDropdownStyleId()+",";
	    		queueString += "dropdownValues:" +column.getDropdownsValues()+",";
	    		queueString += "errorIdFiltering:" +column.getErrorIdFiltering()+",";
	    		queueString += "constantCount:" +column.getConstantCount()+",";
	    		//queueString += "searchCriteriaConstant:" +column.getSearchCriteriaConstant()+",";
	    		queueString += "className:" +column.getClassName()+",";
	    		queueString += "headerClass:" +column.getHeaderClass()+"#";
        	}catch(Exception ex){
    			logger.error("Error in File: GenericQueueUtil.java Method: getColumnString: " + ex.toString(), ex);
    		}
    		return queueString;
        }
        
        /**
         * convertToString: converts each column metadata in a String to be read from the JSP and JavaScript files
         * @param queueDT
         * @return
         */
        public String convertToString(QueueDT queueDT){
        	
        	String queueString = "";
        	QueueColumnDT column;
        	try{
	        	for(int i=1; i<=queueDT.getQueueDTSize(); i++){
	        		try{
	        			
	        		column=queueDT.getColumn(i);
	        		queueString=getColumnString(queueString, column);
	
	        		}catch(Exception e){
	        			logger.error("Error in File: GenericQueueUtil.java Method: convertToString: " + e.toString());
	                    e.printStackTrace();
	        			
	        		}
	        	}
	        		
	    		for(int i=1; i<=queueDT.getQueueDTHiddenSize(); i++){
	        		try{
	        		column=queueDT.getColumnHidden(i);
	        		queueString=getColumnString(queueString, column);
	        		        		
	        		}catch(Exception e){
	        			logger.error("Error in File: GenericQueueUtil.java Method: convertToString: " + e.toString());
	                    e.printStackTrace();
	        			
	        		}     		
	        	}
        	}catch(Exception ex){
    			logger.error("Error in File: GenericQueueUtil.java Method: convertToString: " + ex.toString(), ex);
    		}
        	return queueString;
        }
    	/**
    	 * convertQueueDTToList(): for passing the list of columns to the JSP
    	 * @param queueDT
    	 * @return
    	 */
    	public ArrayList<QueueColumnDT> convertQueueDTToList(QueueDT queueDT){
    		ArrayList<QueueColumnDT> collection = new ArrayList<QueueColumnDT>();
    		try{
	    		int sizeVisibleColumn=queueDT.getQueueDTSize();
	    		for(int i=1; i<=sizeVisibleColumn;i++){
	    			collection.add(queueDT.getColumn(i));
	    		}
	    		
	    		int sizeHiddenColumn=queueDT.getQueueDTHiddenSize();
	    		for(int i=1; i<=sizeHiddenColumn;i++){
	    			collection.add(queueDT.getColumnHidden(i));
	    		}
    		}catch(Exception ex){
    			logger.error("Error in File: GenericQueueUtil.java Method: convertQueueDTToList: " + ex.toString(), ex);
    		}
    		return collection;
    	}
    	
    	public Map<Object, Object> setQueueCount(GenericForm genericForm, QueueDT queueDT){
    		
    		Map<Object, Object> map = genericForm.getAttributeMap();

    		String method ="getColumn";
    		String methodOriginal =method;
    		
        	QueueColumnDT result;
        	Class tClass = queueDT.getClass();
        	
        	for(int i=1; i<=queueDT.getQueueDTSize(); i++){
        		try{
        			method=methodOriginal;
    				method+=i;
    				Method gs1Method = tClass.getMethod(method, new Class[] {});
    				result = (QueueColumnDT) gs1Method.invoke(queueDT, new Object[] {});
    	
    				String constantCount=result.getConstantCount();
    				String method2=result.getMethodGeneralFromForm();
    				String filterType = result.getFilterType();
    	
    				if(filterType!=null && (filterType==DATE_FILTER_TYPE || filterType==MULTISELECT_FILTER_TYPE)){
    					try{
    						Class tClass2 = genericForm.getClass();
    						Method gs1Method2 = tClass2.getMethod(method2, new Class[] {});
    						ArrayList<Object> result2 = (ArrayList<Object>) gs1Method2.invoke(genericForm, new Object[] {});
    						
    						int size=result2.size();
    						map.put(constantCount, new Integer (size));
    					
    					}catch(Exception e){
    						logger.error("Error in File: GenericQueueUtil.java Method: setQueueCount: " + e.toString());
    	        			e.printStackTrace();
    					}
    					
    					
    				}

        		}
        		catch(Exception e){
        			logger.error("Error in File: GenericQueueUtil.java Method: setQueueCount: " + e.toString(), e);
        		}
        	}
        	
        	return map;
        }
        
}
