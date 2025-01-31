//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\nnd\\dt\\TransportQOutDT.java

package gov.cdc.nedss.nnd.dt;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.SQLException;


public class TransportQOutDT implements Serializable
{
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;

   private Long recordId;
   private String messageId;
   private String payloadFile;
   private String payloadContent;
   private String destinationFilename;
   private String routeInfo;
   private String service;
   private String action;
   private String arguments;
   private String messageRecipient;
   private String messageCreationTime;
   private String encryption;
   private String signature;
   private String publicKeyLdapAddress;
   private String publicKeyLdapBaseDN;
   private String publicKeyLdapDN;
   private String certificateURL;
   private String processingStatus;
   private String transportStatus;
   private String transportErrorCode;
   private String applicationStatus;
   private String applicationErrorCode;
   private String applicationResponse;
   private String messageSentTime;
   private String messageReceivedTime;
   private String responseMessageId;
   private String responseArguments;
   private String responseLocalFile;
   private String responseFilename;
   private String responseContent;
   private String responseMessageOrigin;
   private String responseMessageSignature;
   private Integer priority;
   private boolean itNew;
   private boolean itDelete;
   private boolean itDirty;

   /**
    * @roseuid 3FA14FC202BE
    */
   public TransportQOutDT()
   {

   }

   /**
    * Access method for the recordId property.
    *
    * @return   the current value of the recordId property
    */
   public Long getRecordId()
   {
      return recordId;
   }

   /**
    * Sets the value of the recordId property.
    *
    * @param aRecordId the new value of the recordId property
    */
   public void setRecordId(Long aRecordId)
   {
      recordId = aRecordId;
   }

   /**
    * Access method for the messageId property.
    *
    * @return   the current value of the messageId property
    */
   public String getMessageId()
   {
      return messageId;
   }

   /**
    * Sets the value of the messageId property.
    *
    * @param aMessageId the new value of the messageId property
    */
   public void setMessageId(String aMessageId)
   {
      messageId = aMessageId;
   }

   /**
    * Access method for the payloadFile property.
    *
    * @return   the current value of the payloadFile property
    */
   public String getPayloadFile()
   {
      return payloadFile;
   }

   /**
    * Sets the value of the payloadFile property.
    *
    * @param aPayloadFile the new value of the payloadFile property
    */
   public void setPayloadFile(String aPayloadFile)
   {
      payloadFile = aPayloadFile;
   }

   /**
    * Sets the payloadContent for SQLServer.  The data type
    * for this column in sqlServer is Image, it returns a byte[] from
    * the database.
    * @param aPayloadFile
    */
   public void setPayloadContent(byte[] aPayloadFile)
   {
      payloadContent = new String(aPayloadFile);
   }

   public void setPayloadContent(Blob blob) throws SQLException{
     Long lng = new Long(1);
     Long length = new Long(blob.length());
     payloadContent = new String(blob.getBytes(lng.longValue(), length.intValue()));
   }


   /**
    * Access method for the payloadContent property.
    *
    * @return   the current value of the payloadContent property
    */
   public String getPayloadContent()
   {
      return payloadContent;
   }

   /**
    * Sets the value of the payloadContent property.
    *
    * @param aPayloadContent the new value of the payloadContent property
    */
   public void setPayloadContent(String aPayloadContent)
   {
      payloadContent = aPayloadContent;
   }

   /**
    * Access method for the destinationFilename property.
    *
    * @return   the current value of the destinationFilename property
    */
   public String getDestinationFilename()
   {
      return destinationFilename;
   }

   /**
    * Sets the value of the destinationFilename property.
    *
    * @param aDestinationFilename the new value of the destinationFilename property
    */
   public void setDestinationFilename(String aDestinationFilename)
   {
      destinationFilename = aDestinationFilename;
   }

   /**
    * Access method for the routeInfo property.
    *
    * @return   the current value of the routeInfo property
    */
   public String getRouteInfo()
   {
      return routeInfo;
   }

   /**
    * Sets the value of the routeInfo property.
    *
    * @param aRouteInfo the new value of the routeInfo property
    */
   public void setRouteInfo(String aRouteInfo)
   {
      routeInfo = aRouteInfo;
   }

