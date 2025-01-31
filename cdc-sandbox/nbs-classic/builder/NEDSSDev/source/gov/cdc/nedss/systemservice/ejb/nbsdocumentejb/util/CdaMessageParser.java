package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.phdc.CaseType;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.HeaderType;
import gov.cdc.nedss.phdc.HierarchicalDesignationType;
import gov.cdc.nedss.phdc.IdentifierType;
import gov.cdc.nedss.phdc.IdentifiersType;
import gov.cdc.nedss.phdc.NameType;
import gov.cdc.nedss.phdc.PatientType;
import gov.cdc.nedss.phdc.PostalAddressType;
import gov.cdc.nedss.phdc.SectionHeaderType;
import gov.cdc.nedss.phdc.TelephoneType;
import gov.cdc.nedss.systemservice.dao.NBSDocumentMetadataDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.EdxCDAConstants;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.phdc.cda.AD;
import gov.cdc.nedss.phdc.cda.AdxpCity;
import gov.cdc.nedss.phdc.cda.AdxpPostalCode;
import gov.cdc.nedss.phdc.cda.AdxpState;
import gov.cdc.nedss.phdc.cda.AdxpStreetAddressLine;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.EnFamily;
import gov.cdc.nedss.phdc.cda.EnGiven;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.ON;
import gov.cdc.nedss.phdc.cda.PN;
import gov.cdc.nedss.phdc.cda.POCDMT000040ClinicalDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040InfrastructureRootTypeId;
import gov.cdc.nedss.phdc.cda.POCDMT000040Organization;
import gov.cdc.nedss.phdc.cda.POCDMT000040Patient;
import gov.cdc.nedss.phdc.cda.POCDMT000040PatientRole;
import gov.cdc.nedss.phdc.cda.POCDMT000040RecordTarget;
import gov.cdc.nedss.phdc.cda.POCDMT000040SubstanceAdministration;
import gov.cdc.nedss.phdc.cda.TEL;
import gov.cdc.nedss.phdc.cda.TS;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

public class CdaMessageParser extends NbsXMLMessageParserBase {
	static final LogUtils logger = new LogUtils(CdaMessageParser.class.getName());

	private ClinicalDocumentDocument1 clinicalDocumentRoot;
	private POCDMT000040ClinicalDocument1 clinicalDocument;
	private POCDMT000040RecordTarget[] recordTargetArray;
	private HeaderType phdcHeader;
	private CaseType phdcCase;
	private PatientType phdcPatient;
	private NBSDocumentDT nbsDocumentDT;

	public CdaMessageParser(String xmlMessage) throws NEDSSException {
		super(xmlMessage);

		try {
			clinicalDocumentRoot = ClinicalDocumentDocument1.Factory.parse(xmlMessage);
			clinicalDocument = clinicalDocumentRoot.getClinicalDocument();
			recordTargetArray = clinicalDocument.getRecordTargetArray();
		} catch (Exception e) {
			String errString = "CdaMessageParser constructor failed parsing CDA message:  " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}

		this.phdcHeader = this.getEmptyPHDCHeader();
		this.phdcCase = this.getEmptyPHDCCase();
		this.phdcPatient = this.getPHDCCasePatient();

		nbsDocumentDT = new NBSDocumentDT();
	}

	public NBSDocumentDT parseXMLMessage() throws NEDSSException {
		try {
			// Create new Public Health Document Container Message for Case data
			parseCDAXml();
			return nbsDocumentDT;
		} catch (ClassCastException e) {
			e.printStackTrace();
			logger.error("XMLTypeToNBSObject.createNBSDocumentVO  "+e.getMessage());
			throw new NEDSSException(e.getMessage(), e);
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.error("XMLTypeToNBSObject.createNBSDocumentVO  "+e.getMessage());
			throw new NEDSSException(e.getMessage(), e);
		} catch (CreateException e) {
			e.printStackTrace();
			logger.error("XMLTypeToNBSObject.createNBSDocumentVO  "+e.getMessage());
			throw new NEDSSException(e.getMessage(), e);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("XMLTypeToNBSObject.createNBSDocumentVO  "+e.getMessage());
			throw new NEDSSException(e.getMessage(), e);
		}

	}

