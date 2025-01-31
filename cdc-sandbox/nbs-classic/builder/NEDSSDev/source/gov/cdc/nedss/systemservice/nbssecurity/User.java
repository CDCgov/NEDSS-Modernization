//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\User.java

package gov.cdc.nedss.systemservice.nbssecurity;

import java.util.ArrayList;
import gov.cdc.nedss.util.*;

public class User
    extends AbstractVO
{
	private static final long serialVersionUID = 1L;
	
  private String userID;
  private String firstName;
  private String lastName;
  private String comments;
  private String status;
  private String entryID;
  private String password;
  private Long reportingFacilityUid;
  private String userType;
  private String facilityDetails;
  private String readOnly;
  private String facilityID;
  private Long providerUid;
  private String msa;
  private String paa;
  private String admUserTypes;
  private String paaProgramArea;
  private String jurisdictionDerivationInd;

  /**
   * @roseuid 3CE2C2CD006D
   */
  public User()
  {
  }

  /**
   * Access method for the userID property.
   *
   * @return   the current value of the userID property
   */
  public String getUserID()
  {
    return userID;
  }

  /**
   * Sets the value of the userID property.
   *
   * @param aUserID the new value of the userID property
   */
  public void setUserID(String aUserID)
  {
    userID = aUserID;
  }

  /**
   * Access method for the readOnly property.
   *
   * @return   the current value of the readOnly property
   */
  public String getReadOnly()
  {
    return readOnly;
  }

  /**
   * Sets the value of the readOnly property.
   *
   * @param aUserID the new value of the readOnly property
   */
  public void setReadOnly(String aReadOnly)
  {
    readOnly = aReadOnly;
  }

  /**
   * Access method for the facilityName property.
   *
   * @return   the current value of the facilityDetails property
   */
  public String getFacilityDetails()
  {
    return facilityDetails;
  }

  /**
   * Sets the value of the facilityDetails property.
   *
   * @param aUserID the new value of the facilityDetails property
   */
  public void setFacilityDetails(String aFacilityDetails)
  {
    facilityDetails = aFacilityDetails;
  }

  /**
   * Access method for the firstName property.
   *
   * @return   the current value of the firstName property
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Sets the value of the firstName property.
   *
   * @param aFirstName the new value of the firstName property
   */
  public void setFirstName(String aFirstName)
  {
    firstName = aFirstName;
  }

  /**
   * Access method for the lastName property.
   *
   * @return   the current value of the lastName property
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Sets the value of the lastName property.
   *
   * @param aLastName the new value of the lastName property
   */
  public void setLastName(String aLastName)
  {
    lastName = aLastName;
  }

  /**
   * Access method for the comments property.
   *
   * @return   the current value of the comments property
   */
  public String getComments()
  {
    return comments;
  }

  /**
   * Sets the value of the comments property.
   *
   * @param aComments the new value of the comments property
   */
  public void setComments(String aComments)
  {
    comments = aComments;
  }

  /**
   * Access method for the status property.
   *
   * @return   the current value of the status property
   */
  public String getStatus()
  {
    return status;
  }

  /**
   * Sets the value of the status property.
   *
   * @param aStatus the new value of the status property
   */
  public void setStatus(String aStatus)
  {
    status = aStatus;
  }

  /**
   * Access method for the entryID property.
   *
   * @return   the current value of the entryID property
   */
  public String getEntryID()
  {
    return entryID;
  }

  /**
   * Sets the value of the entryID property.
   *
   * @param aEntryID the new value of the entryID property
   */
  public void setEntryID(String aEntryID)
  {
    entryID = aEntryID;
  }

  /**
   * Access method for the password property.
   *
   * @return   the current value of the password property
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Sets the value of the password property.
   *
   * @param aPassword the new value of the password property
   */
  public void setPassword(String aPassword)
  {
    password = aPassword;
  }

  /**
   * @return java.lang.Long
   * @roseuid 3EB00CC102B0
   */
  public Long getReportingFacilityUid()
  {
    return reportingFacilityUid;
  }

  /**
   * @param aReportingFacilityUid
   * @roseuid 3EB02D12037E
   */
  public void setReportingFacilityUid(Long aReportingFacilityUid)
  {
    reportingFacilityUid = aReportingFacilityUid;
  }

  /**
   * @return java.lang.String
   * @roseuid 3EB00CF80178
   */
  public String getUserType()
  {
    return userType;
  }

  /**
   * @param aUserType
   * @roseuid 3EB02D280394
   */
  public void setUserType(String aUserType)
  {
    userType = aUserType;
  }

  /**
   * Access method for the user's property.
   *
   * @return   the current value of the msa property
   */

  public String getMsa()
  {
    return msa;
  }

  /**
   * Setting method for the user's property.
   *
   * @param aRoleName the new value of the msa property if he is a msa
   */

  public void setMsa(String aUserType)
  {
    msa = aUserType;
  }

  /**
   * Access method for the user's property.
   *
   * @return   the current value of the paa property
   *
   */

  public String getPaa()
  {
    return paa;
  }

  /**
   * Setting method for the user's property.
   *
   * @param aRoleName the new value of the user's property if he is a paa
   */

  public void setPaa(String aUserType)
  {
    paa = aUserType;
  }

  /**
   * Access method for the user's property.
   *
   * @return   the current value of the program areas property
   * it will be return as CEDS|TB|HIV format, the program area codes have been
   * separated by pipe symbol.
   */

  public String getPaaProgramArea()
  {
    return paaProgramArea;
  }

  /**
     * Setting method for the user's property.
     *
     * @param
     * it will be as CEDS|TB|HIV format, the program area codes have been
     * separated by pipe symbol.
     */
  public void setPaaProgramArea(String aPaaProgramArea)
  {
    paaProgramArea = aPaaProgramArea;
  }

  /**
    * Access method for the user's property.
    *
    * @return   the current value of the program areas property
    * it will return as MSA-PAA format if a user is both a msa and paa
    * otherwise, it will return either MSA or PAA or a empty string.
    */

  public String getAdminUserType()
  {
    return admUserTypes;
  }

  /**
      * Setting method for the user's property.
      *
      * @return   the current value of the program areas property
      * it will passing as MSA-PAA format if a user is both a msa and paa
      * otherwise, it will pass either MSA or PAA or a empty string.
      */

  public void setAdminUserType(String administratorUserTypes)
  {
    admUserTypes = administratorUserTypes;
  }

  /**
   * @param itDirty
   *
   */
  public void setItDirty(boolean itDirty)
  {
    this.itDirty = itDirty;
  }

  /**
   * @return boolean
   *
   */
  public boolean isItDirty()
  {
    return itDirty;
  }

  /**
   * @param itNew
   *
   */
  public void setItNew(boolean itNew)
  {
    this.itNew = itNew;
  }

  /**
   * @return boolean
   *
   */
  public boolean isItNew()
  {
    return itNew;
  }

  /**
   * @param itDelete
   *
   */
  public void setItDelete(boolean itDelete)
  {
    this.itDelete = itDelete;
  }

  /**
   * @return boolean
   *
   */
  public boolean isItDelete()
  {
    return itDelete;
  }

  public boolean isEqual(java.lang.Object objectname1,
			 java.lang.Object objectname2, Class<?> voClass)
  {
    return true;
  }

/**
 * @return the providerUid
 */
public Long getProviderUid() {
	return providerUid;
}

/**
 * @param providerID the providerUid to set
 */
public void setProviderUid(Long providerUid) {
	this.providerUid = providerUid;
}

public String getJurisdictionDerivationInd() {
	return jurisdictionDerivationInd;
}

public void setJurisdictionDerivationInd(String jurisdictionDerivationInd) {
	this.jurisdictionDerivationInd = jurisdictionDerivationInd;
}
}
