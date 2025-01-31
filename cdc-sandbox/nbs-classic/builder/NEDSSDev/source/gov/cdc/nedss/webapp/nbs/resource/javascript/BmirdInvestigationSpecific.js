function BMD136()
{
    var a = new Array();
    var eBMD136 = getElementByIdOrByName("supplemental_s[0].obsValueNumericDT_s[0].numericValue1_s");
    var varBMD136 = eBMD136.value;
    if(isEmpty(varBMD136))
    {
        return false;
    }
    if(!isNumeric(varBMD136))
    {
        a[0] = eBMD136.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR123', a);
        varTD.className = "error";
        return true;
    }
    return false;
}

function BMD178()
{
    var a = new Array();
    var eBMD178 = getElementByIdOrByName("supplemental_s[7].obsValueNumericDT_s[0].numericValue1");
    var varBMD178 = eBMD178.value;
    if(isEmpty(varBMD178))
    {
        return false;
    }
    if(!isNumeric(varBMD178))
    {
        a[0] = eBMD178.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR008', a);
        varTD.className = "error";
        return true;
    }
    if( (varBMD178 < 1) || (varBMD178 >= 37) )
    {
        a[0] = eBMD178.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR080', a);
        varTD.className = "error";
        return true;
    }
    return false;
}

//Moved this function to elementValidate.js

/*function BMD241()
{
    var a = new Array();
    var eBMD241 = getElementByIdOrByName("supplemental_s[26].obsValueDateDT_s[0].fromTime_s");
    var varBMD241 = eBMD241.value;
    if(isEmpty(varBMD241))
    {
        return false;
    }
    if(!isDate(varBMD241))
    {
        a[0] = eBMD241.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR003', a);
        varTD.className = "error";
        return true;
    }
    var eDOB = getElementByIdOrByName("DEM115");
    var varDOB = eDOB.value;
    if(CompareDateStrings(varBMD241, "12/31/1875") <= 0)
    {
        a[0] = eBMD241.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR004', a);
        varTD.className = "error";
        return true;
    }
    if(CompareDateStringToToday(varBMD241) >= 0)
    {
        a[0] = eBMD241.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR004', a);
        varTD.className = "error";
        return true;
    }
    if(CompareDateStrings(varBMD241, varDOB) >= 0)
    {
        a[0] = eBMD241.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR006', a);
        varTD.className = "error";
        return true;
    }
    return false;
}
*/
//Moved this function to elementValidate.js
/*function BMD264()
{
    var a = new Array();
    var eBMD264 = getElementByIdOrByName("antibioticBatchEntry_s[i].observationVO_s[2].obsValueNumericDT_s[0].numericValue1_s");
    var varBMD264 = eBMD264.value;
    if(isEmpty(varBMD264))
    {
        return false;
    }
    if(!isNumeric(varBMD264))
    {
        a[0] = eBMD264.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR008', a);
        varTD.className = "error";
        return true;
    }
    if(varBMD264 == "999")
    {
        return false;
    }
    if( (varBMD264 < 0) || (varBMD264 > 300) )
    {
        a[0] = eBMD264.getAttribute("fieldLabel");
        varTD.innerText = makeErrorMsg('ERR094', a);
        varTD.className = "error";
        return true;
    }
    return false;
}*/

function caseStatusConfirm()
{
    var caseStatus = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd");
    var caseClassCdhidden= getElementByIdOrByName("caseClassCd");
    var abcCheck= getElementByIdOrByName("proxy.observationVO_s[0].obsValueCodedDT_s[0].code");

    if(abcCheck.value == "T")
    {
        caseStatus.value ="C";
        caseStatus.disabled=true;
        if(caseClassCdhidden != null)
        caseClassCdhidden.value="C";
    }
    return;
}

