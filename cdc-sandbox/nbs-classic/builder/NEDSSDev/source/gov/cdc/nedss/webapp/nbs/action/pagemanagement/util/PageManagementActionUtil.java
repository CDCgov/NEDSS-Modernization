package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.exception.NedssAppLogException;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean.PageManagementProxy;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean.PageManagementProxyHome;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.pagemanagement.util.PageMetaConstants;
import gov.cdc.nedss.pagemanagement.wa.dao.ConditionDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dao.TemplatesDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.NbsBusObjMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaRdbMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dto.PageElementDTO;
import gov.cdc.nedss.pagemanagement.wa.xml.GenerateExportImportXML;
import gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML;
import gov.cdc.nedss.pagemanagement.wa.xml.transform.GenerateJSPTabs;
import gov.cdc.nedss.pagemanagement.wa.xml.util.TemplateExportType;
import gov.cdc.nedss.systemservice.dao.EDXActivityLogDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamPrintUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class PageManagementActionUtil extends PamPrintUtil 
{
	static final LogUtils logger = new LogUtils(PageManagementActionUtil.class.getName());
	public static final String INVOCATION_CONTEXT_PAGE_BUILDER = "Page Builder";
	public static final String INVOCATION_CONTEXT_NON_PAGE_BUILDER = "Non Page Builder";
	public static final String INVOCATION_CONTEXT_PAGE_LIBRARY = "Page Library";
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final String xml2jspDirectory = new StringBuffer(propertiesDirectory).append("xmltojsp").append(File.separator).toString().intern();
	private static final String NBS_TMP_DEPLOYMENT_CONTAINS = "deployment";
	private static final String NBS_TMP_WAR_NAME_CONTAINS = "nbs.war-";
	private static final String SOURCE_FOLDER_IN_WAR = File.separator + "pagemanagement" + File.separator;
	private static final String TARGET_FOLDER_IN_WAR = File.separator + "pagemanagement" + File.separator + "rendering"+ File.separator + "preview";
	private static PageManagementProxy pMap = null;
	private final static String pdfFileName = "NBSErrorPage.pdf";
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private String  editLinkDisabledIcon = "page_white_edit_disabled.gif";
	
	QueueUtil queueUtil = new QueueUtil();
	public Long setWAQuestion(HttpSession session, ArrayList<Object> list )throws Exception{
		Long  waQuestionUid =null;
		MainSessionCommand msCommand = null;
	
		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "setWaQuestion";
			Object[] oParams =new Object[] {list};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
			waQuestionUid = (Long)aList.get(0);
			
			}catch (Exception ex) {
				logger.fatal("Error in setWaQuestion in Action Util: "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
			}
		
			 return  waQuestionUid; 
 		
	}
	public ArrayList<Object> getWAQuestion(HttpSession session,Long waQuestionUid )throws Exception{
		WaQuestionDT dt = new WaQuestionDT();
		WaQuestionDT dt1 = new WaQuestionDT();
		WaQuestionDT dt2 = new WaQuestionDT();
		//WaQuestionDT dtUnit = new WaQuestionDT();
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<Object>  nList = new ArrayList<Object>();
		MainSessionCommand msCommand = null;
		
		CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
	
		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getWaQuestion";
			Object[] oParams =new Object[] {waQuestionUid};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				nList = (ArrayList)aList.get(0); 
			    dt1= (WaQuestionDT)nList.get(0);
			    if(nList != null && nList.size()>1){
				    if(nList.get(1) != null)
				    	dt2=(WaQuestionDT)nList.get(1);
			    }
	
			}
			if(nList != null && nList.size()>1){
				if(dt1.getUnitParentIndentifier() != null && !dt1.getUnitParentIndentifier().equals("")){
					dt = dt1;
					//dtUnit = dt2;
				}else if(dt2.getUnitParentIndentifier() != null && !dt2.getUnitParentIndentifier().equals("")){
					dt = dt2;
					//dtUnit = dt1;
				}
			}else if(nList != null && nList.size()==1){
				dt = dt1;
				//dtUnit = null;
			} 
			
			dt.setGroupDesc((dt.getGroupNm() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_GROUP,dt.getGroupNm()));
			dt.setSubGroupDesc((dt.getSubGroupNm() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_SUBGROUP,dt.getSubGroupNm()));
			dt.setDataTypeDesc((dt.getDataType() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_DATATYPE,dt.getDataType()));
			//dt.setValSetDesc((dt.getValSet() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_VALSET,dt.getValSet()));
			dt.setValSetDesc((dt.getNbsUiComponentUid() == null) ? "" : getValSetDesc(dt.getNbsUiComponentUid().toString()));
			dt.setMaskDesc((dt.getMask() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_MASK ,dt.getMask()));
			dt.setQuestionDataTypeNndDesc((dt.getQuestionDataTypeNnd() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_HL7_DATATYPE,dt.getQuestionDataTypeNnd()));
			dt.setHl7SegmentDesc((dt.getHl7Segment() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_HL7_SEGMENT,dt.getHl7Segment()));
			dt.setStatusDesc((dt.getRecordStatusCd() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_NBS_REC_STATUS_CD,dt.getRecordStatusCd()));
			dt.setDefaultDisplayControlDesc((dt.getNbsUiComponentUid() == null) ? "" : cachedDropDownValues.getDefaultDispCntrlDesc(dt.getNbsUiComponentUid().toString()));
			//dt.setParticipationTypeDesc((dt.getParticipationType() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_DATATYPE ,dt.dt.getParticipationType()));
			if(dt.getNndMsgInd() != null)
			dt.setNndMsgIndDesc((dt.getNndMsgInd().equals(NEDSSConstants.TRUE)) ? "Yes" : "No");
			if(dt.getQuestionReqNnd() != null)
			dt.setQuestionReqNndDesc((dt.getQuestionReqNnd().equals(NEDSSConstants.TRUE)) ? "Yes" : "No");
			dt.setUnitTypeCdDesc((dt.getUnitTypeCd() == null) ? "" : cachedDropDownValues.getDescForCode("NBS_UNIT_TYPE" ,dt.getUnitTypeCd()));
			
			if(dt.getUnitTypeCd()!= null && dt.getUnitTypeCd().equals("CODED"))
				dt.setUnitValueDesc((dt.getUnitValue() == null) ? "" : getValSetDesc(dt.getUnitValue()));
			
			if(dt.getOtherValIndCd() != null)
				dt.setOtherValIndCdDesc((dt.getOtherValIndCd().equals(NEDSSConstants.TRUE)) ? "Yes" : "No");
			
			
			list.add(dt);

			
		  }catch (Exception ex) {
				logger.fatal("Error in getWaQuestion in Action Util: "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
		  }
		  
		
		return  list; 
 		
	}
	public Long getUniqueQuestionIdentifier(){
		Long maxUid = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			maxUid = (Long)dao.getMaxWaQuestionUid();
		}catch(Exception e){
			logger.error("Error in accessing the maxUid from DAO"+e.getMessage(), e);
		}
		return maxUid;
		
	}
	
	public WaQuestionDT getWaQuestionByIdentifier(String questionIdentifier){
		WaQuestionDT waQuestionDT = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			waQuestionDT = dao.findWaQuestion(questionIdentifier);
		}catch(Exception e){
			logger.fatal("Error in accessing the WA Question from DAO"+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return waQuestionDT;	
		
	}
	
	public WaQuestionDT getWaQuestionByName(String uniqueNm){
		WaQuestionDT waQuestionDT = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			waQuestionDT = dao.findWaQuestionByUniqueNm(uniqueNm);
		}catch(Exception e){
			logger.fatal("Error in accessing the WA Question from DAO"+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return waQuestionDT;	
		
	}
	//Rdb Column Name
	public WaQuestionDT getWaQuestionByDataMartColumnName(String dmColumn){
		WaQuestionDT waQuestionDT = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			waQuestionDT = dao.findWaQuestionByDatamartColumn(dmColumn);
		}catch(Exception e){
			logger.fatal("Error in accessing the WA Question from DAO"+e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return waQuestionDT;	
		
	}
	
	
	/**
	 * getWaQuestionByUserDefinedColumnName: Data Mart Column Name = User_defined_column_nm
	 * @param dmColumn
	 * @return
	 */
	public WaQuestionDT getWaQuestionByUserDefinedColumnName(String dmColumn){
		WaQuestionDT waQuestionDT = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			waQuestionDT = dao.findWaQuestionByUserDefinedColumnName(dmColumn);
		}catch(Exception e){
			logger.fatal("Error in accessing the WA Question from DAO"+e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return waQuestionDT;	
		
	}
	
	
	
	
	public WaQuestionDT setTheValuesForDT(WaQuestionDT dt,HttpServletRequest request, String actionMode){
		
		try{
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		java.util.Date startDate= new java.util.Date();
		Timestamp aAddTime = new Timestamp(startDate.getTime());			
		dt.setLastChgTime(aAddTime);
		dt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));		
		if(actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)){
			dt.setItNew(false);
			dt.setItDirty(true);
			//dt.setVersionCtrlNbr(dt.getVersionCtrlNbr()+1);
			
			
		if(dt.getNndMsgInd()!= null && dt.getNndMsgInd().equalsIgnoreCase("T")) {	
			
			if(dt.getQuestionOid() != null && (dt.getQuestionOid().equals("PHIN_QUESTIONS_CDC")||dt.getQuestionOid().equals("2.16.840.1.114222.4.5.232"))){
				dt.setQuestionOid("2.16.840.1.114222.4.5.232");
				dt.setQuestionOidSystemTxt("PHIN Questions");
			}else if(dt.getQuestionOid()!= null && dt.getQuestionOid().equals("L")){
				dt.setQuestionOid("L");
				dt.setQuestionOidSystemTxt("Local");
			}else {
				
				 TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
					Collection<Object> CVGs = returnMap.values();
					if(CVGs !=null && CVGs.size()>0){
						Iterator<Object> ite  = CVGs.iterator();
						while(ite.hasNext()){
							CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)ite.next();
							if(dtCVGCache.getCode().equals(dt.getQuestionOid())){
								dt.setQuestionOid(dtCVGCache.getCodeDescTxt());
								dt.setQuestionOidSystemTxt(dtCVGCache.getCodeShortDescTxt());
								break;
							}
						}
					}
				
			}
		
		}
		}else if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)){
			dt.setItNew(true);
			dt.setItDirty(false);
			dt.setVersionCtrlNbr(new Integer(1));
			dt.setAddTime(aAddTime);
			dt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
			dt.setRecordStatusTime(aAddTime);
			dt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			dt.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
			if(dt != null && dt.getQuestionType() != null && dt.getQuestionType().equalsIgnoreCase(NEDSSConstants.STATE_QUESTIONTYPE_CODE)){
				if(dt.getGroupNm()!=null && dt.getGroupNm().equals(NEDSSConstants.GROUP_INV))
					dt.setDataLocation(NEDSSConstants.NBS_CASE_ANSWER_ANSWER_TXT);
				else if(dt.getGroupNm()!=null && dt.getGroupNm().equals(NEDSSConstants.GROUP_CON))
					dt.setDataLocation(NEDSSConstants.CT_CONTACT_ANSWER_ANSWER_TXT);
				else if(dt.getGroupNm()!=null && dt.getGroupNm().equals(NEDSSConstants.GROUP_IXS))
					dt.setDataLocation(NEDSSConstants.NBS_ANSWER_ANSWER_TXT);
			}
			
			if(dt.getNndMsgInd()!= null && dt.getNndMsgInd().equalsIgnoreCase("T")){
				
				TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
				CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)returnMap.get(dt.getQuestionOidCode());
				dt.setQuestionOid(dtCVGCache.getCodeDescTxt());
				dt.setQuestionOidSystemTxt(dtCVGCache.getCodeShortDescTxt());
				

			}else  { 
			
			if(dt.getQuestionType() != null && dt.getQuestionType().equals(NEDSSConstants.PHIN_QUESTIONTYPE_CODE)){
				dt.setQuestionOid("2.16.840.1.114222.4.5.232");
				dt.setQuestionOidSystemTxt("PHIN Questions");
			}else if(dt.getQuestionType()!= null && dt.getQuestionType().equals(NEDSSConstants.STATE_QUESTIONTYPE_CODE)){
				dt.setQuestionOid("L");
				dt.setQuestionOidSystemTxt("Local");
			}
		}
		}
		if(dt.getNndMsgInd() != null && !(dt.getNndMsgInd().equals(NEDSSConstants.TRUE))){
			dt.setQuestionReqNnd(null);
			dt.setOrderGrpId(null);
		}
		return dt;
		}catch (Exception e){
			logger.fatal("Error in Setting the value for WaQuestionDT in  setTheValuesForDT method" +e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}
	
	/**
	 * Retrieve a list of questions that satisfy the search criteria. <br/> 
	 * NOTE: If this method is called from the Page Builder context,
	 * return only the questions that are not present in the page that
	 * is currently handled.
	 * @param session
	 * @param searchMap
	 * @param method
	 * @param invocationContext
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Object> searchWaQuestions(HttpSession session,
	        Map<Object,Object> searchMap, String method, String invocationContext)
	    throws Exception
	{
		
		try{
	    // NOTE: if the 'pageData' object in session is not null,
	    // exclude the questions present in it from the search results.
	    // 'pageData' is the object that holds the structure of the page builder
	    String businessObjType = null;
	    Collection<String> existingQs = null;
	    if (invocationContext.equals(INVOCATION_CONTEXT_PAGE_BUILDER) && 
	            (PageManagementProxyVO)session.getAttribute("pageData") != null) {
	        PageManagementProxyVO pageData = 
	            (PageManagementProxyVO) session.getAttribute("pageData");
	        existingQs = getQuestionIdsInPageData(pageData);
	        businessObjType = pageData.getWaTemplateDT().getBusObjType();
	    }
	    
	    ArrayList<Object> dtList = new ArrayList<Object>();
	    MainSessionCommand msCommand = null;
	    CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
	    StringBuffer sb = new StringBuffer(" WHERE ques.standard_nnd_ind_cd='F' AND data_type <> 'PART' AND");	
	
		try
		{   
			String whereclause = null;
			if(searchMap != null ){
			    String questionId = handleSrch(searchMap.get("questionIdentifier"));
			    String questionName = handleSrch(searchMap.get("questionNm"));
			    String group = handleSrch(searchMap.get("group"));
			    if(businessObjType!=null && (group==null || group.trim().equals(""))){
			    	group = ", 'GROUP_"+businessObjType+"'";
			    }
			    String subGroup = handleSrch(searchMap.get("subGroup"));
			    String label = handleSrch(searchMap.get("label"));
			    // String description = handleSrch(searchMap.get("description"));
			    //String dataType = handleSrch(searchMap.get("dataType"));
			    //String valueSet = handleSrch(searchMap.get("valSet"));
			    String includeInactiveQ = handleSrch(searchMap.get("recordStatusCd"));
			    String questionType = handleSrch(searchMap.get("questionType"));
			    String templateUID = "";
			    
			    StringBuffer groupNmInStringBuffer = new StringBuffer("");
			    ArrayList<Object> nbsBusObjList = getNbsBusObjMetadataList(session);
			    for(int i=0;i<nbsBusObjList.size();i++){
			    	NbsBusObjMetadataDT nbsBusObjMetadataDT = (NbsBusObjMetadataDT) nbsBusObjList.get(i);
			    	if(nbsBusObjMetadataDT!=null)
			    		groupNmInStringBuffer = groupNmInStringBuffer.append("'"+nbsBusObjMetadataDT.getGroupNm()+"',");
			    }
			    String groupNmInStr="";
			    if(groupNmInStringBuffer.length()>0){
			    	groupNmInStr = groupNmInStringBuffer.substring(0, groupNmInStringBuffer.length()-1);
			    }
			    
			    if(includeInactiveQ.equals("Y")) {
			        includeInactiveQ = "";
			    }
				else {
				    includeInactiveQ = "Active";
				}
				
				if (questionId.length() > 0) {
				    sb.append("  upper(ques.question_identifier) like upper('%").append(questionId).append("%')");
				    sb.append("  AND ");
				}
				
				if(questionName.length() > 0) {
				    sb.append("  upper(ques.question_nm) like upper('%").append(questionName).append("%')");
				    sb.append("  AND ");
				}
				
				if(groupNmInStr.length()>0){
					sb.append("  upper(ques.group_nm) in("+groupNmInStr+")");
				    sb.append("  AND ");
				}
				
				if(subGroup.length() > 0) {
				    sb.append("  upper(ques.sub_group_nm) = upper('").append(subGroup).append("')");
				    sb.append("  AND ");
				}
				
				if(label.length() > 0) {
				    sb.append("  upper(ques.question_label) like upper('%").append(label).append("%')");
				    sb.append("  AND ");
				}
				
				if(questionType.length() > 0) {
				    sb.append("  upper(ques.question_type) = upper('").append(questionType).append("')");
				    sb.append("  AND ");
				}

				if(includeInactiveQ.length() > 0) {
				    sb.append("  upper(ques.record_status_cd) = upper('").append(includeInactiveQ).append("')");
				    sb.append("  AND ");
				}
			
				if(templateUID != null && !templateUID.equals("")) {
				    sb.append(" ques.wa_question_uid not in(select wa_question_uid from WA_UI_metadata WHERE ");
				    sb.append("  wa_template_uid = '").append(templateUID).append("'").append(")");
				}
			}//  Closing for the searchMAp null check 
			
			if(session.getAttribute("batchUidForAdd") != null){
				sb.append(" ques.data_location like 'NBS_CASE_ANSWER%' ");
				session.removeAttribute("batchUidForAdd");
			}
			whereclause=sb.toString().trim();
			if(whereclause.substring(whereclause.length()-3, whereclause.length()).equals("AND")) {
			    whereclause = whereclause.substring(0,whereclause.length()-3);
			}
				
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "searchWaQuestions";
			Object[] oParams =new Object[] {whereclause};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
			dtList = (ArrayList<Object>)aList.get(0);
			
			if(dtList.size() > 0) {
			    java.util.Iterator<Object> iter = dtList.iterator();				

			    while(iter.hasNext())
			    {
			        WaQuestionDT dt = (WaQuestionDT) iter.next();
			        dt.setGroupDesc((dt.getGroupNm() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_GROUP,dt.getGroupNm()));

			        dt.setSubGroupDesc((dt.getSubGroupNm() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_SUBGROUP,dt.getSubGroupNm()));
			        dt.setDataTypeDesc((dt.getDataType() == null) ? "" : dt.getDataType());
        
			        if (method.equals("searchQuestionsSubmitFromPageBuilder")) {
			            String url = "<input type=\"checkbox\" id = "+dt.getWaQuestionUid()+ " name=\"check["+dt.getWaQuestionUid()+"]\" onclick=\"checkforimportedquestions("+dt.getWaQuestionUid().toString()+",'"+dt.getQuestionNm()+"')\"/>";
			            dt.setViewLink(url);
			        }
			    }
			}
		}
		catch(Exception ex) {
		    logger.fatal("Error in setWaQuestion in Action Util: "+ex.getMessage(), ex);
		    throw new Exception(ex.getMessage(), ex);
		}
		
		// Exclude the questions that are already present in the page data
		ArrayList<Object> updatedSearchResults = new ArrayList<Object>();
		if (invocationContext.equals(INVOCATION_CONTEXT_PAGE_BUILDER) &&
		        existingQs != null && existingQs.size() > 0) {
		    for (Object qInSearch : dtList) {
		        WaQuestionDT qDt = (WaQuestionDT) qInSearch;
		        if (!existingQs.contains(String.valueOf(qDt.getQuestionIdentifier()))) {
		            updatedSearchResults.add(qDt);
		        }
		    }
		    
		    // set the updated search results to the return value
		    dtList = updatedSearchResults;
		}
		
		return dtList; 
		}catch(Exception ex) {
		    logger.fatal("Error in searchWaQuestions in Action Util: "+ex.getMessage(), ex);
		    throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public static String handleSrch(Object obj) throws Exception {
		try{
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
		}catch(Exception ex){
			logger.fatal("Error in handleSrch method in util class"+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}	
	
	public static void makeQuestionLink(WaQuestionDT dt, String type) throws Exception{
		try{
		String questionUid = dt.getWaQuestionUid().toString();
		String viewUrl = "<a href=\"javascript:viewValueset('" + questionUid + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" title=\"View\" alt=\"View\"/></a>";
		dt.setViewLink(viewUrl);
		String editUrl = "<a href=\"javascript:editValueset('" + questionUid + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" title=\"Edit\" alt=\"Edit\"/></a>";
		dt.setEditLink(editUrl);
		}catch(Exception ex){
			logger.fatal("Error in makeQuestionLink method in util class"+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	private static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm)throws Exception {
		try{
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		java.util.Iterator<Object> iter = paramMap.keySet().iterator();		
		while (iter.hasNext()) {
			String key = (String) iter.next();		
			String value = (String) paramMap.get(key).toString();
			reqParams.append(key).append("=");
			reqParams.append(value);
			reqParams.append("&");
		}
		reqParams.deleteCharAt(reqParams.length() - 1);
		url.append("<a href='/nbs/");
		url.append(strutsAction);
		url.append(reqParams.toString());
		if(jumperName != null) {
			url.append("#").append(jumperName);
		}
		url.append("'>").append(displayNm).append("</a>");
		return url.toString();
		}catch(Exception ex){
			logger.fatal("Error in buildHyperLink method in util class"+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}

	public String getValSetDesc(String ValSetCode )throws Exception{	
		try{
		ArrayList<Object> valSetList = new ArrayList<Object>();	
		String ValSetDesc="";
		try{
		valSetList = CachedDropDowns.getAllActiveCodeSetNms();	   
		java.util.Iterator<Object> valSetIter = valSetList.iterator();
		while(valSetIter.hasNext()) {						
			DropDownCodeDT dDownDT = (DropDownCodeDT) valSetIter.next();
			if(dDownDT.getKey().toString().equalsIgnoreCase(ValSetCode)){
				ValSetDesc = dDownDT.getValue();
			 }			
		 }		
	   
		}catch (Exception ex) {
			logger.fatal("Error in getValSetDesc in Action Util: "+ex.getMessage(), ex);
			 return ValSetDesc;
		
		}	
	    return ValSetDesc;
		}catch(Exception ex){
			logger.fatal("Error in getValSetDesc method in util class"+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public static ArrayList<Object> getConditionLib(HttpSession session)
	throws Exception {

		ArrayList<Object> conditionLibList = null;
		MainSessionCommand msCommand = null;

	try {
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getConditionLib";
		// Object[] oParams = new Object[] {dt};
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
				null);
		conditionLibList = (ArrayList<Object> ) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in getConditionLib in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
		return conditionLibList;
	}
	
	public ArrayList<Object> getProgramAreaDowns(Collection<Object>  conditionDTColl) throws Exception {
		try{
		Map<Object, Object> programAreaMap = new HashMap<Object,Object>();
		if (conditionDTColl != null) {
			java.util.Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getProgAreaCd()!= null) {
					programAreaMap.put(dt.getProgAreaCd(), dt.getProgAreaCd());
							
				}
				if(dt.getProgAreaCd() == null || dt.getProgAreaCd().trim().equals("")){
					programAreaMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(programAreaMap);
		}catch (Exception ex) {
			logger.fatal("Error in getProgramAreaDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public ArrayList<Object> getCoInfGroupDropDowns(Collection<Object>  conditionDTColl) throws Exception {
		try{
		Map<Object, Object> coInfGroupMap = new HashMap<Object,Object>();
		if (conditionDTColl != null) {
			java.util.Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getCoInfGroup()!= null) {
					coInfGroupMap.put(dt.getCoInfGroup(), CachedDropDowns.getCodeDescTxtForCd(dt.getCoInfGroup(),"COINFECTION_GROUP"));
							
				}
				if(dt.getCoInfGroup() == null || dt.getCoInfGroup().trim().equals("")){
					coInfGroupMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(coInfGroupMap);
		}catch (Exception ex) {
			logger.fatal("Error in getConditionFamilyDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public ArrayList<Object> getConditionFamilyDropDowns(Collection<Object>  conditionDTColl) throws Exception {
		try{
		Map<Object, Object> conditionFamilyMap = new HashMap<Object,Object>();
		if (conditionDTColl != null) {
			java.util.Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getFamilyCd()!= null) {
					conditionFamilyMap.put(dt.getFamilyCd(), CachedDropDowns.getCodeDescTxtForCd(dt.getFamilyCd(),"CONDITION_FAMILY"));
							
				}
				if(dt.getFamilyCd() == null || dt.getFamilyCd().trim().equals("")){
					conditionFamilyMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(conditionFamilyMap);
		}catch (Exception ex) {
			logger.fatal("Error in getConditionFamilyDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	public ArrayList<Object> getAssociatedPageDropDowns(Collection<Object>  conditionDTColl)throws Exception {
		try{
		Map<Object, Object> associatedPageMap = new HashMap<Object,Object>();
		if (conditionDTColl != null) {
			java.util.Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getPageNm()!= null) {
					associatedPageMap.put(dt.getPageNm(), dt.getPageNm());
							
				}
				if(dt.getPageNm() == null || dt.getPageNm().trim().equals("")){
					associatedPageMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
				
			}
		}
		return queueUtil.getUniqueValueFromMap(associatedPageMap);
		}catch (Exception ex) {
			logger.fatal("Error in getAssociatedPageDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public ArrayList<Object> getNndConditionDropDowns(Collection<Object>  conditionDTColl)throws Exception {
		try{
		Map<Object, Object> nndConditionMap = new HashMap<Object,Object>();
		if (conditionDTColl != null) {
			java.util.Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getNndInd()!= null) {
					nndConditionMap.put(dt.getNndInd(), CachedDropDowns.getCodeDescTxtForCd(dt.getNndInd(),"YN"));
							
				}
				if(dt.getNndInd() == null || dt.getNndInd().trim().equals("")){
					nndConditionMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(nndConditionMap);
		}catch (Exception ex) {
			logger.fatal("Error in getNndConditionDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public ArrayList<Object> getStatusDropDowns(Collection<Object>  conditionDTColl)throws Exception {
		try{
		Map<Object, Object> statusMap = new HashMap<Object,Object>();
		if (conditionDTColl != null) {
			java.util.Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getStatusCd()!= null) {
					statusMap.put(dt.getStatusCd(), CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
				}
				if(dt.getStatusCd() == null || dt.getStatusCd().trim().equals("")){
					statusMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(statusMap);
		}catch (Exception ex) {
			logger.fatal("Error in getStatusDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		
		}
	}
	
	public static String getSortCriteria(String sortOrder, String methodName) throws Exception{
		try{
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getProgAreaCd"))
				sortOrdrStr = "Program Area";
			else if(methodName.equals("getPageNm"))
				sortOrdrStr = "Investigation Page";
			else if(methodName.equals("getNndInd"))
				sortOrdrStr = "NND";
			else if(methodName.equals("getStatusCd"))				
				sortOrdrStr = "Status";	
			else if(methodName.equals("getConditionShortNm"))
				sortOrdrStr = "Condition";
			else if(methodName.equals("getFamilyCd"))
				sortOrdrStr = "Condition Family";
			else if(methodName.equals("getConditionCd"))
				sortOrdrStr = "Code";
			else if(methodName.equals("getCoInfGroup"))
				sortOrdrStr = "Coinfection Group";
		} else {
				sortOrdrStr = "Program Area";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortCriteria in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
			
	}
	
	public static String getSortCriteriaQuestion(String sortOrder, String methodName) throws Exception{
		try{
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getQuestionType"))
				sortOrdrStr = "Type";
			else if(methodName.equals("getQuestionIdentifier"))
				sortOrdrStr = "Unique ID";
			else if(methodName.equals("getQuestionNm"))
				sortOrdrStr = "Unique Name";
			else if(methodName.equals("getGroupDesc"))				
				sortOrdrStr = "Group";	
			else if(methodName.equals("getSubGroupDesc"))
				sortOrdrStr = "Subgroup";
			else if(methodName.equals("getQuestionLabel"))
				sortOrdrStr = "Label";
			else if(methodName.equals("getRecordStatusCd"))
				sortOrdrStr = "Status";
		} else {
				sortOrdrStr = "Unique ID";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortCriteriaQuestion in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
			
	}
	
	public static String getSortPageLibrary(String sortOrder, String methodName)throws Exception{
		try{
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getBusObjType"))
				sortOrdrStr = "Event Type";
			else if(methodName.equals("getTemplateNm"))
				sortOrdrStr = "Page Name";
			else if(methodName.equals("getLastChgTime"))
				sortOrdrStr = "Last Updated";
			else if(methodName.equals("getFirstLastName"))				
				sortOrdrStr = "Last Updated By";	
			else if(methodName.equals("getTemplateType"))
				sortOrdrStr = "Page State";
			else if(methodName.equals("getRelatedConditions"))
				sortOrdrStr = "Related Condition(s)";
		} else {
				sortOrdrStr = "Page Name";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" @ in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" @ in descending order ";

		return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortPageLibrary in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
			
	}
	
	public static ArrayList<Object> getConditionShortNm() throws Exception
	
	{
		try{
		ArrayList<Object> conditionSrtNmList = null;
		ConditionDAOImpl dao = new ConditionDAOImpl();
		conditionSrtNmList = (ArrayList<Object> )dao.getConditionShortNmCollection();
		return conditionSrtNmList;
		}catch (Exception ex) {
			logger.fatal("Error in getConditionShortNm in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public static String getConditionShortNmDesc(String conditionCd) throws Exception
	{
		try{
		String conditionSrtNm = null;
		ConditionDAOImpl dao = new ConditionDAOImpl();
		conditionSrtNm = dao.getConditionShortNm(conditionCd);
		return conditionSrtNm;
		}catch (Exception ex) {
			logger.fatal("Error in getConditionShortNmDesc in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public static Map<Object,Object> insertCondition(HttpSession session, ConditionDT dt)throws Exception{
		MainSessionCommand msCommand = null;
		ConditionDT conditionDT  = new ConditionDT();
		Map<Object, Object> errorMap = null;
		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "createCondition";
			Object[] oParams =new Object[] {dt};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<Object> arr =(ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((arr != null) && (arr.size() > 0)) {
			errorMap = (HashMap<Object, Object>) arr.get(0);
				}
			}catch (Exception ex) {
				logger.fatal("Error in insertCondition in Action Util: "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
			}
			return errorMap;
	}
	
	public static void updateConditionLinks(ArrayList<Object>  conditionList,
			HttpServletRequest request) throws Exception{
		try{
		if (conditionList != null && conditionList.size() != 0) {
			for (int i = 0; i < conditionList.size(); i++) {
				ConditionDT dt = (ConditionDT) conditionList.get(i);
				
				HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
				String conditionCd= null;
				try {
					conditionCd = URLEncoder.encode(dt.getConditionCd(), "ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					logger.error("Unsupported Encoding Exception in updateConditionLinks: " + e.getMessage(), e);
					e.printStackTrace();
				}
				parameterMap.put("conditionCd", conditionCd);
				parameterMap.put("method", "viewCondition");
				String condDesc = dt.getConditionShortNm();
				dt.setConditionShortNm(condDesc);				
				String viewHref = request.getAttribute("viewHref") == null ? "": (String) request.getAttribute("viewHref");
				if(!viewHref.equals("")) {
					//String conLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&conditionCd="+ String.valueOf(dt.getConditionCd())+"#condition"+"\'"+")"+"\" >"+ condDesc + "</a>";
					//dt.setViewLink(conLink);
					String viewUrl = "<a href=\"javascript:viewConditionCd('" + conditionCd + "','" + dt.getPageNm() + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
					dt.setViewLink(viewUrl);
					String editUrl = "<a href=\"javascript:editConditionCd('" + conditionCd + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
					dt.setEditLink(editUrl);
				}
			}

		}
		}catch (Exception ex) {
			logger.fatal("Error in updateConditionLinks in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	
	public static Map<Object,Object> updateCondition(HttpSession session, ConditionDT dt)throws Exception{
		MainSessionCommand msCommand = null;
		ConditionDT conditionDT  = new ConditionDT();
		Map<Object, Object> errorMap = null;
		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "updateCondition";
			Object[] oParams =new Object[] {dt};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<Object> arr =(ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((arr != null) && (arr.size() > 0)) {
			errorMap = (HashMap<Object, Object>) arr.get(0);
				}
			}catch (Exception ex) {
				logger.fatal("Error in insertCondition in Action Util: "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
			}
			return errorMap;
	}
	
	// This method trims the leading and trailing spaces before inserting data in the DB
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
	
public static void handleErrors(Exception e, HttpServletRequest request, String actionType,String fileds) throws Exception{
	try{	
		//handle special scenario for codevalue general pagination after submit
		if(cvgPagination(request)) return;		
		if(e.toString().indexOf("SQLException") != -1 || e.toString().indexOf("SQLServerException") != -1) {
			if(actionType != null && actionType.equalsIgnoreCase("edit")) {
				request.setAttribute("error", "Database error while updating. Please check the values and try again.");
			} else if(actionType != null && actionType.equalsIgnoreCase("create")){
				if(e.toString().indexOf("PK")!= -1){
				   request.setAttribute("error", "A record already exists with this "+fileds+". Please enter a unique "+fileds+" to create a new record.");
				}
				else{
					request.setAttribute("error", "Database error while creating. Please check the values and try again.");
				}
			} else if(actionType != null && actionType.equalsIgnoreCase("search")) {
				
				request.setAttribute("error", "Database error while searching. Please check the values and try again.");				
			} else {
			request.setAttribute("error", e.getMessage());
		}
	  }
	}catch (Exception ex) {
		logger.fatal("Error in insertCondition in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	}
	}
private static boolean cvgPagination(HttpServletRequest request) {
	boolean found = false;
	Enumeration<?> enm = request.getParameterNames();
	while(enm.hasMoreElements()) {
		String paramName = (String) enm.nextElement();
		if(paramName != null && paramName.startsWith("d-")) {
			found = true;
			break;
		}
	}
	
	return found;
}

public static Object processRequest(Object[] oParams, HttpSession session) throws Exception {

	MainSessionCommand msCommand = null;
	Object obj = null;

	try {
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "processManageConditionRequest";
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		obj = arr.get(0);

		} catch (Exception ex) {
			logger.fatal("Error in ProcessRequest calling processManageConditionRequest: "+ex.getMessage(), ex);	
			throw new Exception(ex.getMessage(), ex);
		}
		return obj;	
	}
public ArrayList<Object> setUnitwithFieldDT(WaQuestionDT dt,WaQuestionDT dtUnit){
	ArrayList<Object> list = new ArrayList<Object>();
	if(dt.getRelatedUnitInd() != null && dt.getRelatedUnitInd().equals(NEDSSConstants.TRUE)){
		if(dtUnit != null){
			dt.setQuestionUnitIdentifier(dtUnit.getQuestionIdentifier());
			dtUnit.setUnitParentIndentifier(dt.getQuestionIdentifier());
		}
		list.add(dt);
		list.add(dtUnit);
	}else if(dt.getRelatedUnitInd() != null && dt.getRelatedUnitInd().equals(NEDSSConstants.FALSE)){
/*		if(dtUnit != null){
			dt.setQuestionUnitIdentifier(null);
			dtUnit.setUnitParentIndentifier(null);
			dtUnit.setRecordStatusCd(NEDSSConstants.INACTIVE);
		}
		list.add(dt);
		list.add(dtUnit);*/
		list.add(dt);
	}
	else{
		list.add(dt);
	}
	
	return list;
}

