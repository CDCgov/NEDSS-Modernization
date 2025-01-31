//Source file: C:\\CDC\\gov\\gov\\cdc\\nedss\\helpers\\RootDTInterface.java
package gov.cdc.nedss.systemservice.util;

import java.sql.Timestamp;


public interface RootDTInterface
{

    /**
    * A getter for last change user id
    * @return Long
    * @roseuid 3C73C11500C4
    */
    public Long getLastChgUserId();

    /**
    * A setter for last change user id
    * @param aLastChgUserId
    * @roseuid 3C73D82701FD
    */
    public void setLastChgUserId(Long aLastChgUserId);

    /**
    * A getter for jurisdiction code
    * @return String
    * @roseuid 3C73D8D1030F
    */
    public String getJurisdictionCd();

    /**
    * A setter for jurisdiction code
    * @param aJurisdictionCd
    * @roseuid 3C73D8E5000B
    */
    public void setJurisdictionCd(String aJurisdictionCd);

    /**
    * A getter for program area code
    * @return String
    * @roseuid 3C73D90A0145
    */
    public String getProgAreaCd();

    /**
    * A setter for program area code
    * @param aProgAreaCd
    * @roseuid 3C73D91703E2
    */
    public void setProgAreaCd(String aProgAreaCd);

    /**
    * A getter for last change time
    * @return java.sql.Timestamp
    * @roseuid 3C73D9C502AC
    */
    public Timestamp getLastChgTime();

    /**
    * A setter for last change time
    * @param aLastChgTime
    * @roseuid 3C73D9D800AB
    */
    public void setLastChgTime(java.sql.Timestamp aLastChgTime);

    /**
    * A getter for local id
    * @return String
    * @roseuid 3C73DA200253
    */
    public String getLocalId();

    /**
    * A setter for local id
    * @param aLocalId
    * @roseuid 3C73DA2C00CA
    */
    public void setLocalId(String aLocalId);

    /**
    * A getter for add user id
    * @return Long
    * @roseuid 3C73DA4701B9
    */
    public Long getAddUserId();

    /**
    * A stter for add user id
    * @param aAddUserId
    * @roseuid 3C73DA550123
    */
    public void setAddUserId(Long aAddUserId);

    /**
    * A getter for last change reason code
    * @return String
    * @roseuid 3C73DABD00F0
    */
    public String getLastChgReasonCd();

    /**
    * A setter for last change reason code
    * @param aLastChgReasonCd
    * @roseuid 3C73DAC60360
    */
    public void setLastChgReasonCd(String aLastChgReasonCd);

    /**
    * A getter for record status code
    * @return String
    * @roseuid 3C73DAFD023D
    */
    public String getRecordStatusCd();

    /**
    * A setter for record status code
    * @param aRecordStatusCd
    * @roseuid 3C73DB0C02AC
    */
    public void setRecordStatusCd(String aRecordStatusCd);

    /**
    * A getter for record status time
    * @return java.sql.Timestamp
    * @roseuid 3C73DB260015
    */
    public Timestamp getRecordStatusTime();

    /**
    * A setter for record status time
    * @param aRecordStatusTime
    * @roseuid 3C73DB35002A
    */
    public void setRecordStatusTime(java.sql.Timestamp aRecordStatusTime);

    /**
    * A getter for status code
    * @return String
    * @roseuid 3C73DB60004A
    */
    public String getStatusCd();

    /**
    * A setter for status code
    * @param aStatusCd
    * @roseuid 3C73DB6A030C
    */
    public void setStatusCd(String aStatusCd);

    /**
    * A getter for status time
    * @return java.sql.Timestamp
    * @roseuid 3C73DB6F0381
    */
    public Timestamp getStatusTime();

    /**
    * A setter for status time
    * @param aStatusTime
    * @roseuid 3C73DB74018A
    */
    public void setStatusTime(java.sql.Timestamp aStatusTime);

    /**
    * Implement base to return class type - currently CLASSTYPE_ACT or
    * CLASSTYPE_ENTITY
    *
    * @return String
    * @roseuid 3C73FD5C0343
    */
    public String getSuperclass();

    /**
    * A getter for uid
    * @return Long
    * @roseuid 3C7407A80249
    */
    public Long getUid();

    /**
    * A setter for add time
    * @param aAddTime
    * @roseuid 3C7412520078
    */
    public void setAddTime(java.sql.Timestamp aAddTime);

    /**
    * A getter for add time
    * @return java.sql.Timestamp
    * @roseuid 3C74125B0003
    */
    public Timestamp getAddTime();

    /**
    * A checker for the new flag
    * @return boolean
    * @roseuid 3C7440F0021D
    */
    public boolean isItNew();

    /**
    * A setter for the new flag
    * @param itNew
    * @roseuid 3C7441030329
    */
    public void setItNew(boolean itNew);

    /**
    * A checker for the dirty flag
    * @return boolean
    * @roseuid 3C74410A00DA
    */
    public boolean isItDirty();

    /**
    * A setter for the dirty flag
    * @param itDirty
    * @roseuid 3C74410F02C2
    */
    public void setItDirty(boolean itDirty);

    /**
    * A checker for the delete flag
    * @return boolean
    * @roseuid 3C74411402B5
    */
    public boolean isItDelete();

    /**
    * A setter for the delete flag
    * @param itDelete
    * @roseuid 3C74412E012C
    */
    public void setItDelete(boolean itDelete);

    /**
    * A getter for program jurisdiction oid
    * @return Long
    * @roseuid 3CF7906002AE
    */
    public Long getProgramJurisdictionOid();

    /**
    * A setter for the program jurisdiction oid
    * @param aProgramJurisdictionOid
    * @roseuid 3CF7974902A7
    */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid);

    /**
    * A getter for shared indicator
    * @return String
    * @roseuid 3CFBB5DA00CD
    */
    public String getSharedInd();

    /**
    * A setter for shared indicator
    * @param aSharedInd
    * @roseuid 3CFBB5EB01F4
    */
    public void setSharedInd(String aSharedInd);

    /**
     * A getter for version control number
     * @return  Integer
     */
    public Integer getVersionCtrlNbr();
}