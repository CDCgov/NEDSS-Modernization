//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbscontext\\helpers\\NBSPageContext.java

package gov.cdc.nedss.systemservice.nbscontext;

import java.util.TreeMap;
import java.util.Collection;

public class NBSPageContext
{

   /**
    * A TreeMap<Object,Object> of page elements.  The Key is the name of the page element and the
    * Value is a String representing the action that should be taken for that page
    * element.  This could be a Struts Forward Action or a Web Processor Command or a
    * special value such as NOT_DISPLAYED which indicates that the page element
    * should not be rendered.
    */
   private TreeMap<Object,Object> pageElements;

   /**
    * A collection of Strings representing the names of objects that should be
    * preserved in the object store.  All others can be removed.
    */
   private Collection<Object>  preserveObjs;

   /**
    * This is a TreeMap<Object,Object> of the  Tasks that are started by an action on this page.
    * The Action is the Key and the TaskName is the Value.
    */
   private TreeMap<Object,Object> taskStarts;

   /**
    * This is a TreeMap<Object,Object> of the Tasks that End because of an action on this page.  The
    * Action is the Key and the TaskName is the Value.
    */
   private TreeMap<Object,Object> taskEnds;

   /**
    * @roseuid 3CEA523F01AB
    */
   public NBSPageContext()
   {

   }

   /**
    * Access method for the pageElements property.
    *
    * @return   the current value of the pageElements property
    */
   public TreeMap<Object,Object> getPageElements()
   {
      return pageElements;
   }

   /**
    * Sets the value of the pageElements property.
    *
    * @param aPageElements the new value of the pageElements property
    */
   public void setPageElements(TreeMap<Object,Object> aPageElements)
   {
      pageElements = aPageElements;
   }

   /**
    * Access method for the preserveObjs property.
    *
    * @return   the current value of the preserveObjs property
    */
   public Collection<Object>  getPreserveObjs()
   {
      return preserveObjs;
   }

   /**
    * Sets the value of the preserveObjs property.
    *
    * @param aPreserveObjs the new value of the preserveObjs property
    */
   public void setPreserveObjs(Collection<Object> aPreserveObjs)
   {
      preserveObjs = aPreserveObjs;
   }

   /**
    * Access method for the taskStarts property.
    *
    * @return   the current value of the taskStarts property
    */
   public TreeMap<Object,Object> getTaskStarts()
   {
      return taskStarts;
   }

   /**
    * Sets the value of the taskStarts property.
    *
    * @param aTaskStarts the new value of the taskStarts property
    */
   public void setTaskStarts(TreeMap<Object,Object> aTaskStarts)
   {
      taskStarts = aTaskStarts;
   }

   /**
    * Access method for the taskEnds property.
    *
    * @return   the current value of the taskEnds property
    */
   public TreeMap<Object,Object> getTaskEnds()
   {
      return taskEnds;
   }

   /**
    * Sets the value of the taskEnds property.
    *
    * @param aTaskEnds the new value of the taskEnds property
    */
   public void setTaskEnds(TreeMap<Object,Object> aTaskEnds)
   {
      taskEnds = aTaskEnds;
   }
}
