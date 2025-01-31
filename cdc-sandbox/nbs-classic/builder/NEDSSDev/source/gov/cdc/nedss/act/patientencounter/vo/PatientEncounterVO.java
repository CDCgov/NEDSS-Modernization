
package gov.cdc.nedss.act.patientencounter.vo;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.patientencounter.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.util.*;

import java.util.ArrayList;
import java.util.Collection;

   /**
    * Title:            PatientEncounterVO is a class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */

public class PatientEncounterVO
    extends AbstractVO
    implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    public PatientEncounterDT thePatientEncounterDT = new PatientEncounterDT();
    public Collection<Object> theActivityLocatorParticipationDTCollection;
    public Collection<Object> theActIdDTCollection;
    public Collection<Object> theParticipationDTCollection;
    public Collection<Object> theActRelationshipDTCollection;
   /**
    * This is empty constructor
    * @roseuid 3BB88E540343
    */
    public PatientEncounterVO() {
    }

    /**
    * Constructors containing all attributes.
    * @param PatientEncounterDT the thePatientEncounterDT
    * @param Collection<Object>  the theActivityLocatorParticipationDTCollection
    * @param Collection<Object>  the theActivityIdDTCollection
    */
    public PatientEncounterVO(PatientEncounterDT thePatientEncounterDT, Collection<Object> theActivityLocatorParticipationDTCollection, Collection<Object> theActivityIdDTCollection) {
        this.thePatientEncounterDT = thePatientEncounterDT;
        this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
        this.theActIdDTCollection  = theActivityIdDTCollection;
        setItNew(true);
    }

  /**
    * get the value of the boolean property.
    * @param objectname1 Object the object name
    * @param objectname2 Object the object name
    * @param voClass Class the class
    * @return isEqual the value of the boolean value
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {

        return true;
    }

     /**
    * Sets the value of the itDirty property.
    *
    * @param itDirty boolean the new value of the ItDirty property
    */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

 /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean vlue
    */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
    * Sets the value of the ItNew property.
    *
    * @param itNew boolean the new value of the boolean property
    */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

   /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
    public boolean isItNew() {

        return itNew;
    }

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
    public boolean isItDelete() {

        return itDelete;
    }

 /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

    /**
    * gets the value of the ActivityLocatorParticipationDTCollection.
    *
    * @return TheActivityLocatorParticipationDTCollection  the Collection
    */
    public Collection<Object> getTheActivityLocatorParticipationDTCollection() {

        return theActivityLocatorParticipationDTCollection;
    }

    /**
    * Sets the value of the TheActivityLocatorParticipationDTCollection.
    *
    * @param Collection<Object>  the theActivityLocatorParticipationDTCollection
    */
    public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
        this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
        setItDirty(true);
    }

    /**
    * gets the value of the ThePatientEncounterDT.
    *
    * @return PatientEncounterDT the Collection
    */
    public PatientEncounterDT getThePatientEncounterDT() {

        return thePatientEncounterDT;
    }

   /**
    * Sets the value of the ThePatientEncounterDT.
    *
    * @param PatientEncounterDT the thePatientEncounterDT
    */
    public void setThePatientEncounterDT(PatientEncounterDT thePatientEncounterDT) {
        this.thePatientEncounterDT = thePatientEncounterDT;
        setItDirty(true);
    }

    /**
    * gets the value of the TheActivityIdDTCollection(.
    *
    * @return ActIdDTCollection  the Collection
    */
    public Collection<Object> getTheActivityIdDTCollection() {

        return theActIdDTCollection;
    }

    /**
    * Sets the value of the TheActivityIdDTCollection.
    *
    * @param Collection<Object>  the theActivityIdDTCollection
    */
    public void setTheActivityIdDTCollection(Collection<Object> theActivityIdDTCollection) {
        this.theActIdDTCollection  = theActivityIdDTCollection;
        setItDirty(true);
    }

    //Role and participation collection entered by John Park

    /**
    * gets the value of the TheParticipationDTCollection(.
    *
    * @return ParticipationDTCollection  the Collection
    */
    public Collection<Object> getTheParticipationDTCollection() {

        return theParticipationDTCollection;
    }

    /**
    * Sets the value of the theParticipationDTCollection  property.
    *
    * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
    */
    public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection) {
        theParticipationDTCollection  = aTheParticipationDTCollection;
    }


    /**
    * Access method for the theactrelationshipDTCollection  property.
    *
    * @return ActRelationshipDTCollection  the Collection
    */
    public Collection<Object> getTheActRelationshipDTCollection() {

        return theActRelationshipDTCollection;
    }

    /**
    * Sets the value of the theRoleDTCollection  property.
    *
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection) {
        theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }

    /**
    * Access method for the ActIdDT_s property.
    *
    * @param int the index
    *
    * @return ActRelationshipDTCollection  the Collection
    */
    public ActIdDT getActIdDT_s(int index) {

        // this should really be in the constructor
        if (this.theActIdDTCollection  == null)
            this.theActIdDTCollection  = new ArrayList<Object> ();

        int currentSize = this.theActIdDTCollection.size();

        // check if we have a this many DTs
        if (index < currentSize) {

            try {

                Object[] tempArray = this.theActIdDTCollection.toArray();
                Object tempObj = tempArray[index];
                ActIdDT tempDT = (ActIdDT)tempObj;

                return tempDT;
            } catch (Exception e) {

                //##!! System.out.println(e);
            } // do nothing just continue
        }

        ActIdDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++) {
            tempDT = new ActIdDT();
            tempDT.setItNew(true); // this should be done in the constructor of the DT
            this.theActIdDTCollection.add(tempDT);
        }

        return tempDT;
    }

    /**
  * gets the value of the ActLocatorParticipationDT_s
  *
  * @param int the index
  * @return ActivityLocatorParticipationDT the Collection
  */
    public ActivityLocatorParticipationDT getActLocatorParticipationDT_s(int index) {

        // this should really be in the constructor
        if (this.theActivityLocatorParticipationDTCollection  == null)
            this.theActivityLocatorParticipationDTCollection  = new ArrayList<Object> ();

        int currentSize = this.theActivityLocatorParticipationDTCollection.size();

        // check if we have a this many DTs
        if (index < currentSize) {

            try {

                Object[] tempArray = this.theActivityLocatorParticipationDTCollection.toArray();
                Object tempObj = tempArray[index];
                ActivityLocatorParticipationDT tempDT = (ActivityLocatorParticipationDT)tempObj;

                return tempDT;
            } catch (Exception e) {

                //##!! System.out.println(e);
            } // do nothing just continue
        }

        ActivityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++) {
            tempDT = new ActivityLocatorParticipationDT();
            tempDT.setItNew(true); // this should be done in the constructor of the DT
            this.theActivityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
    }
}