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
      NBS: Create New Investigation
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
	            <tr>
		     <td colspan=2 align="center">
	                <span>There is an open investigation for <%=request.getAttribute("condition")%>. A new investigation cannot be created.</span>
	             </td>
	            </tr>
	            </nedss:container>

	<div style="padding:1.0em 0em;"/>
	<div id="botProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
		<input type="button" align="right"  value="Cancel" onclick="closeDialog()"/>
	</div>
  </div>
  </body>
</html>
