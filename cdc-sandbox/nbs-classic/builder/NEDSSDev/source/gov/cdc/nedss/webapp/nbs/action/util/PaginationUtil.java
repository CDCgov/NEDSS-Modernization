package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.form.util.CommonForm;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

/**
 * Util class for DisplayTag 1.1 to handle Pagination / Sorting with Method params 
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PaginationUtil.java
 * Jan 13, 2009
 * @version
 */
public class PaginationUtil {
	
	static final LogUtils logger = new LogUtils(PaginationUtil.class.getName());
	/**
	 * Preserves the NBS current paging/sort status across session.  
	 * @param form
	 * @param request
	 * @param mapping
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ActionForward paginate(BaseForm form, HttpServletRequest request, String name, ActionMapping mapping) {
		
		 ActionForward forwardNew = new ActionForward();
		 ActionForward af = mapping.findForward(name);
		 StringBuffer strURL = new StringBuffer(af.getPath());
		 String pageNo;
		 //Put the pagination, method and sortOrder stuff here...
		 String pageParam = new ParamEncoder("parent").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		 ArrayList<Object> investigationSummaryVOs = new ArrayList<Object>();
		try {
			investigationSummaryVOs = (ArrayList<Object>) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSInvestigationList);
		} catch (Exception ex) {
			investigationSummaryVOs = new ArrayList<Object>();
		}
		 if(pageParam != null && request.getParameter(pageParam) != null) {
			pageNo = request.getParameter(pageParam);
			form.getAttributeMap().put("PageNumber", pageNo);		
		 } else {
			 pageNo = form.getAttributeMap().get("PageNumber") == null ? "1" : (String) form.getAttributeMap().get("PageNumber");
			 
			 //Change to the previous page when the last notification on that page has been removed
			 if((form.getAttributeMap().get("queueSize")!=null || form.getAttributeMap().get("maxRowCount")!=null || investigationSummaryVOs!=null)){
				
				 int queueSize=1, numNotif;
				
				 if(form.getAttributeMap().get("maxRowCount")!=null)
					 queueSize=Integer.parseInt(form.getAttributeMap().get("maxRowCount").toString());
				 else
					 if(form.getAttributeMap().get("queueSize")!=null)
						 queueSize=Integer.parseInt(form.getAttributeMap().get("queueSize").toString());
					 else
						 if(request.getAttribute("maxRowCount")!=null)
							 queueSize=Integer.parseInt(request.getAttribute("maxRowCount").toString());
				 
				 if(request.getAttribute("queueCount")!=null)
					 numNotif = Integer.parseInt(request.getAttribute("queueCount").toString());
				 else
					 numNotif=investigationSummaryVOs.size();
				 
				 int div = numNotif/queueSize;
				 
				 if(numNotif%queueSize==0 && div<Integer.parseInt(pageNo))
					 pageNo=Integer.toString(div);
				 if(div==0)
					 pageNo=Integer.toString(1);
			 }
			 //This is necessary for updating on the form the new page number after removing all the notification from last page
			 form.getAttributeMap().put("PageNumber", pageNo);
			 strURL.append("?").append(pageParam).append("=").append(pageNo);
		 }
		 
		
		 
		 //MethodNm
		 String methodParam = new ParamEncoder("parent").encodeParameterName(TableTagParameters.PARAMETER_SORT);
		 if(methodParam != null && request.getParameter(methodParam) != null) {
				String methd = request.getParameter(methodParam);
				form.getAttributeMap().put("methodName", methd);				
		 } else {
			 String methd = form.getAttributeMap().get("methodName") == null ? "none" : (String) form.getAttributeMap().get("methodName");
			 strURL.append("&").append(methodParam).append("=").append(methd);
		 }
		 //Sort Order
		 String orderParam = new ParamEncoder("parent").encodeParameterName(TableTagParameters.PARAMETER_ORDER);
		 if(orderParam != null && request.getParameter(orderParam) != null) {
				String order = request.getParameter(orderParam);
				form.getAttributeMap().put("sortOrder", order);			
		 } else {
			 	String order = form.getAttributeMap().get("sortOrder") == null ? "1" : (String) form.getAttributeMap().get("sortOrder");
				strURL.append("&").append(orderParam).append("=").append(order); 
		 }

		 forwardNew.setPath(strURL.toString());		
		 
		 return forwardNew;		
	}
	
	/**
	 * displayTagAttribute method finds if the user has accessed pages/sort/by what columns
	 * @param request
	 * @return java.lang.boolean
	 */
	public static boolean _dtagAccessed(HttpServletRequest request) {
		boolean found = false;
		Enumeration enm = request.getParameterNames();
		while(enm.hasMoreElements()) {
			String paramName = (String) enm.nextElement();
			if(paramName != null && paramName.startsWith("d-")) {
				found = true;
				break;
			}
		}
		
		return found;
	}	
	public static ActionForward paginateForCommonForm(CommonForm form, HttpServletRequest request, String name, ActionMapping mapping) {
		
		 ActionForward forwardNew = new ActionForward();
		 ActionForward af = mapping.findForward(name);
		 StringBuffer strURL = new StringBuffer(af.getPath());
		 //Put the pagination, method and sortOrder stuff here...
		 String pageParam = new ParamEncoder("parent").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		 if(pageParam != null && request.getParameter(pageParam) != null) {
			String pageNo = request.getParameter(pageParam);
			form.getAttributeMap().put("PageNumber", pageNo);		
		 } else {
			 String pageNo = form.getAttributeMap().get("PageNumber") == null ? "1" : (String) form.getAttributeMap().get("PageNumber");
			 
			 
			//Change to the previous page when the last notification on that page has been removed
			 if(form.getAttributeMap().get("queueSize")!=null && request.getAttribute("queueCount")!=null){
				 int queueSize = Integer.parseInt(form.getAttributeMap().get("queueSize").toString());
				 int numNotif = Integer.parseInt(request.getAttribute("queueCount").toString());

				 int div = numNotif/queueSize;
				 
				 if(numNotif%queueSize==0 && div<Integer.parseInt(pageNo)){
					 pageNo=Integer.toString(div);
				 if(div==0)
				     pageNo=Integer.toString(1);
				 }
			 }
			 strURL.append("?").append(pageParam).append("=").append(pageNo);
		 }
		 //MethodNm
		 String methodParam = new ParamEncoder("parent").encodeParameterName(TableTagParameters.PARAMETER_SORT);
		 if(methodParam != null && request.getParameter(methodParam) != null) {
				String methd = request.getParameter(methodParam);
				form.getAttributeMap().put("methodName", methd);				
		 } else {
			 String methd = form.getAttributeMap().get("methodName") == null ? "none" : (String) form.getAttributeMap().get("methodName");
			 strURL.append("&").append(methodParam).append("=").append(methd);
		 }
		 //Sort Order
		 String orderParam = new ParamEncoder("parent").encodeParameterName(TableTagParameters.PARAMETER_ORDER);
		 if(orderParam != null && request.getParameter(orderParam) != null) {
				String order = request.getParameter(orderParam);
				form.getAttributeMap().put("sortOrder", order);			
		 } else {
			 	String order = form.getAttributeMap().get("sortOrder") == null ? "1" : (String) form.getAttributeMap().get("sortOrder");
				strURL.append("&").append(orderParam).append("=").append(order); 
		 }
		 
		 forwardNew.setPath(strURL.toString());			
		 return forwardNew;		
	}
	
