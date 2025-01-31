<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.*" %>

<html lang="en">
	<head>		
		<title>Transfer Investigation Ownership</title>
		<%@ include file="/jsp/resources.jsp" %>
		<SCRIPT Language="JavaScript" Src="NotificationSpecific.js"></SCRIPT>	
		
	</head>
	<script language="JavaScript">
	function tOwnership() {
		var opener = getDialogArgument();
		var newJur = getElementByIdOrByName("transOwn").value;
		var exportFacility= getElementByIdOrByName("exportFacility").value;
		var comment = getElementByIdOrByName("NTF137").value;
		if(checkExternalFacility()){
			//getElementByIdOrByName("reqDependentId").style.display="";
			return false;
		}else{
			opener.pamTOwnership(newJur,exportFacility,comment);
			var invest = getElementByIdOrByNameNode("blockparent", opener.document)
			invest.style.display = "none";		
			window.returnValue ="true";		
			window.close();
		}
		
	}
	function trim(str)
	{
	    while (str.charAt(0) == " ") {
	        // remove leading spaces
	        str = str.substring(1);
	    }
	    
	    while (str.charAt(str.length - 1) == " "){
	        // remove trailing spaces
	        str = str.substring(0,str.length - 1);
	    } 
	    return str;
	}
	
	function closeTOwnership() {
		self.close();
		var opener = getDialogArgument();		
		var invest = opener.getElementByIdOrByName("blockparent")
		invest.style.display = "none";					
	}
	function checkMaxLength(sTxtBox) {				
		maxlimit = 1000;					
		if (sTxtBox.value.length > maxlimit)		
		sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}
	
	function enableCommentAndRecipientFields() 
	{
		var newJur = getElementByIdOrByName("transOwn").value;
		if (trim(newJur).length > 0) {
			// make the label black
			getElementByIdOrByName("assignAuCD").style.color = "black";
			
			// enable/disable related fields
			var exportList= getElementByIdOrByName("exportJurisdictionList").value;
			var mySplitResult = exportList.split("|");
			for(i = 0; i < mySplitResult.length; i++){
				if(trim(mySplitResult[i]).length > 0){
					if(newJur==mySplitResult[i]){
						getElementByIdOrByName("errorBlock").style.display="none";
						
						getElementByIdOrByName("comment").className="visible";
						getElementByIdOrByName("exportList").className="visible";
						getElementByIdOrByName("comment").className="tablerow";
						getElementByIdOrByName("exportList").className="tablerow";
					}
					else {
						getElementByIdOrByName("errorBlock").style.display="none";
						getElementByIdOrByName("comment").className="none";
						getElementByIdOrByName("exportList").className="none";
						
						var errorTD = getElementByIdOrByName("errorBlock");
						errorTD.innerText = "";
					}
				}
			}
		}
	}
	
	function checkExternalFacility()
	{
		getElementByIdOrByName("errorBlock").style.display="none";
	    
	    var errors = new Array();
	    var index = 0;
	    var isError = false;
	    var newJur = trim(getElementByIdOrByName("transOwn").value);
	    
		var exportList= getElementByIdOrByName("exportJurisdictionList").value;
		var mySplitResult = exportList.split("|");
				
		if( newJur != null && newJur.length == 0) {
			errors[index++] =  "New Jurisdiction is  required";
			getElementByIdOrByName("assignAuCD").style.color="#CC0000";
			isError = true;		
		}
		else {
		   getElementByIdOrByName("assignAuCD").style.color="black";
		}
		
		if(isError) {
			displayErrors("errorBlock", errors);
			return isError;
		}
		else {
			for(i = 0; i < mySplitResult.length; i++){
				if(trim(mySplitResult[i]).length>0){
					if(newJur==mySplitResult[i]  && trim(getElementByIdOrByName("transOwn").value).length>0){
						
						var commentfield=trim(getElementByIdOrByName("NTF137").value);
						var externalFacility = getElementByIdOrByName("exportFacility").value;
						
						if( externalFacility != null && externalFacility == 0) {
							errors[index++] =  "Recipient is  required";
							getElementByIdOrByName("transferTo").style.color="#CC0000";
							isError = true;		
						}
						else {
						   getElementByIdOrByName("transferTo").style.color="black";
						}
						if( commentfield != null && commentfield == 0) {
							errors[index++] =  "Transfer Comments is  required";
							getElementByIdOrByName("TransferComments").style.color="#CC0000";
							isError = true;		
						}
						else {
						   getElementByIdOrByName("TransferComments").style.color="black";
						}
						
						if(isError) {
							displayErrors("errorBlock", errors);
						}
						return isError;	
					}
				}
			}
		}
	}
