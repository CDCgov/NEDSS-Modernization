package gov.cdc.nedss.act.intervention.ejb.dao;

import gov.cdc.nedss.act.intervention.dt.SubstanceAdministrationDT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
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

public class SubstanceAdminHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(SubstanceAdminHistDAOImpl.class.getName());
  private long interventionUid = -1;
  private int nextSeqNum = 0;

  public SubstanceAdminHistDAOImpl() {
  }//end of constructor

  public SubstanceAdminHistDAOImpl(int nextSeqNum) throws  NEDSSSystemException{
    this.nextSeqNum = nextSeqNum;
  }//end of constructor

  public void store(Object obj)
    throws  NEDSSSystemException {
	  try{
	      SubstanceAdministrationDT dt = (SubstanceAdministrationDT)obj;
	      if(dt == null)
	         throw new NEDSSSystemException("Error: try to store null SubstanceAdministrationDT object.");
	      insertSubstanceAdministrationHist(dt);
	  }catch(Exception ex){
  		logger.fatal("Exception  = "+ex.getMessage(), ex);
  		throw new NEDSSSystemException(ex.toString());
  	  }

  }//end of store()

  public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
	  try{
        Iterator<Object> iterator = null;
        if(coll != null)
        {
        	iterator = coll.iterator();
	        while(iterator.hasNext())
	        {
	        	store(iterator.next());
	        }//end of while
        }//end of if
	  }catch(Exception ex){
  		logger.fatal("Exception  = "+ex.getMessage(), ex);
  		throw new NEDSSSystemException(ex.toString());
  	  }
  }//end of store(Collection)

  public Collection<Object> load(Long intUid, Integer substanceAdminHistSeq) throws NEDSSSystemException,
     NEDSSSystemException {
	  try{
	       logger.info("Starts loadObject() for a substance administration history...");
	       Collection<Object> substanceAdministrationDTColl = selectSubstanceAdministrationHist(intUid.longValue(), substanceAdminHistSeq.intValue());

	       return substanceAdministrationDTColl;
	  }catch(Exception ex){
  		logger.fatal("Exception  = "+ex.getMessage(), ex);
  		throw new NEDSSSystemException(ex.toString());
  	  }
    }//end of load


  ////////////////////////////////////private class methods////////////////////////////////

  private Collection<Object> selectSubstanceAdministrationHist(long intUid, int substanceAdminHistSeq)throws
  NEDSSSystemException {


        SubstanceAdministrationDT substanceAdministrationDT = new SubstanceAdministrationDT();
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
                            "for selectSubstanceAdministrationHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_SUBSTANCE_ADMINISTRATION_HIST);
            preparedStmt.setLong(1, intUid);
            preparedStmt.setLong(2, substanceAdminHistSeq);
            resultSet = preparedStmt.executeQuery();

            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, substanceAdministrationDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                SubstanceAdministrationDT reSetSubstanceAdmin = (SubstanceAdministrationDT)anIterator.next();
                reSetSubstanceAdmin.setItNew(false);
                reSetSubstanceAdmin.setItDirty(false);

                reSetList.add(reSetSubstanceAdmin);
            }
            logger.debug("return substance administration hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "substance administration history; id = " + intUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "substance administration vo; id = " + intUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }



  }//end of selectSubstanceAdministrationHist(...)

  private void insertSubstanceAdministrationHist(SubstanceAdministrationDT dt)throws
    NEDSSSystemException {
      if(dt.getInterventionUid() != null) {
        int resultCount = 0;

            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {


                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_SUBSTANCE_ADMIN_HIST);
                pStmt.setLong(i++, dt.getInterventionUid().longValue());
                pStmt.setInt(i++, nextSeqNum);

                if(dt.getDoseQty() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getDoseQty());

                if(dt.getDoseQtyUnitCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getDoseQtyUnitCd());

                if(dt.getFormCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getFormCd());

                if(dt.getFormDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getFormDescTxt());

                if(dt.getRateQty() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getRateQty());

                if(dt.getRateQtyUnitCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getRateQtyUnitCd());

                if(dt.getRouteCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getRouteCd());

                if(dt.getRouteDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getRouteDescTxt());

                resultCount = pStmt.executeUpdate();
                if ( resultCount != 1 )
                {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            } catch(SQLException se) {
              logger.fatal("SQLException ="+se.getMessage(), se);
              throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
              closeStatement(pStmt);
              releaseConnection(dbConnection);
            }//end of finally
      }//end of if
  }//end of insertSubstancAdministrationHist()
}//end of class
