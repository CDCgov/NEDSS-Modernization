package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.systemservice.nbsErrorLog.NBSErrorLog;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.ActionForward;


public class ErrorMessageHelper {

  public static void setErrMsgToRequest(HttpServletRequest request) {

    try {
      
      ArrayList<Object> errorList = NBSErrorLog.subErrorMapMaker();
      request.setAttribute(NEDSSConstants.ERROR_MESSAGES, errorList);
    } catch(Exception e) {
      e.printStackTrace();
    }

  }

  public static void setErrMsgToRequest(HttpServletRequest request, String pageID) {

    setErrMsgToRequest(request);
  }

  /**
   * @description To handle errorMessages for more than 1 page (multi tab specific scenario)
   * @param request
   * @param pageIDs
   */
  public static void setErrMsgToRequest(HttpServletRequest request, ArrayList<Object> pageIDs) {

    setErrMsgToRequest(request);

  }//setErrMsgToRequest



  public static ActionForward redirectToPage(String pageID, String errorMsgCd) {

    ActionForward af = new ActionForward();
    String url = "";

    try {
       

      ArrayList<Object> errorList = NBSErrorLog.subErrorMapMaker();

      if(errorList != null && errorList.size() > 0 ) {

        for(Iterator<Object> anIter=errorList.iterator(); anIter.hasNext();) {
          ArrayList<Object> subList = (ArrayList<Object> )anIter.next();
          if(errorMsgCd != null && subList.get(0).equals(errorMsgCd)) {

 

            url = (String)subList.get(1);
             

            if(url.startsWith("@")) {
               
              af.setPath(url.substring(1));
              af.setRedirect(true);
            }
          }
        }

      } else {
        af.setPath("error");
        af.setRedirect(true);
      }
    } catch(Exception e) {
      e.printStackTrace();
      af.setPath("error");
      af.setRedirect(true);
       
    }

    return af;
  }

  public static String makeErrMessage(String errorCode, ArrayList<Object> fieldNames) {

    String errMessage = null;
    String returnMsg = null;

    try {

      ArrayList<Object> errorList = NBSErrorLog.subErrorMapMaker();
      ArrayList<Object> tempArray = null;


      for(int i = 0; i < errorList.size(); i++) {

        tempArray = (ArrayList<Object> )errorList.get(i);

        if(tempArray.get(0).toString().equalsIgnoreCase(errorCode)) {
          String msg = tempArray.get(1).toString();
          returnMsg = msg;
          if(fieldNames != null)
          {
            //this loop is exicuted only when fieldNames exist
            for (int j = 0; j < fieldNames.size(); j++) {
              returnMsg = ErrorMessageHelper.replace(msg, "(Field Name)",fieldNames.get(j).toString());
              msg = returnMsg;
            }
          }
           //break the loop once u found the errorCode
          break;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
      throw new NEDSSSystemException(e.toString());
    }
    return returnMsg;

  } // makeErrMessage

  public static String makeErrMessage(String errorCode) {

    return makeErrMessage(errorCode, null);

  } // makeErrMessage


  public static String replace(String errorMessage, String before, String after) {

    StringBuffer sb = new StringBuffer(errorMessage);
    int startpos = errorMessage.indexOf(before);
    int endpos = startpos + before.length();

    if (startpos > -1 && endpos <= errorMessage.length())
      errorMessage = sb.replace(startpos, endpos, after).toString();

    return errorMessage;
  } //replace


}//ErrorMessageHelper