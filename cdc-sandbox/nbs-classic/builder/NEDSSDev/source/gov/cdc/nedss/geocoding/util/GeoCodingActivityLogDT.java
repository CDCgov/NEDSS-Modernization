package gov.cdc.nedss.geocoding.util;

import gov.cdc.nedss.util.AbstractVO;
import java.sql.Timestamp;

public class GeoCodingActivityLogDT extends AbstractVO {
	private static final long serialVersionUID = 1L;
	// Constants //
	public static final String BATCH_RUN_MODE_INCREMENTAL = "I";
	public static final String BATCH_RUN_MODE_FULL = "F";

	// Members //
	private Long geoCodingActivityLogUid;
	private String batchRunMode;
	private Timestamp batchStartTime;
	private Timestamp batchEndTime;
	private Boolean completed;
	private Boolean successful;
	private String completionReason;

	private Integer numTotal;
	private Integer numSingleMatches;
	private Integer numMultiMatches;
	private Integer numZeroMatches;
	private Integer numErrorRecords;
	private Integer numErrors;


	/** Default (empty) constructor. */
	public GeoCodingActivityLogDT() {
	}


	// Accessors / Mutators //

	/** Retrieves the UID for this activity.  @return */
	public Long getGeoCodingActivityLogUid() {
		return geoCodingActivityLogUid;
	}

	/** Sets the UID for this activity.  @param geoCodingActivityLogUid */
	public void setGeoCodingActivityLogUid(Long geoCodingActivityLogUid) {
		this.geoCodingActivityLogUid = geoCodingActivityLogUid;
	}

	/** Retrieves batch run mode.  @return */
	public String getBatchRunMode() {
		return batchRunMode;
	}

	/** Indicates whether batch run mode is Incremental.  @return */
	public boolean isBatchRunModeIncremental() {
		return BATCH_RUN_MODE_INCREMENTAL.equals(batchRunMode);
	}

	/** Indicates whether batch run mode is Full.  @return */
	public boolean isBatchRunModeFull() {
		return BATCH_RUN_MODE_FULL.equals(batchRunMode);
	}

	/** Sets batch run mode.  @param batchRunMode */
	public void setBatchRunMode(String batchRunMode) {
		this.batchRunMode = batchRunMode;
	}

	/** Sets batch run mode to Incremental. */
	public void setBatchRunModeIncremental() {
		this.batchRunMode = BATCH_RUN_MODE_INCREMENTAL;
	}

	/** Sets batch run mode to Full. */
	public void setBatchRunModeFull() {
		this.batchRunMode = BATCH_RUN_MODE_FULL;
	}

	/** Retrieves batch start timestamp.  @return */
	public Timestamp getBatchStartTime() {
		return batchStartTime;
	}

	/** Sets batch start timestamp.  @param batchStartTime */
	public void setBatchStartTime(Timestamp batchStartTime) {
		this.batchStartTime = batchStartTime;
	}

	/** Retrieves batch completion timestamp.  @return */
	public Timestamp getBatchEndTime() {
		return batchEndTime;
	}

	/** Sets batch completion timestamp.  @param batchEndTime */
	public void setBatchEndTime(Timestamp batchEndTime) {
		this.batchEndTime = batchEndTime;
	}

	/** Retrieves batch completed flag.  @return */
	public Boolean getCompleted() {
		return completed;
	}

	/** Sets batch completed flag.  @param completed */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/** Retrieves batch successful flag.  @return */
	public Boolean getSuccessful() {
		return successful;
	}

	/** Sets batch successful flag.  @param successful */
	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	/** Retrieves batch completion reason.  @return */
	public String getCompletionReason() {
		return completionReason;
	}

	/** Sets batch completion reason.  @param completionReason */
	public void setCompletionReason(String completionReason) {
		this.completionReason = completionReason;
	}

	/** Retrieves total number of records processed.  @return */
	public Integer getNumTotal() {
		return numTotal;
	}

	/** Sets total number of records processed.  @param numTotal */
	public void setNumTotal(Integer numTotal) {
		this.numTotal = numTotal;
	}

	/** Retrieves number of single matches.  @return */
	public Integer getNumSingleMatches() {
		return numSingleMatches;
	}

	/** Sets number of single matches.  @param numSingleMatches */
	public void setNumSingleMatches(Integer numSingleMatches) {
		this.numSingleMatches = numSingleMatches;
	}

	/** Retrieves number of multiple matches.  @return */
	public Integer getNumMultiMatches() {
		return numMultiMatches;
	}

	/** Sets number of multiple matches.  @param numMultiMatches */
	public void setNumMultiMatches(Integer numMultiMatches) {
		this.numMultiMatches = numMultiMatches;
	}

	/** Retrieves number of zero matches.  @return */
	public Integer getNumZeroMatches() {
		return numZeroMatches;
	}

	/** Sets number of zero matches.  @param numZeroMatches */
	public void setNumZeroMatches(Integer numZeroMatches) {
		this.numZeroMatches = numZeroMatches;
	}

	/** Retrieves number of error records.  @return */
	public Integer getNumErrorRecords() {
		return numErrorRecords;
	}

	/** Sets number of error records.  @param numErrorRecords */
	public void setNumErrorRecords(Integer numErrorRecords) {
		this.numErrorRecords = numErrorRecords;
	}

	/** Retrieves number of errors.  @return */
	public Integer getNumErrors() {
		return numErrors;
	}

	/** Sets number of errors.  @param numErrors */
	public void setNumErrors(Integer numErrors) {
		this.numErrors = numErrors;
	}


	// AbstractVO Interface //

	/**
	 * Note: Always <code>true</code>.
	 * 
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		return true;
	}


	// Dirty Marker Interface //

	/** Note: Do nothing.   @param itDirty */
	public void setItDirty(boolean itDirty) {
	}

	/** Note: Always <code>true</code>.  @return boolean */
	public boolean isItDirty() {
		return true;
	}

	/** Note: Do nothing.   @param itNew */
	public void setItNew(boolean itNew) {
	}

	/** Note: Always <code>true</code>.  @return boolean */
	public boolean isItNew() {
		return true;
	}

	/** Note: Do nothing.   @param itDelete */
	public void setItDelete(boolean itDelete) {
	}

	/** Note: Always <code>true</code>.  @return boolean */
	public boolean isItDelete() {
		return true;
	}
}