public Long setWATemplate(HttpSession session, WaTemplateVO vo, String actionMode)throws Exception{
	Long  waTemplateUid =null;
	MainSessionCommand msCommand = null;

	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "addPage";
		Object[] oParams =new Object[] {vo,actionMode};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
		waTemplateUid = (Long)aList.get(0);
		
		}catch (Exception ex) {
			logger.fatal("Error in adding the record to WA Template table in action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	
		 return  waTemplateUid; 
		
}
public WaTemplateDT setTheValuesForWaTemplateDT(WaTemplateDT dt,HttpServletRequest request, String actionMode) throws Exception{
	try{
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
	java.util.Date startDate= new java.util.Date();
	Timestamp aAddTime = new Timestamp(startDate.getTime());			
	dt.setLastChgTime(aAddTime);
	dt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));		
	if(actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)){
		dt.setItNew(false);
		dt.setItDirty(true);
		//dt.setVersionCtrlNbr(dt.getVersionCtrlNbr()+1);
	}else if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)){
		dt.setItNew(true);
		dt.setItDirty(false);
		//dt.setVersionCtrlNbr(new Integer(1));
		dt.setAddTime(aAddTime);
		dt.setRecStatusTime(aAddTime);
		dt.setRecStatusCd(NEDSSConstants.RECORD_STATUS_Active);
		dt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		dt.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
	}
	return dt;
	}catch (Exception ex) {
		logger.fatal("Error settng the value for WaTemplateDT -setTheValuesForWaTemplateDT in action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	}
}

public void setTheValuesForPageCondMappingDT(PageCondMappingDT dt,HttpServletRequest request, String actionMode) throws Exception{
	try{
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
	java.util.Date startDate= new java.util.Date();
	Timestamp aAddTime = new Timestamp(startDate.getTime());			
	dt.setLastChgTime(aAddTime);
	dt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));		
	if(actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)){
		dt.setItNew(false);
		dt.setItDirty(true);
		//dt.setVersionCtrlNbr(dt.getVersionCtrlNbr()+1);
	}else if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)){
		dt.setItNew(true);
		dt.setItDirty(false);
		//dt.setVersionCtrlNbr(new Integer(1));
		dt.setAddTime(aAddTime);
		dt.setRecordStatusTime(aAddTime);
		dt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
		dt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		dt.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
	}
	}catch (Exception ex) {
		logger.fatal("Error settng the value for PageCondMappingDT -setTheValuesForPageCondMappingDT in action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	}
}



	public PageManagementProxyVO getPageDraft( Long waTemplateUid, HttpSession session) throws Exception{
		
		MainSessionCommand msCommand = null;
		PageManagementProxyVO pmProxyVO = new PageManagementProxyVO();
		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getPageDraft";
			Object[] oParams =new Object[] {waTemplateUid,false};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				pmProxyVO = (PageManagementProxyVO)aList.get(0);
		
			}	
		  }catch (Exception ex) {
				logger.fatal("Error in getPageDraft in Action Util: "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
		  }
		return pmProxyVO;
	}

    /**
     * Retrieve a collection of PageElementDTOs. This collection is created
     * from the collection of PageElementVOs stored in the PageManagementProxyVO
     * object stored in session. The resulting collection that is returned 
     * is basically a light weight version used for rendering the UI.
     * @param session
     * @return
     */
	public Collection<PageElementDTO> getPageElementDTOColl(HttpSession session) throws Exception
	{
		Collection<PageElementDTO> peDTOist = new ArrayList<PageElementDTO>();
		try{
			
	    // retrieve the proxyVO object from session
		PageManagementProxyVO pmProxyVO = (PageManagementProxyVO)session.getAttribute("pageData");
		
		// get the elementVO collection in proxyVO 
		Collection<Object> elementVoColl = pmProxyVO.getThePageElementVOCollection();

		// create a new set of DTOs, the return value.
		
		peDTOist = getPageElementDTOsFromElementVOColl(elementVoColl);
		}catch (Exception ex) {
			logger.fatal("Error in getPageElementDTOColl in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
		
		// return the new collection  // the place where this method is being called , is eating away this exception.
		//Need to ask Karthik to throw it back...retrieveCurrentPage() method in PageBuilderForm
        return peDTOist;
	}
	
	public Long setPageDraft(HttpSession session,PageManagementProxyVO pageManagementProxyVO, 
	        String pageElementOrder)throws Exception {
	    Long  returnVal = null;
	    MainSessionCommand msCommand = null;

	    try {
	        String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
	        String sMethod = "savePageDraft";
	        Object[] oParams =new Object[] {pageManagementProxyVO, pageElementOrder};		
	        MainSessionHolder holder = new MainSessionHolder();
	        msCommand = holder.getMainSessionCommand(session);
			
	        ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
	        returnVal = (Long)aList.get(0);
	    } 
	    catch (Exception ex) {
	        logger.fatal("Error in savePageDraft in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
		
	    return returnVal; 
	}
	
	/**
	 * Convert a collection of PageElementVO objects into corresponding PageElementDTO objects.
	 * @param elementVoColl
	 * @return
	 */
	public Collection<PageElementDTO> getPageElementDTOsFromElementVOColl(Collection<Object> elementVoColl) throws Exception
	{
	
		Collection<PageElementDTO> peDTOist = new ArrayList<PageElementDTO>();		
		try{
		if(elementVoColl != null){
		    for (Object singleObj : elementVoColl) {
                PageElementVO peVo = (PageElementVO) singleObj;
                PageElementDTO pageElementDTO = new PageElementDTO();
                WaUiMetadataDT waUiMetadataDT = peVo.getWaUiMetadataDT();
                WaRdbMetadataDT waRdbMetadataDT = peVo.getWaRdbMetadataDT();
                WaNndMetadataDT waNndMetadataDT = peVo.getWaNndMetadataDT();
                WaQuestionDT waQuestionDT = peVo.getWaQuestionDT();
                if(waUiMetadataDT.getCoinfectionIndCd()!= null){
                	logger.debug("CoinfectionIndCd from  WaUiMetadataDT : "  +waUiMetadataDT.getCoinfectionIndCd());
                	pageElementDTO.setIsCoInfection(waUiMetadataDT.getCoinfectionIndCd());
                }

                if((waUiMetadataDT.getStandardNndIndCd() != null && waUiMetadataDT.getStandardNndIndCd().equals("F")) ||(waUiMetadataDT.getStandardNndIndCd() == null)){
	                pageElementDTO.setPageElementUid(peVo.getPageElementUid());
	                
	                // element label
	                if (waUiMetadataDT != null && waUiMetadataDT.getQuestionLabel() != null) {
	                    pageElementDTO.setElementLabel(waUiMetadataDT.getQuestionLabel());
	                }
	                else if (waQuestionDT != null && waQuestionDT.getQuestionLabel() != null){
	                    pageElementDTO.setElementLabel(waQuestionDT.getQuestionLabel());
	                }
	                
	                // question identifier
	                if (waUiMetadataDT != null && waUiMetadataDT.getQuestionIdentifier() != null) {
	                    pageElementDTO.setQuestionIdentifier(waUiMetadataDT.getQuestionIdentifier());
	                }
	                else if (waQuestionDT != null && waQuestionDT.getQuestionIdentifier() != null){
	                    pageElementDTO.setQuestionIdentifier(waQuestionDT.getQuestionIdentifier());
	                }
	                
	                // standard question identifier
	                if (waUiMetadataDT != null && waUiMetadataDT.getStandardQuestionIndCd() != null) {
	                    pageElementDTO.setIsStandardized(waUiMetadataDT.getStandardQuestionIndCd());
	                }
	                else {
	                    pageElementDTO.setIsStandardized(NEDSSConstants.FALSE);
	                }
	                
	                // Block Name
	                if (waUiMetadataDT != null && waUiMetadataDT.getBlockName() != null) {
	                    pageElementDTO.setBlockName(waUiMetadataDT.getBlockName());
	                }
	                else if (waQuestionDT != null && waQuestionDT.getBlockName() != null){
	                    pageElementDTO.setBlockName(waQuestionDT.getBlockName());
	                }
	                
	                
	                // Data Mart Repeat Number
	                if (waRdbMetadataDT != null && waRdbMetadataDT.getDataMartRepeatNbr() != null) {
	                    pageElementDTO.setDataMartRepeatNumber(waRdbMetadataDT.getDataMartRepeatNbr());
	                }
	                else if (waQuestionDT != null && waQuestionDT.getDataMartRepeatNumber() != null){
	                    pageElementDTO.setDataMartRepeatNumber(waQuestionDT.getDataMartRepeatNumber());
	                }
	                
	                // Data Mart Column Name
	                if (waUiMetadataDT != null && waUiMetadataDT.getUserDefinedColumnNm() != null) {
	                    pageElementDTO.setDataMartColumnNm(waUiMetadataDT.getUserDefinedColumnNm());
	                }
	                else if (waQuestionDT != null && waQuestionDT.getUserDefinedColumnNm() != null){
	                    pageElementDTO.setDataMartColumnNm(waQuestionDT.getUserDefinedColumnNm());
	                }

	                // published question identifier
	                if (waUiMetadataDT != null && waUiMetadataDT.getPublishIndCd() != null) {
	                    pageElementDTO.setIsPublished(waUiMetadataDT.getPublishIndCd());    
	                }
	                else {
	                    pageElementDTO.setIsPublished(NEDSSConstants.FALSE);
	                }
	                	
	                if (waUiMetadataDT != null && waUiMetadataDT.getCoinfectionIndCd() != null) {
	                    pageElementDTO.setIsCoInfection(waUiMetadataDT.getCoinfectionIndCd());
	                }
	                else if (waQuestionDT != null && waQuestionDT.getCoInfQuestion() != null){
	                    pageElementDTO.setIsCoInfection(waQuestionDT.getCoInfQuestion());
	                }
	                
	                // Batch Entry group id
	                if (waUiMetadataDT != null && waUiMetadataDT.getQuestionGroupSeqNbr() != null) {
	                    pageElementDTO.setQuestionGroupSeqNbr(waUiMetadataDT.getQuestionGroupSeqNbr());    
	                }
	                // DataLocation to check if it goes to case_answer table
	                if (waUiMetadataDT != null && waUiMetadataDT.getDataLocation() != null) {
	                    pageElementDTO.setDataLocation(waUiMetadataDT.getDataLocation());    
	                }

	                // element type
	                if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1002)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_PAGE);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1010)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_TAB);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1015)))             
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_SECTION);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1016)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_SUBSECTION);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1014)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_COMMENT);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1003)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_HYPERLINK);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1012)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_LINE_SEPARATOR);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1030)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_PARTICIPANT_LIST);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1032)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_PATIENT_SEARCH);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1033)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_ACTION_BUTTON);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1034)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_SET_VALUES_BUTTON);
	                else if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null && peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(PageMetaConstants.ORIGDOCLIST)))
	                    pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_ORIG_DOC_LIST);
	                else
	                	 pageElementDTO.setElementType(NEDSSConstants.PAGE_ELEMENT_TYPE_QUESTION);
	                
	                //add the UI Component type also 
	                if(peVo.getWaUiMetadataDT() != null && peVo.getWaUiMetadataDT().getNbsUiComponentUid()!= null) {
	                	pageElementDTO.setElementComponentType(peVo.getWaUiMetadataDT().getNbsUiComponentUid().toString());
	                }
	                peDTOist.add(pageElementDTO);
                }
		    }
		}
		}catch (Exception ex) {
	        logger.fatal("Error in getPageElementDTOsFromElementVOColl in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
		return peDTOist;
	}
	
	/**
	 * Add a collection of page elements to the page.
	 * <b> Note: </b> The pageElementId for each element is set before they are added to
	 * the page.
	 * @param request
	 * @param elementVoColl
	 * @return
	 */
	public ArrayList<Object> addPageElementsToPMProxyVO(HttpServletRequest request, Collection<Object> elementVoColl) throws Exception
	{
	    // update the pageElementId values in the new list
		Collection<Object> newElts = new ArrayList<Object>();
		try{
		if(elementVoColl != null ){
		    for (Object singleObj : elementVoColl) {
		        PageElementVO peVo = (PageElementVO) singleObj;
		        if(peVo.getPageElementUid() == null || 
		                (peVo.getPageElementUid() != null && peVo.getPageElementUid().compareTo(new Long(0)) == 0)) {
		            // get a unique id for this new element
		            Long genUid = (Long)request.getSession().getAttribute("genUid") - 1;
		            request.getSession().setAttribute("genUid", genUid);
		            peVo.setPageElementUid(genUid);                 
		            newElts.add(peVo);
		        }
		    }
		}
		
		// add the new list to the existing collection
        PageManagementProxyVO pmProxyVO = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
		Collection<Object> existingElts = pmProxyVO.getThePageElementVOCollection();
		if (existingElts != null) {
		    existingElts.addAll(newElts);
		    pmProxyVO.setThePageElementVOCollection(existingElts);
		}
		else {
		    pmProxyVO.setThePageElementVOCollection(newElts);
		}

		// set the object back in session
		request.getSession().setAttribute("pageData", pmProxyVO);
		}catch (Exception ex) {
	        logger.fatal("Error in addPageElementsToPMProxyVO in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
		// return new elements with updated page element Ids 
		return (ArrayList<Object>) newElts;
	}
	
	
	/**
	 * Update a single element in the collection of VOs present in session and set the 
	 * updated collection back in session

	 * @param request
	 * @param updatedElt
	 * @return
	 */
	public PageElementVO updateSinglePageElementInPMProxyVO(HttpServletRequest request, PageElementVO updatedElt) throws Exception
	{
	    PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
        Collection<Object> existingPageEltVOs = pmpVo.getThePageElementVOCollection();
        Collection<Object> updatedPageEltVOs = new ArrayList<Object> ();
        try{
        if(updatedElt.getPageElementUid() != null && updatedElt.getPageElementUid().compareTo(new Long(0)) != 0) {
            
            // add all elements from the existing collection 
            // to a new collection except the one to update
            for (Object peVo : existingPageEltVOs) {
                Long val1 = ((PageElementVO) peVo).getPageElementUid();
                Long val2 = updatedElt.getPageElementUid();
                if (val1 != null && val2 != null && val1.equals(val2)) {
                    continue;
                }
                else {
                    updatedPageEltVOs.add(peVo);
                }
            }
            
            // add the single update element to the new collection
            updatedPageEltVOs.add(updatedElt);
        }
        
        // update object in session
        pmpVo.setThePageElementVOCollection(updatedPageEltVOs);
        request.getSession().setAttribute("pageData", pmpVo);
        
		}catch (Exception ex) {
	        logger.fatal("Error in addPageElementsToPMProxyVO in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
	        
        return updatedElt;
	}
	
	/**
	 * Retrieve a single page element object from the PageMangementProxyVO stored in session. 
	 * The object returned is uniquely identified by its element ID.
	 * @param waElementUid
	 * @return
	 */
	public PageElementVO retrievePageElementInPMProxyVO(HttpServletRequest request, String pageElementId) throws Exception
	{
	    PageElementVO returnVal = new PageElementVO();
	    try{
	    PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
	    for (Object peVo : pmpVo.getThePageElementVOCollection()) {
	        Long val1 = ((PageElementVO) peVo).getPageElementUid();
	        Long val2 = Long.parseLong(pageElementId);
	        if (val1 != null && val2 != null && val1.equals(val2)) {
	            returnVal = (PageElementVO) peVo;
	            break;
	        }
	    }
	    }catch (Exception ex) {
	        logger.fatal("Error in retrievePageElementInPMProxyVO in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
	    return returnVal;
	}

	/**
	 * Delete a single page element object from the PageMangementProxyVO stored in session.
	 * @param request 
	 * @param pageElementId
     * @return true is successfully delete, false otherwise.
	 */
	public boolean deletePageElementInPMProxyVO(HttpServletRequest request, String pageElementId)throws Exception
    {
        try{
        PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
        int initialCount = pmpVo.getThePageElementVOCollection().size();
        Collection<Object> updatedPageEltVOs = new ArrayList<Object>();
        
        for (Object peVo : pmpVo.getThePageElementVOCollection()) {
            Long val1 = ((PageElementVO) peVo).getPageElementUid();
            Long val2 = Long.parseLong(pageElementId);
            if (val1 != null && val2 != null && ! val1.equals(val2)) {
                updatedPageEltVOs.add(peVo);
            }
        }
        
        // update object in session
        pmpVo.setThePageElementVOCollection(updatedPageEltVOs);
        request.getSession().setAttribute("pageData", pmpVo);
        
        if (initialCount > pmpVo.getThePageElementVOCollection().size()) {
            return true;
        }
        else {
            return false;
        }
        }catch (Exception ex) {
	        logger.fatal("Error in deletePageElementInPMProxyVO in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
    }
	
	public String checkAssociatedRulesForQuestionId(HttpServletRequest request, String pageElementId)throws Exception
    {
        try{
        	 PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
             PageElementVO returnVal = new PageElementVO();
             
             for (Object peVo : pmpVo.getThePageElementVOCollection()) {
                 Long val1 = ((PageElementVO) peVo).getPageElementUid();
                 Long val2 = Long.parseLong(pageElementId);
                 if (val1 != null && val2 != null &&  val1.equals(val2)) {
                	 returnVal = (PageElementVO) peVo;
                	 break;
                 }
             }
             
             PageManagementDAOImpl dao = new PageManagementDAOImpl();
        	 String associatedRules = dao.checkRulesIdAssocRulesForQuestionId(returnVal.getWaUiMetadataDT().getWaTemplateUid(),returnVal.getWaUiMetadataDT().getQuestionIdentifier());
        	
        	 if(associatedRules != null && !associatedRules.isEmpty())
        		 return associatedRules;
        	else 
        		 return null;
        	 
        }catch (Exception ex) {
	        logger.fatal("Error in checkAssociatedRulesForQuestionId in Action Util: "+ex.getMessage(), ex);
	        throw new Exception(ex.getMessage(), ex);
	    }
        }

	/**
	 * Persist the form present in session to the database. 
	 * <br/> <b> Note: </b> Use the element order received as a string parameter
	 * to arrange the elements in session before persisting it.
	 * @param pageElementsOrderCsv
	 */
	public Long savePageAsDraft(HttpServletRequest request, String pageElementsOrderCsv, String templind)throws Exception
	{
	    Long returnVal = null;
	    try{

	    	// handle DWR case here
	    	if (request == null) {
	    		WebContext context = WebContextFactory.get();
	    		request = context.getHttpServletRequest();
	    	}

	    	HttpSession session =  request.getSession();
	    	PageManagementProxyVO pmProxyVO = (PageManagementProxyVO) session.getAttribute("pageData");
	    	if (templind != null && templind.equals("true")) {
	    		pmProxyVO.getWaTemplateDT().setTemplateType("Template");
	    	}
	    	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
	    	pmProxyVO = setValuesforPmProxyVO(pmProxyVO,nbsSecurityObj, request);

	    	try {
	    		returnVal = setPageDraft(session, pmProxyVO, pageElementsOrderCsv);  	
	    	} 
	    	catch(Exception e) {
	    		logger.fatal("Error from the back end while Submitting the Edit Page Builder:" + e.getMessage(), e);
	    		throw new ServletException("Error while submitting the Edit page Builder"+e.getMessage(), e);
	    	}
	    }catch(Exception e) {
	    	logger.fatal("Error in ActionUtil while Submitting the Edit Page Builder"+e.getMessage(), e);
	    	throw new ServletException("Error occurred while submitting the Edit page Builder"+e.getMessage(), e);
	    }
	    
	    return returnVal;	  
	}
	
	
	public PageManagementProxyVO setValuesforPmProxyVO(PageManagementProxyVO pmProxyVO,NBSSecurityObj nbsSecurityObj, HttpServletRequest request) throws Exception{
		
		try{		
		java.util.Date startDate= new java.util.Date();
		Timestamp aAddTime = new Timestamp(startDate.getTime());
		
		WaTemplateDT waTemplateDT  = pmProxyVO.getWaTemplateDT();
		
		waTemplateDT.setTemplateType(waTemplateDT.getTemplateType());
    	waTemplateDT.setXmlPayload(waTemplateDT.getXmlPayload());
    	waTemplateDT.setBusObjType(waTemplateDT.getBusObjType());
    	waTemplateDT.setWaTemplateUid(pmProxyVO.getWaTemplateDT().getWaTemplateUid());
    	waTemplateDT.setLastChgTime(aAddTime);
		waTemplateDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
		waTemplateDT.setAddTime(aAddTime);
		waTemplateDT.setRecStatusCd(waTemplateDT.getRecStatusCd());
		waTemplateDT.setRecStatusTime(waTemplateDT.getRecStatusTime());
		pmProxyVO.setWaTemplateDT(waTemplateDT);
		
		Collection<Object> pageEleVoColl = pmProxyVO.getThePageElementVOCollection();
		if(pageEleVoColl != null && pageEleVoColl.size()>0){
			 for (Object peVo : pageEleVoColl) {
				   PageElementVO pageElementVO = (PageElementVO)peVo;	
					WaUiMetadataDT waUiMetadataDT = pageElementVO.getWaUiMetadataDT();
					WaRdbMetadataDT waRdbMetadataDT = pageElementVO.getWaRdbMetadataDT();
					WaNndMetadataDT waNndMetadataDT = pageElementVO.getWaNndMetadataDT();
					WaQuestionDT waQuestionDT =pageElementVO.getWaQuestionDT();
					
						try{
							
					//setting the values for UIMetadata
							logger.debug("waUiMetadataDT.getQuestionIdentifier is : "  +waUiMetadataDT.getQuestionIdentifier());

							if(waUiMetadataDT.getCoinfectionIndCd()== null){
								if(waQuestionDT != null){
									logger.debug("Question id is : " + waQuestionDT.getQuestionIdentifier());
									
									waUiMetadataDT.setCoinfectionIndCd(waQuestionDT.getCoInfQuestion());
								}else if(waUiMetadataDT.getQuestionIdentifier()!= null){
									logger.debug("Retrieve waQuestion for : "  +waUiMetadataDT.getQuestionIdentifier());
									waQuestionDT = getWaQuestionByIdentifier(waUiMetadataDT.getQuestionIdentifier());
									if(waQuestionDT!=null)
										waUiMetadataDT.setCoinfectionIndCd(waQuestionDT.getCoInfQuestion());
								}
							}
				//	 waUiMetadataDT.setNbsUiComponentUid(new Long(1008));
			    	 waUiMetadataDT.setWaTemplateUid(pmProxyVO.getWaTemplateDT().getWaTemplateUid());
			    	 waUiMetadataDT.setVersionCtrlNbr(new Integer(1));
			    	 waUiMetadataDT.setLastChgTime(aAddTime);
			    	 waUiMetadataDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
			    	 waUiMetadataDT.setAddTime(aAddTime);
			    	 waUiMetadataDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
			    	 waUiMetadataDT.setRecordStatusTime(aAddTime);
			    	 if((waUiMetadataDT.getRequiredInd()== null)||(waUiMetadataDT.getRequiredInd()!= null && waUiMetadataDT.getRequiredInd().equals("")))
			    		 waUiMetadataDT.setRequiredInd(NEDSSConstants.FALSE);
			    	 
			    	 if((waUiMetadataDT.getStandardNndIndCd() == null)||(waUiMetadataDT.getStandardNndIndCd()!= null && waUiMetadataDT.getStandardNndIndCd().equals("")))
			    		 waUiMetadataDT.setStandardNndIndCd(NEDSSConstants.FALSE);
			    	 // Adding the Question Identifier for static element here //
			    	 if(pageElementVO.getPageElementUid() != null && pageElementVO.getPageElementUid() < 0){
				    	 if (waUiMetadataDT.getQuestionIdentifier() == null || (waUiMetadataDT.getQuestionIdentifier() != null &&  waUiMetadataDT.getQuestionIdentifier().equals(""))){
				    		String stateSpec = UidClassCodes.NBS_CLASS_CODE; 			    		 
				    		UidGeneratorHelper uidGen = new UidGeneratorHelper();
				    		Long quesIdLong = uidGen.getNbsIDLong(UidClassCodes.NBS_QUESTION_ID_LDF_CLASS_CODE).longValue();
				    	    waUiMetadataDT.setQuestionIdentifier(stateSpec+quesIdLong.toString());
				    	 }
			    	 }
			    	 
			    	 pageElementVO.setWaUiMetadataDT(waUiMetadataDT);
						} catch(Exception e) {
					        logger.fatal("Error in setValuesforPmProxyVO on waUiMetadataDT Label : "+waUiMetadataDT.getQuestionLabel()+ ", Exception: "+e.getMessage(), e);
					        throw new ServletException("Error in setValuesforPmProxyVO"+e.getMessage(), e);
					    }
				    	try{ 
			    	 //setting the value for RdbMetadata
				if (waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1002)
						&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1010)
						&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1015)
						&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1014)
						&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1016)){
								
					       // For new questions create a new RDB object
							if(waUiMetadataDT.getRdbcolumnNm() != null && pageElementVO.getPageElementUid()<0){
								waRdbMetadataDT = new WaRdbMetadataDT();
								waRdbMetadataDT.setRdbTableNm(waUiMetadataDT.getRdbTableNm());					    			    	 
						    	waRdbMetadataDT.setRdbcolumNm(waUiMetadataDT.getRdbcolumnNm());					    	
						    	waRdbMetadataDT.setDataMartRepeatNbr(waRdbMetadataDT.getDataMartRepeatNbr());
							}
							
							if(waRdbMetadataDT!=null && waUiMetadataDT!=null)
								waRdbMetadataDT.setUserDefinedColumnNm(waUiMetadataDT.getUserDefinedColumnNm());
							
							if(waRdbMetadataDT!=null){
							     waRdbMetadataDT.setRptAdminColumnNm(waUiMetadataDT.getReportLabel());
								 waRdbMetadataDT.setQuestionIdentifier(waUiMetadataDT.getQuestionIdentifier());
						    	 waRdbMetadataDT.setLastChgTime(aAddTime);
						    	 waRdbMetadataDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
						    	 waRdbMetadataDT.setAddTime(aAddTime);
						    	 waRdbMetadataDT.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
						    	 waRdbMetadataDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
						    	 waRdbMetadataDT.setRecordStatusTime(aAddTime);
						    	 waRdbMetadataDT.setWaTemplateUid(waTemplateDT.getWaTemplateUid());
						    	 waRdbMetadataDT.setWaUiMetadataUid(waUiMetadataDT.getWaUiMetadataUid());
						    	 waRdbMetadataDT.setLocalId(waUiMetadataDT.getLocalId());
						    	 // This need to be removed after the data is clean in 4.0 itself 
	//					    	 if(waRdbMetadataDT.getRdbTableNm() == null){
	//					    		 waRdbMetadataDT.setRdbTableNm("D1");
	//					    	 }
	//					    	 if(waRdbMetadataDT.getRdbcolumNm() == null){
	//					    		 waRdbMetadataDT.setRdbcolumNm("Column1");
	//					    	 }
	//					    	 if(waRdbMetadataDT.getUserDefinedColumnNm() == null){
	//					    		 waRdbMetadataDT.setUserDefinedColumnNm("D1_Column1");
	//					    	 }
						    	 
						    	 if(waRdbMetadataDT.getRdbTableNm() == null || waRdbMetadataDT.getUserDefinedColumnNm() == null){
						    		 logger.info("RdbTableNm or UserDefinedColumnNm is null for QuestionIdentifier: "+waUiMetadataDT.getQuestionIdentifier()+", WaTemplateUid:"+waTemplateDT.getWaTemplateUid());
						    	 }
						    	 
						    	 updateRDBTableNameWhileAddingQuestionOnPage(waTemplateDT.getBusObjType(), waUiMetadataDT, waRdbMetadataDT, request);
					    	 
					    	 }
					    	 
					    	 
					   
					    	 pageElementVO.setWaRdbMetadataDT(waRdbMetadataDT);
				}
				    	} catch(Exception e) {
					        logger.fatal("Error in setValuesforPmProxyVO on waRdbMetadataDT Label : "+waUiMetadataDT.getQuestionLabel()+", Exception: "+e.getMessage(), e);
					        throw new ServletException("Error in setValuesforPmProxyVO"+e.getMessage(), e);
					    }
				    	try{
			    	 //Setting the values for NndMetadata 
			 	 if(waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1002)
								&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1010)
								&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1015)
								&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1014)
								&& waUiMetadataDT.getNbsUiComponentUid().longValue()!=(1016)){
			 		 
  			 		       // For new questions create a new NND object - if we need to have an NND data 
			 		 		if((waUiMetadataDT.getNndMsgInd() != null && 
			 		 		        waUiMetadataDT.getNndMsgInd().equals(NEDSSConstants.TRUE)) && 
			 		 		        (waUiMetadataDT.getQuestionReqNnd()!= null && 
			 		 		                waUiMetadataDT.getQuestionReqNnd().length()>0))
			 		 		{			 		 		
							 		if (waNndMetadataDT == null) 
							 		{
							 			waNndMetadataDT = new WaNndMetadataDT();
							 			waNndMetadataDT.setWaQuestionUid(waUiMetadataDT.getWaQuestionUid());
								    	waNndMetadataDT.setHl7SegmentField(waUiMetadataDT.getHl7Segment());
								    	waNndMetadataDT.setQuestionRequiredNnd(waUiMetadataDT.getQuestionReqNnd());
								    	waNndMetadataDT.setQuestionDataTypeNnd(waUiMetadataDT.getQuestionDataTypeNnd());
								    	waNndMetadataDT.setWaUiMetadataUid(waUiMetadataDT.getWaUiMetadataUid());
								    	waNndMetadataDT.setOrderGroupId(waUiMetadataDT.getOrderGrpId());
								    	waNndMetadataDT.setQuestionIdentifier(waUiMetadataDT.getQuestionIdentifier());
								    	waNndMetadataDT.setQuestionIdentifierNnd(waUiMetadataDT.getQuestionIdentifierNnd());
								    	waNndMetadataDT.setQuestionLabelNnd(waUiMetadataDT.getQuestionLabelNnd());
								    	
								    	// TODO: set other properties as required. Not known at this point. Very vague!!!
								    	
								    	//Remove this hard coded value  after Christi fix the metadata
								    	if(waNndMetadataDT.getQuestionDataTypeNnd() == null){
								    		waNndMetadataDT.setQuestionDataTypeNnd("CWE");
								    	}
								    	if(waNndMetadataDT.getHl7SegmentField() == null){
								    		waNndMetadataDT.setHl7SegmentField("OBX-3.0");
								    	}
								    		
								    	
									 }
							 		
				 		 		if(waNndMetadataDT!=null){
						    		 waNndMetadataDT.setLastChgTime(aAddTime);					    		
							    	 waNndMetadataDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
							    	 waNndMetadataDT.setAddTime(aAddTime);
							    	 waNndMetadataDT.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
							    	 waNndMetadataDT.setRecStatusCd(NEDSSConstants.RECORD_STATUS_Active);
							    	 waNndMetadataDT.setRecStatusTime(aAddTime);
							    	 waNndMetadataDT.setWaTemplateUid(waTemplateDT.getWaTemplateUid());
							    	 waNndMetadataDT.setLocalId(waUiMetadataDT.getLocalId());
								     waNndMetadataDT.setQuestionRequiredNnd(waUiMetadataDT.getQuestionReqNnd());
								     waNndMetadataDT.setQuestionDataTypeNnd(waUiMetadataDT.getQuestionDataTypeNnd());
								     waNndMetadataDT.setQuestionIdentifier(waUiMetadataDT.getQuestionIdentifier());
								     waNndMetadataDT.setQuestionIdentifierNnd(waUiMetadataDT.getQuestionIdentifierNnd());
								     waNndMetadataDT.setQuestionLabelNnd(waUiMetadataDT.getQuestionLabelNnd());
				 		 		}
						    	 pageElementVO.setWaNndMetadataDT(waNndMetadataDT);
			 		 		}
			 	 }
				   }catch(Exception e) {
				        logger.fatal("Error in setValuesforPmProxyVO on waNndMetadataDT Label : "+waUiMetadataDT.getQuestionLabel()+", Exception: "+e.getMessage(), e);
				        throw new ServletException("Error in setValuesforPmProxyVO"+e.getMessage(), e);
			}
				 }
			    	  
				
			}
		}catch(Exception ex) {
	        logger.fatal("Error in ActionUtil in setValuesforPmProxyVO method"+ex.getMessage(), ex);
	        throw new Exception("Error in ActionUtil in setValuesforPmProxyVO method:"+ex.getMessage(), ex);
	    }
	    
			return pmProxyVO;
		}
	
	public Collection<Object> getPageSummaries(HttpSession session) throws Exception{
		
		MainSessionCommand msCommand = null;
		Map<Object, Object> summaryMap = new HashMap<Object, Object>();
		Collection<Object> pageList = new ArrayList<Object>();
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getPageSummaries";
		Object[] oParams =new Object[]{};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			summaryMap = (HashMap<Object,Object>)aList.get(0);
            pageList = pageListWithRelatedConditionsandLinks(summaryMap, session);
            
		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getting the page Summary in Action Util: ", ex.getMessage());
			throw new Exception(ex.getMessage(), ex);
	  }
	  return pageList;
	}
	
	private Collection<Object> pageListWithRelatedConditionsandLinks(Map<Object,Object> summaryMap, HttpSession session) throws Exception{
		
		if(summaryMap==null || summaryMap.size()==0)
			return null;
		Collection<Object> pageSumaryList = (Collection<Object>)summaryMap.get(NEDSSConstants.TEMPLATE_SUMMARIES);
		Collection<Object> pageCondMappingList = (Collection<Object>)summaryMap.get(NEDSSConstants.PAGE_COND_MAPPING_SUMMARIES);
		if(pageCondMappingList!=null){
			Map<Object,Object> pageCondMappingMap = new HashMap<Object,Object>();
			Iterator<Object> ite = pageCondMappingList.iterator();
			while(ite.hasNext()){
				PageCondMappingDT pcmDT = (PageCondMappingDT)ite.next();
				if (pageCondMappingMap.get(pcmDT.getWaTemplateUid())==null){
					Collection<Object> mappingColl = new ArrayList<Object>();
					mappingColl.add(pcmDT);
					pageCondMappingMap.put(pcmDT.getWaTemplateUid(), mappingColl);
				}
				else{
					((Collection<Object>)pageCondMappingMap.get(pcmDT.getWaTemplateUid())).add(pcmDT);
				}
				
			}
			makePageLibraryLink(pageSumaryList,pageCondMappingMap, session);
		}
		return pageSumaryList;
	}
	
	public boolean saveXMLtoDB(HttpSession session, String xmlContents ,Long templateUID)throws Exception{
		boolean saveStatus= false;
		MainSessionCommand msCommand = null;	
		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "saveXMLtoDB";
			Object[] oParams =new Object[] {xmlContents,templateUID};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);			
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
			saveStatus = (Boolean)aList.get(0);
			
			}catch (Exception ex) {
				logger.fatal("Error in saveXMLtoDB in Action Util: "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
			}
		
			 return  saveStatus; 
 		
	}
	
	/**
	 * @param pageList
	 * method get collection of WaTemplateDT and set View and Edit link
	 * @param session 
	 */
	public static void makePageLibraryLink(Collection<Object> pageList, Map<Object,Object> pageCondMappingMap, HttpSession session) throws Exception {
		try{
		if (pageList != null ) {

	    	HashMap<Object,Object> pgMap = new HashMap<Object,Object>();
			Iterator<Object> it = pageList.iterator();
			while(it.hasNext()){
				WaTemplateDT dt = (WaTemplateDT) it.next();
				String str = dt.getConditionCdDesc() +" (" + dt.getConditionCd()+ ")";
				dt.setConditionCdDesc(str);
				String portReqIndCd = dt.getPortReqIndCd();
				String businessObjectType = dt.getBusObjType();
				String temp = "T".equalsIgnoreCase(dt.getPortReqIndCd())?"Yes":"No";
				dt.setPortReqIndCd(temp);
    			dt.setBusObjType(CachedDropDowns.getCodeDescTxtForCd(dt.getBusObjType(), "BUS_OBJ_TYPE"));
				Integer version = dt.getPublishVersionNbr();
				if(version == null && "Draft".equalsIgnoreCase(dt.getTemplateType())){
					dt.setTemplateType("Initial Draft");
				}else if(version != null && version.intValue()==0 && "Draft".equalsIgnoreCase(dt.getTemplateType())){
					dt.setTemplateType("Published with Draft");
				}
				String pgName  = dt.getTemplateNm();
				Long uid= dt.getWaTemplateUid();
				dt.setViewLink("View");
				String viewUrl = "<a href='javascript:onClickViewLink(\"/nbs/PreviewPage.do?from=L&waTemplateUid="+ uid +"&method=viewPageLoad\")'><img src=\"page_white_text.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View\" alt=\"View\"/></a>";				
				dt.setViewLink(viewUrl);
				//if(dt.getTemplateType()!=null && dt.getTemplateType().equals(NEDSSConstants.DRAFT)){
	    		dt.setEditLink("Edit");
	    		String editUrl="";
	    		if("Published".equalsIgnoreCase(dt.getTemplateType())){
	    			String pgNameTrim = pgName.replace("'", " ");
	    			editUrl = "<a href='javascript:draftPopUp(\""+ pgNameTrim +"\",\""+ dt.getWaTemplateUid()+ "\")'><img tabindex=\"0\" src=\"page_white_edit.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"Edit\" alt=\"Edit\"/></a>";
	    		}else{
	    			editUrl = "<a href='javascript:onClickEditLink(\"/nbs/ManagePage.do?fromWhere= L&waTemplateUid="+ uid +"&method=editPageContentsLoad\")'><img src=\"page_white_edit.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"Edit\" alt=\"Edit\"/></a>";	    			
	    		}
	    		
	    		if(businessObjectType!=null && (businessObjectType.equals(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE)|| businessObjectType.equals(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE)))
	    			editUrl = "<img src=\"page_white_edit_disabled.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"Edit\" alt=\"Edit\"/>";
				dt.setEditLink(editUrl);
				if(pageCondMappingMap.containsKey(dt.getWaTemplateUid())){
					setRelatedConditions(dt,(ArrayList<Object>)pageCondMappingMap.get(dt.getWaTemplateUid()));
				}
				if(dt.getTemplateNm() != null){
    				pgMap.put(dt.getWaTemplateUid(),dt.getTemplateNm());	
    			}				
			}
	    	//put the current list of page names in session
	    	session.setAttribute("pgMap", pgMap);
		}
		}catch (Exception ex) {
			logger.fatal("Error in makePageLibraryLink in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public static void setRelatedConditions(WaTemplateDT waTemplateDT,ArrayList<Object> aList){
        if(aList == null || aList.size()==0)
        	return;
        Iterator<Object> ite = aList.iterator();
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        boolean flag=false;
        int i=0;
		while (ite.hasNext()) {
		
			if(flag){
				if(i<4)
				 sb1.append("</br>");
				sb2.append(", ");
			}
			PageCondMappingDT SummaryDT = (PageCondMappingDT)ite.next();
			if (SummaryDT != null && i<4){
				sb1.append(SummaryDT.getConditionDesc());
				sb1.append(" (").append(SummaryDT.getConditionCd()).append(")");
			}
			if (SummaryDT!=null){
				sb2.append(SummaryDT.getConditionDesc());
				sb2.append(" (").append(SummaryDT.getConditionCd()).append(")");
			}
			flag=true;
			i++;
	     }
		if(aList.size()>4){
			sb1.append("</br>......");
		}
		waTemplateDT.setRelatedConditions(sb1.toString());
		waTemplateDT.setRelatedConditionsForPrint(sb2.toString());
	}
	
	public static void makePageLibraryLink(WaTemplateDT dt, String type) throws Exception{
        try{
		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		Long waTempUid = dt.getWaTemplateUid();
		parameterMap.put("waTemplateUid", waTempUid);
		

		if (type.equals("VIEW"))
			parameterMap.put("method", "viewPageLoad");
		else
			parameterMap.put("method", "editPageContentsLoad");
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("PreviewPage.do", parameterMap,
					null, "View"));
		} else {
			dt.setEditLink(buildHyperLink("ManagePage.do", parameterMap,
					null, "Edit"));
		}
        }catch (Exception ex) {
			logger.fatal("Error in makePageLibraryLink(waTemplateDT in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public PageManagementProxyVO publishPage( WaTemplateDT waTemplateDT, HttpSession session) throws Exception{
		
		MainSessionCommand msCommand = null;
		PageManagementProxyVO pmProxyVO = new PageManagementProxyVO();
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "publishPage";
		Object[] oParams =new Object[] {waTemplateDT};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			pmProxyVO = (PageManagementProxyVO)aList.get(0);

		}
		//Update question lookup with updated published pages
		sMethod = "loadLookups";
		msCommand.processRequest(sBeanJndiName, sMethod, null);
	  }catch (Exception ex) {
			logger.fatal("Error in publishPage in Action Util: "+ex.getMessage(), ex);
			throw new Exception("Error occurred in Publish Page: "+ex.getMessage(), ex);
	  }
	  return pmProxyVO;
	}

	/**
	 * Retrieve a list of all question IDs present in the page management proxy VO.
	 * @param proxyVo
	 * @return
	 */
	private Collection<String> getQuestionIdsInPageData(PageManagementProxyVO proxyVo) throws Exception
	{
		try{
	    Collection<String> questionIds = new ArrayList<String>();
	    Collection<Object> pageElts = proxyVo.getThePageElementVOCollection();
        for (Object pe: pageElts) {
            questionIds.add(((PageElementVO)pe).getWaUiMetadataDT().getQuestionIdentifier());
        }
	    
	    return questionIds;
		}catch (Exception ex) {
			logger.fatal("Error in getQuestionIdsInPageData in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
	  }
	}
	
	public Long deleteDraft(HttpSession session, Long waTemplateUid)throws Exception{
	
		MainSessionCommand msCommand = null;

		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "deleteDraft";
			Object[] oParams =new Object[] {waTemplateUid};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
			waTemplateUid = (Long)aList.get(0);
			
			}catch (Exception ex) {
				logger.fatal("Error in deleting the draft- action util class "+ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
			}
		
			 return  waTemplateUid; 
			
	}
	
	public Long saveAsTemplate(Long waTemplateUid,String templNm, String desc, HttpSession session)throws Exception{
		
		MainSessionCommand msCommand = null;

		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "saveTemplate";
			Object[] oParams =new Object[] {waTemplateUid,templNm,desc};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
			waTemplateUid = (Long)aList.get(0);
			
			}catch (Exception ex) {
				logger.fatal("Error in creating the Template- action util class "+ ex.getMessage(), ex);
				throw new Exception(ex.getMessage(), ex);
			}
		
			 return  waTemplateUid; 
			
	}
	
	public String generateXMLandJSP(PageManagementProxyVO pageManagementProxyVO, String type) throws Exception{
		
    try{
		 MarshallPageXML marshallPage = new MarshallPageXML();
		 PagePublisher publisher = PagePublisher.getInstance();
		 String xmlwritedir=xml2jspDirectory + "pageOut.xml";
		 String destDir ="";
	         String returnStr = marshallPage.GeneratePageXMLFile(pageManagementProxyVO, xmlwritedir);	
	        // String returnStr="";
			if (returnStr.equals("Error writing XML file.")){
				logger.error("Error marshalling page to XML when saving in Page Builder");
				return("failure");
			}
			else{						
				  //String dir = pageManagementProxyVO.getWaTemplateDT().getBusObjType();
				 String dir = "PG";
				 if(type != null && type.equals("PAGE")){
				  if(pageManagementProxyVO.getWaTemplateDT().getFormCd() != null && !pageManagementProxyVO.getWaTemplateDT().getFormCd().equals(""))
					  dir = pageManagementProxyVO.getWaTemplateDT().getFormCd();
				  else 
					  // Though we are coming from the PAGE, this is a Template  , So we are giving PG_template_uid
					  dir = dir + "_" + pageManagementProxyVO.getWaTemplateDT().getWaTemplateUid();
				 }else if(type != null && type.equals("TEMP"))
				 {
					 dir = dir + "_" + pageManagementProxyVO.getWaTemplateDT().getWaTemplateUid();
				 }
	
				    //if(pageManagementProxyVO.getWaTemplateDT().getBusObjType() != null)
				    //	busObjType = pageManagementProxyVO.getWaTemplateDT().getBusObjType();
	   				GenerateJSPTabs genTabs = new GenerateJSPTabs();
	   				String indexfile=xml2jspDirectory + "DMB_Generate_Index_JSP.xsl";
	   				String indexviewfile=xml2jspDirectory + "DMB_Generate_PreView_Index_JSP.xsl";
	   				String tabfile=xml2jspDirectory + "DMB_Generate_Tab_JSP.xsl";   				
	   				String tabviewfile=xml2jspDirectory + "DMB_Generate_Tab_JSP.xsl";
   				
	   				//for preview - everything is PageForm (no contact or interview)
	   				String busObjType = NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE;
	   				if (pageManagementProxyVO.getWaTemplateDT().getBusObjType() != null)
	   					busObjType = pageManagementProxyVO.getWaTemplateDT().getBusObjType(); //could be Contact or Interview
   				//start creating the jsp's from  xml
   				try{
   				    genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexfile,NEDSSConstants.CREATE,dir,NEDSSConstants.PREVIEW,busObjType ) ;	
 	   				genTabs.processTabs(xmlwritedir, "/util:PageInfo/Page/PageTab", tabfile,NEDSSConstants.CREATE,dir,NEDSSConstants.PREVIEW, busObjType ) ;
 	   				
 	   				genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexviewfile,NEDSSConstants.VIEW,dir,NEDSSConstants.PREVIEW,busObjType) ;		
 	   				genTabs.processTabs(xmlwritedir, "/util:PageInfo/Page/PageTab", tabviewfile,NEDSSConstants.VIEW,dir,NEDSSConstants.PREVIEW, busObjType ) ;
 	   				
   				} catch (Exception ex) {   					
   					logger.error("Exception in generating XML and JSP: " + ex.getMessage(), ex);
   					return("failure");
   				}
			
			}
		
			return(returnStr);
    }catch(Exception ex){
    	logger.fatal("Error in creating the generateXMLandJSP action util class "+ex.getMessage(), ex);
    	ex.printStackTrace();
		throw new Exception(ex.getMessage(), ex);
	}
	}
	
	/**
	 * Publishes the PageBuilder built JSP pages to NBS TMP war Deployment folder structure
	 */
	public String previewPagedir(PageManagementProxyVO pageManagementProxyVO) throws IOException, InterruptedException, Exception {
		
		boolean success = false;
		//String dir = pageManagementProxyVO.getWaTemplateDT().getBusObjType();
		String dir = "";
		File destDir = new File("");
		try{
		//if(pageManagementProxyVO.getWaTemplateDT().getConditionCd() != null && !pageManagementProxyVO.getWaTemplateDT().getConditionCd().equals(""))
		//	dir = dir + "_" + pageManagementProxyVO.getWaTemplateDT().getConditionCd();
		if(pageManagementProxyVO.getWaTemplateDT().getFormCd() != null && !pageManagementProxyVO.getWaTemplateDT().getFormCd().equals(""))
			dir = pageManagementProxyVO.getWaTemplateDT().getFormCd();
				
		class NbsFolderFilter implements FilenameFilter {
		    public boolean accept(File dir, String name) {
		        return (name.contains(NBS_TMP_DEPLOYMENT_CONTAINS));
		    }
		}
		
		class NbsFolderWARFilter implements FilenameFilter {
		    public boolean accept(File dir, String name) {
		        return (name.contains(NBS_TMP_WAR_NAME_CONTAINS));
		    }
		}
		
		String nbsTmpDeploy = "";
		//Retrieve the tmp deploy location and get hold of tmpxxxxnbs.ear-contents
		String tmpDir = System.getProperty("jboss.server.temp.dir");
		logger.debug("tmpDir -------------------------------------------------- "+tmpDir);
		File nbsDirectoryPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator);
		if(nbsDirectoryPath.exists()) {
			long lastMod = Long.MIN_VALUE;
			FilenameFilter filter = new NbsFolderFilter();
			File [] folders = nbsDirectoryPath.listFiles(filter);
			for (int i = 0; i < folders.length;) {
				File folder = folders[i++];
				if(folder != null && folder.getName().contains(NBS_TMP_DEPLOYMENT_CONTAINS) && (folder.lastModified() > lastMod)) {
					nbsTmpDeploy = folder.getName();
					lastMod = folder.lastModified();
					logger.debug("writeTabFileJSP() --- nbsTmpDeploy-----------------------------------"+nbsTmpDeploy);
					//break;
				}
			}
			
			File nbsDirectoryForWARPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator + nbsTmpDeploy + File.separator);
			FilenameFilter warFilter = new NbsFolderWARFilter();
			String [] warFolders = nbsDirectoryForWARPath.list(warFilter);
			String nbsTmpExpWAR="";
			for (int i = 0; i < warFolders.length;) {
				String folder = warFolders[i++];
				if(folder != null && folder.contains(NBS_TMP_WAR_NAME_CONTAINS)) {
					nbsTmpExpWAR = folder;
					logger.debug("nbsTmpExpWAR----------------------------"+nbsTmpExpWAR);
					break;
				}
			}
			
			//Once the tmpEar Folder Name is found, explore to "nbs-exp.war" and to pageManagement eventually
			if(nbsTmpDeploy.length() > 0) {
				nbsDirectoryPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator + nbsTmpDeploy + File.separator + nbsTmpExpWAR);
				// Destination Dir
				destDir = new File(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator + dir + File.separator); 	
				if(!destDir.exists())
					destDir.mkdir();				
				
				
			}
			
			
		}
		}catch(Exception ex){
			logger.fatal("Error in previewPagedir from action Util: "+ex.getMessage(), ex);
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex);
		}
		return destDir.toString();
	}
	
	public ArrayList<Object> getType(Collection<Object>  wqQuestionDtColl) throws Exception{
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (wqQuestionDtColl != null) {
			java.util.Iterator<?> iter = wqQuestionDtColl.iterator();
			while (iter.hasNext()) {
				WaQuestionDT dt = (WaQuestionDT) iter.next();
				if (dt.getQuestionType()!= null) {
					typeMap.put(dt.getQuestionType(), dt.getQuestionType());
							
				}
				if(dt.getQuestionType() == null || dt.getQuestionType().trim().equals("")){
					typeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	
	public ArrayList<Object> getGroup(Collection<Object>  wqQuestionDtColl) throws Exception {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (wqQuestionDtColl != null) {
			java.util.Iterator<?> iter = wqQuestionDtColl.iterator();
			while (iter.hasNext()) {
				WaQuestionDT dt = (WaQuestionDT) iter.next();
				if (dt.getGroupDesc()!= null) {
					typeMap.put(dt.getGroupDesc(), dt.getGroupDesc());
							
				}
				if(dt.getGroupDesc() == null || dt.getGroupDesc().trim().equals("")){
					typeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	
	public ArrayList<Object> getSubGroup(Collection<Object>  wqQuestionDtColl) {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (wqQuestionDtColl != null) {
			java.util.Iterator<?> iter = wqQuestionDtColl.iterator();
			while (iter.hasNext()) {
				WaQuestionDT dt = (WaQuestionDT) iter.next();
				if (dt.getSubGroupDesc()!= null) {
					typeMap.put(dt.getSubGroupDesc(), dt.getSubGroupDesc());
							
				}
				if(dt.getSubGroupDesc() == null || dt.getSubGroupDesc().trim().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	
	public ArrayList<Object> getStatus(Collection<Object>  wqQuestionDtColl) {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (wqQuestionDtColl != null) {
			java.util.Iterator<?> iter = wqQuestionDtColl.iterator();
			while (iter.hasNext()) {
				WaQuestionDT dt = (WaQuestionDT) iter.next();
				if (dt.getRecordStatusCd()!= null) {
					typeMap.put(dt.getRecordStatusCd(), dt.getRecordStatusCd());
							
				}
				if(dt.getRecordStatusCd() == null || dt.getRecordStatusCd().trim().equals("")){
					typeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	public Long createNewDraft(Long waTemplateUid, HttpSession session)throws Exception{
		
		MainSessionCommand msCommand = null;
		Long newWaTemplateUid = null;

		try{
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "savePublishedPageAsDraft";
			Object[] oParams =new Object[] {waTemplateUid};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			
			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
			newWaTemplateUid = (Long)aList.get(0);
			
			}catch (Exception ex) {
				logger.fatal("Error in creating the new Draft from published version- action util class "+ex.getMessage(), ex);
				ex.printStackTrace();
				throw new Exception(ex.getMessage(), ex);
			}
		
			 return  newWaTemplateUid; 
			
	}
	
	
	public ArrayList<Object> getEventType(Collection<Object>  WaTemplateDTColl) {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (WaTemplateDTColl != null) {
			java.util.Iterator<?> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getBusObjType()!= null) {
					typeMap.put(dt.getBusObjType(), dt.getBusObjType());
							
				}				
				if(dt.getBusObjType() == null || dt.getBusObjType().equals("")){
					typeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	
	public ArrayList<Object> getLastUpdated(Collection<Object>  WaTemplateDTColl) {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (WaTemplateDTColl != null) {
			java.util.Iterator<?> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getLastChgTime()!= null) {
					//typeMap.put(dt.getLastChgTime().toString(), dt.getLastChgTime().toString());
							
				}
				if(dt.getLastChgTime() == null || dt.getLastChgTime().equals("")){
					typeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}

	public ArrayList<Object> getLastUpdatedBy(Collection<Object>  WaTemplateDTColl)throws Exception {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (WaTemplateDTColl != null) {
			java.util.Iterator<?> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getFirstLastName()!= null) {
					typeMap.put(dt.getFirstLastName(), dt.getFirstLastName());
							
				}
				if(dt.getFirstLastName() == null || dt.getFirstLastName().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	
	public ArrayList<Object> getPageStatus(Collection<Object>  WaTemplateDTColl)throws Exception{
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (WaTemplateDTColl != null) {
			java.util.Iterator<?> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getTemplateType()!= null) {
					typeMap.put(dt.getTemplateType(), dt.getTemplateType());
							
				}
				if(dt.getTemplateType() == null || dt.getTemplateType().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}	
	
	private WaTemplateDT getWaTemplateByTemplateNm(String templateNm, String templateType){
		WaTemplateDT waTemplateDT = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			waTemplateDT = dao.findWaTemplateByTemplateNm(templateNm,templateType);
		}catch(Exception e){
			logger.fatal("Error in accessing the WA Template from DAO"+e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return waTemplateDT;	
		
	}
	
	/**
	 * getWaTemplateByDataMartName: returns a template with the same data mart name in case it exists
	 * @param dataMartNm
	 * @param templateType
	 * @return
	 */
	private WaTemplateDT getWaTemplateByDataMartName(String dataMartNm, String templateType){
		WaTemplateDT waTemplateDT = null;
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			waTemplateDT = dao.findWaTemplateByDataMartNm(dataMartNm,templateType);
		}catch(Exception e){
			logger.fatal("Error in accessing the WA Template from DAO"+e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return waTemplateDT;	
		
	}
	
	private ArrayList<Object> findPageByCondAndBO(String condCollStr, String boType){
		ArrayList<Object> pageCondColl = new ArrayList<Object>();
		try{
			PageManagementDAOImpl dao = new PageManagementDAOImpl();
			pageCondColl = dao.findPageByCondAndBO(condCollStr, boType);
		}catch(Exception e){
			logger.fatal("Error in accessing findPageCondByCond from DAO"+e.getMessage(), e);
			e.printStackTrace();
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return pageCondColl;
	}

	 public ArrayList<Object> validateTemplateNmUniqueness(String templateNm, String templateType) throws Exception{
		 try{
	     
	        ArrayList<Object> errorMsgs = new ArrayList<Object>();
	        
	        PageManagementActionUtil util = new PageManagementActionUtil();
	        WaTemplateDT waTemplateDT = null;
	        // validate question identifier
	        try{
	          waTemplateDT =  util.getWaTemplateByTemplateNm(templateNm, templateType);
	        }catch(Exception ex){
				logger.fatal("Error in validateTemplateNmUniqueness "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.getMessage(), ex);
			}
	        if(waTemplateDT != null && waTemplateDT.getTemplateNm() != null && waTemplateDT.getTemplateNm().equals(templateNm)) {
	            errorMsgs.add("A Template  with Template Name " + templateNm + 
	                    " already exists in the system");
	        }   
	      
	        return errorMsgs;
		 }catch(Exception ex){
			 logger.fatal("Error in accessing the validateTemplateNmUniqueness from DAO: " +ex.getMessage(), ex);
			 ex.printStackTrace();
			throw new NEDSSSystemException(ex.getMessage(), ex);
		 }
	    }
	 
	 public ArrayList<Object> validatePageNmUniqueness(String pageNm, Long pageUid, ArrayList<Object> errorMsgs, String templateType) throws Exception{
		 try{
	        
	        PageManagementActionUtil util = new PageManagementActionUtil();
	        WaTemplateDT waTemplateDT = null;
	        // validate question identifier
	        try{
	          waTemplateDT =  util.getWaTemplateByTemplateNm(pageNm.trim(), templateType);
	        }catch(Exception ex){
				logger.fatal("Error in validateTemplateNmUniqueness "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.getMessage(), ex);
			}
	        if(waTemplateDT != null && waTemplateDT.getTemplateNm() != null 
	        		&& waTemplateDT.getTemplateNm().equalsIgnoreCase(pageNm)) {
	        	Long fetchedWaTemplateUid = waTemplateDT.getWaTemplateUid();
	        	if (!fetchedWaTemplateUid.equals(pageUid)) //not the same page 
	            errorMsgs.add("A Page with Page Name " + HTMLEncoder.encodeHtml(pageNm) + 
	                    " already exists in the system");
	        }   
	      
	        return errorMsgs;
		 }catch(Exception ex){
			 logger.fatal("Error in accessing the validatePageNmUniqueness from DAO"+ex.getMessage(), ex);
			 ex.printStackTrace();
			throw new NEDSSSystemException(ex.getMessage(), ex);
		 }
	    }
	 
	 /**
	  * validateDataMartNmUniqueness: validate there's no other data mart name for any other page in the system
	  * @param pageNm
	  * @param pageUid
	  * @param templateType
	  * @return
	  * @throws Exception
	  */
	 public ArrayList<Object> validateDataMartNmUniqueness(String dataMartNm, Long pageUid, ArrayList<Object> errorMsgs, String templateType, String templateNm) throws Exception{
		 try{
			
	        PageManagementActionUtil util = new PageManagementActionUtil();
	        WaTemplateDT waTemplateDT = null;
	        // validate question identifier
	        try{
	          waTemplateDT =  util.getWaTemplateByDataMartName(dataMartNm.trim(), templateType);
	        }catch(Exception ex){
				logger.fatal("Error in validateDataMartNmUniqueness "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.getMessage(), ex);
			}
	        if(waTemplateDT != null && waTemplateDT.getDataMartNm() != null 
	        		&& waTemplateDT.getDataMartNm().equalsIgnoreCase(dataMartNm)) {
	        	Long fetchedWaTemplateUid = waTemplateDT.getWaTemplateUid();
	        	String templateName = waTemplateDT.getTemplateNm();
	        	
	        	if (!fetchedWaTemplateUid.equals(pageUid) && !templateNm.equalsIgnoreCase(templateName)) //not the same page 
	            errorMsgs.add("A Page with Data Mart Name " + HTMLEncoder.encodeHtml(dataMartNm) + 
	                    " already exists in the system");
	        }   
	      
	        return errorMsgs;
		 }catch(Exception ex){
			 logger.fatal("Error in accessing the validateDataMartNmUniqueness from DAO"+ex.getMessage(), ex);
			 ex.printStackTrace();
			throw new NEDSSSystemException(ex.getMessage(), ex);
		 }
	    }
	 
	 public ArrayList<Object>  validateCondition(String[]  condArr, ArrayList<Object> errorMsgs, String boType){
		 String condCollStr = "(";
		 for (int i=0; i<condArr.length; i++) {
 			String  result = condArr[i] ; 			
 			if (result != null && !result.isEmpty()){
 				condCollStr = condCollStr+"'"+result+"',";
 				
 			}
 			
		 }
		 //Removing the last ","
		 int strLen = condCollStr.length(); 
		 condCollStr = condCollStr.substring(0, strLen-1);
		 condCollStr = condCollStr+")";
		 String condStr = null;
		 try{
			 ArrayList<Object> pageCondColl = this.findPageByCondAndBO(condCollStr, boType);
			 //setting error message with the conditions in the right format
			 if(pageCondColl != null){
				 Iterator iter = pageCondColl.iterator();
				 while(iter.hasNext()){
					 PageCondMappingDT pageCondMapDT = (PageCondMappingDT)iter.next();
					 condStr = pageCondMapDT.getConditionDesc()+",";
				 }
				 
			 }
			 String err = null;
			 if(condStr != null){
				 int condLen = condStr.length();
				 condStr = condStr.substring(0, condLen-1);	
				 err = "The following condition(s) have already been mapped to a page: " +HTMLEncoder.encodeHtml(condStr)+". Please remove these conditions from the Related Conditions listbox and try again.";
				 errorMsgs.add(err);
			 }
			
				 
		 }catch(Exception e ){
			 logger.fatal("Error in accessing the validateCondition: "+e.getMessage(), e);
			 e.printStackTrace();
			 throw new NEDSSSystemException(e.getMessage(), e);
		 }	
		 return errorMsgs;
	 }

	
	/**
	 * Following methods are helping page library sorting.
	 * filterLastUpdate, filterLastUpdateBy, filterStatus and filterPorting
	 */
	
	public Collection<Object>  filterLastUpdate(Collection<Object> waTemplateDtColl, 
			Map<Object, Object> dateMap) throws Exception {	
		try{
		Map<Object, Object>  sortedByDateMap = new HashMap<Object,Object>();
		String strDateKey = null;
		if (waTemplateDtColl != null) {
			Iterator<Object>  iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				
				if (dt.getLastChgTime() != null && dateMap != null
						&& (dateMap.size()>0 )) {
					Collection<Object>  dateSet = dateMap.keySet();
					if(dateSet != null){
						Iterator<Object>  iSet = dateSet.iterator();
					while (iSet.hasNext()){
						 strDateKey = (String)iSet.next();
						if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
                    	   if(queueUtil.isDateinRange(dt.getLastChgTime(),strDateKey)){
                    		   sortedByDateMap.put(dt.getWaTemplateUid(), dt);
                    	   }	
                           		
						}  
                       }
					  }
					}
		
				if(dt.getLastChgTime() == null || dt.getLastChgTime().equals("")){
					if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						sortedByDateMap.put(dt.getWaTemplateUid(), dt);
					}
				}

			}
		} 	
	        try{
		return convertInvMaptoColl(sortedByDateMap);
	        }catch(Exception ex){
				 logger.fatal("Error in accessing the convertInvMaptoColl"+ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.getMessage(), ex);
			 }
	 }catch(Exception ex){
		 logger.fatal("Error in accessing the convertInvMaptoColl"+ex.getMessage(), ex);
		 throw new NEDSSSystemException(ex.getMessage(), ex);
	}
	}
	
	   private Collection<Object>  convertInvMaptoColl(Map<Object, Object>  dtMap)throws Exception{
		   Collection<Object>  dtColl = new ArrayList<Object> ();
		   try{
		   if(dtMap !=null && dtMap.size()>0){
			   Collection<Object>  dtKeyColl = dtMap.keySet();
			  Iterator<Object>  iter = dtKeyColl.iterator();
			   while(iter.hasNext()){
				   Long dtUidKey = (Long)iter.next();
				   dtColl.add(dtMap.get(dtUidKey));
			   }
		   }
		   }catch(Exception ex){
				 logger.fatal("Error in accessing the convertInvMaptoColl"+ex.getMessage(), ex);
				 throw new NEDSSSystemException(ex.getMessage(), ex);
		   }
		   return dtColl;
	   }
	
	public Collection<Object>  filterLastUpdateBy(
			Collection<Object>  waTemplateDtColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waTemplateDtColl != null) {
			Iterator<Object> iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getFirstLastName()!= null
						&& typeMap != null
						&& typeMap.containsKey(dt.getFirstLastName())) {
					newTypeColl.add(dt);
				}
				if(dt.getFirstLastName() == null || dt.getFirstLastName().equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.fatal("Error in accessing the filterLastUpdateBy : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterStatus(
			Collection<Object>  waTemplateDtColl, Map<Object,Object> typeMap)throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waTemplateDtColl != null) {
			Iterator<Object> iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getTemplateType()!= null
						&& typeMap != null
						&& typeMap.containsKey(dt.getTemplateType())) {
					newTypeColl.add(dt);
				}
				if(dt.getTemplateType() == null || dt.getTemplateType().equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.fatal("Error in accessing the filterStatus : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterBusObjType(
			Collection<Object>  waTemplateDtColl, Map<Object,Object> typeMap)throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waTemplateDtColl != null) {
			Iterator<Object> iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getBusObjType()!= null
						&& typeMap != null
						&& typeMap.containsKey(dt.getBusObjType())) {
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.fatal("Error in accessing the filterStatus : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterPorting(
			Collection<Object>  waTemplateDtColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waTemplateDtColl != null) {
			Iterator<Object> iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				String portReqIndCd = "Yes".equalsIgnoreCase(dt.getPortReqIndCd())?"T":"F";
				if (portReqIndCd!= null
						&& typeMap != null
						&& typeMap.containsKey(portReqIndCd)) {
					newTypeColl.add(dt);
				}
				if(portReqIndCd == null || portReqIndCd.equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.fatal("Error in accessing the filterPorting : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterByText(
			Collection<Object>  waTemplateDtColl, String filterByText,String column) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waTemplateDtColl != null) {
			Iterator<Object> iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if(column.equals(NEDSSConstants.TEMPLATE_NM) && dt.getTemplateNm() != null && dt.getTemplateNm().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.RELATED_CONDITIONS) && dt.getRelatedConditionsForPrint()!= null && dt.getRelatedConditionsForPrint().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterByText : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newTypeColl;
	}
	
public Collection<Object> getPageHistory(String pageNm,HttpSession session) throws Exception{
		
		MainSessionCommand msCommand = null;
		Collection<Object> pagehistList = new ArrayList<Object>();
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getPageHistory";
		Object[] oParams =new Object[]{pageNm};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			pagehistList = (Collection<Object>)aList.get(0);

		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getting the page history in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
	  return pagehistList;
	}
	
public Collection<Object> getJspFiles(String formCd) throws Exception{	
	
	Collection<Object> aList = new ArrayList<Object>();
try{
	PageManagementDAOImpl dao = new PageManagementDAOImpl();
	aList = dao.retrieveJspFiles(formCd);
	
	
  }catch (Exception ex) {
		logger.fatal("Error in getting the page Summary in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
  }
  return aList;
}

public Collection<Object> getInvestigationFormCodeonServerStartup(){
	WaQuestionDT waQuestionDT = null;
	Collection<Object> frmList = new ArrayList<Object>();
	try{
		if (getPageManagementEJBRef() != null){
		PageManagementDAOImpl dao = new PageManagementDAOImpl();
		//frmList = dao.getInvestigationFormCodeonServerStartup();
		frmList = getPageManagementEJBRef().getInvestigationFormCodeonServerStartup();
		}
	}catch(Exception e){
		logger.fatal("Error in accessing the WA Question from DAO"+e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
	return frmList;	
	
	}

public static PageManagementProxy getPageManagementEJBRef() throws Exception {
	if (pMap == null) {
		NedssUtils nu = new NedssUtils();
		Object pageref = nu.lookupBean(JNDINames.PAGE_MANAGEMENT_PROXY_EJB);
		if (pageref != null) {
			PageManagementProxyHome home = (PageManagementProxyHome) PortableRemoteObject
			.narrow(pageref, PageManagementProxyHome.class);
			pMap = home.create();
		}
	}
	return pMap;
}

public String fetchPublishedMessageGuide(String conditionCd){
	Collection<Object> aList = new ArrayList<Object>();
	String messageGuide = "";
	WaTemplateDT dt = new WaTemplateDT();
	try{
		PageManagementDAOImpl dao = new PageManagementDAOImpl();
		aList = dao.fetchPublishedMessageGuide(conditionCd);
		if(aList != null && aList.size()>0){
			Iterator<Object> iter = aList.iterator();
			while (iter.hasNext()) {
				 dt = (WaTemplateDT) iter.next();
			}
			if(dt.getNndEntityIdentifier()!= null)
				messageGuide = dt.getNndEntityIdentifier().toString();
			

		}	
		
		
	  }catch (Exception ex) {
			logger.fatal("Error in getting the page Summary in Action Util: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
	  }
	  return messageGuide;
	
	}


public TemplateExportType handleExportImportofXML(HttpSession session, String waTemplateUid,String templateNm){
	Map<Object,Object> aMap = new HashMap<Object,Object>();
	Map<Object,Object> uiMap = new HashMap<Object,Object>();
	Map<Object,Object> nndMap = new HashMap<Object,Object>();
	Map<Object,Object> rdbMap = new HashMap<Object,Object>();
	Map<Object,Object> cdGrpMetaMap = new HashMap<Object,Object>();
	Map<Object,Object> cdMap = new HashMap<Object,Object>();
	Map<Object,Object> cdValGen = new HashMap<Object,Object>();
	Map<Object,Object> qMap = new HashMap<Object,Object>();
	Map<Object,Object> ruleMap = new HashMap<Object,Object>();
	TemplateExportType retObj = null;
	EDXActivityLogDT dt = new EDXActivityLogDT();
	EDXActivityDetailLogDT ddt = new EDXActivityDetailLogDT();
	Collection<Object> eDXActivityLogDTDetails = new ArrayList<Object>();
	EDXActivityLogDAOImpl  edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
	
	
	String messageGuide = "";

	MainSessionCommand msCommand = null;
	try{
		TemplatesDAOImpl dao = new TemplatesDAOImpl();
		ArrayList<Object> IdList = new ArrayList<Object>();
		ArrayList<Object> CodeList = new ArrayList<Object>();
		Collection<Object> templateDTColl = new ArrayList<Object>();
		Map<Object,Object> codeSetNmColl = new HashMap<Object,Object>();
		ArrayList<Object> AdditonalCodeList = new ArrayList<Object>();
		
		templateDTColl = dao.fetchQIdentifiers4Template(waTemplateUid);		
		if (templateDTColl != null) {
			java.util.Iterator<Object> iter = templateDTColl.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if(((WaUiMetadataDT)obj) != null && (WaUiMetadataDT)obj instanceof WaUiMetadataDT){
				IdList.add(((WaUiMetadataDT)(obj)).getQuestionIdentifier());
				CodeList.add(((WaUiMetadataDT)(obj)).getCodeSetGroupId());
				}
				
			}
		}
		AdditonalCodeList = dao.fetchAdditonalCodeSets(waTemplateUid);	
		if(AdditonalCodeList != null && AdditonalCodeList.size()>0)
		   CodeList.addAll(AdditonalCodeList);
		
		AdditonalCodeList = dao.fetchAdditonalCodeSetsforStructNumQs(waTemplateUid);	
		if(AdditonalCodeList != null && AdditonalCodeList.size()>0)
		   CodeList.addAll(AdditonalCodeList);
		
		codeSetNmColl = dao.getCodeSetNmColl();
		
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "fetchInsertSql";
		Object[] oParams =new Object[] {"codeset_group_metadata", "code_set_nm", CodeList,waTemplateUid,templateNm,codeSetNmColl};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			cdGrpMetaMap = (Map<Object,Object>)aList.get(0);

		}	
		oParams =new Object[] {"codeset", "code_set_nm", CodeList,waTemplateUid,templateNm,codeSetNmColl};
		 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				cdMap = (Map<Object,Object>)aList.get(0);

			}	
		oParams =new Object[] {"code_value_general", "code_set_nm", CodeList,waTemplateUid,templateNm,codeSetNmColl};
			 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				if(aList != null && aList.size()>0){
					cdValGen = (Map<Object,Object>)aList.get(0);

				}		
		
		//cdGrpMetaMap = dao.fetchInsertSql("codeset_group_metadata", "code_set_nm", CodeList,waTemplateUid);
		//cdMap = dao.fetchInsertSql("codeset", "code_set_nm", CodeList,waTemplateUid);
		//cdValGen = dao.fetchInsertSql("code_value_general", "code_set_nm", CodeList,waTemplateUid);
		oParams =new Object[] {"wa_question", "question_identifier", IdList,waTemplateUid,templateNm,codeSetNmColl};
		 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				qMap = (Map<Object,Object>)aList.get(0);

			}		
		
		oParams =new Object[] {"wa_template", "template_nm", IdList,waTemplateUid,templateNm,codeSetNmColl};
		 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				aMap = (Map<Object,Object>)aList.get(0);

			}	
		oParams =new Object[] {"wa_ui_metadata", "question_identifier", IdList,waTemplateUid,templateNm,codeSetNmColl};
			 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				if(aList != null && aList.size()>0){
					uiMap = (Map<Object,Object>)aList.get(0);

				}	
		
		oParams =new Object[] {"wa_nnd_metadata", "question_identifier", IdList,waTemplateUid,templateNm,codeSetNmColl};
		 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				nndMap = (Map<Object,Object>)aList.get(0);

			}	
		oParams =new Object[] {"wa_rdb_metadata", "question_identifier", IdList,waTemplateUid,templateNm,codeSetNmColl};
			 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				if(aList != null && aList.size()>0){
					rdbMap = (Map<Object,Object>)aList.get(0);

				}
				
		oParams =new Object[] {"wa_rule_metadata", "wa_rule_metadata_uid", IdList,waTemplateUid,templateNm,codeSetNmColl};
		 aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(aList != null && aList.size()>0){
				ruleMap = (Map<Object,Object>)aList.get(0);

			}		
				
		
		 String fileToMake=xml2jspDirectory + "Export.xml";
		GenerateExportImportXML gen = new GenerateExportImportXML();
		retObj=	gen.GenerateExportImportXML(aMap,qMap,uiMap,nndMap,rdbMap,cdGrpMetaMap,cdMap,cdValGen,ruleMap, fileToMake);
		  dt.setSourceUid(null);
	   	  dt.setTargetUid(new Long(waTemplateUid));
	   	  dt.setDocType("Template");
	   	  dt.setDocName(templateNm);
	   	  dt.setRecordStatusCd("Success");
	   	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	   	  dt.setException(null);
	   	  dt.setImpExpIndCd("E");
	   	  dt.setSourceTypeCd(null);
	   	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
	   	  dt.setTargetTypeCd("Template");
	   	  dt.setBusinessObjLocalId(null);	
	   	 // dt.setEDXActivityLogDTDetails(null);	
	   	  edxActivityLogDAOImpl.insertExportEDXActivityLog(dt);
		
		
	  }catch (Exception ex) {
		  dt.setSourceUid(null);
    	  dt.setTargetUid(new Long(waTemplateUid));
    	  dt.setDocType("Template");
    	  dt.setDocName(templateNm);
    	  dt.setRecordStatusCd("Failure");
    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
    	  dt.setException(ex.getMessage());
    	  dt.setImpExpIndCd("E");
    	  dt.setSourceTypeCd(null);
    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
    	  dt.setTargetTypeCd("Template");
    	  dt.setBusinessObjLocalId(null);	
    	  //dt.setEDXActivityLogDTDetails(null);	
    	  edxActivityLogDAOImpl.insertExportEDXActivityLog(dt);
			logger.fatal("Error in getting the page Summary in Action Util: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
	  }
	  return retObj;
	
	}

public ArrayList<Object> readImportXML(HttpSession session,String  filePath,byte[] fileData){
	
	ArrayList<Object> aList = new ArrayList<Object>();
	ArrayList<Object> vList = new ArrayList<Object>();
	EDXActivityLogDAOImpl  edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
	EDXActivityLogDT dt = new EDXActivityLogDT();
	boolean logged= false;
	Map<Object,Object> codeSetNmColl = new HashMap<Object,Object>();
	
	
	MainSessionCommand msCommand = null;
	ArrayList<Object> returnValue=new ArrayList<Object> ();
	String ret="";
	 String srcTemplateNm ="";
	 Long activityLogUid =null;
	try{		
				
	       
	        //Create file
		    File pagemanagementdir = new File(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator ) ;
		    if(!pagemanagementdir.exists()){
		    	pagemanagementdir.mkdir();
		    }
	        File fileToMake = new File(pagemanagementdir.getPath(), "Import.xml");
	        //If file does not exists create file                      
	
	        
	          if (!fileToMake.exists()) {
	        	  boolean isFileCreated = fileToMake.createNewFile();
			  }
	          
	          FileOutputStream fileOutStream = new FileOutputStream(fileToMake);
	          
	          fileOutStream.write(fileData);
	          fileOutStream.flush();
	          fileOutStream.close();

		

		 GenerateExportImportXML gen = new GenerateExportImportXML();
		 aList =gen.readExportImportXML( fileToMake,"");
		 Map<Object, Object> templateMap = (HashMap<Object,Object>)aList.get(1);
		 Iterator<?> itT = templateMap.entrySet().iterator();
		
		 while (itT.hasNext()) {
		        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itT.next();
		      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
			    if (pairs == null) {
			   		logger.warn("Marshaller: Unexpected null ? ");
		    		break;
			    }
			    srcTemplateNm = pairs.getKey().toString();
		 }
		 
		 vList =gen.readExportImportXML( fileToMake,"Vocab");
		  activityLogUid = edxActivityLogDAOImpl.insertActivityLogUid(srcTemplateNm);
		  
		 
		    String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "InsertImportSql";
			Object[] oParams =new Object[] {aList,vList,srcTemplateNm,activityLogUid,codeSetNmColl};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> retList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if(retList != null && retList.size()>0){
				
				ret = retList.get(0).toString();
			}	
		
			String metadataCorrectionMsg ="";
			try{ 
				
				//Correct Metadata after importing template
				
				logger.debug("Template import completed and correctingMetadata after template import.");
				
				new java.math.BigInteger(ret); //After successful import template return template uid, so it wont throw any error. 
											   //In case of error while importing template it will throw exception and buy pass metadata correction.
				boolean status = false;
				try{
		           	Object[] oParams1 = new Object[] {srcTemplateNm};
		 			String sMethod1 = "correctMetadataAfterTemplateImport";
		 			Object obj = CallProxyEJB.callProxyEJB(oParams1, sBeanJndiName, sMethod1, session);
		 			status = (boolean) obj;
				}catch(Exception ex){
					status = false;
	           		logger.error("Exception: "+ex.getMessage(), ex);
	           		//Don't throw exception.
	           	 }
				
	 			logger.debug("Corrected metadata after template import.");
	 			String logType ="Failure";
	 			String metadataCorrectionComment ="Failed to correct metadata after template import, error out while calling CorrectMetadataForTempImport stored procedure.";
	 			if(status){
	 				logType = "Success";
	 				metadataCorrectionComment = "Metadata corrected after template import";
	 			}
	 			
	 			EDXActivityLogDT edxActivityLogDT = prepareEdxActivityDetail(activityLogUid, srcTemplateNm, ret,"Template",logType, metadataCorrectionComment);
	 			
	 			String sBeanJndiName2 = JNDINames.PORT_PAGE_PROXY_EJB;
	 			Object[] oParams2 = new Object[] {edxActivityLogDT};
	 			String sMethod2 = "insertEDXActivityDetailsLog";
	 			CallProxyEJB.callProxyEJB(oParams2, sBeanJndiName2, sMethod2, session);
           	 }
             catch(NumberFormatException ex) {
            	 //Don't throw exception.
            	 logger.error("Exception: "+ex.getMessage(), ex);
           	 }catch(Exception ex){
           		 logger.error("Exception: "+ex.getMessage(), ex);
           		 //Don't throw exception.
           	 }
			
			
			
	  }catch (NedssAppLogException ex) {
		  edxActivityLogDAOImpl.insertEDXActivityLog(ex.getEDXActivityLogDT());   
		  logged= true;
			logger.fatal("Error in getting the page Summary in Action Util: "+ ex.getMessage(), ex);
			ret="error during import";
	  }	
	catch (Exception ex) {
		    if(!logged && !srcTemplateNm.equals("")){
		    	 StringWriter writerStr = new StringWriter();
		         PrintWriter myPrinter = new PrintWriter(writerStr);
		         
		    	 ex.printStackTrace(myPrinter);
                 String stackTraceStr = writerStr.toString();
                 
		    	  dt.setEdxActivityLogUid(null);
		    	  dt.setSourceUid(null);
		    	  dt.setTargetUid(null);
		    	  dt.setDocType("Template");
		    	  dt.setDocName(srcTemplateNm);
		    	  dt.setRecordStatusCd("Failure");
		    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		    	  dt.setException("Import of template "+srcTemplateNm +" failed. Exception occured is "+stackTraceStr);
		    	 
		    	  dt.setImpExpIndCd("I");
		    	  dt.setSourceTypeCd(null);
		    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
		    	  dt.setTargetTypeCd("Template");
		    	  dt.setBusinessObjLocalId(null);	
		    	  ret="error during import";
		    }
		    else if(!logged && srcTemplateNm.equals("")){		    		
		    	  ret="filenotfound";
		    }
			logger.fatal("Error in getting the readImportXML in Action Util: "+ex.getMessage(), ex);
			ex.printStackTrace();
			
	  }  
	
	  returnValue.add(ret);
	  returnValue.add(srcTemplateNm);
	  
	  return returnValue;
	
	}

public static ArrayList<Object> getTemplateCollection(HttpSession session)
throws Exception {

	ArrayList<Object> templateLibList = null;
	MainSessionCommand msCommand = null;
	ArrayList<?> arr = null;
try {
	String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
	String sMethod = "getTemplateLib";
	// Object[] oParams = new Object[] {dt};
	MainSessionHolder holder = new MainSessionHolder();
	msCommand = holder.getMainSessionCommand(session);
	arr = msCommand.processRequest(sBeanJndiName, sMethod,null);
	templateLibList = (ArrayList<Object> ) arr.get(0);
	} catch (Exception ex) {
		logger.fatal("Error in getTemplateLib in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
	return templateLibList;
}


	
public ArrayList<Object> getLastUpdatedDropDowns(Collection<Object>  templateDTColl) throws Exception {
	try{
	Map<Object, Object> lastUpdatedMap = new HashMap<Object,Object>();
	if (templateDTColl != null) {
		java.util.Iterator<Object> iter = templateDTColl.iterator();
		while (iter.hasNext()) {
			WaTemplateDT dt = (WaTemplateDT) iter.next();
			if (dt.getLastChgTime()!= null) {
				lastUpdatedMap.put(dt.getLastChgTime(), dt.getLastChgTime());
						
			}
			if(dt.getLastChgTime() == null || dt.getLastChgTime().equals("")){
				lastUpdatedMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
			}
		}
	}
	return queueUtil.getUniqueValueFromMap(lastUpdatedMap);
	}catch (Exception ex) {
		logger.fatal("Error in getLastUpdatedDowns in Action Util: "+ex.getMessage(), ex);
		ex.printStackTrace();
		throw new Exception(ex.getMessage(), ex);
	
	}
}

public ArrayList<Object> getLastUpdatedByDropDowns(Collection<Object>  templateDTColl) throws Exception {
	try{
	NBSAuthHelper helper = new NBSAuthHelper();
	Map<Object, Object> lastUpdatedByMap = new HashMap<Object,Object>();
	if (templateDTColl != null) {
		java.util.Iterator<Object> iter = templateDTColl.iterator();
		while (iter.hasNext()) {
			WaTemplateDT dt = (WaTemplateDT) iter.next();
			if (dt.getLastChgUserId()!= null) {
				lastUpdatedByMap.put(helper.getUserName(dt.getLastChgUserId()), helper.getUserName(dt.getLastChgUserId()));
						
			}
			if(dt.getLastChgUserId() == null || dt.getLastChgUserId().equals("")){
				lastUpdatedByMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
			}
		}
	}
	return queueUtil.getUniqueValueFromMap(lastUpdatedByMap);
	}catch (Exception ex) {
		logger.fatal("Error in getLastUpdatedDowns in Action Util: "+ex.getMessage(), ex);
		ex.printStackTrace();
		throw new Exception(ex.getMessage(), ex);
	
	}
}
public ArrayList<Object> getSourceDropDowns(Collection<Object>  templateDTColl) throws Exception {
	try{
	Map<Object, Object> sourceMap = new HashMap<Object,Object>();
	if (templateDTColl != null) {
		java.util.Iterator<Object> iter = templateDTColl.iterator();
		while (iter.hasNext()) {
			WaTemplateDT dt = (WaTemplateDT) iter.next();
			if (dt.getSourceNm()!= null) {
				//sourceMap.put(dt.getLastChgUserId(), dt.getLastChgUserId());
				sourceMap.put(dt.getSourceNm(), dt.getSourceNm());
			}
			if(dt.getSourceNm() == null || dt.getSourceNm().trim().equals("")){
				sourceMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
			}
		}
	}
	return queueUtil.getUniqueValueFromMap(sourceMap);
	}catch (Exception ex) {
		logger.fatal("Error in getLastUpdatedDowns in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
}

public ArrayList<Object> getRecordStatusDropDowns(Collection<Object>  templateDTColl) throws Exception {
	try{
	Map<Object, Object> recordStatusCdMap = new HashMap<Object,Object>();
	if (templateDTColl != null) {
		java.util.Iterator<Object> iter = templateDTColl.iterator();
		while (iter.hasNext()) {
			WaTemplateDT dt = (WaTemplateDT) iter.next();
			if (dt.getRecStatusCd()!= null) {
				recordStatusCdMap.put(dt.getRecStatusCd(), dt.getRecStatusCd());
						
			}
			if(dt.getRecStatusCd() == null || dt.getRecStatusCd().trim().equals("")){
				recordStatusCdMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
			}
		}
	}
	return queueUtil.getUniqueValueFromMap(recordStatusCdMap);
	}catch (Exception ex) {
		logger.fatal("Error in getLastUpdatedDowns in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
}

public static void updateTemplateLinks(ArrayList<Object>  TemplatesLibraryList,
		HttpServletRequest request) throws Exception{
	try{
	if (TemplatesLibraryList != null && TemplatesLibraryList.size() != 0) {
		for (int i = 0; i < TemplatesLibraryList.size(); i++) {
			WaTemplateDT dt = (WaTemplateDT) TemplatesLibraryList.get(i);
			
			HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
			String templateUid= null;
			templateUid = dt.getWaTemplateUid().toString();
			parameterMap.put("templateUid", templateUid);
			
			String viewUrl = "<a href=\"javascript:createLink('/nbs/PreviewTemplate.do?method=viewTemplate&templateUid="+ templateUid +"')\"><img src=\"page_white_text.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View\" alt=\"View\"/></a>";				
			dt.setViewLink(viewUrl);
			
			String editUrl = "<a href=\"javascript:EditTemplate('" + templateUid + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
			dt.setEditLink(editUrl);
		}

	}
	}catch (Exception ex) {
		logger.fatal("Error in updateTemplateLinks in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	}
}


public static String getSortCriteriaForTemplate(String sortOrder, String methodName) throws Exception{
	try{
	String sortOrdrStr = null;
	if(methodName != null) {
		if(methodName.equals("getLastChgTime"))
			sortOrdrStr = "Last Updated";
		else if(methodName.equals("getLastChgUserNm"))
			sortOrdrStr = "Last Updated By";
		else if(methodName.equals("getSourceNm"))
			sortOrdrStr = "Source";
		else if(methodName.equals("getRecStatusCd"))				
			sortOrdrStr = "Status";	
		else if(methodName.equals("getTemplateNm"))
			sortOrdrStr = "Template Name";
		else if(methodName.equals("getDescTxt"))
			sortOrdrStr = "Template Description";
	} else {
			sortOrdrStr = "Template Name";
	}
	
	if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
		sortOrdrStr = sortOrdrStr+" in ascending order ";
	else if(sortOrder != null && sortOrder.equals("2"))
		sortOrdrStr = sortOrdrStr+" in descending order ";

	return sortOrdrStr;
	}catch (Exception ex) {
		logger.fatal("Error in getSortCriteriaForTemplate in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
		
}

public static ArrayList<Object> getActivityLogCollection(HttpSession session)
throws Exception {

	ArrayList<Object> activityLogList = null;
	MainSessionCommand msCommand = null;
	ArrayList<?> arr = null;
try {
	String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
	String sMethod = "getActivityLogCollection";
	// Object[] oParams = new Object[] {dt};
	MainSessionHolder holder = new MainSessionHolder();
	msCommand = holder.getMainSessionCommand(session);
	arr = msCommand.processRequest(sBeanJndiName, sMethod,null);
	activityLogList = (ArrayList<Object> ) arr.get(0);
	} catch (Exception ex) {
		logger.fatal("Error in getActivityLogCollection in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
	return activityLogList;
}

public ArrayList<Object> getLogTypeDropDowns(Collection<Object>  activityLogColl) throws Exception {
	try{
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (activityLogColl != null) {
			java.util.Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
				if (edxActivityLogDT.getImpExpIndCd()!= null) {
					if(edxActivityLogDT.getImpExpIndCd().equals(NEDSSConstants.IMPORT_CD))
						edxActivityLogDT.setImpExpIndCdDesc(NEDSSConstants.IMPORT_DESC);
					else if (edxActivityLogDT.getImpExpIndCd().equals(NEDSSConstants.EXPORT_CD))
						edxActivityLogDT.setImpExpIndCdDesc(NEDSSConstants.EXPORT_DESC);
					
					typeMap.put(edxActivityLogDT.getImpExpIndCd(),edxActivityLogDT.getImpExpIndCdDesc());
				}
				if(edxActivityLogDT.getImpExpIndCd() == null || edxActivityLogDT.getImpExpIndCd().trim().equals("")){
					typeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
	}
	return queueUtil.getUniqueValueFromMap(typeMap);
	}catch (Exception ex) {
		logger.fatal("Error in getLogTypeDropDowns in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
}
	

public ArrayList<Object> getLogTemplateNmDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> templateNmMap = new HashMap<Object,Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
					if (edxActivityLogDT.getDocName()!= null) {
						
						
						templateNmMap.put(edxActivityLogDT.getDocName(),edxActivityLogDT.getDocName());
					}
					if(edxActivityLogDT.getDocName() == null || edxActivityLogDT.getDocName().trim().equals("")){
						templateNmMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
		}
		return queueUtil.getUniqueValueFromMap(templateNmMap);
		}catch (Exception ex) {
			logger.fatal("Error in getTemplateNmDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
}


public ArrayList<Object> getLogSourceNmDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> sourceNmMap = new HashMap<Object,Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
					if (edxActivityLogDT.getSrcName()!= null) {
						
						
						sourceNmMap.put(edxActivityLogDT.getSrcName(),edxActivityLogDT.getSrcName());
					}
					if(edxActivityLogDT.getSrcName() == null || edxActivityLogDT.getSrcName().trim().equals("")){
						sourceNmMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
		}
		return queueUtil.getUniqueValueFromMap(sourceNmMap);
		}catch (Exception ex) {
			logger.fatal("Error in getSourceNmDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
}


public ArrayList<Object> getLogStatusDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
					if (edxActivityLogDT.getRecordStatusCd()!= null) {
						
						
						statusMap.put(edxActivityLogDT.getRecordStatusCd(),edxActivityLogDT.getRecordStatusCd());
					}
					if(edxActivityLogDT.getRecordStatusCd() == null || edxActivityLogDT.getRecordStatusCd().trim().equals("")){
						statusMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
		}
		return queueUtil.getUniqueValueFromMap(statusMap);
		}catch (Exception ex) {
			logger.fatal("Error in getLogStatusDropDowns in Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
}


public static void updateImpExpLogLinks(ArrayList<Object>  impExpLogList,
		HttpServletRequest request) throws Exception{
	try{
		
	if (impExpLogList != null && impExpLogList.size() != 0) {
		for (int i = 0; i < impExpLogList.size(); i++) {
			String str ="";
			EDXActivityLogDT dt = (EDXActivityLogDT) impExpLogList.get(i);
			
			HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
			String edxActivityLogUid= null;
			edxActivityLogUid = dt.getEdxActivityLogUid().toString();
			parameterMap.put("edxActivityLogUid", edxActivityLogUid);
			String url = "/nbs/ManageImpExpLog.do?method=viewActivityLogDetails&edxActivityLogUid="+ edxActivityLogUid;
			String viewUrl = "";
			String recStatusTime = formatDate(dt.getRecordStatusTime(),"MM/dd/yyyy hh:mm:ss");
			if(dt.getImpExpIndCd() != null && dt.getImpExpIndCd().equals(NEDSSConstants.IMPORT_CD))
				viewUrl = "<a href=\"javascript:viewActitivityLogDetails('" +url + "');\"> "+recStatusTime+" </a>";
			else
				viewUrl = recStatusTime;
			//String viewUrl = "<a href='/nbs/ManageImpExpLog.do?method=viewActivityLogDetails&edxActivityLogUid="+ edxActivityLogUid +"'>"+dt.getDocName()+"</a>";				
			dt.setViewLink(viewUrl);
			if(dt.getException() != null && !dt.getException().equals("") && dt.getException().length()>251)
				dt.setExceptionShort(dt.getException().substring(0, 250));
			else if(dt.getException() != null && !dt.getException().equals("") && dt.getException().length()<251)
				dt.setExceptionShort(dt.getException());
			
			while(dt.getExceptionShort() != null && !dt.getExceptionShort().equals("") && dt.getExceptionShort().length()>79){
				 str = str + dt.getExceptionShort().substring(0, 79) + "<br>";
				 dt.setExceptionShort(dt.getExceptionShort().substring(80));
			  }
			 
			   if(str.length()>=80){
				   str = str + "\n" + dt.getExceptionShort();
				  dt.setExceptionShort(str);
			   }
			}

	}
	}catch (Exception ex) {
		logger.fatal("Error in updateImpExpLogLinks in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	}
}


public static String getSortCriteriaForImpExpLog(String sortOrder, String methodName) throws Exception{
	try{
	String sortOrdrStr = null;
	if(methodName != null) {
		if(methodName.equals("getRecordStatusTime"))
			sortOrdrStr = "Processed Time";
		else if(methodName.equals("getImpExpIndCdDesc"))
			sortOrdrStr = "Type";
		else if(methodName.equals("getDocName"))
			sortOrdrStr = "Template Name";
		else if(methodName.equals("getSrcName"))
			sortOrdrStr = "Source";
		else if(methodName.equals("getRecordStatusCd"))				
			sortOrdrStr = "Status";
		
	} else {
			sortOrdrStr = "Processed Time";
	}
	
	if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
		sortOrdrStr = sortOrdrStr+" in ascending order ";
	else if(sortOrder != null && sortOrder.equals("2"))
		sortOrdrStr = sortOrdrStr+" in descending order ";

	return sortOrdrStr;
	}catch (Exception ex) {
		logger.fatal("Error in getSortCriteriaForTemplate in Action Util: "+ex.getMessage(), ex);
		throw new Exception(ex.getMessage(), ex);
	
	}
		
}

public void updateConditionCode(){
	
	try{
		if (getPageManagementEJBRef() != null){
		PageManagementDAOImpl dao = new PageManagementDAOImpl();
		//frmList = dao.getInvestigationFormCodeonServerStartup();
	      getPageManagementEJBRef().updateConditionCode();
		}
	}catch(Exception e){
		logger.fatal("Error in accessing the WA Question from DAO"+e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
	
	
	}


public static String formatDate(java.sql.Timestamp timestamp, String format) {
    Date date = null;
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    if (timestamp != null) {
      date = new Date(timestamp.getTime());
    }
    if (date == null) {
      return "";
    }
    else {
      return formatter.format(date);
    }
  }
//This code is for batch entry
public void updateBatchUidForBatchEntry(HttpServletRequest request, String elementIds)throws Exception
{
	try{
		StringTokenizer st = new StringTokenizer(elementIds, ",\n");
		Collection<Object>  waUiMetadataDtColl = new ArrayList<Object>();
   	 	PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
   	 	PageElementVO pageElementVO = new PageElementVO();
		// TODO Step 1 get max parentUid from DB,  
		PageManagementDAOImpl dao = new PageManagementDAOImpl();
		Integer maxGroupUid = null;
		Integer nextGroupUid = null;
		maxGroupUid = dao.getMaxGroupIdForBatch(pmpVo.getWaTemplateDT().getWaTemplateUid());
		
		//TODO step 2 get the max parentUid Id from PageElementVOCollection
		ArrayList<Object> pageElementVoColl = (ArrayList<Object>)pmpVo.getThePageElementVOCollection();
		Integer maxGroupUidFromColl = null;
		maxGroupUidFromColl = (((PageElementVO)(pageElementVoColl.get(0)))).getWaUiMetadataDT().getQuestionGroupSeqNbr();
		
		if(maxGroupUidFromColl == null)
			maxGroupUidFromColl = Integer.valueOf(0);
		
		for(int i =0; i<pageElementVoColl.size(); i++){
			if((((PageElementVO)(pageElementVoColl.get(i)))).getWaUiMetadataDT().getQuestionGroupSeqNbr() != null){
			     if((((PageElementVO)(pageElementVoColl.get(i)))).getWaUiMetadataDT().getQuestionGroupSeqNbr().compareTo(maxGroupUidFromColl)>0){
			    	 maxGroupUidFromColl =  (((PageElementVO)(pageElementVoColl.get(i)))).getWaUiMetadataDT().getQuestionGroupSeqNbr();
			     }
			}
		}
		//TODO step 3 compare both maxId and take the biggest max
		Integer actualMaxParentUid = null;
		if(maxGroupUid.compareTo(maxGroupUidFromColl)>0)
			actualMaxParentUid = maxGroupUid;
		else
			actualMaxParentUid = maxGroupUidFromColl;
		
		//TODO step 4 add 1 to the max parentUid, this will be the parentUid for the current batch elements
		if(actualMaxParentUid != null)
			nextGroupUid = actualMaxParentUid+1;
		else
			nextGroupUid = Integer.valueOf(1);
		
		//TODO Step 5 get all waUiMetadata and update parentUid with maxvalue
		while (st.hasMoreTokens()) {
			Long elementUid = new Long(st.nextToken());
			 for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				 Long val1 = ((PageElementVO) peVo).getPageElementUid();
				 if (val1 != null && elementUid != null &&  val1.equals(elementUid)) {
					 pageElementVO = (PageElementVO) peVo;
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(nextGroupUid);
					 waUiMetadataDtColl.add(pageElementVO.getWaUiMetadataDT());
					 break;
				 }
			 }
		}
		//TODO update the values in the waUiMetadata table
		//dao.updateWaUiMetaDataColl(waUiMetadataDtColl);
		
	}catch (Exception ex) {
        logger.fatal("Error in updateBatchUidForBatchEntry in Action Util: "+ex.getMessage(), ex);
        throw new Exception(ex.getMessage(), ex);
    }
}

public void updateBatchUidForUnbatch(HttpServletRequest request, String elementIds) throws Exception{

	try{
		StringTokenizer st = new StringTokenizer(elementIds, ",\n");
		Collection<Object>  waUiMetadataDtColl = new ArrayList<Object>();
   	 	PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
   	 	PageElementVO pageElementVO = new PageElementVO();
   	 	PageManagementDAOImpl dao = new PageManagementDAOImpl();
   	 	
		//TODO Step 1 get all waUiMetadata 
		while (st.hasMoreTokens()) {
			Long elementUid = new Long(st.nextToken());
			 for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				 Long val1 = ((PageElementVO) peVo).getPageElementUid();
				 if (val1 != null && elementUid != null &&  val1.equals(elementUid)) {
					 pageElementVO = (PageElementVO) peVo;
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableAppearIndCd(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableHeader(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableColumnWidth(null);
					 waUiMetadataDtColl.add(pageElementVO.getWaUiMetadataDT());
					 break;
				 }
			 }
		}
		//TODO step 2 update the batchUid with null value in the waUiMetadata table
		//dao.updateWaUiMetaDataColl(waUiMetadataDtColl);
		
	}catch (Exception ex) {
        logger.fatal("Error in updateBatchUidForUnbatch in Action Util: "+ex.getMessage(), ex);
        throw new Exception(ex.getMessage(), ex);
    }
	
}
	
public void updateBatchUidForRelocatedFieldRow(HttpServletRequest request, String sourceSubsectionEltId,String sourceChildEltsCount,String targetElementId, String sourceElementId)throws Exception{
	try{
		PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
	 	PageElementVO pageElementVO = new PageElementVO();
	 	//Step 1 if the target subsection is batch, assign that batchUid to the relocating fieldRow 
	 	// if target subsection is not batch, it will assign null to the batchUid of the relocating fieldRow
	 	Integer targetParentUid = null;
	 	Long targetId = new Long(targetElementId);
	 	for (Object peVo : pmpVo.getThePageElementVOCollection()) {
			 Long val1 = ((PageElementVO) peVo).getPageElementUid();
			 if (val1 != null && targetId != null &&  val1.equals(targetId)) {
				 pageElementVO = (PageElementVO) peVo;
				 targetParentUid = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
				 break;
			 }
		 }
	 	Long sourceId = new Long(sourceElementId);
	 	for (Object peVo : pmpVo.getThePageElementVOCollection()) {
			 Long val1 = ((PageElementVO) peVo).getPageElementUid();
			 if (val1 != null && sourceId != null &&  val1.equals(sourceId)) {
				 pageElementVO = (PageElementVO) peVo;
				 if(targetParentUid != null){
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(targetParentUid);
					 pageElementVO.getWaUiMetadataDT().setBatchTableAppearIndCd("N");
					 pageElementVO.getWaUiMetadataDT().setBatchTableHeader(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableColumnWidth(null);
				 }
				 if(targetParentUid == null){
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableAppearIndCd(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableHeader(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableColumnWidth(null);
				 }
				 break;
			 }
		 }
	 	// Step 2 if the child count for sourceSubsection is 0, make that subsection unBatch
	 	int sourceChildCount = Integer.parseInt(sourceChildEltsCount);
	 	if(sourceChildCount == 0)
	 	{
	 		Long sourceSubsectionId = new Long(sourceSubsectionEltId);
	 		for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				 Long val1 = ((PageElementVO) peVo).getPageElementUid();
				 if (val1 != null && sourceSubsectionId != null &&  val1.equals(sourceSubsectionId)) {
					 pageElementVO = (PageElementVO) peVo;
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableAppearIndCd(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableHeader(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableColumnWidth(null);
					 break;
				 }
			 }
	 	}
	 	
	}catch (Exception ex) {
        logger.fatal("Error in updateBatchUidForRelocatedFieldRow in Action Util: "+ex.getMessage(), ex);
        throw new Exception(ex.getMessage(), ex);
    }
}

public void updateBatchUidForImportedFieldRow(HttpServletRequest request,String fieldElementIds, String sourceSubsectionElementId)throws Exception
{
	try{
		PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
	 	PageElementVO pageElementVO = new PageElementVO();
	 	
	 	Integer subSectionGroupUid = null;
	 	Long sourceSubsectionId = new Long(sourceSubsectionElementId);
	 	for (Object peVo : pmpVo.getThePageElementVOCollection()) {
			 Long val1 = ((PageElementVO) peVo).getPageElementUid();
			 if (val1 != null && sourceSubsectionId != null &&  val1.equals(sourceSubsectionId)) {
				 pageElementVO = (PageElementVO) peVo;
				 subSectionGroupUid = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
				 break;
			 }
		 }
	 	StringTokenizer st = new StringTokenizer(fieldElementIds, ",\n");
	 	while (st.hasMoreTokens()) {
			Long fieldId = new Long(st.nextToken());
		 	if(subSectionGroupUid != null){
			 	for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				 Long val1 = ((PageElementVO) peVo).getPageElementUid();
				 if (val1 != null && fieldId != null &&  val1.equals(fieldId)) {
					 pageElementVO = (PageElementVO) peVo;
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(subSectionGroupUid);
					 pageElementVO.getWaUiMetadataDT().setBatchTableAppearIndCd("N");
					 pageElementVO.getWaUiMetadataDT().setBatchTableColumnWidth(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableHeader(null);
					 break;
				 }
			 	}
		 	}
	 	}
	 
		
	}catch (Exception ex) {
        logger.fatal("Error in updateBatchUidForImportedFieldRow in Action Util: "+ex.getMessage(), ex);
        throw new Exception(ex.getMessage(), ex);
    }
}
public void updateBatchUidForDraggedFieldRow(HttpServletRequest request, String targetElementId, String sourceElementId)throws Exception{
	try{
		PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
	 	PageElementVO pageElementVO = new PageElementVO();
	 	//Step 1 if the target subsection is batch, assign that batchUid to the relocating fieldRow 
	 	// if target subsection is not batch, it will assign null to the batchUid of the relocating fieldRow
	 	Integer targetParentUid = null;
	 	Long targetId = new Long(targetElementId);
	 	for (Object peVo : pmpVo.getThePageElementVOCollection()) {
			 Long val1 = ((PageElementVO) peVo).getPageElementUid();
			 if (val1 != null && targetId != null &&  val1.equals(targetId)) {
				 pageElementVO = (PageElementVO) peVo;
				 targetParentUid = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
				 break;
			 }
		 }
	 	
		 	Long sourceId = new Long(sourceElementId);
		 	Integer sourceParentUid = null;
		 	for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				 Long val1 = ((PageElementVO) peVo).getPageElementUid();
				 if (val1 != null && sourceId != null &&  val1.equals(sourceId)) {
					 pageElementVO = (PageElementVO) peVo;
					 sourceParentUid = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
					 /*if(sourceParentUid == null)
						 sourceParentUid = Long.valueOf(0);*/
						 
					 /*if(sourceParentUid.compareTo(targetParentUid)== 0){}
					 else
						 pageElementVO.getWaUiMetadataDT().setParentUid(targetParentUid);*/
					 break;
				 }
			 }
		 if(sourceParentUid == null)
			 sourceParentUid = Integer.valueOf(0);
		 if(targetParentUid == null)
			 targetParentUid = Integer.valueOf(0);
		 String changedGrpId = (String)request.getSession().getAttribute("changedGrpID");
		 request.getSession().removeAttribute("changedGrpID");
		 
		 if(targetParentUid.compareTo(sourceParentUid)!= 0 && (changedGrpId == null || !changedGrpId.equals("true"))){
			 sourceParentUid = targetParentUid;
			 for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				 Long val1 = ((PageElementVO) peVo).getPageElementUid();
				 if (val1 != null && sourceId != null &&  val1.equals(sourceId)) {
					 pageElementVO = (PageElementVO) peVo;
					 if(sourceParentUid.intValue() == 0)
						 sourceParentUid = null;
					 pageElementVO.getWaUiMetadataDT().setQuestionGroupSeqNbr(sourceParentUid);
					 pageElementVO.getWaUiMetadataDT().setBatchTableAppearIndCd("N");
					 pageElementVO.getWaUiMetadataDT().setBatchTableColumnWidth(null);
					 pageElementVO.getWaUiMetadataDT().setBatchTableHeader(null);
					 request.getSession().setAttribute("changedGrpID", "true");
					 break;
				 }
			 }
		 }
			 
	}catch (Exception ex) {
        logger.fatal("Error in updateBatchUidForDraggedFieldRow in Action Util: "+ex.getMessage(), ex);
        throw new Exception(ex.getMessage(), ex);
    }
}

/**
 * Retrieve a single page element object from the PageMangementProxyVO stored in session. 
 * The object returned is uniquely identified by its element ID.
 * @param waElementUid
 * @return
 */
public ArrayList<WaUiMetadataDT>  retrieveBatchedQuestions(HttpServletRequest request, Integer questionGrSeqNbr, Long pageElementUid) throws Exception
{
    PageElementVO returnVal = new PageElementVO();
    ArrayList<WaUiMetadataDT>  waUiMetadataList = new ArrayList<WaUiMetadataDT>();	
    try{
    PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
    for (Object peVo : pmpVo.getThePageElementVOCollection()) {
        Integer val1 = ((PageElementVO) peVo).getWaUiMetadataDT().getQuestionGroupSeqNbr();
        Long fieldPgEleUid = ((PageElementVO) peVo).getPageElementUid();
        Integer val2 = questionGrSeqNbr;
        if (val1 != null && val2 != null && val1.equals(val2) && ! fieldPgEleUid.equals(pageElementUid)) {
            returnVal = (PageElementVO) peVo;
            String questionLabel =  returnVal.getWaUiMetadataDT().getQuestionLabel();
            String questionIdentifier = returnVal.getWaUiMetadataDT().getQuestionIdentifier();
            String questionForDisplay = questionLabel.concat("(").concat(questionIdentifier).concat(")");
            returnVal.getWaUiMetadataDT().setQuestionWithQuestionIdentifier(questionForDisplay);
            waUiMetadataList.add(returnVal.getWaUiMetadataDT());
        }
    }
    }catch (Exception ex) {
        logger.fatal("Error in retrieveBatchedQuestions in Action Util: "+ex.getMessage(), ex);
        throw new Exception(ex.getMessage(), ex);
    }
    return waUiMetadataList;
}

public WaTemplateVO getPageDetails( Long waTemplateUid, HttpSession session) throws Exception{
	
	MainSessionCommand msCommand = null;
	WaTemplateVO waTemplateVO = new WaTemplateVO();
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getPageDetails";
		Object[] oParams =new Object[] {waTemplateUid};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			waTemplateVO = (WaTemplateVO)aList.get(0);
	
		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getPageDetails in Page Mgt Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
	  return waTemplateVO;
}

public void updatePageDetails( WaTemplateVO waTemplateVO ) throws Exception{
	
	try{
		if (getPageManagementEJBRef() != null){
	      getPageManagementEJBRef().updatePageDetails(waTemplateVO);
		}
	}catch(Exception e){
		logger.fatal("Error occurred in EJB updating Page Details"+e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
}

public Map<Object,Object> getBatchMap( Long waTemplateUid, HttpSession session) throws Exception{
	
	MainSessionCommand msCommand = null;
	Map<Object,Object> batchMap = new HashMap<Object,Object>();
	
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getBatchMap";
		Object[] oParams =new Object[] {waTemplateUid};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			batchMap = (Map<Object,Object>)aList.get(0);
	
		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getBatchMap in Page Mgt Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
	///////////////////////////////////////////////////
	//For Rolling Notes we are adding in metadata
	//The assumption is made that it is the only thing in the subsection 
	//The User and Date are stored at the start of the note so there is no nbs_question_uid
	//so the second value in the batchmap is null
	if(batchMap != null && batchMap.size() > 0) {
			 Iterator<Entry<Object, Object>>  ite = batchMap.entrySet().iterator();
			  while(ite.hasNext()) {
				  //get the batch metadata for the subsection
				  Map.Entry<Object,Object> pairs1 = (Map.Entry<Object,Object>)ite.next(); 
				  String SubSectionNm = pairs1.getKey().toString();
				  String batch[][] = (String[][])pairs1.getValue();	
				  for(int i=0;i<batch.length;i++) {
					  if(batch[i][5] != null && batch[i][5].toString().equalsIgnoreCase("1019")) {
						  //add the Date as if it was in the question metadata for the Repeating Batch header to work correctly
						 int batchLoc = i + 1;
						 String dateMeta[] = {batch[i][0]+"Date",null,"Y",PageMetaConstants.RollingNoteHeaderDateDisplay, "11","1008",null};
						 batch[batchLoc] = dateMeta;
						//add the User as if it was in the question metadata for the Repeating Batch header to work correctly
						 String userMeta[] = {batch[i][0]+"User",null,"Y", PageMetaConstants.RollingNoteHeaderUserDisplay, "19","1008",null};
						 batch[++batchLoc] = userMeta;
						 //pairs1.setValue(batch);
						 break;  
					  }
				  } //for
			  } //while iter
	} //if not null
	
	return batchMap;
}


public Collection<Object> getWaTemplateUidByPageNm( String templateNm, HttpSession session) throws Exception{
	
	MainSessionCommand msCommand = null;
	Collection<Object> templateUidColl = new ArrayList<Object>();
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getWaTemplateUidByPageNm";
		Object[] oParams =new Object[] {templateNm};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			templateUidColl = ( Collection<Object>)aList.get(0);
	
		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getWaTemplateUidByPageNm in Page Mgt Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
	  return templateUidColl;
}


public WaTemplateDT getWaTemplate(String conditionCd, HttpSession session) throws Exception{
	
	MainSessionCommand msCommand = null;
	WaTemplateDT waTemplateDT = new WaTemplateDT();
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getWaTemplate";
		Object[] oParams =new Object[] {conditionCd};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			waTemplateDT = (WaTemplateDT)aList.get(0);
	
		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getWaTemplate in Page Mgt Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
	  return waTemplateDT;
}

public String getPublishedConditionInd(String conditionCd, HttpSession session) throws Exception{
	
	MainSessionCommand msCommand = null;
	String publishInd=null;
	try{
		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getPublishedCondition";
		Object[] oParams =new Object[] {conditionCd};				
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if(aList != null && aList.size()>0){
			publishInd = (String)aList.get(0);
		}	
	  }catch (Exception ex) {
			logger.fatal("Error in getPublishedConditionInd in Page Mgt Action Util: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	  }
	  return publishInd;
}

public static void updateCodeSystemNameforEdit(WaQuestionDT dt) throws ServletException{
	TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
	Collection<Object> CVGs = returnMap.values();
	if(CVGs !=null && CVGs.size()>0){
		Iterator<Object> ite  = CVGs.iterator();
		while(ite.hasNext()){
			CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)ite.next();
			if(dtCVGCache.getCodeDescTxt().equals(dt.getQuestionOid())){
				dt.setQuestionOidCode(dtCVGCache.getCode());
				break;
			}
		}
	}
}
	public Long findPageExistenceByBusinessObjType(String busObjType, HttpServletRequest request) throws Exception{
		Long waTemplateUid = null;
		try{
			Object[] oParams = new Object[] {busObjType};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "findPageByBusinessObjType";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			ArrayList<Object> pageList = (ArrayList<Object>) obj;
			if(pageList!=null){
				for(int i=0;i<pageList.size();i++){
					WaTemplateDT waTemplateDT = (WaTemplateDT) pageList.get(i);
					waTemplateUid = waTemplateDT.getWaTemplateUid();
					//If page is in initial draft mode then return wa_template_uid of it.
					if("F".equals(waTemplateDT.getPublishIndCd())){
						return waTemplateUid;
					}
				}
			}
		}catch (Exception ex) {
			logger.fatal("Error in findWatemplateUidByBusinessObjType : "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		return waTemplateUid;
	}
	
	
	/**
	 * @param request
	 * @return List of Nbs_Bus_Obj_Metadata
	 * @throws Exception
	 */
	public ArrayList<Object> getNbsBusObjMetadataList(HttpSession session) throws Exception{
		try{
			Object[] oParams = new Object[] {};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getNbsBusObjMetadataList";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, session);
			ArrayList<Object> nbsBusObjList = (ArrayList<Object>) obj;
			return nbsBusObjList;
		}catch (Exception ex) {
			logger.fatal("Error in getNbsBusObjMetadataList : "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}

	/**
	 * @param request
	 * @return NbsBusObjMetadataDT
	 * @throws Exception
	 */
	public NbsBusObjMetadataDT getNbsBusObjMetadataByBusObjType(String busObjType, HttpServletRequest request) throws Exception{
		try{
			Object[] oParams = new Object[] {busObjType};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getNbsBusObjMetadataByBusObjType";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			NbsBusObjMetadataDT nbsBusObjMetadataDT = (NbsBusObjMetadataDT) obj;
			return nbsBusObjMetadataDT;
		}catch (Exception ex) {
			logger.fatal("Error in getNbsBusObjMetadataByBusObjType : "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * @param busObjType
	 * @param waUiMetadataDT
	 * @param request
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateQuestionMetadataWhileAddingOnPage(String busObjType, WaUiMetadataDT waUiMetadataDT, HttpServletRequest request) throws Exception{
		boolean isUpdate = false;
		try{
			String answerTableColumn ="ANSWER_TXT";
			String defaultRDBTableNm = "D_INV_CLINICAL";
			String GROUP_INV = "GROUP_INV";
			
			NbsBusObjMetadataDT nbsBusObjMetadataDT = getNbsBusObjMetadataByBusObjType(busObjType, request);
			if(nbsBusObjMetadataDT!=null){
				String existingTableName = "";
				if(waUiMetadataDT.getDataLocation()!=null){
					existingTableName = waUiMetadataDT.getDataLocation().substring(0,waUiMetadataDT.getDataLocation().indexOf("."));
					
					//If data_location is not in core table then change data_location and rdb_table_nm
					if(!nbsBusObjMetadataDT.getCoreODSETable().contains(existingTableName)){
						waUiMetadataDT.setDataLocation(nbsBusObjMetadataDT.getAnswerTable()+"."+answerTableColumn);
						
						String rdbTableName = null;
						if(!GROUP_INV.equalsIgnoreCase(nbsBusObjMetadataDT.getGroupNm())){
							waUiMetadataDT.setRdbTableNm(nbsBusObjMetadataDT.getCoreRDBTable());
						}else if(waUiMetadataDT.getSubGroupNm()!=null){
							
							rdbTableName = CachedDropDowns.getCodeDescTxtForCd(waUiMetadataDT.getSubGroupNm(), NEDSSConstants.NBS_PH_DOMAINS);
							if(rdbTableName!=null && rdbTableName.length()>0)
								waUiMetadataDT.setRdbTableNm(rdbTableName);
							else
								waUiMetadataDT.setRdbTableNm(defaultRDBTableNm);
						}
					}
				}
				
				waUiMetadataDT.setGroupNm(nbsBusObjMetadataDT.getGroupNm());
				waUiMetadataDT.setStandardQuestionIndCd("F");
				
				isUpdate = true;
			}
		}catch (Exception ex) {
			logger.fatal("Error in updateQuestionMetadataWhileAddingOnPage : "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		return isUpdate;
	}
	

	/**
	 * @param busObjType
	 * @param waUiMetadataDT
	 * @param waRdbMetadataDT
	 * @param request
	 * @throws Exception
	 */
	public void updateRDBTableNameWhileAddingQuestionOnPage(String busObjType,
			WaUiMetadataDT waUiMetadataDT, WaRdbMetadataDT waRdbMetadataDT,
			HttpServletRequest request) throws Exception {
		try {
			String GROUP_INV = "GROUP_INV";
			String defaultRDBTableNm = "D_INV_CLINICAL";
			NbsBusObjMetadataDT nbsBusObjMetadataDT = getNbsBusObjMetadataByBusObjType(
					busObjType, request);
			if (nbsBusObjMetadataDT != null && waUiMetadataDT != null) {
				logger.debug("busObjType: " + busObjType
						+ ", waUiMetadataDT.getQuestionIdentifier(): "
						+ waUiMetadataDT.getQuestionIdentifier()
						+ ", waUiMetadataDT.getSubGroupNm(): "
						+ waUiMetadataDT.getSubGroupNm());

				if (waUiMetadataDT.getQuestionGroupSeqNbr() != null) {
					/*
					 * For issue where Editing the Page is changing the
					 * rdb_column_nm from D_INV_PLACE_REPEAT to
					 * D_INVESTIGATION_REPEAT for NBS243 and NBS290
					 */
					if (waUiMetadataDT.getNbsUiComponentUid() != null
							&& waUiMetadataDT.getNbsUiComponentUid()
									.longValue() != 1017)
						waRdbMetadataDT.setRdbTableNm(nbsBusObjMetadataDT
								.getCoreRepeatRDBTable());
				} else if (waRdbMetadataDT.getRdbTableNm() != null
						&& waRdbMetadataDT.getRdbTableNm().contains("_REPEAT")) {
					if (!GROUP_INV.equalsIgnoreCase(nbsBusObjMetadataDT
							.getGroupNm())) {
						waRdbMetadataDT.setRdbTableNm(nbsBusObjMetadataDT
								.getCoreRDBTable());
					} else if (waUiMetadataDT.getSubGroupNm() != null) {

						String rdbTableName = CachedDropDowns
								.getCodeDescTxtForCd(
										waUiMetadataDT.getSubGroupNm(),
										NEDSSConstants.NBS_PH_DOMAINS);
						if (rdbTableName != null && rdbTableName.length() > 0)
							waRdbMetadataDT.setRdbTableNm(rdbTableName);
						else
							waRdbMetadataDT.setRdbTableNm(defaultRDBTableNm);
					}
				}
			}
		} catch (Exception ex) {
			logger.fatal(
					"Error in updateRDBTableNameWhileAddingQuestionOnPage : busObjType: "
							+ busObjType + ", Exception: " + ex.getMessage(),
					ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * @param activityLogUid
	 * @param templateNm
	 * @param templateUid
	 * @param recordType
	 * @param logType
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	private EDXActivityLogDT prepareEdxActivityDetail(Long activityLogUid, String templateNm, String templateUid, String recordType, String logType, String comment) throws Exception{
		try{
			EDXActivityLogDT dt = new EDXActivityLogDT();
			EDXActivityDetailLogDT ddt = new EDXActivityDetailLogDT();
			Collection<Object> eDXActivityLogDTDetails = new ArrayList<Object>();
			
			ddt.setEdxActivityLogUid(activityLogUid);
		    ddt.setRecordId(templateUid);
		    ddt.setRecordType(recordType);
		    ddt.setRecordName(templateNm);
		    ddt.setLogType(logType);
		    ddt.setComment(comment);
		    eDXActivityLogDTDetails.add(ddt);
			dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
			dt.setLogDetailAllStatus(true);
			dt.setRecordStatusCd("Success");
			
			return dt;
		}catch(Exception ex){
			logger.fatal("Error in prepareEdxActivityDetail "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * Check if pre-requisite subforms are published before publishing parent form. 
	 * @param waTemplateUid
	 * @param waTemplateDTColl
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean checkSubFormsPublishedForPage(Long waTemplateUid, Collection<Object> waTemplateDTColl, HttpServletRequest request) throws Exception{
		try{
			ArrayList<Object> waUiMetadataList = findWaUiMetadataForSubFormByWaTemplateUid(waTemplateUid, request.getSession());
			String formNames = "";
			if(waUiMetadataList.size()>0){
				Map<String, WaTemplateDT> templateMap = new HashMap<String, WaTemplateDT>();
				for (Object obj : waTemplateDTColl) {
					WaTemplateDT waTemplateDT = (WaTemplateDT) obj;
					if(waTemplateDT.getFormCd()!=null)
						templateMap.put(waTemplateDT.getFormCd().toLowerCase(), waTemplateDT);
				}
				for(int i=0;i<waUiMetadataList.size();i++){
					WaUiMetadataDT waUiMetadataDT = (WaUiMetadataDT) waUiMetadataList.get(i);
					
					String formCd = waUiMetadataDT.getDefaultValue().substring("OpenForm:".length());
					
					if(formCd!=null){
						WaTemplateDT waTemplateDT = templateMap.get(formCd.trim().toLowerCase());
						if(waTemplateDT==null){ //If subform page is not created then display error
							formNames = formNames + formCd +";";
						}else if(formCd!=null && formCd.trim().equalsIgnoreCase(waTemplateDT.getFormCd()) && "Initial Draft".equalsIgnoreCase(waTemplateDT.getTemplateType()) && waTemplateDT.getPublishVersionNbr()==null){
							//If subform is not published then display error
							formNames = formNames + formCd +";";
						}
					}
				}
			}
			if(formNames.length()>0){
				formNames = formNames.substring(0,formNames.length()-1);
				String warningMessage  = "This page cannot be published until the related subforms for the page have been published. The subforms related to this page include(s): "+formNames+".";
				request.setAttribute("WarningMessageForSubformPublish", warningMessage);
				return false;
			}
		}catch(Exception ex){
			logger.fatal("Error in checkSubFormsPublishedForPage "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		return true;
	}
	
	
	/**
	 * @param waTemplateUid
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Object> findWaUiMetadataForSubFormByWaTemplateUid(Long waTemplateUid, HttpSession session) throws Exception{
		try{
			Object[] oParams = new Object[] {waTemplateUid};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "findWaUiMetadataForSubFormByWaTemplateUid";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, session);
			ArrayList<Object> waUiMetadataList = (ArrayList<Object>) obj;
			return waUiMetadataList;
		}catch (Exception ex) {
			logger.fatal("Error in findWaUiMetadataForSubFormByWaTemplateUid : "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	

	public NbsPageDT getNbsPageDetailsExceptJspPayloadByFormCd(String formCd) throws NEDSSAppException {
		try {
			NedssUtils nu = new NedssUtils();
			Object pageref = nu.lookupBean(JNDINames.PAGE_MANAGEMENT_PROXY_EJB);
			if (pageref != null) {
				PageManagementProxyHome home = (PageManagementProxyHome) PortableRemoteObject.narrow(pageref, PageManagementProxyHome.class);
				PageManagementProxy pageManageProxy = home.create();
				NbsPageDT nbsPageDT = pageManageProxy.findNBSPageDetailsExceptJspPayloadByFormCd(formCd);
				return nbsPageDT;
			}
		}catch(Exception ex) {
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return null;
	}
		
}