//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\RealizedRole.java

package gov.cdc.nedss.systemservice.nbssecurity;


import  gov.cdc.nedss.util.*;

public class RealizedRole extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   private String roleName;
   private String programAreaCode;
   private String jurisdictionCode;
   private String oldProgramAreaCode;
   private String oldJurisdictionCode;
   private boolean guest;
   private boolean readOnly = true; // make sure that the default access for permissionset is readyonly
   private int seqNum =0;

   private String recordStatus = "";
   private String guestString ="N";
//   private String guestString ="BLANK";

   /**
    * This is a shortcut for finding the security role.
    */
   public PermissionSet thePermissionSet;

   /**
    * @roseuid 3CE2C2CC02F7
    */
   public RealizedRole()
   {

   }

   /**
    * Access method for the roleName property.
    *
    * @return   the current value of the roleName property
    */
   public String getRoleName()
   {
      return roleName;
   }

   /**
    * Sets the value of the roleName property.
    *
    * @param aRoleName the new value of the roleName property
    */
   public void setRoleName(String aRoleName)
   {
      roleName = aRoleName;
   }

   /**
    * Access method for the programAreaCode property.
    *
    * @return   the current value of the programAreaCode property
    */
   public String getProgramAreaCode()
   {
      return programAreaCode;
   }

   /**
    * Sets the value of the programAreaCode property.
    *
    * @param aProgramAreaCode the new value of the programAreaCode property
    */
   public void setProgramAreaCode(String aProgramAreaCode)
   {
      this.programAreaCode = aProgramAreaCode;
   }



   /**
    * Access method for the programAreaCode property.
    *
    * @return   the value of the programAreaCode property that is being replaced
    *           due to editing the realized role.  This is needed by ldap to find the
    *           entry that is being modified
    */
   public String getOldProgramAreaCode()
   {
      return this.oldProgramAreaCode;
   }

   /**
    * Sets the value of the programAreaCode property.
    *
    * @param aProgramAreaCode the value of the programAreaCode property that will
    *                         be replaced due to editing a realized role.  This is
    *                         need by ldap to find the entry being modified.
    */
   public void setOldProgramAreaCode(String aProgramAreaCode)
   {
      this.oldProgramAreaCode = aProgramAreaCode;
   }


   /**
    * Access method for the jurisdictionCode property.
    *
    * @return   the current value of the jurisdictionCode property
    */
   public String getJurisdictionCode()
   {
      return jurisdictionCode;
   }

   /**
    * Sets the value of the jurisdictionCode property.
    *
    * @param aJurisdictionCode the new value of the jurisdictionCode property
    */
   public void setJurisdictionCode(String aJurisdictionCode)
   {
      this.jurisdictionCode = aJurisdictionCode;

   }


   /**
    * Access method for the jurisdictionCode property.
    *
    * @return   the value of the jurisdictionCode property being replaced by a new
    *           value because a the realized role is being edited.  This value is
    *           needed for ldap to find the entry being modified
    */
   public String getOldJurisdictionCode()
   {
      return this.oldJurisdictionCode;
   }

   /**
    * Sets the value of the jurisdictionCode property.
    *
    * @param aJurisdictionCode the value of the jurisdictionCode property to be replaced
    *                          because the realized role is being modified.  LDAP needs
    *                          this value to find the entry being modified.
    */
   public void setOldJurisdictionCode(String aJurisdictionCode)
   {
      this.oldJurisdictionCode = aJurisdictionCode;
   }

   /**
    * Determines if the guest property is true.
    *
    * @return   <code>true<code> if the guest property is true
    */
   public boolean getGuest()
   {
      return guest;
   }

   /**
    * Sets the value of the guest property.
    *
    * @param aGuest the new value of the guest property
    */
   public void setGuest(boolean aGuest)
   {
      guest = aGuest;
   }

   /**
    * Access method for the thePermissionSet property.
    *
    * @return   the current value of the thePermissionSet property
    */
   public PermissionSet getThePermissionSet()
   {
      return thePermissionSet;
   }

    /**
    * @param itDirty
    *
    */
   public void setItDirty(boolean itDirty)
   {
	this.itDirty = itDirty;
   }

   /**
    * @return boolean
    *
    */
   public boolean isItDirty()
   {
	return itDirty;
   }

   /**
    * @param itNew
    *
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }

   /**
    * @return boolean
    *
    */
   public boolean isItNew()
   {
        return itNew;
   }

   /**
    * @param itDelete
    *
    */
   public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * @return boolean
    *
    */
   public boolean isItDelete()
   {
    return itDelete;
   }

   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

  // for struts
   public void  setSeqNum(int seq)
   {
    this.seqNum = seq;
   }

   public void  setReadOnly(boolean boolvalue)
   {
    this.readOnly = boolvalue;
   }

   public String getStatusCd()
   {
    return this.recordStatus;

   }

   public void  setStatusCd(String status)
   {
    this.recordStatus = status;
   }

   public int getSeqNum()
   {
    return this.seqNum;
   }

    public boolean getReadOnly()
   {
    return this.readOnly;
   }
   public void  setGuestString(String agueststring)
   {
    this.guestString =  agueststring;
   }

   public String getGuestString()
   {
    return this.guestString;
   }


}
