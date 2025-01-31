<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Conditions Library </title>
        <%@ include file="/jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageConditionForm.js"></SCRIPT>
        <script type="text/javaScript" src="pagemanagementSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <script type="text/javascript" src="Globals.js"></script>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javaScript">

        blockEnterKey();
		
        function addNewCondition()
        {
       		document.forms[0].action ='/nbs/ManageCondition.do?method=createConditionLoad#condition';
        }

        function createLink(element, url)
		{
			// call the JS function to block the UI while saving is on progress.
			blockUIDuringFormSubmissionNoGraphic();
            document.forms[0].action= url;
            document.forms[0].submit();  
		}
        function cancelForm()
	    {
	        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
	        if (confirm(confirmMsg))
	        {
	            document.forms[0].action ="${manageConditionForm.attributeMap.cancel}";
	        }
	        else {
	            return false;
	        }
	    }
        function submitForm(){
        	if(manageConditionReqFlds()) {
                return false;
            } else {
            	document.forms[0].action ="${manageConditionForm.attributeMap.submit}";
            }
        	
        }
        function EditForm()
        {
        	document.forms[0].action ="${manageConditionForm.attributeMap.Edit}";
        }
        function makeInactive()
        {
        	var conditionNm = '<%=request.getAttribute("conditionNm")%>';
        	var confirmMsg="You have indicated that you would like to inactivate the "+ conditionNm +" condition. Once inactivated, this condition will be no longer available to the users when creating an investigation or summary report. Select OK to continue or Cancel to return to View condition.";
	        if (confirm(confirmMsg))
	        {
	        	document.forms[0].action ="${manageConditionForm.attributeMap.MakeInactive}";
	        	document.forms[0].submit();
	        }
	        else {
	            return false;
	        }
        }
        function makeActive()
        {
        	
	       document.forms[0].action ="${manageConditionForm.attributeMap.MakeActive}";
	       document.forms[0].submit();
	        
        }
        function onKeyUpValidate()
     	{
    	
         	   if(getElementByIdOrByName("SearchText1").value != ""){
         		getElementByIdOrByName("b1SearchText1").disabled=false;
         		getElementByIdOrByName("b2SearchText1").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText1").value == ""){
         		getElementByIdOrByName("b1SearchText1").disabled=true;
         		getElementByIdOrByName("b2SearchText1").disabled=true;
         	   }
         	   if(getElementByIdOrByName("SearchText2").value != ""){
         		getElementByIdOrByName("b1SearchText2").disabled=false;
         		getElementByIdOrByName("b2SearchText2").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText2").value == ""){
         		getElementByIdOrByName("b1SearchText2").disabled=true;
         		getElementByIdOrByName("b2SearchText2").disabled=true;
         	   }
    	
    	}
        function makeMSelects() {
        	$j("#condition").text({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#code").text({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#pArea").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#cFamily").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#associatedPage").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#nndCondition").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#status").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#cInfGroup").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	
        }
        function showCount()
        {
            $j(".pagebanner b").each(function(i){ 
                $j(this).append(" of ").append($j("#queueCnt").attr("value"));
            });
            $j(".singlepagebanner b").each(function(i){ 
                var cnt = $j("#queueCnt").attr("value");
                if(cnt > 0)
                    $j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
            });             
        }
        function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
       function viewConditionCd(conditionCd, pageNm){
    	   blockUIDuringFormSubmissionNoGraphic();
    	   document.forms[0].action ="/nbs/ManageCondition.do?method=viewCondition&fromConditionLib=fromConditionLib&conditionCd="+conditionCd+"&pageNm="+pageNm;
		   document.forms[0].submit();
       }
       function editConditionCd(conditionCd){
    	   blockUIDuringFormSubmissionNoGraphic();
    	   document.forms[0].action ="/nbs/ManageCondition.do?method=editConditionLoad&conditionCd="+conditionCd;
		   document.forms[0].submit();
       }
        
        </script>  
        <style type="text/css">
         div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
         div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
        
		.removefilter{
			background-color:#003470; width:100%; height:25px;
			line-height:25px;float:right;text-align:right;
			}
			removefilerLink {vertical-align:bottom;  }
			.hyperLink
			{
			    font-size : 10pt;
			    font-family : Geneva, Arial, Helvetica, sans-serif;
			    color : #FFFFFF;
				text-decoration: none;
			}
		</style>
    </head>
    <body onload="attachIcons();makeMSelects();showCount();displayTooltips();autocompTxtValuesForJSP();startCountdown();">
        <div id="blockparent"></div>
         <html:form action="/ManageCondition.do">
            <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
	                		 <!-- Top Nav Bar and top button bar -->
								 <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
								 
							    <table role="presentation" style="width:100%;">
									<tr>
									    <td style="text-align:right"  id="srtLink"> 
									        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin4">
									            Return to System Management Main Menu
									        </a>  
									        <input type="hidden" id="actionMode" value="${manageConditionForm.actionMode}"/>
									    </td>
									</tr>
									
								</table>
								 <!-- top bar -->
								<tr><td>&nbsp;</td></tr>
				            	<div class="popupButtonBar">
						             	<input type="submit" id="submitCr" value="Add New" onclick="addNewCondition();"/>
						            	<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
						           		<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
						         </div>
						         <!-- Error message -->
								<% if(request.getAttribute("error") != null) { %>
								    <div class="infoBox errors">
								        <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
								        <ul>
								            <li> ${fn:escapeXml(error)} </li>
								        </ul>
								    </div>    
								<% }%>
								 <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
								 
								 <% if (request.getAttribute("ConfirmMesgCreate") != null) { %>
									  <div class="infoBox success">
									  	${fn:escapeXml(ConfirmMesgCreate)}&nbsp;<a href='${fn:escapeXml(clickHereLk)}'>Click Here</a> to access the Add Page screen.<br/>
									  </div>    
						           <% } %>
						           <% if (request.getAttribute("ConfirmMesg") != null) { %>
									  <div class="infoBox success">
									  	${fn:escapeXml(ConfirmMesg)}
									  </div>    
						           <% } %>
						           
								 <!-- Container Div -->
	          				 	 <% if (request.getAttribute("isPortReq") != null ) { %>
									<div class="infoBox info" style="text-align: left;">
									   Highlighted items below have been related to a new page but require publishing and/or data porting before the new disease module can be utilized by end users.
									</div>
								 <% } %>
	                			 <nedss:container id="section1" name="Condition Library" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
		                             <fieldset style="border-width:0px;" id="result">
		                             <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
		                                <table role="presentation" width="98%">
		                                    <tr>
			                                    <td align="center">
			                                      <display:table name="manageList" class="dtTable" style="margin-top:0em;"  id="parent"  pagesize="${manageConditionForm.attributeMap.queueSize}"   
			                                      requestURI="/ManageCondition.do?method=ViewConditionLib&existing=true" 
			                                      sort="external" export="true" 
			                                      excludedParams="answerArrayText(SearchText1) 
			                                      answerArrayText(SearchText2) answerArray(PROGRAMAREA) answerArray(ASSOCIATEDPAGE)  answerArray(CONDITIONFAMILY) answerArray(NNDCONDITION) answerArray(STATUS)  method">
			                            				<display:setProperty name="export.csv.filename" value="ConditionCodeLibrary.csv"/>
			                    					  	<display:setProperty name="export.pdf.filename" value="ConditionCodeLibrary.pdf"/>
														<display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:4%;text-align:center;"/>
                                        				<display:column property="editLink" title="<p style='display:none'>Edit</p>" media="html" style="width:4%;text-align:center;"/>
		                                                <display:column property="conditionShortNm" title="Condition"  sortable="true"  sortName="getConditionShortNm" defaultorder="ascending" style="width1:16%;" />
		                                                <display:column property="conditionCd" title="Code"   sortable="true"  sortName="getConditionCd" defaultorder="ascending" style="width:9%;" />
		                                                <display:column property="progAreaCd" title="Program Area"  sortable="true"  sortName="getProgAreaCd" defaultorder="ascending" style="width:10%;" />
		                                                <display:column property="familyCdDescTxt" title="Condition Family"  sortable="true"  sortName="getFamilyCd" defaultorder="ascending" style="width:11%;" />
		                                                <display:column property="coInfGroupDescTxt" title="Coinfection Group"  sortable="true"  sortName="getCoInfGroup" defaultorder="ascending" style="width:14%;" />
		                                                <display:column property="nndIndDescTxt" title="NND"  sortable="true"  sortName="getNndInd" defaultorder="ascending" style="width:7%;" />
		                                                <display:column property="pageNmForDisplay" title="Investigation Page"  media="html" sortable="true"  sortName="getPageNm" defaultorder="ascending" style="width:19%;" />
		                                                <display:column property="pageNmForDisplayPrint" title="Investigation Page"  media="pdf csv" sortable="true"  sortName="getPageNm" defaultorder="ascending" style="width:19%;" />
		                                                <display:column property="statusCdDescTxt" title="Status"  sortable="true"  sortName="getStatusCd" defaultorder="ascending" style="width:7%;" />
			                                            <display:setProperty name="basic.empty.showtable" value="true"  />
			                                       </display:table>
			                                     </td>
		                                    </tr>
		                                </table>
		                                </fieldset>
	                              </nedss:container>
	
	                    	<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
		                    <div class="removefilter" id="removeFilters">
		        				<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
		        			</div>
	        		   		</div>
        		   		<!-- top bar -->
							<tr><td>&nbsp;</td></tr>
			            	<div class="popupButtonBar">
					            <input type="submit" id="submitCr" value="Add New" onclick="addNewCondition();"/>
					            <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
					            <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
					         </div>
						         
					</td>
					</tr>
               </div>
               		<html:hidden styleId="INV147" property="attributeMap.searchCriteria.INV147"/>	
               		<html:hidden styleId="INV163" property="attributeMap.searchCriteria.INV163"/>	
               		<html:hidden styleId="INV100" property="attributeMap.searchCriteria.INV100"/>	
               		<html:hidden styleId="NOT118" property="attributeMap.searchCriteria.NOT118"/>	
			   		 <html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>	
			   		 <html:hidden styleId="INV111" property="attributeMap.searchCriteria.INV111"/>
			   		 <html:hidden styleId="INV222" property="attributeMap.searchCriteria.INV222"/>
			   		 <html:hidden styleId="INV333" property="attributeMap.searchCriteria.INV333"/>
			   		 
			   		 
			   		 <html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.INV111"/>
			 		 <html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.INV222"/>
			 		 <html:hidden styleId="STD111" property="attributeMap.searchCriteria.STD111"/>
			 		
			   		 <%@ include file="ManageConditionLibDropdown.jsp" %>
               </html:form>
		    
    </body>

</html>