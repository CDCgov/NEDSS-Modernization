package gov.cdc.nedss.deduplication.dedupbatchprocess;

import gov.cdc.nedss.deduplication.helper.DeduplicationActivityLogDT;
import gov.cdc.nedss.deduplication.vo.DeduplicationPatientMergeVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

/**
 * Created by IntelliJ IDEA.
 * User: cdhanala
 * Date: Apr 8, 2003
 * Time: 10:57:49 AM
 * To change this template use Options | File Templates.
 */
public class DeDuplicationSameBatchProcessor
{
	static final LogUtils logger = new LogUtils(DeDuplicationSameBatchProcessor.class.getName());
        private Timestamp batchStartTime;
        private Timestamp batchEndTime;
        private MainSessionCommand mainSessionCommand = null;
        private Integer autoMergeRecordsId = new Integer(0);
        private Integer autoMergeRecordsSurvive = new Integer(0);
        private Integer similarGroupsId = new Integer(0);
       // private String ProcessType = null;
        private String ProcessException = null;
        static boolean isBatch =  false;
        static boolean runActivitylog = false;
        private static String catchExcep = null;
        //DeDuplicationSameBatchProcessor deDuplicationSameBatchProcessor1 = new DeDuplicationSameBatchProcessor();

        
		/**
         *
         * @param inTimeStamp
         */
        public void setBatchStartTime(Timestamp inTimeStamp)
        {
            batchStartTime = inTimeStamp;
        }

        /**
         *
         * @return
         */
        public Timestamp getBatchStartTime()
        {
            return batchStartTime;
        }

        /**
         *
         * @param inTimeStamp
         */
        public void setBatchEndTime(Timestamp inTimeStamp)
        {
            batchEndTime = inTimeStamp;
        }

        /**
         *
         * @return
         */
        public Timestamp getBatchEndTime()
        {
            return batchEndTime;
        }

        /**
         *
         * @return
         */
        public Integer getAutoMergeRecordsId()
        {
            return autoMergeRecordsId;
        }

        /**
         *
         * @param inAutoMergeRecordsId
         */
        public void setAutoMergeRecordsId(Integer inAutoMergeRecordsId)
        {
            autoMergeRecordsId = inAutoMergeRecordsId;
        }

        /**
         *
         * @param inSet
         */
        public void setAutoMergeRecordsId(Set<Object> inSet)
        {
            Iterator<Object> iterator = inSet.iterator();

            if(iterator.hasNext())
            {
                autoMergeRecordsId = (Integer)iterator.next();
            }
        }

        /**
         *
         * @return
         */
        public Integer getAutoMergeRecordsSurvive()
        {
            return autoMergeRecordsSurvive;
        }

        /**
         *
         * @param inAutoMergeRecordsSurvive
         */
        public void setAutoMergeRecordsSurvive(Integer inAutoMergeRecordsSurvive)
        {
            autoMergeRecordsSurvive = inAutoMergeRecordsSurvive;
        }

        /**
         *
         * @return
         */
        public Integer getSimilarGroupsId()
        {
            return similarGroupsId;
        }

        /**
         *
         * @param inSimilarGroupsId
         */
        public void setSimilarGroupsId(Integer inSimilarGroupsId)
        {
            similarGroupsId = inSimilarGroupsId;
        }

        /**
         *
         * @param inCollection
         */
        public void setAutoMergeRecordsSurvive(Collection<Object> inCollection)
        {
            Iterator<Object> iterator = inCollection.iterator();

            if(iterator.hasNext())
            {
                similarGroupsId = (Integer)iterator.next();
            }
        }
        
      public String getProcessException() {
			return ProcessException;
		}

		public void setProcessException(String processException) {
			ProcessException = processException;
		}
		
		/*public String getCatchExcep() {
			return catchExcep;
		}

		public void setCatchExcep(String catchExcep) {
			this.catchExcep = catchExcep;
		}*/

