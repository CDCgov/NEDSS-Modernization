/**
 * Title: PersonVO helper class.
 * Description: A helper class for person value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.person.vo;


import  gov.cdc.nedss.util.*;
import java.util.Collection;
import java.util.ArrayList;
import java.io.*;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.phdc.IdentifierType;


public class PersonVO extends LdfBaseVO
{
	private static final long serialVersionUID = 1L;
  // private boolean itDirty = false;
  // private boolean itNew = true;
  // private boolean itDelete = false;
   public PersonDT thePersonDT = new PersonDT();
   public Collection<Object> thePersonNameDTCollection=new ArrayList<Object> (); 
   public Collection<Object> thePersonRaceDTCollection;
   public Collection<Object> thePersonEthnicGroupDTCollection;
   public Collection<Object> theEntityLocatorParticipationDTCollection;
   public Collection<Object> theEntityIdDTCollection;


//	private String custom;//custom queues
   //collections for role and participation object association added by John Park
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theRlDTCollection;

   private String defaultJurisdictionCd;
   private Boolean isExistingPatient;
   private boolean isExt = false;
   private boolean isMPRUpdateValid = true;
   private String localIdentifier;
   private String rl;
   private String addReasonCode;
   

   

   
/**
 * getRole
 * @warning This variable is only used for ELRs(since release 4.4). Do not use it for any other purposes as this may cause problems
  * @return string role for ELR 
 */
public String getRole() {
	return rl;
}

/**
 * setRole
 * @warning This variable is only used for ELRs(since release 4.4). Do not use it for any other purposes as this may cause problems
 * @param role
 */
public void setRole(String role) {
	this.rl = role;
}

public boolean isMPRUpdateValid() {
	return isMPRUpdateValid;
}

public void setMPRUpdateValid(boolean isMPRUpdateValid) {
	this.isMPRUpdateValid = isMPRUpdateValid;
}

public Boolean getIsExistingPatient() {
	return isExistingPatient;
}

public void setIsExistingPatient(Boolean isExistingPatient) {
	this.isExistingPatient = isExistingPatient;
}

public String getLocalIdentifier() {
	return localIdentifier;
}

public void setLocalIdentifier(String localIdentifier) {
	this.localIdentifier = localIdentifier;
}

public boolean isExt() {
	return isExt;
}

public void setExt(boolean isExt) {
	this.isExt= isExt;
}


