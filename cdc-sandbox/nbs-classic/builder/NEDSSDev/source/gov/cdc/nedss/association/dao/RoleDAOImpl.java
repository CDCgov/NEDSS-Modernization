//Source file: C:\\development\\source\\gov\\cdc\\nedss\\dao\\RoleDAOImpl.java

package gov.cdc.nedss.association.dao;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
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
import java.util.Hashtable;
import java.util.Iterator;


public class RoleDAOImpl extends BMPBase
{
  //For logging
  static final LogUtils logger = new LogUtils(RoleDAOImpl.class.getName());

 
  private static final String SET_PK = "where subject_entity_uid = ? and cd = ? and role_seq = ?";
  private static final String SET_UID = "where subject_entity_uid = ? and status_cd='A'";
  private static final String SET_SCOPING_UID =  "where scoping_entity_uid = ?";
  private static final String LOAD_SCOPING_CLAUSE = "where scoping_entity_uid = ? and scoping_class_cd = 'PAT' and subject_class_cd in ('PROV', 'CON')";

  private static final String SELECT
      = "SELECT "
      + "subject_entity_uid \"SubjectEntityUid\", "

      + "cd \"Cd\", "
      + "role_seq \"RoleSeq\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "subject_class_cd \"SubjectClassCd\", "
      + "scoping_class_cd \"ScopingClassCd\", "

      + "cd_desc_txt \"CdDescTxt\", "
      + "effective_duration_amt \"EffectiveDurationAmt\", "
      + "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
      + "effective_from_time \"EffectiveFromTime\", "
      + "effective_to_time \"EffectiveToTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "scoping_entity_uid \"ScopingEntityUid\", "
      + "scoping_role_cd \"ScopingRoleCd\", "
      + "scoping_role_seq \"ScopingRoleSeq\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\" "
      + "from Role with (NOLOCK) ";


  private static final String SELECT_BY_PK = "SELECT max(role_seq) from ROLE with (NOLOCK) where subject_entity_uid = ? and cd = ?";
  private static final String SELECT_BY_SUBJECT_SCOPING_CD = "SELECT max(role_seq) from ROLE with (NOLOCK)  where subject_entity_uid = ? and cd = ? and scoping_entity_uid=?";
   private static final String SELECT_BY_UID = SELECT + SET_UID;
   private static final String SELECT_BY_SCOPING_UID = SELECT + SET_SCOPING_UID;
   private static final String SELECT_BY_PATIENT_SCOPED_ENTITIES = SELECT + LOAD_SCOPING_CLAUSE;
   private static final String DELETE_BY_PK = "DELETE from Role " + SET_PK;
   private static final String DELETE_BY_UID = "DELETE from Role " + SET_UID + " and scoping_entity_uid is null " ;
  // for time beign, till we make decision about deleting roles.
 // private static final String DELETE_BY_UID = "UPDATE Role set status_cd='I' " + SET_UID + " and scoping_entity_uid is null " ;
   private static final String UPDATE = "UPDATE Role SET " + "subject_entity_uid = ?, " + "cd = ?, " + "role_seq = ?, " + "add_reason_cd = ?, " + "add_time = ?, " + "add_user_id = ?, " + "subject_class_cd = ?, " + "scoping_class_cd = ?, " + "cd_desc_txt = ?, " + "effective_duration_amt = ?, " + "effective_duration_unit_cd = ?, " + "effective_from_time = ?, " + "effective_to_time = ?, " + "last_chg_reason_cd = ?, " + "last_chg_time = ?, " + "last_chg_user_id = ?, " + "record_status_cd = ?, " + "record_status_time = ?, " + "scoping_entity_uid = ?, " + "scoping_role_cd = ?, " + "scoping_role_seq = ?, " + "status_cd = ?, " + "status_time = ?, " + "user_affiliation_txt = ? " + SET_PK;
   private static final String INSERT = "INSERT INTO Role ( " + "subject_entity_uid, " + "cd, " + "role_seq, " + "add_reason_cd, " + "add_time, " + "add_user_id, " + "subject_class_cd, " + "scoping_class_cd, " + "cd_desc_txt, " + "effective_duration_amt, " + "effective_duration_unit_cd, " + "effective_from_time, " + "effective_to_time, " + "last_chg_reason_cd, " + "last_chg_time, " + "last_chg_user_id, " + "record_status_cd, " + "record_status_time, " + "scoping_entity_uid, " + "scoping_role_cd, " + "scoping_role_seq, " + "status_cd, " + "status_time, " + "user_affiliation_txt" + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
   private static final String SEARCH_BY_PK = "SELECT count(*) from Role with (NOLOCK) " + SET_PK;



/*
   //overide getConnection only for testing
   NedssUtils nu = new NedssUtils ();
   Connection dbConnection = nu.getTestConnection();

   protected synchronized Connection getConnection() throws NEDSSSystemException
    {
        //logger.debug("getting a connection");
        return nu.getTestConnection();
    }
*/
   /**
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E690229
    */
   public RoleDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException
   {

   }

