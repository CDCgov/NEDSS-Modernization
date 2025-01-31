
//Year of most recent treatment
function HEP198()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";
        var HEP198node = getElementByIdOrByName("supplemental_s[31].obsValueNumericDT_s[0].numericValue1_s");
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP198node );

        var now=new Date();
        var currentYear = now.getFullYear();

        var matchYear = /^\d{4}$/;


        if( (HEP198node.value!="") && (HEP198node.value.match(matchYear) == null) )
        {
		errorText = makeErrorMsg('ERR076', labelList.concat(HEP198node.fieldLabel));
		tdErrorCell.innerText = errorText;

		tdErrorCell.className = "error";

        // no match, it is not in yyyy format.

        return true;
        }
        // no date of birth
        if(dobNode.value=="")
        {

            if ((HEP198node.value!="") && (window.isNaN(HEP198node.value)==false) && ((currentYear<HEP198node.value) || (HEP198node.value<1875)))
            {

                    //  check if there already is an error msg there
                    //errorText = "Please enter Year of most recent treatment between 1875 and current year.";
                    errorText = makeErrorMsg('ERR147', labelList.concat(HEP198node.fieldLabel).concat('1875'));
                    tdErrorCell.innerText = errorText;

                    tdErrorCell.className = "error";
                    return true;
            }
        }
        //with date of birth
        else
        {
            var birthYear = dobNode.value.substring(6,10);
            if (HEP198node.value!="" && window.isNaN(HEP198node.value)==false && (currentYear<HEP198node.value || HEP198node.value<birthYear))
            {

                errorText = makeErrorMsg('ERR082', labelList.concat(HEP198node.fieldLabel).concat(birthYear));
                tdErrorCell.innerText = errorText;

                tdErrorCell.className = "error";
                return true;
            }
        }
    return false;
}//Year of most recent treatment


