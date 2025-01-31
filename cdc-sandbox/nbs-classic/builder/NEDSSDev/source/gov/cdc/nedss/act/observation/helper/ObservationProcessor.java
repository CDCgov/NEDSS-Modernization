package gov.cdc.nedss.act.observation.helper;

/**
 * Title:        ObservationProcessor
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corp
 * @author NEDSS development team
 * @version 1.0
 */
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.observation.dt.CodedResultDT;
import gov.cdc.nedss.entity.observation.dt.LoincResultDT;
import gov.cdc.nedss.entity.observation.dt.ObservationNameDT;
import gov.cdc.nedss.entity.observation.util.DisplayObservationList;
import gov.cdc.nedss.entity.observation.vo.LoincSearchResultTmp;
import gov.cdc.nedss.entity.observation.vo.LoincSrchResultVO;
import gov.cdc.nedss.entity.observation.vo.ObservationSearchResultTmp;
import gov.cdc.nedss.entity.observation.vo.ObservationSrchResultVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationNameDAOImpl;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportResultedtestSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportConditionSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationOrganizationInfoVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationPersonInfoVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ProviderDataForPrintVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ResultedTestSummaryVO;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LabResultDAO;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.GenericSummaryVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.UidSummaryVO;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class ObservationProcessor
    extends DAOBase {

  private static final LogUtils logger = new LogUtils(ObservationProcessor.class.
      getName());
  private final String NEXT = "NEXT";
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  private boolean programAreaDerivationExcludeFlag = false;  
  
  public ObservationProcessor() {
	//	 propertyUtil propertyUtil = propertyUtil.getInstance();
  }
//This method is added in place of old method retrieveMorbReportSummary() to improve the performance of the page
  
  @SuppressWarnings("unchecked")
public HashMap<Object, Object> retrieveMorbReportSummaryRevisited(Collection<Object> uidList, boolean isCDCPrintForm,
          NBSSecurityObj nbsSecurityObj, String uidType){
	  	Long providerUid = null;
	    ArrayList<Object>  labReportUids = new ArrayList<Object> ();
	    ArrayList<Object>  treatmentUids = new ArrayList<Object> ();
	    ArrayList<Object>  morbReportSummaryVOColl = new ArrayList<Object> ();
	    ArrayList<Object>  morbReportEventVOColl = new ArrayList<Object> ();
	    MorbReportSummaryVO morbVO = new MorbReportSummaryVO();
	    ObservationSummaryDAOImpl osd = new ObservationSummaryDAOImpl();
	    RetrieveSummaryVO rSummaryVO = new RetrieveSummaryVO();
	    
	    MorbReportSummaryVO morbReportVO = new MorbReportSummaryVO();
	    ArrayList<Object>  morbArray = new ArrayList<Object> ();
	    ArrayList<Object>  argList = new ArrayList<Object> ();
	    Long uid = null;

	    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
	            NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW);
        if (dataAccessWhereClause == null) {
          dataAccessWhereClause = "";
        }
        else {
          dataAccessWhereClause = " AND " + dataAccessWhereClause;

        }
	    	    
	    try {
	      Iterator<Object> iterator = uidList.iterator();
	      Timestamp fromTime = null;
	      Long MorbUidAsSourceForInvestigation = null;
	      while (iterator.hasNext()) {
	    	  if(uidType.equals("PERSON_PARENT_UID")){

	       // Long morbidityUid = ( (UidSummaryVO) morbIterator.next()).getUid();
	    	uid = (Long) iterator.next();
	        //ArrayList<Object> argList = new ArrayList<Object> ();
	    	 argList.clear();
	        argList.add(uid);
	        //ArrayList<Object> morbArray = new ArrayList<Object> ();
	        String selQuery = WumSqlQuery.SELECT_MORBSUMMARY_FORWORKUPNEW+ dataAccessWhereClause;
	        morbArray = (ArrayList<Object> ) preparedStmtMethod(morbVO, argList,
	        		selQuery, NEDSSConstants.SELECT);
	    	  }else if(uidType.equals("MORBIDITY_UID"))
		    	 {
	    		  UidSummaryVO uidVO = (UidSummaryVO) iterator.next();
	    		  Long morbidityUid = uidVO.getUid();
		    	  fromTime = uidVO.getAddTime();
		    	  if(uidVO.getStatusTime()!=null && fromTime != null && fromTime.equals(uidVO.getStatusTime())){
		    		  MorbUidAsSourceForInvestigation=morbidityUid;
		    	  }
		    	 	argList.clear();
	    	        argList.add(morbidityUid);
	    	        //ArrayList<Object> morbArray = new ArrayList<Object> ();
	    	        morbArray = (ArrayList<Object> ) preparedStmtMethod(morbVO, argList,
	    	            WumSqlQuery.SELECT_MORBSUMMARY_FORWORKUP, NEDSSConstants.SELECT);
		    	 }


	        if (morbArray != null) {
	          Iterator<Object> morbReportSummaryIter = morbArray.iterator();
	          while (morbReportSummaryIter.hasNext()) {
	        	  morbReportVO = (MorbReportSummaryVO) morbReportSummaryIter.
	                next();
	        	morbReportVO.setActivityFromTime(fromTime);
	            Long morbidityUid = morbReportVO.getObservationUid();
	            ArrayList<Object>  rptArgs = new ArrayList<Object> ();
	            ArrayList<Object>  rpt = new ArrayList<Object> ();
	            String setValue = "";
	            MorbReportSummaryVO morbReportSummaryVO = null;
	    	    MorbReportSummaryVO morbReportEventVO = null;
	            MorbReportSummaryVO rptValue = new MorbReportSummaryVO();
	            rptArgs.add(morbidityUid);
	            ArrayList<Object>  providerDetails = osd.getProviderInfo(morbidityUid,"PhysicianOfMorb");
	            
	            Map<Object,Object> associationsMap = rSummaryVO.getAssociatedInvList(morbidityUid,nbsSecurityObj, "OBS");
	            morbReportVO.setAssociationsMap(associationsMap);
	            
	            
				getProviderInformation(providerDetails, morbReportVO);
				
				ArrayList<Object>  facilityDetailsDRRQ = osd.getReportingFacilityInfo(morbidityUid,NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT);
				 if(facilityDetailsDRRQ!=null && facilityDetailsDRRQ.size()>0 && facilityDetailsDRRQ.get(0)!=null && morbReportVO != null){
					 morbReportVO.setReportingFacility(facilityDetailsDRRQ.get(0).toString());
				}
				
	              if(isCDCPrintForm){
	            	  ProviderDataForPrintVO providerDataForPrintVO  = new ProviderDataForPrintVO();
	            	  morbReportVO.setProviderDataForPrintVO(providerDataForPrintVO);
	            	  if(providerUid!=null && MorbUidAsSourceForInvestigation !=null){
	            		  osd.getOrderingPersonAddress(providerDataForPrintVO, providerUid);
                    	  osd.getOrderingPersonPhone(providerDataForPrintVO, providerUid);
	            	  }
	            	  if(MorbUidAsSourceForInvestigation!=null){
		            	  Long reportingFacilityUid=null;
		            	  ArrayList<Object>  facilityDetails = osd.getReportingFacilityInfo(morbidityUid,NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT);
		            	  if(facilityDetails!=null && facilityDetails.size()>0 && morbReportVO != null){
		            		  Object[] facility = facilityDetails.toArray();
		            		  if (facility[0] != null) {
			                	  morbReportVO.getProviderDataForPrintVO().setFacility((String) facility[0]);
			                    logger.debug("FacilityName: " + (String) facility[0]);
			                  }
			                  if (facility[1] != null)
			                	  reportingFacilityUid =((Long) facility[1]);
			                  logger.debug("FacilityUid: " + (Long) facility[1]);
			                  
			                  if(reportingFacilityUid!=null){
			                	  osd.getOrderingFacilityAddress(providerDataForPrintVO, reportingFacilityUid);
			                	  osd.getOrderingFacilityPhone(providerDataForPrintVO, reportingFacilityUid);			                	  
			                  }
		            	  }
	            	  }
	            	  
		            }
	            // separate the event Morb and the summary morb.
	            if(uidType.equals("PERSON_PARENT_UID"))
	            {
		            if (morbReportVO.getRecordStatusCd()!=null && (morbReportVO.getRecordStatusCd().equals("UNPROCESSED"))) {
		            	
		            	morbReportSummaryVO = morbReportVO ;
	        			morbReportSummaryVO.setMPRUid(uid);
	        		}
	        		if(morbReportVO.getRecordStatusCd()!=null && !morbReportVO.getRecordStatusCd().equals("LOG_DEL")){
	        			
	        			morbReportEventVO = morbReportVO ;
	        			morbReportEventVO.setMPRUid(uid);
	        		}
	            }else if(uidType.equals("MORBIDITY_UID"))
	            {
	            	ArrayList<Object>  mprUidList = osd.getPatientPersonUid(NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT,morbidityUid);
	                 if(mprUidList != null && mprUidList.size()>0)
	                 {
	                	 Object[] vals = mprUidList.toArray();
	                	 if(morbReportVO.getRecordStatusCd()!=null && (morbReportVO.getRecordStatusCd().equals("UNPROCESSED")))
	                	 {
	                		 morbReportSummaryVO = null;
	                		 morbReportSummaryVO = morbReportVO ;
	                		 morbReportSummaryVO.setMPRUid((Long)vals[0]);
	                	 }
	                	 if(morbReportVO.getRecordStatusCd()!=null && !morbReportVO.getRecordStatusCd().equals("LOG_DEL")){
	                		morbReportEventVO = null; 
	 	        			morbReportEventVO = morbReportVO ;
	 	        			morbReportEventVO.setMPRUid((Long)vals[0]);
	 	        		}	 
	                    
	                 }
	            }

	            
	            
	            rpt = (ArrayList<Object> ) preparedStmtMethod(rptValue, rptArgs,
	                                                 WumSqlQuery.SELECT_REPORT_TYPE,
	                                                 NEDSSConstants.SELECT);

	            if (rpt != null) {
	              Iterator<Object> rptValueIter = rpt.iterator();
	              while (rptValueIter.hasNext()) {
	                rptValue = (MorbReportSummaryVO) rptValueIter.next();
	                setValue = rptValue.getReportType();
	              }
	            }
	           if(morbReportSummaryVO != null) 
	        	   morbReportSummaryVO.setReportType(setValue);
	           if(morbReportEventVO != null) 
	        	   morbReportEventVO.setReportType(setValue);


	            ArrayList<Object>  valList = osd.getPatientPersonInfoMorb(morbidityUid);
	            

	            if(morbReportSummaryVO !=null)
	            {
	            	morbReportSummaryVOColl.add(morbReportSummaryVO);
	            }
	            if(morbReportEventVO !=null)
	            	morbReportEventVOColl.add(morbReportEventVO);

	            /****** TREATMENT SUMMARY VO'S ******/

	            UidSummaryVO treatmentUid = new UidSummaryVO();
	            argList.clear();
	            argList.add(morbidityUid);
	            treatmentUids = (ArrayList<Object> ) preparedStmtMethod(treatmentUid,
	                argList, WumSqlQuery.SELECT_TREATMENT_TARGETS,
	                NEDSSConstants.SELECT);

	            Iterator<Object> iter = treatmentUids.iterator();
	            ArrayList<Object>  treatmentColl = new ArrayList<Object> ();
	            while (iter.hasNext()) {
	              UidSummaryVO vo = (UidSummaryVO) iter.next();
	              Long obsUid = vo.getUid();
	              treatmentColl.add(obsUid);

	            }
	            RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
	            Collection<Object> treatmentSummaryVOs = new ArrayList<Object> (retrieveSummaryVO.
	                retrieveTreatmentSummaryVOList(treatmentColl, nbsSecurityObj));
	            if(treatmentSummaryVOs != null && treatmentSummaryVOs.size()>0 && morbReportSummaryVO != null && (morbReportSummaryVO.getObservationUid() == morbidityUid))
	            	morbReportSummaryVO.setTheTreatmentSummaryVOColl(treatmentSummaryVOs);
	            if(treatmentSummaryVOs != null && treatmentSummaryVOs.size()>0 && morbReportEventVO != null && (morbReportEventVO.getObservationUid() == morbidityUid))
	            	morbReportEventVO.setTheTreatmentSummaryVOColl(treatmentSummaryVOs);
	            UidSummaryVO labReportUid = new UidSummaryVO();
	            argList.clear();
	            argList.add(morbidityUid);
	            labReportUids = (ArrayList<Object> ) preparedStmtMethod(labReportUid,
	                argList, WumSqlQuery.SELECT_LAB_TARGETS, NEDSSConstants.SELECT);
	            LabReportSummaryVO labReportSummaryVOs = new LabReportSummaryVO();
	            Collection<Object> newLabReportSummaryVOCollection  = new ArrayList<Object> ();
	            Collection<Object> labReportSummaryVOCollection  = new ArrayList<Object> ();
	            Collection<Object> labColl = new ArrayList<Object> ();
	            HashMap<Object, Object> labReportMap = new HashMap<Object, Object>();
	            /*If the user has permission to see the Lab */
	            String labUidType = "LABORATORY_UID";
	            if(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW))
	            { labReportMap = retrieveLabReportSummaryRevisited(labReportUids, false, nbsSecurityObj, labUidType);

	           
	            }
	            
	            
	            if(!labReportMap.isEmpty())
	              {
	            	  if(labReportMap.containsKey("labSummList") && morbReportSummaryVO != null)
	            	  {
	            		  newLabReportSummaryVOCollection  = (ArrayList<Object> )labReportMap.get("labSummList");
	            		  Iterator<Object> ite = newLabReportSummaryVOCollection.iterator();
	            		  while( ite.hasNext())
	            		  {
	            			 labReportSummaryVOs = (LabReportSummaryVO) ite. next();
	            			 labColl.add(labReportSummaryVOs);
	            			 morbReportSummaryVO.setTheLabReportSummaryVOColl(labColl);
	            		  }
	            	  }
	            	  if(labReportMap.containsKey("labEventList") && morbReportEventVO != null)
	            	  {
	            		  labReportSummaryVOCollection  =(ArrayList<Object> )labReportMap.get("labEventList");
	            		  Iterator<Object> ite = labReportSummaryVOCollection.iterator();
	            		  while( ite.hasNext())
	            		  {
	            			  labReportSummaryVOs = (LabReportSummaryVO) ite.next();
	            			  labColl.add(labReportSummaryVOs);
	            			  morbReportEventVO.setTheLabReportSummaryVOColl(labColl);
	            			  
	            		  }
	            	  }
	              }
	           
	            /****** END OF LAB REPORT SUMMAY VO'S *****/
	          }
	        }
	      }
	    }
	    catch (Exception ex) {
	      logger.fatal(
	          "Error while getting observations(morb) for given set of UIDS in workup",
	          ex);
	      throw new NEDSSSystemException(ex.toString());
	    }
	    this.populateDescTxtFromCachedValues(morbReportEventVOColl);
	    this.populateDescTxtFromCachedValues(morbReportSummaryVOColl);
	    HashMap<Object, Object> returnMap = new HashMap<Object, Object>();
	    returnMap.put("MorbSummColl", morbReportSummaryVOColl);
	    returnMap.put("MorbEventColl", morbReportEventVOColl);
	    return returnMap; 	  
  }
  

  
  
  /**
   * Goes to the database to obtain the Program Area Codes based on the specified parameters.
   * @param codeVector : Vector
   * @param reportingLabCLIA : String
   * @param nextLookUp : String
   * @param type : String - LN for Loinc, SNM for Snomed, LT for Local Test and LR for local result.
   * @return String
   *
   */
  // AK 7/25/04
  private String getProgAreaCd(Vector<Object> codeVector, String reportingLabCLIA,
                               String nextLookUp, String type) {

    if (codeVector == null || codeVector.size() == 0)
      return null;

    ArrayList<Object>  progAreaCdList = null;
    Vector<Object> toReturn = new Vector<Object>();
    SRTMapDAOImpl dao = new SRTMapDAOImpl();
    String lastPACode = null;

    try {
      for (int k = 0; k < codeVector.size(); k++) {
        progAreaCdList = (ArrayList<Object> ) dao.getProgAreaCd( (String) codeVector.
            elementAt(k), type, reportingLabCLIA);

        // The above method returns the count of PAs found at
        // index 1 and program area at index 0

        // Return null if we got more than one PA
        int count = ( (Integer) progAreaCdList.get(1)).intValue();
        if (count != 1)
          return null;

        String currentPAcode = progAreaCdList.get(0).toString();

        // Compare with previously retrieved PA and return null if they are different.
        if (lastPACode == null)
          lastPACode = currentPAcode;
        else if (!currentPAcode.equals(lastPACode))
          return null;
      } //end of for
    }
    catch (Exception e) {
      e.printStackTrace();
      return null; //break out
    } //end of catch
    return lastPACode;
  } //end of getProgAreaCd()

  /**
   * Returns the code that will be used to help resolve the program area cd
   * @param obsDt : ObservationDT
   * @return code : String
   */
  private String getLocalTestCode(ObservationDT obsDt)
  {
    String code = null;
    if (obsDt != null)
    {
      if (obsDt.getCdSystemCd() != null)
      {
        if (obsDt.getCd() != null && !obsDt.getCd().equals("") &&
            !obsDt.getCd().equals(" "))
        {
          code = obsDt.getCd();
        }
      } //end of if
    } //end of if
    return code;
  } //end of getLocalTestColl

  /**
   * Attempts to resolve the Program Area Cd based on the Local Default Cd.  If
   * more than one type of Program Area Cd is resolved, then return null.  If no
   * Program Area Cd is resolved, return nextLookup value.
   * @param codeVector : Vector
   * @param reportingLabCLIA : String
   * @param nextLookup : String
   * @param sql : String
   * @return String
   */
  // AK 7/25/03
  private String getProgAreaCdLocalDefault(Vector<Object> codeVector,
                                           String reportingLabCLIA,
                                           String nextLookup, String sql) {
    Vector<Object> toReturn = new Vector<Object>();
    SRTMapDAOImpl dao = new SRTMapDAOImpl();
    String lastPACode = null;
    try {
      for (int k = 0; k < codeVector.size(); k++) {
        Collection<Object> defaultPACColl = dao.getProgAreaCdLocalDefault(
            codeVector.elementAt(k).toString(),
            reportingLabCLIA, sql);
        if (defaultPACColl.size() == 1) {
          String currentPACode = defaultPACColl.iterator().next().toString();
          // Compare with previously retrieved PA and return null if they are different.
          if (lastPACode == null)
            lastPACode = currentPACode;
          else if (!currentPACode.equals(lastPACode))
            return null;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return null; //????leave observation.progAreaCd == null?????
    } //end of catch
    return lastPACode;
  } //end of getProgAreaCdLocalDefault(...)

  public String getTestCode(String TestNameCollection, String labCLIA,
                            Long labUid,
                            String programAreaCd) throws NEDSSSystemException {
    String code = null;
    return code;
  }


  public boolean getOrganismReqdIndicatorForResultedTest(String
      testNameCondition,
      String labClia,
      Long labUid,
      String programAreaCd) {
    //String rtTestCode = getTestCodeForResultedTest(testNameCondition,
    //    labClia,
    //    labUid,
    //    programAreaCd);

    if (testNameCondition == null || testNameCondition.trim().length() == 0)
      return false;

    String indicator = new SRTMapDAOImpl().
        getOrganismReqdIndicatorForResultedTest(testNameCondition);

    if (indicator != null && indicator.equalsIgnoreCase(NEDSSConstants.YES)) {
      return true;
    }
    else {
      return false;
    }
  }

  public ArrayList<Object>  findOrderedTestName(String clia, Long labId,
                                       String searchString, String testType,
                                       int cacheNumber, int fromIndex) {
    ArrayList<Object>  resultList = new ArrayList<Object> ();
    StringBuffer query = new StringBuffer();
    EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();

    if ( labId != null && (clia == null || clia.equals("")) ) {
      clia = entityProxyHelper.organizationCLIALookup(labId);
    }
    if (clia == null || clia.equals(""))
      clia = NEDSSConstants.DEFAULT;

    if (testType.equals(NEDSSConstants.TEST_TYPE_LOCAL)) {
        String newQuery = NEDSSSqlQuery.SELECT_ORDERED_TEST_SEARCH_SQL;
        String where = " and ( upper(LAB_TEST_DESC_TXT)  like '%" +
            searchString.toUpperCase() +
            "%' or upper(LAB_TEST_CD)  like '%" +
            searchString.toUpperCase() +
            "%') and LABORATORY_ID = '" + clia + "' order by LAB_TEST_DESC_TXT";
        query.append(newQuery);
        query.append(where);

        resultList = findObservationByName(cacheNumber, fromIndex, query,
                                           "ordered");
    }
    else if (testType.equals(NEDSSConstants.TEST_TYPE_LOINC)) {
         String newQuery = NEDSSSqlQuery.SELECT_RESULTED_ORDERED_LOINC_TEST_SEARCH_SQL;
         String where = "and ( upper( Loinc_code.component_name)  like '%" +
             searchString.toUpperCase() +
             "%' OR upper( Loinc_code.loinc_cd)  like '%" +
             searchString.toUpperCase() +
             "%') order by Loinc_code.component_name";
         query.append(newQuery);
         query.append(where);
         //System.out.println(query.toString());
         resultList = findLoincObservationByName(cacheNumber, fromIndex, query,
                                                 "ordered");
    }

    return resultList;
  }

  public ArrayList<Object>  findResultedTestName(String clia, Long labId,
                                        String searchString, String testType,
                                        int cacheNumber, int fromIndex) {
    ArrayList<Object>  resultList = new ArrayList<Object> ();
    StringBuffer query = new StringBuffer();
    EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
    //BB - 12/14/04 - civil00012111 - Add check for null labId and else condition
    //to set clia to DEFAULT
    if ( labId != null && (clia == null || clia.equals("")) ) {
      clia = entityProxyHelper.organizationCLIALookup(labId);
    }

    if (clia == null || clia.equals(""))
      clia = NEDSSConstants.DEFAULT;

    if (testType.equals(NEDSSConstants.TEST_TYPE_LOCAL)) {
        String newQuery = NEDSSSqlQuery.SELECT_RESULTED_TEST_SEARCH_SQL;
        String where = " and ( upper(LAB_TEST_DESC_TXT)  like '%" +
            searchString.toUpperCase() +
            "%') and LABORATORY_ID = '" + clia + "' order by LAB_TEST_DESC_TXT";
        query.append(newQuery);
        query.append(where);
        resultList = findObservationByName(cacheNumber, fromIndex, query,"resulted");
    }
    else if (testType.equals(NEDSSConstants.TEST_TYPE_LOINC)) {
        String newQuery = NEDSSSqlQuery.SELECT_RESULTED_ORDERED_LOINC_TEST_SEARCH_SQL;
        String where = "and ( upper( Loinc_code.component_name)  like '%" +
            searchString.toUpperCase() +
            "%') order by Loinc_code.component_name";
        query.append(newQuery);
        query.append(where);
        resultList = findLoincObservationByName(cacheNumber, fromIndex, query,
                                                "resulted");
    }

    return resultList;
  }
  
  public String findCodeSetGroupIdFromQuestionIdentifier(String questionIdentifier){
	  
    LabResultDAO dao = new LabResultDAO();
    String codeSetGroupId = dao.findCodeSetGroupIdFromQuestionIdentifier(questionIdentifier);
    
    return codeSetGroupId;
  }
  
	public ArrayList<Object> findLabResultedTestName(String clia, Long labId,
			String searchString, String testType) {
		ArrayList<Object> resultList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
		if (labId != null && (clia == null || clia.equals(""))) {
			clia = entityProxyHelper.organizationCLIALookup(labId);
		}

		if (clia == null || clia.equals(""))
			clia = NEDSSConstants.DEFAULT;

		if (testType.equals(NEDSSConstants.TEST_TYPE_LOCAL)) {
			 {
				String newQuery = NEDSSSqlQuery.SELECT_RESULTED_TEST_SEARCH_SQL;
				String where = " and ( upper(LAB_TEST_DESC_TXT)  like '%"
						+ searchString.toUpperCase()
						+ "%'  OR upper(LAB_TEST_CD) LIKE  '%"
						+ searchString.toUpperCase()
						+ "%') and LABORATORY_ID = '" + clia
						+ "' order by LAB_TEST_DESC_TXT";
				query.append(newQuery);
				query.append(where);
				resultList = findObservationByName(query, "resulted");

			}
		} else if (testType.equals(NEDSSConstants.TEST_TYPE_LOINC)) {
				String newQuery = NEDSSSqlQuery.SELECT_RESULTED_ORDERED_LOINC_TEST_SEARCH_SQL;
				String where = "and ( upper( Loinc_code.component_name)  like '%"
						+ searchString.toUpperCase()
						+ "%' OR  upper(loinc_code.loinc_cd) like '%"
						+ searchString.toUpperCase()
						+ "%') order by Loinc_code.component_name";
				query.append(newQuery);
				query.append(where);
				resultList = findLoincObservationByName(query, "resulted");

			}
	
		return resultList;
	}
	
	public ArrayList<Object> findLabCodedResult(String clia, Long labId,
			String searchString, String testType) {
		ArrayList<Object> resultList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
		if (labId != null && (clia == null || clia.equals(""))) {
			clia = entityProxyHelper.organizationCLIALookup(labId);
		}

		if (clia == null || clia.equals(""))
			clia = NEDSSConstants.DEFAULT;

		if (testType.equals(NEDSSConstants.RESULT_TYPE_LOCAL)) {
			
				String newQuery = NEDSSSqlQuery.SELECT_CODED_RESULT_SEARCH_SQL;
				String where = " and ( upper(LAB_RESULT_DESC_TXT)  like '%"
						+ searchString.toUpperCase()
						+ "%' OR upper(LAB_RESULT_CD) like '%"+searchString.toUpperCase()+"%') and LABORATORY_ID = '" + clia
						+ "' order by LAB_RESULT_DESC_TXT";
				query.append(newQuery);
				query.append(where);
				resultList = findCodedResultByName(query, "resulted");

		} else if (testType.equals(NEDSSConstants.RESULT_TYPE_SNOMED)) {
			String newQuery = NEDSSSqlQuery.SELECT_CODED_RESULT_SNOMED_SEARCH_SQL;
				String where = " ( upper( snomed_desc_txt)  like '%"
						+ searchString.toUpperCase()
						+ "%' or upper(snomed_cd) like '%"+searchString.toUpperCase()+"%') order by snomed_desc_txt";
				query.append(newQuery);
				query.append(where);
				resultList = findCodedResultByName(query, "resulted");
		}

		return resultList;
	}
	
	public ArrayList<Object> findLabCodedResultByCode(String clia, Long labId,
			String searchCode, String testType) {
		ArrayList<Object> resultList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
		if (labId != null && (clia == null || clia.equals(""))) {
			clia = entityProxyHelper.organizationCLIALookup(labId);
		}

		if (clia == null || clia.equals(""))
			clia = NEDSSConstants.DEFAULT;

		if (testType == null || testType.equals(NEDSSConstants.RESULT_TYPE_LOCAL)) {
			
				String newQuery = NEDSSSqlQuery.SELECT_CODED_RESULT_SEARCH_SQL;
				String where = " and ( lab_result_cd = '"
						+ searchCode
						+ "') and LABORATORY_ID = '" + clia
						+ "' order by LAB_RESULT_DESC_TXT";
				query.append(newQuery);
				query.append(where);
				resultList.addAll(findCodedResultByName(query, "resulted"));

		} 
		if (testType == null || testType.equals(NEDSSConstants.RESULT_TYPE_SNOMED)) {
			query = new StringBuffer();
			
				String newQuery = NEDSSSqlQuery.SELECT_CODED_RESULT_SNOMED_SEARCH_SQL;
				String where = " ( snomed_cd = '"
						+ searchCode
						+ "') order by snomed_desc_txt";
				query.append(newQuery);
				query.append(where);
				resultList.addAll(findCodedResultByName(query, "resulted"));

		}

		return resultList;
	}
  
	public ArrayList<Object> findLabResultedTestByCode(String clia, Long labId,
			String searchCode, String testType) {
		ArrayList<Object> resultList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
		if (labId != null && (clia == null || clia.equals(""))) {
			clia = entityProxyHelper.organizationCLIALookup(labId);
		}

		if (clia == null || clia.equals(""))
			clia = NEDSSConstants.DEFAULT;

		if (testType == null || testType.equals(NEDSSConstants.TEST_TYPE_LOCAL)) {
			
				String newQuery = NEDSSSqlQuery.SELECT_RESULTED_TEST_SEARCH_SQL;
				String where = " and ( LAB_TEST_CD  = '"
						+ searchCode
						+ "') and LABORATORY_ID = '" + clia
						+ "' order by LAB_TEST_DESC_TXT";
				query.append(newQuery);
				query.append(where);
				resultList.addAll(findObservationByName(query, "resulted"));
	} 
		if (testType == null || testType.equals(NEDSSConstants.TEST_TYPE_LOINC)) {
			query = new StringBuffer();
			
				String newQuery = NEDSSSqlQuery.SELECT_RESULTED_ORDERED_LOINC_TEST_SEARCH_SQL;
				String where = "and ( Loinc_code.loinc_cd  = '"
						+ searchCode
						+ "') order by Loinc_code.component_name";
				query.append(newQuery);
				query.append(where);
				resultList.addAll(findLoincObservationByName(query, "resulted"));

		}

		return resultList;
	}

  public ArrayList<Object>  findDrugsByName(String clia, Long labId, String searchString,
                                   String testType, String method,
                                   int cacheNumber, int fromIndex) {

    ArrayList<Object>  resultList = new ArrayList<Object> ();
    StringBuffer query = new StringBuffer();
    EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();

    if (clia!=null && (clia.equals("") || clia == null)) {
      clia = entityProxyHelper.organizationCLIALookup(labId);
    }

    if (testType.equals(NEDSSConstants.TEST_TYPE_LOCAL)) {
      
        String newQuery = NEDSSSqlQuery.SELECT_DRUG_SEARCH_SQL;
        String where = " and ( upper( lab_test.LAB_TEST_DESC_TXT)  like '%" +
            searchString.toUpperCase() +
            "%')" + //removed 10/11/04, BB:and Lab_coding_system.laboratory_id = '" + clia +"'"+
            " order by LAB_TEST_DESC_TXT";
        query.append(newQuery);
        query.append(where);
        resultList = findObservationByName(cacheNumber, fromIndex, query,
                                           "drug");

    }
    else if (testType.equals(NEDSSConstants.TEST_TYPE_LOINC)) {
      String meth = "";
      String newQuery;
      
        newQuery = NEDSSSqlQuery.SELECT_DRUG_LOINC_SEARCH_SQL;
        String where = "and ( upper( Loinc_code.component_name)  like '%" +
        searchString.toUpperCase() + "%') ";
      query.append(newQuery);
      query.append(where);
      method = method.trim();

      if ( (! (method.equals("")))) {
        meth = " and Loinc_code.method_type='" + method.toUpperCase() + "'  order by Loinc_code.component_name";
      }
      else {
        meth = " and Loinc_code.method_type IS NULL order by Loinc_code.component_name";
      }
      query.append(meth);
      resultList = findLoincObservationByName(cacheNumber, fromIndex, query,
                                              "drug");
    }
//System.out.println("query-->" + query.toString());
    return resultList;
  }

  public ArrayList<Object>  findDrugsByNameOrCode(String clia, Long labId, String searchString,
          String testType, String method,
          int cacheNumber, int fromIndex) {
		
		ArrayList<Object>  resultList = new ArrayList<Object> ();
		StringBuffer query = new StringBuffer();
		EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
		
		if (clia!=null && (clia.equals("") || clia == null)) {
		clia = entityProxyHelper.organizationCLIALookup(labId);
		}
		
		if (testType.equals(NEDSSConstants.TEST_TYPE_LOCAL)) {
		String newQuery = NEDSSSqlQuery.SELECT_DRUG_SEARCH_SQL;
		String where = " and ( upper( lab_test.LAB_TEST_DESC_TXT)  like '%" +
		searchString.toUpperCase() +
		"%' OR  upper( lab_test.LAB_TEST_CD)  like '%" +
            searchString.toUpperCase() +
            "%')" + //removed 10/11/04, BB:and Lab_coding_system.laboratory_id = '" + clia +"'"+
		" order by LAB_TEST_DESC_TXT";
		query.append(newQuery);
		query.append(where);
		resultList = findObservationByName(cacheNumber, fromIndex, query,
		                  "drug");
		}
		else if (testType.equals(NEDSSConstants.TEST_TYPE_LOINC)) {
		String meth = "";
		String newQuery;
		newQuery = NEDSSSqlQuery.SELECT_DRUG_LOINC_SEARCH_SQL;
		
		String where = "and ( upper( Loinc_code.component_name)  like '%" +
		searchString.toUpperCase() + "%' or upper( Loinc_code.loinc_cd)  like '%" +
		searchString.toUpperCase() + "%') ";
		query.append(newQuery);
		query.append(where);
		method = method.trim();
		
		if ( (! (method.equals("")))) {
		meth = " and Loinc_code.method_type='" + method.toUpperCase() + "'  order by Loinc_code.component_name";
		}
		else {
		meth = " and Loinc_code.method_type IS NULL order by Loinc_code.component_name";
		}
		query.append(meth);
		resultList = findLoincObservationByName(cacheNumber, fromIndex, query,
		                     "drug");
		}
		//System.out.println("query-->" + query.toString());
		return resultList;
}
  
  public ArrayList<Object>  findOrganismsByName(String clia, Long labId,
                                       String searchString, String testType,
                                       int cacheNumber, int fromIndex) {
    ArrayList<Object>  resultList = new ArrayList<Object> ();
    StringBuffer query = new StringBuffer();
    EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
    if (clia.equals("") || clia == null) {
      clia = entityProxyHelper.organizationCLIALookup(labId);
    }
 
      String newQuery = NEDSSSqlQuery.SELECT_ORGANISM_SEARCH_SQL;
      String where = " ((upper(SNOMED_DESC_TXT)  like '%" +
					searchString.toUpperCase() +
					"%') OR ((upper(SNOMED_CD)  like '%" +
					searchString.toUpperCase() +
					"%'))) order by SNOMED_DESC_TXT";
      query.append(newQuery);
      query.append(where);

      resultList = findObservationByName(cacheNumber, fromIndex, query,
                                      "organism");
      return resultList;
  }

  @SuppressWarnings("unchecked")
private ArrayList<Object>  findObservationByName(int cacheNumber, int fromIndex,
                                          StringBuffer query, String type) throws
      NEDSSSystemException {
    ArrayList<Object>  searchResult = new ArrayList<Object> ();
    int totalCount = 0;
    int listCount = 0;
    DisplayObservationList displayObservationList = null;
    ObservationSearchResultTmp searchResultName = new
        ObservationSearchResultTmp();

    //@@ System.out.println("query-----> \n" + query.toString());
    DAOBase dao = new DAOBase();
    ArrayList<Object>  resultList = (ArrayList<Object> ) dao.preparedStmtMethod(searchResultName, null,
        query.toString(), NEDSSConstants.SELECT);

    Iterator<Object> nameItr1 = resultList.iterator();
    CachedDropDownValues cache = new CachedDropDownValues();

    while (nameItr1.hasNext()) {
      ArrayList<Object>  nameList = new ArrayList<Object> ();
      ObservationSrchResultVO srchResultVO = new ObservationSrchResultVO();
      ObservationSearchResultTmp tmp = (ObservationSearchResultTmp) nameItr1.
          next();

			ObservationNameDT nameDT = new ObservationNameDT();

      if (! (tmp.getLaboratoryId() == null)) {

        nameDT.setLaboratoryId(tmp.getLaboratoryId());
        nameDT.setLabTestCd(tmp.getLabTestCd());
        nameDT.setLabTestDescription(tmp.getLabTestDescription());
        nameDT.setLabConditionCd(tmp.getLabConditionCd());
        if (tmp.getLabOrganismIndicator() != null) {
          nameDT.setLabOrganismIndicator(tmp.getLabOrganismIndicator());
        }

        nameList.add(nameDT);
        srchResultVO.setLaboratoryID(nameDT.getLabTestCd());
      }else{
				nameDT.setLaboratoryId("SNM");
				nameDT.setLabTestCd(tmp.getLabTestCd());
				nameDT.setLabTestDescription(tmp.getLabTestDescription());
  			 nameList.add(nameDT);
        srchResultVO.setLaboratoryID(nameDT.getLabTestCd());

			}

      totalCount++;
      srchResultVO.setLabName(type);
      srchResultVO.setPersonNameColl(nameList);
      searchResult.add(srchResultVO);
    } // while (nameItr.hasNext())

    ArrayList<Object>  cacheList = new ArrayList<Object> ();
    for (int j = 0; j < searchResult.size(); j++) {
      ObservationSrchResultVO psvo = new ObservationSrchResultVO();
      psvo = (ObservationSrchResultVO) searchResult.get(j);
      if (fromIndex > searchResult.size()) {
        break;
      }
      if (cacheNumber == listCount) {
        break;
      }
      cacheList.add(searchResult.get(j));
      listCount++;
    }

    ArrayList<Object>  displayList = new ArrayList<Object> ();
		//System.out.println("size-->" + cacheList.size());
    displayObservationList = new DisplayObservationList(totalCount, cacheList,
        fromIndex, listCount);
    displayList.add(displayObservationList);

    return displayList;

  }
  
  @SuppressWarnings("unchecked")
  private ArrayList<Object>  findObservationByName(StringBuffer query, String type) throws
        NEDSSSystemException {
      ArrayList<Object>  searchResult = new ArrayList<Object> ();
      int listCount = 0;
      DisplayObservationList displayObservationList = null;
      ObservationSearchResultTmp searchResultName = new
          ObservationSearchResultTmp();

      //@@ System.out.println("query-----> \n" + query.toString());
      DAOBase dao = new DAOBase();
      ArrayList<Object>  resultList = (ArrayList<Object> ) dao.preparedStmtMethod(searchResultName, null,
          query.toString(), NEDSSConstants.SELECT);

      Iterator<Object> nameItr1 = resultList.iterator();
      ArrayList<Object>  nameList = new ArrayList<Object> ();
      
      while (nameItr1.hasNext()) {
        
        ObservationSearchResultTmp tmp = (ObservationSearchResultTmp) nameItr1.
            next();

  			ObservationNameDT nameDT = new ObservationNameDT();

        if (! (tmp.getLaboratoryId() == null)) {

          nameDT.setLaboratoryId(tmp.getLaboratoryId());
          nameDT.setLabTestCd(tmp.getLabTestCd());
          nameDT.setLabTestDescription(tmp.getLabTestDescription());
          nameDT.setLabConditionCd(tmp.getLabConditionCd());
          if (tmp.getLabOrganismIndicator() != null) {
            nameDT.setLabOrganismIndicator(tmp.getLabOrganismIndicator());
          }

          nameList.add(nameDT);
        }else{
  				nameDT.setLaboratoryId("SNM");
  				nameDT.setLabTestCd(tmp.getLabTestCd());
  				nameDT.setLabTestDescription(tmp.getLabTestDescription());
    			 nameList.add(nameDT);

  			}
      } // while (nameItr.hasNext())

      return nameList;

    }
  
  @SuppressWarnings("unchecked")
  private ArrayList<Object>  findCodedResultByName(StringBuffer query, String type) throws
        NEDSSSystemException {
      ArrayList<Object>  searchResult = new ArrayList<Object> ();
      CodedResultDT codedResultDT = new
    		  CodedResultDT();

      DAOBase dao = new DAOBase();
      searchResult = (ArrayList<Object> ) dao.preparedStmtMethod(codedResultDT, null,
          query.toString(), NEDSSConstants.SELECT);
      
      return searchResult;

      

    }

  @SuppressWarnings("unchecked")
private ArrayList<Object>  findLoincObservationByName(int cacheNumber, int fromIndex,
                                               StringBuffer query, String type) throws
      NEDSSSystemException {
    DAOBase dao = new DAOBase();
    int totalCount = 0;
    int listCount = 0;
    DisplayObservationList displayObservationList = null;
    LoincSearchResultTmp loincResult = new LoincSearchResultTmp();
    //@@ System.out.println("query........." + query);

    ArrayList<Object>  resultList = (ArrayList<Object> ) dao.preparedStmtMethod(loincResult, null,
        query.toString(), NEDSSConstants.SELECT);
    ArrayList<Object>  searchResult = new ArrayList<Object> ();
    Iterator<Object> nameItr1 = resultList.iterator();

    while (nameItr1.hasNext()) {
      ArrayList<Object>  list = new ArrayList<Object> ();
      LoincSearchResultTmp tmp = (LoincSearchResultTmp) nameItr1.next();
      LoincSrchResultVO srchResultVO = new LoincSrchResultVO();
      if (! (tmp.getLoincCd() == null)) {
        LoincResultDT loincDT = new LoincResultDT();

        loincDT.setLoincCd(tmp.getLoincCd());
        loincDT.setLoincComponentName(tmp.getLoincComponentName());
        loincDT.setLoincMethod(tmp.getLoincMethod());
        loincDT.setLoincSystem(tmp.getLoincSystem());
        loincDT.setLoincProperty(tmp.getLoincProperty());
        loincDT.setRelatedClassCd(tmp.getRelatedClassCd());

        if(tmp.getLoincProperty() != null &&
           tmp.getLoincProperty().equalsIgnoreCase("PRID") &&
           tmp.getRelatedClassCd() != null &&
           tmp.getRelatedClassCd().equalsIgnoreCase("MICRO") &&
           tmp.getLoincMethod()!= null){

          String s = tmp.getLoincMethod();
          int length = s.length();
          for(int i=0; i<length;i++)
          {
            char c1 = s.charAt(i);
            if (c1 == 'C') {
              int temp = i +1;
              if( temp != length){
                char c2 = s.charAt(temp);
                if (c2 == 'U'){
                  loincDT.setLabOrganismIndicator("Y");
                  break;
                }
              }

            }
           }
        }
        list.add(loincDT);
        srchResultVO.setLoincCd(tmp.getLoincCd());
      }

      totalCount++;

      srchResultVO.setLabName(type);
      srchResultVO.setLoincColl(list);
      searchResult.add(srchResultVO);
    }

    ArrayList<Object>  cacheList = new ArrayList<Object> ();
    for (int j = 0; j < searchResult.size(); j++) {
      LoincSrchResultVO psvo = new LoincSrchResultVO();
      psvo = (LoincSrchResultVO) searchResult.get(j);
      if (fromIndex > searchResult.size()) {
        break;
      }
      if (cacheNumber == listCount) {
        break;
      }
      cacheList.add(searchResult.get(j));
      listCount++;
    }

    ArrayList<Object>  displayList = new ArrayList<Object> ();
    displayObservationList = new DisplayObservationList(totalCount, cacheList,
        fromIndex, listCount);

    displayList.add(displayObservationList);

    return displayList;
  }
  
  @SuppressWarnings("unchecked")
  private ArrayList<Object>  findLoincObservationByName(StringBuffer query, String type) throws
        NEDSSSystemException {
      DAOBase dao = new DAOBase();
      LoincSearchResultTmp loincResult = new LoincSearchResultTmp();
      //@@ System.out.println("query........." + query);

      ArrayList<Object>  resultList = (ArrayList<Object> ) dao.preparedStmtMethod(loincResult, null,
          query.toString(), NEDSSConstants.SELECT);
      Iterator<Object> nameItr1 = resultList.iterator();

      ArrayList<Object>  list = new ArrayList<Object> ();
      while (nameItr1.hasNext()) {
        
        LoincSearchResultTmp tmp = (LoincSearchResultTmp) nameItr1.next();
        if (! (tmp.getLoincCd() == null)) {
          LoincResultDT loincDT = new LoincResultDT();

          loincDT.setLoincCd(tmp.getLoincCd());
          loincDT.setLoincComponentName(tmp.getLoincComponentName());
          loincDT.setLoincMethod(tmp.getLoincMethod());
          loincDT.setLoincSystem(tmp.getLoincSystem());
          loincDT.setLoincProperty(tmp.getLoincProperty());
          loincDT.setRelatedClassCd(tmp.getRelatedClassCd());

          if(tmp.getLoincProperty() != null &&
             tmp.getLoincProperty().equalsIgnoreCase("PRID") &&
             tmp.getRelatedClassCd() != null &&
             tmp.getRelatedClassCd().equalsIgnoreCase("MICRO") &&
             tmp.getLoincMethod()!= null){

            String s = tmp.getLoincMethod();
            int length = s.length();
            for(int i=0; i<length;i++)
            {
              char c1 = s.charAt(i);
              if (c1 == 'C') {
                int temp = i +1;
                if( temp != length){
                  char c2 = s.charAt(temp);
                  if (c2 == 'U'){
                    loincDT.setLabOrganismIndicator("Y");
                    break;
                  }
                }

              }
             }
          }
          list.add(loincDT);
        }
      }

     

     

      return list;
    }


	/**
	 * getLabReportsFromMorbidity: common method used from DRRQ and DRSAQ to get the morb's labs.
	 * @param morbReportSummaryVO
	 * @param labReportUids
	 * @param nbsSecurityObj
	 */


	@SuppressWarnings("unchecked")
	public Collection<Object> getLabReportSummaryVOColl(NBSSecurityObj nbsSecurityObj, boolean DRSAQ) {
		Collection<Object> reportSummaryColl = new ArrayList<Object>();
		Collection<Object> labList = null;
		Map<Object, Object> observationMap = new HashMap<Object, Object>();
		Map<Object, Object> providerDetails = new HashMap<Object, Object>();
		Map<Object, Object> organizationDetails = new HashMap<Object, Object>();
		Map<Object, Object> associationDetails = new HashMap<Object, Object>();
		Map<Object, Object> resultedTestDetails = new HashMap<Object, Object>();
		Map<Object, Object> doubleMap = new HashMap<Object, Object>();
		Map<Object, Object> susMap = new HashMap<Object, Object>();
		Map<Object, Object> suseptibilityDetails = new HashMap<Object, Object>();
		CachedDropDownValues cdv = new CachedDropDownValues();
		ArrayList<Object> labSummList = new ArrayList<Object>();
		try {
			observationMap = (Map<Object, Object>) getLabReportSummaryListforQueue(nbsSecurityObj, DRSAQ);
			
			if(observationMap==null)
				observationMap = new HashMap<Object, Object>();
			String observationList = getObservationList(observationMap.keySet());
			labList = observationMap.values();
			if(observationList!=null && observationList.trim().length()>0) {
			ObservationSummaryDAOImpl osd = new ObservationSummaryDAOImpl();
			String DRR_Skip_Provider_Info = propertyUtil.getProperty("DRR_SKIP_PROVIDER_INFO", "F");
			if (DRR_Skip_Provider_Info.equals("F"))
				providerDetails = osd.getProviderInfoForQueue(observationList, "ORD");

			// Get the Reporting Facility
			String DRR_Skip_Rpt_Facil = propertyUtil.getProperty("DRR_SKIP_RPT_FACIL", "F");
			if (DRR_Skip_Rpt_Facil.equals("F"))
				organizationDetails = osd.getOrganizationInfoForQueue(observationList, NEDSSConstants.PAR111_TYP_CD);
			if (!DRSAQ) {
				String DRR_Skip_Assoc_Inv = propertyUtil.getProperty("DRR_SKIP_ASSOC_INV", "F");
				if (DRR_Skip_Assoc_Inv.equals("F")) {
					String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION,
							NBSOperationLookup.VIEW);

					if (dataAccessWhereClause == null) {
						dataAccessWhereClause = "";
					} else {
						dataAccessWhereClause = " AND " + dataAccessWhereClause;

					}
					associationDetails = osd.getAssocInvInfoForQueue(observationList, dataAccessWhereClause,
							NEDSSConstants.OBSERVATION_CLASS_CODE);
				}
			}
			doubleMap = (Map<Object, Object>) osd.getResultedTestsForQueues(observationList, "COMP");
			resultedTestDetails = (Map<Object, Object>) doubleMap.get("RESULT");
			susMap = (Map<Object, Object>) doubleMap.get("SUS");
			if (resultedTestDetails != null && susMap != null) {
				String observationResultList = getObservationList(susMap.keySet());

				String DRR_Skip_Suscept = propertyUtil.getProperty("DRR_SKIP_SUSCEPT", "F");

				if (DRR_Skip_Suscept.equals("F") && observationResultList != null && observationResultList.trim().length()>0)
					suseptibilityDetails = osd.getSusceptibilitiesForQueues(observationResultList);
				if (suseptibilityDetails != null && suseptibilityDetails.size() > 0) {
					Collection<Object> resultList = susMap.values();
					Iterator<Object> ite = resultList.iterator();
					while (ite.hasNext()) {
						ArrayList<Object> results = (ArrayList<Object>) ite.next();
						if (results != null && results.size() > 0) {
							Iterator<Object> ite1 = results.iterator();
							while (ite1.hasNext()) {
								ResultedTestSummaryVO rtsVO = (ResultedTestSummaryVO) ite1.next();
								rtsVO.setTheSusTestSummaryVOColl((Collection<Object>) suseptibilityDetails
										.get(String.valueOf(rtsVO.getSourceActUid().longValue())));
							}
						}
					}
				}

			}
			}
			Long observationUid = null;
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			if (labList != null) {
				Iterator<Object> labIt = labList.iterator();
				while (labIt.hasNext()) {

					LabReportResultedtestSummaryVO labReportResultedSummaryVO = (LabReportResultedtestSummaryVO) labIt
							.next();
					LabReportSummaryVO labReportSummaryVO = new LabReportSummaryVO();

					observationUid = labReportResultedSummaryVO.getObservationUid();
					labReportSummaryVO.setObservationUid(observationUid);

					labReportSummaryVO.setVersionCtrlNbr(labReportResultedSummaryVO.getVersionCtrlNbr());

					labReportSummaryVO.setDateReceived(labReportResultedSummaryVO.getDateReceived());
					// labReportSummaryVO.setDateReceived(startDate);
					labReportSummaryVO.setLocalId(labReportResultedSummaryVO.getLocalId());
					/* Setting it as "Lab Report" when the cntrl_display_form_cd is "LapReport" */
					labReportSummaryVO.setType(NEDSSConstants.LAB_REPORT_DESC);
					labReportSummaryVO.setStatus(labReportResultedSummaryVO.getStatus());
					labReportSummaryVO.setElectronicInd(labReportResultedSummaryVO.getElectronicInd());

					if (labReportSummaryVO.getStatus() != null) {
						String tempStr = cdv.getDescForCode("ACT_OBJ_ST", labReportSummaryVO.getStatus());
						if (tempStr != null)
							labReportSummaryVO.setStatus(tempStr);
					}
					String jurisdictionCd = labReportResultedSummaryVO.getJurisdictionCd();
					if (jurisdictionCd != null) {
						String tempString = cdv.getJurisdictionDesc(jurisdictionCd);
						labReportSummaryVO.setJurisdictionCd(jurisdictionCd);
						labReportSummaryVO.setJurisdiction(tempString);
					}
					labReportSummaryVO.setProgramArea(labReportResultedSummaryVO.getProgramArea());
					labReportSummaryVO.setSharedInd(labReportResultedSummaryVO.getSharedInd());

					if (labReportSummaryVO.getProgramArea() != null) {
						String tempStr = cdv.getProgramAreaDesc(labReportSummaryVO.getProgramArea());
						if (tempStr != null) {
							labReportSummaryVO.setProgAreaCd(labReportSummaryVO.getProgramArea());
							labReportSummaryVO.setProgramArea(tempStr);
						}
					}

					ObservationPersonInfoVO provider = (ObservationPersonInfoVO) providerDetails
							.get(String.valueOf(labReportSummaryVO.getObservationUid().longValue()));
					if (provider != null) {
						labReportSummaryVO.setProviderFirstName(provider.getFirstNm());
						labReportSummaryVO.setProviderLastName(provider.getLastNm());
						labReportSummaryVO.setProviderDegree(provider.getDegree());
						labReportSummaryVO.setProviderPrefix(provider.getPrefix());
						labReportSummaryVO.setProviderSuffix(provider.getSuffix());
						labReportSummaryVO.setProviderUid(String.valueOf(provider.getPersonUid().longValue()));
					}

					ObservationOrganizationInfoVO org = (ObservationOrganizationInfoVO) organizationDetails
							.get(String.valueOf(labReportSummaryVO.getObservationUid().longValue()));
					if (org != null)
						labReportSummaryVO.setReportingFacility(org.getOrganizationNm());

					labReportSummaryVO.setMPRUid(labReportResultedSummaryVO.getPersonParentUid());
					labReportSummaryVO.setPatientFirstName(labReportResultedSummaryVO.getFirstNm());
					labReportSummaryVO.setPatientLastName(labReportResultedSummaryVO.getLastNm());
					labReportSummaryVO.setBirthTime(labReportResultedSummaryVO.getBirthTime());
					labReportSummaryVO.setCurrSexCd(labReportResultedSummaryVO.getCurrSexCd());
					labReportSummaryVO.setPersonLocalId(labReportResultedSummaryVO.getPersonLocalId());
					ArrayList<Object> uidList = new ArrayList<Object>();
					uidList.add(labReportSummaryVO.getMPRUid());
					if (associationDetails != null && associationDetails.size() > 0)
						labReportSummaryVO.setInvSummaryVOs((Collection<Object>) associationDetails
								.get(String.valueOf(labReportSummaryVO.getObservationUid().longValue())));
					if (resultedTestDetails != null && resultedTestDetails.size() > 0)
						labReportSummaryVO
								.setTheResultedSummaryTestVOCollection((Collection<Object>) resultedTestDetails
										.get(String.valueOf(labReportSummaryVO.getObservationUid().longValue())));
					returnMap.put(labReportSummaryVO.getObservationUid(), labReportSummaryVO);
					labSummList.add(labReportSummaryVO);

				}
			}
			reportSummaryColl.addAll(returnMap.values());
			this.populateDescTxtFromCachedValuesDRRQ(labSummList);

		} catch (Exception e) {
			logger.error("Error while getting Lab observations for Review", e);
			throw new NEDSSSystemException(e.toString());

		}

		return reportSummaryColl;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Object, Object> getLabReportSummaryListforQueue(NBSSecurityObj nbsSecurityObj, boolean DRSAQ) {

		Map<Object, Object> labSummaryMap = null;
		int labCountFix = propertyUtil.getLabCount();

		// Get Security Properties for ObsNeedingReview
		String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();

		String labQuery = "";
		try {

			if (nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, obsNeedingReviewSecurity)) {
				String labDataAccessWhereClause = nbsSecurityObj
						.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT, obsNeedingReviewSecurity);

				labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
				labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid",
						"obs.program_jurisdiction_oid");
				labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
				logger.debug(" \n labDataAccessWhereClause " + labDataAccessWhereClause);
				ArrayList<Object> inArrayList = new ArrayList<Object>();

				if (labDataAccessWhereClause != null && !labDataAccessWhereClause.equals("") && labDataAccessWhereClause.indexOf("shared_ind")>0)
					labDataAccessWhereClause = labDataAccessWhereClause.substring(
							labDataAccessWhereClause.indexOf("in (") + 4, labDataAccessWhereClause.indexOf("))"));
				else if (labDataAccessWhereClause != null && !labDataAccessWhereClause.equals(""))
					labDataAccessWhereClause = labDataAccessWhereClause.substring(
							labDataAccessWhereClause.indexOf("in (") + 4, labDataAccessWhereClause.indexOf("))"));
				
				if (!DRSAQ) {
					inArrayList.add(labDataAccessWhereClause);
					inArrayList.add("F");
					inArrayList.add(labCountFix);
				} else {
					inArrayList.add("");
					inArrayList.add("T");
					inArrayList.add(labCountFix);
				}

				logger.info("LabReport Summary  List for Review Query - " + labQuery);
				long timebegin = 0;
				long timeend = 0;
				timebegin = System.currentTimeMillis();
				logger.debug("\n timebegin  for LabReportSummaryListforReview " + timebegin);
				labQuery = "{call OBSERVATION_QUEUE_SP(?,?,?)}";
				LabReportResultedtestSummaryVO labReportSummaryVO = new LabReportResultedtestSummaryVO();
				labSummaryMap = (Map<Object, Object>) callStoredProcedureMethodWithResultSetMap(labReportSummaryVO,
						inArrayList, labQuery, "getObservationUid");
				timeend = System.currentTimeMillis();
				logger.debug("\n timeend  for LabReportSummaryListforReview " + timeend);
				logger.debug("\n total time for LabReportSummaryListforReview " + new Long(timeend - timebegin));

			}

		} catch (Exception e) {

			logger.error("Error in fetching LabReportSummaryList for Review ");
			throw new NEDSSSystemException(e.toString());

		}

		return labSummaryMap;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getMorbReportSummaryVOColl(NBSSecurityObj nbsSecurityObj, boolean DRSAQ) {
		Collection<Object> reportSummaryColl = new ArrayList<Object>();
		Collection<Object> morbList = null;
		Map<Object, Object> observationMap = new HashMap<Object, Object>();
		Map<Object, Object> providerDetails = new HashMap<Object, Object>();
		Map<Object, Object> organizationDetails = new HashMap<Object, Object>();
		Map<Object, Object> uidMap = new HashMap<Object, Object>();
		Map<Object, Object> morbLabMap = new HashMap<Object, Object>();
		Map<Object, Object> resultedTestDetails = new HashMap<Object, Object>();
		Map<Object, Object> doubleMap = new HashMap<Object, Object>();
		ObservationSummaryDAOImpl osd = new ObservationSummaryDAOImpl();

		CachedDropDownValues cdv = new CachedDropDownValues();
		try {
			//get the Morb list
			observationMap = (Map<Object, Object>) getMorbReportSummaryListforQueue(nbsSecurityObj, DRSAQ);
			if(observationMap == null)
				observationMap = new HashMap<Object, Object>();

			String observationList = getObservationList(observationMap.keySet());
			morbList = observationMap.values();
			// get all the Provider
			if (observationList != null && observationList.trim().length() > 0) {
				providerDetails = osd.getProviderInfoForQueue(observationList, "PhysicianOfMorb");
				// get all the Organizations
				organizationDetails = osd.getOrganizationInfoForQueue(observationList,
						NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT);
				// get all the lab Uids linked to the Morbs
				uidMap = getLabReportsUids(observationList, nbsSecurityObj);

				String labUidList = getlabUids(uidMap.values());
				// get all the lab linked to the Morbs
				if (labUidList != null && labUidList.length() > 0) {
					morbLabMap = getMorbLabReportSummaryListforQueue(labUidList, nbsSecurityObj);
					// get all the resulted Tests linked to the Morbs
					doubleMap = (Map<Object, Object>) osd.getResultedTestsForQueues(labUidList, "COMP");
					resultedTestDetails = (Map<Object, Object>) doubleMap.get("RESULT");
					// connect resulted tests with Labs
					Collection<Object> labCollection = morbLabMap.values();
					if (labCollection != null && labCollection.size() > 0) {
						Iterator<Object> labIt = labCollection.iterator();
						while (labIt.hasNext()) {
							LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) labIt.next();
							labReportSummaryVO
									.setTheResultedSummaryTestVOCollection((Collection<Object>) resultedTestDetails
											.get(String.valueOf(labReportSummaryVO.getObservationUid().longValue())));
						}
					}
					populateDescTxtFromCachedValuesDRRQ(labCollection);
				}
			}

			HashMap<Long, Object> mapForMorb = new HashMap<Long, Object>();
			//Long observationUid = null;
			if (morbList != null) {
				Iterator<Object> morbIt = morbList.iterator();
				while (morbIt.hasNext()) {
					MorbReportConditionSummaryVO morbReportConditionSummaryVO = (MorbReportConditionSummaryVO) morbIt
							.next();
					MorbReportSummaryVO morbReportSummaryVO = new MorbReportSummaryVO();
					morbReportSummaryVO.setObservationUid(morbReportConditionSummaryVO.getObservationUid());

					morbReportSummaryVO.setDateReceived(morbReportConditionSummaryVO.getDateReceived());

					morbReportSummaryVO.setLocalId(morbReportConditionSummaryVO.getLocalId());

					morbReportSummaryVO.setType(NEDSSConstants.MORB_REPORT_DESC);
					morbReportSummaryVO.setStatus(morbReportConditionSummaryVO.getStatus());
					if (morbReportSummaryVO.getStatus() != null) {
						String tempStr = cdv.getDescForCode("ACT_OBJ_ST", morbReportSummaryVO.getStatus());
						if (tempStr != null)
							morbReportSummaryVO.setStatus(tempStr);
					}
					if (morbReportConditionSummaryVO.getJurisdictionCd() != null) {
						String tempString = cdv.getJurisdictionDesc(morbReportConditionSummaryVO.getJurisdictionCd());
						morbReportSummaryVO.setJurisdictionCd(morbReportConditionSummaryVO.getJurisdictionCd());
						morbReportSummaryVO.setJurisdiction(tempString);
					}
					morbReportSummaryVO.setProgramArea(morbReportConditionSummaryVO.getProgramArea());
					if (morbReportSummaryVO.getProgramArea() != null) {
						String tempStr = cdv.getProgramAreaDesc(morbReportSummaryVO.getProgramArea());
						if (tempStr != null) {
							morbReportSummaryVO.setProgAreaCd(morbReportSummaryVO.getProgramArea());
							morbReportSummaryVO.setProgramArea(tempStr);
						}
					}
					morbReportSummaryVO.setSharedInd(morbReportConditionSummaryVO.getSharedInd());
					morbReportSummaryVO.setReportType(morbReportConditionSummaryVO.getReportType());
					if (morbReportSummaryVO.getReportType() != null) {
						String tempStr = cdv.getDescForCode("MORB_RPT_TYPE", morbReportSummaryVO.getReportType());
						morbReportSummaryVO.setReportTypeDescTxt(tempStr);
					}
					morbReportSummaryVO.setCondition(morbReportConditionSummaryVO.getCondition());
					morbReportSummaryVO.setConditionDescTxt(morbReportConditionSummaryVO.getConditionDscTxt());
					
					morbReportSummaryVO.setMPRUid(morbReportConditionSummaryVO.getPersonParentUid());
					morbReportSummaryVO.setPatientFirstName(morbReportConditionSummaryVO.getFirstNm());
					morbReportSummaryVO.setPatientLastName(morbReportConditionSummaryVO.getLastNm());
					morbReportSummaryVO.setBirthTime(morbReportConditionSummaryVO.getBirthTime());
					morbReportSummaryVO.setCurrSexCd(morbReportConditionSummaryVO.getCurrSexCd());
					morbReportSummaryVO.setPersonLocalId(morbReportConditionSummaryVO.getPersonLocalId());

					ObservationPersonInfoVO provider = (ObservationPersonInfoVO) providerDetails
							.get(String.valueOf(morbReportSummaryVO.getObservationUid().longValue()));
					if (provider != null) {
						morbReportSummaryVO.setProviderFirstName(provider.getFirstNm());
						morbReportSummaryVO.setProviderLastName(provider.getLastNm());
						morbReportSummaryVO.setProviderDegree(provider.getDegree());
						morbReportSummaryVO.setProviderPrefix(provider.getPrefix());
						morbReportSummaryVO.setProviderSuffix(provider.getSuffix());
						morbReportSummaryVO.setProviderUid(String.valueOf(provider.getPersonUid().longValue()));
					}

					ObservationOrganizationInfoVO org = (ObservationOrganizationInfoVO) organizationDetails
							.get(String.valueOf(morbReportSummaryVO.getObservationUid().longValue()));
					if (org != null)
						morbReportSummaryVO.setReportingFacility(org.getOrganizationNm());
					
					ArrayList<Object> labsForMorbList = new ArrayList<Object>();
					//set the labs for individual Morbs
					ArrayList<Object> morLlabUidList = (ArrayList<Object>) uidMap
							.get(String.valueOf(morbReportSummaryVO.getObservationUid().longValue()));
					if (morLlabUidList != null && morLlabUidList.size() > 0) {
						Iterator<Object> ite = morLlabUidList.iterator();
						while (ite.hasNext()) {
							UidSummaryVO uidVO = (UidSummaryVO) ite.next();
							if(morbLabMap.get(String.valueOf(uidVO.getUid().longValue()))!=null)
								labsForMorbList.add(morbLabMap.get(String.valueOf(uidVO.getUid().longValue())));
						}
					}
					
					morbReportSummaryVO.setTheLabReportSummaryVOColl(labsForMorbList);
					 mapForMorb.put(morbReportConditionSummaryVO.getObservationUid(),
							 morbReportSummaryVO);
				}
			}

			reportSummaryColl.addAll(mapForMorb.values());
		} catch (Exception e) {
			logger.error("Error while getting Morb observations for Review", e);
			throw new NEDSSSystemException(e.toString());

		}

		return reportSummaryColl;
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getLabReportsUids(String labReportUids, NBSSecurityObj nbsSecurityObj) {

		Map<Object, Object> uidMap = new HashMap<Object, Object>();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		ArrayList<Object> argList = new ArrayList<Object>();
		UidSummaryVO uidSummary = new UidSummaryVO();
		argList.clear();
		String uidQuery = WumSqlQuery.SELECT_LAB_TARGETS_FOR_MORB + "(" + labReportUids + ")";
		uidMap = (Map<Object, Object>) preparedStmtMethodForMap(uidSummary, argList, uidQuery, NEDSSConstants.SELECT,
				"getUniqueMapKey");

		if (uidMap != null && uidMap.size() > 0) {
			Set<Object> keys = uidMap.keySet();
			Iterator<Object> ite = keys.iterator();
			while (ite.hasNext()) {
				String key = (String) ite.next();
				String observationUid = key.substring(0, key.indexOf("|"));
				if ((observationUid != null)) {
					if (returnMap.containsKey(observationUid))
						((ArrayList<Object>) returnMap.get(observationUid)).add(uidMap.get(key));
					else {
						ArrayList<Object> list = new ArrayList<Object>();
						list.add(uidMap.get(key));
						returnMap.put(observationUid, list);
					}
				}
			}

		}

		return returnMap;

	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getMorbLabReportSummaryListforQueue(String labObservationUids,
			NBSSecurityObj nbsSecurityObj) {

		Map<Object, Object> labSummaryMap = null;

		ArrayList<Object> argList = new ArrayList<Object>();
		LabReportSummaryVO labVO = new LabReportSummaryVO();
		String labQuery = WumSqlQuery.SELECT_LAB_SUMMARY_FOR_MORB + "(" + labObservationUids + ")";
		try {
			labSummaryMap = (Map<Object, Object>) preparedStmtMethodForMap(labVO, argList, labQuery, NEDSSConstants.SELECT,
					"getObservationUid");

		} catch (Exception e) {

			logger.error("Error in fetching getMorbLabReportSummaryListforQueue for Review ");
			throw new NEDSSSystemException(e.toString());

		}

		return labSummaryMap;
	}


// This method is added in place of old method retrieveLabReportSummary() to improve the performance of the page
   
   public HashMap<Object, Object> retrieveLabReportSummaryRevisited(Collection<Object> labReportUids, boolean isCDCFormPrintCase,
                                             NBSSecurityObj nbsSecurityObj, String uidType) {
    //labReportUids = getLongArrayList(labReportUids);
	HashMap<Object, Object> labReportSummarMap = getObservationSummaryListForWorkupRevisited(
        labReportUids, isCDCFormPrintCase, nbsSecurityObj, uidType);

    return labReportSummarMap;
  }
   
	@SuppressWarnings("unchecked")
	private Map<Object, Object> getMorbReportSummaryListforQueue(NBSSecurityObj nbsSecurityObj, boolean DRSAQ) {
		int morbCountFix = propertyUtil.getMorbCount();
		Map<Object, Object> morbSummaryMap = null;
		// Get Security Properties for ObsNeedingReview
		String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();

		String morbQuery = "";
		try {

			if (nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, obsNeedingReviewSecurity)) {
				String morbDataAccessWhereClause = nbsSecurityObj
						.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, obsNeedingReviewSecurity);
				morbDataAccessWhereClause = morbDataAccessWhereClause != null ? "AND " + morbDataAccessWhereClause : "";
				// This is just to keep the alias name of the observation table so that you can
				// remove the sub-query
				morbDataAccessWhereClause = morbDataAccessWhereClause.replaceAll("program_jurisdiction_oid",
						"obs.program_jurisdiction_oid");
				morbDataAccessWhereClause = morbDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");

				ArrayList<Object> inArrayList = new ArrayList<Object>();
				if (morbDataAccessWhereClause != null && !morbDataAccessWhereClause.equals("") && morbDataAccessWhereClause.indexOf("shared_ind")>0)
					morbDataAccessWhereClause = morbDataAccessWhereClause.substring(
							morbDataAccessWhereClause.indexOf("in (") + 4, morbDataAccessWhereClause.indexOf("))"));
				else if (morbDataAccessWhereClause != null && !morbDataAccessWhereClause.equals(""))
					morbDataAccessWhereClause = morbDataAccessWhereClause.substring(
							morbDataAccessWhereClause.indexOf("in (") + 4, morbDataAccessWhereClause.indexOf("))"));

				if (!DRSAQ) {
					inArrayList.add(morbDataAccessWhereClause);
					inArrayList.add("F");
					inArrayList.add(morbCountFix);
				} else {
					inArrayList.add("");
					inArrayList.add("T");
					inArrayList.add(morbCountFix);
				}

				logger.debug(" \n morbDataAccessWhereClause " + morbDataAccessWhereClause);

				logger.info("MorbReport Summary List for Review Query - " + morbQuery);

				long timebegin = 0;
				long timeend = 0;
				timebegin = System.currentTimeMillis();
				logger.debug("\n timebegin  for MorbReportSummaryListforReview " + timebegin);
				morbQuery = "{call OBSERVATION_MORB_QUEUE_SP(?,?,?)}";
				MorbReportConditionSummaryVO morbReportConditionSummaryVO = new MorbReportConditionSummaryVO();
				morbSummaryMap = (Map<Object, Object>) callStoredProcedureMethodWithResultSetMap(
						morbReportConditionSummaryVO, inArrayList, morbQuery, "getObservationUid");
				logger.debug("\n timeend  for MorbReportSummaryListforReview " + timeend);
				logger.debug("\n total time for MorbReportSummaryListforReview " + new Long(timeend - timebegin));
			}

		} catch (Exception e) {
			logger.error("Error in fetching Morbidity Reports for Review ");
			throw new NEDSSSystemException(e.toString());

		}

		return morbSummaryMap;
	}
	
   /**
    * retrieveLabReportSummaryRevisitedFromMorbDRRQ: method used for retrieving the morb's labs
    * @param labReportUids
    * @param isCDCFormPrintCase
    * @param nbsSecurityObj
    * @param uidType
    * @return
    */
   public HashMap<Object, Object> retrieveLabReportSummaryRevisitedFromMorbDRRQ(Collection<Object> labReportUids, boolean isCDCFormPrintCase,
           NBSSecurityObj nbsSecurityObj, String uidType) {
	//labReportUids = getLongArrayList(labReportUids);
	HashMap<Object, Object> labReportSummarMap = getObservationSummaryListForWorkupRevisitedFromMorbDRRQ(
	labReportUids, isCDCFormPrintCase, nbsSecurityObj, uidType);
	
	return labReportSummarMap;
   }
   
   ArrayList<Object> getLabsFromUid(Collection<Object> uidList, NBSSecurityObj nbsSecurityObj, String uidType){
	   

	   
		    int count = 0;
		    ArrayList<Object>  labList = new ArrayList<Object> ();
		    
		    if (uidList != null) {

		      //This is the where Clause
		      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
		              NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW);
		      if (dataAccessWhereClause == null) {
		        dataAccessWhereClause = "";
		      }
		      else {
		        dataAccessWhereClause = " AND " + dataAccessWhereClause;
		      }

		      LabReportSummaryVO labVO = new LabReportSummaryVO();
		      
		      ArrayList<Object>  argList = new ArrayList<Object> ();
		      Long LabAsSourceForInvestigation = null;
		      try {
		        
		        Timestamp fromTime = null;
		        //   uidList = (ArrayList<Object> )getUidSummaryVOArrayList(uidList);
		        Iterator<Object> itLabId = uidList.iterator();
		        while (itLabId.hasNext()) {
		        	if(uidType.equals("PERSON_PARENT_UID")){
			          Long uid = (Long) itLabId.next();
			          //Long observationUid = vo.getUid();
			          argList.clear();
			          argList.add(uid);
			          String selQuery = WumSqlQuery.SELECT_LABSUMMARY_FORWORKUPNEW+ dataAccessWhereClause;
			          labList = (ArrayList<Object> ) preparedStmtMethod(labVO, argList, selQuery, NEDSSConstants.SELECT);
			          count = count + 1;
		        	}else if(uidType.equals("LABORATORY_UID"))
		        	{
		        	  UidSummaryVO vo = (UidSummaryVO) itLabId.next();	
		        	  Long observationUid = vo.getUid();
		        	  fromTime = vo.getAddTime();
		        	 if(vo.getStatusTime()!=null && vo.getStatusTime().compareTo(fromTime)==0){
		        		  LabAsSourceForInvestigation=vo.getUid();
		        	  }
		        	  argList.clear();
		  	          argList.add(observationUid);
		  	          labList = (ArrayList<Object> ) preparedStmtMethod(labVO, argList,
		  	              WumSqlQuery.SELECT_LABSUMMARY_FORWORKUP, NEDSSConstants.SELECT);
		  	          count = count + 1;
		        	}
		        }
		      }
		      catch (Exception ex) {
		          logger.error(
		              "Error while getting observations for given set of UIDS in workup",
		              ex);
		          throw new NEDSSSystemException(ex.toString());
		        }	
		    }

		    return labList;
   }

  @SuppressWarnings("unchecked") 
