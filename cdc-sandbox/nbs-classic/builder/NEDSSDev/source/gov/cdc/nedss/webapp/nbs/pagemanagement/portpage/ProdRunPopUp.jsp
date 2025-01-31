<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page import = "gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil"%>
<html lang="en">
<head>
<title>NBS: Manage Pages : Port Condition</title>
<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
<script type='text/javascript' src='/nbs/dwr/util.js'></script>
<script src="/nbs/dwr/interface/JPortPage.js" type="text/javascript"></script>
<%@ include file="../../jsp/resources.jsp"%>
<script type="text/javascript" src="PortPageSpecific.js"></script>
<script language="JavaScript">  

function runConv(){
	var numberOfCasesToMigrate ='${fn:escapeXml(numberOfCasesToMigrate)}';
	var opener = getDialogArgument();
	opener.document.forms[0].action="/nbs/PortPage.do?method=submitProductionRun&initLoad=true&numberOfCasesToMigrate="+numberOfCasesToMigrate;
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

function tabBrowsing(){
	getElementByIdOrByName("cancelConv").tabIndex = 1;
	getElementByIdOrByName("submitConv").tabIndex = 2;
}
	</script>

</head>

<body onunload="closePopup()" onload="tabBrowsing()">
<html:form action="/PortPage.do">
   <div style="text-align: left;padding-left:8px; padding-top:20px;">
	   <logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
	       <p>You have indicated that you would like to run the conversion process for porting the condition <b>${selectedCondition}</b> from its current 
	          page (<b>${fromPageName}</b>) to the new page (<b>${toPageName}</b>) using the current map (<b>${mapName}</b>). Once at least one case
	          is migrated the following actions <b>cannot</b> be taken until all cases are successfully migrated to the new condition:</p>
	          </br>
	          <ul style="color:red;">
	             <li>
	             The From Page and To Page should not be accessed/edited from within the Page Management module while the conversion is in progress.
	             </li>
	             <li>
	             Investigations for the condition being ported should not be accessed/edited from the user interface while the conversion is in progress.
	             </li>
	             <li>
	             New investigations for the condition being ported should not be created from the user interface while the conversion is in progress.
	             </li>
	             <li>
	             The condition will no longer be available for mappings utilizing the same From Page.
	             </li>
	             <li>
	             If the current map is still editable, the map will be locked and will no longer be editable. 
	             </li>
	             <li>
	             Once ported, the data for this condition will appear in the data mart defined for the new page and will no longer appear in the data mart defined for the old page. If there are no remaining conditions mapped to the old page, then a data mart for the old page will no longer exist in the reporting database. These updates will be applied the next time the ETL process is run.
	             </li>
	             </br>
	            If you have not done so already, <span class="boldred">please take a database back-up</span> PRIOR TO starting the conversion process. 
	          </ul>
	          </br>
	          <p>Select OK to continue or Cancel to return to Port Condition without starting the conversion process.</p>
	   </logic:equal>
	   <logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_LEGACY%>">
	       <p>You have indicated that you would like to run the conversion process for porting the legacy page Legacy Vaccination to
	          page builder page (<b>${toPageName}</b>) using the current map (<b>${mapName}</b>). Once at least one record
	          is migrated the following actions <b>cannot</b> be taken until all records are successfully migrated to the new page builder page.</p>
	          </br>
	          <ul style="color:red;">
	             <li>
	             	Page should not be accessed/edited from Legacy module (LDFs) or the Page Builder module while the conversion is in progress.
	             </li>
	             <li>
	             	If the current map is still editable, the map will be locked and will no longer be editable.
	             </li>
	               <li>
	             Once ported, the data for this condition will appear in the data mart defined for the new page and will no longer appear in the data mart defined for the old page. If there are no remaining conditions mapped to the old page, then a data mart for the old page will no longer exist in the reporting database. These updates will be applied the next time the ETL process is run.
	             </li>
	          </ul>
	          </br>
	            If you have not done so already, <span class="boldred">please take a database back-up</span> PRIOR TO starting the conversion process. 
	          </br>
	          <p>Select OK to continue or Cancel to return to Port Legacy Data without starting the conversion process.</p>
	   </logic:equal>
   </div>
   <div style="text-align: right;padding-right:10px;padding-top:5px;">
    	<input type="button" id="submitConv" class="button"  value="OK" onclick="runConv()" />
		<input type="button" id="cancelConv" class="button"  value="Cancel" autofocus onclick="javascript:self.close()" />
   </div>
</html:form>
</body>
</html>