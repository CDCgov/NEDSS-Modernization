<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.*" %>
<html lang="en">
  <head>
    <title>
      NBS: Associate Report to Investigation
    </title>
    <%@ include file="/jsp/resources.jsp" %>
  </head>
  <SCRIPT Language="JavaScript" Type="text/javascript" SRC="ProcessingDecision.js"></SCRIPT>
  
  <body onunload="closeDialog()" onload="windowResize()">
  <div style="padding:1.0em 0em;"/>
  <div id ="resizeDiv">
	        <nedss:container 
	            id="subsect_processingDecision"
	            name=""
	            classType="subSect"
	            displayImg="false"
	            includeBackToTopLink="no">
	            <logic:equal name="permission" value="true">
		            <tr>
				     	 <td colspan=2 align="center">
			                <span>There is an open investigation for <b><%=request.getAttribute("condition")%></b> for this patient. Do you want to associate this <%=request.getAttribute("report")%> to this investigation?</span>
			             </td>
		            </tr>
	            </logic:equal>
	            <logic:notEqual name="permission" value="true">
		            <tr>
				     	 <td colspan=2 align="center">
			                <span>There is an open investigation for <b><%=request.getAttribute("condition")%><b>; however you do not have permission to associate this 
			                <%=request.getAttribute("report")%><br>to this investigation assigned to <%=request.getAttribute("investigator")%></span>
			             </td>
		            </tr>
		            <tr>
				     	 <td colspan=2 align="center">
			                <span>nbsp;</span>
			             </td>
		            </tr>
		            <tr>
				     	 <td colspan=2 align="center">
			                <span>Please contact your Supervisor or System Administrator if additional assistance is needed.</span>
			             </td>
		            </tr>
	            </logic:notEqual>
	            </nedss:container>

   <div style="padding:1.0em 0em;"/>
	<div id="botProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
	
	<logic:equal name="permission" value="true">
		<logic:equal name="report" value="Morbidity Report">
			<logic:equal name="requestFrom" value="createInvestigation">
				<input type="button" align="right"  value= "  Ok  " onclick="createInvestigationFromViewMorbidityReport('<%=request.getAttribute("CurrentTask")%>')"/>
				
			</logic:equal>
			<logic:notEqual name="requestFrom" value="createInvestigation">
				<input type="button" align="right"  value= "  Ok  " onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','Associate')"/>
			</logic:notEqual>
		</logic:equal>
		<logic:equal name="report" value="Lab Report">
			<input type="button" align="right"  value= "  Ok  " onclick="markProcessingDecisionSelectCondition('LabAssociate')"/>
		</logic:equal>
	</logic:equal>
		<input type="button" align="right"  value="Cancel" onclick="closeDialog()"/>
	</div>
  </div>
  </body>
</html>
