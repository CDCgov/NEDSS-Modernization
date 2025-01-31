package gov.cdc.nedss.webapp.nbs.queue;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class QueueColumnDT  extends AbstractVO
implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	
	private String recordStatusCd;//From RootDTInterface
    private String columnId; //This should be column1, column2, column3 in case of visible columns and columnHidden1, columnHidden2, columnHidden3 in case of hidden columns.
    private String columnName; //This is the title of the column shown in the table, like: Document Type
    private String backendId; //This is an id used to store the value to filter by. For example, in case a text filter, like the patient name, the backendId could be Patient, and we can find the value to filter by in $(“#PATIENT”).
    private String defaultOrder; //The possible values are ascending/descending, depending on the defaultOrder value to apply to the display tag
    private String sortable; //The possible values are true/false depending on the sortable value to apply to the display tag.
    private String sortNameMethod; //like getType, the method used to sort the value of each of the columns from each of the objects on the list.
    private String media; //html/csv/pdf. It could be just html, our html pdf, or html pdf csv, or pdf csv, etc. Any combination.
    private String mediaHtmlProperty;//In case the html is included in the field media, then this is the value used in display:column property.
    private String mediaCsvProperty; //In case the Csv is included in the field media, then this is the value used in display:column property.
    private String mediaPdfProperty; //In case the Pdf is included in the field media, then this is the value used in display:column property.
    private String columnStyle; //Normally, the width of the column displayed in the table, like width: 12%
    private String filterType; //0 if it’s a date type,1 if it’s a text type or 2 if it’s a multiselect type (with one or more values to select by).
    private String className; //This is used for applying a class to the column. For example, in case is a hidden column, the class could be “hidden”. This is used for Program area in DRRQ, for example.
    private String headerClass; //It has the same used than className. However I think we can remove this attribute and not use it for hiding a column.
    private String multipleValues;//true/false if per each cell there's more than 1 value, like description column in DRRQ.
    private String methodGeneralFromForm;//This is only used if the column is of type Date or multiselect. The method name defined here is the one used to get all the elements to show in the dropdown filter from the form.
    private String methodFromElement;//This is the method to get a value from the object. For example, if this is the column that shows the jurisdiction, in order to be able to filter by the correct objects, this method would be getJurisdiction.
    private String filterByConstant; //In NedssConstants.java there are some Constants defined used by the filtering, like a descriptive text. For example: public static String FILTERBYJURISDICTION = "Jurisdiction equal to: ";
   // private String filterTextInput ;//I think we are not using this field and we can remove it
    private String dropdownProperty; //This is used from the JSP that defines the dropdowns in the <html:select property= tag. it would be OBSERVATIONTYPE  for getting the data from the answerArray: (answerArray(OBSERVATIONTYPE)) used from the java for getting the values
    private String dropdownStyleId; // This is used from the JSP that defines the dropdowns in the <html:select styleId= tag. This is the id of the filtering dropdown, like sdate.
    private String dropdownsValues; // This is used from the JSP that defines the dropdowns in the <html:optionsCollection property= tag for getting all the values in the filtering dropdown.
    private String dropdownStyle; //The style applied to filtering dropdown, like the width.
    private String errorIdFiltering; //Used for error message purposes, like OBS118. I’m not sure if it’s the same than backendId.
    private String constantCount; //Used from setQueueCount, to store in the map the count for each column, like JURISDICTIONS_COUNT
    //private String searchCriteriaConstant; //I think this is the same than dropdownProperty and we can remove it.
    
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		recordStatusCd = aRecordStatusCd;
		
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
/*
	public String getColumnPropertyName() {
		return columnPropertyName;
	}

	public void setColumnPropertyName(String columnPropertyName) {
		this.columnPropertyName = columnPropertyName;
	}*/

	public String getBackendId() {
		return backendId;
	}

	public void setBackendId(String backendId) {
		this.backendId = backendId;
	}

	public String getDefaultOrder() {
		return defaultOrder;
	}

	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getSortNameMethod() {
		return sortNameMethod;
	}

	public void setSortNameMethod(String sortNameMethod) {
		this.sortNameMethod = sortNameMethod;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getColumnStyle() {
		return columnStyle;
	}

	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getHeaderClass() {
		return headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}
/*
	public String getFilterTextInput() {
		return filterTextInput;
	}

	public void setFilterTextInput(String filterTextInput) {
		this.filterTextInput = filterTextInput;
	}
*/
	public String getDropdownProperty() {
		return dropdownProperty;
	}

	public void setDropdownProperty(String dropdownProperty) {
		this.dropdownProperty = dropdownProperty;
	}

	public String getDropdownStyleId() {
		return dropdownStyleId;
	}

	public void setDropdownStyleId(String dropdownStyleId) {
		this.dropdownStyleId = dropdownStyleId;
	}

	public String getDropdownsValues() {
		return dropdownsValues;
	}

	public void setDropdownsValues(String dropdownsValues) {
		this.dropdownsValues = dropdownsValues;
	}

	public String getDropdownStyle() {
		return dropdownStyle;
	}

	public void setDropdownStyle(String dropdownStyle) {
		this.dropdownStyle = dropdownStyle;
	}

	public String getErrorIdFiltering() {
		return errorIdFiltering;
	}

	public void setErrorIdFiltering(String errorIdFiltering) {
		this.errorIdFiltering = errorIdFiltering;
	}

	public String getConstantCount() {
		return constantCount;
	}

	public void setConstantCount(String constantCount) {
		this.constantCount = constantCount;
	}
/*
	public String getSearchCriteriaConstant() {
		return searchCriteriaConstant;
	}

	public void setSearchCriteriaConstant(String searchCriteriaConstant) {
		this.searchCriteriaConstant = searchCriteriaConstant;
	}*/

	public String getMethodFromElement() {
		return methodFromElement;
	}

	public void setMethodFromElement(String methodFromForm) {
		this.methodFromElement = methodFromForm;
	}

	public String getMultipleValues() {
		return multipleValues;
	}

	public void setMultipleValues(String multipleValues) {
		this.multipleValues = multipleValues;
	}

	public String getMethodGeneralFromForm() {
		return methodGeneralFromForm;
	}

	public void setMethodGeneralFromForm(String methodGeneralFromForm) {
		this.methodGeneralFromForm = methodGeneralFromForm;
	}

	public String getFilterByConstant() {
		return filterByConstant;
	}

	public void setFilterByConstant(String filterByConstant) {
		this.filterByConstant = filterByConstant;
	}

	public String getMediaHtmlProperty() {
		return mediaHtmlProperty;
	}

	public void setMediaHtmlProperty(String mediaHtmlProperty) {
		this.mediaHtmlProperty = mediaHtmlProperty;
	}

	public String getMediaCsvProperty() {
		return mediaCsvProperty;
	}

	public void setMediaCsvProperty(String mediaCsvProperty) {
		this.mediaCsvProperty = mediaCsvProperty;
	}

	public String getMediaPdfProperty() {
		return mediaPdfProperty;
	}

	public void setMediaPdfProperty(String mediaPdfProperty) {
		this.mediaPdfProperty = mediaPdfProperty;
	}



}
