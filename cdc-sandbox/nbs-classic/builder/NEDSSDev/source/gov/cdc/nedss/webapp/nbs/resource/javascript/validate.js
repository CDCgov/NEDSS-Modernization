
var showErrorStatement= true;


/*
 * VL: this functions is triggered by onLoad event from <body> tag. It enables or
 * disables "Other" input field based on select value.  If select value is "Other",
 * then input field gets enabled, and disabled otherwise.

function validationEnableOnOtherOption() {


	var formNode = getElementByIdOrByName("nedssForm");
	if(formNode!=null){
		var selectNodes = formNode.getElementsByTagName("select");

		for(var i=0; i<selectNodes.length; i++ ) {
			if( selectNodes.item(i).validationType &&
				selectNodes.item(i).validationType == "enableOnOtherOption" ) {
				inputBoxNameRef = selectNodes.item(i).nameRef;
				inputBox = getElementByIdOrByName(inputBoxNameRef);
				if( selectNodes.item(i).value == "OTH" )
					inputBox.disabled = false;
				else
					inputBox.disabled = true;
			}
		}
	}

}

*/
function ToggleListText(pLST, pTXT)
{
    var a = isOptionSelected(pLST, "Other");
    var b = isOptionSelected(pLST, "Yes");
    if(b == false || a == false)
    {
        pTXT.value = "";
        pTXT.disabled=true;
    }
    //pTXT.disabled = !b;
    //pTXT.disabled = !a;
    if(b == true || a == true)
    {
    	pTXT.disabled = false
        pTXT.focus();
    }
}


function toggleOtherInputs( radioObj, txtObj1name, txtObj2name, txtObj3name, txtObj4name )
{
    var txtObj1 = getElementByIdOrByName( txtObj1name );
    var txtObj2 = getElementByIdOrByName( txtObj2name );
    var txtObj3 = getElementByIdOrByName( txtObj3name );
    var txtObj4 = getElementByIdOrByName( txtObj4name );

    if( radioObj.value == 'y' )
    	setting = false;
    else
    	setting = true;

    txtObj1.disabled = setting;
    txtObj2.disabled = setting;
    txtObj3.disabled = setting;
    txtObj4.disabled = setting;
}


/* 
 * VL: trim spaces as well as \n and other blank characters
 */

function trimBlanks( str ) {

  var start =0;
  var end = 0;
  var newStr = "";
  for( var i=0; i<str.length; i++ ) {
  if( (str.charAt(i)!=" ") && (str.charAt(i)!="\n") && (str.charAt(i)!="\t") && 
      (str.charAt(i)!="\r") && (str.charAt(i)!="\l") && (str.charAt(i)!="\xA0") ) {
     start=i; break;
  }
  }
  for( var i=str.length-1; i>=0; i-- ) {
  if( (str.charAt(i)!=" ") && (str.charAt(i)!="\n") && (str.charAt(i)!="\t") && 
      (str.charAt(i)!="\r") && (str.charAt(i)!="\l") && (str.charAt(i)!="\xA0") ) {
     end=i; break;
  }
  }
  for( var i=start; i<=end; i++ ) {
  newStr = newStr + str.charAt(i);
  }
  return( newStr );
}

/*
 * VL: takes string and trims spaces from the beginning and the end.
 * Returns new string without spaces.
 */
function trimSpaces( str ) {
  var start =0;
  var end = 0;
  var newStr = "";
  for( var i=0; i<str.length; i++ ) {
  	if( str.charAt(i) != " " ) {
  	   start=i; break;
  	}
  }
  for( var i=str.length-1; i>=0; i-- ) {
  	if( str.charAt(i) != " " ) {
  	   end=i; break;
  	}
  }
  for( var i=start; i<=end; i++ ) {
  	newStr = newStr + str.charAt(i);
  }
  return( newStr );
}




/*
 * VL: updates disabled field with calulated DOB based on
 * three other input boxes.
 * @arg1 is "Age" text box
 * @arg2 is age units drop down box
 * @arg3 is "Age reported date"
 * @arg4 is field where results to be displayed
 *
 */
function  doAutoCalculateDOB( arg1, arg2, arg3, arg4 ) {
    var varAGE = trimSpaces( arg1.value );
    var varReported = trimSpaces( arg3.value );
    if(isEmpty(varAGE) == true)
    {
        arg4.value = "";
        return false;
    }
    if(isNumeric(varAGE) == false)
    {
        arg4.value = "";
        return false;
    }
    if(varAGE > 150)
    {
        arg4.value = "";
        return false;
    }
    if(isEmpty(arg2.options[arg2.selectedIndex].text) == true)
    {
        arg4.value = "";
        return false;
    }
    if(isEmpty(varReported) == true)
    {
        arg4.value = "";
        return false;
    }
    if(isDate(varReported) == false)
    {
        arg4.value = "";
        return false;
    }
    var d = StringToDate(varReported);
    var varM = d.getMonth() + 1;
    var varD = d.getDate();
    var varY = d.getFullYear();
    var varH = 0;
    var x = 0;
    var y = 0;
    if(isOptionSelected(arg2, "Years") == true)
    {
        varY -= varAGE;
    }
    if(isOptionSelected(arg2, "Months") == true)
    {
        for(x=0; x<varAGE; x++)
        {
            varM--;
            if(varM < 1)
            {
                varY--;
                varM = 12;
            }
        }
    }
    if(isOptionSelected(arg2, "Weeks") == true)
    {
        varAGE *= 7;
        for(x=0; x<varAGE; x++)
        {
            varD--;
            if(varD < 1)
            {
                varM--;
                if(varM < 1)
                {
                    varY--;
                    varM = 12;
                }
                varD = GetMonthMax(varM, varY);
            }
        }
    }
    if(isOptionSelected(arg2, "Days") == true)
    {

        for(x=0; x<varAGE; x++)
        {
            varD--;
            if(varD < 1)
            {
                varM--;
                if(varM < 1)
                {
                    varY--;
                    varM = 12;
                }
                varD = GetMonthMax(varM, varY);
            }
        }
    }
    if(isOptionSelected(arg2, "Hours") == true)
    {
        for(x=0; x<varAGE; x++)
        {
            varH--;
            if(varH < 1)
            {
                varD--;
                if(varD < 1)
                {
                    varM--;
                    varD = GetMonthMax(varM, varY);
                }
                varH = 23;
            }
        }
    }
    if(isOptionSelected(arg2, "Unknow") == true)
    {
            arg4.value = "";
            return true;
    }
    var varMM = "" + varM;
    var varDD = "" + varD;
    var varYY = "" + varY;
    if(varM < 10)   varMM = "0" + varMM;
    if(varD < 10)   varDD = "0" + varDD;
    if(varY < 10)   varYY = "0" + varYY;
    if(varY < 100)  varYY = "0" + varYY;
    if(varY < 1000) varYY = "0" + varYY;
    var s = varMM + "/" + varDD + "/" + varYY;
    d = StringToDate(s);
    s = DateToString(d);
    arg4.value = s;
    return true;


}


/*
 * VL: disables or enables an input box when "Other" option
 * is selected in first <select> box
 * @selectBoxName - select object where "Other" is an option
 * @textBoxName - text box which get disabled and enabled.
 */
