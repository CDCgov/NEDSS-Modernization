<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
   	<head>
	    <title> NBS Contact Page Management </title>
	    <base target="_self">
	</head>
	        		<% String renderDir = request.getAttribute("renderContactDir").toString();
	        			String indexNm = renderDir + "/index.jsp";
	        			String viewIndexNm = renderDir + "/view/index.jsp";
	        		%>
        <div id="doc3">
   		     <div id="bd">
	        	<html:form>
	        		<logic:notEqual name="contactTracingForm" property="actionMode" value="View">
	   			         <%
                           ((RequestDispatcher)request.getRequestDispatcher(indexNm)).include(request, response);
	   			       		//response.flushBuffer();
                          %>
   			        </logic:notEqual>
        			<logic:equal name="contactTracingForm" property="actionMode" value="View">
	   			         <%
                           ((RequestDispatcher)request.getRequestDispatcher(viewIndexNm)).include(request, response);
	   			       		//response.flushBuffer();
                          %>
 			       </logic:equal>
		        </html:form>
        <div id="bd">
       </div>
       </div>
  </body>
</html>