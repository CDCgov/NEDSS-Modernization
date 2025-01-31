//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\updatehandler\\ActionCompositeImpl.java

package gov.cdc.nedss.systemservice.mprupdateengine;

import java.util.*;
import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.systemservice.mprupdateengine.MPRUpdateObjectStructure;

/**
 *
 * <p>Title: ActionCompositeImpl</p>
 * <p>Description: This class implements the ActionComposite interface.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author not attributable
 * @version 1.0
 */
class ActionCompositeImpl implements ActionComposite
{
   List<Object> children = new ArrayList<Object> ();

   /**
    * This is the default constructor.
    */
   public ActionCompositeImpl()
   {

   }

   /**
   The Action composite calls the perform() method on each of its children.
   @param object
   @return boolean
   @throws
   nedss.NEDSSBusinessServicesLayer.CDM.MPRUpdateEngine.MPRUpdateEngineDesign.excep
   tion.MPRUpdateException
    */
   public boolean perform(MPRUpdateObjectStructure object) throws MPRUpdateException
   {
		for (int i = 0; i < children.size(); i++) {
			Action action = (Action) children.get(i);
			if (!action.perform(object)) {
				// only go on when it is not false
				return false;
			}

		}
		return true;
   }

   /**
   The Action composite adds the Action object passed in the parameter to its
   children collection.
   @param action
    */
   public void add(Action action)
   {
		children.add(action);
   }
}
