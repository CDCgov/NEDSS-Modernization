//Source file: C:\\development\\source\\gov\\cdc\\nedss\\helpers\\RoleDT.java

package gov.cdc.nedss.association.dt;


import gov.cdc.nedss.util.*;

import java.sql.Timestamp;
import java.io.*;

import gov.cdc.nedss.systemservice.util.*;

public class RoleDT extends AbstractVO implements AssocDTInterface
{
	private static final long serialVersionUID = 1L;
   private Long roleSeq;
   private String addReasonCd;
   private Timestamp addTime;
   private Long addUserId;
   private String cd;
   private String cdDescTxt;
   //private String classCd;
   private String effectiveDurationAmt;
   private String effectiveDurationUnitCd;
   private Timestamp effectiveFromTime;
   private Timestamp effectiveToTime;
   private String lastChgReasonCd;
   private Timestamp lastChgTime;
   private Long lastChgUserId;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String scopingRoleCd;
   private String statusCd;
   private Timestamp statusTime;
   private String userAffiliationTxt;
   private Long scopingEntityUid;
   private Integer scopingRoleSeq;
   private Long subjectEntityUid;
   private String scopingClassCd;
   private String subjectClassCd;


   /**
    * @roseuid 3BF16BB50267
    */
   public RoleDT()
   {

   }

   /**
    * Access method for the roleSeq property.
    *
    * @return   the current value of the roleSeq property
    */
   public Long getRoleSeq()
   {
      return roleSeq;
   }

   /**
    * Sets the value of the roleSeq property.
    *
    * @param aRoleSeq the new value of the roleSeq property
    */
   public void setRoleSeq(Long aRoleSeq)
   {
      roleSeq = aRoleSeq;
      setItDirty(true);
   }

   /**
    * Access method for the addReasonCd property.
    *
    * @return   the current value of the addReasonCd property
    */
   public String getAddReasonCd()
   {
      return addReasonCd;
   }

   /**
    * Sets the value of the addReasonCd property.
    *
    * @param aAddReasonCd the new value of the addReasonCd property
    */
   public void setAddReasonCd(String aAddReasonCd)
   {
      addReasonCd = aAddReasonCd;
      setItDirty(true);
   }

   /**
    * Access method for the addTime property.
    *
    * @return   the current value of the addTime property
    */
   public Timestamp getAddTime()
   {
      return addTime;
   }

   /**
    * Sets the value of the addTime property.
    *
    * @param aAddTime the new value of the addTime property
    */
   public void setAddTime(Timestamp aAddTime)
   {
      addTime = aAddTime;
      setItDirty(true);
   }

   /**
    * Access method for the addUserId property.
    *
    * @return   the current value of the addUserId property
    */
   public Long getAddUserId()
   {
      return addUserId;
   }

   /**
    * Sets the value of the addUserId property.
    *
    * @param aAddUserId the new value of the addUserId property
    */
   public void setAddUserId(Long aAddUserId)
   {
      addUserId = aAddUserId;
      setItDirty(true);
   }

   /**
    * Access method for the cd property.
    *
    * @return   the current value of the cd property
    */
   public String getCd()
   {
      return cd;
   }

   /**
    * Sets the value of the cd property.
    *
    * @param aCd the new value of the cd property
    */
   public void setCd(String aCd)
   {
      cd = aCd;
      setItDirty(true);
   }

   /**
    * Access method for the cdDescTxt property.
    *
    * @return   the current value of the cdDescTxt property
    */
   public String getCdDescTxt()
   {
      return cdDescTxt;
   }

   /**
    * Sets the value of the cdDescTxt property.
    *
    * @param aCdDescTxt the new value of the cdDescTxt property
    */
   public void setCdDescTxt(String aCdDescTxt)
   {
      cdDescTxt = aCdDescTxt;
      setItDirty(true);
   }

   /**
    * Access method for the classCd property.
    *
    * @return   the current value of the classCd property
    */
/*   public String getClassCd()
   {
      return classCd;
   }
*/
   /**
    * Sets the value of the classCd property.
    *
    * @param aClassCd the new value of the classCd property
    */
  /* public void setClassCd(String aClassCd)
   {
      classCd = aClassCd;
      setItDirty(true);
   }
*/
   /**
    * Access method for the effectiveDurationAmt property.
    *
    * @return   the current value of the effectiveDurationAmt property
    */
   public String getEffectiveDurationAmt()
   {
      return effectiveDurationAmt;
   }

   /**
    * Sets the value of the effectiveDurationAmt property.
    *
    * @param aEffectiveDurationAmt the new value of the effectiveDurationAmt property
    */
   public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
   {
      effectiveDurationAmt = aEffectiveDurationAmt;
      setItDirty(true);
   }

   /**
    * Access method for the effectiveDurationUnitCd property.
    *
    * @return   the current value of the effectiveDurationUnitCd property
    */
   public String getEffectiveDurationUnitCd()
   {
      return effectiveDurationUnitCd;
   }

