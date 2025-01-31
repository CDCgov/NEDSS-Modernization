<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>${fn:escapeXml(PageTitle)}</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script type="text/javaScript">
		 function cancelForm()
		    {
		        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
		        if (confirm(confirmMsg))
		        {
		            document.forms[0].action ="${SRTAdminManageForm.attributeMap.cancel}";
		        }
		        else {
		            return false;
		        }
		    }

		    function submitForm(){
		        if(CodeSetReqFlds()) {
		            return false;
		        }
		        else {
		            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
		        }
		    }

		    function EditForm()
		    {
		    	document.forms[0].action ="${SRTAdminManageForm.attributeMap.EditValueSet}";
		    }

		    function makeInactive()
	        {
	        	var CodesetNm = '${fn:escapeXml(CodesetNm)}';
	        	var confirmMsg="You have indicated that you would like to inactivate the "+ CodesetNm +" value set. Once inactivated, this value set will no longer be available to users as a selection when creating and updating coded questions. Select OK to continue or Cancel to return to View Value Set.";
		        if (confirm(confirmMsg))
		        {
		        	document.forms[0].action ="${SRTAdminManageForm.attributeMap.MakeInactive}";
		        	document.forms[0].submit();
		        }
		        else {
		            return false;
		        }
	        }
		    function makeActive()
	        {
	        	
		       document.forms[0].action ="${SRTAdminManageForm.attributeMap.MakeActive}";
		       document.forms[0].submit();
		        
	        }
		    function viewConceptCd(codesetNm, code)
		    {
		    	var divElt = getElementByIdOrByName("blockparent");
	            divElt.style.display = "block";		
	            var o = new Object();
	            o.opener = self;
	            //window.showModalDialog("/nbs/ManageCodeSet.do?method=viewCodeValGenCode&codesetNm="+codesetNm+"&codeVal="+code, o, GetDialogFeatures(650, 500, false));
	            
	            var URL = "/nbs/ManageCodeSet.do?method=viewCodeValGenCode&codesetNm="+codesetNm+"&codeVal="+code;
	            var modWin = openWindow(URL, o,GetDialogFeatures(790, 570, false, true), divElt, "");
	            //return false;
		    }
		    function editConceptCd(codesetNm, code)
		    {
		    	var divElt = getElementByIdOrByName("blockparent");
	            divElt.style.display = "block";		
	            var o = new Object();
	            o.opener = self;
	            //window.showModalDialog("/nbs/ManageCodeSet.do?method=editCodeValGenCode&codesetNm="+codesetNm+"&codeVal="+code, o, GetDialogFeatures(650, 500, false));
	            
	            var URL = "/nbs/ManageCodeSet.do?method=editCodeValGenCode&codesetNm="+codesetNm+"&codeVal="+code; 
	            var modWin = openWindow(URL, o,GetDialogFeatures(790, 570, false, true), divElt, "");
	            //return false;
		    }

			 function addConceptCd()
			 {
				 document.getElementsByTagName("form")[0].action="";
				
				var divElt = getElementByIdOrByName("blockparent");
	            divElt.style.display = "block";		
	            var o = new Object();
	            o.opener = self;

				if(window.showModalDialog!=undefined && navigator.userAgent.indexOf('Safari')==-1){//IE

					 window.showModalDialog("/nbs/ManageCodeSet.do?method=createCodeValueGenCode", o, GetDialogFeatures(790, 580, false, true));
					 if(divElt!=null && divElt !=undefined)
						divElt.style.display = "";
					 document.forms[0].submit();

				}
				
				
				else{//new browser
				
					var dialogFeatures=GetDialogFeatures(800, 500, false, true);
					dialogFeatures = dialogFeatures.replace("dialogWidth","width").replace("dialogHeight",",height").replace(new RegExp(":", "g"),"=");
					var URL = "/nbs/ManageCodeSet.do?method=createCodeValueGenCode";
					var modWin = window.open(URL,"",dialogFeatures);
					
					 modWin.onbeforeunload=function(){
					
						//if(divElt!=null && divElt !=undefined)
						divElt.style.display = "";
						document.forms[0].submit();
				 };
				 
				 
				}

	            return false;
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
    <body onload="autocompTxtValuesForJSP();startCountdown();showCount();">
    <div id="blockparent"></div>
    <div id="doc3">
         <html:form action="/ManageCodeSet.do" styleId="codeSetForm">	
            <div id="bd">
            <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
             
            <!-- top bar -->
            		 <!-- Indicates Required field -->
            		<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
				        <div align="right"  style="padding-top: 8px;">
				            <i>
				                <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
				            </i>
				        </div>
			        </logic:notEqual>
			        <!-- Return to System Admin Screen -->
			        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		            	<table role="presentation" style="width:100%;">
							<tr>
							    <td style="text-align:right"  id="srtLink"> 
							        <a id="manageLink" href="/nbs/ManageCodeSet.do?method=ViewValueSetLib&context=ReturnToManage">
							            Return to Value Set Library
							        </a>  
							    </td>
							</tr>
						</table>
			        </logic:equal>
			        <!--Top Button bar  -->
			  		<tr><td>&nbsp;</td></tr>
			  		<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		            	<div class="popupButtonBar">
				            <input type="submit" name="submitA" id="submitA" value="Edit" onClick="return EditForm();"/>
				            <% String activeInd = (String)request.getAttribute("ActiveInd");
			                 if (activeInd != null && activeInd.equals("Inactive")) { %>
			                <input type="button" id="submitB" value="Make Inactive" onClick="makeInactive();"/>
			                <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
			                <input type="button" id="submitB" value="Make Active" onClick="makeActive();"/>
			                <%} %>
				         </div>
			         </logic:equal>
			         <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		            	<div class="popupButtonBar">
				            <input type="submit" name="submitA" id="submitA" value="Submit" onClick="return submitForm();"/>
				            <input type="submit" id="submitB" value="Cancel" onClick="return cancelForm();"/>
				         </div>
			         </logic:notEqual>
			         <!-- Confirm Message -->
			           <% if (request.getAttribute("ConfirmMesg") != null) { %>
						  <div class="infoBox success">
						   <% if ("makeCodeSetInactive".equalsIgnoreCase(request.getParameter("method")) || "makeCodeSetActive".equalsIgnoreCase(request.getParameter("method"))) { %>
						        The <b>${fn:escapeXml(codeSetNm)}</b>${fn:escapeXml(ConfirmMesg)}
						   <% } else { %>
						       <b>${fn:escapeXml(codeSetNm)}</b>${fn:escapeXml(ConfirmMesg)}
						    <%}%>  
						  </div>                     
			           <% } %>
			           
			           
			           <% if (request.getAttribute("ConfirmMesgImport") != null) { %>
						  <div class="infoBox success">
						     <b>${fn:escapeXml(CodesetNm)}</b>${fn:escapeXml(ConfirmMesgImport)}
						  </div>    
			           <% } %>
			           
			           <!-- Error message -->
						<% if(request.getAttribute("error") != null) { %>
						    <div class="infoBox errors">
						        <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
						        <ul>
						            <li><c:out value="${error}"/></li>
						        </ul>
						    </div>    
						<% }%>
						
						 <!-- Form Entry Errors -->
		                <tr style="background:#FFF;">
		                    <td colspan="2">
		                        <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
		                        </div>                        
		                    </td>
		                </tr>
						
					<!-- Page Code Starts here -->
		             
		             <nedss:container id="section1" name="${SRTAdminManageForm.actionMode} Value Set " classType="sect" displayImg ="false" includeBackToTopLink="false">
                    	<fieldset style="border-width:0px;" id="codeset">
                        <nedss:container id="subsec1" classType="subSect" name="Value Set Details">
                        	<tr>
                                <td class="fieldName"  id="ValSt">
                                	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                		<font class="boldTenRed" > * </font>
                                	</logic:notEqual><span>Value Set Type:</span>
                                </td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:select title="Value Set Type" name="SRTAdminManageForm"  property="selection.valueSetTypeCd" styleId="ValStF"> 
							                 <html:optionsCollection property="valueSetTypeCdNoSystemStrd" value="key" label="value"/>
							            </html:select>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                                        <nedss:view name="SRTAdminManageForm" property="selection.valueSetTypeCd"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.valueSetTypeCd"/>
                                    </logic:equal>
                                </td>
                            </tr>
		             		<tr>
                                <td class="fieldName"  id="ValSC">
                                	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                		<font class="boldTenRed" > * </font>
                                	</logic:notEqual><span>Value Set Code:</span>
                                </td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text title="Value Set Code" property="selection.valueSetCode" styleId="ValSCF" maxlength="256" size="50" onkeyup="isSpecialCharEnteredForCodeOnlyAlphAndUnderscore(this,event)"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                                        <nedss:view name="SRTAdminManageForm" property="selection.valueSetCode"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.valueSetCode"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"  id="ValSN">
                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                	<font class="boldTenRed" > * </font>
                                </logic:notEqual><span>Value Set Name:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                    <% if(request.getAttribute("SYSValue") != null && request.getAttribute("SYSValue").toString().equals("SYS")) { %>
										<nedss:view name="SRTAdminManageForm" property="selection.valueSetNm"/>   
								    <% }else{ %>
                                        <html:text title="Value Set Name" property="selection.valueSetNm" styleId="ValSNF" size="50" maxlength="256" onkeyup="isSpecialCharEnteredForCodeOnlyAlphAndUnderscore(this,event)"/>
                                     <%} %>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.valueSetNm"/>
                                    </logic:equal>
                                </td>
                            </tr> 
                            <tr>
                                <td class="fieldName"  id="ValSD">
                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                	<font class="boldTenRed" > * </font>
                                </logic:notEqual><span>Value Set Description:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:textarea title="Value Set Description" style="WIDTH: 400px; HEIGHT: 100px;"  styleId="ValSDF" name="SRTAdminManageForm" property="selection.codeSetDescTxt" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeSetDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                            <tr>
		                                <td class="fieldName"  id=""><span>Status:</span>
		                                </td>
		                                <td>
		                                    <nedss:view name="SRTAdminManageForm" property="selection.statusCdDescTxt"/>
		                                </td>
		                            </tr> 
	                        </logic:equal> 
		              	</nedss:container>
		              	
		              	<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		              		<input type="hidden" id="queueCnt" value="${fn:escapeXml(queueConcept)}"/>
		                        <nedss:container id="subsec2" name="Value Set Concepts" classType="subSect" displayLink="false">
		                        	<tr><td>
		                                <table role="presentation" width="98%" align="center">
		                                 <!-- Concept confirm message -->
								
										  <% if (request.getAttribute("confirmConceptMsg") != null) { %>
											 <!-- Edit SuccessMessage is displaying using  confirmConceptMsg -->
											  <div class="infoBox success"><c:out value="${confirmConceptMsg}" escapeXml="false"/></div>    
								           <% } %>	
								           
								           
								           <% if (request.getAttribute("confirmAddConceptMsg") != null) { %>
											 <!-- Edit SuccessMessage is displaying using  confirmConceptMsg -->
											  <div class="infoBox success"><c:out value="${confirmAddConceptMsg}" escapeXml="false"/></div>    
								           <% } %>
								           
								           							          
		                                    <tr>
		                                        <td align="center">
		                                            <display:table name="CodeValGNList" class="dtTable" pagesize="10"  id="parent" requestURI="/ManageCodeSet.do?method=viewCodeSet&existing=true" >
		                                                <display:column property="viewLink" title="<p style='display:none'>View</p" style="width:4%;text-align:center;"/>
		                                                <display:column property="editLink" title="<p style='display:none'>Edit</p" style="width:4%;text-align:center;"/>
		                                                
		                                                
		                                                <!-- Added by jayasudha for showing newly added fields -->
		                                                
		                                                <display:column property="code" title="Local Code" sortable="true" sortName="getCode" defaultorder="ascending"/>
		                                                <display:column property="codeShortDescTxt" title="UI Display Name" sortable="true" sortName="getCodeShortDescTxt" defaultorder="ascending"/>
		                                                <display:column property="conceptCode" title="Concept Code" sortable="true" sortName="getConceptCode" defaultorder="ascending"/>
		                                                <display:column property="conceptPreferredNm" title="Messaging Concept Name" sortable="true" sortName="getConceptPreferredNm" defaultorder="ascending"/>
		                                                <display:column property="codeSystemDescTxt" title="Code System Name" sortable="true" sortName="getCodeSystemDescTxt" defaultorder="ascending"/>
		                                                <display:column property="statusCode" title="Status" sortable="true" sortName="getStatusCode" defaultorder="ascending"/>
		                                                <display:column property="effectiveFromTime" title="Effective From" sortable="true" comparator="gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminDateComparator" />
		                                                <display:column property="effectiveToTime" title="Effective To" sortable="true"  comparator="gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminDateComparator" />
		                                                <!--  here by default I am using default sorting as acending order , but our effective to time  accepted the null value , So due to this reason sorting is not done properly 
		                                                        If we want to display the proper sorting results we need to create our own comparator [null checking handling logic required]
		                                                 -->
		                                                
		                                                <!-- Ended by jayasudha -->
		                                               <display:setProperty name="basic.empty.showtable" value="true"/>
		                                            </display:table>
		                                        </td>
		                                    </tr>
		                                    <tr>
		                                        <td align="right" class="InputButton">
		                                        <% if(request.getAttribute("SYSValue") != null && request.getAttribute("SYSValue").toString().equals("SYS")) { %>
													<input type="button" name="submitCr" id="submitCr" value="Add New" onClick="addConceptCd();" disabled="disabled"/>   
											    <% } else {%>
		                                            <input type="button" name="submitCr" id="submitCr" value="Add New" onClick="addConceptCd();"/>
		                                            <%} %>
		                                        </td>
		                                    </tr>
		                                </table>
		                             </td></tr>
		                        </nedss:container>
		              	</logic:equal>
                    	</fieldset>
                	</nedss:container>
    				<tr><td>&nbsp;</td></tr>
    				<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		            	<div class="popupButtonBar">
				            <input type="submit" name="submitA" id="submitA" value="Edit" onClick="return EditForm();"/>
				            <% String activeInd = (String)request.getAttribute("ActiveInd");
			                 if (activeInd != null && activeInd.equals("Inactive")) { %>
			                <input type="button" id="submitB" value="Make Inactive" onClick="makeInactive();"/>
			                <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
			                <input type="button" id="submitB" value="Make Active" onClick="makeActive();"/>
			                <%} %>
				         </div>
			        </logic:equal>
		         	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		            	<div class="popupButtonBar">
				            <input type="submit" name="submitA" id="submitA" value="Submit" onClick="return submitForm();"/>
				            <input type="submit" id="submitB" value="Cancel" onClick="return cancelForm();"/>
				        </div>
		         	</logic:notEqual>
		         	
            </div>
         </html:form>
        </div>
    </body>
</html>