function selectBoxToggleOfInputBox( selectBoxName, textBoxName ) {
    var arg1 = getElementByIdOrByName( selectBoxName );
    var arg2 = getElementByIdOrByName( textBoxName );
    ToggleListText( arg1, arg2 );
}

/*
 * VL: finds and returns <td> cell element where error needs to be displayed.
 * It determines rowID of current <tr> and using it accesses corresponding <td>
 * element.
 * @ is <SPAN> nodes that are used throughout the document.  It is
 * usually in   "errorMsg|" + element.id   format.
 */

function getTdErrorCell ( element ) {
	

   if( element )
   {
				// VL: The following finds <td> cell where error text needs to be displayed.
				var trNode = getCorrectAttribute(element,"parentNode",element.parentNode);
//fatima
				while(trNode.nodeName!="TR" || !(trNode.rowID || getCorrectAttribute(trNode,"rowID",trNode.rowID)))
				{
					//alert("dentro"+trNode.nodeName+" and "+trNode.rowID);
					trNode = trNode.parentNode;
					if(trNode==null){
					
						return null;
					}
				}
					//alert("paraaaaaaaaaaaaaaaaaaaaaa en "+trNode.nodeName+" and "+trNode.rowID);
				
				var tableNode = trNode.parentNode;
				
				
				while(tableNode.nodeName!="TABLE")
				{
						tableNode = tableNode.parentNode;
				}

				//var rowID = trNode.rowID;
				//if(rowID==null || rowID==undefined)
				//rowID=trNode.getAttribute("rowID");
				var rowID = getCorrectAttribute(trNode,"rowID",trNode.rowID);

				
				var innerTdNodes = tableNode.getElementsByTagName( "td" );
				
				
				var tdErrorNode;
				
				// find matching <td> cell where error text needs to be displayed.
				//fatima
				for (var i=0; i < innerTdNodes.length; i++) {
					if(innerTdNodes.item(i).rowID == rowID || getCorrectAttribute(innerTdNodes.item(i),"rowID",innerTdNodes.item(i).rowID) == rowID){
						return innerTdNodes.item(i);
						}
				}
				
				
    }
	

}


/**
 *  Erases any text inside <td> cell that is used for
 *  displaying error message.  <td> cell is in line, <tr>,
 *  bellow validated element.
 *  @parm element - the element that can be validated
 *  @return - true
 */
function eraseErrorCellForElement( element ) {


	// VL: get <td> cell for displaying error message
	var tdErrorCell = getTdErrorCell( element );

	// VL: Hide error <td> cell as well.
	if( tdErrorCell ) {
		tdErrorCell.getTdErrorCell = "none";
		setText(tdErrorCell,"");

	}
	return( true );
}

/**
 * VL: Validates <input> text element for SSN data format.
 * called from validate() when doing "ssnComplexCheck" validation.
 * @element is <input> element
 * @returns true or false
 */
function validateSSNfield( element ) 
{
	var labelList = new Array();
	var errorMessage = "";
	var errorText = "";
	
	if (isblank(getCorrectAttribute(element,"value",element.value))) {
		return true;
	} else {

		if (checkSSN(getCorrectAttribute(element,"value",element.value)))	{



		} else {  //	else it false validation and you need to tell the user what went wrong
			  //	check if there already is an error msg there
			errorText = makeErrorMsg('ERR007', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));
			// errorText = ("Please enter a valid SSN using this format:  '000-00-0000'.");

			setText(tdErrorCell, errorText);
			
			return false;
		}
	}
}


function isblank(s) {
	for(var i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if ((c != ' ') && (c != '\n') && (c != '\t'))
			return false;
	}
	return true;
}




function checkForBlank(o)
{
	if (o.value == " " ) {
		o.value = "";
	}
}

function submitTimeForm() {

//this is only for testing, to find time difference in running application
var timenow = new Date();
var submitTime= getElementByIdOrByName("submitTime");
if(submitTime != null) submitTime.value =  timenow.valueOf();
submitForm();
}

function submitFormAndChangeContextAction (contextAction) {
	var contextTD = getElementByIdOrByName("ContextAction");
	if( contextTD ) contextTD.setAttribute("value", contextAction);

	submitForm();
}

