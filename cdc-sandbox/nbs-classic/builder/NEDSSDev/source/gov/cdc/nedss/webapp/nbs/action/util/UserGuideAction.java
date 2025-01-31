package gov.cdc.nedss.webapp.nbs.action.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.LogUtils;

public class UserGuideAction extends DispatchAction
{
	static final LogUtils logger = new LogUtils(UserGuideAction.class.getName());

	private void returnFile(HttpServletRequest request, HttpServletResponse response, File file)
		throws Exception 
	{
		try {	
			 PDDocument pdfDocument = PDDocument.load(file);
			 response.setContentType("application/pdf");
			 pdfDocument.save(response.getOutputStream());
		}
		catch (Exception e) {
			logger.error("Exception in UserGuideAction: " + e.getMessage());
			logger.error(e.toString());
		}		
	}

	/**
	 * This method results in one of the three possible outcomes based on the NBS User guide location
	 * value specified in the properties file. If the value is a reference to a pdf file in the disk, it reads it
	 * and returns it to the user. If the value is a URL to an external location, it redirects the 
	 * request to point to the URL mentioned. If no value is present or if the file could not be read, an error
	 * page is displayed to the user. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void open(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) 
	{
		try {
			if (PropertyUtil.getInstance().getNBSUserGuideLocation() != null) {
				// check if a valid value is present
				if (PropertyUtil.getInstance().getNBSUserGuideLocation().trim().length() == 0) {
					logger.error("Value for the User Guide Location property is blank");
					response.sendRedirect("/nbs/error.do");
				}
				// check if the value is a URL
				else if (PropertyUtil.getInstance().getNBSUserGuideLocation().toLowerCase().indexOf("http://www.") >= 0) {
					logger.debug("Value for the User Guide Location property is a URL.");
					response.sendRedirect(PropertyUtil.getInstance().getNBSUserGuideLocation());
				}
				// check if the value is a reference to a file on disk
				else {
					File file = new File(PropertyUtil.getInstance().getNBSUserGuideLocation());
			        if (file.isFile()) {
			        	logger.debug("Value for User Guide Location property is a reference to a local file on disk.");
			        	returnFile(request, response, file); 
			        }
				}
			}
			else {
				logger.error("User Guide Location property is not found in the properties file.");
				response.sendRedirect("/nbs/error.do");
			}
		}
        catch (Exception e) {
        	logger.error("Error while opening the User Location Guide.");
        	logger.error(e.toString());
        	try {
        		response.sendRedirect("/nbs/error.do");
        	}
        	catch (Exception ex) {
        		logger.error("Error while redirecting to the error page:" +ex.getMessage());
        		logger.error(ex.toString());
        	}
        }
	}
}