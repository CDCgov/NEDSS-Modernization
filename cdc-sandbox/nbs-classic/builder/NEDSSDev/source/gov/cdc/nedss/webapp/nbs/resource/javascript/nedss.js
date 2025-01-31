/**
 *  nedss.js.
 *  Global script library.
 *  Calls functions that are in sniffer.js.
 *  @author Ed Jenkins
 */

//  Constants for node types in XML DOM Level 2.
var ELEMENT_NODE                =  1;
var ATTRIBUTE_NODE              =  2;
var TEXT_NODE                   =  3;
var CDATA_SECTION_NODE          =  4;
var ENTITY_REFERENCE_NODE       =  5;
var ENTITY_NODE                 =  6;
var PROCESSING_INSTRUCTION_NODE =  7;
var COMMENT_NODE                =  8;
var DOCUMENT_NODE               =  9;
var DOCUMENT_TYPE_NODE          = 10;
var DOCUMENT_FRAGMENT_NODE      = 11;
var NOTATION_NODE               = 12;

var ErrorMessages = new Array();

/**
 *  Pipe.
 */
var varPipe = "|";

var now = new Date();
var currentYearplus = now.getFullYear() + 1;

function makeErrorMsg(eCode, paramList)
{
	for(var i=0;i<ErrorMessages.length;i++)
	{
		if(ErrorMessages[i][0] == eCode)
		{
			var errorMsg = ErrorMessages[i][1];
			//alert(errorMsg);
			if(paramList != null && paramList.length > 0)
			{
				//alert('Length of paramList = ' + paramList.length );
				for(var j=0;j<paramList.length;j++)
				{
					//alert(paramList[j]);
					errorMsg = errorMsg.replace('(Field Name)',paramList[j]);
					errorMsg = errorMsg.replace('(Current Year Plus One)',currentYearplus);
				}
				return errorMsg;

			}//if
			//maybe no param list, need to return error message in this case
			return errorMsg;
		}//if
	}
	return "";
}

/**
 *  Sets a breakpoint for debugging.
 *  @param pFile the name of the calling class.
 *  @param pFunc the name of the calling method.
 *  @param pBP the breakpoint number.
 */
function setBreakpoint(pFile, pFunc, pBP)
{
    var s = "";
    s += "[DEBUG]\n\n";
    s += "File = " + pFile  + "\n";
    s += "Func = " + pFunc + "\n";
    s += "BP = " + pBP;
    window.alert(s);
}

//  Global variables for logg.
var loggWindow = null;
var loggTitle = null;

/**
 *  Opens a log window.
 *  @param pTitle an optional title.
 *  @return a handle to the new window.
 *  The window title will be set to "logg" + the current date and time if pTitle is not specified.
 */
function loggOpen(pTitle)
{
    var d = new Date();
    var varDate = DateToDateString(d);
    var varTime = DateToTimeString(d);
    var s = "logg - " + varDate + " " + varTime;
    if(pTitle != null)
    {
        s += " - " + pTitle;
    }
    loggTitle = s;
    loggWindow = window.open("/nbs/logg.html");
    window.setTimeout("loggSetTitle()", 1000);
    window.focus();
}

/**
 *  Set the window title for the logg window.
 */
function loggSetTitle()
{
    //  Verify parameters.
    if(loggTitle == null)
    {
        return;
    }
    if(loggWindow == null)
    {
        return;
    }
    var varDoc = loggWindow.document;
    varDoc.title = loggTitle;
}

/**
 *  Writes log messages to a new window.
 *  Creates a new log window if one does not already exist.
 *  @param pWin the window to write to.
 *  @param pFile the name of the calling class.
 *  @param pFunc the name of the calling method.
 *  @param pMSG the message to log.
 *  @param pSeparator if true, it will add an <hr/> after the message is logged.
 */
function logg(pFile, pFunc, pMSG, pSeparator)
{
    //  Verify parameters.
    if(pFile == null)
    {
        return;
    }
    if(pFunc == null)
    {
        return;
    }
    if(pMSG == null)
    {
        return;
    }
    //  Make sure the logg window is open.
    if(loggWindow == null)
    {
        return;
    }
    //  Create temp variables.
    var d = new Date();
    var s = "";
    s += DateToDateString(d) + " ";
    s += DateToTimeString(d, true) + " ";
    s += pFile + " ";
    s += pFunc + " ";
    s += pMSG;
    //  Add the message to the log window.
    var varDoc = loggWindow.document;
    var eTable = varDoc.getElementById("logg");
    var eTR = varDoc.createElement("tr");
    var eTD = varDoc.createElement("td");
    var t = varDoc.createTextNode(s);
    eTD.appendChild(t);
    eTR.appendChild(eTD);
    eTable.appendChild(eTR);
    if(pSeparator == null)
    {
        return;
    }
    var eTRhr = varDoc.createElement("tr");
    var eTDhr = varDoc.createElement("td");
    var eHR = varDoc.createElement("hr");
    eTDhr.appendChild(eHR);
    eTRhr.appendChild(eTDhr);
    eTable.appendChild(eTRhr);
}

/**
 *  Global event handler for body_onload events.
 *  body.xsl will create a default event handler, which will call this.
 *  If you add a body_onload event handler to any page, body.xsl will
 *  add a call to this function before the rest of the code you add.
 */
function global_body_onload()
{
    //  Disable the context menu.
    if(is_ie)
    {
        document.oncontextmenu = document_oncontextmenu;
    }
    else
    {
        var nl = document.getElementsByTagName("body");
        var b = nl[0];
        b.setAttribute("oncontextmenu", "return false;");
    }
    //  Start the session timer.
    StartSessionTimer();
    //  Show hidden fields if running Netscape.
    ShowHiddenFieldsIfNetscape();
    //  Set focus to the first visible input control.
    MoveFocusToFirstField();
}

/**
 *  Event handler to disable the context menu.
 */
function document_oncontextmenu()
{
    return false;
}

/**
 *  Changes the class of the parent <td> elements for all hidden fields
 *  from Hidden to Invisible when running in Netscape.
 *  In Netscape, hidden fields do not get sent to the server.
 *  This is not a problem in IE or Mozilla, only in Netscape.
 */
function ShowHiddenFieldsIfNetscape()
{
    //  Exit if not NN.
    if(!is_nav)
    {
        return;
    }
    //  Create temp variables.
    var varElement = null;
    var varElements = document.frm.elements;
    var varParent = null;
    var n = null;
    var s = null;
    var t = null;
    var x = 0;
    var y = varElements.length;
    var z = 0;
    if(y < 1)
    {
        return;
    }
    //  Loop through all input elements.
    for(x=0; x<y; x++)
    {
        //  Get a field.
        varElement = varElements[x];
        //  Ignore anything that is not a hidden field.
        n = getCorrectAttribute(varElement,"nodeName",varElement.nodeName).toLowerCase();
        if(n != "input")
        {
            continue;
        }
        t = getCorrectAttribute(varElement,"type",varElement.type).toLowerCase();
        if(t != "hidden")
        {
            continue;
        }
        //  Get its parent <td>.
        varParent = varElement.parentNode;
        if(varParent.nodeName.toLowerCase() != "td")
        {
            continue;
        }
        //  Change it from Hidden to Invisilbe.
        MakeElementInvisible(varParent);
    }
}

/**
 *  For the navbar.
 *  @param pURL the URL to GET.
 */
function navbar(pURL)
{
    window.location = pURL;
}

/**
 *  Clears the statusbar.
 */
function ClearStatus()
{
    window.status = " ";
    window.defaultStatus = " ";
}

/**
 *  Toggles the displaying of borders around tables.
 *  @param pOn true to turn borders on or false to turn them off.
 *  @return always returns true.
 */
function ToggleBorders(pOn)
{
    //  Normalize paramters.
    var varBorder = (pOn == null) ? "0" : ( (pOn == true) ? "1" : "0" );
    //  Start with the document element.
    var de = document.documentElement;
    //  Get his children.
    var nl = de.childNodes;
    //  And work on them.
    ToggleBorders_Recursive(nl, varBorder);
    //  Return.
    return true;
}

/**
 *  Helper function for ToggleBorders.
 *  Calls itself recursively until all nodes have been traversed.
 *  @param pNodeList the NodeList to search.
 *  @param pBorder must be either "0" or "1".
 */
function ToggleBorders_Recursive(pNodeList, pBorder)
{
    //  Verify parameters.
    if(pNodeList == null)
    {
        return;
    }
    if(pBorder == null)
    {
        return;
    }
    //  Create temp variables.
    var x = 0;
    var y = pNodeList.length;
    var i = null;
    var varName = null;
    //  Loop through the collection.
    for(x=0; x<y; x++)
    {
        //  Get a node.
        var n = pNodeList.item(x);
        //  Only work with element nodes.
        if(n.nodeType != ELEMENT_NODE)
        {
            continue;
        }
        //  Only work with table elements.
        varName = n.nodeName.toLowerCase();
        if(varName == "table")
        {
            //  Turn borders on or off.
            n.setAttribute("border", pBorder);
        }
        //  Get his children.
        var nl = n.childNodes;
        //  And work on them.
        ToggleBorders_Recursive(nl, pBorder);
    }
}

/**
 *  Toggles enabling/disabling of text box based on status of a checkbox.
 *  @param pCHK the checkbox to examine.
 *  @param pTXT the textbox to enable or disable.
 *  @return always returns true.
 */
function ToggleCheckText(pCHK, pTXT)
{
    //  Get state of checkbox.
    var b = getCorrectAttribute(pCHK,"checked",pCHK.checked);
    //  If unchecked, clear the textbox and disable it.
    if(b == false)
    {
        pTXT.value = "";
    }
    pTXT.disabled = !b;
    //  Or, if checked, then enable the textbox and move the focus to it.
    if(b == true)
    {
        pTXT.focus();
    }
    //  Return
    return true;
}

/**
 *  Toggles enabling/disabling of text box based on status of a listbox.
 *  @param pLST the listbox to examine.
 *  @param pTXT the textbox to enable or disable.
 *  @return always returns true.
 */
function ToggleListText(pLST, pTXT)
{
    //  See if something is selected.
    var b = isOptionSelected(pLST, "Other");
    //  If not, then clear the textbox and disable it.
    if(b == false)
    {
        pTXT.value = "";
    }
    pTXT.disabled = !b;
    //  If something is selected, then enable the textbox and move the focus to it.
    if(b == true)
    {
        pTXT.focus();
    }
    //  Return.
    return true;
}

/**
 *  Makes an element visible.
 *  @param pElement the element to make visible.
 */
function MakeElementVisible(pElement)
{
    //  Verify parameters.
    if(pElement == null)
    {
        return;
    }
    //  Create regular expressions.
    var reVisible = /Visible/;
    var reInvisible = /Invisible/;
    var reHidden = /Hidden/;
    //  Get CSS class.
    var varClass = getCorrectAttribute(pElement, "className", pElement.className);
    //  Change to Visible.
    if(Contains(varClass, "Hidden"))
    {
        varClass = varClass.replace(reHidden, "Visible");
    }
    if(Contains(varClass, "Invisible"))
    {
        varClass = varClass.replace(reInvisible, "Visible");
    }
    pElement.className = varClass;
}

/**
 *  Makes an element invisible.
 *  @param pElement the element to make invisible.
 */
function MakeElementInvisible(pElement)
{
    //  Verify parameters.
    if(pElement == null)
    {
        return;
    }
    //  Create regular expressions.
    var reVisible = /Visible/;
    var reInvisible = /Invisible/;
    var reHidden = /Hidden/;
    //  Get CSS class.
    var varClass = getCorrectAttribute(pElement, "className", pElement.className);
    //  Change to Invisible.
    if(Contains(varClass, "Hidden"))
    {
        varClass = varClass.replace(reHidden, "Invisible");
    }
    if(Contains(varClass, "Visible"))
    {
        varClass = varClass.replace(reVisible, "Invisible");
    }
    pElement.className = varClass;
}

/**
 *  Makes an element hidden.
 *  @param pElement the element to make hidden.
 */
function MakeElementHidden(pElement)
{
    //  Verify parameters.
    if(pElement == null)
    {
        return;
    }
    //  Create regular expressions.
    var reVisible = /Visible/;
    var reInvisible = /Invisible/;
    var reHidden = /Hidden/;
    //  Get CSS class.
    var varClass = getCorrectAttribute(pElement, "className", pElement.className);
    //  Change to Visible.
    if(Contains(varClass, "Visible"))
    {
        varClass = varClass.replace(reVisible, "Hidden");
    }
    if(Contains(varClass, "Invisible"))
    {
        varClass = varClass.replace(reInvisible, "Hidden");
    }
    pElement.className = varClass;
}

/**
 *  Displays an error message at the top of the page.
 *  @param pMSG the error message to display.
 *  If pMSG contains a pipe ("|"), then it will be used as a delimiter
 *  and split up into multiple lines.
 *  @return always returns true.
 */
function ShowTopError(pMSG)
{
    //  Verify parameters.
    if(pMSG == null)
    {
        return;
    }
    //  Get the error cells.
    var e1 = getElementByIdOrByName("error1");
    var e2 = getElementByIdOrByName("error2");
    //  Remove old content.
    RemoveAllChildNodesExceptAttributes(e1)
    //  Add new content.
    var eSpan = document.createElement("span");
    if(Contains(pMSG, varPipe))
    {
        //  Split the string into an array.
        var a = pMSG.split(varPipe);
        var x = 0;
        var y = a.length;
        //  Loop through the array.
        for(x=0; x<y; x++)
        {
            //  Add each string from the array.
            var t = document.createTextNode(a[x]);
            eSpan.appendChild(t);
            //  Put a line break after every string but the last one.
            var eBR = document.createElement("br");
            if(x < (y-1))
            {
                eSpan.appendChild(eBR);
            }
        }
        e1.appendChild(eSpan);
    }
    else
    {
        var t = document.createTextNode(pMSG);
        eSpan.appendChild(t);
        e1.appendChild(eSpan);
    }
    //  Display it.
    e1.className = "error";
    //  Display a blank line before it.
    e2.className = "Visible";
    //  Return.
    return true;
}

/**
 *  Hides the error message at the top of the page.
 *  @return always returns true.
 */
function HideTopError()
{
    //  Get the error cells.
    var e1 = getElementByIdOrByName("error1");
    var e2 = getElementByIdOrByName("error2");
    //  Hide the error message.
    e1.className = "Hidden";
    //  Hide the blank line.
    e2.className = "Hidden";
    //  Reset the message text.
    e1.innerHTML = ".";
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
    //  Verify parameters.
    if(pTD == null)
    {
        return true;
    }
    if(pMSG == null)
    {
        return true;
    }
    //  Display a generic error message at the top of the page.
    var varTopMSG =
        "One or more fields are not valid.  " +
        "Please make the suggested changes and then try again.";
    ShowTopError(varTopMSG);
    //  See if an error message already exists.
    var varMSG = pTD.innerHTML;
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
        varMSG += "<br/>";
    }
    //  Append the new message.
    varMSG += pMSG;
    //  Set message text.
    pTD.innerHTML = varMSG;
    //  Display it.
    pTD.className = "error";
    //  Return.
    return true;
}

/**
 *  Hides an error message.
 *  @param pTD the table cell to be hidden.
 *  @return always returns true.
 */
function HideError(pTD)
{
    //  Verify parameters.
    if(pTD == null)
    {
        return true;
    }
    //  Hide the error message.
    pTD.className = "Hidden";
    //  Reset content to just a placeholder.
    //  A special placeholder is needed so that
    //  ShowError() will know when to append a blank line
    //  and when not to.
    pTD.innerHTML = ".";
    //  Return.
    return true;
}

/**
 *  Hides all error messages.
 *  @return always returns true.
 */
function HideErrors()
{
    //  Get all error lines.
    var a = getElementsWhereIdContains("error");
    var x = 0;
    var y = a.length;
    var e = null;
    //  Hide them all.
    for(x=0; x<y; x++)
    {
        e = a[x];
        if(StartsWith(e.id, "error"))
        {
            continue;
        }
        HideError(e);
    }
    //  Reset the generic error message.
    HideTopError();
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
    var varValue = getCorrectAttribute(pLST, "value", pLST.value);
    if(varValue == "")
    {
        //  Nothing has been selected.
        return false;
    }
    //  Something has been selected.
    return true;
}

