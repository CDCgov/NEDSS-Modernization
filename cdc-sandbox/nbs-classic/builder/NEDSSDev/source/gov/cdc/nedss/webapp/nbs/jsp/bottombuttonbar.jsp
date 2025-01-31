<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<tr>
  <td style="HEIGHT: 15px;"></td>
</tr>
<tr>
  <td><table role="presentation" cellpadding="0" cellspacing="0" border="0" width="750">
      <tr align="left">
        <td valign="top"><img alt="" border="0" height="54" width="25" src="task_button/left_bottom_corner.jpg"></td>
        <td alt ="" background="task_button/tb_cel_bak.jpg" valign="top" align="left" width="500"><logic:equal name="BaseForm" property="actionMode" value="View">
	        <logic:equal name="BaseForm" property="securityMap(checkManageEvents)" value="true">
	            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
	              <tr>
	                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="manageAssociations" 	id="manageAssociations" 	alt="Manage Associations" title="Manage Associations" class="cursorHand" onclick="managmanageAssociations();"></td>
	              </tr>
	              <tr>
	                <td class="boldEightBlack" align="center" valign="top">&nbsp;Manage</br>
	                  Associations&nbsp;</td>
	              </tr>
	            </table>
		<logic:equal name="BaseForm" property="securityMap(checkManageNotific)" value="true">            
	            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
	              <tr>
	                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="createNoti" 	id="createNoti" alt="Create Notification" title="Create Notification" class="cursorHand" onclick="return createRVCTNotification();"></td>
	              </tr>
	              <tr>
	                <td class="boldEightBlack" align="center" valign="top">&nbsp;Create</br>
	                  Notification&nbsp;</td>
	              </tr>
	            </table>
			  </logic:equal>
			  <logic:equal name="BaseForm" property="securityMap(checkCaseReporting)" value="true">            
	            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
	              <tr>
	                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="caseRep" 	id="caseRep" alt="Case Reporting" title="Case Reporting" class="cursorHand" onclick=" "></td>
	              </tr>
	              <tr>
	                <td class="boldEightBlack" align="center" valign="top">&nbsp;Share</br>
	                  Document&nbsp;</td>
	              </tr>
	            </table>
			  </logic:equal>
			  
            <logic:equal name="BaseForm" property="securityMap(checkTransfer)" value="true">      
				<logic:notEqual name="BaseForm" property="attributeMap.TransferOwnershipDisplay" value="NOT_DISPLAYED">      
	            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
	              <tr>
	                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="createNoti" 	id="createNoti" alt="Transfer Ownership" title="Transfer Ownership" class="cursorHand" onclick="return transferPamOwnership();"></td>
	              </tr>
	              <tr>
	                <td class="boldEightBlack" align="center" valign="top">&nbsp;Transfer</br>
	                  Ownership&nbsp;</td>
	              </tr>
	            </table>            
				</logic:notEqual>
            </logic:equal>
          </logic:equal>
          
        </td>
        <td valign="top" width="3"><img alt="" border="0" height="54" width="3" src="task_button/strip_spacer.jpg"></td>
        <td alt ="" background="task_button/tb_cel_bak.jpg" valign="top" align="left" width="250"><logic:notEqual name="BaseForm" property="actionMode" value="View">
            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
              <tr>
                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Submit" id="Submit" alt="Submit button" title="Submit button" class="cursorHand" onclick="return saveForm();">
                </td>
              </tr>
              <tr>
                <td class="boldEightBlack" align="center" valign="top">&nbsp;Submit&nbsp;</td>
              </tr>
            </table>
            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
              <tr>
                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" class="cursorHand" onclick="return cancelForm();">
                </td>
              </tr>
              <tr>
                <td class="boldEightBlack" align="center" valign="top">&nbsp;Cancel&nbsp;</td>
              </tr>
            </table>
          </logic:notEqual>
          <logic:equal name="BaseForm" property="actionMode" value="View">
				<logic:equal name="BaseForm" property="securityMap(editInv)" value="true">
	            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
	              <tr>
	                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" 	name="edit" 	id="edit" 	alt="Edit button" class="cursorHand" onclick="return editForm();"></td>
	              </tr>
	              <tr>
	                <td class="boldEightBlack" align="center" valign="top">&nbsp;Edit&nbsp;</td>
	              </tr>
	            </table>
	           </logic:equal> 
            <logic:equal name="BaseForm" property="securityMap(deleteInvestigation)" value="true">
	            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
	              <tr>
	                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Delete" 	id="delete" 	alt="Delete button" title="Delete button" class="cursorHand" onclick="return deleteForm();"></td>
	              </tr>
	              <tr>
	                <td class="boldEightBlack" align="center" valign="top">&nbsp;Delete&nbsp;</td>
	              </tr>
	            </table>            
            </logic:equal>
            <table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
              <tr>
                <td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" 	name="Print" 	id="print" 	alt="Print button" class="cursorHand" onclick="printForm();"></td>
              </tr>
              <tr>
                <td class="boldEightBlack" align="center" valign="top">&nbsp;Print&nbsp;</td>
              </tr>
            </table>
          </logic:equal>
        </td>
      </tr>
    </table></td>
</tr>
