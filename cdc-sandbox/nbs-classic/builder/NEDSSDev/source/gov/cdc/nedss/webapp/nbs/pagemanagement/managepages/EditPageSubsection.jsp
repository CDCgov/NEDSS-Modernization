<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants,gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT, gov.cdc.nedss.webapp.nbs.form.pagemanagement.managepage.PageElementForm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%
  String strPageTitle = (String)request.getAttribute("pageTitle");
  if(strPageTitle!=null && strPageTitle.contains("Add")){
    strPageTitle ="Add Subsection"; 
  }else{
    strPageTitle ="Edit Subsection";
  } 
%>
<html lang="en">
    <head>
    	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <base target="_self">
        <title>
            <logic:present name="windowTitle">
                <bean:write name="windowTitle" />
            </logic:present>
        </title>
        
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        
        <script type="text/javascript">
        
        	blockEnterKey();
        
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
            var unblock = true;
            
            function handleBatchPageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                 // get reference to opener/parent
                 
                    var opener = getDialogArgument();
                    
                    var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                    if (grayOverlay == null) {
                        grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                    }
                    grayOverlay.style.display = "none";
                 
                    if (e.clientY < 0 || closePopup == true) {

                     // pass the json to the parent
                     if(document.elementIdsForm)
                        opener.manageRepeatingBlockCancel(document.jsonStringForm.jsonString.value, document.elementIdsForm.elementIdsString.value);
                                                 
                        self.close();
                        return true;
                    }
                }
            }
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                    // get reference to opener/parent
				if(unblock){				
                    var opener = getDialogArgument();
                    
                    var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                    if (grayOverlay == null) {
                        grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                    }
                    grayOverlay.style.display = "none";
				}
                    if (e.clientY < 0 || closePopup == true) {
                        
                        self.close();
                        return true;
                    }
                }
            }
            
            function submitForm()
            {
            	
                var allEnabledSearchIpElts = $j("#editPageSubsectionBlock").find(':input:enabled');
                var errorMsgArray = new Array();
                
                for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
                    if ($j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'button'
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'select-one'  
                        && jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) == "") 
                    {
                        var fieldLabel = jQuery.trim($j($j($j($j(allEnabledSearchIpElts[i]).parents("tr")).get(0)).find("td.fieldName").get(0)).attr("title"));
                        var msg = fieldLabel + " is a required field." + "\n";
                        errorMsgArray.push(msg);
                    }
                }
                
                // verify the uniqueness of this name
                var opener = getDialogArgument();
                if (opener.isUniqueElementName('subsection', $j("#tabNameTd").val(), $j("#pageElementUid").val()) == false) {
                    errorMsgArray.push("Subsection name you entered already exists in the page. Please choose a different one.");
                }
                
                if(errorMsgArray != null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
                }
                else {
                	unblock=false;
                    document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=subsection&waQuestionUId=" + $j("#pageElementUid").val();
                    document.forms[0].submit();
                }
           }
           
           function handlePageOnload()
            {
                // focus on the first valid element
                $j("div#editPageSubsectionBlock").find(':input[type!=button]:visible:enabled:first').focus();
            }

           function displayElementProp(selectElt, index) {
               var keyval = index+"_Key";
               var key = getElementByIdOrByName(keyval).value;
               var propName = selectElt.options[selectElt.selectedIndex].value;
               var fieldset1 = "batchQuestionMap(" + key + ").batchTableHeader"; 
               var fieldset2 = "batchQuestionMap(" + key + ").batchTableColumnWidth"; 
               if (propName == 'N') {
                   document.forms[0].elements[fieldset1].disabled=true;
                   document.forms[0].elements[fieldset2].disabled=true;
                   getElementByIdOrByName("header_"+index).value="";
                   getElementByIdOrByName("width_"+index).value="";
                   isValidAggregateWidth();
               }
               else if (propName == 'Y') {
            	   document.forms[0].elements[fieldset1].disabled=false;
                   document.forms[0].elements[fieldset2].disabled=false;
              }

               var node = getElementByIdOrByName("select_"+index);  
           		node.style.textAlign = "left";  
           }

           function isValidAggregateWidth()
           {
               if(getElementByIdOrByName("a_tbox")){
	        	   var questionIds = $j("#queIds").attr("value");
	               var length = questionIds.split(",").length-1;
	               var mySplitResult = questionIds.split(",");
	               var totalWidth = 0*1;
	               
	               for(j=0; j<questionIds.split(",").length-1; j++) {
	            	   fieldset = "batchQuestionMap(" + mySplitResult[j] + ").batchTableColumnWidth"; 
	            	   //alert(fieldset);
	                  // if(document.forms[0].elements(fieldset).disabled==false)
	                	   totalWidth = totalWidth + (document.forms[0].elements[fieldset].value*1);
	               }
	
	               getElementByIdOrByName("a_tbox").value=totalWidth + "%";
               }
           }

           function repeatingBlockSubmitForm()
           {
               var errorMsgArray = new Array();
               
               var subsectionNm = getElementByIdOrByName("tabNameTd");
               if(subsectionNm != null && subsectionNm.value.length == 0){
            	   errorMsgArray.push("Sub Section is a required field."+ "\n"); 
               }
               
               var subsectionNm = getElementByIdOrByName("blockNameId");
               if(subsectionNm != null && subsectionNm.value.length == 0){
            	   errorMsgArray.push("Block Name is a required field."+ "\n"); 
               }


               if(subsectionNm){
           		if(subsectionNm!=null && subsectionNm!='undefined' && subsectionNm.value!=null && subsectionNm.value.match(/^\d/))
           		 errorMsgArray.push("Block Name cannot start with a number");
               }
               
               /* var subsectionNm = getElementByIdOrByName("dataMartRepeatNumberId");
               if(subsectionNm != null && subsectionNm.value.length == 0){
            	   errorMsgArray.push("Data Mart Repeat Number is a required field."+ "\n"); 
               } */


               var mapSize = '<%=request.getAttribute("mapSize")%>';
              // Check to see if at least one question with "Yes" in the dropdown 
               var isAllNo = true;
               for(var i = 0; i < mapSize; i++) 
               {
            	   if(getElementByIdOrByName("code_"+i).value == 'Y'){
            		   isAllNo = false; 
                   } 
            	   
               }
               if(isAllNo == true)
            	   errorMsgArray.push("Please enter at least one question with a 'Appears in Table?' value= 'Yes'."+ "\n");

               for(var i = 0; i < mapSize; i++) 
               {
            	                    
                   if(getElementByIdOrByName("header_"+i).disabled==false && getElementByIdOrByName("header_"+i) != null && getElementByIdOrByName("header_"+i).value.length == 0){
                	   if(!contains(errorMsgArray, "Label in Table is a required field."+ "\n"))
                		   errorMsgArray.push("Label in Table is a required field."+ "\n"); 
                   }
                   if(getElementByIdOrByName("width_"+i).disabled==false && getElementByIdOrByName("width_"+i) != null && getElementByIdOrByName("width_"+i).value.length == 0){
                	   if(!contains(errorMsgArray, "Table Column % is a required field."+ "\n"))
                	   		errorMsgArray.push("Table Column % is a required field."+ "\n"); 
                   }
                   if(getElementByIdOrByName("code_"+i) != null && getElementByIdOrByName("code_"+i).value.length == 0){
                	   if(!contains(errorMsgArray, "Appears in Table is a required field."+ "\n"))
                		   errorMsgArray.push("Appears in Table is a required field."+ "\n"); 
                   }
               }   
               
               if(getElementByIdOrByName("a_tbox").value != '100%')
               {
            	   errorMsgArray.push("The total of the 'Table column %' must calculate to 100%");  
               }
               // verify the uniqueness of this name
               var opener = getDialogArgument();
               if (opener.isUniqueElementName('subsection', $j("#tabNameTd").val(), $j("#pageElementUid").val()) == false) {
                   errorMsgArray.push("Subsection name you entered already exists in the page. Please choose a different one.");
               }
               
               if (opener.isUniqueElementName('blockName', $j("#blockNameId").val(), $j("#pageElementUid").val()) == false) {
                   errorMsgArray.push("Block Name you entered already exists in the page. Please choose a different one.");
               }
               

               if(errorMsgArray != null && errorMsgArray.length > 0){
                   displayGlobalErrorMessage(errorMsgArray);
               }
               else {
                  // alert("/nbs/ManagePageElement.do?method=editSubmit&eltType=subsection&waQuestionUId=" + $j("#pageElementUid").val());
                   document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=subsection&waQuestionUId=" + $j("#pageElementUid").val();
                   document.forms[0].submit();
               }
          }

           function contains(arr, value) {
        	    var i = arr.length;
        	    while (i--) {
        	        if (arr[i] === value) return true;
        	    }
        	    return false;
        	}

        function OnloadRepeatingBlock()
   		{
   			if(getElementByIdOrByName("a_tbox")){
   				
   				 var mapSize = '<%=request.getAttribute("mapSize")%>';
   	              for(var i = 0; i < mapSize; i++) 
   	              {
   	           	   if(getElementByIdOrByName("code_"+i).value == 'N'){ 
   	           		getElementByIdOrByName("code_"+i).value = 'N'
   	           		getElementByIdOrByName("width_"+i).disabled=true;
   	           		getElementByIdOrByName("header_"+i).disabled=true;
   	           		getElementByIdOrByName("header_"+i).value="";
                	getElementByIdOrByName("width_"+i).value="";
   	                } 
  	                // if question is invisible the appears Ind cd will be N and disable.
  	                if(getElementByIdOrByName("display_"+i).value == 'F')
  	                {
  	                	getElementByIdOrByName("code_"+i).value = 'N'
  	      				getElementByIdOrByName("code_"+i+"_textbox").disabled = true;
  	                	getElementByIdOrByName("code_"+i+"_button").disabled = true;
  	                	getElementByIdOrByName("code_"+i).disabled=true;
	     	           	getElementByIdOrByName("width_"+i).disabled=true;
	     	           	getElementByIdOrByName("header_"+i).disabled=true;
	     	           	getElementByIdOrByName("header_"+i).value="";
  	                  	getElementByIdOrByName("width_"+i).value="";
  	                }
   	              }
   			}
   		}

   		 
       </script>
        <style type="text/css">
		    table.aggregateDataEntryTable a {color:#000; text-decoration:none;}
		</style>
    </head>
    
    
    
    <logic:notEmpty name="batchType">
    	<body class="popup" onload="autocompTxtValuesForJSP();handlePageOnload();startCountdown();isValidAggregateWidth();OnloadRepeatingBlock();addRolePresentationToTabsAndSections();" onunload="handleBatchPageUnload(false, event); return false;" style="text-align:center;">   
    </logic:notEmpty>
    <logic:empty name="batchType">
    	<body class="popup" onload="autocompTxtValuesForJSP();handlePageOnload();startCountdown();isValidAggregateWidth();OnloadRepeatingBlock();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;" style="text-align:center;">
    </logic:empty>	
     <!-- Collection of form fields to edit a question -->
      
        
        
        
        <html:form action="/ManagePageElement.do?action=editSubmit">
            <html:hidden styleId="pageElementUid" property="pageEltVo.pageElementUid" />
            
            <logic:present name="pageTitle">
                <div class="popupTitle">
                    <bean:write name="pageTitle" />
                </div>
            </logic:present>
            
            <!-- Top button bar -->
            <div class="popupButtonBar">
                <logic:notEmpty name="manageMap">
	            	<input type="button" name="SubmitForm" value="Submit" onclick="repeatingBlockSubmitForm()"/>
	            </logic:notEmpty>
	            <logic:empty name="manageMap">
                	<input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
                </logic:empty>
                <logic:notEmpty name="batchType">
                	<input type="button" value="Cancel" onclick="handleBatchPageUnload(true, event)"></input>   
                </logic:notEmpty>
                <logic:empty name="batchType">
                	<input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>   
                </logic:empty>	         
            </div>
            
            <!-- Required Field Indicator -->
            <div style="text-align:right; width:100%; margin-top:0.5em;"> 
                <span style="color:#CC0000;"> * </span>
                <span style="color:black; font-style:italic;"> Indicates a Required Field </span>  
            </div>
            
            <!-- Error Messages using Action Messages-->
            <div id="globalFeedbackMessagesBar" class="screenOnly"> </div>
            
            
            
            <!-- Collection of form fields to edit a question -->
            <div id="editPageSubsectionBlock">
                <nedss:container id="sect_section" name="<%= strPageTitle %>" displayLink="false"  classType="sect" displayImg="false" includeBackToTopLink="no">
                    <nedss:container id="subsect_basicInfo" name="Subsection Details" classType="subSect" displayImg="false" includeBackToTopLink="no">
                     <tr>
                            <td title="Subsection Name" class="fieldName"> <span class="boldRed">*</span> Subsection Name: </td>
                            <td>
                                <html:text title="Subsection Name" property="pageEltVo.waUiMetadataDT.questionLabel" maxlength="300" size="40" styleId="tabNameTd" onkeyup="isSpecialCharEnteredForName(this,null,event)"/>
                            </td>
                        </tr>

                        <!--
                        <tr>
                            <td title="Secure Tab" class="fieldName"> Secure Tab <font class="boldTenRed" >*</font>: </td>
                            <td>
                                <html:radio property="pageEltVo.waUiMetadataDT.isSecured" value="T" /> Yes &nbsp;&nbsp;
                                <html:radio property="pageEltVo.waUiMetadataDT.isSecured" value="F"/> No
                            </td>
                        </tr>
                        -->
                        <tr>
                            <td title="Visible" class="fieldName"><span class="boldRed">*</span> Visible: </td>
                            <td>
                                <html:radio title="Visible Yes" property="pageEltVo.waUiMetadataDT.displayInd" value="T" /> Yes &nbsp;&nbsp;
                                <html:radio title="Visible No"  property="pageEltVo.waUiMetadataDT.displayInd" value="F"/> No
                            </td>
                        </tr>
                        
						
	                    <logic:notEmpty name="manageMap">
                        <tr>
                            <td title="Block Name is a meaningful name for the repeating block. All fields in this repeating block will share the same block name. This information is used in the creation of dynamic data marts in the reporting database." class="fieldName"> <span class="boldRed">*</span> Block Name: </td>
                            <td>
                                <html:text title="Block Name is a meaningful name for the repeating block. All fields in this repeating block will share the same block name. This information is used in the creation of dynamic data marts in the reporting database." property="pageEltVo.waUiMetadataDT.blockName" maxlength="21" size="40" styleId="blockNameId" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,40)"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td title="Data Mart Repeat Number is used to indicate the number of times the repeating block data should be pivoted in the creation of dynamic data marts in the reporting database. Valid values include 0-5. A value of 0 indicates that only columns with ALL data for a block element will appear in the data mart." class="fieldName"> Data Mart Repeat Number: </td>
                            <td>
                                <html:text title="Data Mart Repeat Number is used to indicate the number of times the repeating block data should be pivoted in the creation of dynamic data marts in the reporting database. Valid values include 0-5. A value of 0 indicates that only columns with ALL data for a block element will appear in the data mart." property="pageEltVo.waUiMetadataDT.dataMartRepeatNumber" maxlength="1" size="40" styleId="dataMartRepeatNumberId" onkeyup="dataMartRepeatNumber(this,null,event)"/>
                            </td>
                        </tr>
                        </logic:notEmpty>
						
                    </nedss:container>
                     <!-- batch Entry Edit question subsection -->
                      <logic:notEmpty name="manageMap">
                      <input type="hidden" id="queIds" value="<%= request.getAttribute("questionIdString") %>"/>
	                      <nedss:container id="subsect_repeatingBlock" name="Repeating block" classType="subSect" displayImg="false" includeBackToTopLink="no">
                      				<tr><td align="center">
		                      				 <logic:notEmpty name="manageMap">
								             <% if (request.getAttribute("batchMsg") != null) { %>
								             	<div style="text-align:left;width:100%;padding:3px; margin:0.25em 0.15em 0.5em 0.15em;">
											          <%=(String)request.getAttribute("batchMsg")%>
								                </div>
								             <% } %>
								             </logic:notEmpty>
                                          <table class="repeatingTable aggregateDataEntryTable"  style="width:98%;">
						                        <thead>
						                            <tr>
						                               <th style="width:35%;"><u> Question </u></th>
					                                   <th style="width:5%;"><u> Appears in Table? </u></th>
					                                   <th style="width:25%;"><u> Label in Table</u> </th>
					                                   <th style="width:35%;"><u> Table Column % </u></th>
						                            </tr>
						                        </thead>
					                        	<tbody>
					                        	<logic:iterate id="mList" name="manageMap" indexId="index" >
													  <tr>
													  	<input type="hidden" id="<%=index  + "_Key"%>" value="<bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).questionIdentifier" />" />
													  	<input type="hidden" id="<%="display_" + index%>" value="<bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).displayInd" />" />
														    <td width="35%"><bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).questionWithQuestionIdentifier" /></td>
														    <td width="35%" style="text-align:left" id="<%="select_" + index%>">
														    	<html:select title="Appears in Table" name="pageElementForm" property="batchQuestionMap(${mList.key}).batchTableAppearIndCdForJsp"  styleId='<%="code_"+index%>' onchange='<%="displayElementProp(this,"+ index +");"%>' style="text-align:left;" >
												                     <html:optionsCollection property="codedValue(YN)" value="key" label="value"/>
												                 </html:select>
														    </td>
														    <td width="35%"><html:text title="Label in Table" name="pageElementForm" property="batchQuestionMap(${mList.key}).batchTableHeader" value="${mList.value.batchTableHeader}" styleId='<%="header_"+index%>' maxlength="50"/></td>
														    <!--Add this for validation of width  onkeyup="return isValidAggregateWidth();" -->
														    <td width="35%"><html:text title="Table Column %" property="batchQuestionMap(${mList.key}).batchTableColumnWidth" value="${mList.value.batchTableColumnWidth}" styleId='<%="width_"+index%>' onkeyup="return isValidAggregateWidth();"/></td>
													  </tr>
												</logic:iterate>
												<tr><td width="65%" colspan="3" ></td><td height="25px" ><input title="Percentage" type="text" id="a_tbox" name="a_tbox" value="" readonly="readonly" style="border: 0px solid #000000;"/> </td></tr>
					                        </tbody>
				                     </table>
	                              </td></tr>        
	                        </nedss:container>
	                 </logic:notEmpty>
                </nedss:container>              
            </div>
            <!-- batch Entry Edit question subsection -->
            
            
            <!-- Bottom button bar -->
            <div class="popupButtonBar">
	            <logic:notEmpty name="manageMap">
	            	<input type="button" name="SubmitForm" value="Submit" onclick="repeatingBlockSubmitForm()"/>
	            </logic:notEmpty>
	            <logic:empty name="manageMap">
                	<input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
                </logic:empty>
                <logic:notEmpty name="batchType">
                	<input type="button" value="Cancel" onclick="handleBatchPageUnload(true, event)"></input>   
                </logic:notEmpty>
                <logic:empty name="batchType">
                	<input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>   
                </logic:empty>	       
            </div>
        </html:form>
        <logic:notEmpty name="manageMap">
        <form name="jsonStringForm">
            <input type="hidden" name="jsonString" value="<bean:write name="elementsJson"/>" />
        </form>
         <form name="elementIdsForm">
            <input type="hidden" name="elementIdsString" value="<bean:write name="elementIds"/>" />
        </form>
      </logic:notEmpty>
    </body>
</html>