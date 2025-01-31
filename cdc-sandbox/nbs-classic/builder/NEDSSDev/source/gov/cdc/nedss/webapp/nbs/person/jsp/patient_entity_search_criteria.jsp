<?xml version="1.0" encoding="UTF-8"?>

<!-- ### DMB: BEGIN JSP PAGE GENERATE ###- - -->
<html lang="en">
<head>
<base target="_self">
<title>NBS:Find Patient</title>
<%@ include file="/jsp/tags.jsp"%>
<%@ include file="/jsp/resources.jsp"%>
<%@ page import="java.util.*"%>

<script language="JavaScript">      
        //   if (typeof window.event != 'undefined')
  document.onkeydown = function(event)
    {
		//var t=event.srcElement.type;
		if(event!=null){
			var t=event.target.type;
			if(t == '' || t == 'undefined' || t == 'button') {
				return;
			}
			var kc=event.keyCode;
			//alert(kc + '  ' +  t);
			if(t == 'text' && kc == 13) {
				searchPatient();
			}		
    	}
		
		return preventF12(event);
    }
	
	function clearForm(){
		
		
		//Remove the validation bar
		if(document.getElementById("errorMessages")!=null)
			document.getElementById("errorMessages").remove();
		
		//Remove the red color on each label
		var length = document.getElementsByClassName("InputFieldLabel").length

		for(i=0; i<length;i++){
			document.getElementsByClassName("InputFieldLabel")[i].setAttribute("style","")
		}
		
		//Remove the red border on each dropdown
		var length2 = document.getElementsByTagName("input").length

		for(i=0; i<length2;i++){
			var element = document.getElementsByTagName("input")[i];
			if(element!=null && element.getAttribute("name")!=null && element.getAttribute("name").indexOf("textbox")!=-1)
				element.setAttribute("style","");
		}
		
	
		//Last Name
		getElementByIdOrByName("DEM102O_textbox").value="Starts With";
		document.getElementById("DEM102O").selectedIndex="0";
		document.getElementById("DEM102").value="";
		
		//First Name
		getElementByIdOrByName("DEM104O_textbox").value="Starts With";
		document.getElementById("DEM104O").selectedIndex="0";
		document.getElementById("DEM104").value="";
		
		//Date of Birth
		getElementByIdOrByName("DEM105O_textbox").value="Equal";
		document.getElementById("DEM105O").selectedIndex="1";
		document.getElementById("patientDOB1").value="";
		document.getElementById("patientDOB2").value="";
		document.getElementById("patientDOB3").value="";
		flipDate(document.getElementById("DEM105O"))
		
		//Current Sex
		getElementByIdOrByName("DEM114_textbox").value="";
		document.getElementById("DEM114").selectedIndex="-1";
		
		
		//Street address
		getElementByIdOrByName("DEM106O_textbox").value="Contains";
		document.getElementById("DEM106O").selectedIndex="0";
		document.getElementById("DEM106").value="";
			
		//City	
		getElementByIdOrByName("DEM107O_textbox").value="Contains";
		document.getElementById("DEM107O").selectedIndex="0";
		document.getElementById("DEM107").value="";
		
		//State
		getElementByIdOrByName("DEM108O_textbox").value="";
		document.getElementById("DEM108O").selectedIndex="-1";
		
		//Zip
		document.getElementById("DEM109").value="";
		
		//Parent ID(s)
		document.getElementById("DEM229").value="";
		
		//Id Type
		
		getElementByIdOrByName("DEM222_textbox").value="";
		document.getElementById("DEM222").selectedIndex="-1";
		
		//Id Number
		document.getElementById("DEM223").value="";
		
		//Phone
		document.getElementById("DEM238").value="";
		
		//Email
		document.getElementById("DEM182").value="";
		
		//Ethnicity
		getElementByIdOrByName("DEM221_textbox").value="";
		document.getElementById("DEM221").selectedIndex="-1";
		
		//Race
		getElementByIdOrByName("DEM176_textbox").value="";
		document.getElementById("DEM176").selectedIndex="-1";

		window.close();
		
	}
	
     function searchPatient() {
	
					var allEnabledSearchIpElts = $j("#patientSearchByDetails").find(':input:enabled');
					var atleastOne = false;
					var erroronTab1=false;
					var erroronTab2=false;
					var validDob = true;
					var errorMsgArray = new Array(); 
				   // alert(allEnabledSearchIpElts.length);
			        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
			            if ($j(allEnabledSearchIpElts[i]).attr("id") != '' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM102O' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM104O' && 
			             $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM105O' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM106O' &&
			             $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM107O' && $j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
			            && $j(allEnabledSearchIpElts[i]).attr("type") != 'button' && jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) !="" ) {
							  //$j(allEnabledSearchIpElts[i]).attr("type");
							  
							 // alert($j(allEnabledSearchIpElts[i]).attr("id")); 
								atleastOne = true;
								
								//alert($j(allEnabledSearchIpElts[i]).attr("id") + " :  " + $j(allEnabledSearchIpElts[i]).attr("type") + $j(allEnabledSearchIpElts[i]).attr("value"));
							 
							 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {
							            if(!(12 >= $j(allEnabledSearchIpElts[i]).attr("value") && $j(allEnabledSearchIpElts[i]).attr("value") >= 1)){
							            errorText = "Month of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to 12. Please correct the data and try again."; 
										errorMsgArray.push(errorText);
										$j("#patientDOB1").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										 erroronTab1= true; 
										}
								
								if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
								   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
								       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
								  		    errorMsgArray.push(errorText);
									            $j("#patientDOB1").focus();
									            getElementByIdOrByName("DEM105L").style.color="#CC0000";
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
							                  //alert(element);
							                  
							                // alert(days);
							               
									 if(element > 31 || element < 1 )
									{
									    errorText = "Day of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to 31. Please correct the data and try again.";
                                        //errorText = "Date of Birth must be in the format of mm/dd/yyyy. Please correct the data and try again.";
										errorMsgArray.push(errorText);
										$j("#patientDOB2").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										 erroronTab1= true; 
									}
									else if ( days >= element && element >= 1) {
										//return true;
									}else {
									   errorText = "Day of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to "+days+". Please correct the data and try again.";
									   //errorText = "Date of Birth must be in the format of mm/dd/yyyy. Please correct the data and try again.";
										errorMsgArray.push(errorText);
										$j("#patientDOB2").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										 erroronTab1= true; 
									}
							            
							           
								
								if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
								   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
								       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
								  		    errorMsgArray.push(errorText);
									            $j("#patientDOB1").focus();
									            getElementByIdOrByName("DEM105L").style.color="#CC0000";
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
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
									}
									
									if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
									   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
									       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
											    errorMsgArray.push(errorText);
											    $j("#patientDOB1").focus();
											    getElementByIdOrByName("DEM105L").style.color="#CC0000";
											     erroronTab1= true; 
								}	
														 
										
							 }
							//alert("1.4");	
							 //alert($j(allEnabledSearchIpElts[i]).attr("id") + $j(allEnabledSearchIpElts[i]).attr("value"));
							 
							 if(($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOBBet1'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
							     (getElementByIdOrByName("patientDOBBet2") != null && getElementByIdOrByName("patientDOBBet2").value == "")){
							     errorText = "Both DOB Begin Date and DOB End Date must be entered. Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB2").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
							 
							 }
							// alert("1.5");	
							if(($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOBBet2'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
							     (getElementByIdOrByName("patientDOBBet1") != null && getElementByIdOrByName("patientDOBBet1").value == "")){
							      errorText = "Both DOB Begin Date and DOB End Date must be entered. Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB1").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
							 
							 }
							 //alert("1.6");	
							 
							 //id type and id value validation starts here....
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
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										
									}
								}
			            }
			        }
			         //alert("2");
			        if((getElementByIdOrByName("patientDOBBet1") != null && 
							 getElementByIdOrByName("patientDOBBet1").value != "") && 
							 (getElementByIdOrByName("patientDOBBet2") != null &&
							  getElementByIdOrByName("patientDOBBet2").value != null) ){
							  
							  var date1= getElementByIdOrByName("patientDOBBet1").value;
							  var date2= getElementByIdOrByName("patientDOBBet2").value;
							   if(CompareDateStrings(date1, date2) >= 0)
								    {
								       errorText = "DOB Begin Date must be less than the  DOB End Date . Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB1").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
								    }
							 
							 }	
					//Operators
					
					if(getElementByIdOrByName("DEM102O_textbox").value==""||
					getElementByIdOrByName("DEM104O_textbox").value==""||
					getElementByIdOrByName("DEM105O_textbox").value==""||
					getElementByIdOrByName("DEM106O_textbox").value==""||
					getElementByIdOrByName("DEM107O_textbox").value==""){
						
						errorText = "Please select a valid operator value.";
			        	errorMsgArray.push(errorText);
						
					}
					else
			       if(!atleastOne){
			        	errorText = "Please enter at least one item to search.";
			        	errorMsgArray.push(errorText);
			        }
				   
				   
			          
		        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
				     if($j(allEnabledSearchIpElts[i]).attr("type") == 'button')
				        $j(allEnabledSearchIpElts[i]).attr("disabled","disabled");
		        }	
		     // display the errors in the error messages bar.
		    // alert(errorMsgArray.length);
                if(errorMsgArray!=null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
                    handleRefineSearch();
                    return false;
                }
		     	document.personSearchForm.action = "/nbs/PatientEntitySearch.do?method=searchSubmit&ContextAction=Submit";
				isFormSubmission = true;
				blockUiDuringFormSubmission();          
				document.personSearchForm.submit();
				return true;
			}
			
	
	function clearPatient() {
        var allEnabledSearchIpElts = $j("#patientSearchByDetails").find(':input:enabled');
        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
            if ($j(allEnabledSearchIpElts[i]).attr("type") != 'button') {
                $j(allEnabledSearchIpElts[i]).val("");
            }
        }	
	}

	function flipDate(_selectBox){
	
	var _date=getElementByIdOrByName("fulldate");
	var _dateRange = getElementByIdOrByName("betweendate");
	var _birthTimeMonth = getElementByIdOrByName("personSearch.birthTimeMonth");
	var _birthTimeDay = getElementByIdOrByName("personSearch.birthTimeDay");
	var _birthTimeYear = getElementByIdOrByName("personSearch.birthTimeYear");
	var _afterBirthTime = getElementByIdOrByName("personSearch.afterBirthTime");
	var _beforeBirthTime = getElementByIdOrByName("personSearch.beforeBirthTime");
	
	
	if(_selectBox.value=="="){
	        _afterBirthTime.value = "";
	        _beforeBirthTime.value = "";
		_date.className="visible";
		_dateRange.className="none";
	} else {
	        _birthTimeMonth.value = "";
	        _birthTimeDay.value = "";
	        _birthTimeYear.value = "";
	        
		_date.className="none";
		_dateRange.className="visible";
	}

}

