//Source file: C:\\Development\\Source\\gov\\cdc\\nedss\\wum\\helpers\\PublicHealthCaseVO.java

package gov.cdc.nedss.act.publichealthcase.vo;

/**
* Name:		PublicHealthCaseVO.java
* Description:	This is a value object used for identifying a public health case
*               and its associated confirmation methods.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import  gov.cdc.nedss.util.*;

import java.util.*;

import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;


public class PublicHealthCaseVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
 //  private boolean itNew = false;
  // private boolean itDirty = true;
	private boolean isPamCase;
	private CaseManagementDT theCaseManagementDT = new CaseManagementDT();
	private PublicHealthCaseDT thePublicHealthCaseDT = new PublicHealthCaseDT();
   private Collection<Object> theConfirmationMethodDTCollection;
   private Collection<Object> theActIdDTCollection;
   public Collection<Object> theActivityLocatorParticipationDTCollection;
   //Collections added for Participation and Activity Relationship object association
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theActRelationshipDTCollection;
   public Collection<Object> nbsCaseEntityCollection;
   public Collection<Object> nbsAnswerCollection;
   public Collection<EDXActivityDetailLogDT> edxPHCRLogDetailDTCollection;
   public Collection<EDXEventProcessDT> edxEventProcessDTCollection;
   

private String errorText;
   private boolean isCoinfectionCondition;

/**
    * @roseuid 3BD049620097
    */
   public PublicHealthCaseVO()
   {

   }
   
   public Collection<EDXEventProcessDT> getEdxEventProcessDTCollection() {
		return edxEventProcessDTCollection;
	}

	public void setEdxEventProcessDTCollection(
			Collection<EDXEventProcessDT> edxEventProcessDTCollection) {
		this.edxEventProcessDTCollection = edxEventProcessDTCollection;
	}

   public CaseManagementDT getTheCaseManagementDT() {
	return theCaseManagementDT;
	}
	
	public void setTheCaseManagementDT(CaseManagementDT theCaseManagementDT) {
		this.theCaseManagementDT = theCaseManagementDT;
	}

public PublicHealthCaseVO(PublicHealthCaseDT thePublicHealthCaseDT,
              Collection<Object> theConfirmationMethodDTCollection, Collection<Object> theActIdDTCollection,
              Collection<Object> theActivityLocatorParticipationDTCollection,
              Collection<Object> theParticipationDTCollection,
              Collection<Object> theActRelationshipDTCollection)
   {
        this.thePublicHealthCaseDT = thePublicHealthCaseDT;
        this.theConfirmationMethodDTCollection  = theConfirmationMethodDTCollection;
        this.theActIdDTCollection  = theActIdDTCollection;
        this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
        this.theParticipationDTCollection  = theParticipationDTCollection;
        this.theActRelationshipDTCollection  = theActRelationshipDTCollection;
        setItNew(true);
   }

  

public Collection<EDXActivityDetailLogDT> getEdxPHCRLogDetailDTCollection() {
		return edxPHCRLogDetailDTCollection;
	}

	public void setEdxPHCRLogDetailDTCollection(
			Collection<EDXActivityDetailLogDT> edxPHCRLogDetailDTCollection) {
		this.edxPHCRLogDetailDTCollection = edxPHCRLogDetailDTCollection;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public PublicHealthCaseDT getThePublicHealthCaseDT()
   {
      return thePublicHealthCaseDT;
   }

   public void setThePublicHealthCaseDT(PublicHealthCaseDT aPublicHealthCaseDT)
   {
      this.thePublicHealthCaseDT = aPublicHealthCaseDT;
   }

   public Collection<Object> getTheConfirmationMethodDTCollection()
   {
      return theConfirmationMethodDTCollection;
   }

   public void setTheConfirmationMethodDTCollection(Collection<Object> aConfirmationMethodDTCollection)
   {
      this.theConfirmationMethodDTCollection  = aConfirmationMethodDTCollection;
   }

   public Collection<Object> getTheActIdDTCollection()
   {
      return theActIdDTCollection;
   }

   public void setTheActIdDTCollection(Collection<Object> aActIdDTCollection)
   {
      this.theActIdDTCollection  = aActIdDTCollection;
   }

   public Collection<Object> getTheActivityLocatorParticipationDTCollection() {
          return theActivityLocatorParticipationDTCollection;
   }
   public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
      this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
      setItDirty(true);
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3BD0496200BF
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3BD0496201A5
    */
   public void setItDirty(boolean itDirty)
   {
        this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3BD049620200
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3BD04962021E
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3BD049620278
    */
   public boolean isItNew()
   {
    return itNew;
   }
     public boolean isItDelete()
   {
    return itDelete;
   }
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }
   //Role and participation collection entered by John Park
   public Collection<Object> getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    *
    * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
     theParticipationDTCollection  = aTheParticipationDTCollection;
   }
   /**
    * Access method for the theActRelationshipDT property.
    */
   public Collection<Object> getTheActRelationshipDTCollection()
   {
     return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    *
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
    {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }

 public ActIdDT getActIdDT_s(int index) {
      // this should really be in the constructor
      if (this.theActIdDTCollection  == null)
          this.theActIdDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theActIdDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theActIdDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ActIdDT tempDT = (ActIdDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ActIdDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ActIdDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theActIdDTCollection.add(tempDT);
        }

        return tempDT;
  }


  public ActivityLocatorParticipationDT getActLocatorParticipationDT_s(int index) {
      // this should really be in the constructor
      if (this.theActivityLocatorParticipationDTCollection  == null)
          this.theActivityLocatorParticipationDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theActivityLocatorParticipationDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theActivityLocatorParticipationDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ActivityLocatorParticipationDT tempDT = (ActivityLocatorParticipationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ActivityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ActivityLocatorParticipationDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theActivityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
  }
  public ConfirmationMethodDT getConfirmationMethodDT_s(int index) {
      // this should really be in the constructor
      if (this.theConfirmationMethodDTCollection  == null)
          this.theConfirmationMethodDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theConfirmationMethodDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theConfirmationMethodDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ConfirmationMethodDT tempDT = (ConfirmationMethodDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ConfirmationMethodDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ConfirmationMethodDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theConfirmationMethodDTCollection.add(tempDT);
        }

        return tempDT;
  }

public boolean isPamCase() {
	return isPamCase;
}

public void setPamCase(boolean isPamCase) {
	this.isPamCase = isPamCase;
}

public Collection<Object> getNbsAnswerCollection() {
	return nbsAnswerCollection;
}

public void setNbsAnswerCollection(Collection<Object> nbsAnswerCollection) {
	this.nbsAnswerCollection  = nbsAnswerCollection;
}

public Collection<Object> getNbsCaseEntityCollection() {
	return nbsCaseEntityCollection;
}

public void setNbsCaseEntityCollection(Collection<Object> nbsCaseEntityCollection) {
	this.nbsCaseEntityCollection  = nbsCaseEntityCollection;
}

public boolean isCoinfectionCondition() {
	return isCoinfectionCondition;
}

public void setCoinfectionCondition(boolean isCoinfectionCondition) {
	this.isCoinfectionCondition = isCoinfectionCondition;
}

}