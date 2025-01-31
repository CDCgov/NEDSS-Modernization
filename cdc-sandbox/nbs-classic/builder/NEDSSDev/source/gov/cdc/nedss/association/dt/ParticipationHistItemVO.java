package gov.cdc.nedss.association.dt;

import gov.cdc.nedss.util.*;
import java.sql.Timestamp;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ParticipationHistItemVO extends AbstractVO {

	private static final long serialVersionUID = 1L;
   private Long subjectEntityUid;

   private Long actUid;

   private String typeCd;

   private Integer versionCtrlNbr;

   private Timestamp lastChgTime;



  /**
    * Access method for the subjectEntityUid property.
    *
    * @return subjectEntityUid : Long
    */
   public Long getSubjectEntityUid()
   {
      return subjectEntityUid;
   }

   /**
    * Access method for the actUid property.
    *
    * @return actUid : Long
    */
   public Long getActUid()
   {
      return actUid;
   }

   /**
    * Access method for the typeCd property.
    *
    * @return typeCd : String
    */
   public String getTypeCd()
   {
      return typeCd;
   }

   /**
    * Sets the value of the SubjectEntityUid property.
    *
    * @param aSubjectEntityUid : Long
    * @return void
    */
   public void setSubjectEntityUid(Long aSubjectEntityUid)
   {
      subjectEntityUid = aSubjectEntityUid;
   }

   /**
    * Sets the value of the actUid property.
    *
    * @param aActUid : Long
    * @return void
    */
   public void setActUid(Long aActUid)
   {
      actUid = aActUid;
   }

   /**
    * Sets the value of the typeCd property.
    *
    * @param aTypeCd : String
    */
   public void setTypeCd(String aTypeCd)
   {
      typeCd = aTypeCd;
   }

   /**
    * Access method for the versionCtrlNbr property.
    *
    * @return  versionCtrlNbr : Integer
    */
   public Integer getVersionCtrlNbr()
   {
      return versionCtrlNbr;
   }

   /**
    * Setter for the record version
    * @param versionCtrlNbr : Integer
    * @return void
    */
   public void setVersionCtrlNbr(Integer versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
   }

   /**
    * Access method for the lastChgTime property.
    *
    * @return lastChgTime : Timestamp
    */
   public Timestamp getLastChgTime()
   {
      return lastChgTime;
   }

   /**
    * Sets the value of the lastChgTime property.
    *
    * @param aLastChgTime : Timestamp
    */
   public void setLastChgTime(Timestamp aLastChgTime)
   {
      lastChgTime = aLastChgTime;
   }

   /**
    * Not implemented, does not compare parameters passed in.
    * Returns true by default
    * @param objectname1 : Object
    * @param objectname2 : Object
    * @return true : boolean
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Setter to indicate if record should be updated
    * @param itDirty : boolean
    * @return void
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * Indicates if record should be updated in database
    * @return itDirty : boolean
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * Setter to indicate if object should be
    * created in database.
    * @param itNew : boolean
    * @return void
    */
   public void setItNew(boolean itNew)
   {
     this.itNew = itNew;
   }

   /**
    * Indicates if the record is to be created in database
    *
    * @return itNew : boolean
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * Setter to indicate if the record should be deleted
    * @param itDelete : boolean
    * @return void
    */
   public void setItDelete(boolean itDelete)
   {
      this.itDelete = itDelete;
   }

   /**
    * Returns value indicating if the record is to be deleted.
    * @return itDelete : boolean
    */
   public boolean isItDelete()
   {
    return itDelete;
   }
}//end of ParticipationHistItemVO