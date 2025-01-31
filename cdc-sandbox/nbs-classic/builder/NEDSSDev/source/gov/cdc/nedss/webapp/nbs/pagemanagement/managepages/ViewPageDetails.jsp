<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants, gov.cdc.nedss.pagemanagement.util.PageMetaConstants" %>

<html lang="en">
    <head>
    	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <base target="_self">
        <title>NBS: Manage Pages</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
     <script language="JavaScript">    
       function editPageDetail()
       {
       	document.forms[0].action ="/nbs/ManagePage.do?method=editPageDetailsLoad";
		document.forms[0].submit();
       } 
       function clonePage()
       {
       	document.forms[0].action ="/nbs/ManagePage.do?method=clonePageLoad";
		document.forms[0].submit();
       }
      
       
       function showPrintFriendlyPage()
       {
          var divElt = getElementByIdOrByName("blockparent");
          divElt.style.display = "block";
          var o = new Object();
          o.opener = self;
                       
          var URL = "/nbs/ManagePage.do?method=viewPageDetailsLoad&mode=print";
          var dialogFeatures = "dialogWidth:780px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
         

                       
      	  
          var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
      	
          return false;
	}
			           
       function closePrinterFriendlyWindow()
        {
            self.close();
            var opener = getDialogArgument();        
            var pview = getElementByIdOrByNameNode("blockparent", opener.document)
            pview.style.display = "none";
            
            return false;   
        }
        
        function disablePrintLinksAndHideButtons() {
        	//$j("a[href]:not([href^=#])").removeAttr('href');
        	// change the return link to close the window
        	$j("a[href]:not([href^=#])").attr('href',"closePrinterFriendlyWindow();");
        	$j("a[href]:not([href^=#])").text('Return to View Page Details');
        	$j(".NotPrint").hide();
        }          

       </script>       
        <style type="text/css">
            
        </style>
    </head>
       <%
               Long waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid"); 
        %>
<!--
    Page Summary:
    -------------
    This file is View Page Details accessed from a button on View Page.
    Page metadata Page Name, Reporting Mechanism, Page Name, Page Desc
    and Related Conditions are shown.  From this screen you can Clone 
    a page or Edit the details.
-->


