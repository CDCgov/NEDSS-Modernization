package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAttachmentDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.form.util.FileUploadForm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;

public class FileUploadAction extends MappingDispatchAction {
	
	static final LogUtils logger = new LogUtils(FileUploadAction.class.getName());

    public ActionForward showForm(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
		ActionForward actionForward = mapping.findForward("success");
		return actionForward;
	}
    
    public ActionForward doUpload(ActionMapping mapping, 
    		ActionForm aForm, 
    		HttpServletRequest request, 
    		HttpServletResponse response)
    {
        ActionForward actionForward = null;
        String contactUid = "999999";
        try {
            FileUploadForm form = (FileUploadForm) aForm;
            
            // proceed if the file is of valid size
            FormFile file = form.getCtFile();
            int maxSizeInMB = PropertyUtil.getInstance().getMaxFileAttachmentSizeInMB();
            long maxSizeInBytes = maxSizeInMB * 1024 * 1024;
            if (file.getFileSize() < maxSizeInBytes) 
            {
                //Construct CTContactAttachmentDT from form and get it 
            	//out to back end...attachment, desc_txt, file_nm_txt
                CTContactAttachmentDT dt = new CTContactAttachmentDT();
                dt.setItNew(true);
                dt.setCtContactAttachmentUid(new Long(-1));
                
                //ContactUid should be retrieved from NBSContext when user clicks on 
                // Contact Record Id on Events Tab
                contactUid = String.valueOf(NBSContext.retrieve(request.getSession(), 
                		NBSConstantUtil.DSContactUID));
                dt.setCtContactUid(new Long(contactUid));
                
                //Retrieve addUserId from SecurityObject
                NBSSecurityObj secObj = 
                	(NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");            
                String userId = secObj.getTheUserProfile().getTheUser().getEntryID();
                dt.setAddUserId(Long.valueOf(userId));
                dt.setLastChgUserId(Long.valueOf(userId));
                dt.setLastChgTime(new Timestamp(new Date().getTime()));
                
                //FileName
                String fileNm = String.valueOf(form.getFileName());
                dt.setFileNmTxt(fileNm);
                
                //FileDescription
                String descTxt = String.valueOf(form.getFileDescription());
                dt.setDescTxt(descTxt);
                
                //Attachment
                byte[] fileData = file.getFileData();
                dt.setAttachment(fileData);
                
                //Make EJB Call - Persistance
                Long resultUid = processRequest(dt, request.getSession());
                if(resultUid != null && resultUid.longValue() > 0) {
                	request.setAttribute("confirmation", "true");
                	dt.setCtContactAttachmentUid(resultUid);
                	_makeNewAttachmentRowSecurity(dt,request);
                }
            }
            else {
            	form.reset();
            	request.setAttribute("fileUploadForm", form);
            	request.setAttribute("maxFileSizeExceeded", "true");
            	request.setAttribute("maxFileSizeInMB", String.valueOf(maxSizeInMB));
            }
        } catch (Exception e) {
            logger.error("Error in fileUploadAction = " + e);
            request.setAttribute("error", e.getMessage());
        }

        actionForward = mapping.findForward("success");
        logger.debug("Leaving home:doUpload");
        return actionForward;
    }
    
    public ActionForward doDownload(ActionMapping mapping, 
    		ActionForm aForm, 
    		HttpServletRequest request, 
    		HttpServletResponse response)
    {
        try {
        	// retrieve details from request parameter
        	String ctContactAttachmentUid = request.getParameter("ctContactAttachmentUid");
        	String fileName = request.getParameter("fileNmTxt");
        	
        	logger.debug("Download the file with id:" 
        			+ ctContactAttachmentUid + "; and name:" + fileName);
        	
        	if (ctContactAttachmentUid == null || fileName == null) {
        		logger.error("1 or both attributes ctContactAttachmentUid" +
        				" and fileName NOT found in the request parameter");
        	}
        	
        	// get file contents
        	MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getContactAttachment";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
			
			Object[] oParams = {Long.valueOf(ctContactAttachmentUid)};
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			byte[] theByteArray = (byte[])arr.get(0);
			int size = theByteArray.length;

			// prepare response	
			ServletContext context = request.getSession().getServletContext();
			String mimetype = context.getMimeType(fileName);
			response.setContentType((mimetype != null) ? mimetype: "application/octet-stream");
			response.setHeader("cache-control", "must-revalidate");
			response.setContentLength(size);
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			
			// write response
			ServletOutputStream op = response.getOutputStream();
			op.write(theByteArray, 0, size);
			op.flush();
			op.close();
        }
        catch (Exception e) {
            logger.error("Error in doDownload = " + e);
            request.setAttribute("error", e.getMessage());
        }
        
        return null;
    }

    /**
     * 
     * @param dt
     * @param session
     * @return
     * @throws Exception
     */
	private static Long processRequest(CTContactAttachmentDT dt, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		Long resultUid = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "setContactAttachment";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { dt };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			resultUid = (Long) arr.get(0);

			} catch (Exception ex) {
				logger.error("Error while processRequest in FileUploadAction.processRequest: " + ex.toString());	
				throw new Exception(ex);
			}
			return resultUid;	
		}    
	
	
	private static void _makeNewAttachmentRowSecurity(CTContactAttachmentDT dt,HttpServletRequest request) {
		String dateAdded = StringUtils.formatDate(dt.getLastChgTime());
		RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
		String userNm = rsVO.getUserName(dt.getLastChgUserId());
		HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("nbsAttachmentUid", String.valueOf(dt.getCtContactAttachmentUid()));
		parameterMap.put("fileNmTxt", dt.getFileNmTxt());
		FileUploadUtil util = new FileUploadUtil();
		dt.setDescTxt(util.replaceNewLineCarRetn(dt.getDescTxt()));
		
		request.setAttribute("attachmentdescText", dt.getDescTxt());
		request.setAttribute("attachmentLink", dt.getViewLink());
		request.setAttribute("userName", userNm);
		request.setAttribute("addedDate", dateAdded);
		request.setAttribute("attachmentUid", dt.getCtContactAttachmentUid());
		request.setAttribute("fileNmTxt", dt.getFileNmTxt());
		if(null != dt.getCtContactAttachmentUid() && null != dt.getFileNmTxt()) request.setAttribute("newAttachment", "true");
	}
	
	public static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm) {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		Iterator<Object> iter = paramMap.keySet().iterator();		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) paramMap.get(key);
			reqParams.append(key).append("=");
			reqParams.append(value.replaceAll(" ", "%20"));
			reqParams.append("&");
		}
		reqParams.deleteCharAt(reqParams.length() - 1);
		url.append("<a href=javascript:loadAttachment(\"/nbs/");
		url.append(strutsAction);
		url.append(reqParams.toString());
		if(jumperName != null) {
			url.append("#").append(jumperName);
		}
		url.append("\")>").append(displayNm).append("</a>");
		return url.toString();
	}
}