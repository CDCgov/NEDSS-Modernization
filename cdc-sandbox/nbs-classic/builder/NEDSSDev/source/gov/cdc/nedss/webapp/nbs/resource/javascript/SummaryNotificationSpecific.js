function getSummaryReports()
{	
       
        
	var contextActionNode= getElementByIdOrByName("ContextAction");
	var countySelect = getElementByIdOrByName("County");
	contextActionNode.value ="GetSummaryData";
       	var formNode = getElementByIdOrByName("nedssForm");
       	// resetting the flag if you press get Summary Data after the criteria change 
       	criteriaFlag = false;
       	submitForm();
     


}

function addSummaryReports()
{
        var errorArray = new Array();
	if(criteriaFlag == true)
	{
	   alert("You have changed one or more of the selection criteria. Please press the \"Get Summary Reports\" button to display the associated summary reports and try again"); 	   
	   return false;
	}    
	var contextActionNode= getElementByIdOrByName("ContextAction");
	contextActionNode.value ="AddSummaryReport";
        var condition = getElementByIdOrByName("Condition_textbox");
   	if(condition.value == "")
   	{   
   	    errorArray[0] = "Condition";
   	    alert(makeErrorMsg('ERR001', errorArray));
            condition.focus(); 
        }
        else
        {

	var formNode = getElementByIdOrByName("nedssForm");
	submitForm();
        }
}

function changeCriteriaFlag()
{
  criteriaFlag = true;
}



function updateMMWRWeeks()
{
			
				var temp = "";
                            var varYear = getElementByIdOrByName("MMWRYear");
                            if(varYear == null)
                            {
                                return;
                            }
                            if(varYear == "")
                            {
                                return;
                            }
				
                            var varWeeks = getMMWRWeeks(varYear.value);
                            var e = null;
                            var s = null;
                            var t = null;
                            var varID = null;
                            var varName = null;
                            var varValue = null;
                            var x = 0;
                            var y = 0;
                            var varList = getElementByIdOrByName("MMWRWeek");
                            y = varList.options.length;
				
				
				
				var removeOpt = varList.firstChild;

					 while(removeOpt != null){
						  var temp = removeOpt.nextSibling;
						  varList.removeChild(removeOpt);
						  removeOpt= temp;
					 }
				getElementByIdOrByName(varList.name + "_textbox").value="";
				varList.className="none";
				
				
                            for(x=y-1; x>-1; x--)
                            {
                              varList.remove(x);
                            }

                            y = varWeeks.length;
			    
                            var d = new Date();
                            var varMM = d.getMonth() + 1;
                            var varDD = d.getDate();
                            var varYY = d.getFullYear();

				//alert("varYear.value = " + varYear.value);
				//alert("varYY = " + varYY);

			    if (varYear.value == varYY)
			    {
                              s = varMM + "/" + varDD + "/" + varYY;
                              d = new Date(s);
                              s = DateToString(d);
                              var a = CalcMMWR(s);
                              var w = a[1];
			    }
                            temp = ""; 
                            for(x=0; x!=y; x++)
                            {
				
                                b = varWeeks[x];
                                varName = b[1];
                                varID = "id_SUM102_" + varName;
                                varValue = b[0];
                                if(varValue == "")
			           {
				     continue;
				   }
	
                                e = document.createElement("option");
                                e.setAttribute("id", varID);
                                e.setAttribute("value", varValue);
				
				
                                if((x == w) && (varYear.value == varYY))
                                {
                                    e.setAttribute("selected", "selected");

                                }
                                
				t = document.createTextNode(varName);
                                e.appendChild(t);
				varList.appendChild(e);
				temp += varValue +"$" + varName+"|";
				
                            }


		var hiddenNode = getElementByIdOrByName("MMWRWeekOptions");
		if(hiddenNode!=null)
			hiddenNode.value=temp ;
		//alert(hiddenNode.value);
		getElementByIdOrByName("MMWRWeek").style.pixelWidth = 200;


}




function onLoadMMWRWeeks()
{


	var countyNode = getElementByIdOrByName("County");
	if(countyNode.value=="")
	{
			
				var temp = "";
                            var varYear = getElementByIdOrByName("MMWRYear");
                            if(varYear == null)
                            {
                                return;
                            }
                            if(varYear == "")
                            {
                                return;
                            }
				
                            var varWeeks = getMMWRWeeks(varYear.value);
                            var e = null;
                            var s = null;
                            var t = null;
                            var varID = null;
                            var varName = null;
                            var varValue = null;
                            var x = 0;
                            var y = 0;
                            var varList = getElementByIdOrByName("MMWRWeek");
                            y = varList.options.length;

                            for(x=y-1; x>-1; x--)
                            {
                              varList.remove(x);
                            }

                            y = varWeeks.length;
			    
                            var d = new Date();
                            var varMM = d.getMonth() + 1;
                            var varDD = d.getDate();
                            var varYY = d.getFullYear();

				//alert("varYear.value = " + varYear.value);
				//alert("varYY = " + varYY);

			    if (varYear.value == varYY)
			    {
                              s = varMM + "/" + varDD + "/" + varYY;
                              d = new Date(s);
                              s = DateToString(d);
                              var a = CalcMMWR(s);
                              var w = a[1];
			    }

                            for(x=0; x!=y; x++)
                            {
				
                                b = varWeeks[x];
                                varName = b[1];
                                varID = "id_SUM102_" + varName;
                                varValue = b[0];
                                if(varValue == "")
			          {
				    continue;
				  }
                                e = document.createElement("option");
                                e.setAttribute("id", varID);
                                e.setAttribute("value", varValue);
				
				
                                if((x == w) && (varYear.value == varYY))
                                {
                                    e.setAttribute("selected", "selected");

                                }
                                
				t = document.createTextNode(varName);
                                e.appendChild(t);
				varList.appendChild(e);
				temp += varValue +"$" + varName+"|";
				
                            }

		var varOptions = varList.options;
		getElementByIdOrByName(varList.name + "_textbox").value = varOptions[varList.selectedIndex].text;
			
			
			
		var hiddenNode = getElementByIdOrByName("MMWRWeekOptions");
		if(hiddenNode!=null)
			hiddenNode.value=temp ;
		//alert(hiddenNode.value);
		getElementByIdOrByName("MMWRWeek").style.pixelWidth = 200;

	}
}


