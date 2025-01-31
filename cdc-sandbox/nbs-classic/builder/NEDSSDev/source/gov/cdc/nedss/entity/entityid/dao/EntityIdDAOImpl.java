/**
* Name:		EntityIdDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               EntityId value object in the Person entity bean.
*               This class encapsulates all the JDBC calls made by the PersonEJB
*               for a EntityId object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PersonEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.entityid.dao;


import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

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


public class EntityIdDAOImpl extends BMPBase
{

        static final LogUtils logger = new LogUtils(EntityIdDAOImpl.class.getName());

        private static final String INSERT_ENTITY_ID =
                "INSERT INTO " + DataTables.ENTITY_ID_TABLE + "(entity_uid, entity_id_seq, add_reason_cd, " //3
                + "add_time, add_user_id, as_of_date, assigning_authority_cd, assigning_authority_desc_txt, assigning_authority_id_type, " //5
                + "duration_amt, duration_unit_cd, last_chg_reason_cd, last_chg_time, last_chg_user_id, " //5
                + "record_status_cd, record_status_time, root_extension_txt, status_cd, status_time, " //5
                + "type_cd, type_desc_txt, user_affiliation_txt, valid_from_time, valid_to_time, " //5
                + "effective_from_time, effective_to_time) " //2
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        private static final String UPDATE_ENTITY_ID =
                "UPDATE " + DataTables.ENTITY_ID_TABLE + " set add_reason_cd = ?, add_time  = ?, "
                + "add_user_id = ?, as_of_date = ?, assigning_authority_cd = ?, assigning_authority_desc_txt = ?, assigning_authority_id_type = ? , "
                + "duration_amt = ?, duration_unit_cd = ?, last_chg_reason_cd = ?, last_chg_time = ?, "
                + "last_chg_user_id = ?, record_status_cd = ?, record_status_time = ?, "
                + "root_extension_txt = ?, status_cd = ?, status_time = ?, type_cd = ?, type_desc_txt = ?, "
                + "user_affiliation_txt = ?, valid_from_time = ?, valid_to_time = ?, "
                + "effective_from_time = ?, effective_to_time = ? "
                + "WHERE entity_uid = ? AND entity_id_seq = ?";

        private static final String SELECT_ENTITY_IDS =
                "SELECT entity_uid \"entityUid\", entity_id_seq \"entityIdSeq\", "
                + "add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", "
                + "as_of_date \"asOfDate\", "
                + "assigning_authority_cd \"assigningAuthorityCd\", "
                + "assigning_authority_desc_txt \"assigningAuthorityDescTxt\", "
                + "assigning_authority_id_type \"assigningAuthorityIdType\", "
                + "duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", "
                + "last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", "
                + "last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", "
                + "record_status_time \"recordStatusTime\", root_extension_txt \"rootExtensionTxt\", "
                + "status_cd \"statusCd\", status_time \"statusTime\", type_cd \"typeCd\", "
                + "type_desc_txt \"typeDescTxt\", user_affiliation_txt \"userAffiliationTxt\", "
                + "valid_from_time \"validFromTime\", valid_to_time \"validToTime\", "
                + "effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\" "
                + "FROM " + DataTables.ENTITY_ID_TABLE + " WHERE entity_uid = ? and record_status_cd ='ACTIVE'";

        private static final String SELECT_ENTITY_ID_UID =
                "SELECT entity_UID FROM " + DataTables.ENTITY_ID_TABLE + " WHERE entity_UID = ?";

        private static final String DELETE_ENTITY_IDS =
                "DELETE FROM " + DataTables.ENTITY_ID_TABLE + " WHERE entity_uid = ?";
        private static final String SELECT_ENTITYUID_ENTITY_ID =
                " SELECT Entity.entity_uid FROM " + DataTables.ENTITY_ID_TABLE+" with (nolock), "
                + DataTables.ENTITY_TABLE
                + " with (nolock) WHERE Entity_id.type_Cd = 'QEC' "
                + " AND Entity_Id.record_status_cd = '" +  NEDSSConstants.ACTIVE+"' "
                + " AND Entity_Id.root_extension_txt = ? "
                + " AND Entity.class_cd = ? "
                + " AND Entity.entity_uid = Entity_id.entity_uid";
        
        
        private static final String CHECK_ENTITY_IDS =
                "SELECT COUNT(*) count from "+DataTables.ENTITY_ID_TABLE+" where entity_uid in ("
                + "select person_uid from "+DataTables.PERSON_TABLE+" where  entity_uid !=person_parent_uid"
                + " and person_parent_uid in (SELECT distinct top 1 person.person_parent_uid FROM Entity_id eid with(nolock)  inner join person person with(nolock)"
                + " on eid.entity_uid = person.person_uid and eid.entity_uid=?)"
                + " )and record_status_cd='ACTIVE' "
                + " and assigning_authority_cd=?"
                + " and type_cd=?"
                + " and root_extension_txt=?";

        public EntityIdDAOImpl()
        {
        }

        /**
          * This method creates a new EntityID record and returns the personUID for this person.
          * @J2EE_METHOD  --  create
          * @param personUID       the long
          * @param coll            the Collection<Object>
          * @throws NEDSSSystemException
          **/
        public long create(long personUID, Collection<Object> coll) throws NEDSSSystemException
        {
        	try{
                insertEntityIDs(personUID, coll);


                return personUID;
        	}catch(Exception ex){
        		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
        		throw new NEDSSSystemException(ex.toString());
        	}
        }

        /**
          * This method is used to update an EntityID record.
          * @J2EE_METHOD  --  store
          * @param coll       the Collection<Object>
          * @throws NEDSSSystemException
          **/
        public void store(Collection<Object> coll) throws NEDSSSystemException
        {
        	try{
                updateEntityIDs(coll);
        	}catch(Exception ex){
        		logger.fatal("Exception  = "+ex.getMessage(), ex);
        		throw new NEDSSSystemException(ex.toString());
        	}
        }

        /**
          * This method loads the EntityID objects for a given personUID.
          * @J2EE_METHOD  --  load
          * @param personUID       the long
          * @throws NEDSSSystemException
          **/
        public Collection<Object> load(long personUID) throws NEDSSSystemException
        {
        	try{
                Collection<Object> idColl = selectEntityIDs(personUID);
                return idColl;
        	}catch(Exception ex){
        		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
        		throw new NEDSSSystemException(ex.toString());
        	}
        }

        /**
          * This method is used to delete an EntityID record.
          * @J2EE_METHOD  --  remove
          * @param personUID       the long
          * @throws NEDSSSystemException
          **/
        public void remove(long personUID) throws  NEDSSSystemException
        {
        	try{
                removeEntityIDs(personUID);
        	}catch(Exception ex){
        		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
        		throw new NEDSSSystemException(ex.toString());
        	}
        }

        /**
          * This method is used to determine if an EntityID record exists for a given personUID.
          * @J2EE_METHOD  --  findByPrimaryKey
          * @param personUID       the long
          * @throws NEDSSSystemException
          **/
        public Long findByPrimaryKey(long personUID) throws NEDSSSystemException
        {
        	try{
                if (EntityIdExists(personUID))
                        return (new Long(personUID));
                else
                        logger.fatal("Primary key not found in ENTITY_ID_TABLE:"
                        + personUID, new NEDSSDAOSysException());
                        return null;
        	}catch(Exception ex){
        		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
        		throw new NEDSSSystemException(ex.toString());
        	}
        }


        /**
          * This method is used to determine if an EntityID record exists for a specific person.
          * @J2EE_METHOD  --  EntityIdExists
          * @param personUID       the long
          * @throws NEDSSSystemException
          **/
        protected boolean EntityIdExists (long personUID) throws NEDSSSystemException
        {
                Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
                ResultSet resultSet = null;
                boolean returnValue = false;

                try
                {
                        dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_ID_UID);
logger.debug("personID = " + personUID);
                        preparedStmt.setLong(1, personUID);
                        resultSet = preparedStmt.executeQuery();

                        if (!resultSet.next())
                        {
                                returnValue = false;
                        }
                        else
                        {
                                personUID = resultSet.getLong(1);
                                returnValue = true;
                        }
                }
                catch(SQLException sex)
                {
                        logger.fatal("SQLException while checking for an"
                                                        + " existing entity's uid in ENTITY_ID_TABLE -> "
                                                        + personUID, sex);
                        throw new NEDSSDAOSysException(sex.toString());
                }
                catch(NEDSSSystemException nsex)
                {
                        logger.fatal("Exception while getting dbConnection for checking for an"
                                                        + " existing entity's uid -> " + personUID, nsex);
                        throw new NEDSSDAOSysException(nsex.toString());
                }
                finally
                {
                        closeResultSet(resultSet);
                        closeStatement(preparedStmt);
                        releaseConnection(dbConnection);
                }
                return returnValue;
        }


        /**
          * This method is used to add a Collection<Object> of entityID objects for a specific person.
          * @J2EE_METHOD  --  insertEntityIDs
          * @param personUID       the long
          * @param entityIDs      the Collection<Object>
          * @throws NEDSSSystemException
          **/
        private void insertEntityIDs(long personUID, Collection<Object> entityIDs) throws NEDSSSystemException{ 
        	logger.debug("INsert insertEntityIDs::1111:"+personUID);
        	Iterator<Object> anIterator = null;
            ArrayList<Object>  entityList = (ArrayList<Object> )entityIDs;
            try{
                anIterator = entityList.iterator();
                int maxSeq = 0;
                while(null != anIterator && anIterator.hasNext()) {
                    logger.debug("Number of elements: " + entityList.size());
                    EntityIdDT entityID = (EntityIdDT)anIterator.next();
                    if(entityID == null) logger.fatal("Error: Empty entity id collection", new NEDSSDAOAppException());
                    if(maxSeq == 0) {
                    	if(null == entityID.getEntityUid() || entityID.getEntityUid() < 0) entityID.setEntityUid(personUID);
                    	maxSeq = getNextMaxEntityIdSeq((entityID.getEntityUid()).longValue());                           	
                       }
                    	insertEntityID(personUID, entityID,++maxSeq);
                        entityID.setEntityUid(new Long(personUID));
                 }
               }catch(Exception ex){
                        logger.fatal("personUID: "+personUID+" Exception while inserting entity ids into ENTITY_ID_TABLE: personUID: "+personUID+"\n", ex);
                        throw new NEDSSDAOSysException(ex.toString());
              }

             logger.debug("Done inserting all entity ids");
        }//end of inserting entity ids

        /**
          * This method is used to add an entityID entry for a specific person.
          * @J2EE_METHOD  --  insertEntityID
          * @param personUID       the long
          * @param entityID      the EntityIdDT
          * @throws NEDSSDAOSysException
          * @throws NEDSSSystemException
          **/
        private void insertEntityID(long personUID, EntityIdDT entityID, int maxSeq) throws NEDSSSystemException{
             Connection dbConnection = null;
             PreparedStatement preparedStmt = null;              
             int resultCount = 0;
             try
                {
                        dbConnection = getConnection();
                }
                catch(NEDSSSystemException nsex)
                {
                        logger.fatal("Error obtaining dbConnection " +
                                "for inserting entity ids, personUID: "+personUID, nsex);
                        throw new NEDSSSystemException(nsex.toString());
                }

                try
                {
                        preparedStmt = dbConnection.prepareStatement(INSERT_ENTITY_ID);
                        
              /*
               * Fixed for Assigning Authority Cds coming from PHCR or ELR - 07/23/2012
               */
                        
               String assigningAuthouritycd = null;
               if (entityID.getAssigningAuthorityCd() != null && entityID.getAssigningAuthorityCd().length() > 199) {
            	   assigningAuthouritycd = entityID.getAssigningAuthorityCd().substring(0, 198);
            	   logger.debug("Assigning Authourity Code::"+entityID.getAssigningAuthorityCd()
						+ "' for Entity ID :" + personUID
						+ " exceeds permitted length of 199, trimming it to 199 characters as: "+ assigningAuthouritycd);
			    }
			else 
				assigningAuthouritycd = entityID.getAssigningAuthorityCd();
                       int i = 1;

                       
                  
				logger.debug("Person Uid = " + personUID);
				logger.debug("Entity  id seq number = " + entityID.getEntityIdSeq());
                        preparedStmt.setLong(i++, personUID);
                        if(entityID.getEntityIdSeq() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                        else
                                preparedStmt.setInt(i++, maxSeq);
                                preparedStmt.setString(i++, entityID.getAddReasonCd());
                                preparedStmt.setTimestamp(i++, entityID.getAddTime());
                        if(entityID.getAddUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                        else
                                preparedStmt.setLong(i++, (entityID.getAddUserId()).longValue());
                                preparedStmt.setTimestamp(i++, entityID.getAsOfDate());
                                preparedStmt.setString(i++, assigningAuthouritycd);
                                preparedStmt.setString(i++, entityID.getAssigningAuthorityDescTxt());
                                preparedStmt.setString(i++, entityID.getAssigningAuthorityIdType());
                                preparedStmt.setString(i++, entityID.getDurationAmt());
                                preparedStmt.setString(i++, entityID.getDurationUnitCd());
                                preparedStmt.setString(i++, entityID.getLastChgReasonCd());
                                preparedStmt.setTimestamp(i++, entityID.getLastChgTime());
                        if(entityID.getLastChgUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                        else
                                preparedStmt.setLong(i++, (entityID.getLastChgUserId()).longValue());
                                preparedStmt.setString(i++, entityID.getRecordStatusCd());
                                preparedStmt.setTimestamp(i++, entityID.getRecordStatusTime());
                                preparedStmt.setString(i++, entityID.getRootExtensionTxt());
                        if(entityID.getStatusCd() == null)
                                preparedStmt.setString(i++, "A");
                        else
                                preparedStmt.setString(i++, entityID.getStatusCd());
                        if(entityID.getStatusTime() == null)
                                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
                        else
                                preparedStmt.setTimestamp(i++, entityID.getStatusTime());
                                preparedStmt.setString(i++, entityID.getTypeCd());
                                preparedStmt.setString(i++, entityID.getTypeDescTxt());
                                preparedStmt.setString(i++, entityID.getUserAffiliationTxt());
                                preparedStmt.setTimestamp(i++, entityID.getValidFromTime());
                                preparedStmt.setTimestamp(i++, entityID.getValidToTime());

                                preparedStmt.setTimestamp(i++, entityID.getEffectiveFromTime());
                                preparedStmt.setTimestamp(i++, entityID.getEffectiveToTime());


                        resultCount = preparedStmt.executeUpdate();

                        if ( resultCount != 1 )
                                        logger.fatal("Error: none or more than one entity ids inserted at a time, " +
                                                "resultCount = " + resultCount, new NEDSSDAOAppException
                                                ());
                        else
                        {
                          entityID.setItNew(false);
                          entityID.setItDirty(false);
                        }
                }
                catch(SQLException sex)
                {
                        logger.fatal("SQLException while inserting " +
                                        "an entity id into ENTITY_ID_TABLE: personUID:"+personUID+"\n", sex);
                        throw new NEDSSDAOSysException("Table Name : "+ DataTables.ENTITY_ID_TABLE + "  For personUID: "+personUID+"  "+sex.toString(), sex);
                }
                finally
                {
                        closeStatement(preparedStmt);
                        releaseConnection(dbConnection);
                }
        }


		/**
		 * getMaxEntityIdSeq: this method returns the max entity_id_seq number plus 1 (the next one) for a specific entity_uid (personUid) from entity_uid table.
		 * This is used to avoid inserting duplicates in the entity_uid table in case there are INACTIVE records that we have not read from the database and,
		 * therefore, we are not aware of. This method was created as part of the resolution for the Jira Ticker ND-24948 (NBSCentral ticket #15029).
		 * @param personUID
		 * @param entityID
		 * @return
		 * @throws NEDSSSystemException
		 */
        
        
        private int getNextMaxEntityIdSeq(long personUID) throws NEDSSSystemException{
             Connection dbConnection = null;
             PreparedStatement preparedStmt = null;
             int maxSeq = 0;
             try
                {
               			String select_max_entity_id_seq = "select max(entity_id_Seq) from nbs_odse..entity_id where entity_uid = ?";
               		    dbConnection = getConnection();
						preparedStmt = dbConnection.prepareStatement(select_max_entity_id_seq);
						preparedStmt.setLong(1, personUID);
						ResultSet resultCount = preparedStmt.executeQuery();
						if(resultCount !=null && resultCount.next())	maxSeq  = resultCount.getInt(1);
					
                }catch(NEDSSSystemException nse){
                 logger.fatal("Error obtaining dbConnection for inserting entity ids, personUID: "+personUID, nse);
                 throw new NEDSSSystemException(nse.toString());
               }catch(SQLException se){
                     logger.fatal("SQLException while inserting an entity id into ENTITY_ID_TABLE: personUID:"+personUID+"\n", se);
                     throw new NEDSSDAOSysException("Table Name : "+ DataTables.ENTITY_ID_TABLE + "  For personUID: "+personUID+"  "+se.toString(), se);
               }finally{
                        closeStatement(preparedStmt);
                        releaseConnection(dbConnection);
                }
        
              return maxSeq;
        }


        
        
        
        
        
        
        
        
        /**
          * This method is used to update a Collection<Object> of entityID objects for a specific person.
          * @J2EE_METHOD  --  updateEntityIDs
          * @param entityIDs       the Collection<Object>
          * @throws NEDSSSystemException
          **/
        private void updateEntityIDs(Collection<Object> entityIDs) throws  NEDSSSystemException {
        	logger.debug("update entities::::::::::::::");
            Iterator<Object> anIterator = null;
            if(entityIDs != null){
            try{
             	int maxSeq = 0;
                for(anIterator = entityIDs.iterator(); anIterator.hasNext();){
                     EntityIdDT entityID = (EntityIdDT)anIterator.next();
                     if(entityID == null)
                    	 logger.fatal("Error: Empty entity id collection", new NEDSSDAOAppException());
                         logger.debug("Check dirty markers for Store entity id: new = " + entityID.isItNew() + ", dirty = " + entityID.isItDirty());
                         logger.debug("Store entity id: UID = " + entityID.getEntityUid() + "id seq number:" + entityID.getEntityIdSeq());
                         logger.debug("update entities::::::::::::::"+entityID.getEntityUid());
                         if(entityID.isItNew()){
                               if(maxSeq == 0) maxSeq = getNextMaxEntityIdSeq((entityID.getEntityUid()).longValue());           
                               insertEntityID((entityID.getEntityUid()).longValue(), entityID, ++maxSeq);
                         }
                         if(entityID.isItDirty())    updateEntityID(entityID);
                         if (entityID.isItDelete()){
                             entityID.setStatusCd("I");
                             updateEntityID(entityID);
                         }
                      }
               }catch(Exception ex){
                    logger.fatal("Exception while updating entity ids, \n"+ex.getMessage(), ex);
                    throw new NEDSSDAOSysException(ex.toString());
               }

          }
        }//end of updating entity id table

        /**
          * This method is used to update an entityID object for a specific person.
          * @J2EE_METHOD  --  updateEntityID
          * @param entityID       the EntityIdDT
          * @throws NEDSSSystemException
          **/
        private void updateEntityID(EntityIdDT entityID) throws  NEDSSSystemException
        {
                Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
                int resultCount = 0;

                /**
                 * Updates an entity id
                 */

                if(entityID != null)
                {
                        try
                        {
                                dbConnection = getConnection();
                        }
                        catch(NEDSSSystemException nsex)
                        {
                                logger.fatal("Error obtaining dbConnection " +
                                        "for updating entity ids", nsex);
                                throw new NEDSSSystemException(nsex.toString());
                        }

                        try
                        {
                                preparedStmt = dbConnection.prepareStatement(UPDATE_ENTITY_ID);

                                int i = 1;

                                logger.debug("Entity id seq = " + entityID.getEntityIdSeq());

                                preparedStmt.setString(i++, entityID.getAddReasonCd());
                                preparedStmt.setTimestamp(i++, entityID.getAddTime());
                                if(entityID.getAddUserId() == null)
                                        preparedStmt.setNull(i++, Types.INTEGER);
                                else
                                        preparedStmt.setLong(i++, (entityID.getAddUserId()).longValue());
                                        preparedStmt.setTimestamp(i++, entityID.getAsOfDate());
                                        preparedStmt.setString(i++, entityID.getAssigningAuthorityCd());
                                        preparedStmt.setString(i++, entityID.getAssigningAuthorityDescTxt());
                                        preparedStmt.setString(i++, entityID.getAssigningAuthorityIdType());
                                        preparedStmt.setString(i++, entityID.getDurationAmt());
                                        preparedStmt.setString(i++, entityID.getDurationUnitCd());
                                        preparedStmt.setString(i++, entityID.getLastChgReasonCd());
                                        preparedStmt.setTimestamp(i++, entityID.getLastChgTime());
                                if(entityID.getLastChgUserId() == null)
                                        preparedStmt.setNull(i++, Types.INTEGER);
                                else
                                        preparedStmt.setLong(i++, (entityID.getLastChgUserId()).longValue());
                                        preparedStmt.setString(i++, entityID.getRecordStatusCd());
                                        preparedStmt.setTimestamp(i++, entityID.getRecordStatusTime());
                                        preparedStmt.setString(i++, entityID.getRootExtensionTxt());
                                if(entityID.getStatusCd() == null)
                                        preparedStmt.setString(i++, "A");
                                else
                                        preparedStmt.setString(i++, entityID.getStatusCd());
                                if(entityID.getStatusTime() == null)
                                        preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
                                else
                                        preparedStmt.setTimestamp(i++, entityID.getStatusTime());
                                        preparedStmt.setString(i++, entityID.getTypeCd());
                                        preparedStmt.setString(i++, entityID.getTypeDescTxt());
                                        preparedStmt.setString(i++, entityID.getUserAffiliationTxt());
                                        preparedStmt.setTimestamp(i++, entityID.getValidFromTime());
                                        preparedStmt.setTimestamp(i++, entityID.getValidToTime());
                                        preparedStmt.setTimestamp(i++, entityID.getEffectiveFromTime());
                                        preparedStmt.setTimestamp(i++, entityID.getEffectiveToTime());
                                if(entityID.getEntityUid() == null)
                                        preparedStmt.setNull(i++, Types.INTEGER);
                                else
                                        preparedStmt.setLong(i++, (entityID.getEntityUid()).longValue());
                                if(entityID.getEntityIdSeq() == null)
                                        preparedStmt.setNull(i++, Types.INTEGER);
                                else
                                        preparedStmt.setInt(i++, (entityID.getEntityIdSeq()).intValue());

                                resultCount = preparedStmt.executeUpdate();
                                logger.debug("Done updating an entity id");
                                if (resultCount != 1)
                                logger.fatal("Error: none or more than one entity id updated at a time, " +
                                                          "resultCount = " + resultCount, new NEDSSDAOAppException
                                                        ());
                        }
                        catch(SQLException sex)
                        {
                                logger.fatal("SQLException while updating " +
                                        "entity ids, \n"+sex.getMessage(), sex);
                                throw new NEDSSDAOSysException("Table Name : "+ DataTables.ENTITY_ID_TABLE +"  "+sex.toString(), sex);
                        }
                        finally
                        {
                                closeStatement(preparedStmt);
                                releaseConnection(dbConnection);
                        }
                }
        }//end of updating entity id table


        /**
          * This method is used to retrieve entityID objects for a specific person.
          * @J2EE_METHOD  --  selectEntityIDs
          * @param personUID       the long
          * @throws NEDSSSystemException
          **/
        private Collection<Object> selectEntityIDs (long personUID) throws NEDSSSystemException

        {

                Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
                ResultSet resultSet = null;
                ResultSetMetaData resultSetMetaData = null;
                EntityIdDT entityID = new EntityIdDT();
                ResultSetUtils resultSetUtils = new ResultSetUtils();



                try
                {
                        dbConnection = getConnection();
                }
                catch(NEDSSSystemException nsex)
                {
                        logger.fatal("SQLException while obtaining database connection " +
                                                        "for selectEntityIDs ", nsex);
                        throw new NEDSSSystemException(nsex.toString());
                }

                /**
                 * Selects entity ids
                 */
                try
                {
                        preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_IDS);
                        preparedStmt.setLong(1, personUID);
                        resultSet = preparedStmt.executeQuery();
                        resultSetMetaData = resultSet.getMetaData();
                        ArrayList<Object>  idList = new ArrayList<Object> ();
                        ArrayList<Object>  reSetList = new ArrayList<Object> ();
                        idList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, entityID.getClass(), idList);


                        /*if (idList.isEmpty())
                                throw new NEDSSPersonDAOAppException("No entity ids for this personUID: " +
                                                                   personUID);
                        else */
                        for(Iterator<Object> anIterator = idList.iterator(); anIterator.hasNext(); )
                        {
                                EntityIdDT reSetName = (EntityIdDT)anIterator.next();
                                reSetName.setItNew(false);
                                reSetName.setItDirty(false);
                                reSetList.add(reSetName);
                        }

                        logger.debug("Return entity id collection");
                        return reSetList;
                }
                catch(SQLException se)
                {
                        logger.fatal("SQLException while selecting " +
                                                        "entity id collection; uid = " + personUID , se);
                        throw new NEDSSDAOSysException(se.toString());
                }
                catch(ResultSetUtilsException rsuex)
                {
                        logger.fatal("Error in result set handling while selecting entity ids; uid = "+ personUID, rsuex);
                        throw new NEDSSDAOSysException(rsuex.toString());
                }
                catch(Exception ex)
                {
                        logger.fatal("Exception while selection " +
                                  "entity ids; uid = " + personUID, ex);
                        throw new NEDSSDAOSysException(ex.toString());
                }
                finally
                {
                        closeResultSet(resultSet);
                        closeStatement(preparedStmt);
                        releaseConnection(dbConnection);
                }
        }

   public Long selectEntityUID(String quickCd, String classCd) throws NEDSSSystemException

  {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        Long entityUid = null;

        try
        {

               dbConnection = getConnection();
               /** System.out.println(" \n\n The querry  ...... " + SELECT_COUNT_ENTITY_ID.toString() );**/
                preparedStmt = dbConnection.prepareStatement(SELECT_ENTITYUID_ENTITY_ID);
                preparedStmt.setString(1,quickCd);
                preparedStmt.setString(2,classCd);
                resultSet = preparedStmt.executeQuery();
                if(resultSet.next())
                {
                           entityUid = new Long(resultSet.getLong(1));
                   logger.info("\n\n  - entityUid - from isQuickCode Query = " + entityUid);
                 }

              logger.debug("Return entity UID ");
              return entityUid;
        }
        catch(SQLException se)
        {
                logger.fatal("SQLException while selecting " +
                                                "entity  UID", se);
                throw new NEDSSDAOSysException(se.toString());
        }
        catch(Exception ex)
        {
                logger.fatal("Exception while selection " +
                          "entity UID = " , ex);
                throw new NEDSSDAOSysException(ex.toString());
        }
        finally
        {
                closeResultSet(resultSet);
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
        }
}


        /**
          * This method is used to remove an entityID object from a specific person.
          * @J2EE_METHOD  --  removeEntityIDs
          * @param personUID       the long
          * @throws NEDSSSystemException
          **/
        private void removeEntityIDs (long personUID) throws NEDSSSystemException
        {
                Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
                int resultCount = 0;

                try
                {
                        dbConnection = getConnection();
                }
                catch(NEDSSSystemException nsex)
                {
                        logger.fatal("SQLException while obtaining database connection " +
                                                        "for deleting entity ids ", nsex);
                        throw new NEDSSSystemException(nsex.toString());
                }

                /**
                 * Deletes entity ids
                 */
                try
                {
                        preparedStmt = dbConnection.prepareStatement(DELETE_ENTITY_IDS);
                        preparedStmt.setLong(1, personUID);
                        resultCount = preparedStmt.executeUpdate();

                        if (resultCount != 1)
                        {
                                logger.fatal("Error: cannot delete entity ids from ENTITY_ID_TABLE!! resultCount = " +
                                 resultCount, new NEDSSDAOAppException
                                ());
                        }
                }
                catch(SQLException sex)
                {
                        logger.fatal("SQLException while removing " +
                                                        "entity ids; person uid = " + personUID , sex);
                        throw new NEDSSDAOSysException(sex.toString());
                }
                finally
                {
                        closeStatement(preparedStmt);
                        releaseConnection(dbConnection);
                }
        }
        
		private boolean checkInOthers(EntityIdDT entityID) throws  NEDSSSystemException {
            boolean checkInOtherEntities = false;
            Connection dbConnection = null;
            PreparedStatement preparedStmt = null;
            if(entityID != null){
            try{
               dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(CHECK_ENTITY_IDS);
                preparedStmt.setLong(1, entityID.getEntityUid());
                preparedStmt.setString(2, entityID.getAssigningAuthorityCd());
                preparedStmt.setString(3,entityID.getTypeCd());
                preparedStmt.setString(4,entityID.getRootExtensionTxt());
                ResultSet resultSet = preparedStmt.executeQuery();
                int count = resultSet.getInt(1);
                if(count > 0) checkInOtherEntities=true;

               } catch(Exception ex){
                logger.fatal("SQLException while checkInOthers \n"+ex.getMessage(), ex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.ENTITY_ID_TABLE +"  "+ex.toString(), ex);
        }
        finally
        {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
        }
     }
      return checkInOtherEntities;
  }

}//end of EntityIdDAOImpl class