package gov.cdc.nedss.webapp.nbs.action.person.util;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PatientRevisionSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.action.person.PersonSearchSubmit;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;
//import gov.cdc.nedss.webapp.nbs.form.file.FileDetailsForm;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import gov.cdc.nedss.util.HTMLEncoder;

/**
 *
 * <p>Title: </p>
 * <p>Description: Utility class for Patient Module</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class SearchResultPersonUtil {
	
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	static final LogUtils logger = new LogUtils(PersonSearchSubmit.class.getName());
	
	public void setDisplayInfo(ArrayList  revisionList,boolean permission, boolean bEntitySearch, HttpServletRequest request){  	
	    	
	    	Iterator Iter = revisionList.iterator();
	    	
	    	int i =0;
	    	String mode = request.getParameter("Mode1");
	    	
	    	if(mode==null)
	    		mode = (String)request.getAttribute("Mode1");

	    	while(Iter.hasNext()){
	    		PatientSrchResultVO vo = (PatientSrchResultVO)Iter.next();
		    	String mprId = "";
		    	
		    	if(vo.getPersonParentUid()!=null)
		    		mprId=vo.getPersonParentUid().toString();
		    	else
		    		mprId=vo.getPersonUID().toString();

		    	StringBuffer sbView = new StringBuffer();
		    	StringBuffer sbViewFile = new StringBuffer();
		    	StringBuffer sbLink = new StringBuffer();
		    	StringBuffer sbDisplayVersion = new StringBuffer();
		    	
	    	vo.setViewFileWithoutLink(mprId);
		    	
		    	String fullName = this.setPersonNameProfile(vo);
		    	vo.setPersonFullName(fullName);
		    	String fullNameNoLink = this.replaceDecoration(fullName);
		    	vo.setPersonFullNameNoLink(fullNameNoLink);
		    	
		    	String profile = this.setPersonInfoProfile(vo);
		    	vo.setProfile(profile);
		    	String profileNoLink = this.replaceDecoration(profile);
		    	vo.setProfileNoLink(profileNoLink);
		    	
		    	String address = this.setPersonAddressProfile(vo);
		    	vo.setPersonAddressProfile(address);
		    	String addressNoLink = this.replaceDecoration(address);
		    	vo.setPersonAddressProfileNoLink(addressNoLink);
		    	
		    	String phone = this.setPersonPhoneprofile(vo);
		    	vo.setPersonPhoneprofile(phone);
		    	String phoneNoLink = this.replaceDecoration(phone);
		    	vo.setPersonPhoneprofileNoLink(phoneNoLink);
		    	
		    	String id = this.setPersonIds(vo, request);
		    	vo.setPersonIds(id);
		    	String idNoLink = this.replaceDecoration(id);
		    	vo.setPersonIdsNoLink(idNoLink);

		    	String patId = vo.getPersonLocalID();
		    	String actualLocalID="";
		    	 String seedValue = propertyUtil.getSeedValue();
			        String sufix = propertyUtil.getUidSufixCode();
			        String prefix = NEDSSConstants.PERSON;
			        try
			        {
			            if(patId != null && !patId.equals("") && !patId.trim().equals("null")){
			        	  actualLocalID = patId.substring(prefix.length(), patId.indexOf(sufix)); 
			              actualLocalID =  new Long(actualLocalID).longValue() - new Long(seedValue).longValue()+"";			              
			            }          
			            	
			        }
			        catch(NumberFormatException nfe)
			        {
			          logger.error("Can not be able to convert String to long value :"+patId);
			        }
		    	
			    if(permission){ 
		    	//sbView.append("<a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=View&uid="+mprId+"\"><img src=\"page_white_text.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View\"/></a>");
			    	
				    if(mode!=null && mode.equalsIgnoreCase("ManualMerge"))
				    	sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"javascript:function popupViewEvents(){window.open('/nbs/MergeCandidateList1.do?ContextAction=ViewFile&amp;uid="+mprId+"');};popupViewEvents();\">");
				    else
				    	
				    	   if(mode!=null && mode.equalsIgnoreCase("SystemIdentified"))
						    	sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"javascript:function popupViewEvents(){window.open('/nbs/MergeCandidateList2.do?ContextAction=ViewFile&amp;uid="+mprId+"');};popupViewEvents();\">");
						    else
						    		sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid="+mprId+"\">"); 
		    	//sbViewFile.append(" <img src=\"layout.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View File\"/></a>");
		    	sbViewFile.append( actualLocalID + "</a>");
			    }
			    
			    else{
			    	sbViewFile.append( actualLocalID );
			    }
		    	if(vo.getRevisionColl()!= null){
		    		vo.setRevisionColl(new ArrayList<Object>());
		    		//sbLink.append("<img alt=\"\" border=\"0\" src=\"plus_sign.gif\" ");
		    		//sbLink.append("onclick=\"searchResultsRevisionShowOrHide("+mprId+", this,").append(i).append(");\">");
			    	Iterator versionIter = vo.getRevisionColl().iterator();	
			    	while(versionIter.hasNext()){		    		
			    		PatientRevisionSrchResultVO  patVo = (PatientRevisionSrchResultVO )versionIter.next();
			    		sbDisplayVersion.append("<tr class='none'  mpr='").append(mprId).append("'> <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
				    	sbDisplayVersion.append("<td>").append(this.setPersonNameProfile(patVo)).append("</td>");
			    		sbDisplayVersion.append("<td>").append(this.setPersonInfoProfile(patVo)).append("</td>");
			    		sbDisplayVersion.append("<td>").append(this.setPersonAddressProfile(patVo)).append("</td>");
			    		sbDisplayVersion.append("<td>").append(this.setPersonPhoneprofile(patVo)).append("</td>");
			    		sbDisplayVersion.append("<td>").append(this.setPersonIds(patVo, request)).append("</td><td></td></tr>");	
			    		
			    	}
			    	
		    	}
		    	vo.setDisplayRevision(sbDisplayVersion.toString());
		    	
		    	
		    	vo.setView(sbView.toString());
		    	vo.setViewFile(sbViewFile.toString());
		    	vo.setLink(sbLink.toString());
		    	if(bEntitySearch)
				    vo.setLink("<a href=\"javascript:selectPatient('"+mprId+"');\">"+actualLocalID+"</a>");
		    	i++;
		    	if(i >1)
		    		i =0;
	    	}
	    }
	    
	public void setDisplayInfoInvestigation(ArrayList  revisionList,boolean permission){  	
    	
    	Iterator Iter = revisionList.iterator();
    	
    	int i =0;
    	CachedDropDownValues srtValues = new CachedDropDownValues();
    	while(Iter.hasNext()){
    		PatientSrchResultVO vo = (PatientSrchResultVO)Iter.next();
    		String notification="";
    		notification = srtValues.getDescForCode("REC_STAT_NOT_UI", vo.getNotification());
    		if(notification != null)
    			vo.setNotification(notification);
    		
    		String conditionLink = "";
		    if(permission){ 
		    	conditionLink="<a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=InvestigationID&publicHealthCaseUID="+vo.getPublicHealthCaseUid()+"\">"+vo.getCondition()+"</a>";
	    	//sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid="+mprId+"\">"); 
	    	//sbViewFile.append(" <img src=\"layout.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View File\"/></a>");
	    	//sbViewFile.append( actualLocalID + "</a>");
		    	
		    	
		    	//String phcLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&publicHealthCaseUID="+ String.valueOf(inv.getPublicHealthCaseUid())+"\'"+")"+"\" >"+ condDesc + "</a>";
				
		    	vo.setConditionLink(conditionLink);
		    }else{
		   // 	sbViewFile.append( actualLocalID );
		    	
		    	vo.setConditionLink(vo.getCondition());
		    }
		    
		    
		    
    		
    		vo.setCaseStatus(vo.getCaseStatus());
    		
    		String startDate = vo.getStartDate()==null?" ":(StringUtils.formatDate(vo.getStartDate()));
    		vo.setStartDate_s(startDate);
    		
    		vo.setJurisdiction(vo.getJurisdiction());
    		String investigator = (vo.getInvestigatorLastName()==null?"":vo.getInvestigatorLastName()+", ")+(vo.getInvestigatorFirstName()==null?"":vo.getInvestigatorFirstName());
    		vo.setInvestigator(investigator);
    		
    		String personFullName = (vo.getPersonLastName()==null?"":vo.getPersonLastName()+", ")+(vo.getPersonFirstName()==null?"":vo.getPersonFirstName());
    		StringBuffer sbViewFile = new StringBuffer();
    		if(permission){ 
		    	sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid="+String.valueOf(vo.getPersonUID())+"\">"); 
		    	sbViewFile.append( personFullName + "</a>");
		    	
			    }else{
			    	sbViewFile.append( personFullName );
			    }
    			vo.setPersonFullNameNoLink(personFullName);
				vo.setPersonFullName(sbViewFile.toString());
				this.setPatientFormat(vo);
				
			
	    	i++;
	    	if(i >1)
	    		i =0;
    	}
    }
	public void setDateFormat(PatientSrchResultVO vo){
        String startDate = vo.getStartDate()==null?"":
			StringUtils.formatDate(vo.getStartDate());
        startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(vo.getStartDate());
       
        String startDatePrint = vo.getDateReceived()==null?"":
  			StringUtils.formatDate(vo.getStartDate());
        startDatePrint = startDatePrint+"\n"+StringUtils.formatDatewithHrMin(vo.getStartDate());
           
        vo.setStartDate_s(startDate);
        vo.setStartDate(vo.getStartDate()); 
		vo.setDateReceived(startDatePrint);
  	}
	HashMap <String, String> mapClassCd = new HashMap<String, String>();
	 public void getAssociatedInvestigations(PatientSrchResultVO observationSummaryDisplayVO, Long observationUid, HttpServletRequest request){
     	
     	String result="";

     	if(mapClassCd.size()==0){
     		mapClassCd.put("C","Confirmed");
     		mapClassCd.put("P","Probable");
     		mapClassCd.put("N","Not A Case");
     		mapClassCd.put("S","Suspect");
     	}

     	ArrayList<String> conditions = new ArrayList<String>();
	        Collection<Object> invSummaryVOs = observationSummaryDisplayVO.getInvSummaryVOs();
	        String testsStringNoLnk="";
	        String testsStringPrint="";
	        String viewInvref = request.getAttribute("ViewInvHref") == null ? "": (String) request.getAttribute("ViewInvHref");
			Iterator<Object>  caseIterator2 = null;

			    		   
			for (caseIterator2 = invSummaryVOs.iterator(); caseIterator2.hasNext(); ) {
			    InvestigationSummaryVO invSummary = (InvestigationSummaryVO) caseIterator2.next();

			    String localId = invSummary.getLocalId();
			    String condition = invSummary.getConditionCodeText();
			    String classCodeDesc = mapClassCd.get(invSummary.getCaseClassCd());
			    String classCode = classCodeDesc==null ? "" : classCodeDesc;
			    Long uid = invSummary.getPublicHealthCaseUid();
	   			
	   			conditions.add(condition);
	   			testsStringNoLnk+=" "+condition;
	   			testsStringPrint+=localId+" "+condition+" "+classCode;
	   			
 			result +=  "<a href=\"#\" onclick=\"createLink(this,\'" + viewInvref+ "&publicHealthCaseUID="+ String.valueOf(uid)+ "\'"+")"+"\" >"+ localId + "</a><br><b>"+condition+"</b><br>"+classCode+"<br>";
				observationSummaryDisplayVO.setTestsString(result);
	
		    }
		   observationSummaryDisplayVO.setConditions(conditions);
		   observationSummaryDisplayVO.setTestsStringNoLnk(testsStringNoLnk);//For sorting
		   observationSummaryDisplayVO.setTestsStringPrint(testsStringPrint);
	   }
	 
public void setDisplayInfoLaboratoryReport(ArrayList  revisionList,boolean permission, HttpServletRequest request){  	
    	
    	Iterator Iter = revisionList.iterator();
    	ObservationReviewQueueUtil util = new ObservationReviewQueueUtil();
    	int i =0;
    	CachedDropDownValues srtValues = new CachedDropDownValues();
    	while(Iter.hasNext()){
    		PatientSrchResultVO vo = (PatientSrchResultVO)Iter.next();
    		String notification="";
    		notification = srtValues.getDescForCode("REC_STAT_NOT_UI", vo.getNotification());
    		if(notification != null)
    			vo.setNotification(notification);
    		
    		String conditionLink = "";
		    if(permission){ 
		    	conditionLink="<a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=InvestigationID&publicHealthCaseUID="+vo.getPublicHealthCaseUid()+"\">"+vo.getCondition()+"</a>";
	    	//sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid="+mprId+"\">"); 
	    	//sbViewFile.append(" <img src=\"layout.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View File\"/></a>");
	    	//sbViewFile.append( actualLocalID + "</a>");
		    	
		    	
		    	//String phcLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&publicHealthCaseUID="+ String.valueOf(inv.getPublicHealthCaseUid())+"\'"+")"+"\" >"+ condDesc + "</a>";
				
		    	vo.setConditionLink(conditionLink);
		    }else{
		   // 	sbViewFile.append( actualLocalID );
		    	
		    	vo.setConditionLink(vo.getCondition());
		    }
		    
		    
		    
    		vo.setCaseStatus(vo.getCaseStatus());
    		
    			
    		
    		vo.setJurisdiction(vo.getJurisdiction());
    		
    		/*String investigator = (vo.getInvestigatorLastName()==null?"":vo.getInvestigatorLastName()+", ")+(vo.getInvestigatorFirstName()==null?"":vo.getInvestigatorFirstName());
    		vo.setInvestigator(investigator);*/
    		
    		String personFullName = (vo.getPersonLastName()==null?"":vo.getPersonLastName()+", ")+(vo.getPersonFirstName()==null?"":vo.getPersonFirstName());
    		StringBuffer sbViewFile = new StringBuffer();
    		if(permission){ 
		    	sbViewFile.append(" <a onclick=\"changeSubmitOnce(this);\" href=\"/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid="+String.valueOf(vo.getPersonUID())+"\">"); 
		    	sbViewFile.append( personFullName + "</a>");
		    	
			    }else{
			    	sbViewFile.append( personFullName );
			    }
    			vo.setPersonFullNameNoLink(personFullName);
				vo.setPersonFullName(sbViewFile.toString());
				if(vo.getCurrentSex() != null)
					vo.setCurrentSex(srtValues.getDescForCode("SEX", vo.getCurrentSex()));
				this.setPatientFormat(vo);
				/*String currentSex="";
				if(vo.getCurrentSex() != null)
					currentSex = srtValues.getDescForCode("SEX", vo.getCurrentSex());
	    		vo.setCurrentSex(currentSex);
	    		vo.setPersonDOB(vo.getPersonDOB());*/

				//Set Print and Display variable of the start date
				this.setDateFormat(vo);
				//Document Type link
				String viewLabref="/nbs/PatientSearchResults1.do?ContextAction=ViewLab";

    			if(!viewLabref.equals("")) {
    				
    				//StringBuffer patLink = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>");
    				//observationSummaryDisplayVO.setFullName(patLink.toString());
    				StringBuffer event = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewLabref+ "&MPRUid="+ String.valueOf(vo.getMPRUid()) + "&observationUID="+String.valueOf(vo.getObservationUid())+"\'"+")"+"\" >"+ "Lab Report" + "</a>");
    				vo.setDocumentType(event.toString());
    				vo.setDocumentTypeNoLnk("Lab Report");
    		           if(vo.getElectronicInd().equalsIgnoreCase("Y"))
    		        	   vo.setDocumentTypeNoLnk(vo.getDocumentTypeNoLnk()+"\n(E)");
    				
    				util.appendElectronicLabIcon(vo);

    			}
	
    			//Provider and Reporting Facility Information			
    			util.formatProvider(vo);
    			
    			//Description
    			/*
    	           if(vo.isLabFromDoc()){
    	        	   vo.setDescription(vo.getResultedTestString());
    			 
    	           }else{*/
    				 vo.setDescription(DecoratorUtil.getResultedTestsStringForWorup(vo.getTheResultedTestSummaryVOCollection()));
    				 vo.setDescriptions(DecoratorUtil.getResultedDescription(vo.getTheResultedTestSummaryVOCollection()));
    				
    	          // }
    	           String descriptionPrint = util.getDescriptionPrint(vo.getDescription());
    	           vo.setDescriptionPrint(descriptionPrint);
    	           
    			//Associated With
    			
    			//vo.setInvSummaryVOs(labReportSummaryVO.getInvSummaryVOs());
    			request.setAttribute("ViewInvHref","PatientSearchResults1.do?ContextAction=ViewInv");
    			getAssociatedInvestigations(vo, vo.getObservationUid(), request);
    			
		           
	    	i++;
	    	if(i >1)
	    		i =0;
    	}
    }

