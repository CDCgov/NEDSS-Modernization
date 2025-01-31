//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbscontext\\helpers\\NBSContextSystemInfo.java

package gov.cdc.nedss.systemservice.nbscontext;
import gov.cdc.nedss.systemservice.nbscontext.*;
import java.io.*;

public class NBSContextSystemInfo implements Serializable
{

   /**
    * The name of the current page.
    */
   private String currentPageName = "NONE";

   /**
    * The ID of the current page.
    */
   private String currentPageID = NBSConstantUtil.GLOBAL_CONTEXT;

   /**
    * The name of the previous page.
    */
   private String prevPageName = "NONE";

   /**
    * The ID of the previous page.
    */
   private String prevPageID = "NONE";

   /**
    * The name of the current task.
    */
   private String currentTaskName = "NONE";

   /**
    * The name of the previous task.
    */
   private String prevTaskName = "NONE";

   /**
    * The action that caused the transition from the previous page to the new page.
    */
   private String lastAction = "NONE";

   /**
    * @roseuid 3CEA51F601C4
    */
   public NBSContextSystemInfo()
   {

   }

   /**
    * Access method for the currentPageName property.
    *
    * @return   the current value of the currentPageName property
    */
   public String getCurrentPageName()
   {
      return currentPageName;
   }

   /**
    * Sets the value of the currentPageName property.
    *
    * @param aCurrentPageName the new value of the currentPageName property
    */
   public void setCurrentPageName(String aCurrentPageName)
   {
      currentPageName = aCurrentPageName;
   }

   /**
    * Access method for the currentPageID property.
    *
    * @return   the current value of the currentPageID property
    */
   public String getCurrentPageID()
   {
      return currentPageID;
   }

   /**
    * Sets the value of the currentPageID property.
    *
    * @param aCurrentPageID the new value of the currentPageID property
    */
   public void setCurrentPageID(String aCurrentPageID)
   {
      currentPageID = aCurrentPageID;
   }

   /**
    * Access method for the prevPageName property.
    *
    * @return   the current value of the prevPageName property
    */
   public String getPrevPageName()
   {
      return prevPageName;
   }

   /**
    * Sets the value of the prevPageName property.
    *
    * @param aPrevPageName the new value of the prevPageName property
    */
   public void setPrevPageName(String aPrevPageName)
   {
      prevPageName = aPrevPageName;
   }

   /**
    * Access method for the prevPageID property.
    *
    * @return   the current value of the prevPageID property
    */
   public String getPrevPageID()
   {
      return prevPageID;
   }

   /**
    * Sets the value of the prevPageID property.
    *
    * @param aPrevPageID the new value of the prevPageID property
    */
   public void setPrevPageID(String aPrevPageID)
   {
      prevPageID = aPrevPageID;
   }

   /**
    * Access method for the currentTaskName property.
    *
    * @return   the current value of the currentTaskName property
    */
   public String getCurrentTaskName()
   {
      return currentTaskName;
   }

   /**
    * Sets the value of the currentTaskName property.
    *
    * @param aCurrentTaskName the new value of the currentTaskName property
    */
   public void setCurrentTaskName(String aCurrentTaskName)
   {
      currentTaskName = aCurrentTaskName;
   }

   /**
    * Access method for the prevTaskName property.
    *
    * @return   the current value of the prevTaskName property
    */
   public String getPrevTaskName()
   {
      return prevTaskName;
   }

   /**
    * Sets the value of the prevTaskName property.
    *
    * @param aPrevTaskName the new value of the prevTaskName property
    */
   public void setPrevTaskName(String aPrevTaskName)
   {
      prevTaskName = aPrevTaskName;
   }

   /**
    * Access method for the lastAction property.
    *
    * @return   the current value of the lastAction property
    */
   public String getLastAction()
   {
      return lastAction;
   }

   /**
    * Sets the value of the lastAction property.
    *
    * @param aLastAction the new value of the lastAction property
    */
   public void setLastAction(String aLastAction)
   {
      lastAction = aLastAction;
   }
}
