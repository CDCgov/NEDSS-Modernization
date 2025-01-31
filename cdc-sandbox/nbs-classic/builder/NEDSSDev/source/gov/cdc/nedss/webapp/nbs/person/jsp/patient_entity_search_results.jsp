<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<html lang="en">
<head>
<base target="_self">
<title>NBS: Search Results</title>

<%@ include file="/jsp/resources.jsp"%>
<SCRIPT Language="JavaScript"
	Src="/nbs/dwr/interface/JPersonSearchForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="searchResultsSpecific.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
            
        function showCount()
        {
        
             $j(".pagebanner b").each(function(i){ 
                 $j(this).append(" of ").append($j("#queueCnt").attr("value"));
             });
             $j(".singlepagebanner b").each(function(i){ 
                 var cnt = $j("#queueCnt").attr("value");
                
                 if(cnt > 0)
                     $j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
             });             
        } 

        function getPage(target)
    	{  	
    	    document.forms[0].action =target;
    	 	document.forms[0].submit();
    	}
        
        function selectPatient(mprUid){
        	var opener = getDialogArgument();
        	opener.populatePatient(mprUid);
        	closePopup();
        	
        }
        
        function closePopup()
        {              
                self.close();
                var opener = getDialogArgument();          
                var invest = getElementByIdOrByNameNode("pageview", opener.document);
                if (invest != null) {
                     invest.style.display = "none";  
                }                   
            
        }  

        function cancelFilter(key) {                    
        		key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
        		JPersonSearchForm.getAnswerArray(key1, function(data) {			  			
        			revertOldSelections(key, data);
        		});
        }
        
        function revertOldSelections(name, value) 
        {  
            if(value == null) {
                $j("input[@name="+name+"][type='checkbox']").attr('checked', true);
                $j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
                return;
            }

            //step1: clear all selections
            $j("input[@name="+name+"][type='checkbox']").attr('checked', false);
            $j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', false);

            //step2: check previous selections from the form
            for(var i=0; i<value.length; i++) {
                   $j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr('checked', true);
               }
            //step3: if all are checked, automatically check the 'select all' checkbox
            if(value.length == $j("input[@name="+name+"][type='checkbox']").parent().length)
                $j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
        }
        
        function onKeyUpValidate(){
        	if(getElementByIdOrByName("SearchText1").value != ""){
          		getElementByIdOrByName("b1SearchText1").disabled=false;
          		getElementByIdOrByName("b2SearchText1").disabled=false;
          	   }else if(getElementByIdOrByName("SearchText1").value == ""){
          		getElementByIdOrByName("b1SearchText1").disabled=true;
          		getElementByIdOrByName("b2SearchText1").disabled=true;
          	   }
          	   if(getElementByIdOrByName("SearchText2").value != ""){
          		getElementByIdOrByName("b1SearchText2").disabled=false;
          		getElementByIdOrByName("b2SearchText2").disabled=false;
          	   }else if(getElementByIdOrByName("SearchText2").value == ""){
          		getElementByIdOrByName("b1SearchText2").disabled=true;
          		getElementByIdOrByName("b2SearchText2").disabled=true;
          	   }
          	 if(getElementByIdOrByName("SearchText3").value != ""){
           		getElementByIdOrByName("b1SearchText3").disabled=false;
           		getElementByIdOrByName("b2SearchText3").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText3").value == ""){
           		getElementByIdOrByName("b1SearchText3").disabled=true;
           		getElementByIdOrByName("b2SearchText3").disabled=true;
           	   }
            if(getElementByIdOrByName("SearchText4").value != ""){
           		getElementByIdOrByName("b1SearchText4").disabled=false;
           		getElementByIdOrByName("b2SearchText4").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText4").value == ""){
           		getElementByIdOrByName("b1SearchText4").disabled=true;
           		getElementByIdOrByName("b2SearchText4").disabled=true;
           	   }
            if(getElementByIdOrByName("SearchText5").value != ""){
           		getElementByIdOrByName("b1SearchText5").disabled=false;
           		getElementByIdOrByName("b2SearchText5").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText5").value == ""){
           		getElementByIdOrByName("b1SearchText5").disabled=true;
           		getElementByIdOrByName("b2SearchText5").disabled=true;
           	   }
            if(getElementByIdOrByName("SearchText6").value != ""){
           		getElementByIdOrByName("b1SearchText6").disabled=false;
           		getElementByIdOrByName("b2SearchText6").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText6").value == ""){
           		getElementByIdOrByName("b1SearchText6").disabled=true;
           		getElementByIdOrByName("b2SearchText6").disabled=true;
           	   }
		}

        function makeMSelects() {
            $j("#fullName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#telephone").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#idSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#patientIdSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#ageSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#addressSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#patient").text({actionMode: '${fn:escapeXml(ActionMode)}'});	
           }
        function attachIcons() {    
            $j("#searchResultsTable thead tr th a").each(function(i) {  
                if($j(this).html() == 'Name'){
                    $j(this).parent().append($j("#fullName"));                    
                } 
                if($j(this).html() == 'Phone/Email'){
                    $j(this).parent().append($j("#telephone"));                    
                } 
                if($j(this).html() == 'ID'){
                    $j(this).parent().append($j("#idSearch"));                    
                } 
                 if($j(this).html() == 'Patient ID'){
                    $j(this).parent().append($j("#patientIdSearch"));                    
                }  
                 if($j(this).html() == 'Age/DOB/Sex'){
                     $j(this).parent().append($j("#ageSearch"));                    
                 } 
                  if($j(this).html() == 'Address'){
                     $j(this).parent().append($j("#addressSearch"));                    
                 }  
                  if($j(this).html() == 'Patient'){
                      $j(this).parent().append($j("#patient"));                    
                  }  
				});
            $j("#searchResultsTable").before($j("#whitebar"));
            $j("#searchResultsTable").before($j("#removeFilters"));
           
        }

        function displayTooltips() {        
            $j(".sortable a").each(function(i) {
            
                var headerNm = $j(this).html();
                var fullNameSearch = getElementByIdOrByName("SearchText1") == null ? "" : getElementByIdOrByName("SearchText1").value;
                var telePhoneSearch  = getElementByIdOrByName("SearchText2") == null ? "" : getElementByIdOrByName("SearchText2").value;
                var idSearch  = getElementByIdOrByName("SearchText3") == null ? "" : getElementByIdOrByName("SearchText3").value;
                var patientIdSearch  = getElementByIdOrByName("SearchText4") == null ? "" : getElementByIdOrByName("SearchText4").value;
                var ageSearch  = getElementByIdOrByName("SearchText5") == null ? "" : getElementByIdOrByName("SearchText5").value;
                var addressSearch  = getElementByIdOrByName("SearchText6") == null ? "" : getElementByIdOrByName("SearchText6").value;
                
                 if(headerNm =='Name') {                
                	 _setAttributes(headerNm, $j(this), fullNameSearch);
                  }
                else if(headerNm =='Phone/Email') {                
               	 _setAttributes(headerNm, $j(this), telePhoneSearch);
                 }
                else if(headerNm =='ID') {                
                  	 _setAttributes(headerNm, $j(this), idSearch);
                    }
                else if(headerNm =='Patient ID') {                
                  	 _setAttributes(headerNm, $j(this), patientIdSearch);
                    } 
                else if(headerNm =='Age/DOB/Sex') {                
                 	 _setAttributes(headerNm, $j(this), ageSearch);
                   }
                else if(headerNm =='Address') {                
                 	 _setAttributes(headerNm, $j(this), addressSearch);
                   }
             });             
        } 

        function _showAtoZIcon(headerNm, link, colId) {            
            var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
            var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
            var sortSt =  getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;            
            var sortFirstStr = sortSt.substring(0,(sortSt.indexOf("@")-1));
            var sortSecondStr = sortSt.substring(sortSt.indexOf("@")+1);
            
            if(sortFirstStr != null && sortFirstStr==headerNm) {
                if(sortSecondStr != null && sortSecondStr.indexOf("descending") != -1) {
                    link.after(htmlDesc);
                } else {
                    link.after(htmlAsc);
                }
            }

        }

        function _setAttributes(headerNm, link, colId) {
            
            var imgObj = link.parent().find("img");
           
            var toolTip = "";   
            var sortSt =  $j("#sortSt") == null ? "" : $j("#sortSt").html();
            
            var sortFirstStr = sortSt.substring(0,(sortSt.indexOf("@")-1));
            var sortSecondStr = sortSt.substring(sortSt.indexOf("@")+1);
            var orderCls = "SortAsc.gif";
            var altOrderCls = "Sort Ascending";
            var sortOrderCls = "FilterAndSortAsc.gif";
            var altSortOrderCls = "Filter Applied with Sort Ascending";
			
            
            if(sortSecondStr != null && sortSt.indexOf("descending") != -1) {
                orderCls = "SortDesc.gif";
                altOrderCls = "Sort Descending";
                sortOrderCls = "FilterAndSortDesc.gif";
                altSortOrderCls = "Filter Applied with Sort Descending";
            }   
            var filterCls = "Filter.gif";
            var altFilterCls = "Filter Applied";
            //toolTip = colId.html() == null ? "" : colId.html();
            if(colId != null && colId.length > 0) {
            
                link.attr("title", toolTip);
                imgObj.attr("src", filterCls);
                imgObj.attr("alt", altFilterCls);
                if(sortFirstStr != null && sortFirstStr == headerNm ){
                    imgObj.attr("src", sortOrderCls);           
                	imgObj.attr("alt", altSortOrderCls);	
           	 }
            } else {
            
                if(sortFirstStr != null && sortFirstStr==headerNm) {           
                    imgObj.attr("src", orderCls);    
                    imgObj.attr("alt", altOrderCls);
                }           
            }
        }
        
         function selectfilterCriteria()
	 {
	            document.forms[0].action ='/nbs/PatientEntitySearch.do?method=searchSubmit&ContextAction=filterPatientSubmit';
	            document.forms[0].submit();
         } 
         
        function clearFilter()
	{
	             document.forms[0].action ='/nbs/PatientEntitySearch.do?method=searchSubmit&ContextAction=removeFilter';
	             document.forms[0].submit();                                    
        }
        
        function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
        
		function createLink(element, url)
		{
			// call the JS function to block the UI while saving is on progress.
			blockUIDuringFormSubmissionNoGraphic();
            document.forms[0].action= url;
            document.forms[0].submit();  
		}
        
        </script>
