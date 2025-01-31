/**
 *  DMBSpecific.js.
 *  DMB runtime script library.
 *  @author Karthik Muthukumaraswamy, Gregory Tucker
 */

/**
    Global variables used to set error tabs
*/
var activeTabClass = ".blockTab";	
var hiddenTabClass = ".hiddenTab";
var _top = "top";
var _bottom = "bottom";
var disabledBottomTabHandleClass = ".ongletTextDis1";
var errorBottomTabHandleClass    = ".ongletTextErr1";
var enabledBottomTabHandleClass  = ".ongletTextEna1";
var tabHandleIdPrefix = "tabs0head";
var tabBodyIdPrefix = "tabs0tab";
   
		  
function checkEmail(emField){ //reference to email field passed as argument
	
	var fieldValue = emField.value // store field's entire value in variable

	// Begin Valid Email Address Tests
	
	//if field is not empty
	if(fieldValue != ""){
	var atSymbol = 0
	
	//loop through field value string
	for(var a = 0; a < fieldValue.length; a++){
	
	//look for @ symbol and for each @ found, increment atSymbol variable by 1
	if(fieldValue.charAt(a) == "@"){
	atSymbol++
	}
	
	}
	
	// if more than 1 @ symbol exists
	if(atSymbol > 1){
	// then cancel and don't submit form
	alert("Please enter a valid Email Address")
	return false
	}
	
	// if 1 @ symbol was found, and it is not the 1st character in string
	if(atSymbol == 1 && fieldValue.charAt(0) != "@"){
	//look for period at 2nd character after @ symbol
	var period = fieldValue.indexOf(".",fieldValue.indexOf("@")+2)
	
	// "." immediately following 1st "." ?
	var twoPeriods = (fieldValue.charAt((period+1)) == ".") ? true : false
	
	//if period was not found OR 2 periods together OR field contains less than 5 characters OR period is in last position
	if(period == -1 || twoPeriods || fieldValue.length < period + 2 || fieldValue.charAt(fieldValue.length-1)=="."){
	// then cancel and don't submit form
	alert("Please enter a valid Email Address")
	return false
	}
	
	}
	// no @ symbol exists or it is in position 0 (the first character of the field)
	else{
	// then cancel and don't submit form
	alert("Please Enter A Valid Email Address")
	return false
	}
	} // ok if field is empty
	//all tests passed, submit form
	return true
}

function checkCensusTract(fieldElement){
	var badCensusMsg = "Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.";
	var censusTract = fieldElement.value;
	if (censusTract == "")
		return;
	if(censusTract !=null){
    	var re = /^(\d{4}|\d{4}.(?!99)\d{2})$/;
    	if(!re.test(censusTract )){
    		fieldElement.value="";
    		alert(badCensusMsg);
    		return false;
 		}
 	}
 	if (censusTract == "0000" || censusTract.indexOf(".00") != -1) {
 		fieldElement.value="";
 		alert(badCensusMsg);
	    	return false;
 	}
}

/**
* Checks if the character currently entered in the text box 
* is a valid day of month or not. If not, it returns the text that 
* was present before this character was entered.
* Uses JS's isInteger() JS method for validation
*/
function isDayOfMonthCharEntered(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        var varDay = parseInt(s+c);
        if((isInteger(c)) && (varDay>0) && (varDay<32)) 
            s += c;
         pTextbox.value = s;
    }
}

/**
* Checks if the character currently entered in the text box 
* is a valid day of month or not. If not, it returns the text that 
* was present before this character was entered.
* Uses JS's isInteger() JS method for validation
*/
function isMonthCharEntered(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        var varMonth = parseInt(s+c);
        if((isInteger(c)) && (varMonth>0) && (varMonth<13)) 
            s += c;
         pTextbox.value = s;
    }
}

/**
 * Parse the document and determine if there are any validation errors. 
 * Validation checks include:
 * 1. Required fields 
 * 2. Structured numeric format compliance
 * 3. Related units format
 * 4. Repeating batch subsections, no orphaned data
 * 
 * @return true if errors are found, false otherwise.
 */
function pgCheckForErrorsOnSubmit() 
{
    var errorElts = new Array();
    var errorLabels = new Array();
    
    
    //Check if NND notification exists
    var notificationCheck = $j("#NotificationExists")[0];
    var notifReqFields = $j("#NotificationReqFields")[0];
    
    if(notificationCheck !=null && notificationCheck.value=='true'
    	//Do we need this??:
				&& getElementByIdOrByName("notificationSection")!=null && getElementByIdOrByName("notificationSection").innerText.indexOf("NND")!=-1){
    		
    	if(notifReqFields!=null && notifReqFields.value!=""){//If there's an existing notification created.
    	
    	  // Let's check if any of the required fields for NND is missing, and if so, add it to the error elements and labels:
    	   var retVal = pgCheckRequiredFieldsNND(notifReqFields.value);
    	   errorElts = errorElts.concat(retVal.elements);
    	   errorLabels = errorLabels.concat(retVal.labels); 
    		
    	}
    		

    }
    // structured numeric fields
    retVal = pgCheckStructuredNumericFields(".structuredNumericField");
    errorElts = errorElts.concat(retVal.elements);
    errorLabels = errorLabels.concat(retVal.labels); 
    
    // related units fields
    retVal = pgCheckRelatedUnitFields(".relatedUnitsField");
    errorElts = errorElts.concat(retVal.elements);
    errorLabels = errorLabels.concat(retVal.labels); 
    

    // call function on page for dynamic rules
    retVal = pgCheckForDynamicRuleErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
           errorElts = errorElts.concat(retVal.elements);
           errorLabels = errorLabels.concat(retVal.labels);
    }
    
    //check for date field format to be mm/dd/yyyy
    retVal = pgCheckDateFieldFormatOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    }
    /*
    //Reported Age and Reported Age Units
    retVal = pgCheckNumberAndUnitsFieldsFormatOnSubmit("INV2001","INV2002");
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    }
    
    //Age at Onset: and Age at Onset: units
    retVal = pgCheckNumberAndUnitsFieldsFormatOnSubmit("INV143","INV144");
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    }
    
    //Illness duration and illness duration units
    retVal = pgCheckNumberAndUnitsFieldsFormatOnSubmit("INV139","INV140");
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    }*/
    
    
    
    var conditionCode = $j("#headerConditionCode").text();
    
    DWREngine.setAsync(false);
    
    JPageForm.isTbConditionCode(conditionCode, function(data) {
    	
    if(data=="true"){
    		
    	
    //MM of Induration Validation for Positive TST
    retVal = pgCheckPositiveSkintestResultFormatOnSubmit("TUB147", "TUB149");
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    	}
    
    
    
    
    //MM of Induration Validation for Negative TST
    retVal = pgCheckNegativeSkintestResultFormatOnSubmit("TUB147", "TUB149");
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    	}
    }});
      
    
    DWREngine.setAsync(true);
   
    
    DWREngine.setAsync(false);
	//Check if State Case ID is unique across investigations in the system.
    retVal =  pgCheckStateCaseNumberUniquenessOnSubmit(conditionCode);
    if (retVal.elements != null &&  retVal.elements != undefined) {
        errorElts = errorElts.concat(retVal.elements);
        errorLabels = errorLabels.concat(retVal.labels);
    	}

    DWREngine.setAsync(true);
  
    
    
    
    
    
    
    
   // call function on page for As Of fields on Edit
    retVal = pgCheckForBlankAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
           errorElts = errorElts.concat(retVal.elements);
           errorLabels = errorLabels.concat(retVal.labels);
    }
    
    // required fields
    var retVal = pgCheckRequiredFields(".requiredInputField");
    	errorElts = errorElts.concat(retVal.elements);
    	errorLabels = errorLabels.concat(retVal.labels); 
   
    
    //TXT_IDTB fields
	 retVal = pgCheckTXT_IDTBFieldsFormatOnSubmit();
	 if (retVal.elements != null &&  retVal.elements != undefined) {
	     errorElts = errorElts.concat(retVal.elements);
	     errorLabels = errorLabels.concat(retVal.labels);
	 }
	    
        	
    	
    	
    	
    	
    // check for orphans in repeating subsections
    retVal = pgCheckRepeatingSubSectionDanglingField();
    if (retVal.elements != null &&  retVal.elements != undefined) {
               errorElts = errorElts.concat(retVal.elements);
               errorLabels = errorLabels.concat(retVal.labels);
    }
    
    //On Lab Reports PBP, check if there's at least 1 Resulted Test entered
    
    if(emptyResultedTestBatchEntry()==true){//no rows in the batch entry
    	var elem = getElementByIdOrByName("NBS_LAB220L");
        errorElts = errorElts.concat(elem);
        var  reqErrStr = buildErrorAnchorLink(elem, "Resulted Test");
        errorLabels.push(reqErrStr + " is required.");
        colorElementLabelRed(elem);   
    }
    
    if (errorElts.length > 0) {
        displayGlobalErrorMessage(errorLabels);
        var tabElts = getDistinctParentTabs(errorElts);
        setErrorTabProperties(tabElts);        
        return true;    
    }
    else {
        return false;
    }
}

/**
 * Parse the document and determine if there are any validation errors. 
 * Validation checks include:
 * 1. Required fields 
 * 2. Structured numeric format compliance
 * 3. Related units format
 * 
 * @return true if errors are found, false otherwise.
 */
function pgCheckForErrorsOnBatchSubsection(subsectionId) 
{
    var errorElts = new Array();
    var errorLabels = new Array();
    
    // required fields
    var reqClass = ".requiredInputField" + subsectionId;
    var retVal = pgCheckRequiredFields(reqClass);
    
    errorElts = errorElts.concat(retVal.elements);
    errorLabels = errorLabels.concat(retVal.labels); 

    
    // structured numeric fields
    var snClass = ".structuredNumericField" + subsectionId;
    retVal = pgCheckStructuredNumericFields(snClass);
    errorElts = errorElts.concat(retVal.elements);
    errorLabels = errorLabels.concat(retVal.labels); 
    
    // related units fields
    var ruClass = ".relatedUnitsField" + subsectionId;
    retVal = pgCheckRelatedUnitFields(ruClass);
    errorElts = errorElts.concat(retVal.elements);
    errorLabels = errorLabels.concat(retVal.labels); 
    
    retVal = pgCheckDateFieldFormatOnBatchSubmit(subsectionId);
    errorElts = errorElts.concat(retVal.elements);
    errorLabels = errorLabels.concat(retVal.labels); 

  
    //Only if TB Condition codes
    var conditionCode = $j("#headerConditionCode").text();
    DWREngine.setAsync(false);
    JPageForm.isTbConditionCode(conditionCode, function(data) {
    	
    	if(data=="true"){
	    	//MM of Induration Validation for Positive TST
		    var testType = $j("#INV290").val() ;
		    if(testType == "TB119"){//Tuberculin Skin Test
			    retVal = pgCheckPositiveSkintestResultFormatOnSubmit("INV291", "LAB628");
			    errorElts = errorElts.concat(retVal.elements);
			    errorLabels = errorLabels.concat(retVal.labels); 
		    }
		    
		    
		    
		  //MM of Induration Validation for Negative TST
		    if(testType == "TB119"){//Tuberculin Skin Test
		    
			    retVal = pgCheckNegativeSkintestResultFormatOnSubmit("INV291", "LAB628");
			    if (retVal.elements != null &&  retVal.elements != undefined) {
			        errorElts = errorElts.concat(retVal.elements);
			        errorLabels = errorLabels.concat(retVal.labels);
			    	}
			    }
		    }
    		});
    
    
    DWREngine.setAsync(true);
    
    
    
    //only need the labels in the batch
    return errorLabels;
}

function pgCheckDateFieldFormatOnBatchSubmit(batchSubsection) {
	var dateElts = $j('img[src="calendar.gif"]');
	var errorElts = new Array();
	var errorLabels = new Array();
	for (var i = 0; i < dateElts.length; i++) {
		if ($j(dateElts[i]).parent().parent().parent().parent().attr("id") == batchSubsection) {
			var dateEltsLabelId = $j(dateElts[i]).parent().children(
					"input[type=text]").attr("id")
					+ "L";
			var dateEltsLabel = getElementByIdOrByName(dateEltsLabelId).innerHTML;
			var dateEltsId = $j(dateElts[i]).parent().children(
					"input[type=text]").attr("id");
			var dateEltsValue = $j(dateElts[i]).parent().children(
					"input[type=text]").attr("value");
			dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
			if (dateEltsId != null && !dateEltsId.endsWith("Date")) {
				if (dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
					var errHtmlStr = dateEltsLabel
							+ " must be in the format of mm/dd/yyyy.";
					errorElts.push(dateEltsId);
					errorLabels.push(errHtmlStr);
					$j("#" + dateEltsLabelId).css("color", "990000");
				} else if (dateEltsValue.match(dateFormat)) {
					var dtArray = dateEltsValue.match(dateFormat);
					var dtMonth = dtArray[1];
					var dtDay = dtArray[3];
					var dtYear = dtArray[5];
					if ((dtMonth < 1 || dtMonth > 12)
							|| (dtDay < 1 || dtDay > 31)
							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
						var errHtmlStr = dateEltsLabel
								+ " must have valid day and month combination for date value.";
						errorElts.push(dateEltsId);
						errorLabels.push(errHtmlStr);
						$j("#" + dateEltsLabelId).css("color", "990000");
					} else {
						$j("#" + dateEltsLabelId).css("color", "black");
					}
				} else {
					$j("#" + dateEltsLabelId).css("color", "black");
				} // corrected
			}
		}
	}
	return {
		elements : errorElts,
		labels : errorLabels
	}
}

