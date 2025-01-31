package gov.cdc.nedss.geocoding.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import gov.cdc.nedss.geocoding.dao.GeoCodingResultDAOImpl;
import gov.cdc.nedss.geocoding.dao.GeoCodingResultHistDAOImpl;
import gov.cdc.nedss.geocoding.dt.GeoCodingPostalLocatorDT;
import gov.cdc.nedss.geocoding.dt.GeoCodingResultDT;
import gov.cdc.nedss.geocoding.ejb.geocodingservice.dao.GeoCodingActivityLogDAOImpl;
import gov.cdc.nedss.geocoding.exception.NEDSSGeoCodingException;
import gov.cdc.nedss.geocoding.geodata.GeoDataClient;
import gov.cdc.nedss.geocoding.geodata.GeoDataInputAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataResult;
import gov.cdc.nedss.geocoding.geodata.LoadableGeoDataClient;
import gov.cdc.nedss.locator.dao.PostalLocatorDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * Geocoding Helper class.
 * 
 * @author rdodge
 *
 */
public class GeoCodingHelper
{
	// Constants //
	public static final String BLOCK_SIZE = "geocoder.blockSize";
	public static final String RETRY_ERRORS = "geocoder.retryErrors";
	public static final String MULTI_MATCH_POLICY = "geocoder.multiMatchPolicy";
	public static final String MATCH_TIE_THRESHOLD = "geocoder.matchTieThreshold";

	public static final String CLIENT_BATCH_LOOKUP = LoadableGeoDataClient.GEODATA_PREFIX
			+ "." + GeoDataClient.DEFAULT_CLIENT_TYPE + ".batchLookup";
	public static final String CLIENT_BATCH_LOOKUP_SIZE = CLIENT_BATCH_LOOKUP + "Size";

	public static final String MULTI_MATCH_POLICY_EMPTY = "empty";
	public static final String MULTI_MATCH_POLICY_BEST_SCORE = "best";
	public static final String MULTI_MATCH_POLICY_FIRST_RECORD = "first";
	public static final String MULTI_MATCH_POLICY_ZIP_CENTROID = "zip";

	public static final String EXCEPTION_CLEAR_INDS = "Clear Postal Locator Indicators";
	public static final String EXCEPTION_CLEAR_ERROR_INDS = "Clear Postal Locator Errors";
	public static final String EXCEPTION_CLEAR_RECORD_STATUS = "Clear Record Status";
	public static final String EXCEPTION_FETCH_PLS = "Fetch Postal Locators";
	public static final String EXCEPTION_GEOCODER = "Geocoder";
	public static final String EXCEPTION_PERSIST_GR = "Persist Result";
	public static final String EXCEPTION_PERSIST_GRH = "Persist Result Hist";
	public static final String EXCEPTION_UPDATE_PL = "Update Postal Locator";
	public static final String EXCEPTION_FLAG_PL_ERROR_GEO = "Flag Postal Locator for Geo Error";
	public static final String EXCEPTION_FLAG_PL_ERROR_SQL = "Flag Postal Locator for SQL Error";
	public static final String EXCEPTION_UPDATE_ACTIVITY_LOG = "Update Activity Log";

	public static final Integer singleResult = new Integer(1);

	// Members //
	static final LogUtils logger = new LogUtils(GeoCodingHelper.class.getName());

	/** Cache used to look up state abbreviations from codes. */ 
	private static CachedDropDownValues srtc = new CachedDropDownValues();

	/** Single instance. */
	private static GeoCodingHelper instance;

	/** Global session registry. */
	private static SessionRegistry sessionRegistry = new SessionRegistry();

	private ThreadLocalStorageAccessor tlsAccessor;

	private LoadableGeoDataClient clientInstance;
	private Exception clientInstantiationException;

	private PostalLocatorDAOImpl postalLocatorDAO;
	private GeoCodingResultDAOImpl geoCodingResultDAO;
	private GeoCodingResultHistDAOImpl geoCodingResultHistDAO;
	private GeoCodingActivityLogDAOImpl activityLogDAO;

