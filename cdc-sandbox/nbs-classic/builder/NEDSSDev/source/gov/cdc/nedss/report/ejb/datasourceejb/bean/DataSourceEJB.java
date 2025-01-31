package gov.cdc.nedss.report.ejb.datasourceejb.bean;

import gov.cdc.nedss.report.ejb.datasourceejb.dao.*;
import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.report.vo.*;
import gov.cdc.nedss.systemservice.util.*;

import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.*;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

public class DataSourceEJB implements EntityBean
{

    static final LogUtils logger = new LogUtils((DataSourceEJB.class).getName());
    public EntityContext EJB_Context;
    public Connection EJB_Connection = null;
    public DataSource EJB_Datasource = null;
    public DataSourceVO theDataSourceVO;
    private Long DataSourceUID;
    private DataSourceRootDAOImpl dataSourceRootDAO = null;

    /**
     *  @roseuid 3C0D3CF60375
     */
    public DataSourceEJB()
    {
    }

    /**
     *  @roseuid 3C18024A0167
     *  @param dataSourceVO
     */
    public DataSourceVO setDataSourceVO(DataSourceVO dataSourceVO) throws RemoteException
    {
        this.theDataSourceVO = dataSourceVO;
        return theDataSourceVO;
    }

    /**
     *  @roseuid 3C18024A0135
     *  @return getDataSourceVO
     */
    public DataSourceVO getDataSourceVO()
    {
        return theDataSourceVO;
    }

    /**
     *  @roseuid 3C17FD8A00FA
     *  @param entitycontext
     *  @throws EJBException
     *  @throws RemoteException
     */
    public void setEntityContext(EntityContext entitycontext) throws EJBException, RemoteException
    {
        EJB_Context = entitycontext;
    }

    /**
     *  @roseuid 3C0D3CF7002D
     */
    public void ejbActivate()
    {
    }

    /**
     *  @roseuid 3C180110020F
     *  @return DataSourcePK
     */
    public Long ejbCreate(DataSourceVO dataSourceVO) throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException
    {
        Long dataSourceUID = null;
        logger.debug("PlaceEJB create is called");
        this.theDataSourceVO = dataSourceVO;
        try
        {
            if(dataSourceRootDAO == null)
            {
                dataSourceRootDAO = (DataSourceRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_ROOT_DAO_CLASS);
            }
            dataSourceUID = new Long(dataSourceRootDAO.create(this.theDataSourceVO));
        }
        catch(NEDSSSystemException ndsex)
        {
            //EJB_Context.setRollbackOnly();
            logger.fatal(ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        return dataSourceUID;
    }

    /**
     *  @roseuid 3C1801100179
     *  @param primaryKey
     *  @return DataSource
     */
    public Long ejbFindByPrimaryKey(Long pk) throws RemoteException, FinderException, EJBException, NEDSSSystemException
    {
        logger.debug("DataSource EjbFindByPrimaryKey is called and pk = " + pk);
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                if(dataSourceRootDAO == null)
                {
                    dataSourceRootDAO = (DataSourceRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_ROOT_DAO_CLASS);
                }
                findPK = dataSourceRootDAO.findByPrimaryKey(pk.longValue());
                //see notes in clearcase by JLD
                //this.theDataSourceVO = (DataSourceVO)dataSourceRootDAO.loadObject(pk.longValue());
                //theDataSourceVO.getTheDataSourceDT().setDataSourceUid(pk);
            }
        }
        catch(NEDSSSystemException napex)
        {
            logger.fatal("Exception in find by primary key"+napex.getMessage(), napex);
            throw new NEDSSSystemException(napex.getMessage(), napex);
        }
        catch(EJBException ejbex)
        {
            logger.fatal("Exception in find by primary key" + ejbex.getMessage(), ejbex);
            throw new NEDSSSystemException(ejbex.getMessage(), ejbex);
        }
        return findPK;
    }

    /**
     *  @roseuid 3C0D3CF7004C
     *  Loads the dataSourceVO Object from the underlying database for a given UID and sets
     *  theDataSourceVO to this new object.
     */
    public void ejbLoad()
    {
        logger.debug("ejbLoad is being called");
        try
        {
            if(dataSourceRootDAO == null)
            {
                dataSourceRootDAO = (DataSourceRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_ROOT_DAO_CLASS);
            }
            this.theDataSourceVO = (DataSourceVO)dataSourceRootDAO.loadObject(((Long)EJB_Context.getPrimaryKey()).longValue());
        }
        catch(NEDSSSystemException nApp)
        {
            //EJB_Context.setRollbackOnly();
        	logger.fatal(nApp.getMessage(), nApp);
        	throw new NEDSSSystemException(nApp.getMessage(), nApp);
        }
    }

    /**
     *  @roseuid 3C0D3CF70037
     */
    public void ejbPassivate()
    {
    }

    /**
     *  @roseuid 3C180110022E
     */
    public void ejbPostCreate(DataSourceVO dataSourceVO) throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException
    {
    }

    /**
     *  @roseuid 3C0D3CF7006A
     */
    public void ejbRemove() throws RemoteException
    {
        try
        {
            if(dataSourceRootDAO != null)
            {
                dataSourceRootDAO = (DataSourceRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_ROOT_DAO_CLASS);
            }
            dataSourceRootDAO.remove(((Long)EJB_Context.getPrimaryKey()).longValue());
        }

        catch(NEDSSSystemException nApp)
        {
            //EJB_Context.setRollbackOnly();
        	logger.fatal(nApp.getMessage(), nApp);
        	throw new EJBException(nApp.getMessage(), nApp);
        }
    }

    /**
     *  @roseuid 3C0D3CF70060
     */
    public void ejbStore()
    {
        logger.debug("DataSourceEJB Store is called");
        try
        {
            if(dataSourceRootDAO == null)
            {
                dataSourceRootDAO = (DataSourceRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_ROOT_DAO_CLASS);
            }
            //this.theReportVO =  (ReportVreportRootDAO.loadObject(((Long)EJB_Context.getPrimaryKey()).longValue());
            this.theDataSourceVO = (DataSourceVO)dataSourceRootDAO.loadObject(((Long)EJB_Context.getPrimaryKey()).longValue());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException occured: " + ndapex.getMessage(), ndapex);
        	throw new NEDSSSystemException(ndapex.getMessage(), ndapex);
        }
        catch(Exception e)
        {
            logger.fatal("DataSourceEJB.ejbStore: " + e.getMessage(), e);
            throw new NEDSSSystemException(e.getMessage(), e);
        }
    }

    /**
     *  @roseuid 3C0D3CF70092
     */
    public void unsetEntityContext()
    {
        EJB_Context = null;
    }

}