function DateSubtract(startDate, numDays, numMonths, numYears)
{
    var returnDate = new Date(startDate.getTime());
    var yearsToSubtract = numYears;
    var month = returnDate.getMonth() - numMonths;
    if(month < 0)
    {
        yearsToSubtract = Math.floor((month + 12) / 12);
        month += 12 * yearsToSubtract;
        yearsToSubtract -= numYears;
    }
    returnDate.setMonth(month);
    returnDate.setFullYear(returnDate.getFullYear() - yearsToSubtract);
    returnDate.setTime(returnDate.getTime() - 60000 * 60 * 24 * numDays);
    return returnDate;
}

function DaySubtract(startDate, numDays)
{
    return DateSubtract(startDate, numDays, 0, 0);
}

function MonthSubtract(startDate, numMonths)
{
    return DateSubtract(startDate, 0, numMonths, 0);
}

function PathogenConfirmation(pSelectNode, pFormAction)
{
    //save subform data entered
    callSaveData();
    
    var option = pSelectNode.options[pSelectNode.selectedIndex];
    confirmMsg = "Are you sure you want to select " + option.text +"?";
  
    if(confirm(confirmMsg))
    {
        //this is commented as per defect no: 8772
        //getElementByIdOrByName("proxy.observationVO_s[1].obsValueCodedDT_s[0].code").value = "";
    	//need to get the DT for resolving the defect 17128
    	
        document.forms['nedssForm'].action = pFormAction;
        BatchEntryCreateHiddenInputs();
        document.forms['nedssForm'].submit();
    }
    else
    {
        var condtionCd = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd");
        pSelectNode.value = condtionCd.value;
    }
    return;
}

function sixdaysDOB(DOB)
{
    var now = new Date();
    if(DOB != "")
    {
        var dob = new Date(DOB.value);
    }
    var sixday = getElementByIdOrByName("proxy.observationVO_s[20].obsValueCodedDT_s[0].code");
    var fifteenYears = getElementByIdOrByName("supplemental_s[0].obsValueCodedDT_s[0].code");
    if(isDate(DOB.value))
    {
        var dobyear = dob.getFullYear();
        var dobmonth = dob.getMonth();
        var dobday = dob.getDate();
        var nowyear = now .getFullYear();
        var nowmonth = now.getMonth();
        var nowday = now.getDate();
        var ydiff = nowyear - dobyear;
        var mdiff = nowmonth - dobmonth;
        var ddiff = nowday - dobday;
        if( (ddiff < 6) && (mdiff == 0) && (ydiff == 0) )
        {
            sixday.value = "Y";
            sixday.onchange();
        }
        else
        {
            sixday.value = "N";
            sixday.onchange();
        }
        if(ddiff < 31)
        {
            sixday.value = "Y";
            sixday.onchange();
        }
        else
        {
            sixday.value = "N";
            sixday.onchange();
        }
        if( (ddiff == 0) && (mdiff == 0) && (ydiff < 15) )
        {
            fifteenYears.value = "b";
            sixday.onchange();
        }
        else
        {
            fifteenYears.value = "a";
            sixday.onchange();
        }
    }
    else
    {
        sixday.value = "";
        sixday.onchange();
    }
}

function SwitchABCs(oCheckbox)
{
    var show = oCheckbox.checked;
    var caseStatusNode = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd");
    var caseClassCd = getElementByIdOrByName("caseClassCd");
    if(show)
    {
        caseStatusNode.value = "C";
        caseClassCd.value ="C";
        caseStatusNode.disabled = true;
    }
    else
    {

        caseStatusNode.value = "";
        caseStatusNode.disabled = false;
    }
    return;
}

function Trigger(line, triggerCd)
{
    line.trigger.value = triggerCd.value;
}

function BMIRDValidateDate()
{
   var conditionCd = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd");

   //for BMIRD_GroupB and BMIRD_SP
  // as per enhancement requst 10431 always do not display GroupB strep
   if(conditionCd != null && conditionCd.value == "10150")
   {

     if(hasData() == false)
     {
        return  validateDate();
      }
    else
      return true;
    }

    //for BMIRD HI
   // as per enhancement request 10432 no age calculation for HI
    /**if(conditionCd != null && (conditionCd.value == "11715" || conditionCd.value == "10590"))
    {
       return fifteenYearsDOB();
    }**/

}

