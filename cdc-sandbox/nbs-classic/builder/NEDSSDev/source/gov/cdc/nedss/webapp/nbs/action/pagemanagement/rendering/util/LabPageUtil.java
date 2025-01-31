package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedModDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.dt.ObservationInterpDT;
import gov.cdc.nedss.act.observation.dt.ObservationReasonDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations.util.CaseCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * Generic class to convert Form Objects to ObservationProxyVO object
 * @author Pradeep Kuamr Sharma
 *
 */
public class LabPageUtil {
	static final LogUtils logger = new LogUtils(LabPageUtil.class.getName());
	private static Map<Object,Object> questionMap;
	private static Map<String,String> questionToLegacyMap;
	//private static Map<String,String> legacyToQuestionMap;
	public static String labFormCode;
	public static String[] ROOT_OBSERVATION= {"INV107", "INV108", "INV178", "LAB125", "LAB163", "LAB165", "NBS_LAB112", "NBS_LAB166", "NBS_LAB197",  
						"NBS_LAB201", "NBS_LAB214", "NBS_LAB317", "NBS012", "NBS128"};

	/**
	 * Utility method to convert Arrays to Java List object
	 * @param array
	 * @return
	 */
	public static List<String> toList(String[] array) {
	    if (array==null) {
	       return new ArrayList<String>();
	    } else {
	       int size = array.length;
	       List<String> list = new ArrayList<String>(size);
	       for(int i = 0; i < size; i++) {
	          list.add(array[i]);
	       }
	       return list;
	    }
	}
	
	/**
	 *Question to Legacy type question identifier 
	 */
	public static void legacyMap(){
		questionToLegacyMap =  new HashMap<String, String>();
		questionToLegacyMap.put("LAB124", "NBS_LAB124");
		questionToLegacyMap.put("LAB168", "INV107");
		questionToLegacyMap.put("LAB169", "INV108");
		questionToLegacyMap.put("INV178", "INV178");
		questionToLegacyMap.put("LAB125", "LAB125");
		questionToLegacyMap.put("LAB163", "LAB163");
		questionToLegacyMap.put("LAB165", "LAB165");
		questionToLegacyMap.put("LAB112", "NBS_LAB112");
		questionToLegacyMap.put("LAB196", "NBS_LAB196");
		questionToLegacyMap.put("LAB261", "NBS_LAB261");
		questionToLegacyMap.put("LAB166", "NBS_LAB166");
		questionToLegacyMap.put("LAB197", "NBS_LAB197");
		questionToLegacyMap.put("LAB201", "NBS_LAB201");
		questionToLegacyMap.put("LAB214", "NBS_LAB214");
		questionToLegacyMap.put("LAB222", "NBS_LAB222");
		questionToLegacyMap.put("LAB262", "NBS_LAB262");
		questionToLegacyMap.put("LAB264", "NBS_LAB264");
		questionToLegacyMap.put("LAB265", "NBS_LAB265");
		questionToLegacyMap.put("LAB266", "NBS_LAB266");
		questionToLegacyMap.put("LAB269", "NBS_LAB269");
		questionToLegacyMap.put("LAB313", "NBS_LAB313");
		questionToLegacyMap.put("LAB316", "NBS_LAB316");
		questionToLegacyMap.put("LAB317", "NBS_LAB317");
		questionToLegacyMap.put("LAB329", "NBS_LAB329");
   		questionToLegacyMap.put("LAB189", "NBS012");
		questionToLegacyMap.put("NBS128", "NBS128");
		/*legacyToQuestionMap =  new HashMap<String, String>();
		legacyToQuestionMap = 
				questionToLegacyMap.entrySet()
			       .stream()
			       .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
			       */
	}
	
	/**
	 *Question to Legacy type question identifter 
	 * 
	 * @return
	 */
	private static Map<String,String> getQuestionToLegacyMap(){
		if(questionToLegacyMap==null){
			legacyMap();
		}
		return questionToLegacyMap;
	}
	
	

	/**
	 * 
	 * @param labResultProxyVO
	 * @param tempID
	 * @param userId
	 * @param actUID
	 * @param programAreaCode
	 * @param form
	 * @param req
	 * @throws NEDSSAppException
	 */
	public static void setParticipationUidFromClientVO(LabResultProxyVO labResultProxyVO, Long tempID,  String userId, Long actUID, String programAreaCode, PageForm form, HttpServletRequest req) throws NEDSSAppException {
		try {
			labResultProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
			PageStoreUtil.setEntitiesForCreate(labResultProxyVO, form, tempID, userId,actUID, programAreaCode,NEDSSConstants.CLASS_CD_OBS, req);
		}catch (NEDSSAppException e) {
			String errorText="LabPageUtil.getParticipationUid : NEDSSAppException Error processing getParticipationUid:";
			logger.error(errorText + e.getMessage() );
			throw new NEDSSAppException(errorText+"\n"+ e.toString());
		} 
		catch (NumberFormatException e) {
			String errorText="LabPageUtil.getParticipationUid : NumberFormatException Error processing getParticipationUid:";
			logger.error(errorText + e.getMessage() );
			throw new NEDSSAppException(errorText+"\n"+ e.toString());
		}
	}
	