	private int blockSize = 1;
	private boolean retryErrors;
	private String multiMatchPolicy = MULTI_MATCH_POLICY_BEST_SCORE;
	private float matchTieThreshold;

	private boolean clientBatchLookup;
	private int clientBatchLookupSize;

	/** Static inner class implementing session registry. */
	private static class SessionRegistry {
		private static long nextSessionID = 0;
		private static Map<Object,Object> registry = new HashMap<Object,Object>();
		public synchronized Long newSession(GeoCodingHelper instance) {
			nextSessionID++;
			Long sessionID = new Long(nextSessionID);
			registry.put(sessionID, instance.newStorage());
			return sessionID;
		}
		public synchronized void finalizeSession(Long sessionID) {
			registry.remove(sessionID);
		}
		public synchronized SessionStorage getSession(Long sessionID) {
			return (SessionStorage) registry.get(sessionID);
		}
	}

	/** Thread-local storage accessor.  Wrapper around <code>ThreadLocal</code>. */
	private class ThreadLocalStorageAccessor extends ThreadLocal {
		public Object initialValue() {
			return new SessionStorage(blockSize, clientBatchLookupSize);
		}
		public SessionStorage getStorage() {
			return (SessionStorage) super.get();
		}
	}

	/** Static inner class representing session storage. */
	public static class SessionStorage {

		private int initialBlockSize;  // corresponds to outer's blockSize
		private int blockSize;
		private int geoDataBatchSize;

		private int currentBlockIndex;
		private int currentBlockSize;
		private int currentGeoDataBatchSize;

		private GeoCodingPostalLocatorDT[] postalLocatorBlock;
		private GeoDataResult[] geoDataResultBlock;
		private GeoCodingResultDT[] geoCodingResultBlock;
		private Boolean[] resultProcessed;

		private GeoDataInputAddress[] inputAddressBlock;

		private Long activityLogUID;
		private List<Object> exceptionList;
		private BatchCompletionStatus batchCompletionStatus;

		/** Constructor. */
		private SessionStorage(int blockSize, int geoDataBatchSize) {

			batchCompletionStatus = new BatchCompletionStatus();
			exceptionList = new ArrayList<Object> ();
			initialBlockSize = blockSize;
			init(true, blockSize, geoDataBatchSize, null);
		}

		/** Performs soft initialization (not for ad hoc address blocks). */
		public void init() {
			init(blockSize < initialBlockSize, initialBlockSize, geoDataBatchSize, null);  // resize iff too small
		}

		/** Performs soft initialization (for ad hoc address blocks). */
		public void init(GeoCodingPostalLocatorDT[] addressBlock) {
			init(true, addressBlock.length, geoDataBatchSize, addressBlock);
		}

