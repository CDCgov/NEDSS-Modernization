package gov.cdc.nedss.entity.material.ejb.dao;


import gov.cdc.nedss.entity.material.dt.ManufacturedMaterialDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
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

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ManufacturedMaterialHistDAOImpl extends BMPBase {

  static final LogUtils logger = new LogUtils(ManufacturedMaterialHistDAOImpl.class.getName());
  private long observationUid = -1;
  private String interpretationCd = "";
  private short versionCtrlNbr = 0;

  public final String INSERT_MANUFACTURED_MATERIAL_HIST =
     "Insert into manufactured_material_hist(material_uid, manufactured_material_seq, "+
     "version_ctrl_nbr, add_reason_cd, add_time, add_user_id, expiration_time, last_chg_reason_cd, last_chg_time, "+
     "last_chg_user_id, lot_nm, record_status_cd, record_status_time, user_affiliation_txt, stability_from_time, stability_to_time, "+
     "stability_duration_cd, stability_duration_unit_cd) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


  public final String SELECT_MANUFACTURED_MATERIAL_HIST =
     "Select material_uid \"materialUid\", "+
     "manufactured_material_seq \"manufacturedMaterialSeq\", "+
     //"version_ctrl_nbr \"versionCntrlNbr\", "+
     "add_reason_cd \"addReasonCd\", "+
     "add_time \"addTime\", "+
     "add_user_id \"addUserId\", "+
     "expiration_time \"expirationTime\", "+
     "last_chg_reason_cd \"lastChgReasonCd\", "+
     "last_chg_time \"lastChgTime\", "+
     "last_chg_user_id \"lastChgUserId\", "+
     "lot_nm \"lotNm\", "+
     "record_status_cd \"recordStatusCd\", "+
     "record_status_time \"recordStatusTime\", "+
     "user_affiliation_txt \"userAffiliationTxt\", "+
     "stability_from_time \"stabilityFromTime\", "+
     "stability_to_time \"stabilityToTime\", "+
     "stability_duration_cd \"stabilityDurationCd\", "+
     "stability_duration_unit_cd \"stabilityDurationUnitCd\" "+
     "from manufactured_material_hist where material_uid = ? and "+
     "manufactured_material_seq =  ? and version_ctrl_nbr = ?";

  public ManufacturedMaterialHistDAOImpl(short versionCtrlNbr)throws NEDSSDAOSysException, NEDSSSystemException {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

 public ManufacturedMaterialHistDAOImpl()throws NEDSSDAOSysException, NEDSSSystemException {

  }//end of constructor

  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
	  try{
        ManufacturedMaterialDT dt = (ManufacturedMaterialDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObservationDT object.");
          insertManufacturedMaterialHist(dt);
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store()

  public void store(Collection<Object> coll)
	   throws NEDSSDAOSysException, NEDSSSystemException {
	  try{
	       Iterator<Object>  iterator = null;
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
		        	store(iterator.next());
		        }//end of while
	        }//end of if
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store(Collection)

    public Collection<Object>  load(Long materialUid, Integer manufacturedMaterialSeq, Integer manufacturedMaterialHistSeq) throws NEDSSSystemException
    {
    	try{
		    logger.info("Starts loadObject() for a manufactured material history...");
	        Collection<Object>  manufacturedMaterialDTColl = selectManufacturedMaterialHist(materialUid.longValue(), manufacturedMaterialSeq.shortValue(), manufacturedMaterialHistSeq.intValue());
	        return manufacturedMaterialDTColl;
    	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  	}
    }//end of load


    /////////////////////////////private class methods////////////////////////

    private Collection<Object>  selectManufacturedMaterialHist (long mfdMaterialUID, short mfdMaterialSeq, int mfdMaterialVersonCntrlNbr) throws
		NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ManufacturedMaterialDT manufacturedMaterialDt = new ManufacturedMaterialDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
           dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectManufacturedMaterialHist ", nsex);
            throw new NEDSSSystemException( nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_MANUFACTURED_MATERIAL_HIST);
            preparedStmt.setLong(1, mfdMaterialUID);
            preparedStmt.setInt(2, mfdMaterialSeq);
            preparedStmt.setInt(3, mfdMaterialVersonCntrlNbr);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object> pnList = new ArrayList<Object> ();
            ArrayList<Object> reSetList = new ArrayList<Object> ();

            pnList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, manufacturedMaterialDt.getClass(), pnList);
            for(Iterator<Object> anIterator = pnList.iterator(); anIterator.hasNext(); )
            {
                ManufacturedMaterialDT reSetName = (ManufacturedMaterialDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("return manufactured material history collection");
            return reSetList;
        }
        catch(SQLException se)
        {
        	logger.fatal("Error in result set handling while selecting manufactured material history; manufacturedMaterailUid = " + mfdMaterialUID + ", manufacturedMaterialSeq = "+mfdMaterialSeq+ ", mfdMaterialVersonCntrlNbr = " +mfdMaterialVersonCntrlNbr+ " :\n" , se);
            throw new NEDSSSystemException("SQLException while selecting " +
                            "manufactured material history collection; manufacturedMaterailUid = " + mfdMaterialUID + ", manufacturedMaterialSeq = "+mfdMaterialSeq+ ", mfdMaterialVersonCntrlNbr = " +mfdMaterialVersonCntrlNbr+ " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting manufactured material history; manufacturedMaterailUid = " + mfdMaterialUID + ", manufacturedMaterialSeq = "+mfdMaterialSeq+ ", mfdMaterialVersonCntrlNbr = " +mfdMaterialVersonCntrlNbr+ " :\n" , rsuex);
            throw new NEDSSSystemException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection: " +
                  "manufacturedMaterailUid = " + mfdMaterialUID + ", manufacturedMaterialSeq = "+mfdMaterialSeq+ ", mfdMaterialVersonCntrlNbr" +mfdMaterialVersonCntrlNbr, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    private void insertManufacturedMaterialHist(ManufacturedMaterialDT dt)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (dt.getMaterialUid() != null && dt.getManufacturedMaterialSeq() != null)
        {
          int resultCount = 0;
          Connection dbConnection = null;
          PreparedStatement ps = null;
          try
          {
              dbConnection = getConnection();
              ps = dbConnection.prepareStatement(INSERT_MANUFACTURED_MATERIAL_HIST);
              int i = 1;
              ps.setLong(i++, dt.getMaterialUid().longValue());
              ps.setInt(i++, dt.getManufacturedMaterialSeq().shortValue());
              ps.setInt(i++, versionCtrlNbr);

              if(dt.getAddReasonCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                ps.setNull(i++, Types.BIGINT);
              else
                ps.setLong(i++, dt.getAddUserId().longValue());

              if(dt.getExpirationTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getExpirationTime());

              if(dt.getLastChgReasonCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                ps.setNull(i++, Types.BIGINT);
              else
                ps.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getLotNm() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getLotNm());

              if(dt.getRecordStatusCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getUserAffiliationTxt() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getUserAffiliationTxt());

              if(dt.getStabilityFromTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getStabilityFromTime());

              if(dt.getStabilityToTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getStabilityToTime());

              if(dt.getStabilityDurationCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getStabilityDurationCd());

              if(dt.getStabilityDurationUnitCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getStabilityDurationCd());


            resultCount = ps.executeUpdate();
            if ( resultCount != 1 )
            {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
            }
          }
          catch(SQLException se)
          {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
        	  throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          }
          finally
          {
        	  closeStatement(ps);
        	  releaseConnection(dbConnection);
          }
        }
    }



}//end of ManufacturedMaterialHistDAOImpl
