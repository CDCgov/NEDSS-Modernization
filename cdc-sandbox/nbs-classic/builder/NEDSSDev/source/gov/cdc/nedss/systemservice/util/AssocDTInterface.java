//Source file: C:\\CDC\\gov\\gov\\cdc\\nedss\\helpers\\AssocDTInterface.java
/**
* Name:		AssocDTInterface.java
* Description:	This is an interface to implement on certain DT classes
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.systemservice.util;

import java.sql.Timestamp;

public interface AssocDTInterface
{

   /**
    * @return Long
    * @roseuid 3CD1BDBA028F
    */
   public Long getLastChgUserId();

   /**
    * @param aLastChgUserId
    * @roseuid 3CD1BDBA0311
    */
   public void setLastChgUserId(Long aLastChgUserId);

   /**
    * @return java.sql.Timestamp
    * @roseuid 3CD1BDBA03D9
    */
   public Timestamp getLastChgTime();

   /**
    * @param aLastChgTime
    * @roseuid 3CD1BDBB00E2
    */
   public void setLastChgTime(java.sql.Timestamp aLastChgTime);

   /**
    * @return Long
    * @roseuid 3CD1BDBB020E
    */
   public Long getAddUserId();

   /**
    * @param aAddUserId
    * @roseuid 3CD1BDBB02AE
    */
   public void setAddUserId(Long aAddUserId);

   /**
    * @return java.sql.Timestamp
    * @roseuid 3CD1BDBB0359
    */
   public Timestamp getAddTime();

   /**
    * @param aAddTime
    * @roseuid 3CD1BDBC002F
    */
   public void setAddTime(java.sql.Timestamp aAddTime);

   /**
    * @return String
    * @roseuid 3CD1BDBC0129
    */
   public String getLastChgReasonCd();

   /**
    * @param aLastChgReasonCd
    * @roseuid 3CD1BDBC01A1
    */
   public void setLastChgReasonCd(String aLastChgReasonCd);

   /**
    * @return String
    * @roseuid 3CD1BDBC026A
    */
   public String getRecordStatusCd();

   /**
    * @param aRecordStatusCd
    * @roseuid 3CD1BDBC02B0
    */
   public void setRecordStatusCd(String aRecordStatusCd);

   /**
    * @return java.sql.Timestamp
    * @roseuid 3CD1BDBC0346
    */
   public Timestamp getRecordStatusTime();

   /**
    * @param aRecordStatusTime
    * @roseuid 3CD1BDBD0026
    */
   public void setRecordStatusTime(java.sql.Timestamp aRecordStatusTime);

   /**
    * @return String
    * @roseuid 3CD1BDBD0103
    */
   public String getStatusCd();

   /**
    * @param aStatusCd
    * @roseuid 3CD1BDBD0171
    */
   public void setStatusCd(String aStatusCd);

   /**
    * @return java.sql.Timestamp
    * @roseuid 3CD1BDBD02B1
    */
   public Timestamp getStatusTime();

   /**
    * @param aStatusTime
    * @roseuid 3CD1BDBD035C
    */
   public void setStatusTime(java.sql.Timestamp aStatusTime);

   /**
    * @return boolean
    * @roseuid 3CD1BDBE0032
    */
   public boolean isItNew();

   /**
    * @param itNew
    * @roseuid 3CD1BDBE003C
    */
   public void setItNew(boolean itNew);

   /**
    * @return boolean
    * @roseuid 3CD1BDBE0096
    */
   public boolean isItDirty();

   /**
    * @param itDirty
    * @roseuid 3CD1BDBE00E6
    */
   public void setItDirty(boolean itDirty);

   /**
    * @return boolean
    * @roseuid 3CD1BDBE012D
    */
   public boolean isItDelete();

   /**
    * @param itDelete
    * @roseuid 3CD1BDBE0168
    */
   public void setItDelete(boolean itDelete);
}
