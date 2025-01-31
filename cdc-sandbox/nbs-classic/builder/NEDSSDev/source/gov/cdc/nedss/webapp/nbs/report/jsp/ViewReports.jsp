<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.ArrayList, java.util.HashMap,java.util.LinkedHashMap, java.util.TreeMap, java.util.Set, java.util.Iterator" %>
<%@ page import="gov.cdc.nedss.report.dt.ReportDT, gov.cdc.nedss.util.NEDSSConstants, gov.cdc.nedss.util.XMLRequestHelper, gov.cdc.nedss.report.util.ReportConstantUtil" %>
 <!--
    Page Summary:
    -------------
    This file includes the contents of the View Reports Page. This page will 
    contain all the reports (private, public and templates) that are available 
    for a user who is currently logged in. 
-->
<%!
	private Object getData(String pName, boolean pSession, HttpServletRequest request) {
		Object o = null;
		if(pSession == true)
			o = request.getSession().getAttribute(pName);
		else
			o = request.getAttribute(pName);
		return o;
	}
%>

<%
			  ReportConstantUtil rcu = new ReportConstantUtil();
            //  Initialize variables for loops.
            int intRecord = 0;
            int intRecords = 0;
            ArrayList al = null;
            String strRowBackground = null;

            //  Create temp variables.
            String strDataSourceUID = null;
            String strReportUID = null;
            String strTitle = null;
            String strDescription = null;
            String strDateCreated = null;
            String strStatus = null;
            String strReportType = null;
            String strOwnerUID = null;
            String strDateCreatedNum = null;

            //  Get data from the server.
            String strPrivateReports = (String)getData(rcu.PRIVATE_REPORT_LIST, true, request);
            String strPublicReports = (String)getData(rcu.PUBLIC_REPORT_LIST, true, request);
            String strReportTemplates = (String)getData(rcu.TEMPLATE_REPORT_LIST, true, request);
            String strReportingFacilityReports = (String)getData(rcu.REPORTING_FACILITY_REPORT_LIST, true, request);            

            //  Parse the XML data into object trees.
            ArrayList alPrivateReports = XMLRequestHelper.getTable(strPrivateReports);
            ArrayList alPublicReports = XMLRequestHelper.getTable(strPublicReports);
            ArrayList alReportTemplates = XMLRequestHelper.getTable(strReportTemplates);
            ArrayList alReportingFacilityReports = XMLRequestHelper.getTable(strReportingFacilityReports);

