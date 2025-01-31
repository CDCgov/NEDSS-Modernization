<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored="false"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>

<html lang="en">
<head>
<title>NBS: Merge Investigations Confirmation</title>
<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
<script type='text/javascript' src='/nbs/dwr/util.js'></script>

<script language="JavaScript">  

function submitMerge(){
	
    var opener = getDialogArgument();
	opener.document.forms[0].action="/nbs/PageAction.do?method=mergeSubmit&ContextAction=Submit";
    opener.document.forms[0].submit();
    self.close();
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
function closePopup()
{
	self.close();
	/* var opener = getDialogArgument();
	var pview = getElementByIdOrByNameNode("parent", opener.document);
	pview.style.display = "none"; */
	/* return false; */
}


	</script>

</head>

<body onunload="closePopup()" >
<html:form action="/PageAction.do">
   <div style="text-align: left;padding-left:8px; padding-top:20px;">
	  </br>
	     If you continue with the Merge action, the selected investigation records will be merged into a single investigation record.
	         
	          <ul>
	             <li>
	          		All associated event records (lab reports, morbidity reports, vaccinations, treatments, case reports, contact records, and interviews) will move to the surviving investigation. 
	             </li>
	             <li>
	             	All attachments and notes will move to the surviving investigation.
	             </li>
	             <li>
	             	A new 'version' of the surviving investigation will be recorded with the 'last updated by user' as the user who performed the merge and the 'last updated by date' as today's date.
	             </li>
	             <li>
	            	If a notification exists for the surviving investigation, a re-send will be triggered.
	             </li>
	             <li>
	            	The 'losing' investigation will be indicated as 'LOG_DEL' in the system.
	             </li>
	             <li>
	            	If a notification exists for the 'losing' investigation, an updated notification will be triggered with a case status of 'Not a Case'.
	             </li>
	             <li style="color:red;"><b>
	            	Merging of investigations is final and cannot be reversed.</b>
	             </li> 
	          </ul>
	         
	          <p>Select OK to continue or Cancel to not continue.</p>
	
	
   </div>
   <div style="text-align: right;padding-right:10px;padding-top:5px;">
    	<input type="button" id="submitConv" class="button"  value="OK" onclick="submitMerge()" />
		<input type="button" id="cancelConv" class="button"  value="Cancel" autofocus onclick="javascript:self.close()" />
   </div>
</html:form>
</body>
</html>