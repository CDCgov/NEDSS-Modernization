<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>

<html lang="en">
    <head>
    <title>${fn:escapeXml(PageTitle)}</title>
    <%@ include file="/jsp/resources.jsp" %>
        <script language="javascript">
        /** Transfer Ownership of document */
        function transferDocOwnership()
        {
            var divElt = getElementByIdOrByName("blockParent");
            divElt.style.display = "block";		
            var o = new Object();
            o.opener = self;
            var URL = '<%=request.getAttribute("transferOwnership")%>';
            
            var modWin = openWindow(URL, o, GetDialogFeatures(640, 250, false, true), divElt, "");
            return false;
        }
        
        function viewCDA()
        {
        	
            var o = new Object();
            o.opener = self;
            
            var URL = "/nbs/LoadViewDocument2.do?method=originalDocumentView&eventType=eICR";
            var dialogFeatures = "dialogWidth:1200px;dialogHeight:1000px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
          
            var modWin = openWindow(URL, o, dialogFeatures, null, "");
            
            return false;
        }
        
        function showPrintFriendlyPage()
        {
            var divElt = getElementByIdOrByName("blockParent");
            divElt.style.display = "block";
            var o = new Object();
            o.opener = self;
            
            var URL = '<%=request.getAttribute("PrintPage")%>';
            var dialogFeatures = "dialogWidth:800px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
            
            var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
            
            
            return false;
        }
        
        function closePrinterFriendlyWindow()
        {
            self.close();
            var opener = getDialogArgument();         
            var pview = getElementByIdOrByNameNode("blockParent",opener.document)
            pview.style.display = "none";
            
            return false;   
        }
        function disablePrintLinks() {
        	$j("a[href]:not([href^=#])").removeAttr('href');	
        }
        
        function deleteForm(){
    		document.forms[0].target="";     
    		var confirmMsg="If you continue with the Delete action, you will Delete the Document and it will no longer be viewable in the NBS user interface. Select 'OK' to continue or 'Cancel' to Cancel.";
			if (confirm(confirmMsg)) {
				 var deleteAction='<%=request.getAttribute("DeleteHref")%>';
		      	document.forms[0].action =deleteAction;
		        document.forms[0].submit();

			} else {
				return false;
			}
      }
        </script>
        
        <style type="text/css">
			.removefilterRight
				{
					
					background-color:#003470; width:40%; height:0px;
					line-height:25px;float:right;text-align:right;
					
				}
			.removefilterLeft
				{
					background-color:#003470; width:60%; height:25px;
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
    </head>
    <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
    if (printMode.equals("print")) { %>
    <body onload="startCountdown();disablePrintLinks();" onunload="return closePrinterFriendlyWindow();">
    <% } else { %>
     <body onload="startCountdown();">
    <% } %>
   
    <div id="blockParent"></div>
    	<div id="doc3">
    	<html:form action="/LoadViewDoc1.do">
	        <div id="bd">
	            <!-- Top Nav Bar -->
	             
	          <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
	          
	          <!-- Error message for delete denied -->
	          
	          	<% if ((String)request.getAttribute("deleteDeniedMsg") != null) { %>
				  <div class="infoBox errors">
				   <%= request.getAttribute("deleteDeniedMsg") %>
				  </div>    
				<% } %>	  
			 
	          <!-- Return to Files Link : When actionMode = VIEW only(Needs to check) -->
		        <table role="presentation" style="width:100%;">
			 		<tr>
			 		    <td style="text-align:right"  id="srtLink"> 
					 		   <div class="returnToPageLink">
				 		    	 	<% String viewFileLink = (String)request.getAttribute("linkValue2"); 
				 		    	 	if(viewFileLink != null){
				 		    	 	%>
				 		    	 		<a href='<%=request.getAttribute("linkValue2")%>' ><%=request.getAttribute("linkName2")%></a> 
					 		        <%} 
					 		        String returnLink = (String)request.getAttribute("linkValue1"); %>
					 		        <%if(viewFileLink != null && returnLink != null){%>&nbsp;|&nbsp;<%}%>
					 		        <%if(returnLink != null){
								    %>
					 		       	 <a id="" href='<%=request.getAttribute("linkValue1")%>' ><%=request.getAttribute("linkName1")%></a>
					 		         <%} %>
					 		     </div>

			 		        <!--input type="hidden" id="actionMode" value="${SRTAdminManageForm.actionMode}"/-->
			 		    </td>
			 		</tr>
			 		<tr><td>&nbsp;</td></tr>
		 		</table>
		 		
		 		<!-- Success message for mark as reviewed -->
		 		
		 		<% if (request.getAttribute("docMarkReviewConfMsg") != null) { %>
				  <div class="infoBox success">
				  	<%=(String)request.getAttribute("docMarkReviewConfMsg")%>
				  </div>    
				<% } %>
		 		
		 		
		 		 <!-- Return to Files Link- Ends -->
		 		<% 
		 			String checkTransfer = (String)request.getAttribute("checkTransfer"); 
		 			String checkMarkAsReview = (String)request.getAttribute("checkMarkAsReview");
		 			String checkDelete = (String)request.getAttribute("checkDelete");
		 			String checkCreateInvestigation = (String)request.getAttribute("checkCreateInvestigation");
		 			String cdaLink = (String)request.getAttribute("cdaLink");
		 			String std=(String)request.getAttribute("PDLogicCreateInv");
		 		%>
		       
	             <!-- Top button bar -->
                 <table role="presentation" id="docBtnBar" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;width:100%;">
	             		<tr>	
				             	<td  class="removefilterLeft " id="removeFilters"  style="text-align:left">
				             		<%if(checkMarkAsReview != null && checkMarkAsReview.equals("true")){%>
						          <a class="removefilerLink" href="<%=request.getAttribute("MarkAsReviewHref")%>"><font class="hyperLink"> &nbsp;Mark As Reviewed |</font></a>
						            <%}%>
						            <%if(checkCreateInvestigation != null && checkCreateInvestigation.equals("true")){%>
										<%if((std != null && !std.equals("null") && !std.equals("NA")) && (cdaLink != null && cdaLink.equals("true"))){%>
											<a class="removefilerLink" href="/nbs/Load<%=request.getAttribute("currentTask")%>.do?method=createInvestigation&ContextAction=CreateInvestigation&ConditionCd=<%=request.getAttribute("ConditionCd")%>"><font class="hyperLink">&nbsp;Create Investigation |</font></a>
										<%}else{%>
											<a class="removefilerLink" href="/nbs/Load<%=request.getAttribute("currentTask")%>.do?method=createInvestigation&ContextAction=CreateInvestigation&ConditionCd=<%=request.getAttribute("ConditionCd")%>"><font class="hyperLink">&nbsp;Create Investigation |</font></a>
										<%}%>									
									<%}%>		
									<%if(checkTransfer != null && checkTransfer.equals("true")){%>
										<a class="removefilerLink" href="#" onclick="return transferDocOwnership();"><font class="hyperLink">&nbsp;Transfer Ownership |</font></a>
									<%}%>
									<%if(checkDelete != null && checkDelete.equals("true")){%>
										<a class="removefilerLink" href="#" onclick="return deleteForm();"><font class="hyperLink">&nbsp;Delete</font></a>
									<%}%>	
								</td>
								<td class="removefilterRight"  align="right">
									<%if(cdaLink != null && cdaLink.equals("true")){%>
										<a class="removefilerLink" href="#" onclick="return viewCDA();"><font class="hyperLink">&nbsp;View eICR Document |</font></a>
									<%}%>
									<a class="removefilerLink" href="#" onclick="return showPrintFriendlyPage();"><font class="hyperLink">Print&nbsp;</font></a>
								</td>
						</tr>
		         </table>
		         <!-- Top button bar- Ends --> 
		         
		         <!-- Document summary table-->
		         <%@ include file="DocumentSummary.jsp" %>  
		         
		       <!-- Main body Starts here -->
		       <div id="xmlBlock" style="100%">
			     <%=request.getAttribute("DOC_VIEWER")%>	   
		
			</div>     
		   <div>&nbsp;</div>
		                
                <!-- Main Body- Ends here-->
                
                <!-- Bottom button bar -->
                 <table role="presentation" id="docBtnBar" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;width:100%;">
	         			 <tr>	
		         				<td  class="removefilterLeft " id="removeFilters"  style="text-align:left">
		         				<%if(checkMarkAsReview != null && checkMarkAsReview.equals("true")){%>
						          <a class="removefilerLink" href="<%=request.getAttribute("MarkAsReviewHref")%>"><font class="hyperLink"> &nbsp;Mark As Reviewed |</font></a>
						            <%}%>
						            <%if(checkCreateInvestigation != null && checkCreateInvestigation.equals("true")){%>
										<a class="removefilerLink" href="/nbs/Load<%=request.getAttribute("currentTask")%>.do?method=createInvestigation&ContextAction=CreateInvestigation&ConditionCd=<%=request.getAttribute("ConditionCd")%>"><font class="hyperLink">&nbsp;Create Investigation |</font></a>
									<%}%>	
									<%if(checkTransfer != null && checkTransfer.equals("true")){%>
										<a class="removefilerLink" href="#" onclick="return transferDocOwnership();"><font class="hyperLink">&nbsp;Transfer Ownership |</font></a>
									<%}%>
									<%if(checkDelete != null && checkDelete.equals("true")){%>
										<a class="removefilerLink" href="#" onclick="return deleteForm();"><font class="hyperLink">&nbsp;Delete</font></a>
									<%}%>	
									
								</td>
								<td class="removefilterRight"  align="right">
									<%if(cdaLink != null && cdaLink.equals("true")){%>
										<a class="removefilerLink" href="#" onclick="return viewCDA();"><font class="hyperLink">&nbsp;View eICR Document |</font></a>
									<%}%>
									<a class="removefilerLink" href="#" onclick="return showPrintFriendlyPage();"><font class="hyperLink"> Print&nbsp;</font></a>
								</td>
						 </tr>
		         </table>
		         <!-- Bottom button bar -Ends -->
		         
	         </div> <!-- div id = bd -->
	         </html:form> 
         </div> <!-- div id = doc3 -->
    </body> 
</html>