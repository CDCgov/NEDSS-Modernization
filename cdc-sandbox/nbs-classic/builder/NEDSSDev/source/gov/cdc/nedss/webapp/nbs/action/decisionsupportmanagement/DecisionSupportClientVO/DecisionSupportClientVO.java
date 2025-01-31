package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO;

import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;

import java.util.ArrayList;
import java.util.Vector;

public class DecisionSupportClientVO extends ClientVO{
	
	private ArrayList<BatchEntry> batchEntryList = new ArrayList<BatchEntry>();
	
	private ArrayList<BatchEntry> advancedCriteriaBatchEntryList = new ArrayList<BatchEntry>();
	
	private ArrayList<BatchEntry> advancedInvCriteriaBatchEntryList = new ArrayList<BatchEntry>();

	public ArrayList<BatchEntry> getAdvancedInvCriteriaBatchEntryList() {
		return advancedInvCriteriaBatchEntryList;
	}

	public void setAdvancedInvCriteriaBatchEntryList(
			ArrayList<BatchEntry> advancedInvCriteriaBatchEntryList) {
		this.advancedInvCriteriaBatchEntryList = advancedInvCriteriaBatchEntryList;
	}

	public ArrayList<BatchEntry> getAdvancedCriteriaBatchEntryList() {
		return advancedCriteriaBatchEntryList;
	}

	public void setAdvancedCriteriaBatchEntryList(
			ArrayList<BatchEntry> advancedCriteriaBatchEntryList) {
		this.advancedCriteriaBatchEntryList = advancedCriteriaBatchEntryList;
	}

	public ArrayList<BatchEntry> getBatchEntryList() {
		return batchEntryList;
	}

	public void setBatchEntryList(ArrayList<BatchEntry> batchEntryList) {
		this.batchEntryList = batchEntryList;
	}

	public void setAnswerArray(String key, String[] answerArray) {
		if(answerArray.length > 0) {
			Vector<String> answers = new Vector<String>();
			for(int i=0; i<answerArray.length; i++) {
				String answerTxt = answerArray[i];
				/*
				 * An answerTxt.length of zero means that the empty or
				 * "use all" entry has been selected.  If the 
				 * answerArray.length is greater than one that means that
				 * something else has been selected as well.  In this case
				 * ignore the "use all" selection.
				 */
				if (answerArray.length > 1 && (answerTxt == null || answerTxt.length() == 0)) {
					continue;
				}
				answers.add(answerTxt);
			}
			String[] answerList = new String[answers.size()];
			for (int i=0; i < answers.size(); i++) {
				answerList[i] = answers.get(i);
			}
			super.answerArrayMap.put(key, answerList);
		}else
		{
			super.answerArrayMap.put(key, new String[0]);
		}
	}
	
}
