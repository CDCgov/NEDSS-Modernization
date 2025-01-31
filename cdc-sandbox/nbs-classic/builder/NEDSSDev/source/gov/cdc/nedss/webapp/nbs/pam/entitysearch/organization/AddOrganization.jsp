<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO"%>
<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT"%>
<%@ page import="gov.cdc.nedss.entity.organization.vo.OrganizationVO"%>
<%@ page import="gov.cdc.nedss.entity.organization.dt.OrganizationNameDT"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.form.organization.EntitySearchAddOrganizationForm"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ include file="/jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<%@ page isELIgnored ="false" %>

<html lang="en">
    <head>
    <base target="_self">
		<title>${addOrganizationForm.pageTitle}</title>
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPamForm.js"></SCRIPT>
		<script type="text/javaScript" src="alertAdmin.js"> </script>
		<script type="text/javascript">
	        function getDWRCounties(state)
	        { 
	          var state1 = state.value;
	          JPamForm.getDwrCountiesForState(state1, function(data) {
	          DWRUtil.removeAllOptions("NPP023");
	          DWRUtil.addOptions("NPP023", data, "key", "value" );
	           // dwr.util.setValue("name", data);
	            });
	        }
	        
			function isEmpty(pString)
			{
			    if(pString == "")
			    {
			        return true;
			    }
			    return false;
			}
			function trim(str){
				  while (str.charAt(0) == " "){
				    // remove leading spaces
				    str = str.substring(1);
				  }
				  while (str.charAt(str.length - 1) == " "){
				    // remove trailing spaces
				    str = str.substring(0,str.length - 1);
				  } return str;
				}


			function isNumber(field) {
				var re = /^[0-9-'.'-',']*$/;
				if (!re.test(field.value)) {
				field.value = field.value.replace(/[^0-9-'.'-',']/g,"");
				}
			}

		    function addEntityOrganization(id)
			{
	            var qcEntered = false;
	            var zipEntered = false;
	            var phEntered = false;
	            var mailEntered = false;
				var reqField = false;
	            var optional = false;
				var errorText="";
				var isError = false;
				var errorMsgArray = new Array(); 

				getElementByIdOrByName("LNM").style.color="";

				getElementByIdOrByName("QC").style.color="";
				getElementByIdOrByName("TELPH").style.color="";
				 getElementByIdOrByName("ZP").style.color="";
				 getElementByIdOrByName("EMAIL").style.color="";

				

				if(getElementByIdOrByName("name") != null && isEmpty(getElementByIdOrByName("name").value) == false) reqField = true;
				if(getElementByIdOrByName("quickCode")!=null && isEmpty(getElementByIdOrByName("quickCode").value) == false) qcEntered = true;
				if(getElementByIdOrByName("zip")!=null && isEmpty(getElementByIdOrByName("zip").value) == false) zipEntered = true;
				if(getElementByIdOrByName("telephone")!=null && isEmpty(getElementByIdOrByName("telephone").value) == false) phEntered = true;
				if(getElementByIdOrByName("EMail")!=null && isEmpty(getElementByIdOrByName("EMail").value) == false) mailEntered = true;
				  
					if(reqField == false )
				       {
						errorText="Please enter Organization name and try again.\n";
						errorMsgArray.push(errorText);
				        displayGlobalErrorMessage(errorMsgArray);
						isError = true;
				        getElementByIdOrByName("LNM").style.color="#CC0000";
				        return false;
				       }

				       if(qcEntered == true && reqField == true){
				        var msg = validateQuickCode(getElementByIdOrByName("quickCode"));
				         if(msg) {
				        	 errorMsgArray.push(msg);
						     displayGlobalErrorMessage(errorMsgArray);
					         isError = true;
					         getElementByIdOrByName("QC").style.color="#CC0000";
				         }

				       }

				       if(zipEntered == true && reqField == true){

				    	   var zipMsg = validateZipCode(getElementByIdOrByName("zip"));
					         if(zipMsg) {
				        	 errorMsgArray.push(zipMsg);
						     displayGlobalErrorMessage(errorMsgArray);
					         isError = true;
					         getElementByIdOrByName("ZP").style.color="#CC0000";
				         }
				       }
				       if(phEntered == true && reqField == true ){
							var ph = getElementByIdOrByName("telephone");
							var phMsg = "Telephone: must be a numeric entry in the format of nnn-nnn-nnnn.\n \t\tPlease correct the data and try again. \n";
							   if(ph.value.length< 12 )
					           {
						
							    errorMsgArray.push(phMsg);
							    displayGlobalErrorMessage(errorMsgArray);
								isError = true;
								getElementByIdOrByName("TELPH").style.color="#CC0000";

					           }
					       }
				       
				       
				       if(mailEntered == true && reqField == true){

					        var eMsg = emailErrorMessage();
							 if(eMsg)
					           {
								errorMsgArray.push(eMsg);
								displayGlobalErrorMessage(errorMsgArray);
								isError = true;
								getElementByIdOrByName("EMAIL").style.color="#CC0000";

					           }
					       }
	                     
						   if(errorMsgArray!=null && errorMsgArray.length > 0){
						         
								  displayGlobalErrorMessage(errorMsgArray);
						   }

				       if(reqField == true && isError==false){
				   
				        document.forms[0].action ="/nbs/PamAddOrganization.do?method=submitOrganization&identifier="+id;
				          document.forms[0].submit();
				       }

				       return false;
                }
                
			     function validateQuickCode(field) 
			     {
				     var valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-"
				     var temp;
				     var msg1 = "";

				     if(field !=null && field.value.length <=10){
						
				      for (var i=0; i<field.value.length; i++) {
				       temp = "" + field.value.substring(i, i+1);

				        if (valid.indexOf(temp) == -1){
									msg1 = "Quick Code  must consist of the following characters: Alphabetic characters from 'A' through 'Z'; \n\t\t numeric characters '0' through '9'; or hyphen '-'.  Please correct the data and try again.\n";
				        			return msg1;
				       				 }
				         } //for close tag
				     	}//if close
				     else
				     {
						msg1 = "Quick Code  must consist of the following characters: Alphabetic characters from 'A' through 'Z'; \n \t \t numeric characters '0' through '9'; or hyphen '-'.  Please correct the data and try again.\n";
				        return msg1;
				     }

				     
			    }
			    
			 function validateZipCode(field)
			 {
				 if (field !=null && field.value.length!=5 && field.value.length!=10) {
				  var msg2 = "Zip: Must be a numeric entry in the format of nnnnn or nnnnn-nnnn.\n";
				  return msg2;
				 }
				 
			}
			 
			 function emailErrorMessage()
			 {
				 var errorMsg="";
				 
				 if((trim(document.addOrganizationForm.EMail.value)) != "" && (isEmpty(document.addOrganizationForm.EMail.value) == false)){
					var msg = emailCheck(document.addOrganizationForm.EMail.value);
					   if (!msg)
						 {
							
						   errorMsg ="Email: Please enter valid  "+ "Email Address .\n";
							return errorMsg;
						 }
			  }
		
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
    <head>
    <body class="popup" onload="autocompTxtValuesForJSP();addRolePresentationToTabsAndSections();">
        <!-- Page title -->
        <div class="popupTitle">
            Add Organization
        </div>
        
        <!-- Top button bar -->
        
        <div class="popupButtonBar" style="margin-bottom:4px;">
            <input type="button"  value="Submit" id="" onclick="return addEntityOrganization('${fn:escapeXml(identifier)}');"/>
            <input type="button"  value="Cancel" id="" onclick="closePopup();"/>
        </div>
        
        <!-- Page Errors -->
        <%@ include file="../../../../jsp/feedbackMessagesBar.jsp" %>

		<!-- Entry form -->
        <div style="width:100%; text-align:center;">
            <div style="width:98%;">
                <div style="text-align:right; width:100%;"> 
                    <span class="boldTenRed"> * </span>
                    <span class="boldTenBlack"> Indicates a Required Field </span>  
                </div>

                <html:form>
                    <nedss:container id="section1" name="Add organization" classType="sect" displayImg="false" includeBackToTopLink="no">
                        <nedss:container id="subSec1" name="Name" classType="subSect" defaultDisplay="expand">
							   <tr>										
                                    <td class="fieldName" id="QC"> Quick Code: </td>
                                    <td><html:text name="addOrganizationForm" property="quickCode" maxlength="10" size="10"/> </td>
                                </tr>
                                <tr> 
                                    <td class="fieldName" id="LNM"><span style="color:#CC0000;">* </span> Organization Name: </td>
                                    <td >
                                        <html:text name="addOrganizationForm" property="name" maxlength="48"/>
                                    </td>
                                </tr>
                        </nedss:container>	 
                        <nedss:container id="subSec2" name="Address" classType="subSect" defaultDisplay="expand">
                            <tr>
                                <td class="fieldName" >Street Address 1: </td>
                                <td ><html:text name="addOrganizationForm" property="streetAddress1" maxlength="100"/></td>
                            </tr>
                            <tr>
                                <td class="fieldName">Street Address 2: </td>
                                <td ><html:text name="addOrganizationForm" property="streetAddress2" maxlength="100"/></td>
                            </tr>
                            <tr> 
                                <td class="fieldName">City: </td>
                                <td><html:text name="addOrganizationForm" property="city" maxlength="100"/></td>
                            </tr>
                            <tr> 
                                <td class="fieldName">State: </td>
                                <td>
                                    <html:select name="addOrganizationForm" property="state" onchange="getDWRCounties(this);" styleId="NPP021">
                                        <html:optionsCollection property="stateList" value="key" label="value"/>
                                    </html:select>
                                </td>
                            </tr>
                            <tr> 
                                <td class="fieldName" id="ZP">Zip: </td>
                                <td ><html:text name="addOrganizationForm" property="zip" onkeyup="ZipMask(this,event)"/></td> 
                            </tr>
                            <tr> 
                                <td class="fieldName">County: </td>
                                <td>
 								   <html:select name="addOrganizationForm" property="county" styleId="NPP023">
  							           <html:option value="" />
  							           <html:optionsCollection property="dwrCounties" value="key" label="value"/>
                                    </html:select>
                                </td> 
                            </tr>
                            <tr> 
                                <td class="fieldName">Country: </td>
  							   <td>
                                    <html:select name="addOrganizationForm" property="country"  styleId="DEM167">
                                        <html:option value="" />
                                        <html:optionsCollection property="countryList" value="key" label="value"/>
                                    </html:select>
                                </td> 
                            </tr>
                        </nedss:container>
                        	 
                        <nedss:container id="subSec3" name="Contact Information" classType="subSect" defaultDisplay="expand">
                            <tr> 
                                <td class="fieldName" id="TELPH">Telephone: </td>
 						        <td ><html:text name="addOrganizationForm" property="telephone" maxlength="20" onkeyup="TeleMask(this,event)"/> </td> 
                            </tr>
                            <tr> 
                                <td class="fieldName">Ext: </td>
                                <td ><html:text name="addOrganizationForm" property="ext" maxlength="10" size="10" onkeyup="isNumber(this)" /> </td> 
                            </tr>
                            <tr>
                                <td class="fieldName" id="EMAIL">Email: </td>
                                <td><html:text name="addOrganizationForm" property="EMail" maxlength="48"/> </td>
                            </tr>
                        </nedss:container>	 
                    </nedss:container>
                </html:form>
            </div>
        </div>
        
        <!-- Bottom button bar -->
        <div class="popupButtonBar" style="margin-top:4px;">
            <input type="button"  value="Submit" id="" onclick="return addEntityOrganization('${fn:escapeXml(identifier)}');"/>
            <input type="button"  value="Cancel" id="" onclick="closePopup();"/>
        </div>
    </body>
</html>