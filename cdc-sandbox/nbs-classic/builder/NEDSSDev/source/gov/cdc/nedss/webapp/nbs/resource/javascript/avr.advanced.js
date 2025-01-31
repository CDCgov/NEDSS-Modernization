/**
 *  avr.advanced.js.
 *  JavaScript for advanced.xsp.
 *  Calls functions that are in sniffer.js and nedss.js.
 *  @author Ed Jenkins
 */

var aNames = new Array();
var aCriteria = new Array();
var aSelectedColumns = new Array();
var aOperators_CODED = [ ["EQ","Equals"],["NE","Not Equals"],["IN","Is Null"],["NN","Not Null"]];

var varWhere = "";
var varNextID = 0;

function hasColumns()
{
    var x = aSelectedColumns.length;
    if(x > 0)
    {
        return true;
    }
    return false;
}

function translateName(pUID)
{
    var x = 0;
    var y = aNames.length;
    var varText = "";
    var a = null;
    for(x=0; x<y; x++)
    {
        a = aNames[x];
        if(a[0] == pUID)
        {
            varText = a[1];
            break;
        }
    }
    return varText;
}

function translateOperator(pUID, type)
{   //alert('pUID: ' + pUID + ', type: ' + type);
    var x = 0;
    var operatorType = '';
    if(type == 'STRING')
    	operatorType=aOperators_STRING;
    if(type == 'INTEGER')
    	operatorType=aOperators_INTEGER;
    if(type == 'DATETIME')
    	operatorType=aOperators_DATETIME;
    if(type == 'CODED')	
    	operatorType=aOperators_CODED;
    	
    var y = operatorType.length;
    var varText = "";
    var a = null;
    for(x=0; x<y; x++)
    {
        a = operatorType[x];
        if(a[0] == pUID)
        {
            varText = a[1];
            break;
        }
    }
    return varText;
}

function getDataType(pUID)
{
    var x = 0;
    var y = aNames.length;
    var varUID = null;
    var varType = "";
    var a = null;
    for(x=0; x<y; x++)
    {
        a = aNames[x];
        varUID = a[0];
        if(varUID == pUID)
        {
            varType = a[2];
            break;
        }
    }
    return varType;
}

function getMaxForType(pType)
{
    var s = "1";
    if(pType == null)
    {
        return s;
    }
    if(pType == "STRING")
    {
        s = "1";
    }
    if(pType == "NUMBER")
    {
        s = "10";
    }
    if(pType == "INTEGER")
    {
        s = "10";
    }
    if(pType == "DATE")
    {
        s = "10";
    }
    if(pType == "DATETIME")
    {
        s = "19";
    }
    return s;
}

function getMaxForField(pUID)
{
    var x = 0;
    var y = aNames.length;
    var varUID = null;
    var varMax = null;
    var a = null;
    for(x=0; x<y; x++)
    {
        a = aNames[x];
        varUID = a[0];
        if(varUID == pUID)
        {
            varMax = a[3];
            break;
        }
    }
    return varMax;
}

function sortByTitle(e1, e2)
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

function importNames()
{
    var x = 0;
    var y = 0;
    var e = null;
    var t = null;
    var varID = null;
    var varName = null;
    var varValue = null;
    var varList = document.frm.id_name;
    y = varList.options.length;
    for(x=y-1; x>-1; x--)
    {
        varList.remove(x);
    }
    y = aNames.length;
    for(x=0; x<y; x++)
    {
        a = aNames[x];
        varID = varList.id + "_" + a[0];
        varName = a[1];
        varValue = a[0];
        e = document.createElement("option");
        e.setAttribute("id", varID);
        e.setAttribute("value", varValue);
        t = document.createTextNode(varName);
        e.appendChild(t);
        varList.appendChild(e);
    }
    varID = varList.id + "_blank";
    varName = "";
    varValue = "";
    e = document.createElement("option");
    e.setAttribute("id", varID);
    e.setAttribute("value", varValue);
    t = document.createTextNode(varName);
    e.appendChild(t);
    varList.appendChild(e);
}