function flipDateOnload(selectBox){
	
	var _selectBox = getElementByIdOrByName(selectBox);
	var _date=getElementByIdOrByName("fulldate");
	
	var _dateRange = getElementByIdOrByName("betweendate");
	
	if(_selectBox.value=="="){
		_date.className="visible";
		_dateRange.className="none";
	} else {
		_date.className="none";
		_dateRange.className="visible";
	}

}

function handleAdvancedSearch() {
 
    var atleastOne = false;
     var allEnabledSearchIpElts = $j("#patSearch4").find(':input:enabled');
      for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
       if ( $j(allEnabledSearchIpElts[i]).attr("type") != 'checkbox' 
       && $j(allEnabledSearchIpElts[i]).attr("type") != 'radio' && $j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
       && $j(allEnabledSearchIpElts[i]).attr("type") != 'button'){
			
			//alert($j(allEnabledSearchIpElts[i]).attr("id") + " :  " + $j(allEnabledSearchIpElts[i]).attr("type") + $j(allEnabledSearchIpElts[i]).attr("value"));            
			              atleastOne = true;   
			   
			   
			            }//if ends
     
     
    var sectionId = "#patSearch3";
    var sectionHead = $j(sectionId).find("table.sectHeader").get(0);
    var sectionBody = $j(sectionId).find("div.sectBody").get(0);

    if (!atleastOne) {
        $j(sectionBody).hide();
        $j(sectionHead).find("a.toggleIconHref").html(plusIcon);
    } 
    else {
        $j(sectionBody).show();
        $j(sectionHead).find("a.toggleIconHref").html(minusIcon);
    }
    
    updateSectionsTogglerHandle("#" + $j(
                                        $j(
                                            sectionId                                 
                                        ).parent().get(0)
                                      ).attr("id"));
	
	     }//for ends
	}

