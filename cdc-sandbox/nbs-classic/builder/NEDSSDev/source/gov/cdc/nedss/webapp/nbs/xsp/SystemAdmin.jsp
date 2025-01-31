<%@ include file="/jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, 
                 gov.cdc.nedss.util.PageConstants, 
                 gov.cdc.nedss.util.PropertyUtil" %>


<html lang="en">
    <head>
        <title> NBS:System Management</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
			function getElem(id){
			if (document.layers) return document.layers[id];
			else if (document.all) return document.all[id];
			else if (getElementByIdOrByName) return getElementByIdOrByName(id);
			}
			function swapFolder(img){
			objImg=getElem(img);
			if(objImg.src.indexOf('tree.gif')>-1){
			objImg.src='open.gif';
			objImg.alt='Open';
			objImg.title='Open';
			}
			else
			objImg.src='tree.gif';
			swapSub('s'+img);
			}
			function swapText(img){
			objImg=getElem(img);
			if(objImg.innerText.indexOf('+')>-1)
			objImg.innerText='-';
			else
			objImg.innerText='+';
			swapSub('s'+img);
			}
			function swapSub(img){
			elem=getElem(img);
			objImg=elem.style;
			if(objImg.display=='block')
			objImg.display='none';
			else
			objImg.display='block';
			}
			
			function resetLabCache(element){
							var confirmMsg="Are you sure you want to reset Lab Mapping Cache?";
							if (confirm(confirmMsg)) {
								element.href ="/nbs/ResetCache.do?method=resetLabMappingCache";
							}     	
			}
			function resetCVGCache(element){
							var confirmMsg="Are you sure you want to reset Code Value General Cache?";
							if (confirm(confirmMsg)) {
								element.href ="/nbs/ResetCache.do?method=resetCodeValueGeneralCache";
							}
			}	
			function focus()
			{
					var focus = "collapse"
					focus='${fn:escapeXml(focus)}';
					if(focus=='systemAdmin3')
						focus = "expand";
			}
		
	</script>
	<style type="text/css">
		/* In the common.css file, tr of a table of class 'subSect' is set to
		have a gray colored background. Reset it to #FFF (white) here */
		
		/*table.subSect tbody tr {background:#FFF;}*/
	</style>
    </head>
    
    <body onload="startCountdown();">
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
         	<div id="bd">
                <!-- Top Nav Bar and top button bar -->
                <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
                <%
					String confirmMsg= request.getAttribute("confirmMsg") == null ? "" : (String) request.getAttribute("confirmMsg");
					if(! confirmMsg.equals("")) {
				%>
				<table role="presentation" width="100%">
					<tr  align="center">
							      <td align="center" style="border:2px solid blue;" width="100%">
				      			<% if(confirmMsg.indexOf("Failure") != -1) { %>
						      			<font class="boldTenRed">
					      		<%} else {%>	
					       			<font class="boldTenBlack">
					       	<%}%>	
				       			<span id="error1">
						  				${fn:escapeXml(confirmMsg)}
						  			</span>
				              </font>
						</td>
					</tr>
				</table>
				<%} %>

<% 
    int subSectionIndex = 0;
    String tabId = "systemAdmin";
    String []sectionNames = {"System Management"};
    int sectionIndex = 0;
    
    String focus1 = "collapse";
    String focus2 = "collapse";
    String focus3 = "collapse";
    String focus31 = "collapse";
    String focus32 = "collapse";
    String focus4 = "collapse";
    String focus5 = "collapse";
    String focus6 = "collapse";
    String focus7 = "collapse";
    if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin1"))
    	focus1 ="expand";
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin2"))
    	focus2 ="expand";
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin3"))
    	focus3 ="expand";
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin31"))
    {
    	focus3 ="expand";
    	focus31 ="expand";
    }
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin32"))
    {
    	focus3 ="expand";
    	focus32 ="expand";
    }
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin4"))
    	focus4 ="expand";
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin5"))
    	focus5 ="expand";
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin6"))
    	focus6 ="expand";
    else if(request.getParameter("focus")!=null && request.getParameter("focus").equals("systemAdmin7"))
    	focus7 ="expand";
	