function pgCheckDateFieldFormatOnSubmit()
{
	var errorElts = new Array();
    var errorMsgs = new Array();
    
    var dateElts = $j('img[src="calendar.gif"]');
    var j = 0;
    for (var i = 0; i < dateElts.length; i++)
    	{
    	if($j(dateElts[i]).parent().parent().css("display")!="none"){
    	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
    	var dateEltsLabelId=$j(dateElts[i]).parent().children("input[type=text]").attr("id")+"L";
    	var dateEltsLabel=getElementByIdOrByName(dateEltsLabelId).innerHTML;
    	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
    	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
    	
    	 //expression to match required date format
        dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
        if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
        	
        	var a2Str = buildErrorAnchorLink(dateEltsNode[0], dateEltsLabel);  
   		    var errHtmlStr =  a2Str + " must be in the format of mm/dd/yyyy.";
   		 errorMsgs[j] = errHtmlStr;
		 errorElts[j] = getElementByIdOrByName(dateEltsLabelId);
		 j++;
		
		 $j("#"+dateEltsLabelId).css("color", "990000");
          }else if(dateEltsValue.match(dateFormat)){
        	var dtArray = dateEltsValue.match(dateFormat);
        	var	  dtMonth = dtArray[1];
        	var	  dtDay= dtArray[3];
        	var	  dtYear = dtArray[5];
        	if((dtMonth < 1 || dtMonth > 12) || (dtDay < 1 || dtDay> 31) || ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) || ((dtMonth == 2) && (dtDay> 29 || (dtDay ==29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))) ) )
        	{	var a2Str = buildErrorAnchorLink(dateEltsNode[0], dateEltsLabel);  
	   		    var errHtmlStr =  a2Str + " must have valid day and month combination for date value.";
		   		 errorMsgs[j] = errHtmlStr;
				 errorElts[j] = getElementByIdOrByName(dateEltsLabelId);
				 j++;
				 $j("#"+dateEltsLabelId).css("color", "990000");
        	}
        	else{
        		var theColorIs = $j("#"+dateEltsLabelId).css("color");
            	if (theColorIs == "990000") //reset to black only if red
            		$j("#"+dateEltsLabelId).css("color", "black"); //clear color if fields not present
        	}
        }

        else {
        	var theColorIs = $j("#"+dateEltsLabelId).css("color");
        	if (theColorIs == "990000") //reset to black only if red
        		$j("#"+dateEltsLabelId).css("color", "black"); //clear color if fields not present
        }
    	}
}

    //only need the labels in the batch  
    return {elements : errorElts, labels : errorMsgs}  
	
}

/**
 * isVisible: returns false if the element is hidden by himself or any parent, like LDF2 which is hidden by the table NBS_UI_28
 * @param el
 * @returns {Boolean}
 */
function isVisible(el) {
	if(el!=null && el != undefined)
	    if (!el.offsetParent && el.offsetWidth === 0 && el.offsetHeight === 0) {
	        return false;
	    }
    return true;
}


/**
 * pgCheckRequiredFieldsNND: this method is similar to pgCheckRequiredFields but I have removed the checks for batch entries
 * and also build the list of elemenets to check based on the list received as a parameter.
 * So far, the questions identifier related to selects will have L at the end. The rest of them don't. If we find there's
 * any other type of question that needs the L at the end, it will need to be added in line #427. We can always compare how it works with pgCheckRequiredFields.
 * @param requiredFieldsNND
 * @returns {___anonymous25173_25216}
 */
function pgCheckRequiredFieldsNND(requiredFieldsNND)
{
    var errorElts = new Array();
    var errorLabels = new Array();
    var reqdElts = new Array();

    
    var elements = requiredFieldsNND.split(",");
    for(var i=0;i<elements.length;i++){
    	
    	var keyValue=elements[i].split("|");
    	var id = keyValue[0];
    	id=id.replace("[","");
    	id=id.replace("]","");
    	id = id.trim();
    	//If they are selects (single and multiple), add L at the end. Do we need to add any other type of element to this if?? Input text fields don't need it.
    	
    	if($j("#"+id)[0]!=undefined && $j("#"+id)[0]!=null){
	    	if($j("#"+id)[0].type == "select-one" || $j("#"+id)[0].type == "select-multiple")
	    		id = id+"L";
	    	reqdElts.push($j("#"+id)[0]);
    	}
    }
    
    for (var i = 0; i < reqdElts.length; i++)
    {
        if ($j(reqdElts[i]).hasClass("validated") == false)
        {
            var currEltType = "";
            var typeAttr = $j(reqdElts[i]).attr("type");
            if (typeAttr != undefined) {
                currEltType = typeAttr;
            }
            else {
                var nodeName = reqdElts[i].nodeName.toLowerCase();
                currEltType = nodeName;
            }
            //var attrId = $j(reqdElts[i]).attr('id'); //for alert next line
            //alert("currEltType = " + currEltType.toLowerCase() + " id = " + attrId);
            // handle the html input items by type
            switch (currEltType.toLowerCase())
            {
                case 'checkbox':
                    //can't really check if the checkbox should have something selected
                    //var noneChecked = true;
                    //var eltName = $j(reqdElts[i]).attr("name");
                    //var siblings = $j("input[" + eltName + "]");
                    // if (siblings.length == 1) {
                    // 	break;
                    // }
                    break;

                case 'option':
                    var noneSelected = true;
                    var siblings = $j(reqdElts[i]).siblings();
                    for (var s = 0; s < siblings.length; s++)
                    {
                        if (($j(reqdElts[i]).attr("selected") == true  && jQuery.trim($j(reqdElts[i]).val()).length > 0)  ||
                            ($j(siblings[s]).attr("selected") == true  && jQuery.trim($j(siblings[s]).val()).length > 0))
                        {
                            noneSelected = false;
                            break;
                        }
                    }

                    if (noneSelected == true) {
                        // add elt to error elts list
                        errorElts.push(reqdElts[i]);
                        
                        // get the elt title
                        var label = $j($j($j(reqdElts[i]).parent().parent().parent().parent()).find("span[title]").get(0)).html();
                        errorLabels.push(label + " is required.");
                        $j($j(reqdElts[i]).parent().parent().parent().parent()).find("span[title]").css("color", "990000");
                    } 
                    else { 
                        $j($j(reqdElts[i]).parent().parent().parent().parent()).find("span[title]").css("color", "black"); 
                    } //corrected

                    // attach the 'validated' class to siblings to avoid redundant checks
                    $j(reqdElts[i]).siblings().addClass("validated");
                    break;

                case 'span':
                    //used for single-select and multi-select
                    //class put in Label due to conflict
                    var noneSelected = true;
                    var labelNode = reqdElts[i];
                    var labelTitle = $j(labelNode).html();
                    var labelNodeId = $j(labelNode).attr("id");
                   // var isVisib = isVisible(document.getElementById(reqdElts[i].id));
                    
                    
                         //remove the L at the end
		    var selNodeId = labelNodeId.substr(0,labelNodeId.length-1);
		    selNode = getElementByIdOrByName(selNodeId);
		    if (selNode == null) {
		    	//alert("Warning: Problem in pgCheckRequiredFields - please note and call support! Field=" + labelTitle);
		    	break;
		    }
		    //if (selNode.disabled || !isVisib) {
		    	if (selNode.disabled) {
		    	//alert(selNode.type + " " + labelTitle + " is disabled");
		    	break;
		    }
		    
		    //alert(selNode.type + " " + labelTitle + " = " + selNodeId + " length = " + selNode.length);
    		    if(selNode.options != null){
       			for (y = 0; y < selNode.options.length && noneSelected; y++) {
		    		if (selNode.options[y].selected) {
		    		 	if (selNode.options[y].text != "")
		    				noneSelected = false;
		    		}
		    	}
		    }
		    //handle the search (participation) type or read only on edit (e.g. Prog Area, Juris on Lab)
		    if(selNode.options == null){
		    	
		    	//incase of read only question with hidden value on edit pages
			    if(selNode.value!=null){
			    	noneSelected = false;
			    }		    	
		    	var selNodeParText = $j(selNode).text();
		    	//alert("selNodeParText = " + selNodeParText);
		    	if (selNodeParText == null) {
		    		break;
		    	}
		    	if(selNodeParText.length > 0) {
		    		noneSelected = false;
		    	}
		    }
		    
		    //handle the coded with search type (resulted test, for example)
		    var description = document.getElementById(selNodeId+"Description");
		    
		    if(description!=null && description !=undefined)
		    	if(description.textContent!=null && description.textContent!=undefined && description.textContent!='')
		    		noneSelected = false;
		    
		    if (noneSelected) {
		    	 //alert(labelTitle + " has none selected!");
		    	 var theLabel = labelTitle.substr(0,labelTitle.length-1); //remove colon
		    	 var reqErrStr = theLabel;
		    	// if (".requiredInputField" === requiredFieldClassName) //not batch subsection
		    	  	reqErrStr = buildErrorAnchorLink(labelNode, theLabel);
		    	 colorElementLabelRed(labelNode);
		    	 errorLabels.push(reqErrStr + " is required for NND.");
		    	 errorElts.push(labelNode);
		    } else colorElementLabelBlack(labelNode);
		    break;


                case 'textarea':
                    if (jQuery.trim($j(reqdElts[i]).val()) == "") {
                        // add elt to error elts list
                        errorElts.push(reqdElts[i]);
                        
                        // get the elt title
                        var theLabel = getElementLabel(reqdElts[i]);
                        theLabel = theLabel.substr(0,theLabel.length-1); //remove colon
                        var reqErrSt = theLabel;
                   //     if (".requiredInputField" === requiredFieldClassName) //not batch subsection
                        	reqErrStr = buildErrorAnchorLink(reqdElts[i], theLabel);
                        errorLabels.push(reqErrStr + " is required for NND.");
                        colorElementLabelRed(reqdElts[i]);
                    } else { 
                        colorElementLabelBlack(reqdElts[i]); 
                    } //for those colored red now corrected
                    break;

                case 'text':
                	//alert ("checking text " + getElementLabel(reqdElts[i]));

					var theLabel = getElementLabel(reqdElts[i]);
					if(theLabel!=null & theLabel!='undefined'){
						//In case is type organization like reporting facility and it has a value, defect: ND-13614
						
					var typeOrganizationValue = "";
					
						if(isTypeOrganization(reqdElts[i])=="true"){
                    		var id = reqdElts[i].id;
							if(id!=null && id!=null&& id.indexOf("Text")!=-1){
									id = id.substring(0,id.indexOf("Text"));
									typeOrganizationValue=$j("#"+id).html();
										if(typeOrganizationValue ==""){
											errorElts.push(reqdElts[i]);
											theLabel = theLabel.substr(0,theLabel.length-1); //remove colon
											var reqErrStr = theLabel;                       
							//				if (".requiredInputField" === requiredFieldClassName) //not batch subsection
												reqErrStr = buildErrorAnchorLink(reqdElts[i], theLabel);
											errorLabels.push(reqErrStr + " is required for NND.");
											colorElementLabelRed(reqdElts[i]);
											}
										   else { 
											colorElementLabelBlack(reqdElts[i]); 
											} //for those colored red now corrected
						}	
						break;
					}	
                    if (jQuery.trim($j(reqdElts[i]).val()) == "" && typeOrganizationValue =="") {

								// add elt to error elts list
		
		                        errorElts.push(reqdElts[i]);
		
		                        
		
		                       // get the elt title
		
								theLabel = theLabel.substr(0,theLabel.length-1); //remove colon
		
		                        var reqErrStr = theLabel;                       
		
		                    //    if (".requiredInputField" === requiredFieldClassName) //not batch subsection
		
		                        	reqErrStr = buildErrorAnchorLink(reqdElts[i], theLabel);
		
				    	
		
		                        // get the elt title
		
								errorLabels.push(reqErrStr + " is required for NND.");
		
		                        colorElementLabelRed(reqdElts[i]);
							
					}

                    else { 

                        colorElementLabelBlack(reqdElts[i]); 

                    } //for those colored red now corrected

					}
					break;
               case 'hidden' :
                  	var reqId = $j(reqdElts[i]).attr('id');
                    	var reqElt = $j("#" + reqId + "L");
                    if (jQuery.trim($j(reqdElts[i]).val()) == "") {
                        // add elt to error elts list
                        if (reqElt.length == 1) {
                        	errorElts.push(reqElt[0]);
                       		 // get the elt title
                        	errorLabels.push(getElementLabel(reqElt) + " is required for NND.");
                        	colorElementLabelRed(reqElt[0]);
                        }
                    } 
                    else {
                    	if (reqElt.length == 1) {
                        	colorElementLabelBlack(reqElt[0]);
                        }
                    } //for those colored red now corrected
                    break;
            } // switch
        } // if
    } // for
    
    return {elements : errorElts, labels : errorLabels};             
}



/**
 * pgCheckTXT_IDTBFieldsFormatOnSubmit: this method will validate the format of questions with mast TXT_IDTB like Case Id from TB investigations
 */

function pgCheckTXT_IDTBFieldsFormatOnSubmit()

