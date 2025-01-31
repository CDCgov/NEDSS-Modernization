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
         <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JInvestigationForm.js"></SCRIPT>

         <meta http-equiv="MSThemeCompatible" content="yes"/>
	<title>Contact Tracing</title>
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

		        
			function closePopup()
		    {
			    if (isFormSubmission == false) {
	                self.close();
	                var opener = getDialogArgument();
<%-- 	                var pageId='<%=HTMLEncoder.encodeHtml(request.getParameter("pageId"))%>'; --%>
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
		
		 function submitAssociateContacts()
			    {
				var filler =  setCheckBoxValue();
				var filler = getElementByIdOrByName("chkboxIds");
				var opener = getDialogArgument();      
				opener.reloadInvs(filler);
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
             errors[index++] = "You must select at least one contact record to associate";
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
   
  

<body class="popup"  onload="showCount();addRolePresentationToTabsAndSections();" onunload="closePopup()" style="overflow-x: hidden; padding-right: 15px">
 
	         <!-- Page Errors -->
                        <!--%@ include file="../../../jsp/feedbackMessagesBar.jsp" %-->	        
		    <!-- error -->
		    
		    	<div id="errorBlock" class="screenOnly infoBox errors" style="display:none">
			</div>

 <html:form action="/LoadManageCtAssociation.do">
 			<!-- Page title -->
	        <div class="popupTitle">
	        Manage Contact Associations
	        </div>
	        
	        <!-- Top button bar -->
	      	<div class="popupButtonBar">
	            <input type="button" name="Submit" value="Submit" onclick="submitAssociateContacts();"/>
	            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
	        </div>
	        
		<% if (request.getAttribute("ctMessage") != null) { %>
			  <div class="infoBox messages">
			  	<%=(String)request.getAttribute("ctMessage")%>
			  </div>    
		<% } %>
	        

 <tr> <td>
 
             
<html:hidden name="manageCTAssociateForm" property="selectedcheckboxIds" styleId="chkboxIds"/>
	<table role="presentation" width="100%">
	<tr> <td align="center">
            <!-- Display tab table for listing all lab reports -->
            <display:table name="contactsSummaryList" class="dtTable" id="contactlist" style="margin-top:0em;" pagesize="${manageCTAssociateForm.attributeMap.queueSize}"  export="true" requestURI="/LoadManageCtAssociation.do?method=manageContactsLoad&existing=true" sort="external">
	            <display:column title="<p style='display:none'>Select/Deselect All</p>" style="width:5%;">
	            	<div align="center" style="margin-top: 3px">
	                    <input type="checkbox" value="${contactlist.isAssociatedToPHC}" name="${contactlist.ctContactUid}" ${contactlist.checkBoxId}/>
	                </div>
	            </display:column>
	            <display:column property="namedBy" title="Name" />
	            <display:column property="namedOnDate" title="Date Named"  format="{0,date,MM/dd/yyyy}"/>
                   <display:column property="ageDOBSex" title="Age/DOB/Sex" />
                    <display:column property="relationshipCd" title="Relationship" />
                    <display:column property="dispositionCd" title="Disposition" />
	            <display:setProperty name="basic.empty.showtable" value="true"/>
	     </display:table>
	  </td>
    </tr>
  
</table>
       <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>  
   <!-- Bottom button bar -->
   	<div class="popupButtonBar">
            <input type="button" name="Submit" value="Submit" onclick="submitAssociateContacts();"/>
            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
       </div>
    </html:form>
 
      </body>
</html>

