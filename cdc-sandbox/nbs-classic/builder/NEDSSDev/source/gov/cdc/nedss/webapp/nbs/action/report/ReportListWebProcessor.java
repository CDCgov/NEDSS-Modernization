package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.report.dt.ReportDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.XMLRequestHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ReportListWebProcessor
{

    static final LogUtils logger = new LogUtils((ReportWebProcessor.class).getName());
    protected static boolean DEBUG_MODE;

    public ReportListWebProcessor()
    {
    }

    public static String getPrivateReportList(ArrayList<Object>  pvtList)
    {
        return makeReportList(pvtList);
    }

    public static String getPublicReportList(ArrayList<Object>  pubList)
    {
        return makeReportList(pubList);
    }

    public static String getReportTemplateList(ArrayList<Object>  tmpList)
    {
        return makeReportList(tmpList);
    }

    public static String getReportingFacilityReportList(ArrayList<Object>  tmpList)
    {
        return makeReportList(tmpList);
    }    
    /**
     * makeReportList
     */
    private static String makeReportList(ArrayList<Object>  list)
    {
        //logger.info("~~inside 40 -- RLWP");
        String time = null;
       Iterator<Object>  iterator = null;
        StringBuffer sb = new StringBuffer("<table role=\"presentation\">");
        for(iterator = list.iterator(); iterator.hasNext();)
        {
            ReportDT reportDT = (ReportDT)iterator.next();
            sb.append("<record>");
            sb.append("<field>");
            sb.append(reportDT.getDataSourceUid());
            sb.append("</field>");
            sb.append("<field>");
            sb.append(reportDT.getReportUid());
            sb.append("</field>");
            sb.append("<field>");
            String reportTitle = reportDT.getReportTitle();
            if(reportTitle == null)
            {
                reportTitle = "N/A";
            }
            sb.append(XMLRequestHelper.urlEncode(reportTitle));
            sb.append("</field>");
            sb.append("<field>");
            String strDescription = reportDT.getDescTxt();
            if(strDescription == null)
            {
                sb.append("N/A");
            }
            else
            {
                sb.append(XMLRequestHelper.urlEncode(strDescription));
            }
            sb.append("</field>");
            sb.append("<field>");
            if(reportDT.getStatusTime() == null)
            {
                sb.append("N/A");
                time = "0";
            }
            else
            {
                java.sql.Timestamp tStamp = reportDT.getStatusTime();
                Date date = new Date(tStamp.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                sb.append(XMLRequestHelper.xmlEncode(formatter.format(date)));
                long dt = date.getTime();
                String st = new String(Long.toString(dt));
                time = st;
            }
            sb.append("</field>");
            logger.info("~~~~ time is: " + time);
            sb.append("<field>");
            String strShared = reportDT.getShared();
            /* Commented out as avr.xsl compares against Shared IND than Shared DescTxt
            if(strShared.equalsIgnoreCase("P"))
            {
                strShared = "Private";
            }
            else if(strShared.equalsIgnoreCase("S"))
            {
                strShared = "Public";
            }
            else if(strShared.equalsIgnoreCase("T"))
            {
                strShared = "Template";
            }
            else if(strShared.equalsIgnoreCase("R"))
            {
                strShared = "Reporting Facility";
            }           
            */ 
            sb.append(XMLRequestHelper.xmlEncode(strShared));
            sb.append("</field>");
            sb.append("<field>");
            sb.append(XMLRequestHelper.xmlEncode(reportDT.getReportTypeCode()));
            sb.append("</field>");
            sb.append("<field>");
            sb.append(reportDT.getOwnerUid());
            sb.append("</field>");
            sb.append("<field>");
            sb.append(XMLRequestHelper.xmlEncode(time));
            sb.append("</field>");
            sb.append("</record>");
        }
        sb.append("</table>");
        return sb.toString();
    }

}
