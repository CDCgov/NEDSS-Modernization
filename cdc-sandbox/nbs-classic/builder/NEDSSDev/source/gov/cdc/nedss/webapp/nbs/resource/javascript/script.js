/**
 *  script.js.
 *  Global script library.
 *  @author Ed Jenkins
 *  @version 1.9
 */

/**
 *  Toggles the displaying of borders around tables.
 *  @param pOn true to turn borders on or false to turn them off.
 *  @return always returns true.
 */
function ToggleBorders(pOn)
{
    var varTag;
    var varTagName;
    var x;
    var y = document.all.length;
    //  Translate parameters.
    var z = (pOn == true) ? "1" : "0";
    //  Loop through all elements.
    for(x=0; x<y; x++)
    {
        //  Look for tables.
        varTag = document.all[x];
        varTagName = varTag.tagName;
        if(varTagName == "TABLE")
        {
            //  Turn borders on or off.
            varTag.border = z;
        }
    }
    //  Return.
    return true;
}

/**
 *  Displays an error message at the top of the page.
 *  @param pMSG the error message to display.
 *  @return always returns true.
 */
function ShowTopError(pMSG)
{
    //  Set the message text.
    error1.innerText = pMSG;
    //  Display it.
    error1.className = "error";
    //  Display a blank line after it.
    error2.className = "visible";
    //  Return.
    return true;
}

/**
 *  Hides the error message at the top of the page.
 *  @return always returns true.
 */
function HideTopError()
{
    //  Hide the error message.
    error1.className = "none";
    //  Hide the blank line.
    error2.className = "none";
    //  Reset the message text.
    error1.innerText = ".";
    //  Return.
    return true;
}

/**
 *  Displays an error message.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pMSG the error message to display.
 *  @return always returns true.
 */
function ShowError(pTD, pMSG)
{
    //  Display a generic error message at the top of the page.
    var varTopMSG =
        "One or more fields are in error.  " +
        "Please make the suggested changes and then try again.";
    ShowTopError(varTopMSG);
    //  See if an error message already exists.
    var varMSG = pTD.innerText;
    if(varMSG == ".")
    {
        //  If none exists, then clear the place holder.
        varMSG = "";
    }
    else
    {
        //  If one or more messages already exist, then
        //  insert a blank line to separate them from
        //  the next message.
        varMSG += "\n\n";
    }
    //  Append the new message.
    varMSG += pMSG;
    //  Set message text.
    pTD.innerText = varMSG;
    //  Display it.
    pTD.className = "error";
    //  Return.
    return true;
}

/**
 *  Hids an error message.
 *  @param pTD the table cell to be hidden.
 *  @return always returns true.
 */
function HideError(pTD)
{
    //  Hid the error message.
    pTD.className = "none";
    //  Reset content to just a placeholder.
    //  A special placeholder is needed so that
    //  ShowError() will know when to append a blank line
    //  and when not to.
    pTD.innerText = ".";
    //  Return.
    return true;
}

/**
 *  Determines whether something has been selected from a listbox.
 *  @param pLST the listbox to test.
 *  @return true if something has been selected or false if not.
 */
function isSelected(pLST)
{
    //  Get the value (or first value if multiselect).
    var varValue = pLST.value;
    if(varValue == "")
    {
        //  Nothing has been selected.
        return false;
    }
    //  Something has been selected.
    return true;
}

/**
 *  Checks to see if something has been selected from a listbox.
 *  Displays a custom error message if nothing has been selected.
 *  @param pLST the listbox to test.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pMSG the error message to display.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckSelectedCustom(pLST, pTD, pMSG)
{
    if(isSelected(pLST) == false)
    {
        //  Nothing has been selected.
        ShowError(pTD, pMSG);
        return 1;
    }
    //  It's OK.
    return 0;
}

/**
 *  Determines whether something has been selected from a listbox.
 *  @param pLST the listbox to test.
 *  @return true if something has been selected or false if not.
 */
function isOptionSelected(pLST, pString)
{
    if(pLST.selectedIndex < 0)
    {
        //  Nothing is selected.
        return false;
    }
    //  Something is selected.
    //  Find out what it is.
    var varOption = pLST.options[pLST.selectedIndex].text;
    //  See if it is what you want.
    if(varOption == pString)
    {
        //  It is what we want.
        return true;
    }
    //  It's not what we want.
    return false;
}

