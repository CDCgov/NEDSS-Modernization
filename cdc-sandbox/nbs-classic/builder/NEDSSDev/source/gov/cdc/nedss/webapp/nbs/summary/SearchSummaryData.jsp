<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.ArrayList,
                java.lang.Boolean,
                gov.cdc.nedss.webapp.nbs.form.summary.AggregateSummaryForm,
                gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil" %>
<jsp:useBean id="aggregateSummaryForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.summary.AggregateSummaryForm" />
<%
    ArrayList items = aggregateSummaryForm.getManageList();
    request.setAttribute("items", items);
%>

<html lang="en">
    <head>
        <title>Aggregate Reporting</title>
        <%@ include file="/jsp/resources.jsp" %>
    </head>
    
    <body onLoad="autocompTxtValuesForJSP();showCount();displayTooltips();startCountdown();">
        <div id="doc3">
            <!-- Body div -->
            <div id="bd">
                <!-- Top nav bar and top button bar -->
                <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
                <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>  

                <!-- Required Field Indicator -->
                <div class="requiredFieldIndicatorLink"> 
                    <span class="boldTenRed"> * </span>
                    <span class="boldTenBlack"> Indicates a Required Field </span>  
                </div>
                
                <div style="text-align:center;">
                    <html:form action="/AggregateReport.do?method=searchSummaryDataSubmit">
                        <!-- Search block -->
                        <nedss:container id="searchBlock" name="Search Aggregate Report" classType="sect" displayImg ="false" displayLink="false">
                            <nedss:container id="searchBlock_1" classType="subSect" displayImg ="false">
                                <!-- Reporting Area -->
                                <tr>
                                    <td class="fieldName">
                                        <span style="${aggregateSummaryForm.formFieldMap.SUM100.state.requiredIndClass}">*</span>
                                        <span id="SUM100LS" style="${aggregateSummaryForm.formFieldMap.SUM100.errorStyleClass}" 
                                                title="${aggregateSummaryForm.formFieldMap.SUM100.tooltip}">
                                            ${aggregateSummaryForm.formFieldMap.SUM100.label}
                                        </span> 
                                    </td>
                                    <td>
                                        <html:select title="Reporting Area" disabled ="${aggregateSummaryForm.formFieldMap.SUM100.state.disabled}" name="aggregateSummaryForm" property="searchMap(SUM100)" styleId = "SUM100S">
													<html:optionsCollection property="dwrDefaultStateCounties" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
						            <!-- MMWR Year -->
						            <tr>
						                <td class="fieldName">
						                    <span style="${aggregateSummaryForm.formFieldMap.SUM101.state.requiredIndClass}">*</span>
						                    <span id="SUM101LS" style="${aggregateSummaryForm.formFieldMap.SUM101.errorStyleClass}" 
						                            title="${aggregateSummaryForm.formFieldMap.SUM101.tooltip}">
						                        ${aggregateSummaryForm.formFieldMap.SUM101.label}
						                    </span> 
						                </td>
						                <td>
                                        <html:select title="MMRWR Year" disabled ="${aggregateSummaryForm.formFieldMap.SUM101.state.disabled}" name="aggregateSummaryForm" property="searchMap(SUM101)" styleId = "SUM101S" onchange="populateMMWRYear('SUM101S','SUM102S')">
                                              <html:optionsCollection property="mmwrYears" value="key" label="value"/>
                                        </html:select>
						                </td>
						            </tr>
						            <!-- MMWR Week -->
						            <tr>
						                <td class="fieldName">
						                    <!--span style="${aggregateSummaryForm.formFieldMap.SUM102.state.requiredIndClass}">*</span-->
						                    <span id="SUM102LS" style="${aggregateSummaryForm.formFieldMap.SUM102.errorStyleClass}" 
						                            title="${aggregateSummaryForm.formFieldMap.SUM102.tooltip}">
						                        ${aggregateSummaryForm.formFieldMap.SUM102.label}
						                    </span> 
						                </td>
						                <td>
                                        <html:select title="MMRWR Week" disabled ="${aggregateSummaryForm.formFieldMap.SUM102.state.disabled}" name="aggregateSummaryForm" property="searchMap(SUM102)" styleId = "SUM102S">
                                              <html:optionsCollection property="mmwrWeekList" value="key" label="value"/>
                                        </html:select>
						                </td>
						            </tr>                                
                                <!-- Condition -->
                                <tr>
                                    <td class="fieldName">
                                        <span style="${aggregateSummaryForm.formFieldMap.SUM106.state.requiredIndClass}">*</span>
                                        <span id="SUM106LS" style="${aggregateSummaryForm.formFieldMap.SUM106.errorStyleClass}" 
                                                title="${aggregateSummaryForm.formFieldMap.SUM106.tooltip}">
                                            ${aggregateSummaryForm.formFieldMap.SUM106.label}
                                        </span> 
                                    </td>
                                    <td>
                                        <html:select title="Condition" disabled ="${aggregateSummaryForm.formFieldMap.SUM106.state.disabled}" name="aggregateSummaryForm" property="searchMap(SUM106)" styleId = "SUM106S">
                                              <html:optionsCollection property="conditions" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align:right;">
                                        <input type="button" value="Search" onclick="return searchAggregateReport()"/> &nbsp;&nbsp;
                                        <input type="button" value="Clear" onclick="return clearSearchAggregateReportForm()"/>
                                    </td>
                                </tr>
                            </nedss:container>
                        </nedss:container>
                        
                        <!-- Search feedback bar -->
                        <logic:present name="zeroSearchResults">
	                            <div class="infoBox messages" style="margin-top:0.5em; text-align:left;">
	                                No aggregate report data currently exists for this criteria. 
                                   <logic:equal name="aggregateSummaryForm" property="attributeMap(addButton)" value="true">
		                                <a href="javascript:addReport()"> <u> Click Here </u> </a> to add a new aggregate report.
	                                </logic:equal>
	                            </div>                            
                        </logic:present>
                        <!-- Search results block -->
                        <logic:notEmpty name="aggregateSummaryForm" property="actionMode">
                            <logic:notEmpty name="aggregateSummaryForm" property="manageList">
                       			<html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
	                            <nedss:container id="searchResultsBlock" name="Search Results" classType="sect" displayImg ="false" displayLink="false">
	                                <nedss:container id="searchResultsBlock_1" classType="subSect" displayImg ="false">
                                        <tr>
                                            <td align="center">    
                                                <display:table name="items" class="dtTable" pagesize="10"  id="parent"  style="margin-top:0em;" requestURI="/AggregateReport.do?method=searchSummaryDataSubmit&existing=true" sort="external">
                                                    <display:column property="viewLink" title="<p style='display:none'>View</p>" style="width:3%;"/>
                                                    <display:column property="editLink" title="<p style='display:none'>Edit</p>"  style="width:3%;"/>
                                                    <display:column property="rptCntyCD" title="Reporting Area" sortable="true" sortName="getRptCntyCD" defaultorder="descending" style="width:13%;"/>
                                                    <display:column property="mmwrWeek" title="MMWR Week" sortable="true" sortName="getMmwrWeek" defaultorder="descending" style="width:18%;"/>
                                                    <display:column property="conditionCd" title="Condition" sortable="true" sortName="getConditionCd" defaultorder="descending" style="width:21%;"/>
                                                    <display:column property="caseCount" title="Case Count" sortable="true" sortName="getCaseCount" defaultorder="descending" style="width:10%;"/>
                                                    <display:column property="lastChgTIME_s" title="Last Updated" sortable="true" sortName="getLastChgTIME" defaultorder="descending" style="width:12%;"/>
                                                    <display:column property="statusCD" title="Status" sortable="true" sortName="getStatusCD" defaultorder="descending" style="width:10%;"/>
                                                    <display:column property="rptSentDate" title="Sent Date" sortable="true" sortName="getRptSentDate" defaultorder="descending" style="width:10%;"/>
                                                    <display:setProperty name="basic.empty.showtable" value="true"/>
                                                    <display:setProperty name="basic.empty.showtable" value="true"/>
                                                  </display:table>
                                            </td>
                                        </tr>
                                        <logic:equal name="aggregateSummaryForm" property="attributeMap(addButton)" value="true">
		                                    <tr>
		                                        <td style="text-align:right;">
		                                            <input type="button" id="createReportButton" 
		                                            value="Add New Report"
		                                            onclick="javascript:addReport()"  />
		                                        </td>
		                                    </tr>		                                    
	                                    </logic:equal>
	                                </nedss:container>
	                            </nedss:container>
                            </logic:notEmpty>
                        </logic:notEmpty>
                        <!-- Display the aggregate report part of the form -->                                         
                        <logic:present name="aggregateSummaryForm" property="actionMode">
		                         <%@ include file="AggregateReport.jsp" %>
                        </logic:present>   
                        <html:hidden styleId="sortSt" property="attributeMap.sortSt"/>
                    </html:form>                
                </div>
            </div> <!-- id=bd -->
        </div> <!-- id=doc3 -->
    </body>
