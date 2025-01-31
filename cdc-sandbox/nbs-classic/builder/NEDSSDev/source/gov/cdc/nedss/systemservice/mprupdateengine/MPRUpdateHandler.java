package gov.cdc.nedss.systemservice.mprupdateengine;

import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.systemservice.mprupdateengine.MPRUpdateObjectStructure;
/**
 * <p>Title: MPRUpdateHandler</p>
 * <p>Description: This interface specifies an MPR update handler object.
 * An MPR update handler object implements the update business rules. </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender
 * @version 1.0
 */

public interface MPRUpdateHandler
{


   public boolean process(MPRUpdateObjectStructure object) throws MPRUpdateException;

   /**
    * @param actions
    * @roseuid 3E49160E00DA
    */
   public void init(ActionComposite actions);
}
