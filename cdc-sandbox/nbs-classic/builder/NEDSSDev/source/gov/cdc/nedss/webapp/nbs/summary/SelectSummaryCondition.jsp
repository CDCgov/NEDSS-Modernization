<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html lang="en">
    <head>
        <title> Select Condition </title>
        <link rel="stylesheet" href="yui/yui.css" type="text/css" media="screen,print">
        <link rel="stylesheet" href="recent/common.css" type="text/css" media="screen,print">
        <script type="text/javascript">
            /** 
                Called by the unload event(). For example when user clicks on the 
                close window button 
            */
            function handlePopupClose()
            {
                self.close();
                var opener = getDialogArgument();    
                var pview = getElementByIdOrByNameNode("blockparent")
                pview.style.display = "none";                   
            }
            
            /** 
                Direct the user to the corresponding page depending on the 
                condition selected. If the user selects novel influenza, direct
                him to the new JSP page, else direct him the old 
                'Manage Summary Notifications' XSP, XSL page. 
            */ 
            function selectCondition()
            {
                var conditionCode = document.SelectConditionForm.condition.value;
                
                // '11062' is the condition for 'Novel Influenza'
                if (conditionCode == '11062') {
                    self.close();
                    var opener = getDialogArgument();   
                    var pview = getElementByIdOrByNameNode("blockparent")
                    pview.style.display = "none";
                    opener.location = "/nbs/AggregateSummary.do?method=searchLoadSummaryData&conditionCd=" + conditionCode;
                }
                else if (conditionCode.length != 0) { 
                    self.close();
                    var opener = getDialogArgument();   
                    var pview = getElementByIdOrByNameNode("blockparent")
                    pview.style.display = "none";
                    opener.location = "/nbs/LoadManageSummary1.do?ContextAction=GlobalSummaryData";
                }
                else {
                    alert("Please select a condition to proceed...");
                }
            }
            
            /**
                Sort all the options alphabetically in a list box 
            */
            function compareOptionText(a,b) {
                return a.text!=b.text ? a.text<b.text ? -1 : 1 : 0;
            }
            
            function sortOptions(list) {
                var items = list.options.length;
                var tmpArray = new Array(items);
                for ( i=0; i<items; i++ ) {
                    tmpArray[i] = new Option(list.options[i].text,list.options[i].value);
                }
                tmpArray.sort(compareOptionText);
                for ( i=0; i<items; i++ ) {
                    list.options[i] = new Option(tmpArray[i].text,tmpArray[i].value);
                }
            }
            
            /**
                Add an option to the given select box/drop down list
            */
            function addSelectBoxOption(elt, value, label) {
                var optn = document.createElement("OPTION");
				optn.text = label;
				optn.value = value;
				elt.options.add(optn);
            }
            
            /**
                Perform operation when the page has finished loading.
            */
            window.onload = function() {
                // add all conditions from string to drop-down list
                var cstr = "<bean:write name="condition" />";
                var carr = cstr.split("|");
                var selectBox = document.SelectConditionForm.condition;
                for (var i = 0; i < carr.length; i++) {
                    if (carr[i] == "") {
                        addSelectBoxOption(selectBox, "", "");
                    }
                    else {
                        var tmp = carr[i].split("$");
                        addSelectBoxOption(selectBox, tmp[0], tmp[1]);
                    }
                }
                
                // sort all the options added
                sortOptions(selectBox);
            }
        </script>
    </head>
    
    <body class="childWindow" onunload="handlePopupClose()">
        <h5> Please select a condition for the summary case report: <h5/>
        
        <form name="SelectConditionForm">
            <table role="presentation" class="formTable">
                <col style="width:14em;"> </col>
                <col/>
                <tr>
                    <td class="fieldName"> 
                        <span style="color:#CC0000;font-weight:bold;">*</span>
                        <span style="font-weight:bold;"> Condition: </span>
                    </td>
                    <td>
                        <select name="condition">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align:right; padding-top:1em;"> 
                        <input type="button" onclick="selectCondition()" value="Submit" /> &nbsp;&nbsp; 
                        <input type="button" onclick="handlePopupClose()" value="Cancel" />
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>