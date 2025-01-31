// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved

package gov.cdc.nedss.report.ejb.reportejb.dao;

/**
* Name:		    ReportRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               DataSource value object in the Report entity bean.
*               This class encapsulates all the JDBC calls made by the ReportEJB
*               for a Report object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of ReportEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma & NEDSS Development Team
* @version	    1.0
*/

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.dt.FilterValueDT;
import gov.cdc.nedss.report.dt.ReportDT;
import gov.cdc.nedss.report.dt.ReportFilterDT;
import gov.cdc.nedss.report.util.ReportJndiNames;
import gov.cdc.nedss.report.vo.ReportVO;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;

public class ReportRootDAOImpl extends BMPBase
{
		static final LogUtils logger = new LogUtils((ReportRootDAOImpl.class).getName());
		private ReportVO rvo = null;
		private long reportUID;
		private long reportFilterUID;
		private long displayColumnUID;
		private ReportDT reportDT = null;
		private FilterValueDT filterValueDT =null;



		private static ReportFilterDAOImpl reportFilterDAO = null;
		private static ReportDAOImpl reportDAO = null;
		private static DisplayColumnDAOImpl displayColumnDAO = null;

		public ReportRootDAOImpl()
		{
		}

		public Object createReport(Object obj) throws NEDSSSystemException
		{
			ReportVO reportObject = new ReportVO();
			this.rvo = (ReportVO)obj;

		/**
		 * To check if the ReportVO object contains at least one ReportFilterDT object and
		 * also contains ReportDT object
		 */
		 try{

				if(this.rvo.getTheReportDT() != null && this.rvo.getTheReportFilterDTCollection() != null)
				{
				/**
				*  Inserts ReportDT and ReportFilterDT object
				*  As the ReportVO object contains at least one ReportDT and at least one of
				*  ReportFilterDTCollection
				*/

				if (this.rvo != null  && this.rvo.getTheReportFilterDTCollection()!= null)
				{
					   logger.debug("inside the if clause");
					   reportDT = (ReportDT)insertReport(this.rvo);
					   reportObject.setTheReportDT(reportDT);
					   reportUID = reportDT.getReportUid().longValue();
					   logger.debug("inside the create for ReportFilterCollection");
					   ArrayList<Object> reportFilters = (ArrayList<Object> )insertReportFilterCollection(this.rvo, reportUID);
					   reportObject.setTheReportFilterDTCollection(reportFilters);
				}
				logger.debug("Report UID = " + reportUID);
				//this.rvo.getTheReportDT().setReportUid(new Long(reportUID));
			}

	      }
	      catch(NEDSSDAOSysException ndsex)
	      {
	    	  logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
	          throw new EJBException(ndsex.getMessage());
	      }catch (Exception e)
	      {
	    	  logger.fatal("Exception  = "+e.getMessage(), e);
	    	  throw new javax.ejb.EJBException("ReportRootDAOImpl.create " + e.toString());
	      }

	    /**
        * Inserts DisplayColumnDT collection.
		    * The ReportVO may contain zero or more of this list.
        */
		 try{
	        if (this.rvo != null && this.rvo.getTheDisplayColumnDTList() != null)
			    {
				      logger.debug("inside the create for ReportRootDAOImpl:ReportFilterCollection   and reportUID is:"+ reportUID);
	        	  List<Object> displyColumnList = insertDisplayColumnDTList(reportUID, this.rvo);
				      reportObject.setTheDisplayColumnDTList(displyColumnList);
			    }
	        this.rvo.setItNew(false);
	        this.rvo.setItDirty(false);
	        return reportObject;
		 }catch (Exception e){
	    	  logger.fatal("Exception  = "+e.getMessage(), e);
	    	  throw new NEDSSSystemException("ReportRootDAOImpl.create " + e.toString());
	     }
    }

