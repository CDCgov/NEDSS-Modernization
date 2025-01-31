/**
* Name:		EJB class for ClinicalDocument Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.clinicaldocument.ejb.bean;

import java.util.Collection;
import java.util.Date;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.RemoveException;

import java.sql.SQLException;

import javax.ejb.FinderException;



import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.clinicaldocument.vo.*;
import gov.cdc.nedss.act.clinicaldocument.ejb.dao.*;
import gov.cdc.nedss.act.clinicaldocument.dt.*;
import gov.cdc.nedss.systemservice.util.*;

public class ClinicalDocumentEJB implements EntityBean
{
	private static final long serialVersionUID = 1L;
    private ClinicalDocumentVO clinicalDocumentVO;
    private ClinicalDocumentVO oldCDVo;
    private long clinicaldocumentUID;
    private EntityContext cntx;
    private ClinicalDocumentRootDAOImpl clinicaldocumentRootDAO = null;

    //For logging
    static final LogUtils logger = new LogUtils(ClinicalDocumentEJB.class.getName());


    public ClinicalDocumentEJB ()
    {
    }

    /**
     * Gets a ClinicalDocument object containing all attributes to find a clinicaldocument
     */

    public ClinicalDocumentVO getClinicalDocumentVO() throws RemoteException
    {
        return clinicalDocumentVO;
        /*return (new ClinicalDocumentValueObject(	pvo.getActivityClassCd(), pvo.getClinicalDocumentUID(),
                		pvo.getClinicalDocumentStatusCd(), 	pvo.getClinicalDocumentTypeCd(),
                		pvo.getClinicalDocumentTypeDescTxt(), 	pvo.getClinicalDocumentLastUpdate(),
                		pvo.getClinicalDocumentBirthDate(), 	pvo.getClinicalDocumentCalcDOB(),
                                pvo.getClinicalDocumentAge(), 		pvo.getClinicalDocumentCalcAge(),
                		pvo.getClinicalDocumentAgeCategory(), 	pvo.getClinicalDocumentAgeType(),
                		pvo.getClinicalDocumentBirthOrder(), 	pvo.getMultiBirthIndicator(),
                		pvo.getClinicalDocumentBirthRegNumber(), 	pvo.getClinicalDocumentMotherId(),
                		pvo.getClinicalDocumentSexAtBirth(), 	pvo.getClinicalDocumentStateOfBirth(),
                		pvo.getClinicalDocumentDeathDate(), 	pvo.getClinicalDocumentDeathInd(),
                		pvo.getClinicalDocumentEthinictyCd(), 	pvo.getClinicalDocumentCurPrimaryLanguage(),
                		pvo.getClinicalDocumentCurOccupation(),	pvo.getClinicalDocumentMaritalStatus(),
                		pvo.getClinicalDocumentSSN(),		pvo.getClinicalDocumentDLN(),
                		pvo.getClinicalDocumentGender(),		pvo.getClinicalDocumentAccountNumber(),
                		pvo.getClinicalDocumentMedicaidNumber(),
                		pvo.getClinicalDocumentCurSex(), 		pvo.getChildrenInResidence(),
                		pvo.getAdultsInResidence(), 	pvo.getEducationLevel(),
                		pvo.getClinicalDocumentName(), 	        pvo.getClinicalDocumentPreferredName(),
                		pvo.getClinicalDocumentMaidenName(),      pvo.getClinicalDocumentAlaskan(),
  				pvo.getClinicalDocumentAfrican(),	        pvo.getClinicalDocumentAsian(),
   				pvo.getClinicalDocumentPacific(),	        pvo.getClinicalDocumentWhite(),
   				pvo.getClinicalDocumentUnknown(),	        pvo.getClinicalDocumentOther(),
   				pvo.getClinicalDocumentHispanic(),	pvo.getClinicalDocumentCurHomeAddress(),
                		pvo.getClinicalDocumentCurWorkAddress(),
                		pvo.getClinicalDocumentBirthAddress()));*/
    }

    /*Sets clinicaldocument attributes to the values passed in as attributes
       of the value object. */

    public void setClinicalDocumentVO(ClinicalDocumentVO clinicalDocumentVO)throws RemoteException, NEDSSConcurrentDataException
    {
      try
      {
          if (this.clinicalDocumentVO.getTheClinicalDocumentDT().getVersionCtrlNbr().intValue() !=
                  clinicalDocumentVO.getTheClinicalDocumentDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          clinicalDocumentVO.getTheClinicalDocumentDT().setVersionCtrlNbr(new Integer(clinicalDocumentVO.getTheClinicalDocumentDT().getVersionCtrlNbr().intValue()+1));
          oldCDVo = this.clinicalDocumentVO;
          this.clinicalDocumentVO = clinicalDocumentVO;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setClinicalDocumentVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
              logger.fatal("Throwing NEDSSConcurrentDataException"+e.getMessage(),e);
              throw new NEDSSConcurrentDataException( e.getMessage(),e);
          }
          else
          {
              logger.fatal("Throwing generic Exception"+e.getMessage(),e);
              throw new EJBException(e.toString(),e);
          }
      }

    }

    public ClinicalDocumentDT getClinicalDocumentInfo()  throws RemoteException, NEDSSConcurrentDataException
    {
        return clinicalDocumentVO.getTheClinicalDocumentDT();
    }

    public Collection<Object>  getClinicalDocumentIDs() throws RemoteException, NEDSSConcurrentDataException
    {
        return clinicalDocumentVO.getTheActivityIdDTCollection();
    }

    public Collection<Object>  getLocators() throws RemoteException
    {
        return clinicalDocumentVO.getTheActivityLocatorParticipationDTCollection();
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

    public Long ejbCreate(ClinicalDocumentVO clinicalDocumentVO)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
logger.debug("EjbCreate is called");

        this.clinicalDocumentVO = clinicalDocumentVO;

        try
        {
            if(clinicaldocumentRootDAO == null)
            {
                clinicaldocumentRootDAO = (ClinicalDocumentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_ROOT_DAO_CLASS);
            }
            this.clinicalDocumentVO.getTheClinicalDocumentDT().setVersionCtrlNbr(new Integer(1));
            if(this.clinicalDocumentVO.getTheClinicalDocumentDT().getSharedInd() == null)
            this.clinicalDocumentVO.getTheClinicalDocumentDT().setSharedInd("T");
            clinicaldocumentUID = clinicaldocumentRootDAO.create(this.clinicalDocumentVO);
            this.clinicalDocumentVO.getTheClinicalDocumentDT().setClinicalDocumentUid(new Long(clinicaldocumentUID));
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("NEDSSSystemException occured: "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception occured: "+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
        return (new Long(clinicaldocumentUID));
    }

    public void ejbActivate() throws EJBException
    {
    }

    public void ejbPassivate() throws EJBException
    {
        this.clinicaldocumentRootDAO = null;
    }

    public void ejbRemove() throws RemoveException, EJBException
    {
        try
        {
            if(clinicaldocumentRootDAO == null)
            {
                clinicaldocumentRootDAO = (ClinicalDocumentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_ROOT_DAO_CLASS);
            }
            //insertHistory();
            clinicaldocumentRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());

        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("NEDSSSystemException occured: "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
    }

    public void ejbStore()
    {
logger.debug("EjbStore is called");

        try
        {
            if(clinicaldocumentRootDAO == null)
            {
                clinicaldocumentRootDAO = (ClinicalDocumentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_ROOT_DAO_CLASS);
            }
            if(this.clinicalDocumentVO != null && this.clinicalDocumentVO.isItDirty())
            {
               try
               {
                clinicaldocumentRootDAO.store(this.clinicalDocumentVO);

                this.clinicalDocumentVO.setItDirty(false);
                this.clinicalDocumentVO.setItNew(false);
                //Waiting on the "go ahead" to uncomment
                //insertHistory();Not required as of 6/27/02
               }
               catch(NEDSSConcurrentDataException ndcex)
               {
                  logger.debug("Got into concurrent exception in interventionEJB");
                  //cntx.setRollbackOnly();
                   throw new NEDSSSystemException("NEDSSConcurrentDataException in interventionEJB");
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
         logger.fatal("Exception occured:"+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }

    }

    public void ejbLoad() throws EJBException
    {
logger.debug("EjbLoad is called");
        try
        {
            if(clinicaldocumentRootDAO == null)
            {
                clinicaldocumentRootDAO = (ClinicalDocumentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_ROOT_DAO_CLASS);
            }
            this.clinicalDocumentVO = (ClinicalDocumentVO)clinicaldocumentRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.clinicalDocumentVO.setItDirty(false);
            this.clinicalDocumentVO.setItNew(false);
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

    public void ejbPostCreate(ClinicalDocumentVO clinicalDocumentVO)
            throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
        //setClinicalDocumentID(((Integer)cntx.getPrimaryKey()).intValue());
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
                if(clinicaldocumentRootDAO == null)
                {
                    clinicaldocumentRootDAO = (ClinicalDocumentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_ROOT_DAO_CLASS);
                }
                findPK = clinicaldocumentRootDAO.findByPrimaryKey(pk.longValue());
logger.debug("return findpk: " + findPK);
                //this.clinicalDocumentVO = (ClinicalDocumentVO)clinicaldocumentRootDAO.loadObject(pk.longValue());
            }
        }
       catch(NEDSSSystemException nsex)
        {
            logger.fatal("NEDSSSystemException in find by primary key"+nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception in find by primary key"+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
        return findPK;
    }

  private void insertHistory()throws NEDSSSystemException {
    try {
					//If oldCDVo is not null insert into history
					if(oldCDVo != null) {
					  logger.debug("ClinicalDocumentEJB in ejbStore(), ClinicalDocumentUID in oldClinicalDocumentVO : " + oldCDVo.getTheClinicalDocumentDT().getClinicalDocumentUid().longValue());
					  long oldCDUID = oldCDVo.getTheClinicalDocumentDT().getClinicalDocumentUid().longValue();
					  short versionCtrlNbr = oldCDVo.getTheClinicalDocumentDT().getVersionCtrlNbr().shortValue();
					  ClinicalDocumentHistoryManager clinicalDocumentHistoryManager = new ClinicalDocumentHistoryManager(oldCDUID, versionCtrlNbr);
					  clinicalDocumentHistoryManager.store(this.oldCDVo);
					  this.oldCDVo = null;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.fatal(e.getMessage(), e);
			         throw new EJBException(e.getMessage(),e);
				}
  }
}// end of ClinicalDocumentEJB bean class
