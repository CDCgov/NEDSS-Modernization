package gov.cdc.nedss.webapp.nbs.form.page;

import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.DWRUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 *Name: PageSubForm.java Description: Sub Form class for Case Object(for sub forms from dynamic
 * Pages) 
 * Note: A number of items were moved to the BaseForm for the 4.5 release
 * Copyright(c) 2018 Company: GDIT
 *
 * @since: NBS6.0
 * @author Fatima Lopez Calzado
 */
public class PageSubForm extends BaseForm { 
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(PageSubForm.class.getName());

	private PageClientVO pageClientVO = new PageClientVO();

	private Collection<Object>  oldResultedTestVOCollection  = null;

	private ArrayList<Object> conditionFamilyList = new ArrayList<Object>();

	private String formName;
	
	private String formContent;
	
	private String coinfCondInvUid;
	
	private Map coinfectionCondMap =new HashMap<String,String>();
	
	private String coinfInvList;
 

	public PageClientVO getPageClientVO() {
		return pageClientVO;
	}
	
	public void setPageClientVO(PageClientVO pageClientVO) {
		this.pageClientVO = pageClientVO;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		pageClientVO.reset(mapping, request);
	//	batchEntryMap = new HashMap<Object,ArrayList<BatchEntry>>();
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormContent() {
		return formContent;
	}

	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}

	public String getCoinfCondInvUid() {
		return coinfCondInvUid;
	}

	public void setCoinfCondInvUid(String coinfCondInvUid) {
		this.coinfCondInvUid = coinfCondInvUid;
	}

	@SuppressWarnings("unchecked")
	public Map<String,String> getCoinfectionCondMap() {
		return coinfectionCondMap;
	}

	public void setCoinfectionCondMap(Map coinfectionCondMap) {
		this.coinfectionCondMap = coinfectionCondMap;
	}

	public String getCoinfInvList() {
		return coinfInvList;
	}

	public void setCoinfInvList(String coinfInvList) {
		this.coinfInvList = coinfInvList;
	}

	public Collection<Object> getOldResultedTestVOCollection() {
		return oldResultedTestVOCollection;
	}

	public void setOldResultedTestVOCollection(Collection<Object> oldResultedTestVOCollection) {
		this.oldResultedTestVOCollection = oldResultedTestVOCollection;
	}

	public void setConditionFamilyList(ArrayList<Object> conditionFamilyList) {
		this.conditionFamilyList = conditionFamilyList;
	}
	
	public ArrayList<Object> getConditionFamilyList(String conditionCd, NBSSecurityObj nbsSecurityObj, HttpServletRequest req) {
		ArrayList<Object> condFamilyList = new ArrayList<Object>();
		condFamilyList = CachedDropDowns.getConditionFamilyList(conditionCd);
		if (condFamilyList.size() > 1) {
			CachedDropDownValues cdv = new CachedDropDownValues();
			//some of the conditions could be in program areas which the user
			//does not have add investigation permission
			//remove any the user does not have permission
			Iterator<Object> ite = condFamilyList.iterator();
			while (ite.hasNext()) {
				DropDownCodeDT dropDownDT = (DropDownCodeDT) ite.next();
				String thisCondCd = dropDownDT.getKey();
				if (thisCondCd.isEmpty())  //blank
					continue;
				String programArea = cdv.getProgramAreaCd(thisCondCd);
				//we can't create a new investigation for the changed condition
				//if the user doesn't have add permission
				boolean addInvestigationPermission =
					nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
						NBSOperationLookup.ADD, programArea,
						ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
				if (addInvestigationPermission == false)
						ite.remove();
			}
		}
		return condFamilyList;
	}
	

	public ArrayList<Object> validateNotificationReqFields(HttpServletRequest req) 
	{
		//WebContext ctx = WebContextFactory.get();
		// HttpServletRequest req = ctx.getHttpServletRequest();
		Map<?,?> questionMap = (TreeMap<?,?>)QuestionsCache.getQuestionMap().get(this.getPageFormCd());
		ArrayList<Object> returnList = new ArrayList<Object> ();
		String notifErrorFieldList = "";
		try {
			Map<?,?> reqMap = DWRUtil.createNotification(((PageActProxyVO)getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO() ,questionMap, this.getPageFormCd(), req);
			if(reqMap != null && reqMap.size() > 0) 
			{
				Iterator<?>  iter = reqMap.keySet().iterator();
				while(iter.hasNext()) 
				{
					String key = (String) iter.next();
					String keyVal = (String) reqMap.get(key);
					notifErrorFieldList = notifErrorFieldList.concat(key + " "); //used to highlight missing fields on edit
					returnList.add(keyVal);
				}
				this.setErrorFieldList(notifErrorFieldList); //set fields to hilight on edit
				return returnList;
			}
		}
		catch (Exception e) 
		{
			logger.error("Error while Validating Notification Required Fields: " + e.toString());
			returnList.add("ERROR");
			return returnList;
		}
		return null;
	}

	/**
	 * deleteCurrentSubForm: deletes from the subFormHashMap stored in the session, the current key. This is used from subforms like Isolate Tracking page.
	 * @return
	 */

	public ArrayList<String> deleteCurrentSubForm(){
		ArrayList<String> msgList = new ArrayList<String>();
		String currentKey="";
		 
		try{
			WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();
			
			currentKey = (String)request.getSession().getAttribute(NEDSSConstants.CURRENT_KEY);
        	HashMap<String,Object> subFormHashMap =  (HashMap<String, Object>)request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
        	subFormHashMap.remove(currentKey);

		}catch(Exception ex){
			logger.error("PageSubForm.deleteCurrentSubForm: Error in calling the deleteCurrentSubForm, deleting key ="+currentKey);
		}
		return msgList;
	}
    
}
