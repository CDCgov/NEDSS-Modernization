//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\dao\\PhysicalLocatorDAOImpl.java
/**
* Name:		PhysicalLocatorDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               PhysicalLocator value object.
*               This class encapsulates all the JDBC calls required to
*               create,load and store values into the physical_locator table
*
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Venu Pannirselvam
* @modified     Add modification Information here. Each person should add ones own.
* @version	1.0 10/04/2001
*/
package gov.cdc.nedss.locator.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
/**
 * ... end imports
 */
public class PhysicalLocatorDAOImpl extends BMPBase
{
   //For logging
  static final LogUtils logger = new LogUtils(PhysicalLocatorDAOImpl.class.getName());
  public static final String SELECT_PHYSICAL_LOCATOR = "SELECT   physical_locator_uid \"physicalLocatorUid\", "+
      "add_reason_cd \"addReasonCd\", add_time \"addTime\", add_User_id \"addUserId\",last_chg_reason_cd \"lastChgReasonCd\", "+
      "image_txt \"imageTxt\", locator_txt \"locatorTxt\", user_affiliation_txt \"userAffiliationTxt\", record_status_cd \"recordStatusCd\", "+
      "record_status_time \"recordStatusTime\" FROM " + DataTables.PHYSICAL_LOCATOR_TABLE + " WHERE physical_locator_uid = ?";
  public static final String INSERT_PHYSICAL_LOCATOR = "INSERT INTO Physical_locator (physical_locator_uid, add_reason_cd, add_time, "+
  "add_user_id, image_txt, last_chg_reason_cd, last_chg_time, last_chg_user_id, locator_txt, record_status_cd, record_status_time, "+
  "user_affiliation_txt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String UPDATE_PHYSICAL_LOCATOR = "UPDATE Physical_locator SET add_reason_cd = ?, add_time = ?, "+
      "add_user_id = ?, image_txt = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, locator_txt = ?, "+
      "record_status_cd = ?, record_status_time = ?, user_affiliation_txt = ? WHERE physical_locator_uid = ?";

   /**
    * ///////////////////////////// Constructor(s) /////////////////////////////////
    * PhysicalLocatorDAOImpl is the default constructor requires no parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C0D5461029B
    */
   public PhysicalLocatorDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException
   {

   }

   /**
    * /////////////////////////////// Methods(s) ///////////////////////////////////
    * @methodname create
    * Creates an Object of type PhysicalLocatorDT and inserts a row into the
    * physical_locator table
    * Calls insertPhysicalLocator which does the JDBC call
    * @param obj Object to be created
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
    * @roseuid 3C0D54620026
    */
   public long create(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
		   return (insertPhysicalLocator(obj));
	   }catch(NEDSSDAOSysException ex){
		   logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		   throw new NEDSSDAOSysException(ex.toString());
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
   }

   /**
    * @methodname store
    * Updates a row in the physical_locator table
    * Calls updatePhysicalLocator method which does the JDBC call
    * @param obj Object with modified information of type PhysicalLocatorDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D54620293
    */
   public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
		   updatePhysicalLocator(obj);
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
    * Selects a row in the physical_locator table
    * @param physicalLocatorUID unique identifier of the row to be selected
    * @return Object of type PhysicalLocatorDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D546300C8
    */
   public Object loadObject(long physicalLocatorUID) throws NEDSSDAOSysException, NEDSSSystemException
   {
        ResultSet resultSet = null;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        try {
            dbConnection = getConnection();
            PhysicalLocatorDT physicalLocatorVO = new PhysicalLocatorDT();

            preparedStmt = dbConnection.prepareStatement(SELECT_PHYSICAL_LOCATOR);
            preparedStmt.setLong(1, physicalLocatorUID);
            resultSet = preparedStmt.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            ResultSetUtils  populateBean = new ResultSetUtils();
            Class PhysicalLocatorClass = physicalLocatorVO.getClass();
            return (populateBean.mapRsToBean(resultSet,rsMetaData,PhysicalLocatorClass)) ;

        }
         catch (ResultSetUtilsException resue)
        {

        	 logger.fatal("ResultSetUtilsException  = "+resue.getMessage(), resue);
        	 return null;

        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while loading " +
                  "a PhysicalLocator, physicalLocatorUID = " + physicalLocatorUID, sex);

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
    * @methodname insertPhysicalLocator
    * Inserts a row into the physical_locator table
    * @param obj Object to be created
    * @return the unique id of the created object
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
    * @roseuid 3C0D546302BC
    */
   private long insertPhysicalLocator(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
        PhysicalLocatorDT physicalLocatorVO = new PhysicalLocatorDT();
        physicalLocatorVO = (PhysicalLocatorDT) obj;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        long physicalLocatorUID =  -1;


        //Inserts a Physical Locator
        if(physicalLocatorVO != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("SQLException while obtaining database connection " +
                                "for insertPhysicalLocator " , nsex);
                throw new NEDSSSystemException( nsex.getMessage());
            }

            try
            {
                  //Inserts person home pst locator
                  preparedStmt = dbConnection.prepareStatement(INSERT_PHYSICAL_LOCATOR);
                  UidGeneratorHelper uidGen = new UidGeneratorHelper();
                  physicalLocatorUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                  int i = 1;

                preparedStmt.setLong(i++,physicalLocatorUID);
                preparedStmt.setString(i++,physicalLocatorVO.getAddReasonCd());
                if (physicalLocatorVO.getAddTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,physicalLocatorVO.getAddTime());
                if (physicalLocatorVO.getAddUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++,physicalLocatorVO.getAddUserId().longValue());
                
                //BLOB specific changes
                if(physicalLocatorVO.getImageTxt()!=null ){
                		InputStream fis = new ByteArrayInputStream(physicalLocatorVO.getImageTxt());
                        preparedStmt.setBinaryStream(i++,fis, physicalLocatorVO.getImageTxt().length);
                }else{
                	preparedStmt.setNull(i++, Types.BINARY);
                }
                
                preparedStmt.setString(i++,physicalLocatorVO.getLastChgReasonCd());
                if (physicalLocatorVO.getLastChgTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,physicalLocatorVO.getLastChgTime());
                if (physicalLocatorVO.getLastChgUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++,physicalLocatorVO.getLastChgUserId().longValue());
                preparedStmt.setString(i++,physicalLocatorVO.getLocatorTxt());
                preparedStmt.setString(i++,physicalLocatorVO.getRecordStatusCd());
                if (physicalLocatorVO.getRecordStatusTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,physicalLocatorVO.getRecordStatusTime());
                preparedStmt.setString(i++,physicalLocatorVO.getUserAffiliationTxt());

                resultCount = preparedStmt.executeUpdate();

            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                    "PHYSICAL_LOCATOR_TABLE: \n " + INSERT_PHYSICAL_LOCATOR
                    , sex);
                throw new NEDSSDAOSysException( "Table Name : Physical_locator  "+ sex.getMessage(), sex);
             }
            catch(NEDSSSystemException nae)
             {
                logger.fatal("Error while generating UID "+nae.getMessage(), nae);
                throw new NEDSSSystemException( nae.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while generating UID "+ex.getMessage(), ex);
                throw new NEDSSSystemException( ex.toString() );
            }


       finally
                  {
                      closeStatement(preparedStmt);
                      releaseConnection(dbConnection);
                  }

          }// end if
          logger.debug("Phys Locator UID = " + physicalLocatorUID);
          physicalLocatorVO.setPhysicalLocatorUid(new Long(physicalLocatorUID));
          physicalLocatorVO.setItNew(false);
          physicalLocatorVO.setItDirty(false);
          return physicalLocatorUID;
   }

