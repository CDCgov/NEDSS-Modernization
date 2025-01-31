package gov.cdc.nedss.ldf.dao;

import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 *
 */
public class StateDefinedFieldDataDAOImpl extends DAOBase {

 // private long ldfUID = -1;
  private final String INSERT_LDF =  "INSERT INTO State_defined_field_data "+
      "(ldf_uid, "+
      "business_object_nm, "+
      "add_time, "+
      "business_object_uid, "+
      "last_chg_time, "+
      "ldf_value, "+ 
      "version_ctrl_nbr "+
      ") VALUES (?, ?, ?, ?, ?, ?, ?)";
  private final String UPDATE_LDF =  "UPDATE State_defined_field_data SET " +
      "business_object_nm = ?, "+
      "add_time = ?, "+
      "last_chg_time = ?, "+
      "ldf_value = ?, "+
      "version_ctrl_nbr = ? "+
      " where (ldf_uid = ? and business_obj_uid = ?)";
  private final String DELETE_LDF =  "DELETE FROM State_defined_field_data "+
      " WHERE ( business_object_uid = ?)";

  private final String INSERT_HISTORY = "insert into state_defined_field_data_hist (ldf_uid, business_object_uid, business_object_nm, add_time,last_chg_time ,ldf_value ,version_ctrl_nbr) "+
      " select ldf_uid, business_object_uid, business_object_nm, add_time,last_chg_time ,ldf_value ,version_ctrl_nbr "+
      " from state_defined_field_data where business_object_uid = ? ";


  private final String SELECT_LDF =  "SELECT "+
    " sf.ldf_uid \"ldfUid\", "+
    " sf.business_object_nm  \"businessObjNm\", "+
    " sf.add_time    \"addTime\", "+
    " sf.business_object_uid  \"businessObjUid\", "+
    " sf.last_chg_time \"lastChgTime\", "+
    " sf.ldf_value    \"ldfValue\", "+
    " sf.version_ctrl_nbr \"versionCtrlNbr\" "+
    " from State_defined_field_data sf, "+
    " state_defined_field_metadata sdfmd "+
    " where  sf.ldf_uid = sdfmd.ldf_uid "+
    " and sf.business_object_uid = ? ";

    private final String SELECT_LDF_ORDER_BY = " order by sf.ldf_uid ";
    private final String SELECT_LDF_COND_CD_WHERE_CLAUSE = " and sdfmd.condition_cd = ? ";

  static final LogUtils logger = new LogUtils((StateDefinedFieldDataDAOImpl.class).getName());//Used for logging
  public StateDefinedFieldDataDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException {

  }

