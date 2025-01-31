package gov.cdc.nedss.report.ejb.reportejb.bean;

import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.report.vo.*;

import java.rmi.RemoteException;

import javax.ejb.*;
import javax.ejb.EJBHome;

public interface ReportHome extends EJBHome
{

    /**
    * @return Schemas.ODS.Report
    * @roseuid 3C0D3B6503BF
    */
    public Report create(ReportVO reportVO) throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException;

    /**
    * @param primaryKey
    * @return Report
    * @roseuid 3C0D3B6503C9
    */
    public Report findByPrimaryKey(Long primaryKey) throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}