		public static void main(String[] args) {
		Date startDate = new Date();
	    isBatch =  true;
		System.out.println("DeduplicationSameBatchProcess begin: " + startDate);
		DeDuplicationSameBatchProcessor deDuplicationSameBatchProcessor = new DeDuplicationSameBatchProcessor();
		Object[] params = new Object[] {};
		String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
		String methodName = "setSameDeduplicationActivityLog";
		try{
		NBSSecurityObj nbsSecurityObj = deDuplicationSameBatchProcessor
				.obtainNBSSecurityObject();
		
		deDuplicationSameBatchProcessor.setBatchStartTime(new Timestamp(System
				.currentTimeMillis()));
		deDuplicationSameBatchProcessor
				.resetPatientRegistryForDedup(nbsSecurityObj);
		
		      deDuplicationSameBatchProcessor.processBatch(nbsSecurityObj);
		}catch(Exception e){			
            	runActivitylog= true; 
            	catchExcep = e.toString();
            	if (catchExcep.length() >= 1999)
            		catchExcep = catchExcep.substring(0, 1999);
            	logger.fatal("error in main method of Deduplication Same batch process : "+e);  
            	//return;            	
		
		}
		try{
			
		deDuplicationSameBatchProcessor.setBatchEndTime(new Timestamp(System
				.currentTimeMillis()));
		
		//PropertyUtil.setDeDupOverride("FALSE");
		deDuplicationSameBatchProcessor.setOverride(false);
		if(runActivitylog){
			
			params = new Object[] { deDuplicationSameBatchProcessor
    				.prepareDeDuplicationActivityLogDT("T","DeDuplicationSameBatchProcessor",catchExcep) };
			if(null != SchedulerConstants.getSAMEPROCESSEXCEP())
    		SchedulerConstants.setSAMEPROCESSEXCEP(null);
			if(null != SchedulerConstants.getSAMEBATCHSTARTTIME());
    		SchedulerConstants.setSAMEBATCHSTARTTIME(null);
    		    		
    		deDuplicationSameBatchProcessor.getMainSessionCommand().processRequest(
    				jndiName, methodName, params);
    		
			
		}else{
			
		params = new Object[] { deDuplicationSameBatchProcessor
				.prepareDeDuplicationActivityLogDT("T","DeDuplicationSameBatchProcessor") };
		deDuplicationSameBatchProcessor.getMainSessionCommand().processRequest(
				jndiName, methodName, params);
		}
		runActivitylog = false;
		}
		catch(Exception ex){
			ex.printStackTrace();
			}

		Date endDate = new Date();
		System.out.println("DeduplicationSameBatchProcess end: " + endDate);
		//return;
	}

        /**
		 * Resets Patient Registry
		 *
		 * @param nbsSecurityObj
		 * @throws Exception
		 */
        private void resetPatientRegistryForDedup(NBSSecurityObj nbsSecurityObj)throws Exception {
          String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
          String methodNm = "resetPatientRegistryForDedup";
          Object[] params = new Object[] {};
          mainSessionCommand.processRequest(jndiName,methodNm, params);
        }

        /**
         * Fetches the DeDup Taken Size queue back.
         * @return
         * @throws Exception
         */
        private Integer getTakenQueueSize()throws Exception {
          String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
                  String methodName = "getDedupTakenQueueSize";
                  Object[] params = new Object[] {};

                  return (Integer)mainSessionCommand.processRequest(jndiName, methodName, params).get(0);

        }

        /**
         * Fetches the Available queue Size back.
         * @return
         * @throws Exception
         */
        private Integer getAvailableQueueSize()throws Exception {
          String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
                  String methodName = "getDedupAvailableQueueSize";
                  Object[] params = new Object[] {};

                  return (Integer)mainSessionCommand.processRequest(jndiName, methodName, params).get(0);

        }

        /**
         * Sets the property 'OVERRIDE' to TRUE.
         * @param override
         * @throws Exception
         */
        protected void setOverride(boolean override)throws Exception {
          String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
                  String methodName = "setDedupOverride";
                  Object[] params = new Object[] {new Boolean(override)};

                  obtainNBSSecurityObject();
                  mainSessionCommand.processRequest(jndiName, methodName, params);

        }

        /**
         * This method tells us if the property is Override or not.
         * @return boolean
         * @throws Exception
         */
        protected boolean isOverride() throws Exception{
          String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
                  String methodName = "getDedupOverride";
                  Object[] params = new Object[] {};
                  obtainNBSSecurityObject();
                 return ((Boolean)mainSessionCommand.processRequest(jndiName, methodName, params).get(0)).booleanValue();

        }

