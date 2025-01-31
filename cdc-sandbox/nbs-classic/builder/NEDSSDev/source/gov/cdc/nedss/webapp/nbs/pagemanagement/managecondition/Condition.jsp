<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.PropertyUtil" %>
<html lang="en">
    <head>
        <title>NBS: Manage Conditions Library</title>
        <%@ include file="/jsp/resources.jsp" %>
        
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageConditionForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPropertyUtilClient.js"></SCRIPT>
        <script type="text/javaScript" src="pagemanagementSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javaScript">

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
        function makeEnabled()
        {
		  	$j("#pCoIn").attr("disabled", true);
		  	disableAllBrowsers($j("#cCoInf"));
			//$j("#cCoInf").attr("disabled", true);
			getElementByIdOrByName("pCoIn_textbox").disabled=true;
			$j("#pCoIn_button").attr("disabled", true);
	    var prArea =$j("#pAreaFld").val();
				  
		          JPropertyUtilClient.getIsStdOrHivProgramArea(prArea,{
		        	  callback:function(bl) { 
		        		    if(bl){
							 	$j("#pCoIn").attr("disabled", false);
							 	enableAllBrowsers($j("#cCoInf"));
								//$j("#cCoInf").attr("disabled", false);
								getElementByIdOrByName("pCoIn_textbox").disabled=false;
								$j("#pCoIn_button").attr("disabled", false);
		        		    	
		            		    }
		        		  }
		          }  );
				  
		          if( prArea == null || prArea == ''){
		        	  enableAllBrowsers($j("#cCoInf"));
					  $j("#pCoIn").attr("disabled", false);
									//$j("#cCoInf").attr("disabled", false);
									getElementByIdOrByName("pCoIn_textbox").disabled=false;
									$j("#pCoIn_button").attr("disabled", false);
					  }
		  }
          
        function checkPublished()
        {
		  var conditonCode =$j("#condCd").attr("value");
		  var setDisable=false;
		  JPropertyUtilClient.getPublished(conditonCode,{
			  callback:function(bl) {
				if(bl){
					   //$j("#cCoInf").attr("disabled", true);
					   disableAllBrowsers($j("#cCoInf"));
					   $j("#pCoIn").parent().parent().find("img").attr("disabled", true);
					   $j("#pCoIn").parent().parent().find("img").attr("tabIndex", "-1");
					   
  					   $j("#pCoIn").parent().parent().find("input").attr("disabled", true);
					}
				  }
		  }  );
		  
		  
           
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
  <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
    <body onload="autocompTxtValuesForJSP();startCountdown();makeEnabled();checkPublished();">
    </logic:notEqual>
   <logic:equal name="manageConditionForm" property="actionMode" value="View">
     <body onload="autocompTxtValuesForJSP();startCountdown();checkPublished();">
     </logic:equal>
     
        <div id="blockparent"></div>

         <html:form action="/ManageCondition.do" styleId="conditionForm">
            <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
	                		 <!-- Top Nav Bar and top button bar -->
								 <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
								 <input type="hidden" id="condCd" value="${manageConditionForm.selection.conditionCd}"/>
								  <logic:equal name="manageConditionForm" property="actionMode" value="View"> 
							    	<table role="presentation" style="width:100%;">
										<tr>
										    <td style="text-align:right"  id="srtLink"> 
										        <a id="manageLink" href="/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&context=ReturnToManage&existing=true">
										            Return to Condition Library
										        </a>  
										        <input type="hidden" id="actionMode" value="${manageConditionForm.actionMode}"/>
										    </td>
										</tr>
									</table>
									</logic:equal>
									<logic:notEqual name="manageConditionForm" property="actionMode" value="View"> 
										<table role="presentation" style="width:100%;">
											<TR>
												<TD align='right'>
											 	 <i>
										             <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
										     	 </i>
										      </TD>
									       </TR>
									     </table>
								     </logic:notEqual>
								      
								 <!-- top bar -->
								<tr><td>&nbsp;</td></tr>
				            	<div class="popupButtonBar">
					             	 <logic:notEqual name="manageConditionForm" property="actionMode" value="View"> 
						                 <input type="submit" id="submitB" value="Submit" onClick="return submitForm();"/>
						                 <input type="submit" id="submitB" value="Cancel" onClick="return cancelForm();"/>
						                 &nbsp;
								     </logic:notEqual>
								     <logic:equal name="manageConditionForm" property="actionMode" value="View"> 						                 
						                  <% String activeInd = (String)request.getAttribute("ActiveInd");
						                  if (activeInd != null && activeInd.equals("Inactive")) { %>
						                  <input type="submit" id="submitB" value="Edit" onClick="return EditForm();"/>
						                 <input type="button" id="submitB" value="Make Inactive" onClick="makeInactive();"/>
						                 <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
						                 <input type="button" id="submitB" value="Make Active" onClick="makeActive();"/>
						                 <%} %>
								     </logic:equal> 
						         </div>
						         <!-- Error message -->
								<% if(request.getAttribute("error") != null) { %>
								    <div class="infoBox errors">
								        <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
								        <ul>
								            <li>${fn:escapeXml(error)}</li>
								        </ul>
								    </div>    
								<% }%>
								<% if(request.getAttribute("CreateError") != null) { %>
								<div class="infoBox errors">
								    <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
								         <%= request.getAttribute("CreateError")%>
								</div>
								<% }%>
								<%@ include file="/jsp/errors.jsp" %>
								<%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
								 								 
							 	<% if (request.getAttribute("fromConditionLib") != null ) { %>
								  <div class="infoBox info">
								  	This condition has been related to the <b>${fn:escapeXml(pageNm)}</b> but 
								  	requires data porting before this page will be available for data entry  in the 
								  	system. Until data porting has been completed, this condition will continue to utilize the 
								  	<b><%=request.getAttribute("conditionNm")%></b> legacy page.
								  </div>    
					           <% } %>
							 	<% if (request.getAttribute("ConfirmMesgCreate") != null) { %>
								  <div class="infoBox success">
								  	<b>${fn:escapeXml(ConditionShortNm)}</b> ${fn:escapeXml(ConfirmMesgCreate)}&nbsp;<a href='${fn:escapeXml(clickHereLk)}' >Click Here</a> to access the Page Library.<br/>
								  </div>    
					           <% } %>
					           <% if (request.getAttribute("ConfirmMesg") != null) { %>
								  <div class="infoBox success">
								  	<b>${fn:escapeXml(ConditionShortNm)}</b> ${fn:escapeXml(ConfirmMesg)}
								  </div>    
					           <% } %>
					           <tr style="background:#FFF;">
					               <td colspan="2">
					                   <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
					                   </div>                        
					               </td>
					           </tr>
								 <!-- Container Div -->
	          				 
									<tr><td>
									<fieldset style="border-width:0px;" id="condition">
								      <nedss:container id="section3" name="${manageConditionForm.sectionCondition}" classType="sect" displayImg ="false" includeBackToTopLink="no">
								      <!-- Form Entry Errors -->
								              <nedss:container id="subsec2" classType="subSect" name="Condition Details" >
								                  <tr>
								                      <td class="fieldName"  id="cSys">
								                          <font class="boldTenRed" > * </font><span>Coding System:</span>
								                      </td>
								                      <td>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="Create">
								                              <html:select title="Coding System" property="selection.codeSystemCd" styleId = "cSysDn">
								                                  <html:optionsCollection property="SRTAdminCodingSysCd" value="key" label="value"/>
								                              </html:select>
								                          </logic:equal>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="Create">
								                              <nedss:view name="manageConditionForm" property="selection.codeSystemDescTxt"/>
								                          </logic:notEqual>                
								                      </td>
								                  </tr>
								                  <tr>
								                      <td class="fieldName"  id="cCode">
								                          <font class="boldTenRed" > * </font><span>Condition Code:</span>
								                      </td>
								                      <td>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="Create">
								                              <html:text title="Condition Code" property="selection.conditionCd" size="16" maxlength="16" styleId="cCodeFld" onkeyup="isSpecialCharEnteredForCode(this, event)"/>
								                          </logic:equal>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="Create">
								                              <nedss:view name="manageConditionForm" property="selection.conditionCd"/>
								                          </logic:notEqual>                
								                      </td>
								                  </tr>
								                  <tr>
								                      <td class="fieldName"  id="cond">
								                          <font class="boldTenRed" > * </font><span>Condition Name:</span>
								                      </td>
								                      <td>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
								                              <html:text title="Condition Name" property="selection.conditionShortNm" size="50" maxlength="50" styleId="condFld" onkeyup="isSpecialCharEnteredForName(this,null,event)"/>
								                          </logic:notEqual>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.conditionShortNm"/>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								                  <tr>
								                      <td class="fieldName"  id="parea">
								                          <font class="boldTenRed" > * </font><span>Program Area:</span>
								                      </td>
								                      <td>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.progAreaCd"/>
								                          </logic:equal>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="Edit">
								                              <nedss:view name="manageConditionForm" property="selection.progAreaCd"/>
								                              <html:hidden name="manageConditionForm" property="selection.progAreaCd" styleId="pAreaFld"/>
								                          </logic:equal>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="Create">
														  <html:select title="Program Area" property="selection.progAreaCd" styleId="pAreaFld" onchange="makeEnabled()">
								                                <html:optionsCollection property="programAreaList" value="key" label="value"/>
								                              </html:select>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								                  <tr>
							                      <td class="fieldName"  id="cFamily"><span>Condition Family:</span>
								                      </td>
								                      <td>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
								                              <html:select title="Condition Family" property="selection.familyCd" styleId = "pCon">
								                                  <html:optionsCollection property="codedValues(CONDITION_FAMILY)" value="key" label="value"/>
								                              </html:select>
								                          </logic:notEqual>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.familyCd" codeSetNm="CONDITION_FAMILY"/>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								                  <tr>
								                 
								                
								                 
							                      <td class="fieldName"  id="cCoInf"><span>Co-infection Group:</span>
								                      </td>
								                      <td>
								                      <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
															 <html:select title="Co-infection Group" property="selection.coInfGroup" styleId = "pCoIn" >
									                                  <html:optionsCollection property="codedValues(COINFECTION_GROUP)" value="key" label="value"/>
									                              </html:select>
								                          </logic:notEqual>
							                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
							                              <nedss:view name="manageConditionForm" property="selection.coInfGroup" codeSetNm="COINFECTION_GROUP"/>
							                          </logic:equal>  
								                  </tr>
								                  <logic:notEqual name="manageConditionForm" property="actionMode" value="Create">
									                  <tr>
									                      <td class="fieldName"  id=""><span>Status:</span></td>
									                      <td>
									                           <nedss:view name="manageConditionForm" property="selection.statusCdDescTxt"/>
									                      </td>
									                  </tr>
								                  </logic:notEqual> 
								              </nedss:container> 
								              <nedss:container id="subsec3" classType="subSect" name="Condition Behavior" >
								                  <tr>
								                      <td class="fieldName"  id="abc"><span>CDC Reportable Condition (NND):</span>
								                      </td>
								                      <td>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
								                          	<input type="hidden" name="nndInd1" value="Y"> 
								                              <html:radio title="CDC Reportable Condition (NND) Yes" name="manageConditionForm" property="selection.nndInd" styleId="nndInd" value="Y" > Yes </html:radio>
								      						  <html:radio title="CDC Reportable Condition (NND) Yes" name="manageConditionForm" property="selection.nndInd" styleId="nndInd" value="N" > No </html:radio>
								                          </logic:notEqual>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.nndInd"/>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								                  <tr>
								                      <td class="fieldName"  id="abc"><span>Reportable through Morbidity Reports:</span>
								                      </td>
								                      <td>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
								                              <html:radio title="Reportable through Morbidity Reports Yes" name="manageConditionForm" property="selection.reportableMorbidityInd" value="Y"> Yes </html:radio>
								      						  <html:radio title="Reportable through Morbidity Reports No" name="manageConditionForm" property="selection.reportableMorbidityInd" value="N"> No </html:radio>
								                          </logic:notEqual>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.reportableMorbidityInd"/>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								                  <tr>
								                      <td class="fieldName"  id="abc"><span>Reportable in Aggregate (Summary):</span>
								                      </td>
								                      <td>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
								                              <html:radio title="Reportable in Aggregate (Summary) Yes" name="manageConditionForm" property="selection.reportableSummaryInd" value="Y" > Yes </html:radio>
								      						  <html:radio title="Reportable in Aggregate (Summary) No" name="manageConditionForm" property="selection.reportableSummaryInd" value="N"> No </html:radio>
								                          </logic:notEqual>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.reportableSummaryInd"/>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								                  <tr>
								                      <td class="fieldName"  id="abc"><span>Utilizes Contact Tracing Module:</span>
								                      </td>
								                      <td>
								                          <logic:notEqual name="manageConditionForm" property="actionMode" value="View">
								                              <html:radio title="Utilizes Contact Tracing Module Yes" name="manageConditionForm" property="selection.contactTracingEnableInd" value="Y"> Yes </html:radio>
								      						  <html:radio title="Utilizes Contact Tracing Module No" name="manageConditionForm" property="selection.contactTracingEnableInd" value="N"> No </html:radio>
								                          </logic:notEqual>
								                          <logic:equal name="manageConditionForm" property="actionMode" value="View">
								                              <nedss:view name="manageConditionForm" property="selection.contactTracingEnableInd"/>
								                          </logic:equal>                
								                      </td>
								                  </tr>
								              </nedss:container> 
								      		</nedss:container>
								       </fieldset>
       								   <div class="popupButtonBar">
						             	 <logic:notEqual name="manageConditionForm" property="actionMode" value="View"> 
							                 <input type="submit" id="submitB" value="Submit" onClick="return submitForm();"/>
							                 <input type="submit" id="submitB" value="Cancel" onClick="return cancelForm();"/>
							                 &nbsp;
									     </logic:notEqual>
									     <logic:equal name="manageConditionForm" property="actionMode" value="View"> 							                 
							                  <% String activeInd = (String)request.getAttribute("ActiveInd");
							                  if (activeInd != null && activeInd.equals("Inactive")) { %>
							                  <input type="submit" id="submitB" value="Edit" onClick="return EditForm();"/>
							                 <input type="button" id="submitB" value="Make Inactive" onClick="makeInactive();"/>
							                 <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
							                 <input type="button" id="submitB" value="Make Active" onClick="makeActive();"/>
							                 <%} %>
									     </logic:equal> 
						         	</div>
								</td></tr>	
							</div>
						</td>
					</tr>
				</div>
			</html:form>
		</body>
</html>

