package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.entityid.dt.EntityDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.EthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.HL7CEType;
import gov.cdc.nedss.phdc.HL7CWEType;
import gov.cdc.nedss.phdc.HL7CXType;
import gov.cdc.nedss.phdc.HL7ContinuationPointerType;
import gov.cdc.nedss.phdc.HL7DRType;
import gov.cdc.nedss.phdc.HL7FNType;
import gov.cdc.nedss.phdc.HL7HDType;
import gov.cdc.nedss.phdc.HL7LabReportType;
import gov.cdc.nedss.phdc.HL7MSHType;
import gov.cdc.nedss.phdc.HL7NK1Type;
import gov.cdc.nedss.phdc.HL7NTEType;
import gov.cdc.nedss.phdc.HL7OrderObservationType;
import gov.cdc.nedss.phdc.HL7PATIENTRESULTType;
import gov.cdc.nedss.phdc.HL7PID1Type;
import gov.cdc.nedss.phdc.HL7PIDType;
import gov.cdc.nedss.phdc.HL7SoftwareSegmentType;
import gov.cdc.nedss.phdc.HL7VisitType;
import gov.cdc.nedss.phdc.HL7XADType;
import gov.cdc.nedss.phdc.HL7XPNType;
import gov.cdc.nedss.phdc.HL7XTNType;
import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.ELRXRefDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.Hl7ToNBSObjectConverter;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

/**
 * 
 * @author Pradeep Kumar Sharma
 * 
 */
public class HL7XmlTypeToNBSObject {
	static final LogUtils logger = new LogUtils(
			HL7XmlTypeToNBSObject.class.getName());

	
}
