//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\UserProfile.java

package gov.cdc.nedss.systemservice.nbssecurity;

import java.util.*;
import gov.cdc.nedss.util.*;

public class UserProfile
    extends AbstractVO
{
	private static final long serialVersionUID = 1L;
  public Collection<Object> theRealizedRlCollection;
  public User theUser;

  public UserProfile()
  {
  }

  /**
   * Access method for the theRealizedRoleCollection  property.
   *
   * @return   the current value of the theRealizedRoleCollection  property
   */
  public Collection<Object> getTheRealizedRoleCollection()
  {
    return theRealizedRlCollection;
  }

  /**
   * Sets the value of the theRealizedRoleCollection  property.
   *
   * @param aTheRealizedRoleCollection  the new value of the theRealizedRoleCollection  property
   */
  public void setTheRealizedRoleCollection(Collection<Object>
					   aTheRealizedRoleCollection)
  {
    theRealizedRlCollection  = aTheRealizedRoleCollection;
  }

  /**
   * Access method for the theUser property.
   *
   * @return   the current value of the theUser property
   */
  public User getTheUser()
  {
    return theUser;
  }

  public User getTheUser_s()
  {
    if (theUser == null)
    {
      theUser = new User();

    }
    return theUser;
  }

  /**
   * Sets the value of the theUser property.
   *
   * @param aTheUser the new value of the theUser property
   */
  public void setTheUser(User aTheUser)
  {
    theUser = aTheUser;
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
   * Used to access the collection elements from Struts.
   *
   * @param index
   * @return RealizedRole
   * @roseuid 3D0621FB03C5
   */

  public RealizedRole getTheRealizedRole_s(int index)
  {
    if (this.theRealizedRlCollection  == null)
    {
      this.theRealizedRlCollection  = new ArrayList<Object> ();
    }
    int currentSize = this.theRealizedRlCollection.size();
    // check if we have this many realized roles
    if (index < currentSize)
    {
      try
      {
	Object[] tempArray = this.theRealizedRlCollection.toArray();

	Object tempObj = tempArray[index];

	RealizedRole tempRole = (RealizedRole) tempObj;
	return tempRole;
      }
      catch (Exception e)
      {
	// logger.error(e);
      } // do nothing just continue
    }

    RealizedRole tempRole = null;

    for (int i = currentSize; i < index + 1; i++)
    {
      tempRole = new RealizedRole();
      this.theRealizedRlCollection.add(tempRole);
    }

    return tempRole;

  }
}
