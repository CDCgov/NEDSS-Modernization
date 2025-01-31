package gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean;

import javax.ejb.*;

import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.systemservice.mprupdateengine.MPRUpdateHandler;
import gov.cdc.nedss.systemservice.mprupdateengine.MPRUpdateHandlerFactory;
import gov.cdc.nedss.systemservice.mprupdateengine.MPRUpdateVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * <p>Title: MPRUpdateEngineEJB</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MPRUpdateEngineEJB implements SessionBean, MPRUpdateBusinessInterface
{
    private static final LogUtils logger = new LogUtils((MPRUpdateEngineEJB.class).getName());
    private SessionContext context = null;
    /**
     * @J2EE_METHOD  --  MPRUpdateEngineEJB
     */
    public MPRUpdateEngineEJB    ()
    {

    }

    /**
     * @J2EE_METHOD  --  ejbCreate
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate    ()
    {

    }

    /**
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove    ()
    {

    }

    /**
     * @J2EE_METHOD  --  ejbActivate
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate    ()
    {

    }

    /**
     * @J2EE_METHOD  --  ejbPassivate
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate    ()
    {

    }

    /**
     * @J2EE_METHOD  --  setSessionContext
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext    (SessionContext sc)
    {
		this.context = sc;
    }


    //**********************************************************************/
    //BEGIN: updateWithRevision(PersonVO newRevision, NBSSecurityObj secObj)
    //**********************************************************************/

    /**
     *  @J2EE_METHOD  --  updateWithRevision
     * @param newRevision
     * @param secObj
     * @return boolean
     * @throws RemoteException
     * @throws MPRUpdateException
     */
    public boolean updateWithRevision(PersonVO newRevision, NBSSecurityObj secObj) throws RemoteException, MPRUpdateException
    {
    	try {
			Long mprUID = null;
			if(newRevision == null)
			{
			  throw new MPRUpdateException("Please provide a not null person object, newRevision is: "+ newRevision);
			}
			else
			{
			  //Get the mpr uid
			  PersonDT personDT = newRevision.getThePersonDT();
			  if (personDT != null)
			  {
			    mprUID = personDT.getPersonParentUid();
			  }
			}

			if(mprUID == null)
			{
			  throw new MPRUpdateException("A person parent uid expected for this person uid: "+
			                               newRevision.getThePersonDT().getPersonUid());
			}

			//Retrieve a mpr with the mprUID
			PersonVO mpr = getMPR(mprUID, secObj);
			mpr.setMPRUpdateValid(newRevision.isMPRUpdateValid());

			logger.debug("mpr is: " + mpr);

			if(mpr != null) //With the MPR, update...
			{
			  //localId need to be same for MPR and Revision and it need to be set at backend
			  newRevision.getThePersonDT().setLocalId(mpr.getThePersonDT().getLocalId());
			  return update(mpr, newRevision, secObj);
			}
			else //No MPR.
			{
			      throw new MPRUpdateException("Cannot get a mpr for this person parent uid: "+
			                               mprUID);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    private PersonVO getMPR(Long personUid, NBSSecurityObj secObj) throws MPRUpdateException
    {
      NedssUtils nu = new NedssUtils();
      try
      {
        Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
        EntityControllerHome home = (EntityControllerHome)javax.rmi.PortableRemoteObject.narrow(obj,EntityControllerHome.class);
        EntityController eController = home.create();

        return eController.getMPR(personUid, secObj);
      }
      catch(RemoteException rex)
      {
        logger.fatal("RemoteException: cannot retrieve mpr."+rex.getMessage(), rex);
        throw new MPRUpdateException(rex.getMessage(), rex);
      }
      catch(CreateException cex)
      {
        logger.fatal("CreateException: cannot create an EntityController object."+cex.getMessage(),cex);
        throw new MPRUpdateException(cex.getMessage(), cex);
      }
    }

    //###################################################################/
    //END: updateWithRevision(PersonVO newRevision, NBSSecurityObj secObj)
    //###################################################################/


    //*******************************************************************************************/
    //BEGIN: updateWithMPR(PersonVO survivingMPR, Collection<Object> percededMPRs, NBSSecurityObj secObj)
    //*******************************************************************************************/

    /**
     * @J2EE_METHOD  --  updateWithMPRs
     * @param survivingMPR
     * @param percededMPRs
     * @param secObj
     * @return boolean
     * @throws MPRUpdateException
     * @throws RemoteException
     */
    public boolean updateWithMPRs(PersonVO survivingMPR, Collection<Object> percededMPRs, NBSSecurityObj secObj) throws MPRUpdateException, RemoteException
    {
		try {
			MPRUpdateVO mprUpdateVO = new MPRUpdateVO(survivingMPR, percededMPRs);

			MPRUpdateHandler handler = getHandler(MPRUpdateEngineConstants.DEFAULT_HANDLER);

			if(handler.process(mprUpdateVO))
			{
				return saveMPR(mprUpdateVO.getMpr(), secObj); //reuse
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }
    //#########################################################################################/
    //END: updateWithMPR(PersonVO survivingMPR, Collection<Object> percededMPRs, NBSSecurityObj secObj)
    //#########################################################################################/



    //*************************************************************************************/
    //BEGIN: updateWithRevision (PersonVO mpr, PersonVO newRevision, NBSSecurityObj secObj)
    //*************************************************************************************/
    /**
     * @J2EE_METHOD  --  updateWithRevision
     * @param mpr
     * @param newRevision
     * @param secObj
     * @return boolean
     * @throws RemoteException
     * @throws MPRUpdateException
     */
    public boolean updateWithRevision (PersonVO mpr, PersonVO newRevision, NBSSecurityObj secObj) throws RemoteException, MPRUpdateException
    {
      return update(mpr, newRevision, secObj);
    }

    //Updates the mpr, based on the new revision, using the default handler
    private boolean update(PersonVO mpr, PersonVO newRevision, NBSSecurityObj secObj)
    	throws MPRUpdateException
    {
    	try {
			logger.debug("Starts update mpr, person uid = " + mpr.getThePersonDT().getPersonUid());
			logger.debug("Starts update mpr, person parent uid = " + mpr.getThePersonDT().getPersonParentUid());
			Collection<Object> aNewRevisionList = new ArrayList<Object> ();
			aNewRevisionList.add(newRevision);

			MPRUpdateVO mprUpdateVO = new MPRUpdateVO(mpr, aNewRevisionList);
			logger.debug("Try to get a " + MPRUpdateEngineConstants.DEFAULT_HANDLER + " handler.");
			MPRUpdateHandler handler = getHandler(MPRUpdateEngineConstants.DEFAULT_HANDLER);
			logger.debug("The handler is: " + handler);
			if(handler.process(mprUpdateVO))
			{
				return saveMPR(mpr, secObj);
			}else
			{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    //Creates a handler on the basis of the type code supplied
    private MPRUpdateHandler getHandler(String key)
    {
    	try {
			MPRUpdateHandlerFactory factory = MPRUpdateHandlerFactory.getInstance();
			return factory.getHandler(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /*Does final preparation of the updated mpr before persistence into database
    by appending a business trigger cd */
    private boolean saveMPR(PersonVO mpr, NBSSecurityObj secObj) throws MPRUpdateException
    {
    	//try
    	//{
	    	/*PersonDT personDT = mpr.getThePersonDT();
	    	logger.debug("personDT isItNew:" + personDT.isItNew()+ ":personDT.IsItDirty:" +personDT.isItDirty() );
	    	if(!personDT.isItDirty()) personDT.setItDirty(true);

	    	PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
	    	personDT = (PersonDT)prepareVOUtils.prepareVO(personDT,
	    												NBSBOLookup.PATIENT,
	    												NEDSSConstants.PER_EDIT, //new code: "PAT_EDIT" as design doc
	    												DataTables.PERSON_TABLE,
	    												NEDSSConstants.BASE,
	    												secObj);
	    	mpr.setThePersonDT(personDT);*/
	    	return storeMPR(mpr, NEDSSConstants.PAT_EDIT, secObj);
    	//}
    	/*catch(NEDSSConcurrentDataException ncex)
    	{
    		logger.fatal("Error: Data concurrency problem while preparing personvo(mpr).");
    		throw new MPRUpdateException("Error: Data concurrency problem while preparing personvo(mpr).");
    	}*/
    }

    //Persists the updated mpr into database
    private boolean storeMPR(PersonVO mpr, String businessTriggerCd, NBSSecurityObj secObj) throws MPRUpdateException
    {
    	NedssUtils nu = new NedssUtils();
		try
		{
		    Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
		    EntityControllerHome home = (EntityControllerHome)javax.rmi.PortableRemoteObject.narrow(obj,EntityControllerHome.class);
		    EntityController eController = home.create();

		    if(eController.setMPR(mpr, businessTriggerCd, secObj) != null)
		    {
		    	return true;
		    }
		    else
		    {
		    	return false;
		    }
		}
		catch(RemoteException rex)
		{
			logger.fatal("RemoteException: cannot persist mpr."+rex.getMessage(), rex);
			throw new MPRUpdateException(rex.getMessage(),rex);
		}
		catch(CreateException cex)
		{
			logger.fatal("CreateException: cannot create an EntityController object."+cex.getMessage(),cex);
			throw new MPRUpdateException(cex.getMessage(), cex);
		}
		catch(NEDSSConcurrentDataException ncex)
    	{
    		logger.fatal("NEDSSConcurrentDataException: Data concurrency problem while persisting personvo(mpr)."+ncex.getMessage(),ncex);
            //throw new MPRUpdateException("Error: Data concurrency problem while persisting personvo(mpr).");
            // To Mangle is ok, I would do it in a useful way... CD 07/16/2003.
            throw new MPRUpdateException(ncex.toString(), ncex);
    	}
    }
    //#####################################################################################/
    //END: updateWithRevision (PersonVO mpr, PersonVO newRevision, NBSSecurityObj secObj)
    //#####################################################################################/
}