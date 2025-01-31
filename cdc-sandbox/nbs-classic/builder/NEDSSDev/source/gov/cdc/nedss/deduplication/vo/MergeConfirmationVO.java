//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\deduplication\\vo\\MergeConfirmationVO.java

package gov.cdc.nedss.deduplication.vo;
import gov.cdc.nedss.util.*;

public class MergeConfirmationVO implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
   private String localId;
   private Boolean survivor;
   private String lastName;
   private String middleName;
   private String firstName;
   private String nmUseCd;
   private String nmUseCdDesc;

   /**
    * @roseuid 3E92CF320261
    */
   public MergeConfirmationVO()
   {

   }

   /**
    * Access method for the localID property.
    *
    * @return   the current value of the localID property
    */
   public String getLocalId()
   {
      return localId;
   }

   /**
    * Sets the value of the localID property.
    *
    * @param aLocalID the new value of the localID property
    */
   public void setLocalId(String localId)
   {
      this.localId = localId;
   }

   /**
    * Access method for the survivor property.
    *
    * @return   the current value of the survivor property
    */
   public Boolean getSurvivor()
   {
      return survivor;
   }

   /**
    * Sets the value of the survivor property.
    *
    * @param aSurvivor the new value of the survivor property
    */
   public void setSurvivor(Boolean aSurvivor)
   {
      survivor = aSurvivor;
   }

   /**
    * Access method for the lastName property.
    *
    * @return   the current value of the lastName property
    */
   public String getLastName()
   {
      return lastName;
   }

   /**
    * Sets the value of the lastName property.
    *
    * @param aLastName the new value of the lastName property
    */
   public void setLastName(String aLastName)
   {
      lastName = aLastName;
   }

   /**
    * Access method for the middleName property.
    *
    * @return   the current value of the middleName property
    */
   public String getMiddleName()
   {
      return middleName;
   }

   /**
    * Sets the value of the middleName property.
    *
    * @param aMiddleName the new value of the middleName property
    */
   public void setMiddleName(String aMiddleName)
   {
      middleName = aMiddleName;
   }

   /**
    * Access method for the firstName property.
    *
    * @return   the current value of the firstName property
    */
   public String getFirstName()
   {
      return firstName;
   }

   /**
    * Sets the value of the firstName property.
    *
    * @param aFirstName the new value of the firstName property
    */
   public void setFirstName(String aFirstName)
   {
      firstName = aFirstName;
   }

   public void setNmUseCd(String nmUseCd) {
     this.nmUseCd = nmUseCd;
   }

   public String getNmUseCd() {
     return nmUseCd;
   }
   public void setNmUseCdDesc(String nmUseCdDesc) {
    this.nmUseCdDesc = nmUseCdDesc;
  }

  public String getNmUseCdDesc() {
    return nmUseCdDesc;
  }

}
