package gov.cdc.nedss.report.ejb.sas.bean;

import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.report.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.rmi.*;

import javax.ejb.*;

public interface SASEngine extends EJBObject
{

    public String runReport(RunReportVO reunReportVO, ReportVO reportVO, DataSourceVO dsvo, NBSSecurityObj securityObj) throws RemoteException;

}