/**
    * Sets the value of the itNew property
    */
   public PersonVO()
   {
      setItNew(true);
   }

   /**
    * Constructors containing all attributes of the Person value object
    * @param thePersonDT the new value of the thePersonDT property
    * @param thePersonNameDTCollection  the new value of the thePersonNameDTCollection  property
    * @param thePersonRaceDTCollection  the new value of the thePersonRaceDTCollection  property
    * @param thePersonEthnicGroupDTCollection  the new value of the thePersonEthnicGroupDTCollection  property
    * @param theEntityLocatorParticipationDTCollection  the new value of the theEntityLocatorParticipationDTCollection  property
    * @param theEntityIdDTCollection  the new value of the theEntityIdDTCollection  property
    */
   public PersonVO(PersonDT thePersonDT,
                   Collection<Object> thePersonNameDTCollection,
                   Collection<Object> thePersonRaceDTCollection,
                   Collection<Object> thePersonEthnicGroupDTCollection,
                   Collection<Object> theEntityLocatorParticipationDTCollection,
                   Collection<Object> theEntityIdDTCollection)
   {
         this.thePersonDT = thePersonDT;
         this.thePersonNameDTCollection=thePersonNameDTCollection;
         this.thePersonRaceDTCollection=thePersonRaceDTCollection;
         this.thePersonEthnicGroupDTCollection=thePersonEthnicGroupDTCollection;
         this.theEntityLocatorParticipationDTCollection=theEntityLocatorParticipationDTCollection;
         this.theEntityIdDTCollection=theEntityIdDTCollection;
         setItNew(true);
   }

   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Sets the value of the itDirty property
    * @param itDirty the new value of the itDirty property
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * Access method for the itDirty property.
    * @return   the current value of the itDirty property
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * Sets the value of the itNew property
    * @param itNew the new value of the itNew property
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
    * Access method for the isItNew property.
    * @return   the current value of the isItNew property
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * Access method for the itDelete property.
    * @return   the current value of the itDelete property
    */
    public boolean isItDelete()
   {
    return itDelete;
   }

   /**
    * Sets the value of the itDelete property
    * @param itDelete the new value of the itDelete property
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

  /**
   * Access method for the theEntityLocatorParticipationDTCollection  property.
   * @return   the current value of the theEntityLocatorParticipationDTCollection  property
   */
  public Collection<Object> getTheEntityLocatorParticipationDTCollection() {
    return theEntityLocatorParticipationDTCollection;
  }

  /**
   * Sets the value of the theEntityLocatorParticipationDTCollection  and itDirty property
   * @param theEntityLocatorParticipationDTCollection  the new value of the theEntityLocatorParticipationDTCollection  property
   */
  public void setTheEntityLocatorParticipationDTCollection(Collection<Object> theEntityLocatorParticipationDTCollection) {
    this.theEntityLocatorParticipationDTCollection  = theEntityLocatorParticipationDTCollection;
    setItDirty(true);
  }

  /**
   * Access method for the thePersonDT property.
   * @return   the current value of the thePersonDT property
   */
  public PersonDT getThePersonDT() {
    return thePersonDT;
  }

  /**
   * Sets the value of the thePersonDT and itDirty property
   * @param thePersonDT the new value of the thePersonDT property
   */
  public void setThePersonDT(PersonDT thePersonDT) {
    this.thePersonDT = thePersonDT;
    setItDirty(true);
  }

  /**
   * Access method for the thePersonEthnicGroupDTCollection  property.
   * @return   the current value of the thePersonEthnicGroupDTCollection  property
   */
  public Collection<Object> getThePersonEthnicGroupDTCollection() {
    return thePersonEthnicGroupDTCollection;
  }

  /**
   * Sets the value of the thePersonEthnicGroupDTCollection  and itDirty property
   * @param thePersonEthnicGroupDTCollection  the new value of the thePersonEthnicGroupDTCollection  property
   */
  public void setThePersonEthnicGroupDTCollection(Collection<Object> thePersonEthnicGroupDTCollection) {
    this.thePersonEthnicGroupDTCollection  = thePersonEthnicGroupDTCollection;
    setItDirty(true);
  }

  /**
   * Access method for the thePersonNameDTCollection  property.
   * @return   the current value of the thePersonNameDTCollection  property
   */
  public Collection<Object> getThePersonNameDTCollection() {
    return thePersonNameDTCollection;
  }

  /**
   * Sets the value of the thePersonNameDTCollection  and itDirty property
   * @param thePersonNameDTCollection  the new value of the thePersonNameDTCollection  property
   */
  public void setThePersonNameDTCollection(Collection<Object> thePersonNameDTCollection) {
    this.thePersonNameDTCollection  = thePersonNameDTCollection;
    setItDirty(true);
  }

  /**
   * Access method for the thePersonRaceDTCollection  property.
   * @return   the current value of the thePersonRaceDTCollection  property
   */
  public Collection<Object> getThePersonRaceDTCollection() {
    return thePersonRaceDTCollection;
  }

  /**
   * Sets the value of the thePersonRaceDTCollection  and itDirty property
   * @param thePersonRaceDTCollection  the new value of the thePersonRaceDTCollection  property
   */
  public void setThePersonRaceDTCollection(Collection<Object> thePersonRaceDTCollection) {
    this.thePersonRaceDTCollection  = thePersonRaceDTCollection;
    setItDirty(true);
  }

  /**
   * Access method for the theEntityIdDTCollection  property.
   * @return   the current value of the theEntityIdDTCollection  property
   */
  public Collection<Object> getTheEntityIdDTCollection() {
    return theEntityIdDTCollection;
  }

  /**
   * Sets the value of the theEntityIdDTCollection  and itDirty property
   * @param theEntityIdDTCollection  the new value of the theEntityIdDTCollection  property
   */
  public void setTheEntityIdDTCollection(Collection<Object> theEntityIdDTCollection) {
    this.theEntityIdDTCollection  = theEntityIdDTCollection;
    setItDirty(true);
  }

  /**
   * Access method for the theParticipationDTCollection  property.
   * @return   the current value of the theParticipationDTCollection  property
   */
   public Collection<Object> getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
     theParticipationDTCollection  = aTheParticipationDTCollection;
   }

   /**
    * Access method for the theRoleDTCollection  property.
    * @return   the current value of the theRoleDTCollection  property
    */
   public Collection<Object> getTheRoleDTCollection()
   {
     return theRlDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
    public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
    {
      theRlDTCollection  = aTheRoleDTCollection;
    }

   /**
    * Access method for a selected PersonNameDT object
    * @param index the index value of the PersonNameDT object in thePersonNameDTCollection  property
    * @return   the selected PersonNameDT object
    */
  public PersonNameDT getPersonNameDT_s(int index) {
      // this should really be in the constructor
      if (this.thePersonNameDTCollection  == null)
          this.thePersonNameDTCollection  = new ArrayList<Object> ();

      int currentSize = this.thePersonNameDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.thePersonNameDTCollection.toArray();

          Object tempObj  = tempArray[index];

          PersonNameDT tempDT = (PersonNameDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
          } // do nothing just continue
      }

       PersonNameDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new PersonNameDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          tempDT.setPersonNameSeq(new Integer(i+1));
          this.thePersonNameDTCollection.add(tempDT);
        }

        return tempDT;
  }


    /**
     * This method was needed as a result of logic used in Merge Person also
     * known as DeDuplication.  Specifically in PersonEJB.updateRevisonsAfterMerge() this
     * method is used.
     * This method will create a new PersonVO representing the VO which it is called on.
	  * @return java.lang.Object
	  * @roseuid 3E70827D0317
	  */
	 public Object deepCopy() throws Exception
	 {
           Object deepCopy = null;
           try {
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(baos);
           oos.writeObject(this);
           ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
           ObjectInputStream ois = new ObjectInputStream(bais);
           deepCopy = ois.readObject();
           } catch (Exception e) {
             e.printStackTrace();
             throw e;
           }
           return  deepCopy;

		/*PersonVO cloneVo = new PersonVO();
		cloneVo.setItDelete(itDelete);
		cloneVo.setItDirty(itDirty);
		cloneVo.setItNew(itNew);
		cloneVo.setTheEntityIdDTCollection((ArrayList<Object> )((ArrayList<Object> )theEntityIdDTCollection).clone());
		cloneVo.setTheEntityLocatorParticipationDTCollection((ArrayList<Object> )((ArrayList<Object> )theEntityLocatorParticipationDTCollection).clone());
		cloneVo.setTheParticipationDTCollection((ArrayList<Object> )((ArrayList<Object> )theParticipationDTCollection).clone());
		cloneVo.setThePersonDT(clonePersonDT(thePersonDT));
		cloneVo.setThePersonEthnicGroupDTCollection((ArrayList<Object> )((ArrayList<Object> )thePersonEthnicGroupDTCollection).clone());
		cloneVo.setThePersonNameDTCollection((ArrayList<Object> )((ArrayList<Object> )thePersonNameDTCollection).clone());
		cloneVo.setThePersonRaceDTCollection((ArrayList<Object> )((ArrayList<Object> )thePersonRaceDTCollection).clone());
		cloneVo.setTheRoleDTCollection((ArrayList<Object> )((ArrayList<Object> )theRoleDTCollection).clone());
              */

	    //return cloneVo;
	 }




   /**
    * Access method for a selected PersonRaceDT object
    * @param index the index value of the PersonRaceDT object in thePersonRaceDTCollection  property
    * @return   the selected PersonRaceDT object
    */
  public PersonRaceDT getPersonRaceDT_s(int index) {
      // this should really be in the constructor
      if (this.thePersonRaceDTCollection  == null)
          this.thePersonRaceDTCollection  = new ArrayList<Object> ();

      int currentSize = this.thePersonRaceDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.thePersonRaceDTCollection.toArray();

          Object tempObj  = tempArray[index];

          PersonRaceDT tempDT = (PersonRaceDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       PersonRaceDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new PersonRaceDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.thePersonRaceDTCollection.add(tempDT);
        }

        return tempDT;
  }

   /**
    * Access method for a selected PersonEthnicGroupDT object
    * @param index the index value of the PersonEthnicGroupDT object in thePersonEthnicGroupDTCollection  property
    * @return   the selected PersonEthnicGroupDT object
    */
    public PersonEthnicGroupDT getPersonEthnicGroupDT_s(int index) {
      // this should really be in the constructor
      if (this.thePersonEthnicGroupDTCollection  == null)
          this.thePersonEthnicGroupDTCollection  = new ArrayList<Object> ();

      int currentSize = this.thePersonEthnicGroupDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.thePersonEthnicGroupDTCollection.toArray();

          Object tempObj  = tempArray[index];

          PersonEthnicGroupDT tempDT = (PersonEthnicGroupDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
       } // do nothing just continue
      }

       PersonEthnicGroupDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new PersonEthnicGroupDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.thePersonEthnicGroupDTCollection.add(tempDT);
        }

        return tempDT;
  }

   /**
    * Access method for a selected EntityLocatorParticipationDT object
    * @param index the index value of the EntityLocatorParticipationDT object in theEntityLocatorParticipationDTCollection  property
    * @return   the selected EntityLocatorParticipationDT object
    */
  public EntityLocatorParticipationDT getEntityLocatorParticipationDT_s(int index) {
      // this should really be in the constructor
      if (this.theEntityLocatorParticipationDTCollection  == null)
          this.theEntityLocatorParticipationDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theEntityLocatorParticipationDTCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theEntityLocatorParticipationDTCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityLocatorParticipationDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theEntityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
  }

   /**
    * Access method for a selected EntityIdDT object
    * @param index the index value of the EntityIdDT object in theEntityIdDTCollection  property
    * @return   the selected EntityIdDT object
    */
  public EntityIdDT getEntityIdDT_s(int index) {
      // this should really be in the constructor
      if (this.theEntityIdDTCollection  == null)
          this.theEntityIdDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theEntityIdDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theEntityIdDTCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityIdDT tempDT = (EntityIdDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       EntityIdDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityIdDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theEntityIdDTCollection.add(tempDT);
        }

        return tempDT;
  }

  /**
    * @return java.lang.String
    * @roseuid 3E80CEC301E4
    */
   public String getDefaultJurisdictionCd()
   {
    return this.defaultJurisdictionCd;
   }

   /**
    * @param aDefaultJurisdictionCd
    * @roseuid 3E80CF3C0399
    */
   public void setDefaultJurisdictionCd(String aDefaultJurisdictionCd)
   {
     this.defaultJurisdictionCd = aDefaultJurisdictionCd;
   }

public String getAddReasonCode() {
	return addReasonCode;
}

public void setAddReasonCode(String addReasonCode) {
	this.addReasonCode = addReasonCode;
}
/*
public String getCustom() {
	return custom;
}

public void setCustom(String custom) {
	this.custom = custom;
}
*/
}
