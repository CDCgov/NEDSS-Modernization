package gov.cdc.nedss.geocoding.ejb.geocodingservice.bean;

import gov.cdc.nedss.geocoding.exception.*;
import gov.cdc.nedss.geocoding.util.BatchCompletionStatus;
import gov.cdc.nedss.geocoding.dt.GeoCodingPostalLocatorDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.sql.Timestamp;
import java.rmi.RemoteException;

/**
 * Business Interface for the Geocoding Service.
 * 
 * @author rdodge
 */
public interface GeoCodingBusinessInterface
{
	// Session Support Operations //

	/** Requests a new geocoding session. */
	public Long newSession(NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;

	/** Finalizes the specified geocoding session. */
	public void finalizeSession(Long sessionId, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;


	// Geocoding Operations //

	/** Geocodes the specified address. */
	public Integer geoCodeAddress(Long sessionID, GeoCodingPostalLocatorDT address, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;

	/** Geocodes the specified block of addresses; reads, codes then writes the entire address block in sequential steps. */
	public Integer geoCodeAddressBlock(Long sessionID, GeoCodingPostalLocatorDT[] addressBlock, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;


	// Geocoding-Internal Operations //

	/** Internal method used by batch mode operations to clear geocoding status of postal locators flagged as errors. */
	public void clearErrorRecords(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;

	/** Internal method used by batch mode operations to clear geocoding status of postal locators flagged as geocoded. */
	public void clearGeoCodedRecords(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;

	/** Internal method used by batch mode operations to geocode a block of addresses. */
	public Exception geoCodeBatchAddressBlock(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;

	/** Internal method used by batch mode operations to update the activity log. */
	public void updateActivityLog(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException;


	// Batch Mode Operations //

	/** Propagates record status from postal locators to the corresponding geocoding results. */
	public void updateGeoCodingRecordStatus(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException;

	/** Processes geocoding for all addresses created/updated since the batch was last run. */
	public BatchCompletionStatus processIncrementalBatchGeoCoding(Long sessionID, Long activityLogUid, Boolean retryErrors, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;

	/** [Re]processes geocoding for all recorded addresses. */
	public BatchCompletionStatus processFullBatchGeoCoding(Long sessionID, Long activityLogUid, Boolean retryErrors, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException;


	// Batch Mode Support Operations //

	/** Creates a batch log entry indicating a new batch has commenced. */
	public Long logBatchStarted(Long sessionID, Boolean isFullBatch, Timestamp startTime, NBSSecurityObj nbsSecurityObj) throws RemoteException;

	/** Updates the specified batch log entry with relevant data and an indicator that the batch has completed. */
	public void logBatchCompleted(Long sessionID, Long uid, Timestamp endTime, Boolean isCompleted, NBSSecurityObj nbsSecurityObj) throws RemoteException;

	/** Retrieves the (ending) timestamp of the most recent batch process to complete. */
	public Timestamp getLastBatchProcessTime(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException;
}
