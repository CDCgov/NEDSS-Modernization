/**
 * Title:        nestedElements controller
 * Description:  does the client side presentation processing of nested data
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       10/12/2001 Jay Kim
 * @modified     10/12/2001 Jay Kim
 * @version      1.0.0, 10/12/2001
 */



function BatchEntryInitialize(type){

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
	//global - uninitialized data
	popupBackupData = hiddenNode.value;
	var items = hiddenNode.value.split("|");   
	var indexed = "";

		for (var i=0; i < items.length; i++) {
			if(items[i]!=""){
				if(items[i].substr(0,5)=="index"){
					indexed += items[i] + lineDelimiter;
					//increment the increment
					increment = increment + 1;
				}else{
					increment = increment + 1;
					indexed += "index" + nvDelimiter + increment + pairDelimiter + items[i] + lineDelimiter;
				}
			}
		}
	hiddenNode.value=indexed;
	updatenestedElementsHistoryBox(type);

	if(hiddenNode.selectedIndex){
		editnestedElementsType(type, hiddenNode.selectedIndex);
	}
    
}


/**
 * Description:	adds a new type to multiple data types
 *
 * param @type	name of of the nested type on the page
 */
function addnestedElementsType(type) {
	var error2TD = getElementByIdOrByName("error2");
	if( error2TD ) error2TD.setAttribute("className", "none");

	var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);

		if (validateNestedElements(type) == false) {
			var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);

			var oldValues = hiddenNode.value;

			//create the string of data
			var newTypeInput = createDataString(nestedElementsTableNode).replace(/statusCd~/, "statusCd"+ nvDelimiter + "A");
			increment = increment + 1;

			// this if for default, you may not have more than one default
			if(newTypeInput.search(/~default/) > 0){
				// we have a default in the edited string, now remove from the unused string
				oldValues = oldValues.replace(/~default/g,"~");

			}



			hiddenNode.value = oldValues + "index" + nvDelimiter + increment + pairDelimiter + newTypeInput + lineDelimiter;

			//alert(hiddenNode.value);

			clearnestedElementsInputBox(type);

			updatenestedElementsHistoryBox(type);

			newTypeInput = null;
			hiddenNode = null;
		}
}
/**
 * Description:	creates the data string stored in a hidden variable for each multiple data type element
 *
 * param @node	the tbody object that contains all the input types for that nested element
 */
