//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\proxy\\ejb\\observationproxyejb\\vo\\LabResultProxyVO.java

/**
 * Title: LabResultProxyVO class.
 * Description: A proxy Value Object build for LabReports which comprises
 * of acts and entity ValueObjects. This proxy is used to add / update /delete
 * exisiting or new acts/entities.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author nmallela
 * @version 1.0
 */


package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.RoleDT;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.ArrayList;

import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;

public class LabResultProxyVO extends PageActProxyVO
{
	private static final long serialVersionUID = 1L;
   public boolean associatedNotificationInd;
	private Long sendingFacilityUid;
	public boolean associatedInvInd=false;
	//private Collection<Object> thePersonVOCollection;
   private Collection<ObservationVO> theObservationVOCollection;
   //private Collection<Object> theOrganizationVOCollection;
   private Collection<Object> theMaterialVOCollection;
   //private Collection<Object> theParticipationDTCollection;
 //  private Collection<Object> theActRelationshipDTCollection;
   private Collection<Object> theRlDTCollection;
   private Collection<Object> theActIdDTCollection;
   public Collection<Object> theInterventionVOCollection;
   public Collection<Object> eDXDocumentCollection;
   private ArrayList<String> theConditionsList;
   private Collection<MessageLogDT> messageLogDCollection =null;

  
  

public Collection<MessageLogDT> getMessageLogDCollection() {
	return messageLogDCollection;
}

public void setMessageLogDCollection(Collection<MessageLogDT> messageLogDCollection) {
	this.messageLogDCollection = messageLogDCollection;
}

public Collection<Object> geteDXDocumentCollection() {
	return eDXDocumentCollection;
}

public void seteDXDocumentCollection(
		Collection<Object> eDXDocumentCollection) {
	this.eDXDocumentCollection = eDXDocumentCollection;
}

private String labClia = null;
   private boolean manualLab = false;
   public String getLabClia() {
	return labClia;
}

public void setLabClia(String labClia) {
	this.labClia = labClia;        
}

/**
    * Default constructor
    * @roseuid 3C33AA5D0190
    */
   public LabResultProxyVO()
   {

   }
   public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
   {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(baos);
       oos.writeObject(this);
       ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
       ObjectInputStream ois = new ObjectInputStream(bais);
       Object deepCopy = ois.readObject();

       return  deepCopy;
   }


   public Long getSendingFacilityUid() {
	return sendingFacilityUid;
}

public void setSendingFacilityUid(Long sendingFacilityUid) {
	this.sendingFacilityUid = sendingFacilityUid;
}

/**
    * Determines if the associatedNotificationInd property is true.
    *
    * @return   <code>true<code> if the associatedNotificationInd property is true
    */
   public boolean getAssociatedNotificationInd()
   {
      return associatedNotificationInd;
   }

   /**
    * Sets the value of the associatedNotificationInd property.
    *
    * @param aAssociatedNotificationInd the new value of the associatedNotificationInd property
    */
   public void setAssociatedNotificationInd(boolean aAssociatedNotificationInd)
   {
      associatedNotificationInd = aAssociatedNotificationInd;
   }

   /**
    * Access method for the theObservationVOCollection  property.
    *
    * @return   the current value of the theObservationVOCollection  property
    */
   public Collection<ObservationVO> getTheObservationVOCollection()
   {
      return theObservationVOCollection;
   }

   /**
    * Sets the value of the theObservationVOCollection  property.
    *
    * @param aTheObservationVOCollection  the new value of the theObservationVOCollection  property
    */
   public void setTheObservationVOCollection(Collection<ObservationVO> aTheObservationVOCollection)
   {
      theObservationVOCollection  = aTheObservationVOCollection;
	  setItDirty(true);
   }


   /**
    * Access method for the theMaterialVOCollection  property.
    *
    * @return   the current value of the theMaterialVOCollection  property
    */
   public Collection<Object> getTheMaterialVOCollection()
   {
      return theMaterialVOCollection;
   }