function reloadOperators()
{	
	getElementByIdOrByName("id_value").setAttribute("size",10);
    var varNI = 0;
    var varUID = null;
    var varType = null;
    var varMax = 1;
    varNI = document.frm.id_name.selectedIndex;
    if(varNI == -1)
    {
        //loadOperators(aOperators);
        document.frm.id_value.maxLength = varMax;
        return;
    }
    varUID = document.frm.id_name.options[varNI].value;
    varType = getDataType(varUID);
    varMax = getMaxForType(varType);
    if(varType == "STRING")
    {	
    	getElementByIdOrByName("id_value").setAttribute("size",30);
        loadOperators(aOperators_STRING);
        varMax = getMaxForField(varUID);
    }
    if(varType == "INTEGER")
    {
        loadOperators(aOperators_INTEGER);
    }
    if(varType == "DATETIME")
    {
        loadOperators(aOperators_DATETIME);
    }
    if(varType == "CODED") 
    {
	    //aOperators_CODED = [ ["EQ","Equals"],["NE","Not Equals"],["IN","Is Null"],["NN","Not Null"]];
    	loadOperators(aOperators_CODED);
    }

	document.frm.id_value.maxLength = varMax;
                            		    
}

function loadOperators(pAL)
{
	
    var x = 0;
    var y = 0;
    
    y = document.frm.id_operator.length;
    getElementByIdOrByName("operator_textbox").value = ""; 
    var targetNode = document.frm.id_operator;
    var removeOpt = targetNode.firstChild;
    
    while(removeOpt != null){
      var temp = removeOpt.nextSibling;
      targetNode.removeChild(removeOpt);
      removeOpt= temp;
     }
     
    getElementByIdOrByName("operator_textbox").removeAttribute("bNum");    
    targetNode.className="none";
    
    /*for(x=y-1; x>-1; x--)
    {	
    	alert("y:"+ y +" x:"+x);
    	getElementByIdOrByName("operator_textbox").value = ""; 
        document.frm.id_operator.remove(x);
    }*/
    
    y = pAL.length;
    var varID = null;
    var varName = null;
    var varValue = null;
    var e = null;
    var t = null;
    var a = null;
    y = pAL.length;
    for(x=0; x<y; x++)
    {
        a = pAL[x];

        varID = "id_operator_" + a[0];
        varName = a[1];
        varValue = a[0];
        e = document.createElement("option");
        e.setAttribute("id", varID);
        e.setAttribute("value", varValue);
        t = document.createTextNode(varName);
        e.appendChild(t);
        document.frm.id_operator.appendChild(e);
    }
    aOperators = pAL;
}

function UpdateWhereClause()
{	//alert('aCriteria: ' + aCriteria);
    var x = 0;
    var y = aCriteria.length;
    var z = y - 1;
    var varType = null;
    var varName = null;
    var varOperator = null;
    var varValue = null;
    var s = null;
    var t = null;
    var a = null;
    varWhere = "";
    for(x=0; x<y; x++)
    {
        a = aCriteria[x];
        varType = a[1].toUpperCase();
        s = "";
        if(varType == "CLAUSE")
        {
            varName = translateName(a[2]);
            varOperator = translateOperator(a[3],varType);
            varValue = a[4];      
            
            if ( getElementByIdOrByName("id_operator").value == 'BW' || a[3] == 'BW' ) {                      
            	varValue = varValue + ',' + a[5];
            }
            	
            t = getDataType(a[2]);
            s += "(";
            s += varName;
            s += " ";
            s += varOperator;
            s += " ";

            if(t == "STRING" || t=="CODED")
            {
                s += "\"" + varValue + "\"";
            }
            else
            {
                s += varValue;
            }
            s += ")";
        }
        if(varType == "OPERATOR")
        {
            s += a[3];
        }
        varWhere += s;
        if(x < z)
        {
            varWhere += " ";
        }
    }
    //alert('varWhere: ' + varWhere);
    document.frm.id_where.value = varWhere;
}