   /**
    * Sets the value of the effectiveDurationUnitCd property.
    *
    * @param aEffectiveDurationUnitCd the new value of the effectiveDurationUnitCd property
    */
   public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
   {
      effectiveDurationUnitCd = aEffectiveDurationUnitCd;
      setItDirty(true);
   }

   /**
    * Access method for the effectiveFromTime property.
    *
    * @return   the current value of the effectiveFromTime property
    */
   public Timestamp getEffectiveFromTime()
   {
      return effectiveFromTime;
   }

   /**
    * Sets the value of the effectiveFromTime property.
    *
    * @param aEffectiveFromTime the new value of the effectiveFromTime property
    */
   public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
   {
      effectiveFromTime = aEffectiveFromTime;
      setItDirty(true);
   }

   /**
    * Access method for the effectiveToTime property.
    *
    * @return   the current value of the effectiveToTime property
    */
   public Timestamp getEffectiveToTime()
   {
      return effectiveToTime;
   }

   /**
    * Sets the value of the effectiveToTime property.
    *
    * @param aEffectiveToTime the new value of the effectiveToTime property
    */
   public void setEffectiveToTime(Timestamp aEffectiveToTime)
   {
      effectiveToTime = aEffectiveToTime;
      setItDirty(true);
   }

   /**
    * Access method for the lastChgReasonCd property.
    *
    * @return   the current value of the lastChgReasonCd property
    */
   public String getLastChgReasonCd()
   {
      return lastChgReasonCd;
   }

   /**
    * Sets the value of the lastChgReasonCd property.
    *
    * @param aLastChgReasonCd the new value of the lastChgReasonCd property
    */
   public void setLastChgReasonCd(String aLastChgReasonCd)
   {
      lastChgReasonCd = aLastChgReasonCd;
      setItDirty(true);
   }

   /**
    * Access method for the lastChgTime property.
    *
    * @return   the current value of the lastChgTime property
    */
   public Timestamp getLastChgTime()
   {
      return lastChgTime;
   }

   /**
    * Sets the value of the lastChgTime property.
    *
    * @param aLastChgTime the new value of the lastChgTime property
    */
   public void setLastChgTime(Timestamp aLastChgTime)
   {
      lastChgTime = aLastChgTime;
      setItDirty(true);
   }

   /**
    * Access method for the lastChgUserId property.
    *
    * @return   the current value of the lastChgUserId property
    */
   public Long getLastChgUserId()
   {
      return lastChgUserId;
   }

   /**
    * Sets the value of the lastChgUserId property.
    *
    * @param aLastChgUserId the new value of the lastChgUserId property
    */
   public void setLastChgUserId(Long aLastChgUserId)
   {
      lastChgUserId = aLastChgUserId;
      setItDirty(true);
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
      setItDirty(true);
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
      setItDirty(true);
   }

   /**
    * Access method for the scopingRoleCd property.
    *
    * @return   the current value of the scopingRoleCd property
    */
   public String getScopingRoleCd()
   {
      return scopingRoleCd;
   }

   /**
    * Sets the value of the scopingRoleCd property.
    *
    * @param aScopingRoleCd the new value of the scopingRoleCd property
    */
   public void setScopingRoleCd(String aScopingRoleCd)
   {
      scopingRoleCd = aScopingRoleCd;
      setItDirty(true);
   }

   /**
    * Access method for the statusCd property.
    *
    * @return   the current value of the statusCd property
    */
   public String getStatusCd()
   {
      return statusCd;
   }

   /**
    * Sets the value of the statusCd property.
    *
    * @param aStatusCd the new value of the statusCd property
    */
   public void setStatusCd(String aStatusCd)
   {
      statusCd = aStatusCd;
      setItDirty(true);
   }

   /**
    * Access method for the statusTime property.
    *
    * @return   the current value of the statusTime property
    */
   public Timestamp getStatusTime()
   {
      return statusTime;
   }

   /**
    * Sets the value of the statusTime property.
    *
    * @param aStatusTime the new value of the statusTime property
    */
   public void setStatusTime(Timestamp aStatusTime)
   {
      statusTime = aStatusTime;
      setItDirty(true);
   }

   /**
    * Access method for the userAffiliationTxt property.
    *
    * @return   the current value of the userAffiliationTxt property
    */
   public String getUserAffiliationTxt()
   {
      return userAffiliationTxt;
   }

   /**
    * Sets the value of the userAffiliationTxt property.
    *
    * @param aUserAffiliationTxt the new value of the userAffiliationTxt property
    */
   public void setUserAffiliationTxt(String aUserAffiliationTxt)
   {
      userAffiliationTxt = aUserAffiliationTxt;
      setItDirty(true);
   }

