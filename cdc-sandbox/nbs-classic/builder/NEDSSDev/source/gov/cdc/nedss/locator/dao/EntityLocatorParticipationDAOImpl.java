//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\dao\\EntityLocatorParticipationDAOImpl.java
/**
* Name:		EntityLocatorParticipationDAOImpl.java
* Description:	This extends BMPBase which implements NEDSSDAOInterface for the
*               EntityLocatorParticipation value object.
*               This class encapsulates all the JDBC calls required to
*               create,load and store values into the entity_locator_participation table
*
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Venu Pannirselvam
* @modified     Add modification Information here. Each person should add ones own.
* @version	1.0 10/04/2001
*/
package gov.cdc.nedss.locator.dao;

/**
 * /////////////////////////////// Import(s) ////////////////////////////////////
 */
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * ... end imports
 */
public class EntityLocatorParticipationDAOImpl extends BMPBase
{
   //For logging
  static final LogUtils logger = new LogUtils(EntityLocatorParticipationDAOImpl.class.getName());
  public static final String INSERT_ENTITY_LOCATOR_PARTICIPATION = "INSERT INTO Entity_locator_participation (entity_uid, locator_uid, "+
                        "add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, class_cd, duration_amt, duration_unit_cd, from_time, "+
                        "last_chg_reason_cd, last_chg_time, last_chg_user_id, locator_desc_txt, record_status_cd, record_status_time, status_cd, "+
                        "status_time, to_time, use_cd, user_affiliation_txt, valid_time_txt, version_ctrl_nbr, as_of_date) VALUES "+
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
  public static final String UPDATE_ENTITY_LOCATOR_PARTICIPATION = "UPDATE " + DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE + " SET add_reason_cd=?, "+
                         "add_time=?, add_user_id=?, cd = ?, cd_desc_txt = ?, class_cd=?, duration_amt = ?, duration_unit_cd = ?,  from_time = ?,  "+
                         "last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, locator_desc_txt = ?, record_status_cd = ?, record_status_time = ?,  "+
                         "status_cd = ?, status_time = ?, to_time = ?, use_cd = ?, user_affiliation_txt = ?, valid_time_txt = ?, version_ctrl_nbr = ?, as_of_date = ? WHERE entity_uid =? AND locator_uid = ?";
  public static final String SELECT_ENTITY_LOCATOR_PARTICIPATION_UID = "SELECT entity_UID FROM " + DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE + " WHERE entity_UID = ?";
  public static final String SELECT_ENTITY_LOCATORS = "SELECT locator_uid FROM " + DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE + " WHERE entity_uid = ?";
  public static final String SELECT_ENTITY_LOCATOR_PARTICIPATION = "SELECT entity_uid \"entityUid\", locator_uid \"locatorUid\", cd \"cd\",  cd_desc_txt \"cdDescTxt\", "+
                        "class_cd \"classCd\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\",  from_time \"fromTime\",  locator_desc_txt \"locatorDescTxt\", "+
                        "record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\",  status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", "+
                        "use_cd \"useCd\", user_affiliation_txt \"userAffiliationTxt\", valid_time_txt \"validTimeTxt\", add_reason_cd \"addReasonCd\", add_time \"addTime\", "+
                        "add_user_id \"addUserId\",  last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", version_ctrl_nbr \"versionCtrlNbr\", as_of_date \"asOfDate\" FROM " +
                        DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE + " WHERE entity_uid = ? AND locator_uid = ?";

  private PhysicalLocatorDAOImpl physicalLocatorDAO = null;
  private PostalLocatorDAOImpl postalLocatorDAO = null;
  private TeleLocatorDAOImpl teleLocatorDAO = null;

   /**
    * ///////////////////////////// Constructor(s) /////////////////////////////////
    * EntityLocatorParticipationDAOImpl is the default constructor requires no
    * parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C0D51C50060
    */
   public EntityLocatorParticipationDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException
   {

   }