		/**
		 * Initializes session storage for either hard or soft initialization.
		 * 
		 * @param resizeBlock Indicates type of initialization
		 * @param blockSize
		 * @param geoDataBatchSize
		 * @param addressBlock  <code>null</code> unless processing ad hoc address block
		 */
		private void init(boolean resizeBlock, int blockSize, int geoDataBatchSize,
				GeoCodingPostalLocatorDT[] addressBlock) {

			batchCompletionStatus.clear();
			exceptionList.clear();

			// Only done in the constructor, when processing an ad hoc address block,
			// or when cycling from an ad hoc address block (of smaller block size)
			// back to regular batch mode.
			if (resizeBlock) {
				this.blockSize = blockSize;
				this.geoDataBatchSize = geoDataBatchSize;

				if (postalLocatorBlock == null || postalLocatorBlock.length != blockSize) {

					if (addressBlock == null) {
						postalLocatorBlock = new GeoCodingPostalLocatorDT[blockSize];  // default
					}
					else {
						postalLocatorBlock = addressBlock;  // ad hoc address block
					}
				}
				if (geoDataResultBlock == null || geoDataResultBlock.length != blockSize)
					geoDataResultBlock = new GeoDataResult[blockSize];
				if (geoCodingResultBlock == null || geoCodingResultBlock.length != blockSize)
					geoCodingResultBlock = new GeoCodingResultDT[blockSize];
				if (resultProcessed == null || resultProcessed.length != blockSize)
					resultProcessed = new Boolean[blockSize];

				if (inputAddressBlock == null || inputAddressBlock.length != geoDataBatchSize)
					inputAddressBlock = new GeoDataInputAddress[geoDataBatchSize];
			}

			clearBuffers();  // clear buffers
		}
		public void clearBuffers() {
			clearBlock();
			clearInputAddressBlock();
		}
		public void clearBlock() {
			for (int i = 0; i < blockSize; i++) {
				clearRow(i);
			}
			currentBlockSize = 0;
			currentBlockIndex = -1;
		}
		public void clearRow(int index) {
			postalLocatorBlock[index] = null;
			geoDataResultBlock[index] = null;
			geoCodingResultBlock[index] = null;
			resultProcessed[index] = null;
		}
		public void clearInputAddressRow(int index) {
			inputAddressBlock[index] = null;
		}
		public void clearInputAddressBlock() {
			for (int i = 0; i < geoDataBatchSize; i++) {
				clearInputAddressRow(i);
			}
			currentGeoDataBatchSize = 0;
		}
	}

	/** Static inner class representing a problem encountered. */
	public static class ExceptionEntry {
		private String exceptionType;
		private int majorIndex;
		private int minorIndex;

		/** Constructor. */
		public ExceptionEntry(String exceptionType, int majorIndex, int minorIndex) {
			this.exceptionType = exceptionType;
			this.majorIndex = majorIndex;
			this.minorIndex = minorIndex;
		}
		public String getExceptionType() { return exceptionType; }
		public int getMajorIndex() { return majorIndex; }
		public int getMinorIndex() { return minorIndex; }

		public String toString() {
			return exceptionType + " [" +
					(majorIndex >= 0 ? "Block " + majorIndex : "") +
					(minorIndex >= 0 ? "/" + minorIndex : "") + "]";
		}
	}

	/** Private default constructor. */
	private GeoCodingHelper() {

		Properties properties = GeoCodingUtils.getNEDSSProperties();
		if (properties != null) {
			blockSize = GeoCodingUtils.getIntegerProperty(properties, BLOCK_SIZE, blockSize);
			retryErrors = GeoCodingUtils.getBooleanProperty(properties, RETRY_ERRORS, retryErrors);
			matchTieThreshold = GeoCodingUtils.getFloatProperty(
					properties, MATCH_TIE_THRESHOLD, matchTieThreshold);
			multiMatchPolicy = properties.getProperty(MULTI_MATCH_POLICY, multiMatchPolicy);

			clientBatchLookup = GeoCodingUtils.getBooleanProperty(
					properties, CLIENT_BATCH_LOOKUP, clientBatchLookup);
			clientBatchLookupSize = GeoCodingUtils.getIntegerProperty(
					properties, CLIENT_BATCH_LOOKUP_SIZE, clientBatchLookupSize);

			clientInstance = new LoadableGeoDataClient();

			postalLocatorDAO = (PostalLocatorDAOImpl) NEDSSDAOFactory.getDAO(
					JNDINames.POSTAL_LOCATOR_DAO_CLASS);
			geoCodingResultDAO = (GeoCodingResultDAOImpl) NEDSSDAOFactory.getDAO(
					JNDINames.GEOCODING_RESULT_DAO_CLASS);
			geoCodingResultHistDAO = (GeoCodingResultHistDAOImpl) NEDSSDAOFactory.getDAO(
					JNDINames.GEOCODING_RESULT_HIST_DAO_CLASS);
			activityLogDAO = (GeoCodingActivityLogDAOImpl) NEDSSDAOFactory.getDAO(
					JNDINames.GEOCODING_ACTIVITYLOGDAO_CLASS);

			// Add thread-local storage //
//			tlsAccessor = new ThreadLocalStorageAccessor();
		}
	}

