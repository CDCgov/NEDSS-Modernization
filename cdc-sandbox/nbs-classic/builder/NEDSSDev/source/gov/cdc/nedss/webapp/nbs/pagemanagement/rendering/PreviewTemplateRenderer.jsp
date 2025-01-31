<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
    <head>
        <title> NBS: Manage Templates </title>
    </head>
     <% String renderDir = request.getAttribute("renderDir").toString();
         String cond = renderDir + "/index.jsp";
         String viewCond = renderDir + "/view/index.jsp";
     %>
     <body>
        <div id="doc3">
             <div id="bd">
                <html:form action="/ManageTemplates.do">
                       
                    <logic:equal name="PageForm" property="actionMode" value="Preview">
                        <%
						 ((RequestDispatcher)request.getRequestDispatcher(viewCond)).include(request, response);
						%>
                   </logic:equal>
                        
                </html:form>
       		</div>
       </div>
  </body>
</html>