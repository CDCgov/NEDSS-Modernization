package gov.cdc.nedss.webapp.nbs.action.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;

/**
 * ClientVO: parent ClientVO for all ClientVO
 * @author Pradeep Sharma
 * @update:Updated for Lab to make Lab work for dynamic pages
 * @updatedBy Pradeep Sharma
 * @version NBS 6.0
 */
public class ClientVO {
	private int unKnownRace  =0;
	private int americanIndianAlskanRace  =0;
	private int africanAmericanRace  =0;
	private int whiteRace  =0;
	private Map<Object,Object> answerMap = new HashMap<Object,Object>();
	protected Map<Object,Object> answerArrayMap = new HashMap<Object,Object>();
	private Map<Object,Object> hivAnswerMap = new HashMap<Object,Object>();
	private Map<Object,Object> hivAnswerBatchMap = new HashMap<Object,Object>();
	private int asianRace;
	private int hawaiianRace;
	private int otherRace;
	private int notAsked;
	private int refusedToAnswer;

	/*LAB Specific changes : to make lab work in Dynamic pages*/
	private Collection<Object>  oldResultedTestVOCollection  = null;
	/*LAB Specific changes : This may change when we get the documents from design
	   * form loads values to the MorbidityProxyVO objects
	   */
	private LabResultProxyVO labResultProxyVO = null;
	
	/*LAB Specific changes : 
   * form loads old values to the LabReportProxyVO objects
   */
	private LabResultProxyVO oldLabResultProxyVO = null;
	
	/*LAB Specific changes : 
   * form loads old values to the LabReportProxyVO objects
   */
	/*LAB Specific changes : */
	private Collection<Object> theResultedTestVOCollection = null;
	/*LAB Specific changes : */
    private ObservationVO patientStatusVO = null;
	/*LAB Specific changes : */
    private ObservationVO oldPatientStatusVO = null;

	/*LAB Specific changes : Get old ProxyVO
	 *   
	 */ 
	public LabResultProxyVO getLabResultProxyVO() {
		 if (labResultProxyVO == null)
			 labResultProxyVO = new LabResultProxyVO();
		return labResultProxyVO;
	}
	/*LAB Specific changes : */
	public void setLabResultProxyVO(LabResultProxyVO labResultProxyVO) {
		this.labResultProxyVO = labResultProxyVO;
	}
	
	/*LAB Specific changes : */
	//public LabResultProxyVO getOldLabResultProxyVO() {
	//	return oldLabResultProxyVO;
	//}
	
	/*LAB Specific changes : */
	//public void setOldLabResultProxyVO(LabResultProxyVO oldLabResultProxyVO) {
	//	this.oldLabResultProxyVO = oldLabResultProxyVO;
	//}
	
	/*LAB Specific changes : */
	public ArrayList<Object> getOldResultedTestVOCollection() {
	    return (ArrayList<Object> )oldResultedTestVOCollection;
	}
	
	/*LAB Specific changes : */
	public void setOldResultedTestVOCollection(Collection<Object> oldResultedTestVOCollection) {
		this.oldResultedTestVOCollection = oldResultedTestVOCollection;
	}
	
	public int getUnKnownRace() {
		return unKnownRace;
	}
	public void setUnKnownRace(int unKnownRace) {
		this.unKnownRace = unKnownRace;
	}
	public int getAmericanIndianAlskanRace() {
		return americanIndianAlskanRace;
	}
	public void setAmericanIndianAlskanRace(int americanIndianAlskanRace) {
		this.americanIndianAlskanRace = americanIndianAlskanRace;
	}
	public int getAfricanAmericanRace() {
		return africanAmericanRace;
	}
	public void setAfricanAmericanRace(int africanAmericanRace) {
		this.africanAmericanRace = africanAmericanRace;
	}
	public int getWhiteRace() {
		return whiteRace;
	}
	public void setWhiteRace(int whiteRace) {
		this.whiteRace = whiteRace;
	}
	
