package gov.cdc.nedss.localfields.helper;

import gov.cdc.nedss.localfields.vo.NbsQuestionVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * LocalFieldMetaDataHelper is used to update the FORM specific QuestionMap with the LDF Actions (Add, Edit, Delete)
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFieldMetaDataHelper.java
 * Sep 9, 2008
 * @version
 */
public class LocalFieldMetaDataHelper extends DAOBase {
	
	private static final LogUtils logger = new LogUtils(LocalFieldMetaDataHelper.class.getName());
	private static final ArrayList<Object> codesetNames = CachedDropDowns.getCodesetNames();
	private static TreeMap<Object,Object> codesetNmgroupMap;
	private static LocalFieldMetaDataHelper instnce = new LocalFieldMetaDataHelper();
	
	private LocalFieldMetaDataHelper() {
		if (codesetNmgroupMap == null)
			initiateCodesetMap();
	}

	public static LocalFieldMetaDataHelper getInstance() {
		return instnce;
	}	  
	  
	/**
	 * updateLDFQuestionMap method inserts/overwrites the QuestionCache Map<Object,Object> with the newly added/updated LDF from LDFAdmin
	 * @param vo
	 * @throws Exception
	 */
	public void updateLDFQuestionMap(NbsQuestionVO vo) throws Exception {
		
		NbsQuestionMetadata qMetadata = new NbsQuestionMetadata();

		//Question identifier exists for most of the LDFs that have an entry in the NBS_Question table, if it does not, create a temporary one and put
		String questionId = vo.getQuestion().getQuestionIdentifier() == null ? "" : (String)vo.getQuestion().getQuestionIdentifier();
		if(questionId.equals(""))
			questionId = "LDFTAB";
		
		qMetadata.setQuestionIdentifier(questionId);
		qMetadata.setDataType(vo.getQuestion().getDataType());
		
		String codesetGroupId = vo.getQuestion().getCodeSetGroupId() == null ? "" : vo.getQuestion().getCodeSetGroupId().toString();
		if(! codesetGroupId.equals("")) {
			qMetadata.setCodeSetGroupId(new Long(codesetGroupId));
			String codesetNm = (String)codesetNmgroupMap.get(codesetGroupId);
			qMetadata.setCodeSetNm(codesetNm);
		}

		//QuestionUid is retrieved purposefully from UI Metadata since Question table entry might be missing in certain scenarios (ex., when a new Tab is added)
		qMetadata.setNbsQuestionUid(vo.getUiMetadata().getNbsQuestionUid());
		String formCd = vo.getUiMetadata().getInvestigationFormCd();
		qMetadata.setInvestigationFormCd(formCd);
		qMetadata.setQuestionLabel(vo.getUiMetadata().getQuestionLabel());
		qMetadata.setQuestionToolTip(vo.getUiMetadata().getQuestionToolTip());
		qMetadata.setEnableInd(vo.getUiMetadata().getEnableInd());
		qMetadata.setOrderNbr(vo.getUiMetadata().getOrderNbr());
		qMetadata.setRequiredInd(vo.getUiMetadata().getRequiredInd());
		qMetadata.setLdfPageId(vo.getUiMetadata().getLdfPageId());
		qMetadata.setParentUid(vo.getUiMetadata().getParentUid());
		qMetadata.setNbsUiMetadataUid(vo.getUiMetadata().getNbsUiMetadataUid());
		
		//Once NbsQuestionMetadata is created, update the QuestionCache with this new entry
		Map<?,?> questionMap = (Map<?,?>)QuestionsCache.getQuestionMap().get(formCd);
		if(questionMap != null && questionMap.size() > 0)
			((Map<Object,Object>)QuestionsCache.getQuestionMap().get(formCd)).put(questionId, qMetadata);
	}
	
	/**
	 * deleteLDFQuestionMap method removes the newly added/updated LDF from the QuestionCache Map<Object,Object> 
	 * @param questionUid
	 * @throws Exception
	 */
	public void deleteLDFQuestionMap(Long questionUid, String formCd) throws Exception {
		
		Map questionMap = (Map)QuestionsCache.getQuestionMap().get(formCd);
		if(questionMap != null && questionMap.size() > 0) {
			Iterator iter = questionMap.keySet().iterator();
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				Long uid = metaData.getNbsQuestionUid() == null ? new Long(0) : metaData.getNbsQuestionUid();
				if(uid.compareTo(questionUid) == 0){
					questionMap.remove(metaData.getQuestionIdentifier() == null ? "LDFTAB" : metaData.getQuestionIdentifier());
					break;
				}
			}		
		}
	}
	
	private void initiateCodesetMap() {
		
		try {
			if(codesetNmgroupMap == null) {
				codesetNmgroupMap = new TreeMap<Object,Object>();
				if(codesetNames != null && codesetNames.size() > 0) {
					Iterator<Object> iter = codesetNames.iterator();
					DropDownCodeDT dt = (DropDownCodeDT) iter.next();
					String key = dt.getKey();
					String value = dt.getValue();
					codesetNmgroupMap.put(key, value);
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while initiateCodesetMap in gov.cdc.nedss.localfields.helper.LocalFieldMetaDataHelper: " + e.getMessage());
		}
	}

	/**
	 * Retrieves Tab Order for the LDF to show
	 * @param metadataUid
	 * @param formCd
	 * @return
	 */
	public int retrieveTabOrder(String formCd, Long metadataUid, boolean ldfTab) {
		
		int tabOrderId = 0;
	    Connection dbConnection = null;
	    PreparedStatement preparedStmt = null;
	    ResultSet resultSet = null;
	    
		String codeSql = "SELECT order_nbr from NBS_UI_Metadata WHERE (nbs_ui_component_uid = 1010) AND (investigation_form_cd =?) AND (nbs_ui_metadata_uid =?) order by order_nbr ";
		if(ldfTab)
			codeSql = "SELECT max(order_nbr) from NBS_UI_Metadata WHERE (nbs_ui_component_uid = 1010) AND (investigation_form_cd =?) AND record_status_cd='Active' ";
		
	    try {
	      dbConnection = getConnection();
	      preparedStmt = dbConnection.prepareStatement(codeSql);
	      preparedStmt.setString(1, formCd);
	      if(!ldfTab)
	    	  preparedStmt.setLong(2, metadataUid == null ? 0 : metadataUid.longValue());
	      resultSet = preparedStmt.executeQuery();
	      while(resultSet.next()) {
	        tabOrderId = resultSet.getInt(1);
	      }
	    }
	    catch(Exception e) {
	      logger.error("Error while retrieving tabOrderId for tab: " + e.toString());
	    }
	    finally {
	      closeResultSet(resultSet);
	      closeStatement(preparedStmt);
	      releaseConnection(dbConnection);
	    }		
		return tabOrderId;
	}
	
}