	private NBSDocumentDT parseCDAXml() throws Exception
	{
		try
		{
			this.parseEffectiveTime();

			String cdaCode = this.parseCDACode();

			this.setPHDCCaseDocumentType();
			this.setPHDCCasePurpose();
			this.parseSendingAppEventId();
			
			this.parseCondition(cdaCode);
			nbsDocumentDT.setNbsDocumentMetadataUid(this.lookupNbsDocumentMetadataUid(cdaCode));
			
			this.parsePatient();
			this.parseTreatmentId();
			
			nbsDocumentDT.setPhdcDocDerivedTxt(this.getPHDCXMLString());
			return nbsDocumentDT;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw new Exception("Exception in parseCDAXml", e);
		} 
	}

	private void parseEffectiveTime() {
		// Effective time
		TS effectiveTime = clinicalDocument.getEffectiveTime();
		String effectiveTimeString = effectiveTime.getValue();
		Calendar calEffectivTime = Calendar.getInstance();
		try {
			if (effectiveTimeString != null) {
				if (effectiveTimeString.length() == 8) {
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					Date date = df.parse(effectiveTimeString);
					calEffectivTime.setTime(date);
				} else if (effectiveTimeString.length() == 19) {
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssZ");
					Date date = df.parse(effectiveTimeString);
					calEffectivTime.setTime(date);
				}
			}
		} catch (ParseException e) {
				String answer = effectiveTimeString == null ? "" : effectiveTimeString;
				String errString = "CdaMessageParser.parseEffectiveTime:  Invalid date:  '" +  answer + "':  " + e.getMessage();
				logger.error(errString);
				throw new NEDSSSystemException(errString, e);
		}
		phdcHeader.setCreationTime(calEffectivTime);  //Set to effective time, if not available or invalid, then current time is used
	}

	private String parseCDACode() {
		CE code = clinicalDocument.getCode();
		if (code != null) {
			SectionHeaderType phdcCaseSectionHeader = getPHDCCaseSectionHeader();
			phdcCaseSectionHeader.setDescription(code.getDisplayName());
		}
		
		return code.getCode();
	}

	private void setPHDCCaseDocumentType() {
		SectionHeaderType phdcCaseSectionHeader = this.getPHDCCaseSectionHeader();

		CodedType phdcCaseDocumentType = phdcCaseSectionHeader.getDocumentType();
		
		Coded documentTypeCode = new Coded(NEDSSConstants.PHC_236, "Code_value_general", "PUBLIC_HEALTH_EVENT");
		phdcCaseDocumentType.setCode(documentTypeCode.getCode());
		phdcCaseDocumentType.setCodeDescTxt(documentTypeCode.getCodeDescription());
		phdcCaseDocumentType.setCodeSystemCode(documentTypeCode.getCodeSystemCd());
	}
	
	private void setPHDCCasePurpose() {
		SectionHeaderType phdcCaseSectionHeader = this.getPHDCCaseSectionHeader();

		// Currently "hard coded" to this type - SHARE_NOTF
		nbsDocumentDT.setDocPurposeCd(NEDSSConstants.CLASS_CD_SHARE_NOTF);
		CodedType phdcCasePurpose = phdcCaseSectionHeader.getPurpose();
		Coded purposeCode = new Coded(NEDSSConstants.CLASS_CD_SHARE_NOTF, "Code_value_general", "NBS_DOC_PURPOSE");
		phdcCasePurpose.setCode(purposeCode.getCode());
		phdcCasePurpose.setCodeDescTxt(purposeCode.getCodeDescription());
		phdcCasePurpose.setCodeSystemCode(purposeCode.getCodeSystemCd());
	}
	
