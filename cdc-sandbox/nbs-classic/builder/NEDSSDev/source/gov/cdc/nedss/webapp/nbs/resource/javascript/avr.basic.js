/**
 *  avr.basic.js.
 *  JavaScript for basic.xsp.
 *  Calls functions that are in sniffer.js and nedss.js.
 *  @author Ed Jenkins
 */

var aFilters = new Array();
var aSelectedColumns = new Array();
var aCriteria = new Array();
var a = null;
var varTest = false;

var varDiseaseSelected = false;
var varCountySelected = false;

String.prototype.startsWith = function(str) 
{return (this.match("^"+str)==str)}

/**
 *  Validates the form.
 *  @return true if the form is valid or false if not.
 */
function isValid()
{
    var x = 0;
    var y = aFilters.length;
    var z = 0;
    var varUID = "";
    var varCode = "";
    var varType = "";
    var varName = "";
    var varMin = "";
    var varMax = "";
    var isRequired = "";
    var varCount = 0;
    var varMSG = "";
    var varValid = true;
    var s = null;
    var e = null;
    var td = null;
    HideErrors();
    for(x=0; x<y; x++)
    {
        a = aFilters[x];
        varUID = a[0];
        varCode = a[1];
        varType = a[2];
        varName = a[3];
        varMin = a[4];
        varMax = a[5];
        isRequired = a[6];

        if(varCode.startsWith("C_D01") && isRequired == "Y")
        {
        	
            s = "";
            s += "id_";
            s += "C_D01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varMSG = "A minimum of " + varMin + " disease must be selected in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(varCount > varMax)
            {
                if(varMax > 0)
                {
                    varMSG = "A maximum of " + varMax + " diseases can be selected for this report.";
                    ShowError(td, varMSG);
                    varValid = false;
                    continue;
                }
            }
        }
        if(varCode.startsWith("J_S01") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "J_S01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varMSG = "A minimum of " + varMin + " state must be selected in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(varCount > varMax)
            {
                if(varMax > 0)
                {
                    varMSG = "A maximum of " + varMax + " states can be selected for this report.";
                    ShowError(td, varMSG);
                    varValid = false;
                    continue;
                }
            }
        }
        
        if(varCode.startsWith("CVG_CUSTOM") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "CVG_CUSTOM";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varMSG = "A minimum of " + varMin + " value must be selected in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(varCount > varMax)
            {
                if(varMax > 0)
                {
                    varMSG = "A maximum of " + varMax + " value can be selected for this report.";
                    ShowError(td, varMSG);
                    varValid = false;
                    continue;
                }
            }
        }
        
        if(varCode.startsWith("TXT") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "TXT_01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            if(e.value==null || e.value=='')
            {
                varMSG = "Text Filter must be entered in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
        }
        if(varCode.startsWith("D_") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "D_01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            if(e.value==null || e.value=='')
            {
                varMSG = "Days Filter must be entered in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
        }
        
        if(varCode.startsWith("STD_HIV") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "STD_HIV_WRKR";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varMSG = "A minimum of " + varMin + " worker must be selected in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(varCount > varMax)
            {
                if(varMax > 0)
                {
                    varMSG = "A maximum of " + varMax + " workers can be selected for this report.";
                    ShowError(td, varMSG);
                    varValid = false;
                    continue;
                }
            }
        }
        
        if(varCode.startsWith("J_C01") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "J_C01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varMSG = "A minimum of " + varMin + " county must be selected in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(varCount > varMax)
            {
                if(varMax > 0)
                {
                    varMSG = "A maximum of " + varMax + " counties can be selected for this report.";
                    ShowError(td, varMSG);
                    varValid = false;
                    continue;
                }
            }
        }
        if(varCode.startsWith("J_R01") && isRequired == "Y")
        {
            s = "";
            s += "id_";
            s += "J_R01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varMSG = "A minimum of " + varMin + " region must be selected in order to generate a report.";
                ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(varCount > varMax)
            {
                if(varMax > 0)
                {
                    varMSG = "A maximum of " + varMax + " regions can be selected for this report.";
                    ShowError(td, varMSG);
                    varValid = false;
                    continue;
                }
            }
        }
        if(varCode.startsWith("T_T01") && isRequired == "Y")
        {
            var ea = getElementByIdOrByName("id_T_T01a");
            var eb = getElementByIdOrByName("id_T_T01b");
            td = getElementByIdOrByName("id_T_T01a_error");
            z = 0;
            z += CheckDate(ea.value, true, td, "From Date");
            if(z > 0)
            {
                varValid = false;
            }
            td = getElementByIdOrByName("id_T_T01b_error");
            z += CheckDate(eb.value, true, td, "To Date");
            if(z > 0)
            {
                varValid = false;
            }
            td = getElementByIdOrByName("id_T_T01a_error");
            if(z == 0)
            {
                z += CheckDateOrder(ea.value, eb.value, "From Date", "To Date", td);
                if(z > 0)
                {
                    varValid = false;
                    continue;
                }
            }
        }
        //validate if only from date is entered etc., scenarios
        if(varCode.startsWith("T_T01") && isRequired == "N") {
            var ea = getElementByIdOrByName("id_T_T01a");
            var eb = getElementByIdOrByName("id_T_T01b");

            if(ea.value.length>0 && eb.value.length==0) {
	            var varMSG = "Please enter a valid To Date using this format: 'mm/dd/yyyy'.";
	            td = getElementByIdOrByName("id_T_T01b_error");
	            ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(ea.value.length==0 && eb.value.length>0) {
	            var varMSG = "Please enter a valid From Date using this format: 'mm/dd/yyyy'.";
	            td = getElementByIdOrByName("id_T_T01a_error");
	            ShowError(td, varMSG);
                varValid = false;
                continue;
            }
                        
            if(z > 0)
            {
                varValid = false;
            }
            td = getElementByIdOrByName("id_T_T01a_error");
            if(z == 0)
            {
                z += CheckDateOrder(ea.value, eb.value, "From Date", "To Date", td);
                if(z > 0)
                {
                    varValid = false;
                    continue;
                } 
            }
        }
        if(varCode.startsWith("M_Y01") && isRequired == "N") {
                    //alert("Here");
	            var ea = getElementByIdOrByName("id_M_Y01a");
	            var eb = getElementByIdOrByName("id_M_Y01b");
	
	            if(ea.value.length>0 && eb.value.length==0) {
		            var varMSG = "Please enter a valid To Date using this format: 'mm/yyyy'.";
		            td = getElementByIdOrByName("id_M_Y01b_error");
		            ShowError(td, varMSG);
	                varValid = false;
	                continue;
	            }
	            if(ea.value.length==0 && eb.value.length>0) {
		            var varMSG = "Please enter a valid From Date using this format: 'mm/yyyy'.";
		            td = getElementByIdOrByName("id_M_Y01a_error");
		            ShowError(td, varMSG);
	                varValid = false;
	                continue;
	            }
	                        
	            if(z > 0)
	            {
	                varValid = false;
	            }
	            td = getElementByIdOrByName("id_M_Y01a_error");
	            //if(z == 0)
	            //{
	            //    z += CheckDateOrder(ea.value, eb.value, "From Date", "To Date", td);
	            //    if(z > 0)
	            //    {
	            //        varValid = false;
	            //        continue;
	            //    } 
	            //}
        }
        if(varCode.startsWith("T_T02"))
        {
            var ea = getElementByIdOrByName("id_T_T02a").value;
            var eb = getElementByIdOrByName("id_T_T02b").value;
            
            td = getElementByIdOrByName("id_T_T02a_error");
		    var varMSG = "From Date must be less than or equal to To Date. Please correct the data entry and try again.";

            if(ea > eb)
            {
            	ShowError(td, varMSG);
                varValid = false;
                continue;
            }
          }
    }
    return varValid;
}

function hasAdvanceFilters()
{
	var adv = aCriteria.length;

	if(adv > 0)	
	{
		return true;
	}
    return false;
	
}

function hasColumns()
{

    var col = aSelectedColumns.length;

	if(col > 0)	
	{
		return true;
	}
    return false;
}

var varProxyFlag = true;

function proxy_callback()
{
    if(varProxyFlag == true)
    {
        varProxyFlag = false;
        return;
    }
    ResetSessionTimer();
    var f = window.frames[0].document.frm;
    var varInput = f.id_ProxyInput.value;
    var varOutput = f.id_ProxyOutput.value;
    var aTable = ParseTable(varOutput);
    var countiesSelected = getElementByIdOrByName("COUNTIES_SELECTED").value;
    	//alert('countiesSelected: ' + countiesSelected);
    if(countiesSelected != 'true')
    	LoadCounties(aTable);
    
    if(varTest == true)
    {
        varTest = false;
        window.setTimeout(proxy_callback_test, 2000);
    }
}

function proxy_callback_test()
{
    var varList = getElementByIdOrByName("id_J_C01");
    SelectAllOptions(varList, true);
}

function LoadCounties(pTable)
{
    if(pTable == null)
    {
        return;
    }
    var varList = document.frm.id_J_C01;
    var x = 0;
    var y = varList.options.length;
    var a = null;
    var e = null;
    var t = null;
    var varUID = null;
    var varDescription = null;
    var varSelected = null;
    for(x=y-1; x>=0; x--)
    {
        varList.remove(x);
    }
    y = pTable.length;
    for(x=0; x< y; x++)
    {
        a = pTable[x];
        varUID = a[0];
        varDescription = a[1];
        varSelected = a[2];
        if(varUID == "")
        {
            continue;
        }
        if(varDescription == "")
        {
            continue;
        }
        e = document.createElement("option");
        e.setAttribute("id", "id_J_C01_" + varUID);
        e.setAttribute("value", varUID);
        if(varSelected == "true")
        {
            e.setAttribute("selected", "selected");
        }
        t = document.createTextNode(varDescription);
        e.appendChild(t);
        varList.appendChild(e);
    }
}
function hasBasicAdvanceEntered() {

   	if( !hasAdvanceFilters() && !hasBasicFilters() ) 
   	{	
		return false;                       		
   	}  
   	return true;
   	
}

function hasBasicFilters()
{
    var x = 0;
    var y = aFilters.length;
    var z = 0;
    var varUID = "";
    var varCode = "";
    var varType = "";
    var varName = "";
    var varMin = "";
    var varMax = "";
    var varCount = 0;
    var varMSG = "";
    var varValid = true;
    var s = null;
    var e = null;
    var td = null;
	var basicAnswered = false;
    HideErrors();

    for(x=0; x<y; x++)
    {
        a = aFilters[x];
        varUID = a[0];
        varCode = a[1];
        varType = a[2];
        varName = a[3];
        varMin = a[4];
        varMax = a[5];


        if(varCode.startsWith("C_D01"))
        {
        	
            s = "";
            s += "id_";
            s += "C_D01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }
        }
        if(varCode.startsWith("J_S01"))
        {
            s = "";
            s += "id_";
            s += "J_S01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }

        }
        if(varCode.startsWith("J_C01"))
        {
            s = "";
            s += "id_";
            s += "J_C01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }

        }
        
        if(varCode.startsWith("STD_HIV_WRKR"))
        {
            s = "";
            s += "id_";
            s += "STD_HIV_WRKR";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }

        }
        
        if(varCode.startsWith("CVG_CUSTOM"))
        {
            s = "";
            s += "id_";
            s += "CVG_CUSTOM";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }

        }
        
        if(varCode.startsWith("TXT"))
        {
            s = "";
            s += "id_";
            s += "TXT_01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            if(e.value !=null && e.value !='')
            {
            	basicAnswered = true;
            }

        }
        if(varCode.startsWith("D_"))
        {
            s = "";
            s += "id_";
            s += "D_01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            if(e.value !=null && e.value !='')
            {
            	basicAnswered = true;
            }

        }
        if(varCode.startsWith("J_R01"))
        {
            s = "";
            s += "id_";
            s += "J_R01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }
        }
        if(varCode.startsWith("T_T01"))
        {
            var ea = getElementByIdOrByName("id_T_T01a");
            var eb = getElementByIdOrByName("id_T_T01b");
            td = getElementByIdOrByName("id_T_T01a_error");
            z = 0;
            
            if(ea.value.length > 0) {
	            z += CheckDate(ea.value, true, td, "From Date");
	        }
            if(z > 0)
            {
                varValid = false;
            }
            td = getElementByIdOrByName("id_T_T01b_error");
            
            if(eb.value.length > 0) {
            	z += CheckDate(eb.value, true, td, "To Date");
            }	
            if(ea.value.length>0 && eb.value.length==0) {
	            var varMSG = "Please enter a valid To Date using this format: 'mm/dd/yyyy'.";
	            ShowError(td, varMSG);
                varValid = false;
                continue;
            }
            if(ea.value.length==0 && eb.value.length>0) {
	            var varMSG = "Please enter a valid From Date using this format: 'mm/dd/yyyy'.";
	            ShowError(td, varMSG);
                varValid = false;
                continue;
            }
                        
            if(z > 0)
            {
                varValid = false;
            }
            td = getElementByIdOrByName("id_T_T01a_error");
            if(z == 0)
            {
                z += CheckDateOrder(ea.value, eb.value, "From Date", "To Date", td);
                if(z > 0)
                {
                    varValid = false;
                    continue;
                } 
            } 
            if(ea.value.length>0 && eb.value.length>0)
            	basicAnswered = true;
            
        }
        if(varCode.startsWith("M_Y01"))
	        {
	            var ea = getElementByIdOrByName("id_M_Y01a");
	            var eb = getElementByIdOrByName("id_M_Y01b");
	            td = getElementByIdOrByName("id_M_Y01a_error");
	            z = 0;
	            
	            td = getElementByIdOrByName("id_M_Y01b_error");
	            
	            if(ea.value.length>0 && eb.value.length==0) {
		            var varMSG = "Please enter a valid To Date using this format: 'mm/yyyy'.";
		            ShowError(td, varMSG);
	                varValid = false;
	                continue;
	            }
	            if(ea.value.length==0 && eb.value.length>0) {
		            var varMSG = "Please enter a valid From Date using this format: 'mm/yyyy'.";
		            ShowError(td, varMSG);
	                varValid = false;
	                continue;
	            }
	                        
	            if(z > 0)
	            {
	                varValid = false;
	            }
	            td = getElementByIdOrByName("id_M_Y01a_error");
	            if(ea.value.length>0 && eb.value.length>0)
	            	basicAnswered = true;
	            
        }
        if(varCode.startsWith("T_T02"))
        {
            var ea = getElementByIdOrByName("id_T_T02a").value;
            var eb = getElementByIdOrByName("id_T_T02b").value;

            td = getElementByIdOrByName("id_T_T02a_error");
		    var varMSG = "From Date must be less than or equal to To Date. Please correct the data entry and try again.";

            if(ea > eb)
            {
            	ShowError(td, varMSG);
                varValid = false;
                continue;
            } else {
            	basicAnswered = true;
            }
          }          
    }
    //finally check if Include NULLs checkbox is checked
    if(getElementByIdOrByName("id_disease_include_nulls")!= null && getElementByIdOrByName("id_disease_include_nulls").checked)
	    basicAnswered = true;
    if(getElementByIdOrByName("id_county_include_nulls")!= null && getElementByIdOrByName("id_county_include_nulls").checked)
	    basicAnswered = true;
    if(getElementByIdOrByName("id_region_include_nulls")!= null && getElementByIdOrByName("id_region_include_nulls").checked)
	    basicAnswered = true;
    if(getElementByIdOrByName("id_timeRange_include_nulls")!= null && getElementByIdOrByName("id_timeRange_include_nulls").checked)
	    basicAnswered = true;
    if(getElementByIdOrByName("id_timePeriod_include_nulls")!= null && getElementByIdOrByName("id_timePeriod_include_nulls").checked)
	    basicAnswered = true;
    if(getElementByIdOrByName("id_monthYearRange_include_nulls")!= null && getElementByIdOrByName("id_monthYearRange_include_nulls").checked)
	    basicAnswered = true;
    if(getElementByIdOrByName("id_cvg_include_nulls")!= null && getElementByIdOrByName("id_cvg_include_nulls").checked)
	    basicAnswered = true;
    
    return basicAnswered;
}


function reqBasicFiltersEntered() {

    var x = 0;
    var y = aFilters.length;
    var z = 0;
    var varUID = "";
    var varCode = "";
    var varType = "";
    var varName = "";
    var varMin = "";
    var varMax = "";
    var isRequired = "";
    var varCount = 0;
    var varMSG = "";
    var reqBasicFiltersEntered = true;
    var isAnyBasFiltReq = false;
    var s = null;
    var e = null;
    var td = null;
    var varValid = true;
    HideErrors();
    for(x=0; x<y; x++)
    {
        a = aFilters[x];
        varUID = a[0];
        varCode = a[1];
        varType = a[2];
        varName = a[3];
        varMin = a[4];
        varMax = a[5];
        isRequired = a[6];

        if(varCode.startsWith("C_D01") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "C_D01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        if(varCode.startsWith("J_S01") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "J_S01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        
        if(varCode.startsWith("CVG_CUSTOM") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "CVG_CUSTOM";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        if(varCode.startsWith("TXT") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "TXT_01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            if(e.value==null || e.value=='')
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        if(varCode.startsWith("D_") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "D_01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            if(e.value==null || e.value=='')
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        if(varCode.startsWith("J_C01") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "J_C01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        
        if(varCode.startsWith("STD_HIV_WRKR") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "STD_HIV_WRKR";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        if(varCode.startsWith("J_R01") && isRequired == "Y")
        {
        	isAnyBasFiltReq = true;
            s = "";
            s += "id_";
            s += "J_R01";
            e = getElementByIdOrByName(s);
            s += "_error";
            td = getElementByIdOrByName(s);
            varCount = GetSelectedCount(e);
            if(varCount < varMin)
            {
                reqBasicFiltersEntered = false;
                continue;
            }
        }
        if(varCode.startsWith("T_T01"))
        {	
	        if(isRequired == "Y")
	        	isAnyBasFiltReq = true;
	        	
            var ea = getElementByIdOrByName("id_T_T01a");
            var eb = getElementByIdOrByName("id_T_T01b");

            if(ea.value.length==0 && eb.value.length==0) {	 
	            if(isRequired == "Y")           
    	            reqBasicFiltersEntered = false;
                continue;
            }
			if(ea.value.length>0 && eb.value.length==0)            
				varValid = false;
			if(eb.value.length>0 && ea.value.length==0)            
				varValid = false;						
            z = 0;
            z += CheckDate(ea.value, true, null, "From Date");
            if(z > 0)
            {
                varValid = false;
            }
            z += CheckDate(eb.value, true, null, "To Date");
            if(z > 0)
            {
                varValid = false;
            }
            if(z == 0)
            {
                z += CheckDateOrder(ea.value, eb.value, "From Date", "To Date", null);
                if(z > 0)
                {
                    varValid = false;
                    continue;
                }
            }
            
        }
	if(varCode.startsWith("M_Y01"))
	{	
		if(isRequired == "Y")
			isAnyBasFiltReq = true;

	    var ea = getElementByIdOrByName("id_M_Y01a");
	    var eb = getElementByIdOrByName("id_M_Y01b");

	    if(ea.value.length==0 && eb.value.length==0) {	 
		    if(isRequired == "Y")           
		    reqBasicFiltersEntered = false;
		    continue;
	  	}
		if(ea.value.length>0 && eb.value.length==0) {           
		   varValid = false;
		   continue;
		}
		if(eb.value.length>0 && ea.value.length==0) {        
		   varValid = false;
		   continue;
		}
	            
        }
        if(varCode.startsWith("T_T02")) {
        	if(isRequired == "Y")
        		isAnyBasFiltReq = true;
        		
            var ea = getElementByIdOrByName("id_T_T02a").value;
            var eb = getElementByIdOrByName("id_T_T02b").value;
            if(ea > eb)
            {
                varValid = false;
                continue;
            }
        }
    }

    getElementByIdOrByName("BASIC_REQUIRED_ENTERED").value=reqBasicFiltersEntered;
    getElementByIdOrByName("ANY_BASIC_REQUIRED").value=isAnyBasFiltReq;
    getElementByIdOrByName("IS_BASICDATA_VALID").value=varValid;
    
    return isAnyBasFiltReq;

}