	/** Returns shared instance.  @return */
	public static synchronized GeoCodingHelper getInstance() {

		if (instance == null) {
			instance = new GeoCodingHelper();

			// Initialize Client //
			instance.initClientInstance();
		}
		return instance;
	}


	// Geodata Client Support //

	/** Initializes geodata client instance. */
	protected synchronized void initClientInstance() {

		try {
			getClientInstance().init(GeoCodingUtils.getNEDSSProperties(),
					GeoDataClient.DEFAULT_CLIENT_TYPE);

			instance.clientInstantiationException = null;
		}
		catch (Exception e) {
			logger.fatal("Cannot instantiate geodata client: " + GeoCodingUtils.getStackTrace(e));
			instance.clientInstantiationException = e;
		}
	}

	/**
	 * Ensures geodata client is initialized, if possible.
	 * 
	 * If initialization fails, the log records the error and <code>isInitialized()</code>
	 * returns <code>false</code>, and subsequent geodata method calls will fail appropriately.
	 */
	public void ensureClientIsInitialized() {
		try {
			if (!getClientInstance().isInitialized()) {

				// Initialize geodata client // 
				synchronized(this) {
					if (!getClientInstance().isInitialized()) {  // re-check to be sure
						initClientInstance();
					}
				}

				// Handle initialization failure //
				if (instance.clientInstantiationException != null) {
					logger.fatal("Cannot instantiate geodata client - exception: " +
							instance.clientInstantiationException.toString(),
							instance.clientInstantiationException);
				}
			}
		}
		catch (Exception e) {
			logger.fatal("Cannot ensure geodata client is initialized - exception: " + e.toString(), e);
		}
	}


	// Accessors / Mutators //

	/** Returns the geodata client instance.  @return */
	public LoadableGeoDataClient getClientInstance() throws Exception {

		// Throw instantiation exception if initialization failed
		if (clientInstance == null) {
			throw new NEDSSGeoCodingException("GeoDataClient instance is null.");
		}
		return clientInstance;
	}

	/** Sets the geodata client instance.  @param clientInstance */
	protected void setClientInstance(LoadableGeoDataClient clientInstance) {
		this.clientInstance = clientInstance;
	}

	/** Returns the postal locator DAO.  @return */
	public PostalLocatorDAOImpl getPostalLocatorDAO() {
		return postalLocatorDAO;
	}

	/** Sets the postal locator DAO.  @param postalLocatorDAO */
	protected void setPostalLocatorDAO(PostalLocatorDAOImpl postalLocatorDAO) {
		this.postalLocatorDAO = postalLocatorDAO;
	}

	/** Returns the geocoding result DAO.  @return */
	public GeoCodingResultDAOImpl getGeoCodingResultDAO() {
		return geoCodingResultDAO;
	}

	/** Sets the geocoding result DAO.  @param geoCodingResultDAO */
	protected void setGeoCodingResultDAO(GeoCodingResultDAOImpl geoCodingResultDAO) {
		this.geoCodingResultDAO = geoCodingResultDAO;
	}

	/** Returns the geocoding result history DAO.  @return */
	public GeoCodingResultHistDAOImpl getGeoCodingResultHistDAO() {
		return geoCodingResultHistDAO;
	}

	/** Sets the geocoding result history DAO.  @param geoCodingResultHistDAO */
	protected void setGeoCodingResultHistDAO(GeoCodingResultHistDAOImpl geoCodingResultHistDAO) {
		this.geoCodingResultHistDAO = geoCodingResultHistDAO;
	}

	/** Returns the geocoding activity log DAO.  @return */
	public GeoCodingActivityLogDAOImpl getActivityLogDAO() {
		return activityLogDAO;
	}

	/** Sets the geocoding activity log DAO.  @param activityLogDAO */
	protected void setActivityLogDAO(GeoCodingActivityLogDAOImpl activityLogDAO) {
		this.activityLogDAO = activityLogDAO;
	}


