//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbscontext\\helpers\\NBSPage.java

package gov.cdc.nedss.systemservice.nbscontext;

import java.util.TreeMap;

public class NBSPage
{

   /**
    * The ID assigned to the page.
    */
   private String pageID;

   /**
    * The name of the page.
    */
   private String pageName;

   /**
    * This represents the default behavior for the page.  This behavior can be
    * overridden by a Task.  In that case, the action specified for an individual
    * Page Element in the Task Override replaces the default action and the entire
    * preserveObj list is replaced.
    */
   public NBSPageContext theDefaultPageContext;

   /**
    * This is a a TreeMap<Object,Object> of Task Overrides.  The Key is the TaskName (String) and
    * the Value is a PageContext object detailing the overrides.
    */
   public TreeMap<Object,Object> theTaskOverridesTreeMap;

   /**
    * @roseuid 3CEA520002CC
    */
   public NBSPage()
   {

   }

   /**
    * Access method for the pageID property.
    *
    * @return   the current value of the pageID property
    */
   public String getPageID()
   {
      return pageID;
   }

   /**
    * Sets the value of the pageID property.
    *
    * @param aPageID the new value of the pageID property
    */
   public void setPageID(String aPageID)
   {
      pageID = aPageID;
   }

   /**
    * Access method for the pageName property.
    *
    * @return   the current value of the pageName property
    */
   public String getPageName()
   {
      return pageName;
   }

   /**
    * Sets the value of the pageName property.
    *
    * @param aPageName the new value of the pageName property
    */
   public void setPageName(String aPageName)
   {
      pageName = aPageName;
   }

   /**
    * Access method for the theDefaultPageContext property.
    *
    * @return   the current value of the theDefaultPageContext property
    */
   public NBSPageContext getTheDefaultPageContext()
   {
      return theDefaultPageContext;
   }

   /**
    * Sets the value of the theDefaultPageContext property.
    *
    * @param aTheDefaultPageContext the new value of the theDefaultPageContext property
    */
   public void setTheDefaultPageContext(NBSPageContext aTheDefaultPageContext)
   {
      theDefaultPageContext = aTheDefaultPageContext;
   }
}
