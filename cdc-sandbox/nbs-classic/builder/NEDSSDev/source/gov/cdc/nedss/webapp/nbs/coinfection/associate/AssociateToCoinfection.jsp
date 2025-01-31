<%-- 
  - Author(s): Greg Tucker
  - Date:
  - Copyright Notice: SRA International 2015
  - @(#)
  - Description: Used to associate co-infections on create and all investigations on view
  - to treatments, (future- interviews and contacts)
  --%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<html lang="en">
    <head>
         <%@ include file="/jsp/resources.jsp" %>

         <meta http-equiv="MSThemeCompatible" content="yes"/>
	<title>${fn:escapeXml(BaseForm.pageTitle)}</title>
	<base target="_self">	
	<script type="text/javascript">
		var isFormSubmission = false;
		window.onload = function() {
			$j("body").find(':input:visible:enabled:first').focus();
		}
		
		if (typeof window.event != 'undefined')
		  	document.onkeydown = function()
		    		{
				var t=event.srcElement.type;
				if(t == '' || t == 'undefined' || t == 'button') {
					return;
				}
				var kc=event.keyCode;
				if(t == 'text' && kc == 13) {
				}
				
				return preventF12(event);
		    	}

	function cancelPopup()
	{

		var opener = getDialogArgument(); 
		getElementByIdOrByNameNode("coinfList", opener.document).value = "";
		closePopup();
	}
	
		        
	function closePopup()
	{
		if (isFormSubmission == false) {
	                self.close();

	                var opener = getDialogArgument(); 
	                var pageId='${fn:escapeXml(pageId)}';
	                var invest = null; 
	                if(pageId == 'pageview') { 
	                	invest = getElementByIdOrByNameNode("pageview", opener.document);}
	                else        
	                	invest = getElementByIdOrByNameNode("pamview", opener.document)
	                if (invest == null) {
               			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
         			 }
	                invest.style.display = "none";  
	                return true;
			    } 
		    }
	function showCount() {
	       		$j(".pagebanner b").each(function(i){ 
				$j(this).append(" of ").append($j("#queueCnt").attr("value"));
			});
			$j(".singlepagebanner b").each(function(i){ 
				var cnt = $j("#queueCnt").attr("value");
			if(cnt > 0)
				$j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
			});				
	}
		
	function submitAssociateCoinfections()
	{
		var filler =  setCheckBoxValue();
		var filler = getElementByIdOrByName("chkboxIds");
		var opener = getDialogArgument();    

		var contextAction = getElementByIdOrByName("ContextAction").value;
		
		if (contextAction=="Edit") {
		     var sourceUid = getElementByIdOrByName("SourceUid").value;
		     //alert("sourceUid=" + sourceUid);
		     document.forms[0].action ="/nbs/LoadManageCoinfectionAssociation.do?method=associateInvestigationsSubmit&ContextAction=Submit&AssociatedInvestigations="+filler.value+"&sourceUid="+sourceUid ;
             document.forms[0].submit();
             return;		
		}
		getElementByIdOrByNameNode("coinfList", opener.document).value = filler.value;
		opener.document.forms[0].submit();
		closePopup();				
	}
			    
	function isValidCheck(){
	        $j(".infoBox").hide();
    
  		  	var errors = new Array();
  		  	var index = 0;
    		var isError = false;   
    		 
	        var sb = "";
	        var filler = getElementByIdOrByName("chkboxIds");
	        var input = document.getElementsByTagName("input");
	        for(var i = 0; i < input.length; i++)   {
                if(input[i].type == 'checkbox') {
                    if(input[i].checked == true)
                        sb = sb + input[i].name + ":";
                }
	        }
           if(sb = null || sb == ""){ 
             errors[index++] = "You must select at least one coinfection record to associate";
              isError = true;	
           }
	       if(isError) {
			displayErrors("errorBlock", errors);
			return false;
			}
		   else {
			return true;
		 }
	   }

			    
  	  function setCheckBoxValue(){
	        var sb = "";
	        var filler = getElementByIdOrByName("chkboxIds");
	        var input = document.getElementsByTagName("input");
	        for(var i = 0; i < input.length; i++)   {
	                if(input[i].type == 'checkbox') {
	                    if(input[i].checked == true)
	                        sb = sb + input[i].name + ":";
	                }
	        }
	        filler.value=sb;
	    }
	  	
	</script>
		        

   </head>
   
  

<body class="popup"  onload="showCount();addRolePresentationToTabsAndSections();" onunload="closePopup()"  style="overflow-x:hidden">
 
	         <!-- Page Errors -->
                        <!--%@ include file="../../../jsp/feedbackMessagesBar.jsp" %-->	        
		    <!-- error -->
		    
		    	<div id="errorBlock" class="screenOnly infoBox errors" style="display:none">
			</div>

 <html:form action="/LoadManageCoinfectionAssociation.do">
 			<!-- Page title -->
	        <div class="popupTitle">
	        	${fn:escapeXml(BaseForm.pageTitle)}
	        </div>
	        
	        <!-- Top button bar -->
	      	<div class="popupButtonBar">
	            <input type="button" name="Submit" value="Submit" onclick="submitAssociateCoinfections();"/>
	            <input type="button" name="Cancel" value="Cancel" onclick="cancelPopup();" />
	        </div>
	        
		<% if (request.getAttribute("coinfMessage") != null) { %>
			  <div class="infoBox messages" style="text-align: left;">
			  	<%=(String)request.getAttribute("coinfMessage")%>
			  </div>    
		<% } %>
	        

 <tr> <td>
 
             
	<html:hidden name="associateToCoinfectionForm" property="selectedcheckboxIds" styleId="chkboxIds"/>
	<table role="presentation" width="100%">
	<tr> <td align="center">
            <!-- Display tab table for listing all coinfection  -->
            <display:table name="coinfectionsSummaryList" class="dtTable" id="coinflist" style="margin-top:0em;" pagesize="${associateToCoinfectionForm.attributeMap.queueSize}"  export="true" requestURI="/LoadManageCoinfectionAssociation.do?method=associateToCoinfectionLoad&existing=true" sort="external">
	         <display:column style="width:5%;">
	            	<div align="center" style="margin-top: 3px">
	                    <input type="checkbox" value="${coinflist.associated}" name="${coinflist.publicHealthCaseUid}" ${coinflist.checkBoxId} ${coinflist.disabled}/>
	                </div>
	         </display:column>
		<display:column property="investigationStartDate" format="{0,date,MM/dd/yyyy}" style="width:8%;text-align:left;" title="Start Date"/>
		<display:column property="status" style="width:6%;text-align:left;" title="Status"/>
		<display:column property="condition" style="width:24%;text-align:left;" title="Condition"/>                                                  
		<display:column property="caseStatus" style="width:10%;text-align:left;" title="Case Status"/>
		<display:column property="jurisdiction" style="width:14%;text-align:left;" title="Jurisdiction"/>
		<display:column property="investigator" style="width:14%;text-align:left;" title="Investigator"/>
		<display:column property="localId" style="width:12%;text-align:left;" title="Investigation ID "/>
		<display:column property="coinfectionId" style="width:12%;text-align:left;" title="Coinfection ID "/> 
	        <display:setProperty name="basic.empty.showtable" value="true"/>
	     </display:table>
	 </td>
      </tr>
  
</table>
       <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>  
       <input type="hidden" id="ContextAction" value="${fn:escapeXml(ContextAction)}"/>  
       <input type="hidden" id="SourceUid" value="${fn:escapeXml(SourceUid)}"/> 
   <!-- Bottom button bar -->
   	<div class="popupButtonBar">
            <input type="button" name="Submit" value="Submit" onclick="submitAssociateCoinfections();"/>
            <input type="button" name="Cancel" value="Cancel" onclick="cancelPopup();" />
       </div>
    </html:form>
 
      </body>
</html>

