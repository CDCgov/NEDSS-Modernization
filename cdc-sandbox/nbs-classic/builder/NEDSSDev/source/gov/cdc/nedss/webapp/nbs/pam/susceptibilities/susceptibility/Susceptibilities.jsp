<%@ include file="/jsp/tags.jsp" %>
<%@ page import="java.util.*" %>
<%@ page isELIgnored ="false" %>

<html lang="en">
	<head>
	 <base target="_self">
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>Genotyping and Drug Susceptibility Testing</title>
		<%@ include file="/jsp/resources.jsp" %>	
	    <SCRIPT LANGUAGE="JavaScript" >

	    /*
		pgCheckForErrorsOnSubmit: validation on dates
		*/
		
		
		function pgCheckForErrorsOnSubmit(){
			
		  var errorElts = new Array();
   		  var errorLabels = new Array();
    
	
		 //check for date field format to be mm/dd/yyyy
			retVal = pgCheckDateFieldFormatOnSubmit();
			if (retVal.elements != null &&  retVal.elements != undefined) {
				errorElts = errorElts.concat(retVal.elements);
				errorLabels = errorLabels.concat(retVal.labels);
			}


		 if (errorElts.length > 0) {
				displayGlobalErrorMessage(errorLabels);
				var tabElts = getDistinctParentTabs(errorElts);
				setErrorTabProperties(tabElts);        
				return true;    
			}
			else {
				return false;
			}
		}

		



	    /*
	    setValues: this method read the value from each of the fields in the popup and create a string with same structure than the default values read from the metadata.
	    This string, will be concatenated to each of the default values for each of the string, that way
	    all of the rows have in common this values. From this popup, the method from the investigation page
	    that set the values in the repeating block will be called.
	    */
			function setValues()
			{
				if(!pgCheckForErrorsOnSubmit()){
				 var opener = getDialogArgument();  
				 var arrayIds = ["LABAST5","LABAST14","LABAST3","LABAST3Oth","LABAST7","LABAST7Oth"];
				 var id;
				 var value;
				 var bulkValue="";
				 for(var i =0; i<arrayIds.length; i++){
					 
					id = arrayIds[i];//This should be dynamic....
					value = $j("#"+id).val();//Values on the screen
					
					if(value!="" && value!=null){
						if(bulkValue!="")
							bulkValue=bulkValue+"~";
						bulkValue = bulkValue+id+"^"+value;	//For more values: ~
					}
				 }
	
		
				 
				 
				var idButton = '<%=request.getAttribute("id")%>';
				
				var hiddenButton = opener.getElementByIdOrByName(idButton+"Hidden");
				
				var defaultValue = hiddenButton.value ; //defaultValues

				//var subSection = "NBS_UI_GA27033";//TODO: this needs to be dynamic.
				var elem = opener.getElementByIdOrByName(idButton);
				var subSection = opener.getSubsectionIdOfRepeatingBlock(elem);
				var preffix = "";
					
				 if(defaultValue!=null && defaultValue.length>2){
					 preffix= defaultValue.substring(0,3);
					 defaultValue = defaultValue.substring(3);
				 }
				 
				 
				 
				 
				opener.addDefaultValuesToSubSection(defaultValue, subSection, bulkValue);

			  	closePopup();
                return false;
				
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
	</head>
	<body class="popup" onload="autocompTxtValuesForJSP();focusOnFirstBodyElement();addRolePresentationToTabsAndSections();" onkeypress="SubmitOnEnter();">
        <!-- Page title -->
        <div class="popupTitle">
            Genotyping and Drug Susceptibility Testing
        </div>
        
        <!-- Top button bar -->
        <%String queId = (String) request.getAttribute("identifier");%>
        <div class="popupButtonBar">
            <input type="submit" name="Submit" value="Submit" onclick="return setValues();"/>
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
		<nedss:container id="section1" name="Genotyping and Drug Susceptibility Testing" classType="sect" 
    displayImg ="false" displayLink="false" includeBackToTopLink="no" >
		
            <input id="perTitle" type="hidden" name="perTitle"  value="">
            <input id="ContextAction" type="hidden" name="ContextAction" value="EntitySearch">

            <!-- Search Table -->
            <table role="presentation" class="subSect">
         
                <tbody>
          	     
	
		<nedss:container id="subsect_defaultValues" name="Enter Default Values"  isHidden="F" classType="subSect" >	
		
		
		  <tr>
			  <td colspan = "2">
			   <p style="text-align: left">The values entered here will be applied to each row added.</p>
			  </td>
			 
		  </tr>
                    <tr>
                        <td id = "LABAST5L" class="fieldName"> Date Collected: </td>
                            </td>

					<td style="white-space:nowrap">
                        

							<html:text name="PageForm"  property="pageClientVO.answer(LABAST5)" maxlength="10" size="10" styleId="LABAST5" onkeyup="DateMask(this,null,event)" styleClass="NBS_UI_GA27033" title="Antimicrobial Susceptibility Specimen Collection Date"/>
							<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LABAST5','LABAST5Icon'); return false;" styleId="LABAST5Icon" onkeypress="showCalendarEnterKey('LABAST5','LABAST5Icon',event)"></html:img>


						</td> 
						
                    </tr>
					
					
					<tr>
                        <td id = "LABAST14L" class="fieldName"> Date Reported: </td>
                            </td>

					<td style="white-space:nowrap">
                        

							<html:text name="PageForm"  property="pageClientVO.answer(LABAST14)" maxlength="10" size="10" styleId="LABAST14" onkeyup="DateMask(this,null,event)" styleClass="NBS_UI_GA27033" title="Antimicrobial susceptibility result reported date"/>
							<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LABAST14','LABAST14Icon'); return false;" styleId="LABAST14Icon" onkeypress="showCalendarEnterKey('LABAST14','LABAST14Icon',event)"></html:img>


						</td> 
						
                    </tr>
					
					
					<tr>
                        <td id = "LABAST3L" class="fieldName"> Specimen Source: </td>
                            </td>

						<td style="white-space:nowrap">
							<html:select name="PageForm" property="pageClientVO.answer(LABAST3)" styleId="LABAST3" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" onchange="enableOrDisableOther('LABAST3');pgSelectNextFocus(this);">
							<nedss:optionsCollection property="codedValue(PHVS_TB_MICRO_SITE_V3)" value="key" label="value" /> </html:select>

						</td> 
						
                    </tr>
					
					
					
					<tr>
                        <td id = "LABAST3OthL" class="fieldName">
							<span class="InputDisabledLabel otherEntryField" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3OthL">Other Specimen Source:</span>
						</td>

						</td></td>

						<td style="white-space:nowrap">
							<html:text name="PageForm" disabled="true" property="pageClientVO.answer(LABAST3Oth)" size="40" maxlength="40" title="Other Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" styleId="LABAST3Oth"/></td></tr>
						</td> 
							
                    </tr>
					
					<tr>
                        <td id = "LABAST7L" class="fieldName"> Test Method (Optional): </td>
                      
						<td style="white-space:nowrap">
							<html:select name="PageForm" property="pageClientVO.answer(LABAST7)" styleId="LABAST7" title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" onchange="enableOrDisableOther('LABAST7');pgSelectNextFocus(this);">
			<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTMETHOD_TB)" value="key" label="value" /> </html:select>
		
						</td> 
						
                    </tr>
				
					
					
					
					
					<tr>
                        <td id="LABAST7OthL" class="fieldName">
							<span class="InputDisabledLabel otherEntryField" title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" id="LABAST7OthL">Other Test Method (Optional):</span></td>

						</td>
						<td style="white-space:nowrap">
							<html:text name="PageForm" disabled="true" property="pageClientVO.answer(LABAST7Oth)" size="40" maxlength="40" title="Other Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" styleId="LABAST7Oth"/></td></tr>
						</td> 
						
                    </tr>
						
		</nedss:container>
       
                  
                </tbody>
            </table>
		 </nedss:container>
        </html:form>
        <!-- Bottom button bar -->    
        <div class="popupButtonBar">
            <input type="submit" name="Submit" value="Submit" onclick="return setValues();"/>
            <input type="button" name="Cancel" value="Cancel" onclick="closePopup();" />
        </div>
    </body>
</html>	