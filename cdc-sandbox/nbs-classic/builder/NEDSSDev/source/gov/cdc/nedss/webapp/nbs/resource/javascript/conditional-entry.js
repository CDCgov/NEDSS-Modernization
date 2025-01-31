
/**
 * Javascript for the element types that need to be hidden and invalid or valid and shown when another element is in some state
 *
 * @author Jay Kim
 * @version CVS $Revision:
 */

/**
 * Wrapper function that defaults to "yes" selection of controller.
 *
 * @author Vieta Losik
 * @version CVS $Revision:
 *
 */

/*
 * Renamed by Vieta Losik to support test value in cases when controller should react to "No" or other selections.
 */
function nestedElementsController(elementName, controllerName) {
	nestedElementsControllerValueSpecific( elementName, controllerName ,"NA" );
}

function nestedElementsControllerValueSpecific(elementName, controllerName, testValue) {
	
	var controllerTbody = getElementByIdOrByName("nestedElementsControllerController" + elementName);
	var inputControllers = controllerTbody.getElementsByTagName("input");
	var selectControllers = controllerTbody.getElementsByTagName("select");
	
	
	var payloadTbody = getElementByIdOrByName("nestedElementsControllerPayload" + elementName);
	var elementNodes = payloadTbody.getElementsByTagName("input");
	var selectNodes = payloadTbody.getElementsByTagName("select");
	var spanNodes = payloadTbody.getElementsByTagName("span");


	var disable = true;
	var controllerState;
	var payloadState;
	
	
	
	var controllerNode = getElementByIdOrByName(controllerName);
	
	
	// used for line triggers
	var selectedValue="";
	var trNodes = payloadTbody.getElementsByTagName("tr");
	
	
	//	if controller state is checked and payload state is visible than it is inverse
		// this only works for radio and check boxes
		for (var i=0; i < inputControllers.length; i++) {
				
				
				if (inputControllers.item(i).type == "radio") 
				{
					if (inputControllers.item(i).value == testValue)
					{
						if (inputControllers.item(i).checked == true)
							disable = false;
					}
					else if(testValue=="NA")
					{
						if (inputControllers.item(i).checked == true)
							selectedValue = inputControllers.item(i).value;
						
					}
				}
				if (inputControllers.item(i).type == "checkbox") {
					if (inputControllers.item(i).checked == true)
						disable = false;
					//	need to check for inverse logic of checkboxes
					if(inputControllers.item(0).checked == true && ((payloadTbody.getAttribute("className") != "none" && payloadTbody.getAttribute("className")!=null)|| (payloadTbody.getAttribute("class") != "none"&& payloadTbody.getAttribute("class")!=null) ))
							disable = true;

					if(inputControllers.item(0).checked == false && (payloadTbody.getAttribute("className") == "none" || payloadTbody.getAttribute("class") == "none" ))
							disable = false;
				}
			
		}
		
		
		
		
		
		
		
			if (controllerNode!=null && controllerNode.type == "select-one") {
					
					selectedValue = controllerNode.value;
					
					if (controllerNode.value == testValue)
					{
							disable = false;
							
							for (var i=0; i < trNodes.length; i++) 
							{
								
								if(trNodes.item(i).test)
								{
									var bShow = false;
									
									bShow = eval(trNodes.item(i).test);
									
									if(bShow)
									{
										trNodes.item(i).setAttribute("className", "visible");
										trNodes.item(i).setAttribute("class", "visible");
										
									} 
									else 
									{
										nestedElementsControllerClearInternalInputs(trNodes.item(i));
										trNodes.item(i).setAttribute("className", "none");							
										trNodes.item(i).setAttribute("class", "none");
										
									}
								}
							}
					}
					
					else if(testValue=="NA")
					{
			
										
					// the trigger
					
					// check each tr to see if we have any triggers
							
							var evalResult = eval( getCorrectAttribute(controllerTbody,"eval",controllerTbody.eval) );
							
							for (var i=0; i < trNodes.length; i++) {
								
								var triggeri=getCorrectAttribute(trNodes.item(i),"trigger",trNodes.item(i).trigger);
								if (triggeri && trNodes.item(i).parentNode==payloadTbody)	
								{ 
									
									/*if(trNodes.item(i).trigger==selectedValue || (trNodes.item(i).trigger.search(/\|/)!=-1 && trNodes.item(i).trigger.indexOf(selectedValue)!=-1 && selectedValue!=""))
									{
									
										trNodes.item(i).setAttribute("className", "visible");
										disable=false;
										
									} */
									
									//gst 2015 change - was...
									//if( !evalResult && 
									//    (trNodes.item(i).trigger==selectedValue || (trNodes.item(i).trigger.search(/\|/)!=-1 && trNodes.item(i).trigger.indexOf(selectedValue)!=-1 && selectedValue!="") ) )
									
									if( !evalResult && 
									    (triggeri==selectedValue || (triggeri.search(/\|/)!=-1 && itemInList(triggeri, selectedValue) && selectedValue!="") ) )
									{
										
										trNodes.item(i).setAttribute("className", "visible");
										trNodes.item(i).setAttribute("class", "visible");
										
										disable=false;
										
									}
									else 
									{
										
										nestedElementsControllerClearInternalInputs(trNodes.item(i).parentNode);
										trNodes.item(i).setAttribute("className", "none");
										trNodes.item(i).setAttribute("class", "none");
										
										
									}
																	
									
								}
							}
			
					}
					
					
					// do additional logic check 
					if(getCorrectAttribute(controllerNode,"check",controllerNode.check))
					{
						
						var disableHold = disable;
						disable = eval(controllerNode.check);
						if (disable==null)
							disable=disableHold;
							

					}
					
					
			} 
			else if(controllerNode!=null && controllerNode.type == "select-multiple")
			{
			
			
					//selectedValue = controllerNode.value;
										
					for(var i=0; i<controllerNode.options.length;i++)
					{
						var option = controllerNode.options[i];
						if(option.selected)
						{
							if(option.value==testValue)
							{
								disable = false;
								
							}
						}
										
					}
					
					/*
					if (controllerNode.value == testValue)
					{
							disable = false;
					}
					*/
					
					
					if(testValue=="NA")
					{
			
			
						// initialize trigger tr's
						for (var j=0; j < trNodes.length; j++) {
							
							if (getCorrectAttribute(trNodes.item(j),"trigger",trNodes.item(j).trigger))	
							{
								trNodes.item(j).setAttribute("className", "none");
								trNodes.item(j).setAttribute("class", "none");
								
							}
						}
						// display triggered tr's
						for(var i=0; i<controllerNode.options.length;i++)
						{
							var option = controllerNode.options[i];
							if(option.selected){


								// check each tr to see if we have any triggers

										for (var j=0; j < trNodes.length; j++) {
											
											var triggerj=getCorrectAttribute(trNodes.item(j),"trigger",trNodes.item(j).trigger);
											if (triggerj)	
											{

												if(triggerj==option.value || trNodes.item(j).className=="visible")
												{
													
													trNodes.item(j).setAttribute("className", "visible");
													trNodes.item(j).setAttribute("class", "visible");


													disable=false;
												} 



											}

										}

							}


						}
						// clean up untriggered tr's
						for (var j=0; j < trNodes.length; j++) 
						{
							var triggerj2=getCorrectAttribute(trNodes.item(j),"trigger",trNodes.item(j).trigger);
							
							if (triggerj2)	
							{

								if(trNodes.item(j).className=="none")
								{
									nestedElementsControllerClearInternalInputs(trNodes.item(j));
								} 



							}

						}

				
				}
			}
			
			// radio line trigger 
			else if(controllerNode!=null && controllerNode.type == "radio")
			{
				if(testValue=="NA")
				{
					for (var j=0; j < trNodes.length; j++) {
						var triggerj3=getCorrectAttribute(trNodes.item(j),"trigger",trNodes.item(j).trigger);
						
						if (trigger3 && trNodes.item(j).parentNode==payloadTbody)	
						{ 

							if(trigger3==selectedValue) // || (trNodes.item(i).trigger.search(/\|/)!=-1 && trNodes.item(i).trigger.indexOf(selectedValue)!=-1 && selectedValue!=""))
							{
								trNodes.item(j).setAttribute("className", "visible");
								trNodes.item(j).setAttribute("class", "visible");
								
								disable=false;

							} 
							else 
							{

								nestedElementsControllerClearInternalInputs(trNodes.item(j));
								trNodes.item(j).setAttribute("className", "none");
								trNodes.item(j).setAttribute("class", "none");


							}



						}
					}
				}
			
			}
			
			
		//	initially hide everything
		
				var tFootNodes = payloadTbody.getElementsByTagName("tfoot");
				for (var i=0; i < tFootNodes.length; i++)
				{
					if(tFootNodes.item(i).id.search(/nestedElementsControllerPayload/)>-1){
						
						tFootNodes.item(i).setAttribute("className", "none");
						tFootNodes.item(i).setAttribute("class", "none");
						
						
					}
				}
		
		
		
		if(getCorrectAttribute(controllerTbody,"eval",controllerTbody.eval))
			{
					var bReturnValue = eval(getCorrectAttribute(controllerTbody,"eval",controllerTbody.eval));
					//disable = eval(controllerTbody.eval) || disable;
					if(bReturnValue!=null)
						disable = bReturnValue && disable;
					
					
			}
	
		
		
		
		//	do the necessary enabling or disabling of elements controlled by the controller

								for (var i=0; i < selectNodes.length; i++) {
									if ((disable)||(testValue=='OTH'))
									{
										if(selectNodes.item(i).type == "select-multiple")
										{
											selectNodes.item(i).selectedIndex=-1;
										}
										else
										{
											
											selectNodes.item(i).value="";
										}
									}
								}
								//	clean up the selected variables based on status of controller
								for (var i=0; i < elementNodes.length; i++) {
									if (elementNodes.item(i).type == "text")	{
										
										
										if ((disable)||(testValue=='OTH'))
										{											
											elementNodes.item(i).value="";
										} else {											
											if(elementNodes.item(i).fieldLabel != null && elementNodes.item(i).fieldLabel.search("As Of")!=-1) {
												
												if(getElementByIdOrByName("today") != null)
													elementNodes.item(i).value = getElementByIdOrByName("today").value;
											}
										}
										
									}
									// When enabling radio button, we set it to default "No"
									else if (elementNodes.item(i).type == "radio")	{
										if (disable)
											elementNodes.item(i).checked = false;
										if ( (!disable) && (elementNodes.item(i).value=='n') )
											elementNodes.item(i).checked = true;
									}
									else if (elementNodes.item(i).type == "checkbox")	{
										if (disable)
										{
											elementNodes.item(i).checked = false;
											checkboxObservationData(elementNodes.item(i));
										}
									}
									// to clear out the entity search hidden variable
									else if (elementNodes.item(i).type == "hidden" && elementNodes.item(i).mode=="uid")	{
										if (disable)
										{
											elementNodes.item(i).value = "";

										}
									}
									else if (elementNodes.item(i).type == "hidden" && elementNodes.item(i).mode=="batch-entry")	{
										
										if (disable){
											if(!(elementNodes.item(i).name == "vaccineBatch")){
											  elementNodes.item(i).value = elementNodes.item(i).value.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");
											  updateBatchEntryHistoryBox(elementNodes.item(i).name);
											  resetBatchEntryInputElements(elementNodes.item(i).name);
											}

										}
									}
									
								}
								// to clear out the spans on the entity search
								for (var i=0; i < spanNodes.length; i++) 
								{

									if (disable && spanNodes.item(i).id.match(/entity./)!=null)					
											spanNodes.item(i).innerHTML="";
								}
		
		//	make the payload section of the controller visible or hide based on the controller status
		if (disable)
		{	
			payloadTbody.setAttribute("className", "none");
			payloadTbody.setAttribute("class", "none");
			
			
			
		}
		else
		{	
			payloadTbody.setAttribute("className", "visible");
			payloadTbody.setAttribute("class", "visible");
			
			
		}

		

		//	need to remove contents of the hidden variable if it has been disabled by controller
		//	other controllers will be inside theads, so they will still be visible
		if (disable) {
				

			//	hide all the nested tbodies
			nestedTBodies = payloadTbody.getElementsByTagName("tfoot");
			
			for (var i=0; i < nestedTBodies.length; i++) {
				//if(tFootNodes.item(i).id=="nestedElementsControllerPayload")
				if(tFootNodes.item(i).id.search(/nestedElementsControllerPayload/)>-1)	{			
					nestedTBodies.item(i).setAttribute("className", "none");
					nestedTBodies.item(i).setAttribute("class", "none");
			}
			}
			
		}
}


function nestedElementsControllerClearInternalInputs(trNodes)
{
	
	var elementNodes = trNodes.getElementsByTagName("input");
	var selectNodes = trNodes.getElementsByTagName("select");
	
			for (var i=0; i < selectNodes.length; i++)
			{
					if(selectNodes.item(i).type == "select-multiple")
					{
						selectNodes.item(i).selectedIndex=-1;
					}
					else
					{
						
						selectNodes.item(i).value="";
					}
			}
			//	clean up the selected variables based on status of controller
			for (var i=0; i < elementNodes.length; i++) {
				if (elementNodes.item(i).type == "text")	
				{
					
						elementNodes.item(i).value="";
				}
				
		}
		
}

    function itemInList(listOfItems, itemToCheck)
    {
   	var splitConditions = listOfItems.split('|');
   	for(var i=0; i<splitConditions.length; i++) {
		 if (splitConditions[i] == itemToCheck)
		         	return true;
    	}
	return false; //not in list
    }
