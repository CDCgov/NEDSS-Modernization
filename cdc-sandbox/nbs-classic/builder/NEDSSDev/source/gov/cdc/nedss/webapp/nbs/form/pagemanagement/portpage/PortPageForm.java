package gov.cdc.nedss.webapp.nbs.form.pagemanagement.portpage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.upload.FormFile;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import gov.cdc.nedss.page.ejb.portproxyejb.dt.AnswerMappingDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.util.PBtoPBConverterProcessor;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

/**
 * @author PatelDh
 *
 */
public class PortPageForm extends BaseForm {
	
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(PortPageForm.class.getName());
	
	//Map has question for display logic.
	private HashMap<String, MappedFromToQuestionFieldsDT> displayToQuestionsMap = new HashMap<String, MappedFromToQuestionFieldsDT> ();
	private HashMap<String, MappedFromToQuestionFieldsDT> displayFromQuestionsMap = new HashMap<String, MappedFromToQuestionFieldsDT> ();
	//Map has question for mapping logic.
	private HashMap<String, ArrayList<Object>> mappedQuestionsMap = new HashMap<String, ArrayList<Object>> ();
	private String fromPageFormCd;
	private String toPageFormCd;
	private Long fromPageWaTemplateUid;
	private Long toPageWaTemplateUid;
	private MappedFromToQuestionFieldsDT currentQuestion = new MappedFromToQuestionFieldsDT();
	private ArrayList<Object> toPageQuestions =  new ArrayList<Object>();
	private ArrayList<Object> fromPageQuestions =  new ArrayList<Object>();
	private String selectedConditionCode;
	private ArrayList<Object> toPageAnswerList =  new ArrayList<Object>();
	private ArrayList<Object> fromPageAnswerList =  new ArrayList<Object>();
	private ArrayList<Object> portStatus = new ArrayList<Object> ();
	private ArrayList<Object> map = new ArrayList<Object> ();
	private ArrayList<Object> frmDataType=new ArrayList<Object>();
	private ArrayList<Object> toDataType=new ArrayList<Object>();
	private ArrayList<Object> answerStatus = new ArrayList<Object> ();
	private ArrayList<Object> answerMap = new ArrayList<Object>();
	private ArrayList<Object> revFromDataType = new ArrayList<Object>();
	private ArrayList<Object> revMapQuestion = new ArrayList<Object>();
	private ArrayList<Object> revToDataType = new ArrayList<Object>();
	private ArrayList<Object> revMapAnswer = new ArrayList<Object>();
	private int mapNeededCount=0;
	private int ansMapNeededCount=0;
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private String mapName;
	private ArrayList<Object> toPageListDD = new ArrayList<Object>();                        //used in Port Page to display the To Page Drop Down.
	private ArrayList<Object> fromPageListDD = new ArrayList<Object>();                     //used in Port Page to display the From Page Drop Down.
	private Long nbsConversionPageMgmtUid;
	private boolean lockMapping = false;
	private FormFile importFile;
    private String mappingType = PortPageUtil.MAPPING_PAGEBUILDER;
    private String busObjType;
    private ArrayList<Object> sn_UNITQuestionList =  new ArrayList<Object>();
    private HashMap<String, String> toQuestionMappingTypesMap = new HashMap<String, String> (); // This map is use to check constraint on 'TO' Page repeating block question. Repeating to Repeating mapping.
    
