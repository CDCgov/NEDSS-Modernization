package gov.cdc.nedss.systemservice.ejb.pamconversionejb.util;

import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.material.dt.ManufacturedMaterialDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;

/**
 * @author pateldh
 *
 */
public class LegacyToPBConverterProcessor {
	static final LogUtils logger = new LogUtils(LegacyToPBConverterProcessor.class.getName());
	static PortPageUtil portUtil = new PortPageUtil();
	
	private static HashMap<Object,Object> nbsConversionMappingMap = new HashMap<Object,Object>();
	
	private static ArrayList<String> queToPortList = new ArrayList<String>();
	
	private static void initializeMapping(){
		nbsConversionMappingMap = new HashMap<Object,Object>();
		queToPortList = new ArrayList<String>();
	}
	
	/**
	 * @param runType
	 * @param conditionCdGroupId
	 * @param numberOfCases
	 * @param conditionCd
	 * @param toPageWaTemplateUid
	 * @param nbsConversionPageMgmtUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String startConversionProcess(String runType, Long conditionCdGroupId, int numberOfCases,String conditionCd, Long toPageWaTemplateUid, Long nbsConversionPageMgmtUid, String busObjType, HttpServletRequest request) throws Exception{
		try{
			logger.debug("runType: "+runType+", numberOfCases: "+numberOfCases+", conditionCd: "+conditionCd+", toPageWaTemplateUid: "+toPageWaTemplateUid+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", busObjType: "+busObjType);
			String runStatus = "";
			if(PortPageUtil.RUN_TYPE_PRERUN.equals(runType)){
				runStatus = PBtoPBConverterProcessor.preRunValidation(conditionCdGroupId,conditionCd,nbsConversionPageMgmtUid,request,runType, toPageWaTemplateUid, busObjType);
			}else if(PortPageUtil.RUN_TYPE_PRODUCTION.equals(runType)){
				runStatus = PBtoPBConverterProcessor.preRunValidation(conditionCdGroupId,conditionCd,nbsConversionPageMgmtUid,request,runType, toPageWaTemplateUid, busObjType);
				
				if(!portUtil.PRE_RUN_ERROR.equals(runStatus)){
					initializeMapping();
					if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType)){
						conditionCd = PortPageUtil.LEGACY_EVENT_DUMMY_CONDITION_CD;
					}
					runStatus = convertLegacyRecords(conditionCd, conditionCdGroupId, numberOfCases, toPageWaTemplateUid, nbsConversionPageMgmtUid, busObjType, request);
				}
			}
			return runStatus;
		}catch(Exception ex){
			logger.fatal("Exception thrown at startConversionProcess: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * @param conditionCd
	 * @param conditionCdGroupId
	 * @param numberOfrecords
	 * @param toPageWaTemplateUid
	 * @param nbsConversionPageMgmtUid
	 * @param request
	 * @return
	 */
	private static String convertLegacyRecords(String conditionCd, Long conditionCdGroupId, int numberOfRecords, Long toPageWaTemplateUid, Long nbsConversionPageMgmtUid, String busObjType, HttpServletRequest request){
		
		java.util.Date startDate= new java.util.Date();
		Timestamp startTime = new Timestamp(startDate.getTime());
		NBSConversionMasterDT nbsConvMasterDT = new NBSConversionMasterDT();
		long startTimeMillis = System.currentTimeMillis();
		NBSConversionConditionDT nbsConvCondDT = PBtoPBConverterProcessor.getNbsConversionConditionDTByCondition(conditionCd, nbsConversionPageMgmtUid,request);
		try{
			//Load mapping and list of questions to port.
			loadMappingAndConversionMaps(conditionCdGroupId, request);
			
			//Publish the To Page if not
			publishToPage(toPageWaTemplateUid, request);
			
			//get list of intervention_uid associated with Legacy Page.
			Map<Long, String> eventUidAndLocalIdMap = getNotConvertedInterventionUids(conditionCdGroupId, request);
			if(eventUidAndLocalIdMap!=null)
				logger.debug("Not Converted eventUidAndLocalIdMap count: "+eventUidAndLocalIdMap.size());
			
			Collection<Object> errorColl = new ArrayList<Object>();
			
			long convertedRecordsCount = 0;
			//Iterate through cases one by one and move data.
			if(eventUidAndLocalIdMap!=null && eventUidAndLocalIdMap.keySet().size() >= numberOfRecords){
				Iterator it = eventUidAndLocalIdMap.entrySet().iterator();
			    while (it.hasNext() && convertedRecordsCount<numberOfRecords) {
			        Map.Entry element = (Map.Entry)it.next();//numberOfCases
					 
						Long eventUid = (Long) element.getKey();
						String localId = (String) element.getValue();
						logger.debug("Converting eventUid: "+eventUid+", LocalId: "+localId);
						
						nbsConvMasterDT.setActUid(eventUid);
						if(eventUid!=null){
							Long actUID = null;
							
							//Help to find out failure records from nbs_conversion_master table.
							nbsConvMasterDT.setActUid(actUID);
					        nbsConvMasterDT.setProcessMessageTxt("Conversion started for "+localId);
					        
							//Convert Vaccination record one by one
							if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType)){
								actUID = convertVaccination(eventUid, busObjType, request);
							}
							
							logger.info("After Conversion actUID: "+actUID);
					        convertedRecordsCount++;
					        if(actUID!=null){
						        // Add logging in NBS_Conversion_Master table for successful conversion of case, insert dropDownCodeDT.getValue(); i.e. Conversion successful for CAS10001010GA01
						        nbsConvMasterDT.setActUid(actUID);
						        nbsConvMasterDT.setProcessMessageTxt("Conversion successful for "+localId);
						        nbsConvMasterDT.setStatusCd("Pass");
						        nbsConvMasterDT.setProcessTypeInd(portUtil.RUN_TYPE_PRODUCTION);
								
						        //After converting 1st case update condition Mapping Status IN_PROGESS. It helps to lock mapping.
								if(convertedRecordsCount==1){
									PBtoPBConverterProcessor.updateNbsConversionCondition(nbsConversionPageMgmtUid, PortPageUtil.NBS_PAGE_MAPPING_STATUS_PORTING_IN_PROGRESS, conditionCdGroupId, conditionCd, request);
								}
								logger.debug("Conversion successful for - eventUid: "+eventUid+", LocalId: "+localId);
					        }else{
					        	// Add Error Log
					        	logger.debug("Conversion failed for - eventUid: "+eventUid+", LocalId: "+localId);
					        	NBSConversionMappingDT nbsConvMapDT = new NBSConversionMappingDT();
					        	nbsConvMapDT.setConditionCdGroupId(conditionCdGroupId);
					        	nbsConvMapDT.setNbsConversionMappingUid(0L);
						        nbsConvMasterDT.setProcessMessageTxt("Conversion failed.Please check NBS_conversion_error table for further details uid: "+eventUid+", LocalId: "+localId);
						        nbsConvMasterDT.setStatusCd("Fail");
						        nbsConvMasterDT.setProcessTypeInd(portUtil.RUN_TYPE_PRODUCTION);
						        String errorMessage = "Converison Failed for Business Object Type "+busObjType+"'s Record :"+localId;
						        NBSConversionErrorDT nbsConErrorDT = portUtil.setNBSConversionMappingDT("CONVERSION_FAILED",errorMessage,nbsConvMapDT);
						        errorColl.add(nbsConErrorDT);
						        nbsConvMasterDT.setNBSConversionErrorDTCollection(errorColl);
					        }
					        
					        java.util.Date endDate = new java.util.Date();
					        Timestamp endTime = new Timestamp(endDate.getTime());
					        nbsConvMasterDT.setStartTime(startTime);
					        nbsConvMasterDT.setEndTime(endTime);
					        nbsConvMasterDT.setNbsConversionConditionUid(nbsConvCondDT.getNbsConversionConditionUid());
					        PBtoPBConverterProcessor.writeToLogMaster(nbsConvMasterDT,request.getSession());
					        
						}
			    }
			}
			
