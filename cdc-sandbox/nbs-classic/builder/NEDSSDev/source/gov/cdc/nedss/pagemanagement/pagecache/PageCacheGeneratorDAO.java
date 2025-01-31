package gov.cdc.nedss.pagemanagement.pagecache;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.pagecache.util.PageBuilder;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PageCacheGeneratorDAO.java
 * Jan 04, 2010
 * @version 1.0
 */
public class PageCacheGeneratorDAO extends DAOBase
{

    private static final Logger logger = Logger.getLogger(PageCacheGeneratorDAO.class);

    //Eventually sql should hit the WA_Tables to join the UI_Component for the JSP snippets
    private static final String sql = "SELECT NBS_UI_METADATA_UID \"nbsUiMetadataUid\"," +
    								  " NBS_UI_COMPONENT_UID \"nbsUiComponentUid\"," +
    								  " NBS_QUESTION_UID \"nbsQuestionUid\"," +
    								  " PARENT_UID \"parentUid\"," +
    								  " QUESTION_LABEL \"questionLabel\"," +
    								  " JSP_SNIPPET_CREATE_EDIT \"jspSnippetCreateEdit\"," +
    								  " JSP_SNIPPET_VIEW \"jspSnippetView\"" +
    								  " FROM NBS_UI_METADATA" +
    								  " WHERE (INVESTIGATION_FORM_CD = ?)";

    /**
     * buildServerPageCache is a PageManagement Utility that:
     *  (a) Builds JSP Pages from a given ConditionCd/FormCd during Page Publish
     *  (b) Writes the generated JSP to appropriate <nbs.dir> System Folder to load appropriately.
     *  
     * @param uid the UID of the record to view.
     * @return java.util.ArrayList <NbsQuestionMetadata>
     * @throws NEDSSDAOSysException if an error occurs.
     */
    private ArrayList<Object> buildServerPageCache(String invFormCd) throws NEDSSDAOSysException {
    	
    	ArrayList<Object> questionsList = new ArrayList<Object> ();
    	ResultSetMetaData resultSetMetaData = null;
    	ResultSetUtils resultSetUtils = new ResultSetUtils();
    	
    	if(invFormCd == null)
    		invFormCd = "INV_FORM_CHLR";
    	
    	NbsQuestionMetadata dt = new NbsQuestionMetadata();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            ps = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, invFormCd);            
            rs = ps.executeQuery();
			if(rs!=null){
				resultSetMetaData = rs.getMetaData();
				questionsList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(rs,
						resultSetMetaData, NbsQuestionMetadata.class, questionsList);

				logger.debug("returned questions list");
			}

        } catch(Exception ex) {
            logger.fatal("Exception ="+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        finally {
        	closeResultSet(rs);
			closeStatement(ps);
			releaseConnection(con);
        }
        return questionsList;
    }
    /**
     * 
     * @return
     */
    public boolean renderJSP(String mode) {
    	
    	boolean success = false;
    	
    	ArrayList<Object> list = buildServerPageCache(null);
		PageBuilder pb =PageBuilder.getInstance();
		try {
			pb.writeToJSP(list, mode);
		} catch (Exception e) {
			logger.fatal("Error while writing to JSP: " + e.getMessage(),e);
			throw new NEDSSSystemException("Error while writing to JSP: " + e.getMessage());
		} 
    		
    	return success;
    }
    
}
