/*
 * Created on Dec 11, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean;

import javax.ejb.*;
import java.rmi.*;
import java.util.*;
import gov.cdc.nedss.systemservice.exception.SRTCacheManagerException;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface SRTCacheManagerBusinessInterface {
	public Collection<Object>  getOrderedTestNames(String labCLIAid, String programAreaCD) throws
		RemoteException, EJBException;

	public Collection<Object>  getOrderedTestNames(String labCLIAid) throws RemoteException,
		EJBException;

	public Collection<Object>  getResultedTestNames(String labCLIAid, String programAreaCD) throws
		RemoteException, EJBException;

	public Collection<Object>  getResultedTestNames(String labCLIAid) throws RemoteException,
		EJBException;

	/** Returns a collection of SRTLabTestDT representing ordered tests
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTLabTestDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getOrderedTests(Map<String,String> filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of SRTLabTestDT representing resulted tests
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTLabTestDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getResultedTests(Map filter) throws RemoteException,
		SRTCacheManagerException;

            /** Returns a collection of SRTLabTestDT representing resulted tests
             *  based on the filers defined in <code>filter</code>.
             *
             * @param filter A map that holds the filters.  The key to the map holds
             * filter type, the value for map holds the filter value.
             * @return A sorted collection of SRTLabTestDT.
             * @throws RemoteException
             * @throws SRTCacheManagerException If filters defined in <code>filter</code>
             * are not supported.
             */
        public Collection<Object>  getResultedDrugTests(Map filter) throws RemoteException,
                    SRTCacheManagerException;


	/** Returns a collection of SRTGenericCodeDT representing anatomic sites
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTGenericCodeDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getAnatomicSites(Map filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of SRTGenericCodeDT representing specimen source
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTGenericCodeDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getSpecimenSources(Map filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of SRTGenericCodeDT representing units
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTGenericCodeDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getUnits(Map filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of SRTLabResultDT representing organisms
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTLabResultDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getOrganisms(Map filter) throws RemoteException,
		SRTCacheManagerException;

        /** Returns a collection of SRTLabResultDT representing lab results
         *  based on the filers defined in <code>filter</code>.
         *
         * @param filter A map that holds the filters.  The key to the map holds
         * filter type, the value for map holds the filter value.
         * @return A sorted collection of SRTLabResultDT.
         * @throws RemoteException
         * @throws SRTCacheManagerException If filters defined in <code>filter</code>
         * are not supported.
     */
    public Collection<Object>  getLabResults(Map filter) throws RemoteException,
            SRTCacheManagerException;


	/** Returns a collection of String representing NBS display IDs
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A collection of String.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
 */
	public Collection<Object>  getNBSDisplayElementIDs(Map filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of SRTTreatmentDT representing treatments
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTTreatmentDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getTreatments(Map filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of SRTTreatmentDrugDT representing treatment drugs
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTTreatmentDrugDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
	 */
	public Collection<Object>  getTreatmentDrugs(Map filter) throws RemoteException,
		SRTCacheManagerException;

	/** Returns a collection of String representing morbidity reporting facility IDs
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of String.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.

	public Collection<Object>  getMorbReportingFacility(Map filter) throws RemoteException,
		SRTCacheManagerException;
        */
	/** Returns a collection of SRTGenericCodeDT representing investigation reporting sources
	 *  based on the filers defined in <code>filter</code>.
	 *
	 * @param filter A map that holds the filters.  The key to the map holds
	 * filter type, the value for map holds the filter value.
	 * @return A sorted collection of SRTGenericCodeDT.
	 * @throws RemoteException
	 * @throws SRTCacheManagerException If filters defined in <code>filter</code>
	 * are not supported.
      */
	public Collection<Object>  getInvestigationReportingSource(Map filter) throws RemoteException,
		SRTCacheManagerException;


        public void initializeSRTCache() throws RemoteException;

}
