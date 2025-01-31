/**
* Name:		ConfirmationMethodDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               ConfirmationMethod value object in the PublicHealthCase entity bean.
*               This class encapsulates all the JDBC calls made by the PublicHealthCaseEJB
*               for a ConfirmationMethod object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PublicHealthCaseEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.publichealthcase.ejb.dao;


import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
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


public class ConfirmationMethodDAOImpl extends BMPBase
{
    private ConfirmationMethodDT confirmationMethodDT = null;
    private long phcUID = -1;
    public static final String INSERT_CONFIRMATION_METHOD =
           "INSERT INTO " + DataTables.CONFIRMATION_METHOD_TABLE
          +"(public_health_case_uid, confirmation_method_cd, confirmation_method_time, "
          +"confirmation_method_desc_txt) VALUES (?, ?, ?, ?)";
    public static final String SELECT_CONFIRMATION_METHODS =
           "SELECT public_health_case_uid \"publicHealthCaseUid\", "
          +"confirmation_method_cd \"confirmationMethodCd\", "
          +"confirmation_method_desc_txt \"confirmationMethodDescTxt\", "
          +"confirmation_method_time \"confirmationMethodTime\" FROM "
          + DataTables.CONFIRMATION_METHOD_TABLE
          + " WHERE public_health_case_uid = ?";
    public static final String SELECT_CONFIRMATION_METHOD_UID =
           "SELECT public_health_case_uid FROM "
          + DataTables.CONFIRMATION_METHOD_TABLE
          + " WHERE public_health_case_uid = ? and confirmation_method_cd=? ";
    public static final String UPDATE_CONFIRMATION_METHOD =
           "UPDATE " + DataTables.CONFIRMATION_METHOD_TABLE
           + " set confirmation_method_desc_txt = ?, "
           + "confirmation_method_time = ? WHERE public_health_case_uid = ? "
           + "AND confirmation_method_cd = ?";
    public static final String REMOVE_ALL_CONFIRMATION_METHODS =
           "DELETE  from " + DataTables.CONFIRMATION_METHOD_TABLE
           + "  where public_health_case_uid = ? ";

    public static final String UNKNOWN ="Unknown";
    //For logging
    static final LogUtils logger = new LogUtils(ConfirmationMethodDAOImpl.class.getName());

    public ConfirmationMethodDAOImpl()
    {
    }

    public long create(long phcUID, Collection<Object> coll) throws NEDSSSystemException
    {
        if(!coll.isEmpty())
        {
            Iterator<Object> anIterator = null;
            ArrayList<Object>  methodList = (ArrayList<Object> )coll;

            try
            {
                /**
                * Inserts confirmation methods
                */
                anIterator = methodList.iterator();

                while(anIterator.hasNext())
                {
                    logger.debug("Number of elements: " + methodList.size());
                    ConfirmationMethodDT confirmationMethod = (ConfirmationMethodDT)anIterator.next();

                    if (confirmationMethod != null)
                    {
                        insertConfirmationMethod(phcUID, confirmationMethod);
                        confirmationMethod.setPublicHealthCaseUid(new Long(phcUID));
                        confirmationMethod.setItNew(false);
                        confirmationMethod.setItDirty(false);
                    }
                    else
                    {
                        continue;
                    }
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while inserting " +
                            "confirmation methods into CONFIRMATION_METHOD_TABLE: \n", ex.getMessage(), ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
        }
        return phcUID;
    }

    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
    logger.debug("Start DAO store().");
        /* this is a temporary solution, where we delete all existing confirmationmethods and add new selected.
        Later on front end should set delete flag to one which needs to be deleted, set new to one
        which are to be inserted and set dirty false for which are to be retained */
        if(!coll.isEmpty()){
          Iterator<Object> iter = coll.iterator();
          if (iter.hasNext()){
            ConfirmationMethodDT confirmationMethod = (ConfirmationMethodDT)iter.next();
            removeall(confirmationMethod.getPublicHealthCaseUid()); // remove all existing confirmation methods for this phcuid
          }
        }
    logger.debug("All confirmation methods are removed.");
        if(!coll.isEmpty())
        {
            try
            {
                for(Iterator<Object> anIterator = coll.iterator(); anIterator.hasNext(); )
                {
                    ConfirmationMethodDT confirmationMethod = (ConfirmationMethodDT)anIterator.next();
                    // For time being we will always insert as we are deleting all rows before.
                    insertConfirmationMethod((confirmationMethod.getPublicHealthCaseUid()).longValue(), confirmationMethod);
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "phc confirmation methods, \n"+ex.getMessage(), ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
        }
    }

    public Collection<Object> load(long phcUID) throws
                NEDSSSystemException
    {
        /**
         * Selects ConfirmationMethodDT collection
         */
        Collection<Object> confMethodColl = selectConfirmationMethods(phcUID);
        return confMethodColl;
    }

    public void remove(Long PublicHealthCaseUID) throws  NEDSSSystemException
    {
    	 removeall( PublicHealthCaseUID);
    }

    public Long findByPrimaryKey(long phcUID, String confirmationMethodCd) throws
		NEDSSSystemException
    {
	    	try{
	        if (confirmationMethodExists(phcUID, confirmationMethodCd))
	            return (new Long(phcUID));
	        else
	            logger.error("Primary key not found in PUBLIC_HEALTH_CASE_TABLE:"
	            + phcUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    protected boolean confirmationMethodExists (long phcUID, String confirmationMethodCd) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ConfirmationMethodDAOImpl.SELECT_CONFIRMATION_METHOD_UID);
logger.debug("phcUID = " + phcUID);
            preparedStmt.setLong(1, phcUID);
            preparedStmt.setString(2, confirmationMethodCd);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                phcUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing confirmation method's uid in CONFIRMATION_METHOD_TABLE -> "
                            + phcUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing confirmation method's uid -> " + phcUID, nsex);
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

    private void insertConfirmationMethod(long phcUID, ConfirmationMethodDT confirmationMethod)
            throws NEDSSSystemException
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
            logger.fatal("Error obtaining dbConnection " +
                "for inserting a ConfirmationMethodDT object", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(ConfirmationMethodDAOImpl.INSERT_CONFIRMATION_METHOD);

            int i = 1;

logger.debug("PublicHealthCase Uid = " + phcUID);
            preparedStmt.setLong(i++, phcUID);
            if(confirmationMethod.getConfirmationMethodCd() == null)
                preparedStmt.setString(i++,ConfirmationMethodDAOImpl.UNKNOWN);
            else
                preparedStmt.setString(i++, confirmationMethod.getConfirmationMethodCd());
            if(confirmationMethod.getConfirmationMethodTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, confirmationMethod.getConfirmationMethodTime());
            preparedStmt.setString(i++, confirmationMethod.getConfirmationMethodDescTxt());

            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                    logger.error
                        ("Error: none or more than one confirmation methods inserted at a time, " +
                        "resultCount = " + resultCount);
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while inserting " +
                    "a confirmation method into CONFIRMATION_METHOD_TABLE: \n"+sex.getMessage(), sex);
            throw new NEDSSDAOSysException( sex.toString() );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    private void updateConfirmationMethod(ConfirmationMethodDT confirmationMethod) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        if(confirmationMethod != null)
        {
          try
          {
            dbConnection = getConnection();
          }
          catch(NEDSSSystemException nsex)
          {
            logger.fatal("Error obtaining dbConnection " +
                "for updating a confirmation method"+nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.toString());
          }

        /**
         * Updates a confirmation method
         */

           try
            {
                preparedStmt = dbConnection.prepareStatement(ConfirmationMethodDAOImpl.UPDATE_CONFIRMATION_METHOD);

                int i = 1;

                preparedStmt.setString(i++, confirmationMethod.getConfirmationMethodDescTxt());
                if(confirmationMethod.getConfirmationMethodTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, confirmationMethod.getConfirmationMethodTime());
                preparedStmt.setLong(i++, (confirmationMethod.getPublicHealthCaseUid()).longValue());
                preparedStmt.setString(i++, confirmationMethod.getConfirmationMethodCd());


                resultCount = preparedStmt.executeUpdate();
    logger.debug("Done updating a confirmation Mmthod");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one confirmation method updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    "a confirmation method, \n"+sex.getMessage(), sex);
                throw new NEDSSDAOSysException( sex.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating confirmation method table

    private Collection<Object> selectConfirmationMethods (long phcUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ConfirmationMethodDT confirmationMethod = new ConfirmationMethodDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectConfirmationMethods() " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects public health case's confirmation methods
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(ConfirmationMethodDAOImpl.SELECT_CONFIRMATION_METHODS);
            preparedStmt.setLong(1, phcUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  methodList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
            methodList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, confirmationMethod.getClass(), methodList);

            /*if (methodList.isEmpty())
                throw new NEDSSPublicHealthCaseDAOAppException("No entity ids for this PublicHealthCaseUID: " +
                                   PublicHealthCaseUID);
            else*/
            for(Iterator<Object> anIterator = methodList.iterator(); anIterator.hasNext(); )
            {
                ConfirmationMethodDT reSetConfirmationMethod = (ConfirmationMethodDT)anIterator.next();
                reSetConfirmationMethod.setItNew(false);
                reSetConfirmationMethod.setItDirty(false);
                reSetList.add(reSetConfirmationMethod);
            }

            logger.debug("Return confirmation method collection");

            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "confirmation method collection; uid = " + phcUID , se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting confirmation methods."+rsuex.getMessage(), rsuex);
            throw new NEDSSDAOSysException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "confirmation methods; uid = " + phcUID, ex);
            throw new NEDSSDAOSysException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selection confirmation methods
  public void removeall(Long phcuid){
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    try{
           dbConnection = getConnection();
    }
    catch(NEDSSSystemException nsex){
            logger.fatal("Error obtaining dbConnection " +
                "for removing confirmation methods", nsex);
            throw new NEDSSSystemException(nsex.toString());
    }
    /**
      * Remove confirmation methods
     */
      try{
         preparedStmt = dbConnection.prepareStatement(ConfirmationMethodDAOImpl.REMOVE_ALL_CONFIRMATION_METHODS );
         int i = 1;
         preparedStmt.setLong(i++, phcuid.longValue());
         preparedStmt.executeUpdate();
    logger.debug("Done updating a confirmation Mmthod");
      }
      catch(SQLException sex){
        logger.fatal("SQLException while deleting confirmation methods, \n"+sex.getMessage(), sex);
                throw new NEDSSDAOSysException( sex.toString() );
      }
      finally{
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
      }
    }

    /*
    protected boolean isValidData (PublicHealthCaseValueObject pvo)
    {
        if ( (pvo.getPublicHealthCaseUID() == 0) || (pvo.getPublicHealthCaseLastUpdate() == null) ||
             (pvo.getPublicHealthCase().getPublicHealthCaseFirstName() == null) ||
             (pvo.getPublicHealthCase().getPublicHealthCaseMiddleName() == null) ||
             (pvo.getPublicHealthCase().getPublicHealthCaseLastName() == null) ||
             /*(pvo.getPublicHealthCaseBirthDate() == null) || (pvo.getPublicHealthCaseCalcDOB() == null) ||
             (pvo.getPublicHealthCaseAge() == null) || (pvo.getPublicHealthCaseCalcAge() == null) ||
             (pvo.getPublicHealthCaseAgeCategory() == null) || (pvo.getPublicHealthCaseAgeType() == null) ||
             (pvo.getPublicHealthCaseMultBirthStat() == null) || (pvo.getPublicHealthCaseBirthOrder() == null) ||
             (pvo.getMultiBirthIndicator() == null) || (pvo.getPublicHealthCaseBirthRegNumber() == null) ||
             (pvo.getPublicHealthCaseMotherId() == null) || (pvo.getPublicHealthCaseBirthLocation() == null) ||
             (pvo.getPublicHealthCaseSexAtBirth() == null) || (pvo.getPublicHealthCaseStateOfBirth() == null) ||
             (pvo.getPublicHealthCaseDeathDate() == null) || (pvo.getPublicHealthCaseSexCd() == null) ||
             (pvo.getPublicHealthCaseDeathInd() == null) || (pvo.getPublicHealthCaseAliveStatus() == null) ||
             (pvo.getPublicHealthCaseBirthDate() == null) || (pvo.getPublicHealthCaseEthinictyCd() == null) ||
             (pvo.getPublicHealthCaseCurPrimaryLanguage() == null) || (pvo.getPublicHealthCaseCurOccupation() == null) ||
             (pvo.getPublicHealthCaseCurOccupationCode() == null) || (pvo.getPublicHealthCaseMaritalStatus() == null) ||
             (pvo.getPublicHealthCaseSSN() == null) || (pvo.getPublicHealthCaseDLN() == null) ||
             (pvo.getPublicHealthCaseGender() == null) || (pvo.getPublicHealthCaseAccountNumber() == null) ||
             (pvo.getPublicHealthCaseMedicaidNumber() == null) || (pvo.getPublicHealthCaseCurSex() == null) ||
             (pvo.getChildrenInResidence() == null) || (pvo.getAdultsInResidence() == null) ||
             (pvo.getEducationLevel() == null) || (pvo.getPublicHealthCase().getPublicHealthCaseFromDtTm() == null) ||
             (pvo.getPublicHealthCase().getPublicHealthCaseToDtTm() == null) ||
             (pvo.getPublicHealthCaseCurHomeAddress().getPublicHealthCaseCurAddr1() == null) ||
             (pvo.getPublicHealthCaseCurHomeAddress().getPublicHealthCaseCurCity() == null) ||
             (pvo.getPublicHealthCaseCurHomeAddress().getPublicHealthCaseCurState() == null) ||
             (pvo.getPublicHealthCaseCurHomeAddress().getState() == null) ||
             (pv.getPublicHealthCaseCurHomeAddress().getPublicHealthCaseCurZip() == null) ||
             (pvo.getPublicHealthCaseCurHomeAddress().getPublicHealthCaseCurCounty() == null) ||*/
         //    (pvo.getPublicHealthCaseCurHomeAddress().getPublicHealthCaseCurCountry() == null))
           // return false;
       // else
         //   return true;
   // }*/

/*
    public  Connection getConnection(){
      Connection dbConnection = null;
      try{
        logger.debug("test21");
        Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
        logger.debug("test1");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "nbs_ods", "ods");
      logger.debug("test2");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
    }
*/
   public static void main(String argments[])
   {
   /* try{
    NedssUtils nedssUtils = new NedssUtils();
        //UserProfile userProfile = new UserProfile();

        Collection<Object>  group = new ArrayList<Object> ();
	NBSSecurityObj securityObj = null;
	Object objref = nedssUtils.lookupBean("MainControl");
    MainSessionCommandHome home =(MainSessionCommandHome)PortableRemoteObject.narrow(objref,gov.cdc.nedss.ejb.mainsessioncommand.bean.MainSessionCommandHome.class);
    MainSessionCommand msc = home.create();
    PublicHealthCaseVO  publicHealthCaseVO =  new PublicHealthCaseVO();
    PublicHealthCaseDT  publicHealthCaseDT =  new PublicHealthCaseDT();
    publicHealthCaseDT.setPublicHealthCaseUid(new Long(-20));

     ConfirmationMethodDAOImpl dao = new ConfirmationMethodDAOImpl();
     ConfirmationMethodDT cmDT1 = new ConfirmationMethodDT();
     ConfirmationMethodDT cmDT2 = new ConfirmationMethodDT();
      ArrayList<Object> cmDTColl = new ArrayList<Object> ();
      cmDTColl.add(cmDT1);
      cmDTColl.add(cmDT2);

     //dao.create(123456,cmDTColl);

     dao.selectConfirmationMethods(605131133);

     }catch(Exception e){
      e.printStackTrace();
     }

      PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
      PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
      ArrayList<Object> cmDTColl = new ArrayList<Object> ();
      ConfirmationMethodDT cmDT1 = new ConfirmationMethodDT();
      ConfirmationMethodDT cmDT2 = new ConfirmationMethodDT();
      //phcDT.setAddUserId(new Long(100));
      cmDTColl.add(cmDT1);
      cmDTColl.add(cmDT2);
      phcVO.setThePublicHealthCaseDT(phcDT);
      phcVO.setTheConfirmationMethodDTCollection(cmDTColl);
      try
      {
          PublicHealthCaseDAOImpl phcDAO = new PublicHealthCaseDAOImpl();
          phcDAO.create(phcVO);
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      } */
   }// End of main method

}//end of PublicHealthCaseDAOImpl class
