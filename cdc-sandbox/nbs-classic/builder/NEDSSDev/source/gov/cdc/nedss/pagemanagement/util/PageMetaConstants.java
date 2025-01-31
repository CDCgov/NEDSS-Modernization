package gov.cdc.nedss.pagemanagement.util;

/**
 * PageMetaConstants - constants used with Page Building
 * @author gtucker
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * Feb. 9th, 2010
 * @version 0.7
 */
public class PageMetaConstants {
    //UID values in NBS_UI_Component table are static 
    public static final long  PAGE = 1002L;
    public static final long  TAB= 1010L;
    public static final long  SECTION = 1015L;
    public static final long  SUBSECTION = 1016L; 
    public static final long  INPUT = 1008L;
    public static final long  SINGLESELECT = 1007L;
    public static final long  MULTISELECT = 1013L;
    public static final long  CHECKBOX = 1001L;
    public static final long  RADIO = 1006L;
    public static final long  BUTTON = 1000L;
    public static final long  HYPERLINK = 1003L;
    public static final long  TEXTAREA = 1009L;
    public static final long  SUBHEADING = 1011L;
    public static final long  LINESEPARATOR = 1012L;
    public static final long  READONLY = 1014L;
    public static final long  PARTICIPATION = 1017L;
    public static final long  ROLLINGNOTE = 1019L;
    public static final long  TABLE = 1022L;
    public static final long  INFORMATIONBAR = 1023L;
    public static final long  SINGLESELECTREADONLYSAVE = 1024L;
    public static final long  MULTISELECTREADONLYSAVE = 1025L;    
    public static final long  INPUTREADONLYSAVE = 1026L;
    public static final long  SINGLESELECTREADONLYNOSAVE = 1027L;
    public static final long  MULTISELECTREADONLYNOSAVE = 1028L;    
    public static final long  INPUTREADONLYNOSAVE = 1029L;  
    public static final long  PARTICIPANTLIST = 1030L;
    public static final long  PATIENTSEARCH = 1032L;
    public static final long  ACTIONBUTTON = 1033L;
    public static final long  SETVALUEBUTTON = 1034L;
    public static final long  LOGICFLAG = 1035L;
    public static final long  ORIGDOCLIST = 1036L;
    
	//int values of the above component types
    public static final int aPAGE = 1002;
    public static final int aTAB= 1010;
    public static final int aSECTION = 1015;
    public static final int aSUBSECTION = 1016; 
    public static final int aINPUT = 1008;
    public static final int aSINGLESELECT = 1007;
    public static final int aMULTISELECT = 1013;
    public static final int aCHECKBOX = 1001;
    public static final int aRADIO = 1006;
    public static final int aBUTTON = 1000;
    public static final int aHYPERLINK = 1003;
    public static final int aTEXTAREA = 1009;
    public static final int aSUBHEADING = 1011;
    public static final int aLINESEPARATOR = 1012;
    public static final int aREADONLY = 1014;
    public static final int aPARTICIPATION = 1017;
    public static final int aROLLINGNOTE = 1019;
    public static final int aTABLE = 1022;
    public static final int aINFORMATIONBAR = 1023;
    public static final int aSINGLESELECTREADONLYSAVE = 1024;
    public static final int aMULTISELECTREADONLYSAVE = 1025; 
    public static final int aINPUTREADONLYSAVE = 1026;
    public static final int aSINGLESELECTREADONLYNOSAVE = 1027;
    public static final int aMULTISELECTREADONLYNOSAVE = 1028; 
    public static final int aINPUTREADONLYNOSAVE = 1029; 
    public static final int aPARTICIPANTLIST = 1030;
    public static final int aSINGLESELECTWITHSEARCH = 1031;
    public static final int aPATIENTSEARCH = 1032;
    public static final int aACTIONBUTTON = 1033;
    public static final int aSETVALUEBUTTON = 1034;
    public static final int aLOGICFLAG = 1035;
    public static final int aORIGDOCLIST = 1036;
    
    
    //control types
    public static final String  TEXTBOX_CONTROL = "Textbox";
    public static final String  TEXTBOX_READONLY_SAVE_CONTROL = "ReadOnlyTextboxSave";
    public static final String  TEXTBOX_READONLY_NOSAVE_CONTROL = "ReadOnlyTextboxSave";
    public static final String  SINGLESELECT_CONTROL = "Select";
    public static final String  SINGLESELECT_READONLY_SAVE_CONTROL = "ReadOnlySelectSave";
    public static final String  SINGLESELECT_READONLY_NOSAVE_CONTROL = "ReadOnlySelectNoSave";
    public static final String  MULTISELECT_CONTROL = "MultiSelect";
    public static final String  MULTISELECT_READONLY_SAVE_CONTROL = "ReadOnlyMultiSelectSave";   
    public static final String  MULTISELECT_READONLY_NOSAVE_CONTROL = "ReadOnlyMultiSelectNoSave";  
    public static final String  CHECKBOX_CONTROL = "Checkbox";
    public static final String  RADIO_CONTROL = "Radio";
    public static final String  BUTTON_CONTROL = "Button";
    public static final String  HYPERLINK_CONTROL = "Hyperlink";
    public static final String  TEXTAREA_CONTROL = "Textarea";
    public static final String  ROLLINGNOTE_CONTROL = "RollingNote";
    public static final String  SUBHEADING_CONTROL = "SubHeading";
    public static final String  LINESEPARATOR_CONTROL = "LineSeperator";
    public static final String  PARTICIPATION_CONTROL = "Search";
    public static final String  TABLE_CONTROL = "Table";
    public static final String  INFORMATIONBAR_CONTROL = "InformationBar";
    public static final String  PARTICIPANTLIST_CONTROL = "ParticipantList";
    public static final String  SINGLESELECTSEARCH_CONTROL = "SingleSelectSearch";
    public static final String  PATIENT_SEARCH_CONTROL = "PatientSearch";
    public static final String  ACTION_BUTTON_CONTROL = "ActionButton";
    public static final String  SET_VALUE_BUTTON_CONTROL = "SetValueButton";
    public static final String  LOGIC_FLAG_CONTROL = "LogicFlag";
    public static final String  ORIGDOCLIST_CONTROL = "OrigDocList";
    
    
    //default size
    public static final int  ROLLING_NOTE_BATCH_TABLE_COLUMN_WIDTH = 70;
    public static final String  TEXTBOX_DEFAULT_SIZE = "40";
    public static final String  TEXTBOX_DEFAULT_MAX_LENGTH = "40";
    public static final String  NUMERICAL_DEFAULT_SIZE = "10";
    public static final String  NUMERICAL_DEFAULT_MAX_LENGTH = "10";
    public static final String  ROLLING_NOTE_MAX_LENGTH = "1900";
    
    public static final String RollingNoteHeaderDateDisplay = "Date/Time";
    public static final String RollingNoteDateDisplayLabel = "Date Added or Updated"; 
    public static final String RollingNoteDateDisplayTip = "This is a hidden read-only field for the Date the note was added or updated";
    public static final String RollingNoteHeaderUserDisplay = "Added/Updated By"; 
    public static final String RollingNoteUserDisplayLabel = "Added or Updated By"; 
    public static final String RollingNoteUserDisplayTip = "This is a hidden read-only field for the user that added or updated the note"; 
    public static final String ParticipantListDisplayTip = "This is a readonly list of all the participants associated with this event"; 
    public static final String OriginalDocumentDisplayTip = "This is a list of all original electronic documents imported for this event"; 
    

}