function submitForm() 
{
    // call the JS function to block the UI while saving is on progress.
    //blockUIDuringFormSubmission();
	
	// continue with form submission
	callSaveData();	
	showErrorStatement=true;
	// hide tab error icon
	var divTabNodes = document.getElementsByTagName("div");
	for(var i=0; i < divTabNodes.length; i++) {
		// consider only divs that are tab type
		
		if(getCorrectAttribute(divTabNodes.item(i), "type", divTabNodes.item(i).type) == "tab"){
			var tabName = divTabNodes.item(i).id.replace(/tabControl/,"");
			// alert(tabName);
			var tabNode = getElementByIdOrByName("tabTdBgcolortop"+tabName);
			//get child to get <nobr> node
			if(tabNode!=null){
				tabNode = tabNode.firstChild;
				var imgNodes = tabNode.getElementsByTagName("img");
				if(imgNodes.length >0){
					tabNode.removeChild(imgNodes.item(0));
				}
			}
		}
	}

	// Hide any old error messages

	var errorTD = getElementByIdOrByName("error1");
	if( errorTD )
	{
		setAttributeClass(errorTD,"none");
	}
	var errorTD = getElementByIdOrByName("error2");
	if( errorTD ){
		setAttributeClass(errorTD,"none");
	}
	var errorTD = getElementByIdOrByName("errorRange");
	if( errorTD ){
	setAttributeClass(errorTD,"none");
	}


	var sendToBackEnd = true;
	var divNodes = null;
	if(arguments.length > 0){
        sendToBackEnd = arguments[0];
        divNodes = arguments[1];
	}
	//	create hidden input nodes for double batch entry
	if(CheckForChildWindow()){
		var error = false;

		if(arguments.length > 0)
			error = arguments[0];




		if(divNodes)
			var formNode = divNodes.getElementById("nedssForm");
		else
			var formNode = getElementByIdOrByName("nedssForm");

		var inputNodes = formNode.getElementsByTagName("input");
		var selectNodes = formNode.getElementsByTagName("select");
		var textareaNodes = formNode.getElementsByTagName("textarea");
		var subformNodes = formNode.getElementsByTagName("cdcform");

		/* VL  Erase errors for all possible elements.  Errors that are displayed
		*     in a separate line, <TR>, under the elements being validate.
		*/
					var errorTDNodeArray = document.getElementsByTagName("td");
					for(var i=0; i < errorTDNodeArray.length; i++) {
						var errorTDNode = errorTDNodeArray.item(i);
						if(getCorrectAttribute(errorTDNode, "rowID",errorTDNode.rowID)!=null || errorTDNode.getAttribute("rowID"))
						{
							setText(errorTDNode,"");
							
						}
					}


					for(var i=0; i < inputNodes.length; i++) {
						if ( ((getCorrectAttribute(inputNodes.item(i), "type", inputNodes.item(i).type) == "text")||(getCorrectAttribute(inputNodes.item(i), "type", inputNodes.item(i).type) == "file")) && getCorrectAttribute(inputNodes.item(i),"validate",inputNodes.item(i).validate)) {
							if( !getCorrectAttribute(inputNodes.item(i),"isNested",inputNodes.item(i).isNested ))
								eraseErrorCellForElement(inputNodes.item(i));
						}
					}

					for(var i=0; i<selectNodes.length; i++ ) {
						if( !getCorrectAttribute(selectNodes.item(i),"isNested",selectNodes.item(i).isNested) && getCorrectAttribute(selectNodes.item(i),"validate",selectNodes.item(i).validate ))
							eraseErrorCellForElement(selectNodes.item(i));
					}
					for(var i=0; i<textareaNodes.length; i++ ) {
						if(!getCorrectAttribute(textareaNodes.item(i),"isNested",textareaNodes.item(i).isNested) && getCorrectAttribute(textareaNodes,"validate",textareaNodes.item(i).validate))
							eraseErrorCellForElement(textareaNodes.item(i));
					}

					for(var i=0; i<subformNodes.length; i++) {
						if(!getCorrectAttribute(subformNodes.item(i),"isNested",subformNodes.item(i).isNested)) {
							
							eraseErrorCellForElement(subformNodes.item(i));
						}
					}




		



		/////////////////////////////
		//	DO VALIDATION
		var nestedError = false;		
		
		for(var i=0; i < inputNodes.length; i++)	{
			var tmpInputNode = inputNodes.item(i);
			if ( (tmpInputNode.type == "text")|| (tmpInputNode.type == "file")|| (tmpInputNode.type == "checkbox") || (tmpInputNode.type == "hidden") ) {
				if(!getCorrectAttribute(tmpInputNode,"isNested",tmpInputNode.isNested) && getCorrectAttribute(tmpInputNode,"validate",tmpInputNode.validate) && (validate(tmpInputNode) == false) ){
					error = true;
					//show some error message if none specified 
					
					errorNode = getTdErrorCell(tmpInputNode);

					if(errorNode.innerText==""){
						setText(errorNode, "GENERIC ERROR TEXT - error message not specified");
						setAttributeClass(errorNode,"error");
					}
					
						
				}
				
				else if(getCorrectAttribute(tmpInputNode,"isNested",tmpInputNode.isNested) && 
						tmpInputNode.name.search("asOfDate")==-1 
						&& ((tmpInputNode.type == "text" && 
						tmpInputNode.value!="") || (tmpInputNode.type == "checkbox" 
						&& tmpInputNode.checked==true) || (tmpInputNode.type == "file" && getCorrectAttribute(tmpInputNode, "parent", tmpInputNode.parent)=="Attachment" && tmpInputNode.value!="" && tmpInputNode.id.indexOf(getFileInputVisible())!=-1))){
				nestedError=true;
				}
				//CHECK FOR SPECIAL CHARACTERS
				//updateForIllegalChars(tmpInputNode);
			}
		}


		// Process all <select> elements. We need it for validating
			// ID Type drop down box.

		for(var i=0; i<selectNodes.length; i++ ) {
			var tmpSelectNode = selectNodes.item(i);
			if( !getCorrectAttribute(tmpSelectNode,"isNested",tmpSelectNode.isNested) && getCorrectAttribute(tmpSelectNode,"validate",tmpSelectNode.validate) && (validate( tmpSelectNode ) == false))
				error = true;
			else if(getCorrectAttribute(tmpSelectNode,"isNested",tmpSelectNode.isNested) && tmpSelectNode.value!=""){
				var def = tmpSelectNode.getAttribute("default");
				if(def==null)
					nestedError=true;
			}
		}
		for(var i=0; i<textareaNodes.length; i++ ) {
			var tmpTextAreaNode = textareaNodes.item(i);
			if( !getCorrectAttribute(tmpTextAreaNode,"isNested",tmpTextAreaNode.isNested) && getCorrectAttribute(tmpTextAreaNode,"validate",tmpTextAreaNode.validate) && (validate( tmpTextAreaNode ) == false) )
				error = true;
			else if(getCorrectAttribute(tmpTextAreaNode,"isNested",tmpTextAreaNode.isNested) && tmpTextAreaNode.value!=""){
							nestedError=true;
			}
		}
		
		//If any one of question group's question is validated as error, shows tab error icon on particular tab
		
		if(callValidateData() == false) {
			if(error == false) {			
				error = true;
				var tabNode = getElementByIdOrByName("tabTdBgcolortop"+tabName);
				if(tabNode!=null){
					tabNode = tabNode.firstChild;
					var imgNode = document.createElement("img");
					imgNode.setAttribute("src", "tab_error.gif");
				    imgNode.setAttribute("alt", "Error");
			        imgNode.setAttribute("title", "Error");
					tabNode.appendChild(imgNode);
				}						

			}	
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////

		if(nestedError==true)
		{
			if(nestedError)
			{
				var nestedErrorTD = getElementByIdOrByName("error2");
				if( nestedErrorTD ){
					setAttributeClass(nestedErrorTD,"error");
				}
				//document.location.href="#top";
				scroll(0,0);
			}

			return;
		}
		
		//check the function array for specific form validation functions
		//if function returns false it is okay else there is an error condition
		for (var i=0;i<validationArray.length;i++){
			if(validationArray[i]!=null){
				var temp = validationArray[i]();
				if(error!=true)
					error = temp;

			}
		}
		
		
		// Display error at the top of the page
		if ( (error==false) && (nestedError==false)) {

			var doubleBatchNodes = document.getElementsByTagName("input");
			for (var i=0; i<doubleBatchNodes.length; i++) {
				if(getCorrectAttribute(doubleBatchNodes.item(i),"type",doubleBatchNodes.item(i).type)=="hidden" && doubleBatchNodes.item(i).id.search(/nestedElementsHiddenField/i)!=-1){
					BatchEntryCreateHiddenInputs();
					break;
				}else if(doubleBatchNodes.item(i).type=="hidden" && doubleBatchNodes.item(i).id.search(/PopupData/i)!=-1){
					PopupCreateHiddenInputsForStruts();
					break;
				}
			}


			if(gSubmitOnce)
			{
			  gSubmitOnce= false;
			  callSaveData();
			  formNode.submit();
			}

		}
		
		
		//indicate visually which tab is in error condition
			// need to determine which div has error
			var divNodes = document.getElementsByTagName("div");
			var tabCount = 0;
			for(var i=0; i < divNodes.length; i++) {
				// consider only divs that are tab type
				if(getCorrectAttribute(divNodes.item(i), "type", divNodes.item(i).type) == "tab"){
					tabCount = tabCount + 1;
					// retrieve error tr for each tab and see if error messages exist
					var trNodes = divNodes.item(i).getElementsByTagName("tr");
					
					for(var j=0; j < trNodes.length; j++) {
						if(trNodes.item(j).id == "error-message-tr"){
							var tdNode = getCorrectFirstChild(trNodes.item(j));
							if(tdNode.innerText!="" || (tdNode.innerText==undefined && tdNode.textContent!="")) {
								//alert( "errorText: " + tdNode.innerText);
								//display the error visual on the tab
								//from the id of the div retrieve the node for the tab
								var tabName = divNodes.item(i).id.replace(/tabControl/,"");
								//alert(tabName);
								var tabNode = getElementByIdOrByName("tabTdBgcolortop"+tabName);
								//get child to get <nobr> node
								if(tabNode!=null){
									tabNode = tabNode.firstChild;

									//append the error icon

									var imgNode = document.createElement("img");
									imgNode.setAttribute("src", "tab_error.gif");
								    imgNode.setAttribute("alt", "Error");
							        imgNode.setAttribute("title", "Error");
									tabNode.appendChild(imgNode);
									break;
								}
							}
						}
					}
				}
			}
			
			if( error==true && showErrorStatement==true)
			{
				var errorTD = getElementByIdOrByName("error1");
				if( errorTD )	{
					var errorText=null;
					if(tabCount > 1)
						errorText = makeErrorMsg('ERR122');
					else
						errorText = makeErrorMsg('ERR015');
					errorTD.innerText = errorText;
					
					if(errorText!="" && errorTD.textContent=="")//if there is no changes
						errorTD.textContent=errorText;//Firefox

					setAttributeClass(errorTD,"error");
				}
				//document.location.href="#top";
				scroll(0,0);
						//return;
			}
	}
}





function cancelForm() {

	if(CheckForChildWindow() && gSubmitOnce ){
        var target = "";
        var confirmMsg;	
	var labelList = new Array();

        if(arguments.length > 0)
        target = arguments[0];
        if(arguments.length > 1) {
           confirmMsg = arguments[1];
        	
        }
           
        else
        {
           //confirmMsg = "Are you sure you want to Cancel and lose any information you have just entered?";
	   confirmMsg = makeErrorMsg('ERR013',labelList.concat(""));
	   
	}
		if (confirm(confirmMsg))
		{
		      gSubmitOnce = false;
			window.location = target;
		}
		else
		{
			 gSubmitOnce = true;
			return;
		}
	}

}


function deleteForm(page) {

	var labelList = new Array();
	
	//temporary fix
        var confirmMsg = "If you continue with the Delete action, you will delete the information. Select OK to continue, or Cancel to not continue.";	
	   
	
		if (confirm(confirmMsg))
		{
		      
		      getPage(page);
			
		}
		else
		{
			
			 
			return;
		}


}



function deleteInvestigationForm(page) {

	var labelList = new Array();
	var confirmMsg = makeErrorMsg('ERR174', labelList.concat(""));
	  
	
		if (confirm(confirmMsg))
		{
		      
		      getPage(page);
			
		}
		else
		{
			
			 
			return;
		}


}

function getPage(target) {

	var error = false;


	// Hide any old error messages

	var errorTD = getElementByIdOrByName("error1");
	if( errorTD ){
		setAttributeClass(errorTD,"none");
	}
	var error2TD = getElementByIdOrByName("error2");
	if( error2TD ) {
		setAttributeClass(error2TD,"none");
	}
	var errorTD = getElementByIdOrByName("errorRange");
	if( errorTD ) {
		setAttributeClass(errorTD,"none");
	
	}



	var formNode = getElementByIdOrByName("nedssForm");
	var inputNodes = formNode.getElementsByTagName("input");
	var selectNodes = formNode.getElementsByTagName("select");
	var textareaNodes = formNode.getElementsByTagName("textarea");


	/* VL  Erase errors for all possible elements.  Errors that are displayed
	 *     in a separate line, <TR>, under the elements being validate.
	 */
	for(var i=0; i < inputNodes.length; i++) {
		if ( (inputNodes.item(i).type == "text") ||
		     (inputNodes.item(i).type == "checkbox") ) {
			if( !getCorrectAttribute(inputNodes.item(i),"isNested",inputNodes.item(i).isNested ))
				eraseErrorCellForElement(inputNodes.item(i));
		}
	}

	for(var i=0; i<selectNodes.length; i++ ) {
		if( !getCorrectAttribute(selectNodes.item(i),"isNested",selectNodes.item(i).isNested ))
				eraseErrorCellForElement(selectNodes.item(i));
	}
	for(var i=0; i<textareaNodes.length; i++ ) {
		if( !getCorrectAttribute(textareaNodes.item(i),"isNested",textareaNodes.item(i).isNested ))
				eraseErrorCellForElement(textareaNodes.item(i));
	}
	// Do validation
	for(var i=0; i < inputNodes.length; i++)	{
		if ( (inputNodes.item(i).type == "text") ||
		     (inputNodes.item(i).type == "checkbox") ) {
			if( !getCorrectAttribute(inputNodes.item(i),"isNested",inputNodes.item(i).isNested) && (validate(inputNodes.item(i)) == false) )
					error = true;
		}
	}

	// Process all <select> elements. We need it for validating
        // ID Type drop down box.
	for(var i=0; i<selectNodes.length; i++ ) {
		if( !getCorrectAttribute(selectNodes.item(i),"isNested",selectNodes.item(i).isNested) && (validate( selectNodes.item(i) ) == false) )
			error = true;
	}
	for(var i=0; i<textareaNodes.length; i++ ) {
		if( !getCorrectAttribute(textareaNodes.item(i),"isNested",textareaNodes.item(i).isNested) && (validate( textareaNodes.item(i) ) == false) )
			error = true;
		
	}



	// Display error at the top of the page
	if ( (error==false) && (gSubmitOnce)) {
	     gSubmitOnce = false;
		window.location = target;
	}
	else if( error==true ) {
		var errorTD = getElementByIdOrByName("error1");
		if( errorTD )	{
			var errorText = makeErrorMsg('ERR122');
			setText(errorTD, errorText);
			setAttributeClass(errorTD,"error");
		}
		return;
	}







}

function promptForm() {

        var target = "";
        var confirmMsg;

        if(arguments.length > 0)
        target = arguments[0];
        if(arguments.length > 1)
           confirmMsg = arguments[1];
        else
           confirmMsg = "Are you Sure?";

		if (confirm(confirmMsg))
			window.location = target;
		else
			return;

}

function noCreateNotificationAccess()
{
    window.alert("The ability to send a notification for this disease will be available in an upcoming release.");
}




function DateSubtract(startDate, numDays, numMonths, numYears)
{
	var returnDate = new Date(startDate.getTime());
	var yearsToSubtract = numYears;

	var month = returnDate.getMonth()	- numMonths;
	if (month < 0)
	{
		yearsToSubtract = Math.floor((month+12)/12);
		month += 12*yearsToSubtract;
		yearsToSubtract -= numYears;
	}
	returnDate.setMonth(month);
	returnDate.setFullYear(returnDate.getFullYear()	- yearsToSubtract);

	returnDate.setTime(returnDate.getTime()-60000*60*24*numDays);

	return returnDate;

}

function YearSubtract(startDate, numYears)
{
	return DateSubtract(startDate,0,0,numYears);
}

function MonthSubtract(startDate, numMonths)
{
	return DateSubtract(startDate,0,numMonths,0);
}

function DaySubtract(startDate, numDays)
{
	return DateSubtract(startDate,numDays,0,0);
}

function confirmSubmitOnce(href,link) {

	   var labelList = new Array();
	   var confirmMsg = makeErrorMsg('ERR108',labelList.concat(""));
	   

		if (confirm(confirmMsg))
		{						
			
			href.href = link;			
			
		} 		
	
}


function changeSubmitOnce(linkurl)
{
	/**
	 * The URL for the popup for "View Events" in ViewLabReportLoad.java
	 * starts with "javascript".  Suppressing the "click more than once"
	 * for this popup is undesirable.  If you can think of a better way
	 * to do this make it so.  
	 */
	var isJsPopUp = (linkurl.toString().substring(0,25).toUpperCase() == "JAVASCRIPT:FUNCTION POPUP") || (linkurl.toString().substring(0,25).toUpperCase() == "JAVASCRIPT:FUNCTION%20POP");
	
	if (!isJsPopUp)
	{
		if(gSubmitOnce)
		{
			gSubmitOnce = false;
		}
		else
		{
			linkurl.href="#";
			return false;
		}
	}
}

/**
 * Common functionality for validating date which checks for 
 * proper format and validates against 12/31/1875
 */
function basicDateVal(element) 
{
	var labelList = new Array();
	var errorMessage = "";
	var errorText = "";
	
	var tdErrorCell = getTdErrorCell( element );
	var elementFieldLabel = getCorrectAttribute(element,"fieldLabel",element.fieldLabel);
	var elementRequired = getCorrectAttribute ( element, "required", element.required);
	//alert('inside testFunct, element value = ' + getCorrectAttribute(element,"value",element.value));
				if (isblank(getCorrectAttribute(element,"value",element.value)) && (elementRequired && elementRequired=="true"))
				{
					
					if( !elementFieldLabel )
						errorText = "This is a required field"
					else
						errorText = makeErrorMsg('ERR023', labelList.concat(elementFieldLabel));
					
					if( tdErrorCell.innerText == "" ){
					  	setText(tdErrorCell,errorText);
					  	
						
					}
					else{
					  	setText(tdErrorCell,errorText);
					  	
						
					}
					setAttributeClass(tdErrorCell,"error");
					return false;

				} else if (isblank(getCorrectAttribute(element,"value",element.value))) {
					return true;

				} else {
					// First check if it is in mm/dd/yyyy format
					if (!isDate( getCorrectAttribute(element,"value",element.value) ) ) {
						
						if( !elementFieldLabel)
							errorText = "Please enter a valid date using this format:  'mm/dd/yyyy'."
						else
							
							errorText = makeErrorMsg('ERR003', labelList.concat(elementFieldLabel));


						if( tdErrorCell.innerText == "" ){
						  	setText(tdErrorCell,errorText);
						  	
						}
						else{
							tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
							tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
						  	
						}
						setAttributeClass(tdErrorCell,"error");
						

						return false;

					// if is in mm/dd/yyyy format, check if it is later than 12/31/1875
					} else	if (CompareDateStrings(getCorrectAttribute(element,"value",element.value), "12/31/1875") == -1 )  {					
						
						//errorText = element.fieldLabel + " must be from 12/31/1875 to today's day.  Please correct the data and try again.";
						errorText = makeErrorMsg('ERR004', labelList.concat(elementFieldLabel));

						if( tdErrorCell.innerText == "" ){
						  	setText(tdErrorCell,errorText);
						  	
						}
						else{
						  	tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
						  	tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
						}
						setAttributeClass(tdErrorCell,"error");
						
						return false;
						

					} else {

						return true;
					}
				}
	
}//basicDateVal

/**
 * Common functionality for validating date against DOB  
 */

function advanceDateVal(element) 
{
	var labelList = new Array();
	var errorMessage = "";
	var errorText = "";
	var tdErrorCell = getTdErrorCell( element );
	// Get value of another date that we need to compare to.
	var dateAfter = "";
	if( getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef) )
		dateAfter = getElementByIdOrByName( getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef) ).value;
	       // if is in mm/dd/yyyy format, check if it in a right range.
		if ((CompareDateStrings(getCorrectAttribute(element,"value",element.value), dateAfter) == -1))  {
			//errorText = "Please enter a " + element.fieldLabel + " that is after the Date of Birth.";
			
			var elementFieldLabel = getCorrectAttribute(element,"fieldLabel",element.fieldLabel);
			var elementDateAfterRef = getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef);
			var elementDateAfterRefIdOrName = getElementByIdOrByName( elementDateAfterRef );
			var elementDateAfterRefIdOrNameFieldLabel = getCorrectAttribute(elementDateAfterRefIdOrName,"fieldLabel",elementDateAfterRefIdOrName.fieldLabel);
			errorText = makeErrorMsg('ERR074', labelList.concat(elementFieldLabel).concat(elementDateAfterRefIdOrNameFieldLabel));

			if(elementFieldLabel == "Date of Death")
			{
			//errorText = "Please enter the date for Date of Death field that is greater than (Date of Birth) and try again."
			//errorText = makeErrorMsg('ERR074', labelList.concat(element.fieldLabel),getElementByIdOrByName( element.dateAfterRef ).fieldLabel);
			errorText = makeErrorMsg('ERR074', labelList.concat(elementFieldLabel).concat(elementDateAfterRefIdOrNameFieldLabel));
			}
			if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				  
			}
			else{
				  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
				  tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
			}
			setAttributeClass(tdErrorCell,"error");
			

			return false;
		}
		
		return futureDateVal(element);


}//advanceDateVal

