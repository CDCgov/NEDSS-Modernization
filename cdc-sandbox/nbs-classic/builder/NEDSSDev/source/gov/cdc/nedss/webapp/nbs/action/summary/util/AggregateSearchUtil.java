package gov.cdc.nedss.webapp.nbs.action.summary.util;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.AggregateSummaryResultVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.util.WumUtil;
import gov.cdc.nedss.webapp.nbs.action.summary.dt.AggregateSummaryResultsDT;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.summary.AggregateSummaryForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

/**
 * Search Util class implemented for Aggregate Summary Reporting
 * @author NBS Development Team
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * AggregateSearchUtil.java
 * Aug 20, 2009
 * @version
 */
public class AggregateSearchUtil {

	static LogUtils logger = new LogUtils(AggregateSearchUtil.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	static int TOTAL_COUNT_TOTAL_TMUID = 0;
	public static CachedDropDownValues cdv = new CachedDropDownValues();
	/**
	 * 
	 * @param form
	 * @param req
	 */
	public static void searchSubmit(AggregateSummaryForm form, HttpServletRequest req) {
		
		MainSessionCommand msCommand = null;
    	String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
        String sMethod = "getAggregateSummaryColl";
        Map<Object,Object> inpMap = (Map<Object,Object>)form.getSearchMap();
        Map<Object,Object> selectedMap = new HashMap<Object,Object>();
        Map<?,?> questionMap = AggregateSummaryUtil.getQuestionMap();
       Iterator<Map.Entry<Object, Object>>  it = inpMap.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
            if(pairs != null && pairs.getValue() != null){
            	NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(pairs.getKey());
            	if(metaData != null){
                    String key = metaData.getDataLocation();
                    if(pairs.getValue() != null && !pairs.getValue().equals(""))
                    	selectedMap.put(key, pairs.getValue());  
            	}
            }
        }
        
        try {
			Object[] searchParams = new Object[] {selectedMap};          
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(req.getSession());
      
			ArrayList<?> result = (ArrayList<?> ) msCommand.processRequest(sBeanJndiName, sMethod, searchParams);
			setSummaryResultToForm(result, form, req);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NEDSSConcurrentDataException e) {
			e.printStackTrace();
		} catch (NEDSSAppException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	 /**
	  * Sets array of  results DT to the Form. 
	  * @param AggregateSummaryResultsDT
	  * @return
	  */
	 public static void setSummaryResultToForm(ArrayList<?> alist,AggregateSummaryForm form, HttpServletRequest req) {
		 
		 //Based on FormCd, set the TableMetaDataUid for TotalCount (any other way??)
		 if(form.getFormCd() != null && form.getFormCd().equalsIgnoreCase(NBSConstantUtil.INV_FORM_FLU))
			 TOTAL_COUNT_TOTAL_TMUID = 22;
		 
		 //set Security if user has permission to view Add New Report Button
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		boolean addEditPermission = nbsSecurityObj.getPermission(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD);
		form.getAttributeMap().put("addButton", String.valueOf(addEditPermission));
		boolean viewPermission = nbsSecurityObj.getPermission(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.VIEW);
		

		 int count =0;
		 ArrayList<Object> returnList = new ArrayList<Object> ();
		 ArrayList<?> resultArr = null;
		 DropDownCodeDT ddDT = new DropDownCodeDT();
		 try{
				 if(alist != null && alist.size() > 0) {					 
					 resultArr = (ArrayList<?> )alist.get(0);
					 form.getAttributeMap().put("queueCount", String.valueOf(resultArr.size()));
					Iterator<?> iter = resultArr.iterator();
					CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
					 Map<?,?> questionMap = AggregateSummaryUtil.getQuestionMap();
					while(iter.hasNext()) {					
						AggregateSummaryResultVO vo = (AggregateSummaryResultVO) iter.next();
						   if(vo != null){
							   boolean notifExists = false;
							   AggregateSummaryResultsDT dt = new AggregateSummaryResultsDT();
							   Collection<Object>  ansCol =  vo.getNbsCaseAnswerDTColl();
							  Iterator<Object>  it = ansCol.iterator();
							   if(vo.getPublicHealthCaseDT() != null){
								   dt.setPhc_UID(vo.getPublicHealthCaseDT().getPublicHealthCaseUid());
								   
								   NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get("SUM100");
								   ArrayList<?> aList = form.getDwrDefaultStateCounties();
								   for (int i = 0; i < aList.size(); i += 1) {
									   ddDT = (DropDownCodeDT) aList.get(i);
									   if(ddDT.getKey().equals(vo.getPublicHealthCaseDT().getRptCntyCd())){
										   dt.setRptCntyCD(ddDT.getValue()); 
										   break;
									   }
								   }

								   metaData = (NbsQuestionMetadata) questionMap.get("SUM116");
								   aList = (ArrayList<?> )CachedDropDowns.getCodedValueForType(metaData
											.getCodeSetNm()).clone();
								   for (int i = 0; i < aList.size(); i += 1) {
									   ddDT = (DropDownCodeDT) aList.get(i);
									   if(ddDT.getKey().equals(vo.getPublicHealthCaseDT().getCaseClassCd())){
										   dt.setCaseClassCD(ddDT.getValue()); 
										   break;
									   }
								   }
								   
								   metaData = (NbsQuestionMetadata) questionMap.get("SUM115");
								   aList = (ArrayList<?> )CachedDropDowns.getCodedValueForType(metaData
											.getCodeSetNm()).clone();
								   for (int i = 0; i < aList.size(); i += 1) {
									   ddDT = (DropDownCodeDT) aList.get(i);
									   if(ddDT.getKey().equals(vo.getPublicHealthCaseDT().getCountIntervalCd())){
										   dt.setGrpInterval(ddDT.getValue()); 
										   break;
									   }
								   }
								   //codesetnm = metaData.getCodeSetNm();
								  // String caseStatus = cachedDropDownValues.getDescForCode(vo.getPublicHealthCaseDT().getCaseClassCd(),codesetnm);
								   
								   dt.setRptFormCmpltTIME_s(vo.getPublicHealthCaseDT().getRptFormCmpltTime_s());
								   dt.setLastChgTIME_s(StringUtils.formatDate(vo.getPublicHealthCaseDT().getLastChgTime()));
								   //Currently Notification Specific attributes are retrieved from PublicHealthCaseDT
								   String recordStatusCd = vo.getPublicHealthCaseDT().getRecordStatusCd();
								   if(recordStatusCd != null)
									   notifExists = true;
								   dt.setStatusCD(recordStatusCd);
								   dt.setRptSentDate(StringUtils.formatDate(vo.getPublicHealthCaseDT().getRptSentTime()));
								   //Condition
								   dt.setConditionCd(cdv.getConditionDesc(vo.getPublicHealthCaseDT().getCd()));
								   //MMWR Week
								   dt.setMmwrWeek(formatMMWrWeek(vo.getPublicHealthCaseDT(), form));
							   }

							   while(it.hasNext()){
								   NbsCaseAnswerDT ansDT = (NbsCaseAnswerDT) it.next();	
								   if(ansDT != null){									 
										String key =  getVal(AggregateSummaryUtil.getQuestionKeyMap().get(ansDT.getNbsQuestionUid()));
										if(key.equalsIgnoreCase("SUM107")){
											//CaseCount in SearchResults represent TOTAL_COUNT_TOTAL for which TableMetadataUid needs to be retrieved to compared against specific Answer
											//For AggregateSummary, tableMetadataUid for TOTAL_COUNT_TOTAL is 22
											if(ansDT.getAnswerTxt() != null && ansDT.getNbsTableMetadataUid().intValue() == TOTAL_COUNT_TOTAL_TMUID) {
												Long caseCnt = Long.valueOf(ansDT.getAnswerTxt());
												dt.setCaseCount(caseCnt);
											}
										}
										/*if(key.equalsIgnoreCase("SUM115")){
											if(ansDT.getAnswerTxt() != null)							               
											dt.setGrpInterval(ansDT.getAnswerTxt());
										}*/	
								   }							   
							   }
							   makeAggregateSummaryLink(dt,VIEW, viewPermission, notifExists);
							   makeAggregateSummaryLink(dt,EDIT, addEditPermission, notifExists);
							   count++;
						       returnList.add(dt);
						   }					
					}
				} if(count == 0){
					form.getAttributeMap().put("NORESULT","NORESULT");
					returnList = null;					
				}
				if (resultArr == null || (resultArr != null && resultArr.size() <= 0) ) {
					req.setAttribute("zeroSearchResults", new Boolean(true));
				}
				
				//Sort them before setting to form
				boolean existing = req.getParameter("existing") == null ? false : true;
				sortResults(form, returnList, existing, req);

				form.setManageList(returnList);				
				
		 }catch (Exception e) {
	        	e.printStackTrace();
	            logger.fatal("ERROR in getSummaryResult calling PAM_PROXY_EJB from mainsession control", e);
	     } 		
	 }


	//This method trims the leading and trailing spaces before inserting data in the DB
	public static void trimSpaces(Object dtObject)
	{
	 try {
		Class<?> clazzz = dtObject.getClass();
	   Object returnVal = new Object();
	   String finalVal;
	   Method methods[] = clazzz.getDeclaredMethods();
	   Method setMethod = null;
	   for (int i = 0; i < methods.length; i++) {
		   Method method = (Method) methods[i];
		   Class<?>[] paraTypes = { method.getReturnType()};
	       if (methods[i].getName().equals("isItDirty")
		   || methods[i].getName().equals("isItNew")
		   || methods[i].getName().equals("isItDelete")
		   || methods[i].getName().equals("isEqual")
		       )
	    	   continue;
		   if (methods[i].getName().startsWith("get")) {
			   returnVal = methods[i].invoke(dtObject, (Object[])null);
			   
		         if(returnVal!=null && methods[i].getReturnType().equals(String.class))
		         {
		        	 finalVal = returnVal.toString().trim();
		        	 Object[] para = { finalVal };
		        	// find the set method name 
					String setMethodName = "set" + methods[i].getName().substring(3);
					setMethod = clazzz.getMethod(setMethodName,(Class[])paraTypes );
					setMethod.invoke(dtObject, para); 
		         }	 
		       }
		   continue;
		     }
	 } 
	 catch (InvocationTargetException invk) {
	   invk.printStackTrace();
	 }
	 catch (IllegalAccessException iacc) {
	   iacc.printStackTrace();
	 }catch (NoSuchMethodException nsme){     
		 nsme.printStackTrace();
	}
		
	}

	public static void makeAggregateSummaryLink(AggregateSummaryResultsDT dt, String type, boolean permission, boolean notifExists) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		//String loincCdTmp = URLEncoder.encode(dt.getLoincCd(),"ISO-8859-1");
		parameterMap.put("phcUid", dt.getPhc_UID().toString());

		if (type.equals("VIEW"))
			parameterMap.put("method", "viewSummaryData");
		else
			parameterMap.put("method", "editSummaryData");
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("AggregateReport.do", parameterMap,"default", "View", permission, notifExists));
		} else {
				dt.setEditLink(buildHyperLink("AggregateReport.do", parameterMap,"default", "Edit", permission, notifExists));
		}
	}
	public static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm, boolean permission, boolean notifExists) {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		String imgSrc = "";
		Iterator<Object> iter = paramMap.keySet().iterator();		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) paramMap.get(key);
			reqParams.append(key).append("=");
			reqParams.append(value);
			reqParams.append("&");
		}
		reqParams.deleteCharAt(reqParams.length() - 1);
		
		// mouse over title
		if (displayNm.equals("Edit")) {
			url.append("<a title=\"Edit\" href=\"javascript:editForm('/nbs/");
		}
		else if (displayNm.equals("View")) {
			url.append("<a title=\"View\" href='/nbs/");
		}
		else {
			url.append("<a href='/nbs/");
		}
		
		url.append(strutsAction);
		url.append(reqParams.toString());
		//Set Existing to True for Pagination
		url.append("&existing=true");
		
		if(jumperName != null) {
			url.append("#").append(jumperName);
		}
		if (displayNm.equals("Edit")) {
			url.append("','").append(notifExists).append("')\">");
		} else {
			url.append("'>");
		}
		//NOTE: Replacing the text with an image icon
		// url.append("'>").append(displayNm).append("</a>");
		if (displayNm.equals("Edit")) {
			imgSrc = "<img src=\"page_white_edit.gif\" tabindex=\"0\" alt=\"Edit\" title=\"Edit\">";
			url.append(imgSrc);
		}
		else if (displayNm.equals("View")) {
			imgSrc = "<img src=\"page_white_text.gif\" tabindex=\"0\" alt=\"View\" title=\"View\">";
			url.append(imgSrc);
		} 
		url.append("</a>");
		
		if(permission)	
			return url.toString();
		else
			return imgSrc;
	}

	 
	 public static String handleSrch(Object obj) {
			String toBeRepaced = obj == null ? "" : (String) obj;
			if(toBeRepaced.equals("")) return "";
			String specialCharacter = "'";
			String replacement = "''";
			int s = 0;
			int e = 0;
			StringBuffer result = new StringBuffer();
			while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0) {
				result.append(toBeRepaced.substring(s, e));
				result.append(replacement);
				s = e + specialCharacter.length();
			}
			result.append(toBeRepaced.substring(s));
			return result.toString();
		}
		private static String getVal(Object obj) {
			return obj == null ? "" : (String) obj;

		}	
		
		/**
		 * 
		 * @param AggregateSummaryForm
		 * @param ArrayList<Object> results
		 * @param existing
		 * @param request
		 */
		public static void sortResults(AggregateSummaryForm form, ArrayList<Object> results, boolean existing, HttpServletRequest request) {

			// Retrieve sort-order and sort-direction from displaytag params
			String sortMethod = getSortMethod(request, form);
			String direction = getSortDirection(request, form);

			boolean directionFlag = true;
			if (direction != null && direction.equals("2"))
				directionFlag = false;

			//Read from properties file to determine default sort order
			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none")) || (!existing)) {
					sortMethod = "getMmwrWeek";
					directionFlag = false;
					if(form.getAttributeMap().get("PageNumber") == null)
						form.getAttributeMap().put("PageNumber", "1");
			}
			
			NedssUtils util = new NedssUtils();			
			if (sortMethod != null && results != null && results.size() > 0) {
				//Handle Numeric Counts in a different Fashion
				if(sortMethod != null && sortMethod.equalsIgnoreCase("getCaseCount")) {
					 Object[] array = results.toArray();
					 NumericComparator nc = new AggregateSearchUtil().new NumericComparator();
					 nc.ascending(directionFlag);
					 Arrays.sort(array, nc);
					 results.clear();
				      for (int i = 0; i < array.length ; i++)
				    	  results.add(array[i]);
				} else {
					//Standard Way of NBS Sort (String Sorts)
					util.sortObjectByColumn(sortMethod,(Collection<Object>) results, directionFlag);
				}
			}
			
			//Finally put sort criteria in form
			String sortOrderParam = getSortCriteria(directionFlag == true ? "1" : "2", sortMethod);
			form.getAttributeMap().put("sortSt", sortOrderParam);

		}
		
		private static String getSortMethod(HttpServletRequest request, AggregateSummaryForm form) {
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			} else{
				return form.getAttributeMap().get("methodName") == null ? null : (String) form.getAttributeMap().get("methodName");
			}
			
		}

		private static String getSortDirection(HttpServletRequest request, AggregateSummaryForm form) {
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			} else{
				return form.getAttributeMap().get("sortOrder") == null ? "1": (String) form.getAttributeMap().get("sortOrder");
			}
			
		}
		
		/**
		 * Returns MMWR Week with format Wk(mm/dd/yyyy - mm/dd/yyyy) in the search results
		 * @param mmwrWk
		 * @param form
		 * @return
		 */
		public static String formatMMWrWeek(PublicHealthCaseDT phcDT, AggregateSummaryForm form) {
			String retVal = "";
			String yr = phcDT.getMmwrYear();
			String wk = phcDT.getMmwrWeek();
			
			if(form.getMmwrWeekMap().get(yr) != null) {
				ArrayList<?> list = (ArrayList<?> )form.getMmwrWeekMap().get(yr);
				Iterator<?> iter = list.iterator();
				while(iter.hasNext()) {
					DropDownCodeDT dt = (DropDownCodeDT) iter.next();
					String key = getVal(dt.getKey()); 
					if(key.equalsIgnoreCase(wk) )
						return dt.getValue();
				}
			}			
			return retVal;
		}
		
		
		public static String getSortCriteria(String sortOrder, String methodName){
			
			String sortOrdrStr = null;
			if(methodName != null) {
				if(methodName.equals("getRptCntyCD"))
					sortOrdrStr = "Reporting Area";
				else if(methodName.equals("getMmwrWeek"))
					sortOrdrStr = "MMWR Week";
				else if(methodName.equals("getConditionCd"))
					sortOrdrStr = "Condition";
				else if(methodName.equals("getCaseCount"))
					sortOrdrStr = "Case Count";
				else if(methodName.equals("getLastChgTIME"))				
					sortOrdrStr = "Last Updated";				
				else if(methodName.equals("getStatusCD"))				
					sortOrdrStr = "Status";
				else if(methodName.equals("getRptSentDate"))				
					sortOrdrStr = "Sent Date";				
			} else {
				sortOrdrStr = "Date of Report";
			}
			
			if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr+"| in ascending order ";
			else if(sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr+"| in descending order ";

			return sortOrdrStr;
				
		}		

		class NumericComparator implements Comparator<Object> {
			boolean ascending = true;
			public void ascending(boolean directionflag){
			      ascending = directionflag;
			}
			public int compare(Object cnt1, Object cnt2){
				Long caseCnt1 = ( (AggregateSummaryResultsDT) cnt1).getCaseCount();
				Long caseCnt2 = ( (AggregateSummaryResultsDT) cnt2).getCaseCount();
				if(ascending) {
					if( caseCnt1.longValue() > caseCnt2.longValue() )
						return 1;
					else if( caseCnt1.longValue() < caseCnt2.longValue() )
						return -1;
					else
						return 0;					
				} else {
					if( caseCnt1.longValue() > caseCnt2.longValue() )
						return -1;
					else if( caseCnt1.longValue() < caseCnt2.longValue() )
						return 1;
					else
						return 0;
				}
			}
		}
		
		/**
		 * 
		 * @param treeMap
		 * @param req
		 * @return
		 */
	 	public static String loadProgramAreasToString(HttpServletRequest req) {
	 		
			HttpSession session = req.getSession();			
			//Security Object
			NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	        TreeMap<Object, Object>   programAreas = null;
	        String strProgramAreas = "";
	        // Get the programArea Codes
	        try {
	        	programAreas =   secObj.getProgramAreas(NBSBOLookup.SUMMARYREPORT,NEDSSConstants.SUMMARY_REPORT_ADD);
	     
	        	if (programAreas!=null)
	        	{
	        		if(programAreas.isEmpty()) {
	        			logger.debug("programAreas is empty {}!!!!");
	        		}
	        		else {
	        			strProgramAreas = sortTreeMaptoString(programAreas);
	        		}
	        	}
	        	else {
	        		logger.warn("Program Areas are NULL inside loadProgramAreasToString: AggregateSearchUtil");
	        	}
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        return strProgramAreas;
	 	}

	 		
	 	private static String sortTreeMaptoString(TreeMap<Object,Object> treeMap) {
			class StringComparator implements Comparator<Object> {
				public int compare(Object a, Object b) {
					Map.Entry<?,?> m1 = (Map.Entry<?,?>) a;
					Map.Entry<?,?> m2 = (Map.Entry<?,?>) b;

					return (m1.getValue()).toString().compareTo(
							(m2.getValue()).toString());
				}
			}

			StringBuffer sb = new StringBuffer();
			Set<?> s = treeMap.entrySet();
			Map.Entry<?,?>[] entries = (Map.Entry<?,?>[]) s.toArray(new Map.Entry<?,?>[s.size()]);
			Arrays.sort(entries, new StringComparator());
			if (s.size() > 0) {
				for (Iterator<?> setIterator = s.iterator(); setIterator.hasNext();)
				// for (int i = 0; i < s.size(); i++)
				{
					Map.Entry<?,?> m = (Map.Entry<?,?>) setIterator.next();
					sb.append(NEDSSConstants.STR_TOKEN).append(m.getKey()).append(
							NEDSSConstants.STR_TOKEN).append(
							NEDSSConstants.STR_COMMA);
					// logger.info(m.getKey()+" => "+m.getValue());
				}

			}

			if (sb == null)
				sb = sb.append(NEDSSConstants.STR_000);
			return sb.toString().substring(0,
					(sb.toString()).lastIndexOf(NEDSSConstants.STR_COMMA));

		}	
}