			long endTimeMillis   = System.currentTimeMillis();
			long totalTimeMillis = endTimeMillis - startTimeMillis;
			logger.debug("Total conversion time for "+numberOfRecords+" records is:"+totalTimeMillis+" milliseconds.");
			
			if(eventUidAndLocalIdMap.keySet().size()==numberOfRecords){
				logger.debug("Conversion completed successfully.");
				
				PBtoPBConverterProcessor.updateNbsConversionCondition(nbsConversionPageMgmtUid, PortPageUtil.NBS_PAGE_MAPPING_STATUS_COMPLETE, conditionCdGroupId, conditionCd, request);
				PBtoPBConverterProcessor.updateNbsConversionPageMgmt(nbsConversionPageMgmtUid, PortPageUtil.NBS_PAGE_MAPPING_STATUS_COMPLETE, request);
				return portUtil.PROD_RUN_COMPLETE;
			}
			
		}catch(Exception ex){
			logger.fatal("Exception thrown at convertLegacyRecords: "+ex.getMessage(), ex);
			
			java.util.Date endDate = new java.util.Date();
	        Timestamp endTime = new Timestamp(endDate.getTime());
	        nbsConvMasterDT.setStartTime(startTime);
			nbsConvMasterDT.setEndTime(endTime);
			nbsConvMasterDT.setProcessTypeInd(portUtil.RUN_TYPE_PRODUCTION);
			nbsConvMasterDT.setStatusCd("Fail");
			nbsConvMasterDT.setNbsConversionConditionUid(nbsConvCondDT.getNbsConversionConditionUid());
			//Extract exception details
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			String exceptionMessage = errors.toString();
			String errorMsgToStore = exceptionMessage.substring(0,Math.min(exceptionMessage.length(), 2000));
			nbsConvMasterDT.setProcessMessageTxt(errorMsgToStore);
			
			PBtoPBConverterProcessor.writeToLogMaster(nbsConvMasterDT,request.getSession());
			
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return portUtil.PROD_RUN_PARTIAL;
	}
	
	/**
	 * @param toPageWaTemplateUid
	 * @param request
	 */
	private static void publishToPage(Long toPageWaTemplateUid, HttpServletRequest request){
		try{
			PageManagementActionUtil util = new PageManagementActionUtil();
			WaTemplateVO waTemplateVO =  util.getPageDetails(toPageWaTemplateUid, request.getSession());
			WaTemplateDT templateDT = waTemplateVO.getWaTemplateDT();
			logger.debug("Publishing page waTemplateUid "+toPageWaTemplateUid+", TemplateNm: "+templateDT.getTemplateNm());
			if(PortPageUtil.TEMPLATE_TYPE_PUBLISHED_WITH_DRAFT.equals(templateDT.getTemplateType()) || PortPageUtil.TEMPLATE_TYPE_DRAFT.equals(templateDT.getTemplateType())){
				String mapName = (String) request.getAttribute("mapName");
				
				String versionNote = "This page was published by the system as a part of the Page Builder to Page Builder conversion process. "+
				"At the time of the conversion, all existing legacy vaccinations data were ported to this page using the "+mapName+" map.";
				
				templateDT.setVersionNote(versionNote);
				
				//Publish the page
				util.publishPage(templateDT, request.getSession());
			}
		}catch(Exception ex){
			logger.fatal("Exception thrown at publishToPage: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param conditionCdGroupId
	 * @param request
	 */
	private static void loadMappingAndConversionMaps(Long conditionCdGroupId, HttpServletRequest request){
		try{
			logger.debug("conditionCdGroupId: "+conditionCdGroupId);
			
			if(nbsConversionMappingMap.size()==0){
				ArrayList<Object> nbsConversionMappingList = new ArrayList<Object>();
				String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
		    	String sMethod = "getNBSConversionMappings";
				Object[] oParams = new Object[] {conditionCdGroupId};
				Object nbsConversionMappingObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
				nbsConversionMappingList = (ArrayList<Object>) nbsConversionMappingObj;
				logger.debug("Total Mapping questions: "+nbsConversionMappingList.size());
				
				for(int i=0;i<nbsConversionMappingList.size();i++){
					NBSConversionMappingDT conversionMappingDT = (NBSConversionMappingDT) nbsConversionMappingList.get(i);
					String key = "";
					if(conversionMappingDT.getFromCode()!=null)
						key = conversionMappingDT.getFromQuestionId().trim()+conversionMappingDT.getFromCode().trim();
					else
						key = conversionMappingDT.getFromQuestionId().trim();
					
					nbsConversionMappingMap.put(key, conversionMappingDT);
					
					queToPortList.add(conversionMappingDT.getFromQuestionId().trim());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception thrown while loading conversion mapping: conditionCdGroupId"+conditionCdGroupId+" ,Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * @param conditionCdGroupId
	 * @param request
	 * @return
	 */
	private static Map<Long, String> getNotConvertedInterventionUids(Long conditionCdGroupId, HttpServletRequest request){
		try{
			logger.debug("conditionCdGroupId: "+conditionCdGroupId);
			//get list of public_health_case_uid associated with From Page.
			
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
	    	String sMethod = "getInterventionUids";
			Object[] sParams = new Object[] {};
			Object interventionUidObj = CallProxyEJB.callProxyEJB(sParams, sBeanJndiName, sMethod, request.getSession());
			Map<Long, String> interventionUidAndLocalIdMap= (Map<Long, String>) interventionUidObj;
			
			ArrayList<Object> convertedInterventionUidList = PBtoPBConverterProcessor.getConvertedCasesFromNbsConversionMaster(conditionCdGroupId, PortPageUtil.CASE_CONVERSION_STATUS_CODE_PASS, request);
			
			for(int i=0;i<convertedInterventionUidList.size();i++){
				Long phcUid = (Long) convertedInterventionUidList.get(i);
				interventionUidAndLocalIdMap.remove(phcUid);
			}
			return interventionUidAndLocalIdMap;
		}catch(Exception ex){
			logger.fatal("Exception thrown getNotConvertedInterventionUids, conditionCdGroupId: "+conditionCdGroupId+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param interventionUid
	 * @param request
	 * @return
	 */
	public static VaccinationProxyVO getVaccinationProxy(Long interventionUid, HttpServletRequest request){
		try{
			logger.debug("interventionUid: "+interventionUid);
			String sBeanJndiName = JNDINames.INTERVENTION_PROXY_EJB;
	    	String sMethod = "getVaccinationProxy";
			Object[] sParams = new Object[] {interventionUid};
			Object object = CallProxyEJB.callProxyEJB(sParams, sBeanJndiName, sMethod, request.getSession());
			VaccinationProxyVO vaccinationProxyVO= (VaccinationProxyVO) object;
			return vaccinationProxyVO;
		}catch(Exception ex){
			logger.fatal("Exception thrown getVaccinationProxy interventionUid: "+interventionUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param interventionUid
	 * @param busObjType
	 * @param request
	 * @return
	 */
	private static Long convertVaccination(long interventionUid, String busObjType, HttpServletRequest request){
		try{
			//get getVaccinationProxy from InterventionProxyEJB. it will return VaccinationProxyVO.
			VaccinationProxyVO vaccinationProxyVO = getVaccinationProxy(interventionUid,request);
			//get pageActProxyVO and set fields from Observation and Material tables.
			PageActProxyVO pageActProxyVO = (PageActProxyVO) PageLoadUtil.getProxyObject(Long.toString(interventionUid), busObjType, request.getSession());
			
			if(vaccinationProxyVO!=null && pageActProxyVO!=null){
				for(Object key : nbsConversionMappingMap.keySet()){
					NBSConversionMappingDT conversionMappingDT = (NBSConversionMappingDT) nbsConversionMappingMap.get(key);
					
					if("VAC101".equals(conversionMappingDT.getFromQuestionId())){
						if(vaccinationProxyVO.getTheMaterialVO()!=null && vaccinationProxyVO.getTheMaterialVO().getTheMaterialDT()!=null){
							String materialNm = vaccinationProxyVO.getTheMaterialVO().getTheMaterialDT().getNm();
							if(materialNm!=null){
								pageActProxyVO.getInterventionVO().getTheInterventionDT().setMaterialCd(materialNm);
							}
						}
					}else if("VAC105".equals(conversionMappingDT.getFromQuestionId())){
						Iterator<ObservationVO>  iter =  vaccinationProxyVO.getTheObservationVOCollection().iterator();
				        while(iter.hasNext())
				        {
				           ObservationVO observationVO = iter.next();
				           Collection<Object>  obsValueNumColl = observationVO.getTheObsValueNumericDTCollection();
				           if(obsValueNumColl != null)
				           {
				              Iterator<Object>  obsValueNumIter = obsValueNumColl.iterator();
				               while(obsValueNumIter.hasNext())
				               {
				                  ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT)obsValueNumIter.next();
				                  BigDecimal ageAtVaccination = obsValueNumericDT.getNumericValue1();
				                  if(ageAtVaccination!=null)
				                	  pageActProxyVO.getInterventionVO().getTheInterventionDT().setAgeAtVacc(ageAtVaccination.intValue());
				               }
				           }
				        }
					}else if("VAC106".equals(conversionMappingDT.getFromQuestionId())){
						Iterator<ObservationVO>  iter =  vaccinationProxyVO.getTheObservationVOCollection().iterator();
				        while(iter.hasNext())
				        {
				           ObservationVO observationVO = iter.next();
				           Collection<Object>  obsValuecodedColl = observationVO.getTheObsValueCodedDTCollection();
				           if(obsValuecodedColl != null)
				           {
				              Iterator<Object>  obsValueCodedIter = obsValuecodedColl.iterator();
				               while(obsValueCodedIter.hasNext())
				               {
				                  ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)obsValueCodedIter.next();
				                  String ageAtVaccinationUnit = obsValueCodedDT.getCode();
				                  if(ageAtVaccinationUnit!=null)
				                	  pageActProxyVO.getInterventionVO().getTheInterventionDT().setAgeAtVaccUnitCd(ageAtVaccinationUnit);
				               }
				           }
				        }
					}else if("VAC107".equals(conversionMappingDT.getFromQuestionId())){
						if(vaccinationProxyVO.getTheMaterialVO()!=null && vaccinationProxyVO.getTheMaterialVO().getTheRoleDTCollection()!=null){
							Iterator<Object>  iter =  vaccinationProxyVO.getTheMaterialVO().getTheRoleDTCollection().iterator();
							while(iter.hasNext()){
								RoleDT roleDT = (RoleDT)iter.next();
								if(roleDT!=null && "MfgdVaccine".equals(roleDT.getCd()) && "MAT".equals(roleDT.getSubjectClassCd()) && "ORG".equals(roleDT.getScopingClassCd()) && "MfgrOfVaccine".equals(roleDT.getScopingRoleCd()) && roleDT.getScopingEntityUid()!=null){
									if(conversionMappingDT.getFromCode().equals(Long.toString(roleDT.getScopingEntityUid()))){
										pageActProxyVO.getInterventionVO().getTheInterventionDT().setVaccMfgrCd(conversionMappingDT.getToCode());
									}
								}
							}
						}
						
					}else if("VAC108".equals(conversionMappingDT.getFromQuestionId())){
						if(vaccinationProxyVO.getTheMaterialVO()!=null && vaccinationProxyVO.getTheMaterialVO().getTheManufacturedMaterialDTCollection()!=null){
							Iterator<Object>  iter =  vaccinationProxyVO.getTheMaterialVO().getTheManufacturedMaterialDTCollection().iterator();
							while(iter.hasNext())
					        {
								ManufacturedMaterialDT manufacturedMaterialDT = (ManufacturedMaterialDT)iter.next();
								if(manufacturedMaterialDT!=null){
									pageActProxyVO.getInterventionVO().getTheInterventionDT().setMaterialLotNm(manufacturedMaterialDT.getLotNm());
								}
					        }
						}
					}else if("VAC109".equals(conversionMappingDT.getFromQuestionId())){
						if(vaccinationProxyVO.getTheMaterialVO()!=null && vaccinationProxyVO.getTheMaterialVO().getTheMaterialDT()!=null){
							Iterator<Object>  iter =  vaccinationProxyVO.getTheMaterialVO().getTheManufacturedMaterialDTCollection().iterator();
							while(iter.hasNext())
					        {
								ManufacturedMaterialDT manufacturedMaterialDT = (ManufacturedMaterialDT)iter.next();
								if(manufacturedMaterialDT!=null){
									pageActProxyVO.getInterventionVO().getTheInterventionDT().setMaterialExpirationTime(manufacturedMaterialDT.getExpirationTime());
								}
					        }
						}
					}
					
				}
				
				//Update person participation's subjectClassCd and remove subjectClassCd='MAT' from Participation Collection
				
				Collection<Object>  participationDTColl = pageActProxyVO.getTheParticipationDTCollection();
	            if(participationDTColl != null)
	            {
	            	Iterator<Object>  iter =  participationDTColl.iterator();
					while(iter.hasNext())
			        {
						ParticipationDT participationDT = (ParticipationDT) iter.next();
						if(participationDT!=null && NEDSSConstants.SUBJECT_OF_VACCINE.equalsIgnoreCase(participationDT.getTypeCd())){
							participationDT.setSubjectClassCd(NEDSSConstants.CLASS_CD_PAT);
							participationDT.setItDirty(true);
						}else if(participationDT!=null && NEDSSConstants.MATERIAL_CLASS_CODE.equalsIgnoreCase(participationDT.getSubjectClassCd())){
							iter.remove();
						}
			        }
	            }
	            
	            //Create NBSActEntity
	            
	            Collection<Object>  nbsActEntityDTColl = createActEntityFromParticipation(participationDTColl, vaccinationProxyVO);
	            pageActProxyVO.getPageVO().setActEntityDTCollection(nbsActEntityDTColl);
	            
	            //Update InformationAsOfDate from AsOfDateGeneral
	            Collection<Object> personDTColl = pageActProxyVO.getThePersonVOCollection();
	            if(personDTColl != null)
	            {
	            	Iterator<Object>  iter =  personDTColl.iterator();
					while(iter.hasNext())
			        {
						PersonVO personVO = (PersonVO) iter.next();
						if (personVO!=null && personVO.getThePersonDT()!=null && NEDSSConstants.PAT.equals(personVO.getThePersonDT().getCd())) {
							Collection<Object> personNameDTColl = personVO.getThePersonNameDTCollection();
							if(personNameDTColl!=null){
								Iterator<Object>  nameIter =  personNameDTColl.iterator();
								while(nameIter.hasNext()){
									PersonNameDT personNameDT = (PersonNameDT) nameIter.next();
									if(personNameDT!=null){
										personVO.getThePersonDT().setAsOfDateAdmin(personNameDT.getAsOfDate());
										break;
									}
								}
							}
							personVO.getThePersonDT().setItDirty(true);
							personVO.getThePersonDT().setItNew(false);
							personVO.setItDirty(true);
							personVO.setItNew(false);
						}
			        }
	            }
	            
			}
			
			// Update pageActProxyVO
			pageActProxyVO.setItDirty(true);
			pageActProxyVO.setItNew(false);
			pageActProxyVO.setItDelete(false);
			pageActProxyVO.getInterventionVO().getTheInterventionDT().setItDirty(true);
			pageActProxyVO.setConversionHasModified(true);
	        Long actUID = PageStoreUtil.sendProxyToPageEJB(pageActProxyVO, request, NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE);
	        return actUID;
		}catch(Exception ex){
			logger.fatal("Exception thrown convertVaccination interventionUid: "+interventionUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	
	private static Collection<Object> createActEntityFromParticipation(Collection<Object> participationCollection, VaccinationProxyVO vaccinationProxyVO)
	{
		try{
			Collection<Object>  nbsActEntityDTColl = new ArrayList<Object> ();
			if (participationCollection != null && vaccinationProxyVO!=null) {
				Iterator<Object> iterator = participationCollection.iterator();
				while (iterator.hasNext()) {
					ParticipationDT partDT = (ParticipationDT) iterator.next();
					NbsActEntityDT nbsActEntityDT = new NbsActEntityDT();
					
					nbsActEntityDT.setActUid(partDT.getActUid());
					nbsActEntityDT.setAddTime(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getAddTime());
					nbsActEntityDT.setAddUserId(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getAddUserId());
					nbsActEntityDT.setEntityUid(partDT.getSubjectEntityUid());
					nbsActEntityDT.setEntityVersionCtrlNbr(new Integer(1));
					nbsActEntityDT.setLastChgTime(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getLastChgTime());
					nbsActEntityDT.setLastChgUserId(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getLastChgUserId());
					nbsActEntityDT.setRecordStatusCd(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getRecordStatusCd());
					nbsActEntityDT.setRecordStatusTime(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getRecordStatusTime());
					nbsActEntityDT.setTypeCd(partDT.getTypeCd());
					nbsActEntityDT.setItNew(true);
					nbsActEntityDT.setItDirty(false);
					nbsActEntityDT.setItDelete(false);
					nbsActEntityDTColl.add(nbsActEntityDT);
				}
			}
			return nbsActEntityDTColl;
		}catch(Exception ex){
			logger.fatal("Exception thrown createActEntityFromParticipation Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
}
