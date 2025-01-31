package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean;

import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.ejb.dao.EDXDocumentDAOImpl;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistoryManager;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.ContainerDocument.Container;
import gov.cdc.nedss.systemservice.dao.EDXActivityLogDAOImpl;
import gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.genericXMLParser.MsgXMLMappingDT;
import gov.cdc.nedss.systemservice.genericXMLParser.GenericXMLParser;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;
import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

/**
 * 
 * @author 
 * History:
 * 2011/10/12	reformat
 */
public class NbsDocumentEJB implements SessionBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(NbsDocumentEJB.class.getName());
	private SessionContext sessionCtx = null;
	
	public NBSDocumentVO getNBSDocumentWithoutActRelationship(Long nbsDocUid,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		try {
			NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();
			NBSDocumentVO nbsDocumentVO = null;
			PersonVO personVO = null;
			ParticipationDT participationDt = null;

			try {
				nbsDocumentVO = nbsDocDAO.getNBSDocument(nbsDocUid);
			} catch (Exception e) {
				logger.fatal("Error in getting the record from NBS Document table in getNBSDocument"+e.getMessage(), e);
				throw new NEDSSAppException(e.getMessage(), e);
			}

			Long personUid = nbsDocumentVO.getPatientVO().getThePersonDT()
					.getPersonUid();

			try {
				personVO = this.getPerson(personUid, nbsSecurityObj);
			} catch (Exception e) {
				logger.fatal("Error in getting the info for Patient table in getNBSDocument"+e.getMessage(), e);
				throw new NEDSSAppException(e.getMessage(), e);
			}

			nbsDocumentVO.setPatientVO(personVO);

			try {
				participationDt = this.getParticipation(personUid, nbsDocUid,
						nbsSecurityObj);
			} catch (Exception e) {
				logger.fatal("Error in getting the Participation info in getNBSDocument "+e.getMessage(), e);
				throw new NEDSSAppException(e.getMessage(), e);
			}

			nbsDocumentVO.setParticipationDT(participationDt);
			return nbsDocumentVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public NBSDocumentVO getNBSDocument(Long nbsDocUid,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		NBSDocumentVO nbsDocumentVO = getNBSDocumentWithoutActRelationship(
				nbsDocUid, nbsSecurityObj);
		ActRelationshipDAOImpl actRelDaoImpl = new ActRelationshipDAOImpl();
		Collection<Object> actRelColl = null;
		try {
			actRelColl = actRelDaoImpl.loadSource(nbsDocumentVO
					.getNbsDocumentDT().getNbsDocumentUid().longValue());
		} catch (Exception e) {
			logger.fatal("Error in getting the Act Relationships in getNBSDocument "+e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
		nbsDocumentVO.setActRelColl(actRelColl);
		return nbsDocumentVO;
	}

	private PersonVO getPerson(Long personUID, NBSSecurityObj nbsSecurityObj)
			throws RemoteException, Exception {
		try {
			PersonVO personVO = null;

			// Entity Controller lookup
			NedssUtils nedssUtils = new NedssUtils();
			Object lookedUpObj = nedssUtils
					.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("!!!!!!!!!!EntityController lookup = "
					+ lookedUpObj.toString());
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
					.narrow(lookedUpObj, EntityControllerHome.class);
			logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
			EntityController entityController = ecHome.create();

			if (personUID != null) {
				try {

					personVO = entityController
							.getPerson(personUID, nbsSecurityObj);
				} catch (Exception ex) {
					logger.fatal("Error while getting person imformation: ", ex);
					return null;
				}

			}
			return personVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	private ParticipationDT getParticipation(Long subjectEntityUid,
			Long actUid, NBSSecurityObj nbsSecurityObj) throws RemoteException,
			Exception {
		try {
			ParticipationDT participationDt = null;
			// Entity Controller lookup
			NedssUtils nedssUtils = new NedssUtils();
			Object lookedUpObj = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("!!!!!!!!!!ActController lookup = "
					+ lookedUpObj.toString());
			ActControllerHome actHome = (ActControllerHome) PortableRemoteObject
					.narrow(lookedUpObj, ActControllerHome.class);
			logger.debug("!!!!!!!!!!Found ActControllerHome: " + actHome);
			ActController actController = actHome.create();

			if (subjectEntityUid != null && actUid != null) {
				try {
					ArrayList<Object> arr = (ArrayList<Object>) actController
							.getParticipations(subjectEntityUid, actUid,
									nbsSecurityObj);
					participationDt = (ParticipationDT) arr.get(0);

				} catch (Exception ex) {
					logger.fatal("Error while getting person imformation: ", ex);
					return null;
				}
			}
			return participationDt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public Long updateDocument(NBSDocumentVO nbsDocVO,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		try {
			Long nbsDocuid = updateDocumentWithOutthePatient(nbsDocVO,
					nbsSecurityObj);
			// Entity Controller lookup
			NedssUtils nedssUtils = new NedssUtils();
			Object lookedUpObj = nedssUtils
					.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("!!!!!!!!!!EntityController lookup = "
					+ lookedUpObj.toString());
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
					.narrow(lookedUpObj, EntityControllerHome.class);
			logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
			EntityController entityController = ecHome.create();

			Long realUid = null;
			// update the person history
			String businessTriggerCd = NEDSSConstants.PAT_EDIT;
				//#7861 - can't delete patient because open patient revisions.
			if (nbsDocVO.getPatientVO().isItDelete())
				businessTriggerCd = NEDSSConstants.PAT_DEL;
			
			try {
				realUid = entityController.setPatientRevision(
						nbsDocVO.getPatientVO(), businessTriggerCd, nbsSecurityObj);
			} catch (NEDSSConcurrentDataException ex) {
				logger.fatal("The entity cannot be updated as concurrent access is not allowed!");
				throw new NEDSSConcurrentDataException( ex.getMessage(), ex);
			} catch (Exception ex) {
				logger.fatal("Error in executing entityController.setPatientRevision when personVO.isDirty is true");
				ex.printStackTrace();
				throw new javax.ejb.EJBException( ex.getMessage(), ex);
			}

			// inserting the record to participation History.
			try {
				insertParticipationHistory(nbsDocVO.getParticipationDT());
			} catch (Exception e) {
				logger.fatal("Error while inserting to Participation History ",
						e.getMessage());
				throw new javax.ejb.EJBException( e.getMessage(), e);
			}

			return nbsDocuid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public Long updateDocumentWithOutthePatient(NBSDocumentVO nbsDocVO,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		Long nbsDocUid = null;
		NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();

		try {
			NBSDocumentVO nbSOldDocumentVO = getNBSDocument(nbsDocVO
					.getNbsDocumentDT().getNbsDocumentUid(), nbsSecurityObj);

			if (nbSOldDocumentVO != null) {
				NBSDocumentDT nbsDocumentDT = nbsDocVO.getNbsDocumentDT();
				nbsDocumentDT.setSuperclass("ACT");
				RootDTInterface rootDTInterface = nbsDocVO.getNbsDocumentDT();
				String businessObjLookupName = NBSBOLookup.DOCUMENT;
				String businessTriggerCd = null;
				businessTriggerCd = "DOC_PROCESS";

				if (nbsDocumentDT.getRecordStatusCd() != null
						&& nbsDocumentDT.getRecordStatusCd().equals(
								NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE))
					businessTriggerCd = "DOC_DEL";
				if (nbsDocVO.isFromSecurityQueue())
					businessTriggerCd = "DOC_IN_PROCESS";
				String tableName = "NBS_DOCUMENT";
				String moduleCd = "BASE";
				PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				nbsDocumentDT = (NBSDocumentDT) prepareVOUtils.prepareVO(
						rootDTInterface, businessObjLookupName,
						businessTriggerCd, tableName, moduleCd, nbsSecurityObj);

				// update the record
				nbsDocUid = nbsDocDAO.updateNbsDocument(nbsDocumentDT);

				// insert the old record in the history
				nbsDocDAO.insertNBSDocumentHist(nbSOldDocumentVO
						.getNbsDocumentDT());
			}
		} catch (Exception e) {
			logger.fatal("Error while updatinh the record to NBSDocument/NBSDocumentHist table "+e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

		return nbsDocUid;

	}

	/**
	 * 
	 * @param dt
	 * @throws NEDSSSystemException
	 */
	private void insertParticipationHistory(ParticipationDT dt)
			throws NEDSSSystemException {
		if (dt != null) {
			ParticipationHistoryManager man = new ParticipationHistoryManager(
					dt.getSubjectEntityUid().longValue(), dt.getActUid()
							.longValue(), dt.getTypeCd());
			try {
				man.store(dt);
			} catch (Exception e) {
				logger.fatal("Error in inserting the History for the participation"+e.getMessage(), e);
				throw new NEDSSSystemException(
						"Error while inserting the Participation History "
								+ e.getMessage(), e);
			}

		}
	}

	public void ejbCreate() throws java.rmi.RemoteException,
			javax.ejb.CreateException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void ejbRemove() throws EJBException, RemoteException {
	}

	@Override
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		
	}

	public NbsInterfaceDT extractXMLDocument(Long uid, NBSSecurityObj nbsSecurityObj)
			throws RemoteException, Exception {

		try {
			NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
			NbsInterfaceDT nbsInterfaceDT = nbsInterfaceDAOImpl
				.getInterfaceDTByUID(uid);
			return nbsInterfaceDT;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public Collection<Object> extractNBSInterfaceUids(NBSSecurityObj nbsSecurityObj)
			throws RemoteException, Exception {

		try {
			NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
			Collection<Object> newMessageColl = nbsInterfaceDAOImpl
					.getSQLUnprocessedInterfaceUIDCollection(NEDSSConstants.PHC_236);
			return newMessageColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	
	public NBSDocumentVO createNBSDocument(NbsInterfaceDT nbsInterfaceDT,
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			NEDSSAppException {
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		NBSDocumentVO nBSDocumentVO = new NBSDocumentVO();
		NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();

		try {

			if (nbsInterfaceDT.getXmlPayLoadContent() == null) {
				nbsInterfaceDT = nbsInterfaceDAOImpl.getInterfaceDTByUID(
						nbsInterfaceDT.getNbsInterfaceUid());
			}
			String xml = nbsInterfaceDT.getXmlPayLoadContent();
			Map<Object, Object> returnMap =  retrieveXMLSchemaLocation(xml);
			String schemaLocation = (String)returnMap.get(NEDSSConstants.XML_SCHEMA);
			Object payload = returnMap.get(NEDSSConstants.XML_PAYLOAD);
			NBSDocumentMetadataDT nbsDocMDT = getDocumentMetadataUsingSchemaLocationAndDocTypeCd(schemaLocation);
			XMLTypeToNBSObject xmlObject = new XMLTypeToNBSObject();
			CDAXMLTypeToNBSObject cdaXmlObject = new CDAXMLTypeToNBSObject();
			if (nbsDocMDT != null
					&& nbsDocMDT.getDocTypeCd() != null
					&& nbsDocMDT.getDocTypeCd().equals(
							NEDSSConstants.EDX_PHDC_DOC_TYPE))
				nBSDocumentVO = xmlObject.createNBSDocumentVO(nbsInterfaceDT,
						time, nbsDocMDT, nbsSecurityObj);
			else if (nbsDocMDT != null
					&& nbsDocMDT.getDocTypeCd() != null
					&& nbsDocMDT.getDocTypeCd().equals(
							NEDSSConstants.CDA_PHDC_TYPE))
				nBSDocumentVO = cdaXmlObject.createNBSDocumentVO(
						nbsInterfaceDT, time, nbsDocMDT, payload, nbsSecurityObj);
			else
				throw new NEDSSAppException("Unknown ContainerDocument:\n"
						+ xml);

		
			
			
			xmlObject.insertNbsDocumentVO(nbsInterfaceDT,nBSDocumentVO,
						nbsSecurityObj);
			
				

	   
			nbsInterfaceDT.setRecordStatusCd(NEDSSConstants.EDX_ACTIVITY_SUCCESS);
			nbsInterfaceDT.setRecordStatusTime(time);
			nbsInterfaceDAOImpl.updateNbsInterfaceDT(nbsInterfaceDT);
			
			
	
			
			
		} catch (EJBException e) {
			logger.fatal("EJBException raised in createNbsDocumentVO:  "+e.getMessage() + e);
			throw new NEDSSAppException(e.getMessage(),e);
		} catch (NEDSSSystemException e) {
			logger.fatal("NEDSSSystemException raised in createNbsDocumentVO "
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(), e);
		} catch (NEDSSConcurrentDataException e) {
			logger.fatal("NEDSSConcurrentDataException raised in createNbsDocumentVO "
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),	e);
		} catch (NEDSSException e) {
			logger.fatal("NEDSSException raised in createNbsDocumentVO " + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(), e);
		} catch (CreateException e) {
			logger.fatal("CreateException raised in createNbsDocumentVO " + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.fatal("Exception raised in createNbsDocumentVO " + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
		return nBSDocumentVO;
	}

	public void createEDXActivityLog(NbsInterfaceDT nbsInterfaceDT,
			EDXActivityLogDT eDXActivityLogDT, NBSSecurityObj nbsSecurityObj)
			throws RemoteException {
		try {
			NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
			EDXActivityLogDAOImpl eDXActivityLogDAOImpl = new EDXActivityLogDAOImpl();
			nbsInterfaceDAOImpl.updateNbsInterfaceDT(nbsInterfaceDT);
			eDXActivityLogDT.setEdxActivityLogUid(eDXActivityLogDAOImpl.insertEDXActivityLogDT(eDXActivityLogDT));
			
			// this inserts details
			eDXActivityLogDAOImpl.insertEDXActivityLog(eDXActivityLogDT);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public Boolean getInvestigationAssoWithDocumentColl(Long nbsDocuUid,
			NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		Boolean isInvAsso;
		NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();
		try {
			isInvAsso = new Boolean(
					nbsDocDAO.getInvestigationAssoWithDocumentColl(nbsDocuUid));
		} catch (Exception e) {
			logger.fatal("Error in getInvestigationAssoWithDocumentColl() "+e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(), e);
		}

		return isInvAsso;
	}

	public void transferOwnership(Long documentUID, String newProgramAreaCode,
			String newJurisdictionCode, NBSSecurityObj nbsSecurityObj)
			throws RemoteException, NEDSSAppException {
		try {
			NBSDocumentVO nbSOldDocumentVO = getNBSDocument(documentUID,
					nbsSecurityObj);

			if (newJurisdictionCode != null)
				nbSOldDocumentVO.getNbsDocumentDT().setJurisdictionCd(
						newJurisdictionCode);

			if (newProgramAreaCode != null)
				nbSOldDocumentVO.getNbsDocumentDT().setProgAreaCd(
						newProgramAreaCode);
			if (nbSOldDocumentVO.getNbsDocumentDT().getRecordStatusCd()
					.equals(NEDSSConstants.OBS_UNPROCESSED))
				nbSOldDocumentVO.setFromSecurityQueue(true);
			updateDocumentWithOutthePatient(nbSOldDocumentVO, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("Error in transferOwnership "+e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(), e);
		}

	}
	
	private boolean documentIsCase(String xml) throws NEDSSAppException{
		try {
			ContainerDocument containerDoc = (ContainerDocument)XmlObject.Factory.parse(xml);
			Container container = containerDoc.getContainer();
			String containerType = container.type.getSourceName();
			if (container.isSetCase()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.fatal("Unable to process ContainerDocument"+e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}
	
	private boolean documentIsHL7LabReport(String xml) throws NEDSSAppException {
		try {
			ContainerDocument containerDoc = (ContainerDocument)XmlObject.Factory.parse(xml);
			Container container = containerDoc.getContainer();
			String containerType = container.type.getSourceName();
			if (container.isSetHL7LabReport()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.fatal("Unable to process ContainerDocument"+e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	private Map<Object, Object> retrieveXMLSchemaLocation(String xml) {
		try {			
			XmlObject xobj = XmlObject.Factory.parse(xml);
			XmlCursor cursor = xobj.newCursor();
			String schemaLocation = "";
			Map<Object, Object> returnMap = new HashMap<Object, Object>();

			if (cursor.toFirstChild()) {
				schemaLocation = cursor.getAttributeText(new QName(
						"http://www.w3.org/2001/XMLSchema-instance",
						"schemaLocation"));
			}

			cursor.dispose();
			returnMap.put(NEDSSConstants.XML_SCHEMA, schemaLocation);
			returnMap.put(NEDSSConstants.XML_PAYLOAD, xobj);
			return returnMap;

		} catch (Exception e) {
			logger.fatal("Error while parsing and retreiving schema Location from XML "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}

	}

	private NBSDocumentMetadataDT getDocumentMetadataUsingSchemaLocationAndDocTypeCd(String schemaLocation) {
		try {
			Map metaDataBySchemaLocationMap = QuestionsCache.getNBSDocMetadataMapBySchemaLocation();
			
			if (metaDataBySchemaLocationMap.containsKey(schemaLocation))
				return (NBSDocumentMetadataDT) metaDataBySchemaLocationMap
						.get(schemaLocation);
			else {
				String errString = "Error in getDocumentMetadataUsingSchemaLocation() - attempting to get NBSDocumentMetadataDT for schemaLocation = "
						+ schemaLocation;
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	public Collection<Object> getUnProcessedDocuement(
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			NEDSSAppException {
		try {
			NbsDocumentDAOImpl nbsDocumentDAO = new NbsDocumentDAOImpl();
			Collection<Object> unprocessedDocuementCollection = nbsDocumentDAO
					.getUnprocessesNBSDcoumentDTColl();
			return unprocessedDocuementCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public ArrayList<MsgXMLMappingDT> getXMLMapping(String documentType,
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			NEDSSAppException {
		try {
			NbsInterfaceDAOImpl nbsInterfaceDAO = new NbsInterfaceDAOImpl();
			ArrayList<MsgXMLMappingDT> xmlMapping = nbsInterfaceDAO.getOriginalDocumentXMLMapping(documentType) ;
			return xmlMapping;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public void updateDocumentAsProcessed(NBSDocumentDT nbsDocumentDT,
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			NEDSSAppException {
		try {
			NbsDocumentDAOImpl nbsDocumentDAO = new NbsDocumentDAOImpl();
			nbsDocumentDAO.updateNbsDocument(nbsDocumentDT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public void updateNBSInterface (NbsInterfaceDT nbsInterfaceDT,
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			NEDSSAppException {
		try {
			NbsInterfaceDAOImpl nbsInterfaceDAO = new NbsInterfaceDAOImpl();
			nbsInterfaceDAO.updateNbsInterfaceDT(nbsInterfaceDT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public void parseOriginalXML(ArrayList<MsgXMLMappingDT> eICRMappingTable, NbsInterfaceDT nbsInterfaceDT,
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			NEDSSAppException {
		try {
			GenericXMLParser genericXMLParser = new GenericXMLParser();
			genericXMLParser.parseXML(eICRMappingTable, nbsInterfaceDT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	public Long createEDXDocument(EDXDocumentDT edxDocumentDT, 
			NBSSecurityObj nbsSecurityObj)  throws RemoteException,	NEDSSAppException{
		try {
			EDXDocumentDAOImpl dao = new EDXDocumentDAOImpl();
			return dao.insertEDXDocument(edxDocumentDT);
		} catch (Exception e) {
			logger.fatal("Exception : "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public void createEDXEventProcess(EDXEventProcessDT edxEventProcessDT, NBSSecurityObj nbsSecurityObj)  throws RemoteException,	NEDSSAppException{
		try {
			NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
			documentDAO.insertEventProcessDTs(edxEventProcessDT);
		} catch (Exception e) {
			logger.fatal("Exception : "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	
	public EDXEventProcessDT getEDXEventProcessDTBySourceIdandEventType(String sourceId, String eventType, NBSSecurityObj nbsSecurityObj)  throws RemoteException,	NEDSSAppException{
		try {
			NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
			return documentDAO.getEDXEventProcessDTBySourceIdandEventType(sourceId, eventType);
		} catch (Exception e) {
			logger.fatal("Exception : "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	
	public String getPersonLocalIdBySourceIdandEventType(String sourceId, String eventType, NBSSecurityObj nbsSecurityObj)  throws RemoteException,	NEDSSAppException{
		try {
			NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
			return documentDAO.getPersonLocalIdBySourceIdandEventType(sourceId, eventType);
		} catch (Exception e) {
			logger.fatal("Exception : "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
}
