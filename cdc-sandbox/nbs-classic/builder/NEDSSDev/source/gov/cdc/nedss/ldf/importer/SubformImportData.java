/*
 * Created on Jan 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.io.*;

/**
 * @author xzheng
 *
 * This class represents the custom subform nodes
 * specified in the import XML file.
 *
 */
public class SubformImportData implements ImportData, Serializable {

	private String objectID;
	private String comment;
	private String actionType;
	private String classCode;
	private String NEDSSBussinessObjectName;
	private String conditionCode;
	private String displayOrder;
	private String name;
	private String HTMLSource;
	private String importVersionNbr;
	private String pageSetID;
	private String stateCode;
	private String deploymentCode;

	/**
	 *
	 * @param objectID
	 * @return
	 */
	public String getObjectID() {
		return this.objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getActionType() {
		return this.actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getNEDSSBussinessObjectName() {
		return this.NEDSSBussinessObjectName;
	}
	public void setNEDSSBussinessObjectName(String NEDSSBussinessObjectName) {
		this.NEDSSBussinessObjectName = NEDSSBussinessObjectName;
	}
	public String getConditionCode() {
		return this.conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	public String getDisplayOrder() {
		return this.displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHTMLSource() {
		return this.HTMLSource;
	}
	public void setHTMLSource(String HTMLSource) {
		this.HTMLSource = HTMLSource;
	}
	public String getImportVersionNbr() {
		return this.importVersionNbr;
	}
	public void setImportVersionNbr(String importVersionNbr) {
		this.importVersionNbr = importVersionNbr;
	}
	public String getPageSetID() {
		return this.pageSetID;
	}
	public void setPageSetID(String pageSetID) {
		this.pageSetID = pageSetID;
	}
	public String getStateCode() {
		return this.stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getDeploymentCode() {
		return this.deploymentCode;
	}
	public void setDeploymentCode(String deploymentCode) {
		this.deploymentCode = deploymentCode;
	}
	/**
	 * @return
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * @param string
	 */
	public void setClassCode(String string) {
		classCode = string;
	}

}