/**
 * Common functionality for validating date against future date  
 */

function futureDateVal(element) 
{
	var labelList = new Array();
	var errorMessage = "";
	var errorText = "";
	//alert('inside futureDateVal');
	var tdErrorCell = getTdErrorCell( element );
	if ((CompareDateStringToToday(getCorrectAttribute(element,"value",element.value)) == 1)) {	

		setText(tdErrorCell,"");
		
		
		//errorText = "Please enter " + element.fieldLabel + " between 12/31/1875 and today's date.";
		errorText = makeErrorMsg('ERR004', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));

		if( tdErrorCell.innerText == "" ){
			setText(tdErrorCell,errorText);
			
		}
		else{
			tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
		}
		setAttributeClass(tdErrorCell,"error");
		

		return false;
	} else {
		return true;
	}					


}//futureDateVal

/**
 * Common functionality for validating date against given
 * two dates viz., dateBefore and dateAfter
 */

function compareDate(element) {

	//alert('compareDateVal');
	var dateAfter = "";
	var dateBefore = "";
	var errorText="";
	var labelList = new Array();
			
	errorMsg = getCorrectAttribute(element,"errorMessage",element.errorMessage);
	var tdErrorCell = getTdErrorCell( element );
	
	if( getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef) )
		dateAfter = getElementByIdOrByName( getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef) ).value;
         

	if( getCorrectAttribute(element,"dateBeforeRef",element.dateBeforeRef) )

		dateBefore = getElementByIdOrByName( getCorrectAttribute(element,"dateBeforeRef",element.dateBeforeRef) ).value;


	 if( (dateAfter!="")&&(CompareDateStrings(getCorrectAttribute(element,"value",element.value),dateAfter)==-1) ) {
				
		
		errorText = makeErrorMsg('ERR092', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)).concat('Date of Birth'));		
		if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
		}
		else{
			tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
			  
		}
		setAttributeClass(tdErrorCell,"error");
		
		return false;

	 } 
	 else {
		return true;
	}


} //compareDate

