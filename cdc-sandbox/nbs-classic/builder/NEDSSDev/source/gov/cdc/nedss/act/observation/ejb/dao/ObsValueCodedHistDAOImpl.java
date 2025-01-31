package gov.cdc.nedss.act.observation.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.act.sqlscript.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ObsValueCodedHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ObsValueCodedHistDAOImpl.class.getName());
  private long observationUid = -1;
  private String code = "";
  private short versionCtrlNbr = 0;

  /**
   * Initializes the versionCtrlNbr class attribute
   * @param versionCtrlNbr : short
   */
  public ObsValueCodedHistDAOImpl(short versionCtrlNbr)throws  NEDSSSystemException {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Default constructor
   */
  public ObsValueCodedHistDAOImpl(){


  }//end of constructor

  /**
   * Results in the addition of an ObsValueCodedDT object to history
   * @param obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
        ObsValueCodedDT dt = (ObsValueCodedDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObsValueCodedDT object.");
          insertObsValueCodedHist(dt);

    }//end of store()

    /**
     * Results in the addition of 0.* obsValueCodedDT object to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
       Iterator<Object>  iterator = null;
        if(coll != null)
        {
        	iterator = coll.iterator();
	        while(iterator.hasNext())
	        {
	        	store(iterator.next());
	        }//end of while
        }//end of if
  }//end of store(Collection)

  /**
   * Builds a collection of obsValueCodedDT objects
   * @param obsUid : Long
   * @param code : String
   * @param versionCtrlNbr : Integer
   * @throws NEDSSSystemException
   */
  public Collection<Object> load(Long obsUid, String code, Integer versionCtrlNbr) throws NEDSSSystemException
      {
	       logger.info("Starts loadObject() for a observation value coded history...");
        Collection<Object> obsValueCodedDTColl = selectObsValueCodedHist(obsUid.longValue(), code, versionCtrlNbr.shortValue());

        return obsValueCodedDTColl;

    }//end of load

    /**
     * Returns the versionCtrlNbr
     * @return versionCtrlNbr : short
     *
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }//end of getSeqNumber()

    /*
   * return a collection of obsValueCodedHistItemVOs
   *
  public Collection<Object>  getObsValueCodedHistItemColl(Long observationUid, String code, String sqlQuery)
	throws NEDSSSystemException, NEDSSSystemException
    {
	       Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ObsValueCodedHistItemVO vo = new ObsValueCodedHistItemVO();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for ObsValueCodedHistItemVO", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, observationUid.longValue());
            preparedStmt.setString(2, code);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object> idList = new ArrayList<Object> ();
            ArrayList<Object> reSetList = new ArrayList<Object> ();
            idList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, vo.getClass(), idList);

            for(Iterator<Object> anIterator = idList.iterator(); anIterator.hasNext(); )
            {
                ObsValueCodedHistItemVO reSetName = (ObsValueCodedHistItemVO)anIterator.next();
                reSetList.add(reSetName);
            }
            logger.debug("Return ObsValueCodedHistItemVO collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "ObsValueCodedHistItemVO collection; uid = " + observationUid , se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting ObsValueCodedHistItemVO.", rsuex);
            throw new NEDSSSystemException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "ObsValueCodedHistItemVO; observationUid = " + observationUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }//end of finally
    }//end of getObsValueCodedHistItemColl()

    public ObsValueCodedHistItemVO getObsValueCodedHistItem(Long observationUid,String code, String sqlQuery)
    throws NEDSSSystemException, NEDSSSystemException
    {
	       ObsValueCodedHistItemVO obsValueCodedHistItemVO = new ObsValueCodedHistItemVO();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for getObsValueCodedHistItem ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, observationUid.longValue());
            preparedStmt.setString(2, code);

            resultSet = preparedStmt.executeQuery();

	           logger.debug("ObsValueCodedHistItemVO object for observation value coded history: observationUid = " + observationUid);

            resultSetMetaData = resultSet.getMetaData();

            obsValueCodedHistItemVO = (ObsValueCodedHistItemVO)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, obsValueCodedHistItemVO.getClass());
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "ObsValueCodedHistItemVO: id = " + observationUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "ObsValueCodedHistItemVO; id = " + observationUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return obsValueCodedHistItemVO for observation value coded history");

        return obsValueCodedHistItemVO;
    }*/

  ///////////////////////////////private class methods//////////////////////////////

  /**
   * Builds a collection of obsValueCodedDT objects from history
   * @param obsUid : long
   * @param code : String
   * @param versionCtrlNbr : short
   */
  private Collection<Object> selectObsValueCodedHist(long obsUid, String code, short versionCtrlNbr)
    throws  NEDSSSystemException {
      ObsValueCodedDT obsValueCodedDT = new ObsValueCodedDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectObsValueCodedHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_VALUE_CODED_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setString(2, code);
            preparedStmt.setShort(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueCodedDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObsValueCodedDT reSetCoded = (ObsValueCodedDT)anIterator.next();
                reSetCoded.setItNew(false);
                reSetCoded.setItDirty(false);

                reSetList.add(reSetCoded);
            }//end of for
            logger.debug("return observation coded hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation value coded history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation value coded vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }


    }//end of select ObsValueCodedHist

  /**
   * Results in the addtion of an obsValueCodedDT record to history
   * @param dt : ObsValueCodedDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObsValueCodedHist(ObsValueCodedDT dt)throws
    NEDSSSystemException {
    if(dt.getObservationUid() != null && dt.getCode() != null) {
        int resultCount = 0;
            //PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_PERSON_HIST);
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_VALUE_CODED_HIST);

                pStmt.setLong(i++, dt.getObservationUid().longValue());

                pStmt.setString(i++, dt.getCode());
                pStmt.setShort(i++, versionCtrlNbr);

                if(dt.getCodeSystemCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getCodeSystemCd());

                if(dt.getCodeSystemDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getCodeSystemDescTxt());

                if(dt.getCodeVersion() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getCodeVersion());

                if(dt.getDisplayName() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getDisplayName());

                if(dt.getOriginalTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getOriginalTxt());
                if (dt.getAltCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++,dt.getAltCd() );

                if (dt.getAltCdDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getAltCdDescTxt());

                if (dt.getAltCdSystemCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getAltCdSystemCd());

                if(dt.getAltCdSystemDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++,dt.getAltCdSystemDescTxt());

                if(dt.getCodeDerivedInd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getCodeDerivedInd());


                resultCount = pStmt.executeUpdate();
                if ( resultCount != 1 )
                {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            } catch(SQLException se) {
              se.printStackTrace();
              throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
              closeStatement(pStmt);
              releaseConnection(dbConnection);
            }//end of finally
      }//end of if
  }//end of insertObsValueCodedHist()




}//end of class