/**
 *  Determines whether the specified text has been selected from a listbox.
 *  @param pLST the listbox to test.
 *  @param pString the text to look for.
 *  @return true if the specified text has been selected or false if not.
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
    var varOption = pLST.options[pLST.selectedIndex].text.toUpperCase();
    //  See if it is what you want.
    var varString = pString.toUpperCase();
    if(varOption == varString)
    {
        //  It is what we want.
        return true;
    }
    //  It's not what we want.
    return false;
}

/**
 *  Determines whether the specified value has been selected from a listbox.
 *  @param pLST the listbox to test.
 *  @param pString the value to look for.
 *  @return true if the specified value has been selected or false if not.
 */
function isOptionSelectedByValue(pLST, pString)
{
    if(pLST.selectedIndex < 0)
    {
        //  Nothing is selected.
        return false;
    }
    //  Something is selected.
    //  Find out what it is.
    var varOption = pLST.options[pLST.selectedIndex].value.toUpperCase();
    //  See if it is what you want.
    var varString = pString.toUpperCase();
    if(varOption == varString)
    {
        //  It is what we want.
        return true;
    }
    //  It's not what we want.
    return false;
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
    var varMSG = "Please select " + pName + " and try again.";
    //  Check it out.
    return CheckSelectedCustom(pLST, pTD, varMSG);
}

/**
 *  Gets a count of how many options are selected in a listbox.
 *  @param pLST the listbox to count.
 *  @return the number of options that are selected.
 */
function GetSelectedCount(pLST)
{
    //  Verify parameters.
    if(pLST == null)
    {
        return 0;
    }
    //  Shortcut.
    if(pLST.selectedIndex == -1)
    {
        return 0;
    }
    var x = 0;
    var y = pLST.options.length;
    var z = 0;
    var o = null;
    //  Loop through all options.
    for(x=0; x<y; x++)
    {
        o = pLST.options[x];
        //  If it is selected, increment the count.
        if(o.selected == true)
        {
            z++;
        }
    }
    //  Return the count.
    return z;
}

/**
 *  Selectes or deselects all options in a listbox.
 *  @param pLST the listbox to work on.
 *  @param pSelected true to select or false to deselect.
 */
function SelectAllOptions(pLST, pSelected)
{
    //  Verify parameters.
    if(pLST == null)
    {
        return;
    }
    //  Create temp variables for looping.
    var x = 0;
    var y = pLST.options.length;
    var varName = null;
    var varValue = null;
    //  Select all options.
    for(x=0; x<y; x++)
    {
        varName = pLST.options[x].text;
        varValue = pLST.options[x].value;
        //  Skip empty options.
        if(varName == "")
        {
            continue;
        }
        if(varValue == "")
        {
            continue;
        }
        pLST.options[x].selected = pSelected;
    }
}

/**
 *  Selectes or deselects an option in a listbox.
 *  @param pLST the listbox to work on.
 *  @param pValue the value of the option to select or deselect.
 *  @param pSelected true to select or false to deselect.
 *  @deprecated
 */
function SelectOption(pLST, pValue, pSelected)
{
    //  Verify parameters.
    if(pLST == null)
    {
        window.alert("SelectOption:  pLST is null");
        return;
    }
    if(pValue == null)
    {
        window.alert("SelectOption:  pValue is null");
        return;
    }
    //  Create temp variables for looping.
    var x = 0;
    var y = pLST.options.length;
    var varValue = null;
    //  Select all options.
    for(x=0; x<y; x++)
    {
        varValue = pLST.options[x].value;
        window.alert("SelectOption:  varValue = " + varValue);
        if(varValue == pValue)
        {
            window.alert("SelectOption:  found it!");
            pLST.options[x].selected = pSelected;
            break;
        }
    }
}

/**
 *  Gets an array of values of selected options, if any.
 *  @param pLST the listbox to work on.
 *  @return an array of values.
 */
function GetSelected(pLST)
{
    //  Create return variable.
    var a = new Array();
    //  Verify parameters.
    if(pLST == null)
    {
        return;
    }
    //  Create temp variables for looping.
    var x = 0;
    var y = pLST.options.length;
    var z = 0;
    var o = null;
    //  Get selected options.
    for(x=0; x<y; x++)
    {
        o = pLST.options[x];
        if(o.selected == true)
        {
            a[z++] = o.value;
        }
    }
    //  Return result.
    return a;
}

/**
 *  Selects options in a listbox.
 *  If no values match any values in the listbox, the first option will be selected.
 *  @param pLST the listbox to work on.
 *  @param pValues an array of values to select.
 */
function SetSelected(pLST, pValues)
{
    //  Verify parameters.
    if(pLST == null)
    {
        return;
    }
    if(pValues == null)
    {
        return;
    }
    var x = 0;
    var y = pLST.options.length;
    var o = null;
    var v = null;
    var a = 0;
    var b = pValues.length;
    var c = false;
    for(x=0; x<y; x++)
    {
        o = pLST.options[x];
        v = o.value;
        for(a=0; a<b; a++)
        {
            if(v == pValues[a])
            {
                o.selected = true;
                c = true;
            }
            else
            {
                o.selected = false;
            }

        }
    }
    if(c == false)
    {
        if(y > 0)
        {
            pLST.options[0].selected = true;
        }
    }
}

/**
 *  Selectes or deselects an option in a listbox.
 *  @param pLST the listbox to work on.
 *  @param pValue the value of the option to select or deselect.
 *  @param pSelected true to select or false to deselect.
 */
/*
function SetSelected(pLST, pValue, pSelected)
{
    //  Verify parameters.
    if(pLST == null)
    {
        return;
    }
    if(pValue == null)
    {
        return;
    }
    if(pSelected == null)
    {
        return;
    }
    //  Create temp variables for looping.
    var x = 0;
    var y = pLST.options.length;
    var varValue = null;
    //  Select all options.
    for(x=0; x<y; x++)
    {
        varValue = pLST.options[x].value;
        if(varValue == pValue)
        {
            pLST.options[x].selected = pSelected;
            break;
        }
    }
}
*/

/**
 *  Deletes all options from the specified listbox.
 *  @param pLST the listbox to delete options from.
 */
function DeleteOptions(pLST)
{
    //  Verify parameters.
    if(pLST == null)
    {
        return;
    }
    //  Delete all options.
    var y = pLST.options.length - 1;
    while(y >= 0)
    {
        var o = pLST.options[y];
        var t = o.text;
        pLST.remove(y);
        y--;
    }
}

/**
 *  Copies options from one listbox to another.
 *  Deletes all options from pDest first.
 *  @param pSource the listbox to copy options from.
 *  @param pDest the listbox to copy options to.
 */
function CopyOptions(pSource, pDest)
{
    //  Verify parameters.
    if(pSource == null)
    {
        return;
    }
    if(pDest == null)
    {
        return;
    }
    //  Create temp variables for looping.
    var x = 0;
    var y = pSource.options.length;
    var o = null;
    var varName = null;
    var varValue = null;
    var e = null;
    var t = null;
    //  Copy options from the source listbox to the destination listbox.
    for(x=0; x<y; x++)
    {
        o = pSource.options[x];
        varName = o.text;
        varValue = o.value;
        //  Create a new option for the destination listbox.
        e = document.createElement("option");
        e.setAttribute("value", varValue);
        t = document.createTextNode(varName);
        e.appendChild(t);
        //  Add the new option to the destination listbox.
        pDest.appendChild(e);
    }
}

/**
 *  Moves options from one listbox to another.
 *  @param pSource the listbox to move options from.
 *  @param pDest the listbox to move options to.
 *  @param pAll true to move all options or false to move only the ones that are selected.
 */
function MoveOptions(pSource, pDest, pAll,pDestSort)
{
    //alert(pSource.id);
    //  Verify parameters.
    if(pSource == null)
    {
        return;
    }
    if(pDest == null)
    {
        return;
    }
    if(pAll == null)
    {
        return;
    }
    //  Create temp variables for looping.
    var x = 0;
    var y = pSource.options.length;
    var o = null;
    var varName = null;
    var varValue = null;
    var varSelected = null;
    var e = null;
    var t = null;
    var varDI = 0;
    var varDL = 0;
    var varIP = 0;
    var varId = null;
    var b = null;
    var varNext = pSource.selectedIndex;
    //  Add options to the destination listbox.
    for(x=0; x<y; x++)
    {
        o = pSource.options[x];
        varName = o.text;
        varValue = o.value;
        varSelected = o.selected;
        //  Skip empty options.
        if(varName == "")
        {
            continue;
        }
        if(varValue == "")
        {
            continue;
        }
        if( (pAll == false) && (varSelected == false) )
        {
            continue;
        }
        
        
        
        e = document.createElement("option");
        e.setAttribute("value", varValue);
        t = document.createTextNode(varName);
        e.appendChild(t);
        //  Add the new option to the destination listbox.
        varDI = pDest.selectedIndex;
        varDL = pDest.options.length - 1;
        if(varDL < 0)
        {
            pDest.appendChild(e);
        }
        else
        {
            if(varDI < 0)
            {
                pDest.appendChild(e);
            }
            else
            {
                b = pDest.options[varDI];
                pDest.insertBefore(e, b);
            }
        }
        
    }
    MoveSortColumns(pSource,pAll,pDestSort);
    //  Remove options from the source listbox.
    for(x=y-1; x>=0; x--)
    {
       // alert("x:"+x);
        o = pSource.options[x];
        varName = o.text;
        varValue = o.value;
        varSelected = o.selected;
        //  Skip empty options.
        if(varName == "")
        {
            continue;
        }
        if(varValue == "")
        {
            continue;
        }
        if( (pAll == false) && (varSelected == false) )
        {
            continue;
        }
        pSource.remove(x);
    }
    if(pAll == true)
    {
        pDest.focus();
        return;
    }
    //  Select the next thing in the source listbox.
    if(varNext >= pSource.options.length)
    {
        varNext = pSource.options.length - 1;
    }
    if(varNext < 0)
    {
        return;
    }
    pSource.options[varNext].selected = true;
    pSource.focus();
    
    getElementByIdOrByName("SORT_ORDER_textbox").value="";
	getElementByIdOrByName("SORT_ORDER_textbox").disabled=true;
	getElementByIdOrByName("SORT_ORDER_button").disabled=true;
	
}

function MoveSortColumns(pSource,pAll,pDestSort)
{
    //  Verify parameters.
    if(pSource == null)
    {
        return;
    }
    if(pDestSort == null)
    {
        return;
    }
    if(pAll == null)
    {
        return;
    }
    if(pSource.id=="id_AVAILABLE_COLUMNS_list")
    {
    
	//  Create temp variables for looping.
	var x = 0;
	var y = pSource.options.length;
	var o = null;
	var varName = null;
	var varValue = null;
	var varSelected = null;
	var e = null;
	var t = null;
	var varDI = 0;
	var varDL = 0;
	var varIP = 0;
	var varId = null;
	var b = null;
	var varNext = pSource.selectedIndex;
	//  Add options to the destination listbox.
	for(x=0; x<y; x++)
	{
	o = pSource.options[x];
	varName = o.text;
	varValue = o.value;
	varSelected = o.selected;
	//  Skip empty options.
	if(varName == "")
	{
	continue;
	}
	if(varValue == "")
	{
	continue;
	}
	if( (pAll == false) && (varSelected == false) )
	{
	continue;
	}

	e = document.createElement("option");
	varId = "id_sort_"+varValue;
	e.setAttribute("id", varId);
	e.setAttribute("value", varValue);
	t = document.createTextNode(varName);
	e.appendChild(t);
	try{
		pDestSort.insertBefore(e);//IE
	}catch(err){
		pDestSort.insertBefore(e, pDestSort.childNodes[0]);
	}
	}
	}

	else
	{   
	  var y = 0;
	  var x = pSource.options.length;
	  var o = null;
	  if(pAll == true)
	  {
	     getElementByIdOrByName("SORT_COLUMN_textbox").value = "";
             RemoveAllChildNodesExceptAttributes(pDestSort);
             getElementByIdOrByName("id_SORT_BY_list").className="none";
	  }
	  else
	  {
	     
		  for(y=0; y<x; y++)
		  {

		    o = pSource.options[y];
		    var val=null;
		    if(o.value==null)
                       return;
                     val=o.value;
		    var varSelected = o.selected;


		    if(varSelected == false)    
			continue;

		    else  {
                            var q = 0;
			    var r = pDestSort.options.length;
	  		    for(q=0; q<r; q++)
	  		    {
	  		      //alert("last :"+pDestSort.options[q].value);
	  		      //alert("val :"+val);
	  		      if(pDestSort.options[q]!=null && val==pDestSort.options[q].value)
	  		      {
			    	pDestSort.remove(q);
			    	
			      }
			    }
		    }
		 }
		 getElementByIdOrByName("SORT_COLUMN_textbox").value = "";
	}
	}

	}
  

/**
 *  Determines whether a number is less than another number.
 *  This is needed because the less than symbol is a reserved character in XML
 *  and some JavaScript is generated dynamically in XML files.
 *  @param p1 the first number.
 *  @param p2 the second number.
 *  @return true if p1 is less than p2 or false if not.
 */
function isLesser(p1, p2)
{
    if(p1 < p2)
    {
        return true;
    }
    return false;
}

/**
 *  Determines whether a number is greater than another number.
 *  This is just here for completeness, to go with isLesser().
 *  @param p1 the first number.
 *  @param p2 the second number.
 *  @return true if p1 is greater than p2 or false if not.
 */
function isGreater(p1, p2)
{
    if(p1 > p2)
    {
        return true;
    }
    return false;
}

/**
 *  Looks up an option in a listbox, based on the specified value,
 *  and gets the text associated with it.
 *  @param pLST the listbox to search.
 *  @param pValue the value to search for.
 *  @return the text associated with the specified value
 *  or an empty string if the value was not found.
 */
function TranslateValueToText(pLST, pValue)
{
    var varText = "";
    var varOptions = pLST.options;
    var x = 0;
    var y = varOptions.length;
    var varOption = "";
    var varValue = pValue.toUpperCase();
    for(x=0; x<y; x++)
    {
        varOption = varOptions[x].value.toUpperCase();
        if(varOption == varValue)
        {
            varText = varOptions[x].text;
            break;
        }
    }
    return varText;
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
    var varMSG = "The " + pName + " field can not be blank.";
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
    var varMSG = "The " + pName + " field can not have more than " + pMax + " characters.";
    //  Check it out.
    return CheckLenCustom(pString, pMax, pTD, varMSG);
}

/**
* Checks if the character currently entered in the text box 
* is a valid alpha numeric or a space character. 
* If not, it returns the text that was present before this 
* character was entered. Uses isAlphaNumeric() JS method 
* defined below for alphanumeric validation.
*/
function isAlphaNumericOrSpaceCharacterEntered(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for (x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if (c == " " || isAlphaNumeric(c)) {
            s += c;
        }
        pTextbox.value = s;
    }
}

/**
* Checks if the character currently entered in the text box 
* is a valid alpha numeric or not. If not, it returns the text that 
* was present before this character was entered.
* Uses isAlphaNumeric() JS method defined below for alphanumeric 
* validation.
*/
function isAlphaNumericCharacterEntered(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isAlphaNumeric(c)) s += c;
         pTextbox.value = s;
    }
}

/**
* Checks if the character currently entered in the text box 
* is a valid numeric or not. If not, it returns the text that 
* was present before this character was entered.
* Uses JS's isInteger() JS method for validation
*/
function isNumericCharacterEntered(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isInteger(c)) s += c;
         pTextbox.value = s;
    }
}

/**
 *  Determines whether a string contains only a numeric value.
 *  @param pString the string to test.
 *  @return true if the string is a numeric value or false if not.
 *  @deprecated Use isDecimal() or isInteger() instead.
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
 *  Determines whether a string contains only a numeric or alphabetical
 *  value. It returns false if special characters are entered.
 *  @param pString the string to test.
 *  @return true if the string is a numeric or an alphabet value or false if not.
 */
function isAlphaNumeric(pString)
{
    //  Establish a pattern:  one or more digits.
    var varPattern = /[^a-zA-Z0-9]+/;
    //  Perform a regular expression match.
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
    {
        return true;
    }
    //  The match succeeded.
    //  The string is alphanumeric.
    return false;
}

/**
 *  Determines whether a string contains a decimal number.
 *  @param pString the string to test.
 *  @return true if the string is a decimal number or false if not.
 */
function isDecimal(pString)
{
    return isInteger(pString);
}

/**
 *  Determines whether a string contains only an integer value.
 *  @param pString the string to test.
 *  @return true if the string is an integer value or false if not.
 */
function isInteger(pString)
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