/**
 *  Checks to see if something has been selected from a listbox.
 *  Displays a generic error message if nothing has been selected.
 *  @param pLST the listbox to test.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pName the name of the field to check.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckSelectedGeneric(pLST, pTD, pName)
{
    //  Create a generic error message and include the field name.
    var varMSG =
        "You have not selected anything for the \"" + pName + "\" field.  " +
        "Please select something.";
    //  Check it out.
    return CheckSelectedCustom(pString, pTD, varMSG);
}

/**
 *  Determines whether a string value is empty.
 *  @param pString the string to test.
 *  @return true if the string is empty or false if not.
 */
function isEmpty(pString)
{
    if(pString == "")
    {
        //  The string is empty.
        return true;
    }
    //  The string is not empty.
    return false;
}

/**
 *  Checks to see if a string is empty.
 *  Displays a custom error message if the string is empty.
 *  @param pString the string to test.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pMSG the error message to display.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckEmptyCustom(pString, pTD, pMSG)
{
    if(isEmpty(pString) == true)
    {
        //  It's empty.
        ShowError(pTD, pMSG);
        return 1;
    }
    //  It's OK.
    return 0;
}

/**
 *  Checks to see if a string is empty.
 *  Displays a generic error message if the string is empty.
 *  @param pString the string to test.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pName the name of the field to check.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckEmptyGeneric(pString, pTD, pName)
{
    //  Create a generic error message and include the field name.
    var varMSG = "The \"" + pName + "\" field can not be blank.  Please enter something.";
    //  Check it out.
    return CheckEmptyCustom(pString, pTD, varMSG);
}

/**
 *  Determines whether a string value is longer than it should be.
 *  @param pString the string to test.
 *  @param pMax the maximum string length allowed.
 *  @return true if the string is too long or false if not.
 */
function isLong(pString, pMax)
{
    //  Get the string length.
    var varLen = pString.length;
    //  See if it is too long.
    if(varLen > pMax)
    {
        //  The string is too long.
        return true;
    }
    //  The string is not too long.
    return false;
}

/**
 *  Checks to see if a string is too long.
 *  Displays a custom error message if the string is too long.
 *  @param pString the string to test.
 *  @param pMax the maximum string length allowed.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pMSG the error message to display.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckLenCustom(pString, pMax, pTD, pMSG)
{
    if(isLong(pString, pMax) == true)
    {
        //  It's too long.
        ShowError(pTD, pMSG);
        return 1;
    }
    //  It's OK.
    return 0;
}

/**
 *  Checks to see if a string is too long.
 *  Displays a generic error message if the string is too long.
 *  @param pString the string to test.
 *  @param pMax the maximum string length allowed.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pName the name of the field to check.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckLenGeneric(pString, pMax, pTD, pName)
{
    //  Create a generic error message and include the field name.
    var varMSG = "The \"" + pName + "\" field can not have more than " + pMax + " characters.";
    //  Check it out.
    return CheckLenCustom(pString, pMax, pTD, varMSG);
}

/**
 *  Determines whether a string contains only a numeric value.
 *  @param pString the string to test.
 *  @return true if the string is a numeric value or false if not.
 */
function isNumeric(pString)
{
    //  Establish a pattern:  one or more digits.
    var varPattern = /^\d+$/;
    //  Perform a regular expression match.
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
    {
        //  The match failed.
        //  The string is not numeric.
        return false;
    }
    //  The match succeeded.
    //  The string is numeric.
    return true;
}

/**
 *  Checks to see if a string is numeric.
 *  Displays a custom error message if the string is not numeric.
 *  @param pString the string to test.
 *  @param pRequired true if a value is required for pString or false if not.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pMSG the error message to display.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckNumericCustom(pString, pRequired, pTD, pMSG)
{
    if(isEmpty(pString) == true)
    {
        //  The string is empty...
        if(pRequired == true)
        {
            //  but a value is required, so it's in error.
            ShowError(pTD, pMSG);
            return 1;
        }
        else
        {
            //  and it's not required anyway, so it's OK.
            return 0;
        }
    }
    if(isNumeric(pString) == false)
    {
        //  It's not numeric.
        ShowError(pTD, pMSG);
        return 1;
    }
    //  It's OK.
    return 0;
}

/**
 *  Checks to see if a string is numeric.
 *  Displays a generic error message if the string is not numeric.
 *  @param pString the string to test.
 *  @param pRequired true if a value is required for pString or false if not.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pName the name of the field to check.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckNumericGeneric(pString, pRequired, pTD, pName)
{
    //  Create a generic error message and include the field name.
    var varMSG = "The \"" + pName + "\" field must be numeric.\nPlease enter numbers only.";
    //  Check it out.
    return CheckNumericCustom(pString, pRequired, pTD, varMSG);
}

/**
 *  Determines whether a string is formatted as an SSN.
 *  @param pString the string to test.
 *  @return true if the string is in SSN format or false if not.
 */
