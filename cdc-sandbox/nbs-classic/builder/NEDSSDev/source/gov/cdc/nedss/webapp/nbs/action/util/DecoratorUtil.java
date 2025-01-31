package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationAuditLogSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ResultedTestSummaryVO;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.NumberUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class DecoratorUtil
{

    public static final String  TABLE_START               = "<table role=\"presentation\">";

    public static final String  TABLE_END                 = "</table>";

    public static final String  TR_START                  = "<tr>";

    public static final String  TR_END                    = "</tr>";

    public static final String  TD_START                  = "<td>";

    public static final String  TD_END                    = "</td>";

    public static final String  TD_ALIGN_RIGHT            = "<td align='right'>";

    public static final String  TD_ALIGN_LEFT             = "<td align='left'>";

    public static final String  BOLD_START                = "<b>";

    public static final String  BOLD_END                  = "</b>";

    public static final String  TAGCLOSER                 = ">";

    public static final String  UL_START                  = "";

    public static final String  UL_END                    = "";

    public static final String  BR                        = "<BR>";

    public static final String  RESULTED_TEST_LABEL       = "Resulted Test";

    public static final String  CODED_RESULT_LABEL        = "Coded Result";

    public static final String  NUMERIC_RESULT_LABEL      = "Numeric Result";

    public static final String  TEXT_RESULT_LABEL         = "Text Result";

    public static final String  REFERENCE_RANGE_LABEL     = "Reference Range";

    public static final String  ORGANISM_NAME_LABEL       = "Organism Name";

    public static final String  DATE_RECEIVED_LABEL       = "Date Received";

    public static final String  ORDERED_TEST_LABEL        = "Ordered Test";

    public static final String  LOCAL_ID_LABEL            = "Local ID";

    public static final String  ORDER_PROVIDER_LABEL      = "Ordering Provider";

    public static final String  ACCESSION_NUMBER_LABEL    = "Accession Number";

    public static final String  SPECIMEN_SOURCE_LABEL     = "Specimen Source";

    public static final String  PROCESSING_DECISION_LABEL = "Processing Decision";

    public static final String  REPORTING_FACILITY_LABEL  = "Reporting Facility";

    public static final String  TEST_STATUS_LABEL         = "Test Status";

    public static final String  DATE_COLLECTED_LABEL      = "Date Collected";

    public static final String  DRUG_NAME_LABEL           = "Drug Name";

    public static final String  SPACER                    = "<tr><td style=\"HEIGHT: 5px;\"></td></tr>";

    public static final String  SINGLE_SPACE              = "&nbsp;";

    public static final String  LIST_ITEM_START           = "<UL><LI>";

    public static final String  LIST_ITEM_END             = "</LI></UL>";

    public static final String  LI_START                  = "<DIV>";

    public static final String  LI_END                    = "</DIV>";

    public static final String  COLON                     = ":";

    public static final String  UNDERLINE_BEGIN           = "<U>";

    public static final String  UNDERLINE_END             = "</U>";

    public static final String  ITALIC_BEGIN              = "<I>";

    public static final String  ITALIC_END                = "</I>";

    // public static final String[] abc =
    // {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    
    private final static Logger logger                    = Logger.getLogger(DecoratorUtil.class);

    public static String getLabDates(String dateRecieved, String dateCollected)
    {
        StringBuffer buf1 = new StringBuffer("");
        buf1.append(TABLE_START);
        buf1.append(TR_START);
        buf1.append(TD_ALIGN_LEFT);
        buf1.append(dateRecieved);
        buf1.append(TD_END);
        buf1.append(TR_END);
        if (dateCollected != null)
        {
            buf1.append(SPACER);
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(BOLD_START);
            buf1.append(UNDERLINE_BEGIN);
            buf1.append(DATE_COLLECTED_LABEL);
            buf1.append(BOLD_END);
            buf1.append(UNDERLINE_END);
            buf1.append(TD_END);
            buf1.append(TR_END);
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(dateCollected);
            buf1.append(TD_END);
            buf1.append(TR_END);
            buf1.append(SPACER);
        }
        buf1.append(TABLE_END);
        return buf1.toString();
    }

    public static String getOrderedTestString(String link, LabReportSummaryVO lvo)
    {
        StringBuffer buf1 = new StringBuffer("");
        CachedDropDownValues cdv = new CachedDropDownValues();
        if (lvo != null)
        {
            // Resulted Tests
            // Loacl ID Start
            buf1.append(TABLE_START);
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(LOCAL_ID_LABEL)).append(LOCAL_ID_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append(lvo.getLocalId() == null ? "NA" : lvo.getLocalId());
            buf1.append(TD_END);
            buf1.append(TR_END);
            // Loacl ID End
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(REPORTING_FACILITY_LABEL)).append(REPORTING_FACILITY_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append(lvo.getReportingFacility() == null ? "NA" : lvo.getReportingFacility());
            buf1.append(TD_END);
            buf1.append(TR_END);
            // Ordering Provider: Start
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(ORDER_PROVIDER_LABEL)).append(ORDER_PROVIDER_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append((lvo.getProviderFullName() == null || lvo.getProviderFullName().trim().length() == 0) ? "No Information Given"
                    : lvo.getProviderFullName());
            buf1.append(TD_END);
            buf1.append(TR_END);
            // Ordering Provider: end
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(ORDERED_TEST_LABEL)).append(ORDERED_TEST_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append(lvo.getOrderedTest() == null ? "No Information Given" : lvo.getOrderedTest());
            buf1.append(TD_END);
            buf1.append(TR_END);
            // Accession Number: Start
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(ACCESSION_NUMBER_LABEL)).append(ACCESSION_NUMBER_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append((lvo.getAccessionNumber() == null || lvo.getAccessionNumber().trim().length() == 0) ? "No Information Given"
                    : lvo.getAccessionNumber());
            buf1.append(TD_END);
            buf1.append(TR_END);
            // Accession Number End
            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(DATE_RECEIVED_LABEL)).append(DATE_RECEIVED_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append(link);
            buf1.append(TD_END);
            buf1.append(TR_END);

            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(DATE_COLLECTED_LABEL)).append(DATE_COLLECTED_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append(lvo.getDateCollected() == null ? "NA" : getDateString(lvo.getDateCollected()));
            buf1.append(TD_END);
            buf1.append(TR_END);

            buf1.append(TR_START);
            buf1.append(TD_ALIGN_RIGHT);
            buf1.append(BOLD_START);
            buf1.append(balanceSpaces(SPECIMEN_SOURCE_LABEL)).append(SPECIMEN_SOURCE_LABEL).append(COLON);
            buf1.append(BOLD_END);
            buf1.append(TD_END);
            buf1.append(TD_ALIGN_LEFT);
            buf1.append(SINGLE_SPACE);
            buf1.append((lvo.getSpecimenSource() == null || lvo.getSpecimenSource().trim().length() == 0) ? "NA" : (lvo
                    .getSpecimenSource()));
            buf1.append(TD_END);
            buf1.append(TR_END);
            if (lvo.getProcessingDecisionCd() != null)
            {
                buf1.append(TR_START);
                buf1.append(TD_ALIGN_RIGHT);
                buf1.append(BOLD_START);
                buf1.append(balanceSpaces(PROCESSING_DECISION_LABEL)).append(PROCESSING_DECISION_LABEL).append(COLON);
                buf1.append(BOLD_END);
                buf1.append(TD_END);
                buf1.append(TD_ALIGN_LEFT);
                buf1.append(SINGLE_SPACE);
                if (lvo.getIsAssociated())
                    buf1.append(cdv.getCodeShortDescTxt(lvo.getProcessingDecisionCd(), "CM_PROCESS_STAGE"));
                else
                    buf1.append(cdv.getCodeShortDescTxt(lvo.getProcessingDecisionCd(),
                            "STD_SYPHILIS_NONSYPHILIS_PROCESSING_DECISION"));
                buf1.append(TD_END);
                buf1.append(TR_END);
            }
            buf1.append(SPACER);
            buf1.append(TABLE_END);
            return buf1.toString();
        }
        else
            return "";
    }

    /**
     * Gets the Action Name for the passed ModuleName. Deletes all the spaces in
     * the Module Name.
     * 
     * @param moduleName
     *            String
     * @return String
     */
    public static String getResultedTestsString(Collection<Object> resultedTests)
    {
        if (resultedTests != null)
        {
            StringBuffer buf1 = new StringBuffer("");
            buf1.append(TABLE_START);
            Iterator it = resultedTests.iterator();
            int i = 1;
            while (it.hasNext())
            {
                ResultedTestSummaryVO rvo = (ResultedTestSummaryVO) it.next();
                int j = 0;
                // Resulted Tests
                if (rvo.getResultedTest() != null)
                {
                    buf1.append(TR_START);
                    buf1.append(TD_ALIGN_RIGHT);
                    buf1.append(BOLD_START);
                    buf1.append(balanceSpaces(RESULTED_TEST_LABEL)).append(RESULTED_TEST_LABEL).append(SINGLE_SPACE)
                            .append(String.valueOf(i)).append(COLON);
                    buf1.append(BOLD_END);
                    buf1.append(TD_END);
                    buf1.append(TD_ALIGN_LEFT);
                    buf1.append(SINGLE_SPACE);
                    buf1.append(rvo.getResultedTest() == null ? "" : rvo.getResultedTest());
                    buf1.append(TD_END);
                    buf1.append(TR_END);
                }
                if (rvo.getNumericResultCompare() != null || rvo.getNumericResultValue1() != null)
                {
                    buf1.append(TR_START);
                    buf1.append(TD_ALIGN_RIGHT);
                    buf1.append(BOLD_START);
                    buf1.append(balanceSpaces(NUMERIC_RESULT_LABEL)).append(NUMERIC_RESULT_LABEL).append(SINGLE_SPACE)
                            .append(String.valueOf(i)).append(COLON);
                    buf1.append(BOLD_END);
                    buf1.append(TD_END);
                    buf1.append(TD_ALIGN_LEFT);
                    buf1.append(SINGLE_SPACE);
                    buf1.append(rvo.getNumericResultCompare() == null ? "" : (rvo.getNumericResultCompare()));
                    buf1.append(rvo.getNumericResultValue1() == null ? "" : (rvo.getNumericResultValue1().toString()));
                    buf1.append(rvo.getNumericResultSeperator() == null ? "" : (rvo.getNumericResultSeperator()));
                    buf1.append(rvo.getNumericResultValue2() == null ? "" : (rvo.getNumericResultValue2().toString()));
                    buf1.append(rvo.getNumericResultUnits() == null ? "" : (rvo.getNumericResultUnits()));
                    buf1.append(TD_END);
                    buf1.append(TR_END);
                }
                if (rvo.getTextResultValue() != null)
                {
                    buf1.append(TR_START);
                    buf1.append(TD_ALIGN_RIGHT);
                    buf1.append(BOLD_START);
                    buf1.append(balanceSpaces(TEXT_RESULT_LABEL)).append(TEXT_RESULT_LABEL).append(SINGLE_SPACE)
                            .append(String.valueOf(i)).append(COLON);
                    buf1.append(BOLD_END);
                    buf1.append(TD_END);
                    buf1.append(TD_ALIGN_LEFT);
                    buf1.append(SINGLE_SPACE);
                    buf1.append(rvo.getTextResultValue() == null ? "" : (rvo.getTextResultValue()));
                    buf1.append(TD_END);
                    buf1.append(TR_END);
                }
                // Test Status [N]: Start
                if (rvo.getResultedTestStatus() != null)
                {
                    buf1.append(TR_START);
                    buf1.append(TD_ALIGN_RIGHT);
                    buf1.append(BOLD_START);
                    buf1.append(balanceSpaces(TEST_STATUS_LABEL)).append(TEST_STATUS_LABEL).append(SINGLE_SPACE)
                            .append(String.valueOf(i)).append(COLON);
                    buf1.append(BOLD_END);
                    buf1.append(TD_END);
                    buf1.append(TD_ALIGN_LEFT);
                    buf1.append(SINGLE_SPACE);
                    buf1.append(rvo.getResultedTestStatus() == null ? "N/A" : (rvo.getResultedTestStatus()));
                    buf1.append(TD_END);
                    buf1.append(TR_END);
                }
                // Test Status [N]: End

                if (rvo.getCodedResultValue() != null && rvo.getCtrlCdUserDefined1() != null
                        && rvo.getCtrlCdUserDefined1().equals("N"))
                {
                    buf1.append(TR_START);
                    buf1.append(TD_ALIGN_RIGHT);
                    buf1.append(BOLD_START);
                    buf1.append(balanceSpaces(CODED_RESULT_LABEL)).append(CODED_RESULT_LABEL).append(SINGLE_SPACE)
                            .append(String.valueOf(i)).append(COLON);
                    buf1.append(BOLD_END);
                    buf1.append(TD_END);
                    buf1.append(TD_ALIGN_LEFT);
                    buf1.append(SINGLE_SPACE);
                    buf1.append(rvo.getCodedResultValue() == null ? "" : rvo.getCodedResultValue());
                    buf1.append(TD_END);
                    buf1.append(TR_END);
                }
                else if (rvo.getOrganismName() != null)
                {
                    buf1.append(TR_START);
                    buf1.append(TD_ALIGN_RIGHT);
                    buf1.append(BOLD_START);
                    buf1.append(balanceSpaces(ORGANISM_NAME_LABEL)).append(ORGANISM_NAME_LABEL).append(SINGLE_SPACE)
                            .append(String.valueOf(i)).append(COLON);
                    buf1.append(BOLD_END);
                    buf1.append(TD_END);
                    buf1.append(TD_ALIGN_LEFT);
                    buf1.append(SINGLE_SPACE);
                    buf1.append(rvo.getOrganismName() == null ? "" : rvo.getOrganismName());
                    buf1.append(TD_END);
                    buf1.append(TR_END);
                }
                if (rvo.getTheSusTestSummaryVOColl() != null)
                {
                    Iterator<Object> ite = rvo.getTheSusTestSummaryVOColl().iterator();
                    while (ite.hasNext())
                    {
                        ResultedTestSummaryVO susceptibility = (ResultedTestSummaryVO) ite.next();
                        buf1.append(TR_START);
                        buf1.append(TD_ALIGN_RIGHT);
                        buf1.append(BOLD_START);
                        buf1.append(balanceSpaces(DRUG_NAME_LABEL)).append(DRUG_NAME_LABEL).append(SINGLE_SPACE)
                                .append(NumberUtil.toRoman(j)).append(COLON);
                        buf1.append(BOLD_END);
                        buf1.append(TD_END);
                        buf1.append(TD_ALIGN_LEFT);
                        buf1.append(SINGLE_SPACE);
                        buf1.append(susceptibility.getResultedTest() == null ? "" : susceptibility.getResultedTest());
                        buf1.append(TD_END);
                        buf1.append(TR_END);

                        if (susceptibility.getNumericResultCompare() != null
                                || susceptibility.getNumericResultValue1() != null)
                        {
                            buf1.append(TR_START);
                            buf1.append(TD_ALIGN_RIGHT);
                            buf1.append(BOLD_START);
                            buf1.append(balanceSpaces(NUMERIC_RESULT_LABEL)).append(NUMERIC_RESULT_LABEL)
                                    .append(SINGLE_SPACE).append(NumberUtil.toRoman(j)).append(COLON);
                            buf1.append(BOLD_END);
                            buf1.append(TD_END);
                            buf1.append(TD_ALIGN_LEFT);
                            buf1.append(SINGLE_SPACE);
                            buf1.append(susceptibility.getNumericResultCompare() == null ? "" : (susceptibility
                                    .getNumericResultCompare()));
                            buf1.append(susceptibility.getNumericResultValue1() == null ? "" : (susceptibility
                                    .getNumericResultValue1().toString()));
                            buf1.append(susceptibility.getNumericResultSeperator() == null ? "" : (susceptibility
                                    .getNumericResultSeperator()));
                            buf1.append(susceptibility.getNumericResultValue2() == null ? "" : (susceptibility
                                    .getNumericResultValue2().toString()));
                            buf1.append(susceptibility.getNumericResultUnits() == null ? "" : (susceptibility
                                    .getNumericResultUnits()));
                            buf1.append(TD_END);
                            buf1.append(TR_END);
                        }
                        else if (susceptibility.getCodedResultValue() != null)
                        {
                            buf1.append(TR_START);
                            buf1.append(TD_ALIGN_RIGHT);
                            buf1.append(BOLD_START);
                            buf1.append(balanceSpaces(CODED_RESULT_LABEL)).append(CODED_RESULT_LABEL)
                                    .append(SINGLE_SPACE).append(NumberUtil.toRoman(j)).append(COLON);
                            buf1.append(BOLD_END);
                            buf1.append(TD_END);
                            buf1.append(TD_ALIGN_LEFT);
                            buf1.append(SINGLE_SPACE);
                            buf1.append(susceptibility.getCodedResultValue() == null ? "" : susceptibility
                                    .getCodedResultValue());
                            buf1.append(TD_END);
                            buf1.append(TR_END);
                        }
                        j++;
                    }
                }
                buf1.append(SPACER);
                i++;
            }
            buf1.append(TABLE_END);
            return buf1.toString();
        }
        else
            return "";
    }

    private static String balanceSpaces(String label)
    {
        StringBuffer sb = new StringBuffer("");
        if (label != null && label.length() < 15)
        {
            int i = 15 - label.length();
            for (int j = 0; j < i; j++)
            {
                sb.append(SINGLE_SPACE);
            }
        }
        return sb.toString();
    }

    protected static String getDateString(java.sql.Timestamp timestamp)
    {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        if (timestamp != null)
        {
            date = new Date(timestamp.getTime());
        }
        if (date == null)
        {
            return null;
        }
        else
        {
            return formatter.format(date);
        }
    }

    public Map<Object, Object> createIndividualNotificationMap(Collection<Object> notificationSummaryVOCollection)
    {
        Map notificationMap = new HashMap<Object, Object>();
        if (notificationSummaryVOCollection != null && notificationSummaryVOCollection.size() > 0)
        {
            Iterator ite = notificationSummaryVOCollection.iterator();
            while (ite.hasNext())
            {
                NotificationSummaryVO notSum = (NotificationSummaryVO) ite.next();
                if (notificationMap.containsKey(notSum.getNotificationUid()))
                {
                    ((Collection<Object>) notificationMap.get(notSum.getNotificationUid())).add(notSum);
                }
                else
                {
                    Collection<Object> notCollection = new ArrayList<Object>();
                    notCollection.add(notSum);
                    notificationMap.put(notSum.getNotificationUid(), notCollection);
                }
            }
        }
        return notificationMap;
    }

    private String displayPrimaryNotification()
    {
        StringBuffer sb = new StringBuffer();

        // sb.append("<tr>");
        // sb.append("<td align=\"center\">");
        sb.append("<table id=\"notificationHistoryTable\" class=\"dtTable\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<td style=\"background-color: #EFEFEF; border:1px solid #666666;\" width=\"2%\"/>");
        sb.append("<th width=\"20%\">Status Change Date</th>");
        sb.append("<th>Date Sent</th>");
        sb.append("<th>Jurisdiction</th>");
        sb.append("<th>Case Status</th>");
        sb.append("<th>Status</th>");
        sb.append("<th>Type</th>");
        sb.append("<th>Recipient</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        return sb.toString();
    }



    public String buildNotificationList(Collection<Object> notificationSummaryVOCollection)
    {
        boolean isFirst = true;
        int recordIndex = 0;
        Map notificationMap = createIndividualNotificationMap(notificationSummaryVOCollection);

        int notificationsSize = notificationMap.size();

        StringBuffer sb = new StringBuffer();

        sb.append(displayPrimaryNotification());

        if (notificationsSize == 0)
        {
            sb.append("<tbody><tr class=\"empty\"><td colspan=\"8\">Nothing found to display.</td></tr>");
            sb.append("</tbody></table></td></tr>");
            return sb.toString();
        }
        else
        {
            Set notificationKeys = notificationMap.keySet();
            Iterator ite = notificationKeys.iterator();
            while (ite.hasNext())
            {
                Long key = (Long) ite.next();
                Collection<Object> notificationCollection = (Collection) notificationMap.get(key);
                int notCollection = notificationCollection.size();
                isFirst = true;
                CachedDropDownValues cdv = new CachedDropDownValues();
                Iterator anIter = notificationCollection.iterator();

                String positionClass = "";
                if (recordIndex % 2 == 1)
                {
                    positionClass = "odd";
                }
                else
                {
                    positionClass = "even";
                }

                sb.append("<tbody>");

                int rowIndex = 0;
                while (anIter.hasNext())
                {
                    String dateCreated = "";
                    String dateSent = "";
                    String jurisdictionDescTxt = "";
                    String caseStatus = "";
                    String recordStatus = "";
                    String comments = "";
                    String type = "";
                    String recipient = "";

                    NotificationSummaryVO notSum = (NotificationSummaryVO) anIter.next();

                    // ignore records that have recordStatus as
                    // "IN_BATCH_PROCESS"
                    if (notSum.getRecordStatusCd().equals("IN_BATCH_PROCESS"))
                    {
                        recordIndex++;
                        continue;
                    }

                    recordStatus = notSum.getRecordStatusCd();
                    dateCreated = StringUtils.formatDate(notSum.getRecordStatusTime());

                    if (recordStatus.equals("COMPLETED"))
                        dateSent = StringUtils.formatDate(notSum.getRptSentTime());

                    jurisdictionDescTxt = cdv.getJurisdictionDesc(notSum.getJurisdictionCd());

                    caseStatus = notSum.getCaseClassCdTxt() == null ? "" : notSum.getCaseClassCdTxt();
                    comments = notSum.getTxt() == null ? "" : notSum.getTxt();

                    type = notSum.getNotificationSrtDescCd();
                    recipient = notSum.getRecipient();

                    String parentClass = "parent_" + notSum.getNotificationUid();
                    String childClass = "child_" + notSum.getNotificationUid();
                    String invisibleChildClass = "none " + childClass;

                    if (isFirst)
                    {
                        sb.append("<tr class=\"" + parentClass + " " + positionClass + "\">");

                        if (notCollection > 1)
                            sb.append("<td align=\"center\" valign=\"middle\"><img alt=\"\" border=\"0\" "
                                    + "src=\"plus_sign.gif\" onclick=\"displayNotifications('" + parentClass
                                    + "');\"/></td>");
                        else
                            sb.append("<td/>");
                    }
                    else
                    {
                        sb.append("<tr class=\"" + invisibleChildClass + " " + positionClass + "\">");
                        sb.append("<td/>");
                    }

                    sb.append("<td>" + dateCreated + "</td>");
                    sb.append("<td>" + dateSent + "</td>");
                    sb.append("<td>" + jurisdictionDescTxt + "</td>");
                    sb.append("<td>" + caseStatus + "</td>");
                    sb.append("<td>" + recordStatus + "</td>");
                    sb.append("<td>" + type + "</td>");
                    sb.append("<td>" + recipient + "</td>");
                    sb.append("</tr>");

                    if (isFirst)
                    {
                        sb.append("<tr class=\"" + positionClass + "\">");
                    }
                    else
                    {
                        sb.append("<tr class=\"" + invisibleChildClass + " " + positionClass + "\">");
                    }

                    sb.append("<td style=\"border-bottom:3px solid #DDD;\"> </td>");
                    sb.append("<td colspan=\"7\" style=\"border-bottom:3px solid #DDD;\"><b>Comments: </b>" + comments
                            + "</td>");
                    sb.append("</tr>");

                    isFirst = false;
                }
                recordIndex++;
            }
            sb.append("</tbody></table>");
            return sb.toString();
        }
    }


    private String displayPrimaryInvHistory()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<table id=\"invHistoryTable\" class=\"dtTable\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<td style=\"background-color: #EFEFEF; border:1px solid #666666\"/>");
        sb.append("<th width=\"15%\">Change Date</th>");
        sb.append("<th>User</th>");
        sb.append("<th>Jurisdiction</th>");
        sb.append("<th>Case Status</th>");
        sb.append("<th>Version</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        return sb.toString();
    }

    public String buildInvHistoryList(Collection<Object> invHistorySummaryVOCollection)
    {

        boolean isFirst = true;
        int invHistSize = invHistorySummaryVOCollection.size();

        StringBuffer sb = new StringBuffer();

        sb.append(displayPrimaryInvHistory());

        if (invHistSize == 0)
        {
            sb.append("<tbody><tr class=\"empty\"><td colspan=\"8\">Nothing found to display.</td></tr>");
            sb.append("</tbody></table></td></tr>");
            return sb.toString();
        }

        Iterator anIter = invHistorySummaryVOCollection.iterator();
        sb.append("<tbody>");

        int rowIndex = 0;
        while (anIter.hasNext())
        {
            rowIndex++;
            String rowCssClass = "";

            if (rowIndex % 2 == 1)
            {
                rowCssClass = "odd";
            }
            else
            {
                rowCssClass = "even";
            }

            String changeDate = "";
            String user = "";
            String jurisdictionDescTxt = "";
            String caseStatus = "";
            String version = "";

            InvestigationAuditLogSummaryVO summaryVO = (InvestigationAuditLogSummaryVO) anIter.next();

            changeDate = summaryVO.getChangeDate();
            user = summaryVO.getUserName();
            jurisdictionDescTxt = summaryVO.getJuridictionText();
            caseStatus = summaryVO.getCaseStatusText() == null ? "" : summaryVO.getCaseStatusText();
            version = summaryVO.getVersionCtrlNbr().toString();

            if (isFirst)
            {
                sb.append("<tr style=\"display:;\" class=\"" + rowCssClass + "\">");

                if (invHistSize > 1)
                    sb.append("<td align=\"center\" valign=\"middle\"><img alt=\"\" border=\"0\" "
                            + "src=\"plus_sign.gif\" onclick=\"displayInvHistory(this);\"/></td>");
                else
                    sb.append("<td/>");
            }
            else
            {
                sb.append("<tr style=\"display:none;\" class=\"" + rowCssClass + "\">");
                sb.append("<td/>");
            }

            sb.append("<td>" + changeDate + "</td>");
            sb.append("<td>" + user + "</td>");
            sb.append("<td>" + jurisdictionDescTxt + "</td>");
            sb.append("<td>" + caseStatus + "</td>");
            sb.append("<td>" + version + "</td>");
            sb.append("</tr>");

            isFirst = false;
        }
        sb.append("</tbody></table>");

        return sb.toString();
    }

    public static String getResultedTestsStringForWorup(Collection<Object> resultedTests)
    { int index=0;
    
        if (resultedTests != null && resultedTests.size() > 0)
        {
            NedssUtils nUtil = new NedssUtils();
            nUtil.sortObjectByColumn("getResultedTest", resultedTests, true);
            StringBuffer buf1 = new StringBuffer("");
            Iterator<Object> it = resultedTests.iterator();
            int i = 1;
            buf1.append(UL_START);
            while (it.hasNext())
            {
                ResultedTestSummaryVO rvo = (ResultedTestSummaryVO) it.next();
                int j = 0;
                // Resulted Tests
                if (rvo.getResultedTest() != null)
                {
                    buf1.append(LI_START);
                    buf1.append(BOLD_START);
                    buf1.append(rvo.getResultedTest() == null ? "" : rvo.getResultedTest().trim()).append(COLON)
                            .append(SINGLE_SPACE);
                    buf1.append(BOLD_END);
                  //  buf1.append(BR);
                }
				
                if (rvo.getTextResultValue() != null)
                {
                	buf1.append(BR);//Added
                	buf1.append(rvo.getTextResultValue() == null ? "" : (rvo.getTextResultValue()));
                    if (rvo.getResultedTestStatus() != null)
                        buf1.append(" - (").append(rvo.getResultedTestStatus()).append(")");
                  //  buf1.append(BR);
                }

                if (rvo.getCodedResultValue() != null && rvo.getCtrlCdUserDefined1() != null
                        && rvo.getCtrlCdUserDefined1().equals("N"))
                {
                	buf1.append(BR);//added
                	buf1.append(rvo.getCodedResultValue() == null ? "" : rvo.getCodedResultValue());
                    if (rvo.getResultedTestStatus() != null)
                        buf1.append(" - (").append(rvo.getResultedTestStatus()).append(")");
                    //buf1.append(BR);
                }
                else if (rvo.getOrganismName() != null)
                {
                	buf1.append(BR);//added
                    buf1.append(rvo.getOrganismName() == null ? "" : rvo.getOrganismName());
                   // buf1.append(BR);
                }
                if ((rvo.getNumericResultCompare() != null
						&& !rvo.getNumericResultCompare().contains("<NULL>") && !rvo
						.getNumericResultCompare().trim().equals(""))
						|| rvo.getNumericResultValue1() != null
						|| (rvo.getLowRange() != null && rvo.getLowRange()
								.trim().length() != 0)
						|| (rvo.getHighRange() != null && rvo.getHighRange()
								.trim().length() != 0)) {
                	//buf1.append(BR);//Added
                	if(rvo.getNumericResultCompare() == null)
                		 buf1.append("");
                	else{
                		buf1.append(BR);
                		buf1.append(rvo.getNumericResultCompare());
                	}
                		
                   // buf1.append(rvo.getNumericResultCompare() == null ? "" : (rvo.getNumericResultCompare()));
                    buf1.append(rvo.getNumericResultValue1() == null ? "" : (rvo.getNumericResultValue1().toString()));
                    buf1.append(rvo.getNumericResultSeperator() == null ? "" : (rvo.getNumericResultSeperator()));
                    buf1.append(rvo.getNumericResultValue2() == null ? "" : (rvo.getNumericResultValue2().toString()));
                    buf1.append(rvo.getNumericResultUnits() == null ? "" : (" " + rvo.getNumericResultUnits()));
                    if (rvo.getLowRange() != null || rvo.getHighRange() != null)
                    {
                    	buf1.append(BR);
                    	buf1.append("<B>Reference Range:</B>");
                        buf1.append(" (").append(rvo.getLowRange() == null ? "" : rvo.getLowRange())
                                .append(rvo.getHighRange() == null ? "" : "-" + rvo.getHighRange()).append(")");
                    }
                    if (rvo.getResultedTestStatus() != null)
                        buf1.append(" - (").append(rvo.getResultedTestStatus()).append(")");
                    buf1.append(BR);
                    //buf1.append(BR);//Added
                }
                if (rvo.getTheSusTestSummaryVOColl() != null && rvo.getTheSusTestSummaryVOColl().size() > 0)
                {
                    nUtil.sortObjectByColumn("getResultedTest", rvo.getTheSusTestSummaryVOColl(), true);
                    Iterator<Object> ite = rvo.getTheSusTestSummaryVOColl().iterator();
                    buf1.append("<br><div onmouseout='showHideReflex(this)' onmouseover='showHideReflex(this)'><a style='cursor:pointer'>Show Reflex Test Results</a><br><br><div style='display:none'>");
                    index++;
                    buf1.append(UL_START);
                    while (ite.hasNext())
                    {
                        ResultedTestSummaryVO susceptibility = (ResultedTestSummaryVO) ite.next();
                        buf1.append(LI_START);
                        buf1.append(BOLD_START);
                        buf1.append(susceptibility.getResultedTest() == null ? "" : susceptibility.getResultedTest())
                                .append(COLON).append(SINGLE_SPACE);
                        buf1.append(BOLD_END);
                        
                        if (susceptibility.getOrganismName() != null)
                        {
                            buf1.append(susceptibility.getOrganismName() == null ? "" : susceptibility
                                    .getOrganismName());
                           /* if (susceptibility.getResultedTestStatus() != null)
                                buf1.append(" - (").append(susceptibility.getResultedTestStatus()).append(")");*/
                        }
                        else if (susceptibility.getNumericResultCompare() != null
                                || susceptibility.getNumericResultValue1() != null)
                        {
                        	//buf1.append("<B>Reference Range:</B>");
                            buf1.append(susceptibility.getNumericResultCompare() == null ? "" : (susceptibility
                                    .getNumericResultCompare()));
                            buf1.append(susceptibility.getNumericResultValue1() == null ? "" : (susceptibility
                                    .getNumericResultValue1().toString()));
                            buf1.append(susceptibility.getNumericResultSeperator() == null ? "" : (susceptibility
                                    .getNumericResultSeperator()));
                            buf1.append(susceptibility.getNumericResultValue2() == null ? "" : (susceptibility
                                    .getNumericResultValue2().toString()));
                            buf1.append(susceptibility.getNumericResultUnits() == null ? "" : (" " + susceptibility
                                    .getNumericResultUnits()));
                            if (rvo.getLowRange() != null || rvo.getHighRange() != null)
                            {
                            	buf1.append(BR);
                            	buf1.append("<B>Reference Range:</B>");
                                buf1.append(susceptibility.getLowRange() == null ? "" : susceptibility.getLowRange())
                                        .append(susceptibility.getHighRange() == null ? "" : "-" + susceptibility.getHighRange());
                            }
                            if (susceptibility.getResultedTestStatus() != null)
                                buf1.append(" - (").append(susceptibility.getResultedTestStatus()).append(")");
                        }
                        buf1.append(BR);//Added
                        buf1.append(LI_END);
                        j++;
                    }
                    buf1.append(UL_END);
                    buf1.append("</div></div>");
                }
                i++;
                buf1.append(BR);//added
                buf1.append(LI_END);
            }
            buf1.append(UL_END);
            return buf1.toString();
        }
        else
            return "";
    }
    
    public static ArrayList<String> getResultedDescription(Collection<Object> resultedTests){
    	ArrayList<String> descriptions = new ArrayList<String>();
    	if (resultedTests != null && resultedTests.size() > 0)
    	{
    		NedssUtils nUtil = new NedssUtils();
    		nUtil.sortObjectByColumn("getResultedTest", resultedTests, true);
    		
    		Iterator<Object> it = resultedTests.iterator();
    		
    		
    		while (it.hasNext())
    		{
    			ResultedTestSummaryVO rvo = (ResultedTestSummaryVO) it.next();
    		
    			// Resulted Tests
    			if (rvo.getResultedTest() != null)
    			{
    				descriptions.add(rvo.getResultedTest().trim());
    				
    			}
    		}
    	}
    	return descriptions;
    }
}