function handleRefineSearch() {
 	 if(getElementByIdOrByName("patientDOBBet1") != null && getElementByIdOrByName("patientDOBBet1").value != ""){
	    var _date=getElementByIdOrByName("fulldate");
	   var _dateRange = getElementByIdOrByName("betweendate");
	    _date.className="none";
		_dateRange.className="visible";
	 }
	}
	
function setSelectedIndex(s, v) {
 
    for ( var i = 0; i < s.options.length; i++ ) {
   
        if ( s.options[i].value == v ) {
        
            s.options[i].selected = true;
            
            return;
        }
    }
}

/** 
* Block the UI using the blockUI JQuery plugin
*/
function blockUiDuringFormSubmission()
{
    var saveMsg = '<div class="submissionStatusMsg" style="height:90px"> <div class="header"> Loading Search Results </div>' +  
        '<div class="body"> <img src="saving_data.gif" alt="Saving data" title="Saving data"/> Your search results are being loaded. Please wait ... </div> </div>';         
	$j.blockUI({  
	    message: saveMsg,  
	    css: {  
	        top:  ($j(window).height() - 500) /2 + 'px', 
	        left: ($j(window).width() - 500) /2 + 'px', 
	        width: '500px',
	        height: '90px'
	    }  
	});
}
      
          </script>

