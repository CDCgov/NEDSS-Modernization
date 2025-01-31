package gov.cdc.nedss.report.ejb.reportejb.bean;

import gov.cdc.nedss.report.ejb.reportejb.dao.*;
import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.report.vo.ReportVO;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.sql.DataSource;

public class ReportEJB implements EntityBean
{

    static final LogUtils logger = new LogUtils((ReportEJB.class).getName());
    public EntityContext entitycontext;
    public ReportVO theReportVO;
    private Long ReportUID;
    private ReportRootDAOImpl reportRootDAO = null;

    /**
    * @roseuid 3C17FD7803BB
    */
    public ReportEJB()
    {
    }

    /**
    * @param entitycontext
    * @throws EJBException
    * @throws RemoteException
    * @roseuid 3C17FD790005
    */
    public void setEntityContext(EntityContext entitycontext) throws EJBException, RemoteException
    {
        this.entitycontext = entitycontext;
    }

    /**
    * @param reportVO
    * @roseuid 3C1800CB015C
    */
    public void setReportVO(ReportVO reportVO) throws RemoteException
    {
        this.theReportVO = reportVO;
    }

    /**
    * @return ReportVO
    * @roseuid 3C1800CB012A
    */
    public ReportVO getReportVO() throws RemoteException
    {
        return theReportVO;
    }

    /**
    * @roseuid 3C0D3B650224
    */
    public void AVRReportEJB()
    {
    }

    /**
    * @roseuid 3C0D3B6502EC
    */
    public void ejbActivate()
    {
    }

    /**
    * @return ReportPK
    * @roseuid 3C1800CB009E
    */
    public Long ejbCreate(ReportVO reportVO) throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException
    {
        Long reportUID = null;
        logger.debug("ReportEJB create is called");
        //this.theReportVO = reportVO;
        try
        {
            if(reportRootDAO == null)
            {
                reportRootDAO = (ReportRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_ROOT_DAO_CLASS);
            }
            this.theReportVO = (ReportVO)reportRootDAO.createReport(reportVO);
            reportUID = theReportVO.getTheReportDT().getReportUid();
        }
        catch(NEDSSSystemException ndsex)
        {
            entitycontext.setRollbackOnly();
            logger.fatal("NEDSSSystemException occured:" + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        catch(Exception e)
        {
            logger.debug("ReportEJB.ejbCreate: " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new javax.ejb.EJBException("ReportEJB.ejbCreate: " + e.toString(),e);
        }
        logger.debug("ReportEJB.ejbCreate reportUID " + reportUID);
        return reportUID;
    }

    /**
    * @param primaryKey
    * @return Report
    * @roseuid 3C1800CA03E6
    */
    public Long ejbFindByPrimaryKey(Long primaryKey) throws RemoteException, FinderException, EJBException, NEDSSSystemException
    {
        logger.debug("ReportEJB ejbFindByPrimaryKey is called - pk = " + primaryKey);
        Long findPK = null;
        try
        {
            if(primaryKey != null)
            {
                logger.debug("not null - pk = " + primaryKey);
                if(reportRootDAO == null)
                {
                    reportRootDAO = (ReportRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_ROOT_DAO_CLASS);
                }
                findPK = reportRootDAO.findByPrimaryKey(primaryKey.longValue());
                //see notes in clear case by JLD
                //this.theReportVO = (ReportVO)reportRootDAO.loadObject(primaryKey.longValue());
            }
        }
        catch(NEDSSSystemException napex)
        {
            logger.fatal("NEDSSSystemException occured: " + napex.getMessage(), napex);
            throw new EJBException(napex.getMessage(), napex);
        }
        catch(EJBException ejbex)
        {
            logger.fatal("EJBException occured: " + ejbex.getMessage(), ejbex);
            throw new EJBException(ejbex.getMessage(), ejbex);
        }
        catch(Exception e)
        {
            logger.fatal("ReportEJB.findByPrimaryKey: " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new javax.ejb.EJBException("ReportEJB.findByPrimaryKey: " + e.toString(),e);
        }
        return findPK;
    }

    /**
    * @roseuid 3C0D3B65030A
    */
    public void ejbLoad() throws EJBException
    {
        logger.debug("ReportEJB load is called");
        try
        {
            if(reportRootDAO == null)
            {
                reportRootDAO = (ReportRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_ROOT_DAO_CLASS);
            }
            //      logger.debug("Got the reportVO" +EJB_Context.getPrimaryKey() );
            this.theReportVO = (ReportVO)reportRootDAO.loadObject(((Long)entitycontext.getPrimaryKey()).longValue());
            logger.debug("Got the reportVO " + entitycontext.getPrimaryKey());
        }
        catch(NEDSSSystemException ndsex)
        {
            entitycontext.setRollbackOnly();
            logger.fatal("NEDSSSystemException occured: " + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        catch(Exception e)
        {
            logger.fatal(e.getMessage(), e);
            throw new javax.ejb.EJBException("ReportEJB.ejbLoad: " + e.toString(),e);
        }
    }

    /**
    * @roseuid 3C0D3B650300
    */
    public void ejbPassivate()
    {
    }

    /**
    * @roseuid 3C1800CB00BC
    */
    public void ejbPostCreate(ReportVO reportVO) throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException
    {
    }

    /**
    * @roseuid 3C0D3B650333
    */
    public void ejbRemove() throws javax.ejb.RemoveException, EJBException
    {
        logger.debug("ReportEJB remove is called");
        try
        {
            if(reportRootDAO == null)
            {
                reportRootDAO = (ReportRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_ROOT_DAO_CLASS);
            }
            reportRootDAO.remove(((Long)entitycontext.getPrimaryKey()).longValue());
        }
        catch(NEDSSSystemException ndsex)
        {
            entitycontext.setRollbackOnly();
            logger.fatal("NEDSSSystemException occued: " + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        catch(Exception e)
        {
            logger.debug("ReportEJB.ejbRemove: " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new javax.ejb.EJBException("ReportEJB.ejbRemove: " + e.toString(),e);
        }
    }

    /**
			//entitycontext.setRollbackOnly();
    * @roseuid 3C0D3B65031F
    */
    public void ejbStore() throws EJBException
    {
        logger.debug("ReportEJB Store is called");
        try
        {
            if(reportRootDAO == null)
            {
                reportRootDAO = (ReportRootDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_ROOT_DAO_CLASS);
            }
            //this.theReportVO =  (ReportVreportRootDAO.loadObject(((Long)EJB_Context.getPrimaryKey()).longValue());
            this.theReportVO = (ReportVO)reportRootDAO.storeReport(this.theReportVO);
        }
        catch(NEDSSSystemException ndsex)
        {
            entitycontext.setRollbackOnly();
            logger.fatal("NEDSSSystemException occured: " + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        catch(Exception e)
        {
            logger.fatal(e.getMessage(), e);
            throw new javax.ejb.EJBException("ReportEJB.ejbStore: " + e.toString(),e);
        }
    }

    /**
    * @roseuid 3C0D3B65035B
    */
    public void unsetEntityContext()
    {
        this.entitycontext = null;
    }

}
