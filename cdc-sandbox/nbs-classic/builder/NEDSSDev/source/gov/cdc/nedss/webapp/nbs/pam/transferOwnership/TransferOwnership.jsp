<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<html lang="en">
	<head>
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>Transfer Investigation Ownership</title>
		<%@ include file="../../jsp/resources.jsp" %>
		<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
		<script type='text/javascript' src='/nbs/dwr/util.js'></script>	
		<script type='text/javascript' src='/nbs/dwr/interface/JPamForm.js'></script>	
		<SCRIPT Language="JavaScript" Src="PamSpecific.js"></SCRIPT>		
			
		<script language="JavaScript">
		function closeTOwnership() {
			self.close();
			var opener = getDialogArgument();		
			var pview =getElementByIdOrByNameNode("pamview", opener.document)
			pview.style.display = "none";					
		}
	
	function tOwnership() {
		
		var opener = getDialogArgument();
		var newJur = getElementByIdOrByName("TUB237").value;
		var exportFacility= getElementByIdOrByName("exportFacility").value;
		var comment = getElementByIdOrByName("NTF137").value;
		if(checkExternalFacility()){
			//getElementByIdOrByName("reqDependentId").style.display="";
			return false;
		}else{
			opener.pamTOwnership(newJur,exportFacility,comment);
			var invest =getElementByIdOrByNameNode("pamview", opener.document)
			invest.style.display = "none";				
			window.close();
		}
		
	}
	
	function enableCommentAndRecipientFields() 
	{
		var newJur = getElementByIdOrByName("TUB237").value;
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
	    var newJur = getElementByIdOrByName("TUB237").value;
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
					if(newJur==mySplitResult[i]  && trim(getElementByIdOrByName("TUB237").value).length>0){
					
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
	
	function checkMaxLength(sTxtBox) {				
		maxlimit = 1000;					
		if (sTxtBox.value.length > maxlimit)		
		sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}		
	
	function disableflds(){
       	getElementByIdOrByName("errorBlock").style.display="none";
		getElementByIdOrByName("comment").className="none";
		getElementByIdOrByName("exportList").className="none";
	}	
	</script>
	</head>

	<body onload="disableflds()" onunload="closeTOwnership()">		
	<div style="width:100%; margin:0 auto; margin-top:3px; margin-bottom:3px; text-align:left; font-size:1.1em; font-weight:bold;color:white;background:#003470; padding:3px 0px">Transfer Ownership</div>
	     <div  id="topButtId" class="grayButtonBar" style="text-align: right;">
			<input type="button" align="right"  value="   Submit   " onclick="tOwnership()"/>
			<input type="button" align="right"  value= "Cancel" onclick="closeTOwnership()"/>	
	      </div>
	        <div class="infoBox messages" style="text-align: left;">
                      Transfer <b>${PageForm.attributeMap.caseLocalId}</b> from <b>${PageForm.attributeMap.oldJurisdiction}</b> Jurisdiction to:
                 </div>    
	 <html:hidden name="investigationForm" property="exportJurisdictionList" styleId="chkboxIds"/>
	  <div style="padding:0.5em 0em;">
	       <nedss:container id="sect_transferOwn" name="Security Assignment" classType="sect" displayImg="false" includeBackToTopLink="no" displayLink="no">
	       <nedss:container id="subsect_transferOwn" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">	
		<html:form>
		  <tr style="background:#FFF;">
	            <td colspan="2" width="100%">
	                <div class="infoBox errors" style="display:none;" id="errorBlock">
	                </div>
	            </td>
	          </tr>	
	               <tr>
	                <td class="fieldName" id="assignAuCD">
	                 		<font class="boldTenRed" > * </font><b>New Jurisdiction:<b>
	                 </td>	                	
	                 <td>
			             <html:select title="New Jurisdiction" name="PamForm" property="pamClientVO.answer(TUB237)"  onchange="enableCommentAndRecipientFields()"  styleId ="TUB237">
			                 	<html:optionsCollection property="jurisdictionList" value="key" label="value"/>
		                   </html:select>
	                </td>
	            </tr>
	            
	            
	            
	     <tr  id="exportList">
	                <td class="fieldName" id="transferTo">
	                 		<font class="boldTenRed" > * </font><b>Recipient:<b>
	                 </td>	                	
	                 <td>
			             <html:select title="Recipient" name="PamForm" property="pamClientVO.oldPamProxyVO.notificationVO_s.theNotificationDT.exportReceivingFacilityUid"     styleId ="exportFacility">
			                 	<html:optionsCollection property="exportFacilityList" value="key" label="value"/>
		                   </html:select>
	                </td>
	            </tr>
	            
	            
	                <tr  id="comment">
	                <td class="fieldName" id="TransferComments">
	                 		<font class="boldTenRed" > * </font><b>  Transfer Comments:<b>
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
		     </html:form>
		    </nedss:container>
	        </nedss:container>
	        </div>
	     <div  id="botButtId" class="grayButtonBar" style="text-align: right;">
			<input type="button" align="right"  value="   Submit   " onclick="tOwnership()"/>
			<input type="button" align="right"  value= "Cancel" onclick="closeTOwnership()"/>	
	      </div>
	  </body>	
	  </html>