function isAlphabet(value){
    var alphaExp = /^[a-zA-Z]+$/;
    if(value.match(alphaExp)){
        return true;
    }else{
        return false;
    }
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
    var varMSG = "Please enter a whole number into the " + pName + " field and try again.";
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
    var varMSG = "Please enter a valid SSN using this format:  '000-00-0000'.";
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

/**
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
    if(varM <   10) varMM = "0" + varMM;
    if(varD <   10) varDD = "0" + varDD;
    if(varY <   10) varYY = "0" + varYY;
    if(varY <  100) varYY = "0" + varYY;
    if(varY < 1000) varYY = "0" + varYY;
    //  Assemble the strings.
    var s = varMM + "/" + varDD + "/" + varYY;
    //  Return result.
    return s;
}

/**
 *  Converts a Date object into a date-formatted string.
 *  @param pDate a Date object.
 *  @return a date-formatted string.
 *  NOTE:  This just calls DateToString.
 */
function DateToDateString(pDate)
{
    //  Call DateToString.
    return DateToString(pDate);
}

/**
 *  Converts a Date object into a date-formatted string.
 *  @param pDate a Date object.
 *  @param pMili an optional flag.
 *  If set to true, it will include milliseconds.
 *  @return a time-formatted string.
 *  NOTE:  If the date's value is not valid, it will return an empty string.
 *  NOTE:  The format returned is "HH:MM:SS".
 */
function DateToTimeString(pDate, pMili)
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
    var varH = pDate.getHours() + 1;
    var varM = pDate.getMinutes();
    var varS = pDate.getSeconds();
    var varI = pDate.getMilliseconds();
    //  Convert them to strings.
    var varHH = "" + varH;
    var varMM = "" + varM;
    var varSS = "" + varS;
    var varII = "" + varI;
    //  Zero pad.
    if(varH <   10) varHH = "0" + varHH;
    if(varM <   10) varMM = "0" + varMM;
    if(varS <   10) varSS = "0" + varSS;
    if(varI <   10) varII = "0" + varII;
    if(varI <  100) varII = "0" + varII;
    if(varI < 1000) varII = "0" + varII;
    //  Assemble the strings.
    var s = varHH + ":" + varMM + ":" + varSS;
    if(pMili != null)
    {
        s += "." + varII;
    }
    //  Return result.
    return s;
}

/**
 *  Converts a Date object into a date-formatted string suitable for sorting.
 *  @param pDate a Date object.
 *  @return a date-formatted string.
 *  NOTE:  If the date's value is not valid, it will return an empty string.
 *  NOTE:  The format returned is "YYYY/MM/DD".
 */
function DateToSortableString(pDate)
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
    if(varM <   10) varMM = "0" + varMM;
    if(varD <   10) varDD = "0" + varDD;
    if(varY <   10) varYY = "0" + varYY;
    if(varY <  100) varYY = "0" + varYY;
    if(varY < 1000) varYY = "0" + varYY;
    //  Assemble the strings.
    var s = varYY + "/" + varMM + "/" + varDD;
    //  Return.
    return s;
}

/**
 *  Converts a date string to a number representing the day of the year.
 *  @param pDate the date to convert to a day of year.
 *  @return the day of the year for the given date or 0 if pDate is not valid.
 */
function DateToDOY(pDate)
{
    //  Verify parameters.
    if(!isDate(pDate))
    {
        return 0;
    }
    //  Create temp variables.
    var varDate = new Date(pDate);
    var varMM = varDate.getMonth();
    var varDD = varDate.getDate();
    var varYY = varDate.getFullYear();
    var x = 0;
    var y = 0;
    //  Create return variable.
    var z = 0;
    //  Create a month array.
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
    var varMod4   = varYY %   4;
    var varMod100 = varYY % 100;
    var varMod400 = varYY % 400;
    if(varMod4   == 0) varMonths[1] = 29;
    if(varMod100 == 0) varMonths[1] = 28;
    if(varMod400 == 0) varMonths[1] = 29;
    //  Calculate the day of year.
    y = varMM;
    for(x=0; x<y; x++)
    {
        z += varMonths[x];
    }
    z += varDD;
    //  Return result.
    return z;
}

/**
 *  Converts a number representing the day of the year to a date string.
 *  @param pDOY the day of the year to convert.
 *  Must be between 1 and 366.
 *  @param pYear puts the DOY in context.
 *  Must be between 1583 and 9999.
 *  @return a date string or null if pDOY is not valid.
 */
function DOYToDate(pDOY, pYear)
{
    //  Verify parameters.
    if( (pDOY < 1) || (pDOY > 366) )
    {
        return null;
    }
    if( (pYear < 1583) || (pYear > 9999) )
    {
        return null;
    }
    //  Create temp variables.
    var varMM = 0;
    var varDD = 0;
    var varYY = pYear;
    var x = 0;
    var y = 0;
    var z = pDOY;
    //  Create return variable.
    var s = null;
    //  Create a month array.
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
    var varMod4   = varYY %   4;
    var varMod100 = varYY % 100;
    var varMod400 = varYY % 400;
    if(varMod4   == 0) varMonths[1] = 29;
    if(varMod100 == 0) varMonths[1] = 28;
    if(varMod400 == 0) varMonths[1] = 29;
    //  Calculate the date.
    for(x=0; x<12; x++)
    {
        if(z <= varMonths[x])
        {
            break;
        }
        z -= varMonths[x];
    }
    varMM = x + 1;
    varDD = z;
    //  Zero pad.
    if(varMM <   10) varMM = "0" + varMM;
    if(varDD <   10) varDD = "0" + varDD;
    if(varYY <   10) varYY = "0" + varYY;
    if(varYY <  100) varYY = "0" + varYY;
    if(varYY < 1000) varYY = "0" + varYY;
    //  Assemble the strings.
    var s = varMM + "/" + varDD + "/" + varYY;
    //  Return result.
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
    varMSG = "Please enter a valid " + pName + " using this format:  'mm/dd/yyyy'.";
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
    varMSG = "Please enter a " + pName + " between 12/31/1875 and today's date.";
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
        p1Name + " must be less than or equal to " +
        p2Name + ". Please correct the data entry and try again.";
    if(CompareDateStrings(p1, p2) == 1)
    {
        ShowError(pTD, varMSG);
        return 1;
    }
    return 0;
}

/**
 *  Verifies the validity and order of two dates.
 *  Both dates are optional.
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
 *  Verifies the validity and order of two dates.
 *  Both dates are required.
 *  @param p1 the first date string.
 *  @param p2 the second date string.
 *  @param p1Name the name of the first field.
 *  @param p2Name the name of the second field.
 *  @param pTD the table cell where the error message is to be displayed.
 *  @return 0 if OK or 1 if error.
 *  The return value is numeric so that errors can be accumulated.
 */
function CheckDatesRequired(p1, p2, p1Name, p2Name, pTD)
{
    var x = 0;
    //  Check format and range.
    x += CheckDate(p1, true, pTD, p1Name);
    x += CheckDate(p2, true, pTD, p2Name);
    //  If either one is bad, then return.
    if(x > 0)
    {
        return x;
    }
    //  Only check for proper order if both were
    //  filled in and neither one had any errors.
    x += CheckDateOrder(p1, p2, p1Name, p2Name, pTD);
    return x;
}