protected HashMap<Object, Object> getObservationSummaryListForWorkupRevisited(Collection<Object> uidList,boolean isCDCFormPrintCase,
      NBSSecurityObj nbsSecurityObj, String uidType) throws NEDSSSystemException {
    ArrayList<Object>  labSummList = new ArrayList<Object> ();
    ArrayList<Object>  labEventList = new ArrayList<Object> ();
    int count = 0;
    
   
    Long providerUid=null;
    RetrieveSummaryVO rSummaryVO = new RetrieveSummaryVO();

    if (uidList != null) {
      String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
              NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW);
      if (dataAccessWhereClause == null) {
        dataAccessWhereClause = "";
      }
      else {
        dataAccessWhereClause = " AND " + dataAccessWhereClause;
      }
      
      
     
      LabReportSummaryVO labVO = new LabReportSummaryVO();
      
     
      ArrayList<Object>  labList = new ArrayList<Object> ();
      ArrayList<Object>  argList = new ArrayList<Object> ();
      Long LabAsSourceForInvestigation = null;
      try {
        
        Timestamp fromTime = null;
        //   uidList = (ArrayList<Object> )getUidSummaryVOArrayList(uidList);
        Iterator<Object> itLabId = uidList.iterator();
        while (itLabId.hasNext()) {
        	if(uidType.equals("PERSON_PARENT_UID")){
	          Long uid = (Long) itLabId.next();
	          //Long observationUid = vo.getUid();
	          argList.clear();
	          argList.add(uid);
	          String selQuery = WumSqlQuery.SELECT_LABSUMMARY_FORWORKUPNEW+ dataAccessWhereClause;
	          labList = (ArrayList<Object> ) preparedStmtMethod(labVO, argList, selQuery, NEDSSConstants.SELECT);
	          count = count + 1;
        	}else if(uidType.equals("LABORATORY_UID"))
        	{
        	  UidSummaryVO vo = (UidSummaryVO) itLabId.next();	
        	  Long observationUid = vo.getUid();
        	  fromTime = vo.getAddTime();
        	 if(vo.getStatusTime()!=null && vo.getStatusTime().compareTo(fromTime)==0){
        		  LabAsSourceForInvestigation=vo.getUid();
        	  }
        	  argList.clear();
  	          argList.add(observationUid);
  	          labList = (ArrayList<Object> ) preparedStmtMethod(labVO, argList,
  	              WumSqlQuery.SELECT_LABSUMMARY_FORWORKUP, NEDSSConstants.SELECT);
  	          count = count + 1;
        	}
           if(labList != null) {
            Iterator<Object> labIt = labList.iterator();
            while (labIt.hasNext()) {
              LabReportSummaryVO labRepVO = (LabReportSummaryVO) labIt.next();
              labRepVO.setActivityFromTime(fromTime);
              LabReportSummaryVO labRepSumm = null;
              LabReportSummaryVO labRepEvent = null;
              ObservationSummaryDAOImpl osd = new ObservationSummaryDAOImpl();
              OrganizationNameDAOImpl organizationDao = new OrganizationNameDAOImpl();
              Map<Object,Object> uidMap = osd.getLabParticipations(labRepVO.getObservationUid());
              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR110_TYP_CD) ){
            		if (labRepVO.getRecordStatusCd()!=null && (labRepVO.getRecordStatusCd().equals("UNPROCESSED"))) {
            			labRepSumm = labRepVO ;
            			labRepSumm.setMPRUid((Long)uidMap.get(NEDSSConstants.PAR110_TYP_CD));
            		}
            		if(labRepVO.getRecordStatusCd()!=null && !labRepVO.getRecordStatusCd().equals("LOG_DEL")){
            			labRepEvent = labRepVO;
            			labRepEvent.setMPRUid((Long)uidMap.get(NEDSSConstants.PAR110_TYP_CD));
            		}
              }
            	  
              ArrayList<Object>  valList = osd.getPatientPersonInfo(labRepVO.getObservationUid());
              ArrayList<Object>  providerDetails = osd.getProviderInfo(labRepVO.getObservationUid(),"ORD");
              ArrayList<Object>  actIdDetails = osd.getActIdDetails(labRepVO.getObservationUid());
              Map<Object,Object> associationsMap = rSummaryVO.getAssociatedInvList(labRepVO.getObservationUid(),nbsSecurityObj, "OBS");
              if(labRepEvent!=null){
            	  labRepEvent.setAssociationsMap(associationsMap);
              }
              if(labRepSumm!=null){
            	  labRepSumm.setAssociationsMap(associationsMap);
              }
              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR111_TYP_CD) && labRepEvent != null) {
            	  labRepEvent.setReportingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR111_TYP_CD)));
               }
              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR111_TYP_CD) && labRepSumm != null) {
            	  labRepSumm.setReportingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR111_TYP_CD)));
               }
              
              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR101_TYP_CD) && labRepEvent != null) {
            	  labRepEvent.setOrderingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR101_TYP_CD)));
               }
              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR101_TYP_CD) && labRepSumm != null) {
            	  labRepSumm.setOrderingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR101_TYP_CD)));
               }

              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR104_TYP_CD) && labRepEvent != null) {
            	  CachedDropDownValues cddv = new CachedDropDownValues();
            	  labRepEvent.setSpecimenSource(cddv.getDescForCode("SPECMN_SRC",osd.getSpecimanSource((Long)uidMap.get(NEDSSConstants.PAR104_TYP_CD))));
               }
              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR104_TYP_CD) && labRepSumm != null) {
            	  CachedDropDownValues cddv = new CachedDropDownValues();
            	  labRepSumm.setSpecimenSource(cddv.getDescForCode("SPECMN_SRC",osd.getSpecimanSource((Long)uidMap.get(NEDSSConstants.PAR104_TYP_CD))));
               }
              
              providerUid = getProviderInformation(providerDetails, labRepEvent);
              
              if(isCDCFormPrintCase && providerUid!=null && LabAsSourceForInvestigation!=null){
            	  ProviderDataForPrintVO providerDataForPrintVO =null;
            	  if(labRepEvent.getProviderDataForPrintVO()==null){
            		  providerDataForPrintVO =new ProviderDataForPrintVO();
            		  labRepEvent.setProviderDataForPrintVO(providerDataForPrintVO);
               	  }           	  
                  Long orderingFacilityUid = null;
                  if(uidMap.get(NEDSSConstants.PAR101_TYP_CD)!=null){
                	  orderingFacilityUid=(Long)uidMap.get(NEDSSConstants.PAR101_TYP_CD);
                  }
                  if(orderingFacilityUid!=null){
                	  ArrayList list = (ArrayList)organizationDao.load(orderingFacilityUid);
                	 if( !list.isEmpty()) {
                		 OrganizationNameDT dt = (OrganizationNameDT)list.get(0);
                	 
                		  providerDataForPrintVO.setFacilityName(dt.getNmTxt());
                	 }
                	  osd.getOrderingFacilityAddress(providerDataForPrintVO, orderingFacilityUid);
                      osd.getOrderingFacilityPhone(providerDataForPrintVO, orderingFacilityUid);
                  }
                  if(providerUid!=null){
                	  osd.getOrderingPersonAddress(providerDataForPrintVO, providerUid);
                      osd.getOrderingPersonPhone(providerDataForPrintVO, providerUid);
                  }
              }
              
              getProviderInformation(providerDetails, labRepSumm);
              
            
              if (actIdDetails != null && actIdDetails.size() > 0 && labRepEvent != null) {
                  Object[] accessionNumber = actIdDetails.toArray();
                  if (accessionNumber[0] != null) {
                	  labRepEvent.setAccessionNumber((String) accessionNumber[0]);
                    logger.debug("AccessionNumber: " + (String) accessionNumber[0]);
                  }
              }
              if (actIdDetails != null && actIdDetails.size() > 0 && labRepSumm != null) {
                  Object[] accessionNumber = actIdDetails.toArray();
                  if (accessionNumber[0] != null) {
                	  labRepSumm.setAccessionNumber((String) accessionNumber[0]);
                    logger.debug("AccessionNumber: " + (String) accessionNumber[0]);
                  }
              }

              if(labRepEvent!= null) 
            	  labEventList.add(labRepEvent);
              if(labRepSumm !=null)
            	  labSummList.add(labRepSumm);
              
              Long ObservationUID = labRepVO.getObservationUid();
              argList.clear();
              argList.add("COMP"); // It should be constant
              argList.add(ObservationUID);

			getTestAndSusceptibilities(argList, labRepEvent, labRepSumm);
        }

           }
        }
        }
      catch (Exception ex) {
        logger.error(
            "Error while getting observations for given set of UIDS in workup",
            ex);
        throw new NEDSSSystemException(ex.toString());
      }
  }
    

  this.populateDescTxtFromCachedValues(labSummList);
  this.populateDescTxtFromCachedValues(labEventList);
  HashMap<Object, Object> returnMap = new HashMap<Object, Object>();
  returnMap.put("labSummList", labSummList);
  returnMap.put("labEventList", labEventList);
  return returnMap;
} //end of getObservationSummaryVOCollectionForWorkup()

    
/**
 * getObservationSummaryListForWorkupRevisitedFromMorbDRRQ
 * @param uidList
 * @param isCDCFormPrintCase
 * @param nbsSecurityObj
 * @param uidType
 * @return
 * @throws NEDSSSystemException
 */
    @SuppressWarnings("unchecked") 
  protected HashMap<Object, Object> getObservationSummaryListForWorkupRevisitedFromMorbDRRQ(Collection<Object> uidList,boolean isCDCFormPrintCase,
        NBSSecurityObj nbsSecurityObj, String uidType) throws NEDSSSystemException {
      ArrayList<Object>  labEventList = new ArrayList<Object> ();
      int count = 0;

      Long providerUid=null;
      RetrieveSummaryVO rSummaryVO = new RetrieveSummaryVO();

      if (uidList != null) {

        //This is the where Clause
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
                NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW);
        if (dataAccessWhereClause == null) {
          dataAccessWhereClause = "";
        }
        else {
          dataAccessWhereClause = " AND " + dataAccessWhereClause;
        }
        
        
       
        LabReportSummaryVO labVO = new LabReportSummaryVO();
        
       
        ArrayList<Object>  labList = new ArrayList<Object> ();
        ArrayList<Object>  argList = new ArrayList<Object> ();
        Long LabAsSourceForInvestigation = null;
        try {
          
          Timestamp fromTime = null;
          //   uidList = (ArrayList<Object> )getUidSummaryVOArrayList(uidList);
          Iterator<Object> itLabId = uidList.iterator();
          while (itLabId.hasNext()) {
          	if(uidType.equals("PERSON_PARENT_UID")){
  	          Long uid = (Long) itLabId.next();
  	          //Long observationUid = vo.getUid();
  	          argList.clear();
  	          argList.add(uid);
  	          String selQuery = WumSqlQuery.SELECT_LABSUMMARY_FORWORKUPNEW+ dataAccessWhereClause;
  	          labList = (ArrayList<Object> ) preparedStmtMethod(labVO, argList, selQuery, NEDSSConstants.SELECT);
  	          count = count + 1;
          	}else if(uidType.equals("LABORATORY_UID"))
          	{
          	  UidSummaryVO vo = (UidSummaryVO) itLabId.next();	
          	  Long observationUid = vo.getUid();
          	  fromTime = vo.getAddTime();
//          	  DateFormat format =  DateFormat.getDateInstance(DateFormat.MEDIUM);
//          	  if(vo.getStatusTime()!=null && format.format(vo.getStatusTime()).equals(format.format(fromTime))){
          	 if(vo.getStatusTime()!=null && vo.getStatusTime().compareTo(fromTime)==0){
          		  LabAsSourceForInvestigation=vo.getUid();
          	  }
          	  argList.clear();
    	          argList.add(observationUid);
    	          labList = (ArrayList<Object> ) preparedStmtMethod(labVO, argList,
    	              WumSqlQuery.SELECT_LABSUMMARY_FORWORKUP, NEDSSConstants.SELECT);
    	          count = count + 1;
          	}
             if(labList != null) {
              //Timing
              //t2begin = System.currentTimeMillis();
              Iterator<Object> labIt = labList.iterator();
              while (labIt.hasNext()) {
                LabReportSummaryVO labRepVO = (LabReportSummaryVO) labIt.next();
                labRepVO.setActivityFromTime(fromTime);
                LabReportSummaryVO labRepSumm = null;
                LabReportSummaryVO labRepEvent = null;
                //Populate name properties and report type cd for lab report summary vo, labVO
                //Timing
                //beforeFindPerson = System.currentTimeMillis();
                //Object[] vals = findPatientLegalName(observationUid, nbsSecurityObj);

                //ArrayList<Object> patientPersonInfo = (ArrayList<Object> )preparedStmtMethod(labVO, argList, QUICK_FIND_PATIENT, NEDSSConstants.SELECT);
                ObservationSummaryDAOImpl osd = new ObservationSummaryDAOImpl();
                OrganizationNameDAOImpl organizationDao = new OrganizationNameDAOImpl();
                Map<Object,Object> uidMap = osd.getLabParticipations(labRepVO.getObservationUid());
                if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR110_TYP_CD) ){
              		if (labRepVO.getRecordStatusCd()!=null && (labRepVO.getRecordStatusCd().equals("UNPROCESSED"))) {
              			labRepSumm = labRepVO ;
              			labRepSumm.setMPRUid((Long)uidMap.get(NEDSSConstants.PAR110_TYP_CD));
              		}
              		if(labRepVO.getRecordStatusCd()!=null && !labRepVO.getRecordStatusCd().equals("LOG_DEL")){
              			labRepEvent = labRepVO;
              			labRepEvent.setMPRUid((Long)uidMap.get(NEDSSConstants.PAR110_TYP_CD));
              		}
                }
              	  
                ArrayList<Object>  providerDetails = osd.getProviderInfo(labRepVO.getObservationUid(),"ORD");
                ArrayList<Object>  actIdDetails = osd.getActIdDetails(labRepVO.getObservationUid());
                Map<Object,Object> associationsMap = rSummaryVO.getAssociatedInvList(labRepVO.getObservationUid(),nbsSecurityObj, "OBS");

                if(labRepEvent!=null){
              	  labRepEvent.setAssociationsMap(associationsMap);
                }
                if(labRepSumm!=null){
              	  labRepSumm.setAssociationsMap(associationsMap);
                }
                if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR111_TYP_CD) && labRepEvent != null) {
              	  labRepEvent.setReportingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR111_TYP_CD)));
                 }
                if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR111_TYP_CD) && labRepSumm != null) {
              	  labRepSumm.setReportingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR111_TYP_CD)));
                 }
                if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR104_TYP_CD) && labRepEvent != null) {
              	  CachedDropDownValues cddv = new CachedDropDownValues();
              	  labRepEvent.setSpecimenSource(cddv.getDescForCode("SPECMN_SRC",osd.getSpecimanSource((Long)uidMap.get(NEDSSConstants.PAR104_TYP_CD))));
                 }
                if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR104_TYP_CD) && labRepSumm != null) {
              	  CachedDropDownValues cddv = new CachedDropDownValues();
              	  labRepSumm.setSpecimenSource(cddv.getDescForCode("SPECMN_SRC",osd.getSpecimanSource((Long)uidMap.get(NEDSSConstants.PAR104_TYP_CD))));
                 }
                
                providerUid = getProviderInformation(providerDetails, labRepEvent);
                
                if(isCDCFormPrintCase && providerUid!=null && LabAsSourceForInvestigation!=null){
              	  ProviderDataForPrintVO providerDataForPrintVO =null;
              	  if(labRepEvent.getProviderDataForPrintVO()==null){
              		  providerDataForPrintVO =new ProviderDataForPrintVO();
              		  labRepEvent.setProviderDataForPrintVO(providerDataForPrintVO);
                 	  }           	  
                    Long orderingFacilityUid = null;
                    if(uidMap.get(NEDSSConstants.PAR101_TYP_CD)!=null){
                  	  orderingFacilityUid=(Long)uidMap.get(NEDSSConstants.PAR101_TYP_CD);
                    }
                    if(orderingFacilityUid!=null){
                  	  ArrayList list = (ArrayList)organizationDao.load(orderingFacilityUid);
                  	 if( !list.isEmpty()) {
                  		 OrganizationNameDT dt = (OrganizationNameDT)list.get(0);
                  	 
                  		  providerDataForPrintVO.setFacilityName(dt.getNmTxt());
                  	 }
                  	  osd.getOrderingFacilityAddress(providerDataForPrintVO, orderingFacilityUid);
                        osd.getOrderingFacilityPhone(providerDataForPrintVO, orderingFacilityUid);
                    }
                    if(providerUid!=null){
                  	  osd.getOrderingPersonAddress(providerDataForPrintVO, providerUid);
                        osd.getOrderingPersonPhone(providerDataForPrintVO, providerUid);
                    }
                }
                
                getProviderInformation(providerDetails, labRepSumm);
               
                if (actIdDetails != null && actIdDetails.size() > 0 && labRepEvent != null) {
                    Object[] accessionNumber = actIdDetails.toArray();
                    if (accessionNumber[0] != null) {
                  	  labRepEvent.setAccessionNumber((String) accessionNumber[0]);
                      logger.debug("AccessionNumber: " + (String) accessionNumber[0]);
                    }
                }
                if (actIdDetails != null && actIdDetails.size() > 0 && labRepSumm != null) {
                    Object[] accessionNumber = actIdDetails.toArray();
                    if (accessionNumber[0] != null) {
                  	  labRepSumm.setAccessionNumber((String) accessionNumber[0]);
                      logger.debug("AccessionNumber: " + (String) accessionNumber[0]);
                    }
                }

                if(labRepEvent!= null) 
              	  labEventList.add(labRepEvent);
                
                Long ObservationUID = labRepVO.getObservationUid();
                argList.clear();
                argList.add("COMP"); // It should be constant
                argList.add(ObservationUID);

  			getTestAndSusceptibilitiesDRRQ(argList, labRepEvent, labRepSumm);
          }

             }
          }
          }
        catch (Exception ex) {
          logger.error(
              "Error while getting observations for given set of UIDS in workup",
              ex);
          throw new NEDSSSystemException(ex.toString());
        }
      }
   
  //  this.populateDescTxtFromCachedValues(labSummList);
    this.populateDescTxtFromCachedValuesDRRQ(labEventList);
    HashMap<Object, Object> returnMap = new HashMap<Object, Object>();
    //returnMap.put("labSummList", labSummList);
    returnMap.put("labEventList", labEventList);
    return returnMap;
  } //end of getObservationSummaryVOCollectionForWorkup()
  
  
  /**
   * getProviderInformation(): common method for getting the provider information from DRRQ and Patient file (Summary and Event tab).
   * @param providerDetails
   * @param labRep
   * @return
   */
  private Long getProviderInformation (ArrayList<Object>  providerDetails, LabReportSummaryVO labRep){  

	  Long providerUid = null;
	  
      if (providerDetails != null && providerDetails.size() > 0 && labRep != null) {
          Object[] orderProvider = providerDetails.toArray();

          if (orderProvider[0] != null) {
        	  labRep.setProviderLastName((String) orderProvider[0]);
              logger.debug("ProviderLastName: " + (String) orderProvider[0]);
          }
          if (orderProvider[1] != null){
        	  labRep.setProviderFirstName((String) orderProvider[1]);
          	  logger.debug("ProviderFirstName: " + (String) orderProvider[1]);
          }
          if (orderProvider[2] != null){
        	  labRep.setProviderPrefix((String) orderProvider[2]);
           	  logger.debug("ProviderPrefix: " + (String) orderProvider[2]);
          }
          if (orderProvider[3] != null){
        	  labRep.setProviderSuffix(( String)orderProvider[3]);
          	  logger.debug("ProviderSuffix: " + (String) orderProvider[3]);
          }
      	  if (orderProvider[4] != null){
      		labRep.setDegree(( String)orderProvider[4]);
         	logger.debug("ProviderDegree: " + (String) orderProvider[4]);
      	  }
     	  if (orderProvider[5] != null){
     		providerUid= (Long)orderProvider[5];
     	    labRep.setProviderUid((String)(orderProvider[5]+""));
         	logger.debug("orderProviderUid: " + (Long) orderProvider[5]);
     	  }
        }
      
      return providerUid;
      
  }
  
  /**
   * getProviderInformation(): common method for getting the provider information from DRRQ and Patient file (Summary and Event tab).
   * @param providerDetails
   * @param labRep
   * @return
   */
  
  private Long getProviderInformation (ArrayList<Object>  providerDetails, MorbReportSummaryVO morbRep){  

	  Long providerUid = null;
	  
      if (providerDetails != null && providerDetails.size() > 0 && morbRep != null) {
          Object[] orderProvider = providerDetails.toArray();

          if (orderProvider[0] != null) {
        	  morbRep.setProviderLastName((String) orderProvider[0]);
              logger.debug("ProviderLastName: " + (String) orderProvider[0]);
          }
          if (orderProvider[1] != null){
        	  morbRep.setProviderFirstName((String) orderProvider[1]);
          	  logger.debug("ProviderFirstName: " + (String) orderProvider[1]);
          }
          if (orderProvider[2] != null){
        	  morbRep.setProviderPrefix((String) orderProvider[2]);
           	  logger.debug("ProviderPrefix: " + (String) orderProvider[2]);
          }
          if (orderProvider[3] != null){
        	  morbRep.setProviderSuffix(( String)orderProvider[3]);
          	  logger.debug("ProviderSuffix: " + (String) orderProvider[3]);
          }
      	  if (orderProvider[4] != null){
      		morbRep.setDegree(( String)orderProvider[4]);
         	logger.debug("ProviderDegree: " + (String) orderProvider[4]);
      	  }
     	  if (orderProvider[5] != null){
     		providerUid= (Long)orderProvider[5];
     		morbRep.setProviderUid((String)(orderProvider[5]+""));
         	logger.debug("orderProviderUid: " + (Long) orderProvider[5]);
     	  }
        }
      
      return providerUid;
      
  }
  
  /**
   * getTestAndSusceptibilities: common method for getting the test and susceptibilities from Patient File and DRRQ
   * @param argList
   * @param labRepEvent
   * @param labRepSumm
   */
      private void getTestAndSusceptibilities(ArrayList<Object> argList, LabReportSummaryVO labRepEvent, LabReportSummaryVO labRepSumm){
    	  String query = "";
    	  
    	  
    	  
    	  ResultedTestSummaryVO testVO = new ResultedTestSummaryVO();
    	  ArrayList<Object>  testList = null;
    	  
			query = WumSqlQuery.SELECT_LABRESULTED_REFLEXTEST_SUMMARY_FORWORKUP_SQL;

				testList = (ArrayList<Object> ) preparedStmtMethod(testVO, argList,query, NEDSSConstants.SELECT);
           //afterReflex = System.currentTimeMillis();
           //totalReflex += (afterReflex - beforeReflex);
				
		    	  if (testList != null) {

		             	if(labRepEvent != null)
		             	  labRepEvent.setTheResultedSummaryTestVOCollection(testList);
		             	if(labRepSumm != null)
		             		labRepSumm.setTheResultedSummaryTestVOCollection(testList);
		             	
		                 Iterator<Object> it = testList.iterator();
		                 
		                 //timing
		                 //t3begin = System.currentTimeMillis();
		                 while (it.hasNext()) {
		                   ResultedTestSummaryVO RVO = (ResultedTestSummaryVO) it.next();
		                   
		                   
		                   
		                   	setSusceptibility(RVO, labRepEvent, labRepSumm, argList);
           
		                 }
		    	  }
         }
      
      
      private void getTestAndSusceptibilitiesDRRQ(ArrayList<Object> argList, LabReportSummaryVO labRepEvent, LabReportSummaryVO labRepSumm){
    	  String query = "";
    	  
    	  
    	  
    	  ResultedTestSummaryVO testVO = new ResultedTestSummaryVO();
    	  ArrayList<Object>  testList = null;
    	  
			query = WumSqlQuery.SELECT_LABRESULTED_REFLEXTEST_SUMMARY_FORWORKUP_SQL;

				testList = (ArrayList<Object> ) preparedStmtMethod(testVO, argList,query, NEDSSConstants.SELECT);
           //afterReflex = System.currentTimeMillis();
           //totalReflex += (afterReflex - beforeReflex);
				
		    	  if (testList != null) {

		             	if(labRepEvent != null)
		             	  labRepEvent.setTheResultedSummaryTestVOCollection(testList);
		             	if(labRepSumm != null)
		             		labRepSumm.setTheResultedSummaryTestVOCollection(testList);
		             	
		                 Iterator<Object> it = testList.iterator();
		                 
		                 //timing
		                 //t3begin = System.currentTimeMillis();
		                 String DRR_Skip_Suscept = propertyUtil.getProperty("DRR_SKIP_SUSCEPT", "F");
		                 while (it.hasNext()) {
		                   ResultedTestSummaryVO RVO = (ResultedTestSummaryVO) it.next();
		                   
			    			  if (DRR_Skip_Suscept.equals("F"))
			    				  setSusceptibilityDRRQ(RVO, labRepEvent, labRepSumm, argList);
           
		                 } //while
		    	  } //testList != null
      }
      
      
      private void setSusceptibility(ResultedTestSummaryVO RVO, LabReportSummaryVO labRepEvent, LabReportSummaryVO labRepSumm, ArrayList<Object> argList){
    	  
    	  
    	  ResultedTestSummaryVO susVO = new ResultedTestSummaryVO();
    	  int countResult = 0;
    	  int countSus = 0;
    	  UidSummaryVO sourceActUidVO = new UidSummaryVO();
    	  ArrayList<Object>  susList = new ArrayList<Object> ();

                 Long sourceActUid = RVO.getSourceActUid();
                 argList.clear();
                 argList.add("REFR"); // It should be constant
                 argList.add(sourceActUid);
                 
                // int num = setSusceptability2(argList);
                 
                 countResult = countResult + 1;
                 //beforeSus = System.currentTimeMillis();
                 String theQuery = "";
       		       theQuery = WumSqlQuery.GET_SOURCE_ACT_UID_FOR_SUSCEPTIBILITES_SQL;
                  
                 
                   
                 susList = (ArrayList<Object> ) preparedStmtMethod(sourceActUidVO,argList,
                     theQuery, NEDSSConstants.SELECT);
                 //afterSus = System.currentTimeMillis();
                 //totalSus += (afterSus - beforeSus);
                 if (susList != null) {
                   Iterator<Object> susIter = susList.iterator();
                   ArrayList<Object>  susListFinal = new ArrayList<Object> ();
                   //timing
                   //t4begin = System.currentTimeMillis();

  	           ArrayList<Object>  multipleSusceptArray = new ArrayList<Object> ();

                   while (susIter.hasNext()) {
                     UidSummaryVO uidVO = (UidSummaryVO) susIter.next();
                     Long sourceAct = uidVO.getUid();
                     argList.clear();
                     argList.add("COMP"); // It should be constant
  //System.out.println("source act -->" + sourceAct.toString());
                     argList.add(sourceAct);

                     countSus = countSus + 1;
                     //beforeReflex2 = System.currentTimeMillis();
                     
                       susListFinal = (ArrayList<Object> ) preparedStmtMethod(susVO, argList,
                           WumSqlQuery.SELECT_LABSUSCEPTIBILITES_REFLEXTEST_SUMMARY_FORWORKUP_SQLSERVER,
                           NEDSSConstants.SELECT);
                     //afterReflex2 = System.currentTimeMillis();
                     //totalReflex2 += (afterReflex2 - beforeReflex2);

  					Iterator<Object> multSuscepts = susListFinal.iterator();
  					while (multSuscepts.hasNext())
  					{
  							ResultedTestSummaryVO rtsVO = (ResultedTestSummaryVO)multSuscepts.next();
  							multipleSusceptArray.add(rtsVO);
  					}
  											//multipleSuscept.add(susListFinal);
                   }

  					if (multipleSusceptArray != null) {
  						RVO.setTheSusTestSummaryVOColl(multipleSusceptArray);
  					}

  										//if (multipleSuscept != null) {
  										//	RVO.setTheSusTestSummaryVOColl(multipleSuscept);
  										//}
                   //t4end = System.currentTimeMillis();
                 }
               
               //t3end = System.currentTimeMillis();
             
    	  
    	  
      }
  
      

	@SuppressWarnings("unchecked")
	private void setSusceptibilityDRRQ(ResultedTestSummaryVO RVO, LabReportSummaryVO labRepEvent,
			LabReportSummaryVO labRepSumm, ArrayList<Object> argList) {

		ResultedTestSummaryVO susVO = new ResultedTestSummaryVO();
		Long sourceActUid = RVO.getSourceActUid();

		ArrayList<Object> susListFinal = new ArrayList<Object>();

		ArrayList<Object> multipleSusceptArray = new ArrayList<Object>();

		argList.clear();
		argList.add("COMP"); // It should be constant
		argList.add("REFR");
		argList.add(sourceActUid);

		
			susListFinal = (ArrayList<Object>) preparedStmtMethod(susVO, argList,
					WumSqlQuery.SELECT_LABSUSCEPTIBILITES_REFLEXTEST_SUMMARY_FORWORKUP_SQLSERVER_DRRQ,
					NEDSSConstants.SELECT);
		Iterator<Object> multSuscepts = susListFinal.iterator();
		while (multSuscepts.hasNext()) {
			ResultedTestSummaryVO rtsVO = (ResultedTestSummaryVO) multSuscepts.next();
			multipleSusceptArray.add(rtsVO);
		}

		if (multipleSusceptArray != null) {
			RVO.setTheSusTestSummaryVOColl(multipleSusceptArray);
		}

	}
  
      
      
  public ArrayList<Object>  getUidSummaryVOArrayList(Collection<Object> uidLongCollection) {
    ArrayList<Object>  uidSummaryVOColl = new ArrayList<Object> ();
    Iterator<Object> itor = uidLongCollection.iterator();
    while (itor.hasNext()) {

      //System.out.println("\n\n\n\n\n  " + itor.next().getClass().toString());
      Long uidLong = (Long) itor.next();
      UidSummaryVO uidSummaryVO = new UidSummaryVO();
      uidSummaryVO.setUid(uidLong);
      uidSummaryVOColl.add(uidSummaryVO);

    }
    return uidSummaryVOColl;
  }

  private void populateDescTxtFromCachedValues(Collection<Object>
                                               reportSummaryVOCollection) {
    ReportSummaryInterface sumVO = null;
    LabReportSummaryVO labVO = null;
    LabReportSummaryVO labMorbVO = null;
    MorbReportSummaryVO morbVO = null;
    ResultedTestSummaryVO resVO = null;
    Iterator<Object> resItor = null;
    Iterator<Object> labMorbItor = null;
    ResultedTestSummaryVO susVO = null;
    Iterator<Object> susItor = null;
    Collection<Object> susColl = null;
    Collection<Object> labMorbColl = null;
    CachedDropDownValues cdv = new CachedDropDownValues();
    String tempStr = null;

   Iterator<Object>  itor = reportSummaryVOCollection.iterator();
    while (itor.hasNext()) {
      cdv = new CachedDropDownValues();
      sumVO = (ReportSummaryInterface) itor.next();
      if (sumVO instanceof LabReportSummaryVO) {
        labVO = (LabReportSummaryVO) sumVO;
        labVO.setType(NEDSSConstants.LAB_REPORT_DESC);
        if (labVO.getProgramArea() != null) {
          tempStr = cdv.getProgramAreaDesc(labVO.getProgramArea());
          labVO.setProgramArea(tempStr);
        }
        if (labVO.getJurisdiction() != null) {
          tempStr = cdv.getJurisdictionDesc(labVO.getJurisdiction());
          if(!tempStr.isEmpty())
        	  labVO.setJurisdiction(tempStr);
        }
        if (labVO.getStatus() != null) {
          tempStr = cdv.getDescForCode("ACT_OBJ_ST", labVO.getStatus());
          if (tempStr != null)
            labVO.setStatus(tempStr);
        }
        if (labVO.getTheResultedTestSummaryVOCollection() != null &&
            labVO.getTheResultedTestSummaryVOCollection().size() > 0) {
          resItor = labVO.getTheResultedTestSummaryVOCollection().iterator();
          while (resItor.hasNext()) {
            resVO = (ResultedTestSummaryVO) resItor.next();


						if (resVO.getCtrlCdUserDefined1() != null)
            {
							if (resVO.getCtrlCdUserDefined1() != null && resVO.getCtrlCdUserDefined1().equals("N"))
							{
								if (resVO.getCodedResultValue() != null &&
										!resVO.getCodedResultValue().equals("")) {
									tempStr = cdv.getCodedResultDesc(resVO.getCodedResultValue());
									resVO.setCodedResultValue(tempStr);
								}
							}
							else if (resVO.getCtrlCdUserDefined1() == null || resVO.getCtrlCdUserDefined1().equals("Y"))
							{
								if (resVO.getOrganismName() != null && resVO.getOrganismCodeSystemCd()!=null ) {
									if (resVO.getOrganismCodeSystemCd() != null && resVO.getOrganismCodeSystemCd().equals("SNM")) {
										tempStr = cdv.getOrganismListDescSNM(resVO.getCodedResultValue());
										resVO.setOrganismName(tempStr);
									}
									else {
										tempStr = cdv.getOrganismListDesc(resVO.getCodedResultValue());
										resVO.setOrganismName(tempStr);
									}
								}
							}
						}else if (resVO.getCtrlCdUserDefined1() == null ){
							if (resVO.getOrganismName() != null){
//System.out.println("got in with an org for elr");
								if (resVO.getOrganismCodeSystemCd()!=null ) {
									if (resVO.getOrganismCodeSystemCd().equals("SNM")) {
										tempStr = cdv.getOrganismListDescSNM(resVO.getCodedResultValue());
										if (tempStr == null)
										{
											resVO.setOrganismName(resVO.getOrganismName());
										}
										else
										{
											resVO.setOrganismName(tempStr);
										}
									}
									else
									{
										tempStr = cdv.getOrganismListDesc(resVO.getCodedResultValue());
										if (tempStr == null)
										{
											resVO.setOrganismName(resVO.getOrganismName());
										}
										else
										{
											resVO.setOrganismName(tempStr);
										}

									}
								}
								else
								{
									resVO.setOrganismName(resVO.getOrganismName());
								}
							}
							else
							{
								tempStr = cdv.getOrganismListDesc(resVO.getCodedResultValue());
								if (tempStr == null)
								{
									resVO.setOrganismName(resVO.getCodedResultValue());
								}
								else
								{
									resVO.setOrganismName(tempStr);
								}
							}
						}

            if ( (resVO.getCdSystemCd() != null) &&
                (! (resVO.getCdSystemCd().equals("")))) {
              if (resVO.getCdSystemCd().equals("LN")) {
                if (resVO.getResultedTestCd() != null &&
                    !resVO.getResultedTestCd().equals("")) {
                  tempStr = cdv.getResultedTestDesc(resVO.getResultedTestCd());
                  // System.out.println("\n The temStr for resVO" + tempStr);
                  if (tempStr != null && !tempStr.equals(""))
                    resVO.setResultedTest(tempStr);
                }
              }
              else if (!resVO.getCdSystemCd().equals("LN")) {
                if (resVO.getResultedTestCd() != null &&
                    !resVO.getResultedTestCd().equals("")) {
                  tempStr = cdv.getResultedTestDescLab(resVO.getCdSystemCd(),
                      resVO.getResultedTestCd());
                  if (tempStr != null && !tempStr.equals(""))
                    resVO.setResultedTest(tempStr);

                }
              }
            }
         // Added this for ER16368
            if ((resVO.getResultedTestStatusCd() != null) &&(! (resVO.getResultedTestStatusCd().equals("")))){
            	tempStr = cdv.getDescForCode("ACT_OBJ_ST",resVO.getResultedTestStatusCd());
            	 if (tempStr != null && !tempStr.equals(""))
                   	resVO.setResultedTestStatus(tempStr);
            }
         // End  ER16368
            susColl = resVO.getTheSusTestSummaryVOColl();
            if (susColl != null && susColl.size() > 0) {
              susItor = susColl.iterator();
              while (susItor.hasNext()) {
                susVO = (ResultedTestSummaryVO) susItor.next();

                if (susVO.getCodedResultValue() != null &&
                    !susVO.getCodedResultValue().equals("")) {
                  tempStr = cdv.getCodedResultDesc(susVO.getCodedResultValue());
                  if (tempStr != null && !tempStr.equals(""))
                	  susVO.setCodedResultValue(tempStr);
                }
                if (susVO.getCdSystemCd() != null &&
                    !susVO.getCdSystemCd().equals("")) {
                  if (susVO.getCdSystemCd().equals("LN")) {
                    if (susVO.getResultedTestCd() != null &&
                        !susVO.getResultedTestCd().equals("")) {
                      tempStr = cdv.getResultedTestDesc(susVO.getResultedTestCd());

                      if (tempStr != null && !tempStr.equals("")) {
                        susVO.setResultedTest(tempStr);
                      }
                    }
                  }
                  else if (!susVO.getCdSystemCd().equals("LN")) {
                    if (susVO.getResultedTestCd() != null &&
                        !susVO.getResultedTestCd().equals("")) {
                      tempStr = cdv.getResultedTestDescLab(susVO.getCdSystemCd(),
                          susVO.getResultedTestCd());
                      if (tempStr != null && !tempStr.equals("")) {
                        susVO.setResultedTest(tempStr);
                      }
                    }
                  }
                }

              } // inner while
            }
          } //outer while
        } //if
      }
      else if (sumVO instanceof MorbReportSummaryVO) {
        morbVO = (MorbReportSummaryVO) sumVO;
        if (morbVO.getCondition() != null) {
          tempStr = cdv.getConditionDesc(morbVO.getCondition());
          morbVO.setConditionDescTxt(tempStr);
        }
        if (morbVO.getProgramArea() != null) {
          tempStr = cdv.getProgramAreaDesc(morbVO.getProgramArea());
          morbVO.setProgramArea(tempStr);
        }
        if (morbVO.getJurisdiction() != null) {
          tempStr = cdv.getJurisdictionDesc(morbVO.getJurisdiction());
          morbVO.setJurisdiction(tempStr);
        }
        morbVO.setType(NEDSSConstants.MORB_REPORT_DESC);
        if (morbVO.getReportType() != null) {
          tempStr = cdv.getDescForCode("MORB_RPT_TYPE", morbVO.getReportType());
          morbVO.setReportTypeDescTxt(tempStr);
        }
        labMorbColl = morbVO.getTheLabReportSummaryVOColl();
        if (labMorbColl != null) {
          //morb has collection of labsumvo
          labMorbItor = labMorbColl.iterator();
          while (labMorbItor.hasNext()) {
            labMorbVO = (LabReportSummaryVO) labMorbItor.next();
            if (labMorbVO.getTheResultedTestSummaryVOCollection() != null) {

              //lab has collection of ResultedTestSummaryVO
              resItor = labMorbVO.getTheResultedTestSummaryVOCollection().
                  iterator();
              while (resItor.hasNext()) {
                resVO = (ResultedTestSummaryVO) resItor.next();
                if (resVO.getCodedResultValue() != null &&
                    !resVO.getCodedResultValue().equals("")) {
                  //tempStr = cdv.getCodedResultDesc(resVO.getCodedResultValue());
                  //resVO.setCodedResultValue(tempStr);
                }
                if (resVO.getResultedTest() != null &&
                    !resVO.getResultedTest().equals("")) {
                  //tempStr = cdv.getResultedTestDesc(resVO.getResultedTestCd());
                  //resVO.setResultedTest(tempStr);

                }

                susColl = resVO.getTheSusTestSummaryVOColl();
                if (susColl != null && susColl.size() > 0) {
                  //ResultedTestSummaryVO has collection of SusTestSummaryVO
                  susItor = susColl.iterator();
                  while (susItor.hasNext()) {
                    susVO = (ResultedTestSummaryVO) susItor.next();
                    if (susVO.getCodedResultValue() != null &&
                        !susVO.getCodedResultValue().equals("")) {
                      tempStr = cdv.getCodedResultDesc(susVO.
                          getCodedResultValue());
                      susVO.setCodedResultValue(tempStr);
                    }

                  } // inner while
                }
              } //outer while
            } //if lab
          } // while labmorb
        } //if labmorbcoll

      }
    }
  }

  private void populateDescTxtFromCachedValuesDRRQ(Collection<Object>
                                               reportSummaryVOCollection) {
    ReportSummaryInterface sumVO = null;
    LabReportSummaryVO labVO = null;
    ResultedTestSummaryVO resVO = null;
    Iterator<Object> resItor = null;
    CachedDropDownValues cdv = new CachedDropDownValues();
    String tempStr = null;

   Iterator<Object>  itor = reportSummaryVOCollection.iterator();
    while (itor.hasNext()) {
      cdv = new CachedDropDownValues();
      sumVO = (ReportSummaryInterface) itor.next();
      if (sumVO instanceof LabReportSummaryVO) {
        labVO = (LabReportSummaryVO) sumVO;
        labVO.setType(NEDSSConstants.LAB_REPORT_DESC);

        if (labVO.getTheResultedTestSummaryVOCollection() != null &&
            labVO.getTheResultedTestSummaryVOCollection().size() > 0) {
          resItor = labVO.getTheResultedTestSummaryVOCollection().iterator();
          while (resItor.hasNext()) {
            resVO = (ResultedTestSummaryVO) resItor.next();

         // Added this for ER16368
            if ((resVO.getResultedTestStatusCd() != null) &&(! (resVO.getResultedTestStatusCd().equals("")))){
            	tempStr = cdv.getDescForCode("ACT_OBJ_ST",resVO.getResultedTestStatusCd());
            	 if (tempStr != null && !tempStr.equals(""))
                   	resVO.setResultedTestStatus(tempStr);
            }
         // End  ER16368
            
            if (resVO.getCtrlCdUserDefined1() != null && resVO.getCtrlCdUserDefined1().equals("N"))
			{
				if (resVO.getCodedResultValue() != null &&
						!resVO.getCodedResultValue().equals("")) {
					tempStr = cdv.getCodedResultDesc(resVO.getCodedResultValue());
					resVO.setCodedResultValue(tempStr);
				}
			}
         
          } //outer while
        } //if
      }
    }
  }

  
  @SuppressWarnings("unchecked")
