//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\helpers\\ParticipationDT.java

package gov.cdc.nedss.association.dt;


import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NedssUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;


public class ParticipationDT extends AbstractVO implements AssocDTInterface, Comparable
{
	private static final long serialVersionUID = 1L;
   private String addReasonCd;
   private Timestamp addTime;
   private Long addUserId;
   private String awarenessCd;
   private String awarenessDescTxt;
   private String durationAmt;
   private String durationUnitCd;
   private Timestamp fromTime;
   private String lastChgReasonCd;
   private Timestamp lastChgTime;
   private Long lastChgUserId;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String statusCd;
   private Timestamp statusTime;
   private String typeCd;
   private Timestamp toTime;
   private String typeDescTxt;
   private String userAffiliationTxt;
   private String subjectEntityClassCd;


   private Long subjectEntityUid;

   private Integer roleSeq;
   private String cd;
   private String actClassCd;
   private String subjectClassCd;
   private Long actUid;

   /**
    * @roseuid 3BF16BB4036A
    */
   public ParticipationDT()
   {

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
      //addReasonCd = aAddReasonCd;
      setItDirty(true);
      addReasonCd = aAddReasonCd;

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
      //addTime = aAddTime;
      setItDirty(true);
      addTime = aAddTime;

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
      //addUserId = aAddUserId;
      setItDirty(true);
      addUserId = aAddUserId;

   }

   /**
    * Access method for the awarenessCd property.
    *
    * @return   the current value of the awarenessCd property
    */
   public String getAwarenessCd()
   {
        return awarenessCd;
   }

   /**
    * Sets the value of the awarenessCd property.
    *
    * @param aAwarenessCd the new value of the awarenessCd property
    */
   public void setAwarenessCd(String aAwarenessCd)
   {
      //awarenessCd = aAwarenessCd;
      setItDirty(true);
      awarenessCd = aAwarenessCd;

   }

   /**
    * Access method for the awarenessDescTxt property.
    *
    * @return   the current value of the awarenessDescTxt property
    */
   public String getAwarenessDescTxt()
   {
        return awarenessDescTxt;
   }

   /**
    * Sets the value of the awarenessDescTxt property.
    *
    * @param aAwarenessDescTxt the new value of the awarenessDescTxt property
    */
   public void setAwarenessDescTxt(String aAwarenessDescTxt)
   {
      //awarenessDescTxt = aAwarenessDescTxt;
      setItDirty(true);
      awarenessDescTxt = aAwarenessDescTxt;

   }

   /**
    * Access method for the durationAmt property.
    *
    * @return   the current value of the durationAmt property
    */
   public String getDurationAmt()
   {
        return durationAmt;
   }

   /**
    * Sets the value of the durationAmt property.
    *
    * @param aDurationAmt the new value of the durationAmt property
    */
   public void setDurationAmt(String aDurationAmt)
   {
      //durationAmt = aDurationAmt;
      setItDirty(true);
      durationAmt = aDurationAmt;

   }

   /**
    * Access method for the durationUnitCd property.
    *
    * @return   the current value of the durationUnitCd property
    */
   public String getDurationUnitCd()
   {
        return durationUnitCd;
   }

   /**
    * Sets the value of the durationUnitCd property.
    *
    * @param aDurationUnitCd the new value of the durationUnitCd property
    */
   public void setDurationUnitCd(String aDurationUnitCd)
   {
      //durationUnitCd = aDurationUnitCd;
      setItDirty(true);
      durationUnitCd = aDurationUnitCd;

   }

   /**
    * Access method for the fromTime property.
    *
    * @return   the current value of the fromTime property
    */
   public Timestamp getFromTime()
   {
        return fromTime;
   }

