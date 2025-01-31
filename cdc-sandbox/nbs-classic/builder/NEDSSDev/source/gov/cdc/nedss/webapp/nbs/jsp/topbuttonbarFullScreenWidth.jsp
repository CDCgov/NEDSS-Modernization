<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<% 
    String caseUid = (request.getAttribute("DSInvUid") == null) ? "" : ((String)request.getAttribute("DSInvUid"));
%>
		
<!-- Return to Files Link : When actionMode = VIEW only -->
<logic:notEmpty name="BaseForm" property="attributeMap.coInfectionConditionList">
   	<b><span class="label"> Related Co-Infections: </span>
	   	<span class="value"> ${BaseForm.attributeMap.coInfectionConditionList}</span>
	</b>
</logic:notEmpty>

<logic:equal name="BaseForm" property="actionMode" value="View">
    <logic:equal name="BaseForm" property="securityMap(checkFile)" value="true">
        <div class="returnToPageLink">
           ${BaseForm.attributeMap.linkValue}
             <logic:notEmpty name="BaseForm" property="attributeMap.linkReturnToCustomQueue">
                &nbsp;|&nbsp;${BaseForm.attributeMap.linkReturnToCustomQueue}                 
           	 </logic:notEmpty>
            <logic:notEmpty name="BaseForm" property="attributeMap.linkValue1">
                &nbsp;|&nbsp;${BaseForm.attributeMap.linkValue1}                 
            </logic:notEmpty>
            <logic:notEmpty name="BaseForm" property="attributeMap.linkValueViewEvents">
                &nbsp;|&nbsp;${BaseForm.attributeMap.linkValueViewEvents}                 
            </logic:notEmpty>
        </div>
    </logic:equal> 
</logic:equal>


