package gov.cdc.nedss.util;

/**
 * Title:
 * Description:This is a default class to put some Authentication logic before login.
 * Copyright: Copyright (c) 2003
 * Company: CSC
 * @author NEDSS Development Team
 * @version 1.0
 */

public class UserAuthentication {
  public UserAuthentication() {
  }
  /**
   * This method will allow user to put some authentication logic before login.If the user don't define
   * any class and method in the proprty file this would be the default method.Right now it is not doing any thing,
   * just returns a true value.
   * @param request
   * @param response
   * @return boolean
   */
  public boolean doAuthentication(Object request,Object response){
    return true;
  }
}