   /**
    * Connection dbConnection = nu.getTestConnection();
    * override getConnection for testing purposes
    * @return java.sql.Connection
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @roseuid 3C434E69011A
    */
/*   protected synchronized Connection getConnection() throws NEDSSSystemException
   {
        return nu.getConnection();
   }
*/
   /**
    * This method accepts a Collection<Object>  of RoleDT's.  If there is only one RoleDT in
    * the collection the addRole method should be called, if there are two RoleDT's
    * then the addRoleRelationship should be called - any more than two RoleDT's
    * should generate an exception.
    * @param aUID
    * @param coll
    * @return long
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C46E92C01F4
    */
   public long create(long aUID, Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
		     if(coll == null)
		    	 logger.error("RoleDAOImpl,in create method, roleCollection  is null.");
		
		     if(coll.size() == 1)
		     {
				Iterator<Object> it = coll.iterator();
			        RoleDT role1 = (RoleDT)it.next();
				this.addRole(role1);
				return aUID;
		     }
		     else if(coll.size() == 2)
		     {
		        Iterator<Object> it = coll.iterator();
		        RoleDT role1 = (RoleDT)it.next();
		        RoleDT role2 = (RoleDT)it.next();
		        this.addRoleRelationship(role1, role2);
		        return aUID;
		     }
		     else
		     {
		    	 logger.error("RoleDAOImpl, in create method, roleCollection  should only contains 1 or 2 RoleDTs");
		         return -1;
		     }
	   }catch(NEDSSDAOSysException ex){
		   logger.fatal("aUID: "+aUID+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
		   throw new NEDSSDAOSysException(ex.toString());
	   }catch(Exception ex){
		   logger.fatal("aUID: "+aUID+" Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
   }

   /**
    * @param obj
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E6A00FE
    */
   public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
	        RoleDT dt = (RoleDT)obj;
	        logger.debug("\nfor ROle dt.dirty?:"+ dt.isItDirty()+ "\n\ndt.delete?:"+ dt.isItDelete() + "\n\n:dt.isItNew:"+dt.isItNew()
	                + ":dt.entityUID:"+dt.getSubjectEntityUid());
	       if(dt == null)
	           logger.error("Error: try to store null RoleDT object.");
	        if(dt.isItNew())
	          insertRole(dt);
	        else if(dt.isItDelete())
	          remove(dt);
	        else if(dt.isItDirty())
	          updateRole(dt);
	   }catch(NEDSSDAOSysException ex){
		   logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		   throw new NEDSSDAOSysException(ex.toString());
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }

   }