/**
 * Common functionality for validating date against given
 * two dates viz., dateBefore and dateAfter
 */

function compareDateVal(element) {

	//alert('compareDateVal');
	var dateAfter = "";
	var dateBefore = "";
	var errorText="";
	var labelList = new Array();
			
	errorMsg = getCorrectAttribute(element,"errorMessage",element.errorMessage);
	var tdErrorCell = getTdErrorCell( element );
	var elementDateAfterRef = getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef);
	var elementDateBeforeRef = getCorrectAttribute(element,"dateBeforeRef",element.dateBeforeRef);
	var elementDateAfterRefIdOrName = getElementByIdOrByName(elementDateAfterRef);
	var elementDateBeforeRefIdOrName = getElementByIdOrByName(elementDateBeforeRef);
	var elementDateBeforeRefIdOrNameFieldLabel;
	
	if(elementDateBeforeRefIdOrName!=null && elementDateBeforeRefIdOrName !=undefined)
		elementDateBeforeRefIdOrNameFieldLabel= getCorrectAttribute(elementDateBeforeRefIdOrName,"fieldLabel",elementDateBeforeRefIdOrName.fieldLabel);
	
	if(elementDateAfterRef)
		dateAfter = elementDateAfterRefIdOrName.value;
        if(elementDateBeforeRef)

		dateBefore = elementDateBeforeRefIdOrName.value;


	 if( (dateAfter!="")&&(CompareDateStrings(dateAfter, getCorrectAttribute(element,"value",element.value))==-1) ) {
				
		var elementFieldLabel = getCorrectAttribute(element,"fieldLabel",element.fieldLabel);
		var elementDateAfterRefIdOrNamefieldLabel = getCorrectAttribute(elementDateAfterRefIdOrName, "fieldLabel", elementDateAfterRefIdOrName.fieldLabel);
		errorText = makeErrorMsg('ERR006', labelList.concat(elementFieldLabel).concat(elementDateAfterRefIdOrNamefieldLabel));		
		if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
		}
		else{
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			  tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
		}
		setAttributeClass(tdErrorCell,"error");
		
		return false;

	 } else if( (dateBefore!="")&&(CompareDateStrings(getCorrectAttribute(element,"value",element.value), dateBefore)==-1) ) {
	 
	
		//alert('element.fieldLabel = ' +element.fieldLabel);
		var elementFieldLabel=getCorrectAttribute(element,"fieldLabel",element.fieldLabel);
		
		  errorText = makeErrorMsg('ERR006', labelList.concat(elementDateBeforeRefIdOrNameFieldLabel.concat(elementFieldLabel)));
		if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
		}
		else{

			setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);	
		}
		setAttributeClass(tdErrorCell,"error");
		
		return false;

	} else {
		return true;
	}


} //compareDateVal
function compareDateValForDeath(element) {

	var dateAfter = "";
	var dateBefore = "";
	var errorText="";
	var labelList = new Array();
			
	errorMsg = getCorrectAttribute(element,"errorMessage",element.errorMessage);
	
	var elementDateAfterRef = getCorrectAttribute(element,"dateAfterRef",element.dateAfterRef);
	var elementDateBeforeRef = getCorrectAttribute(element,"dateBeforeRef",element.dateBeforeRef);
	var elementDateAfterRefIdOrName = getElementByIdOrByName(elementDateAfterRef);
	var elementDateBeforeRefIdOrName = getElementByIdOrByName(elementDateBeforeRef);
	var elementDateAfterRefIdOrNameFieldLabel;
	if(elementDateAfterRefIdOrName!=null && elementDateAfterRefIdOrName !=undefined)
		elementDateAfterRefIdOrNameFieldLabel= getCorrectAttribute(elementDateAfterRefIdOrName, "fieldLabel",elementDateAfterRefIdOrName.fieldLabel);
	
	var tdErrorCell = getTdErrorCell( element );
	
	if( elementDateAfterRef)
		dateAfter = elementDateAfterRefIdOrName.value;


	if(elementDateBeforeRef)

		dateBefore = elementDateBeforeRefIdOrName.value;


	 if( (dateAfter!="")&&(CompareDateStrings(dateAfter, getCorrectAttribute(element,"value",element.value))==-1) ) {
				
		//alert('element.fieldLabel = ' +element.fieldLabel);
		errorText = makeErrorMsg('ERR074', concat(elementDateAfterRefIdOrNameFieldLabel).labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));		
		if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
				
		}
		else{
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			  tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
				
		}
		setAttributeClass(tdErrorCell,"error");
		
		return false;

	 } else if( (dateBefore!="")&&(CompareDateStrings(getCorrectAttribute(element,"value",element.value), dateBefore)==-1) ) {
	 
	
		//alert('element.fieldLabel = ' +element.fieldLabel);
		var elementDateBeforeRefIdOrName= getElementByIdOrByName(elementDateBeforeRef);
		  errorText = makeErrorMsg('ERR074', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)).concat(getCorrectAttribute(elementDateBeforeRefIdOrName,"fieldLabel",elementDateBeforeRefIdOrName.fieldLabel)));
		if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
				
		}
		else{
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			  tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
				
		}
		setAttributeClass(tdErrorCell,"error");
		
		return false;

	} else {
		return true;
	}


} //compareDateValForDeath

   function RUB118() 
   {
   	var labelList = new Array();
	var errorMessage = "";
	var errorText = "";
   	var RUB118node = getElementByIdOrByName("proxy.observationVO_s[100].obsValueDateDT_s[0].fromTime_s");
    	var dateOfBirthNode = getElementByIdOrByName("hiddenDateOfBirth");
       	var tdErrorCell = getTdErrorCell( RUB118node );
            if (isblank(RUB118node.value)){
    	         return false;
            }
            else 
            {	
    	  	var labelList = new Array();
              	// First check if it is in mm/dd/yyyy format
              	if (!isDate( RUB118node.value ) &&(getCorrectAttribute(RUB118node,"fieldLabel",RUB118node.fieldLabel))) 
              	{	
                        		    
     		    //errorText = RUB118node.fieldLabel + " must be in format of mm/dd/yyyy.  Please correct the data and try again.";   			
    		    errorText = makeErrorMsg('ERR003', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel))); 		    
    		    setText(tdErrorCell,errorText);
    		    
    		    setAttributeClass(tdErrorCell,"error");
    		    
            	return false;		
        	//return true;
                }//if
             }//else
   	     
   	    // no date of birth
   	    //04/16 changed the code according latest ps
	    if(dateOfBirthNode.value==""||dateOfBirthNode.value!="")
	    { 
	        if (CompareDateStrings(RUB118node.value, "12/31/1875") == -1) 
	        //	|| (CompareDateStringToToday(RUB118node.value) == 1))	
	        {	
	        	//check if there already is an error msg there
	        	//errorText = makeErrorMsg('ERR004', labelList.concat(element.fieldLabel));
	        
	        	//errorText = RUB118node.fieldLabel + " must be greater than 12/31/1875.  Please correct the data and try again.";
	        	errorText = makeErrorMsg('ERR068', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));	        
	        	setText(tdErrorCell,errorText);
	        	
	        	setAttributeClass(tdErrorCell,"error");
	        	
	        	//return true;
	        	return false;
	       	}
	     }
	     //with date of birth 
	/*   else
	     {
	        if ((CompareDateStrings(RUB118node.value, dateOfBirthNode.value) == -1) ||
	           (CompareDateStringToToday(RUB118node.value) == 1))	
	        {	
	          //check if there already is an error msg there
	          var errorText = RUB118node.fieldLabel + " must be greater than " + dateOfBirthNode.value + ".  Please correct the data and try again.";
	         	        	        
	          setText(tdErrorCell,errorText);
	        
	          setAttributeClass(tdErrorCell,"error");
	          return false;
	       	}
	      }*/
              	     
   	   return true;  
   	
   }