   /**
    * /////////////////////////////// Methods(s) ///////////////////////////////////
    * @methodname create
    * Iterates through an ArrayList<Object> of EntityLocatorDTs and calls the create method
    * with the entityUID and the value object
    * @param long Entity Unique ID
    * @param Collection<Object>  an ArrayList<Object> of EntityLocators
    * @param entityUID
    * @param coll
    * @return  long a positive value if successful
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the
    * NEDSSUIDGenerator
    * Need to check if the locator already exists otherwise add
    * @roseuid 3C0D51C50197
    */
   public long create(long entityUID, Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
   {   	
	   	long locatorUID = 0;
   		try{
	        ArrayList<Object>  entityLocatorArray =  (ArrayList<Object> )coll;
	        Iterator<Object> iterator = entityLocatorArray.iterator();
	
	          while (iterator.hasNext())
	         {
	
	          locatorUID = create(entityUID,iterator.next());
	
	         }
   		}catch(NEDSSDAOSysException ex){
   			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
   			throw new NEDSSDAOSysException(ex.toString());
   		}catch(Exception ex){
   			logger.fatal("Exception  = "+ex.getMessage(), ex);
   			throw new NEDSSSystemException(ex.toString());
   		}

    return 1;
   }

   /**
    * @methodname create
    * Creates an Object of type EntityLocatorParticipationDT and inserts a row into
    * the entity_locator_participation table
    * Calls the respective locator's create method based on the class code
    * Calls its own insertmethod
    * @param long Entity Unique ID
    * @param obj Object to be created
    * @param entityUID
    * @return the unique id of the created object
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the
    * NEDSSUIDGenerator
    * @roseuid 3C0D51C600C6
    */
   public long create(long entityUID, Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
          long locatorUID = -1;

       try{
          if  (obj == null)
              logger.error("The Entity_Locator_Participation Object is NULL");

          		logger.debug("Trying to get entity locator ");
               EntityLocatorParticipationDT  entityLocatorVO = (EntityLocatorParticipationDT) obj;
               entityLocatorVO.setEntityUid(Long.getLong("" + entityUID));
               	logger.debug("Got entity locator participation obj");
               if (entityLocatorVO.getClassCd().equals(NEDSSConstants.POSTAL)) {
            	   	logger.debug("In postal");
                   PostalLocatorDT postalLocatorVO = entityLocatorVO.getThePostalLocatorDT();
                   if(postalLocatorDAO == null)
                   {
                      postalLocatorDAO = new PostalLocatorDAOImpl();
                   }
                   locatorUID = postalLocatorDAO.create(postalLocatorVO);
                   logger.debug(" pstLocator UID = " + locatorUID);
               }

                if (entityLocatorVO.getClassCd().equals(NEDSSConstants.PHYSICAL)) {
                   PhysicalLocatorDT physicalLocatorVO = entityLocatorVO.getThePhysicalLocatorDT();
                   if(physicalLocatorDAO == null)
                   {
                      physicalLocatorDAO = new PhysicalLocatorDAOImpl();
                   }
                   locatorUID = physicalLocatorDAO.create(physicalLocatorVO);
                   logger.debug(" physLocator UID = " + locatorUID);
               }

                if (entityLocatorVO.getClassCd().equals(NEDSSConstants.TELE)) {
                   TeleLocatorDT teleLocatorVO = entityLocatorVO.getTheTeleLocatorDT();
                   if(teleLocatorDAO == null)
                   {
                      teleLocatorDAO = new TeleLocatorDAOImpl();
                   }
                   locatorUID = teleLocatorDAO.create(teleLocatorVO);
                   logger.debug(" teleLocator UID = " + locatorUID);
               }
                logger.debug("Locator UID = " + locatorUID);
               if (!(locatorUID > 0))
                   logger.error("Create of Locator of Type " + entityLocatorVO.getClassCd() + "/n Failed in Entity_Locator_Participation create method" );


                   insertEntityLocatorParticipation(entityUID, locatorUID, obj);
                entityLocatorVO.setItNew(false);
                entityLocatorVO.setItDirty(false);

          }
          catch (Exception e){

            logger.fatal("Error creating entity locator participation"+e.getMessage(), e);
            throw new NEDSSSystemException(e.toString());
          }
           return locatorUID;
   }

   /**
    * @param personUID
    * @return Long
    * @throws gov.cdc.nedss.cdm.ejb.person.exceptions.NEDSSPersonDAOSysException
    * @throws gov.cdc.nedss.cdm.ejb.person.exceptions.NEDSSPersonDAOFinderException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @roseuid 3C0D51C60292
    */
   public Long findByPrimaryKey(long personUID) throws NEDSSSystemException, NEDSSDAOSysException
   {
	   try{
	        if (entityLocatorParticipationExists(personUID))
	            return (new Long(personUID));
	        else
	            logger.error("Primary key not found in ENTITY_LOCATOR_PARTICIPATION_TABLE:"
	            + personUID);
	            return null;
	   }catch(NEDSSDAOSysException ex){
  			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
  			throw new NEDSSDAOSysException(ex.toString());
  	   }catch(Exception ex){
  			logger.fatal("Exception  = "+ex.getMessage(), ex);
  			throw new NEDSSSystemException(ex.toString());
  	   }
   }

   /**
    * @param personUID
    * @return boolean
    * @throws gov.cdc.nedss.cdm.ejb.person.exceptions.NEDSSPersonDAOSysException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @roseuid 3C0D51C700B3
    */
   protected boolean entityLocatorParticipationExists(long personUID) throws NEDSSDAOSysException, NEDSSSystemException
   {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_LOCATOR_PARTICIPATION_UID);
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
                            + " existing entity locator participation's uid in " +
                            "ENTITY_LOCATOR_PARTICIPATION_TABLE -> "
                            + personUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing entity locator participation's uid -> " + personUID , nsex);
            throw new NEDSSDAOSysException( nsex.getMessage());
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
    * @methodname store
    * Iterates through an ArrayList<Object>  of EntityLocatorDTs and calls the
    * storeEntityLocatorParticipation method with the value object
    * @param obj Object with modified information of type EntityLocatorParticipationDT
    * @param coll
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D51C70226
    */
   public void store(Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
   {
        ArrayList<Object>  entityLocatorArray =  (ArrayList<Object> )coll;
        Iterator<Object> iterator = entityLocatorArray.iterator();
        EntityLocatorParticipationDT entityLocatorVO = new EntityLocatorParticipationDT();
        try {
        logger.debug("Start updating entity locators");
         while (iterator.hasNext())
         {
          entityLocatorVO = (EntityLocatorParticipationDT)iterator.next();
          //if( entityLocatorVO.getLocatorUid() == null)
          if( entityLocatorVO.isItNew() && entityLocatorVO.getEntityUid() != null)
             create(entityLocatorVO.getEntityUid().longValue(),entityLocatorVO);
          else
             if( entityLocatorVO.isItDirty()&& entityLocatorVO.getEntityUid() != null)
                storeEntityLocatorParticipation(entityLocatorVO);
          else
             if( entityLocatorVO.isItDelete() && entityLocatorVO.getEntityUid() != null){
                entityLocatorVO.setStatusCd(NEDSSConstants.STATUS_INACTIVE );
                storeEntityLocatorParticipation(entityLocatorVO);
             }

          logger.debug("Done updating entity locators");
         }
     } catch (NEDSSSystemException nae) {
              logger.fatal("Error while generating UID "+nae.getMessage(), nae);
              throw new NEDSSSystemException( nae.toString() );
      }
      catch(Exception ex)
      {
          logger.fatal("Error while updating entity locator."+ex.getMessage(), ex);
          throw new NEDSSSystemException(ex.toString());
      }
   }

   /**
    * @methodname store
    * Updates a row in the respective locator table base on the class code
    * Updates a row in the entity_locator_participation table
    * Call the respective locator's store method
    * Calls updateEntityLocatorParticipation which updates the
    * entity_locator_participation table
    * @param obj Object with modified information of type EntityLocatorParticipationDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D51C80078
    */
   public void storeEntityLocatorParticipation(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
              if  (obj == null)
                  logger.error("The Entity_Locator_Participation Object is NULL");

              EntityLocatorParticipationDT  entityLocatorVO = (EntityLocatorParticipationDT) obj;

               if (entityLocatorVO.getClassCd().equals(NEDSSConstants.POSTAL)
                               && entityLocatorVO.getThePostalLocatorDT()  != null) {
                   PostalLocatorDT postalLocatorVO = entityLocatorVO.getThePostalLocatorDT();
                   if (postalLocatorVO.isItDirty()) {
                      if(postalLocatorDAO == null)
                      {
                          postalLocatorDAO = new PostalLocatorDAOImpl();
                      }
                      postalLocatorDAO.store(postalLocatorVO);
                    }
               }

                if (entityLocatorVO.getClassCd().equals(NEDSSConstants.PHYSICAL)
                         && entityLocatorVO.getThePhysicalLocatorDT() != null) {
                   PhysicalLocatorDT physicalLocatorVO = entityLocatorVO.getThePhysicalLocatorDT();
                   if (physicalLocatorVO.isItDirty()) {
                       if(physicalLocatorDAO == null)
                       {
                          physicalLocatorDAO = new PhysicalLocatorDAOImpl();
                       }
                       physicalLocatorDAO.store(physicalLocatorVO);
                   }
               }

                if (entityLocatorVO.getClassCd().equals(NEDSSConstants.TELE)
                               && entityLocatorVO.getTheTeleLocatorDT() != null) {
                   TeleLocatorDT teleLocatorVO = entityLocatorVO.getTheTeleLocatorDT();
                   if (teleLocatorVO.isItDirty()) {
                       if(teleLocatorDAO == null)
                       {
                          teleLocatorDAO = new TeleLocatorDAOImpl();
                       }
                       teleLocatorDAO.store(teleLocatorVO);
                   }
               }



               if (entityLocatorVO.isItDirty())
                   updateEntityLocatorParticipation(obj);
	   }catch(NEDSSDAOSysException ex){
 			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
 			throw new NEDSSDAOSysException(ex.toString());
 	   }catch(Exception ex){
 			logger.fatal("Exception  = "+ex.getMessage(), ex);
 			throw new NEDSSSystemException(ex.toString());
 	   }
   }

   /**
    * @methodname load
    * Selects all the locator id for a given entityID from the
    * entity_locator_participation table
    * Populates the EntityLocatorParticipation value object by calling the load
    * method with entityUID and the locatorUID
    * @param entityLocatorUID unique Entity identifier of the rows to be selected
    * @param entityUID
    * @return Collection<Object>  of Objects of type EntityLocatorParticipationDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D51C802A9
    */
   public Collection<Object> load(long entityUID) throws NEDSSDAOSysException, NEDSSSystemException
   {

      ArrayList<Object>  entityLocatorArray  = new ArrayList<Object> ();
      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      ResultSet resultSet = null;
      try {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_LOCATORS);
            preparedStmt.setLong(1, entityUID);
            logger.debug("Sql for entity locator befor execution" + SELECT_ENTITY_LOCATORS);
            resultSet = preparedStmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            ArrayList<Object>  locatorList = new ArrayList<Object> ();


            while (resultSet.next()) {

              locatorList.add(new Long(resultSet.getLong("locator_uid")));



            }

           Iterator<Object> itr = locatorList.iterator();
           while (itr.hasNext()) {

                entityLocatorArray.add(loadEntityLocatorParticipation(entityUID,  ((Long)itr.next()).longValue()));
           }

           return entityLocatorArray;
      }
      catch (SQLException sex)
      {
            logger.fatal("SQLException while loading " +
                  "a EntityLocatorParticipation, entityUID = " + entityUID, sex);

            throw new NEDSSDAOSysException(sex.toString());

      }
      finally
      {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
      }



   }

