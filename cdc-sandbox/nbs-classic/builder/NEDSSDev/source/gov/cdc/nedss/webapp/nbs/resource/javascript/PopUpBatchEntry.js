/**
 * Title:        double batch entry
 * Description:  does the client side presentation processing of nested data
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       10/12/2001 Jay Kim
 * @modified     10/12/2001 Jay Kim
 * @version      1.0.0, 10/12/2001
 */


/*	Variables in the globals

var increment = 0;
var nvDelimiter = "=";
var pairDelimiter = "&";
var lineDelimiter = "|";
*/


/**
 * Description:	updates the gray history box based on the data structure string
 *
 * param @type	name of of the nested type on the page
 */
var cacheString = "";

function PopupInitialize(type,action){
	var hiddenNode = getElementByIdOrByName("PopupData" + type);
	var items = hiddenNode.value.split("]|");
	var indexedData = "";
	if(items.length > 1)	{
		for (var i=0; i < items.length; i++) {
			if(items[i]!=""){
				var position = items[i].indexOf("^[");

				var outerItems = items[i].substr(0,position);
				var innerItems = items[i].substr(position+2,items[i].length);	
				//index the parent if necessary
				if(outerItems.substr(0,5)=="index"){
					indexedData += items[i];
					//increment the increment
					increment = increment + 1;
				}else{
					increment = increment + 1;
					indexedData += "index" + nvDelimiter + increment + pairDelimiter + outerItems;					
				}
				//index the inner elements
				//use increment starting at zero
				indexedData += "^[";
				var innerIncrement = 0;
				innerElements = innerItems.split("|");
				for (var innerIndex=0; innerIndex < innerElements.length; innerIndex++) {
					if (innerElements[innerIndex] != "") {
						if(innerElements[innerIndex].substr(0,5)=="index"){
							indexedData += items[i] + lineDelimiter;
							//increment the increment
							innerIncrement = innerIncrement + 1;
						}else{
							innerIncrement = innerIncrement + 1;
							indexedData += "index" + nvDelimiter + innerIncrement + pairDelimiter + innerElements[innerIndex] + lineDelimiter;					
						}
					}
				}
				indexedData += "]|";
				
			}
		}
	}
	hiddenNode.value = indexedData;
	if(action=='edit')
		PopupUpdateHistoryBox(type);
	else
		PopupUpdateHistoryBoxView(type);	
		
	if(gBatchEntryDelete==false)
    {
		var nodeAddButton = getElementByIdOrByName("PopupAddEditButton" + type);
		nodeAddButton.setAttribute("className", "none");
			
	}	
	    
}

