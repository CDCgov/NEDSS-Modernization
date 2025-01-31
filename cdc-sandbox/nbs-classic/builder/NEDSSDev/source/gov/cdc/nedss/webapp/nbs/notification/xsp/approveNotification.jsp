<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>
<%@ page import="gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*"%>
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<html lang="en">
<head>
    <%
    	request.setAttribute("notificationCode",request.getParameter("notificationCd")); 
    	request.setAttribute("nndmAssociated",request.getParameter("nndAssociated"));
   	 	request.setAttribute("nbsjurisdiction",request.getParameter("jurisdiction"));
    	request.setAttribute("sharedAssocaited",request.getParameter("shareAssocaited"));
    %>
	<% if(request.getParameter("nndAssociated") != null && request.getParameter("nndAssociated").equals("true") && (request.getParameter("shareAssocaited") != null && request.getParameter("shareAssocaited").equals("true"))) {%>
		<title>Pending NND and Share Notification</title>
	<%} %>

	<% if(request.getParameter("nndAssociated") != null && request.getParameter("nndAssociated").equals("true")) {%>
	<title>Pending NND Notification</title>
	<%} %>

	<% if(request.getParameter("shareAssocaited") != null && request.getParameter("shareAssocaited").equals("true")) {%>
	<title>Pending Share Notification</title>
	<%} %>

	<%if(request.getParameter("nndAssociated") != null && request.getParameter("nndAssociated").equals("false") && request.getParameter("notificationCd") != null && request.getParameter("notificationCd").equals("EXP_NOTF") && request.getParameter("shareAssocaited") != null && request.getParameter("shareAssocaited").equals("false")){ %>
	<title>Approve Message</title>
	<%}%>

	<%if(request.getParameter("nndAssociated") != null && request.getParameter("nndAssociated").equals("false")&&
	(request.getParameter("notificationCd") != null && (request.getParameter("notificationCd").equals("SHARE_NOTF") || request.getParameter("notificationCd").equals("NOTF") || request.getParameter("notificationCd").equals("SHARE_NOTF_BATCH"))) ){%>
	<title>Approve Message</title>
	<%}%>

    <script language="JavaScript">
    
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

    function getDialogArgument(){
    	
    	var o = window.dialogArguments;
    	var opener;
    	 
    	if(o==undefined)
    		opener = window.opener;
    	else
    		opener = o.opener;
    	
    	return opener;
    }
    
    function submitPopup(obj)
    {
        var object=obj;
        var opener = getDialogArgument();
		var url;

		if(object == 'approve'){
        opener.getElementByIdOrByName("apprRejComments").value=getElementByIdOrByName("approveCommts").value;
        var pview = opener.getElementByIdOrByName("urlStore");
        url = pview.value + "&apprRejComments=" + encodeURIComponent(getElementByIdOrByName("approveCommts").value);
        }
        if(object == 'nndAssocFalse'){
        opener.getElementByIdOrByName("apprRejComments").value=getElementByIdOrByName("approveCommts1").value;
        var pview = opener.getElementByIdOrByName("urlStore");
         url = pview.value + "&apprRejComments=" + encodeURIComponent(getElementByIdOrByName("approveCommts1").value);
        }
		opener.document.forms[0].action= url;
		opener.document.forms[0].submit();
		self.close();

    }

    function closePopup()
    {
		self.close();
		var opener = getDialogArgument();
		var pview = opener.getElementByIdOrByName("blockparent");
		pview.style.display = "none";
		return true;
    }

    function checkMaxLength(sTxtBox) {
		maxlimit = 200;
		if (sTxtBox.value.length > maxlimit)
		sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}
	function changeContent() {
		var notificationCd='${fn:escapeXml(notificationCode)}';
		var nndAssociated='${fn:escapeXml(nndmAssociated)}';
		var jurisdiction='${fn:escapeXml(nbsjurisdiction)}';
		var shareAssociated='${fn:escapeXml(sharedAssocaited)}';

		if (nndAssociated=='true' && shareAssociated=='true'){
			dialogWidth = '750px;';
			dialogHeight  = '140px;'
			getElementByIdOrByName("allAssocTrue").style.display="block";
		}
		if(nndAssociated=='true' && shareAssociated=='false'){
			dialogWidth = '650px;';
			dialogHeight  = '140px;'
			getElementByIdOrByName("nndAssocTrue").style.display="block";
		}

		if(shareAssociated=='true' && nndAssociated=='false'){
			dialogWidth = '650px;';
			dialogHeight  = '140px;'
			getElementByIdOrByName("shareTrue").style.display="block";
		}

		//approve with message
		if(nndAssociated=='false' && (notificationCd=='EXP_NOTF' || notificationCd=='EXP_NOTF_PHDC') && shareAssociated =='false'){
			getElementByIdOrByName("approveCommts1").value="";
			dialogWidth = '600px;';
			dialogHeight  = '400px;';
			getElementByIdOrByName("nndAssocFalse").style.display="block";
			getElementByIdOrByName("approveCommts1").focus();
		}

		//simple approve
		if(nndAssociated=='false' && (notificationCd=='SHARE_NOTF'|| notificationCd=='SHARE_NOTF_BATCH' || notificationCd=='SHARE_NOTF_PHDC' || notificationCd=='NOTF')){
			getElementByIdOrByName("approve").style.display="block";
			getElementByIdOrByName("approveCommts").focus();
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

<body  onload="changeContent()" onunload="closePopup()">

	<div id="allAssocTrue" style="display:none;" >
		<table role="presentation" style="font-family :Arial; font-size : 10pt;" > <tr><td>&nbsp;&nbsp;</td></tr>
			<tr><td class="fieldName">
			&nbsp;&nbsp;&nbsp;&nbsp;There is currently a pending NND and a pending Share notification for this case. No action can be taken on this transfer <br/>&nbsp;&nbsp;&nbsp;&nbsp;request until the pending NND and the pending Share notification have been given a disposition.</td></tr><tr></tr><tr></tr>
			<tr><td align="right"><input type="button" class="Button" style="width: 80px" value="OK"  onclick="closePopup()"/></td></tr>
		</table>
	</div>


	<div id="nndAssocTrue" style="display:none;" >
		<table role="presentation" style="font-family :Arial; font-size : 10pt;" > <tr><td>&nbsp;&nbsp;</td></tr>
			<tr><td class="fieldName">
			&nbsp;&nbsp;&nbsp;&nbsp;There is currently a pending NND notification for this case. No action can be taken on this transfer <br/>&nbsp;&nbsp;&nbsp;&nbsp;request until the pending NND notification has been given a disposition.</td></tr><tr></tr><tr></tr>
			<tr><td align="right"><input type="button" class="Button" style="width: 80px" value="OK"  onclick="closePopup()"/></td></tr>
		</table>
	</div>

	<div id="approve" style="display:none; width:600px; height:300px;">
		<table role="presentation" cellspacing="15" cellpadding="20" style="font-family : Geneva, Arial, Helvetica, sans-serif; font-size : 10pt;">
			<tr>
				<td class="InputFieldLabel-popup" nowrap>
					<span style="" >Approval Comments:</span>
				</td>
				<td class="InputField-popup">
					<textarea rows="5" cols="40" title="Approval Comments" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)" id="approveCommts" style="text-align:left">
					</textarea>
				</td>
			</tr>
		  <tr>
		      <td align="right" colspan="2">
		            <input type="button" class="Button" value="Submit" onclick="submitPopup('approve')"/>
		            <input type="button" class="Button" value="Cancel" onclick="javascript:self.close()"/>
		      </td>
		 </tr>
		</table>
	</div>

	<%
	String encodedFilename = request.getParameter("notifRecip");
	String decodedFilename = java.net.URLDecoder.decode(encodedFilename);
	%>


	<div id="nndAssocFalse" style="display:none;" >
		<table role="presentation"  cellpadding="20" style="font-family :Arial; font-size : 10pt;">
			<tr>
				<td> By approving this transfer, this case will no longer be counted in your reporting jurisdiction; the <br/>ownership of this case will be transfered to: <b>${fn:escapeXml(decodedFilename)}</b> <br/><br/>Once transferred, the investigation will be closed, the investigation jurisdiction will be set to <br/>
				<b>${fn:escapeXml(jurisdiction)}</b> and the case status will be set to 'Not A Case'. If there is an existing NND notification for this case, a rescind notification will be sent to the CDC.
				<br/>
				</td>
			</tr>
		</table>
		<table role="presentation" cellspacing="15" cellpadding="20" style="font-family :Arial; font-size : 10pt;">
			<tr>
				<td class="InputFieldLabel-popup" nowrap>
					<span style="" >Approval Comments:</span>
				</td>
				<td class="InputField-popup">
					<textarea rows="5" cols="40" title="Approval Comments" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)" id="approveCommts1">
					</textarea>
				</td>
		  </tr>
		  <tr>
			  <td align="right" colspan="2">
					<input type="button" class="Button" value="Submit" onclick="submitPopup('nndAssocFalse')"/>
					<input type="button" class="Button" value="Cancel" onclick="javascript:self.close()"/>
			  </td>
		  </tr>
		 </table>
	</div>
	<div id="shareTrue"  style="display:none;">
		<table role="presentation" style="font-family :Arial; font-size : 10pt;" > <tr><td>&nbsp;&nbsp;</td></tr>
			<tr><td>
			&nbsp;&nbsp;&nbsp;&nbsp;There is currently a pending Share notification for this case. No action can be taken on this transfer <br/>&nbsp;&nbsp;&nbsp;&nbsp;request until the pending Share notification has been given a disposition.</td></tr><tr></tr><tr></tr>
			<tr><td align="right"><input type="button" class="Button" style="width: 80px" value="OK"  onclick="closePopup()"/></td></tr>
		</table>
	</div>

	</body>
	</html>