</html>
<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JAggregateForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="AggregateSummarySpecific.js"></SCRIPT>
<script type="text/javascript">
    var formCode = "<%= NBSConstantUtil.INV_FORM_FLU %>"; 
    
    function editForm(link, notificationExists) {
				if(notificationExists =='true'){
					var confirmMsg="An NND notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
					if (confirm(confirmMsg)) {
				        document.aggregateSummaryForm.action =link;
				        document.aggregateSummaryForm.submit();
				        return false;
					}
				} else {
				        document.aggregateSummaryForm.action =link;
				        document.aggregateSummaryForm.submit();
				        return false;				
				}
    }
    
	function showCount() {
		$j(".pagebanner b").each(function(i){ 
			$j(this).append(" of ").append($j("#queueCnt").attr("value"));
		});
		$j(".singlepagebanner b").each(function(i){ 
			var cnt = $j("#queueCnt").attr("value");
			if(cnt > 0)
				$j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
		});				
	}
			    
    /** Validate the form by checking for required fields */
    function validateSearchForm()
	{
	    $j(".infoBox").hide();

	    var errors = new Array();
	    var index = 0;
	    var isValid = true;
	    
	    var fieldIds = ["SUM100","SUM101","SUM106"];
	    var fieldNames = ["Reporting Area", "MMWR Year", "Condition"];
	    for (var i = 0; i < fieldIds.length; i++) {
	       if (jQuery.trim($j("#" + fieldIds[i] + "S").val()) == "") {
	           isValid = false;
	           
	           // add to errors array
	           errors[index++] = fieldNames[i] + " is required";
	           
	           // highlight the field label to red
	           $j("#" + fieldIds[i] + "LS").css("color", "#C00");
	       }
	       else {
	           // highlight the field label to black
               $j("#" + fieldIds[i] + "LS").css("color", "#000");
	       }
	    }
	    
	    if(isValid == false) {
	        displayGlobalErrorMessage(errors);
	    }
	    
	    return isValid;
    }
    
    /** Clear the Search Aggregate Report Form. Clear only the non-editable fields */
    function clearSearchAggregateReportForm() {
        var allEnabledSearchIpElts = $j("#searchBlock").find(':input:enabled');
        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
            if ($j(allEnabledSearchIpElts[i]).attr("type") != 'button') {
                $j(allEnabledSearchIpElts[i]).val("");
            }
        }
        populateMMWRYear('SUM101S','SUM102S');
        return false;
    }

    /** Search for report aggregate data */ 
    function searchAggregateReport() {
        if (validateSearchForm() == true) 
        {

	        document.aggregateSummaryForm.action ="/nbs/AggregateReport.do?method=searchSummaryDataSubmit#searchResultsBlock";
	        document.aggregateSummaryForm.submit();
	        return false;
        }
    }
    
    /** Display a screen where user can enter details for a new aggregate report and save it */
    function addReport()
    {
        window.location = "/nbs/AggregateReport.do?method=createLoadSummaryData&invFormCd=" + formCode + "&existing=true#default";
    }
    
    $j(document).ready(function() {
        // focus on the first valid element        
        var currentActionMode = '${fn:escapeXml(aggregateSummaryForm.actionMode)}';
        if (currentActionMode != "<%= NEDSSConstants.VIEW %>") {
            $j("body").find(':input:visible:enabled:first').focus();
        }
    });
</script>
		<style type="text/css">
			.multiSelect {
			margin-left:3px;
			margin-right:0px;
			float: right;
			}
		</style>