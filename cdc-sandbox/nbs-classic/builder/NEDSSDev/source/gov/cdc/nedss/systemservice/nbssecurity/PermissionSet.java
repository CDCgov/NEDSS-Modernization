//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\PermissionSet.java

package gov.cdc.nedss.systemservice.nbssecurity;

import  gov.cdc.nedss.util.*;

public class PermissionSet extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   private String roleName;
   private String description;
   private boolean readOnly;

   /**
    * This byte array stores the business object operation infrormation for the
    * security role in a compact format.  Each byte in the array represents a single
    * business object operation.  The businessObjectOperationUtil class is provided
    * to assist in the access and management of the operations stored in this manner.
    */
   private byte[] businessObjectOperations;

   /**
    * @roseuid 3CE2C2CB0314
    */
   public PermissionSet()
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
      roleName = aRoleName.trim();
   }

   /**
    * Access method for the description property.
    *
    * @return   the current value of the description property
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * Sets the value of the description property.
    *
    * @param aDescription the new value of the description property
    */
   public void setDescription(String aDescription)
   {
      description = aDescription;
   }

   /**
    * Determines if the readOnly property is true.
    *
    * @return   <code>true<code> if the readOnly property is true
    */
   public boolean getReadOnly()
   {
      return readOnly;
   }

   /**
    * Sets the value of the readOnly property.
    *
    * @param aReadOnly the new value of the readOnly property
    */
   public void setReadOnly(boolean aReadOnly)
   {
      readOnly = aReadOnly;
   }

   /**
    * Access method for the businessObjectOperations property.
    *
    * @return   the current value of the businessObjectOperations property
    */
   public byte[] getBusinessObjectOperations()
   {
      return businessObjectOperations;
   }

   /**
    * Sets the value of the businessObjectOperations property.
    *
    * @param aBusinessObjectOperations the new value of the businessObjectOperations property
    */
   public void setBusinessObjectOperations(byte[] aBusinessObjectOperations)
   {
      businessObjectOperations = aBusinessObjectOperations;
   }

   /**
    * Returns true if the business object operation stored at the given index
    * contains the value OPERATION_AVAIALABLE
    *
    * @param operationIndex
    * @return boolean
    * @roseuid 3CDB5C9800C1
    */
   public boolean isOperationAvailableToOwner(int operationIndex)
   {
	byte byteValue = this.businessObjectOperations[operationIndex];
	if(byteValue == BusinessObjOperationUtil.OPERATION_UNAVAILABLE)
		     return false;
	else if(byteValue == BusinessObjOperationUtil.OPERATION_AVAILABLE
	        || byteValue == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
		      return true;
	else
	    return false;
   }

   /**
    * Returns true if the business object operation stored at the given index
    * contains the value OPERATION_GUEST
    *
    * @param operationIndex
    * @return boolean
    * @roseuid 3CDB5EF803B4
    */
   public boolean isOperationAvailableToGuest(int operationIndex)
   {
        byte byteValue = this.businessObjectOperations[operationIndex];
	if(byteValue == BusinessObjOperationUtil.OPERATION_UNAVAILABLE)
		     return false;
	else if(byteValue == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL
	        || byteValue == BusinessObjOperationUtil.OPERATION_GUEST)
		      return true;
	else
	    return false;
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
}
