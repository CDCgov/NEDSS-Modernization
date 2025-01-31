package gov.cdc.nedss.ldf.importer;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.imported.generated.CustomDefinedFieldType;
import gov.cdc.nedss.ldf.imported.generated.CustomSubForm;
import gov.cdc.nedss.ldf.imported.generated.CustomSubFormType;
import gov.cdc.nedss.ldf.imported.generated.NEDSSImport;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ImportDataJaxbParser {

	static final LogUtils logger =
		new LogUtils(ImportDataJaxbParser.class.getName());

	private ArrayList<?> definedFieldDataList = new ArrayList<Object> ();
	private ArrayList<?> subformDataList = new ArrayList<Object> ();
	private String version;
	private String adminComment;
	private String importFile;
	private String importDirectory;
	
	private JAXBContext jc;

	private final String PACKAGE_NAME = "gov.cdc.nedss.ldf.imported.generated";

	/**
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return
	 */
	public String setVersion(String version) {
		return this.version = version;
	}

	/**
	 * @param string
	 */
	public void setImportDirectory(String string) {
		importDirectory = string;
	}

	/**
	 * @param string
	 */
	public void setImportFile(String string) {
		importFile = string;
	}

	/**
	 * @return
	 */
	public String getImportDirectory() {
		return importDirectory;
	}

	/**
	 * @return
	 */
	public String getImportFile() {
		return importFile;
	}

	public ImportDataJaxbParser() throws NEDSSAppException {
		try{
		    jc = JAXBContext.newInstance(PACKAGE_NAME);
		} catch(JAXBException ex){
			throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	public void parse() throws IOException,NEDSSSystemException, NEDSSAppException{
		try{
			Unmarshaller u = jc.createUnmarshaller();
			
			NEDSSImport nedssImport =
				(NEDSSImport)u.unmarshal(new FileInputStream(importFile));
			this.adminComment = nedssImport.getComment();
			this.version = nedssImport.getVersion().toString();

			if(nedssImport.getCustomDefinedFields() != null){
				this.definedFieldDataList = extractDefinedFieldDataList(
						nedssImport.getCustomDefinedFields().getCustomDefinedField());
			}
			if(nedssImport.getCustomSubForms() != null){
				this.subformDataList = extractSubformDataList(
						nedssImport.getCustomSubForms().getCustomSubForm());
			}
			if (definedFieldDataList != null) {

				for (Iterator<?> iter = definedFieldDataList.iterator();
					iter.hasNext();
					) {
					DefinedFieldImportData element =
						(DefinedFieldImportData) iter.next();
					element.setImportVersionNbr(version);
				}

			}

			if (subformDataList != null) {

				for (Iterator<?> iter = subformDataList.iterator(); iter.hasNext();) {
					SubformImportData element = (SubformImportData) iter.next();
					element.setImportVersionNbr(version);

					element.setHTMLSource(
						PropertyUtil.getInstance().getProperty(
							ImportConstants.SF_XHTML_DIRECTORY_KEY,
							ImportConstants.DEFAULT_SF_XHTML_DIRECTORY)
							+ element.getHTMLSource());
				}
			}
			
			
		}catch(JAXBException e){
			throw new NEDSSAppException(e.getMessage());
		}
	}
	
	/**
	 * @param customSubForms
	 * @return
	 */
	private ArrayList<Object> extractSubformDataList(List<Object>customSubForms) {
		ArrayList<Object> list = new ArrayList<Object> ();
		
		for(int i=0; i < customSubForms.size(); i++){
			SubformImportData data = new SubformImportData();
			
			CustomSubFormType form = (CustomSubFormType)customSubForms.get(i);
			
			data.setObjectID(form.getObjectID());
			data.setComment(form.getComment());
			data.setActionType(form.getActionType());
			data.setDisplayOrder(form.getDisplayOrder());
			data.setName(form.getName()); //??
			data.setClassCode(form.getClassCode());
			data.setHTMLSource(form.getHTMLSource());
			data.setPageSetID(form.getPageSetID());
			
			
			list.add(data);
		}
		return list;
	}

	/**
	 * @param customDefinedFields
	 * @return
	 */
	private ArrayList<Object> extractDefinedFieldDataList(List<Object>customDefinedFields) {
		ArrayList<Object> list = new ArrayList<Object> ();
		
		for(int i=0; i < customDefinedFields.size(); i++){
			DefinedFieldImportData data = new DefinedFieldImportData();
			
			CustomDefinedFieldType field = (CustomDefinedFieldType)customDefinedFields.get(i);
			data.setObjectID(field.getObjectID());
			data.setComment(field.getComment());
			data.setActionType(field.getActionType());
			data.setNationalIdentifier(field.getNationalIdentifier());
			data.setDataType(field.getDataType());
			data.setCodeSetName(field.getCodeSetName());
			data.setDisplayOrder(field.getDisplayOrder());
			data.setFieldSize(field.getFieldSize());
			data.setLabelText(field.getLabelText());
			data.setValidationText(field.getValidationText());
			data.setRequiredIndicator(field.getRequiredIndicator());
			data.setPageSetID(field.getPageSetID());
			data.setValidationJavaScriptText(field.getValidationJavaScriptText());
			data.setNNDIndicator(field.getNNDIndicator());
			
			list.add(data);
		}
		return list;
	}

	/**
	 * @return
	 */
	public ArrayList<?> getDefinedFieldDataList() {
		return definedFieldDataList;
	}

	/**
	 * @return
	 */
	public ArrayList<?> getSubformDataList() {
		return subformDataList;
	}

	/**
	 * @return
	 */
	public String getAdminComment() {
		return adminComment;
	}

	/**
	 * @param string
	 */
	public void setAdminComment(String string) {
		adminComment = string;
	}
	
	public static void main(String[] args) throws Exception{
		ImportDataJaxbParser parser = new ImportDataJaxbParser();
		parser.parse();
		parser.getDefinedFieldDataList();
	}
}
