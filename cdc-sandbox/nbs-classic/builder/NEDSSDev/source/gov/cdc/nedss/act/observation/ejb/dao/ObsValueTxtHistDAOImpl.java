package gov.cdc.nedss.act.observation.ejb.dao;

import java.io.*;
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

public class ObsValueTxtHistDAOImpl extends BMPBase{

  static final LogUtils logger = new LogUtils(ObsValueTxtHistDAOImpl.class.getName());
  private long observationUid = -1;
  private short obsValueTxtSeq = -1;
  private short versionCtrlNbr = 0;

  /**
   * Initializes the following class attribute: versionCtrlNbr
   * @param versionCtrlNbr : short
   */
  public ObsValueTxtHistDAOImpl(short versionCtrlNbr)throws  NEDSSSystemException {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Default constructor
   */
  public ObsValueTxtHistDAOImpl() {

  }//end of constructor

  /**
   * Results in the addition of an ObsValueTxtDT record to history
   * @para obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
        ObsValueTxtDT dt = (ObsValueTxtDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObsValueTxtDT object.");
          insertObsValueTxtHist(dt);

    }//end of store()

  /**
   * Results in the addtion of 0.* ObsValueTxtDT objects to history
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
   * Loads a collection of ObsValueTxtDT objects.
   * @param obsUid : Long
   * @param obsValueTxtSeq : Integer
   * @param versionCtrlNbr : Integer
   * @return Collection
   * @throws NEDSSSystemException
   */
  public Collection<Object> load(Long obsUid, Integer obsValueTxtSeq, Integer versionCtrlNbr) throws NEDSSSystemException,
     NEDSSSystemException {
	       logger.info("Starts loadObject() for a observation value txt history...");
        Collection<Object> obsValueTxtDTColl = selectObsValueTxtHist(obsUid.longValue(), obsValueTxtSeq.shortValue(), versionCtrlNbr.shortValue());

        return obsValueTxtDTColl;

    }//end of load

  /**
   * Returns the versionCtrlNbr
   * @return versionCtrlNbr : short
   */
  public short getVersionCtrlNbr()
  {
    return this.versionCtrlNbr;
  }//end of getVersionCtrlNbr()

  /////////////////////////////////private class methods//////////////////////////
  /**
   * Creates a collection of ObsValueTxtDT objects
   * @param obsUid : long
   * @param obsValueTxtSeq : short
   * @param versionCtrlNbr : short
   * @return Collection
   * @throws NEDSSSystemException
   */
  private Collection<Object> selectObsValueTxtHist(long obsUid, short obsValueTxtSeq, short versionCtrlNbr)
    throws NEDSSSystemException {
      ObsValueTxtDT obsValueTxtDT = new ObsValueTxtDT();
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
                            "for selectObsValueTxtHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_VALUE_TXT_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setShort(2, obsValueTxtSeq);
	           preparedStmt.setShort(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueTxtDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObsValueTxtDT reSetTxt = (ObsValueTxtDT)anIterator.next();
                reSetTxt.setItNew(false);
                reSetTxt.setItDirty(false);

                reSetList.add(reSetTxt);
            }
            logger.debug("return observation txt hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation value txt history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation value txt vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }


    }//end of select ObsValueTxtHist

  /**
   * Results in the addtion of a ObsValueTxtDT object to history
   * @param dt : ObsValueTxtDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObsValueTxtHist(ObsValueTxtDT dt)throws
    NEDSSSystemException {
    if(dt.getObservationUid() != null && dt.getObsValueTxtSeq() != null) {
        int resultCount = 0;
            //PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_PERSON_HIST);
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_VALUE_TXT_HIST);
                pStmt.setLong(i++, dt.getObservationUid().longValue());
                pStmt.setShort(i++, dt.getObsValueTxtSeq().shortValue());
                pStmt.setShort(i++, versionCtrlNbr);

                if(dt.getDataSubtypeCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getDataSubtypeCd());

                if(dt.getEncodingTypeCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getEncodingTypeCd());

                if(dt.getTxtTypeCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getTxtTypeCd());

                //BLOB specific changes
                if(dt.getValueImageTxt()!=null ){
                		InputStream fis = new ByteArrayInputStream(dt.getValueImageTxt());
                		pStmt.setBinaryStream(i++,fis, dt.getValueImageTxt().length);
                }else{
                	pStmt.setNull(i++, Types.BINARY);
                }

                if(dt.getValueTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getValueTxt());

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
  }//end of insertObsValueTxtHist()

}//end of class
