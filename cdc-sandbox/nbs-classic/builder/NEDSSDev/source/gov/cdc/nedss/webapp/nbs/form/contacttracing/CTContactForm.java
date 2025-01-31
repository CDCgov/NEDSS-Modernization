package gov.cdc.nedss.webapp.nbs.form.contacttracing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO.CTContactClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.DWRUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTRulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;

/**
 *Name: CTContactForm.java Description: Form class modified for Contact Records(for Dynamic
 * Pages) Much of this form was moved to the Base Form. 
 * Copyright(c) 2013 Company: Leidos
 *
 * @since: NBS4.5
 * @author Gregory Tucker (modifications for dynamic page)
 */


public class CTContactForm extends BaseForm{

	private static final long serialVersionUID = 1L;
	private CTContactClientVO cTContactClientVO = new CTContactClientVO();
	private CTContactProxyVO cTContactProxyVO = new CTContactProxyVO();
    private boolean stdContact = false;


	static final LogUtils logger = new LogUtils(CTContactForm.class.getName());
	private Long mprUid;

	private static int nextId = 1;


	public void clearAll() {
		getAttributeMap().clear();
	}


	public CTContactProxyVO getcTContactProxyVO() {
		return cTContactProxyVO;
	}

	public void setcTContactProxyVO(CTContactProxyVO cTContactProxyVO) {
		this.cTContactProxyVO = cTContactProxyVO;
	}

	public Long getMprUid() {
		return mprUid;
	}

	public void setMprUid(Long mprUid) {
		this.mprUid = mprUid;
	}

	public CTContactClientVO getcTContactClientVO() {
		return cTContactClientVO;
	}

	public void setcTContactClientVO(CTContactClientVO cTContactClientVO) {
		this.cTContactClientVO = cTContactClientVO;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		cTContactClientVO.reset(mapping, request);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if (errors == null)
			errors = new ActionErrors();
		return errors;
	}