<style type="text/css">
table.FORM {
	width: 100%;
	margin-top: 15em;
}
</style>
</head>


<body class="popup"
	onload="startCountdown();autocompTxtValuesForJSP();handleRefineSearch();handleAdvancedSearch();">
	<div id="pageview"></div>
	<!-- Container Div: To hold top nav bar, button bar, body and footer -->
	<div id="doc3">
		<html:form action="/PatientEntitySearch.do">
			<!-- Body div -->
			<div id="bd">
			
			<div class="popupTitle">
            	Search for Existing Patient
        	</div>
				<!-- top button bar -->

				<div class="grayButtonBar">
					<table role="presentation" width="100%">
						<tr>
							<td align="left" width="80%"></td>

							<td align="right"><input type="button" name="Close"
								value="Close" onclick="clearForm()" /></td>
							<td align="right" style="width: 10px; padding-left: 5px"><input
								type="button" name="Submit" value="Submit"
								onclick="searchPatient()" /></td>
						</tr>

					</table>

				</div>
				<table role="presentation" width="100%">
					<tr>
						<td height="10">&nbsp;</td>
					</tr>
				</table>


				<!-- Page Errors -->
				<%@ include file="/jsp/feedbackMessagesBar.jsp"%>

				<!-- Error Messages using Action Messages-->
				<div id="globalFeedbackMessagesBar" class="screenOnly">
					<logic:messagesPresent name="error_messages">
						<div class="infoBox errors" id="errorMessages">
							<b> <a name="errorMessagesHref"></a> Please fix the following errors:
							</b> <br />
							<ul>
								<html:messages id="msg" name="error_messages">
									<li><bean:write name="msg" /></li>
								</html:messages>
								<ul>
						</div>
					</logic:messagesPresent>
				</div>



				<!-- ################### PAGE TAB ###################### -->
				<jsp:include page="PatientSearch.jsp" />
				<div class="grayButtonBar">
					<table role="presentation" width="100%">
						<tr>
							<td align="left" width="80%"></td>
							<td align="right"><input type="button" name="Close"
								value="Close" onclick="clearForm()" /></td>
							<td align="right" style="width: 10px; padding-left: 5px"><input
								type="button" name="Submit" value="Submit"
								onclick="searchPatient()" /></td>
						</tr>
					</table>

				</div>
		</html:form>
	</div>
	<!-- Container Div -->
</body>

</html>
<SCRIPT LANGUAGE="JavaScript">
		function startCountdown() {
			var sessionTimeout = <%= request.getSession().getMaxInactiveInterval()%>
			min = sessionTimeout / 60;
			sec = 0;				
			getTimerCountDown();
		}
	</SCRIPT>
