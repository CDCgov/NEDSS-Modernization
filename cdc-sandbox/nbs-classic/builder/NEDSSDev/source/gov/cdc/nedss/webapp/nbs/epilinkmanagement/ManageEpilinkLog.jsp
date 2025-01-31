<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.ArrayList" %>
<html lang="en">
<head>
<title>NBS: Manage  Epi-link Activity Logs</title>
<%@ include file="/jsp/resources.jsp"%>

<SCRIPT Language="JavaScript"
	Src="/nbs/dwr/interface/JEpiLinkLogForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css" />

<style type="text/css">
div.messages {
	background: #E4F2FF;
	color: #000;
	padding: 0.5em;
	border-width: 1px 1px 1px 1px;
	border-color: #7AA6D5;
	border-style: solid;
	font-size: 95%;
}

div.popupButtonBar {
	text-align: right;
	width: 100%;
	background: #EEE;
	border-bottom: 1px solid #DDD;
}

.removefilter {
	background-color: #003470;
	width: 100%;
	height: 25px;
	line-height: 25px;
	float: right;
	text-align: right;
}

removefilerLink {
	vertical-align: bottom;
}

.hyperLink {
	font-size: 10pt;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	color: #FFFFFF;
	text-decoration: none;
}
</style>

<script type="text/javascript">


function onKeyUpValidate(){      	  
	   if(getElementByIdOrByName("SearchText2").value != ""){
		getElementByIdOrByName("b1SearchText2").disabled=false;
		getElementByIdOrByName("b2SearchText2").disabled=false;
	   }else if(getElementByIdOrByName("SearchText2").value == ""){
		getElementByIdOrByName("b1SearchText2").disabled=true;
		getElementByIdOrByName("b2SearchText2").disabled=true;
	   }
}

function searchActivityLog()
{
	
	document.forms[0].action ="/nbs/EpiLinkLogAdmin.do?method=searchEpilinkLogSubmit&initLoad=true";
	
}	
function returnToManage()
{
    var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
    if (confirm(confirmMsg)) {
        document.forms[0].action ="/nbs/SystemAdmin.do?focus=systemAdmin7";
    } else {
        return false;
    }	      	
}

//the following code for Queue
function attachIcons() {
    $j("#parent thead tr th a").each(function(i) 
    {
 	   if($j(this).html() == 'Processed Date' ) {
            $j(this).parent().append($j("#lprocessedDate"));
        }
 	   
        if($j(this).html() == 'User Name' ) {
            $j(this).parent().append($j("#luserName"));
        }
          
        if($j(this).html() == 'Old Epi-Link Id') {
            $j(this).parent().append($j("#loldEpiLink"));
        }
        if($j(this).html() == 'New Epi-Link Id') {
            $j(this).parent().append($j("#lnewEpiLink"));
        }
        
    });
    
    $j("#parent").before($j("#whitebar"));
    $j("#parent").before($j("#removeFilters"));
}

function makeMSelects() {
    $j("#lprocessedDate").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	$j("#loldEpiLink").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	$j("#lnewEpiLink").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});

	$j("#luserName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
   
}

function showCount() {
	$j(".pagebanner b").each(function(i) {
		$j(this).append(" of ").append($j("#queueCnt").attr("value"));
	});
	$j(".singlepagebanner b").each(
			function(i) {
				var cnt = $j("#queueCnt").attr("value");
				if (cnt > 0)
					$j(this).append(" Results 1 to ").append(cnt).append(
							" of ").append(cnt);
			});
}