   /**
    * Sets the value of the theMaterialVOCollection  property.
    *
    * @param aTheMaterialVOCollection  the new value of the theMaterialVOCollection  property
    */
   public void setTheMaterialVOCollection(Collection<Object> aTheMaterialVOCollection)
   {
      theMaterialVOCollection  = aTheMaterialVOCollection;
	  setItDirty(true);
   }

   /**
    * Access method for the theParticipationDTCollection  property.
    *
    * @return   the current value of the theParticipationDTCollection  property
    
   public Collection<Object> getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    *
    * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
    
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
      theParticipationDTCollection  = aTheParticipationDTCollection;
	  setItDirty(true);
   }


   /**
    * Access method for the theRoleDTCollection  property.
    *
    * @return   the current value of the theRoleDTCollection  property
    */
   public Collection<Object> getTheRoleDTCollection()
   {
      return theRlDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    *
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
   public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
   {
      theRlDTCollection  = aTheRoleDTCollection;
          setItDirty(true);
   }

   /**
    * Comparision method between Objects
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C03E481013A
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Sets the Object to dirty as to notify backend that the Object has been modified
    * @param itDirty
    * @param itDirty
    * @roseuid 3C03E481019E
    */
   public void setItDirty(boolean itDirty)
   {
		this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * Checks whether the Object is dirty ie whether Object is modified or not
    * @return boolean
    * Checks whether the Object is dirty ie whether Object is modified or not
    * @roseuid 3C03E48101C6
    */
   public boolean isItDirty()
   {
		return itDirty;
   }

   /**
    * Sets the Object as new ie. to notify backend that this is a new Object
    * @param itNew
    * @param itNew
    * @roseuid 3C03E48101D0
    */
   public void setItNew(boolean itNew)
   {
		this.itNew = itNew;
   }

   /**
    * Checks whether the Object is new or not
    * @return boolean
    * @return boolean
    * @roseuid 3C03E481020C
    */
   public boolean isItNew()
   {
		return itNew;
   }

   /**
    * Sets the Object as delete flag ie. to notify backend that this is a new Object
    * @param itDelete
    * @param itDelete
    * @roseuid 3C03E4810220
    */
   public void setItDelete(boolean itDelete)
   {
		this.itDelete = itDelete;
   }

   /**
    * Checks whether the Object's delete flag is set to true or false
    * @return boolean
    * @return boolean
    * @roseuid 3C03E4810252
    */
   public boolean isItDelete()
   {
        return itDelete;
   }

   /**
    * @roseuid 3ED21B8C02F4
    */
   public void reset()
   {
       theObservationVOCollection   = null;
       theMaterialVOCollection  = null;
       theRlDTCollection  = null;
       theActIdDTCollection  = null;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.entity.person.vo.PersonVO
    * @roseuid 3ED21B8C03DA
    */
   public PersonVO getPersonVO_s(int index)
   {
   // this should really be in the constructor
   if (getThePersonVOCollection()  == null)
	   setThePersonVOCollection( new ArrayList<Object> ());

   int currentSize = getThePersonVOCollection().size();

   // check if we have a this many personNameDTs
   if (index < currentSize)
   {
     try {
       Object[] tempArray = getThePersonVOCollection().toArray();

       Object tempObj  = tempArray[index];

       PersonVO tempVO = (PersonVO) tempObj;

       return tempVO;
     }
     catch (Exception e) {
        //##!! System.out.println(e);
     } // do nothing just continue
   }

    PersonVO tempVO = null;

     for (int i = currentSize; i < index+1; i++)
     {
       tempVO = new PersonVO();
       getThePersonVOCollection().add(tempVO);
     }

     return tempVO;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.act.observation.vo.ObservationVO
    * @roseuid 3ED21B8F00B4
    */
   public ObservationVO getObservationVO_s(int index)
   {
   // this should really be in the constructor
   if (this.theObservationVOCollection  == null)
       this.theObservationVOCollection  = new ArrayList<ObservationVO> ();

   int currentSize = this.theObservationVOCollection.size();

   // check if we have a this many personNameDTs
   if (index < currentSize)
   {
     try {
       Object[] tempArray = this.theObservationVOCollection.toArray();

       Object tempObj  = tempArray[index];

       ObservationVO tempVO = (ObservationVO) tempObj;

       return tempVO;
     }
     catch (Exception e) {
        //##!! System.out.println(e);
     } // do nothing just continue
   }

    ObservationVO tempVO = null;

     for (int i = currentSize; i < index+1; i++)
     {
       tempVO = new ObservationVO();
       this.theObservationVOCollection.add(tempVO);
     }

     return tempVO;
   }

   /**
    * Sets the value of the theActDTCollection  property.
    * @param aActDTCollection  the new value of the theActDTCollection  property
    * @param aTheActIdDTCollection
    * @roseuid 3ED21B8F015E
    */
   public void setTheActIdDTCollection(Collection<Object> aTheActIdDTCollection)
   {
   theActIdDTCollection  = aTheActIdDTCollection;
   }

   /**
    * Access method for the theActDTCollection  property.
    * @return   the current value of the theActIdDTCollection  property
    * @roseuid 3ED21B8F0208
    */
   public Collection<Object> getTheActIdDTCollection()
   {
   return theActIdDTCollection;
   }

   /**
    * Sets the value of the theInterventionVOCollection  property.
    * @param aInterventionVOCollection  the new value of the theInterventionVOCollection  property
    * @param aInterventionVOCollection
    */
   public void setTheInterventionVOCollection(Collection<Object> aInterventionVOCollection)
   {
   theInterventionVOCollection  = aInterventionVOCollection;
   }

   /**
    * Access method for the theInterventionVOCollection  property.
    * @return   the current value of the theInterventionVOCollection  property
    */
   public Collection<Object> getTheInterventionVOCollection()
   {
   return theInterventionVOCollection;
   }


   /**
    * @param index
    * @return gov.cdc.nedss.act.actid.dt.ActIdDT
    * @roseuid 3ED21B8F026C
    */
   public ActIdDT getActDT_s(int index)
   {
   if (this.theActIdDTCollection  == null)
       this.theActIdDTCollection  = new ArrayList<Object> ();

   int currentSize = this.getTheActIdDTCollection().size();

   // check if we have a this many ActIdDTs
   if (index < currentSize)
   {
     try {
       Object[] tempArray = this.getTheActIdDTCollection().toArray();

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
       this.theActIdDTCollection.add(tempDT);
     }

     return tempDT;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.entity.material.vo.MaterialVO
    * @roseuid 3ED21B8F0316
    */
   public MaterialVO getMaterialVO_s(int index)
   {
   if (this.theMaterialVOCollection  == null)
       this.theMaterialVOCollection  = new ArrayList<Object> ();

   int currentSize = this.theMaterialVOCollection.size();

   if (index < currentSize)
   {
     try {
       Object[] tempArray = this.theMaterialVOCollection.toArray();

       Object tempObj  = tempArray[index];

       MaterialVO tempVO = (MaterialVO) tempObj;

       return tempVO;
     }
     catch (Exception e) {
        //##!! System.out.println(e);
     } // do nothing just continue
   }

    MaterialVO tempVO = null;

     for (int i = currentSize; i < index+1; i++)
     {
       tempVO = new MaterialVO();
       this.theMaterialVOCollection.add(tempVO);
     }

     return tempVO;
   }

   public boolean isManualLab() {
	   return manualLab;
   }

   public void setManualLab(boolean manualLab) {
	   this.manualLab = manualLab;
   }
	/*
	 * Get the Condition codes associated with the Resulted Tests
	 */
	public ArrayList<String> getTheConditionsList() {
		return theConditionsList;
	}
	/*
	 * Set the Condition codes associated with the Resulted Tests
	 */
	public void setTheConditionsList(ArrayList<String> theConditionsList) {
		this.theConditionsList = theConditionsList;
	}
	/**
	 * @return the associatedInvInd
	 */
	public boolean isAssociatedInvInd() {
		return associatedInvInd;
	}

	/**
	 * @param associatedInvInd the associatedInvInd to set
	 */
	public void setAssociatedInvInd(boolean associatedInvInd) {
		this.associatedInvInd = associatedInvInd;
	}
}