{

	var errorElts = new Array();

   var errorMsgs = new Array();

   

   var txtidtbElts = $j('.txt_idtbField');

   var j = 0;

   for (var i = 0; i < txtidtbElts.length; i++)

   	{


   	if($j(txtidtbElts[i]).parent().parent().css("display")!="none"){

			var txtidtbEltsNode=$j(txtidtbElts[i]).parent().children("input[type=text]");

			var txtidtbEltsLabelId=$j(txtidtbElts[i]).parent().children("input[type=text]").attr("id")+"L";

			var txtidtbEltsLabel=getElementByIdOrByName(txtidtbEltsLabelId).innerHTML;

			var txtidtbEltsId=$j(txtidtbElts[i]).parent().children("input[type=text]").attr("id");

			var txtidtbEltsValue=$j(txtidtbElts[i]).parent().children("input[type=text]").attr("value");

			

			 //expression to match required date format

			var txtidtbFormat = /^[0-9]{4}[./-][A-Z]{2}[./-][A-Z0-9]{9}$/;

			if(txtidtbEltsValue != '' && !txtidtbEltsValue.match(txtidtbFormat)) {

				

				var a2Str = buildErrorAnchorLink(txtidtbEltsNode[0], txtidtbEltsLabel);  

				var errHtmlStr =  a2Str + " must be in the format of YYYY-GA-ABCD12345.";//TODO" Update this text according to the Jira user story??

				errorMsgs[j] = errHtmlStr;

				errorElts[j] = getElementByIdOrByName(txtidtbEltsLabelId);

				j++;

			

				$j("#"+txtidtbEltsLabelId).css("color", "990000");

			  

			} else {

				var theColorIs = $j("#"+txtidtbEltsLabelId).css("color");

				if (theColorIs == "990000") //reset to black only if red

					$j("#"+txtidtbEltsLabelId).css("color", "black"); //clear color if fields not present

				}

			}

}



   //only need the labels in the batch  

   return {elements : errorElts, labels : errorMsgs}  

	

}





/**
    Check that all fields with the styleClass of requiredInputField have values.
    @return Array of elements with errors.
*/
function pgCheckRequiredFields(requiredFieldClassName)
{
    var errorElts = new Array();
    var errorLabels = new Array();
    var reqdElts = $j(requiredFieldClassName);

    //alert("in pgCheckRequiredFields(" + requiredFieldClassName);
    for (var i = 0; i < reqdElts.length; i++)
    {
        if ($j(reqdElts[i]).hasClass("validated") == false)
        {
            var currEltType = "";
            var typeAttr = $j(reqdElts[i]).attr("type");
            if (typeAttr != undefined) {
                currEltType = typeAttr;
            }
            else {
                var nodeName = reqdElts[i].nodeName.toLowerCase();
                currEltType = nodeName;
            }
            //var attrId = $j(reqdElts[i]).attr('id'); //for alert next line
            //alert("currEltType = " + currEltType.toLowerCase() + " id = " + attrId);
            // handle the html input items by type
            switch (currEltType.toLowerCase())
            {
                case 'checkbox':
                    //can't really check if the checkbox should have something selected
                    //var noneChecked = true;
                    //var eltName = $j(reqdElts[i]).attr("name");
                    //var siblings = $j("input[" + eltName + "]");
                    // if (siblings.length == 1) {
                    // 	break;
                    // }
                    break;

                case 'option':
                    var noneSelected = true;
                    var siblings = $j(reqdElts[i]).siblings();
                    for (var s = 0; s < siblings.length; s++)
                    {
                        if (($j(reqdElts[i]).attr("selected") == true  && jQuery.trim($j(reqdElts[i]).val()).length > 0)  ||
                            ($j(siblings[s]).attr("selected") == true  && jQuery.trim($j(siblings[s]).val()).length > 0))
                        {
                            noneSelected = false;
                            break;
                        }
                    }

                    if (noneSelected == true) {
                        // add elt to error elts list
                        errorElts.push(reqdElts[i]);
                        
                        // get the elt title
                        var label = $j($j($j(reqdElts[i]).parent().parent().parent().parent()).find("span[title]").get(0)).html();
                        errorLabels.push(label + " is required.");
                        $j($j(reqdElts[i]).parent().parent().parent().parent()).find("span[title]").css("color", "990000");
                    } 
                    else { 
                        $j($j(reqdElts[i]).parent().parent().parent().parent()).find("span[title]").css("color", "black"); 
                    } //corrected

                    // attach the 'validated' class to siblings to avoid redundant checks
                    $j(reqdElts[i]).siblings().addClass("validated");
                    break;

                case 'span':
                    //used for single-select and multi-select
                    //class put in Label due to conflict
                    var noneSelected = true;
                    var labelNode = reqdElts[i];
                    var labelTitle = $j(labelNode).html();
                    var labelNodeId = $j(labelNode).attr("id");
                   // var isVisib = isVisible(document.getElementById(reqdElts[i].id));
                    
                    
                         //remove the L at the end
		    var selNodeId = labelNodeId.substr(0,labelNodeId.length-1);
		    selNode = getElementByIdOrByName(selNodeId);
		    if (selNode == null) {
		    	//alert("Warning: Problem in pgCheckRequiredFields - please note and call support! Field=" + labelTitle);
		    	break;
		    }
		    //if (selNode.disabled || !isVisib) {
		    	if (selNode.disabled) {
		    	//alert(selNode.type + " " + labelTitle + " is disabled");
		    	break;
		    }
		    
		    //alert(selNode.type + " " + labelTitle + " = " + selNodeId + " length = " + selNode.length);
    		    if(selNode.options != null){
       			for (y = 0; y < selNode.options.length && noneSelected; y++) {
		    		if (selNode.options[y].selected) {
		    		 	if (selNode.options[y].text != "")
		    				noneSelected = false;
		    		}
		    	}
		    }
		    //handle the search (participation) type or read only on edit (e.g. Prog Area, Juris on Lab)
		    if(selNode.options == null){
		    	
		    	//incase of read only question with hidden value on edit pages
			    if(selNode.value!=null){
			    	noneSelected = false;
			    }		    	
		    	var selNodeParText = $j(selNode).text();
		    	//alert("selNodeParText = " + selNodeParText);
		    	if (selNodeParText == null) {
		    		break;
		    	}
		    	if(selNodeParText.length > 0) {
		    		noneSelected = false;
		    	}
		    }
		    
		    //handle the coded with search type (resulted test, for example)
		    var description = document.getElementById(selNodeId+"Description");
		    
		    if(description!=null && description !=undefined)
		    	if(description.textContent!=null && description.textContent!=undefined && description.textContent!='')
		    		noneSelected = false;
		    
		    if (noneSelected) {
		    	 //alert(labelTitle + " has none selected!");
		    	 var theLabel = labelTitle.substr(0,labelTitle.length-1); //remove colon
		    	 var reqErrStr = theLabel;
		    	 if (".requiredInputField" === requiredFieldClassName) //not batch subsection
		    	  	reqErrStr = buildErrorAnchorLink(labelNode, theLabel);
		    	 colorElementLabelRed(labelNode);
		    	 errorLabels.push(reqErrStr + " is required.");
		    	 errorElts.push(labelNode);
		    } else colorElementLabelBlack(labelNode);
		    break;


                case 'textarea':
                    if (jQuery.trim($j(reqdElts[i]).val()) == "") {
                        // add elt to error elts list
                        errorElts.push(reqdElts[i]);
                        
                        // get the elt title
                        var theLabel = getElementLabel(reqdElts[i]);
                        theLabel = theLabel.substr(0,theLabel.length-1); //remove colon
                        var reqErrSt = theLabel;
                        if (".requiredInputField" === requiredFieldClassName) //not batch subsection
                        	reqErrStr = buildErrorAnchorLink(reqdElts[i], theLabel);
                        errorLabels.push(reqErrStr + " is required.");
                        colorElementLabelRed(reqdElts[i]);
                    } else { 
                        colorElementLabelBlack(reqdElts[i]); 
                    } //for those colored red now corrected
                    break;

                case 'text':
                	//alert ("checking text " + getElementLabel(reqdElts[i]));

					var theLabel = getElementLabel(reqdElts[i]);
					if(theLabel!=null & theLabel!='undefined'){
						//In case is type organization like reporting facility and it has a value, defect: ND-13614
						
					var typeOrganizationValue = "";
					
						if(isTypeOrganization(reqdElts[i])=="true"){
                    		var id = reqdElts[i].id;
							if(id!=null && id!=null&& id.indexOf("Text")!=-1){
									id = id.substring(0,id.indexOf("Text"));
									typeOrganizationValue=$j("#"+id).html();
										if(typeOrganizationValue ==""){
											errorElts.push(reqdElts[i]);
											theLabel = theLabel.substr(0,theLabel.length-1); //remove colon
											var reqErrStr = theLabel;                       
											if (".requiredInputField" === requiredFieldClassName) //not batch subsection
												reqErrStr = buildErrorAnchorLink(reqdElts[i], theLabel);
											errorLabels.push(reqErrStr + " is required.");
											colorElementLabelRed(reqdElts[i]);
											}
										   else { 
											colorElementLabelBlack(reqdElts[i]); 
											} //for those colored red now corrected
						}	
						break;
					}	
                    if (jQuery.trim($j(reqdElts[i]).val()) == "" && typeOrganizationValue =="") {

								// add elt to error elts list
		
		                        errorElts.push(reqdElts[i]);
		
		                        
		
		                       // get the elt title
		
								theLabel = theLabel.substr(0,theLabel.length-1); //remove colon
		
		                        var reqErrStr = theLabel;                       
		
		                        if (".requiredInputField" === requiredFieldClassName) //not batch subsection
		
		                        	reqErrStr = buildErrorAnchorLink(reqdElts[i], theLabel);
		
				    	
		
		                        // get the elt title
		
								errorLabels.push(reqErrStr + " is required.");
		
		                        colorElementLabelRed(reqdElts[i]);
							
					}

                    else { 

                        colorElementLabelBlack(reqdElts[i]); 

                    } //for those colored red now corrected

					}
					break;
               case 'hidden' :
                  	var reqId = $j(reqdElts[i]).attr('id');
                    	var reqElt = $j("#" + reqId + "L");
                    if (jQuery.trim($j(reqdElts[i]).val()) == "") {
                        // add elt to error elts list
                        if (reqElt.length == 1) {
                        	errorElts.push(reqElt[0]);
                       		 // get the elt title
                        	errorLabels.push(getElementLabel(reqElt) + " is required.");
                        	colorElementLabelRed(reqElt[0]);
                        }
                    } 
                    else {
                    	if (reqElt.length == 1) {
                        	colorElementLabelBlack(reqElt[0]);
                        }
                    } //for those colored red now corrected
                    break;
            } // switch
        } // if
    } // for
    
    return {elements : errorElts, labels : errorLabels};             
}

/**
    On submit check that structured numeric fields are entered 
    correctly. A keyup checks each key but a field could be left incomplete 
    or the related units (if any) not entered.
*/
function pgCheckStructuredNumericFields(structuredNumericFieldClassName)
{
    var errorElts = new Array();
    var errorLabels = new Array();
    var snElts = $j(structuredNumericFieldClassName);
    var pattern = /^([<>=]|[<][=]|[>][=]|[<][>])?(((((\d){0,10})|((\d){1,10})([\.]{1})((\d){1,5}))?)|(((((\d){1,10})([\.]{1})((\d){1,5})[+])|(((\d){1,10})[+]))|((((\d){1,10})|(((\d){1,10})([\.]{1})((\d){1,5})))[-:\/](((\d){1,10})|(((\d){1,10})([\.]{1})((\d){1,5})))))?)?$/;

      //alert("# of elts with structuredNumericField set = " + snElts.length);

    for (var i = 0; i < snElts.length; i++)
    {
        if ($j(snElts[i]).hasClass("validated") == false)
        {
            var currEltType = "";
            var typeAttr = $j(snElts[i]).attr("type");
            if (typeAttr != undefined) {
                currEltType = typeAttr;
            }
            else {
                var nodeName = snElts[i].nodeName.toLowerCase();
                currEltType = nodeName;
            }

            // handle the html input items by type
            switch (currEltType.toLowerCase())
            {
                case 'checkbox':
                    break;
        
                case 'option':
                    break;
        
                case 'text':
                    var snVal = jQuery.trim($j(snElts[i]).val());
                    var justSetRed = false;
                    
                    // check for a sibling (i.e., the related unit)
                    if ($j(snElts[i]).siblings().get(0) != null && $j(snElts[i]).siblings().get(0) != undefined) {
                        // check if the sibling has a value selected
                        if ($j($j(snElts[i]).siblings().get(0)).val() == "" && snVal != "") {
                            // add elt to list
                            errorElts.push(snElts[i]);

                            // value for sibling is required
                            errorLabels.push(getElementLabel(snElts[i]) + " does not have a related unit selected.");
                            colorElementLabelRed(snElts[i]);
                            justSetRed = true;
                        } 
                        else if ($j($j(snElts[i]).siblings().get(0)).val() != "" && snVal == "") {
                            // add elt to list
                            errorElts.push(snElts[i]);
                        
                            // entry required if units selected
                            errorLabels.push(getElementLabel(snElts[i]) + " has a unit selected but no Structured Numeric value entered.");
                            colorElementLabelRed(snElts[i]);
                            justSetRed = true;
                            break;
                        } 
                        else { 
                            colorElementLabelBlack(snElts[i]); 
                        } //for those colored red now corrected
                    }
                           
                    if (pattern.test(snVal)) {
                        if (!justSetRed) { 
                            colorElementLabelBlack(snElts[i]); 
                        }
                        break;
                    } 
                    else {
                        // add elt to list
                        errorElts.push(snElts[i]);
                        
                        errorLabels.push(getElementLabel(snElts[i]) + " is not correctly formated for a Structured Numeric.");
                        colorElementLabelRed(snElts[i]);
                    break;
                }
            }
        }
    }
    
    // return both error labels and elements    
    return {elements : errorElts, labels : errorLabels};
}