function PopupUpdateHistoryBox(type) {
	
	var historyBoxNode = getElementByIdOrByName("PopupHistoryBox" + type);
	
	var removeTR = historyBoxNode.firstChild;
		 while(removeTR != null){
			  var temp = removeTR.nextSibling;
			  historyBoxNode.removeChild(removeTR);
			  removeTR= temp;
	 	}

	var hiddenNode = getElementByIdOrByName("PopupData" + type);
	
	
	
	//do a check for the target string replace with cache if it exists
	if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
		hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
	}


	var items = hiddenNode.value.split("]|");

	if(items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				if(items[i]!=""){
								//	need to distinguish inner items vs. outer items

					var position = items[i].indexOf("^[");

					var outerItems = items[i].substr(0,position);
					var innerItems = items[i].substr(position+2,items[i].length);

					if(outerItems.search(/statusCd~I/)==-1) {		


							var newTR = document.createElement("tr");
							
							var pairs = outerItems.split("^");

							if (pairs.length > 1)	{
								identifier = pairs[0].split(nvDelimiter);

								//	add button
								//checks to see if noptions set, removes add button
								var nodeAddButton = document.createElement("input");
								if(gBatchEntryDelete)
    								{
								nodeAddButton.setAttribute("type", "button");
								nodeAddButton.setAttribute("value", "Add Susceptibility");
								nodeAddButton.onclick = PopupAddInnerElement;
								
								}
								var nodeAddButtonTD = document.createElement("td");
								nodeAddButtonTD.setAttribute("align", "right");
								
								

								
								//	need to do it this way because the function needs to be referenced by value
								
								nodeAddButton.setAttribute("batchIdentifier", type);
								nodeAddButton.setAttribute("identifier", identifier[1]);
								if(gBatchEntryDelete)
    								{
									nodeAddButtonTD.appendChild(nodeAddButton);
								}
								nodeAddButton = null;
								
								
								var editDeleteTD = document.createElement("td");
								editDeleteTD.width=70;
								editDeleteTD.setAttribute("align", "left");


								var aEdit = document.createElement("a");
								aEdit.setAttribute("href", "javascript:PopupEditOuter('" + type + "','" + identifier[1]+ "');");
								var editText = document.createTextNode("edit");
								aEdit.appendChild(editText);

								var newDivideText = document.createTextNode(" | ");
								var aDelete = document.createElement("a");
								aDelete.setAttribute("href", "javascript:PopupDeleteOuter('" + type + "','" + identifier[1] + "');");
								var deleteText = document.createTextNode("delete");
								aDelete.appendChild(deleteText);


								editDeleteTD.appendChild(aEdit);
								
								if(gBatchEntryDelete)
								{

									editDeleteTD.appendChild(newDivideText);
									editDeleteTD.appendChild(aDelete);
								}	


								newTR.appendChild(editDeleteTD);

								for (var j=0;j < pairs.length; j++) {
									var nameValue = pairs[j].split(nvDelimiter);
									var name = nameValue[0];
									var value = nameValue[1];

									//	have to find out if the element is text or select and choose the value or name
									name = name.replace(/\*\*\*/,"");
									var elementNode = getElementByIdOrByName(name);
									if (elementNode != null)	{
										if (elementNode.header )	{

											var displayText = "";
											var pattern = new RegExp("select");
											if (pattern.test(elementNode.type))	{

												for (var v=0; v < elementNode.options.length; v++)	{
													if (elementNode.options[v].value == value) {
														displayText = elementNode.options[v].text;
													}
												}
												
												if (displayText == '') 
												   displayText = value;
											}
											else if (elementNode.type == "text")	{
												displayText = value;
											}
											else {
												displayText = value;

											}
											var newTD = document.createElement("td");
											var newText =document.createTextNode(displayText);
											newTD.appendChild(newText);
											newTR.appendChild(newTD);
										}
									}
								}

								newTR.appendChild(nodeAddButtonTD);

								historyBoxNode.appendChild(newTR);
							}



			//	the inner batch elements


							var innerTR = document.createElement("tr");
							//innerTR.setAttribute("width","100%");
							innerTR.setAttribute("className","Shaded");
							var innerTD = document.createElement("td");
							innerTD.colSpan=50;
							innerTD.setAttribute("align", "left");
							var innerTABLE = document.createElement("table");
							innerTABLE.cellspacing=0;
							innerTABLE.cellpadding=1;
							innerTABLE.setAttribute("border", "0");
							innerTABLE.setAttribute("width", "100%");
							var innerBODY = document.createElement("tbody");


									innerElements = innerItems.split("|");

									for (var innerIndex=0; innerIndex < innerElements.length; innerIndex++) {
										if (innerElements[innerIndex].search(/statusCd~I/)==-1 && innerElements[innerIndex] != "") {

											innerPairs = innerElements[innerIndex].split("^");
											//need to find the first pair , the index and setup the edit and delete links
											var index = innerPairs[0].split(nvDelimiter);


													var senTR = document.createElement("tr");
													senTR.setAttribute("width", "100%");



													var editDeleteTD = document.createElement("td");
													editDeleteTD.width=100;
													editDeleteTD.setAttribute("align", "right");
													var aEdit = document.createElement("a");
													aEdit.setAttribute("href", "javascript:PopupEditInner('" + type + "','" + index[1]+ "','" + identifier[1] + "');");
													var editText = document.createTextNode("edit");
													aEdit.appendChild(editText);

													var newDivideText = document.createTextNode(" | ");
													var aDelete = document.createElement("a");
													aDelete.setAttribute("href", "javascript:PopupDeleteInner('" + type + "','" + index[1] + "','" + identifier[1] + "');");
													var deleteText = document.createTextNode("delete");
													aDelete.appendChild(deleteText);

													editDeleteTD.appendChild(aEdit);
													editDeleteTD.appendChild(newDivideText);
													editDeleteTD.appendChild(aDelete);
													senTR.appendChild(editDeleteTD);


												for (var j=0;j < innerPairs.length; j++) {
													if(innerPairs[j]!=""){

														var nameValue = innerPairs[j].split(nvDelimiter);
														var name = nameValue[0];
														var value = nameValue[1];
														//	have to find out if the element is text or select and choose the value or name

														var elementNode = getElementByIdOrByName(name);

														if (elementNode != null)	{

															if (elementNode.header)	{


																var displayText = "";
																var pattern = new RegExp("select");
																if (pattern.test(elementNode.type))	{

																	for (var v=0; v < elementNode.options.length; v++)	{
																		if (elementNode.options[v].value == value) {
																			displayText = elementNode.options[v].text;
																		}
																		
																	}
																			
																	if (displayText == '') 
																	   displayText = value;
		
																}
																else if (elementNode.type == "text")	{
																	displayText = value;
																}
																headerTD = document.createElement("td");
																var headerText =document.createTextNode(displayText);
																headerTD.appendChild(headerText);
																senTR.appendChild(headerTD);
															}
														}
													}
												}

										innerBODY.appendChild(senTR);

									}
							}
							innerTABLE.appendChild(innerBODY);
							innerTD.appendChild(innerTABLE);
							innerTR.appendChild(innerTD);
							historyBoxNode.appendChild(innerTR);
					}
				}// if check for empty string and statusCd=I
			}	// for each items
			
			
		}	// if items


		

}

/**
 * Description:	add a parent by appending to the hidden value string with the delimited values
 *
 * param @type
 */
