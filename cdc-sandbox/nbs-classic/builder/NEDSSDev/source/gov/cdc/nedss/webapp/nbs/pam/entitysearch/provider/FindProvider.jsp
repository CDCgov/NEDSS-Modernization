<%@ include file="/jsp/tags.jsp" %>
<%@ page import="java.util.*" %>
<%@ page isELIgnored ="false" %>

<html lang="en">
	<head>
	 <base target="_self">
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>Investigator Search Criteria</title>
		<%@ include file="/jsp/resources.jsp" %>	
	    <SCRIPT LANGUAGE="JavaScript" >
	    	function focusOnFirstBodyElement()
	    	{
	    		
	    	//	$j(".searchTable").find(':input:visible:enabled:first').focus();
	    	$j("#lastNameTxt").focus();

    		}
	    	function SubmitOnEnter()
			{
				var lab = '${fn:escapeXml(identifier)}';
				if(turnOffParentSubmit==false){
					var key=event.keyCode
					if (key==13) {
						if(document.activeElement.name=="Cancel")
			    			closePopup();
						var form = getElementByIdOrByName("nedssForm");
						if (form == null)
							return false; //only do shortcut for nedssForm
						findProvider(lab);
					}
				}
				turnOffParentSubmit=false;
			}
			function isEmpty(pString)
			{
			    if(pString == "")
			    {
			        return true;
			    }
			    return false;
			}	    
			
			function findProvider(id)
			{
                var atLeastOneSearchCriteria = false;
                var errorTD = getElementByIdOrByName("error1");
                errorTD.setAttribute("className", "none");
                var errorText=""; 
                var isError = false;
                var errorMsgArray = new Array(); 
               
					
                if(getElementByIdOrByName("providerSearch.lastName") != null && isEmpty(getElementByIdOrByName("providerSearch.lastName").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.firstName")!=null && isEmpty(getElementByIdOrByName("providerSearch.firstName").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.streetAddr1")!=null && isEmpty(getElementByIdOrByName("providerSearch.streetAddr1").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.cityDescTxt")!=null && isEmpty(getElementByIdOrByName("providerSearch.cityDescTxt").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.state")!=null && isEmpty(getElementByIdOrByName("providerSearch.state").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.zipCd")!=null && isEmpty(getElementByIdOrByName("providerSearch.zipCd").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.phoneNbrTxt")!=null && isEmpty(getElementByIdOrByName("providerSearch.phoneNbrTxt").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.rootExtensionTxt")!=null && isEmpty(getElementByIdOrByName("providerSearch.rootExtensionTxt").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("providerSearch.typeCd")!=null && isEmpty(getElementByIdOrByName("providerSearch.typeCd").value) == false) atLeastOneSearchCriteria = true;
					
				if( atLeastOneSearchCriteria == false ) 
				{
					errorText = "Please enter at least one item to search on and try again.\n";
					isError = true;
					errorMsgArray.push(errorText);
				 }
					 			
				//Zip validation
				var msg = validateZIP(getElementByIdOrByName("providerSearch.zipCd").value);
				if(msg.length > 0) {
				    errorMsgArray.push(msg);
					isError = true;	
					getElementByIdOrByName("zipCodeLabel").style.color="#CC0000";
				}
				else {
				    getElementByIdOrByName("zipCodeLabel").style.color="#000";
				}
				
				if(atLeastOneSearchCriteria==true )
				{
					var ph=getElementByIdOrByName("providerSearch.phoneNbrTxt");
					if( ph.value.length< 12 && ph.value!=null && ph.value!="")
					{
						//var phMsg = (" _")+"\t"+"Telephone: must be a numeric entry in the format of nnn-nnn-nnnn.";
						var phMsg = "Telephone must be a numeric entry in the format of nnn-nnn-nnnn.";
						errorMsgArray.push(phMsg);
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
				var idType = getElementByIdOrByName("providerSearch.typeCd");
				var idValue = getElementByIdOrByName("providerSearch.rootExtensionTxt");
				if( idType.value.length == 0 && idValue.value.length > 0) {
					errorText = "ID Type is required if ID value is not blank";
					errorMsgArray.push(errorText);
					isError = true;
					getElementByIdOrByName("idTypeLabel").style.color="#C00";
				}
				else {
				    getElementByIdOrByName("idTypeLabel").style.color="#000";
				}
				
				if(idType.value.length > 0 && idValue.value.length == 0) {
					errorText = "ID Value is required if ID Type is selected";
					errorMsgArray.push(errorText);				
					isError = true;
					getElementByIdOrByName("idValueLabel").style.color="#C00";
				} 
				else {
				    getElementByIdOrByName("idValueLabel").style.color="#000";
				}
				
				if( atLeastOneSearchCriteria == true && isError == false) {
		               
				
					document.forms[0].action ="/nbs/Provider.do?method=searchSubmit&identifier="+id;
					document.forms[0].submit();
			  		return true;
			  	}
			  	
                // display the errors in the error messages bar.
			  	if(errorMsgArray!=null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
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
		</SCRIPT>		
	</head>
	<body class="popup" onload="autocompTxtValuesForJSP();focusOnFirstBodyElement();addRolePresentationToTabsAndSections();" onkeypress="SubmitOnEnter();">
        <!-- Page title -->
        <div class="popupTitle">
            Search for Existing Provider
        </div>
        
        <!-- Top button bar -->
        <%String queId = (String) request.getAttribute("identifier");%>
        <div class="popupButtonBar">
            <input type="submit" name="Submit" value="Submit" onclick="return findProvider('${fn:escapeXml(identifier)}');"/>
            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
        </div>
        
        <!-- error1 (error message block) -->
        <div class="none" id="error1" style="width:100%; text-align:center;"> </div>
        
   <!-- Error Messages using Action Messages-->
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
        <html:form styleId="nedssForm">
            <input id="perTitle" type="hidden" name="perTitle"  value="">
            <input id="ContextAction" type="hidden" name="ContextAction" value="EntitySearch">

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
                    <!-- Last Name -->
                    <tr>
                        <td class="fieldName"> Last Name: </td>
                        <td class="conditionControl" style="white-space:nowrap"> 
                            <html:select title="Last Name operator" name="providerSearchForm" property="providerSearch.lastNameOperator" 
                                    styleId="lnoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_SNDX)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td style="white-space:nowrap">
                            <html:text title="Last Name" styleId ="lastNameTxt" name="providerSearchForm" property="providerSearch.lastName"/>
                        </td> 
                    </tr>
                    <!-- First Name -->
                    <tr>
                        <td class="fieldName"> First Name: </td>
                        <td class="conditionControl"> 
                            <html:select title = "First Name operator" name="providerSearchForm" property="providerSearch.firstNameOperator"  
                                    styleId="fnoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_SNDX)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td >
                            <html:text title = "First Name" name="providerSearchForm" property="providerSearch.firstName"/>
                        </td> 
                    </tr>
                    <!-- Street Address -->
                    <tr>
                        <td class="fieldName"> Street Address: </td>
                        <td class="conditionControl"> 
                            <html:select title = "Street Address operator" name="providerSearchForm" property="providerSearch.streetAddr1Operator"  
                                    styleId="saoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_ALPHA)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td>
                            <html:text title = "Street Address" name="providerSearchForm" property="providerSearch.streetAddr1"/>
                        </td> 
                    </tr>
                    <!-- City -->
                    <tr>
                        <td class="fieldName"> City: </td>
                        <td class="conditionControl"> 
                            <html:select title = "City operator" name="providerSearchForm" property="providerSearch.cityDescTxtOperator"  
                                    styleId="cdoperator">
                                <html:optionsCollection property="codedValueNoBlnk(SEARCH_ALPHA)" value="key" label="value"/>
                            </html:select>
                        </td>
                        <td>
                            <html:text title = "City" name="providerSearchForm" property="providerSearch.cityDescTxt"/>
                        </td> 
                    </tr>
                    <!-- State -->
                    <tr>
                        <td class="fieldName"> State: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td style="white-space:nowrap">
                            <html:select title = "State" name="providerSearchForm" property="providerSearch.state"  styleId="stoperator">
                                <html:optionsCollection property="codedValue(STATE_LIST)" value="key" label="value"/>
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
                            <html:text title = "Zip" name="providerSearchForm" property="providerSearch.zipCd"/>
                        </td> 
                    </tr>
                    <!-- Telephone -->
                    <tr>
                        <td class="fieldName" id="phoneNumberLabel"> Telephone: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td>
                            <html:text title = "Telephone" name="providerSearchForm" property="providerSearch.phoneNbrTxt" onkeyup="TeleMask(this,null,event)"/>
                        </td> 
                    </tr>
                    <!-- ID Type -->
                    <tr>
                        <td class="fieldName" id="idTypeLabel"> ID Type: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td style="white-space:nowrap">
                            <html:select title = "ID Type" name="providerSearchForm" property="providerSearch.typeCd"  styleId="tcoperator">
                                <html:optionsCollection property="codedValue(EI_TYPE_PRV)" value="key" label="value"/>
                            </html:select>
                        </td> 
                    </tr>
                    <!-- Value -->
                    <tr>
                        <td class="fieldName" id="idValueLabel"> ID Value: </td>
                        <td class="conditionControl"> 
                            &nbsp;
                        </td>
                        <td>
                            <html:text title = "ID Value" name="providerSearchForm" property="providerSearch.rootExtensionTxt"/>
                        </td> 
                    </tr>
                </tbody>
            </table>
        </html:form>
        
        <!-- Bottom button bar -->    
        <div class="popupButtonBar">
            <input type="submit" name="Submit" value="Submit" onclick="return findProvider('${fn:escapeXml(identifier)}');"/>
            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
        </div>
    </body>
</html>