   /**
    * Access method for the service property.
    *
    * @return   the current value of the service property
    */
   public String getService()
   {
      return service;
   }

   /**
    * Sets the value of the service property.
    *
    * @param aService the new value of the service property
    */
   public void setService(String aService)
   {
      service = aService;
   }

   /**
    * Access method for the action property.
    *
    * @return   the current value of the action property
    */
   public String getAction()
   {
      return action;
   }

   /**
    * Sets the value of the action property.
    *
    * @param aAction the new value of the action property
    */
   public void setAction(String aAction)
   {
      action = aAction;
   }

   /**
    * Access method for the arguments property.
    *
    * @return   the current value of the arguments property
    */
   public String getArguments()
   {
      return arguments;
   }

   /**
    * Sets the value of the arguments property.
    *
    * @param aArguments the new value of the arguments property
    */
   public void setArguments(String aArguments)
   {
      arguments = aArguments;
   }

   /**
    * Access method for the messageRecipient property.
    *
    * @return   the current value of the messageRecipient property
    */
   public String getMessageRecipient()
   {
      return messageRecipient;
   }

   /**
    * Sets the value of the messageRecipient property.
    *
    * @param aMessageRecipient the new value of the messageRecipient property
    */
   public void setMessageRecipient(String aMessageRecipient)
   {
      messageRecipient = aMessageRecipient;
   }

   /**
    * Access method for the messageCreationTime property.
    *
    * @return   the current value of the messageCreationTime property
    */
   public String getMessageCreationTime()
   {
      return messageCreationTime;
   }

   /**
    * Sets the value of the messageCreationTime property.
    *
    * @param aMessageCreationTime the new value of the messageCreationTime property
    */
   public void setMessageCreationTime(String aMessageCreationTime)
   {
      messageCreationTime = aMessageCreationTime;
   }

   /**
    * Access method for the encryption property.
    *
    * @return   the current value of the encryption property
    */
   public String getEncryption()
   {
      return encryption;
   }

   /**
    * Sets the value of the encryption property.
    *
    * @param aEncryption the new value of the encryption property
    */
   public void setEncryption(String aEncryption)
   {
      encryption = aEncryption;
   }

   /**
    * Access method for the signature property.
    *
    * @return   the current value of the signature property
    */
   public String getSignature()
   {
      return signature;
   }

   /**
    * Sets the value of the signature property.
    *
    * @param aSignature the new value of the signature property
    */
   public void setSignature(String aSignature)
   {
      signature = aSignature;
   }

   /**
    * Access method for the publicKeyLdapAddress property.
    *
    * @return   the current value of the publicKeyLdapAddress property
    */
   public String getPublicKeyLdapAddress()
   {
      return publicKeyLdapAddress;
   }

   /**
    * Sets the value of the publicKeyLdapAddress property.
    *
    * @param aPublicKeyLdapAddress the new value of the publicKeyLdapAddress property
    */
   public void setPublicKeyLdapAddress(String aPublicKeyLdapAddress)
   {
      publicKeyLdapAddress = aPublicKeyLdapAddress;
   }

   /**
    * Access method for the publicKeyLdapBaseDN property.
    *
    * @return   the current value of the publicKeyLdapBaseDN property
    */
   public String getPublicKeyLdapBaseDN()
   {
      return publicKeyLdapBaseDN;
   }

   /**
    * Sets the value of the publicKeyLdapBaseDN property.
    *
    * @param aPublicKeyLdapBaseDN the new value of the publicKeyLdapBaseDN property
    */
   public void setPublicKeyLdapBaseDN(String aPublicKeyLdapBaseDN)
   {
      publicKeyLdapBaseDN = aPublicKeyLdapBaseDN;
   }

   /**
    * Access method for the publicKeyLdapDN property.
    *
    * @return   the current value of the publicKeyLdapDN property
    */
   public String getPublicKeyLdapDN()
   {
      return publicKeyLdapDN;
   }

