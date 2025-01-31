package gov.cdc.nedss.systemservice.mprupdateengine;

import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.systemservice.mprupdateengine.MPRUpdateObjectStructure;
import gov.cdc.nedss.systemservice.util.MPRUpdateEnigneUtil;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import java.util.*;

/**
 * <p>Title: MPRUpdateHandlerDefaultImpl</p>
 * <p>Description: This is the default implementation class for the
 * MPRUpdateHandler interface.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender
 * @version 1.0
 */

class MPRUpdateHandlerDefaultImpl implements MPRUpdateHandler
{
   private ActionComposite actions = null;

   /**
    * @roseuid 3E47DCEB03B9
    */
   public MPRUpdateHandlerDefaultImpl()
   {

   }

   /**
    * This method initializes the MPR update handler with an Action composite.
    * The Action composite contains all the actions available to the update handler.
    * @param actions
    * @return void
    * @roseuid 3E49146000BB
    */
   public void init(ActionComposite actions)
   {
   		// Separate this method out to easily support needs to use
		// reflection to create instances, so that we can make the
		// handler factory to hold multiple handlers
		this.actions = actions;
   }

   /**
    * The MPR update handler object executes the business rules on the
    * object passed in the parameter.
    * @param updateVO
    * @return boolean
    * @throws gov.cdc.nedss.systemservice.mprupdateengine.exception.MPRUpdateException
    * @roseuid 3E4914600148
    */
   public boolean process(MPRUpdateObjectStructure object) throws MPRUpdateException
   {

          boolean performFlag = actions.perform(object);
          if (performFlag)
          {
            MPRUpdateVO mprUpdateVO = (MPRUpdateVO) object;
            PersonVO mpr = mprUpdateVO.getMpr();
            Collection<Object>  col = mpr.getTheEntityLocatorParticipationDTCollection();
            if(col!=null && col.size()>0)
            {
              Iterator<Object>  ite = col.iterator();
               while (ite.hasNext())
               {
                  EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) ite.next();
                  if((entityLocatorParticipationDT.getThePhysicalLocatorDT()!=null && entityLocatorParticipationDT.getThePhysicalLocatorDT().isItDirty())||
                     (entityLocatorParticipationDT.getTheTeleLocatorDT()!=null && entityLocatorParticipationDT.getTheTeleLocatorDT().isItDirty())||
                     (entityLocatorParticipationDT.getThePostalLocatorDT()!=null && entityLocatorParticipationDT.getThePostalLocatorDT().isItDirty()))
                     entityLocatorParticipationDT.setItDirty(true);
                }
            }
            mpr.setItDelete(false);
            mpr.setItNew(false);
            mpr.setItDirty(true);
            mpr.getThePersonDT().setItDirty(true);
          }
          return true;
   }

}