%>
<html lang="en">
    <head>
        <title> NBS: Reports (Private, Public and Templates) </title>
        <%@ include file="../../jsp/resources.jsp" %>
        <SCRIPT language="JavaScript" src="avr.reports.js"></SCRIPT>
        <script type="text/javascript">
        	var <%=XMLRequestHelper.xmlEncodeArrayListToJavaScript(alPrivateReports, "aPrivate")%>
			var <%=XMLRequestHelper.xmlEncodeArrayListToJavaScript(alPublicReports, "aPublic")%>
			var <%=XMLRequestHelper.xmlEncodeArrayListToJavaScript(alReportTemplates, "aTemplates")%>
			var <%=XMLRequestHelper.xmlEncodeArrayListToJavaScript(alReportingFacilityReports, "aReportingFacility")%>			
			function openReportPopup(ReportUID, DataSourceUID) {
				var block = getElementByIdOrByName("doc3");
				block.style.display = "block";
				var o = new Object();
				o.opener = self;
			//	var returnMessage = window.showModalDialog("/nbs/RunReport.do?method=LoadFilterCriteria&ReportUID="+ReportUID+"&DataSourceUID="+DataSourceUID, o, "dialogWidth: " + 1200 + "px;dialogHeight: " +
		             //   800 + "px;status: no;unadorned: yes;scroll: yes;help: no; resizable: yes;");
				
				var URL = "/nbs/RunReport.do?method=LoadFilterCriteria&ReportUID="+ReportUID+"&DataSourceUID="+DataSourceUID;
				var dialogFeatures = "dialogWidth: " + 1200 + "px;dialogHeight: " +
                800 + "px;status: no;unadorned: yes;scroll: yes;help: no; resizable: yes;";
				
				 var modWin = openWindow(URL, o,dialogFeatures, block, "");
				 
				 
				 
				return false;
			}
            /* Execute the report selected */
		    function runReport(reportType, reportUID, reportDataSourceId)
		    {
                // FIXME : Since the current logic to run all report types is the same,
                // there is a single if grouping.
                if (reportType == "P" || reportType == "S" || reportType == "T" || reportType == "R")
                {
                    // set the form values and post the form 
                    document.frm.mode.value = "edit";
					document.frm.ObjectType.value = <%= NEDSSConstants.REPORT %>;
                    document.frm.OperationType.value = <%= NEDSSConstants.REPORT_BASIC %>;
					document.frm.DataSourceUID.value = reportDataSourceId;
					document.frm.ReportUID.value = reportUID;

				    document.frm.action = "/nbs/nfc";
				    document.frm.method = "post";
				    document.frm.target = "_self";

				    document.frm.submit();
                }
		    }
		    
		    /* Delete the report selected */
		    function deleteReport(reportType, reportUID, reportDataSourceId)
            {
                var choice = confirm("Do you want to delete the report?");
                if (choice) {
	                    document.frm.mode.value = "edit";
	                    document.frm.ObjectType.value = <%= NEDSSConstants.REPORT %>;
	                    document.frm.OperationType.value = <%= NEDSSConstants.REPORT_DELETE %>;
	                    document.frm.DataSourceUID.value = reportDataSourceId;
	                    document.frm.ReportUID.value = reportUID;
	                    
	                    //  Post the form.
	                    document.frm.action = "/nbs/nfc";
	                    document.frm.method = "post";
	                    document.frm.target = "_self";
	                    document.frm.submit();
                }
            }

            /* JQuery stuff. In page sorting of table data is handled here. */
	        $j(document).ready(function() { 
				// extend the default setting to always include the zebra widget. 
				$j.tablesorter.defaults.widgets = ['zebra'];

				// call the tablesorter plugin 
				$j(".dtTable").tablesorter(
				                            {
				                                headers : {
				                                            0:{
				                                                sorter:false
				                                               },
				                                            1:{
				                                                sorter:'ahref'
				                                              }
				                                          },
				                                sortList: [[1,0]]            
				                            }
				                          );

                // call the tablesorter plugin 
                $j(".privateDtTable").tablesorter(
                                            {
                                                headers : {
                                                            0:{
                                                                sorter:false
                                                               },
                                                            1:{
                                                                sorter:false
                                                               },   
                                                            2:{
                                                                sorter:'ahref'
                                                              }
                                                          },
                                                sortList: [[2,0]]            
                                            }
                                          );
                });                                          
        </script>
        
        <style type="text/css">
            /* In the common.css file, tr of a table of class 'subSect' is set to
            have a gray colored background. Reset it to #FFF (white) here */

            table.subSect tbody tr {background:#FFF;}
        </style>
    </head>
    
    <body onload="startCountdown();">
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
           <form name="frm">
                <input type="hidden" name="mode" />
                <input type="hidden" name="ObjectType" />
                <input type="hidden" name="OperationType" />
                <input type="hidden" name="DataSourceUID" />
                <input type="hidden" name="ReportUID" />
           </form>    
           

        
            <!-- Body div -->
            <div id="bd">
                <!-- Top Nav Bar and top button bar -->
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                
				            <% if (request.getAttribute("errorDataSource") != null) { %>
                        <div class="infoBox errors">
	                        <ul>
	                          <li>
	                              <%=request.getAttribute("errorDataSource") == null ? "" : request.getAttribute("errorDataSource")%>      
	                          </li>
	                        </ul>
	                    </div>    
                    <% } %>
					
                <!-- SECTIONS TOGGLER. Expand/Collapse all sections inside the 'bd' Div element  -->
                <%
                    LinkedHashMap allReports = (LinkedHashMap) request.getAttribute("ReportsTree");
                    Set reportTypeKeySet = (Set) allReports.keySet();
                    if (reportTypeKeySet.size() > 0)
                    {
                        int loopIndex = 0;
                %>
	                    <table role="presentation" class="sectionsToggler" style="width:100%;">
		                    <tr>
		                        <td>
		                            <ul class="horizontalList">
										<li style="margin-right:5px;"><b>Go to: </b></li>
										<%
										     Iterator reportTypeKeySetIter = reportTypeKeySet.iterator();
										     while (reportTypeKeySetIter.hasNext()) 
										     {
										          loopIndex++;
										          String reportTypeKey = (String) reportTypeKeySetIter.next();
										%>
										          <li style="text-transform:capitalize;"><a href="javascript:gotoSection('<%= reportTypeKey.replaceAll(" ", "") %>')"><%= reportTypeKey.replaceAll("_", " ") + " Reports"%></a></li>
										        <% if (loopIndex < reportTypeKeySet.size()) { %>
										          <li class="delimiter"> | </li>
										        <% } %>
										<%
										     }
										%>
							        </ul>
							    </td>
							</tr>
                            <tr>
		                        <td style="padding-top:1em;">
		                            <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('bd')"/>Collapse Sections</a>
		                        </td>
                            </tr>
                        </table>
                <% } %>

                <% 
                    // Variables used to set unique Ids to the HTML elements when the page is renedered.
				    int subSectionIndex = 0; // unique across all sections
				%>

                <%
                    // Iterate through the reports tree
                    Iterator reportTypeKeySetIter = reportTypeKeySet.iterator();
                    while (reportTypeKeySetIter.hasNext()) 
                    {
                        // reportTypeKey will be used to identify a section uniquely.
                        String reportTypeKey = (String) reportTypeKeySetIter.next();
                %>
                        <!-- SECTION -->
                        <nedss:container id='<%= reportTypeKey.replaceAll(" ", "") %>'  name='<%=reportTypeKey.replaceAll("_"," ") + " Reports" %>' classType="sect" defaultDisplay="collapse">
                            <% Set reportSectionKeySet = ((TreeMap)((TreeMap)allReports.get(reportTypeKey))).keySet(); %>
                            
                            <% if (reportSectionKeySet.size() <= 0) { %>
                                <div style="text-align:left;"> There are no <%=reportTypeKey.replaceAll("_"," ") %> reports available. </div>
                            <% } %>
                        
                            <% Iterator reportSectionKeySetIter = reportSectionKeySet.iterator();
                               while (reportSectionKeySetIter.hasNext()) 
                               {
                                    subSectionIndex++;
                                    String reportSectionKey = (String) reportSectionKeySetIter.next();
                                    ArrayList sectionReports = (ArrayList) ((TreeMap)allReports.get(reportTypeKey)).get(reportSectionKey);
                                    request.setAttribute("sectionReports", sectionReports);
                            %>
					                <!-- SUB_SECTION -->
					                <nedss:container id='<%= reportTypeKey.replaceAll(" ", "") + (++subSectionIndex) %>' 
					                        name="<%= reportSectionKey %>" classType="subSect" defaultDisplay="collapse">
		                                <tr>
		                                   <td colspan="4">
		                                       <% 
		                                           // Display tag table class    
		                                           String dtTableClass = "dtTable inPageSortable";
		                                           if (reportTypeKey.equalsIgnoreCase("private")||reportTypeKey.equalsIgnoreCase("Reporting_Facility")) {
		                                               dtTableClass = "privateDtTable inpageSortable";
		                                           }
		                                           
		                                           // display tag table id (unique across all sections)
		                                           String dtIndex = "DT-" + subSectionIndex;
		                                                                                    
		                                       %>
		                                       
	                                           <display:table name="sectionReports" class="<%= dtTableClass %>" id="<%= dtIndex %>">
	                                            <% if ((HTMLEncoder.encodeHtml(String.valueOf(((ReportDT)pageContext.getAttribute(dtIndex)).isRunPermission())).equals("true"))) {
	     		                                           if(pageContext.getAttribute(dtIndex)!=null && ((ReportDT)pageContext.getAttribute(dtIndex)).getReportUid()!=null && ((ReportDT)pageContext.getAttribute(dtIndex)).getDataSourceUid()!=null)
															%>
                                                        	<display:column  property="reportActionRun" title="<p style='display:none'>Action</p>" style="width:4em; text-align:center;"/>
                                                       <% }%><% else{%>
	                                                       <display:column title="<p style='display:none'>Action</p>" style="width:4em; text-align:center;">
	                                                        	<span style=\"color:#666666;\">Run</span>
	                                                        </display:column>
                                                         <%}%>

	                                               <%if (reportTypeKey.equalsIgnoreCase("private")||reportTypeKey.equalsIgnoreCase("Reporting_Facility") ){%>  
	                                               <% if ((reportTypeKey.equalsIgnoreCase("private")||reportTypeKey.equalsIgnoreCase("Reporting_Facility")) && HTMLEncoder.encodeHtml(String.valueOf(((ReportDT)pageContext.getAttribute(dtIndex)).isDeletePermission())).equals("true")) { %>
	                                                        <display:column  property="reportActionDelete" title="<p style='display:none'>Action</p>" style="width:4em; text-align:center;"/>
													<% } else if((reportTypeKey.equalsIgnoreCase("private")||reportTypeKey.equalsIgnoreCase("Reporting_Facility")) && ! HTMLEncoder.encodeHtml(String.valueOf(((ReportDT)pageContext.getAttribute(dtIndex)).isDeletePermission())).equals("true" )){%>
														<display:column title="<p style='display:none'>Action</p>" style="width:4em; text-align:center;">
															<span style="color:#666666;">Delete</span>
														</display:column>
													<%}%>
	                                               <%}%>
	                                               <display:column  property="reportActionDetail" title="Report Title" class="hoverDescLink"/>
	                                               <display:column property="addTime" title="Date Created" format="{0,date,MM/dd/yyyy}" style="width:7em;"/>
	                                           </display:table>
		                                   </td>
	                                    </tr>
	                                </nedss:container>
	                           <%
		                        }
		                       %>
	                    </nedss:container>
                        <%
                        }
                        %>
            </div> <!-- div id=bd -->
        </div> <!-- div id=doc3 -->
    </body>
</html>