   /**
    * Sets the value of the publicKeyLdapDN property.
    *
    * @param aPublicKeyLdapDN the new value of the publicKeyLdapDN property
    */
   public void setPublicKeyLdapDN(String aPublicKeyLdapDN)
   {
      publicKeyLdapDN = aPublicKeyLdapDN;
   }

   /**
    * Access method for the certificateURL property.
    *
    * @return   the current value of the certificateURL property
    */
   public String getCertificateURL()
   {
      return certificateURL;
   }

   /**
    * Sets the value of the certificateURL property.
    *
    * @param aCertificateURL the new value of the certificateURL property
    */
   public void setCertificateURL(String aCertificateURL)
   {
      certificateURL = aCertificateURL;
   }

   /**
    * Access method for the processingStatus property.
    *
    * @return   the current value of the processingStatus property
    */
   public String getProcessingStatus()
   {
      return processingStatus;
   }

   /**
    * Sets the value of the processingStatus property.
    *
    * @param aProcessingStatus the new value of the processingStatus property
    */
   public void setProcessingStatus(String aProcessingStatus)
   {
      processingStatus = aProcessingStatus;
   }

   /**
    * Access method for the transportStatus property.
    *
    * @return   the current value of the transportStatus property
    */
   public String getTransportStatus()
   {
      return transportStatus;
   }

   /**
    * Sets the value of the transportStatus property.
    *
    * @param aTransportStatus the new value of the transportStatus property
    */
   public void setTransportStatus(String aTransportStatus)
   {
      transportStatus = aTransportStatus;
   }

   /**
    * Access method for the transportErrorCode property.
    *
    * @return   the current value of the transportErrorCode property
    */
   public String getTransportErrorCode()
   {
      return transportErrorCode;
   }

   /**
    * Sets the value of the transportErrorCode property.
    *
    * @param aTransportErrorCode the new value of the transportErrorCode property
    */
   public void setTransportErrorCode(String aTransportErrorCode)
   {
      transportErrorCode = aTransportErrorCode;
   }

   /**
    * Access method for the applicationStatus property.
    *
    * @return   the current value of the applicationStatus property
    */
   public String getApplicationStatus()
   {
      return applicationStatus;
   }

   /**
    * Sets the value of the applicationStatus property.
    *
    * @param aApplicationStatus the new value of the applicationStatus property
    */
   public void setApplicationStatus(String aApplicationStatus)
   {
      applicationStatus = aApplicationStatus;
   }

   /**
    * Access method for the applicationErrorCode property.
    *
    * @return   the current value of the applicationErrorCode property
    */
   public String getApplicationErrorCode()
   {
      return applicationErrorCode;
   }

   /**
    * Sets the value of the applicationErrorCode property.
    *
    * @param aApplicationErrorCode the new value of the applicationErrorCode property
    */
   public void setApplicationErrorCode(String aApplicationErrorCode)
   {
      applicationErrorCode = aApplicationErrorCode;
   }

   /**
    * Access method for the applicationResponse property.
    *
    * @return   the current value of the applicationResponse property
    */
   public String getApplicationResponse()
   {
      return applicationResponse;
   }

   /**
    * Sets the value of the applicationResponse property.
    *
    * @param aApplicationResponse the new value of the applicationResponse property
    */
   public void setApplicationResponse(String aApplicationResponse)
   {
      applicationResponse = aApplicationResponse;
   }

   /**
    * Access method for the messageSentTime property.
    *
    * @return   the current value of the messageSentTime property
    */
   public String getMessageSentTime()
   {
      return messageSentTime;
   }

   /**
    * Sets the value of the messageSentTime property.
    *
    * @param aMessageSentTime the new value of the messageSentTime property
    */
   public void setMessageSentTime(String aMessageSentTime)
   {
      messageSentTime = aMessageSentTime;
   }

   /**
    * Access method for the messageReceivedTime property.
    *
    * @return   the current value of the messageReceivedTime property
    */
   public String getMessageReceivedTime()
   {
      return messageReceivedTime;
   }

