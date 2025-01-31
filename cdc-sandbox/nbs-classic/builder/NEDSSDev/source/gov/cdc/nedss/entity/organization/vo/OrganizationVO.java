//Source file: C:\\Development\\Source\\gov\\cdc\\nedss\\cdm\\helpers\\OrganizationVO.java
/**
* Name:		    OrganizationVO
* Description:	Organization Value Object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/
package gov.cdc.nedss.entity.organization.vo;

import java.util.Collection;
import java.util.ArrayList;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.phdc.IdentifierType;


public class OrganizationVO extends LdfBaseVO {
	
	private static final long serialVersionUID = 1L;
    public OrganizationDT theOrganizationDT = new OrganizationDT();
    public Collection<Object> theOrganizationNameDTCollection;
    public Collection<Object> theEntityLocatorParticipationDTCollection;
    public Collection<Object> theEntityIdDTCollection;

    //collections for role and participation object association added by John Park
    public Collection<Object> theParticipationDTCollection;
    public Collection<Object> theRlDTCollection;
    private String sendingFacility;
    private String sendingSystem;
    private String localIdentifier;
    private String rl;

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

	public String getLocalIdentifier() {
    	return localIdentifier;
    }

    public void setLocalIdentifier(String localIdentifier) {
    	this.localIdentifier = localIdentifier;
    }

	public String getSendingFacility() {
		return sendingFacility;
	}

	public void setSendingFacility(String sendingFacility) {
		this.sendingFacility = sendingFacility;
	}

	public String getSendingSystem() {
		return sendingSystem;
	}

	public void setSendingSystem(String sendingSystem) {
		this.sendingSystem = sendingSystem;
	}

	public OrganizationVO() {
        setItNew(true);
    }

    public OrganizationVO(OrganizationDT theOrganizationDT, Collection<Object> theOrganizationNameDTCollection, Collection<Object> theEntityLocatorParticipationDTCollection, Collection<Object> theEntityIdDTCollection) {
        this.theOrganizationDT = theOrganizationDT;
        this.theOrganizationNameDTCollection  = theOrganizationNameDTCollection;
        this.theEntityLocatorParticipationDTCollection = theEntityLocatorParticipationDTCollection;
        this.theEntityIdDTCollection  = theEntityIdDTCollection;
        setItNew(true);
    }

    /**
     *
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {

        return true;
    }

    /**
     *
     * @param aItDirty
     */
    public void setItDirty(boolean aItDirty) {
        itDirty = aItDirty;
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
     * @param aItNew
     */
    public void setItNew(boolean aItNew) {
        itNew = aItNew;
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
     * @param aItDelete
     */
    public void setItDelete(boolean aItDelete) {
        itDelete = aItDelete;
    }

    /* getTheOrganizationDT */

    /**
     *
     * @return OrganizationDT
     */
    public OrganizationDT getTheOrganizationDT() {

        return theOrganizationDT;
    }

    /* setTheOrganizationDT */

    /**
     *
     * @param theOrganizationDT
     */
    public void setTheOrganizationDT(OrganizationDT theOrganizationDT) {
        this.theOrganizationDT = theOrganizationDT;
    }

    /* getTheOrganizationNameDT */

    /**
     *
     * @return Collection
     */
    public Collection<Object> getTheOrganizationNameDTCollection() {

        return theOrganizationNameDTCollection;
    }

    /* setTheOrganizationNameDT */

    /**
     *
     * @param theOrganizationNameDTCollection
     */
    public void setTheOrganizationNameDTCollection(Collection<Object> theOrganizationNameDTCollection) {
        this.theOrganizationNameDTCollection  = theOrganizationNameDTCollection;
    }

    /* getTheEntityLocatorParticipationDT */

    /**
     *
     * @return Collection
     */
    public Collection<Object> getTheEntityLocatorParticipationDTCollection() {

        return theEntityLocatorParticipationDTCollection;
    }

    /* setTheEntityLocatorParticipationDT */

    /**
     *
     * @param theEntityLocatorParticipationDTCollection
     */
    public void setTheEntityLocatorParticipationDTCollection(Collection<Object> theEntityLocatorParticipationDTCollection) {
        this.theEntityLocatorParticipationDTCollection  = theEntityLocatorParticipationDTCollection;
    }

    /* getTheEntityIdDT */

    /**
     *
     * @return Collection
     */
    public Collection<Object> getTheEntityIdDTCollection() {

        return theEntityIdDTCollection;
    }

    /* setTheEntityIdDT */

    /**
     *
     * @param theEntityIdDTCollection
     */
    public void setTheEntityIdDTCollection(Collection<Object> theEntityIdDTCollection) {
        this.theEntityIdDTCollection  = theEntityIdDTCollection;
    }

    //Role and participation collection entered by John Park

    /**
     *
     * @return Collection
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
     * @return Collection
     */
    public Collection<Object> getTheRoleDTCollection() {

        return theRlDTCollection;
    }

    /**
     *
     * @param aTheRoleDTCollection
     */
    public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection) {
        theRlDTCollection  = aTheRoleDTCollection;
    }

    /**
     *
     * @param index
     * @return OrganizationNameDT
     */
    public OrganizationNameDT getOrganizationNameDT_s(int index) {

        // this should really be in the constructor
        if (this.theOrganizationNameDTCollection  == null)
            this.theOrganizationNameDTCollection  = new ArrayList<Object> ();

        int currentSize = this.theOrganizationNameDTCollection.size();

        // check if we have a this many DTs
        if (index < currentSize) {

            try {

                Object[] tempArray = this.theOrganizationNameDTCollection.toArray();
                Object tempObj = tempArray[index];
                OrganizationNameDT tempDT = (OrganizationNameDT)tempObj;

                return tempDT;
            } catch (Exception e) {
            }
        }

        OrganizationNameDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++) {
            tempDT = new OrganizationNameDT();
            tempDT.setItNew(true); // this should be done in the constructor of the DT
            this.theOrganizationNameDTCollection.add(tempDT);
        }

        return tempDT;
    }

    /**
     *
     * @param index
     * @return EntityLocatorParticipationDT
     */
    public EntityLocatorParticipationDT getEntityLocatorParticipationDT_s(int index) {

        // this should really be in the constructor
        if (this.theEntityLocatorParticipationDTCollection  == null)
            this.theEntityLocatorParticipationDTCollection  = new ArrayList<Object> ();

        int currentSize = this.theEntityLocatorParticipationDTCollection.size();

        // check if we have a this many DTs
        if (index < currentSize) {

            try {

                Object[] tempArray = this.theEntityLocatorParticipationDTCollection.toArray();
                Object tempObj = tempArray[index];
                EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT)tempObj;

                return tempDT;
            } catch (Exception e) {
            }
        }

        EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++) {
            tempDT = new EntityLocatorParticipationDT();
            tempDT.setItNew(true); // this should be done in the constructor of the DT
            this.theEntityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
    }

    /**
     *
     * @param index
     * @return EntityIdDT
     */
    public EntityIdDT getEntityIdDT_s(int index) {

        // this should really be in the constructor
        if (this.theEntityIdDTCollection  == null)
            this.theEntityIdDTCollection  = new ArrayList<Object> ();

        int currentSize = this.theEntityIdDTCollection.size();

        // check if we have a this many DTs
        if (index < currentSize) {

            try {

                Object[] tempArray = this.theEntityIdDTCollection.toArray();
                Object tempObj = tempArray[index];
                EntityIdDT tempDT = (EntityIdDT)tempObj;

                return tempDT;
            } catch (Exception e) {
            }
        }

        EntityIdDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++) {
            tempDT = new EntityIdDT();
            tempDT.setItNew(true); // this should be done in the constructor of the DT
            tempDT.setEntityIdSeq(new Integer(i + 1));
            this.theEntityIdDTCollection.add(tempDT);
        }

        return tempDT;
    }
}