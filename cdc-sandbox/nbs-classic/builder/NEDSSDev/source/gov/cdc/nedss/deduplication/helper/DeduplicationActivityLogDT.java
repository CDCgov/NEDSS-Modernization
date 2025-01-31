//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\deduplication\\helper\\DeduplicationActivityLogDT.java

package gov.cdc.nedss.deduplication.helper;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.DirtyMarkerInterface;
import java.sql.Timestamp;

public class DeduplicationActivityLogDT extends AbstractVO implements DirtyMarkerInterface 
{
	private static final long serialVersionUID = 1L;
   private Long deduplicationActivityLogUid;
   private Timestamp batchStartTime;
   private Timestamp batchEndTime;
   private Integer mergedRecordsIdentifiedNbr;
   private Integer mergedRecordsSurvivedNbr;
   private String overrideInd;
   private Integer similarGroupNbr;
   private String ProcessType;  //dedup changes
   private String ProcessException; // dedup changes 
   
   /**
    * @roseuid 3EAD29A10222
    */
   public DeduplicationActivityLogDT() 
   {
    
   }
   
   /**
    * Access method for the deduplicationActivityLogUid property.
    * 
    * @return   the current value of the deduplicationActivityLogUid property
    */
   public Long getDeduplicationActivityLogUid() 
   {
      return deduplicationActivityLogUid;    
   }
   
   /**
    * Sets the value of the deduplicationActivityLogUid property.
    * 
    * @param aDeduplicationActivityLogUid the new value of the deduplicationActivityLogUid property
    */
   public void setDeduplicationActivityLogUid(Long aDeduplicationActivityLogUid) 
   {
      deduplicationActivityLogUid = aDeduplicationActivityLogUid;    
   }
   
   /**
    * Access method for the batchStartTime property.
    * 
    * @return   the current value of the batchStartTime property
    */
   public Timestamp getBatchStartTime() 
   {
      return batchStartTime;    
   }
   
   /**
    * Sets the value of the batchStartTime property.
    * 
    * @param aBatchStartTime the new value of the batchStartTime property
    */
   public void setBatchStartTime(Timestamp aBatchStartTime) 
   {
      batchStartTime = aBatchStartTime;    
   }
   
   /**
    * Access method for the batchEndTime property.
    * 
    * @return   the current value of the batchEndTime property
    */
   public Timestamp getBatchEndTime() 
   {
      return batchEndTime;    
   }
   
   /**
    * Sets the value of the batchEndTime property.
    * 
    * @param aBatchEndTime the new value of the batchEndTime property
    */
   public void setBatchEndTime(Timestamp aBatchEndTime) 
   {
      batchEndTime = aBatchEndTime;    
   }
   
   /**
    * Access method for the mergedRecordsIdentifiedNbr property.
    * 
    * @return   the current value of the mergedRecordsIdentifiedNbr property
    */
   public Integer getMergedRecordsIdentifiedNbr() 
   {
      return mergedRecordsIdentifiedNbr;    
   }
   
   /**
    * Sets the value of the mergedRecordsIdentifiedNbr property.
    * 
    * @param aMergedRecordsIdentifiedNbr the new value of the mergedRecordsIdentifiedNbr property
    */
   public void setMergedRecordsIdentifiedNbr(Integer aMergedRecordsIdentifiedNbr) 
   {
      mergedRecordsIdentifiedNbr = aMergedRecordsIdentifiedNbr;    
   }
   
   /**
    * Access method for the mergedRecordsSurvivedNbr property.
    * 
    * @return   the current value of the mergedRecordsSurvivedNbr property
    */
   public Integer getMergedRecordsSurvivedNbr() 
   {
      return mergedRecordsSurvivedNbr;    
   }
   
   /**
    * Sets the value of the mergedRecordsSurvivedNbr property.
    * 
    * @param aMergedRecordsSurvivedNbr the new value of the mergedRecordsSurvivedNbr property
    */
   public void setMergedRecordsSurvivedNbr(Integer aMergedRecordsSurvivedNbr) 
   {
      mergedRecordsSurvivedNbr = aMergedRecordsSurvivedNbr;    
   }
   
   /**
    * Access method for the overrideInd property.
    * 
    * @return   the current value of the overrideInd property
    */
   public String getOverrideInd() 
   {
      return overrideInd;    
   }
   
   /**
    * Sets the value of the overrideInd property.
    * 
    * @param aOverrideInd the new value of the overrideInd property
    */
   public void setOverrideInd(String aOverrideInd) 
   {
      overrideInd = aOverrideInd;    
   }
   
   /**
    * Access method for the similarGroupNbr property.
    * 
    * @return   the current value of the similarGroupNbr property
    */
   public Integer getSimilarGroupNbr() 
   {
      return similarGroupNbr;    
   }
   
   /**
    * Sets the value of the similarGroupNbr property.
    * 
    * @param aSimilarGroupNbr the new value of the similarGroupNbr property
    */
   public void setSimilarGroupNbr(Integer aSimilarGroupNbr) 
   {
      similarGroupNbr = aSimilarGroupNbr;    
   }
   
   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3EAD2A25002E
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) 
   {
    return true;
   }
   
   /**
    * @param itDirty
    * @roseuid 3EAD2A25003F
    */
   public void setItDirty(boolean itDirty) 
   {
    
   }
   
   /**
    * @return boolean
    * @roseuid 3EAD2A25004F
    */
   public boolean isItDirty() 
   {
    return true;
   }
   
   /**
    * @param itNew
    * @roseuid 3EAD2A250050
    */
   public void setItNew(boolean itNew) 
   {
    
   }
   
   /**
    * @return boolean
    * @roseuid 3EAD2A25005E
    */
   public boolean isItNew() 
   {
    return true;
   }
   
   /**
    * @param itDelete
    * @roseuid 3EAD2A25006D
    */
   public void setItDelete(boolean itDelete) 
   {
    
   }
   
   /**
    * @return boolean
    * @roseuid 3EAD2A25006F
    */
   public boolean isItDelete() 
   {
    return true;
   }

public String getProcessType() {
	return ProcessType;
}

public void setProcessType(String processType) {
	ProcessType = processType;
}

public String getProcessException() {
	return ProcessException;
}

public void setProcessException(String processException) {
	ProcessException = processException;
}
   
   
}
