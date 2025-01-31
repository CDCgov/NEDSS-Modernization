package gov.cdc.nedss.act.notification.vo;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.locator.dt.*;

// gov.cdc.nedss.* imports
import gov.cdc.nedss.util.*;

import java.util.ArrayList;

// Import Statements
import java.util.Collection;

/**
 * Title:        NotificationVO
 * Description:  Notification Value Object
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       11/14/2001 Keith Welch
 * @modified     11/14/2001 Keith Welch
 * @version      1.0.0
 */

public class NotificationVO
    extends AbstractVO {
	
	private static final long serialVersionUID = 1L;
    private NotificationDT theNotificationDT = new NotificationDT();
    private UpdatedNotificationDT theUpdatedNotificationDT = null;

    //   private Collection<Object>  theEntityLocatorParticipationDTCollection;
    public Collection<Object> theActivityLocatorParticipationDTCollection;
    public Collection<Object> theActIdDTCollection;

    //Collections added for Participation and Activity Relationship object association
    public Collection<Object> theActRelationshipDTCollection;
    public Collection<Object> theParticipationDTCollection;

    public NotificationVO() {
        itDirty = false;
        itNew = true;
        itDelete = false;
    }

    public NotificationVO(NotificationDT atheNotificationDT, Collection<Object> atheActivityLocatorParticipationDTCollection, Collection<Object> atheActIdDTCollection, Collection<Object> atheActRelationshipDTCollection, Collection<Object> atheParticipationDTCollection) {
        this.theNotificationDT = atheNotificationDT;
        this.theActivityLocatorParticipationDTCollection  = atheActivityLocatorParticipationDTCollection;
        this.theActIdDTCollection  = atheActIdDTCollection;
        this.theActRelationshipDTCollection  = atheActRelationshipDTCollection;
        this.theParticipationDTCollection  = atheParticipationDTCollection;
    }

    /**
     *
     * @return NotificationDT theNotificationDT
     */
    public NotificationDT getTheNotificationDT() {

        return theNotificationDT;
    }

    /**
     *
     * @param theNotificationDT
     */
    public void setTheNotificationDT(NotificationDT theNotificationDT) {
        this.theNotificationDT = theNotificationDT;
    }

    /**
     *
     * @return Collection<Object>  theActIdDTCollection
     */
    public Collection<Object> getTheActIdDTCollection() {

        return theActIdDTCollection;
    }

    /**
     *
     * @param atheActIdDTCollection
     */
    public void setTheActIdDTCollection(Collection<Object> atheActIdDTCollection) {
        theActIdDTCollection  = atheActIdDTCollection;
    }

    /**
     *
     * @return Collection<Object>  theActivityLocatorParticipationDTCollection
     */
    public Collection<Object> getTheActivityLocatorParticipationDTCollection() {

        return theActivityLocatorParticipationDTCollection;
    }

    /**
     *
     * @param theActivityLocatorParticipationDTCollection
     */
    public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
        this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
        setItDirty(true);
    }

    /**
     *
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
        voClass = objectname1.getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     *
     * @param itDirty
     */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     *
     * @param itNew
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     *
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

    //Role and participation collection entered by John Park

    /**
     *
     * @return Collection<Object>  theParticipationDTCollection
     */
    public Collection<Object> getTheParticipationDTCollection() {

        return theParticipationDTCollection;
    }

    /**
     *
     * @param aTheParticipationDTCollection
     */
    public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection) {
        theParticipationDTCollection  = aTheParticipationDTCollection;
    }

    /**
     *
     * @return Collection<Object>  theActRelationshipDTCollection
     */
    public Collection<Object> getTheActRelationshipDTCollection() {

        return theActRelationshipDTCollection;
    }

    /**
     *
     * @param aTheActRelationshipDTCollection
     */
    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection) {
        theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }

    /**
     *
     * @param index
     * @return ActIdDT actIdDT_s
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
     *
     * @param index
     * @return ActivityLocatorParticipationDT actLocatorParticipationDT_s
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
	/**
	 * @return updatedNotificationDT
	 */
	public UpdatedNotificationDT getTheUpdatedNotificationDT() {
		return theUpdatedNotificationDT;
	}

	/**
	 * @param updatedNotificationDT updated notification
	 */
	public void setTheUpdatedNotificationDT(UpdatedNotificationDT updatedNotificationDT) {
		theUpdatedNotificationDT = updatedNotificationDT;
	}

}