//Date of transfusion
function HEP214()
{
        var HEP214node = getElementByIdOrByName("supplemental_s[6].obsValueDateDT_s[0].fromTime_s");
        var HEP102node = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveFromTime_s");
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP214node );
        var HEP214nodeYear =  HEP214node.value .substring(6, 10);
        var twoWeekBefore = DaySubtract(new Date(HEP102node.value),14);
        var sixMonthsBefore = MonthSubtract(new Date(HEP102node.value),6);
	var labelList = new Array();

        var errorText = "";
        var errorMessage = "";

        if (isblank(HEP214node.value))
	{
	    return false;

	}
	else
	{
	 	// First check if it is in mm/dd/yyyy format
		if (!(isDate( HEP214node.value )) &&(HEP214node.fieldLabel))
		{

	        	errorText = makeErrorMsg('ERR003', labelList.concat(HEP214node.fieldLabel));

	        	tdErrorCell.innerText = errorText;
	        	tdErrorCell.className = "error";

	        	return true;
		}//if
        }//else
        // first validate against Symptom Onset Date if patient has Symptom Onset Date
        if(!(HEP102node.value==""))
        {
   //   ----------------

	       // First check if it is in mm/dd/yyyy format for Symptom Onset Date
	       if (!isDate( HEP102node.value ) &&(HEP102node.fieldLabel))
	       {

	       		//errorText = makeErrorMsg('ERR003', labelList.concat(HEP102node.fieldLabel));
	       		tdErrorCell.innerText = errorText;
	                tdErrorCell.className = "error";

	                return true;
	       }//if


	       // no date of birth for Symptom Onset Date
	       if(dobNode.value=="")
	       {

	               if ((CompareDateStrings(HEP102node.value, "12/31/1875") == -1) ||
	                   (CompareDateStringToToday(HEP102node.value) == 1))
	           	{

	              	   //var errorText = makeErrorMsg('ERR004', labelList.concat(HEP102node.fieldLabel));
	                   tdErrorCell.innerText = errorText;

	                   tdErrorCell.className = "error";
	                   return true;
	           	}
	           	if ((CompareDateStrings(HEP214node.value, '12/31/1875') == -1) ||
			    (CompareDateStringToToday(HEP214node.value) == 1))
			    {
			        errorText = makeErrorMsg('ERR004', labelList.concat(HEP214node.fieldLabel));
			        tdErrorCell.innerText = errorText;
			        tdErrorCell.className = "error";
			        return true;
			    }
	           	if((CompareDateStrings(HEP214node.value,HEP102node.value) == -1) ||
	           	(CompareDateStringToToday(HEP102node.value) == 1)||
	           	(CompareDateStrings(HEP214node.value,HEP102node.value) == 0) ||
	           	(CompareDateStringToToday(HEP214node.value) == 0))
	           	{

	           	   errorText = makeErrorMsg('ERR092', labelList.concat(HEP214node.fieldLabel).concat(HEP102node.fieldLabel));
			   tdErrorCell.innerText = errorText;
			   tdErrorCell.className = "error";
	                   return true;
	           	}
	       }
	       //with date of birth for Symptom Onset Date
	       else
	       {
	           if ((CompareDateStrings(HEP102node.value, dobNode.value) == -1) ||
	               (CompareDateStringToToday(HEP102node.value) == 1))
	           {

	           //    errorText = makeErrorMsg('ERR092', labelList.concat(HEP102node.fieldLabel).concat(dobNode.fieldLabel));
	               tdErrorCell.innerText = errorText;

	               tdErrorCell.className = "error";
	               return true;
	           }
	       }


  //     -----------------
              if(!(dobNode.value==""))
              {
              if( (( (twoWeekBefore!="")&&(CompareDateStrings(twoWeekBefore, HEP214node.value)==-1) ) ||
	        ( (sixMonthsBefore!="")&&(CompareDateStrings(HEP214node.value, sixMonthsBefore)==-1) )) && HEP214nodeYear >999 )
	      {

	                errorText = makeErrorMsg('ERR091', labelList.concat(HEP214node.fieldLabel).concat(HEP102node.fieldLabel));

	                tdErrorCell.innerText = errorText;

	                tdErrorCell.className = "error";
	                return true;
             }
             }

      	}//close if 'first validate against Symptom Onset Date if patient has Symptom Onset Date'
      	//second validate against DOB if patient has DOB
	if(!(dobNode.value==""))
	{
	     if ((CompareDateStrings(HEP214node.value, dobNode.value) == -1) ||
		 (CompareDateStringToToday(HEP214node.value) == 1))
			{
			      //check if there already is an error msg there
			      errorText = makeErrorMsg('ERR092', labelList.concat(HEP214node.fieldLabel).concat(dobNode.fieldLabel));

			      tdErrorCell.innerText = errorText;
			      tdErrorCell.className = "error";
			      return true;
			 }
	}//close if 'second validate against DOB if patient has DOB'

	//last validate against 1875 and current date if DOB and Symptom Onset Date are blank
	if((dobNode.value=="") || (HEP102node.value==""))
	{
	      if ((CompareDateStrings(HEP214node.value, '12/31/1875') == -1) ||
	       	 (CompareDateStringToToday(HEP214node.value) == 1))
	      {

	       	     errorText = makeErrorMsg('ERR004', labelList.concat(HEP214node.fieldLabel));

	       	     tdErrorCell.innerText = errorText;
		     tdErrorCell.className = "error";
	       	     return true;
	      }
	}

    return false;
}//Date of transfusion


//Year of most recent incarceration
function HEP224()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";

        var HEP224node = getElementByIdOrByName("supplemental_s[33].obsValueNumericDT_s[0].numericValue1_s");

        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP224node );

        var pattern = /\d{4}/;
        var now=new Date();
        var currentYear = now.getFullYear();

        if(( HEP224node.value!="" ) && (pattern.exec(HEP224node.value) == null))
        {
            //errorText = "Year of most recent incarceration must be a 4-digit numeric entry inthe format of yyyy.";
            errorText = makeErrorMsg('ERR076', labelList.concat(HEP224node.fieldLabel));
            tdErrorCell.innerText = errorText;
            tdErrorCell.className = "error";

            return true;

        }

        // no date of birth
        if(dobNode.value=="")
        {

            if ((HEP224node.value!="" && window.isNaN(HEP224node.value)==false && (currentYear<HEP224node.value || HEP224node.value<1875)))
            {
                    //  check if there already is an error msg there
                    //errorText = "Year of most recent incarceration must be equal to or greater than " + 1875 + " and less than or equal to current year.  Please correct the data and try again.";
                    errorText = makeErrorMsg('ERR147', labelList.concat(HEP224node.fieldLabel).concat('1875'));
                    tdErrorCell.innerText = errorText;

                    tdErrorCell.className = "error";
                    return true;
            }
        }
        //with date of birth
        else
        {
            var birthYear = dobNode.value.substring(6,10);
            if ((HEP224node.value!="" && window.isNaN(HEP224node.value)==false && (currentYear<HEP224node.value || HEP224node.value<birthYear)))
            {
                //  check if there already is an error msg there
                //errorText = "Year of most recent incarceration must be equal to or greater than " + birthYear + " and less than or equal to current year.  Please correct the data and try again.";
                errorText = makeErrorMsg('ERR082', labelList.concat(HEP224node.fieldLabel).concat(birthYear));
                tdErrorCell.innerText = errorText;
                tdErrorCell.className = "error";
                return true;
            }
        }
           return false;
}//Year of most recent incarceration




