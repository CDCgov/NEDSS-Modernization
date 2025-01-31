<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
    <head>
        <title>Search Laboratory Result Code</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>		  
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js"></script>				
        <script language="JavaScript">
            function manageLabResult()
            {
                if(LDLabResultSearchReqFlds()) {
                    return false;
                } else {
                    document.forms[0].action ="/nbs/ExistingResultsSnomedLink.do?method=labResultSearchSubmit";
                }
            }
            			
            function populateLabResult(cd)
            { 
                var parent = window.opener;
                var parentDoc = parent.document;					
                dwr.util.setValue(getElementByIdOrByNameNode("searchCriteria(LABRESULT)",parentDoc), cd);
                dwr.util.setValue(getElementByIdOrByNameNode("searchCriteria(SNOMED)",parentDoc), '');
                dwr.util.setValue(getElementByIdOrByNameNode("labResult",parentDoc), cd);
                self.close();
            }
            
            function populateLabId(labId)
            {
                var parent = window.opener;
                var parentDoc = parent.document;	
                dwr.util.setValue(getElementByIdOrByNameNode("searchCriteria(LABID)",parentDoc),labId);
                dwr.util.setValue(getElementByIdOrByNameNode("labId",parentDoc), labId);
                //JSRTForm.setSearchCriteria("LABID", cd);
                self.close();
            }
            
            function closePopup()
            {
				self.close();
				var rvct = getElementByIdOrByNameNode("blockparent", opener.document)
				rvct.style.display = "none";					
            }
            
            function unhideParent()
            {
	           var rvct = getElementByIdOrByNameNode("blockparent", opener.document)
	           rvct.style.display = "none";					
            }
            
			function bonload()
            {
                autocompTxtValuesForJSP();
                getElementByIdOrByName('srchLabResult').focus();			
			}	
		</script>
	</head>
    <body onload="bonload();">
        <div id="doc3">
            <div id="bd">
                <%@ include file="/jsp/popupUtil.jsp" %>
                <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
                <logic:notEmpty name="SRTAdminManageForm" property="attributeMap.NORESULT">
                    <table role="presentation" style="width: 100%;">
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
                
                <html:form action="/ExistingResultsSnomedLink.do">
                    <nedss:container id="section1" name="Find an existing locally defined result" classType="sect" displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(Lab Result Code or Lab Result Description is required to submit a search)</i></td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="resCode"><font class="boldTenRed" > * </font><span>Lab Result Code:</span></td>
                                    <td>
                                        <html:text title="Lab Result Code" styleId="srchLabResult" property="searchCriteria(LABTEST)" size="20" maxlength="20" />
                                    </td>
                                </tr>   		    
                                <tr>
                                    <td class="fieldName" id="resDesc"><font class="boldTenRed" > * </font><span>Lab Result Description:</span></td>
                                    <td>
                                        <html:select title="Lab Result Description" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>						 
                                        <html:text title="Lab Result Description" styleId="srchLabResultDesc" property="searchCriteria(RESULT_DESC)" size="25" maxlength="50"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="nameLab"><font class="boldTenRed" > * </font><span>Lab Name:</span></td>
                                    <td>
                                        <html:select title="Lab Name" property="searchCriteria(LABID)" styleId="srchLabId">
                                            <html:optionsCollection property="laboratoryIds" value="key" label="value"/>
                                        </html:select>					 
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return manageLabResult();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="closePopup();"/>			  
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
                    
                    <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList"> 
                        <nedss:container id="section2" name="Search Results" classType="sect" displayImg ="false" displayLink="false">
                            <fieldset style="border-width:0px;">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
	                                            <display:column property="viewLink" title="Lab Result Code" />
	                                            <display:column property="labResultDescTxt" title="Lab Result Description"/>
	                                            <display:column property="laboratoryId" title="Lab ID"/>
	                                            <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr> 
                                </table>
                            </fieldset>
                        </nedss:container>
                    </logic:notEmpty>
                </html:form>
            </div>
        </div>
    </body>
</html>