	/** Returns the block size (default: zero).  @return */
	public int getBlockSize() {
		return blockSize;
	}

	/** Sets the block size.  @param blockSize */
	protected void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	/** Indicates whether postal locators with geocoding errors are retried (default: false).  @return */
	public boolean getRetryErrors() {
		return retryErrors;
	}

	/** Sets the indicator governing retry of postal locators with geocoding errors.  @param retryErrors */
	protected void setRetryErrors(boolean retryErrors) {
		this.retryErrors = retryErrors;
	}


	/** Returns the multi-match policy (default: "best").  @return */
	public String getMultiMatchPolicy() {
		return multiMatchPolicy;
	}

	/** Determines if the multi-match policy is to return only status information (i.e., no geocode).  @return */
	public boolean isMultiMatchPolicyEmpty() {
		return MULTI_MATCH_POLICY_EMPTY.equals(getMultiMatchPolicy());
	}

	/** Determines if the multi-match policy is to use the [first] record with the highest score.  @return */
	public boolean isMultiMatchPolicyBestScore() {
		return MULTI_MATCH_POLICY_BEST_SCORE.equals(getMultiMatchPolicy());
	}

	/** Determines if the multi-match policy is to use the first record returned.  @return */
	public boolean isMultiMatchPolicyFirstRecord() {
		return MULTI_MATCH_POLICY_FIRST_RECORD.equals(getMultiMatchPolicy());
	}

	/** Determines if the multi-match policy is to return a geocode based on the zip centroid.  @return */
	public boolean isMultiMatchPolicyZipCentroid() {
		return MULTI_MATCH_POLICY_ZIP_CENTROID.equals(getMultiMatchPolicy());
	}


	/** Sets the multi-match policy.  @param multiMatchPolicy */
	public void setMultiMatchPolicy(String multiMatchPolicy) {
		this.multiMatchPolicy = multiMatchPolicy;
	}

	/** Sets the multi-match policy to return only status information (i.e., no geocode). */
	public void setMultiMatchPolicyEmpty() {
		setMultiMatchPolicy(MULTI_MATCH_POLICY_EMPTY);
	}

	/** Sets the multi-match policy to use the [first] record with the highest score. */
	public void setMultiMatchPolicyBestScore() {
		setMultiMatchPolicy(MULTI_MATCH_POLICY_BEST_SCORE);
	}

	/** Sets the multi-match policy to use the first record returned. */
	public void setMultiMatchPolicyFirstRecord() {
		setMultiMatchPolicy(MULTI_MATCH_POLICY_FIRST_RECORD);
	}

	/** Sets the multi-match policy to return a geocode based on the zip centroid. */
	public void setMultiMatchPolicyZipCentroid() {
		setMultiMatchPolicy(MULTI_MATCH_POLICY_ZIP_CENTROID);
	}

	/** Returns the match tie threshold (default: <code>0.0</code>).  @return */
	public float getMatchTieThreshold() {
		return matchTieThreshold;
	}

	/** Sets the match tie threshold.  @param matchTieThreshold */
	public void setMatchTieThreshold(float matchTieThreshold) {
		this.matchTieThreshold = matchTieThreshold;
	}


	/** Indicates whether the client will perform batch lookup of addresses (default: <code>false</code>).  @return */
	public boolean isClientBatchLookup() {
		return clientBatchLookup;
	}

	/** Sets the indicator for client batch lookup of addresses.  @param clientBatchLookup */
	protected void setClientBatchLookup(boolean clientBatchLookup) {
		this.clientBatchLookup = clientBatchLookup;
	}

	/** Returns the client batch lookup size (default: zero).  @return */
	public int getClientBatchLookupSize() {
		return clientBatchLookupSize;
	}

	/** Sets the client batch lookup size.  @param clientBatchLookupSize */
	protected void setClientBatchLookupSize(int clientBatchLookupSize) {
		this.clientBatchLookupSize = clientBatchLookupSize;
	}


	// Session Storage Helpers //

