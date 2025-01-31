<%@ page isELIgnored ="false" %>
<%@ include file="/jsp/tags.jsp" %>
<%@ page import = "gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.*" %>

<html lang="en">
	<head>		
		<title>Transfer Ownership</title>
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<%@ include file="/jsp/resources.jsp" %>
		<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
		<script type='text/javascript' src='/nbs/dwr/util.js'></script>	
		<script type='text/javascript' src='/nbs/dwr/interface/JNbsDocumentForm.js'></script>	
	
	<script language="JavaScript">
	blockEnterKey();
	
	function tOwnership() {
		var errorTD = getElementByIdOrByName("error1");
		var isError = false;
		var opener = getDialogArgument();
		var newJur = getElementByIdOrByName("Juris").value;
		var newArea = getElementByIdOrByName("abc");
		var progArea = null;
		var errorText=""; 
		var msg="";
		if(newJur == null || newJur == "") {
			errorText += "* Jurisdiction is a Required Field.\n";
			getElementByIdOrByName("docJus").style.color="#CC0000";
			isError = true;	
		}else
		{
			getElementByIdOrByName("docJus").style.color="black";
		}
		if(newArea.type == 'select-one'){
			if(newArea != null && newArea.value.length == 0 ){
			errorText += "* Program Area is a Required Field.\n";
			getElementByIdOrByName("docPro").style.color="#CC0000";
			isError = true;	
			}else
			{
				getElementByIdOrByName("docPro").style.color="black";
			}
		}
		if(isError) {
			errorTD.innerText = msg + errorText;
			errorTD.className = "errorTag";	

	    }
		if(!isError) {
			if(newArea.type == 'select-one')
				progArea = newArea.value;
			opener.document.forms[0].action= "/nbs/LoadViewDoc1.do?method=transferOwnershipSubmit&juris=" + newJur +"&progArea=" + progArea;
			opener.document.forms[0].submit();
			self.close();
			//var invest = getElementByIdOrByNameNode("blockparent", opener.document)
			//invest.style.display = "none";				
			//window.close();
		}
	}
	function docTOwnership(jurisd) {
		
		opener.document.forms[0].action= url;
		opener.document.forms[0].submit();
		self.close();

	    document.forms[0].action ="/nbs/LoadViewDoc1.do?method=transferOwnershipSubmit&juris=" + jurisd;
	    document.forms[0].submit();
	}

	function closeTOwnership() {
		self.close();
		var opener = getDialogArgument();		
		var invest = getElementByIdOrByNameNode("blockParent",opener.document)
		invest.style.display = "none";					
	}

	</script>
	 
	</head>
	<%
	TreeMap objStore = (TreeMap) request.getSession().getAttribute("NBSObjectStore");
	String typeOfAction = request.getParameter("ContextAction"); 
	String localIdObs = (String)request.getParameter("");
	if(localIdObs == null) {
		//localIdObs = (String) objStore.get(NBSConstantUtil.DSObservationLocalID);
	}
	//String jurisdiction =  objStore.get("DSObservationJurisdiction") == null ? "" :  (String) objStore.get("DSObservationJurisdiction");
	//String programArea = objStore.get("DSObservationProgramArea") == null ? "" : (String) objStore.get("DSObservationProgramArea");		
	%>
	<body onload="autocompTxtValuesForJSP();" onunload="closeTOwnership()">		
		<html:form>
			<span>	
				<table role="presentation">
					 <tr>
						<td><table role="presentation">
							<tr><td style="WIDTH: 15px;"></td>
									<td class="none" id="error1"></td>
							</tr>
						</table></td>
				    </tr>
			    </table>		
			  <table role="presentation" cellspacing="15" cellpadding="20" style="font-family : Geneva, Arial, Helvetica, sans-serif; font-size : 10pt;">
			  	  <tr>
			  	  		<td  align="left" colspan="4">
	  	  					Transfer <b>${nbsDocumentForm.attributeMap.DSDocumentLocalID}</b> to: 
			  	  		</td>
			  	  </tr>
				  <tr>
				  	<td>&nbsp;</td>
		               <td class="InputFieldCenter" id="docPro">
		               <span style="color:#CC0000">*</span>
		               <span>Program Area:</span></td>
		               <td class="InputField">
		               		<logic:empty name="nbsDocumentForm" property="attributeMap.oldProgramArea">
				               	<html:select property="summaryDt.programArea" styleId ="abc">
				               		<html:optionsCollection property="programAreaList" value="key" label="value"/>
			                  	</html:select>
		                  	</logic:empty>
		                  	<logic:notEmpty name="nbsDocumentForm" property="attributeMap.oldProgramArea">
		                  		 <span id ="abc">${nbsDocumentForm.attributeMap.oldProgramArea}</span>
		                  	</logic:notEmpty>
		               </td>
		               <td>&nbsp;</td>
		          </tr>
		          <tr>
		          	<td>&nbsp;</td>
		               <td class="InputFieldCenter" id="docJus">
		               <span style="color:#CC0000">*</span>
		               <span>Jurisdiction:</span></td>
		               <td class="InputField">
			               	<html:select title = "Jurisdiction" property="summaryDt.jurisdiction" styleId ="Juris">
			               		<html:optionsCollection property="jurisdictionList" value="key" label="value"/>
		                  	</html:select>
		               </td>
		               <td>&nbsp;</td>
		          </tr>
		          <% if(request.getAttribute("IsInvAsso") != null){%>
		          <tr>
		          	<td colspan="4">
		          		<b>NOTE: This Document is associated with an Investigation. Transferring this Document will not result in the Investigation being transferred.</b>
		          	</td>
		          </tr>
		          <%}%>
			  	  <tr>
			  	  	<td  colspan="3">&nbsp;</td>
				      <td align="left" nowrap>
				            <input type="button" class="Button" value="Submit" onclick="tOwnership()"/>
				            <input type="button" class="Button" value="Cancel" onclick="closeTOwnership()"/>
				      </td>
			     </tr>																				  
			  </table>
		    </span>
	     </html:form>
    </body>	
</html>