    public Object storeReport(Object obj) throws NEDSSSystemException
    {
    	try{
		    ReportVO reportObject = new ReportVO();
	        logger.debug("ReportRootDAOImpl:store ");
	        this.rvo = (ReportVO)obj;
	
	        /**
	        *  Updates ReportDT object
	        */
	        if(this.rvo.getTheReportDT() != null && this.rvo.getTheReportFilterDTCollection() != null
				         && this.rvo.getTheReportDT().isItNew())
	        {
	            logger.debug("ReportRootDAOImpl:store the collection and reportDT are not null but new");
	            reportDT =  (ReportDT)insertReport(this.rvo);
	            reportObject.setTheReportDT(reportDT);
	            reportUID = reportDT.getReportUid().longValue();
	            ArrayList<Object> reportFilters = (ArrayList<Object> )insertReportFilterCollection(this.rvo, reportUID);
	            reportObject.setTheReportFilterDTCollection(reportFilters);
	            this.rvo.getTheReportDT().setItNew(false);
	            this.rvo.getTheReportDT().setItDirty(false);
	        }
	        if(this.rvo.getTheReportDT() != null && this.rvo.getTheReportFilterDTCollection() != null ) // && this.rvo.getTheReportDT().isItDirty())
	        {
				logger.info("Got into the update of ReportRootDAOImpl");
	            reportDT = updateReport(this.rvo);
	            reportObject.setTheReportDT(reportDT);
	            ArrayList<Object> reportFilters = (ArrayList<Object> )updateReportFilterCollection(this.rvo);
	            reportObject.setTheReportFilterDTCollection(reportFilters);
	            this.rvo.getTheReportDT().setItDirty(false);
	            this.rvo.getTheReportDT().setItNew(false);
	        }
	
	        /**
	        * Updates DisplayColumnDTList collection
	        */
	
	        if (this.rvo.getTheDisplayColumnDTList() != null)
	        {
	            logger.debug("ReportRootDAOImpl:Store of ReportRootDAO");
	            List<Object> displayColumnList = updateDisplayColumnDTList(this.rvo);
	            reportObject.setTheDisplayColumnDTList(displayColumnList);
	        }
			return reportObject;
    	}catch (Exception e){
	    	  logger.fatal("Exception  = "+e.getMessage(), e);
	    	  throw new NEDSSSystemException("ReportRootDAOImpl.create " + e.toString());
	    }
    }