function validateDate()
{
    var currentDate;
    var DOBvar = "";
    var serverDate = getElementByIdOrByName("today");
    var DOB = getElementByIdOrByName("DEM115");
    //var AOD = getElementByIdOrByName("DEM215");
    //if(AOD == null || AOD.value == "")
    //{
    //AOD =  getElementByIdOrByName("DEM207");
    //currentDate = new Date(AOD.value);
    //}
    //else
    //{
      //currentDate = new Date(AOD.value);
    //}

    currentDate = new Date(serverDate.value);



    var flag = getElementByIdOrByName("proxy.observationVO_s[20].obsValueCodedDT_s[0].code");
    var sixDaysBefore = "";
    var oneMonthBefore = "";
    var zeroToSixDays = "";
    var fifteenDays = "";
    var fifteenYearsBefore = "";
    zeroToSixDays = getElementByIdOrByName("Early-Onset Group B Strep (0-6 days)");

    if(DOB != null)
    DOBvar = DOB.value;

   // if(DOBvar == null || DOBvar =="" || DOBvar == "undefined")
    //{
	//  var age = getElementByIdOrByName("DEM216");
	  //var ageUnit = getElementByIdOrByName("DEM218");
		//if(age != null && age.value != ""  && ageUnit != null && ageUnit.value != ""  && AOD != null && AOD.value != "")
		//{
            //   DOBvar  = calculateDOB(AOD, ageUnit , age);
		//}
   // }

    if(DOBvar  == null || DOBvar  =="")
    {
        if(zeroToSixDays != null)
        {
            zeroToSixDays.className = "none";
        }
        return false;
    }
    if( (CompareDateStrings(DOBvar, "12/31/1875") == -1) || (CompareDateStringToToday(DOBvar ) == 1) )
    {
        flag.value = "";
        flag.onchange();
        if(zeroToSixDays != null)
        zeroToSixDays.className = "none";
    }
    else
    {
        sixDaysBefore = DaySubtract(currentDate, 7);
        oneMonthBefore = MonthSubtract(currentDate, 1);
        fifteenYearsBefore = YearSubtract(currentDate, 15);
        if(zeroToSixDays != null)
        {
            zeroToSixDays.className = "none";
        }
        if(isDate(DOBvar))
        {
            var formDate = new Date(DOBvar);
            if( (oneMonthBefore != "") && (CompareDateStrings(oneMonthBefore, formDate) == -1) )
            {
                if(flag!=null)
                {
                    //flag.value = "Y";
                    flag.onchange();
                    zeroToSixDays.className = "none";
                    /**if( (sixDaysBefore != "") && (CompareDateStrings(sixDaysBefore, formDate) == -1) )
                    {
                        if(zeroToSixDays != null)
                        {
                            zeroToSixDays.className = "visible";
                        }
                    }**/
                }
            }
        }
        else
        {
            if(flag != null)
            {
                flag.value = "N";
                flag.onchange();
            }
            else
            {
                flag.onchange();
                return true;
            }
        }
    }
    if(DOB && (DOBvar   == ""))
    {
        return false;
    }

}


function fifteenYearsDOB()
{
   var hiFlag = getElementByIdOrByName("supplemental_s[0].obsValueCodedDT_s[0].code");
   if(hiFlag != null )
   {
	    var currentDate;
	    var dobDate;
	    var fifteenYearsBefore;

	    var serverDate = getElementByIdOrByName("today");
	    var DOB = getElementByIdOrByName("DEM115");

	    if(serverDate != null)
	    currentDate = new Date(serverDate.value);

	    if(DOB != null)
	    dobDate =  new Date(DOB.value);
	    else
	     return false;

        fifteenYearsBefore = YearSubtract(currentDate, 15);

	    if( (fifteenYearsBefore != "") && (CompareDateStrings(fifteenYearsBefore, dobDate) == -1) )
	    {
	        hiFlag.onchange();
	        return false;
	    }
	    else
	    {
	       hiFlag.onchange();
	       return true;
	    }
    }
    else
      return false;
}

