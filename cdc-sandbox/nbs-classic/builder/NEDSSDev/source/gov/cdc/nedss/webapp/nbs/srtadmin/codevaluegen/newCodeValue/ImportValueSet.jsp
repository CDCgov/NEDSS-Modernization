<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage Value Sets:Import Value Set</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js">
        </SCRIPT>
        <script type="text/javaScript">
        function cancelForm()
	    {
	       
	      document.forms[0].action ='/nbs/ManageCodeSet.do?method=ViewValueSetLib&context=cancel';
	       
	    }
        function submitForm()
	    {
	       
	      document.forms[0].action ='/nbs/ManageCodeSet.do?method=importValueSetStore';
	       
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
        </script>  
        <style type="text/css">
        div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
        div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
        </style>
    </head>
    <body onload="showCount();startCountdown();">
     <html:form action="/ManageCondition.do">
    	<div id="doc3">
            <div id="bd">
            <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
            	<tr><td>&nbsp;</td></tr>
            	<div class="popupButtonBar">
		            <input type="submit" name="submit" id="submitA" value="OK" style="width: 55px" onClick="return submitForm();"/>
		            <input type="submit" id="submitB" value="Cancel" onClick="return cancelForm();"/>
		        </div>
		        <tr><td>&nbsp;</td></tr>
		        <!-- Error Message -->
		        <% if(request.getAttribute("error") != null) { %>
				    <div class="infoBox errors">
				        <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				        <ul><li>${fn:escapeXml(error)}</li></ul>
				    </div>    
				<% }%>
            	<% if (request.getAttribute("VadsValueSetCode") != null && request.getAttribute("VadsValueSetCode1") != null && request.getAttribute("VadsValueSetCode2") != null) { %>
				  <div class="infoBox messages">
				       ${fn:escapeXml(VadsValueSetCode1)}<b>${fn:escapeXml(VadsValueSetCode)}</b>${fn:escapeXml(VadsValueSetCode2)}
				  </div>    
	             <% } %>
            	 <nedss:container id="section1" name=" Import Confirmation" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
            	 		<nedss:container id="subsec1" name=" Value Set Details" classType="subSect" displayImg ="false">
	            	 		<tr>
	                           	<td class="fieldName" id="CodeSetDes"><span>Value Set Code:</span></td>
	                            <td>
	                               <nedss:view name="SRTAdminManageForm" property="selection.valueSetCode"/>
	                            </td>
	                        </tr>
	                        <tr>
	                           	<td class="fieldName" id="CodeSetDes"><span>Value Set Name:</span></td>
	                            <td>
	                               <nedss:view name="SRTAdminManageForm" property="selection.valueSetNm"/>
	                            </td>
	                        </tr>
	                        <tr>
	                           	<td class="fieldName" id="CodeSetDes"><span>Value Set Description:</span></td>
	                            <td>
	                               <nedss:view name="SRTAdminManageForm" property="selection.codeSetDescTxt"/>
	                            </td>
	                        </tr>
	                        <tr>
	                           	<td class="fieldName" id="CodeSetDes"><span>Value Set OID:</span></td>
	                            <td>
	                               <nedss:view name="SRTAdminManageForm" property="selection.valueSetOid"/>
	                            </td>
	                        </tr>
            	 		</nedss:container>
            	 		<nedss:container id="subsec2" name=" Value Set Concepts" classType="subSect" displayImg ="false">
            	 		<input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCountVads)}"/>
	            	 		<tr><td>
	                               <table role="presentation" width="98%" align="center">
	                                   <tr>
	                                       <td align="center">
	                                           <display:table style="border: 1px solid black" name="phinList" class="dtTable" pagesize="10"  id="parent"  requestURI="/ManageCodeSet.do?method=importValueSetLoad&existing=true" sort="external" export="true">
	                                               <display:column property="conceptCode" title="Concept Code" sortable="true" sortName="getConceptCode" defaultorder="ascending"/>
	                                               <display:column property="conceptNm" title="Concept Name" sortable="true" sortName="getConceptNm" defaultorder="ascending"/>
	                                               <display:column property="conceptPreferredNm" title="Preferred Concept Name" sortable="true" sortName="getConceptPreferredNm" defaultorder="ascending"/>
	                                               <display:column property="codeSystemDescTxt" title="Code System Name" sortable="true" sortName="getCodeSystemDescTxt" defaultorder="ascending"/>
	                                               <display:setProperty name="basic.empty.showtable" value="true"/>
	                                           </display:table>
	                                       </td>
	                                   </tr>
	                               </table>
                              </td></tr>
            	 		</nedss:container>
            	 		<tr><td>&nbsp;</td></tr>
            	 		<div class="popupButtonBar">
				            <input type="submit" name="submit" id="submitA" value="OK" style="width: 55px" onClick="return submitForm();"/>
				            <input type="submit" id="submitB" value="Cancel" onClick="return cancelForm();"/>
				        </div>
            	 	
	             </nedss:container>       
            </div>
        </div>
        </html:form>
    </body>
</html>