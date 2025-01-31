package gov.cdc.nedss.act.interview.vo;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Collection;
/**
 * Title:       InterviewVO.java
 * Description: Value object for Interview associated with an Investigation.
 * Copyright:   Copyright (c) 2013
 * Company:		Leidos
 * @author      NBS Development Team
 * @version 1.0
 */

public class InterviewVO extends AbstractVO
{
   private static final long serialVersionUID = 1L;
   private InterviewDT theInterviewDT = new InterviewDT();
   private Collection<EDXEventProcessDT> edxEventProcessDTCollection;
   
   public Collection<EDXEventProcessDT> getEdxEventProcessDTCollection() {
	return edxEventProcessDTCollection;
}

public void setEdxEventProcessDTCollection(
		Collection<EDXEventProcessDT> edxEventProcessDTCollection) {
	this.edxEventProcessDTCollection = edxEventProcessDTCollection;
}

/**
    * Constructor
    */
   public InterviewVO()
   {
   }

   /**
    * Gets InterviewDT
    * @return : InterviewDT
    */
   public InterviewDT getTheInterviewDT()
   {
      return theInterviewDT;
   }

   /**
    * 
    * @param theInterviewDT
    */
   public void setTheInterviewDT(InterviewDT theInterviewDT)
   {
      this.theInterviewDT = theInterviewDT;
      setItDirty(true);
   }

   
   
  /**
   * 
   */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * sets itDirty
    * @param itDirty : boolean value
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * gets itDirty
    * @return : boolean value
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * sets itNew
    * @param itNew : boolean value
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
    * gets itNew
    * @return : boolean value
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * gets itDelete
    * @return : boolean value
    */
    public boolean isItDelete()
   {
    return itDelete;
   }

   /**
    * sets itDelete
    * @param itDelete : boolean value
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }


 
}