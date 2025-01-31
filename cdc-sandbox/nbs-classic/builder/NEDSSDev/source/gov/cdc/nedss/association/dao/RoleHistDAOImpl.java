package gov.cdc.nedss.association.dao;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */
public class RoleHistDAOImpl
    extends BMPBase
{

    static final LogUtils logger = new LogUtils(RoleHistDAOImpl.class.getName());
    private long subjectEntityUid = -1;
    private String cd = "";
    private short roleSeq = -1;
    private short versionCtrlNbr = 0;
    private final String SELECT_ROLE_VERSION_CTRL_NBR_HIST =
            "select version_ctrl_nbr from role_hist where subject_entity_uid = ? and cd = ? and role_seq = ?";
    private final String INSERT_ROLE_HIST = "insert into role_hist(" +
                                            "subject_entity_uid, " + "cd, " +
                                            "role_seq, " +
                                            "version_ctrl_nbr, " +
                                            "add_reason_cd, " +
                                            "add_time, " + "add_user_id, " +
                                            "cd_desc_txt, " +
                                            "effective_duration_amt, " +
                                            "effective_duration_unit_cd, " +
                                            "effective_from_time, " +
                                            "effective_to_time, " +
                                            "last_chg_reason_cd, " +
                                            "last_chg_time, " +
                                            "last_chg_user_id, " +
                                            "record_status_cd, " +
                                            "record_status_time, " +
                                            "scoping_class_cd, " +
                                            "scoping_entity_uid, " +
                                            "scoping_role_cd, " +
                                            "scoping_role_seq, " +
                                            "status_time, " + "status_cd, " +
                                            "subject_class_cd, " +
                                            "user_affiliation_txt) " +
                                            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String SELECT_ROLE_HIST =
        "select subject_entity_uid \"subjectEntityUid\", " + "cd \"cd\", " +
         "role_seq \"roleSeq\", " + "version_ctrl_nbr \"versionCtrlNbr\", " +
         "add_reason_cd \"addReasonCd\", " + "add_time \"addTime\", " +
         "add_user_id \"addUserId\", " + "cd_desc_txt \"cdDescTxt\", " +
         "effective_duration_amt \"effectiveDurationAmt\", " +
         "effective_duration_unit_cd \"effectiveDurationUnitCd\", " +
         "effective_from_time \"effectiveFromTime\", " +
         "effective_to_time \"effectiveToTime\", " +
         "last_chg_reason_cd \"lastChgReasonCd\", " +
         "last_chg_time \"lastChgTime\", " +
         "last_chg_user_id \"lastChgUserId\", " +
         "record_status_cd \"recordStatusCd\", " +
         "record_status_time \"recordStatusTime\", " +
         "scoping_class_cd \"scoping_class_cd\", " +
         "scoping_entity_uid \"scopingEntityUid\", " +
         "scoping_role_cd \"scopingRoleCd\", " +
         "scoping_role_seq \"scopingRoleSeq\", " +
         "status_time \"statusTime\", " + "status_cd \"statusCd\", " +
         "subject_class_cd \"subjectClassCd\", " +
         "user_affiliation_txt \"userAffiliationTxt\" " +
         "from role_hist where subject_entity_uid = ? " + "and cd = ? " +
         "and role_seq = ? " + "and version_ctrl_nbr = ?";

    /**
     * Creates a new RoleHistDAOImpl object.
     */
    public RoleHistDAOImpl()
    {
    } //end of constructors

    /**
     * Creates a new RoleHistDAOImpl object.
     *
     * @param versionCtrlNbr the data record version control number
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public RoleHistDAOImpl(int versionCtrlNbr)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.versionCtrlNbr = (short)versionCtrlNbr;
    } //end of constructor

    /**
     * Creates a new RoleHistDAOImpl object.
     *
     * @param subjectEntityUid the subject entity uid
     * @param cd the subject role code
     * @param roleSeq the subject role sequence number
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public RoleHistDAOImpl(long subjectEntityUid, String cd, short roleSeq)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        this.subjectEntityUid = subjectEntityUid;
	        this.cd = cd;
	        this.roleSeq = roleSeq;
	        getNextRoleVersionCtrlNbr();
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of constructor

    /**
     * A method used to update a role
     * @param obj the role object to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        RoleDT dt = (RoleDT)obj;
	
	        if (dt == null)
	            throw new NEDSSSystemException("Error: try to store null RoleDT object.");
	
	        insertRoleHist(dt);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of store()

    /**
     * A method used to update roles
     * @param coll the roles to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Collection<Object> coll)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        Iterator<Object> it = coll.iterator();
	
	        while (it.hasNext())
	        {
	            this.store(it.next());
	        } //end of while
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of store

    /**
     * A method used to retrieve a role object
     * @param subjectEntityUid the subject entity uid
     * @param cd the role code
     * @param roleSeq the subject entity role sequence number
     * @param versionCtrlNbr the entity role version contriol number
     * @return a RoleDT object
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public RoleDT load(Long subjectEntityUid, String cd, Integer roleSeq,
                       Integer versionCtrlNbr)
                throws NEDSSSystemException
    {
        logger.info("Starts loadObject() for a role history...");
        try{
	        RoleDT roleDT = selectRoleHist(subjectEntityUid.longValue(), cd,
	                                       roleSeq.shortValue(),
	                                       versionCtrlNbr.shortValue());
	        roleDT.setItNew(false);
	        roleDT.setItDirty(false);
	        logger.info(
	                "Done loadObject() for a activity locator participation history - return: " +
	                roleDT.toString());
	
	        return roleDT;
        }catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of load

    /**
     *  An accessor for the version control number
     * @return a short value
     */
    public short getVersionCtrlNbr()
    {

        return this.versionCtrlNbr;
    }

   ///////////////////////////////private class methods//////////////////
    private RoleDT selectRoleHist(long subjectEntityUid, String cd,
                                  short roleSeq, short roleVersionCtrlNbr)
                           throws NEDSSSystemException
    {

        RoleDT roleDT = new RoleDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal(
                    "SQLException while obtaining database connection " +
                    "for selectRoleHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ROLE_HIST);
            preparedStmt.setLong(1, subjectEntityUid);
            preparedStmt.setString(2, cd);
            preparedStmt.setShort(3, roleSeq);
            preparedStmt.setShort(4, roleVersionCtrlNbr);
            resultSet = preparedStmt.executeQuery();
            logger.debug(
                    "RoleDT object for role History: subjectEntityUid = " +
                    subjectEntityUid + ", cd = " + cd + ", roleSeq = " +
                    roleSeq);
            resultSetMetaData = resultSet.getMetaData();
            roleDT = (RoleDT)resultSetUtils.mapRsToBean(resultSet,
                                                        resultSetMetaData,
                                                        roleDT.getClass());
            roleDT.setItNew(false);
            roleDT.setItDirty(false);
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "SQLException while selecting " +
                    "Role history; subjectEntityUid = " + subjectEntityUid +
                    ", cd = " + cd + ", roleSeq = " + roleSeq, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while selecting " +
                    "Role history; subjectEntityUid = " + subjectEntityUid +
                    ", cd = " + cd + ", roleSeq = " + roleSeq, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.info("return RoleDT for Role history");

        return roleDT;
    } //end of selectRoleHist(...)

    private void insertRoleHist(RoleDT dt)
                         throws NEDSSDAOSysException, NEDSSSystemException
    {

        if (dt.getSubjectEntityUid() != null && dt.getCd() != null &&
            dt.getRoleSeq() != null)
        {

            int resultCount = 0;
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;

            try
            {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(INSERT_ROLE_HIST);
                pStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
                pStmt.setString(i++, dt.getCd());
                pStmt.setShort(i++, dt.getRoleSeq().shortValue());
                pStmt.setShort(i++, versionCtrlNbr);

                if (dt.getAddReasonCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAddReasonCd());

                if (dt.getAddTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getAddTime());

                if (dt.getAddUserId() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getAddUserId().longValue());

                if (dt.getCdDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getCdDescTxt());

                if (dt.getEffectiveDurationAmt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getEffectiveDurationAmt());

                if (dt.getEffectiveDurationUnitCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

                if (dt.getEffectiveFromTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

                if (dt.getEffectiveToTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getEffectiveToTime());

                if (dt.getLastChgReasonCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getLastChgReasonCd());

                if (dt.getLastChgTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getLastChgTime());

                if (dt.getLastChgUserId() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getLastChgUserId().longValue());

                if (dt.getRecordStatusCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getRecordStatusCd());

                if (dt.getRecordStatusTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getRecordStatusTime());

                if (dt.getScopingClassCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getScopingClassCd());

                if (dt.getScopingEntityUid() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getScopingEntityUid().longValue());

                if (dt.getScopingRoleCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getScopingRoleCd());

                if (dt.getScopingRoleSeq() == null)
                    pStmt.setNull(i++, Types.SMALLINT);
                else
                    pStmt.setShort(i++, dt.getScopingRoleSeq().shortValue());

                pStmt.setTimestamp(i++, dt.getStatusTime());
                pStmt.setString(i++, dt.getStatusCd());

                if (dt.getSubjectClassCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getSubjectClassCd());

                if (dt.getUserAffiliationTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getUserAffiliationTxt());

                resultCount = pStmt.executeUpdate();

                if (resultCount != 1)
                {
                    throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
                                                   resultCount);
                }
            }
            catch (SQLException se)
            {
            	logger.fatal("SQLException  = "+se.getMessage(), se);
                throw new NEDSSDAOSysException("Error: SQLException while inserting\n" +
                                               se.getMessage());
            }
            finally
            {
                closeStatement(pStmt);
                releaseConnection(dbConnection);
            } //end of finally
        } //end of if
    } //end of insertMaterialEncounterHist()

    private void getNextRoleVersionCtrlNbr()
    {

        ResultSet resultSet = null;
        short seqTemp = -1;
        Connection dbConnection = null;
        PreparedStatement pStmt = null;

        try
        {
            dbConnection = getConnection();
            pStmt = dbConnection.prepareStatement(
                            SELECT_ROLE_VERSION_CTRL_NBR_HIST);
            pStmt.setLong(1, subjectEntityUid);
            pStmt.setString(2, cd);
            pStmt.setShort(3, roleSeq);
            resultSet = pStmt.executeQuery();

            while (resultSet.next())
            {
                seqTemp = resultSet.getShort(1);
                logger.debug("RoleHistDAOImpl--seqTemp: " + seqTemp);

                if (seqTemp > versionCtrlNbr)
                    versionCtrlNbr = seqTemp;
            }

            ++versionCtrlNbr;
            logger.debug("RoleHistDAOImpl--versionCtrlNbr: " +
                         versionCtrlNbr);
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);;
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(pStmt);
            releaseConnection(dbConnection);
        } //end of finally
    } //end of getNextRoleVersionCtrlNbr()
} //end of RoleHistDAOImpl