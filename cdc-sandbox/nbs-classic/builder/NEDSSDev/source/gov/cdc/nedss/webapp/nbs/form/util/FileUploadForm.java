package gov.cdc.nedss.webapp.nbs.form.util;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * FileUploadForm.java
 * Nov 19, 2009
 * @version 1.0
 */
public class FileUploadForm extends ActionForm {
	
	private String fileName;
	private String fileDescription;
    private FormFile ctFile;
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public FormFile getCtFile() {
		return ctFile;
	}
	public void setCtFile(FormFile ctFile) {
		this.ctFile = ctFile;
	}
	
	public void reset() {
		fileName = "";
		fileDescription = "";
		ctFile = null;
	}
}