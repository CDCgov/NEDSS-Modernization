<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
    <head>
    <base target="_self">
    <title>Search for Coded Result</title>
        <%@ include file="/jsp/resources.jsp" %>	  
        <script language="JavaScript">  
        
		document.onkeypress=function(e){
			if(e.keyCode==13 && document.activeElement.name !="Cancel"){
    			findCodedResult();
				return false;
    		}
	}
    	
            function findCodedResult()
            {
                var index = 0;
                var errors = new Array();
                var isError = false;

                var codedResult  = getElementByIdOrByName("codedResult").value;
                var searchResultList  = getElementByIdOrByName("searchResultList").value;

                if(codedResult.length == 0 || searchResultList.length == 0)
                {
                    errors[index++] = "Please enter item to search on and try again.";
                    isError = true;
                }					
                
                if(isError) {
                    $j("#zeroResultsMsg").hide();
                    displayGlobalErrorMessage(errors);
                    return false;   
                }
                
                blockUiDuringFormSubmission();
                document.forms[0].action ="/nbs/LabResultedTestLink.do?method=codedResultSearchSubmit";
				document.forms[0].submit();				
            }

            function blockUiDuringFormSubmission()
    		{
    		    var saveMsg = '<div class="submissionStatusMsg"> <div class="header"> Loading Search Results </div>' +  
    		        '<div class="body"> <img src="saving_data.gif" alt="Saving data" title="Saving data" /> Your search results are being loaded. Please wait ... </div> </div>';         
    			$j.blockUI({  
    			    message: saveMsg,  
    			    css: {  
    			        top:  ($j(window).height() - 500) /2 + 'px', 
    			        left: ($j(window).width() - 500) /2 + 'px', 
    			        width: '500px'
    			    }  
    			});
    		}
            
            function closePopup()
            {
                self.close();
                if(opener!=null){
	                var block= getElementByIdOrByNameNode("blockparent", opener.document)
	                block.style.display = "none";
                }
            }
            
            function bonload()
            {
                autocompTxtValuesForJSP();
                getElementByIdOrByName('codedResult').focus();											
            }
            
		</script>
	</head>
    <body class="popup" onload="bonload();addRolePresentationToTabsAndSections();">
   
     <table role="presentation" id="container" height="500" width="760" >
    <tr>
    <td valign="top">
    	<div class="popupTitle">
            Search for Coded Result
        </div>
        
        <div class="popupButtonBar">
            <input type="button" id="Submit" name="Submit" value="Submit" onclick="return findCodedResult();"/>
			<input type="button" name="Cancel" value="Cancel" onclick="return closePopup();" />
        </div>
        
        <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
        <%@ include file="../../../jsp/errors.jsp" %>
        
        <!-- 0 search results message -->
        <logic:notEmpty name="labResultedTestForm" property="attributeMap.NORESULT">
            <table role="presentation" style="width:100%;">
                <tr>
                    <td>
                        <div class="infoBox messages" id="zeroResultsMsg">
                            Your Search Criteria resulted in 0 possible matches.
                            Please refine your search and try again.
                        </div>
                    </td>
                </tr>
            </table>
        </logic:notEmpty>
        
        <html:form action="/LabTestLoincLink.do">
       	<nedss:container id="subsec0" classType="subSect" displayImg="false">
        	<tr>
	        	<td class="fieldName" id="testName"><span>Coded Result:</span></td>
	            <td>				 
	            	<html:text title="Please enter all or part of a coded result description or the code associated with the description." styleId="codedResult" name="labResultedTestForm" property="searchCriteria(CODEDRESULT)" size="25" maxlength="100"/>
	            </td>
	        </tr>             
	        <tr>
				<td class="fieldName" id="queueAppLabel" valign="top">
					<span>Search:</span>
				</td>
				<td> 
					<input type="hidden" name="approval1" value="N">
					<table role="presentation">
						<tr>
						<td>
							<html:radio title="Short list, includes local coded results" name="labResultedTestForm" property="searchCriteria(CODESEARCHLIST)" styleId="searchResultList" value="LOCALRESULT" >Short list, includes local coded results</html:radio>
						</td>
						</tr>
						<tr>
						<td>
							<html:radio title="Long list, includes standard (SNOMED) coded results" name="labResultedTestForm" property="searchCriteria(CODESEARCHLIST)" styleId="searchResultList" value="SNOMEDRESULT" >Long list, includes standard (SNOMED) coded results</html:radio>
						</td>
						</tr>
					</table> 
				</td>
			</tr>
		</nedss:container>			  	
        </html:form>
    </td>
    </tr> 
    <tr>
    <td valign="bottom"> 
        <div class="popupButtonBar">
	        <input type="button" id="Submit" name="Submit" value="Submit" onclick="return findCodedResult();"/>
	        <input type="button" name="Cancel" value="Cancel" onclick="return closePopup();" />
	    </div>
	</td>
    </tr> 
    </table>
    </body>
</html>