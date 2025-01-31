package gov.cdc.nedss.pagemanagement.wa.xml.util;


import java.util.HashMap;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML;
import gov.cdc.nedss.pagemanagement.wa.xml.util.*; //Note: xmlbeans jar file maps here..
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;

/**
 * PageElementDependentJavaScript - This is a temp class for adding logic that is currently
 * not supported by the rule engine but is needed on standard pages. Hopefully the functionality
 * in this class can be moved to the rule engine in the future. Some hardcoded rules
 * were removed in Oct/Nov 2010. 
 * @author Gregory Tucker
 * <p>Copyright: Copyright(c)2010</p>
 * <p>Company: CSC</p>
 * PageElementDependentJavaScript.java
 * May 24, 2010
 * @version 1.0
 */


public class PageElementDependentJavaScript {

	static final LogUtils logger = new LogUtils(PageElementDependentJavaScript.class.getName());
	
	private static final String REPORTED_AGE_ID = "INV2001";
	//private static final String REPORTED_AGE_UNIT_ID = "INV2002";
	private static final String DOB_ID = "DEM115";
	private static final String GENERAL_INFO_AS_OF_DATE_ID =  "NBS104";
	private static final String OTHER_PERSONEL_DETAILS_AS_OF_ID = "NBS096";
	//private static final String CALCULATED_DOB_ID = "NBS094";
	private static final String STATE_ID = "DEM162";
	private static final String STATE_ID_WORK = "DEM162_W";
	private static final String STATE_ID_MOTHER = "MTH166";
	
	private static final String IMPORTED_STATE_ID = "INV154";
	private static final String EXPOSURE_STATE_ID = "INV503";
	private static final String EXPOSURE_COUNTRY_ID = "INV502";
	private static final String WAS_PATIENT_HOSPITALIZED_ID = "INV128";
	private static final String ILLNESS_ONSET_DATE_ID = "INV137";
	private static final String ILLNESS_END_DATE_ID = "INV138";
	private static final String ILLNESS_DURATION_ID = "INV139";
	private static final String ILLNESS_ONSET_AGE_ID = "INV143";
	//private static final String ILLNESS_ONSET_AGE_UNITS_ID = "INV144";
	//private static final String DATE_OF_DEATH_ID = "INV146";
	//private static final String DID_PATIENT_DIE_ID = "INV145";
	//private static final String PART_OF_OUTBREAK_ID = "INV150";
	//private static final String OUTBREAK_NAME_ID = "INV151";
	//private static final String DISEASE_ACQUIRED_ID = "INV152";
	private static final String DURATION_STAY_IN_HOSP_ID = "INV134";
	private static final String ADMIT_DATE_ID = "INV132";
	private static final String DISCHARGE_DATE_ID = "INV133";
	private static final String PATIENT_COUNTY_ID = "DEM165";
	private static final String PATIENT_COUNTY_ID_WORK = "DEM165_W";
	private static final String PATIENT_COUNTY_ID_MOTHER = "MTH168";
	private static final String IMPORTED_COUNTY_ID = "INV156";
	//private static final String IS_PATIENT_DECEASED_ID = "DEM127";
	//private static final String PATIENT_DECEASED_DATEID = "DEM128";
	private static final String SELECT_NEXT_FOCUS = "pgSelectNextFocus(this);";
	private static final String CS_REPORTING_STATE="CS016";