public String getLaboratorySystemDescTxt(String laboratory_id) {
    String codeSql;
    String codeValue = null;
    //  logic to check if code has seperate table
    codeSql = "select Laboratory_system_desc_txt str1 from  " +
          NEDSSConstants.SYSTEM_REFERENCE_TABLE +
          "..lab_coding_system where laboratory_id = ?";
    logger.debug("codeSQL is" + codeSql);
    if (laboratory_id == null) {
      codeSql = null; //ent table
      logger.error(
          "ObservationProcessor:getLaboratorySystemDescTxt: Input code == null");
    }
    else {
      // else, code is in common table

      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      GenericSummaryVO summaryResult = new GenericSummaryVO();
      arrayList.add(laboratory_id);
      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(summaryResult,
          arrayList, codeSql, NEDSSConstants.SELECT);
      summaryResult = (GenericSummaryVO) list.get(0);
      codeValue = summaryResult.getStr1();

    }
    return codeValue;

  }

   public HashMap<Object, Object> getProgramArea(String reportingLabCLIA,
                                 Collection<Object> observationVOCollection,
                                 String electronicInd)
   {

     HashMap<Object, Object> returnMap = new HashMap<Object, Object>();
     if (reportingLabCLIA == null)
     {
       returnMap.put(NEDSSConstants.ERROR,
                     NEDSSConstants.REPORTING_LAB_CLIA_NULL);
       return returnMap;
     }

     Iterator<Object> obsIt = observationVOCollection.iterator();
     Hashtable<Object, Object> paHTBL = new Hashtable<Object, Object>();

     //iterator through each resultTest
     while (obsIt.hasNext())
     {
       ObservationVO obsVO = (ObservationVO) obsIt.next();
       ObservationDT obsDt = obsVO.getTheObservationDT();

       String obsDomainCdSt1 = obsDt.getObsDomainCdSt1();
       String obsDTCode = obsDt.getCd();
       boolean found = false;
       
       //Set exclude flag to false - if any of the components - Lab Result (SNOMED or Local) or Lab Test (LOINC or
       //Local) is excluded, this flag will be set so as not to fail the derivation for this resulted test.
       programAreaDerivationExcludeFlag = false;

       // make sure you are dealing with a resulted test here.
       if ( (obsDomainCdSt1 != null) &&
           obsDomainCdSt1.equals(ELRConstants.ELR_OBSERVATION_RESULT) &&
           (obsDTCode != null) &&
           (!obsDTCode.equals(NEDSSConstants.ACT114_TYP_CD)))
       {
         // Retrieve PAs using Lab Result --> SNOMED code mapping
         // If ELR, use actual CLIA - if manual use "DEFAULT" as CLIA
         String progAreaCd;
         if ( electronicInd.equals(NEDSSConstants.ELECTRONIC_IND_ELR) )
           progAreaCd = getPAFromSNOMEDCodes(reportingLabCLIA, obsVO.getTheObsValueCodedDTCollection());
         else
           progAreaCd = getPAFromSNOMEDCodes(NEDSSConstants.DEFAULT, obsVO.getTheObsValueCodedDTCollection());


         // If PA returned, check to see if it is the same one as before.
         if (progAreaCd != null)
         {
           found = true;
           paHTBL.put(progAreaCd.trim(), progAreaCd.trim());
           if (paHTBL.size() != 1)
           {
             break;
           }

         }

         // Retrieve PAs using Resulted Test --> LOINC mapping
         if (!found)
         {
           progAreaCd = getPAFromLOINCCode(reportingLabCLIA, obsVO);
           // If PA returned, check to see if it is the same one as before.
           if (progAreaCd != null)
           {
             found = true;
             paHTBL.put(progAreaCd.trim(), progAreaCd.trim());
             if (paHTBL.size() != 1)
             {
               break;
             }
           }
         }

         // Retrieve PAs using Local Result Code to PA mapping
         if (!found)
         {
           progAreaCd = getPAFromLocalResultCode(reportingLabCLIA,
                                                 obsVO.
                                                 getTheObsValueCodedDTCollection());
           // If PA returned, check to see if it is the same one as before.
           if (progAreaCd != null)
           {
             found = true;
             //System.out.println("Found!" + progAreaCd);
             paHTBL.put(progAreaCd.trim(), progAreaCd.trim());
             if (paHTBL.size() != 1)
             {
               break;
             }
           }
         }

         // Retrieve PAs using Local Result Code to PA mapping
         if (!found)
         {
           progAreaCd = getPAFromLocalTestCode(reportingLabCLIA, obsVO);
           // If PA returned, check to see if it is the same one as before.
           if (progAreaCd != null)
           {
             found = true;
             paHTBL.put(progAreaCd.trim(), progAreaCd.trim());
             if (paHTBL.size() != 1)
             {
               break;
             }
           }
         }
         
         //If we haven't found a PA and the no components were excluded based on the exclude flag,
         //clear the PA hashtable which will fail the derivation
         if (!found && !programAreaDerivationExcludeFlag) 
         {
           paHTBL.clear();
           break;
         }
       }
     } //end of while

     if(paHTBL.size() == 0)
     {
       returnMap.put(NEDSSConstants.ERROR, ELRConstants.PROGRAM_ASSIGN_2);
     }
     else if (paHTBL.size() == 1)
     {
       returnMap.put(ELRConstants.PROGRAM_AREA_HASHMAP_KEY, paHTBL.keys().nextElement().toString());
     }
     else
     {
       returnMap.put(NEDSSConstants.ERROR, ELRConstants.PROGRAM_ASSIGN_1);
     }
     return returnMap;
   } //end of getProgramArea





  /**
   * Returns a collection of Snomed codes to be used to resolve the program area code.
   * If more than one type of snomed is resolved, return null.
   * @param ObservationVO resultTestVO
   * @param reportingLabCLIA : String
   * @return Vector
   */
   // AK - 7/25/04
   private String getPAFromSNOMEDCodes(String reportingLabCLIA,
		   Collection<Object> obsValueCodedDtColl) {

	   Vector<Object> snomedVector = new Vector<Object>();
	   SRTMapDAOImpl dao = new SRTMapDAOImpl();

	   if (reportingLabCLIA == null)
		   return null;

	   if (obsValueCodedDtColl != null) {
		   Iterator<Object> codedDtIt = obsValueCodedDtColl.iterator();
		   while (codedDtIt.hasNext()) {

			   ObsValueCodedDT codedDt = (ObsValueCodedDT) codedDtIt.next();
			   String codeSystemCd = codedDt.getCodeSystemCd();

			   if (codeSystemCd == null || codeSystemCd.trim().equals(""))
				   continue;

			   String obsCode = codedDt.getCode();
			   if (obsCode == null || obsCode.trim().equals(""))
				   continue;

			   /*
			    * 	Check if ObsValueCodedDT.codeSystemCd='L' and CLIA for Reporting Lab is available,
			    *  find Snomed For ObsValueCodedDT.code(Local Result to Snomed lookup)
			    */


			   if (!codeSystemCd.equals(ELRConstants.ELR_SNOMED_CD)){
				   // If local code and it is not excluded from PA Derivation, attempt to retrieve corresponding SNOMED code
				   if (!dao.removePADerivationExcludedLabResultCodes(obsCode, reportingLabCLIA)) {
					   ArrayList<Object>  snomedList = (ArrayList<Object> ) dao.getSnomed(codedDt.getCode(),
							   // ELRConstants.TYPE = "LR"
							   ELRConstants.TYPE,
							   reportingLabCLIA);
					   if ( ( (Integer) snomedList.get(1)).intValue() == 1)
						   snomedVector.addElement(snomedList.get(0));
				   } else {
					   //If so, set exclude flag so we won't fail this resulted test if no PA is derived for it
					   programAreaDerivationExcludeFlag = true;
				   }
			   }
		   
		   /*  If already coded using SNOMED code, just add it to the return array.
		    *  check if ObsValueCodedDT.codeSystemCd="SNM", use ObsValueCodedDT.code for Snomed
     	    *  Need to check SNOMED codes for Program Area Derivation Exclusion flag - don't include codes with this set
		    */
		   else if (codeSystemCd.equals("SNM")) {
			   // If snomed code and it is not excluded from PA Derivation, add it to the SNOMED Vector
			   if (!dao.removePADerivationExcludedSnomedCodes(obsCode)) 
				   snomedVector.addElement(obsCode);
			   else
				   //Otherwise don't add it and set the exclude flag so we won't fail this resulted test if no PA is derived for it
				   programAreaDerivationExcludeFlag = true;
		   }
	   } //end of while


	   // Now that we have resolved all the SNOMED codes, try to derive the PA
	   if (snomedVector != null &&
			   snomedVector.size() == obsValueCodedDtColl.size())
		   return getProgAreaCd(snomedVector, reportingLabCLIA,
				   NEXT, ELRConstants.ELR_SNOMED_CD);

   } //end of if

   return null;
} //end of getPAFromSNOMED(...)

  /**
   * Attempts to resolve a ProgramAreaCd based on Loinc.
   * @param reportingLabCLIA : String
   * @param obsDt : ObservationDT
   * @return loincVector : Vector
   * @throws NEDSSDAOSysException
   */
  // AK - 7/25/04
  private String getPAFromLOINCCode(String reportingLabCLIA,
                                    ObservationVO resultTestVO) throws
      NEDSSDAOSysException {

    ObservationDT obsDt = resultTestVO.getTheObservationDT();
    if (obsDt == null || reportingLabCLIA == null)
      return null;

    String cdSystemCd = obsDt.getCdSystemCd();
    if (cdSystemCd == null || cdSystemCd.trim().equals(""))
      return null;

    String obsCode = obsDt.getCd();
    if (obsCode == null || obsCode.trim().equals(""))
      return null;

    SRTMapDAOImpl dao = new SRTMapDAOImpl();
    Vector<Object> loincVector = new Vector<Object>();

    if(cdSystemCd.equals(ELRConstants.ELR_OBSERVATION_LOINC))
    {
      //Check if this loinc code should be excluded from Program Area derivation
      //If so, set exclude flag so we won't fail this resulted test if no PA is derived for it
      if (dao.removePADerivationExcludedLoincCodes(obsCode)){
      	programAreaDerivationExcludeFlag = true;
        return null;
      }
      
      loincVector.addElement(obsCode);
    }
    else
    {
        //Check if this local test code should be excluded from Program Area derivation
        //If so, set exclude flag so we won't fail this resulted test if no PA is derived for it
        if (dao.removePADerivationExcludedLabTestCodes(obsCode, reportingLabCLIA)) {
        	programAreaDerivationExcludeFlag = true;
            return null;
        }
    	
    	ArrayList<Object>  loincList = (ArrayList<Object> ) dao.getMsgProgAreaLoincSnomed(obsCode,
         "LT",
         reportingLabCLIA);
     if ( ( (Integer) loincList.get(1)).intValue() == 1)
       loincVector.addElement(loincList.get(0));
    }

   
/*
    if (!cdSystemCd.equals(ELRConstants.ELR_OBSERVATION_LOINC)) {
      ArrayList<Object> loincList = (ArrayList<Object> ) dao.getMsgProgAreaLoincSnomed(obsCode,
          "LT",
          reportingLabCLIA);
      if ( ( (Integer) loincList.get(1)).intValue() == 1)
        loincVector.addElement(loincList.get(0));
    }
    else if (cdSystemCd.equals(ELRConstants.ELR_OBSERVATION_LOINC))
      loincVector.addElement(obsCode);
*/
      // Now that we have resolved all the LOINC codes, try to derive the PA
    return getProgAreaCd(loincVector, reportingLabCLIA, NEXT,
                         ELRConstants.ELR_OBSERVATION_LOINC);

  } //end of getLoincColl(...)

  /**
   * Attempts to resolve a program area cd based on Local Result code.
   * @param codedColl : Collection
   * @param reportingLabCLIA : String
   * @param ObservationVO : ObservationVO
   * @return progrAreaCd : String
   */
  // AK - 7/25/04
  public String getPAFromLocalResultCode(String reportingLabCLIA,
		  Collection<Object> obsValueCodedDtColl) {
	  String lastProgAreaCd = null;
	  String progAreaCd = null;
	  SRTMapDAOImpl dao = new SRTMapDAOImpl();

	  if (obsValueCodedDtColl == null || reportingLabCLIA == null)
		  return null;

	  Vector<Object> codeVector = new Vector<Object>();
	  Iterator<Object> codedDtIt = obsValueCodedDtColl.iterator();

	  while (codedDtIt.hasNext()) {
		  ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) codedDtIt.next();
		  String code = obsValueCodedDT.getCode();
		  String codeSystemCd = obsValueCodedDT.getCodeSystemCd();
		  if (code != null && codeSystemCd!=null && !codeSystemCd.equals(ELRConstants.ELR_SNOMED_CD)) {
		      //Check if this local result code should be excluded from Program Area derivation
			  if (!dao.removePADerivationExcludedLabResultCodes(code, reportingLabCLIA)){
				  codeVector.addElement(code);
			  } else {
			      //If so, set exclude flag so we won't fail this resulted test if no PA is derived for it
				  programAreaDerivationExcludeFlag = true;

			  }

		  }

		  String codeSql = null;
		  // Use default condition code to PA mapping
		  codeSql = ELRConstants.
			  SELECT_LOCAL_RESULT_DEFAULT_CONDITION_PROGRAM_AREA_CD_SQL;

		  progAreaCd = getProgAreaCdLocalDefault(codeVector, reportingLabCLIA,
				  NEXT, codeSql);

		  if (progAreaCd == null) {

			  // Use default PA code
			 codeSql = ELRConstants.
				  SELECT_LOCAL_RESULT_DEFAULT_PROGRAM_AREA_CD_SQL;

			  progAreaCd = getProgAreaCdLocalDefault(codeVector, reportingLabCLIA,
					  NEXT, codeSql);
		  }

		  if (lastProgAreaCd == null)
			  lastProgAreaCd = progAreaCd;
		  else
			  if (!lastProgAreaCd.equals(progAreaCd))

				  // Different PAs returned - error.
				  return null;

	  }
	  return lastProgAreaCd;

  } //end of method

  /**
   * Attempts to resolve a program area cd based on LocalTestDefault cd.
   * @param reportingLabCLIA : String
   * @param obsDt : ObservationVO
   * @return progAreaCd : String
   */
  // AK - 7/25/04
  public String getPAFromLocalTestCode(String reportingLabCLIA,
                                       ObservationVO resultTestVO) {

    ObservationDT obsDt = resultTestVO.getTheObservationDT();
    
    //If this test has a LOINC, we should return and not treat it as a local test 
    if (obsDt.equals(ELRConstants.ELR_OBSERVATION_LOINC))
    	return null;
    
    String code = getLocalTestCode(obsDt);
    SRTMapDAOImpl dao = new SRTMapDAOImpl();

    if (reportingLabCLIA == null || code == null || code.trim().equals(""))
      return null;

    //Check if this code should be excluded from Program Area derivation
    if (dao.removePADerivationExcludedLabTestCodes(code, reportingLabCLIA))
      return null;
        
    String progAreaCd = null;

    Vector<Object> codeVector = new Vector<Object>();
    codeVector.addElement(code);

    String codeSql = null;
    // Use default condition code to PA mapping
    codeSql = ELRConstants.
          SELECT_LOCAL_TEST_DEFAULT_CONDITION_PROGRAM_AREA_CD_SQL;

    progAreaCd = getProgAreaCdLocalDefault(codeVector, reportingLabCLIA,
                                           NEXT, codeSql);

    if (progAreaCd == null) {
        codeSql = ELRConstants.SELECT_LOCAL_TEST_DEFAULT_PROGRAM_AREA_CD_SQL;

      progAreaCd = getProgAreaCdLocalDefault(codeVector, reportingLabCLIA,
                                             NEXT, codeSql);
    }
    return progAreaCd;

  } //end of method

  public ObservationVO labLoincSnomedLookup(ObservationVO obsVO, String labClia) {
  	/*
  	 * Ajith 7/15/05
  	 * When a reporting facility without a clia number is selected by the user, the droplist
  	 * of lab tests presented comes from the "DEFAULT" group. Furthermore, even "DEFAULT" lab tests
  	 * and results can be mapped to LOINC and SNOMEDs( respectively ) just as the lab specific tests
  	 * and results using the labtest_loinc and labresult_snomed DWYER tables.
  	 * Because of these reasons, we must use the clia number "DEFAULT" when no clia is available
  	 * for the selected facility. Otherwise alternate codes coming out of LOINC and SNOMED associations
  	 * will not be set on the OT and RT.
  	 *
  	 */
    if (labClia == null && obsVO.getTheObservationDT().getCdSystemCd().equals(NEDSSConstants.DEFAULT))
    	labClia = NEDSSConstants.DEFAULT ;

    if (labClia == null )
      return obsVO;


//System.out.println("Before obsVO.getTheObservationDT().getAltCdSystemCd(): " + obsVO.getTheObservationDT().getAltCdSystemCd());
//System.out.println("Before obsVO.getTheObservationDT().getAltCd(): " + obsVO.getTheObservationDT().getAltCd());
//System.out.println("Before obsVO.getTheObservationDT().getCdDerivedInd(): " + obsVO.getTheObservationDT().getCdDerivedInd());
    //Do lookup for the observation dt
    doLoincCdLookupForObservationDT(obsVO.getTheObservationDT(), labClia);

    //Do lookup for all the obsValueCodedDT
    doSnomedCdLookupForObsValueCodedDTs(obsVO.getTheObsValueCodedDTCollection(),
                                        labClia);

    // System.out.println("/n After obsVO.getTheObservationDT().getAltCdSystemCd(): " + obsVO.getTheObservationDT().getAltCdSystemCd());
    //System.out.println("After obsVO.getTheObservationDT().getAltCd(): " + obsVO.getTheObservationDT().getAltCd());
    // System.out.println("After obsVO.getTheObservationDT().getCdDerivedInd(): " + obsVO.getTheObservationDT().getCdDerivedInd());

    return obsVO;
  }

  private void doLoincCdLookupForObservationDT(ObservationDT obsDT,
                                               String labClia) {
    String cdSystemCd = obsDT.getCdSystemCd();
    String altCdSystemCd = obsDT.getAltCdSystemCd();

    if (cdSystemCd != null && !cdSystemCd.equals("LN") && altCdSystemCd == null) {
      //System.out.println("/n labClia: " + labClia);
      // System.out.println("obsDT.getCd(): " + obsDT.getCd());
      List<Object> loincCdList = new SRTMapDAOImpl().findLoincCds(labClia, obsDT.getCd());
      //If only one loinc cd found, use it, otherwise discard
      if (loincCdList != null && loincCdList.size() == 1) {
        obsDT.setAltCdSystemCd("LN");
        obsDT.setAltCd( (String) loincCdList.get(0));
        obsDT.setCdDerivedInd("Y");
      }
    }
  }

  private void doSnomedCdLookupForObsValueCodedDTs(Collection<Object> obsValueCodedDTs,
      String labClia) {
    if (obsValueCodedDTs == null || obsValueCodedDTs.isEmpty())
      return;

    for (Iterator<Object> it = obsValueCodedDTs.iterator(); it.hasNext(); ) {
      ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) it.next();
      if (obsValueCodedDT == null)
        continue;

      String cdSystemCd = obsValueCodedDT.getCodeSystemCd();
      String altCdSystemCd = obsValueCodedDT.getAltCdSystemCd();

      if (cdSystemCd != null && !cdSystemCd.equals("SNM") && altCdSystemCd == null) {
        // System.out.println("/n labClia: " + labClia);
        //  System.out.println("obsValueCodedDT.getCode(): " + obsValueCodedDT.getCode());
        List<Object> snomedCdList = new SRTMapDAOImpl().findSnomedCds(labClia,
            obsValueCodedDT.getCode());
        //If only one snomed cd found, use it, otherwise discard
        if (snomedCdList != null && snomedCdList.size() == 1) {
          obsValueCodedDT.setAltCdSystemCd("SNM");
          obsValueCodedDT.setAltCd( (String) snomedCdList.get(0));
          obsValueCodedDT.setCodeDerivedInd("Y");
        }
      }

    }
  }

  @SuppressWarnings("unchecked")
