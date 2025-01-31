package gov.cdc.nedss.act.observation.ejb.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.util.StringUtils;

public class EDXDocumentDAOImpl extends BMPBase{
    static final LogUtils logger = new LogUtils(EDXDocumentDAOImpl.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    public EDXDocumentDAOImpl()
    {
    }
    
    public long insertEDXDocument(EDXDocumentDT dt)
            throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining db connection " +
                "while inserting into EDX_Document", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
        Long edxDocumentUid = null;
        try
        {
        	preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_EDX_DOCUMENT, Statement.RETURN_GENERATED_KEYS);
        	
			
			int resultCount = 0;
			int i = 1;
			
			logger.debug("EDXDocumentDT = " + dt);
			if(dt.getActUid()!=null)
				preparedStmt.setLong(i++, dt.getActUid().longValue());
			else
				preparedStmt.setNull(i++, Types.BIGINT);
			
			
				preparedStmt.setString(i++, dt.getPayload());
			preparedStmt.setString(i++, dt.getRecordStatusCd());
			preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());
			preparedStmt.setTimestamp(i++, dt.getAddTime());
			preparedStmt.setString(i++, dt.getDocTypeCd());
			preparedStmt.setLong(i++, dt.getNbsDocumentMetadataUid()
					.longValue());

			preparedStmt.setString(i++, dt.getOriginalPayload());
			preparedStmt.setString(i++, dt.getOriginalDocTypeCd());
			if(dt.getEdxDocumentParentUid()!=null)
				preparedStmt.setLong(i++, dt.getEdxDocumentParentUid());
			else
				preparedStmt.setNull(i++, Types.BIGINT);
			
			
				resultCount = preparedStmt.executeUpdate();			
				ResultSet keyRS	= preparedStmt.getGeneratedKeys();
				if (keyRS.next()) {
					edxDocumentUid = keyRS.getLong(1);
				}
				if (keyRS != null)
					closeResultSet(keyRS);
		 
			
			logger.debug("EDXDocument inserted for act_uid " + dt.getActUid());
       }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException while inserting " +
                        "document into EDX_Document: \n", sqlex);
            throw new NEDSSDAOSysException( sqlex.toString() );
        }
        catch(Exception ex)
        {
            logger.fatal("Error while inserting EDXDocument", ex);
            throw new NEDSSSystemException (ex.toString());
        }
        finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        
        return edxDocumentUid;
    }//end of inserting a EDX_Document


    public Collection<Object> selectEDXDocumentCollection (long actUid) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        EDXDocumentDT eDXDocumentDT = new EDXDocumentDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectEDXDocumentCollection " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects selectEDXDocuments
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_EDX_DOCUMENT_COLLECTION);
            preparedStmt.setLong(1, actUid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            reSetList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, eDXDocumentDT.getClass(), reSetList);
            logger.debug("return EDX_Document collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            throw new NEDSSDAOSysException("SQLException while selecting " +
                            "EDX_Document collection; id = " + actUid + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException reuex)
        {
            logger.fatal("Error in result set handling while selecting selectEDXDocumentCollection.", reuex);
            throw new NEDSSDAOSysException(reuex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting EDXDocuments
    
    public EDXDocumentDT selectIndividualEDXDocument (long eDXDocumentUid) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        EDXDocumentDT eDXDocumentDT = new EDXDocumentDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectIndividualEDXDocument " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects selectEDXDocuments
         * "SELECT EDX_Document.EDX_Document_uid \"eDXDocumentUid\", EDX_Document.act_uid \"actUid\", "
			+ " EDX_Document.payload \"payload\" , "
			+ " EDX_Document.record_status_cd \"recordStatusCd\" , "
			+ " EDX_Document.record_status_time \"recordStatusTime\" , "
			+ " EDX_Document.add_time \"addTime\" , "
			+ " EDX_Document.doc_type_cd \"docTypeCd\" , "
			+ " NBS_document_metadata.document_view_xsl \"documentViewXsl\" , "
			+ " NBS_document_metadata.xml_schema_location \"xmlSchemaLocation\"  "
			+ " FROM EDX_Document, NBS_document_metadata "
			+ " WHERE EDX_Document.nbs_document_metadata_uid=NBS_document_metadata.nbs_document_metadata_uid "
			+ " and EDX_Document.EDX_Document_Uid  = ?"
         */
        try
        {
        	preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_INDIVIDUAL_EDX_DOCUMENT);
            preparedStmt.setLong(1, eDXDocumentUid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  otList = new ArrayList<Object> ();
          	otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, eDXDocumentDT.getClass(), otList);
            logger.debug("return EDXDocument collection");
            return (EDXDocumentDT)otList.get(0);
        }
        catch(SQLException se)
        {
            throw new NEDSSDAOSysException("SQLException while selecting " +
                            "EDX_Document ; eDXDocumentUid = " + eDXDocumentUid + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException reuex)
        {
            logger.fatal("Error in result set handling while selecting selectIndividualEDXDocument.", reuex);
            throw new NEDSSDAOSysException(reuex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting EDXDocument
    
/*public static void main(String args[])
    {
       logger.debug("EDXDocument - Doing the main thing");
     try
       {
    	 EDXDocumentDT dt = new EDXDocumentDT();
         Long uid = new Long(12);
         dt.setActUid(new Long(10180005));
         dt.setPayload("XML");
         dt.setRecordStatusCd("ACTIVE");
         dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
         dt.setDocTypeCd("Lab");
         dt.setAddTime(new Timestamp(new Date().getTime()));
         dt.setNbsDocumentMetadataUid(new Long(1005));
         EDXDocumentDAOImpl dao = new EDXDocumentDAOImpl();
         dao.insertEDXDocument(dt);
         logger.debug("Executed insertEDXDocument: " + dt);
       }
       catch(Exception e)
       {
         logger.debug("\n\nObsValueCodedDAOImpl ERROR : turkey no worky = \n" + e);

       }
     }
*/
}