function requiredForNotification(element){

		//var notificationExists = pNotificationExits;
		var labelList = new Array();
		var tdErrorCell = getTdErrorCell(element);
		var errorText = makeErrorMsg('ERR135', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));
		var notificationExists = getElementByIdOrByName("NotificationExists");
		
		if(isblank(getCorrectAttribute(element,"value",element.value)) && notificationExists.value=="true")
		{	
		    
		    if( tdErrorCell.innerText == "" ){
		    	setText(tdErrorCell,errorText);
		    	
		    	
		    }
		    else{
		    	tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		    	tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
		    	
		    }
		    	setAttributeClass(tdErrorCell,"error");
		    	
			return false;
		}
		
}

function abcStateCaseRequiredFieldValidator(element){

		//var notificationExists = pNotificationExits;
		var labelList = new Array();
		var tdErrorCell = getTdErrorCell(element);
		var errorText = makeErrorMsg('ERR135', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));
		var notificationExists = getElementByIdOrByName("NotificationExists");
		
		var abc = "";
		var abcsInd ="";
		var abcCheckBox=getElementByIdOrByName('proxy.observationVO_s[0].obsValueCodedDT_s[0].code');
		//alert("Is abcCheckbox checked: " + getElementByIdOrByName('proxy.observationVO_s[0].obsValueCodedDT_s[0].code').value);
		if(getElementByIdOrByName('ABCIndicator')!=null)
		{
			abcsInd =getElementByIdOrByName('ABCIndicator').value;
		}
		if(abcCheckBox !=null)
		{
			abcCheckBox = abcCheckBox.value;
		}
		if(abcsInd=="T" && abcCheckBox=="T")
		{
			abc = "T";
		}
		//alert("My abcCheckBox = " + abcCheckBox + " abc : "+abc);
		//alert("notificationExists.value: " + notificationExists.value);
		if(isblank(getCorrectAttribute(element,"value",element.value)) && notificationExists.value=="true" && abc=="T")
		{	
				    
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
				
			}
			else{
				tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
				tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
				
			}
				setAttributeClass(tdErrorCell,"error");
				
				return false;
		}		

}
function submitRequiredForNND()
{
     
     var contextAction = getElementByIdOrByName("ContextAction");
	 contextAction.value="EditInvestigation";
	 submitForm();
}