/**
    Check that numeric fields with related units have a related unit selected 
    if a value is entered. Also check that if related unit is selected that a 
    value is entered
*/
function pgCheckRelatedUnitFields(relatedUnitsFieldClassName)
{

    var errorElts = new Array();
    var errorLabels = new Array();
    var ruElts = $j(relatedUnitsFieldClassName);
    for (var i = 0; i < ruElts.length; i++)
    {
        if ($j(ruElts[i]).hasClass("validated") == false)
        {
            var currEltType = "";
            var typeAttr = $j(ruElts[i]).attr("type");
            if (typeAttr != undefined) {
                currEltType = typeAttr;
            }
            else {
                var nodeName = ruElts[i].nodeName.toLowerCase();
                currEltType = nodeName;
            }
	
            // handle the html input items by type
            switch (currEltType.toLowerCase())
            {
                case 'checkbox':
                    break;
        
                case 'option':
                    break;
                    
               case 'span':
               	//single and multi selects
                    break;
                    
                case 'text':
                    var ruVal = jQuery.trim($j(ruElts[i]).val());
                    
                    // check for a sibling (i.e., the related unit)
                    if ($j(ruElts[i]).siblings().get(0) != null && $j(ruElts[i]).siblings().get(0) != undefined) 
                    {
                        // check if the sibling has a value selected
                        if ($j($j(ruElts[i]).siblings().get(0)).val() == "" && ruVal != "") 
                        {	
                            // add elt to list
                            errorElts.push(ruElts[i]);
                            
                            // value for sibling is required
                            var labelTitle = getElementLabel(ruElts[i]);
                            var theLabel = labelTitle.substr(0,labelTitle.length-1); //remove colon
                            var reqErrStr = theLabel;
                            if (".relatedUnitsField" === relatedUnitsFieldClassName) //not batch subsection
                            	reqErrStr = buildErrorAnchorLink(ruElts[i], theLabel);                            
                            errorLabels.push(reqErrStr +  " has a value but no related unit selected.");
                            colorElementLabelRed(ruElts[i]);
                            
                            
                        } 
                        else if ($j($j(ruElts[i]).siblings().get(0)).val() != "" && ruVal == "") 
                        {
                            // add elt to list
                            errorElts.push(ruElts[i]);
                            // entry required if units selected
                            var labelTitle = getElementLabel(ruElts[i]);
                            var theLabel = labelTitle.substr(0,labelTitle.length-1); //remove colon
                            var reqErrStr = theLabel;
                            if (".relatedUnitsField" === relatedUnitsFieldClassName) //not batch subsection
                            	reqErrStr = buildErrorAnchorLink(ruElts[i], theLabel); 
                            errorLabels.push(reqErrStr +  " has a unit selected but no value entered.");
                            colorElementLabelRed(ruElts[i]);
                            break;
                        } 
                        else {
                            colorElementLabelBlack(ruElts[i]); 
                        } //for those colored red now corrected
                    } // if
                    break;
            } // switch
        }
    }
    
    // return both error labels and elements
    return {elements : errorElts, labels : errorLabels};
}

/**
 * isTypeOrganization: returns true if the elt is of type organization, like Reporting Facility
 * @param elt
 * @returns {String}
 */
function isTypeOrganization(elt){
	
	var typeOrg = "false";
	
	if($j(elt).parent()[0].id.indexOf("SearchControls")!=-1)
		typeOrg="true";
	
	return typeOrg;	
}
//find the element label
function getElementLabel(elt)
{
  var label = "";
  label = $j($j($j(elt).parent().parent()).find("span[title]").get(0)).html();
  if (label==undefined && isTypeOrganization(elt)=="true")//Type of element: organization, for example, reporting facility. Defect ND-13614
	  label = $j($j($j(elt).parent().parent().parent()).find("span[title]").get(0)).html();
  return label;
}
// color element label red for error
function colorElementLabelRed(elt)
{
	$j($j(elt).parent().parent()).find("span[title]").css("color", "990000");
	
	if(isTypeOrganization(elt)=="true")//Type organization, like reporting facility
		$j($j(elt).parent().parent().parent()).find("span[title]").css("color", "990000");
	  
}
// color element label black for ok
function colorElementLabelBlack(elt)
{
	$j($j(elt).parent().parent()).find("span[title]").css("color", "black");
	if(isTypeOrganization(elt)=="true")//Type organization, like reporting facility
		$j($j(elt).parent().parent().parent()).find("span[title]").css("color", "black");
}

/**
 * Get a list of all tab handle elements.
 */
function getTabHandles(tabGroup) 
{
    var allTabHandles = new Array();
    if (tabGroup == _top) {
        //alert("tabgroup = " + tabGroup);
        if ($j(disabledTabHandleClass).length > 0) {
            var tmp = $j(disabledTabHandleClass);
            for (var i = 0; i < tmp.length; i++) {
                allTabHandles.push(tmp[i]);
            }
        }
        if ($j(errorTabHandleClass).length > 0) {
            var tmp = $j(errorTabHandleClass);
            for (var i = 0; i < tmp.length; i++) {
                allTabHandles.push(tmp[i]);
            }
        }
        if ($j(enabledTabHandleClass).length > 0) {
            var tmp = $j(enabledTabHandleClass);
            for (var i = 0; i < tmp.length; i++) {
                allTabHandles.push(tmp[i]);
            }
        }
        
        //alert("# of top tab handles = " + allTabHandles.length);
    }
    else if (tabGroup == _bottom) {
        //alert("tabgroup = " + tabGroup);
        // get a list of all tab handles
	    if ($j(disabledBottomTabHandleClass).length > 0) {
            var tmp = $j(disabledBottomTabHandleClass);
            for (var i = 0; i < tmp.length; i++) {
                allTabHandles.push(tmp[i]);
            }
        }
        if ($j(errorBottomTabHandleClass).length > 0) {
            var tmp = $j(errorBottomTabHandleClass);
            for (var i = 0; i < tmp.length; i++) {
                allTabHandles.push(tmp[i]);
            }
        }
        if ($j(enabledBottomTabHandleClass).length > 0) {
            var tmp = $j(enabledBottomTabHandleClass);
            for (var i = 0; i < tmp.length; i++) {
                allTabHandles.push(tmp[i]);
            }
        }
        
        //alert("# of bottom tab handles = " + allTabHandles.length);
    }
    
    return allTabHandles;    
}

/**
 * Find out the parent tab element for a given element. 
 */
function getParentTab(elt)
{
  // get the parent tab by its class name
  var parentTab = $j(elt).parents(activeTabClass).get(0);
  if (parentTab == null || parentTab == undefined) {
    parentTab = $j(elt).parents(hiddenTabClass).get(0);
  }

  if (parentTab == null || parentTab == undefined) {
    return null;
  } else {
    return parentTab;
  }
}

/**
 * Given a set of elements, retrieve a distinct list of parent tabs to which
 * these elements belong.
 */
function getDistinctParentTabs(childElts)
{
  var tabElts = new Array();
  for (var i = 0; i < childElts.length; i++) {
    var elt = getParentTab(childElts[i]);
    var notAlreadyThere = true;
    if (elt != null) {
      // check if already in list
      for (j = 0; j < tabElts.length; ++j) {
        if (tabElts[j] == elt)
          notAlreadyThere = false;
      }

      if (notAlreadyThere) {
        tabElts.push(elt);
      }
    }
  }
  return tabElts;
}
/**
 * Create an anchor that goes to the to correct tab and sets focus on the field.
 * For 4.5.b.
 */
function buildErrorAnchorLink(errorElement, errorLabel)
{

	if (errorElement == null)
		return errorLabel;

	var theTabId = "tabs0tab0"; //default to first tab;
	var theTabElt = getParentTab(errorElement);
	var theErrEleId = errorElement.id;
	var codeClearButtonId = theErrEleId.substring(0, theErrEleId.length-1) + "CodeClearButton";
	var clearEle = getElementByIdOrByName(codeClearButtonId);
	if (clearEle != null){
		theErrEleId = theErrEleId.substring(0, theErrEleId.length-1) + "Text";
		//Fix defect when the text is associated to Search button and it doesn't exist until it's selected
		if(getElementByIdOrByName(theErrEleId)==null || getElementByIdOrByName(theErrEleId)==undefined)
			theErrEleId=errorElement.id;	
	}
	else if (theErrEleId.charAt(theErrEleId.length-1) == 'L'){ //strip last char if L
		if($(theErrEleId).parentNode.next().firstElementChild.type=="checkbox")
			theErrEleId=$(theErrEleId).parentNode.next().firstElementChild.name;
		else
			if($(theErrEleId).parentNode.next().firstElementChild.type=="textarea")
				theErrEleId=$(theErrEleId).parentNode.next().firstElementChild.name;
		else
			if($(theErrEleId).parentNode.next().firstElementChild.onkeyup!=null && $(theErrEleId).parentNode.next().firstElementChild.onkeyup.toString().indexOf("DateMask")!=-1)
				theErrEleId=theErrEleId.substring(0, theErrEleId.length-1);
			else
				theErrEleId = theErrEleId.substring(0, theErrEleId.length-1) + "_textbox";
	}
	if (theTabElt != null) {
		theTabId = $j(theTabElt).attr("id");
	}
	var theTabChar = theTabId.charAt(8);
	var errHref = "<a href=\"javascript: selectTab(0,6," + 	theTabChar + ",'ongletTextEna','ongletTextDis','ongletTextErr',null,null); getElementByIdOrByName('" + theErrEleId +"').focus()\">" + errorLabel + "</a>";
	

	//alert("ErrorLabel " + errorLabel + " errHref= " + errHref);
	return errHref;
}

/**
 * Set the background color of error tabs to red.
 */
function setErrorTabProperties(tabElements)
{
    var topTabHandles = getTabHandles(_top);
    var bottomTabHandles = getTabHandles(_bottom);
    var firstTopErrorTabId;
    // prepare a look up array for later use
    var errorTabsLookupArray = new Array();
    // alert("tabElements.length=" + tabElements.length + " topTabHandles.length=" + topTabHandles.length + " bottomTabHandles.length=" + bottomTabHandles.length);
    for (var i = 0; i < tabElements.length; i++) {
        var tabBodyId = $j(tabElements[i]).attr("id");
        var tabHandleId = tabHandleIdPrefix + tabBodyId.replace(tabBodyIdPrefix, "");
        //alert("asso array : b4 : " + tabHandleId + "errorTabsLookupArray[tabHandleId] = " + errorTabsLookupArray[tabHandleId]);
        errorTabsLookupArray[tabHandleId] = "Filled";
        //alert("asso array : after : " + tabHandleId + "errorTabsLookupArray[tabHandleId] = " + errorTabsLookupArray[tabHandleId]);
    } 
    

    // assign error class to top tab handles
    for (var i = 0; i < topTabHandles.length; i++) {
        var tabHandleId = $j(topTabHandles[i]).attr("id");
        
        //alert("errorTabsLookupArray[tabHandleId] for tabHandleId:" + tabHandleId + " = " + errorTabsLookupArray[tabHandleId]);
        
        if (errorTabsLookupArray[tabHandleId] != null && 
                errorTabsLookupArray[tabHandleId] != undefined &&
                errorTabsLookupArray[tabHandleId] != "") {
	        if (firstTopErrorTabId == null) {
	            //alert("First error tab handle id, body id = " + tabHandleId + ", " + tabBodyId);
	            firstTopErrorTabId = tabHandleId;
	            $j(topTabHandles[i]).addClass('ongletTextErr');
	            $j(topTabHandles[i]).removeClass('ongletTextDis');
	            $j(topTabHandles[i]).removeClass('ongletTextEna');  
	        }
	        else {
	            $j(topTabHandles[i]).addClass('ongletTextErr');
	            $j(topTabHandles[i]).removeClass('ongletTextDis');
	            $j(topTabHandles[i]).removeClass('ongletTextEna');
	        }
        }
        else {
            $j(topTabHandles[i]).removeAttr('classNameErrorStdLayout');
            $j(topTabHandles[i]).removeClass('ongletTextErr');
            $j(topTabHandles[i]).removeClass('ongletTextEna');
            $j(topTabHandles[i]).addClass('ongletTextDis');
            
        }
    }
    
    // assign error class to bottom tab handles
    for (var i = 0; i < bottomTabHandles.length; i++) {
        var tabHandleId = $j(bottomTabHandles[i]).attr("id");
        
        if (errorTabsLookupArray[tabHandleId] != null && 
                errorTabsLookupArray[tabHandleId] != undefined &&
                errorTabsLookupArray[tabHandleId] != "") {
                    bottomTabHandles[i].classNameErrorStdLayout = new Object();
	            $j(bottomTabHandles[i]).addClass('ongletTextErr1');
	            $j(bottomTabHandles[i]).removeClass('ongletTextDis1');
	            $j(bottomTabHandles[i]).removeClass('ongletTextEna1');
        }
        else {
	        $j(bottomTabHandles[i]).removeAttr('classNameErrorStdLayout');
	        $j(bottomTabHandles[i]).removeClass('ongletTextErr1');
	        $j(bottomTabHandles[i]).removeClass('ongletTextEna1');
            	$j(bottomTabHandles[i]).addClass('ongletTextDis1');
        }
    }
    //alert("jumping to first error tab.." + firstTopErrorTabId);
    // go to the first errror tab...
    if (firstTopErrorTabId != null) {
    	getElementByIdOrByName(firstTopErrorTabId).classNameErrorStdLayout = new Object();
    	//jump to first error tab
    	$j("#" + firstTopErrorTabId).click();    
    }

    
}

/**
 * Check for batch repeating subsections where the user forgot to press the Add
 * key.
 * 
 */