   /**
    * @methodname loadEntityLocatorParticipation
    * Selects a row in the entity_locator_participation table
    * Populates the EntityLocatorParticipation value object
    * Sets the appropriate LocatorValueObject base on class code
    * @param entityLocatorUID unique identifier of the row to be selected
    * @param entityUID
    * @param locatorUID
    * @return Object of type EntityLocatorParticipationDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D51C900E8
    */
   public Object loadEntityLocatorParticipation(long entityUID, long locatorUID) throws NEDSSDAOSysException, NEDSSSystemException
   {
            Connection dbConnection = null;
            PreparedStatement preparedStmt = null;
            ResultSet resultSet = null;

            try {
            dbConnection = getConnection();
            EntityLocatorParticipationDT entityLocatorVO = new EntityLocatorParticipationDT();

            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_LOCATOR_PARTICIPATION);
            preparedStmt.setLong(1, entityUID);
            preparedStmt.setLong(2, locatorUID);
            resultSet = preparedStmt.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            ResultSetUtils  populateBean = new ResultSetUtils();
            Class EntityLocatorParticipationClass = entityLocatorVO.getClass();
            entityLocatorVO = (EntityLocatorParticipationDT) populateBean.mapRsToBean(resultSet,rsMetaData,EntityLocatorParticipationClass) ;
     // logger.debug("Participation populated" + entityLocatorVO.toString());
            if(entityLocatorVO.getClassCd().equals(NEDSSConstants.POSTAL))
            {
              // logger.debug("Adding postal locator");
               if(postalLocatorDAO == null)
               {
                  postalLocatorDAO = new PostalLocatorDAOImpl();
               }
               entityLocatorVO.setThePostalLocatorDT((PostalLocatorDT) postalLocatorDAO.loadObject(entityLocatorVO.getLocatorUid().longValue()));
            }
            if(entityLocatorVO.getClassCd().equals(NEDSSConstants.PHYSICAL))
            {
               // logger.debug("Adding physical locator");
               if(physicalLocatorDAO == null)
               {
                  physicalLocatorDAO = new PhysicalLocatorDAOImpl();
               }
               PhysicalLocatorDT physicalLocatorDT = (PhysicalLocatorDT) physicalLocatorDAO.loadObject(entityLocatorVO.getLocatorUid().longValue());
               physicalLocatorDT.setImageTxt(null);
               entityLocatorVO.setThePhysicalLocatorDT(physicalLocatorDT);
              // logger.debug("Loaded physical with value: " +   entityLocatorVO.getThePhysicalLocatorDT());
            }

            if(entityLocatorVO.getClassCd().equals(NEDSSConstants.TELE))
            {
               // logger.debug("Adding tele locator");
               if(teleLocatorDAO == null)
               {
                  teleLocatorDAO = new TeleLocatorDAOImpl();
               }
               entityLocatorVO.setTheTeleLocatorDT((TeleLocatorDT) teleLocatorDAO.loadObject(entityLocatorVO.getLocatorUid().longValue()));

            }
          //  logger.debug("A locatorVO" + entityLocatorVO.toString());
            entityLocatorVO.setItNew(false);
            entityLocatorVO.setItDirty(false);
            entityLocatorVO.setItDelete(false);
            return entityLocatorVO;
        }
         catch (ResultSetUtilsException resue)
        {
        	 logger.fatal("ResultSetUtilsException  = "+resue.getMessage(), resue);
            return null;

        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while loading " +
                  "a EntityLocatorParticipation, entityUID = " + entityUID, sex);

