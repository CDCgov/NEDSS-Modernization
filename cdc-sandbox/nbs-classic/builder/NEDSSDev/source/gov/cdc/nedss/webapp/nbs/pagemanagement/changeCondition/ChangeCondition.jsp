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
<! //////////////////////////////////////////////////////////////////////////////////////////// ->
<! // Note: This JSP is for the Change Condition select and also the Change Condition Confirm.  ->
<! // The confirmation text is hidden until the selection is made.                              ->
<! //////////////////////////////////////////////////////////////////////////////////////////// ->
<html lang="en">
	<head>
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>NBS: Change Condition</title>
		<%@ include file="../../jsp/resources.jsp" %>
		<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
		<script type='text/javascript' src='/nbs/dwr/util.js'></script>	
		<script type='text/javascript' src='/nbs/dwr/interface/JPageForm.js'></script>	
		<SCRIPT Language="JavaScript" Src="PageSpecific.js"></SCRIPT>		
			
	<script language="JavaScript">
	

	document.onkeypress = function (e) {
		var attribute = getCorrectAttribute(document.activeElement, "type", document.activeElement.type);
		
		if(attribute!=null){
			if(attribute.toLowerCase()!="button")
				if (e!=null && e.which=='13')
					return false;
		}
		else
			return false;
		}
	
	function closeChangeConditionDialogBox() {
			self.close();
			var opener = getDialogArgument();			
			var pview = getElementByIdOrByNameNode("pageview", opener.document)
			pview.style.display = "none";
			
	}
	
	function handlePageOnload() {
	                // focus on the first valid element
	                $j("div#changeConditionBlock").find(':input[type!=button]:visible:enabled:first').focus();
    }
    
	function changeToRelatedConditionConfirm() {
		var newCondCd = "";
		var newCondDesc = "";
		
		selectElt = getElementByIdOrByName("INV169");
		if(selectElt.options != null){
		    for (i = 0; i < selectElt.options.length; i++) {
		        if (selectElt.options[i].selected && selectElt.options[i].text != "") {		            
		            newCondDesc = selectElt.options[i].text;
		            newCondCd = selectElt.options[i].value;
		        }
		    }
    		}
		
		//alert("new cond = " + newCondCd + " Desc= " + newCondDesc);
		if(newCondCd == ""){
			alert("Please select a new condition or press Cancel to return to View Investigation");
			return false;
		}
		//hide the selection box and show the confirmation text
		showConfirmText(newCondDesc);


	}
	function showConfirmText(newCondDesc) {
	
		var wkStr = " ";
		//resize the window
		
		//For Modal dialog
	        window.dialogHeight = '440px';
	        window.dialogWidth = '590px';
	        
	    //For Window open
	        window.resizeTo('590','440');

		//replace tokens in the text with the condition descriptions
    	wkStr = getElementByIdOrByName('notificationCheck').innerHTML;
    	wkStr = wkStr.replace("newDesc", newCondDesc);
    	getElementByIdOrByName('notificationCheck').innerHTML = wkStr;
    		
		wkStr = getElementByIdOrByName('youHaveIndicated').innerHTML;
		wkStr = wkStr.replace("newDesc", newCondDesc);
		getElementByIdOrByName('youHaveIndicated').innerHTML = wkStr;
		
		wkStr = getElementByIdOrByName('quesCarriedOver').innerHTML;
		wkStr = wkStr.replace("newDesc", newCondDesc);
		wkStr = wkStr.replace("newDesc", newCondDesc); //twice in string
		getElementByIdOrByName('quesCarriedOver').innerHTML = wkStr;	
		
		wkStr = getElementByIdOrByName('anyAssoc').innerHTML;
		wkStr = wkStr.replace("newDesc", newCondDesc);
		getElementByIdOrByName('anyAssoc').innerHTML = wkStr;	

		getElementByIdOrByName('condTitle').style.display="none";
		getElementByIdOrByName('popupButtonTop').style.display="none";
		getElementByIdOrByName('requiredMsg').style.display="none";
		getElementByIdOrByName('changeConditionBlock').style.display="none";
		getElementByIdOrByName('confirmationText').style.display="block";
	}
	
	function changeToRelatedCondition() {
		var newCondCd = "";
		var newCondDesc = "";
		
		var selectElt = getElementByIdOrByName("INV169");
		if(selectElt.options != null){
		    for (i = 0; i < selectElt.options.length; i++) {
		        if (selectElt.options[i].selected && selectElt.options[i].text != "") {
		            newCondDesc = selectElt.options[i].text;
		            newCondCd = selectElt.options[i].value;
		        }
		    }
    	}			
		
		var opener = getDialogArgument();
		opener.pgChangeToNewCondition(newCondCd, newCondDesc);
		var invest = getElementByIdOrByNameNode("pageview", opener.document)
		invest.style.display = "none";				
		window.close();	
		blockUIDuringFormSubmission();
	}
	
	</script>

	</head>

	<body class="popup" style="width:100%;margin:0em;" onload="handlePageOnload();addRolePresentationToTabsAndSections();" onunload="closeChangeConditionDialogBox()">	
	<html:form style="width:100%;margin: 0em;">

	<div id='condTitle' style="width:100%; margin:0 auto; margin-top:3px; margin-bottom:3px; text-align:left; font-size:1.1em; font-weight:bold;color:white;background:#003470; padding:3px 0px">Change Condition</div>
        <div id="popupButtonTop" class="grayButtonBar" style="text-align:right; margin-top:5px;">
	      	<input type="button" class="Button" value="Submit" onclick="changeToRelatedConditionConfirm()"/>
	      	<input type="button" class="Button" value="Cancel" onclick="closeChangeConditionDialogBox()"/>
	</div>
	        <!-- Required Field Indicator -->
        <div id='requiredMsg' style="text-align:right; width:100%; margin-top:0.5em;"> 
                <span style="color:#CC0000;"> * </span>
                <span style="color:black; font-style:italic;"> Indicates a Required Field </span>  
        </div>
	 
	<!-- Error Messages using Action Messages-->
	<div id="globalFeedbackMessagesBar" class="screenOnly"> </div>

	<div id="changeConditionBlock">
	  <nedss:container id="chng_cond" name="Change Condition - Select Condition" classType="sect" displayImg="false" includeBackToTopLink="no" displayLink="no">
	    <nedss:container id="subsect_chng_cond" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">
				
		<tr>
		   <td colspan="2">Change the Condition from <b>${PageForm.attributeMap.oldConditionDesc}</b> to: </td>
		</tr>
	        <tr>
	                <td class="InputFieldLabel" id="assignAuCD">
	                <span style="color:#CC0000">*</span>
	                <span style="" >New Condition:</span> 
	                	
	                 <td style="white-space: nowrap" class="InputField">
			     <html:select name="PageForm" property="newConditionCd" styleId ="INV169">
			            <html:optionsCollection property="conditionFamilyList" value="key" label="value"/>
		             </html:select>
	                </td>
	        </tr>
		</span>     
              </nedss:container>
	  </nedss:container>
	     <!-- Bottom button bar -->    
	  <div id="popupButtonBottom" class="grayButtonBar" style="text-align:right; margin-top:5px;">
	      	<input type="button" class="Button" value="Submit" onclick="changeToRelatedConditionConfirm()"/>
	      	<input type="button" class="Button" value="Cancel" onclick="closeChangeConditionDialogBox()"/>
	</div>
	</div>
	
			<! --------------------------------------------------------------------- ->
			<! -- Confirmation Text follows - is hidden until the selection is made. ->
			<! --------------------------------------------------------------------- ->
	    <div style="padding:0.5em 0em;">	
		<span>
		
		
		<table role="presentation" style="display:none" id="confirmationText">	
		
		
      	
		<tr><td COLSPAN=2 id="youHaveIndicated"><p style="margin-left:10px;">You have indicated that you would like to change the condition associated with <b>${PageForm.attributeMap.caseLocalId}</b> from <b>${PageForm.attributeMap.oldConditionDesc}</b> to 
		<b>newDesc</b>. Once the condition is changed, the following will occur:</p></td></tr>
		
		<tr><td COLSPAN=2></td></tr>
		<tr><td COLSPAN=2></td></tr>
		
		<tr>
		<td id="quesCarriedOver" COLSPAN="2"><ul><li>Data that has been entered ${PageForm.attributeMap.oldConditionDesc} will be carried over if the
		  questions are also on the newDesc investigation. If the question is not on
		  the newDesc investigation, then the data will not be carried over.</li></ul></td></tr>  
		<tr>
		<td><ul><li>If previously entered, Case Status will not be carried over. Please review and update case status based on the case definition associated with the new condition.</li></ul></td></tr>
		<tr><td id="anyAssoc"><ul><li>Any associated event records (e.g., lab reports, morbidity reports, treatments, etc.) will remain associated 
		  with the newDesc investigation.</li></ul></td></tr>
		<tr><td><ul><li>Contact tracing links to contact records/associated investigations will be maintained, but please review contacts to ensure changes that may be required to those records are made.</li></ul></td></tr>  
		
		 
		
		
		<tr><td id="notificationCheck" COLSPAN=2>
		<logic:equal name="PageForm" property="attributeMap.NotificationExists" value="true">
		<ul><li><font color="#CC0000">A notification has already been sent to the CDC for ${PageForm.attributeMap.oldConditionDesc}. An updated message will be sent to the CDC indicating the change in condition to newDesc. Please review the case status and update notification comments accordingly.</font></li></ul>
		</logic:equal>
		</td></tr>
		<br />
 		
      	
	        <tr><td COLSPAN=2><p style="margin-left:10px;">Select OK to continue or Cancel to return to View Investigation without changing the condition.</p></td></tr>
	        <tr><td  COLSPAN=2>&nbsp;</td></tr>
	        <tr>
		    	<td align="right" colspan="2">
		    		<input type="button" class="Button" value="   OK   " onclick="changeToRelatedCondition()"/>
		    		<input type="button" class="Button" value="Cancel" onclick="closeChangeConditionDialogBox()"/>
		</td></tr> 
      		</table>
      	
    </html:form>
</body>	
</html>