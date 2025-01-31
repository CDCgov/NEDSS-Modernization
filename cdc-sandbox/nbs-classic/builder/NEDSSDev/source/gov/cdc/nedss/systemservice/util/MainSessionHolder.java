package gov.cdc.nedss.systemservice.util;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.util.*;

import java.util.*;
import javax.ejb.*;
import javax.rmi.*;
import javax.servlet.http.*;


public class MainSessionHolder
{

    //  private static Map<Object,Object> mainSessionTable = Collections.synchronizedMap(new HashMap<Object,Object>());;
    static final LogUtils logger = new LogUtils(MainSessionHolder.class.getName());

    /**
     *  Default constructor.
     */
    public MainSessionHolder()
    {
    }

    /**
     *  Gets a session command for a HttpSession.
     *  @param session and HttpSession.
     *  @throws Exception.
     */
    public MainSessionCommand getMainSessionCommand(HttpSession session) throws Exception
    {
        String strRemoteAddress = (String)session.getAttribute(NEDSSConstants.REMOTE_ADDRESS);
        String strRemoteHost = (String)session.getAttribute(NEDSSConstants.REMOTE_HOST);
        MainSessionCommand msCommand = null;
        msCommand = (MainSessionCommand)session.getAttribute("msCommand");
        if(msCommand != null)
        {
            logger.info("Just used an old MainSessionCommand for: " + (String)session.getAttribute(NEDSSConstants.USERNAME));
        }
        else
        {
            logger.info("Create a new MainSessionCommand for " + (String)session.getAttribute(NEDSSConstants.USERNAME));
            msCommand = getMainSessionCommand((String)session.getAttribute(NEDSSConstants.USERNAME), (String)session.getAttribute(NEDSSConstants.PWORD), session.getId(), strRemoteAddress, strRemoteHost);
            session.setAttribute("msCommand", msCommand);
        }
        try
        {
            String test = msCommand.test();
            logger.debug("Main session test string is: " + test);
        }
        catch(Exception e)
        {
            // Retry getting new mainsession
            msCommand = null;
            logger.error(e.getMessage(), e);
            logger.error("The session command was stale. Trying to get new main session command");
            msCommand = getMainSessionCommand((String)session.getAttribute(NEDSSConstants.USERNAME), (String)session.getAttribute(NEDSSConstants.PWORD), session.getId(), strRemoteAddress, strRemoteHost);
            session.setAttribute("msCommand", msCommand);
        }
        return msCommand;
    }

    /**
     *  Gets MainSessionCommand remote references.
     *  @param username the user's username.
     *  @param password the user's password.
     *  @param sessionId the ID of the user's session.
     *  @param pRemoteAddress the client's IP address.
     *  @param pRemoteHost the client's host name.
     *  @throws Exception.
     */
    private MainSessionCommand getMainSessionCommand(String username, String password, String sessionId, String pRemoteAddress, String pRemoteHost) throws java.lang.Exception
    {
        if(username != null)
        {
            MainSessionCommandHome msCommandHome = null;
            try
            {
                NedssUtils nedssUtils = new NedssUtils();
                String sBeanName = JNDINames.MAIN_CONTROL_EJB;
                msCommandHome = (MainSessionCommandHome)PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanName), MainSessionCommandHome.class);
            }
            catch(Exception ex)
            {
                logger.fatal("Unable to obtain a main session command home");
                throw new Exception(ex.getMessage(), ex);
            }
            MainSessionCommand ms = msCommandHome.create(username, password, sessionId, pRemoteAddress, pRemoteHost);
            logger.info("Mainsession command is: " + ms);
            logger.info("user name is: " + username + " and session id is: " + sessionId);
            return ms;
        }
        else
        {
            logger.error("User name and password are required to continue");
            throw new Exception("User name and password are required to continue");
        }
    }

}