	private void parseCondition(String code) {
		CodedType phdcCondition = phdcCase.getCondition();
		phdcCondition.setCode(NEDSSConstants.NOINFORMATIONGIVEN_CODE);
		phdcCondition.setCodeDescTxt(NEDSSConstants.NOINFORMATIONGIVEN);
		phdcCondition.setCodeSystemCode(NEDSSConstants.UNDEFINED);

		if (code == null) {
			String errString = "CdaMessageParser.parseCDAXml:  Code is missing, can't determine type of CDA.";
			logger.warn(errString);
			// throw new NEDSSSystemException(errString);

			return;
		}

		if (code.equalsIgnoreCase(NEDSSConstants.CDA_PHDC_CODE)) {
			String conditionCd = null;

			// Query for condition
			II[] templateIdArray = clinicalDocument.getTemplateIdArray();
			if (templateIdArray != null && templateIdArray.length != 0) {
				/*
				 * String conditionCd = "NI";
				 * phdcCondition.setCodeDescTxt(NEDSSConstants
				 * .NOINFORMATIONGIVEN);
				 */
				String root = ((II) templateIdArray[0]).getRoot();

				// TODO: Do database look up against Condition_cd table
				if (root != null) {
					if (root.equalsIgnoreCase("2.16.840.1.113883.10.20.15.1.2")) {
						conditionCd = "10100";
					} else if (root
							.equalsIgnoreCase("2.16.840.1.113883.10.20.15.1.1")) {
						conditionCd = "10220";
					} else if (root
							.equalsIgnoreCase("2.16.840.1.113883.10.20.15.1.3")) {
						conditionCd = "10350";
					} else if (root
							.equalsIgnoreCase("2.16.840.1.113883.10.20.15.1.4")) {
						conditionCd = "10230";
					} else
						conditionCd = "10190";
				}

			} else
				conditionCd = "10190";
			if (conditionCd != null) {
				Coded conditionCode = new Coded(conditionCd,
						"v_Condition_code", "PHC_TYPE");
				phdcCondition.setCode(conditionCode.getCode());
				phdcCondition
						.setCodeDescTxt(conditionCode.getCodeDescription());
				phdcCondition
						.setCodeSystemCode(conditionCode.getCodeSystemCd());
			}
		}

		return;
	}

	private Long lookupNbsDocumentMetadataUid(String code) {
		NBSDocumentMetadataDAOImpl nbsDocumentMetadataDAO = new NBSDocumentMetadataDAOImpl();

		if (code != null && code.equalsIgnoreCase(NEDSSConstants.CDA_PHDC_CODE)) {
			// Get PHCR NBS_document_metadata row
			return nbsDocumentMetadataDAO.getNBSDocumentMetadataUIDByDocTypeCd(NEDSSConstants.CDA_PHDC_TYPE);
		} else {
			// Get generic CDA NBS_document_metadata row
			return  nbsDocumentMetadataDAO.getNBSDocumentMetadataUIDByDocTypeCd(NEDSSConstants.CDA_TYPE);
		}
	}
	
	private void parseSendingAppEventId() {
		POCDMT000040InfrastructureRootTypeId  typeId = clinicalDocument.getTypeId();
		String extensionString=null;
	
		if (typeId != null) {
			extensionString = typeId.getExtension();
			phdcCase.getSectionHeader().setSendingApplicationEventIdentifier(extensionString);
		}
	}

	private void parsePatient() {
		// Patient - Parse out patient into PHDC XML to create MPR/Revision for Document to be imported.
		if (recordTargetArray.length > 0) {
			POCDMT000040PatientRole patientRole = recordTargetArray[0].getPatientRole();
			POCDMT000040Patient patient = patientRole.getPatient();
			
			parsePatientName(patient);
			parsePatientBirthdate(patient);			
			parseGender(patient);
			parseEthnicity(patient);
			parseRace();
			parsePatientId(patientRole);
			parsePatientAddress(patientRole);
			parsePatientTelephone(patientRole);
			parseProviderOrganization(patientRole);
		}
	}