function validateDatasourceColumnForm() {

	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("dscError"));
	
	var name = getElementByIdOrByName("column_name").value;
	var title= getElementByIdOrByName("column_title").value;
	var type = getElementByIdOrByName("column_type_code").value;
	var reportFilterInd = getElementByIdOrByName("report_filter_ind").checked;
	
	if(name == "" || title == "" || type == "") {
	
		tdErrorCell.innerText = "Please enter all the required fields";
		tdErrorCell.textContent = "Please enter all the required fields";
			setAttributeClass(tdErrorCell,"error");
			
			return false;
	}

	if(getElementByIdOrByName("availabeDSColumnMaxLength") != null) {
	
		var maxLnString = getElementByIdOrByName("availabeDSColumnMaxLength").value;

		var begin = maxLnString.indexOf(name) + name.length + 1;
		var end = maxLnString.substring(begin);
		var retVal = end.substring(0,end.indexOf("|"));

		getElementByIdOrByName("column_max_len").value = retVal;
	}
	
	
	if(reportFilterInd) {
		
		
		var srtTableNm = getElementByIdOrByName("srtTableNm").value;
		var CodeOrDesc = getElementByIdOrByName("CodeOrDesc").value;

	
		if(srtTableNm=="") {
		
			
			tdErrorCell.innerText = "Please select SRT Table";
			tdErrorCell.textContent = "Please select SRT Table";
			setAttributeClass(tdErrorCell,"error");
			
			
		} else if(srtTableNm == "CODE_VALUE_GENERAL" || srtTableNm == "CODE_VALUE_CLINICAL") {
		
			var codesetNm1 = getElementByIdOrByName("codesetNm1").value;
			var codesetNm2 = getElementByIdOrByName("codesetNm2").value;
			
			if(codesetNm1.length > 0) {
			
				getElementByIdOrByName("codesetNm").value=codesetNm1;
				
			} else if(codesetNm2.length > 0) {
			
				getElementByIdOrByName("codesetNm").value=codesetNm2;	
			}
			
			if(codesetNm1=="" && codesetNm2=="") {
			
				tdErrorCell.innerText = "Codeset needs to be entered for the selected SRT Table";
				tdErrorCell.textContent = "Codeset needs to be entered for the selected SRT Table";
				setAttributeClass(tdErrorCell,"error");
				
			
			} else if(CodeOrDesc=="") {
		
				tdErrorCell.innerText = "Please select Store Code or Description field";
				tdErrorCell.textContent = "Please select Store Code or Description field";
				setAttributeClass(tdErrorCell,"error");
				
						
			} else {
			
				setText(tdErrorCell,"");
				
				setAttributeClass(tdErrorCell,"none")
				
				submitForm();	
			}
		
		}  else {
			if(CodeOrDesc=="") {
		
				tdErrorCell.innerText = "Please select Store Code or Description field";
				tdErrorCell.textContent = "Please select Store Code or Description field";
				setAttributeClass(tdErrorCell,"error");
				
								
			} else {
				setText(tdErrorCell,"");
				
				setAttributeClass(tdErrorCell,"none")
				
				submitForm();	
			}	
		}
		
		return false;		

	} else {
		setText(tdErrorCell,"");
		
		setAttributeClass(tdErrorCell,"none")
		
		submitForm();		
	}

}

function validateSRTAssociation(element) {

	if(getCorrectAttribute(element,"value",element.value) == "STRING") {
	
		getElementByIdOrByName("associateCodeSet").disabled=false;
	} else {
		if(getElementByIdOrByName("report_filter_ind").checked)
			nestedElementsController('firstCE','associateCodeSet');
		getElementByIdOrByName("report_filter_ind").checked = false;
			
		getElementByIdOrByName("associateCodeSet").disabled=true;
	}
}

function deleteDataSourceConfirm() {

	
	var confirmMsg = "Are you sure you want to Delete this DataSource?";

	if (confirm(confirmMsg))
		submitForm();
	else
		return;
			
}

function deleteReportConfirm() {

	var confirmMsg = "Are you sure you want to Delete this Report?";

	if (confirm(confirmMsg))
		submitForm();
	else
		return;
			
}