function submitSendNotification()
{
     var contextAction = getElementByIdOrByName("ContextAction");
     contextAction.value="SubmitAndSendNotification";
     var counter = 0;
     var tempValue=0;
     var type = "Count";     
     var counterColIndex;     
     var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);     
     var items = hiddenNode.value.split("|");
     
     
     if(items.length > 1)	
     {
     	var lineCounter = 0;
     	for (var i=0; i < items.length; i++) 
     	{
         if(items[i].search(/numericValue~/)==-1 && items[i]!="")
         {
	   var pairs = items[i].split("^");
	   if (pairs.length > 1)	
	   {
	     identifier = pairs[0].split(nvDelimiter);
	     
             for (var j=0;j < pairs.length; j++) 
             {
				
		if(pairs[j]!="")
		{
		  var nameValue = pairs[j].split(nvDelimiter);
		  var name = nameValue[0];
		  var value = nameValue[1];
		  var elementNode = getElementByIdOrByName(name);
		  if (elementNode != null)
		  {
		    if(elementNode.counter=="on")
		    {
			counterColIndex = j;
			if(isNaN(parseInt(value))){								  
			 value = 0;
			}

			tempValue = parseInt(tempValue) + parseInt(value);
			counter = parseInt(tempValue);
		    }
		  }              
		}
	      }
	   }
	 }
	}	//if this isn't an inactive one
     }
     //alert("counter value is " + counter);
     var x = false;
     if(counter <= 0 || isNaN(counter)) {
     //x = window.confirm("Are you sure you want to send a notification for a condition count of Zero?");
     x = window.confirm(makeErrorMsg('ERR064', ""));
     if(x == false)
     {
             //  The user hit the "Cancel" button, so don't do anything.
          return false;
     }        
    }
    submitForm();
}



function getsatDate()
{
   var y = getElementByIdOrByName("MMWRYear");
   var w = getElementByIdOrByName("MMWRWeek");
   var h = getElementByIdOrByName("satWeek");
   var s = getSaturdayForMMWR(y.value, w.value);
   h.value = s;
     
}

function uniqueSummaryReports(){
	//return false if no problems
	        var errorArray = new Array();
		
		var buttonNode = getElementByIdOrByName("BatchEntryAddButtonCount");
		
		if(buttonNode.value=="Add Count")
		{
			var statusNode;
        		var sourceNode = getElementByIdOrByName("report[i].observationVO_s[0].obsValueCodedDT_s[0].code");
        		var countNode = getElementByIdOrByName("Count");
        		var items = countNode.value.split("|");
        		var status;
			var targetNode = false;
			//alert(sourceNode.value);
        		if(items.length > 1)	
			{
				 for (var i=0; i < items.length; i++) 
				 {
				  var pairs = items[i].split("^");
				  if (pairs.length > 1)	
				  {
				     identifier = pairs[0].split(nvDelimiter);
				  	var tempSourceName;
				 	for (var j=0;j < pairs.length; j++) 
				 	{

						if(pairs[j]!="")
						{
					 	 var nameValue = pairs[j].split(nvDelimiter);
						  var name = nameValue[0];
						  var value = nameValue[1];
						  //alert(" name " + name + " value " + value);
						  if(name == "report[i].observationVO_s[0].obsValueCodedDT_s[0].code")
						    tempSourceName = value;
						  if(value == sourceNode.value){
						    targetNode = true;
						  }
						  if(targetNode){
						   
						     if(name == "report[i].statusCd" && tempSourceName == sourceNode.value){
						      statusNode= value;
						     //alert("statusNode  "+ statusNode);
						    }
						   } 

						}
		  	          	}
				 }   
			      }		  	          	
     			}	
        		
        		
        		
        		//alert( " Hello "+ statusNode.value) 
		        if(statusNode == "A")
        		{
				var errorTD = getElementByIdOrByName("nestedErrorMsgCount");
				errorTD.setAttribute("className", "none");
				var errorText="";


				sourceNode = getElementByIdOrByName("report[i].observationVO_s[0].obsValueCodedDT_s[0].code");
				countNode = getElementByIdOrByName("Count");


				if (countNode.value.indexOf("~" + sourceNode.value + "^",0)!=-1)	{



					//errorText += "You attempted to add a source that already exisits in the counts batch entry box. You may not enter a duplicate source.";
					       errorText += makeErrorMsg('ERR093', "");

								errorTD.innerText = errorText;
								
								if(errorText!="" && errorTD.innerText)//if there is no changes
									errorTD.textContent=errorText;//Firefox

								errorTD.setAttribute("className", "error");
					return true;
				}

			
                         }
 
                }


				return false;
			
	
}

batchEntryValidationArray[0] = uniqueSummaryReports;
criteriaFlag = false;
