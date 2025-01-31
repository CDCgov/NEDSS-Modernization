package gov.cdc.nedss.geocoding.ejb.geocodingservice.bean;

import gov.cdc.nedss.geocoding.dao.GeoCodingResultDAOImpl;
import gov.cdc.nedss.geocoding.dt.GeoCodingPostalLocatorDT;
import gov.cdc.nedss.geocoding.dt.GeoCodingResultDT;
import gov.cdc.nedss.geocoding.ejb.geocodingservice.dao.GeoCodingActivityLogDAOImpl;
import gov.cdc.nedss.geocoding.exception.NEDSSGeoCodingException;
import gov.cdc.nedss.geocoding.geodata.GeoDataAddressConverter;
import gov.cdc.nedss.geocoding.geodata.GeoDataInputAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataResult;
import gov.cdc.nedss.geocoding.geodata.LoadableGeoDataClient;
import gov.cdc.nedss.geocoding.util.BatchCompletionStatus;
import gov.cdc.nedss.geocoding.util.GeoCodingHelper;
import gov.cdc.nedss.geocoding.util.GeoCodingActivityLogDT;
import gov.cdc.nedss.geocoding.util.MultiMatchState;
import gov.cdc.nedss.locator.dao.PostalLocatorDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.*;
import javax.rmi.PortableRemoteObject;

/**
 * Geocoding Service EJB implementation.
 * 
 * @author rdodge
 */
public class GeoCodingServiceEJB implements SessionBean, GeoCodingBusinessInterface {

	// Members //
	static final LogUtils logger = new LogUtils(GeoCodingServiceEJB.class.getName());
	static final long serialVersionUID = 1L;

	/** Default (empty) constructor. */
	public GeoCodingServiceEJB() {}


	// EJB method implementations //

	/** J2EE ejbCreate() method. */
	public void ejbCreate() {}

	/** J2EE ejbRemove() method. */
	public void ejbRemove() {}

	/** J2EE ejbActivate() method. */
	public void ejbActivate() {}

	/** J2EE ejbPassivate() method. */
	public void ejbPassivate() {}

	/** J2EE Session Bean setSessionContext() method.  @param sc */
	public void setSessionContext(SessionContext sc) {}


	// Session Support Operations //

