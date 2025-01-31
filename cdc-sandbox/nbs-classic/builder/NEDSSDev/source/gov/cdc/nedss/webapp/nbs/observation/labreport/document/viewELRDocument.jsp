<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="en">
<script language="javascript">
        function showPrintFriendlyPage()
        {
            var o = new Object();
            o.opener = self;
            var date = '${fn:escapeXml(dateReceivedHidden)}';
            var documnetId = '${fn:escapeXml(documentUid)}';
            var URL = '/nbs/viewELRDocument.do?method=viewGenericSubmit&documentUid='+documnetId+'&mode=print&dateReceivedHidden='+date;
            var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
            var divElt = getElementByIdOrByName("blockParent");
            	
            var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
        	
            return false;
        }
        
        function closePrinterFriendlyWindow()
        {
            self.close();
           return false;   
        }
        function disablePrintLinks() {
        	$j("a[href]:not([href^=#])").removeAttr('href');	
        }
        
        </script>
        
        <style type="text/css">
			.removefilterRight
				{
					
					background-color:#003470; width:100%; height:0px;
					line-height:25px;float:right;text-align:right;
					
				}
			.removefilterLeft
				{
					background-color:#003470; width:100%; height:25px;
					line-height:25px;
				}
			.removefilerLink {vertical-align:bottom;  }
			.removefilerLink1 {vertical-align:bottom; float:right;text-align:right;}
			.leftAlign {float:left;text-align:left;}
			rightAlign {text-align:right;}

			.hyperLink
				{
				    font-size : 10pt;
				    font-family : Geneva,Arial, Helvetica, sans-serif;
				    color : #FFFFFF;
					text-decoration: none;
				}
            
            table.subSect tbody tr {background:#FFF;}
        </style>
    <head>
    <title>${fn:escapeXml(PageTitle)}</title>
    <%@ include file="/jsp/resources.jsp" %>
    </head>
        <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
    if (printMode.equals("print")) { %>
    <body onload="startCountdown();disablePrintLinks();colapseRawXML();populateDateReceived();" onunload="return closePrinterFriendlyWindow();">
    <% } else { %>
     <body onload="startCountdown();colapseRawXML();populateDateReceived();">
    <% } %>
		<div id="blockParent"></div>
    		<div id="doc3">
    			<html:form action="/LoadViewDoc1.do">
    			<div class="grayButtonBar" style="text-align: right;">
            				<input type="button" align="right" name="Print" value="Print" onclick="showPrintFriendlyPage();"/>
   		 		</div>
					<div id="bd">
						 <input id="dateReceivedHidden" type="hidden" value="${fn:escapeXml(dateReceivedHidden)}"/>
					   <!-- Main body Starts here -->
					   <div id="xmlBlock" style="100%">
						 <%=request.getAttribute("documentViewHTML")%>	   
						</div>     
					 </div> <!-- div id = bd -->
				 </html:form> 
			 </div> <!-- div id = doc3 -->
		</body> 
</html>