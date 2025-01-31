package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.reportadmin.dao.UserProfileDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;


public class ReportAdminUtil {
	
    private static final int STRING_BUFFER_SIZE = 1024;
    private static final Logger logger = Logger.getLogger(ReportAdminUtil.class);
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();
    private static final UserProfileDAO daoUserProfile = new UserProfileDAO();
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();

    
    /**
     * getSASProgramSRT returns the available sas programs
     * @return
     */
    public String getSASProgramSRT() throws Exception
    {
    	PropertyUtil properties = PropertyUtil.getInstance();
        String reportAdminSasPgmLoc = properties.getReportAdminSasPgmLocation();
        StringBuffer sb = new StringBuffer(STRING_BUFFER_SIZE);
        // List<Object> the files in that directory.
        File filPGM = null;
        String[] arrPGM = null;
        try
        {
            filPGM = new File(reportAdminSasPgmLoc);
            arrPGM = filPGM.list();
            // Loop through the list and assemble an SRT string.
            int x = 0;
            int y = arrPGM.length;

            for(x=0; x<y; x++)
            {
                if(arrPGM[x].startsWith("."))
                {
                    continue;
                }
                /*
                    The database has inconsistient case for filenames for standard reports.
                    The XSL does a case-sensitive comparison between SRT values and field values.
                    Therefore, to make the drop-down listboxes work correctly, we must
                    convert the filenames to upper-case.
                    Also in ReportDT.java and NewReport.java
                */
                sb.append(arrPGM[x].toUpperCase());
                sb.append("$");
                sb.append(arrPGM[x].toUpperCase());
                sb.append("|");
            }
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
            throw new NEDSSException("Please verify the property 'REPORT_ADMIN_SAS_PGM_LOCATION' in NEDSS.properties is configured correctly.");
        }
        return sb.toString();
    }    
    
    public String getReportTypeCdSRT() {
        // ReportAdmin.ReportTypeCodeSRT
        TreeMap<Object,Object> tmReportTypeCode = new TreeMap<Object,Object>();
        tmReportTypeCode.put("SAS_CUSTOM", "Custom");
        tmReportTypeCode.put("SAS_GRAPH", "Graph");
        tmReportTypeCode.put("SAS_ODS_HTML", "Table");
        StringBuffer sbReportTypeCode = new StringBuffer(STRING_BUFFER_SIZE);
        Set<Object> setReportTypeCode = tmReportTypeCode.keySet();
       Iterator<Object>  iReportTypeCode = setReportTypeCode.iterator();
        while(iReportTypeCode.hasNext())
        {
            String k = (String)iReportTypeCode.next();
            String v = (String)tmReportTypeCode.get(k);
            sbReportTypeCode.append(k);
            sbReportTypeCode.append("$");
            sbReportTypeCode.append(v);
            sbReportTypeCode.append("|");
        }
        return sbReportTypeCode.toString();
    }
    
     public String getSharedSRT() {
    	 
         // ReportAdmin.SharedSRT
         TreeMap<Object,Object> tmShared = new TreeMap<Object,Object>();
         tmShared.put("P", "Private");
         tmShared.put("S", "Public");
         tmShared.put("T", "Template");
         tmShared.put("R", "Reporting Facility");
         StringBuffer sbShared = new StringBuffer(STRING_BUFFER_SIZE);
         Set<Object> setShared = tmShared.keySet();
        Iterator<Object>  iShared = setShared.iterator();
         while(iShared.hasNext())
         {
             String k = (String)iShared.next();
             String v = (String)tmShared.get(k);
             sbShared.append(k);
             sbShared.append("$");
             sbShared.append(v);
             sbShared.append("|");
         }
         return sbShared.toString();    	 
     }
     public String getDataSourceSRT() {
    	 
         StringBuffer sbDataSource = new StringBuffer(STRING_BUFFER_SIZE);
         ArrayList<Object> alDataSource = daoDataSource.list();
        Iterator<Object>  iDataSource = alDataSource.iterator();
         while(iDataSource.hasNext())
         {
             DataSourceDT dtDataSource = (DataSourceDT)iDataSource.next();
             String k = dtDataSource.getDataSourceUID_s();
             String v = dtDataSource.getDataSourceName().toUpperCase();
             sbDataSource.append(k);
             sbDataSource.append("$");
             sbDataSource.append(v);
             sbDataSource.append("|");
         }
         return sbDataSource.toString();    	 
     }
     
