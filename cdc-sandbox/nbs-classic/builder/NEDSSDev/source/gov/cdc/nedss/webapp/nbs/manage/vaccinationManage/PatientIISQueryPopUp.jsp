<%@ include file="/jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
	<%@ page isELIgnored ="false" %>
	<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
	<%@ page import="gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl" %>
	<%@ page import="javax.servlet.http.HttpServletRequest" %>
	<html lang="en">
	<head>
	<base target="_self">
	<title>Query Immunization Registry</title>
	<script language="JavaScript">
	
		function setSelectedIndex(s, v) {

		    for ( var i = 0; i < s.options.length; i++ ) {

			if ( s.options[i].value == v ) {

			    s.options[i].selected = true;

			    return;
			}
		    }
		}
		
		
		function searchPatient(){
			
		}
		
		function disableEnableBirthOrder(val){
			if(val == 'Y'){
				getElementByIdOrByName("DEM117").disabled=false;
			}else{
				getElementByIdOrByName("DEM117").value="";
				getElementByIdOrByName("DEM117").disabled=true;
			}
		}

		function isNumeric1to10Entered(pTextbox)
		{
		    var varVal = pTextbox.value;
		    var y = 0; var s = ""; var c = "";
		    y = varVal.length;
		    
		    for(x=0; x<y; x++) {
			c = varVal.substr(x, 1);
			if(isInteger(c)) s += c;
			pTextbox.value = s;
		    }
		    
		    var intVal = parseInt(pTextbox.value);
		    
		    if(intVal<=0 || varVal>10){
		    	pTextbox.value = "";
		    }
		}
		
		
		function  validateAndSubmitToIIS() {
					
			var errorText="";
			var allEnabledSearchIpElts = $j("#doc4").find(':input:enabled');
			var atleastOne = false;
			var erroronTab1=false;
			var validDob = true;
			var errorMsgArray = new Array(); 
			for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
			    if ($j(allEnabledSearchIpElts[i]).attr("id") != '' && 
			    $j(allEnabledSearchIpElts[i]).attr("type") != 'checkbox' 
			    && $j(allEnabledSearchIpElts[i]).attr("type") != 'radio' && $j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
			    && $j(allEnabledSearchIpElts[i]).attr("type") != 'button' && jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) !="" ) {
				atleastOne = true;

				 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {
					    if(!(12 >= $j(allEnabledSearchIpElts[i]).attr("value") && $j(allEnabledSearchIpElts[i]).attr("value") >= 1)){
					    	errorText = "Month of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to 12. Please correct the data and try again."; 
							errorMsgArray.push(errorText);
							$j("#patientDOB1").focus();
							getElementByIdOrByName("DEM115L").style.color="#CC0000";
							 erroronTab1= true; 
					    }

					   if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
					  	 $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
					       		errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
							    errorMsgArray.push(errorText);
							    $j("#patientDOB1").focus();
							    getElementByIdOrByName("DEM115L").style.color="#CC0000";
							     erroronTab1= true; 
					    }

				 }

				 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {

					    var month = getElementByIdOrByName("personSearch.birthTimeMonth").value;
						var year = getElementByIdOrByName("personSearch.birthTimeYear").value;
						var days = "";
						var element = $j(allEnabledSearchIpElts[i]).attr("value");

					       if(month!=null && month !="" && year !=null && year !="")
						{
						    days = getDaysWithMonthAndYear(stripOffZero(month),year);
						}

						else if(month!=null && month!=""){
						 days = getDaysWithMonth(stripOffZero(month));
						 }
						else{
						 days = "31";
						 }

						 if(element > 31 || element < 1 )
						{
						    errorText = "Day of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to 31. Please correct the data and try again.";
							errorMsgArray.push(errorText);
							$j("#patientDOB2").focus();
							getElementByIdOrByName("DEM115L").style.color="#CC0000";
							 erroronTab1= true; 
						}
						else if ( days >= element && element >= 1) {
						}else {
						   errorText = "Day of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to "+days+". Please correct the data and try again.";
							errorMsgArray.push(errorText);
							$j("#patientDOB2").focus();
							getElementByIdOrByName("DEM115L").style.color="#CC0000";
							 erroronTab1= true; 
						}



					if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
					   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
					       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
						    errorMsgArray.push(errorText);
						    $j("#patientDOB1").focus();
						    getElementByIdOrByName("DEM115L").style.color="#CC0000";
						     erroronTab1= true; 
					}		
				 }

				 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {

				    var thisYear = new Date().getFullYear();
				    var element = $j(allEnabledSearchIpElts[i]).attr("value");							


						 if (!(thisYear >= element && element >= 1875)) {
						   errorText = "Year of Birth must be greater than 1875 and less than or equal to the current year. Please correct the data and try again.";
						   errorMsgArray.push(errorText);
						   $j("#patientDOB3").focus();
						   getElementByIdOrByName("DEM115L").style.color="#CC0000";
						    erroronTab1= true; 
						}

						if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
						   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
						       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
								    errorMsgArray.push(errorText);
								    $j("#patientDOB1").focus();
								    getElementByIdOrByName("DEM115L").style.color="#CC0000";
								     erroronTab1= true; 
					}	


				 }

				 
				  if(($j(allEnabledSearchIpElts[i]).attr("id") == 'DEM222'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
				     (getElementByIdOrByName("DEM223") != null && getElementByIdOrByName("DEM223").value == "")){
				     errorText = "ID Value must have a value. Please correct the data and try again.";
						   errorMsgArray.push(errorText);
						   $j("#patientDOB2").focus();
						   getElementByIdOrByName("DEM223L").style.color="#CC0000";
						     getElementByIdOrByName("DEM222L").style.color="black";
						      erroronTab1= true; 

				 }

				if($j(allEnabledSearchIpElts[i]).attr("id") == 'DEM115') {
					if (!isDate( $j(allEnabledSearchIpElts[i]).attr("value") ) ) {
						errorText = "Date of Birth must be in the format of mm/dd/yyyy. Please correct the data and try again.";
						errorMsgArray.push(errorText);
						$j("#DEM115").focus();
						getElementByIdOrByName("DEM115L").style.color="#CC0000";

					}
				}
			    }
			}
			
				

		       if(!atleastOne){
				errorText = "Please enter at least one item to search.";
				errorMsgArray.push(errorText);
			}
						          
			/*if(errorMsgArray!=null && errorMsgArray.length > 0){
			    alert(errorText);
			    return false;
			}*/
			
			if(errorMsgArray!=null && errorMsgArray.length > 0){
			    displayGlobalErrorMessage(errorMsgArray);
			    var tabElts = new Array(); 

			    if(erroronTab1){                
			    	//tabElts.push(getElementByIdOrByName("tabs0tab0"));
			    }  
			    setErrorTabProperties(tabElts);  
			    return false;
			}else{
				document.forms[0].action ="/nbs/IISQuery.do?method=searchPatientFromIIS&initLoad=true";
    	        document.forms[0].submit();
    	        return true;
			}
		}
		
		
		
		function closePopup() {
			var confirmMsg="If you continue with the Cancel action, you will not be able to search for patient records in the Immunization Registry and will be returned to the Manage Associations screen. Select OK to continue, or Cancel to not continue. ";
			    if (confirm(confirmMsg)) {
					var opener = getDialogArgument();			
					var pview =getElementByIdOrByNameNode("pageview", opener.document);
					pview .style.display = "none";
					self.close();
					return false;	
			    } else {
					return false;
			}
		}

		function populateForRefineSearch(){
			var birthOrderEle = getElementByIdOrByName("DEM117");
			var birthOrderVal = <%=request.getAttribute("birthOrder")%>;
			var multipleBirthIndEle = getElementByIdOrByName("DEM116");
			if(multipleBirthIndEle!=null && multipleBirthIndEle.value=='Y'){
				disableEnableBirthOrder('Y');
				if(birthOrderVal>0)
					birthOrderEle.value=birthOrderVal;
				else
					birthOrderEle.value="";
			}else{
				disableEnableBirthOrder('N');
			}
		}
	</script>  
	</head>
		
	<body class="popup" onload="autocompTxtValuesForJSP();populateForRefineSearch();">
	
	<div id="doc3">
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
	   <html:form><div id="doc4">
	   	<div style="width:100%; background:#185394; padding:3px; color:#FFF; text-align:left; font-size:110%; font-weight:bold;">Query Immunization Registry</div>
	   	<div class="popupButtonBar">
			<input type="button" name="Submit" value="Submit Query" onclick="return validateAndSubmitToIIS()"/>
			<input type="button" align="right"  value= "Cancel" onclick="closePopup()"/>	
		</div>
	                  <!-- ################# SECTION ################  -->
					<nedss:container id="patSearch1" name="Demographics" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="sect">

					<!-- ########### SUB SECTION ###########  -->
					<nedss:container id="patSearch2" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="subSect" >
					<tr>
					<td width="20%"> &nbsp;</td>
					<td align=""><span id=""><span><b>Search Criteria</b></span></span></td>	

					</tr>
										
			                
					    <tr>
						<td class="fieldName">

						<span class="InputFieldLabel" valign="middle" id="DEM102L" style="" title="Patient's Last Name"> Last Name: </span> &nbsp; </td> 

						
						     
						 <td> <html:text  property="personSearch.lastName" styleId="DEM102" title="The patient's last name"/> </td>                        
					    </tr>
			                    <tr>                                                    
			                        <td class="fieldName">
			                       
			                        <span class="InputFieldLabel" id="DEM104L" style="" title="Patient's First Name">
			                         First Name: </span>&nbsp; </td> 
			                      
			                        
			                         <td> <html:text  property="personSearch.firstName" styleId="DEM104" title="The patient's first name" /> </td>
			                    </tr>
			                    <tr>                                                    
						<td class="fieldName">

						<span class="InputFieldLabel" id="DEM105L" style="" title="The patient's middle name or initial">
						 Middle Name: </span>&nbsp; </td> 

						
						 <td> <html:text  property="personSearch.middleName" styleId="DEM105" title="The patient's middle name" /> </td>
			                    </tr>
			                    <tr>   
			                          <td class="fieldName"> 
			                              
			                           
			                           <span class="InputFieldLabel" id="DEM115L" style="" title="Patient's Date of Birth">Date of Birth:</span>&nbsp;</td> 	                            
											
									  
											<td width="60%" >
											<div id ="fulldate">
											<html:text  property="personSearch.birthTimeMonth" styleId="patientDOB1" size="2" maxlength="2" onkeyup="isNumericCharacterEntered(this);moveFocusParsedInputs(1, this, 'patientDOB',event)" title="For searching month of DOB"/>
											<html:text  property="personSearch.birthTimeDay" styleId="patientDOB2" size="2" maxlength="2" onkeyup="isNumericCharacterEntered(this);moveFocusParsedInputs(2, this, 'patientDOB',event)" title="For searching day of DOB"/>
											<html:text  property="personSearch.birthTimeYear" styleId="patientDOB3" size="4" maxlength="4" onkeyup="isNumericCharacterEntered(this);" title="For searching year of DOB"/>
											
											<!--  <input id="patientDOB1" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeMonth" size="2" maxlength="2" onkeyup="isNumericCharacterEntered(this);" >
											<input id="patientDOB2" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeDay" size="2" maxlength="2" onkeyup="isNumericCharacterEntered(this);" >
											<input id="patientDOB3" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeYear" size="4" maxlength="4" onkeyup="isNumericCharacterEntered(this);" >  -->
											
											</div>
											
											</td>
										    </tr>
											
								
			                    <tr>
			                        <td class="fieldName">
			                           <span class="InputFieldLabel" id="DEM114L" style="" title="Patient's Current Sex"> Current Sex: </span>&nbsp;</td> 
			                       
			                        <td>
					                     <html:select name="iisQueryForm" property="personSearch.currentSex" styleId="DEM114" title="The patient's current sex">
					                        <html:optionsCollection property="codedValue(SEX)" value="key" label="value"/>
					                    </html:select>	                                                    
			                        </td>
			                    </tr>
			                    <tr>
			                        <td class="fieldName"> 
			                           <span class="InputFieldLabel" id="DEM106L" style="" title="Patient's Street Address"> Street Address: </span>&nbsp;</td> 
			                        			                    
				                    <td>
				                    <html:text  property="personSearch.streetAddr1" styleId="DEM106" title="Patient's Street Address" onkeydown="if (SubmitOnEnter()==false) return false;"  size="" maxlength="50" />
				                    	                    
				                    </td>    
			                    </tr>
			                     <tr>
			                        <td class="fieldName">
			                           <span class="InputFieldLabel" id="DEM107L" style="" title="Patient's City"> City: </span>&nbsp; </td> 
			                      
			                      		<td>
				                    <html:text  property="personSearch.cityDescTxt" styleId="DEM107" title="Patient's City" onkeydown="if (SubmitOnEnter()==false) return false;"  size="" maxlength="50"  />
				                    	                    
				                    </td>    
			                    </tr>
			                    <tr>
			                        <td class="fieldName">
			                           <span class="InputFieldLabel" id="DEM108L" style="" title="Patient's State"> State: </span>&nbsp; </td> 
			      
				                    <td>
				                    <html:select name="iisQueryForm" property="personSearch.state" styleId="DEM108O" title="Patient's State">
				                    <html:optionsCollection property="stateList" value="key" label="value" /> 
				                    </html:select>
				                    	                    
				                    </td>    
			                    </tr>
			                    <tr>
			                        <td class="fieldName">
			                           <span class="InputFieldLabel" id="DEM108L" style="" title="Patient's Zip"> Zip: </span>&nbsp;</td> 
			               
			               			<td>
				                    <html:text  property="personSearch.zipCd" styleId="DEM109" title="Patient's Zip Code" onkeydown="if (SubmitOnEnter()==false) return false;"  size="" maxlength="50"  onkeyup="ZipMask(this,event)" />
				                    	                    
				                    </td>    
			                    </tr>
			                    <tr>
						<td class="fieldName" width="20%"> 
						   <span class="InputFieldLabel" id="DEM238L" style="" title="Patient's Telephone">Phone: </span>&nbsp; 
						</td> 

						<td>
							<html:text  property="personSearch.phoneNbrTxt" styleId="DEM238" maxlength="50" onkeyup="TeleMask(this,event)" title="Any associated patient phone number (e.g., home, work, cell)"  />
						</td>  					    				                     
			                    </tr>
			                  
			                   </nedss:container>
			                   </nedss:container>
			                   
			                   
			            <nedss:container id="patSearch3" name="Maternal & Birth Information" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="sect">
					   
						<!-- ########### SUB SECTION ###########  -->
						<nedss:container id="patSearch4" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="subSect" >

							
						    <tr>
							<td class="fieldName">

							<span class="InputFieldLabel" valign="middle" id="DEM200L" style="" title="Mother's Last Name"> Mother's Last Name: </span> &nbsp; </td> 

							
							<td align=""> <html:text  property="personSearch.motherLastName" styleId="DEM200" title="Mother's Last Name"/> </td>                        
						    </tr>
						    <tr>                                                    
							<td class="fieldName">
								<span class="InputFieldLabel" id="DEM201L" style="" title="Mother's First Name">Mother's First Name: </span>&nbsp; 
							</td> 

							<td> <html:text  property="personSearch.motherFirstName" styleId="DEM201" title="Mother's First Name" /> </td>
						    </tr>
						    <tr>                                                    
							<td class="fieldName">
								<span class="InputFieldLabel" id="DEM134L" style="" title="Mother's Maiden Name">Mother's Maiden Name: </span>&nbsp; 
							</td> 

							<td> <html:text  property="personSearch.motherMaidenName" styleId="DEM134" title="Mother's Maiden Name" /> </td>
						    </tr>

						    <tr>
							<td class="fieldName">
							   <span class="InputFieldLabel" id="DEM116L" style="" title="Multiple Birth Indicator">Multiple Birth Indicator: </span>&nbsp;
							</td> 
							
							<td>
							    <html:select name="iisQueryForm" property="personSearch.multipleBirthIndicator" styleId="DEM116" title="Indication of whether the patient was part of a multiple birth." onchange="disableEnableBirthOrder(this.value)">
									<html:optionsCollection property="codedValue(YN)" value="key" label="value" />
							    </html:select>	                                                    
							</td>
						    </tr>
						    <tr>
							<td class="fieldName">
							   <span class="InputFieldLabel" id="DEM117L" style="" title="Birth Order"> Birth Order: </span>&nbsp;
							</td> 
							
						    	<td>
						    		<html:text  property="personSearch.birthOrder" size="" maxlength="2"  styleId="DEM117" title="If the patient was part of a multiple birth, indicate birth order number as a numeric value between 1 and 10" value="" disabled="true" onkeyup="isNumeric1to10Entered(this)"/>
						    	</td>    
			                    </tr>

						   </nedss:container>
			               </nedss:container>
			                   
			                   
			              
			<div class="popupButtonBar">
				<input type="button" name="Submit" value="Submit Query" onclick="return validateAndSubmitToIIS()"/>
				<input type="button" align="right"  value= "Cancel" onclick="closePopup()"/>	
			</div>	 
	</div>
	</html:form>
	</div>
	
	</body>
	</html>
