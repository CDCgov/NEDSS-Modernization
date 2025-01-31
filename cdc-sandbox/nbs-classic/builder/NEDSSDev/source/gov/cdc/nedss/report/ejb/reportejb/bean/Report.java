package gov.cdc.nedss.report.ejb.reportejb.bean;

import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.report.vo.*;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface Report extends EJBObject
{

    /**
    * @param reportVO
    * @roseuid 3C1800B801F5
    */
    public void setReportVO(ReportVO reportVO) throws RemoteException;

    /**
    * @return ReportVO
    * @roseuid 3C18009E01EE
    */
    public ReportVO getReportVO() throws RemoteException;

}
