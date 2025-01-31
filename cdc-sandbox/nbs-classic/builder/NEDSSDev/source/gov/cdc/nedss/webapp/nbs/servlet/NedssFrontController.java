package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbsErrorLog.NBSErrorLog;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.encryption.EncryptionService;
import gov.cdc.nedss.systemservice.nbssecurity.encryption.Encryptor;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.Command;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NbsDbPropertyUtil;
import gov.cdc.nedss.util.PageConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.RequestHelper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionServlet;

/**
 * The main servlet.
 */
public class NedssFrontController extends ActionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final PropertyUtil propUtil = PropertyUtil.getInstance();

	static final LogUtils logger = new LogUtils(NedssFrontController.class
			.getName());

	static final String AUTH_CLASS = "gov.cdc.nedss.util.UserAuthentication";

	static final String AUTH_METHOD = "doAuthentication";
	static boolean loadCache =false;
	/**
	 * DOCUMENT ME!
	 */
	public void destroy() {
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @throws ServletException
	 *             DOCUMENT ME!
	 */
	public void init() throws ServletException {
		super.init();

		try {
			NBSContext.initializeNBSContext(NBSConstantUtil.configFileName);
			NBSErrorLog.loadErrorMessages(NEDSSConstants.NBS_ERRORLOG);
			//     if (PropertyUtil.getLDF() == true){
			//LdfHelper helper = new LdfHelper();
			//helper.SyncXsp();
			//   }
		} catch (Exception e) {
			logger.error("couldn't configure the NBSContext");
			logger.error(e.getMessage(), e);
		}

		//  Cache the cdv in the servlet context.
		//  That way we can create just one instance
		//  instead of trying to create one in each XSP file.
		//  This makes pages load faster when SilverStream is not running.
		//  I'm putting it in the servlet context
		//  so there isn't a different copy for each user's session.
		//  CachedDropDownValues cdv = null;
		//  try
		//  {
		//      cdv = new CachedDropDownValues();
		//  }
		//  catch(Exception ex)
		//  {
		//      logger.error(ex.getMessage(), ex);
		//  }
		//  ServletContext sc = getServletContext();
		//  sc.setAttribute("CachedDropDownValues", cdv);
	}

	protected void dispatch(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {

		// response.setContentType("text/html");
		//  RequestDispatcher dispatcher=
		// getServletContext().getRequestDispatcher(page);
		//  dispatcher.include(request,response);
		response.sendRedirect(page);
	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Inside process of NFC");
		boolean userMigrated= true;
		
		//if(!loadCache)
		//{
		//	loadCache= true;
			//CachedDropDowns.startUp(request);
		//}
		// setup the error page
		String page = null;
		String loginErrorPage = propUtil.getUserAuthenticationErrorPage();
		if (loginErrorPage == null || loginErrorPage.trim().equals("")) {
			loginErrorPage = PageConstants.LOGINERRORPAGE;
		}
	

		if(!NbsDbPropertyUtil.getUserDBCheck()){
			//request.setAttribute("usersMigrated", "True");
			logger.debug("method not found in the specified class");
			
			dispatch(request, response, loginErrorPage +"?usersMigrated=false");
			return;
		}
		// check session first, then get the session associated with the request
		boolean isNewUserSession = isNewUserSession(request);
		HttpSession session = request.getSession(true);
		try {

			// if session is invalid, login again
			if (isNewUserSession) 
			{
				String userName = retrieveUserName(request);
				logger.debug("user name is :" + userName);

				if (userName == null) {
					// dispatch timeout page.
					page = PageConstants.TIMEOUTPAGE;
					dispatch(request, response, page);
					return;
				} else {

					/*
					 * This code will allow user to define a authentication
					 * class and method in the Property file and implement the
					 * authentication logic in the method.The method will return
					 * a boolean value.Also User can define a error page in
					 * property file to display in case the method feturns
					 * false. If user don't define any class in the property
					 * file, it will call the method from default class and
					 * display the nedss default login error page in case if
					 * default method returns false.
					 */
					boolean userAuthentication = true;

					// this is a bug strClass.equals(null), cleaned up the code,
					// xz 11/03/2004
					String strClass = propUtil.getUserAuthentication();
					// if(strClass.equals("") || strClass.equals(null))
					if (strClass == null || strClass.trim().equals("")) {
						strClass = AUTH_CLASS;
					}
					String strMethod = propUtil
							.getUserAuthenticationMethod();
					if (strMethod == null || strMethod.trim().equals("")) {
						strMethod = AUTH_METHOD;

					}
					Class authClass = Class.forName(strClass);
					Method[] method = authClass.getDeclaredMethods();
					Map resultMap = new HashMap<Object,Object>();
					for (int i = 0; i < method.length; i++) {
						Method method1 = (Method) method[i];
						String methodName = method1.getName();
						resultMap.put(methodName, method1);
					}
					Method authMethod = (Method) resultMap.get(strMethod);
					if (authMethod != null) {
						Object[] obj1 = { request, response };
						try {
							Boolean obj = (Boolean) authMethod.invoke(authClass
									.newInstance(), obj1);
							userAuthentication = obj.booleanValue();
							logger.debug("userAuthentication :"
									+ userAuthentication);
						} catch (IllegalArgumentException ie) {
							logger
									.debug("IllegalArgumentException thrown in nedss Front controller.");
							ie.printStackTrace();
						}

					} else {
						logger.debug("method not found in the specified class");
						dispatch(request, response, loginErrorPage);
						return;
					}
					if (userAuthentication == false) {
						logger
								.debug("User don't have a permission to access this application ");
						dispatch(request, response, loginErrorPage);
						return;

					} //User Authentication code ends here.

					// update session values
					String password = request.getParameter("Password");
					session.invalidate();
					// Probably new user, invalidate old session.
					session = request.getSession(true);

					//Puts username and password into session
					session.setAttribute(NEDSSConstants.USERNAME, userName);
					session.setAttribute(NEDSSConstants.PWORD, password);

					//  Get remote address and host for security log.
					String strRemoteAddress = null;
					String strRemoteHost = null;
					InetAddress ia = null;

					try {
						strRemoteAddress = request.getHeader(NEDSSConstants.X_FORWARDED_FOR);
			            if (strRemoteAddress == null || "".equals(strRemoteAddress)) {
			            	strRemoteAddress = request.getRemoteAddr();
			            }

						if (strRemoteAddress != null) {
							ia = InetAddress.getByName(strRemoteAddress);
							strRemoteHost = ia.getHostName();

							if (strRemoteAddress.startsWith("127.")) {
								ia = InetAddress.getLocalHost();
								strRemoteAddress = ia.getHostAddress();
								ia = InetAddress.getByName(strRemoteAddress);
								strRemoteHost = ia.getHostName();
							}
						}
					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
					}
					session.setAttribute(NEDSSConstants.REMOTE_ADDRESS,
							strRemoteAddress);
					session.setAttribute(NEDSSConstants.REMOTE_HOST,
							strRemoteHost);

					//  Log in.
					boolean pass = false;
					NBSSecurityObj nbsSecurityObject = null;
					try {
						MainSessionHolder holder = new MainSessionHolder();
						MainSessionCommand msCommand = holder
								.getMainSessionCommand(session);
						logger
								.debug("About to call nbsSecurityLogin with msCommand; VAL IS: "
										+ msCommand);
						nbsSecurityObject = (NBSSecurityObj) msCommand
								.nbsSecurityLogin(userName, password);
						logger.debug("Successfully called nbsSecurityLogin: "
								+ nbsSecurityObject);
						session.setAttribute("msCommandHandle", msCommand
								.getHandle());

						if (nbsSecurityObject != null) {
							pass = true;
						}
					} catch (Exception e) { 
						logger.fatal("", e);
						session.setAttribute("error",
								" Error logging. Please check your User Name");
						dispatch(request, response, loginErrorPage);
						logger.error(e.getMessage(), e);

						return;
					}

					if (pass == true) {
						logger.debug("NBSSecurityObject from ejb is: "
								+ nbsSecurityObject);

						if (nbsSecurityObject != null) {
							session.setAttribute("NBSSecurityObject",
									nbsSecurityObject);

							//this logon initial condition
							page = "/nbs/HomePage.do?method=loadHomePage";
						}
					} else {
						session.setAttribute("error",
								" Error logging. Please check your user name ");
						page = loginErrorPage;
					}
				}
			}

			//      String strStruts = request.getParameter("struts");
			//     boolean bStruts = ((strRequestURI == null) ? false : true);
			String strRequestURI = request.getRequestURI();

			if (strRequestURI.endsWith(".do")) {

				//extract Task name out of URI
				int startPos = strRequestURI.lastIndexOf('/') + 1;
				int endPos = strRequestURI.lastIndexOf('.');
				String strTask = strRequestURI.substring(startPos, endPos);
				String contextAction = request.getParameter("ContextAction");
				logger.debug("Checking context with Task Name, Action: "
						+ strTask + ", " + contextAction);
				
				if(strTask!=null && !strTask.startsWith("Load")){
					//logSecurity(request,strTask, contextAction);
				}

				if (contextAction == null) {
					contextAction = (String) request
							.getAttribute("ContextAction");
				}

				// check to make sure context is correct
				if (!NBSContext.checkContext(session, strTask, contextAction)) {
					logger
							.error("Context Incorrect - User must have used the back, refresh or history button.");

					// AK - 7/20/2004 civil00011670
					// Gracefully recover from context errors - reinitializing
					// the context by taking the user
					// back to the home page.
					session
							.setAttribute(
									"error",
									"An unexpected error occured."
											+ "<a href=\"/nbs/HomePage.do?method=loadHomePage\"> Click Here to return to Home page</a>");

					page = "/nbs/error";
				} else {
					logger
							.debug("Starting the STRUTS superclass process method");
					super.process(request, response);

					return;
				}
			} else {

				if (page == null) {
					logger.debug("Create helper");

					RequestHelper helper = new RequestHelper(request, response,
							session);
					logger.debug("Before get command");

					Command cmdHelper = helper.getCommand();
					logger.debug("Command is: " + cmdHelper);
					page = cmdHelper.execute(helper);

					//ArrayList<Object> arr =
					// (ArrayList<Object> )session.getAttribute("result");
					//logger.debug ("Array of size : " + arr.size());
					// DisplayPersonList list = (DisplayPersonList)arr.get(0);
					// logger.debug("Array List<Object> size is: " +
					// list.getTotalCounts());
					logger.debug("Page is: " + page);
				}
			}

			// }
		} catch (Exception e) {
			logger.fatal("", e);
			logger.debug("Exception is: " + e.toString());
			e.printStackTrace();

			try {
				session.setAttribute("error", e.getMessage());
			} catch (IllegalStateException ise) {
				logger.debug("IllegalStateException is: " + ise.toString());
				session = request.getSession(true);
				session.setAttribute("error", e.getMessage());
				
			}

			logger.error(e.getMessage(), e);
			page = "/nbs/error";
		}

		//   }
		// }	
		
		
		dispatch(request, response, page);
	}

	/**
	 * @param request
	 * @return
	 */
	private boolean isNewUserSession(HttpServletRequest request) throws IllegalStateException{
		
		// default to existing user session.  if any of the following
		// test passes, set to new user session.
		// TEST 1: no security object in session object
		// TEST 2: SSO with SSOEntry request parameter set to true
		// TEST 3: SSO with new user name
		// TEST 4: !SSO with UserName request parameter set to a value
		boolean retVal = false;
		HttpSession session = request.getSession(true);
		NBSSecurityObj nbsSecurityObject = (NBSSecurityObj) session
				.getAttribute("NBSSecurityObject");
		if (nbsSecurityObject == null) {
			// TEST 1: no security object
			retVal = true;
		}
		else{
			String userName = null;
			// SSO senario
			if (propUtil.isSSO()) {
				String ssoEntry = request.getParameter("SSOEntry");
				// TEST 2: SSO with SSOEntry request parameter set to true
				if(ssoEntry != null && ssoEntry.equalsIgnoreCase("true")){
					retVal = true;
				}
				else{
					// if SSO, check whether it is the same user
					userName = retrieveUserName(request);
					// TEST 3: SSO with new user name
					if (userName == null || (userName != null
							&& !userName.equalsIgnoreCase(nbsSecurityObject
									.getTheUserProfile().getTheUser().getUserID()))) {
						retVal = true;
					}					
				}
			} else {
				// if not SSO, check whether we are trying to login
				userName = request.getParameter("UserName");
				// TEST 4: !SSO with UserName request parameter set to a value
				if (userName != null) {
					retVal = true;
				}
			}
			
		}
		
		// if new user session, we need to invalidate the existing session
		if (retVal == true) {
			session.invalidate(); // new user, invalidate old session.
		}
		
		return retVal;
	}

	/**
	 * @param request
	 * @return
	 */
	private String retrieveUserName(HttpServletRequest request) {

		String userName = null;
		if (propUtil.isSSO()) {
			userName = request.getRemoteUser();
			logger.debug("SSO user name is: " + userName);
			// if using NTLM, strip the domain info
			if(userName != null){
				int index = userName.lastIndexOf("\\");
				if(index > -1){
					userName = userName.substring(index+1);
				}
			}
		} else {
			userName = HTMLEncoder.encodeHtml(request.getParameter("UserName"));

			// If NBS uses encryption on user login, we need to decrypt the use
			// name first
			// XZ 01/21/2005
			String encryptionSetting = propUtil
					.getUsernameEncryptionSetting();
			if (encryptionSetting != null && encryptionSetting.length() != 0) {
				Encryptor encryptor = EncryptionService.getInstance()
						.getEncryptor(encryptionSetting);
				if (encryptor != null) {
					userName = encryptor.decryptF(userName);
				}
				else{
					logger.error("The user name decryptor is not set up correctly");
				}
			}
		}
		return userName;
	}

}