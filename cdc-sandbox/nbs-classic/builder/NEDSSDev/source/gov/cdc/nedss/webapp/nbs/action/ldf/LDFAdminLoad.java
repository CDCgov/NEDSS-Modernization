package gov.cdc.nedss.webapp.nbs.action.ldf;

/**
 * Title:        Actions
 * Description:  initializes the ldf admin
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:jay kim
 * @version 1.0
 */

import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.LDFMetaDataHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




public class LDFAdminLoad
    extends Action {
  //For logging
	static final LogUtils logger = new LogUtils(LDFAdminLoad.class.getName());

  public LDFAdminLoad() {
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
    String sPageID = request.getParameter("pageID");
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION);
     if(securityCheck != true)
     {
       session.setAttribute("error", "Failed at security checking.");
       throw new ServletException("Failed at security checking.");
     }


    //initialize the drop downs

    SRTMap srtm = null;
    Map<Object,Object> map = new TreeMap<Object, Object>();
    NEDSSConstants Constants = new NEDSSConstants();
    ArrayList<Object> pages = null;

    NedssUtils nu = new NedssUtils();
   try{
      Object objref = nu.lookupBean(JNDINames.SRT_CACHE_EJB);
      //logger.debug("objref " + objref);
      SRTMapHome home = (SRTMapHome) PortableRemoteObject.narrow(objref,
          SRTMapHome.class);
      srtm = home.create();
      pages = srtm.getLDFPageIDsHome();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    request.setAttribute("pages",pages);

    Collection<Object>  descColl10 = new ArrayList<Object> ();
    Collection<Object>  descColl17 = new ArrayList<Object> ();
    Collection<Object>  descColl24 = new ArrayList<Object> ();
    Collection<Object>  descColl30 = new ArrayList<Object> ();
    Collection<Object>  descColl400 = new ArrayList<Object> ();
    
    if(pages!=null)
     {
                Iterator<Object>  it1 = pages.iterator();
                 while (it1.hasNext())
                 {
                      LdfPageSetDT ldfPageSetDT= (LdfPageSetDT)it1.next();

              if(ldfPageSetDT.getParentIsCd() != null)
                         {
                            if(ldfPageSetDT.getParentIsCd().equals("10"))
                      {

                        if(ldfPageSetDT.getUiDisplay().equals("Link"))
                        {
                            descColl10.add(ldfPageSetDT);

                            }

                      }
                      else if(ldfPageSetDT.getParentIsCd().equals("17"))
                      {

                        if(ldfPageSetDT.getUiDisplay().equals("Link"))
                        {
                            descColl17.add(ldfPageSetDT);

                            }

                      }
                      else if(ldfPageSetDT.getParentIsCd().equals("24"))
                      {

                        if(ldfPageSetDT.getUiDisplay().equals("Link"))
                        {
                            descColl24.add(ldfPageSetDT);

                            }

                      }
                          else if(ldfPageSetDT.getParentIsCd().equals("30"))
                      {

                        if(ldfPageSetDT.getUiDisplay().equals("Link"))
                        {
                            descColl30.add(ldfPageSetDT);

                            }

                      }
                      else if(ldfPageSetDT.getParentIsCd().equals("400"))
                      {

                        if(ldfPageSetDT.getUiDisplay().equals("Link"))
                        {
                            descColl400.add(ldfPageSetDT);

                            }

                      }                            


                    }
                  }
            }
     request.setAttribute("descColl10",descColl10);
     request.setAttribute("descColl17",descColl17);
     request.setAttribute("descColl24",descColl24);
     request.setAttribute("descColl30",descColl30);
     request.setAttribute("descColl400",descColl400);
     this.getLDFCount(pages,nbsSecurityObj,request,session);
    return mapping.findForward("next");

  }

  private void getLDFCount(Collection<Object> pages, NBSSecurityObj nbsSecurityObj,HttpServletRequest request,HttpSession session)
  {
    //   Call LDFMetadata
    Map<?,?> countMap = new HashMap<Object,Object>();
    Collection<Object>  pageIdCollection  = new ArrayList<Object> ();

    if(pages!=null)
        {Iterator<Object>  iter = pages.iterator();
                     while (iter.hasNext())
                        {
                            LdfPageSetDT ldfPageSet= (LdfPageSetDT)iter.next();
                            if(ldfPageSet.getLdfPageId()!= null)
                            {
                              pageIdCollection.add(ldfPageSet.getLdfPageId());
                           }
                        }
        }


    try {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.LDFMetaData_EJB;
      String sMethod = "getMetaDataCount";
      Object[] oParams = {pageIdCollection};
      logger.debug("getting  ldfMetaDataCount from LDFMetaDataEJB, via mainsession");

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      logger.info("mscommand in LDFAdminLoad class is: " +
                  msCommand);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
        countMap = (HashMap<?,?>)resultUIDArr.get(0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    request.setAttribute("countMap",countMap);


  }



}