function PopupAddParent(type) {
	if(CheckForChildWindow()){
		if (PopupValidate(type) == false) {	
			//
			var hiddenNode = getElementByIdOrByName("PopupData" + type);
			var parentNode = getElementByIdOrByName("PopupParent" + type);
			//	variable holding the parsed up new parent batch data
			var newParent = createDataString(parentNode).replace(/statusCd~/, "statusCd"+nvDelimiter+"A");
			
			
			
			//increase the increment
			increment = increment + 1;
			hiddenNode.value += "index"+ nvDelimiter + increment + pairDelimiter + PopupCreateEntitySearchData() + newParent + "[]|";

			

			//clearnestedElementsInputBox(type);
			PopupClearInputs(type);
			PopupUpdateHistoryBox(type);
		}
	}

}

/**
 * Description:	Add the inner element to the parent element
 *
 * param @type	name of of the nested type on the page
 */
function PopupAddInnerElement() {
	if(CheckForChildWindow()){

		var src=event.srcElement;
		var arg1=src.getAttribute("batchIdentifier");
		var arg2=src.getAttribute("identifier");


		//	need to display parent in edit mode
		var hiddenNode = getElementByIdOrByName("PopupData" + arg1);
			//	split up the string
		if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
			hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
		}



			var targettedData = "";
			var items = hiddenNode.value.split("]|");


					//	need to put in a target 
					for (var i=0; i < items.length; i++) {
						if(items[i]!=""){
							var position = items[i].indexOf("^[");

							var outerItems = items[i].substr(0,position);
							var innerItems = items[i].substr(position+2,items[i].length);

							var pairs = outerItems.split("^");
							var check = pairs[0].split(nvDelimiter);
								if (check[1] == arg2) {
									targettedData+= outerItems + "^[" + "$$$" + "]|";
								}
								else {
									targettedData+= outerItems + "^[" + innerItems + "]|";
								}
						}
					}
					//update the hidden node with the target prepared
					hiddenNode.value = targettedData;


					for (var i=0; i < items.length; i++) {
						if(items[i]!=""){


								var position = items[i].indexOf("^[");

								var outerItems = items[i].substr(0,position);
								var innerItems = items[i].substr(position+2,items[i].length);


								var pairs = outerItems.split("^");
								//	check the first name value pair which is the type selectbox to determine which one we are editing
								var check = pairs[0].split(nvDelimiter);
								if (check[1] == arg2) {
									//	open the child window and initialize the susceptibilities
									var x = 100;	//screen.availWidth;
									var y = 100;	//screen.availHeight;

									var ELR = getElementByIdOrByName("ELR");		
									//ChildWindowHandle = window.open("/nbs/wum/general_lab_susceptibility_create?OperationType=create","getData","left=" + x + ", top=" + y + ", width=610, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=no");
									ChildWindowHandle = window.open("/nbs/LoadAddObservationLab2.do?ContextAction=createSuscept&ELR="+ELR.value,"getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=no");
									
									var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
									cacheString = innerItems;
									break;

								}
						}
					}
	}
}

function PopupWait(innerDataStructure,editIndex){
	var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerDataStructure + "','"+ editIndex +"')", 1000);
}

function PopupPassToChildWindow(innerDataStructure,editIndex){
	childHiddenNode = ChildWindowHandle.getElementById("nestedElementsHiddenFieldSusceptibility");
	if(childHiddenNode!=null){
		childHiddenNode.value = innerDataStructure;
		if(editIndex==-1){

			//	have to activate the update history box function on the child window
			childHiddenNode.onfocus();
		} else{
			//activate the edit event of the child window

			childHiddenNode.setAttribute("selectedIndex",editIndex);
			childHiddenNode.onfocus();
		}
	}else
		PopupWait(innerDataStructure, editIndex);
}

function PopupPassTheInnerBack(){
	
}

