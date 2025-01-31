package gov.cdc.nedss.webapp.nbs.action.pam.util;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Pradeep Sharma
 *
 */
public class PamDeleteUtil {
	static final LogUtils logger = new LogUtils(PamDeleteUtil.class.getName());

	public static ActionForward  deletePam(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)throws IOException, ServletException {
		 PamForm pamForm= (PamForm) form;
	      String sContextAction = request.getParameter("ContextAction");
          ActionForward forwardNew = new ActionForward();
	       if (sContextAction.equalsIgnoreCase("ReturnToFileEvents"))
		      {
		         NBSContext.store(request.getSession(), NBSConstantUtil.DSFileTab, "3");
		      }
			 else
				 NBSContext.store(request.getSession(), NBSConstantUtil.DSFileTab, "1");
	    	try{
	    		  String PhcUid=(String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
	    		  Long phcUidLong= 	new Long(PhcUid);
	    	      String invFormCd = pamForm.getPamFormCd();
	    	      NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationUid, PhcUid);
	    	 	String deleteString = deleteProxyObject(phcUidLong,pamForm, invFormCd, request.getSession());
				 if(deleteString!=null && !deleteString.trim().equalsIgnoreCase("") ){
					 NBSContext.store(request.getSession(), NBSConstantUtil.DSRejectedDeleteString, deleteString);
					 ActionForward af = mapping.findForward("DeleteDenied");
					 StringBuffer strURL = new StringBuffer(af.getPath());
					 strURL.append("?ContextAction=DeleteDenied&publicHealthCaseUID==").
	                  append(PhcUid);
					 forwardNew.setPath(strURL.toString());
					 forwardNew.setRedirect(true);
					 return forwardNew;
	    	  }
	    	  }catch (Exception e) {
					logger.error("Exception happened in PamDeleteUtil"+ e);
					e.printStackTrace();
					throw new ServletException("Exception happened in PamDeleteUtil :"+e.getMessage(),e);
				} 
	    	  return mapping.findForward(sContextAction);
	      }

	   private static String deleteProxyObject(Long publicHealthCaseUID,PamForm  pamForm,
			   String invFormCd, HttpSession session) throws NEDSSAppConcurrentDataException, NEDSSAppConcurrentDataException, Exception
		{

		   	Map<Object,Object> returnMap = new HashMap<Object,Object>();
			MainSessionCommand msCommand = null;
			String deleteError = "";
			try {
				String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
				String sMethod = "deletePamProxy";
				Object[] oParams = new Object[] { publicHealthCaseUID};

				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);

				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

				returnMap = (Map<Object,Object>) arr.get(0);

				Integer labCount = (Integer)returnMap.get(NEDSSConstants.LAB_REPORT);
				logger.debug(" PamDeleteUtil.deleteProxyObject:  labCount returned from PamProxyProxyEJB is "+ labCount);
				if(labCount==null || labCount.intValue()<0)
					labCount = new Integer(0);
				Integer morbCount = (Integer)returnMap.get(NEDSSConstants.MORBIDITY_REPORT);
				logger.debug(" PamDeleteUtil.deleteProxyObject:  morbCount returned from PamProxyProxyEJB is "+ morbCount);
				if(morbCount==null || morbCount.intValue()<0)
					morbCount = new Integer(0);
				Integer vaccCount = (Integer)returnMap.get(NEDSSConstants.AR_TYPE_CODE);
				logger.debug(" PamDeleteUtil.deleteProxyObject:  vaccCount returned from PamProxyProxyEJB is "+ vaccCount);
				if(vaccCount==null || vaccCount.intValue()<0)
					vaccCount = new Integer(0);
				Integer documentCount = (Integer)returnMap.get(NEDSSConstants.DocToPHC);
				logger.debug(" PamDeleteUtil.deleteProxyObject:  documentCount returned from PamProxyProxyEJB is "+ documentCount);
				if(documentCount==null || documentCount.intValue()<0)
					documentCount = new Integer(0);				
				String securityException = "";
				if(returnMap.get(NEDSSConstants.SECURITY_FAIL)!=null)
					securityException = (String)returnMap.get(NEDSSConstants.SECURITY_FAIL);
				if(securityException.equalsIgnoreCase(NEDSSConstants.SECURITY_FAIL) )
					throw new Exception("Security Exception happens as securityException is not empty "+ securityException);

						  	if(labCount.intValue()>0 || morbCount.intValue()>0 || vaccCount.intValue()>0 || documentCount.intValue()>0)
				{
					deleteError="You cannot Delete this Investigation as there is:\r\n\r\n " +
					labCount+ " Associated Lab Report(s)\r\n "
					+morbCount+ " Associated Morbidity Report(s)\r\n "
					+vaccCount+ " Associated Vaccination(s)\r\n "
					+documentCount+ " Associated Document(s)\r\n\r\n "
					+"Disassociate the Event(s) and try again. \r\n Note: You can only disassociate Events you have access to disassociate.";
				}
			} catch (NEDSSAppConcurrentDataException ncde) {
				throw new NEDSSAppConcurrentDataException("Concurrent access occurred in PamDeleteUtil : "
						+ ncde.toString());
			}  catch (Exception exp) {
				throw new Exception("PamDeleteUtil: getOldProxyObject");
			}
			return deleteError;
		}
}