function pgCheckRepeatingSubSectionDanglingField()
{
  var errorElts = new Array();
  var errorLabels = new Array();
  // find all batch subsections
  var bssElts = $j('.batchSubSection');

  for (var i = 0; i < bssElts.length; i++) {
    var errCount = 0;
    var ssName = $j(bssElts[i]).children(":first").text();
    var ssId = bssElts[i].id;
    // alert ("Checking subsection " + ssName);
    var addNewButtonDisplay='none';
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+ssId);
	if(rowshow!=null){
		addNewButtonDisplay = $j(rowshow).css('display');
	}
	//alert(addNewButtonDisplay);
	if(addNewButtonDisplay=='none'){
    var subSectionBody = $j(bssElts[i]).find("tbody");
    // alert("Number of tbody = " + subSectionBody.length);
    for (var j = 0; j < subSectionBody.length; j++) {
      var subSectionTBody = $j(subSectionBody.get(j));

      var subSectionsInput = subSectionTBody.find("INPUT");
      for (var k = 0; k < subSectionsInput.length; ++k) {
        var typeAttr = $j(subSectionsInput[k]).attr("type");
        var idAttr = $j(subSectionsInput.get(k)).attr("id");
        var disabledText = $j("#" + idAttr).attr("disabled");
        var disabledTextLabel = $j("#" + idAttr + "L").attr("disabled");
		//alert(typeAttr + " "+idAttr+ " "+disabledText+" "+disabledTextLabel+" "+jQuery.trim($j(subSectionsInput.get(k)).val()));
		if(disabledTextLabel == null || disabledTextLabel==undefined){//participation
			if(idAttr.indexOf("CodeClearButton")!=-1){
				disabledTextLabel=!$j("#" + idAttr).parent().is(":visible");
			}
		}
		// idAttr.indexOf("DescriptionId") was added for code lookup scenarios
        if ((idAttr != null && idAttr.length > 0 && idAttr.indexOf("DescriptionId")!=-1 && typeAttr == 'hidden') || (( idAttr != null && idAttr.length > 0 && (disabledTextLabel==undefined || disabledTextLabel == false) && disabledText == false
            && (typeAttr == 'text' || typeAttr == 'textarea')))) {
        	
          if (jQuery.trim($j(subSectionsInput.get(k)).val()).length > 0) {
        	  //alert(typeAttr + " "+idAttr+ " "+disabledText+" "+disabledTextLabel+" "+jQuery.trim($j(subSectionsInput.get(k)).val()));
            // alert("Error disabled text= " +disabledTextLabel + " id="
           //  +idAttr);
           //  alert(idAttr + " orphaned text value in subsection" +
         //    $j(subSectionsInput.get(k)).val());
            ++errCount;
          }
        }
      }
      // this is problematic due to default values, view mode, other, etc.
      var subSectionsSelect = subSectionTBody.find("SELECT");
      for (var k = 0; k < subSectionsSelect.length; ++k) {
        var selId = $j(subSectionsSelect.get(k)).attr("id");
        var selTextId = selId + '_textbox';
        selNode = getElementByIdOrByName(selTextId);
        selDisabled = $j("#" + selId).attr("disabled");
        selDisabledLabel = $j("#" + selId + "L").attr("disabled");
        var hiddenLabel = false;
        var selHiddenLabel;
        
        
        if ($j("#" + selId + "L")!=null && $j("#" + selId + "L")!=undefined &&
        		 $j("#" + selId + "L").parents("tr")!=null &&  $j("#" + selId + "L").parents("tr")!=undefined &&
        		 $j("#" + selId + "L").parents("tr")[0]!=null && $j("#" + selId + "L").parents("tr")[0] !=undefined){
        	
        	selHiddenLabel = $j("#" + selId + "L").parents("tr")[0].getAttribute("style");
        	if(selHiddenLabel!=null && selHiddenLabel!=undefined && selHiddenLabel.indexOf("display: none")!=-1)
        		hiddenLabel=true; 	
        }
        
        
        
        if (selNode != null && selNode.value != "" && (selDisabledLabel == undefined || selDisabledLabel == false) && hiddenLabel==false) {
          // alert(" orphaned select in subsection " + selNode.value);
          $j("#" + selId + "L").css("color", "990000");
          ++errCount;
        } else if (selNode == null) {
          selNode = getElementByIdOrByName(selId);
          if (selNode != null && (selDisabledLabel == undefined || selDisabledLabel == false)) {
            if (selNode.selectedIndex > 0) {
              $j("#" + selId + "L").css("color", "990000");
              // alert("Multiselect has selected item " + selId);
              ++errCount;
            }
          }
        }
      }

    }
    if (errCount != 0) {
      errorElts.push(bssElts[i]);
      errorLabels.push("You have entered or edited information and have not clicked on Add or Update in the <b>"
          + ssName + "</b> repeating block table. Please take the appropriate action and try again.");
    }
	}
  }

  // return both error labels and elements
  return { elements : errorElts, labels : errorLabels };
}   


/**
 * Given a set of elements, retrieve a distinct list of parent tabs to which
 * these elements belong.
 */
function pgProcessErrorFieldsToHilight(errFieldStr)
{ 
  if (errFieldStr == "null") {
    return;
  }

  var errorElts = new Array();
  var errorLabels = new Array();

  var errorFields = errFieldStr.split(" "); // split on the space
  for (i = 0; i < errorFields.length; ++i) {

    var errFld = "";
    errFld = errorFields[i];

    if (errFld == "")
      break;
    // alert ("errFld = " + errFld);
    var errFldLabel = errFld + "L";
    var lblNode = getElementByIdOrByName(errFldLabel);
    var fldNode = getElementByIdOrByName(errFld);
    // color labels red and build error list to display in
    // displayGlobalErrorMessage()
    if (lblNode != null && fldNode != null) {
      $j(lblNode).css("color", "990000");
      errorElts = errorElts.concat(fldNode);
      var label = $j($j($j(lblNode).parent()).find("span[title]").get(0)).html();
      if (label != "undefined")
        errorLabels.push("Enter data for " + buildErrorAnchorLink(lblNode, label));
    } else if (errFld == "DEM152") {
      $j(lblNode).css("color", "990000");
      var label = $j($j($j(lblNode).parent()).find("span[title]").get(0)).html();
      if (label != "undefined")
        errorLabels.push("Enter data for " + buildErrorAnchorLink(lblNode, label));
    }
  }

  if (errorElts.length > 0) {
    displayGlobalErrorMessage(errorLabels);
    var tabElts = new Array();
    tabElts = getDistinctParentTabs(errorElts);
    setErrorTabProperties(tabElts);
  }
}

function getRepeatingBlockUtilDispText(questionId, uiComponentId)
{
	var textToDisplay;
	if(getElementByIdOrByName(questionId) != null){
		textToDisplay =getElementByIdOrByName(questionId).value;
	}
	//multi-select
	if(uiComponentId == "1013" && getElementByIdOrByName(questionId) != null ){ 
		textToDisplay = "";
		var myList = getElementByIdOrByName(questionId);
		var myListCount = myList.options.length; // number of items						
		for (var i=0; i < myListCount; i++) {
			if (myList.options[i].selected == true){
				var optionsVal = myList.options[i].value;
				if((optionsVal  == "Other" || optionsVal  == "OTH") && getElementByIdOrByName(questionId+"Oth")!=null){
					optionsVal   = "OTH"+ "$MulOth$"+ getElementByIdOrByName(questionId+"Oth").value +"#MulOth#"; 
				}
				textToDisplay += optionsVal  + ";";
			}
		}
	}
	
	if(uiComponentId == "1007" && getElementByIdOrByName(questionId) != null && textToDisplay != null && textToDisplay  == 'OTH'){
		if(getElementByIdOrByName(questionId+"Oth") != null) {
			textToDisplay  =  textToDisplay +":"+ getElementByIdOrByName(questionId+"Oth").value; 
		}	
	}
	
	if(uiComponentId == "1031" && getElementByIdOrByName(questionId+"Description") != null ){

		var elem = getElementByIdOrByName(questionId);

		if(elem!=null && elem!=undefined && elem.selectedIndex!=-1){
			var selectedIndex = elem.selectedIndex;

			textToDisplay=elem.options[selectedIndex ].text;

		}
		
		if(getElementByIdOrByName(questionId+"Description") != null && (textToDisplay == null || textToDisplay == "")){
			textToDisplay  =  getElementByIdOrByName(questionId+"Description").textContent; 
		}
	
	}
	
	if(getElementByIdOrByName(questionId+"UNIT") != null && getElementByIdOrByName(questionId+"UNIT").value != null && getElementByIdOrByName(questionId+"UNIT").value != '') {
		var sel = questionId+"UNIT";	
		var obj = getElementByIdOrByName(sel);
		var w = obj.selectedIndex;
		var selected_text = obj.options[w].text;
		textToDisplay  =  textToDisplay  +"$sn$"+ getElementByIdOrByName(questionId+"UNIT").value+"$val$"+selected_text; 
	} 
	return textToDisplay;
}

function repeatingBlockCheckForEmptyRow(questionId, emptyRowFlag)
{
	if(questionId.endsWith("Date") || questionId.endsWith("User"))
			return emptyRowFlag;
  if (getElementByIdOrByName(questionId) != null) {
    var textval = getElementByIdOrByName(questionId).value;
    if (!(textval == null || textval.trim() == '')) {
      emptyRowFlag = "no";
    }
  }

  if (getElementByIdOrByName(questionId + "Oth") != null && getElementByIdOrByName(questionId + "Oth").value != null
      && getElementByIdOrByName(questionId + "Oth").value != '') {
    emptyRowFlag = "no";
  }
  return emptyRowFlag;
}

function repeatingBlockHandleMultiVal(mulVal, questionId)
{
  var handlemulVal;
  var myList = getElementByIdOrByName(questionId);
  
  var myListCount = myList.options.length;
  
  if(mulVal!=null && mulVal.length>0 && myListCount>0)
	  	myList.options[0].selected = false;
  
  while (mulVal.indexOf(",") != -1) {
    selectedmulVal = mulVal.substring(0, mulVal.indexOf(","));
    if (selectedmulVal.indexOf("$MulOth$") != -1) {
      selectedmulVal = mulVal.substring(0, mulVal.indexOf("$MulOth$"));
      othVal = mulVal.substring(mulVal.indexOf("$MulOth$") + 8, mulVal.indexOf("#MulOth#"));
      if (othVal != null && othVal != '') {
        getElementByIdOrByName(questionId + "Oth").value = othVal;
      }
    } else {
      selectedmulVal = mulVal.substring(0, mulVal.indexOf(","));
    }
    mulVal = mulVal.substring(mulVal.indexOf(",") + 1);
    if (selectedmulVal != null && selectedmulVal != '') {
      for (i = 0; i < myListCount; i++) {
        if (myList.options[i].value == selectedmulVal) {
          if (myList.options[i].value != '' && myList.options[i].value != "") {
            myList.options[i].selected = true;
            handlemulVal = handlemulVal + selectedmulVal;
          }
        }
      }
    }

  }
  // alert("mulVal .....!!!!."+mulVal );
  if (mulVal.indexOf("$MulOth$") != -1) {
    othVal = mulVal.substring(mulVal.indexOf("$MulOth$") + 8, mulVal.indexOf("#MulOth#"));
    mulVal = mulVal.substring(0, mulVal.indexOf("$MulOth$"));
    // alert(othVal);
    if (othVal != null && othVal != '') {
      getElementByIdOrByName(questionId + "Oth").value = othVal;
    }
  }
  if (mulVal != null && mulVal != '') {
    for (i = 0; i < myListCount; i++) {
      if (myList.options[i].value == mulVal) {
        if (myList.options[i].value != '' && myList.options[i].value != "") {
          myList.options[i].selected = true;
          handlemulVal = handlemulVal + mulVal;
        }
      }
    }
  }
}
    
//get values one at a time for subsection to display in repeating block table

function repeatingBlockFillValue(val)
{
  if (val != null && val.indexOf("||") != -1) {
    var mulVal;
    val = val.substring(0, val.length - 2);
    if (val.indexOf("||") != -1) {
      mulVal = val.substring(0, val.indexOf("||"));
      val = val.substring(val.indexOf("||") + 2);
      if (mulVal.indexOf("$MulOth$") != -1) {
        mulVal = mulVal.substring(mulVal.indexOf("$$") + 2, mulVal.indexOf("$MulOth$"));
        othVal = mulVal.substring(mulVal.indexOf("$MulOth$") + 8, mulVal.indexOf("#MulOth#"));
      } else {
        mulVal = mulVal.substring(mulVal.indexOf("$$") + 2, mulVal.length);
      }
    }
    while (val.indexOf("||") != -1) {
      var val1 = val.substring(0, val.indexOf("||"));
      if (val1.indexOf("$MulOth$") != -1) {
        mulVal = mulVal + "," + val1.substring(val1.indexOf("$$") + 2, val1.indexOf("$MulOth$"));
        othVal = val1.substring(val1.indexOf("$MulOth$") + 8, val1.indexOf("#MulOth#"))
      } else {
        mulVal = mulVal + "," + val1.substring(val1.indexOf("$$") + 2, val1.length);
      }
      val = val.substring(val.indexOf("||") + 2);
    }
    if (mulVal != '' && mulVal != 'undefined' && mulVal != null) {
      // mulVal = mulVal +", "+ val.substring(val.indexOf("$$")+2, val.length);
      if (val.indexOf("$MulOth$") != -1) {
        mulVal = mulVal + "," + val.substring(val.indexOf("$$") + 2, val.indexOf("$MulOth$"));
        othVal = val.substring(val.indexOf("$MulOth$") + 8, val.indexOf("#MulOth#"));
      } else {
        mulVal = mulVal + "," + val.substring(val.indexOf("$$") + 2, val.length);
      }
    } else {
      if (val.indexOf("$MulOth$") != -1) {
        mulVal = val.substring(val.indexOf("$$") + 2, val.indexOf("$MulOth$"));
        othVal = val.substring(val.indexOf("$MulOth$") + 8, val.indexOf("#MulOth#"));
      } else {
        mulVal = val.substring(val.indexOf("$$") + 2, val.length);
      }
    }
    val = mulVal;
    mulVal = null;

  } else if (val != null && val.indexOf("$$") != -1) {
    if (val.indexOf(":") != -1 && val.indexOf("OTH")!=-1) {
      val = val.substring(val.indexOf("$$") + 2, val.indexOf(":"));
    } else {
      val = val.substring(val.indexOf("$$") + 2, val.length);
    }
  } else if (val != null && val.indexOf("$sn$") != -1) {
    val = val.substring(0, val.indexOf("$sn$")) + ' ' + val.substring(val.indexOf("$val$") + 5, val.length);
  } else if (val == null){
    val = "";
  }
  if (val.length > 200) {
    val = val.substring(0, 200) + "...";
  }
  // alert("val=" + val);
  return val;
}