function PopupEditOuter(type, identifier) {
	if(CheckForChildWindow()){

		var entitySearchNodeValue = getElementByIdOrByName("resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid-values");
										
		//	need to display parent in edit mode
		var hiddenNode = getElementByIdOrByName("PopupData" + type);
			//	split up the string
	
		
		if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
			hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
		}


			var items = hiddenNode.value.split("]|");
				if (items.length > 1)	{
					for (var i=0; i < items.length; i++) {
						if(items[i]!=""){
							var position = items[i].indexOf("^[");

							var outerItems = items[i].substr(0,position);
							var innerItems = items[i].substr(position+2,items[i].length);

							var pairs = outerItems.split("^");
							//	check the first name value pair which is the type selectbox to determine which one we are editing
							var check = pairs[0].split(nvDelimiter);
							if (check[1] == identifier) {
								for (var j=0; j < pairs.length; j++) {
									
									var nameValue = pairs[j].split(nvDelimiter);
									var name = nameValue[0];
									var value = nameValue[1];
									
									//for read only
									var readonly = false;
									if(name.search(/\*\*\*/)!=-1){ //true if found one
										readonly=true;
										name = name.replace(/\*\*\*/,"");
									}
									
									var node = getElementByIdOrByName(name);
									//entity search
									var entitySearchNode = getElementByIdOrByName("entity-"+name);
									
									if (entitySearchNode!=null)
									{
										
										var entityValues = value.split("!");
										
										
										var spans = entitySearchNode.getElementsByTagName("span");	
										for(var p=0;p<spans.length;p++)	{
											if(spans.item(p).id=="entity.name")
											{
												spans.item(p).innerHTML = entityValues[0];			
											} 
											else if(spans.item(p).id=="entity.localID")
											{	
												if(entityValues[1]!=null)
													spans.item(p).innerHTML = entityValues[1];
												else
													spans.item(p).innerHTML = "&nbsp;&nbsp;";
											}
											else if(spans.item(p).id=="entity.phoneNbrTxt")
											{	
												if(entityValues[2]!=null)
													spans.item(p).innerHTML = entityValues[2];
												else
													spans.item(p).innerHTML = "&nbsp;&nbsp;";
											}
											else if(spans.item(p).id=="entity.extensionTxt")
											{	
												if(entityValues[3]!=null)
													spans.item(p).innerHTML = entityValues[3];
												else
													spans.item(p).innerHTML = "&nbsp;&nbsp;";
											}
										}//for	
										
										
										
										entitySearchNodeValue.value = value;
										
										
										
									}
									//end entity search 
									
										if (node != null && node.name!="resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid-values"){
											if(readonly)
												node.disabled=true;
											else
												node.disabled=false;

											node.value = value;
											
											if(node.onchange && node.type!="select-one"){
												node.onchange();
												if(readonly && node.onkeypress){
													node.onkeypress();
												}
											}
										}
										
										//for plain text types
										
										if (node != null && node.nodeName=="SPAN")	
										{
											
											if (node.hasChildNodes())	
											{
												//alert(node.firstChild.nodeName + "  inner text = " + node.innerHTML);
												
													var deleteNode = node.removeChild(node.firstChild);
													deleteNode = null;
												
											}
											
											if (node.type == "select" && node.translated!="true")
											{
												
												value = findNameForValue("list"+ name, value);
												
											}		
											var textNode = document.createTextNode(value);
											node.appendChild(textNode);
											node.setAttribute("readonly", "true");
										}
										
										
								}
							}
						}
					}

					
					var nodeAddButton = getElementByIdOrByName("PopupAddEditButton" + type);
					nodeAddButton.value = nodeAddButton.value.replace(/Add\s/, "Update ");
					nodeAddButton.onclick = function() { PopupUpdateParent(type,identifier);};
					
					if(gBatchEntryDelete==false)
					{
						
						nodeAddButton.setAttribute("className", "visible");
					}	
				}
	}
	
	

}
/**
 * Description:	updates the parent data table
 *
 * param @type:
 * param @identifier
 */
function PopupUpdateParent(type, identifier){

	

	if(CheckForChildWindow()){
		if (PopupValidate(type) == false) {	
			//var error = false;
			//error = validateNestedElements(type);
			//if (error == false) {
				var PopupElementsTableNode = getElementByIdOrByName("PopupEntryTable" + type);
				var parentNode = getElementByIdOrByName("PopupParent" + type);
				var unused = "";
				var updatedInput = "";
				var hiddenNode = getElementByIdOrByName("PopupData" + type);
				if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
					hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
				}

				var items = hiddenNode.value.split("]|");
				if (items.length > 1)	{
					for (var i=0; i < items.length; i++) {
						if(items[i]!=""){
							var position = items[i].indexOf("^[");

							var outerItems = items[i].substr(0,position);
							var innerItems = items[i].substr(position+2,items[i].length);

							var pairs = outerItems.split("^");
							//	check the first name value pair which is the type selectbox to determine which one we are editing
							var check = pairs[0].split(nvDelimiter);
							if (check[1] == identifier) 
							{
							
								updatedInput = "index" + nvDelimiter + identifier + pairDelimiter + PopupCreateEntitySearchData() + createDataString(parentNode) + startChildDelimiter + innerItems + endChildDelimiter + lineDelimiter;				
								
							}else {
								unused += outerItems + pairDelimiter + startChildDelimiter + innerItems + endChildDelimiter + lineDelimiter;
							}
						}
					}
					hiddenNode.value = updatedInput + unused;
				}

			//}

			PopupClearInputs(type);
			PopupUpdateHistoryBox(type);
		}
	}
}

/**
 * Description:	prepares for edit of the inner types
 *
 * param @type:	identifies the popup type to do
 * param @identifier: identifies the line inside to act on
 */
