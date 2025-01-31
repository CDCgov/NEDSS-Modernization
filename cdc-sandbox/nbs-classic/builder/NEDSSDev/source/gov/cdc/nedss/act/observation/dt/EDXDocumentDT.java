package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import java.util.Collection;

import gov.cdc.nedss.util.AbstractVO;

public class EDXDocumentDT extends AbstractVO {

	private static final long serialVersionUID = 1L;

	private Long eDXDocumentUid;
	
	private Long actUid;

	private String payload;

	private String recordStatusCd;

	private Timestamp recordStatusTime;

	private Timestamp addTime;

	private String docTypeCd;

	private Long nbsDocumentMetadataUid;
	
	private String documentViewXsl;
	
	private String xmlSchemaLocation;

	private String progAreaCd = null;

	private String jurisdictionCd = null;

	private Long programJurisdictionOid = null;

	private String sharedInd = null;

	private boolean itDirty = false;

	private boolean itNew = false;

	private boolean itDelete = false;

	private Long edxDocumentParentUid;
	
	private String originalPayload;
	
	private String originalDocTypeCd;
	
	private String versionNbr;
	
	private String viewLink;
	
	/**
	 * this is empty constructor
	 * 
	 */
	public EDXDocumentDT() {

	}

	public EDXDocumentDT(Long eDXDocumentUid, Long actUid,

	String payload,

	String recordStatusCd,

	Timestamp recordStatusTime,

	Timestamp addTime,

	String docTypeCd,

	Long nbsDocumentMetadataUid,
	
	String documentViewXsl,

	String xmlSchemaLocation,
	
	String progAreaCd,

	String jurisdictionCd,

	Long programJurisdictionOid,

	String sharedInd,

	boolean itDirty,

	boolean itNew,

	boolean itDelete,

	Collection<Object> theObsValueCodedModDTCollection

	) {
		this.eDXDocumentUid = eDXDocumentUid;

		this.actUid = actUid;

		this.payload = payload;

		this.recordStatusCd = recordStatusCd;

		this.recordStatusTime = recordStatusTime;

		this.addTime = addTime;

		this.docTypeCd = docTypeCd;

		this.nbsDocumentMetadataUid = nbsDocumentMetadataUid;
		
		this.documentViewXsl = documentViewXsl;
		
		this.xmlSchemaLocation = xmlSchemaLocation;
		
		this.progAreaCd = progAreaCd;

		this.jurisdictionCd = jurisdictionCd;

		this.programJurisdictionOid = programJurisdictionOid;

		this.sharedInd = sharedInd;

		this.itDirty = itDirty;

		this.itNew = itNew;

		this.itDelete = itDelete;

	}

	public Long getEDXDocumentUid() {
		return eDXDocumentUid;
	}

	public void setEDXDocumentUid(Long eDXDocumentUid) {
		this.eDXDocumentUid = eDXDocumentUid;
	}

	public Long getActUid() {
		return actUid;
	}

	public void setActUid(Long actUid) {
		this.actUid = actUid;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public String getDocTypeCd() {
		return docTypeCd;
	}

	public void setDocTypeCd(String docTypeCd) {
		this.docTypeCd = docTypeCd;
	}

	public Long getNbsDocumentMetadataUid() {
		return nbsDocumentMetadataUid;
	}

	public void setNbsDocumentMetadataUid(Long nbsDocumentMetadataUid) {
		this.nbsDocumentMetadataUid = nbsDocumentMetadataUid;
	}

	public String getProgAreaCd() {
		return progAreaCd;
	}

	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}

	public String getJurisdictionCd() {
		return jurisdictionCd;
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
	}

	public Long getProgramJurisdictionOid() {
		return programJurisdictionOid;
	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		this.programJurisdictionOid = programJurisdictionOid;
	}

	public String getSharedInd() {
		return sharedInd;
	}

	public void setSharedInd(String sharedInd) {
		this.sharedInd = sharedInd;
	}

	public boolean isItDirty() {
		return itDirty;
	}

	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	public boolean isItNew() {
		return itNew;
	}

	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	public boolean isItDelete() {
		return itDelete;
	}

	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	public String getDocumentViewXsl() {
		return documentViewXsl;
	}

	public void setDocumentViewXsl(String documentViewXsl) {
		this.documentViewXsl = documentViewXsl;
	}
	
	public String getXmlSchemaLocation() {
		return xmlSchemaLocation;
	}

	public void setXmlSchemaLocation(String xmlSchemaLocation) {
		this.xmlSchemaLocation = xmlSchemaLocation;
	}
	
	public Long getEdxDocumentParentUid() {
		return edxDocumentParentUid;
	}

	public void setEdxDocumentParentUid(Long edxDocumentParentUid) {
		this.edxDocumentParentUid = edxDocumentParentUid;
	}

	public String getOriginalPayload() {
		return originalPayload;
	}

	public void setOriginalPayload(String originalPayload) {
		this.originalPayload = originalPayload;
	}

	public String getOriginalDocTypeCd() {
		return originalDocTypeCd;
	}

	public void setOriginalDocTypeCd(String originalDocTypeCd) {
		this.originalDocTypeCd = originalDocTypeCd;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getVersionNbr() {
		return versionNbr;
	}

	public void setVersionNbr(String versionNbr) {
		this.versionNbr = versionNbr;
	}

	public String getViewLink() {
		return viewLink;
	}

	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}

}
