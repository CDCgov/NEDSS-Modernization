<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html style="overflow-x:hidden" lang="en">
    <head>
    	<base target="_self">
        <title>NBS: Manage Value Sets</title>
         
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script type="text/javaScript">

		blockEnterKey();
		/**
		* setConceptCode: this method sets the Local code in the Concept Code
		*/
				
		function setConceptCode(){
			var localCode = getElementByIdOrByName("ValLC").value;
			getElementByIdOrByName("ValSCF").value = localCode;
		}
		
		/**
		* setConceptName: this method sets the Display Name in Concept Name
		*/
		
		function setConceptName(){
			var localCode = getElementByIdOrByName("ValLDN").value;
			getElementByIdOrByName("ValSNF").value = localCode;		
		}
		
		/**
		* setPreferredConceptName: this method sets the Short Display Name in Preferred Concept Name
		*/
		
		
		function setPreferredConceptName(){
			var localCode = getElementByIdOrByName("ValSDN").value;
			getElementByIdOrByName("ValPSNF").value = localCode;			
		}
		
		
		function editLoad(code)
        { 
			var codeValGnCd='${fn:escapeXml(codeValGenCode)}';
			var codesetNm='${fn:escapeXml(codesetNm)}';
            document.forms[0].action ="/nbs/ManageCodeSet.do?method=editCodeValGenCode&codesetNm="+codesetNm+"&codeVal="+codeValGnCd;
            document.forms[0].submit();		
            return true;		
        }

		function submitDwrCodeset() {
			var mode= "EDIT";
			if(ConceptCodeReqFlds(mode)) {
	            return false;
	        }
	        else {
				var cc = getElementByIdOrByName("ValSNF").value;
				var pcc = getElementByIdOrByName("ValPSNF").value;
				var valscf = getElementByIdOrByName("ValSCF").value;
				var codesnf_dd = getElementByIdOrByName("CodeSNF_DD").value;
				var hidLocalCodeVal = getElementByIdOrByName("hidLocalCodeVal").value;
															

				// added by jayasudha
				//var vallc = getElementByIdOrByName("ValLC").value;
				var valldn = getElementByIdOrByName("ValLDN").value;
				var valsdn = getElementByIdOrByName("ValSDN").value;
				var valeft = getElementByIdOrByName("ValEFT").value;
				var valetd = getElementByIdOrByName("ValETD").value;
				var vallsc = getElementByIdOrByName("valLSC").value;
				var valadc = getElementByIdOrByName("ValADC").value;
				var ValSCF = getElementByIdOrByName("ValSCF").value;
				var CodeSNF_DD = getElementByIdOrByName("CodeSNF_DD").value;

				var cdSysDescTxt = "";
				var codeSNF = "";
				//if(phin == 'PHIN'){
					cdSysDescTxt = getElementByIdOrByName("CodeSNF_DD");
					codeSNF = cdSysDescTxt.value;
				//}
			   // else if(phin == 'LOCAL')
			    	//codeSNF = getElementByIdOrByName("CodeSNF_TEXT").value;
		    	
				var phin = "LOCAL";
					
				if(codeSNF ==  "L" || codeSNF == "NBS_CDC" ){
					phin  = "LOCAL";
				} else {
					phin  = "PHIN";
				}
				
				
				JSRTForm.updateCodeValGenCode(hidLocalCodeVal ,phin ,  cc, pcc, ValSCF ,CodeSNF_DD,  valldn ,  valsdn ,  valeft ,  valetd,  vallsc ,  valadc , function(data) {
			    });
			    setTimeout("loadPWindow()", 1000);
	        }
		}
		function loadPWindow() {
			var codeSetNm='${fn:escapeXml(codeSetNm)}';
			var opener = getDialogArgument();
          	opener.document.forms[0].action ="/nbs/ManageCodeSet.do?method=viewCodeSet&codeSetNm="+codeSetNm;
          	opener.document.forms[0].submit();
          	editCalled = true;
			self.close();										    
		
		}
		function submitDwrCreateCodeset()
		{
			var mode= "CREATE";
			if(ConceptCodeReqFlds(mode)) {
				
	            return false;
	        }
	        else {
				
				var cc = getElementByIdOrByName("ValSNF").value;
				var pcc = getElementByIdOrByName("ValPSNF").value;
				var valSCF = getElementByIdOrByName("ValSCF").value;
				var phin = getElementByIdOrByName("first").value;

				var ValLC = getElementByIdOrByName("ValLC").value;
				var ValLDN = getElementByIdOrByName("ValLDN").value;
				var ValSDN = getElementByIdOrByName("ValSDN").value;
				var ValEFT = getElementByIdOrByName("ValEFT").value;
				var ValETD = getElementByIdOrByName("ValETD").value;
				var valLSC = getElementByIdOrByName("valLSC").value;
				var ValADC = getElementByIdOrByName("ValADC").value;


								
				var cdSysDescTxt = "";
				var codeSNF = "";
				//if(phin == 'PHIN'){
					cdSysDescTxt = getElementByIdOrByName("CodeSNF_DD");
					codeSNF = cdSysDescTxt.value;
				//}
			   // else if(phin == 'LOCAL')
			    	//codeSNF = getElementByIdOrByName("CodeSNF_TEXT").value;
		    	
				
				if(codeSNF ==  "L" || codeSNF == "NBS_CDC" ){
					
					phin  = "LOCAL";
				} else {
					
					phin  = "PHIN";
				}
				
			     JSRTForm.createCodeValGenSubmit(cc, pcc,valSCF,phin,codeSNF, ValLC,  ValLDN ,  ValSDN ,  ValEFT ,  ValETD,  valLSC ,  ValADC , function(data) {
			     
			     if(data == 'NOTSUCCESS'){
			        var errorMsgArray = new Array();
				   	var msg = "A record already exists with this Code. Please enter a unique Code to create a new record.";
				   	errorMsgArray.push(msg);
				   	displayErrors("errorBlock", errorMsgArray);
					return false;	           		
			     }else if(data == 'SUCCESS'){
			    	 setTimeout("loadQWindow()", 1000); 
			     }else{
			    	 var errorMsgArray = new Array();
					   	var msg = "Database error while creating. Please check the values and try again.";
					   	errorMsgArray.push(msg);
					   	displayErrors("errorBlock", errorMsgArray);
						return false;
			     } 
			    });
	        }
		}
		function loadQWindow() {
			var codeSetNm='${fn:escapeXml(codeSetNm)}';
			var opener = getDialogArgument();
          	opener.document.forms[0].action ="/nbs/ManageCodeSet.do?method=viewCodeSet&codeSetNm="+codeSetNm;
			self.close();										    
		
		}
        
		function submitConcept()
        { 
		//	if(ConceptCodeReqFlds()) {
			//	 return false;
		//	}else
		//	{
			//	var o = window.dialogArguments;
			//	var opener = o.opener;      
			//	opener.reloadCodeset();

				//window.close();
				//closeForm(true);
			//	return false;
			//}
			
		    if(ConceptCodeReqFlds()) {
	            return false;
	        }
	        else {
				document.forms[0].action = "${SRTAdminManageForm.attributeMap.submit}";
	       }
        }
        
		 var closeCalled = false;
		 
         function closeForm(closePopup, event)
         {
        	// This check is required to avoid duplicate invocation 
             // during close button clicked and page unload.
	            if (closeCalled == false) {
	                closeCalled = true;
	                
	                // Note: A check for event.clientY < 0 is required to
	                // make sure the "X" icon the top right corner of
	                // a window is cliced. i.e., Page unloads 
	                // due to edit/other link clicks withing the window frame
	                // are therefore ignored. 
                 if ((event!=null && event.clientY < 0) || closePopup == true) {
                    // get reference to opener/parent           
                    var opener = getDialogArgument();
                    
                    // refresh parent's form
                    opener.document.forms[0].action ="${SRTAdminManageForm.attributeMap.CancelConcept}";
                    opener.document.forms[0].submit();
                    
                    // pass control to parent's call back handler
                    window.returnValue = "windowClosed";
	                    
                    window.close();
                 } 
             }
         }


	var editCalled = false;
        function closeForm2(closePopup, event)
         {
        	// This check is required to avoid blank screen when X pressed 
             // during edit button clicked and page unload.
	            if (editCalled == false) {
	            
	                
	                // Note: A check for event.clientY < 0 is required to
	                // make sure the "X" icon the top right corner of
	                // a window is cliced. i.e., Page unloads 
	                // due to edit/other link clicks withing the window frame
	                // are therefore ignored. 
                 if ((event!=null && event.clientY < 0) || closePopup == true) {
                    // get reference to opener/parent           
                    var opener = getDialogArgument();
                    
                    // refresh parent's form
                    opener.document.forms[0].action ="${SRTAdminManageForm.attributeMap.CancelConcept}";
                    opener.document.forms[0].submit();
                    
                    // pass control to parent's call back handler
                    window.returnValue = "windowClosed";
	                    
                    window.close();
                 } 
             }
         }



         function closeFormButton(closePopup, event)
         {
        	// This check is required to avoid duplicate invocation 
             // during close button clicked and page unload.
             var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
		        if (confirm(confirmMsg))
		        {
			            if (closeCalled == false) {
			                closeCalled = true;
			                
			                // Note: A check for event.clientY < 0 is required to
			                // make sure the "X" icon the top right corner of
			                // a window is cliced. i.e., Page unloads 
			                // due to edit/other link clicks withing the window frame
			                // are therefore ignored. 
			                
			                if(event==undefined || event == null)
			                	event = e;
		                 if (event.clientY < 0 || closePopup == true) {
		                    // get reference to opener/parent           
		                    var opener = getDialogArgument();
		                    
		                    // refresh parent's form
		                    opener.document.forms[0].action ="${SRTAdminManageForm.attributeMap.CancelConcept}";
		                    opener.document.forms[0].submit();
		                    
		                    // pass control to parent's call back handler
		                    window.returnValue = "windowClosed";
			                    
		                    window.close();
		                 } 
		             }
		        }
		        else {
		            return false;
		        }
         }

        
		</script> 
		<style type="text/css">
	        div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
	        div.popupTitle {width:100%; background:#185394; padding:3px; color:#FFF; text-align:left; font-size:110%; font-weight:bold;}	
	        div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
	        .removefilter{
						background-color:#003470; width:100%; height:25px;
						line-height:25px;float:right;text-align:right;
						}
						removefilerLink {vertical-align:bottom;  }
						.hyperLink
						{
						    font-size : 10pt;
						    font-family : Geneva, Arial, Helvetica, sans-serif;
						    color : #FFFFFF;
							text-decoration: none;
						}
        </style>
		</head> 
	<logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
    	<body class="popup"  onload="addRolePresentationToTabsAndSections();" style="overflow-y:hidden;" onload="startCountdown();" onunload="closeForm(true, event);addRolePresentationToTabsAndSections();"> 
    </logic:equal>
    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
    	<body class="popup"  onload="addRolePresentationToTabsAndSections();" style="overflow-y:hidden;" onload="startCountdown();" onunload="closeForm2(true, event);addRolePresentationToTabsAndSections();">
    </logic:notEqual> 
    <!-- Page title -->
    <div class="popupTitle">${fn:escapeXml(BaseForm.pageTitle)}</div>
    <div id="doc3">
         <html:form action="/ManageCodeSet.do" styleId="codeSetForm">	
            	<div id="bd">
            <!-- top bar -->
			        <!--Top Button bar  -->
				        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
			            	<div class="popupButtonBar">
					            <input type="button" name="submitA" id="submitA" value="Submit" onClick="submitDwrCodeset();"/>
					            <input type="submit" id="submitB" value="Cancel" onClick="return closeFormButton(true,event);"/>
					         </div>
				         </logic:equal>
				         <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
			            	<div class="popupButtonBar">
					            <input type="button" name="submitA" id="submitA" value="Submit" onClick="submitDwrCreateCodeset();"/>
					            <input type="submit" id="submitB" value="Cancel" onClick="return closeFormButton(true,event);"/>
					         </div>
				         </logic:equal>
				         <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
			            	<div class="popupButtonBar">
					            <input type="submit" name="submitA" id="submitA" value="Edit" onClick="editCalled=true;return editLoad();"/>
					            <input type="submit" id="submitB" value="Close" onClick="return closeForm(true);"/>
					         </div>
				         </logic:equal>
				          <!-- Indicates Required field -->
	            		<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
					        <div align="right"  style="padding-top: 8px;">
					            <i>
					                <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
					            </i>
					        </div>
				        </logic:notEqual>
				        <!-- Error text -->
				        <tr style="background:#FFF;">
				            <td colspan="2">
				                <div class="infoBox errors" style="display:none;" id="errorBlock">
				                </div>
				            </td>
				         </tr>
			            <!-- Page Code Starts here -->
			            
			            
			             <!--  Jayasudha has changed the file on 16/11/2016 for LOCALE Field enhancement  -->
                           
                            <nedss:container id="section1" name="Local Concept Code" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
	                    	<fieldset style="border-width:0px;" id="codeset">
		                        <nedss:container id="subsec1" classType="subSect" displayImg ="false">
		                        	<tr>
		                                <td class="fieldName"  id="localCode">
		                                	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                		<font class="boldTenRed" > * </font>
		                                	</logic:notEqual><span>Local Code:</span>
		                                </td>
		                                <td>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
		                                        <html:text title="Local Code" onkeyup="isSpecialCharEnteredForCodeOnlyAlphAndUnderscore(this,event);setConceptCode()" property="codeValGnSelection.code" styleId="ValLC" size="50" maxlength="20"/>
		                                    </logic:equal>
		                                   <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.code"/>
											 <html:hidden property="codeValGnSelection.code" name="SRTAdminManageForm"  styleId="hidLocalCodeVal"/>
										   </logic:notEqual>
		                                </td>
		                            </tr>
				             		<tr>
		                                <td class="fieldName"  id="longDisplayName">
		                                	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                		<font class="boldTenRed" > * </font>
		                                	</logic:notEqual><span>Long Display Name:</span>
		                                </td>
		                                <td>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
		                                        <html:text title="Long Display Name" onkeyup = "setConceptName()" property="codeValGnSelection.codeDescTxt" styleId="ValLDN" size="50" maxlength="256"/>
		                                    </logic:equal>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
		                                        <html:text title="Long Display Name" property="codeValGnSelection.codeDescTxt" styleId="ValLDN" size="50" maxlength="256"/>
		                                    </logic:equal>
		                                   <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.codeDescTxt"/>
		                                    </logic:equal>
		                                </td>
		                            </tr>
		                            <tr>
		                                <td class="fieldName"  id="shorDisplayName">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	<font class="boldTenRed" > * </font>
		                                </logic:notEqual><span>Short Display Name (UI Display):</span>
		                                </td>
		                                <td>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
		                                        <html:text title="Short Display Name (UI Display)" onkeyup="setPreferredConceptName()" property="codeValGnSelection.codeShortDescTxt" styleId="ValSDN" maxlength="256" size="50"/>
		                                    </logic:equal>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
		                                        <html:text title="Short Display Name (UI Display)" property="codeValGnSelection.codeShortDescTxt" styleId="ValSDN" maxlength="100" size="50"/>
		                                    </logic:equal>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.codeShortDescTxt"/>
		                                    </logic:equal>
		                                </td>
		                            </tr> 
		                            <tr>
		                                <td class="fieldName"  id="efecFromTime">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	<font class="boldTenRed" > * </font>
		                                </logic:notEqual><span>Effective From Time:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <html:text title="Effective From Time" property="codeValGnSelection.effectiveFromTime"   maxlength="10" size="10" styleId="ValEFT" onkeyup="DateMaskFuture(this,event)"/>
												<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('ValEFT','INV147Icon');return false;" onkeypress = "showCalendarFutureEnterKey('ValEFT','INV147Icon',event);" styleId="INV147Icon"></html:img>
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.effectiveFromTime"/>
		                                    </logic:equal>
		                                </td>
		                            </tr> 
									<tr>
		                                <td class="fieldName"  id="efecToTime">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	
		                                </logic:notEqual><span>Effective To Time:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <html:text title="Effective To Time" property="codeValGnSelection.effectiveToTime"  maxlength="10" size="10" styleId="ValETD" onkeyup="DateMaskFuture(this,event)"/>
												<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('ValETD','INV147Icon');return false;" onkeypress = "showCalendarFutureEnterKey('ValETD','INV147Icon',event);" styleId="INV147Icon"></html:img>
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.effectiveToTime"/>
		                                    </logic:equal>
		                                </td>
		                            </tr> 
									
									
									
									
		                            <tr>
		                                <td class="fieldName"  id="statusCode">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	<font class="boldTenRed" > * </font>
		                                </logic:notEqual><span>Status Code:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                       
		                                        <html:select title="Status Code" name="SRTAdminManageForm"  property="codeValGnSelection.statusCd" styleId="valLSC" acomplete="false" > 
									                
									                <html:option value=""></html:option>
									                <html:option value="A">Active</html:option>
									                <html:option value="I">Inactive</html:option>
									            </html:select>
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <logic:notEmpty name="SRTAdminManageForm" property="codeValGnSelection.statusCd">
													<logic:equal name="SRTAdminManageForm" property="codeValGnSelection.statusCd" value="A">
															Active
													</logic:equal>
													 <logic:equal name="SRTAdminManageForm" property="codeValGnSelection.statusCd" value="I">
															Inactive
													 </logic:equal>
											 </logic:notEmpty>
		                                    </logic:equal>
		                                </td>
		                            </tr>
									
									 <tr>
		                                <td class="fieldName"  id="admiComments">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	
		                                </logic:notEqual><span>Administrative Comments:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <html:textarea title="Administrative Comments" style="WIDTH: 400px; HEIGHT: 100px;"  styleId="ValADC" name="SRTAdminManageForm" property="codeValGnSelection.adminComments" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)" />
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.adminComments"/>
		                                    </logic:equal>
		                                </td>
		                            </tr>
									
									
									
		                          </nedss:container>
	                            </fieldset>
                            </nedss:container>
                            <!-- Ended By jayasudha -->
                            
			            <nedss:container id="section1" name="Messaging Concept Code " classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
	                    	<fieldset style="border-width:0px;" id="codeset">
		                        <nedss:container id="subsec1" classType="subSect" displayImg ="false">
		                        	<tr style="display:none">
		                                <td class="fieldName"  id="Phin">
		                                	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                		<font class="boldTenRed" > * </font>
		                                	</logic:notEqual><span>Concept Type:</span>
		                                </td>
		                                <td>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
		                                        <html:select title="Concept Type" name="SRTAdminManageForm"  property="codeValGnSelection.conceptTypeCd" styleId="first" > 
									                 <html:optionsCollection property="valueSetTypeCdNoSystemStrd" value="key" label="value"/>
									            </html:select>
		                                    </logic:equal>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
									             <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.conceptTypeCd"/>
		                                    </logic:notEqual>
		                                </td>
		                            </tr>
				             		<tr>
		                                <td class="fieldName"  id="Ccode">
		                                	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                		<font class="boldTenRed" > * </font>
		                                	</logic:notEqual><span>Concept Code:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <html:text title="Concept Code" property="codeValGnSelection.conceptCode" styleId="ValSCF" size="50" maxlength="256"/>
		                                    </logic:notEqual>
		                                   <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.conceptCode"/>
		                                    </logic:equal>
		                                </td>
		                            </tr>
		                            <tr>
		                                <td class="fieldName"  id="Cname">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	<font class="boldTenRed" > * </font>
		                                </logic:notEqual><span>Concept Name:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <html:text title="Concept Name" property="codeValGnSelection.conceptNm" styleId="ValSNF" maxlength="256" size="50"/>
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.conceptNm"/>
		                                    </logic:equal>
		                                </td>
		                            </tr> 
		                            <tr>
		                                <td class="fieldName"  id="PCname">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	<font class="boldTenRed" > * </font>
		                                </logic:notEqual><span>Preferred Concept Name:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <html:text title="Preferred Concept Name" property="codeValGnSelection.conceptPreferredNm" styleId="ValPSNF" maxlength="256" size="50"/>
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.conceptPreferredNm"/>
		                                    </logic:equal>
		                                </td>
		                            </tr> 
		                            <tr>
		                                <td class="fieldName"  id="CSname">
		                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                                	<font class="boldTenRed" > * </font>
		                                </logic:notEqual><span>Code System Name:</span>
		                                </td>
		                                <td>
		                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
											
										<!-- jayasudha for enable the code system name drop down box
										          <html:text property="codeValGnSelection.codeSystemDescTxt" styleId="CodeSNF_TEXT" maxlength="100"/> -->
		                                        
		                                        <html:select title="Concept System Name" name="SRTAdminManageForm"  property="codeValGnSelection.codeSystemCd" styleId="CodeSNF_DD" acomplete="false" > 
									                 <nedss:optionsCollection property="codedValue(CODE_SYSTEM)" value="key" label="value"/>
									            </html:select>
		                                    </logic:notEqual>
		                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                                        <nedss:view name="SRTAdminManageForm" property="codeValGnSelection.codeSystemDescTxt"/>
		                                    </logic:equal>
		                                </td>
		                            </tr>
		                          </nedss:container>
	                            </fieldset>
                            </nedss:container>
                            
                            
                           
                            <!-- Bottom button bar -->
				          <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
			            	<div class="popupButtonBar">
					            <input type="button" name="submitA" id="submitA" value="Submit" onClick="submitDwrCodeset();"/>
					            <input type="submit" id="submitB" value="Cancel" onClick="return closeFormButton(true,event);"/>
					         </div>
				         </logic:equal>
				         <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
			            	<div class="popupButtonBar">
					             <input type="submit" name="submitA" id="submitA" value="Edit" onClick="editCalled=true;return editLoad();"/>
					            <input type="submit" id="submitB" value="Close" onClick="return closeForm(true);"/>
					        </div>
				         </logic:equal>
				         <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
			            	<div class="popupButtonBar">
					            <input type="button" name="submitA" id="submitA" value="Submit" onClick="submitDwrCreateCodeset();"/>
					            <input type="submit" id="submitB" value="Cancel" onClick="return closeFormButton(true,event);"/>
					         </div>
			         	</logic:equal>
            		</div>
         	</html:form>
        </div>
    </body>
</html>