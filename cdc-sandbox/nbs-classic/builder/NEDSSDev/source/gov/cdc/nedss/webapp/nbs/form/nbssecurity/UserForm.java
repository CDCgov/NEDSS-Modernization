package gov.cdc.nedss.webapp.nbs.form.nbssecurity;

import java.util.*;
import gov.cdc.nedss.util.*;
import org.apache.struts.action.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

public class UserForm
    extends ActionForm
{

  private static final LogUtils logger = new LogUtils(UserForm.class.getName());
  private UserProfile userProfile;
  private UserProfile userOldProfile;

  public UserForm()
  {
  }

  public void reset()
  {
    userProfile = null;
    userOldProfile = null;
  }

  public void setUserProfile(UserProfile theUserProfile)
  {
    this.userProfile = theUserProfile;
  }

  public UserProfile getUserProfile()
  {
    if (this.userProfile == null)
    {
      this.userProfile = new UserProfile();
    }

    return this.userProfile;
  }

  public UserProfile getOldUserProfile()
  {
    return this.userOldProfile;
  }

  public void setOldUserProfile(UserProfile theUserProfile)
  {
    this.userOldProfile = theUserProfile;
  }

}
