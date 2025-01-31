/**
* Name:		    OrganizationNameDT
* Description:	OrganizationName data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/
package gov.cdc.nedss.entity.organization.dt;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.RootDTInterface;

import java.sql.Timestamp;
import java.util.*;


public class OrganizationNameDT
    extends AbstractVO implements RootDTInterface{
	private static final long serialVersionUID = 1L;
    private Long organizationUid;
    private Integer organizationNameSeq;
    private String nmTxt;
    private String nmUseCd;
    private String recordStatusCd;
    private String defaultNmInd;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    /**
     *
     * @return  Long
     */
    public Long getOrganizationUid() {

        return organizationUid;
    }

    /**
     *
     * @param aOrganizationUid
     */
    public void setOrganizationUid(Long aOrganizationUid) {
        organizationUid = aOrganizationUid;
        setItDirty(true);
    }

    /**
     *
     * @return  Integer
     */
    public Integer getOrganizationNameSeq() {

        return organizationNameSeq;
    }

    /**
     *
     * @param aOrganizationNameSeq
     */
    public void setOrganizationNameSeq(Integer aOrganizationNameSeq) {
        organizationNameSeq = aOrganizationNameSeq;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getNmTxt() {

        return nmTxt;
    }

    /**
     *
     * @param aNmTxt
     */
    public void setNmTxt(String aNmTxt) {
        nmTxt = aNmTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getNmUseCd() {

        return nmUseCd;
    }

    /**
     *
     * @param aNmUseCd
     */
    public void setNmUseCd(String aNmUseCd) {
        nmUseCd = aNmUseCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getRecordStatusCd() {

        return recordStatusCd;
    }

    /**
     *
     * @param aRecordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd) {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getDefaultNmInd() {

        return defaultNmInd;
    }

    /**
     *
     * @param aDefaultNmInd
     */
    public void setDefaultNmInd(String aDefaultNmInd) {
        defaultNmInd = aDefaultNmInd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getProgAreaCd() {

        return progAreaCd;
    }

    /**
     *
     * @param aProgAreaCd
     */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

    /**
     *
     * @param aJurisdictionCd
     */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     *
     * @return Long
     */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

    /**
     *
     * @param aProgramJurisdictionOid
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getSharedInd() {

        return sharedInd;
    }

    /**
     *
     * @param aSharedInd
     */
    public void setSharedInd(String aSharedInd) {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     *
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return  boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((OrganizationNameDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     *
     * @param itDirty
     */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     *
     * @return  boolean
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     *
     * @param itNew
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     *
     * @return  boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return  boolean
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     *
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

    /**
    * A getter for last change user id
    * @return Long
    */
    public Long getLastChgUserId(){
     return organizationUid;
    }

    /**
    * A setter for last change user id
    * @param aLastChgUserId
    * @roseuid 3C73D82701FD
    */
    public void setLastChgUserId(Long aLastChgUserId){
    }

    /**
    * A getter for last change time
    * @return java.sql.Timestamp
    * @roseuid 3C73D9C502AC
    */
    public Timestamp getLastChgTime(){
      return new Timestamp(new Date().getTime());
    }

    /**
    * A setter for last change time
    * @param aLastChgTime
    * @roseuid 3C73D9D800AB
    */
    public void setLastChgTime(java.sql.Timestamp aLastChgTime){
    }

    /**
    * A getter for local id
    * @return String
    * @roseuid 3C73DA200253
    */
    public String getLocalId(){
      return NEDSSConstants.CLASSTYPE_ENTITY;
    }

    /**
    * A setter for local id
    * @param aLocalId
    * @roseuid 3C73DA2C00CA
    */
    public void setLocalId(String aLocalId){
    }

       /**
   Access method for the addUserId property.

   @return   the current value of the addUserId property
    */
    public Long getAddUserId()
    {
        return organizationUid;
    }

   /**
   Sets the value of the addUserId property.

   @param aAddUserId the new value of the addUserId property
    */
    public void setAddUserId(Long aAddUserId)
    {
      organizationUid = aAddUserId;
    }
    /**
    * A getter for last change reason code
    * @return String
    * @roseuid 3C73DABD00F0
    */
    public String getLastChgReasonCd(){
      return NEDSSConstants.CLASSTYPE_ENTITY;
    }

    /**
    * A setter for last change reason code
    * @param aLastChgReasonCd
    * @roseuid 3C73DAC60360
    */
    public void setLastChgReasonCd(String aLastChgReasonCd){
    }

        /**
     *
     * @return  Timestamp
     */
    public Timestamp getRecordStatusTime() {

        return new Timestamp(new Date().getTime());
    }

    /**
     *
     * @param aRecordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime) {

    }

    /**
    * A getter for status code
    * @return String
    * @roseuid 3C73DB60004A
    */
    public String getStatusCd(){
      return NEDSSConstants.CLASSTYPE_ENTITY;
    }

    /**
    * A setter for status code
    * @param aStatusCd
    * @roseuid 3C73DB6A030C
    */
    public void setStatusCd(String aStatusCd){
    }

       /**
    * A getter for status time
    * @return java.sql.Timestamp
    * @roseuid 3C73DB6F0381
    */
    public Timestamp getStatusTime(){
     return new Timestamp(new Date().getTime());
    }

    /**
    * A setter for status time
    * @param aStatusTime
    * @roseuid 3C73DB74018A
    */
    public void setStatusTime(java.sql.Timestamp aStatusTime){
    }

    /**
    * Implement base to return class type - currently CLASSTYPE_ACT or
    * CLASSTYPE_ENTITY
    *
    * @return String
    * @roseuid 3C73FD5C0343
    */
    public String getSuperclass(){
     return NEDSSConstants.CLASSTYPE_ENTITY;
    }

    /**
    * A getter for uid
    * @return Long
    * @roseuid 3C7407A80249
    */
    public Long getUid(){
      return organizationUid;
    }

    /**
    * A setter for add time
    * @param aAddTime
    * @roseuid 3C7412520078
    */
    public void setAddTime(java.sql.Timestamp aAddTime){
    }

    /**
    * A getter for add time
    * @return java.sql.Timestamp
    * @roseuid 3C74125B0003
    */
    public Timestamp getAddTime(){
      return new Timestamp(new Date().getTime());
    }

    public Integer getVersionCtrlNbr(){
      return organizationNameSeq;
    }
}