package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean;
/**
 * <p>Title: SRTCacheManagerEJB</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

import javax.ejb.*;

import java.rmi.*;
import java.util.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.*;
import gov.cdc.nedss.systemservice.exception.InvalidSRTFilterKeysException;
import gov.cdc.nedss.systemservice.exception.SRTCacheManagerException;


public class SRTCacheManagerEJB implements javax.ejb.SessionBean, SRTCacheManagerBusinessInterface {



  static final LogUtils logger = new LogUtils (SRTCacheManagerEJB.class.getName());

  public SRTCacheManagerEJB    ()
  {

  }




  /**
   * A container invokes this method before it ends the life of the session object. This
   * happens as a result of a client's invoking a remove operation, or when a container
   * decides to terminate the session object after a timeout. This method is called with
   * no transaction context.
   */
  public void ejbRemove    ()
  {

  }

  /**
   * The activate method is called when the instance is activated from its 'passive' state.
   * The instance should acquire any resource that it has released earlier in the ejbPassivate()
   * method. This method is called with no transaction context.
   */
  public void ejbActivate    ()
  {

  }

  /**
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
   * Called by the container to create a session bean instance. Its parameters typically
   * contain the information the client uses to customize the bean instance for its use.
   * It requires a matching pair in the bean class and its home interface.
   */
  public void ejbCreate    ()
  {

  }

  /**
   *
   * @param sessioncontext EJB specific SessionContext object
   * @throws EJBException
   * @throws RemoteException
   * @throws NEDSSSystemException
   */

  public void setSessionContext    (SessionContext sessioncontext) throws EJBException,RemoteException, NEDSSSystemException
  {

  }



  /**
   * Deprecated
   * @param labCLIAid
   * @param programAreaCD
   * @return
   * @throws RemoteException
   * @throws EJBException
   */
  public Collection<Object>  getOrderedTestNames(String labCLIAid, String programAreaCD) throws
      RemoteException, EJBException {
    try {
		Collection<Object>  coll = null;
		coll = SRTCache.getOrderedTestNames(labCLIAid, programAreaCD);
		return coll;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

  }
  /**
   * Deprecated
   * @param labCLIAid
   * @return
   * @throws RemoteException
   * @throws EJBException
   */
    public Collection<Object>  getOrderedTestNames(String labCLIAid) throws RemoteException,
        EJBException {
      try {
		Collection<Object>  coll = null;
		  coll = SRTCache.getOrderedTestNames(labCLIAid);
		  return coll;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

    }
    /**
     * Deprecated
     * @param labCLIAid
     * @param programAreaCD
     * @return
     * @throws RemoteException
     * @throws EJBException
     */
    public Collection<Object>  getResultedTestNames(String labCLIAid, String programAreaCD) throws
        RemoteException, EJBException {
      try {
		Collection<Object>  coll = null;
		  coll = SRTCache.getResultedTestNames(labCLIAid, programAreaCD);
		  return coll;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

    }
    /**
     * Deprecated
     * @param labCLIAid
     * @return
     * @throws RemoteException
     * @throws EJBException
     */
    public Collection<Object>  getResultedTestNames(String labCLIAid) throws
        RemoteException,
        EJBException {
      try {
		Collection<Object>  coll = null;
		  coll = SRTCache.getResultedTestNames(labCLIAid);
		  return coll;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

    }


    //New methods for SRT Filtering
    /**
     * Initializes the SRT Cache upon startup
     */
    public void initializeSRTCache() throws RemoteException
    {
    	try{
    		SRTCacheOrderedTestHelper srtCacheOrderedTestHelper = SRTCacheOrderedTestHelper.getInstance();
    	    SRTCacheResultedTestHelper srtCacheResultedTestHelper =  SRTCacheResultedTestHelper.getInstance();
    	       logger.debug("initializeSRTCache  The  ResultedCache and ordered Test cache is Loaded");
    	      	
    	 }catch(Exception ex){
    		logger.fatal("Exception thrown in initializeSRTCache method with ex: "+ ex.getMessage(),ex);
    		throw new RemoteException(ex.getMessage(),ex);
    	 }
    		
    	
      /*
      SRTCacheAnatomicSiteHelper srtCacheAnatomicSiteHelper = SRTCacheAnatomicSiteHelper.getInstance();
      SRTCacheSpecimenSourceHelper srtCacheSpecimenSourceHelper = SRTCacheSpecimenSourceHelper.getInstance();
      SRTCacheUnitsHelper srtCacheUnitsHelper = SRTCacheUnitsHelper.getInstance();
      SRTCacheLabResultHelper srtCacheLabResultHelper =  SRTCacheLabResultHelper.getInstance();
      SRTCacheDisplayCodeHelper srtCacheDisplayCodeHelper = SRTCacheDisplayCodeHelper.getInstance();
      SRTCacheTreatmentHelper srtCacheTreatmentHelper =  SRTCacheTreatmentHelper.getInstance();
      SRTCacheReportingSourceHelper srtCacheReportingSourceHelper = SRTCacheReportingSourceHelper.getInstance();
*/
    }
    /**
     * getOrderedTests returns a collection of ordered test based on the filtering
     * criteria. SRTFilterKeys defines the keys for filter. getOrderedTests currently accepts
     * the following combinations
     * 1. SRTFilterKeys.REPORTING_FACILITY_ID
     *    SRTFilterKeys.PROGRAM_AREA_CODE
     * 2. SRTFilterKeys.REPORTING_FACILITY_ID
     *    SRTFilterKeys.CONDITION_CODE
     * 3. SRTFilterKeys.REPORTING_FACILITY_ID
     *    SRTFilterKeys.ORDERED_TEST_CODE
     * 4. SRTFilterKeys.REPORTING_FACILITY_ID
     * 5. HashMap<Object,Object> of size zero will return all the ordered tests
     * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
     * Returns: If the combination is right, returnset will contain whole collection or filtered if the
     * filter value is found in the cache
     *
     * @param HashMap<Object,Object> filter
     * @return
     * @throws RemoteException
     * @throws SRTCacheManagerException
     */
    public Collection<Object>  getOrderedTests(Map<String,String> filter) throws
      RemoteException, SRTCacheManagerException {
    try{
    	
    if(!SRTCacheOrderedTestHelper.validateFilterKeys(filter))
      throw new InvalidSRTFilterKeysException("getOrderedTests: Invalid filters. Validation failed");

    SRTCacheOrderedTestHelper helper = SRTCacheOrderedTestHelper.getInstance();
    Collection<Object>  coll = helper.getOrderedTestNames(filter);
    return coll;
    }
    catch(Exception ex){
    	logger.fatal("Exception thrown in getOrderedTests method with ex: "+ ex.getMessage(),ex);
    	throw new RemoteException(ex.getMessage(),ex);
    }

  }
  /**
   * getResultedTests returns a collection of resulted tests based on the filtering
   * criteria. SRTFilterKeys defines the keys for filter. getResultedTests currently accepts
   * the following combinations
   * 1. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.PROGRAM_AREA_CODE
   * 2. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.CONDITION_CODE
   * 3. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.ORDERED_TEST_CODE
   * 4. SRTFilterKeys.REPORTING_FACILITY_ID
   * 5. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.RESULTED_TEST_CODE
   * 6. HashMap<Object,Object> of size zero will return all the resulted tests
   * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
   * Returns: If the combination is right, returnset will contain whole collection or filtered
   * if the filter value is found in the cache
   * @param filter
   * @return
   * @throws RemoteException
   * @throws SRTCacheManagerException
   */
  public Collection<Object>  getResultedTests(Map filter) throws
    RemoteException, SRTCacheManagerException{
    try{
  	if(!SRTCacheResultedTestHelper.validateKeys(filter))
      throw new InvalidSRTFilterKeysException("getResultedTests: Invalid filters. Validation failed");
    SRTCacheResultedTestHelper helper =  SRTCacheResultedTestHelper.getInstance();
    Collection<Object>  coll = helper.getResultedTests(filter,false);
    return coll;
    }
    catch(Exception ex){
    	logger.fatal("Exception thrown in getResultedTests method with ex: "+ ex.getMessage(),ex);
    	throw new RemoteException(ex.getMessage(), ex);
    }
  }

  /**
 * getResultedTests returns a collection of resulted tests based on the filtering
 * criteria. SRTFilterKeys defines the keys for filter. getResultedTests currently accepts
 * the following combinations
 * 1. SRTFilterKeys.REPORTING_FACILITY_ID
 *    SRTFilterKeys.PROGRAM_AREA_CODE
 * 2. SRTFilterKeys.REPORTING_FACILITY_ID
 *    SRTFilterKeys.CONDITION_CODE
 * 3. SRTFilterKeys.REPORTING_FACILITY_ID
 *    SRTFilterKeys.ORDERED_TEST_CODE
 * 4. SRTFilterKeys.REPORTING_FACILITY_ID
 * 5. SRTFilterKeys.REPORTING_FACILITY_ID
 *    SRTFilterKeys.RESULTED_TEST_CODE
 * 6. HashMap<Object,Object> of size zero will return all the resulted tests
 * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
 * Returns: If the combination is right, returnset will contain whole collection or filtered
 * if the filter value is found in the cache
 * @param filter
 * @return
 * @throws RemoteException
 * @throws SRTCacheManagerException
 */
public Collection<Object>  getResultedDrugTests(Map filter) throws
  RemoteException, SRTCacheManagerException{

  try{
  	if(!SRTCacheResultedTestHelper.validateKeys(filter))
    throw new InvalidSRTFilterKeysException("getResultedTests: Invalid filters. Validation failed");
  SRTCacheResultedTestHelper helper =  SRTCacheResultedTestHelper.getInstance();
  Collection<Object>  coll = helper.getResultedTests(filter,true);
  return coll;
  }
  catch(Exception ex){
	logger.fatal("Exception thrown in getResultedDrugTests method with ex:"+ ex.getMessage(),ex);
	throw new RemoteException(ex.getMessage(),ex);
  }
}

  /**
   * getAnatomicSites returns a collection of anatomic sites based on the filtering
   * criteria. SRTFilterKeys defines the keys for filter. getAnatomicSites currently accepts
   * the following combinations
   * 1. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.ORDERED_TEST_CODE
   * 2. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.RESULTED_TEST_CODE
   * 3. SRTFilterKeys.LOINC_CODE (May not be used for 1.1.3)
   * 4. HashMap<Object,Object> of size zero will return all the anatomic sites
   * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
   * Returns: If the combination is right, returnset will contain whole collection or filtered
   * if the filter value is found in the cache
   * @param filter
   * @return
   * @throws RemoteException
   * @throws SRTCacheManagerException
   */
  public Collection<Object>  getAnatomicSites(Map filter) throws
      RemoteException, SRTCacheManagerException {
  	try{
  		
    if(!SRTCacheAnatomicSiteHelper.validateFilterKeys(filter))
      throw new InvalidSRTFilterKeysException("getAnatomicSites:Invalid filters. Validation failed");

    SRTCacheAnatomicSiteHelper helper = SRTCacheAnatomicSiteHelper.getInstance();
    Collection<Object>  coll =  helper.getAnatomicSites(filter);
    return coll;
    }
    catch(Exception ex){
  	logger.fatal("Exception thrown in getAnatomicSites method with ex: "+ ex.getMessage(),ex);
  	throw new RemoteException(ex.getMessage(),ex);
    }

  }
  /**
   * getSpecimenSources returns a collection of specimen sources based on the filtering
   * criteria. SRTFilterKeys defines the keys for filter. getSpecimenSources currently accepts
   * the following combinations
   * 1. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.ORDERED_TEST_CODE
   * 2. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.RESULTED_TEST_CODE
   * 3. SRTFilterKeys.LOINC_CODE
   * 4. HashMap<Object,Object> of size zero will return all the specimen sources
   * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
   * Returns: If the combination is right, returnset will contain whole collection or filtered
   * if the filter value is found in the cache
   * @param filter
   * @return
   * @throws RemoteException
   * @throws SRTCacheManagerException
   */

  public Collection<Object>  getSpecimenSources(Map filter) throws
      RemoteException, SRTCacheManagerException {
  	try{
    if(!SRTCacheSpecimenSourceHelper.validateFilterKeys(filter))
      throw new InvalidSRTFilterKeysException("getSpecimenSources:Invalid filters. Validation failed");

    SRTCacheSpecimenSourceHelper helper = SRTCacheSpecimenSourceHelper.getInstance();
    Collection<Object>  coll =  helper.getSpecimenSource(filter);
    return coll;
    }
    catch(Exception ex){
    	logger.fatal("Exception thrown in getSpecimenSources method with ex: "+ ex.getMessage(),ex);
    	throw new RemoteException(ex.getMessage(),ex);
    }
  }
  /**
   * getUnits returns a collection of units based on the filtering
   * criteria. SRTFilterKeys defines the keys for filter. getUnits currently accepts
   * the following combinations
   * 1. SRTFilterKeys.REPORTING_FACILITY_ID
   *    SRTFilterKeys.RESULTED_TEST_CODE
   * 2. SRTFilterKeys.LOINC_CODE (May not be used for 1.1.3)
   * 3. HashMap<Object,Object> of size zero will return all the units
   * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
   * Returns: If the combination is right, returnset will contain whole collection or filtered
   * if the filter value is found in the cache
   * @param filter
   * @return
   * @throws RemoteException
   * @throws SRTCacheManagerException
   */

  public Collection<Object>  getUnits(Map filter) throws
      RemoteException, SRTCacheManagerException {
    try{
  	if(!SRTCacheUnitsHelper.validateFilterKeys(filter))
      throw new InvalidSRTFilterKeysException("getUnits:Invalid filters. Validation failed");
     SRTCacheUnitsHelper helper = SRTCacheUnitsHelper.getInstance();
     Collection<Object>  coll =  helper.getUnits(filter);
     return coll;
      }catch(Exception ex){
      	logger.fatal("Exception thrown in getUnits method with ex:"+ ex.getMessage(),ex);
      	throw new RemoteException(ex.getMessage(), ex);
      }

  }

  /**
 * getSpecimenSources returns a collection of specimen sources based on the filtering
 * criteria. SRTFilterKeys defines the keys for filter. getSpecimenSources currently accepts
 * the following combinations
 * 1. SRTFilterKeys.REPORTING_FACILITY_ID
 *    SRTFilterKeys.ORDERED_TEST_CODE
 * 2. SRTFilterKeys.REPORTING_FACILITY_ID
 *    SRTFilterKeys.RESULTED_TEST_CODE
 * 3. SRTFilterKeys.LOINC_CODE
 * 4. HashMap<Object,Object> of size zero will return all the specimen sources
 * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
 * Returns: If the combination is right, returnset will contain whole collection or filtered
 * if the filter value is found in the cache
 * @param filter
 * @return
 * @throws RemoteException
 * @throws SRTCacheManagerException
 */

  public Collection<Object>  getOrganisms(Map filter) throws
      RemoteException, SRTCacheManagerException {
    try{
    if(!SRTCacheLabResultHelper.validateKeys(filter))
     throw new InvalidSRTFilterKeysException("getNBSDisplayElementIDs: Validation failed");
    SRTCacheLabResultHelper helper =  SRTCacheLabResultHelper.getInstance();
    Collection<Object>  coll = helper.getOrganisms(filter);
    return coll;
      }catch(Exception ex){
      	logger.fatal("Exception thrown in getOrganisms method with ex: "+ ex.getMessage(),ex);
      	throw new RemoteException(ex.getMessage(), ex);
      }
  }

  /**
 * getSpecimenSources returns a collection of specimen sources based on the filtering
 * criteria. SRTFilterKeys defines the keys for filter. getLabResults currently accepts
 * the following combinations
 * 1. SRTFilterKeys.RESULTED_TEST_CODE
 *    SRTFilterKeys.REPORTING_FACILITY_ID

 * 2. SRTFilterKeys.REPORTING_FACILITY_ID

 * 3. SRTFilterKeys.LAB_RESULT_CODE
      SRTFilterKeys.REPORTING_FACILITY_ID
 * 4. HashMap<Object,Object> of size zero will return all the lab results
 * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
 * Returns: If the combination is right, returnset will contain whole collection or filtered
 * if the filter value is found in the cache
 * @param filter
 * @return
 * @throws RemoteException
 * @throws SRTCacheManagerException
 */
  public Collection<Object>  getLabResults(Map filter) throws
    RemoteException, SRTCacheManagerException {
  try{
  	if(!SRTCacheLabResultHelper.validateKeys(filter))
     throw new InvalidSRTFilterKeysException("getNBSDisplayElementIDs: Validation failed");
  SRTCacheLabResultHelper helper =  SRTCacheLabResultHelper.getInstance();
  Collection<Object>  coll = helper.getLabResults(filter);
  return coll;
  }catch(Exception ex){
      	logger.fatal("Exception thrown in getLabResults method with ex:"+ ex.getMessage(),ex);
      	throw new RemoteException(ex.getMessage(), ex);
  }
}


/**
* getSpecimenSources returns a collection of specimen sources based on the filtering
* criteria. SRTFilterKeys defines the keys for filter. getSpecimenSources currently accepts
* the following combinations
 *         1. SRTFilterKeys.REPORTING_FACILITY_ID
*
 *         2. SRTFilterKeys.REPORTING_FACILITY_ID
 *            SRTFilterKeys.RESULTED_TEST_CODE

* 4. HashMap<Object,Object> of size zero will return all the display codes.
* Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
* Returns: If the combination is right, returnset will contain whole collection or filtered
* if the filter value is found in the cache
* @param filter
* @return
* @throws RemoteException
* @throws SRTCacheManagerException
*/

  public Collection<Object>  getNBSDisplayElementIDs(Map filter) throws
      RemoteException, SRTCacheManagerException {
   /*
    if(!SRTCacheDisplayCodeHelper.validateKeys(filter))
      throw new InvalidSRTFilterKeysException("getNBSDisplayElementIDs: Validation failed");

    SRTCacheDisplayCodeHelper srtCacheDisplayCodeHelper = SRTCacheDisplayCodeHelper.getInstance();
    Collection<Object>  coll = srtCacheDisplayCodeHelper.getNBSDisplayElements(filter);
    return coll;
    */
   return null;
  }

  /**
 * getSpecimenSources returns a collection of specimen sources based on the filtering
 * criteria. SRTFilterKeys defines the keys for filter. getTreatment currently accepts
 * the following combinations
 * 1. SRTFilterKeys.CONDITION_CODE
 * 2. SRTFilterKeys.PROGRAM_AREA_CODE
 * 4. HashMap<Object,Object> of size zero will return all the treatments
 * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
 * Returns: If the combination is right, returnset will contain whole collection or filtered
 * if the filter value is found in the cache
 * @param filter
 * @return
 * @throws RemoteException
 * @throws SRTCacheManagerException
 */

  public Collection<Object>  getTreatments(Map filter) throws
      RemoteException, SRTCacheManagerException {
    try{
    	if(!SRTCacheTreatmentHelper.validateKeys(filter))
        throw new InvalidSRTFilterKeysException("getTreatments: Validation failed");
       SRTCacheTreatmentHelper helper =  SRTCacheTreatmentHelper.getInstance();
       Collection<Object>  coll = helper.getTreatments(filter);
       return coll;
      }catch(Exception ex){
      	logger.fatal("Exception thrown in getTreatments method with ex:"+ ex.getMessage(),ex);
      	throw new RemoteException(ex.getMessage() , ex);
      }
  }

  /**
 * getSpecimenSources returns a collection of specimen sources based on the filtering
 * criteria. SRTFilterKeys defines the keys for filter. getTreatmentDrugs currently accepts
 * the following combinations
 * 1. SRTFilterKeys.CONDITION_CODE
 * 2. SRTFilterKeys.PROGRAM_AREA_CODE
 * 4. HashMap<Object,Object> of size zero will return all the treatment drugs
 * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
 * Returns: If the combination is right, returnset will contain whole collection or filtered
 * if the filter value is found in the cache
 * @param filter
 * @return
 * @throws RemoteException
 * @throws SRTCacheManagerException
 */

  public Collection<Object>  getTreatmentDrugs(Map filter) throws
      RemoteException, SRTCacheManagerException {

    try {
		if(!SRTCacheTreatmentHelper.validateKeys(filter))
		  throw new InvalidSRTFilterKeysException("getTreatmentDrugs: Validation failed");

		SRTCacheTreatmentHelper helper =  SRTCacheTreatmentHelper.getInstance();
		Collection<Object>  coll = helper.getDrugs(filter);
		return coll;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}



}
/*This requirement may not be needed
  public Collection<Object>  getMorbReportingFacility(Map filter) throws
      RemoteException, SRTCacheManagerException {
    return null;
  }
 */
  /**
   * getInvestigationReportingSource returns a collection of reporting sources based on the filtering
   * criteria. SRTFilterKeys defines the keys for filter. getInvestigationReportingSource currently accepts
   * the following combinations
   * 1. SRTFilterKeys.CONDITION_CODE
   * 2. SRTFilterKeys.PROGRAM_AREA_CODE
   * 3. HashMap<Object,Object> of size zero will return all the resulted tests
   * Exception: InvalidFilterKeys Exception thrown if hashmap is null or invalid combination
   * Returns: If the combination is right, returnset will contain whole collection or filtered
   * if the filter value is found in the cache
   * @param filter
   * @return
   * @throws RemoteException
   * @throws SRTCacheManagerException
   */

  public Collection<Object>  getInvestigationReportingSource(Map filter) throws
    RemoteException, SRTCacheManagerException {
  /*
  if(!SRTCacheReportingSourceHelper.validateFilterKeys(filter))
      throw new InvalidSRTFilterKeysException("getInvestigationReportingSource:Invalid filters. Validation failed");
  SRTCacheReportingSourceHelper helper = SRTCacheReportingSourceHelper.getInstance();
   Collection<Object>  coll =  helper.getInvestigationReportingSource(filter);
   return coll;
   */
   return null;
  }








}