function repeatingBatchClearFields(questionId, uiComponentType)
{
  dwr.util.setValue(questionId, "");
  var keyL = questionId + "L";
  getElementByIdOrByName(questionId).disabled = false;
  getElementByIdOrByName(keyL).disabled = false;
  if (questionId + "-selectedValues" != null && getElementByIdOrByName(questionId + "-selectedValues") != null) {
    getElementByIdOrByName(questionId + "-selectedValues").disabled = false;
  }
  $j("#" + questionId).parent().parent().find("img").attr("disabled", false);
	var calendars = $j( "img[src*='calendar.gif']");
	if( $j("#" + questionId).parent().parent().find(calendars)[0]!=undefined &&  $j("#" + questionId).parent().parent().find(calendars)[0]!=null)
		$j("#" + questionId).parent().parent().find(calendars)[0].attr("tabIndex", "0");
  //Defect ND-12336
  $j("#" + questionId).parent().parent().find("input").attr("disabled", false);
  if (questionId + "Oth" != null && getElementByIdOrByName(questionId + "Oth") != null) {
    var keyOth = questionId + "Oth";
    dwr.util.setValue(questionId + "Oth", "");
    getElementByIdOrByName(keyOth).disabled = true;
    getElementByIdOrByName(questionId + "OthL").disabled = true;
  }
  if (questionId + "UNIT" != null && getElementByIdOrByName(questionId + "UNIT") != null) {
    var keyUnit = questionId + "UNIT";
    dwr.util.setValue(questionId + "UNIT", "");
    getElementByIdOrByName(keyUnit).disabled = false;
  }

  if (uiComponentType == "1007" || uiComponentType == "1013") {
    autocompTxtValuesForJSPByElement(questionId);
  }
  if (getElementByIdOrByName(questionId + "UNIT") != null) {
    autocompTxtValuesForJSPByElement(questionId + "UNIT");
  }
  if (uiComponentType == "1017") {
    repeatingBlockClearParticipant(questionId);
  }
  
  
  if (uiComponentType == "1031") {
	  repeatingBlockClearCodedWithSearch(questionId);
	}
  
}

function repeatingBlockClearParticipant(questionId)
{
  $j("#" + questionId + "Disp").text("");
  $j("#" + questionId + "Disp").removeAttr('disabled');
  $j("#" + questionId + "S").removeAttr('disabled');
  $j("#" + questionId + "Text").show();
  $j("#" + questionId + "L").show();
  $j("#" + questionId + "CodeLookupButton").show();
  $j("#clear" + questionId).hide();
  $j("#" + questionId + "L").show();
  $j("#" + questionId + "SearchControls").show();
}

/**
 * repeatingBlockClearCodedWithSearch: clear all the elements related to the new UI component Coded with Search in the repeating block
 * @param questionId
 */
function repeatingBlockClearCodedWithSearch(questionId)
{
	 //alert("Here :"+questionId);
	document.getElementsByName(questionId+"_textbox")[0].value="";
	$j("#" + questionId + "_CodeId").text("");
	$j("#" + questionId + "_DescriptionId").text("");
	$j("#" + questionId + "CodeId").val("");
	$j("#" + questionId + "DescriptionId").val("");
	$j("#" + questionId + "_Description").text("");
	document.getElementById(questionId+"Description").textContent="";//Span filled from Search button
	document.getElementsByName(questionId+"_textbox")[0].parentElement.style.display="";//show the dropdown
	showSearchClearButtonCodedWithSearch(questionId);

}

function showSearchClearButtonCodedWithSearch(questionId){
	$j("#" + questionId + "Search").show();
	$j("#" + questionId + "ClearButton").show();
}

function repeatingBlockReadyParticipantEdit(questionId) {
 	$j("#clear"+questionId).show();
 	$j("#" + questionId+"SearchControls").hide();
} 	
 	
function repeatingBlockHandleViewParticipant(partVal, questionId, partDisp) {
	getElementByIdOrByName(questionId).value = partVal;
	if(partDisp){
    $j("#"+questionId+"Disp").html(partDisp); 
  } else {
    $j("#"+questionId+"Disp").html(partVal);
  }
	$j("#"+questionId+"Span").text(partVal);
	$j("#"+questionId+"Span").attr('disabled','disabled');
	$j("#"+questionId+"S").attr('disabled','disabled');
 	$j("#"+questionId+"L").hide(); 
	$j("#"+questionId+"SearchControls").hide();
	var reqNode = $j("#"+questionId+"L");
	var reqText = $j(reqNode).parent().children('span:first').text();
	if ((reqText == "* ") || (reqText == "*")) {
		$j(reqNode).parent().children('span:first').remove();
  }
}



function repeatingBlockHandleViewParticipant2(partVal, questionId, partDisp) {
	getElementByIdOrByName(questionId).value = partVal;
	if(partDisp){
    $j("#"+questionId+"Disp_2").html(partDisp); 
  } else {
    $j("#"+questionId+"Disp_2").html(partVal);
  }
	$j("#"+questionId+"Span_2").text(partVal);
	$j("#"+questionId+"Span_2").attr('disabled','disabled');
	$j("#"+questionId+"_2S").attr('disabled','disabled');
 	$j("#"+questionId+"_2L").hide(); 
	$j("#"+questionId+"_2SearchControls").hide();
	var reqNode = $j("#"+questionId+"_2L");
	var reqText = $j(reqNode).parent().children('span:first').text();
	if ((reqText == "* ") || (reqText == "*")) {
		$j(reqNode).parent().children('span:first').remove();
  }
}

/**
 * repeatingBlockHandleViewCodedWithSearch: view new UI component element (coded with search) from a repeating block
 * @param val
 * @param questionId
 * @param partDisp
 */
function repeatingBlockHandleViewCodedWithSearch(val, questionId, description, descriptionId, codeId) {

	//$j("#" + questionId + "_CodeId").text(codeId);
	//$j("#" + questionId + "_DescriptionId").text(descriptionId);
	//$j("#" + questionId + "_Description").text(description);
	var realValue = description.replace("("+codeId+")","");
	
	$j("#" + questionId + "Search").hide();
	$j("#" + questionId + "ClearButton").hide();
	document.getElementById(questionId).value=val;
	autocompTxtValuesForJSPByElement(questionId);
	//Hide the single select
	document.getElementsByName(questionId+"_textbox")[0].parentElement.style.display="none";
	var val2 = document.getElementsByName(questionId+"_textbox")[0].value;

	if(val2 =="" || val2==null)
		val2=realValue;//val doesn't exist in the dropdown, because it is coming from the Search button
	
	$j("#" + questionId + "Description").text(val2)
}





function repeatingBlockHandleEditParticipant(partVal, questionId, partDisp) 
{
	getElementByIdOrByName(questionId).value = partVal;
	if(partDisp){
	  $j("#"+questionId+"Disp").html(partDisp); 
	} else {
	  $j("#"+questionId+"Disp").html(partVal);
	}
	repeatingBlockReadyParticipantEdit(questionId);
	var reqNode = $j("#"+questionId+"L");
	var reqText = $j(reqNode).parent().children('span:first').text();
	if ((reqText == "* ") || (reqText == "*")) {
		$j(reqNode).parent().children('span:first').remove();
    }
} 

	function repeatingBlockHandleEditCodedWithSearch(val, questionId, description, descriptionId, codeId, id){
		//alert(val+" "+questionId+" "+description+" "+descriptionId+" "+codeId);
		if(questionId=="NBS_LAB280"){
			handleOrganismOrCodedResult(val, questionId, description, descriptionId, codeId, id);
		}
	
		else
			repeatingBlockHandleEditCodedWithSearchNoCodedResult(val, questionId, description, descriptionId, codeId);
	
	}
	
	

	var val1, questionId1, description1, descriptionId1,codeId1, id1, isNew=false;

	var data = "0";
	//var myMap = new Map();

	var myArray = new Array();

	function isKeyInArray(key){
		var valueToReturn = "0";

		for(var i =0; i<myArray.length; i++){
			var value = myArray[i];
			var valueOrganism = key+"-1";
			var valueCoded = key+"-2";
			
			if (value==valueOrganism)
				valueToReturn="1";
			
			if (value==valueCoded)
				valueToReturn="2";
		}
		
		return valueToReturn;
	}

/**
 * removeKeyFromArray: remove the key from the array, for example, if the key used to be an organism, we need to remove key+1, in order to add key+2
 * @param key
 * @returns {String}
 */
	function removeKeyFromArray(key, parentDoc){//parentDoc has been added in case we are removing the key from the child window
	
		var windowParent = window;
		if(parentDoc!=null && parentDoc != undefined && parentDoc != document)
			windowParent=getDialogArgument();


		for(var i =0; i<windowParent.myArray.length; i++){
			var value = windowParent.myArray[i];
			if (value==key)
				windowParent.myArray[i]="DELETED"
		}
	}

	
	
	
		function handleOrganismOrCodedResult(val, questionId, description, descriptionId, codeId, id){
	
			  var codedResult = false;
	
			  var testNameSearchButton = "";
	
			  var parentDoc = document;
	
			  
			  var resultedTestSection = getResultedTestSectionName(parentDoc);
			  
			  
			  var programAreaCode = getProgramArea(parentDoc);
			  var labUid=-1;
	
	
	
			 /* if(getElementByIdOrByNameNode("attributeMap.NBS_LAB365Uid",parentDoc)!=null &&
	
				getElementByIdOrByNameNode("attributeMap.NBS_LAB365Uid",parentDoc)!=undefined)
	
				 
	
				  labUid = getElementByIdOrByNameNode("attributeMap.NBS_LAB365Uid",parentDoc).value;//Reporting Facility Code
	*/
				labUid = getReportingFacilityUid(parentDoc);
	
			  var testName="";
	
	
	
			  if(testNameSearchButton == null || testNameSearchButton == "" || testNameSearchButton == undefined){//it is coming from dropdown
	
				  testName =getElementByIdOrByNameNode("NBS_LAB220",parentDoc).value;
	
				 
	
			  }else
	
				  testName=testNameSearchButton;//From Search button
	
			  
	
			  if(parentDoc!=document)
	
				  JPageForm = JBaseForm;
	
			  
	
			  if(labUid!=null){
	
				  if(labUid!=-1 && labUid.indexOf("|")!=-1)
	
					  labUid = labUid.substring(0,labUid.indexOf("|"));
	
				  var keyOrganismCoded = getKey(resultedTestSection);
			
				  data = isKeyInArray(keyOrganismCoded);//1 if organism, 2 if coded, otherwise non in array
				//  data = myMap.get(key);
				  //If the combination is already in the map
				  
				  if(data=="1"){
					  
					  showOrganism();
					  repeatingBlockHandleEditCodedWithSearchNoCodedResult(val, questionId, description, descriptionId, codeId);
	
				  }
				  else
					  if(data=="2"){
					  
						hideOrganism();
						repeatingBlockHandleEditCodedWithSearchCodedResult(val, questionId, description, descriptionId, codeId);
	
				  }//if the combination is not in the map yet
				 else
			
				  JPageForm.showHideOrganismByID(programAreaCode,labUid,testName, id, function(data) {
				
					 var length = myArray.length;
					  myArray[length]=getKey(resultedTestSection)+"-"+data;
					//  myMap.set(key,data);
					 if(data=="1"){//organism
						 showOrganism();
						 repeatingBlockHandleEditCodedWithSearchNoCodedResult(val, questionId, description, descriptionId, codeId);
	
					 }
	
					 else
					 if(data=="2"){//coded
	
						 hideOrganism();
						 repeatingBlockHandleEditCodedWithSearchCodedResult(val, questionId, description, descriptionId, codeId);
				
					 }
	
					 
				 });
	
			  }	
	
			
	
			  
	
			  
	
			  
	
			}
	
	
			  

		


function repeatingBlockHandleEditCodedWithSearchNoCodedResult(val, questionId, description, descriptionId, codeId) {

	$j("#" + questionId + "CodeId").text(codeId);
	$j("#" + questionId + "DescriptionId").text(descriptionId);
	$j("#" + questionId + "Description").text(description);
	$j("#" + questionId + "Search").hide();
	$j("#" + questionId + "ClearButton").show();
	
	if(document.getElementById(questionId)!=null && document.getElementById(questionId) !=undefined){
		document.getElementById(questionId).value=val;
		autocompTxtValuesForJSPByElement(questionId);
	}
	
	//Hide the single select
	if(document.getElementsByName(questionId+"_textbox")!=null && document.getElementsByName(questionId+"_textbox")!=undefined &&
	document.getElementsByName(questionId+"_textbox")[0]!=null && document.getElementsByName(questionId+"_textbox")[0]!=undefined){
	document.getElementsByName(questionId+"_textbox")[0].parentElement.style.display="none";
	
		var val2 = document.getElementsByName(questionId+"_textbox")[0].value;
		if(val2 =="" || val2==null)
			val2=val;//val doesn't exist in the dropdown, because it is coming from the Search button
	}
	//$j("#" + questionId + "Description").text(val2);
}