%>
	
			<div class="view"  id="<%= tabId %>" style="text-align:center;"></div>               
             <nedss:container  includeBackToTopLink="no" id="<%= \"sect_\"  + sectionIndex %>" name="<%= sectionNames[sectionIndex++] %>" classType="sect" defaultDisplay="collapse">
		        <!-- SUB_SECTION : Investigation Details -->
		        <!-- Alert Administration -->
		        <%if(nbsSecObj!=null)
                    if(nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN))
                    {
				 %>
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Decision Support Management" classType="subSect" defaultDisplay="<%=focus1%>">
		        	<tr>
					<td style="padding-left:20px;">
						<table role="presentation" class="srtmanage">
							<tr><td align="left" valign="top" nowrap>&nbsp;<a	href="/nbs/AlertUser.do?method=alertAdminUser">Manage Alerts</a></td></tr>
				        	      <tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/EmailAlert.do?method=loadEmail">Manage User Email</a></td></tr>
				        	      <tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ManageDecisionSupport.do?method=loadqueue&initLoad=true">Manage Workflow Decision Support</a></td></tr>
	                                 </table>
					</td>
				</tr>
		        </nedss:container>
		        <%} %>  
		        <!-- Epi-Link Administration -->
		        <%if(nbsSecObj!=null)
                    if(nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN))
                    {
				 %>
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Epi-Link (Lot Number) Management" classType="subSect" defaultDisplay="<%=focus7%>">
		        	<tr>
					<td style="padding-left:20px;">
						<table role="presentation" class="srtmanage">
							<tr><td align="left" valign="top" nowrap>&nbsp;<a	href="/nbs/EpiLinkAdmin.do?method=mergeEpilink">Manage Epi-link ID</a></td></tr>
				        	      <tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/EpiLinkLogAdmin.do?method=loadActivityLog">Manage Epi-link ID Activity log</a></td></tr>
				        	           </table>
					</td>
				</tr>
		        </nedss:container>
		        <%} %>	
		         <!-- Laboratory Management -->
		        <%if(nbsSecObj!=null)
                    if(nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.SRTADMIN))
                    {
				 %>
				<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Case Report and Laboratory Management" classType="subSect" defaultDisplay="<%=focus3%>">
				  <tr>
					<td style="padding-left:20px;">
							<table role="presentation" class="srtmanage">
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/TriggerCodes.do?method=manageLoad&initLoad=true">Manage Trigger Codes for Case Reporting</a></td></tr>

								<tr><td align="left" valign="top">&nbsp;<a href="/nbs/Laboratories.do?method=searchLab" name='lab'>Manage Laboratories</a></td></tr>
							    <nedss:container id="subSec1" name="Manage Lab Results" classType="subSect" defaultDisplay="<%=focus31%>">
									<tr>
										<td style="padding-left:20px;">
											<table role="presentation" class="srtmanage">
												<tr><td align="left" valign="top" nowrap><a href="/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabLoad">Manage Local Results</a></td></tr>
												<tr><td align="left" valign="top" nowrap><a href="/nbs/SnomedCode.do?method=manageSnomedCodeLoad">Manage SNOMEDs</a></td></tr>
												<tr><td align="left" valign="top" nowrap><a href="/nbs/ExistingResultsSnomedLink.do?method=searchResultSnomedLinkLoad">Manage Link between Lab Results and SNOMED</a></td></tr>
												<tr><td align="left" valign="top" nowrap><a href="/nbs/SnomedtoConditionLink.do?method=searchSnomedtoCondLinkLoad">Manage Link between SNOMED and Condition</a></td></tr>			
											</table>
										</td>
									</tr>
								</nedss:container> 
								<nedss:container id="subSec2" name="Manage Lab Tests" classType="subSect" defaultDisplay="<%=focus32%>">
									<tr>
										<td style="padding-left:20px;">
											<table role="presentation" class="srtmanage">
											<tr><td align="left" valign="top" nowrap><a href="/nbs/LDLabTests.do?method=searchLabLoad">Manage Local Lab Tests</a></td></tr>
											<tr><td align="left" valign="top" nowrap><a href="/nbs/ManageLoincs.do?method=manageLoincs">Manage LOINCs</a></td></tr>
											<tr><td align="left" valign="top" nowrap><a href="/nbs/LabTestLoincLink.do?method=searchLoincLoad">Manage Link between Lab Tests and LOINC</a></td></tr>
											<tr><td align="left" valign="top" nowrap><a href="/nbs/LoinctoConditionLink.do?method=searchLoinctoCondLinkLoad">Manage Link between LOINC and Condition</a></td></tr>			
														
											</table>
										</td>
									</tr>
							    </nedss:container> 
								<tr><td align="left" valign="top" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="resetLabCache(this)">Reset Lab Mapping Cache</a></td></tr>
							</table>
						</td>
					</tr> 
		        </nedss:container>
		        <%} %>
		        <!-- Messaging Management -->
		        <%		        
		        if(nbsSecObj!=null)
		        {
		        	boolean isElrPermited = nbsSecObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEWELRACTIVITY);
		        	boolean isPhcrPermited = nbsSecObj.getPermission(NBSBOLookup.CASEREPORTING, NBSOperationLookup.VIEWPHCRACTIVITY);
		        	boolean isSRSystemPermited = nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.IMPORTEXPORTADMIN);
		        	
                    if(isElrPermited || isPhcrPermited || isSRSystemPermited)
                    {
				 %>
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Messaging Management" classType="subSect" defaultDisplay="<%=focus2%>">
		        	<tr>
						<td style="padding-left:20px;">
							<table role="presentation" class="srtmanage">
							<%
							if(isElrPermited)		                    
		                    {
						 	%>
						 	<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/LoadDSMActivityLog.do?method=searchActivityLog&param1=11648804">Manage ELR Activity Log</a></td></tr>
							<%} 
							if(isPhcrPermited)
		                    {
						 	%>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/LoadDSMActivityLog.do?method=searchActivityLog&param1=PHC236">Manage PHCR Activity Log</a></td></tr>
							<%} if(isSRSystemPermited)
		                    {%>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ReceivingSystem.do?method=manageLoad&initLoad=true">Manage Sending and Receiving Systems</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ManageHivPartnerServices.do?method=loadFileInfo&initLoad=true">Manage HIV Partner Services File</a></td></tr>
							<%} %>
							</table>
						</td>
					</tr>
		        </nedss:container>
		        <%}} %>
		        <!-- Page Management -->
		        <%if(nbsSecObj!=null)
                    if(nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION))
                    { 
				 %>
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Page Management" classType="subSect" defaultDisplay="<%=focus4%>">
		        	<tr>
						<td style="padding-left:20px;">
							<table role="presentation" class="srtmanage">
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true">Manage Conditions</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="ManagePage.do?method=list&initLoad=true">Manage Pages</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true">Manage Questions</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&initLoad=true">Manage Templates</a></td></tr>  
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true">Manage Value Sets</a></td></tr>
							</table>
						</td>
					</tr>
		        </nedss:container>
		        <%} %>
		        <!-- Reports Management-->
		        <% if(nbsSecObj!=null)
                    if(nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.REPORTADMIN))
                    {
				 %>
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Report Management" classType="subSect" defaultDisplay="<%=focus5%>">
		        	<tr>
						<td style="padding-left:20px;">
							<table role="presentation" class="srtmanage">
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ListDataSource.do">Manage Data Sources</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ListReport.do">Manage Reports</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/ListReportSections.do">Manage Report Sections</a></td></tr>
							</table>
						</td>
					</tr>
		        </nedss:container>
		        <%} %>
		        <!--Security Management  -->
		        <% if(nbsSecObj!=null)
           			if (nbsSecObj.isUserMSA() || nbsSecObj.isUserPAA())
           			{
				 %>
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Security Management" classType="subSect" defaultDisplay="<%=focus6%>">
		           <tr>
					 <td style="padding-left:20px;">
						<table role="presentation" class="srtmanage">
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="<%=request.getAttribute("permSetUrl")%>">Manage Permission Sets</a></td></tr>
							<tr><td align="left" valign="top" nowrap>&nbsp;<a href="/nbs/userList.do">Manage Users</a></td></tr>
						</table>
						</td>
					</tr>
		        </nedss:container>
		        <%} %>
		    </nedss:container>    
            </div><!-- bd -->
        </div><!-- doc3 -->
    </body>
</html>