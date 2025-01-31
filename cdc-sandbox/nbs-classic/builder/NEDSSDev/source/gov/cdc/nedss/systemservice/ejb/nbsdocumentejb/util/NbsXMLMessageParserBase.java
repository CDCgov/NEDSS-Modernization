package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.phdc.CaseType;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.HeaderType;
import gov.cdc.nedss.phdc.HierarchicalDesignationType;
import gov.cdc.nedss.phdc.PatientType;
import gov.cdc.nedss.phdc.SectionHeaderType;
import gov.cdc.nedss.phdc.ContainerDocument.Container;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;

public abstract class NbsXMLMessageParserBase {
	private String xmlMessage;
	private ContainerDocument phdcMessageDoc;
	private Container phdcContainer;
	private HeaderType phdcHeader;
	private CaseType phdcCase;
	private PatientType phdcPatient;
	private SectionHeaderType phdcCaseSectionHeader;
	
	public NbsXMLMessageParserBase(String xmlMessage) {
		this.xmlMessage = xmlMessage;
		initPHDCMessage();
	}
	
	public abstract NBSDocumentDT parseXMLMessage() throws NEDSSException;
	
	protected String getPHDCXMLString() {
		return phdcMessageDoc.toString();
	}
	
	private void initPHDCMessage() {
		phdcMessageDoc = ContainerDocument.Factory.newInstance();
		phdcContainer = phdcMessageDoc.addNewContainer();
		phdcHeader = phdcContainer.addNewHeader();
		phdcCase = phdcContainer.addNewCase();
		phdcPatient = phdcCase.addNewPatient();
		phdcCaseSectionHeader = phdcCase.addNewSectionHeader();
	}

	protected HeaderType getPHDCHeader() {
		return phdcHeader;
	}

	protected HeaderType getEmptyPHDCHeader() {
		initializeEmptyPHDCCodedType(phdcHeader.addNewMessageType());
		initializeEmptyPHDCCodedType(phdcHeader.addNewResultStatus());
		phdcHeader.setProcessingID("");
		phdcHeader.setMessageControlID("");
		phdcHeader.setVersionID("");

		initializeEmptyPHDCHierarchicalDesignationType(phdcHeader.addNewReceivingApplication());
		initializeEmptyPHDCHierarchicalDesignationType(phdcHeader.addNewReceivingFacility());
		initializeEmptyPHDCHierarchicalDesignationType(phdcHeader.addNewSendingApplication());
		initializeEmptyPHDCHierarchicalDesignationType(phdcHeader.addNewSendingFacility());
		
		return phdcHeader;
	}
	protected CaseType getPHDCCase() {
		return phdcCase;
	}

	protected CaseType getEmptyPHDCCase() {
		initializeEmptyPHDCCodedType(phdcCase.addNewCondition());
		phdcCaseSectionHeader.setDescription("");
		phdcCaseSectionHeader.setSendingApplicationEventIdentifier("");
		initializeEmptyPHDCCodedType(phdcCaseSectionHeader.addNewDocumentType());
		initializeEmptyPHDCCodedType(phdcCaseSectionHeader.addNewPurpose());

		return phdcCase;
	}
	
	protected SectionHeaderType getPHDCCaseSectionHeader() {
		return phdcCaseSectionHeader;
	}
	
	protected PatientType getPHDCCasePatient() {
		return phdcPatient;
	}
	
	public static CodedType initializeEmptyPHDCCodedType(CodedType emptyCoded) {
		emptyCoded.setCode("");
		emptyCoded.setCodeDescTxt("");
		emptyCoded.setCodeSystemCode("");
		return emptyCoded;
	}
	
	public static HierarchicalDesignationType initializeEmptyPHDCHierarchicalDesignationType(HierarchicalDesignationType emptyHierarchicalDesignationType) {
		emptyHierarchicalDesignationType.setNamespaceID("");
		emptyHierarchicalDesignationType.setUniversalID("");
		emptyHierarchicalDesignationType.setUniversalIDType("");
		return emptyHierarchicalDesignationType;
	}

	
	
}
