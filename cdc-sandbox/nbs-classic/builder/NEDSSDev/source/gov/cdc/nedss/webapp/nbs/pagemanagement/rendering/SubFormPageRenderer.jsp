<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
    <head>
    <title> NBS Page Management </title>
    <logic:notEqual name="PageSubForm" property="businessObjectType" value="CASE">
	    <base target="_self">
    </logic:notEqual>    
    </head>
	<% String renderDir = request.getAttribute("renderDir").toString();
	   String basePg = renderDir + "/index.jsp";
	   String viewPg = renderDir + "/view/index.jsp";
	   String basePgCompare = renderDir + "/view/indexCompare.jsp";
	   String basePgMerge = renderDir + "/indexMerge.jsp";
	   
	   
	%>       
      <div id="doc3">
   	<div id="bd">
	<html:form action="/PageSubFormAction.do">
			      
			   		
					<logic:equal name="PageSubForm" property="actionMode" value="View">
						 <%
						    ((RequestDispatcher)request.getRequestDispatcher(viewPg)).include(request, response);
						  %>
					</logic:equal>
					
					<logic:notEqual name="PageSubForm" property="actionMode" value="View">
						<logic:equal name="PageSubForm" property="actionModeParent" value="View">
						 <%
						    ((RequestDispatcher)request.getRequestDispatcher(viewPg)).include(request, response);
						  %>
						</logic:equal>
						<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
						 <%
						    ((RequestDispatcher)request.getRequestDispatcher(basePg)).include(request, response);
						  %>
						</logic:notEqual>
					</logic:notEqual>
 			       	
	</html:form>
        <div id="bd">
       </div>
       </div>
  </body>
</html>