	/** Requests new session in global registry.  @return */
	public Long newSession() {
		return sessionRegistry.newSession(this);
	}

	/** Removes session from global registry.  @param sessionID @return */
	public void finalizeSession(Long sessionID) {
		sessionRegistry.finalizeSession(sessionID);
	}

	/** Returns storage for the indicated global registry session.  @param sessionID @return */
	public SessionStorage getSessionFromRegistry(Long sessionID) {
		return sessionRegistry.getSession(sessionID);
	}


	/** Returns a new session storage object instance. */
	public SessionStorage newStorage() {
		return new SessionStorage(blockSize, clientBatchLookupSize);
	}

	/** [Re]Initializes session storage. */
	public void initStorage() {
		initStorage(getSessionStorage());
	}
	public void initStorage(Long sessionID) {
		initStorage(getSessionFromRegistry(sessionID));
	}
	public void initStorage(SessionStorage storage) {
		storage.init();
	}

	/** Clears session storage buffers. */
	public void clearStorageBuffers() {
		clearStorageBuffers(getSessionStorage());
	}
	public void clearStorageBuffers(Long sessionID) {
		clearStorageBuffers(getSessionFromRegistry(sessionID));
	}
	public void clearStorageBuffers(SessionStorage storage) {
		storage.clearBuffers();
	}

	/**
	 * Initializes session storage with the specified ad hoc address block.
	 * 
	 * @param addressBlock
	 */
	public void initStorageWithAddressBlock(GeoCodingPostalLocatorDT[] addressBlock) {
		initStorageWithAddressBlock(addressBlock, getSessionStorage());
	}
	public void initStorageWithAddressBlock(GeoCodingPostalLocatorDT[] addressBlock, Long sessionID) {
		initStorageWithAddressBlock(addressBlock, getSessionFromRegistry(sessionID));
	}
	public void initStorageWithAddressBlock(GeoCodingPostalLocatorDT[] addressBlock, SessionStorage storage) {
		storage.init(addressBlock);
	}


	/** Returns session batch completion status.  @return */
	public BatchCompletionStatus getBatchCompletionStatus() {
		return getBatchCompletionStatus(getSessionStorage());
	}
	public BatchCompletionStatus getBatchCompletionStatus(Long sessionID) {
		return getBatchCompletionStatus(getSessionFromRegistry(sessionID));
	}
	public BatchCompletionStatus getBatchCompletionStatus(SessionStorage storage) {
		return storage.batchCompletionStatus;
	}

	/** Returns session exception list.  @return */
	public List<Object> getExceptionList() {
		return getExceptionList(getSessionStorage());
	}
	public List<Object> getExceptionList(Long sessionID) {
		return getExceptionList(getSessionFromRegistry(sessionID));
	}
	public List<Object> getExceptionList(SessionStorage storage) {
		return storage.exceptionList;
	}

	/** Returns session exception list as a string.  @return */
	public String getExceptionString() {
		return getExceptionString(getSessionStorage());
	}
	public String getExceptionString(Long sessionID) {
		return getExceptionString(getSessionFromRegistry(sessionID));
	}
	public String getExceptionString(SessionStorage storage) {

		StringBuffer sb = new StringBuffer();

		for (Iterator<Object> it = getExceptionList(storage).iterator(); it.hasNext(); ) {
			sb.append(it.next().toString()).append("; ");
		}
		return sb.toString();
	}

	/** Returns session number of exceptions.  @return */
	public int getNumExceptions() {
		return getNumExceptions(getSessionStorage());
	}
	public int getNumExceptions(Long sessionID) {
		return getNumExceptions(getSessionFromRegistry(sessionID));
	}
	public int getNumExceptions(SessionStorage storage) {
		return getExceptionList(storage).size();
	}

