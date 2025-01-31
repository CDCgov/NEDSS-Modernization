package gov.cdc.nedss.geocoding.util;

import java.io.Serializable;

/**
 * Geocoding Batch Completion Status object.
 * 
 * @author rdodge
 *
 */
public class BatchCompletionStatus implements Serializable {

	static final long serialVersionUID = 1L;

	private boolean completed;
	private String completionReason;

	private int numTotal;
	private int numSingleMatches;
	private int numMultiMatches;
	private int numZeroMatches;
	private int numErrorRecords;
	private int numFatalSkipped;
	private int numExceptions;

	/** Default constructor. */
	public BatchCompletionStatus() {
		clear();
	}

	public void clear() {
		completed = false;
		completionReason = "";
		numTotal = numSingleMatches = numMultiMatches = numZeroMatches =
			numErrorRecords = numFatalSkipped = numExceptions = 0;
	}

	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getCompletionReason() {
		return completionReason;
	}
	public void setCompletionReason(String completionReason) {
		this.completionReason = completionReason;
	}

	public int getNumTotal() {
		return numTotal;
	}
	public void setNumTotal(int numTotal) {
		this.numTotal = numTotal;
	}
	public int getNumSingleMatches() {
		return numSingleMatches;
	}
	public void setNumSingleMatches(int numSingleMatches) {
		this.numSingleMatches = numSingleMatches;
	}
	public int getNumMultiMatches() {
		return numMultiMatches;
	}
	public void setNumMultiMatches(int numMultiMatches) {
		this.numMultiMatches = numMultiMatches;
	}
	public int getNumZeroMatches() {
		return numZeroMatches;
	}
	public void setNumZeroMatches(int numZeroMatches) {
		this.numZeroMatches = numZeroMatches;
	}
	public int getNumErrorRecords() {
		return numErrorRecords;
	}
	public void setNumErrorRecords(int numErrorRecords) {
		this.numErrorRecords = numErrorRecords;
	}
	public int getNumFatalSkipped() {
		return numFatalSkipped;
	}
	public void setNumFatalSkipped(int numFatalSkipped) {
		this.numFatalSkipped = numFatalSkipped;
	}
	public int getNumExceptions() {
		return numExceptions;
	}
	public void setNumExceptions(int numExceptions) {
		this.numExceptions = numExceptions;
	}
}
