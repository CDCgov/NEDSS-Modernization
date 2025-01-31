package gov.cdc.nedss.act.observation.ejb.dao;


import java.util.*;
import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.sql.SQLException;
import java.math.BigDecimal;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.act.sqlscript.*;

// for testing:
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ObsValueNumericDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(ObsValueNumericDAOImpl.class.getName());

    public ObsValueNumericDAOImpl()
    {
    }

    public long create(long observationUid, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        insertObsValueNumerics(observationUid, coll);
	
	        return observationUid;
    	}catch(Exception ex){
    		logger.fatal("observationUid: "+observationUid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
        updateObsValueNumerics(coll);
    }
    public void remove(long observationUID) throws  NEDSSSystemException
    {
        removeObsValueNumeric(observationUID);
    }

    public Collection<Object> load(long observationUid) throws
		NEDSSSystemException
    {
        Collection<Object> coll = selectObsValueNumerics(observationUid);
        return coll;
    }

    public Long findByPrimaryKey(long observationUid) throws
		NEDSSSystemException
    {
        if (obsValueNumericExists(observationUid))
            return (new Long(observationUid));
        else
            logger.error("Primary key not found in obsValueNumeric table:"
            + observationUid);
            return null;
    }


    private void insertObsValueNumerics(long observationUid,  Collection<Object> coll)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;
        ArrayList<Object>  collList = (ArrayList<Object> )coll;

        try
        {
            anIterator = collList.iterator();

            while(anIterator.hasNext())
            {
                ObsValueNumericDT obsValueNumeric = (ObsValueNumericDT)anIterator.next();

                if (obsValueNumeric != null)
                {
                    insertObsValueNumeric(observationUid, obsValueNumeric);

                    obsValueNumeric.setObservationUid(new Long(observationUid));
                }
            }
        }
        catch(Exception ex)
        {
            logger.fatal("observationUid: "+observationUid+" Exception while inserting obsValueNumeric \n", ex);
            throw new NEDSSDAOSysException( ex.toString() );
        }
    logger.debug("Done inserting all obsValueNumeric");
    }//end of inserting person names

    private void insertObsValueNumeric(long observationUid,
            ObsValueNumericDT obsValueNumeric)
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
            logger.fatal("Error obtaining dbConnection for updating obsValueNumeric", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {

            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBS_VALUE_NUMERIC);

            int i = 1;


        preparedStmt.setLong(i++, observationUid);
        if (obsValueNumeric.getObsValueNumericSeq() == null)
	    preparedStmt.setNull(i++, Types.INTEGER);
	else
            preparedStmt.setInt(i++,obsValueNumeric.getObsValueNumericSeq().intValue());
        preparedStmt.setString(i++,obsValueNumeric.getHighRange());
        preparedStmt.setString(i++,obsValueNumeric.getLowRange());
        preparedStmt.setString(i++,obsValueNumeric.getComparatorCd1());
        if (obsValueNumeric.getNumericValue1() == null)
	    preparedStmt.setNull(i++, Types.DOUBLE);
	else
            preparedStmt.setDouble(i++,obsValueNumeric.getNumericValue1().doubleValue());
        if (obsValueNumeric.getNumericValue2() == null)
	    preparedStmt.setNull(i++, Types.DOUBLE);
	else
            preparedStmt.setDouble(i++,obsValueNumeric.getNumericValue2().doubleValue());
        preparedStmt.setString(i++,obsValueNumeric.getNumericUnitCd());
        preparedStmt.setString(i++,obsValueNumeric.getSeparatorCd());
        if (obsValueNumeric.getNumericScale1() == null)
    	    preparedStmt.setNull(i++, Types.SMALLINT);
    	else
                preparedStmt.setInt(i++,obsValueNumeric.getNumericScale1().intValue());
        if (obsValueNumeric.getNumericScale2() == null)
    	    preparedStmt.setNull(i++, Types.SMALLINT);
    	else
                preparedStmt.setInt(i++,obsValueNumeric.getNumericScale2().intValue());
            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                    logger.error
                        ("Error: none or more than one obsValueNumeric inserted at a time, " +
                        "resultCount = " + resultCount);
            else
            {
                obsValueNumeric.setItNew(false);
                obsValueNumeric.setItDirty(false);
                obsValueNumeric.setItDelete(false);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("observationUid: "+observationUid+" SQLException while inserting obsValueNumeric \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_NUMERIC_TABLE + "  For observationUid: "+observationUid+"  "+sex.toString(), sex );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    private void updateObsValueNumerics(Collection<Object> coll) throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;

        if(coll != null)
        {
            try
            {
                for(anIterator = coll.iterator(); anIterator.hasNext(); )
                {
                    ObsValueNumericDT obsValueNumeric = (ObsValueNumericDT)anIterator.next();
                    if(obsValueNumeric != null  && obsValueNumeric.getObservationUid() != null)
                    {
                      //logger.warn("Error: Empty obsValueNumeric collection");
                      //in the order of precedence
                      if (obsValueNumeric.isItDelete())
                      {
                          removeObsValueNumeric (obsValueNumeric.getObservationUid().longValue());
                      }
                      else if (obsValueNumeric.isItNew())
                      {
                          insertObsValueNumeric((obsValueNumeric.getObservationUid()).longValue(), obsValueNumeric);
                      }
                      else if (obsValueNumeric.isItDirty())
                      {
                        updateObsValueNumeric(obsValueNumeric);
                      }
                  }
                  else
                    logger.warn("Error: Empty obsValueNumeric collection");
              }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating obsValueNumeric, \n", ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
        }
    }//end of updating obsValueNumeric table

    private void updateObsValueNumeric(ObsValueNumericDT obsValueNumeric) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a obsValueNumeric
         */

         if(obsValueNumeric != null  && obsValueNumeric.getObservationUid() != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection for updating obsValueNumeric", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBS_VALUE_NUMERIC);

                int i = 1;

                preparedStmt.setString(i++,obsValueNumeric.getHighRange());
                preparedStmt.setString(i++,obsValueNumeric.getLowRange());
                preparedStmt.setString(i++,obsValueNumeric.getComparatorCd1());
                if (obsValueNumeric.getNumericValue1() == null)
	            preparedStmt.setNull(i++, Types.DOUBLE);
	        else
	            preparedStmt.setDouble(i++,obsValueNumeric.getNumericValue1().doubleValue());
                if (obsValueNumeric.getNumericValue2() == null)
	            preparedStmt.setNull(i++, Types.DOUBLE);
	        else
	            preparedStmt.setDouble(i++,obsValueNumeric.getNumericValue2().doubleValue());
                preparedStmt.setString(i++,obsValueNumeric.getNumericUnitCd());
                preparedStmt.setString(i++,obsValueNumeric.getSeparatorCd());
                if (obsValueNumeric.getNumericScale1() == null)
            	    preparedStmt.setNull(i++, Types.SMALLINT);
            	else
                        preparedStmt.setInt(i++,obsValueNumeric.getNumericScale1().intValue());
                if (obsValueNumeric.getNumericScale2() == null)
            	    preparedStmt.setNull(i++, Types.SMALLINT);
            	else
                        preparedStmt.setInt(i++,obsValueNumeric.getNumericScale2().intValue());
                if (obsValueNumeric.getObservationUid() == null)
	            preparedStmt.setNull(i++, Types.INTEGER);
	        else
	            preparedStmt.setLong(i++,obsValueNumeric.getObservationUid().longValue());
                if (obsValueNumeric.getObsValueNumericSeq() == null)
	            preparedStmt.setNull(i++, Types.INTEGER);
	        else
	            preparedStmt.setInt(i++,obsValueNumeric.getObsValueNumericSeq().intValue());

                resultCount = preparedStmt.executeUpdate();

                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one obsValueNumeric updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating  obsValueNumeric, \n", sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_NUMERIC_TABLE +"  "+sex.toString(), sex);
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating obsValueNumeric table

    protected boolean obsValueNumericExists (long observationUid) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_NUMERIC_UID);
            preparedStmt.setLong(1, observationUid);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                observationUid =   resultSet.getLong(1);

                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an existing observation uid  -&gt; " + observationUid, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an existing observation uid -&gt; " + observationUid, nsex);
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

    private Collection<Object> selectObsValueNumerics (long observationUid) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ObsValueNumericDT obsValueNumeric= new ObsValueNumericDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                        "for selectPersonNames " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects obsValueNumeric
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_NUMERIC);

            preparedStmt.setLong(1, observationUid);

            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  itemList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            itemList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueNumeric.getClass(), itemList);


           /* if (pnList.isEmpty())
                throw new NEDSSObservationDAOAppException("No record for this observationUid: " + observationUid);
            else */

            for(Iterator<Object> anIterator = itemList.iterator(); anIterator.hasNext(); )
            {
                ObsValueNumericDT reSetName = (ObsValueNumericDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("return obsValueNumeric collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting obsValueNumeric collection; uid = " + observationUid, se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting obsValueNumeric.", rsuex);
            throw new NEDSSDAOSysException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection observation ; uid = " + observationUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private void removeObsValueNumeric (long observationUID) throws NEDSSSystemException
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
                            "for deleting observation Value Numeric " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Deletes Observation Value Numeric
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBS_VALUE_NUMERIC);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete observation Value Numeric from OBS_VALUE_NUMERIC_TABLE !! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "observation value Numeric; id = " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing ObsValueNumeric


  /*  protected synchronized Connection getConnection(){
      Connection conn = null;
      try{
        Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa", "sapasswd");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
    }
*/
    public static void main(String args[]){
      logger.debug("ObservationDAOImpl - Doing the main thing");

      try{

        // To test update
        ObsValueNumericDAOImpl obsDAOI = new ObsValueNumericDAOImpl();

  /*       Long uid = new Long(10);
        Long person_uid = new Long(1);

        ObsValueNumericDT obsDT = new ObsValueNumericDT();

        obsDT.setObservationUid(new Long(47));
        obsDT.setObsValueNumericSeq(new Integer(4));

        obsDT.setHighRange("very high");
        obsDT.setLowRange("veryverylow?");
        obsDT.setComparatorCd1("Cval");
        obsDT.setNumericValue1(new Long(99));
        obsDT.setNumericValue2(new Long(43));
        obsDT.setNumericUnitCd("please");
        obsDT.setSeparatorCd("yo");
        obsDT.setItNew(false);
        obsDT.setItDirty(true);

        Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
        ObsValueNumericDAOImpl Obs = new   ObsValueNumericDAOImpl();
        Obs.store(cObsNameDT);*/
 //To Test create

   /*   ObsValueNumericDT obsDT = new ObsValueNumericDT();

        obsDT.setObsValueNumericSeq(new Integer(2));
        obsDT.setHighRange("Three high");
        obsDT.setLowRange("three low");
        obsDT.setComparatorCd1("tval");
        obsDT.setNumericValue1(new Integer(33));
        obsDT.setNumericValue2(new Integer(32));
        obsDT.setNumericUnitCd("tplease");
        obsDT.setSeparatorCd("helo");
//        obsDT.setItNew(true);
  //      obsDT.setItDirty(false);

        Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
        ObsValueNumericDAOImpl Obs = new   ObsValueNumericDAOImpl();
        Obs.create(48, cObsNameDT);
*/
        //ObsValueNumericDT obsDT = new ObsValueNumericDT();

        ObsValueNumericDAOImpl obs = new   ObsValueNumericDAOImpl();
        Collection<Object> col = (ArrayList<Object> ) obs.load(48);

        Iterator<Object> ite = col.iterator();
        while (ite.hasNext())
        {
          ObsValueNumericDT obsDT  = (ObsValueNumericDT) ite.next();

          logger.debug(obsDT.getObservationUid());
          logger.debug(obsDT.getObsValueNumericSeq());
          logger.debug(obsDT.getHighRange());
          logger.debug(obsDT.getLowRange());
          logger.debug(obsDT.getComparatorCd1());
          logger.debug(obsDT.getNumericValue1());
          logger.debug(obsDT.getNumericValue2());
          logger.debug(obsDT.getNumericUnitCd());
          logger.debug(obsDT.getSeparatorCd());
          logger.debug(obs.findByPrimaryKey(obsDT.getObservationUid().longValue()));
        }

          logger.debug(obs.findByPrimaryKey(33));


/*

        Long uid = new Long(10);
        Long person_uid = new Long(1);
        ObservationDT obsDT = new ObservationDT();
        obsDT.setActivityDurationAmt("OBS test");
        obsDT.setActivityDurationUnitCd("OBS Activity CD");
        obsDT.setObservationUid(uid);
        Timestamp t = new Timestamp(new java.util.Date().getTime());
        obsDT.setActivityFromTime(t);
        obsDT.setActivityToTime(t);
        obsDT.setAddReasonCd("OBS Reason");
        Timestamp y = new Timestamp(123132323);
        obsDT.setAddTime(y);
        obsDT.setAddUserId(uid);
        obsDT.setCd("OBS");
        obsDT.setCdDescTxt("OBS full");
        obsDT.setCdSystemCd("OBS Test");
        obsDT.setCdSystemDescTxt("OBS Test");
        obsDT.setConfidentialityCd("OBS 321");
        obsDT.setConfidentialityDescTxt("OBS Test");
        obsDT.setDerivationExp(new Integer(1));
        //obsDT.setEffectiveDurationAmt("OBS test");
        //obsDT.setEffectiveDurationUnitCd("OBS test");
        //obsDT.setEffectiveFromTime(y);
        obsDT.setEffectiveToTime(t);
        obsDT.setGroupLevelCd("A");
        obsDT.setJurisdictionCd("OBS TEST");
        obsDT.setLabConditionCd("OBS Lab");
        obsDT.setLastChgReasonCd("OBS reason");
        obsDT.setLastChgTime(y);
        obsDT.setLastChgUserId(uid);
        obsDT.setLocalId("OBS Test");
        obsDT.setMethodCd("OBS Method");
        obsDT.setMethodDescTxt("OBS Desc Txt");
        obsDT.setObsDomainCd("OBS Domain");
        obsDT.setObservationUid(uid);
        obsDT.setOrgAccessPermis("Org Permission");
        obsDT.setPriorityCd("OBS Priority");
        obsDT.setPriorityDescTxt("OBS Desc Txt");
        //obsDT.setProgAreaAccessPermis("OBS ProgAreaAccessPermis");
        obsDT.setRecordStatusCd("OBS Status");
        obsDT.setRecordStatusTime(t);
        obsDT.setRepeatNbr(new Integer(1));
        obsDT.setStatusCd("C");
        //obsDT.setStatusTime(l);

        obsDT.setTargetSiteCd("OBS Target");
        obsDT.setTargetSiteDescTxt("OBS TargetDesc Txt");
        obsDT.setTxt("OBS text");
        obsDT.setSubjectPersonUid(person_uid);
        obsDT.setItNew(true);
        obsDT.setItDirty(false);
        Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
        ObservationDAOImpl Obs = new   ObservationDAOImpl();
        Obs.create(obsDT);

//For findByPrimaryKey
        ObservationDAOImpl Obs1 = new   ObservationDAOImpl();
        Long l = Obs1.findByPrimaryKey(47);

        //for Remove method

        ObservationDAOImpl Obs = new   ObservationDAOImpl();
        Obs.remove(42);
        */
      }catch(Exception e){
        logger.debug("\n\nObservationDAOImpl ERROR : Not working \n" + e);
      }
}

}//end of ObservationNameDAOImpl class

