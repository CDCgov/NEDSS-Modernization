package gov.cdc.nedss.webapp.nbs.servlet;

/**
 * <p>Title: ResetProperties</p>
 * <p>Description: To reset properties read from property file and read
 *  new property file </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Shailesh Desai
 * @version 1.0
 */
import gov.cdc.nedss.util.PropertyUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetProperties extends HttpServlet{

  private static final String CONTENT_TYPE = "text/html";
  public  static String yesNoFlag = null;
  private static String strResetSRTCacheURL = null;

/**
 * DOCUMENT ME!
 *
 * @throws ServletException DOCUMENT ME!
 */
   public void init() throws ServletException {
        super.init();

   }
   public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {
        doPost(request, response);
        strResetSRTCacheURL = request.getScheme() + "://" + request.getServerName() + ":"
                                          + request.getServerPort()
                                          + request.getRequestURI();


      try{
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        PropertyUtil.getInstance().setReadFromFile(true);
        
          out.println(
            "<html lang=\"en\">" +
            "<head>" +
            "<title>Reset Properties.</title>" +
            "</head>" +
            "<body>" +
            "<p><b>NEDSS Management Console</b><p>" +
            "<form name=\"input\" action=\""+ strResetSRTCacheURL +"\"" +
            "method=\"get\">"
            );

          out.println(
            "<p>Reset Properties Cache:  "
          );


        if(yesNoFlag != null){

          if (yesNoFlag.equals("true")){
              out.println("True<p> <p>The Properties Cache was successfully cleared.<p>");

              }
           }
        else{
            out.println("False<p>");
        }

          out.println(
            "<input type=\"hidden\" name=\"yesNoFlag\" value=\"true\">" +
            "<input type=\"submit\" value=\"Reset Properties Cache\">" +
            "</form>" +
            "</body>" +
            "</html>"
          );
      }

      catch (Exception e) {
          e.printStackTrace();
        }
    }
      public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doGet(request, response);
        yesNoFlag = request.getParameter("yesNoFlag");
      }

      /**Clean up resources*/
      public void destroy() {
      }

}