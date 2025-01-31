package gov.cdc.nedss.webapp.nbs.action.ldf;

/**
 * Title:        Actions
 * Description:  initializes the ldf admin
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:jay kim
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import javax.rmi.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.HashMap;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.webapp.nbs.form.ldf.LdfForm;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.systemservice.nbssecurity.*;

public class FileImportLoad extends Action {

	public FileImportLoad() {
	}

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
		HttpSession session = request.getSession();
		String sPageID = request.getParameter("pageID");
		NBSSecurityObj nbsSecurityObj =
			(NBSSecurityObj) session.getAttribute("NBSSecurityObject");
                boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION);
                if(securityCheck != true)
                {
                  session.setAttribute("error", "Failed at security checking.");
                  throw new ServletException("Failed at security checking.");
                }

		request.setAttribute("cancelButtonHref", "/nbs/LDFAdminLoad.do");

		ErrorMessageHelper.setErrMsgToRequest(request, "ps227");
		//TODO check security
		this.getImportFileName(request, session);

		return mapping.findForward("next");

	}

	private void getImportFileName(
		HttpServletRequest request,
		HttpSession session) {
		String[] fileNames = null;
		StringBuffer sb = null;
		StringBuffer sb1 = null;
		String DSImportFilenames = null;
		TreeMap<Object, Object> filemap = new TreeMap<Object, Object>();
		try {

			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
			String sMethod = "getImportFileNames";
			Object[] oParams = {
			};

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr =
				msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				fileNames = (String[]) resultUIDArr.get(0);
			}

			sb = new StringBuffer();

			for (int i = 0; i < fileNames.length; i++) {
				String b = fileNames[i];

				sb1 = new StringBuffer();
				sb1.append(
					PropertyUtil.getInstance().getProperty(
						ImportConstants.IMPORT_FILE_DIRECTORY_KEY,
						ImportConstants.DEFAULT_IMPORT_FILE_DIRECTORY));
				sb1.append(b);
				filemap.put(new Integer(i), sb1.toString());

				sb.append(i).append("$");
				/*sb.append(
					PropertyUtil.getProperty(
						ImportConstants.IMPORT_FILE_DIRECTORY_KEY,
						ImportConstants.DEFAULT_IMPORT_FILE_DIRECTORY));
                                */
				sb.append(b);
				sb.append("|");
			}
			DSImportFilenames = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("DSImportFilenames", DSImportFilenames);
		session.setAttribute("filemap", filemap);
	}

}