	/**
	 * Populate MaterialVO
	 * @param tempid
	 * @param answerText
	 * @param labResultProxyVO
	 * @param nbsQuestionMetadata
	 * @throws NEDSSAppException
	 */
	public static void populateMaterialVO(Long tempid, String answerText,  LabResultProxyVO oldLabResultProxyVO, LabResultProxyVO labResultProxyVO, NbsQuestionMetadata nbsQuestionMetadata) throws NEDSSAppException {
		MaterialVO materialVO = new MaterialVO();
		MaterialDT materialDT = new MaterialDT();
		String descTxt ="";
			if(oldLabResultProxyVO!=null) {
				materialVO = (MaterialVO)getFirstElementOfCollection(oldLabResultProxyVO.getTheMaterialVOCollection());
				materialDT  =materialVO.getTheMaterialDT();
			}
			try {
				if(answerText !=null && answerText.trim().length()>0) {
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, materialDT, answerText, RenderConstants.MATERIAL);
					descTxt = getDescTxt("SPECMN_SRC",materialDT.getCd());
				}
				else {
					materialDT.setCd("");
				}
				materialDT.setCdDescTxt(descTxt);
				labResultProxyVO.setTheMaterialVOCollection(new ArrayList<Object>());
				if(oldLabResultProxyVO==null) {
					materialDT.setMaterialUid(new Long(tempid--));
					materialDT.setItNew(true);
					materialDT.setItDirty(false);
					materialVO.setTheMaterialDT(materialDT);
					materialVO.setItNew(true);
					materialVO.setItDirty(false);
				}else {
					materialDT.setItNew(false);
					materialDT.setItDirty(true);
					materialVO.setTheMaterialDT(materialDT);
					materialVO.setItNew(false);
					materialVO.setItDirty(true);
				}

				labResultProxyVO.getTheMaterialVOCollection().add(materialVO);
			} catch (Exception e) {
				logger.error("LabPageUtil.populateMaterialVO Exception thrown" +  e); 
				logger.error("LabPageUtil.populateMaterialVO Exception " +  e.getMessage()); 
				throw new NEDSSAppException(e.getMessage(), e);
			}
	}
	/**
	 * Populate ObservationVO
	 * @param tempid
	 * @param labResultProxyVO
	 * @param oldRootObservationVO
	 * @param form
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static ObservationVO populateObservationObject(Long tempid, LabResultProxyVO labResultProxyVO, ObservationVO oldRootObservationVO, PageForm form, HttpServletRequest req) throws Exception {
		ObservationVO observationVO= new ObservationVO(); 
		NbsQuestionMetadata nbsQuestionMetadata = null;
		String answerText="";
		//String Lab112AnswerText="";
		try {
			ObservationDT observationDT=null;
			ObsValueCodedDT obsValueCodedDT =null;
			ObsValueDateDT obsValueDateDT = null;
			ObsValueNumericDT obsValueNumericDT = null;
			ObsValueTxtDT  obsValueTxtDT = null;
			ObservationInterpDT observationInterpDT = null;
			ObservationReasonDT observationReasonDT = null;
			ObsValueCodedModDT obsValueCodedModDT = null;
			ActIdDT actIdDT =  new ActIdDT();
			if(oldRootObservationVO==null) {
				 observationDT= new ObservationDT();
				 observationDT.setItNew(true);
				 obsValueCodedDT =new ObsValueCodedDT();
				 obsValueCodedDT.setItNew(true);
				 obsValueDateDT = new ObsValueDateDT();
				 obsValueDateDT.setItNew(true);
				 obsValueNumericDT = new ObsValueNumericDT();
				 obsValueNumericDT.setItNew(true);
				 obsValueTxtDT = new ObsValueTxtDT();
				 obsValueTxtDT.setItNew(true);
				 observationInterpDT = new ObservationInterpDT();
				 observationInterpDT.setItNew(true);
				 observationReasonDT = new ObservationReasonDT();
				 observationReasonDT.setItNew(true);
				 obsValueCodedModDT = new ObsValueCodedModDT();
				 obsValueCodedModDT.setItNew(true);
				 actIdDT =  new ActIdDT();
				 actIdDT.setItNew(true);
				
				 
			}else {
				 observationDT= oldRootObservationVO.getTheObservationDT();
				 if(oldRootObservationVO.getTheObsValueCodedDTCollection()!=null && oldRootObservationVO.getTheObsValueCodedDTCollection().size()>0) {
					 obsValueCodedDT =(ObsValueCodedDT)getFirstElementOfCollection(oldRootObservationVO.getTheObsValueCodedDTCollection());
					 obsValueCodedDT.setItDirty(true);
				 }else {
					 obsValueCodedDT =new ObsValueCodedDT();
					 obsValueCodedDT.setItNew(true);
				 }
				
				 if(oldRootObservationVO.getTheObsValueDateDTCollection()!=null && oldRootObservationVO.getTheObsValueDateDTCollection().size()>0) {
					 obsValueDateDT =(ObsValueDateDT)getFirstElementOfCollection(oldRootObservationVO.getTheObsValueDateDTCollection());
					 obsValueDateDT.setItDirty(true);
				 }
				
				 if(oldRootObservationVO.getTheObsValueNumericDTCollection()!=null && oldRootObservationVO.getTheObsValueNumericDTCollection().size()>0) {
					 obsValueNumericDT =(ObsValueNumericDT)getFirstElementOfCollection(oldRootObservationVO.getTheObsValueNumericDTCollection());
					 obsValueNumericDT.setItDirty(true);
						
				 }
				
				 if(oldRootObservationVO.getTheObsValueTxtDTCollection()!=null && oldRootObservationVO.getTheObsValueTxtDTCollection().size()>0) {
					 obsValueTxtDT =(ObsValueTxtDT)getFirstElementOfCollection(oldRootObservationVO.getTheObsValueTxtDTCollection());
					 obsValueTxtDT.setItDirty(true);
						
				 }
				 if(oldRootObservationVO.getTheObservationInterpDTCollection()!=null && oldRootObservationVO.getTheObservationInterpDTCollection().size()>0) {
					 observationInterpDT =(ObservationInterpDT)getFirstElementOfCollection(oldRootObservationVO.getTheObservationInterpDTCollection());
					 observationInterpDT.setItDirty(true);
				 }
				 if(oldRootObservationVO.getTheObservationReasonDTCollection()!=null && oldRootObservationVO.getTheObservationReasonDTCollection().size()>0) {
					 observationReasonDT =(ObservationReasonDT)getFirstElementOfCollection(oldRootObservationVO.getTheObservationReasonDTCollection());
					 observationReasonDT.setItDirty(true);
				 }
				 if(oldRootObservationVO.getTheObsValueCodedModDTCollection()!=null && oldRootObservationVO.getTheObsValueCodedModDTCollection().size()>0) {
					 obsValueCodedModDT =(ObsValueCodedModDT)getFirstElementOfCollection(oldRootObservationVO.getTheObsValueCodedModDTCollection());
					 obsValueCodedModDT.setItDirty(true);
				 }
				 if(oldRootObservationVO.getTheActIdDTCollection()!=null && oldRootObservationVO.getTheActIdDTCollection().size()>0) {
					 actIdDT =(ActIdDT)getFirstElementOfCollection(oldRootObservationVO.getTheActIdDTCollection());
					 actIdDT.setItDirty(true);
				 }
			}
			String labFormCode = PageManagementCommonActionUtil.checkIfPublishedPageExists(req, NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
			questionMap =CaseCreateHelper.loadQuestions(labFormCode);	
			Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
			List<String> list= toList(ROOT_OBSERVATION);
			//Top level Observations list
			for(int i=0 ; i<list.size(); i++) {
				String key =list.get(i);
				answerText =(String)answerMap.get(key);
				nbsQuestionMetadata = (NbsQuestionMetadata)questionMap.get(key);
				if((!(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.ACT_ID) 
						|| nbsQuestionMetadata.getDataLocation().contains(RenderConstants.MATERIAL))))  {
					
					logger.debug("key here is " +key);
					logger.debug("nbsQuestionMetadata.getDataLocation() is " +nbsQuestionMetadata.getDataLocation());
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBSERVATION_DB))
						if(observationDT==null)
							observationDT = new ObservationDT();
						if(key.equals(PageConstants.LAB_CD_DESC_TXT) && answerText!=null && answerText.length()>0) {
							String firstPart =ResultedTestUtil.getFirstPart(answerText);
							String secondPart =ResultedTestUtil.getSecondPart(answerText);
							observationDT.setCd(firstPart);
							observationDT.setCdDescTxt(secondPart);
						}else {
							DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationDT, answerText, RenderConstants.OBSERVATION);
						}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBS_VALUE_CODED)) {
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedDT, answerText, RenderConstants.OBS_VALUE_CODED);
						if (obsValueCodedDT.getCode() == null || obsValueCodedDT.getCode().trim().length() == 0) {
							
						}else {
							if(observationVO.getTheObsValueCodedDTCollection()==null) {
								observationVO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
							}
							observationVO.getTheObsValueCodedDTCollection().add(obsValueCodedDT);
						}
					}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBS_VALUE_DATE)) {
						if(obsValueDateDT==null)
							obsValueDateDT = new ObsValueDateDT();
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueDateDT, answerText, RenderConstants.OBS_VALUE_DATE);
						if(observationVO.getTheObsValueDateDTCollection()==null) {
							observationVO.setTheObsValueDateDTCollection(new ArrayList<Object>());
						}
						obsValueDateDT.setObsValueDateSeq(observationVO.getTheObsValueDateDTCollection().size());
						observationVO.getTheObsValueDateDTCollection().add(obsValueDateDT);
					}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBS_VALUE_NUMERIC)) {
						if(obsValueNumericDT==null)
							obsValueNumericDT = new ObsValueNumericDT();
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueNumericDT, answerText, RenderConstants.OBS_VALUE_NUMERIC);
						if(observationVO.getTheObsValueNumericDTCollection()==null) {
							observationVO.setTheObsValueNumericDTCollection(new ArrayList<Object>());
						}
						obsValueNumericDT.setObsValueNumericSeq(observationVO.getTheObsValueNumericDTCollection().size());
						observationVO.getTheObsValueNumericDTCollection().add(obsValueNumericDT);
					}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBS_VALUE_TXT)) {
						if(obsValueTxtDT==null)
							obsValueTxtDT = new ObsValueTxtDT();
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueTxtDT, answerText, RenderConstants.OBS_VALUE_TXT);
						if(observationVO.getTheObsValueTxtDTCollection()==null) {
							observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
						}
						obsValueTxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size());
						observationVO.getTheObsValueTxtDTCollection().add(obsValueTxtDT);
					}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBSERVATION_INTERP)) {
						if(observationInterpDT==null)
							observationInterpDT = new ObservationInterpDT();
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationInterpDT, answerText, RenderConstants.OBSERVATION_INTERP);
						String temp = getDescTxt("OBS_INTRP", observationInterpDT.getInterpretationCd());
						if (temp != null) {
							observationInterpDT.setInterpretationDescTxt(temp);
						}

						if(observationVO.getTheObservationInterpDTCollection()==null) {
							observationVO.setTheObservationInterpDTCollection(new ArrayList<Object>());
						}
						observationVO.getTheObservationInterpDTCollection().add(observationInterpDT);
					}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBSERVATION_REASON)) {
						if(observationReasonDT==null)
							observationReasonDT = new ObservationReasonDT();
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationReasonDT, answerText, RenderConstants.OBSERVATION_REASON);
						if(observationVO.getTheObservationReasonDTCollection()==null) {
							observationVO.setTheObservationReasonDTCollection(new ArrayList<Object>());
						}
						observationVO.getTheObservationReasonDTCollection().add(observationReasonDT);
					}
					if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBS_VALUE_CODED_MOD)) {
						if(obsValueCodedModDT==null)
							obsValueCodedModDT = new ObsValueCodedModDT();
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedModDT, answerText, RenderConstants.OBS_VALUE_CODED_MOD);
						if(observationVO.getTheObsValueCodedModDTCollection()==null) {
							observationVO.setTheObsValueCodedModDTCollection(new ArrayList<Object>());
						}
						observationVO.getTheObsValueCodedModDTCollection().add(obsValueCodedModDT);
					}
					if(getQuestionToLegacyMap().get(observationDT.getCd())!=null) {
						observationDT.setCd(getQuestionToLegacyMap().get(observationDT.getCd()));
					}
					observationVO.setTheObservationDT(observationDT);
				}else if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.ACT_ID)) {
					if(answerText!=null && answerText.trim().length()>0) {
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, actIdDT, answerText, RenderConstants.ACT_ID);
					}else {
						actIdDT.setRootExtensionTxt("");
					}
					
					if(labResultProxyVO.getTheActIdDTCollection()==null) {
						observationVO.setTheActIdDTCollection(new ArrayList<Object>());
					}
					actIdDT.setActIdSeq(new Integer(0));
					observationVO.getTheActIdDTCollection().add(actIdDT);
					} 
				else if(nbsQuestionMetadata.getDataLocation().equalsIgnoreCase(RenderConstants.MATERIAL_CD)) {
						LabResultProxyVO oldLabResultProxyVO = (LabResultProxyVO)form.getPageClientVO().getOldPageProxyVO();

							populateMaterialVO(tempid, answerText, oldLabResultProxyVO, labResultProxyVO, nbsQuestionMetadata);
					}
			}
			 String shared =observationVO.getTheObservationDT().getSharedInd();

			 if ( shared == null) {
				 observationVO.getTheObservationDT().setSharedInd("F");
			 }
			 else
				 observationVO.getTheObservationDT().setSharedInd("T");
			 observationVO.getTheObservationDT().setAddReasonCd(NEDSSConstants.OBS_ADD_REASON_CD);
			 if(observationVO.getTheObservationDT().getCd()==null || observationVO.getTheObservationDT().getCd().equals("") || observationVO.getTheObservationDT().getCd().equals("$$")) {
				 PropertyUtil util = PropertyUtil.getInstance();  
				 observationVO.getTheObservationDT().setCd(util.getLab112Cd());
				 observationVO.getTheObservationDT().setCdDescTxt(NEDSSConstants.NOINFORMATIONGIVEN);
			 }

			 observationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD);
			 observationVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
			 if(observationVO.getTheObservationDT().getProgAreaCd()!=null && observationVO.getTheObservationDT().getProgAreaCd().equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE))
				 observationVO.getTheObservationDT().setProgAreaCd(NEDSSConstants.ANY);
			 if(observationVO.getTheObservationDT().getJurisdictionCd()!=null && observationVO.getTheObservationDT().getJurisdictionCd().equalsIgnoreCase(NEDSSConstants.JURISDICTION_NONE))
				 observationVO.getTheObservationDT().setJurisdictionCd(NEDSSConstants.ANY);
			 


		}
		catch (Exception e) {
			String errorText="LabPageUtil.populateObservationObject : Error processing transferBeanValueForDT for NbsQuestionMetadata:"+ nbsQuestionMetadata.toString()+"\n";
			String errorText2="LabPageUtil.populateObservationObject : Error processing transferBeanValueForDT for User value:"+ answerText+"\n";
			logger.error(errorText + errorText2 +e.getMessage() );
			throw new Exception(errorText + errorText2 +"\n"+ e.toString());
		}
		return observationVO;
	}
	
	/**
	 * 
	 * @param coll
	 * @return
	 */
	public static Object getFirstElementOfCollection(Collection<?> coll) {
		if(coll!=null && coll.size()>0) {
			Iterator<?> iter = coll.iterator();
			Object first = iter.next();
			return first;
		}else
			return null;
	}
	
	/**
	 * 
	 * @param participationDT
	 * @param subjectClassCode
	 * @param typeCd
	 * @param typeDesc
	 * @return
	 * @throws NEDSSAppException 
	 */
	public static ParticipationDT getParticipationDT(Collection<Object> participationDT, String subjectClassCode, String typeCd, String typeDesc) throws NEDSSAppException
    {

        try {
			if (participationDT != null)
			{
			    Iterator<Object> it = participationDT.iterator();
			    while (it.hasNext())
			    {
			        ParticipationDT part = (ParticipationDT) it.next();
			        if (part.getTypeCd().equalsIgnoreCase(typeCd) && part.getSubjectClassCd().equalsIgnoreCase(subjectClassCode)
			                && part.getTypeDescTxt().equalsIgnoreCase(typeDesc))
			        {
			            return part;
			        }
			    }
			}
		} catch (Exception e) {
			logger.error("LabPageUtil.getParticipationDT Exception thrown" +  e); 
			logger.error("LabPageUtil.getParticipationDT Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
        return null;
    }
	
	/**
	 * Get Description of code
	 * @param srt
	 * @param code
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String getDescTxt(String srt,String code) throws NEDSSAppException{
		String descTxt=null;
	    try{
	    	CachedDropDownValues cdv = new CachedDropDownValues();
	    	descTxt= cdv.getDescForCode(srt,code);
	    	if(descTxt==null)//NBSCentral defect #12482
	    		descTxt=code;
	    }catch(Exception e){
			logger.error("LabPageUtil.getDescTxt Exception thrown" +  e); 
			logger.error("LabPageUtil.getDescTxt Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
    }
	   return descTxt;
	}
	

  public static ActRelationshipDT genericActRelationShipCreate(String recordStatusCd,
	     Long sourceActUid, String sourceClassCd, String statusCd,
	     Long targetActUid, String targetClassCd, String typeCd, String typeDescTxt)
  	{
	   ActRelationshipDT actRelDT = new ActRelationshipDT();
	   actRelDT.setRecordStatusCd(recordStatusCd);
	   actRelDT.setSourceActUid(sourceActUid);
	   actRelDT.setSourceClassCd(sourceClassCd);
	   actRelDT.setStatusCd(statusCd);
	   actRelDT.setTargetActUid(targetActUid);
	   actRelDT.setTargetClassCd(targetClassCd);
	   actRelDT.setTypeCd(typeCd);
	   actRelDT.setTypeDescTxt(typeDescTxt);
	   actRelDT.setItNew(true);
	   logger.debug("\n\n\n sourceActUid = "  + sourceActUid + " targetActUid== "  + targetActUid +  " typeDescTxt== "  + typeDescTxt);
	   return actRelDT;
  	}
  
  public static Map<String, Map<Long, Object>> findResultedTestsObservation(LabResultProxyVO labResultProxyVO, ObservationVO rootObservationVO) throws NEDSSAppException {
	 Map<String, Map<Long, Object>> map  = new HashMap<String, Map<Long, Object>>();
	 
	  Collection<ObservationVO> observationColl = labResultProxyVO.getTheObservationVOCollection();
	  Collection<Object> actRelationshipColl = rootObservationVO.getTheActRelationshipDTCollection();
	  Map<Long, Object> resultedtTestMap  = new HashMap<Long, Object>();
	  Map<Long, Object> susceptibilityMap  = new HashMap<Long, Object>();
	  Map<Long, Object> track329Map  = new HashMap<Long, Object>();
	  Map<Long, Object> trackIsolateMap  = new HashMap<Long, Object>();
	  Map<Long, Object> observationPatientStatusVOMap  = new HashMap<Long, Object>();
	  Map<Long, Object> susAndTrackIsolateVOMap  = new HashMap<Long, Object>();
	  Map<Long, Object> aRSourceMap  = new HashMap<Long, Object>();
	  Map<Long, Object> labCommentMap  = new HashMap<Long, Object>();
	  Map<Long, Object> labResultCommentMap  = new HashMap<Long, Object>();
	  Collection<String> list= null;
		
	  //ActRelationship to get ResultedTests
	  Map<Long, Object> actRelationshipDTMap  = new HashMap<Long, Object>();
	  ActRelationshipDT actRelationshipDT = null;
	  try {
			if(actRelationshipColl!=null) {
				Iterator<?> it  = actRelationshipColl.iterator();
				while(it.hasNext()) {
					actRelationshipDT = (ActRelationshipDT)it.next();
					actRelationshipDTMap.put(actRelationshipDT.getSourceActUid(), actRelationshipDT);
				}
			}
		} catch (Exception e) {
			logger.error("LabPageUtil.findResultedTestsObservation Exception thrown while processing actRelationshipDTMap" +  e); 
			logger.error("LabPageUtil.findResultedTestsObservation Exception thrown while processing actRelationshipDTMap" +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	  
	  ObservationVO observationVO=null;
	  try {
		  list =TrackIsolateUtil.getQuestionToLegacyMap().values();
			if(observationColl!=null) {
				Iterator<?> it  = observationColl.iterator();
				while(it.hasNext()) {
					observationVO = (ObservationVO)it.next();
					if(observationVO.getTheObservationDT().getObservationUid().compareTo(rootObservationVO.getTheObservationDT().getObservationUid()) == 0){
						continue;
					}else if(observationVO.getTheObservationDT().getCd()!=null && 
							(observationVO.getTheObservationDT().getObsDomainCdSt1().equals(NEDSSConstants.LAB222) ||  observationVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB_330))) {
						observationPatientStatusVOMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);

					}else if(observationVO.getTheObservationDT().getCd()!=null && observationVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB222)) {
						susceptibilityMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);
						if(observationVO.getTheActRelationshipDTCollection()!=null && observationVO.getTheActRelationshipDTCollection().size()>0) {
							Iterator<Object> iterator = observationVO.getTheActRelationshipDTCollection().iterator();
							while(iterator.hasNext()) {
								ActRelationshipDT arDT = (ActRelationshipDT)iterator.next();
								aRSourceMap.put(arDT.getSourceActUid(), arDT);
							}
						}
					}else if(observationVO.getTheObservationDT().getCd()!=null && observationVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB_329)) {
						track329Map.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);
						if(observationVO.getTheActRelationshipDTCollection()!=null && observationVO.getTheActRelationshipDTCollection().size()>0) {
							Iterator<Object> iterator = observationVO.getTheActRelationshipDTCollection().iterator();
							while(iterator.hasNext()) {
								ActRelationshipDT arDT = (ActRelationshipDT)iterator.next();
								aRSourceMap.put(arDT.getSourceActUid(), arDT);
							}
						}
					}else if(observationVO.getTheObservationDT().getCd()!=null &&  observationVO.getTheObservationDT().getCd().equals(PageConstants.LAB_COMMENT_214)) {
						labResultCommentMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);

					}
					else {
						if(actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid())!=null) {
							ActRelationshipDT secondTierArDT =  (ActRelationshipDT)actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid());
							if(secondTierArDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT108_TYP_CD)
									&& 	secondTierArDT.getTypeDescTxt().equalsIgnoreCase(NEDSSConstants.HAS_COMPONENT)
									&& 	observationVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD)
									&& 	observationVO.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase(NEDSSConstants.LAB_CTRLCD_DISPLAY)
									) {
								resultedtTestMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);
							}
							else{
								
								if (actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid())!=null) {
									secondTierArDT =  (ActRelationshipDT)actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid());
									if(secondTierArDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT108_TYP_CD)
											&& 	secondTierArDT.getTypeDescTxt().equalsIgnoreCase("Component")
											&& 	observationVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD)
											&& 	observationVO.getTheObservationDT().getCtrlCdDisplayForm()==null
											) {
								
										resultedtTestMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);
									}
								}
							}
						}else if(actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid())!=null) {
							ActRelationshipDT secondTierArDT =  (ActRelationshipDT)actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid());
							if(secondTierArDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.APND)
									&& 	secondTierArDT.getTypeDescTxt().equalsIgnoreCase(NEDSSConstants.APPENDS)
									&& 	observationVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase(NEDSSConstants.C_ORDER)
									&& 	observationVO.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase(NEDSSConstants.LAB_CTRLCD_DISPLAY)
									) {
								labCommentMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);
							}
						}
						else if(actRelationshipDTMap.get(observationVO.getTheObservationDT().getObservationUid())==null) {
							String code = observationVO.getTheObservationDT().getCd();
							ActRelationshipDT arDT =null;
							if(list.contains(code)) {
								if(aRSourceMap.get(observationVO.getTheObservationDT().getObservationUid())!=null) {
									arDT =(ActRelationshipDT)aRSourceMap.get(observationVO.getTheObservationDT().getObservationUid());
									if(trackIsolateMap.get(arDT.getTargetActUid())!=null) {
										Map<String, Object> trackIsolateObservation  = (Map<String, Object>)trackIsolateMap.get(arDT.getTargetActUid());
										trackIsolateObservation.put(code, observationVO);
										trackIsolateMap.put(arDT.getTargetActUid(), trackIsolateObservation);
									}else {
										Map<String, Object> trackIsolateObservation  = new HashMap<String, Object>();
										trackIsolateObservation.put(code, observationVO);
										trackIsolateMap.put(arDT.getTargetActUid(), trackIsolateObservation);
									}
								}
							}else {
								susAndTrackIsolateVOMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO);
								}
						}
					}
				}
			}else {
				String erroText="LabPageUtil.findResultedTestsObservation: observationColl is null!!!!";
				logger.error(erroText);
				throw new NEDSSAppException(erroText);
			}
			
			map.put(PageConstants.TRACK_329_VO_TEST, track329Map);
			map.put(PageConstants.TRACK_ISOLATE_TEST, trackIsolateMap);
			map.put(PageConstants.ISOLATE_AND_SUSCEPTABILITY_TEST_VO, susAndTrackIsolateVOMap);
			map.put(PageConstants.AR_MAP_FOR_LAB, aRSourceMap);
			map.put(PageConstants.SUSCEPTABILITY_TEST, susceptibilityMap);
			map.put(PageConstants.LAB_RESULTED_TEST, resultedtTestMap);
			map.put(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION, observationPatientStatusVOMap);
			map.put(PageConstants.LAB_COMMENT, labCommentMap);
			map.put(PageConstants.LAB_RESULT_COMMENT, labResultCommentMap);
			
			
			
	  } catch (Exception e) {
			logger.error("LabPageUtil.findResultedTestsObservation Exception thrown while processing observationColl:-" +  e); 
			logger.error("LabPageUtil.findResultedTestsObservation Exception while processing observationColl:-" +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	  
	  return map;
  }
  
  public static Long setPatientStatusVO(ObservationVO rootObservationVO, PageForm obsForm, Long tempID, 
  		LabResultProxyVO labResultProxyVO, Map<Long, Object> oldObservationPatientStatusVOMap) throws NEDSSAppException{
  	String nbs330Value=null;
  	ObservationVO oldPatientStatusVO=null;
  	Collection<Object> coll =oldObservationPatientStatusVOMap.values();
  	CachedDropDownValues cdv = new CachedDropDownValues();
  	try{
  		ObservationVO nBS330ObservationVO = new ObservationVO();
  		
			//ACT108 between lab112 and 108 
      	Map<Object,Object> answerMap = obsForm.getPageClientVO().getAnswerMap();
      	
      	if(answerMap.get(NEDSSConstants.NBS_LAB330)!=null)
      		nbs330Value =(String)answerMap.get(NEDSSConstants.NBS_LAB330);
      	
      	if(coll!=null && coll.size()==0 && (nbs330Value==null  || nbs330Value.equals(""))){
          	return tempID;
      	}
      	if(coll!=null && coll.size()>0) {
      		oldPatientStatusVO=(ObservationVO)LabPageUtil.getFirstElementOfCollection(coll);
      	}
      	
      	if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
      		labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
      	}
				
			//ACT108 between lab112 and 108 
			if (!nbs330Value.equals("") && oldPatientStatusVO==null) {
				nBS330ObservationVO.setItNew(true);
				nBS330ObservationVO.getTheObservationDT().setItNew(true);
				nBS330ObservationVO.getTheObservationDT().setCd(NEDSSConstants.LAB_330);
				nBS330ObservationVO.getObsValueCodedDT_s(0).setItNew(true);
				nBS330ObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setCode(nbs330Value);
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setDisplayName(cdv.getDescForCode("PHVSFB_SPCMNPTSTATUS", nbs330Value));
	    		nBS330ObservationVO.getTheObservationDT().setStatusTime(new Timestamp(new Date().getTime()));
				nBS330ObservationVO.getTheObservationDT().setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
				nBS330ObservationVO.getTheObservationDT().setCdDescTxt(NEDSSConstants.LAB_330_CD_DESC_TXT);
				nBS330ObservationVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				nBS330ObservationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.PATIENT_STATUS_OBS_DOMAIN_CD);
          	
				ActRelationshipDT actRelationship330DT  =LabPageUtil.genericActRelationShipCreate(
              NEDSSConstants.RECORD_STATUS_ACTIVE,
              nBS330ObservationVO.getTheObservationDT().getObservationUid(),
              NEDSSConstants.CLASS_CD_OBS,
              NEDSSConstants.STATUS_ACTIVE,
              rootObservationVO.getTheObservationDT().getObservationUid(),
              NEDSSConstants.CLASS_CD_OBS,
              NEDSSConstants.ACT108_TYP_CD,
              NEDSSConstants.HAS_COMPONENT);
  			labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship330DT);
			}
			else if (nbs330Value.equals("") && oldPatientStatusVO!=null){
				nBS330ObservationVO= oldPatientStatusVO;
				nBS330ObservationVO.setItDelete(true);
				nBS330ObservationVO.getTheObservationDT().setItDelete(true);
				nBS330ObservationVO.getTheObservationDT().setCd(NEDSSConstants.NBS_LAB330);
				nBS330ObservationVO.getObsValueCodedDT_s(0).setItDelete(true);
				ActRelationshipDT actDT1 = new ActRelationshipDT();
				actDT1.setTargetActUid(rootObservationVO.getTheObservationDT().getObservationUid());
				actDT1.setSourceActUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
				actDT1.setTypeCd(NEDSSConstants.ACT108_TYP_CD);
				actDT1.setItDelete(true);
				labResultProxyVO.getTheActRelationshipDTCollection().add(actDT1);
			}
			else{
				nBS330ObservationVO= oldPatientStatusVO;
				nBS330ObservationVO.getTheObservationDT().setObservationUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setCode(nbs330Value);
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setDisplayName(cdv.getDescForCode("PHVSFB_SPCMNPTSTATUS", nbs330Value));
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setObservationUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
	    		nBS330ObservationVO.setItDirty(true);
	    		nBS330ObservationVO.setItNew(false);
	    		nBS330ObservationVO.getTheObservationDT().setItDirty(true);
	    		nBS330ObservationVO.getTheObservationDT().setItNew(false);
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setItDirty(true);
	    		nBS330ObservationVO.getObsValueCodedDT_s(0).setItNew(false);
	    	}
			labResultProxyVO.getTheObservationVOCollection().add(nBS330ObservationVO);
  	} catch(Exception ex){
  	logger.error("Error thrown within setPatientStatusVO:"+ex);	
  	throw new NEDSSAppException("Error thrown within setPatientStatusVO:",ex);
  	}
  	return tempID;
  }
  
  /**
   * Method to set lab comment object
   * @param rootObservationVO
   * @param obsForm
   * @param tempID
   * @param labResultProxyVO
   * @param oldLabCommentVOMap
   * @return
   * @throws NEDSSAppException
   */
  public static Long setLabCommentVO(ObservationVO rootObservationVO, PageForm obsForm, Long tempID, 
	  		LabResultProxyVO labResultProxyVO, Map<Long, Object> oldLabResultCommentVOMap) throws NEDSSAppException{
	  	String nbsLAB266Value=null;
	  	ObservationVO oldLabResultCommentVO=null;
	  	
	  	Collection<Object> collResult =oldLabResultCommentVOMap.values();
	  	try{
	  		ObservationVO labCommentObservationVO = new ObservationVO();
	  		ObservationVO labCommentResultObservationVO = new ObservationVO();
	  		
	      	Map<Object,Object> answerMap = obsForm.getPageClientVO().getAnswerMap();
	      	if(answerMap.get(PageConstants.NBS460)!=null)
	      		nbsLAB266Value =(String)answerMap.get(PageConstants.NBS460);

	      	if(collResult!=null && collResult.size()>0) {
	      		oldLabResultCommentVO=(ObservationVO)LabPageUtil.getFirstElementOfCollection(collResult);
	      	}
	      	
	      	
	      	if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
	      		labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
	      	}
			if (oldLabResultCommentVO==null) {
				labCommentObservationVO.setItNew(true);
				labCommentObservationVO.getTheObservationDT().setItNew(true);
				labCommentObservationVO.getTheObservationDT().setStatusCd(NEDSSConstants.STATUS_CD_D);
				labCommentObservationVO.getTheObservationDT().setCd(NEDSSConstants.NOINFORMATIONGIVEN_CODE);
				labCommentObservationVO.getTheObservationDT().setCdDescTxt(NEDSSConstants.NOINFORMATIONGIVEN);
				labCommentObservationVO.getTheObservationDT().setCdSystemCd("2.16.840.1.113883");
				labCommentObservationVO.getTheObservationDT().setCdSystemDescTxt(NEDSSConstants.LABCOMMENT);
				labCommentObservationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.C_ORDER);
				labCommentObservationVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT_DESC);
				labCommentObservationVO.getTheObservationDT().setStatusTime( new Timestamp(new Date().getTime()));
			    if(rootObservationVO != null){
			    	labCommentObservationVO.getTheObservationDT().setEffectiveFromTime(rootObservationVO.getTheObservationDT().getEffectiveFromTime());
			    	labCommentObservationVO.getTheObservationDT().setActivityToTime(rootObservationVO.getTheObservationDT().getActivityToTime());
			    	labCommentObservationVO.getTheObservationDT().setRptToStateTime(rootObservationVO.getTheObservationDT().getRptToStateTime());
			      }
				labCommentObservationVO.getObsValueTxtDT_s(0).setItNew(true);
				labCommentObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
				labCommentObservationVO.getObsValueTxtDT_s(0).setValueTxt(nbsLAB266Value);
				labCommentObservationVO.getObsValueTxtDT_s(0).setTxtTypeCd("N");
				labCommentObservationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());

			    
				ActRelationshipDT actRelationship130DT  =LabPageUtil.genericActRelationShipCreate(
                        NEDSSConstants.RECORD_STATUS_ACTIVE,
                        labCommentObservationVO.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS,
                        NEDSSConstants.STATUS_ACTIVE,
                        rootObservationVO.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.APND,
                        NEDSSConstants.APPENDS);

				
				labCommentResultObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
				labCommentResultObservationVO.getTheObservationDT().setCd(PageConstants.LAB_COMMENT_214);
				labCommentResultObservationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.C_RESULT);
				labCommentResultObservationVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LABCOMMENT);
				labCommentResultObservationVO.getTheObservationDT().setCdDescTxt("User Report Comment");
				labCommentResultObservationVO.getTheObservationDT().setCdSystemCd("NBS");
				labCommentResultObservationVO.getTheObservationDT().setCdSystemDescTxt("NEDSS Base System");
				labCommentResultObservationVO.getTheObservationDT().setStatusCd("D");
				labCommentResultObservationVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
				labCommentResultObservationVO.getTheObservationDT().setAddUserId(rootObservationVO.getTheObservationDT().getAddUserId());
				labCommentResultObservationVO.getTheObservationDT().setActivityToTime(new java.sql.Timestamp(new Date().getTime()));
				
				labCommentResultObservationVO.getObsValueTxtDT_s(0).setValueTxt(nbsLAB266Value);

				ActRelationshipDT actRelationship130ExtDT = LabPageUtil.genericActRelationShipCreate(
	                        NEDSSConstants.RECORD_STATUS_ACTIVE,
	                        labCommentResultObservationVO.getTheObservationDT().getObservationUid(),
	                        NEDSSConstants.CLASS_CD_OBS,
	                        NEDSSConstants.STATUS_ACTIVE,
	                        labCommentObservationVO.getTheObservationDT().getObservationUid(),
	                        NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.COMP,
	                        NEDSSConstants.IS_CAUSE_FOR);
				 
				labCommentResultObservationVO.setItNew(true);
				labCommentResultObservationVO.setItDirty(false);
				labCommentResultObservationVO.getTheObservationDT().setItNew(true);
				labCommentResultObservationVO.getTheObservationDT().setItDirty(false);
					
				labCommentObservationVO.setItNew(true);	
				labCommentObservationVO.setItDirty(false);
				labCommentObservationVO.getTheObservationDT().setItNew(true);	
				labCommentObservationVO.getTheObservationDT().setItDirty(false);
	  			labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship130ExtDT);
	  			labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship130DT);
	  			labResultProxyVO.getTheObservationVOCollection().add(labCommentObservationVO);
	  			labResultProxyVO.getTheObservationVOCollection().add(labCommentResultObservationVO);
	  			
			}
			else {
				oldLabResultCommentVO.getObsValueTxtDT_s(0).setValueTxt(nbsLAB266Value);
				oldLabResultCommentVO.setItDirty(true);
				oldLabResultCommentVO.setItNew(false);
				oldLabResultCommentVO.getObsValueTxtDT_s(0).setItDirty(true);
				oldLabResultCommentVO.getObsValueTxtDT_s(0).setItNew(false);
				labResultProxyVO.getTheObservationVOCollection().add(oldLabResultCommentVO);
	    	}
	  	} catch(Exception ex){
	  	logger.error("Error thrown within setLabCommentVO:"+ex);	
	  	throw new NEDSSAppException("Error thrown within setLabCommentVO:",ex);
	  	}
	  	return tempID;
	  }

  /**
   * This is a utility method to create ObservationVO object that is used to create ObservationVO object with default values passed as parameters
   * @return
   */
  public static ObservationVO createObservationVO(Long tempID, String code, String codeDescTxt, String codeSystemCode,  
		  				String codeSystemDescTxt, Timestamp effectiveFromTime, Timestamp effectiveToTime, String StatusCode, 
		  				String RecordStatusCode, String obsDomainCodeSt1) {
	  ObservationVO observationVO  =  new ObservationVO();
	  ObservationDT observationDT = new ObservationDT();
	  observationDT = observationVO.getTheObservationDT();
	  observationDT.setObservationUid(new Long(tempID));
	  observationDT.setCd(code);
	  observationDT.setCdSystemCd(codeSystemCode);
	  observationDT.setCdDescTxt(codeDescTxt);
	  observationDT.setCdSystemDescTxt(codeSystemDescTxt);
	  observationDT.setEffectiveFromTime(effectiveFromTime);
	  observationDT.setEffectiveToTime(effectiveToTime);
	  observationDT.setStatusCd(StatusCode);
	  observationDT.setRecordStatusCd(RecordStatusCode);
	  observationDT.setObsDomainCdSt1(obsDomainCodeSt1);
	  observationDT.setItNew(true);
	  observationVO.setTheObservationDT(observationDT);
	  observationVO.setItNew(true);
      return observationVO;
    		  
  }
  /**
   * @method : fetchObservationVO
   * @params : ObservationUID, ObservationVOCollection
   * @returnType : ObservationVO
   */
  public ObservationVO fetchObservationVO(Long sUID, Collection<ObservationVO> obsColl)
  {

      Iterator<ObservationVO> obsIter = obsColl.iterator();

      while (obsIter.hasNext())
      { 
          ObservationVO observationVO = (ObservationVO) obsIter.next();

          if (observationVO.getTheObservationDT().getObservationUid().compareTo(sUID) == 0)

              return observationVO;
      }

      return null;
  } // fetchObservationVO

}
