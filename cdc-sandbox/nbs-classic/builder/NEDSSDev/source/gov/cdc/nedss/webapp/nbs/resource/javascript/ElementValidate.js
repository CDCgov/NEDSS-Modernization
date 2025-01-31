/**
 *  checks validation for each element
 *  Displays error message
 *  @param element - the input object that has validate attribute
 *  @return true for valid , false for invalid
 *
 */
function validate(element)
{
	var labelList = new Array();

	var errorText = "";
	var tdErrorCell = getTdErrorCell( element) ;
	var elementValidate = getCorrectAttribute (element, "validate", element.validate);
	var elementFieldLabel = getCorrectAttribute(element, "fieldLabel", element.fieldLabel);

	if (elementValidate == "jurisdictionConditionCodeDuplicateCheck") {


		var identifier=null;
		var varCaller;

		if(arguments.caller!=undefined)
			varCaller=arguments.caller;
		else
			varCaller=validate.caller.arguments;
		
		for(var i=0;i<varCaller.length;i++){
			if(i==1)
				identifier=varCaller[i];
		}
		//alert("what is indeneejr = "+identifier);

		setAttributeClass(tdErrorCell,"none");
		// check error condition
		var programAreaNode = getElementByIdOrByName("userProfile.theRealizedRole_s[i].programAreaCode");
		var jurisdictionNode = getElementByIdOrByName("userProfile.theRealizedRole_s[i].jurisdictionCode");
		var seqNum = getElementByIdOrByName("userProfile.theRealizedRole_s[i].seqNum");

		var roleNode = getElementByIdOrByName("Role");
		var flag = true;
		//alert(roleNode.value);
		var items = roleNode.value.split("|");

		var palength = "userProfile.theRealizedRole_s[i].programAreaCode~".length;

		var jurlength= "userProfile.theRealizedRole_s[i].jurisdictionCode~".length;
		var rolelength = "userProfile.theRealizedRole_s[i].roleName~".length;
		var len = "userProfile.theRealizedRole_s[i].".length;


		var startpos = 0;
		var paValue = "";
		var jurValue = "";

		if (items.length > 1)
		{
			//alert("***seqNum: " + seqNum.value);
			var temp = null;
			temp = seqNum.value;


			for (var i=0; i < items.length-1; i++)
			{


				if(seqNum.value.indexOf("b")!=-1)
				{

					temp = seqNum.value.substring(1);
					temp = new Number(temp)+1;
					//alert("***temp: " + temp + " and identifier: " + identifier);
					if(temp!=identifier)
					{
						if(items[i].search(/statusCd~I/)==-1)
						{

							var seqnum = items[i].search(/index/i);

							if(gBatchEntryIndex<10)
							{
								//alert("single digit")
								num = items[i].substr(seqnum + 6,1);
							}
							else
							{
								//alert("multi");
								num = items[i].substr(seqnum + 6,2);
							}


							if (num != gBatchEntryIndex)
							{

								var jurstartpos = items[i].search(/userProfile/i);
								startpos = jurstartpos + palength;
								var pastartpos = items[i].search(/programAreaCode/i);
								var x  = pastartpos - len ;
								var begin = startpos + 1;
								var end =  x -1;
								var jurValue= items[i].substring(begin,end);
								pastartpos = x + palength;
								var rolestartpos = items[i].search(/roleName/i);
								var y  = rolestartpos - len ;
								var paValue = items[i].substring(pastartpos, y-1);
								var seqNumStartPos = items[i].search(/seqNum/i);
								var z =  seqNumStartPos - len;
								rolestartpos = y + rolelength;
								var permSetValue = items[i].substring(rolestartpos, z-1);



								if (paValue == programAreaNode.value)
								{

									if( jurValue == jurisdictionNode.value)
									{
										flag = false;
									}
								}

							}   // end of if seq num

						}
					}
				}
				else
				{
					//alert("&&&temp: " + temp + " and identifier: " + identifier);
					if(temp!=identifier)
					{
						if(items[i].search(/statusCd~I/)==-1)
						{

							var seqnum = items[i].search(/index/i);

							if(gBatchEntryIndex<10)
							{
								//alert("single digit")
								num = items[i].substr(seqnum + 6,1);
							}
							else
							{
								//alert("multi");
								num = items[i].substr(seqnum + 6,2);
							}


							if (num != gBatchEntryIndex)
							{

								var jurstartpos = items[i].search(/userProfile/i);
								startpos = jurstartpos + palength;
								var pastartpos = items[i].search(/programAreaCode/i);
								var x  = pastartpos - len ;
								var begin = startpos + 1;
								var end =  x -1;
								var jurValue= items[i].substring(begin,end);
								pastartpos = x + palength;
								var rolestartpos = items[i].search(/roleName/i);
								var y  = rolestartpos - len ;
								var paValue = items[i].substring(pastartpos, y-1);
								var seqNumStartPos = items[i].search(/seqNum/i);
								var z =  seqNumStartPos - len;
								rolestartpos = y + rolelength;
								var permSetValue = items[i].substring(rolestartpos, z-1);



								if (paValue == programAreaNode.value)
								{

									if( jurValue == jurisdictionNode.value)
									{
										flag = false;
									}
								}

							}   // end of if seq num

						}
					}
				}
			}
		}

		if (isblank(element.value) || flag == false  ) {

			var brNode = document.createElement("br");
			tdErrorCell .appendChild(brNode);
			if (flag==true)
			{
				if( !elementFieldLabel)
				errorText = makeErrorMsg('ERR023', labelList);
				else

				errorText = makeErrorMsg('ERR023', labelList.concat(elementFieldLabel));

			}
			else	// flag = false and this is a duplication error
			{
				errorText = makeErrorMsg('ERR057');

			}

			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell, errorText);
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");

				return false;
		}
		else {
			return true;
		}

	} //jurisdictionConditionCodeDuplicateCheck

	if (elementValidate == "pregnantMale")
	{
		var sexNode = null;
		sexNode = getElementByIdOrByName("DEM113");
		if (sexNode==null)
		{
			sexNode = getElementByIdOrByName("entity.sex");
		}

		var varOptions = sexNode.options;
		if (sexNode.selectedIndex != -1)
		{
			sexNode = varOptions[sexNode.selectedIndex].text;
		}
		else
		{
			sexNode = "";
		}	

		if ( (sexNode == "Male") && ( (element.value == "UNK") || (element.value == "Y") ) )
		{
			errorText = makeErrorMsg('ERR177');
		    setText(tdErrorCell, errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}

		return true;

		
	} //pregnantMale
    
	if(elementValidate == "CON241")
    {
    	var a = new Array();
 	    var conFrom241 = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.infectiousFromDate_s");
 	    var varConF241 = conFrom241.value;
 	    var conTo241 = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.infectiousToDate_s");
	    var varConT241 = conTo241.value;
	    if(isEmpty(varConF241))
	    {
	        return true;
	    }
	    if(!isDate(varConF241))
	    {
	        a[0] = conFrom241.getAttribute("fieldLabel");

			setText(tdErrorCell,makeErrorMsg('ERR003', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    if(isEmpty(varConT241))
	    {
	        return true;
	    }
	    if(!isDate(varConT241))
	    {
	        a[0] = conTo241.getAttribute("fieldLabel");

			setText(tdErrorCell,makeErrorMsg('ERR003', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    if(CompareDateStrings(varConF241, varConT241) >= 0)
	    {
	        a[0] = eBMD241.getAttribute("fieldLabel");
			
			setText(tdErrorCell,makeErrorMsg('ERR006', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    return true;
    }


        if (elementValidate == "BMD241")
	{
	    var a = new Array();
	    var eBMD241 = getElementByIdOrByName("supplemental_s[26].obsValueDateDT_s[0].fromTime_s");
	    var varBMD241 = eBMD241.value;
	    if(isEmpty(varBMD241))
	    {
	        return true;
	    }
	    if(!isDate(varBMD241))
	    {
	        a[0] = eBMD241.getAttribute("fieldLabel");
			setText(tdErrorCell, makeErrorMsg('ERR003', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    var eDOB = getElementByIdOrByName("DEM115");
	    var varDOB = eDOB.value;
	    if(CompareDateStrings(varBMD241, "12/31/1875") <= 0)
	    {
	        a[0] = eBMD241.getAttribute("fieldLabel");
			setText(tdErrorCell, makeErrorMsg('ERR004', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    if(CompareDateStringToToday(varBMD241) >= 0)
	    {
	        a[0] = eBMD241.getAttribute("fieldLabel");
			setText(tdErrorCell, makeErrorMsg('ERR004', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    if(CompareDateStrings(varBMD241, varDOB) >= 0)
	    {
	        a[0] = eBMD241.getAttribute("fieldLabel");
			setText(tdErrorCell, makeErrorMsg('ERR006', a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    return true;
        }

	if (elementValidate == "required") {


		if (isblank(element.value)) {


			if( !elementFieldLabel ) {

			       // changed as per Jay Kim, if errorCode exist in XSP take it or it is ERR001
			       if(getCorrectAttribute(element, "errorCode", element.errorCode))
			       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       else

				errorText = makeErrorMsg('ERR001', labelList);
			}

			else {
			        // changed as per Jay Kim, if errorCode exist in XSP take it or it is ERR001
			       if(getCorrectAttribute(element, "errorCode", element.errorCode))
			       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       else
				errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
			}


			/*
			if(element.fieldLabel == "County")
				errorText = makeErrorMsg('ERR001', labelList.concat(element.fieldLabel));
			if(element.fieldLabel == "Type")
				errorText = makeErrorMsg('ERR001', labelList.concat(element.fieldLabel));

			*/

			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			}
			else {
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			  
			  
			  }
			setAttributeClass(tdErrorCell,"error");
				return false;
		}
		else {
			return true;
		}
	} //required
	
	if (elementValidate == "requiredIfSTD") {

        var ccd1 = getElementByIdOrByName("ccd");
        
        var stdConditions = getElementByIdOrByName("STDConditions");
        
        if(ccd1.value==null || stdConditions.value==null){
        	return;
        }
        //alert(stdConditions.value);
        //alert(ccd1.value);
		if (isblank(element.value) && conditionInList(stdConditions.value, ccd1.value)) {


			if( !elementFieldLabel ) {

			       if(getCorrectAttribute(element, "errorCode", element.errorCode))
			       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       else

				errorText = makeErrorMsg('ERR001', labelList);
			}

			else {
			       if(getCorrectAttribute(element, "errorCode", element.errorCode))
			       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       else
				errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
			}


			/*
			if(element.fieldLabel == "County")
				errorText = makeErrorMsg('ERR001', labelList.concat(element.fieldLabel));
			if(element.fieldLabel == "Type")
				errorText = makeErrorMsg('ERR001', labelList.concat(element.fieldLabel));

			*/

			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			}
			else {
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			  
			  }
			setAttributeClass(tdErrorCell,"error");
				return false;
		}
		else {
			return true;
		}
	} //required

	if (elementValidate == "requiredForSelect") {

		var labelList = new Array();

		//tdErrorCell.className="none";
		if (isblank(element.value)) {

			if( !elementFieldLabel )
				errorText = makeErrorMsg('ERR001', labelList);


			else
				errorText = makeErrorMsg('ERR001',labelList.concat(elementFieldLabel));



			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			}
			else {
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			  
			  }
			setAttributeClass(tdErrorCell,"error");
				return false;
		}
		else {
			return true;
		}
	} //requiredForSelect

	if (elementValidate == "requiredIfOtherNotEmpty") {

		var otherElement = getElementByIdOrByName( getCorrectAttribute(element, "nameRef", element.nameRef));

		if ( !isblank(otherElement.value) && isblank(element.value) ) {
			var errorText = "";
			if( !elementFieldLabel )

				errorText = makeErrorMsg('ERR001', labelList);
			else
			{
				//errorText = element.fieldLabel + "is a required field. Please make a selection and try again.";

				errorText = makeErrorMsg('ERR002', labelList.concat(elementFieldLabel));

			}

			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			}
			else{
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

			}
			setAttributeClass(tdErrorCell,"error");

			return false;
		}
		else {
			return true;
		}
	} //requiredIfOtherNotEmpty


	//VL for validation id value(input box) without entering id type (drop down box)

	if (elementValidate == "ssn")	{

		if (isblank(element.value))
			return true;

		else {

			if (isSSN(element.value))	{
				//	if it was an error need to clear it out
				return true;
			}
			else	{	//	else it false validation and you need to tell the user what went wrong


				//	check if there already is an error msg there

				setText(tdErrorCell, "error in ssn");
				
				return false;
			}

		}
	} //ssn


	if (elementValidate == "dateFormat") {

		if (isblank(element.value)){
			return true;
		}else {
			// First check if it is in mm/dd/yyyy format
			if (!isDate( element.value ) ) {

				if( !elementFieldLabel ){
					errorText = makeErrorMsg('ERR003', labelList);
				}

				else
				{
					//errorText = "Please enter " + element.fieldLabel + " using this format: 'mm/dd/yyyy'.";
					errorText = makeErrorMsg('ERR003', labelList.concat(elementFieldLabel));

				 }

				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

				  
				}
				setAttributeClass(tdErrorCell,"error");

				return false;

			// if is in mm/dd/yyyy format, check if it in a right range.
			} else {
				return true;
			}
		}
	} //dateFormat


	if(elementValidate == "conditional-date") {
		if (isblank(element.value)){

			errorText = makeErrorMsg("ERR112", labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			}
			else {
				setText(tdErrorCell,errorText);
			}
			setAttributeClass(tdErrorCell,"error");

			return false;
		} else {

if (!isDate( element.value ) ) {

				if( !elementFieldLabel )

					errorText = makeErrorMsg('ERR011', labelList);
				else {
					errorText = makeErrorMsg('ERR003', labelList.concat(elementFieldLabel));
					//errorText = makeErrorMsg('ERR003', labelList.concat(element.fieldLabel, 'replacement1','replacement2', 'replacement3', 'replacement4', 'replacement5'));
				}


				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

				}
				setAttributeClass(tdErrorCell,"error");

				return false;

			// if is in mm/dd/yyyy format, check if it in a right range.
			} else	if ((CompareDateStrings(element.value, "12/31/1875") == -1) ||
				    (CompareDateStringToToday(element.value) == 1))  {

				errorText = makeErrorMsg('ERR004', labelList.concat(elementFieldLabel));

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



		}


	}//conditional-date


	if (elementValidate == "date")	{
		var labelList = new Array();

		if (isblank(element.value) && (element.required && (element.required=="true"||element.required==true)))	{
		  // alert("test1 " +getCorrectAttribute(element,"fieldLabel",element.fieldLabel));
                       	if( !elementFieldLabel )
			{
			   if(getCorrectAttribute(element, "errorCode", element.errorCode))
			      errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList);
			   else
			      errorText = makeErrorMsg('ERR001', labelList);
			}
			else if(elementFieldLabel == "Treatment Date")
			{
			       // alert("inside else if");
			        if(getCorrectAttribute(element, "errorCode", element.errorCode))
			     	     errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			     	else
			             errorText = makeErrorMsg('ERR023', labelList.concat(elementFieldLabel));
			}
			else
			{
			   if(getCorrectAttribute(element, "errorCode", element.errorCode))
			     errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			   else
			     errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
			}

			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			}
			else {
				setText(tdErrorCell,errorText);
				
			}
			setAttributeClass(tdErrorCell,"error");

			return false;

		} else if (isblank(element.value)){

			return true;

		} else {

			// First check if it is in mm/dd/yyyy format

			if (!isDate( element.value ) ) {

				if( !elementFieldLabel )

					errorText = makeErrorMsg('ERR011', labelList);
				else {
					errorText = makeErrorMsg('ERR003', labelList.concat(elementFieldLabel));
					//errorText = makeErrorMsg('ERR003', labelList.concat(element.fieldLabel, 'replacement1','replacement2', 'replacement3', 'replacement4', 'replacement5'));
				}


				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

				  
				}
				setAttributeClass(tdErrorCell,"error");

				return false;

			// if is in mm/dd/yyyy format, check if it in a right range.
			} else	if ((CompareDateStrings(element.value, "12/31/1875") == -1) ||
			             (CompareDateStringToToday(element.value) == 1))  {
				if(elementFieldLabel != "Infectious Period To"){

					errorText = makeErrorMsg('ERR004', labelList.concat(elementFieldLabel));
	
					if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);
					}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
	
					}
					setAttributeClass(tdErrorCell,"error");
	
					return false;
				}
			} else { 
				//Compare against DOB to make sure date is after
				if ( (elementFieldLabel == "Investigation Start Date") ||
	  				 (elementFieldLabel == "Diagnosis Date") ||
	  				 (elementFieldLabel == "Illness Onset Date") ||
	  				 (elementFieldLabel == "County") ||
					 (elementFieldLabel == "State") 
					 )
				{
				    var a = new Array();
					var eDOB = getElementByIdOrByName("DEM115");
				    var varDOB = eDOB.value;
				    if (!(isblank(varDOB)) && (CompareDateStrings(element.value, varDOB) < 0))
				    {
				        a[0] = element.getAttribute("fieldLabel");
						setText(tdErrorCell, makeErrorMsg('ERR178', a));
				        setAttributeClass(tdErrorCell,"error");
				        return false;
				    }
				}
				//Add lab compares against DOB to make sure date is after
	  			if (elementFieldLabel == "Date Specimen Collected")
	  			{
				    var a = new Array();
					var eDOB = null;
					eDOB = getElementByIdOrByName("entity.DOB");
					if (eDOB==null)
					{
						eDOB = getElementByIdOrByName("DEM115");
					}
				    var varDOB = eDOB.value;
				    if (!(isblank(varDOB)) && (CompareDateStrings(element.value, varDOB) < 0))
				    {
				        a[0] = element.getAttribute("fieldLabel");
						setText(tdErrorCell, makeErrorMsg('ERR178', a));
				        setAttributeClass(tdErrorCell,"error");
				        return false;
				    }
				}
				//Compare Date Received by Public Health to Lab Report Date - Report date must come on or before Received date
				if (elementFieldLabel == "Date Received by Public Health") 
	  			{
					var eReport = null;
				    var varReport = "";
					eReport = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.activityToTime_s");
					if (eReport==null)
					{
						eReport = getElementByIdOrByName("morbidityReport.theObservationDT.activityToTime_s");
					}
					else 
					{
						varReport = eReport.value;
					}
				    if (!(isblank(varReport)) && (CompareDateStrings(element.value, varReport) < 0))
				    {
						
					   setText(tdErrorCell,makeErrorMsg('ERR179'));
				       setAttributeClass(tdErrorCell,"error");
				        return false;
				    }
	  			}
				//Compare Diagnosis to Onset - can't diagnosis before onset
	  			if (elementFieldLabel == "Diagnosis Date") 
	  			{
					var eOnset = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveFromTime_s");
				    var varOnset = eOnset.value;
				    if (!(isblank(varOnset)) && (CompareDateStrings(element.value, varOnset) < 0))
				    {
						setText(tdErrorCell,makeErrorMsg('ERR180'));
				        setAttributeClass(tdErrorCell,"error");
				        return false;
				    }
	  			}
	  			
	  		
				return true;
			}
		}
	} //date
        if (elementValidate == "BMD264")
	{
	    var a = new Array();
	    var eBMD264 = getElementByIdOrByName("antibioticBatchEntry_s[i].observationVO_s[2].obsValueNumericDT_s[0].numericValue1_s");
	    var varBMD264 = eBMD264.value;
	    if(isEmpty(varBMD264))
	    {
	        return true;
	    }
	    if(!isNumeric(varBMD264))
	    {
	        a[0] = eBMD264.getAttribute("fieldLabel");
			setText(tdErrorCell,makeErrorMsg('ERR008',a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    if(varBMD264 == 999)
	    {
	       return true;
	    }
	    if( (varBMD264 < 0) || (varBMD264 > 300) )
	    {
	        a[0] = eBMD264.getAttribute("fieldLabel");
			setText(tdErrorCell,makeErrorMsg('ERR094',a));
	        setAttributeClass(tdErrorCell,"error");
	        return false;
	    }
	    return true;
        }//BMD264

	if (elementValidate == "validFromDate") {

		return basicDateVal(element);
	}

	if (elementValidate == "afterDOB")	{

		if(basicDateVal(element) == true) return advanceDateVal(element);

	}//afterDOB


	// This method specific for due date field is in Hep-General, It will accept for future date

	if (elementValidate == "dueDate")	{
		if(basicDateVal(element) == true) return advanceDateVal(element);
	}//dueDate


	if (elementValidate == "expirationBeforeDate")
	{
		return RUB118();
		//return advanceDateVal(element);
	}//expirationBeforeDate

	if (elementValidate == "LAB361")
	{
		return changeLAB361(element);
	}//LAB361
	if (elementValidate == "LAB362")
		{
			return changeLAB362(element);
	}
	if (elementValidate == "LAB356")
	{
		return changeLAB356(element);
	}
	if (elementValidate == "LAB349")
	{
		return changeLAB349(element);
	}
	if (elementValidate == "dateRange")	{
	      	if (isblank(element.value))
			return true;
		else {
			//Compare against DOB to make sure date is after
			if ( (elementFieldLabel == "Illness Onset Date") )
			{
			    var a = new Array();
				var eDOB = getElementByIdOrByName("DEM115");
			    var varDOB = eDOB.value;
			    if (!(isblank(varDOB)) && (CompareDateStrings(element.value, varDOB) < 0))
			    {
			        a[0] = element.getAttribute("fieldLabel");
					setText(tdErrorCell,makeErrorMsg('ERR178',a));
			        setAttributeClass(tdErrorCell,"error");
			        return false;
			    }
			}

			if(basicDateVal(element) == true) {
			   
				if(getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null && !(isblank(element.value))){

				var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));

					if(partnerNode!=null && partnerNode.value == "") { //error condition
						//var errorText = "please enter the associated data";
						errorText = makeErrorMsg('ERR158', labelList.concat('DOB Begin Date','DOB End Date'));
						if( tdErrorCell.innerText == "" ){
						  setText(tdErrorCell,errorText);
						}
						else{
						  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
						  
						}
						setAttributeClass(tdErrorCell,"error");
					return false;
				}
				}
				if(futureDateVal(element) == true)
					return compareDateVal(element);
				else
					return false;
			} else
				return false;
		}//else

	}//dateRange
if (elementValidate == "checkCultureDate")	{

	      	if (isblank(element.value))
			return abcStateCaseRequiredFieldValidator(element);
		else {


			if(basicDateVal(element) == true) {


				if(futureDateVal(element) == true){

					return compareDate(element);
				}
				else
					return false;
			} else
				return false;
		}//else

	}//dateRange

	if (elementValidate == "validDeceasedDate")	{
	
	        
		if (isblank(element.value))
			return true;
		else {


			if(basicDateVal(element) == true) {


				if(futureDateVal(element) == true)
					return compareDateValForDeath(element);
				else
					return false;
			} else
				return false;
		}//else

	} //validDeceasedDate

	if (elementValidate == "validAfterDOB")	{
	
	        
		if (isblank(element.value))
			return true;
		else {


			if(basicDateVal(element) == true) {


				if(futureDateVal(element) == true)
					return compareDateValForDeath(element);
				else
					return false;
			} else
				return false;
		}//else

	} //validAfterDOB


	if (elementValidate == "yearFormat") {

		if (isblank(element.value))
		{
			return true;
		}

		else
		{
			// First check if it is in yyyy format
			var pattern = /\d{4}/;
			if(pattern.exec(element.value) == null)
			{


				if(!elementFieldLabel)
					errorText = makeErrorMsg('ERR076', labelList);

				else
				{
					//errorText = "Please enter " + element.fieldLabel + " using this format: yyyy.";
					errorText = makeErrorMsg('ERR076', labelList.concat(elementFieldLabel));
				}
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
		}

	} //yearFormat


	if (elementValidate == "validateYear" ) {


		thisYear = new Date().getFullYear();

		if(isblank(element.value))
			return true;

		else if (!isblank(element.value) && thisYear >= element.value && element.value >= 1875) {
			return true;
		}
		else {

			errorText = makeErrorMsg('ERR082', labelList.concat(elementFieldLabel).concat('1875'));
			//errorText="Please enter " + element.fieldLabel + " field between 1875 and current year.";
			var pattern = /\d{4}/;
			if(pattern.exec(element.value) == null)
			{

				var errorText = "";
				if( !elementFieldLabel )
					errorText = makeErrorMsg('ERR076', labelList);

				else
				{
					errorText = makeErrorMsg('ERR076', labelList.concat(elementFieldLabel));
					//errorText = "Please enter " + element.fieldLabel + " using this format: yyyy.";
				}

				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);
				}
				else{
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

				}
					setAttributeClass(tdErrorCell,"error");

				return false;


			}

			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

			}
				setAttributeClass(tdErrorCell,"error");

			return false;
		}
	} //validateYear



	if(elementValidate == "Validate_MMWR_Year")
	{



		var notificationExists = getElementByIdOrByName("NotificationExists");

		if(isblank(element.value) && notificationExists.value=="true")
					{
			errorText = makeErrorMsg('ERR135', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}

		if(isblank(element.value))
		{
			return true;
		}

		if(isNumeric(element.value) == false || element.value < 1950 || element.value > currentYearplus)
		{	errorText = makeErrorMsg('ERR050', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
		return true;

	}//Validate_MMWR_Year





	if(elementValidate == "requiredForNotification")
		{
			return requiredForNotification(element);

			return true;

		}//requiredForNotification

	if(elementValidate == "abcStateCaseRequiredFieldValidator")
				{
					return abcStateCaseRequiredFieldValidator(element);
					
					return true;
				
		}//abcStateCaseRequiredFieldValidator


	if (elementValidate == "yearValidation")	{


		if (isblank(element.value))
		{
			return true;
		}
		else
		{
			// First check if it is in yyyy format
			var pattern = /\d{4}/;
			if(pattern.exec(element.value) == null)
			{


				var errorText = "";

				if( !elementFieldLabel )

					errorText = makeErrorMsg('ERR076', labelList);
				else
				{
					errorText = makeErrorMsg('ERR076', labelList.concat(elementFieldLabel));
					//errorText = "Please enter " + element.fieldLabel + " using this format: yyyy.";

				}
				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

				}
				setAttributeClass(tdErrorCell,"error");

				return false;


			}
			else {


				// Get value of another date that we need to compare to.
				var dateAfter = element.value;
				var dateBefore = "";

				var year;
				var now=new Date();
				var currentYear = now.getFullYear();
				if(dateAfter !="")
				{
				    	if( getCorrectAttribute(element,"dateBeforeRef",element.dateBeforeRef) )
					{
						dateBefore = getElementByIdOrByName( getCorrectAttribute(element,"dateBeforeRef",element.dateBeforeRef) ).value;
						year = dateBefore.substring(6,10);
					}
					else
						return true;

					if(dateAfter < year)
					{
						var errorText = makeErrorMsg('ERR082', labelList.concat(elementFieldLabel).concat('1875'));
						setText(tdErrorCell,errorText);
						setAttributeClass(tdErrorCell,"error");
						return false;
					}
					if((year =="" && dateAfter > currentYear) || (year =="" && dateAfter <1875))
					{

						errorText = makeErrorMsg('ERR082', labelList.concat(elementFieldLabel).concat('1875'));
						setText(tdErrorCell,errorText);
						setAttributeClass(tdErrorCell,"error");
						return false;
					}
					if((year !="" && dateAfter > currentYear) || (year !="" && dateAfter <1875))
					{
					     	errorText = makeErrorMsg('ERR082', labelList.concat(elementFieldLabel).concat('1875'));
						setText(tdErrorCell,errorText);
						setAttributeClass(tdErrorCell,"error");
						return false;
					}

				}
				else
					return true;
			}
		}

	} //yearValidation


	if (elementValidate == "ssnComplexCheck" ) {

		var selectBox = getElementByIdOrByName( element.nameRef );

		if(  (selectBox.value != "")  && (isblank(element.value) ) ) {

			var errorText = ("Please enter " + elementFieldLabel + " and try again.");
			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}

		if ( ( selectBox.value == 'AS' ) || ( selectBox.value == 'MSSN' ) || ( selectBox.value == 'SS' ) ) {


			if ( isblank(element.value) ) {
				return true;
			} else if ( !isSSN(trimSpaces(element.value)) ){
				//errorText = "Please enter a valid SSN using this format:  '000-00-0000'.";
				errorText = makeErrorMsg('ERR007', labelList.concat(elementFieldLabel));
				if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);
					  
				}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					  
					}
					setAttributeClass(tdErrorCell,"error");
				return false;
			}
			return true;
		}

	} //ssnComplexCheck


	if (elementValidate == "acctIdPersonCheck" ) {
				if (isblank(element.value))
				{
					var errorText = "";
					if(!elementFieldLabel)

						errorText = makeErrorMsg('ERR023', labelList);
					else
					{
						//errorText = "This is a required field. Please select " + element.fieldLabel + " and try again.";
						errorText = makeErrorMsg('ERR023', labelList.concat(elementFieldLabel));
					}

					if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);

					}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					  
					  
					}

					tsetAttributeClass(tdErrorCell,"error");
					return false;
				}
				var selectBox = getElementByIdOrByName("person.entityIdDT_s[i].rootExtensionTxt");
				if( (selectBox.value == "") && (!isblank(element.value) ) ) {
					//errorText = ("Please enter " + element.fieldLabel + " and try again.");
					errorText = makeErrorMsg('ERR023', labelList.concat(elementFieldLabel));
					setText(tdErrorCell,errorText);
					setAttributeClass(tdErrorCell,"error");
					return false;
				}
				return true;
	} //acctIdPersonCheck


	if (elementValidate == "acctIdSearchCheck") {
		var selectBox = getElementByIdOrByName( getCorrectAttribute(element,"nameRef",element.nameRef));
		var errorID  = 'ERR001';
		if(getCorrectAttribute(element,"type",element.type) == 'text') errorID = 'ERR066';

		if( (selectBox.value!="") && (isblank(element.value) ) ) {
			var errorText = makeErrorMsg(errorID, labelList.concat(elementFieldLabel));
			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
		return true;

	} //acctIdSearchCheck



	if (elementValidate == "zipCode" ) {
		isValid = false;
		if (isblank(element.value))
			return true;
		else
		{
			var s = element.value;

			for(var i = 0; i < s.length; i++)
			{
				var c = s.charAt(i);
				if ((c == '-') || (c == '0') || (c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') || (c == '6') || (c == '7') || (c == '8') || (c == '9')) {

					if(s.length == 5 || s.length == 10) {

						isValid = true;
					} else {
						isValid = false;
					}
				}

				else
				{
					isValid = false;
					break;
				}
			}
			if (!isValid)
			{
				//var errorText = ("Please enter a number into the " + element.fieldLabel + " field.");
				var errorText = makeErrorMsg("ERR103", labelList.concat(elementFieldLabel));
				setText(tdErrorCell,errorText);
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
			return true;
		}
	} //zipCode

       // this code is added bacause some numeric validation will take ZERO also.
       	if (elementValidate == "numericValue" ) {
       
       
       		if(getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null && !(isblank(element.value))){
       
       			var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
       
       		}
       		if (isblank(element.value) || (isNumeric(element.value) && element.value>=0)) {
       			return true;
       		} else {
       
       			//errorText = "Please enter a whole number into " + element.fieldLabel + " that is  greater than or equal to 1.";
       			errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
       			if( tdErrorCell.innerText == "" ){
       			  setText(tdErrorCell,errorText);
				}
       			else{
       			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
       
				}
       			setAttributeClass(tdErrorCell,"error");
       
       
       			return false;
       		}
	} //( numericValue ) Error008

	if (elementValidate == "Error097" ) {


		if(getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null && !(isblank(element.value))){

			var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));

		}
		if (isblank(element.value) || (isNumeric(element.value) && element.value>0)) {
			return true;
		} else {

			//errorText = "Please enter a whole number into " + element.fieldLabel + " that is  greater than or equal to 1.";
			errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			}
			else{
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

			}
			setAttributeClass(tdErrorCell,"error");


			return false;
		}
	} //Error097

	if(elementValidate == "numeric-range") {

		if(isblank(element.value))
			return true;
		else if(isNumeric(element.value)) {
			if(element.value < 0 || element.value > 150) {

				errorText = makeErrorMsg('ERR009', labelList.concat(elementFieldLabel));
				setText(tdErrorCell,errorText);
				setAttributeClass(tdErrorCell,"error");
				return false;
			} else {
				return true;
			}
		} else {
			errorText = makeErrorMsg('ERR009', labelList.concat(elementFieldLabel));
			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}

	} //numeric-range

	if (elementValidate == "numeric" ) {

		var labelList = new Array();
		if(getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null && !(isblank(element.value))){

			var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			if(partnerNode!=null)
				if(partnerNode.value == "") { //error condition
					var errorText = "please enter the associated data";
					if ((getCorrectAttribute(element, "requiredPartner",element.requiredPartner) == "person.personNameDT_s[i].durationUnitCd") || (getCorrectAttribute(element, "requiredPartner",element.requiredPartner) == "person.entityIdDT_s[i].durationUnitCd") || (getCorrectAttribute(element, "requiredPartner",element.requiredPartner) == "address[i].durationUnitCd") || (getCorrectAttribute(element, "requiredPartner",element.requiredPartner) == "telephone[i].durationUnitCd"))
					{
						errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
						//errorText = element.fieldLabel +" is a required field. Please make a selection and try again.";
					}
					if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);
					}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

					}
					setAttributeClass(tdErrorCell,"error");


				return false;
			}
		}
		if (isblank(element.value) || (isNumeric(element.value) && (element.value>=0)))
		{
			if((elementFieldLabel == "specify weeks") && (element.value > 36))
			{
				errorText = makeErrorMsg('ERR080', labelList.concat(elementFieldLabel));
				//errorText = "Please enter a number between 1 and 36 in the weeks field and try again.";
				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);
				}
				else{
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
				}
			return true;
		} else {
			//errorText = element.fieldLabel +" must be a whole number greater than or equal to one.";
		     if(getCorrectAttribute(element, "errorCode", element.errorCode))
			  errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
		     else
			errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));

			if(elementFieldLabel == "Illness Duration")
			{
			errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
			//errorText = element.fieldLabel +" must be a whole number greater than or equal to one.";
			}


			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);

			}
			else {
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);


			 }
			setAttributeClass(tdErrorCell,"error");

			return false;
		}
	} //numeric

	if (elementValidate == "numericBirthOrder" ) {

		if (isblank(element.value)) {
			return true;
		}
		if (isNumeric(element.value))
		{
			if ((element.value <=0) || (element.value >= 25))
			{
				errorText = makeErrorMsg('ERR067', labelList.concat(elementFieldLabel));
				//errorText = "Please enter " + element.fieldLabel + " between 1 and 24 and try again.";
				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				  
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				  
				  
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
			return true;

		}

		else {
			errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
			//errorText = "Please enter a whole number into the " + element.fieldLabel + " field and try again.";
			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
		}
			else{
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			  
			  
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}

	} //numericBirthOrder
	if (elementValidate == "numericGreaterThanZero" ) {

			if (isblank(element.value)) {
				return true;
			}
			if (isNumeric(element.value))
			{
				if ((element.value == 0))
				{
					errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
					//errorText = "Please enter " + element.fieldLabel + " between 1 and 24 and try again.";
					if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);
					  
					}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					  
					  
					}
					setAttributeClass(tdErrorCell,"error");
					return false;
				}
				return true;

			}

			else {
				errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
				//errorText = "Please enter a whole number into the " + element.fieldLabel + " field and try again.";
				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				  
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				  
				  
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
			}

	} //numericGastationalAge
	if (elementValidate == "numericGreaterThanOne" ) {

				if (isblank(element.value)) {
					return true;
				}

				if (isNumeric(element.value))
				{
					if ((element.value == 0))
					{
					    	errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
						//errorText = "Please enter " + element.fieldLabel + " between 1 and 24 and try again.";
						if( tdErrorCell.innerText == "" ){
						  setText(tdErrorCell,errorText);
						  
						}
						else{
						  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
						  
						  
						}
						setAttributeClass(tdErrorCell,"error");
						return false;
					}
					return true;

				}
				else if(element.value<1)
				{
					errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
					//errorText = "Please enter " + element.fieldLabel + " between 1 and 24 and try again.";
					if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);
					  
					}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					  
					  
					}
					setAttributeClass(tdErrorCell,"error");
					return false;
				}

				else {
					errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
					//errorText = "Please enter a whole number into the " + element.fieldLabel + " field and try again.";
					if( tdErrorCell.innerText == "" ){
					  setText(tdErrorCell,errorText);
					  
					}
					else{
					  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					  
					  
					}
					setAttributeClass(tdErrorCell,"error");
					return false;
				}

	} //numericGastationalAge


	if (elementValidate == "numericPreTermBirth" ) {

		if (isblank(element.value)) {
			return true;
		}
		if (isNumeric(element.value))
		{
			if ((element.value <=0) || (element.value >= 37))
			{
				errorText = makeErrorMsg('ERR080', labelList.concat(elementFieldLabel));
				//errorText = "Please enter weeks between 1 and 36 and try again.";
				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				  
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
			return true;

		}
		else {
			errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
			//errorText = "Please enter a whole number into the weeks field and try again.";
			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			  
			}
			else{
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			  
			  
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //numericPreTermBirth


	if (elementValidate == "decimalValidation" ) {

		if(isblank(element.value))
			return true;

		else if (!isblank(element.value) && element.value<=150) {

			var theValue = element.value;
			if(theValue.indexOf(".") != -1) {
				theValue = theValue.substring(0,(theValue.indexOf(".") + 2));
				element.value=theValue;
			}

			return true;
		}
		else
		{
			errorText = makeErrorMsg('ERR009', labelList.concat(elementFieldLabel));
			//errorText = "Please enter " + element.fieldLabel + " field between 0 and 150 and try again.";
			if( tdErrorCell.innerText == "" ){
			setText(tdErrorCell,errorText);

			}
			else{
			setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			
			
			}
			setAttributeClass(tdErrorCell,"error");

			return false;

		}
	} //decimalValidation


	if (elementValidate == "agePertussis" )	{
		if(element.offsetWidth==0 )
			return true;

		if ((isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null) ||
		   (isNumeric(element.value) && (element.value>0) && (element.value<=999)) &&  getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null)
		{
		  return true;
		}
		else
		{
		  if (isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null)
			{
			    var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			    if(partnerNode.value != null && partnerNode.value == "")
				return true;
			}

			var errorText;
			if( !isNumeric(element.value) )
				errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
			else if(element.value>=1)
			{
			   var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			   //we need to show this error only ageUnit is years otherwise return true
			   if(partnerNode != null && partnerNode.value != null && partnerNode.value != ""){			 
			    return true;
			    }
			    else{
				errorText = makeErrorMsg('ERR002', labelList.concat(elementFieldLabel));
			    }	
			}
			else if(element.value<1)
			{
			  errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
			    
			}
			else{
			    var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			    if(partnerNode.value != null && partnerNode.value == "")
				{ 				  
				   errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
				}
			    else
			      return true;
			  }

			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //agePertussis

	if(elementValidate == "telephone") {
		if ((isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null) ||
		   (isNumeric(element.value)) &&  getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null)
		{ 
		  return true;
		}
		else
		{
			var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			if (isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null)
			{   
				var errorText;
			    if(partnerNode.value != null && partnerNode.value.length > 0 )
				{ 
				   errorText = makeErrorMsg('ERR187', labelList.concat(elementFieldLabel));
				}
			    else {
			      return true;
				}
			} else {
				return true;	
			}
			if( tdErrorCell.innerText == "" ){
			setText(tdErrorCell,errorText);
			
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			
			if( tdErrorCell.textContent != undefined &&  tdErrorCell.textContent=="")
				tdErrorCell.textContent = errorText;
			else
				if( tdErrorCell.textContent != undefined)
					tdErrorCell.textContent = tdErrorCell.textContent + "\n" + errorText;
			
			
			
			
			
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	}
	if(elementValidate == "telephone") {


		if ((isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null) ||
		   (isNumeric(element.value)) &&  getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null)
		{ 
		  return true;
		}
		
		else
		{
			var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			
			
			if (isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null)
			{   
				var errorText;
			    
			    if(partnerNode.value != null && partnerNode.value.length > 0 )
				{ 
				   errorText = makeErrorMsg('ERR187', labelList.concat(elementFieldLabel));
				}
			    else {
					
			      return true;
				}

			} else {
				return true;	
			}


			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
		
	
	}

	if (elementValidate == "age" )	{
		if(element.offsetWidth==0 )
			return true;
		if ((isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null) ||
		   (isNumeric(element.value) && (element.value>0) && (element.value<=150)) &&  getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null)
		{
		  return true;
		}
		else
		{
			if (isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null)
			{
			    var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			    if(partnerNode.value != null && partnerNode.value == "")
				return true;
			}

			var errorText;
			if( !isNumeric(element.value) )
				errorText = makeErrorMsg('ERR009', labelList.concat(elementFieldLabel));
			else if(element.value>150 || element.value<1)
			{
			     var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			     //we need to show this error only ageUnit is years otherwise return true
			    if(partnerNode != null && partnerNode.value != null && partnerNode.value != "Y")
			    return true;
				errorText = makeErrorMsg('ERR009', labelList.concat(elementFieldLabel));
			}
			else{
			    var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
			    if(partnerNode.value != null && partnerNode.value == "")
				{ //error condition
				   //errorText = "Please select "+ element.fieldLabel + " unit and try again.";
				   errorText = makeErrorMsg('ERR149', labelList.concat(partnerNode.fieldLabel).concat(elementFieldLabel));
				}
			    else
			      return true;
			  }

			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //age


	if (elementValidate == "ERR008") {

		if (isblank(element.value) ||(isNumeric(element.value) && (element.value>0)) )
		{
                  	return true;
		}
		else
		{
			var errorText;
			if( !isNumeric(element.value) )
				//errorText = "Please enter a whole number into the " + element.fieldLabel + " field.";
				errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
			else if(element.value <1)
				//errorText = "Please enter a whole number into the " + element.fieldLabel + " field.";
				errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" )
			{
				setText(tdErrorCell,errorText);
				
			}
			else
			{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //ERR008


	if (elementValidate == "wholeNuberPertus") {
		if (isblank(element.value) ||(isNumeric(element.value) && (element.value>0)) )
		{
			return true;
		}
		else
		{
			var errorText;
			if( !isNumeric(element.value) )
			{
			//errorText = element.fieldLabel +" must be a whole number greater than or equal to one.";
			errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
			}
			else if(element.value <1)
			{
			//errorText = element.fieldLabel +" must be a whole number greater than or equal to one.";
			errorText = makeErrorMsg('ERR097', labelList.concat(elementFieldLabel));
			}
			if( tdErrorCell.innerText == "" )
			{
				setText(tdErrorCell,errorText);
				
			}
			else
			{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
				return false;
		}
	} //wholeNuberPertus


	if (elementValidate == "checkForPRT009" ) {

		if (isblank(element.value) || ( isNumeric(element.value) && (element.value <=300)  || (element.value ==999)  ) )
		{
			return true;
		}
		else
		{
			errorText = makeErrorMsg('ERR094', labelList.concat(elementFieldLabel));
			//errorText = "Please enter a value in the " + element.fieldLabel + " field that is  between 0 and 300 or enter 999 for unknown.";

			if( tdErrorCell.innerText == "" )
			{
				setText(tdErrorCell,errorText);
				
			}
			else
			{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //checkForPRT009



	if (elementValidate == "numberRange53" ) {

		var notificationExists = getElementByIdOrByName("NotificationExists");

		if(isblank(element.value) && notificationExists.value=="true")
			{
			errorText = makeErrorMsg('ERR135', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		}

		if (isblank(element.value))
			return true;


		if( (element.value > 0) && (element.value <= 53) )
		{
			return true;
		}
		else
		{
			errorText = makeErrorMsg('ERR049', labelList.concat(elementFieldLabel));
			//errorText = "Please enter a number from 1 to 53.";
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);

			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");

			return false;
		}

	} //numberRange53


	if( elementValidate == "lengthLimit" ) {


		var maxLength = element.maxLength;

		if(element.maxFieldLength)
			maxLength = element.maxFieldLength;

		//alert(maxLength);
		if(isLong(element.value, maxLength) == true) {

			errorText = makeErrorMsg('ERR010', labelList.concat(elementFieldLabel).concat(maxLength));
			errorText = errorText.replace('(Maxlength)', maxLength);
			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //lengthLimit

	if( elementValidate == "lengthLimit80") {

		var maxLength = "80";
		if(element.maxFieldLength)
			maxLength = element.maxFieldLength;

		if(element.value.length > maxLength)  {
			errorText = makeErrorMsg('ERR010', labelList.concat(elementFieldLabel).concat( maxLength));
			
			setText(tdErrorCell, errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //lengthLimit80

       if( elementValidate == "lengthLimit2000" ) {

		var maxLength = "2000";
		if(element.maxFieldLength)
			maxLength = element.maxFieldLength;

		if(element.value.length > maxLength)  {
			errorText = makeErrorMsg('ERR010', labelList.concat(elementFieldLabel).concat( maxLength));
			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
	} //lengthLimit2000


	if(elementValidate == "reg-expr"){
		//alert(element.name);
		var pattern = new RegExp(element.pattern);
		if(pattern.exec(element.value) == null){

			if ( element.name == "resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue" ||
				 element.name == "resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue" ||
				 element.name == "labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue")
			{
				var errorText = "Invalid Numeric Result Value";

				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

			}

				setAttributeClass(tdErrorCell,"error");
			}
			else
			{
				var errorText = "";
				errorText = makeErrorMsg(getCorrectAttribute(element,"errorCode",element.errorCode), labelList.concat(elementFieldLabel));

				if( tdErrorCell.innerText == ""){
				  setText(tdErrorCell,errorText);
				}
				else{
						setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

			}
				setAttributeClass(tdErrorCell,"error");
			}
			return false;
		} else {
			if(tdErrorCell.className!="error"){
				setAttributeClass(tdErrorCell,"none");
			}
			return true;
		}
	} //reg-expr

        if (elementValidate == "JurisdictionCheckForObs")	{
			setAttributeClass(tdErrorCell,"none");

			if (isblank(element.value)) {
				var errorText = "";
				       if(getCorrectAttribute(element, "errorCode", element.errorCode))
				       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
				       else
				           errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
				if( tdErrorCell.innerText == "" ){
				  setText(tdErrorCell,errorText);
				}
				else{
				  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				  
				}
				setAttributeClass(tdErrorCell,"error");
				
					return false;
			}
			else {

			var ContextAction= getElementByIdOrByName("ContextAction");
			var NBSSecJurisdictionParseString = getElementByIdOrByName("NBSSecurityJurisdictions");
			var items = NBSSecJurisdictionParseString.value.split("|");
			var containsJurisdiction = false;
			var confirmMsg = makeErrorMsg('ERR052', labelList.concat(""));

	               	if (items.length > 1)	{
				for (var i=0; i < items.length; i++) {
					if (items[i]!=""  && items[i] == element.value )
					{

					  containsJurisdiction = true;
					}
				}
			}


			 if(containsJurisdiction)
			 {

			    if(ContextAction.value == "AddLabDataEntry" || ContextAction.value == "SubmitAndLoadLabDE1")
			    {


			        ContextAction.value = "SubmitAndLoadLabDE1";
			        return true;
			    }
			    else if( ContextAction.value == "AddMorbDataEntry" || ContextAction.value == "SubmitAndLoadMorbDE1")
			    {

			        ContextAction.value = "SubmitAndLoadMorbDE1";
			        return true;
			    }
			    else if( ContextAction.value == "SubmitAndCreateInvestigation" || ContextAction.value == "SubmitAndCreateInvestigationNoViewAccess")
			    {
				   ContextAction.value = "SubmitAndCreateInvestigation";
			        return true;
			    }
			    else
			    { // this is through file

			    ContextAction.value = "Submit";
			    }
			    return true;
			 }
			 else
			 {
			    if(ContextAction.value == "AddLabDataEntry" || ContextAction.value == "SubmitAndLoadLabDE1")
			    {

			        ContextAction.value = "SubmitAndLoadLabDE1";
			        var returnVariable = confirm(confirmMsg);
				showErrorStatement= returnVariable;
				return returnVariable;
			    }
			    else if( ContextAction.value == "AddMorbDataEntry" || ContextAction.value == "SubmitAndLoadMorbDE1")
			    {

			        ContextAction.value = "SubmitAndLoadMorbDE1";
			        var returnVariable = confirm(confirmMsg);
				showErrorStatement= returnVariable;
				return returnVariable;
			    }
			    else if(ContextAction.value == "SubmitAndCreateInvestigation")
			    {

				//ContextAction.value = "SubmitAndCreateInvestigationNoViewAccess";
				if(element.value == "NONE")
				{
					ContextAction.value = "SubmitNoViewAccess";
					var returnVariable = confirm(confirmMsg);
					showErrorStatement= returnVariable;
					return returnVariable;
				}

				var returnVariable = confirm(confirmMsg);
				showErrorStatement= returnVariable;
				return returnVariable;
			    }
			    else
			    {

 				if(element.value == "NONE")
				{
					ContextAction.value = "SubmitNoViewAccess";
					var returnVariable = confirm(confirmMsg);
					showErrorStatement= returnVariable;
					return returnVariable;
				}
				ContextAction.value = "SubmitNoViewAccess";
				var returnVariable = confirm(confirmMsg);
				showErrorStatement= returnVariable;
				return returnVariable;
			    }

			 }
			}
	} //JurisdictionCheck
	if (elementValidate == "JurisdictionCheck")	{
		setAttributeClass(tdErrorCell,"none");

		if (isblank(element.value)) {
			var errorText = "";
			       if(getCorrectAttribute(element, "errorCode", element.errorCode))
			       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       else
			           errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
			  setText(tdErrorCell,errorText);
			}
			else{
			  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			  
			}
			setAttributeClass(tdErrorCell,"error");
				return false;
		}
		else {

		var viewInvestigation = getElementByIdOrByName("viewInvestigation");

		var NBSSecJurisdictionParseString = getElementByIdOrByName("NBSSecurityJurisdictions");
		var items = NBSSecJurisdictionParseString.value.split("|");
		var containsJurisdiction = false;
		var confirmMsg = makeErrorMsg('ERR058', labelList.concat(""));

		if (items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				if (items[i]!=""  && items[i] == element.value )
				{
				  containsJurisdiction = true;
				}
			}
		}

		 if(containsJurisdiction)
		 {
		    var ContextAction= getElementByIdOrByName("ContextAction");
			ContextAction.value = "Submit";
			//alert(viewInvestigation.value);
			if(viewInvestigation != null && viewInvestigation.value == "false")
			ContextAction.value = "SubmitNoViewAccess";

			return true;
		 }
		 else
		 {
		    var ContextAction= getElementByIdOrByName("ContextAction");
			ContextAction.value = "SubmitNoViewAccess";
			var returnVariable = confirm(confirmMsg);
			showErrorStatement= returnVariable;
			return returnVariable;
		 }
		}
	} //JurisdictionCheck

	if (elementValidate == "numericRangeMIC") {
		var theValue = element.value;
		if (isblank(element.value) || ((theValue.indexOf(".") != -1) && (element.value<=999.999)) ||
		   (isNumeric(element.value) && (element.value>=0) && (element.value < 1000)) ) {
			return true;
		} else {

				//errorText = "Please enter a " + element.fieldLabel + " that is between 0.000 - 999.999.";
				errorText = makeErrorMsg('ERR084', labelList.concat(elementFieldLabel));

			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

			}
			setAttributeClass(tdErrorCell,"error");

			return false;
		}
	} //numericRangeMIC

	if (elementValidate == "numericErr128") {

			var theValue = element.value;
			if (isblank(element.value) || ((isNumeric(element.value) && (element.value>0) && (element.value < 1000))) ) {
			// if (isblank(element.value) || (isNumeric(element.value) && (element.value>0) && (element.value < 1000)) ) {
				return true;
			} else {
				//errorText = "Please enter a " + element.fieldLabel + " that is between 0.000 - 999.999.";
					errorText = makeErrorMsg('ERR128', labelList.concat(elementFieldLabel));

				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);

				}
				else{
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					
					
				}
				setAttributeClass(tdErrorCell,"error");

				return false;
			}
	} //numericErr128
	if (elementValidate == "numericErr123") {

			var theValue = element.value;
			if (isblank(element.value) || ((isNumeric(element.value) && (element.value>0) && (element.value < 100))) ) {
			// if (isblank(element.value) || (isNumeric(element.value) && (element.value>0) && (element.value < 100)) ) {
				return true;
			} else {
				//errorText = "Please enter a " + element.fieldLabel + " that is between 0.000 - 999.999.";
					errorText = makeErrorMsg('ERR123', labelList.concat(elementFieldLabel));

				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);
				}
				else{
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);

				}
				setAttributeClass(tdErrorCell,"error");

				return false;
			}
	} //numericErr123
	if (elementValidate == "vaccinationAge" )	{
				if ((isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null) ||
			   (isNumeric(element.value) && (element.value>0) && (element.value<=150)) &&  getCorrectAttribute(element, "requiredPartner",element.requiredPartner)==null)
			{
			  return true;
			}
			else
			{
				if (isblank(element.value) && getCorrectAttribute(element, "requiredPartner",element.requiredPartner)!=null)
				{
				    var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
				    if(partnerNode.value != null && partnerNode.value == "")
					return true;
				}

				var errorText;
				if( !isNumeric(element.value) || element.value>149 || element.value<1)
					errorText = makeErrorMsg('ERR009', labelList.concat(elementFieldLabel));
				else{
				    var partnerNode = getElementByIdOrByName(getCorrectAttribute(element, "requiredPartner",element.requiredPartner));
				    if(partnerNode.value != null && partnerNode.value == "")
					{
					  errorText = makeErrorMsg('ERR002', labelList.concat(elementFieldLabel));
					}
				    else
				      return true;
				  }

				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);
				}
				else{
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
	} //age
        if (elementValidate == "validateDOBYear" ) {
                      	
			thisYear = new Date().getFullYear();
			if(isblank(element.value))
				return true;
			else if(!isNumeric(element.value))
			{
			   errorText = makeErrorMsg('ERR076', labelList.concat(elementFieldLabel));
			   if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			   }
			   else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			   }
				setAttributeClass(tdErrorCell,"error");
	 			return false;
			}
	
			else if (!isblank(element.value) && thisYear >= element.value && element.value >= 1875) {
				return true;
			}
			else {
					if( !elementFieldLabel )
					{
					       errorText = makeErrorMsg('ERR147', labelList);
					}
					else
					{
					     errorText = makeErrorMsg('ERR147', labelList.concat(elementFieldLabel));
						
					}
					if( tdErrorCell.innerText == "" ){
						setText(tdErrorCell,errorText);
					}
					else{
						setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
						
						
					}
						
					setAttributeClass(tdErrorCell,"error");
					return false;
			}
		} //validateDOBYear
		
	if (elementValidate == "validateDOBMonth" ) {

		if(isblank(element.value))
			return true;
		else if(!isNumeric(element.value))
		{
		   errorText = makeErrorMsg('ERR155', labelList.concat(elementFieldLabel));
		   if( tdErrorCell.innerText == "" ){
			setText(tdErrorCell,errorText);
		   }
		   else{
			setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
		    
		   }
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
		else if (!isblank(element.value) && 12 >= element.value && element.value >= 1) {
			return true;
		}
		else {
			var errorText = "";
			if( !elementFieldLabel )
				errorText = makeErrorMsg('ERR155', labelList);
			else
			{
				errorText = makeErrorMsg('ERR155', labelList.concat(elementFieldLabel));
			}
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
				
			}
			else{
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
				
				
			}
			setAttributeClass(tdErrorCell,"error");
			return false;
		    }
	} //validateDOBMonth

if (elementValidate == "validateDOBDay" ) {
                var month = getElementByIdOrByName("personSearch.birthTimeMonth").value;
                var year = getElementByIdOrByName("personSearch.birthTimeYear").value;
                var days = "";
                if(month!=null && month!="" && year!=null && year!="")
                {
                    days = getDaysWithMonthAndYear(stripOffZero(month),year);
                }
                else if(month!=null && month!="")
                 days = getDaysWithMonth(stripOffZero(month));
                else
                 days = "31";
                if(isblank(element.value))
			return true;
		else if(!isNumeric(element.value) || element.value > 31 || element.value < 1 )
		{
			errorText = makeErrorMsg('ERR156', labelList.concat(elementFieldLabel));
			if( tdErrorCell.innerText == "" ){
				setText(tdErrorCell,errorText);
			}
		   else{
			setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			
		   }
			setAttributeClass(tdErrorCell,"error");
			return false;
		}
		else if (!isblank(element.value) && days >= element.value && element.value >= 1) {
			return true;
		}
		else {
				var errorText = "";
				if( !elementFieldLabel )
					errorText = makeErrorMsg('ERR157', labelList);
				else
				{
					errorText = makeErrorMsg('ERR157', labelList.concat(elementFieldLabel));
				}
				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);
					
				}
				else{
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					
					
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
	} //validateDOBDay
	
	// validate alpha only 
	if (elementValidate == "alpha") {
	
			if (isblank(element.value))
			{
				return true;
			}
	
			else
			{
				// no numbers but everything else
				var pattern = /\d/g;
				if(pattern.exec(element.value) != null)
				{
	
	
					if( !elementFieldLabel )
						errorText = makeErrorMsg('ERR159', labelList);
	
					else
					{
						//errorText = "Please enter " + element.fieldLabel + " using this format: yyyy.";
						errorText = makeErrorMsg('ERR159', labelList.concat(elementFieldLabel));
					}
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
			}
	
	} //alpha only
	
	if (elementValidate == "requiredNumber" ) {
			if (isblank(element.value)) {
	
	
				if( !elementFieldLabel ) {
				        
				       if(getCorrectAttribute(element, "errorCode", element.errorCode))
				       		errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       		else
						errorText = makeErrorMsg('ERR001', labelList);
				}
				else {
				       if(getCorrectAttribute(element, "errorCode", element.errorCode))
					       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
			       		else			
						errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
				}
					
				if( tdErrorCell.innerText == "" ){
					setText(tdErrorCell,errorText);
				}
				else {
					setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
					
					
				}
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
			if(isblank(element.value))
				return true;
			else if(isNumeric(element.value)) {
				if(element.value < 0) {
					errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
					setText(tdErrorCell,errorText);
					setAttributeClass(tdErrorCell,"error");
				return false;		
				} else {
					return true;
				}
			} else {
				errorText = makeErrorMsg('ERR008', labelList.concat(elementFieldLabel));
				setText(tdErrorCell,errorText);
				setAttributeClass(tdErrorCell,"error");
				return false;
			}
			
			
		} //requiredNumber
					if (elementValidate == "requiredDate" ) {
						if (isblank(element.value)) {	
							if( !elementFieldLabel ) {
							        
							       if(getCorrectAttribute(element, "errorCode", element.errorCode))
							       		errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
						       		else
									errorText = makeErrorMsg('ERR001', labelList);
							}
							else {
							       if(getCorrectAttribute(element, "errorCode", element.errorCode))
								       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
						       		else			
									errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
							}
								
							if( tdErrorCell.innerText == "" ){
								setText(tdErrorCell,errorText);
							}
							else {
								setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
								
							}
							setAttributeClass(tdErrorCell,"error");
							return false;
						}
						if (!isDate( element.value ) ) {
							if( !elementFieldLabel ){
								errorText = makeErrorMsg('ERR003', labelList);
								}
							else
							{
								//errorText = "Please enter " + element.fieldLabel + " using this format: 'mm/dd/yyyy'.";
								errorText = makeErrorMsg('ERR003', labelList.concat(elementFieldLabel));
							 }
							if( tdErrorCell.innerText == "" ){
						  		setText(tdErrorCell,errorText);
								
							}
							else{
								setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
								
								
							}
							setAttributeClass(tdErrorCell,"error");
							return false;
							// if is in mm/dd/yyyy format, check if it in a right range.
						}else if ((CompareDateStrings(element.value, "12/31/1875") == -1) ||
						    (CompareDateStringToToday(element.value) == 1))  {
		
		
						errorText = makeErrorMsg('ERR004', labelList.concat(elementFieldLabel));
		
						if( tdErrorCell.innerText == "" ){
		
						  setText(tdErrorCell,errorText);
						  
						}
						else{
						  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
						  
						  
						}
						setAttributeClass(tdErrorCell,"error");
		
						return false;
					}  
						else {
							return true;
						}
				

			
		} //requiredDate
	if (elementValidate == "requiredAlpha") {
		
				if (isblank(element.value)) {	
								if( !elementFieldLabel ) {
								        
								       if(getCorrectAttribute(element, "errorCode", element.errorCode))
								       		errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
							       		else
										errorText = makeErrorMsg('ERR001', labelList);
								}
								else {
								       if(getCorrectAttribute(element, "errorCode", element.errorCode))
									       	errorText = makeErrorMsg(getCorrectAttribute(element, "errorCode", element.errorCode), labelList.concat(elementFieldLabel));
							       		else			
										errorText = makeErrorMsg('ERR001', labelList.concat(elementFieldLabel));
								}
									
								if( tdErrorCell.innerText == "" ){
									setText(tdErrorCell,errorText);
									
								}
								else {
									setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
									
									
								}
								setAttributeClass(tdErrorCell,"error");
								return false;
				}
		
				else
				{
					// no numbers but everything else
					var pattern = /\d/g;
					if(pattern.exec(element.value) != null)
					{
		
		
						if( !elementFieldLabel )
							errorText = makeErrorMsg('ERR159', labelList);
		
						else
						{
							//errorText = "Please enter " + element.fieldLabel + " using this format: yyyy.";
							errorText = makeErrorMsg('ERR159', labelList.concat(elementFieldLabel));
						}
						if( tdErrorCell.innerText == "" ){
							setText(tdErrorCell, errorText);
						}
						else{
						  setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
		
						  
						}
						setAttributeClass(tdErrorCell,"error");
		
						return false;
		
		
					} else {
						return true;
					}
				}
		
		} //alpha only
		
		if(element.userDefinedJS == "true"){
			if(elementValidate != null){

				if(elementValidate != ""){

					if(elementValidate != "lengthLimit"){
						if (!isblank(element.value)){
						//alert(elementValidate);
						eval(elementValidate + "()");
					}
						
					}

				}		
			}

		}
	
	
} //validate



    function conditionInList(stdConditions, condToCheck)
    {
    	if (stdConditions == null || condToCheck == null)
    		return false;
   	var splitConditions = stdConditions.split('|');
   	for(var i=0; i<splitConditions.length; i++) {
		 if (splitConditions[i] == condToCheck)
		         	return true;
    	}
	return false; //not in list
    }