function deleteReportSectionConfirm() {

	var confirmMsg = "Are you sure you want to Delete this Section? If you delete this section, all the reports assigned to this section will be assigned to DEFAULT section.";

	if (confirm(confirmMsg))
		submitForm();
	else
		return;
			
}

function deleteReportFilterConfirm() {

	var confirmMsg = "Are you sure you want to delete the Report Filter?";

	if (confirm(confirmMsg))
		submitForm();
	else
		return;

}

function validateReportFilterForm() {

	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("rpfError"));
	var filter = getElementByIdOrByName("filter_uid").value;
	
	if(filter == "") {
	
		tdErrorCell.innerText = "Please select a Report Filter.";
		tdErrorCell.textContent = "Please select a Report Filter.";
		setAttributeClass(tdErrorCell,"error");
		
		return false;	
	}
	
	if(filter==1 || filter==2 || filter==3 || filter==8 || filter==9 || filter==10) {
		
		var type = getElementByIdOrByName("reportFilterType").value;
		var column = getElementByIdOrByName("column_uid").value;
		
		if(type == "" || column == "") {
		
			tdErrorCell.innerText = "Please enter all the required fields";
			tdErrorCell.textContent = "Please enter all the required fields";
			setAttributeClass(tdErrorCell,"error");
			
			return false;		
		}else {
			
			setText(tdErrorCell,"");
			
			setAttributeClass(tdErrorCell,"none")
			
			submitForm();	
		}
	
	} else if(filter==5 || filter==6 || filter==11 || filter==12 ) {
	
		var column = getElementByIdOrByName("column_uid").value;
		if(column == "") {		
			tdErrorCell.innerText = "Please enter all the required fields";
			tdErrorCell.textContent = "Please enter all the required fields";
			setAttributeClass(tdErrorCell,"error");
			
			return false;		
		} else {
			
			setText(tdErrorCell,"");
			
			setAttributeClass(tdErrorCell,"none")
			
			submitForm();	
		}
	
	} else {
	
		setText(tdErrorCell,"");
		
		setAttributeClass(tdErrorCell,"none")
		
		submitForm();
	}
}


function resetReportFilterFields() {

	getElementByIdOrByName("reportFilterType_textbox").value="";
	getElementByIdOrByName("reportFilterType").value="";
	
	getElementByIdOrByName("column_uid_textbox").value="";
	getElementByIdOrByName("column_uid").value="";

	nestedElementsController('','filter_uid');
}

function validateReportAdminDS() {


	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("dsError"));

	setText(tdErrorCell,"");
	
	setAttributeClass(tdErrorCell,"none")
	
	
	var errorMsg = "There is no data mart by that name. Please enter a name that matches the name of an existing data mart.  Note that data source name must start with 'nbs_ods.', 'nbs_rdb.', 'nbs_msg.', or 'nbs_srt.' ";
	

	var name = trimBlanks(getElementByIdOrByName("data_source_name").value);
	var title= getElementByIdOrByName("data_source_title").value;
	var desc = getElementByIdOrByName("desc_txt").value;	

	if(name == "" || title == "" || desc == "") {
	
		tdErrorCell.innerText = "Please enter all the required fields";
		tdErrorCell.textContent = "Please enter all the required fields";
			setAttributeClass(tdErrorCell,"error");
			
			return false;
	}
	
	if(StartsWith(name.toUpperCase(), "NBS_ODS.") || StartsWith(name.toUpperCase(), "NBS_RDB.") || StartsWith(name.toUpperCase(), "NBS_MSG.") || StartsWith(name.toUpperCase(), "NBS_SRT.")) {
		
		if(name.length <= 8) {
			tdErrorCell.innerText = errorMsg;
			tdErrorCell.textContent = errorMsg;
			setAttributeClass(tdErrorCell,"error");
			
			return false;		
		
		} 
	
	} else {
		tdErrorCell.innerText = errorMsg;
		tdErrorCell.textContent = errorMsg;
			setAttributeClass(tdErrorCell,"error");
			
			return false;	
	
	}
	setText(tdErrorCell,"");
	
	setAttributeClass(tdErrorCell,"none")	
	submitForm();	
}

function validateReportAdminDSForEdit() {

	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("dsError"));

	setText(tdErrorCell,"");
	
	setAttributeClass(tdErrorCell,"none")	
	

	var title= getElementByIdOrByName("data_source_title").value;
	var desc = getElementByIdOrByName("desc_txt").value;	

	if(title == "" || desc == "") {
	
		setText(tdErrorCell, "Please enter all the required fields");
		setAttributeClass(tdErrorCell,"error");
		
		return false;
	}
	
	setText(tdErrorCell,"");
	
	setAttributeClass(tdErrorCell,"none")
	
	submitForm();	

}


function populateDSTitle(element) {

	var dsTitleList = getElementByIdOrByName("listds_title").value;
	var begin = 0;
	var dsName = "|" + getCorrectAttribute(element,"value",element.value) + "$";
	var dsTitle = getElementByIdOrByName("ds_title");

	
	if(getCorrectAttribute(getCorrectAttribute(element,"value",element.value),"length",getCorrectAttribute(element,"value",element.value).length) == 2)
		begin = dsTitleList.indexOf(dsName)+4;
	else if(getCorrectAttribute(getCorrectAttribute(element,"value",element.value),"length",getCorrectAttribute(element,"value",element.value).length) == 1)
		begin = dsTitleList.indexOf(dsName)+3;
	else if(getCorrectAttribute(getCorrectAttribute(element,"value",element.value),"length",getCorrectAttribute(element,"value",element.value).length) == 3)
		begin = dsTitleList.indexOf(dsName)+5;
	else if(getCorrectAttribute(getCorrectAttribute(element,"value",element.value),"length",getCorrectAttribute(element,"value",element.value).length) == 4)
		begin = dsTitleList.indexOf(dsName)+6;	
		

	var end = dsTitleList.substring(begin);
	var retVal = end.substring(0,end.indexOf("|"));

	dsTitle.innerHTML = retVal;

}

function checkAvailableDSColumns() {

	var msg = "All Columns have been added for this Data Source. There are no additional Columns to add at this time.";
	var isAvailable = getElementByIdOrByName("dsColumnsAvailable").value;
	
	if (isAvailable == "true")
		window.alert(msg);
	else
		submitForm();

}



function validateReportSectionForEdit() {

	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("rsError"));


	setText(tdErrorCell,"");
	
	setAttributeClass(tdErrorCell,"none")	
	

	var desc= getElementByIdOrByName("section_desc_txt").value;
	var comment = getElementByIdOrByName("comments").value;	

	if(desc == "" || comment == "") {
	
		setText(tdErrorCell, "Please enter all the required fields");
		setAttributeClass(tdErrorCell,"error");
		
		
		return false;
	}
	
	setText(tdErrorCell,"");
	
	setAttributeClass(tdErrorCell,"none")	
	submitForm();	

}

function doDoubleDareDelete(action,confMsg)
{
	//var choice = confirm("Are you sure you want to delete the data source column?");
	var choice = confirm(confMsg);
	if (choice) {
		var frm = getElementByIdOrByName("nedssForm");
		frm.setAttribute("action", action);
		document.forms[0].submit();
	}
	else
		return;
}