/* Length of most recent incarceration */
function HEP225()
{
    //  Get this field.
    var eHEP225 = getElementByIdOrByName("supplemental_s[34].obsValueNumericDT_s[0].numericValue1_s");
    //  Get value.
    var varHEP225 = eHEP225.value;
    //  Get the units field.
    var eHEP226 = getElementByIdOrByName("supplemental_s[35].obsValueCodedDT_s[0].code");
    //  Get value.
    var varHEP226i = eHEP226.selectedIndex;
    var varHEP226 = "";
    if(varHEP226i != -1)
     varHEP226 = eHEP226.options[varHEP226i].value;

    //  Create an array to hold error message parameters.
    var a = new Array();
    //  Get the place to put error message.
    var varTD = getTdErrorCell(eHEP225);
    //  Get HEP223 element.
    var eHEP223 = getElementByIdOrByName("supplemental_s[32].obsValueCodedDT_s[0].code");
    if(eHEP223 == null)
    {
        return true;
    }
    //  Get value of HEP223.
    var varHEP223i = eHEP223.selectedIndex;
    var varHEP223 = "";
    if(varHEP223i != -1)
    varHEP223 = eHEP223.options[varHEP223i].value;
    //  If it's not "Y", it's OK.
    if(varHEP223 != "Y")
    {
        return false;
    }
    //  Years
    if(varHEP226 == "Y")
    {
        if(isEmpty(varHEP225))
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR087', a);
            varTD.className = "error";
            return true;
        }
        if(!isNumeric(varHEP225))
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR008', a);
            varTD.className = "error";
            return true;
        }
        return false;
    }
    //  Months
    if(varHEP226 == "M")
    {
        if(isEmpty(varHEP225))
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR087', a);
            varTD.className = "error";
            return true;
        }
        if(!isNumeric(varHEP225))
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR008', a);
            varTD.className = "error";
            return true;
        }
        if(varHEP225 <= 6)
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR087', a);
            varTD.className = "error";
            return true;
        }
        return false;
    }
    //  Missing fields.
    var var225 = isEmpty(varHEP225);
    var var226 = isEmpty(varHEP226);
    //  Have units, but no number.
    if( (var225 == true) && (var226 == false) )
    {
        a[0] = eHEP225.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR087', a);
        varTD.className = "error";
        return true;
    }
    //  Have number, but no units.
    if( (var225 == false) && (var226 == true) )
    {
        if(!isNumeric(varHEP225))
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR008', a);
            varTD.className = "error";
            return true;
        }
        a[0] = eHEP225.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR002', a);
        varTD.className = "error";
        return true;
    }
    //  Have both.
    if( (var225 == false) && (var226 == false) )
    {
        if(!isNumeric(varHEP225))
        {
            a[0] = eHEP225.getAttribute("fieldLabel");
            varTD.innerText = makeErrorMsg('ERR008', a);
            varTD.className = "error";
            return true;
        }
        a[0] = eHEP225.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR087', a);
        varTD.className = "error";
        return true;
    }
    //  Have neither.
    return false;
}/* Length of most recent incarceration. */

validationArray[13] = HEP224;
validationArray[14] = HEP225;
validationArray[15] = HEP214;
validationArray[16] = HEP198;


/*
tabValidationArray[3] = HEP223;
//tabValidationArray[4] = HEP198;
//tabValidationArray[5] = HEP213;
//tabValidationArray[4] = HEP225;
*/
