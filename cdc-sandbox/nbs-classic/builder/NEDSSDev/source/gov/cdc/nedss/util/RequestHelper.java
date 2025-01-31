package gov.cdc.nedss.util;

/**
 * Title:        RequestHelper
 * Description:  Gets an HTTP request and returns the correspoing Command
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       10/12/2001 Sohrab Jahani
 * @modified     10/12/2001 Sohrab Jahani
 * @version      1.0.0, 10/12/2001
 */

import gov.cdc.nedss.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
//import com.sas.servlet.util.SocketListener;

import java.util.Map;

public class RequestHelper {

/**
 * Current Command Type
 */
  private int objectType=NEDSSConstants.UNKNOWN_COMMAND;
/**
 * Current Operation Type of the Command
 */
  private int operationType=NEDSSConstants.UNKNOWN_OPERATION;

/**
 * Current Request Object
 */
  private HttpServletRequest request;
  private HttpSession session;
  private HttpServletResponse response;

 /**
  * Sets the internal request value of the class
  *
  * @param HttpServletRequest current request object
  */

  public RequestHelper(HttpServletRequest request, HttpSession session) {
    this.request = request;
    this.session = session;
  }

/**
 * Overloaded constructor
 * Request Helper with request, response, session as parameters
 *
 * @version 	1.01 18 Feb 2001
 * @author 	Nedss Development Team
 */

  public RequestHelper(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    this.request = request;
	this.response = response;
    this.session = session;
  }

/**
 * Gets the internal request value of the class
 *
 * @return  current request object
 */
  public HttpServletRequest  getRequest(){
    return request;
  }
  /**
   * Sets the internal response
   * @ returns current response Object
   */
   public HttpServletResponse getResponse() {
	return response;
   }

  public HttpSession getSession()
  {
    if (request != null)
    {
      return request.getSession();
    }
    else
      return null;
  }

/**
 * Gets the command corresonding to the request object
 *
 * @return command corresonding to the request object
 */

  public Command getCommand(){

    Command command=null;

    // Check the Command Type corresponding to the Object Type
    // if there is some problem getting the ObjectType integer value, then we'll
    // use our default value
    if (request.getParameter("ObjectType") != null)
    {
      try{
        objectType = ( new Integer(request.getParameter("ObjectType")).intValue());
      }
      catch (Exception e) {}
    }

    String strCommandClass = NEDSSConstants.commandProcessor[objectType];
    //##!! System.out.println("Object type is : " +  strCommandClass );
    try{
      // Retrieve the correct command object
      command = (Command) Class.forName(strCommandClass).newInstance();
  //##!! System.out.println("This command is:" + command);

    } catch(ClassNotFoundException ce){
  //##!! System.out.println("There was a problem locating the command processor class: "+strCommandClass);
      ce.printStackTrace();
    } catch(Exception e){
      e.printStackTrace();
    }

   return command;
  }

}