	PageElementType element = null;
	public void setPageElementDependentJavascriptFunctions (java.util.HashMap<String, PageElementType> quesMap) throws NEDSSSystemException {
      try {
		if (quesMap.get(REPORTED_AGE_ID) != null) {
			//check dependencies to compute Reported Age
			if (quesMap.get(DOB_ID) != null && (quesMap.get(OTHER_PERSONEL_DETAILS_AS_OF_ID) != null || quesMap.get(GENERAL_INFO_AS_OF_DATE_ID) != null))
				element = quesMap.get(DOB_ID); //DOB
				DateQuestionType dateQues = element.getDateQuestion();
				if (dateQues != null) {
					String onChangeStr = "pgCalculateReportedAge('DEM115','INV2001','INV2002','NBS096','NBS104')";
					String existingOnChange = dateQues.getOnChange();
					if (existingOnChange != null)
						dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
					else
						dateQues.setOnChange(onChangeStr);
					//must also set onBlur in case they type in the date instead of selecting it
					String existingOnBlur = dateQues.getOnBlur();
					if (existingOnBlur != null)
						dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
					else
						dateQues.setOnBlur(onChangeStr);
				}
		}
		if (quesMap.get(STATE_ID) != null) {
			element = quesMap.get(STATE_ID); //State
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "getDWRCounties(this, 'DEM165');getDWRCitites(this)";
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
			
		}
		//CS_REPORTING_STATE
		if (quesMap.get(CS_REPORTING_STATE) != null) {
            element = quesMap.get(CS_REPORTING_STATE); //State
            CodedQuestionType codedQues = element.getCodedQuestion();
            String onChangeStr = "getDWRCounties(this, 'AR109') ";
            String existingOnChange = codedQues.getOnChange();
            if (existingOnChange != null)
                codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
            else
                codedQues.setOnChange(onChangeStr);
        }
		
		if (quesMap.get(STATE_ID_WORK) != null) {
			element = quesMap.get(STATE_ID_WORK); //State
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "getDWRCounties(this, 'DEM165_W')";
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}
		if (quesMap.get(STATE_ID_MOTHER) != null) {
			element = quesMap.get(STATE_ID_MOTHER); //State
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "getDWRCounties(this, 'MTH168')";
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}

		if (quesMap.get(IMPORTED_STATE_ID) != null) {
			element = quesMap.get(IMPORTED_STATE_ID); //State
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "getDWRCounties(this, 'INV156')";
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}
		
		if (quesMap.get(EXPOSURE_STATE_ID) != null) {
			element = quesMap.get(EXPOSURE_STATE_ID); //State
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "getDWRCounties(this, 'INV505')";
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}
		if (quesMap.get(EXPOSURE_COUNTRY_ID) != null) {
			element = quesMap.get(EXPOSURE_COUNTRY_ID); //State
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "getDWRStatesByCountry(this, 'INV503')";
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}


		if (quesMap.get(WAS_PATIENT_HOSPITALIZED_ID) != null) {
			element = quesMap.get(WAS_PATIENT_HOSPITALIZED_ID); //Y or N
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');" + SELECT_NEXT_FOCUS;
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}
		if (quesMap.get(ILLNESS_DURATION_ID) != null  
				&& quesMap.get(ILLNESS_ONSET_DATE_ID) != null
				&& quesMap.get(ILLNESS_END_DATE_ID) != null) {
			element = quesMap.get(ILLNESS_ONSET_DATE_ID);
			DateQuestionType dateQues = element.getDateQuestion();
			String onChangeStr = "pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')";
			String existingOnChange = dateQues.getOnChange();
			if (existingOnChange != null)
				dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				dateQues.setOnChange(onChangeStr);
			//must also set onBlur in case they type in the date instead of selecting it
			String existingOnBlur = dateQues.getOnBlur();
			if (existingOnBlur != null)
				dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
			else
				dateQues.setOnBlur(onChangeStr);
			element = quesMap.get(ILLNESS_END_DATE_ID);
			dateQues = element.getDateQuestion();
			existingOnChange = dateQues.getOnChange();
			if (existingOnChange != null)
				dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				dateQues.setOnChange(onChangeStr);

			//must also set onBlur in case they type in the date instead of selecting it
			existingOnBlur = dateQues.getOnBlur();
			if (existingOnBlur != null)
				dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
			else
				dateQues.setOnBlur(onChangeStr);
		}

		if (quesMap.get(ILLNESS_ONSET_AGE_ID) != null
				&& quesMap.get(ILLNESS_ONSET_DATE_ID) != null
				&& quesMap.get(DOB_ID) != null) {
			element = quesMap.get(ILLNESS_ONSET_DATE_ID);
			DateQuestionType dateQues = element.getDateQuestion();
			String onChangeStr = "pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144')";
			String existingOnChange = dateQues.getOnChange();
			if (existingOnChange != null)
				dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				dateQues.setOnChange(onChangeStr);
			//must also set onBlur in case they type in the date instead of selecting it
			String existingOnBlur = dateQues.getOnBlur();
			if (existingOnBlur != null)
				dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
			else
				dateQues.setOnBlur(onChangeStr);

			element = quesMap.get(DOB_ID);
			dateQues = element.getDateQuestion();
			existingOnChange = dateQues.getOnChange();
			if (existingOnChange != null)
				dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				dateQues.setOnChange(onChangeStr);
			//must also set onBlur in case they type in the date instead of selecting it
			existingOnBlur = dateQues.getOnBlur();
			if (existingOnBlur != null)
				dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
			else
				dateQues.setOnBlur(onChangeStr);
		}


		if (quesMap.get(DURATION_STAY_IN_HOSP_ID) != null
				&& quesMap.get(ADMIT_DATE_ID) != null
				&& quesMap.get(DISCHARGE_DATE_ID) != null) {
			//check dependencies to compute Reported Age
			if (quesMap.get(ADMIT_DATE_ID) != null && quesMap.get(DISCHARGE_DATE_ID) != null) {
				element = quesMap.get(DISCHARGE_DATE_ID); //INV133
				DateQuestionType dateQues = element.getDateQuestion();
				if (dateQues != null) {
					String onChangeStr = "pgCalcDaysInHosp('INV132', 'INV133', 'INV134')";
					String existingOnChange = dateQues.getOnChange();
					if (existingOnChange != null)
						dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
					else
						dateQues.setOnChange(onChangeStr);
					//must also set onBlur in case they type in the date instead of selecting it
					String existingOnBlur = dateQues.getOnBlur();
					if (existingOnBlur != null)
						dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
					else
						dateQues.setOnBlur(onChangeStr);
				}
				element = quesMap.get(ADMIT_DATE_ID); //INV133
				dateQues = element.getDateQuestion();
				if (dateQues != null) {
					String onChangeStr = "pgCalcDaysInHosp('INV132', 'INV133', 'INV134')";
					String existingOnChange = dateQues.getOnChange();
					if (existingOnChange != null)
						dateQues.setOnChange(onChangeStr + ";" + existingOnChange);
					else
						dateQues.setOnChange(onChangeStr);
					//must also set onBlur in case they type in the date instead of selecting it
					String existingOnBlur = dateQues.getOnBlur();
					if (existingOnBlur != null)
						dateQues.setOnBlur(onChangeStr + ";" + existingOnBlur);
					else
						dateQues.setOnBlur(onChangeStr);
				}
				
			}
		}
		
		/* removed per Jay Nelson  11/02/2010
		if (quesMap.get(DISEASE_ACQUIRED_ID) != null) {
			element = quesMap.get(DISEASE_ACQUIRED_ID); //IND, OOC, OOA, OOJ, OOS, UNK
			CodedQuestionType codedQues = element.getCodedQuestion();
			String onChangeStr = "enableOrDisableDiseaseAcquired('INV152', 'INV153', 'INV154', 'INV155', 'INV156'); " + SELECT_NEXT_FOCUS;
			String existingOnChange = codedQues.getOnChange();
			if (existingOnChange != null)
				codedQues.setOnChange(onChangeStr + ";" + existingOnChange);
			else
				codedQues.setOnChange(onChangeStr);
		}
		*/
		if (quesMap.get(PATIENT_COUNTY_ID) != null) {
			element = quesMap.get(PATIENT_COUNTY_ID); 
			CodedQuestionType codedQues = element.getCodedQuestion();
			if (codedQues != null)
				codedQues.setSourceField(STATE_ID);

		}
		if (quesMap.get(PATIENT_COUNTY_ID_WORK) != null) {
			element = quesMap.get(PATIENT_COUNTY_ID_WORK); 
			CodedQuestionType codedQues = element.getCodedQuestion();
			if (codedQues != null)
				codedQues.setSourceField(STATE_ID_WORK);
		}
		
		if (quesMap.get(IMPORTED_COUNTY_ID) != null) {
			element = quesMap.get(IMPORTED_COUNTY_ID); 
			CodedQuestionType codedQues = element.getCodedQuestion();
			if (codedQues != null)
				codedQues.setSourceField(IMPORTED_STATE_ID);
		}
		if (quesMap.get(PATIENT_COUNTY_ID_MOTHER) != null) {
			element = quesMap.get(PATIENT_COUNTY_ID_MOTHER); 
			CodedQuestionType codedQues = element.getCodedQuestion();
			if (codedQues != null)
				codedQues.setSourceField(STATE_ID_MOTHER);

		}		
	} catch (Exception e) {
			logger.error("Marshaller error adding Page Element JavaScript functions to XML " +e.getMessage());
			e.printStackTrace();
			throw new NEDSSSystemException("Marshaller error adding Page Element JavaScript functions to XML " + e.toString());
	}	
  }

}