   /**
    * end of inserting physical locator
    * @methodname updatePhysicalLocator
    * Updates a row in the physical_locator table
    * @param obj Object with modified information of type PhysicalLocatorDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D5464012D
    */
   private void updatePhysicalLocator(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {

        PhysicalLocatorDT physicalLocatorVO = new PhysicalLocatorDT();
        physicalLocatorVO = (PhysicalLocatorDT) obj;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int i = 1;
        int resultCount = 0;

        //Updates Physical Locator
        if(physicalLocatorVO != null)
        {
            try
            {
               dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "while updating person address", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                //updates physical_locator
                preparedStmt = dbConnection.prepareStatement(UPDATE_PHYSICAL_LOCATOR);

                preparedStmt.setString(i++,physicalLocatorVO.getAddReasonCd());
                if (physicalLocatorVO.getAddTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,physicalLocatorVO.getAddTime());
                if (physicalLocatorVO.getAddUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++,physicalLocatorVO.getAddUserId().longValue());
                // BLOB specific changes
                if(physicalLocatorVO.getImageTxt()!=null ){
                		InputStream fis = new ByteArrayInputStream(physicalLocatorVO.getImageTxt());
                        preparedStmt.setBinaryStream(i++,fis, physicalLocatorVO.getImageTxt().length);
                }else{
                	preparedStmt.setNull(i++, Types.BINARY);
                }
                preparedStmt.setString(i++,physicalLocatorVO.getLastChgReasonCd());
                if (physicalLocatorVO.getLastChgTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,physicalLocatorVO.getLastChgTime());
                if (physicalLocatorVO.getLastChgUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++,physicalLocatorVO.getLastChgUserId().longValue());
                preparedStmt.setString(i++,physicalLocatorVO.getLocatorTxt());
                preparedStmt.setString(i++,physicalLocatorVO.getRecordStatusCd());
                if (physicalLocatorVO.getRecordStatusTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++,physicalLocatorVO.getRecordStatusTime());
                preparedStmt.setString(i++,physicalLocatorVO.getUserAffiliationTxt());
                if (physicalLocatorVO.getPhysicalLocatorUid() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++,physicalLocatorVO.getPhysicalLocatorUid().longValue());

                resultCount = preparedStmt.executeUpdate();
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    "physical locator uid = " +
                    physicalLocatorVO.getPhysicalLocatorUid() , sex);
                throw new NEDSSDAOSysException("Table Name : Physical_locator  "+ sex.getMessage(), sex);
            }

         finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        }
   }

   /**
    * @methodname isValidData
    * Checks if certian attributes of the value object are not null
    * @param obj Object of type PhysicallLocatorDT
    * @param Obj
    * @return boolean
    * @roseuid 3C0D5464034A
    */
   protected boolean isValidData(Object Obj)
   {
          boolean validData = true;
          try{
	          PhysicalLocatorDT physicalLocatorVO = (PhysicalLocatorDT) Obj;
	
	          if ((physicalLocatorVO.getAddReasonCd() == null) ||
	               (physicalLocatorVO.getAddTime() == null)    ||
	               (physicalLocatorVO.getAddUserId() == null)
	              )
	          validData = false;
          }catch(Exception ex){
        	  logger.fatal("Exception = "+ex.getMessage(), ex);
        	  throw new NEDSSSystemException(ex.toString());
          }
          return validData;
   }
}
/**
 * end of PhysicalLocatorDAOImpl class
 */
