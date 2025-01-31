//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\updatehandler\\MultiUpdateField.java

package gov.cdc.nedss.systemservice.mprupdateengine;

import java.util.List;

/**
 *
 * <p>Title: MultiUpdateField</p>
 * <p>Description: This is a helper class that defines the data structure needed for updating MPR
fields with multiple values.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author not attributable
 * @version 1.0
 */
class MultiUpdateField {
	private String soruceID;
	private String AODFieldName;
	private String collectionName;
	private List<Object> elements;

	/**
	 * Returns the name for the as-of-date field.
	 * @return String
	 */
	public String getAODFieldName() {
		return AODFieldName;
	}

	/**
	 * Returns the source ID for the field to be updated.
	 * @return String
	 */
	public String getSoruceID() {
		return soruceID;
	}

	/**
	 * Returns the collection of MultiUpdateFieldElement.
	 * @return List
	 */
	public List<Object> getElements() {
		return elements;
	}

	/**
	 * Sets the as-of-date field name.
	 * @param aODFieldName
	 */
	public void setAODFieldName(String aODFieldName) {
		AODFieldName = aODFieldName;
	}

	/**
	 * Sets the source ID for the field to be updated.
	 * @param collectionFieldName
	 */
	public void setSoruceID(String collectionFieldName) {
		this.soruceID = collectionFieldName;
	}

	/**
	 * Sets the the collection of MultiUpdateFieldElement.
	 * @param elements
	 */
	public void setElements(List<Object>elements) {
		this.elements = elements;
	}

	/**
	 * Returns the method name for the as-of-date field getter.
	 * @return String
	 */
	public String aODFieldGetMethodName() {
		return "get" + AODFieldName;
	}

	/**
	 * Returns the method name for the as-of-date field setter.
	 * @return String
	 */
	public String aODFieldSetMethodName() {
		return "set" + AODFieldName;
	}

	/**
	 * Returns the collection name.
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * Sets the collection name.
	 * @param collectionName
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * Returns the method name for the collection field getter.
	 * @return String
	 */
	public String collectionFieldGetMethodName() {
		return "get" + collectionName;
	}

	public String collectionFieldSetMethodName() {
		return "set" + collectionName;
	}
}
