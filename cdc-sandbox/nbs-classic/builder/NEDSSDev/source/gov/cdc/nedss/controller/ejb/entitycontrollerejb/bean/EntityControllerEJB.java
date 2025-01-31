
//
// Original code was made by:
// -- Java Code Generation Process --

package gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean;

// Import Statements

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entitygroup.dt.EntityGroupDT;
import gov.cdc.nedss.entity.entitygroup.ejb.bean.EntityGroup;
import gov.cdc.nedss.entity.entitygroup.ejb.bean.EntityGroupHome;
import gov.cdc.nedss.entity.entitygroup.vo.EntityGroupVO;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.ejb.bean.Material;
import gov.cdc.nedss.entity.material.ejb.bean.MaterialHome;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.NonPersonLivingSubjectDT;
import gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean.NonPersonLivingSubject;
import gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean.NonPersonLivingSubjectHome;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.NonPersonLivingSubjectVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.ejb.bean.Organization;
import gov.cdc.nedss.entity.organization.ejb.bean.OrganizationHome;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.ejb.bean.Person;
import gov.cdc.nedss.entity.person.ejb.bean.PersonHome;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.ejb.bean.Place;
import gov.cdc.nedss.entity.place.ejb.bean.PlaceHome;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.helper.LDFHelper;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindPersonDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPatientMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean.MPRUpdateEngine;
import gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean.MPRUpdateEngineHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

/**
 * Title: EntityControllerEJB class Description: This is the bean class that
 * implements all the methods used by remote interface(EntityController).
 * EntityControllerEJB is a session bean that is used to access entities(Person,
 * organization, material, EntityGroup, NonPersonLivingSubject) and their
 * dependent objects(like person name, person race etc). Copyright: Copyright
 * (c) 2002 Company: Computer Sciences Corporation
 * 
 * @version 1.0
 * @author 11/29/2001 Rich Randazzo, Sohrab Jahani & NEDSS Development Team
 * @modified 12/12/2001 Sohrab Jahani
 * @version 1.0.0
 */
/*
 * Name StateDefinedProxy should be changed. It does not have anything related
 * to proxy. It has few common methods for LDF.
 */
public class EntityControllerEJB implements javax.ejb.SessionBean {

	// private NedssUtils nedssUtils = null;
	static final LogUtils logger = new LogUtils(
			EntityControllerEJB.class.getName());

	private SessionContext cntx;
	private SessionContext sessioncontext;