function createDataString(node) {

	var tdNodes = node.getElementsByTagName("td");

	var data = "";

	for( var i=0; i < tdNodes.length; i++) {
		var element = tdNodes.item(i).firstChild;
		 while(element != null && getCorrectAttribute(element,"className",element.className);!="none"){
			  var temp = getCorrectAttribute(element,"nextSibling",element.nextSibling);
				
			if (getCorrectAttribute(element,"nodeName",element.nodeName) == "SPAN" && element.id!==null &&(getCorrectAttribute(element,"type",element.type=="text"||getCorrectAttribute(element,"type",element.type=="select"||getCorrectAttribute(element,"type",element.type=="textarea")) 
				{
					if(element.firstChild!=null)
					{
						data +=element.id + nvDelimiter + element.firstChild.nodeValue + pairDelimiter;
						element.setAttribute("translated", "true");
					}
				}	
				
			  else if (getCorrectAttribute(element,"type",element.type) == "text") {
					//updateForIllegalChars(element);
					data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			  }
			  else if (getCorrectAttribute(element,"type",element.type) == "select-one") {
				  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			  }
			  else if (getCorrectAttribute(element,"type",element.type) == "textarea") {
				  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			  }
			  else if (getCorrectAttribute(element,"type",element.type) == "hidden" && getCorrectAttribute(element,"name",element.name)!=null && getCorrectAttribute(element,"name",element.name)!="") {
				  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			  }
			  else if (getCorrectAttribute(element,"type",element.type) == "checkbox") {
			  	if(getCorrectAttribute(element,"checked",element.checked)==true)
			  		data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			  	else
			  		data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + "" + pairDelimiter;

			  }
				
			  element = temp;


		}

	}

	return data;
}



/**
 * Description:	need to do this for selects getting data from the backend, has to wait for the drop down to be
 *				populated first before we begin giving the values to the elements inside the white box
 * param @node
 */

function editnestedElementsType(type, identifier) {
	
	gBatchEntryIndex=identifier;
	
	var isThereOne=false;
	resetNestedElementsType(type);
	//clearnestedElementsInputBox(type);

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
	//	split up the string
	var items = hiddenNode.value.split("|");
	if (items.length > 1)	{
		for (var i=0; i < items.length; i++) {
			var pairs = items[i].split("^");
			//	check the first name value pair which is the type selectbox to determine which one we are editing
			var check = pairs[0].split(nvDelimiter);
			if (check[1] == identifier) {
				for (var j=0; j < pairs.length; j++) {
					if(pairs[j]!=""){
						var nameValue = pairs[j].split(nvDelimiter);
						var name = nameValue[0];
						var value = nameValue[1];
						var node = getElementByIdOrByName(name);
						if (node != null) {
							if(node.type=="select-one" && node.onchange){
								node.value = value;
								node.onchange();
								isThereOne=true;
							}
						}
					}

				}
			}
		}

	}
	//	wait for the event to finish before updating the edit county
 	if(isThereOne){
 		var timeoutInt = window.setTimeout("editnestedElementsType2('" + type + "','" + identifier + "')", 1500);
 	}
 	else
 		editnestedElementsType2(type, identifier);

}

//	when users selects to edit an input item
function editnestedElementsType2(type, identifier) {

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
	//	split up the string
	var items = hiddenNode.value.split("|");
	if (items.length > 1)	{
		for (var i=0; i < items.length; i++) {
			var pairs = items[i].split("^");
			//	check the first name value pair which is the type selectbox to determine which one we are editing
			var check = pairs[0].split(nvDelimiter);
			if (check[1] == identifier) {
				for (var j=0; j < pairs.length; j++) {
					if(pairs[j]!=""){
						var nameValue = pairs[j].split(nvDelimiter);
						var name = nameValue[0];
						var value = nameValue[1];
						var node = getElementByIdOrByName(name);
						if (node != null) {
							if(node.type!="checkbox")
								node.value = value;

							if(node.onchange && node.type!="select-one"){
								node.onchange();
							}
							if(node.type=="checkbox" && value!="")
								node.checked=true;
							if(node.type=="checkbox" && value=="")
								node.checked=false;

						}
					}

				}
			}
		}
		//	update the add information link with update information
		var nodeAddButton = getElementByIdOrByName("BatchEntryAddButton" + type);
		nodeAddButton.value = nodeAddButton.value.replace(/Add\s/, "Update ");
		nodeAddButton.onclick = function() { updatenestedElementsType(type,identifier);};


	}

}

//	when user has finished editing and wants to save changes
function updatenestedElementsType(type, identifier) {
	var error2TD = getElementByIdOrByName("error2");
	if( error2TD ) error2TD.setAttribute("className", "none");

	var error = false;
	error = validateNestedElements(type);
	if (error == false) {
		var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
		var unused = "";
		var editedInput = "";
		var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
		var items = hiddenNode.value.split("|");

		if (items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				if (items[i]!=""){
					var pairs = items[i].split("^");
					//	check the first name value pair which is the type selectbox to determine which one we are editing
					check = pairs[0].split(nvDelimiter);

					if (check[1] == identifier) {
						editedInput = "index" + nvDelimiter + identifier + pairDelimiter +createDataString(nestedElementsTableNode);
						//	need to change the type drop down value to what is being edited


					}
					else {
						unused += items[i] + "|";

					}
				}
			}

			//if the editedInput has default then remove the default from everywhere else

			if(editedInput.search(/~default/) > 0){
				// we have a default in the edited string, now remove from the unused string
				unused = unused.replace(/~default/g,"~");

			}


			hiddenNode.value = editedInput + "|" + unused;
			//	need to enable  the type select box so that users cannot select to add info to a new type during edit mode

			//var SelectNode = nestedElementsTableNode.getElementsByTagName("select").item(0);
			//SelectNode.disabled = false;
			updatenestedElementsHistoryBox(type);
			clearnestedElementsInputBox(type);
		}

	}

}

//	need to update the history box based on what the user has changed
function updatenestedElementsHistoryBox(type) {

	var counter = 0;
	var counterColIndex;
	var bCounterOn = false;

	var historyBoxNode = getElementByIdOrByName("nestedElementsHistoryBox|" + type);



	var removeTR = historyBoxNode.firstChild;
	 while(removeTR != null){
		  var temp = removeTR.nextSibling;
		  historyBoxNode.removeChild(removeTR);
		  removeTR= temp;
	 }



	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);


	var items = hiddenNode.value.split("|");

	if(items.length > 1)	{
		var lineCounter = 0;
		for (var i=0; i < items.length; i++) {

			if(items[i].search(/statusCd~I/)==-1 && items[i]!=""){

							lineCounter++;
							var newTR = document.createElement("tr");

							if (i % 2 == 0)
								newTR.setAttribute("className","NotShaded");
							else
								newTR.setAttribute("className","Shaded");


							var pairs = items[i].split("^");

							if (pairs.length > 1)	{
								identifier = pairs[0].split(nvDelimiter);


							//	the edit and delete actions
											var editDeleteTD = document.createElement("td");
											editDeleteTD.width=75;
											editDeleteTD.setAttribute("align", "left");
												var aEdit = document.createElement("a");
													aEdit.setAttribute("href", "javascript:editnestedElementsType('" + type + "','" + identifier[1]+ "');");
												var editText = document.createTextNode("Edit");
												aEdit.appendChild(editText);



												var newDivideText = document.createTextNode(" | ");
												var aDelete = document.createElement("a");
													aDelete.setAttribute("href", "javascript:deletenestedElementsType('" + type + "','" + identifier[1] + "');");
												var deleteText = document.createTextNode("Delete");
												aDelete.appendChild(deleteText);



											editDeleteTD.appendChild(aEdit);
											if(gBatchEntryDelete)
											{

												editDeleteTD.appendChild(newDivideText);
												editDeleteTD.appendChild(aDelete);
											}

											newTR.appendChild(editDeleteTD);
											
											// Display line numbers if needed.
											var tableNode = getElementByIdOrByName("nestedElementsTable|" + type);
											//	alert( type + "  " + tableNode );
											if( (tableNode!=null) && (tableNode.showLineNumbers=="true") ) {
											var lineNum = i+1;
											var aNumber = document.createTextNode( parseInt(lineCounter) );
											var lineNumberTD = document.createElement("td");
											lineNumberTD.width=50;
											lineNumberTD.appendChild( aNumber );
											newTR.appendChild( lineNumberTD );
											}
											
								
								
											

								for (var j=0;j < pairs.length; j++) {
									if(pairs[j]!=""){
										var nameValue = pairs[j].split(nvDelimiter);
										var name = nameValue[0];
										var value = nameValue[1];
										//	have to find out if the element is text or select and choose the value or name

										var elementNode = getElementByIdOrByName(name);

										if (elementNode != null)	
										{
											//append partner information to header fields
											if(elementNode.partner)
											{
												var displayText = "";
												var pattern = new RegExp("select");
												if (pattern.test(elementNode.type))	{

													for (var v=0; v < elementNode.options.length; v++)	{
														if (elementNode.options[v].value == value) {
															displayText = elementNode.options[v].text;
														}
													}
													
													var oldTD = newTR.childNodes(newTR.childNodes.length-1);
													var newText =document.createTextNode(" " + displayText);
													oldTD.appendChild(newText);
													
													
												}

											}
											//update history box with designated header fields
										
											if (elementNode.header)	{

												var displayText = "";
												var pattern = new RegExp("select");
												if (pattern.test(elementNode.type))	{

													for (var v=0; v < elementNode.options.length; v++)	{
														if (elementNode.options[v].value == value) {
															displayText = elementNode.options[v].text;
														}
													}
												}
												else if (elementNode.type == "text")	
												{
													displayText = value;
													
													
													
												}
												else if (elementNode.type == "hidden")	{
													displayText = value;
												}
												else if (elementNode.type == "textarea")	{
													displayText = value;
												}
												else if (elementNode.type == "checkbox")	{
													displayText = value;
												}
												
												
												var newTD = document.createElement("td");
												var newText =document.createTextNode(displayText);
												newTD.appendChild(newText);
												newTR.appendChild(newTD);
												
												

												
												if(elementNode.counter=="on")
												{
													counterColIndex = j;
													if(!isNumeric1(value))
													  value = 0;
													if(!isNumeric1(counter))
													  counter = 0;
													//alert("value = " + value + "  counter = " + counter);  
													counter = parseInt(counter) + parseInt(value);
													//if(isNaN(counter))
													   //counter = 0;
													bCounterOn = true;
												}










											}
										}
									}
								}


								historyBoxNode.appendChild(newTR);

							}


							
			}	//if this isn't an inactive one
		}
		
		
		
									
									
									
									
									
									
									// stick in the total
									
									if(bCounterOn)
									{
										var newTR = document.createElement("tr");
										for (var i=1;i <= counterColIndex; i++) 
										{
											if(i == parseInt(counterColIndex)-1)
											{
												var newTD = document.createElement("td");
												newTD.setAttribute("align", "right");
												newTD.setAttribute("className", "boldTenBlack");
												var displayText = "Total: ";
												var newText =document.createTextNode(displayText);
												newTD.appendChild(newText);
												newTR.appendChild(newTD);
											}
											else if(i == parseInt(counterColIndex))
											{
												var newTD = document.createElement("td");
												newTD.setAttribute("align", "left");
												newTD.setAttribute("className", "boldTenBlack");
												var displayText = counter;
												var newText =document.createTextNode(displayText);
												newTD.appendChild(newText);
												newTR.appendChild(newTD);
											}
											else
											{
												var newTD = document.createElement("td");
												var displayText = "";
												var newText =document.createTextNode(displayText);
												newTD.appendChild(newText);
												newTR.appendChild(newTD);
											}
											
										}
										historyBoxNode.appendChild(newTR);
									}
									
									
									
									
							
		


















		
		
		
		
		
	}



}

//	clear the input elements after user creates a new one
function clearnestedElementsInputBox(type) {

	var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
	var selects = nestedElementsTableNode.getElementsByTagName("select");
	var inputs = nestedElementsTableNode.getElementsByTagName("input");
	var textareas = nestedElementsTableNode.getElementsByTagName("textarea");

	for( var i=0; i < selects.length; i++) {
		selects.item(i).selectedIndex = 0;
		if(selects.item(i).onchange){
			selects.item(i).onchange();
		}
	}
	for( var i=0; i < inputs.length; i++) {
		if ((inputs.item(i).type == "text" || inputs.item(i).type == "hidden")&&(inputs.item(i).name.search("theObservationDT.cd")==-1))
			inputs.item(i).value = "";
		if (inputs.item(i).type == "checkbox")
			inputs.item(i).checked=false;
	}
	for( var i=0; i < textareas.length; i++) {
		textareas.item(i).value = "";
	}
	selects = null;
	inputs = null;
	textareas = null;

			//default the button to inserting new entry

			var nodeAddButton = getElementByIdOrByName("BatchEntryAddButton" + type);
			nodeAddButton.value = nodeAddButton.value.replace(/Update/, "Add");
			nodeAddButton.onclick = function() { addnestedElementsType(type);};


}
/**
 * Description:	update the status code to I
 *
 * param @type	name of of the nested type on the page
 * param @identifier name of the specific data element being deleted
 */
function deletenestedElementsType(type, identifier) {
	var unused = "";
	var used = "";
	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
	var items = hiddenNode.value.split("|");
	if (items.length > 1)	{
		for (var i=0; i < items.length; i++) {
			var pairs = items[i].split("^");
			//	check the first name value pair which is the type selectbox to determine which one we are editing
			check = pairs[0].split(nvDelimiter);
			if (check[1] == identifier) {
				//	need to update the status code of the string
				used = items[i].replace(/statusCd~A/, "statusCd"+ nvDelimiter+"I");

			}
			else {
				unused += items[i] + "|";
			}
		}
		hiddenNode.value = used + "|" + unused;
		updatenestedElementsHistoryBox(type);
	}

	unused = null;
	hiddenNode = null;
	items = null;


	clearnestedElementsInputBox(type);
}


/**
 * Description:	function used in validation procedure, resets all the error msgs that may appear
 *
 * param @type	name of of the nested type on the page
 */

function resetNestedElementsType(type)
{

	var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
	var spans = nestedElementsTableNode.getElementsByTagName("span");








		var inputNodes = nestedElementsTableNode.getElementsByTagName("input");
		var selectNodes = nestedElementsTableNode.getElementsByTagName("select");
		var textareaNodes = nestedElementsTableNode.getElementsByTagName("textarea");

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















	var errorTD = getElementByIdOrByName("nestedErrorMsg" + type);
	errorTD.setAttribute("className", "none");


	for(var i=0; i < spans.length; i++)	{
		if(spans.item(i)!=null){
				var removeTR = spans.item(i).firstChild;
				while(removeTR != null){
				  var temp = removeTR.nextSibling;
				  spans.item(i).removeChild(removeTR);
				  removeTR= temp;
				}
		}
	}
}

function BatchEntryKeySubmit(tbody){

	var key=event.keyCode
	if (key==13) {
		turnOffParentSubmit = true;

		buttonNode = getElementByIdOrByName("BatchEntryAddButton" + tbody.type);
		buttonNode.click();
	}


}

//*********************		THIS IS FOR VIEW PAGES ONLY


/**
 * Description:
 *
 * param @type	name of of the nested type on the page
 * param @type	name of of the nested type on the page
 */
function ViewNestedElementsType(type, identifier) {
	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
	//	split up the string

	var items = hiddenNode.value.split("|");
	for (var i=0; i < items.length; i++) {
		if(items[i]!=""){
			var pairs = items[i].split("^");
			//	check the first name value pair which is the type selectbox to determine which one we are editing
			var check = pairs[0].split(nvDelimiter);
			if (check[1] == identifier) {
				for (var j=0; j < pairs.length; j++) {
					if(pairs[j]!=""){
						var nameValue = pairs[j].split(nvDelimiter);
						var name = nameValue[0];
						var value = nameValue[1];

						var node = getElementByIdOrByName(name);

						if (node != null && node.type!="hidden")	{
							if (node.hasChildNodes())	
							{
								var deleteNode = node.removeChild(node.firstChild);
								deleteNode = null;
							}
							
							if (node.conditional)
							{
								var payloadTbody = getElementByIdOrByName("nestedElementsControllerPayload" + node.conditional);
								

								if(value==node.trigger)
									payloadTbody.setAttribute("className", "visible");
								else
									payloadTbody.setAttribute("className", "none");
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


function BatchEntryInitializeForView(type){

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
	//global - uninitialized data
	popupBackupData = hiddenNode.value;
	var items = hiddenNode.value.split("|");
	var indexed = "";

		for (var i=0; i < items.length; i++) {
			if(items[i]!=""){
				if(items[i].substr(0,5)=="index"){
					indexed += items[i] + lineDelimiter;
					//increment the increment
					increment = increment + 1;
				}else{
					increment = increment + 1;
					indexed += "index" + nvDelimiter + increment + pairDelimiter + items[i] + lineDelimiter;
				}
			}
		}
	hiddenNode.value=indexed;
	BatchEntryUpdatedViewHistoryBox(type);

	if(hiddenNode.selectedIndex){
		ViewNestedElementsType(type, hiddenNode.selectedIndex);
	}


}


function BatchEntryUpdatedViewHistoryBox(type) {

	var historyBoxNode = getElementByIdOrByName("nestedElementsHistoryBox|" + type);

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);


	var items = hiddenNode.value.split("|");

	for (var i=0; i < items.length; i++) {
		if(items[i].search(/statusCd~I/)==-1 && items[i]!=""){
						var newTR = document.createElement("tr");
						newTR.setAttribute("width","100%");


						if (i % 2 == 0)
							newTR.setAttribute("className","NotShaded");
						else
							newTR.setAttribute("className","Shaded");


						var pairs = items[i].split("^");
						identifier = pairs[0].split(nvDelimiter);

						var DetailsTD = document.createElement("td");
						DetailsTD.setAttribute("align", "left");
						var DetailsLink = document.createElement("a");
						DetailsLink.setAttribute("href", "javascript:ViewNestedElementsType('" + type + "','" + identifier[1]+ "');");
						var DetailsText = document.createTextNode("Details");
						DetailsLink.appendChild(DetailsText);

						DetailsTD.appendChild(DetailsLink);

							newTR.appendChild(DetailsTD);



						for (var j=0; j < pairs.length; j++) {
							if(pairs[j]!=""){
								var nameValue = pairs[j].split(nvDelimiter);
								var name = nameValue[0];
								var value = nameValue[1];
								//	have to find out if the element is text or select and choose the value or name


								var elementNode = getElementByIdOrByName(name);

								if (elementNode != null)	
								{
								
									//append partner information to header fields
									if(elementNode.partner)
									{
										if (elementNode.type == "select")
										{
											value = findNameForValue("list" + name, value);

											var oldTD = newTR.childNodes(newTR.childNodes.length-1);
											var newText =document.createTextNode(" " + value);
											oldTD.appendChild(newText);


										}

									}
									//update history box with designated header fields
								
								
									if (elementNode.header)	
									{

										if (elementNode.type == "select")
											value = findNameForValue("list" + name, value);
										var newText = document.createTextNode(value);

										var newTD = document.createElement("td");
										newTD.appendChild(newText);

										newTR.appendChild(newTD);

									}
								}
							}

						}

							historyBoxNode.appendChild(newTR);

					}
		}
}



//*********************		VALIDATION ON ELEMENT ADD
function validateNestedElements(type) {
	var error = false;


		//check the function array for functions
				for (var i=0;i<batchEntryValidationArray.length;i++){
					if(batchEntryValidationArray[i]!=null){
						error = batchEntryValidationArray[i]();

					}
		}


		var NestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
		var inputNodes = NestedElementsTableNode.getElementsByTagName("input");
		var selectNodes = NestedElementsTableNode.getElementsByTagName("select");
		var textareaNodes = NestedElementsTableNode.getElementsByTagName("textarea");

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
			     (inputNodes.item(i).type == "checkbox") ||
			     (inputNodes.item(i).type == "hidden")) {
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
function BatchEntryCreateHiddenInputs() {
	var inputNodes = document.getElementsByTagName("input");
	var elementCount = 0;
	var raceCount = 0;

	for (var i=0; i < inputNodes.length; i++) {
		if (inputNodes.item(i).type == "hidden") {
			var hiddenNode = inputNodes.item(i);
			//	! check for a specific pattern in the id of the hidden field, batch entry hidden field containing all the name value pairs
			var patternBatchEntry = new RegExp("nestedElementsHiddenField");

			if (patternBatchEntry.test(hiddenNode.id))	{

				//need to reset the counter
				elementCount=0;
				//	split up each entry
				var entries = hiddenNode.value.split("|");
				//	j will be the index for the struts index
				for (var j=0; j < entries.length; j++) {
					//	split up each element
					if(entries[j]!=""){
						var elements = entries[j].split("^");
						for (var k=0; k < elements.length; k++) {
							if(elements[k]!=""){
								var pair = elements[k].split(nvDelimiter);
								var name = pair[0];
								var value = pair[1];
								// need to change the index for struts
									//	create the correct index
										var index = "[" + elementCount + "]";
									//	get the corrected index for the hidden variable i'm about to create, use regular expression to replace
									var corrected = name.replace(/\[\D\]/, index);




								var strutsNode = document.createElement("input");
								strutsNode.setAttribute("type", "hidden");
								strutsNode.setAttribute("value", value);
								strutsNode.setAttribute("name", corrected);
								var formNode = getElementByIdOrByName("nedssForm");
								formNode.appendChild(strutsNode);
							}
						}
						elementCount++;
					}//if
				}//for
				//clear out the hidden variable , no need to pass to the backend
				hiddenNode.value="";
			}//if


		}
	}

}

		function isNumeric1(str)
		// returns true if str is numeric
		// that is it contains only the digits 0-9
		// returns false otherwise
		// returns false if empty
		{
		//alert("isNumeric");
		  var len= str.length;
		  if (len==0)
		    return false;
		  //else
		  var p=0;
		  var ok= true;
		  var ch= "";
		  while (ok && p<len)
		  {
		    ch= str.charAt(p);
		    if ('0'<=ch && ch<='9')
		      p++;
		    else
		      ok= false;
		  }
		  return ok;
		}