    private void parsePatientName(POCDMT000040Patient patient) {
		PN patientName = null;
		PN[] patientNameArray = patient.getNameArray();
		if (patientNameArray.length > 0) {
			NameType phdcPatientName = phdcPatient.addNewName();

			int nameArray = 0;
			while (patientName == null && nameArray < patientNameArray.length) { // Look until we find a name with an "L" use code
				patientName = patientNameArray[nameArray++];
				List patientUseList = patientName.getUse();

				if (patientUseList != null) {
					Iterator patientUseIter = patientUseList.iterator();
					boolean legalUseFound = false;
					while (patientUseIter.hasNext() && !legalUseFound) {
						String useString = (String)(patientUseIter.next());
						if ( useString.equalsIgnoreCase("L") )
							legalUseFound = true;
					}
					if (!legalUseFound)
						patientName = null;
				} else {
					patientName = null;  // No use code in this name
				}
			}
			
			// If no "L" use code was found, use first element in array
			if (patientName == null)
				patientName = patientNameArray[0];
			
			EnGiven[] givenNameArray = patientName.getGivenArray();
			if (givenNameArray.length > 0) {
				EnGiven givenName = givenNameArray[0];
				phdcPatientName.setFirst(givenName.newCursor().getTextValue());
			} else { // No first name is available
				// throw exception
			}

			EnFamily[] familyNameArray = patientName.getFamilyArray();
			if (familyNameArray.length > 0) {
				EnFamily familyName = familyNameArray[0];
				phdcPatientName.setLast(familyName.newCursor().getTextValue());
			} else { // No last name is available
				// throw exception
			}

		} else { // No name is available
			// throw exception
		}
    }
	
    private void parsePatientBirthdate(POCDMT000040Patient patient) {
		TS birthTime = patient.getBirthTime();

		if (birthTime != null) {
			String patientBirthdate = birthTime.getValue();
			Calendar calBirthdate = Calendar.getInstance();

			try {
				if (patientBirthdate != null) {
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					Date date = df.parse(patientBirthdate);
					calBirthdate.setTime(date);
					phdcPatient.setDateOfBirth(calBirthdate);
				}
			} catch (ParseException e) {
					String answer = patientBirthdate == null ? "" : patientBirthdate;
					String errString = "CdaMessageParser.parseCDAXml:  Invalid date:  '" + 
									   answer + "':  " + e.getMessage();
					logger.error(errString);
					throw new NEDSSSystemException(errString, e);
			}
		}
    }

    private void parseGender(POCDMT000040Patient patient) {
		CE admistrativeGenderCode = patient.getAdministrativeGenderCode();
		if (admistrativeGenderCode != null) {
			CodedType phdcPatientSexCode = phdcPatient.addNewSex(); 
			phdcPatientSexCode.setCode(admistrativeGenderCode.getCode());
			if (admistrativeGenderCode.getDisplayName() != null) 
				phdcPatientSexCode.setCodeDescTxt(admistrativeGenderCode.getDisplayName());
			else
				phdcPatientSexCode.setCodeDescTxt(NEDSSConstants.UNDEFINED);
			phdcPatientSexCode.setCodeSystemCode(admistrativeGenderCode.getCodeSystem());
		}
    }
    
	private void parseEthnicity(POCDMT000040Patient patient) {
		CE ethnicGroupCode = patient.getEthnicGroupCode();
		if (ethnicGroupCode != null) {
			CodedType phdcPatientEthnicityCode = phdcPatient.addNewEthnicity();
			phdcPatientEthnicityCode.setCode(ethnicGroupCode.getCode());
			phdcPatientEthnicityCode.setCodeDescTxt(ethnicGroupCode.getDisplayName());
			phdcPatientEthnicityCode.setCodeSystemCode(ethnicGroupCode.getCodeSystem());
		}
	}
	
