<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<html lang="en">
	<head>
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>Manage Value Set: Value Set Import</title>
		 <%@ include file="/jsp/resources.jsp" %>
		<script type="text/javascript" src="srtadmin.js"></script>
		<script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js">
        </SCRIPT>
		<script language="JavaScript">
		 function importVads(){
			 var errors = new Array();
             var arrIndex = 0;
             var isMore = false;
             var criteria  = getElementByIdOrByName("vadsSearch");
             if(criteria != null && criteria.value.length == 0)
             {
                 errors[arrIndex++] = "Value sets Available for Import is required";
                 
                 // display the list of errors    
                 displayErrors("errorBlock", errors);
                 
                 getElementByIdOrByName("Vads").style.color="#CC0000";
                 return false;		
             }
             
             var opener = getDialogArgument();
			 var newVads = getElementByIdOrByName("vadsSearch").value;
			 opener.importVadsValue(newVads, isMore);
             
             var page = getElementByIdOrByNameNode("blockparent", opener.document)
 			 page.style.display = "none";				
 			 window.close();
            }
         
		 function closePopup()
		    {
				self.close();
				var opener = getDialogArgument();
				var pview = getElementByIdOrByNameNode("blockparent", opener.document);
				pview.style.display = "none";
				return true;
		    }
		</script>
	</head>
	<body onunload="closePopup()" style="overflow-x: hidden">		
			<div>
				<table role="presentation" cellspacing="15" cellpadding="20" style="font-family : Geneva, Arial, Helvetica, sans-serif; font-size : 10pt;">
					<tr style="background:#FFF;">
			            <td colspan="2">
			                <div class="infoBox errors" style="display:none;" id="errorBlock">
			                </div>
			            </td>
			         </tr>
					<tr style="background:#FFF;">
			           	   <td colspan="2" style="padding-left:10px; padding-top:20px"> Please enter the Value Set Code for the value set that you would like to import. Ensure the Value Set Code is correctly entered, as the import will only retrieve the exact matches. </td>
				    </tr>
				    <tr>
				          <td class="InputFieldLabel" id="Vads">
				          	<span style="color:#CC0000">*</span><span style="" >Value Set Code:</span>
				          </td>
				          <td class="InputField">
				           	<html:text title="Value Set Code" styleId="vadsSearch" name="SRTAdminManageForm"  property="searchCriteria(VADValueSetCd)"  size="50" maxlength="100"/>
				          </td>
				    </tr>
				    <tr>
					      <td  align="right" colspan="2">
					            <input type="button"   class="button" style="height:24px; width: 55px" value="OK" onclick="importVads();"/>
					            <input type="button"  class="button" style="height:24px; width: 55px" value="Cancel" onclick="javascript:self.close()"/>
					      </td>
				     </tr>
				</table>
	     	</div>
	</body>	
</html>
