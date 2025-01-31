//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\helpers\\MsgOutLocalUIDGeneratorDT.java

package gov.cdc.nedss.nnd.helper;

import  gov.cdc.nedss.util.*;

public class MsgOutLocalUIDGeneratorDT extends AbstractVO
{
   private String classNameCd;
   private Long seedValueNbr;
   private String typeCd;
   private String UIDPrefixCd;
   private String UIDSuffixCd;

   /**
    * @roseuid 3D62571001B5
    */
   public MsgOutLocalUIDGeneratorDT()
   {

   }

   /**
    * Access method for the classNameCd property.
    *
    * @return   the current value of the classNameCd property
    */
   public String getClassNameCd()
   {
      return classNameCd;
   }

   /**
    * Sets the value of the classNameCd property.
    *
    * @param aClassNameCd the new value of the classNameCd property
    */
   public void setClassNameCd(String aClassNameCd)
   {
      classNameCd = aClassNameCd;
   }

   /**
    * Access method for the seedValueNbr property.
    *
    * @return   the current value of the seedValueNbr property
    */
   public Long getSeedValueNbr()
   {
      return seedValueNbr;
   }

   /**
    * Sets the value of the seedValueNbr property.
    *
    * @param aSeedValueNbr the new value of the seedValueNbr property
    */
   public void setSeedValueNbr(Long aSeedValueNbr)
   {
      seedValueNbr = aSeedValueNbr;
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
      typeCd = aTypeCd;
   }

   /**
    * Access method for the UIDPrefixCd property.
    *
    * @return   the current value of the UIDPrefixCd property
    */
   public String getUIDPrefixCd()
   {
      return UIDPrefixCd;
   }

   /**
    * Sets the value of the UIDPrefixCd property.
    *
    * @param aUIDPrefixCd the new value of the UIDPrefixCd property
    */
   public void setUIDPrefixCd(String aUIDPrefixCd)
   {
      UIDPrefixCd = aUIDPrefixCd;
   }

   /**
    * Access method for the UIDSuffixCd property.
    *
    * @return   the current value of the UIDSuffixCd property
    */
   public String getUIDSuffixCd()
   {
      return UIDSuffixCd;
   }

   /**
    * Sets the value of the UIDSuffixCd property.
    *
    * @param aUIDSuffixCd the new value of the UIDSuffixCd property
    */
   public void setUIDSuffixCd(String aUIDSuffixCd)
   {
      UIDSuffixCd = aUIDSuffixCd;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D62571001C5
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D62571001D4
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571001D6
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D62571001D7
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571001E5
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D62571001E6
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571001F5
    */
   public boolean isItDelete()
   {
    return true;
   }
}
