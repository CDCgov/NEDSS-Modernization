package gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.vo.ObservationQA;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseRootDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationAuditLogSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.reportadmin.dao.UserProfileDAO;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.CDAEventSummaryParser;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.GenericSummaryVO;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.util.UidSummaryVO;
import gov.cdc.nedss.util.VOTester;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.rmi.PortableRemoteObject;

public class RetrieveSummaryVO
    extends DAOBase {
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  private static final String statement_sql =
      " select obs.observation_uid observationUid, obs.cd cd, " +
      " obs.ctrl_cd_display_form ctrlCdDisplayForm , " +
      " obs.version_ctrl_nbr versionCtrlNbr, obs.shared_ind sharedInd, " +
      " obs.local_id localId, " +
      " obs.cd_desc_txt \"cdDescTxt\" , " +
      " obs.cd_system_desc_txt \"cdSystemDescTxt\"  , " +
      " obs.cd_system_cd \"cdSystemCd\" ," +
      " obs.cd_version \"cdVersion\" , " +
      " obscode.observation_uid obsCodeUid ,obscode.code code, obscode.original_txt originalTxt, obscode.code_system_desc_txt codeSystemDescTxt," +
      " obsdate.observation_uid obsDateUid , " +
      " obsdate.from_time fromTime, obsdate.to_time toTime, obsdate.duration_amt durationAmt, obsdate.duration_unit_cd durationUnitCd," +
      " obsDate.obs_value_date_seq obsValueDateSeq, " +
      " obsnumeric.observation_uid obsNumericUid , " +
      " obsnumeric.numeric_value_1 numericValue1, obsnumeric.numeric_value_2 numericValue2, " +
      " obsnumeric.numeric_scale_1 numericScale1, obsnumeric.numeric_scale_2 numericScale2, " +
      " obsnumeric.numeric_unit_cd numericUnitCd, obsnumeric.obs_value_numeric_seq obsValueNumericSeq, " +
      " obstxt.observation_uid obsTxtUid , " +
      " obstxt.value_txt valueTxt, obstxt.obs_value_txt_seq obsValueTxtSeq, " +
      " ar2.source_act_uid sourceActUid, ar2.target_act_uid targetActUid, " +
      " ar2.type_cd typeCd" +
      " from Observation obs " +
      " left outer join  obs_value_coded obscode " +
      " on obs.observation_uid   = obscode.observation_uid " +
      " left outer join  obs_value_date obsdate " +
      " on obs.observation_uid = obsdate.observation_uid  " +
      " left outer join  obs_value_numeric obsnumeric " +
      " on obs.observation_uid = obsnumeric.observation_uid " +
      " left outer join  obs_value_txt obstxt " +
      " on obs.observation_uid = obstxt.observation_uid  " +
      " inner join  act_relationship ar  " +
      " on  ar.source_act_uid = obs.observation_uid  " +
      " left outer join act_relationship ar2 " +
      " on ar2.target_act_uid  = ar.source_act_uid " +
      " where   " +
      "  ar.target_act_uid  = ?  " +
      " order by obs.observation_uid, ar2.source_act_uid  ";

  private static final String statement_oracle =
      "select obs.observation_uid \"observationUid\", obs.cd \"cd\", " +
      "obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", " +
      "obs.version_ctrl_nbr \"versionCtrlNbr\", obs.shared_ind \"sharedInd\", " +
      "obs.local_id \"localId\", " +
      "obs.cd_desc_txt \"cdDescTxt\" , " +
      "obs.cd_system_desc_txt \"cdSystemDescTxt\"  , " +
      "obs.cd_system_cd \"cdSystemCd\" ," +
      "obs.cd_version \"cdVersion\" , " +
      "obscode.observation_uid \"obsCodeUid\",obscode.code \"code\", obscode.original_txt \"originalTxt\", obscode.code_system_desc_txt \"codeSystemDescTxt\", " +
      "obsdate.observation_uid \"obsDateUid\", " +
      "obsdate.from_time \"fromTime\", obsdate.to_time \"toTime\", obsdate.duration_amt \"durationAmt\", obsdate.duration_unit_cd \"durationUnitCd\", " +
      "obsDate.obs_value_date_seq \"obsValueDateSeq\", " +
      "obsnumeric.observation_uid \"obsNumericUid\", " +
      "obsnumeric.numeric_value_1 \"numericValue1\", obsnumeric.numeric_value_2 \"numericValue2\", " +
      "obsnumeric.numeric_scale_1 \"numericScale1\", obsnumeric.numeric_scale_2 \"numericScale2\", " +
      "obsnumeric.numeric_unit_cd \"numericUnitCd\", obsnumeric.obs_value_numeric_seq \"obsValueNumericSeq\", " +
      "obstxt.observation_uid \"obsTxtUid\", " +
      "obstxt.value_txt \"valueTxt\", obstxt.obs_value_txt_seq \"obsValueTxtSeq\", " +
      "ar2.source_act_uid \"sourceActUid\", ar2.target_act_uid \"targetActUid\", " +
      "ar2.type_cd \"typeCd\" " +
      "from Observation obs, obs_value_coded obscode, obs_value_date obsdate, " +
      "obs_value_numeric obsnumeric, obs_value_txt obstxt, act_relationship ar, " +
      "act_relationship ar2 " +
      "where " +
      "obs.observation_uid = obscode.observation_uid(+) " +
      "and obs.observation_uid = obsdate.observation_uid(+) " +
      "and obs.observation_uid = obsnumeric.observation_uid(+) " +
      "and obs.observation_uid = obstxt.observation_uid(+) " +
      "and ar.source_act_uid = obs.observation_uid " +
      "and ar.source_act_uid = ar2.target_act_uid(+) " +
      "and ar.target_act_uid  = ?" +
      " order by obs.observation_uid, ar2.source_act_uid ";

  public RetrieveSummaryVO() {
  }

  private static final LogUtils logger = new LogUtils(RetrieveSummaryVO.class.
      getName());

  /**
       * This method will access the collection of summaryVO for investigations to be
   * papulated on view file page for a person whose UID has been passed
   * @param uid -- personUID
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @return myCollection  -- Colection of InvestgationSummaryVO for the pased person Uid
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  retrieveInvestgationSummaryVO(Long uid,
                                                  NBSSecurityObj nbsSecurityObj) {
    String aQuery = null;
    ArrayList<Object>  myCollection  = new ArrayList<Object> ();
    try{
	    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	        NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
	    logger.debug("WorkupProxyEJB:getInvestgationSummaryVO uid = " + uid +
	                 " - dataAccessWhereClause = " + dataAccessWhereClause);
	    if (dataAccessWhereClause == null) {
	      dataAccessWhereClause = "";
	    }
	    else {
	      dataAccessWhereClause = " AND " + dataAccessWhereClause;
	
	    }
	    
	      aQuery = WumSqlQuery.SELECT_WORKUP_SQL + dataAccessWhereClause;
	
	
	    logger.info("aQuery = " + aQuery);
	    logger.debug("getInvestgationSummaryVO - aQuery = " + aQuery);
	    ArrayList<Object>  pList = new ArrayList<Object> ();
	    
	    InvestigationSummaryVO investigationSummaryVO = new InvestigationSummaryVO();
	
	    /**
	     * Selects InvestgationSummary
	     */

	      ArrayList<Object> inputArgs = new ArrayList<Object> ();
	      inputArgs.add(uid);
	      pList = (ArrayList<Object> )preparedStmtMethod(investigationSummaryVO, inputArgs, aQuery, NEDSSConstants.SELECT);
	
	      CachedDropDownValues cache = new CachedDropDownValues();
	      TreeMap<?,?> mapPhcClass = cache.getCodedValuesAsTreeMap("PHC_CLASS");
	      mapPhcClass = cache.reverseMap(mapPhcClass); // we can add another method that do not do reverse
	      TreeMap<?, ?> mapPhcSts = cache.getCodedValuesAsTreeMap("PHC_IN_STS");
	      mapPhcSts = cache.reverseMap(mapPhcSts); // we can add another method that do not do reverse
	      TreeMap<Object, Object> mapConditionCode = cache.getConditionCodes();
	      TreeMap<Object, Object> mapJurisdiction = cache.getJurisdictionCodedValuesWithAll();
	
	      for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); ) {
	        logger.debug(
	            "WorkupProxyEJB:getInvestgationSummaryVO anIterator.hasNext()" +
	            anIterator.hasNext());
	        investigationSummaryVO = (InvestigationSummaryVO) anIterator.next();
	        if (investigationSummaryVO.getCaseClassCd() != null
	            && investigationSummaryVO.getCaseClassCd().trim().length() != 0)
	        {
	          investigationSummaryVO.setCaseClassCodeTxt((String)mapPhcClass.get(investigationSummaryVO.getCaseClassCd()));
	        }
	
	        if (investigationSummaryVO.getInvestigationStatusCd() != null
	            && investigationSummaryVO.getInvestigationStatusCd().trim().length() != 0)
	        {
	          investigationSummaryVO.setInvestigationStatusDescTxt((String)mapPhcSts.get(investigationSummaryVO.getInvestigationStatusCd()));
	        }
	
	        if (investigationSummaryVO.getCd() != null
	            && investigationSummaryVO.getCd().trim().length() != 0)
	        {
	         // map = cache.reverseMap(map); // we can add another method that do not do reverse
	          investigationSummaryVO.setConditionCodeText((String)mapConditionCode.get(investigationSummaryVO.getCd()));
	        }
	
	        if (investigationSummaryVO.getJurisdictionCd() != null
	            && investigationSummaryVO.getJurisdictionCd().trim().length() != 0)
	        {
	       //   map = cache.reverseMap(map); // we can add another method that do not do reverse
	          investigationSummaryVO.setJurisdictionDescTxt((String)mapJurisdiction.get(investigationSummaryVO.getJurisdictionCd()));
	        }
	        myCollection.add(investigationSummaryVO);
	      }
	    }
	    catch (Exception ex) {
	    	logger.error("Exception ="+ex.getMessage(), ex);
	    }
	    logger.debug("WorkupProxyEJB:getInvestgationSummaryVO Completed WorkupProxyEJB:getInvestgationSummaryVO***************");
	    return myCollection;
  } // getInvestgationSummaryVO

  /**
       * This method will access the collection of summaryVO for investigations to be
   * papulated on view file page for a person whose UID has been passed
   * @param uid -- personUID
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @return myCollection  -- Colection of InvestgationSummaryVO for the pased person Uid
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  retrieveInvestgationSummaryVO(Collection<Object> uidList,
                                                  NBSSecurityObj nbsSecurityObj) {
    String aQuery = null;
    ArrayList<Object>  myCollection  = new ArrayList<Object> ();
    try{
	    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	        NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
	    logger.debug("WorkupProxyEJB:getInvestgationSummaryVO uid = " + uidList +
	                 " - dataAccessWhereClause = " + dataAccessWhereClause);
	    if (dataAccessWhereClause == null) {
	      dataAccessWhereClause = "";
	    }
	    else {
	      dataAccessWhereClause = " AND " + dataAccessWhereClause;
	
	    }
	     dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "Public_health_case.program_jurisdiction_oid");
	    dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "Public_health_case.shared_ind");
	
	    Long uid = null;
		Iterator<Object> itor = uidList.iterator();
	    while (itor.hasNext()) {
	      uid = (Long) itor.next();
	      /** @todo Check break.*/
	      break;
	    }
	    aQuery = WumSqlQuery.SELECT_WORKUP_SQL_IN ;

	    aQuery = aQuery + uid.toString() + dataAccessWhereClause +" order by code1.code_short_desc_txt desc";
	    
	    logger.info("aQuery = " + aQuery);
	    logger.debug("getInvestgationSummaryVO - aQuery = " + aQuery);
	    ArrayList<Object>  pList = new ArrayList<Object> ();
	    
	    InvestigationSummaryVO investigationSummaryVO = new InvestigationSummaryVO();
	
	    /**
	     * Selects InvestgationSummary
	     */

	    pList = (ArrayList<Object> )preparedStmtMethod(investigationSummaryVO, null, aQuery, NEDSSConstants.SELECT);

	      CachedDropDownValues cache = new CachedDropDownValues();
	      TreeMap<?, ?> mapPhcClass = cache.getCodedValuesAsTreeMap("PHC_CLASS");
	      mapPhcClass = cache.reverseMap(mapPhcClass); // we can add another method that do not do reverse
	      TreeMap<?, ?> mapPhcSts = cache.getCodedValuesAsTreeMap("PHC_IN_STS");
	      mapPhcSts = cache.reverseMap(mapPhcSts); // we can add another method that do not do reverse
	      TreeMap<Object, Object> mapConditionCode = cache.getConditionCodes();
	      TreeMap<Object, Object> mapJurisdiction = cache.getJurisdictionCodedValuesWithAll();
	
	      for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); ) {
	        logger.debug(
	            "WorkupProxyEJB:getInvestgationSummaryVO anIterator.hasNext()" +
	            anIterator.hasNext());
	        investigationSummaryVO = (InvestigationSummaryVO) anIterator.next();
	        if (investigationSummaryVO.getCaseClassCd() != null
	            && investigationSummaryVO.getCaseClassCd().trim().length() != 0)
	        {
	          investigationSummaryVO.setCaseClassCodeTxt((String)mapPhcClass.get(investigationSummaryVO.getCaseClassCd()));
	        }
	
	        if (investigationSummaryVO.getInvestigationStatusCd() != null
	            && investigationSummaryVO.getInvestigationStatusCd().trim().length() != 0)
	        {
	          investigationSummaryVO.setInvestigationStatusDescTxt((String)mapPhcSts.get(investigationSummaryVO.getInvestigationStatusCd()));
	        }
	
	        if (investigationSummaryVO.getCd() != null
	            && investigationSummaryVO.getCd().trim().length() != 0)
	        {
	          investigationSummaryVO.setConditionCodeText((String)mapConditionCode.get(investigationSummaryVO.getCd()));
	        }
	
	        if (investigationSummaryVO.getJurisdictionCd() != null
	            && investigationSummaryVO.getJurisdictionCd().trim().length() != 0)
	        {
	          investigationSummaryVO.setJurisdictionDescTxt((String)mapJurisdiction.get(investigationSummaryVO.getJurisdictionCd()));
	        }
	
	        myCollection.add(investigationSummaryVO);
	      }
    }catch (Exception e) {
      logger.error("RetrieveSummaryVO:retrieveInvestgationSummaryVO Error thrown! Please check!" + e.getMessage(),e);
    }
    logger.debug("RetrieveSummaryVO:retrieveInvestgationSummaryVO Completed WorkupProxyEJB:getInvestgationSummaryVO***************");
    return myCollection;
  } // getInvestgationSummaryVO

  private String prepareSQLForContainsIn(Collection<Object> uidList, String query,
                                         String whereClause) {
	  Long uid = null;
	  StringBuffer sf = new StringBuffer();
	  try{
	    sf.append("('");
	    Iterator<Object>  itor = uidList.iterator();
	    while (itor.hasNext()) {
	      uid = (Long) itor.next();
	      sf.append(uid.toString());
	      if (itor.hasNext()) {
	        sf.append("', '");
	      }
	    }
	    sf.append("')");
	
	    logger.debug(query + sf.toString() + whereClause);
	  }catch(Exception ex){
		  logger.error("Exception ="+ex.getMessage(),ex);
	  }
    return query + sf.toString() + whereClause;
  }



  /**
   * This method will access the HashMap<Object,Object> of summaryList for Vaccination to be
   * papulated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Vaccination related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of VaccinationSummaryVO for the passed person UID
   */
  public HashMap<Object,Object> retrieveVaccinationSummaryListForWorkup(Long personUID,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
	  try{
		  return (getVaccinationSummaryListForWorkup(personUID, nbsSecurityObj));
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Vaccination to be
   * papulated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Vaccination related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of VaccinationSummaryVO for the passed person UID
   */
  public HashMap<Object,Object> retrieveVaccinationSummaryListForWorkup(Collection<Object> uidList,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
	  try{
		  return (getVaccinationSummaryListForWorkup(uidList, nbsSecurityObj));
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Vaccination to be
   * papulated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Vaccination related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of VaccinationSummaryVO for the passed person UID
   */
  @SuppressWarnings("unchecked")
protected HashMap<Object,Object> getVaccinationSummaryListForWorkup(Long personUID,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
    String SELECT_VACC;
    HashMap<Object,Object> vaccinationsSummaryVOColl = null;
    try {
    
	    String pk = "getInterventionUid";
	    if (personUID != null) {
	      if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
	                                        NBSOperationLookup.VIEW)) {
	        logger.debug(
	            "Permission for retrieveVaccinationSummaryListForWorkup() = " +
	            NBSBOLookup.INTERVENTIONVACCINERECORD);
	        //throw new NEDSSSystemException("no permissions to view a vaccination");
	      }
	      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	          NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);
	      logger.info("dataAccessWhereClause = " + dataAccessWhereClause);
	
	      if (dataAccessWhereClause == null) {
	        dataAccessWhereClause = "";
	      }
	      else {
	        dataAccessWhereClause = " AND " + dataAccessWhereClause;
	
	      }
	      

	        SELECT_VACC = WumSqlQuery.SELECT_VACCINATION_SUMMARY_LIST_FOR_WORKUP;

	      SELECT_VACC = SELECT_VACC + dataAccessWhereClause;
	      logger.debug("Select vacc query is: " + SELECT_VACC);
	      /**
	       * Get a collection of intervention summary vo as a map
	       */
          VaccinationSummaryVO vacSummaryVO = new VaccinationSummaryVO();
          ArrayList<Object> inputArgs = new ArrayList<Object> ();
          inputArgs.add(personUID);

      
          String aQuery = SELECT_VACC +   dataAccessWhereClause ;
          vaccinationsSummaryVOColl =
            (HashMap<Object,Object>)preparedStmtMethodForMap(vacSummaryVO, inputArgs,
                                            aQuery, NEDSSConstants.SELECT, pk);
          CachedDropDownValues cache = new CachedDropDownValues();
          TreeMap<?, ?> mapCode = cache.getCodedValuesAsTreeMap("VAC_NM");
          mapCode = cache.reverseMap(mapCode); // we can add another method that do not do reverse
          for (Iterator<Object> anIterator = vaccinationsSummaryVOColl.values().iterator(); anIterator.hasNext(); ) {
	          VaccinationSummaryVO vaccSummaryVO = (VaccinationSummaryVO) anIterator.next();
	          if (vaccSummaryVO.getVaccineNameCode() != null
	              && vaccSummaryVO.getVaccineNameCode().trim().length() != 0)
	          {
	        	  vaccSummaryVO.setVaccineAdministered(((String)mapCode.get(vaccSummaryVO.getVaccineNameCode())));
	          }
	
	      }
     
	    }
    }catch (Exception ex) {
      logger.fatal("Error while getting vaccination for a person in workup"+ex.getMessage(),ex);
      throw new NEDSSSystemException(ex.toString());
    }
    return vaccinationsSummaryVOColl;
  } //end of getObservationSummaryVOCollectionForWorkup()

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Vaccination to be
   * papulated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Vaccination related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of VaccinationSummaryVO for the passed person UID
   */
  @SuppressWarnings("unchecked")
protected HashMap<Object,Object> getVaccinationSummaryListForWorkup(Collection<Object> uidList,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
    String SELECT_VACC;
    String getPK = "getInterventionUid";
    HashMap<Object,Object> vaccinationsSummaryVOColl = null;
    try {
    
	    if (uidList != null) {
	      if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
	                                        NBSOperationLookup.VIEW)) {
	        logger.debug(
	            "Permission for retrieveVaccinationSummaryListForWorkup() = " +
	            NBSBOLookup.INTERVENTIONVACCINERECORD);
	        //throw new NEDSSSystemException("no permissions to view a vaccination");
	      }
	      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	          NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);
	      logger.info("dataAccessWhereClause = " + dataAccessWhereClause);
	
	      if (dataAccessWhereClause == null) {
	        dataAccessWhereClause = "";
	      }
	      else {
	        dataAccessWhereClause = " AND " + dataAccessWhereClause;
	
	      }

	        SELECT_VACC = WumSqlQuery.
	            SELECT_VACCINATION_SUMMARY_LIST_FOR_WORKUP_SQL_IN;
	      SELECT_VACC = this.prepareSQLForContainsIn(uidList, SELECT_VACC,
	                                                 dataAccessWhereClause);
	      logger.debug("Select vacc query is: " + SELECT_VACC);
	      /**
	       * Get a collection of intervention summary vo as a map
	       */
	
	      VaccinationSummaryVO vacSummaryVO = new VaccinationSummaryVO();
	      
          String aQuery = SELECT_VACC +   dataAccessWhereClause ;
          vaccinationsSummaryVOColl =
            (HashMap<Object, Object>)preparedStmtMethodForMap(vacSummaryVO, null, aQuery,
                                             NEDSSConstants.SELECT, getPK);
          CachedDropDownValues cache = new CachedDropDownValues();
          TreeMap<?, ?> mapCode = cache.getCodedValuesAsTreeMap("VAC_NM");
          mapCode = cache.reverseMap(mapCode); // we can add another method that do not do reverse
	          for (Iterator<Object> anIterator = vaccinationsSummaryVOColl.values().iterator(); anIterator.hasNext(); ) {
		          VaccinationSummaryVO vaccSummaryVO = (VaccinationSummaryVO) anIterator.next();
		          vaccSummaryVO.setAssociationsMap(this.getAssociatedInvList(vaccSummaryVO.getInterventionUid(), nbsSecurityObj, "INTV"));
		          ArrayList<Object> providerDetails = new ArrayList<Object>();
		          providerDetails = this.getProviderInfo(vaccSummaryVO.getInterventionUid(), "PerformerOfVacc", "INTERVENTION");
		          if (providerDetails != null && providerDetails.size() > 0 && vaccSummaryVO != null) {
		              Object[] provider = providerDetails.toArray();
		
		              if (provider[0] != null) {
		            	  vaccSummaryVO.setProviderLastName((String) provider[0]);
		                logger.debug("ProviderLastName: " + (String) provider[0]);
		              }
		              if (provider[1] != null)
		            	  vaccSummaryVO.setProviderFirstName((String) provider[1]);
		              logger.debug("ProviderFirstName: " + (String) provider[1]);
		              if (provider[2] != null)
		            	  vaccSummaryVO.setProviderPrefix((String) provider[2]);
		               logger.debug("ProviderPrefix: " + (String) provider[2]);
		              if (provider[3] != null)
		            	  vaccSummaryVO.setProviderSuffix(( String)provider[3]);
		              	logger.debug("ProviderSuffix: " + (String) provider[3]);
		
		              	if (provider[4] != null)
		              		vaccSummaryVO.setDegree(( String)provider[4]);
		                 	logger.debug("ProviderDegree: " + (String) provider[4]);
		
		            }
		          if (vaccSummaryVO.getVaccineNameCode() != null
		              && vaccSummaryVO.getVaccineNameCode().trim().length() != 0)
		          {
		            vaccSummaryVO.setVaccineAdministered(((String)mapCode.get(vaccSummaryVO.getVaccineNameCode())));
		          }
	
	        }

	    }
    }catch (Exception ex) {
      logger.fatal("Error while getting vaccination for a person in workup"+ex.getMessage(),ex);
      throw new NEDSSSystemException(ex.toString());
    }
    return vaccinationsSummaryVOColl;
  } //end of getObservationSummaryVOCollectionForWorkup()


  /**
   * This method will access the HashMap<Object,Object> of summaryList for Observation(Morb Report) to be
       * papulated on investigation page for investigation whose UID has been passed
   * @param investigationUID -- UID  for investigation to Access Observation related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @return HashMap<Object,Object> -- HashMap<Object,Object> of ObservationSummaryVO for the passed investigation UID
   */


  /**
   * This method will access the Collection<Object>  of VaccinationSummaryVO for passed investigationUID
   * @param investigationUID -- UID  for investigation to Access Vaccination related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @throws RemoteException
   * @throws EJBException
   * @throws FinderException
   * @throws CreateException
   * @return Collection<Object>  -- Collection<Object>  of VaccinationSummaryVO for the passed investigationUID
   */
  public Collection<Object>  retrieveVaccinationSummaryVOCollection(Long
      investigationUID, NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException {
    String aQuery = null;
    
    try{
	    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	        NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);
	    logger.debug(
	        "WorkupProxyEJB:retrieveVaccinationSummaryVOCollection  investigationUID = " +
	        investigationUID + " - dataAccessWhereClause = " +
	        dataAccessWhereClause);
	    if (dataAccessWhereClause == null) {
	      dataAccessWhereClause = "";
	    }
	    else {
	      dataAccessWhereClause = "AND " + dataAccessWhereClause;
	
	    }
	    aQuery = WumSqlQuery.SELECT_WORKUP_SQL + dataAccessWhereClause;

	    logger.info("aQuery = " + aQuery);
	    logger.debug("getInvestgationSummaryVO - aQuery = " + aQuery);	
    }catch(NEDSSSystemException ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    return new ArrayList<Object> ();
  } // retrieveVaccinationSummaryList

  /**
   * This method will access the HashMap<Object,Object> of VaccinationSummaryVO for passed investigationUID
   * to papulate the Vaccination summary on Investigation page
   * @param publicHealthUID -- UID  for investigation to Access Vaccination related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @throws RemoteException
   * @throws EJBException
   * @throws FinderException
   * @throws CreateException
   * @return HashMap<Object,Object> -- HashMap<Object,Object> of VaccinationSummaryVO for the passed investigationUID
   */
  @SuppressWarnings("unchecked")
public HashMap<Object,Object> retrieveVaccinationSummaryVOForInv(Long publicHealthUID,
      NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException {

    String aQuery = null;
    String getPK = "getInterventionUid";
    HashMap<Object,Object> vaccinationsSummaryVOColl = null;
    try {
	    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	        NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);
	    logger.debug(
	        "InvestigationProxyEJB:retrieveVaccinationSummaryVOForInv publicHealthUID = " +
	        publicHealthUID + " - dataAccessWhereClause = " + dataAccessWhereClause);
	    if (dataAccessWhereClause == null) {
	      dataAccessWhereClause = "";
	    }
	    else {
	      dataAccessWhereClause = "AND " + dataAccessWhereClause;
	
	    }
		aQuery = WumSqlQuery.VACCINATIONS_FOR_A_PHC + dataAccessWhereClause;

	    logger.info("aQuery = " + aQuery);
	    logger.debug("getInvestgationSummaryVO - aQuery = " + aQuery);
	    
	//    Map<Object,Object> map = null;
	    VaccinationSummaryVO vaccinationSummaryVO = new VaccinationSummaryVO();
	    

	    ArrayList<Object> inputArgs = new ArrayList<Object> ();
	      inputArgs.add(publicHealthUID);
	      vaccinationsSummaryVOColl =
	          (HashMap<Object,Object>)preparedStmtMethodForMap(vaccinationSummaryVO,
	                                          inputArgs, aQuery, NEDSSConstants.SELECT, getPK);
	
	      CachedDropDownValues cache = new CachedDropDownValues();
	      TreeMap<?, ?> mapCode = cache.getCodedValuesAsTreeMap("VAC_NM");
	      mapCode = cache.reverseMap(mapCode); // we can add another method that do not do reverse
	      for (Iterator<Object> anIterator = vaccinationsSummaryVOColl.values().iterator(); anIterator.hasNext(); ) {
	        VaccinationSummaryVO vaccSummaryVO = (VaccinationSummaryVO) anIterator.next();
	        if (vaccSummaryVO.getVaccineNameCode() != null
	            && vaccSummaryVO.getVaccineNameCode().trim().length() != 0)
	        {
	          vaccSummaryVO.setVaccineAdministered(((String)mapCode.get(vaccSummaryVO.getVaccineNameCode())));
	        }
	
	      }

    	}catch (Exception e) {
		     logger.fatal("RetrieveSummaryVO.retrieveVaccinationSummaryVOForInv Error thrown. Please check."+e.getMessage(),e);
		     throw new NEDSSSystemException(e.toString());

    	}
	    logger.debug("InvestigationProxyEJB:retrieveVaccinationSummaryVOForInv Completed InvestigationProxyEJB:retrieveVaccinationSummaryVOForInv***************");
	
	    return vaccinationsSummaryVOColl;
  } // retrieveVaccinationSummaryList

  /**
   * This method will access the HashMap<Object,Object> of VaccinationSummaryList for passed personUID
   * and investigationUID to papulate the Vaccination summary on manage vaccination page
   * @param publicHealthUID -- UID  for investigation to Access Vaccination related to it
   * @param personUID -- UID  for person to Access Observation related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @throws RemoteException
   * @throws EJBException
   * @throws FinderException
   * @throws CreateException
   * @return HashMap<Object,Object> -- HashMap<Object,Object> of VaccinationSummaryVO for the passed investigationUID
   */

  public HashMap<Object,Object> retrieveVaccinationSummaryListForManage(Long personUID,
      Long publicHealthUID, NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException,
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      javax.ejb.FinderException,
      javax.ejb.CreateException {
    boolean viewVaccPermission = nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
                                      NBSOperationLookup.VIEW,
                                      ProgramAreaJurisdictionUtil.
                                      ANY_PROGRAM_AREA,
                                      ProgramAreaJurisdictionUtil.
                                      ANY_JURISDICTION) ;
    HashMap<Object,Object> mergedCollection  = new HashMap<Object,Object>();
    try {
		 if(viewVaccPermission){
		    logger.info(
		        "inside retrieveVaccinationSummaryListForManage:colInvestigation ");
		    HashMap<Object,Object> colPerson = getVaccinationSummaryListForWorkup(personUID,
		        nbsSecurityObj);
		    logger.debug("getVaccinationSummaryListForWorkup in manage: " + colPerson +
		                 "size  " + colPerson.size());
		    logger.info("retrieveVaccinationSummaryListForManage:colPerson ");
		    HashMap<Object,Object> colInvestigation = retrieveVaccinationSummaryVOForInv(
		        publicHealthUID, nbsSecurityObj);
		    logger.debug("retrieveVaccinationSummaryVOForInv in manage: " +
		                 colInvestigation + "size= " + colInvestigation.size());
		    logger.info("retrieveVaccinationSummaryListForManage: " + colInvestigation);
		    
	    
	
	      // popluate treeMap.  the key is from interventionUID()
		    for (Iterator<Object> anIterator = colInvestigation.values().iterator();
	           anIterator.hasNext(); ) {
		        VaccinationSummaryVO vacVO = (VaccinationSummaryVO) anIterator.next();
		        vacVO.setYesNoFlag("first");
		        vacVO.setIsAssociated(true);
		        mergedCollection.put(vacVO.getInterventionUid(), vacVO);
		        logger.debug("mergedCollection  in manage with Investigation: " +
		                     mergedCollection  + "size= " + mergedCollection.size());
		      }
		      // iterate over first collection and see if interventionUID()s map
		      for (Iterator<Object> anIterator = colPerson.values().iterator();
		           anIterator.hasNext(); ) {
		        logger.debug("size of colPerson  " + colPerson.size());
		        VaccinationSummaryVO vacVO = (VaccinationSummaryVO) anIterator.next();
		        if (!mergedCollection.containsKey(vacVO.getInterventionUid())) {
		          vacVO.setYesNoFlag("second");
		          vacVO.setIsAssociated(false);
		          mergedCollection.put(vacVO.getInterventionUid(), vacVO);
		          logger.debug(
		              "mergedCollection  in manage with Investigation and size= " +
		              mergedCollection.size());
		        }
		      }
	    
	    }
    }catch (Exception ex) {
      logger.fatal("retrieveVaccinationSummaryListForObservation: "+ex.getMessage(), ex);
    }
    logger.debug("returned value of mergedCollection  " + mergedCollection  +
                 "size= " + mergedCollection.size());
    return (mergedCollection);

  }

  /**
       * This method will access the Collection<Object>  of NotificationSummaryList for passed
   * and investigationUID to papulate the Notification summary on manage Notification page
   * @param PublicHealthCaseDT -- PublicHealthCaseDT to Access Notifications related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @return Collection<Object>  -- Collection<Object>  of NotificationSummaryVO for the passed publicHealthCaseDT
   */
  public Collection<Object>  retrieveAutoResendSummaries(String sourceClassCd,String typeCd,
                                                Long sourceUid) throws
      NEDSSSystemException {

    ArrayList<Object>  theNotificationSummaryVOCollection  = new ArrayList<Object> ();
    String statement;
   statement = WumSqlQuery.SELECT_NOTIFICATION_AUTO_RESEND_SQL;
    
    logger.debug("statement = " + statement);

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();
    NotificationSummaryVO notifVO = new NotificationSummaryVO();
    ArrayList<Object> retval = new ArrayList<Object> ();
    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(statement);
      preparedStmt.setString(1, sourceClassCd);
      preparedStmt.setString(2, typeCd);
      preparedStmt.setObject(3, sourceUid);
      logger.info("preparedStmt = " + statement);
      resultSet = preparedStmt.executeQuery();
      while (resultSet.next()) {
        notifVO = new NotificationSummaryVO();
        notifVO.setNotificationUid(new Long(resultSet.getLong("NotificationUid")));
        notifVO.setCd(resultSet.getString("Cd"));
        notifVO.setCaseClassCd(resultSet.getString("CaseClassCd"));
        notifVO.setProgAreaCd(resultSet.getString("ProgAreaCd"));
        notifVO.setSharedInd(resultSet.getString("SharedInd"));
        notifVO.setJurisdictionCd(resultSet.getString("JurisdictionCd"));
        notifVO.setAutoResendInd(resultSet.getString("AutoResendInd"));
        notifVO.setItNew(false);
        notifVO.setItDirty(false);
        retval.add(notifVO);
      }
      logger.debug("get resultSet " + resultSet.toString());
      resultSetMetaData = resultSet.getMetaData();

    }
    catch (SQLException se) {
      logger.fatal("Error: SQLException while selecting \n"+se.getMessage(), se);
      throw new NEDSSSystemException(se.getMessage());
    }
    //catch(ResultSetUtilsException rsuex)
    catch (Exception rsuex) {
      logger.fatal(
          "Error in result set handling while populate NotificationSummaryVO."+rsuex.getMessage(),
          rsuex);
      throw new NEDSSSystemException(rsuex.toString());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return retval;
    
  } 

  /**
       * This method will access the Collection<Object>  of NotificationSummaryList for passed
   * and investigationUID to papulate the Notification summary on manage Notification page
   * @param PublicHealthCaseDT -- PublicHealthCaseDT to Access Notifications related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @return Collection<Object>  -- Collection<Object>  of NotificationSummaryVO for the passed publicHealthCaseDT
   */
  public Collection<Object>  retrieveNotificationSummaryListForManage(PublicHealthCaseDT
      publicHealthCaseDT, NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException {
    Long publicHealthUID = publicHealthCaseDT.getPublicHealthCaseUid();
    ArrayList<Object> theNotificationSummaryVOCollection  = new ArrayList<Object> ();
    //R3.0 AggregateSummary Changes - PHC for Summary dont have Jurisdiction, so bypass securityCheck Conditionally
    if (publicHealthUID != null) {
    	String caseTypeCd = publicHealthCaseDT.getCaseTypeCd();
    	if(caseTypeCd != null && !caseTypeCd.equalsIgnoreCase(NEDSSConstants.CASETYPECD_AGGREGATE_SUMMARY)) {	
	      if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	                                        NBSOperationLookup.VIEW,
	                                        publicHealthCaseDT.getProgAreaCd(),
	                                        publicHealthCaseDT.getJurisdictionCd(),
	                                        publicHealthCaseDT.getSharedInd())) {
	    	  logger.info("INVESTIGATION = " + NBSBOLookup.INVESTIGATION + ",  VIEW = " + NBSOperationLookup.VIEW);
	    	  throw new NEDSSSystemException("no permissions to VIEW a notification");
	      }
      }
      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
          NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW,
          DataTables.NOTIFICATION_TABLE);
      logger.info("Notification dataAccessWhereClause = " + dataAccessWhereClause);

      if (dataAccessWhereClause == null) {
        dataAccessWhereClause = "";
      }
      else {
        dataAccessWhereClause = " AND " + dataAccessWhereClause;

      }
      String statement;

      //For AggregateSummary dont set any dataAccessWhereClause
      if(caseTypeCd != null && caseTypeCd.equalsIgnoreCase(NEDSSConstants.CASETYPECD_AGGREGATE_SUMMARY)) {
         statement = WumSqlQuery.SELECT_SUMMARY_NOTIFICATION_SQL;
      } else {
	            statement = WumSqlQuery.SELECT_NOTIFICATION_SQL + dataAccessWhereClause;

      }
      
      
      logger.debug("Notification statement = " + statement);

      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      ResultSet resultSet = null;
      ResultSetMetaData resultSetMetaData = null;
      ResultSetUtils resultSetUtils = new ResultSetUtils();
      NotificationSummaryVO notifVO = new NotificationSummaryVO();
      try {
        dbConnection = getConnection();
        preparedStmt = dbConnection.prepareStatement(statement);
        logger.info("publicHealthUID.longValue() = " + publicHealthUID.longValue());
        preparedStmt.setLong(1, publicHealthUID.longValue());
        logger.info("preparedStmt = " + preparedStmt.toString());
        resultSet = preparedStmt.executeQuery();
        logger.debug("get resultSet " + resultSet.toString());
        resultSetMetaData = resultSet.getMetaData();
        ArrayList<Object> retval = new ArrayList<Object> ();
        retval = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
            resultSetMetaData, notifVO.getClass(), retval);
        CachedDropDownValues cache = new CachedDropDownValues();
        for (Iterator<Object> anIterator = retval.iterator(); anIterator.hasNext(); ) {
          NotificationSummaryVO newVO = (NotificationSummaryVO) anIterator.next();
          if (newVO.getCaseClassCd() != null
              && newVO.getCaseClassCd().trim().length() != 0)
          {
            TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("PHC_CLASS");
            map = cache.reverseMap(map); // we can add another method that do not do reverse
            newVO.setCaseClassCdTxt((String)map.get(newVO.getCaseClassCd()));
          }
          if (newVO.getCd() != null
              && newVO.getCd().trim().length() != 0)
          {
            TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("PHC_TYPE");
            map = cache.reverseMap(map); // we can add another method that do not do reverse
            newVO.setCdTxt((String)map.get(newVO.getCd()));
          }

          newVO.setItNew(false);
          newVO.setItDirty(false);
          logger.debug("NotificationSummaryVO:" + newVO.toString());
          theNotificationSummaryVOCollection.add(newVO);
        }
      }
      catch (SQLException se) {
        logger.fatal("Error: SQLException while selecting \n"+se.getMessage(), se);
        throw new NEDSSSystemException(se.getMessage());
      }
      catch (ResultSetUtilsException rsuex) {
        logger.fatal("Error in result set handling while populate RoleDTs."+rsuex.getMessage(),
                     rsuex);
        throw new NEDSSSystemException(rsuex.toString());
      }
      finally {
        closeResultSet(resultSet);
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
      }
    }
    return theNotificationSummaryVOCollection;
  } //retrieveNotificationSummaryListForManage

  /**
       * This method will access the Collection<Object>  of NotificationSummaryList for passed
       * investigationUID to papulate the Notification summary on investigation page
   * @param PublicHealthCaseDT -- PublicHealthCaseDT to Access Notifications related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @return Collection<Object>  -- Collection<Object>  of NotificationSummaryVO for the passed publicHealthCaseDT
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  retrieveNotificationSummaryListForInvestigation(Long
      publicHealthUID, NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException {
    ArrayList<Object> theNotificationSummaryVOCollection  = new ArrayList<Object> ();
    if (publicHealthUID != null) {
      if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                        NBSOperationLookup.VIEW)) {
        logger.info("INVESTIGATION = " + NBSBOLookup.INVESTIGATION +
                    ",  VIEW = " + NBSOperationLookup.VIEW);
        throw new NEDSSSystemException("no permissions to VIEW a notification");
      }
      
      String statement[] = new String[2];

  
        statement[0] = WumSqlQuery.SELECT_NOTIFICATION_FOR_INVESTIGATION_SQL;
        statement[1] = WumSqlQuery.
            SELECT_NOTIFICATION_HIST_FOR_INVESTIGATION_SQL +" ORDER BY notHist.version_ctrl_nbr DESC";
     
      logger.info("statement0 = " + statement[0]);
      logger.info("statement1 = " + statement[1]);

      NotificationSummaryVO notifVO = new NotificationSummaryVO();
      try
      {
        for (int i = 0; i < statement.length; i++) {
          logger.info("publicHealthUID.longValue() = " +
                      publicHealthUID.longValue());
          ArrayList<Object> inputArg = new ArrayList<Object> ();
          inputArg.add(publicHealthUID);
          ArrayList<Object> retval = new ArrayList<Object> ();
          retval = (ArrayList<Object> )preparedStmtMethod(notifVO, inputArg, statement[i], NEDSSConstants.SELECT);

          //break if there is no existing Notification
          if (retval.size() == 0) {
            break;
          }
          CachedDropDownValues cache = new CachedDropDownValues();
          for (Iterator<Object> anIterator = retval.iterator(); anIterator.hasNext(); ) {
            NotificationSummaryVO newVO = (NotificationSummaryVO) anIterator.
                next();
            if (newVO.getCaseClassCd() != null
                && newVO.getCaseClassCd().trim().length() != 0)
            {
              TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("PHC_CLASS");
              map = cache.reverseMap(map); // we can add another method that do not do reverse
              newVO.setCaseClassCdTxt((String)map.get(newVO.getCaseClassCd()));
            }
            if (newVO.getCd() != null
                && newVO.getCd().trim().length() != 0)
            {
              TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("PHC_TYPE");
              map = cache.reverseMap(map); // we can add another method that do not do reverse
              newVO.setCdTxt((String)map.get(newVO.getCd()));
            }
            
            if (newVO.getCdNotif() != null
                    && newVO.getCdNotif().trim().length() != 0)
                {
            	newVO.setCdNotif(newVO.getCdNotif());
            	newVO.setNotificationSrtDescCd(CachedDropDowns.getCodeDescTxtForCd(newVO.getCdNotif(),"NBS_DOC_PURPOSE"));
       	      }
            if (newVO.getRecipient() != null
                    && newVO.getRecipient().trim().length() != 0)
                {
         	   	  newVO.setRecipient(newVO.getRecipient());
               }
            else if(newVO.getRecipient()== null){
            	if(newVO.getNndInd()!=null && newVO.getNndInd().equals(NEDSSConstants.YES))
            		newVO.setRecipient(NEDSSConstants.ADMINFLAGCDC);
            	else
            		newVO.setRecipient(NEDSSConstants.LOCAl_DESC);
            }
             
            if (!newVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_NOTF))
            	newVO.setCaseReport(true);
   
            newVO.setItNew(false);
            newVO.setItDirty(false);
            logger.debug("NotificationSummaryVO:" + newVO.toString());
            theNotificationSummaryVOCollection.add(newVO);
          }
        }
      }
      catch (Exception e) {
       logger.fatal("Error: while selecting \n"+e.getMessage(), e);
       throw new NEDSSSystemException(e.getMessage());
     }

    }

    return theNotificationSummaryVOCollection;
  } //retrieveNotificationSummaryListForInvestigationo

  /**
       * This method will access the Collection<Object>  of NotificationSummaryList for passed
       * investigationUID to papulate the Notification summary on investigation page
   * @param PublicHealthCaseDT -- PublicHealthCaseDT to Access Notifications related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @return Collection<Object>  -- Collection<Object>  of NotificationSummaryVO for the passed publicHealthCaseDT
   */

  @SuppressWarnings("unchecked")
