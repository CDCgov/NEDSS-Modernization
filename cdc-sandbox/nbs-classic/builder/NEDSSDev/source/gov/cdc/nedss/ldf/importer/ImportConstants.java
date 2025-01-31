package gov.cdc.nedss.ldf.importer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ImportConstants {

	// default values
	public static final String REMOVE = "REMOVE";
	public static final String ADD = "ADD";
	public static final String UPDATE = "UPDATE";
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String INACTIVE = "I";
	public static final String ACTIVE = "A";
	public static final String JAVASCRIPT_VALIDATION_TEXT_CODE = "JS";
	public static final String DATA_TYPE_CODED_VALUE_CODE = "CV";
	public static final String DATA_TYPE_ST_CODE = "ST";
	public static final String CLASS_CODE_CDC = "CDC";
	public static final String CLASS_CODE_DM = "DM";
	
	// hash map keys
	public static final String FILE = "FILE";
	public static final String LOG_SUMMARY = "logSummary";

	// import directories
	public static final String IMPORT_FILE_DIRECTORY_KEY =
		"cdf_subform_import_directory";
	
	public static final String DEFAULT_IMPORT_FILE_DIRECTORY =
		System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "CDF-Subform" + 
		System.getProperty("file.separator") + "import" + 
		System.getProperty("file.separator");
	
	public static final String IMPORT_SUCCESS_DIRECTORY_KEY =
		"cdf_subform_success_directory";
	
	public static final String DEFAULT_IMPORT_SUCCESS_DIRECTORY =
		System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "CDF-Subform" + 
		System.getProperty("file.separator") + "success" + 
		System.getProperty("file.separator");
	
	public static final String IMPORT_FAILURE_DIRECTORY_KEY =
		"cdf_subform_failure_directory";
	
	public static final String DEFAULT_IMPORT_FAILURE_DIRECTORY =
		System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "CDF-Subform" + 
		System.getProperty("file.separator") + "failure" + 
		System.getProperty("file.separator");
	
	public static final String SF_XHTML_DIRECTORY_KEY =
		"subform_content_directory";

	public static final String DEFAULT_SF_XHTML_DIRECTORY =
		System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "CDF-Subform" + 
		System.getProperty("file.separator") + "xhtml" + 
		System.getProperty("file.separator");

	// Log error code
	public static final String SUCCESS_CODE = "SUCCESS";
	public static final String FAILURE_CODE = "FAILURE";
	public static final String INVALID_FILE_CODE = "INVALID_FILE";
	public static final String INVALID_FILE_FORMAT_CODE = "INVALID_FILE_FORMAT";
	public static final String INCONSISTENT_DATA = "INCONSISTENT_DATA";
	public static final String INVALID_XHTML_FORMAT_CODE = "INVALID_XHTML_FORMAT";
	public static final String INCONSISTENT_XHTML_FORMAT_CODE = "INVALID_XHTML_DATA";
	public static final String INVALID_SRT_VALUE_CODE = "INVALID_SRT_VALUE";

	// Log error message
	public static final String SUCCESS_TEXT = "Import successful.";
	public static final String INVALID_FILE_TEXT = "Can not process import file: ";
	public static final String HTML_PARSE_ERROR_TEXT =
		"Errors while parsing HTML:";
	
	// exception string
	public static final String FILE_NOT_EXISTS_TEXT =
		" does not exist in the directory ";
	public static final String IMPORT_PARSER_UNINITIALIZED_TEXT =
		"The ImportDataParser has not been initialized.";
	public static final String FAILED_TO_PARSE_TEXT =
		"Failed to parse import file ";
	public static final String INVALID_JAVASCRIPT_TEXT =
		"The validationJavaScriptText cannot be empty.";
	public static final String FACTORY_INITIALIZATION_FAILURE_TEXT =
		"Can not initialize custom defined field and subform importer factory";
	public static final String NEGATIVE_IMPORT_VERSION_NBR_TEXT =
		"Cannot create a negative importVersionNbr.";
	public static final String DUPLICATED_IMPORT_VERSION_NBR_TEXT =
		"Cannot import a file with duplicated version number: ";
	public static final String INVALID_PARAMETERS_TEXT =
		"Invalid parameters to get importer class.";
	public static final String CREATE_RECORD_FAILURE_TEXT =
		"Cannot create a record in cdf_subform_import_log table.";
	public static final String IMPORTER_CREATE_FAILURE_TEXT =
		"Can not create custom defined field and subform importer class";
	public static final String NO_PERMISSION_TEXT = "Don't have permission!";
	public static final String NULL_DF_OBJECT_ID_TEXT = "Object id must be specified.";
	public static final String DUPLICATED_DF_OBJECT_ID_TEXT = "Duplicated object id: ";
	public static final String DUPLICATED_DF_FORM_ID_TEXT = "Duplicated XHTML input field id: ";
	public static final String INVALID_LABEL_TEXT = "Defined field label text can not be longer than 300 characters.";
	public static final String INVALID_CODE_SET_TEXT = "The code set name field can not be empty";
	public static final String NULL_DF_PAGE_SET_TEXT = "Page set id must be specified.";
	public static final String INVALID_DF_PAGE_SET_TEXT = "Invalid page set id: ";
	public static final String NULL_DF_CLASS_CODE_TEXT = "Class code must be specified.";
	public static final String INVALID_DF_CLASS_CODE_TEXT = "Invalid class code: ";
	public static final String NULL_DF_NND_IND_TEXT = "NND indicator for the field must be specified.";
	public static final String INVALID_DF_NND_IND_TEXT = "NND indicator for the field must be " + YES + " or " + NO;
	public static final String NULL_DF_DATA_TYPE_TEXT = "Defined field data type must be specified.";
	public static final String INVALID_DF_DATA_TYPE_TEXT = "Invalid defined field data type: ";
	public static final String INVALID_DF_VALIDATION_TYPE_TEXT = "Invalid defined field validation type: ";
	public static final String NULL_EMBED_DF_ID_TEXT = "ID for the field must be specified.";
	public static final String INVALID_DF_CODE_SET_TEXT = "Invalid defined field code set: ";
	
	// Tag values
	public static final String INPUT = "INPUT";
	public static final String TEXTAREA = "TEXTAREA";
	public static final String SELECT = "SELECT";
	public static final String SPAN = "SPAN";
	public static final String ID = "ID";
	public static final String NAME = "NAME";
	public static final String DATATYPE = "DATATYPE";
	public static final String LABELTEXT = "LABELTEXT";
	public static final String CODESETNAME = "CODESETNAME";
	public static final String CLASS_CD = "CLASSCODE";
	public static final String COMMENT = "COMMENT";
	public static final String NND_IND = "NNDINDICATOR";
	public static final String NATIONALIDENTIFIER = "NATIONALIDENTIFIER";
	public static final String OBJECTID = "OBJECTID";
	public static final String TYPE = "TYPE";
	public static final String FORMNAME = "FORMNAME";
	public static final String CDCFORM = "CDCFORM";
	public static final String FORMVERSION = "FORMVERSION";
	public static final String RADIO = "radio";
	public static final String BUTTON = "button";
	public static final String SUBMIT = "submit";

	public ImportConstants() {
	}

}