     public String getUserProfileSRT() {
    	 
         StringBuffer sbUserProfile = new StringBuffer(STRING_BUFFER_SIZE);
         ArrayList<Object> alUserProfile = daoUserProfile.list();
        Iterator<Object>  iUserProfile = alUserProfile.iterator();
         sbUserProfile.append("0");
         sbUserProfile.append("$");
         sbUserProfile.append("SYSTEM");
         sbUserProfile.append("|");
         while(iUserProfile.hasNext())
         {
             UserProfileDT dtUserProfile = (UserProfileDT)iUserProfile.next();
             String k = dtUserProfile.getNEDSS_ENTRY_ID_s();
             String v = dtUserProfile.toString();
             sbUserProfile.append(k);
             sbUserProfile.append("$");
             sbUserProfile.append(v);
             sbUserProfile.append("|");
         }
         return sbUserProfile.toString();    	 
     }
     
     public String getDistinctCodeSetNm(String tableName) {
    	 
         StringBuffer codesetNm = new StringBuffer(STRING_BUFFER_SIZE);
         ArrayList<Object> alCodeSetNm = daoDataSource.getSRTCodeSetNames(tableName);
        Iterator<Object>  iCodeSetNm = alCodeSetNm.iterator();
         while(iCodeSetNm.hasNext())
         {
        	 String cdsetNm = (String) iCodeSetNm.next();
        	 codesetNm.append(cdsetNm);
        	 codesetNm.append("$");
        	 codesetNm.append(cdsetNm);
        	 codesetNm.append("|");
         }
         return codesetNm.toString();          
    	 
     }
     public String getDataSourceTitleSRT() {
    	 
         StringBuffer sbDataSource = new StringBuffer(STRING_BUFFER_SIZE);
         ArrayList<Object> alDataSource = daoDataSource.list();
        Iterator<Object>  iDataSource = alDataSource.iterator();
         while(iDataSource.hasNext())
         {
             DataSourceDT dtDataSource = (DataSourceDT)iDataSource.next();
             String k = dtDataSource.getDataSourceUID_s();
             String title = dtDataSource.getDataSourceTitle();
             sbDataSource.append(k);
             sbDataSource.append("$");
             sbDataSource.append(title);
             sbDataSource.append("|");
         }
         return sbDataSource.toString();    	 
     }     
     
     public String getDataSourceColumnTypes() {
         // ReportAdmin.ColumnTypeCodeSRT
         TreeMap<Object,Object> tmColumnTypeCode = new TreeMap<Object,Object>();
         tmColumnTypeCode.put("STRING", "String");
         tmColumnTypeCode.put("INTEGER", "Number");
         tmColumnTypeCode.put("DATETIME", "Date/Time");
         StringBuffer sbColumnTypeCode = new StringBuffer(STRING_BUFFER_SIZE);
         Set<Object> setColumnTypeCode = tmColumnTypeCode.keySet();
        Iterator<Object>  iColumnTypeCode = setColumnTypeCode.iterator();
         while(iColumnTypeCode.hasNext())
         {
             String k = (String)iColumnTypeCode.next();
             String v = (String)tmColumnTypeCode.get(k);
             sbColumnTypeCode.append(k);
             sbColumnTypeCode.append("$");
             sbColumnTypeCode.append(v);
             sbColumnTypeCode.append("|");
         }
         return sbColumnTypeCode.toString();    	 
     }
     public String getSectionCode() {
    	 
         StringBuffer sbReportSection = new StringBuffer(STRING_BUFFER_SIZE);
         ArrayList<Object> alReportSection = daoReportSection.list();
        Iterator<Object>  iReportSection = alReportSection.iterator();
         while(iReportSection.hasNext())
         {
        	 ReportSectionDT dtReportSection = (ReportSectionDT)iReportSection.next();
             long k = dtReportSection.getSectionCd();
             String title = dtReportSection.getSectionDescTxt();
             sbReportSection.append(k);
             sbReportSection.append("$");
             sbReportSection.append(title);
             sbReportSection.append("|");
         }
         return sbReportSection.toString();    	 
     }     

}