<!-- Top Button Bar -->
 <logic:notEqual name="BaseForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">

	<table role="presentation" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;" class="topButtonBar">
		<tr>
			<!-- Investigation related actions -->
			<td style="vertical-align:top; padding:0px;">
				<logic:equal name="BaseForm" property="actionMode" value="View">
				   
					<table role="presentation" align="left">
						<tr>
							<logic:equal name="BaseForm" property="securityMap(checkManageEvents)" value="true">
							<logic:notEqual name="BaseForm" property="attributeMap.ManageEventsDisplay" value="NOT_DISPLAYED">
								<td style="vertical-align:top; padding-top:0px;">
									<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40" 
											border="0" name="manageAssociations"  id="manageAssociations"  alt="Manage Associations" title="Manage Associations" 
											class="cursorHand" onclick="return manageAssociations();"> <br/>
									Manage <br/> Associations
								</td>
								 </logic:notEqual>
							</logic:equal>
							<logic:equal name="BaseForm" property="securityMap(checkManageNotific)" value="true">
								<logic:notEqual name="BaseForm" property="attributeMap.CreateNotificationDisplay" value="NOT_DISPLAYED">
									<td style="vertical-align:top; padding-top:0px;">
										<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
												border="0" name="createNoti" id="createNoti" alt="Create Notification" title="Create Notification" 
												class="cursorHand" onclick="return createPamNotification('<%=caseUid%>');"> <br/>
												Create <br/> Notifications
									</td>
								</logic:notEqual>
							</logic:equal>
							<logic:equal name="BaseForm" property="securityMap(checkCaseReporting)" value="true">
								<logic:notEqual name="BaseForm" property="attributeMap.CaseReportingDisplay" value="NOT_DISPLAYED">
								   <logic:notEqual name="BaseForm" property="securityMap(shareButton)" value="NOT_DISPLAYED">
									<td style="vertical-align:top; padding-top:0px;">
										<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
												border="0" name="caseRep" id="caseRep" alt="Case Reporting" title="Case Reporting" 
												class="cursorHand" onclick="return sharePamCaseLoad();"> <br/>Share <br/> Document
									</td>
									 </logic:notEqual>
								</logic:notEqual>
							</logic:equal>
							<logic:equal name="BaseForm" property="securityMap(checkTransfer)" value="true">       
								<logic:notEqual name="BaseForm" property="attributeMap.TransferOwnershipDisplay" value="NOT_DISPLAYED">
									<td style="vertical-align:top; padding-top:0px;">
										<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="createNoti" id="createNoti" alt="Transfer Ownership" title="Transfer Ownership" 
											class="cursorHand" onclick="return transferPamOwnership();"> <br/>
											Transfer <br/> Ownership
									</td>
								</logic:notEqual>
							</logic:equal>
							
							
							<logic:equal name="BaseForm" property="securityMap(checkChangeCondition)" value="true">       
									<td style="vertical-align:top; padding-top:0px;">
										<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="changeCond" id="changeCond" alt="Change Condition" title="Change Condition" 
											class="cursorHand" onclick="return pgChangeCondition();"> <br/>
											Change <br/> Condition
									</td>
							</logic:equal>                        
							
						</tr>
					</table>
					
				</logic:equal>
			</td>
			
			
			<!-- General page actions like create, edit, delete and print --> 
			<td style="vertical-align:top; padding:0px;">
				<table role="presentation" align="right">
				
				   <tr>
						<logic:notEqual name="BaseForm" property="actionMode" value="View">
							<logic:equal name="BaseForm" property="businessObjectType" value="ISO">
							
							
							<td style="vertical-align:top; padding-top:0px;">
								<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
										border="0" name="SubmitTop" id="Submit" alt="Submit button" title="Submit button" 
										class="cursorHand" onclick="return saveSubForm();"> <br/>
								 Submit
							</td>
							<td style="vertical-align:top; padding-top:0px;">
								<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
										border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" 
										class="cursorHand" onclick="return cancelForm();"> <br/>
								Cancel
							</td>
							
							
							</logic:equal>
							<logic:notEqual name="BaseForm" property="businessObjectType" value="ISO">
								<logic:equal name="BaseForm" property="disableSubmitBeforePageLoadsFlag" value="T">
		                        <td style="vertical-align:top; padding-top:0px;">
		                            <input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
		                                    border="0" name="SubmitTop" id="SubmitTop" disabled="disabled" alt="Submit button" title="Submit button" 
		                                    class="cursorHand" onclick="return saveForm();"> <br/>
		                             Submit
		                        </td>
		                       </logic:equal>
		                       <logic:equal name="BaseForm" property="disableSubmitBeforePageLoadsFlag" value="F">
		                        <td style="vertical-align:top; padding-top:0px;">
		                            <input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
		                                    border="0" name="SubmitTop" id="SubmitTop" alt="Submit button" title="Submit button" 
		                                    class="cursorHand" onclick="return saveForm();"> <br/>
		                             Submit
		                        </td>
		                       </logic:equal>
								<td style="vertical-align:top; padding-top:0px;">
									<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" 
											class="cursorHand" onclick="return cancelForm();"> <br/>
									Cancel
								</td>
								</logic:notEqual>
							</logic:notEqual>
						<logic:equal name="BaseForm" property="actionMode" value="View">
							<logic:equal name="BaseForm" property="securityMap(editInv)" value="true">
								<td style="vertical-align:top; padding-top:0px;">
									<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="Delete" id="delete" 
											alt="Edit button"  title="Edit button" class="cursorHand" 
											onclick="return editForm();" /><br/>Edit
								</td>
							</logic:equal>
							<logic:equal name="BaseForm" property="securityMap(deleteInvestigation)" value="true">
								<td style="vertical-align:top; padding-top:0px;">
									<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="Delete" id="delete" 
											alt="Delete button"  title="Delete button" class="cursorHand" 
											onclick="return deleteForm();" /><br/>Delete
								</td>
							</logic:equal>
							
							<logic:equal name="BaseForm" property="businessObjectType" value="ISO">
								<td style="vertical-align:top; padding-top:0px;">
										<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
												border="0" name="Delete" id="delete" 
												alt="Edit button"  title="Edit button" class="cursorHand" 
												onclick="return editForm();" /><br/>Edit
									</td>
								<td style="vertical-align:top; padding-top:0px;">
										<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
												border="0" name="Delete" id="delete" 
												alt="Delete button"  title="Delete button" class="cursorHand" 
												onclick="return deleteForm();" /><br/>Delete
								</td>
							</logic:equal>
							<td style="vertical-align:top; padding-top:0px;">
								<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="Print" id="print" 
											alt="Print page button" title="Print page button" class="cursorHand" 
											onclick="return showPrintFriendlyPage();" /><br/>Print                                   
							</td>
						  
						 
							  <logic:equal name="BaseForm" property="securityMap(printCDCFRForm)" value="true">
								<td style="vertical-align:top; padding-top:0px;">
									<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
												border="0" name="printcdcforms" id="printcdcforms" 
												alt="Print cdc form button" title="Print cdc form button" class="cursorHand" 
												onclick="return printAllForms();" /><br/>Print CDC Forms
								</td>
							</logic:equal>
							
							<logic:notEqual name="BaseForm" property="securityMap(printCDCForm)" value="NOT_DISPLAYED">
							<td style="vertical-align:top; padding-top:0px;">
								<input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
											border="0" name="Delete" id="delete" 
											alt="Print form button" title="Print form button" class="cursorHand" 
											onclick="return printForm();" /><br/>Print CDC Form
							</td>
						                       
					 </logic:notEqual>
						</logic:equal>
						<logic:equal name="BaseForm" property="actionMode" value="CREATE_EXTEND">
							   <td style="vertical-align:top; padding-top:0px;">
							  <input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
							  border="0" name="Add Extended Data" id="delete" 
							  alt="Add extended button"  title="Add extended button" class="cursorHand" 
						onclick="return addExtendedForm();" /><br/>Add<br/>Extended<br/>Data
							</td>
						</logic:equal>
					</tr>            
				</table>
			</td>
		</tr>
	</table>
	<div class="printerIconBlock screenOnly">
    <table role="presentation" style="width:98%; margin:3px;">
        <tr>
            <td style="text-align:right; font-weight:bold;"> 
                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
            </td>
        </tr>
    </table>
