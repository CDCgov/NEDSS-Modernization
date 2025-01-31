package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.dsm.AlgorithmDocument;
import gov.cdc.nedss.dsm.CodedType;
import gov.cdc.nedss.dsm.ElrCriteriaType;
import gov.cdc.nedss.dsm.SendingSystemType;
import gov.cdc.nedss.dsm.AlgorithmDocument.Algorithm;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
/**
 * DSMLabMatchHelper - This is called by HL7ELRValidateDecisionSupport.java to review the Lab Report
 * and determine if it matches an algorithm. Note that three inner classes which are contained within
 * this class are used (TestCodedValue, TestTextValue and TestNumericValue). They are convenience classes
 * to hold the data from the algorithm.
 * @author Greg Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA</p>
 * DSMLabMatchHelper.java
 * August 1st, 2016
 * @version 0.9
 */

public class DSMLabMatchHelper {
	
	
	
	static final String NULL_STRING = "null";
	static final LogUtils logger = new LogUtils(DSMLabMatchHelper.class.getName());
	//hold quick access values for this Workflow Decision Support algorithm
	private Map<String,String> systemNameMap = new HashMap<String,String>(); //name-OID
	private Map<String,String> systemOidMap = new HashMap<String,String>(); //OID-name
	private Map<String,String> resultedTestCodeMap = new HashMap<String,String>(); //test code/test desc
	//these resulted test lists need to be collections and not maps because a test could repeat
	private List<TestCodedValue> resultedTestCodedValueList = new ArrayList<TestCodedValue>(); //coded values
	private List<TestNumericValue> resultedTestNumericValueList = new ArrayList<TestNumericValue>(); //numeric values
	private List<TestTextValue> resultedTestTextValueList = new ArrayList<TestTextValue>(); //text values
	private Algorithm algorithm = null;
	private AlgorithmDocument algorithmDocument = null;
	private String algorithmNm = "";
	private String andOrLogic = "";
	private Boolean algorithmIsAndLogic = false;
	private Boolean algorithmIsOrLogic = false;
	
	
	/**
	 * Constructor - expects algorithm payload (see DSMAlgorithm.xsd)
	 * Get the values from the algorithm and populate class variables.
	 * @param algorithmDocument
	 * @throws NEDSSAppException
	 */
	DSMLabMatchHelper (AlgorithmDocument algorithmDocument) throws NEDSSAppException {
		logger.debug("DSMLabMatchHelper: adding algorithm to cache...");
		try {
			this.algorithm=algorithmDocument.getAlgorithm();
			this.algorithmDocument=algorithmDocument;
		} catch (Exception e) {
			logger.error("DSMLabMatchHelper algorithmDocument.getAlgorithm() failed");
			throw new NEDSSAppException("ELR to Algorithm Matching Failed: DSMLabMatchHelper.Constructor Unable to process Container Document",e);
		}
		if (algorithm.getAlgorithmName() != null) {
			algorithmNm = algorithm.getAlgorithmName();
			logger.debug("    STARTED initial processing of algorithm " + algorithmNm);
		}
		//And or Or Algorithm
		if (algorithm.getElrAdvancedCriteria() != null && algorithm.getElrAdvancedCriteria().getAndOrLogic() != null)
			andOrLogic = algorithm.getElrAdvancedCriteria().getAndOrLogic();
		else 
			andOrLogic = DecisionSupportConstants.OR_AND_OR_LOGIC; //OR
		if (andOrLogic.equals(DecisionSupportConstants.OR_AND_OR_LOGIC))
			this.algorithmIsOrLogic = true;
		else
			this.algorithmIsAndLogic = true;
		//initialize maps;
		this.systemNameMap = new HashMap<String,String>();//name-OID
		this.systemOidMap = new HashMap<String,String>(); //OID-name
		//populate receiving systems map if present
		if(algorithm.getApplyToSendingSystems()!=null ){
			try {
				SendingSystemType  sendingSystemType  =algorithm.getApplyToSendingSystems();
				for(int i=0; i<sendingSystemType.getSendingSystemArray().length; i++){
					CodedType sendingSystemCodedType= sendingSystemType.getSendingSystemArray()[i];
					String receivingSystemOid 		= sendingSystemCodedType.getCodeSystemCode();
					String receivingSystemDescTxt 	= sendingSystemCodedType.getCode();

					if(receivingSystemOid!=null){
						//criteriaBufferKey.append(spacer);
						if (receivingSystemDescTxt != null)
							systemOidMap.put(receivingSystemOid, receivingSystemDescTxt);
						else
							systemOidMap.put(receivingSystemOid, NULL_STRING);
					}
					if(receivingSystemDescTxt!=null){
						if (receivingSystemOid != null)
							systemNameMap.put(receivingSystemDescTxt,receivingSystemOid);
						else
							systemNameMap.put(receivingSystemDescTxt, NULL_STRING);
					}
				} //for
			} catch (Exception e) {
				logger.error("DSMLabMatchHelper constructor failed");
				throw new NEDSSAppException("ELR to Algorithm Matching Failed: DSMLabMatchHelper.Constructor Unable to process specified sending systems",e);
			}	
		}

		//next populate resultedTestCodeMap
		if(algorithm.getElrAdvancedCriteria()!=null && algorithm.getElrAdvancedCriteria().getElrCriteriaArray()!=null) {
			ElrCriteriaType[] elrCriteriaArray = algorithm.getElrAdvancedCriteria().getElrCriteriaArray();  //getElrCriteriaArray().
			try {
				for(ElrCriteriaType elrCriteria : elrCriteriaArray){
					CodedType elrResultTestCriteriaType= elrCriteria.getResultedTest();
					if(elrResultTestCriteriaType!=null && elrResultTestCriteriaType.getCode()!=null && elrResultTestCriteriaType.getCode().length()>0){
						String code = elrResultTestCriteriaType.getCode();
						String codeDesc = elrResultTestCriteriaType.getCodeDescTxt();
						resultedTestCodeMap.put(code,  codeDesc);
						if (elrCriteria.getElrCodedResultValue() != null) {
							TestCodedValue thisCodedValue = new TestCodedValue();
							thisCodedValue.setTestCode(code);
							thisCodedValue.setTestCodeDesc(codeDesc);
							thisCodedValue.setResultCode(elrCriteria.getElrCodedResultValue().getCode());
							thisCodedValue.setResultCodeDesc(elrCriteria.getElrCodedResultValue().getCodeDescTxt());
							resultedTestCodedValueList.add(thisCodedValue);
						} else if (elrCriteria.getElrTextResultValue() != null) {
							TestTextValue thisTextValue = new TestTextValue();
							thisTextValue.setTestCode(code);
							thisTextValue.setTestCodeDesc(codeDesc);
							if (elrCriteria.getElrTextResultValue().getTextValue() != null)
								thisTextValue.setTextValue(elrCriteria.getElrTextResultValue().getTextValue());
							if (elrCriteria.getElrTextResultValue().getComparatorCode() != null) {
								if (elrCriteria.getElrTextResultValue().getComparatorCode().getCode() != null)
									thisTextValue.setComparatorCode(elrCriteria.getElrTextResultValue().getComparatorCode().getCode());
								if (elrCriteria.getElrTextResultValue().getComparatorCode().getCodeDescTxt() != null)
									thisTextValue.setComparatorCodeDesc(elrCriteria.getElrTextResultValue().getComparatorCode().getCodeDescTxt());
							}
							resultedTestTextValueList.add(thisTextValue);
						} else if (elrCriteria.getElrNumericResultValue() != null) {
							TestNumericValue thisNumericValue = new TestNumericValue();
							thisNumericValue.setTestCode(code);
							thisNumericValue.setTestCodeDesc(codeDesc);
							//comparator
							if (elrCriteria.getElrNumericResultValue().getComparatorCode() != null) {
								if (elrCriteria.getElrNumericResultValue().getComparatorCode().getCode() != null)
									thisNumericValue.setComparatorCode(elrCriteria.getElrNumericResultValue().getComparatorCode().getCode());
								if (elrCriteria.getElrNumericResultValue().getComparatorCode().getCodeDescTxt() != null)
									thisNumericValue.setComparatorCodeDesc(elrCriteria.getElrNumericResultValue().getComparatorCode().getCodeDescTxt());
							}
							//value1
							if (elrCriteria.getElrNumericResultValue().getValue1() != null) {
								try {
										BigDecimal algorithmNumericValue1 = new BigDecimal(elrCriteria.getElrNumericResultValue().getValue1());
										thisNumericValue.setValue1(algorithmNumericValue1);
								} catch (Exception e) {
									logger.error("DSMLabMatchHelper can't convert algorithm value1 to big decimal");
								}
							}
							//separator
							if (elrCriteria.getElrNumericResultValue().getSeperatorCode() != null)
								thisNumericValue.setSeparatorCode(elrCriteria.getElrNumericResultValue().getSeperatorCode());
							//value2
							if (elrCriteria.getElrNumericResultValue().getValue2() != null)
								try {
									BigDecimal algorithmNumericValue2 = new BigDecimal(elrCriteria.getElrNumericResultValue().getValue2());
									thisNumericValue.setValue2(algorithmNumericValue2);
							} catch (Exception e) {
								logger.error("DSMLabMatchHelper can't convert algorithm value2 to big decimal");
							}
							//units	
							if (elrCriteria.getElrNumericResultValue().getUnit() != null) {
								if (elrCriteria.getElrNumericResultValue().getUnit().getCode() != null)
									thisNumericValue.setUnitCode(elrCriteria.getElrNumericResultValue().getUnit().getCode());
								if (elrCriteria.getElrNumericResultValue().getUnit().getCodeDescTxt() != null)
									thisNumericValue.setUnitCodeDesc(elrCriteria.getElrNumericResultValue().getUnit().getCodeDescTxt());
							}
							resultedTestNumericValueList.add(thisNumericValue);
						}
					} //elrResultTestCriteriaType not null
				} //for
			} catch (Exception e) {
				logger.error("DSMMatchHelper failed");
				throw new NEDSSAppException("DSMMatchHelper.get criteria Exception thrown",e);
			}
		}
		logger.debug("    COMPLETED initial processing of algorithm " + algorithmNm);
	} //end Constructor

