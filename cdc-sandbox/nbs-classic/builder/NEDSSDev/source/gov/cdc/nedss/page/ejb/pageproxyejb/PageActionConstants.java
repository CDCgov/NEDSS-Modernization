package gov.cdc.nedss.page.ejb.pageproxyejb;

/**Description: The PageActionConstant class defines action constants that define user action.
 * The expectation is to use the Page Constants class to find the methods in PageCaseUtil.java class for Cases. 
 * Similar method mapping needs to be done for Labs, Morb. Reports, other Acts and Entities as and when NBS is changed 
 * to move such functionality in Dynamic Forms.
 * @version:NBS4.0
 * @author Pradeep Sharma
 *@since: 2010
 */
public interface PageActionConstants{
	//Investigation Case specific actions
	public static final String deleteContactAttachment="deleteContactAttachment";
	public static final String deletePageProxy="deletePageProxy";
	public static final String exportOwnership="exportOwnership";
	public static final String getAggregateSummary="getAggregateSummary";
	public static final String getAggregateSummaryColl="getAggregateSummaryColl";
	public static final String getContactAttachment="getContactAttachment";
	public static final String getContactAttachmentSummaryCollection="getContactAttachmentSummaryCollection";
	public static final String getContactProxyVO="getContactProxyVO";
	public static final String getNamedAsContactSummaryByCondition="getNamedAsContactSummaryByCondition";
	public static final String getPageProxyVO ="getPageProxyVO ";
	public static final String getPublicHealthCaseDT="getPublicHealthCaseDT";
	public static final String getPublicHealthCaseVO="getPublicHealthCaseVO";
	public static final String getRootExtensionTxt="getRootExtensionTxt";
	public static final String retrieveNotificationSummaryListForInvestigation="retrieveNotificationSummaryListForInvestigation";
	public static final String setAggregateSummary="setAggregateSummary";
	public static final String setContactAttachment="setContactAttachment";
	public static final String setContactNote ="setContactNote ";
	public static final String setContactProxyVO="setContactProxyVO";
	public static final String setPageActProxyVO="setPageActProxyVO";
	public static final String setPageProxyWithAutoAssoc="setPageProxyWithAutoAssoc";
	public static final String transferOwnership="transferOwnership";
	public static final String updateContactAssociations="updateContactAssociations";
	public static final String changeCondition="changeCondition";
	
	
}