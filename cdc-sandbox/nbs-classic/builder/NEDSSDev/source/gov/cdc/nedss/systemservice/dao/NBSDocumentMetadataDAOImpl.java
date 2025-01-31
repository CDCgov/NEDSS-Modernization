package gov.cdc.nedss.systemservice.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;

public class NBSDocumentMetadataDAOImpl extends DAOBase {
	private static final LogUtils logger = new LogUtils(NBSDocumentMetadataDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	private static String SELECT_NBS_DOCUMENT_METADATA_UID_BY_DOC_TYPE_CD = 
		"SELECT nbs_document_metadata_uid FROM " + DataTables.NBS_DOCUMENT_METADATA_TABLE + " WHERE doc_type_cd = ?";


	public Long getNBSDocumentMetadataUIDByDocTypeCd(String docTypeCd) {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        Long nbsDocumentMetadataUID;
        
        try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_NBS_DOCUMENT_METADATA_UID_BY_DOC_TYPE_CD);
			preparedStmt.setString(1, docTypeCd);
			resultSet = preparedStmt.executeQuery();
            if (resultSet.next())
            	nbsDocumentMetadataUID = new Long(resultSet.getLong(1));
            else {
            	String errorMsg = "NBSDocumentMetadataDAOImpl.getNBSDocumentMetadataUIDByDocTypeCd - Could not find metadata row for doc_type_cd = " + docTypeCd;
    			logger.error(errorMsg); 
    			throw new NEDSSSystemException(errorMsg);
            }

		} catch (Exception ex) {
			String errorMsg = "NBSDocumentMetadataDAOImpl.getNBSDocumentMetadataUIDByDocTypeCd:  Exception while selecting for doc_type_cd = " + docTypeCd +"\n";
			logger.fatal(errorMsg, ex);
			throw new NEDSSSystemException(errorMsg + ex.getMessage(), ex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
		return nbsDocumentMetadataUID;
	}

	public Collection<Object> getNBSDocumentMetadata()
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String docQuery = "";

		try{

			dbConnection = getConnection();

			
				docQuery = " SELECT "
					+ " nbsdocmeta.nbs_document_metadata_uid nbsDocumentMetadataUid," 
					+ " nbsdocmeta.xml_schema_location  xmlSchemaLocation,"
					+ " nbsdocmeta.document_view_xsl documentViewXslTxt,"
					+ " nbsdocmeta.document_view_cda_xsl documentViewCdaXslTxt,"
					+ " nbsdocmeta.description  description,"
					+ " nbsdocmeta.doc_type_cd docTypeCd,"
					+	" nbsdocmeta.add_time addTime," 
					+ " nbsdocmeta.add_user_id addUserId," 
					+ " nbsdocmeta.record_status_cd recordStatusCd,"
					+ " nbsdocmeta.record_status_Time recordStatusTime," 
					+ " nbsdocmeta.xmlbean_factory_class_nm xmlbeanFactoryClassNm ," 
					+ " nbsdocmeta.parser_class_nm parserClassNm " 
					+ " FROM " 
					+ DataTables.NBS_DOCUMENT_METADATA_TABLE  
					+ " nbsdocmeta";	



			preparedStmt = dbConnection.prepareStatement(docQuery);

			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  docMetaList = new ArrayList<Object> ();

			if(resultSet!=null)
				resultSetMetaData = resultSet.getMetaData();

			docMetaList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
																	 resultSetMetaData, 
																	 NBSDocumentMetadataDT.class, 
																	 docMetaList);

			
			return docMetaList;
			
		} catch(Exception e) {
			logger.fatal("Error in fetching Document Metadata  "+e.getMessage(), e); 
			throw new NEDSSSystemException(e.toString(), e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		} //end of finally 

	}

}
