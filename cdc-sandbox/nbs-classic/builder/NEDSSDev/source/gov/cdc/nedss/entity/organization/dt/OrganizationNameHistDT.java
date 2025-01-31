/**
 * Title: OrganizationNameHistDT helper class.
 * Description: A helper class for organization name history data table
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.organization.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class OrganizationNameHistDT extends AbstractVO
{

    private Long organizationUid;
    private Integer organizationNameSeq;
    private Integer versionCtrlNbr;
    private String nmTxt;
    private String nmUseCd;
    private String recordStatusCd;
    private String defaultNmInd;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

   /**
    * Access method for the organizationUid property.
    * @return   the current value of the organizationUid property
    */
    public Long getOrganizationUid()
    {
        return organizationUid;
    }

   /**
    * Sets the value of both organizationUid and itDirty properties
    * @param aOrganizationUid the new value of the organizationUid property
    */
    public void setOrganizationUid(Long aOrganizationUid)
    {
        organizationUid = aOrganizationUid;
        setItDirty(true);
    }

   /**
    * Access method for the organizationNameSeq property.
    * @return   the current value of the organizationNameSeq property
    */
    public Integer getOrganizationNameSeq()
    {
        return organizationNameSeq;
    }

   /**
    * Sets the value of both organizationNameSeq and itDirty properties
    * @param aOrganizationNameSeq the new value of the organizationNameSeq property
    */
    public void setOrganizationNameSeq(Integer aOrganizationNameSeq)
    {
        organizationNameSeq = aOrganizationNameSeq;
        setItDirty(true);
    }

   /**
    * Access method for the versionCtrlNbr property.
    * @return   the current value of the versionCtrlNbr property
    */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

   /**
    * Sets the value of both versionCtrlNbr and itDirty properties
    * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
    */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

   /**
    * Access method for the nmTxt property.
    * @return   the current value of the nmTxt property
    */
    public String getNmTxt()
    {
        return nmTxt;
    }

   /**
    * Sets the value of both nmTxt and itDirty properties
    * @param aNmTxt the new value of the nmTxt property
    */
    public void setNmTxt(String aNmTxt)
    {
        nmTxt = aNmTxt;
        setItDirty(true);
    }

   /**
    * Access method for the nmUseCd property.
    * @return   the current value of the nmUseCd property
    */
    public String getNmUseCd()
    {
        return nmUseCd;
    }

   /**
    * Sets the value of both nmUseCd and itDirty properties
    * @param aNmUseCd the new value of the nmUseCd property
    */
    public void setNmUseCd(String aNmUseCd)
    {
        nmUseCd = aNmUseCd;
        setItDirty(true);
    }

   /**
    * Access method for the recordStatusCd property.
    * @return   the current value of the recordStatusCd property
    */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

   /**
    * Sets the value of both recordStatusCd and itDirty properties
    * @param aRecordStatusCd the new value of the recordStatusCd property
    */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

   /**
    * Access method for the defaultNmInd property.
    * @return   the current value of the defaultNmInd property
    */
    public String getDefaultNmInd()
    {
        return defaultNmInd;
    }

   /**
    * Sets the value of both defaultNmInd and itDirty properties
    * @param aDefaultNmInd the new value of the defaultNmInd property
    */
    public void setDefaultNmInd(String aDefaultNmInd)
    {
        defaultNmInd = aDefaultNmInd;
        setItDirty(true);
    }

   /**
    * Access method for the progAreaCd property.
    * @return   the current value of the progAreaCd property
    */
    public String getProgAreaCd()
    {
      return progAreaCd;
    }

   /**
    * Sets the value of both progAreaCd and itDirty properties
    * @param aProgAreaCd the new value of the progAreaCd property
    */
    public void setProgAreaCd(String aProgAreaCd)
    {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
    }

   /**
    * Access method for the jurisdictionCd property.
    * @return   the current value of the jurisdictionCd property
    */
    public String getJurisdictionCd ()
    {
      return jurisdictionCd ;
    }

   /**
    * Sets the value of both jurisdictionCd and itDirty properties
    * @param aJurisdictionCd the new value of the jurisdictionCd property
    */
    public void setJurisdictionCd (String aJurisdictionCd )
    {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
    }

   /**
    * Access method for the programJurisdictionOid property.
    * @return   the current value of the programJurisdictionOid property
    */
    public Long getProgramJurisdictionOid ()
    {
      return programJurisdictionOid;
    }

   /**
    * Sets the value of both programJurisdictionOid and itDirty properties
    * @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
    */
    public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
    {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
    }

   /**
    * Access method for the sharedInd property.
    * @return   the current value of the sharedInd property
    */
    public String getSharedInd()
    {
      return sharedInd;
    }

   /**
    * Sets the value of both sharedInd and itDirty properties
    * @param aSharedInd the new value of the sharedInd property
    */
    public void setSharedInd(String aSharedInd)
    {
      sharedInd = aSharedInd;
      setItDirty(true);
    }

   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
      voClass =  (( OrganizationNameHistDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
     }

   /**
    * Sets the value of the itDirty property
    * @param itDirty the new value of the itDirty property
    */
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
    }

   /**
    * Access method for the itDirty property.
    * @return   the current value of the itDirty property
    */
    public boolean isItDirty() {
     return itDirty;
    }

   /**
    * Sets the value of the itNew property
    * @param itNew the new value of the itNew property
    */
    public void setItNew(boolean itNew) {
     this.itNew = itNew;
    }

   /**
    * Access method for the isItNew property.
    * @return   the current value of the isItNew property
    */
    public boolean isItNew() {
     return itNew;
    }

   /**
    * Access method for the itDelete property.
    * @return   the current value of the itDelete property
    */
    public boolean isItDelete() {
     return itDelete;
    }

   /**
    * Sets the value of the itDelete property
    * @param itDelete the new value of the itDelete property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
    }

}
