//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\nnd\\vo\\NNDMessageHeaderVO.java

package gov.cdc.nedss.nnd.vo;

import gov.cdc.nedss.messageframework.notificationmastermessage.MessageHeader;
import gov.cdc.nedss.util.AbstractVO;

public class NNDMessageHeaderVO extends AbstractVO
{
   public Long publicHealthCaseUId;
   public String publicHealthCaseLocalId;
   public MessageHeader castorMessageHeader;

   /**
    * @roseuid 3EDF5661038F
    */
   public NNDMessageHeaderVO()
   {

   }

   /**
    * Access method for the publicHealthCaseUId property.
    *
    * @return   the current value of the publicHealthCaseUId property
    */
   public Long getPublicHealthCaseUId()
   {
      return publicHealthCaseUId;
   }

   /**
    * Sets the value of the publicHealthCaseUId property.
    *
    * @param aPublicHealthCaseUId the new value of the publicHealthCaseUId property
    */
   public void setPublicHealthCaseUId(Long aPublicHealthCaseUId)
   {
      publicHealthCaseUId = aPublicHealthCaseUId;
   }

   /**
    * Access method for the publicHealthCaseLocalId property.
    *
    * @return   the current value of the publicHealthCaseLocalId property
    */
   public String getPublicHealthCaseLocalId()
   {
      return publicHealthCaseLocalId;
   }

   /**
    * Sets the value of the publicHealthCaseLocalId property.
    *
    * @param aPublicHealthCaseLocalId the new value of the publicHealthCaseLocalId property
    */
   public void setPublicHealthCaseLocalId(String aPublicHealthCaseLocalId)
   {
      publicHealthCaseLocalId = aPublicHealthCaseLocalId;
   }

   /**
    * Access method for the castorMessageHeader property.
    * @return   the current value of the castorMessageHeader property
    * @roseuid 3EDF58880319
    */
   public MessageHeader getCastorMessageHeader()
   {
    return castorMessageHeader;
   }

   /**
    * Sets the value of the castorMessageHeader property.
    * @param aCastorMessageHeader the new value of the castorMessageHeader property
    * @roseuid 3EDF58880355
    */
   public void setCastorMessageHeader(MessageHeader aCastorMessageHeader)
   {
     this.castorMessageHeader = aCastorMessageHeader;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3EE2523D03BA
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3EE2523E0022
    */
   public void setItDirty(boolean itDirty)
   {
     this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3EE2523E0054
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3EE2523E005E
    */
   public void setItNew(boolean itNew)
   {
     this.itNew = itNew;

   }

   /**
    * @return boolean
    * @roseuid 3EE2523E0072
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3EE2523E0073
    */
   public void setItDelete(boolean itDelete)
   {
     this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3EE2523E0086
    */
   public boolean isItDelete()
   {
    return itDelete;
   }
}
