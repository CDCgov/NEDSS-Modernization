//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\dao\\TeleLocatorDAOImpl.java
/**
* Name:		TeleLocatorDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               TeleLocator value object.
*               This class encapsulates all the JDBC calls required to
*               create,load and store values into the tele_locator table
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
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 * ... end imports
 */
public class TeleLocatorDAOImpl extends BMPBase
{
  //For logging
  static final LogUtils logger = new LogUtils(TeleLocatorDAOImpl.class.getName());
  public static final String SELECT_TELE_LOCATOR = "SELECT tele_locator_uid \"teleLocatorUid\", add_reason_cd \"addReasonCd\", "+
      "add_time \"addTime\", add_user_id \"addUserId\", cntry_cd \"cntryCd\", email_address \"emailAddress\", extension_txt \"extensionTxt\", "+
      "last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", phone_nbr_txt \"phoneNbrTxt\", "+
      "record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", url_address \"urlAddress\", user_affiliation_txt \"userAffiliationTxt\" "+
      "FROM Tele_locator WHERE tele_locator_uid = ?";
  public static final String INSERT_TELE_LOCATOR = "INSERT INTO Tele_locator (tele_locator_uid, add_reason_cd, "+
      "add_time, add_user_id, cntry_cd, email_address, extension_txt, last_chg_reason_cd, last_chg_time, last_chg_user_id, phone_nbr_txt, "+
      "record_status_cd, record_status_time, url_address, user_affiliation_txt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String UPDATE_TELE_LOCATOR = "UPDATE Tele_locator SET add_reason_cd = ?, add_time = ?, add_user_id = ?, cntry_cd = ?, "+
      " email_address = ?, extension_txt = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, phone_nbr_txt = ?, record_status_cd = ?, "+
      "record_status_time = ?, url_address = ?, user_affiliation_txt = ? WHERE tele_locator_uid = ?";

   /**
    * ///////////////////////////// Constructor(s) /////////////////////////////////
    * TeleLocatorDAOImpl is the default constructor requires no parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C0D5D2402FB
    */
   public TeleLocatorDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException
   {

   }