public Collection<Object>  retrieveNotificationSummaryListForInvestigation1(Long
      publicHealthUID, NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException {
    ArrayList<Object> theNotificationSummaryVOCollection  = new ArrayList<Object> ();
    try {
	    if (publicHealthUID != null) {
	      if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	                                        NBSOperationLookup.VIEW)) {
	        logger.info("INVESTIGATION = " + NBSBOLookup.INVESTIGATION +
	                    ",  VIEW = " + NBSOperationLookup.VIEW);
	        throw new NEDSSSystemException("no permissions to VIEW a notification");
	      }
	      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	          NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW,
	          DataTables.NOTIFICATION_TABLE);
	      logger.info("dataAccessWhereClause = " + dataAccessWhereClause);
	
	      if (dataAccessWhereClause == null) {
	        dataAccessWhereClause = "";
	      }
	      else {
	        dataAccessWhereClause = " AND " + dataAccessWhereClause;
	
	      }
	      String statement[] = new String[2];
	
 
	        statement[0] = WumSqlQuery.SELECT_NOTIFICATION_FOR_INVESTIGATION_SQL1 +
	            dataAccessWhereClause;
	        statement[1] = WumSqlQuery.SELECT_NOTIFICATION_HIST_FOR_INVESTIGATION_SQL1 +
	            dataAccessWhereClause + " ORDER BY notHist.version_ctrl_nbr DESC";
 
	      logger.debug("statement = " + statement);
	      NotificationSummaryVO notifVO = new NotificationSummaryVO();
	      CachedDropDownValues cache = new CachedDropDownValues();
	      TreeMap<?, ?> mapPhcClass = cache.getCodedValuesAsTreeMap("PHC_CLASS");
	      mapPhcClass = cache.reverseMap(mapPhcClass); // we can add another method that does not do reverse
	      TreeMap<?, ?> mapPhcType = cache.getCodedValuesAsTreeMap("PHC_TYPE");
	      mapPhcType = cache.reverseMap(mapPhcType); // we can add another method that do not do reverse

      
	        for (int i = 0; i < statement.length; i++) {
	          logger.info("publicHealthUID.longValue() = " +
	                      publicHealthUID.longValue());
	          ArrayList<Object> inputArg = new ArrayList<Object> ();
	          inputArg.add(publicHealthUID);
	          ArrayList<Object> retval = new ArrayList<Object> ();
	          retval = (ArrayList<Object> )preparedStmtMethod(notifVO, inputArg, statement[i], NEDSSConstants.SELECT);
	
	          //break out of loop if there is no existing Notification
	          if (retval.size() == 0) {
	            break;
	          }
	          for (Iterator<Object> anIterator = retval.iterator(); anIterator.hasNext(); ) {
	            NotificationSummaryVO newVO = (NotificationSummaryVO) anIterator.
	                next();
	            if (newVO.getCaseClassCd() != null
	                && newVO.getCaseClassCd().trim().length() != 0)
	            {
	              newVO.setCaseClassCdTxt((String)mapPhcClass.get(newVO.getCaseClassCd()));
	            }
	            if (newVO.getCd() != null
	                && newVO.getCd().trim().length() != 0)
	            {
	              newVO.setCdTxt((String)mapPhcType.get(newVO.getCd()));
	            }
	            
	            if (newVO.getCdNotif() != null
	                    && newVO.getCdNotif().trim().length() != 0)
	                {
	            	newVO.setCdNotif(newVO.getCdNotif());
	            	newVO.setNotificationSrtDescCd(CachedDropDowns.getCodeDescTxtForCd(newVO.getCdNotif(),"NBS_DOC_PURPOSE"));
	       	      }
	            if (newVO.getRecipient() != null
	                    && newVO.getRecipient().trim().length() != 0)
	                {
	         	   	  newVO.setRecipient(newVO.getRecipient());
	               }
	            else{
	            	if(newVO.getNndInd()!=null && newVO.getNndInd().equals(NEDSSConstants.YES))
	            		newVO.setRecipient(NEDSSConstants.ADMINFLAGCDC);
	            	else
	            		newVO.setRecipient(NEDSSConstants.LOCAl_DESC);
	            }
	             
	            if (!newVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_NOTF))
	            	newVO.setCaseReport(true);
	            
	            newVO.setItNew(false);
	            newVO.setItDirty(false);
	            logger.debug("NotificationSummaryVO:" + newVO.toString());
	            theNotificationSummaryVOCollection.add(newVO);
	          }
	        }
      
	    }
    }catch (Exception e) {
      logger.fatal("Error: while selecting \n"+e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage());
    }
    return theNotificationSummaryVOCollection;
  }

  /*
   * retrieveObservationQuestionAnswer
       * This function will return all observations related to questions in the form.
   * @param targeActUid -- target ActUID Access Observation Questions related to main observation
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @return Collection<Object>  -- Collection<Object>  of Observation Question for the passed targeActUid
   */

  public Collection<ObservationVO>  retrieveObservationQuestions(Long targeActUid,
                                                 NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException {

    ArrayList<ObservationVO> theObservationQuestionColl = new ArrayList<ObservationVO> ();
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();
    String sql = null;
    sql = statement_sql;
    
    logger.debug("statement = " + sql);
    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(sql);
      preparedStmt.setLong(1, targeActUid.longValue());
      resultSet = preparedStmt.executeQuery();
      resultSetMetaData = resultSet.getMetaData();
      ArrayList<Object> retval = new ArrayList<Object> ();
      retval = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
          resultSetMetaData, ObservationQA.class, retval);
      Long previousTargetActUid = null;
      Long previousObservationUid = null;
      ObservationVO obsVO = null;
      ArrayList<Object> obsCodes = null;
      ArrayList<Object> obsDates = null;
      ArrayList<Object> obsNumerics = null;
      ArrayList<Object> obsValueTxts = null;
      for (Iterator<Object> anIterator = retval.iterator(); anIterator.hasNext(); ) {
        ObservationQA obsQA = (ObservationQA) anIterator.next();

        // //##!! System.out.println("previousTargetActUid = " +  previousTargetActUid);
        //   //##!! System.out.println("crrent TargetActUid = " +  obsQA.getTargetActUid());
        if (previousTargetActUid == null ||
            !previousTargetActUid.equals(obsQA.getTargetActUid())) {
          if (previousObservationUid == null ||
              !previousObservationUid.equals(obsQA.getObservationUid())) {
            obsVO = new ObservationVO();
            theObservationQuestionColl.add(obsVO);
            //Initialize the Collections for this new Observation
            obsCodes = null;
            obsDates = null;
            obsNumerics = null;
            obsValueTxts = null;
            logger.debug("Mark - CREATED NEW OBSERVATION Uid=" +
                         obsQA.getObservationUid());
            previousObservationUid = obsQA.getObservationUid();
            // put ObeservatioDT
            obsVO.getTheObservationDT().setObservationUid(obsQA.
                getObservationUid());
            obsVO.getTheObservationDT().setVersionCtrlNbr(obsQA.
                getVersionCtrlNbr());
            obsVO.getTheObservationDT().setSharedInd(obsQA.getSharedInd());
            obsVO.getTheObservationDT().setCd(obsQA.getCd());
            obsVO.getTheObservationDT().setCtrlCdDisplayForm(obsQA.
                getCtrlCdDisplayForm());
            obsVO.getTheObservationDT().setLocalId(obsQA.getLocalId());
            obsVO.getTheObservationDT().setCdDescTxt(obsQA.getCdDescTxt());
            obsVO.getTheObservationDT().setCdSystemDescTxt(obsQA.
                getCdSystemDescTxt());
            obsVO.getTheObservationDT().setCdSystemCd(obsQA.getCdSystemCd());
            obsVO.getTheObservationDT().setCdVersion(obsQA.getCdVersion());
            obsVO.getTheObservationDT().setItNew(false);
            obsVO.getTheObservationDT().setItDirty(false);
            // ObsValueCode
          }
          if (obsQA.getObsCodeUid() != null) {
            if (obsCodes == null) {
              obsCodes = new ArrayList<Object> ();
              obsVO.setTheObsValueCodedDTCollection(obsCodes);
            }
            ObsValueCodedDT obsCode = new ObsValueCodedDT();
            obsCode.setObservationUid(obsQA.getObsCodeUid());
            obsCode.setCode(obsQA.getCode());
            obsCode.setCodeSystemDescTxt(obsQA.getCodeSystemDescTxt());
            obsCode.setOriginalTxt(obsQA.getOriginalTxt());
            obsCode.setItNew(false);
            obsCode.setItDirty(false);
            obsCodes.add(obsCode);
            //obsVO.setTheObsValueCodedDTCollection(obsCodes);
          }
          // ObsvalueDate
          if (obsQA.getObsDateUid() != null)

          {
            if (obsDates == null) {
              obsDates = new ArrayList<Object> ();
              obsVO.setTheObsValueDateDTCollection(obsDates);
            }
            //ArrayList<Object> obsDates = new ArrayList<Object> ();
            ObsValueDateDT obsDate = new ObsValueDateDT();
            obsDate.setObservationUid(obsQA.getObsDateUid());
            obsDate.setFromTime(obsQA.getFromTime());
            obsDate.setToTime(obsQA.getToTime());
            obsDate.setDurationAmt(obsQA.getDurationAmt());
            obsDate.setDurationUnitCd(obsQA.getDurationUnitCd());
            obsDate.setObsValueDateSeq(obsQA.getObsValueDateSeq());
            obsDate.setItNew(false);
            obsDate.setItDirty(false);
            obsDates.add(obsDate);

            //obsVO.setTheObsValueDateDTCollection(obsDates);
          }
          // ObsvalueNumeric
          if (obsQA.getObsNumericUid() != null) {
            if (obsNumerics == null) {
              obsNumerics = new ArrayList<Object> ();
              obsVO.setTheObsValueNumericDTCollection(obsNumerics);
            }
            //ArrayList<Object> obsNumerics = new ArrayList<Object> ();
            ObsValueNumericDT obsNumeric = new ObsValueNumericDT();
            obsNumeric.setObservationUid(obsQA.getObsNumericUid());
            obsNumeric.setNumericScale1(obsQA.getNumericScale1());
            obsNumeric.setNumericScale2(obsQA.getNumericScale2());
            obsNumeric.setNumericValue1(obsQA.getNumericValue1());
            obsNumeric.setNumericValue2(obsQA.getNumericValue2());
            obsNumeric.setNumericUnitCd(obsQA.getNumericUnitCd());
            obsNumeric.setObsValueNumericSeq(obsQA.getObsValueNumericSeq());
            obsNumeric.setItNew(false);
            obsNumeric.setItDirty(false);
            obsNumerics.add(obsNumeric);
            // obsVO.setTheObsValueNumericDTCollection(obsNumerics);
          }
          // ObsvalueTxt
          if (obsQA.getObsTxtUid() != null) {
            if (obsValueTxts == null) {
              obsValueTxts = new ArrayList<Object> ();
              obsVO.setTheObsValueTxtDTCollection(obsValueTxts);
            }
            //ArrayList<Object> obsValueTxts = new ArrayList<Object> ();
            ObsValueTxtDT obsValueTxt = new ObsValueTxtDT();
            obsValueTxt.setObservationUid(obsQA.getObsTxtUid());
            obsValueTxt.setValueTxt(obsQA.getValueTxt());
            obsValueTxt.setObsValueTxtSeq(obsQA.getObsValueTxtSeq());
            obsValueTxt.setItNew(false);
            obsValueTxt.setItDirty(false);
            obsValueTxts.add(obsValueTxt);
            //  obsVO.setTheObsValueTxtDTCollection(obsValueTxts);
          }

          previousTargetActUid = obsQA.getTargetActUid();
          if (previousTargetActUid != null &&
              previousTargetActUid.equals(obsQA.getObservationUid())) {
            //       //##!! System.out.println("First time both are equal");
            Collection<Object>  actColl = new ArrayList<Object> ();
            ActRelationshipDT ar = new ActRelationshipDT();
            ar.setSourceActUid(obsQA.getSourceActUid());
            ar.setTargetActUid(obsQA.getTargetActUid());
            ar.setTypeCd(obsQA.getTypeCd());
            ar.setItDirty(false);
            actColl.add(ar);
            obsVO.setTheActRelationshipDTCollection(actColl);
          }
        }
        else {
          ObservationVO innerObs = (ObservationVO) theObservationQuestionColl.
              get(theObservationQuestionColl.size() - 1);
          Collection<Object>  actColl;
          if ( (actColl = innerObs.getTheActRelationshipDTCollection()) == null) {
            actColl = new ArrayList<Object> ();
          }
          ActRelationshipDT ar = new ActRelationshipDT();
          ar.setSourceActUid(obsQA.getSourceActUid());
          ar.setTargetActUid(obsQA.getTargetActUid());
          ar.setTypeCd(obsQA.getTypeCd());
          ar.setRecordStatusCd(NEDSSConstants.ACTIVE);
          ar.setItDirty(false);
          actColl.add(ar);
          innerObs.setTheActRelationshipDTCollection(actColl);
          theObservationQuestionColl.set(theObservationQuestionColl.size() - 1,
                                         innerObs);
        }

      }
      logger.debug("RetrieveSummaryVO");
      VOTester.createReport(theObservationQuestionColl,
                            "RetrievSummaryVO-obsColl");

      return theObservationQuestionColl;

    }
    catch (Exception e) {
      logger.fatal("Error in getting observation questions. \n"+e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }

  }

  /**
   * This method will access the HashMap<Object,Object> of TreatmentSummaryList for passed personUID
   * and investigationUID to papulate the Treatment summary on manage treatment page
   * @param publicHealthUID -- UID  for investigation to Access Vaccination related to it
   * @param personUID -- UID  for person to Access Observation related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @throws RemoteException
   * @throws EJBException
   * @throws FinderException
   * @throws CreateException
   * @return HashMap<Object,Object> -- HashMap<Object,Object> of TreatmentSummaryVO for the passed investigationUID
   */

  public ArrayList<Object> retrieveTreatmentSummaryListForManage(Long personUID,
      Long publicHealthUID, Collection<Object>  docSumColl, NBSSecurityObj nbsSecurityObj) throws
                                                NEDSSSystemException,
                                                java.rmi.RemoteException,
                                                javax.ejb.EJBException,
                                                javax.ejb.FinderException,
                                                javax.ejb.CreateException {
    boolean viewTreatPermission = nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
                                      NBSOperationLookup.VIEW,
                                      ProgramAreaJurisdictionUtil.
                                      ANY_PROGRAM_AREA,
                                      ProgramAreaJurisdictionUtil.
                                      ANY_JURISDICTION);
      logger.debug("TREATMENT = " + NBSBOLookup.TREATMENT + ",  VIEW = " +
                   NBSOperationLookup.VIEW);
      //throw new NEDSSSystemException("no permissions to VIEW a treatment");
    ArrayList<Object> treatmentSummCollForPerson = new ArrayList<Object>();
    try{
	    if(viewTreatPermission){
		    logger.info("inside retrieveTreatmentSummaryListForManage:colInvestigation ");
		    treatmentSummCollForPerson = getTreatmentSummaryListForWorkup(personUID, nbsSecurityObj);
		    // Add Treatments from associated PHDC documents
		    CDAEventSummaryParser esp = new CDAEventSummaryParser();
		    treatmentSummCollForPerson.addAll(esp.getTreatmentMapByLocalId(docSumColl, nbsSecurityObj).values());
		    //System.out.println("treatmentSummCollForPerson size is :" + treatmentSummCollForPerson.size());
		    ArrayList<Object> collInvestigation = retrieveTreatmentSummaryVOForInvForManage(publicHealthUID, nbsSecurityObj);
		    logger.debug("retrieveTreatmentSummaryVOForInvForManage in manage: " + collInvestigation + "size= " + collInvestigation.size());
		    HashMap<Object,Object> collMorb = retrieveTreatmentDetailsForMorbForManage(publicHealthUID, nbsSecurityObj);
		    // 04/15/2011 - Don't show the treatments separately created via mob reports
		    logger.debug("retrieveTreatmentSummaryVOForInvForManage in manage: " + collMorb + "size= " + collMorb.size());
		    StringBuffer inClause = new StringBuffer("");
		    if(treatmentSummCollForPerson!= null && treatmentSummCollForPerson.size()>0)
		    {
			     inClause.append("( ");
			     Iterator<Object>  it  = treatmentSummCollForPerson.iterator();
			     while(it.hasNext())
			     {
			       TreatmentSummaryVO treatSumVO  = (TreatmentSummaryVO)it.next();
			       if(it.hasNext())
			       {
			         inClause.append(treatSumVO.getTreatmentUid()).append(", ");
			       }
			       else
			       {
			         inClause.append(treatSumVO.getTreatmentUid());
			       }
			     }
			     inClause.append(" )");
		    }
		     HashMap<Object,Object> linkedMorb = new HashMap<Object,Object>();
		     if(treatmentSummCollForPerson.size()>0)
		     {
		       linkedMorb = retrieveTreatmentAndObservationMorbForManage(inClause.toString(), nbsSecurityObj);
		     }
		     logger.debug("retrieveTreatmentSummaryVOForInvForManage in manage: " + collMorb + "size= " + collMorb.size());
		
		     if(treatmentSummCollForPerson.size()>0)
		     {
		    	 linkedMorb = retrieveTreatmentAndObservationMorbForManage(inClause.toString(), nbsSecurityObj);
		     }
		     logger.debug("retrieveTreatmentSummaryVOForInvForManage in manage: " + collMorb + "size= " + collMorb.size());
		
		
		     logger.info("retrieveTreatmentSummaryListForManage: " + collInvestigation);
		     ArrayList<Object> treatmentViaMorb = new ArrayList<Object>();
		      // iterate over first collection and see if treatmentUID()s map
		     if( treatmentSummCollForPerson!= null)
		     {
		      for (Iterator<Object> anIterator = treatmentSummCollForPerson.iterator(); anIterator.hasNext(); ) {
		        //System.out.println("size of colPerson  " + treatmentSummCollForPerson.size());
		        TreatmentSummaryVO treatVO = (TreatmentSummaryVO) anIterator.next();
		        if (collInvestigation.contains(treatVO.getTreatmentUid())) {
		          treatVO.setIsAssociated(true);
		        }
		        else if (collMorb.get(treatVO.getTreatmentUid())!= null) {
		        	treatmentViaMorb.add(treatVO);
		        }
		        else if(linkedMorb.get(treatVO.getTreatmentUid())!= null)
		        	treatmentViaMorb.add(treatVO);
		        if(treatVO.getArTypeCd()!= null)
		        {
		          CachedDropDownValues cached = new CachedDropDownValues();
		          String translatedCondition = cached.getCachedConditionCodeList(treatVO.getArTypeCd());
		          treatVO.setArTypeCd(translatedCondition);
		        }
		      }
		     }
		    treatmentSummCollForPerson.removeAll(treatmentViaMorb);
	    }
    }catch(Exception ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    return treatmentSummCollForPerson;
  }

  /**
   * This method will access the HashMap<Object,Object> of TreatmentSummaryVO for passed investigationUID
   * to papulate the Treatment summary on Investigation page
   * @param publicHealthUID -- UID  for investigation to Access Treatment related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
   * @throws RemoteException
   * @throws EJBException
   * @throws FinderException
   * @throws CreateException
   * @return HashMap<Object,Object> -- HashMap<Object,Object> of TreatmentSummaryVO for the passed investigationUID
   */
  public HashMap<Object,Object> retrieveTreatmentSummaryVOForInv(Long publicHealthUID,
                                                  NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException {
    String aQuery = null;

    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW, "Treatment");



	logger.debug(
        "InvestigationProxyEJB:retrieveTreatmentSummaryVOForInv publicHealthUID = " +
        publicHealthUID + " - dataAccessWhereClause = " + dataAccessWhereClause);

	if (dataAccessWhereClause == null) {
      dataAccessWhereClause = "";
    }
    else {
      dataAccessWhereClause = "AND " + dataAccessWhereClause;

    }
    
    aQuery = WumSqlQuery.TREATMENTS_FOR_A_PHC_ORACLE + dataAccessWhereClause;
    logger.info("aQuery = " + aQuery);
    logger.debug("getInvestgationSummaryVO - aQuery = " + aQuery);
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();
    HashMap<Object,Object> treatmentsSummaryVOHashMap = new HashMap<Object,Object>();
    //TreeMap<Object, Object> treatmentsSummaryVOTreeMap = new TreeMap<Object, Object>();
    Map<Object,Object> map = null;
    TreatmentSummaryVO treatmentSummaryVO = new TreatmentSummaryVO();
    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(aQuery);
      preparedStmt.setLong(1, publicHealthUID.longValue());
      resultSet = preparedStmt.executeQuery();
      resultSetMetaData = resultSet.getMetaData();
      treatmentsSummaryVOHashMap = (HashMap<Object,Object>) resultSetUtils.mapRsToBeanMap(
          resultSet, resultSetMetaData, treatmentSummaryVO.getClass(),
          "getTreatmentUid", map);
      NbsDocumentDAOImpl nbsDAO = new NbsDocumentDAOImpl();
      Map<String, EDXEventProcessDT> edxEventsMap = nbsDAO.getEDXEventProcessMapByCaseId(publicHealthUID);
      CDAEventSummaryParser cdaParser = new CDAEventSummaryParser();
      if(treatmentsSummaryVOHashMap==null)
    	  treatmentsSummaryVOHashMap = new HashMap<Object, Object>();
      treatmentsSummaryVOHashMap.putAll(cdaParser.getTreatmentMapByPHCUid(edxEventsMap, nbsSecurityObj));
    }
    

    catch (Exception ex) {
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    logger.debug("InvestigationProxyEJB:retrieveTreatmentSummaryVOForInv Completed InvestigationProxyEJB:retrieveTreatmentSummaryVOForInv***************");

    return treatmentsSummaryVOHashMap;
  } // retrieveTreatmentSummaryList




  @SuppressWarnings("unchecked")
