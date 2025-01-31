package nndm;

public class MessageKey {
	
	/**
	 * @return Returns the messageUid.
	 */
	public Long getMessageUid() {
		return messageUid;
	}
	/**
	 * @param messageUid The messageUid to set.
	 */
	public void setMessageUid(Long messageUid) {
		this.messageUid = messageUid;
	}
	/**
	 * @return Returns the rootExtText.
	 */
	public String getRootExtText() {
		return rootExtText;
	}
	/**
	 * @param rootExtText The rootExtText to set.
	 */
	public void setRootExtText(String rootExtText) {
		this.rootExtText = rootExtText;
	}
	private String rootExtText = null;
	private Long messageUid = null;

}
