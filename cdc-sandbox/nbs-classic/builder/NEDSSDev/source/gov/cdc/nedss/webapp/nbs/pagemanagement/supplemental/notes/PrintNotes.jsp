<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<html lang="en">
    <head>
        <title>NBS: Notes</title>

 <%@ include file="/jsp/tags.jsp" %>
 <%@ include file="/jsp/resources.jsp" %>


 <!--
    Page Summary:
    -------------
    This file includes the Notes from the supplimental tab for the print .
-->
	<% 
		int sectionIndex = 0;
		int subSectionIndex = 0; 
		String tabId = "viewSupplementalInformation";
	%>
<script type="text/javascript">
	
function closePrinterFriendlyWindow()
{
    self.close();
    var opener = getDialogArgument();   
    var pageId = '${fn:escapeXml(pageId)}'; 
    if(pageId == 'pageview')  
	var pview = getElementByIdOrByNameNode("pageview", opener.document);
    else
	var pview = getElementByIdOrByNameNode("pamview", opener.document);
      pview.style.display = "none";    
    return false;   
}
</script>	
	
	
   <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}     
           
    </style>
    </head>
     <% String PatientRevision = (request.getAttribute("PatientRevision") == null) ? "" : ((String)request.getAttribute("PatientRevision"));
       String caseLocalId = (request.getAttribute("DSInvUid") == null) ? "" : ((String)request.getAttribute("DSInvUid"));
      	String perMprUid =  (request.getAttribute("DSPatientPersonUID") == null) ? "" : ((String)request.getAttribute("DSPatientPersonUID"));
       String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));	
       if (printMode.equals("print")) { %>
        <body onload="autocompTxtValuesForJSP();startCountdown();" onunload="return closePrinterFriendlyWindow();" > 
       <%}%>
      <html:form>
	 <table role="presentation">
	<div class="view" id="<%= tabId %>" style="text-align:center;">   	 
	     <div class="printerIconBlock screenOnly">
						    <table role="presentation" style="width:98%; margin:3px;">
						        <tr>
						            <td style="text-align:right; font-weight:bold;"> 
						                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
						            </td>
						        </tr>
						    </table>
			               </div>          
	    </table>
	    <%
	        // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>
    <%@ include file="../../patient/PatientSummary.jsp" %> 
	    <!-- SECTION : Associations (Associated Lab Reports, Associated Morbidity Reports, 
	           Associated Treatments, and Associated Vaccination) --> 
	<div>
	    <!-- Notes and Attachment SECTION-->
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="Notes and Attachments" classType="sect">	    
	    <!-- SUB_SECTION : Notes -->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notes" classType="subSect" >
                <tr>
                    <td style="padding:0.5em;">
                        <display:table id="notesDTTable" name="nbsNotes" class="dtTable" defaultsort="4" defaultorder="ascending">
                             <display:column class="dateField" property="lastChgTime" title="Date Added" style="width:14%;"/>
                             <display:column class="nameField" property="lastChgUserNm" title="Added By" style="width:14%;"/>
                             <display:column property="note" title="Note"  style="width:60%;"/>
                             <display:column class="iconField" property="privateIndCd" title="Private" style="text-align:center;"/>
                             <display:setProperty name="basic.empty.showtable" value="true"/>
                         </display:table>
                    </td>
                </tr>
             </nedss:container>	    
        </nedss:container>        
	</div>
	<table role="presentation">
	    <div class="printerIconBlock screenOnly">
						    <table role="presentation" style="width:98%; margin:3px;">
						        <tr>
						            <td style="text-align:right; font-weight:bold;"> 
						                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
						            </td>
						        </tr>
					 </table>
	             </div>    
	       </div>    
	   </table>	 	               
 </html:form>
  </body>
</html>