function HIValidateDate()
{
   var hiFlag = getElementByIdOrByName("supplemental_s[0].obsValueCodedDT_s[0].code");
   if(hiFlag != null )
   {
	    var currentDate;
	    var dobDate;
	    var fifteenYearsBefore;

	    var serverDate = getElementByIdOrByName("today");
	    var DOB = getElementByIdOrByName("DEM115");

	    if(serverDate != null)
	    currentDate = new Date(serverDate.value);

	    if(DOB != null)
	    dobDate =  new Date(DOB.value);
	    else
	     return false;

         fifteenYearsBefore = YearSubtract(currentDate, 15);
	    if( (fifteenYearsBefore != "") && (CompareDateStrings(fifteenYearsBefore, dobDate) == -1) )
	    {
	        return false;
	    }
	    else
	    {
	       return true;
	    }
    }
    else
      return false;
}

function BMD131Check()
{
  // var YN is "Was the patient < 15 years of age at the time of first positive culture?" BMD300
  var YN = getElementByIdOrByName("supplemental_s[16].obsValueCodedDT_s[0].code");
  var temp = getElementByIdOrByName("tempBMD131");
  var seroType = getElementByIdOrByName("supplemental_s[0].obsValueCodedDT_s[0].code");
  if((seroType.value == "b" || seroType.value == "UNK") && (temp.value == "b" || temp.value == "UNK"))
    temp.value = seroType.value;
  else
    YN.onchange();
    temp.value = seroType.value;
}


function BMD131ViewLoad()
{
  var seroType = getElementByIdOrByName("supplemental_s[0].obsValueCodedDT_s[0].code");
  // var YN is "Was the patient < 15 years of age at the time of first positive culture?" BMD300
  var YN = getElementByIdOrByName("supplemental_s[16].obsValueCodedDT_s[0].code");
  var other = getElementByIdOrByName("nestedElementsControllerPayloadBMD131c");
  var payLoad = getElementByIdOrByName("nestedElementsControllerPayloadBMD300c");
  if((seroType.value == "b" || seroType.value == "UNK") && YN.value == "Y")
  {
    other.setAttribute("className", "none");
    payLoad.setAttribute("className", "visible");
  }
  else if(seroType.value == "OTH")
  {
    other.setAttribute("className", "visible");
    payLoad.setAttribute("className", "none");
  }
  else
  {
    payLoad.setAttribute("className", "none");
  }
}


function BMD300Check()
{
  var seroType = getElementByIdOrByName("supplemental_s[0].obsValueCodedDT_s[0].code");
  var YN = getElementByIdOrByName("supplemental_s[16].obsValueCodedDT_s[0].code");
  if(YN.value == "Y" && (seroType.value == "b" || seroType.value == "UNK"))
  {
    return false;
  }
  else
  {
   return true;
  }
}