   /**
    * Sets the value of the fromTime property.
    *
    * @param aFromTime the new value of the fromTime property
    */
   public void setFromTime(Timestamp aFromTime)
   {
      //fromTime = aFromTime;
      setItDirty(true);
      fromTime = aFromTime;

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
      //lastChgReasonCd = aLastChgReasonCd;
      setItDirty(true);
      lastChgReasonCd = aLastChgReasonCd;

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
      //lastChgTime = aLastChgTime;
      setItDirty(true);
      lastChgTime = aLastChgTime;

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
      //lastChgUserId = aLastChgUserId;
      setItDirty(true);
      lastChgUserId = aLastChgUserId;

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
      //recordStatusCd = aRecordStatusCd;
      setItDirty(true);
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
      //statusCd = aStatusCd;
      setItDirty(true);
      statusCd = aStatusCd;

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
      //statusTime = aStatusTime;
      setItDirty(true);
      statusTime = aStatusTime;

   }

   /**
    * Access method for the typeCd property.
    *
    * @return   the current value of the typeCd property
    */
   public String getTypeCd()
   {
        return typeCd;
   }

   /**
    * Sets the value of the typeCd property.
    *
    * @param aTypeCd the new value of the typeCd property
    */
   public void setTypeCd(String aTypeCd)
   {
      //typeCd = aTypeCd;
      setItDirty(true);
      typeCd = aTypeCd;

   }

   /**
    * Access method for the toTime property.
    *
    * @return   the current value of the toTime property
    */
   public Timestamp getToTime()
   {
        return toTime;
   }

   /**
    * Sets the value of the toTime property.
    *
    * @param aToTime the new value of the toTime property
    */
   public void setToTime(Timestamp aToTime)
   {
      //toTime = aToTime;
      setItDirty(true);
      toTime = aToTime;

   }

   /**
    * Access method for the typeDescTxt property.
    *
    * @return   the current value of the typeDescTxt property
    */
   public String getTypeDescTxt()
   {
        return typeDescTxt;
   }

   /**
    * Sets the value of the typeDescTxt property.
    *
    * @param aTypeDescTxt the new value of the typeDescTxt property
    */
   public void setTypeDescTxt(String aTypeDescTxt)
   {
      //typeDescTxt = aTypeDescTxt;
      setItDirty(true);
      typeDescTxt = aTypeDescTxt;

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
      //userAffiliationTxt = aUserAffiliationTxt;
      setItDirty(true);
      userAffiliationTxt = aUserAffiliationTxt;

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
      //subjectEntityUid = aSubjectEntityUid;
      setItDirty(true);
      subjectEntityUid = aSubjectEntityUid;






















   }

   /**
    * Access method for the roleSeq property.
    *
    * @return   the current value of the roleSeq property
    */
   public Integer getRoleSeq()
   {
        return roleSeq;
   }

   /**
    * Sets the value of the roleSeq property.
    *
    * @param aRoleSeq the new value of the roleSeq property
    */
   public void setRoleSeq(Integer aRoleSeq)
   {
      //roleSeq = aRoleSeq;
      setItDirty(true);
      roleSeq = aRoleSeq;
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
   }

   /**
    * Access method for the actClassCd property.
    *
    * @return   the current value of the actClassCd property
    */
   public String getActClassCd()
   {
      return actClassCd;
   }

   /**
    * Sets the value of the actClassCd property.
    *
    * @param aActClassCd the new value of the actClassCd property
    */
   public void setActClassCd(String aActClassCd)
   {
      actClassCd = aActClassCd;
   }

   /**
    * Access method for the subjectEntityClassCd property.
    *
    * @return   the current value of the subjectEntityClassCd property
    */
   public String getSubjectEntityClassCd()
   {
      return subjectEntityClassCd;
   }

