/*
 * Created on Sep 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.nbssecurity.encryption.*;
import gov.cdc.nedss.util.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xzheng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LoginServlet extends HttpServlet {

	static LogUtils logger = new LogUtils(LoginServlet.class.getName());
	 private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("UserName");
		logger.debug("Enter LoginServlet with user name = : " + userName);
		
		String loginErrorPage = propertyUtil.getUserAuthenticationErrorPage();
        if(loginErrorPage == null || loginErrorPage.trim().equals("")){
        	loginErrorPage = PageConstants.LOGINERRORPAGE;                                     	
         }
        
		String redirectUrl = null;
		if (userName == null) {
			redirectUrl = loginErrorPage;
		} else {

			// TODO: add your custom login logic here, if failed, redirect to
			// loginErrorPage

			// encode the user name if necessary
			String encryptionSetting = propertyUtil.getUsernameEncryptionSetting();
			if (encryptionSetting != null && encryptionSetting.length() != 0) {
				Encryptor encryptor = EncryptionService.getInstance()
						.getEncryptor(encryptionSetting);
				if (encryptor != null) {
					userName = encryptor.encryptF(userName);
					userName = URLEncoder.encode(userName, "UTF-8");
				}
			}
			redirectUrl = response.encodeRedirectURL("/nbs/nfc?UserName="
					+ userName);
		}
		
		  List<String> allowedUrls = new ArrayList<String>();
		  allowedUrls.add("/nbs/nfc?UserName="+userName);
		  allowedUrls.add(PageConstants.LOGINERRORPAGE);

		if(NedssUtils.isLocalPath(redirectUrl) && allowedUrls.contains(redirectUrl) ) response.sendRedirect(redirectUrl);
	}

}