</div>
</logic:notEqual>
<logic:equal name="BaseForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
	<div class="grayButtonBar" style="text-align: right;">
		<table role="presentation" width="100%">
			<tr>
				<td align="left" >
					<logic:equal name="BaseForm" property="actionMode" value="View">
						<logic:equal name="BaseForm" property="securityMap(checkMarkReviewd)" value="true">
						    <logic:equal name="BaseForm" property="attributeMap(PDLogic)" value="NA">
								<input type="button" name="markReviewd" value="  Mark as Reviewed  " onclick="markAsReviewed('');"/>	
							</logic:equal>	
							<logic:notEqual name="BaseForm" property="attributeMap(PDLogic)" value="NA">
								<input type="button" name="markReviewd" value="  Mark as Reviewed  " onclick="OpenMarkAsReviewed('LabReport');"/>	
							</logic:notEqual>
						</logic:equal>
						<logic:equal name="BaseForm" property="securityMap(checkClearMarkAsReview)" value="true">
							<input type="button" name="clearMarkReviewd" value="  Clear Mark as Reviewed  " onclick="clearMarkAsReviewed();"/>		
						</logic:equal>
						<logic:equal name="BaseForm" property="securityMap(checkTransfer)" value="true">       
							<input type="button" name="TransferOwn" value="  Transfer Ownership " onclick="transferLabOwnership();"/>		
						</logic:equal>
					</logic:equal>
				</td>

				<td align="right">	
						 <logic:notEqual name="BaseForm" property="actionMode" value="View">
							<input type="button" id="SubmitTop" name="Submit" disabled="disabled" value="  Submit " onclick="saveContextForm();"/>	
							<logic:notEqual name="BaseForm" property="actionMode" value="Edit">	
							 	<logic:equal name="BaseForm" property="securityMap(SubmitCreateInvPage)" value="true">
									<input type="button" name="SubmitAndCreateInvestiation" value="  Submit and Create Investigation " onclick="submitAndCreateInvestgationForm();"/>		
								</logic:equal>
							</logic:notEqual>
									<input type="button" name="Cancel" value="  Cancel " onclick="return cancelContextForm();"/>		
						</logic:notEqual>
						<logic:equal name="BaseForm" property="actionMode" value="View">
							<logic:equal name="BaseForm" property="securityMap(editPage)" value="true">
									 <input type="button" name="edit" value="  Edit  " onclick="return editContextForm();"/>
							</logic:equal>
							<logic:equal name="BaseForm" property="securityMap(deletePage)" value="true">
									<input type="button" name="Delete" value="  Delete  " onclick="return deleteContextForm();"/>
							</logic:equal>
							<logic:equal name="BaseForm" property="securityMap(SubmitCreateInvPage)" value="true">
									<input type="button" name="createInvestigation" value="  Create Investigation  " onclick="CreateInvestigationForm();"/>
									<input type="button" name="AssociateInvestigations" value="  Associate Investigations " onclick="AssociateInvestigationForm();"/>
							</logic:equal>      
								 <input type="button" name="Print" value=" Print  " onclick="showPrintFriendlyPage();"/>
						</logic:equal>
				</td> 
			</tr>
		</table>
	</div>	
</logic:equal> 


