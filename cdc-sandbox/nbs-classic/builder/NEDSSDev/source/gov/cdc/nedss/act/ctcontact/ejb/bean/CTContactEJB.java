/**
 * Title: CTContactEJB  Class
 * Description: Enterprise bean class for CTContactEJB
 * Copyright:    Copyright (c) 2009
 * Company: CSC
 * @author Pradeep Sharma
 * @version 3.1
 */

package gov.cdc.nedss.act.ctcontact.ejb.bean;
 
import java.sql.Timestamp;
import java.util.Date;

import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactRootDAO;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

public class CTContactEJB implements javax.ejb.EntityBean
{
	private static final long serialVersionUID = 1L;
	private CTContactVO contactVO;
	private CTContactVO oldContactVO;
	private long contactUID;
	private javax.ejb.EntityContext cntx;
	private CTContactRootDAO cTContactRootDAO = null;

	static final LogUtils logger = new LogUtils(CTContactEJB.class.getName());

	public CTContactEJB()
	{
	}

	public void ejbActivate() throws EJBException,java.rmi.RemoteException
	{

	}

	public Long ejbCreate(CTContactVO contactVO) throws CreateException, DuplicateKeyException, EJBException, NEDSSSystemException,java.rmi.RemoteException
	{
		this.contactVO = contactVO;
		try
		{
			if(cTContactRootDAO == null)
			{
				cTContactRootDAO = (CTContactRootDAO)NEDSSDAOFactory.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
			}
			this.contactVO.getcTContactDT().setVersionCtrlNbr(new Integer(1));
			contactUID = cTContactRootDAO.create(this.contactVO);
			this.contactVO.getcTContactDT().setCtContactUid(new Long(contactUID));
		}
		catch(NEDSSSystemException ndsex)
		{
			logger.fatal("CTContactEJB.ejbCreate: " + ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.getMessage(), ndsex);
		}
		return (new Long(contactUID));
	}

