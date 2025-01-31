<%@ include file="/jsp/tags.jsp" %>
<html lang="en">
    <head>
        <title>Search SNOMED Code</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>		  
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js"></script>				
        <script type="text/javascript">
            function findComponent()
            {
                var errors = new Array();
                var index = 0;
                var isError = false;
                					
                var cd  = getElementByIdOrByName("srchSnomed").value;
                var desc  = getElementByIdOrByName("snomedCd").value;
                						
                if(cd.length == 0 && desc.length == 0)
                {	
                    errors[index++] = "SNOMED Code or SNOMED Description is required";
                    getElementByIdOrByName("snomedCode").style.color="#CC0000";							
                    getElementByIdOrByName("descS").style.color="#CC0000";
                    isError = true;
                }
                else {
                    document.forms[0].action ="/nbs/ExistingResultsSnomedLink.do?method=snomedSearchSubmit";
                }

                if (isError) {
                    displayGlobalErrorMessage(errors);
                    return false;
                }
            }

            function populateSnomed(cd)
            { 
                var parent = window.opener;
                var parentDoc = parent.document;
                parentDoc.getElementById("snomed").innerHTML = cd;	
                parentDoc.getElementById("snomed1") == null ? "" : parentDoc.getElementById("snomed1").innerHTML = cd;	
                JSRTForm.setSearchCriteria("SNOMED", cd);
                dwr.util.setValue(getElementByIdOrByNameNode("searchCriteria(SNOMED)",parentDoc), cd);
                dwr.util.setValue(getElementByIdOrByNameNode("snomed",parentDoc), cd);
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
                getElementByIdOrByName('srchSnomed').focus();					
            }
		</script>
	</head>
    <body  onload="bonload();">
        <div id="doc3">
            <div id="bd">
                <%@ include file="/jsp/popupUtil.jsp" %>
                <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
                
                <logic:notEmpty name="SRTAdminManageForm" property="attributeMap.NORESULT">
                    <table role="presentation">
                        <tr>
                            <td align="center">
                                <div class="infoBox messages" id="zeroResultsMsg">
                                    Your Search Criteria resulted in 0 possible matches.
                                    Please refine your search and try again.
                                </div>
                            </td>
                        </tr>
                    </table>
                </logic:notEmpty>
                    
                <html:form action="/ExistingResultsSnomedLink.do">
	               <nedss:container id="section3" name="Find an existing SNOMED Code" classType="sect" displayImg ="false" displayLink="false">
		              <fieldset style="border-width:0px;" >
		                  <nedss:container id="subSect3" classType="subSect" displayImg ="false">
				                <tr>
				                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(SNOMED Code or SNOMED Description is required to submit a search)</i></td>
				                </tr>   		    
                                <tr>
                                    <td class="fieldName" id="snomedCode"><font class="boldTenRed" > * </font><span>SNOMED Code:</span></td>
                                    <td>
                                        <html:text title="SNOMED Code" styleId="srchSnomed" property="searchCriteria(SNOMED_CD)" maxlength="20"/>
                                    </td>
                                </tr>      		    
                                <tr>
                                    <td class="fieldName" id="descS"><font class="boldTenRed" > * </font><span>SNOMED Description:</span></td>
                                    <td>
                                        <html:select title="SNOMED Description" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>						 
                                        <html:text title="SNOMED Description" styleId="snomedCd" property="searchCriteria(SNOMED)" size="25" maxlength="100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return findComponent();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="closePopup();"/>			  
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>  
                        </fieldset>
                    </nedss:container>
                      
                    <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList"> 
                        <nedss:container id="section4" name="Search Results" classType="sect" displayImg ="false" displayLink="false">
                            <fieldset style="border-width:0px;" >
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
	                                            <display:column property="selectLink" title="SNOMED Code" />
	                                            <display:column property="snomedDescTx" title="SNOMED Description" />
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