package gov.cdc.nedss.webapp.nbs.form.importandexport;

import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.DecisionSupportClientVO;
import gov.cdc.nedss.webapp.nbs.action.importandexport.util.ReceivingSystemUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class ReceivingFacilityForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	ReceivingSystemUtil rsUtil = new ReceivingSystemUtil();

	Map<Object, Object> searchMap = new HashMap<Object, Object>();
	ArrayList<Object> manageList = new ArrayList<Object>();
	ArrayList<Object> queueList = new ArrayList<Object>();
	ArrayList<Object> oldManageList = new ArrayList<Object>();
	Object selection = new Object();
	Object oldDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();
	private int srchFldCount;
	ExportReceivingFacilityDT exportRecFacDT = new ExportReceivingFacilityDT();
	ExportReceivingFacilityDT oldExpRecFacDT = new ExportReceivingFacilityDT();
	ArrayList<Object> sendSysList = new ArrayList<Object>();

	// For Filtering/sorting
	Map<Object, Object> searchCriteriaArrayMap = new HashMap<Object, Object>();
	private Collection<Object> receivingSysDTColl;
	private ArrayList<Object> owner = new ArrayList<Object>();
	private ArrayList<Object> sender = new ArrayList<Object>();
	private ArrayList<Object> receipient = new ArrayList<Object>();
	private ArrayList<Object> Transfer = new ArrayList<Object>();
	private ArrayList<Object> reportType = new ArrayList<Object>();

	private QueueDT queueDT;
	public QueueDT getQueueDT() {
		return queueDT;
	}

	public void setQueueDT(QueueDT queueDT) {
		this.queueDT = queueDT;
	}

	public ArrayList<QueueColumnDT> getQueueCollection() {
		return queueCollection;
	}

	public void setQueueCollection(ArrayList<QueueColumnDT> queueCollection) {
		this.queueCollection = queueCollection;
	}

	public String getStringQueueCollection() {
		return stringQueueCollection;
	}

	public void setStringQueueCollection(String stringQueueCollection) {
		this.stringQueueCollection = stringQueueCollection;
	}

	private ArrayList<QueueColumnDT> queueCollection;
	private String stringQueueCollection;
	 
	public void initializeDropDowns() {
		owner = rsUtil.getOwnerDropDowns(receivingSysDTColl);
		sender = rsUtil.getSenderDropDowns(receivingSysDTColl);
		receipient = rsUtil.getRecipientDropDowns(receivingSysDTColl);
		Transfer = rsUtil.getTransferDropDowns(receivingSysDTColl);
		reportType = rsUtil.getReportTypeDropDowns(receivingSysDTColl);
	}

	public ArrayList<Object> getReportType() {
		return reportType;
	}

	public void setReportType(ArrayList<Object> reportType) {
		this.reportType = reportType;
	}

	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object, Object>();
	}

	public String[] getAnswerArray(String key) {
		return (String[]) searchCriteriaArrayMap.get(key);
	}

	public void setAnswerArray(String key, String[] answer) {
		if (answer.length > 0) {
			String[] answerList = new String[answer.length];
			boolean selected = false;
			for (int i = 1; i <= answer.length; i++) {
				String answerTxt = answer[i - 1];
				if (!answerTxt.equals("")) {
					selected = true;
					answerList[i - 1] = answerTxt;
				}
			}
			if (selected)
				searchCriteriaArrayMap.put(key, answerList);
		}
	}

	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey, HTMLEncoder.encodeHtml(answer));
		}
	}
	
	public int getSrchFldCount() {
		return srchFldCount;
	}

	public void setSrchFldCount(int srchFldCount) {
		this.srchFldCount = srchFldCount;
	}

	public Map<Object, Object> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<Object, Object> searchMap) {
		this.searchMap = searchMap;
	}

	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, answer);
	}

	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
	}

	public ArrayList<Object> getManageList() {
		return manageList;
	}

	public void setManageList(ArrayList<Object> manageList) {
		this.manageList = manageList;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = copyObject(selection);
	}

	public void resetSelection() {
		this.selection = new Object();
		this.oldDT = new Object();
	}

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getReturnToLink() {
		return returnToLink;
	}

	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}

	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public void clearSelections() {
		this.setSearchMap(new HashMap<Object, Object>());
		this.setManageList(new ArrayList<Object>());
		this.resetSelection();
		this.setAttributeMap(new HashMap<Object, Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);

	}

	public Object getOldDT() {
		return oldDT;
	}

	public void setOldDT(Object oldDT) {
		this.oldDT = copyObject(oldDT);
	}

	private static Object copyObject(Object param) {
		Object deepCopy = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(param);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			deepCopy = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopy;
	}

	public ArrayList<Object> getOldManageList() {
		return oldManageList;
	}

	public void setOldManageList(ArrayList<Object> oldManageList) {
		this.oldManageList = oldManageList;
	}

	public ExportReceivingFacilityDT getExportRecFacDT() {
		return exportRecFacDT;
	}

	public void setExportRecFacDT(ExportReceivingFacilityDT exportRecFacDT) {
		this.exportRecFacDT = exportRecFacDT;
	}

	public ExportReceivingFacilityDT getOldExpRecFacDT() {
		return oldExpRecFacDT;
	}

	public void setOldExpRecFacDT(ExportReceivingFacilityDT oldExpRecFacDT) {
		this.oldExpRecFacDT = oldExpRecFacDT;
	}

	public ArrayList<Object> getStatusList() {
		ArrayList<Object> statusList = new ArrayList<Object>();
		DropDownCodeDT cdDT1 = new DropDownCodeDT();
		cdDT1.setKey(NEDSSConstants.STATUS_ACTIVE);
		cdDT1.setValue(NEDSSConstants.RECORD_STATUS_ACTIVE);
		statusList.add(cdDT1);
		DropDownCodeDT cdDT2 = new DropDownCodeDT();
		cdDT2.setKey(NEDSSConstants.STATUS_INACTIVE);
		cdDT2.setValue(NEDSSConstants.RECORD_STATUS_INACTIVE);
		statusList.add(cdDT2);
		return statusList;
	}

	public ArrayList<Object> getSendSysList() {
		return CachedDropDowns.getCodedValueForType("YN");
	}

	public void setSendSysList(ArrayList<Object> sendSysList) {
		this.sendSysList = sendSysList;
	}

	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(
			Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}

	public Collection<Object> getReceivingSysDTColl() {
		return receivingSysDTColl;
	}

	public void setReceivingSysDTColl(Collection<Object> receivingSysDTColl) {
		this.receivingSysDTColl = receivingSysDTColl;
	}

	public ArrayList<Object> getOwner() {
		return owner;
	}

	public void setOwner(ArrayList<Object> owner) {
		this.owner = owner;
	}

	public ArrayList<Object> getSender() {
		return sender;
	}

	public void setSender(ArrayList<Object> sender) {
		this.sender = sender;
	}

	public ArrayList<Object> getReceipient() {
		return receipient;
	}

	public void setReceipient(ArrayList<Object> receipient) {
		this.receipient = receipient;
	}

	public ArrayList<Object> getTransfer() {
		return Transfer;
	}

	public void setTransfer(ArrayList<Object> transfer) {
		Transfer = transfer;
	}

	public ArrayList<Object> getQueueList() {
		return queueList;
	}

	public void setQueueList(ArrayList<Object> queueList) {
		this.queueList = queueList;
	}

	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object>) CachedDropDowns
				.getCodedValueForType(key);

		if ("PUBLIC_HEALTH_EVENT".equals(key)) {
			Iterator iterator = aList.iterator();
			ArrayList<Object> tempList = new ArrayList<Object>();

			while (iterator.hasNext()) {
				DropDownCodeDT dropDownCodeDT = (DropDownCodeDT) iterator
						.next();
				if (!"CMR".equals(dropDownCodeDT.getKey()) && !"Z32".equals(dropDownCodeDT.getKey()))
					tempList.add(dropDownCodeDT);
			}
			aList = tempList;
		}

		return aList;
	}

}