<style type="text/css">
table.dtTable {
	margin-top: 0em;
}

table.dtTable, table.privateDtTable {
	width: 100%;
	border: 1px solid #666666;
	padding: 0.5em;
	margin: 0m auto;
	margin-top: 0em;
}

table.dtTable thead tr th, table.privateDtTable thead tr th {
	text-decoration: none;
	border: 1px solid #666666;
	font-weight: bold;
	background: #EFEFEF;
	padding: 0em;
	text-align: center;
}

table.dtTable tbody tr td, table.privateDtTable tbody tr td {
	vertical-align: top;
}

table.dtTable thead tr th, table.privateDtTable thead tr th {
	text-decoration: none;
	border: 1px solid #666666;
	font-weight: bold;
	background: #EFEFEF;
	padding: 0.1em 0 0.1em 0.1em;
	text-align: center;
}

table.dtTable tbody tr.odd, table.privateDtTable tbody tr.odd, table.dtTable tbody tr.odd td table tr
	{
	background: #FFF;
}

table.dtTable tbody tr.even, table.privateDtTable tbody tr.even, table.dtTable tbody tr.even td table tr
	{
	background: #dce7f7;
}

table.dtTable tbody tr td table tr td {
	border-width: 0;
}

table.dtTable tbody tr td, table.privateDtTable tbody tr td {
	padding: 2px;
	border-width: 0px 1px 1px 0px;
	border-style: solid;
	border-color: #C2D4EF;
}