</script>

	<body onunload="closeTOwnership()">		
		<html:form>
		 <html:hidden name="investigationForm" property="exportJurisdictionList" styleId="chkboxIds"/>
			 <table role="presentation" cellspacing="15" cellpadding="20" style="font-family : Geneva, Arial, Helvetica, sans-serif; font-size : 10pt;">
			  
				<tr style="background:#FFF;">
	              <td colspan="2">
	                  <div class="infoBox errors" style="display:none;" id="errorBlock">
	                  </div>
	              </td>
		          </tr>
				  	<tr>
			  	  		<td colspan="2">
	  	  					Transfer <b>${fn:escapeXml(DSInvestigationLocalID)}</b> from <b>${fn:escapeXml(oldJurisdiction)}</b> Jurisdiction to:
			  	  		</td>
				  	</tr>
		          	<tr>
		                <td class="InputFieldLabel" id="assignAuCD">
		                      <span style="color:#CC0000">*</span>
			                <span style="">
			                    New Jurisdiction:
			                </span> 
			        </td>
		               <td class="InputField" id="transfer">
			               	<html:select  title="New Jurisdiction"  property="proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd"  onchange="enableCommentAndRecipientFields()" styleId ="transOwn">
			               		<html:optionsCollection property="jurisdictionList" value="key" label="value"/>
		                  	</html:select>
		               </td>
		          </tr>
		           <tr id="exportList" class="none">
		               <td class="InputFieldLabel" id="transferTo">
		                      <span style="color:#CC0000">*</span>
			                <span style="" >
			                    Recipient:
			                </span> 
			        </td>
		               <td class="InputField">
			               	<html:select title="Recipient" property="proxy.notificationVO_s.theNotificationDT.exportReceivingFacilityUid"  styleId ="exportFacility">
			               		<html:optionsCollection property="exportFacilityList" value="key" label="value"/>
		                  	</html:select>
		               </td>
		          </tr>
      		      <tr id="comment" class="none">
      		           <td class="InputFieldLabel" id="TransferComments">
		                    <span style="color:#CC0000">*</span>
			                <span style="" >
			                    Transfer Comments:
			                </span> 
			           </td>
		               <td>
						 	<textarea title="Transfer Comments" rows="6" cols="60" id="NTF137" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"></textarea>
					   </td>
		          </tr>
		          <tr>
			  	  		<td colspan="2">
						    <b>If this investigation has any associated observations or documents, the jurisdiction for these observations or documents will be transferred automatically to this new jurisdiction, as well.</b>
<br/>
<br/>
<b>NOTE: Any contact records associated with this investigation WILL NOT be transferred to the new jurisdiction.  If you would like to transfer the jurisdiction for any of the contact records, you can transfer them manually from the edit contact record screen.</b>
		  	  	  	</td>
			  	  </tr>
			     <tr>
				      <td style="padding-right:145px;" align="right" colspan="2">
				            <input type="button" class="Button" value="Submit" onclick="tOwnership()"/>
				            <input type="button" class="Button" value="Cancel" onclick="closeTOwnership()"/>
				      </td>
			     </tr>																				  
			  </table>
		</span>
	</html:form>
</body>	
</html>
