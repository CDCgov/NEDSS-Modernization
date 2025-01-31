package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  This servlet runs when you explicitly log out of the system.
 *  @author Ed Jenkins
 */
public class LogoutServlet extends HttpServlet
{

    private static LogUtils logger = null;

    /**
     *  Default constructor.
     */
    public LogoutServlet()
    {
        logger = new LogUtils(this.getClass().getName());
    }

    public void init(ServletConfig config) throws ServletException
    {
        //  Verify parameters.
        if(config == null)
        {
            return;
        }
        super.init(config);
    }

    public String getServletInfo()
    {
        return "Invalidates the session.";
    }

    public void destroy()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //  Get remote address and host for security log.
        String strRemoteAddress = null;
        String strRemoteHost = null;
        InetAddress ia = null;
        try
        {
        	strRemoteAddress = request.getHeader(NEDSSConstants.X_FORWARDED_FOR);
            if (strRemoteAddress == null || "".equals(strRemoteAddress)) {
            	strRemoteAddress = request.getRemoteAddr();
            }
            if(strRemoteAddress != null)
            {
                ia = InetAddress.getByName(strRemoteAddress);
                strRemoteHost = ia.getHostName();
                if(strRemoteAddress.startsWith("127."))
                {
                    ia = InetAddress.getLocalHost();
                    strRemoteAddress = ia.getHostAddress();
                    ia = InetAddress.getByName(strRemoteAddress);
                    strRemoteHost = ia.getHostName();
                }
            }
        }
        catch(Exception ex)
        {
            logger.error(ex.getMessage(), ex);
        }
        //  Log out.
        boolean booTimeout = false;
        try
        {
            HttpSession ses = request.getSession(false);
            if(ses == null)
            {
                booTimeout = true;
            }
            else
            {
                //String strUsername = (String)ses.getAttribute("Username");
                String strSessionID = ses.getId();
                NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) ses.getAttribute("NBSSecurityObject");
                String uFirstNm = nbsSecurityObj.getTheUserProfile().getTheUser().getFirstName();
                String uLastNm = nbsSecurityObj.getTheUserProfile().getTheUser().getLastName();
                Long nedssEntryId = new Long(nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID());
    		MainSessionHolder holder = new MainSessionHolder();
    		MainSessionCommand msCommand = holder.getMainSessionCommand(ses);
    		msCommand.logout(uFirstNm,uLastNm, strSessionID, strRemoteAddress, strRemoteHost,nedssEntryId);
                msCommand.remove(); // remove instance of main session commnad.
                /* Get rid of all session attributes */
                Enumeration enumeration = ses.getAttributeNames();
                while (enumeration.hasMoreElements())
                {
                  Object obj = enumeration.nextElement();
                  obj = null;
                }

                ses.invalidate();
                if(uFirstNm != null)
                {
                    logger.debug(uFirstNm + " has logged out.");
                }
            }
        }
        catch(Exception ex)
        {
            logger.error(ex.getMessage(), ex);
        }
        
        // redirect to a page set in nedss.properties, xz 10/18/2004
        String nonTimeoutPage = PropertyUtil.getInstance().getLogoutPage();
        if(nonTimeoutPage == null || nonTimeoutPage.trim().length() == 0){
        	nonTimeoutPage = PageConstants.LOGOUTPAGE;
        }
        String s = (booTimeout == true) ? PageConstants.TIMEOUTPAGE : nonTimeoutPage;
        response.sendRedirect(s);
    }

}