	public Map<Object, Object> getAnswerArrayMap() {
		return answerArrayMap;
	}
	public void setAnswerArrayMap(Map<Object, Object> answerArrayMap) {
		this.answerArrayMap = answerArrayMap;
	}
	public int getAsianRace() {
		return asianRace;
	}
	public void setAsianRace(int asianRace) {
		this.asianRace = asianRace;
	}
	public int getHawaiianRace() {
		return hawaiianRace;
	}
	public void setHawaiianRace(int hawaiianRace) {
		this.hawaiianRace = hawaiianRace;
	}
	public void setAnswerArray(String key, String[] answer) {
		if(answer.length > 0) {
			String [] answerList = new String[answer.length];
			boolean selected = false;
			for(int i=1; i<=answer.length; i++) {
				String answerTxt = answer[i-1];
				if(!answerTxt.equals("")) {
					selected = true;
					answerList[i-1] = answerTxt;
				}
			}
			if(selected)
				answerArrayMap.put(key,answerList);
		}
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		 unKnownRace  =0;
		 americanIndianAlskanRace  =0;
		 africanAmericanRace  =0;
		 whiteRace  =0;
		 answerMap = new HashMap<Object,Object>();
		 answerArrayMap = new HashMap<Object,Object>();
		 asianRace = 0;
		 hawaiianRace = 0;
		 otherRace = 0;
		 notAsked = 0;
		 refusedToAnswer = 0;
	}
	public String[] getAnswerArray(String key) {
		return (String[])answerArrayMap.get(key);
	}
	public Map<Object, Object> getAnswerMap() {
		return answerMap;
	}
	public void setAnswerMap(Map<Object, Object> answerMap) {
		this.answerMap = answerMap;
	}
	public String getAnswer(String key) {
		return (String)answerMap.get(key);
	}

	public void setAnswer(String key, String answer) {
		answerMap.put(key,answer );
	}
	public Map<Object,Object> getArrayAnswerMap() {
		return answerArrayMap;
	}
	public void setArrayAnswerMap(Map<Object, Object>  answerArrayMap) {
		this.answerArrayMap = answerArrayMap;
	}
	/**
	 * @return the hivAnswerMap
	 */
	public Map<Object, Object> getHivAnswerMap() {
		return hivAnswerMap;
	}
	/**
	 * @param hivAnswerMap the hivAnswerMap to set
	 */
	public void setHivAnswerMap(Map<Object, Object> hivAnswerMap) {
		this.hivAnswerMap = hivAnswerMap;
	}
	/**
	 * @return the hivAnswerBatchMap
	 */
	public Map<Object, Object> getHivAnswerBatchMap() {
		return hivAnswerBatchMap;
	}
	/**
	 * @param hivAnswerBatchMap the hivAnswerBatchMap to set
	 */
	public void setHivAnswerBatchMap(Map<Object, Object> hivAnswerBatchMap) {
		this.hivAnswerBatchMap = hivAnswerBatchMap;
	}
	/**
	 * @return the otherRace
	 */
	public int getOtherRace() {
		return otherRace;
	}
	/**
	 * @param otherRace the otherRace to set
	 */
	public void setOtherRace(int otherRace) {
		this.otherRace = otherRace;
	}
	/**
	 * @return the refusedToAnswer
	 */
	public int getRefusedToAnswer() {
		return refusedToAnswer;
	}
	/**
	 * @param refusedToAnswer the refusedToAnswer to set
	 */
	public void setRefusedToAnswer(int refusedToAnswer) {
		this.refusedToAnswer = refusedToAnswer;
	}
	public int getNotAsked() {
		return notAsked;
	}
	public void setNotAsked(int notAsked) {
		this.notAsked = notAsked;
	}
	public Collection<Object> getTheResultedTestVOCollection() {
		return theResultedTestVOCollection;
	}
	public void setTheResultedTestVOCollection(Collection<Object> theResultedTestVOCollection) {
		this.theResultedTestVOCollection = theResultedTestVOCollection;
	}
	public ObservationVO getPatientStatusVO() {
		return patientStatusVO;
	}
	public void setPatientStatusVO(ObservationVO patientStatusVO) {
		this.patientStatusVO = patientStatusVO;
	}
	public ObservationVO getOldPatientStatusVO() {
		return oldPatientStatusVO;
	}
	public void setOldPatientStatusVO(ObservationVO oldPatientStatusVO) {
		this.oldPatientStatusVO = oldPatientStatusVO;
	}

}