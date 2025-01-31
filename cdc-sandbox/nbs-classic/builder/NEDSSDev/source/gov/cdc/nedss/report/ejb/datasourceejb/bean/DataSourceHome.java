package gov.cdc.nedss.report.ejb.datasourceejb.bean;

import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.report.vo.*;

import java.rmi.*;

import javax.ejb.*;
import javax.ejb.EJBHome;

public interface DataSourceHome extends EJBHome
{

    /**
    * @return javax.activation.DataSource
    * @roseuid 3C0D3CF700EC
    */
    public DataSource create(DataSourceVO dataSourceVO) throws CreateException, RemoteException, NEDSSSystemException;

    /**
    * @param primaryKey
    * @return DataSource
    * @roseuid 3C0D3CF700F6
    */
    public DataSource findByPrimaryKey(Long primaryKey) throws RemoteException, CreateException, DuplicateKeyException, FinderException, EJBException, NEDSSSystemException;

}
