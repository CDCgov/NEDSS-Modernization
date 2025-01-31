package gov.cdc.nedss.report.util;

import gov.cdc.nedss.report.ejb.datasourceejb.bean.DataSourceHome;
import gov.cdc.nedss.report.ejb.reportejb.bean.ReportHome;
import gov.cdc.nedss.report.ejb.sas.bean.SASEngineHome;
import gov.cdc.nedss.util.JNDINames;

/**
* Name:     ReportJNDINames.java
* Description:  This class is used to store the JNDI names of various entities.
*               Change made here should be reflected in the deployment descriptors.
* Copyright:    Copyright (c) 2001
* Company:  Computer Sciences Corporation
* @author   Pradeep Sharma & NEDSS Development Team
* @version  1.0
**/
public interface ReportJndiNames
{

    public static final String DATA_SOURCE_ROOT_DAO_CLASS = "gov.cdc.nedss.report.ejb.datasourceejb.dao.DataSourceRootDAOImpl";
    public static final String DATA_SOURCE_DAO_CLASS = "gov.cdc.nedss.report.ejb.datasourceejb.dao.DataSourceDAOImpl";
    public static final String DATA_SOURCE_COLUMN_DAO_CLASS = "gov.cdc.nedss.report.ejb.datasourceejb.dao.DataSourceColumnDAOImpl";
    public static final String REPORT_DAO_CLASS = "gov.cdc.nedss.report.ejb.reportejb.dao.ReportDAOImpl";
    public static final String REPORT_FILTER_DAO_CLASS = "gov.cdc.nedss.report.ejb.reportejb.dao.ReportFilterDAOImpl";
    public static final String DISPLAY_COLUMN_DAO_CLASS = "gov.cdc.nedss.report.ejb.reportejb.dao.DisplayColumnDAOImpl";
    public static final String REPORT_ROOT_DAO_CLASS = "gov.cdc.nedss.report.ejb.reportejb.dao.ReportRootDAOImpl";

}