public void setPatientFormat(PatientSrchResultVO vo){
	String sex = vo.getCurrentSex()==null?null:(vo.getCurrentSex());
	String birthTime=vo.getPersonDOB()==null?null:vo.getPersonDOB();
	String personLocalId=vo.getPersonLocalID()==null?null:vo.getPersonLocalID();
	String personAge="";
	if(null != birthTime)    personAge = PersonUtil.displayAgeForPatientResults(vo.getPersonDOB()).toString().trim();
	String personUid = "";

 	String seedValue = propertyUtil.getSeedValue();
    String sufix = propertyUtil.getUidSufixCode();
    String prefix = NEDSSConstants.PERSON;
    try{
        if(personLocalId != null && !personLocalId.equals("") && !personLocalId.trim().equals("null")){
        	personUid = personLocalId.substring(prefix.length(), personLocalId.indexOf(sufix)); 
        	personUid =  new Long(personUid).longValue() - new Long(seedValue).longValue()+"";			              
        }          
        	
    }catch(NumberFormatException nfe){
      logger.error("Can not be able to convert String to long value :"+personLocalId);
    }
    
    StringBuffer fullName = new StringBuffer(vo.getPersonFullName());
    StringBuffer fullNameNoLink = new StringBuffer(vo.getPersonFullNameNoLink());
    if (personUid != null && personUid.trim().length() > 0 ) {
    	fullName.append("<BR> <b>Patient ID: </b>").append(personUid);
    	fullNameNoLink.append("\nPatient ID: ").append(personUid);
    }
    if (sex != null && sex.trim().length() > 0) {
    	if("M".equalsIgnoreCase(sex)) sex="Male";
    	else if("F".equalsIgnoreCase(sex)) sex="Female";
    	else if("U".equalsIgnoreCase(sex)) sex="Unknown";
    	
    	fullName.append("<BR>").append(sex);
    	fullNameNoLink.append("\n").append(sex);
    }
    if (birthTime != null && birthTime.trim().length() > 0) {
    	fullName.append("<BR>").append(birthTime);
    	fullNameNoLink.append("\n").append(birthTime);
    }
    if (personAge != null && personAge.trim().length() > 0) {
    	fullName.append(" &#40;").append(personAge).append("&#41"); 
    	fullNameNoLink.append(" (").append(personAge).append(")");
    }

    vo.setPersonFullName(fullName.toString());
    vo.setPersonFullNameNoLink(fullNameNoLink.toString());
    
}

	    public String getIDDescTxt(String id)
	    {

		CachedDropDownValues srtValues = new CachedDropDownValues();
		String desc = srtValues.getCodeDescTxt(id);

		return desc;
	    }


	    public void setAgeAndUnits(PatientSearchVO psVO) {

	      String currentAgeAndUnits = PersonUtil.displayAge(psVO.getBirthTime());

	      if(currentAgeAndUnits!=null ||currentAgeAndUnits!="")
	      {
	        //System.out.println("currentAge and units = " + currentAgeAndUnits);
	        int pipe = currentAgeAndUnits.indexOf('|');
	        psVO.setAgeReported(currentAgeAndUnits.substring(0, pipe));
	        psVO.setAgeReportedUnitCd(currentAgeAndUnits.substring(pipe + 1));
	      }
	     }//setAgeAndUnits

	public void replaceDisplayLocalIDWithActualLocalID(PatientSearchVO psVO) {
		String seedValue = propertyUtil.getSeedValue();
		String suffix = propertyUtil.getUidSufixCode();
		String prefix = NEDSSConstants.PERSON;

		if (psVO.getPatientIDOperator().equals("IN")) {
			ArrayList<String> psArr = parse(psVO.getLocalID());
			ArrayList<String> psNew = new ArrayList();
			Iterator<String> psIter = psArr.iterator();
			while (psIter.hasNext()) {
				try {
					psNew.add(prefix
							.concat(String.valueOf((new Long(seedValue)
									.longValue() + (new Long(psIter.next()
									.trim()).longValue())))).concat(suffix));
				} catch (NumberFormatException nfe) {
					logger.error("Can not be able to convert String to long value :"
							+ psVO.getLocalID());
				}
			}

			String psStr = arrayListToCommaDelimitedString(psNew);
			if (psStr.length() == 0) {
				psStr = null;
			}
			psVO.setLocalID(psStr);
		} else {

			String actualLocalID = null;
			try {
				actualLocalID = prefix
						.concat(String.valueOf((new Long(seedValue).longValue() + (new Long(
								psVO.getLocalID().trim()).longValue()))))
						.concat(suffix);
			} catch (NumberFormatException nfe) {
				logger.error("Can not be able to convert String to long value :"
						+ psVO.getLocalID());
			}
			psVO.setLocalID(actualLocalID);
		}
	}
	     
	 	private static final char[] delims = {' ', '\t', ',', ';'};

	 	private ArrayList<String> parse (String str) {
			ArrayList<String>	ret 	= new ArrayList<String>();
			char[] 				chars 	= str.toCharArray();		
			String 				elem 	= "";
			
			for (int i=0; i < chars.length; i++) {
				boolean isDelim = false;
				char c = chars[i];
				for (int j=0; j < delims.length; j++) {
					char d = delims[j];
					if (c == d) {
						isDelim = true;
						if (elem.length() > 0 ) {
							ret.add(elem.trim());
						}
						elem = "";
						break;
					}
				}
				if (!isDelim) {
					elem += c;
				}
			}		
			elem = elem.trim();
			if (elem.length() > 0) {
				ret.add(elem);			
			}
			return ret;
		}
		
	 	private String parseToCommaDelimitedString(String str) {
			String ret = "";
			ArrayList<String> 	a = parse(str);
			Iterator<String> 	i = a.iterator();
			while(i.hasNext()) {
				ret += i.next()+",";
			}
			if (ret.length() > 0) {
				ret = ret.substring(0, ret.length()-1);
			}
			return ret;
		}
		
	 	private String arrayListToCommaDelimitedString(ArrayList a) {
			String ret = "";
			Iterator<String> 	i = a.iterator();
			while(i.hasNext()) {
				ret += "'"+i.next()+"',";
			}
			if (ret.length() > 0) {
				ret = ret.substring(0, ret.length()-1);
			}
			return ret;
		}

	     public void trimEventID(PatientSearchVO psVO) {
	     	
	     	String eventID = psVO.getActId().trim();
	     	psVO.setActId(eventID);
	     	
	     }
	     
	     /**
	      * Method used in HomePage PatientSearch Process
	      * @param psVO
	      */
	     public void _populateBirthTime(PatientSearchVO psVO) {
	     	String birthTime = psVO.getBirthTime() == null ? "" : psVO.getBirthTime();
	     	if(!birthTime.equals("")) {
	     		String month = birthTime.substring(0,2); 
	     		String day = birthTime.substring(3,5); 
	     		String year = birthTime.substring(6);
	     		
	     		psVO.setBirthTimeMonth(month);
	     		psVO.setBirthTimeDay(day);
	     		psVO.setBirthTimeYear(year);
	     	}
	     	
	     }
	 
	     
	     
		  public String setPersonNameProfile(Object vo) {
		  	StringBuffer personNameProfile = new StringBuffer();
			Collection<?> personNameColl = null;			
			String actualLocalID = "";
			if(vo instanceof PatientSrchResultVO){
				PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
				personNameColl = patVo.getPersonNameColl();			
			}
			if(vo instanceof PatientRevisionSrchResultVO){
				PatientRevisionSrchResultVO patVo = (PatientRevisionSrchResultVO)vo;
				personNameColl = patVo.getPersonNameColl();
				//patientID = patVo.getPersonLocalID();
			}
			String nameCode="";
	    	String firstName ="";
	    	String lastName ="";
	    	String middleName ="";
	    	String suffix ="";
	    	String fullName="";
	    	if(personNameColl!=null){
		    	Iterator nameIter =personNameColl.iterator();
		    	while(nameIter.hasNext()){		    		
		    		PersonNameDT name = (PersonNameDT)nameIter.next();    		
		    		
		    		lastName = name.getLastNm()!=null?name.getLastNm():"";
		    		lastName = lastName.length()>0?lastName + ", " :"---, ";
		    		firstName = name.getFirstNm()!=null?name.getFirstNm():"";
		    		firstName = firstName.length()>0?firstName:"---";		    		
		    		middleName = name.getMiddleNm()!=null?" "+name.getMiddleNm():"";
		    		
		    		suffix = name.getNmSuffix()!=null?name.getNmSuffix():"";
		    		suffix  = suffix.length()>0 ? ", "+suffix:suffix;
		    		
		    		nameCode = name.getNmUseCd()!=null?" <b>"+name.getNmUseCd()+"</b><br> ":"";
		    		//nameCode = "<br>";
		    		if(fullName.equals(""))
		    			fullName =  nameCode  + lastName + firstName + middleName + suffix + "<br>";
		    		else
		    			fullName = fullName  + nameCode  + lastName + firstName + middleName + suffix + "<br>";	
		    		
		    	}
			 }
	    	//personNameProfile.append(firstName).append(lastName).append(nameCode).append(actualLocalID);
	    	personNameProfile.append(fullName).append(actualLocalID);
	    	
	    	return personNameProfile.toString();
		  }
	     
			public String setPersonInfoProfile(Object vo) {
				StringBuffer sbProfile = new StringBuffer();
		    	
				if(vo instanceof PatientSrchResultVO){
					PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
					if(patVo.getPersonDOB() != null && !patVo.getPersonDOB().equals("")){
					sbProfile.append(PersonUtil.displayAgeForPatientResults(patVo.getPersonDOB())).append("<br>");					
					sbProfile.append(patVo.getPersonDOB()).append("<br>");
					}
					sbProfile.append(patVo.getCurrentSex()!=null?patVo.getCurrentSex():"");
				}
				if(vo instanceof PatientRevisionSrchResultVO){
					PatientRevisionSrchResultVO patVo = (PatientRevisionSrchResultVO)vo;
					if(patVo.getPersonDOB() != null && !patVo.getPersonDOB().equals("")){
					sbProfile.append(PersonUtil.displayAgeForPatientResults(patVo.getPersonDOB())).append("<br>");
					sbProfile.append(patVo.getPersonDOB()).append("<br>");
					}
					sbProfile.append(patVo.getCurrentSex()!=null?patVo.getCurrentSex():"");
				}
		    	
				
				return sbProfile.toString();
			}	  
		  
			/**
			 * replaceDecoration: used from Print and Download functionality to not show the tags
			 * @param value
			 * @return
			 */
			public String replaceDecoration(String value){
				
				value = value.replace("<b>","").replace("</b>","").replace("<br>"," ");
				
				return value;
			}

			public String setPersonAddressProfile(Object vo) {
			  StringBuffer personAddressProfile = new StringBuffer();
				Collection personLocatorsColl = null;
				if(vo instanceof PatientSrchResultVO){
					PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
					personLocatorsColl = patVo.getPersonLocatorsColl();
				}
				if(vo instanceof PatientRevisionSrchResultVO){
					PatientRevisionSrchResultVO patVo = (PatientRevisionSrchResultVO)vo;
					personLocatorsColl = patVo.getPersonLocatorsColl();
				}
			  boolean flag = false;
			  if(personLocatorsColl != null){
				     Iterator<Object>  addressIter = personLocatorsColl.iterator();
				      while (addressIter.hasNext()){
				        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
				        if (elp.getThePostalLocatorDT() != null){
				        	    if(flag){
				        	    	if(personAddressProfile != null && personAddressProfile.toString() != null && !personAddressProfile.toString().equals(""))
				        	    		personAddressProfile.append("<br>");
				        	    	//else	
				        	    //	personAddressProfile.append("<br>");			        	    	
				        	    }
				        	    flag = true;
				        	    
				        	    String useCdDesc=elp.getUseCd();
				        	    String countryCd = this.getCountryDescTxt(elp.getThePostalLocatorDT().getCntryCd());
				        	    if(useCdDesc != null && useCdDesc.equalsIgnoreCase("BIRTH PLACE") && (countryCd==null || countryCd.trim().length()==0))
				        	    	continue;
				        	     personAddressProfile.append("<b>"+useCdDesc+"</b><br>");
			        	    	 personAddressProfile.append(elp.getThePostalLocatorDT().getStreetAddr1()!=null?HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getStreetAddr1())+"<br>":"");
					        	 personAddressProfile.append(elp.getThePostalLocatorDT().getStreetAddr2()!=null?HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getStreetAddr2())+"<br>":"");
					        	 personAddressProfile.append(elp.getThePostalLocatorDT().getCityCd()!=null?HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getCityCd())+", " :"");
					        	 personAddressProfile.append(elp.getThePostalLocatorDT().getStateCd()!=null?HTMLEncoder.encodeHtml(this.getStateDescTxt(elp.getThePostalLocatorDT().getStateCd()))+" ":"");
					        	 personAddressProfile.append(elp.getThePostalLocatorDT().getZipCd()!=null?HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getZipCd()):"");
					        	 //Display the country if it is not UNITED STATES
					        	
					        	//If use_cd != 'Birth Place', then checking the country to be 'UNITED STATES'. Will display the country if it is not 'UNTIED STATES'
					        	 if(useCdDesc != null && !useCdDesc.equalsIgnoreCase("BIRTH PLACE") && !countryCd.equalsIgnoreCase("UNITED STATES")){
				        	    	 personAddressProfile.append(countryCd!=null? "<br>"+HTMLEncoder.encodeHtml(countryCd)+" ":"");
					        	    }
					        	 //If use_cd='Birth Place', then showing address with country
					        	 if(useCdDesc != null && useCdDesc.equalsIgnoreCase("BIRTH PLACE")){
				        	    	 personAddressProfile.append((countryCd!=null && countryCd.trim().length()!=0)?HTMLEncoder.encodeHtml(countryCd)+" ":"");
					        	    }
				        	    
				        }
				        
				       }			       
				    }
			return personAddressProfile.toString();
		}
		  
			public String setPersonPhoneprofile(Object vo) {
			StringBuffer personPhoneprofile = new StringBuffer();
			
			Collection personLocatorsColl = null;
			if(vo instanceof PatientSrchResultVO){
				PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
				personLocatorsColl = patVo.getPersonLocatorsColl();
			}
			if(vo instanceof PatientRevisionSrchResultVO){
				PatientRevisionSrchResultVO patVo = (PatientRevisionSrchResultVO)vo;
				personLocatorsColl = patVo.getPersonLocatorsColl();
			}
			boolean flag = false;
			if (personLocatorsColl != null){
			     Iterator<Object>  addressIter = personLocatorsColl.iterator();
			      while (addressIter.hasNext()){
			        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
			        if (elp.getTheTeleLocatorDT() != null){			        	
			        	if(flag){
			        		//personPhoneprofile.append("<br>");	        	    	
		        	    }

			        	flag = true;
			        	//String useCd=CachedDropDowns.getCdForCdDescTxt(elp.getUseCd(), "EL_USE_TELE_PAT");
			        	if(elp.getTheTeleLocatorDT().getPhoneNbrTxt()!= null && !elp.getTheTeleLocatorDT().getPhoneNbrTxt().equals("")){
			        	  
				        	if(elp.getUseCd() != null && (elp.getUseCd().equals("Work Phone") || elp.getUseCd().equals("Work Place")) && elp.getCd().equalsIgnoreCase("PH")){
				        	personPhoneprofile.append("<b>"+ "Work" +"</b><br>");
				            personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
				        	}
				        	else if(elp.getUseCd() != null && elp.getUseCd().equals("Home") && elp.getCd().equalsIgnoreCase("PH")){
					        	personPhoneprofile.append("<b>"+ "Home" +"</b><br>");
					            personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
					        	}
				        	else if(elp.getUseCd() != null && elp.getUseCd().equals("Mobile Contact") && elp.getCd().equalsIgnoreCase("CP")){
					        	personPhoneprofile.append("<b>"+ "Cell" +"</b><br>");
					            personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
					        	}
				        	else if(elp.getCd() != null){
				        		  String typeCd=CachedDropDowns.getCodeDescTxtForCd(elp.getCd(), "EL_TYPE_TELE_PAT");
				        		  if(typeCd != null && !typeCd.equals(""))
				        		  personPhoneprofile.append("<b>"+ HTMLEncoder.encodeHtml(typeCd) +"</b><br>");
				        		 personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
				        	}
			        	}
			           
			        	if(elp.getTheTeleLocatorDT().getEmailAddress() != null){
			        	personPhoneprofile.append("<b>"+ "Email" +"</b><br>");
			        	personPhoneprofile.append(elp.getTheTeleLocatorDT().getEmailAddress()!=null?elp.getTheTeleLocatorDT().getEmailAddress():"").append("<br>");
			        	}
			        }
			      }
			    }
			  
	 		return personPhoneprofile.toString();
		}

			public String setPersonIds(Object vo, HttpServletRequest request) {
			StringBuffer personIds = new StringBuffer();
			Collection personIdColl = null;
			
			if(vo instanceof PatientSrchResultVO){
				PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
				personIdColl = patVo.getPersonIdColl();
			}
			if(vo instanceof PatientRevisionSrchResultVO){
				PatientRevisionSrchResultVO patVo = (PatientRevisionSrchResultVO)vo;
				personIdColl = patVo.getPersonIdColl();
			}
			
			if (personIdColl != null){
				boolean flag = false;
			     Iterator<Object>  personPersonIdsIte = personIdColl.iterator();
			      while (personPersonIdsIte.hasNext()){
			    	  EntityIdDT ids  = (EntityIdDT) personPersonIdsIte.next();
			    	  if(ids.getRootExtensionTxt()==null || ids.getRootExtensionTxt().trim().length()==0)
			    		  continue;
			    	  NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute(
            		          "NBSSecurityObject");
                	  boolean HIVQuestionPermission = secObj.getPermission(
          					NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS);
                	  if(!HIVQuestionPermission && ids.getTypeCd()!=null && ids.getTypeCd().equals( CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.PARTNER_SERVICES_ID, "EI_TYPE_PAT")))
                		  continue;
			    	  if(flag){
			    		  personIds.append("<br>");	        	    	
		        	    }
			    	  flag = true;
						
						  if(ids.getTypeCd()!=null&&"Social Security".equalsIgnoreCase(ids.getTypeCd())) {
							  
							  personIds=new StringBuffer("<b>"+ids.getTypeCd()+"</b><br>").append(ids.getRootExtensionTxt()!=null?ids.getRootExtensionTxt():"").append("<br>").
									  
									  append(personIds); 
						  
						  }else {
							  personIds.append(ids.getTypeCd()!=null?"<b>"+ids.getTypeCd()+"</b><br>":"");
							  personIds.append(ids.getRootExtensionTxt()!=null?ids.getRootExtensionTxt():"");
						  }
			    	 
			      }
			    }
			
			return personIds.toString();
		}


		
		   private String getStateDescTxt(String sStateCd) {

			      CachedDropDownValues srtValues = new CachedDropDownValues();
			      TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
			      String desc = "";

			      if (sStateCd != null && treemap.get(sStateCd) != null) {
			         desc = (String) treemap.get(sStateCd);

			      }
			      return desc;
			   }
		   
		   private String getCountryDescTxt(String sCountryCd) {

			      CachedDropDownValues srtValues = new CachedDropDownValues();
			     TreeMap<Object,Object> treemap = srtValues.getCountryCodesAsTreeMap();
			      String desc = "";

			      if (sCountryCd != null && treemap.get(sCountryCd) != null) {
			         desc = (String) treemap.get(sCountryCd);

			      }
			      return desc;
			   }
		   
			public ActionForward filterPatientSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
				PersonSearchForm psForm = (PersonSearchForm)form;
				try{	
					Collection<Object>  personVoColl = null;
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSSearchCriteriaMap, psForm.getSearchCriteriaArrayMap());

					String reportType = (String)psForm.getAttributeMap().get("reportType");
					String custom = (String)psForm.getAttributeMap().get("custom");
				 	String queueName =(String)psForm.getAttributeMap().get("queueName");
					
					if(reportType != null && reportType.equalsIgnoreCase("I")){
						personVoColl = this.filterInvs(psForm, request);
						if(custom!=null && custom.equalsIgnoreCase("true"))//Custom queue
							request.setAttribute("PageTitle", queueName);
						else
							request.setAttribute("PageTitle", "Event Search Results");
					}
					else if(reportType != null && (reportType.equalsIgnoreCase("LR") || reportType.equalsIgnoreCase("LMC"))){
						personVoColl = this.filterObservations(psForm, request);
						if(custom!=null && custom.equalsIgnoreCase("true"))//Custom queue
							request.setAttribute("PageTitle", queueName);
						else
							request.setAttribute("PageTitle", "Event Search Results");
					}
				
					else{
						personVoColl = this.filterPatient(psForm, request);
						String pageTitle = psForm.getPageTitle();
				          if(pageTitle!=null && pageTitle.equalsIgnoreCase("Merge Candidate List"))  
				      		request.setAttribute("PageTitle","Merge Candidate List");//ND-27449
				          else
				        	request.setAttribute("PageTitle", "Search Results");
						this.sortPatientLibarary(psForm, personVoColl, true, request);
					}
					if(personVoColl == null){					
						//personVoColl = (ArrayList<Object> ) psForm.getPatientVoCollection();
						personVoColl = (ArrayList<Object> ) NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonList); 
					    
					}
					if(reportType != null && reportType.equalsIgnoreCase("I")){
						this.sortInvs(psForm, personVoColl, true, request);
					}
					else if(reportType != null && (reportType.equalsIgnoreCase("LR") || reportType.equalsIgnoreCase("LMC"))){
						this.sortObservations(psForm, personVoColl, true, request);
					}
					/*else
						this.sortPatientLibarary(psForm, personVoColl, true, request);*/
					request.setAttribute("personList", personVoColl);	
					NBSContext.store(request.getSession() ,NBSConstantUtil.DSPersonList, personVoColl);
					if(personVoColl!= null){
						psForm.getAttributeMap().put("queueCount", String.valueOf(personVoColl.size()));
						request.setAttribute("queueCount", String.valueOf(personVoColl.size()));
						request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));  //Added
						//request.getAttribute("contextAction");
						if("removeFilter".equalsIgnoreCase(request.getParameter("ContextAction")))
							request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
					}else{
						psForm.getAttributeMap().put("queueCount", String.valueOf(0));
						request.setAttribute("queueCount", "0");
					}		
					
					if( request.getParameter("ContextAction")!=null && request.getParameter("ContextAction").equals("filterPatientSubmit") )
						psForm.getAttributeMap().put("PageNumber", "1");
					
				
				}catch(Exception e){
					//throw new ServletException("Error while Filter patient : "+e.getMessage(),e);
				}		
				return PaginationUtil.personPaginate(psForm, request, "searchResultLoad",mapping);
			}
			
			
			@SuppressWarnings("unchecked")
			public Collection<Object>  filterPatient(PersonSearchForm psForm , HttpServletRequest request) throws Exception {

				Collection<Object>  patientList = new ArrayList<Object> ();
				

				String sortOrderParam = null;
				try {

					Map<Object, Object> searchCriteriaMap = psForm.getSearchCriteriaArrayMap();
					ArrayList<Object> patientVoCollection = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonListFull);
					//due form lose vo collection because it comes from other action
					//ArrayList<Object> patientVoCollection = (ArrayList<Object> ) psForm.getPatientVoCollection();
					//psForm.getAttributeMap().get("reportType");
					patientList = getFilteredBySearchText(patientVoCollection, searchCriteriaMap,psForm);
					
					String sortMethod = getSortMethod(request, psForm);
					String direction = getSortDirection(request, psForm);
					if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
						Map<Object, Object> sColl =  psForm.getAttributeMap().get("searchCriteria") == null ? 
										new TreeMap<Object, Object>() :(TreeMap<Object, Object>) psForm.getAttributeMap().get("searchCriteria");
						sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
					} else {
						sortOrderParam = getSortPatient(direction, sortMethod);
					}
					if(sortOrderParam != null && sortOrderParam.trim().length()==0){
						sortOrderParam = "Name @ in ascending order" ;
					}
					Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
					String srchCriteriaFullNm = null;
					String srchCriteriaTelePhone = null;
					String srchCriteriaPatientId = null;
					String srchCriteriaAge = null;
					String srchCriteriaAddress = null;
					String srchCriteriaPatient = null;
					//set the error message to the form
					if(sortOrderParam != null)
						searchCriteriaColl.put("sortSt", sortOrderParam);

					if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
						srchCriteriaFullNm = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
						searchCriteriaColl.put("fulNameSearch", srchCriteriaFullNm);
					}
					if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
						srchCriteriaTelePhone = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
						searchCriteriaColl.put("telePhoneSearch", srchCriteriaTelePhone);
					}
					if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
						srchCriteriaTelePhone = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
						searchCriteriaColl.put("idSearch", srchCriteriaTelePhone);
					}
					if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
						srchCriteriaPatientId = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
						searchCriteriaColl.put("patientIdSearch", srchCriteriaPatientId);
					}
					if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
						srchCriteriaAge = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
						searchCriteriaColl.put("ageSearch", srchCriteriaAge);
					}
					if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
						srchCriteriaAddress = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
						searchCriteriaColl.put("addressSearch", srchCriteriaAddress);
					}
					if(searchCriteriaMap.get("Patient_FILTER_TEXT")!=null){
						srchCriteriaPatient = (String) searchCriteriaMap.get("Patient_FILTER_TEXT");
						searchCriteriaColl.put("patient", srchCriteriaPatient);
					}
					psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
					

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error while filtering the filterQuestionLib: "+ e.toString());
					throw new Exception(e.getMessage());

				}
				return patientList;
			}
			
			@SuppressWarnings("unchecked")
			public Collection<Object>  filterInvs(PersonSearchForm psForm , HttpServletRequest request) {

				Collection<Object>  investigationSummaryVOs = new ArrayList<Object> ();
				
				String srchCriteriaInv = null;
				String srchCriteriaJuris = null;
				String srchCriteriaCond = null;
				String srchCriteriaStatus = null;
				String sortOrderParam = null;
				String srchCriteriaDate = null;
				String srchCriteriaNotif = null;
				
				try {
					
					Map<Object,Object> searchCriteriaMap = psForm.getSearchCriteriaArrayMap();
					// Get the existing SummaryVO collection in the form
					ArrayList<Object> invSummaryVOs = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonListFull);
					
					// Filter by the investigator
					investigationSummaryVOs = getFilteredInvestigation(invSummaryVOs, searchCriteriaMap);

					String[] inv = (String[]) searchCriteriaMap.get("INVESTIGATOR");
					String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
					String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
					String[] status = (String[]) searchCriteriaMap.get("CASESTATUS");
					String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
					String[] notif = (String[]) searchCriteriaMap.get("NOTIFICATION");
					
					String filterPatient = null;
					if(searchCriteriaMap.get("Patient_FILTER_TEXT")!=null){
						filterPatient = (String) searchCriteriaMap.get("Patient_FILTER_TEXT");
						request.setAttribute("PATIENT", filterPatient);
					}
					
					String filterInvestgationId=null;
					if(searchCriteriaMap.get("LocalIdSearchInv_FILTER_TEXT")!=null){
						filterInvestgationId = (String) searchCriteriaMap.get("LocalIdSearchInv_FILTER_TEXT");
						request.setAttribute("InvestigationID", filterInvestgationId);
					}
					
					Integer invCount = new Integer(inv == null ? 0 : inv.length);
					Integer jurisCount = new Integer(juris == null  ? 0 : juris.length);
					Integer condCount = new Integer(cond == null  ? 0 : cond.length);
					Integer statusCount = new Integer(status == null  ? 0 : status.length);
					Integer startDateCount = new Integer(startDate == null ? 0 : startDate.length);
					Integer notifCount = new Integer(notif == null ? 0 : notif.length);
					
					// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
					if(invCount.equals((psForm.getAttributeMap().get("InvestigatorsCount"))) &&
							(jurisCount.equals(psForm.getAttributeMap().get("JurisdictionsCount"))) && 
							(condCount.equals(psForm.getAttributeMap().get("ConditionsCount"))) &&
							(statusCount.equals(psForm.getAttributeMap().get("caseStatusCount"))) &&
							(startDateCount.equals(psForm.getAttributeMap().get("dateFilterListCount"))) &&
							(notifCount.equals(psForm.getAttributeMap().get("notificationsCount"))) &&
							filterPatient == null && filterInvestgationId == null) {
						
						String sortMethod = getSortMethod(request, psForm);
						String direction = getSortDirection(request, psForm);		
					
						//Make the A-Z icons consistent during sorting and filtering 
						if(direction == null || (direction != null && direction.equals("1")))
							direction = "2";
						else if(direction != null && direction.equals("2"))
							direction = "1";
						
						if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
							Map<Object,Object> sColl =  psForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) psForm.getAttributeMap().get("searchCriteria");
							sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
						} else {
							sortOrderParam = getSortPatient(direction, sortMethod);
						}	
						
						if("getLocalId".equalsIgnoreCase(sortMethod)) sortOrderParam = sortOrderParam.replace("Local ID", "Investigation ID");
						Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
						searchCriteriaColl.put("sortSt", sortOrderParam);
						psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
						return investigationSummaryVOs;			
					}
								
					
					ArrayList<Object> invList = (psForm.getInvestigators().size() != 0) ? psForm.getInvestigators():(ArrayList<Object>)psForm.getAttributeMap().get("investigatorDDList");
					ArrayList<Object> jurisList = (psForm.getJurisdictionsDD().size() != 0) ? psForm.getJurisdictionsDD():(ArrayList<Object>)psForm.getAttributeMap().get("jurisdictionDDList");
					ArrayList<Object> condList = (psForm.getConditions().size() != 0)? psForm.getConditions():(ArrayList<Object>)psForm.getAttributeMap().get("conditionDDList");
					ArrayList<Object> statusList = (psForm.getCaseStatusesDD().size() != 0)? psForm.getCaseStatusesDD():(ArrayList<Object>)psForm.getAttributeMap().get("CaseStatusDDList");
					ArrayList<Object> dateList = (psForm.getStartDateDropDowns().size() != 0)? psForm.getStartDateDropDowns():(ArrayList<Object>)psForm.getAttributeMap().get("startDateDDList");
					ArrayList<Object> notifList = (psForm.getNotifications().size() != 0)? psForm.getNotifications():(ArrayList<Object>)psForm.getAttributeMap().get("notificationDDList");
					
					Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
					String sortMethod = getSortMethod(request, psForm);
					String direction = getSortDirection(request, psForm);	
					
					//Make the A-Z icons consistent during sorting and filtering 
					if(direction == null || (direction != null && direction.equals("1")))
						direction = "2";
					else if(direction != null && direction.equals("2"))
						direction = "1";
					
					if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
						Map<Object,Object> sColl =  psForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) psForm.getAttributeMap().get("searchCriteria");
						sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
					} else {
						sortOrderParam = getSortPatient(direction, sortMethod);
					}
					if(sortOrderParam != null && sortOrderParam.trim().length()==0){
						sortOrderParam = "Start Date @ in descending order" ;
					}
										
					if("getLocalId".equalsIgnoreCase(sortMethod)) sortOrderParam = sortOrderParam.replace("Local ID", "Investigation ID");
				
					srchCriteriaInv = queueUtil.getSearchCriteria(invList, inv, NEDSSConstants.FILTERBYINVESTIGATOR);
					srchCriteriaJuris = queueUtil.getSearchCriteria(jurisList, juris, NEDSSConstants.FILTERBYJURISDICTION);
					srchCriteriaCond = queueUtil.getSearchCriteria(condList, cond, NEDSSConstants.FILTERBYCONDITION);
					srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSTATUS);
					srchCriteriaDate = queueUtil.getSearchCriteria(dateList, startDate, NEDSSConstants.FILTERBYDATE);
					srchCriteriaNotif = queueUtil.getSearchCriteria(notifList, notif, NEDSSConstants.FILTERBYNOTIF);

					//set the error message to the form
					if(sortOrderParam != null)
						searchCriteriaColl.put("sortSt", sortOrderParam);
					if(srchCriteriaDate != null)
						searchCriteriaColl.put("INV147", srchCriteriaDate);
					if(srchCriteriaInv != null)
						searchCriteriaColl.put("INV100", srchCriteriaInv);
					if(srchCriteriaJuris != null)
						searchCriteriaColl.put("INV107", srchCriteriaJuris);
					if(srchCriteriaCond != null)
						searchCriteriaColl.put("INV169", srchCriteriaCond);
					if(srchCriteriaStatus != null)
						searchCriteriaColl.put("INV163", srchCriteriaStatus);
					if(srchCriteriaNotif != null)
						searchCriteriaColl.put("NOT118", srchCriteriaNotif);
					if(filterPatient != null){
						searchCriteriaColl.put("PATIENT", filterPatient);
						request.setAttribute("PATIENT", filterPatient);
					}
					
					if(filterInvestgationId != null) {
						searchCriteriaColl.put("LOCALID", filterInvestgationId);
						request.setAttribute("InvestigationID", filterInvestgationId);
					}
					psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error while filtering the investigation by Investigator: "+ e.toString());
					
				} 
				return investigationSummaryVOs;
			}
			public Collection<Object>  getFilteredBySearchText(Collection<Object>  patientVoCollection,
					Map<Object, Object> searchCriteriaMap,PersonSearchForm psForm ) throws Exception{

		      try{
		    	
				String filterByFullNameText = null;
				String filterByTelephoneText = null;
				String filterByIdText = null;
				String filterByPatientIdText = null;
				String filterByAgeText = null;
				String filterByAddressText = null;
				String filterByPatientName = null;
				if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
					filterByFullNameText = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
					filterByTelephoneText = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
					filterByIdText = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
					filterByPatientIdText = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
					filterByAgeText = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
					filterByAddressText = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("Patient_FILTER_TEXT")!=null){
					filterByPatientName = (String) searchCriteriaMap.get("Patient_FILTER_TEXT");
				}
			    try{
				
					/**
					 * Following methods are helping for page sorting
					 */
					
				
					if(filterByFullNameText!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByFullNameText, "Name");
					}
					if(filterByTelephoneText!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByTelephoneText, "Phone/Email");
					}
					if(filterByIdText!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByIdText, "Id");
					}
					if(filterByPatientIdText!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByPatientIdText, "Patient ID");
					}
					if(filterByAgeText!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByAgeText, "Age/DOB/Sex");
					}
					if(filterByAddressText!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByAddressText, "Address");
					}
					if(filterByPatientName!= null){
						patientVoCollection = this.filterByText(patientVoCollection, filterByPatientName, "Patient ");
					}
			       }catch (Exception e) {
						e.printStackTrace();
						logger.error("Error while filtering the getFiltered: "+ e.toString());
						throw new Exception(e.getMessage());

			        }
		        }catch (Exception e) {
					e.printStackTrace();
					logger.error("Error while filtering the getFiltered: "+ e.toString());
					throw new Exception(e.getMessage());

				}

				return patientVoCollection;

			}
			public Collection<Object>  getFilteredInvestigation(Collection<Object> invSummaryVOs,
					Map<Object, Object>  searchCriteriaMap) {
				
		    	String[] inv = (String[]) searchCriteriaMap.get("INVESTIGATOR"); //Initial Search Criteria  and filtered criteria
				String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
				String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
				String[] status = (String[]) searchCriteriaMap.get("CASESTATUS");
				String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
				String[] notif = (String[]) searchCriteriaMap.get("NOTIFICATION");
				
				String filterPatient = null;
				if(searchCriteriaMap.get("Patient_FILTER_TEXT")!=null){
					filterPatient = (String) searchCriteriaMap.get("Patient_FILTER_TEXT");
				}
				
				String filterInvestgationId=null;
				if(searchCriteriaMap.get("LocalIdSearchInv_FILTER_TEXT")!=null){
					filterInvestgationId = (String) searchCriteriaMap.get("LocalIdSearchInv_FILTER_TEXT");
				}
				
				Map<Object, Object>  invMap = new HashMap<Object,Object>();
				Map<Object, Object>  jurisMap = new HashMap<Object,Object>();
				Map<Object, Object>  condMap = new HashMap<Object,Object>();
				Map<Object, Object>  statusMap = new HashMap<Object,Object>();
				Map<Object, Object>  dateMap = new HashMap<Object,Object>();
				Map<Object, Object>  notifMap = new HashMap<Object,Object>();
				//Map<Object, Object>  invStatusMap = new HashMap<Object,Object>();
				
				if (inv != null && inv.length > 0)
					invMap = queueUtil.getMapFromStringArray(inv);
				if (juris != null && juris.length > 0)
					jurisMap = queueUtil.getMapFromStringArray(juris);
				if (cond != null && cond.length > 0)
					condMap = queueUtil.getMapFromStringArray(cond);
				if (status != null && status.length > 0)
					statusMap = queueUtil.getMapFromStringArray(status);
				if (startDate != null && startDate.length >0)
					dateMap = queueUtil.getMapFromStringArray(startDate);
				if (notif != null && notif.length >0)
					notifMap = queueUtil.getMapFromStringArray(notif);
				try {		
				
				if(invMap != null && invMap.size()>0)
					invSummaryVOs = filterInvestigationsbyInvestigator(
						invSummaryVOs, invMap);
				if (jurisMap != null && jurisMap.size()>0)
					invSummaryVOs = filterInvestigationsbyJurisdiction(
						invSummaryVOs, jurisMap);
				if (condMap != null && condMap.size()>0)
					invSummaryVOs = filterInvestigationsbyCondition(
						invSummaryVOs, condMap);
				if (statusMap != null && statusMap.size()>0)
					invSummaryVOs = filterInvestigationsbyCaseStatus(
						invSummaryVOs, statusMap);
				if(dateMap != null && dateMap.size()>0)
					invSummaryVOs = filterInvestigationsbyStartdate(invSummaryVOs,dateMap);
				if(notifMap != null && notifMap.size()>0)
					invSummaryVOs = filterInvestigationsbyNotifRecordStatus(invSummaryVOs,notifMap);
				if(filterPatient!= null)
					invSummaryVOs = filterByText(invSummaryVOs, filterPatient, NEDSSConstants.INV_PATIENT);
				
				if(filterInvestgationId != null) 
					invSummaryVOs = filterByText(invSummaryVOs, filterInvestgationId, NEDSSConstants.INV_LOCAL_ID);
		
				
				}
				catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			
				
				return invSummaryVOs;
				
			}

			
			
			public Collection<Object>  filterInvestigationsbyInvestigator(
					Collection<Object>  invSummaryVOs, Map<Object,Object> investgatorMap) {
				Collection<Object>  newInvColl = new ArrayList<Object> ();
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getInvestigator() != null
								&& investgatorMap != null
								&& investgatorMap.containsKey(invSummaryVO
										.getInvestigator())) {
							newInvColl.add(invSummaryVO);
						}
						if(invSummaryVO.getInvestigator() == null || invSummaryVO.getInvestigator().trim().equals("")){
							if(investgatorMap != null && investgatorMap.containsKey(NEDSSConstants.BLANK_KEY)){
								newInvColl.add(invSummaryVO);
							}
						}

					}

				}
				return newInvColl;

			}

			public Collection<Object>  filterInvestigationsbyJurisdiction(
					Collection<Object>  invSummaryVOs, Map<Object,Object> jurisMap) {
				Collection<Object>  newInvColl = new ArrayList<Object> ();
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getJurisdictionCd() != null
								&& jurisMap != null
								&& jurisMap.containsKey(invSummaryVO
										.getJurisdictionCd())) {
							newInvColl.add(invSummaryVO);
						}

					}

				}
				return newInvColl;

			}

			public Collection<Object>  filterInvestigationsbyCaseStatus(
					Collection<Object>  invSummaryVOs, Map<Object,Object> statusMap) {
				Collection<Object>  newInvColl = new ArrayList<Object> ();
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getCaseStatusCd() != null && statusMap != null
								&& statusMap.containsKey(invSummaryVO.getCaseStatusCd())) {
							newInvColl.add(invSummaryVO);
						}
						if(invSummaryVO.getCaseStatusCd() == null || invSummaryVO.getCaseStatusCd().trim().equals("")){
							if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
								newInvColl.add(invSummaryVO);
							}
						}

					}

				}
				return newInvColl;

			}
			
			public Collection<Object>  filterInvestigationsbyStartdate(Collection<Object> invSummaryVOs, Map<Object, Object> dateMap) {
				Map<Object, Object>  newInvMap = new HashMap<Object,Object>();
				String strDateKey = null;
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getStartDate() != null && dateMap != null
								&& (dateMap.size()>0 )) {
							Collection<Object>  dateSet = dateMap.keySet();
							if(dateSet != null){
								Iterator<Object>  iSet = dateSet.iterator();
							while (iSet.hasNext()){
								 strDateKey = (String)iSet.next();
								if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
		                    	   if(queueUtil.isDateinRange(invSummaryVO.getStartDate(),strDateKey)){
		                    		   newInvMap.put(invSummaryVO.getPublicHealthCaseUid(), invSummaryVO);
		                    	   }	
		                           		
								}  
		                       }
							  }
							}
				
						if(invSummaryVO.getStartDate() == null || invSummaryVO.getStartDate().equals("")){
							if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
								 newInvMap.put(invSummaryVO.getPublicHealthCaseUid(), invSummaryVO);
							}
						}

					}
				} 	

				
				return convertInvMaptoColl(newInvMap);

			}
			
			public Collection<Object>  filterInvestigationsbyStartdateForLab(Collection<Object> invSummaryVOs, Map<Object, Object> dateMap) {
				Map<Object, Object>  newInvMap = new HashMap<Object,Object>();
				String strDateKey = null;
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getStartDate() != null && dateMap != null
								&& (dateMap.size()>0 )) {
							Collection<Object>  dateSet = dateMap.keySet();
							if(dateSet != null){
								Iterator<Object>  iSet = dateSet.iterator();
							while (iSet.hasNext()){
								 strDateKey = (String)iSet.next();
								if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
		                    	   if(queueUtil.isDateinRange(invSummaryVO.getStartDate(),strDateKey)){
		                    		   newInvMap.put(invSummaryVO.getLocalId(), invSummaryVO);
		                    	   }	
		                           		
								}  
		                       }
							  }
							}
				
						if(invSummaryVO.getStartDate() == null || invSummaryVO.getStartDate().equals("")){
							if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
								 newInvMap.put(invSummaryVO.getLocalId(), invSummaryVO);
							}
						}

					}
				} 	

				
				return convertInvMaptoColl(newInvMap);

			}
			public Collection<Object>  filterObservationsbyDescription(
					Collection<Object>  observationReviewColl, Map<Object,Object> testMap) {
				Collection<Object>  newObsColl = new ArrayList<Object> ();
				
				if (observationReviewColl != null) {
					Iterator<Object>  iter = observationReviewColl.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO obsSummaryVO = (PatientSrchResultVO)iter.next();
						if (obsSummaryVO.getDescriptions()!= null
								&& testMap != null){
						
							
							boolean found = false;
							for(int i=0; i<obsSummaryVO.getDescriptions().size() && !found;i++){
							Map<Object, Object>  rtestMap = new HashMap<Object,Object>();
							rtestMap = getTestsfromObs(obsSummaryVO.getDescriptions().get(i),rtestMap);
							Collection<Object>  keyColl = rtestMap.keySet();	
							if(keyColl != null){
								Iterator<Object>  it = keyColl.iterator();
								
								
								while(it.hasNext()){
									String test = (String)it.next();
									if(testMap.containsKey(test)){
										newObsColl.add(obsSummaryVO);
										found=true;
										break;
									}
								}
							 
							}
							}
							
						}if(obsSummaryVO.getDescriptions()==null || obsSummaryVO.getDescriptions().size() == 0){
							if(testMap != null && testMap.containsKey(NEDSSConstants.BLANK_KEY)){
								newObsColl.add(obsSummaryVO);
							}
						}
					}

				}
				return newObsColl;

			}
			public Collection<Object>  filterObservationsbyType(
					Collection<Object>  observationReviewColl, Map<Object,Object> typeMap) {
				Collection<Object>  newObsColl = new ArrayList<Object> ();
				if (observationReviewColl != null) {
					Iterator<Object>  iter = observationReviewColl.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO obsSummaryVO = (PatientSrchResultVO)iter.next();
						if (obsSummaryVO.getDocumentTypeNoLnk()!= null
								&& typeMap != null
								&& typeMap.containsKey(obsSummaryVO
										.getDocumentTypeNoLnk())) {
							newObsColl.add(obsSummaryVO);
						}
						if(obsSummaryVO.getDocumentTypeNoLnk() == null || obsSummaryVO.getDocumentTypeNoLnk().equals("")){
							if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
								newObsColl.add(obsSummaryVO);
							}
						}

					}

				}
				return newObsColl;

			}
			private Map<Object,Object> getTestsfromObs(String tests, Map<Object,Object> rTestMap){
				String[] strArr = tests.split("<BR>");

				if(strArr != null){ 
					for(int i=0;i<strArr.length;i++){
						rTestMap.put(strArr[i],strArr[i]);
					}
				}
				return rTestMap;
			}
			public Collection<Object>  filterObservationsbyTestConditions(
					Collection<Object>  observationReviewColl, Map<Object,Object> testMap) {
				Collection<Object>  newObsColl = new ArrayList<Object> ();
				
				if (observationReviewColl != null) {
					Iterator<Object>  iter = observationReviewColl.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO obsSummaryVO = (PatientSrchResultVO)iter.next();
						if (obsSummaryVO.getConditions()!= null
								&& testMap != null){
							
							for(int i=0; i<obsSummaryVO.getConditions().size();i++){
							Map<Object, Object>  rtestMap = new HashMap<Object,Object>();
							rtestMap = getTestsfromObs(obsSummaryVO.getConditions().get(i),rtestMap);
							Collection<Object>  keyColl = rtestMap.keySet();	
							if(keyColl != null){
								Iterator<Object>  it = keyColl.iterator();
								while(it.hasNext()){
									String test = (String)it.next();
									if(testMap.containsKey(test)){
										if(!newObsColl.contains(obsSummaryVO)) //Added
											newObsColl.add(obsSummaryVO);
										break;
									}
								}
							 
							}
							}
							
						}if(obsSummaryVO.getConditions()==null || obsSummaryVO.getConditions().size() == 0){
							if(testMap != null && testMap.containsKey(NEDSSConstants.BLANK_KEY)){
								newObsColl.add(obsSummaryVO);
							}
						}
					}

				}
				return newObsColl;

			}
			
			
		   private Collection<Object>  convertInvMaptoColl(Map<Object, Object>  invMap){
			   Collection<Object>  invColl = new ArrayList<Object> ();
			   if(invMap !=null && invMap.size()>0){
				   Collection<Object>  invKeyColl = invMap.keySet();
				  Iterator<Object>  iter = invKeyColl.iterator();
				   while(iter.hasNext()){
					   String invKey = (String)iter.next();
					   invColl.add(invMap.get(invKey));
				   }
			   }
			   return invColl;
		   }

			public Collection<Object>  filterInvestigationsbyCondition(Collection<Object> invSummaryVOs,
					Map<Object, Object>  conditionMap) {
				Collection<Object>  newInvColl = new ArrayList<Object> ();
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getCd() != null && conditionMap != null
								&& conditionMap.containsKey(invSummaryVO.getCd())) {
							newInvColl.add(invSummaryVO);
						}

					}

				}
				return newInvColl;

			}

			/**
			 * Filters Queue Collection<Object>  by Notification RecordStatusCd
			 * @param invSummaryVOs
			 * @param notifRecStatMap
			 * @return
			 */
			public Collection<Object>  filterInvestigationsbyNotifRecordStatus(Collection<Object> invSummaryVOs, Map<Object,Object> notifRecStatMap) {
				Collection<Object>  newInvColl = new ArrayList<Object> ();
				if (invSummaryVOs != null) {
					Iterator<Object>  iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						String notifRecStatusCd = invSummaryVO.getNotificationCd();
						if (notifRecStatusCd != null && notifRecStatMap != null && notifRecStatMap.containsKey(notifRecStatusCd)) {
							newInvColl.add(invSummaryVO);
						}
						if(notifRecStatusCd == null || (notifRecStatusCd != null && notifRecStatusCd.trim().equals(""))){
							if(notifRecStatMap != null && notifRecStatMap.containsKey(NEDSSConstants.BLANK_KEY)){
								newInvColl.add(invSummaryVO);
							}
						}				
					}
				}		
				return newInvColl;
			}	
			public String getSortPatient(String sortOrder, String methodName)throws Exception{
				try{
				String sortOrdrStr = null;
				if(methodName != null) {
					if(methodName.equals("getPersonFullName"))
						sortOrdrStr = "Name";
					else if(methodName.equals("getPersonPhoneprofile"))
						sortOrdrStr = "Phone/Email";
					else if(methodName.equals("getViewFile"))
						sortOrdrStr = "Patient ID";
					else if(methodName.equals("getPersonIds"))
						sortOrdrStr = "ID";
					else if(methodName.equals("getPersonAddress"))
						sortOrdrStr = "Address";
					else if(methodName.equals("getProfile"))
						sortOrdrStr = "Age/DOB/Sex";
					
					else if(methodName.equals("getStartDate")){
					
						sortOrdrStr = "Start Date";
					}
					else if(methodName.equals("getInvestigator"))
						sortOrdrStr = "Investigator";
					else if(methodName.equals("getPersonFullNameNoLink"))
						sortOrdrStr = "Patient";
					else if(methodName.equals("getJurisdiction"))
						sortOrdrStr = "Jurisdiction";
					else if(methodName.equals("getCondition"))
						sortOrdrStr = "Condition";
					else if(methodName.equals("getCaseStatus"))
						sortOrdrStr = "Case Status";
					else if(methodName.equals("getNotification"))
						sortOrdrStr = "Notification";
					
					
					else if(methodName.equals("getDocumentTypeNoLnk"))
						sortOrdrStr = "Document Type";
					else if(methodName.equals("getReportingFacilityProvider"))
						sortOrdrStr = "Reporting Facility/Provider";
					else if(methodName.equals("getDescriptionPrint"))
						sortOrdrStr = "Description";
					else if(methodName.equals("getTestsStringNoLnk"))
						sortOrdrStr = "Associated With";
					else if(methodName.equals("getLocalId"))
						sortOrdrStr = "Local ID";
					
				} else {
				
						sortOrdrStr = "Name";
				}

				if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
					sortOrdrStr = sortOrdrStr+" @ in ascending order ";
				else if(sortOrder != null && sortOrder.equals("2"))
					sortOrdrStr = sortOrdrStr+" @ in descending order ";

				return sortOrdrStr;
				}catch (Exception ex) {
					logger.fatal("Error in getSortPageLibrary in Action Util: ", ex);
					throw new Exception(ex.toString());

				}

			}
			
			public void sortInvs(PersonSearchForm psForm, Collection<Object>  investigationList, boolean existing, HttpServletRequest request) throws Exception {
				PropertyUtil properties = PropertyUtil.getInstance();
				// Retrieve sort-order and sort-direction from displaytag params
				String sortMethod = getSortMethod(request, psForm);
				String direction = getSortDirection(request, psForm);

				boolean invDirectionFlag = true;
				if (direction != null && direction.equals("2")){
					invDirectionFlag = false;
					
				}
					
				
			
				//Read from properties file to determine default sort order
				if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					sortMethod = "getStartDate";
					invDirectionFlag = false;
						//invDirectionFlag = !properties.getMyProgramAreasQueueSortbyNewestInvStartdate();
				}else
					invDirectionFlag =!invDirectionFlag;
				
			
				
				NedssUtils util = new NedssUtils();
				if (sortMethod != null && investigationList != null
						&& investigationList.size() > 0) {
					updateInvestigationSummaryVObeforeSort(investigationList,sortMethod);
					util.sortObjectByColumn(sortMethod,
							(Collection<Object>) investigationList, invDirectionFlag);
				
					_updateSummaryVosForDate(investigationList);
					updateInvestigationSummaryVOAfterSort(investigationList);
					
				}
				
				if(!existing) {
					//Finally put sort criteria in form
					String sortOrderParam = this.getSortPatient(invDirectionFlag == true ? "1" : "2", sortMethod);
					if("getLocalId".equalsIgnoreCase(sortMethod)) sortOrderParam = sortOrderParam.replace("Local ID", "Investigation ID");
					Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
					searchCriteriaColl.put("sortSt", sortOrderParam);
					psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				}

			}
			public void sortPatientLibarary(PersonSearchForm psForm, Collection<Object>  patientList, boolean existing, HttpServletRequest request) throws Exception {

				// Retrieve sort-order and sort-direction from display tag parameters
				String sortMethod = getSortMethod(request, psForm);
				String direction = getSortDirection(request, psForm);
				String reportType = (String) psForm.getAttributeMap().get("reportType");
				
				boolean invDirectionFlag = true;
				if (direction != null && direction.equals("2"))
					invDirectionFlag = false;

				NedssUtils util = new NedssUtils();
			
				 if(reportType!= null && reportType.equalsIgnoreCase("I")){
					//Read from properties file to determine default sort order
					if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
						sortMethod = "getStartDate";
						invDirectionFlag = false;
					}
				}
				 else {
						//Read from properties file to determine default sort order
						if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
							String mode = (String)request.getAttribute("Mode1");  // 	String mode = request.getParameter("Mode1");
							
							if(mode==null || (mode!=null && mode.isEmpty()))
								mode = request.getParameter("Mode1");//In case it's Manual Merge
							
							if(mode!=null && (mode.equalsIgnoreCase("SystemIdentified") || mode.equalsIgnoreCase("ManualMerge"))){//ND-27654 (Sorting by Patient ID column)
								sortMethod = "getViewFile";
							
								
							}
							else//Default
								sortMethod="getPersonFullName";
							invDirectionFlag = true;
						}
					}
				if (sortMethod != null && patientList != null && patientList.size() > 0) {
					
					util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) patientList, invDirectionFlag);
					
				}
				
				String mode = (String)request.getAttribute("Mode1"); 
				String contextAction = (String)request.getAttribute("contextAction"); 
				
				//That way, we make sure to return the filter to Patient ID column if the workflow is Manual Merge or System Identified and Remove all links was just clicked.
				if(mode!=null && (mode.equalsIgnoreCase("SystemIdentified")|| mode.equalsIgnoreCase("ManualMerge")) &&
						contextAction!=null && (contextAction.equalsIgnoreCase("removeFilter")))
								existing = false;
				
				if(!existing) {
					//Finally put sort criteria in form
					String sortOrderParam = this.getSortPatient(invDirectionFlag == true ? "1" : "2", sortMethod);
					Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
					searchCriteriaColl.put("sortSt", sortOrderParam);
					psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				}
			}
			
			public Collection<Object>  getFilteredObservation(Collection<Object>  obsSummaryVOs,
					Map<Object, Object>  searchCriteriaMap) {
				
		    	
				String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
				String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
				String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
				String[] obsType = (String[]) searchCriteriaMap.get("OBSERVATIONTYPE");
				String[] prgArea = (String[]) searchCriteriaMap.get("PROGRAMAREA");
				String[] des = (String[]) searchCriteriaMap.get("DESCRIPTION");
				String filterPatient = null;
				if(searchCriteriaMap.get("PatientSearchText_FILTER_TEXT")!=null){
					filterPatient = (String) searchCriteriaMap.get("PatientSearchText_FILTER_TEXT");
				}
				String filterLocalId = null;
				if(searchCriteriaMap.get("LocalIdSearchText_FILTER_TEXT")!=null){
					filterLocalId = (String) searchCriteriaMap.get("LocalIdSearchText_FILTER_TEXT");
				}
				String filterProviderReportingFacility = null;
				if(searchCriteriaMap.get("Provider_FILTER_TEXT")!=null){
					filterProviderReportingFacility = (String) searchCriteriaMap.get("Provider_FILTER_TEXT");
				}
				
				/*String filterDescription = null;
				if(searchCriteriaMap.get("Description_FILTER_TEXT")!=null){
					filterDescription = (String) searchCriteriaMap.get("Description_FILTER_TEXT");
				
				}*/
				
				Map<Object, Object>  jurisMap = new HashMap<Object,Object>();
				Map<Object, Object>  condMap = new HashMap<Object,Object>();
				Map<Object, Object>  dateMap = new HashMap<Object,Object>();
				Map<Object, Object>  obsTypeMap = new HashMap<Object,Object>();
				Map<Object, Object>  prgAreaMap = new HashMap<Object,Object>();
				Map<Object, Object>  desMap = new HashMap<Object,Object>();
				
				if (juris != null && juris.length > 0)
					jurisMap = queueUtil.getMapFromStringArray(juris);
				if (cond != null && cond.length > 0)
					condMap = queueUtil.getMapFromStringArray(cond);
				if (startDate != null && startDate.length >0)
					dateMap = queueUtil.getMapFromStringArray(startDate);
				if (obsType != null && obsType.length >0)
					obsTypeMap = queueUtil.getMapFromStringArray(obsType);
				if (prgArea != null && prgArea.length >0)
					prgAreaMap = queueUtil.getMapFromStringArray(prgArea);
				if (des != null && des.length > 0)
					desMap = queueUtil.getMapFromStringArray(des);
				
			
				if (jurisMap != null && jurisMap.size()>0)
					obsSummaryVOs = filterInvestigationsbyJurisdiction(obsSummaryVOs, jurisMap);
				
				if(dateMap != null && dateMap.size()>0)
					obsSummaryVOs = filterInvestigationsbyStartdateForLab(obsSummaryVOs,dateMap);
				/*if(obsTypeMap != null && obsTypeMap.size()>0)
					obsSummaryVOs = filterObservationsbyType(obsSummaryVOs,obsTypeMap);
				*/
				if(condMap != null && condMap.size()>0)
					obsSummaryVOs = filterObservationsbyTestConditions(obsSummaryVOs,condMap);
				try {
				
					if(filterPatient!= null){
							obsSummaryVOs = filterByText(obsSummaryVOs, filterPatient, NEDSSConstants.INV_PATIENT);
					}
					if(filterLocalId!= null){
						obsSummaryVOs = filterByText(obsSummaryVOs, filterLocalId, NEDSSConstants.INV_LOCAL_ID);
					}
					if(filterProviderReportingFacility!= null){
						obsSummaryVOs = filterByText(obsSummaryVOs, filterProviderReportingFacility, NEDSSConstants.INV_PROVIDER);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(desMap != null && desMap.size()>0)
					obsSummaryVOs = filterObservationsbyDescription(obsSummaryVOs,desMap);
			
//				if(filterDescription!= null){
//					obsSummaryVOs = filterByText(obsSummaryVOs, filterDescription, NEDSSConstants.INV_DESCRIPTION);
//				}
				
				return obsSummaryVOs;
				
			}
			
			@SuppressWarnings("unchecked")
			public Collection<Object>  filterObservations(PersonSearchForm psForm, HttpServletRequest request) {
	    		
	    		Collection<Object>  observationSummaryVOs = new ArrayList<Object> ();
	    		
	    		String srchCriteriaDate = null;
	    		String srchObsType = null;
	    		String srchCriteriaJuris = null;
	    		String sortOrderParam = null;
	    		String srchCriteriaCond = null;
	    		String srchCriteriaDes = null;
	    		
	    		try {
	    			
	    			Map<Object,Object> searchCriteriaMap = psForm.getSearchCriteriaArrayMap();
					// Get the existing SummaryVO collection in the form
					ArrayList<Object> obsSummaryVOs = ((ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonListFull));
					
	    			
	    			// Filter by the investigator
	    			observationSummaryVOs = this.getFilteredObservation(obsSummaryVOs, searchCriteriaMap);

	    			
	    			String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
	    			String[] cond = (String[]) searchCriteriaMap.get("CONDITION"); //Associated with 
	    			String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
	    			String[] observationType = (String[]) searchCriteriaMap.get("OBSERVATIONTYPE"); 
	    			String[] des = (String[]) searchCriteriaMap.get("DESCRIPTION");//Description Condition
	    			String filterPatient = null;
	    			if(searchCriteriaMap.get("PatientSearchText_FILTER_TEXT")!=null){
	    				filterPatient = (String) searchCriteriaMap.get("PatientSearchText_FILTER_TEXT");
	    				request.setAttribute("PATIENT", filterPatient);
	    			}
	    			String filterLocalId = null;
	    			if(searchCriteriaMap.get("LocalIdSearchText_FILTER_TEXT")!=null){
	    				filterLocalId = (String) searchCriteriaMap.get("LocalIdSearchText_FILTER_TEXT");
	    				request.setAttribute("LOCALID", filterLocalId);
	    			}
	    			String filterProviderReportingFacility = null;
	    			if(searchCriteriaMap.get("Provider_FILTER_TEXT")!=null){
	    				filterProviderReportingFacility = (String) searchCriteriaMap.get("Provider_FILTER_TEXT");
	    				request.setAttribute("PROVIDER", filterProviderReportingFacility);
	    			}
	    			
	    			/*String filterDescription = null;
	    			if(searchCriteriaMap.get("Description_FILTER_TEXT")!=null){
	    				filterDescription = (String) searchCriteriaMap.get("Description_FILTER_TEXT");
	    				request.setAttribute("DESCRIPTION", filterDescription);
	    			
	    			}*/
	    			
	    			Integer jurisCount = new Integer(juris == null  ? 0 : juris.length);
	    			Integer condCount = new Integer(cond == null  ? 0 : cond.length);
	    			Integer startDateCount = new Integer(startDate == null  ? 0 : startDate.length);
	    			Integer observationTypeCount = new Integer(observationType == null  ? 0 : observationType.length);
	    			Integer desCount = new Integer(des == null  ? 0 : des.length);
	    			
	    			if((jurisCount.equals(psForm.getAttributeMap().get("JurisdictionsCount"))) &&
	    			(condCount.equals(psForm.getAttributeMap().get("ConditionsDDCount"))) &&
	    			(startDateCount.equals(psForm.getAttributeMap().get("dateFilterListCount"))) &&
	    			(observationTypeCount.equals(psForm.getAttributeMap().get("observationTypeCount")))&&
	    			(desCount.equals(psForm.getAttributeMap().get("DescriptionCount"))) &&
					
				
					filterPatient == null && filterLocalId == null && filterProviderReportingFacility == null){
	    				String sortMethod = getSortMethod(request, psForm);
	    				String direction = getSortDirection(request, psForm);			
	    				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
	    					Map<Object, Object>  sColl =  psForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) psForm.getAttributeMap().get("searchCriteria");
	    					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
	    				} else {
	    					sortOrderParam = getSortPatient(direction, sortMethod);
	    				}				
	    				Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
	    				if(sortOrderParam!= null && sortOrderParam.indexOf("Start Date") > -1 )
	    					sortOrderParam = sortOrderParam.replace("Start Date", "Date Received");
	    				searchCriteriaColl.put("sortSt", sortOrderParam);
	    				psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
	    				return observationSummaryVOs;				
	    			}
	    			
	    			ArrayList<Object> jurisList = (psForm.getJurisdictionsDD().size() != 0) ? psForm.getJurisdictionsDD():(ArrayList<Object>)psForm.getAttributeMap().get("jurisdictionDDList");
	    			ArrayList<Object> dateList = (psForm.getStartDateDropDowns().size() != 0)? psForm.getStartDateDropDowns():(ArrayList<Object>)psForm.getAttributeMap().get("startDateDDList");
	    			ArrayList<Object> obsTypeList = (psForm.getObservationTypesDD().size() != 0)?psForm.getObservationTypesDD():(ArrayList<Object>)psForm.getAttributeMap().get("observationTypeDDList");
	    			ArrayList<Object> condList = (psForm.getConditions().size() != 0)? psForm.getConditions():(ArrayList<Object>)psForm.getAttributeMap().get("conditionDD");
	    			ArrayList<Object> desList = (psForm.getDescriptionDD().size() != 0)? psForm.getDescriptionDD():(ArrayList<Object>)psForm.getAttributeMap().get("descriptionDDList");
	    			
	    			Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
	    			String sortMethod = getSortMethod(request, psForm);
	    			String direction = getSortDirection(request, psForm);			
	    			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
	    				Map<Object, Object>  sColl =  psForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) psForm.getAttributeMap().get("searchCriteria");
	    				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
	    			} else {
	    				sortOrderParam = getSortPatient(direction, sortMethod);
	    			}
	    			if(sortOrderParam!= null && sortOrderParam.indexOf("Start Date") > -1 )
	    				sortOrderParam = sortOrderParam.replace("Start Date", "Date Received");
	    			if(sortOrderParam != null && sortOrderParam.trim().length()==0){
						sortOrderParam = "Date Received @ in ascending order" ;
					}
	    			srchCriteriaJuris = queueUtil.getSearchCriteria(jurisList, juris, NEDSSConstants.FILTERBYJURISDICTION);
	    			srchCriteriaDate = queueUtil.getSearchCriteria(dateList, startDate, NEDSSConstants.FILTERBYDATE);
	    			srchObsType = queueUtil.getSearchCriteria(obsTypeList, observationType, NEDSSConstants.FILTERBYOBSERVATIONTYPE);
	    			srchCriteriaCond = queueUtil.getSearchCriteria(condList, cond, NEDSSConstants.FILTERBYCONDITION);
	    		
	    			srchCriteriaDes = queueUtil.getSearchCriteria(desList, des, NEDSSConstants.FILTERBYDESCRIPTION);
	    			//set the error message to the form
	    			if(sortOrderParam != null)
	    				searchCriteriaColl.put("sortSt", sortOrderParam);
	    			if(srchCriteriaDate != null)
	    				searchCriteriaColl.put("INV147", srchCriteriaDate);
	    			if(srchCriteriaJuris != null)
	    				searchCriteriaColl.put("INV107", srchCriteriaJuris);
	    			if(srchCriteriaCond != null)
	    				searchCriteriaColl.put("INV169", srchCriteriaCond);
	    			if(srchObsType != null)
	    				searchCriteriaColl.put("OBS118", srchObsType);
	    			if(filterPatient != null)
	    				searchCriteriaColl.put("PATIENT", filterPatient);
	    			if(filterLocalId != null)
	    				searchCriteriaColl.put("LOCALID", filterLocalId);
	    			if(filterProviderReportingFacility != null)
	    				searchCriteriaColl.put("PROVIDER", filterProviderReportingFacility);
	    			
	    			if(srchCriteriaDes != null)
	    				searchCriteriaColl.put("DESCI", srchCriteriaDes);
	    			
	    			psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
	    			
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			logger.error("Error while filtering the investigation by Investigator: "+ e.toString());
	    			
	    		} 
	    		return observationSummaryVOs;
	    	}
	    	public void sortObservations(PersonSearchForm psForm, Collection<Object>  observationList, boolean existing, HttpServletRequest request) throws Exception {
	    		// Retrieve sort-order and sort-direction from displaytag params
	    		String sortMethod = getSortMethod(request, psForm);
	    		String direction = getSortDirection(request, psForm);

	    		boolean invDirectionFlag = true;
	    		if (direction != null && direction.equals("2"))
	    			invDirectionFlag = false;

	    		//Read from properties file to determine default sort order
	    		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
	    				sortMethod = "getStartDate";
	    				invDirectionFlag = true;
	    				
	    		}
	    		
	    		logger.debug(":::::sortMethod:::::::"+sortMethod);
				logger.debug(":::::invDirectionFlag:::::::"+invDirectionFlag);
	    		
	    		NedssUtils util = new NedssUtils();
	    		if (sortMethod != null && observationList != null
	    				&& observationList.size() > 0) {
	    			util.sortObjectByColumnGeneric(sortMethod,
	    					(Collection<Object>) observationList, invDirectionFlag);
	    		}
	    		
	    	    		
	    		//Changed method sortObjectByColumn to sortObjectByColumnPatientSearch : TO change sorting order alphabetically instead of ASCII-code
	    		
	    		if(!existing) {
	    			//Finally put sort criteria in form
	    			String sortOrderParam = this.getSortPatient(invDirectionFlag == true ? "1" : "2", sortMethod);
	    			if(sortOrderParam!= null && sortOrderParam.indexOf("Start Date") > -1 )
	    				sortOrderParam = sortOrderParam.replace("Start Date", "Date Received");
	    			Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
	    			searchCriteriaColl.put("sortSt", sortOrderParam);
	    			psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
	    		}
	    	}
			
			private static String getSortMethod(HttpServletRequest request, PersonSearchForm psForm ) {
				if (PaginationUtil._dtagAccessed(request)) {
					return request.getParameter((new ParamEncoder("searchResultsTable")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			} else{
				return psForm.getAttributeMap().get("methodName") == null ? null : (String) psForm.getAttributeMap().get("methodName");
				}

			}

			private static String getSortDirection(HttpServletRequest request, PersonSearchForm psForm) {
				if (PaginationUtil._dtagAccessed(request)) {
					return request.getParameter((new ParamEncoder("searchResultsTable")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				} else{
					return psForm.getAttributeMap().get("sortOrder") == null ? "1": (String) psForm.getAttributeMap().get("sortOrder");
				}

			}
			
			public Collection<Object>  filterByText(
					Collection<Object>  patientVoCollection, String filterByText,String column) throws Exception {
				Collection<Object>  newTypeColl = new ArrayList<Object> ();
				try{
				if (patientVoCollection != null) {
					Iterator<Object> iter = patientVoCollection.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
						boolean flag = true;
						if(column.equals("Name")) {
								if( filterByText.contains(",")&& vo.getPersonFullName() != null && vo.getPersonFullName().toUpperCase().contains(filterByText.toUpperCase())){
									newTypeColl.add(vo);
								}else if (vo.getLastNameforFilter()!= null && vo.getLastNameforFilter().toUpperCase().contains(filterByText.toUpperCase())){
									newTypeColl.add(vo);
									flag = false;
								}else if (flag && vo.getFirstNameforFilter()!= null && vo.getFirstNameforFilter().toUpperCase().contains(filterByText.toUpperCase())){
									newTypeColl.add(vo);
								}
						}
						if(column.equals("Phone/Email") && vo.getPersonPhoneprofile()!= null && vo.getPersonPhoneprofile().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						if(column.equals("Id") && vo.getPersonIds()!= null && vo.getPersonIds().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						if(column.equals("Patient ID") && vo.getPersonLocalID()!= null && vo.getPersonLocalID().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						if(column.equals("Age/DOB/Sex") && vo.getProfile()!= null && vo.getProfile().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						if(column.equals("Address") && vo.getPersonAddressProfile()!= null && vo.getPersonAddressProfile().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						
						if(column.equals("Patient ")) {
							if( filterByText.contains(",")&& vo.getPersonFullNameNoLink() != null && vo.getPersonFullNameNoLink().toUpperCase().contains(filterByText.toUpperCase())){
								newTypeColl.add(vo);
							}else if (vo.getPersonLastName()!= null && vo.getPersonLastName().toUpperCase().contains(filterByText.toUpperCase())){
								newTypeColl.add(vo);
								flag = false;
							}else if (flag && vo.getPersonFirstName()!= null && vo.getPersonFirstName().toUpperCase().contains(filterByText.toUpperCase())){
								newTypeColl.add(vo);
							}
					}
						if(column.equals(NEDSSConstants.INV_PATIENT) && vo.getPersonFullNameNoLink() != null 
								&& vo.getPersonFullNameNoLink().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						if(column.equals(NEDSSConstants.INV_LOCAL_ID) && vo.getLocalId() != null 
								&& vo.getLocalId().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						if(column.equals(NEDSSConstants.INV_PROVIDER) && vo.getReportingFacilityProvider() != null 
								&& vo.getReportingFacilityProvider().toUpperCase().contains(filterByText.toUpperCase())){
							newTypeColl.add(vo);
						}
						
					}
				}
				}catch(Exception ex){
					 logger.error("Error filtering the filterByText : "+ex.getMessage());
					 throw new NEDSSSystemException(ex.getMessage());
				}
				return newTypeColl;
			}
			
			
			public void showButton(HttpServletRequest request, String scString){
				
				TreeMap<Object,Object> tm = null;

				 request.setAttribute("DSSearchCriteriaString", scString);
				NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute(
		          "NBSSecurityObject");
				String sCurrTask = NBSContext.getCurrentTask(request.getSession());
				
				if(sCurrTask!=null && (sCurrTask.equalsIgnoreCase("MergeCandidateList1")||sCurrTask.equalsIgnoreCase("MergeCandidateList2"))){

					
					tm = NBSContext.getPageContext(request.getSession(), "PS209",
		    				"Submit");
					
				}else{
					tm = NBSContext.getPageContext(request.getSession(), "PS090", "Submit");
				}

				// add button security
		         boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
		             NBSOperationLookup.ADD);
		         request.setAttribute("addButton", String.valueOf(bAddButton));

		         if (secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.ADD)) {
		            request.setAttribute("addButtonHref",
		                                 "/nbs/" + sCurrTask + ".do?ContextAction=" +
		                                 tm.get("Add"));
		         }

		         if(sCurrTask!=null &&sCurrTask.equals("MergeCandidateList1"))
		    		{

		         request.setAttribute("refineSearchHref",
		        		 
	    					"/nbs/" + sCurrTask +
	    					".do?ContextAction=" +
	    					tm.get("RefineSearch")+"&Mode1=ManualMerge");
	    			request.setAttribute("formHref", "/nbs/" + HTMLEncoder.encodeHtml(sCurrTask) + ".do");
	    			request.setAttribute("newSearchHref",
	    					"/nbs/" + sCurrTask +
	    					".do?ContextAction=" + tm.get("NewSearch")+"&Mode1=ManualMerge");
	    			 request.setAttribute("viewHref",
		        		 
		                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
		    	                              tm.get("View"));

		    		    			 request.setAttribute("viewFileHref",
		    	                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
		    	                              tm.get("ViewFile"));
		    		    		}
		    		         else{
		    		         request.setAttribute("refineSearchHref",
		    		                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
		                              
		                              tm.get("RefineSearch"));
		         request.setAttribute("newSearchHref",
		                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
		                              tm.get("NewSearch"));
		         request.setAttribute("viewHref",
		                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
		                              tm.get("View"));

		         request.setAttribute("viewFileHref",
		                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
		                              tm.get("ViewFile"));
		         
		         request.setAttribute("addPatHref",
                         "/nbs/" + sCurrTask + ".do?ContextAction=Add");
		                
			}
			}			
			public void DisplayInfoforViewFile(PersonVO  pVo,CompleteDemographicForm pform,HttpServletRequest request,String mode){  	    	
		    		
			    	//String mprId = vo.getPersonParentUid().toString();	
				   PatientSrchResultVO vo = new PatientSrchResultVO();			    
			      	
			    	//vo.setPersonFullName(this.setPersonNameProfile(vo));
			    	//vo.setProfile(this.setPersonInfoProfile(vo));
				    pform.resetBatch();
				     setPersonNameDetails(pVo,pform,mode);
			    	vo.setPersonAddressProfile(this.setPersonAddressDetails(pVo,pform));
			    	vo.setPersonPhoneprofile(this.setPersonPhoneDetails(pVo,request,pform));
			    	vo.setPersonIds(this.setPersonIdDetails(pVo,pform, request));
			    	vo.setProfile(this.setPersonRaceSummary(pVo,request,pform));
			    	 setPersonEthnicityDetails(pVo,pform);
			    	pform.setPatSrcResVO(vo);  	//}
		    }
			  public void setPersonNameDetails(PersonVO vo,CompleteDemographicForm pform,String mode) {
				  	StringBuffer personNameProfile = new StringBuffer();					
					String patientID="";
					String actualLocalID = "";
					Collection personNamesColl = null;
					if(vo != null ){
						//PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
						personNamesColl = vo.getThePersonNameDTCollection();
						//personLocatorsColl = patVo.getPersonLocatorsColl();
					}
					String nameCode="";
			    	String firstName ="";
			    	String lastName ="";
			    	String fullName="";
					if(personNamesColl != null && personNamesColl.size()>1){
						  	NedssUtils nUtil = new NedssUtils();
							nUtil.sortObjectByColumn("getAsOfDate", personNamesColl, false); 
					}
			    	Iterator nameIter =personNamesColl.iterator();
			    	boolean flag = false;
			    	while(nameIter.hasNext()){		    		
			    		PersonNameDT name = (PersonNameDT)nameIter.next(); 
			    		if(name != null && name.getStatusCd() != null && name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)
			    				&& name.getRecordStatusCd() != null && name.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)){
			    		  BatchEntry dt = new BatchEntry();
		                  HashMap<String,String> map = new HashMap<String,String>();
		                  map.put("nameDate", (StringUtils.formatDate(name.getAsOfDate())).toString());
		                  map.put("nameType", name.getNmUseCd());
		                  map.put("nameTypeDesc", (CachedDropDowns.getCodeDescTxtForCd(name.getNmUseCd(), "P_NM_USE")));
		                  map.put("namePrefix", name.getNmPrefix());
		                  map.put("namePrefixDesc", (CachedDropDowns.getCodeDescTxtForCd(name.getNmPrefix(), "P_NM_PFX")));
		                  map.put("nameLast", name.getLastNm());
		                  map.put("nameSecLast", name.getLastNm2());
		                  map.put("nameFirst", name.getFirstNm());
		                  map.put("nameMiddle", name.getMiddleNm());
		                  map.put("nameSecMiddle", name.getMiddleNm2());
		                  map.put("nameSuffix", name.getNmSuffix());
		                  map.put("nameSuffixDesc", (CachedDropDowns.getCodeDescTxtForCd(name.getNmSuffix(), "P_NM_SFX")));
		                  map.put("nameDegree", name.getNmDegree());
		                  map.put("nameDegreeDesc", (CachedDropDowns.getCodeDescTxtForCd(name.getNmDegree(), "P_NM_DEG")));
		                  map.put("person_uid",name.getPersonUid().toString());
		                  dt.setAnswerMap(map);
		                  dt.setExisting(true);
		                  pform.setBatchAnswer(dt,"Name");
		                  if(mode != null && mode.equals("Create")){
		                	  pform.setAddrAsOf((StringUtils.formatDate(name.getAsOfDate())).toString());
		                	  pform.setPhAsOf((StringUtils.formatDate(name.getAsOfDate())).toString());
		                	  pform.setIdAsOf((StringUtils.formatDate(name.getAsOfDate())).toString());
		                	  pform.setRaceAsOf((StringUtils.formatDate(name.getAsOfDate())).toString());
		                  }
		                  
			    		}
				       
			    	}			    	
				  }
			     
			
			public String setPersonAddressDetails(PersonVO vo,CompleteDemographicForm pform) {
				  StringBuffer personAddressProfile = new StringBuffer();
					Collection personLocatorsColl = null;
					if(vo != null ){
						//PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
						  PersonUtil util = new PersonUtil();				            
				            util.getBirthAddress(vo,pform);
				            util.getDeceasedAddress(vo,pform);
						personLocatorsColl = vo.getTheEntityLocatorParticipationDTCollection();
						//personLocatorsColl = patVo.getPersonLocatorsColl();
					}
					
				  boolean flag = false;
				  boolean homeAddrExists=false;
				  Timestamp mostRecentAddressAOD = null;
				  //Long locatorUID = this.getUniqueUid(personLocatorsColl);
				  if(personLocatorsColl != null && personLocatorsColl.size()>0){
					  	NedssUtils nUtil = new NedssUtils();
						nUtil.sortObjectByColumn("getAsOfDate", personLocatorsColl, false); 
					     Iterator<Object>  addressIter = personLocatorsColl.iterator();
					      while (addressIter.hasNext()){
					        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
					        if (elp.getThePostalLocatorDT() != null){
					        	    if (elp.getStatusCd() != null && elp.getClassCd() != null &&
					                        elp.getClassCd().equals(NEDSSConstants.POSTAL) && !(elp.getUseCd().equals(NEDSSConstants.BIRTH) || elp.getUseCd().equals(NEDSSConstants.DEATH))
					                       && elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
						                      elp.getRecordStatusCd() != null &&
						                      elp.getRecordStatusCd().
						                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					                  PostalLocatorDT postal = elp.getThePostalLocatorDT();
					                  BatchEntry dt = new BatchEntry();
					                  HashMap<String,String> map = new HashMap<String,String>();					                 
					                  map.put("addrNameDate", (StringUtils.formatDate(elp.getAsOfDate())).toString());					                 
					                  map.put("addrType", elp.getCd()==null?"":elp.getCd());
					                  map.put("addrTypeDesc", elp.getCd()==null?"":getCodeDescTxtForCd(elp.getCd(), "EL_TYPE_PST_PAT",""));
					                  map.put("addrUse", elp.getUseCd()==null?"":elp.getUseCd());
					                  map.put("addrUseDesc", elp.getUseCd()==null?"":getCodeDescTxtForCd(elp.getUseCd(), "EL_USE_PST_PAT",""));
					                  map.put("addrStr1", elp.getThePostalLocatorDT().getStreetAddr1()==null?"":elp.getThePostalLocatorDT().getStreetAddr1());
					                  map.put("addrStr2", elp.getThePostalLocatorDT().getStreetAddr2()==null?"":elp.getThePostalLocatorDT().getStreetAddr2());
					                  map.put("addrCity", elp.getThePostalLocatorDT().getCityDescTxt() ==null?"":elp.getThePostalLocatorDT().getCityDescTxt());
					                  map.put("addrState", elp.getThePostalLocatorDT().getStateCd()==null?"":elp.getThePostalLocatorDT().getStateCd());					                
					                  map.put("addrZip", elp.getThePostalLocatorDT().getZipCd()==null?"":elp.getThePostalLocatorDT().getZipCd());
					                  map.put("addrCounty", elp.getThePostalLocatorDT().getCntyCd()==null?"":elp.getThePostalLocatorDT().getCntyCd());
					                  map.put("addrCensusTract", elp.getThePostalLocatorDT().getCensusTract()==null?"":elp.getThePostalLocatorDT().getCensusTract());
					                  map.put("addrCountry", elp.getThePostalLocatorDT().getCntryCd()==null?"":elp.getThePostalLocatorDT().getCntryCd());
					                  map.put("addrCountyDesc", elp.getThePostalLocatorDT().getCntyCd()==null?"":getCodeDescTxtForCd(elp.getThePostalLocatorDT().getCntyCd(),"", elp.getThePostalLocatorDT().getStateCd()==null?"":elp.getThePostalLocatorDT().getStateCd()));
					                  map.put("addrCountryDesc", elp.getThePostalLocatorDT().getCntryCd()==null?"":getCodeDescTxtForCd(elp.getThePostalLocatorDT().getCntryCd(), NEDSSConstants.COUNTRY_LIST,""));
					                  map.put("addrStateDesc", elp.getThePostalLocatorDT().getStateCd()==null?"":getCodeDescTxtForCd(elp.getThePostalLocatorDT().getStateCd(), NEDSSConstants.STATE_LIST,""));
					                  map.put("addrUseCdDesc", elp.getUseCd()==null?"":getCodeDescTxtForCd(elp.getUseCd(), "EL_USE_PST_PAT",""));
					                  map.put("addrComments",  elp.getLocatorDescTxt()==null?"":elp.getLocatorDescTxt());
					                  map.put("locator_uid", elp.getLocatorUid()==null?"":elp.getLocatorUid().toString());
					                  dt.setAnswerMap(map);
					                  dt.setExisting(true);
					                  pform.setBatchAnswer(dt,"Address");
					                  //this is for file summary tab
					                  if (elp.getCd() != null && elp.getUseCd() != null &&
					                      elp.getCd().equals(NEDSSConstants.HOME) &&
					                      elp.getUseCd().equals(NEDSSConstants.HOME) &&
					                      elp.getStatusCd() != null &&
					                      elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
					                      elp.getRecordStatusCd() != null &&
					                      elp.getRecordStatusCd().
					                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					                    if (mostRecentAddressAOD == null ||
					                        (elp.getAsOfDate() != null &&
					                     !elp.getAsOfDate().before(mostRecentAddressAOD))) {
					                   mostRecentAddressAOD = elp.getAsOfDate();
					                    
					                   personAddressProfile = new StringBuffer();
					                   if(mostRecentAddressAOD == null)
					                    personAddressProfile.append("<b>"+"Address (Home)"+"</b><br>");
					                   else
					                	  personAddressProfile.append("<b>"+"Address (Home)</b><br>");
					                	 //  personAddressProfile.append("<b>"+"Home Address("+StringUtils.formatDate(elp.getAsOfDate())+")</b><br>");  
						        	    personAddressProfile.append(elp.getThePostalLocatorDT().getStreetAddr1()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getStreetAddr1())+"<br>":"");
						        		personAddressProfile.append(elp.getThePostalLocatorDT().getStreetAddr2()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getStreetAddr2())+"<br>":"");
						        		personAddressProfile.append(elp.getThePostalLocatorDT().getCityDescTxt()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getCityDescTxt())+", " :"");
						        		personAddressProfile.append(elp.getThePostalLocatorDT().getStateCd()!=null? HTMLEncoder.encodeHtml(this.getStateDescTxt(elp.getThePostalLocatorDT().getStateCd()))+" ":"");
						        		personAddressProfile.append(elp.getThePostalLocatorDT().getZipCd()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getZipCd()):"");
						        		personAddressProfile.append(elp.getThePostalLocatorDT().getCntyCd()==null?"":"<br>"+HTMLEncoder.encodeHtml(getCodeDescTxtForCd(elp.getThePostalLocatorDT().getCntyCd(),"", elp.getThePostalLocatorDT().getStateCd()==null?"":elp.getThePostalLocatorDT().getStateCd())));
						        		homeAddrExists = true;
						        		  flag = true;
					                   
					                    }
					                  }
					        	    }
					        	  
					        	   /* else if(elp.getUseCd() != null && elp.getUseCd().equalsIgnoreCase("Work Place") && elp.getCd().equalsIgnoreCase("O")){
						        	    personAddressProfile.append("<b>"+"Work"+"</b><br>");
						        	    }
					        	    else if(elp.getCd() != null ){
					        	    	 String typeCd=CachedDropDowns.getCodeDescTxtForCd(elp.getCd(), "EL_TYPE_PST_PAT");
					        	    	 if(typeCd != null && !typeCd.equalsIgnoreCase(""))
					        	    	personAddressProfile.append("<b>"+typeCd+"</b><br>");
						        	    }*/       		
					        }
					        
					       }	
					      if(!homeAddrExists){
					    	  addressIter = personLocatorsColl.iterator();					    	
					    	  StringBuffer personAddressProfilePrev = new StringBuffer();
					    	  mostRecentAddressAOD = null;
						      while (addressIter.hasNext()){
						        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
						        if (elp.getThePostalLocatorDT() != null){
					        	    if(flag){
					        	    	if(personAddressProfile != null && personAddressProfile.toString() != null && !personAddressProfile.toString().equals(""))
					        	    		personAddressProfile.append("<br>");
					        	    	//else	
					        	    //	personAddressProfile.append("<br>");			        	    	
					        	    }
					        	    if (elp.getStatusCd() != null && elp.getClassCd() != null &&
					                        elp.getClassCd().equals(NEDSSConstants.POSTAL) && !(elp.getUseCd().equals(NEDSSConstants.BIRTH) || elp.getUseCd().equals(NEDSSConstants.DEATH))
					                       && elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
						                      elp.getRecordStatusCd() != null &&
						                      elp.getRecordStatusCd().
						                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						        	    	 String typeCd=CachedDropDowns.getCodeDescTxtForCd(elp.getCd(), "EL_TYPE_PST_PAT");
						        	    	 personAddressProfilePrev = personAddressProfile;						        	    	
						        	    	 if(typeCd != null && !typeCd.equalsIgnoreCase("")){
						        	    		 personAddressProfile.append("<b>Address</b> ");
						        	    		 personAddressProfile.append("(").append(typeCd).append(")<br>");
						        	    		
						        	    	    //personAddressProfile.append("<b>"+typeCd+"("+ elp.getAsOfDate() +")</b><br>");
						        	    		personAddressProfile.append(elp.getThePostalLocatorDT().getStreetAddr1()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getStreetAddr1())+"<br>":"");
								        		personAddressProfile.append(elp.getThePostalLocatorDT().getStreetAddr2()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getStreetAddr2())+"<br>":"");
								        		personAddressProfile.append(elp.getThePostalLocatorDT().getCityDescTxt()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getCityDescTxt())+", " :"");
								        		personAddressProfile.append(elp.getThePostalLocatorDT().getStateCd()!=null? HTMLEncoder.encodeHtml(this.getStateDescTxt(elp.getThePostalLocatorDT().getStateCd()))+" ":"");
								        		personAddressProfile.append(elp.getThePostalLocatorDT().getZipCd()!=null? HTMLEncoder.encodeHtml(elp.getThePostalLocatorDT().getZipCd()):"");
								        		personAddressProfile.append(elp.getThePostalLocatorDT().getCntyCd()==null?"":"<br>"+HTMLEncoder.encodeHtml(getCodeDescTxtForCd(elp.getThePostalLocatorDT().getCntyCd(),"", elp.getThePostalLocatorDT().getStateCd()==null?"":elp.getThePostalLocatorDT().getStateCd())));
								        		flag = true;
								        		homeAddrExists = true;
						        	    	 }       	    	 
							        	    }
						        }						        
						       }				    	  
					      }
					    }
				  if(!homeAddrExists){
					  personAddressProfile.append("No Address Available"); 
				  }
				return personAddressProfile.toString();
			}
			
			public String setPersonPhoneDetails(PersonVO vo,HttpServletRequest request,CompleteDemographicForm pform) {
				StringBuffer personPhoneprofile = new StringBuffer();
				Collection personLocatorsColl = null;
				 
				if(vo != null){
					//PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
					personLocatorsColl = vo.getTheEntityLocatorParticipationDTCollection();
					//personLocatorsColl = patVo.getPersonLocatorsColl();
				}
				
				int i =0;
				if (personLocatorsColl != null && personLocatorsColl.size()>0){
					NedssUtils nUtil = new NedssUtils();
					nUtil.sortObjectByColumn("getAsOfDate", personLocatorsColl, false); 
				     Iterator<Object>  addressIter = personLocatorsColl.iterator();
				      while (addressIter.hasNext()){
				        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
				        if (elp.getTheTeleLocatorDT() != null){
				        	if( elp.getStatusCd() != null &&
				                      elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
				                      elp.getRecordStatusCd() != null &&
				                      elp.getRecordStatusCd().
				                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)){
				        	  BatchEntry dt = new BatchEntry();
			                  HashMap<String,String> map = new HashMap<String,String>();
			                  map.put("phDate", (StringUtils.formatDate(elp.getAsOfDate())).toString());
			                  map.put("phType", elp.getCd()==null?"":elp.getCd());
			                  map.put("phTypeDesc", elp.getCd()==null?"":getCodeDescTxtForCd(elp.getCd(), "EL_TYPE_TELE_PAT",""));
			                  map.put("phUse", elp.getUseCd()==null?"":elp.getUseCd());
			                  map.put("phUseDesc", elp.getUseCd()==null?"":getCodeDescTxtForCd(elp.getUseCd(), "EL_USE_TELE_PAT",""));
			                  map.put("phCntryCd", elp.getTheTeleLocatorDT().getCntryCd()==null?"":elp.getTheTeleLocatorDT().getCntryCd());
			                  map.put("phNum", elp.getTheTeleLocatorDT().getPhoneNbrTxt()==null?"":elp.getTheTeleLocatorDT().getPhoneNbrTxt());
			                  map.put("phExt", elp.getTheTeleLocatorDT().getExtensionTxt() ==null?"":elp.getTheTeleLocatorDT().getExtensionTxt());
			                  map.put("phEmail", elp.getTheTeleLocatorDT().getEmailAddress()==null?"":elp.getTheTeleLocatorDT().getEmailAddress());
			                  map.put("phUrl", elp.getTheTeleLocatorDT().getUrlAddress()==null?"":elp.getTheTeleLocatorDT().getUrlAddress());
			                  map.put("phComments",  elp.getLocatorDescTxt()==null?"":elp.getLocatorDescTxt());
			                  map.put("locator_uid", elp.getLocatorUid()==null?"":elp.getLocatorUid().toString());			                  
			                  dt.setAnswerMap(map);
			                  pform.setBatchAnswer(dt,"Phone");
				        	//String useCd=CachedDropDowns.getCdForCdDescTxt(elp.getUseCd(), "EL_USE_TELE_PAT");
				        	//if(elp.getTheTeleLocatorDT().getPhoneNbrTxt()!= null && !elp.getTheTeleLocatorDT().getPhoneNbrTxt().equals("")){
				        	  if (elp.getCd() != null && elp.getUseCd() != null &&
				                      elp.getCd().equals(NEDSSConstants.PHONE) &&
				                      elp.getUseCd().equals(NEDSSConstants.HOME) &&
				                      elp.getStatusCd() != null &&
				                      elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
				                      elp.getRecordStatusCd() != null &&
				                      elp.getRecordStatusCd().
				                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					                  if (elp.getAsOfDate() != null) {
					                	  if(i <2 ){
					                		  i++;
					                		  personPhoneprofile.append("<b>"+ "Home" +"</b><br>");
					                		  personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
					                		  if(elp.getTheTeleLocatorDT().getEmailAddress()!=null){
					                			  personPhoneprofile.append("<b>"+ "Email" +"</b><br>");
					                			  personPhoneprofile.append(elp.getTheTeleLocatorDT().getEmailAddress()!=null?HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getEmailAddress()):"").append("<br>");
				                    			}
					                	  }
					                    
					                  }
				        	  }
				        	  
				        	  else if (elp.getCd() != null && elp.getUseCd() != null &&
			                           elp.getCd().equals(NEDSSConstants.PHONE) &&
			                           elp.getUseCd().equals(NEDSSConstants.WORK_PHONE) &&
			                           elp.getStatusCd() != null &&
			                           elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
			                           elp.getRecordStatusCd() != null &&
			                           elp.
			                           getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					                    if (elp.getAsOfDate() != null ) {
					                    	if(i <2 ){
				                		  i++;
				                		  personPhoneprofile.append("<b>"+ "Work" +"</b><br>");
				                		  personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
					                      if(elp.getTheTeleLocatorDT().getEmailAddress()!=null){
					                    	  personPhoneprofile.append("<b>"+ "Email" +"</b><br>");
					                    	  personPhoneprofile.append(elp.getTheTeleLocatorDT().getEmailAddress()!=null?HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getEmailAddress()):"").append("<br>");
			                    			}
				                    	}
				                      
				                    }
			                    
				        	  }
				        	  
				        	  else if (elp.getCd() != null && elp.getUseCd() != null &&
			                           elp.getCd().equals(NEDSSConstants.CELL) &&
			                           elp.getUseCd().equals(NEDSSConstants.MOBILE) &&
			                           elp.getStatusCd() != null &&
			                           elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
			                           elp.getRecordStatusCd() != null &&
			                           elp.
			                           getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					                    if (elp.getAsOfDate() != null) {
					                    		if(i <2 ){
							                		i++;
							                		personPhoneprofile.append("<b>"+ "Cell " +"</b><br>");
							                		personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
							                        if(elp.getTheTeleLocatorDT().getEmailAddress()!=null){
							                        	personPhoneprofile.append("<b>"+ "Email" +"</b><br>");
							                        	personPhoneprofile.append(elp.getTheTeleLocatorDT().getEmailAddress()!=null?HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getEmailAddress()):"").append("<br>");
					                    			}
					                    		}			                      
					                    }
				        	  }
				        	  else if (elp.getCd() != null && elp.getUseCd() != null &&
			                           elp.getCd().equals(NEDSSConstants.NET) &&
			                           elp.getStatusCd() != null &&
			                           elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
			                           elp.getRecordStatusCd() != null &&
			                           elp.
			                           getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					                    if (elp.getAsOfDate() != null ) {
					                    	if(i <2 ){
				                		  i++;
				                		  if(elp.getTheTeleLocatorDT().getPhoneNbrTxt()!=null){
				                		  personPhoneprofile.append("<b>"+ "Home" +"</b><br>");
				                		  personPhoneprofile.append(HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getPhoneNbrTxt())).append("<br>");
				                		  }
					                      if(elp.getTheTeleLocatorDT().getEmailAddress()!=null){
					                    	  personPhoneprofile.append("<b>"+ "Email" +"</b><br>");
					                    	  personPhoneprofile.append(elp.getTheTeleLocatorDT().getEmailAddress()!=null?HTMLEncoder.encodeHtml(elp.getTheTeleLocatorDT().getEmailAddress()):"").append("<br>");
			                    			}
				                    	}
				                      
				                    }
			                    
				        	  }
				        	  		                    
				        	}			        	
				        }
				      }
				}
				if(personPhoneprofile.toString().length() ==0){
					personPhoneprofile.append("No Phone Info Available");
				}
		 		return personPhoneprofile.toString();
			}
			
			public String setPersonIdDetails(PersonVO vo,CompleteDemographicForm pform, HttpServletRequest request) {
				StringBuffer personIds = new StringBuffer();
				Collection personIdColl = null;
				
				if(vo!= null){
					personIdColl = vo.getTheEntityIdDTCollection();
				}
			
				
				if (personIdColl != null && personIdColl.size()>0){
					NedssUtils nUtil = new NedssUtils();
					nUtil.sortObjectByColumn("getAsOfDate", personIdColl, false); 
					boolean flag = false;
					int i =0;
				     Iterator<Object>  personPersonIdsIte = personIdColl.iterator();
				     HashMap<Long, String> sortedMap = new HashMap<Long, String>();
				      while (personPersonIdsIte.hasNext()){
				    	  
				    	  EntityIdDT ids  = (EntityIdDT) personPersonIdsIte.next();
				    	 if(ids.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
				                      ids.getRecordStatusCd() != null &&
				                      ids.getRecordStatusCd().
				                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)){
					    	  BatchEntry dt = new BatchEntry();
			                  HashMap<String,String> map = new HashMap<String,String>();
			                  map.put("idDate", (StringUtils.formatDate(ids.getAsOfDate())).toString());
			                  map.put("idType", ids.getTypeCd()==null?"":ids.getTypeCd());
			                  map.put("idTypeDesc", ids.getTypeCd()==null?"":getCodeDescTxtForCd(ids.getTypeCd(), "EI_TYPE_PAT",""));
			                  map.put("idAssgn", ids.getAssigningAuthorityCd()==null?"":ids.getAssigningAuthorityCd());
			                  map.put("idAssgnDesc",  ids.getAssigningAuthorityCd()==null?"":getCodeDescTxtForCd(ids.getAssigningAuthorityCd(), "EI_AUTH_PAT",""));
			                  map.put("idValue", ids.getRootExtensionTxt()==null?"":ids.getRootExtensionTxt()); 
			                  map.put("entity_uid", ids.getEntityUid()==null?"":ids.getEntityUid().toString());
			                  map.put("entityIdSeq", ids.getEntityIdSeq()==null?"":ids.getEntityIdSeq().toString());
			                  dt.setAnswerMap(map);
			                  pform.setBatchAnswer(dt,"Identification");
			                  
				    	  if (ids.getTypeCd() != null &&
			                      ids.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
			                      ids.getRecordStatusCd() != null &&
			                      ids.getRecordStatusCd().
			                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
			                  if (ids.getAsOfDate() != null ) {
			                	  NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute(
			            		          "NBSSecurityObject");
			                	  boolean HIVQuestionPermission = secObj.getPermission(
			          					NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS);
			                	  if(!HIVQuestionPermission && ids.getTypeCd()!=null && ids.getTypeCd().equals(NEDSSConstants.PARTNER_SERVICES_ID))
			                		  continue;
			                	  	if(i <2 ){
			                		  i++;
				                	  personIds.append(ids.getTypeCd()!=null?"<b>"+HTMLEncoder.encodeHtml(getCodeDescTxtForCd(ids.getTypeCd(), "EI_TYPE_PAT",""))+"</b><br>":"");
							    	  personIds.append(ids.getRootExtensionTxt()!=null?HTMLEncoder.encodeHtml(ids.getRootExtensionTxt()):"").append("<br>");
			                	  	}
			                    }
			                  }
				        }
				    	 
				      }
				    }
				if(personIds.toString().length() == 0){
					personIds.append("No ID Info Available");
				}
				return personIds.toString();
			}
			
			public String setPersonRaceSummary(PersonVO vo,HttpServletRequest request,CompleteDemographicForm pform) {
				StringBuffer sbProfile = new StringBuffer();
				Collection personRaceColl = null;
				
				
				if(vo!= null){
					//PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
					personRaceColl = vo.getThePersonRaceDTCollection();
					//personIdColl = patVo.getPersonIdColl();
				}
				if (personRaceColl != null){
					boolean flag = false;
					int count=0;
					int i=0;
					  String detailedRaces="";
					  String detailedRacesDesc="";
					  Timestamp prevOldasDate=null;
					  String prevRaceCatCd="";
					  String prevRaceCd="";
					  String personUid ="";
					  if(personRaceColl.size()>1){ //defect 4624 - not sorted
						  	NedssUtils nUtil = new NedssUtils();
							nUtil.sortObjectByColumn("getAsOfDate", personRaceColl, false); 
					  }					  
				     Iterator<Object>  iter = personRaceColl.iterator();
				     int size = personRaceColl.size();
				   
				      //while (iter.hasNext()){
				    	 
				    	  flag = true;
				    	  //PersonRaceDT rdt  = (PersonRaceDT) iter.next();
				    	 
		                 
		                   while (iter.hasNext()) {
							  PersonRaceDT race = (PersonRaceDT) iter.next();
							  BatchEntry dt = new BatchEntry();
							  i=i+1;
			                  HashMap<String,String> map = new HashMap<String,String>();
							  if(race!=null && race.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)){
					           String asofDate= StringUtils.formatDate(race.getAsOfDate());
					          
					           if(prevRaceCatCd.equals("") || prevRaceCatCd.equals(race.getRaceCategoryCd().trim())){					        	 
						           if(detailedRaces.trim().equals("") && race.getRaceCd() != null && !race.getRaceCd().trim().equals("") && !race.getRaceCd().trim().equals(race.getRaceCategoryCd().trim())){
						        	   detailedRaces=race.getRaceCd();
						        	   detailedRacesDesc = getCodeDescTxtForCd(race.getRaceCd().trim(),"RACE",race.getRaceCategoryCd().trim());
						           }
						           else if(!detailedRaces.trim().equals("") && race.getRaceCd() != null && !race.getRaceCd().trim().equals("") && !race.getRaceCd().trim().equals(race.getRaceCategoryCd().trim())){
						           detailedRaces=detailedRaces + " | " + race.getRaceCd();
						           detailedRacesDesc = detailedRacesDesc + " | " + getCodeDescTxtForCd(race.getRaceCd().trim(),"RACE",race.getRaceCategoryCd().trim());
						           }
						           prevOldasDate = race.getAsOfDate();
						           prevRaceCatCd = race.getRaceCategoryCd();
						           prevRaceCd = race.getRaceCd();
					           }else if(!prevRaceCatCd.equals("") && !prevRaceCatCd.equals(race.getRaceCategoryCd().trim())){
					        	  
					        	   map.put("raceDate",StringUtils.formatDate(prevOldasDate));						           								          
						             if(prevRaceCatCd.trim().equals(NEDSSConstants.UNKNOWN)){
						            	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.UNKNOWN, "RACE_CALCULATED"));
						            	 map.put("raceType",NEDSSConstants.UNKNOWN);
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)){
						                	 map.put("raceType",NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
						                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE, "RACE_CALCULATED"));
						                	
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.ASIAN)){						                	
						                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.ASIAN, "RACE_CALCULATED"));
						                	 map.put("raceType",NEDSSConstants.ASIAN);
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.AFRICAN_AMERICAN)){
						                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AFRICAN_AMERICAN, "RACE_CALCULATED"));
						                	 map.put("raceType",NEDSSConstants.AFRICAN_AMERICAN);
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)){
						                	 map.put("raceType",NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
						                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER, "RACE_CALCULATED"));
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.WHITE)){
						                	 map.put("raceType",NEDSSConstants.WHITE);
						                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WHITE, "RACE_CALCULATED"));
						                	
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.OTHER_RACE)){									               
						               	 map.put("raceTypeDesc","Other");
						               	 map.put("raceType",NEDSSConstants.OTHER_RACE);	
						                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.REFUSED_TO_ANSWER)){									               
							               	 map.put("raceType",NEDSSConstants.REFUSED_TO_ANSWER);
							               	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.REFUSED_TO_ANSWER, "RACE_CALCULATED"));	
						                }else{
						                	 map.put("raceType",prevRaceCatCd);
						                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(prevRaceCatCd, "RACE_CALCULATED"));
						                }
						             map.put("raceDetailCat",detailedRaces);
						             map.put("raceDetailCatDesc",detailedRacesDesc);
						             if(race.getPersonUid() != null && !race.getPersonUid().equals("")){
						             personUid = race.getPersonUid().toString();
						             map.put("personUid",race.getPersonUid().toString());
						             }
						             dt.setAnswerMap(map);
					                  pform.setBatchAnswer(dt,"Race");
					                  count=count+1;
						            		            
						          
							        if(race.getRaceCd() != null && !race.getRaceCd().trim().equals("") && !race.getRaceCd().trim().equals(race.getRaceCategoryCd().trim())){
							        	   detailedRaces=race.getRaceCd();
							        	   detailedRacesDesc = getCodeDescTxtForCd(race.getRaceCd().trim(),"RACE",race.getRaceCategoryCd().trim());
							           }else{
							        	   detailedRaces ="";
							        	   detailedRacesDesc ="";
							           }
						        	   prevRaceCatCd = race.getRaceCategoryCd();
						        	   prevOldasDate = race.getAsOfDate();
						        	   prevRaceCd = race.getRaceCd();
							        }   
							      			
					             }//race != null ends
							  
							    if(i==size){
							    	 if(race!=null && race.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)){
							    		if(race.getRaceCategoryCd() != null && !race.getRaceCategoryCd().trim().equals("")){
							    		    if(race.getRaceCd() != null && race.getRaceCategoryCd().equals(race.getRaceCd())){
							    		    	 dt = new BatchEntry();
								                 map = new HashMap<String,String>();
							    		    	
							    		    	   map.put("raceDate",StringUtils.formatDate(race.getAsOfDate()));		
							    		    	   if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.UNKNOWN)){
										            	 map.put("raceType",NEDSSConstants.UNKNOWN);
										            	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.UNKNOWN, "RACE_CALCULATED"));
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)){
										                	map.put("raceType",NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
										                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE, "RACE_CALCULATED"));				                	 
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.ASIAN)){
										                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.ASIAN, "RACE_CALCULATED"));
										                	 map.put("raceType",NEDSSConstants.ASIAN);
										                	
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.AFRICAN_AMERICAN)){
										                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AFRICAN_AMERICAN, "RACE_CALCULATED"));
										                	 map.put("raceType",NEDSSConstants.AFRICAN_AMERICAN);
										               
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)){
										                	map.put("raceType",NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
										                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER, "RACE_CALCULATED"));
										                
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.WHITE)){
										                	 map.put("raceType",NEDSSConstants.WHITE);
										                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WHITE, "RACE_CALCULATED"));
										                	
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.OTHER_RACE)){									               
										                	 map.put("raceTypeDesc","Other");
											               	 map.put("raceType",NEDSSConstants.OTHER_RACE);	               		                
										                }else if(race.getRaceCategoryCd().trim().equals(NEDSSConstants.REFUSED_TO_ANSWER)){									               
											               	 map.put("raceType",NEDSSConstants.REFUSED_TO_ANSWER);
											               	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.REFUSED_TO_ANSWER, "RACE_CALCULATED"));	
										                }else{
										                	 map.put("raceType",race.getRaceCategoryCd().trim());
										                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(race.getRaceCategoryCd().trim(), "RACE_CALCULATED"));
										                }	             					            
							                	          map.put("raceDetailCat","");
							                	          map.put("raceDetailCatDesc","");
							                	          map.put("personUid",personUid);
							                	          dt.setAnswerMap(map);
							    		                  pform.setBatchAnswer(dt,"Race");
							    		                  detailedRaces ="";
							    		    }	    			
							    		}					    		 
							    	 } 	
							     }//if i==size ends
					           }
		                   
		                   if(detailedRaces != null && !detailedRaces.trim().equals("")){
		                	   BatchEntry dt = new BatchEntry();
				                 HashMap<String,String> map = new HashMap<String,String>();
						            
		                	   map.put("raceDate",StringUtils.formatDate(prevOldasDate));	
						           
		                	   if(prevRaceCatCd.trim().equals(NEDSSConstants.UNKNOWN)){
					            	 map.put("raceType",NEDSSConstants.UNKNOWN);
					            	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.UNKNOWN, "RACE_CALCULATED"));
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)){
					                	map.put("raceType",NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE, "RACE_CALCULATED"));				                	 
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.ASIAN)){
					                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.ASIAN, "RACE_CALCULATED"));
					                	 map.put("raceType",NEDSSConstants.ASIAN);
					                	
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.AFRICAN_AMERICAN)){
					                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AFRICAN_AMERICAN, "RACE_CALCULATED"));
					                	 map.put("raceType",NEDSSConstants.AFRICAN_AMERICAN);
					               
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)){
					                	map.put("raceType",NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER, "RACE_CALCULATED"));
					                
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.WHITE)){
					                	 map.put("raceType",NEDSSConstants.WHITE);
					                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WHITE, "RACE_CALCULATED"));
					                	
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.OTHER_RACE)){									               
					                	 map.put("raceTypeDesc","Other");
						               	 map.put("raceType",NEDSSConstants.OTHER_RACE);	               		                
					                }else if(prevRaceCatCd.trim().equals(NEDSSConstants.REFUSED_TO_ANSWER)){									               
						               	 map.put("raceType",NEDSSConstants.REFUSED_TO_ANSWER);
						               	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.REFUSED_TO_ANSWER, "RACE_CALCULATED"));	
					                }else{
					                	 map.put("raceType",prevRaceCatCd);
					                	 map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(prevRaceCatCd, "RACE_CALCULATED"));
					                }	             					            
		                	          map.put("raceDetailCat",detailedRaces);
		                	          map.put("raceDetailCatDesc",detailedRacesDesc);
		                	          map.put("personUid",personUid);
		                	          dt.setAnswerMap(map);
		    		                  pform.setBatchAnswer(dt,"Race");
		    		                  count=count+1;
		    		                  prevRaceCatCd ="";
							        }
		                   
		                  
		                
		                   iter = personRaceColl.iterator(); 
				                  while (iter.hasNext()) {
									  PersonRaceDT race = (PersonRaceDT) iter.next();
									  BatchEntry dt = new BatchEntry();
					                  HashMap<String,String> map = new HashMap<String,String>();
									  if(race!=null && race.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)){
							           String asofDate= StringUtils.formatDate(race.getAsOfDate());	       
							        	   if(race.getRaceCategoryCd().trim().equals("U") ){
								        	     map.put("raceDate",asofDate);
								        	     map.put("raceTypeDesc",CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.UNKNOWN, "RACE_CALCULATED"));
								            	 map.put("raceType",NEDSSConstants.UNKNOWN);
								            	 map.put("personUid",personUid);
								            	 boolean unknownAlreadyExists = false;
								            	 if(pform.getAllBatchAnswer4Table("raceTable")!=null)
								            	 {
								            		 Iterator<BatchEntry> ite = pform.getAllBatchAnswer4Table("raceTable").iterator();
								            		 while (ite.hasNext()){
								            			 BatchEntry be = ite.next();
								            			 if(be.getAnswerMap()!=null && be.getAnswerMap().get("raceType")!=null && be.getAnswerMap().get("raceType").equals(NEDSSConstants.UNKNOWN)){
								            				 unknownAlreadyExists=true;
								            			 }
								            		 }
								            	 }
								            	 if(!unknownAlreadyExists){
					            				 dt.setAnswerMap(map);
								                 pform.setBatchAnswer(dt,"Race");
								            	 }
								            	 
								           }
									  }
				                   }
		                   
		                  
		                  
				    //  }
				}
				
				String racestr="";				
				if(request.getAttribute("fileSummaryRaceCategory") != null && ((StringBuffer)request.getAttribute("fileSummaryRaceCategory")).toString().length()>1){
				  racestr = ((StringBuffer)request.getAttribute("fileSummaryRaceCategory")).toString();
				  racestr = racestr.substring(0, racestr.length()-2);
				}
				if(racestr != null && racestr.equals("")){
					racestr = "No Race Info Available";
				}
				String ethstr="";
				if(vo!= null){
					//PatientSrchResultVO patVo = (PatientSrchResultVO)vo;
					PersonDT pdt = vo.getThePersonDT();
	    			 CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
					ethstr = (pdt.getEthnicGroupInd() == null) ? "" : cachedDropDownValues.getCodeShortDescTxt(pdt.getEthnicGroupInd(), "PHVS_ETHNICITYGROUP_CDC_UNK");
					
					//personIdColl = patVo.getPersonIdColl();
				}			
				if(ethstr == null || ethstr.trim().equalsIgnoreCase("") || ethstr.equalsIgnoreCase("no desc put in")){					
					ethstr = "No Ethnicity Info Available";					
				}
				
				    sbProfile.append("<b>Race</b><br>");
		    	    sbProfile.append(racestr+"<br>");
		    	    sbProfile.append("<b>Ethnicity</b><br>");
		    	    sbProfile.append(ethstr+"<br>");
				
							
				return sbProfile.toString();
			}	  
		 
			public String getCodeDescTxtForCd(String code, String codeSetNm,String state){
				ArrayList<Object> aList = new ArrayList<Object>();
				if(codeSetNm.equalsIgnoreCase(NEDSSConstants.STATE_LIST)){
				 aList = CachedDropDowns.getStateList();
				}else if(codeSetNm.equalsIgnoreCase("")){
					 aList = CachedDropDowns.getCountyCodes(state);
				}else if(codeSetNm.equalsIgnoreCase(NEDSSConstants.COUNTRY_LIST)){
				 aList = CachedDropDowns.getCountryList();
			   }else if(codeSetNm.equalsIgnoreCase("PRIMARY_OCCUPATION")){
				   aList = CachedDropDowns.getPrimaryOccupationCodes(); 
			   }else if(codeSetNm.equalsIgnoreCase("PRIMARY_LANGUAGE")){
				   aList = CachedDropDowns.getLanguageCodes(); 
			   }else if(codeSetNm.equalsIgnoreCase("RACE")){
				   aList = CachedDropDowns.getRaceCodes(state); 
			   }
			   else{
				 aList = CachedDropDowns.getCachedDropDownList(codeSetNm);
				}				
				StringBuffer desc = new StringBuffer("");
					if (aList != null && aList.size() > 0) {
						Iterator ite = aList.iterator();
						while (ite.hasNext()) {
							DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
							if (ddcDT.getKey().equals(code)) {
								desc.append(ddcDT.getValue());
								break;
							}
						}
					}
					return desc.toString();
				} 
			
			private Long getUniqueUid(Collection<EntityLocatorParticipationDT> personLocatorsColl){
				Long uid = null;
				
				Timestamp addTimestamp = null; 				
				if(personLocatorsColl != null){
				     Iterator<EntityLocatorParticipationDT>  entityPartDTIter = personLocatorsColl.iterator();
				      while (entityPartDTIter.hasNext()){
				        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)entityPartDTIter.next();
				        if(addTimestamp == null){
				        	addTimestamp = elp.getAsOfDate();
				        }
				        if(elp.getAsOfDate()!=null && elp.getAsOfDate().compareTo(addTimestamp) > 0){
				        	addTimestamp = elp.getAsOfDate();
				        }
				      }
				}
				
				if(personLocatorsColl != null){
				     Iterator<EntityLocatorParticipationDT>  addressIter = personLocatorsColl.iterator();
				      while (addressIter.hasNext()){
				        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
				        if (elp.getCd().equals(NEDSSConstants.HOME) && elp.getUseCd().equals(NEDSSConstants.HOME) && 
				        		elp.getThePostalLocatorDT() != null){				        	
				        	if(elp.getThePostalLocatorDT().getAddTime() !=null &&
				        			addTimestamp.equals(elp.getThePostalLocatorDT().getAddTime())){
				        		 uid = elp.getThePostalLocatorDT().getPostalLocatorUid();
				        	}
				        }
				      }
				}
				
				if(uid == null && personLocatorsColl != null){
				     Iterator<EntityLocatorParticipationDT>  addressIter = personLocatorsColl.iterator();
				      while (addressIter.hasNext()){
				        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
				        if (elp.getThePostalLocatorDT() != null){
				        	if(addTimestamp.equals(elp.getThePostalLocatorDT().getAddTime())){
				        		 uid = elp.getThePostalLocatorDT().getPostalLocatorUid();
				        	}
				        }
				      }
					
				}
				return uid;
			}
			
			 public void setPersonEthnicityDetails(PersonVO vo,CompleteDemographicForm pform) {
				  Collection<Object>  ethnicities = vo.getThePersonEthnicGroupDTCollection();
				  String[] parsedEthnicity=null;
				  int count =0;
				  if (ethnicities != null) {
					 parsedEthnicity= new String[ethnicities.size()];  

			        Iterator<Object>  iter = ethnicities.iterator();

			         if (iter != null) {
                     
			            while (iter.hasNext()) {
                          
			               PersonEthnicGroupDT ethnic = (PersonEthnicGroupDT) iter.next();
			                if (ethnic.getRecordStatusCd().equals(NEDSSConstants.
			                   RECORD_STATUS_ACTIVE)) {
			                  
			                  parsedEthnicity[count]=ethnic.getEthnicGroupCd();
			                  count =count+1;

			               }
			            }
			         }			    	
				  }
			      if(count>0 && parsedEthnicity != null){
			    	  pform.setSpanOrigin(parsedEthnicity); 
			      }else{
			    	  pform.setSpanOrigin(null); 
			      }
			 }
			 QueueUtil queueUtil = new QueueUtil();
			 public ArrayList<Object> getInvestigatorDropDowns(Collection<Object> invSummaryVOs) {
					Map<Object, Object>  invMap = new HashMap<Object,Object>();
					if (invSummaryVOs != null) {
						Iterator<Object> iter = invSummaryVOs.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
							
							String firstName = vo.getInvestigatorFirstName()== null? NEDSSConstants.NO_FIRST_NAME_INVESTIGATOR : vo.getInvestigatorFirstName();
							String lastName = vo.getInvestigatorLastName() == null? NEDSSConstants.NO_LAST_NAME_INVESTIGATOR : vo.getInvestigatorLastName();
							String invName = lastName + ", " +firstName;
							if (vo.getInvestigator() != null && vo.getInvestigator().trim() != "") {
								invMap.put(vo.getInvestigator(), invName);
							}
							if(vo.getInvestigator() == null || vo.getInvestigator().trim().equals("")){
								invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
							}
						}

					}

					return queueUtil.getUniqueValueFromMap(invMap);
				}

				public ArrayList<Object> getJurisDropDowns(Collection<Object> invSummaryVOs) {
					Map<Object, Object>  invMap = new HashMap<Object,Object>();
					if (invSummaryVOs != null) {
						Iterator<Object>  iter = invSummaryVOs.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
							if (vo.getJurisdictionCd() != null) {
								invMap.put(vo.getJurisdictionCd(), vo
										.getJurisdiction());
							}

						}

					}

					return queueUtil.getUniqueValueFromMap(invMap);
				}

				public ArrayList<Object> getConditionDropDowns(Collection<Object> invSummaryVOs) {
					Map<Object, Object>  invMap = new HashMap<Object,Object>();
					if (invSummaryVOs != null) {
						Iterator<Object>  iter = invSummaryVOs.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
							if (vo.getCd() != null) {
								invMap.put(vo.getCd(), vo
										.getCondition());
							}
						}
					}
					return queueUtil.getUniqueValueFromMap(invMap);
				}
			    /*
			     * We seem to be using two code sets, PHC_CLASS and PHVS_PHC_CLASS.
			     * Code 2931005 is coming over with a null caseClassCdTxt value in View File. 
			     * Added check for null text but need to address issue.-gt
			     * Causes ERROR gov.cdc.nedss.webapp.nbs.action.util.QueueUtil  - Error in String Comparison
			     */
				public ArrayList<Object> getCaseStatusDropDowns(Collection<Object> invSummaryVOs) {
					Map<Object, Object>  invMap = new HashMap<Object,Object>();
					if (invSummaryVOs != null) {
						Iterator<Object>  iter = invSummaryVOs.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
							if (vo.getCaseStatusCd() != null && vo.getCaseStatus() != null) {
								invMap.put(vo.getCaseStatusCd(), vo
										.getCaseStatus());
							}
							if(vo.getCaseStatusCd() == null || vo.getCaseStatusCd().trim().equals("")){
								invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
							}
						}
					}
					return queueUtil.getUniqueValueFromMap(invMap);
				}
				public ArrayList<Object> getNotificationValuesfromColl(Collection<Object> invSummaryVOs) {
					Map<Object, Object>  invMap = new HashMap<Object,Object>();
					if (invSummaryVOs != null) {
						Iterator<Object>  iter = invSummaryVOs.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
							if (vo.getNotificationCd() != null) {
								invMap.put(vo.getNotificationCd(), vo.getNotification());
							}
							if(vo.getNotificationCd() == null || (vo.getNotificationCd() !=null && vo.getNotificationCd().trim().equals(""))){
								invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
							}
						}
					}
					return queueUtil.getUniqueValueFromMap(invMap);
				}
				
				public ArrayList<Object> getObservationType(Collection<Object>  observationReviewColl){
					Map<Object, Object>  obsTypeMap = new HashMap<Object,Object>();
						obsTypeMap.put("Lab Report", "Lab Report");
					return queueUtil.getUniqueValueFromMap(obsTypeMap);
				}
				
				
				public ArrayList<Object> getResultedTestandCondition(Collection<Object>  observationReviewColl){
					Map<Object, Object>  rTestMap = new HashMap<Object,Object>();
					if (observationReviewColl != null) {
						Iterator<Object>  iter = observationReviewColl.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO obsSummaryVO = (PatientSrchResultVO)iter.next();
							
							for(int i=0; obsSummaryVO.getConditions()!=null && i<obsSummaryVO.getConditions().size(); i++){
								if(obsSummaryVO.getConditions() != null && obsSummaryVO.getConditions().get(i)!=null){
								
									rTestMap = getTestsfromObs(obsSummaryVO.getConditions().get(i),rTestMap);
								}
								if(obsSummaryVO.getConditions() == null || obsSummaryVO.getConditions().get(i)==null || obsSummaryVO.getConditions().get(i).trim().equals("")){
									rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
								}
							}
							if(obsSummaryVO.getConditions()!=null && obsSummaryVO.getConditions().size()==0)
								rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
							
						}
					}
					return queueUtil.getUniqueValueFromMap(rTestMap); 
				}
				
				
				////Pruthvi : change
				public ArrayList<Object> getResultedDescription(Collection<Object>  observationReviewColl){
					Map<Object, Object>  rTestMap = new HashMap<Object,Object>();
					if (observationReviewColl != null) {
						Iterator<Object>  iter = observationReviewColl.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO obsSummaryVO = (PatientSrchResultVO)iter.next();
							
							for(int i=0; obsSummaryVO.getDescriptions()!=null && i<obsSummaryVO.getDescriptions().size(); i++){
								if(obsSummaryVO.getDescriptions() != null && obsSummaryVO.getDescriptions().get(i)!=null){
								
									rTestMap = getTestsfromObs(obsSummaryVO.getDescriptions().get(i),rTestMap);
								}
								if(obsSummaryVO.getDescriptions() == null || obsSummaryVO.getDescriptions().get(i)==null || obsSummaryVO.getDescriptions().get(i).trim().equals("")){
									rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
								}
							}
							if(obsSummaryVO.getDescriptions()!=null && obsSummaryVO.getDescriptions().size()==0)
								rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
							
						}
					}
					return queueUtil.getUniqueValueFromMap(rTestMap); 
				}
				private void updateInvestigationSummaryVObeforeSort(Collection<Object> invSummaryVOs, String sortMethod) {
					Iterator<Object> iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getInvestigator() == null || (invSummaryVO.getInvestigator() != null && invSummaryVO.getInvestigator().equals(""))) {
							if("getInvestigator".equalsIgnoreCase(sortMethod))	invSummaryVO.setInvestigator("");
							else invSummaryVO.setInvestigator("ZZZZZ");
						}

						if (invSummaryVO.getCaseStatus() == null || (invSummaryVO.getCaseStatus() != null && invSummaryVO.getCaseStatus().equals(""))) {
							if("getCaseStatus".equalsIgnoreCase(sortMethod)) invSummaryVO.setCaseStatus("");
							else invSummaryVO.setCaseStatus("ZZZZZ");
						}
						if(invSummaryVO.getStartDate() == null || (invSummaryVO.getStartDate()!= null && invSummaryVO.equals(""))) {
							   Timestamp ts = new Timestamp (0) ; 
							   Calendar cal = GregorianCalendar.getInstance(); 
							   cal.setTimeInMillis (0); 
							   cal.set( 5000,0,1) ; 
							   ts.setTime(cal.getTimeInMillis()); 
							   invSummaryVO.setStartDate(ts);
						   } 
						if (invSummaryVO.getNotification() == null || (invSummaryVO.getNotification() != null && invSummaryVO.getNotification().equals(""))){
							if("getNotification".equalsIgnoreCase(sortMethod)) invSummaryVO.setNotification("");
							else invSummaryVO.setNotification("ZZZZZ");
						}
						
					
					}
					
				}
				private void updateInvestigationSummaryVOAfterSort(Collection<Object> invSummaryVOs) {
					Iterator<Object> iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getInvestigator() != null && invSummaryVO.getInvestigator().equals("ZZZZZ")) {
							invSummaryVO.setInvestigator("");
						}
						if (invSummaryVO.getCaseStatus() != null && invSummaryVO.getCaseStatus().equals("ZZZZZ")) {
							invSummaryVO.setCaseStatus("");
						}
						if (invSummaryVO.getNotification() != null && invSummaryVO.getNotification().equals("ZZZZZ")) {
							invSummaryVO.setNotification("");
						}
					}
					
				}
				private void _updateSummaryVosForDate(Collection<Object> invSummaryVOs) {
					
					Iterator<Object> iter = invSummaryVOs.iterator();
					while (iter.hasNext()) {
						PatientSrchResultVO invSummaryVO = (PatientSrchResultVO)iter.next();
						if (invSummaryVO.getStartDate() != null && invSummaryVO.getStartDate().getTime() >  System.currentTimeMillis()) {
							invSummaryVO.setStartDate(null);
						}
					}
				}

				public void updateMarkAsReviewdPermission(NBSSecurityObj nbsSecurityObj, PersonSearchForm psForm, ArrayList list,
						HttpServletRequest request) {
					String programArea="";
					String jurisdiction="";
					String sharedInd="";
					String permissionLab="";
			    	String uid="";
			    	boolean permissionsLab = true;
			    	boolean permissions=false;
				    
					if (null != list && list.size() > 0) {
						Iterator<Object> iter = list.iterator();
						while (iter.hasNext()) {
							PatientSrchResultVO vo = (PatientSrchResultVO)iter.next();
							programArea= vo.getProgramAreaCode();
							jurisdiction= vo.getJurisdictionCd();
							sharedInd =  ProgramAreaJurisdictionUtil.SHAREDISTRUE;
							uid=vo.getObservationUid()+"";
			    			permissionsLab = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.MARKREVIEWED,programArea,jurisdiction, sharedInd);
			    			if(!permissionsLab)	permissionLab+=uid+"|";
			    			else  permissions=true;			    	
			    		}			
					} else {
						//if records size is empty (last processed)
						permissions = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.MARKREVIEWED);
					}

					psForm.getAttributeMap().put("permissionLab",permissionLab);
					psForm.getAttributeMap().put("permissionMarkAsReviewed",permissions);
					
					
				}
				
				
		    	public void sortObservationsQueue(PersonSearchForm psForm, Collection<Object>  observationList, boolean existing, HttpServletRequest request) throws Exception {
		    		String sortMethod = "getStartDate";
		       		boolean invDirectionFlag = true;
	    			String sortOrderParam = this.getSortPatient(invDirectionFlag == true ? "1" : "2", sortMethod);
		    		if(sortOrderParam!= null && sortOrderParam.indexOf("Start Date") > -1 )
		    		   sortOrderParam = sortOrderParam.replace("Start Date", "Date Received");
		    		Map<Object, Object>  searchCriteriaColl = new TreeMap<Object,Object>();
		    		searchCriteriaColl.put("sortSt", sortOrderParam);
		    		psForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		       	}
					
				
}