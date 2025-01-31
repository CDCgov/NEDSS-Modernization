//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\systemservice\\mprupdateengine\\ejb\\MPRUpdateBusinessInterface.java

package gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.Collection;


/**
 *
 * <p>Title: MPRUpdateBusinessInterface</p>
 * <p>Description: This is the one and only business interface exposed by MPR Update Engine to its
clients.  Clients of MPR Update Engine use this interface to invoke services
provided by the component.
</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author not attributable
 * @version 1.0
 */
public interface MPRUpdateBusinessInterface
{

   /**
   Updates MPR with a patient revision.  The MPR to be updated will be retrieved
   from the database using information contained in the patient revision,
   newPersonVO.  The retrieved MPR will be updated with the demographics
   information contained in newPersonVO.  The updated MPR will be stored in the
   database.
   @param newRevision
   @return boolean
   @throws java.rmi.RemoteException
   @throws gov.cdc.nedss.systemservice.mprupdateengine.exception.MPRUpdateException
    */
   public boolean updateWithRevision(PersonVO newRevision, NBSSecurityObj secObj) throws RemoteException, MPRUpdateException;

   /**
   Updates survivingMPR with demographics information contained in the
   supercededMPRs collection.  Both items in supercededMPRs and the survivingMPR
   must be an object representing an MPR.  After the merge, the survivingMPR will
   be stored in the database.
   @param survivingMPR
   @param percededMPRs
   @return boolean
   @throws gov.cdc.nedss.systemservice.mprupdateengine.exception.MPRUpdateException
   @throws java.rmi.RemoteException
    */
   public boolean updateWithMPRs(PersonVO survivingMPR, Collection<Object> percededMPRs, NBSSecurityObj secObj) throws MPRUpdateException, RemoteException;

   /**
   Updates MPR with a patient revision.  The parameter, mpr, represents the MPR to
   be updated.  The MPR will be updated with the demographics information
   contained in newPersonVO.  The updated MPR will be stored in the database.
   @param mpr
   @param newRevision
   @return boolean
   @throws java.rmi.RemoteException
   @throws gov.cdc.nedss.systemservice.mprupdateengine.exception.MPRUpdateException
    */
   public boolean updateWithRevision(PersonVO mpr, PersonVO newRevision, NBSSecurityObj secObj) throws RemoteException, MPRUpdateException;
}
