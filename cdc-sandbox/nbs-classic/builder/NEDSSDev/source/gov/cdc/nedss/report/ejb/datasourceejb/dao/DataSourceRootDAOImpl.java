// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved

/**
* Name:		    DataSourceRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               DataSource value object in the DataSource entity bean.
*               This class encapsulates all the JDBC calls made by the DataSourceEJB
*               for a DataSource object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of DataSourceEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma & NEDSS Development Team
* @version	    1.0
*/

package gov.cdc.nedss.report.ejb.datasourceejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.dt.DataSourceDT;
import gov.cdc.nedss.report.util.ReportJndiNames;
import gov.cdc.nedss.report.vo.DataSourceVO;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;



public class DataSourceRootDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils((DataSourceRootDAOImpl.class).getName());
	private DataSourceVO dvo = null;
	private long dataSourceUID;
	private long dataSourceColumnUID;
	private static DataSourceDAOImpl dataSourceDAO = null;
	private static DataSourceColumnDAOImpl dataSourceColumnDAO = null;

	public DataSourceRootDAOImpl()
	{
	}

	/**
	 * public create method in the DataSourceRootDAOImpl. This in turn calls the other private
	 * methods for the create.
	 *
	 */
	public DataSourceVO createDataSource(Object obj) throws NEDSSSystemException
	{
		try{
			this.dvo = (DataSourceVO)obj;
			DataSourceDT dataSourceDT = null;
			ArrayList<Object> list = null;
	
			/**
			*  Inserts DataSourceDT object and returns a UID.
			*/
	
			if (this.dvo != null)
			{
				logger.debug("DataSourceRootDAOImpl inside the if clause");
				dataSourceDT = insertDataSource(this.dvo);
			}
			//logger.debug("DataSourceRootDAOImpl dataSourceUID = " + dataSourceDT.);
			dvo.setTheDataSourceDT(dataSourceDT);
	
	
			/**
			* Inserts DataSourceColumn collection
			*/
	
			if (this.dvo != null && this.dvo.getTheDataSourceColumnDTCollection() != null)
			{
				list = (ArrayList<Object> )insertDataSourceColumn(dataSourceUID, dvo.getTheDataSourceColumnDTCollection());
			}
			this.dvo.setTheDataSourceColumnDTCollection(list);
			this.dvo.setItNew(false);
			this.dvo.setItDirty(false);
			return this.dvo;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Updates the DataSourceVO Object in the database.
	 */

	public DataSourceVO storeDataSourceVO(Object obj) throws NEDSSSystemException
	{
		this.dvo = (DataSourceVO)obj;
		DataSourceDT dataSourceDT = null;
		ArrayList<Object> dataSourceColumnList = null;

		try{
			/**
			*  Updates DataSourceDT object
			*/
			if(this.dvo.getTheDataSourceDT() != null && this.dvo.getTheDataSourceDT().isItNew())
			{
				dataSourceDT = insertDataSource(this.dvo);
				this.dvo.getTheDataSourceDT().setItNew(false);
				this.dvo.getTheDataSourceDT().setItDirty(false);
				dvo.setTheDataSourceDT(dataSourceDT);
			}
			if(this.dvo.getTheDataSourceDT() != null && this.dvo.getTheDataSourceDT().isItDirty())
			{
				dataSourceDT = updateDataSource(this.dvo);
				this.dvo.getTheDataSourceDT().setItDirty(false);
				this.dvo.getTheDataSourceDT().setItNew(false);
				dvo.setTheDataSourceDT(dataSourceDT);
			}
	
			/**
			* Updates DataSourceColumn collection
			*/
	
			if (this.dvo.getTheDataSourceColumnDTCollection() != null)
			{
				dataSourceColumnList =	(ArrayList<Object> )updateDataSourceColumn(this.dvo);
				dvo.setTheDataSourceColumnDTCollection(dataSourceColumnList);
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return dvo;
	}

	/**
	 * for a given UID, it removes the DataSourceVO object from the Database.
	 */
	public void remove(long dataSourceUID) throws NEDSSSystemException
	{
		try{
			/**
			* Removes DataSourceColumn Collection
			*/
			removeDataSourceColumn(dataSourceUID);
			/**
			*  Removes DataSource
			*/
			removeDataSource(dataSourceUID);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * loadObject gets the DataSourceVO object from the database(based on given dataSourceUID).
	 */
	public Object loadObject(long dataSourceUID) throws NEDSSSystemException
	{
		this.dvo = new DataSourceVO();
		logger.debug("DataSourceRootDAOImpl.loadObject dataSourceUID = " +   dataSourceUID);

		try{
			/**
			*  Selects DataSourceDT object
			*/
			DataSourceDT dDT = selectDataSource(dataSourceUID);
			this.dvo.setTheDataSourceDT(dDT);
	
			/**
			* Selects DataSourceColumnDT Collection
			*/
	
			Collection<Object>  dsColl = selectDataSourceColumnCollection(dataSourceUID);
			this.dvo.setTheDataSourceColumnDTCollection(dsColl);
	
			this.dvo.setItNew(false);
			this.dvo.setItDirty(false);
			return this.dvo;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Finds if the given dataSourceUID value exists in the database.
	 */

	public Long findByPrimaryKey(long dataSourceUID) throws NEDSSSystemException
	{
		try{
			/**
			 * Finds intervention object
			 */
			Long interventionPK = findDataSource(dataSourceUID);
			return interventionPK;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Inserts DataSourceVO Object into the database.
	 */

	private DataSourceDT insertDataSource(DataSourceVO dvo) throws EJBException, NEDSSSystemException
	{
		DataSourceDT dataSourceDT = null;
		logger.debug("inside the insert dataSource");
		try
		{
			if(dataSourceDAO == null)
			{
				dataSourceDAO = (DataSourceDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_DAO_CLASS);
				logger.debug("inside the insert dataSource 2");
			}
		    dataSourceDT = dataSourceDAO.createDataSource(dvo.getTheDataSourceDT());
		    dvo.setTheDataSourceDT(dataSourceDT);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		return dataSourceDT;
	}

	/**
	 * Insets dataSourceColumn object into the database from a given dataSourceVO.
	 */
	private Collection<Object>  insertDataSourceColumn(long dataSourceUID, Collection<Object>  dataSourceColumns) throws EJBException, NEDSSSystemException
	{
		logger.debug("inside insert for dataSourceColumn");
		ArrayList<Object> dataSourceColumnsResult = null;
		try
		{
			if(dataSourceColumnDAO == null)
			{
				dataSourceColumnDAO = (DataSourceColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_COLUMN_DAO_CLASS);
		    }
				dataSourceColumnsResult = (ArrayList<Object> )dataSourceColumnDAO.createDataSourceColumns(dataSourceUID, dataSourceColumns);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.getMessage());
		}
		return dataSourceColumnsResult;

	}

	/**
	 * loads the DataSourceDT object from the dastabase for a given dataSourceUID.
	 */
	private  DataSourceDT selectDataSource(long dataSourceUID) throws NEDSSSystemException
	{
		try
		{
			if(dataSourceDAO == null)
			{
				dataSourceDAO = (DataSourceDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_DAO_CLASS);
			}
			logger.debug("DataSourceRootDAOImpl.selectDataSource dataSourceUID = " +   dataSourceUID);
			return ((DataSourceDT)dataSourceDAO.loadObject(dataSourceUID));
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
	}

	/**
	 * This method gets a collection of dataSourceColumns for a given dataSourceUID.
	 */
	private Collection<Object>  selectDataSourceColumnCollection(long aUID) throws NEDSSSystemException
	{
		try
		{
			if(dataSourceColumnDAO == null)
			{
				dataSourceColumnDAO = (DataSourceColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_COLUMN_DAO_CLASS);
			}
			logger.debug("DataSourceRootDAOImpl.selectDataSourceColumCollection  aUID = " +   aUID);
			return (dataSourceColumnDAO.load(aUID));
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		}


	/**
	 * Removes the DataSourceVO for a given dataSourceUID.
	 */
	private void removeDataSource(long aUID) throws NEDSSSystemException
	{
		try
		{
			if(dataSourceDAO == null)
			{
				dataSourceDAO = (DataSourceDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_DAO_CLASS);
			}
		dataSourceDAO.remove(aUID);
        }
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
	}

	/**
	 * Removes the dataSourceColumns for a given dataSourceUID
	 */

	private void removeDataSourceColumn(long aUID) throws NEDSSSystemException
	{
		try
		{
			if(dataSourceColumnDAO == null)
			{
				dataSourceColumnDAO = (DataSourceColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_COLUMN_DAO_CLASS);
			}
			dataSourceColumnDAO.remove(aUID);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
	}

	private DataSourceDT updateDataSource(DataSourceVO dvo) throws NEDSSSystemException
	{
		DataSourceDT dataSourceDT = null;
		try
		{
			if(dataSourceDAO == null)
			{
				dataSourceDAO = (DataSourceDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_DAO_CLASS);
			}
		dataSourceDT = dataSourceDAO.storeDataSource(dvo.getTheDataSourceDT());
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		return dataSourceDT;
	}

	private Collection<Object>  updateDataSourceColumn(DataSourceVO dvo) throws NEDSSSystemException
	{
		ArrayList<Object> dataSourceColumns  = null;
		try
		{
			if(dataSourceColumnDAO == null)
			{
				dataSourceColumnDAO = (DataSourceColumnDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_COLUMN_DAO_CLASS);
			}
			dataSourceColumns = (ArrayList<Object> )dataSourceColumnDAO.storeDataSourceColumns(dvo.getTheDataSourceColumnDTCollection());
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndsApp)
		{
			logger.fatal("NEDSSSystemException = "+ndsApp.getMessage(), ndsApp);
			throw new NEDSSSystemException(ndsApp.getMessage());
		}
		return dataSourceColumns;
	}

	private Long findDataSource(long dataSourceUID) throws NEDSSSystemException
	{
		Long findPK = null;
		try
		{
			if(dataSourceDAO == null)
			{
				dataSourceDAO = (DataSourceDAOImpl)NEDSSDAOFactory.getDAO(ReportJndiNames.DATA_SOURCE_DAO_CLASS);
			}
			findPK = dataSourceDAO.findByPrimaryKey(dataSourceUID);
		}
		catch(NEDSSDAOSysException ndsex)
		{
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		}
		catch(NEDSSSystemException ndapex)
		{
			logger.fatal("NEDSSSystemException = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
		return findPK;
	}

/**
    public static void main(String args[]){
      logger.debug("DataSourceRootDAOImpl - Doing the main thing");
      try{
		logger.debug("DataSourceRootDAOImpl - Doing the same thing");
      	DataSourceDT dataSourceDT = new DataSourceDT();
		DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
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