function isSSN(pString)
{
    //  Establish a pattern:  3 digits, a dash, 2 digits, a dash, and 4 digits.
    var varPattern = /^\d{3}-\d{2}-\d{4}$/;
    //  Perform a regular expression match.
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
    {
        //  The match failed.
        //  The string is not an SSN.
        return false;
    }
    //  The match succeeded.
    //  The string is an SSN.
    return true;
}

/**
 *  Checks to see if a string is an SSN.
 *  Displays a custom error message if the string is not an SSN.
 *  @param pString the string to test.
 *  @param pRequired true if a value is required for pString or false if not.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pMSG the error message to display.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckSSNCustom(pString, pRequired, pTD, pMSG)
{
    if(isEmpty(pString) == true)
    {
        //  The string is empty...
        if(pRequired == true)
        {
            //  but a value is required, so it's in error.
            ShowError(pTD, pMSG);
            return 1;
        }
        else
        {
            //  and it's not required anyway, so it's OK.
            return 0;
        }
    }
    if(isSSN(pString) == false)
    {
        //  It's not an SSN.
        ShowError(pTD, pMSG);
        return 1;
    }
    //  It's OK.
    return 0;
}

/**
 *  Checks to see if a string is an SSN.
 *  Displays a generic error message if the string is not an SSN.
 *  @param pString the string to test.
 *  @param pRequired true if a value is required for pString or false if not.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pName the name of the field to check.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckSSNGeneric(pString, pRequired, pTD, pName)
{
    //  Create a generic error message and include the field name.
    var varMSG =
        "The \"" + pName + "\" field has an invalid SSN.\n" +
        "Please enter a valid SSN using this format:  '000-00-0000'.";
    //  Check it out.
    return CheckSSNCustom(pString, pRequired, pTD, varMSG);
}

/**
 *  Determines whether a string is formatted as a date.
 *  @param pString the string to test.
 *  NOTE:  The expected format is "MM/DD/YYYY".
 *  @return true if the string is in proper date format or false if not.
 */
function isDate(pString)
{
    //  Establish a pattern:  2 digits, a slash, 2 digits, a slash, and 4 digits.
    var varPattern = /^\d{2}\/\d{2}\/\d{4}$/;
    //  Perform a regular expression match.
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
    {
        //  The match failed.
        //  The string is not a date.
        return false;
    }
    //  The match succeeded.
    //  Convert to a Date object and see if we get NaN.
    var d = StringToDate(pString);
    var t = d.getTime();
    var n = window.isNaN(t);
    if(n == true)
    {
        return false;
    }
    //  Verify ranges for each date component.
    var varTemp = varMatch.toString();
    var varArray = varTemp.split("/");
    var varMonth = varArray[0];
    var varDay = varArray[1];
    var varYear = varArray[2];
    //  If you allow less than 4-digit years,
    //  they are subject to windowing rules.
    //  Requiring 4-digit years prevents that.
    if
    (
        (varMonth <    1) || (varMonth >   12)
    ||  (varDay   <    1) || (varDay   >   31)
    ||  (varYear  < 1000) || (varYear  > 9999)
    )
    {
        //  One or more parts of the date are out of range.
        return false;
    }
    //  Further verification for accuracy on the day part.
    //  This also helps prevent automatic rollovers.
    var varMonths = new Array(12);
    varMonths[0]  = 31;
    varMonths[1]  = 28;
    varMonths[2]  = 31;
    varMonths[3]  = 30;
    varMonths[4]  = 31;
    varMonths[5]  = 30;
    varMonths[6]  = 31;
    varMonths[7]  = 31;
    varMonths[8]  = 30;
    varMonths[9]  = 31;
    varMonths[10] = 30;
    varMonths[11] = 31;
    //  Implement all 3 leap-year rules
    //  for complete accuracy in February.
    var varMod4   = varYear %   4;
    var varMod100 = varYear % 100;
    var varMod400 = varYear % 400;
    if(varMod4   == 0) varMonths[1] = 29;
    if(varMod100 == 0) varMonths[1] = 28;
    if(varMod400 == 0) varMonths[1] = 29;
    if(varDay > varMonths[varMonth-1])
    {
        //  The day is out of range.
        return false;
    }
    //  It's OK.
    return true;
}