   /**
    * Sets the value of the messageReceivedTime property.
    *
    * @param aMessageReceivedTime the new value of the messageReceivedTime property
    */
   public void setMessageReceivedTime(String aMessageReceivedTime)
   {
      messageReceivedTime = aMessageReceivedTime;
   }

   /**
    * Access method for the responseMessageId property.
    *
    * @return   the current value of the responseMessageId property
    */
   public String getResponseMessageId()
   {
      return responseMessageId;
   }

   /**
    * Sets the value of the responseMessageId property.
    *
    * @param aResponseMessageId the new value of the responseMessageId property
    */
   public void setResponseMessageId(String aResponseMessageId)
   {
      responseMessageId = aResponseMessageId;
   }

   /**
    * Access method for the responseArguments property.
    *
    * @return   the current value of the responseArguments property
    */
   public String getResponseArguments()
   {
      return responseArguments;
   }

   /**
    * Sets the value of the responseArguments property.
    *
    * @param aResponseArguments the new value of the responseArguments property
    */
   public void setResponseArguments(String aResponseArguments)
   {
      responseArguments = aResponseArguments;
   }

   /**
    * Access method for the responseLocalFile property.
    *
    * @return   the current value of the responseLocalFile property
    */
   public String getResponseLocalFile()
   {
      return responseLocalFile;
   }

   /**
    * Sets the value of the responseLocalFile property.
    *
    * @param aResponseLocalFile the new value of the responseLocalFile property
    */
   public void setResponseLocalFile(String aResponseLocalFile)
   {
      responseLocalFile = aResponseLocalFile;
   }

   /**
    * Access method for the responseFilename property.
    *
    * @return   the current value of the responseFilename property
    */
   public String getResponseFilename()
   {
      return responseFilename;
   }

   /**
    * Sets the value of the responseFilename property.
    *
    * @param aResponseFilename the new value of the responseFilename property
    */
   public void setResponseFilename(String aResponseFilename)
   {
      responseFilename = aResponseFilename;
   }

   /**
    * Access method for the responseContent property.
    *
    * @return   the current value of the responseContent property
    */
   public String getResponseContent()
   {
      return responseContent;
   }

   /**
    * Sets the value of the responseContent property.
    *
    * @param aResponseContent the new value of the responseContent property
    */
   public void setResponseContent(String aResponseContent)
   {
      responseContent = aResponseContent;
   }

   /**
    * Access method for the responseMessageOrigin property.
    *
    * @return   the current value of the responseMessageOrigin property
    */
   public String getResponseMessageOrigin()
   {
      return responseMessageOrigin;
   }

   /**
    * Sets the value of the responseMessageOrigin property.
    *
    * @param aResponseMessageOrigin the new value of the responseMessageOrigin property
    */
   public void setResponseMessageOrigin(String aResponseMessageOrigin)
   {
      responseMessageOrigin = aResponseMessageOrigin;
   }

   /**
    * Access method for the responseMessageSignature property.
    *
    * @return   the current value of the responseMessageSignature property
    */
   public String getResponseMessageSignature()
   {
      return responseMessageSignature;
   }

   /**
    * Sets the value of the responseMessageSignature property.
    *
    * @param aResponseMessageSignature the new value of the responseMessageSignature property
    */
   public void setResponseMessageSignature(String aResponseMessageSignature)
   {
      responseMessageSignature = aResponseMessageSignature;
   }

   /**
    * Access method for the priority property.
    *
    * @return   the current value of the priority property
    */
   public Integer getPriority()
   {
      return priority;
   }

   /**
    * Sets the value of the priority property.
    *
    * @param aPriority the new value of the priority property
    */
   public void setPriority(Integer aPriority)
   {
      priority = aPriority;
   }

   public void setItDirty(boolean itDirty)
   {
     this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3D77B5B7009E
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3D77B5B700AB
    */
   public void setItNew(boolean itNew)
   {
     this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3D77B5B700AD
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3D77B5B700BB
    */
   public void setItDelete(boolean itDelete)
   {
     this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3D77B5B700BD
    */
   public boolean isItDelete()
   {
    return itDelete;
   }

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = TransportQOutDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

   
}
