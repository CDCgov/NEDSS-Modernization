package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.util.LogUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * NBSJavaServerPagePublisher publishes preConstructed JSPs' to the appropriate Exploded War Content Folder Structure 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSJavaServerPagePublisher.java
 * Jan 12, 2010
 * @version 1.0
 */
public class NBSJavaServerPagePublisher extends HttpServlet {
	
	static LogUtils logger = new LogUtils(NBSJavaServerPagePublisher.class.getName());
	
	public void init() throws ServletException {
		
		PagePublisher publisher = PagePublisher.getInstance();
		try {

			//Can also be used as Page Renderer (Probably by PageManagement Publisher API ??? T.B.D)
			
			/* Uncomment once ready to plugin
			PageBuilder pb =PageBuilder.getInstance();
			ArrayList list = (ArrayList)PageBuilder<Module_API>.retrievePageMetaDataCollection();
			pb.writeJspContentToFile(list);
			*/
			
			//By default on Server Startup Publishes the Page
			publisher.publishPage("");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Publishing page in PageManagement Module: " + e.toString());
			
		}		
		
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		
		
	}

	
}