	/**
	 * Requests a new geocoding session.
	 * 
	 * @param nbsSecurityObj
	 * @return Session object
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public Long newSession(NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		Long sessionID = null;
		try {
			logger.debug("Inside the newSession() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.newSession().");
			}

			// Allocate new session //
			sessionID = GeoCodingHelper.getInstance().newSession();
		}
		catch (Exception e) {
			if (e instanceof NEDSSGeoCodingException) {
				logger.fatal(e.getMessage(), e);
				throw (NEDSSGeoCodingException) e;
			}
			logger.fatal(e.getMessage(), e);
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return sessionID;
	}

	/**
	 * Finalizes the specified geocoding session.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public void finalizeSession(Long sessionID, NBSSecurityObj nbsSecurityObj)
			throws RemoteException, NEDSSGeoCodingException {

		try {
			logger.debug("Inside the finalizeSession() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.finalizeSession().");
			}

			// Finalize session //
			GeoCodingHelper.getInstance().finalizeSession(sessionID);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
	}


	// Geocoding Operations //

	/**
	 * Geocodes the specified address.
	 * 
	 * @param sessionID
	 * @param address
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public Integer geoCodeAddress(Long sessionID, GeoCodingPostalLocatorDT address,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		try {
			logger.debug("Inside the geoCodeAddress() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.geoCodeAddress().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Geocode address //
			GeoCodingHelper.getInstance().ensureClientIsInitialized();
			geoCodeAddress(sessionID, address, userID, true);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return GeoCodingHelper.singleResult;
	}

	/**
	 * Geocodes the specified block of addresses.  Reads, codes then
	 * writes the entire address block in sequential steps.
	 * 
	 * @param sessionID
	 * @param addressBlock
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public Integer geoCodeAddressBlock(Long sessionID, GeoCodingPostalLocatorDT[] addressBlock,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		int count = 0;

		try {
			logger.debug("Inside the geoCodeAddressBlock() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.geoCodeAddressBlock().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Geocode address block //
			GeoCodingHelper.getInstance().ensureClientIsInitialized();
			GeoCodingResultDT[] resultBlock = geoCodeAddressBlock(sessionID, addressBlock, userID, true);
			count = resultBlock.length;
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return new Integer(count);
	}


	// Geocoding-Internal Operations //

	/**
	 * Internal method used by batch mode operations to clear geocoding status
	 * of postal locators flagged as errors.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public void clearErrorRecords(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		try {
			logger.debug("Inside the clearErrorRecords() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.clearErrorRecords().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Clear geocoding status of error records //
			clearErrorRecords(sessionID, userID);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
	}

	/**
	 * Internal method used by batch mode operations to clear geocoding status
	 * of postal locators flagged as geocoded.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public void clearGeoCodedRecords(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		try {
			logger.debug("Inside the clearGeoCodedRecords() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.clearGeoCodedRecords().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Clear geocoding status of geocoded records //
			clearGeoCodedRecords(sessionID, userID);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
	}

	/**
	 * Internal method used by batch mode operations to geocode a block of addresses.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public Exception geoCodeBatchAddressBlock(Long sessionID,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		Exception result = null;
		try {
			logger.debug("Inside the geoCodeBatchAddressBlock() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.geoCodeBatchAddressBlock().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Geocode address block //
			result = geoCodeAddressBlock(sessionID, userID, true);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return result;
	}

	/**
	 * Internal method used by batch mode operations to update the activity log.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 */
	public void updateActivityLog(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException {

		try {
			logger.debug("Inside the updateActivityLog() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.updateActivityLog().");
			}

			// Clear geocoding status of geocoded records //
			updateActivityLog(sessionID);
		}
		catch (RuntimeException e) {
			logger.fatal(e.getMessage(), e);
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
	}


	// Batch Mode Operations //

	/**
	 * Propagates record status from postal locators to the corresponding geocoding results.
	 * Is called in order to flag any Inactive rows as prior to batch processing so that
	 * they are not processed.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 */
	public void updateGeoCodingRecordStatus(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException {

		try {
			logger.debug("Inside the updateGeoCodingRecordStatus() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.updateGeoCodingRecordStatus().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Update record status //
			updateGeoCodingRecordStatus(sessionID, userID);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
	}

	/**
	 * Processes geocoding for all addresses created/updated
	 * since the batch was last run.
	 * 
	 * @param sessionID
	 * @param activityLogUid
	 * @param retryErrors
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public BatchCompletionStatus processIncrementalBatchGeoCoding(Long sessionID, Long activityLogUid, Boolean retryErrors,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		BatchCompletionStatus status = null;
		try {
			logger.debug("Inside the processIncrementalBatchGeoCoding() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.processIncrementalBatchGeoCoding().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Perform incremental batch geocoding //
			GeoCodingHelper.getInstance().ensureClientIsInitialized();
			status = processIncrementalBatchGeoCoding(sessionID, activityLogUid, retryErrors, userID, nbsSecurityObj);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return status;
	}

	/**
	 * [Re]processes geocoding for all recorded addresses.
	 * 
	 * @param sessionID
	 * @param activityLogUid
	 * @param retryErrors
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSGeoCodingException
	 */
	public BatchCompletionStatus processFullBatchGeoCoding(Long sessionID, Long activityLogUid, Boolean retryErrors,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSGeoCodingException {

		BatchCompletionStatus status = null;
		try {
			logger.debug("Inside the processFullBatchGeoCoding() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.processFullBatchGeoCoding().");
			}
			Long userID = extractUserId(nbsSecurityObj);

			// Perform full batch geocoding //
			GeoCodingHelper.getInstance().ensureClientIsInitialized();
			status = processFullBatchGeoCoding(sessionID, activityLogUid, retryErrors, userID, nbsSecurityObj);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (e instanceof NEDSSGeoCodingException) {
				throw (NEDSSGeoCodingException) e;
			}
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return status;
	}


	// Batch Mode Support Operations //

	/**
	 * Creates a batch log entry indicating a new batch has commenced.
	 * 
	 * @param sessionID
	 * @param isFullBatch
	 * @param startTime
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 */
	public Long logBatchStarted(Long sessionID, Boolean isFullBatch, Timestamp startTime,
			NBSSecurityObj nbsSecurityObj) throws RemoteException {

		Long activityLogUid = null;
		try {
			logger.debug("Inside the logBatchStarted() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.logBatchStarted().");
			}

			// Log batch started //
			activityLogUid = logBatchStarted(sessionID, isFullBatch, startTime);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return activityLogUid;
	}

	/**
	 * Updates the specified batch log entry with relevant data and an indicator
	 * that the batch has completed.
	 * 
	 * @param sessionID
	 * @param uid
	 * @param endTime
	 * @param isCompleted
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 */
	public void logBatchCompleted(Long sessionID, Long uid, Timestamp endTime, Boolean isCompleted,
			NBSSecurityObj nbsSecurityObj) throws RemoteException {

		try {
			logger.debug("Inside the logBatchCompleted() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.logBatchCompleted().");
			}

			// Log batch completed //
			logBatchCompleted(sessionID, uid, endTime, isCompleted);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
	}

	/**
	 * Retrieves the (ending) timestamp of the most recent batch process to complete.
	 * 
	 * @param sessionID
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 */
	public Timestamp getLastBatchProcessTime(Long sessionID, NBSSecurityObj nbsSecurityObj) throws RemoteException {

		Timestamp lastBatchProcessTime = null;

		try {
			logger.debug("Inside the getLastBatchProcessTime() method");

			// Authenticate //
			if (!authenticateUser(nbsSecurityObj)) {
				throw new NEDSSSystemException("Not authenticated for GeocodingService.getLastBatchProcessTime().");
			}

			// Load timestamp from the DAO //
			lastBatchProcessTime = getLastBatchProcessTime(sessionID);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw (EJBException) new EJBException(e.toString()).initCause(e);
		}
		return lastBatchProcessTime;
	}


	// Internal Methods //

	/**
	 * Controls / provides access to the current EJB through the remote interface.
	 * 
	 * @return Remote object
	 * @throws Exception
	 */
	private GeoCodingService getSelfAsRemoteObject() throws Exception {

		NedssUtils nedssUtils = new NedssUtils();

		GeoCodingService serviceObject = null;
		GeoCodingServiceHome serviceHome = null;

		try {
			serviceHome = (GeoCodingServiceHome) PortableRemoteObject.narrow(
					nedssUtils.lookupBean(JNDINames.GEOCODING_SERVICE_EJB), GeoCodingServiceHome.class);
			serviceObject = serviceHome.create();
		}
		catch (Exception e) {
			logger.fatal("Exception while obtaining GeoCodingService remote interface: " +
					e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
		return serviceObject;
	}

	/**
	 * Authenticates user.  Return value can typically be ignored;
	 * relies on checked exception to indicate authentication failure. 
	 * 
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSSystemException Thrown if user is not authenticated
	 */
	protected boolean authenticateUser(NBSSecurityObj
			nbsSecurityObj) throws NEDSSSystemException {

		try {
			logger.debug("Inside the authenticateUser() method");
			boolean check = nbsSecurityObj.getPermission(
					NBSBOLookup.SYSTEM, NBSOperationLookup.GEOCODING);

			if (check == false) {
				throw new NEDSSSystemException(
						"User does not have permission to access the GeoCoding Service.");
			}
			return check;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}


	/**
	 * Propagates record status from postal locators to the corresponding geocoding results.
	 * 
	 * @param sessionID
	 * @param userId
	 * @return
	 */
	protected void updateGeoCodingRecordStatus(Long sessionID, Long userId) throws Exception {

		GeoCodingHelper helper = GeoCodingHelper.getInstance();

		// Update record status //
		GeoCodingResultDAOImpl dao = helper.getGeoCodingResultDAO();
		try {
			dao.updateGeoCodingResultForInactivatedPostalLocators();
		}
		catch (Exception e) {
			helper.addException(GeoCodingHelper.EXCEPTION_CLEAR_RECORD_STATUS, -1, -1, sessionID);
			logger.fatal("Exception while attempting to update geocoding record status: " + e.getMessage(), e);

			throw new NEDSSSystemException(e.getMessage(), e);  // abort
		}
	}

	/**
	 * Clears geocoding status of postal locators flagged as errors.
	 * 
	 * @param sessionID
	 * @param userId
	 * @throws Exception
	 */
	protected void clearErrorRecords(Long sessionID, Long userId) throws Exception {
		
		String errorString = null;
		GeoCodingHelper helper = GeoCodingHelper.getInstance();
		PostalLocatorDAOImpl dao = helper.getPostalLocatorDAO();

		// Clear geocoding indicators for addresses flagged as errors //
		try {
			dao.clearErrorGeocodeMatchIndicators();
		}
		catch (Exception e) {
			helper.addException(GeoCodingHelper.EXCEPTION_CLEAR_ERROR_INDS, -1, -1, sessionID);
			logger.fatal("Exception while attempting to clear postal locator errors for geocoding batch: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
			// attempt to continue
		}
	}

	/**
	 * Clears geocoding status of postal locators flagged as geocoded.
	 * 
	 * @param sessionID
	 * @param userId
	 * @throws Exception
	 */
	protected void clearGeoCodedRecords(Long sessionID, Long userId) throws Exception {
		
		GeoCodingHelper helper = GeoCodingHelper.getInstance();

		// Clear geocoding indicators for all addresses (except those flagged as errors) //
		PostalLocatorDAOImpl dao = helper.getPostalLocatorDAO();
		try {
			dao.clearAllGeocodeMatchIndicators();
		}
		catch (Exception e) {
			helper.addException(GeoCodingHelper.EXCEPTION_CLEAR_INDS, -1, -1, sessionID);
			logger.fatal("Exception while clearing geocoding status for batch: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);  // not being able to make PL updates is fatal to the geocoding process
		}
	}

	/**
	 * Performs an incremental batch geocoding operation.
	 * 
	 * @param sessionID
	 * @param activityLogUid
	 * @param retryErrors
	 * @param userId
	 * @param nbsSecurityObj
	 * @return
	 * @throws Exception
	 */
	protected BatchCompletionStatus processIncrementalBatchGeoCoding(Long sessionID, Long activityLogUid,
			Boolean retryErrors, Long userId, NBSSecurityObj nbsSecurityObj) throws Exception {

		try {
			String errorString = null;
			GeoCodingHelper helper = GeoCodingHelper.getInstance();
			PostalLocatorDAOImpl dao = helper.getPostalLocatorDAO();

			// Clear error geocoding indicators if appropriate (note: parameter overrides property setting) //
			if (Boolean.TRUE.equals(retryErrors) || (retryErrors == null && helper.getRetryErrors())) {
				try {
					getSelfAsRemoteObject().clearErrorRecords(sessionID, nbsSecurityObj);
				}
				catch (Exception e) {
					logger.fatal(e.getMessage(), e);
					throw new NEDSSSystemException(e.getMessage(), e);
					
				}  // attempt to continue
			}

			// Initialize Storage //
			helper.initStorage(sessionID);  // initialize in regular batch mode
			helper.setActivityLogUid(activityLogUid, sessionID);

			// Loop until no more blocks remain, or two failures are encountered //
			Exception geoCodingException = null;
			int blockSize = 0, i = 0, fatalExceptionCount = 0;
			do {
				// Fetch address (postal locator) block //
				Collection<Object>  postalLocators = null;
				while (fatalExceptionCount <= 1) {  // alternative to 'while (true)'
					try {
						postalLocators = dao.findPostalLocatorsNeedingGeoCoding();
						break;  // LOOP EXIT - default
					}
					catch (Exception e) {
						helper.addException(GeoCodingHelper.EXCEPTION_FETCH_PLS, i, -1, sessionID);
						errorString = "Exception while attempting to fetch postal locators for geocoding batch (Block " +
								i + "): " + e.toString();
						logger.fatal(errorString, e);

						// Fail twice before aborting //
						fatalExceptionCount++;
						if (fatalExceptionCount > 1) {
							break;  // INNER LOOP EXIT - max retries reached
						}
					}
				}

				// If applicable, abort (part 1) by breaking out of loop //
				if (fatalExceptionCount > 1) {
					break;  // LOOP EXIT - max retries reached
				}

				// For remaining address blocks...
				blockSize = postalLocators.size();
				if (blockSize != 0) {
					helper.clearStorageBuffers(sessionID);
					helper.setBufferBlockSize(blockSize, sessionID);
					helper.setBufferIndex(i, sessionID);
					postalLocators.toArray(helper.getPostalLocatorBlock(sessionID));

					// Geocode/persist address block //
					try {
						try {
							geoCodingException = getSelfAsRemoteObject().geoCodeBatchAddressBlock(sessionID, nbsSecurityObj);
						}
						catch (Exception e1) {
							logger.error(e1.getMessage(), e1);
							geoCodingException = e1;
						}
						updateBatchStatus(sessionID);
						getSelfAsRemoteObject().updateActivityLog(sessionID, nbsSecurityObj);
					}
					catch (Exception e) {
						helper.addException(GeoCodingHelper.EXCEPTION_GEOCODER, i, -1, sessionID);
						errorString = "Unexpected exception while processing geocoding batch (Block " + i + "): " + e.toString();
						logger.fatal(errorString, e);

						geoCodingException = e;
					}

					// Fail twice before aborting //
					if (geoCodingException != null) {
						fatalExceptionCount++;

						// Abort (part 1) by breaking out of loop //
						if (fatalExceptionCount > 1) {
							break;  // LOOP EXIT - max retries reached
						}
					}

					i++;  // increment major index
				}
			} while (blockSize != 0);

			// Log exception list (regardless of error flow) //
			if (helper.getNumExceptions(sessionID) > 0) {
				logger.fatal("Errors accumulated while processing: " + helper.getExceptionString(sessionID));
			}

			// If applicable, abort (part 2) by rethrowing exception //
			if (fatalExceptionCount > 1) {
				if (geoCodingException instanceof NEDSSGeoCodingException) {
					throw geoCodingException;  // METHOD EXIT - max retries reached
				}
				else {
					throw (NEDSSGeoCodingException) new NEDSSGeoCodingException("Runtime exception: " +
							geoCodingException.toString()).initCause(geoCodingException);  // METHOD EXIT - max retries reached
				}
			}

			// Otherwise, set status to Completed & return //
			BatchCompletionStatus status = helper.getBatchCompletionStatus(sessionID);
			status.setCompleted(fatalExceptionCount <= 0);

			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Performs a full batch geocoding operation.
	 * 
	 * @param sessionID
	 * @param activityLogUid
	 * @param retryErrors
	 * @param userId
	 * @param nbsSecurityObj
	 * @return
	 * @throws Exception
	 */
	protected BatchCompletionStatus processFullBatchGeoCoding(Long sessionID, Long activityLogUid,
			Boolean retryErrors, Long userId, NBSSecurityObj nbsSecurityObj) throws Exception {

		try {
			// Clear geocoding indicators for all addresses (except those flagged as errors) //
			getSelfAsRemoteObject().clearGeoCodedRecords(sessionID, nbsSecurityObj);  // EARLY EXIT opportunity if Exception is thrown

			// Run 'incremental' batch geocoding on all addresses //
			this.processIncrementalBatchGeoCoding(sessionID, activityLogUid, retryErrors, userId, nbsSecurityObj);

			// Return status //
			BatchCompletionStatus status = GeoCodingHelper.getInstance().getBatchCompletionStatus(sessionID);
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Performs geocoding [only] for the indicated address; returns a geocoding result.
	 * 
	 * @param sessionID
	 * @param address
	 * @param userId
	 * @param persist Indicates whether result should be persisted
	 * @return
	 * @throws NEDSSGeoCodingException
	 */
	protected GeoCodingResultDT geoCodeAddress(Long sessionID, GeoCodingPostalLocatorDT address, Long userId,
			boolean persist) throws NEDSSGeoCodingException {

		try {
			String errorString = null;
			GeoCodingResultDT result = null;
			Exception geoCodingException = null;

			// Initialize storage //
			GeoCodingHelper helper = GeoCodingHelper.getInstance();
			helper.initStorage(sessionID);  // okay to treat as if initializing regular batch mode

			try {
				// Obtain result from client //
				LoadableGeoDataClient clientInstance = helper.getClientInstance();
				GeoDataAddressConverter addressConverter = clientInstance.getFactory().getAddressConverter();
				helper.updateStatePostalCode(address);
				GeoDataInputAddress inputAddress = addressConverter.convertInputToGeoData(address);
				GeoDataResult geoDataResult = clientInstance.lookupAddress(inputAddress);
				result = extractResult(sessionID, address, addressConverter, inputAddress, geoDataResult);
			}
			catch (Exception e) {
				helper.addException(GeoCodingHelper.EXCEPTION_GEOCODER, helper.getBufferIndex(sessionID), -1, sessionID);
				errorString = "Exception in geoCodeAddress() while attempting to geocode an address [PL=" +
						fetchPostalLocatorUID(address) + "]: " + e.toString();
				logger.fatal(errorString, e);
				geoCodingException = e;
			}

			// Persist result if applicable //
			if (persist && geoCodingException == null) {
				try {
					persistGeoCodingResult(sessionID, result, userId);
				}
				catch (Exception e) {
					errorString = "Exception in geoCodeAddress() while attempting to persist a geocoding result [PL=" +
							fetchPostalLocatorUID(address) + "]: " + e.toString();
					logger.fatal(errorString, e);
					geoCodingException = e;
				}
			}

			// [Re]throw exception if applicable //
			if (geoCodingException != null) {
				if (geoCodingException instanceof NEDSSGeoCodingException) {
					throw (NEDSSGeoCodingException) geoCodingException;
				}
				throw (NEDSSGeoCodingException) new NEDSSGeoCodingException(errorString).initCause(geoCodingException);
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Performs geocoding [only] for the indicated address block; returns a returns a
	 * block of geocoding results.  Uses single or batch geodata mode depending on
	 * helper settings.
	 * 
	 * Note: This variant is used with an ad hoc address block.
	 * 
	 * @param sessionID
	 * @param addressBlock
	 * @param userId
	 * @return
	 * @throws NEDSSGeoCodingException
	 */
	protected GeoCodingResultDT[] geoCodeAddressBlock(Long sessionID, GeoCodingPostalLocatorDT[] addressBlock, Long userId,
			boolean persist) throws NEDSSGeoCodingException {

		try {
			// Initialize storage //
			GeoCodingHelper helper = GeoCodingHelper.getInstance();
			helper.initStorageWithAddressBlock(addressBlock, sessionID);

			// Geocode address block //
			geoCodeAddressBlock(sessionID, userId, persist);

			// Extract geocoding result block from storage //
			int resultBlockSize = helper.getBufferBlockSize(sessionID);
			GeoCodingResultDT[] resultBlock = new GeoCodingResultDT[resultBlockSize];
			System.arraycopy(helper.getGeoCodingResultBlock(sessionID), 0, resultBlock, 0, resultBlockSize);
			return resultBlock;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Performs geocoding [only] for the indicated address block; returns a returns a
	 * block of geocoding results.  Uses single or batch geodata mode depending on
	 * helper settings.
	 * 
	 * @param sessionID
	 * @param userId
	 * @param persist Indicates whether result block should be persisted
	 * @return Exception
	 * @throws NEDSSGeoCodingException
	 */
	protected Exception geoCodeAddressBlock(Long sessionID, Long userId, boolean persist) {

		try {
			int i = 0;
			int blockSize = 0;
			int minorIndex = 0;

			String errorString = null;
			Exception result = null, geoCodingException = null, persistenceException = null;

			GeoCodingPostalLocatorDT[] addressBlock = null;
			GeoDataResult[] geoDataBlock = null;
			GeoCodingResultDT[] resultBlock = null;
			Boolean[] resultProcessed = null;

			try {
				GeoCodingHelper helper = GeoCodingHelper.getInstance();

				blockSize = helper.getBufferBlockSize(sessionID);
				addressBlock = helper.getPostalLocatorBlock(sessionID);
				resultBlock = helper.getGeoCodingResultBlock(sessionID);
				geoDataBlock = helper.getGeoDataResultBlock(sessionID);
				resultProcessed = helper.getResultProcessed(sessionID);

				boolean useBatch = helper.isClientBatchLookup() && helper.getClientBatchLookupSize() > 0;

				// Batch mode: Process block in increments of batch size //
				if (useBatch) {
					logger.debug("Using Batch geodata client mode.");

					// Set batch size; increment outer loop index by this number each iteration //
					int increment = helper.getClientBatchLookupSize();
					helper.setBufferGeoDataBatchSize(increment, sessionID);

					boolean shortLastBlock = false;
					LoadableGeoDataClient clientInstance = helper.getClientInstance();
					GeoDataAddressConverter addressConverter = clientInstance.getFactory().getAddressConverter();
					GeoDataInputAddress[] inputAddresses = helper.getGeoDataInputAddressBlock(sessionID);

					// Loop until all addresses have been processed or an error is encountered //
					while (i < blockSize) {

						// First iteration: ensure increment is no larger than blockSize //
						// Last iteration: reduce increment if needed //
						if (increment > blockSize - i) {
							increment = blockSize - i;
							helper.setBufferGeoDataBatchSize(increment, sessionID);
							shortLastBlock = true;
						}

						// Assign input addresses //
						for (int j = 0; j < increment; j++) {
							helper.updateStatePostalCode(addressBlock[i + j]);
							inputAddresses[j] = addressConverter.convertInputToGeoData(addressBlock[i + j]);
						}

						// Short blocks on last iteration are smaller than client batch size; pass short array. 
						if (shortLastBlock) {
							GeoDataInputAddress[] shortArray = new GeoDataInputAddress[increment];

							// Set short array to buffer's contents //
							for (int j = 0; j < increment; j++) {
								shortArray[j] = inputAddresses[j];
							}
							inputAddresses = shortArray;  // local var. points to short array
						}

						// Perform address lookups //
						GeoDataResult[] geoDataResults = null;
						try {
							geoDataResults = clientInstance.lookupAddresses(inputAddresses);

							// Extract results //
							for (int j = 0; j < increment; j++) {
								resultBlock[i + j] = extractResult(sessionID, addressBlock[i + j],
										addressConverter, inputAddresses[j], geoDataResults[j]);

								geoDataBlock[i + j] = geoDataResults[j];
								resultProcessed[i + j] = Boolean.TRUE;
							}

							i += increment;  // increment counters
							minorIndex++;
						}
						catch (Exception e) {
							helper.addException(GeoCodingHelper.EXCEPTION_GEOCODER, helper.getBufferIndex(sessionID), minorIndex, sessionID);
							errorString = "Geodata exception at geoCodeAddressBlock() iteration " + minorIndex +
									" [i=" + i + "][PL[0]=" + fetchPostalLocatorUID(addressBlock[i]) + "]: " + e.toString();
							logger.fatal(errorString, e);

							// Update storage //
							for (int j = 0; j < increment; j++) {
								resultBlock[i + j] = null;
								geoDataBlock[i + j] = null;
								resultProcessed[i + j] = Boolean.FALSE;  // flag as NOT processed
							}
							helper.setBufferBlockSize(i + increment, sessionID);  // buffer block size is the # of flagged rows

							geoCodingException = e;
							break;  // LOOP EXIT
						}
					}
				}
				else {
					// Default: Process a single address at a time using geoCodeAddress() //
					logger.debug("Using single-address geodata client mode.");

					for (i = 0; i < blockSize; i++, minorIndex++) {
						try {
							resultBlock[i] = geoCodeAddress(sessionID, addressBlock[i], userId, false);
							resultProcessed[i] = Boolean.TRUE;
						}
						catch (Exception e) {
							errorString = "Geocoding exception at geoCodeAddressBlock() iteration " +
									minorIndex + "[PL=" + fetchPostalLocatorUID(addressBlock[i]) + "]: " + e.toString();
							logger.fatal(errorString, e);

							// Update storage //
							resultBlock[i] = null;
							resultProcessed[i] = Boolean.FALSE;
							helper.setBufferBlockSize(i + 1, sessionID);  // buffer block size is the # of flagged rows

							geoCodingException = e;
							break;  // LOOP EXIT
						}
					}
				}

				// Persist result block if applicable (whether or not exceptions were encountered) //
				if (persist) {
					persistenceException = persistGeoCodingResultBlock(sessionID, userId);
				}
			}
			catch (Exception e) {

				// Any exception caught here is truly unexpected and will not have a corresponding entry in the exception list
				errorString = "Unexpected exception in geoCodeAddressBlock() iteration " + minorIndex + ": " + e.toString();
				logger.fatal(errorString, e);
				result = e;
			}

			// Prepare/return result //
			if (result == null) {
				result = persistenceException != null ? persistenceException : geoCodingException;
			}
			return result;  // default: null
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Creates a batch log entry indicating a new batch has commenced.
	 * 
	 * @param sessionID
	 * @param isFullBatch
	 * @param startTime
	 * @return
	 * @throws Exception
	 */
	protected Long logBatchStarted(Long sessionID, Boolean isFullBatch, Timestamp startTime) throws Exception {

		Long uid = null;
		GeoCodingActivityLogDAOImpl dao = GeoCodingHelper.getInstance().getActivityLogDAO();

		// Ensure parameter is in range //
		if (isFullBatch == null) {
			throw new NEDSSGeoCodingException("Exception in logBatchStarted(): isFullBatch is null.");
		}

		// Log batch started //
		try {
			uid = dao.createNew(isFullBatch.booleanValue(), startTime);
		}
		catch (Exception e) {
			logger.fatal("Exception - cannot add batch activity log entry [" + uid + "]: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);  // abort
		}
		return uid;
	}

	/**
	 * Updates the specified batch log entry with relevant data and an indicator
	 * that the batch has completed.
	 * 
	 * @param sessionID
	 * @param uid
	 * @param endTime
	 * @param isCompleted
	 * @throws Exception
	 */
	protected void logBatchCompleted(Long sessionID, Long uid, Timestamp endTime, Boolean isCompleted) throws Exception {

		try{
		GeoCodingActivityLogDAOImpl dao = GeoCodingHelper.getInstance().getActivityLogDAO();

		// Ensure parameter is in range //
		if (isCompleted == null) {
			isCompleted = Boolean.FALSE;  // default to false
		}

		// Log batch completed //
		
			dao.updateFinal(uid, endTime, isCompleted);
		}
		catch (Exception e) {
			logger.fatal("Exception - cannot update batch activity log entry [" + uid + "]: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);  // abort
		}
	}

	/**
	 * Retrieves the (ending) timestamp of the most recent batch process to complete.
	 * 
	 * @param sessionID
	 * @return
	 * @throws Exception
	 */
	public Timestamp getLastBatchProcessTime(Long sessionID) throws Exception {
		
		Timestamp timestamp = null;
		GeoCodingActivityLogDAOImpl dao = GeoCodingHelper.getInstance().getActivityLogDAO();

		// Fetch last batch processed time //
		try {
			timestamp = dao.selectLastActivityTimestamp();
		}
		catch (Exception e) {
			logger.fatal("Exception - cannot get last batch process time: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e); // abort
		}
		return timestamp;
	}


	// Helpers //

	/**
	 * Extracts the user ID (i.e., Entry ID) from the indicated security object.
	 * 
	 * @param nbsSecurityObj
	 * @return
	 */
	private Long extractUserId(NBSSecurityObj nbsSecurityObj) {

		long userID = -1;
		String user = null;
		String entryID = null;

		try {
			user = nbsSecurityObj.getTheUserProfile().getTheUser().getUserID();
			entryID = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			if (entryID != null && entryID.length() > 0) {
				userID = Long.parseLong(entryID);
			}
		}
		catch (Exception e) {
			logger.fatal("User \"" + user + "\" does not exist - using null Entry ID: " +
					e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

		return userID >= 0 ? new Long(userID) : null;
	}

	/**
	 * Extracts result address from an array of potential address candidates;
	 * also populates calculated fields.
	 * 
	 * @param sessionID
	 * @param address
	 * @param addressConverter
	 * @param inputAddress
	 * @param geoDataResult
	 * @return
	 */
	private GeoCodingResultDT extractResult(Long sessionID, GeoCodingPostalLocatorDT address,
			GeoDataAddressConverter addressConverter,
			GeoDataInputAddress inputAddress, GeoDataResult geoDataResult) {

		try {
			Float nextScoreDiff = null;
			Integer nextScoreCount = null;

			GeoCodingResultDT result = null;

			int numMatches = geoDataResult.getNumOutputAddresses();

			// Handle multiple matches //
			if (geoDataResult.isMatchCountClassMulti()) {

				// Convert candidate addresses to output format //
				GeoCodingResultDT[] resultCandidates = new GeoCodingResultDT[numMatches];
				for (int i = 0; i < numMatches; i++) {
					resultCandidates[i] = (GeoCodingResultDT) addressConverter.convertGeoDataToOutput(
							geoDataResult.getOutputAddresses()[i]);
				}

				GeoCodingHelper helper = GeoCodingHelper.getInstance();

				// Populate match state //
				MultiMatchState multiMatchState = new MultiMatchState();
				for (int i = 0; i < numMatches; i++) {
					multiMatchState.addIndex(i, resultCandidates[i].getScore());
				}

				// Determine next-score information //
				multiMatchState.setMatchTieThreshold(helper.getMatchTieThreshold());
				nextScoreDiff = multiMatchState.getNextScoreDiff();
				nextScoreCount = multiMatchState.getNextScoreCount();

				// Select the applicable match //
				if (helper.isMultiMatchPolicyBestScore()) {

					// Take 1st top-match, if score available (if no scores returned, take 1st match)
					Integer bestScoreIndex = multiMatchState.getFirstIndexWithinMatchTieThreshold();
					result = resultCandidates[bestScoreIndex != null ? bestScoreIndex.intValue() : 0];
				}
				else if (helper.isMultiMatchPolicyFirstRecord()) {

					// Take 1st match, irrespective of score
					result = resultCandidates[0];
				}
				else if (helper.isMultiMatchPolicyZipCentroid()) {

					// Obtain zip-level geocode
					try {
						result = geoCodeZipCode(sessionID, inputAddress.getZip());  // recode for zip centroid
					}
					catch (Exception e) {
						logger.fatal(e.getMessage(), e);
					}
				}
				// else - helper.isMatchTiePolicyEmpty() - result candidate is null
			}

			// Handle single matches //
			else if (geoDataResult.isMatchCountClassSingle()) {

				// Take 1st & only result candidate
				result = (GeoCodingResultDT) addressConverter.convertGeoDataToOutput(
						geoDataResult.getOutputAddresses()[0]);

				// nextScoreDiff is null, and
				nextScoreCount = MultiMatchState.zeroCount;
				numMatches = 1;  // just to be sure
			}

			// Handle zero matches //
			else {  // if (geoDataResult.isMatchCountClassZero())

				// Apply those fields that were received from the geocoder
				if (numMatches > 0) {
					result = (GeoCodingResultDT) addressConverter.convertGeoDataToOutput(
							geoDataResult.getOutputAddresses()[0]);

					// nextScoreDiff and nextScoreCount are null 
				}
				else {
					result = new GeoCodingResultDT();
				}
				numMatches = 0;  // override # of matches
			}

			// All match count classes: Assign UID, status members //
			result.setPostalLocatorUid(address.getPostalLocatorUid());
			result.setResultType(geoDataResult.getResultType());
			result.setNumMatches(new Integer(numMatches));
			result.setNextScoreDiff(nextScoreDiff);
			result.setNextScoreCount(nextScoreCount);
			result.setEntityClassCd(address.getEntityClassCd());
			result.setEntityUid(address.getEntityUid());

			// Exit //
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the specified batch completion status object with totals extracted
	 * from the indicated result block.
	 * 
	 * @param sessionID
	 */
	private void updateBatchStatus(Long sessionID) {

		try {
			int numTotal = 0, numMultiMatches = 0, numSingleMatches = 0, numZeroMatches = 0,
					numErrorRecords = 0, numFatalSkipped = 0;

			GeoCodingHelper helper = GeoCodingHelper.getInstance();
			int blockSize = helper.getBufferBlockSize(sessionID);

			GeoCodingResultDT[] resultBlock = helper.getGeoCodingResultBlock(sessionID);
			Boolean[] resultProcessed = helper.getResultProcessed(sessionID);
			BatchCompletionStatus status = helper.getBatchCompletionStatus(sessionID);

			// Count match count class totals //
			for (int i = 0; i < blockSize; i++) {
				if (Boolean.TRUE.equals(resultProcessed[i])) {
					int numMatches = resultBlock[i].getNumMatches().intValue();
					if (numMatches > 1) {
						numMultiMatches++;
					}
					else if (numMatches == 1) {
						numSingleMatches++;
					}
					else {  // numMatches == 0
						numZeroMatches++;
					}
					numTotal++;
				}
				else if (Boolean.FALSE.equals(resultProcessed[i])) {
					numErrorRecords++;  // in case any unexpected exceptions occurred during processing
					numTotal++;
				}
				else {  // resultProcessed[i] == null
					numFatalSkipped++;
					// do not count toward total processed
				}
			}

			// Set Exception String //
			status.setCompletionReason(helper.getExceptionString(sessionID));  // is being accumulated anyhow, so overwrite it

			// Set totals //
			status.setNumTotal(status.getNumTotal() + numTotal);
			status.setNumMultiMatches(status.getNumMultiMatches() + numMultiMatches);
			status.setNumSingleMatches(status.getNumSingleMatches() + numSingleMatches);
			status.setNumZeroMatches(status.getNumZeroMatches() + numZeroMatches);
			status.setNumErrorRecords(status.getNumErrorRecords() + numErrorRecords);
			status.setNumFatalSkipped(status.getNumFatalSkipped() + numFatalSkipped);
			status.setNumExceptions(helper.getNumExceptions(sessionID));  // is being accumulated, so overwrite w/updated value
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Uses geoCodeAddress() to look up geocoding information for the indicated Zip code.
	 * 
	 * @param sessionID
	 * @param zipCode
	 * @return
	 * @throws NEDSSGeoCodingException
	 */
	protected GeoCodingResultDT geoCodeZipCode(Long sessionID, String zipCode) throws NEDSSGeoCodingException {

		try {
			GeoCodingPostalLocatorDT zipAddress = new GeoCodingPostalLocatorDT();
			zipAddress.setZipCd(zipCode);

			return geoCodeAddress(sessionID, zipAddress, null, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the specified batch completion status object with totals extracted
	 * from the indicated result block; writes updated totals to the database.
	 * 
	 * @param sessionID
	 */
	protected void updateActivityLog(Long sessionID) {

		
		GeoCodingHelper helper = GeoCodingHelper.getInstance();
		GeoCodingActivityLogDAOImpl dao = helper.getActivityLogDAO();
		try{
		// Build activity log udpate entry //
		GeoCodingActivityLogDT entry = new GeoCodingActivityLogDT();
		entry.setGeoCodingActivityLogUid(helper.getActivityLogUid(sessionID));
		helper.setActivityLogFromStatus(entry, helper.getBatchCompletionStatus(sessionID));

		// Update activity log //
		
			dao.updateStatus(entry);
		}
		catch (Exception e) {
			helper.addException(GeoCodingHelper.EXCEPTION_UPDATE_ACTIVITY_LOG, helper.getBufferIndex(sessionID), -1, sessionID);
			logger.fatal("Exception - cannot update activity log entry: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
			// do not rethrow e
		}
	}

	/**
	 * Returns the postal locator UID of the specified Postal Locator (or <code>null</code>
	 * if not specified).
	 * 
	 * @param dt
	 * @return
	 */
	protected Long fetchPostalLocatorUID(GeoCodingPostalLocatorDT dt) {
		return (dt != null) ? dt.getPostalLocatorUid() : null;
	}

	/**
	 * Returns the postal locator UID of the specified Geocoding Result (or <code>null</code>
	 * if not specified).
	 * 
	 * @param dt
	 * @return
	 */
	protected Long fetchPostalLocatorUID(GeoCodingResultDT dt) {
		return (dt != null) ? dt.getPostalLocatorUid() : null;
	}

	/**
	 * Persists a geocoding result object.
	 * 
	 * @param sessionID
	 * @param result
	 * @param userId
	 * @return
	 * @throws NEDSSGeoCodingException Unable to persist result, but successfully flagged PL record as 'E'
	 * @throws NEDSSSystemException Unable to persist either result or 'E' in PL - indicates a more serious system condition
	 */
	protected Long persistGeoCodingResult(Long sessionID, GeoCodingResultDT result, Long userId)
			throws NEDSSGeoCodingException, NEDSSSystemException {

		Long uid = null;
		String errorString = null;

		GeoCodingHelper helper = GeoCodingHelper.getInstance();
		GeoCodingResultDAOImpl geoDAO = helper.getGeoCodingResultDAO();
		PostalLocatorDAOImpl plDAO = helper.getPostalLocatorDAO();

		try {
			uid = geoDAO.store(result, userId);
		}
		catch (Exception e) {

			Long plUID = fetchPostalLocatorUID(result);
			helper.addException(GeoCodingHelper.EXCEPTION_PERSIST_GR, helper.getBufferIndex(sessionID), -1, sessionID);
			errorString = "Exception 1 in persistResult() [" + helper.getBufferIndex(sessionID) +
					"][PL=" + plUID + "]: " + e.toString();
			logger.fatal(errorString, e);
			// attempt to continue

			// Flag postal locator as error; failure here means we must abort altogether // 
			try {
				plDAO.setGeocodeMatchIndicatorError(plUID.longValue());
			}
			catch (Exception e1) {
				helper.addException(GeoCodingHelper.EXCEPTION_FLAG_PL_ERROR_SQL, helper.getBufferIndex(sessionID), -1, sessionID);
				errorString = "Exception 2 in persistResult() [" + helper.getBufferIndex(sessionID) +
						"][PL=" + plUID + "]: " + e.toString();
				logger.fatal(errorString, e1);

				// Not being able to flag a SQL error w/a PL update is fatal to the geocoding process
				if (e1 instanceof NEDSSSystemException) {
					logger.fatal(e1.getMessage(), e1);
					throw (NEDSSSystemException) e1;
				}
				throw (NEDSSSystemException) new NEDSSSystemException(errorString).initCause(e1);
			}

			// Throw checked exception to indicate inability to persist result, but success in flagging as 'E' //
			if (e instanceof NEDSSGeoCodingException) {
				logger.fatal(e.getMessage(), e);
				throw (NEDSSGeoCodingException) e;
			}
			logger.fatal(e.getMessage(), e);
			throw (NEDSSGeoCodingException) new NEDSSGeoCodingException(errorString).initCause(e);
		}
		return uid;
	}

	/**
	 * Persists a block of geocoding result objects.
	 * 
	 * @param sessionID
	 * @param userId
	 * @return Exception
	 */
	protected Exception persistGeoCodingResultBlock(Long sessionID, Long userId) {

		try {
			GeoCodingHelper helper = GeoCodingHelper.getInstance();
			GeoCodingResultDAOImpl geoDAO = helper.getGeoCodingResultDAO();
			PostalLocatorDAOImpl plDAO = helper.getPostalLocatorDAO();

			Long plUID = null;
			String errorString = null;
			String flagErrorExceptionType = null;  // exception type to use in case of a problem while flagging a PL
			Exception fatalException = null;

			// Iterate through block; fail only if we cannot write to the postal locator table //
			int i;
			for (i = 0; i < helper.getBufferBlockSize(sessionID); i++) {
				flagErrorExceptionType = null;  // reset
				plUID = null;

				if (Boolean.FALSE.equals(helper.getResultProcessed(sessionID)[i])) {
					flagErrorExceptionType = GeoCodingHelper.EXCEPTION_FLAG_PL_ERROR_GEO;  // flag a geocoder error
				}
				else if (Boolean.TRUE.equals(helper.getResultProcessed(sessionID)[i])) {
					try {
						plUID = fetchPostalLocatorUID(helper.getGeoCodingResultBlock(sessionID)[i]);
						geoDAO.store(helper.getGeoCodingResultBlock(sessionID)[i], userId);
					}
					catch (Exception e) {
						flagErrorExceptionType = GeoCodingHelper.EXCEPTION_FLAG_PL_ERROR_SQL;  // flag a SQL error
						helper.getResultProcessed(sessionID)[i] = Boolean.FALSE;

						helper.addException(GeoCodingHelper.EXCEPTION_PERSIST_GR, helper.getBufferIndex(sessionID), i, sessionID);
						errorString = "Exception 1 in persistResultBlock() [" + helper.getBufferIndex(sessionID) +
								"/" + i + "/" + helper.getResultProcessed(sessionID)[i] + "][PL=" + plUID + "]: " + e.toString();
						logger.fatal(errorString, e);

						// attempt to continue
					}
				}

				// Flag selected postal locator as error; failure here means we must abort altogether // 
				if (flagErrorExceptionType != null) {
					try {
						plUID = fetchPostalLocatorUID(helper.getPostalLocatorBlock(sessionID)[i]);
						plDAO.setGeocodeMatchIndicatorError(plUID.longValue());
					}
					catch (Exception e) {
						helper.getResultProcessed(sessionID)[i] = null;  // indicates neither successful store nor 'E'

						helper.addException(flagErrorExceptionType, helper.getBufferIndex(sessionID), i, sessionID);
						errorString = "Exception 2 in persistResultBlock() [" + helper.getBufferIndex(sessionID) +
								"/" + i + "/" + helper.getResultProcessed(sessionID)[i] + "][PL=" + plUID + "]: " + e.toString();
						logger.fatal(errorString, e);

						// Not being able to flag a SQL error w/a PL update is fatal to the geocoding process
						fatalException = e;
						break;  // LOOP EXIT - 'i' remains index of record w/fatal exception
					}
				}
			} // for

			// If fatal DB error(s) encountered while flagging 'E', clear remaining records in block //
			if (fatalException != null) {

				logger.fatal("Skipping " + (helper.getBufferBlockSize(sessionID) - i) + " geocoded records in Block " +
						helper.getBufferIndex(sessionID) + " because persistence error(s) prevent updating status information.");

				for ( ; i < helper.getBufferBlockSize(sessionID); i++) {
//				helper.getGeoCodingResultBlock(sessionID)[i] = null;  // clearing these is not needed
//				helper.getGeoDataResultBlock(sessionID)[i] = null;  // clearing these is not needed
					helper.getResultProcessed(sessionID)[i] = null;  // flag as not processed AND not flaggable with 'E'
				}
			}

			// Exit //
			return fatalException;  // default: null
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
}