table.dtTable tbody tr td.hoverDescLink a, table.privateDtTable tbody tr td.hoverDescLink a
	{
	text-decoration: none;
	color: #000;
	cursor: help;
}

table.dtTable tbody tr td.hoverDescLink a:hover, table.privateDtTable tbody tr td.hoverDescLink a:hover
	{
	background: #FFE2BF;
}

table.dtTable tbody tr td.dateField, table.dtTable tbody tr td.iconField
	{
	width: 50px;
}

table.dtTable tbody tr td.nameField {
	width: 150px;
}

table.dtTable tbody tr td.iconField {
	text-align: center;
}

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
</head>
<body class="popup"
	onload="startCountdown();attachIcons();makeMSelects();showCount();displayTooltips();addTabs();addRolePresentationToTabsAndSections();">
	<div id="blockparent"></div>
	<div id="doc3">
		<div id="bd">
			<div class="popupTitle">Patient Search Results</div>
			<div style="text-align: right; margin-bottom: 8px;">
				<a href='${fn:escapeXml(newSearchHref)}'> New Search
				</a> &nbsp;|&nbsp; <a
					href='${fn:escapeXml(refineSearchHref)}'> Refine
					Search </a>
			</div>
			<div class="infoBox messages" style="text-align: center;">
				Your Search Criteria: ${fn:escapeXml(DSSearchCriteriaString)}
				resulted in ${fn:escapeXml(queueCount)} possible matches. Would you like to <a href='${fn:escapeXml(refineSearchHref)}'> refine your search</a>
			<logic:match name="personSearchForm"
					property="attributeMap.totalRecords" value="error">
					<br>
				      The number of records that you can access has been exceeded. You will not be able to view all records that match your search parameters. Please refine your search to narrow your results.
				   
				</logic:match>
			</div>

			<html:form action="/PatientEntitySearch.do">
				<table role="presentation" width="100%">
					<tr>
						<td align="center"><display:table name="personList"
								class="dtTable" pagesize="20" id="searchResultsTable"
								style="border: 1px solid black;"
								requestURI="/nbs/PatientEntitySearch.do?method=searchSubmit&ContextAction=sortingByColumn&existing=true&initLoad=true"
								sort="external" export="true" excludedParams="*">

								<display:column property="link"
									style="width:12%;text-align:left;" sortable="true"
									sortName="getLink" title="Patient ID" />

								<display:column property="personFullName" sortable="true"
									sortName="getPersonFullName" style="width:20%;text-align:left;"
									defaultorder="ascending" title="Name" />
								<display:column property="profile" sortable="true"
									sortName="getProfile" style="width:15%;text-align:left;"
									defaultorder="ascending" title="Age/DOB/Sex" />
								<display:column property="personAddressProfile" sortable="true"
									sortName="getPersonAddress" style="width:20%;text-align:left;"
									title="Address" />
								<display:column property="personPhoneprofile" sortable="true"
									style="width:18%;text-align:left;"
									sortName="getPersonPhoneprofile" title="Phone/Email" />
								<display:column property="personIds"
									style="width:19%;text-align:left;" sortable="true"
									sortName="getPersonIds" title="ID" />

								<display:setProperty name="basic.empty.showtable" value="true" />
							</display:table> <html:select property="answerArray(SearchText1)"
								styleId="fullName" onchange="selectfilterCriteria()"
								multiple="false" size="1">
								<html:optionsCollection property="noDataArray" value="key"
									label="value" />
							</html:select> <html:select property="answerArray(SearchText2)"
								styleId="telephone" onchange="selectfilterCriteria()"
								multiple="false" size="1">
								<html:optionsCollection property="noDataArray" value="key"
									label="value" />
							</html:select> <html:select property="answerArray(SearchText3)"
								styleId="idSearch" onchange="selectfilterCriteria()"
								multiple="false" size="1">
								<html:optionsCollection property="noDataArray" value="key"
									label="value" />
							</html:select> <html:select property="answerArray(SearchText4)"
								styleId="patientIdSearch" onchange="selectfilterCriteria()"
								multiple="false" size="1">
								<html:optionsCollection property="noDataArray" value="key"
									label="value" />
							</html:select> <html:select property="answerArray(SearchText5)"
								styleId="ageSearch" onchange="selectfilterCriteria()"
								multiple="false" size="1">
								<html:optionsCollection property="noDataArray" value="key"
									label="value" />
							</html:select> <html:select property="answerArray(SearchText6)"
								styleId="addressSearch" onchange="selectfilterCriteria()"
								multiple="false" size="1">
								<html:optionsCollection property="noDataArray" value="key"
									label="value" />
							</html:select> <html:hidden styleId="SearchText1"
								property="attributeMap.searchCriteria.fulNameSearch" /> <html:hidden
								styleId="SearchText2"
								property="attributeMap.searchCriteria.telePhoneSearch" /> <html:hidden
								styleId="SearchText3"
								property="attributeMap.searchCriteria.idSearch" /> <html:hidden
								styleId="SearchText4"
								property="attributeMap.searchCriteria.patientIdSearch" /> <html:hidden
								styleId="SearchText5"
								property="attributeMap.searchCriteria.ageSearch" /> <html:hidden
								styleId="SearchText6"
								property="attributeMap.searchCriteria.addressSearch" /></td>
					</tr>
				</table>
				<!-- html:hidden styleId="queueCnt" property="attributeMap.queueCount"/-->
				<div id="whitebar"
					style="background-color: #FFFFFF; width: 100%; height: 1px;"
					align="right"></div>
				<div class="removefilter" id="removeFilters">
					<a class="removefilerLink" href="javascript:clearFilter()"
						style="text-decoration: none"><font class="hyperLink">
							| Remove All Filters/Sorts&nbsp;</font></a>
				</div>
		</div>
	</div>
	<div style="display: none; visibility: none;" id="errorMessages">
		<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :
		</b> <br />
		<ul>
			<logic:iterate id="errors" name="personSearchForm" property="attributeMap.searchCriteria">
			    <li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
			</logic:iterate>
		</ul>
	</div>
	<html:hidden styleId="queueCnt" property="attributeMap.queueCount" />
	</div>
	</html:form>
</body>
</html>