function PopupEditInner(type, innerIdentifier, outerIdentifier){
	if(CheckForChildWindow()){
	

	
	//find the innerItems
			var hiddenNode = getElementByIdOrByName("PopupData" + type);
			if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
				hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
			}

			//	split up the string
			var targettedData = "";
					var items = hiddenNode.value.split("]|");
						
							
							//	need to put in a target 
							for (var i=0; i < items.length; i++) {
								if(items[i]!=""){
									var position = items[i].indexOf("^[");
									
									var outerItems = items[i].substr(0,position);
									var innerItems = items[i].substr(position+2,items[i].length);
									
									var pairs = outerItems.split("^");
									var check = pairs[0].split(nvDelimiter);
										if (check[1] == outerIdentifier) {
											targettedData+= outerItems + "^[" + "$$$" + "]|";
										}
										else {
											targettedData+= outerItems + "^[" + innerItems + "]|";
										}
								}
							}
							//update the hidden node with the target prepared
							hiddenNode.value = targettedData;
							
							
							for (var i=0; i < items.length; i++) {
								if(items[i]!=""){
														
								
										var position = items[i].indexOf("^[");
			
										var outerItems = items[i].substr(0,position);
										var innerItems = items[i].substr(position+2,items[i].length);
			
			
										var pairs = outerItems.split("^");
										//	check the first name value pair which is the type selectbox to determine which one we are editing
										var check = pairs[0].split(nvDelimiter);
										if (check[1] == outerIdentifier) {
											//	open the child window and initialize the susceptibilities
											var x = 100;	//screen.availWidth;
											var y = 100;	//screen.availHeight;
						
											//ChildWindowHandle = window.open("/nbs/wum/general_lab_susceptibility_create?OperationType=edit","getData","left=" + x + ", top=" + y + ", width=610, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no");
											var ELR = getElementByIdOrByName("ELR");	
											ChildWindowHandle = window.open("/nbs/LoadEditObservationLab2.do?ContextAction=editSuscept&ELR="+ELR.value,"getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=no");
											
											var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "','"+ innerIdentifier+"')", 3000);
											cacheString = innerItems;
											break;
			
										}
								}
							}
	}

}


function PopupDeleteOuter(type, identifier){
	if(CheckForChildWindow()){

		var unused = "";
		var used = "";
		var hiddenNode = getElementByIdOrByName("PopupData" + type);
		if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
			hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
		}

		//	split up the string

		var items = hiddenNode.value.split("]|");

		for (var i=0; i < items.length; i++) {
				if(items[i]!=""){
					var position = items[i].indexOf("^[");

					var outerItems = items[i].substr(0,position);
					var innerItems = items[i].substr(position+2,items[i].length);

					var pairs = outerItems.split("^");
					var check = pairs[0].split(nvDelimiter);
						if (check[1] == identifier) {
							used = items[i].replace(/statusCd~A/g, "statusCd"+nvDelimiter+"I") + "]|";
						}else{
							unused += items[i] + "]|";
						}
				}
		}

		hiddenNode.value = used + unused;
		PopupUpdateHistoryBox(type);
	}
}


function PopupDeleteInner(type, innerIdentifier, outerIdentifier){
	if(CheckForChildWindow()){

		var unused = "";
		var hiddenNode = getElementByIdOrByName("PopupData" + type);
		if(hiddenNode.value.search(/\$\$\$/)!=-1) {	
			hiddenNode.value = hiddenNode.value.replace(/\$\$\$/,cacheString);
		}

			//	split up the string

			var items = hiddenNode.value.split("]|");

			for (var i=0; i < items.length; i++) {
					if(items[i]!=""){
						var position = items[i].indexOf("^[");

						var outerItems = items[i].substr(0,position);
						var innerItems = items[i].substr(position+2,items[i].length);

						var pairs = outerItems.split("^");
						var check = pairs[0].split(nvDelimiter);
							if (check[1] == outerIdentifier) {
								used=outerItems + "^[";
								innerElements = innerItems.split("|");

								for (var innerIndex=0; innerIndex < innerElements.length; innerIndex++) {
									if (innerElements[innerIndex] != "") {
										var innerPairs = innerElements[innerIndex].split("^");
										var innerCheck = innerPairs[0].split(nvDelimiter);
										if (innerCheck[1] == innerIdentifier) {
											used += innerElements[innerIndex].replace(/statusCd~A/, "statusCd"+nvDelimiter+"I") + "|";	
										} else{
											used += innerElements[innerIndex] + "|";
										}
									}
								}

							}else{
								unused += items[i] + "]|";
							}
					}
		}

		hiddenNode.value = used + "]|" + unused;

		PopupUpdateHistoryBox(type);
	}
}
/**
 * Description:	utility function that clears out input element values
 *
 * param @node:	the container that holds all of the inputs
 */
function PopupClearInputs(type) {
	
	//for entity search
	
	entitySearchClear("entity-table-resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid");
	
	var PopupElementsTableNode = getElementByIdOrByName("PopupEntryTable" + type);
	var selects = PopupElementsTableNode.getElementsByTagName("select");
	var inputs = PopupElementsTableNode.getElementsByTagName("input");
	var textareas = PopupElementsTableNode.getElementsByTagName("textarea");
	
	var tdNodes = PopupElementsTableNode.getElementsByTagName("td");
	for( var i=0; i < tdNodes.length; i++) 
	{
		if (tdNodes.item(i).hasChildNodes() && tdNodes.item(i).firstChild.nodeName=="SPAN")	
		{
			var spanNode = tdNodes.item(i).firstChild;
			
				var deleteNode = spanNode.removeChild(spanNode.firstChild);
				deleteNode = null;
		}
		
	}

	for( var i=0; i < selects.length; i++) {
		var def = selects.item(i).getAttribute("default");
		if(def!=null){
			selects.item(i).value = def;
		}else{
			selects.item(i).selectedIndex = 0;
		}
		if(selects.item(i).onchange){
			selects.item(i).onchange();
		}
		if(selects.item(i).disabled==true)
			selects.item(i).disabled=false;
	}
	for( var i=0; i < inputs.length; i++) {
		if (inputs.item(i).type == "text" || inputs.item(i).type == "hidden")
			inputs.item(i).value = "";
		if(inputs.item(i).disabled==true)
			inputs.item(i).disabled=false;
	}
	for( var i=0; i < textareas.length; i++) {
		textareas.item(i).value = "";
	}
	selects = null;
	inputs = null;
	textareas = null;
			//default the button to inserting new entry

			var nodeAddButton = getElementByIdOrByName("PopupAddEditButton" + type);
			nodeAddButton.value = nodeAddButton.value.replace(/Update\s/, "Add ");
			nodeAddButton.onclick = function() { PopupAddParent(type);};
			
			if(gBatchEntryDelete==false)
			{

				nodeAddButton.setAttribute("className", "none");
			}	
					
}

