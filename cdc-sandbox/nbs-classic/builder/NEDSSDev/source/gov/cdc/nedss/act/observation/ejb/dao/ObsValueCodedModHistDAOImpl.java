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

public class ObsValueCodedModHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ObsValueCodedModHistDAOImpl.class.getName());
  private long observationUid = -1;
  private String code = "";
  private String codeModeCd = "";
  private short versionCtrlNbr = 0;

  /**
   * Initializes the versionCtrlNbr class attribute
   * @param versionCtrlNbr : short
   */
  public ObsValueCodedModHistDAOImpl(short versionCtrlNbr)throws  NEDSSSystemException {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Default constructor
   */
  public ObsValueCodedModHistDAOImpl() {


  }//end of constructor

  /**
   * Results in the addition of an ObsValueCodedModDT object to history
   * @param obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
        ObsValueCodedModDT dt = (ObsValueCodedModDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObsValueCodedModDT object.");
          insertObsValueCodedModHist(dt);

    }//end of store()

  /**
     * Results in the addition of 0.* obsValueCodedModDT object to history
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
   * Builds a collection of obsValueCodedModDT objects
   * @param obsUid : Long
   * @param code : String
   * @param versionCtrlNbr : Integer
   * @throws NEDSSSystemException
   */
  public Collection<Object> load(Long obsUid, String code, String codeModeCd, Integer obsValueCodedModHistSeq) throws NEDSSSystemException
      {
	       logger.info("Starts loadObject() for a observation value coded mod history...");
        Collection<Object> obsValueCodedModDTColl = selectObsValueCodedModHist(obsUid.longValue(), code, codeModeCd, obsValueCodedModHistSeq.shortValue());

        return obsValueCodedModDTColl;

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
   * return a collection of obsValueCodedModHistItemVOs
   *
  public Collection<Object>  getObsValueCodedModHistItemColl(Long observationUid, String code, String codedModCd,  String sqlQuery)
	throws NEDSSSystemException, NEDSSSystemException
    {
	       Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ObsValueCodedModHistItemVO vo = new ObsValueCodedModHistItemVO();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for ObsValueCodedModHistItemVO", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, observationUid.longValue());
            preparedStmt.setString(2, code);
            preparedStmt.setString(3, codedModCd);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object> idList = new ArrayList<Object> ();
            ArrayList<Object> reSetList = new ArrayList<Object> ();
            idList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, vo.getClass(), idList);

            for(Iterator<Object> anIterator = idList.iterator(); anIterator.hasNext(); )
            {
                ObsValueCodedModHistItemVO reSetName = (ObsValueCodedModHistItemVO)anIterator.next();
                reSetList.add(reSetName);
            }
            logger.debug("Return ObsValueCodedModHistItemVO collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "ObsValueCodedModHistItemVO collection; uid = " + observationUid , se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting ObsValueCodedModHistItemVO.", rsuex);
            throw new NEDSSSystemException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "ObsValueCodedModHistItemVO; observationUid = " + observationUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }//end of finally
    }//end of getObsValueCodedModHistItemColl()

    public ObsValueCodedModHistItemVO getObsValueCodedModHistItem(Long observationUid,String code,String codeModCd, String sqlQuery)
    throws NEDSSSystemException, NEDSSSystemException
    {
	       ObsValueCodedModHistItemVO obsValueCodedModHistItemVO = new ObsValueCodedModHistItemVO();
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
                            "for getObsValueCodedModHistItem ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, observationUid.longValue());
            preparedStmt.setString(2, code);
            preparedStmt.setString(3, codeModCd);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ObsValueCodedModHistItemVO object for observation value coded mod history: observationUid = " + observationUid);

            resultSetMetaData = resultSet.getMetaData();

            obsValueCodedModHistItemVO = (ObsValueCodedModHistItemVO)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, obsValueCodedModHistItemVO.getClass());
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "ObsValueCodedModHistItemVO: id = " + observationUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "ObsValueCodedModHistItemVO; id = " + observationUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return obsValueCodedModHistItemVO for observation value coded mod history");

        return obsValueCodedModHistItemVO;
    }*/

  /////////////////////////////////private class methods////////////////////////////////
  /**
   * Builds a collection of obsValueCodedModDT objects from history
   * @param obsUid : long
   * @param code : String
   * @param codedMod : String
   * @param versionCtrlNbr : short
   * @return Collection
   * @throws NEDSSSystemException
   */
  private Collection<Object> selectObsValueCodedModHist(long obsUid, String code, String codeModeCd, short versionCtrlNbr)
    throws NEDSSSystemException {
        ObsValueCodedModDT obsValueCodedModDT = new ObsValueCodedModDT();
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
                            "for selectObsValueCodedModHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_VALUE_CODED_MOD_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setString(2, code);
	           preparedStmt.setString(3, codeModeCd);
            preparedStmt.setShort(4, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueCodedModDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObsValueCodedModDT reSetCodedMod = (ObsValueCodedModDT)anIterator.next();
                reSetCodedMod.setItNew(false);
                reSetCodedMod.setItDirty(false);

                reSetList.add(reSetCodedMod);
            }
            logger.debug("return observation coded mod hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation value coded mod history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation value coded mod vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }



    }//end of select ObsValueCodedModHist

  /**
   * Results in the addtion of an obsValueCodedModDT record to history
   * @param dt : ObsValueCodedModDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObsValueCodedModHist(ObsValueCodedModDT dt)throws
    NEDSSSystemException {
    if(dt.getObservationUid() != null && dt.getCode() != null && dt.getCodeModCd() != null) {
        int resultCount = 0;
            //PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_PERSON_HIST);
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_VALUE_CODED_MOD_HIST);
                pStmt.setLong(i++, dt.getObservationUid().longValue());
                pStmt.setString(i++, dt.getCode());
                pStmt.setString(i++, dt.getCodeModCd());
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
  }//end of insertObsValueCodedModHist()



}//end of class
