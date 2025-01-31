/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.cdc.nedss.ldf.subform.util;

import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;

/**
 * @author nmallela
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SubformConstants {


	//below are the constants mapping subform shortname to subform name
	
	//Risk Exposure & Other
	public static final String daycare = "/Risk Exposure/daycare.xhtml";
	public static final String foodhandler = "/Risk Exposure/foodhandler.xhtml";
	public static final String travelhistory = "/Risk Exposure/travelhistory.xhtml";
	public static final String driwatexp = "/Risk Exposure/driwatexp.xhtml";
	public static final String recwatexp = "/Risk Exposure/recwatexp.xhtml";
	public static final String animalcontact = "/Risk Exposure/animalcontact.xhtml";
	public static final String pregnancy = "/Risk Exposure/pregnancy.xhtml";
	public static final String seafoodexp = "/Risk Exposure/seafoodexp.xhtml";
	public static final String underconds = "/Risk Exposure/underconds.xhtml";
	public static final String relcases = "/Risk Exposure/relcases.xhtml";
	public static final String foodnet = "/Risk Exposure/foodnet.xhtml";

	//Disease Specific
	public static final String trichinosis1 = "/Disease Specific/Trichinellosis/signs_and_symptoms.xhtml";
	public static final String trichinosis2 = "/Disease Specific/Trichinellosis/suspect_food.xhtml";	
	
	public static final String cyclosporiasis1 = "/Disease Specific/Cyclosporiasis/signs_and_symptoms.xhtml";
	public static final String cyclosporiasis2 = "/Disease Specific/Cyclosporiasis/other_clinical_data.xhtml";
	public static final String cyclosporiasis3 = "/Disease Specific/Cyclosporiasis/other_epidemiological_data.xhtml";
	
	public static final String typhoidfever = "/Disease Specific/Typhoid Fever/other_clinical_data.xhtml";	
	
	public static final String cholera1 = "/Disease Specific/Cholera And Other Vibrios/signs_and_symptoms.xhtml";
	public static final String cholera2 = "/Disease Specific/Cholera And Other Vibrios/other_clinical_data.xhtml";
	public static final String cholera3 = "/Disease Specific/Cholera And Other Vibrios/other_epidemiological_data.xhtml";
	public static final String cholera4 = "/Disease Specific/Cholera And Other Vibrios/v_cholerae_01_or_0139_data.xhtml";
	
	public static final String botulism1 = "/Disease Specific/Botulism/fdd_other_clinical_data.xhtml";
	public static final String botulism2 = "/Disease Specific/Botulism/other_clinical_data.xhtml";	
	
	public static final String ehec = "/Disease Specific/EHEC/other_clinical_data.xhtml";	
	public static final String toxoplasmosis = "/Disease Specific/Toxoplasmosis/other_clinical_data.xhtml";
	//Campylobacter(Added R3.1)
	public static final String campylobacter = "/Disease Specific/Campylobacter/other_clinical_data.xhtml";
	
	//Isolate Tracking
	public static final String isolatetracking = "/Isolate Tracking/isolatetracking.xhtml";

	//Risk Exposure & Other Display Names for  NBS Pages
	public static final String daycare_name = "Day Care";
	public static final String foodhandler_name = "Food Handler";
	public static final String travelhistory_name = "Travel History";
	public static final String driwatexp_name = "Drinking Water Exposure";
	public static final String recwatexp_name = "Recreational Water Exposure";
	public static final String animalcontact_name = "Animal Contact";
	public static final String pregnancy_name = "Pregnancy";
	public static final String seafoodexp_name = "Seafood Exposure";
	public static final String underconds_name = "Underlying Conditions";
	public static final String relcases_name = "Related Cases";
	public static final String foodnet_name = "FoodNet";
 	
	//Disease Specific Display Names for  NBS Pages 	 	
 	public static final String trichinosis1_name = "Signs and Symptoms";
	public static final String trichinosis2_name = "Suspect Food";	
	
	public static final String cyclosporiasis1_name = "Signs and Symptoms";
	public static final String cyclosporiasis2_name = "Other Clinical Data";
	public static final String cyclosporiasis3_name = "Other Epidemiological Data";	
	
	public static final String typhoidfever_name = "Other Clinical Data";	
	
	public static final String cholera1_name = "Signs and Symptoms";
	public static final String cholera2_name = "Other Clinical Data";
	public static final String cholera3_name = "Other Epidemiological Data";
	public static final String cholera4_name = "V. Cholerae 01 or 0139 Data";	
	
	public static final String botulism1_name = "FDD - Other Clinical Data";
	public static final String botulism2_name = "Other Clinical Data";	
	
	public static final String ehec_name = "Other Clinical Data";	
	public static final String toxoplasmosis_name = "Other Clinical Data";	
 
	//Campylobacter(Added R3.1)
	public static final String campylobacter_name = "Other Clinical Data";
	
	//Isolate Tracking
	public static final String isolatetracking_name = "Isolate Tracking";

	
	//xml constants
	
	public static final String NEDSSIMPORT = "<NEDSSImport xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"..\\..\\Properties\\CDF_Subform_Import.xsd\">";
		
	//directory locations
	public final static String PROPERTY_FILE = PropertyUtil.propertiesDir + "SFCONDITIONS.properties";
	public final static String RAW_XHTMLS_DIR = PropertyUtil.nedssDir + "CDF-Subform" + File.separator + "rawdata/";

	public final static String PROCESSED_XHTMLS_DIR = PropertyUtil.nedssDir + "CDF-Subform" + File.separator + "xhtml/";
	public final static String SUBFORMS_IMPORT_XML_DIR = PropertyUtil.nedssDir + "CDF-Subform" + File.separator + "import/";
	public final static String SUCCESS_DIR = PropertyUtil.nedssDir + "CDF-Subform" + File.separator + "success/";
	public final static String FAILURE_DIR = PropertyUtil.nedssDir + "CDF-Subform" + File.separator + "failure/";
	
	/**
	 * @return Returns the animalcontact.
	 */
	public static String getAnimalcontact() {
		return animalcontact;
	}
	/**
	 * @return Returns the animalcontact_name.
	 */
	public static String getAnimalcontact_name() {
		return animalcontact_name;
	}
	/**
	 * @return Returns the botulism1.
	 */
	public static String getBotulism1() {
		return botulism1;
	}
	/**
	 * @return Returns the botulism1_name.
	 */
	public static String getBotulism1_name() {
		return botulism1_name;
	}
	/**
	 * @return Returns the botulism2.
	 */
	public static String getBotulism2() {
		return botulism2;
	}
	/**
	 * @return Returns the botulism2_name.
	 */
	public static String getBotulism2_name() {
		return botulism2_name;
	}
	/**
	 * @return Returns the cholera1.
	 */
	public static String getCholera1() {
		return cholera1;
	}
	/**
	 * @return Returns the cholera1_name.
	 */
	public static String getCholera1_name() {
		return cholera1_name;
	}
	/**
	 * @return Returns the cholera2.
	 */
	public static String getCholera2() {
		return cholera2;
	}
	/**
	 * @return Returns the cholera2_name.
	 */
	public static String getCholera2_name() {
		return cholera2_name;
	}
	/**
	 * @return Returns the cholera3.
	 */
	public static String getCholera3() {
		return cholera3;
	}
	/**
	 * @return Returns the cholera3_name.
	 */
	public static String getCholera3_name() {
		return cholera3_name;
	}
	/**
	 * @return Returns the cholera4.
	 */
	public static String getCholera4() {
		return cholera4;
	}
	/**
	 * @return Returns the cholera4_name.
	 */
	public static String getCholera4_name() {
		return cholera4_name;
	}
	/**
	 * @return Returns the cyclosporiasis1.
	 */
	public static String getCyclosporiasis1() {
		return cyclosporiasis1;
	}
	/**
	 * @return Returns the cyclosporiasis1_name.
	 */
	public static String getCyclosporiasis1_name() {
		return cyclosporiasis1_name;
	}
	/**
	 * @return Returns the cyclosporiasis2.
	 */
	public static String getCyclosporiasis2() {
		return cyclosporiasis2;
	}
	/**
	 * @return Returns the cyclosporiasis2_name.
	 */
	public static String getCyclosporiasis2_name() {
		return cyclosporiasis2_name;
	}
	/**
	 * @return Returns the cyclosporiasis3.
	 */
	public static String getCyclosporiasis3() {
		return cyclosporiasis3;
	}
	/**
	 * @return Returns the cyclosporiasis3_name.
	 */
	public static String getCyclosporiasis3_name() {
		return cyclosporiasis3_name;
	}
	/**
	 * @return Returns the daycare.
	 */
	public static String getDaycare() {
		return daycare;
	}
	/**
	 * @return Returns the daycare_name.
	 */
	public static String getDaycare_name() {
		return daycare_name;
	}
	/**
	 * @return Returns the driwatexp.
	 */
	public static String getDriwatexp() {
		return driwatexp;
	}
	/**
	 * @return Returns the driwatexp_name.
	 */
	public static String getDriwatexp_name() {
		return driwatexp_name;
	}
	/**
	 * @return Returns the ehec.
	 */
	public static String getEhec() {
		return ehec;
	}
	/**
	 * @return Returns the ehec_name.
	 */
	public static String getEhec_name() {
		return ehec_name;
	}
	/**
	 * @return Returns the foodhandler.
	 */
	public static String getFoodhandler() {
		return foodhandler;
	}
	/**
	 * @return Returns the foodhandler_name.
	 */
	public static String getFoodhandler_name() {
		return foodhandler_name;
	}
	/**
	 * @return Returns the foodnet.
	 */
	public static String getFoodnet() {
		return foodnet;
	}
	/**
	 * @return Returns the foodnet_name.
	 */
	public static String getFoodnet_name() {
		return foodnet_name;
	}
	/**
	 * @return Returns the pregnancy.
	 */
	public static String getPregnancy() {
		return pregnancy;
	}
	/**
	 * @return Returns the pregnancy_name.
	 */
	public static String getPregnancy_name() {
		return pregnancy_name;
	}
	/**
	 * @return Returns the recwatexp.
	 */
	public static String getRecwatexp() {
		return recwatexp;
	}
	/**
	 * @return Returns the recwatexp_name.
	 */
	public static String getRecwatexp_name() {
		return recwatexp_name;
	}
	/**
	 * @return Returns the relcases.
	 */
	public static String getRelcases() {
		return relcases;
	}
	/**
	 * @return Returns the relcases_name.
	 */
	public static String getRelcases_name() {
		return relcases_name;
	}
	/**
	 * @return Returns the seafoodexp.
	 */
	public static String getSeafoodexp() {
		return seafoodexp;
	}
	/**
	 * @return Returns the seafoodexp_name.
	 */
	public static String getSeafoodexp_name() {
		return seafoodexp_name;
	}
	/**
	 * @return Returns the toxoplasmosis.
	 */
	public static String getToxoplasmosis() {
		return toxoplasmosis;
	}
	/**
	 * @return Returns the toxoplasmosis_name.
	 */
	public static String getToxoplasmosis_name() {
		return toxoplasmosis_name;
	}
	/**
	 * @return Returns the travelhistory.
	 */
	public static String getTravelhistory() {
		return travelhistory;
	}
	/**
	 * @return Returns the travelhistory_name.
	 */
	public static String getTravelhistory_name() {
		return travelhistory_name;
	}
	/**
	 * @return Returns the trichinosis1.
	 */
	public static String getTrichinosis1() {
		return trichinosis1;
	}
	/**
	 * @return Returns the trichinosis1_name.
	 */
	public static String getTrichinosis1_name() {
		return trichinosis1_name;
	}
	/**
	 * @return Returns the trichinosis2.
	 */
	public static String getTrichinosis2() {
		return trichinosis2;
	}
	/**
	 * @return Returns the trichinosis2_name.
	 */
	public static String getTrichinosis2_name() {
		return trichinosis2_name;
	}
	/**
	 * @return Returns the typhoidfever.
	 */
	public static String getTyphoidfever() {
		return typhoidfever;
	}
	/**
	 * @return Returns the typhoidfever_name.
	 */
	public static String getTyphoidfever_name() {
		return typhoidfever_name;
	}
	/**
	 * @return Returns the underconds.
	 */
	public static String getUnderconds() {
		return underconds;
	}
	/**
	 * @return Returns the underconds_name.
	 */
	public static String getUnderconds_name() {
		return underconds_name;
	}
	/**
	 * @return Returns the underconds.
	 */
	public static String getIsolatetracking() {
		return isolatetracking;
	}
	/**
	 * @return Returns the underconds_name.
	 */
	public static String getIsolatetracking_name() {
		return isolatetracking_name;
	}
	
	/**
	 * 
	 * @return java.lang.String
	 */
	public static String getCampylobacter() {
		return campylobacter;
	}
	
	/**
	 * 
	 * @return java.lang.String
	 */
	public static String getCampylobacter_name() {
		return campylobacter_name;
	}		
}
