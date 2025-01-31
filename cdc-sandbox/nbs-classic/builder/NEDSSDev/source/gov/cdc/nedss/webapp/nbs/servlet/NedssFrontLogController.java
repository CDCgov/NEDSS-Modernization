package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean.LogController;
import gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean.LogControllerHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title:        NedssFrontLogController
 * Description:  NEDSS Front End Log Controller which communicates with
 *               Apps Tier Log Controller Session Bean EJB
 *
 * Copyright:    Copyright (c) 2001-2002
 * Company:      Computer Sciences Corporation
 * @author       12/31/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     01/09/2002 Sohrab Jahani
 * @version      1.0.0
 */
public class NedssFrontLogController extends HttpServlet implements SingleThreadModel
{

    private static final String CONTENT_TYPE = "text/html";
    // this value is just an example; it will be overwritten dynmically.
    private static String strNedssFrontLogContorllerURL = PropertyUtil.getInstance().getProviderURL()+"/NedssFrontLogController/NedssFrontLogController";
    private static final String strWebTierGroup = "WebTierLevel";
    private static final String strAppsTierGroup = "AppsTierLevel";
    static final LogUtils logger = new LogUtils((NedssFrontLogController.class).getName());  // Added for the logger
    static LogController logController = null;

    public void destroy()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int iWebTierLevel;
        int iAppsTierLevel;
        strNedssFrontLogContorllerURL = request.getScheme() +"://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html lang=\"en\">" + "<head>" + "<title>Reload Logger</title>" + "</head>" + "<body>" + "<p><b>NEDSS Management Console</b><p>" + "<form name=\"input\" action=\"" + strNedssFrontLogContorllerURL + "\"" + "method=\"get\">");
        String strNewWebTierLevel = request.getParameter(strWebTierGroup);
        if(strNewWebTierLevel == null)
        {
            strNewWebTierLevel = LogUtils.getLogLevelName();
        }
        out.println("<p>Web Tier Logging Level:<p>" + radioButtonHTML(strNewWebTierLevel, "ALL", strWebTierGroup) + radioButtonHTML(strNewWebTierLevel, "DEBUG", strWebTierGroup) + radioButtonHTML(strNewWebTierLevel, "INFO", strWebTierGroup) + radioButtonHTML(strNewWebTierLevel, "WARN", strWebTierGroup) + radioButtonHTML(strNewWebTierLevel, "ERROR", strWebTierGroup) + radioButtonHTML(strNewWebTierLevel, "FATAL", strWebTierGroup) + radioButtonHTML(strNewWebTierLevel, "OFF", strWebTierGroup) + "<p>");
        String strNewAppsTierLevel = request.getParameter(strAppsTierGroup);
        if(strNewAppsTierLevel == null)
        {
            strNewAppsTierLevel = LogUtils.getLogLevelName();
        }
        out.println("<p>Apps Tier Logging Level:<p>" + radioButtonHTML(strNewAppsTierLevel, "ALL", strAppsTierGroup) + radioButtonHTML(strNewAppsTierLevel, "DEBUG", strAppsTierGroup) + radioButtonHTML(strNewAppsTierLevel, "INFO", strAppsTierGroup) + radioButtonHTML(strNewAppsTierLevel, "WARN", strAppsTierGroup) + radioButtonHTML(strNewAppsTierLevel, "ERROR", strAppsTierGroup) + radioButtonHTML(strNewAppsTierLevel, "FATAL", strAppsTierGroup) + radioButtonHTML(strNewAppsTierLevel, "OFF", strAppsTierGroup) + "<p>");
        out.println("<input type=\"submit\" value=\"Change Settings\">" + "</form>" + "</body>" + "</html>");
        iWebTierLevel = LogUtils.levelName2LevelNumber(strNewWebTierLevel);
        // Changing the Log Level of Web Tier
        LogUtils.setLogLevel(iWebTierLevel);
        iAppsTierLevel = LogUtils.levelName2LevelNumber(strNewAppsTierLevel);
        // Changing the Log Level of Apps Tier
        logController.setLogLevel(iAppsTierLevel);
        // Web Tier Testing
        runTest();
        // Apps Tier Testing
        logController.runTest();
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

    public void init() throws ServletException
    {
        LogUtils.setLogLevel(6);
        try
        {
            PropertyUtil propUtil = PropertyUtil.getInstance();
            logger.debug("PropertyUtil.valid=" + propUtil.isValid());
            NedssUtils nedssUtils = new NedssUtils();
            logger.debug("\n(*) Looking up LogControllerEJB");
            String sBeanName = JNDINames.LOG_CONTROLLER_EJB;
            Object obj = nedssUtils.lookupBean(sBeanName);
            logger.debug("\n--->lookup = " + obj.toString());
            logger.debug("\n(*) Narrowing : ");
            LogControllerHome home = (LogControllerHome)PortableRemoteObject.narrow(obj, LogControllerHome.class);
            logger.debug("\n--->Found LogControllerHome --------------- ");
            logger.debug("(*) Creating an instance of LogController : ");
            logController = home.create();
            logger.debug("\n---> LogController is  =" + logController);
        }
        catch(Exception e)
        {
            logger.debug("EJB Tester exception e = " + e + "\n");
            e.printStackTrace();
        }
    }

    void runTest()
    {
        logger.fatal("*** Testing Started ...");
        logger.fatal("Current Level:" + LogUtils.getLogLevelName() + "[" + LogUtils.getLogLevel() + "]");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARNING");
        logger.error("ERROR");
        logger.fatal("FATAL");
        logger.fatal("*** Testing Finished ...");
        logger.fatal("*** Testing (More Informative) BEGIN ...");
        logger.fatal("Current Level:" + LogUtils.getLogLevelName() + "[" + LogUtils.getLogLevel() + "]");
        logger.debug("Sohrab", "DEBUG");
        logger.info("Sohrab", "INFO");
        logger.warn("Sohrab", "WARNING");
        logger.error("Sohrab", "ERROR");
        logger.fatal("Sohrab", "FATAL");
        logger.fatal("*** Testing (More Informative) END ...");
        

    }

    private  NBSSecurityObj getMockNBSSecurityObj(){
    	NBSSecurityObj nbsSecurityObj = new NBSSecurityObj(null, null);
    	
    	return nbsSecurityObj;
    }

    private String radioButtonHTML(String Level, String Name, String GroupName)
    {
        return "<input type=\"radio\" name=\"" + GroupName + "\" value=\"" + Name + "\"" + ((Level.compareTo(Name) == 0) ? " checked=\"checked\"" : "") + ">" + Name + "<br>";
    }

}
