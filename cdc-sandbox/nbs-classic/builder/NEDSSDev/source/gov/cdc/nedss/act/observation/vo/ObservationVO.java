//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\wum\\helpers\\ObservationVO.java

/**
* Name:		ObservationVO.java
* Description:	This is a value object used for identifying an observation
*               and its associated dependent objects.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.observation.vo;

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.material.dt.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.ArrayList;

public class ObservationVO extends AbstractVO implements Comparable<Object>
{
	private static final long serialVersionUID = 1L;
   private ObservationDT theObservationDT = new ObservationDT();
   private Collection<Object> theActIdDTCollection;
   private Collection<Object> theObservationReasonDTCollection;
   private Collection<Object> theObservationInterpDTCollection;
   private Collection<Object> theObsValueCodedDTCollection;
   private Collection<Object> theObsValueCodedModDTCollection;
   private Collection<Object> theObsValueTxtDTCollection;
   private Collection<Object> theObsValueDateDTCollection;
   private Collection<Object> theObsValueNumericDTCollection;
   private Collection<Object> theActivityLocatorParticipationDTCollection;
   //Collections added for Participation and Activity Relationship object association
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theActRelationshipDTCollection;
   public Collection<Object> theMaterialDTCollection;
   /**
    * @roseuid 3BE6D48A026F
    */
   public ObservationVO()
   {

   }

   public ObservationVO(ObservationDT theObservationDT,
                       Collection<Object> theActIdDTCollection,
                       Collection<Object> theObservationReasonDTCollection,
                       Collection<Object> theObservationInterpDTCollection,
                       Collection<Object> theObsValueCodedDTCollection,
                       Collection<Object> theObsValueCodedModDTCollection,
                       Collection<Object> theObsValueTxtDTCollection,
                       Collection<Object> theObsValueDateDTCollection,
                       Collection<Object> theObsValueNumericDTCollection,
                       Collection<Object> theActivityLocatorParticipationDTCollection)
   {
      this.theObservationDT = theObservationDT;
      this.theActIdDTCollection  = theActIdDTCollection;
      this.theObservationReasonDTCollection  = theObservationReasonDTCollection;
      this.theObservationInterpDTCollection  = theObservationInterpDTCollection;
      this.theObsValueCodedDTCollection  = theObsValueCodedDTCollection;
      this.theObsValueCodedModDTCollection  = theObsValueCodedModDTCollection;
      this.theObsValueTxtDTCollection  = theObsValueTxtDTCollection;
      this.theObsValueDateDTCollection  = theObsValueDateDTCollection;
      this.theObsValueNumericDTCollection  = theObsValueNumericDTCollection;
      this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
      setItNew(true);
   }

   /**
    * Access method for the theObservationReasonDTCollection  property.
    *
    * @return   the current value of the theObservationReasonDTCollection  property
    */
   public Collection<Object> getTheObservationReasonDTCollection()
   {
      return theObservationReasonDTCollection;
   }

   /**
    * Sets the value of the theObservationReasonDTCollection  property.
    *
    * @param aTheObservationReasonDTCollection  the new value of the theObservationReasonDTCollection  property
    */
   public void setTheObservationReasonDTCollection(Collection<Object> aTheObservationReasonDTCollection)
   {
      theObservationReasonDTCollection  = aTheObservationReasonDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObservationDT property.
    *
    * @return   the current value of the theObservationDT property
    */
   public ObservationDT getTheObservationDT()
   {
      return theObservationDT;
   }

   /**
    * Sets the value of the theObservationDT property.
    *
    * @param aTheObservationDT the new value of the theObservationDT property
    */
   public void setTheObservationDT(ObservationDT aTheObservationDT)
   {
      theObservationDT = aTheObservationDT;
      setItDirty(true);
   }

   /**
    * Access method for the theActIdDTCollection  property.
    *
    * @return   the current value of the theActIdDTCollection  property
    */
   public Collection<Object> getTheActIdDTCollection()
   {
      return theActIdDTCollection;
   }

   /**
    * Access method for the theActIdDTCollection  property.
    *
    * @return   the current value of the theActIdDTCollection  property
    */
   public Collection<Object> getTheMaterialDTCollection()
   {
      return theMaterialDTCollection;
   }

   /**
    * Sets the value of the theActIdDTCollection  property.
    *
    * @param aTheActIdDTCollection  the new value of the theActIdDTCollection  property
    */
   public void setTheMaterialDTCollection(Collection<Object> aTheMaterialDTCollection)
   {
      theMaterialDTCollection  = aTheMaterialDTCollection;
      setItDirty(true);
   }


   /**
    * Sets the value of the theActIdDTCollection  property.
    *
    * @param aTheActIdDTCollection  the new value of the theActIdDTCollection  property
    */
   public void setTheActIdDTCollection(Collection<Object> aTheActIdDTCollection)
   {
      theActIdDTCollection  = aTheActIdDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObsValueCodedDTCollection  property.
    *
    * @return   the current value of the theObsValueCodedDTCollection  property
    */
   public Collection<Object> getTheObsValueCodedDTCollection()
   {
      return theObsValueCodedDTCollection;
   }

   /**
    * Access method for the theObsValueCodedModDTCollection  property.
    *
    * @return   the current value of the theObsValueCodedDTCollection  property
    */
   public Collection<Object> getTheObsValueCodedModDTCollection() {
    return theObsValueCodedModDTCollection;
   }

   /**
    * Sets the value of the theObsValueCodedDTCollection  property.
    *
    * @param aTheObsValueCodedDTCollection  the new value of the theObsValueCodedDTCollection  property
    */
   public void setTheObsValueCodedDTCollection(Collection<Object> aTheObsValueCodedDTCollection)
   {
      theObsValueCodedDTCollection  = aTheObsValueCodedDTCollection;
      setItDirty(true);
   }

   /**
    * Sets the value of the theObsValueCodedDTCollection  property.
    *
    * @param aTheObsValueCodedDTCollection  the new value of the theObsValueCodedDTCollection  property
    */
   public void setTheObsValueCodedModDTCollection(Collection<Object> aTheObsValueCodedModDTCollection)
   {
      theObsValueCodedModDTCollection  = aTheObsValueCodedModDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObsValueTxtDTCollection  property.
    *
    * @return   the current value of the theObsValueTxtDTCollection  property
    */
   public Collection<Object> getTheObsValueTxtDTCollection()
   {
      return theObsValueTxtDTCollection;
   }

   /**
    * Sets the value of the theObsValueTxtDTCollection  property.
    *
    * @param aTheObsValueTxtDTCollection  the new value of the theObsValueTxtDTCollection  property
    */
   public void setTheObsValueTxtDTCollection(Collection<Object> aTheObsValueTxtDTCollection)
   {
      theObsValueTxtDTCollection  = aTheObsValueTxtDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObsValueDateDTCollection  property.
    *
    * @return   the current value of the theObsValueDateDTCollection  property
    */
   public Collection<Object> getTheObsValueDateDTCollection()
   {
      return theObsValueDateDTCollection;
   }

   /**
    * Sets the value of the theObsValueDateDTCollection  property.
    *
    * @param aTheObsValueDateDTCollection  the new value of the theObsValueDateDTCollection  property
    */
   public void setTheObsValueDateDTCollection(Collection<Object> aTheObsValueDateDTCollection)
   {
      theObsValueDateDTCollection  = aTheObsValueDateDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObsValueNumericDTCollection  property.
    *
    * @return   the current value of the theObsValueNumericDTCollection  property
    */
   public Collection<Object> getTheObsValueNumericDTCollection()
   {
      return theObsValueNumericDTCollection;
   }

   /**
    * Sets the value of the theObsValueNumericDTCollection  property.
    *
    * @param aTheObsValueNumericDTCollection  the new value of the theObsValueNumericDTCollection  property
    */
   public void setTheObsValueNumericDTCollection(Collection<Object> aTheObsValueNumericDTCollection)
   {
      theObsValueNumericDTCollection  = aTheObsValueNumericDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObservationInterpDTCollection  property.
    *
    * @return   the current value of the theObservationInterpDTCollection  property
    */
   public Collection<Object> getTheObservationInterpDTCollection()
   {
      return theObservationInterpDTCollection;
   }

   /**
    * Sets the value of the theObservationInterpDTCollection  property.
    *
    * @param aTheObservationInterpDTCollection  the new value of the theObservationInterpDTCollection  property
    */
   public void setTheObservationInterpDTCollection(Collection<Object> aTheObservationInterpDTCollection)
   {
      theObservationInterpDTCollection  = aTheObservationInterpDTCollection;
      setItDirty(true);
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
    * @roseuid 3BE6D48A02BF
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3BE6D48B004A
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3BE6D48B00E0
    */
   public boolean isItDirty()
   {
      return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3BE6D48B0112
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3BE6D48B01A8
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
   //Role and participation Collection<Object> entered by John Park
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
          tempDT.setItNew(true);
          this.theActIdDTCollection.add(tempDT);
        }

        return tempDT;
  }

  public MaterialDT getMaterialDT_s(int index) {
       // this should really be in the constructor
       if (this.theMaterialDTCollection  == null)
           this.theMaterialDTCollection  = new ArrayList<Object> ();

       int currentSize = this.theMaterialDTCollection.size();

       // check if we have a this many DTs
       if (index < currentSize)
       {
         try {
           Object[] tempArray = this.theMaterialDTCollection.toArray();

           Object tempObj  = tempArray[index];

           MaterialDT tempDT = (MaterialDT) tempObj;

           return tempDT;
         }
         catch (Exception e) {
            //##!! System.out.println(e);
         } // do nothing just continue
       }

        MaterialDT tempDT = null;

         for (int i = currentSize; i < index+1; i++)
         {
           tempDT = new MaterialDT();
           tempDT.setItNew(true);
           this.theMaterialDTCollection.add(tempDT);
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

  public ObsValueCodedDT getObsValueCodedDT_s(int index) {
      // this should really be in the constructor
      if (this.theObsValueCodedDTCollection  == null)
          this.theObsValueCodedDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObsValueCodedDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObsValueCodedDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObsValueCodedDT tempDT = (ObsValueCodedDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObsValueCodedDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObsValueCodedDT();
          //tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theObsValueCodedDTCollection.add(tempDT);
        }

        return tempDT;
  }

  public ObsValueCodedModDT getObsValueCodedModDT_s(int index) {
      // this should really be in the constructor
      if (this.theObsValueCodedModDTCollection  == null)
          this.theObsValueCodedModDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObsValueCodedModDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObsValueCodedModDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObsValueCodedModDT tempDT = (ObsValueCodedModDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObsValueCodedModDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObsValueCodedModDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theObsValueCodedModDTCollection.add(tempDT);
        }

        return tempDT;
  }



  public ObsValueTxtDT getObsValueTxtDT_s(int index) {
      // this should really be in the constructor
      if (this.theObsValueTxtDTCollection  == null)
          this.theObsValueTxtDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObsValueTxtDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObsValueTxtDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObsValueTxtDT tempDT = (ObsValueTxtDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObsValueTxtDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObsValueTxtDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          tempDT.setObsValueTxtSeq(new Integer(i+1));
          this.theObsValueTxtDTCollection.add(tempDT);
        }

        return tempDT;
  }


  public ObsValueDateDT getObsValueDateDT_s(int index) {
      // this should really be in the constructor
      if (this.theObsValueDateDTCollection  == null)
          this.theObsValueDateDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObsValueDateDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObsValueDateDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObsValueDateDT tempDT = (ObsValueDateDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObsValueDateDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObsValueDateDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          tempDT.setObsValueDateSeq(new Integer(i+1));
          this.theObsValueDateDTCollection.add(tempDT);
        }

        return tempDT;
  }


  public ObsValueNumericDT getObsValueNumericDT_s(int index) {
      // this should really be in the constructor
      if (this.theObsValueNumericDTCollection  == null)
          this.theObsValueNumericDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObsValueNumericDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObsValueNumericDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObsValueNumericDT tempDT = (ObsValueNumericDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObsValueNumericDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObsValueNumericDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          tempDT.setObsValueNumericSeq(new Integer(i+1));
          this.theObsValueNumericDTCollection.add(tempDT);
        }

        return tempDT;
  }


  public ObservationReasonDT getObservationReasonDT_s(int index) {
      // this should really be in the constructor
      if (this.theObservationReasonDTCollection  == null)
          this.theObservationReasonDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObservationReasonDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObservationReasonDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObservationReasonDT tempDT = (ObservationReasonDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObservationReasonDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObservationReasonDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theObservationReasonDTCollection.add(tempDT);
        }

        return tempDT;
  }


  public ObservationInterpDT getObservationInterpDT_s(int index) {
      // this should really be in the constructor
      if (this.theObservationInterpDTCollection  == null)
          this.theObservationInterpDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theObservationInterpDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theObservationInterpDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ObservationInterpDT tempDT = (ObservationInterpDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObservationInterpDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ObservationInterpDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theObservationInterpDTCollection.add(tempDT);
        }

        return tempDT;
  }

  public int compareTo(Object observationVO)
  {
     return this.theObservationDT.getUid().compareTo(((ObservationVO)observationVO).getTheObservationDT().getUid());
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

}
