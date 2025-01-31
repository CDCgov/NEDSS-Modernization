package gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean;

import java.util.Collection;
import java.util.HashMap;
import java.sql.Timestamp;
import java.rmi.RemoteException;
import gov.cdc.nedss.deduplication.exception.*;
import gov.cdc.nedss.deduplication.helper.DeduplicationActivityLogDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.deduplication.vo.*;

public interface DeDuplicationBusinessInterface
{

   /**
    * @param personVOCollection
    * @param nbsSecurityObj
    * @roseuid 3E66585C007D
    */
    public DeduplicationPatientMergeVO mergeMPR(Collection<Object> personVOCollection, String specifiedSurvivorPatientId, NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public Collection<Object> getSimilarGroup(Long groupNbr, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSDeduplicationException;

    public DeduplicationPatientMergeVO samePatientMatch(NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public DeduplicationSimilarPatientVO similarPatientMatch(NBSSecurityObj nbsSecurityObj) throws RemoteException;
    
   // public Integer similarPatientMatch(NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public void processSimilarQueue(NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public Integer getActiveGroupNumberCount(NBSSecurityObj nbsSecurityOb) throws RemoteException;

   // public void setDeduplicationActivityLog(DeduplicationActivityLogDT deduplicationActivityLogDT, NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public void setSameDeduplicationActivityLog(DeduplicationActivityLogDT deduplicationActivityLogDT, NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public void setSimilarDeduplicationActivityLog(DeduplicationActivityLogDT deduplicationActivityLogDT, NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public void createPersonMergeDt(Collection<Object> mergeDtColl, NBSSecurityObj nbsSecurityObj) throws RemoteException;

    public Integer getDedupTakenQueueSize(NBSSecurityObj nbsSecurityOb) throws RemoteException;

    public Integer getDedupAvailableQueueSize(NBSSecurityObj nbsSecurityOb) throws RemoteException;
    /**
   * Calls PropertyUtil.setDedup_Override()
   *
   * @param override
   * @param nbsSecurityObj
   * @roseuid 3EBFC292035B
   */
  public void setDedupOverride(Boolean override, NBSSecurityObj nbsSecurityObj)throws RemoteException;

  /**
   * Calls PropertyUtil.getDedup_Override()
   *
   * @param nbsSecurityObj
   * @return java.lang.Boolean
   * @roseuid 3EBFC2C3003E
   */
  public Boolean getDedupOverride(NBSSecurityObj nbsSecurityObj) throws RemoteException;

  public Timestamp getLastBatchProcessTime(NBSSecurityObj nbsSecurityObj) throws RemoteException;

  public void resetPatientRegistryForDedup(NBSSecurityObj nbsSecurityObj) throws RemoteException;
  
  public void removeFromMergeForDedup(String patientUid, NBSSecurityObj nbsSecurityObj) throws RemoteException;

}