function DateMask(pTextbox, pKey, e)
{
	
    if(pTextbox == null)
        return;
	var cYear = new Date().getFullYear().toString();

    var varKey = 0;
    if(pKey == null){
        if(is_ie){
		if(window.event!=null)
            	varKey = window.event.keyCode;
        }
        else{
        	if(e!=null)
        		varKey=e.which;
        }
    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "/";
    var d = 0;

	if(varVal==undefined)
		varVal=pTextbox.target.value;
	
    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
    
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    y = varVal.length;

    for(x=0; x<y; x++) {

        c = varVal.substr(x, 1);
        if(isInteger(c)) {
			if(d == 0 && (c == 0 || c == 1))
				s += c;
			if(d == 1) {
				if( (s==0 && (c>= 0 && c <= 9)) || (s==1 && (c>=0 && c<3))  )
				s += c;
			}
			if(d == 2 && (c>=0 && c<4))
				s += c;
			if(d == 3) {
				var d2 = s.substr(3,1);
				if(d2 == 0 && (c>=1 && c<10))	
					s += c;
				if((d2 == 1 || d2 == 2) && (c>=0 && c<10))	
					s += c;
				if(d2 == 3 && (c==0 || c==1))	
					s += c;
			}
			if(d == 4)	{
				var y1 = cYear.substr(0,1); 
				if(c >= 1 && (c <=y1))
					s += c;					
			}
			if(d == 5) {
				var y1 = s.substr(6,1);
				if(y1==1 && (c==8 || c==9))
					s += c;
				if(y1==2) {
					var y2 = cYear.substr(1,1); 
					if(c>=0 && c<= y2)
						s += c;
				}
			}
			if(d == 6) {
				var y2 = s.substr(7,1);
				if(y2==8) {
					if(c>=7 && c<=9)
						s += c;
					if(c<=6) {}
				} else if(y2==9 && (c>=0 && c<=9)) {
					s += c;
				} else {
					var y3 = cYear.substr(2,1); 
					if(c>=0 && c<= y3)
						s += c;
				}
			}
			if(d == 7) {
				var y2 = s.substr(7,1);
				var y3 = s.substr(8,1);
				if(y2 == 8 && y3 == 7) {
					if(c<6) {}
					if(c>=6) s+= c;
					
				}else if(y2 == 8 && y3==8){
					s +=c;
				}else if(y2 == 8 && y3==9){
					s +=c;
				}
				else if(y2 == 9) {
					s += c;
				} else {
					var y4 = cYear.substr(3,1);
					if(y4 >= 0) y4 = cYear.substr(2,2); //per Jit 2011
					y4 = parseInt(y4);					
					if(c>=0 && c<= y4) {					
						var tst = s + c;
						var result = CompareDates(new Date(), new Date(tst));						
						if(result != -1)
							s += c;					
					}
						
						
						
				}
			}			
			
			d++;

            if(s.length == 2 || s.length == 5)
			s += z;
        }
        
       	if(d == 8)            
       	break;

    }

	if(pTextbox.value!=undefined)
		pTextbox.value = s;
	else
		pTextbox.target.value = s;
	

    if(d != 8)
    {	
        return;
    }

}

// This YearMask allows user to enter upto CurrentYear +1 Year. For example in 2009 it allows to enter 2010
function YearMask(pTextbox, pKey)
{

    if(pTextbox == null)
        return;
	var cYear = new Date().getFullYear().toString();
	var now = new Date();
	//var cYear = (now.getFullYear() + 1).toString();
    var varKey = 0;
    if(pKey == null){
        if(is_ie){
			if(window.event!=null)
			{
	          	varKey = window.event.keyCode;
			}
		
        }
    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "/";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
   
    
    for(x=0; x<y; x++) {
    
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    y = varVal.length;

    for(x=0; x<y; x++) {
    	
        c = varVal.substr(x, 1);
         if(isInteger(c)) {
			if(d == 0){
				var y1 = cYear.substr(0,1); 
				if(c >= 1 && (c <=y1))
					s += c;					
				}
				
			if(d == 1) {
				var y1 = s.substr(0,1);
				if(y1==1 && (c==8 || c==9))
					s += c;
				if(y1==2) {
					var y2 = cYear.substr(1,1); 
					if(c>=0 && c<= y2)
						s += c;
					}
				}	
				
			if(d == 2) {
				var y2 = s.substr(1,1);
				if(y2==8) {
					if(c>=7 && c<=9)
						s += c;
					if(c<=6) {}
				} else if(y2==9 && (c>=0 && c<=9)) {
					s += c;
				} else {
					var y3 = cYear.substr(2,1); 
					if(c>=0 && c<= y3)
						s += c;
				}
			}
			if(d == 3) {
				var y2 = s.substr(1,1);
				var y3 = s.substr(2,1);
				if(y2 == 8 && y3==7) {
					if(c<5) {}
					if(c>=5) s+= c;

				}else if(y2 == 8 && y3==8)
				{
					s +=c;
				}else if(y2 == 8 && y3==9)
				{
					s +=c;
				}
				else if(y2 == 9) {
					s += c;
				}
				else {
					
					if(varVal <= cYear) {
					
						var y4 = cYear.substr(3,1); 
						if(y4 >= 0) y4 = cYear.substr(2,2); //per Jit 2011
						y4 = parseInt(y4);
						if(c>=0 && c<= y4) {					
							var tst = s + c;
							var result = CompareDates(new Date(), new Date(tst));						
							if(result != -1)
								s += c;					
						}					
					}					
				
				}
			}			
				
			d++;
        }
        
       	if(d == 4)  
       	{
       		break;
       	}
    }

    pTextbox.value = s;

    if(d != 4)
    {	
        return;
    }

}



//YearMaskFuture: it is the same method than YearMask but allowing future years as well.
//Any year (4 digits) under 1875 won't be allowed to be entered
//Any year >=1875, won't go through any of the validation in this method, and it will be allowed.
//Entering a number < 4 digits will be controlled from the page on submit.
//This method was created for the user story ND-30918

function YearMaskFuture(pTextbox, pKey)
{

 if(pTextbox == null)
     return;
	var cYear = new Date().getFullYear().toString();
	var now = new Date();
	//var cYear = (now.getFullYear() + 1).toString();
 var varKey = 0;
 if(pKey == null){
     if(is_ie){
			if(window.event!=null)
			{
	          	varKey = window.event.keyCode;
			}
		
     }
 }
 else
 {
     varKey = pKey;
 }
 var varVal = pTextbox.value;
 var x = 0;
 var y = 0;
 var s = "";
 var c = "";
 var z = "/";
 var d = 0;

 var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
 y = varKeys.length;

 
 for(x=0; x<y; x++) {
 
     if(varKey == varKeys[x])
     {
         return;
     }
 }
 y = varVal.length;

 
 
 if(varVal.substr(0, 1)==1){//If this is true, it works exactly the same than the method: YearMask. //If this is not true, we don't need to check anything else, we allow any year > 1875
	 for(x=0; x<y; x++) {
	 	
	     c = varVal.substr(x, 1);
	      if(isInteger(c)) {
				if(d == 0){
					var y1 = cYear.substr(0,1); 
					if(c >= 1 && (c <=y1))
						s += c;					
					}
					
				if(d == 1) {
					var y1 = s.substr(0,1);
					if(y1==1 && (c==8 || c==9))
						s += c;
					if(y1==2) {
						var y2 = cYear.substr(1,1); 
						if(c>=0 && c<= y2)
							s += c;
						}
					}	
					
				if(d == 2) {
					var y2 = s.substr(1,1);
					if(y2==8) {
						if(c>=7 && c<=9)
							s += c;
						if(c<=6) {}
					} else if(y2==9 && (c>=0 && c<=9)) {
						s += c;
					} else {
						var y3 = cYear.substr(2,1); 
						if(c>=0 && c<= y3)
							s += c;
					}
				}
				if(d == 3) {
					var y2 = s.substr(1,1);
					var y3 = s.substr(2,1);
					if(y2 == 8 && y3==7) {
						if(c<5) {}
						if(c>=5) s+= c;
	
					}else if(y2 == 8 && y3==8)
					{
						s +=c;
					}else if(y2 == 8 && y3==9)
					{
						s +=c;
					}
					else if(y2 == 9) {
						s += c;
					}
					else {
						
						if(varVal <= cYear) {
						
							var y4 = cYear.substr(3,1); 
							if(y4 >= 0) y4 = cYear.substr(2,2); //per Jit 2011
							y4 = parseInt(y4);
							if(c>=0 && c<= y4) {					
								var tst = s + c;
								var result = CompareDates(new Date(), new Date(tst));						
								if(result != -1)
									s += c;					
							}					
						}					
					
					}
				}			
					
				d++;
	     }
	     
	    	if(d == 4)  
	    	{
	    		break;
	    	}
	 }

 }
 else{
	 
	 if(isInteger(pTextbox.value))
		 s=pTextbox.value;
 }
 
 pTextbox.value = s;

 if(d != 4)
 {	
     return;
 }

}



function MonthYearMask(pTextbox, pKey)
{
    if(pTextbox == null)
        return;
	var cYear = new Date().getFullYear().toString();

    var varKey = 0;
    if(pKey == null){
        if(is_ie){
		if(window.event!=null)
            	varKey = window.event.keyCode;
        }
    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "/";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
    
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    y = varVal.length;

    for(x=0; x<y; x++) {

        c = varVal.substr(x, 1);
        if(isInteger(c)) {
			if(d == 0 && (c == 0 || c == 1))
				s += c;
			if(d == 1) {
				if( (s==0 && (c>= 0 && c <= 9)) || (s==1 && (c>=0 && c<3))  )
				s += c;
			}
			if(d == 2)	{
				var y1 = cYear.substr(0,1); 
				if(c >= 1 && (c <=y1))
					s += c;					
			}
			if(d == 3) {
				var y1 = s.substr(3,1);
				if(y1==1 && (c==8 || c==9))
					s += c;
				if(y1==2) {
					var y2 = cYear.substr(1,1); 
					if(c>=0 && c<= y2)
						s += c;
				}
			}
			if(d == 4) {
				var y2 = s.substr(4,1);
				if(y2==8) {
					if(c>=7 && c<=9)
						s += c;
					if(c<=6) {}
				} else if(y2==9 && (c>=0 && c<=9)) {
					s += c;
				} else {
					var y3 = cYear.substr(2,1); 
					if(c>=0 && c<= y3)
						s += c;
				}
			}
			if(d == 5) {
				var y2 = s.substr(4,1);
				var y3 = s.substr(5,1);
				
				if(y2 == 8 && y3 == 7) {
					if(c<6) {}
					if(c>=6) s+= c;
					
				}else if(y2 == 9) {
					s += c;
				} else {
					var y4 = cYear.substr(3,1); 
					if(y4 >= 0) y4 = cYear.substr(2,2); //per Jit 2011
					y4 = parseInt(y4);
					if(c>=0 && c<= y4) {					
						var tst = '01/' + s + c;
						var result = CompareDates(new Date(), new Date(tst));						
						if(result != -1)
							s += c;					
					}
				}
			}			
			
			d++;

            if(s.length == 2)
			s += z;
        }
        
       	if(d == 6)            
       	break;

    }

    pTextbox.value = s;

    if(d != 6)
    {	
        return;
    }

}

function DateMaskFuture(pTextbox, pKey, e)
{

    if(pTextbox == null)
        return;
    var varKey = 0;
    if(pKey == null){
        if(is_ie){
		if(window.event!=null)
            	varKey = window.event.keyCode;
        }else{
        	
        	varKey=e.which;
        }
    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "/";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
    
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    y = varVal.length;

    for(x=0; x<y; x++) {

        c = varVal.substr(x, 1);
        if(isInteger(c)) {
			if(d == 0 && (c == 0 || c == 1))
				s += c;
			if(d == 1) {
				if( (s==0 && (c>= 0 && c <= 9)) || (s==1 && (c>=0 && c<3))  )
				s += c;
			}
			if(d == 2 && (c>=0 && c<4))
				s += c;
			if(d == 3) {
				var d2 = s.substr(3,1);
				if(d2 == 0 && (c>=1 && c<10))	
					s += c;
				if((d2 == 1 || d2 == 2) && (c>=0 && c<10))	
					s += c;
				if(d2 == 3 && (c==0 || c==1))	
					s += c;
			}
			if(d == 4)	{
				if(c >=1)	
					s += c;					
			}
			if(d == 5) {
				var y1 = s.substr(6,1);
				if(y1==1 && (c==8 || c==9))
					s += c;
				if(y1>1) {
					s += c;
				}
			}
			if(d == 6) {
				var y2 = s.substr(7,1);
				if(y2==8) {
					if(c>=7 && c<=9)
						s += c;
					if(c<=6) {}
				} else if(y2==9 && (c>=0 && c<=9)) {
					s += c;
				} else {
					if(c>=0)
						s += c;
				}
			}
			if(d == 7) {
				var y2 = s.substr(7,1);
				var y3 = s.substr(8,1);
				if(y2 == 8 && y3 == 7) {
					if(c<6) {}
					if(c>=6) s+= c;
					
				}else if(y2 == 9) {
					s += c;
				}
				else {
					if(c>=0)
							s += c;					
				}
			}			
			
			d++;

            if(s.length == 2 || s.length == 5)
			s += z;
        }
        
       	if(d == 8)            
       	break;

    }

    pTextbox.value = s;

    if(d != 8)
    {	
        return;
    }

}

/**
 *  Moves the focus to the first field.
 */
function MoveFocusToFirstField()
{
    var e = null;
    var f = document.forms[0];
    var y = f.elements.length;
    if(y < 1)
    {
        return;
    }
    e = f.elements[0];
    MoveFocusToNextField(e);
}

/**
 *  Moves the focus to the next field.
 *  @param pField the current field.
 */
function MoveFocusToNextField(pField)
{
    //  Verify parameters.
    if(pField == null)
    {
        return;
    }
    
    //alert ("pField.type: " + pField.type);
    //  Create temp variables.
    var varID = pField.id;
    var varForm = pField.form;
    var varElement = null;
    var varElements = varForm.elements;
    var x = 0;
    var y = varElements.length;
    var z = 0;
    var s = null;
    var t = null;
    var n = null;
    var varParent = null;
    //  Make sure an ID has been set.
    if(varID == "")
    {
        return;
    }
    //  Do not move focus if there is only one field.
    if(y == 1)
    {
        return;
    }
    //  Find the current field.
    for(x=0; x<y; x++)
    {
        //  Get a field.
        varElement = varElements[x];
        //  Don't stop until you get to the current field.
        if(varElement.id == varID)
        {
            z = x;
            break;
        }
    }
    //  Abort the mission if this is the last field.
    if(z == (y-1))
    {
        return;
    }
    //  Find the next visible field after the current field.
    for(x=z+1; x<y; x++)
    {
        //  Get a field.
        varElement = varElements[x];
        //  Ignore fieldsets or anything that is not an <input> or a <select> or a <textarea>.
        n = getCorrectAttribute(varElement,"nodeName",varElement.nodeName).toLowerCase();
        //alert('nodeName: ' + n);
        if( (n != "input") && (n != "select") && (n != "textarea") )
        {
            continue;
        }
        if(n == "input")
        {
            //  Make sure it's not a hidden field or an image.
            t = getCorrectAttribute(varElement,"type",varElement.type).toLowerCase();
            
            // If the end of tab hidden field is returned, return control and do not proceed.
            if (varElement.getAttribute("name") == "endOfTab") {
                return;
            }
            
            if( (t == "hidden") || (t == "image") )
            {
                continue;
            }
            
            //check if the element is button & its parent's class is 'none', if so, then continue
            if( (t == "button")) {
     		if(varElement.parentNode.className == 'none')
            	continue;
            }
            
        }
        //  Make sure it's not disabled.
        if(varElement.disabled == true)
        {
            continue;
        }
        //  Make sure it's not hidden or invisible.
        s = varElement.className;
        //alert('varElement.className: ' + s);
        if(Contains(s, "Hidden"))
        {	
            continue;
        }
        if(Contains(s, "Invisible"))
        {
            continue;
        }
        //  Make sure the parent <td> is not hidden or invisible.
        varParent = varElement.parentNode;
        while(true)
        {
            //  Abort if never found.
            if(varParent == null)
            {
                return;
            }
            //  Proceed when found.
            if(varParent.nodeName.toLowerCase() == "td")
            {
                break;
            }
            //  Otherwise, continue searching.
            varParent =getCorrectAttribute(varParent, "parentNode",  varParent.parentNode);
        }
        s = getCorrectAttribute(varParent, "className", varParent.className);
        if(Contains(s, "Hidden"))
        {
            continue;
        }
        if(Contains(s, "Invisible"))
        {
            continue;
        }
        //  Make sure the grandparent <tr> is not hidden or invisible.
        varParent =getCorrectAttribute(varParent, "parentNode",  varParent.parentNode);
	 
        while(true)
        {
            //  Abort if never found.
            if(varParent == null)
            {
                return;
            }
            //  Proceed when found.
            if(varParent.nodeName.toLowerCase() == "tr")
            {
                break;
            }
            //  Otherwise, continue searching.
            varParent =getCorrectAttribute(varParent, "parentNode",  varParent.parentNode);
        }
        s = getCorrectAttribute(varParent, "className", varParent.className);
        if(Contains(s, "Hidden"))
        {
            continue;
        }
        if(Contains(s, "Invisible"))
        {
            continue;
        }
        //  Set focus to this field and return.
        //alert('before setting focus: ' + varElement.id + ' ' + varElement.value);      
        varElement.focus();
        break;
    }
}

/**
 *  Calculates the MMWR Year and Week from a given date string.
 *
 *  Business rules:
 *
 *  1)? The first day of any MMWR Week is Sunday.
 *
 *  2)? MMWR Week numbering is sequential beginning with 1 and incrementing
 *      with each week to a maximum of 52 or 53.
 *
 *  3)? MMWR Week #1 of an MMWR year is the first week of the year that has at
 *      least four days in the calendar year.
 *
 *  For example, if January 1 occurs on a Sunday, Monday, Tuesday or Wednesday,
 *  the calendar week that includes January 1 would be MMWR Week #1.
 *  If January 1 occurs on a Thursday, Friday, or Saturday, the calendar week
 *  that includes January 1 would be the last MMWR Week of the previous year (#52 or #53).
 *
 *  Because of this rule, December 29, 30, and 31 could potentially fall into
 *  MMWR Week #1 of the following MMWR Year.
 *
 *  Usage:
 *
 *  <script type="text/JavaScript">
 *      function id_dob_onblur()
 *      {
 *          var varDate = document.frm.id_dob.value;
 *          var varMMWR = CalcMMWR(varDate);
 *          var varYear =getElementByIdOrByName("id_year");
 *          var varWeek =getElementByIdOrByName("id_week");
 *          varYear.innerHTML = varMMWR[0];
 *          varWeek.innerHTML = varMMWR[1];
 *      }
 *  </script>
 *
 *  @param pDate a date string.
 *  @return an array containing the the MMWR Year and Week for the given date.
 */
function CalcMMWR(pDate)
{
    //  Create return variable.
    var r = new Array();
    //  Verify parameters.
    if(!isDate(pDate))
    {
        r[0] = "";
        r[1] = "";
        return r;
    }
    //  Define constants.
    var SECOND = 1000;
    var MINUTE = 60 * SECOND;
    var HOUR = 60 * MINUTE;
    var DAY = 24 * HOUR;
    var WEEK = 7 * DAY;
    //  Convert to date object.
    var varDate = new Date(pDate);
    var varTime = varDate.getTime();
    //  Get January 1st of given year.
    var varJan1Date = new Date("01/01/" + varDate.getFullYear());
    var varJan1Day = varJan1Date.getDay();
    var varJan1Time = varJan1Date.getTime();
    //  Create temp variables.
    var t = varJan1Time;
    var d = null;
    var h = 0;
    var s = "";
    //  MMWR Year.
    var y = varJan1Date.getFullYear();
    //  MMWR Week.
    var w = 0;
    //  Find first day of MMWR Year.
    if(varJan1Day < 4)
    {
        //  If SUN, MON, TUE, or WED, go back to nearest Sunday.
        t -= (varJan1Day * DAY);
        //  Loop through each week until we reach the given date.
        while(t <= varTime)
        {
            //  Increment the week counter.
            w++;
            d = new Date(t);
//          s += "a, " + w + "&nbsp;&nbsp;&nbsp;&nbsp;" + d.toString() + "<br/>";
            //  Move on to the next week.
            t += WEEK;
            //  Adjust for daylight savings time as necessary.
            d = new Date(t);
            h = d.getHours();
            if(h == 1)
            {
                t -= HOUR;
            }
            if(h == 23)
            {
                t += HOUR;
            }
        }
        //  If at end of year, move on to next year if this week has
        //  more days from next year than from this year.
        if(w == 53)
        {
            var varNextJan1Date = new Date("01/01/" + (y+1));
            var varNextJan1Day = varNextJan1Date.getDay();
            if(varNextJan1Day < 4)
            {
                y++;
                w = 1;
            }
        }
    }
    else
    {
        //  If THU, FRI, or SAT, go forward to nearest Sunday.
        t += ((7 - varJan1Day) * DAY);
        //  Loop through each week until we reach the given date.
        while(t <= varTime)
        {
            //  Increment the week counter.
            w++;
            d = new Date(t);
//          s += "b, " + w + "&nbsp;&nbsp;&nbsp;&nbsp;" + d.toString() + "<br/>";
            //  Move on to the next week.
            t += WEEK;
            //  Adjust for daylight savings time as necessary.
            d = new Date(t);
            h = d.getHours();
            if(h == 1)
            {
                t -= HOUR;
            }
            if(h == 23)
            {
                t += HOUR;
            }
        }
        //  If at beginning of year, move back to previous year if this week has
        //  more days from last year than from this year.

        if(w == 0)
        {
            d = new Date(t);
            if( (d.getMonth() == 0) && (d.getDate() <= 4) )
            {
                y--;
                var a = CalcMMWR("12/31/" + y);
                w = a[1];
            }
        }
    }
    //  Zero pad left.
    if(w < 10)
    {
        w = "0" + w;
    }
    //  Assemble result.
    r[0] = y;
    r[1] = w;
    //  Return result.
    return r;
}

/**
 *  Calculates the MMWR Year and Week from a given date string.
 *  Usage:
 *  <script type="text/JavaScript">
 *      function id_dob_onblur()
 *      {
 *          var varDate = document.frm.id_dob.value;
 *          var varMMWR = CalcMMWR(varDate);
 *          var varYear =getElementByIdOrByName("id_year");
 *          var varWeek =getElementByIdOrByName("id_week");
 *          varYear.innerHTML = varMMWR[0];
 *          varWeek.innerHTML = varMMWR[1];
 *      }
 *  </script>
 *  @param pDate a date string.
 *  @return an array containing the the MMWR Year and Week for the given date.
 */
function CalcMMWR2_Temp_DoNotUse(pDate)
{
    //  Create return variable.
    var r = new Array();
    //  Verify parameters.
    if(!isDate(pDate))
    {
        r[0] = "";
        r[1] = "";
        return r;
    }
    //  Convert to date object.
    var varDate = new Date(pDate);
    var varDateString = DateToString(varDate);
    var varDateDD = varDate.getDate();
    var varDateYY = varDate.getFullYear();
    var varDateDOY = DateToDOY(varDateString);
    //  Get day of year.
    var varDOY = DateToDOY(pDate);
    //  Find January 1st of given year.
    var varJan1String = "01/01/" + varDateYY;
    var varJan1Date = new Date(varJan1String);
    var varJan1Day = varJan1Date.getDay();
    var varJan1DOY = DateToDOY(varJan1String);
    //  Get number of weeks between JAN 1 and the given date.
    var varWeeks = Math.floor(varDateDOY / 7);
    //  Create temp variables.
    var y = varDateYY;
    var w = varWeeks + 1;
    //  Adjustments for JAN.
    if( (varWeeks == 0) && (varJan1Day > 3) && (varDateDD < 4) )
    {
        y = varDateYY - 1;
        var a = CalcMMWR2("12/31/" + r[0]);
        w = a[1];
    }
    //  Adjustments for DEC.
    if(varWeeks == 53)
    {
        var varNextJan1Date = new Date("01/01/" + (varDateYY+1));
        var varNextJan1Day = varNextJan1Date.getDay();
        if(varNextJan1Day < 4)
        {
            y++;
            w = 1;
        }
    }
    //  Return result.
    r[0] = y;
    r[1] = w;
    return r;
}

/**
 *  Gets the MMWR weeks for a year.
 *  @param pYear the year to get a list of weeks for.
 *  @return an array of weeks.
 *  The array will have 54 elements.
 *  The first element will always be an array of 2 empty strings.
 *  If the year only has 52 weeks, then the last element will be an array of 2 empty strings.
 *  If an error occurs, all elements will be an array of 2 empty strings.
 *  When copying data from this array to <option> elements,
 *  skip all elements of the array that contain an array of 2 empty strings.
 */
function getMMWRWeeks(pYear)
{
    //  Create return variable.
    var r = new Array(54);
    //  Initialize the array.
    var rx = 0;
    for(rx=0; rx<54; rx++)
    {
        var aTemp = ["", ""];
        r[rx] = aTemp;
    }
    //  Verify parameters.
    if(pYear == null)
    {
        return r;
    }
    if(!isInteger(pYear))
    {
        return r;
    }
    //  Define constants.
    var SECOND = 1000;
    var MINUTE = 60 * SECOND;
    var HOUR = 60 * MINUTE;
    var DAY = 24 * HOUR;
    var SIX_DAYS = 6 * DAY;
    var WEEK = 7 * DAY;
    //  Convert to date object.
    var varDate = new Date("12/31/" + pYear);
    var varTime = varDate.getTime();
    var varDay = varDate.getDay();
    //  Get January 1st of given year.
    var varJan1Date = new Date("01/01/" + varDate.getFullYear());
    var varJan1Day = varJan1Date.getDay();
    var varJan1Time = varJan1Date.getTime();
    //  Create temp variables.
    var t = varJan1Time;
    var tSAT = 0;
    var d = null;
    var h = 0;
    var s = "";
    var wTemp = "";
    //  MMWR Year.
    var y = varJan1Date.getFullYear();
    //  MMWR Week.
    var w = 0;
    //  Find first day of MMWR Year.
    if(varJan1Day < 4)
    {
        //  If SUN, MON, TUE, or WED, go back to nearest Sunday.
        t -= (varJan1Day * DAY);
    }
    else
    {
        //  If THU, FRI, or SAT, go forward to nearest Sunday.
        t += ((7 - varJan1Day) * DAY);
    }
    //  Add each week until we get to the end of the year.
    while(t <= varTime)
    {
        //  Increment the week counter.
        w++;
        //  Assemble week number, Sunday, and Saturday.
        d = new Date(t);
        s = "";
        if(w < 10)
        {
            s += "0";
        }
        s += w + " (" + DateToString(d);
        tSAT = t + SIX_DAYS;
        //  Adjust for daylight savings time as necessary.
        d = new Date(tSAT);
        h = d.getHours();
        if(h == 1)
        {
            tSAT -= HOUR;
        }
        if(h == 23)
        {
            tSAT += HOUR;
        }
        d = new Date(tSAT);
        s += " - " + DateToString(d) + ")";
        //  Move on to the next week.
        t += WEEK;
        //  Adjust for daylight savings time as necessary.
        d = new Date(t);
        h = d.getHours();
        if(h == 1)
        {
            t -= HOUR;
        }
        if(h == 23)
        {
            t += HOUR;
        }
        //  Rule #4.
        if( (w == 53) && (varDay < 3) )
        {
            break;
        }
        //  Zero pad left.
        wTemp = (w < 10) ? ("0" + w) : w;
        //  Add it to the result set.
        var a = [wTemp, s];
        r[w] = a;
    }
    //  Return result.
    return r;
}

/**
 *  Gets the Saturday for the given MMWR.
 *  @param pYear the year to calculate from.
 *  @param pWeek the week to calculate from.
 *  @return a date string.
 */
function getSaturdayForMMWR(pYear, pWeek)
{
    //  Create return variable.
    var s = "";
    //  Start calculating.
    var aWeeks = getMMWRWeeks(pYear);
    //  Create temp variables.
    var varLen = aWeeks.length;
    var varWeek = new Number(pWeek);
    //  Verify parameters.
    if(varLen == 0)
    {
        return s;
    }
    if(varWeek < 0)
    {
        return s;
    }
    if(varWeek > (varLen-1))
    {
        return s;
    }
    //  Get the week.
    var aWeek = aWeeks[varWeek];
    //  Parse the result.
    var aString = aWeek[1];
    s = aString.substr(17, 10);
    //  Return result.
    return s;
}

/**
 *  Gets the Sunday for the given MMWR.
 *  @param pYear the year to calculate from.
 *  @param pWeek the week to calculate from.
 *  @return a date string.
 */
function getSundayForMMWR(pYear, pWeek)
{
    //  Create return variable.
    var s = "";
    //  Start calculating.
    var aWeeks = getMMWRWeeks(pYear);
    //  Create temp variables.
    var varLen = aWeeks.length;
    var varWeek = new Number(pWeek);
    //  Verify parameters.
    if(varLen == 0)
    {
        return s;
    }
    if(varWeek < 0)
    {
        return s;
    }
    if(varWeek > (varLen-1))
    {
        return s;
    }
    //  Get the week.
    var aWeek = aWeeks[varWeek];
    //  Parse the result.
    var aString = aWeek[1];
    s = aString.substr(4, 10);
    //  Return result.
    return s;
}

function doHyperlinkSameWindow(pURL)
{
    window.location.href = pURL;
}

function doHyperlinkNewWindow(pURL)
{
    var varOptions = "toolbar=1,location=1,directories=1,status=1,menubar=1,scrollbars=1,resizable=1,copyhistory=1";
    window.open(pURL, "nedss", varOptions);
}

/**
 *  Creates an opening tag for XML output.
 *  @param pName the name of the tag to open.
 *  @return a string containing the opening tag.
 *  The angle brackets are escaped as hex values
 *  to allow for XML tags within text nodes.
 */
function OpenTag(pName)
{
    var s = "";
    s += "\x3C";
    s += pName;
    s += "\x3E";
    return s;
}

/**
 *  Creates a closing tag for XML output.
 *  @param pName the name of the tag to close.
 *  @return a string containing the closing tag.
 *  The angle brackets are escaped as hex values
 *  to allow for XML tags within text nodes.
 */
function CloseTag(pName)
{
    var s = "";
    s += "\x3C";
    s += "/" + pName;
    s += "\x3E";
    return s;
}

/**
 *  Encodes a string that may contain special characters that are reserved in XML.
 *  The JavaScript escape() function does the same job, but it is deprecated.
 *  The JavaScript encodeURI() function replaces escape(), but it does not handle
 *  all of the characters that escape() did.
 *  Only characters that affect XML well-formedness will be encoded.
 *  The percent sign is encoded, too, because it is part of the encoding scheme.
 *  @param pString the string to encode.
 *  @return an encoded version of the string.
 *  @see xmlDecode(String)
 */
function xmlEncode(pString)
{
    //  Verify parameters.
    if(pString == null)
    {
        return "";
    }
    //  Create return value.
    var s = "";
    //  Create temp variables.
    var c = 0;
    //  Create variables for looping.
    var x = 0;
    var y = pString.length;
    //  Loop through all of the characters in the string.
    for(x=0; x<y; x++)
    {
        //  Get a character.
        c = pString.charCodeAt(x);
        switch(c)
        {
            //  Translate it if it is a reserved character.
            case 0x22:  s += "%22"; break;  //  "   double quote
            case 0x25:  s += "%25"; break;  //  %   percent sign
            case 0x26:  s += "%26"; break;  //  &   ampersand
            case 0x27:  s += "%27"; break;  //  '   single quote
            case 0x3C:  s += "%3C"; break;  //  <   left angle bracket
            case 0x3E:  s += "%3E"; break;  //  >   right angle bracket
            //  Otherwise, pass it on as-is.
            default:    s += String.fromCharCode(c);    break;  //  all other characters
        }
    }
    //  Return the result.
    return s;
}

/**
 *  Decodes a string that may contain special characters that are reserved in XML.
 *  The JavaScript unescape() function does the same job, but it is deprecated.
 *  The JavaScript decodeURI() function replaces unescape(), but it does not handle
 *  all of the characters that unescape() did.
 *  Only character references that affect XML well-formedness will be decoded.
 *  The percent sign is decoded, too, because it is part of the encoding scheme.
 *  @param pString the string to decode.
 *  @return the decoded version of the string.
 *  @see xmlEncode(String)
 */
function xmlDecode(pString)
{
    //  Verify parameters.
    if(pString == null)
    {
        return "";
    }
    //  Create return value.
    var s = "";
    //  Create temp variables.
    var c = 0;
    var ss = "";
    //  Create variables for looping.
    var x = 0;
    var y = pString.length;
    //  Loop through all of the characters in the string.
    for(x=0; x<y;)
    {
        //  Get a character.
        c = pString.charCodeAt(x);
        if(c == 37)
        {
            //  If it is the percent sign,
            //  decode the following two characters at once.
            ss = "0x" + pString.substr(x+1, 2);
            //  Convert them to an integer and append the character code equivalent.
            c = new Number(ss);
            s += String.fromCharCode(c);
            //  Skip the sign and the 2-digit code.
            x += 3;
        }
        else
        {
            //  If it is not the percent sign, then pass it on as-is.
            s += String.fromCharCode(c);
            //  Skip past the character.
            x += 1;
        }
    }
    //  Return the result.
    return s;
}

/**
 *  Encodes a string that may contain special characters that are reserved in XML.
 *  Encodes into a format suitable for writing into an attribute.
 *  Only characters that affect XML well-formedness will be encoded.
 *  @param pString the string to encode.
 *  @return an encoded version of the string.
 */
function xmlEncodeToXHTML(pString)
{
    //  Verify parameters.
    if(pString == null)
    {
        return "";
    }
    //  Create return value.
    var s = "";
    //  Create temp variables.
    var c = 0;
    //  Create variables for looping.
    var x = 0;
    var y = pString.length;
    //  Loop through all of the characters in the string.
    for(x=0; x<y; x++)
    {
        //  Get a character.
        c = pString.charCodeAt(x);
        switch(c)
        {
            //  Translate it if it is a reserved character.
            case 0x22:  s += "&quot;";  break;  //  "   double quote
            case 0x26:  s += "&amp;";   break;  //  &   ampersand
            case 0x27:  s += "&apos;";  break;  //  '   single quote
            case 0x3C:  s += "&lt;";    break;  //  <   left angle bracket
            case 0x3E:  s += "&gt;";    break;  //  >   right angle bracket
            //  Otherwise, pass it on as-is.
            default:    s += String.fromCharCode(c);    break;  //  all other characters
        }
    }
    //  Return the result.
    return s;
}

/**
 *  Encodes a string that may contain special characters that are reserved in XML.
 *  Encodes into a format suitable for writing into a JavaScript String.
 *  Only characters that affect XML well-formedness will be encoded.
 *  @param pString the string to encode.
 *  @return an encoded version of the string.
 */
function xmlEncodeToJavaScript(pString)
{
    //  Verify parameters.
    if(pString == null)
    {
        return "";
    }
    //  Create return value.
    var s = "";
    //  Create temp variables.
    var c = 0;
    //  Create variables for looping.
    var x = 0;
    var y = pString.length;
    //  Loop through all of the characters in the string.
    for(x=0; x<y; x++)
    {
        //  Get a character.
        c = pString.charCodeAt(x);
        switch(c)
        {
            //  Translate it if it is a reserved character.
            case 0x22:  s += "\u0022";  break;  //  "   double quote
            case 0x26:  s += "\u0026";  break;  //  &   ampersand
            case 0x27:  s += "\u0027";  break;  //  '   single quote
            case 0x3C:  s += "\u003C";  break;  //  <   left angle bracket
            case 0x3E:  s += "\u003E";  break;  //  >   right angle bracket
            //  Otherwise, pass it on as-is.
            default:    s += String.fromCharCode(c);    break;  //  all other characters
        }
    }
    //  Return the result.
    return s;
}

/**
 *  Encodes a string for use in a URL.
 *  Similar to xmlEncode(String)
 *  but this one encodes spaces and question marks too.
 *  @param pString the string to encode.
 *  @return an encoded version of the string.
 *  @see urlDecode(String)
 */
function urlEncode(pString)
{
    //  Verify parameters.
    if(pString == null)
    {
        return "";
    }
    //  Create return value.
    var s = "";
    //  Create temp variables.
    var c = 0;
    //  Create variables for looping.
    var x = 0;
    var y = pString.length;
    //  Loop through all of the characters in the string.
    for(x=0; x<y; x++)
    {
        //  Get a character.
        c = pString.charCodeAt(x);
        switch(c)
        {
            //  Translate it if it is a reserved character.
            case 0x20:  s += "%20"; break;  //      space
            case 0x22:  s += "%22"; break;  //  "   double quote
            case 0x25:  s += "%25"; break;  //  %   percent sign
            case 0x26:  s += "%26"; break;  //  &   ampersand
            case 0x27:  s += "%27"; break;  //  '   single quote
            case 0x3C:  s += "%3C"; break;  //  <   left angle bracket
            case 0x3E:  s += "%3E"; break;  //  >   right angle bracket
            case 0x3F:  s += "%3F"; break;  //  ?   question mark
            //  Otherwise, pass it on as-is.
            default:    s += String.fromCharCode(c);    break;  //  all other characters
        }
    }
    //  Return the result.
    return s;
}

/**
 *  Decodes a string from a URL.
 *  Just a thing wrapper for xmlDecode(String).
 *  @param pString the string to decode.
 *  @return the decoded version of the string.
 *  @see urlEncode(String)
 */
function urlDecode(pString)
{
    return xmlDecode(pString);
}

/**
 *  Parses an XML string.
 *  @param pXML an XML string having the following format.
    <table>
        <record>
            <field>value</field>
            <field>value</field>
            <field>value</field>
        </record>
        <record>
            <field>value</field>
            <field>value</field>
            <field>value</field>
        </record>
    </table>
 *  @return an array of arrays.
 */
function ParseTable(pXML)
{
    //  Create return variable.
    var aTable = new Array();
    //  Verify parameters.
    if(pXML == null)
    {
        return;
    }
    //  Create temp variables.
    var xRecord = 0;
    var yRecord = 0;
    var xField = 0;
    var yField = 0;
    var sTable = new String(pXML);
    var sRecord = null;
    //  Loop through all records in the table.
    var y = 0;
    while(true)
    {
        //  Search for a record.
        xRecord = sTable.indexOf("<record>");
        yRecord = sTable.indexOf("</record>");
        //  Stop when you can't find any more records.
        if(xRecord == -1)
        {
            break;
        }
        if(yRecord == -1)
        {
            break;
        }
        //  Isolate the fields within the record.
        var aRecord = new Array();
        xRecord += 8;
        sRecord = sTable.substr(xRecord, yRecord);
        //  Loop through all fields in the record.
        var x = 0;
        while(true)
        {
            //  Search for a field.
            xField = sRecord.indexOf("<field>");
            yField = sRecord.indexOf("</field>");
            //  Stop when you can't find any more fields.
            if(xField == -1)
            {
                break;
            }
            if(yField == -1)
            {
                break;
            }
            //  Also stop if you've gone past the current record.
            if(xField > yRecord)
            {
                break;
            }
            //  Isolate the field's value.
            xField += 7;
            var sField = sRecord.substring(xField, yField);
            //  Save it.
            aRecord[x++] = sField;
            //  Move on to the next field.
            yField += 8;
            sRecord = sRecord.substr(yField, yRecord);
        }
        //  Save the record.
        aTable[y++] = aRecord;
        //  Move on to the next record.
        yRecord += 9;
        sTable = sTable.substr(yRecord, sTable.length);
    }
    //  Return result.
    return aTable;
}

/**
 *  Removes all child nodes that are not attributes.
 *  @param pNode Susan Smith.
 */
function RemoveAllChildNodesExceptAttributes(pNode)
{
    //  Verify parameters.
    if(pNode == null)
    {
        return;
    }
    //  Get all child nodes.
    var nl = pNode.childNodes;
    //  Create temp variables.
    var x = 0;
    var y = nl.length;
    var n = null;
    var t = null;
    //  Loop through the collection.
    for(x=y-1; x>=0; x--)
    {
        //  Get a node.
        n = nl.item(x);
        //  If it's an attribute, skip it.
        t = n.nodeType;
        if(t == ATTRIBUTE_NODE)
        {
            continue;
        }
        //  Remove it.
        pNode.removeChild(n);
    }
}

/**
 *  Sorts an array in ascending order.
 *  @param a the first element to compare.
 *  @param b the second element to compare.
 *  @return 0, 1, or -1.
 */
function SortAscending(a, b)
{
    if(a < b)
    {
        return -1;
    }
    if(a > b)
    {
        return 1;
    }
    return 0;
}

/**
 *  Sorts an array in descending order.
 *  @param a the first element to compare.
 *  @param b the second element to compare.
 *  @return 0, 1, or -1.
 */
function SortDescending(a, b)
{
    if(a < b)
    {
        return 1;
    }
    if(a > b)
    {
        return -1;
    }
    return 0;
}

/**
 *  Sorts an array of dates (in long integer format) in ascending order, ignoring the time.
 *  @param a the first element to compare.
 *  @param b the second element to compare.
 *  @return 0, 1, or -1.
 */
function SortDatesAscending(a, b)
{
    //  Convert numbers to dates.
    var da = new Date(a);
    var db = new Date(b);
    //  Convert to sortable string format.
    var sa = DateToSortableString(da);
    var sb = DateToSortableString(db);
    //  Sort them.
    var z  = SortAscending(sa, sb);
    //  Return the result.
    return z;
}

/**
 *  Sorts an array of dates (in long integer format) in descending order, ignoring the time.
 *  @param a the first element to compare.
 *  @param b the second element to compare.
 *  @return 0, 1, or -1.
 */
function SortDatesDescending(a, b)
{
    //  Convert numbers to dates.
    var da = new Date(a);
    var db = new Date(b);
    //  Convert to sortable string format.
    var sa = DateToSortableString(da);
    var sb = DateToSortableString(db);
    //  Sort them.
    var z  = SortDescending(sa, sb);
    //  Return the result.
    return z;
}

/**
 *  Determines whether pSubstring is a substring of pString.
 *  @param pString the string to look in.
 *  @param pSubstring the string to look for.
 *  @return true or false.
 */
function Contains(pString, pSubstring)
{
    //  Verify parameters.
    if(pString == null)
    {
        return false;
    }
    if(pSubstring == null)
    {
        return false;
    }
    if(pString == "")
    {
        return false;
    }
    if(pSubstring == "")
    {
        return false;
    }
    //  Search for substring.
    var x = pString.indexOf(pSubstring);
    //  Return result.
    if(x == -1)
    {
        return false;
    }
    return true;
}

/**
 *  Global variable for getElementsWhereIdContains.
 */
var aElementsContains = new Array();

/**
 *  Global variable for getElementsWhereIdContains.
 */
var aElementsContainsCount = 0;

/**
 *  Gets an array of elements where the ID contains the given pattern.
 *  @param pID the pattern to search for.
 *  @return an array of elements.
 *  The array will be empty if no elements were found.
 */
function getElementsWhereIdContains(pID)
{
    //  Create return variable.
    aElementsContains = new Array();
    //  Reset the count.
    aElementsContainsCount = 0;
    //  Verify paramters.
    if(pID == null)
    {
        return aElementsContains;
    }
    //  Start with the document element.
    var de = document.documentElement;
    //  Get his children.
    var nl = de.childNodes;
    //  And work on them.
    getElementsWhereIdContains_Recursive(pID, nl);
    //  Return result.
    return aElementsContains;
}

/**
 *  Helper function for getElementsWhereIdContains.
 *  Calls itself recursively until all nodes have been traversed.
 *  Do not call this directly.
 *  @param pID the pattern to search for.
 *  @param pNodeList the NodeList to search.
 */
function getElementsWhereIdContains_Recursive(pID, pNodeList)
{
    //  Verify parameters.
    if(pID == null)
    {
        return;
    }
    if(pNodeList == null)
    {
        return;
    }
    //  Create temp variables.
    var x = 0;
    var y = pNodeList.length;
    var i = null;
    //  Loop through the collection.
    for(x=0; x<y; x++)
    {
        //  Get a node.
        var n = pNodeList.item(x);
        //  Only work with element nodes.
        if(n.nodeType != ELEMENT_NODE)
        {
            continue;
        }
        //  Get its ID.
        i = n.getAttribute("id");
        //  See if it contains the pattern we're looking for.
        if(Contains(i, pID))
        {
            //  If it does, add it to the result set.
            aElementsContains[aElementsContainsCount++] = n;
        }
        //  Get his children.
        var nl = n.childNodes;
        //  And work on them.
        getElementsWhereIdContains_Recursive(pID, nl);
    }
}

/**
 *  Determines whether pStrings starts with pSubstring.
 *  @param pString the string to look in.
 *  @param pSubstring the string to look for.
 *  @return true or false.
 */
function StartsWith(pString, pSubstring)
{
    //  Create return variable.
    var b = false;
    //  Verify parameters.
    if(pString == null)
    {
        return b;
    }
    if(pSubstring == null)
    {
        return b;
    }
    if(pString == "")
    {
        return false;
    }
    if(pSubstring == "")
    {
        return false;
    }
    var varStrLen = pString.length;
    var varSubLen = pSubstring.length;
    if(varSubLen > varStrLen)
    {
        return b;
    }
    //  Extract substring.
    var s = pString.substr(0, varSubLen);
    //  Compare to the given substring.
    if(s == pSubstring)
    {
        b = true;
    }
    //  Return result.
    return b;
}

/**
 *  Global variable for getElementsWhereIdStartsWith.
 */
var aElementsStartsWith = new Array();

/**
 *  Global variable for getElementsWhereIdStartsWith.
 */
var aElementsStartsWithCount = 0;

/**
 *  Gets an array of elements where the ID starts with the given pattern.
 *  @param pID the pattern to search for.
 *  @return an array of elements.
 *  The array will be empty if no elements were found.
 */
function getElementsWhereIdStartsWith(pID)
{
    //  Create return variable.
    aElementsStartsWith = new Array();
    //  Reset the count.
    aElementsStartsWithCount = 0;
    //  Verify paramters.
    if(pID == null)
    {
        return aElementsStartsWith;
    }
    //  Start with the document element.
    var de = document.documentElement;
    //  Get his children.
    var nl = de.childNodes;
    //  And work on them.
    getElementsWhereIdStartsWith_Recursive(pID, nl);
    //  Return result.
    return aElementsStartsWith;
}

/**
 *  Helper function for getElementsWhereIdStartsWith.
 *  Calls itself recursively until all nodes have been traversed.
 *  Do not call this directly.
 *  @param pID the pattern to search for.
 *  @param pNodeList the NodeList to search.
 */
function getElementsWhereIdStartsWith_Recursive(pID, pNodeList)
{
    //  Verify parameters.
    if(pID == null)
    {
        return;
    }
    if(pNodeList == null)
    {
        return;
    }
    //  Create temp variables.
    var x = 0;
    var y = pNodeList.length;
    var i = null;
    //  Loop through the collection.
    for(x=0; x<y; x++)
    {
        //  Get a node.
        var n = pNodeList.item(x);
        //  Only work with element nodes.
        if(n.nodeType != ELEMENT_NODE)
        {
            continue;
        }
        //  Get its ID.
        i = n.getAttribute("id");
        //  See if it starts with the pattern we're looking for.
        if(StartsWith(i, pID))
        {
            //  If it does, add it to the result set.
            aElementsStartsWith[aElementsStartsWithCount++] = n;
        }
        //  Get his children.
        var nl = n.childNodes;
        //  And work on them.
        getElementsWhereIdStartsWith_Recursive(pID, nl);
    }
}

/**
 *  Makes all elements visible where the ID starts with the given pattern.
 *  @param pID the pattern to search for.
 */
function MakeAllElementsVisibleWhereIdStartsWith(pID)
{
    var a = getElementsWhereIdStartsWith(pID);
    var e = null;
    var x = 0;
    var y = a.length;
    for(x=0; x<y; x++)
    {
        e = a[x];
        MakeElementVisible(e);
    }
}

/**
 *  Makes all elements invisible where the ID starts with the given pattern.
 *  @param pID the pattern to search for.
 */
function MakeAllElementsInvisibleWhereIdStartsWith(pID)
{
    var a = getElementsWhereIdStartsWith(pID);
    var e = null;
    var x = 0;
    var y = a.length;
    for(x=0; x<y; x++)
    {
        e = a[x];
        MakeElementInvisible(e);
    }
}
/**
 *  Makes all elements hidden where the ID starts with the given pattern.
 *  @param pID the pattern to search for.
 */
function MakeAllElementsHiddenWhereIdStartsWith(pID)
{
    var a = getElementsWhereIdStartsWith(pID);
    var e = null;
    var x = 0;
    var y = a.length;
    for(x=0; x<y; x++)
    {
        e = a[x];
        MakeElementHidden(e);
    }
}

/*
    The next 3 variables are declared here, but will be
    initialized by SessionTimer.xsl when you include
    this tag inside the <head> element in your XSP files:
    <nedss:InitializeSessionTimer frequency="1"/>
*/

/**
 *  The session timeout setting as specified in server.xml or web.xml.
 *  Expressed here in milliseconds.
 */
var varSessionTimeoutSetting = 0;

/**
 *  The time when the session will expire.
 *  Expressed here in milliseconds.
 */
var varSessionTimeoutTime = 0;

/**
 *  How often to update the statusbar.
 *  Expressed here in seconds.
 */
var varSessionTimerInterval = 1;

/**
 *  True to display the session timer or false to hide it.
 *  Only needed for beta testing.
 */
var varSessionTimerDisplayFlag = false;

/**
 *  The ID used to track timer events.
 *  Used here to start and stop the timer.
 */
var varSessionTimerIntervalID  = 0;

/**
 *  Make the timer events occur once every second.
 */
var varSessionTimerResolution  = 1000;

/**
 *  Calculates the current amount of time remaining before the session expires
 *  and writes status information to the statusbar.
 */
function UpdateSessionTimer()
{
    //  Another timer event has occured.
    //  Keep counting down until there is no more time remaining.
    varSessionTimeoutTime -= varSessionTimerResolution;
    //  Don't let the countdown timer go below zero.
    if(varSessionTimeoutTime < 0)
    {
        varSessionTimeoutTime = 0;
    }
    //  Compute time remaining.
    var varSeconds = Math.floor(varSessionTimeoutTime / 1000);
    //  How many minutes do we have left?
    var varMinutes = Math.floor(varSeconds / 60);
    //  How many seconds do we have left?
    varSeconds -= (varMinutes * 60);
    //  Assemble a string to display in the status bar.
    var s = "";
    s += "Session Time Remaining:  ";
    if(!(varMinutes > 9))
    {
        s += "0";
    }
    s += varMinutes;
    s += ":";
    if(!(varSeconds > 9))
    {
        s += "0";
    }
    s += varSeconds;
    //  If we can display status...
    if(varSessionTimerDisplayFlag == true)
    {
        //  and if we are at an interval when the user wants to be notified...
        if((varSeconds % varSessionTimerInterval) == 0)
        {
            //  then display the status.
            window.status = s;
        }
    }
}

/**
 *  Resets the session timer.
 *  Use this when opening a new window or submitting a form in another frame or an iframe
 *  to keep the current window's session timer in sync with the actual session on the server.
 */
function ResetSessionTimer()
{
    varSessionTimeoutLength = varSessionTimeoutSetting;
}

/**
 *  Enables or disables the output of the session timer.
 *  The timer keeps running until you call StopSessionTimer().
 */
function ToggleSessionTimer()
{
    //  Flip the flag.
    varSessionTimerDisplayFlag = !varSessionTimerDisplayFlag;
    //  Clear the status bar when disabling output.
    if(varSessionTimerDisplayFlag == false)
    {
        window.status = "";
    }
}

/**
 *  Starts the session timer.
 */
function StartSessionTimer()
{
    varSessionTimerIntervalID = window.setInterval(UpdateSessionTimer, varSessionTimerResolution);
}

/**
 *  Stops the session timer.
 */
function StopSessionTimer()
{
    window.clearInterval(varSessionTimerIntervalID);
}

/**
 *  Checks to see if the special key has been pressed
 *  that will reveal the session timer.
 *  @param _this for NN, it is the event; for IE, it is the object that had the event.
 */
function CheckSessionTimer(_this)
{
    var x = 0;
    if(is_ie)
    {
        x = window.event.keyCode;
    }
    else
    {
        x = _this.which;
    }
    if(x == 19)
    {
        ToggleSessionTimer();
    }
}

/**
 *  Creates a new form.
 *  Use CreateAgent and ImportFormElements
 *  when you need to submit a form more than once.
 *  @param pID the id of the form.
 *  @return true if the agent was created or false if not.
 */
function CreateAgent(pID)
{
    //  Verify Parameters.
    if(pID == null)
    {
        return false;
    }
    //  Make sure this agent doesn't already exist.
    var varTest = getElementByIdOrByName(pID);
    if(varTest != null)
    {
        return false;
    }
    //  Create the form.
    var eForm = document.createElement("form");
    var varID = new String(pID);
    var varName = new String(pID);
    eForm.setAttribute("id", varID);
    eForm.setAttribute("name", varName);
    //  Get the body element.
    var nl = document.getElementsByTagName("body");
    var eBody = nl.item(0);
    //  Add the form to the end of the body, after all other forms.
    eBody.appendChild(eForm);
    //  Return.
    return true;
}

/**
 *  Copies form elements from one form to another.
 *  Use CreateAgent and ImportFormElements
 *  when you need to submit a form more than once.
 *  The names will be the same, but the IDs will
 *  have the new form name prepended.
 *  For example, if a field's old ID is "id_Username",
 *  then it's new ID will be "frmSecretAgent_id_Username".
 *  @param pFrom the ID of the old form.
 *  @param pTo the ID of the new form.
 *  NOTE:  The multiple attribute for <select> elements
 *  doesn't behave correctly in IE,
 *  so call this method for NN, but not IE.
 */
function ImportFormElements(pFrom, pTo)
{
    //  Verify parameters.
    if(pFrom == null)
    {
        return;
    }
    if(pTo == null)
    {
        return;
    }
    var varFrom = getElementByIdOrByName(pFrom);
    var varTo = getElementByIdOrByName(pTo);
    if(varFrom == null)
    {
        return;
    }
    if(varTo == null)
    {
        return;
    }
    if(varFrom.nodeName.toLowerCase() != "form")
    {
        return;
    }
    if(varTo.nodeName.toLowerCase() != "form")
    {
        return;
    }
    //  Make sure the new form is empty.
    RemoveAllChildNodesExceptAttributes(varTo);
    //  Create a table to hold the new elements.
    var eTable = document.createElement("table");
    //  Create temp variables.
    var varElement = null;
    var varElements = varFrom.elements;
    var varNodeName = null;
    var varInputType = null;
    var s = null;
    var x = 0;
    var y = varElements.length;
    var z = 0;
    //  Loop through all input elements.
    for(x=0; x<y; x++)
    {
        //  Get a field.
        varElement = varElements[x];
        //  Ignore fieldsets or anything that is not an <input> or a <select> or a <textarea>.
        varNodeName = getCorrectAttribute(varElement,"nodeName",varElement.nodeName).toLowerCase();
        if( (varNodeName != "input") && (varNodeName != "select") && (varNodeName != "textarea") )
        {
            continue;
        }
        //  Copy input elements from the old form to the new one.
        if(varNodeName == "input")
        {
            var varID = varElement.getAttribute("id");
            var varName = varElement.getAttribute("name");
            s = (varNodeName == "hidden") ? "text" : varNodeName;
            var eElement = document.createElement(s);
            s = pTo + "_" + varID;
            eElement.setAttribute("id", s);
            eElement.setAttribute("name", varName);
            var varValue = varElement.value;
            if(varValue != "")
            {
                eElement.setAttribute("value", varValue);
            }
            var t = getCorrectAttribute(varElement,"type",varElement.type);
            if( (t == "checkbox") || (t == "radio") )
            {
                if(getCorrectAttribute(varElement, "checked",varElement.checked) == true)
                {
                    eElement.setAttribute("checked", "checked");
                }
            }
            var eTD = document.createElement("td");
            eTD.setAttribute("class", "Invisible");
            eTD.appendChild(eElement);
            var eTR = document.createElement("tr");
            eTR.appendChild(eTD);
            eTable.appendChild(eTR);
            z++;
        }
        if(varNodeName == "select")
        {
            var varID = varElement.getAttribute("id");
            var varName = varElement.getAttribute("name");
            var eElement = document.createElement(varNodeName);
            s = pTo + "_" + varID;
            eElement.setAttribute("id", s);
            eElement.setAttribute("name", varName);
            s = varElement.getAttribute("size");
            if(s == "")
            {
                s = "1";
            }
            eElement.setAttribute("size", s);
            var n = new Number(s);
            if(n > 1)
            {
                eElement.setAttribute("multiple", "multiple");
            }
            var varOptions = varElement.options;
            var o = null;
            var oValue = null;
            var oText = null;
            var oSelected = false;
            var ox = 0;
            var oy = varOptions.length;
            for(ox=0; ox<oy; ox++)
            {
                o = varOptions[ox];
                oValue = o.value;
                oText = o.text;
                oSelected = o.selected;
                var eOption = document.createElement("option");
                eOption.setAttribute("value", oValue);
                if(oSelected == true)
                {
                    eOption.setAttribute("selected", "selected");
                }
                var t = document.createTextNode(oText);
                eOption.appendChild(t);
                eElement.appendChild(eOption);
            }
            var eTD = document.createElement("td");
            eTD.setAttribute("class", "Invisible");
            eTD.appendChild(eElement);
            var eTR = document.createElement("tr");
            eTR.appendChild(eTD);
            eTable.appendChild(eTR);
            z++;
        }
        if(varNodeName == "textarea")
        {
            var varID = varElement.getAttribute("id");
            var varName = varElement.getAttribute("name");
            var varValue = varElement.value;
            var eElement = document.createElement(varNodeName);
            s = pTo + "_" + varID;
            eElement.setAttribute("id", s);
            eElement.setAttribute("name", varName);
            if(varValue != "")
            {
                var t = document.createTextNode(varValue);
                eElement.appendChild(t);
            }
            var eTD = document.createElement("td");
            eTD.setAttribute("class", "Invisible");
            eTD.appendChild(eElement);
            var eTR = document.createElement("tr");
            eTR.appendChild(eTD);
            eTable.appendChild(eTR);
            z++;
        }
    }
    if(z == 0)
    {
        //  If there are no input elements, at least
        //  create an empty table that will be valid XHTML.
        var eTD = document.createElement("td");
        var eTR = document.createElement("tr");
        eTR.appendChild(eTD);
        eTable.appendChild(eTR);
    }
    //  Add the table to the form.
    varTo.appendChild(eTable);
}

/**
 *  Submits the form whenever the Enter key is pressed.
 *  Call this from the onkeypress event of a textbox.
 *  @param _this for NN, it is the event; for IE, it is the object that had the event.
 *  @param pURL the URL to post to or null for the default.
 */
function SubmitOnKeyPressEnter(_this, pURL)
{
    var x = 0;
    var varURL = (pURL == null) ? "/nbs/nfc" : pURL;
    if(is_ie)
    {
        x = window.event.keyCode;
    }
    else
    {
        x = _this.which;
    }
    if(x == 13)
    {
        post(varURL);
    }
}

/**
 *  Posts the form.
 *  @param pURL the URL to post to or null for the default.
 *  @param pWindow the window to post in or null for the current window.
 */
function post(pURL, pWindow)
{
    //  Setup default parameters.
    var varURL = (pURL == null) ? "/nbs/nfc" : pURL;
    var varWin = (pWindow == null) ? "_self" : pWindow;
    //  Post the form.
    document.frm.action = varURL;
    document.frm.method = "post";
    document.frm.target = varWin;
    document.frm.submit();
}

/**
 *  Posts the form to the debugger in a new window.
 */
function OpenDebugWindow()
{
    ResetSessionTimer();
    document.frm.action = "/nbs/debug";
    document.frm.method = "post";
    document.frm.target = "_blank";
    document.frm.submit();
}

/**
 *  Checks the given year to see if it is a valid MMWR year.
 *  Must be between 1950 and current year plus one.
 *  @param pYear the year to check.
 *  @return an error message if pYear is between 1950 and current year plus one or null if OK.
 */
function Validate_MMWR_Year(pYear)
{
    //  Calculate max year.
    var d = new Date();
    var y = d.getFullYear() + 1;
    var s = "Please enter a valid year between 1950 and " + y + " in the yyyy format.";
    //  Verify parameters.
    if(pYear == null)
    {
        return null;
    }
    if(pYear < 1950)
    {
        return s;
    }
    if(pYear > y)
    {
        return s;
    }
    return null;
}

/**
 *  Gets the left and top pixel coordinates of an element.
 *  Adapted from "Determining Element Page Coordinates" by Peter Belesis.
 *  http://www.webreference.com/dhtml/diner/realpos4/
 *  @param pElement the element to work with.
 *  @return an array containing the element's [x,y] coordinates
 *  or null if pElement is null.
 */
function GetCoordinates(pElement)
{
    //  Verify parameters.
    if(pElement == null)
    {
        return null;
    }
    //  Create temp variables.
    var varLeft = pElement.offsetLeft;
    var varTop  = pElement.offsetTop;
    var varParentElement = pElement.offsetParent;
    var varParentTagName = varParentElement.tagName.toUpperCase();
    var varParentBorder = 0;
    var varParentFrame = null;
    //  Loop through all parent elements.
    while(varParentElement != null)
    {
        if(is_ie)
        {
            if( (varParentTagName != "TABLE") && (varParentTagName != "BODY") )
            {
                //  Add cell border.
                varLeft += varParentElement.clientLeft;
                varTop  += varParentElement.clientTop;
            }
        }
        else
        {
            if(varParentTagName == "TABLE")
            {
                varParentBorder = parseInt(varParentElement.border);
                //  If no valid border attribute, then...
                if(isNaN(varParentBorder))
                {
                    //  check the table's frame attribute.
                    varParentFrame = varParentElement.getAttribute("frame");
                    //  If frame has any value, then...
                    if(varParentFrame != null)
                    {
                        // add one pixel.
                        varLeft++;
                        varTop++;
                    }
                }
                //  If a border width is specified, then...
                else if(varParentBorder > 0)
                {
                    //  add the border width.
                    varLeft += varParentBorder;
                    varTop  += varParentBorder;
                }
            }
        }
        //  Add offset of parent.
        varLeft += varParentElement.offsetLeft;
        varTop  += varParentElement.offsetTop;
        //  Move up the element hierarchy.
        varParentElement = varParentElement.offsetParent;
    }
    //  Create return variable.
    var r = [varLeft, varTop];
    //  Return result.
    return r;
}

/**
 *  Combo box.
 *  Adapted from "Auto-Complete" by Matt Kruse.
 *  http://www.mattkruse.com/javascript/autocomplete/index.html
 *  Usage:
 *      <input
 *       type="text"
 *       name="cboState_textbox"
 *       id="cboState_textbox"
 *       maxlength="50"
 *       style="width: 120px;"
 *       onkeyup="ComboBox('cboState', false);"
 *      />
 *      <select
 *       name="cboState_listbox"
 *       id="cboState_listbox"
 *       size="5"
 *       style="width: 120px;"
 *       onchange="ComboBox('cboState', true, true);"
 *      >
 *  @param pName the base name of the combobox.
 *  @param pRestricted true to limit to values in the listbox or false to allow anything.
 *  Defaults to true if not specified.
 *  @param pChanged set to true when calling from the onchange event of the listbox.
 *  Defaults to false if not specified.
 */
function ComboBox(pName, pRestricted, pChanged)
{
    //  Verify parameters.
    if(pName == null)
    {
        return;
    }
    if(pRestricted == null)
    {
        pRestricted = true;
    }
    //  Create temp variables.
    var varTextbox = getElementByIdOrByName(pName + "_textbox");
    var varListbox = getElementByIdOrByName(pName + "_listbox");
    var varOptions = varListbox.options;
    var varTextValue = varTextbox.value.toUpperCase();
    var varListValue = null;
    var varFound = false;
    var x = 0;
    var y = varOptions.length;
    //  When the listbox changes, just update the textbox and return.
    if(pChanged != null)
    {
        x = varListbox.selectedIndex;
        if(x != -1)
        {
            varTextbox.value = varOptions[x].text;
        }
        return;
    }
    //  Look for what's in the textbox to be in a listbox option.
    for(x=0; x<y; x++)
    {
        varListValue = varOptions[x].text.toUpperCase();
        if(varListValue.indexOf(varTextValue) == 0)
        {
            varFound = true;
            break;
        }
    }
    //  If you get a match, select it.
    //  Otherwise, deselect whatever is selected.
    varListbox.selectedIndex = (varFound == true) ? x : -1;
    if(varTextValue == "")
    {
        varListbox.selectedIndex = -1;
    }
    //  Select the characters that were found and entered for you.
    if(varTextbox.createTextRange)
    {
        if( (pRestricted == true) && (varFound == false) )
        {
            varTextbox.value = varTextbox.value.substring(0, varTextbox.value.length-1);
            return;
        }
        var varKeys ="8;46;37;38;39;40;33;34;35;36;45;";
        var k = window.event.keyCode;
        if(varKeys.indexOf(k + ";") == -1)
        {
            var varRange = varTextbox.createTextRange();
            var varOldValue = varRange.text;
            var varNewValue = (varFound == true) ? varOptions[x].text : varOldValue;
            if(varNewValue != varTextbox.value)
            {
                varTextbox.value = varNewValue;
                varRange = varTextbox.createTextRange();
                varRange.moveStart('character', varOldValue.length);
                varRange.select();
            }
        }
    }
}
function getDaysWithMonthAndYear(month, year) {
  // create array to hold number of days in each month
  var ar = new Array(13);
  ar[0] = 31;
  ar[1] = 31; // January
  ar[2] = (leapYear(year)) ? 29 : 28; // February
  ar[3] = 31; // March
  ar[4] = 30; // April
  ar[5] = 31; // May
  ar[6] = 30; // June
  ar[7] = 31; // July
  ar[8] = 31; // August
  ar[9] = 30; // September
  ar[10] = 31; // October
  ar[11] = 30; // November
  ar[12] = 31; // December

  // return number of days in the specified month (parameter)
  return ar[month];
}
function getDaysWithMonth(month) {
  // create array to hold number of days in each month
  var ar = new Array(13);
  ar[0] = 31;
  ar[1] = 31; // January
  ar[2] = 29; // February
  ar[3] = 31; // March
  ar[4] = 30; // April
  ar[5] = 31; // May
  ar[6] = 30; // June
  ar[7] = 31; // July
  ar[8] = 31; // August
  ar[9] = 30; // September
  ar[10] = 31; // October
  ar[11] = 30; // November
  ar[12] = 31; // December
  // return number of days in the specified month (parameter)
  return ar[month];
}

function leapYear(year) {
  if (year % 4 == 0) {// basic rule
    return true; // is leap year
  }
   return false; // is not leap year
}

function stripOffZero(element)
{
  if(element!=null && element!="")
  {
     if(element.substring(0,1)=="0")
     {
       return element.substring(1,2)
     }
     else
     {
       return element;
     }
  }
}
function ldfNullValue(){}

//masks the time entry HH:MM numeric field without any spl characters
function TimeMask(pTextbox, pKey)
{
    if(pTextbox == null) return;

    var varKey = 0;
    if(pKey == null)
    {
        if(is_ie)
        {
		if(window.event!=null)
            		varKey = window.event.keyCode;
        }
    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    //  Get previous key, value, and mode.
    var k = "" + pTextbox.getAttribute("key");
    var v = "" + pTextbox.getAttribute("val");
    var m = "" + pTextbox.getAttribute("mode");
    if(varVal.length < 2)
    {
        m = "add";
    }
    if(m == "")
    {
        m = "add";
    }
    pTextbox.setAttribute("key", varKey);
    pTextbox.setAttribute("val", varVal);
    pTextbox.setAttribute("mode", m);

    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = ":";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46 ];
    y = varKeys.length;
    for(x=0; x<y; x++)
    {
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    if(m == "edit")
    {
        return;
    }
    y = varVal.length;
    for(x=0; x<y; x++)
    {
        c = varVal.substr(x, 1);
        if(isInteger(c))
        {
            s += c;
            d++;
            if( (d == 2))
            {
                s += z;
            }
        }
        if(d == 8)
        {
            pTextbox.setAttribute("mode", "edit");
            break;
        }
    }
    if(s != varVal)
        pTextbox.value = s;

    if(d != 8)
        return;
}

function SSNMask(pTextbox, pKey) {

	feildmask(pTextbox, pKey, "ssn");
}
function TeleMask(pTextbox, pKey,e) {

	feildmask(pTextbox, pKey, "tele",e);
}
function ZipMask(pTextbox, pKey) {

	feildmask(pTextbox, pKey, "zip");
}
function monthYearMask(){
	feildmask(pTextbox, pKey, "monthYear");
}

function feildmask(pTextbox, pKey,type,e)
{

    if(pTextbox == null)
        return;

    var varKey = 0;
    if(pKey == null){
        if(is_ie){
		if(window.event!=null)
            	varKey = window.event.keyCode;
        }else{
			if(e!=null)
				varKey=e.which;
		}

    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "-";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
    
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    y = varVal.length;

    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isInteger(c))
        {
            s += c;
            d++;
            
            if (type == "ssn") {
            
		    if( (d == 3) || (d == 5) )
			s += z;
            
            } else if (type == "tele") {

		    if( (d == 3) || (d == 6) )
			s += z;
            } else if (type == "zip") {

		    if( (d == 5))
			s += z;
            }
        }
        
	if (type == "ssn") {
        	if(d == 9)            
        	break;
        }

	if (type == "tele") {
        	if(d == 10)            
        	break;
        }
	
	if (type == "zip") {
        	if(d == 9)            
        	break;
        }
    }

    if(s != varVal)
        pTextbox.value = s;

    if (type == "ssn") {
	    if(d != 9)
	        return;    
    }
    if (type == "tele") {
	    if(d != 10)
	        return;    
    }
    if (type == "zip") {
    	if(d==5) {
    		pTextbox.value = s.substring(0,5);
    	}
	    if(d != 5 || d != 9)
	        return;    
    }

}

function CaseNumberMask(pTextbox, fieldId, pKey)
{
    if (pTextbox == null) {
        return;
    }
        
    if(pTextbox.value.length == pTextbox.getAttribute("maxlength")) {
        return;
    }

    var varKey = 0;
    if(pKey == null) {
        if(is_ie) {
            if(window.event!=null) {
                varKey = window.event.keyCode;
            }
        }
    }
    else {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "-";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
        if(varKey == varKeys[x])
        {
            return;
        }
    }

    y = varVal.length;

    for(x=0; x<y; x++)
    {
        c = varVal.substr(x, 1);
        if (x < 4)
        {
            if(isInteger(c)) {
                s += c;
            }
        }
	    else if (x == 5 || x == 6)
        {
            if(isAlphabet(c)) {
                s += c.toUpperCase();
            }
        }
        else if (x == 8)
        {
            if(c != '-') {
                s += c.toUpperCase();
            }
        }
	    else if (x > 8)
	    {
            s += c.toUpperCase();
        }
	    
	    if (s.length == 4 || s.length == 7) {
            s += z;
        }
        
         pTextbox.value = s;
            
        if (s.length <= 4) {
            YearMask(pTextbox);
        }
    }
}

function paddedSuffix(val, suffixLen)
{
    // capitalize vals
    var t = "";
    var y = val.length;
    for(var x=0; x<y; x++)
    {
        t += (val.substr(x, 1)).toUpperCase();
    }
    
    val = t;    
    
    if (val.length == suffixLen) {
        return val;
    }
    else {
        var c = suffixLen - val.length;
        var s = "";
        for (i=0;i<c;i++) {
            s+="0";
        }
        s += val;

        return s;
    }
}

function FormatCaseNumberMask(pTextbox)
{
    var returnVal = pTextbox.value;
    var substrings = pTextbox.value.split("-");
    var count = substrings.length - 1;
    if (count == 2) {
        var t = pTextbox.value.substring(pTextbox.value.lastIndexOf("-")+1, pTextbox.value.length);
        returnVal = pTextbox.value.substring(0, pTextbox.value.lastIndexOf("-")+1) + paddedSuffix(t,9);
    }

    pTextbox.value = returnVal;    
}

function UpperCaseMask(pTextbox, pKey)
{
    if(pTextbox == null)
        return;
    
    var varKey = 0;
    if(pKey == null){
        if(is_ie){
        if(window.event!=null)
                varKey = window.event.keyCode;
        }
    }
    else
    {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
        if(varKey == varKeys[x])
        {
            return;
        }
    }
    y = varVal.length;

    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isAlphabet(c)) {
            s += c.toUpperCase();
        }
        else {
            s += c;
        }
    }

    pTextbox.value = s;
}

function validatePhoneLength()
{
 
	var HomepNo  = getElementByIdOrByName("DEM238").value;
	
	var WorkpNo  = getElementByIdOrByName("DEM240").value;	
   
	var i=0;	
	  var ar = new Array(2);  

  if(HomepNo != null && HomepNo !="" && HomepNo.length !=0 &&  HomepNo.length != 12 ){
       ar[i] = "Home Phone Number should be of the form nnn-nnn-nnnn";
	   i++;
	   // displayGlobalErrorMessage("Home phone Number should be of the form nnn-nnn-nnnn" + txt);
 
  }

   if(WorkpNo != null &&  WorkpNo != "" &&  WorkpNo.length !=0 && WorkpNo.length != 12 ){
	    ar[i] ="Work Phone Number should be of the form nnn-nnn-nnnn"; 
		i++;// January
	  //displayGlobalErrorMessage("Work phone Number should be of the form nnn-nnn-nnnn" + txt); 
  }
  if(i==1){
   displayGlobalErrorMessage(ar[0]);
   return false;  
  }
  if(i==2){
   displayGlobalErrorMessage(ar);
   return false;
  }
  if(i==0)
	  return true;
	
}

/**
* Checks if the character currently entered in the text box 
* is a valid numeric or not. If not, it returns the text that 
* was present before this character was entered.
* Uses JS's isInteger() JS method for validation
*/
function isDecimalCharacterEntered(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
	var count=0;
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isInteger(c)||(c=="." && count==0)) s += c;
         pTextbox.value = s;
		 if(c==".")
			 count=1;
    }
}

//This function allows letters, numbers and special characters underscore("_") and space.
function specialCharMask(pTextbox,pKey)
{
    if (pTextbox == null) {
        return;
    }
       
    var varKey = 0;
    if(pKey == null) {
        if(is_ie) {
            if(window.event!=null) {
                varKey = window.event.keyCode;
            }
        }
    }
    else {
        varKey = pKey;
    }
    var varVal = pTextbox.value;
    var x = 0;
    var y = 0;
    var s = "";
    var c = "";
    var z = "_";   
    var m = " ";
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    y = varKeys.length;
    
    for(x=0; x<y; x++) {
        if(varKey == varKeys[x])
        {
            return;
        }
    }

    y = varVal.length;

    for(x=0; x<y; x++)
    {
        c = varVal.substr(x, 1);
      
            if(isInteger(c)|| isAlphabet(c) || c==z || c==m) {
                s += c;           
        }	   
    }
	           
   pTextbox.value = s;      
   
}

/*
* Checks if the character currently entered in the text box 
* is a special character except underscore(_).
* If it is, it returns the text that was present before this 
* character was entered.
* This function is being used in code for Labtest, result, 
* condition, question, valueset
*/

function isSpecialCharEnteredForCodeOnlyAlphAndUnderscore(pTextbox,pKey)
{
	if (pTextbox == null) {
        return;
		}
		
		var varVal = pTextbox.value;
		var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
		
		var varKey = 0;
		if(pKey == null) {
		   if(is_ie) {
		      if(window.event!=null) {
		            varKey = window.event.keyCode;
		      }
		   }
		}
		else {
		     varKey = pKey;
			}
		
		
		var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
		y = varKeys.length;
		
		for(x=0; x<y; x++) {
		 if(varKey == varKeys[x])
		 {
		      return;
		 }
		}
		
		z = varVal.length;
		
		flag = false;
		var pos = 0;
		for (x=0; x<z; x++) {
		    c = varVal.substr(x, 1);
		    if (isAlphaNumeric(c) || c == "_" /*||c == "-" ||c == "+"*/) {
		        s += c;
		    }else{
		     flag = true;
		     pos = s.length;
		    }
		
		}
		if(flag){
		    pTextbox.value = s;
		    var obj = document.activeElement;
		    if (obj) {
		      var tr = obj.createTextRange();
			  if (obj && tr) {
				  tr.moveStart("character", pos);
				  tr.collapse();
				  tr.select();
		      }
		   }        
			    	
		} //End Flag If
}


/*
* Checks if the character currently entered in the text box 
* is a special character except underscore(_), dash(-), plus(+). 
* If it is, it returns the text that was present before this 
* character was entered.
* This function is being used in code for Labtest, result, 
* condition, question, valueset
*/

function isSpecialCharEnteredForCode(pTextbox,pKey)
{
	if (pTextbox == null) {
        return;
		}
		
		var varVal = pTextbox.value;
		var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
		
		var varKey = 0;
		if(pKey == null) {
		   if(is_ie) {
		      if(window.event!=null) {
		            varKey = window.event.keyCode;
		      }
		   }
		}
		else {
		     varKey = pKey;
			}
		
		
		var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
		y = varKeys.length;
		
		for(x=0; x<y; x++) {
		 if(varKey == varKeys[x])
		 {
		      return;
		 }
		}
		
		z = varVal.length;
		
		flag = false;
		var pos = 0;
		for (x=0; x<z; x++) {
		    c = varVal.substr(x, 1);
		    if (isAlphaNumeric(c) || c == "_" ||c == "-" ||c == "+") {
		        s += c;
		    }else{
		     flag = true;
		     pos = s.length;
		    }
		
		}
		if(flag){
		    pTextbox.value = s;
		    var obj = document.activeElement;
		    if (obj) {
		      var tr = obj.createTextRange();
			  if (obj && tr) {
				  tr.moveStart("character", pos);
				  tr.collapse();
				  tr.select();
		      }
		   }        
			    	
		} //End Flag If
}


/*
* Checks if the character currently entered in the text box 
* is a special character except these characters ()-_+=:;<,>./ and star(*)
* If it is, it returns the text that was present before this 
* character was entered.
* This function is being used in name for condition, 
* page/template Name, tab, section, subsection
*/

function isSpecialCharEnteredForName(pTextbox,pKey, e)
{
	
	
	if (pTextbox == null) {
	        return;
	}
	
	var varVal = pTextbox.value;
	var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
	
	var varKey = 0;
	if(pKey == null) {
	 //  if(is_ie) {
	      if(window.event!=null) {
	            varKey = window.event.keyCode;
	      }else
				varKey = e.keyCode;
	 //  }
	}
	else {
	     varKey = pKey;
    	}

	
	var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
	y = varKeys.length;

	for(x=0; x<y; x++) {
	 if(varKey == varKeys[x])
	 {
	      return;
	 }
	}
	
	z = varVal.length;
	
	flag = false;
	var pos = 0;
	for (x=0; x<z; x++) {
	    c = varVal.substr(x, 1);
	    if (isAlphaNumeric(c) || c==" " || c=="(" ||c==")"|| c == "_" ||c == "-" ||c == "+"  || 
	    		c=="=" || c==":" || c==";" ||c=="," || c=="." || 
	    		c=="/" || c=="*") {
	        s += c;
	    }else{
	     flag = true;
	     pos = s.length;
	    }
	
	}
	if(flag){
	    pTextbox.value = s;
	    var obj = document.activeElement;
	    if (obj) {
		var tr = obj.createTextRange();
		if (obj && tr) {
		  tr.moveStart("character", pos);
		  tr.collapse();
		  tr.select();
	        }
	   }        
		    	
	} //End Flag If
	    
}

/*
* Checks if the character currently entered in the text box 
* is a special character except these characters ()-_:; and star(*)
* If it is, it returns the text that was present before this 
* character was entered.
* This function is being used in name for condition, 
* page/template Name, tab, section, subsection
*/

function isSpecialCharEnteredForQueueName(pTextbox,pKey, e)
{
	
	
	if (pTextbox == null) {
	        return;
	}
	
	var varVal = pTextbox.value;
	var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
	
	var varKey = 0;
	if(pKey == null) {
	 //  if(is_ie) {
	      if(window.event!=null) {
	            varKey = window.event.keyCode;
	      }else
				varKey = e.keyCode;
	 //  }
	}
	else {
	     varKey = pKey;
    	}

	
	var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
	y = varKeys.length;

	for(x=0; x<y; x++) {
	 if(varKey == varKeys[x])
	 {
	      return;
	 }
	}
	
	z = varVal.length;
	
	flag = false;
	var pos = 0;
	for (x=0; x<z; x++) {
	    c = varVal.substr(x, 1);
	    if (isAlphaNumeric(c) || c==" " || c=="(" ||c==")"|| c == "_" ||c == "-"  || 
	    		c==":" || c==";" ||c=="," ||  c=="*") {
	        s += c;
	    }else{
	     flag = true;
	     pos = s.length;
	    }
	
	}
	if(flag){
	    pTextbox.value = s;
	    var obj = document.activeElement;
	    if (obj) {
		var tr = obj.createTextRange();
		if (obj && tr) {
		  tr.moveStart("character", pos);
		  tr.collapse();
		  tr.select();
	        }
	   }        
		    	
	} //End Flag If
	    
}

/*
* isSpecialCharEnteredForDataMarts: Checks if the character currently entered in the text box 
* is a special character except this character _.
* If it is, it returns the text that was present before this 
* character was entered.
* Also, it changes the value to UpperCase
* This function is being used in Block Name for Edit Subsection.
*/


function isSpecialCharEnteredForDataMarts(pTextbox,pKey,e,maxLength)
{
	if (pTextbox == null) {
	        return;
	}

	var varVal = pTextbox.value;
	var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
	
	var varKey = 0;
	if(pKey == null) {
	 //  if(is_ie) {
	      if(window.event!=null) {
	            varKey = window.event.keyCode;
	      }else
				varKey = e.keyCode;
	 //  }
	}
	else {
	     varKey = pKey;
    	}

	
	var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
	y = varKeys.length;

	for(x=0; x<y; x++) {
	 if(varKey == varKeys[x])
	 {
	      return;
	 }
	}

	z = varVal.length;
	
	flag = false;
	var pos = 0;
	for (x=0; x<z; x++) {
	    c = varVal.substr(x, 1);
	    if ((isAlphaNumeric(c) || c=="_") && (!(x==0 && isNumericString(c)))) {
	        s += c;
	    }else{
	     flag = true;
	     pos = s.length;
	    }
	
	}
	if(flag){
	    pTextbox.value = s;
	    var obj = document.activeElement;
	    if (obj) {
		var tr = obj.createTextRange();
		if (obj && tr) {
		  tr.moveStart("character", pos);
		  tr.collapse();
		  tr.select();
	        }
	   }        
		    	
	} //End Flag If
	 pTextbox.value=pTextbox.value.toUpperCase(); 
	 
	 if(pTextbox.value.length>maxLength)
		 pTextbox.value=pTextbox.value.substring(0,maxLength-1);
}



/**
 * dataMartRepeatNumber(): it should be an integer from 0 to 5.
 */

function dataMartRepeatNumber (pTextbox, pKey, e){
	
	if (pTextbox == null) {
	        return;
	}
	
	pTextbox.value=pTextbox.value.toUpperCase();
	var varVal = pTextbox.value;
	var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
	
	var varKey = 0;
	if(pKey == null) {
	 //  if(is_ie) {
	      if(window.event!=null) {
	            varKey = window.event.keyCode;
	      }else
				varKey = e.keyCode;
	 //  }
	}
	else {
	     varKey = pKey;
    	}

	
	var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
	y = varKeys.length;

	for(x=0; x<y; x++) {
	 if(varKey == varKeys[x])
	 {
	      return;
	 }
	}
	
	z = varVal.length;
	
	flag = false;
	var pos = 0;
	for (x=0; x<z; x++) {
	    c = varVal.substr(x, 1);
	    if (isInteger(c) && (c>-1 && c<6)) {
	        s += c;
	    }else{
	     flag = true;
	     pos = s.length;
	    }
	
	}
	if(flag){
	    pTextbox.value = s;
	    var obj = document.activeElement;
	    if (obj) {
		var tr = obj.createTextRange();
		if (obj && tr) {
		  tr.moveStart("character", pos);
		  tr.collapse();
		  tr.select();
	        }
	   }        
		    	
	} //End Flag If
	    
}

//Allowed special characters ()-_+=,. and space

function isSpecialCharEnteredForTabName(pTextbox,pKey)
{
	if (pTextbox == null) {
        return;
		}
		
		var varVal = pTextbox.value;
		var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
		
		var varKey = 0;
		if(pKey == null) {
		   if(is_ie) {
		      if(window.event!=null) {
		            varKey = window.event.keyCode;
		      }
		   }
		}
		else {
		     varKey = pKey;
			}
		
		
		var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
		y = varKeys.length;
		
		for(x=0; x<y; x++) {
		 if(varKey == varKeys[x])
		 {
		      return;
		 }
		}
		
		z = varVal.length;
		
		flag = false;
		var pos = 0;
		for (x=0; x<z; x++) {
		    c = varVal.substr(x, 1);
		    if (isAlphaNumeric(c) || c==" " || c=="(" ||c==")"|| c == "_" ||c == "-" ||c == "+"  || 
		    		c=="=" || c=="," || c=="." )
		    {
		        s += c;
		    }else{
		     flag = true;
		     pos = s.length;
		    }
		
		}
		if(flag){
		    pTextbox.value = s;
		    var obj = document.activeElement;
		    if (obj) {
		      var tr = obj.createTextRange();
			  if (obj && tr) {
				  tr.moveStart("character", pos);
				  tr.collapse();
				  tr.select();
		      }
		   }        
			    	
		} //End Flag If
}

//Allowed special characters _-* and space

function isSpecialCharEnteredForSectionName(pTextbox,pKey)
{
	if (pTextbox == null) {
        return;
		}
		
		var varVal = pTextbox.value;
		var x = 0; var y = 0; var z = 0; var s = ""; var c = "";
		
		var varKey = 0;
		if(pKey == null) {
		   if(is_ie) {
		      if(window.event!=null) {
		            varKey = window.event.keyCode;
		      }
		   }
		}
		else {
		     varKey = pKey;
			}
		
		
		var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
		y = varKeys.length;
		
		for(x=0; x<y; x++) {
		 if(varKey == varKeys[x])
		 {
		      return;
		 }
		}
		
		z = varVal.length;
		
		flag = false;
		var pos = 0;
		for (x=0; x<z; x++) {
		    c = varVal.substr(x, 1);
		    if (isAlphaNumeric(c) || c==" " || c == "_" ||c == "-" || c=="*" ) 
		    {
		        s += c;
		    }else{
		     flag = true;
		     pos = s.length;
		    }
		
		}
		if(flag){
		    pTextbox.value = s;
		    var obj = document.activeElement;
		    if (obj) {
		      var tr = obj.createTextRange();
			  if (obj && tr) {
				  tr.moveStart("character", pos);
				  tr.collapse();
				  tr.select();
		      }
		   }        
			    	
		} //End Flag If
}

function contactRecordPopUp(urlToOpen)
{
	/*
	var urlToOpen = "/nbs/PatientSearch.do?method=searchLoad";
	var params="left=100, top=50, width=800, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var pview =getElementByIdOrByName(pamview);
	pview.style.display = "block";
	newwindow = window.open(urlToOpen,'Patient', params);
	newwindow.focus();
	*/
		
	var dialogFeatures = "dialogWidth: " + "980" + "px;dialogHeight: " + 
            "900" + "px;status: no;unadorned: yes;scroll: yes;help: no; resizable:no"
	//var urlToOpen = "/nbs/PatientSearch.do?method=searchLoad";
	var divElt = getElementByIdOrByName("pamview");
	if (divElt == null) {
		divElt = getElementByIdOrByName("blockparent");
	}
    divElt.style.display = "block";
    var o = new Object();
    o.opener = self;
	//var modWin = window.showModalDialog(urlToOpen,o, dialogFeatures);
	
	var modWin = openWindow(urlToOpen, o,dialogFeatures, divElt, "");
	
	
	
	if (modWin == "windowClosed") {
        divElt.style.display = "none";
        
        var saveMsg = '<div class="submissionStatusMsg"> <div class="header"> Page Loading </div>' +  
        '<div class="body">Please wait...  The system is loading the requested page.</div> </div>';         
		$j.blockUI({  
		    message: saveMsg,  
		    css: {  
		        top:  ($j(window).height() - 500) /2 + 'px', 
		        left: ($j(window).width() - 500) /2 + 'px', 
		        width: '500px'
		    }  
		});
    }
}

function SearchPatientPopUp()
{
    var contactUrl = "/nbs/LoadCTSearchfromXSP.do?ContextAction=PatientSearch";	
    var urlToOpen = contactUrl;
    
    var divElt = getElementByIdOrByName("pamview");
    if (divElt == null) {
        divElt = getElementByIdOrByName("blockparent");
    }
    
    divElt.style.display = "block";
    
    var o = new Object();
    o.opener = self;
	var modWin = window.open(urlToOpen);
            
    if (modWin == "windowClosed") {
        divElt.style.display = "none";
    }
}

      function ManageCtAssociationtPopUp(){
      
                 // alert( "patientRevision = " +patientRevision);
                //  alert( "caseLocalId= " +caseLocalId);
     
                  
     		var urlToOpen =  "/nbs/LoadManageCtAssociation.do?ContextAction=default&method=manageContactsLoad";
     		var divElt = getElementByIdOrByName("pamview");
     	    	if (divElt == null) {
     	       	 divElt = getElementByIdOrByName("blockparent");
     	    	}
     	    	divElt.style.display = "block";
     	    	var o = new Object();
     	    	o.opener = self;
     	    	//window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
     	    	
     	    	var modWin = openWindow(urlToOpen, o,GetDialogFeatures(760, 700, false, true), divElt, "");
     	    	//window.open(urlToOpen);
     	}

		  
function reldPage() {
	document.forms[0].action ="/nbs/LoadManageCtAssociation.do?method=manageContactsSubmit";
	document.forms[0].submit(); 
}
		  
function deleteContact(del,deleteContact)
{
	document.forms[0].target="";   
	document.forms[0].action ="/nbs/LoadContactTracing.do?method=deleteSubmit";
	document.forms[0].submit();
}
		  
//This logic used in CreateInputForm.xsl for Select Condition
//The enter key is changed to a tab for STD conditions so the
//Processing Decision popup is not bypassed.
function enterToTabForSTD(keyCode)
{
    if (keyCode == null || keyCode != 13) //not enter key - return
        return;
    if (getElementByIdOrByName("STDConditions") == null)
    	return;
    var stdCondList = "";
    stdCondList = getElementByIdOrByName("STDConditions").value;
    if (stdCondList == "")
    	return;
    var theSelectedCond = $j('#ccd :selected').val();
    if (theSelectedCond == null || theSelectedCond == "")
    	return;
    //user selected STD condition?
    if (stdCondList.indexOf(theSelectedCond) > -1) 
    	event.keyCode=9;  //change to tab
    return;
    
}
