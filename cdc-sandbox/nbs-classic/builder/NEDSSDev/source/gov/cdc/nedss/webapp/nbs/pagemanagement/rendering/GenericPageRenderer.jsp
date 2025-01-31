<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
    <head>
    <% String renderDir = request.getAttribute("renderDir").toString();
	   String basePg = renderDir + "/index.jsp";
	   String viewPg = renderDir + "/view/index.jsp";
	   String basePgCompare = renderDir + "/view/indexCompare.jsp";
	   
	   
	%>
    <title>NBS Page Management</title>
   <logic:notEqual name="PageForm" property="businessObjectType" value="CASE">
	    <base target="_self">
    </logic:notEqual>    
    </head>
    </head>  
      <div id="doc3">
   	<div id="bd">
	<html:form action="/PageAction.do">
	        <logic:notEqual name="PageForm" property="actionMode" value="View">
		        <logic:notEqual name="PageForm" property="actionMode" value="Compare">
	 				<logic:notEqual name="PageForm" property="actionMode" value="Merge">
	   				 <% 
	   				   ((RequestDispatcher)request.getRequestDispatcher(basePg)).include(request, response);
	   				 %>
		   			</logic:notEqual>
	   			</logic:notEqual>
   			</logic:notEqual>
        	<logic:equal name="PageForm" property="actionMode" value="View">
     				<% 
     				  ((RequestDispatcher)request.getRequestDispatcher(viewPg)).include(request, response);
     				%>
 			</logic:equal>        	
	</html:form>
        <div id="bd">
       </div>
       </div>
  </body>
</html>