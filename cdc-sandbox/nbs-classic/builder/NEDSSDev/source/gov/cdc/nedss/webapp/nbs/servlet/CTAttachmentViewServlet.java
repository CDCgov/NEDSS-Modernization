package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * CTAttachmentViewServlet.java
 * Nov 23, 2009
 * @version 1.0
 */
public class CTAttachmentViewServlet extends HttpServlet {
	
	static final LogUtils logger = new LogUtils(NedssChartServlet.class.getName());
	static final int BUFSIZE = 65534;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String attachmentTxt = "";
		//get the attachmentUid for the record that needs to be viewed.
		Long attachmentUid = Long.valueOf(request.getParameter("ctContactAttachmentUid"));
		String fileNm = request.getParameter("fileNmTxt");
		try {
			attachmentTxt = processRequest(attachmentUid, request.getSession());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retrieving CT Attachment For: " + e.getMessage());
		}
		
		doDownload(request, response, attachmentTxt, fileNm);		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	
	/**
	 * Sends a file to the ServletResponse output stream.  
	 * @param req
	 * @param resp
	 * @param attachmentTxt
	 * @param filename
	 * @throws IOException
	 */
	private void doDownload(HttpServletRequest req, HttpServletResponse resp, String attachmentTxt, String filename) throws IOException {
		
		//File f = new File(filename);
		//int length = 0;
		ServletOutputStream op = resp.getOutputStream();
		
		byte[] theByteArray = attachmentTxt.getBytes();
		int size = theByteArray.length;
		
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filename);

		resp.setContentType((mimetype != null) ? mimetype: "application/octet-stream");
		resp.setContentLength(size);
		resp.setHeader("Content-Disposition", "attachment; filename=\""+ filename + "\"");
/*
		byte[] bbuf = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(f));

		while ((in != null) && ((length = in.read(bbuf)) != -1)) {
			op.write(bbuf, 0, length);
		}
*/
		op.write(theByteArray, 0, size);
		//in.close();
		op.flush();
		op.close();
	}
    
    
	@SuppressWarnings("unchecked")
	private static String processRequest(Long ctUid, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		String attachmentTxt = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getContactAttachment";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { ctUid };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			attachmentTxt = (String)arr.get(0);

			} catch (Exception ex) {
				logger.error("Error while processRequest in FileUploadAction: " + ex.toString());	
				throw new Exception(ex);
			}
			return attachmentTxt;	
	}     
	
	
}
