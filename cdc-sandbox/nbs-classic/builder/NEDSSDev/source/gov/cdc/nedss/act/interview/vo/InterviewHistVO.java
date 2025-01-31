package gov.cdc.nedss.act.interview.vo;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2013
 * Company: Leidos
 * @version 1.0
 */

public class InterviewHistVO extends AbstractVO {

  /**
   * Default Constructor
   */
	private static final long serialVersionUID = 1L;
  public InterviewHistVO() {
  }

   private Long interviewUid;


   private Integer versionCtrlNbr;

   /**
    * InterventionDT.lastChgTime
    */
   private Timestamp lastChgTime;



  /**
    * Access method for the interviewUid property.
    *
    * @return interviewUid : Long
    */
   public Long getInterviewUid()
   {
      return interviewUid;
   }

   /**
    * Sets the value of the interviewUid property.
    *
    * @param aInterviewUid : Long
    * @return void
    */
   public void setInterviewUid(Long aInterviewUid)
   {
      interviewUid = aInterviewUid;
   }

   /**
    * Access method for the versionCtrlNbr property.
    *
    * @return versionCtrlNbr : Integer
    */
   public Integer getVersionCtrlNbr()
   {
      return versionCtrlNbr;
   }

   /**
    * Sets the value of the versionCtrlNbr property.
    *
    * @param versionCtrlNbr : Integer
    * @return void
    */
   public void setVersionCtrlNbr(Integer versionCtrlNbr)
   {
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
    * @param aLastChgTime the new value of the lastChgTime property
    */
   public void setLastChgTime(Timestamp aLastChgTime)
   {
      lastChgTime = aLastChgTime;
   }

   /**
    * Returns true by default.  Logic needs to be determined.
    * @param objectname1 : Object
    * @param objectname2 : Object
    * @return true : boolean
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Not implemented.  Need to determine if needed.
    * @param itDirty : boolean
    * @return void
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * Returns true by default.  Need to determine if needed.
    * @return true : boolean
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * Not implemented.  Need to determine if needed.
    * @param itNew : boolean
    * @return void
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * Not implemented, returns true by default.
    * Need to determine if needed.
    * @return true : boolean
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * Not implemented. Need to determine if needed.
    * @param itDelete : boolean
    * @return void
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * Not implemented.  Need to determine if needed
    * @return true : boolean
    */
   public boolean isItDelete()
   {
    return true;
   }
}//end of class