function repeatingBlockHandleEditCodedWithSearchCodedResult(val, questionId, description, descriptionId, codeId) {


	$j("#" + questionId + "Search").hide();
	$j("#" + questionId + "ClearButton").hide();
	
	
	//getElementByIdOrByName(questionId).value=codeId;
	//getElementByIdOrByName(questionId+"_textbox").value=description;
	



	if(document.getElementById(questionId)!=null && document.getElementById(questionId) !=undefined){
		document.getElementById(questionId).value=codeId;
		getElementByIdOrByName(questionId+"_textbox").value=description;
		autocompTxtValuesForJSPByElement(questionId);
		$j("#" + questionId + "CodeId").val("");
		$j("#" + questionId + "DescriptionId").val("");
	}
	
	
	//Show the single select
	if(document.getElementsByName(questionId+"_textbox")!=null && document.getElementsByName(questionId+"_textbox")!=undefined &&
	document.getElementsByName(questionId+"_textbox")[0]!=null && document.getElementsByName(questionId+"_textbox")[0]!=undefined){
	document.getElementsByName(questionId+"_textbox")[0].parentElement.style.display="";
	
		var val2 = document.getElementsByName(questionId+"_textbox")[0].value;
		if(val2 =="" || val2==null)
			val2=val;//val doesn't exist in the dropdown, because it is coming from the Search button
	}
	//$j("#" + questionId + "Description").text(val2);

}

  
function clearReassignClicked(identifier)
{
  $j("#" + identifier).val("");
  $j("#" + identifier + "Span").html("");
  $j("#" + identifier + "Uid").val("");
  $j("#" + identifier + "Text").show();
  $j("#" + identifier + "CodeLookupButton").show();
  $j("#clear" + identifier).hide();
  $j("#" + identifier + "SearchControls").show();
}

// As Of dates are unusual as the Information As Of shows on Create but the other As Of dates do not
// They only show on Edit (They have a visibility type of E in the XSL)
function pgCheckForBlankAsOfErrorsOnSubmit() {
    var errorElts = new Array();
    var errorLabels = new Array();
   
    //if Other Personal Details As Of not present - we are in Create not Edit mode   
    var sexAsOfDateEle = getElementByIdOrByName("NBS096");
    if (sexAsOfDateEle==null) { //return not edit or not present
 	return {elements : errorElts, labels : errorLabels}
    }   
   
    retVal = pgCheckForBlankNameAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
            errorElts = errorElts.concat(retVal.elements);
            errorLabels = errorLabels.concat(retVal.labels);
    }      
    
    retVal = pgCheckForBlankOtherPersonalDetailsAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
            errorElts = errorElts.concat(retVal.elements);
            errorLabels = errorLabels.concat(retVal.labels);
    }
    
    retVal = pgCheckForBlankMaritalStatusAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
            errorElts = errorElts.concat(retVal.elements);
            errorLabels = errorLabels.concat(retVal.labels);
    }
    
    
    retVal = pgCheckForBlankAddressAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
            errorElts = errorElts.concat(retVal.elements);
            errorLabels = errorLabels.concat(retVal.labels);
    }
    
    retVal = pgCheckForBlankPhoneAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
            errorElts = errorElts.concat(retVal.elements);
            errorLabels = errorLabels.concat(retVal.labels);
    }
    
    retVal = pgCheckForBlankEthinicityRaceAsOfErrorsOnSubmit();
    if (retVal.elements != null &&  retVal.elements != undefined) {
            errorElts = errorElts.concat(retVal.elements);
            errorLabels = errorLabels.concat(retVal.labels);
    }    
    
  return {elements : errorElts, labels : errorLabels}
}   

function pgCheckForBlankOtherPersonalDetailsAsOfErrorsOnSubmit() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array();
    var sexAsOfDateEle = getElementByIdOrByName("NBS096");
    if (sexAsOfDateEle == null) {
            	return {elements : errorElts, labels : errorMsgs}
    } 
    if(sexAsOfDateEle != null && sexAsOfDateEle.value == "") {
 	//if any as of date sex fields are present and have a value, post an error
 	var dobStr = getElementByIdOrByName("DEM115") == null ? "" : getElementByIdOrByName("DEM115").value;
 	var rptAgeStr = getElementByIdOrByName("INV2001") == null ? "" : getElementByIdOrByName("INV2001").value;
 	var birthSexStr = getElementByIdOrByName("DEM114") == null ? "" : getElementByIdOrByName("DEM114").value;
 	var currSexStr = getElementByIdOrByName("DEM113") == null ? "" : getElementByIdOrByName("DEM113").value;
 	var addedGendStr = getElementByIdOrByName("NBS213") == null ? "" : getElementByIdOrByName("NBS213").value;
 	var prefGendStr = getElementByIdOrByName("NBS274") == null ? "" : getElementByIdOrByName("NBS274").value;
 	var birthCountryStr = getElementByIdOrByName("DEM126") == null ? "" : getElementByIdOrByName("DEM126").value; //Contact only
 	if (dobStr != "" || rptAgeStr != "" || birthSexStr != "" || currSexStr != "" || addedGendStr != "" || prefGendStr != "" || birthCountryStr != "") {
 		 var a2Str = buildErrorAnchorLink(sexAsOfDateEle, "Other Personal Details As Of Date");  
		 var errHtmlStr =  "If any data is present in Other Personal Details then " + a2Str + " is required. ";
		 errorMsgs[i] = errHtmlStr;
		 errorElts[i] = getElementByIdOrByName('NBS096');
		 $j('#NBS096L').css("color", "990000");
		 i++;
	} else $j('#NBS096L').css("color", "black"); //clear color if fields not present
  	
 } else $j('#NBS096L').css("color", "black"); //no red color date present
 return {elements : errorElts, labels : errorMsgs}
}


function pgCheckForBlankMaritalStatusAsOfErrorsOnSubmit() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array();
    var maritalStatusAsOfDateEle = getElementByIdOrByName("NBS098");
    if (maritalStatusAsOfDateEle == null) {
        	return {elements : errorElts, labels : errorMsgs}
    }    
    if(maritalStatusAsOfDateEle != null && maritalStatusAsOfDateEle.value == "") {
 	//if any as of date sex fields are present and have a value, post an error
 	var maritalStr = getElementByIdOrByName("DEM140") == null ? "" : getElementByIdOrByName("DEM140").value;
 	var primaryOccupStr = getElementByIdOrByName("DEM139") == null ? "" : getElementByIdOrByName("DEM139").value;
 	var primaryLangStr = getElementByIdOrByName("DEM142") == null ? "" : getElementByIdOrByName("DEM142").value;
 	var speaksEngStr = getElementByIdOrByName("NBS214") == null ? "" : getElementByIdOrByName("NBS214").value;
 	if (maritalStr!= "" || primaryOccupStr != "" || primaryLangStr != "" || speaksEngStr!= "") {
 		 var a2Str = buildErrorAnchorLink(maritalStatusAsOfDateEle, "Marital Status As Of Date");  
		 var errHtmlStr =  "If any data is present in Marital Status then " + a2Str + " is required. ";
		 errorMsgs[i] = errHtmlStr;
		 errorElts[i] = getElementByIdOrByName('NBS098');
		 $j('#NBS098L').css("color", "990000");
		 i++;
	} else $j('#NBS098L').css("color", "black"); //clear color if fields not present
  	
 } else $j('#NBS098L').css("color", "black"); //no red color date present
 return {elements : errorElts, labels : errorMsgs}
}

function pgCheckForBlankEthinicityRaceAsOfErrorsOnSubmit() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array();
    var raceAsOfDateEle = getElementByIdOrByName("NBS101");
    if (raceAsOfDateEle == null) {
        	return {elements : errorElts, labels : errorMsgs}
    } 
    var amerIndChkd = $j("input[type='checkbox'][name='pageClientVO.americanIndianAlskanRace']").attr('checked');
    var asianChkd = $j("input[type='checkbox'][name='pageClientVO.asianRace']").attr('checked');
    var afrAmerChkd = $j("input[type='checkbox'][name='pageClientVO.africanAmericanRace']").attr('checked');
    var hawianChkd = $j("input[type='checkbox'][name='pageClientVO.hawaiianRace']").attr('checked');
    var whiteChkd = $j("input[type='checkbox'][name='pageClientVO.whiteRace']").attr('checked');
    var unknownRaceChkd = $j("input[type='checkbox'][name='pageClientVO.unKnownRace']").attr('checked');
    var otherRaceChkd = $j("input[type='checkbox'][name='pageClientVO.otherRace']").attr('checked');
    var refusedRaceChkd = $j("input[type='checkbox'][name='pageClientVO.refusedToAnswer']").attr('checked'); 
    var notAskedRaceChkd = $j("input[type='checkbox'][name='pageClientVO.notAsked']").attr('checked'); 
    if ((raceAsOfDateEle != null && raceAsOfDateEle.value == "") && 
        ((amerIndChkd != null && amerIndChkd) || (asianChkd != null && asianChkd) || 
        (afrAmerChkd != null && afrAmerChkd) || (hawianChkd != null && hawianChkd) || 
        (whiteChkd != null && whiteChkd) || (unknownRaceChkd != null && unknownRaceChkd) ||
        (otherRaceChkd != null && otherRaceChkd) || (refusedRaceChkd != null && refusedRaceChkd) || (notAskedRaceChkd != null && notAskedRaceChkd) )) {
         	var a2Str = buildErrorAnchorLink(raceAsOfDateEle, "Race As Of Date");  
		var errHtmlStr =  "If any Race is checked then " + a2Str + " is required. ";
		errorMsgs[i] = errHtmlStr;
		errorElts[i] = getElementByIdOrByName('NBS101');
		$j('#NBS101L').css("color", "990000");
		i++;
	} else $j('#NBS101L').css("color", "black"); //clear color if fields not present
          
    var ethnicityAsOfDateEle = getElementByIdOrByName("NBS100");
    var theEthnicity =   $j('#DEM155 :selected').val();
    if ((theEthnicity != null && theEthnicity != "") && 
        (ethnicityAsOfDateEle != null && ethnicityAsOfDateEle.value == "")) {
         	var a2Str = buildErrorAnchorLink(ethnicityAsOfDateEle, "Ethnicity As Of Date");  
		var errHtmlStr =  "If Ethnicity is selected then " + a2Str + " is required. ";
		errorMsgs[i] = errHtmlStr;
		errorElts[i] = getElementByIdOrByName('NBS100');
		$j('#NBS100L').css("color", "990000");
		i++;
	} else $j('#NBS100L').css("color", "black"); //clear if color fields not present        
        
 return {elements : errorElts, labels : errorMsgs}
}   

function pgCheckForBlankNameAsOfErrorsOnSubmit() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array();
    var nameAsOfDateEle = getElementByIdOrByName("NBS095");
    if (nameAsOfDateEle == null) {
        	return {elements : errorElts, labels : errorMsgs}
    }
    if(nameAsOfDateEle != null && nameAsOfDateEle.value == "") {
 	//if any name fields are present and have a value, post an error
 	var firstNameStr = getElementByIdOrByName("DEM104") == null ? "" : getElementByIdOrByName("DEM104").value;
 	var lastNameStr = getElementByIdOrByName("DEM102") == null ? "" : getElementByIdOrByName("DEM102").value;
 	var middleNameStr = getElementByIdOrByName("DEM105") == null ? "" : getElementByIdOrByName("DEM105").value;

 	if (firstNameStr!= "" || lastNameStr!= "" || middleNameStr != "") {
 		 var a2Str = buildErrorAnchorLink(nameAsOfDateEle, "Name Information As Of Date");  
		 var errHtmlStr =  "If any Name fields are present then " + a2Str + " is required. ";
		 errorMsgs[i] = errHtmlStr;
		 errorElts[i] = getElementByIdOrByName('NBS095');
		 $j('#NBS095L').css("color", "990000");
		 i++;
	} else $j('#NBS095L').css("color", "black"); //clear color if fields not present
  	
 } else $j('#NBS095L').css("color", "black"); //no red color date present
 return {elements : errorElts, labels : errorMsgs}
}


function pgCheckForBlankAddressAsOfErrorsOnSubmit() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array();
    var addressAsOfDateEle = getElementByIdOrByName("NBS102");
    if (addressAsOfDateEle == null) {
    	return {elements : errorElts, labels : errorMsgs}
    }
    if(addressAsOfDateEle != null && addressAsOfDateEle.value == "") {
 	//if any address fields are present and have a value, post an error
 	var street1Str = getElementByIdOrByName("DEM159") == null ? "" : getElementByIdOrByName("DEM159").value;
 	var street2Str = getElementByIdOrByName("DEM160") == null ? "" : getElementByIdOrByName("DEM160").value;
 	var cityStr = getElementByIdOrByName("DEM161") == null ? "" : getElementByIdOrByName("DEM161").value;
	var zipStr = getElementByIdOrByName("DEM163") == null ? "" : getElementByIdOrByName("DEM163").value;
	var countyStr = getElementByIdOrByName("DEM165") == null ? "" : getElementByIdOrByName("DEM165").value;
 	if (street1Str!= "" || street2Str!= "" || cityStr != "" || zipStr != "" || countyStr != "") {
 		 var a2Str = buildErrorAnchorLink(addressAsOfDateEle, "Address Information As Of Date");  
		 var errHtmlStr =  "If any Address fields are present then " + a2Str + " is required. ";
		 errorMsgs[i] = errHtmlStr;
		 errorElts[i] = getElementByIdOrByName('NBS102');
		 $j('#NBS102L').css("color", "990000");
		 i++;
	} else $j('#NBS102L').css("color", "black"); //clear color if fields not present
  	
 } else $j('#NBS102L').css("color", "black"); //no red color date present
 return {elements : errorElts, labels : errorMsgs}
}

