package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * PamConversionDAO
 * Description:
 * Copyright: Copyright (c) 2008
 * @author Pradeep Sharma
 * @updated : 2010 by Pradeep Sharma
 */

public class PamConversionDAO extends DAOBase{
    static final LogUtils logger = new LogUtils(PamConversionDAO.class.getName());
	//private String convert
	private static  String SELECT_NBSCONVERSION="SELECT nbs_conversion_mapping_uid  \"nbsConversionMappingUid\",from_code   \"fromCode\",from_code_set_nm  \"fromCodeSetNm\"," +
			"from_data_type  \"fromDataType\",from_question_id  \"fromQuestionId\",condition_cd_group_id   \"conditionCdGroupId\"," +
			"to_code  \"toCode\",to_code_set_nm  \"toCodeSetNm\",to_data_type  \"toDataType\",to_question_id  \"toQuestionId\",translation_required_ind  " +
			" \"translationRequiredInd\",from_db_location  \"fromDbLocation\",to_db_location  \"toDbLocation\",from_label  \"fromLabel\", " +
			" to_label \"toLabel\", legacy_block_ind \"legacyBlockInd\", block_id_nbr \"blockIdNbr\", other_ind \"otherInd\", unit_ind \"unitInd\", " +
			" unit_type_cd \"unitTypeCd\", unit_value \"unitValue\", " + 
			" trigger_question_id \"triggerQuestionId\", trigger_question_value \"triggerQuestionValue\", from_other_question_id \"fromOtherQuestionId\", " +
			" conversion_type \"conversionType\" " +
			" FROM  NBS_conversion_mapping  where condition_cd_group_id = ? " +
			" order by  to_question_id, to_db_location";
	
	private static HashMap<Object,Object> cachedPHCLocationMap = new HashMap<Object,Object>();

	private static HashMap<Object,Object> cachedQuestionIdMap = new HashMap<Object,Object>();
	private static HashMap<Object,Object> cachedSNMQuestionIdMap = new HashMap<Object,Object>();
	private static HashMap<Object,Object> cachedUIMetadataIdMap = new HashMap<Object,Object>();
	private static String conditioncd="";
	
	
	private static HashMap<Object,Object> cachedPamCaseAnswerLocationMap = new HashMap<Object,Object>();
	private static HashMap<Object,Object> cachedConditionCodeIdMap= new HashMap<Object,Object>();
	public static String conditionCd=null;

	public void initializePamConversionMapping() {
		cachedPHCLocationMap = new HashMap<Object,Object>();
		cachedQuestionIdMap = new HashMap<Object,Object>();
		cachedSNMQuestionIdMap = new HashMap<Object,Object>();
		cachedUIMetadataIdMap = new HashMap<Object,Object>();
		cachedConditionCodeIdMap= new HashMap<Object,Object>(); //per Tennessee Josh prob #8101
		conditioncd="";
		conditionCd=null;
	}

	public static void setCachedPHCLocationMap(HashMap<Object,Object> cachedPHCLocationMap) {
		PamConversionDAO.cachedPHCLocationMap = cachedPHCLocationMap;
	}

	public static void setCachedPamCaseAnswerLocationMap(
			HashMap<Object,Object> cachedPamCaseAnswerLocationMap) {
		PamConversionDAO.cachedPamCaseAnswerLocationMap = cachedPamCaseAnswerLocationMap;
	}
	
	public static HashMap<Object, Object> getCachedPHCLocationMap() {
		return cachedPHCLocationMap;
	}
	/**
	 * Get the nbs_conversion_mapping for the conditionCd Group associated with the conditionCd
	 * @param conditionCode
	 * @return questionIdMap
	 */
	public HashMap<Object,Object> getCachedQuestionIdMap(String conditionCode) {
		
		Long conditionCdGroupId = null;
		try
		{ 
			NBSConversionConditionDT nbsConversionConditionDT = getNBSConversionConditionDTForCondition(conditionCode, null);
			if(nbsConversionConditionDT != null){
				conditionCdGroupId= nbsConversionConditionDT.getConditionCdGroupId();
			}
		} catch (Exception ex) {
				logger.error("conditionCode: "+conditionCode+" Exception in getCachedQuestionIdMap: Condition not in nbs_conversion_condition table??  ERROR = " + ex.getMessage(), ex);	
		}
		return (getCachedQuestionIdMap(conditionCode, conditionCdGroupId));
	}
	
	public HashMap<Object,Object> getCachedQuestionIdMap(String conditionCode, Long conditionCdGroupId) {
		try{
			if(cachedQuestionIdMap.size()>10 && conditionCode!=null && conditionCode.equals(conditionCd))
				
			if(cachedQuestionIdMap.size()==0 ||  conditionCode!=null || !(conditionCode!=null && conditionCode.equals(conditionCd))){
				return cachedQuestionIdMap;
			}
			
			PamConversionDAO pamConversionDAO= new PamConversionDAO();
			pamConversionDAO.getNBSConvserionsCollection(conditionCode, conditionCdGroupId);
			conditionCd= conditionCode;
		}catch(Exception ex){
			logger.error("conditionCode: "+conditionCode+" Exception = "+ex.getMessage(), ex);
		}
		return cachedQuestionIdMap;
	}
	