function calculateDOB(asOfDateNode, reportedAgeUnitsNode, reportedAgeNode)
{
   var calcDOB;
   var asOfDate = new Date(asOfDateNode.value);
		if(reportedAgeUnitsNode.value=="Y")
		{
			// convert years into milliseconds
			//alert("asOfDate.getTime() = "+ asOfDate.getTime() +" reportedAgeNode.value*31104000000 =  " + reportedAgeNode.value*31104000000);
			var newYear = asOfDate.getFullYear() - reportedAgeNode.value;

			calcDOB = DateToString(new Date(asOfDate.setFullYear(newYear)));
			//alert(calcDOB);

		}
		else if(reportedAgeUnitsNode.value=="M")
		{


			var newMonth = (asOfDate.getMonth()) - (reportedAgeNode.value % 12);
			if(newMonth<1)
			{
				newMonth = 12 - (Math.abs(newMonth)%12);

			}
			// figure out the year
			if((asOfDate.getMonth() - reportedAgeNode.value) < 0)
			{
				var newYear = asOfDate.getFullYear() - (Math.ceil(reportedAgeNode.value/12));
				asOfDate = new Date(asOfDate.setFullYear(newYear));
			}
			//var newDate = asOfDate.getTime() - (reportedAgeNode.value*2592000000);

			calcDOB = DateToString(new Date(asOfDate.setMonth(newMonth)));
		}
		else if(reportedAgeUnitsNode.value=="D")
		{
			var newDate = asOfDate.getTime() - (reportedAgeNode.value*86400000);

			calcDOB = DateToString(new Date(newDate));

		}
		else if(reportedAgeUnitsNode.value=="W")
		{
					var newDate = asOfDate.getTime() - (reportedAgeNode.value*604800000);

					calcDOB = DateToString(new Date(newDate));

		}
		else if(reportedAgeUnitsNode.value=="H")
		{
					var newDate = asOfDate.getTime() - (reportedAgeNode.value*3600000);

					calcDOB = DateToString(new Date(newDate));
		}
		return calcDOB;
}

function validateDateCollege()
{
    var DOB = getElementByIdOrByName("DEM115");
    var currentDate = new Date();
    var twentyfourYearBefore = "";
    var fifteenYearsBefore = "";
    var flag = getElementByIdOrByName("SDD");
    twentyfourYearBefore = YearSubtract(currentDate,24);
    fifteenYearsBefore = YearSubtract(currentDate, 15);
    if(isDate(DOB.value))
    {
        var formDate = new Date(DOB.value);
        if( (fifteenYearsBefore != "") && (CompareDateStrings(fifteenYearsBefore, formDate) == -1) )
        {

             flag.setAttribute("className", "none");
        }

        if( (twentyfourYearBefore != "") && (CompareDateStrings(twentyfourYearBefore, formDate) == 1) )
        {
             flag.setAttribute("className", "none");
        }
    }
}

function YearSubtract(startDate, numYears)
{
    return DateSubtract(startDate, 0, 0, numYears);
}


function hasData()
{
     var zeroToSixDays = getElementByIdOrByName("Early-Onset Group B Strep (0-6 days)");

     if(zeroToSixDays == null)
     return false;

	var inputNodes = zeroToSixDays.getElementsByTagName("input");
	var selectNodes = zeroToSixDays.getElementsByTagName("select");


	var hasValue = false;
	for (var i=0;i<inputNodes.length;i++)
	{
		if(inputNodes[i].type != "hidden" && inputNodes[i].type != "button" && inputNodes[i].value !=null && inputNodes[i].value != "")
		{
		  hasValue = true;
		}
	}
	for (var i=0;i<selectNodes.length;i++)
	{

		if(selectNodes[i].value !=null && selectNodes[i].value !="")
		{
		  hasValue = true;
		  //alert("hasData " +hasData  );
		}
	}

	if(hasValue && zeroToSixDays != null)
	{
	     //alert("has value if if condition : "+hasValue );
	     if(hasValue)
	        zeroToSixDays.className = "visible";
	     else
	        zeroToSixDays.className = "none";
	}

	return hasValue;
}