	private void parseRace() {
		QName rootAttribute = new QName("root");
	    QName codeAttribute = new QName("code");
	    QName codeSystemAttribute = new QName("codeSystem");
	    QName displayNameAttribute = new QName("displayName");
	
	    XmlObject[] children = clinicalDocument.selectPath(EdxCDAConstants.CDA_NAMESPACE + EdxCDAConstants.CDA_NAMESPACE);
	
	    for (int i= 0; i < children.length; i++) {
	    	XmlObject observation = (XmlObject)children[i];
	    	XmlCursor observationCursor = observation.newCursor();
	    	
	    	observationCursor.toChild(0);
	    	String rootString = observationCursor.getAttributeText(rootAttribute);
	    	if (rootString != null && rootString.equalsIgnoreCase("2.16.840.1.113883.10.20.15.3.9")) {
	    		observationCursor.toParent();
	    		observationCursor.toChild(3);
	    		String raceCode = observationCursor.getAttributeText(codeAttribute);
	    		String raceCodeDisplayName = observationCursor.getAttributeText(displayNameAttribute);
	    		String raceCodeSystem = observationCursor.getAttributeText(codeSystemAttribute);
	    		if (raceCode != null) {
	    			CodedType raceCodedType = phdcPatient.addNewRace();
	    			raceCodedType.setCode(raceCode);
	    			raceCodedType.setCodeDescTxt(raceCodeDisplayName);
	    			raceCodedType.setCodeSystemCode(raceCodeSystem);
	    		}
	    	}		    	
	    	observationCursor.dispose();
	    }
	}
	
	private void parseTreatmentId() {
		ArrayList<Object> idList = new ArrayList<Object>();
		XmlObject[] children = clinicalDocument
				.selectPath(EdxCDAConstants.CDA_NAMESPACE + EdxCDAConstants.CDA_STRUCTURED_XML_ENRTY_SA);

		for (int i = 0; i < children.length; i++) {
			POCDMT000040SubstanceAdministration substanceAdministration = (POCDMT000040SubstanceAdministration) children[i];
			if (substanceAdministration.getIdArray() != null
					&& substanceAdministration.getIdArray().length > 0) {
				String idString = substanceAdministration.getIdArray(0)
						.getExtension();
				if (idString != null)
					idList.add(idString);
			}
		}
		nbsDocumentDT.getEventIdMap().put(NEDSSConstants.TREATMENT_ACT_TYPE_CD,
				idList);
	}

    private void parsePatientId(POCDMT000040PatientRole patientRole) {
		II[] patientIdArray = patientRole.getIdArray();
		II patientId = null;
		if (patientIdArray != null) {
			patientId = patientIdArray[0];
			IdentifiersType identifiers = phdcPatient.addNewIdentifiers();
			IdentifierType patientIdentifier = identifiers.addNewIdentifier();
			HierarchicalDesignationType aType = patientIdentifier.addNewAssigningAuthority();
			patientIdentifier.setIDNumber(patientId.getExtension());
			patientIdentifier.setIDTypeCode(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID);
			aType.setNamespaceID("NBS");
			aType.setUniversalID("NEDSS Base System");
			aType.setUniversalIDType("L");
			//TODO:  Populate other IdentifierType fields
		}
	}

    private void parseProviderOrganization(POCDMT000040PatientRole patientRole) {
    	POCDMT000040Organization providerOrganization = patientRole.getProviderOrganization();
		if (providerOrganization != null) {
			ON[] providerOrganizationNameArray = providerOrganization.getNameArray();
			if (providerOrganizationNameArray != null) { 
				ON providerOrganizationName = providerOrganizationNameArray[0];
				String name = providerOrganizationName.newCursor().getTextValue();
				phdcHeader.getSendingFacility().setNamespaceID(name);
			}
		}
	}