        /**
         * This method kicks off the batch process.
         */
        private void processBatch(NBSSecurityObj nbsSecurityObj) throws Exception
        {
            //NBSSecurityObj nbsSecurityObj = obtainNBSSecurityObject();

            String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;

            String methodName = "samePatientMatch";
           // String methodName1 = "similarPatientMatch";
          //  String methodName2 = "processSimilarQueue";
            String methodName3 = "createPersonMergeDt";

            Object[] params = new Object[] {};

            try
            {

                ArrayList<?>  arrayList = getMainSessionCommand().processRequest(jndiName, methodName, params);
                DeduplicationPatientMergeVO deduplicationPatientMergeVO = (DeduplicationPatientMergeVO)arrayList.get(0);
              //  System.out.println("matching same Patients count : "+arrayList.size());
               // Date date2 = new Date();
               // System.out.println("samePatientMatch end: " + date2);

                //Insert a entry in to the PersonMerge table...
                if(deduplicationPatientMergeVO != null)
                {
                    Object[] params1 = new Object[] {deduplicationPatientMergeVO.getThePersonMergeDT()};
                    getMainSessionCommand().processRequest(jndiName, methodName3, params1);
                }

                //Check for null is necessitated because in case the pivot size is < 2 we return a null
                //out of the samePatientMatch service...
                if(deduplicationPatientMergeVO != null)
                {
                    this.setAutoMergeRecordsId(deduplicationPatientMergeVO.getSameRecordsID());
                    this.setAutoMergeRecordsSurvive(deduplicationPatientMergeVO.getSurvivingRecordsID());
                    this.setProcessException(deduplicationPatientMergeVO.getProcessException());
                }
                
             //   Date date1 = new Date();
              //  System.out.println("createPersonMergeDt end: " + date1);
                
              /*  ArrayList<Object> arrayList = getMainSessionCommand().processRequest(jndiName, methodName1, params);
                this.setSimilarGroupsId((Integer)arrayList.get(0));
                System.out.println("matching grps : "+arrayList.get(0));
                
                Date date3 = new Date();
                System.out.println("similarPatientMatch end: " + date3);

                getMainSessionCommand().processRequest(jndiName, methodName2, params);*/

            }
            catch (RemoteException e)
            {
                
                
                logger.fatal("error in processBatch of Same Batch Processor :" + e); 
            	throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            
        }

        /**
         * Invoking the method results in a NBSSeurityObj being returned to the caller...
         * @return NBSSecurityObj
         */
        private NBSSecurityObj obtainNBSSecurityObject() throws Exception
        {
            return getMainSessionCommand().nbsSecurityLogin("dedup_batch", "dedup_batch");
        }

        public MainSessionCommand getMainSessionCommand() throws Exception
        {
            NedssUtils nedssUtils = new NedssUtils();

            if(mainSessionCommand != null) return mainSessionCommand;

            MainSessionCommandHome msCommandHome;

            try
            {
            	String sBeanName = JNDINames.MAIN_CONTROL_EJB;
                msCommandHome = (MainSessionCommandHome)PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanName), MainSessionCommandHome.class);
                mainSessionCommand = msCommandHome.create();
                return mainSessionCommand;
            }
            catch (ClassCastException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            catch (NEDSSSystemException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            catch (RemoteException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            catch (CreateException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
        }

        /**
         * This method takes the  overrideInd and prepares the DeduplicationActivityLogDT
         * and return the same to the caller.
         * @param inString
         * @return DeduplicationActivityLogDT
         * @throws Exception
         */
        private DeduplicationActivityLogDT prepareDeDuplicationActivityLogDT(String inString,String strProcess) throws Exception
        {

            DeduplicationActivityLogDT deduplicationActivityLogDT = new DeduplicationActivityLogDT();
           // logger.debug("into DeduplicationActivityLogDT for Same patient merge begin : " ); 
            deduplicationActivityLogDT.setBatchStartTime(this.getBatchStartTime());
            deduplicationActivityLogDT.setBatchEndTime(this.getBatchEndTime());
            deduplicationActivityLogDT.setMergedRecordsSurvivedNbr(this.getAutoMergeRecordsSurvive());
            deduplicationActivityLogDT.setMergedRecordsIdentifiedNbr(this.getAutoMergeRecordsId());
            deduplicationActivityLogDT.setSimilarGroupNbr(this.getSimilarGroupsId());
            deduplicationActivityLogDT.setOverrideInd(inString);
            deduplicationActivityLogDT.setProcessType(strProcess);
            deduplicationActivityLogDT.setProcessException(this.getProcessException());
            //Leaving the uidGeneratorHelper be set in side of the JVM where DAO resides...
            //deduplicationActivityLogDT.setDeduplicationActivityLogUid(uidGeneratorHelper.getNbsIDLong(UidClassCodes.DEDUPLICATION_CLASS_CODE));

            return deduplicationActivityLogDT;
        }
        
        /**
         * This method takes the  overrideInd and prepares the DeduplicationActivityLogDT
         * and return the same to the caller.
         * @param inString
         * @return DeduplicationActivityLogDT
         * @throws Exception
         */
        private DeduplicationActivityLogDT prepareDeDuplicationActivityLogDT(String inString,String strProcess,String strEmpty) throws Exception
        {

            DeduplicationActivityLogDT deduplicationActivityLogDT = new DeduplicationActivityLogDT();
            logger.debug("into DeduplicationActivityLogDT for Same patient merge begin : " );
            if(null != SchedulerConstants.getSAMEBATCHSTARTTIME()){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            java.util.Date date = sdf.parse(SchedulerConstants.getSAMEBATCHSTARTTIME());
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            deduplicationActivityLogDT.setBatchStartTime(timestamp);
            }else{
            	deduplicationActivityLogDT.setBatchStartTime(this.getBatchStartTime());
            }
            deduplicationActivityLogDT.setBatchEndTime(new Timestamp(System
    				.currentTimeMillis()));
            deduplicationActivityLogDT.setMergedRecordsSurvivedNbr(new Integer(0));
            deduplicationActivityLogDT.setMergedRecordsIdentifiedNbr(new Integer(0));
            deduplicationActivityLogDT.setSimilarGroupNbr(new Integer(0));
            deduplicationActivityLogDT.setOverrideInd(inString);
            deduplicationActivityLogDT.setProcessType(strProcess);
            if(strEmpty.length() > 0){
            	deduplicationActivityLogDT.setProcessException(strEmpty);
            }else{
            	deduplicationActivityLogDT.setProcessException(catchExcep);
            }
            //Leaving the uidGeneratorHelper be set in side of the JVM where DAO resides...
            //deduplicationActivityLogDT.setDeduplicationActivityLogUid(uidGeneratorHelper.getNbsIDLong(UidClassCodes.DEDUPLICATION_CLASS_CODE));

            return deduplicationActivityLogDT;
        }

        public static void deDuplicationProcess() throws Exception {
		Date startDate = new Date();
		logger.debug("DeduplicationSameBatchProcess begin: " + startDate);

		DeDuplicationSameBatchProcessor deDuplicationSameBatchProcessor = new DeDuplicationSameBatchProcessor();
		NBSSecurityObj nbsSecurityObj = deDuplicationSameBatchProcessor
				.obtainNBSSecurityObject();
		//The override is 'FALSE' and the idea is to run the batch if things are quiet...
		//if(DeDuplicationQueueHelper.getDedupTakenQueue().size() == 0 && DeDuplicationQueueHelper.getDedupAvailableQueue().size() == 0)
		String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
		String methodName = "setSameDeduplicationActivityLog";
		Object[] params = new Object[] {};
		deDuplicationSameBatchProcessor.setBatchStartTime(new Timestamp(System
				.currentTimeMillis()));
		deDuplicationSameBatchProcessor
				.resetPatientRegistryForDedup(nbsSecurityObj);
		deDuplicationSameBatchProcessor.processBatch(nbsSecurityObj);
		deDuplicationSameBatchProcessor.setBatchEndTime(new Timestamp(System
				.currentTimeMillis()));
		params = new Object[] { deDuplicationSameBatchProcessor
				.prepareDeDuplicationActivityLogDT("T","DeDuplicationSameBatchProcessor") };
		deDuplicationSameBatchProcessor.getMainSessionCommand().processRequest(
				jndiName, methodName, params);

		Date endDate = new Date();
		logger.debug("DeduplicationSameBatchProcess end: " + endDate);
		return;
        }
        
            public static void deDuplicationActivityProcess(String excep) throws Exception {
    		Date startDate = new Date();
    		logger.debug("DeduplicationSameBatchActivityProcess begin: " + startDate);

    		DeDuplicationSameBatchProcessor deDuplicationSameBatchProcessor = new DeDuplicationSameBatchProcessor();
    		//NBSSecurityObj nbsSecurityObj = deDuplicationSameBatchProcessor
    				//.obtainNBSSecurityObject();
    		//The override is 'FALSE' and the idea is to run the batch if things are quiet...
    		//if(DeDuplicationQueueHelper.getDedupTakenQueue().size() == 0 && DeDuplicationQueueHelper.getDedupAvailableQueue().size() == 0)
    		String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
    		String methodName = "setSameDeduplicationActivityLog";
    		Object[] params = new Object[] {};
    		params = new Object[] { deDuplicationSameBatchProcessor
    				.prepareDeDuplicationActivityLogDT("T","DeDuplicationSameBatchProcessor",excep) };
    		SchedulerConstants.setSAMEPROCESSEXCEP(null);
    		SchedulerConstants.setSAMEBATCHSTARTTIME(null);
    		    		
    		deDuplicationSameBatchProcessor.getMainSessionCommand().processRequest(
    				jndiName, methodName, params);

    		Date endDate = new Date();
    		logger.debug("DeduplicationSameBatchActivity Process end: " + endDate);
    		return;
            }

}