/*
 *  Converts a date-formatted string into a Date object.
 *  @param pString the string to convert.
 *  @return a Date object.
 *  NOTE:  To ensure a valid Date value on return, run isDate() first.
 */
function StringToDate(pString)
{
    //  Convert it.
    var d = new Date(pString);
    //  Return it.
    return d;
}

/**
 *  Converts a Date object into a date-formatted string.
 *  @param pDate a Date object.
 *  @return a date-formatted string.
 *  NOTE:  If the date's value is not valid, it will return an empty string.
 *  NOTE:  The format returned is "MM/DD/YYYY".
 */
function DateToString(pDate)
{
    //  Get the internal numeric value,
    //  which is the number of seconds since 01/01/1970.
    var t = pDate.getTime();
    //  If it's not a valid number, return an empty string.
    var n = window.isNaN(t);
    if(n == true)
    {
        return "";
    }
    //  Separate the date parts.
    var varM = pDate.getMonth() + 1;
    var varD = pDate.getDate();
    var varY = pDate.getFullYear();
    //  Convert them to strings.
    var varMM = "" + varM;
    var varDD = "" + varD;
    var varYY = "" + varY;
    //  Zero pad.
    if(varM < 10)   varMM = "0" + varMM;
    if(varD < 10)   varDD = "0" + varDD;
    if(varY < 10)   varYY = "0" + varYY;
    if(varY < 100)  varYY = "0" + varYY;
    if(varY < 1000) varYY = "0" + varYY;
    //  Assemble the strings.
    var s = varMM + "/" + varDD + "/" + varYY;
    //  Return.
    return s;
}

/**
 *  Compares two Date objects.
 *  @param d1 the first date.
 *  @param d2 the second date.
 *  @return
 *  -1 if d1 comes before d2 or
 *  0 if they are equal or
 *  1 if d1 comes after d2 or
 *  NaN if either date is invalid.
 */
function CompareDates(d1, d2)
{
    //  Get the internal numeric values.
    var t1 = d1.getTime();
    var t2 = d2.getTime();
    //  Verify validity.
    var n1 = window.isNaN(t1);
    var n2 = window.isNaN(t2);
    //  If either date is invalid, return NaN.
    if
    (
        (n1 == true)
    ||  (n2 == true)
    )
    {
        return Number.NaN;
    }
    //  d1 comes before d2.
    if(t1 < t2) return -1;
    //  d1 comes after d2.
    if(t1 > t2) return  1;
    //  The are the same.
    return 0;
}

/**
 *  Compares two date-formatted strings.
 *  @param p1 the first date.
 *  @param p2 the second date.
 *  @return
 *  -1 if d1 comes before d2 or
 *  0 if they are equal or
 *  1 if d1 comes after d2 or
 *  NaN if either date is invalid.
 *  NOTE:  This is a convenience method that just
 *  converts the string parameters into dates
 *  and then calls CompareDates().
 */
function CompareDateStrings(p1, p2)
{
    //  Convert strings to dates.
    var d1 = StringToDate(p1);
    var d2 = StringToDate(p2);
    //  Compare them.
    var x = CompareDates(d1, d2);
    //  Return.
    return x;
}

/**
 *  Compares a date-formatted string to today's date.
 *  @param pString the date to compare.
 *  @return
 *  -1 if pString comes before today or
 *  0 if they are equal or
 *  1 if pString comes after today or
 *  NaN if pString is not a valid date.
 *  NOTE:  This is a convenience method that just
 *  converts the string parameter into a date
 *  and then calls CompareDateStrings().
 */
function CompareDateStringToToday(pString)
{
    //  Get today's date.
    var d = new Date();
    //  Get rid of the time portion.
    var s = DateToString(d);
    //  Compare the two date strings.
    var x = CompareDateStrings(pString, s);
    //  Return.
    return x;
}