   /**
    * /////////////////////////////// Methods(s) ///////////////////////////////////
    * @methodname create
    * Creates an Object of type TeleLocatorDT and inserts a row into the tele_locator
    * table
    * Calls insertTeleLocator which does the JDBC call
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
    * @roseuid 3C0D5D250099
    */
   public long create(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {

	   try{
		   return (insertTeleLocator(obj));
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
    * Updates a row in the tele_locator table
    * Calls updateTeleLocator method which does the JDBC call
    * @param obj Object with modified information of type TeleLocatorDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D5D250270
    */
   public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
	   try{
		   updateTeleLocator(obj);
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
    * Selects a row in the tele_locator table
    * @param teleLocatorUID unique identifier of the row to be selected
    * @return Object of type TeleLocatorDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D5D260037
    */
   public Object loadObject(long teleLocatorUID) throws NEDSSDAOSysException, NEDSSSystemException
   {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        try {
            dbConnection = getConnection();
            TeleLocatorDT teleLocatorVO = new TeleLocatorDT();
            if (dbConnection == null)
            {
                System.err.println("Can not get connection.");
            }

            preparedStmt = dbConnection.prepareStatement(SELECT_TELE_LOCATOR);
            preparedStmt.setLong(1, teleLocatorUID);
            resultSet = preparedStmt.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            ResultSetUtils  populateBean = new ResultSetUtils();
            Class TeleLocatorClass = teleLocatorVO.getClass();
            return (populateBean.mapRsToBean(resultSet,rsMetaData,TeleLocatorClass)) ;

        }
        catch (ResultSetUtilsException resue)
        {
            logger.fatal("teleLocatorUID ="+teleLocatorUID+ " ResultSetUtilsException =" +resue.getMessage(), resue);

            return null;

        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while loading " +
                  "a TeleLocator, teleLocatorUID = " + teleLocatorUID, sex);

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
    * @methodname insertTeleLocator
    * Inserts a row into the tele_locator table
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
    * @roseuid 3C0D5D260267
    */
   private long insertTeleLocator(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {
        TeleLocatorDT teleLocatorVO = new TeleLocatorDT();
        teleLocatorVO = (TeleLocatorDT) obj;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        long teleLocatorUID =  -1;


        //Inserts a Tele Locator
        if(teleLocatorVO != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("SQLException while obtaining database connection " +
                                "for insertTeleLocator " , nsex);
                throw new NEDSSSystemException( nsex.getMessage());
            }

            try
            {
              //Inserts person home pst locator
              preparedStmt = dbConnection.prepareStatement(INSERT_TELE_LOCATOR);
              UidGeneratorHelper uidGen = new UidGeneratorHelper();
              teleLocatorUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
              int i = 1;
              preparedStmt.setLong(i++, teleLocatorUID);
              preparedStmt.setString(i++,teleLocatorVO.getAddReasonCd());
              if (teleLocatorVO.getAddTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                 preparedStmt.setTimestamp(i++,teleLocatorVO.getAddTime());
              if (teleLocatorVO.getAddUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
              else
                  preparedStmt.setLong(i++,teleLocatorVO.getAddUserId().longValue());
              preparedStmt.setString(i++,teleLocatorVO.getCntryCd());
              preparedStmt.setString(i++,teleLocatorVO.getEmailAddress());
              preparedStmt.setString(i++,teleLocatorVO.getExtensionTxt());
              preparedStmt.setString(i++,teleLocatorVO.getLastChgReasonCd());
              if (teleLocatorVO.getLastChgTime() == null)
              preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                 preparedStmt.setTimestamp(i++,teleLocatorVO.getLastChgTime());
              if (teleLocatorVO.getLastChgUserId() == null)
                 preparedStmt.setNull(i++,Types.INTEGER);
              else
                preparedStmt.setLong(i++,teleLocatorVO.getLastChgUserId().longValue());
              preparedStmt.setString(i++,teleLocatorVO.getPhoneNbrTxt());
              preparedStmt.setString(i++,teleLocatorVO.getRecordStatusCd());
              if (teleLocatorVO.getRecordStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                preparedStmt.setTimestamp(i++,teleLocatorVO.getRecordStatusTime());
              preparedStmt.setString(i++,teleLocatorVO.getUrlAddress());
              preparedStmt.setString(i++,teleLocatorVO.getUserAffiliationTxt());
              resultCount = preparedStmt.executeUpdate();
              teleLocatorVO.setTeleLocatorUid(new Long(teleLocatorUID));

            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting  TELE_LOCATOR_TABLE: \n " + INSERT_TELE_LOCATOR, sex);
                throw new NEDSSDAOSysException( "Table Name : Tele_locator  "+sex.getMessage(), sex);
             }
            catch(NEDSSSystemException nae)
            {
                logger.fatal("Error while generating UID ", nae);
                throw new NEDSSSystemException( nae.toString() );
            }
            catch(Exception e){
              logger.fatal("Exception while inserting " +
                    "TELE_LOCATOR_TABLE: \n " + INSERT_TELE_LOCATOR, e);
              throw new NEDSSDAOSysException( e.toString() );
            }


            finally
            {
            	closeStatement(preparedStmt);
            	releaseConnection(dbConnection);
            }

            teleLocatorVO.setItNew(false);
            teleLocatorVO.setItDirty(false);
          }// end if


          return teleLocatorUID;
   }

   /**
    * end of inserting tele locator
    * @methodname updateTeleLocator
    * Updates a row in the tele_locator table
    * @param obj Object with modified information of type TeleLocatorDT
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
    * @exception NEDSSDAOSysException This is an exception thrown in the event of
    * ir-recoverable system errors.
    * @exception NEDSSSystemException This is an exception thrown in the event of
    * application errors.
    * @exception NEDSSSystemException This is an exception thrown by the BMPBase
    * super class
    * @roseuid 3C0D5D2700A6
    */
   private void updateTeleLocator(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
   {

        TeleLocatorDT teleLocatorVO = new TeleLocatorDT();
        teleLocatorVO = (TeleLocatorDT) obj;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int i = 1;
        int resultCount = 0;

        //Updates Tele Locator
        if(teleLocatorVO != null)
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
                //updates tele_locator
                preparedStmt = dbConnection.prepareStatement(UPDATE_TELE_LOCATOR);



              preparedStmt.setString(i++,teleLocatorVO.getAddReasonCd());
              if (teleLocatorVO.getAddTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                preparedStmt.setTimestamp(i++,teleLocatorVO.getAddTime());
              if (teleLocatorVO.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
              else
                preparedStmt.setLong(i++,teleLocatorVO.getAddUserId().longValue());
              preparedStmt.setString(i++,teleLocatorVO.getCntryCd());
              preparedStmt.setString(i++,teleLocatorVO.getEmailAddress());
              preparedStmt.setString(i++,teleLocatorVO.getExtensionTxt());
              preparedStmt.setString(i++,teleLocatorVO.getLastChgReasonCd());
              if (teleLocatorVO.getLastChgTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                preparedStmt.setTimestamp(i++,teleLocatorVO.getLastChgTime());
			  if(teleLocatorVO.getLastChgUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			  else
                preparedStmt.setLong(i++,teleLocatorVO.getLastChgUserId().longValue());
              preparedStmt.setString(i++,teleLocatorVO.getPhoneNbrTxt());
              preparedStmt.setString(i++,teleLocatorVO.getRecordStatusCd());
              if (teleLocatorVO.getRecordStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                preparedStmt.setTimestamp(i++,teleLocatorVO.getRecordStatusTime());
              preparedStmt.setString(i++,teleLocatorVO.getUrlAddress());
              preparedStmt.setString(i++,teleLocatorVO.getUserAffiliationTxt());
              if (teleLocatorVO.getTeleLocatorUid() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
              else
               preparedStmt.setLong(i++,teleLocatorVO.getTeleLocatorUid().longValue());
              resultCount = preparedStmt.executeUpdate();
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    "tele locator uid = " +
                    teleLocatorVO.getTeleLocatorUid(), sex);
                throw new NEDSSDAOSysException("Table Name : Tele_locator  "+sex.getMessage(), sex);
            }

            finally
            {
                closeStatement(preparedStmt);
                preparedStmt = null;
                releaseConnection(dbConnection);
            }
        }
   }

   /**
    * @methodname isValidData
    * Checks if certian attributes of the value object are not null
    * @param obj Object of type TeleLocatorDT
    * @param Obj
    * @return boolean
    * @roseuid 3C0D5D270291
    */
   protected boolean isValidData(Object Obj)
   {
	   boolean validData = true;
	   try{
          
          TeleLocatorDT teleLocatorVO = (TeleLocatorDT) Obj;

          if ((teleLocatorVO.getAddReasonCd() == null) ||
               (teleLocatorVO.getAddTime() == null)    ||
               (teleLocatorVO.getAddUserId() == null)
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
 * end of TeleLocatorDAOImpl class
 */
