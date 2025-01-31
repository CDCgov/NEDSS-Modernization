/**
 *  avr.column.js.
 *  JavaScript for column.xsp.
 *  Calls functions that are in sniffer.js and nedss.js.
 *  @author Ed Jenkins
 */

var aAvailableColumns = new Array();
var aSelectedColumns = new Array();

function sortByDescription(e1, e2)
{
    var x = e1[1];
    var y = e2[1];
    if(x < y)
    {
        return -1;
    }
    if(x > y)
    {
        return 1;
    }
    return 0;
}

function importData(pArray, pLST)
{
    if(pArray == null)
    {
        return;
    }
    if(pLST == null)
    {
        return;
    }
    var x = 0;
    var y = 0;
    var e = null;
    var t = null;
    var varID = null;
    var varName = null;
    var varValue = null;
    y = pLST.options.length;
    for(x=y-1; x>-1; x--)
    {
        pLST.remove(x);
    }
    y = pArray.length;
    for(x=0; x<y; x++)
    {
        a = pArray[x];
        varID = pLST.id + "_" + a[0];
        varName = a[1];
        varValue = a[0];
        varSelected = a[2];
        e = document.createElement("option");
        e.setAttribute("id", varID);
        e.setAttribute("value", varValue);
        if(varSelected=="true"){
          e.setAttribute("selected", varSelected);
          getElementByIdOrByName("SORT_COLUMN_textbox").value = varName;
        }
        t = document.createTextNode(varName);
        e.appendChild(t);
        pLST.appendChild(e);
    }
    varID = pLST.id + "_blank";
    varName = "";
    varValue = "";
    e = document.createElement("option");
    e.setAttribute("id", varID);
    e.setAttribute("value", varValue);
    t = document.createTextNode(varName);
    e.appendChild(t);
    //pLST.appendChild(e);
}

/**
 *  Removes duplicate values from the available column list that are also in the selected column list.
 */
function importColumns()
{
    var ax = 0;
    var ay = aAvailableColumns.length;
    var sx = 0;
    var sy = aSelectedColumns.length;
    var b = false;
    var tx = 0;
    var aAvailableTemp = new Array();
    for(ax=0; ax<ay; ax++)
    {
        var aa = aAvailableColumns[ax];
        b = false;
        for(sx=0; sx<sy; sx++)
        {
            var sa = aSelectedColumns[sx];
            if(aa[0] == sa[0])
            {
                b = true;
            }
        }
        if(b == false)
        {
            aAvailableTemp[tx++] = aa;
        }
    }
    aAvailableColumns = aAvailableTemp;
    aAvailableColumns.sort(sortByDescription);
    importData(aAvailableColumns, document.frm.id_AVAILABLE_COLUMNS_list);
    importData(aSelectedColumns, document.frm.id_SELECTED_COLUMNS_list);
    importData(aSortColumns, document.frm.id_SORT_BY_list);    
	disableSortOrder(document.frm.id_SORT_BY_list);	
}

	function disableSortOrder(varSort) {
		//alert('varSort : ' + varSort.value);	
		//alert('varSort SElInd : ' + varSort.selectedIndex);	
		var varSortVal = varSort.value;
	    if(varSortVal.length == 0) {
		    getElementByIdOrByName("SORT_ORDER_textbox").value="";
		    getElementByIdOrByName("SORT_ORDER").value="";
			getElementByIdOrByName("SORT_ORDER_textbox").disabled=true;
			getElementByIdOrByName("SORT_ORDER_button").disabled=true;
		}
		if(varSort.selectedIndex != -1) {
			enableSortOrder();
		}
	}

	function enableSortOrder() {	
		getElementByIdOrByName("SORT_ORDER_textbox").disabled=false;
		getElementByIdOrByName("SORT_ORDER_button").disabled=false;
		getElementByIdOrByName("SORT_ORDER_textbox").value="Ascending";
		getElementByIdOrByName("SORT_ORDER").value="ASC";		
	}


