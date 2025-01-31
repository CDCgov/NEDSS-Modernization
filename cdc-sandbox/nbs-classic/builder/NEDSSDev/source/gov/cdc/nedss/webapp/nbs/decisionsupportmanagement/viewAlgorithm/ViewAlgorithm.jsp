<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NBS: Manage Workflow Decision Support</title>
<%@ include file="../../jsp/resources.jsp"%>
<%@ include file="../../jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<SCRIPT Language="JavaScript" Src="DecisionSupportManagementSpecific.js"></SCRIPT>
<script type="text/javascript"	src="/nbs/dwr/interface/JDecisionSupport.js"></SCRIPT>
<script language="JavaScript">
	function editForm() 
	{
		document.forms[0].action ="/nbs/DecisionSupport.do?method=editLoad";
		document.forms[0].submit();           
	}

	 function cancelForm()
     {
         var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
         if (confirm(confirmMsg)) {
             document.forms[0].action ="/nbs/ManageDecisionSupport.do?method=loadqueue";
             document.forms[0].submit();
         } else {
             return false;
         }
     }

	 function makeInactive()
     {
      var algorithmName="<%=HTMLEncoder.sanitizeHtml((String)request.getAttribute("AlgorithmNm"))%>";
      var confirmMsg="You have indicated that you would like to inactivate the " + algorithmName +
		 " algorithm. Once inactivated, this algorithm will no longer be applied as a part of Workflow Decision Support. "+
		 "Select OK to continue or Cancel to return to View Algorithm.";
	        if (confirm(confirmMsg))
	        {
	        	document.forms[0].action ="${decisionSupportForm.attributeMap.MakeInactive}";
	        	document.forms[0].submit();
	        }
	        else {
	            return false;
	        }
     }
     function makeActive()
     {
     	 var algorithmName="<%=HTMLEncoder.sanitizeHtml((String)request.getAttribute("AlgorithmNm"))%>";
      	 var confirmMsg="You have indicated that you would like to activate the " + algorithmName +
 		 " algorithm. Once activated, this algorithm will be applied as a part of Workflow Decision Support. "+
 		 " Select OK to continue or Cancel to return to View Algorithm.";
 	        if (confirm(confirmMsg))
 	        {
 	        	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer('ElrIdAdvancedSubSection',function(answer) {
 	        		var eventType = getElementByIdOrByName("eventTypeVal");
 	        		if((answer == null || answer.length == 0) && eventType.value=="11648804") {
 	        			var errors = new Array();
 	        			 var actionVal = getElementByIdOrByName("actionVal")
 	        			if(actionVal != null && actionVal != undefined && actionVal.value == '3'){
 	        				errors[0] = "Advanced Criteria and/or Lab Criteria is required to activate an algorithm for Event Type = Lab Report and Action = Mark as Reviewed. Please select Advanced Criteria and try again."
 	        			}
 	        			else{
 	        				errors[0] = "Advanced Criteria and/or Lab Criteria is required to activate an algorithm for Event Type = Lab Report and Action = Create " + 
 	        				"Investigation or Create Investigation with Notification. Please select Advanced Criteria and try again."
 	        			}
 	        			displayGlobalErrorMessage(errors);
 	        			return false;
 	        		 } else
	 	        	{
 	        			document.forms[0].action ="${decisionSupportForm.attributeMap.MakeActive}";
 	    	        	document.forms[0].submit();
	 	        	}    		
 	        	}); 
 	        	
 	        }
 	        else {
 	            return false;
 	        }
	        
     }
     function exportAlgorithm()
     {
      	var algorithmUid = '${fn:escapeXml(algorithmUid)}';
       	document.forms[0].action ="/nbs/DecisionSupport.do?method=exportAlgorithm&algorithmUid="+algorithmUid;
       	document.forms[0].submit();	        
     }
     
     function printAlgorithm() {
         var divElt = getElementByIdOrByName("algorithmview");
         var algorithmUid = '${fn:escapeXml(algorithmUid)}';
         divElt.style.display = "block";
         var o = new Object();
         o.opener = self;   
         var URL = "/nbs/DecisionSupport.do?method=viewLoad&mode=print&algorithmUid="+algorithmUid;
         var dialogFeatures = "dialogWidth:700px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
         
         openWindow(URL, o, dialogFeatures, divElt, "");
         
         return false;
      }
     //function disablePrintLinks() {
    //		$j("a[href]:not([href^=#])").removeAttr('href');	
    //	}
     
	</script>
<style type="text/css">
table.FORM {
	width: 100%;
	margin-top: 15em;
}

