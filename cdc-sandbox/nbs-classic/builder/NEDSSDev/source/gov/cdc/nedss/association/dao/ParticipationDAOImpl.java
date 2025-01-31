//Source file: C:\\development\\source\\gov\\cdc\\nedss\\dao\\ParticipationDAOImpl.java
package gov.cdc.nedss.association.dao;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;


public class ParticipationDAOImpl
    extends BMPBase
{

    private static final String SET_PK = "where subject_entity_uid = ? and act_uid = ? and type_cd = ?";
    private static final String SET_UID = "where subject_entity_uid = ?";
    private static final String SET_ACT_UID = "where act_uid = ?";
    private static final String SET_SUBJECTUID_ACTUID = "where subject_entity_uid = ? and act_uid = ?";
    private static final String SET_TYPE = "where subject_entity_uid = ? and type_cd = ?";
    private static final String SET_TYPE_ACT_SUB_CDS = "where subject_entity_uid = ? and type_cd = ? and act_class_cd = ? and subject_class_cd = ?";
    private static final String SELECT = "SELECT " +
                                         "subject_entity_uid \"SubjectEntityUid\", " +
                                         "act_uid \"ActUid\", " +
                                         "subject_class_cd \"SubjectClassCd\", " +
                                         "act_class_cd \"ActClassCd\", " +
                                         "add_reason_cd \"AddReasonCd\", " +
                                         "add_time \"AddTime\", " +
                                         "add_user_id \"AddUserId\", " +
                                         "awareness_cd \"AwarenessCd\", " +
                                         "awareness_desc_txt \"AwarenessDescTxt\", " +
                                         "cd \"Cd\", " +
                                         "duration_amt \"DurationAmt\", " +
                                         "duration_unit_cd \"DurationUnitCd\", " +
                                         "from_time \"FromTime\", " +
                                         "last_chg_reason_cd \"LastChgReasonCd\", " +
                                         "last_chg_time \"LastChgTime\", " +
                                         "last_chg_user_id \"LastChgUserId\", " +
                                         "record_status_cd \"RecordStatusCd\", " +
                                         "record_status_time \"RecordStatusTime\", " +
                                         "role_seq \"RoleSeq\", " +
                                         "status_cd \"StatusCd\", " +
                                         "status_time \"StatusTime\", " +
                                         "to_time \"ToTime\", " +
                                         "type_cd \"TypeCd\", " +
                                         "type_desc_txt \"TypeDescTxt\", " +
                                         "user_affiliation_txt \"UserAffiliationTxt\" " +
                                         "from Participation with (NOLOCK)  ";
    private static final String SELECT_BY_PK = SELECT + SET_PK;
    private static final String SELECT_BY_SUBJECTUID_ACTUID = SELECT + SET_SUBJECTUID_ACTUID;
    private static final String SELECT_BY_UID = SELECT + SET_UID;
    private static final String SELECT_BY_ACT_UID = SELECT + SET_ACT_UID;
    private static final String SELECT_BY_TYPE = SELECT + SET_TYPE;
    private static final String SELECT_BY_TYPE_ACT_SUB_CD =
            SELECT + SET_TYPE_ACT_SUB_CDS;
    private static final String DELETE_BY_PK = "DELETE from Participation " +
                                               SET_PK;
    private static final String DELETE_BY_UID = "DELETE from Participation " +
                                                SET_UID;
    private static final String UPDATE = "UPDATE Participation SET " +
                                         "subject_entity_uid = ?, " +
                                         "act_uid = ?, " +
                                         "subject_class_cd = ?, " +
                                         "act_class_cd = ?, " +
                                         "add_reason_cd = ?, " +
                                         "add_time = ?, " +
                                         "add_user_id = ?, " +
                                         "awareness_cd = ?, " +
                                         "awareness_desc_txt = ?, " +
                                         "cd = ?, " + "duration_amt = ?, " +
                                         "duration_unit_cd = ?, " +
                                         "from_time = ?, " +
                                         "last_chg_reason_cd = ?, " +
                                         "last_chg_time = ?, " +
                                         "last_chg_user_id = ?, " +
                                         "record_status_cd = ?, " +
                                         "record_status_time = ?, " +
                                         "role_seq = ?, " +
                                         "status_cd = ?, " +
                                         "status_time = ?, " +
                                         "to_time = ?, " + "type_cd = ?, " +
                                         "type_desc_txt = ?, " +
                                         "user_affiliation_txt = ? " +
                                         SET_PK;
    private static final String INSERT = "INSERT INTO Participation ( " +
                                         "subject_entity_uid, " +
                                         "act_uid, " +
                                         "subject_class_cd = ?, " +
                                         "act_class_cd = ?, " +
                                         "add_reason_cd, " + "add_time, " +
                                         "add_user_id, " + "awareness_cd, " +
                                         "awareness_desc_txt, " + "cd, " +
                                         "duration_amt, " +
                                         "duration_unit_cd, " +
                                         "from_time, " +
                                         "last_chg_reason_cd, " +
                                         "last_chg_time, " +
                                         "last_chg_user_id, " +
                                         "record_status_cd, " +
                                         "record_status_time, " +
                                         "role_seq, " + "status_cd, " +
                                         "status_time, " + "to_time, " +
                                         "type_cd, " + "type_desc_txt, " +
                                         "user_affiliation_txt " +
                                         " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    static final LogUtils logger = new LogUtils(ParticipationDAOImpl.class.getName());

    /**
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E4602AA
    */
    public ParticipationDAOImpl()
                         throws NEDSSDAOSysException, NEDSSSystemException
    {
    }

    /**
    * Connection dbConnection = nu.getTestConnection();
    * override getConnection for testing purposes
    * @return java.sql.Connection
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @roseuid 3C434E46019C
    */

    /*   protected synchronized Connection getConnection() throws NEDSSSystemException
       {
            logger.debug("getting a connection");
            return nu.getConnection();
       }
    */

    /**
    * A method used to insert a participation record
    * @param uid a long value
    * @param obj the participation object to be inserted
    * @return long
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E470017
    */
    public long create(long uid, Object obj)
                throws NEDSSDAOSysException, NEDSSSystemException
    {

    	try{
	        ParticipationDT dt = (ParticipationDT)obj;
	
	        if (dt == null)
	            throw new NEDSSSystemException("Error: try to create null ParticipationDT object.");
	
	        insertParticipation((ParticipationDT)obj);
	
	        return uid;
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  uid:"+uid+" Exception = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("NEDSSDAOSysException  uid:"+uid+" Exception = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
    * A method used to update a participation record
    * @param obj
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E47017F
    */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        ParticipationDT dt = (ParticipationDT)obj;
	        logger.debug(
	                "\ndt.dirty?:" + dt.isItDirty() + "\n\ndt.delete?:" +
	                dt.isItDelete() + "\n\n:dt.isItNew:" + dt.isItNew() +
	                ":dt.entityUID:" + dt.getSubjectEntityUid());
	
	        //Integer partSeq = dt.getParticipationSeq();
	        //if(partSeq == null)
	        //throw new NEDSSSystemException("Error: The value for participation_seq cannot be null.");
	        if (dt == null)
	            throw new NEDSSSystemException("Error: try to store null ParticipationDT object.");
	
	        if (dt.isItNew())
	            insertParticipation(dt);
	        else if (dt.isItDelete())
	            remove(dt);
	        else if (dt.isItDirty())
	            updateParticipation(dt);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    
    public void store(Collection<Object> participationCollection, RootDTInterface rootInterfaceDT)
            throws NEDSSDAOSysException, NEDSSSystemException
            {
		     try {
					Iterator<Object> iterator = participationCollection.iterator();
					while(iterator.hasNext()) {
						ParticipationDT participationDT = (ParticipationDT)iterator.next();
							participationDT.setActUid(rootInterfaceDT.getUid());
						    insertParticipation(participationDT);
						
						logger.debug("\nparticipationDT.dirty?:" + participationDT.isItDirty() + "\n\nparticipationDT.delete?:" +participationDT.isItDelete() + "\n\n:dt.isItNew:" + participationDT.isItNew() +":participationDT.entityUID:" + participationDT.getSubjectEntityUid());
					}
				} catch ( NEDSSSystemException e) {
					logger.fatal("ParticipationDAOImpl.store Exception raised while storing participationCollection Objects", e);
					throw new EJBException();
				}
            }

    /**
    * A method used to retrieve entity participation objects
    * @param uid
    * @return java.util.Collection
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E4702DE
    */
    public Collection<Object> load(long uid)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		return selectParticipation(uid);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  uid "+uid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("NEDSSDAOSysException  uid "+uid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
    * A method used to retrieve act participation
    * @param uid
    * @return java.util.Collection
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E4800AF
    */
    public Collection<Object> loadAct(long actUid)
                       throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		return selectParticipationAct(actUid);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  actUid "+actUid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("NEDSSDAOSysException  actUid "+actUid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * A method used to retrieve a collection of participation records
     * @param subjectUid an entity uid
     * @param typeCd a participation type code
     * @return a collection of participation objects
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(long subjectUid, String typeCd)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		return selectPartByType(subjectUid, typeCd);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  subjectUid "+subjectUid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("NEDSSDAOSysException  subjectUid "+subjectUid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Called when logic requires the loading of a specific participation record based
     * on subjectEntityUid and ActUid.
     * @param uid
     * @param act_uid
     * @return
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(long uid, long act_uid)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		return selectParticipation(uid, act_uid);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  uid "+uid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("NEDSSDAOSysException  uid "+uid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * selecttUid method retrieves uid of a subject(person or patient)
     *
     */
    public Collection<Object> selectUid(Long queryUid, String sqlStatement)
    throws  NEDSSSystemException
    {
    	Connection dbConnection = null;
    	PreparedStatement preparedStmt = null;
    	ResultSet resultSet = null;
    	ArrayList<Object>  uidCollection=new ArrayList<Object> ();
    	Long uid = null;
    	try
    	{
    		dbConnection = getConnection(NEDSSConstants.ODS);
    	}
    	catch(NEDSSSystemException nsex)
    	{
    		logger.fatal("SQLException while obtaining database connection " + nsex);
    		throw new NEDSSSystemException(nsex.toString());
    	}

    	try
    	{
    		preparedStmt = dbConnection.prepareStatement(sqlStatement);
    		preparedStmt.setLong(1, queryUid.longValue());
    		resultSet = preparedStmt.executeQuery();
    		if(resultSet != null)
    		{
    			while (resultSet.next())
    			{
    				uid = new Long(resultSet.getLong(1));
    				logger.debug(" inside ParticipationDAOImpl, selectUid get uid = " + uid.longValue());
    				uidCollection.add(uid);

    			}
    		}
    		return uidCollection;
    	}
    	catch(Exception e)
    	{
    		logger.fatal("Exception  = "+e.getMessage(), e);;
    		throw new NEDSSSystemException("Error: Exception while obtaining database connection " + e.getMessage());
    	}
    	finally
    	{
    		closeResultSet(resultSet);
    		closeStatement(preparedStmt);
    		releaseConnection(dbConnection);
    	}
    }
    
    /**
     * A method used to retrieve participation objects
     * @param subjectUid
     * @param typeCd
     * @param actClassCd
     * @param subClassCd
     * @return
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(long subjectUid, String typeCd, String actClassCd,
                           String subClassCd)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        logger.debug("Inside new method ----");
	
	        return selectPartByTypeActSub(subjectUid, typeCd, actClassCd,
                                      subClassCd);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  subjectUid "+subjectUid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("NEDSSDAOSysException  subjectUid "+subjectUid+" Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
    * A method used to delete a participation
    * @param uid
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E480267
    */
    public void remove(ParticipationDT dt)
                throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		removeParticipation(dt);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
    * @param dt
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E4803C6
    */
    private void insertParticipation(ParticipationDT part)
                              throws NEDSSDAOSysException,
                                     NEDSSSystemException
    {

        /**
       * This method will add one record to the Participation table.  It should perform the
       * necessary DB lock to read the next participation_sequence numbers (part.participation_seq)
       * for the supplied key (subject_entity_uid, act_uid) in the supplied ParticipationDT object.
       * Once the next sequence is generated, the new participation is then inserted.  Both the
       * determining of the sequence and the insertion of the new record are handled by a stored
       * procedure called addParticipation_sp.  On completion of the stored procedure, this method
       * returns the ParticipationDT object that was passed in.  However, it will now have a value for
       * participation_seq.
       */
        logger.debug("About to call getConnection");
        logger.debug("Just got the connection");

        Connection dbConnection = null;
        CallableStatement sProc = null;
        NedssUtils nu = new NedssUtils();

        try
        {

            //*************************************************************************
            //  This code is for accessing a stored procedure.
            // stored procedure call to get the issue related information
            //  logger.error("Connection at start of insert participation is: " + nu.getOpenCursor());
            if (part != null && part.getSubjectEntityUid() != null &&  part.getSubjectEntityUid() != 0 &&
                part.getActUid() != null && part.getTypeCd() != null)
            {
                dbConnection = getConnection();
                logger.debug("About to call stored procedure");

                String sQuery = "{call addParticipation_sp(?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,?, ?,?,?,?,?)}";

                // prepare the stored procedure
                sProc = dbConnection.prepareCall(sQuery);

                // register the output parameters
                // sProc.registerOutParameter(24, java.sql.Types.INTEGER);//participation_seq
                int i = 1;
                sProc.setLong(i++, part.getSubjectEntityUid().longValue()); //1
                logger.debug(
                        "part.getSubjectEntityUid().longValue(): " +
                        part.getSubjectEntityUid().longValue());
                sProc.setLong(i++, part.getActUid().longValue()); //2
                logger.debug(
                        "part.getActUid().longValue()" +
                        part.getActUid().longValue());
                sProc.setString(i++, part.getTypeCd()); //3
                logger.debug("part.getTypeCd() : " + part.getTypeCd());
                sProc.setString(i++, part.getAddReasonCd()); //4
                logger.debug("part.getAddReasonCd()" +
                             part.getAddReasonCd());

                if (part.getAddTime() == null)
                    sProc.setNull(i++, Types.TIMESTAMP); //5
                else
                {
                    sProc.setTimestamp(i++, part.getAddTime());
                    logger.debug(
                            "part.getAddTime()" +
                            part.getAddTime().toString());
                }

                if (part.getAddUserId() == null)
                    sProc.setNull(i++, Types.BIGINT);
                else
                {
                    sProc.setLong(i++, part.getAddUserId().longValue()); //6
                    logger.debug(
                            "part.getAddUserId().longValue()" +
                            part.getAddUserId().longValue());
                }

                sProc.setString(i++, part.getAwarenessCd()); //7
                logger.debug("part.getAwarenessCd()" +
                             part.getAwarenessCd());
                sProc.setString(i++, part.getAwarenessDescTxt()); //8
                logger.debug(
                        "part.getAwarenessDescTxt()" +
                        part.getAwarenessDescTxt());
                sProc.setString(i++, part.getCd()); //9
                logger.debug("part.getCd()" + part.getCd());
                sProc.setString(i++, part.getDurationAmt()); //10
                logger.debug("part.getDurationAmt()" +
                             part.getDurationAmt());
                sProc.setString(i++, part.getDurationUnitCd()); //11
                logger.debug(
                        "part.getDurationUnitCd" + part.getDurationUnitCd());

                if (part.getFromTime() == null)
                    sProc.setNull(i++, Types.TIMESTAMP);
                else
                {
                    sProc.setTimestamp(i++, part.getFromTime()); //12
                    logger.debug(
                            "part.getFromTime()" +
                            part.getFromTime().toString());
                }

                sProc.setString(i++, part.getLastChgReasonCd()); //13
                logger.debug(
                        "part.getLastChgReasonCd()" +
                        part.getLastChgReasonCd());

                if (part.getLastChgTime() == null)
                    sProc.setNull(i++, Types.TIMESTAMP);
                else
                {
                    sProc.setTimestamp(i++, part.getLastChgTime()); //14
                    logger.debug(
                            "part.getLastChgTime()" +
                            part.getLastChgTime().toString());
                }

                if (part.getLastChgUserId() == null)
                    sProc.setNull(i++, Types.BIGINT);
                else //15
                    sProc.setLong(i++, part.getLastChgUserId().longValue());

                sProc.setString(i++, part.getRecordStatusCd()); //16

                if (part.getRecordStatusTime() == null)
                    sProc.setNull(i++, Types.TIMESTAMP);
                else //17
                    sProc.setTimestamp(i++, part.getRecordStatusTime());

                if (part.getRoleSeq() == null)
                    sProc.setNull(i++, Types.SMALLINT);
                else
                {
                    sProc.setInt(i++, part.getRoleSeq().intValue()); //18
                    logger.debug(
                            "part.getRoleSeq().intValue()" +
                            part.getRoleSeq().intValue());
                }

                sProc.setString(i++, part.getStatusCd()); //19

                if (part.getStatusTime() == null)
                    sProc.setNull(i++, Types.TIMESTAMP);
                else //20
                    sProc.setTimestamp(i++, part.getStatusTime());

                if (part.getToTime() == null)
                    sProc.setNull(i++, Types.TIMESTAMP);
                else //21
                    sProc.setTimestamp(i++, part.getToTime());

                //sProc.setString(i++, part.getTypeCd());//21
                sProc.setString(i++, part.getTypeDescTxt()); //22
                sProc.setString(i++, part.getUserAffiliationTxt()); //23
                sProc.setString(i++, part.getSubjectClassCd()); //24
                sProc.setString(i++, part.getActClassCd()); //25

                // execute the stored procedure
                logger.debug("-- ABout to execute the stored procedure");
                logger.info("sProcedure is: " + sProc.toString());
                sProc.execute();
            }
            else
                logger.error("Part of primary key is null");
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while obtaining database connection.\n" +
                                           se.getMessage());
        }
        catch (NEDSSSystemException nse)
        {
        	logger.fatal("NEDSSSystemException  = "+nse.getMessage(), nse);
            throw new NEDSSDAOSysException("Error: NEDSSSystemException while obtaining database connection.\n" +
                                           nse.getMessage());
        }
        catch (Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException("Error: Exception while insert in Participation.\n" +ex.getMessage());
        }
        finally
        {
            closeCallableStatement(sProc);
            releaseConnection(dbConnection);
        }

        //    logger.error("Connection at end of insert participation is: " + nu.getOpenCursor());
        //***************************************************************************
    }

    /**
    * @param dt
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E490196
    */
    private void updateParticipation(ParticipationDT dt)
                              throws NEDSSDAOSysException,
                                     NEDSSSystemException
    {

        int resultCount = 0;
        NedssUtils nu = new NedssUtils();

        //PreparedStatement preparedStmt = getPreparedStatement(UPDATE);
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        //    logger.error("Connection at start of update participation is: " + nu.getOpenCursor());
        try
        {

            if (dt != null && dt.getSubjectEntityUid() != null &&
                dt.getActUid() != null && dt.getTypeCd() != null)
            {
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(UPDATE);

                // int i = setPreparedStatement(preparedStmt, dt);
                int i = 1;
                preparedStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
                preparedStmt.setLong(i++, dt.getActUid().longValue());
                preparedStmt.setString(i++, dt.getSubjectClassCd());
                preparedStmt.setString(i++, dt.getActClassCd());
                preparedStmt.setString(i++, dt.getAddReasonCd());

                if (dt.getAddTime() == null)
                    preparedStmt.setTimestamp(i++,
                                              new Timestamp(new java.util.Date().getTime()));
                else
                    preparedStmt.setTimestamp(i++, dt.getAddTime());

                if (dt.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                    preparedStmt.setLong(i++, dt.getAddUserId().longValue());

                preparedStmt.setString(i++, dt.getAwarenessCd());
                preparedStmt.setString(i++, dt.getAwarenessDescTxt());
                preparedStmt.setString(i++, dt.getCd());
                preparedStmt.setString(i++, dt.getDurationAmt());
                preparedStmt.setString(i++, dt.getDurationUnitCd());

                if (dt.getFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, dt.getFromTime());

                preparedStmt.setString(i++, dt.getLastChgReasonCd());

                if (dt.getLastChgTime() == null)
                    preparedStmt.setTimestamp(i++,
                                              new Timestamp(new java.util.Date().getTime()));
                else
                    preparedStmt.setTimestamp(i++, dt.getLastChgTime());

                if (dt.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                    preparedStmt.setLong(i++,
                                         dt.getLastChgUserId().longValue());

                preparedStmt.setString(i++, dt.getRecordStatusCd());

                if (dt.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());

                if (dt.getRoleSeq() == null)
                    preparedStmt.setNull(i++, Types.SMALLINT);
                else
                    preparedStmt.setInt(i++, dt.getRoleSeq().intValue());

                preparedStmt.setString(i++, dt.getStatusCd());
                preparedStmt.setTimestamp(i++, dt.getStatusTime());

                if (dt.getToTime() == null)
                    preparedStmt.setTimestamp(i++,
                                              new Timestamp(new java.util.Date().getTime()));
                else
                    preparedStmt.setTimestamp(i++, dt.getToTime());

                preparedStmt.setString(i++, dt.getTypeCd());
                preparedStmt.setString(i++, dt.getTypeDescTxt());
                preparedStmt.setString(i++, dt.getUserAffiliationTxt());

                //primary keys values for selection
                preparedStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
                preparedStmt.setLong(i++, dt.getActUid().longValue());
                preparedStmt.setString(i++, dt.getTypeCd());
                resultCount = preparedStmt.executeUpdate();

                if (resultCount != 1)
                {
                    throw new NEDSSSystemException("Error: none or more than one entity updated at a time, resultCount = " +
                                                   resultCount);
                }
                else
                {
                    dt.setItNew(false);
                    dt.setItDirty(false);
                }
            }
        }
        catch (SQLException se)
        {
        	logger.fatal("Exception  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while updating\n" +
                                           se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);

            //   logger.error("Connection at end of update participation is: " + nu.getOpenCursor());
        }
    }

    /**
    * @param uid
    * @return java.util.Collection
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E49034F
    */
    private Collection<Object> selectParticipation(long uid)
                                    throws NEDSSDAOSysException,
                                           NEDSSSystemException
    {

        //PreparedStatement preparedStmt = getPreparedStatement(SELECT_BY_UID);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ParticipationDT dt = new ParticipationDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_UID);
            preparedStmt.setLong(1, uid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ParticipationDT newdt = new ParticipationDT();
                newdt = (ParticipationDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
        	logger.fatal("Exception  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
        	logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException("Error in result set handling while populate ParticipationDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
     * Loads a partcipation record based on the actUid and subjectEntityUid.
     * @param uid
     * @param act_uid
     * @return
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private Collection<Object> selectParticipation(long uid, long act_uid)
                                    throws NEDSSDAOSysException,
                                           NEDSSSystemException
    {

        //PreparedStatement preparedStmt = getPreparedStatement(SELECT_BY_UID);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ParticipationDT dt = new ParticipationDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_SUBJECTUID_ACTUID);
            preparedStmt.setLong(1, uid);
            preparedStmt.setLong(2, act_uid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ParticipationDT newdt = new ParticipationDT();
                newdt = (ParticipationDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
        	logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException("Error in result set handling while populate ParticipationDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    /**
    * @param uid
    * @return java.util.Collection
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E4A0120
    */
    private Collection<Object> selectParticipationAct(long uid)
                                       throws NEDSSDAOSysException,
                                              NEDSSSystemException
    {

        //PreparedStatement preparedStmt = getPreparedStatement(SELECT_BY_ACT_UID);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ParticipationDT dt = new ParticipationDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_ACT_UID);
            preparedStmt.setLong(1, uid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ParticipationDT newdt = new ParticipationDT();
                newdt = (ParticipationDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
        	logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException("Error in result set handling while populate ParticipationDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
    * @param uid
    * @param typeCd
    * @param actClassCd
    * @param subClassCd
    * @return java.util.Collection
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid
    */
    private Collection<Object> selectPartByTypeActSub(long subjectUid, String typeCd,
                                              String actClassCd,
                                              String subClassCd)
                                       throws NEDSSDAOSysException,
                                              NEDSSSystemException
    {

        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ParticipationDT dt = new ParticipationDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(
                                   SELECT_BY_TYPE_ACT_SUB_CD);
            preparedStmt.setLong(1, subjectUid);
            preparedStmt.setString(2, typeCd);
            preparedStmt.setString(3, actClassCd);
            preparedStmt.setString(4, subClassCd);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            logger.debug("after result set ");

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ParticipationDT newdt = new ParticipationDT();
                newdt = (ParticipationDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
        	logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException("Error in result set handling while populate ParticipationDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private Collection<Object> selectPartByType(long subjectUid, String typeCd)
                                 throws NEDSSDAOSysException,
                                        NEDSSSystemException
    {

        //PreparedStatement preparedStmt = getPreparedStatement(SELECT_BY_TYPE);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ParticipationDT dt = new ParticipationDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_TYPE);
            preparedStmt.setLong(1, subjectUid);
            preparedStmt.setString(2, typeCd);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ParticipationDT newdt = new ParticipationDT();
                newdt = (ParticipationDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
        	logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException("Error in result set handling while populate ParticipationDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
    * @param uid
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E4A02D9
    */
    private void removeParticipation(ParticipationDT dt)
                              throws NEDSSDAOSysException,
                                     NEDSSSystemException
    {

        //PreparedStatement preparedStmt = getPreparedStatement(DELETE_BY_UID);
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            logger.debug(
                    "$$$$###Delete participation being called :" +
                    DELETE_BY_PK);
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_BY_PK);
            preparedStmt.setLong(1, dt.getSubjectEntityUid().longValue());
            preparedStmt.setLong(2, dt.getActUid().longValue());
            preparedStmt.setString(3, dt.getTypeCd());
            preparedStmt.executeUpdate();
        }
        catch (SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while deleting\n" +
                                           se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

}