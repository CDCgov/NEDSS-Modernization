package gov.cdc.nedss.deduplication.dedupbatchprocess;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.deduplication.helper.DeduplicationActivityLogDT;
import gov.cdc.nedss.deduplication.vo.DeduplicationPatientMergeVO;
import gov.cdc.nedss.exception.NEDSSSystemException;

import javax.rmi.PortableRemoteObject;
import javax.ejb.CreateException;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: cdhanala
 * Date: Apr 8, 2003
 * Time: 10:57:49 AM
 * To change this template use Options | File Templates.
 */
public class DeDuplicationBatchProcessor
{
	static final LogUtils logger = new LogUtils(DeDuplicationBatchProcessor.class.getName());
        private Timestamp batchStartTime;
        private Timestamp batchEndTime;
        private MainSessionCommand mainSessionCommand = null;
        private Integer autoMergeRecordsId = new Integer(0);
        private Integer autoMergeRecordsSurvive = new Integer(0);
        private Integer similarGroupsId = new Integer(0);

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
           Iterator<Object>  iterator = inSet.iterator();

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
           Iterator<Object>  iterator = inCollection.iterator();

            if(iterator.hasNext())
            {
                similarGroupsId = (Integer)iterator.next();
            }
        }

        public static void main(String[] args) throws Exception {
		Date startDate = new Date();
		System.out.println("DeduplicationBatchProcess begin: " + startDate);
		PropertyUtil propertyUtil;
		propertyUtil = PropertyUtil.getInstance();

		DeDuplicationBatchProcessor deDuplicationBatchProcessor = new DeDuplicationBatchProcessor();
		MainSessionCommand ms = deDuplicationBatchProcessor
				.getMainSessionCommand();

		String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
		String methodName = "setDeduplicationActivityLog";
		NBSSecurityObj nbsSecurityObj = deDuplicationBatchProcessor
				.obtainNBSSecurityObject();
		Object[] params = new Object[] {};
		deDuplicationBatchProcessor.setBatchStartTime(new Timestamp(System
				.currentTimeMillis()));
		deDuplicationBatchProcessor
				.resetPatientRegistryForDedup(nbsSecurityObj);
		deDuplicationBatchProcessor.processBatch(nbsSecurityObj);
		deDuplicationBatchProcessor.setBatchEndTime(new Timestamp(System
				.currentTimeMillis()));
		//PropertyUtil.setDeDupOverride("FALSE");
		deDuplicationBatchProcessor.setOverride(false);
		params = new Object[] { deDuplicationBatchProcessor
				.prepareDeDuplicationActivityLogDT("T") };
		deDuplicationBatchProcessor.getMainSessionCommand().processRequest(
				jndiName, methodName, params);

		Date endDate = new Date();
		System.out.println("DeduplicationBatchProcess end: " + endDate);
		return;
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
        private void setOverride(boolean override)throws Exception {
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
        private boolean isOverride() throws Exception{
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
            String methodName1 = "similarPatientMatch";
            String methodName2 = "processSimilarQueue";
            String methodName3 = "createPersonMergeDt";

            Object[] params = new Object[] {};

            try
            {

                ArrayList<?> arrayList = getMainSessionCommand().processRequest(jndiName, methodName, params);
                DeduplicationPatientMergeVO deduplicationPatientMergeVO = (DeduplicationPatientMergeVO)arrayList.get(0);

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
                }

                arrayList = getMainSessionCommand().processRequest(jndiName, methodName1, params);
                this.setSimilarGroupsId((Integer)arrayList.get(0));

                getMainSessionCommand().processRequest(jndiName, methodName2, params);

            }
            catch (RemoteException e)
            {
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
        private DeduplicationActivityLogDT prepareDeDuplicationActivityLogDT(String inString) throws Exception
        {

            DeduplicationActivityLogDT deduplicationActivityLogDT = new DeduplicationActivityLogDT();

            deduplicationActivityLogDT.setBatchStartTime(this.getBatchStartTime());
            deduplicationActivityLogDT.setBatchEndTime(this.getBatchEndTime());
            deduplicationActivityLogDT.setMergedRecordsSurvivedNbr(this.getAutoMergeRecordsSurvive());
            deduplicationActivityLogDT.setMergedRecordsIdentifiedNbr(this.getAutoMergeRecordsId());
            deduplicationActivityLogDT.setSimilarGroupNbr(this.getSimilarGroupsId());
            deduplicationActivityLogDT.setOverrideInd(inString);
            //Leaving the uidGeneratorHelper be set in side of the JVM where DAO resides...
            //deduplicationActivityLogDT.setDeduplicationActivityLogUid(uidGeneratorHelper.getNbsIDLong(UidClassCodes.DEDUPLICATION_CLASS_CODE));

            return deduplicationActivityLogDT;
        }

        public static void deDuplicationProcess() throws Exception {
		Date startDate = new Date();
		logger.debug("DeduplicationBatchProcess begin: " + startDate);

		DeDuplicationBatchProcessor deDuplicationBatchProcessor = new DeDuplicationBatchProcessor();
		NBSSecurityObj nbsSecurityObj = deDuplicationBatchProcessor
				.obtainNBSSecurityObject();
		//The override is 'FALSE' and the idea is to run the batch if things are quiet...
		//if(DeDuplicationQueueHelper.getDedupTakenQueue().size() == 0 && DeDuplicationQueueHelper.getDedupAvailableQueue().size() == 0)
		String jndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
		String methodName = "setDeduplicationActivityLog";
		Object[] params = new Object[] {};
		deDuplicationBatchProcessor.setBatchStartTime(new Timestamp(System
				.currentTimeMillis()));
		deDuplicationBatchProcessor
				.resetPatientRegistryForDedup(nbsSecurityObj);
		deDuplicationBatchProcessor.processBatch(nbsSecurityObj);
		deDuplicationBatchProcessor.setBatchEndTime(new Timestamp(System
				.currentTimeMillis()));
		params = new Object[] { deDuplicationBatchProcessor
				.prepareDeDuplicationActivityLogDT("T") };
		deDuplicationBatchProcessor.getMainSessionCommand().processRequest(
				jndiName, methodName, params);

		Date endDate = new Date();
		logger.debug("DeduplicationBatchProcess end: " + endDate);
		return;
        }

}