public TreeMap<Object, Object> getCodeSystemDescription(String laboratory_id)
  {
    String codeSql = null;
    TreeMap<Object, Object> codeMap = null;
   // Use default condition code to PA mapping
    
      codeSql = "select a.laboratory_id str1, a.laboratory_system_desc_txt str2 from "+
          NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..lab_coding_system a where laboratory_id = '" + laboratory_id + "'";

    GenericSummaryVO summaryVO = new GenericSummaryVO();
    ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(summaryVO, null,
        codeSql, NEDSSConstants.SELECT);
    codeMap = new TreeMap<Object, Object>();
    for (int count = 0; count < list.size(); count++) {
      GenericSummaryVO genSumm = (GenericSummaryVO) list.get(count);
      codeMap.put(NEDSSConstants.KEY_CODINGSYSTEMCD, genSumm.getStr1());
      codeMap.put(NEDSSConstants.KEY_CODESYSTEMDESCTXT , genSumm.getStr2());
    }
    return codeMap;
  }
/*
 *   getDerivedConditionList - get the list of conditions for the set of resulted tests
 *   @param reportingLabId (clia)
 *   @param observationVOCollection - the Result Tests
 *   @param electronicInd
 *   @return arrayList of condition codes
 */
public ArrayList<String> getDerivedConditionList(String reportingLabCLIA,
			Collection<Object> observationVOCollection, String electronicInd) {
		int noConditionFoundForResultedTestCount = 0;
		ArrayList<String> returnList =  new ArrayList<String> ();

		Iterator<Object> obsIt = observationVOCollection.iterator();
		// iterator through each resultTest
		while (obsIt.hasNext()) {
			ArrayList<String> resultedTestConditionList =  new ArrayList<String> ();
			ObservationVO obsVO = (ObservationVO) obsIt.next();
			ObservationDT obsDt = obsVO.getTheObservationDT();

			String obsDomainCdSt1 = obsDt.getObsDomainCdSt1();
			String obsDTCode = obsDt.getCd();

			// make sure you are dealing with a resulted test here.
			if ((obsDomainCdSt1 != null)
					&& obsDomainCdSt1
							.equals(ELRConstants.ELR_OBSERVATION_RESULT)
					&& (obsDTCode != null)
					&& (!obsDTCode.equals(NEDSSConstants.ACT114_TYP_CD))) {
				
				// Retrieve Condition List using SNM Lab Result --> SNOMED code mapping
				// If ELR, use actual CLIA - if manual use "DEFAULT" as CLIA
				if (electronicInd.equals(NEDSSConstants.ELECTRONIC_IND_ELR))
					resultedTestConditionList = getConditionsFromSNOMEDCodes(reportingLabCLIA,
							obsVO.getTheObsValueCodedDTCollection());
				else
					resultedTestConditionList = getConditionsFromSNOMEDCodes(NEDSSConstants.DEFAULT,
							obsVO.getTheObsValueCodedDTCollection());

				// if no conditions found - try LN to retrieve Condition using Resulted Test --> LOINC mapping
				if (resultedTestConditionList.isEmpty()) {
					String loincCondition = getConditionForLOINCCode(reportingLabCLIA, obsVO);
					if (loincCondition!= null && !loincCondition.isEmpty())
						resultedTestConditionList.add(loincCondition);
				}

				// none - try LR to retrieve default Condition using Local Result Code to condition mapping
				if (resultedTestConditionList.isEmpty()) {
					String localResultDefaultConditionCd = getConditionCodeForLocalResultCode(reportingLabCLIA,
							obsVO.getTheObsValueCodedDTCollection());
					if (localResultDefaultConditionCd != null && !localResultDefaultConditionCd.isEmpty())
						resultedTestConditionList.add(localResultDefaultConditionCd);
				}
				// none - try LT to retrieve default Condition using Local Test Code to condition mapping
				if (resultedTestConditionList.isEmpty()) {
					String localTestDefaultConditionCd = getConditionCodeForLocalTestCode(reportingLabCLIA,
							obsVO);
					if (localTestDefaultConditionCd != null && !localTestDefaultConditionCd.isEmpty())
						resultedTestConditionList.add(localTestDefaultConditionCd);
				}
				// none - see if default condition code exists for the resulted lab test
				if (resultedTestConditionList.isEmpty()) {
					String defaultLabTestConditionCd = getDefaultConditionForLabTestCode(obsDTCode, reportingLabCLIA);
					if (defaultLabTestConditionCd != null && !defaultLabTestConditionCd.isEmpty())
						resultedTestConditionList.add(defaultLabTestConditionCd);
				}
				if (resultedTestConditionList.isEmpty()) {
					noConditionFoundForResultedTestCount = noConditionFoundForResultedTestCount + 1;
				}
				//if we found conditions add them to the return list
				if (!resultedTestConditionList.isEmpty()) {
					Set<String> hashset = new HashSet<String>();
					hashset.addAll(returnList);
					hashset.addAll(resultedTestConditionList);
					//get rid of dups..
					returnList = new ArrayList<String>(hashset);
				} //resulted test condition list not empty
			} //end of if valid resulted test
		} // end of while more resulted tests
		//if we couldn't derive a condition for a test, return no conditions
		if (noConditionFoundForResultedTestCount > 0)
			returnList.clear(); //incomplete list - return empty list

		return returnList;
} // end of ConditionList