   /**
    * Access method for the scopingEntityUid property.
    *
    * @return   the current value of the scopingEntityUid property
    */
   public Long getScopingEntityUid()
   {
      return scopingEntityUid;
   }

   /**
    * Sets the value of the scopingEntityUid property.
    *
    * @param aScopingEntityUid the new value of the scopingEntityUid property
    */
   public void setScopingEntityUid(Long aScopingEntityUid)
   {
      scopingEntityUid = aScopingEntityUid;
      setItDirty(true);
   }

   /**
    * Access method for the scopingRoleSeq property.
    *
    * @return   the current value of the scopingRoleSeq property
    */
   public Integer getScopingRoleSeq()
   {
      return scopingRoleSeq;
   }

   /**
    * Sets the value of the scopingRoleSeq property.
    *
    * @param aScopingRoleSeq the new value of the scopingRoleSeq property
    */
   public void setScopingRoleSeq(Integer aScopingRoleSeq)
   {
      scopingRoleSeq = aScopingRoleSeq;
      setItDirty(true);
   }

   /**
    * Access method for the subjectEntityUid property.
    *
    * @return   the current value of the subjectEntityUid property
    */
   public Long getSubjectEntityUid()
   {
      return subjectEntityUid;
   }

   /**
    * Sets the value of the subjectEntityUid property.
    *
    * @param aSubjectEntityUid the new value of the subjectEntityUid property
    */
   public void setSubjectEntityUid(Long aSubjectEntityUid)
   {
      subjectEntityUid = aSubjectEntityUid;
      setItDirty(true);
   }

      /**
    * Access method for the scopingClassCd property.
    *
    * @return   the current value of the scopingClassCd property
    */
   public String getScopingClassCd()
   {
      return scopingClassCd;
   }

   /**
    * Sets the value of the scopingClassCd property.
    *
    * @param aScopingClassCd the new value of the scopingClassCd property
    */
   public void setScopingClassCd(String aScopingClassCd)
   {
      scopingClassCd = aScopingClassCd;
   }

   /**
    * Access method for the subjectClassCd property.
    *
    * @return   the current value of the subjectClassCd property
    */
   public String getSubjectClassCd()
   {
      return subjectClassCd;
   }

   /**
    * Sets the value of the subjectClassCd property.
    *
    * @param aSubjectClassCd the new value of the subjectClassCd property
    */
   public void setSubjectClassCd(String aSubjectClassCd)
   {
      subjectClassCd = aSubjectClassCd;
   }


   /**
    * @roseuid 3BF13DA401E7
    */
   public void Role()
   {

   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C44A8D200A9
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
      voClass =  ((RoleDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
   }

   /**
    * @param itDirty
    * @roseuid 3C44A8D202EE
    */
   public void setItDirty(boolean itDirty)
   {
    this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3C44A8D30000
    */
   public boolean isItDirty()
   {
    return this.itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3C44A8D3005A
    */
   public void setItNew(boolean itNew)
   {
	this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3C44A8D3015F
    */
   public boolean isItNew()
   {
    return this.itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3C44A8D301B9
    */
   public void setItDelete(boolean itDelete)
   {
	this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3C44A8D302C7
    */
   public boolean isItDelete()
   {
    return this.itDelete;
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
/*
   public void debugPrint()
    {

        System.out.println("subjectEntityUid: " + subjectEntityUid.toString());
        System.out.println("classCd: " + classCd.toString());
        System.out.println("roleSeq: " + roleSeq.toString());
        System.out.println("addReasonCd: " + addReasonCd.toString());
        System.out.println("addTime: " + addTime.toString());
        System.out.println("addUserId: " + addUserId.toString());
        System.out.println("cd: " + cd.toString());
        System.out.println("cdDescTxt: " + cdDescTxt.toString());
        System.out.println("effectiveDurationAmt: " + effectiveDurationAmt.toString());
        System.out.println("effectiveDurationUnitCd: " + effectiveDurationUnitCd.toString());
        System.out.println("effectiveFromTime: " + effectiveFromTime.toString());
        System.out.println("effectiveToTime: " + effectiveToTime.toString());
        System.out.println("lastChgReasonCd: " + lastChgReasonCd.toString());
        System.out.println("lastChgTime: " + lastChgTime.toString());
        System.out.println("lastChgUserId: " + lastChgUserId.toString());
        System.out.println("recordStatusCd: " + recordStatusCd.toString());
        System.out.println("recordStatusTime: " + recordStatusTime.toString());
        System.out.println("scopingEntityUid: " + scopingEntityUid.toString());
        System.out.println("scopingRoleCd: " + scopingRoleCd.toString());
        System.out.println("scopingRoleSeq: " + scopingRoleSeq.toString());
        System.out.println("statusCd: " + statusCd.toString());
        System.out.println("statusTime: " + statusTime.toString());
        System.out.println("userAffiliationTxt: " + userAffiliationTxt.toString());
    }
*/
}