    public void remove(long reportUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        *  Removes ReportFilter
	        */
			removeReportFilterCollection(reportUID);
	
	        /**
	        *  Removes DisplayColumn
	        */
	        removeDisplayColumn(reportUID);
	
				  /**
	        * Removes ReportDT
	        */
	        removeReport(reportUID);
    	}catch (Exception e){
	    	  logger.fatal("reportUID: "+reportUID+" Exception  = "+e.getMessage(), e);
	    	  throw new NEDSSSystemException("ReportRootDAOImpl.create " + e.toString());
	    }
	}

    public Object loadObject(long reportUID) throws NEDSSSystemException
    {
    	try{
	        logger.debug("ReportRootDAOImpl:loadReportRootDAOImpl");
	        this.rvo = new ReportVO();
	
	        /**
	        *  Selects ReportDT object
	        */
	        logger.debug("ReportRootDAOImpl:loadReportRootDAOImpl" + 1);
	        ReportDT rDT = selectReport(reportUID);
	        this.rvo.setTheReportDT(rDT);
	
	        /**
	        * Selects ReportFilterDT Collection
	        */
	        Collection<Object>  rfColl = selectReportFilterCollection(reportUID);
	        this.rvo.setTheReportFilterDTCollection(rfColl);
	        logger.debug("ReportRootDAOImpl:loadReportRootDAOImpl and rfColl" + rfColl);
	
	
	        /**
	        * Selects DisplayColumnDT List
	        */
	        List<Object> dcList = selectDisplayColumnList(reportUID);
	        this.rvo.setTheDisplayColumnDTList(dcList);
	        logger.debug("ReportRootDAO:loadObject after selectDisplayColumnList");
	
	        this.rvo.setItNew(false);
	        this.rvo.setItDirty(false);
	        logger.debug("ReportRootDAO:loadObject this.rvo " + this.rvo);
	        return this.rvo;
    	}catch (Exception e){
	    	  logger.fatal("reportUID: "+reportUID+" Exception  = "+e.getMessage(), e);
	    	  throw new NEDSSSystemException("ReportRootDAOImpl.create " + e.toString());
	    }
    }

     public Long findByPrimaryKey(long reportUID) throws NEDSSSystemException
     {
    	 try{
	        /**
	         * Finds report object
	         */
	         Long reportPK = findReport(reportUID);
	         logger.debug("ReportRootDAO:findByPrimaryKey Done find by primarykey!");
	         return reportPK;
    	 }catch (Exception e){
	    	  logger.fatal("reportUID: "+reportUID+" Exception  = "+e.getMessage(), e);
	    	  throw new NEDSSSystemException("ReportRootDAOImpl.create " + e.toString());
	    }
     }

	   private List<Object> insertDisplayColumnDTList(long reportUID, ReportVO rvo) throws EJBException, NEDSSSystemException
	   {
		     try
		     {
				if(displayColumnDAO == null)
			    {
				    displayColumnDAO = (DisplayColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DISPLAY_COLUMN_DAO_CLASS);
				     logger.debug("ReportRootDAOImpl:insertDisplayColumnDTList  inside the insert report 2");
			    }
			    List<Object> displayColumnList = displayColumnDAO.create(reportUID, rvo.getTheDisplayColumnDTList());
		     }
		     catch(NEDSSDAOSysException ndsex)
	         {
		    	 logger.fatal("Exception  = "+ndsex.getMessage(), ndsex);
	             throw new EJBException(ndsex.getMessage());
	         }
         catch (Exception e)
         {
             logger.fatal("reportUID: "+reportUID+" ReportRootDAOImpl:insertDisplayColumnList: " + e.getMessage() + "\n",e);
	   	     throw new javax.ejb.EJBException("ReportRootDAOImpl:insertDisplayColumnList " + e.toString());
         }
		 return rvo.getTheDisplayColumnDTList();
	   }

     private Object insertReport(ReportVO rvo) throws EJBException, NEDSSSystemException
     {
		     logger.debug("ReportRootDAOImpl:insertReport inside the insert report");
		     ReportDT reportDT = null;
         try
         {
             if(reportDAO == null)
             {
				         reportDAO = (ReportDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_DAO_CLASS);
             }
		         reportDT = (ReportDT)reportDAO.createReport(rvo.getTheReportDT());
		         rvo.setTheReportDT(reportDT); //.setReportUid(new Long(reportUID));
			       logger.debug("ReportRootDAOImpl:insertReport  Report UID = " + reportDT.getReportUid());
        }
        catch(NEDSSDAOSysException ndsex)
        {
        	logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
        catch (Exception e)
	      {
            logger.fatal("ReportRootDAOImpl.insertReport: " + e.getMessage() + "\n",e);
	    	    throw new javax.ejb.EJBException("ReportRootDAOImpl.insertReport " + e.toString());
	      }
        return reportDT;
    }

	private Collection<Object>  insertReportFilterCollection(ReportVO rvo, long reportUID) throws EJBException, NEDSSSystemException
	{
		logger.debug("ReportRootDAOImpl:insertReportFilterCollection   inside insert for insertReportFilterCollection");
		Iterator<Object> anIterator = null;
		ReportFilterDT reportFilterDT = null;
		ArrayList<Object> reportFilters = null;
		try
		{
			if(reportFilterDAO == null)
			{
				logger.debug("ReportRootDAOImpl:insertReportFilterCollection   got the inside if for the insertReportFilterCollection");
				reportFilterDAO = (ReportFilterDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_FILTER_DAO_CLASS);
				logger.debug("ReportRootDAOImpl:insertReportFilterCollection  got the reportFilertDAO");
			}
			reportFilters = (ArrayList<Object> )reportFilterDAO.createReportFilter(reportUID, rvo.getTheReportFilterDTCollection());
			logger.debug("ReportRootDAOImpl:insertReportFilterCollection   got the reportFilertDAO and reportUID is : " + reportUID);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ndsex.getMessage(), ndsex);
			throw new EJBException("ReportRootDAOImpl:insertReportFilterCollection  Exception throws in the insertReportFilerCollection  :" +ndsex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("reportUID: "+reportUID+" ReportRootDAOImpl.insertReportFilterCollection: " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.insertReportFilterCollection  " + e.toString());
		}
		return reportFilters;
	}


	private  ReportDT selectReport(long reportUID) throws NEDSSSystemException
	{
		try
		{
			if(reportDAO == null)
			{
				reportDAO = (ReportDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_DAO_CLASS);
			}
			return ((ReportDT)reportDAO.loadObject(reportUID));
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("ReportRootDAOImpl:selectReport: " + e.getMessage() + "\n",e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl:selectReport " + e.toString());
		}
  }

	private List<Object> selectDisplayColumnList(long reportUID) throws NEDSSSystemException
	{
		try
		{
			if(displayColumnDAO == null)
			{
				displayColumnDAO = (DisplayColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DISPLAY_COLUMN_DAO_CLASS);
			}
			return ((List<Object>)displayColumnDAO.loadDisplayColumns(reportUID));
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("ReportRootDAOImpl.selectDisplayColumnList: " + e.getMessage()+ "\n",e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.selectDisplayColumnList " + e.toString());
		}
	}

	private Collection<Object>  selectReportFilterCollection(long reportUID) throws NEDSSSystemException
	{
		try
		{
			if(reportFilterDAO == null)
			{
				reportFilterDAO = (ReportFilterDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_FILTER_DAO_CLASS);
			}
			logger.debug("ReportRootDAOImpl:selectReportFilterCollection  and reportUID: " + reportUID);
			return (reportFilterDAO.load(reportUID));
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("reportUID: "+reportUID+" ReportRootDAOImpl:selectReportFilterCollection: " + e.getMessage() + "\n", e);
			e.printStackTrace();
			throw new javax.ejb.EJBException("ReportRootDAOImpl:selectReportFilterCollection  " + e.toString());
		}
	}

	private void removeReport(long aUID) throws NEDSSSystemException
	{
		try
		{
			if(reportDAO == null)
			{
				reportDAO = (ReportDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_DAO_CLASS);
			}
			reportDAO.remove(aUID);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("aUID: "+aUID+ "NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("aUID: "+aUID+ "NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("aUID: "+aUID+ "ReportRootDAOImpl.removeReport : " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.removeReport " + e.toString());
		}
	}

	private void removeReportFilterCollection(long reportUID) throws NEDSSSystemException
	{
		try
		{
			if(reportFilterDAO == null)
			{
				reportFilterDAO = (ReportFilterDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_FILTER_DAO_CLASS);
			}
			reportFilterDAO.remove(reportUID);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("reportUID: "+reportUID+" NEDSSSystemException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("reportUID: "+reportUID+" NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("reportUID: "+reportUID+" ReportRootDAOImpl.removeReportFilterCollection: " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.removeReportFilterCollection  " + e.toString());
		}
	}


	private void removeDisplayColumn(long reportUID) throws NEDSSSystemException
	{
		try
		{
			if(displayColumnDAO == null)
			{
				logger.debug("ReportRootDAOImpl:removeDisplayColumn ");
				displayColumnDAO = (DisplayColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DISPLAY_COLUMN_DAO_CLASS);
			}
			logger.debug("ReportRootDAOImpl:removeDisplayColumn outside if");
			displayColumnDAO.remove(reportUID);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("reportUID: "+reportUID+" NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("reportUID: "+reportUID+" ReportRootDAOImpl.removeDisplayColumn: " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.removeDisplayColumn " + e.toString());
		}
	}
	private ReportDT updateReport(ReportVO rvo) throws NEDSSSystemException
	{
		ArrayList<Object>  reportVO = null;
		ReportDT reportDT = null;
		try
		{
			if(reportDAO == null)
			{
				reportDAO = (ReportDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_DAO_CLASS);
			}
			reportDT = (ReportDT)reportDAO.storeReport(rvo.getTheReportDT());
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("ReportRootDAOImpl.updateReport: " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.updateReport " + e.toString());
		}
		return reportDT;
	}

	private List<Object> updateDisplayColumnDTList(ReportVO rvo) throws NEDSSSystemException
	{
		List<Object> displayColumnList = null;
		try
		{
			if(displayColumnDAO == null)
			{
				displayColumnDAO = (DisplayColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DISPLAY_COLUMN_DAO_CLASS);
		}
		displayColumnList = displayColumnDAO.store(rvo.getTheDisplayColumnDTList());
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("ReportRootDAOImpl.updateDisplayColumnList : " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.updateDisplayColumnList " + e.toString());
		}
		return displayColumnList;
	}

	private Collection<Object> updateReportFilterCollection(ReportVO rvo) throws NEDSSSystemException
	{
		ArrayList<Object>  reportFilters = null;
		try
		{
			if(reportFilterDAO == null)
			{
				reportFilterDAO = (ReportFilterDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_FILTER_DAO_CLASS);
			}
			reportFilters = (ArrayList<Object> )reportFilterDAO.storeReportFilters(rvo.getTheReportFilterDTCollection());
		}
		catch(NEDSSDAOSysException ndsex)
		{
			logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("ReportRootDAOImpl.updateReportFilterCollection: " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.updateReportFilterCollection  " + e.toString());
		}
		return reportFilters;
	}

	private Long findReport(long reportUID) throws NEDSSSystemException
	{
		logger.debug("The ReportRootDAOImpl:findReport report UId is " + reportUID);
		Long findPK = null;
		try
		{
			if(reportDAO == null)
			{
				reportDAO = (ReportDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.REPORT_DAO_CLASS);
			}
			logger.debug("The ReportRootDAOImpl:findReport report UId is 1 " + reportUID);
			findPK = reportDAO.findByPrimaryKey(reportUID);
			logger.debug("The ReportRootDAOImpl:findReport findPK : " + findPK);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		catch (Exception e)
		{
			logger.fatal("ReportRootDAOImpl.findReport : " + e.getMessage() + "\n", e);
			throw new javax.ejb.EJBException("ReportRootDAOImpl.findReport " + e.toString());
		}
		return findPK;
	}

 /**   public static void main(String args[]){
      logger.debug("DataSourceRootDAOImpl - Doing the main thing");
      try{
		logger.debug("DataSourceRootDAOImpl - Doing the same thing");
      	DataSourceDT dataSourceDT = new DataSourceDT();
		logger.debug("1");
		DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
		logger.debug("2");
		//dataSourceDT.setConditionSecurity("t");
		logger.debug("3");
		dataSourceDT.setDescTxt("test");
		logger.debug("4");



		dataSourceColumnDT.setColumnName("test");
		logger.debug("5");
		dataSourceColumnDT.setDisplayable("test");
		logger.debug("6");
		Collection<Object>  dcoll = new ArrayList<Object> ();
		logger.debug("7");
		dcoll.add(dataSourceColumnDT);
		logger.debug("8");




        DataSourceVO dtVO = new DataSourceVO();
        dtVO.setTheDataSourceDT(dataSourceDT);
		dtVO.setTheDataSourceColumnDTCollection(dcoll);

        DataSourceRootDAOImpl dtRootDAO = new DataSourceRootDAOImpl();
		logger.debug("test");
		dtRootDAO.create(dtVO);

      }catch(Exception e){
        logger.debug("\nDataSourceRootDAOImpl ERROR : turkey no worky = \n" + e);
      }
    }
*/
}//end of DataSourceRootDAOImpl class
