package gov.cdc.nedss.systemservice.dt;

import java.sql.Blob;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class NBSDocumentMetadataDT extends AbstractVO implements RootDTInterface {
    private Long nbsDocumentMetadataUid;
    private String xmlSchemaLocation;
    private Blob documentViewXsl;
    private String description;
    private String docTypeCd;
    private String xmlbeanFactoryClassNm;
    private String parserClassNm;
    private String documentViewXslTxt;
    
	private Long addUserId;
	private boolean itDirty = false;
	private boolean itNew = true;
	private boolean itDelete = false;
	private String jurisdictionCd;
	private String lastChgReasonCd;
	private Long lastChgUserId;
	private Timestamp lastChgTime;
	private String localId;
	private Long programJurisdictionOid;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String statusCd;
	private String sharedInd;
	private Timestamp statusTime;
	private Timestamp addTime;
	
	
	private Blob documentViewCdaXsl;
	private String documentViewCdaXslTxt;
	
	
	public Long getNbsDocumentMetadataUid() {
		return nbsDocumentMetadataUid;
	}

	public void setNbsDocumentMetadataUid(Long nbsDocumentMetadataUid) {
		this.nbsDocumentMetadataUid = nbsDocumentMetadataUid;
	}

	public String getXmlSchemaLocation() {
		return xmlSchemaLocation;
	}

	public void setXmlSchemaLocation(String xmlSchemaLocation) {
		this.xmlSchemaLocation = xmlSchemaLocation;
	}
	
	public Blob getDocumentViewXsl() {
		return documentViewXsl;
	}

	public void setDocumentViewXsl(Blob documentViewXsl) {
		this.documentViewXsl = documentViewXsl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocTypeCd() {
		return docTypeCd;
	}

	public void setDocTypeCd(String docTypeCd) {
		this.docTypeCd = docTypeCd;
	}

	public String getXmlbeanFactoryClassNm() {
		return xmlbeanFactoryClassNm;
	}

	public void setXmlbeanFactoryClassNm(String xmlbeanFactoryClassNm) {
		this.xmlbeanFactoryClassNm = xmlbeanFactoryClassNm;
	}

	public String getParserClassNm() {
		return parserClassNm;
	}

	public void setParserClassNm(String parserClassNm) {
		this.parserClassNm = parserClassNm;
	}
	
	public String getDocumentViewXslTxt() {
		return documentViewXslTxt;
	}

	public void setDocumentViewXslTxt(String documentViewXslTxt) {
		this.documentViewXslTxt = documentViewXslTxt;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return addTime;
	}

	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return addUserId;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return jurisdictionCd;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return lastChgReasonCd;
	}

	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return lastChgTime;
	}

	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return lastChgUserId;
	}

	public String getLocalId() {
		// TODO Auto-generated method stub
		return localId;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
		
	}

	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
		
	}

	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd=jurisdictionCd;
		
	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		this.lastChgReasonCd = lastChgReasonCd;
		
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime=lastChgTime;
		
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
		
	}

	public void setLocalId(String localId) {
		this.localId = localId;
		
	}

	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		this.programJurisdictionOid = programJurisdictionOid;
		
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
		
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
		
	}

	public void setSharedInd(String sharedInd) {
		this.sharedInd = sharedInd;
		
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
		
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
		
	}

	public Blob getDocumentViewCdaXsl() {
		return documentViewCdaXsl;
	}

	public void setDocumentViewCdaXsl(Blob documentViewCdaXsl) {
		this.documentViewCdaXsl = documentViewCdaXsl;
	}

	public String getDocumentViewCdaXslTxt() {
		return documentViewCdaXslTxt;
	}

	public void setDocumentViewCdaXslTxt(String documentViewCdaXslTxt) {
		this.documentViewCdaXslTxt = documentViewCdaXslTxt;
	}

}
