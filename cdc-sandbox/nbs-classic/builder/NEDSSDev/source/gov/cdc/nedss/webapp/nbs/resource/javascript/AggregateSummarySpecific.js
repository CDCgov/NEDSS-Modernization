function CalcMMWRWeekOnLoad()
{
	JAggregateForm.CalcMMWRWeekOnLoad(function(data) {
	    DWRUtil.setValue("SUM102S",data);
	    JAggregateForm.setSearchCriteria("SUM102", data);
	    autocompTxtValuesForJSP();
   });
}

function displayTooltips() {	
	$j(".sortable a").each(function(i) {
		var headerNm = $j(this).html();
		_setAttributes(headerNm, $j(this));
	});				
}

function _setAttributes(headerNm, link) {
	
	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
	var hdrFromSortSt = sortSt.substring(0, sortSt.indexOf("|"));
	//alert('sortSt: ' + sortSt + ', headerNm: ' + headerNm);
	var sortOrderCls = "GraySortAsc.gif";
	var altSortOrder = "Sort Ascending";
	if(sortSt != null && sortSt.indexOf("descending") != -1) {
		sortOrderCls = "GraySortDesc.gif";
		altSortOrder = "Sort Descending";
	}  	
	if(sortSt != null && (headerNm == hdrFromSortSt))
		link.after("<img class='multiSelect' align='top' border='0' src='" + sortOrderCls + "' alt = '"+altSortOrder+"'/>");		
}

function populateMMWRYear(yr, wk) {
	var mmYear = DWRUtil.getValue(yr);
	//alert(mmYear);
	JAggregateForm.getMmwrWeeks(mmYear, function(data) {
	    dwr.util.removeAllOptions(wk);
	    dwr.util.addOptions(wk, data, "key", "value");
	    DWRUtil.setValue(wk,"");
	    autocompTxtValuesForJSP();
   });		
}

