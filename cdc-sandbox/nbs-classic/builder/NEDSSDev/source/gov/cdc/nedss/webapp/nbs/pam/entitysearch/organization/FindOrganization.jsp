<%@ include file="/jsp/tags.jsp" %>
<%@ page import="java.util.*" %>
<%@ page isELIgnored ="false" %>   
<html lang="en">
	<head>
	<base target="_self">
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>Organization Search Criteria</title>
		<%@ include file="/jsp/resources.jsp" %>
		
	    <script type="text/javaScript" >
			function isEmpty(pString)
			{
			    if(pString == "")
			    {
			        return true;
			    }
			    return false;
			}
			function focusOnFirstBodyElement()
	    	{
	    		
	    	//	$j(".searchTable").find(':input:visible:enabled:first').focus();
	    	$j("#lastNameTxt").focus();

    		}
    	    function SubmitOnEnter()
			{
			if(document.activeElement.name =="Cancel"){
				closePopup();
			}else{
					
				var org = '${fn:escapeXml(identifier)}';
					if(turnOffParentSubmit==false){
						var key=event.keyCode

						if (key==13) {
							var form = getElementByIdOrByName("nedssForm");
							if (form == null)
								return false; //only do shortcut for nedssForm
							findOrganization(org);
						}
					}
					turnOffParentSubmit=false;
					
				}
			}
			   
            function findOrganization(id)
            {
                var errors = new Array();
				var atLeastOneSearchCriteria = false;
				var errorText=""; 
				var isError = false;
				
				if(getElementByIdOrByName("organizationSearch.nmTxt") != null && isEmpty(getElementByIdOrByName("organizationSearch.nmTxt").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.streetAddr1")!=null && isEmpty(getElementByIdOrByName("organizationSearch.streetAddr1").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.cityDescTxt")!=null && isEmpty(getElementByIdOrByName("organizationSearch.cityDescTxt").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.stateCd")!=null && isEmpty(getElementByIdOrByName("organizationSearch.stateCd").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.zipCd")!=null && isEmpty(getElementByIdOrByName("organizationSearch.zipCd").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.phoneNbrTxt")!=null && isEmpty(getElementByIdOrByName("organizationSearch.phoneNbrTxt").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.rootExtensionTxt")!=null && isEmpty(getElementByIdOrByName("organizationSearch.rootExtensionTxt").value) == false) atLeastOneSearchCriteria = true;
				if(getElementByIdOrByName("organizationSearch.typeCd")!=null && isEmpty(getElementByIdOrByName("organizationSearch.typeCd").value) == false) atLeastOneSearchCriteria = true;
				
				if( atLeastOneSearchCriteria == false ) 
				{
					errorText = "Please enter at least one item to search on and try again.";
					errors.push(errorText);
					isError = true;
				 }
				 			
				//Zip validation
				var msg = validateZIP(getElementByIdOrByName("organizationSearch.zipCd").value);
				if(msg.length > 0) {
				    errors.push(msg);
					isError = true;
					getElementByIdOrByName("zipCodeLabel").style.color="#CC0000";	
				}
				else {
                    getElementByIdOrByName("zipCodeLabel").style.color="#000";
                }

				if(atLeastOneSearchCriteria==true)
				{
				    var ph=getElementByIdOrByName("organizationSearch.phoneNbrTxt");
					if( ph.value.length< 12 && ph.value!=null && ph.value!="")
					{
						var phMsg = "Telephone must be a numeric entry in the format of nnn-nnn-nnnn.";
                        errors.push(phMsg);    
						isError = true;
						getElementByIdOrByName("phoneNumberLabel").style.color="#C00";
				     }
				     else {
                        getElementByIdOrByName("phoneNumberLabel").style.color="#000";
                     }
                }
                else {
                    getElementByIdOrByName("phoneNumberLabel").style.color="#000";
                }

				//ID Type, ID Value Validation	
				var idType = getElementByIdOrByName("organizationSearch.typeCd");
				var idValue = getElementByIdOrByName("organizationSearch.rootExtensionTxt");
				if( idType.value.length == 0 && idValue.value.length > 0) {
                    errorText = "ID Type is required if ID value is not blank";
                    errors.push(errorText);
                    isError = true;
                    getElementByIdOrByName("idTypeLabel").style.color="#C00";
                }
                else {
                    getElementByIdOrByName("idTypeLabel").style.color="#000";
                }
                
                if(idType.value.length > 0 && idValue.value.length == 0) {
                    errorText = "ID Value is required if ID Type is selected";
                    errors.push(errorText);              
                    isError = true;
                    getElementByIdOrByName("idValueLabel").style.color="#C00";
                } 
                else {
                    getElementByIdOrByName("idValueLabel").style.color="#000";
                }
				
				// if no errors submit the form	
				if( atLeastOneSearchCriteria == true && isError == false) {
					document.forms[0].action ="/nbs/PamOrganization.do?method=searchSubmit&identifier="+id;
			  		document.forms[0].submit();
			  		return true;
			  	}
			  	
			  	// display the errors in the error messages bar.
                if(errors != null && errors.length > 0){
                    displayGlobalErrorMessage(errors);
                }
				return false;
            }
			
			function validateZIP(field) {
				var valid = "0123456789-";
				var hyphencount = 0;
				if(field.length == 0) return "";
				if (field.length!=5 && field.length!=10) {
					msg = "Zip code should be either 5 digits or 5 digits followed by an hyphen and 4 digits. Example : 12345 or 12345-6789\n";
					return msg;
				}
				for (var i=0; i < field.length; i++) {
					temp = "" + field.substring(i, i+1);
					if (temp == "-") hyphencount++;
					if (valid.indexOf(temp) == "-1") {
						msg = "Zip code contains invalid characters.";
						return msg;
					}
					if ((hyphencount > 1) || ((field.length==10) && ""+field.charAt(5)!="-")) {
					msg = "The hyphen character should be used with a properly formatted 5 digit+four zip code, like '12345-6789'.";
					return msg;
				   }
				}
				return "";
			}	
		 function closePopup()
	           {              
	                   self.close();
	                   var opener = getDialogArgument();      
	                   var invest = getElementByIdOrByNameNode("pamview", opener.document);
	                   if (invest != null) {
	                        invest.style.display = "none";  
	                   }                   
	               
	           }  		
		</script>		
	</head>
	
	<body class="popup" onload="autocompTxtValuesForJSP();focusOnFirstBodyElement();addRolePresentationToTabsAndSections();" onkeypress="SubmitOnEnter();" style="overflow:hidden">
	   <!-- Page title -->
        <div class="popupTitle">
            Search for Existing Organization
        </div>
        
        <!-- Top button bar -->
        <%String queId = (String) request.getAttribute("identifier");%>
        <div class="popupButtonBar">
            <input type="submit" name="Submit" value="Submit" onclick="return findOrganization('${fn:escapeXml(identifier)}');"/>
            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
        </div>
        
        <!-- error -->
		<div id="globalFeedbackMessagesBar" class="screenOnly">
		    <logic:messagesPresent name="error_messages">
		        <div class="infoBox errors" id="errorMessages">
		            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
		            <ul>
		                <html:messages id="msg" name="error_messages">
		                    <li> <bean:write name="msg" /> </li>
		                </html:messages>
		            <ul>
		        </div>
		    </logic:messagesPresent>
		</div>    
        
		<html:form  styleId="nedssForm">
            <!-- Search Table -->
            <table role="presentation" class="searchTable">
                <thead>
                    <tr>
                        <td> &nbsp; </td>
                        <th> Operators </th>
                        <th style="text-align:left;"> Search Criteria </th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Name -->
                    <tr>
                        <td class="fieldName"> Name: </td>
						<td class="conditionControl" style="white-space:nowrap"> 
                            <html:select title="Name operator" name="organizationSearchForm" property="organizationSearch.nmTxtOperator" 
                                    styleId="lnoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_SNDX)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td>
                            <html:text title = "Name" styleId ="lastNameTxt" name="organizationSearchForm" property="organizationSearch.nmTxt"/>
                        </td> 
                       
                    </tr>
                    
                   <!-- Street Address -->
                    <tr>
                        <td class="fieldName"> Street Address: </td>
                        <td class="conditionControl"> 
                            <html:select title = "Street Address operator" name="organizationSearchForm" property="organizationSearch.streetAddr1Operator" 
                                    styleId="saoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_ALPHA)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td>
                            <html:text title = "Street Address" name="organizationSearchForm" property="organizationSearch.streetAddr1"/>
                        </td> 
                    </tr>
                    
                    <!-- City -->
                    <tr>
                        <td class="fieldName"> City: </td>
                        <td class="conditionControl"> 
                            <html:select title = "City operator" name="organizationSearchForm" property="organizationSearch.cityDescTxtOperator"  
                                    styleId="cdoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_ALPHA)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td>
                            <html:text title = "City" name="organizationSearchForm" property="organizationSearch.cityDescTxt"/>
                        </td> 
                    </tr>
                    
				    <!-- State -->
                    <tr>
                        <td class="fieldName"> State: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td style="white-space:nowrap">
                            <html:select title = "State" name="organizationSearchForm" property="organizationSearch.stateCd"  styleId="stoperator">
                                <html:optionsCollection property="stateList" value="key" label="value"/>
                            </html:select>
                        </td> 
                    </tr>
                    
                    <!-- Zip -->
                    <tr>
                        <td class="fieldName" id="zipCodeLabel"> Zip: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td>
                            <html:text title = "Zip" name="organizationSearchForm" property="organizationSearch.zipCd"/>
                        </td> 
                    </tr>
                    
                    <!-- Telephone -->
                    <tr>
                        <td class="fieldName" id="phoneNumberLabel"> Telephone: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td>
                            <html:text title = "Telephone" name="organizationSearchForm" property="organizationSearch.phoneNbrTxt" onkeyup="TeleMask(this,event)"/>
                        </td> 
                    </tr>
                    
                    <!-- ID Type -->
                    <tr>
                        <td class="fieldName" id="idTypeLabel"> ID Type: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td style="white-space:nowrap">
                            <html:select title = "ID Type" name="organizationSearchForm" property="organizationSearch.typeCd"  styleId="tcoperator">
                                <html:optionsCollection property="codedValue(EI_TYPE_PRV)" value="key" label="value"/>
                            </html:select>
                        </td> 
                    </tr>
                    
                    <!-- Value -->
                    <tr>
                        <td class="fieldName" id="idValueLabel"> Value: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td>
                            <html:text title = "Value" name="organizationSearchForm" property="organizationSearch.rootExtensionTxt"/>
                        </td> 
                    </tr>
                </tbody>
            </table>
        </html:form>
		        
        <!-- Bottom button bar -->    
        <div class="popupButtonBar">
            <input type="submit" name="Submit" value="Submit" onclick="return findOrganization('${fn:escapeXml(identifier)}');"/>
            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
        </div>    
    </body>
</html>