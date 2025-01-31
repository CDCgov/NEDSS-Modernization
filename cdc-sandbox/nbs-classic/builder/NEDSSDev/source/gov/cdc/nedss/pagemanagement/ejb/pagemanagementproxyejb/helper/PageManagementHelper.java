package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.helper;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.dt.NbsRdbMetadataDT;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.localfields.dt.NndMetadataDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSPageDAOImpl;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.localfields.ejb.dao.NBSRdbMetadataDAOImpl;
import gov.cdc.nedss.localfields.ejb.dao.NBSUIMetaDataDAOImpl;
import gov.cdc.nedss.localfields.ejb.dao.NNDMetadataDAOImpl;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.pagemanagement.wa.dao.ConditionDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaRdbMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML;
import gov.cdc.nedss.pagemanagement.wa.xml.transform.GenerateJSPTabs;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class PageManagementHelper 
{
	public PageManagementHelper() {}
	// For logging
	static final LogUtils logger = new LogUtils(PageManagementHelper.class.getName());
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final String xml2jspDirectory = new StringBuffer(propertiesDirectory).append("xmltojsp").append(File.separator).toString().intern();
	static final int BUFFER = 2048;
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();

	
	
	/**
	 * copyDataMartRepeatNumberInRepeatingSubsections: copy the datamart repeat number from subsection to the repeating questions
	 * @param pageElementVOCollection
	 */
	public void copyDataMartRepeatNumberFromRepeatingSubsectionsToQuestion(Collection<Object> pageElementVOCollection){
		
		Iterator iter = pageElementVOCollection.iterator();
		Map<String, PageElementVO> mapElements = new HashMap<String, PageElementVO>();
		while(iter.hasNext()){
			
			PageElementVO pageElement = (PageElementVO)iter.next();
			WaRdbMetadataDT waRdb = pageElement.getWaRdbMetadataDT();
			WaUiMetadataDT waUi = pageElement.getWaUiMetadataDT();
			
			
			if(waUi.getNbsUiComponentUid()==1016 && waUi.getQuestionGroupSeqNbr()!=null){//repeating subsection
				String groupSeq = waUi.getQuestionGroupSeqNbr()+"";
				mapElements.put(groupSeq,pageElement);
			}
		}
		
		Iterator iter2 = pageElementVOCollection.iterator();
		
		while(iter2.hasNext()){
			
			PageElementVO pageElement = (PageElementVO)iter2.next();
			WaRdbMetadataDT waRdb = pageElement.getWaRdbMetadataDT();
			WaUiMetadataDT waUi = pageElement.getWaUiMetadataDT();
			
			if(waUi!=null && waUi.getNbsUiComponentUid()!=1016 && waUi.getQuestionGroupSeqNbr()!=null){//repeating subsection
				String groupSeq = waUi.getQuestionGroupSeqNbr()+"";
				PageElementVO pageElement2 = mapElements.get(groupSeq);
				if(pageElement2.getWaUiMetadataDT()!=null && waRdb!=null)
					waRdb.setDataMartRepeatNbr(pageElement2.getWaUiMetadataDT().getDataMartRepeatNumber());
				
				if(pageElement2.getWaUiMetadataDT()!=null)
					waUi.setBlockName(pageElement2.getWaUiMetadataDT().getBlockName());
			
				
			}
			else
				if(waUi.getQuestionGroupSeqNbr()==null){//No repeating block
					if(waUi!=null)
						waUi.setBlockName(null);
					if(waRdb!=null){
						waRdb.setBlockName(null);
						waRdb.setDataMartRepeatNbr(null);		
					}
			
				}
		}
	}
	
	public void copyDataMartRepeatNumberInRepeatingSubsectionsFromRepeatingQuestions(Collection<Object> pageElementVOCollection){
		
		Iterator iter = pageElementVOCollection.iterator();
		Map<String, PageElementVO> mapElements = new HashMap<String, PageElementVO>();
		while(iter.hasNext()){
			
			PageElementVO pageElement = (PageElementVO)iter.next();
			WaUiMetadataDT waUi = pageElement.getWaUiMetadataDT();
			WaRdbMetadataDT waRdb = pageElement.getWaRdbMetadataDT();
			
			
			if(waUi!=null && waUi.getNbsUiComponentUid()!=1016 && waUi.getQuestionGroupSeqNbr()!=null && waRdb!=null && waRdb.getDataMartRepeatNbr()!=null){//question in repeating subsection
				String groupSeq = waUi.getQuestionGroupSeqNbr()+"";
				mapElements.put(groupSeq,pageElement);
			}
		}
		
		Iterator iter2 = pageElementVOCollection.iterator();
		
		if(mapElements!=null && mapElements.size()>0)
		while(iter2.hasNext()){
			
			PageElementVO pageElement = (PageElementVO)iter2.next();
			WaUiMetadataDT waUi = pageElement.getWaUiMetadataDT();
			WaRdbMetadataDT waRdb = pageElement.getWaRdbMetadataDT();
			//copy only if there's no a value in Data Mart Repeat block in Subsection
			if(waUi!=null && waUi.getNbsUiComponentUid()==1016 && waUi.getQuestionGroupSeqNbr()!=null && waUi.getDataMartRepeatNumber()==null){//repeating subsection
				String groupSeq = waUi.getQuestionGroupSeqNbr()+"";
				PageElementVO pageElement2 = mapElements.get(groupSeq);
				if(pageElement2!=null && pageElement2.getWaRdbMetadataDT()!=null)
				waUi.setDataMartRepeatNumber(pageElement2.getWaRdbMetadataDT().getDataMartRepeatNbr());
			}
		}
	}
	
	
	public void publishPageMetadataToNBS( PageManagementProxyVO pageManagementProxyVO,  Long currentUser, byte[] jspPayload,boolean publishedBefore) 
	{
		Collection<Object> pageElementVOCollection = pageManagementProxyVO.getThePageElementVOCollection();
		WaTemplateDT waTemplateDT = pageManagementProxyVO.getWaTemplateDT();
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
		NBSUIMetaDataDAOImpl nbsUIMetaDataDAOImpl = new NBSUIMetaDataDAOImpl();
		NNDMetadataDAOImpl nndMetadataDAOImpl = new NNDMetadataDAOImpl();
		NBSRdbMetadataDAOImpl nbsRdbMetadataDAOImpl = new NBSRdbMetadataDAOImpl();
		NBSPageDAOImpl nbsPageDAOImpl = new NBSPageDAOImpl();
		Long nbsPageUid = null;
		Timestamp currentTime = new Timestamp(new Date().getTime());
			
		// Look for existing NBS_Page entry for this page - if found then update, otherwise create a new page
		//NbsPageDT existingNbsPageDT = nbsPageDAOImpl.findNBSPageByTemplateUid(waTemplateDT.getWaTemplateUid());
		NbsPageDT existingNbsPageDT = nbsPageDAOImpl.findNBSPageByConditionCd(waTemplateDT.getFormCd());
		if (existingNbsPageDT.getNbsPageUid() != null) {
			nbsPageUid = existingNbsPageDT.getNbsPageUid();
		}

		NbsPageDT nbsPageDT = new NbsPageDT(waTemplateDT);
		nbsPageDT.setLastChgTime(currentTime);
		nbsPageDT.setLastChgUserId(currentUser);
		nbsPageDT.setRecordStatusTime(currentTime);
		nbsPageDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
		nbsPageDT.setJspPayload(jspPayload);

		// Version_ctrl_nbr for nbs_ui_metadata
		int nbsUiMetadataVersionCtrlNbr = -1;

		if (nbsPageUid != null) {
			// Update existing NBS_page (this is a re-publish)
			nbsPageDT.setNbsPageUid(nbsPageUid);
			nbsPageDAOImpl.updateNBSPage(nbsPageDT);

			// Move existing data in RDB and NND for this page to history and get next version_ctrl_nbr
			nbsRdbMetadataDAOImpl.updateNBSRdbMetadataHistoryByNbsPageUid(nbsPageUid);		
			nbsRdbMetadataDAOImpl.deleteByNbsPageUid(nbsPageUid);
			if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE) ||
					pageManagementProxyVO.getWaTemplateDT().getBusObjType().equalsIgnoreCase(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE)) {
				nndMetadataDAOImpl.updateNNDMetadataHistoryByNBSPageUid(nbsPageUid);		
				nndMetadataDAOImpl.deleteByNbsPageUid(nbsPageUid);
			}
			nbsUiMetadataVersionCtrlNbr = nbsUIMetaDataDAOImpl.getVersionCtrlNbrForPage(nbsPageUid);

			// Prep NBS_ui_metadata table:  Move previous (by nbs_page_uid) to history and Delete all non-questions from active (where question_identifier is null) 
			nbsUIMetaDataDAOImpl.updateNBSUiMetadataHistoryByNbsPageUid(nbsPageUid);
			
			// Delete the static element
			nbsUIMetaDataDAOImpl.deleteStaticElementByNbsPageUid(nbsPageUid);
		} else {
			nbsPageUid = nbsPageDAOImpl.createNBSPage(nbsPageDT);
			// New page, so it'll be version 1
			nbsUiMetadataVersionCtrlNbr = 1;
		}

		// Check porting required indicator - if TRUE - don't set form code, otherwise, a new page, no porting, publish form code
		// Publish/Update Investigation form code to Condition code table
		ConditionDAOImpl conditionDAO = new ConditionDAOImpl();
		/*
		 * We have to include the following if condition  after the porting is completed. As per the discussion with Jit
		 * For Beta we are not giving the porting.  For 4.0 GA , include this if condition - HA 04/07/2010  
		 */
		
		//if(waTemplateDT.getPortReqIndCd() != null && !(waTemplateDT.getPortReqIndCd().equalsIgnoreCase(NEDSSConstants.TRUE))){
			String serverRestart = propertyUtil.getServerRestart();
			Collection<Object> pageCondMapColl = (Collection<Object>)pageManagementProxyVO.getPageCondMappingColl();
			//gst-Only looking at this for Investigation pages, not Contact or Interview
			if(pageCondMapColl!=null && pageManagementProxyVO.getWaTemplateDT().getBusObjType().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE)){
				
				Iterator<Object> iter = pageCondMapColl.iterator();
				while(iter.hasNext()){
					PageCondMappingDT pageCondMappDT = (PageCondMappingDT)iter.next();
					
					String portReqInd = conditionDAO.conditionPortInd(pageCondMappDT.getConditionCd());				
					//publishedBefore = conditionDAO.conditionPublishedBefore(pageCondMappDT.getConditionCd());
					if(portReqInd!= null && !portReqInd.equalsIgnoreCase(NEDSSConstants.TRUE)){
						if (serverRestart != null && !serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE) && !publishedBefore){
							conditionDAO.updateConditionForPublishedPage(pageCondMappDT.getConditionCd(), waTemplateDT.getFormCd(), waTemplateDT.getMessageId(),"P");
						}
						else if (serverRestart != null && serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE)){
							conditionDAO.updateConditionForPublishedPage(pageCondMappDT.getConditionCd(), waTemplateDT.getFormCd(), waTemplateDT.getMessageId(),null);
						}
					}
				}
			}
				
		//}
		
		//Iterate through pageElementVO collection and publish data to NBS_Question, NBS_ui_metadata, NBS_rdb_metadata and NND_metadata
		Iterator<Object> iter = pageElementVOCollection.iterator();
		while (iter.hasNext()) {
			Long nbsQuestionUid = null;
			Long nbsUiMetadataUid = null;
			PageElementVO pageElementVO = (PageElementVO)iter.next();
			WaQuestionDT waQuestionDT = pageElementVO.getWaQuestionDT();		
			
			//Update/Insert NBS_Question's with Wa_question data
			if (waQuestionDT.getQuestionIdentifier() != null && waQuestionDT.getQuestionIdentifier().trim().length() > 0) {
			
				nbsQuestionUid = publishWAQuestionToNBS(waQuestionDT, currentUser, currentTime);
			}
			//Update/Insert NBS_UI_metadata
			if (pageElementVO.getWaUiMetadataDT() != null) {
				WaUiMetadataDT waUiMetadataDT = pageElementVO.getWaUiMetadataDT();
				NbsUiMetadataDT existingNbsUiMetadataDT = null;
				if (waUiMetadataDT.getQuestionIdentifier() != null) {
				
					existingNbsUiMetadataDT = nbsUIMetaDataDAOImpl.findNBSUiMetadataByQuestionIdentifier(waUiMetadataDT.getQuestionIdentifier(), nbsPageUid);
				}
				NbsUiMetadataDT nbsUiMetadataDT = new NbsUiMetadataDT(waUiMetadataDT);
				nbsUiMetadataDT.setNbsQuestionUid(nbsQuestionUid);
				nbsUiMetadataDT.setNbsPageUid(nbsPageUid);
				nbsUiMetadataDT.setLastChgTime(currentTime);				
				nbsUiMetadataDT.setLastChgUserId(currentUser);
				nbsUiMetadataDT.setRecordStatusTime(currentTime);
				nbsUiMetadataDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
				nbsUiMetadataDT.setVersionCtrlNbr(nbsUiMetadataVersionCtrlNbr);
				nbsUiMetadataDT.setInvestigationFormCd(waTemplateDT.getFormCd());
				nbsUiMetadataDT.setAdminComment(waUiMetadataDT.getAdminComment());
				nbsUiMetadataDT.setCodeSetGroupId(waUiMetadataDT.getCodeSetGroupId());
				nbsUiMetadataDT.setDataCd(waUiMetadataDT.getDataCd());
				nbsUiMetadataDT.setDataLocation(waUiMetadataDT.getDataLocation());
				nbsUiMetadataDT.setDataType(waUiMetadataDT.getDataType());
				nbsUiMetadataDT.setDataUseCd(waUiMetadataDT.getDataUseCd());
				nbsUiMetadataDT.setCoinfectionIndCd(waUiMetadataDT.getCoinfectionIndCd());
				nbsUiMetadataDT.setBlockName(waUiMetadataDT.getBlockName());
				if (existingNbsUiMetadataDT != null && existingNbsUiMetadataDT.getNbsUiMetadataUid() != null) {
					//Existing entry, need to update
					nbsUiMetadataDT.setAddTime(existingNbsUiMetadataDT.getAddTime());
					nbsUiMetadataDT.setAddUserId(existingNbsUiMetadataDT.getAddUserId());
					nbsUiMetadataDT.setNbsUiMetadataUid(existingNbsUiMetadataDT.getNbsUiMetadataUid());
					//nbsUiMetadataDT.setNbsQuestionUid(existingNbsUiMetadataDT.getNbsQuestionUid());
					nbsUIMetaDataDAOImpl.updateNBSUiMetadataBase(nbsUiMetadataDT);
					nbsUiMetadataUid = existingNbsUiMetadataDT.getNbsUiMetadataUid();
				} else{
					//New entry, straight insert
					nbsUiMetadataDT.setAddTime(currentTime);
					nbsUiMetadataDT.setAddUserId(currentUser);
					nbsUiMetadataUid = nbsUIMetaDataDAOImpl.createNBSUiMetadataBase(nbsUiMetadataDT);
				}
			}
			//Update/Insert NBS_rdb_metadata  - Here we are always inserting the data?
			if (pageElementVO.getWaRdbMetadataDT() != null) {
				WaRdbMetadataDT waRdbMetadataDT = pageElementVO.getWaRdbMetadataDT();
				NbsRdbMetadataDT nbsRdbMetadataDT = new NbsRdbMetadataDT();
				nbsRdbMetadataDT.setNbsPageUid(nbsPageUid);
				nbsRdbMetadataDT.setNbsUiMetadataUid(nbsUiMetadataUid);
				nbsRdbMetadataDT.setRdbTableNm(waRdbMetadataDT.getRdbTableNm());
				nbsRdbMetadataDT.setUserDefinedColumnNm(waRdbMetadataDT.getUserDefinedColumnNm());
				nbsRdbMetadataDT.setRdbColumnNm(waRdbMetadataDT.getRdbcolumNm());
				nbsRdbMetadataDT.setRptAdminColumnNm(waRdbMetadataDT.getRptAdminColumnNm());
				nbsRdbMetadataDT.setLastChgTime(currentTime);
				nbsRdbMetadataDT.setLastChgUserId(currentUser);
				nbsRdbMetadataDT.setRecordStatusTime(currentTime);
				nbsRdbMetadataDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
				nbsRdbMetadataDT.setDataMartRepeatNbr(waRdbMetadataDT.getDataMartRepeatNbr());
				nbsRdbMetadataDAOImpl.createNBSRdbMetadata(nbsRdbMetadataDT);
			}
			//Update/Insert NND_metadata gst- if Investigation Page
			if (pageElementVO.getWaNndMetadataDT() != null &&
					(pageManagementProxyVO.getWaTemplateDT().getBusObjType().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE) ||
							pageManagementProxyVO.getWaTemplateDT().getBusObjType().equalsIgnoreCase(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE))) {
				WaNndMetadataDT waNndMetadataDT = pageElementVO.getWaNndMetadataDT();
				NndMetadataDT nndMetadataDT = new NndMetadataDT(waNndMetadataDT);
				nndMetadataDT.setNbsPageUid(nbsPageUid);
				nndMetadataDT.setNbsUiMetadataUid(nbsUiMetadataUid);
				nndMetadataDT.setAddTime(currentTime);
				nndMetadataDT.setAddUserId(currentUser);
				nndMetadataDT.setLastChgTime(currentTime);
				nndMetadataDT.setLastChgUserId(currentUser);
				nndMetadataDT.setRecordStatusTime(currentTime);
				nndMetadataDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
				nndMetadataDT.setHL7SegmentField(waNndMetadataDT.getHl7SegmentField());
				nndMetadataDT.setInvestigationFormCd(waTemplateDT.getFormCd()); // Do we need this for NND? HA
				nndMetadataDT.setQuestionIdentifierNnd(waNndMetadataDT.getQuestionIdentifierNnd());
				nndMetadataDT.setQuestionLabelNnd(waNndMetadataDT.getQuestionLabelNnd());
				nndMetadataDT.setQuestionRequiredNnd(waNndMetadataDT.getQuestionRequiredNnd());
				nndMetadataDT.setQuestionDataTypeNnd(waNndMetadataDT.getQuestionDataTypeNnd());
				nndMetadataDT.setOrderGroupId(waNndMetadataDT.getOrderGroupId());
				nndMetadataDT.setTranslationTableNm(waNndMetadataDT.getTranslationTableNm());
				nndMetadataDT.setQuestionIdentifier(waNndMetadataDT.getQuestionIdentifier());
				nndMetadataDT.setMsgTriggerIndCd(waNndMetadataDT.getMsgTriggerIndCd());
				nndMetadataDT.setXmlPath(waNndMetadataDT.getXmlPath());
				nndMetadataDT.setXmlTag(waNndMetadataDT.getXmlTag());
				nndMetadataDT.setXmlDataType(waNndMetadataDT.getXmlDataType());
				nndMetadataDT.setPartTypeCd(waNndMetadataDT.getPartTypeCd());
				nndMetadataDT.setRepeatGroupSeqNbr(waNndMetadataDT.getRepeatGroupSeqNbr());
				nndMetadataDT.setQuestionOrderNnd(waNndMetadataDT.getQuestionOrderNnd());
				nndMetadataDT.setQuestionMap(waNndMetadataDT.getQuestionMap());
				nndMetadataDT.setIndicatorCd(waNndMetadataDT.getIndicatorCd());
				nndMetadataDAOImpl.createNNDMetadata(nndMetadataDT);
			}
		}
		
		//Set publish_ind_cd for WA_ui_metadata rows for this page to true
		pageManagementDAOImpl.updateWaUiMetadataToPublished(waTemplateDT.getWaTemplateUid());
	}
	
	
    private Long publishWAQuestionToNBS(WaQuestionDT waQuestionDT, Long currentUser, Timestamp currentTime)
    {
        NBSQuestionDAOImpl nbsQuestionDAOImpl = new NBSQuestionDAOImpl();

        // Look up to see if there is an existing NBS_question for the incoming
        // question (using question_identifier)
        NbsQuestionDT existingNbsQuestionDT = nbsQuestionDAOImpl.findNBSQuestionByQuestionIdentifier(waQuestionDT
                .getQuestionIdentifier());
        Long nbsQuestionUid = existingNbsQuestionDT.getNbsQuestionUid();

        // Map WaQuestionDT to NbsQuestionDT
        NbsQuestionDT nbsQuestionDT = new NbsQuestionDT(waQuestionDT);
        nbsQuestionDT.setNbsQuestionUid(nbsQuestionUid);

        // If new question- create it, otherwise update the existing question
        if (nbsQuestionUid == null)
        {
            nbsQuestionDT.setAddTime(currentTime);
            nbsQuestionDT.setAddUserId(currentUser);
            nbsQuestionDT.setLastChgTime(currentTime);
            nbsQuestionDT.setLastChgUserId(currentUser);
            nbsQuestionDT.setRecordStatusTime(currentTime);
            nbsQuestionDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
            nbsQuestionDT.setVersionCtrlNbr(1);
            nbsQuestionUid = nbsQuestionDAOImpl.createNBSQuestionBase(nbsQuestionDT);
        }
        else
        {
            nbsQuestionDT.setLastChgTime(currentTime);
            nbsQuestionDT.setLastChgUserId(currentUser);
            nbsQuestionDT.setRecordStatusTime(currentTime);
            nbsQuestionDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
            nbsQuestionDT.setVersionCtrlNbr(existingNbsQuestionDT.getVersionCtrlNbr() + 1);
            nbsQuestionDAOImpl.updateNBSQuestion(nbsQuestionDT);
        }
        return nbsQuestionUid;
    }
	
	public Collection<Object> orderAndSyncPageElements(Collection<Object> pageElementVOCollection, String pageElementOrder) throws NEDSSSystemException {
		Collection<Object> sequencedPageElements = new ArrayList<Object>(); 
		Collection<Object> nonOrderedColl = new ArrayList<Object>();
		Integer orderNbr = 1;
		
		HashMap<Object,Object> pageElementMap = new HashMap<Object,Object>();
		
        try
        {
            Iterator<Object> pageElementIter = pageElementVOCollection.iterator();
            while (pageElementIter.hasNext())
            {
                PageElementVO pageElementVO = (PageElementVO) pageElementIter.next();
                // The elements which we are not showing on the UI
                WaUiMetadataDT waUiDt = pageElementVO.getWaUiMetadataDT();
                if (waUiDt != null && waUiDt.getStandardNndIndCd() != null && waUiDt.getStandardNndIndCd().equals("T"))
                {
                    nonOrderedColl.add(pageElementVO);
                }
                else
                {
                    // The elements which are on the UI
                    pageElementMap.put(pageElementVO.getPageElementUid(), pageElementVO);
                }
            }

            // Parse through uidString and search for each uid in
            // pageElementMap, update the orderNbr in the WaUiMetaDataDT and put
            // in collection to return to persist
            StringTokenizer st = new StringTokenizer(pageElementOrder, ",\n");
            while (st.hasMoreTokens())
            {
                Long nextUid = new Long(st.nextToken());
                PageElementVO pageElementVO = (PageElementVO) pageElementMap.get(nextUid);

                pageElementVO.getWaUiMetadataDT().setOrderNbr(orderNbr++);

                sequencedPageElements.add(pageElementVO);
            }
            // Adding the nonUI Elements at the end
            sequencedPageElements.addAll(nonOrderedColl);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new NEDSSSystemException(ex.getMessage());
        }
		return sequencedPageElements;
	}
	
	public void generateXMLandJSP(PageManagementProxyVO pageManagementProxyVO,Long waTemplateUid) throws NEDSSSystemException {
		boolean isSavedtoDB = false;  
		 MarshallPageXML marshallPage = new MarshallPageXML();
		 String xmlwritedir=xml2jspDirectory + "pageOut.xml";
	         String templateType = marshallPage.GeneratePageXMLFile(pageManagementProxyVO, xmlwritedir);	
	        // String returnStr="";
			if (!templateType.isEmpty()) {
				if (!templateType.contains("Published"))
					logger.error("Error marshalling page to XML when saving in Page Builder");
			} else{
				//start writing the xml to database
				try {
					DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
				    dfactory.setNamespaceAware(true);
				    Document xmlDoc = null;
				    try {
				   // xmlDoc = dfactory.newDocumentBuilder().parse("c:/temp/pageOut.xml");
   				//DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

   				InputSource is = new InputSource(xmlwritedir);

   				Document document = dfactory.newDocumentBuilder().parse(is);

   				StringWriter sw = new StringWriter();

   				Transformer serializer = TransformerFactory.newInstance().newTransformer();

   				serializer.transform(new DOMSource(document), new StreamResult(sw));

   				String xmlContents=sw.toString();
   				
				    isSavedtoDB = saveXMLtoDB(xmlContents,waTemplateUid);
				    } catch (DOMException ex ){	
				    	logger.error("SAX Exception: " + ex.getMessage());
				    } catch (IOException ex) {
				    	logger.error("IOException: " + ex.getMessage());
					} catch (ParserConfigurationException ex) {
						logger.error("ParserConfigurationException: " + ex.getMessage());
					}
		              
				}catch(Exception e) {
					logger.error("Exception: " + e.getMessage());
				    e.printStackTrace();
				    throw new NEDSSSystemException(e.getMessage());
			  }
			//end of writing xml to database 

				if(isSavedtoDB){  				
					String dir = pageManagementProxyVO.getWaTemplateDT().getBusObjType();
					if(pageManagementProxyVO.getWaTemplateDT().getConditionCd() != null && !pageManagementProxyVO.getWaTemplateDT().getConditionCd().equals(""))
					dir = dir + "_" + pageManagementProxyVO.getWaTemplateDT().getConditionCd();
   				String theBusinessObject = NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE;
   				if (pageManagementProxyVO.getWaTemplateDT().getBusObjType() != null)
   					theBusinessObject = pageManagementProxyVO.getWaTemplateDT().getBusObjType();
   				String indexfile = "";
   				String indexviewfile = "";
   				GenerateJSPTabs genTabs = new GenerateJSPTabs();
                if (theBusinessObject.equalsIgnoreCase(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE))
                {
                    indexfile = xml2jspDirectory + "DMB_Generate_Contact_Index_JSP.xsl";
                    indexviewfile = xml2jspDirectory + "DMB_Generate_View_Contact_Index_JSP.xsl";
                }
                else if (theBusinessObject.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
                {
                    indexfile = xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
                    indexviewfile = xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
                }
                else if (theBusinessObject.equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE)) 
                {
                    indexfile = xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
                    indexviewfile = xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
                }
                else if (theBusinessObject.equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE))
                {
                    indexfile = xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
                    indexviewfile = xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
                }
                else
                {
                    indexfile = xml2jspDirectory + "DMB_Generate_Index_JSP.xsl";
                    indexviewfile = xml2jspDirectory + "DMB_Generate_View_Index_JSP.xsl";
                }
   				//same tab translate is used..
   				String tabfile=xml2jspDirectory + "DMB_Generate_Tab_JSP.xsl";
   				String tabviewfile=xml2jspDirectory + "DMB_Generate_View_Tab_JSP.xsl";
   				
   				//start creating the jsp's from  xml
   				try{
   					genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexfile,NEDSSConstants.CREATE,dir,NEDSSConstants.PUBLISH,theBusinessObject) ;		
 	   				genTabs.processTabs(xmlwritedir, "/util:PageInfo/Page/PageTab", tabfile,NEDSSConstants.CREATE,dir,NEDSSConstants.PUBLISH,theBusinessObject) ;
 	   				
 	   				genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexviewfile,NEDSSConstants.VIEW,dir,NEDSSConstants.PUBLISH,theBusinessObject) ;		
 	   				genTabs.processTabs(xmlwritedir, "/util:PageInfo/Page/PageTab", tabviewfile,NEDSSConstants.VIEW,dir,NEDSSConstants.PUBLISH,theBusinessObject ) ;

   				} catch (Exception ex) {
   					logger.error("Exception: " + ex.getMessage());
   					throw new NEDSSSystemException(ex.getMessage());
   				}
			} //savedtoDb
		} //published
	}
	
	public boolean saveXMLtoDB(String XMLContents,Long waTemplateUid) 
	{
		boolean saveStatus = false;
		//if (!nbsSecurityObj.getPermission(NBSBOLookup.PAGEMANAGEMENT, NBSOperationLookup.VIEW)) {
		//logger.info("nbsSecurityObj.getPermission(NedssBOLookup.PAGEMANAGEMENT,NBSOperationLookup.VIEW) is false");
		//throw new NEDSSSystemException("NO PERMISSIONS");
		//}
		//logger.info("nbsSecurityObj.getPermission(NedssBOLookup.PAGEMANAGEMENT,NBSOperationLookup.VIEW) is true");
		
		//TODO:  For now, just mark WaTemplate inactive - probably should mark all associated metadata inactive too
        try
        {
            PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
            saveStatus = pageManagementDAOImpl.saveXMLtoDB(XMLContents, waTemplateUid);
        }
        catch (Exception e)
        {
            logger.error("PageManagementProxyEJB.inactivatePage:  Error while inactivating Page for WaTemplate uid:  "
                    + waTemplateUid + " - " + e.getMessage());
            throw new NEDSSSystemException(e.getMessage());
        }
        return saveStatus;
    }
	
    public String generateXMLPayLoad(PageManagementProxyVO pageManagementProxyVO, Long waTemplateUid)
            throws NEDSSSystemException
    {
        boolean isSavedtoDB = false;
        String xmlContents = "";
        MarshallPageXML marshallPage = new MarshallPageXML();
        String xmlwritedir = xml2jspDirectory + "pageOut.xml";
        String returnStr = marshallPage.GeneratePageXMLFile(pageManagementProxyVO, xmlwritedir);
        // String returnStr="";
        if (returnStr.contains("Error"))
        {
            logger.error("Error marshalling page to XML when saving in Page Builder");
        }
        else
        {
            // start writing the xml to database
            try
            {
                DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
                dfactory.setNamespaceAware(true);
                Document xmlDoc = null;
                try
                {
                    // xmlDoc =
                    // dfactory.newDocumentBuilder().parse("c:/temp/pageOut.xml");
                    // DocumentBuilderFactory docBuilderFactory =
                    // DocumentBuilderFactory.newInstance();

                    InputSource is = new InputSource(xmlwritedir);

                    Document document = dfactory.newDocumentBuilder().parse(is);

                    StringWriter sw = new StringWriter();

                    Transformer serializer = TransformerFactory.newInstance().newTransformer();

                    serializer.transform(new DOMSource(document), new StreamResult(sw));

                    xmlContents = sw.toString();

                }
                catch (DOMException ex)
                {
                    logger.error("SAX Exception: " + ex.getMessage());
                }
                catch (IOException ex)
                {
                    logger.error("IOException: " + ex.getMessage());
                }
                catch (ParserConfigurationException ex)
                {
                    logger.error("ParserConfigurationException: " + ex.getMessage());
                }
            }
            catch (Exception e)
            {
                logger.error("Exception: " + e.getMessage());
                e.printStackTrace();
                throw new NEDSSSystemException(e.getMessage());
            }
            // end of writing xml to database
        }
        return xmlContents;
    }
	
	public File generateJSPPayLoad(PageManagementProxyVO pageManagementProxyVO,Long waTemplateUid) throws NEDSSSystemException
	{
		boolean isSavedtoDB = false; 	
		String xmlwritedir=xml2jspDirectory + "pageOut.xml";	
		File nbsDirectoryPath = new File("");
		File JspPayLoad = new File("");
				  				
		String dir = "temp"; //was pageManagementProxyVO.getWaTemplateDT().getBusObjType();
		if(pageManagementProxyVO.getWaTemplateDT().getFormCd()!= null && !pageManagementProxyVO.getWaTemplateDT().getFormCd().equals(""))
			dir = pageManagementProxyVO.getWaTemplateDT().getFormCd();
   				
   		GenerateJSPTabs genTabs = new GenerateJSPTabs();
   		String indexfile = "";
   		String indexviewfile = "";
   		String indexfilemerge = "";
   		String indexviewfilecompare = "";
   		
   				//gst route to correct index xsls
   		if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equals(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE)) 
		{
   			indexfile=xml2jspDirectory + "DMB_Generate_Contact_Index_JSP.xsl";
   			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Contact_Index_JSP.xsl";
   		} 
		else if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE)) 
		{
			indexfile=xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
   		}
		else if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE)) 
		{
			indexfile=xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
   		}
		else if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) 
		{
			indexfile=xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
   		}
		else if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equals(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE)) 
		{
			indexfile=xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
   		}
		else if (pageManagementProxyVO.getWaTemplateDT().getBusObjType().equals(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE)) 
		{
			indexfile=xml2jspDirectory + "DMB_Generate_Generic_Index_JSP.xsl";
			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Generic_Index_JSP.xsl";
   		}
		else 
		{
			indexfile=xml2jspDirectory + "DMB_Generate_Index_JSP.xsl"; //Investigation
   			indexviewfile=xml2jspDirectory + "DMB_Generate_View_Index_JSP.xsl";
   			indexfilemerge=xml2jspDirectory + "DMB_Generate_Merge_Index_JSP.xsl"; //Investigation
   			indexviewfilecompare=xml2jspDirectory + "DMB_Generate_Compare_Index_JSP.xsl";
   			
   		}
		String tabfile=xml2jspDirectory + "DMB_Generate_Tab_JSP.xsl";
		String tabviewfile=xml2jspDirectory + "DMB_Generate_View_Tab_JSP.xsl";
		String tabcomparefile=xml2jspDirectory + "DMB_Generate_Compare_Tab_JSP.xsl";
		String tabcompare2file=xml2jspDirectory + "DMB_Generate_Compare2_Tab_JSP.xsl";
		String tabmergefile=xml2jspDirectory + "DMB_Generate_Merge_Tab_JSP.xsl";
		String tabmerge2file=xml2jspDirectory + "DMB_Generate_Merge2_Tab_JSP.xsl";
		
		
		String businessObjectType = NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE; //default
		if (pageManagementProxyVO.getWaTemplateDT().getBusObjType() != null)
			businessObjectType = pageManagementProxyVO.getWaTemplateDT().getBusObjType(); //could be Contact or Interview
   				//start creating the jsp's from  xml
		try{ 				    
			genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexfile,NEDSSConstants.CREATE,dir,NEDSSConstants.PUBLISH, businessObjectType) ;		
			genTabs.processTabs(xmlwritedir, "/util:PageInfo/Page/PageTab", tabfile,NEDSSConstants.CREATE,dir,NEDSSConstants.PUBLISH, businessObjectType ) ;
			
			genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexviewfile,NEDSSConstants.VIEW,dir,NEDSSConstants.PUBLISH,businessObjectType ) ;	
			genTabs.processTabs(xmlwritedir, "/util:PageInfo/Page/PageTab", tabviewfile,NEDSSConstants.VIEW,dir,NEDSSConstants.PUBLISH, businessObjectType ) ;
			
			if((NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE).equals(businessObjectType)){
				//Generates compare files in view mode inside View directory 
				genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexviewfilecompare,"compare",dir,NEDSSConstants.PUBLISH,businessObjectType ) ;	
				genTabs.processTabsCompare(xmlwritedir, "/util:PageInfo/Page", tabcomparefile,tabcompare2file, "compare",dir,NEDSSConstants.PUBLISH, businessObjectType ) ;
			
				//Generates merge files in edit mode inside condition directory
				genTabs.processIndex(xmlwritedir, "/util:PageInfo/Page", indexfilemerge,"merge",dir,NEDSSConstants.PUBLISH,businessObjectType ) ;		
				genTabs.processTabsCompare(xmlwritedir, "/util:PageInfo/Page/PageTab", tabmergefile, tabmerge2file, "merge",dir,NEDSSConstants.PUBLISH, businessObjectType ) ;
				
				
			}
			
			
			String directory = "pagemanagement";
			String folderNm = dir; 	    	
			nbsDirectoryPath = new File(PropertyUtil.nedssDir + directory + File.separator + folderNm + File.separator);
	   		
			try 
		    { 	     
				//call the zipDir method 
				zipDir(nbsDirectoryPath.toString(),nbsDirectoryPath.toString()+File.separator + folderNm); 
			} 
			catch(Exception e) 
			{ 
				logger.error("Exception: " + e.getMessage(),e);
			} 
			JspPayLoad = new File(nbsDirectoryPath.toString()+ File.separator + folderNm+".zip");
			
			 
   			//genTabs.processIndex("c:/temp/pageOut.xml", "/util:PageInfo/Page", "c:/aNBS/aProto/DMB_Generate_Index_JSP.xsl","Create",dir ) ;		
   			//genTabs.processTabs("c:/temp/pageOut.xml", "/util:PageInfo/Page/PageTab", "c:/aNBS/aProto/DMB_Generate_Tab_JSP.xsl","Create",dir ) ;
   				
   			//genTabs.processIndex("c:/temp/pageOut.xml", "/util:PageInfo/Page", "c:/aNBS/aProto/DMB_Generate_View_Index_JSP.xsl","View",dir ) ;		
   			//genTabs.processTabs("c:/temp/pageOut.xml", "/util:PageInfo/Page/PageTab", "c:/aNBS/aProto/DMB_Generate_View_Tab_JSP.xsl","View",dir ) ;
   		} 
		catch (Exception ex) 
		{
   			logger.fatal("Exception: " + ex.getMessage(),ex);
   			throw new NEDSSSystemException(ex.getMessage());
   		}
   		return JspPayLoad;		
	}
	
	//here is the code for the method 
	  /*public void zipDir(String dir2zip,String dir) 
	  { 
		 
		  try
		     {
		     File inFolder=new File(dir2zip);
		     File outFolder=new File(dir+".zip");
		     ZipOutputStream out = new ZipOutputStream(new 
		BufferedOutputStream(new FileOutputStream(outFolder)));
		     BufferedInputStream in = null;
		     byte[] data    = new byte[1000];
		     String files[] = inFolder.list();
		     for (int i=0; i<files.length; i++)
		      {
		      in = new BufferedInputStream(new FileInputStream
		(inFolder.getPath() + "/" + files[i]), 1000);                  
		out.putNextEntry(new ZipEntry(files[i])); 
		      int count;
		      while((count = in.read(data,0,1000)) != -1)
		      {
		           out.write(data, 0, count);
		          }
		      out.closeEntry();
		      }
		      out.flush();
		      out.close();
		      }
		      catch(Exception e)
		         {
		              e.printStackTrace();
		          } 
		     }*/
	  
	private static void zipDir(String dir, String zipFileName)
			throws IOException {
		File dirObj = new File(dir);
		ZipOutputStream out = null;
		if (!dirObj.isDirectory()) {
			System.err.println(dir + " is not a directory");
			System.exit(1);
		}
		try {
			out = new ZipOutputStream(
					new FileOutputStream(zipFileName + ".zip"));
			System.out.println("Creating : " + zipFileName);
			addDir(dirObj, out);
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Could not completely read file " + e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Could not completely read file " + e);
		} finally {
			if (out != null)
				out.close();
		}

	}

    private static void addDir(File dirObj, ZipOutputStream out) throws IOException, Exception
    {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[1024];
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isDirectory())
            {
                addDir(files[i], out);
                continue;
            }
            if (!files[i].getPath().endsWith(".zip"))
            {
                FileInputStream in = new FileInputStream(files[i].getPath());
                System.out.println(" Adding: " + files[i].getPath());
                out.putNextEntry(new ZipEntry(files[i].getPath()));
                // Transfer from the file to the ZIP file
                int len;
                while ((len = in.read(tmpBuf)) > 0)
                {
                    out.write(tmpBuf, 0, len);
                }
                // Complete the entry
                out.closeEntry();
                in.close();
            }
        }
    }

    public static byte[] getBytesFromFile(File file) throws IOException
    {
        try
        {
            InputStream is = new FileInputStream(file);

            long length = file.length();
            byte[] bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;

            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
                offset += numRead;
            }

            if (offset < bytes.length)
            {
                throw new IOException("Could not completely read file " + file.getName());
            }

            is.close();
            return bytes;
        }
        catch (Exception e)
        {
            logger.error("Exception: " + e.getMessage());
            throw new NEDSSSystemException(e.toString());
        }
    }
   
    public void deleteJspFiles(String pgFormCode)
    {
        try
        {
            PagePublisher.getInstance();
            deleteDirectory(new File(PagePublisher.getSourceDirectoryPath(pgFormCode)));
        }
        catch (Exception e)
        {
            logger.error("Exception: " + e.getMessage());
        }
    }

    static public boolean deleteDirectory(File path)
    {
        try
        {
            if (path.exists())
            {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    if (files[i].isDirectory())
                    {
                        deleteDirectory(files[i]);
                    }
                    else
                    {
                        files[i].delete();
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Exception: " + e.getMessage());
        }
        return (path.delete());
	}
    
}