            throw new NEDSSDAOSysException(sex.toString());

        }
        catch(Exception ex)
        {
            logger.fatal("Exception while loading " +
                  "a EntityLocatorParticipation, entityUID = " + entityUID, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }


   }

   /**
    * @methodname insertEntityLocatorParticipation
    * Inserts a row into the entity_locator_participation table
    * @param obj Object to be created
    * @param entityUID
    * @param locatorUID
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the
    * NEDSSUIDGenerator
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D51C90341
    */
   private void insertEntityLocatorParticipation(long entityUID, long locatorUID, Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
        EntityLocatorParticipationDT entityLocatorVO = (EntityLocatorParticipationDT) obj;

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
                            "for insertEntityLocatorParticipation " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        //Inserts a Entity Locator Participation Row


            try
            {

                preparedStmt = dbConnection.prepareStatement(INSERT_ENTITY_LOCATOR_PARTICIPATION);


                int i = 1;

                preparedStmt.setLong(i++, entityUID); //1
                preparedStmt.setLong(i++, locatorUID);//2
                preparedStmt.setString(i++,entityLocatorVO.getAddReasonCd());//3
                if (entityLocatorVO.getAddTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //4
                else
                     preparedStmt.setTimestamp(i++,entityLocatorVO.getAddTime());
                if (entityLocatorVO.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);   //5
                else
                    preparedStmt.setLong(i++,entityLocatorVO.getAddUserId().longValue());
                preparedStmt.setString(i++,entityLocatorVO.getCd());
                preparedStmt.setString(i++,entityLocatorVO.getCdDescTxt());
                preparedStmt.setString(i++,entityLocatorVO.getClassCd());
                preparedStmt.setString(i++,entityLocatorVO.getDurationAmt());
                preparedStmt.setString(i++,entityLocatorVO.getDurationUnitCd()); //10
                if (entityLocatorVO.getFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                        preparedStmt.setTimestamp(i++,entityLocatorVO.getFromTime());//11
                preparedStmt.setString(i++,entityLocatorVO.getLastChgReasonCd());
                if (entityLocatorVO.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,entityLocatorVO.getLastChgTime());//13
                if (entityLocatorVO.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++,entityLocatorVO.getLastChgUserId().longValue());//14
                preparedStmt.setString(i++,entityLocatorVO.getLocatorDescTxt()); //15
                preparedStmt.setString(i++,entityLocatorVO.getRecordStatusCd());
                if (entityLocatorVO.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,entityLocatorVO.getRecordStatusTime());//17
                if (entityLocatorVO.getStatusCd() == null)
                  preparedStmt.setString(i++,entityLocatorVO.getStatusCd());
                else
                  preparedStmt.setString(i++,entityLocatorVO.getStatusCd().trim());
                if (entityLocatorVO.getStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,entityLocatorVO.getStatusTime());//19
                if (entityLocatorVO.getToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                   preparedStmt.setTimestamp(i++,entityLocatorVO.getToTime());//20
                preparedStmt.setString(i++,entityLocatorVO.getUseCd()); //21
                preparedStmt.setString(i++,entityLocatorVO.getUserAffiliationTxt());
                preparedStmt.setString(i++,entityLocatorVO.getValidTimeTxt());
                if (entityLocatorVO.getVersionCtrlNbr() == null){
                  preparedStmt.setInt(i++,1); // Insert should put value 1. It may come from front end
	          logger.debug("The version control number cannot be null, the database doesn't allow this!");
                }
	        else
                  preparedStmt.setInt(i++,entityLocatorVO.getVersionCtrlNbr().intValue());

                if(entityLocatorVO.getAsOfDate() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,entityLocatorVO.getAsOfDate());

                resultCount = preparedStmt.executeUpdate();


            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                    "ENTITY_LOCATOR_PARTICIPATION_TABLE: \n " + INSERT_ENTITY_LOCATOR_PARTICIPATION
                    , sex);
                throw new NEDSSDAOSysException( "Table Name : Entity_locator_participation  " + sex.getMessage(), sex );
             }
            finally
           {
              //closeResultSet(resultSet);
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
           }

   }

   /**
    * end of inserting Entity locator participation row
    * @methodname updateEntityLocatorParticipation
    * Updates a row in the entity_locator_participation table
    * @param obj Object with modified information of type EntityLocatorParticipationDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D51CA02AC
    */
   private void updateEntityLocatorParticipation(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {

         EntityLocatorParticipationDT entityLocatorVO = (EntityLocatorParticipationDT) obj;
         if (entityLocatorVO.getEntityUid() != null &&
            entityLocatorVO.getLocatorUid() != null)
        {
            Connection dbConnection = null;
            PreparedStatement preparedStmt = null;
            int i = 1;
            int resultCount = 0;

            //Updates Entity Locator Participation Row
            if(entityLocatorVO != null)
            {
              try {
                dbConnection = getConnection();
              }
              catch (NEDSSSystemException nsex) {
                logger.fatal("Error obtaining dbConnection " +
                             "while updating Entity_Locator_Participation", nsex);
                throw new NEDSSSystemException(nsex.toString());
              }

              try {
                //updates entity_locator_participation
                preparedStmt = dbConnection.prepareStatement(
                    UPDATE_ENTITY_LOCATOR_PARTICIPATION);

                preparedStmt.setString(i++, entityLocatorVO.getAddReasonCd());
                if (entityLocatorVO.getAddTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getAddTime());
                if (entityLocatorVO.getAddUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++, entityLocatorVO.getAddUserId().longValue());
                preparedStmt.setString(i++, entityLocatorVO.getCd());
                preparedStmt.setString(i++, entityLocatorVO.getCdDescTxt());
                preparedStmt.setString(i++, entityLocatorVO.getClassCd());
                preparedStmt.setString(i++, entityLocatorVO.getDurationAmt());
                preparedStmt.setString(i++, entityLocatorVO.getDurationUnitCd());
                if (entityLocatorVO.getFromTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getFromTime());
                preparedStmt.setString(i++, entityLocatorVO.getLastChgReasonCd());
                if (entityLocatorVO.getLastChgTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getLastChgTime());
                if (entityLocatorVO.getLastChgUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++,
                                       entityLocatorVO.getLastChgUserId().longValue());
                preparedStmt.setString(i++, entityLocatorVO.getLocatorDescTxt());
                preparedStmt.setString(i++, entityLocatorVO.getRecordStatusCd());
                if (entityLocatorVO.getRecordStatusTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getRecordStatusTime());
                if (entityLocatorVO.getStatusCd() == null)
                  preparedStmt.setString(i++, entityLocatorVO.getStatusCd());
                else
                  preparedStmt.setString(i++, entityLocatorVO.getStatusCd().trim());
                if (entityLocatorVO.getStatusTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getStatusTime());
                if (entityLocatorVO.getToTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getToTime());
                preparedStmt.setString(i++, entityLocatorVO.getUseCd());
                preparedStmt.setString(i++, entityLocatorVO.getUserAffiliationTxt());
                preparedStmt.setString(i++, entityLocatorVO.getValidTimeTxt());
                int verCtrlNbr = 1;
                if (entityLocatorVO.getVersionCtrlNbr() == null) {
                  logger.debug("VersionCtrlNbr is null *** ");
                  preparedStmt.setInt(i++, verCtrlNbr);
                }
                else {
                  logger.debug("VersionCtrlNbr exists");
                  preparedStmt.setInt(i++,
                                      entityLocatorVO.getVersionCtrlNbr().intValue());
                }
                if (entityLocatorVO.getAsOfDate() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, entityLocatorVO.getAsOfDate());

                preparedStmt.setLong(i++, entityLocatorVO.getEntityUid().longValue()); //22 Cannot be null
                preparedStmt.setLong(i++, entityLocatorVO.getLocatorUid().longValue()); //23 cannot be null

                resultCount = preparedStmt.executeUpdate();
              }

              catch (SQLException sex) {
                logger.fatal("SQLException while updating " +
                             "entity locator uid = " +
                             entityLocatorVO.getEntityUid(), sex);
                throw new NEDSSDAOSysException("Table Name : "+DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE+"  " + sex.getMessage(), sex);
              }
              catch (Exception e) {
            	  logger.fatal("Exception  = "+e.getMessage(), e);
            	  throw new NEDSSDAOSysException(e.toString());
              }

              finally {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
              }
            }
        }
   }

   /**
    * @methodname isValidData
    * Checks if certian attributes of the value object are not null
    * @param obj Object of type EntityLocatorParticipationDT
    * @param Obj
    * @return boolean
    * @roseuid 3C0D51CB0113
    */
   protected boolean isValidData(Object Obj)
   {

          boolean validData = true;
          try{
	          EntityLocatorParticipationDT entityLocatorVO = (EntityLocatorParticipationDT) Obj;
	
	          if ((entityLocatorVO.getAddReasonCd() == null) ||
	               (entityLocatorVO.getAddTime() == null)    ||
	               (entityLocatorVO.getAddUserId() == null)  ||
	               (entityLocatorVO.getCd()        == null)  ||
	               (entityLocatorVO.getClassCd()   == null)  ||
	               (entityLocatorVO.getRecordStatusCd() == null) ||
	               (entityLocatorVO.getStatusCd()       == null) ||
	               (entityLocatorVO.getUseCd()          == null)
	              )
	            validData = false;
	
	          if ((entityLocatorVO.getThePhysicalLocatorDT() == null) &&
	              (entityLocatorVO.getThePostalLocatorDT() == null) &&
	              (entityLocatorVO.getTheTeleLocatorDT() == null)
	              )
	              validData = false;
          }catch(Exception ex){
        	  logger.error("Exception  = "+ex.getMessage(), ex);
        	  throw new NEDSSSystemException(ex.toString(), ex);
          }
          return validData;
   }
}
/**
 * end of EntityLocatorParticipationDAOImpl class
 */