	/**
	 * Records the indicated geocoding exception.
	 * 
	 * @param type
	 * @param majorIndex (use -1 to indicate that major index is not applicable)
	 * @param minorIndex (use -1 to indicate that minor index is not applicable)
	 */
	public void addException(String type, int majorIndex, int minorIndex) {
		addException(type, majorIndex, minorIndex, getSessionStorage());
	}
	public void addException(String type, int majorIndex, int minorIndex, Long sessionID) {
		addException(type, majorIndex, minorIndex, getSessionFromRegistry(sessionID));
	}
	public void addException(String type, int majorIndex, int minorIndex, SessionStorage storage) {
		getExceptionList(storage).add(new ExceptionEntry(type, majorIndex, minorIndex));
	}

	/** Gets/sets session activity log UID.  @return */
	public Long getActivityLogUid() {
		return getActivityLogUid(getSessionStorage());
	}
	public Long getActivityLogUid(Long sessionID) {
		return getActivityLogUid(getSessionFromRegistry(sessionID));
	}
	public Long getActivityLogUid(SessionStorage storage) {
		return storage.activityLogUID;
	}
	public void setActivityLogUid(Long activityLogUID) {
		setActivityLogUid(activityLogUID, getSessionStorage());
	}
	public void setActivityLogUid(Long activityLogUID, Long sessionID) {
		setActivityLogUid(activityLogUID, getSessionFromRegistry(sessionID));
	}
	public void setActivityLogUid(Long activityLogUID, SessionStorage storage) {
		storage.activityLogUID = activityLogUID;
	}

	public int getBufferIndex() {
		return getBufferIndex(getSessionStorage());
	}
	public int getBufferIndex(Long sessionID) {
		return getBufferIndex(getSessionFromRegistry(sessionID));
	}
	public int getBufferIndex(SessionStorage storage) {
		return storage.currentBlockIndex;
	}
	public void setBufferIndex(int index) {
		setBufferIndex(index, getSessionStorage());
	}
	public void setBufferIndex(int index, Long sessionID) {
		setBufferIndex(index, getSessionFromRegistry(sessionID));
	}
	public void setBufferIndex(int index, SessionStorage storage) {
		storage.currentBlockIndex = index;
	}
	public void clearBufferRow(int index) {
		clearBufferRow(index, getSessionStorage());
	}
	public void clearBufferRow(int index, Long sessionID) {
		clearBufferRow(index, getSessionFromRegistry(sessionID));
	}
	public void clearBufferRow(int index, SessionStorage storage) {
		storage.clearRow(index);
	}

	public int getBufferBlockSize() {
		return getBufferBlockSize(getSessionStorage());
	}
	public int getBufferBlockSize(Long sessionID) {
		return getBufferBlockSize(getSessionFromRegistry(sessionID));
	}
	public int getBufferBlockSize(SessionStorage storage) {
		return storage.currentBlockSize;
	}
	public void setBufferBlockSize(int size) {
		setBufferBlockSize(size, getSessionStorage());
	}
	public void setBufferBlockSize(int size, Long sessionID) {
		setBufferBlockSize(size, getSessionFromRegistry(sessionID));
	}
	public void setBufferBlockSize(int size, SessionStorage storage) {
		storage.currentBlockSize = size;
	}
	public int getBufferGeoDataBatchSize() {
		return getBufferGeoDataBatchSize(getSessionStorage());
	}
	public int getBufferGeoDataBatchSize(Long sessionID) {
		return getBufferGeoDataBatchSize(getSessionFromRegistry(sessionID));
	}
	public int getBufferGeoDataBatchSize(SessionStorage storage) {
		return storage.currentGeoDataBatchSize;
	}
	public void setBufferGeoDataBatchSize(int size) {
		setBufferGeoDataBatchSize(size, getSessionStorage());
	}
	public void setBufferGeoDataBatchSize(int size, Long sessionID) {
		setBufferGeoDataBatchSize(size, getSessionFromRegistry(sessionID));
	}
	public void setBufferGeoDataBatchSize(int size, SessionStorage storage) {
		storage.currentGeoDataBatchSize = size;
	}

