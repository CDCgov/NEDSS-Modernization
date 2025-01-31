package gov.cdc.nedss.webapp.nbs.action.observation.review.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.file.WorkupLoad;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;

public class ObservationReviewQueueUtil {
	
	static final LogUtils logger = new LogUtils(ObservationReviewQueueUtil.class.getName());
	QueueUtil queueUtil = new QueueUtil();
	PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	public ArrayList<Object> getJurisDropDowns(Collection<Object>  observationReviewColl) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if (obsSummaryVO.getJurisdiction()!= null) {
					invMap.put(obsSummaryVO.getJurisdiction(),obsSummaryVO.getJurisdiction());
				}
				if(obsSummaryVO.getJurisdiction() == null || obsSummaryVO.getJurisdiction().trim().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}

		}

		return queueUtil.getUniqueValueFromMap(invMap);

	}
	
	public ArrayList<Object> getObservationType(Collection<Object>  observationReviewColl){
		Map<Object, Object>  obsTypeMap = new HashMap<Object,Object>();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if(obsSummaryVO.getType() != null){
					obsTypeMap.put(obsSummaryVO.getType(), obsSummaryVO.getType());
				}
				if(obsSummaryVO.getType() == null || obsSummaryVO.getType().trim().equals("")){
					obsTypeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(obsTypeMap);
	}
	
	public ArrayList<Object> getStartDateDropDownValues() {
		return queueUtil.getStartDateDropDownValues();
	}
	
	public ArrayList<Object> getResultedTestandCondition(Collection<Object>  observationReviewColl){
		Map<Object, Object>  rTestMap = new HashMap<Object,Object>();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				
				for(int i=0; obsSummaryVO.getCondition()!=null && i<obsSummaryVO.getCondition().size(); i++){
					if(obsSummaryVO.getCondition() != null && obsSummaryVO.getCondition().get(i)!=null){
					
						rTestMap = getTestsfromObs(obsSummaryVO.getCondition().get(i),rTestMap);
					}
					if(obsSummaryVO.getCondition() == null || obsSummaryVO.getCondition().get(i)==null || obsSummaryVO.getCondition().get(i).trim().equals("")){
						rTestMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
				if(obsSummaryVO.getCondition()!=null && obsSummaryVO.getCondition().size()==0)
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
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				
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
	
	public ArrayList<Object> getProgramArea(Collection<Object>  observationReviewColl){
		Map<Object, Object>  rProgMap = new HashMap<Object,Object>();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if(obsSummaryVO.getProgramArea() != null){
					rProgMap = getTestsfromObs(obsSummaryVO.getProgramArea(),rProgMap);			
				}
				if(obsSummaryVO.getProgramArea() == null || obsSummaryVO.getProgramArea().trim().equals("")){
					rProgMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(rProgMap); 
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
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterPatient = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}
		String filterLocalId = null;
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterLocalId = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
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
			obsSummaryVOs = filterObservationsbyJurisdiction(
					obsSummaryVOs, jurisMap);
		
		if(dateMap != null && dateMap.size()>0)
			obsSummaryVOs = filterObservationsbyStartdate(obsSummaryVOs,dateMap);
		if(obsTypeMap != null && obsTypeMap.size()>0)
			obsSummaryVOs = filterObservationsbyType(obsSummaryVOs,obsTypeMap);
		
		if(condMap != null && condMap.size()>0)
			obsSummaryVOs = filterObservationsbyTestConditions(obsSummaryVOs,condMap);
	
		if(prgAreaMap != null && prgAreaMap.size()>0)
			obsSummaryVOs = filterObservationsbyProgramArea(obsSummaryVOs,prgAreaMap);
		if(filterPatient!= null){
			obsSummaryVOs = filterByText(obsSummaryVOs, filterPatient, NEDSSConstants.INV_PATIENT);
		}
		if(filterLocalId!= null){
			obsSummaryVOs = filterByText(obsSummaryVOs, filterLocalId, NEDSSConstants.INV_LOCAL_ID);
		}
		if(filterProviderReportingFacility!= null){
			obsSummaryVOs = filterByText(obsSummaryVOs, filterProviderReportingFacility, NEDSSConstants.INV_PROVIDER);
		}
		
		if(desMap != null && desMap.size()>0)
			obsSummaryVOs = filterObservationsbyDescription(obsSummaryVOs,desMap);
	
//		if(filterDescription!= null){
//			obsSummaryVOs = filterByText(obsSummaryVOs, filterDescription, NEDSSConstants.INV_DESCRIPTION);
//		}
		
		return obsSummaryVOs;
		
	}
	
	public Collection<Object>  filterByText(
			Collection<Object>  obsSummaryVOs, String filterByText,String column) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
		if (obsSummaryVOs != null) {
			Iterator<Object> iter = obsSummaryVOs.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter.next();
				if(column.equals(NEDSSConstants.INV_PATIENT) && obsSummaryVO.getFullNameNoLnk() != null 
						&& obsSummaryVO.getFullNameNoLnk().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(obsSummaryVO);
				}
				if(column.equals(NEDSSConstants.INV_LOCAL_ID) && obsSummaryVO.getObservationId() != null 
						&& obsSummaryVO.getObservationId().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(obsSummaryVO);
				}
				if(column.equals(NEDSSConstants.INV_PROVIDER) && obsSummaryVO.getProviderReportingFacility() != null 
						&& obsSummaryVO.getProviderReportingFacility().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(obsSummaryVO);
				}
				
				/*if(column.equals(NEDSSConstants.INV_DESCRIPTION) && obsSummaryVO.getDescription() != null 
						&& obsSummaryVO.getDescription().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(obsSummaryVO);
			
				}*/
			}
		}
		}catch(Exception ex){			 
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newInvColl;
	}
	
	public Collection<Object>  filterObservationsbyJurisdiction(
			Collection<Object>  observationReviewColl, Map<Object,Object> jurisMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if (obsSummaryVO.getJurisdiction()!= null
						&& jurisMap != null
						&& jurisMap.containsKey(obsSummaryVO
								.getJurisdiction())) {
					newInvColl.add(obsSummaryVO);
				}
				if(obsSummaryVO.getJurisdiction() == null || obsSummaryVO.getJurisdiction().equals("")){
					if(jurisMap != null && jurisMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(obsSummaryVO);
					}
				}

			}

		}
		return newInvColl;

	}
	
	public Collection<Object>  filterObservationsbyStartdate(
			Collection<Object>  observationReviewColl, Map<Object,Object> dateMap) {
		Collection<Object>  newObsColl = new ArrayList<Object> ();
		Map<Object, Object>  newObsMap = new HashMap<Object,Object>();
		Collection<Object>  blankObsColl = new ArrayList<Object> ();
		String strDateKey = null;
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if (obsSummaryVO.getDateReceived()!= null && dateMap != null
						&& (dateMap.size()>0 )) {
					Collection<Object>  dateSet = dateMap.keySet();
					if(dateSet != null){
						Iterator<Object>  iSet = dateSet.iterator();
					while (iSet.hasNext()){
						 strDateKey = (String)iSet.next();
						if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
                    	   if(queueUtil.isDateinRange(obsSummaryVO.getDateReceived(),strDateKey)){
                    		   newObsMap.put(obsSummaryVO.getObservationId(), obsSummaryVO);
                    	   }	
                           		
						}  
                       }
					  }
					}
		
				if(obsSummaryVO.getDateReceived() == null || obsSummaryVO.getDateReceived().equals("")){
					if(dateMap != null && (dateMap.containsKey(NEDSSConstants.BLANK_KEY)) ||(dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)) ){
						newObsMap.put(obsSummaryVO.getObservationId(), obsSummaryVO);
					}
				}

			}
		} 	

		
		return queueUtil.convertMaptoColl(newObsMap);

	}
	
	public Collection<Object>  filterObservationsbyType(
			Collection<Object>  observationReviewColl, Map<Object,Object> typeMap) {
		Collection<Object>  newObsColl = new ArrayList<Object> ();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if (obsSummaryVO.getType()!= null
						&& typeMap != null
						&& typeMap.containsKey(obsSummaryVO
								.getType())) {
					newObsColl.add(obsSummaryVO);
				}
				if(obsSummaryVO.getType() == null || obsSummaryVO.getType().equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newObsColl.add(obsSummaryVO);
					}
				}

			}

		}
		return newObsColl;

	}
	
	public Collection<Object>  filterObservationsbyTestConditions(
			Collection<Object>  observationReviewColl, Map<Object,Object> testMap) {
		Collection<Object>  newObsColl = new ArrayList<Object> ();
		
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if (obsSummaryVO.getCondition()!= null
						&& testMap != null){
					
					for(int i=0; i<obsSummaryVO.getCondition().size();i++){
					Map<Object, Object>  rtestMap = new HashMap<Object,Object>();
					rtestMap = getTestsfromObs(obsSummaryVO.getCondition().get(i),rtestMap);
					Collection<Object>  keyColl = rtestMap.keySet();	
					if(keyColl != null){
						Iterator<Object>  it = keyColl.iterator();
						while(it.hasNext()){
							String test = (String)it.next();
							if(testMap.containsKey(test)){
								newObsColl.add(obsSummaryVO);
								break;
							}
						}
					 
					}
					}
					
				}if(obsSummaryVO.getCondition()==null || obsSummaryVO.getCondition().size() == 0){
					if(testMap != null && testMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newObsColl.add(obsSummaryVO);
					}
				}
			}

		}
		return newObsColl;

	}
	
	
	//Pruthvi Change
	public Collection<Object>  filterObservationsbyDescription(
			Collection<Object>  observationReviewColl, Map<Object,Object> testMap) {
		Collection<Object>  newObsColl = new ArrayList<Object> ();
		
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
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
	public Collection<Object>  filterObservationsbyProgramArea(
			Collection<Object>  observationReviewColl, Map<Object,Object> prgMap) {
		Collection<Object>  newObsColl = new ArrayList<Object> ();
		if (observationReviewColl != null) {
			Iterator<Object>  iter = observationReviewColl.iterator();
			while (iter.hasNext()) {
				ObservationSummaryDisplayVO obsSummaryVO = (ObservationSummaryDisplayVO) iter
						.next();
				if (obsSummaryVO.getProgramArea() != null
						&& prgMap != null
						&& prgMap.containsKey(obsSummaryVO
								.getProgramArea())) {
					newObsColl.add(obsSummaryVO);
				}
				if(obsSummaryVO.getProgramArea() == null || obsSummaryVO.getProgramArea().equals("")){
					if(prgMap != null && prgMap.containsKey(NEDSSConstants.BLANK_KEY)){
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
	
	public String getSortCriteria(String sortOrder, String methodName){
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getDateReceived"))
				sortOrdrStr = "Date Received";
			else if(methodName.equals("getJurisdiction"))
				sortOrdrStr = "Jurisdiction";
			else if(methodName.equals("getType"))
				sortOrdrStr = "Document Type";
			else if(methodName.equals("getFullNameNoLnk"))
				sortOrdrStr = "Patient Name";
			else if(methodName.equals("getLocalId"))				
				sortOrdrStr = "Local ID";
			else if(methodName.equals("getTestsStringNoLnk"))				
				sortOrdrStr = "Associated With";
			else if(methodName.equals("getProviderReportingFacility"))				
				sortOrdrStr = "Reporting Facility/Provider";
			else if(methodName.equals("getDescriptionPrint"))				
				sortOrdrStr = "Description"; 
			else if(methodName.equals("getProgramArea"))				
				sortOrdrStr = "Program Area";
		} else {
			sortOrdrStr = "Date Received";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;
			
	}
	
	/**
	 * appendElectronicLabIcon(): method used from DRRQ and DRASQ for adding the electronic icon to the electronic labs
	 * @param observationSummaryDisplayVO
	 */
	public static void appendElectronicLabIcon(ObservationSummaryDisplayVO observationSummaryDisplayVO){
		
		String event = observationSummaryDisplayVO.getTypeLnk();
		//Append Electronic Ind
		if(observationSummaryDisplayVO.getType().trim().equals("Lab Report")){
			if(observationSummaryDisplayVO.getElectronicInd()!=null && observationSummaryDisplayVO.getElectronicInd().equals("Y")){
				observationSummaryDisplayVO.setTypeLnk(event+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
			}else
				observationSummaryDisplayVO.setTypeLnk(event.toString());
		}
		
		
	}
	
	public static void appendElectronicLabIcon(PatientSrchResultVO observationSummaryDisplayVO){
		
		String event = observationSummaryDisplayVO.getDocumentType();
		//Append Electronic Ind
		//if(observationSummaryDisplayVO.getType().trim().equals("Lab Report")){
			if(observationSummaryDisplayVO.getElectronicInd()!=null && observationSummaryDisplayVO.getElectronicInd().equals("Y")){
				observationSummaryDisplayVO.setDocumentType(event+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
			}else
				observationSummaryDisplayVO.setDocumentType(event.toString());
		//}
		
		
	}
	
	
	
	

	
	/**
	   * getProviderInformation(): common method for getting the provider information from DRRQ and Patient file (Summary and Event tab).
	   * @param providerDetails
	   * @param labRep
	   * @return
	   */
	  
	  public Long getProviderInformationCaseReport (ArrayList<Object>  providerDetails, SummaryDT docSummaryVO){  

		  Long providerUid = null;
		  
	      if (providerDetails != null && providerDetails.size() > 0 && docSummaryVO != null) {
	          Object[] orderProvider = providerDetails.toArray();

	          if (orderProvider[0] != null) {
	        	  docSummaryVO.setProviderLastName((String) orderProvider[0]);
	              logger.debug("ProviderLastName: " + (String) orderProvider[0]);
	          }
	          if (orderProvider[1] != null){
	        	  docSummaryVO.setProviderFirstName((String) orderProvider[1]);
	          	  logger.debug("ProviderFirstName: " + (String) orderProvider[1]);
	          }
	          if (orderProvider[2] != null){
	        	  docSummaryVO.setProviderPrefix((String) orderProvider[2]);
	           	  logger.debug("ProviderPrefix: " + (String) orderProvider[2]);
	          }
	          if (orderProvider[3] != null){
	        	  docSummaryVO.setProviderSuffix(( String)orderProvider[3]);
	          	  logger.debug("ProviderSuffix: " + (String) orderProvider[3]);
	          }
	      	  if (orderProvider[4] != null){
	      		docSummaryVO.setProviderDegree(( String)orderProvider[4]);
	         	logger.debug("ProviderDegree: " + (String) orderProvider[4]);
	      	  }
	     	  if (orderProvider[5] != null){
	     		providerUid= (Long)orderProvider[5];
	     		docSummaryVO.setProviderUid((String)(orderProvider[5]+""));
	         	logger.debug("orderProviderUid: " + (Long) orderProvider[5]);
	     	  }
	        }
	      
	      return providerUid;
	      
	  }
	  
	  public void formatProvider(ObservationSummaryDisplayVO observationSummaryDisplayVO, MorbReportSummaryVO morbReportSummaryVO){
          WorkupLoad wl = new WorkupLoad();
			
			String provider = wl.getProviderFullName(morbReportSummaryVO.getProviderPrefix(), morbReportSummaryVO.getProviderFirstName(), morbReportSummaryVO.getProviderLastName(), morbReportSummaryVO.getProviderSuffix());
			provider = provider==null?"":"<b>Ordering Provider:</b><br>"+provider;
			String facility = observationSummaryDisplayVO.getReportingFacility()==null?"":"<b>Reporting Facility:</b><br>"+morbReportSummaryVO.getReportingFacility();
			observationSummaryDisplayVO.setProviderReportingFacility(facility+"<br>"+provider);
			
		
			String providerPrint = wl.getProviderFullName(morbReportSummaryVO.getProviderPrefix(), morbReportSummaryVO.getProviderFirstName(), morbReportSummaryVO.getProviderLastName(), morbReportSummaryVO.getProviderSuffix());
			providerPrint = providerPrint==null?"":"Ordering Provider:\n"+providerPrint;
			String facilityPrint = observationSummaryDisplayVO.getReportingFacility()==null?"":" Reporting Facility:\n"+morbReportSummaryVO.getReportingFacility();
			
			
			observationSummaryDisplayVO.setProviderReportingFacilityPrint(facilityPrint+"\n"+providerPrint);	
	  }
	  //Lab Report Event Search
	  public void formatProvider(PatientSrchResultVO vo){
          WorkupLoad wl = new WorkupLoad();
			
			String provider = wl.getProviderFullName(vo.getProviderPrefix(), vo.getProviderFirstName(), vo.getProviderLastName(), vo.getProviderSuffix());
			provider = provider==null?"":"<b>Ordering Provider:</b><br>"+provider;
			String facility = vo.getReportingFacility()==null?"":"<b>Reporting Facility:</b><br>"+vo.getReportingFacility();
			vo.setReportingFacilityProvider(facility+"<br>"+provider);
			
		
			String providerPrint = wl.getProviderFullName(vo.getProviderPrefix(), vo.getProviderFirstName(), vo.getProviderLastName(), vo.getProviderSuffix());
			providerPrint = providerPrint==null?"":"Ordering Provider:\n"+providerPrint;
			String facilityPrint = vo.getReportingFacility()==null?"":" Reporting Facility:\n"+vo.getReportingFacility();
			
			
			vo.setReportingFacilityProviderPrint(facilityPrint+"\n"+providerPrint);	
	  }
	  
	  
	  public void formatProvider(ObservationSummaryDisplayVO observationSummaryDisplayVO, LabReportSummaryVO labReportSummaryVO){
	      
		  WorkupLoad wl = new WorkupLoad();
			
			String provider = wl.getProviderFullName(labReportSummaryVO.getProviderPrefix(), labReportSummaryVO.getProviderFirstName(), labReportSummaryVO.getProviderLastName(), labReportSummaryVO.getProviderSuffix());
			provider = provider==null?"":"<b>Ordering Provider:</b><br>"+provider;
			String facility = observationSummaryDisplayVO.getReportingFacility()==null?"":"<b>Reporting Facility:</b><br>"+labReportSummaryVO.getReportingFacility();
			observationSummaryDisplayVO.setProviderReportingFacility(facility+"<br>"+provider);
			
		
			String providerPrint = wl.getProviderFullName(labReportSummaryVO.getProviderPrefix(), labReportSummaryVO.getProviderFirstName(), labReportSummaryVO.getProviderLastName(), labReportSummaryVO.getProviderSuffix());
			providerPrint = providerPrint==null?"":"Ordering Provider:\n"+providerPrint;
			String facilityPrint = observationSummaryDisplayVO.getReportingFacility()==null?"":" Reporting Facility:\n"+labReportSummaryVO.getReportingFacility();
			
			
			observationSummaryDisplayVO.setProviderReportingFacilityPrint(facilityPrint+"\n"+providerPrint);	

	   
	  }

	  public void formatProvider(ObservationSummaryDisplayVO observationSummaryDisplayVO, SummaryDT summaryDt){

			   WorkupLoad wl = new WorkupLoad();
			      
			   String provider = wl.getProviderFullName(observationSummaryDisplayVO.getProviderPrefix(), observationSummaryDisplayVO.getProviderFirstName(), observationSummaryDisplayVO.getProviderLastName(), observationSummaryDisplayVO.getProviderSuffix());
				provider = provider==null?"":"<b>Ordering Provider:</b><br>"+provider;
				String facility = observationSummaryDisplayVO.getSendingFacilityNm()==null?"":"<b>Sending Facility:</b><br>"+observationSummaryDisplayVO.getSendingFacilityNm();
				observationSummaryDisplayVO.setProviderReportingFacility(facility+"<br>"+provider);
				
				String providerPrint = wl.getProviderFullName(observationSummaryDisplayVO.getProviderPrefix(), observationSummaryDisplayVO.getProviderFirstName(), observationSummaryDisplayVO.getProviderLastName(), observationSummaryDisplayVO.getProviderSuffix());
				providerPrint = providerPrint==null?"":"Ordering Provider:\n"+providerPrint;
				String facilityPrint = observationSummaryDisplayVO.getSendingFacilityNm()==null?"":" Sending Facility:\n"+observationSummaryDisplayVO.getSendingFacilityNm();
				observationSummaryDisplayVO.setProviderReportingFacilityPrint(facilityPrint+"\n"+providerPrint);
				
	  }
	  
	  /**
	   * getDescriptionPrint(): common method used from DRRQ and DRSAQ for removing the html tags on print/export pdf.
	   * @param description
	   * @return
	   */
	  
  	public String getDescriptionPrint(String description){
		
		if(description!=null){
			
			description=description.replaceAll("<LI>","\n").replaceAll("</LI>","").replaceAll("<UL>","\n").replaceAll("</UL>","")
			.replaceAll("<b>","").replaceAll("</b>","").replaceAll("<BR>","\n").replaceAll("<br>","\n").replaceAll("<B>","").replaceAll("</B>","").replaceAll("&nbsp;","").replaceAll("<DIV>", "").replaceAll("</DIV>", "")
			.replaceAll("<div style='display:none'>","").replaceAll("<div onmouseout='showHideReflex(this) onmouseover='showHideReflex(this)'>","")
			.replaceAll("<a style='cursor:pointer'>", "").replaceAll("</div>","").replaceAll("</a>","");

			String replace = "<div onmouseout='showHideReflex(this)' onmouseover='showHideReflex(this)'>Show Reflex Test Results";
			int index = description.indexOf(replace);
			if(index != -1){
				description=description.substring(0,index)+description.substring(index+replace.length(),description.length());
				
			}
		}    		
		
		return description;    		
	}
  	
    private String getSexDesc(String sex)
    {
       String desc="";
 	  if(sex.equals("M"))
 		  desc = "Male";
 	  if(sex.equals("F"))
 		  desc = "Female";
 	  if(sex.equals("U"))
 		  desc = "Unknown";

        return desc;
    }
    

  	/**
  	 * setPatientFormat(): common method used from DRRQ and DRSAQ for setting the patient.
  	 * @param observationSummaryDisplayVO
  	 */
  	public void setPatientFormat(ObservationSummaryDisplayVO observationSummaryDisplayVO){
  		
  		String sex = observationSummaryDisplayVO.getCurrSexCd()==null?null:observationSummaryDisplayVO.getCurrSexCd();
		
		String birthTime=observationSummaryDisplayVO.getBirthTime()==null?null:observationSummaryDisplayVO.getBirthTime();
		String personLocalId=observationSummaryDisplayVO.getPersonLocalId()==null?null:observationSummaryDisplayVO.getPersonLocalId();
		String personAge = PersonUtil.displayAgeForPatientResults(observationSummaryDisplayVO.getBirthTime()).toString().trim();
		String personUid = "";
		//String actualLocalID="";
   	 	String seedValue = propertyUtil.getSeedValue();
	        String sufix = propertyUtil.getUidSufixCode();
	        String prefix = NEDSSConstants.PERSON;
	        try
	        {
	            if(personLocalId != null && !personLocalId.equals("") && !personLocalId.trim().equals("null")){
	            	personUid = personLocalId.substring(prefix.length(), personLocalId.indexOf(sufix)); 
	            	personUid =  new Long(personUid).longValue() - new Long(seedValue).longValue()+"";			              
	            }          
	            	
	        }
	        catch(NumberFormatException nfe)
	        {
	          logger.error("Can not be able to convert String to long value :"+personLocalId);
	        }
	        
	        
  		StringBuffer fullName = new StringBuffer(observationSummaryDisplayVO.getFullName());
		StringBuffer fullNameNoLink = new StringBuffer(observationSummaryDisplayVO.getFullNameNoLnk());
		if (personUid != null) {
			fullName.append("<BR> <b>Patient ID: </b>").append(personUid);
			fullNameNoLink.append("\nPatient ID: ").append(personUid);
		}
		if (sex != null) {
			fullName.append("<BR>").append(sex);
			fullNameNoLink.append("\n").append(sex);
		}
		if (birthTime != null) {
			fullName.append("<BR>").append(birthTime);
			fullNameNoLink.append("\n").append(birthTime);
		}
		if (personAge != null && personAge != "") {
			fullName.append(" &#40;").append(personAge).append("&#41"); 
			fullNameNoLink.append(" (").append(personAge).append(")");
		}
		
		observationSummaryDisplayVO.setFullName(fullName.toString());
		observationSummaryDisplayVO.setFullNameNoLnk(fullNameNoLink.toString().replaceAll("^\n\t","").replaceAll("\t", ""));
  	}
  	
  	
public void setPatientFormat(InvestigationSummaryVO investigationSummaryVO){
  		
  		String sex = investigationSummaryVO.getCurrSexCd()==null?null:investigationSummaryVO.getCurrSexCd();
		String birthTime=investigationSummaryVO.getBirthTime()==null?null:StringUtils.formatDate(investigationSummaryVO.getBirthTime());
		String personLocalId=investigationSummaryVO.getPersonLocalId()==null?null:investigationSummaryVO.getPersonLocalId();
		String personAge = PersonUtil.displayAgeForPatientResults(birthTime).toString().trim();
		String personUid = "";
		//String actualLocalID="";
   	 	String seedValue = propertyUtil.getSeedValue();
	        String sufix = propertyUtil.getUidSufixCode();
	        String prefix = NEDSSConstants.PERSON;
	        try
	        {
	            if(personLocalId != null && !personLocalId.equals("") && !personLocalId.trim().equals("null")){
	            	personUid = personLocalId.substring(prefix.length(), personLocalId.indexOf(sufix)); 
	            	personUid =  new Long(personUid).longValue() - new Long(seedValue).longValue()+"";			              
	            }          
	            	
	        }
	        catch(NumberFormatException nfe)
	        {
	          logger.error("Can not be able to convert String to long value :"+personLocalId);
	        }
	        
	        
  		StringBuffer fullName = new StringBuffer(investigationSummaryVO.getPatientFullName());
		StringBuffer fullNameNoLink = new StringBuffer(investigationSummaryVO.getPatientFullNameLnk());
		if (personUid != null) {
			fullName.append("<BR> \n<b>Patient ID: </b>").append(personUid);
			fullNameNoLink.append("<BR>\n<b>Patient ID:</b> ").append(personUid);
		}
		if (sex != null && !sex.equals("")) {
			fullName.append("<BR>").append(getSexDesc(sex));
			fullNameNoLink.append("\n<BR>").append(getSexDesc(sex));
		}
		if (birthTime != null) {
			fullName.append("<BR>").append(birthTime);
			fullNameNoLink.append("<BR>").append(birthTime);
		}
		if (personAge != null && personAge != "") {
			fullName.append(" &#40;").append(personAge).append("&#41"); 
			fullNameNoLink.append(" (").append(personAge).append(")");
		}
		
		investigationSummaryVO.setPatientFullName(fullName.toString());
		investigationSummaryVO.setPatientFullNameLnk(fullNameNoLink.toString().replaceAll("^\n\t","").replaceAll("\t", ""));
  	}
  	
  	/**
  	 * setDateFormat(): common method used from DRRQ and DRSAQ for setting the date.
  	 * @param labReportSummaryVO
  	 * @param observationSummaryDisplayVO
  	 */
  	
  	public void setDateFormat(LabReportSummaryVO labReportSummaryVO, ObservationSummaryDisplayVO observationSummaryDisplayVO){
        String startDate = labReportSummaryVO.getDateReceived()==null?"No Date":
			StringUtils.formatDate(labReportSummaryVO.getDateReceived());
        startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
       
        String startDatePrint = labReportSummaryVO.getDateReceived()==null?"No Date":
  			StringUtils.formatDate(labReportSummaryVO.getDateReceived());
        startDatePrint = startDatePrint+"\n"+StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
           
       observationSummaryDisplayVO.setDateReceived(startDate);
       observationSummaryDisplayVO.setDateReceived(labReportSummaryVO.getDateReceived()); 
       observationSummaryDisplayVO.setDateReceivedPrint(startDatePrint); 
		
  	}
  	
  	public void setDateFormat(MorbReportSummaryVO morbReportSummaryVO, ObservationSummaryDisplayVO observationSummaryDisplayVO){
        String startDate = morbReportSummaryVO.getDateReceived()==null?"No Date":
			StringUtils.formatDate(morbReportSummaryVO.getDateReceived());
        startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(morbReportSummaryVO.getDateReceived());
       
        String startDatePrint = morbReportSummaryVO.getDateReceived()==null?"No Date":
  			StringUtils.formatDate(morbReportSummaryVO.getDateReceived());
        startDatePrint = startDatePrint+"\n"+StringUtils.formatDatewithHrMin(morbReportSummaryVO.getDateReceived());
           
       observationSummaryDisplayVO.setDateReceived(startDate);
       observationSummaryDisplayVO.setDateReceived(morbReportSummaryVO.getDateReceived()); 
       observationSummaryDisplayVO.setDateReceivedPrint(startDatePrint); 
		
  	}
  	
  	
  	
  	public void setDateFormat(SummaryDT summaryDt, ObservationSummaryDisplayVO observationSummaryDisplayVO){
  		String startDate = summaryDt.getLastChgTime()==null?"No Date":StringUtils.formatDate(summaryDt.getLastChgTime());
		startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(summaryDt.getLastChgTime());

		String startDatePrint = summaryDt.getLastChgTime()==null?"No Date":StringUtils.formatDate(summaryDt.getLastChgTime());
		startDatePrint = startDatePrint+"\n"+StringUtils.formatDatewithHrMin(summaryDt.getLastChgTime());
           
		observationSummaryDisplayVO.setDateReceived(startDate);   
		observationSummaryDisplayVO.setDateReceived(summaryDt.getLastChgTime()); 
        observationSummaryDisplayVO.setDateReceivedPrint(startDatePrint); //Set Time-stamp
        
		
  	}
}