function displayTooltips() {
	$j(".sortable a").each(
			function(i) {
				var headerNm = $j(this).html();
				var sortSt = getElementByIdOrByName("sortSt") == null ? ""
						: getElementByIdOrByName("sortSt").value;
				var EPL102 = getElementByIdOrByName("EPL102") == null ? ""
						: getElementByIdOrByName("EPL102").value;
				var EPL103 = getElementByIdOrByName("EPL103") == null ? ""
						: getElementByIdOrByName("EPL103").value;
				var EPL104 = getElementByIdOrByName("EPL104") == null ? ""
						: getElementByIdOrByName("EPL104").value;

				var EPL105 = getElementByIdOrByName("SearchText2") == null ? ""
						: getElementByIdOrByName("SearchText2").value;
		
				
					  
				 if (headerNm == 'Processed Date') {
					_handleTemplate(headerNm, $j(this), EPL102);
				} else if (headerNm == 'User Name') {
					_setAttributes(headerNm, $j(this), EPL105);
				} else if (headerNm == 'Old Epi-Link Id') {
					_handleTemplate(headerNm, $j(this), EPL103);
				} else if (headerNm == 'New Epi-Link Id') {
					_handleTemplate(headerNm, $j(this), EPL104);
				} 
			});
}

function _handleTemplate(headerNm, link, colId)
{
 	var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
 	var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
 	
 	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
 	if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
 		if(sortSt != null && sortSt.indexOf("descending") != -1) {
 			link.after(htmlDesc);
 		} else {
 			link.after(htmlAsc);
 		}
 	}
}

function _setAttributes(headerNm, link, colId) {
	var imgObj = link.parent().find("img");
	var toolTip = "";
	//var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();  
	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : document
			.getElementById("sortSt").value;
	var orderCls = "SortAsc.gif";
	var altOrderCls = "Sort Ascending";
	var sortOrderCls = "FilterAndSortAsc.gif";
	var altSortOrderCls = "Filter Applied with Sort Ascending";
	
	if (sortSt != null && sortSt.indexOf("descending") != -1) {
		orderCls = "SortDesc.gif";
		altOrderCls = "Sort Descending";
		sortOrderCls = "FilterAndSortDesc.gif";
		altSortOrderCls = "Filter Applied with Sort Descending";
		
	}
	var filterCls = "Filter.gif";
	var altFilterCls = "Filter Applied";
	toolTip = colId == null ? "" : colId;
	if (colId != null && colId.length > 0) {
		link.attr("title", toolTip);
		imgObj.attr("src", filterCls);
		imgObj.attr("alt", altFilterCls);
		if (sortSt != null && sortSt.indexOf(headerNm) != -1) {
			imgObj.attr("src", sortOrderCls);
			imgObj.attr("alt", altSortOrderCls);		
			
		}
	} else {
		if (sortSt != null && sortSt.indexOf(headerNm) != -1) {
			imgObj.attr("src", orderCls);
			imgObj.attr("alt", altOrderCls);
			
		}
	}
}

function cancelFilter(key) {
	key1 = key.substring(key.indexOf("(") + 1, key.indexOf(")"));
	JEpiLinkLogForm.getAnswerArray(key1, function(data) {
		revertOldSelections(key, data);
	});
}

function revertOldSelections(name, value) {
	if (value == null) {
		$j("input[@name=" + name + "][type='checkbox']").attr('checked',
				true);
		$j("input[@name=" + name + "][type='checkbox']").parent().parent()
				.find('INPUT.selectAll').attr('checked', true);
		return;
	}

	//step1: clear all selections
	$j("input[@name=" + name + "][type='checkbox']").attr('checked', false);
	$j("input[@name=" + name + "][type='checkbox']").parent().parent()
			.find('INPUT.selectAll').attr('checked', false);

	//step2: check previous selections from the form
	for ( var i = 0; i < value.length; i++) {
		$j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr(
				'checked', true);
	}
	//step3: if all are checked, automatically check the 'select all' checkbox
	if (value.length == $j("input[@name=" + name + "][type='checkbox']")
			.parent().length)
		$j("input[@name=" + name + "][type='checkbox']").parent().parent()
				.find('INPUT.selectAll').attr('checked', true);
}


function selectfilterCriteria() {
	document.forms[0].action = '/nbs/EpiLinkLogAdmin.do?method=filterActivityLogSubmit';
	document.forms[0].submit();
}

function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
 function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}

blockEnterKey();
</script>

<logic:notEmpty name="epiLinkLogForm"
	property="attributeMap.SEARCHRESULT">
	<body
		onload="attachIcons();makeMSelects();showCount();displayTooltips();startCountdown();">
