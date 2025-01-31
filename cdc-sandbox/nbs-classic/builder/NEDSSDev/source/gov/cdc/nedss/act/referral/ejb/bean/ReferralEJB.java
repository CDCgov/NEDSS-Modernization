/**
* Name:		EJB class for Referral Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.referral.ejb.bean;

import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.act.referral.ejb.dao.ReferralHistoryManager;
import gov.cdc.nedss.act.referral.ejb.dao.ReferralRootDAOImpl;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public class ReferralEJB implements EntityBean
{
	private static final long serialVersionUID = 1L;
    private ReferralVO referralVO;
    private ReferralVO oldReferralVo;
    private long referralUID;
    private EntityContext cntx;
    private ReferralRootDAOImpl referralRootDAO = null;

    //For logging
     static final LogUtils logger = new LogUtils(ReferralEJB.class.getName());


    public ReferralEJB ()
    {
    }

    /**
     * Gets a Referral object containing all attributes to find a referral
     */

    public ReferralVO getReferralVO()
    {
        return referralVO;
        /*return (new ReferralValueObject(	referralVO.getActivityClassCd(), referralVO.getReferralUID(),
                		referralVO.getReferralStatusCd(), 	referralVO.getReferralTypeCd(),
                		referralVO.getReferralTypeDescTxt(), 	referralVO.getReferralLastUpdate(),
                		referralVO.getReferralBirthDate(), 	referralVO.getReferralCalcDOB(),
                                referralVO.getReferralAge(), 		referralVO.getReferralCalcAge(),
                		referralVO.getReferralAgeCategory(), 	referralVO.getReferralAgeType(),
                		referralVO.getReferralBirthOrder(), 	referralVO.getMultiBirthIndicator(),
                		referralVO.getReferralBirthRegNumber(), 	referralVO.getReferralMotherId(),
                		referralVO.getReferralSexAtBirth(), 	referralVO.getReferralStateOfBirth(),
                		referralVO.getReferralDeathDate(), 	referralVO.getReferralDeathInd(),
                		referralVO.getReferralEthinictyCd(), 	referralVO.getReferralCurPrimaryLanguage(),
                		referralVO.getReferralCurOccupation(),	referralVO.getReferralMaritalStatus(),
                		referralVO.getReferralSSN(),		referralVO.getReferralDLN(),
                		referralVO.getReferralGender(),		referralVO.getReferralAccountNumber(),
                		referralVO.getReferralMedicaidNumber(),
                		referralVO.getReferralCurSex(), 		referralVO.getChildrenInResidence(),
                		referralVO.getAdultsInResidence(), 	referralVO.getEducationLevel(),
                		referralVO.getReferralName(), 	        referralVO.getReferralPreferredName(),
                		referralVO.getReferralMaidenName(),      referralVO.getReferralAlaskan(),
  				referralVO.getReferralAfrican(),	        referralVO.getReferralAsian(),
   				referralVO.getReferralPacific(),	        referralVO.getReferralWhite(),
   				referralVO.getReferralUnknown(),	        referralVO.getReferralOther(),
   				referralVO.getReferralHispanic(),	referralVO.getReferralCurHomeAddress(),
                		referralVO.getReferralCurWorkAddress(),
                		referralVO.getReferralBirthAddress()));*/
    }

    /*Sets referral attributes to the values passed in as attributes
       of the value object. */

    public void setReferralVO(ReferralVO referralVO) throws NEDSSConcurrentDataException
    {
        try
      {
          if (this.referralVO.getTheReferralDT().getVersionCtrlNbr().intValue() !=
                  referralVO.getTheReferralDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          referralVO.getTheReferralDT().setVersionCtrlNbr(new Integer(referralVO.getTheReferralDT().getVersionCtrlNbr().intValue()+1));
          oldReferralVo = this.referralVO;
          this.referralVO = referralVO;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setReferralVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
              logger.fatal("Throwing NEDSSConcurrentDataException"+e.getMessage(),e);
              throw new NEDSSConcurrentDataException( e.getMessage(),e);
          }
          else
          {
              logger.fatal("Throwing generic Exception"+e.getMessage(),e);
              throw new EJBException(e.getMessage(),e);
          }
      }
    }

    public ReferralDT getReferralInfo()
    {
        return referralVO.getTheReferralDT();
    }

    public Collection<Object> getReferralIDs()
    {
        return referralVO.getTheActivityIdDTCollection();
    }

    public Collection<Object> getLocators()
    {
        return referralVO.getTheActivityLocatorParticipationDTCollection();
    }

    public EntityContext getEntityContext()
    {
        return cntx;
    }

    public void setEntityContext(EntityContext cntx)
    {
        this.cntx = cntx;
    }

    public void unsetEntityContext()
    {
        cntx = null;
    }

    public Long ejbCreate(ReferralVO referralVO)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
