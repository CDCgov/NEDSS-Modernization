
package gov.cdc.nedss.elr.dt;

import java.io.Serializable;
import java.lang.reflect.Field;


public class ELRWorkerQueueDT implements Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageId;
	private String payloadName;
	private String payloadBinaryContent;
	private String payloadTextContent;
	private String localFilename;
	private String service;
	private String action;
	private String arguments;
	private String fromPartyId;
   	private String messageRecipient;
   	private String errorCode;
   	private String errorMessage;
   	private String processingStatus;
   	private String applicationStatus;
   	private String encryption;
   	private String receivedTime;
   	private String lastUpdateTime;
   	private String processId;
   	private boolean itNew;
   	private boolean itDelete;
   	private boolean itDirty;
	   	
	private Long recordId;
	   public Long getRecordId() {
		return recordId;
	}
	
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public String getMessageId() {
		return messageId;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public String getPayloadName() {
		return payloadName;
	}
	
	public void setPayloadName(String payloadName) {
		this.payloadName = payloadName;
	}
	
	public String getPayloadBinaryContent() {
		return payloadBinaryContent;
	}
	
	public void setPayloadBinaryContent(String payloadBinaryContent) {
		this.payloadBinaryContent = payloadBinaryContent;
	}
	
	public String getPayloadTextContent() {
		return payloadTextContent;
	}
	
	public void setPayloadTextContent(String payloadTextContent) {
		this.payloadTextContent = payloadTextContent;
	}
	
	public String getLocalFilename() {
		return localFilename;
	}
	
	public void setLocalFilename(String localFilename) {
		this.localFilename = localFilename;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getArguments() {
		return arguments;
	}
	
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	
	public String getFromPartyId() {
		return fromPartyId;
	}
	
	public void setFromPartyId(String fromPartyId) {
		this.fromPartyId = fromPartyId;
	}
	
	public String getMessageRecipient() {
		return messageRecipient;
	}
	
	public void setMessageRecipient(String messageRecipient) {
		this.messageRecipient = messageRecipient;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getProcessingStatus() {
		return processingStatus;
	}
	
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	
	public String getApplicationStatus() {
		return applicationStatus;
	}
	
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	public String getEncryption() {
		return encryption;
	}
	
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	
	public String getReceivedTime() {
		return receivedTime;
	}
	
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
	
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getProcessId() {
		return processId;
	}
	
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public boolean isItNew() {
		return itNew;
	}
	
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}
	
	public boolean isItDelete() {
		return itDelete;
	}
	
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	
	public boolean isItDirty() {
		return itDirty;
	}
	
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}


	public ELRWorkerQueueDT() {
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = ELRWorkerQueueDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	
}