function BMD124(element)
{

	var labelList = new Array();
	var tdErrorCell = getTdErrorCell(element);
	var errorText = makeErrorMsg('ERR135', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));
	var notificationExists = getElementByIdOrByName("NotificationExists");

	if(isblank(getCorrectAttribute(element,"value",element.value)) && notificationExists.value=="true")
				{
		errorText = makeErrorMsg('ERR135', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));
		if( tdErrorCell.innerText == "" )
			tdErrorCell.innerText = errorText;
		else
			tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		tdErrorCell.className = "error";
		return false;
	}

	if(isblank(getCorrectAttribute(element,"value",element.value)))
	{
		return true;
	}


	//var labelList = new Array();

	if (isblank(getCorrectAttribute(element,"value",element.value)) && (element.required && element.required=="true"))
	{

		if( !getCorrectAttribute(element,"fieldLabel",element.fieldLabel) )
			errorText = makeErrorMsg('ERR001', labelList);
		else
			errorText = makeErrorMsg('ERR001', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));

		if( tdErrorCell.innerText == "" )
			tdErrorCell.innerText = errorText;
		else {
			tdErrorCell.innerText = errorText;
		}
		tdErrorCell.className = "error";

		return false;

	} else if (isblank(getCorrectAttribute(element,"value",element.value))){

		return true;

	} else {

		// First check if it is in mm/dd/yyyy format

		if (!isDate( getCorrectAttribute(element,"value",element.value) ) ) {

			if( !getCorrectAttribute(element,"fieldLabel",element.fieldLabel) )

				errorText = makeErrorMsg('ERR011', labelList);
			else {

				errorText = makeErrorMsg('ERR003', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));
				//errorText = makeErrorMsg('ERR003', labelList.concat(element.fieldLabel, 'replacement1','replacement2', 'replacement3', 'replacement4', 'replacement5'));
			}


			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.className = "error";

			return false;

		// if is in mm/dd/yyyy format, check if it in a right range.
		} else	if ((CompareDateStrings(getCorrectAttribute(element,"value",element.value), "12/31/1875") == -1) ||
			    (CompareDateStringToToday(getCorrectAttribute(element,"value",element.value)) == 1))  {


			errorText = makeErrorMsg('ERR004', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));

			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.className = "error";

			return false;
		} else {
			return true;
		}
	}
} //BMD124
function otherResonsFor142(node){
var Other142 = node.value;
 if(Other142 =='OTH'){
	  	getElementByIdOrByName("BMD318Q").className = "visible";
	  	//alert("visible");
	}
	else {
	 	getElementByIdOrByName("supplemental_s[9].obsValueTxtDT_s[0].valueTxt").value= "";
	 	getElementByIdOrByName("BMD318Q").className = "none";
  	}
}
function otherResonsFor144(node){
var Other144 = node.value;
 if(Other144 =='OTH'){
	  	getElementByIdOrByName("BMD319Q").className = "visible";
	  	//alert("visible");
	}
	else {
	 	getElementByIdOrByName("supplemental_s[10].obsValueTxtDT_s[0].valueTxt").value= "";
	 	getElementByIdOrByName("BMD319Q").className = "none";
  	}
}
 function updateUnknownWeight(){
  var unknownWeight = getElementByIdOrByName("proxy.observationVO_s[45].obsValueNumericDT_s[0].numericValue1_s");
  var unknownWeightCheckBox = getElementByIdOrByName("unknownWeightCheckBox");
  var unknownKg = getElementByIdOrByName("proxy.observationVO_s[42].obsValueNumericDT_s[0].numericValue1_s");
  var unknownLbs = getElementByIdOrByName("proxy.observationVO_s[43].obsValueNumericDT_s[0].numericValue1_s");
  var unknownOunce = getElementByIdOrByName("proxy.observationVO_s[44].obsValueNumericDT_s[0].numericValue1_s");
  if(unknownWeightCheckBox.checked)
  {
	  unknownWeight.value = -1;
  }
  else {
	  unknownWeight.value = "";
	  unknownKg.disabled = false;
  	  unknownLbs.disabled = false;
	  unknownOunce.disabled = false;
  	  return;
  }
  unknownKg.disabled = true;
  unknownLbs.disabled = true;
  unknownOunce.disabled = true;
  unknownKg.value = "";
  unknownLbs.value = "";
  unknownOunce.value = "";

 }