/************************FOR VIEW MODE ONLY****************************/
/**
 * Description:	updates the gray history box based on the data structure string
 *
 * param @type	name of of the nested type on the page
 */
function PopupUpdateHistoryBoxView(type) {
	
	var historyBoxNode = getElementByIdOrByName("PopupHistoryBox" + type);

	var removeTR = historyBoxNode.firstChild;
		 while(removeTR != null){
			  var temp = removeTR.nextSibling;
			  historyBoxNode.removeChild(removeTR);
			  removeTR= temp;
	 	}

	var hiddenNode = getElementByIdOrByName("PopupData" + type);



	var items = hiddenNode.value.split("]|");

	if(items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				if(items[i]!=""){
								//	need to distinguish inner items vs. outer items

					var position = items[i].indexOf("^[");

					var outerItems = items[i].substr(0,position);
					var innerItems = items[i].substr(position+2,items[i].length);

					if(outerItems.search(/statusCd~I/)==-1) {		


							var newTR = document.createElement("tr");
							newTR.setAttribute("width","100%");
							
							var pairs = outerItems.split("^");
							
							if (pairs.length > 1)	{
								identifier = pairs[0].split(nvDelimiter);

								

								var detailsTD = document.createElement("td");
								detailsTD.width=70;
								detailsTD.setAttribute("align", "left");


								var aDetails = document.createElement("a");
								aDetails.setAttribute("href", "javascript:PopupDetailsOuter('" + type + "','" + identifier[1]+ "');");
								var detailsText = document.createTextNode("Details");
								aDetails.appendChild(detailsText);

								detailsTD.appendChild(aDetails);
								
								newTR.appendChild(detailsTD);

								for (var j=0;j < pairs.length; j++) {
									var nameValue = pairs[j].split(nvDelimiter);
									var name = nameValue[0];
									var value = nameValue[1];

									//	have to find out if the element is text or select and choose the value or name

									var elementNode = getElementByIdOrByName(name);
									
									
									if (elementNode != null)	{
									
										if (elementNode.header)	{
											
											var val1= value;
											if (elementNode.type == "select")
												value = findNameForValue("list" + name, value);
											var newText = document.createTextNode(value);

											var newTD = document.createElement("td");
											newTD.appendChild(newText);

											newTR.appendChild(newTD);
										}
									}
								}

								

								historyBoxNode.appendChild(newTR);
							}



			//	the inner batch elements


							var innerTR = document.createElement("tr");
							innerTR.setAttribute("width","100%");
							innerTR.setAttribute("className","Shaded");
							var innerTD = document.createElement("td");
							innerTD.colSpan=10;
							innerTD.setAttribute("align", "left");
							var innerTABLE = document.createElement("table");
							innerTABLE.setAttribute("border", "0");
							innerTABLE.setAttribute("width", "100%");
							var innerBODY = document.createElement("tbody");


									innerElements = innerItems.split("|");

									for (var innerIndex=0; innerIndex < innerElements.length; innerIndex++) {
										if (innerElements[innerIndex].search(/statusCd~I/)==-1 && innerElements[innerIndex] != "") {

											innerPairs = innerElements[innerIndex].split("^");
											//need to find the first pair , the index and setup the edit and delete links
											var index = innerPairs[0].split(nvDelimiter);


													var senTR = document.createElement("tr");
													senTR.setAttribute("width", "100%");



													var innerDetailsTD = document.createElement("td");
													innerDetailsTD.width=90;
													innerDetailsTD.setAttribute("align", "right");
													var innerDetails = document.createElement("a");
													innerDetails.setAttribute("href", "javascript:PopupDetailsInner('" + type + "','" + index[1]+ "','" + identifier[1] + "');");
													var innerDetailsText = document.createTextNode("Details");
													innerDetails.appendChild(innerDetailsText);

													

													innerDetailsTD.appendChild(innerDetails);
													
													senTR.appendChild(innerDetailsTD);


												for (var j=0;j < innerPairs.length; j++) {
													if(innerPairs[j]!=""){

														var nameValue = innerPairs[j].split(nvDelimiter);
														var name = nameValue[0];
														var value = nameValue[1];
														//	have to find out if the element is text or select and choose the value or name

														var elementNode = getElementByIdOrByName(name);

														if (elementNode != null)	{

															if (elementNode.header)	{
																if (elementNode.type == "select")
																	value = findNameForValue("list" + name, value);
																var newInnerText = document.createTextNode(value);

																var newInnerTD = document.createElement("td");
																newInnerTD.appendChild(newInnerText);

																senTR.appendChild(newInnerTD);
															}
														}
													}
												}

										innerBODY.appendChild(senTR);

									}
							}
							innerTABLE.appendChild(innerBODY);
							innerTD.appendChild(innerTABLE);
							innerTR.appendChild(innerTD);
							historyBoxNode.appendChild(innerTR);
					}
				}// if check for empty string and statusCd=I
			}	// for each items
			
			
		}	// if items




}

