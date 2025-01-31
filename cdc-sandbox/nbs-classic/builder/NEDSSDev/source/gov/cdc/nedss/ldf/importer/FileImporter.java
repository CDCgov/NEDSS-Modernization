/*
 * Created on Jan 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.sql.Timestamp;
import java.util.*;
import java.io.*;
import org.xml.sax.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.dt.CdfSubformImportLogDT;
import gov.cdc.nedss.ldf.importer.ImportConstants;
import gov.cdc.nedss.util.*;

/**
 * @author xzheng
 *
 * This is file importer implementation.
 */
class FileImporter extends AbstractBaseImporter {

	//For logging
	static final LogUtils logger =
		new LogUtils(FileImporter.class.getName());
		
	FileImporter() {

	}

	/** This method performs import of XML definition file.
	 *
	 * @return The import result.
	 * @throws NEDSSAppException
	 */
	public ImporterResult performImport() throws NEDSSAppException {

		CdfSubformImportLogDT dt = new CdfSubformImportLogDT();
		dt.setImportTime(new Timestamp(new Date().getTime()));
		dt.setSourceNm(getSource());

		ImporterResult result = new ImporterResult();
		ImportDataJaxbParser parser = null;
		Long version = null;
		boolean importSuccessful = true;
		Long uid = null;

		// Step 1:  parse the file first
		try {
			parser = new ImportDataJaxbParser();
			parser.setImportFile(getSource());
			parser.setImportDirectory(getSourceLocation());
			parser.parse();
			version = new Long(parser.getVersion());

			// TODO: use java resources to load the process code
			// and log txt description.  For now hard coded.
			// also put the process code in a constant file.
			dt.setImportVersionNbr(version);
			dt.setAdminComment(parser.getAdminComment());
			dt.setProcessCd(ImportConstants.SUCCESS_CODE);
			dt.setLogMessageTxt(ImportConstants.SUCCESS_TEXT);

			try{
				ImportLogHelper helper = new ImportLogHelper();
				uid = helper.processImportLog(dt);
				
				result.setImportUid(uid);
				result.setDefinedFieldImportData(parser.getDefinedFieldDataList());
				result.setSubformImportData(parser.getSubformDataList());
				
			} catch (NEDSSDBUniqueKeyViolation e1){
				// this is caused by importing a duplicated version, set the version number to null
				dt.setImportVersionNbr(null);
				dt.setProcessCd(ImportConstants.INCONSISTENT_DATA);
				dt.setLogMessageTxt(ImportConstants.DUPLICATED_IMPORT_VERSION_NBR_TEXT + version);
				importSuccessful = false;
				
				ImportLogHelper helper = new ImportLogHelper();
				uid = helper.processImportLog(dt);
				result.setImportUid(uid);
			}

		} catch (IOException e) {
			dt.setProcessCd(ImportConstants.INVALID_FILE_CODE);
			dt.setLogMessageTxt(ImportConstants.INVALID_FILE_TEXT + getSource());
			importSuccessful = false;
				
			ImportLogHelper helper = new ImportLogHelper();
			uid = helper.processImportLog(dt);
			result.setImportUid(uid);

		} catch (NEDSSAppException e) {
			dt.setProcessCd(ImportConstants.INVALID_FILE_FORMAT_CODE);
			dt.setLogMessageTxt(e.getMessage());
			importSuccessful = false;
				
			ImportLogHelper helper = new ImportLogHelper();
			uid = helper.processImportLog(dt);
			result.setImportUid(uid);
		}
		
		// move the file to success folder
		FileImporterHelper importerHelper =
			(FileImporterHelper) ImporterFactory
				.getInstance()
				.getImporterHelper(
				ImportConstants.FILE);
		if (importSuccessful) {
			importerHelper.moveFileToSuccessDir(getSource());
		} else {
			importerHelper.moveSubFormToFailureDir(getSource());
		}

		//System.out.println(cdfList.size());
		return result;
	}

	protected String extractSourceLocation() {
		//System.out.println("Directory == " + new File(getSource()).getParent());
		//System.out.println("File == " + new File(getSource()).getName());
		return new File(getSource()).getParent() + File.separator;
	}

	public static void main(String[] args)
		throws SAXException, IOException, NEDSSAppException {

		FileImporter importer = new FileImporter();
		importer.source =
			PropertyUtil.getInstance().getProperty(
				ImportConstants.IMPORT_FILE_DIRECTORY_KEY,
				ImportConstants.DEFAULT_IMPORT_FILE_DIRECTORY)
				+ "SampleImport.xml";
		importer.performImport();
	}

}