    private void parsePatientAddress(POCDMT000040PatientRole patientRole) {
		AD[] addressArray = patientRole.getAddrArray();
		if (addressArray.length > 0) {
			PostalAddressType phdcPostalAddress = phdcPatient.addNewPostalAddress();
			AD address = addressArray[0];
			
			AdxpStreetAddressLine[] streetAddressLineArray = address.getStreetAddressLineArray();
			if (streetAddressLineArray.length > 0) {
				AdxpStreetAddressLine streetAddressLine = streetAddressLineArray[0];
				phdcPostalAddress.setStreetAddressOne(streetAddressLine.newCursor().getTextValue());
			}
		
			AdxpCity[] cityArray = address.getCityArray();
			if (cityArray.length > 0) {
				AdxpCity city = cityArray[0];
				phdcPostalAddress.setCity(city.newCursor().getTextValue());
			}
			
			AdxpState[] stateArray = address.getStateArray();
			if (stateArray.length > 0) {
				CodedType phdcPostalAddressState = phdcPostalAddress.addNewState();
				AdxpState state = stateArray[0];
				String patientState = state.newCursor().getTextValue();
		        CachedDropDownValues cdv = new CachedDropDownValues();
		        Coded stateCode = new Coded(cdv.getStateCodeByAbbreviation(patientState), DataTables.STATE_CODE_VIEW, "STATE_CCD");
				phdcPostalAddressState.setCode(stateCode.getCode());
				phdcPostalAddressState.setCodeDescTxt(stateCode.getCodeDescription());
				phdcPostalAddressState.setCodeSystemCode(stateCode.getCodeSystemCd());
			}
			AdxpPostalCode[] postalCodeArray = address.getPostalCodeArray();
			if (postalCodeArray.length > 0) {
				AdxpPostalCode postalCode = postalCodeArray[0];
				phdcPostalAddress.setZipCode(postalCode.newCursor().getTextValue());
			}					
		}
    }

	private void parsePatientTelephone(POCDMT000040PatientRole patientRole) {
		TEL[] telecomArray = patientRole.getTelecomArray();
		if (telecomArray.length > 0) {
			TelephoneType phdcTelephone = phdcPatient.addNewTelephone();
			TEL telecom = telecomArray[0];
			String telecomString = telecom.getValue();


			if (telecomString != null) 					
				this.parseTelephone(telecomString, phdcTelephone);
		}
	}
   
    private void parseTelephone(String telecomString, TelephoneType phdcTelephone) {
		// Following will parse:
		// tel:(999)555-1212 -> 999-555-1212
		// tel:(999)555-1212;ext=9999 -> 999-555-1212 and 9999
		// tel:+1-999-555-1212 -> 999-555-1212
		// tel:+1-999-555-1212;ext=9999 -> 999-555-1212

    	// Telephone Number parsing
		String parsetelephoneNumber;
		
		// If we have a '+' then there is a country code we want to drop
		if (telecomString.indexOf("+") > 0 && telecomString.indexOf("-") > telecomString.indexOf("+")) 
			parsetelephoneNumber = telecomString.substring(telecomString.indexOf("-") + 1);
		else 
			parsetelephoneNumber = telecomString.substring(telecomString.indexOf(":") + 1);
			
		// If we have a ';' it is at end of telephone number and before extension, so drop thereafter
		if (parsetelephoneNumber.indexOf(";") > 0)
			parsetelephoneNumber = parsetelephoneNumber.substring(0, parsetelephoneNumber.indexOf(";"));
		
		// Replace (areacode) with areacode-
		if (parsetelephoneNumber.indexOf("(") != -1)
			parsetelephoneNumber = parsetelephoneNumber.substring(parsetelephoneNumber.indexOf("(") + 1);
		if (parsetelephoneNumber.indexOf(")") > 0)
			parsetelephoneNumber =  parsetelephoneNumber.substring(0, parsetelephoneNumber.indexOf(")")) +
					                "-" + 
					                parsetelephoneNumber.substring(parsetelephoneNumber.indexOf(")") + 1);
		
		phdcTelephone.setNumber(parsetelephoneNumber);

		// Telephone Extension parsing
		if (telecomString.indexOf("ext=") > 0) {
			phdcTelephone.setExtension(telecomString.substring(telecomString.indexOf("ext=") + 4));
		}

    }
}