/**
 * Returns a List of Condition Codes associated with the passed Snomed codes.
 * 
 * @param reportingLabCLIA : String
 * @param obsValueCodedDtColl : Collection
 * @return ArrayList<string>
 */
 // AK - 7/25/04
	private ArrayList<String> getConditionsFromSNOMEDCodes(
			String reportingLabCLIA, Collection<Object> obsValueCodedDtColl) {

		ArrayList<String> snomedConditionList = new ArrayList<String>();
		SRTMapDAOImpl dao = new SRTMapDAOImpl();

		if (obsValueCodedDtColl != null) {
			Iterator<Object> codedDtIt = obsValueCodedDtColl.iterator();
			while (codedDtIt.hasNext()) {
				String snomedCd = "";
				String conditionCd = "";
				ObsValueCodedDT codedDt = (ObsValueCodedDT) codedDtIt.next();
				String codeSystemCd = codedDt.getCodeSystemCd();

				if (codeSystemCd == null || codeSystemCd.trim().equals(""))
					continue;

				String obsCode = codedDt.getCode();
				if (obsCode == null || obsCode.trim().equals(""))
					continue;

				/* If the code is not a Snomed code, try to get the snomed code.
				 * Check if ObsValueCodedDT.codeSystemCd='L' and CLIA for
				 * Reporting Lab is available, find the Snomed code for
				 * ObsValueCodedDT.code(Local Result to Snomed lookup)
				 */
				if (!codeSystemCd.equals(ELRConstants.ELR_SNOMED_CD)) {
					ArrayList<Object> snomedList = (ArrayList<Object>) dao
							.getSnomed(codedDt.getCode(),
							// ELRConstants.TYPE = "LR"
									ELRConstants.TYPE, reportingLabCLIA);
					if (((Integer) snomedList.get(1)).intValue() == 1)
						snomedCd = (String) snomedList.get(0);
					else continue;
				}

				/*
				 * If already coded using SNOMED code, just add it to the return
				 * array. check if ObsValueCodedDT.codeSystemCd="SNM", use
				 * ObsValueCodedDT.code for Snomed
				 */
				else if (codeSystemCd.equals("SNM")) {
					snomedCd = obsCode;
				}
				
				//if these is a Snomed code, see if we can get a corresponding condition for it
				try {
					conditionCd = dao.getConditionForSnomedCode(snomedCd);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (conditionCd != null && !conditionCd.isEmpty())
					snomedConditionList.add(conditionCd);
			} // end of while has next
		} // end if collection not null
		return snomedConditionList;
	} //getConditionsFromSNOMEDCodes()
	
	private String getConditionForLOINCCode(String reportingLabCLIA,
			ObservationVO resultTestVO) throws NEDSSDAOSysException {

		String loincCd = "";
		ObservationDT obsDt = resultTestVO.getTheObservationDT();
		if (obsDt == null || reportingLabCLIA == null)
			return null;

		String cdSystemCd = obsDt.getCdSystemCd();
		if (cdSystemCd == null || cdSystemCd.trim().equals(""))
			return null;

		String obsCode = obsDt.getCd();
		if (obsCode == null || obsCode.trim().equals(""))
			return null;

		SRTMapDAOImpl dao = new SRTMapDAOImpl();
		if (cdSystemCd.equals(ELRConstants.ELR_OBSERVATION_LOINC)) {
			loincCd = obsCode;
		} else {
			ArrayList<Object> loincList = (ArrayList<Object>) dao
					.getMsgProgAreaLoincSnomed(obsCode, "LT", reportingLabCLIA);
			if (((Integer) loincList.get(1)).intValue() == 1)
				loincCd = (String) loincList.get(0);
		}

		// If we have resolved the LOINC code, try to derive the condition
		if (loincCd == null || loincCd.isEmpty())
			return loincCd;

		String conditionCd = "";
		try {
			conditionCd = dao.getConditionForLoincCode(loincCd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (conditionCd);

	} //end of getConditionForLoincCode()
	
	 /**
	   * Gets the default condition for a Local Result code.
	   * If we find that it maps to more than one condition code, return nothing.
	   * @param reportingLabCLIA : String
	   * @param obsValueCodedDtColl: Collection
	   * @return conditionCd : String
	   */
	  public String getConditionCodeForLocalResultCode(String reportingLabCLIA,
			  Collection<Object> obsValueCodedDtColl) {
		  String conditionCd = "";
		  HashMap<String, String> conditionMap = new HashMap<String,String>();
		  if (obsValueCodedDtColl == null || reportingLabCLIA == null)
			  return null;

		SRTMapDAOImpl dao = new SRTMapDAOImpl();
	  	Iterator<Object> codedDtIt = obsValueCodedDtColl.iterator();
		  while (codedDtIt.hasNext()) {
			  ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) codedDtIt.next();
			  String code = obsValueCodedDT.getCode();
			  //String codeSystemCd = obsValueCodedDT.getCodeSystemCd();
			  if (code != null) {
					String defaultCondition = dao.getDefaultConditionForLocalResultCode(code, reportingLabCLIA);
					if (defaultCondition != null && !defaultCondition.isEmpty()) {
						conditionCd = defaultCondition;
						conditionMap.put(defaultCondition, code);
					}
			  } 
		}
		if (conditionMap.size() > 1 || conditionMap.isEmpty())
			return("");
		else return(conditionCd);
	  }	
	  
		/**
	   * Gets the default condition for the Local Test code.
	   * @param resultTestVO : Collection
	   * @param reportingLabCLIA : String
	   * @return conditionCd : String
	   */
	  public String getConditionCodeForLocalTestCode(String reportingLabCLIA,
              ObservationVO resultTestVO) {
	  
		  //edit checks
		  if (reportingLabCLIA == null || resultTestVO == null)
			  return null;
		  ObservationDT obsDt = resultTestVO.getTheObservationDT();
	      if (obsDt.getCd() == null || obsDt.getCd().equals("") ||
		            obsDt.getCd().equals(" ") || obsDt.getCdSystemCd() == null)  
	    	  return null;
	      
	      String testCd = obsDt.getCd();

		  SRTMapDAOImpl dao = new SRTMapDAOImpl();

		  String conditionCd = dao.getDefaultConditionForLocalResultCode(testCd, reportingLabCLIA);
		  return(conditionCd);
	  } //getConditionCodeForLocalTestCode()	
	  
		/**
	   * Gets the default condition for the Lab Test code.
	   * @param lab_test_cd
	   * @return conditionCd : String
	   */
	  public String getDefaultConditionForLabTestCode(String labTestCd, String reportingLabCLIA) {
	  
		  SRTMapDAOImpl dao = new SRTMapDAOImpl();

		  String conditionCd = dao.getDefaultConditionForLabTest(labTestCd, reportingLabCLIA );
		  //see if the DEFAULT is set for the lab test if still not found..
		  if ((conditionCd == null || conditionCd.isEmpty()) && !reportingLabCLIA.equals(NEDSSConstants.DEFAULT))
			   conditionCd = dao.getDefaultConditionForLabTest(labTestCd, NEDSSConstants.DEFAULT);
		  
		  return(conditionCd);
	  } 		  
	  
		public String getObservationList(Set<Object> observatioUids) throws NEDSSSystemException {
			try {
				StringBuffer observationList = new StringBuffer("");
				if (observatioUids == null)
					return observationList.toString();
				for (Iterator<Object> allIt = observatioUids.iterator(); allIt.hasNext();) {
					String cd = (String) allIt.next();
					if (cd != null) {
						if (cd.trim().length() != 0) {
							observationList = observationList.append(cd).append(", ");
						}
					}
				}
				if (observationList.toString().trim().length() > 0) {

					return observationList.toString().trim().substring(0, (observationList.toString().trim().length() - 1));
				} else {
					return observationList.toString();
				}
			} catch (Exception ex) {
				logger.fatal("Error while getObservationList", ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}
		
	public String getlabUids(Collection<Object> UIdVOs) {

		try {
			StringBuffer sb = new StringBuffer("");
			if (UIdVOs == null || UIdVOs.size() == 0)
				return sb.toString();
			else {
				Iterator<Object> ite = UIdVOs.iterator();
				while (ite.hasNext()) {
					@SuppressWarnings("unchecked")
					ArrayList<Object> uidList = (ArrayList<Object>) ite.next();
					if (uidList != null && uidList.size() > 0) {
						Iterator<Object> list = uidList.iterator();
						while (list.hasNext()) {
							UidSummaryVO uidSumm = (UidSummaryVO) list.next();
							sb.append(String.valueOf(uidSumm.getUid().longValue())).append(",");
						}
					}

				}
				if (sb.toString().trim().length() > 0) {
					return sb.toString().trim().substring(0, (sb.toString().trim().length() - 1));
				} else
					return sb.toString();
			}
		} catch (Exception ex) {
			logger.fatal("Error while getlabUids", ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}
}