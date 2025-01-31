package gov.cdc.nedss.webapp.nbs.action.investigation.common;
/**
 *
 * <p>Title: BaseViewSubmit</p>
 * <p>Description: This is a Submit action class for View Submit and is a base
 * class for all view Submit.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.util.*;
import java.sql.Timestamp;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;

import javax.servlet.RequestDispatcher;

import org.apache.struts.action.*;

/**
 * Title:         InvestigationViewSubmit is an action class
 * Description:   This class retrieves data from EJB and puts them into request
 *                object for use in the xml file
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        NEDSS TEAM
 * @version       1.0
 */

public class BaseViewSubmit
    extends CommonAction {

   //For logging
   static final LogUtils logger = new LogUtils(BaseViewSubmit.class.
                                               getName());
   static String strLock = "lock";

   /**
    * This is constructor
    *
    */
   public BaseViewSubmit() {
   }

   /**
    * this base class perform has been overridden by implementation classes
    * @param mapping :ActionMapping the mapping
    * @param form : ActionForm the form contain values
    * @param request : HttpServletRequest the request
    * @param response : HttpServletResponse the response
    * @return  ActionForward Object
    * @throws  IOException
    * @throws ServletException
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {

	   throw new ServletException();

   }
   /**
    * looks for patient in InvestigationProxyVO and returns it
    * @param type_cd : String
    * @param investigationProxyVO : InvestigationProxyVO
    * @return : PersonVO
    */

   protected PersonVO getPersonVO(String type_cd, InvestigationProxyVO investigationProxyVO) {
      Collection<Object>  participationDTCollection  = null;
      Collection<Object>  personVOCollection  = null;
      ParticipationDT participationDT = null;
      PersonVO personVO = null;
      participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
      personVOCollection  = investigationProxyVO.getThePersonVOCollection();
      if (participationDTCollection  != null) {
        Iterator<Object>  anIterator1 = null;
        Iterator<Object>  anIterator2 = null;
         for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext(); ) {
            participationDT = (ParticipationDT) anIterator1.next();
            if (participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
               for (anIterator2 = personVOCollection.iterator(); anIterator2.hasNext(); ) {
                  personVO = (PersonVO) anIterator2.next();
                  if (personVO.getThePersonDT().getPersonUid().longValue() == participationDT.getSubjectEntityUid().longValue()) {
                     return personVO;
                  }
                  else {
                     continue;
                  }
               }
            }
            else {
               continue;
            }
         }
      }
      return null;
   }

}