function PopupDetailsOuter(type,identifier){
	
	var hiddenNode = getElementByIdOrByName("PopupData" + type);
	
	
	
		var items = hiddenNode.value.split("]|");
	
		if(items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				if(items[i]!=""){
								//	need to distinguish inner items vs. outer items

					var position = items[i].indexOf("^[");
					var outerItems = items[i].substr(0,position);
					var innerItems = items[i].substr(position+2,items[i].length);
					var pairs = outerItems.split("^");
								//	check the first name value pair which is the type selectbox to determine which one we are editing
								var check = pairs[0].split(nvDelimiter);
								if (check[1] == identifier) {
									for (var j=0; j < pairs.length; j++) {
										if(pairs[j]!=""){
											var nameValue = pairs[j].split(nvDelimiter);
											var name = nameValue[0];
											var value = nameValue[1];
											
											var readonly = false;
											if(name.search(/\*\*\*/)!=-1){ //true if found one
												readonly=true;
												name = name.replace(/\*\*\*/,"");
											}
											
											var node = getElementByIdOrByName(name);
											
											//entity search
											var entitySearchNode = getElementByIdOrByName("entity-"+name);

											if (entitySearchNode!=null)
											{
												
												var entityValues = value.split("!");
												var spans = entitySearchNode.getElementsByTagName("span");	
												for(var p=0;p<spans.length;p++)	{
													if(spans.item(p).id=="entity.name")
													{
														spans.item(p).innerHTML = entityValues[0];			
													} 
													else if(spans.item(p).id=="entity.localID")
													{	
														if(entityValues[1]!=null)
															spans.item(p).innerHTML = entityValues[1];
														else
															spans.item(p).innerHTML = "&nbsp;&nbsp;";														
													}
													else if(spans.item(p).id=="entity.phoneNbrTxt")
													{	
														if(entityValues[2]!=null)
															spans.item(p).innerHTML = entityValues[2];
														else
															spans.item(p).innerHTML = "&nbsp;&nbsp;";
													}
													else if(spans.item(p).id=="entity.extensionTxt")
													{	
														if(entityValues[3]!=null)
															spans.item(p).innerHTML = entityValues[3];	
														else
															spans.item(p).innerHTML = "&nbsp;&nbsp;";
													}
												}//for		



											}
											//end entity search 
											
											
											if (node != null && node.type!="hidden")	{
												if (node.hasChildNodes())	{
													var deleteNode = node.removeChild(node.firstChild);
													deleteNode = null;
												}
												if (node.type == "select")
													value = findNameForValue("list"+ name, value);
					
												var textNode = document.createTextNode(value);
												node.appendChild(textNode);
											}
										}
									}
								}
				}
			}
		}
}

function PopupDetailsInner(type, innerIdentifier, outerIdentifier){

	var hiddenNode = getElementByIdOrByName("PopupData" + type);

	
	var items = hiddenNode.value.split("]|");
	
	for (var i=0; i < items.length; i++) {
			if(items[i]!=""){


					var position = items[i].indexOf("^[");

					var outerItems = items[i].substr(0,position);
					var innerItems = items[i].substr(position+2,items[i].length);


					var pairs = outerItems.split("^");
					//	check the first name value pair which is the type selectbox to determine which one we are editing
					var check = pairs[0].split(nvDelimiter);
					if (check[1] == outerIdentifier) {
						//	open the child window and initialize the susceptibilities
						var x = 100;	//screen.availWidth;
						var y = 100;	//screen.availHeight;

						//ChildWindowHandle = window.open("/nbs/wum/general_lab_susceptibility_view?OperationType=view","getData","left=" + x + ", top=" + y + ", width=610, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no");
						
						var ELR = getElementByIdOrByName("ELR");							
						ChildWindowHandle = window.open("/nbs/LoadViewObservationLab2.do?ContextAction=viewSuscept&ELR="+ELR.value,"getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=no");
						
						var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "','"+ innerIdentifier+"')", 1000);
						
						break;

					}
			}
	}
}


//*********************		function to create by pressing the enter key
function PopupEntryKeySubmit(tbody){
	
	var key=event.keyCode
		if (key==13) { 
			turnOffParentSubmit = true;
			var name = tbody.id.split("PopupParent");
			buttonNode = getElementByIdOrByName("PopupAddEditButton" + name[1]);
			buttonNode.click();
	}
	
}

//*********************		create entity search parse string
function PopupCreateEntitySearchData()
{
	var dataNode = getElementByIdOrByName("resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid-values");
	
	
	
	return "table-resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid~" + dataNode.value.replace(/\s /g,"").replace(/\^/g,"!") +"^"

}