function exportData(pLST)
{
    var s = null;
    s = "";
    if(pLST == null)
    {
        s += OpenTag("table");
        s += CloseTag("table");
        return s;
    }
    var x = 0;
    var y = pLST.options.length;
    var o = null;
    var varName = null;
    var varValue = null;
    s = "";
    s += OpenTag("table");
    for(x=0; x<y; x++)
    {
        o = pLST.options[x];
        varName = o.text;
        varValue = o.value;
        if(varName == "")
        {
            continue;
        }
        if(varValue == "")
        {
            continue;
        }
        s += " ";
        s += OpenTag("record");
        s += " ";
        s += OpenTag("field");
        s += varValue;
        s += CloseTag("field");
        s += " ";
        s += OpenTag("field");
        s += varName;
        s += CloseTag("field");
        s += " ";
        s += OpenTag("field");
        s += "false";
        s += CloseTag("field");
        s += " ";
        s += CloseTag("record");
        s += " ";
    }
    s += CloseTag("table");
    return s;
}

function exportColumns()
{
    document.frm.id_AVAILABLE_COLUMNS.value = exportData(document.frm.id_AVAILABLE_COLUMNS_list);
    document.frm.id_SELECTED_COLUMNS.value = exportData(document.frm.id_SELECTED_COLUMNS_list);
    document.frm.id_SORT_BY.value = exportData(document.frm.id_SORT_BY_list);
}

/**
 *  Validates the form.
 *  @return true if the form is valid or false if not.
 */
function isValid()
{
    var x = document.frm.id_SELECTED_COLUMNS_list.options.length;
    if(x < 1)
    {
        return false;
    }
    return true;
}


function do_id_MoveUp_onclick()
{
    var varList = document.frm.id_SELECTED_COLUMNS_list;
    var varSI = varList.selectedIndex;
    if(varSI < 1)
    {
        return;
    }
    if(varList.options[varSI].value == "")
    {
        return;
    }
    var aNew = new Array();
    var x = 0;
    var y = aSelectedColumns.length;
    var z = 0;
    for(x=0; x<varSI-1; x++)
    {
        aNew[z++] = aSelectedColumns[x];
    }
    aNew[z++] = aSelectedColumns[varSI];
    aNew[z++] = aSelectedColumns[varSI-1];
    for(x=varSI+1; x<y; x++)
    {
        aNew[z++] = aSelectedColumns[x];
    }
    aSelectedColumns = aNew;
    var o = varList.options[varSI];
    var varID = o.id;
    var varName = o.text;
    var varValue = o.value;
    varList.remove(varSI);
    var e = document.createElement("option");
    e.setAttribute("id", varID);
    e.setAttribute("value", varValue);
    e.setAttribute("selected", "selected");
    var t = document.createTextNode(varName);
    e.appendChild(t);
    var b = varList.options[varSI-1];
    varList.insertBefore(e, b);
    sortColumnlist(varList);
}

function sortColumnlist(source) {

	var dest = getElementByIdOrByName('id_SORT_BY_list');
	arrValues = new Array();
	arrTexts   = new Array();
	
	for(i=0; i<source.length; i++)  {
	  arrValues[i] = source.options[i].value;
	  arrTexts[i] = source.options[i].text;
	}

	for(i=0; i<dest.length; i++)  {
		//alert(dest.options[i].id + dest.options[i].value);
	  dest.options[i].id = "id_sort_" + arrValues[i];
	  dest.options[i].value = arrValues[i];
	  dest.options[i].text = arrTexts[i];

	}
}


function do_id_MoveDown_onclick()
{
    var varList = document.frm.id_SELECTED_COLUMNS_list;
    var varSI = varList.selectedIndex;
    if(varSI < 0)
    {
        return;
    }
    if(varSI > varList.length-3)
    {
        return;
    }
    if(varList.options[varSI].value == "")
    {
        return;
    }
    var aNew = new Array();
    var x = 0;
    var y = aSelectedColumns.length;
    var z = 0;
    for(x=0; x<varSI; x++)
    {
        aNew[z++] = aSelectedColumns[x];
    }
    aNew[z++] = aSelectedColumns[varSI+1];
    aNew[z++] = aSelectedColumns[varSI];
    for(x=varSI+2; x<y; x++)
    {
        aNew[z++] = aSelectedColumns[x];
    }
    aSelectedColumns = aNew;
    var o = varList.options[varSI];
    var varID = o.id;
    var varName = o.text;
    var varValue = o.value;
    varList.remove(varSI);
    var e = document.createElement("option");
    e.setAttribute("id", varID);
    e.setAttribute("value", varValue);
    e.setAttribute("selected", "selected");
    var t = document.createTextNode(varName);
    e.appendChild(t);
    var b = varList.options[varSI+1];
    varList.insertBefore(e, b);
    sortColumnlist(varList);
}