	public static Object getCachedSNMQuestionIdMap(String key) {
		return cachedSNMQuestionIdMap.get(key);
	}
		
	
	@SuppressWarnings("unchecked")
	public Collection<Object>   getNBSConvserionsCollection(String cdToBeTranslated, Long conditionCdGroupId) throws NEDSSSystemException{
		NBSConversionMappingDT  nbsConversionMapperrDT  = new NBSConversionMappingDT();
		ArrayList<Object> nBSConversionMappingDTCollection  = new ArrayList<Object> ();
		Collection<Object>  snmCollection= new ArrayList<Object> ();
		Collection<Object>  mappingErrorColl= new ArrayList<Object> ();
		Collection<Object> questionMappingCollection = new ArrayList<Object>();
		try
		{
			nBSConversionMappingDTCollection.add(conditionCdGroupId);
			nBSConversionMappingDTCollection  = (ArrayList<Object> )preparedStmtMethod(nbsConversionMapperrDT, nBSConversionMappingDTCollection, SELECT_NBSCONVERSION, NEDSSConstants.SELECT);
			if (nBSConversionMappingDTCollection.size() == 0) {
				NBSConversionMappingDT nbsConversionMappingDT = new NBSConversionMappingDT();
				nbsConversionMappingDT.setConditionCdGroupId(conditionCdGroupId);
				nbsConversionMappingDT.setNbsConversionMappingUid(0L);
				NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("NO_MAPPING_FOUND","Error: no mapping found for condition_cd "+cdToBeTranslated, nbsConversionMappingDT);
				mappingErrorColl.add(nBSConversionErrorDT);
			}
			Iterator it = nBSConversionMappingDTCollection.iterator();
			{
				while(it.hasNext()){
					NBSConversionMappingDT  nbsConversionMapperrDT1 =(NBSConversionMappingDT)it.next();
					Collection<Object>  errorColl = checkForMappingErrors(nbsConversionMapperrDT1, cdToBeTranslated);
					mappingErrorColl.addAll(errorColl);
					String key = "";
					if(nbsConversionMapperrDT1.getFromCode()!=null)
						key = nbsConversionMapperrDT1.getFromQuestionId().trim()+nbsConversionMapperrDT1.getFromCode().trim();
					else
						key = nbsConversionMapperrDT1.getFromQuestionId().trim();
					if(cachedQuestionIdMap.get(key)!=null)
						((Collection)cachedQuestionIdMap.get(key)).add(nbsConversionMapperrDT1);
					else{
						questionMappingCollection = new ArrayList<Object>();
						questionMappingCollection.add(nbsConversionMapperrDT1);
						cachedQuestionIdMap.put(key,questionMappingCollection ); 
					}
					
					String PHCKey = nbsConversionMapperrDT1.getFromCode() != null ? nbsConversionMapperrDT1
							.getFromDbLocation().trim().toUpperCase()
							+ nbsConversionMapperrDT1.getFromCode()
							: nbsConversionMapperrDT1.getFromDbLocation()
									.trim().toUpperCase();
					
					if (cachedPHCLocationMap.get(PHCKey) != null
							&& nbsConversionMapperrDT1.getFromDbLocation() != null
							&& nbsConversionMapperrDT1.getFromDbLocation()
									.toUpperCase()
									.startsWith("PUBLIC_HEALTH_CASE")) {
						((Collection) cachedPHCLocationMap.get(PHCKey))
								.add(nbsConversionMapperrDT1);
					} else if (cachedPHCLocationMap.get(PHCKey) == null
							&& nbsConversionMapperrDT1.getFromDbLocation() != null
							&& nbsConversionMapperrDT1.getFromDbLocation()
									.toUpperCase()
									.startsWith("PUBLIC_HEALTH_CASE")) {
						questionMappingCollection = new ArrayList<Object>();
						questionMappingCollection.add(nbsConversionMapperrDT1);
						cachedPHCLocationMap
								.put(PHCKey, questionMappingCollection);
					}

					if((nbsConversionMapperrDT1.getUnitInd()!=null && nbsConversionMapperrDT1.getUnitInd().trim().equalsIgnoreCase("T"))||
						(nbsConversionMapperrDT1.getUnitTypeCd()!=null && nbsConversionMapperrDT1.getUnitTypeCd().trim().length()>0)||
						(nbsConversionMapperrDT1.getBlockIdNbr()!=null && nbsConversionMapperrDT1.getBlockIdNbr().intValue()>0)||
							(nbsConversionMapperrDT1.getOtherInd()!=null && nbsConversionMapperrDT1.getOtherInd().trim().equalsIgnoreCase("T"))){
						if(cachedSNMQuestionIdMap.get(nbsConversionMapperrDT1.getToQuestionId())==null){
							snmCollection = new ArrayList();
							snmCollection.add(nbsConversionMapperrDT1);
						}
						else{
							snmCollection=(Collection)cachedSNMQuestionIdMap.get(nbsConversionMapperrDT1.getToQuestionId());
							snmCollection.add(nbsConversionMapperrDT1);
						}
						cachedSNMQuestionIdMap.put(nbsConversionMapperrDT1.getToQuestionId(), snmCollection);

					}
				}
				this.conditioncd=cdToBeTranslated;
			}
			if(mappingErrorColl!=null && mappingErrorColl.size()>0){
				cachedQuestionIdMap=new HashMap<Object,Object>();
			}
		}
		 catch (Exception ex) {
			logger.fatal("cdToBeTranslated: "+cdToBeTranslated+" Exception in getNBSConvserionsCollection:  ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString(), ex);
		}
		return mappingErrorColl;
	}
	
	String SELECT_NBS_UIMETADATA_FOR_ID=	"select nbs_ui_component_uid \"nbsUiComponentUid\", question_identifier \"questionIdentifier\", nbs_question_uid \"nbsQuestionUid\" , version_ctrl_nbr \"versionCtrlNbr\", QUESTION_GROUP_SEQ_NBR \"questionGroupSeqNbr\", unit_type_cd \"unitTypeCd\"  from nbs_UI_METADATA where question_identifier=? and nbs_page_uid in (select nbs_page_uid from nbs_page where wa_template_uid in (select wa_template_uid from page_cond_mapping where condition_cd=?))";
			
	@SuppressWarnings("unchecked")
	public  NbsUiMetadataDT getNBSUiMetadtaForIdentifier(String identifier, String conditionCode) throws NEDSSSystemException{
		if(cachedUIMetadataIdMap.get(identifier+ conditionCode)!=null){
			return (NbsUiMetadataDT)cachedUIMetadataIdMap.get(identifier+ conditionCode);
		}
		else
		{
			NbsUiMetadataDT nbsUiMetadataDT = new NbsUiMetadataDT();
			NbsUiMetadataDT nbsUiMetadataDT1 =null;
			ArrayList<Object> questionUiMetadataDTCollection  = new ArrayList<Object> ();
			questionUiMetadataDTCollection.add(identifier);
			questionUiMetadataDTCollection.add(conditionCode);
			try
			{
				questionUiMetadataDTCollection  = (ArrayList<Object> )preparedStmtMethod(nbsUiMetadataDT, questionUiMetadataDTCollection, SELECT_NBS_UIMETADATA_FOR_ID, NEDSSConstants.SELECT);
				if(questionUiMetadataDTCollection.size()>1){
					logger.error("Exception in getNBSUiMetadtaForIdentifier,REASON: question_identifier the size for  "+ identifier +" is "+ questionUiMetadataDTCollection.size());
				}
				Iterator it =		questionUiMetadataDTCollection.iterator();
				while(it.hasNext()){
					nbsUiMetadataDT1 = (NbsUiMetadataDT)it.next();
				}
			}
			 catch (Exception ex) {
				logger.fatal("identifier: "+identifier+", conditionCode: "+conditionCode+" Exception in getNBSUiMetadtaForIdentifier:  ERROR = " + ex.getMessage(), ex);
						throw new NEDSSSystemException(ex.toString(), ex);
			}
			 
			 cachedUIMetadataIdMap.put((identifier+ conditionCode), nbsUiMetadataDT1) ;
			 return nbsUiMetadataDT1;
		}
		
	}	
	
	String SELECT_CONVERSION_CONDITION="SELECT nbs_conversion_condition_uid \"nbsConversionConditionUid\",condition_cd \"conditionCd\", condition_cd_group_id  \"conditionCdGroupId\"  FROM NBS_conversion_condition where condition_cd_group_id=?";
	
	@SuppressWarnings("unchecked")
	public Map<Object,Object> getNBSConditionCollection(Long conditionCdGroupId) throws NEDSSSystemException{
		NBSConversionConditionDT  nBSConversionConditionDT  = new NBSConversionConditionDT();
		ArrayList<Object> nBSConversionMappingDTCollection  = new ArrayList<Object> ();
		try
		{
			nBSConversionMappingDTCollection.add(conditionCdGroupId);
			nBSConversionMappingDTCollection  = (ArrayList<Object> )preparedStmtMethod(nBSConversionConditionDT, nBSConversionMappingDTCollection, SELECT_CONVERSION_CONDITION, NEDSSConstants.SELECT);
			Iterator it = nBSConversionMappingDTCollection.iterator();
			{
				while(it.hasNext()){
					NBSConversionConditionDT  nBSConversionConditionDT1 =(NBSConversionConditionDT)it.next();
						cachedConditionCodeIdMap.put(nBSConversionConditionDT1.getConditionCd(), nBSConversionConditionDT1);
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getNBSConditionCollection:  ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString(), ex);
		}
		return cachedConditionCodeIdMap;
	}

	/**
	 * getGroupIdForCondition - see NBS_conversion_condition table
	 * @param conditionCd
	 * @return conditionCdGroupId (or null if none found)
	 * @throws NEDSSSystemException
	 */
	String SELECT_CONVERSION_CONDITION_CD_GROUP_ID="SELECT nbs_conversion_condition_uid \"nbsConversionConditionUid\",condition_cd \"conditionCd\", condition_cd_group_id  \"conditionCdGroupId\", NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", status_cd \"statusCd\", add_time \"addTime\",  last_chg_time \"lastChangeTime\" FROM NBS_conversion_condition where condition_cd=? ";
	@SuppressWarnings("unchecked")
	public NBSConversionConditionDT getNBSConversionConditionDTForCondition(String conditionCd, Long nbsConversionPageMgmtUid) throws NEDSSSystemException{
		
		NBSConversionConditionDT  nbsConversionConditionDT = null;
		
		
		if (cachedConditionCodeIdMap.containsKey(conditionCd) && nbsConversionPageMgmtUid==null) {   //here nbsConversionPgMgmtUid==null for Legacy to Page Builder porting and it is not null for PB to PB porting.
			nbsConversionConditionDT = (NBSConversionConditionDT) cachedConditionCodeIdMap.get(conditionCd);
			return nbsConversionConditionDT;	
		}
		
		NBSConversionConditionDT  nBSConversionConditionDT  = new NBSConversionConditionDT();
		ArrayList<Object> nBSConversionMappingDTCollection  = new ArrayList<Object> ();
		Long conditionCdGroupId = null;
		try
		{
			nBSConversionMappingDTCollection.add(conditionCd);
			
			String query=SELECT_CONVERSION_CONDITION_CD_GROUP_ID;
			
			if(nbsConversionPageMgmtUid!=null){
				nBSConversionMappingDTCollection.add(nbsConversionPageMgmtUid);
				query = SELECT_CONVERSION_CONDITION_CD_GROUP_ID+ " and NBS_Conversion_Page_Mgmt_uid = ? order by add_time desc";
			}else{
				query = SELECT_CONVERSION_CONDITION_CD_GROUP_ID+" order by add_time desc";
			}
					
			nBSConversionMappingDTCollection  = (ArrayList<Object> )preparedStmtMethod(nBSConversionConditionDT, nBSConversionMappingDTCollection, query, NEDSSConstants.SELECT);
			Iterator it = nBSConversionMappingDTCollection.iterator();
			{
				while(it.hasNext()){
					nbsConversionConditionDT =(NBSConversionConditionDT)it.next();
					conditionCdGroupId = nbsConversionConditionDT.getConditionCdGroupId();
					break;
				}
			}
		}catch (Exception ex) {
			logger.fatal("conditionCd: "+conditionCd+" Exception in getGroupIdForCondition:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		//lets cache the group
		//getNBSConditionCollection(conditionCdGroupId);
		
		return nbsConversionConditionDT;
	}	
	
	
	public HashMap<Object,Object> getCachedConditionCodeIdMap(Long conditionCdGroupId) {
		try{
			if(cachedConditionCodeIdMap.values().size()<1)
				getNBSConditionCollection(conditionCdGroupId);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return cachedConditionCodeIdMap;
	}

	public void setCachedConditionCodeIdMap(HashMap<Object,Object> cachedConditionCodeIdMap) {
		this.cachedConditionCodeIdMap = cachedConditionCodeIdMap;
	}
	

	private Collection<Object>  checkForMappingErrors(NBSConversionMappingDT nBSConversionMappingDT, String cdToBeTranslated){
		Collection<Object>  coll = new ArrayList<Object> ();
		//TO_DB_LOCATION		
		try{
			if(nBSConversionMappingDT.getToDbLocation()==null || nBSConversionMappingDT.getToDbLocation().trim().equalsIgnoreCase("")){
				 String errorMessage=" to_db_location missing. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid();
				 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("TO_DB_LOCATION_MISSING",errorMessage, nBSConversionMappingDT);
				coll.add(nBSConversionErrorDT);
			}
			else if(nBSConversionMappingDT.getToDbLocation().indexOf(".")<0){
				 String errorMessage=" to_db_location missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". The to_db_location needs to be populated as \"table_name.column_nm\".";
				 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("TO_DB_LOCATION_MISSING",errorMessage, nBSConversionMappingDT);
				coll.add(nBSConversionErrorDT);
			}
			else if(!nBSConversionMappingDT.getToDbLocation().trim().equalsIgnoreCase("") ){
				String tableName = nBSConversionMappingDT.getToDbLocation().substring(0, nBSConversionMappingDT.getToDbLocation().indexOf("."));
				String columnName= nBSConversionMappingDT.getToDbLocation().substring(nBSConversionMappingDT.getToDbLocation().indexOf(".")+1);
				boolean isExistingTable = checkIfExistingTable(tableName);
				if(!isExistingTable){
					String errorMessage=" to_db_location missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". The to_db_location TABLE:\""+ tableName+"\" does not exist.";
					 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("NON_EXISTING_TABLE \""+ tableName+"\"",errorMessage, nBSConversionMappingDT);
						coll.add(nBSConversionErrorDT);
				}
				if(isExistingTable){
					boolean isExistingColumn = checkIfExistingColumn(tableName, columnName);
					if(!isExistingColumn){
						String errorMessage=" to_db_location missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". For  to_db_location  TABLE :\""+ tableName+"\",columnName:\""+columnName+"\"  does not exist.";
						 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("NON_EXISTING_COLUMN for Table:"+ tableName+",columnName:"+columnName+"  does not exist",errorMessage, nBSConversionMappingDT);
							coll.add(nBSConversionErrorDT);
					}
				}
			}
			//FROM_DB_LOCATION
			if(nBSConversionMappingDT.getFromDbLocation()==null || nBSConversionMappingDT.getFromDbLocation().trim().equalsIgnoreCase("")){
				 String errorMessage=" from_db_location missing. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+".";
				 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("FROM_DB_LOCATION_MISSING",errorMessage, nBSConversionMappingDT);
				coll.add(nBSConversionErrorDT);
			}
			else if(nBSConversionMappingDT.getFromDbLocation().indexOf(".")<0){
				 String errorMessage=" from_db_location missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". The from_db_location needs to be populated as \"table_name.column_nm\".";
				 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("FROM_DB_LOCATION_MISSING",errorMessage, nBSConversionMappingDT);
				coll.add(nBSConversionErrorDT);
			}
			else if(!nBSConversionMappingDT.getFromDbLocation().trim().equalsIgnoreCase("") ){
				String tableName = nBSConversionMappingDT.getFromDbLocation().substring(0, nBSConversionMappingDT.getFromDbLocation().indexOf("."));
				String columnName= nBSConversionMappingDT.getFromDbLocation().substring(nBSConversionMappingDT.getFromDbLocation().indexOf(".")+1);
				boolean isExistingTable = checkIfExistingTable(tableName);
				if(!isExistingTable){
					String errorMessage=" from_db_location missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". The from_db_location TABLE:\""+ tableName+"\" does not exist.";
					 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("NON_EXISTING_TABLE "+ tableName,errorMessage, nBSConversionMappingDT);
						coll.add(nBSConversionErrorDT);
				}
				if(isExistingTable){
					boolean isExistingColumn = checkIfExistingColumn(tableName, columnName);
					if(!isExistingColumn){
						String errorMessage=" from_db_location missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". For  from_db_location  TABLE :\""+ tableName+"\",columnName:\""+columnName+"\"  does not exist.";
						 NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("NON_EXISTING_COLUMN for Table:\""+ tableName+"\",columnName:\""+columnName+"\"  does not exist",errorMessage, nBSConversionMappingDT);
							coll.add(nBSConversionErrorDT);
					}
				}
			}
			
			if(nBSConversionMappingDT.getFromQuestionId()==null || nBSConversionMappingDT.getFromQuestionId().trim().equalsIgnoreCase("")){
				String errorMessage=" from_question_id  missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". from_question_id cannot be null or Empty.";
				NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("FROM_QUESTION_ID_MISSING",errorMessage, nBSConversionMappingDT);
				coll.add(nBSConversionErrorDT);
		
			}
			
			if(nBSConversionMappingDT.getToQuestionId()==null || nBSConversionMappingDT.getToQuestionId().trim().equalsIgnoreCase("")){
				String errorMessage=" to_question_id  missing or not in recognizable format. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". to_question_id cannot be null or Empty.";
				NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("TO_QUESTION_ID_MISSING",errorMessage, nBSConversionMappingDT);
				coll.add(nBSConversionErrorDT);
			}
			else if(nBSConversionMappingDT.getToQuestionId()!=null && !nBSConversionMappingDT.getToQuestionId().trim().equalsIgnoreCase("")){
				NbsUiMetadataDT nbsUiMetadataDT=getNBSUiMetadtaForIdentifier(nBSConversionMappingDT.getToQuestionId(), cdToBeTranslated);
				if(nbsUiMetadataDT==null){
					String errorMessage=" to_question_id  does not exist in NBS_QUESTION table. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+". Please check to_question_id if it exists in NBS_QUESTION table(column question_identifier).";
					NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("TO_QUESTION_ID_WRONG_MAPPING",errorMessage, nBSConversionMappingDT);
					coll.add(nBSConversionErrorDT);
				}
			}
			
			if(nBSConversionMappingDT.getFromCodeSetNm()!=null){
				String fromCodeSetNm = nBSConversionMappingDT.getFromCodeSetNm();
				String fromCode = nBSConversionMappingDT.getFromCode();
				String returnVal=getValidCodeSetNm(fromCodeSetNm, fromCode);
				if(returnVal==null){
					
				}
				else if(returnVal.indexOf("INVALID_CODE_SET_NM_CODE_COMBINATION_ERROR")>-1){
					String errorMessage=" Mapped FROM_CODE_SET_NM_EXISTS_BUT_CODE_DOES_NOT_EXIST. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+"."+returnVal+".";
					NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("FROM_CODE_SET_NM_EXISTS_BUT_CODE_DOES_NOT_EXIST",errorMessage, nBSConversionMappingDT);
					coll.add(nBSConversionErrorDT);
				}
				else if(returnVal.indexOf("INVALID_CODE_SET_NM_ERROR")>-1){
					String errorMessage=" Mapped FROM_CODE_SET_NM_DOES_NOT_EXIST. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+"."+returnVal+".";
					NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("FROM_CODE_SET_NM_DOES_NOT_EXIST",errorMessage, nBSConversionMappingDT);
					coll.add(nBSConversionErrorDT);
				}
			}	
			if(nBSConversionMappingDT.getToCodeSetNm()!=null){
				String toCodeSetNm = nBSConversionMappingDT.getToCodeSetNm();
				String toCode = nBSConversionMappingDT.getToCode();
				String returnVal=getValidCodeSetNm(toCodeSetNm, toCode);
				if(returnVal==null){
					
				}
				else if(returnVal.indexOf("INVALID_CODE_SET_NM_CODE_COMBINATION_ERROR")>-1){
					String errorMessage=" Mapped TO_CODE_SET_NM_EXISTS_BUT_CODE_DOES_NOT_EXIST. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+"."+returnVal+".";
					NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("TO_CODE_SET_NM_EXISTS_BUT_CODE_DOES_NOT_EXIST",errorMessage, nBSConversionMappingDT);
					coll.add(nBSConversionErrorDT);
				}
				else if(returnVal.indexOf("INVALID_CODE_SET_NM_ERROR")>-1){
					String errorMessage=" Mapped TO_CODE_SET_NM_DOES_NOT_EXIST. Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nBSConversionMappingDT.getNbsConversionMappingUid()+"."+returnVal+".";
					NBSConversionErrorDT nBSConversionErrorDT= setNBSConversionMappingDT("TO_CODE_SET_NM_DOES_NOT_EXIST",errorMessage, nBSConversionMappingDT);
					coll.add(nBSConversionErrorDT);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return coll;
	}
	
	public String getValidCodeSetNm(String codeSetNm, String cd) {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getValidCodeSetNm ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		ResultSet  rs = null;
		ResultSet  rs2 =null; 
		PreparedStatement preparedStmt2 = null;
		String srte="nbs_srte..";
		String tableExists="";
		String tableAndCodeExists="";
		 		try{
			 preparedStmt = dbConnection.prepareStatement("select class_cd from "+srte+"codeset where code_set_nm=?");
			 preparedStmt.setString(1, codeSetNm);
			 rs = preparedStmt.executeQuery();
			if (rs.next()) {
				logger.debug("class_cd = " + rs.getString(1));
				String class_cd= rs.getString(1);
				if(class_cd==null || class_cd.trim().equals("")){
					tableExists="INVALID_CODE_SET_NM_ERROR. CODE_SET_NM \""+ codeSetNm+"\" DOES_NOT_EXISTS.";
				return tableExists;
				}
				if(cd!=null && !cd.trim().equals("")){
				
				preparedStmt2 = dbConnection.prepareStatement("select code from "+srte+class_cd+" where code=?");
				preparedStmt2.setString(1, cd);
					 rs2 = preparedStmt2.executeQuery();
					if (rs2.next()) {
						logger.debug("Validating code for "+ class_cd + " code = " + rs2.getString(1));
						String code= rs2.getString(1);
						if(code==null || code.trim().equals("")){
							tableAndCodeExists="INVALID_CODE_SET_NM_CODE_COMBINATION_ERROR. CODE_SET_NM \""+codeSetNm+"\" EXISTS_BUT_CODE \""+ cd+"\" DOES_NOT_EXISTS IN \""+ class_cd +"\" TABLE";
						return tableAndCodeExists;
						}
					}
					else 
						return tableAndCodeExists="INVALID_CODE_SET_NM_CODE_COMBINATION_ERROR. CODE_SET_NM \""+codeSetNm+"\" EXISTS_BUT_CODE \""+ cd+"\" DOES_NOT_EXISTS IN \""+ class_cd +"\" TABLE";
				}
			}
			else 	
				return tableExists="INVALID_CODE_SET_NM_ERROR.CODE_SET_NM \""+codeSetNm+"\" DOES_NOT_EXISTS";
			return null;
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while checking existence of fromCodeSetNm " +codeSetNm+ "and fromCode "+ cd+"combination"+ sqlex.getMessage(), sqlex);
			throw new NEDSSDAOSysException( "SQLException while checking existence of fromCodeSetNm " +codeSetNm+ "and fromCode "+ cd+"combination"+ sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("Exception while checking existence of fromCodeSetNm " +codeSetNm+ "and fromCode "+ cd+"combination"+ ex.getMessage(), ex);
			throw new NEDSSDAOSysException( "Exception while checking existence of fromCodeSetNm " +codeSetNm+ "and fromCode "+ cd+"combination"+ ex.toString(), ex );
		}
		finally
		{
			closeResultSet(rs2);
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);

		}

	}
	
	public boolean  checkIfExistingTable(String tableName){
		Connection dbConnection = null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for checkIfExistingTable ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		ResultSet rs =null;
		boolean tableExists= false;
		try{
			DatabaseMetaData dbm = dbConnection.getMetaData();
            rs = dbm.getTables(null, null, tableName, null);
            if (rs.next()) {
            	tableExists= true;
                }
                else {
                	tableExists= false;
                }
		}
		catch(SQLException sqlex)
		{
			logger.fatal("tableName: "+tableName+" SQLException while checking the existence of table:"+ tableName+" \n", sqlex);
			throw new NEDSSSystemException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("tableName: "+tableName+" Exception while checking the existence of table::"+ tableName+" \n", ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeResultSet(rs);
			releaseConnection(dbConnection);
		}
		
		return tableExists;
	}
	public boolean  checkIfExistingColumn(String tableName, String columnName){
		Connection dbConnection = null;
		ResultSet rs =null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for checkIfExistingColumn ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		boolean tableExists= false;
		try{
			DatabaseMetaData dbm = dbConnection.getMetaData();
			  rs = dbm.getColumns(null, null, tableName,columnName); 
            if (rs.next()) {
            	tableExists= true;
                }
                else {
                	tableExists= false;
                }
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while checking the existence ofcolumn for table:"+ tableName+"  :with columnName"+columnName, sqlex);
			throw new NEDSSSystemException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("Exception while checking the existence ofcolumn for table:"+ tableName+"  :with columnName"+columnName, ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeResultSet(rs);
			releaseConnection(dbConnection);
		}
		
		return tableExists;
	}
	
	private NBSConversionErrorDT setNBSConversionMappingDT(String errorCd, String errorMessage, NBSConversionMappingDT nBSConversionMappingDT ){
		NBSConversionErrorDT nBSConversionErrorDT =  new NBSConversionErrorDT();
		try{
			nBSConversionErrorDT.setErrorCd(errorCd);
			nBSConversionErrorDT.setErrorMessageTxt(errorMessage);
			nBSConversionErrorDT.setConditionCdGroupId(nBSConversionMappingDT.getConditionCdGroupId());
			nBSConversionErrorDT.setNbsConversionMappingUid(nBSConversionMappingDT.getNbsConversionMappingUid());
		}catch(Exception ex){
			logger.error("errorCd: "+errorCd+", errorMessage: "+errorMessage+" Exception = "+ex.getMessage(), ex);
		}
		return nBSConversionErrorDT;
		
	}
	
	public NBSConversionMasterDT  checkIfPageExistsForCondition(String conditionCode) throws NEDSSSystemException {
			   
			   
		NBSConversionMasterDT nBSConversionMasterDT = new NBSConversionMasterDT();
		String matchedTemplateType="";
			   Connection dbConnection = null;
			   PreparedStatement preparedStmt = null;
			   ResultSet resultSet = null;
			   String CHECK_FOR_CONDITION_CODE = "select template_type from Page_cond_mapping, wa_template where "
				   +" Page_cond_mapping.wa_template_uid=wa_template.wa_template_uid "
				   +" and template_type in ('Published With Draft', 'Published') "
				   +" and Page_cond_mapping.condition_cd=?";
			    logger.debug("SQL query for checking if condition exisits:-"+CHECK_FOR_CONDITION_CODE);
			   try
			   {
				   dbConnection = getConnection();
				   preparedStmt = dbConnection.prepareStatement(CHECK_FOR_CONDITION_CODE);
				   preparedStmt.setString(1, conditionCode);
					   
				   resultSet = preparedStmt.executeQuery();

				   if (!resultSet.next())
				   {
					   nBSConversionMasterDT.setProcessMessageTxt("ERROR: Condition Code "+ conditionCode+" cannot be ported, as there is not a Published Page for this Condition.");
				   }
				   else
				   {
					   matchedTemplateType= resultSet.getString(1);
					   logger.debug("Matched template type of "+matchedTemplateType+" found for conditionCode :"+conditionCode);
				   }
			   }
			   catch(SQLException sqlex)
			   {
				   logger.fatal("conditionCode: "+conditionCode+" SQL query excetion for query :"+CHECK_FOR_CONDITION_CODE);
				   logger.fatal("SQLException while checking for an"
						   + " existing condition code in prerun stage:", sqlex);
				   throw new NEDSSDAOSysException( sqlex.getMessage(), sqlex);
			   }
			   catch(NEDSSSystemException nsqlex)
			   {
				   logger.fatal("conditionCode: "+conditionCode+" SQL query excetion for query :"+CHECK_FOR_CONDITION_CODE);
				   logger.fatal("Exception while getting dbConnection for checking for an existing conditionCode " , nsqlex);
				   throw new NEDSSDAOSysException( nsqlex.getMessage(), nsqlex);
			   }
			   finally
			   {
				   closeResultSet(resultSet);
				   closeStatement(preparedStmt);
				   releaseConnection(dbConnection);
			   }
			   return nBSConversionMasterDT;
		   }
	
	private static  String SELECT_NBSCONVERSION_FOR_NND="SELECT nbs_conversion_mapping_uid  \"nbsConversionMappingUid\",from_code   \"fromCode\",from_code_set_nm  \"fromCodeSetNm\"," +
			"from_data_type  \"fromDataType\",from_question_id  \"fromQuestionId\",NBS_conversion_mapping.condition_cd_group_id   \"conditionCdGroupId\"," +
			"to_code  \"toCode\",to_code_set_nm  \"toCodeSetNm\",to_data_type  \"toDataType\",to_question_id  \"toQuestionId\",translation_required_ind  " +
			" \"translationRequiredInd\",from_db_location  \"fromDbLocation\",to_db_location  \"toDbLocation\",from_label  \"fromLabel\", " +
			" to_label \"toLabel\", legacy_block_ind \"legacyBlockInd\", block_id_nbr \"blockIdNbr\", other_ind \"otherInd\", unit_ind \"unitInd\", " +
			" unit_type_cd \"unitTypeCd\", unit_value \"unitValue\", " + 
			" trigger_question_id \"triggerQuestionId\", trigger_question_value \"triggerQuestionValue\", from_other_question_id \"fromOtherQuestionId\", " +
			" conversion_type \"conversionType\" " +
			" FROM  NBS_conversion_mapping, NBS_conversion_condition " +
			" WHERE nbs_conversion_mapping.condition_cd_group_id = nbs_conversion_condition.condition_cd_group_id " +
			" and nbs_conversion_condition.condition_cd = ? " +
			" order by  to_question_id, to_db_location";
	@SuppressWarnings("unchecked")
	/**
	 * This method was developed for Page Builder to Master Message processing. It may be temporary until
	 * porting is revisited. The above porting calls checkForMappingErrors and returns an empty collection
	 * if any errors are found. Also in spite being passed a condition code, it doesn't check for condition.
	 * It returns everything in the table.
	 * NOTE: This method should include a caching mechanism before going into production.
	 * @param conditionCd
	 * @return Hashmap of NBSConversionMappingDT in From Question key
	 * @throws NEDSSSystemException
	 */
	public HashMap<Object,Object> getNbsConversionMetadataForNND(String conditionCd) throws NEDSSSystemException{
		NBSConversionMappingDT  nbsConversionMapperrDT  = new NBSConversionMappingDT();
		ArrayList<Object> nBSConversionMappingDTCollection  = new ArrayList<Object> ();
		HashMap<Object,Object> nndFromQuestionIdMap = new HashMap<Object,Object>();

		Collection<Object> questionMappingCollection = new ArrayList<Object>();
			ArrayList<Object> conditionCollection  = new ArrayList<Object> ();
			conditionCollection.add(conditionCd);
			try
			{
				nBSConversionMappingDTCollection  = (ArrayList<Object> )preparedStmtMethod(nbsConversionMapperrDT, conditionCollection, SELECT_NBSCONVERSION_FOR_NND, NEDSSConstants.SELECT);
				Iterator it = nBSConversionMappingDTCollection.iterator();
				while(it.hasNext()){
					NBSConversionMappingDT  nbsConvDataDT =(NBSConversionMappingDT)it.next();
					String key = "";
					if(nbsConvDataDT.getFromCode()!=null)
						key = nbsConvDataDT.getFromQuestionId().trim()+nbsConvDataDT.getFromCode().trim();
					else
						key = nbsConvDataDT.getFromQuestionId().trim();
					if (nndFromQuestionIdMap.get(key) != null)
						logger.warn(" getNbsConversionMetadataForNND: Duplicate key in nbs_conversion_map: " + key);
					
					if(nndFromQuestionIdMap.get(key)!=null)
						((Collection)nndFromQuestionIdMap.get(key)).add(nbsConvDataDT);
					else{
						questionMappingCollection = new ArrayList<Object>();
						questionMappingCollection.add(nbsConvDataDT);
						nndFromQuestionIdMap.put(key,questionMappingCollection ); 
					}
					
				}
			}
			 catch (Exception ex) {
				logger.fatal("conditionCd: "+conditionCd+" Exception in getNbsConversionMetadataForNND:  ERROR = " + ex.getMessage(), ex);
						throw new NEDSSSystemException(ex.toString(), ex);
			}
		return nndFromQuestionIdMap;

	}	
	
}
