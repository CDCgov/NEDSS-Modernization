package gov.cdc.nedss.geocoding.batchprocess;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.geocoding.util.BatchCompletionStatus;
import gov.cdc.nedss.exception.NEDSSSystemException;

import javax.rmi.PortableRemoteObject;
import javax.ejb.CreateException;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;

import org.apache.log4j.Level;

/**
 * Geocoding Batch Processor.
 * 
 * @author rdodge
 *
 */
public class GeoCodingBatchProcessor
{
	// Constants //
	public static final String RUN_MODE_INCREMENTAL = "Incremental";
	public static final String RUN_MODE_FULL = "Full";

	public static final String ERROR_POLICY_NONE = "NONE";
	public static final String ERROR_POLICY_RETRY = "RETRY";
	public static final String ERROR_POLICY_SKIP = "SKIP";

	public static final String COMMAND_NEW_SESSION = "newSession";
	public static final String COMMAND_FINALIZE_SESSION = "finalizeSession";
	public static final String COMMAND_LOG_BATCH_START = "logBatchStarted";
	public static final String COMMAND_UPDATE_RECORD_STATUS = "updateGeoCodingRecordStatus";
	public static final String COMMAND_PROCESS_INCREMENTAL_BATCH = "processIncrementalBatchGeoCoding";
	public static final String COMMAND_PROCESS_FULL_BATCH = "processFullBatchGeoCoding";
	public static final String COMMAND_LOG_BATCH_END = "logBatchCompleted";

	// Members //
	static final LogUtils logger = new LogUtils(GeoCodingBatchProcessor.class.getName());

	private Long sessionID;
	private Long activityLogUID;
	private String batchRunMode;
	private Timestamp batchStartTime;
	private Timestamp batchEndTime;

	private BatchCompletionStatus batchCompletionStatus = new BatchCompletionStatus();  // will be replaced on success

	private MainSessionCommand mainSessionCommand;


	// Accessors / Mutators //

