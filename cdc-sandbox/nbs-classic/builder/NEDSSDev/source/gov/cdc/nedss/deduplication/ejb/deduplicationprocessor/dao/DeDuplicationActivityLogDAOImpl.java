//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\deduplication\\ejb\\deduplicationprocessorejb\\dao\\DeDuplicationActivityLogDAOImpl.java

package gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao;

import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactAttachmentDAO;
import gov.cdc.nedss.deduplication.helper.DeduplicationActivityLogDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

public class DeDuplicationActivityLogDAOImpl extends DAOBase
{
	static final LogUtils logger = new LogUtils(DeDuplicationActivityLogDAOImpl.class.getName());

    private final String INSERT_ACTIVITYLOG_DT = "INSERT INTO DEDUPLICATION_ACTIVITY_LOG (" +
                                                 "DEDUPLICATION_ACTIVITY_LOG_UID, BATCH_START_TIME, " +
                                                 "BATCH_END_TIME, MERGED_RECORDS_IDENTIFIED_NBR, " +
                                                 "MERGED_RECORDS_SURVIVED_NBR, OVERRIDE_IND, " +
                                                 "SIMILAR_GROUP_NBR, PROCESS_TYPE,PROCESS_EXCEPTION ) " +
                                                 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

   /**
    * @roseuid 3EA802AC0271
    */
   public DeDuplicationActivityLogDAOImpl()
   {

   }

   /**Given a Collection<Object> as a input parameter this method iterates throught the entry's of the
    * collection and inserts a row in to the DeDuplication Activity Log table in the DataBase.
    * Basically the Audit information collected throught the batch run is persisted via this method.
    * @param obj
    * @roseuid 3EA67FC9027E
    */
  /* public void create(Collection<Object> inCollection) throws Exception
   {
        ArrayList<Object>  parameterArrayList<Object> = new ArrayList<Object> ();
        Iterator<Object> iterator = inCollection.iterator();
        UidGeneratorHelper uidGeneratorHelper = new UidGeneratorHelper();

        while(iterator.hasNext())
        {
            DeduplicationActivityLogDT dedupALDT = (DeduplicationActivityLogDT)iterator.next();

            // Special Case set the value here...
            dedupALDT.setDeduplicationActivityLogUid(uidGeneratorHelper.getNbsIDLong(UidClassCodes.DEDUPLICATION_CLASS_CODE));
            dedupALDT.setItNew(true);
            dedupALDT.setItDirty(false);
            parameterArrayList.add(dedupALDT.getDeduplicationActivityLogUid());
            parameterArrayList.add(dedupALDT.getBatchStartTime());
            parameterArrayList.add(dedupALDT.getBatchEndTime());
            parameterArrayList.add(dedupALDT.getMergedRecordsIdentifiedNbr());
            parameterArrayList.add(dedupALDT.getMergedRecordsSurvivedNbr());
            parameterArrayList.add(dedupALDT.getOverrideInd());
            parameterArrayList.add(dedupALDT.getSimilarGroupNbr());

            preparedStmtMethod(null, parameterArrayList, INSERT_ACTIVITYLOG_DT, NEDSSConstants.UPDATE);
        }
   }*/
   
   /**Given a Collection<Object> as a input parameter this method iterates throught the entry's of the
    * collection and inserts a row in to the DeDuplication Activity Log table in the DataBase.
    * Basically the Audit information collected throught the batch run is persisted via this method.
    * @param obj
    * @roseuid 3EA67FC9027E
    */
   public void createSame(Collection<Object> inCollection) throws Exception
   {
	   try{
	        ArrayList<Object>  parameterArrayList= new ArrayList<Object> ();
	        Iterator<Object> iterator = inCollection.iterator();
	        UidGeneratorHelper uidGeneratorHelper = new UidGeneratorHelper();
	
	        while(iterator.hasNext())
	        {
	            DeduplicationActivityLogDT dedupALDT = (DeduplicationActivityLogDT)iterator.next();
	
	            // Special Case set the value here...
	            dedupALDT.setDeduplicationActivityLogUid(uidGeneratorHelper.getNbsIDLong(UidClassCodes.DEDUPLICATION_CLASS_CODE));
	            dedupALDT.setItNew(true);
	            dedupALDT.setItDirty(false);
	            parameterArrayList.add(dedupALDT.getDeduplicationActivityLogUid());
	            parameterArrayList.add(dedupALDT.getBatchStartTime());
	            parameterArrayList.add(dedupALDT.getBatchEndTime());
	            parameterArrayList.add(dedupALDT.getMergedRecordsIdentifiedNbr());
	            parameterArrayList.add(dedupALDT.getMergedRecordsSurvivedNbr());
	            parameterArrayList.add(dedupALDT.getOverrideInd());
	            parameterArrayList.add(dedupALDT.getSimilarGroupNbr());
	            parameterArrayList.add(dedupALDT.getProcessType());
	            parameterArrayList.add(dedupALDT.getProcessException());
	
	            preparedStmtMethod(null, parameterArrayList, INSERT_ACTIVITYLOG_DT, NEDSSConstants.UPDATE);
	        }
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString());
	   }
   }
   
   /**Given a Collection<Object> as a input parameter this method iterates throught the entry's of the
    * collection and inserts a row in to the DeDuplication Activity Log table in the DataBase.
    * Basically the Audit information collected throught the batch run is persisted via this method.
    * @param obj
    * @roseuid 3EA67FC9027E
    */
   public void createSimilar(Collection<Object> inCollection) throws Exception
   {
	   try{
	        ArrayList<Object>  parameterArrayList= new ArrayList<Object> ();
	        Iterator<Object> iterator = inCollection.iterator();
	        UidGeneratorHelper uidGeneratorHelper = new UidGeneratorHelper();
	
	        while(iterator.hasNext())
	        {
	            DeduplicationActivityLogDT dedupALDT = (DeduplicationActivityLogDT)iterator.next();
	
	            // Special Case set the value here...
	            dedupALDT.setDeduplicationActivityLogUid(uidGeneratorHelper.getNbsIDLong(UidClassCodes.DEDUPLICATION_CLASS_CODE));
	            dedupALDT.setItNew(true);
	            dedupALDT.setItDirty(false);
	            parameterArrayList.add(dedupALDT.getDeduplicationActivityLogUid());
	            parameterArrayList.add(dedupALDT.getBatchStartTime());
	            parameterArrayList.add(dedupALDT.getBatchEndTime());
	            parameterArrayList.add(dedupALDT.getMergedRecordsIdentifiedNbr());
	            parameterArrayList.add(dedupALDT.getMergedRecordsSurvivedNbr());
	            parameterArrayList.add(dedupALDT.getOverrideInd());
	            parameterArrayList.add(dedupALDT.getSimilarGroupNbr());
	            parameterArrayList.add(dedupALDT.getProcessType());
	            parameterArrayList.add(dedupALDT.getProcessException());
	
	            preparedStmtMethod(null, parameterArrayList, INSERT_ACTIVITYLOG_DT, NEDSSConstants.UPDATE);
	        }
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString());
	   }
   }

   
}