function updateUnknownHeight(){
  var unknownHeight = getElementByIdOrByName("proxy.observationVO_s[49].obsValueNumericDT_s[0].numericValue1_s");
  var unknownHeightCheckBox = getElementByIdOrByName("unknownHeightCheckBox");
  var unknownFt = getElementByIdOrByName("proxy.observationVO_s[46].obsValueNumericDT_s[0].numericValue1_s");
  var unknownInch = getElementByIdOrByName("proxy.observationVO_s[47].obsValueNumericDT_s[0].numericValue1_s");
  var unknownCm = getElementByIdOrByName("proxy.observationVO_s[48].obsValueNumericDT_s[0].numericValue1_s");
  if(unknownHeightCheckBox.checked)
  {
	  unknownHeight.value = -1;
  }
  else {
	  unknownHeight.value = "";
	  if (getElementByIdOrByName("unknownFt") != null)
	  	unknownFt.disabled = false;
	  if (getElementByIdOrByName("unknownInch") != null)
  	  	unknownInch.disabled = false;
  	  if (getElementByIdOrByName("unknownCm") != null)
	  	unknownCm.disabled = false;
  	  return;
  }
  if (getElementByIdOrByName("unknownFt") != null) {
  	unknownFt.disabled = true;
  	unknownFt.value = "";
  }
  if (getElementByIdOrByName("unknownInch") != null) {
  	unknownInch.disabled = true;
  	unknownInch.value = "";
  }
  if (getElementByIdOrByName("unknownCm") != null) {
  	unknownCm.disabled = true;
  	unknownCm.value = "";
  }

 }
 function BMD326And327(){
 var unknownHeightCheckBox = getElementByIdOrByName("unknownHeightCheckBox");
 var unknownHeight = getElementByIdOrByName("proxy.observationVO_s[49].obsValueNumericDT_s[0].numericValue1_s");
 var unknownFt = getElementByIdOrByName("proxy.observationVO_s[46].obsValueNumericDT_s[0].numericValue1_s");
 var unknownInch = getElementByIdOrByName("proxy.observationVO_s[47].obsValueNumericDT_s[0].numericValue1_s");
 var unknownCm = getElementByIdOrByName("proxy.observationVO_s[48].obsValueNumericDT_s[0].numericValue1_s");
 if(unknownHeightCheckBox.checked)
  {
	  unknownHeight.value = -1;
	  if (getElementByIdOrByName("unknownFt") != null) {
 	  	unknownFt.disabled = true;
   	  	unknownInch.disabled = true;
 	  	unknownCm.disabled = true;
 	  }
  } 
  else {
 	  unknownHeight.value = "";
 	  if (getElementByIdOrByName("unknownFt") != null) {
 	 	unknownFt.disabled = false;
   	  	unknownInch.disabled = false;
 	  	unknownCm.disabled = false;
 	  }
 var unknownWeightCheckBox = getElementByIdOrByName("unknownWeightCheckBox");
 var unknownKg = getElementByIdOrByName("proxy.observationVO_s[42].obsValueNumericDT_s[0].numericValue1_s");
 var unknownLbs = getElementByIdOrByName("proxy.observationVO_s[43].obsValueNumericDT_s[0].numericValue1_s");
 var unknownOunce = getElementByIdOrByName("proxy.observationVO_s[44].obsValueNumericDT_s[0].numericValue1_s");
 var unknownWeight = getElementByIdOrByName("proxy.observationVO_s[45].obsValueNumericDT_s[0].numericValue1_s");
 if(unknownWeightCheckBox.checked)
  {
	  unknownWeight.value = -1;
  	  unknownKg.disabled = true;
    	  unknownLbs.disabled = true;
    	  if (getElementByIdOrByName("unknownOunce") != null) 
  	  	unknownOunce.disabled = true;
  }
  else{
  	  unknownWeight.value = "";
  	  unknownKg.disabled = false;
    	  unknownLbs.disabled = false;
    	  if (getElementByIdOrByName("unknownOunce") != null) 
  	  	unknownOunce.disabled = false;
  }
 }
 }