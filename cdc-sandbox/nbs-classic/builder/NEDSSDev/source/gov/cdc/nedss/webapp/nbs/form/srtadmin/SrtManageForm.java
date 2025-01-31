package gov.cdc.nedss.webapp.nbs.form.srtadmin;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class SrtManageForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	
	SRTAdminUtil srtAdminUtil = new SRTAdminUtil();
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<?> manageList = new ArrayList<Object> ();
	ArrayList<?> oldManageList = new ArrayList<Object> ();
	ArrayList<?> codeValueGnList = new ArrayList<Object> ();
	Object selection = new Object();
	Object codeValGnSelection = new Object();
	Object oldDT = new Object();
	Object tmpCVGDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private int srchFldCount;
	private PhinVadsSystemVO phinVadsSystemVo;
	private String lastCodeSelected;
	
	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private ArrayList<Object> type = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	static final LogUtils logger = new LogUtils(SrtManageForm.class.getName());
	
	private ArrayList<Object> code= new ArrayList<Object> ();
	private ArrayList<Object> name= new ArrayList<Object> ();	
	private ArrayList<Object> description= new ArrayList<Object> ();
	
	
	public ArrayList<Object> getType() {
		return type;
	}
	public void setType(ArrayList<Object> type) {
		this.type = type;
	}
	public ArrayList<Object> getStatus() {
		return status;
	}
	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}

	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

	public void setAnswerArray(String key, String[] answer) {
		if(answer.length > 0) {
			String [] answerList = new String[answer.length];
			boolean selected = false;
			for(int i=1; i<=answer.length; i++) {
				String answerTxt = answer[i-1];
				if(!answerTxt.equals("")) {
					selected = true;
					answerList[i-1] = answerTxt;
				}
			}
			if(selected)
				searchCriteriaArrayMap.put(key,answerList);
		}
	}
	
	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}
	
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}
	
	public PhinVadsSystemVO getPhinVadsSystemVo() {
		return phinVadsSystemVo;
	}
	public void setPhinVadsSystemVo(PhinVadsSystemVO phinVadsSystemVo) {
		this.phinVadsSystemVo = phinVadsSystemVo;
	}
	public int getSrchFldCount() {
		return srchFldCount;
	}
	public void setSrchFldCount(int srchFldCount) {
		this.srchFldCount = srchFldCount;
	}
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}
	
	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, HTMLEncoder.encodeHtml(answer));
	}

	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
	}
	
	
	public ArrayList<?> getManageList() {
		return manageList;
	}
	public void setManageList(ArrayList<?>  manageList) {
		this.manageList = manageList;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
	public Object getSelection() {
		return selection;
	}
	public void setSelection(Object selection) {
		this.selection = copyObject(selection);
	}
	
	public void resetSelection() {
		this.selection = new Object();
		this.lastCodeSelected = "";
		this.oldDT = new Object();
	}
	
	public Object getCodeValGnSelection() {
		return codeValGnSelection;
	}
	public void setCodeValGnSelection(Object codeValGnSelection) {
		this.codeValGnSelection = copyObject(codeValGnSelection);
	}
	public void resetCodeValGnSelection() {
		this.codeValGnSelection = new Object();
		this.oldDT = new Object();
	}
	public String getActionMode() {
		return actionMode;
	}
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}	
	
	public void clearSelections() {
		this.setSearchMap(new HashMap<Object,Object>());
		this.setManageList(new ArrayList<Object>());
		this.resetSelection();
		this.setAttributeMap(new HashMap<Object,Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);
		
	}
	public ArrayList<Object> getProgramAreaList(){
		return CachedDropDowns.getProgramAreaList();
	}

	public ArrayList<Object> getConditionList(){
		return CachedDropDowns.getAllConditions();
	}
	
	public ArrayList<Object> getLaboratoryIds(){
		return CachedDropDowns.getLaboratoryIds();
	}

	public ArrayList<Object> getTestTypeList(){
		return CachedDropDowns.getTestTypeList();
	}

	public ArrayList<Object> getAllCodeSetNms(){
		return CachedDropDowns.getAllCodeSetNms();
	}

	public Object getOldDT() {
		return oldDT;
	}
	public void setOldDT(Object oldDT) {
		this.oldDT = copyObject(oldDT);
	}
	
    private static Object copyObject(Object param) {
    	Object deepCopy = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(param);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			deepCopy= ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopy;
	}	

	public Object getCodedValueNoBlnk(String key) {
		ArrayList<?> list = (ArrayList<?> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		return aList;
	}

	public Object getSearchCriteriaDropDown() {
		ArrayList<Object> list = new ArrayList<Object> ();
		DropDownCodeDT dt = new DropDownCodeDT();DropDownCodeDT dt1 = new DropDownCodeDT();DropDownCodeDT dt2 = new DropDownCodeDT();
		//dt.setKey("");dt.setValue("");list.add(dt);
		dt1.setKey("CT"); dt1.setValue("Contains"); list.add(dt1);
		dt2.setKey("="); dt2.setValue("Equal"); list.add(dt2);
		return list;
	}
	public ArrayList<Object> getAllCodeSystemCdDescs(){
		String codeSetNm = searchMap.get("CODEVALGEN") == null ? "" : (String) searchMap.get("CODEVALGEN");
		return CachedDropDowns.getAllCodeSystemCdDescs(codeSetNm);
	}
	
	public ArrayList<?> getOldManageList() {
		return oldManageList;
	}
	public void setOldManageList(ArrayList<?>  oldManageList) {
		this.oldManageList = oldManageList;
	}

	public ArrayList<Object> getSRTAdminAssignAuth(){
		return CachedDropDowns.getSRTAdminAssignAuth();
	}
	public ArrayList<Object> getSRTAdminCodingSysCd(){
		return CachedDropDowns.getSRTAdminCodingSysCd();
	}

	public ArrayList<Object> getCodingSystemCodes(String assignAuthority) {
		return CachedDropDowns.getCodingSystemCodes(assignAuthority);

	}
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
		code= new ArrayList<Object> ();
		name= new ArrayList<Object> ();	
		description= new ArrayList<Object> ();
	}
	public void initializeDropDowns(ArrayList<Object> codeSetList) {
		type = srtAdminUtil.getType(codeSetList);
		status = srtAdminUtil.getStatusDropDowns(codeSetList);
     }
	public ArrayList<?> getCodeValueGnList() {
		return codeValueGnList;
	}
	public void setCodeValueGnList(ArrayList<?> codeValueGnList) {
		this.codeValueGnList = codeValueGnList;
	}
	
	public String updateCodeValGenCode(String loclCode , String phinStdConcept  , String conceptNm, String prefConceptNm , String conceptCode , String conectSysName,
						 String ValLDN , String ValSDN , String ValEFT , String ValETD,  String valLSC , String ValADC  ) {
		
		 WebContext ctx = WebContextFactory.get();
		 HttpServletRequest request = ctx.getHttpServletRequest();
		 HttpServletResponse response = ctx.getHttpServletResponse();
		logger.debug("Values"); 
		CodeValueGeneralDT dt = (CodeValueGeneralDT) getCodeValGnSelection();
		//dt.setCodeDescTxt(conceptNm);
		//dt.setCodeShortDescTxt(prefConceptNm);
		dt.setConceptNm(conceptNm);
		dt.setConceptPreferredNm(prefConceptNm);
		dt.setConceptTypeCd(phinStdConcept);
		dt.setCodeSystemDescTxt(conectSysName);
		dt.setConceptCode(conceptCode);
		dt.setCode(loclCode);
		dt.setCodeDescTxt(ValLDN);
		dt.setCodeShortDescTxt(ValSDN);
		dt.setStatusCd(valLSC);
		dt.setLocalEffectiveFromTime(ValEFT);
		dt.setLocalEffectiveToTime(ValETD);
		dt.setAdminComments(ValADC);
		
		TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
		CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)returnMap.get(conectSysName);
		dt.setCodeSystemCd(dtCVGCache.getCodeDescTxt());//2.16.840.1.113883.6.96 code_desc_txtCodeSystemDescTxt
		dt.setCodeSystemDescTxt(dtCVGCache.getCodeShortDescTxt());//SNOMED-CT code_short_desc_txt
		logger.debug("DT Object Values ****************"+dt.toString());
		SRTAdminUtil.trimSpaces(dt);
		try {
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "updateCodeValueGeneral", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.EDIT,NEDSSConstants.CODE);
			logger.error("Exception in updateCodeValGenCode: " + e.getMessage());
			setPageTitle(SRTAdminConstants.EDIT_VALUE_SET, request);
			setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			request.getSession().setAttribute("errorConcept" ,request.getAttribute("error"));
			return "SUCCESS";
		} finally {
		}
		StringBuffer displaySb = new StringBuffer();
		displaySb.append("<b>").append(dt.getCode()).append("</b>-<b>").append(dt.getCodeDescTxt()).append("</b> has been successfully updated in the system.");
		request.getSession().setAttribute("confirmConceptMsg" ,displaySb.toString());
		request.getSession().removeAttribute("confirmAddConceptMsg");
		return "SUCCESS";		
	}	
	
	private static String changeFormat(String date) throws NEDSSAppException{
		
		try{
		
			SimpleDateFormat formatter, FORMATTER;
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObject = formatter.parse(date);
			FORMATTER = new SimpleDateFormat("MM-dd-yyyy");
			return FORMATTER.format(dateObject);
		}catch(Exception e){	
			logger.error(e);
			logger.error("CDCFieldRecordForm.changeFormat Exception thrown, "+ e);
			throw new NEDSSAppException("SrtManageForm.changeFormat Exception thrown, ", e);
		}
}
	
	public String createCodeValGenSubmit(String conceptNm, String prefConceptNm, String code, String phinStdConceptInd, String codeSystemDescTxt , 
									String ValLC, String ValLDN , String ValSDN , String ValEFT , String ValETD,  String valLSC , String ValADC  ) {
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		HttpServletResponse response = ctx.getHttpServletResponse();
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		
		CodeValueGeneralDT dt = (CodeValueGeneralDT) getCodeValGnSelection();
		CodeSetDT codesetDt = (CodeSetDT)getSelection();
		dt.setCodeSetNm(codesetDt.getCodeSetNm());
		dt.setCode(ValLC);
		dt.setCodeDescTxt(ValLDN);
		dt.setCodeShortDescTxt(ValSDN);
		//dt.setCodeSystemCd("L");
		dt.setCodeSystemDescTxt(codeSystemDescTxt);
		dt.setIndentLevelNbr(new Integer(1));
		dt.setIsModifiableInd("Y");
		dt.setStatusCd(valLSC);
		dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
		dt.setConceptCode(code);
		dt.setConceptNm(conceptNm);
		dt.setConceptPreferredNm(prefConceptNm);
		dt.setConceptStatusCd(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
		dt.setConceptTypeCd(phinStdConceptInd);
		dt.setLocalEffectiveFromTime(ValEFT);
		dt.setLocalEffectiveToTime(ValETD);
		dt.setAdminComments(ValADC);
		TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
		CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)returnMap.get(codeSystemDescTxt);
		dt.setCodeSystemCd(dtCVGCache.getCodeDescTxt());//2.16.840.1.113883.6.96 code_desc_txtCodeSystemDescTxt
		dt.setCodeSystemDescTxt(dtCVGCache.getCodeShortDescTxt());//SNOMED-CT code_short_desc_txt

		dt.setAddUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
		try {
			SRTAdminUtil.trimSpaces(dt);
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "createCodeValueGeneral", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE, NEDSSConstants.CODE);
			request.setAttribute("manageList", getCodeValueGnList());
			request.setAttribute("SearchResult", "SearchResult");
			setPageTitle(SRTAdminConstants.ADD_VALUE_SET, request);
			request.getSession().setAttribute("errorConcept" ,request.getAttribute("error"));
			if(e.toString().contains("PK Violated")){
				return "NOTSUCCESS";
			}
			else{				
				return e.toString();
			}
		}
		request.setAttribute("manageList", getCodeValueGnList());
		StringBuffer displaySb = new StringBuffer();
		displaySb.append("<b>").append(dt.getCode()).append("</b>-<b>").append(dt.getCodeDescTxt()).append("</b> has been successfully added to the <b>").append(codesetDt.getCodeSetNm()).append("</b> value set.");
		request.getSession().setAttribute("confirmAddConceptMsg" ,displaySb.toString());
		return "SUCCESS";		
	}		
	private void searchSubmit(ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		//manageForm.getSearchMap().remove("CODESYSTEMCDDESC");
		Map<Object,Object> searchMap = manageForm.getSearchMap();
		CodeSetDT codeSetDt = (CodeSetDT)manageForm.getSelection();
		Object[] searchParams = null;
		
	//	if(searchMap.size() > 0)
		searchParams = new Object[] {codeSetDt.getCodeSetNm()};
				//searchMap.values().toArray();
			
		Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "retrieveCodeSetValGenFields", searchParams };
		ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
		Iterator<?> iter = dtList.iterator();
		while(iter.hasNext()) {
			CodeValueGeneralDT dt = (CodeValueGeneralDT) iter.next();
			dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
			String viewUrl = "<a href=\"javascript:viewConceptCd('" + dt.getCode() + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" title=\"View\" alt=\"View\" /></a>";
			dt.setViewLink(viewUrl);
			String editUrl = "<a href=\"javascript:editConceptCd('" + dt.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" title=\"Edit\" alt=\"Edit\" /></a>";
			dt.setEditLink(editUrl);
		}			
		manageForm.setCodeValueGnList(dtList);
	
	}
	public ArrayList<Object> getvalueSetTypeCdNoSystemStrd(){
		return CachedDropDowns.getvalueSetTypeCdNoSystemStrd();
	}
	public ArrayList<Object> getCode() {
		return code;
	}
	public void setCode(ArrayList<Object> code) {
		this.code = code;
	}
	public ArrayList<Object> getName() {
		return name;
	}
	public void setName(ArrayList<Object> name) {
		this.name = name;
	}
	public ArrayList<Object> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<Object> description) {
		this.description = description;
	}
	public String getLastCodeSelected() {
		return lastCodeSelected;
	}
	public void setLastCodeSelected(String lastSelection) {
		this.lastCodeSelected = lastSelection;
	}
}
