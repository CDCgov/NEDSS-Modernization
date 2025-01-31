package gov.cdc.nedss.act.attachment.dt;

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Timestamp;

import org.apache.struts.upload.FormFile;

public class AttachmentDT
  extends AbstractVO
  implements RootDTInterface
{
private static final long serialVersionUID = 1L;


private Long nbsAttachmentUid;
private Long attachmentParentUid;
private String descTxt;
private String lastChangeTime;
private Long lastChangeUserId;
private String lastChangeUserName;
private FormFile attachment;
private byte[] attachmentData;
private String fileNmTxt;	
private String deleteLink;
private String viewLink;
private String typeCd;
private String lastChgUserNm;
private String statusCd;
private String inputFile;
private String attachmentPath;



/**
 * @roseuid 3E9AE7AA029F
 */
public AttachmentDT()
{

}





/**
 * sets RecordStatusTime(convenient method for struts)
 * @param strTime : String value
 */
public void setRecordStatusTime_s(String strTime)
{
  if (strTime == null)
  {
    return;
  }
  this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
}

/**
 * sets StatusTime( convenient method for struts)
 * @param strTime : String value
 */
public void setStatusTime_s(String strTime)
{
  if (strTime == null)
  {
    return;
  }
  this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
}

/**
 * @param objectname1
 * @param objectname2
 * @param voClass
 * @return boolean
 * @roseuid 3EAECC070398
 */
public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
{
  return true;
}

/**
 * @return java.lang.String
 * @roseuid 3EAECC0B0165
 */
public String getJurisdictionCd()
{
  return null;
}

/**
 * @param aJurisdictionCd
 * @roseuid 3EAECC0B03D6
 */
public void setJurisdictionCd(String aJurisdictionCd)
{

}

/**
 * @return java.lang.String
 * @roseuid 3EAECC0D02BD
 */
public String getSuperclass()
{
 return NEDSSConstants.CLASSTYPE_ACT;
}

/**
 * @return boolean
 * @roseuid 3EAECC0E01E2
 */
public boolean isItNew()
{
  return itNew;
}

/**
 * @param itNew
 * @roseuid 3EAECC0E028E
 */
public void setItNew(boolean itNew)
{
  this.itNew = itNew;
}

/**
 * @return boolean
 * @roseuid 3EAECC0E03A7
 */
public boolean isItDirty()
{
  return itDirty;
}

/**
 * @param itDirty
 * @roseuid 3EAECC0F006B
 */
public void setItDirty(boolean itDirty)
{
  this.itDirty = itDirty;
}

/**
 * @return boolean
 * @roseuid 3EAECC0F01B3
 */
public boolean isItDelete()
{
  return itDelete;
}

/**
 * @param itDelete
 * @roseuid 3EAECC0F02AD
 */
public void setItDelete(boolean itDelete)
{
  this.itDelete = itDelete;
}

@Override
public void setAddTime(Timestamp aAddTime) {
	// TODO Auto-generated method stub
	
}

@Override
public Timestamp getAddTime() {
	// TODO Auto-generated method stub
	return null;
}
















@Override
public String getProgAreaCd() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setProgAreaCd(String aProgAreaCd) {
	// TODO Auto-generated method stub
	
}













@Override
public String getLocalId() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setLocalId(String aLocalId) {
	// TODO Auto-generated method stub
	
}






@Override
public Long getAddUserId() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setAddUserId(Long aAddUserId) {
	// TODO Auto-generated method stub
	
}






@Override
public String getLastChgReasonCd() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setLastChgReasonCd(String aLastChgReasonCd) {
	// TODO Auto-generated method stub
	
}






@Override
public String getRecordStatusCd() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setRecordStatusCd(String aRecordStatusCd) {
	// TODO Auto-generated method stub
	
}






@Override
public Timestamp getRecordStatusTime() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setRecordStatusTime(Timestamp aRecordStatusTime) {
	// TODO Auto-generated method stub
	
}











@Override
public Timestamp getStatusTime() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setStatusTime(Timestamp aStatusTime) {
	// TODO Auto-generated method stub
	
}






@Override
public Long getUid() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public Long getProgramJurisdictionOid() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
	// TODO Auto-generated method stub
	
}






@Override
public String getSharedInd() {
	// TODO Auto-generated method stub
	return null;
}






@Override
public void setSharedInd(String aSharedInd) {
	// TODO Auto-generated method stub
	
}






@Override
public Integer getVersionCtrlNbr() {
	// TODO Auto-generated method stub
	return null;
}

public Long getNbsAttachmentUid() {
	return nbsAttachmentUid;
}

public void setNbsAttachmentUid(Long nbsAttachmentUid) {
	this.nbsAttachmentUid = nbsAttachmentUid;
}

public Long getAttachmentParentUid() {
	return attachmentParentUid;
}

public void setAttachmentParentUid(Long attachmentParentUid) {
	this.attachmentParentUid = attachmentParentUid;
}

public FormFile getAttachment() {
	return attachment;
}

public void setAttachment(FormFile attachment) {
	this.attachment = attachment;
}

public String getFileNmTxt() {
	return fileNmTxt;
}

public void setFileNmTxt(String fileNmTxt) {
	this.fileNmTxt = fileNmTxt;
}

public String getDeleteLink() {
	return deleteLink;
}

public void setDeleteLink(String deleteLink) {
	this.deleteLink = deleteLink;
}

public String getViewLink() {
	return viewLink;
}

public void setViewLink(String viewLink) {
	this.viewLink = viewLink;
}

public String getTypeCd() {
	return typeCd;
}

public void setTypeCd(String typeCd) {
	this.typeCd = typeCd;
}

public String getLastChgUserNm() {
	return lastChgUserNm;
}

public void setLastChgUserNm(String lastChgUserNm) {
	this.lastChgUserNm = lastChgUserNm;
}

public String getLastChangeTime() {
	return lastChangeTime;
}

public void setLastChangeTime(String lastChangeTime) {
	this.lastChangeTime = lastChangeTime;
}










public String getDescTxt() {
	return descTxt;
}








public void setDescTxt(String descTxt) {
	this.descTxt = descTxt;
}








@Override
public Timestamp getLastChgTime() {
	// TODO Auto-generated method stub
	return null;
}








@Override
public void setLastChgTime(Timestamp aLastChgTime) {
	// TODO Auto-generated method stub
	
}





public Long getLastChangeUserId() {
	return lastChangeUserId;
}





public void setLastChangeUserId(Long lastChangeUserId) {
	this.lastChangeUserId = lastChangeUserId;
}





@Override
public Long getLastChgUserId() {
	// TODO Auto-generated method stub
	return null;
}





@Override
public void setLastChgUserId(Long aLastChgUserId) {
	// TODO Auto-generated method stub
	
}





public String getStatusCd() {
	return statusCd;
}





public void setStatusCd(String statusCd) {
	this.statusCd = statusCd;
}


public String getAttachmentPath() {
	return attachmentPath;
}





public void setAttachmentPath(String attachmentPath) {
	this.attachmentPath = attachmentPath;
}





public String getInputFile() {
	return inputFile;
}





public void setInputFile(String inputFile) {
	this.inputFile = inputFile;
}





public byte[] getAttachmentData() {
	return attachmentData;
}





public void setAttachmentData(byte[] attachmentData) {
	this.attachmentData = attachmentData;
}





public String getLastChangeUserName() {
	return lastChangeUserName;
}





public void setLastChangeUserName(String lastChangeUserName) {
	this.lastChangeUserName = lastChangeUserName;
}
}