	public ArrayList<?> clearDetailsAsian() {
		getcTContactClientVO().getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_ASIAN);
		return (ArrayList<?> ) getRaceList("2028-9");
	}
	public ArrayList<?> clearDetailsHawaii() {
		getcTContactClientVO().getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_HAWAII);
		return (ArrayList<?> ) getRaceList("2076-8");
	}


	public ArrayList<Object> fireRule(String fieldAndValue) {
		long  start=0;
		start = System.currentTimeMillis();
		logger.debug("........start.:"+start);
		String key = "";
		String value = "";
		String keyVal="";
		if (fieldAndValue != null && fieldAndValue.indexOf(":") >= 0) {
			String[] tokens = fieldAndValue.split(":");
			key = tokens[0];
			if(tokens.length==1)// user selected null value.
				keyVal="abcxyz";
			if(tokens.length>1 && tokens[1] != null && !(tokens[1].equals("")))
				keyVal = tokens[1];
			if(keyVal !=null && keyVal.indexOf(",")==0 && keyVal.lastIndexOf(",")==0){
				String[] vals = keyVal.split(",");
				value = vals[1];
			}
			else{
				value = keyVal;
			}
			if ("abcxyz".equals(value)) {
				value = null;
			}
		}
		List<Object> valList= new ArrayList<Object> ();
		if(value !=null && value.lastIndexOf(",")>0){
			String[] values = value.split(",");
			if(values != null && values.length>0){
				for(int i = 0;i<values.length;i++){
					if(values[i] != null && !values[i].equals("")){
						valList.add(values[i]);
					}
				}
			}

		}

		if (formFieldMap != null && formFieldMap.size() > 0) {
			FormField fField = (FormField)formFieldMap.get(key);
			if(valList!=null && valList.size()>0){
				fField.getState().setMultiSelVals(valList);
				fField.setValue("");

			}
			else if((valList == null ) || (valList!=null && valList.size()==0)){
				fField.setValue(value);
				fField.getState().setMultiSelVals(null);
			}
			formFieldMap.put(fField.getFieldId(), fField);
		}
		CTRulesEngineUtil util = new CTRulesEngineUtil();

		return (ArrayList<Object> )util.fireRules(key,this, formFieldMap);

	}
	
	/**
	 * dwr call to delete the attachment and return String
	 * @param uid
	 * @return
	 */
	public boolean deleteAttachment(String uid) {
		boolean isDeleted = false;
		Long ctUid = Long.valueOf(uid);
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();			
		try {
			processRequest(ctUid, req.getSession());
			isDeleted = true;
		} catch (Exception e) {				
			e.printStackTrace();
			logger.error("Error while deleting CT Attachment for uid: " + uid);
		}

		return isDeleted;
	}

	@SuppressWarnings("unchecked")
	private static String processRequest(Long ctUid, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		String attachmentTxt = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "deleteContactAttachment";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { ctUid };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			attachmentTxt = (String)arr.get(0);

		} catch (Exception ex) {
			logger.error("Error while processRequest in FileUploadAction: " + ex.toString());	
			throw new Exception(ex);
		}
		return attachmentTxt;	
	}     		

	public String getDwrSpecifiedInvestigator(String providerUid, String identifier) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		StringBuffer sb = new StringBuffer();
		String dwrInvestigatorDetails = "";
		String uidVersion = "1";
		NBSSecurityObj nbsSecurityObj = null;
		//if no Uid get the Default Investigator
		if (providerUid == null || providerUid.isEmpty() || providerUid.equalsIgnoreCase("default")) {
			try {
				nbsSecurityObj = (NBSSecurityObj) req.getSession()
					.getAttribute("NBSSecurityObject");
			} catch (Exception e) {
			logger.warn("Exception getting security obj in getDwrInvestigatorDetailsByUid: " + e);
			}
			if (nbsSecurityObj != null
				&& nbsSecurityObj.getTheUserProfile().getTheUser()
						.getProviderUid() != null) {
			
				try {
					providerUid =  nbsSecurityObj.getTheUserProfile()
						.getTheUser().getProviderUid().toString();
				} catch (NumberFormatException e) {
				logger.error("Error in getDwrInvestigatorDetailsByUid: provider UID format invalid?");
				} catch (Exception e) {
				logger.warn("Exception getting provider uid in getDwrInvestigatorDetailsByUid: " + e);
				}
			} 
		} //
		if (providerUid == null)
			return ""; //not really error, no default provider
		try {
			PersonVO providerVO = findProvider(providerUid, req.getSession());
			if (providerVO != null) {
				QuickEntryEventHelper qeHelper = new QuickEntryEventHelper();
				dwrInvestigatorDetails = qeHelper.makePRVDisplayString(providerVO);
				if (providerVO.getThePersonDT().getVersionCtrlNbr() != null)
					uidVersion = providerVO.getThePersonDT().getVersionCtrlNbr().toString();
			}
		} catch (Exception e) {
			dwrInvestigatorDetails = "";
			logger.error("Exception raised in getDwrSpecifiedInvestigator: "
					+ e);
			e.printStackTrace();
		}
		if (dwrInvestigatorDetails == null || dwrInvestigatorDetails.isEmpty())
			return "";

		getAttributeMap().put(identifier + "Uid",
				(providerUid + "|" + uidVersion));
		getAttributeMap().put(identifier + "SearchResult",
				dwrInvestigatorDetails);
		sb.append(providerUid).append("$$$$$").append(
				dwrInvestigatorDetails);
		return sb.toString();
	}
	
    /**
	 * Retrieves Person VO Information
	 * @param providerUid personUid
	 * @param session
	 * @return
	 */
    private static PersonVO findProvider(String providerUidStr, HttpSession session)
    {
    	
        PersonVO personVO = null;
        MainSessionCommand msCommand = null;
        try
        {
        	Long providerUid = new Long(providerUidStr);
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "getPerson";
            Object[] oParams = new Object[] { providerUid };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            personVO = (PersonVO) arr.get(0);
        }
        catch (NumberFormatException e) {
			logger.error("Number format err in CTContactForm.findPerson(" + providerUidStr + ")");
			} 
        catch (Exception ex)
        {
        	logger.warn("CTContactForm.findPerson(" + providerUidStr + ") had error");
            ex.printStackTrace();
            if (session == null)
            {
                logger.error("Error: no session, please login");
            }
        }
        return personVO;
    }	
	public static synchronized int getNextId()
    {
		return nextId++;
    }
	//gst: may want to revisit if we can get rid of this.. for performance reasons.. who usees the qquick entry list anyway?
	public void initializeForm(ActionMapping mapping, HttpServletRequest request) {
		// QECodes autocomplete
		ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(false, "PRV");
		request.getSession().setAttribute("qecList", qecList);
		ArrayList<Object> qecListORG = CachedDropDowns.getAllQECodes(false,
				"ORG");
		request.getSession().setAttribute("qecListORG", qecListORG);
	}
	
	/**
	 * @return the stdContact
	 */
	public boolean isStdContact() {
		return stdContact;
	}

	/**
	 * @param stdContact the stdContact to set
	 */
	public void setStdContact(boolean stdContact) {
		this.stdContact = stdContact;
	}
	
}