<% 
    int sectionIndex = 0;
    int subSectionIndex = 0;
    String tabId = "userInterface";
    String templateType = (String)request.getAttribute("templateType");
    String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
   
    if (printMode.equals("print")) { %>
    <body onload="startCountdown();disablePrintLinksAndHideButtons();" onunload="return closePrinterFriendlyWindow();">
    <% } else { %>
     <body onload="startCountdown();">
    <% } %>

    <div id="blockparent"></div>
      <html:form action="/ManagePage.do" styleId="viewPageDetailsForm">
        <div id="doc3">
             <tr><td>
                  <!-- Body div -->
	                <div id="bd">
                    	     <!-- Top Nav Bar and top button bar -->
                
                    		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
		     
                      <div align="right">
                        <a href="/nbs/PreviewPage.do?waTemplateUid=<%= waTemplateUid%>&method=viewPageLoad">Return to View Page </a>                 
                       </div>                     


	   		 <%if(request.getAttribute("mode") != null && request.getAttribute("mode").toString().equalsIgnoreCase("print")){ %>
				<div class="">
				 <% } else {%>
			<div class="grayButtonBar">
	   		  <% } %>

				<table role="presentation" width="100%">
				   <tr>
				   <%if(templateType != null && templateType .equals("Draft")){%>
					<td align="left" >
						<logic:equal name="showCloneButton" value="true">
							<input type="button" class="NotPrint" name="Clone Page" value="Clone Page" onclick="clonePage()"/>
						</logic:equal>
  					</td>
  				  <%}%>
				        <td align="right"> 
					         <%if(templateType != null && templateType .equals("Draft")){%>
						    	      <input type="button" class="NotPrint" name="Edit"  value="Edit"  onclick="editPageDetail();"/> &nbsp;
				    	      	  <%}%>
		                    	<input type="button" class="NotPrint" name="Print" value="Print" onclick="return showPrintFriendlyPage();" />
		                    </td>
				</tr>
				</table>
		      </div>   
		                 
		 
	   		<% if (request.getAttribute("ConfirmMesg") != null) { %>
	   			    <% if (request.getAttribute("ConfirmMesg1") != null && request.getAttribute("ConfirmMesg2") != null ) { %>
						<div class="infoBox success">${fn:escapeXml(ConfirmMesg1)}<b>${fn:escapeXml(ConfirmMesg)}</b>${fn:escapeXml(ConfirmMesg2)}</div>   
					<% } else { %>
				    	<div class="infoBox success">${fn:escapeXml(ConfirmMesg)}</div>
				    <% }
	   	     } %>
				
				<div class="printerIconBlock screenOnly">
					<table role="presentation" style="width:98%; margin:3px;">
					<tr>
						<td style="text-align:right; font-weight:bold;"> 
							<a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
						</td>
					</tr>
					</table>
				</div>
				<% if (request.getAttribute("messageInd") != null ) { %>
		      	<div class="infoBox info" style="text-align: left;">
				   Please note that any conditions that require data porting, (highlighted below), will utilize the legacy page
				   for data entry until the data porting process has been completed.
				</div>
				<%} %>	
							
    				<!-- SECTION : View Page Detail --> 
    				<nedss:container id="section1" name="View Page Details" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="no">
        				<!-- SUB_SECTION :Page Details -->
        				<nedss:container id="subSection1" name="Page Details" classType="subSect" displayImg ="false">
           					<!-- Event Type - currently always Investigation -->
           					<!-- <nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.busObjType" />  -->
           			<!-- Event Type -->		
          						<tr>
             						   <td class="fieldName" title="The type of event associated with the page.">
                 						<b>Event Type:</b>
               						  </td>
          						    <td>
            							<nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.busObjType" codeSetNm="BUS_OBJ_TYPE" />
         						    </td>
       							</tr>
       						<logic:equal name="pageBuilderForm"  property="selection.waTemplateDT.busObjType" value="INV">
       				<!-- Mapping Guide -->
       							<tr>
							    <td class="fieldName"   title="The reporting mechanism for the page (e.g, the name of the messaging guide that should be used to report to the CDC).">
								<b>Message Mapping Guide:</b>
							    </td>
							    <td>
	    							<nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.messageId" />
							    </td>
   							</tr>
   							</logic:equal>
   					<!-- Page Name -->
   							<tr>
   							    <td class="fieldName"  title="A name that uniquely identifies the page to users of the system.">
   							    	<b>Page Name:</b></span>
   							    </td>
   							    <td>
   								<nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.templateNm" />
							    </td>
							</tr>
					<!-- Data Mart Name -->
						<logic:equal name = "pageBuilderForm" property="selection.waTemplateDT.busObjType" value="INV" >
   							<tr>
   							    <td class="fieldName"  title="A name that uniquely identifies the Data Mart to users of the system.">
   							    	<b>Data Mart Name:</b></span>
   							    </td>
   							    <td>
   								<nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.dataMartNm" />
							    </td>
							</tr>
						</logic:equal>
            		<!-- Page Description -->
   							<tr>
   							    <td class="fieldName"  title="A description of the page.">
   								<b>Page Description:</b>
   							    </td>
							    <td>
								<nedss:view  name = "pageBuilderForm" property="selection.waTemplateDT.descTxt" />
							    </td>
  							</tr>
  					<!-- Related Conditions -->
  							<!-- Conditions --> 
  							<logic:equal name="pageBuilderForm"  property="selection.waTemplateDT.busObjType" value="CON">
  							<nedss:Condition  toolTip="The condition or conditions related to this page." busObjType="${pageBuilderForm.selection.waTemplateDT.busObjType}" name="conditionList" conditionLabel="Related Condition(s)" /> 
  							 </logic:equal>
  							<logic:equal name="pageBuilderForm"  property="selection.waTemplateDT.busObjType" value="INV">
  							<nedss:Condition  toolTip="The condition or conditions related to this page." busObjType="${pageBuilderForm.selection.waTemplateDT.busObjType}" name="conditionList" conditionLabel="Related Condition(s)" /> 
  							 </logic:equal>
  							<logic:equal name="pageBuilderForm"  property="selection.waTemplateDT.busObjType" value="IXS">
  							<nedss:Condition  toolTip="The condition or conditions related to this page." busObjType="${pageBuilderForm.selection.waTemplateDT.busObjType}" name="conditionList" conditionLabel="Related Condition(s)" /> 
  							 </logic:equal>
   					</nedss:container>
				</nedss:container>
                   
	   		 <%if(request.getAttribute("mode") != null && request.getAttribute("mode").toString().equalsIgnoreCase("print")){ %>
				<div class="">
				 <% } else {%>
				<div class="grayButtonBar">
	   		  <% } %>
	   	
				<table role="presentation" width="100%">
				   <tr>
				       <%if(templateType != null && templateType .equals("Draft")){%>
						<td align="left" >
							<logic:equal name="showCloneButton" value="true">
								<input type="button" class="NotPrint" name="Clone Page" value="Clone Page" onclick="clonePage()"/>
							</logic:equal>
	  					</td>
  					<%}%>

				        <td align="right"> 
				            <%if(templateType != null && templateType .equals("Draft")){%>
						    	<input type="button" class="NotPrint" name="Edit"  value="Edit"  onclick="editPageDetail();"/> &nbsp;
      				    	     <%}%> 	
		                    	<input type="button" name="Print" class="NotPrint" value="Print" onclick="return showPrintFriendlyPage();" />
		                    	</td>
				</tr>
				</table>
		        </div>  
		        <div align="right">
                        <a href="/nbs/PreviewPage.do?waTemplateUid=<%= waTemplateUid%>&method=viewPageLoad">Return to View Page </a>                 
                </div>  
			</div> <!-- id = "bd" -->
    		</td>
   	    </tr>
	</div> <!-- id = "doc3" -->
    </html:form>
    	<div class="printerIconBlock screenOnly">
    	<table role="presentation" style="width:98%; margin:3px;">
    		<tr>
    		<td style="text-align:right; font-weight:bold;"> 
    			<a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
    		</td>
    		</tr>
    		</table>
	</div>
  </body>
</html>