div.printexport {
	width: 100%;
	border: 1px solid #DDD;
	margin: 0.5em 0em 0.5em 0em;
	background: #efefef;
	border-color: none;
	border-style: solid;
	font: bold 78% verdana;
	font-size: 12px;
	font-weight: bold;
	padding: 2px;
	border-left-style: outset;
	border-bottom-style: outset
}
table.actionSect {background-color:#DCE7F7;}
</style>
</head>
<body onload="autocompTxtValuesForJSP();startCountdown();onLoadFunction()">

<div id="algorithmview"></div>
<!-- Container Div: To hold top nav bar, button bar, body and footer -->
<div id="doc3">
<html:form>
	<!-- Body div -->
	<div id="bd"><!-- Top Nav Bar and top button bar --> 
	<%@ include	file="../../jsp/topNavFullScreenWidth.jsp"%>
	<!-- For create/edit mode only --> <!-- Page Errors -->
	 <%@ include file="../../../jsp/feedbackMessagesBar.jsp"%>
	<!-- For preview mode only -->
	<div class="printerIconBlock screenOnly">
	    <table role="presentation" style="width:98%; margin:3px;">
	        <tr>
	            <td style="text-align:right; font-weight:bold;"> 
	                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
	            </td>
	        </tr>
	    </table>
	</div>
	<% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
	if (!printMode.equals("print")) { %>
	<div style="text-align: right; width: 100%;">
		<span class="boldTenBlack"> 
			<a id="manageLink"	href="/nbs/ManageDecisionSupport.do?method=loadqueue">
				Return to Algorithm Library
			</a> 
		</span>
	</div>
	<!-- top button bar -->
	<div class="grayButtonBar">
		<input type="button"  name="Print" style="width: 70px" value="Print" onclick="return printAlgorithm();" /> 
		<input type="button"  name="Export" value="Export" style="width: 70px" onclick="exportAlgorithm();" />
            <% String activeInd = (String)request.getAttribute("ActiveInd");					                 
            if (activeInd != null && activeInd.equals("Inactive")) { %>     
           <input type="button" id="submitB" value="Make Inactive" onClick="makeInactive();"/>
           <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
           <input type="button" id="Submit" name="Submit" value="Edit" onclick="return editForm();" />
           <input type="button" id="submitB" value="Make Active" onClick="makeActive();"/>
           <%} %>
	</div>
	<% } %>
	 <% if (request.getAttribute("ConfirmMesg") != null) { %>
	  <div class="infoBox success">
	  		<b>${fn:escapeXml(AlgorithmNm)}</b>${fn:escapeXml(ConfirmMesg)}
	  </div>    
    <% } %>
	<input type="hidden" id="actionMode" value="<c:out value="${decisionSupportForm.actionMode}" />"/> 
	<!-- Algorithm Summary Bar --> 
	<%@ include file="AlgorithmSummary.jsp"%>
	<!-- Tab container -->
	<% String pageNm = "View Algorithm" + " - " + (String) request.getAttribute("EventTypeName");
    %>
	 <nedss:container id="section1"	name="<%=pageNm%>" classType="sect" displayImg="false"	displayLink="false" includeBackToTopLink="no">
		<table role="presentation">
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<layout:tabs width="100%" styleClass="tabsContainer">
			<layout:tab key="Basic Criteria">
				<jsp:include page="ViewAlgorithm_BasicCriteria.jsp" />
			</layout:tab>

			<layout:tab key="Action">
				<jsp:include page="ViewAlgorithm_Action.jsp" />
			</layout:tab>
		</layout:tabs>
	</nedss:container> <!-- Bottom button bar -->
	<% if (!printMode.equals("print")) { %>
	<div class="grayButtonBar">
		<input type="button" style="font-weight:normal" name="Print" style="width: 70px" value="Print" onclick="return printAlgorithm();" /> 
		<input type="button" style="font-weight:normal" name="Export" value="Export" style="width: 70px" onclick="exportAlgorithm();" />
            <% String activeInd = (String)request.getAttribute("ActiveInd");
            if (activeInd != null && activeInd.equals("Inactive")) { %>
           <input type="button" style="font-weight:normal" id="submitB" value="Make Inactive" onClick="makeInactive();"/>
           <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
           <input	type="button" style="font-weight:normal" id="Submit" name="Submit" value="Edit" onclick="return editForm();" />
           <input type="button" style="font-weight:normal" id="submitB" value="Make Active" onClick="makeActive();"/>
           <%} %>
	</div>
	<%} %>
	</div>
	<!-- id=bd -->
</html:form></div>
<!-- Container Div -->
</body>
</html>