//*********************		VALIDATION ON ELEMENT ADD
function PopupValidate(type) {
	var error = false;

		var PopupTableNode = getElementByIdOrByName("PopupEntryTable" + type);
		var inputNodes = PopupTableNode.getElementsByTagName("input");
		var selectNodes = PopupTableNode.getElementsByTagName("select");
		var textareaNodes = PopupTableNode.getElementsByTagName("textarea");

		/* VL  Erase errors for all possible elements.  Errors that are displayed
		 *     in a separate line, <TR>, under the elements being validate.
		 */

		for(var i=0; i < inputNodes.length; i++) {
			if ( (inputNodes.item(i).type == "text") ||
			     (inputNodes.item(i).type == "checkbox") ) {
				eraseErrorCellForElement(inputNodes.item(i));
			}
		}
		for(var i=0; i<selectNodes.length; i++ ) {
			eraseErrorCellForElement(selectNodes.item(i));
		}
		for(var i=0; i<textareaNodes.length; i++ ) {
			eraseErrorCellForElement(textareaNodes.item(i));
		}



		for(var i=0; i < inputNodes.length; i++)	{
			if ( (inputNodes.item(i).type == "text") ||
			     (inputNodes.item(i).type == "checkbox") ) {
				if( validate(inputNodes.item(i)) == false) {
					error = true;
				}
			}  
		}
		for(var i=0; i < selectNodes.length; i++)	{
			if( validate(selectNodes.item(i)) == false) {
				error = true;
			}
		}

		for(var i=0; i < textareaNodes.length; i++)	{
			if( validate(textareaNodes.item(i)) == false) {
				error = true;
			}
		}

		var errorTD = getElementByIdOrByName("nestedErrorMsg" + type);
		if (error == true)
			errorTD.setAttribute("className", "error");
		else
			errorTD.setAttribute("className", "none");
	return error;
}




//*********************		THIS IS FOR STRUTS


/**
 * Description:need to create hidden elements with struts style naming convention for form to object translation
 *
 * param @type	name of of the nested type on the page
 * param @type	name of of the nested type on the page
 */
function PopupCreateHiddenInputsForStruts() {
	
	var inputNodes = document.getElementsByTagName("input");
	var parentCount=0;
	var childCount=0;
		
			
	
	for (var i=0; i < inputNodes.length; i++) {
		if (inputNodes.item(i).type == "hidden") {
			var hiddenNode = inputNodes.item(i);
			//	! check for a specific pattern in the id of the hidden field, batch entry hidden field containing all the name value pairs
			var patternBatchEntry = new RegExp("PopupData");

			if (patternBatchEntry.test(hiddenNode.id))	{
				var items = hiddenNode.value.split("]|");
				// need to split up the parent vs the children
				for(var j=0;j<items.length;j++) {
					if (items[j]!="") {
						
						var position = items[j].indexOf("^[");
											
						var parent = items[j].substr(0,position);
						var children = items[j].substr(position+2,items[j].length);
						
						// now i can iterate through and create the hidden elements
						
						var elements = parent.split("^");
						
						
							for (var k=0; k < elements.length; k++) {
								if(elements[k]!=""){
									var pair = elements[k].split(nvDelimiter);
									var name = pair[0];
									var value = pair[1];
									// need to change the index for struts
										//	create the correct index
											var index = "[" + parentCount + "]";
										//	get the corrected index for the hidden variable i'm about to create, use regular expression to replace
										var corrected = name.replace(/\[\i\]/, index);
									var outerStrutsNode = document.createElement("input");
									outerStrutsNode.setAttribute("type", "hidden");
									outerStrutsNode.setAttribute("value", value);
									outerStrutsNode.setAttribute("name", corrected);
									var formNode = getElementByIdOrByName("nedssForm");
									formNode.appendChild(outerStrutsNode);
									//	need to create the child hidden form elements now
									//	maybe multiple children
								}
							}
							//	need to create the child hidden form elements now
							//	maybe multiple children
							var innerElements = children.split("|");
							for (var l=0; l < innerElements.length; l++) {
								if(innerElements[l]!=""){
									var innerElement = innerElements[l].split("^");
									for (var m=0; m < innerElement.length; m++) {
										if(innerElement[m]!=""){
											var pair = innerElement[m].split(nvDelimiter);
											var name = pair[0];
											var value = pair[1];
											// need to change the index for struts
												//	create the correct index
													var outerIndex = "[" + parentCount + "]";
													var innerIndex = "[" + childCount + "]";
												//	get the corrected index for the hidden variable i'm about to create, use regular expression to replace
												var outerCorrected = name.replace(/\[\i\]/, outerIndex);
												var totalCorrected = outerCorrected.replace(/\[\j\]/, innerIndex);
											var strutsNode = document.createElement("input");
											strutsNode.setAttribute("type", "hidden");
											strutsNode.setAttribute("value", value);
											strutsNode.setAttribute("name", totalCorrected);
											var formNode = getElementByIdOrByName("nedssForm");
											formNode.appendChild(strutsNode);
										}
									}
									//need to increment the inner
									childCount++;
								}
							}
							
							parentCount++;
							childCount=0;
					}
				}
				hiddenNode.value="";	
			}//if popup hidden node
			


		}//if
	}//for

}