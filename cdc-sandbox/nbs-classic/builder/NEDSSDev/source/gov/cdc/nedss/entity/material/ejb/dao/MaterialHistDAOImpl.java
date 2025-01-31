package gov.cdc.nedss.entity.material.ejb.dao;

import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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

public class MaterialHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(MaterialHistDAOImpl.class.getName());
  private long materialUid = -1;
  private short versionCtrlNbr = 0;

  private final String SELECT_MATERIAL_SEQ_NUMBER_HIST =
  "select version_ctrl_nbr from material_hist where material_uid = ?";

  private final String INSERT_MATERIAL_HIST =
  "insert into material_hist(material_uid, version_ctrl_nbr, "+
  "add_reason_cd, "+
  "add_time, "+
  "add_user_id, "+
  "cd, "+
  "cd_desc_txt, "+
  "description, "+
  "effective_duration_amt, "+
  "effective_duration_unit_cd, "+
  "effective_from_time, "+
  "effective_to_time, "+
  "form_cd, "+
  "form_desc_txt, "+
  "handling_cd, "+
  "handling_desc_txt, "+
  "last_chg_reason_cd, "+
  "last_chg_time, "+
  "last_chg_user_id, "+
  "local_id, "+
  "nm, "+
  "qty, "+
  "qty_unit_cd, "+
  "record_status_cd, "+
  "record_status_time, "+
  "risk_cd, "+
  "risk_desc_txt, "+
  "status_cd, "+
  "status_time, "+
  "user_affiliation_txt) "+
  "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  private final String SELECT_MATERIAL_HIST =
  "select material_uid \"materialUid\", "+
  "version_ctrl_nbr \"versionCtrlNbr\", "+
  "add_reason_cd \"addReasonCd\", "+
  "add_time \"addTime\", "+
  "add_user_id \"addUserId\", "+
  "cd \"cd\", "+
  "cd_desc_txt \"cdDescTxt\", "+
  "description \"description\", "+
  "effective_duration_amt \"effectiveDurationAmt\", "+
  "effective_duration_unit_cd \"effectiveDurationUnitCd\", "+
  "effective_from_time \"effectiveFromTime\", "+
  "effective_to_time \"effectiveToTime\", "+
  "form_cd \"formCd\", "+
  "form_desc_txt \"formDescTxt\", "+
  "handling_cd \"handlingCd\", "+
  "handling_desc_txt \"handlingDescTxt\", "+
  "last_chg_reason_cd \"lastChgReasonCd\", "+
  "last_chg_time \"lastChgTime\", "+
  "last_chg_user_id \"lastChgUserId\", "+
  "local_id \"localId\", "+
  "nm, "+
  "qty, "+
  "qty_unit_cd \"qtyUnitCd\", "+
  "record_status_cd \"recordStatusCd\", "+
  "record_status_time \"recordStatusTime\", "+
  "risk_cd \"riskCd\", "+
  "risk_desc_txt \"riskDescTxt\", "+
  "status_cd \"statusCd\", "+
  "status_time \"statusTime\", "+
  "user_affiliation_txt \"userAffiliationTxt\" "+
  "from material_hist where material_uid = ? and version_ctrl_nbr = ?";

  public MaterialHistDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException{
  }

  public MaterialHistDAOImpl(long materialUid, short versionCtrlNbr)throws NEDSSDAOSysException,
  NEDSSSystemException {
    this.materialUid = materialUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextMaterialHistId();
  }//end of constructor

  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
	  try{
        MaterialDT dt = (MaterialDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null MaterialDT object.");
        insertMaterialHist(dt);
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store()

    public void store(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
    	try{
		   Iterator<Object>  it = coll.iterator();
		    while(it.hasNext()) {
		      this.store(it.next());
		    }//end of while
    	}catch(NEDSSDAOSysException ex){
  		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
  		  throw new NEDSSDAOSysException(ex.toString());
  	  	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  	}
  }//end of store

  public MaterialDT load(Long materialUid, Integer materialHistSeq) throws NEDSSSystemException
  {
	  	try{
	       logger.info("Starts loadObject() for a material history...");
	        MaterialDT mtDT = selectMaterialHist(materialUid.longValue(), materialHistSeq.intValue());
	        mtDT.setItNew(false);
	        mtDT.setItDirty(false);
	        logger.info("Done loadObject() for a material history - return: " + mtDT.toString());
	        return mtDT;
	  	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  	}
    }//end of load

    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

     /////////////////////////////private class methods/////////////////////////////

    private MaterialDT selectMaterialHist(long materialUid, int materialVersionCtrlNbr)throws NEDSSSystemException
   {


        MaterialDT mtDT = new MaterialDT();
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
                            "for selectMaterialHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_MATERIAL_HIST);
            preparedStmt.setLong(1, materialUid);
            preparedStmt.setLong(2, materialVersionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

            logger.debug("MaterialDT object for material History: materialUid = " + materialUid);

            resultSetMetaData = resultSet.getMetaData();

            mtDT = (MaterialDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, mtDT.getClass());

            mtDT.setItNew(false);
            mtDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "material history; MaterialUid = " + materialUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "material dt; MaterialUid = " + materialUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return MaterialDT for place history");

        return mtDT;
  }//end of selectMaterialHist(...)


    private void insertMaterialHist(MaterialDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
    if(dt.getMaterialUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_MATERIAL_HIST);
              pStmt.setLong(i++, dt.getMaterialUid().longValue());
              pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

              if(dt.getAddReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getAddUserId().longValue());

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdDescTxt());

              if(dt.getDescription() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getDescription());

              if(dt.getEffectiveDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationAmt());

              if(dt.getEffectiveDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

              if(dt.getEffectiveFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

              if(dt.getEffectiveToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveToTime());

              if(dt.getFormCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getFormCd());

              if(dt.getFormDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getFormDescTxt());

              if(dt.getHandlingCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getHandlingCd());

              if(dt.getHandlingDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getHandlingDescTxt());

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getLocalId() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLocalId());

              if(dt.getNm() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getNm());

              if(dt.getQty() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getQty());

              if(dt.getQtyUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getQtyUnitCd());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getRiskCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRiskCd());

              if(dt.getRiskDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRiskDescTxt());

              pStmt.setString(i++, dt.getStatusCd());

              pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());


              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("Exception  = "+se.getMessage(), se);
        	  throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertMaterialEncounterHist()

}//end of MaterialHistDAOImpl