</logic:notEmpty>
<logic:empty name="epiLinkLogForm"
	property="attributeMap.SEARCHRESULT">
	<body>
</logic:empty>

<div id="blockparent"></div>
<div id="doc3">
	<div id="bd">
		<!-- Top Page Nav -->
		<%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>
		<table role="presentation" style="width: 100%;">
			<tr>
				<td style="text-align: right" id="srtLink"><a id="manageLink"
					href="/nbs/SystemAdmin.do?focus=systemAdmin7"> Return to System
						Management Main Menu </a> 
				</td>
			</tr>
			<tr>
				<td align="right" style="padding-top: 5px;"><i> <font
						class="boldTenRed"> * </font><font class="boldTenBlack">Indicates
							a Required Field </font> </i>
				</td>
			</tr>
		</table>
		<!-- Page Errors -->
		<logic:messagesPresent name="error_messages">
			<div class="infoBox errors" id="errorMessages">
				<ul>
					<html:messages id="msg" name="error_messages">
						<li><bean:write name="msg" />
						</li>
					</html:messages>
					<ul>
			</div>
		</logic:messagesPresent>

		<html:form>
			<nedss:container id="section0" name="Activity Log Filter Selection"
				classType="sect" includeBackToTopLink="false" displayImg="false"
				displayLink="false">
				<fieldset style="border-width: 0px;" id="search">
					<nedss:container id="subsec0" classType="subSect"
						displayImg="false">
						<tr>
							<td align="left"
								style="padding-top: 5px; padding-left: 7px; font-weight: bold">Time
								Frame of Event Record Processing</td>
							<td align="right"></td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><font class="boldTenRed">
									* </font><span>From:</span>
							</td>
							<td><html:text name="epiLinkLogForm"
									property="activityLogClientVO.processingDateFrom"
									styleId="from_date" size="20" maxlength="10"
									title="Time Frame of Event Record Processing from" 
									onkeyup="DateMask(this,null,event)" /> <html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('from_date','from_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('from_date','from_dateIcon', event);"
									styleId="from_dateIcon"></html:img>
							</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><span></span>
							</td>
							<td>mm/dd/yyyy</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><font class="boldTenRed">
									* </font><span>To:</span>
							</td>
							<td><html:text name="epiLinkLogForm"
									property="activityLogClientVO.processingDateTo"
									styleId="to_date" size="20" maxlength="10"
									title="Time Frame of Event Record Processing To" 
									onkeyup="DateMask(this,null,event)" /> <html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('to_date','to_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('to_date','to_dateIcon', event);"
									styleId="to_dateIcon"></html:img> 
							</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><span></span>
							</td>
							<td>mm/dd/yyyy</td>
						</tr>
					<tr>
							<td align="left"></td>
							<td align="right" class="InputField"><input type="submit"
								name="search" id="searchButton" value="Search"
								onClick="return searchActivityLog();" /> <input type="submit"
								name="cancel" id="cancelButton" value="Cancel"
								onClick="return returnToManage();" /> &nbsp;</td>
						</tr>
						
					</nedss:container>
				</fieldset>
			</nedss:container>

	
	
	<!-- search results -->
	<logic:notEmpty name="epiLinkLogForm"
				property="attributeMap.SEARCHRESULT">

				<tr>
					<td>&nbsp;</td>
				</tr>
				<!-- Top button bar -->
<div class="grayButtonBar">
					<table role="presentation" width="100%">
						<tr>
							<td align="left">
								<!-- <input type="button" name="View Import/Export Activity Log" style="width: 190px" value="View Import/Export Activity Log" onclick="viewPageHistoryPopUp()"/>
							   	--> &nbsp;</td>
							<td align="right"><input type="button" value="Print" id=" "
								onclick="printQueue();" /> <input type="button"
								value="Download" id=" " onclick="exportQueue();" />
							</td>
						</tr>
					</table>
				</div>
			
				<nedss:container id="section1" name="Activity Log Search Results"
					classType="sect" displayImg="false" displayLink="false"
					includeBackToTopLink="false">
					<fieldset style="border-width: 0px;" id="result">
						<html:hidden styleId="queueCnt" property="attributeMap.queueCount" />
						<div id="whitebar"
							style="background-color: #FFFFFF; width: 100%; height: 1px;"
							align="right"></div>
						<div class="removefilter" id="removeFilters">
						<html:link href="/nbs/EpiLinkLogAdmin.do?method=searchEpilinkLogSubmit&initLoad=true"><font
								class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font>
							</html:link >
						</div>
	
					
					
						<table role="presentation" width="98%">
							<tr>
								<td align="center"><display:table name="epilinkList"
										class="dtTable" style="margin-top:0em;" id="parent"
										pagesize="${epiLinkLogForm.attributeMap.queueSize}"
										requestURI="/EpiLinkLogAdmin.do?method=searchEpilinkLogSubmit&existing=true"
										sort="external" export="true"
										 >
										<display:setProperty name="export.csv.filename"
											value="EpilinkActivityLog.csv" />
										<display:setProperty name="export.pdf.filename"
											value="EpilinkActivityLog.pdf" />
										<display:column property="processedDate" title="Processed Date"
											media="html" format="{0,date,MM/dd/yyyy}"
											sortable="true" sortName="getProcessedDate"
											defaultorder="descending" style="width:20%;" />
										<display:column property="name"
											title="User Name" sortable="true"
											sortName="getUserName"
											 defaultorder="ascending"
											style="width:20%;" />	
											<display:column property="oldEpilinkId"  
											title="Old Epi-Link Id" sortable="true"
											sortName="getOldEpilinkId"
											 defaultorder="ascending"
											style="width:20%;" />
											<display:column property="newEpilinkId"
											title="New Epi-Link Id" sortable="true"
											sortName="getNewEpilinkId"
											 defaultorder="ascending"
											style="width:20%;" />
											<display:column property="investigationsString"
											title="Investigations Modified" 
											style="width:20%;" />
											
								
										
										<display:setProperty name="basic.empty.showtable" value="true" />
									</display:table></td>
							</tr>
						</table>
					</fieldset>
					</nedss:container>	
					
					<div class="grayButtonBar">
					<table role="presentation" width="100%">
						<tr>
							<td align="left">
								<!-- <input type="button" name="View Import/Export Activity Log" style="width: 190px" value="View Import/Export Activity Log" onclick="viewPageHistoryPopUp()"/>
							   	--> &nbsp;</td>
							<td align="right"><input type="button" value="Print" id=" "
								onclick="printQueue();" /> <input type="button"
								value="Download" id=" " onclick="exportQueue();" />
							</td>
						</tr>
					</table>
				</div>					
  
<html:select property="answerArray(PROCESSEDDATE)" styleId="lprocessedDate"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="processedDate" value="key"
			label="value" style="width:180" />
	</html:select>
	<html:select property="answerArray(OLDEPILINK)" styleId="loldEpiLink"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="oldEpiLink" value="key" label="value" />
	</html:select>
	<html:select property="answerArray(NEWEPILINK)"
		styleId="lnewEpiLink" onchange="selectfilterCriteria()"
		multiple="true" size="4" style="width:180">
		<html:optionsCollection property="newEpiLink" value="key"
			label="value" />
	</html:select>
	<html:select property="answerArray(SearchText2)" styleId="luserName"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	
	
<html:hidden styleId="sortSt" name="epiLinkLogForm"
		property="attributeMap.searchCriteria.sortSt" />
	<html:hidden styleId="EPL102" name="epiLinkLogForm"
		property="attributeMap.searchCriteria.EPL102" />
	<html:hidden styleId="EPL103" name="epiLinkLogForm"
		property="attributeMap.searchCriteria.EPL103" />
	<html:hidden styleId="EPL104" name="epiLinkLogForm"
		property="attributeMap.searchCriteria.EPL104" />
	<html:hidden styleId="SearchText2" name="epiLinkLogForm"
		property="attributeMap.searchCriteria.EPL105" />
	
	
	</logic:notEmpty>
	
	</html:form>
	<%@ include file="/jsp/footer.jsp"%>
</div>
</div>


</body>
</html>