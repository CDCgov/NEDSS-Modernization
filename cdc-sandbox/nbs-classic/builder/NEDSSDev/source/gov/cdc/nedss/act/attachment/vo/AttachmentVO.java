package gov.cdc.nedss.act.attachment.vo;

import gov.cdc.nedss.act.attachment.dt.AttachmentDT;
import gov.cdc.nedss.util.AbstractVO;

/**
 * Title:        AttachmentVO.java
 * Description:  Value object for the Treatment object.
 * Copyright:    Copyright (c) 2017
 * Company:      CSRA
 * @author       Fatima Lopez Calzado
 * @version      1.0
 */

public class AttachmentVO
    extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private AttachmentDT theAttachmentDT = new AttachmentDT();
  /*private Collection<Object> theTreatmentProcedureDTCollection;
  private Collection<Object> theTreatmentAdministeredDTCollection;
  private Collection<Object> theActIdDTCollection;
  private Collection<Object> theActivityLocatorParticipationDTCollection;
  //Collections added for Participation and Activity Relationship object association
  public Collection<Object> theParticipationDTCollection;
  public Collection<Object> theActRelationshipDTCollection;*/

  /**
   * @roseuid 3EAEC6C100B9
   */
  public AttachmentVO() {

  }

  /**
   * Constructor containing all attributes
   */
  public AttachmentVO(AttachmentDT theAttachmentDT) {
    this.theAttachmentDT = theAttachmentDT;/*
    this.theTreatmentProcedureDTCollection  = theTreatmentProcedureDTCollection;
    this.theTreatmentAdministeredDTCollection  =
        theTreatmentAdministeredDTCollection;
    this.theActIdDTCollection  = theActIdDTCollection;
    this.theActivityLocatorParticipationDTCollection  =
        theActivityLocatorParticipationDTCollection;*/
    setItNew(true);
  }

  /**
   * @param objectname1
   * @param objectname2
   * @param voClass
   * @return boolean
   * @roseuid 3EAECE150221
   */
  public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
    return true;
  }

  /**
   * @param itDirty
   * @roseuid 3EAECE1601A4
   */
  public void setItDirty(boolean itDirty) {
    this.itDirty = itDirty;

  }

  /**
   * @return boolean
   * @roseuid 3EAECE1602FB
   */
  public boolean isItDirty() {
    return itDirty;
  }

  /**
   * @param itNew
   * @roseuid 3EAECE1603A7
   */
  public void setItNew(boolean itNew) {
    this.itNew = itNew;
  }

  /**
   * @return boolean
   * @roseuid 3EAECE170146
   */
  public boolean isItNew() {
    return itNew;
  }

  /**
   * @param itDelete
   * @roseuid 3EAECE1701F2
   */
  public void setItDelete(boolean itDelete) {
    this.itDelete = itDelete;

  }

  /**
   * @return boolean
   * @roseuid 3EAECE17030B
   */
  public boolean isItDelete() {
    return itDelete;
  }

public AttachmentDT getTheAttachmentDT() {
	return theAttachmentDT;
}

public void setTheAttachmentDT(AttachmentDT theAttachmentDT) {
	this.theAttachmentDT = theAttachmentDT;
}



}