	public FormFile getImportFile() {
		return importFile;
	}
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	public Long getFromPageWaTemplateUid() {
		return fromPageWaTemplateUid;
	}
	public void setFromPageWaTemplateUid(Long fromPageWaTemplateUid) {
		this.fromPageWaTemplateUid = fromPageWaTemplateUid;
	}
	public Long getToPageWaTemplateUid() {
		return toPageWaTemplateUid;
	}
	public void setToPageWaTemplateUid(Long toPageWaTemplateUid) {
		this.toPageWaTemplateUid = toPageWaTemplateUid;
	}
	public ArrayList<Object> getToPageListDD() {
		return toPageListDD;
	}
	public void setToPageListDD(ArrayList<Object> toPageListDD) {
		this.toPageListDD = toPageListDD;
	}
	public ArrayList<Object> getFromPageListDD() {
		return fromPageListDD;
	}
	public void setFromPageListDD(ArrayList<Object> fromPageListDD) {
		this.fromPageListDD = fromPageListDD;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public int getAnsMapNeededCount() {
		return ansMapNeededCount;
	}
	public void setAnsMapNeededCount(int ansMapNeededCount) {
		this.ansMapNeededCount = ansMapNeededCount;
	}
	public ArrayList<Object> getRevFromDataType() {
		return revFromDataType;
	}
	public void setRevFromDataType(ArrayList<Object> revFromDataType) {
		this.revFromDataType = revFromDataType;
	}
	public ArrayList<Object> getRevMapQuestion() {
		return revMapQuestion;
	}
	public void setRevMapQuestion(ArrayList<Object> revMapQuestion) {
		this.revMapQuestion = revMapQuestion;
	}
	public ArrayList<Object> getRevToDataType() {
		return revToDataType;
	}
	public void setRevToDataType(ArrayList<Object> revToDataType) {
		this.revToDataType = revToDataType;
	}
	public ArrayList<Object> getRevMapAnswer() {
		return revMapAnswer;
	}
	public void setRevMapAnswer(ArrayList<Object> revMapAnswer) {
		this.revMapAnswer = revMapAnswer;
	}
	public int getMapNeededCount() {
		return mapNeededCount;
	}
	public void setMapNeededCount(int mapNeededCount) {
		this.mapNeededCount = mapNeededCount;
	}

	private ArrayList<Object> mappedQuestionAnswerListForReview =  new ArrayList<Object>();
	
	public ArrayList<Object> getAnswerStatus() {
		return answerStatus;
	}
	public void setAnswerStatus(ArrayList<Object> answerStatus) {
		this.answerStatus = answerStatus;
	}
	public ArrayList<Object> getAnswerMap() {
		return answerMap;
	}
	public void setAnswerMap(ArrayList<Object> answerMap) {
		this.answerMap = answerMap;
	}

	public HashMap<String, MappedFromToQuestionFieldsDT> getDisplayToQuestionsMap() {
		return displayToQuestionsMap;
	}
	public void setDisplayToQuestionsMap(
			HashMap<String, MappedFromToQuestionFieldsDT> displayToQuestionsMap) {
		this.displayToQuestionsMap = displayToQuestionsMap;
	}
	public HashMap<String, MappedFromToQuestionFieldsDT> getDisplayFromQuestionsMap() {
		return displayFromQuestionsMap;
	}
	public void setDisplayFromQuestionsMap(
			HashMap<String, MappedFromToQuestionFieldsDT> displayFromQuestionsMap) {
		this.displayFromQuestionsMap = displayFromQuestionsMap;
	}
	public HashMap<String, ArrayList<Object>> getMappedQuestionsMap() {
		return mappedQuestionsMap;
	}
	public void setMappedQuestionsMap(
			HashMap<String, ArrayList<Object>> mappedQuestionsMap) {
		this.mappedQuestionsMap = mappedQuestionsMap;
	}
	
	public String getSelectedConditionCode() {
		return selectedConditionCode;
	}
	public void setSelectedConditionCode(String selectedConditionCode) {
		this.selectedConditionCode = selectedConditionCode;
	}
	public String getFromPageFormCd() {
		return fromPageFormCd;
	}
	public void setFromPageFormCd(String fromPageFormCd) {
		this.fromPageFormCd = fromPageFormCd;
	}
	public String getToPageFormCd() {
		return toPageFormCd;
	}
	public void setToPageFormCd(String toPageFormCd) {
		this.toPageFormCd = toPageFormCd;
	}
	public MappedFromToQuestionFieldsDT getCurrentQuestion() {
		return currentQuestion;
	}
	public void setCurrentQuestion(MappedFromToQuestionFieldsDT currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	public ArrayList<Object> getToPageQuestions() {
		return toPageQuestions;
	}
	public void setToPageQuestions(ArrayList<Object> toPageQuestions) {
		this.toPageQuestions = toPageQuestions;
	}
	public ArrayList<Object> getFromPageQuestions() {
		return fromPageQuestions;
	}
	public void setFromPageQuestions(ArrayList<Object> fromPageQuestions) {
		this.fromPageQuestions = fromPageQuestions;
	}
	public ArrayList<Object> getToPageAnswerList() {
		return toPageAnswerList;
	}
	public void setToPageAnswerList(ArrayList<Object> toPageAnswerList) {
		this.toPageAnswerList = toPageAnswerList;
	}
	public ArrayList<Object> getFromPageAnswerList() {
		return fromPageAnswerList;
	}
	public void setFromPageAnswerList(ArrayList<Object> fromPageAnswerList) {
		this.fromPageAnswerList = fromPageAnswerList;
	}
	public ArrayList<Object> getMappedQuestionAnswerListForReview() {
		return mappedQuestionAnswerListForReview;
	}
	public void setMappedQuestionAnswerListForReview(ArrayList<Object> mappedQuestionAnswerListForReview) {
		this.mappedQuestionAnswerListForReview = mappedQuestionAnswerListForReview;
	}
	public Long getNbsConversionPageMgmtUid() {
		return nbsConversionPageMgmtUid;
	}
	public void setNbsConversionPageMgmtUid(Long nbsConversionPageMgmtUid) {
		this.nbsConversionPageMgmtUid = nbsConversionPageMgmtUid;
	}
	public boolean isLockMapping() {
		return lockMapping;
	}
	public void setLockMapping(boolean lockMapping) {
		this.lockMapping = lockMapping;
	}
	//Methods for PortPage
	/*The following method is to get Page Description,MMG and Related Conditions of From Page and To Page based on waTemplateUID*/
	public ArrayList<String> getPageFieldsByUid(Long uid){
		logger.debug("uid: "+uid);
		String mmg="";
		String descTxt="";
		ArrayList<String> fieldList = new ArrayList<String>();
		WaTemplateVO waTemplateVO = new WaTemplateVO();
		try{
			PageManagementActionUtil util = new PageManagementActionUtil();
			WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();
			HttpSession session = request.getSession();
			
			waTemplateVO = util.getPageDetails(uid, session);                            //getting VO
			
			if(waTemplateVO!=null){
				mmg = waTemplateVO.getWaTemplateDT().getMessageId();
				
				if(mmg!=null){
					fieldList.add(mmg);
				}else{
					fieldList.add("");                                      
				}                                          //adding Message Mapping Guide to list
	
				descTxt= waTemplateVO.getWaTemplateDT().getDescTxt();
				if(descTxt==null){
					fieldList.add("");
				}else{
					fieldList.add(descTxt);                                      //adding Page Description to list
				}
			}
			ArrayList<Object> pageCondList=new ArrayList<Object>();
			String relCond="";
			pageCondList.addAll(waTemplateVO.getWaPageCondMappingDTColl());
		    if(!pageCondList.isEmpty()){
				for(int i=0;i<pageCondList.size();i++){
					PageCondMappingDT pcmDT=(PageCondMappingDT)pageCondList.get(i);
				    relCond=relCond+pcmDT.getConditionDesc()+"("+pcmDT.getConditionCd()+")"+"<br>";                 //adding conditondesc and condition code in form of string 
				}
				fieldList.add(relCond);
		    }else{
		    	fieldList.add("");
		    }
			
		}catch(Exception e){
			logger.error("Error in calling the getPageDetails, uid: "+uid+", Exception: "+e.getMessage(),e);
		}
		return fieldList;
	}
	
	public ArrayList<Object> getToPageList(String FormCd){
		ArrayList<Object> toPageList=new ArrayList<Object>();
		try{
			for(int i=0;i<toPageListDD.size();i++){
			    DropDownCodeDT ddDT= (DropDownCodeDT)toPageListDD.get(i);
			       if(!FormCd.equals(ddDT.getKey())){
			        	toPageList.add(ddDT);
			       }
			}
		}catch(Exception e){
			logger.error("Error in calling the getToPageList, FormCd: "+FormCd+", Exception: "+e.getMessage(),e);
		}
		return toPageList;

	}
	
	////////////////////////////////////////
	
	
	
	public ArrayList<String> getToQuestionFields(String fromQuestionId,String toQuestionId){
		logger.debug("fromQuestionId: "+fromQuestionId+", toQuestionId: "+toQuestionId);
		ArrayList<String> toQuestionFieldList = new ArrayList<String>();
		try{
			MappedFromToQuestionFieldsDT toQuestionField = displayToQuestionsMap.get(toQuestionId);
			
			// To disable Repeating Block Number textbox for repeating to repeating mapping. Get from question's repeating group sequence number.
			MappedFromToQuestionFieldsDT fromQuestion = displayFromQuestionsMap.get(fromQuestionId);
 			String fromQuestionGroupSeqNbr = "";
			if(fromQuestion!=null && fromQuestion.getQuestionGroupSeqNbr()!=null)
				fromQuestionGroupSeqNbr = Integer.toString(fromQuestion.getQuestionGroupSeqNbr());
			
			if(toQuestionField!=null){
				toQuestionFieldList.add(toQuestionField.getFromLabel());
				toQuestionFieldList.add(toQuestionField.getFromDataType());
				if(toQuestionField.getFromCodeSetNm()!=null)
					toQuestionFieldList.add(toQuestionField.getFromCodeSetNm());
				else
					toQuestionFieldList.add("");

                if(toQuestionField.getQuestionGroupSeqNbr()==null){
                	toQuestionFieldList.add("");
                    toQuestionFieldList.add("");
                }
                else{
                	toQuestionFieldList.add(toQuestionField.getQuestionGroupSeqNbr().toString());
                	if(toQuestionField.getBlockIdNbr()==null){
                		Integer blockID=1;
                	toQuestionFieldList.add(blockID.toString());
                	toQuestionFieldList.add("");
    				toQuestionFieldList.add(fromQuestionGroupSeqNbr);
                	}else{
                		ArrayList<Object> mappedQueList = mappedQuestionsMap.get(fromQuestionId);
            			if(mappedQueList!=null && mappedQueList.size()>0){
            				MappedFromToQuestionFieldsDT mappedDT = (MappedFromToQuestionFieldsDT) mappedQueList.get(0);
            			             if(mappedDT.getToQuestionId()!=null && mappedDT.getToQuestionId().equals(toQuestionId)){
            			            	 toQuestionFieldList.add(mappedDT.getBlockIdNbr().toString());
            			            	 toQuestionFieldList.add("true");
            			            	 toQuestionFieldList.add(fromQuestionGroupSeqNbr);
            			             }else{
            			            	 // For Repeating to repeating mapping keep blockIdNbr 1, even if user change the mapping for it.
            			            	 if(fromQuestion.getQuestionGroupSeqNbr()!=null && toQuestionField.getQuestionGroupSeqNbr()!=null)
            			            		 toQuestionFieldList.add(PortPageUtil.BLOCK_ID_NBR_FOR_REP_TO_REP_MAPPING);
            			            	 else
            			            		 toQuestionFieldList.add(Integer.toString(1+toQuestionField.getBlockIdNbr()));
            			            	 toQuestionFieldList.add("false");
            			            	 toQuestionFieldList.add(fromQuestionGroupSeqNbr);
            			             }
            			}else{
            				if(fromQuestion.getQuestionGroupSeqNbr()!=null && toQuestionField.getQuestionGroupSeqNbr()!=null)
			            		 toQuestionFieldList.add(PortPageUtil.BLOCK_ID_NBR_FOR_REP_TO_REP_MAPPING);
			            	 else
			            		 toQuestionFieldList.add(Integer.toString(1+toQuestionField.getBlockIdNbr()));
            				toQuestionFieldList.add("false");
            				toQuestionFieldList.add(fromQuestionGroupSeqNbr);
            			}
                		
                	}
                	}
				
			}else{
				toQuestionFieldList.add("");
				toQuestionFieldList.add("");
				toQuestionFieldList.add("");
				toQuestionFieldList.add("");
				toQuestionFieldList.add("");
				toQuestionFieldList.add("");
				toQuestionFieldList.add(fromQuestionGroupSeqNbr);
			}
		}catch(Exception ex){
    		logger.error("Error in DWR call to getToQuestionFields method of PortPageForm class,fromQuestionId: "+fromQuestionId+", toQuestionId: "+toQuestionId+", Exception: " +ex.getMessage(), ex);
    	}
		return toQuestionFieldList;
	}
	
	public ArrayList<Object> getToQuestionAndValuesets(String fromQuestionId, String fromCode){
		logger.debug("fromQuestionId: "+fromQuestionId+", fromCode:"+fromCode);
		ArrayList<Object> toQuestionFieldList = new ArrayList<Object>();
		ArrayList<Object> toCodedropDownList = new ArrayList<Object>();
	
		try{
			ArrayList<Object> toQuestionListDD = mappedQuestionsMap.get(fromQuestionId);

			if(toQuestionListDD!=null && toQuestionListDD.size()>0){
				for(int j = 0; j<toQuestionListDD.size();j++){
					MappedFromToQuestionFieldsDT toQuestionField = (MappedFromToQuestionFieldsDT) toQuestionListDD.get(j);
					if(toQuestionField.getToCodeSetNm()!=null && toQuestionField.getToCodeSetNm().length()!= 0){
						DropDownCodeDT dDownDT = new DropDownCodeDT();
						dDownDT.setKey(""); dDownDT.setValue("");
						toCodedropDownList.add(dDownDT);
						if(toQuestionField!=null){
							String toCodeSetNm = toQuestionField.getToCodeSetNm();
							for(int i=0;i<toPageAnswerList.size();i++){
								AnswerMappingDT dt = (AnswerMappingDT) toPageAnswerList.get(i);
								if(dt!=null && dt.getQuestionIdentifier().equals(toQuestionField.getToQuestionId()) && dt.getCodeSetNm()!=null && dt.getCodeSetNm().equalsIgnoreCase(toCodeSetNm)){
									DropDownCodeDT ddDT = new DropDownCodeDT();
									ddDT.setKey(dt.getCode());
									ddDT.setValue(dt.getCode() +" : "+ dt.getCodeDescTxt());
									toCodedropDownList.add(ddDT);
								}
							}

							break;
						}
					}
				}
			}
	    // toCodedropDownList;
		
			ArrayList<Object> toQuestionList = mappedQuestionsMap.get(fromQuestionId);
			if(toQuestionList!=null && toQuestionList.size()>0){
				for(int j = 0; j<toQuestionList.size();j++){
				MappedFromToQuestionFieldsDT toQuestionField = (MappedFromToQuestionFieldsDT) toQuestionList.get(j);
				if(toQuestionField!=null && toQuestionField.getToQuestionId() != null && toQuestionField.getToQuestionId().length()!=0 ){
					toQuestionFieldList.add(toQuestionField.getFromDataType());
					toQuestionFieldList.add(toQuestionField.getToQuestionId());
					toQuestionFieldList.add(toQuestionField.getToLabel());
					toQuestionFieldList.add(toQuestionField.getToDataType());
					if(toQuestionField.getToCodeSetNm()!=null){
						toQuestionFieldList.add(toQuestionField.getToCodeSetNm());
					}else{
						toQuestionFieldList.add("");
					}
					for(int i=0;i<fromPageAnswerList.size();i++){
						AnswerMappingDT ansDT = (AnswerMappingDT) fromPageAnswerList.get(i);
						if(fromQuestionId.equals(ansDT.getQuestionIdentifier()) && fromCode.equals(ansDT.getCode())){
							toQuestionFieldList.add(ansDT.getToCode());
							toQuestionFieldList.add(ansDT.getToCodeDescTxt());
						}
					}
					if(toQuestionFieldList.size()==5){
						toQuestionFieldList.add("");
						toQuestionFieldList.add("");
					}
					toQuestionFieldList.add(toCodedropDownList);
					break;
				}
				}
				if(toQuestionFieldList.size() == 0){
					toQuestionFieldList.add("");
					toQuestionFieldList.add("");
					toQuestionFieldList.add("");
					toQuestionFieldList.add("");
					toQuestionFieldList.add("");
					toQuestionFieldList.add("");
					toQuestionFieldList.add("");
					toQuestionFieldList.add(toCodedropDownList);
				}
			}
		}catch(Exception ex){
    		logger.error("Error in DWR call to getToQuestionAndValuesets method of PortPageForm class, fromQuestionId: "+fromQuestionId+", fromCode:"+fromCode+ ", Exception: " +ex.getMessage(), ex);
    	}
		return toQuestionFieldList;
	}
	
	public ArrayList<Object> getToCodeDropDown(String fromQuestionId){
		logger.debug("fromQuestionId :"+fromQuestionId);
		ArrayList<Object> toCodedropDownList = new ArrayList<Object>();
		try{
			ArrayList<Object> toQuestionList = mappedQuestionsMap.get(fromQuestionId);
			if(toQuestionList!=null && toQuestionList.size()>0){
				MappedFromToQuestionFieldsDT toQuestionField = (MappedFromToQuestionFieldsDT) toQuestionList.get(0);
				DropDownCodeDT dDownDT = new DropDownCodeDT();
			    dDownDT.setKey(""); dDownDT.setValue("");
			    toCodedropDownList.add(dDownDT);
			    if(toQuestionField!=null){
			    	String toCodeSetNm = toQuestionField.getToCodeSetNm();
			        for(int i=0;i<toPageAnswerList.size();i++){
			        	AnswerMappingDT dt = (AnswerMappingDT) toPageAnswerList.get(i);
			        	if(dt!=null && dt.getQuestionIdentifier().equals(toQuestionField.getToQuestionId()) && dt.getCodeSetNm()!=null && dt.getCodeSetNm().equalsIgnoreCase(toCodeSetNm)){
			        		DropDownCodeDT ddDT = new DropDownCodeDT();
			        		ddDT.setKey(dt.getCode());
			        		ddDT.setValue(dt.getCode() +" : "+ dt.getCodeDescTxt());
			        		toCodedropDownList.add(ddDT);
			        	}
			        }
			    }
			}
		}catch(Exception ex){
    		logger.error("Error in DWR call to getToCodeDropDown method of PortPageForm class, fromQuestionId :"+fromQuestionId+", Exception: " +ex.getMessage(), ex);
    	}
	    return toCodedropDownList;
	}
	public ArrayList<Object> getToQuestionDropDown(String fromQuestionIdentifier,String dataType){
		ArrayList<Object> toQuestiondropDownList = new ArrayList<Object>();
		logger.debug("fromQuestionIdentifier :"+fromQuestionIdentifier+", dataType: "+dataType);
		try{
			DropDownCodeDT dDownDT = new DropDownCodeDT();
		    dDownDT.setKey(""); dDownDT.setValue("");
		    toQuestiondropDownList.add(dDownDT);
		    
		    String fromPartQuestionClassCd="";
		    if(PortPageUtil.DATA_TYPE_PART.equals(dataType)){
		    	fromPartQuestionClassCd = PortPageUtil.getParticipationSubjectClassCd(fromQuestionIdentifier);
		    }
		    
			for(int i=0; i<toPageQuestions.size();i++){
				MappedFromToQuestionFieldsDT toQuestionDT = (MappedFromToQuestionFieldsDT) toPageQuestions.get(i);
				if(toQuestionDT!=null){
					DropDownCodeDT ddDT = new DropDownCodeDT();
	        		ddDT.setKey(toQuestionDT.getFromQuestionId());
	        		ddDT.setValue(toQuestionDT.getFromQuestionId() +" : "+ toQuestionDT.getFromLabel());

	        		/*When From Question is of DATE type ,then the To Question dropdown will show only DATE type questions and  when 
	        		From Question's are of type other than DATE,then the To Question dropdown will be questions of all datatypes other than DATE type questions.*/
	        		if(PortPageUtil.DATA_TYPE_DATE.equals(dataType) && PortPageUtil.DATA_TYPE_DATE.equals(toQuestionDT.getFromDataType())){
	        			toQuestiondropDownList.add(ddDT);
	        		}else if(PortPageUtil.DATA_TYPE_PART.equals(dataType) && PortPageUtil.DATA_TYPE_PART.equals(toQuestionDT.getFromDataType())){
	        			String toPartQuestionSubjectClassCd = PortPageUtil.getParticipationSubjectClassCd(toQuestionDT.getFromQuestionId());;
	        			if(fromPartQuestionClassCd.equals(toPartQuestionSubjectClassCd))
	        				toQuestiondropDownList.add(ddDT);
	        		}else if(!PortPageUtil.DATA_TYPE_DATE.equals(dataType)&&!PortPageUtil.DATA_TYPE_DATE.equals(toQuestionDT.getFromDataType()) 
	        				&& !PortPageUtil.DATA_TYPE_PART.equals(dataType) && !PortPageUtil.DATA_TYPE_PART.equals(toQuestionDT.getFromDataType())){
	        			toQuestiondropDownList.add(ddDT);
	        		}
				}
			}
		
		}catch(Exception ex){
    		logger.error("Error in DWR call to getToQuestionDropDown method of PortPageForm class, fromQuestionIdentifier :"+fromQuestionIdentifier+", dataType: "+dataType+", Exception: " +ex.getMessage(), ex);
    	}
		return toQuestiondropDownList;
	}
	public ArrayList<Object> getMappedToQuestionFields(String fromQuestionId, String answerGroupSeqNbr,String dataType){
		ArrayList<Object> toQuestionFieldLists = new ArrayList<Object>();
		ArrayList<Object> toQuestiondropDownList = new ArrayList<Object>();
		logger.debug("fromQuestionId :"+fromQuestionId+", answerGroupSeqNbr: "+answerGroupSeqNbr+", dataType:"+dataType);
		try{
			DropDownCodeDT dDownDT = new DropDownCodeDT();
		    dDownDT.setKey(""); dDownDT.setValue("");
		    toQuestiondropDownList.add(dDownDT);
		    
		    String fromPartQuestionClassCd="";
		    if(PortPageUtil.DATA_TYPE_PART.equals(dataType)){
		    	fromPartQuestionClassCd = PortPageUtil.getParticipationSubjectClassCd(fromQuestionId);
		    }
		    
			for(int i=0; i<toPageQuestions.size();i++){
				MappedFromToQuestionFieldsDT toQuestionDT = (MappedFromToQuestionFieldsDT) toPageQuestions.get(i);
				if(toQuestionDT!=null){
					DropDownCodeDT ddDT = new DropDownCodeDT();
	        		ddDT.setKey(toQuestionDT.getFromQuestionId());
	        		ddDT.setValue(toQuestionDT.getFromQuestionId() +" : "+ toQuestionDT.getFromLabel());

	        		//To check if question is repeating or discrete.
	        		ddDT.setIntValue(toQuestionDT.getQuestionGroupSeqNbr());

	        		/*When From Question is of DATE type ,then the To Question dropdown will show only DATE type questions and  when 
	        		From Question's are of type other than DATE,then the To Question dropdown will be questions of all datatypes other than DATE type questions.*/
	        		if(PortPageUtil.DATA_TYPE_DATE.equals(dataType) && PortPageUtil.DATA_TYPE_DATE.equals(toQuestionDT.getFromDataType())){
	        			toQuestiondropDownList.add(ddDT);
	        		}else if(PortPageUtil.DATA_TYPE_PART.equals(dataType) && PortPageUtil.DATA_TYPE_PART.equals(toQuestionDT.getFromDataType())){
	        			String toPartQuestionSubjectClassCd = PortPageUtil.getParticipationSubjectClassCd(toQuestionDT.getFromQuestionId());;
	        			if(fromPartQuestionClassCd.equals(toPartQuestionSubjectClassCd))
	        				toQuestiondropDownList.add(ddDT);
	        		}else if(!PortPageUtil.DATA_TYPE_DATE.equals(dataType)&&!PortPageUtil.DATA_TYPE_DATE.equals(toQuestionDT.getFromDataType()) 
	        				&& !PortPageUtil.DATA_TYPE_PART.equals(dataType) && !PortPageUtil.DATA_TYPE_PART.equals(toQuestionDT.getFromDataType())){
	        			toQuestiondropDownList.add(ddDT);
	        		}
				}
			}
	
			ArrayList<Object> mappedQueDTList = mappedQuestionsMap.get(fromQuestionId);
			
			if(mappedQueDTList!=null && mappedQueDTList.size()>0){
				for(int i=0;i<mappedQueDTList.size();i++){
					MappedFromToQuestionFieldsDT mappedQueDT = (MappedFromToQuestionFieldsDT) mappedQueDTList.get(i);
					
					if(mappedQueDT!=null && (mappedQueDT.getAnswerGroupSeqNbr()==null || mappedQueDT.getAnswerGroupSeqNbr().toString().equalsIgnoreCase(answerGroupSeqNbr))){ // mappedQueDT.getAnswerGroupSeqNbr() has some values for repeating block question and its null for descreate questions.
						toQuestionFieldLists.add(mappedQueDT.getToQuestionId());
						
						if(mappedQueDT.getToLabel()!=null)
							toQuestionFieldLists.add(mappedQueDT.getToLabel());
						else 
							toQuestionFieldLists.add("");
						if(mappedQueDT.getToDataType()!=null)
							toQuestionFieldLists.add(mappedQueDT.getToDataType());
						else 
							toQuestionFieldLists.add("");
						
						if(mappedQueDT.getToCodeSetNm()!=null)
							toQuestionFieldLists.add(mappedQueDT.getToCodeSetNm());
						else
							toQuestionFieldLists.add("");
						
						
						if(mappedQueDT.isCodeMappingRequired() )
							toQuestionFieldLists.add("Y");
						else if(!mappedQueDT.isCodeMappingRequired() && !PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED.equals(mappedQueDT.getStatusCode()) && !PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R.equals(mappedQueDT.getStatusCode()))           //Inorder to display Map Answer? field empty if a question has status mapping needed.
							toQuestionFieldLists.add("N");
						else
							toQuestionFieldLists.add("");
						
						if(mappedQueDT.isFieldMappingRequired())// && mappedQueDT.getToQuestionId()!=null)
							toQuestionFieldLists.add("Y");
						else
							toQuestionFieldLists.add("N");
						
		                
		                if(mappedQueDT.getBlockIdNbr()!=null && mappedQueDT.getBlockIdNbr()>=1)
	                		toQuestionFieldLists.add(mappedQueDT.getBlockIdNbr().toString());
	                    else
	                    	toQuestionFieldLists.add("");
		                if(mappedQueDT.getToQuestionId() != null && !mappedQueDT.getToQuestionId().equals("")){
		                	toQuestiondropDownList=PortPageUtil.removeIfDuplicateToQuestion(toQuestiondropDownList,mappedQueDT.getToQuestionId());
		                }
		                
		                PortPageUtil.updateToQuestionIdDropdownForRepeatingMapping(this, fromQuestionId, toQuestiondropDownList);
		                
		                toQuestionFieldLists.addAll(toQuestiondropDownList);
		                
		              break;
					}
				}
			}
			
			if(toQuestionFieldLists.size()==0){
				toQuestionFieldLists.add("");
				toQuestionFieldLists.add("");
				toQuestionFieldLists.add("");
				toQuestionFieldLists.add("");
				toQuestionFieldLists.add("");
				toQuestionFieldLists.add("");
				toQuestionFieldLists.add("");
				PortPageUtil.updateToQuestionIdDropdownForRepeatingMapping(this, fromQuestionId, toQuestiondropDownList);
				toQuestionFieldLists.addAll(toQuestiondropDownList);
				
			}
			
			
		}catch(Exception ex){
    		logger.error("Error in DWR call to getMappedToQuestionFields method of PortPageForm class, fromQuestionId :"+fromQuestionId+", answerGroupSeqNbr: "+answerGroupSeqNbr+", dataType:"+dataType+", Exception: " +ex.getMessage(), ex);
    	}
		return toQuestionFieldLists;
	}
	
	
   /* Filtering code for question page starts here*/
	public void initializeDropDowns()throws Exception {
		try{
			portStatus = getStatusDD(fromPageQuestions);
			map=getMapDD(fromPageQuestions);
			frmDataType=getFrmDataTypeDD(fromPageQuestions);
			toDataType=getToDataTypeDD(fromPageQuestions, toPageQuestions);
		}catch(Exception ex){
			logger.fatal("Exception while initializeDropDowns: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
     }
	


	public ArrayList<Object> getMap() {
		return map;
	}
	public void setMap(ArrayList<Object> map) {
		this.map = map;
	}
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}
	public ArrayList<Object> getPortStatus() {
		return portStatus;
	}
	public void setPortStatus(ArrayList<Object> portStatus) {
		this.portStatus = portStatus;
	}
	public ArrayList<Object> getFrmDataType() {
		return frmDataType;
	}
	public void setFrmDataType(ArrayList<Object> frmDataType) {
		this.frmDataType = frmDataType;
	}
	public ArrayList<Object> getToDataType() {
		return toDataType;
	}
	public void setToDataType(ArrayList<Object> toDataType) {
		this.toDataType = toDataType;
	}

	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	
	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

	public void clearAll() throws Exception {
		try{
			String fromQuestionPage = (String)(getAttributeMap().get("fromQuestionPage"));
			getAttributeMap().clear();
			searchCriteriaArrayMap = new HashMap<Object,Object>();
			getAttributeMap().put("fromQuestionPage",fromQuestionPage);
		}catch(Exception ex){
			logger.fatal("Exception while clearAll: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	public void setAnswerArray(String key, String[] answer) throws Exception {
		try{
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
		}catch(Exception ex){
			logger.fatal("Exception setAnswerArray, key:"+key+", Exception: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

	public void setAnswerArrayText(String key, String answer) throws Exception {
		try{
			if(answer!=null && answer.length() > 0) {
				String newKey = key+"_FILTER_TEXT";
					searchCriteriaArrayMap.put(newKey,HTMLEncoder.encodeHtml(answer));
			}
		}catch(Exception ex){
			logger.fatal("Exception setAnswerArrayText, key:"+key+", answer:"+answer+", Exception: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

	
	public ArrayList<Object> getStatusDD(Collection<Object>  fromPageQuestions) throws Exception{
		try{
			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (fromPageQuestions != null) {
				java.util.Iterator<?> iter = fromPageQuestions.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (mqDT.getStatusDesc()!= null) {
						statusMap.put(mqDT.getStatusDesc(), mqDT.getStatusDesc());
								
					}
				}
			}
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(getMappingType())){
				statusMap.put(PortPageUtil.STATUS_COMPLETE, PortPageUtil.STATUS_COMPLETE);
				statusMap.put(PortPageUtil.STATUS_COMPLETE_REPEATING_BLOCK, PortPageUtil.STATUS_COMPLETE_REPEATING_BLOCK);
			}
			return queueUtil.getUniqueValueFromMap(statusMap);
		}catch(Exception ex){
			logger.fatal("Exception getStatusDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

	public ArrayList<Object> getFrmDataTypeDD(Collection<Object>  fromPageQuestions) throws Exception{
		try{
			Map<Object, Object> dataMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (fromPageQuestions != null) {
				java.util.Iterator<?> iter = fromPageQuestions.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (mqDT.getFromDataType()!= null) {
						dataMap.put(mqDT.getFromDataType(), mqDT.getFromDataType());
								
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(dataMap);
		}catch(Exception ex){
			logger.fatal("Exception getFrmDataTypeDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	public ArrayList<Object> getToDataTypeDD(Collection<Object>  fromPageQuestions, Collection<Object>  toPageQuestions) throws Exception{
		try{
			Map<Object, Object> dataMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (fromPageQuestions != null) {
				java.util.Iterator<?> iter = fromPageQuestions.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (mqDT.getToDataType()!= null && !mqDT.getToDataType().equals("")) {
						dataMap.put(mqDT.getToDataType(), mqDT.getToDataType());
								
					}
				}
			}
			if (toPageQuestions != null) {
				java.util.Iterator<?> iter = toPageQuestions.iterator();
				for(int i=0; i<toPageQuestions.size();i++){
					
					while (iter.hasNext()) {
						MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
						if (mqDT.getFromDataType()!= null && !mqDT.getFromDataType().equals("")) {
							dataMap.put(mqDT.getFromDataType(), mqDT.getFromDataType());
									
						}
					}
				}
			}
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(getMappingType())){
				dataMap.put(NEDSSConstants.BLANK_KEY,NEDSSConstants.BLANK_VALUE);
			}
			return queueUtil.getUniqueValueFromMap(dataMap);
		}catch(Exception ex){
			logger.fatal("Exception getToDataTypeDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	public ArrayList<Object> getMapDD(Collection<Object>  fromPageQuestions) throws Exception{
		try{
			Map<Object, Object> mapMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (fromPageQuestions != null) {
				java.util.Iterator<?> iter = fromPageQuestions.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (PortPageUtil.MAPPED_QA_YN_Y.equals(mqDT.getQuestionMappedCode()) || PortPageUtil.MAPPED_QA_YN_N.equals(mqDT.getQuestionMappedCode())) {
						mapMap.put( mqDT.getQuestionMappedCode(),  (CachedDropDowns.getCodeDescTxtForCd(mqDT.getQuestionMappedCode(), PortPageUtil.CODE_SET_YN)));					
					}else{
						mapMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(getMappingType())){
				mapMap.put(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.ANSWER_MAPPED_NO);
				mapMap.put(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.ANSWER_MAPPED_YES);
			}
			return queueUtil.getUniqueValueFromMap(mapMap);
		}catch(Exception ex){
			logger.fatal("Exception getMapDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	//Filtering code for answer page//
	public void initializeAnswerDD()throws Exception {
		try{
			answerStatus = getAnswerStatusDD(fromPageAnswerList);
			answerMap=getAnswerMapDD(fromPageAnswerList);
		}catch(Exception ex){
			logger.fatal("Exception initializeAnswerDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
     }
	
	public ArrayList<Object> getAnswerStatusDD(Collection<Object>  fromPageAnswerList) throws Exception{
		try{
			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (fromPageAnswerList != null) {
				java.util.Iterator<?> iter = fromPageAnswerList.iterator();
				while (iter.hasNext()) {
					AnswerMappingDT amDT = (AnswerMappingDT) iter.next();
					if (amDT.getStatus()!= null) {
						statusMap.put(amDT.getStatus(), amDT.getStatus());
								
					}
				}
			}
			statusMap.put(PortPageUtil.STATUS_COMPLETE, PortPageUtil.STATUS_COMPLETE);
			return queueUtil.getUniqueValueFromMap(statusMap);
		}catch(Exception ex){
			logger.fatal("Exception getAnswerStatusDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	public ArrayList<Object> getAnswerMapDD(Collection<Object>  fromPageAnswerList) throws Exception{
		try{
			Map<Object, Object> mapMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (fromPageAnswerList != null) {
				java.util.Iterator<?> iter = fromPageAnswerList.iterator();
				while (iter.hasNext()) {
					AnswerMappingDT amDT = (AnswerMappingDT) iter.next();
					if (PortPageUtil.MAPPED_QA_YN_Y.equals(amDT.getMapped())||PortPageUtil.MAPPED_QA_YN_N.equals(amDT.getMapped())) {
						mapMap.put( amDT.getMapped(),   (CachedDropDowns.getCodeDescTxtForCd(amDT.getMapped(), PortPageUtil.CODE_SET_YN)));
								
					}else{
						mapMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			mapMap.put(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.ANSWER_MAPPED_NO);
			mapMap.put(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.ANSWER_MAPPED_YES);
			return queueUtil.getUniqueValueFromMap(mapMap);
		}catch(Exception ex){
			logger.fatal("Exception getAnswerMapDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	//Filtering code for Review Page
	public void initializeReviewDropDowns()throws Exception {
		try{
			revFromDataType = getRevFromDataTypeDD(mappedQuestionAnswerListForReview);
			revMapQuestion = getRevMapQuestionDD(mappedQuestionAnswerListForReview);
			revToDataType = getRevToDataTypeDD(mappedQuestionAnswerListForReview);
			revMapAnswer = getRevMapAnswerDD(mappedQuestionAnswerListForReview);
		}catch(Exception ex){
			logger.fatal("Exception initializeReviewDropDowns: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	public ArrayList<Object> getRevFromDataTypeDD(Collection<Object>  mappedQuestionAnswerListForReview) throws Exception{
		try{
			Map<Object, Object> dataMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (mappedQuestionAnswerListForReview != null) {
				java.util.Iterator<?> iter = mappedQuestionAnswerListForReview.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (mqDT.getFromDataType()!= null && !mqDT.getFromDataType().equals("")) {
						dataMap.put(mqDT.getFromDataType(), mqDT.getFromDataType());
								
					}
					if(mqDT.getFromDataType() == null || mqDT.getFromDataType().trim().equals("")){
						dataMap.put(NEDSSConstants.BLANK_KEY,NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(dataMap);
		}catch(Exception ex){
			logger.fatal("Exception getRevFromDataTypeDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	public ArrayList<Object> getRevMapQuestionDD(Collection<Object>  mappedQuestionAnswerListForReview) throws Exception{
		try{
			Map<Object, Object> mapMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (mappedQuestionAnswerListForReview != null) {
				java.util.Iterator<?> iter = mappedQuestionAnswerListForReview.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (PortPageUtil.MAPPED_QA_YN_Y.equals(mqDT.getQuestionMappedCode()) || PortPageUtil.MAPPED_QA_YN_N.equals(mqDT.getQuestionMappedCode())) {
						mapMap.put( mqDT.getQuestionMappedCode(),  (CachedDropDowns.getCodeDescTxtForCd(mqDT.getQuestionMappedCode(), PortPageUtil.CODE_SET_YN)));					
					}else{
						mapMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(mapMap);
		}catch(Exception ex){
			logger.fatal("Exception getRevMapQuestionDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	public ArrayList<Object> getRevToDataTypeDD(Collection<Object>  mappedQuestionAnswerListForReview) throws Exception{
		try{
			Map<Object, Object> dataMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (mappedQuestionAnswerListForReview != null) {
				java.util.Iterator<?> iter = mappedQuestionAnswerListForReview.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (mqDT.getToDataType()!= null && !mqDT.getToDataType().equals("")) {
						dataMap.put(mqDT.getToDataType(), mqDT.getToDataType());
								
					}
					if(mqDT.getToDataType() == null || mqDT.getToDataType().trim().equals("")){
						dataMap.put(NEDSSConstants.BLANK_KEY,NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(dataMap);
		}catch(Exception ex){
			logger.fatal("Exception getRevToDataTypeDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	public ArrayList<Object> getRevMapAnswerDD(Collection<Object>  mappedQuestionAnswerListForReview) throws Exception{
		try{
			Map<Object, Object> mapMap = new HashMap<Object,Object>();
			QueueUtil queueUtil = new QueueUtil();
			if (mappedQuestionAnswerListForReview != null) {
				java.util.Iterator<?> iter = mappedQuestionAnswerListForReview.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) iter.next();
					if (PortPageUtil.MAPPED_QA_YN_Y.equals(mqDT.getAnswerMappedCode()) || PortPageUtil.MAPPED_QA_YN_N.equals(mqDT.getAnswerMappedCode())) {
						mapMap.put( mqDT.getAnswerMappedCode(), (CachedDropDowns.getCodeDescTxtForCd(mqDT.getAnswerMappedCode(), PortPageUtil.CODE_SET_YN)));					
					}else{
						mapMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(mapMap);
		}catch(Exception ex){
			logger.fatal("Exception getRevMapAnswerDD: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	
	public void clearMapAndList() throws Exception {
		try{
			getAttributeMap().clear();
			searchCriteriaArrayMap = new HashMap<Object,Object>();
			displayFromQuestionsMap= new HashMap<String,MappedFromToQuestionFieldsDT>();
			displayToQuestionsMap = new HashMap<String,MappedFromToQuestionFieldsDT>();
			mappedQuestionsMap = new HashMap<String, ArrayList<Object>> ();
			fromPageQuestions = new ArrayList<Object>();
			toPageQuestions= new ArrayList<Object>();
			fromPageAnswerList = new ArrayList<Object>();
			toPageAnswerList = new ArrayList<Object>();
			portStatus = new ArrayList<Object>();
			map = new ArrayList<Object>();
			frmDataType = new ArrayList<Object>();
			toDataType = new ArrayList<Object>();
			answerStatus = new ArrayList<Object>();
			answerMap = new ArrayList<Object>();
			lockMapping = false;
			currentQuestion = new MappedFromToQuestionFieldsDT();
		}catch(Exception ex){
			logger.fatal("Exception clearMapAndList: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

	
	
	public ArrayList<Object> getPublicHealthCaseCount(String conditionCd){
		logger.debug("conditionCd :"+conditionCd);
		ArrayList<Object> conditionCount = new ArrayList<Object>();
		Integer totalCaseCount;
		Integer pendingNotificationCount;
		Integer approvedNotificationCount;
		try{
			WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();

			Object[] oParams = new Object[] {conditionCd, PortPageUtil.PORT_PAGEBUILDER};
			String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;
			String sMethod = "getNumberOfCasesToTransfer";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			totalCaseCount = (Integer) obj;
			
			Object[] notificationOParams = new Object[] {conditionCd, NEDSSConstants.NOTIFICATION_PENDING_CODE};
			String notificationSBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String notificationSMethod = "getNotificationCountByConditionAndStatus";
			Object notificationObj = CallProxyEJB.callProxyEJB(notificationOParams, notificationSBeanJndiName, notificationSMethod, request.getSession());
			pendingNotificationCount = (Integer) notificationObj;
			
			notificationOParams = new Object[] {conditionCd, NEDSSConstants.NOTIFICATION_APPROVED_CODE};
			Object approvedNotificationObj = CallProxyEJB.callProxyEJB(notificationOParams, notificationSBeanJndiName, notificationSMethod, request.getSession());
			approvedNotificationCount = (Integer) approvedNotificationObj;
			
			//Find remaining cases to convert
			Integer remainingCases = totalCaseCount;
			NBSConversionConditionDT nbsConvCondDT= PBtoPBConverterProcessor. getNbsConversionConditionDTByCondition( conditionCd,  nbsConversionPageMgmtUid,  request);
				
			if(nbsConvCondDT!=null){
				ArrayList<Object> convertedCaseList = PBtoPBConverterProcessor.getConvertedCasesFromNbsConversionMaster(nbsConvCondDT.getConditionCdGroupId(), PortPageUtil.CASE_CONVERSION_STATUS_CODE_PASS, request);
				if(convertedCaseList!=null){
					remainingCases = totalCaseCount - convertedCaseList.size();
				}
			}
			
			conditionCount.add(totalCaseCount);
			conditionCount.add(pendingNotificationCount);
			conditionCount.add(approvedNotificationCount);
			conditionCount.add(remainingCases);

		}catch(Exception ex){
    		logger.error("Error in DWR call to getToQuestionDropDown method of PortPageForm class, conditionCd :"+conditionCd+", Exception: " +ex.getMessage(), ex);
    	}
		return conditionCount;
	}
	
	public String getMappingType() {
		return mappingType;
	}
	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}
	public String getBusObjType() {
		return busObjType;
	}
	public void setBusObjType(String busObjType) {
		this.busObjType = busObjType;
	}
	public ArrayList<Object> getSn_UNITQuestionList() {
		return sn_UNITQuestionList;
	}
	public void setSn_UNITQuestionList(ArrayList<Object> sn_UNITQuestionList) {
		this.sn_UNITQuestionList = sn_UNITQuestionList;
	}
	public HashMap<String, String> getToQuestionMappingTypesMap() {
		return toQuestionMappingTypesMap;
	}
	public void setToQuestionMappingTypesMap(
			HashMap<String, String> toQuestionMappingTypesMap) {
		this.toQuestionMappingTypesMap = toQuestionMappingTypesMap;
	}
	
}