   /**
    * Sets the value of the subjectEntityClassCd property.
    *
    * @param aSubjectClassCd the new value of the subjectEntityClassCd property
    */
   public void setSubjectEntityClassCd(String aSubjectEntityClassCd)
   {
      subjectEntityClassCd = aSubjectEntityClassCd;
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
    * Access method for the actUid property.
    *
    * @return   the current value of the actUid property
    */
   public Long getActUid()
   {
        return actUid;
   }

   /**
    * Sets the value of the actUid property.
    *
    * @param aActUid the new value of the actUid property
    */
   public void setActUid(Long aActUid)
   {
      //actUid = aActUid;
      setItDirty(true);
      actUid = aActUid;
   }

   /**
    * @roseuid 3BF13DB2016F
    */
   public void Participation()
   {

   }

   /**
    * Compare two java Objects
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C44A8C20241
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {

      voClass =  ((ParticipationDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
   }

   /**
    * set the flag to dirty(for edit)
    * @param itDirty
    * @roseuid 3C44A8C3022E
    */
   public void setItDirty(boolean itDirty)
   {
        this.itDirty = itDirty;
   }

   /**
    * check the flag for dirty
    * @return boolean
    * @roseuid 3C44A8C303C9
    */
   public boolean isItDirty()
   {
        return itDirty;
   }

   /**
    * set the flag to new(Create)
    * @param itNew
    * @roseuid 3C44A8C4006D
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }

   /**
    * check the flag for new
    * @return boolean
    * @roseuid 3C44A8C4021B
    */
   public boolean isItNew()
   {
        return itNew;
   }

   /**
    * set the flag for delete(Delete)
    * @param itDelete
    * @roseuid 3C44A8C402B2
    */
   public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * check the flag for delete
    * @return boolean
    * @roseuid 3C44A8C500A0
    */
   public boolean isItDelete()
   {
        return itDelete;
   }

/**
 * This  method will prepare the clone for the object
 * @return deepCopy -- the clone Object
 * @throws CloneNotSupportedException
 * @throws IOException
 * @throws ClassNotFoundException
 */
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

@Override
public int compareTo(Object arg0) {
	if(arg0 instanceof ParticipationDT){
		if(this.getFromTime() != null && ((ParticipationDT)arg0).getFromTime() != null){
		return this.getFromTime().compareTo(((ParticipationDT)arg0).getFromTime());
		}
	}
	return 1;
}
}
/**
 *
 * ParticipationDT.setParticipationSeq(Integer){
 *       //participationSeq = aParticipationSeq;
 *       participationSeq = aParticipationSeq;
 *       setItDirty(true);
 *    }
 *
 *
 * ParticipationDT.getClassCd(){
 *         return classCd;
 *    }
 *
 *
 * ParticipationDT.getParticipationSeq(){
 *         return participationSeq;
 *    }
 *
 *
 * ParticipationDT.setClassCd(String){
 *       //classCd = aClassCd;
 *       classCd = aClassCd;
 *       setItDirty(true);
 *    }
 *
 *
 *
 * ParticipationDT.debugPrint(){
 *         System.out.println("subjectEntityUid: " + subjectEntityUid.toString());
 *         System.out.println("actUid: " + actUid.toString());
 *         System.out.println("participationSeq: " + participationSeq.toString());
 *         System.out.println("addReasonCd: " + addReasonCd.toString());
 *         System.out.println("addTime: " + addTime.toString());
 *         System.out.println("addUserId: " + addUserId.toString());
 *         System.out.println("awarenessCd: " + awarenessCd.toString());
 *         System.out.println("awarenessDescTxt: " + awarenessDescTxt.toString());
 *         System.out.println("classCd: " + classCd.toString());
 *         System.out.println("durationAmt: " + durationAmt.toString());
 *         System.out.println("durationUnitCd: " + durationUnitCd.toString());
 *         System.out.println("fromTime: " + fromTime.toString());
 *         System.out.println("lastChgReasonCd: " + lastChgReasonCd.toString());
 *         System.out.println("lastChgTime: " + lastChgTime.toString());
 *         System.out.println("lastChgUserId: " + lastChgUserId.toString());
 *         System.out.println("recordStatusCd: " + recordStatusCd.toString());
 *         System.out.println("recordStatusTime: " + recordStatusTime.toString());
 *         System.out.println("roleSeq: " + roleSeq.toString());
 *         System.out.println("statusCd: " + statusCd.toString());
 *         System.out.println("statusTime: " + statusTime.toString());
 *         System.out.println("toTime: " + toTime.toString());
 *         System.out.println("typeCd: " + typeCd.toString());
 *         System.out.println("typeDescTxt: " + typeDescTxt.toString());
 *         System.out.println("userAffiliationTxt: " + userAffiliationTxt.toString());
 *     }
 *
 *
 */