  /**
   *
   */
  public long create(Object obj) throws NEDSSDAOSysException, NEDSSSystemException {
	  try{
		  	long ldfUID = -1;
	        ldfUID = insertLDF((StateDefinedFieldDataDT)obj);
	        ((StateDefinedFieldDataDT)obj).setItNew(false);
	        ((StateDefinedFieldDataDT)obj).setItDirty(false);
	        return ldfUID;
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

  }//create

  /**
   *
   */

  public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException, NEDSSConcurrentDataException {

    try{
        updateLDF((StateDefinedFieldDataDT)obj);
    }catch(NEDSSDAOSysException ex){
	  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
	  throw new NEDSSDAOSysException(ex.toString());
    }catch(NEDSSConcurrentDataException ex){
  	  logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
  	  throw new NEDSSConcurrentDataException(ex.toString());
    }catch(Exception ex){
	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	  throw new NEDSSSystemException(ex.toString());
    }

  }//store


  public void setLDFData(Collection<Object> stateDefinedFieldDataCollection, String businessObjNm, Integer versionCtrlNbr, Long businessOjbUid)throws NEDSSDAOSysException, NEDSSSystemException, NEDSSConcurrentDataException {
  //get unique bus_object_nm and business_object uid from collection??
  //execute query to copy_history_data from state_defined_field_data to history
  //execute query to delete data from state_defined_field_data
  //iterate through collection to call store()

  //get the version ctrl number from the business object depending on businessObjNm
  if(versionCtrlNbr == null){
    String tableName = null;
    String uidColumn = null;
    if(businessObjNm.equalsIgnoreCase(NEDSSConstants.ORGANIZATION_LDF)){
      tableName = "ORGANIZATION";
      uidColumn = "organization_uid";
    }
    else if(businessObjNm.equalsIgnoreCase(NEDSSConstants.PATIENT_LDF)||businessObjNm.equalsIgnoreCase(NEDSSConstants.PROVIDER_LDF)){
            tableName = "PERSON";
            uidColumn = "person_uid";
    }
    else if(businessObjNm.equalsIgnoreCase(NEDSSConstants.TREATMENT_LDF)){
              tableName = "TREATMENT";
              uidColumn = "treatment_uid";
    }
    else if(businessObjNm.equalsIgnoreCase(NEDSSConstants.MORBREPORT_LDF)||businessObjNm.equalsIgnoreCase(NEDSSConstants.LABREPORT_LDF) ){
      tableName = "OBSERVATION";
      uidColumn = "observation_uid";
    }
    else if(businessObjNm.equalsIgnoreCase(NEDSSConstants.VACCINATION_LDF)){
      tableName = "INTERVENTION";
      uidColumn = "intervention_uid";
    }
    else
    {
      tableName = "PUBLIC_HEALTH_CASE";
      uidColumn = "public_health_case_uid";
    }

    StringBuffer query = new StringBuffer();
    query.append("select version_ctrl_nbr from ").append(tableName).append(" where ");
    query.append(uidColumn);
    query.append(" = ?");

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;

       try
       {
         dbConnection = getConnection();
         preparedStmt = dbConnection.prepareStatement(query.toString());
         preparedStmt.setObject(1,businessOjbUid);

         resultSet = preparedStmt.executeQuery();
         while(resultSet.next()){
           versionCtrlNbr = new Integer(resultSet.getInt(1));
         }
       }
       catch(Exception e){
    	   logger.fatal("Exception  = "+e.getMessage(), e);
           throw new NEDSSSystemException(e.toString());
       }
       finally{
    	   closeResultSet(resultSet);
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
       }

  }

  try{
	  //copy to hist and delete
	  this.removeLDF(businessOjbUid);
	
	  //now do the inserts
	  Iterator<Object> iter = null;
	     if(stateDefinedFieldDataCollection  != null) {
	       for (iter = stateDefinedFieldDataCollection.iterator(); iter.hasNext(); ) {
	          StateDefinedFieldDataDT stateDefinedFieldDataDT = (StateDefinedFieldDataDT) iter.next();
	
	         if (stateDefinedFieldDataDT != null) {
	           stateDefinedFieldDataDT.setBusinessObjNm(businessObjNm);
	           stateDefinedFieldDataDT.setBusinessObjUid(businessOjbUid);
	           if (stateDefinedFieldDataDT.getLdfUid() != null &&
	               stateDefinedFieldDataDT.getLdfUid().longValue() > 0) {
	             //we want to do the insert here
	
	             //make sure to set the versionCtrl number
	             /**
	              * The version control number is increased explicitly for edit process as
	              * version control number cannot be retreived correctly if the process is in the
	              * same transaction.
	              */
	             if(!stateDefinedFieldDataDT.isItDirty() && versionCtrlNbr.intValue()==1)
	             {
	               stateDefinedFieldDataDT.setVersionCtrlNbr(new Integer(versionCtrlNbr.intValue()));
	             }
	             else
	               stateDefinedFieldDataDT.setVersionCtrlNbr(new Integer(versionCtrlNbr.intValue()+ 1));
	             stateDefinedFieldDataDT.setItDirty(false);
	             stateDefinedFieldDataDT.setItNew(true);
	             this.insertLDF(stateDefinedFieldDataDT);
	           }
	
	         }
	       }
	
	     }
  	}catch(Exception e){
	   logger.fatal("Exception  = "+e.getMessage(), e);
       throw new NEDSSSystemException(e.toString());
  	}




  }

  private void updateLDF (StateDefinedFieldDataDT stateDefinedFieldDataDT) throws NEDSSSystemException, NEDSSConcurrentDataException {

    try {

        ArrayList<Object>  ldfDataUpdList = new ArrayList<Object> ();
        ldfDataUpdList.add(stateDefinedFieldDataDT.getBusinessObjNm());
        ldfDataUpdList.add( stateDefinedFieldDataDT.getAddTime());
        ldfDataUpdList.add( stateDefinedFieldDataDT.getLastChgTime());
        ldfDataUpdList.add( stateDefinedFieldDataDT.getLdfValue());
        ldfDataUpdList.add( stateDefinedFieldDataDT.getVersionCtrlNbr());
        ldfDataUpdList.add( stateDefinedFieldDataDT.getLdfUid());
        ldfDataUpdList.add( stateDefinedFieldDataDT.getBusinessObjUid());
        Long resultCount = (Long) preparedStmtMethod(stateDefinedFieldDataDT,ldfDataUpdList,UPDATE_LDF,"UPDATE");
      } catch(Exception sqle) {
    	  logger.fatal("Exception  = "+sqle.getMessage(), sqle);
          throw new NEDSSSystemException( sqle.toString() );
      }
  }//updateLDF

  /**
   *
   */
  private long insertLDF(StateDefinedFieldDataDT stateDefinedFieldDataDT) throws NEDSSSystemException {

    UidGeneratorHelper uidGen = null;
     Integer resultCount  = null;

    try {
      ArrayList<Object>  insLDFList = new ArrayList<Object> ();
      insLDFList.add(stateDefinedFieldDataDT.getLdfUid());
      insLDFList.add(stateDefinedFieldDataDT.getBusinessObjNm());
      insLDFList.add(stateDefinedFieldDataDT.getAddTime() == null ? new Timestamp(new Date().getTime()): stateDefinedFieldDataDT.getAddTime());
      insLDFList.add(stateDefinedFieldDataDT.getBusinessObjUid());
      insLDFList.add(stateDefinedFieldDataDT.getLastChgTime());
      insLDFList.add(stateDefinedFieldDataDT.getLdfValue());
      insLDFList.add(stateDefinedFieldDataDT.getVersionCtrlNbr());
      resultCount = (Integer)preparedStmtMethod(stateDefinedFieldDataDT,insLDFList,INSERT_LDF, "INSERT");
      if(resultCount.longValue() <1)
        throw new NEDSSSystemException("insert failed for StateDefinedFieldData");
      } catch(Exception ex) {
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
          throw new NEDSSSystemException(ex.toString());
      }
      return resultCount.longValue();
  }//insertLDF

    /**
     *
     */
    public void remove(Long businessObjectUid) throws NEDSSDAOSysException, NEDSSSystemException {
    	try{
            removeLDF(businessObjectUid);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     *
     */
     private void removeLDF(Long businessObjUid) throws NEDSSSystemException {

        StateDefinedFieldDataDT stateDefinedFieldDataDT = new StateDefinedFieldDataDT();
        Integer resultCount = null;

        /*First insert all of the records that will be deleted to the history table*/
        try {
	          ArrayList<Object>  arrayList = new ArrayList<Object> ();
	          arrayList.add(businessObjUid);
	          resultCount = (Integer)preparedStmtMethod(stateDefinedFieldDataDT,arrayList,INSERT_HISTORY, "INSERT");
	          logger.info("inserted "+resultCount+" state_defined_field_data_history records.");
	      } catch(Exception ex) {
	    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	          throw new NEDSSSystemException(ex.toString());
	      }
       /*Now delete all the records related to this bus obj uid. */
        try {
	          ArrayList<Object>  arrayList = new ArrayList<Object> ();
	          arrayList.add(businessObjUid);
	
	          resultCount = (Integer)preparedStmtMethod(stateDefinedFieldDataDT,arrayList,DELETE_LDF, "DELETE");
	          logger.info("deleted "+resultCount+" state_defined_field_data records.");
	      } catch(Exception ex) {
	    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	          throw new NEDSSSystemException(ex.toString());
	      }

    }//end of removing places

    /**
     *
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> getLDFCollection(Long busObjectUid, String conditionCode) throws NEDSSSystemException {

        StateDefinedFieldDataDT stateDefinedFieldDataDT = new StateDefinedFieldDataDT();
        ArrayList<Object>  pList = new ArrayList<Object> ();
        try
        {
            StringBuffer query = new StringBuffer(SELECT_LDF);
            if (conditionCode != null) //only include this where clause when the cond code is not null
              query.append(this.SELECT_LDF_COND_CD_WHERE_CLAUSE);
            query.append(this.SELECT_LDF_ORDER_BY);

            pList.add(busObjectUid);
            if (conditionCode != null)
              pList.add(conditionCode); //set the cond code if it is not null
            pList = (ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldDataDT, pList,query.toString(), NEDSSConstants.SELECT);

        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        return pList;
    }//end of selecting place



    /**
     * Pam Conversion specific method.
     * @param businessObjUid
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public void removeLDFForPamConversion(Long businessObjUid) throws NEDSSSystemException {
    	try{
    		removeLDF(businessObjUid);
    	}catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
    }

}//StateDefinedFieldDAO
