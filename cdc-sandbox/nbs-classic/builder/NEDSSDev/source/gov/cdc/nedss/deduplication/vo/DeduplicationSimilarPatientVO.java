package gov.cdc.nedss.deduplication.vo;


import gov.cdc.nedss.util.AbstractVO;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Joe Daughtery
 * @version 1.0
 */

public class DeduplicationSimilarPatientVO extends AbstractVO {
	
  private static final long serialVersionUID = 1L;
  private String processException;
  private Integer similarrecords;

  public Integer getSimilarrecords() {
	return similarrecords;
}

public void setSimilarrecords(Integer similarrecords) {
	this.similarrecords = similarrecords;
}

public String getProcessException() {
	return processException;
}

public void setProcessException(String processException) {
	this.processException = processException;
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

  
}