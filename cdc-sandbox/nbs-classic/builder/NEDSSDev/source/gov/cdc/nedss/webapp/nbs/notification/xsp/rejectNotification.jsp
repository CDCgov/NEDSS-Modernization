<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>
<%@ page import="gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*"%>
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<html lang="en">
<head>
    <title>Reject Message</title>
    <style type="text/css">
	    div.infoBox { width:100%; border:1px solid #DDD; padding:3px; margin:0.15em 0.15em 0.5em 0.15em;}
	    div.infoBox ul {list-style-type: none; margin-bottom:2px;}
	    div.infoBox table tr td {padding:0px 3px 0px 3px;}
	    div.infoBox span.label, div.infoBox span.value {font-size:110%;}
	    div.nedssLightYellowBg {background:#FFFFCF; border:1px solid #EFEFC2; font-size:130%;}
	    span.messages, div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:2px 0px 2px 0px; border-color:#7AA6D5; border-style:solid; font-size:110%;}
	    span.errors, div.errors { text-align:left;background:#FFE4E3; color:#000; padding:0.5em; border-width:2px 0px 2px 0px; border-color:#E47E7A; border-style:solid; font-size:110%;}
	    span.success, div.success { background:#E0FFD3;color:#000; padding:0.5em; border-width:2px 0px 2px 0px; border-color:#8AD66A; border-style:solid; font-size:110%;}
    </style>
    <script language="JavaScript">
    function submitPopup()
	    {		
    	var opener = getDialogArgument();
			getElementByIdOrByNameNode("apprRejComments", opener.document).value=getElementByIdOrByName("rejectComments").value;
			var pview = getElementByIdOrByNameNode("urlStore", opener.document);
			var url = pview.value + "&apprRejComments=" + encodeURIComponent(getElementByIdOrByName("rejectComments").value);
			opener.document.forms[0].action= url;
			opener.document.forms[0].submit();
			self.close();
    }
    function closePopup()
	    {
			self.close();
			var opener = getDialogArgument();
			var pview = getElementByIdOrByNameNode("blockparent", opener.document);
			pview.style.display = "none";
			return true;
    }
    function checkMaxLength(sTxtBox) {
		maxlimit = 1000;
		if (sTxtBox.value.length > maxlimit)
		sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}
    
    function displayErrors(containerId, errors)
    {
        if (getElementByIdOrByName(containerId).style.display == "none") {
        	getElementByIdOrByName(containerId).style.display = "block";
        }
        
        if (errors != null && containerId != null && getElementByIdOrByName(containerId) != null)
        {
            var head = "<b> <a name=\"" + containerId + 
                "_errorMessagesHref\" />  Please fix the following errors </b> <br/>";
    	    var msgElt = getElementByIdOrByName(containerId);
    	            
	        var errorsUl = "<ul style=\"margin-top:5px;\"><li style=\"list-style:disc;\">" + errors + "</li></ul>";
	        msgElt.innerHTML = head + errorsUl;
    	    
    	    // shift the focus to the error block
    	    //window.location = "#" + containerId + '_errorMessagesHref';
        }
    }

    function getDialogArgument(){
    	
    	var o = window.dialogArguments;
    	var opener;
    	 
    	if(o==undefined)
    		opener = window.opener;
    	else
    		opener = o.opener;
    	
    	return opener;
    }
    
    /*
    getElementByIdOrByName(): if getElementById return null or undefined, use getElementByName
    */
    function getElementByIdOrByName(name){
    	
    	var element;
    		if(document.getElementById(name)==null || document.getElementById(name)==undefined)
    			element = document.getElementsByName(name)[0];
    		else
    			element = document.getElementById(name);
    		
    		return element;
    }
    	
    function getElementByIdOrByNameNode(name, node){
    	
    	var element;
    		if(node.getElementById(name)==null || node.getElementById(name)==undefined)
    			element = node.getElementsByName(name)[0];
    		else
    			element = node.getElementById(name);
    		
    		return element;
    }
    
	function verify() {
		
		getElementByIdOrByName("errorBlock").style.display="none";
	    
	    var errors = new Array();
	    var index = 0;
	    var isError = false;

	    var rejComments= getElementByIdOrByName("rejectComments");
	   	    
		if( rejComments != null && rejComments.value.length == 0) {
			errors[index++] =  "Reason for Rejection is required";
			getElementByIdOrByName("rej").style.color="#CC0000";
			isError = true;		
		}
		else {
		   getElementByIdOrByName("rej").style.color="black";
		}
	    if(isError) {
			displayErrors("errorBlock", errors);
		}
	    else {
			submitPopup();
		}
		
	}	
		
			
    </script>
 <style type="text/css">
    
    TD.InputFieldLabel-popup
    {
    	font-family: arial;
    	font-size: 10pt;
    	font-weight: bold;
    	text-align: right;
    	padding: 5px 5px 5px 3px;
    	vertical-align: top;
    }
    TD.InputField-popup
    {
    	padding: 5px 5px 5px 3px;
    	vertical-align: top;
    }
    </style>
</head>
<body onunload="closePopup()">
	<form>
		<table role="presentation" style="width:100%;font-family : Geneva, Arial, Helvetica, sans-serif; font-size : 10pt; margin-top:20px;">
			<tr style="background:#FFF;">
		        <td colspan="2">
		            <div class="infoBox errors" style="display:none;" id="errorBlock">
		            </div>
		        </td>
	        </tr>
			<tr>
				<td class="InputFieldLabel-popup">
					<span style="color:#CC0000" >*</span>
					<span style="" id="rej" >Reason for Rejection: </span> 
				</td>
				<td class="InputField-popup">
					<textarea rows="5" cols="40" title="Reason for Rejection" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)" id="rejectComments"></textarea>
				</td>
			</tr>
	        <tr>
		      <td style="padding-right:35px;" align="right" colspan="2">
	            <input type="button" class="Button" value="Submit" onclick="verify();"/>
	            <input type="button" class="Button" value="Cancel" onclick="javascript:self.close()"/>
		      </td>
	    	</tr>
		</table>
	</form>
</body>
</html>



