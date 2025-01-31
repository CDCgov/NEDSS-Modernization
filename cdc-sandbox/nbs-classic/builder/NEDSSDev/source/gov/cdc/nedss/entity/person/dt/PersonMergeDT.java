//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\entity\\person\\dt\\PersonMergeDT.java

package gov.cdc.nedss.entity.person.dt;

import gov.cdc.nedss.util.*;

import gov.cdc.nedss.systemservice.util.*;
import java.sql.Timestamp;

public class PersonMergeDT extends AbstractVO implements DirtyMarkerInterface
{
	private static final long serialVersionUID = 1L;
   private Long supercedPersonUid;
   private Integer supercededVersionCtrlNbr;
   private Long supercededParentUid;
   private Long survivingPersonUid;
   private Integer survivingVersionCtrlNbr;
   private Long survivingParentUid;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String mergeUserId;
   private Timestamp mergeTime;

   /**
    * @roseuid 3EA69D9200F8
    */
   public PersonMergeDT()
   {

   }

   /**
    * Access method for the supercedPersonUid property.
    *
    * @return   the current value of the supercedPersonUid property
    */
   public Long getSupercedPersonUid()
   {
      return supercedPersonUid;
   }

   /**
    * Sets the value of the supercedPersonUid property.
    *
    * @param aSupercedPersonUid the new value of the supercedPersonUid property
    */
   public void setSupercedPersonUid(Long aSupercedPersonUid)
   {
      supercedPersonUid = aSupercedPersonUid;
   }

   /**
    * Access method for the supercededVersionCtrlNbr property.
    *
    * @return   the current value of the supercededVersionCtrlNbr property
    */
   public Integer getSupercededVersionCtrlNbr()
   {
      return supercededVersionCtrlNbr;
   }

   /**
    * Sets the value of the supercededVersionCtrlNbr property.
    *
    * @param aSupercededVersionCtrlNbr the new value of the supercededVersionCtrlNbr property
    */
   public void setSupercededVersionCtrlNbr(Integer aSupercededVersionCtrlNbr)
   {
      supercededVersionCtrlNbr = aSupercededVersionCtrlNbr;
   }

   /**
    * Access method for the supercededParentUid property.
    *
    * @return   the current value of the supercededParentUid property
    */
   public Long getSupercededParentUid()
   {
      return supercededParentUid;
   }

   /**
    * Sets the value of the supercededParentUid property.
    *
    * @param aSupercededParentUid the new value of the supercededParentUid property
    */
   public void setSupercededParentUid(Long aSupercededParentUid)
   {
      supercededParentUid = aSupercededParentUid;
   }

   /**
    * Access method for the survivingPersonUid property.
    *
    * @return   the current value of the survivingPersonUid property
    */
   public Long getSurvivingPersonUid()
   {
      return survivingPersonUid;
   }

   /**
    * Sets the value of the survivingPersonUid property.
    *
    * @param aSurvivingPersonUid the new value of the survivingPersonUid property
    */
   public void setSurvivingPersonUid(Long aSurvivingPersonUid)
   {
      survivingPersonUid = aSurvivingPersonUid;
   }

   /**
    * Access method for the survivingVersionCtrlNbr property.
    *
    * @return   the current value of the survivingVersionCtrlNbr property
    */
   public Integer getSurvivingVersionCtrlNbr()
   {
      return survivingVersionCtrlNbr;
   }

   /**
    * Sets the value of the survivingVersionCtrlNbr property.
    *
    * @param aSurvivingVersionCtrlNbr the new value of the survivingVersionCtrlNbr property
    */
   public void setSurvivingVersionCtrlNbr(Integer aSurvivingVersionCtrlNbr)
   {
      survivingVersionCtrlNbr = aSurvivingVersionCtrlNbr;
   }

   /**
    * Access method for the survivingParentUid property.
    *
    * @return   the current value of the survivingParentUid property
    */
   public Long getSurvivingParentUid()
   {
      return survivingParentUid;
   }

   /**
    * Sets the value of the survivingParentUid property.
    *
    * @param aSurvivingParentUid the new value of the survivingParentUid property
    */
   public void setSurvivingParentUid(Long aSurvivingParentUid)
   {
      survivingParentUid = aSurvivingParentUid;
   }

   /**
    * Access method for the recordStatusCd property.
    *
    * @return   the current value of the recordStatusCd property
    */
   public String getRecordStatusCd()
   {
      return recordStatusCd;
   }

   /**
    * Sets the value of the recordStatusCd property.
    *
    * @param aRecordStatusCd the new value of the recordStatusCd property
    */
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      recordStatusCd = aRecordStatusCd;
   }

   /**
    * Access method for the recordStatusTime property.
    *
    * @return   the current value of the recordStatusTime property
    */
   public Timestamp getRecordStatusTime()
   {
      return recordStatusTime;
   }

   /**
    * Sets the value of the recordStatusTime property.
    *
    * @param aRecordStatusTime the new value of the recordStatusTime property
    */
   public void setRecordStatusTime(Timestamp aRecordStatusTime)
   {
      recordStatusTime = aRecordStatusTime;
   }

   /**
    * Access method for the mergeUserId property.
    *
    * @return   the current value of the mergeUserId property
    */
   public String getMergeUserId()
   {
      return mergeUserId;
   }

   /**
    * Sets the value of the mergeUserId property.
    *
    * @param aMergeUserId the new value of the mergeUserId property
    */
   public void setMergeUserId(String aMergeUserId)
   {
      mergeUserId = aMergeUserId;
   }

   /**
    * Access method for the mergeTime property.
    *
    * @return   the current value of the mergeTime property
    */
   public Timestamp getMergeTime()
   {
      return mergeTime;
   }

   /**
    * Sets the value of the mergeTime property.
    *
    * @param aMergeTime the new value of the mergeTime property
    */
   public void setMergeTime(Timestamp aMergeTime)
   {
      mergeTime = aMergeTime;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3EA69D920127
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3EA69D920136
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3EA69D920146
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3EA69D920147
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3EA69D920156
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3EA69D920157
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3EA69D920166
    */
   public boolean isItDelete()
   {
    return true;
   }


}
