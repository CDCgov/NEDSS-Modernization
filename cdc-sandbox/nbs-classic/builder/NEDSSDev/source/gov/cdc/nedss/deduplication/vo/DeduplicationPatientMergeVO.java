package gov.cdc.nedss.deduplication.vo;

import java.util.Collection;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Joe Daughtery
 * @version 1.0
 */

public class DeduplicationPatientMergeVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private Collection<Object> mergeDtColl;
  private Collection<Object> mergeConfirmationVO;
  private boolean itDirty;
  private boolean itNew;
  private boolean itDelete;
  private Integer sameRecordsID;
  private Integer survivingRecordsID;
  private PersonVO mpr;
  private Collection<Object> revisionCollection;
  private String processException;

  public DeduplicationPatientMergeVO() {
  }

  public void setMPR(PersonVO mpr) {
    this.mpr = mpr;
  }
  public PersonVO getMPR(){
    return mpr;
  }

  public void setRevisionCollection(Collection<Object> revisionCollection) {
    this.revisionCollection  = revisionCollection;
  }
  public Collection<Object> getRevisionCollection() {
    return revisionCollection;
  }

  public void setSurvivingRecordsID(Integer survivingRecordsID) {
    this.survivingRecordsID = survivingRecordsID;
  }
  public Integer getSurvivingRecordsID() {
    return survivingRecordsID;
  }


  public void setSameRecordsID(Integer sameRecordsID) {
    this.sameRecordsID = sameRecordsID;
  }
  public Integer getSameRecordsID() {
    return sameRecordsID;
  }

  public boolean isEqual(Object obj1, Object obj2, Class cl) {
    //logic needs to be determined
    throw new UnsupportedOperationException();
    //return false;
  }
  public void setItDelete(boolean itDelete) {
    this.itDelete = itDelete;
  }

  public boolean isItDelete() {
    return itDelete;
  }


  public void setItNew(boolean itNew) {
    this.itNew = itNew;
  }

  public boolean isItNew() {
    return itNew;
  }


  public void setItDirty(boolean itDirty) {
    this.itDirty = itDirty;
  }

  public boolean isItDirty() {
    return itDirty;
  }

  public void setThePersonMergeDT(Collection<Object> mergeDtColl) {
    this.mergeDtColl = mergeDtColl;
  }

  public void setTheMergeConfirmationVO(Collection<Object> mergeConfirmationVO) {
    this.mergeConfirmationVO = mergeConfirmationVO;
  }

  public Collection<Object> getTheMergeConfirmationVO() {
    return mergeConfirmationVO;
  }

  public Collection<Object> getThePersonMergeDT() {
    return mergeDtColl;
  }

public String getProcessException() {
	return processException;
}

public void setProcessException(String processException) {
	this.processException = processException;
}
  
  
}