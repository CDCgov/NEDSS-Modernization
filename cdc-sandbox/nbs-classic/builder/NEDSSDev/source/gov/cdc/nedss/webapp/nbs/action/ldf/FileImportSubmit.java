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
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportHome;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.helper.LdfHelper;
import gov.cdc.nedss.webapp.nbs.form.ldf.LdfForm;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.exception.*;

public class FileImportSubmit
    extends Action {

  public FileImportSubmit() {
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
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    HttpSession session = request.getSession();

    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION);
    if(securityCheck != true)
    {
      session.setAttribute("error", "Failed at security checking.");
      throw new ServletException("Failed at security checking.");
    }

    String sourceNumber = request.getParameter("source");

    TreeMap<Object,Object> filemap = (TreeMap<Object, Object>)session.getAttribute("filemap");
    String source = (String)filemap.get(new Integer(sourceNumber));
    try {
      Long importUid = submitSourceToEJB(source, session);
      session.setAttribute("importUid", importUid);
    }
    catch(NEDSSAppException ne)
    {
      session.setAttribute("error", ne.getErrorCd());
      throw new ServletException(ne.getMessage(),ne);
    }
    return mapping.findForward("next");

  }

  private Long submitSourceToEJB(String source,
                                 HttpSession session)
  throws NEDSSAppException{

    Long importUid = null;

    try {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
      String sMethod = "performImport";
      Object[] oParams = {
          source};

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
        importUid = (Long) resultUIDArr.get(0);
      }
      
      // XZ(08/02/2004), since we turn LDF/CDF xspCache back on, we need to invalidate 
      // the cache here after the import.  This is not a clean sulotion.  We should 
      // create a caching service, which support remote invalidations using something
      // like JMS.  That way we can cache the LDF/CDF xsp in the web tier, while 
      // updating the cache in the ap tier, right before LDF/CDF metadata is updated in
      // the database.  For now, we do the cache invalidation right at CDF import and
      // LDF edit.  Since DMDF is not created using our UI, the App server needs to be 
      // restarted after data migration in order to have DMDF shown to the end users.
      LdfHelper.resetXSPPageCache(session);

    }
    catch(NEDSSDBUniqueKeyViolation dupKey){
      throw dupKey;
    }
    catch(NEDSSAppException ne){
      throw ne;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return importUid;
  }

}