   /**
    * @param subjectUid
    * @return java.util.Collection<Object>
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E6A025C
    */
   public Collection<Object> load(long subjectUid) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
		   return selectRole(subjectUid, SELECT_BY_UID);
	   }catch(NEDSSDAOSysException ex){
		   logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		   throw new NEDSSDAOSysException(ex.toString());
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
   }

   //Code to fix ELR defect where the role sequence was increasing with eache new ELRS
   public int loadCountBySubjectScpingCdComb(RoleDT roleDT) throws NEDSSDAOSysException, NEDSSSystemException
   {
        //PreparedStatement preparedStmt = getPreparedStatement(sqlStatement);
        ResultSet resultSetCode = null;
        RoleDT dt = new RoleDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;
        try
        {
        	dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_SUBJECT_SCOPING_CD);


            preparedStmt.setLong(1, roleDT.getSubjectEntityUid()); 
            preparedStmt.setString(2, roleDT.getCd());
            preparedStmt.setLong(3, roleDT.getScopingEntityUid());
            
            resultSetCode = preparedStmt.executeQuery();
            int count = 0;
            if (resultSetCode.next()){
            	count=resultSetCode.getInt(1);
            }
            
            return count;
        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while selecting \n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        finally
        {
            closeResultSet(resultSetCode);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
   }

   public Long loadCountBySubjectCdComb(RoleDT roleDT) throws NEDSSDAOSysException, NEDSSSystemException
   {
        //PreparedStatement preparedStmt = getPreparedStatement(sqlStatement);
        ResultSet resultSetCode = null;
        RoleDT dt = new RoleDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;
        try
        {
        	dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_PK);


            preparedStmt.setLong(1, roleDT.getSubjectEntityUid()); 
            preparedStmt.setString(2, roleDT.getCd());
            
            resultSetCode = preparedStmt.executeQuery();
            long count = 0;
            if (resultSetCode.next()){
            	count=resultSetCode.getLong(1);
            }
            
            return count;
        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while selecting \n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        finally
        {
            closeResultSet(resultSetCode);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
   }
   /**
    * This method will return all roles where scoping entity uid is that of
    * the passed in object.  It may be records where the subject entity uid is
    * that of a Material, Collector, CopyToProvider, or EmergencyContact etc.
    * @param scopingUid
    * @return
    * @throws NEDSSDAOSysException
    * @throws NEDSSSystemException
    */
   public Collection<Object> loadScoping(long scopingUid) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
		   return selectRole(scopingUid, SELECT_BY_SCOPING_UID);
	   }catch(NEDSSDAOSysException ex){
		   logger.fatal("scopingUid"+scopingUid+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
		   throw new NEDSSDAOSysException(ex.toString());
	   }catch(Exception ex){
		   logger.fatal("scopingUid"+scopingUid+" Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
   }

   /**
    * This will return a collection of roles where the subject_entity_uid
    * represents that of a Collector, CopyToProvider, or EmergencyContact.
    * @param scopingUid
    * @return
    */
   public Collection<Object> loadEntitiesScopedToPatient(long scopingUid) {
	   ArrayList<Object>  resetList = new ArrayList<Object> ();
	   try{
		   resetList = (ArrayList<Object>)selectRole(scopingUid, SELECT_BY_PATIENT_SCOPED_ENTITIES);
	   }catch(Exception ex){
		   logger.fatal("scopingUid"+scopingUid+" Exception = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
	   return resetList;
   }

   /**
    * @param uid
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E6B002D
    */
   public void remove(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
	        logger.debug("!!!!!!!!!!!remove being called!!!!!!!!!!!");
	        removeRole(obj);
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
    * @roseuid 3C434E6B018B
    */
   private void insertRole(RoleDT dt) throws NEDSSDAOSysException, NEDSSSystemException
   {
        int resultCount = 0;
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;
        int ifKeyExistis=loadIfKeyExist(dt);
        
        if(ifKeyExistis>0) {
        	logger.debug("Key already exists: Hence insert skipped!!!");
        	dt.toString();
        	return;
        }
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(INSERT);
            int i = 1;
	        if (dt.getSubjectEntityUid()== null)
	          preparedStmt.setNull(i++, Types.BIGINT);
	        else
	          preparedStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
	        preparedStmt.setString(i++, dt.getCd());
	        if (dt.getRoleSeq() == null)
	          preparedStmt.setNull(i++, Types.INTEGER);
	        else
	          preparedStmt.setInt(i++, dt.getRoleSeq().intValue());
	        preparedStmt.setString(i++, dt.getAddReasonCd());
	        if( dt.getAddTime()==null)
	            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
	        else
	            preparedStmt.setTimestamp(i++, dt.getAddTime() );
	        if( dt.getAddUserId()==null)
	            preparedStmt.setNull(i++, Types.BIGINT );
	        else
	            preparedStmt.setLong(i++, dt.getAddUserId().longValue() );
	        preparedStmt.setString(i++, dt.getSubjectClassCd());
	        preparedStmt.setString(i++, dt.getScopingClassCd());
	
	        preparedStmt.setString(i++, dt.getCdDescTxt());
	        preparedStmt.setString(i++, dt.getEffectiveDurationAmt());
	        preparedStmt.setString(i++, dt.getEffectiveDurationUnitCd());
	        if( dt.getEffectiveFromTime()==null)
	            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
	        else
	            preparedStmt.setTimestamp(i++, dt.getEffectiveFromTime() );
	        if( dt.getEffectiveToTime()==null)
	            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
	        else
	            preparedStmt.setTimestamp(i++, dt.getEffectiveToTime() );
	        preparedStmt.setString(i++, dt.getLastChgReasonCd());
	        if( dt.getLastChgTime()==null)
	            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
	        else
	            preparedStmt.setTimestamp(i++, dt.getLastChgTime() );
	        if( dt.getLastChgUserId()==null)
	            preparedStmt.setNull(i++, Types.BIGINT );
	        else
	            preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() );
	        preparedStmt.setString(i++, dt.getRecordStatusCd());
	        preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());
	        //logger.debug("Setting scopingEntityUID on prepared Statement");
	        if(dt.getScopingEntityUid()==null)
	          preparedStmt.setNull(i++, Types.BIGINT);
	        else
	          preparedStmt.setLong(i++, dt.getScopingEntityUid().longValue());
	        preparedStmt.setString(i++, dt.getScopingRoleCd());
	        if( dt.getScopingRoleSeq()==null)
	            preparedStmt.setNull(i++, Types.SMALLINT );
	        else
	            preparedStmt.setInt(i++, dt.getScopingRoleSeq().intValue() );
	        preparedStmt.setString(i++, dt.getStatusCd());
	        preparedStmt.setTimestamp(i++, dt.getStatusTime());
	        preparedStmt.setString(i++, dt.getUserAffiliationTxt());
	
	        resultCount = preparedStmt.executeUpdate();
            if ( resultCount != 1 )
            {
                logger.error ("Error: none or more than one role inserted at a time, resultCount = " + resultCount);
            }
            else
            {
                dt.setItNew(false);
                dt.setItDirty(false);
            }
        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while inserting\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException("Table Name : Role  "+se.getMessage(), se);
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
   }

   /**
    * @param dt
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E6B0344
    */
   private void updateRole(RoleDT dt) throws NEDSSDAOSysException, NEDSSSystemException
   {
       int resultCount = 0;
       PreparedStatement preparedStmt = null;
       Connection dbConnection = null;
       try
        {
          dbConnection = getConnection();
          preparedStmt = dbConnection.prepareStatement(UPDATE);
          int i = 1;
        if (dt.getSubjectEntityUid() == null)
          preparedStmt.setNull(i++,Types.BIGINT);
        else
          preparedStmt.setLong(i++, dt.getSubjectEntityUid().longValue());

        preparedStmt.setString(i++, dt.getCd());
        if (dt.getRoleSeq() == null)
           preparedStmt.setNull(i++,Types.INTEGER);
        else
           preparedStmt.setInt(i++, dt.getRoleSeq().intValue());
        preparedStmt.setString(i++, dt.getAddReasonCd());
        if( dt.getAddTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getAddTime() );
        if( dt.getAddUserId()==null)
            preparedStmt.setNull(i++, Types.BIGINT );
        else
            preparedStmt.setLong(i++, dt.getAddUserId().longValue() );
        preparedStmt.setString(i++, dt.getSubjectClassCd());
        preparedStmt.setString(i++, dt.getScopingClassCd());

        preparedStmt.setString(i++, dt.getCdDescTxt());
        preparedStmt.setString(i++, dt.getEffectiveDurationAmt());
        preparedStmt.setString(i++, dt.getEffectiveDurationUnitCd());
        if( dt.getEffectiveFromTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getEffectiveFromTime() );
        if( dt.getEffectiveToTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getEffectiveToTime() );
        preparedStmt.setString(i++, dt.getLastChgReasonCd());
        if( dt.getLastChgTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getLastChgTime() );
        if( dt.getLastChgUserId()==null)
            preparedStmt.setNull(i++, Types.BIGINT );
        else
            preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() );
        preparedStmt.setString(i++, dt.getRecordStatusCd());
        preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());
        //logger.debug("Setting scopingEntityUID on prepared Statement");
        if(dt.getScopingEntityUid()==null)
          preparedStmt.setNull(i++, Types.BIGINT);
        else
          preparedStmt.setLong(i++, dt.getScopingEntityUid().longValue());
          //logger.debug("Set scopingEntityUID on prepared Statement");
        preparedStmt.setString(i++, dt.getScopingRoleCd());
        if( dt.getScopingRoleSeq()==null)
            preparedStmt.setNull(i++, Types.SMALLINT );
        else
            preparedStmt.setInt(i++, dt.getScopingRoleSeq().intValue() );
        preparedStmt.setString(i++, dt.getStatusCd());
        preparedStmt.setTimestamp(i++, dt.getStatusTime());
        preparedStmt.setString(i++, dt.getUserAffiliationTxt());

            preparedStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
            preparedStmt.setString(i++, dt.getCd());
            preparedStmt.setInt(i++, dt.getRoleSeq().intValue());
            resultCount = preparedStmt.executeUpdate();
            if ( resultCount != 1 )
            {
                logger.error ("Error: none or more than one role updated at a time, resultCount = " + resultCount);
            }
            else
            {
                dt.setItNew(false);
                dt.setItDirty(false);
            }
        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while updating\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException("Table Name : Role  "+se.getMessage(), se);
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
   }

   /**
    * @param uid, sqlStatement
    * @return java.util.Collection<Object>
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434E6C0115
    */
   private Collection<Object> selectRole(long uid, String sqlStatement) throws NEDSSDAOSysException, NEDSSSystemException
   {
        //PreparedStatement preparedStmt = getPreparedStatement(sqlStatement);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        RoleDT dt = new RoleDT();
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;
        //preparedStmt = getPreparedStatement(INSERT);
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(sqlStatement);

            preparedStmt.setLong(1, uid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dt.getClass(), retval);
            for(Iterator<Object> anIterator = retval.iterator(); anIterator.hasNext(); )
            {
                RoleDT newdt = (RoleDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }
            return resetList;
        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while selecting \n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while populate RoleDTs."+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException(rsuex.toString());
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
    * @roseuid 3C434E6C02D7
    */
   private void removeRole(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
        //PreparedStatement preparedStmt = getPreparedStatement(DELETE_BY_UID);
        logger.debug("@@@@@@@@@@@RemoveRole Being called@@@@@@@@@! and SQL is :" + DELETE_BY_PK);
        RoleDT dt = (RoleDT)obj;
        logger.debug("dt.getSubjectEntityUid():" + dt.getSubjectEntityUid().longValue()+ ":dt.getCd():"+ dt.getCd()+": dt.getRoleSeq().shortValue():"+  dt.getRoleSeq().shortValue());
        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_BY_PK);
            preparedStmt.setLong(1, dt.getSubjectEntityUid().longValue());
            preparedStmt.setString(2, dt.getCd());
            preparedStmt.setInt(3, dt.getRoleSeq().intValue());
            preparedStmt.executeUpdate();
        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while deleting\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
   }


   /**
    * @param role1
    * @param role2
    * @return java.util.Collection<Object>
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @J2EE_METHOD  --  addRoleRelationship
    * This method will add two records to the Role table.  It should perform the
    * necessary DB lock to read the next sequence numbers (Role.role_seq) for the
    * supplied key (subject_entity_uid, class_cd) in two supplied RoleDTobjects.  The
    * next sequence for each should be stored in the respective record's
    * Role.role_seq column and the corresponding sequence should be stored in the
    * Role.scoping_role_seq column.  Returned is a collection of the two roles
    * populated
    * with the role_seq and scoping_role_seq values, in the order that they were
    * supplied to
    * this method
    * @roseuid 3C434E6D02ED
    */
   public Collection<Object> addRoleRelationship(RoleDT role1, RoleDT role2) throws NEDSSDAOSysException, NEDSSSystemException
   {
        Connection dbConnection  = null;

        Hashtable<Object,Object> htIssue = new Hashtable<Object,Object>();
        PreparedStatement preparedStmt = null;
        ArrayList<Object>  roles = new ArrayList<Object> ();
        CallableStatement sProc= null;
        if(role1.getScopingEntityUid().longValue()!=role2.getSubjectEntityUid().longValue()||role1.getSubjectEntityUid().longValue()!=role2.getScopingEntityUid().longValue())
        {
          logger.error ("Error: Roles are not scoping to each other correctly");
        }
   try
    {
    //*************************************************************************
    //  This code is for accessing a stored procedure.
      // stored procedure call to get the issue related information
      //logger.debug("About to call stored procedure");
      dbConnection = getConnection();
      String sQuery  = "{call addRoleRelationship_sp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

      // prepare the stored procedure
       sProc = dbConnection.prepareCall(sQuery);

      // register the output parameters
      sProc.registerOutParameter(3, java.sql.Types.INTEGER);//role1 role_seq
      sProc.registerOutParameter(19, java.sql.Types.INTEGER);//role1 scoping_role_seq
      sProc.registerOutParameter(27, java.sql.Types.INTEGER);//role2 role_seq
      sProc.registerOutParameter(43, java.sql.Types.INTEGER);//role2 scoping_role_seq
      //int i = 1;

      sProc.setLong(1,role1.getSubjectEntityUid().longValue());  //1
      sProc.setString(2,role1.getCd());
      sProc.setString(4, role1.getAddReasonCd());
      sProc.setTimestamp(5, role1.getAddTime());
      sProc.setLong(6, role1.getAddUserId().longValue());
      //sProc.setString(7, role1.getCd());
      sProc.setString(7, role1.getCdDescTxt());
      sProc.setString(8, role1.getEffectiveDurationAmt());
      sProc.setString(9, role1.getEffectiveDurationUnitCd());
      sProc.setTimestamp(10, role1.getEffectiveFromTime());
      sProc.setTimestamp(11, role1.getEffectiveToTime());
      sProc.setString(12, role1.getLastChgReasonCd());
      sProc.setTimestamp(13, role1.getLastChgTime());
      sProc.setLong(14, role1.getLastChgUserId().longValue());
      sProc.setString(15, role1.getRecordStatusCd());
      sProc.setTimestamp(16, role1.getRecordStatusTime());
      sProc.setLong(17, role1.getScopingEntityUid().longValue());
      sProc.setString(18, role1.getScopingRoleCd());
      sProc.setString(20, role1.getStatusCd());
      sProc.setTimestamp(21, role1.getStatusTime());
      sProc.setString(22, role1.getUserAffiliationTxt());
      sProc.setString(23, role1.getSubjectClassCd());
      sProc.setString(24, role1.getScopingClassCd());//24

      sProc.setLong(25,role2.getSubjectEntityUid().longValue()); //25
      sProc.setString(26,role2.getCd());
      sProc.setString(28, role2.getAddReasonCd());
      sProc.setTimestamp(29, role2.getAddTime());
      sProc.setLong(30, role2.getAddUserId().longValue());
      //sProc.setString(30, role2.getCd());
      sProc.setString(31, role2.getCdDescTxt());
      sProc.setString(32, role2.getEffectiveDurationAmt());
      sProc.setString(33, role2.getEffectiveDurationUnitCd());
      sProc.setTimestamp(34, role2.getEffectiveFromTime());
      sProc.setTimestamp(35, role2.getEffectiveToTime());
      sProc.setString(36,role2.getLastChgReasonCd());
      sProc.setTimestamp(37, role2.getLastChgTime());
      sProc.setLong(38, role2.getLastChgUserId().longValue());
      sProc.setString(39, role2.getRecordStatusCd());
      sProc.setTimestamp(40, role2.getRecordStatusTime());
      sProc.setLong(41, role2.getScopingEntityUid().longValue());
      sProc.setString(42, role2.getScopingRoleCd());
      sProc.setString(44, role2.getStatusCd());
      sProc.setTimestamp(45, role2.getStatusTime());
      sProc.setString(46, role2.getUserAffiliationTxt()); //46
      sProc.setString(47, role2.getSubjectClassCd());
      sProc.setString(48, role2.getScopingClassCd());//48
      // execute the stored procedure
      //logger.debug("ABout to execute the stored procedure");

      sProc.execute();

      // get the output parameters
      int role_Sequence1 = sProc.getInt(3);
      //logger.debug("Value of role_seq1 in DAO is: " + role_Sequence1);
      Long roleSequence1 = new Long(role_Sequence1);
      role1.setRoleSeq(roleSequence1);
      int scoping_sequence1 = sProc.getInt(19);
      //logger.debug("Value of scoping_role_seq1 in DAO is: " + scoping_sequence1);
      Integer sRoleSequence1 = new Integer(scoping_sequence1);
      role1.setScopingRoleSeq(sRoleSequence1);
      int role_Sequence2 = sProc.getInt(27);
      //logger.debug("Value of role_seq2 in DAO is: " + role_Sequence2);
      Long roleSequence2 = new Long(role_Sequence2);
      role2.setRoleSeq(roleSequence2);
      int scoping_sequence2 = sProc.getInt(43);
      //logger.debug("Value of scoping_seq2 in DAO is: " + scoping_sequence2);
      Integer sRoleSequence2 = new Integer(scoping_sequence2);
      role2.setScopingRoleSeq(sRoleSequence2);

      roles.add(role1);
      roles.add(role2);


      return roles;
      }

        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while obtaining database connection.\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException(se.getMessage());
        }
        catch(NEDSSSystemException nse)
        {
            logger.fatal("Error: NEDSSSystemException while obtaining database connection.\n"+nse.getMessage() , nse);
            throw new NEDSSDAOSysException(nse.getMessage());
        }

        finally
        {

            closeCallableStatement(sProc);
            releaseConnection(dbConnection);
        }
   }

   //Refactor from entity root DAOs
   //set collection of RoleDTs and return them with sequence values assigned- Wade Steele
   public Collection<Object> setRoleDTCollection(Collection<Object> roleDTs)
      throws NEDSSSystemException, NEDSSSystemException
  {
      ArrayList<Object>  returnRoles = new ArrayList<Object> ();
      ArrayList<Object>  rolesDTArray = (ArrayList<Object> )roleDTs;
      logger.debug("Size of array after cast is: " + rolesDTArray.size());
      try
      {
          Iterator<Object> iter = rolesDTArray.iterator();
          while(iter.hasNext())
          {
              RoleDT roleDT1 = (RoleDT)iter.next();
              int i = rolesDTArray.indexOf(roleDT1);
              logger.debug("Index is: " + i);
              Long scopingEntUID = roleDT1.getScopingEntityUid();
              if(roleDT1.isItDirty()==true)
              {
                  if(scopingEntUID==null)
                  {
                      logger.debug("This is a single role to add");
                      RoleDT newRoleDT1 = addRole(roleDT1);
                      returnRoles.add(newRoleDT1);
                      newRoleDT1.setItDirty(false);
                      rolesDTArray.set(i, newRoleDT1);

                  }
                  else
                  {
                      logger.debug("This role has a scoping role");
                      Long role_seq = roleDT1.getRoleSeq();
                      Long subjEntUID = roleDT1.getSubjectEntityUid();
                      Integer scopRoleSeq = roleDT1.getScopingRoleSeq();
                      String cd = roleDT1.getCd();
                      String scopingRoleCd = roleDT1.getScopingRoleCd();
                      Iterator<Object> iter2 = rolesDTArray.iterator();

                      while(iter2.hasNext())
                      {
                          logger.debug("Looping to find the scoping");
                          RoleDT roleDT2 = (RoleDT)iter2.next();
                          int index = rolesDTArray.indexOf(roleDT2);
                          Long scopingEntUID2 = roleDT2.getScopingEntityUid();
                          Integer scopingRoleSeq2 = roleDT2.getScopingRoleSeq();
                          String scopingRoleCd2 = roleDT2.getScopingRoleCd();

                          if(scopingEntUID.longValue()==roleDT2.getSubjectEntityUid().longValue() &&
                             subjEntUID.longValue()==scopingEntUID2.longValue() &&
                             role_seq.intValue() ==scopingRoleSeq2.intValue() &&
                             scopRoleSeq.intValue() ==roleDT2.getRoleSeq().intValue() &&
                             scopingRoleCd == scopingRoleCd2 &&
                             cd == roleDT2.getCd() &&
                             roleDT2.isItDirty()!=false)
                          {
                              logger.debug("This is the scoping role");
                              ArrayList<Object>  newRoles = (ArrayList<Object> )addRoleRelationship(roleDT1, roleDT2);
                              RoleDT newRoleDT1 = (RoleDT)newRoles.get(0);
                              RoleDT newRoleDT2 = (RoleDT)newRoles.get(1);
                              newRoleDT1.setItDirty(false);
                              newRoleDT2.setItDirty(false);
                              rolesDTArray.set(i, newRoleDT1);
                              rolesDTArray.set(index, newRoleDT2);
                              returnRoles.add(newRoleDT1);
                              returnRoles.add(newRoleDT2);
                          }
                      }
                  }
                }
            }
            logger.debug("Size of the collection returned is: " + returnRoles.size());
            return returnRoles;


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to set roles"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to set roles"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
   /**
    * @param role1
    * @return RoleDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @J2EE_METHOD  --  addRole
    * This method will add one record to the Role table.  It should perform the
    * necessary DB lock to read the next role sequence number. Returned is the roleDT
    * that
    * was passed in, but it now has a value for role_seq.
    * @roseuid 3C434E6E0171
    */
   public RoleDT addRole(RoleDT role1) throws NEDSSDAOSysException, NEDSSSystemException
   {
        Connection dbConnection  = null;
        Hashtable<Object,Object> htIssue = new Hashtable<Object,Object>();
       // PreparedStatement preparedStmt = null;
        CallableStatement sProc = null;

try
    {
    //*************************************************************************
    //  This code is for accessing a stored procedure.
      // stored procedure call to get the issue related information
      //logger.debug("About to call stored procedure");
      logger.debug("subject entity uid is: " +role1.getSubjectEntityUid() );
      logger.debug("Code is: " + role1.getCd());
      dbConnection  =  getConnection();
      String sQuery  = "{call addRoleRelationship_sp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

      // prepare the stored procedure
      sProc = dbConnection.prepareCall(sQuery);

      // register the output parameters
      sProc.registerOutParameter(3, java.sql.Types.INTEGER);//role1 role_seq
      sProc.registerOutParameter(19, java.sql.Types.INTEGER);//role1 scoping_role_seq
      sProc.registerOutParameter(27, java.sql.Types.INTEGER);//role2 role_seq
      sProc.registerOutParameter(43, java.sql.Types.INTEGER);//role2 scoping_role_seq
      //int i = 1;

      sProc.setLong(1,role1.getSubjectEntityUid().longValue());  //1
      sProc.setString(2,role1.getCd());
      sProc.setString(4, role1.getAddReasonCd());
      sProc.setTimestamp(5, role1.getAddTime());
      if (role1.getAddUserId() != null)
        sProc.setLong(6, role1.getAddUserId().longValue());
      else
        sProc.setNull(6,Types.BIGINT);
      //sProc.setString(7, role1.getCd());
      sProc.setString(7, role1.getCdDescTxt());
      sProc.setString(8, role1.getEffectiveDurationAmt());
      sProc.setString(9, role1.getEffectiveDurationUnitCd());
      sProc.setTimestamp(10, role1.getEffectiveFromTime());
      sProc.setTimestamp(11, role1.getEffectiveToTime());
      sProc.setString(12, role1.getLastChgReasonCd());
      sProc.setTimestamp(13, role1.getLastChgTime());
      if (role1.getLastChgUserId() != null)
        sProc.setLong(14, role1.getLastChgUserId().longValue());
      else
        sProc.setNull(14,Types.BIGINT);
      sProc.setString(15, role1.getRecordStatusCd());
      sProc.setTimestamp(16, role1.getRecordStatusTime());
      sProc.setNull(17, Types.BIGINT);
      sProc.setString(18, null);
      sProc.setString(20, role1.getStatusCd());
      sProc.setTimestamp(21, role1.getStatusTime());
      sProc.setString(22, role1.getUserAffiliationTxt());
      sProc.setString(23, role1.getSubjectClassCd());
      sProc.setString(24, role1.getScopingClassCd()); //24

      sProc.setNull(25, Types.BIGINT); //25
      sProc.setString(26, null);
      sProc.setString(28, null);
      sProc.setNull(29, Types.TIMESTAMP);
      sProc.setNull(30, Types.BIGINT);

      sProc.setString(31, null);
      sProc.setString(32, null);
      sProc.setString(33, null);
      //sProc.setString(33, null);
      sProc.setNull(34, Types.TIMESTAMP);
      sProc.setNull(35, Types.TIMESTAMP);
      sProc.setString(36, null);
      sProc.setNull(37, Types.TIMESTAMP);
      sProc.setNull(38, Types.BIGINT);
      sProc.setString(39, null);
      sProc.setNull(40, Types.TIMESTAMP);
      sProc.setNull(41, Types.BIGINT);
      sProc.setString(42, null);
      sProc.setString(44, null);
      sProc.setNull(45, Types.TIMESTAMP);
      sProc.setString(46, null);
      sProc.setString(47, null);
      sProc.setString(48, null);

      // execute the stored procedure
      //logger.debug("ABout to execute the stored procedure");

      sProc.execute();

      // get the output parameters
      int role_Sequence1 = sProc.getInt(3);
      Long roleSequence1 = new Long(role_Sequence1);
      role1.setRoleSeq(roleSequence1);

      return role1;
      }

        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while obtaining database connection.\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(NEDSSSystemException nse)
        {
            logger.fatal("Error: NEDSSSystemException while obtaining database connection.\n"+nse.getMessage() , nse);
            throw new NEDSSDAOSysException( nse.getMessage());
        }

        finally
        {
            closeCallableStatement(sProc);
            releaseConnection(dbConnection);
        }
   }

   public void setRoleDTs(Collection<Object> roleDTCollection) throws NEDSSDAOSysException, NEDSSSystemException
   {

    Iterator<Object> itr = roleDTCollection.iterator();
    int resultCount = 0;
    PreparedStatement preparedStmt = null;
    Connection dbConnection = null;
       try
       {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(INSERT);
            while(itr.hasNext())
	        {    RoleDT roleDT = (RoleDT) itr.next();
	            resultCount = prepareAndInsertRole(roleDT, preparedStmt, dbConnection);
	             if ( resultCount != 1 )
	            {
	                logger.error ("Error: none or more than one role inserted at a time, resultCount = " + resultCount);
	
	            }
	
	         }

        }catch(SQLException se)
        {
            logger.fatal("Error: SQLException in setRoleDTs\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }



   }

    private int prepareAndInsertRole(RoleDT dt, PreparedStatement preparedStmt, Connection conn ) throws NEDSSDAOSysException, NEDSSSystemException
    {
     int resultCount = 0;
     try
        {

        int i = 1;
        if (dt.getSubjectEntityUid()== null)
          preparedStmt.setNull(i++, Types.BIGINT);
        else
          preparedStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
        preparedStmt.setString(i++, dt.getCd());
        if (dt.getRoleSeq() == null)
          preparedStmt.setNull(i++, Types.INTEGER);
        else
          preparedStmt.setInt(i++, dt.getRoleSeq().intValue());
        preparedStmt.setString(i++, dt.getAddReasonCd());
        if( dt.getAddTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getAddTime() );
        if( dt.getAddUserId()==null)
            preparedStmt.setNull(i++, Types.BIGINT );
        else
            preparedStmt.setLong(i++, dt.getAddUserId().longValue() );
        preparedStmt.setString(i++, dt.getSubjectClassCd());
        preparedStmt.setString(i++, dt.getScopingClassCd());

        preparedStmt.setString(i++, dt.getCdDescTxt());
        preparedStmt.setString(i++, dt.getEffectiveDurationAmt());
        preparedStmt.setString(i++, dt.getEffectiveDurationUnitCd());
        if( dt.getEffectiveFromTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getEffectiveFromTime() );
        if( dt.getEffectiveToTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getEffectiveToTime() );
        preparedStmt.setString(i++, dt.getLastChgReasonCd());
        if( dt.getLastChgTime()==null)
            preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
            preparedStmt.setTimestamp(i++, dt.getLastChgTime() );
        if( dt.getLastChgUserId()==null)
            preparedStmt.setNull(i++, Types.BIGINT );
        else
            preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() );
        preparedStmt.setString(i++, dt.getRecordStatusCd());
        preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());
        if(dt.getScopingEntityUid()==null)
          preparedStmt.setNull(i++, Types.BIGINT);
        else
          preparedStmt.setLong(i++, dt.getScopingEntityUid().longValue());
        preparedStmt.setString(i++, dt.getScopingRoleCd());
        if( dt.getScopingRoleSeq()==null)
            preparedStmt.setNull(i++, Types.SMALLINT );
        else
            preparedStmt.setInt(i++, dt.getScopingRoleSeq().intValue() );
        preparedStmt.setString(i++, dt.getStatusCd());
        preparedStmt.setTimestamp(i++, dt.getStatusTime());
        preparedStmt.setString(i++, dt.getUserAffiliationTxt());

        resultCount = preparedStmt.executeUpdate();
        return resultCount;

        }
        catch(SQLException se)
        {
            logger.fatal("Error: SQLException while inserting\n"+se.getMessage() , se);
            throw new NEDSSDAOSysException( se.getMessage());

        }

    }
    
    public int loadIfKeyExist(RoleDT roleDT) throws NEDSSDAOSysException, NEDSSSystemException
    {
         //PreparedStatement preparedStmt = getPreparedStatement(sqlStatement);
         ResultSet resultSetCode = null;
         RoleDT dt = new RoleDT();
         PreparedStatement preparedStmt = null;
         Connection dbConnection = null;
         try
         {
         	dbConnection = getConnection();
             preparedStmt = dbConnection.prepareStatement(SEARCH_BY_PK);


             preparedStmt.setLong(1, roleDT.getSubjectEntityUid()); 
             preparedStmt.setString(2, roleDT.getCd());
             preparedStmt.setLong(3, roleDT.getRoleSeq()); 
             
             resultSetCode = preparedStmt.executeQuery();
             int count = 0;
             if (resultSetCode.next()){
             	count=resultSetCode.getInt(1);
             }
             
             return count;
         }
         catch(SQLException se)
         {
             logger.fatal("Error: SQLException while searching if K exists \n"+se.getMessage() , se);
             throw new NEDSSDAOSysException( se.getMessage());
         }
         finally
         {
             closeResultSet(resultSetCode);
             closeStatement(preparedStmt);
             releaseConnection(dbConnection);
         }
    }

}