function pgCheckForBlankPhoneAsOfErrorsOnSubmit() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array();
    var teleAsOfDateEle = getElementByIdOrByName("NBS103");
    if (teleAsOfDateEle == null) {
    	return {elements : errorElts, labels : errorMsgs}
    }
    if(teleAsOfDateEle != null && teleAsOfDateEle.value == "") {
 	//if any telephone fields are present and have a value, post an error
 	var hmTeleStr = getElementByIdOrByName("DEM177") == null ? "" : getElementByIdOrByName("DEM177").value;
 	var wkTeleStr = getElementByIdOrByName("NBS002") == null ? "" : getElementByIdOrByName("NBS002").value;
 	var cellStr = getElementByIdOrByName("NBS006") == null ? "" : getElementByIdOrByName("NBS006").value;
	var emailStr = getElementByIdOrByName("DEM182") == null ? "" : getElementByIdOrByName("DEM182").value;;
 	if (hmTeleStr != "" || wkTeleStr != "" || cellStr != "" || emailStr != "") {
 		 var a2Str = buildErrorAnchorLink(teleAsOfDateEle, "Telephone Information As Of Date");  
		 var errHtmlStr =  "If any Telephone/Email fields are present then " + a2Str + " is required. ";
		 errorMsgs[i] = errHtmlStr;
		 errorElts[i] = getElementByIdOrByName('NBS103');
		 $j('#NBS103L').css("color", "990000");
		 i++;
	} else $j('#NBS103L').css("color", "black"); //clear color if fields not present
  	
 } else $j('#NBS103L').css("color", "black"); //no red color date present
 return {elements : errorElts, labels : errorMsgs}
}

/*
 * clearSingleSelectWithSearchButton: method called from the new UI component Single Select With Search
 * */
function clearSingleSelectWithSearchButton(id, parentDoc)
{
	if(parentDoc == null|| parentDoc == undefined)
		parentDoc=document;
	
	var testDescription = getElementByIdOrByNameNode(id+"Description", parentDoc);
	var testCodeId = getElementByIdOrByNameNode(id+"CodeId", parentDoc);
	var testDescriptionId = getElementByIdOrByNameNode(id+"DescriptionId", parentDoc);
	
	if(getElementByIdOrByNameNode(id+"_textbox", parentDoc)!=null && getElementByIdOrByNameNode(id+"_textbox", parentDoc) !=undefined)
		getElementByIdOrByNameNode(id+"_textbox", parentDoc).value="";
	
	if(testDescription!=null && testDescription !=undefined){
		testDescription.innerText= "";
		testDescription.textContent= "";
	}
	
	if(testCodeId!=null && testCodeId !=undefined)
		testCodeId.value = null;
	
	if(testDescriptionId!=null && testDescriptionId !=undefined)
		testDescriptionId.value = null;
	
	$j($j("#"+id+"Description", parentDoc).parent().parent()).find(":input:not([type=button])").val("");
	
	if(parentDoc.getElementsByName(id+"_textbox")[0]!=null && parentDoc.getElementsByName(id+"_textbox")[0] != undefined)
		if(parentDoc.getElementsByName(id+"_textbox")[0].parentElement!=null && parentDoc.getElementsByName(id+"_textbox")[0].parentElement!=undefined)
			parentDoc.getElementsByName(id+"_textbox")[0].parentElement.style.display="";
	$j("#" + id + "Search").show();
	
	
	if(id == "NBS_LAB220")//If we are clearing the Resulted Test, then we need to hide the Organism in case is shown
		hideOrganism(parentDoc);
}


function searchFromSingleSelectWithSearch(id) {
	var urlToOpen = "/nbs/SingleSelectLinkPB.do?method=labTestSearchLoad&identifier="+id;
	var params = "left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var o = new Object();
	o.opener = self;

	var dialogFeatures = "dialogWidth: " + 790
	+ "px;dialogHeight: " + 520
	+ "px;status: no;unadorned: yes;scroll: yes;scrollbars: yes;help: no;"
	+ (true ? "resizable: yes;" : "");

	var modWin = openWindow(urlToOpen, o, dialogFeatures, null, "");
	

}


 /**
  * populateCodedWithSearchValue: populates the code and description in the new coded with search component.
  * @param description
  * @param testCode
  * @param id
  */
 function populateCodedWithSearchValue(description, testCode, id)
 {          
	var opener = getDialogArgument();
 	var parent = opener;
	var parentDoc = parent.document;
	var form = JBaseForm;
	var changeElr=true;
	//opener.clearSingleSelectWithSearchButton(id, parentDoc);
	if(parent.window.location.href.indexOf("MyTaskList")!=-1 || parent.window.location.href.indexOf("PatientSearchResults1")!=-1){
		form =JBaseForm;
		changeElr=false;
	}  
	form.getDwrPopulateResultedTestByUid(description, testCode, function(data){   
   	dwr.util.setValue(parentDoc.getElementById(id+"Description"), data);
   	dwr.util.setValue(parentDoc.getElementById(id+"CodeId"), testCode);
   	dwr.util.setValue(parentDoc.getElementById(id+"DescriptionId"), description+" ("+testCode+")");
   
   	//parentDoc.getElementById(id+"_textbox").value="";
   	parentDoc.getElementById(id).value="";

	parentDoc.getElementsByName(id+"_textbox")[0].parentElement.style.display="none";
	
	var classesParent = $j(parentDoc.getElementsByName(id+"_textbox")[0]).parents(".subSect").attr("class");
	
	if(classesParent.indexOf("batchSubSection")==-1)//It is not inside a repeating block subsection, then no need for changing the color
		changeElr=false;
	
	if(id == "NBS_LAB220")//Resulted Test
   		showHideOrganism(parentDoc, testCode);
	
	if(changeElr)
		changeSectionDarkBGColor(parentDoc, id);
 
		if(id != "NBS_LAB220")
			self.close();
 	});
	
 }
 
 
	function changeSectionDarkBGColor(parentDoc, questionIdentifier)
		{
			 var t = $j(parentDoc.getElementById(questionIdentifier)).parents(".subSect")[0];
			 
			 t.style.display = "";
			 for (var i = 0; i < 4; i++){
			 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
			 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
			}
			
			 /*var numericResultTable = parentDoc.getElementById("numericResultTable");
			 numericResultTable.style.display = "";
			 $j(numericResultTable).find("tr").css("background-color","#BCD4F5");	 
			 
			 var textResultTable = parentDoc.getElementById("textResultTable");
			 textResultTable.style.display = "";
			 $j(textResultTable).find("tr").css("background-color","#BCD4F5");
			*/
		}  
	
 

	  



	
	/**
	 * pgCheckPositiveSkintestResultFormatOnSubmit: this method implements the Jira user story ND-27280.
	 * This method will validate If TUB147 (Tuberculin Skin Test Result) =  10828004 (Positive),
	 *  then TUB149 (Tuberculin Skin Test MM of Induration) should be greater than 4 or less than or equal to 99 or blank.
	 *  If it is not validated, then, it will return the corresponding error message to be shown at the top of the page
	 *  from investigation edit submit / create submit.
	 * @returns {___anonymous107923_107964}
	 */
	


	function pgCheckPositiveSkintestResultFormatOnSubmit(testResult, MMInduration)

	{
	   var skinTestResult =  $j("#"+testResult);//If positive
	   var MMOfInduration = $j("#"+MMInduration);// should be >4 or <=99. 
	   
	   var errorElts = new Array();
	   var errorMsgs = new Array();

	   var txtidtbElts=[];
	   var txtidtbEltsLabelNodes=[];
	   
		   
	   txtidtbElts.push(skinTestResult);
	   txtidtbElts.push(MMOfInduration);
	   


	   var j = 0;
	   var i = 0;//Used for Skin Result
	   var k = 1;//Used for MM of Induration
	   var typeElement = "select";
	   var typeElement2 = "input[type=text]";
	
	
	   	if($j(txtidtbElts[i]).parent().parent().css("display")!="none"){

	  		var txtidtbEltsNode2=$j(txtidtbElts[k]).parent().children(typeElement2);
			var txtidtbEltsLabelId2=$j(txtidtbElts[k]).parent().children(typeElement2).attr("id")+"L";
			var txtidtbEltsLabel2=getElementByIdOrByName(txtidtbEltsLabelId2).innerHTML;
			var txtidtbEltsValue=$j(txtidtbElts[i]).parent().children(typeElement).attr("value");
			var txtidtbEltsValue2=$j(txtidtbElts[k]).parent().children(typeElement2).attr("value");

			if(txtidtbEltsValue == '10828004' && ((txtidtbEltsValue2 != undefined && txtidtbEltsValue2 != null && txtidtbEltsValue2 !='') && (txtidtbEltsValue2<=4 ||  txtidtbEltsValue2>99))) {//The elements that we are checking (the value of i, whether is 0 or 1, doesn't have the value, but the other element does have a value, then we need to create an error message for the element we are validating (i)

					var a2Str = buildErrorAnchorLink(txtidtbEltsNode2[0], txtidtbEltsLabel2);  

					var errHtmlStr;
					//Millimeters of Induration must be greater than 4 and less than or equal to 99 if Tuberculin (Mantoux) Skin Test Result is Positive.
					errHtmlStr = a2Str+ " must be greater than 4 and less than or equal to 99 if Tuberculin (Mantoux) Skin Test Result is Positive.";
					errorMsgs[j] = errHtmlStr;
					errorElts[j] = getElementByIdOrByName(txtidtbEltsLabelId2);
					j++;
					$j("#"+txtidtbEltsLabelId2).css("color", "990000");



				} else {

					var theColorIs = $j("#"+txtidtbEltsLabelId2).css("color");

					if (theColorIs == "990000" || theColorIs=="rgb(153, 0, 0)") //reset to black only if red

						$j("#"+txtidtbEltsLabelId2).css("color", "black"); //clear color if fields not present

					}
				}

	   //only need the labels in the batch  

	   return {elements : errorElts, labels : errorMsgs}  



	}
	
	
	function pgCheckNegativeSkintestResultFormatOnSubmit(testResult, MMInduration)

	{
	   var skinTestResult =  $j("#"+testResult);//If negative
	   var MMOfInduration = $j("#"+MMInduration);// should be <10 or =99. 
	   
	   var errorElts = new Array();
	   var errorMsgs = new Array();

	   var txtidtbElts=[];
	   var txtidtbEltsLabelNodes=[];
	   
		   
	   txtidtbElts.push(skinTestResult);
	   txtidtbElts.push(MMOfInduration);
	   


	   var j = 0;
	   var i = 0;//Used for Skin Result
	   var k = 1;//Used for MM of Induration
	   var typeElement = "select";
	   var typeElement2 = "input[type=text]";
	
	
	   	if($j(txtidtbElts[i]).parent().parent().css("display")!="none"){

	  		var txtidtbEltsNode2=$j(txtidtbElts[k]).parent().children(typeElement2);
			var txtidtbEltsLabelId2=$j(txtidtbElts[k]).parent().children(typeElement2).attr("id")+"L";
			var txtidtbEltsLabel2=getElementByIdOrByName(txtidtbEltsLabelId2).innerHTML;
			var txtidtbEltsValue=$j(txtidtbElts[i]).parent().children(typeElement).attr("value");
			var txtidtbEltsValue2=$j(txtidtbElts[k]).parent().children(typeElement2).attr("value");

			if(txtidtbEltsValue == '260385009' && ((txtidtbEltsValue2 != undefined && txtidtbEltsValue2 != null && txtidtbEltsValue2 !='') && (txtidtbEltsValue2>=10 &&  txtidtbEltsValue2!=99))) {//The elements that we are checking (the value of i, whether is 0 or 1, doesn't have the value, but the other element does have a value, then we need to create an error message for the element we are validating (i)

					var a2Str = buildErrorAnchorLink(txtidtbEltsNode2[0], txtidtbEltsLabel2);  

					var errHtmlStr;
					//Millimeters of Induration must be less than 10 or equal to 99 if Tuberculin (Mantoux) Skin Test Result is Negative.
					errHtmlStr = a2Str+ " must be less than 10 or equal to 99 if Tuberculin (Mantoux) Skin Test Result is Negative.";
					errorMsgs[j] = errHtmlStr;
					errorElts[j] = getElementByIdOrByName(txtidtbEltsLabelId2);
					j++;
					$j("#"+txtidtbEltsLabelId2).css("color", "990000");



				} else {

					var theColorIs = $j("#"+txtidtbEltsLabelId2).css("color");

					if (theColorIs == "990000" || theColorIs=="rgb(153, 0, 0)") //reset to black only if red

						$j("#"+txtidtbEltsLabelId2).css("color", "black"); //clear color if fields not present

					}
				}

	   //only need the labels in the batch  

	   return {elements : errorElts, labels : errorMsgs}  



	}
	

	
	
	function pgCheckStateCaseNumberUniquenessOnSubmit(conditionCode)
	{
	   var stateCaseNumber =  $j("#INV173");//State case number
	   var stateCaseNumberLabel=$j("#INV173L")//State case number label
	   var stateCaseNumberValue = "";
	   var errorElts = new Array();
	   var errorMsgs = new Array();

	   //If State case number exists in the page
	   if(stateCaseNumber!=null && stateCaseNumber!=undefined && stateCaseNumber!="null"){
		   stateCaseNumberValue = stateCaseNumber.val();

		   if(stateCaseNumberValue!=''){
			   JPageForm.isStateCaseNumberUnique(stateCaseNumberValue, conditionCode, function(data) {
		    
		    	if(data==false || data=="false"){
		    		
		    		//State case number is not unique across investigations in the system
					var errHtmlStr = "The value entered for Investigation Case State ID is not unique within the system.";
					errorMsgs[0] = errHtmlStr;
					errorElts[0] = getElementByIdOrByName(stateCaseNumberLabel);
		    	}
		    });
		   }
	   }
	   
	   //only need the labels in the batch  
	   return {elements : errorElts, labels : errorMsgs}  
	}
	
	
	
	