public ArrayList<Object> retrieveTreatmentSummaryVOForInvForManage(Long publicHealthUID,
                                                  NBSSecurityObj nbsSecurityObj) throws
                                                  java.rmi.RemoteException,
                                                  javax.ejb.EJBException,
                                                  NEDSSSystemException,
                                                  javax.ejb.FinderException,
                                                  javax.ejb.CreateException {
    String aQuery = null;
    ArrayList<Object> returnList = new ArrayList<Object> ();
    try{
	    aQuery = "SELECT source_act_uid uid FROM Act_relationship  with (nolock) " +
	          " WHERE record_status_cd = 'ACTIVE' " +
	          " AND target_act_uid = " + publicHealthUID +
	          " AND source_class_cd = 'TRMT' " +
	          " AND target_class_cd = 'CASE' and type_cd = 'TreatmentToPHC'";
	

	
	    UidSummaryVO summaryVO = new UidSummaryVO();
	    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(summaryVO, null, aQuery, NEDSSConstants.SELECT);
	
	    
	    if(list.size()>0)
	    {
	     Iterator<Object>  it = list.iterator();
	      while(it.hasNext())
	      {
	        UidSummaryVO summVO = (UidSummaryVO)it.next();
	        returnList.add(summVO.getUid());
	      }
	    }
    }catch(Exception ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
public HashMap<Object,Object> retrieveTreatmentDetailsForMorbForManage(Long publicHealthUID,
                                                NBSSecurityObj nbsSecurityObj) throws
                                                java.rmi.RemoteException,
                                                javax.ejb.EJBException,
                                                NEDSSSystemException,
                                                javax.ejb.FinderException,
                                                javax.ejb.CreateException {
                                              String aQuery = null;

      aQuery = "SELECT source_act_uid \"uid1\", observation.OBSERVATION_UID \"uid2\",observation.CD \"str1\"  FROM Act_relationship with (nolock) , observation  with (nolock) "+
       " WHERE target_act_uid IN(SELECT source_act_uid FROM Act_relationship  with (nolock) "+
       " WHERE record_status_cd = 'ACTIVE' "+
       " AND target_act_uid =  "+ publicHealthUID +
       " AND source_class_cd = 'OBS' AND target_class_cd = 'CASE' and type_cd = 'MorbReport') "+
       " AND Act_relationship.record_status_cd = 'ACTIVE' "+
       " AND Act_relationship.status_cd='A' "+
       " AND source_class_cd = 'TRMT' "+
       " AND target_class_cd = 'OBS' and type_cd = 'TreatmentToMorb' "+
       " AND Act_relationship.TARGET_ACT_UID = observation.OBSERVATION_UID ";

    logger.info("aQuery = " + aQuery);
  HashMap<Object,Object> map  =new HashMap<Object,Object>();
  GenericSummaryVO summaryVO = new GenericSummaryVO();
  try{
	  ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(summaryVO, null, aQuery, NEDSSConstants.SELECT);
	  if(list!= null)
	  {
	   Iterator<Object>  it = list.iterator();
	    while(it.hasNext())
	    {
	      GenericSummaryVO summVO = (GenericSummaryVO)it.next();
	      map.put(summVO.getUid1(), summVO);
	    }
	
	  }
  }catch(Exception ex){
	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	  throw new NEDSSSystemException(ex.toString());
  }
  return map;
}

@SuppressWarnings("unchecked")
public HashMap<Object,Object> retrieveTreatmentAndObservationMorbForManage(String inClause,
                                              NBSSecurityObj nbsSecurityObj) throws
                                              java.rmi.RemoteException,
                                              javax.ejb.EJBException,
                                              NEDSSSystemException,
                                              javax.ejb.FinderException,
                                              javax.ejb.CreateException {
                                            String aQuery = null;

    aQuery = "SELECT source_act_uid \"uid1\", "+
        " observation.OBSERVATION_UID \"uid2\", "+
        "observation.CD \"str1\" "+
        "FROM Act_relationship with (nolock) , observation  with (nolock) "+
        "WHERE Act_relationship.record_status_cd = 'ACTIVE' "+
        "AND source_act_uid IN "+
        inClause +
        "AND Act_relationship.record_status_cd = 'ACTIVE' "+
        "AND Act_relationship.status_cd='A'  "+
        "AND source_class_cd = 'TRMT'  "+
        "AND target_class_cd = 'OBS' "+
        "and type_cd = 'TreatmentToMorb' "+
        "AND Act_relationship.TARGET_ACT_UID = observation.OBSERVATION_UID";

    logger.info("aQuery = " + aQuery);
    HashMap<Object,Object> map  =new HashMap<Object,Object>();
    GenericSummaryVO summaryVO = new GenericSummaryVO();
    try{
	    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(summaryVO, null, aQuery, NEDSSConstants.SELECT);
	    if(list!= null)
	    {
	     Iterator<Object>  it = list.iterator();
	      while(it.hasNext())
	      {
	        GenericSummaryVO summVO = (GenericSummaryVO)it.next();
	        map.put(summVO.getUid1(), summVO);
	      }
	    }
    }catch(Exception ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    return map;
  }

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Treatment to be
   * populated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Treatment related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of TreatmentSummaryVO for the passed person UID
   */
  public ArrayList<Object> retrieveTreatmentSummaryListForWorkup(Long personUID,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
	  try{
		  return (getTreatmentSummaryListForWorkup(personUID, nbsSecurityObj));
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Treatment to be
   * papulated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Treatment related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of TreatmentSummaryVO for the passed person UID
   */
  public HashMap<Object,Object> retrieveTreatmentSummaryListForWorkup(Collection<Object> uidList,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
	  try{
		  return (getTreatmentSummaryListForWorkup(uidList, nbsSecurityObj));
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

	 @SuppressWarnings("unchecked")
	public Collection<Object> retrieveTreatmentSummaryVOList(Collection<Object> uidList,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		ArrayList<Object> labList = new ArrayList<Object> ();
		try{
			String SELECT_TRT;
			HashMap<Object,Object> map = new HashMap<Object,Object>();
			if (uidList != null) {
				if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)) {
					logger.debug(
							"Permission for retrieveTreatmentSummaryListForWorkup() = " +
							NBSBOLookup.TREATMENT);
					//throw new NEDSSSystemException("no permissions to view a treatment");
				}
				SELECT_TRT = WumSqlQuery.SELECT_TREATMENT_SUMMARY_LIST_FOR_MORB;
				SELECT_TRT = this.prepareSQLForContainsIn(uidList, SELECT_TRT, "");
				TreatmentSummaryVO treatmentSummaryVO = new TreatmentSummaryVO();

				labList = (ArrayList<Object> )preparedStmtMethod(treatmentSummaryVO, null, SELECT_TRT, NEDSSConstants.SELECT)  ;
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labList;
  }

  public HashMap<Object,Object> retrieveTreatmentSummaryVOForPHCList(Collection<Object> uidList,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
    return (getTreatmentSummaryVOForPHCList(uidList, nbsSecurityObj));
  }



  @SuppressWarnings("unchecked")
public HashMap<Object,Object> getTreatmentSummaryVOForPHCList(Collection<Object> uidList,
		NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
         ArrayList<Object> labList = new ArrayList<Object> ();
         String SELECT_TRT1 = "";
         String SELECT_TRT2 ="";
         HashMap<Object,Object> map = new HashMap<Object,Object>();
         try{
	         if (uidList != null) {
	                 if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)) {
	                         logger.debug(
	                                         "Permission for retrieveTreatmentSummaryListForWorkup() = " +
	                                         NBSBOLookup.TREATMENT);
	                         //throw new NEDSSSystemException("no permissions to view a treatment");
	                 }
	                   SELECT_TRT1 = "select distinct (treatment.treatment_uid)  \"treatmentUid\", "+
	                       " Treatment.cd \"treatmentNameCode\", "+
	                       " ar.add_time \"createDate\", "+
	                       " Treatment.cd_desc_txt \"customTreatmentNameCode\", Treatment.local_id \"localId\",  "+
	                       " treatment_administered.EFFECTIVE_FROM_TIME \"activityFromTime\" "+
	                       " from treatment  with (nolock) , "+
	                       " treatment_administered  with (nolock) , "+
	                       " act_relationship ar  with (nolock) "+
	                       " where treatment.treatment_uid in(select act_uid from participation "+
	                                                        " where participation.subject_entity_uid in(select person_uid from person "+
	                       " where person.person_parent_uid IN " ;
	
	                   //03/15/05 - Narendra - Updated query to fetch only active treatment records(as per design)
	                   SELECT_TRT2 = ") and participation.type_cd='SubjOfTrmt' "+
	                       " and participation.ACT_CLASS_CD='TRMT' "+
	                       " and participation.SUBJECT_CLASS_CD='PSN' "+
	                       " and participation.record_status_cd='ACTIVE') "+
	                       " and treatment.treatment_uid=treatment_administered.treatment_uid "+
	                       " and ar.type_cd='TreatmentToPHC' "+
	                       " and ar.source_act_uid = Treatment.treatment_uid "+
	                       " and treatment.record_status_cd='ACTIVE'";
	
	
	
	                 SELECT_TRT1 = this.prepareSQLForContainsIn(uidList, SELECT_TRT1, "");
	                 TreatmentSummaryVO treatmentSummaryVO = new TreatmentSummaryVO();
	                 SELECT_TRT1  = SELECT_TRT1+ SELECT_TRT2;
	                 labList = (ArrayList<Object> )preparedStmtMethod(treatmentSummaryVO, null, SELECT_TRT1, NEDSSConstants.SELECT)  ;
	         }
         if(labList.size()>0)
	         {
	          Iterator<Object>  it = labList.iterator();
	           while(it.hasNext())
	           {
	             TreatmentSummaryVO treatmentSummaryVO = (TreatmentSummaryVO)it.next();
	             treatmentSummaryVO.setAssociationMap(this.getAssociatedInvList(treatmentSummaryVO.getTreatmentUid(), nbsSecurityObj, "TRMT"));
	             ArrayList<Object> providerDetails = new ArrayList<Object>();
	             providerDetails = this.getProviderInfo(treatmentSummaryVO.getTreatmentUid(), "ProviderOfTrmt", "TREATMENT");
	             if (providerDetails != null && providerDetails.size() > 0 && treatmentSummaryVO != null) {
	                 Object[] provider = providerDetails.toArray();
	
	                 if (provider[0] != null) {
	                	 treatmentSummaryVO.setProviderLastName((String) provider[0]);
	                   logger.debug("ProviderLastName: " + (String) provider[0]);
	                 }
	                 if (provider[1] != null)
	                	 treatmentSummaryVO.setProviderFirstName((String) provider[1]);
	                 logger.debug("ProviderFirstName: " + (String) provider[1]);
	                 if (provider[2] != null)
	                	 treatmentSummaryVO.setProviderPrefix((String) provider[2]);
	                  logger.debug("ProviderPrefix: " + (String) provider[2]);
	                 if (provider[3] != null)
	                	 treatmentSummaryVO.setProviderSuffix(( String)provider[3]);
	                 	logger.debug("ProviderSuffix: " + (String) provider[3]);
	
	                 	if (provider[4] != null)
	                 		treatmentSummaryVO.setDegree(( String)provider[4]);
	                    	logger.debug("ProviderDegree: " + (String) provider[4]);
	
	               }
	             map.put(treatmentSummaryVO.getTreatmentUid(), treatmentSummaryVO);
	           }
	         }

         }catch(Exception ex){
        	 logger.fatal("Exception  = "+ex.getMessage(), ex);
        	 throw new NEDSSSystemException(ex.toString());
         }

         return map;
}

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Treatment to be
   * populated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Treatment related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of TreatmentSummaryVO for the passed person UID
   */
  @SuppressWarnings("unchecked")
protected ArrayList<Object> getTreatmentSummaryListForWorkup(Long personUID,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
    String SELECT_TRT;
    ArrayList<Object> list = new ArrayList<Object> ();
    try{
	    if (personUID != null) {
	      if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
	                                        NBSOperationLookup.VIEW)) {
	        logger.debug(
	            "Permission for retrieveTreatmentSummaryListForWorkup() = " +
	            NBSBOLookup.TREATMENT);
	        //throw new NEDSSSystemException("no permissions to view a treatment");
	      }
	      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	          NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW);
	      logger.info("dataAccessWhereClause = " + dataAccessWhereClause);
	
	      /** @todo fix where clause */
	      //dataAccessWhereClause = null;
	
	      if (dataAccessWhereClause == null) {
	        dataAccessWhereClause = "";
	      }
	      else {
	        dataAccessWhereClause = " AND " + dataAccessWhereClause;
	
	      }
	      SELECT_TRT = WumSqlQuery.SELECT_TREATMENT_SUMMARY_LIST_FOR_WORKUP_ORACLE;
	      SELECT_TRT = SELECT_TRT + dataAccessWhereClause;
	      logger.debug("Select trt query is: " + SELECT_TRT);
	      /**
	       * Get a collection of treatment summary vo as a map
	       */
	      ArrayList<Object> inputArgs = new ArrayList<Object> ();
	      inputArgs.add(personUID);
	      TreatmentSummaryVO treatSum = new TreatmentSummaryVO();
	      list =  (ArrayList<Object> )preparedStmtMethod(treatSum, inputArgs,
	           SELECT_TRT, NEDSSConstants.SELECT);
	      /**if(list!= null)
	      {
	       Iterator<Object>  it = list.iterator();
	        while(it.hasNext())
	        {
	          TreatmentSummaryVO summ = (TreatmentSummaryVO)it.next();
	          map.put(summ.getTreatmentUid(), summ);
	        }
	      }*/
	
	
	    }
    }catch(Exception ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    return list;
  } //end of getTreatmentSummaryVOCollectionForWorkup()

  /**
   * This method will access the HashMap<Object,Object> of summaryList for Treatment to be
   * papulated on view file page for a person whose UID has been passed
   * @param personUID -- UID  for person to Access Treatment related to it
       * @param nbsSecurityObj -- security object to get the data Access Where Clause
   * @throws NEDSSSystemException
       * @return HashMap<Object,Object> -- HashMap<Object,Object> of TreatmentSummaryVO for the passed person UID
   */
  protected HashMap<Object,Object> getTreatmentSummaryListForWorkup(Collection<Object> uidList,
      NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
    String SELECT_TRT;
    HashMap<Object,Object> map = new HashMap<Object,Object>();
    Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
    try {
    if (uidList != null) {
    	if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
                                        NBSOperationLookup.VIEW)) {
    		logger.debug(
            "Permission for retrieveTreatmentSummaryListForWorkup() = " +
            NBSBOLookup.TREATMENT);
        //throw new NEDSSSystemException("no permissions to view a treatment");
    	}
    	String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
          NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW, "Treatment");
    	logger.info("dataAccessWhereClause = " + dataAccessWhereClause);


      /** @todo fix where clause */
      //dataAccessWhereClause = null;

    	if (dataAccessWhereClause == null) {
    		dataAccessWhereClause = "";
    	}else {
    		dataAccessWhereClause = " AND " + dataAccessWhereClause;

    	}
    	SELECT_TRT = WumSqlQuery.
            SELECT_TREATMENT_SUMMARY_LIST_FOR_WORKUP_ORACLE_IN;
    	SELECT_TRT = this.prepareSQLForContainsIn(uidList, SELECT_TRT,
                                                 dataAccessWhereClause);
    	/**
    	 * Get a collection of treatment summary vo as a map
    	 */
    	
    	ResultSetMetaData resultSetMetaData = null;
    	ResultSetUtils resultSetUtils = new ResultSetUtils();

      
        dbConnection = getConnection();
        preparedStmt = dbConnection.prepareStatement(SELECT_TRT +
            dataAccessWhereClause);
        resultSet = preparedStmt.executeQuery();
        Long treatmentUID = null;
        String treatAdministered = null;
        CachedDropDownValues cache = new CachedDropDownValues();
        if (resultSet != null) {
          while (resultSet.next()) {
            TreatmentSummaryVO treatSumm = new TreatmentSummaryVO();
            treatSumm.setPersonUid(new Long(resultSet.getLong("personUid")));
            treatmentUID = new Long(resultSet.getLong("treatmentUID"));
            treatSumm.setTreatmentUid(treatmentUID);
            treatSumm.setTreatmentNameCode(resultSet.getString("treatmentNameCode"));
            treatSumm.setCustomTreatmentNameCode(resultSet.getString("customTreatmentNameCode"));
            treatSumm.setLocalId(resultSet.getString("localId"));
            treatSumm.setActivityFromTime(resultSet.getTimestamp("activityFromTime"));
            //Begin support for Morb display
            treatSumm.setParentUid(new Long(resultSet.getLong("parentUid")));
            treatSumm.setArTypeCd(resultSet.getString("arTypeCd"));
            //end support for morb display


            map.put(treatmentUID, treatSumm);
          }
        }

      }
    }
    catch (SQLException se) {
      logger.fatal(
          "Error: SQLException while getting treatment for a person in workup",
          se);
      throw new NEDSSSystemException(se.getMessage());
    }
    catch (Exception ex) {
      logger.fatal("Error while getting treatment for a person in workup",
                   ex);
      throw new NEDSSSystemException(ex.toString());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return map;
  } //end of getTreatmentSummaryVOCollectionForWorkup()

  @SuppressWarnings("unchecked")
public Collection<Object>  findAllMorbReportUidListForManage(Long subjectUID, String MORBREPORT_WHERECLAUSE)
  {
    try
    {
      String aQuery = "SELECT "+
                "part.act_uid  \"uid\""+
                "FROM Participation part  with (nolock) , Observation obs  with (nolock) "+
                "WHERE part.act_class_cd = '" + NEDSSConstants.OBSERVATION_CLASS_CODE + "' " +
                "AND part.subject_entity_uid = " + subjectUID + " " +
                "AND part.subject_class_cd = '" + NEDSSConstants.PERSON_CLASS_CODE + "' " +
                "AND part.type_cd = '" + NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT + "' " +
                "AND part.record_status_cd = '" + NEDSSConstants.RECORD_STATUS_ACTIVE + "' " +
                "AND obs.ctrl_cd_display_form = '" + NEDSSConstants.MORBIDITY_REPORT + "' " +
                "AND part.act_uid = obs.observation_uid " +
                "AND obs.record_status_cd = '" + NEDSSConstants.RECORD_STATUS_PROCESSED + "' " +
                MORBREPORT_WHERECLAUSE;
      logger.debug("aQuery:  " + aQuery);
      List<Object> targetMorbReportUidList = null;
      UidSummaryVO uiSum = new UidSummaryVO();
      targetMorbReportUidList =  (ArrayList<Object> )preparedStmtMethod(uiSum, null,
                aQuery, NEDSSConstants.SELECT);
      return targetMorbReportUidList;
    }
    catch(Exception e)
    {
      logger.fatal("Error in RetrieveSummaryVO findAllMorbReportUidListForManage = " +  e.getMessage(),e);
      throw new NEDSSSystemException(e.toString());
    }
  }

  public Collection<Object>  findAllActiveMorbReportUidListForManage()
  {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;

    try
    {
      dbConnection = getConnection();

      if(dbConnection == null) throw new NullPointerException("Error obtaining a new db connection.");

      String aQuery = "SELECT "+
                "source_act_uid, "+
                "target_act_uid "+
                "FROM Act_Relationship   with (nolock) "+
                "WHERE target_class_cd = '" + NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE + "' " +
                "AND source_class_cd = '" + NEDSSConstants.OBSERVATION_CLASS_CODE + "' " +
                "AND type_cd = '" + NEDSSConstants.MORBIDITY_REPORT + "' " +
                "AND record_status_cd = '" + NEDSSConstants.RECORD_STATUS_ACTIVE + "' ";
      logger.debug("aQuery:  " + aQuery);
      preparedStmt = dbConnection.prepareStatement(aQuery);
      resultSet = preparedStmt.executeQuery();
      List<Object> morbReportUidList = null;
      while(resultSet.next())
      {
        long sourceActUid =  resultSet.getLong(1);
        long targetActUid = resultSet.getLong(2);
        UidSummaryVO sourceUid = new UidSummaryVO();
        UidSummaryVO targetUid = new UidSummaryVO();
        sourceUid.setUid( new Long(sourceActUid));
        targetUid.setUid(new Long(targetActUid));
        UidSummaryVO targettUid = new UidSummaryVO();
        UidSummaryVO[] morbReportUid = {sourceUid, targetUid};
        if(morbReportUidList == null) morbReportUidList = new ArrayList<Object> ();
           morbReportUidList.add(morbReportUid);
      }
      return morbReportUidList;
  }
    catch(SQLException sex)
    {
      logger.fatal("Error in RetrieveSummaryVO findAllActiveMorbReportUidListForManage = " +  sex.getMessage(),sex);
      throw new NEDSSSystemException(sex.toString());
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  }


  @SuppressWarnings("unchecked")
public Collection<Object>  findAllLabReportUidListForManage(Long subjectUID, String LABREPORT_WHERECLAUSE)
  {
    try
    {
      EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
      Collection<Object>  uidList = entityProxyHelper.findActivePatientUidsByParentUid(subjectUID);

      String aQuery = "SELECT "+
                "act_uid \"uid\""+
                "FROM Participation part  with (nolock) , Observation obs  with (nolock) "+
                "WHERE part.act_class_cd = '" + NEDSSConstants.OBSERVATION_CLASS_CODE + "' " +
                "AND part.subject_class_cd = '" + NEDSSConstants.PERSON_CLASS_CODE + "' " +
                "AND part.type_cd = '" + NEDSSConstants.PAR110_TYP_CD + "' " +
                "AND part.record_status_cd = '" + NEDSSConstants.RECORD_STATUS_ACTIVE + "' " +
                "AND part.act_uid = obs.observation_uid " +
                "AND obs.record_status_cd IN ('" + NEDSSConstants.OBS_PROCESSED + "', '" + NEDSSConstants.OBS_UNPROCESSED + "')" +
                "AND part.subject_entity_uid IN ";
      aQuery = this.prepareSQLForContainsIn(uidList, aQuery, LABREPORT_WHERECLAUSE);
      logger.debug("aQuery:  " + aQuery);
      List<Object> targetLabReportUidList = null;
      UidSummaryVO uiSum = new UidSummaryVO();
      targetLabReportUidList =  (ArrayList<Object> )preparedStmtMethod(uiSum, null,
             aQuery, NEDSSConstants.SELECT);

      return targetLabReportUidList;
    }
    catch(Exception e)
    {
      logger.fatal("Error in RetrieveSummaryVO findAllLabReportUidListForManage = " +  e.getMessage(),e);
      throw new NEDSSSystemException(e.toString());
    }
  }
  @SuppressWarnings("unchecked")
public List<Object> findTargetReportUidListFor(Long investigationUid, String typeCd, String secureWhereClause)
{
  try
  {
    String aQuery = "SELECT "+
              "ar.source_act_uid  \"uid\" "+
              "FROM observation obs  with (nolock) , Act_Relationship ar  with (nolock) "+
              "WHERE ar.type_cd = '" + typeCd + "' " +
              "AND ar.target_act_uid = " + investigationUid + " " +
              "AND ar.source_class_cd = '" + NEDSSConstants.OBSERVATION_CLASS_CODE + "' " +
              "AND ar.target_class_cd = '" + NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE + "' " +
              "AND ar.record_status_cd = '" + NEDSSConstants.RECORD_STATUS_ACTIVE + "' " +
              "AND ar.source_act_uid = obs.observation_uid " + secureWhereClause;
    logger.debug("aQuery:  " + aQuery);
    List<Object> targetLabReportUidList = null;
    UidSummaryVO uiSum = new UidSummaryVO();
    targetLabReportUidList =  (ArrayList<Object> )preparedStmtMethod(uiSum, null,
              aQuery, NEDSSConstants.SELECT);
    return targetLabReportUidList;
  }
  catch(Exception e)
  {
    logger.fatal("Error in RetrieveSummaryVO findTargetLabReportUidListFor = " +  e.getMessage(),e);
    throw new NEDSSSystemException(e.toString());
  }
 }

 private EntityController getEntityControllerRemoteInterface()throws EJBException{
      	EntityController entityController = null;
    	try{
            logger.debug("YOU ARE IN THE getRemoteInterface() method");

            NedssUtils nu = new NedssUtils();
            Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
            logger.debug("YOU JUST DID A JNDI LOOKUP = " + obj.toString());
            EntityControllerHome entityControllerHome =	(EntityControllerHome)javax.rmi.PortableRemoteObject.narrow(obj,EntityControllerHome.class);
            logger.debug("YOU JUST INSTANTIATED THE HOME INTERFACE");
            entityController = entityControllerHome.create();
            logger.debug("YOU NOW HAVE A REMOTE INTERFACE");
		}catch(Exception e)
		{
			logger.fatal("Error while creating a EntityController reference in WorkupProxyEJB."+e.getMessage(), e);
            throw new EJBException(e.toString());
		}
			return entityController;
	}
 
 public Map<Object,Object> getPHCConditionAndProgArea(Long publicHealthCaseUid) {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Map<Object,Object> resultMap = new HashMap<Object,Object>();

		try {
			dbConnection = getConnection();

			if (dbConnection == null)
				throw new NullPointerException(
						"Error obtaining a new db connection.");

			String aQuery = "SELECT cd " + NEDSSConstants.CONDITION_CD
					+ ", prog_area_cd " + NEDSSConstants.PROG_AREA_CD
					+ " FROM Public_health_case  with (nolock) "
					+ "where public_health_case_uid=?";
			logger.debug("aQuery:  " + aQuery);
			preparedStmt = dbConnection.prepareStatement(aQuery);
			preparedStmt.setLong(1, publicHealthCaseUid.longValue());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				resultMap.put(NEDSSConstants.CONDITION_CD, resultSet
						.getString(NEDSSConstants.CONDITION_CD));
				resultMap.put(NEDSSConstants.PROG_AREA_CD, resultSet
						.getString(NEDSSConstants.PROG_AREA_CD));
			}
			return resultMap;
		} catch (SQLException sex) {
			logger
					.fatal("Error in RetrieveSummaryVO getPHCConditionAndProgArea = "
							+ sex.getMessage(), sex);
			throw new NEDSSSystemException(sex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

 public static Collection<Object>  notificationSummaryOnInvestigation(PublicHealthCaseVO
	      publicHealthCaseVO,Object object, NBSSecurityObj nbsSecurityObj) throws
	      NEDSSSystemException, NEDSSSystemException {

	    Collection<Object>  theNotificationSummaryVOCollection  = null;
	    Long publicHealthCaseUID = null;
	    NotificationSummaryVO notificationSummaryVO = null;

	    try{
		    if (publicHealthCaseVO != null) {
		      publicHealthCaseUID = publicHealthCaseVO.getThePublicHealthCaseDT().
		          getPublicHealthCaseUid();
		    }
		    RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
	
		    if (publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd() != null) {
		      theNotificationSummaryVOCollection  = (ArrayList<Object> ) retrieveSummaryVO.
		          retrieveNotificationSummaryListForInvestigation(publicHealthCaseUID,
		          nbsSecurityObj);
		    }
		    else {
		      theNotificationSummaryVOCollection  = (ArrayList<Object> ) retrieveSummaryVO.
		          retrieveNotificationSummaryListForInvestigation1(publicHealthCaseUID,
		          nbsSecurityObj);
	
		    }
		    if (theNotificationSummaryVOCollection  != null) {
			     Iterator<Object>  anIterator = theNotificationSummaryVOCollection.iterator();
			      int count = 0;
			      while (anIterator.hasNext()) {
			        notificationSummaryVO = (NotificationSummaryVO) anIterator.next();
	
			        if (count == 0) { //check only for the current Notification record
			          if (notificationSummaryVO.getRecordStatusCd().trim().equals(
			              NEDSSConstants.NOTIFICATION_APPROVED_CODE) ||
			              notificationSummaryVO.getRecordStatusCd().trim().equals(
			              NEDSSConstants.NOTIFICATION_PENDING_CODE) ||
			              (notificationSummaryVO.getAutoResendInd() != null && notificationSummaryVO.getAutoResendInd().equalsIgnoreCase("T"))) {
			        	  if(object instanceof InvestigationProxyVO){
			        		  InvestigationProxyVO investigationProxyVO = (InvestigationProxyVO)object;
			        		  investigationProxyVO.setAssociatedNotificationsInd(true);
			        	  } else if(object instanceof PamProxyVO) {
			        		  PamProxyVO pamProxy = (PamProxyVO) object;
			        		  pamProxy.setAssociatedNotificationsInd(true);
			        	  }
			          }
			        }
			        count++;
			        logger.debug("Notification record status is " +
			                     notificationSummaryVO.getRecordStatusCd());
	
			        if (notificationSummaryVO.getRecordStatusCd() != null &&
			            notificationSummaryVO.getRecordStatusCd().trim().equals(
			            NEDSSConstants.PENDING_APPROVAL_STATUS)) {
			          notificationSummaryVO.setCd(publicHealthCaseVO.
			                                      getThePublicHealthCaseDT().getCd());
			          notificationSummaryVO.setCdTxt(publicHealthCaseVO.
			                                         getThePublicHealthCaseDT().
			                                         getCdDescTxt());
			       
			          //The following lines of code were commented out for  notificationSummaryVO.setCaseClassCd as there was a bug openend 
			          //in release 3.0 where the notificationSummary was getting the caseClassCd from publicHealthCase for Pending approval cases only.
			          /**As this was not a true reflection of notification, and to fix the bog, this code was commented out.
			         /   notificationSummaryVO.setCaseClassCd(publicHealthCaseVO.
			         /                                    getThePublicHealthCaseDT().
			                                           getCaseClassCd());
			          CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
			          String caseClassCdTxt = cachedDropDownValues.getDescForCode(
			             NEDSSConstants.CASE_CLASS_CODE_SET_NM,
			             publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd());
			         notificationSummaryVO.setCaseClassCdTxt(caseClassCdTxt);
			         */ //!!##System.out.println("notificationSummaryVO.getCaseClassCd()" + notificationSummaryVO.getCaseClassCd());
			        }
			      }
			    }
		    
	        // TODO:Needs to be fixed to move to Action class.
		    
	  	    if (theNotificationSummaryVOCollection  != null) {
	  	     Iterator<Object>  anIterator1 = theNotificationSummaryVOCollection.iterator();
	  	      while (anIterator1.hasNext()) {
	  	        notificationSummaryVO = (NotificationSummaryVO) anIterator1.next();
	  	        	  if(object instanceof InvestigationProxyVO){
	  	        		  InvestigationProxyVO investigationProxyVO = (InvestigationProxyVO)object;
	  	        		  if(notificationSummaryVO.isCaseReport()){
	  	        			if(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_APPROVED_CODE) ){
	  		        			  investigationProxyVO.setOOSystemInd(true);
	  		        		  }
	  		        	
	  	        			if(notificationSummaryVO.isHistory!=null && 
	  	        					!notificationSummaryVO.isHistory.equals("T") && 
	  	        					notificationSummaryVO.getCdNotif()!=null && 
	  	        					(notificationSummaryVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || notificationSummaryVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) && 
	  	        					!(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE) || 
	  	        							notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED))){
	  		        			  investigationProxyVO.setOOSystemPendInd(true);
	  		        		  }
	  		        		}  
	  	        		 
	  	        		  }
	  	        	  else if(object instanceof PamProxyVO) {
	  	        		  PamProxyVO pamProxy = (PamProxyVO) object;
	  	        		  if(notificationSummaryVO.isCaseReport()){
	  	        			if(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_APPROVED_CODE) ){
	  	        				pamProxy.setOOSystemInd(true);
	  		        		  }
	  		        		
	  	        			if(notificationSummaryVO.isHistory!=null && 
	  	        					!notificationSummaryVO.isHistory.equals("T") && 
	  	        					notificationSummaryVO.getCdNotif()!=null && 
	  	        					(notificationSummaryVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || notificationSummaryVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) && 
	  	        					!(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE) 
	  	        							|| notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED))){
	  	        				pamProxy.setOOSystemPendInd(true);
	  		        		}  
	  	        	  }
	  	          }
	  	        	else if(object instanceof PageActProxyVO) {
	  	        		PageActProxyVO pageProxy = (PageActProxyVO) object;
	  	        		  if(notificationSummaryVO.isCaseReport()){
	  	        			if(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_APPROVED_CODE) ){
	  	        				pageProxy.setOOSystemInd(true);
	  		        		  }
	  		        		
	  	        			if(notificationSummaryVO.isHistory!=null && 
	  	        					!notificationSummaryVO.isHistory.equals("T") && 
	  	        					notificationSummaryVO.getCdNotif()!=null && 
	  	        					(notificationSummaryVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || notificationSummaryVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) && 
	  	        					!(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE) 
	  	        							|| notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED))){
	  	        				pageProxy.setOOSystemPendInd(true);
	  		        		}  
	  	        	  }
	  	          }
	  	      }
	  	    }
	    }catch(Exception ex){
	    	logger.fatal("Exception  = "+ex.getMessage(), ex);
	    	throw new NEDSSSystemException(ex.toString());
	    }
	    return theNotificationSummaryVOCollection;
	  } //end of observationAssociates()
 
 /**
  * @param notificationUid
  * @param phcCd
  * @param phcClassCd
  * @param progAreaCd
  * @param jurisdictionCd
  * @param sharedInd
  * @param nbsSecurityObj
  */
 public static  void updateNotification(Long notificationUid,
 		String businessTriggerCd,
 		String phcCd,
 		String phcClassCd,
 		String progAreaCd,
 		String jurisdictionCd,
 		String sharedInd,
 		NBSSecurityObj nbsSecurityObj) {

     NedssUtils nedssUtils = new NedssUtils();
     Collection<Object>  notificationVOCollection  = null;
     Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
     try
     {
       ActControllerHome ecHome = (ActControllerHome) PortableRemoteObject.narrow(theLookedUpObject,ActControllerHome.class);
       ActController actController = ecHome.create();
       NotificationVO notificationVO = actController.getNotification(notificationUid,nbsSecurityObj);
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
       NotificationDT newNotificationDT = null;
       NotificationDT notificationDT = notificationVO.getTheNotificationDT();
       notificationDT.setProgAreaCd(progAreaCd);
       notificationDT.setJurisdictionCd(jurisdictionCd);
       notificationDT.setCaseConditionCd(phcCd);
       notificationDT.setSharedInd(sharedInd);
       notificationDT.setCaseClassCd(phcClassCd);
       notificationVO.setItDirty(true);
       notificationDT.setItDirty(true);

       //retreive the new NotificationDT generated by PrepareVOUtils
       newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
       notificationDT, NBSBOLookup.NOTIFICATION, businessTriggerCd,
       DataTables.NOTIFICATION_TABLE, NEDSSConstants.BASE, nbsSecurityObj);

       //replace old NotificationDT in NotificationVO with the new NotificationDT
       notificationVO.setTheNotificationDT(newNotificationDT);
       Long newNotficationUid = actController.setNotification(notificationVO,nbsSecurityObj);
     }catch (Exception e){
           logger.fatal("Error calling ActController.setNotification() " + e.getMessage(),e);
           throw new NEDSSSystemException("Error in calling ActControllerEJB.setNotification()" + e.toString());
     }

      logger.info("updateNotification on NNDMessageSenderHelper complete");

 }
		 // Added this for Investigation audit log summary on the RVCT Page(civil00014862)
		 
		 /**
		  * This method will access the Collection<Object>  of AuditLog SummaryList for passed
		  * investigationUID to populate the user audit log summary on investigation page
		* @param PublicHealthCaseUID -- PublicHealthCaseUID to Access summary related to it
		  * @param nbsSecurityObj -- security object to get the data Access Where Clause
		* @throws NEDSSSystemException
		* @return Collection<Object>  -- Collection<Object>  of AuditLogSummaryVO for the passed publicHealthUID
		*/
		@SuppressWarnings("unchecked")
		public ArrayList<Object> retrieveInvestigationAuditLogSummaryVO(Long
		 publicHealthUID, NBSSecurityObj nbsSecurityObj) throws
		 NEDSSSystemException {
			ArrayList<Object> investigationAuditLogSummaryVOCollection  = new ArrayList<Object> ();
			
			try{
			  if (publicHealthUID != null) {
			  if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
			                                   NBSOperationLookup.VIEW)) {
				  logger.info("INVESTIGATION = " + NBSBOLookup.INVESTIGATION +
			               ",  VIEW = " + NBSOperationLookup.VIEW);
				  throw new NEDSSSystemException("no permissions to VIEW  AuditLog Summary");
			  }
			 
			  String statement[] = new String[2];
			  statement[0] = WumSqlQuery.SELECT_PUBLIC_HEALTHCASE_HISTORY_FOR_INVESTIGATION_LOG + "ORDER BY Public_health_case_hist.version_ctrl_nbr desc ";
			  statement[1] = WumSqlQuery.SELECT_PUBLIC_HEALTHCASE_FOR_INVESTIGATION_LOG ;
	
		      logger.info("statement0 = " + statement[0]);
		      logger.info("statement1 = " + statement[1]);

	      
	      
	    	  InvestigationAuditLogSummaryVO invAuditLogVO = new InvestigationAuditLogSummaryVO();
	    	  ArrayList<Object> inputArg = new ArrayList<Object> ();
	          inputArg.add(publicHealthUID);
	          ArrayList<Object> phHistory = new ArrayList<Object> ();
	          ArrayList<Object> phCurrent  = new ArrayList<Object> ();
	          phCurrent = (ArrayList<Object> )preparedStmtMethod(invAuditLogVO, inputArg, statement[1], NEDSSConstants.SELECT);  
	          for (Iterator<Object> anIterator = phCurrent.iterator(); anIterator.hasNext(); ) {
	        	  InvestigationAuditLogSummaryVO currentVO = (InvestigationAuditLogSummaryVO) anIterator.next();
			      currentVO = getUpdatedInvestigationAuditLogSummaryVO(currentVO);  
			      investigationAuditLogSummaryVOCollection.add(currentVO);
			      if(currentVO !=null && currentVO.getVersionCtrlNbr().intValue()!=NEDSSConstants.INITIAL_VERSION_CONTROL_NUMBER){
			        	  phHistory = (ArrayList<Object> )preparedStmtMethod(invAuditLogVO, inputArg, statement[0], NEDSSConstants.SELECT);  
			          
			        	  for (Iterator<Object> historyIterator = phHistory.iterator(); historyIterator.hasNext(); ) {
				        	  InvestigationAuditLogSummaryVO historyVO = (InvestigationAuditLogSummaryVO) historyIterator.next();
				        	  historyVO = getUpdatedInvestigationAuditLogSummaryVO(historyVO);
				        	  investigationAuditLogSummaryVOCollection.add(historyVO);
			        	  }	
			        	  
	          	  }
	          
	          } 
	        
	       
	      }
		}catch (Exception e) {
	    	   logger.fatal("Error: while selecting \n"+e.getMessage(), e);
	    	   throw new NEDSSSystemException(e.getMessage());
	    }
		return   investigationAuditLogSummaryVOCollection      ;
	} 
		
	 
		public String getUserName(long userId){
		 UserProfileDAO daoUserProfile = new UserProfileDAO();
		 StringBuffer userName = new StringBuffer();
		 try{
			 ArrayList<Object> alUserProfile = daoUserProfile.list();
		     Iterator<Object>  iUserProfile = alUserProfile.iterator();
		     while(iUserProfile.hasNext())
	         {
	             UserProfileDT dtUserProfile = (UserProfileDT)iUserProfile.next();
	             if(dtUserProfile!=null && dtUserProfile.getNEDSS_ENTRY_ID()== userId){
	             String fName = dtUserProfile.getFIRST_NM()== null ?"": dtUserProfile.getFIRST_NM();
	             String lName = dtUserProfile.getLAST_NM()== null ?"" : dtUserProfile.getLAST_NM();
	             userName.append(fName);
	             userName.append(" ");
	             userName.append(lName);
	             break;
	             }
	         }
		 }catch(Exception ex){
			 logger.error("Exception  = "+ex.getMessage(), ex);
		 }
	     return userName.toString();  
	      
	 }	
		public InvestigationAuditLogSummaryVO getUpdatedInvestigationAuditLogSummaryVO(InvestigationAuditLogSummaryVO oneVO){
			CachedDropDownValues cache = new CachedDropDownValues();
			InvestigationAuditLogSummaryVO updatedVO = new InvestigationAuditLogSummaryVO();
			try{
				if(oneVO !=null && oneVO.getVersionCtrlNbr().intValue()>0){
					if(oneVO.getVersionCtrlNbr().intValue()!= NEDSSConstants.INITIAL_VERSION_CONTROL_NUMBER ){
					updatedVO.setLastChgUserId(oneVO.getLastChgUserId()== null ? new Long(0): oneVO.getLastChgUserId());
					updatedVO.setUserName(getUserName(updatedVO.getLastChgUserId().longValue()));
					updatedVO.setChangeDate(StringUtils.formatDate(oneVO.getLastChgTime()));
					}else{
						updatedVO.setAddUserId(oneVO.getAddUserId()== null ? new Long(0): oneVO.getAddUserId());
						updatedVO.setUserName(getUserName(updatedVO.getAddUserId().longValue()));
						updatedVO.setChangeDate(StringUtils.formatDate(oneVO.getAddTime()));
					}
					updatedVO.setJuridictionText(cache.getJurisdictionDesc(oneVO.getJurisdictionCd()==null ? "":oneVO.getJurisdictionCd()));
					String caseClassCdTxt = cache.getDescForCode(NEDSSConstants.CASE_CLASS_CODE_SET_NM,
	  					  					oneVO.getCaseStatusCd()==null ? "":oneVO.getCaseStatusCd());
					updatedVO.setCaseStatusText(caseClassCdTxt);
					updatedVO.setVersionCtrlNbr(oneVO.getVersionCtrlNbr());
				}
			}catch(Exception ex){
				logger.error("Exception  = "+ex.getMessage(), ex);
			}
			return updatedVO;
		}	
			
		//End (civil00014862)	
			

