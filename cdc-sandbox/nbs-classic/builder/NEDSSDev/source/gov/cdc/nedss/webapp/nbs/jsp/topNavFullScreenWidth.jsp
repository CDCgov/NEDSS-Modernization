<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, gov.cdc.nedss.util.PageConstants, gov.cdc.nedss.util.PropertyUtil" %>
<%@ page isELIgnored ="false" %>

	<meta http-equiv="cache-control" content="no-cache">
	
	<meta http-equiv="expires" content="0">
	<meta http-equiv="pragma" content="no-cache">
	

	<SCRIPT LANGUAGE="JavaScript">
		function startCountdown() {
			var sessionTimeout = <%= request.getSession().getMaxInactiveInterval()%>
			min = sessionTimeout / 60;
			sec = 0;				
			getTimerCountDown();
			
			addTabs();
			addRolePresentationToTabsAndSections();
		}
		
		
		
		$j(document).ready(function(){	 	
			   listenersToPreventBackButton();

	    });
		
		
		
	</SCRIPT>  	
    <%
		NBSSecurityObj nbsSecObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		if(request.isRequestedSessionIdValid() == false)
		{
			response.sendRedirect(PageConstants.TIMEOUTPAGE);
		}
		if(nbsSecObj == null)
		{
			response.sendRedirect(PageConstants.TIMEOUTPAGE);
		}
		String myPAInvestigationsSecurity = PropertyUtil.getInstance().getMyProgramAreaSecurity();
    %>
    <!-- Navigation Bar -->
    <table role="presentation" class="nedssNavTable">
        <tr>
            <td>
                <table role="presentation" align="left">
                    <tr>
                        <td class="navLink" >
                            <html:link href ="/nbs/HomePage.do?method=loadHomePage">Home</html:link> 
                        </td>
                        <td> <span> | </span> </td>
							 <%
				            if(nbsSecObj!=null)
				                if
				                (
				                    (nbsSecObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.FIND))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.ORGANIZATION, NBSOperationLookup.MANAGE))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.PROVIDER, NBSOperationLookup.MANAGE))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD, "ANY", "ANY"))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD, "ANY", "ANY"))
				                )
				                {							 
							 %>                        
                        <td class="navLink" >
                            <html:link href ="/nbs/LoadNavbar.do?ContextAction=DataEntry">Data Entry</html:link>
                        </td>
                        <td><span> | </span></td>
                        <%}%>
                        <%
								if(nbsSecObj!=null && nbsSecObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.MERGE))
			                {
                        %>
                        <td class="navLink" >
                            <html:link href ="/nbs/LoadNavbar1.do?ContextAction=MergePerson">Merge Patients</html:link>
                        </td>
                        <td><span> | </span></td>
                        <%}%>                        
                        <%
			                if(nbsSecObj!=null && nbsSecObj.getPermission(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity))
			                {                        
                        %>
                        <td class="navLink" >
                            <html:link href ="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true">Open Investigations</html:link>
                        </td>
                        <td><span> | </span></td>
                        <%}%>
                        <%
			               if(nbsSecObj!=null)
			                if
			                (
			                    (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE))
			                ||  (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC))
			                ||  (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE))
							   ||  (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY))			                
			                )
			                {                        
                        %>
                        <td class="navLink" >
                            <html:link href ="/nbs/nfc?ObjectType=7&OperationType=116">Reports</html:link>
                        </td>
                        <td><span> | </span></td>
                        <%}%>
                        <%
				            if(nbsSecObj!=null)
				                if
				                (  (nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN)) 
				                ||  (nbsSecObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEWELRACTIVITY))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.SRTADMIN))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.CASEREPORTING, NBSOperationLookup.VIEWPHCRACTIVITY))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.IMPORTEXPORTADMIN))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.REPORTADMIN))
				                ||  (nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN))
				                ||  (nbsSecObj.isUserMSA() || nbsSecObj.isUserPAA() || nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION))
				                )
				                {                        
                        %>
                        <td class="navLink" >
                            <html:link href ="/nbs/SystemAdmin.do">System Management</html:link>
                        </td>
                        <%}%>
                    </tr>
                </table>
            </td>
            <td>
                <table role="presentation" align="right">
                    <tr>
                        <td class="navLink" >
                            <a href="/nbs/UserGuide.do?method=open" target="_blank"> Help </a>
                        </td>
                        <td><span> | </span></td>
                        <td class="navLink">
                            <html:link href ="/nbs/logout">Logout</html:link>
                        </td>
                    </tr>
                </table>
            </td>
            <td style=width:105px;>
                &nbsp;
            </td>
        </tr>
    </table>

   <!-- Page Header, Currently logged in User, System Logo -->
   <h1  class="pageHeader"  style="padding: 0px; margin: 0px; font-size: 13px;">
   <table role="presentation" class="nedssPageHeaderAndLogoTable">
        <tbody>
            <tr>
                <!-- Page title -->
               <logic:present name="PageTitle" scope="request">
                    <td class="pageHeader" style="padding-bottom:0px; margin-bottom:0px;">
                    <a name=pageTop>
                         <%= request.getAttribute("PageTitle") %>
                        </a>
                    </td>
                </logic:present>
                            
                <logic:notPresent name="PageTitle" scope="request">
                   <logic:present name="BaseForm">
                    <td class="pageHeader" style="padding-bottom:0px; margin-bottom:0px;">
                        <a name=pageTop>
                        ${BaseForm.pageTitle}
                        </a>
                    </td>
                    <input type="hidden" id="actionMode" value="${BaseForm.actionMode}"/>
                    
                   </logic:present>
                </logic:notPresent>
                <input type="hidden" id="isTestVar" value="<%= request.getSession().getAttribute("isTest") %>"/> 
                <!-- Logged in User -->
                <%
                    String fullName = "";
                    try {
                        NBSSecurityObj so = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
                        if (so != null) {
                             fullName = so.getTheUserProfile().getTheUser().getFirstName() + " " + so.getTheUserProfile().getTheUser().getLastName();
                        }   
                    }
                    catch (Exception e) {
                    }           
                %>
                <td class="currentUser" style="padding-bottom:0px; margin-bottom:0px;">
                    User : <%= fullName %> 
					
			
                </td>
                
                <!-- NBS states logo -->
                <td class="currentUser logo" style="padding-bottom:0px; margin-bottom:0px;">
                    <img style="background:#DCDCDC" title="Logo" alt="NBS Logo" border="0" height="32" width="80" src="../../images/nedssLogo.jpg">
                </td>
            </tr>
            <tr>
                <td colspan="3" style="padding:0px; margin:0px; height:9px;
                        background-image: url(dropshadow.gif); background-repeat: repeat-x">
						
                </td>
            </tr>
			
			
        </tbody>
   </table>
   </h1>