package gov.cdc.nedss.act.observation.ejb.dao;

import java.util.*;
import java.sql.*;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
// for testing:



public class ObservationInterpDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(ObservationInterpDAOImpl.class.getName());
    private static final String BLANK_STRING = " ";

    public ObservationInterpDAOImpl()
    {
    }

    public long create(long observationUid, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        insertObservationInterps(observationUid, coll);
	
	        return observationUid;
    	}catch(Exception ex){
    		logger.fatal("observationUid: "+observationUid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
    		updateObservationInterps(coll);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object> load(long observationUid) throws
		NEDSSSystemException
    {
    	try{
	        Collection<Object> coll = selectObservationInterps(observationUid);
	        return coll;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long observationUid) throws
		NEDSSSystemException
    {
    	try{
	        if (itemExists(observationUid))
	            return (new Long(observationUid));
	        else
	            logger.error("Primary key not found in PERSON_NAME_TABLE:"
	            + observationUid);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
            return null;
    }


    private void insertObservationInterps(long observationUid,  Collection<Object> coll)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;
        ArrayList<Object>  collList = (ArrayList<Object> )coll;

        try
        {
            anIterator = collList.iterator();

            while(anIterator.hasNext())
            {
                ObservationInterpDT observationInterp = (ObservationInterpDT)anIterator.next();

                if (observationInterp != null)
                {
                    insertObservationInterp(observationUid, observationInterp);

                    observationInterp.setObservationUid(new Long(observationUid));
                }
            }
        }
        catch(Exception ex)
        {
            logger.fatal("observationUid: "+observationUid+" Exception while inserting " +
                    "observation interps into OBSERVATION_INTERP_TABLE: \n", ex);
            throw new NEDSSDAOSysException( ex.toString() );
        }

        logger.debug("Done inserting all observation interps");
    }//end of inserting Observation Interps

    private void insertObservationInterp(long observationUid,
            ObservationInterpDT observationInterp)
            throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

       try
        {

            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_INTERP);
            int i = 1;
            preparedStmt.setLong(i++, observationUid);
            if (observationInterp.getInterpretationCd() != null ) {
               logger.debug("Interrup code is: " + observationInterp.getInterpretationCd() ) ;
               if(! observationInterp.getInterpretationCd().equals(""))
                 preparedStmt.setString(i++,observationInterp.getInterpretationCd());
               else
                 preparedStmt.setString(i++,BLANK_STRING);
            }
            else
                 preparedStmt.setString(i++,BLANK_STRING);
           // preparedStmt.setString(i++,observationInterp.getInterpretationDescTxt());
            if (observationInterp.getInterpretationDescTxt() != null ) {
               if(! observationInterp.getInterpretationDescTxt().equals(""))
                 preparedStmt.setString(i++,observationInterp.getInterpretationDescTxt());
               else
                 preparedStmt.setString(i++,BLANK_STRING);
            }
            else
                 preparedStmt.setString(i++,BLANK_STRING);

            resultCount = preparedStmt.executeUpdate();

            observationInterp.setItDelete(false);
            observationInterp.setItDirty(false);
            observationInterp.setItNew(false);
            if ( resultCount != 1 )
                    logger.error
                        ("Error: none or more than one observation interps inserted at a time, " +
                        "resultCount = " + resultCount);
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while inserting " +
                    "a observation interps into  OBSERVATION_INTERP_TABLE: \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBSERVATION_INTERP_TABLE + "  For observationUid: "+observationUid+"  "+sex.toString(), sex );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    private void updateObservationInterps(Collection<Object> coll) throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;

        if(coll != null)
        {
            logger.debug("Updating Observation Interps");
            try
            {
                for(anIterator = coll.iterator(); anIterator.hasNext(); )
                {
                    ObservationInterpDT observationInterp = (ObservationInterpDT)anIterator.next();
                    if(observationInterp != null && observationInterp.getObservationUid() !=  null)
                    {
                      //in the order of precedence
                      if (observationInterp.isItDelete())
                      {
                        removeObservationInterp (observationInterp.getObservationUid().longValue());
                      }
                      else if (observationInterp.isItNew())
                      {
                        insertObservationInterp((observationInterp.getObservationUid()).longValue(), observationInterp);
                      }
                      else if (observationInterp.isItDirty())
                      {
                        updateObservationInterp (observationInterp);
                      }
                    }
                    else
                       logger.error("Error: Empty observation interp collection");

                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "observation interps, \n", ex);
                throw new NEDSSSystemException( ex.toString() );
            }
        }
    }//end of updating observation interp table

    private void updateObservationInterp(ObservationInterpDT observationInterp) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a observation interp
         */

        if(observationInterp != null && observationInterp.getObservationUid() != null)
        {
            try
            {
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBSERVATION_INTERP);

                int i = 1;
               // preparedStmt.setString(i++,observationInterp.getInterpretationCd());
                if (observationInterp.getInterpretationCd() != null ) {
                   logger.debug("Interrup code is: " + observationInterp.getInterpretationCd() ) ;
                   if(! observationInterp.getInterpretationCd().equals(""))
                     preparedStmt.setString(i++,observationInterp.getInterpretationCd());
                  else
                    preparedStmt.setString(i++,BLANK_STRING);
                }
                else
                   preparedStmt.setString(i++,BLANK_STRING);

                 if (observationInterp.getInterpretationDescTxt() != null ) {
                   if(! observationInterp.getInterpretationDescTxt().equals(""))
                      preparedStmt.setString(i++,observationInterp.getInterpretationDescTxt());
                   else
                    preparedStmt.setString(i++,BLANK_STRING);
                 }
                 else
                    preparedStmt.setString(i++,BLANK_STRING);

              preparedStmt.setLong(i++,observationInterp.getObservationUid().longValue());


                resultCount = preparedStmt.executeUpdate();

                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one observation interp updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    "observation interps, \n" , sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBSERVATION_INTERP_TABLE + "  "+sex.toString(), sex);
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating observation interp table

    protected boolean itemExists (long observationUid) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_INTERP_UID);
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
            logger.fatal("SQLException while checking for an"
                            + " existing observation reason's uid in OBSERVATION_INTERP_TABLE - "
                            + observationUid, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing observation interp's uid - " + observationUid , nsex);
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

    private Collection<Object> selectObservationInterps (long observationUid) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ObservationInterpDT observationInterp= new ObservationInterpDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        /**
         * Selects observation interps
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_INTERP);

            preparedStmt.setLong(1, observationUid);

            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  itemList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            itemList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, observationInterp.getClass(), itemList);


           /* if (pnList.isEmpty())
                throw new NEDSSObservationDAOAppException("No record for this observationUid: " + observationUid);
            else */

            for(Iterator<Object> anIterator = itemList.iterator(); anIterator.hasNext(); )
            {
                ObservationInterpDT reSetName = (ObservationInterpDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("return observation interp collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation interp collection; uid = " + observationUid, se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting observation interps.", rsuex);
            throw new NEDSSDAOSysException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "observation interps; uid = " + observationUid, ex);
            throw new NEDSSDAOSysException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private void removeObservationInterp (long observationUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Deletes Observation Interp
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBSERVATION_INTERP);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete observation interp from OBSERVATION_INTERP_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "observation Interp; id = " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing observation Interp


    

    public static void main(String args[]){
      logger.debug("ObservationInterpDAOImpl - Doing the main thing");

      try{

        // To test update
       // ObservationInterpDAOImpl obsDAOI = new ObservationInterpDAOImpl();

  /*       Long uid = new Long(10);
        Long person_uid = new Long(1);

        ObservationInterpDT obsDT = new ObservationInterpDT();

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
        ObservationInterpDT Obs = new   ObservationInterpDT();
        Obs.store(cObsNameDT);*/
 //To Test create

     ObservationInterpDT obsDT = new ObservationInterpDT();

     obsDT.setInterpretationCd("one");
     obsDT.setInterpretationDescTxt("hello");

//        obsDT.setItNew(true);
  //      obsDT.setItDirty(false);

        Collection<Object> cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
        ObservationInterpDAOImpl Obs = new   ObservationInterpDAOImpl();
        Obs.create(49, cObsNameDT);

        //ObservationInterpDT obsDT = new ObservationInterpDT();

        ObservationInterpDAOImpl obs = new   ObservationInterpDAOImpl();
        Collection<Object> col = (ArrayList<Object> ) obs.load(48);

        Iterator<Object> ite = col.iterator();
        while (ite.hasNext())
        {
          //ObservationInterpDT obsDT  = (ObservationInterpDT) ite.next();

          logger.debug(obsDT.getObservationUid());
          logger.debug(obsDT.getInterpretationCd());
          logger.debug(obsDT.getInterpretationDescTxt());


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
}