public HashMap<Object,Object> retrieveDocumentSummaryVOForInv(Long publicHealthUID,
			      NBSSecurityObj nbsSecurityObj) throws
			      java.rmi.RemoteException,
			      javax.ejb.EJBException,
			      NEDSSSystemException,
			      javax.ejb.FinderException,
			      javax.ejb.CreateException {

			String sqlQuery = WumSqlQuery.DOCUMENT_FOR_A_PHC;
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();
	
			    logger.info("aQuery = " + sqlQuery);
			    logger.debug("getInvestgationSummaryVO - aQuery = " + sqlQuery);
			    HashMap<Object,Object> documentSummaryVOColl = new HashMap<Object,Object>();
			    ArrayList<Object> docList= new ArrayList<Object> ();
			    Map<Object,Object> map = null;
			    //SummaryDT summaryDT = new SummaryDT();
			    try {
			    	dbConnection = getConnection();
			    	preparedStmt = dbConnection.prepareStatement(sqlQuery);
			    	preparedStmt.setLong(1, publicHealthUID.longValue());
					resultSet = preparedStmt.executeQuery();
					if(resultSet!=null){
					resultSetMetaData = resultSet.getMetaData();
					
					documentSummaryVOColl=(HashMap<Object,Object>) resultSetUtils.mapRsToBeanMap(
					          resultSet, resultSetMetaData,SummaryDT.class,
					          "getNbsDocumentUid", map);
					logger.debug("returned document list");
					
			    
							}
			    }
			    catch (SQLException se) {
			        logger.fatal("Error: SQLException while selecting \n"+se.getMessage(), se);
			        throw new NEDSSSystemException(se.getMessage());
     		    }catch (Exception rsuex) {
			        logger.fatal("Error in result set handling while populate retrieveDocumentSummaryVOForInv."+rsuex.getMessage(),rsuex);
			        throw new NEDSSSystemException(rsuex.toString());
     		    }finally {
			        closeResultSet(resultSet);
			        closeStatement(preparedStmt);
			        releaseConnection(dbConnection);
     		    }
			    logger.debug("InvestigationProxyEJB:retrieveDocumentSummaryVOForInv Completed InvestigationProxyEJB:retrieveDocumentSummaryVOForInv***************");
			    return documentSummaryVOColl;
			  } // retrieveDocumentSummaryList

		public Collection<Collection> getCTContactSummaryDTColl(Long publicHealthCaseUid){
			Collection<Object> coll = new ArrayList<Object>();
			String sql = "";
			
			return null;
		}
		/*
		 * getAssociatedInvList - from the act relationship retrieve any associated cases for the passed in class code
		 * Note: This was modified for STD to also retrieve the Processing Decision stored in the add_reason_cd
		 * Processing Decision is only stored for STD and only when associating a lab or morb to a closed case.
		 */
		  public Map<Object,Object>  getAssociatedDocumentList(Long uid, NBSSecurityObj nbsSecurityObj,String targetClassCd, String sourceClassCd) throws NEDSSSystemException
		  {
			  Map<Object,Object> assocoiatedDocMap= new HashMap<Object,Object> ();
			  Connection dbConnection = null;
	          PreparedStatement preparedStmt = null;
	          ResultSet resultSet = null;
			  try{
			    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
			            NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW);
			        logger.debug("getAssociatedDocumentList uid = " + uid +
			                     " - dataAccessWhereClause = " + dataAccessWhereClause);
			        if (dataAccessWhereClause == null) {
			          dataAccessWhereClause = "";
			        }
			        else {
			          dataAccessWhereClause = " AND " + dataAccessWhereClause;

			        }

				  String ASSOCIATED_DOC_QUERY = 
					  "select nbs_document.local_id \"localId\", " +
					  "nbs_document.nbs_document_uid \"uid\" " +	  
					  "from nbs_document  with (nolock) " +
					  "inner join act_relationship  with (nolock) on " +
					  "nbs_document.nbs_document_uid = act_relationship.source_act_uid " +
					  "and act_relationship.target_act_uid = ? " +
					  "and act_relationship.source_class_cd = ? " +
					  "and target_class_cd = ? " +
					  "and nbs_document.record_status_cd!='LOG_DEL' ";
	
				  ASSOCIATED_DOC_QUERY=ASSOCIATED_DOC_QUERY+dataAccessWhereClause;
		            

		            
		                dbConnection = getConnection();
		                preparedStmt = dbConnection.prepareStatement(ASSOCIATED_DOC_QUERY);
		                preparedStmt.setLong(1, uid.longValue());
		                preparedStmt.setString(2, sourceClassCd);
		                preparedStmt.setString(3, targetClassCd);
		                resultSet = preparedStmt.executeQuery();
		                logger.debug("get resultSet " + resultSet.toString());
		                while(resultSet.next()){
		                	assocoiatedDocMap.put(resultSet.getString("localId"), resultSet.getLong("uid"));
		                }
		              }
		              catch(SQLException se)
		              {
		                logger.fatal("Error: SQLException while getting associated document in workup", se);
		                throw new NEDSSSystemException( se.getMessage());
		              }
		              catch(Exception ex)
		              {
		                  logger.fatal("Error: Exception while getting associated document in workup", ex);
		                  throw new NEDSSSystemException(ex.toString());
		              }
		              finally
		              {
		                  closeResultSet(resultSet);
		                  closeStatement(preparedStmt);
		                  releaseConnection(dbConnection);
		              }

		      return assocoiatedDocMap;
		  }

		  

		  public Map<Object,Object>  getAssociatedInvListForLabs(Long uid, NBSSecurityObj nbsSecurityObj,String sourceClassCd) throws NEDSSSystemException
		  {
			  Map<Object,Object> assocoiatedInvMap= new HashMap<Object,Object> ();
			  Connection dbConnection = null;
	          PreparedStatement preparedStmt = null;
	          ResultSet resultSet = null;
			  try{
				  String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
			            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			        logger.debug("WorkupProxyEJB:getInvestgationSummaryVO uid = " + uid +
			                     " - dataAccessWhereClause = " + dataAccessWhereClause);
			        if (dataAccessWhereClause == null) {
			          dataAccessWhereClause = "";
			        }
			        else {
			          dataAccessWhereClause = " AND " + dataAccessWhereClause;

			        }

				  String ASSOCIATED_INV_QUERY = 
					  "select public_health_case.local_id \"localId\", " +
					  "public_health_case.cd \"cd\", " +
					  "act_relationship.add_reason_cd \"dispositionCd\" " +	  
					  "from public_health_case  with (nolock) " +
					  "inner join act_relationship  with (nolock) on " +
					  "public_health_case.public_health_case_uid = act_relationship.target_act_uid " +
					  "and act_relationship.source_act_uid = ? " +
					  "and act_relationship.source_class_cd = ? " +
					  "and target_class_cd = 'CASE' " +
					  "and public_health_case.record_status_cd!='LOG_DEL' ";


			        ASSOCIATED_INV_QUERY=ASSOCIATED_INV_QUERY+dataAccessWhereClause;
		            
		            String dispositionCd = null;

		            
		                dbConnection = getConnection();
		                preparedStmt = dbConnection.prepareStatement(ASSOCIATED_INV_QUERY);
		                preparedStmt.setLong(1, uid.longValue());
		                preparedStmt.setString(2, sourceClassCd);
		                resultSet = preparedStmt.executeQuery();
		                logger.debug("get resultSet " + resultSet.toString());
		                while(resultSet.next()){
		                	assocoiatedInvMap.put(resultSet.getString("localId"), resultSet.getString("cd"));
		                	if (sourceClassCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)) {
		                		dispositionCd = resultSet.getString("dispositionCd");
		                		if (!resultSet.wasNull() && dispositionCd != null && !dispositionCd.isEmpty())
		                			assocoiatedInvMap.put(resultSet.getString("localId")+"-"+resultSet.getString("cd"), dispositionCd);
		                	}
		                }
		              }
		              catch(SQLException se)
		              {
		                logger.fatal("Error: SQLException while getting associated investigations in workup"+se.getMessage(), se);
		                throw new NEDSSSystemException( se.getMessage());
		              }
		              catch(Exception ex)
		              {
		                  logger.fatal("Error: Exception while getting associated investigations in workup"+ex.getMessage(), ex);
		                  throw new NEDSSSystemException(ex.toString());
		              }
		              finally
		              {
		                  closeResultSet(resultSet);
		                  closeStatement(preparedStmt);
		                  releaseConnection(dbConnection);
		              }

		      return assocoiatedInvMap;
		  }
		  
		  
		  
		  public Map<Object,Object>  getAssociatedInvList(Long uid, NBSSecurityObj nbsSecurityObj,String sourceClassCd) throws NEDSSSystemException
		  {
			  Map<Object,Object> assocoiatedInvMap= new HashMap<Object,Object> ();
			  Connection dbConnection = null;
	          PreparedStatement preparedStmt = null;
	          ResultSet resultSet = null;
			  try{
				  String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
			            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			        logger.debug("WorkupProxyEJB:getInvestgationSummaryVO uid = " + uid +
			                     " - dataAccessWhereClause = " + dataAccessWhereClause);
			        if (dataAccessWhereClause == null) {
			          dataAccessWhereClause = "";
			        }
			        else {
			          dataAccessWhereClause = " AND " + dataAccessWhereClause;

			        }

				  String ASSOCIATED_INV_QUERY = 
					  "select public_health_case.local_id \"localId\", " +
					  "public_health_case.cd \"cd\", " +
					  "act_relationship.add_reason_cd \"dispositionCd\" " +	  
					  "from public_health_case  with (nolock) " +
					  "inner join act_relationship  with (nolock) on " +
					  "public_health_case.public_health_case_uid = act_relationship.target_act_uid " +
					  "and act_relationship.source_act_uid = ? " +
					  "and act_relationship.source_class_cd = ? " +
					  "and target_class_cd = 'CASE' " +
					  "and public_health_case.record_status_cd!='LOG_DEL' ";


			        ASSOCIATED_INV_QUERY=ASSOCIATED_INV_QUERY+dataAccessWhereClause;
		            
		            String dispositionCd = null;

		            
		                dbConnection = getConnection();
		                preparedStmt = dbConnection.prepareStatement(ASSOCIATED_INV_QUERY);
		                preparedStmt.setLong(1, uid.longValue());
		                preparedStmt.setString(2, sourceClassCd);
		                resultSet = preparedStmt.executeQuery();
		                logger.debug("get resultSet " + resultSet.toString());
		                while(resultSet.next()){
		                	assocoiatedInvMap.put(resultSet.getString("localId"), resultSet.getString("cd"));
		                	if (sourceClassCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)) {
		                		dispositionCd = resultSet.getString("dispositionCd");
		                		if (!resultSet.wasNull() && dispositionCd != null && !dispositionCd.isEmpty())
		                			assocoiatedInvMap.put(resultSet.getString("localId")+"-"+resultSet.getString("cd"), dispositionCd);
		                	}
		                }
		              }
		              catch(SQLException se)
		              {
		                logger.fatal("Error: SQLException while getting associated investigations in workup"+se.getMessage(), se);
		                throw new NEDSSSystemException( se.getMessage());
		              }
		              catch(Exception ex)
		              {
		                  logger.fatal("Error: Exception while getting associated investigations in workup"+ex.getMessage(), ex);
		                  throw new NEDSSSystemException(ex.toString());
		              }
		              finally
		              {
		                  closeResultSet(resultSet);
		                  closeStatement(preparedStmt);
		                  releaseConnection(dbConnection);
		              }

		      return assocoiatedInvMap;
		  }

		  /**
		   * getAssociatedInvListVersion2(): this version has been created as a replacement for the previous method called getAssociatedInvList.
		   * This method returns collection of objects instead of maps and it gets more information than the previous one.
		   * @param uid
		   * @param nbsSecurityObj
		   * @param sourceClassCd
		   * @return
		   * @throws NEDSSSystemException
		   */
		  
		  public Collection<Object>  getAssociatedInvListVersion2(Long uid, NBSSecurityObj nbsSecurityObj,String sourceClassCd) throws NEDSSSystemException
		  {
			  Collection<Object> investigations = new ArrayList<Object>();
			  Connection dbConnection = null;
	          PreparedStatement preparedStmt = null;
	          ResultSet resultSet = null;
			  try{
				  String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
			            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			        logger.debug("WorkupProxyEJB:getInvestgationSummaryVO uid = " + uid +
			                     " - dataAccessWhereClause = " + dataAccessWhereClause);
			        if (dataAccessWhereClause == null) {
			          dataAccessWhereClause = "";
			        }
			        else {
			          dataAccessWhereClause = " AND " + dataAccessWhereClause;

			        }

				  String ASSOCIATED_INV_QUERY = 
					  "select public_health_case.local_id \"localId\", " +
					  "public_health_case.case_class_cd \"classCd\", " +
					  "public_health_case.public_health_case_uid \"publichHealhCaseUid\", " +
					  "public_health_case.cd \"cd\", " +
					  "public_health_case.cd_desc_txt \"cd_desc_txt\", " +
					  "act_relationship.add_reason_cd \"dispositionCd\" " +	  
					  "from public_health_case  with (nolock) " +
					  "inner join act_relationship  with (nolock) on " +
					  "public_health_case.public_health_case_uid = act_relationship.target_act_uid " +
					  "and act_relationship.source_act_uid = ? " +
					  "and act_relationship.source_class_cd = ? " +
					  "and target_class_cd = 'CASE' " +
					  "and public_health_case.record_status_cd!='LOG_DEL' ";


			        ASSOCIATED_INV_QUERY=ASSOCIATED_INV_QUERY+dataAccessWhereClause;
		            		            
		                dbConnection = getConnection();
		                preparedStmt = dbConnection.prepareStatement(ASSOCIATED_INV_QUERY);
		                preparedStmt.setLong(1, uid.longValue());
		                preparedStmt.setString(2, sourceClassCd);
		                resultSet = preparedStmt.executeQuery();
		                logger.debug("get resultSet " + resultSet.toString());
		                while(resultSet.next()){
		                	InvestigationSummaryVO investigation = new InvestigationSummaryVO();
		                	investigation.setCd(resultSet.getString("cd"));
		                	investigation.setConditionCodeText(resultSet.getString("cd_desc_txt"));
		                	investigation.setCaseClassCd(resultSet.getString("classCd"));
		                	investigation.setLocalId(resultSet.getString("localId"));
		                	investigation.setDisposition(resultSet.getString("dispositionCd"));
		                	investigation.setPublicHealthCaseUid(resultSet.getLong("publichHealhCaseUid"));

		                	investigations.add(investigation);
		                }
		                
		              }
		              catch(SQLException se)
		              {
		                logger.fatal("Error: SQLException while getting associated investigations in workup"+se.getMessage(), se);
		                throw new NEDSSSystemException( se.getMessage());
		              }
		              catch(Exception ex)
		              {
		                  logger.fatal("Error: Exception while getting associated investigations in workup"+ex.getMessage(), ex);
		                  throw new NEDSSSystemException(ex.toString());
		              }
		              finally
		              {
		                  closeResultSet(resultSet);
		                  closeStatement(preparedStmt);
		                  releaseConnection(dbConnection);
		              }

		      return investigations;
		  }
		  
	public Map<Object, Object> getAssociatedDocumentMapBysourceEventId(
			String sourceEventUid, NBSSecurityObj nbsSecurityObj,
			String sourceClassCd) throws NEDSSSystemException{
		Map<Object, Object> assocoiatedDocMap = new HashMap<Object, Object>();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try {
			String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
					NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW);
			logger.debug("getAssociatedDocumentMapBysourceEventId source_event_uid = "
					+ sourceEventUid
					+ " - dataAccessWhereClause = "
					+ dataAccessWhereClause);
			if (dataAccessWhereClause == null) {
				dataAccessWhereClause = "";
			} else {
				dataAccessWhereClause = " AND " + dataAccessWhereClause;
			}
			String ASSOCIATED_DOC_QUERY = "select nbs_document.local_id \"localId\", "
					+ "nbs_document.cd \"cd\" "
					+ "from nbs_document  with (nolock) "
					+ "inner join edx_event_process  with (nolock) on "
					+ "nbs_document.nbs_document_uid = edx_event_process.nbs_document_uid "
					+ "and edx_event_process.doc_event_type_cd = ? and  edx_event_process.source_event_id = ? "
					+ "and nbs_document.record_status_cd!='LOG_DEL' ";
	
			ASSOCIATED_DOC_QUERY = ASSOCIATED_DOC_QUERY + dataAccessWhereClause;
		
		
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(ASSOCIATED_DOC_QUERY);
			preparedStmt.setString(1, sourceClassCd);
			preparedStmt.setString(2, sourceEventUid);
			resultSet = preparedStmt.executeQuery();
			logger.debug("get resultSet " + resultSet.toString());
			while (resultSet.next()) {
				assocoiatedDocMap.put(resultSet.getString("localId"),
						resultSet.getString("cd"));
			}
		} catch (SQLException se) {
			logger.fatal(
					"Error: SQLException while getting associated Documents in workup"+se.getMessage(),
					se);
			throw new NEDSSSystemException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal(
					"Error: Exception while getting associated Documents in workup"+ex.getMessage(),
					ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return assocoiatedDocMap;
	}
		  
		  
		  public ArrayList<Object>  getProviderInfo(Long uid,String partTypeCd, String eventType) throws NEDSSSystemException
		  {
			  ArrayList<Object> providerInfo= new ArrayList<Object> ();

			  String PROVIDER = "SELECT person_name.last_nm \"lastNm\", person_name.nm_degree \"degree\", " +
			  		"person_name.first_nm \"firstNm\" , person_name.nm_prefix \"prefix\" ,  person_name.nm_suffix \"suffix\" " +
			  		"FROM person_name  with (nolock) , "+eventType+ " with (nolock) , "+
			  		"participation WHERE person_name.person_uid = participation.subject_entity_uid " +
			  		"AND participation.act_uid = "+eventType+"."+eventType+"_uid " +
			  		"AND participation.type_cd = ? AND participation.subject_class_cd='PSN' " +
			  		"AND "+eventType+"."+eventType+"_uid = ? " ;
		            Connection dbConnection = null;
		            PreparedStatement preparedStmt = null;
		            ResultSet resultSet = null;

		            try
		            {
		                dbConnection = getConnection();
		                preparedStmt = dbConnection.prepareStatement(PROVIDER);
		                preparedStmt.setString(1, partTypeCd);
		                preparedStmt.setLong(2, uid.longValue());
		                resultSet = preparedStmt.executeQuery();
		                logger.debug("get resultSet " + resultSet.toString());
		                while(resultSet.next()){
		                	providerInfo.add(0, resultSet.getString("lastNm"));
		                	providerInfo.add(1, resultSet.getString("firstNm"));
		                	providerInfo.add(2, resultSet.getString("prefix"));
		                	providerInfo.add(3, resultSet.getString("suffix"));
		                	providerInfo.add(4, resultSet.getString("degree"));
		                }
		              }
		              catch(SQLException se)
		              {
		                logger.fatal("Error: SQLException while getting provider for "+eventType+" in workup"+se.getMessage(), se);
		                throw new NEDSSSystemException( se.getMessage());
		              }
		              catch(Exception ex)
		              {
		                  logger.fatal("Error while getting provider for "+eventType+" in workup"+ex.getMessage(), ex);
		                  throw new NEDSSSystemException(ex.toString());
		              }
		              finally
		              {
		                  closeResultSet(resultSet);
		                  closeStatement(preparedStmt);
		                  releaseConnection(dbConnection);
		              }

		      return providerInfo;
		  }
		
		 
		  public static void checkBeforeCreateAndStoreMessageLogDTCollection(Long investigationUID, Collection reportSumVOCollection, NBSSecurityObj nbsSecurityObj){

			  try {
				PublicHealthCaseDT publicHealthCaseDT = null;
				    NedssUtils nedssUtils = new NedssUtils();
				    Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				    logger.debug("ActController lookup = " + object.toString());
				ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(
				    object, ActControllerHome.class);
				logger.debug("Found ActControllerHome: " + acthome);
				    ActController actController = null;
				    actController = acthome.create();
				    publicHealthCaseDT = actController.getPublicHealthCaseInfo(investigationUID, nbsSecurityObj);
				  
				   
				if(publicHealthCaseDT.isStdHivProgramAreaCode()){
					createAndStoreMesssageLogDTCollection( reportSumVOCollection, publicHealthCaseDT, nbsSecurityObj );
					
				}
			} catch (Exception e) {
				logger.error("createAndStoreMessgaeLogCollection: Unabel to create/Store MessageLOG DT Collection"+ e.getMessage(),e);
	  
			  
			}
		  }
		  
		  public static void createAndStoreMesssageLogDTCollection(Collection<Object> reportSumVOCollection,PublicHealthCaseDT publicHealthCaseDT, NBSSecurityObj nbsSecurityObj ){
				try {
					Collection<MessageLogDT> coll =  new ArrayList<MessageLogDT>();
					java.util.Date dateTime = new java.util.Date();
					Timestamp time = new Timestamp(dateTime.getTime());

					if(!reportSumVOCollection.isEmpty())
					  {
						logger.debug("Number of observation sum vo: " + reportSumVOCollection.size());
					      Iterator<Object>  theIterator = reportSumVOCollection.iterator();
					       while( theIterator.hasNext() )
					       {
					            ReportSummaryInterface reportSumVO = (ReportSummaryInterface)theIterator.next();
					            if(reportSumVO.getIsAssociated()== true && reportSumVO.getIsTouched()== true){
					            	PublicHealthCaseRootDAOImpl phc = new PublicHealthCaseRootDAOImpl();
					            	PublicHealthCaseDT phcDT =phc.getOpenPublicHealthCaseWithInvestigatorDT(publicHealthCaseDT.getPublicHealthCaseUid());
					            	Long providerUid=nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
					            	if( phcDT!=null 
					            		&& (providerUid==null  
					            		|| !(providerUid.compareTo(phcDT.getCurrentInvestigatorUid())==0))){
					            		MessageLogDT messageLogDT =createMessageLogDT(phcDT, nbsSecurityObj);
					            		coll.add(messageLogDT);
					            	}
					    			
					            }
					       }
							MessageLogDAOImpl messageLogDAOImpl =  new MessageLogDAOImpl();
							try {
								messageLogDAOImpl.storeMessageLogDTCollection(coll);
							} catch (Exception e) {
								logger.error("Unable to store the Error message in createAndStoreMesssageLogDTCollection for = "
										+ publicHealthCaseDT.toString());
							}
					  }
				} catch (Exception e) {
					logger.error("createAndStoreMesssageLogDTCollection error throw"+ e.getMessage(),e);
				}
		  }
		  
		  public static MessageLogDT createMessageLogDT(PublicHealthCaseDT publicHealthCaseDT, NBSSecurityObj nbsSecurityObj){
			MessageLogDT messageLogDT = new MessageLogDT();
			try {
				java.util.Date dateTime = new java.util.Date();
				Timestamp time = new Timestamp(dateTime.getTime());
				messageLogDT.setConditionCd(publicHealthCaseDT.getCd());
				messageLogDT.setLastChgTime(time);
				messageLogDT.setAddTime(time);
				messageLogDT.setItNew(true);
				messageLogDT.setLastChgUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
				messageLogDT.setEventUid(publicHealthCaseDT.getPublicHealthCaseUid());
				messageLogDT.setEventTypeCd(NEDSSConstants.INVESTIGATION);
				messageLogDT.setMessageStatusCd(MessageConstants.N);
				messageLogDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				messageLogDT.setRecordStatusTime(time);
				messageLogDT.setMessageTxt(MessageConstants.NEW_PROVIDER_LAB_REPORT);
				messageLogDT.setUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
				messageLogDT.setPersonUid(publicHealthCaseDT.getCurrentPatientUid());
				messageLogDT.setAssignedToUid(publicHealthCaseDT.getCurrentInvestigatorUid());
			} catch (NumberFormatException e) {
				logger.error("Unable to store the Error message in createMessageLogDT for = "
						+ publicHealthCaseDT.toString(), e);
			}	
			return messageLogDT;
			
		  }
	/**
	 * Get the list of candidate co-infections.	  
	 * @param mprUid
	 * @param conditionCd
	 * @return
	 * @throws NEDSSAppException
	 */
	public ArrayList<Object> getCoinfectionInvList(Long mprUid,
			String conditionCd) throws NEDSSAppException {
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ResultSetMetaData resultSetMetaData = null;
		String coinfectionQuery = "";

		coinfectionQuery=WumSqlQuery.COINFECTION_INV_LIST_SQL;

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(coinfectionQuery);
			preparedStmt.setLong(1, mprUid.longValue());
			preparedStmt.setString(2, conditionCd);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
					CoinfectionSummaryVO.class, coinfectionInvList);
		} catch (SQLException se) {
			logger.fatal(
					"Error: SQLException while getting coinfection Inv List "
							+ conditionCd + " in workup"+se.getMessage(), se);
			throw new NEDSSAppException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal(
					"Error: Exception while getting coinfection Inv List for "
							+ conditionCd + " in workup"+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return coinfectionInvList;
	}
	/**
	 * Get the list of Investigations sharing the same co_infection_ID
	 * @param public_health_case_uid
	 * @return
	 * @throws NEDSSAppException
	 */
	public ArrayList<Object> getSpecificCoinfectionInvList(Long public_health_case_uid) throws NEDSSAppException {
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ResultSetMetaData resultSetMetaData = null;
		String coinfectionQuery = WumSqlQuery.COINFECTION_SPECIFIED_INV_LIST_SQL;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(coinfectionQuery);
			preparedStmt.setLong(1, public_health_case_uid.longValue());
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, CoinfectionSummaryVO.class,
					coinfectionInvList);
		} catch (SQLException se) {
			logger.fatal("Error: SQLException while getting coinfection Inv List assoc with PHC  "
					+ public_health_case_uid + " in workup" + se.getMessage(), se);
			throw new NEDSSAppException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal("Error: Exception while getting coinfection Inv List assoc with PHC  " + public_health_case_uid
					+ " in workup" + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return coinfectionInvList;
	}
	
	public ArrayList<Object> getSpecificCoinfectionInvListPHC(Long public_health_case_uid) throws NEDSSAppException {
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ResultSetMetaData resultSetMetaData = null;
		String coinfectionQuery = WumSqlQuery.COINFECTION_SPECIFIED_INV_LIST_SQL_PHC;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(coinfectionQuery);
			preparedStmt.setLong(1, public_health_case_uid.longValue());
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, CoinfectionSummaryVO.class,
					coinfectionInvList);
		} catch (SQLException se) {
			logger.fatal(
					"Error: SQLException while getting coinfection Inv List assoc with PHC (getSpecificCoinfectionInvListPHC) "
							+ public_health_case_uid + " in workup" + se.getMessage(),
					se);
			throw new NEDSSAppException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal(
					"Error: Exception while getting coinfection Inv List assoc with PHC (getSpecificCoinfectionInvListPHC) "
							+ public_health_case_uid + " in workup" + ex.getMessage(),
					ex);
			throw new NEDSSAppException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return coinfectionInvList;
	}
	
	public Integer getSpecificCoinfectionInvListCount(Long public_health_case_uid) throws NEDSSAppException {
		Integer count = null;
		String coinfectionQuery = WumSqlQuery.COINFECTION_SPECIFIED_INV_LIST_SQL_COUNT;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		logger.debug("Begin execution of getSpecificCoinfectionInvListCount ");
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(coinfectionQuery);
			preparedStmt.setLong(1, public_health_case_uid.longValue());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			logger.debug("End execution of getSpecificCoinfectionInvListCount ");
			logger.info("count = " + count);

			return count;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	
	public ArrayList<Object> getOpenInvList(Long mprUid,
			String conditionCd) throws NEDSSAppException {
		ArrayList<Object> openInvList = new ArrayList<Object>();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ResultSetMetaData resultSetMetaData = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(WumSqlQuery.OPEN_INV_LIST);
			preparedStmt.setLong(1, mprUid.longValue());
			preparedStmt.setString(2, conditionCd);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
					CoinfectionSummaryVO.class, openInvList);
		} catch (SQLException se) {
			logger.fatal(
					"Error: SQLException while getting Open Inv List "
							+ conditionCd + " in workup"+se.getMessage(), se);
			throw new NEDSSAppException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal(
					"Error: Exception while getting open Inv List for "
							+ conditionCd + " in workup"+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return openInvList;
	}
	
	public ArrayList<Object> getInvListForCoInfectionId(Long mprUid,String coInfectionId) throws NEDSSAppException {
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ResultSetMetaData resultSetMetaData = null;
		String coinfectionQuery = "";
		coinfectionQuery=WumSqlQuery.COINFECTION_INV_LIST_FOR_GIVEN_COINFECTION_ID_SQL;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(coinfectionQuery);
			preparedStmt.setString(1, coInfectionId);
			preparedStmt.setLong(2, mprUid.longValue());
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
					CoinfectionSummaryVO.class, coinfectionInvList);
		} catch (SQLException se) {
			logger.fatal(
					"FATAL ERROR:SQLException: SQLException while getInvListForCoInfectionId for MPRuid:"+mprUid+" for coinfectionId " + coInfectionId + " in retrieveSummaryVO"+se.getMessage(), se);
			throw new NEDSSAppException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal(
					"FATAL ERROR:Exception: Exception while getInvListForCoInfectionId for MPRuid:"+mprUid+" for coinfectionId " + coInfectionId + " in retrieveSummaryVO"+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return coinfectionInvList;
	}
	
	/**
	 * Merge investigation cases that require special check for cases that are not logically deleted
	 * @param mprUid
	 * @param coInfectionId
	 * @return
	 * @throws NEDSSAppException
	 */
	public ArrayList<Object> getInvListForMergeCoInfectionId(Long mprUid,String coInfectionId) throws NEDSSAppException {
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ResultSetMetaData resultSetMetaData = null;
		String coinfectionQuery = "";
		coinfectionQuery=WumSqlQuery.MERGE_COINFECTION_INV_LIST_FOR_GIVEN_COINFECTION_ID_SQL;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(coinfectionQuery);
			preparedStmt.setString(1, coInfectionId);
			preparedStmt.setLong(2, mprUid.longValue());
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
					CoinfectionSummaryVO.class, coinfectionInvList);
		} catch (SQLException se) {
			logger.fatal(
					"FATAL ERROR:SQLException: SQLException while getInvListForCoInfectionId for MPRuid:"+mprUid+" for coinfectionId " + coInfectionId + " in retrieveSummaryVO"+se.getMessage(), se);
			throw new NEDSSAppException(se.getMessage());
		} catch (Exception ex) {
			logger.fatal(
					"FATAL ERROR:Exception: Exception while getInvListForCoInfectionId for MPRuid:"+mprUid+" for coinfectionId " + coInfectionId + " in retrieveSummaryVO"+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return coinfectionInvList;
	}
		  
}