package gov.cdc.nedss.webapp.nbs.action.organization;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.person.util.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.form.organization.*;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;

/**
 * Title:        Actions
 * Description:  Find Organization
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:      CSC Development Team
 * @version 1.0
 */
public class OrganizationFind extends Action {
  static final LogUtils logger = new LogUtils(OrganizationFind.class.getName());


  public OrganizationFind() {
  }



  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
	  
	  try {
		  HttpSession session = request.getSession(false);
		  NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

		  String strType = request.getParameter("OperationType");
		  if (strType == null)
			  strType = (String) request.getAttribute("OperationType");

		  if (strType.equalsIgnoreCase(NEDSSConstants.SEARCH)){
			  OrganizationSearchForm osForm = (OrganizationSearchForm) form;
			  osForm.reset();
			  boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.ORGANIZATION, NBSOperationLookup.FINDINACTIVE);
			  request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));

			  // add role  security

			  boolean role = secObj.getPermission(NBSBOLookup.ORGANIZATION,"ROLEADMINISTRATION");
			  request.setAttribute("addRole", String.valueOf(role));
			  return mapping.findForward("searchCriteria");


		  } else if(strType.equalsIgnoreCase("entitySearch")){

			  OrganizationSearchForm orgForm = (OrganizationSearchForm) form;
			  orgForm.reset();
			  boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.ORGANIZATION, NBSOperationLookup.FINDINACTIVE);
			  request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));

			  ActionForward af = mapping.findForward("entitySearchCriteria");
			  ActionForward forwardNew = new ActionForward();
			  StringBuffer strURL = new StringBuffer(af.getPath());

			  strURL.append("&searchType=entity");
			  forwardNew.setPath(strURL.toString());
			  forwardNew.setRedirect(false);
			  return forwardNew;

		  } else if(strType.equalsIgnoreCase(NEDSSConstants.SEARCH_RESULTS)){

			  findOrganization(form,session,request,false);
			  request.setAttribute("searchPageOperationType",NEDSSConstants.SEARCH_RESULTS);
			  request.setAttribute("criteriaPageOperationType",NEDSSConstants.SEARCH);
			  request.setAttribute("organizationOperationType","view");



			  // gov.cdc.nedss.util.VOTester.createReport(osVO, "Testing VO - ");
			  // add button security
			  boolean bAddButton = secObj.getPermission(NBSBOLookup.ORGANIZATION, NBSOperationLookup.ADD);
			  request.setAttribute("addButton", String.valueOf(bAddButton));
			  String strCurrentIndex = (String)request.getParameter("currentIndex");

			  if(strCurrentIndex!=null) {

				  ActionForward af = mapping.findForward("searchResults");
				  ActionForward forwardNew = new ActionForward();
				  StringBuffer strURL = new StringBuffer(af.getPath());
				  strURL.append("&currentIndex=").append(strCurrentIndex);
				  forwardNew.setPath(strURL.toString());
				  forwardNew.setRedirect(false);
				  return mapping.findForward("searchResults");
			  } else
				  return mapping.findForward("searchResults");

		  } else if(strType.equalsIgnoreCase("entity")){

			  System.out.println("entity search for Organization");
			  findOrganization(form,session,request,true);

			  request.setAttribute("searchPageOperationType",NEDSSConstants.SEARCH_RESULTS);
			  request.setAttribute("criteriaPageOperationType",NEDSSConstants.SEARCH);
			  request.setAttribute("organizationOperationType","view");

			  String strCurrentIndex = (String)request.getParameter("currentIndex");
			  if(strCurrentIndex!=null) {
				  ActionForward af = mapping.findForward("searchResults");
				  ActionForward forwardNew = new ActionForward();
				  StringBuffer strURL = new StringBuffer(af.getPath());
				  strURL.append("&currentIndex=").append(strCurrentIndex);
				  forwardNew.setPath(strURL.toString());
				  forwardNew.setRedirect(false);
				  return forwardNew;
			  } else
				  return mapping.findForward("entitySearchResults");
		  }
	  }catch (Exception e) {
		  logger.error("Exception in Organization Find: " + e.getMessage());
		  e.printStackTrace();
		  throw new ServletException("General error occurred in  Organization Find : "+e.getMessage());
	  }
      	throw new ServletException();
		}//execute


        private void findOrganization(ActionForm form, HttpSession session,HttpServletRequest request, boolean bEntitySearch){

			ArrayList<?> organizationList = new ArrayList<Object> ();
			OrganizationSearchForm osForm = (OrganizationSearchForm) form;
			OrganizationSearchVO osVO = (OrganizationSearchVO) osForm.getOrganizationSearch();

			if (osVO != null){
				NedssUtils nedssUtils = null;
				MainSessionCommand msCommand = null;
				String sBeanJndiName = "";
				String sMethod = "";
				Object[] oParams = null;

				session.setAttribute("organizationFind", osVO);
				sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
				sMethod = "findOrganization";
				oParams = new Object[]{osVO, new Integer(PropertyUtil.getInstance().getNumberOfRows()), new Integer(0)};

				try {
					MainSessionHolder holder = new MainSessionHolder();
					msCommand = holder.getMainSessionCommand(session);
					ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
					organizationList = (ArrayList<?> )arrList.get(0);
					//gov.cdc.nedss.util.VOTester.createReport("****************************************WHAT WE GOT HERE - ");

	            } catch(Exception e){
				    e.printStackTrace();
	            }

				//session.setAttribute("result", organizationList);
				request.setAttribute("OrganizationSearchCollection",organizationList);

				//  build the criteria string
				StringBuffer sQuery = new StringBuffer("");
				CachedDropDownValues cache = new CachedDropDownValues();

				if (osVO.getNmTxt() != null && !osVO.getNmTxt().equals(""))
					sQuery.append("Name").append(" "+cache.getDescForCode("SEARCH_SNDX",osVO.getNmTxtOperator())+" ").append("'"+osVO.getNmTxt()+"'").append(", ");

				if (osVO.getStreetAddr1() != null && !osVO.getStreetAddr1().equals(""))
					sQuery.append("Street Address").append(" "+cache.getDescForCode("SEARCH_SNDX",osVO.getStreetAddr1Operator())+" ").append("'"+osVO.getStreetAddr1()+"'").append(", ");

				if (osVO.getCityDescTxt() != null && !osVO.getCityDescTxt().equals(""))
					sQuery.append("City").append(" "+cache.getDescForCode("SEARCH_SNDX",osVO.getCityDescTxtOperator())+" ").append("'"+osVO.getCityDescTxt()+"'").append(", ");

				if (osVO.getStateCd() != null && !osVO.getStateCd().equals(""))
					sQuery.append("State equals ").append("'"+getStateDescTxt(osVO.getStateCd())+"'").append(", ");

				if (osVO.getZipCd() != null && !osVO.getZipCd().equals(""))
					sQuery.append("Zip Code").append(" "+cache.getDescForCode("SEARCH_ALPHA",osVO.getZipCdOperator())+" ").append("'"+osVO.getZipCd()+"'").append(", ");

				if (osVO.getLocalID() != null && !osVO.getLocalID().equals(""))
				    sQuery.append("Organization ID equals ").append("'"+osVO.getLocalID()+"'").append(", ");

				if (osVO.getRootExtensionTxt() != null && !osVO.getRootExtensionTxt().equals("")) {
					if (osVO.getTypeCd() != null && !osVO.getTypeCd().equals(""))
						sQuery.append(osVO.getTypeCd()).append(" "+cache.getDescForCode("SEARCH_ALPHA",osVO.getRootExtensionTxtOperator())+" ").append("'"+osVO.getRootExtensionTxt()+"'").append(", ");
				}

				if (osVO.getRoleCd() != null && !osVO.getRoleCd().equals(""))
				  sQuery.append("Role equals ").append("'"+ cache.getDescForCode("RL_TYPE", osVO.getRoleCd())+"'").append(", ");

				request.setAttribute("searchCriteria",sQuery.toString());
			}//if
        }//findOrganization

        private String getStateDescTxt(String sStateCd) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<?,?> treemap = srtValues.getStateCodes1("USA");
			String desc="";
			if(sStateCd!=null && treemap.get(sStateCd)!=null)
				desc = (String)treemap.get(sStateCd);
			return desc;
        }//getStateDescTxt

 }//OrganizationFind