/**
 *  Checks to see if a string is a valid date.
 *  Displays an error message if the string is not a valid date.
 *  @param pString the string to test.
 *  @param pRequired true if a value is required for pString or false if not.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @param pName the name of the field to check.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckDate(pString, pRequired, pTD, pName)
{
    var varMSG = "";
    varMSG =
        "The \"" + pName + "\" field has an invalid date.\n" +
        "Please enter a valid date using this format:  'mm/dd/yyyy'.";
    if(isEmpty(pString) == true)
    {
        //  The string is empty...
        if(pRequired == true)
        {
            //  but a value is required, so it's in error.
            ShowError(pTD, varMSG);
            return 1;
        }
        else
        {
            //  and it's not required anyway, so it's OK.
            return 0;
        }
    }
    if(isDate(pString) == false)
    {
        //  It's not a date.
        ShowError(pTD, varMSG);
        return 1;
    }
    varMSG =
        "The " + pName + " you have entered is invalid.\n" +
        "Please enter a date between 12/31/1875 and today's date.";
    //  Range check.  If out of range, display custom error message.
    if(CompareDateStrings(pString, "12/31/1875") == -1)
    {
        ShowError(pTD, varMSG);
        return 1;
    }
    if(CompareDateStringToToday(pString) == 1)
    {
        ShowError(pTD, varMSG);
        return 1;
    }
    //  It's OK.
    return 0;
}

/**
 *  Verifies date order.
 *  @param p1 the first date string.
 *  @param p2 the second date string.
 *  @param p1Name the name of the first field.
 *  @param p2Name the name of the second field.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckDateOrder(p1, p2, p1Name, p2Name, pTD)
{
    var varMSG =
        "The date that you have entered into the " + p1Name + " field\n" +
        "is greater than the date entered in the " + p2Name + " field.\n" +
        "Please select a date for the " + p1Name + " field that is\n" +
        "less than the one in the " + p2Name + " field and try again.";
    if(CompareDateStrings(p1, p2) == 1)
    {
        ShowError(pTD, varMSG);
        return 1;
    }
    return 0;
}

/**
 *  Verifies the validity and order of two dates.
 *  @param p1 the first date string.
 *  @param p2 the second date string.
 *  @param p1Name the name of the first field.
 *  @param p2Name the name of the second field.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckDates(p1, p2, p1Name, p2Name, pTD)
{
    var x = 0;
    //  Check format and range.
    x += CheckDate(p1, false, pTD, p1Name);
    x += CheckDate(p2, false, pTD, p2Name);
    //  If either one is bad, then return.
    if(x > 0)
    {
        return x;
    }
    //  Only check for proper order if both were
    //  filled in and neither one had any errors.
    if
    (
        (isEmpty(p1) == false)
    &&  (isEmpty(p2) == false)
    )
    {
        x += CheckDateOrder(p1, p2, p1Name, p2Name, pTD);
    }
    return x;
}

/**
 *  Submits the form and notes the specified action to take.
 *  @param pAction the action to take.
 *  @return always returns true.
 */
function DoSubmit(pAction)
{
    //  Set the action.
    frm.txtAction.value = pAction;
    //  Submit the form.
    frm.submit();
    //  Return.
    return true;
}

/**
 *  Resets the form.
 *  @return always returns true.
 */
function frm_clear()
{
    //  Reset the form.
    frm.reset();
    //  Return.
    return true;
}

/**
 *  Jumps to a History page.
 *  @return always returns true.
 */
function frm_history()
{
    DoSubmit("history");
}

/**
 *  Cancels the current action.
 *  @return always returns true.
 */
function frm_cancel()
{
    //  Prompt the user first.
    var x = false;
    x = window.confirm("Are you sure you want to cancel this?");
    if(x == false)
    {
        //  The user hit the "Cancel" button, so don't do anything.
        return false;
    }
    //  The user hit the "OK" button, so go ahead with the cancel.
    DoSubmit("cancel");
}

/**
 *  Jumps to an add page.
 *  @return always returns true.
 */
function frm_add()
{
    DoSubmit("add");
}

/**
 *  Jumps to an edit page.
 *  @return always returns true.
 */
function frm_edit()
{
    DoSubmit("edit");
}

/**
 *  Jumps to a delete page.
 *  @return always returns true.
 */
function frm_delete()
{
    DoSubmit("delete");
}