	/** Sets/gets session ID. */
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}
	public Long getSessionID() {
		return sessionID;
	}

	/** Sets/gets activity log UID. */
	public void setActivityLogUID(Long activityLogUID) {
		this.activityLogUID = activityLogUID;
	}
	public Long getActivityLogUID() {
		return activityLogUID;
	}

	/** Sets/gets batch run mode (full or incremental). */
	public void setBatchRunMode(String batchRunMode) {
		this.batchRunMode = batchRunMode;
	}
	public String getBatchRunMode() {
		return batchRunMode;
	}

	/** Sets/gets batch start time. */
	public void setBatchStartTime(Timestamp inTimeStamp) {
		batchStartTime = inTimeStamp;
	}
	public Timestamp getBatchStartTime() {
		return batchStartTime;
	}

	/** Sets/gets batch end time. */
	public void setBatchEndTime(Timestamp inTimeStamp) {
		batchEndTime = inTimeStamp;
	}
	public Timestamp getBatchEndTime() {
		return batchEndTime;
	}

	/** Sets/gets batch completion status object. */
	public void setBatchCompletionStatus(BatchCompletionStatus status) {
		this.batchCompletionStatus = status;
	}
	public BatchCompletionStatus getBatchCompletionStatus() {
		return batchCompletionStatus;
	}

	/**
	 * Indicates whether we are running a Full or Incremental batch process.
	 *  
	 * @return True indicates full run mode; false indicates incremental mode.
	 */
	public boolean isFullRunMode() {
		return RUN_MODE_FULL.equals(batchRunMode); 
	}


	// Session Commands //

	/**
	 * Starts a new session.
	 * 
	 * @throws Exception
	 */
	private void newSession() throws Exception {

		// Execute command //
		Object[] params = new Object[] {};
		ArrayList<?> result = invokeSessionCommand(COMMAND_NEW_SESSION, params);

		// Set Log UID; retain for future use //
		this.setSessionID((Long) result.get(0));
	}

	/**
	 * Finalizes the current session.
	 * 
	 * @throws Exception
	 */
	private void finalizeSession() throws Exception {

		// Execute command //
		Object[] params = new Object[] { sessionID };
		invokeSessionCommand(COMMAND_FINALIZE_SESSION, params);
	}

	/**
	 * Logs the batch start time and run mode; initializes the remaining fields.
	 * 
	 * @throws Exception
	 */
	private void logBatchStart() throws Exception {

		// Execute command //
		Object[] params = new Object[] { sessionID, Boolean.valueOf(isFullRunMode()), batchStartTime };
		ArrayList<?> result = invokeSessionCommand(COMMAND_LOG_BATCH_START, params);

		// Set Log UID; retain for future use //
		this.setActivityLogUID((Long) result.get(0));
	}

	/**
	 * Logs the batch end time and batch completion status.
	 * 
	 * @throws Exception
	 */
	private void logBatchEnd() throws Exception {

		Boolean isCompleted = Boolean.valueOf(batchCompletionStatus.isCompleted());

		// Execute command //
		Object[] params = new Object[] { sessionID, activityLogUID, batchEndTime, isCompleted };
		invokeSessionCommand(COMMAND_LOG_BATCH_END, params);
	}

	/**
	 * Updates geocoding record status.
	 * 
	 * @throws Exception
	 */
	private void updateRecordStatus() throws Exception {

		// Execute command //
		Object[] params = new Object[] { sessionID };
		invokeSessionCommand(COMMAND_UPDATE_RECORD_STATUS, params);
	}

	/**
	 * Processes an incremental batch for geocoding.
	 * 
	 * @param retryErrors
	 * @throws Exception
	 */
	private void processIncrementalBatch(Boolean retryErrors) throws Exception {

		// Execute command //
		Object[] params = new Object[] { sessionID, activityLogUID, retryErrors };
		ArrayList<?> result = invokeSessionCommand(COMMAND_PROCESS_INCREMENTAL_BATCH, params);

		// Set completion status //
		this.setBatchCompletionStatus((BatchCompletionStatus) result.get(0));
	}

	/**
	 * Processes a full batch for geocoding.
	 * 
	 * @param retryErrors
	 * @throws Exception
	 */
	private void processFullBatch(Boolean retryErrors) throws Exception {

		// Execute command //
		Object[] params = new Object[] { sessionID, activityLogUID, retryErrors };
		ArrayList<?> result = invokeSessionCommand(COMMAND_PROCESS_FULL_BATCH, params);

		// Set completion status //
		this.setBatchCompletionStatus((BatchCompletionStatus) result.get(0));
	}


	// Static Methods //

	/**
	 * Batch run method, invoked from command line or EJB.
	 * Launches a batch process in the specified run mode.
	 * 
	 * @param runMode Incremental or Full batch
	 * @param errorPolicy Retry, Skip or No Policy
	 * @param isInvokedFromMain True iff invoked from the command line
	 */
	private static void runBatch(String runMode, String errorPolicy, boolean isInvokedFromMain) throws Exception {

		displayOutput("STARTING GeoCoding Batch Process...", isInvokedFromMain, Level.INFO);

		GeoCodingBatchProcessor batchProcessor = new GeoCodingBatchProcessor();

		// Authenticate //
		batchProcessor.obtainNBSSecurityObject();  // return value is not needed locally

		// Determine start-of-batch timestamp //
		long now = System.currentTimeMillis();  // start time
		batchProcessor.setBatchStartTime(new Timestamp(now));
		batchProcessor.setBatchRunMode(runMode);

		Boolean retryErrors = ERROR_POLICY_RETRY.equals(errorPolicy) ? Boolean.TRUE :
			(ERROR_POLICY_SKIP.equals(errorPolicy) ? Boolean.FALSE : null);
		String modeDisplayString = ERROR_POLICY_RETRY.equals(errorPolicy) ? "with RETRY " :
				(ERROR_POLICY_SKIP.equals(errorPolicy) ? "with SKIP " : "");

		displayOutput("Attempting to run a" + (batchProcessor.isFullRunMode() ? " Full" : "n Incremental") +
				" batch process " + modeDisplayString + "at " + new Date(now) + ".", isInvokedFromMain, Level.INFO);

		try {
			// Start session //
			try {
				batchProcessor.newSession();
			}
			catch (RemoteException e) {
				throw new Exception(e.toString());
			}
	
			// Log batch start //
			try {
				batchProcessor.logBatchStart();
			}
			catch (RemoteException e) {
				throw new Exception(e.toString());
			}
	
			// Run batch process (Incremental or Full) //
			try {
				// Update record status //
				batchProcessor.updateRecordStatus();
	
				// Process batch //
				if (batchProcessor.isFullRunMode()) {
					batchProcessor.processFullBatch(retryErrors);
				}
				else {
					batchProcessor.processIncrementalBatch(retryErrors);
				}
			}
			catch (Exception e) {
				logger.fatal("GeoCodingService EJB returned an exception: " + e.toString(), e);
				// do not rethrow e
			}
	
			// Determine end-of-batch timestamp //
			now = System.currentTimeMillis();
			batchProcessor.setBatchEndTime(new Timestamp(now));
	
			displayOutput((batchProcessor.isFullRunMode() ? "Full" : "Incremental") + " Batch run ended " +
					(batchProcessor.getBatchCompletionStatus().isCompleted() ?
							"successfully" : "with errors") +
					" at " + new Date(now) + ".", isInvokedFromMain, Level.INFO);
	
			// Log batch completion //
			try {
				batchProcessor.logBatchEnd();
			}
			catch (RemoteException e) {
				throw new Exception(e.toString());
			}
	
			// Finalize session //
			try {
				batchProcessor.finalizeSession();
			}
			catch (RemoteException e) {
				throw new Exception(e.toString());
			}
	
			displayOutput("EXITING GeoCoding Batch Process...", isInvokedFromMain, Level.INFO);
		}
		catch (Exception oe) {

			displayOutput("ERROR in GeoCoding Batch Process - terminating abnormally.", isInvokedFromMain, Level.FATAL);

			// Finalize any open sessions //
			try { batchProcessor.finalizeSession(); }
			catch (Exception e) {}

			throw oe;  // rethrow outer exception
		}
	}

	/**
	 * Main method, called from the command line.
	 * 
	 * Default run mode is Incremental.  To specify a run mode, supply a
	 * command-line argument.  The first character (case-insensitive) is
	 * used to determine the run mode; use "F" for Full or "I" for
	 * Incremental batch processing.
	 * 
	 * To override the error policy setting (set in the geocoder properties),
	 * supply a command-line argument of "RETRY" (case-insensitive) to force
	 * retry of errors flagged in a previous run, or "SKIP" to skip retries.
	 * (Any other string will be treated as if no override was specified.) 
	 *
	 * Examples:
	 * 
	 *     Full - "<<command>> FULL"
	 *            "<<command>> F"
	 *            "<<command>> FULL RETRY"
	 *            "<<command>> RETRY F"
	 * 
	 *     Incremental - "<<command>> INCREMENTAL"
	 *                   "<<command>> I"
	 *                   "<<command>> I SKIP"
	 *                   "<<command>> SKIP INCREMENTAL"
	 *  
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Ensure properties are loaded //
		PropertyUtil.getInstance();

		String runMode = RUN_MODE_INCREMENTAL;
		String errorPolicy = ERROR_POLICY_NONE;
		int argIndex = -1;

		// Determine if error policy is specified and, if so, which argument it is //
		if (args.length >= 2) {

			// Try in 2nd position first //
			if (ERROR_POLICY_RETRY.equalsIgnoreCase(args[1]) || ERROR_POLICY_SKIP.equalsIgnoreCase(args[1])) {
				errorPolicy = ERROR_POLICY_RETRY.equalsIgnoreCase(args[1]) ? ERROR_POLICY_RETRY : ERROR_POLICY_SKIP;
				argIndex = 0;  // ergo run mode is 1st arg.
			}
			else if (ERROR_POLICY_RETRY.equalsIgnoreCase(args[0]) || ERROR_POLICY_SKIP.equalsIgnoreCase(args[0])) {
				errorPolicy = ERROR_POLICY_RETRY.equalsIgnoreCase(args[0]) ? ERROR_POLICY_RETRY : ERROR_POLICY_SKIP;
				argIndex = 1;  // ergo run mode is 2nd arg.
			}
		}
		else if (args.length >= 1) {
			if (ERROR_POLICY_RETRY.equalsIgnoreCase(args[0]) || ERROR_POLICY_SKIP.equalsIgnoreCase(args[0])) {
				errorPolicy = ERROR_POLICY_RETRY.equalsIgnoreCase(args[0]) ? ERROR_POLICY_RETRY : ERROR_POLICY_SKIP;
				// ergo run mode is not specified
			}
			else {
				argIndex = 0;  // ergo run mode is 1st arg.
			}
		}

		// Process run mode command-line argument (if any) //
		if (argIndex >= 0 && args[argIndex] != null && args[argIndex].length() >= 1) {
			String firstLetter = args[argIndex].substring(0, 1);
			if (firstLetter.equalsIgnoreCase("F")) {
				runMode = RUN_MODE_FULL;
			}
			else if (!firstLetter.equalsIgnoreCase("I")) {
				System.out.println("Unrecognized run mode \"" + args[argIndex] +
						"\" - will run in Incremental mode.");
			}
			// else - is Incremental //
		}
		// else - is Incremental //

		try {
			runBatch(runMode, errorPolicy, true /* is invoked from main() */);
		}
		catch (Exception e) {
			System.out.println("ABORTING GeoCoding - encountered an exception.");
			throw e;
		}
	}

	/**
	 * Top-level method (default) called by the batch controller / scheduler.
	 * By default the batch runs in Incremental mode.
	 * 
	 * @throws Exception
	 */
	public static void geoCodingProcess() throws Exception {
		geoCodingProcessIncremental();
	}

	/**
	 * Top-level method (for Incremental runs) called by the
	 * batch controller / scheduler.
	 * 
	 * @throws Exception
	 */
	public static void geoCodingProcessIncremental() throws Exception {
		runBatch(RUN_MODE_INCREMENTAL, ERROR_POLICY_NONE, false /* is NOT invoked from main() */);
	}

	/**
	 * Top-level method (for Full runs) called by the
	 * batch controller / scheduler.
	 * 
	 * @throws Exception
	 */
	public static void geoCodingProcessFull() throws Exception {
		runBatch(RUN_MODE_FULL, ERROR_POLICY_NONE, false /* is NOT invoked from main() */);
	}


	// Helpers //

	/**
	 * Invoking the method results in a NBSSeurityObj being returned to the caller...
	 * @return NBSSecurityObj
	 */
	private NBSSecurityObj obtainNBSSecurityObject() throws Exception {
		return getMainSessionCommand().nbsSecurityLogin("geocoding_batch", "geocoding_batch");
	}

	/**
	 * Controls / provides access to the <code>MainSessionCommand</code> EJB.
	 * 
	 * @return
	 * @throws Exception
	 */
	private MainSessionCommand getMainSessionCommand() throws Exception {
		NedssUtils nedssUtils = new NedssUtils();

		if (mainSessionCommand != null)
			return mainSessionCommand;  // EARLY EXIT

		MainSessionCommandHome msCommandHome;

		try {
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			msCommandHome = (MainSessionCommandHome) PortableRemoteObject.narrow(
					nedssUtils.lookupBean(sBeanName), MainSessionCommandHome.class);
			mainSessionCommand = msCommandHome.create();
			return mainSessionCommand;
		}
		catch (ClassCastException e) {
			throw new Exception(e.toString());
		}
		catch (NEDSSSystemException e) {
			throw new Exception(e.toString());
		}
		catch (RemoteException e) {
			throw new Exception(e.toString());
		}
		catch (CreateException e) {
			throw new Exception(e.toString());
		}
	}

	/**
	 * Wrapper for <code>MainSessionCommand.processRequest()</code>.
	 *  
	 * @param methodName
	 * @param params
	 * @return ArrayList
	 * @throws Exception
	 */
	private ArrayList<?> invokeSessionCommand(String methodName, Object[] params) throws Exception {
		return getMainSessionCommand().processRequest(JNDINames.GEOCODING_SERVICE_EJB, methodName, params);
	}


	/**
	 * Sends output to either the console or the application log depending on how
	 * the calling method was invoked.
	 * 
	 * @param output
	 * @param isInvokedFromMain
	 */
	private static void displayOutput(String output, boolean isInvokedFromMain, Level level) {
		if (isInvokedFromMain) {
			System.out.println(output);
		}
		else {
			if (Level.FATAL.equals(level))
				logger.fatal(output);
			else if (Level.ERROR.equals(level))
				logger.error(output);
			else if (Level.WARN.equals(level))
				logger.warn(output);
			else if (Level.INFO.equals(level))
				logger.info(output);
			else if (Level.DEBUG.equals(level))
				logger.debug(output);
		}
	}
}