	public Algorithm getAlgorithm() {
		return this.algorithm;
	}
	public AlgorithmDocument getAlgorithmDocument() {
		return this.algorithmDocument; 
	}
	/**
	 * isThisLabAMatch
	 * @param resultedTestCodeColl - test codes in the incoming lab
	 * @param resultedTestColl - lab results in the incoming lab
	 * @param sendingFacilityClia - Lab clia
	 * @param sendingFacilityName - Lab name
	 * @return true if this algorithm is a match, false otherwise
	 */
	public boolean isThisLabAMatch(Collection<Object> resultedTestCodeColl,
			Collection<Object> resultedTestColl, String sendingFacilityClia, String sendingFacilityName) {
		logger.debug("DSMLabMatchHelper.isThisLabAMatch checking algorithm named " +algorithmNm + " to see if it is a match");
		//Is this Decision Support Algorithm looking for the lab test(s) in these Lab results?
		if (testsDoNotMatch(resultedTestCodeColl, resultedTestCodeMap, andOrLogic))
			return false;
		
		//Is this Decision Support Algorithm only for certain facilities? (Not ALL)
		if (systemOidMap != null && !systemOidMap.isEmpty()) {
			logger.debug("DSMLabMatchHelper.isThisLabAMatch algorithm specifies sending system(s) - checking if this lab is from one of those specified systems.");
			Boolean nameMatched = true;
			Boolean oidMatched = true;
			if (sendingFacilityClia != null && !sendingFacilityClia.isEmpty() && systemOidMap.containsKey(sendingFacilityClia)) {
				logger.debug("Algorithm matches test code and Sending System OID of " + sendingFacilityClia);
				oidMatched = true;
			} else {
				logger.debug("Algorithm matches test code but Sending System OID of " + sendingFacilityClia +"  not in list of Specified Sending Systems");
				oidMatched = false;
			}
			if (sendingFacilityName != null && !sendingFacilityName.isEmpty() && systemNameMap.containsKey(sendingFacilityName)) {
				logger.debug("Algorithm matches test code and Sending System OID and Sending System Name of " + sendingFacilityName);
				nameMatched = true;
			} else {
				if (sendingFacilityName != null)
					logger.debug("Algorithm matches test code and Sending System OID but Sending System Name of " + sendingFacilityName +"  not in list of Specified Sending Systems");
				nameMatched = false;
			}
			if (oidMatched || nameMatched) //GST ToDo: Check if both should match.
				logger.debug("Algorithm matches either the Sending Facility Name or the Sending Facility Oid");
			else
				return false; //specified facility(s) do not match so this algorithm is not a match
		}
		
		//test is in algorithm, check if value matches
		Boolean isMatch = false;
		try {
			isMatch = testIfAlgorthmMatchesLab(resultedTestColl, resultedTestCodedValueList, resultedTestTextValueList, resultedTestNumericValueList);
		} catch (NEDSSAppException e) {
			e.printStackTrace();
		}
		if (isMatch) {
			logger.debug("Match Found: Decision Support Algorithm " +algorithmNm +" matches this lab result");
			return true;
		}
		return false;
	}
	/**
	 * See if the values specified in the Algorithm match the Lab Test
	 * 
	 * @param resultedTestColl
	 * @param testCodedValueList
	 * @param testTextValueList
	 * @param testNumericValueList
	 * @return true if match found, false if not
	 * @throws NEDSSAppException 
	 */
	private boolean testIfAlgorthmMatchesLab(
			Collection<Object> resultedTestColl,
			List<TestCodedValue> testCodedValueList,
			List<TestTextValue> testTextValueList,
			List<TestNumericValue> testNumericValueList) throws NEDSSAppException {

		try {
			//Look for Coded Value Test results first
			logger.debug("\n\n*****************DSMLabMatchHelper.testIfAlgorthmMatchesLab("+algorithmNm + ")*****************\n");
			logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab testing " +testCodedValueList.size() + " coded values");
			Iterator testCodedValueIter = testCodedValueList.iterator();
			while (testCodedValueIter.hasNext()) {
				TestCodedValue algorithmCodedValue = (TestCodedValue) testCodedValueIter.next();
				Boolean textAlgorithmMatched = false;
				logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab checking coded value for a " +algorithmCodedValue.testCodeDesc + " match");
				Iterator<?> resultedTestIt= resultedTestColl.iterator();
				while(resultedTestIt.hasNext()){

					ObservationVO resultObsVO =  (ObservationVO)resultedTestIt.next();
					String testCode = null;
					if(resultObsVO.getTheObservationDT().getCd()!=null)
						testCode = resultObsVO.getTheObservationDT().getCd();
					else if(resultObsVO.getTheObservationDT().getAltCd()!=null)
						testCode = resultObsVO.getTheObservationDT().getAltCd();
					else continue; //no test code?
					if (testCode != null && algorithmCodedValue.getTestCode().equalsIgnoreCase(testCode)) {
						if(resultObsVO.getTheObsValueCodedDTCollection()!=null){
							Iterator<Object> subObsIt = resultObsVO.getTheObsValueCodedDTCollection().iterator();
							while(subObsIt.hasNext()){
								ObsValueCodedDT obsValueCodedDT= (ObsValueCodedDT)subObsIt.next();
								//Test code match?
								if (obsValueCodedDT.getCode() != null && algorithmCodedValue.getResultCode().equalsIgnoreCase(obsValueCodedDT.getCode())) {
									textAlgorithmMatched = true;
									logger.debug(">>>>>>>>Algorithm Coded Value matched<<<<<<<<<");
									if (algorithmIsOrLogic) 
										return true;
								} else if (algorithmIsAndLogic) //result code did not match
									return false;
							} //subObs has next
						} //obsValueCoded present
					}//code matches
				} //next lab test
				if (!textAlgorithmMatched)
					logger.debug("---------Algorithm Coded Value did NOT match any lab Obs Value Coded---------");
			} //while algorithm TestCodedValue has next

			//Look for Text Value Test Results next
			logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab testing " +testTextValueList.size() + " text values");
			Iterator testTextValueIter = testTextValueList.iterator();
			while (testTextValueIter.hasNext()) {
				TestTextValue algorithmTextValue = (TestTextValue) testTextValueIter.next();
				logger.debug("Checking if lab has a text result " +algorithmTextValue.comparatorCode + " " + algorithmTextValue.textValue);
				Boolean textAlgorithmMatched = false;
				Iterator<?> resultedTestIt= resultedTestColl.iterator();
				while(resultedTestIt.hasNext()){
					ObservationVO resultObsVO =  (ObservationVO)resultedTestIt.next();
					String testCode = null;
					if(resultObsVO.getTheObservationDT().getCd()!=null)
						testCode = resultObsVO.getTheObservationDT().getCd();
					else if(resultObsVO.getTheObservationDT().getAltCd()!=null)
						testCode = resultObsVO.getTheObservationDT().getAltCd();
					if (testCode != null && algorithmTextValue.getTestCode().equalsIgnoreCase(testCode)) {
						if(resultObsVO.getTheObsValueTxtDTCollection()!=null){
							logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab checking text value for an " +algorithmTextValue.comparatorCodeDesc + " match");
							Iterator<Object> subObsIt = resultObsVO.getTheObsValueTxtDTCollection().iterator();
							while(subObsIt.hasNext()){
								ObsValueTxtDT obsValueTxtDT= (ObsValueTxtDT)subObsIt.next();
                                if(obsValueTxtDT.getTxtTypeCd()==null || obsValueTxtDT.getTxtTypeCd().trim().equals("") || obsValueTxtDT.getTxtTypeCd().equalsIgnoreCase("O")) {//NBSCentral #11984: to avoid comparing with the notes
									if (algorithmTextValue.comparatorCode.equals(NEDSSConstants.EQUAL_LOGIC)) {
										if (obsValueTxtDT.getValueTxt() != null && obsValueTxtDT.getValueTxt().equals(algorithmTextValue.getTextValue())) 
											textAlgorithmMatched = true;
									} else if (algorithmTextValue.comparatorCode.equals(NEDSSConstants.CONTAINS_LOGIC)) {
										if (obsValueTxtDT.getValueTxt() != null && obsValueTxtDT.getValueTxt().contains(algorithmTextValue.getTextValue())) 
											textAlgorithmMatched = true;
									} else if (algorithmTextValue.comparatorCode.equals(NEDSSConstants.STARTS_WITH_LOGIC)) {
										if (obsValueTxtDT.getValueTxt() != null && obsValueTxtDT.getValueTxt().startsWith(algorithmTextValue.getTextValue())) 
											textAlgorithmMatched = true;
									} else if (algorithmTextValue.comparatorCode.equals(NEDSSConstants.NOT_EQUAL_LOGIC)) {
										if (obsValueTxtDT.getValueTxt() != null && obsValueTxtDT.getValueTxt().compareTo(algorithmTextValue.getTextValue())!=0) 
											textAlgorithmMatched = true;
									} else if (algorithmTextValue.comparatorCode.equals(NEDSSConstants.NOTNULL_LOGIC)) {
										if (obsValueTxtDT.getValueTxt() != null && (obsValueTxtDT.getValueTxt().length() >0) )
											textAlgorithmMatched = true;
									} else 
										logger.warn("DSMLabMatchHelper.testIfAlgorthmMatchesLab unexpected algorithmTextValue.comparatorCode of " +algorithmTextValue.comparatorCode);
	                                }
							} //subObs has next
							if (textAlgorithmMatched) {
								logger.debug(">>>>>>>>Algorithm Text Value matched<<<<<<<<<");
								if (algorithmIsOrLogic)
									return true;
							} 

							if (algorithmIsAndLogic && !textAlgorithmMatched) //result code did not match
								return false;
						} //obsValueTxt present
					}//test code matches
				} //next lab test
				if (!textAlgorithmMatched)
					logger.debug("-------Algorithm Text Value did NOT match the lab Obs Value Txt---------");
			} //next text algorithm result

			//Look for Numeric Value Test Results next	
			//Comparisons supported are != Not Equal, < Less Than, <= Less or Equal, = Equal, >	Greater Than, >= Greater or Equal and BET Between
			logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab testing " +testNumericValueList.size() + " numeric value(s)");
			Iterator testNumericValueIter = testNumericValueList.iterator();
			while (testNumericValueIter.hasNext()) {
				TestNumericValue algorithmNumericValue = (TestNumericValue) testNumericValueIter.next();
				logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab checking numeric value for a " +algorithmNumericValue.comparatorCodeDesc+algorithmNumericValue.value1 + " match");
				Boolean numericAlgorithmMatched = false;
				Iterator<?> resultedTestIt= resultedTestColl.iterator();
				while(resultedTestIt.hasNext()){
					ObservationVO resultObsVO =  (ObservationVO)resultedTestIt.next();
					String testCode = null;
					if(resultObsVO.getTheObservationDT().getCd()!=null)
						testCode = resultObsVO.getTheObservationDT().getCd();
					else if(resultObsVO.getTheObservationDT().getAltCd()!=null)
						testCode = resultObsVO.getTheObservationDT().getAltCd();
					if (testCode != null && algorithmNumericValue.getTestCode().equalsIgnoreCase(testCode)) {
						if(resultObsVO.getTheObsValueNumericDTCollection()!=null){
							logger.debug("DSMLabMatchHelper.testIfAlgorthmMatchesLab checking numeric value for an " +algorithmNumericValue.comparatorCodeDesc + " match");
							Iterator<Object> subObsIt = resultObsVO.getTheObsValueNumericDTCollection().iterator();
							while(subObsIt.hasNext()){	
								ObsValueNumericDT obsValueNumericDT= (ObsValueNumericDT)subObsIt.next();
								//check if the units match (if present)
								if (algorithmNumericValue.getUnitCode() != null) {
									String labUnits = obsValueNumericDT.getNumericUnitCd();
									if (labUnits == null)
										continue; //no units match here
									else if (algorithmNumericValue.getUnitCode().equals(labUnits.trim()))
										logger.debug("Algorithm units match with lab units of " +labUnits);
									else {
										logger.debug("Algorithm units of " +algorithmNumericValue.getUnitCode() +"does not match with lab units of "+labUnits);
										continue; //no units match here
									}
								}
								//if the unlikely case the incoming lab has a comparator that is not equal, can't definitively match >,< >=, <= and <>
								if (obsValueNumericDT.getComparatorCd1() != null && !obsValueNumericDT.getComparatorCd1().equals(NEDSSConstants.EQUAL_LOGIC)) {
									String labComparator = obsValueNumericDT.getComparatorCd1().trim();
									if (labComparator.equals(NEDSSConstants.LESS_THAN_LOGIC) || labComparator.equals(NEDSSConstants.LESS_THAN_OR_EQUAL_LOGIC)
											||labComparator.equals(NEDSSConstants.GREATER_THAN_LOGIC) || labComparator.equals(NEDSSConstants.GREATER_THAN_OR_EQUAL_LOGIC)
											||labComparator.equals(NEDSSConstants.NOT_EQUAL_LOGIC2))
										continue; //skip this result
								}
								Boolean isTiterLab = false;
								if (obsValueNumericDT.getSeparatorCd() != null && !obsValueNumericDT.getSeparatorCd().trim().isEmpty()) {
									String labSeparator = obsValueNumericDT.getSeparatorCd().trim();
									//can't handle separators of /, -, or +
									if (!labSeparator.equals(NEDSSConstants.COLON)) {
										logger.debug("Lab has numeric result with separator that is not a colon [ " +labSeparator + "]");
										continue;//skip this result
									}
									if (obsValueNumericDT.getNumericValue2() == null) {
										logger.debug("Lab has numeric result with colon separator but no Numeric Value2?");
										continue;//skip this result
									}
									//numeric value 1 is 1 in the ratio i.e. =1:8, =1:16, =1:32
									if (obsValueNumericDT.getNumericValue1() != null) {
										BigDecimal bdOne = new BigDecimal(1);
										if (obsValueNumericDT.getNumericValue1().compareTo(bdOne) == 0) {
											logger.debug("Lab has titer value");
											isTiterLab = true;
										} else {
											logger.debug("Lab looks like titer but numeric value1 is [" + obsValueNumericDT.getNumericValue1() +"] ");
											continue;//skip this result
										}
									}
								}
								if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.EQUAL_LOGIC)) {
									//For BigDecimal must use CompareTo and not Equals (using Equals 5.0 is not equal to 5.00, using CompareTo they are equal)
									if (!isTiterLab && obsValueNumericDT.getNumericValue1() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue1()) == 0) {
										numericAlgorithmMatched = true;
									} else if (isTiterLab && obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == 0) {
										numericAlgorithmMatched = true;
									}

								} else if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.GREATER_THAN_LOGIC)) {
									//For BigDecimal must use CompareTo and not Equals (using Equals 5.0 is not equal to 5.00, using CompareTo they are equal)
									if (!isTiterLab && obsValueNumericDT.getNumericValue1() != null && obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == 1) {
										numericAlgorithmMatched = true;
									} else if (isTiterLab && obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == -1) {
										numericAlgorithmMatched = true;
									}
								} else if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.GREATER_THAN_OR_EQUAL_LOGIC)) {
									if (!isTiterLab && obsValueNumericDT.getNumericValue1() != null && 
											(obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == 0 || 
											obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == 1) ) {
										numericAlgorithmMatched = true;
									} else if (isTiterLab && obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == 0 || 
											obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == -1)  {
										numericAlgorithmMatched = true;
									}
								} else if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.LESS_THAN_LOGIC)) {
									if (!isTiterLab && obsValueNumericDT.getNumericValue1() != null && 
											(obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == -1) ) {
										numericAlgorithmMatched = true;
									}else if (isTiterLab && obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == 1)  {
										numericAlgorithmMatched = true;
									}
								} else if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.LESS_THAN_OR_EQUAL_LOGIC)) {
									if (!isTiterLab && obsValueNumericDT.getNumericValue1() != null && 
											(obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == 0 || 
											obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == -1) ) {
										numericAlgorithmMatched = true;
									} else if (isTiterLab && obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == 0 || 
											obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) == 1)  {
										numericAlgorithmMatched = true;
									}
								} else if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.NOT_EQUAL_LOGIC)) {
									if (!isTiterLab && obsValueNumericDT.getNumericValue1() != null && 
											obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) != 0) {
										numericAlgorithmMatched = true;
									} else if (isTiterLab && obsValueNumericDT.getNumericValue2() != null && algorithmNumericValue.value1 != null && algorithmNumericValue.value1.compareTo(obsValueNumericDT.getNumericValue2()) != 0)  {
										numericAlgorithmMatched = true;
									}
								} else if (algorithmNumericValue.comparatorCode.equals(NEDSSConstants.BETWEEN_LOGIC)) {
									if (obsValueNumericDT.getNumericValue1() != null && 
											(obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == 0 ||
											obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value1) == 1) )
									{
										if (obsValueNumericDT.getNumericValue1() != null && algorithmNumericValue.value2 != null &&
												(obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value2) == 0 ||
												obsValueNumericDT.getNumericValue1().compareTo(algorithmNumericValue.value2) == -1) ) {
											numericAlgorithmMatched = true;
										}
									}
								}
								if (numericAlgorithmMatched) {
									logger.debug(">>>>>>>>Algorithm Numeric Value matched<<<<<<<<<");
									if (algorithmIsOrLogic)
										return true;
								} 
							} //next obsValueNumeric
						} //obsValueNumericDT collection
						if (!numericAlgorithmMatched) 
							logger.debug("-------Algorithm Numeric Value did NOT match and lab Obs Value Numeric-----------");
					}//code matched
				} //end resulted tests
				//if no resulted tests matched and we have AND logic
				if (algorithmIsAndLogic && !numericAlgorithmMatched) //result code did not match
					return false;
			}//testNumericValueIter

		} catch (Exception e) {
			logger.error("DSMMatchHelper Match Attempt failed with exception");
			throw new NEDSSAppException("DSMMatchHelper.isThisLabAMatch Exception thrown",e);
		}
		////All test complete!
		if (algorithmIsOrLogic)
			return false; //nothing matched
		if (algorithmIsAndLogic)
			return true; //everything matched

		return false; //failsafe
	}

	/**
	 * Note negative logic - testsDoNotMatch
	 * Quickly rule out labs that don't have the tests the algorithm seeks.
	 * If it is an OR, one must be there. If an AND, all must be there.
	 * @param resultedTestCodeColl - resulted test codes in lab
	 * @param resultedTestCodeMap2 - resulted test codes in Algorithm
	 * @param andOrLogic2 - algorithm and/or logic
	 * @return boolean true if this algorithm is not a match 
	 */
	private boolean testsDoNotMatch(Collection<Object> resultedTestCodeColl,
			Map<String, String> resultedTestCodeMap2, String andOrLogic2) {
		Iterator resultedTestIter = resultedTestCodeMap2.keySet().iterator();
		Boolean foundOne = false;
		while (resultedTestIter.hasNext()) {
			String resultedTest = (String) resultedTestIter.next();
			if (resultedTestCodeColl.contains(resultedTest)) {
				foundOne = true;
				if (andOrLogic2.equals(DecisionSupportConstants.OR_AND_OR_LOGIC)) //OR
					return(false);
			} else {
				if (andOrLogic2.equals(DecisionSupportConstants.AND_AND_OR_LOGIC)) //AND
					return true;
			}
		}
		logger.debug("This lab test contains the Test Code(s) the Decision Support algorithm " +algorithmNm + " is looking for. ");
		return false;
	}
	public String getAlgorithmNm() {
		return algorithmNm;
	}
		///////////////////////////////////////////////////////////////////////////////
	//Inner POJO Classes for the 3 Resulted Test Types - Coded, Text and Numeric //
	///////////////////////////////////////////////////////////////////////////////
		private class TestCodedValue {
			private String testCode;
			private String testCodeDesc;
			private String resultCode;
			private String resultCodeDesc;
			public String getTestCode() {
				return testCode;
			}
			public void setTestCode(String testCode) {
				this.testCode = testCode;
			}
			public String getTestCodeDesc() {
				return testCodeDesc;
			}
			public void setTestCodeDesc(String testCodeDesc) {
				this.testCodeDesc = testCodeDesc;
			}
			public String getResultCode() {
				return resultCode;
			}
			public void setResultCode(String resultCode) {
				this.resultCode = resultCode;
			}
			public String getResultCodeDesc() {
				return resultCodeDesc;
			}
			public void setResultCodeDesc(String resultCodeDesc) {
				this.resultCodeDesc = resultCodeDesc;
			}
			
		}
		private class TestNumericValue {
			private String testCode;
			private String testCodeDesc;
			private String comparatorCode;
			private String comparatorCodeDesc;
			private BigDecimal value1;
			private String separatorCode;
			private BigDecimal value2;
			private String unitCode;
			private String unitCodeDesc;
			
			public String getTestCode() {
				return testCode;
			}
			public void setTestCode(String testCode) {
				this.testCode = testCode;
			}
			public String getTestCodeDesc() {
				return testCodeDesc;
			}
			public void setTestCodeDesc(String testCodeDesc) {
				this.testCodeDesc = testCodeDesc;
			}
			public String getComparatorCode() {
				return comparatorCode;
			}
			public void setComparatorCode(String comparatorCode) {
				this.comparatorCode = comparatorCode;
			}
			public String getComparatorCodeDesc() {
				return comparatorCodeDesc;
			}
			public void setComparatorCodeDesc(String comparatorCodeDesc) {
				this.comparatorCodeDesc = comparatorCodeDesc;
			}
			public BigDecimal getValue1() {
				return value1;
			}
			public void setValue1(BigDecimal value1) {
				this.value1 = value1;
			}
			public String getSeparatorCode() {
				return separatorCode;
			}
			public void setSeparatorCode(String separatorCode) {
				this.separatorCode = separatorCode;
			}
			public BigDecimal getValue2() {
				return value2;
			}
			public void setValue2(BigDecimal value2) {
				this.value2 = value2;
			}
			public String getUnitCode() {
				return unitCode;
			}
			public void setUnitCode(String unitCode) {
				this.unitCode = unitCode;
			}
			public String getUnitCodeDesc() {
				return unitCodeDesc;
			}
			public void setUnitCodeDesc(String unitCodeDesc) {
				this.unitCodeDesc = unitCodeDesc;
			}

			
		}
		private class TestTextValue {
			private String testCode;
			private String testCodeDesc;
			private String comparatorCode;
			private String comparatorCodeDesc;
			private String textValue;
			
			public String getTestCode() {
				return testCode;
			}
			public void setTestCode(String testCode) {
				this.testCode = testCode;
			}
			public String getTestCodeDesc() {
				return testCodeDesc;
			}
			public void setTestCodeDesc(String testCodeDesc) {
				this.testCodeDesc = testCodeDesc;
			}
			public String getTextValue() {
				return textValue;
			}
			public void setTextValue(String textValue) {
				this.textValue = textValue;
			}
			public String getComparatorCode() {
				return comparatorCode;
			}
			public void setComparatorCode(String comparatorCode) {
				this.comparatorCode = comparatorCode;
			}
			public String getComparatorCodeDesc() {
				return comparatorCodeDesc;
			}
			public void setComparatorCodeDesc(String comparatorCodeDesc) {
				this.comparatorCodeDesc = comparatorCodeDesc;
			}

		}
	
	
	
}
