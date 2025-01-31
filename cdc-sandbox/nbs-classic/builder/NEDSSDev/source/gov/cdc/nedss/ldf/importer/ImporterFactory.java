/*
 * Created on Jan 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.ImportConstants;

import java.util.*;

/**
 * @author xzheng
 *
 * This is a singleton class that serves as the object factory for custom defined field
 * and subform importer.  The default importer is file based importer.
 *
 */
public class ImporterFactory {

	/**
	 * unique instance
	 * */
	private static ImporterFactory sInstance = null;

	private Map<Object,Object> importers = null;
	private Map<Object,Object> importerHelpers = null;
	/**
	 * default importer key
	 * */
	public static String DEFAULT_IMPORTER = ImportConstants.FILE;

	/**
	 * Private constuctor
	 */
	private ImporterFactory() {
		importers = new HashMap<Object,Object>();
		importerHelpers = new HashMap<Object,Object>();
		try {
			//TODO: we may need to configure this so that it
			// is more flexible.  For now we only support file
			// based importer
			importers.put(
				DEFAULT_IMPORTER,
				Class.forName("gov.cdc.nedss.ldf.importer.FileImporter"));
			importerHelpers.put(
				DEFAULT_IMPORTER,
				Class.forName("gov.cdc.nedss.ldf.importer.FileImporterHelper"));
			} catch (ClassNotFoundException e) {
			throw new NEDSSSystemException(ImportConstants.FACTORY_INITIALIZATION_FAILURE_TEXT);
		}
	}

	/**
	 * Get the unique instance of this class.
	 */
	public static synchronized ImporterFactory getInstance() {

		if (sInstance == null) {
			sInstance = new ImporterFactory();
		}

		return sInstance;

	}

	/** This method returns the default importer implementation.
	 *
	 * @param source The source location for the importer.
	 * @return
	 */
	public Importer getDefaultImporter(String source) {
		return getImporter(DEFAULT_IMPORTER, source);
	}

	/** This method returns the importer specified by the key.
	 *
	 * @param source The source location for the importer.
	 * @return
	 */
	public Importer getImporter(String key, String source) {
		if (key == null || source == null) {
			throw new NEDSSSystemException(ImportConstants.INVALID_PARAMETERS_TEXT);
		}
		Class importerClass = (Class) importers.get(key);
		if (importerClass != null) {
			try {
				AbstractBaseImporter importer =
					(AbstractBaseImporter) importerClass.newInstance();
				importer.init(source);
				return importer;
			} catch (InstantiationException e) {
				throw new NEDSSSystemException(ImportConstants.IMPORTER_CREATE_FAILURE_TEXT);
			} catch (IllegalAccessException e) {
				throw new NEDSSSystemException(ImportConstants.IMPORTER_CREATE_FAILURE_TEXT);
			}
		}
		return null;
	}

	/** This method returns the default importer implementation.
	 *
	 * @param source The source location for the importer.
	 * @return
	 */
	public ImporterHelper getDefaultImporterHelper() {
		return getImporterHelper(DEFAULT_IMPORTER);
	}

	/** This method returns the importer specified by the key.
	 *
	 * @param source The source location for the importer.
	 * @return
	 */
	public ImporterHelper getImporterHelper(String key) {
		if (key == null) {
			throw new NEDSSSystemException(ImportConstants.INVALID_PARAMETERS_TEXT);
		}
		Class helperClass = (Class) importerHelpers.get(key);
		if (helperClass != null) {
			try {
				ImporterHelper helper =
					(ImporterHelper) helperClass.newInstance();
				return helper;
			} catch (InstantiationException e) {
				throw new NEDSSSystemException(ImportConstants.IMPORTER_CREATE_FAILURE_TEXT);
			} catch (IllegalAccessException e) {
				throw new NEDSSSystemException(ImportConstants.IMPORTER_CREATE_FAILURE_TEXT);
			}
		}
		return null;
	}
}