function insertIntoCriteria(pArray)
{
    var aNew = new Array();
    var varLI = document.frm.id_CriteriaList_list.selectedIndex;
    var varLL = document.frm.id_CriteriaList_list.options.length - 1;
    var varIP = (varLI==-1) ? varLL : varLI;
    var x = 0;
    var y = aCriteria.length;
    var z = 0;
    for(x=0; x<varIP; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aNew[z++] = pArray;
    for(x=varIP; x<y; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aCriteria = aNew;
    UpdateSequenceNumbers();
}

function insertIntoList(pID, pName, pValue, pSelected)
{	//alert('inside insertIntoList, pID ' + pID + ' pName: ' + pName + 'pValue: ' + pValue +  'pSelected: ' + pSelected);
    var e = document.createElement("option");
    e.setAttribute("id", pID);
    e.setAttribute("value", pValue);
    if(pSelected == true)
    {
        e.setAttribute("selected", "selected");
    }
    var t = document.createTextNode(pName);
    e.appendChild(t);
    var varLI = document.frm.id_CriteriaList_list.selectedIndex;
    var varLL = document.frm.id_CriteriaList_list.options.length - 1;
    var varIP = (varLI==-1) ? varLL : varLI;
    var b = document.frm.id_CriteriaList_list.options[varIP];
    document.frm.id_CriteriaList_list.insertBefore(e, b);
}


function insertOperator(pOperator)
{	
    var sSEQ = "0";
    var sType = "OPERATOR";
    var sName = "";
    var sOperator = pOperator;
    var sValue = "";
    var a = [sSEQ, sType, sName, sOperator, sValue, "false"];
    insertIntoCriteria(a);
    varNextID++;
    var varID = "id_CriteriaList_list_" + varNextID;
    var varName = sOperator;
    var varValue = varID;
    var varSelected = "true";
    insertIntoList(varID, varName, varValue, varSelected);
    UpdateWhereClause();
}


function removeCriteria()
{
    var varList = document.frm.id_CriteriaList_list;
    var varSI = varList.selectedIndex;
    if(varSI == -1 || (varSI==varList.length-1 && varList[varSI].value==""))
    {
        return;
    }
    /*
    if(varList.options[varSI].value == "")
    {
        return;
    }
    */
    var aNew = new Array();
    var x = 0;
    var y = aCriteria.length;
    var z = 0;
    for(x=0; x<varSI; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    for(x=varSI+1; x<y; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aCriteria = aNew;
    varList.remove(varSI);
    if(varList.options[varSI].value == "")
    {
        varSI--;
    }
    if( (varSI > -1) && (varSI < (y-1)) )
    {
        varList.options[varSI].selected = true;
    }
    UpdateSequenceNumbers();
    UpdateWhereClause();
}

function removeAllCriteria()
{
    aCriteria = new Array();
    importCriteria();
}

function UpdateSequenceNumbers()
{
    var a = null;
    var s = 0;
    var x = 0;
    var y = aCriteria.length;
    for(x=0, s=1; x<y; x++, s++)
    {
        a = aCriteria[x];
        a[0] = String(s);
    }
}

function importCriteria()
{	//alert(aCriteria);
    var x = 0;
    var y = 0;
    var a = null;
    var e = null;
    var t = null;
    var cSEQ = null;
    var cType = null;
    var cName = null;
    var cOperator = null;
    var cValue = null;
    var varID = null;
    var varName = null;
    var varValue = null;
    var varType = null;
    var varList = document.frm.id_CriteriaList_list;
    y = varList.options.length;
    for(x=y-1; x>-1; x--)
    {
        varList.remove(x);
    }
    y = aCriteria.length;
    for(x=0; x<y; x++)
    {
        a = aCriteria[x];
        //alert(a);
        cSEQ = a[0];
        cType = a[1].toUpperCase();
        cName = a[2];
        cOperator = a[3];
        cValue = a[4];
        varID = varList.id + "_" + cSEQ;
        varName = "";
        varValue = "";
        if(cType == "CLAUSE")
        {
            varType = getDataType(cName);
            varName += translateName(cName);
            varName += " ";
            varName += translateOperator(cOperator, varType);
            varName += " ";
            //alert('translateOperator(cOperator, varType): ' + translateOperator(cOperator, varType));
            if(varType == "STRING"  || varType == "CODED")
            {
				if(cValue.length > 0)
                	varName += "\"";
            }
            varName += cValue;
            
            if(cOperator == 'BW') {
            	varName += " and ";
            	varName += a[5];
            }
            
            if(varType == "STRING" || varType == "CODED")
            {
                if(cValue.length > 0)
	                varName += "\"";
            }
            varValue = cValue;
        }
        
        
        if(cType == "OPERATOR")
        {
            varName = cOperator;
            varValue = cOperator;
        }
        if(varName == "")
        {
            continue;
        }
        if(varValue == "")
        {	if(cOperator != 'IN' && cOperator != 'NN')
            	continue;
        }
        e = document.createElement("option");
        e.setAttribute("id", varID);
        e.setAttribute("value", varValue);
        t = document.createTextNode(varName);
        e.appendChild(t);
        varList.appendChild(e);
    }
    varID = varList.id + "_blank";
    varName = "";
    varValue = "";
    e = document.createElement("option");
    e.setAttribute("id", varID);
    e.setAttribute("value", varValue);
    t = document.createTextNode(varName);
    e.appendChild(t);
    varList.appendChild(e);
    UpdateWhereClause();
}

function exportCriteria()
{	//alert('inside exportCriteria');
    var x = 0;
    var y = aCriteria.length;
    var a = 0;
    var b = 0;
    var s = 0;
    var varCriteriaList = "";
    varCriteriaList += OpenTag("table");
    for(x=0, s=1; x<y; x++, s++)
    {
        varCriteriaList += " ";
        varCriteriaList += OpenTag("record");
        var aTemp = aCriteria[x];
        aTemp[0] = String(s);
        b = aTemp.length;
        for(a=0; a<b; a++)
        {
            varCriteriaList += " ";
            varCriteriaList += OpenTag("field");
            //alert('aTemp[2]: '+ getDataType(aTemp[2]));
            if(getDataType(aTemp[2]) == 'CODED')
            	varCriteriaList += aTemp[a];
            else	
	            varCriteriaList += xmlEncode(aTemp[a]);
            varCriteriaList += CloseTag("field");
        }
        varCriteriaList += " ";
        varCriteriaList += CloseTag("record");
        varCriteriaList += " ";
    }
    varCriteriaList += CloseTag("table");
    document.frm.id_CriteriaList.value = varCriteriaList;
}

function do_id_value_onkeydown(_this)
{
    var k = 0;
    if(is_ie)
    {
        k = window.event.keyCode;
    }
    else
    {
        k = _this.which;
    }
    if(k == 13)
    {
        return false;
    }
    return true;
}

function do_id_value_onkeyup(_this)
{
    var k = 0;
    if(is_ie)
    {
        k = window.event.keyCode;
    }
    else
    {
        k = _this.which;
    }
    if(k == 13)
    {
        document.frm.id_insert.click();
        document.frm.id_value.value = "";
        return false;
    }
    var varNI = document.frm.id_name.selectedIndex;
    var varUID = document.frm.id_name.options[varNI].value;
    var varType = getDataType(varUID);

    if(varType == "DATETIME")
    {	
    	if(_this.value!=undefined &&_this.value.length > 10)
			_this.value = _this.value.substr(0,10);
		else
			if(_this.value==undefined &&_this.target.value.length > 10)  		
    		_this.value = _this.target.value.substr(0,10);

        DateMask(_this,null,_this);
    }
    return true;
}

function do_id_value_onkeypress(_this)
{
    var k = 0;
    if(is_ie)
    {
        k = window.event.keyCode;
    }
    else
    {
        k = _this.which;
    }
    if(k == 13)
    {
        return false;
    }
    return true;
}

function do_id_insert_onclick()
{
    var varNI = 0;
    var varUID = null;
    var varType = null;
    varNI = document.frm.id_name.selectedIndex;
    if(varNI == -1)
    {
        return;
    }
    varUID = document.frm.id_name.options[varNI].value;    
    varType = getDataType(varUID);
    varOperator = getElementByIdOrByName("id_operator").value;
    var varValue = document.frm.id_value.value;
    var x = 0;
    var y = 0;
    var td = getElementByIdOrByName("id_name_error");
    HideErrors();
    
    //check if operator is selected, if not prompt the user!
    if(isEmpty(varOperator) == true)
    {
		var varMSG = "The Logic field can not be blank.";
		ShowError(td, varMSG);
		varValid = false;
		return;
    }    
    
    if((varType == "STRING") && (varOperator != "IN") && (varOperator != "NN"))
    {	
        x += CheckEmptyGeneric(varValue, td, "Value");
        if(x > 0)
        {
            return;
        }
    }
    if((varType == "CODED") && (varOperator != "IN") && (varOperator != "NN"))
    {

		var selectbox = getElementByIdOrByName("id_operator1");
		for(i=selectbox.options.length-1; i>=0; i--)
		{
			if(selectbox.options[i].selected) {    
				varValue = "selected";
			}
		}

	        x += CheckEmptyGeneric(varValue, td, "Value");

        if(x > 0)
        {
            return;
        }
    }    
    
    if(varType == "INTEGER" && (varOperator != "IN") && (varOperator != "NN"))
    {
        x += CheckNumericGeneric(varValue, true, td, "Value");
        if(x > 0)
        {
            return;
        }
    }
    if(varType == "DATETIME" && (varOperator != "IN") && (varOperator != "NN"))
    {
        x += CheckDate(varValue, true, td, "Value");
        if(x > 0)
        {
            return;
        }
        
        if(varOperator == "BW") {

	        y += CheckDate(document.frm.id_value2.value, true, td, "Value");
	        if(y > 0)
	        {
	            return;
	        } 
            y += CheckDateOrder(varValue, document.frm.id_value2.value, "From Date", "To Date", td);
            if(y > 0)
            {
                return;
            } 	        
        }
       
    }
    var varOI = document.frm.id_operator.selectedIndex;
    var sSEQ = "0";
    var sType = "CLAUSE";
    var sName = document.frm.id_name.options[varNI].value;
    var sOperator = document.frm.id_operator.options[varOI].value;
    var sValue = document.frm.id_value.value;

	//alert('varType: ' + varType);
	
	if(varType == 'CODED') {
		sValue = "";
		var selectbox = getElementByIdOrByName("id_operator1");
		var codeOrDesc = getCodeOrDesc(varUID);
		var cnt = 0;		
		for(i=selectbox.options.length-1; i>=0; i--)
		{
			if(selectbox.options[i].selected) {
			
				cnt++;		
				if(cnt > 1) {
					sValue += "|";
				}
				if(codeOrDesc == 'C') {					
					sValue += selectbox.options[i].id;
					//alert('cnt: ' + cnt + ' value: ' +  sValue[cnt]);
				} else {
					sValue += selectbox.options[i].value;
				}
			}			
		}
	    var a = [sSEQ, sType, sName, sOperator, sValue, "false"];
	    insertIntoCriteria(a);

	} else {
	    if(sOperator == 'BW') {
		    var sValue2= document.frm.id_value2.value;
		    var a = [sSEQ, sType, sName, sOperator, sValue, sValue2, "false"];
		    insertIntoCriteria(a);
	    } else {
		    var a = [sSEQ, sType, sName, sOperator, sValue, "false"];
		    insertIntoCriteria(a);   
	    }
	}


    var varID = "id_CriteriaList_list_" + varNextID;
    varNextID++;
    var varName = "";
    varName += document.frm.id_name.options[varNI].text;
    varName += " ";
    varName += document.frm.id_operator.options[varOI].text;
    varName += " ";

    if(varType == "STRING" || varType == "CODED")
    {
    	if(sValue.length > 0)
        varName += "\"";
    }
    
    varName += sValue;
    
    if(sOperator=='BW') {
    	varName += " and ";
    	varName += document.frm.id_value2.value;
    }
    
    if(varType == "STRING" || varType == "CODED")
    {
      	if(sValue.length > 0)
        varName += "\"";
    }
    var varValue = varID;
    var varSelected = "false";
    //alert('varValue: ' + varValue);
    insertIntoList(varID, varName, varValue, varSelected);
    UpdateWhereClause();
}

function do_id_MoveUp_onclick()
{
    var varList = document.frm.id_CriteriaList_list;
    var varSI = varList.selectedIndex;
    if(varSI < 1 || (varSI==varList.length-1 && varList[varSI].value==""))
    {
        return;
    }
    /*
    if(varList.options[varSI].value == "")
    {
        return;
    }
    */
    var aNew = new Array();
    var x = 0;
    var y = aCriteria.length;
    var z = 0;
    for(x=0; x<varSI-1; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aNew[z++] = aCriteria[varSI];
    aNew[z++] = aCriteria[varSI-1];
    for(x=varSI+1; x<y; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aCriteria = aNew;
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
    UpdateSequenceNumbers();
    UpdateWhereClause();
}

function do_id_MoveDown_onclick()
{
    var varList = document.frm.id_CriteriaList_list;
    var varSI = varList.selectedIndex;
    if(varSI < 0)
    {
        return;
    }
    if(varSI > varList.length-3)
    {
        return;
    }
    /*
    if(varList.options[varSI].value == "")
    {
        return;
    }
    */
    var aNew = new Array();
    var x = 0;
    var y = aCriteria.length;
    var z = 0;
    for(x=0; x<varSI; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aNew[z++] = aCriteria[varSI+1];
    aNew[z++] = aCriteria[varSI];
    for(x=varSI+2; x<y; x++)
    {
        aNew[z++] = aCriteria[x];
    }
    aCriteria = aNew;
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
    UpdateSequenceNumbers();
    UpdateWhereClause();
}

function loadValueTypes() {
	
	//don't display multiselect & value2 initially
	getElementByIdOrByName("id_operator1").className="none";
	getElementByIdOrByName("id_value").className="visible";
	getElementByIdOrByName("id_value").disabled=true;
	getElementByIdOrByName("id_value2").className="none";	
	getElementByIdOrByName("id_between").className="none";	
	
   	if(isEmpty(getElementByIdOrByName("id_name").value) == true) {
		getElementByIdOrByName("id_insert").disabled=true;
	} else {
		getElementByIdOrByName("id_insert").disabled=false;
	}
	
	HideErrors();
}

function reloadValueTypes() {
	HideErrors();
	var selectedFld = getElementByIdOrByName("id_name").value;
	var selectedOpr = getElementByIdOrByName("id_operator").value;
	//alert('selectedFld: ' + selectedFld + 'selectedOpr: ' + selectedOpr);
	var fldDataType = getDataType(selectedFld);


	if(fldDataType=='CODED') {
	
		getElementByIdOrByName("id_value").className="none";
		getElementByIdOrByName("id_value2").className="none";
		getElementByIdOrByName("id_between").className="none";
		getElementByIdOrByName("id_operator1").className="visible";
		
		if(selectedOpr=='IN' || selectedOpr=='NN') {
		
			deselectList(getElementByIdOrByName("id_operator1"));			
			getElementByIdOrByName("id_operator1").className="none";
			getElementByIdOrByName("id_value").value="";
			getElementByIdOrByName("id_value").className="visible";
			getElementByIdOrByName("id_value").disabled=true;
			
		} else {
		   
		    var varList = getElementByIdOrByName("id_operator1");
		    var x = 0;
		    var y = varList.options.length;		
		    for(x=y-1; x>=0; x--)
		    {
		        varList.remove(x);
		    }
			e = document.createElement("option");
        	var t = document.createTextNode("Loading Values......");
    		e.appendChild(t);	    
		    varList.appendChild(e);
		    
			getElementByIdOrByName("id_value").disabled=false;
			getElementByIdOrByName("id_operator1").className="visible";
		}	
	
	} else {
		//alert('inside else');
		getElementByIdOrByName("id_operator1").className="none";
		if(selectedOpr=='BW') {
			getElementByIdOrByName("id_value2").value="";
			getElementByIdOrByName("id_value2").className="visible";
			getElementByIdOrByName("id_between").className="visible";
		} else {
			getElementByIdOrByName("id_value").value="";
			getElementByIdOrByName("id_value2").className="none";
			getElementByIdOrByName("id_between").className="none";
		}    
		
		if(selectedOpr=='IN' || selectedOpr=='NN') {
			getElementByIdOrByName("id_value").value="";
			getElementByIdOrByName("id_value").className="visible";
			getElementByIdOrByName("id_value").disabled=true;
		} else {
			getElementByIdOrByName("id_value").disabled=false;
		}		
	}

}

function getCodeSet(pUID)
{
    var x = 0;
    var y = aNames.length;
    var varUID = null;
    var codesetNm = "";
    var a = null;
    for(x=0; x<y; x++)
    {
        a = aNames[x];
        varUID = a[0];
        if(varUID == pUID)
        {
            codesetNm = a[5];
            break;
        }
    }
    return codesetNm;
}

function getCodeOrDesc(pUID)
{
    var x = 0;
    var y = aNames.length;
    var varUID = null;
    var codeOrDesc = "";
    var a = null;
    for(x=0; x<y; x++)
    {
        a = aNames[x];
        varUID = a[0];
        if(varUID == pUID)
        {
            codeOrDesc = a[4];
            break;
        }
    }
    return codeOrDesc.toUpperCase();
}

var varProxyFlag = true;
var varTest = false;

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
	LoadCodedValues(aTable);
    if(varTest == true)
    {
        varTest = false;
        window.setTimeout(proxy_callback_test, 2000);
    }
}

function LoadCodedValues(pTable)
{
    if(pTable == null)
    {
        return;
    }
    var varList = document.frm.id_operator1;
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
        if(varUID == "")
        {
            continue;
        }
        if(varDescription == "")
        {
            continue;
        }
        e = document.createElement("option");
        e.setAttribute("id", varUID);
        e.setAttribute("value", varDescription);
        t = document.createTextNode(varDescription);
        e.appendChild(t);
        varList.appendChild(e);
    }
}

function deselectList(listID) 
{ 
        var optionsList = listID.getElementsByTagName('option'); 

        for (i = optionsList.length - 1; i>=0; i--)
        { 
                optionsList[i].selected = false; 
        } 
} 
