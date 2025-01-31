package gov.cdc.nedss.report.ejb.datasourceejb.bean;

import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.report.vo.*;

import java.rmi.*;

import javax.ejb.*;
import javax.ejb.EJBObject;

public interface DataSource extends EJBObject
{

    /**
    * @param dataSourceVO
    * @roseuid 3C180238019D
    */
    public DataSourceVO setDataSourceVO(DataSourceVO dataSourceVO) throws RemoteException;

    /**
    * @return getDataSourceVO
    * @roseuid 3C18021F02A5
    */
    public DataSourceVO getDataSourceVO() throws RemoteException;

}