	/**
	 * @roseuid 3BEFB6EF039F
	 * @J2EE_METHOD -- EntityControllerEJB
	 */
	public EntityControllerEJB() {
		// nedssUtils = new NedssUtils();
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 * 
	 * @roseuid 3BEFB6EF03BD
	 * @J2EE_METHOD -- ejbRemove
	 */
	public void ejbRemove() {

	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 * 
	 * @roseuid 3BEFB6EF03D1
	 * @J2EE_METHOD -- ejbActivate
	 */
	public void ejbActivate() {

	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 * 
	 * @roseuid 3BEFB6EF03DB
	 * @J2EE_METHOD -- ejbPassivate
	 */
	public void ejbPassivate() {

	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 * 
	 * @roseuid 3C02AFF502A3
	 * @J2EE_METHOD -- ejbCreate
	 */
	public void ejbCreate() {
		logger.debug("EntityControllerEJB create method ----------------------------- ");
	}

	/**
	 * Returns the personVO for a given personUID, it makes call to
	 * getPersonInternal.
	 * 
	 * @param personUID
	 *            the person UID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonVO for a given personUID.
	 * @throws EJBException
	 * @roseuid 3C02AFFC00EB
	 * @J2EE_METHOD -- getPerson
	 */

	public PersonVO getPerson(Long personUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {

		PersonVO personVO = null;

		try {
			personVO = this.getPersonInternal(personUID, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("EntityControllerEJB.getPerson: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
		return personVO;
	}

	/**
	 * gets the revision for the patient
	 * 
	 * @param personUID
	 * @param nbsSecurityObj
	 * @return
	 * @throws EJBException
	 */
	public PersonVO getPatientRevision(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws EJBException {
		return getPersonInternal(personUID, nbsSecurityObj);
	}

	/**
	 * Creates(if personVO.isItNew() is true) or updates(if personVO.isItDirty()
	 * is true) the personVO
	 * 
	 * @param personVO
	 *            the personVO object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Long value representing the personUID created/updated in the
	 *         process
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFC018B
	 * @J2EE_METHOD -- setPerson
	 */
	public Long setPerson(PersonVO personVO, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSConcurrentDataException {
		Long personUID = new Long(-1);

		try {
			Person person = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
			logger.debug("PersonEJB lookup = " + obj.toString());
			PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,
					PersonHome.class);
			logger.debug("Found PersonHome: " + home + "| isItNew: "
					+ personVO.isItNew());

			Collection<Object> elpDTCol = personVO
					.getTheEntityLocatorParticipationDTCollection();
			Collection<Object> rDTCol = personVO.getTheRoleDTCollection();
			Collection<Object> pDTCol = personVO
					.getTheParticipationDTCollection();
			Collection<Object> col = null;
			if (elpDTCol != null) {
				col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
				personVO.setTheEntityLocatorParticipationDTCollection(col);
			}
			if (rDTCol != null) {
				col = this.iterateRDT(rDTCol, nbsSecurityObj);
				personVO.setTheRoleDTCollection(col);
			}
			if (pDTCol != null) {
				col = this.iteratePDT(pDTCol, nbsSecurityObj);
				personVO.setTheParticipationDTCollection(col);
			}

			if (personVO.isItNew()) {
				person = home.create(personVO);
				personUID = person.getPersonVO().getThePersonDT()
						.getPersonUid();
				logger.debug(" EntityControllerEJB.setPerson() Person Created");
			} else {
				person = home.findByPrimaryKey(personVO.getThePersonDT()
						.getPersonUid());
				person.setPersonVO(personVO);
				personUID = personVO.getThePersonDT().getPersonUid();
				logger.debug(" EntityControllerEJB.setPerson() Person Updated");
				//
				if(personVO.getThePersonDT().getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_SUPERCEDED)){
					FindPersonDAOImpl personDao = new FindPersonDAOImpl();
					personDao.deleteEdxPatientMatchDTColl(personUID);	
				}
			}

		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("EntityControllerEJB.setPerson: NEDSSConcurrentDataException: "  + ex.getMessage(),ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.setPerson: NEDSSConcurrentDataException: NEDSSConcurrentDataException: " + e.getMessage(),e);
				throw new NEDSSConcurrentDataException(e.getMessage(),e);
			} else {
				logger.fatal("EntityControllerEJB.setPerson: Exception: " + e.getMessage(),e);
				throw new javax.ejb.EJBException(e.getMessage(),e);
			}
		}
		return personUID;
	}

	/**
	 * if parent uid is null, the person is MPR, make a clone for revision
	 * 
	 * @param personVO
	 * @param businessTriggerCd
	 * @param nbsSecurityObj
	 * @return
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3E7B3806004E
	 * @J2EE_METHOD -- setPatientRevision
	 */
	public java.lang.Long setPatientRevision(PersonVO personVO,
			String businessTriggerCd, NBSSecurityObj nbsSecurityObj)
			throws EJBException, NEDSSConcurrentDataException {
		PersonVO mprPersonVO = null;
		Long mprPersonUid = null;
		Long personUid = null;
		try {
			PersonDT personDT = personVO.getThePersonDT();
			if (personDT.getPersonParentUid() == null) {
				// todo cloneVO
				try {
					mprPersonVO = this.cloneVO(personVO);
					// as per shannon comments should not reflect on mpr
					mprPersonVO.getThePersonDT().setDescription(null);
					mprPersonVO.getThePersonDT().setAsOfDateAdmin(null);
					mprPersonVO.getThePersonDT().setAgeReported(null);
					mprPersonVO.getThePersonDT().setAgeReportedUnitCd(null);
					if (mprPersonVO.getThePersonDT().getCurrSexCd() == null
							|| mprPersonVO.getThePersonDT().getCurrSexCd().trim()
									.length() == 0)
						mprPersonVO.getThePersonDT().setAsOfDateSex(null);

				} catch (Exception e) {
					logger.debug(e);
				}
				mprPersonUid = this.setPersonInternal(mprPersonVO,
						NBSBOLookup.PATIENT, "PAT_CR", nbsSecurityObj);
				
				mprPersonVO = this.getPersonInternal(mprPersonUid, nbsSecurityObj);
				personVO.getThePersonDT().setPersonParentUid(mprPersonUid);
				personVO.getThePersonDT().setLocalId(
						mprPersonVO.getThePersonDT().getLocalId());
			} else {
				if (businessTriggerCd != null
						&& (businessTriggerCd.equals("PAT_CR") || businessTriggerCd
								.equals("PAT_EDIT"))) {
					this.updateWithRevision(personVO, nbsSecurityObj);
				}

				// civil00011674. If we are in this block,
				// personVO.getThePersonDT().getLocalId()
				// should never be null or empty. Somehow, this does happen
				// sporadically. The
				// following code fixed the defect. However, we need to research
				// more why
				// personVO.getThePersonDT().getLocalId() is null or empty in the
				// first place.
				if (personVO.getThePersonDT().getLocalId() == null
						|| personVO.getThePersonDT().getLocalId().trim().length() == 0) {
					mprPersonUid = personVO.getThePersonDT().getPersonParentUid();
					mprPersonVO = this.getPersonInternal(mprPersonUid,
							nbsSecurityObj);
					personVO.getThePersonDT().setLocalId(
							mprPersonVO.getThePersonDT().getLocalId());
				}
			}

			
			personUid = this.setPersonInternal(personVO, NBSBOLookup.PATIENT,
					businessTriggerCd, nbsSecurityObj);
			if (personVO.getThePersonDT() != null && (personVO.getThePersonDT().getElectronicInd() != null
					&& !personVO.getThePersonDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {// ldf code
																												// begin
				LDFHelper ldfHelper = LDFHelper.getInstance();
				ldfHelper.setLDFCollection(personVO.getTheStateDefinedFieldDataDTCollection(), personVO.getLdfUids(),
						NEDSSConstants.PATIENT_LDF, null, personUid, nbsSecurityObj);
			}
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPatientRevision: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
		// ldf code end
		return personUid;
	}

	private PersonVO cloneVO(PersonVO personVO)
			throws CloneNotSupportedException, IOException,
			ClassNotFoundException {
		try {
			if (personVO != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(personVO);
				ByteArrayInputStream bais = new ByteArrayInputStream(
						baos.toByteArray());
				ObjectInputStream ois = new ObjectInputStream(bais);
				Object clonePersonVO = ois.readObject();

				return (PersonVO) clonePersonVO;
			} else
				return personVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.cloneVO: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	private void updateWithRevision(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		NedssUtils nedssUtils = new NedssUtils();
		try {

			if(!personVO.getThePersonDT().isReentrant()) {
				Object theLookedUpObject;
			theLookedUpObject = nedssUtils
					.lookupBean(JNDINames.MPR_UPDATE_ENGINE_EJB);
			MPRUpdateEngineHome mprHome = (MPRUpdateEngineHome) PortableRemoteObject
					.narrow(theLookedUpObject, MPRUpdateEngineHome.class);
			MPRUpdateEngine mprUpdateEngine = mprHome.create();
			mprUpdateEngine.updateWithRevision(personVO, nbsSecurityObj);
			}

		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.updateWithRevision: " + e.getMessage(), e);
				throw new NEDSSConcurrentDataException(e.getMessage(), e);
			} else {
				logger.fatal("EntityControllerEJB.updateWithRevision: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}

	}

	/**
	 * @roseuid 3E7B38130157
	 * @J2EE_METHOD -- getProvider
	 */
	public PersonVO getProvider(Long personUID, NBSSecurityObj nbsSecurityObj)
			throws EJBException {
		return getPersonInternal(personUID, nbsSecurityObj);
	}

	/**
	 * @roseuid 3E7B38040196
	 * @J2EE_METHOD -- setProvider
	 */
	public Long setProvider(PersonVO personVO, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSConcurrentDataException {
		try {
			boolean callOrgHashCode= false;
			if(personVO.isItNew() && personVO.getThePersonDT().isItNew() && personVO.getThePersonDT().getElectronicInd().equalsIgnoreCase("Y") 
					&& !personVO.getThePersonDT().isCaseInd()){
				callOrgHashCode= true;
				personVO.getThePersonDT().setEdxInd("Y");

			}
			long personUid= setPersonInternal(personVO, "PROVIDER", businessTriggerCd,
					nbsSecurityObj);
			
			if(callOrgHashCode){
				EdxMatchingCriteriaUtil edxMatchingCriteriaUtil = new EdxMatchingCriteriaUtil();
				try {
					personVO.getThePersonDT().setPersonUid(personUid);
					edxMatchingCriteriaUtil.setProvidertoEntityMatch(personVO, nbsSecurityObj);
				} catch (Exception e) {
					logger.error("EntityControllerEJB.setProvider method exception thrown for matching criteria:"+e);
					throw new EJBException("EntityControllerEJB.setProvider method exception thrown for matching criteria:"+e);
				}
			}
			return personUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setProvider: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	/**
	 * @roseuid 3E7B38040196
	 * @J2EE_METHOD -- setProvider
	 */
	public Long setPlace(PlaceVO placeVO, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSConcurrentDataException {

		return setPlace(placeVO, "PLACE", businessTriggerCd,
				nbsSecurityObj);

	}

	/**
	 * Returns the personDT for a given personUID.
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonDT for a given personUID.
	 * @throws EJBException
	 * @roseuid 3C02AFFC020D
	 * @J2EE_METHOD -- getPersonInfo
	 */
	public PersonDT getPersonInfo(Long personUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			PersonDT personDT = null;
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personDT = personVO.getThePersonDT();
			logger.debug("EntityController.getPersonInfo(): PersonDT = " + personDT);
			return personDT;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the personDT object
	 * 
	 * @param personDT
	 *            the personDT object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFC02A3
	 * @J2EE_METHOD -- setPersonInfo
	 */
	public void setPersonInfo(PersonDT personDT, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSConcurrentDataException {
		try {
			PersonVO personVO = null;
			if (personDT.getPersonUid() != null)
				personVO = getPerson(personDT.getPersonUid(), nbsSecurityObj);
			if (personVO != null)
				personVO.setThePersonDT(personDT);
			personVO.setItNew(false);
			personVO.setItDirty(true);
			this.setPerson(personVO, nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPersonInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the Collection<Object> of personNameDT's for a given personUID
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonNameDT Collection<Object> for a given personUID
	 * @throws EJBException
	 * @roseuid 3C02AFFC032F
	 * @J2EE_METHOD -- getPersonNames
	 */
	public Collection<Object> getPersonNames(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personNameDTs = new ArrayList<Object>();
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personNameDTs = personVO.getThePersonNameDTCollection();

			return personNameDTs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonNames: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the personNamesDT for a given personUID for a given nmUseCd
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nmUseCd
	 *            the nmUseCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonNameDT for a given personUID
	 * @throws EJBException
	 * @roseuid 3C02AFFC03BC
	 * @J2EE_METHOD -- getPersonNames
	 */
	public PersonNameDT getPersonName(Long personUID, String nmUseCd,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personNameDTs = new ArrayList<Object>();
			PersonNameDT personNameDT = null;

			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personNameDTs = personVO.getThePersonEthnicGroupDTCollection();

			Iterator<Object> anIterator = null;
			if (personNameDTs != null) {
				for (anIterator = personNameDTs.iterator(); anIterator.hasNext();) {
					personNameDT = (PersonNameDT) anIterator.next();
					if ((nmUseCd != null)
							&& (personNameDT.getNmUseCd().compareTo(nmUseCd.trim()) == 0)) {
						logger.debug(" EntityControllerEJB.getPersonNames() got PersonNameDT for nmUseCd = "
								+ nmUseCd);
						return personNameDT;
					} else {
						continue;
					}
				}

			}

			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonName: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Updates the personNamesDT Collection<Object> object
	 * 
	 * @param personNames
	 *            the personNameDT Collection<Object> object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFD00D8
	 * @J2EE_METHOD -- setPersonNames
	 */
	public void setPersonNames(Collection<Object> personNames,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			PersonVO personVO = null;
			PersonNameDT personNameDT = null;
			Iterator<Object> anIterator = null;
			if (personNames != null) {
				anIterator = personNames.iterator();
				if (anIterator.hasNext()) {
					personNameDT = (PersonNameDT) anIterator.next();
					personVO = this.getPerson(personNameDT.getPersonUid(),
							nbsSecurityObj);
					personVO.setThePersonNameDTCollection(personNames);
					personVO.setItNew(false);
					personVO.setItDirty(true);
					this.setPerson(personVO, nbsSecurityObj);
					logger.debug("EntityController.setPersonNames() has been set for the personUid = "
							+ personNameDT.getPersonUid());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPersonNames: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns the personRaceDT Collection<Object> object
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonRaceDT Collection<Object> for a given personUID
	 * @throws EJBException
	 * @roseuid 3C02AFFD0182
	 * @J2EE_METHOD -- getPersonRaces
	 */
	public Collection<Object> getPersonRaces(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personRaceDTs = new ArrayList<Object>();
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personRaceDTs = personVO.getThePersonRaceDTCollection();

			return personRaceDTs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonRaces: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * @roseuid 3E7B38140232
	 * @J2EE_METHOD -- getPersonInternal
	 */
	private PersonVO getPersonInternal(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws EJBException {
		PersonVO personVO = null;

		try {
			Person person = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
			logger.debug("Ent Controller lookup = " + obj.toString());
			PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,
					PersonHome.class);

			if (personUID != null)
				person = home.findByPrimaryKey(personUID);
			if (person != null)
				personVO = person.getPersonVO();
			// for LDFs
			if (personVO.getThePersonDT() != null && (personVO.getThePersonDT().getElectronicInd() != null
					&& !personVO.getThePersonDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
			ArrayList<Object> ldfList = new ArrayList<Object>();
			try {
				LDFHelper ldfHelper = LDFHelper.getInstance();
				ldfList = (ArrayList<Object>) ldfHelper.getLDFCollection(
						personUID, null, nbsSecurityObj);
			} catch (Exception e) {
				logger.error("Exception occured while retrieving LDFCollection<Object>  = "
						+ e.toString());
			}

			if (ldfList != null) {
				logger.debug("Before setting LDFCollection<Object>  = "
						+ ldfList.size());
				personVO.setTheStateDefinedFieldDataDTCollection(ldfList);
			}
			}

			logger.debug("Ent Controller past the find - person = "
					+ person.toString());
			logger.debug("Ent Controllerpast the find - person.getPrimaryKey = "
					+ person.getPrimaryKey());

		} catch (Exception e) {
			logger.fatal("EntityControllerEJB.getPersonInternal: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
		return personVO;

	}

	/**
	 * @roseuid 3E7B380C036B
	 * @J2EE_METHOD -- setPersonInternal
	 */
	private Long setPersonInternal(PersonVO personVO,
			String businessObjLookupName, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSConcurrentDataException {
		Long personUID = new Long(-1);
		String localId = "";
		boolean isELRCase = false;
		try {
			if (personVO.isItNew() || personVO.isItDirty()) {

				// changed as per shannon and chase, keep the temp localid and
				// set it back to personDT after prepareVOUtils
				if (personVO.getThePersonDT().isItNew()
						&& !(businessObjLookupName
								.equalsIgnoreCase(NEDSSConstants.businessObjLookupNamePROVIDER)))
					localId = personVO.getThePersonDT().getLocalId();

				if(localId==null){
					personVO.getThePersonDT().setEdxInd("Y");
					isELRCase= true;
				}
					
				PrepareVOUtils prepVOUtils = new PrepareVOUtils();
				RootDTInterface personDT = prepVOUtils.prepareVO(
						personVO.getThePersonDT(), businessObjLookupName,
						businessTriggerCd, "PERSON", "BASE", nbsSecurityObj);

				if (personVO.getThePersonDT().isItNew()
						&& !(businessObjLookupName
								.equalsIgnoreCase(NEDSSConstants.businessObjLookupNamePROVIDER)))
					personDT.setLocalId(localId);

				personVO.setThePersonDT((PersonDT) personDT);
				Collection<Object> coll = null;
				coll = personVO.getTheEntityLocatorParticipationDTCollection();
				if (coll != null) {
					iterateELPDT(coll, nbsSecurityObj);
					personVO.setTheEntityLocatorParticipationDTCollection(coll);
				}

				coll = personVO.getTheRoleDTCollection();
				if (coll != null) {
					iterateRDT(coll, nbsSecurityObj);
					personVO.setTheRoleDTCollection(coll);
				}
				coll = personVO.getTheParticipationDTCollection();
				if (coll != null) {
					iteratePDT(coll, nbsSecurityObj);
					personVO.setTheParticipationDTCollection(coll);
				}

				preparePersonNameBeforePersistence(personVO, nbsSecurityObj);

				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
				logger.debug("Ent Controller lookup = " + obj.toString());
				PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,
						PersonHome.class);
				if (personVO.isItNew()) {

					Person person = home.create(personVO);
					personUID = person.getPersonVO().getThePersonDT()
							.getPersonUid();
					logger.debug(" EntityControllerEJB.setProvider() Person Created");
				} else {
					Person person = home.findByPrimaryKey(personVO
							.getThePersonDT().getPersonUid());
					person.setPersonVO(personVO);
					personUID = person.getPersonVO().getThePersonDT()
							.getPersonUid();
					logger.debug(" EntityControllerEJB.setProvider() Person Updated");
				}
				if(isELRCase){
					try {
						personVO.getThePersonDT().setPersonUid(personUID);
						personVO.getThePersonDT().setPersonParentUid(personUID);
						setPatientHashCd(personVO, nbsSecurityObj);
					} catch (RemoteException e) {
						logger.error("RemoteException thrown while creating hashcode for the ELR patient."+e);
						throw new EJBException(e);
					
					} catch (NEDSSAppException e) {
						logger.error("NEDSSAppException thrown while creating hashcode for the ELR patient."+e);
						throw new EJBException(e);
					}
				}
			}
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("EntityControllerEJB.setPersonInternal: NEDSSConcurrentDataException: concurrent access is not allowed: " + ex.getMessage(),ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.setPersonInternal: NEDSSConcurrentDataException: " + e.getMessage(),e);
				throw new NEDSSConcurrentDataException(e.getMessage(),e);
			} else {
				logger.fatal("EntityControllerEJB.setPersonInternal: Exception: " + e.getMessage(),e);
				throw new javax.ejb.EJBException(e.getMessage(),e);
			}
		}
		return personUID;
	}
	
	private Long setPlace(PlaceVO placeVO,
			String businessObjLookupName, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSConcurrentDataException {
		Long placeUID = new Long(-1);
		String localId = "";

		try {
			if (placeVO.isItNew() || placeVO.isItDirty()) {

				// keep the temp localid and set it back to placeDT after prepareVOUtils
				//TODO: change businessObjLookupName to Place
				if (placeVO.getThePlaceDT().isItNew()
						&& !(businessObjLookupName
								.equalsIgnoreCase(NEDSSConstants.businessObjLookupNamePLACE)))
					localId = placeVO.getThePlaceDT().getLocalId();


					
				PrepareVOUtils prepVOUtils = new PrepareVOUtils();
				RootDTInterface placeDT = prepVOUtils.prepareVO(
						placeVO.getThePlaceDT(), businessObjLookupName,
						businessTriggerCd, "PLACE", "BASE", nbsSecurityObj);
				// set localID back to placeDT after prepareVOUtils
				if (placeVO.getThePlaceDT().isItNew()
						&& !(businessObjLookupName
								.equalsIgnoreCase(NEDSSConstants.businessObjLookupNamePLACE)))
					placeDT.setLocalId(localId);

				placeVO.setThePlaceDT((PlaceDT) placeDT);
				Collection<Object> coll = null;

				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.PLACE);
				logger.debug("Ent Controller lookup = " + obj.toString());
				PlaceHome home = (PlaceHome) PortableRemoteObject.narrow(obj,
						PlaceHome.class);
				if (placeVO.isItNew()) {

					Place place = home.create(placeVO);
					placeUID = place.getPlaceVO().getThePlaceDT().getPlaceUid();
					logger.debug(" EntityControllerEJB.setPlace() Place Created");
				} else {
					Place place = home.findByPrimaryKey(placeVO
							.getThePlaceDT().getPlaceUid());
					place.setPlaceVO(placeVO);
					placeUID = place.getPlaceVO().getThePlaceDT().getPlaceUid();
					logger.debug(" EntityControllerEJB.setPlace() Place Updated");
				}
			}
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("EntityControllerEJB.setPlace: NEDSSConcurrentDataException: concurrent access is not allowed " + ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.setPlace: NEDSSConcurrentDataException: " + e.getMessage(), e);
				throw new NEDSSConcurrentDataException(e.getMessage(), e);
			} else {
				logger.fatal("EntityControllerEJB.setPlace: Exception: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}
		return placeUID;

	}

	/**
	 * @roseuid 3E7B17250186
	 * @J2EE_METHOD -- preparePersonNameBeforePersistence
	 */
	private void preparePersonNameBeforePersistence(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) {
		try {
			Collection<Object> namesCollection = personVO
					.getThePersonNameDTCollection();
			if (namesCollection != null && namesCollection.size() > 0) {

				Iterator<Object> namesIter = namesCollection.iterator();
				PersonNameDT selectedNameDT = null;
				while (namesIter.hasNext()) {
					PersonNameDT thePersonNameDT = (PersonNameDT) namesIter.next();
					if (thePersonNameDT.getNmUseCd() != null
							&& !thePersonNameDT.getNmUseCd().trim().equals("L"))
						continue;
					if (thePersonNameDT.getAsOfDate() != null) {
						if (selectedNameDT == null)
							selectedNameDT = thePersonNameDT;
						else if (selectedNameDT.getAsOfDate()!=null && thePersonNameDT.getAsOfDate()!=null  && thePersonNameDT.getAsOfDate().after(
								selectedNameDT.getAsOfDate())) {
							selectedNameDT = thePersonNameDT;
						}
					} else {
						if (selectedNameDT == null)
							selectedNameDT = thePersonNameDT;
					}
				}
				if (selectedNameDT != null) {
					personVO.getThePersonDT().setLastNm(selectedNameDT.getLastNm());
					personVO.getThePersonDT().setFirstNm(
							selectedNameDT.getFirstNm());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.preparePersonNameBeforePersistence: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The personRaceDT object for a given personUID and given raceCd
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param raceCd
	 *            the raceCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonRaceDT for a given personUID
	 * @throws EJBException
	 * @roseuid 3C02AFFD022D
	 * @J2EE_METHOD -- getPersonRace
	 */

	public PersonRaceDT getPersonRace(Long personUID, String raceCd,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personRaceDTs = new ArrayList<Object>();
			PersonRaceDT personRaceDT = null;

			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personRaceDTs = personVO.getThePersonRaceDTCollection();

			Iterator<Object> anIterator = null;
			if (personRaceDTs != null) {
				for (anIterator = personRaceDTs.iterator(); anIterator.hasNext();) {
					personRaceDT = (PersonRaceDT) anIterator.next();
					if ((raceCd != null)
							&& (personRaceDT.getRaceCd().compareTo(raceCd.trim()) == 0)) {
						logger.debug(" EntityControllerEJB.getPersonRace() got PersonRaceDT for raceCd = "
								+ raceCd);
						return personRaceDT;
					} else {
						continue;
					}
				}

			}

			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonRace: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Updates the personRaceDT Collection<Object> object
	 * 
	 * @param personRaces
	 *            the personRaceDT collection object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFD036D
	 * @J2EE_METHOD -- setPersonRaces
	 */
	public void setPersonRaces(Collection<Object> personRaces,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			PersonVO personVO = null;
			PersonRaceDT personRaceDT = null;
			Iterator<Object> anIterator = null;
			if (personRaces != null) {
				anIterator = personRaces.iterator();
				if (anIterator.hasNext()) {
					personRaceDT = (PersonRaceDT) anIterator.next();
					personVO = this.getPerson(personRaceDT.getPersonUid(),
							nbsSecurityObj);
					personVO.setThePersonRaceDTCollection(personRaces);
					personVO.setItNew(false);
					personVO.setItDirty(true);
					this.setPerson(personVO, nbsSecurityObj);
					logger.debug("EntityController.setPersonRaces() has been set for the personUid = "
							+ personRaceDT.getPersonUid());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPersonRaces: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the PersonEthnicGroupDT collection object
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonEthnicGroupDT collection object
	 * @throws EJBException
	 * @roseuid 3C02AFFE004D
	 * @J2EE_METHOD -- getPersonEthnicGroups
	 */
	public Collection<Object> getPersonEthnicGroups(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personEthnicGroupDTs = new ArrayList<Object>();
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personEthnicGroupDTs = personVO
						.getThePersonEthnicGroupDTCollection();

			return personEthnicGroupDTs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonEthnicGroups: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns the personEthnicGroupDT object for a given personUID.
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param ethnicGroupCd
	 *            the ethnicGroupCd(String ethnicGroupCd)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PersonEthnicGroupDT object
	 * @throws EJBException
	 * @roseuid 3C02AFFE0120
	 * @J2EE_METHOD -- getPersonEthnicGroup
	 */
	public PersonEthnicGroupDT getPersonEthnicGroup(Long personUID,
			String ethnicGroupCd, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personEthnicGroupDTs = new ArrayList<Object>();
			PersonEthnicGroupDT personEthnicGroupDT = null;

			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personEthnicGroupDTs = personVO
						.getThePersonEthnicGroupDTCollection();

			Iterator<Object> anIterator = null;
			if (personEthnicGroupDTs != null) {
				for (anIterator = personEthnicGroupDTs.iterator(); anIterator
						.hasNext();) {
					personEthnicGroupDT = (PersonEthnicGroupDT) anIterator.next();
					if ((ethnicGroupCd != null)
							&& (personEthnicGroupDT.getEthnicGroupCd().compareTo(
									ethnicGroupCd) == 0)) {
						logger.debug(" EntityControllerEJB.getPersonEthnicGroup() got PersonEthnicGroupDT for ethnicGroupCd = "
								+ ethnicGroupCd);
						return personEthnicGroupDT;
					} else {
						continue;
					}
				}

			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonEthnicGroup: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the personEthnicGroupDT collection object
	 * 
	 * @param personEthnicGroups
	 *            the personEthnicGroupDT Collection<Object> object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFE0260
	 * @J2EE_METHOD -- setPersonEthnicGroups
	 */
	public void setPersonEthnicGroups(Collection<Object> personEthnicGroups,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			PersonVO personVO = null;
			PersonEthnicGroupDT personEthnicGroupDT = null;
			Iterator<Object> anIterator = null;
			if (personEthnicGroups != null) {
				anIterator = personEthnicGroups.iterator();
				if (anIterator.hasNext()) {
					personEthnicGroupDT = (PersonEthnicGroupDT) anIterator.next();
					personVO = this.getPerson(personEthnicGroupDT.getPersonUid(),
							nbsSecurityObj);
					personVO.setThePersonEthnicGroupDTCollection(personEthnicGroups);
					personVO.setItNew(false);
					personVO.setItDirty(true);
					this.setPerson(personVO, nbsSecurityObj);
					logger.debug("EntityController.setPersonEthnicGroups() has been set for the personUid = "
							+ personEthnicGroupDT.getPersonUid());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPersonEthnicGroups: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns the personID collection object associated with a given personUID
	 * 
	 * @param personUID
	 *            the person UID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of personID's
	 * @throws EJBException
	 * @roseuid 3C02AFFE031E
	 * @J2EE_METHOD -- getPersonIDs
	 */
	public Collection<Object> getPersonIDs(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> personIDs = new ArrayList<Object>();
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				personIDs = personVO.getTheEntityIdDTCollection();

			return personIDs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPerson: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the personID collection object associated with a given personUID
	 * and typeCd
	 * 
	 * @param personUID
	 *            the person UID(Long value)
	 * @param typeCd
	 *            the typeCd(String typeCd)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> object representing personID's
	 * @throws EJBException
	 * @roseuid 3C02AFFE03DD
	 * @J2EE_METHOD -- getPersonIDs
	 */
	public Collection<Object> getPersonIDs(Long personUID, String typeCd,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> entityIdDTs = new ArrayList<Object>();
			Collection<Object> newEntityIDs = new ArrayList<Object>();
			EntityIdDT entityIdDT = null;

			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				entityIdDTs = personVO.getTheEntityIdDTCollection();

			if (entityIdDTs != null) {
				Iterator<Object> anIterator = null;
				for (anIterator = entityIdDTs.iterator(); anIterator.hasNext();) {
					entityIdDT = (EntityIdDT) anIterator.next();
					if ((entityIdDT != null)
							&& ((entityIdDT.getTypeCd()).compareTo(typeCd) == 0)) {
						logger.debug(" EntityControllerEJB.getPersonIDs Entity_ID for typeCd: "
								+ entityIdDT.getTypeCd());
						newEntityIDs.add(entityIdDT);
					} else {
						continue;
					}

				}

				return newEntityIDs;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Updates the personIDs Collection<Object> object
	 * 
	 * @param personIDs
	 *            the personIDs object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFF0117
	 * @J2EE_METHOD -- setPersonIDs
	 */
	public void setPersonIDs(Collection<Object> personIDs,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			PersonVO personVO = null;
			PersonDT personDT = null;
			Long personUid = null;
			EntityIdDT entityIdDT = null;
			Iterator<Object> anIterator = null;
			if (personIDs != null) {
				anIterator = personIDs.iterator();
				if (anIterator.hasNext()) {
					entityIdDT = (EntityIdDT) anIterator.next();
					if (entityIdDT.getEntityUid() != null)
						personVO = getPerson(entityIdDT.getEntityUid(), null);
					if (personVO != null)
						personVO.setTheEntityIdDTCollection(personIDs);
					personVO.setItNew(false);
					personVO.setItDirty(true);
					this.setPerson(personVO, nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPersonIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the PersonLocator collection object for a given personUID
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of personLocators
	 * @throws EJBException
	 * @roseuid 3C02AFFF01DF
	 * @J2EE_METHOD -- getAllPersonLocators
	 */
	public Collection<Object> getAllPersonLocators(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				entityLocatorParticipationDTs = personVO
						.getTheEntityLocatorParticipationDTCollection();

			return entityLocatorParticipationDTs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getAllPersonLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the PersonLocator collection object
	 * 
	 * @param entityLocatorParticipationDTCollection
	 *            the entityLocatorParticipationDTCollection object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02AFFF02A8
	 * @J2EE_METHOD -- setAllPersonLocators
	 */
	public void setAllPersonLocators(
			Collection<Object> entityLocatorParticipationDTCollection,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			PersonVO personVO = null;
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;

			if (entityLocatorParticipationDTCollection != null) {
				Iterator<Object> anIterator = entityLocatorParticipationDTCollection
						.iterator();
				if (anIterator.hasNext()) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					if (entityLocatorParticipationDT.getEntityUid() != null)
						personVO = getPerson(
								entityLocatorParticipationDT.getEntityUid(),
								nbsSecurityObj);
					if (personVO != null)
						personVO.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);
					personVO.setItNew(false);
					this.setPerson(personVO, nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setAllPersonLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns the personPhysicalLocator Collection<Object> object for a given
	 * personUID
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of personPhysicalLocators
	 * @throws EJBException
	 * @roseuid 3C02AFFF0370
	 * @J2EE_METHOD -- getPersonPhysicalLocators
	 */
	public Collection<Object> getPersonPhysicalLocators(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> physicalLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				entityLocatorParticipationDTs = personVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					physicalLocators.add(entityLocatorParticipationDT
							.getThePhysicalLocatorDT());
				}
			}
			return physicalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonPhysicalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the PersonPostalLocators Collection<Object> object for a given
	 * personUID
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of PersonPostalLocators
	 * @throws EJBException
	 * @roseuid 3C02B0000118
	 * @J2EE_METHOD -- getPersonPostalLocators
	 */
	public Collection<Object> getPersonPostalLocators(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> postalLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				entityLocatorParticipationDTs = personVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					postalLocators.add(entityLocatorParticipationDT
							.getThePostalLocatorDT());
				}
			}
			return postalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPerson: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the PersonTeleLocators Collection<Object> object for a given
	 * personUID
	 * 
	 * @param personUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of PersonTeleLocators
	 * @throws EJBException
	 * @roseuid 3C02B00002C7
	 * @J2EE_METHOD -- getPersonTeleLocators
	 */
	public Collection<Object> getPersonTeleLocators(Long personUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			PersonVO personVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> teleLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (personUID != null)
				personVO = getPerson(personUID, nbsSecurityObj);
			if (personVO != null)
				entityLocatorParticipationDTs = personVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					teleLocators.add(entityLocatorParticipationDT
							.getTheTeleLocatorDT());
				}
			}
			return teleLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPersonTeleLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the NonPersonLivingSubjectVO object for a given
	 * nonPersonLivingSubjectUID
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return NonPersonLivingSubjectVO for a given nonPersonLivingSubjectUID
	 * @throws EJBException
	 * @roseuid 3C02B0010098
	 * @J2EE_METHOD -- getNonPersonLivingSubject
	 */

	public NonPersonLivingSubjectVO getNonPersonLivingSubject(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {

		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;

			try {
				NonPersonLivingSubject nonPersonLivingSubject = null;
				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils
						.lookupBean(JNDINames.NONPERSONLIVINGSUBJECTEJB);
				logger.debug("Ent Controller lookup = " + obj.toString());
				NonPersonLivingSubjectHome home = (NonPersonLivingSubjectHome) PortableRemoteObject
						.narrow(obj, NonPersonLivingSubjectHome.class);
				if (nonPersonLivingSubjectUID != null)
					nonPersonLivingSubject = home
							.findByPrimaryKey(nonPersonLivingSubjectUID);
				if (nonPersonLivingSubject != null)
					nonPersonLivingSubjectVO = nonPersonLivingSubject
							.getNonPersonLivingSubjectVO();

				logger.debug("Ent Controller past the find - nonPersonLivingSubject = "
						+ nonPersonLivingSubject.toString());
				logger.debug("Ent Controllerpast the find - nonPersonLivingSubjectVO.getPrimaryKey = "
						+ nonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT()
								.getNonPersonUid());

			} catch (Exception e) {
				logger.fatal(
						"EntController.getNonPersonLivingSubject exception e = "
								+ e + "\n", e);

				throw new javax.ejb.EJBException(e.toString());
			}
			return nonPersonLivingSubjectVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubject: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Creates(if nonPersonLivingSubjectVO.isItNew() is true) or updates(if
	 * nonPersonLivingSubjectVO.isItDirty() is true) nonPersonLivingSubjectVO
	 * objects
	 * 
	 * @param nonPersonLivingSubjectVO
	 *            the nonPersonLivingSubjectVO object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Long value representing theNonPersonLivingSubjectUID
	 *         created/updated in the process.
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B0010188
	 * @J2EE_METHOD -- setNonPersonLivingSubject
	 */
	public Long setNonPersonLivingSubject(
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		Long nonPersonLivingSubjectUID = new Long(-1);
		try {
			NonPersonLivingSubject nonPersonLivingSubject = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils
					.lookupBean(JNDINames.NONPERSONLIVINGSUBJECTEJB);
			logger.debug("NonPersonLivingSubjectEJB lookup = " + obj.toString());
			NonPersonLivingSubjectHome home = (NonPersonLivingSubjectHome) PortableRemoteObject
					.narrow(obj, NonPersonLivingSubjectHome.class);
			logger.debug("Found NonPersonLivingSubjectHome: " + home);

			Collection<Object> elpDTCol = nonPersonLivingSubjectVO
					.getTheEntityLocatorParticipationDTCollection();
			Collection<Object> rDTCol = nonPersonLivingSubjectVO
					.getTheRoleDTCollection();
			Collection<Object> pDTCol = nonPersonLivingSubjectVO
					.getTheParticipationDTCollection();
			Collection<Object> col = null;
			if (elpDTCol != null) {
				col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
				nonPersonLivingSubjectVO
						.setTheEntityLocatorParticipationDTCollection(col);
			}
			if (rDTCol != null) {
				col = this.iterateRDT(rDTCol, nbsSecurityObj);
				nonPersonLivingSubjectVO.setTheRoleDTCollection(col);
			}
			if (pDTCol != null) {
				col = this.iteratePDT(pDTCol, nbsSecurityObj);
				nonPersonLivingSubjectVO.setTheParticipationDTCollection(col);
			}

			if (nonPersonLivingSubjectVO.isItNew()) {
				nonPersonLivingSubject = home.create(nonPersonLivingSubjectVO);
				nonPersonLivingSubjectUID = nonPersonLivingSubject
						.getNonPersonLivingSubjectVO()
						.getTheNonPersonLivingSubjectDT().getNonPersonUid();
				logger.debug(" EntityControllerEJB.setNonPersonLivingSubject() NonPersonLivingSubject Created and UID: "
						+ nonPersonLivingSubjectUID);
			} else {
				nonPersonLivingSubject = home
						.findByPrimaryKey(nonPersonLivingSubjectVO
								.getTheNonPersonLivingSubjectDT()
								.getNonPersonUid());
				nonPersonLivingSubject
						.setNonPersonLivingSubjectVO(nonPersonLivingSubjectVO);
				nonPersonLivingSubjectUID = nonPersonLivingSubject
						.getNonPersonLivingSubjectVO()
						.getTheNonPersonLivingSubjectDT().getNonPersonUid();
				logger.debug(" EntityControllerEJB.setNonPersonLivingSubject() NonPersonLivingSubject Updated for UID: "
						+ nonPersonLivingSubjectUID.longValue());
			}
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("EntityControllerEJB.setNonPersonLivingSubject: NEDSSConcurrentDataException: concurrent access is not allowed " + ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.setNonPersonLivingSubject: NEDSSConcurrentDataException: " + e.getMessage(), e);
				throw new NEDSSConcurrentDataException(e.getMessage(), e);
			} else {
				logger.fatal("EntityControllerEJB.setNonPersonLivingSubject: EJBException: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}
		return nonPersonLivingSubjectUID;
	}

	/**
	 * Returns the NonPersonLivingSubjectDT object for a given
	 * nonPersonLivingSubjectUID
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return NonPersonLivingSubjectDT for a given nonPersonLivingSubjectUID
	 * @throws EJBException
	 * @roseuid 3C02B001026E
	 * @J2EE_METHOD -- getNonPersonLivingSubjectInfo
	 */
	public NonPersonLivingSubjectDT getNonPersonLivingSubjectInfo(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			NonPersonLivingSubjectDT nonPersonLivingSubjectDT = null;
			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				nonPersonLivingSubjectDT = nonPersonLivingSubjectVO
						.getTheNonPersonLivingSubjectDT();
			logger.debug("EntityController.getNonPersonLivingSubjectInfo(): NonPersonLivingSubjectDT = "
					+ nonPersonLivingSubjectDT);
			return nonPersonLivingSubjectDT;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubjectInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Updates the nonPersonLivingSubjectDT
	 * 
	 * @param nonPersonLivingSubjectDT
	 *            the nonPersonLivingSubjectDT object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B001035F
	 * @J2EE_METHOD -- setNonPersonLivingSubjectInfo
	 */

	public void setNonPersonLivingSubjectInfo(
			NonPersonLivingSubjectDT nonPersonLivingSubjectDT,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			if (nonPersonLivingSubjectDT.getNonPersonUid() != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectDT.getNonPersonUid(), nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				nonPersonLivingSubjectVO
						.setTheNonPersonLivingSubjectDT(nonPersonLivingSubjectDT);
			nonPersonLivingSubjectVO.setItNew(false);
			nonPersonLivingSubjectVO.setItDirty(true);
			this.setNonPersonLivingSubject(nonPersonLivingSubjectVO, nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setNonPersonLivingSubjectInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns The getNonPersonLivingSubjectIDs Collection<Object> object for a
	 * given nonPersonLivingSubjectUID
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of getNonPersonLivingSubjectIDs for a given
	 *         nonPersonLivingSubjectUID
	 * @throws EJBException
	 * @roseuid 3C02B0020071
	 * @J2EE_METHOD -- getNonPersonLivingSubjectIDs
	 */
	public Collection<Object> getNonPersonLivingSubjectIDs(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			Collection<Object> nonPersonLivingSubjectIDs = new ArrayList<Object>();
			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				nonPersonLivingSubjectIDs = nonPersonLivingSubjectVO
						.getTheEntityIdDTCollection();

			return nonPersonLivingSubjectIDs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubjectIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The getNonPersonLivingSubjectIDs Collection<Object> object for a
	 * given nonPersonLivingSubjectUID and given typeCd
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param typeCd
	 *            the typeCd (String typeCd)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of getNonPersonLivingSubjectIDs for a given
	 *         nonPersonLivingSubjectUID
	 * @throws EJBException
	 * @roseuid 3C02B002016B
	 * @J2EE_METHOD -- getNonPersonLivingSubjectIDs
	 */
	public Collection<Object> getNonPersonLivingSubjectIDs(
			Long nonPersonLivingSubjectUID, String typeCd,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			Collection<Object> entityIdDTs = new ArrayList<Object>();
			Collection<Object> newEntityIDs = new ArrayList<Object>();
			EntityIdDT entityIdDT = null;

			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				entityIdDTs = nonPersonLivingSubjectVO.getTheEntityIdDTCollection();

			if (entityIdDTs != null) {
				Iterator<Object> anIterator = null;
				for (anIterator = entityIdDTs.iterator(); anIterator.hasNext();) {
					entityIdDT = (EntityIdDT) anIterator.next();
					if ((entityIdDT != null)
							&& ((entityIdDT.getTypeCd()).compareTo(typeCd) == 0)) {
						newEntityIDs.add(entityIdDT);
					} else {
						continue;
					}
				}
				return newEntityIDs;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubjectIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the existing nonPersonLivingSubjectIDs collection
	 * 
	 * @param nonPersonLivingSubjectIDs
	 *            the collection of nonPersonLivingSubjectIDs
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B00202FC
	 * @J2EE_METHOD -- setNonPersonLivingSubjectIDs
	 */
	public void setNonPersonLivingSubjectIDs(
			Collection<Object> nonPersonLivingSubjectIDs,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			EntityIdDT entityIdDT = null;
			Iterator<Object> anIterator = null;
			if (nonPersonLivingSubjectIDs != null) {
				anIterator = nonPersonLivingSubjectIDs.iterator();
				if (anIterator.hasNext()) {
					entityIdDT = (EntityIdDT) anIterator.next();
					if (entityIdDT.getEntityUid() != null)
						nonPersonLivingSubjectVO = getNonPersonLivingSubject(
								entityIdDT.getEntityUid(), null);
					if (nonPersonLivingSubjectVO != null)
						nonPersonLivingSubjectVO
								.setTheEntityIdDTCollection(nonPersonLivingSubjectIDs);
					nonPersonLivingSubjectVO.setItNew(false);
					nonPersonLivingSubjectVO.setItDirty(true);
					this.setNonPersonLivingSubject(nonPersonLivingSubjectVO,
							nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setNonPersonLivingSubjectIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns The collection of NonPersonLivingSubjectLocators
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return NonPersonLivingSubjectLocators
	 * @throws EJBException
	 * @roseuid 3C02B0030018
	 * @J2EE_METHOD -- getAllNonPersonLivingSubjectLocators
	 */
	public Collection<Object> getAllNonPersonLivingSubjectLocators(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				entityLocatorParticipationDTs = nonPersonLivingSubjectVO
						.getTheEntityLocatorParticipationDTCollection();

			return entityLocatorParticipationDTs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getAllNonPersonLivingSubjectLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the nonPersonLivingSubjectLocators
	 * 
	 * @param entityLocatorParticipationDTCollection
	 *            the entityLocatorParticipationDT's collection object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B0030127
	 * @J2EE_METHOD -- setAllNonPersonLivingSubjectLocators
	 */
	public void setAllNonPersonLivingSubjectLocators(
			Collection<Object> entityLocatorParticipationDTCollection,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;

			if (entityLocatorParticipationDTCollection != null) {
				Iterator<Object> anIterator = entityLocatorParticipationDTCollection
						.iterator();
				if (anIterator.hasNext()) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					if (entityLocatorParticipationDT.getEntityUid() != null)
						nonPersonLivingSubjectVO = getNonPersonLivingSubject(
								entityLocatorParticipationDT.getEntityUid(),
								nbsSecurityObj);
					if (nonPersonLivingSubjectVO != null)
						nonPersonLivingSubjectVO
								.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);
					nonPersonLivingSubjectVO.setItNew(false);
					this.setNonPersonLivingSubject(nonPersonLivingSubjectVO,
							nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setAllNonPersonLivingSubjectLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Returns The nonPersonLivingSubjectPhysicalLocators object for a given
	 * nonPersonLivingSubjectUID
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return nonPersonLivingSubjectPhysicalLocators
	 * @throws EJBException
	 * @roseuid 3C02B003023F
	 * @J2EE_METHOD -- getNonPersonLivingSubjectPhysicalLocators
	 */
	public Collection<Object> getNonPersonLivingSubjectPhysicalLocators(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> physicalLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				entityLocatorParticipationDTs = nonPersonLivingSubjectVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					physicalLocators.add(entityLocatorParticipationDT
							.getThePhysicalLocatorDT());
				}
			}
			return physicalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubjectPhysicalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The NonPersonLivingSubjectPostalLocators object for a given
	 * nonPersonLivingSubjectUID
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of nonPersonLivingSubjectPostalLocators
	 * @throws EJBException
	 * @roseuid 3C02B0040088
	 * @J2EE_METHOD -- getNonPersonLivingSubjectPostalLocators
	 */
	public Collection<Object> getNonPersonLivingSubjectPostalLocators(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> postalLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				entityLocatorParticipationDTs = nonPersonLivingSubjectVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					postalLocators.add(entityLocatorParticipationDT
							.getThePostalLocatorDT());
				}
			}
			return postalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubjectPostalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The nonPersonLivingSubjectTeleLocators object for a given
	 * nonPersonLivingSubjectUID
	 * 
	 * @param nonPersonLivingSubjectUID
	 *            the nonPersonLivingSubjectUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of PersonLivingSubjectTeleLocators for a given
	 *         nonPersonLivingSubjectUID
	 * @throws EJBException
	 * @roseuid 3C02B00402E1
	 * @J2EE_METHOD -- getNonPersonLivingSubjectTeleLocators
	 */

	public Collection<Object> getNonPersonLivingSubjectTeleLocators(
			Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> teleLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (nonPersonLivingSubjectUID != null)
				nonPersonLivingSubjectVO = getNonPersonLivingSubject(
						nonPersonLivingSubjectUID, nbsSecurityObj);
			if (nonPersonLivingSubjectVO != null)
				entityLocatorParticipationDTs = nonPersonLivingSubjectVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					teleLocators.add(entityLocatorParticipationDT
							.getTheTeleLocatorDT());
				}
			}
			return teleLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getNonPersonLivingSubjectTeleLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The OrganizationVO object for a given organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return OrganizationVO for a given organizationUID
	 * @throws EJBException
	 * @roseuid 3C02B0050166
	 * @J2EE_METHOD -- getOrganization
	 */
	public OrganizationVO getOrganization(Long organizationUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		logger.debug("EntController.getOrganization ------------------------------");
		OrganizationVO organizationVO = new OrganizationVO();

		try {
			Organization org = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.ORGANIZATIONEJB);
			logger.debug("EntController.getOrganization - obj = "
					+ obj.toString());
			OrganizationHome home = (OrganizationHome) PortableRemoteObject
					.narrow(obj, OrganizationHome.class);
			org = home.findByPrimaryKey(organizationUID);
			organizationVO = org.getOrganizationVO();

		} catch (Exception e) {
			logger.fatal("EntControllerEJB.getOrganization: " + e.getMessage(),e);
			throw new EJBException(e.getMessage(),e);
		}
		if (organizationVO.getTheOrganizationDT() != null
				&& (organizationVO.getTheOrganizationDT().getElectronicInd() != null && !organizationVO
						.getTheOrganizationDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
			// for LDFs
			ArrayList<Object> ldfList = new ArrayList<Object>();
			try {
				LDFHelper ldfHelper = LDFHelper.getInstance();
				ldfList = (ArrayList<Object>) ldfHelper.getLDFCollection(organizationUID, null, nbsSecurityObj);
			} catch (Exception e) {
				logger.fatal(
						"EntControllerEJB.getOrganization: Exception occured while retrieving LDFCollection<Object> "
								+ e.getMessage(),
						e);
				throw new EJBException(e.getMessage(), e);
			}

			if (ldfList != null) {
				logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
				organizationVO.setTheStateDefinedFieldDataDTCollection(ldfList);
			}
		}

		return organizationVO;
	}

	/**
	 * Creates(if organizationVO.isItNew() is true) or updates(if
	 * organizationVO.isItDirty() is true) the organizationVO
	 * 
	 * @param organizationVO
	 *            the organizationVO object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Long value representing the organizationUID created/updated in
	 *         the process.
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B005029C
	 * @J2EE_METHOD -- setOrganization
	 */
	public Long setOrganization(OrganizationVO organizationVO,
			NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException,
			javax.ejb.EJBException {
		Long ouid = new Long(-1);
		try {
			boolean callOrgHashCode= false;
			if(organizationVO.isItNew() && organizationVO.getTheOrganizationDT().isItNew() && organizationVO.getTheOrganizationDT().getElectronicInd().equalsIgnoreCase("Y")){
				callOrgHashCode= true;
				organizationVO.getTheOrganizationDT().setEdxInd("Y");
			}
			Organization organization = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.ORGANIZATIONEJB);
			logger.debug("EntityControllerEJB.setOrganization - lookup = "
					+ obj.toString());
			OrganizationHome home = (OrganizationHome) PortableRemoteObject
					.narrow(obj, OrganizationHome.class);
			logger.debug("EntityControllerEJB.setOrganization - Found OrganizationHome: "
					+ home);

			Collection<Object> elpDTCol = organizationVO
					.getTheEntityLocatorParticipationDTCollection();
			Collection<Object> rDTCol = organizationVO.getTheRoleDTCollection();
			Collection<Object> pDTCol = organizationVO
					.getTheParticipationDTCollection();
			Collection<Object> col = null;
			if (elpDTCol != null) {
				col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
				organizationVO
						.setTheEntityLocatorParticipationDTCollection(col);
			}
			if (rDTCol != null) {
				col = this.iterateRDT(rDTCol, nbsSecurityObj);
				organizationVO.setTheRoleDTCollection(col);
			}
			if (pDTCol != null) {
				col = this.iteratePDT(pDTCol, nbsSecurityObj);
				organizationVO.setTheParticipationDTCollection(col);
			}
			if (organizationVO.isItNew()) {
				organization = home.create(organizationVO);
				logger.debug(" EntityControllerEJB.setOrganization -  Organization Created");
				logger.debug("EntityControllerEJB.setOrganization - organization.getOrganizationVO().getTheOrganizationDT().getOrganizationUid() =  "
						+ organization.getOrganizationVO()
								.getTheOrganizationDT().getOrganizationUid());
				ouid = organization.getOrganizationVO().getTheOrganizationDT()
						.getOrganizationUid();
			} else {
				organization = home.findByPrimaryKey(organizationVO
						.getTheOrganizationDT().getOrganizationUid());
				organization.setOrganizationVO(organizationVO);
				logger.debug(" EntityControllerEJB.setOrganization -  Organization Updated");
				ouid = organization.getOrganizationVO().getTheOrganizationDT()
						.getOrganizationUid();
			}
			if(callOrgHashCode){
				organizationVO.getTheOrganizationDT().setOrganizationUid(ouid);
				EdxMatchingCriteriaUtil edxMatchingCriteriaUtil = new EdxMatchingCriteriaUtil();
				edxMatchingCriteriaUtil.setOrganizationtoEntityMatch(organizationVO, nbsSecurityObj);
			}
	
		} catch (NEDSSConcurrentDataException ex) {
			logger.error("EntityControllerEJB.setOrganization: NEDSSConcurrentDataException: concurrent access is not allowed" + ex.getMessage(),ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.error("EntityControllerEJB.setOrganization: NEDSSConcurrentDataException: " + e.getMessage(),e);
				throw new NEDSSConcurrentDataException(e.getMessage(),e);
			} else {
				logger.fatal("EntityControllerEJB.setOrganization: Exception: " + e.getMessage(),e);
				throw new EJBException(e.getMessage(),e);
			}
		}

		logger.debug("EntityControllerEJB.setOrganization - ouid  =  " + ouid);
		return ouid;
	}

	/**
	 * Sets the organization values in the databse based on the businessTrigger
	 * 
	 * @roseuid 3E6E4E05003E
	 * @J2EE_METHOD -- setOrganization
	 * @param organizationVO
	 *            the OrganizationVO
	 * @param businessTriggerCd
	 *            the String
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj
	 * @return organizationUID the Long
	 * @throws NEDSSConcurrentDataException
	 * @throws javax.ejb.EJBException
	 */
	public Long setOrganization(OrganizationVO organizationVO,
			String businessTriggerCd, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, NEDSSConcurrentDataException,
			javax.ejb.EJBException {
		Long organizationUID;
		try {
			organizationUID = setOrganizationInternal(organizationVO,
					NBSBOLookup.ORGANIZATION, businessTriggerCd, nbsSecurityObj);
		} catch (NEDSSConcurrentDataException ex) {
			logger.error("EntityControllerEJB.setOrganization: NEDSSConcurrentDataException: concurrent access is not allowed" + ex.getMessage(),ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.error("EntityControllerEJB.setOrganization: NEDSSConcurrentDataException: " + e.getMessage(),e);
				throw new NEDSSConcurrentDataException(e.getMessage(),e);
			} else {
				logger.fatal("EntityControllerEJB.setOrganization: Exception: " + e.getMessage(),e);
				throw new EJBException(e.getMessage(),e);
			}
		}

		return organizationUID;
	}

	/**
	 * Returns The OrganizationDT object for a given organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return OrganizationDT for a given organizationUID.
	 * @throws EJBException
	 * @roseuid 3C02B00503DD
	 * @J2EE_METHOD -- getOrganizationInfo
	 */
	public OrganizationDT getOrganizationInfo(Long organizationUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			OrganizationDT organizationDT = null;
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				organizationDT = organizationVO.getTheOrganizationDT();
			logger.debug("EntityController.getOrganizationInfo(): OrganizationDT = "
					+ organizationDT);
			return organizationDT;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the existing organizationDT object
	 * 
	 * @param organizationDT
	 *            the organizationDT object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B006013F
	 * @J2EE_METHOD -- setOrganizationInfo
	 */
	public void setOrganizationInfo(OrganizationDT organizationDT,
			NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException,
			javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			if (organizationDT.getOrganizationUid() != null)
				organizationVO = getOrganization(
						organizationDT.getOrganizationUid(), nbsSecurityObj);
			if (organizationVO != null)
				organizationVO.setTheOrganizationDT(organizationDT);
			organizationVO.setItNew(false);
			organizationVO.setItDirty(true);
			this.setOrganization(organizationVO, nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setOrganizationInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the OrganizationID collection object associated with a given
	 * organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> object representing the OrganizationID's
	 * @throws EJBException
	 * @roseuid 3C02B006028A
	 * @J2EE_METHOD -- getOrganizationIDs
	 */
	public Collection<Object> getOrganizationIDs(Long organizationUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> organizationIDs = new ArrayList<Object>();
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				organizationIDs = organizationVO.getTheEntityIdDTCollection();

			return organizationIDs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the OrganizationID collection object associated with a given
	 * organizationUID and a given typeCd
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param typeCd
	 *            the typeCd(String typeCd)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> object representing the OrganizationID's
	 * @throws EJBException
	 * @roseuid 3C02B00603D4
	 * @J2EE_METHOD -- getOrganizationIDs
	 */
	public Collection<Object> getOrganizationIDs(Long organizationUID,
			String typeCd, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> entityIdDTs = new ArrayList<Object>();
			Collection<Object> newEntityIDs = new ArrayList<Object>();
			EntityIdDT entityIdDT = null;

			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				entityIdDTs = organizationVO.getTheEntityIdDTCollection();

			if (entityIdDTs != null) {
				Iterator<Object> anIterator = null;
				for (anIterator = entityIdDTs.iterator(); anIterator.hasNext();) {
					entityIdDT = (EntityIdDT) anIterator.next();
					if ((entityIdDT != null)
							&& ((entityIdDT.getTypeCd()).compareTo(typeCd) == 0)) {
						newEntityIDs.add(entityIdDT);
					} else {
						continue;
					}
				}
				return newEntityIDs;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The OrganizationLocators Collection<Object> for a given
	 * organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> object representing OrganizationLocators
	 * @throws EJBException
	 * @roseuid 3C02B00701F5
	 * @J2EE_METHOD -- getAllOrganizationLocators
	 */
	public Collection<Object> getAllOrganizationLocators(Long organizationUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				entityLocatorParticipationDTs = organizationVO
						.getTheEntityLocatorParticipationDTCollection();

			return entityLocatorParticipationDTs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getAllOrganizationLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the OrganizationLocators(entityLocatorParticipationDT's)
	 * 
	 * @param entityLocatorParticipationDTCollection
	 *            the entityLocatorParticipationDTCollection(
	 *            entityLocatorParticipationDT Collection<Object>)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B0070353
	 * @J2EE_METHOD -- setAllOrganizationLocators
	 */
	public void setAllOrganizationLocators(
			Collection<Object> entityLocatorParticipationDTCollection,
			NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException,
			javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;

			if (entityLocatorParticipationDTCollection != null) {
				Iterator<Object> anIterator = entityLocatorParticipationDTCollection
						.iterator();
				if (anIterator.hasNext()) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					if (entityLocatorParticipationDT.getEntityUid() != null)
						organizationVO = getOrganization(
								entityLocatorParticipationDT.getEntityUid(),
								nbsSecurityObj);
					if (organizationVO != null)
						organizationVO
								.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);
					organizationVO.setItNew(false);
					this.setOrganization(organizationVO, nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setAllOrganizationLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the collection of OrganizationPhysicalLocators associated with a
	 * given organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of OrganizationPhysicalLocators
	 * @throws EJBException
	 * @roseuid 3C02B00800CA
	 * @J2EE_METHOD -- getOrganizationPhysicalLocators
	 */
	public Collection<Object> getOrganizationPhysicalLocators(
			Long organizationUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> physicalLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				entityLocatorParticipationDTs = organizationVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					physicalLocators.add(entityLocatorParticipationDT
							.getThePhysicalLocatorDT());
				}
			}

			return physicalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationPhysicalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the collection of OrganizationPostalLocators associated with a
	 * given organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of OrganizationPostalLocators
	 * @throws EJBException
	 * @roseuid 3C02B008039B
	 * @J2EE_METHOD -- getOrganizationPostalLocators
	 */
	public Collection<Object> getOrganizationPostalLocators(
			Long organizationUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> postalLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				entityLocatorParticipationDTs = organizationVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					postalLocators.add(entityLocatorParticipationDT
							.getThePostalLocatorDT());
				}
			}

			return postalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationPostalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the collection of OrganizationTeleLocators associated with a
	 * given organizationUID
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of OrganizationTeleLocators
	 * @throws EJBException
	 * @roseuid 3C02B0090298
	 * @J2EE_METHOD -- getOrganizationTeleLocators
	 */
	public Collection<Object> getOrganizationTeleLocators(Long organizationUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object>();
			Collection<Object> teleLocators = new ArrayList<Object>();
			EntityLocatorParticipationDT entityLocatorParticipationDT = null;
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				entityLocatorParticipationDTs = organizationVO
						.getTheEntityLocatorParticipationDTCollection();
			Iterator<Object> anIterator = null;
			if (entityLocatorParticipationDTs != null) {
				for (anIterator = entityLocatorParticipationDTs.iterator(); anIterator
						.hasNext();) {
					entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
							.next();
					teleLocators.add(entityLocatorParticipationDT
							.getTheTeleLocatorDT());
				}
			}

			return teleLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationTeleLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The materialDT object for a given materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return MaterialVO object.
	 * @throws EJBException
	 * @roseuid 3C02B00A01A9
	 * @J2EE_METHOD -- getMaterial
	 */

	public MaterialVO getMaterial(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {

		try {
			MaterialVO materialVO;

			// bypasses security if it's null
			// whoever who addes the security code should take into consideration
			// that whether it's needed to have the functionality of bypassing
			// security or not. If it's not the case the if (...) should be comment
			// out
			// to do the security check for all the accesses through this access.
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			try {
				
				Material material = null;
				Long lpk = materialUID;
				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.MATERIAL);
				logger.debug("Ent Controller lookup = " + obj.toString());
				logger.debug("HelloClient before the narrow");
				MaterialHome home = (MaterialHome) PortableRemoteObject.narrow(obj,
						MaterialHome.class);

				logger.debug("Found Ent Controller Home --------------- ");

				material = home.findByPrimaryKey(lpk);
				materialVO = material.getMaterialVO();

				logger.debug("Ent Controller past the find - material = "
						+ material.toString());
				logger.debug("Ent Controllerpast the find - material.getPrimaryKey = "
						+ material.getPrimaryKey());
				logger.debug("Ent Controller Done find by primary key!");
			} catch (Exception e) {
				logger.fatal("getMaterial exception e = " + e + "\n", e);
				throw new EJBException(e.toString());
			}
			return materialVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterial: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Convenient method to create or update the materialVO collection object(
	 * instead of creating/updating one by one using setMaterial method)
	 * 
	 * @param materialVOCollection
	 *            the materialVO's Collection<Object>
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of Long value representing the materialUID's
	 *         created/updated in the process.
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 */

	public Collection<Object> setMaterials(
			Collection<Object> materialVOCollection,
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			javax.ejb.EJBException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		try {
			ArrayList<Object> materialUidCollection = new ArrayList<Object>();

			Iterator<Object> itr = materialVOCollection.iterator();
			try {
				while (itr.hasNext()) {
					MaterialVO materialVO = (MaterialVO) itr.next();
					Long materialUid = setMaterial(materialVO, nbsSecurityObj);
					materialUidCollection.add(materialUid);
				}
			} catch (Exception e) {
				logger.error("Error in setMaterials " + e);
				e.printStackTrace();
				throw new NEDSSSystemException(e.toString());
			}
			return materialUidCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setMaterials: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * Creates(if materialVO.isItNew() is true) or updates(if
	 * materialVO.isItDirty() is true) the materialVO for a given materialUID.
	 * 
	 * @param materialVO
	 *            the materialVO object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Long value representing the materialVO object created/updated in
	 *         the process.
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B00A0330
	 * @J2EE_METHOD -- setMaterial
	 */
	public Long setMaterial(MaterialVO materialVO, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSConcurrentDataException {
		try {
			Long returnValue;

			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			try {
				Material material = null;
				MaterialDT materialDT = materialVO.getTheMaterialDT();

				Long lpk = materialDT.getMaterialUid();
				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.MATERIAL);
				logger.debug("Ent Controller lookup = " + obj.toString());
				logger.debug("HelloClient before the narrow");
				MaterialHome home = (MaterialHome) PortableRemoteObject.narrow(obj,
						MaterialHome.class);

				logger.debug("Found Ent Controller Home --------------- ");

				Collection<Object> elpDTCol = materialVO
						.getTheEntityLocatorParticipationDTCollection();
				Collection<Object> rDTCol = materialVO.getTheRoleDTCollection();
				Collection<Object> pDTCol = materialVO
						.getTheParticipationDTCollection();
				Collection<Object> col = null;
				if (elpDTCol != null) {
					col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
					materialVO.setTheEntityLocatorParticipationDTCollection(col);
				}
				if (rDTCol != null) {
					col = this.iterateRDT(rDTCol, nbsSecurityObj);
					materialVO.setTheRoleDTCollection(col);
				}
				if (pDTCol != null) {
					col = this.iteratePDT(pDTCol, nbsSecurityObj);
					materialVO.setTheParticipationDTCollection(col);
				}

				if (materialVO.isItNew()) {
					logger.debug(" EntityControllerEJB.setMaterial() isItNew :"
							+ materialVO.isItNew());
					material = home.create(materialVO);
					logger.debug(" EntityControllerEJB.setMaterial() Material Created");
				} else {
					logger.debug(" EntityControllerEJB.setMaterial() isItNew :"
							+ materialVO.isItNew());
					material = home.findByPrimaryKey(materialVO.getTheMaterialDT()
							.getMaterialUid());
					try {
						material.setMaterialVO(materialVO);
						logger.debug(" EntityControllerEJB.setMaterial() Material Updated");
					} catch (NEDSSConcurrentDataException ndcex) {
						logger.debug("got the NEDSSConcurrentDataException in entityControllerEJB");
						throw new NEDSSConcurrentDataException(
								"Exception thrown from entityControllerEJB");
					}
				}
				returnValue = material.getMaterialVO().getTheMaterialDT()
						.getMaterialUid();

				logger.debug("Ent Controller past the find - material = "
						+ material.toString());
				logger.debug("Ent Controllerpast the find - material.getPrimaryKey = "
						+ material.getPrimaryKey());
				logger.debug("Ent Controller Done find by primary key!");

			} catch (NEDSSConcurrentDataException ex) {
				logger.fatal("The entity cannot be updated as concurrent access is not allowed!");
				logger.error("Throwing NEDSSConcurrentDataException");
				throw new NEDSSConcurrentDataException(
						"Concurrent access occurred in EntityControllerEJB/ setMaterialEJB : "
								+ ex.toString());
			} catch (Exception e) {
				if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
					logger.error("Throwing NEDSSConcurrentDataException");
					throw new NEDSSConcurrentDataException(e.toString());
				} else {
					logger.fatal("setMaterial failed" + e + "\n", e);
					throw new EJBException(e.toString());
				}
			}
			return returnValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setMaterial: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The materialDT object for a given materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return materialDT
	 * @throws EJBException
	 * @roseuid 3C02B00B00CE
	 * @J2EE_METHOD -- getMaterialInfo
	 */
	public MaterialDT getMaterialInfo(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getMaterial(materialUID, null).getTheMaterialDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterialInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates a given materialDT object.
	 * 
	 * @param materialDT
	 *            the materialDT object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B00B025F
	 * @J2EE_METHOD -- setMaterialInfo
	 */

	public void setMaterialInfo(MaterialDT materialDT,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			if (materialDT == null) {
				logger.debug("Warning: Material is called with a null MaterialDT");
				return;
			}

			MaterialVO materialVO = getMaterial(materialDT.getMaterialUid(), null);
			materialVO.setTheMaterialDT(materialDT);
			setMaterial(materialVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setMaterialInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the collection of MaterialLocators object for a given materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> of materialLocators.
	 * @throws EJBException
	 * @roseuid 3C02B00C0011
	 * @J2EE_METHOD -- getAllMaterialLocators
	 */
	public Collection<Object> getAllMaterialLocators(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getMaterial(materialUID, null)
					.getTheEntityLocatorParticipationDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getAllMaterialLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the MaterialLocators(entityLocatorParticipationDT
	 * Collection<Object>) object
	 * 
	 * @param entityLocatorParticipationDTCollection
	 *            the entityLocatorParticipationDT Collection<Object>
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B00C01AC
	 * @J2EE_METHOD -- setAllMaterialLocators
	 */
	public void setAllMaterialLocators(
			Collection<Object> entityLocatorParticipationDTCollection,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code
				// throws proper exception if it's needed
			}

			Collection<Object> materialLocatorsCollection = entityLocatorParticipationDTCollection;
			if (materialLocatorsCollection == null) {
				logger.debug("Warning: Material is called with a null Material Entity Locators Collection<Object>");
				return;
			}

			if (materialLocatorsCollection.isEmpty()) {
				logger.debug("Warning: Material Entity Locators Collection<Object> is Empty");
				return;
			}

			// Getting the material UID
			MaterialVO materialVO = getMaterial(
					((EntityLocatorParticipationDT) (materialLocatorsCollection.iterator().next()))
							.getEntityUid(), null);
			// Doing the ncessary changes
			materialVO
					.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);
			// writing it back.

			setMaterial(materialVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setAllMaterialLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns materialPhysicalLocators for a given materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return materialPhysicalLocators Collection<Object> object
	 * @throws EJBException
	 * @roseuid 3C02B00C0347
	 * @J2EE_METHOD -- getMaterialPhysicalLocators
	 */
	public Collection<Object> getMaterialPhysicalLocators(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> materialPhysicalLocators = new ArrayList<Object>();
			Collection<Object> materialLocatorsCollection = getAllMaterialLocators(
					materialUID, null);

			if ((materialLocatorsCollection == null)
					|| (materialLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = materialLocatorsCollection.iterator();
			// go through all the items, and if they have a Physical Locator add it
			// the ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				PhysicalLocatorDT physicalLocatorDT = entityLocatorParticipationDT
						.getThePhysicalLocatorDT();

				if (physicalLocatorDT != null)
					materialPhysicalLocators.add(physicalLocatorDT);
			} while (iterator.hasNext());

			return materialPhysicalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterialPhysicalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns materialPostalLocators for a given materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return materialPostalLocators Collection<Object> object
	 * @throws EJBException
	 * @roseuid 3C02B00D02B2
	 * @J2EE_METHOD -- getMaterialPostalLocators
	 */
	public Collection<Object> getMaterialPostalLocators(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> materialPostalLocators = new ArrayList<Object>();
			Collection<Object> materialLocatorsCollection = getAllMaterialLocators(
					materialUID, null);

			if ((materialLocatorsCollection == null)
					|| (materialLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = materialLocatorsCollection.iterator();
			// go through all the items, and if they have a Postal Locator add it
			// the ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				PostalLocatorDT postalLocatorDT = entityLocatorParticipationDT
						.getThePostalLocatorDT();

				if (postalLocatorDT != null)
					materialPostalLocators.add(postalLocatorDT);
			} while (iterator.hasNext());

			return materialPostalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterialPostalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns materialTeleLocators for a given materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return materialTeleLocators Collection<Object> object
	 * @throws EJBException
	 * @roseuid 3C02B00E0231
	 * @J2EE_METHOD -- getMaterialTeleLocators
	 */
	public Collection<Object> getMaterialTeleLocators(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> materialTeleLocators = new ArrayList<Object>();
			Collection<Object> materialLocatorsCollection = getAllMaterialLocators(
					materialUID, null);

			if ((materialLocatorsCollection == null)
					|| (materialLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = materialLocatorsCollection.iterator();
			// go through all the items, and if they have a Tele Locator add it the
			// ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				TeleLocatorDT teleLocatorDT = entityLocatorParticipationDT
						.getTheTeleLocatorDT();

				if (teleLocatorDT != null)
					materialTeleLocators.add(teleLocatorDT);
			} while (iterator.hasNext());

			return materialTeleLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterialTeleLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the MaterialID collection object associated with a given
	 * materialUID
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> object representing the MaterialID's
	 * @throws EJBException
	 * @roseuid 3C02B00F01BA
	 * @J2EE_METHOD -- getMaterialIDs
	 */
	public Collection<Object> getMaterialIDs(Long materialUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getMaterial(materialUID, null).getTheEntityIdDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterialIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the MaterialID collection object associated with a given
	 * materialUID and a given typeCd
	 * 
	 * @param materialUID
	 *            the materialUID(Long value)
	 * @param typeCd
	 *            the typeCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Collection<Object> object representing the MaterialID's
	 * @throws EJBException
	 * @roseuid 3C02B00F037D
	 * @J2EE_METHOD -- getMaterialIDs
	 */
	public Collection<Object> getMaterialIDs(Long materialUID, String typeCd,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> materialEntityIdDTCollection = getMaterial(
					materialUID, null).getTheEntityIdDTCollection();
			Collection<Object> filteredEntityIdDTCollection = new ArrayList<Object>();
			Iterator<Object> iterator = materialEntityIdDTCollection.iterator();
			Object currentElement;

			while ((currentElement = iterator.next()) != null)
				if (((EntityIdDT) currentElement).getTypeCd().compareTo(typeCd) == 0)
					filteredEntityIdDTCollection.add(currentElement);

			return filteredEntityIdDTCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMaterialIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates collection of materialIDs
	 * 
	 * @param materialIDs
	 *            the materialIDs collection object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B010025C
	 * @J2EE_METHOD -- setMaterialIDs
	 */
	public void setMaterialIDs(Collection<Object> materialIDs,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			if (materialIDs == null) {
				logger.debug("Warning: Material is called with a null Meterial Entity Collection<Object>");
				return;
			}
			if (materialIDs.isEmpty()) {
				logger.debug("Warning: Material is called with an empty Meterial Entity Collection<Object>");
				return;
			}
			Iterator<Object> interator = materialIDs.iterator();

			EntityIdDT entityIdDT = (EntityIdDT) interator.next();
			Long materialUID = entityIdDT.getEntityUid();

			if (materialUID == null) {
				logger.debug("Warning: Material is called with a null Enitty UID");
				return;
			}
			// the main thing functionality is done here.
			// getting the materialVO
			MaterialVO materialVO = getMaterial(materialUID, null);
			// doing the modifictaion
			materialVO.setTheEntityIdDTCollection(materialIDs);
			// putting it back
			setMaterial(materialVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPerson: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The placeVO object for a given placeUID
	 * 
	 * @param placeUID
	 *            the placeUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PlaceVO for a given placeUID
	 * @throws EJBException
	 * @roseuid 3C02B0110041
	 * @J2EE_METHOD -- getPlace
	 */
	public PlaceVO getPlace(Long placeUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {

		try {
			PlaceVO placeVO;

			// bypasses security if it's null
			// whoever who addes the security code should take into consideration
			// that whether it's needed to have the functionality of bypassing
			// security or not. If it's not the case the if (...) should be comment
			// out
			// to do the security check for all the accesses through this access.
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			try {
				
				Place place = null;
				Long lpk = placeUID;
				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.PLACE);
				logger.debug("Ent Controller lookup = " + obj.toString());
				logger.debug("HelloClient before the narrow");
				PlaceHome home = (PlaceHome) PortableRemoteObject.narrow(obj,
						PlaceHome.class);

				logger.debug("Found Ent Controller Home --------------- ");

				place = home.findByPrimaryKey(lpk);
				placeVO = place.getPlaceVO();

				logger.debug("Ent Controller past the find - place = "
						+ place.toString());
				logger.debug("Ent Controllerpast the find - place.getPrimaryKey = "
						+ place.getPrimaryKey());
				logger.debug("Ent Controller Done find by primary key!");

			} catch (Exception e) {
				logger.fatal("EntityControllerEJB.getPlace: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);

			}

			return placeVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlace: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Creates(in case placeVO.isItNew() is true) or Updates(in case
	 * placeVO.isItDirty() is true) the placeVO
	 * 
	 * @param placeVO
	 *            the placeVO object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Long value representing the placeUID created/updated in the
	 *         process.
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B0110221
	 * @J2EE_METHOD -- setPlace
	 */
	public Long setPlace(PlaceVO placeVO, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSConcurrentDataException {
		try {
			Long returnValue;

			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			try {
				Place place = null;
				PlaceDT placeDT = placeVO.getThePlaceDT();

				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.PLACE);
				logger.debug("Ent Controller lookup = " + obj.toString());
				logger.debug("HelloClient before the narrow");
				PlaceHome home = (PlaceHome) PortableRemoteObject.narrow(obj,
						PlaceHome.class);

				logger.debug("Found Ent Controller Home --------------- ");

				Collection<Object> elpDTCol = placeVO
						.getTheEntityLocatorParticipationDTCollection();
				Collection<Object> rDTCol = placeVO.getTheRoleDTCollection();
				Collection<Object> pDTCol = placeVO
						.getTheParticipationDTCollection();
				Collection<Object> col = null;
				if (elpDTCol != null) {
					col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
					placeVO.setTheEntityLocatorParticipationDTCollection(col);
				}
				if (rDTCol != null) {
					col = this.iterateRDT(rDTCol, nbsSecurityObj);
					placeVO.setTheRoleDTCollection(col);
				}
				if (pDTCol != null) {
					col = this.iteratePDT(pDTCol, nbsSecurityObj);
					placeVO.setTheParticipationDTCollection(col);
				}

				if (placeVO.isItNew()) {
					logger.debug(" EntityControllerEJB.setPlace() isItNew: "
							+ placeVO.isItNew());
					place = home.create(placeVO);
					logger.debug(" EntityControllerEJB.setPlace() Place Created");
				} else {
					logger.debug(" EntityControllerEJB.setPlace() isItNew: "
							+ placeVO.isItNew());
					place = home.findByPrimaryKey(placeVO.getThePlaceDT()
							.getPlaceUid());
					try {
						place.setPlaceVO(placeVO);
					} catch (NEDSSConcurrentDataException ex) {
						logger.fatal("The entity cannot be updated as concurrent access is not allowed!");
						logger.error("Throwing NEDSSConcurrentDataException");
						throw new NEDSSConcurrentDataException(
								"Concurrent access occurred in EntityControllerEJB/ setPlaceEJB : "
										+ ex.toString());
					}
					logger.debug(" EntityControllerEJB.setPlace() Place Updated");
				}
				returnValue = place.getPlaceVO().getThePlaceDT().getPlaceUid();
				logger.debug("Ent Controller past the find - place = "
						+ place.toString());
				logger.debug("Ent Controllerpast the find - place.getPrimaryKey = "
						+ place.getPrimaryKey());
				logger.debug("Ent Controller Done find by primary key!");
			} catch (Exception e) {
				if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
					logger.fatal("EntityControllerEJB.setPlace: NEDSSConcurrentDataException: " + e.getMessage(), e);
					throw new NEDSSConcurrentDataException(e.getMessage(),e);
				} else {
					logger.fatal("EntityControllerEJB.setPlace: Exception: " + e.getMessage(), e);
					throw new javax.ejb.EJBException(e.getMessage(), e);
				}
			}

			return returnValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPlace: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The PlaceDT object for a given personUID
	 * 
	 * @param placeUID
	 *            the placeUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PlaceDT for a given placeUID
	 * @throws EJBException
	 * @roseuid 3C02B0120010
	 * @J2EE_METHOD -- getPlaceInfo
	 */
	public PlaceDT getPlaceInfo(Long placeUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getPlace(placeUID, null).getThePlaceDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlaceInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the placeDT object
	 * 
	 * @param placeDT
	 *            the PlaceDT object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B01201FB
	 * @J2EE_METHOD -- setPlaceInfo
	 */
	public void setPlaceInfo(PlaceDT placeDT, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSConcurrentDataException,
			CreateException, RemoteException, NEDSSSystemException,
			FinderException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			if (placeDT == null) {
				logger.debug("Warning: Place is called with a null PlaceDT");
				return;
			}

			PlaceVO placeVO = getPlace(placeDT.getPlaceUid(), null);
			placeVO.setThePlaceDT(placeDT);
			setPlace(placeVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPlaceInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The PlaceLocators object for a given placeUID
	 * 
	 * @param placeUID
	 *            the placeUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return PlaceLocators for a given placeUID
	 * @throws EJBException
	 * @roseuid 3C02B014030C
	 * @J2EE_METHOD -- getAllPlaceLocators
	 */
	public Collection<Object> getAllPlaceLocators(Long placeUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getPlace(placeUID, null)
					.getTheEntityLocatorParticipationDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getAllPlaceLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * updates the placeLocators collection for given
	 * entityLocatorParticipationDTCollection
	 * 
	 * @param entityLocatorParticipationDTCollection
	 *            the entityLocatorParticipationDT Collection<Object>
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B0150123
	 * @J2EE_METHOD -- setAllPlaceLocators
	 */
	public void setAllPlaceLocators(
			Collection<Object> entityLocatorParticipationDTCollection,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> placeLocatorsCollection = entityLocatorParticipationDTCollection;
			if (placeLocatorsCollection == null) {
				logger.debug("Warning: Place is called with a null Place Entity Locators Collection<Object>");
				return;
			}

			if (placeLocatorsCollection.isEmpty()) {
				logger.debug("Warning: Place Entity Locators Collection<Object> is Empty");
				return;
			}

			// Getting the place UID
			PlaceVO placeVO = getPlace(
					((EntityLocatorParticipationDT) (placeLocatorsCollection.iterator().next()))
							.getEntityUid(), null);
			// Doing the ncessary changes
			placeVO.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);
			// writing it back.
			setPlace(placeVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setAllPlaceLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The placePhysicalLocators collection for a given placeUID
	 * 
	 * @param placeUID
	 *            the placeUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return placePhysicalLocators collection
	 * @throws EJBException
	 * @roseuid 3C02B015032B
	 * @J2EE_METHOD -- getPlacePhysicalLocators
	 */
	public Collection<Object> getPlacePhysicalLocators(Long placeUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> placePhysicalLocators = new ArrayList<Object>();
			Collection<Object> placeLocatorsCollection = getAllPlaceLocators(
					placeUID, null);

			if ((placeLocatorsCollection == null)
					|| (placeLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = placeLocatorsCollection.iterator();
			// go through all the items, and if they have a Physcial Locator add it
			// the ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				PhysicalLocatorDT physicalLocatorDT = entityLocatorParticipationDT
						.getThePhysicalLocatorDT();

				if (physicalLocatorDT != null)
					placePhysicalLocators.add(physicalLocatorDT);
			} while (iterator.hasNext());

			return placePhysicalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlacePhysicalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The placePostalLocators collection for a given placeUID
	 * 
	 * @param placeUID
	 *            the placeUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return placePostalLocators collection
	 * @throws EJBException
	 * @roseuid 3C02B0160369
	 * @J2EE_METHOD -- getPlacePostalLocators
	 */
	public Collection<Object> getPlacePostalLocators(Long placeUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> placePostalLocators = new ArrayList<Object>();
			Collection<Object> placeLocatorsCollection = getAllPlaceLocators(
					placeUID, null);

			if ((placeLocatorsCollection == null)
					|| (placeLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = placeLocatorsCollection.iterator();
			// go through all the items, and if they have a Postal Locator add it
			// the ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				PostalLocatorDT postalLocatorDT = entityLocatorParticipationDT
						.getThePostalLocatorDT();

				if (postalLocatorDT != null)
					placePostalLocators.add(postalLocatorDT);
			} while (iterator.hasNext());

			return placePostalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlacePostalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The placeTeleLocators collection for a given placeUID
	 * 
	 * @param placeUID
	 *            the placeUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return placeTeleLocators collection
	 * @throws EJBException
	 * @roseuid 3C02B01703BB
	 * @J2EE_METHOD -- getPlaceTeleLocators
	 */
	public Collection<Object> getPlaceTeleLocators(Long placeUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> placeTeleLocators = new ArrayList<Object>();
			Collection<Object> placeLocatorsCollection = getAllPlaceLocators(
					placeUID, null);

			if ((placeLocatorsCollection == null)
					|| (placeLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = placeLocatorsCollection.iterator();
			// go through all the items, and if they have a Tele Locator add it the
			// ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				TeleLocatorDT teleLocatorDT = entityLocatorParticipationDT
						.getTheTeleLocatorDT();

				if (teleLocatorDT != null)
					placeTeleLocators.add(teleLocatorDT);
			} while (iterator.hasNext());

			return placeTeleLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlaceTeleLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The placeIDs collection for a given placeUID
	 * 
	 * @param placeUID
	 *            the personUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return placeIDs collection for a given placeUID
	 * @throws EJBException
	 * @roseuid 3C02B01203E5
	 * @J2EE_METHOD -- getPlaceIDs
	 */
	public Collection<Object> getPlaceIDs(Long placeUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getPlace(placeUID, null).getTheEntityIdDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlaceIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The placeIDs collection for a given placeUID
	 * 
	 * @param placeUID
	 *            the personUID(Long value)
	 * @param typeCd
	 *            the typeCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return placeIDs collection for a given placeUID
	 * @throws EJBException
	 * @roseuid 3C02B014010D
	 * @J2EE_METHOD -- setPlaceIDs
	 */
	public Collection<Object> getPlaceIDs(Long placeUID, String typeCd,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> placeEntityIdDTCollection = getPlace(placeUID, null)
					.getTheEntityIdDTCollection();
			Collection<Object> filteredEntityIdDTCollection = new ArrayList<Object>();
			Iterator<Object> iterator = placeEntityIdDTCollection.iterator();
			Object currentElement;

			while ((currentElement = iterator.next()) != null)
				if (((EntityIdDT) currentElement).getTypeCd().compareTo(typeCd) == 0)
					filteredEntityIdDTCollection.add(currentElement);

			return filteredEntityIdDTCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getPlaceIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the PlaceID's collection objects
	 * 
	 * @param placeIDs
	 *            the placeIDs collection
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B014010D
	 * @J2EE_METHOD -- setPlaceIDs
	 */
	public void setPlaceIDs(Collection<Object> placeIDs,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			if (placeIDs == null) {
				logger.debug("Warning: Place is called with a null Meterial Entity Collection<Object>");
				return;
			}
			if (placeIDs.isEmpty()) {
				logger.debug("Warning:  Place is called with an empty Meterial Entity Collection<Object>");
				return;
			}
			Iterator<Object> interator = placeIDs.iterator();

			EntityIdDT entityIdDT = (EntityIdDT) interator.next();
			Long placeUID = entityIdDT.getEntityUid();

			if (placeUID == null) {
				logger.debug("Warning: Place is called with a null Enitty UID");
				return;
			}
			// the main thing functionality is done here.
			// getting the placeVO
			PlaceVO placeVO = getPlace(placeUID, null);
			// doing the modifictaion
			placeVO.setTheEntityIdDTCollection(placeIDs);
			// putting it back
			setPlace(placeVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPlaceIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The OrganizationNameDT Collection<Object> for a given
	 * organizationUID and given nmUseCd
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return OrganizationNameDT Collection<Object>
	 * @throws EJBException
	 * @roseuid 3C02B0190042
	 * @J2EE_METHOD -- getOrganizationNames
	 */
	public Collection<Object> getOrganizationNames(Long organizationUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> organizationNames = new ArrayList<Object>();
			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				organizationNames = organizationVO
						.getTheOrganizationNameDTCollection();

			return organizationNames;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationNames: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The OrganizationNameDT Collection<Object> for a given
	 * organizationUID and given nmUseCd
	 * 
	 * @param organizationUID
	 *            the organizationUID(Long value)
	 * @param nmUseCd
	 *            the nmUseCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return OrganizationNameDT Collection<Object>
	 * @throws EJBException
	 * @roseuid 3C02B0190273
	 * @J2EE_METHOD -- getOrganizationNames
	 */
	public Collection<Object> getOrganizationNames(Long organizationUID,
			String nmUseCd, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			Collection<Object> organizationNames = new ArrayList<Object>();
			Collection<Object> newOrganizationNames = new ArrayList<Object>();
			OrganizationNameDT organizationNameDT = null;

			if (organizationUID != null)
				organizationVO = getOrganization(organizationUID, nbsSecurityObj);
			if (organizationVO != null)
				organizationNames = organizationVO
						.getTheOrganizationNameDTCollection();

			if (organizationNames != null) {
				Iterator<Object> anIterator = null;
				for (anIterator = organizationNames.iterator(); anIterator
						.hasNext();) {
					organizationNameDT = (OrganizationNameDT) anIterator.next();
					if ((organizationNameDT != null)
							&& ((organizationNameDT.getNmUseCd())
									.compareTo(nmUseCd) == 0)) {
						newOrganizationNames.add(organizationNameDT);
					} else {
						continue;
					}

				}

				return newOrganizationNames;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getOrganizationNames: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e); 
		}
	}

	/**
	 * Updates the organizationNames (organizationNameDT collection)
	 * 
	 * @param organizationNames
	 *            organizationNames(organizationNameDT collection)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B01A01E8
	 * @J2EE_METHOD -- setOrganizationNames
	 */
	public void setOrganizationNames(Collection<Object> organizationNames,
			NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException,
			javax.ejb.EJBException {
		try {
			OrganizationVO organizationVO = null;
			OrganizationNameDT organizationNameDT = null;
			Iterator<Object> anIterator = null;
			if (organizationNames != null) {
				anIterator = organizationNames.iterator();
				if (anIterator.hasNext()) {
					organizationNameDT = (OrganizationNameDT) anIterator.next();
					if (organizationNameDT.getOrganizationUid() != null)
						organizationVO = getOrganization(
								organizationNameDT.getOrganizationUid(), null);
					if (organizationVO != null)
						organizationVO
								.setTheOrganizationNameDTCollection(organizationNames);
					organizationVO.setItNew(false);
					this.setOrganization(organizationVO, nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setOrganizationNames: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the EntityGroupVO object for a given entityGroupUID
	 * 
	 * @param entityGroupUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return EntityGroupVO for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B01B003B
	 * @J2EE_METHOD -- getEntityGroup
	 */
	public EntityGroupVO getEntityGroup(Long entityGroupUID,
			NBSSecurityObj nbsSecurityObj) {
		EntityGroupVO entityGroupVO;

		// bypasses security if it's null
		// whoever who addes the security code should take into consideration
		// that whether it's needed to have the functionality of bypassing
		// security or not. If it's not the case the if (...) should be comment
		// out
		// to do the security check for all the accesses through this access.
		if (nbsSecurityObj != null) {
			// reserved for security check code

			// throws proper exception if it's needed
		}

		try {
			
			EntityGroup entityGroup = null;
			Long lpk = entityGroupUID;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.ENTITY_GROUP);
			logger.debug("Ent Controller lookup = " + obj.toString());
			logger.debug("HelloClient before the narrow");
			EntityGroupHome home = (EntityGroupHome) PortableRemoteObject
					.narrow(obj, EntityGroupHome.class);

			logger.debug("Found Ent Controller Home --------------- ");

			entityGroup = home.findByPrimaryKey(lpk);
			entityGroupVO = entityGroup.getEntityGroupVO();

			logger.debug("Ent Controller past the find - entityGroup = "
					+ entityGroup.toString());
			logger.debug("Ent Controllerpast the find - entityGroup.getPrimaryKey = "
					+ entityGroup.getPrimaryKey());
			logger.debug("Ent Controller Done find by primary key!");

		} catch (Exception e) {
			logger.fatal("EntityControllerEJB.getEntityGroup: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);

		}

		return entityGroupVO;
	}

	/**
	 * Creates(if entityGroupVO.isItNew() is true) or updates(if
	 * entityGroupVO.isItDirty() is true) the entityGroupVO
	 * 
	 * @param entityGroupVO
	 *            the EntityGroupVO object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return Long value representing the EntityGroupUID created/updated in the
	 *         process.
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B01B0280
	 * @J2EE_METHOD -- setEntityGroup
	 */
	public Long setEntityGroup(EntityGroupVO entityGroupVO,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		Long returnValue;

		// bypasses security if it's null
		if (nbsSecurityObj != null) {
			// reserved for security check code

			// throws proper exception if it's needed
		}

		try {
			EntityGroup entityGroup = null;
			EntityGroupDT entityGroupDT = entityGroupVO.getTheEntityGroupDT();

			Long lpk = entityGroupDT.getEntityGroupUid();
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.ENTITY_GROUP);
			logger.debug("Ent Controller lookup = " + obj.toString());
			logger.debug("HelloClient before the narrow");
			EntityGroupHome home = (EntityGroupHome) PortableRemoteObject
					.narrow(obj, EntityGroupHome.class);

			logger.debug("Found Ent Controller Home --------------- ");

			Collection<Object> elpDTCol = entityGroupVO
					.getTheEntityLocatorParticipationDTCollection();
			Collection<Object> rDTCol = entityGroupVO.getTheRoleDTCollection();
			Collection<Object> pDTCol = entityGroupVO
					.getTheParticipationDTCollection();
			Collection<Object> col = null;
			if (elpDTCol != null) {
				col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
				entityGroupVO.setTheEntityLocatorParticipationDTCollection(col);
			}
			if (rDTCol != null) {
				col = this.iterateRDT(rDTCol, nbsSecurityObj);
				entityGroupVO.setTheRoleDTCollection(col);
			}
			if (pDTCol != null) {
				col = this.iteratePDT(pDTCol, nbsSecurityObj);
				entityGroupVO.setTheParticipationDTCollection(col);
			}

			if (entityGroupVO.isItNew()) {
				logger.debug(" EntityControllerEJB.setEntityGroup() isItNew: "
						+ entityGroupVO.isItNew());
				entityGroup = home.create(entityGroupVO);
				logger.debug(" EntityControllerEJB.setEntityGroup() EntityGroup Created");
			} else {
				logger.debug(" EntityControllerEJB.setEntityGroup() isItNew: "
						+ entityGroupVO.isItNew());
				entityGroup = home.findByPrimaryKey(entityGroupVO
						.getTheEntityGroupDT().getEntityGroupUid());

				try {
					entityGroup.setEntityGroupVO(entityGroupVO);
				} catch (NEDSSConcurrentDataException ndcex) {
					logger.debug("got the NEDSSConcurrentDataException in EntityControllerEJB");
					throw new NEDSSConcurrentDataException(
							"Exception thrown from EntityControllerEJB");
				}
				logger.debug(" EntityControllerEJB.setEntityGroup() EntityGroup Updated");
			}

			returnValue = entityGroup.getEntityGroupVO().getTheEntityGroupDT()
					.getEntityGroupUid();

			logger.debug("Ent Controller past the find - entityGroup = "
					+ entityGroup.toString());
			logger.debug("Ent Controllerpast the find - entityGroup.getPrimaryKey = "
					+ entityGroup.getPrimaryKey());
			logger.debug("Ent Controller Done find by primary key!");
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("EntityControllerEJB.setEntityGroup: NEDSSConcurrentDataException: concurrent access is not allowed: " + ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.setEntityGroup: NEDSSConcurrentDataException: " + e.getMessage(), e);
				throw new NEDSSConcurrentDataException(e.getMessage(), e);
			} else {
				logger.fatal("EntityControllerEJB.setEntityGroup: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}
		return returnValue;
	}

	/**
	 * Returns The EntityGroupDT object for a given entityGroupUID
	 * 
	 * @param entityGroupUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return EntityGroupDT for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B01C00D3
	 * @J2EE_METHOD -- getEntityGroupInfo
	 */
	public EntityGroupDT getEntityGroupInfo(Long entityGroupUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getEntityGroup(entityGroupUID, null).getTheEntityGroupDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getEntityGroupInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates the entityGroupDT
	 * 
	 * @param entityGroupDT
	 *            the EntityGroupDT object
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B01C032C
	 * @J2EE_METHOD -- setEntityGroupInfo
	 */
	public void setEntityGroupInfo(EntityGroupDT entityGroupDT,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			if (entityGroupDT == null) {
				logger.debug("Warning: EntityGroup is called with a null entityGroupDT");
				return;
			}

			EntityGroupVO entityGroupVO = getEntityGroup(
					entityGroupDT.getEntityGroupUid(), null);
			entityGroupVO.setTheEntityGroupDT(entityGroupDT);
			setEntityGroup(entityGroupVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setEntityGroupInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns all of Entity Group Entity group Locators based on entityGroupUID
	 * and security
	 * 
	 * @param entityGroupUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return entityGroupLocatorCollection for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B01F022B
	 * @J2EE_METHOD -- getAllEntityGroupLocators
	 */
	public Collection<Object> getAllEntityGroupLocators(Long entityGroupUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getEntityGroup(entityGroupUID, null)
					.getTheEntityLocatorParticipationDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getAllEntityGroupLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates all of Entity Group Entity Locators based on
	 * entityLocatorParticipationDTCollection and security
	 * 
	 * @param entityLocatorParticipationDTCollection
	 *            the entityLocatorParticipationDT Collection<Object>
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B02000A6
	 * @J2EE_METHOD -- setAllEntityGroupLocators
	 */
	public void setAllEntityGroupLocators(
			Collection<Object> entityLocatorParticipationDTCollection,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> entityGroupLocatorsCollection = entityLocatorParticipationDTCollection;
			if (entityGroupLocatorsCollection == null) {
				logger.debug("Warning: Entity Group called with a null Entity Group Entity Locators Collection<Object>");
				return;
			}

			if (entityGroupLocatorsCollection.isEmpty()) {
				logger.debug("Warning: Entity Group Entity Locators Collection<Object> is Empty");
				return;
			}

			// Getting the entityGroup UID
			EntityGroupVO entityGroupVO = getEntityGroup(
					((EntityLocatorParticipationDT) (entityGroupLocatorsCollection.iterator().next()))
							.getEntityUid(), null);
			// Doing the ncessary changes
			entityGroupVO
					.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);
			// writing it back.
			setEntityGroup(entityGroupVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setAllEntityGroupLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns all of Entity Group Entity Postal Locators based on UID and
	 * security
	 * 
	 * @param entityGroupUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return entityGroupPostalLocatorCollection for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B0200313
	 * @J2EE_METHOD -- getEntityGroupPhysicalLocators
	 */
	public Collection<Object> getEntityGroupPhysicalLocators(
			Long entityGroupUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> entityGroupPhysicalLocators = new ArrayList<Object>();
			Collection<Object> entityGroupLocatorsCollection = getAllEntityGroupLocators(
					entityGroupUID, null);

			if ((entityGroupLocatorsCollection == null)
					|| (entityGroupLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = entityGroupLocatorsCollection.iterator();
			// go through all the items, and if they have a Physcial Locator add it
			// the ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				PhysicalLocatorDT physicalLocatorDT = entityLocatorParticipationDT
						.getThePhysicalLocatorDT();

				if (physicalLocatorDT != null)
					entityGroupPhysicalLocators.add(physicalLocatorDT);
			} while (iterator.hasNext());

			return entityGroupPhysicalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getEntityGroupPhysicalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns all of Entity Group Entity Postal Locators based on UID and
	 * security
	 * 
	 * @param entityGroupUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return entityGroupPostalLocatorCollection for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B0220031
	 * @J2EE_METHOD -- getEntityGroupPostalLocators
	 */
	public Collection<Object> getEntityGroupPostalLocators(Long entityGroupUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> entityGroupPostalLocators = new ArrayList<Object>();
			Collection<Object> entityGroupLocatorsCollection = getAllEntityGroupLocators(
					entityGroupUID, null);

			if ((entityGroupLocatorsCollection == null)
					|| (entityGroupLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = entityGroupLocatorsCollection.iterator();
			// go through all the items, and if they have a Postal Locator add it
			// the ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				PostalLocatorDT postalLocatorDT = entityLocatorParticipationDT
						.getThePostalLocatorDT();

				if (postalLocatorDT != null)
					entityGroupPostalLocators.add(postalLocatorDT);
			} while (iterator.hasNext());

			return entityGroupPostalLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getEntityGroupPostalLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns all of EntityGroupTeleLocators based on entityGroupUID and
	 * security
	 * 
	 * @param entityGroupUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return entityGroupTeleLocator Collection<Object> for a given
	 *         entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B023014B
	 * @J2EE_METHOD -- getEntityGroupTeleLocators
	 */
	public Collection<Object> getEntityGroupTeleLocators(Long entityGroupUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> entityGroupTeleLocators = new ArrayList<Object>();
			Collection<Object> entityGroupLocatorsCollection = getAllEntityGroupLocators(
					entityGroupUID, null);

			if ((entityGroupLocatorsCollection == null)
					|| (entityGroupLocatorsCollection.isEmpty()))
				return null;

			Iterator<Object> iterator = entityGroupLocatorsCollection.iterator();
			// go through all the items, and if they have a Tele Locator add it the
			// ArrayList<Object> .
			do {
				Object currentItem = iterator.next();
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) currentItem;
				TeleLocatorDT teleLocatorDT = entityLocatorParticipationDT
						.getTheTeleLocatorDT();

				if (teleLocatorDT != null)
					entityGroupTeleLocators.add(teleLocatorDT);
			} while (iterator.hasNext());

			return entityGroupTeleLocators;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getEntityGroupTeleLocators: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The EntityGroupIDs collection for a given entityGroupUID
	 * 
	 * @param personUID
	 *            the entityGroupUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return EntityGroupIDs for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B01D0188
	 * @J2EE_METHOD -- getEntityGroupIDs
	 */
	public Collection<Object> getEntityGroupIDs(Long entityGroupUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			return getEntityGroup(entityGroupUID, null)
					.getTheEntityIdDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getEntityGroupIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns The EntityGroupIDs collection for a given entityGroupUID and
	 * given typeCd
	 * 
	 * @param personUID
	 *            the entityGroupUID(Long value)
	 * @param typeCd
	 *            the typeCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return EntityGroupIDs for a given entityGroupUID
	 * @throws EJBException
	 * @roseuid 3C02B01E0003
	 * @J2EE_METHOD -- getEntityGroupIDs
	 */
	public Collection<Object> getEntityGroupIDs(Long entityGroupUID,
			String typeCd, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			Collection<Object> entityGroupEntityIdDTCollection = getEntityGroup(
					entityGroupUID, null).getTheEntityIdDTCollection();
			Collection<Object> filteredEntityIdDTCollection = new ArrayList<Object>();
			Iterator<Object> iterator = entityGroupEntityIdDTCollection.iterator();
			Object currentElement;

			while ((currentElement = iterator.next()) != null)
				if (((EntityIdDT) currentElement).getTypeCd().compareTo(typeCd) == 0)
					filteredEntityIdDTCollection.add(currentElement);

			return filteredEntityIdDTCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getEntityGroupIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Updates all of Entity Group EntityGroupIDs based on entityGroupIDs and
	 * security
	 * 
	 * @param entityGroupIDs
	 *            the entityGroupIDs Collection<Object>
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 * @roseuid 3C02B01E039D
	 * @J2EE_METHOD -- setEntityGroupIDs
	 */
	public void setEntityGroupIDs(Collection<Object> entityGroupIDs,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSConcurrentDataException {
		try {
			// bypasses security if it's null
			if (nbsSecurityObj != null) {
				// reserved for security check code

				// throws proper exception if it's needed
			}

			if (entityGroupIDs == null) {
				logger.debug("Warning:  Entity Group is called with a null Meterial Entity Collection<Object>");
				return;
			}
			if (entityGroupIDs.isEmpty()) {
				logger.debug("Warning:  Entity Group is called with an empty Meterial Entity Collection<Object>");
				return;
			}
			Iterator<Object> interator = entityGroupIDs.iterator();

			EntityIdDT entityIdDT = (EntityIdDT) interator.next();
			Long entityGroupUID = entityIdDT.getEntityUid();

			if (entityGroupUID == null) {
				logger.debug("Warning: Entity Group is called with a null Enitty UID");
				return;
			}
			// the main thing functionality is done here.
			// getting the entityGroupVO
			EntityGroupVO entityGroupVO = getEntityGroup(entityGroupUID, null);
			// doing the modifictaion
			entityGroupVO.setTheEntityIdDTCollection(entityGroupIDs);
			// putting it back
			setEntityGroup(entityGroupVO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setEntityGroupIDs: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the roleDT for a given subjectEntityUID, scopingEntityUID and
	 * classCd
	 * 
	 * @param subjectEntityUID
	 *            the subjectEntityUID(Long value)
	 * @param scopingEntityUID
	 *            the scopingEntityUID(Long value)
	 * @param classCd
	 *            the classCd(String value)
	 * @param roleSeq
	 *            the roleSeq(Integer value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return RoleDT object
	 * @throws EJBException
	 * @roseuid 3C02B024028D
	 * @J2EE_METHOD -- getRole ####not yet implemented####
	 */
	public RoleDT getRole(Long subjectEntityUID, String classCd,
			Integer roleSeq, Long scopingEntityUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		return null;
	}

	/**
	 * Returns the role collection for a given subjectEntityUID
	 * 
	 * @param subjectEntityUID
	 *            the subjectEntityUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return RoleDT Collection<Object>
	 * @throws EJBException
	 * @roseuid 3C02B026016D
	 * @J2EE_METHOD -- getRoles ####not yet implemented####
	 */
	public Collection<Object> getRoles(Long subjectEntityUID,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		return null;
	}

	/**
	 * Returns the role collection for a given subjectEntityUID,
	 * scopingEntityUID and classCd
	 * 
	 * @param subjectEntityUID
	 *            the subjectEntityUID(Long value)
	 * @param scopingEntityUID
	 *            the scopingEntityUID(Long value)
	 * @param classCd
	 *            the classCd(String value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return RoleDT Collection<Object>
	 * @throws EJBException
	 * @roseuid 3C02B027002E
	 * @J2EE_METHOD -- getRoles ####not yet implemented####
	 */
	public Collection<Object> getRoles(Long subjectEntityUID, String classCd,
			Long scopingEntityUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		return null;
	}

	/**
	 * Returns the ParticipationDT object for a given subjectEntityUID and given
	 * actUID
	 * 
	 * @param subjectEntityUID
	 *            the subjectEntityUID(Long value)
	 * @param actUID
	 *            the actUID(Long value)
	 * @param classCd
	 *            the classCd(String value)
	 * @param roleSeq
	 *            the roleSeq(Integer value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return ParticipationDT
	 * @throws EJBException
	 * @roseuid 3C02B02801C0
	 * @J2EE_METHOD -- getParticipation ####not yet implemented####
	 */
	public ParticipationDT getParticipation(Long subjectEntityUID, Long actUID,
			Integer participationSeq, String classCd, Integer roleSeq,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
		return null;
	}

	/**
	 * Returns The participationDT collection object
	 * 
	 * @param subjectEntityUID
	 *            the subjectEntityUID(Long value)
	 * @param actUID
	 *            the actUID(Long value)
	 * @param nbsSecurityObj
	 *            the NBSSecurityObj (security object)
	 * @return participationDT collection
	 * @throws EJBException
	 * @roseuid 3C02B02A024F
	 * @J2EE_METHOD -- getParticipations ####not yet implemented####
	 */
	public Collection<Object> getParticipations(Long subjectEntityUID,
			Long actUID, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException {
		return null;
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 * 
	 * @roseuid 3C02CDB200AB
	 * @J2EE_METHOD -- setSessionContext
	 */
	public void setSessionContext(SessionContext sessioncontext)
			throws EJBException, RemoteException {
		this.sessioncontext = sessioncontext;
	}

	private Long setOrganizationInternal(OrganizationVO organizationVO,
			String businessObjLookupName, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException {
		Long organizationUID = new Long(-1);
		try {
			logger.debug("\n\n Inside set");
			if (!(organizationVO.isItNew()) && !(organizationVO.isItDirty())) {
				return organizationVO.getTheOrganizationDT()
						.getOrganizationUid();
			} else {
				PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				OrganizationDT newOrganizationDT = (OrganizationDT) prepareVOUtils
						.prepareVO(organizationVO.getTheOrganizationDT(),
								businessObjLookupName, businessTriggerCd,
								DataTables.ORGANIZATION_TABLE,
								NEDSSConstants.BASE, nbsSecurityObj);
				organizationVO.setTheOrganizationDT(newOrganizationDT);

				Collection<Object> elpDTCol = organizationVO
						.getTheEntityLocatorParticipationDTCollection();
				Collection<Object> rDTCol = organizationVO
						.getTheRoleDTCollection();
				Collection<Object> pDTCol = organizationVO
						.getTheParticipationDTCollection();
				Collection<Object> col = null;
				if (elpDTCol != null) {
					col = this.iterateELPDT(elpDTCol, nbsSecurityObj);
					organizationVO
							.setTheEntityLocatorParticipationDTCollection(col);
				}
				if (rDTCol != null) {
					col = this.iterateRDT(rDTCol, nbsSecurityObj);
					organizationVO.setTheRoleDTCollection(col);
				}
				if (pDTCol != null) {
					col = this.iteratePDT(pDTCol, nbsSecurityObj);
					organizationVO.setTheParticipationDTCollection(col);
				}
				/* Call the function to persist the OrganizationName */

				this.prepareOrganizationNameBeforePersistence(organizationVO,
						nbsSecurityObj);

				Organization organization = null;
				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.ORGANIZATIONEJB);
				logger.debug("EntityControllerEJB.setOrganization - lookup = "
						+ obj.toString());
				OrganizationHome home = (OrganizationHome) PortableRemoteObject
						.narrow(obj, OrganizationHome.class);
				logger.debug("EntityControllerEJB.setOrganization - Found OrganizationHome: "
						+ home);

				if (organizationVO.isItNew()) {
					organization = home.create(organizationVO);
					logger.debug(" EntityControllerEJB.setOrganization -  Organization Created");
					logger.debug("EntityControllerEJB.setOrganization - organization.getOrganizationVO().getTheOrganizationDT().getOrganizationUid() =  "
							+ organization.getOrganizationVO()
									.getTheOrganizationDT()
									.getOrganizationUid());
					organizationUID = organization.getOrganizationVO()
							.getTheOrganizationDT().getOrganizationUid();
				} else {
					organization = home.findByPrimaryKey(organizationVO
							.getTheOrganizationDT().getOrganizationUid());
					organization.setOrganizationVO(organizationVO);
					logger.debug(" EntityControllerEJB.setOrganization -  Organization Updated");
					organizationUID = organization.getOrganizationVO()
							.getTheOrganizationDT().getOrganizationUid();
				}
			}
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("EntityControllerEJB.setOrganizationInternal: NEDSSConcurrentDataException: concurrent access is not allowed: " + ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		} catch (Exception e) {
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityControllerEJB.setOrganizationInternal: NEDSSConcurrentDataException: " + e.getMessage(), e);
				throw new NEDSSConcurrentDataException(e.getMessage(), e);
			} else {
				logger.fatal("EntityControllerEJB.setOrganizationInternal: Exception: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}

		logger.debug("EntityControllerEJB.setOrganization - ouid  =  "
				+ organizationUID);
		return organizationUID;

	}

	private void prepareOrganizationNameBeforePersistence(
			OrganizationVO organizationVO, NBSSecurityObj nbsSecurityObj) {
		try {
			Collection<Object> namesCollection = null;
			Iterator<Object> anIterator = null;
			String selectedName = null;
			namesCollection = organizationVO.getTheOrganizationNameDTCollection();
			if (namesCollection != null) {
				try {
					for (anIterator = namesCollection.iterator(); anIterator
							.hasNext();) {
						OrganizationNameDT organizationNameDT = (OrganizationNameDT) anIterator
								.next();
						if (organizationNameDT.getNmUseCd().equals("L")) {
							selectedName = organizationNameDT.getNmTxt();
							organizationVO.getTheOrganizationDT().setDisplayNm(
									selectedName);
						}
					}
				} catch (Exception e) {
					logger.error("Exception setting the Organization Name: "+selectedName);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.prepareOrganizationNameBeforePersistence: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/**
	 * This private method is used to populate the system attributes on the
	 * EntityLocatoryParticipation Collection<Object>
	 * 
	 * @param dtCol
	 *            Collection<Object>
	 * @param securityObj
	 *            NBSSecurityObj object
	 * @return Collection<Object> EntityLocatoryParticipation collection object
	 *         populated with system attributes
	 */
	private Collection<Object> iterateELPDT(Collection<Object> dtCol,
			NBSSecurityObj nbsSecurityObj) {
		Collection<Object> retCol = new ArrayList<Object>();
		Collection<Object> collection = new ArrayList<Object>();
		Iterator<Object> anIterator = null;
		collection = dtCol;
		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		logger.debug("Collection<Object> size before iteration in iterateELPDT "
				+ collection.size());
		if (collection != null) {
			try {
				for (anIterator = collection.iterator(); anIterator.hasNext();) {
					EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) anIterator
							.next();
					AssocDTInterface assocDTInterface = elpDT;
					logger.debug("Iterating EntityLocatorParticipationDT");
					elpDT = (EntityLocatorParticipationDT) prepareVOUtils
							.prepareAssocDT(assocDTInterface, nbsSecurityObj);
					logger.debug("Came back from PrepareVOUtils");
					retCol.add(elpDT);
					if (retCol == null)
						logger.debug("Collection<Object> in iterateELPDT is null");
				}
			}

			catch (Exception e) {
				logger.fatal("EntityControllerEJB.iterateELPDT: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}
		logger.debug("Collection<Object> size after iteration in iterateELPDT "
				+ retCol.size());
		return retCol;

	}

	/**
	 * This private method is used to populate the system attributes on the
	 * RoleDT Collection<Object>
	 * 
	 * @param dtCol
	 *            Collection<Object>
	 * @param securityObj
	 *            NBSSecurityObj object
	 * @return Collection<Object> RoleDT collection object populated with system
	 *         attributes
	 */
	private Collection<Object> iterateRDT(Collection<Object> dtCol,
			NBSSecurityObj nbsSecurityObj) {
		Collection<Object> retCol = new ArrayList<Object>();
		Collection<Object> collection = new ArrayList<Object>();
		Iterator<Object> anIterator = null;
		collection = dtCol;
		logger.debug("Collection<Object> size before iteration in iterateRDT "
				+ collection.size());
		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		if (collection != null) {
			try {
				for (anIterator = collection.iterator(); anIterator.hasNext();) {
					RoleDT rDT = (RoleDT) anIterator.next();
					if (rDT.isItDirty() || rDT.isItNew() || rDT.isItDelete()) {
						logger.debug("EntityController:rdT.IsItDelete"
								+ rDT.isItDelete() + "rdt.IsItNew:"
								+ rDT.isItNew() + "rdt.IsItDirty:"
								+ rDT.isItDirty());
						AssocDTInterface assocDTInterface = rDT;
						rDT = (RoleDT) prepareVOUtils.prepareAssocDT(
								assocDTInterface, nbsSecurityObj);
						retCol.add(rDT);
					}
				}
			} catch (Exception e) {
				logger.fatal("EntityControllerEJB.iterateRDT: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}
		logger.debug("Collection<Object> size after iteration in iterateRDT "
				+ retCol.size());
		return retCol;

	}

	/**
	 * This private method is used to populate the system attributes on the
	 * ParticipationDT Collection<Object>
	 * 
	 * @param dtCol
	 *            Collection<Object>
	 * @param securityObj
	 *            NBSSecurityObj object
	 * @return Collection<Object> ParticipationDT collection object populated
	 *         with system attributes
	 */

	private Collection<Object> iteratePDT(Collection<Object> dtCol,
			NBSSecurityObj nbsSecurityObj) {
		Collection<Object> retCol = new ArrayList<Object>();
		Collection<Object> collection = new ArrayList<Object>();
		Iterator<Object> anIterator = null;
		collection = dtCol;
		logger.debug("Collection<Object> size before iteration in iteratePDT "
				+ collection.size());
		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

		if (collection != null) {
			try {
				for (anIterator = collection.iterator(); anIterator.hasNext();) {
					ParticipationDT pDT = (ParticipationDT) anIterator.next();
					if (pDT.isItDirty() || pDT.isItNew() || pDT.isItDelete()) {
						logger.debug("EntityController:pdT.IsItDelete"
								+ pDT.isItDelete() + "pdt.IsItNew:"
								+ pDT.isItNew() + "pdt.IsItDirty:"
								+ pDT.isItDirty());
						AssocDTInterface assocDTInterface = pDT;
						pDT = (ParticipationDT) prepareVOUtils.prepareAssocDT(
								assocDTInterface, nbsSecurityObj);
						retCol.add(pDT);
					}
				}
			} catch (Exception e) {
				logger.fatal("EntityControllerEJB.iteratePDT: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
		}
		logger.debug("Collection<Object> size after iteration in iteratePDT "
				+ retCol.size());
		return retCol;
	}

	/**
	 * Returns the PersonVO object
	 * 
	 * @roseuid 3E7B38100399
	 * @J2EE_METHOD -- getMPR
	 * @param personUID
	 *            Long
	 * @param nbsSecurityObj
	 *            NBSSecurityObj
	 * @return personVO PersonVO
	 * @throws EJBException
	 */
	public PersonVO getMPR(Long personUID, NBSSecurityObj nbsSecurityObj)
			throws EJBException {
		try {
			PersonVO personVO = this.getPersonInternal(personUID, nbsSecurityObj);
			return personVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.getMPR: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	/**
	 * Returns a Long object which contains the personUID
	 * 
	 * @roseuid 3E7B380702FD
	 * @J2EE_METHOD -- setMPR
	 * @param personVO
	 *            PersonVO
	 * @param businessTriggerCd
	 *            String
	 * @param nbsSecurityObj
	 *            NBSSecurityObj
	 * @return personUID Long
	 * @throws EJBException
	 * @throws NEDSSConcurrentDataException
	 *             if data concurrency occurs
	 */
	public Long setMPR(PersonVO personVO, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSConcurrentDataException {
		try {
			Long personUID = null;
			personVO.getThePersonDT().setEdxInd(NEDSSConstants.EDX_IND);
			if(personVO.isExt()){
				personVO.setItNew(false);
				personVO.setItDirty(false);
				if(personVO.isExt()){
					if(personVO.getThePersonNameDTCollection()!=null){
						Collection<Object> coll = personVO.getThePersonNameDTCollection();
						Iterator<Object> itName = coll.iterator();
						while (itName.hasNext()) {
							PersonNameDT personNameDT = (PersonNameDT) itName
									.next();
							personNameDT.setItDirty(true);
							personNameDT.setItNew(false);

			
						}
					}
				}
			}
			if(personVO.isMPRUpdateValid()){
				personUID = this.setPersonInternal(personVO, NBSBOLookup.PATIENT,
					businessTriggerCd, nbsSecurityObj);
			
			try {
				
				if(personVO.getThePersonDT().getPersonParentUid()==null)
					personVO.getThePersonDT().setPersonParentUid(personUID);
					setPatientHashCd(personVO, nbsSecurityObj);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NEDSSAppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			return personUID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setMPR: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	/*
	 * Call the function to persist the patient hashcode in edx patient match
	 * table
	 */

	public void setPatientHashCd(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			RemoteException, NEDSSAppException {

		try {
			long personUid = personVO.getThePersonDT().getPersonParentUid();
			FindPersonDAOImpl personDao = new FindPersonDAOImpl();
			EdxPatientMatchingCriteriaUtil util = new EdxPatientMatchingCriteriaUtil();
			personDao.deleteEdxPatientMatchDTColl(personUid);
			try {
				if(personVO.getThePersonDT().getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)){
				 personVO.getThePersonDT().setPersonUid(personUid);
				 util.setPatientToEntityMatch(personVO, nbsSecurityObj);
				}
			} catch (Exception e) {
				//per defect #1836 change to warning..
				logger.warn("Unable to setPatientHashCd for personUid: "+personUid);
				logger.warn("Exception in setPatientToEntityMatch -> unhandled exception: " +e.getMessage());
			}
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityControllerEJB.setPatientHashCd: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}
}