	public Long ejbFindByPrimaryKey(Long primaryKey) throws FinderException, EJBException, NEDSSSystemException,java.rmi.RemoteException
	{
		Long findPK = null;
		try
		{
			if(primaryKey != null)
			{
				if(cTContactRootDAO == null)
				{
					cTContactRootDAO = (CTContactRootDAO)NEDSSDAOFactory.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
				}
				findPK = cTContactRootDAO.findByPrimaryKey(primaryKey.longValue());

			}
		}

		catch (NEDSSDAOSysException nodsex)
		{
			logger.fatal("CTContactEJB.ejbFindByPrimaryKey: NEDSSDAOSysException: " + nodsex.getMessage(), nodsex);
			throw new EJBException(nodsex.getMessage(), nodsex);

		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("CTContactEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + nsex.getMessage(), nsex);
			throw new EJBException(nsex.getMessage(), nsex);

		}
		return findPK;
	}

	public void ejbLoad() throws EJBException,java.rmi.RemoteException
	{
		try
		{
			if(cTContactRootDAO == null)
			{
				cTContactRootDAO = (CTContactRootDAO)NEDSSDAOFactory.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
			}
			this.contactVO = (CTContactVO)cTContactRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
			this.contactVO.setItDirty(false);
			this.contactVO.setItNew(false);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("CTContactEJB.ejbLoad: " + nsex.getMessage(), nsex);
			throw new EJBException(nsex.getMessage(), nsex);

		}
	}

	public void ejbPassivate() throws EJBException,java.rmi.RemoteException
	{
		try {
			this.cTContactRootDAO = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CTContactEJB.ejbPassivate: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	public void ejbPostCreate(CTContactVO contactVO) throws javax.ejb.CreateException, DuplicateKeyException, EJBException,NEDSSSystemException,java.rmi.RemoteException
	{
	}

	public void ejbRemove() throws javax.ejb.RemoveException, EJBException,java.rmi.RemoteException {
		try {
			if(cTContactRootDAO == null) {
				cTContactRootDAO = (CTContactRootDAO)NEDSSDAOFactory.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
			}
			insertHistory("remove");

			this.contactVO.setItDelete(true);
			cTContactRootDAO.remove(this.contactVO);

		} catch(NEDSSSystemException ndsex) {
			logger.fatal("CTContactEJB.ejbRemove: " + ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.getMessage(), ndsex);
		}
	}

	public void ejbStore() throws EJBException,java.rmi.RemoteException
	{
		try
		{
			if(cTContactRootDAO == null)
			{
				cTContactRootDAO = (CTContactRootDAO)NEDSSDAOFactory.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
			}
			if(this.contactVO != null && this.contactVO.isItDirty()) {
				try {
					cTContactRootDAO.updateCTContact(this.contactVO);

					this.contactVO.setItDirty(false);
					this.contactVO.setItNew(false);
					insertHistory("update");
				} catch(NEDSSConcurrentDataException ndcex) {
					logger.debug("Got into concurrent exception CTContactEJB"+ ndcex);
					cntx.setRollbackOnly();
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException in ctcontactEJB");
				}
			}else if (this.contactVO != null && this.contactVO.isItDelete()){
				Timestamp currentDate = new Timestamp(new Date().getTime());
				contactVO.getcTContactDT().setLastChgTime(currentDate);
				contactVO.getcTContactDT().setRecordStatusTime(currentDate);
				contactVO.getcTContactDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
	
				cTContactRootDAO.updateCTContact(this.contactVO);

				this.contactVO.setItDirty(false);
				this.contactVO.setItNew(false);
				this.contactVO.setItDelete(true);
				insertHistory("delete");
			}

		}
		catch(NEDSSConcurrentDataException ndcex)
		{
			logger.fatal("CTContactEJB.ejbStore: NEDSSConcurrentDataException: " + ndcex.getMessage(), ndcex);
			throw new EJBException(ndcex.getMessage(), ndcex);
		}
		catch(NEDSSDAOSysException nodsysex)
		{
			logger.fatal("CTContactEJB.ejbStore: NEDSSDAOSysException: " + nodsysex.getMessage(), nodsysex);
			throw new EJBException(nodsysex.getMessage(), nodsysex);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("CTContactEJB.ejbStore: NEDSSSystemException: " + nsex.getMessage(), nsex);
			throw new EJBException(nsex.getMessage(), nsex);
		}
	}

	public CTContactVO getCTContactVO(){
		try {
			return contactVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CTContactEJB.getCTContactVO: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	private void insertHistory(String fromAction) throws NEDSSSystemException{
		try {
			if(oldContactVO != null) {
				logger.debug("CTContactEJB in ejbStore(), ctcontactUID in contactVO : " + contactVO.getcTContactDT().getCtContactUid().longValue());
				cTContactRootDAO = new CTContactRootDAO();
				cTContactRootDAO.insertNBSContactHist(oldContactVO, contactVO, fromAction);
				this.oldContactVO = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CTContactEJB.insertHistory: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	public void setCTContactVO(CTContactVO ctcontactVO) throws NEDSSConcurrentDataException,java.rmi.RemoteException{
		try
		{
			logger.debug("CTContactEJB:contactVO.getTheCTContactDT().getVersionCtrlNbr():"+contactVO.getcTContactDT().getVersionCtrlNbr());
			logger.debug("CTContactEJB:ctcontactVO.getTheCTContactDT().getVersionCtrlNbr():"+ctcontactVO.getcTContactDT().getVersionCtrlNbr());
			if (this.contactVO.getcTContactDT().getVersionCtrlNbr().intValue() !=
				ctcontactVO.getcTContactDT().getVersionCtrlNbr().intValue() )
			{
				logger.error("CTContactEJB:Throwing NEDSSConcurrentDataException");
				throw new NEDSSConcurrentDataException
				( "CTContactEJB:NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
			}
			ctcontactVO.getcTContactDT().setVersionCtrlNbr(new Integer(ctcontactVO.getcTContactDT().getVersionCtrlNbr().intValue()+1));
			logger.debug("CTContactEJB:ctcontactVO.getCTContactDT().getVersionCtrlNbr()after increment:"+ctcontactVO.getcTContactDT().getVersionCtrlNbr());
			oldContactVO = this.contactVO;
			this.contactVO = ctcontactVO;
		}
		catch(Exception e)
		{
			logger.debug("CTContactEJB:"+e.toString()+" : setCTContactVO dataconcurrency catch: " + e.getClass());
			logger.debug("CTContactEJB:Exception string is: " + e.toString());
			if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
			{
				logger.fatal("CTContactEJB.setCTContactVO: NEDSSConcurrentDataException: " + e.getMessage(), e);
				throw new NEDSSConcurrentDataException(e.getMessage(), e);
			}
			else
			{
				logger.fatal("CTContactEJB.setCTContactVO: Exception: " + e.getMessage(), e);
				throw new EJBException(e.getMessage(), e);
			}
		}
	}//setCTContactVO

	public void setEntityContext(javax.ejb.EntityContext ctx)
	{
		try {
			this.cntx = ctx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CTContactEJB.setEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	public void unsetEntityContext()
	{
		try {
			this.cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CTContactEJB.unsetEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

}
