package gov.cdc.nedss.systemservice.ejb.pamconversionejb.util;

import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.localfields.ejb.dao.NBSUIMetaDataDAOImpl;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.ReversePamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReverseConvertToNewStructure extends DAOBase{
	 static final LogUtils logger = new LogUtils(ReverseConvertToNewStructure.class.getName());
		 
	/*private static  String SELECT_NBSCONVERSION="SELECT nbs_conversion_mapping_uid  \"nbsConversionMappingUid\",from_code   \"fromCode\",from_code_set_nm  \"fromCodeSetNm\"," +
	"from_data_type  \"fromDataType\",from_question_id  \"fromQuestionId\",condition_cd_group_id   \"conditionCdGroupId\"," +
	"to_code  \"toCode\",to_code_set_nm  \"toCodeSetNm\",to_data_type  \"toDataType\",to_question_id  \"toQuestionId\",translation_required_ind  " +
	" \"translationRequiredInd\",from_db_location  \"fromDbLocation\",to_db_location  \"toDbLocation\",from_label  \"fromLabel\""
+ " FROM  NBS_conversion_mapping order by  to_question_id, to_db_location";
	private static HashMap<Object,Object> cachedQuestionIdMap = new HashMap<Object,Object>();
	
	@SuppressWarnings("unchecked")
	public void  getNBSConvserionsCollection() throws NEDSSSystemException{
		NBSConversionMappingDT  nbsConversionMapperrDT  = new NBSConversionMappingDT();
		ArrayList<Object> nBSConversionMappingDTCollection  = new ArrayList<Object> ();
		try
		{
			nBSConversionMappingDTCollection  = (ArrayList<Object> )preparedStmtMethod(nbsConversionMapperrDT, nBSConversionMappingDTCollection, SELECT_NBSCONVERSION, NEDSSConstants.SELECT);
			Iterator it = nBSConversionMappingDTCollection.iterator();
			{
				while(it.hasNext()){
					NBSConversionMappingDT  nbsConversionMapperrDT1 =(NBSConversionMappingDT)it.next();
					String key = "";
						key = nbsConversionMapperrDT1.getToQuestionId().trim();
					cachedQuestionIdMap.put(key,nbsConversionMapperrDT1 );
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getNBSConvserionsCollection:  ERROR = " + ex);
					throw new NEDSSSystemException(ex.toString());
		}
	}
	public PamProxyVO  reverseConvertToNewStructure(PamProxyVO pamProxyVO,
			NBSSecurityObj nbsSecurityObj) {
		Map<Object,Object> questionIdMap = PamConversionDAO.getCachedQuestionIdMap(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
		PamConversionDAO pamConversionDAO = new PamConversionDAO();
		if (cachedQuestionIdMap.values()==null || cachedQuestionIdMap.values().size()==0){
			getNBSConvserionsCollection();
		}
		PublicHealthCaseVO publicHealthCaseVO= pamProxyVO.getPublicHealthCaseVO();
		Map<Object,Object> hashMap=pamProxyVO.getPamVO().getPamAnswerDTMap();
		if(questionIdMap.get("INV112")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV112");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setRptSourceCd(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		
		if(questionIdMap.get("INV138")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV138");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveToTime_s(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV139")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV139");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveDurationAmt(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}

		if(questionIdMap.get("INV140")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV140");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveDurationUnitCd(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		
		if(questionIdMap.get("INV148")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV148");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setDayCareIndCD(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}

		if(questionIdMap.get("INV149")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV149");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setFoodHandlerIndCD(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}

		if(questionIdMap.get("INV152")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV152");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setDiseaseImportedCd(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV153")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV153");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setImportedCountryCD(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV154")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV154");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setImportedStateCD(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV155")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV155");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setImportedCityDescTxt(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV156")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV156");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setImportedCountyCD(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV157")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV157");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setTransmissionModeCd(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		if(questionIdMap.get("INV159")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV159");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				publicHealthCaseVO.getThePublicHealthCaseDT().setDetectionMethodCd(nbsCaseAnswerDT.getAnswerTxt());
				nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
				nbsCaseAnswerDT.setItDelete(true);
				nbsCaseAnswerDT.setItDirty(false);
				nbsCaseAnswerDT.setItNew(false);
			}
		}
		pamProxyVO =setConfirmationMethodColl( pamProxyVO,  questionIdMap,hashMap,
			 nbsSecurityObj);
		
		ReversePamConversionDAO reversePamConversionDAO = new ReversePamConversionDAO();
		reversePamConversionDAO.sePamProxyVO(pamProxyVO, nbsSecurityObj);
		
		return pamProxyVO;
	}
	private PamProxyVO setConfirmationMethodColl(PamProxyVO pamProxyVO, Map<Object,Object> questionIdMap, Map<Object,Object> hashMap,
			NBSSecurityObj nbsSecurityObj){
		PamConversionDAO pamConversionDAO = new PamConversionDAO();
		String time = null;
		if(questionIdMap.get("INV162")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV162");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)hashMap.get(questionDT.getNbsQuestionUid());
				if(nbsCaseAnswerDT!=null){
					time = nbsCaseAnswerDT.getAnswerTxt();
					nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
					nbsCaseAnswerDT.setItDelete(true);
					nbsCaseAnswerDT.setItDirty(false);
					nbsCaseAnswerDT.setItNew(false);
				}
			}
		}
		if(questionIdMap.get("INV161")!=null){
			NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT) questionIdMap.get("INV161");
			NbsQuestionDT questionDT = pamConversionDAO
			.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
					.getToQuestionId(), pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			Collection<Object>  confirmColl = new ArrayList<Object> ();
			if(hashMap.get(questionDT.getNbsQuestionUid())!=null){
				Collection<?>  coll=(ArrayList<?> )hashMap.get(questionDT.getNbsQuestionUid());
				//publicHealthCaseVO.getThePublicHealthCaseDT().setDetectionMethodCd(nbsCaseAnswerDT.getAnswerTxt());
				Iterator<?> it = coll.iterator();
				while(it.hasNext()){
					NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)it.next();
					ConfirmationMethodDT confirmationMethodDT= new ConfirmationMethodDT();
					confirmationMethodDT.setConfirmationMethodTime_s(time);
					confirmationMethodDT.setConfirmationMethodCd(nbsCaseAnswerDT.getAnswerTxt());
					confirmationMethodDT.setItNew(true);
					confirmationMethodDT.setItDirty(false);
					confirmColl.add(confirmationMethodDT);
					nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(questionDT.getVersionCtrlNbr());
					nbsCaseAnswerDT.setItDelete(true);
					nbsCaseAnswerDT.setItDirty(false);
					nbsCaseAnswerDT.setItNew(false);
				}
			}
			if(confirmColl!=null && confirmColl.size()>0){
				pamProxyVO.getPublicHealthCaseVO().setTheConfirmationMethodDTCollection(confirmColl);
			}
			else if(confirmColl!=null && confirmColl.size()==0){
				ConfirmationMethodDT confirmationMethodDT= new ConfirmationMethodDT();
				confirmationMethodDT.setConfirmationMethodTime_s(time);
				confirmColl.add(confirmationMethodDT);
				pamProxyVO.getPublicHealthCaseVO().setTheConfirmationMethodDTCollection(confirmColl);
			}
		}
		return pamProxyVO;
	}

	public void removeMetadata() {
		ReversePamConversionDAO reversePamConversionDAO = new ReversePamConversionDAO();
		reversePamConversionDAO.logicallyDeleteMetada();
	}
*/
}