logger.debug("EjbCreate is called");

        this.referralVO = referralVO;

        try
        {
            if(referralRootDAO == null)
            {
                referralRootDAO = (ReferralRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_ROOT_DAO_CLASS);
            }
            this.referralVO.getTheReferralDT().setVersionCtrlNbr(new Integer(1));
            if(this.referralVO.getTheReferralDT().getSharedInd() == null)
            this.referralVO.getTheReferralDT().setSharedInd("T");
            referralUID = referralRootDAO.create(this.referralVO);
            this.referralVO.getTheReferralDT().setReferralUid(new Long(referralUID));
        }
        catch(NEDSSSystemException ndsex)
        {

            logger.fatal("NEDSSSystemException occured: "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception occured: " + ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
        return (new Long(referralUID));
    }

    public void ejbActivate() throws EJBException
    {
    }

    public void ejbPassivate() throws EJBException
    {
        this.referralRootDAO = null;
    }

    public void ejbRemove() throws RemoveException, EJBException
    {
        try
        {
            if(referralRootDAO == null)
            {
                referralRootDAO = (ReferralRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_ROOT_DAO_CLASS);
            }
            //insertHistory();
            referralRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());

        }
        catch(NEDSSSystemException ndsex)
        {

            logger.fatal("NEDSSSystemException occured: " + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
    }

    public void ejbStore() throws EJBException
    {
logger.debug("EjbStore is called");

        try
        {
            if(referralRootDAO == null)
            {
                referralRootDAO = (ReferralRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_ROOT_DAO_CLASS);
            }
            if(this.referralVO != null && this.referralVO.isItDirty())
            {
                try
                {
                    referralRootDAO.store(this.referralVO);
                    //referralRootDAO.store(this.referralVO);


                    this.referralVO.setItDirty(false);
                    this.referralVO.setItNew(false);
                    //Waiting on the "go ahead" to uncomment
                    //insertHistory();Not required as of 6/27/2002
                 }
                 catch(NEDSSConcurrentDataException ndcex)
                 {
                    logger.fatal("NEDSSConcurrentDataException in RefrralEJB"+ndcex.getMessage(),ndcex);
                    //cntx.setRollbackOnly();
                    throw new NEDSSSystemException(ndcex.getMessage(),ndcex);
                 }
            }
        }
        catch(NEDSSSystemException napex)
        {
            logger.fatal("NEDSSSystemException occured: "+napex.getMessage(), napex);
            throw new EJBException(napex.getMessage(),napex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception occured: " + ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }

    }

    public void ejbLoad() throws EJBException
    {
logger.debug("EjbLoad is called");
        try
        {
            if(referralRootDAO == null)
            {
                referralRootDAO = (ReferralRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_ROOT_DAO_CLASS);
            }
            this.referralVO = (ReferralVO)referralRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.referralVO.setItDirty(false);
            this.referralVO.setItNew(false);
        }
        catch(NEDSSSystemException npdaex)
        {
            logger.fatal("NEDSSSystemException occured: "+npdaex.getMessage(), npdaex);
            throw new EJBException(npdaex.getMessage(),npdaex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception occured: "+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
    }

    public void ejbPostCreate(ReferralVO referralVO)
            throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
        //setReferralID(((Integer)cntx.getPrimaryKey()).intValue());
    }

    public Long ejbFindByPrimaryKey(Long pk) throws RemoteException,
        FinderException, EJBException, NEDSSSystemException
    {
logger.debug("EjbFindByPrimaryKey is called");
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                if(referralRootDAO == null)
                {
                    referralRootDAO = (ReferralRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_ROOT_DAO_CLASS);
                }
                findPK = referralRootDAO.findByPrimaryKey(pk.longValue());
logger.debug("return findpk: " + findPK);
                //this.referralVO = (ReferralVO)referralRootDAO.loadObject(pk.longValue());
            }
        }
       catch(NEDSSSystemException nsex)
        {
            logger.fatal("NEDSSSystemException in find by primary key" + nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception occured: " + ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
        return findPK;
    }
    private void insertHistory() throws NEDSSSystemException{
      try {
		//If oldReferralVO is not null insert into history
		  if(oldReferralVo != null) {
		    logger.debug("ReferralEJB in ejbStore(), referralUID in oldReferralVo : " + oldReferralVo.getTheReferralDT().getReferralUid().longValue());
		    long oldReferralUID = oldReferralVo.getTheReferralDT().getReferralUid().longValue();
		    short versionCtrlNbr = oldReferralVo.getTheReferralDT().getVersionCtrlNbr().shortValue();
		    ReferralHistoryManager referralHistoryManager = new ReferralHistoryManager(oldReferralUID, versionCtrlNbr);
		    referralHistoryManager.store(this.oldReferralVo);
		    this.oldReferralVo = null;
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("Exception occured: " + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
		}
    }
}// end of ReferralEJB bean class
