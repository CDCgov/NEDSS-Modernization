/*
 * Created on Jan 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.io.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;

/**
 * @author xzheng
 *
 * The helper for FileImporter.
 */
public class FileImporterHelper extends ImporterHelper{

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.importer.ImporterHelper#getImportFileNames()
	 */
	public String[] getImportFileNames() {
		File sourceDirectory =
			new File(
				PropertyUtil.getInstance().getProperty(
					ImportConstants.IMPORT_FILE_DIRECTORY_KEY,
					ImportConstants.DEFAULT_IMPORT_FILE_DIRECTORY));
                ImportFileNameFilter importFileNameFilter = new ImportFileNameFilter();
		return sourceDirectory.list(importFileNameFilter);

	}

	/**
	*
	* @param sourceFileName
	* @throws NEDSSAppException
	*/
	public void moveSubFormToFailureDir(String sourceFileName)
		throws NEDSSAppException {

		File fileComplete = new File(sourceFileName);
		String fileName = fileComplete.getName();
		if (!fileComplete.exists()) {
			System.out.println("file does not exist !!!");
			throw new NEDSSAppException(
				fileName
					+ ImportConstants.FILE_NOT_EXISTS_TEXT
					+ fileComplete.getParent()
					+ " !!!");
		} else {

			File failureDirectory =
				new File(
					PropertyUtil.getInstance().getProperty(
						ImportConstants.IMPORT_FAILURE_DIRECTORY_KEY,
						ImportConstants.DEFAULT_IMPORT_FAILURE_DIRECTORY));
			if (!failureDirectory.exists()) {
				failureDirectory.mkdirs();
			}
			fileComplete.renameTo(new File(failureDirectory + "/" + fileName));
			System.out.println(
				"wrote "
					+ sourceFileName
					+ " successfully to "
					+ failureDirectory
					+ " !!!");
		}

	}


	/**
	 *
	 * @param sourceFileName
	 * @throws NEDSSAppException
	 */
	public void moveFileToSuccessDir(String sourcefileName)
		throws NEDSSAppException {

		File fileComplete = new File(sourcefileName);
		String fileName = fileComplete.getName();
		if (!fileComplete.exists()) {
			throw new NEDSSAppException(
				fileName
					+ ImportConstants.FILE_NOT_EXISTS_TEXT
					+ fileComplete.getParent()
					+ " !!!");
		} else {
			File successDirectory =
				new File(
					PropertyUtil.getInstance().getProperty(
						ImportConstants.IMPORT_SUCCESS_DIRECTORY_KEY,
						ImportConstants.DEFAULT_IMPORT_SUCCESS_DIRECTORY));
			if (!successDirectory.exists()) {
				successDirectory.mkdirs();
			}
			fileComplete.renameTo(new File(successDirectory + "/" + fileName));
			System.out.println(
				"wrote "
					+ sourcefileName
					+ " successfully to "
					+ successDirectory
					+ " !!!");
		}
	}
}