	/** Returns session postal locator block.  @return */
	public GeoCodingPostalLocatorDT[] getPostalLocatorBlock() {
		return getPostalLocatorBlock(getSessionStorage());
	}
	public GeoCodingPostalLocatorDT[] getPostalLocatorBlock(Long sessionID) {
		return getPostalLocatorBlock(getSessionFromRegistry(sessionID));
	}
	public GeoCodingPostalLocatorDT[] getPostalLocatorBlock(SessionStorage storage) {
		return storage.postalLocatorBlock;
	}

	/** Returns session geodata result block.  @return */
	public GeoDataResult[] getGeoDataResultBlock() {
		return getGeoDataResultBlock(getSessionStorage());
	}
	public GeoDataResult[] getGeoDataResultBlock(Long sessionID) {
		return getGeoDataResultBlock(getSessionFromRegistry(sessionID));
	}
	public GeoDataResult[] getGeoDataResultBlock(SessionStorage storage) {
		return storage.geoDataResultBlock;
	}

	/** Returns session geocoding result block.  @return */
	public GeoCodingResultDT[] getGeoCodingResultBlock() {
		return getGeoCodingResultBlock(getSessionStorage());
	}
	public GeoCodingResultDT[] getGeoCodingResultBlock(Long sessionID) {
		return getGeoCodingResultBlock(getSessionFromRegistry(sessionID));
	}
	public GeoCodingResultDT[] getGeoCodingResultBlock(SessionStorage storage) {
		return storage.geoCodingResultBlock;
	}

	/** Returns session result-processed indicator block.  @return */
	public Boolean[] getResultProcessed() {
		return getResultProcessed(getSessionStorage());
	}
	public Boolean[] getResultProcessed(Long sessionID) {
		return getResultProcessed(getSessionFromRegistry(sessionID));
	}
	public Boolean[] getResultProcessed(SessionStorage storage) {
		return storage.resultProcessed;
	}

	/** Returns session geodata input address block.  @return */
	public GeoDataInputAddress[] getGeoDataInputAddressBlock() {
		return getGeoDataInputAddressBlock(getSessionStorage());
	}
	public GeoDataInputAddress[] getGeoDataInputAddressBlock(Long sessionID) {
		return getGeoDataInputAddressBlock(getSessionFromRegistry(sessionID));
	}
	public GeoDataInputAddress[] getGeoDataInputAddressBlock(SessionStorage storage) {
		return storage.inputAddressBlock;
	}


	/** Returns thread-local session storage.  @return */
	protected SessionStorage getSessionStorage() {
		return (SessionStorage) tlsAccessor.getStorage();
	}


	// Address Helpers //

	/**
	 * Fetches the two-letter state postal code corresponding
	 * to the specified FIPS code.
	 * 
	 * @param stateCd
	 * @return
	 */
	public String getStatePostalCode(String stateCd) {
		return srtc.getStateAbbreviationByCode(stateCd);
	}

	/**
	 * Updates the specified geocoding postal locator with
	 * the two-letter state postal code.
	 * 
	 * @param dt
	 */
	public void updateStatePostalCode(GeoCodingPostalLocatorDT dt) {
		dt.setStatePostalCd(getStatePostalCode(dt.getStateCd()));
	}

	/**
	 * Populates the specified activity log entry from the indicated batch completion status.
	 * 
	 * @param entry
	 * @param status
	 */
	public void setActivityLogFromStatus(GeoCodingActivityLogDT entry, BatchCompletionStatus status) {

		entry.setCompleted(Boolean.valueOf(status.isCompleted()));
		entry.setCompletionReason(status.getCompletionReason());
		entry.setNumTotal(new Integer(status.getNumTotal()));
		entry.setNumSingleMatches(new Integer(status.getNumSingleMatches()));
		entry.setNumMultiMatches(new Integer(status.getNumMultiMatches()));
		entry.setNumZeroMatches(new Integer(status.getNumZeroMatches()));
		entry.setNumErrorRecords(new Integer(status.getNumErrorRecords()));
		entry.setNumErrors(new Integer(status.getNumExceptions()));
	}
}