	/**
	 * To find if Next/Previous Pages are being accessed (as part of DisplayTag API)
	 * @param request
	 * @return
	 */
	public static boolean cvgPagination(HttpServletRequest request) {
		boolean found = false;
		Enumeration enm = request.getParameterNames();
		while(enm.hasMoreElements()) {
			String paramName = (String) enm.nextElement();
			if(paramName != null && paramName.startsWith("d-")) {
				found = true;
				break;
			}
		}
		
		return found;
	}	
	
	public static ActionForward personPaginate(BaseForm form, HttpServletRequest request, String name, ActionMapping mapping) {
		
		 ActionForward forwardNew = new ActionForward();
		 ActionForward af = mapping.findForward(name);
		 StringBuffer strURL = new StringBuffer(af.getPath());
		 //Put the pagination, method and sortOrder stuff here...
		 String pageParam = new ParamEncoder("searchResultsTable").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		 if(pageParam != null && request.getParameter(pageParam) != null) {
			String pageNo = request.getParameter(pageParam);
			form.getAttributeMap().put("PageNumber", pageNo);		
			request.getSession().setAttribute("pageNumberCustom", pageNo);
		 } else {
			 String pageNo = form.getAttributeMap().get("PageNumber") == null ? "1" : (String) form.getAttributeMap().get("PageNumber");
			 strURL.append("?").append(pageParam).append("=").append(pageNo); 
			 request.getSession().setAttribute("pageNumberCustom", pageNo);//In case we came back from investigation to search results,
			 //and a different page has not been selected, so we need to store the last one that was shown after coming back from the investigation.
			 
			 
		 }
		 //MethodNm
		 String methodParam = new ParamEncoder("searchResultsTable").encodeParameterName(TableTagParameters.PARAMETER_SORT);
		 if(methodParam != null && request.getParameter(methodParam) != null) {
				String methd = request.getParameter(methodParam);
				form.getAttributeMap().put("methodName", methd);				
		 } else {
			 String methd = form.getAttributeMap().get("methodName") == null ? "none" : (String) form.getAttributeMap().get("methodName");
			 strURL.append("&").append(methodParam).append("=").append(methd);
		 }
		 //Sort Order
		 String orderParam = new ParamEncoder("searchResultsTable").encodeParameterName(TableTagParameters.PARAMETER_ORDER);
		 if(orderParam != null && request.getParameter(orderParam) != null) {
				String order = request.getParameter(orderParam);
				form.getAttributeMap().put("sortOrder", order);			
		 } else {
			 	String order = form.getAttributeMap().get("sortOrder") == null ? "1" : (String) form.getAttributeMap().get("sortOrder");
				strURL.append("&").append(orderParam).append("=").append(order); 
		 }
		 NBSContext.store(request.getSession() ,NBSConstantUtil.DSAttributeMap, form.getAttributeMap());
		 forwardNew.setPath(strURL.toString());			
		 return forwardNew;		
	}
}