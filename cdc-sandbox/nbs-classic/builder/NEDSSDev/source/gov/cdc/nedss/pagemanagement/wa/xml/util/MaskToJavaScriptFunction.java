package gov.cdc.nedss.pagemanagement.wa.xml.util;
/**
 * MaskToJavaScriptFunction is used to add JS mask functions to the onkeyup (or onblur)
 * of a UI element based on the mask field from the table. It is also used to set the style class for a final validation on
 * submit. The mask field dropdown comes
 * from code value general:
 * 		select * from code_value_general where code_set_nm = 'NBS_MASK_TYPE';
 * Note that isDate(this) and DateMaskFuture(this) is handled in the xsl
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: CSC</p>
 * MaskToJavaScriptFunction.java
 * Feb 18, 2010
 * @version 0.9
 */

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML;
import gov.cdc.nedss.util.LogUtils;


public class MaskToJavaScriptFunction {

	static final LogUtils logger = new LogUtils(MaskToJavaScriptFunction.class.getName());
private static final String isAlphaNumericCharacterEnteredJS = "isAlphaNumericCharacterEntered(this)";
public static final String isNumericCharacterEnteredJS = "isNumericCharacterEntered(this)";
private static final String isDecimalCharacterEnteredJS = "isDecimalCharacterEntered(this)";
private static final String isStructuredNumericCharacterEnteredJS = "isStructuredNumericCharacterEntered(this)";

private static final String isDateJS = "isDate(this)";
private static final String isMonthJS = "isMonthCharEntered(this)";
private static final String isDayJS = "isDayOfMonthCharEntered(this)";
private static final String ZipMaskJS = "ZipMask(this,event)";
private static final String SSNMaskJS = "SSNMask(this, event)";
private static final String TeleMaskJS = "TeleMask(this, event)";
private static final String YearMaskJS = "YearMask(this, event)";
private static final String checkEmailJS = "checkEmail(this)";
private static final String checkCensusTract = "checkCensusTract(this)";
private static final String CaseNumberMaskJS = "CaseNumberMask(this, null, event)";
private static final String FormatCaseNumberMaskJS = "FormatCaseNumberMask(this)";
private static final String EMAIL_STYLE_CLASS = "emailField";
private static final String upperCaseMaskJS = "UpperCaseMask(this)";
private static final String StructuredNumericJS = "isStructuredNumericCharEntered(this)";
private static final String isTemperatureCharJS = "isTemperatureCharEntered(this)";
private static final String isTemperatureEnteredJS = "isTemperatureEntered(this)";
private static final String CheckFullYearJS = "pgCheckFullYear(this)";
private static final String TXT_IDTB_CLASS ="txt_idtbField";



private static final String NUM_MASK_CODE = "NUM";
private static final String ZIP_MASK_CODE = "TXT_ZIP";
private static final String PHONE_MASK_CODE = "TXT_PHONE";
private static final String EMAIL_MASK_CODE = "TXT_EMAIL";
private static final String SSN_MASK_CODE = "TXT_SSN";
private static final String YEAR_MASK_CODE = "NUM_YYYY";
private static final String MONTH_MASK_CODE = "NUM_MM";
private static final String DAY_MASK_CODE = "NUM_DD";
private static final String EXT_MASK_CODE = "NUM_EXT";
private static final String TEMPERATURE_MASK_CODE = "NUM_TEMP";
private static final String TEXT_MASK_CODE = "TXT";
private static final String ID10_MASK_CODE = "TXT_ID10";
private static final String ID12_MASK_CODE = "TXT_ID12";
private static final String ID15_MASK_CODE = "TXT_ID15";
private static final String IDTB_MASK_CODE = "TXT_IDTB";
private static final String CENSUS_TRACT_MASK_CODE="CENSUS_TRACT";
public static final String STRUCTURED_NUMERIC_MASK_CODE = "NUM_SN";
public static final String TXT_IDTB_MASK_CODE = "TXT_IDTB";

/**
 * getJavaScriptForOnKeyUpMask - return the OnKeyUp JavaScript functions to call for the mask value
 *     OnKeyUp is called each time a key is entered
 *
 * @param mask
 * @param existing onkeyup function if any
 *
 */
public String getJavaScriptForOnKeyUpMask(String strMask, String existingJS) {
	String retStr = "";

	if(strMask!=null){
		if (strMask.equalsIgnoreCase(NUM_MASK_CODE))
			retStr = isNumericCharacterEnteredJS;
		else if (strMask.equalsIgnoreCase(ZIP_MASK_CODE))
			retStr = ZipMaskJS;
		else if (strMask.equalsIgnoreCase(PHONE_MASK_CODE))
			retStr = TeleMaskJS;
		else if (strMask.equalsIgnoreCase(SSN_MASK_CODE))
			retStr = SSNMaskJS;
		else if (strMask.equalsIgnoreCase(YEAR_MASK_CODE))
			retStr = YearMaskJS;
		else if (strMask.equalsIgnoreCase(MONTH_MASK_CODE))
			retStr = isMonthJS;
		else if (strMask.equalsIgnoreCase(DAY_MASK_CODE))
			retStr = isDayJS;
		else if (strMask.equalsIgnoreCase(EXT_MASK_CODE))
			retStr = isNumericCharacterEnteredJS;
		else if (strMask.equalsIgnoreCase(TEMPERATURE_MASK_CODE))
			retStr = isTemperatureCharJS;
		else if (strMask.equalsIgnoreCase("TXT_ID10"))
			retStr = isAlphaNumericCharacterEnteredJS + ";" + upperCaseMaskJS + ";chkMaxLength(this,10)";
		else if (strMask.equalsIgnoreCase("TXT_ID12"))
			retStr = isAlphaNumericCharacterEnteredJS + ";" + upperCaseMaskJS + ";chkMaxLength(this,12)";
		else if (strMask.equalsIgnoreCase("TXT_ID15"))
			retStr = isAlphaNumericCharacterEnteredJS + ";" + upperCaseMaskJS + ";chkMaxLength(this,15)";
		else if (strMask.equalsIgnoreCase(TXT_IDTB_MASK_CODE))
			retStr = CaseNumberMaskJS;
		else if (strMask.equalsIgnoreCase(STRUCTURED_NUMERIC_MASK_CODE))
			retStr = StructuredNumericJS;
	
		
		else logger.warn("Unhandled mask type of " + strMask);

	}
	
	if (!retStr.isEmpty() && existingJS != null && !existingJS.isEmpty())
		retStr = retStr + ";" + existingJS;
	return (retStr);
}

/**
 * getJavaScriptForOnBlurMask - return the OnBlur JavaScript functions to call for the mask value
 *     OnBlur is called when the user leaves the field
 *
 * @param mask
 * @param existing onblur function if any
 *
 */
public String getJavaScriptForOnBlurMask(String strMask, String existingJS) {
	String retStr = "";

    if (strMask.equalsIgnoreCase(IDTB_MASK_CODE))
		retStr = FormatCaseNumberMaskJS;
	else if (strMask.equalsIgnoreCase(EMAIL_MASK_CODE))
		retStr = checkEmailJS;
	else if (strMask.equalsIgnoreCase(TEMPERATURE_MASK_CODE))
		retStr = isTemperatureEnteredJS;
	else if (strMask.equalsIgnoreCase(YEAR_MASK_CODE))
		retStr = CheckFullYearJS;
	else if (strMask.equalsIgnoreCase(CENSUS_TRACT_MASK_CODE))
		retStr = checkCensusTract;
	if (!retStr.isEmpty() && existingJS != null && !existingJS.isEmpty())
		retStr = retStr + ";" + existingJS;
	return (retStr);
}

/**
 * getStyleClassForMask - return the StyleClass to set(if any) for an element having the specified mask.
 *     Style class is used for final edits on Submit of the form.
 *
 * @param mask
 * @return styleClass (if any)
 *
 */
public String getStyleClassForMask(String strMask) {
	String retStr = "";

	if(strMask!=null){
		if (strMask.equalsIgnoreCase(EMAIL_MASK_CODE))
			retStr = EMAIL_STYLE_CLASS;
	
		if(strMask.equalsIgnoreCase(TXT_IDTB_MASK_CODE))
			retStr = TXT_IDTB_CLASS;
	}
	return (retStr);
}

}//class