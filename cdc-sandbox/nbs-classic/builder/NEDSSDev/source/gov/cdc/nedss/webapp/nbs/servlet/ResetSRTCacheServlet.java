/**
 * Title:        ResetSRTCacheServlet
 * Description:  Reset SRT Cache communicates with CachedDropDownValues
 *               to reset the map, and clear the cache
 *
 *
 * Copyright:    Copyright (c) 2001-2002
 * Company: 	 Computer Sciences Corporation
 * @author       05/09/2002 Roger Wilson and NEDSS Development Team
 * @modified     05/09/2002 Roger Wilson
 * @version      1.0.0
 */

package gov.cdc.nedss.webapp.nbs.servlet;

// Import Statements
// java.* imports
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetSRTCacheServlet extends HttpServlet implements SingleThreadModel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private static final String CONTENT_TYPE = "text/html";

  // this value is just an example; it will be overwritten dynmically.

  private static String strResetSRTCacheURL = "http://localhost:8080/NedssFrontLogController/NedssFrontLogController";
  public  static String yesNoFlag = null;

  //CachedDropDown Values instance necessary to reset the treemap from the class
  CachedDropDownValues srtc = new CachedDropDownValues();


    /**
     * Logger for this class
     */

  static final LogUtils logger = new LogUtils((ResetSRTCacheServlet.class).getName()); // Added for the logger


  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
    strResetSRTCacheURL = "http://" + request.getServerName() + ":"
                                              + request.getServerPort()
                                              + request.getRequestURI();

  try{
    logger.debug("Entering the reset SRT cache.");
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();

      out.println(
        "<html lang=\"en\">" +
        "<head>" +
        "<title>Reset SRT Cache</title>" +
        "</head>" +
        "<body>" +
        "<p><b>NEDSS Management Console</b><p>" +
        "<form name=\"input\" action=\""+ strResetSRTCacheURL +"\"" +
        "method=\"get\">"
        );

      out.println(
        "<p>Reset SRT Cache:  "
      );


    if(yesNoFlag != null){

      if (yesNoFlag.equals("true")){
          out.println("True<p> <p>The SRT Cache was successfully cleared.<p>");
          //logger.debug("Size of the map, returned from the system before reset: " + srtc.map.size());
          logger.debug("Inside the reset function");
          /*
          srtc.map.clear();
          srtc.mapForDesc.clear();
          srtc.cachedAddressType = null;
          srtc.cachedAddressUse = null;
          srtc.cachedConditionCode = null;
          srtc.cachedIndustryCode = null;
          srtc.cachedLanguageCode = null;
          srtc.cachedPACV = null;
          srtc.cachedProgramAreaCd = null;
          */

          //logger.debug("Size of the map, after from the system before reset: " + srtc.map.size());

          }
       }
    else{
        out.println("False<p>");
        //logger.debug("Size of the map, before the reset function: " + srtc.map.size());
    }

      out.println(
        "<input type=\"hidden\" name=\"yesNoFlag\" value=\"true\">" +
        "<input type=\"submit\" value=\"Reset SRT Cache\">" +
        "</form>" +
        "</body>" +
        "</html>"
      );
  }

  catch (Exception e) {
      logger.debug("Exception e = " + e + "\n");
      e.printStackTrace();
    }
}
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //doGet(request, response);
    yesNoFlag = request.getParameter("yesNoFlag");
    logger.debug("Value of yesNoFlag: " + yesNoFlag);
  }

  /**Clean up resources*/
  public void destroy() {
  }

}