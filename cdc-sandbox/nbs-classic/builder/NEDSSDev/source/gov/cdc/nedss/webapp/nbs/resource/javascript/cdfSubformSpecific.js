function openImporter() {
	window.open("/nbs/ldf/import","","left=100, top=100, width=750, height=300, menubar=no,titlebar=yes,toolbar=no,scrollbars=no,location=no,status=yes");
}

function closeImporter() {
	if(opener.document!=null)
	{
		opener.document.location.reload();
	}	
	self.close();
	if (window.opener && !window.opener.closed) {	
		window.opener.focus();
		//window.opener.reload();
		
	}
	else { 
		alert('The Parent Window has been closed. Please login to NEDSS');
		self.close();
		window.open("/nbs/Login");
	}
}


function callLoadData() {}

function callSaveData() {}

function callValidateData() {}

function callDisableData() {}

function callEnableData() {}

function callClearData() {}

function submitAndClose() {
	submitForm();
	closeImporter();
	
}

function submitAndRestore() {
	getElementByIdOrByName("contextAction").value="save";
	submitForm();
}

function cancelImport() {

        var target = "";
        if(arguments.length > 0)
        target = arguments[0];
        window.location = target;
		
}
function copyFromTo(from, to) {

	from.className='none';	
	if(gXSLType == 'input') {
	
		//for ADD
		var options = new Object();
		for (var i=0; i<to.options.length; i++) {
			options[to.options[i].value] = to.options[i].text;
			}
		for (var i=0; i<from.options.length; i++) {
			var o = from.options[i];

			if (options[o.value] == null || options[o.value] == "undefined" || options[o.value]!=o.text) {
				to.options[to.options.length] = new Option( o.text, o.value, false, false);
//				if(o.selected) 
//				to.options[(to.options.length)-1].selected = true;
				
			}
		}
	}
	
	
	if(gXSLType == 'view') {
		// always add empty one in the first entry
		to.options[0] = new Option("", "");
				
		//in view pages select boxes are named prepened with list (xsl effect)		
		var fromList = "list" + from.id;	
		var list = getElementByIdOrByName(fromList) == null ? "" : getElementByIdOrByName(fromList).value;

		//N$No|UNK$Unknown|Y$Yes|

		var items = list.split("|");
		if (items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				// start from index 1
				var pairs = items[i].split("$");
				to.options[i+1] = new Option(pairs[1], pairs[0]);
//				if(from.value == pairs[0])
//				to.options[i].selected = true;					
				
				
			}		
		}
	}

}		

//on submit, copies back the selected option from subform to NBS form element
function copySelectedOptions(from,to) {

	var options = new Object();
	
	for(var i=0;i<to.options.length;i++) {
		options[to.options[i].value] = to.options[i].text;
	}
	
	for(var i=0;i<from.options.length;i++){
		var o = from.options[i];
		if(o.selected){
			if(options[o.value] == null || options[o.value]!=o.text){
				to.options[to.options.length] = new Option( o.text, o.value);
				
				if(to.options[(to.options.length)-1] != null) {
				
					to.options[(to.options.length)-1].selected=true;
					return;

				}
			}
		}
	}

		

}

function removeAllOptions(from){
	for(var i=(from.options.length-1);i>=0;i--){
		from.options[i] = null;
	}
	from.selectedIndex = -1;
}

function validateInteger(stValue, errNode) {
	
	msg='';
	tdNode=errNode.getElementsByTagName("td").item(0);
	
	if(stValue.length>0 && stValue !=' ') {
		
		if(stValue < 1) {
			msg='Value must be greater than or equal to 1.';
		}
	}
	
	if(msg!=null && msg.length>0) {
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}

}

//date validation
function validateSFDate(stValue, errNode) {
	msg='';
	//alert('stValue ==' + stValue);
	//* step1: Get the error Node for the CSF field to validate 
	tdNode=errNode.getElementsByTagName("td").item(0);
	if(stValue.length>0 && stValue !=' ') {
		// Check for mm/dd/yyyy format
		if (!isDate( stValue ) ) {

			//msg= makeErrorMsg('ERR003', 'Date Entered');
			msg="Date entered must be in the format of mm/dd/yyyy. Please correct the data and try again.\n";
			

		// If in right format, check for range(12/31/1875 - today)
		} else	if ((CompareDateStrings(stValue, "12/31/1875") == -1) || (CompareDateStringToToday(stValue) == 1))  {

			//msg= makeErrorMsg('ERR004', 'Date Entered');
			msg="Date entered must be greater than 12/31/1875 and less than or equal to today's date. Please correct the data and try again.\n";
		}
	}

	if(msg!=null && msg.length>0) {
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}
	
}

//date validation
function validateSFFutureDate(stValue, errNode) {
	msg='';
	//alert('stValue ==' + stValue);
	//* step1: Get the error Node for the CSF field to validate 
	tdNode=errNode.getElementsByTagName("td").item(0);

	if(stValue.length>0 && stValue !=' ') {
		// Check for mm/dd/yyyy format
		if (!isDate( stValue ) ) {

			//msg= makeErrorMsg('ERR003', 'Date Entered');
			msg="Date entered must be in the format of mm/dd/yyyy. Please correct the data and try again.\n";
			

		// If in right format, check for future date range(>= 12/31/1875)
		} else	if ((CompareDateStrings(stValue, "12/31/1875") == -1))  {

			//msg= makeErrorMsg('ERR068', 'Date Entered');
			msg="Date entered must be greater than 12/31/1875. Please correct the data and try again.\n";
		}
	}

	if(msg!=null && msg.length>0) {
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}
	
}

function validateExpectedReshipDate(stValue, date1, date2, errNode) {
	
	msg='';
	tdNode=errNode.getElementsByTagName("td").item(0);
	
	expectedShipDate = getElementByIdOrByName(date1).value;
	actualShipDate = getElementByIdOrByName(date2).value;

	if(stValue.length>0 && stValue != ' ') {
		
		if(expectedShipDate.length > 0 || actualShipDate.length > 0) {
			
			if ((CompareDateStrings(stValue, expectedShipDate) == -1) || (CompareDateStrings(stValue, actualShipDate) == -1))  {
				
				msg="Expected Reship Date cannot be before 'Expected Ship Date' or 'Actual Ship Date'.\n";
			}
		
		}
	
	}
	
	if(msg!=null && msg.length>0) {
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}

}

function validateActualReshipDate(stValue, date1, errNode) {
	
	msg='';
	tdNode=errNode.getElementsByTagName("td").item(0);
	
	actualShipDate = getElementByIdOrByName(date1).value;

	if(stValue.length>0 && stValue != ' ') {
		
		if (!isDate( stValue ) ) {

			msg="Date entered must be in the format of mm/dd/yyyy. Please correct the data and try again.\n";

		} 
		
		if ((CompareDateStrings(stValue, "12/31/1875") == -1) || (CompareDateStringToToday(stValue) == 1))  {

			msg="Date entered must be greater than 12/31/1875 and less than or equal to today's date. Please correct the data and try again.\n";

		} else {

			if(actualShipDate.length > 0) {

				if ((CompareDateStrings(stValue, actualShipDate) == -1))  {

					msg= msg + "Actual Reship Date cannot be before 'Actual Ship Date'.\n";
				}

			}		
		}
		
		
	
	}
	
	if(msg!=null && msg.length>0) {
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}

}

function compareSFDates(date1, date2, errNode1, errNode2) {
	msg='';
	if(validateSFDate(date1, errNode1)==false && validateSFDate(date2, errNode2)==false) {
	
		tdNode=errNode2.getElementsByTagName("td").item(0);
		if (CompareDateStrings(date2, date1) == -1) {
			msg="Date of Departure must be equal to or greater than Date of Arrival. Please correct the data and try again."
		}
		if(msg!=null && msg.length>0) {	
			tdNode.innerText=msg;
			tdNode.className='error';
			return true;
		} else {	
			tdNode.innerText='';
			tdNode.className='none';
			return false;
		}		

	}
	return false;
}

function clearErrorCell(errN) {
	
	errorNode = getElementByIdOrByName(errN);
	
	if(errorNode != null) {
	
		tdNode=errorNode.getElementsByTagName("td").item(0);
		tdNode.innerText='';
		tdNode.className='none';	
	}
}


function checkMaxLength(sTxtBox) {
	
	maxlimit = 2000;
		
	if (sTxtBox.value.length > maxlimit)		
		sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
		

}


//addTrackingIsolate
function addTrackingIsolate(type, rtBatchEntry) {
	
	//alert('inside addIsolateCSF, rtBatchEntry is   \n\n' + rtBatchEntry);

	if(type != 'Test Result') {
		//alert('Batch Entry not inside a Lab Report !!!');
		return;
	
	}
	
	var isolateTracking = getElementByIdOrByName("FDD_Q_375");
	
	if((isolateTracking != null) && (isolateTracking.checked ==false)) {
		return;
	}
	
	var batchEntries = rtBatchEntry.split("|");
	var isolateSt = "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName";
	var isolateSearchSt = "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd";
	
	var statusCdEntry = "resultedTest[i].theSusceptibilityVO.theObservationDT.statusCd";
		
	var isolates = new Array();
	var active = false;
	//alert('before Step 1');
	
	//Step 1: Clear off all the CSF Isolate Tracking fields
	
	var isolateTrack1=getElementByIdOrByName("FDD_Q_291");
	var track1=getElementByIdOrByName("FDD_Q_291C");
	var isolateTrack2=getElementByIdOrByName("FDD_Q_303");
	var track2=getElementByIdOrByName("FDD_Q_303C");
	var isolateTrack3=getElementByIdOrByName("FDD_Q_310");
	var track3=getElementByIdOrByName("FDD_Q_310C");
	
	if(isolateTrack1 == null && isolateTrack2 == null && isolateTrack3 == null) return;
	
	
	//Step 2: Iterate through the Resulted Test Batch Entry and trim off the inactive records
	
	var found = false;
	
	for(var b=0; b<batchEntries.length-1; b++) {
		
		var nvPairs = batchEntries[b].split("^");
		
		for(var c=0; c<nvPairs.length-1; c++) {
			
			if(StartsWith(nvPairs[c], statusCdEntry)) {

				var temp = nvPairs[c].split("~");
				var statusCd = temp[1];
				//alert('The Status Ind selected is == ' + statusCd);
				if(statusCd == 'A') {
					active = true;
							
				}			
			}
			if((StartsWith(nvPairs[c], isolateSt) || StartsWith(nvPairs[c], isolateSearchSt))  && found == false) {
				var temp = nvPairs[c].split("~");
				var organismInd = temp[1];
				//alert('The Organism Ind selected is == ' + organismInd);
				
				if(active == true && organismInd.length > 0) {
					var organismName = "";
					if(organismInd.length == 7)
						organismName = getOrgNameForCode(organismInd);
					else
						organismName = organismInd;

					//alert('The organismName retrieved is == ' + organismName);	
					
					if(organismName != "") {
						isolates.push(organismName);					
						active=false;
						found = true;
					}
				}	

			}//startswith
			
		}
		found = false;
	}
	
	//alert('isolates: ' + isolates);
	////Step 3: Iterate through the isolates array (generated above) and populate the Lab CSFs now
	
	if(isolates[0] != null)
		isolateTrack1.value=isolates[0];
	if(isolates[1] != null)
		isolateTrack2.value=isolates[1];
	if(isolates[2] != null)
		isolateTrack3.value=isolates[2];
		
	callSaveData();
	callLoadData();			

	/*
	if(isolates.length > 0 && isolates.length<4) {
		//alert('isolates array contents now are : ' + isolates);
		//isolates.sort();
		
		for(var i=0; i<isolates.length; i++) {		
			
			//eval("track" + (i+1)).checked=true;
			eval("isolateTrack" + (i+1)).value=isolates[i];			
			callSaveData();
			callLoadData();			
		}
	}
	*/
}

//updateTrackingIsolate

function updateTrackingIsolate(type, rtBatchEntry) {
	
	//alert('inside updateIsolateCSF, rtBatchEntry is   \n\n' + rtBatchEntry);
	
	if(type != 'Test Result') {
		//alert('Batch Entry not inside a Lab Report !!!');
		return;	
	}
	
	var isolateTracking = getElementByIdOrByName("FDD_Q_375");
	
	if((isolateTracking != null) && (isolateTracking.checked ==false)) {
		return;
	}
	
	
	var batchEntries = rtBatchEntry.split("|");
	var isolateSt = "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName";
	var isolateSearchSt = "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd";
	
	var statusCdEntry = "resultedTest[i].theSusceptibilityVO.theObservationDT.statusCd";
	var active = false;
	var isolates = new Array();
	
	var isolateTrack1=getElementByIdOrByName("FDD_Q_291");
	var track1=getElementByIdOrByName("FDD_Q_291C");
	var isolateTrack2=getElementByIdOrByName("FDD_Q_303");
	var track2=getElementByIdOrByName("FDD_Q_303C");
	var isolateTrack3=getElementByIdOrByName("FDD_Q_310");
	var track3=getElementByIdOrByName("FDD_Q_310C");
	
	if(isolateTrack1 == null && isolateTrack2 == null && isolateTrack3 == null) return;

	//Step 2: Iterate through the RT Batch, find the organismName entry in the isolate & update
	
	var found = false;
	
	for(var b=0; b<batchEntries.length-1; b++) {
		
		var nvPairs = batchEntries[b].split("^");
		
		for(var c=0; c<nvPairs.length-1; c++) {
			
			if(StartsWith(nvPairs[c], statusCdEntry)) {

				var temp = nvPairs[c].split("~");
				var statusCd = temp[1];
				//alert('The Status Ind selected is == ' + statusCd);
				if(statusCd == 'A') {
					active = true;							
				}				
			}
			
			if((StartsWith(nvPairs[c], isolateSt) || StartsWith(nvPairs[c], isolateSearchSt))  && found == false) {
				var temp = nvPairs[c].split("~");
				var organismInd = temp[1];
				//alert('The Organism Ind selected is == ' + organismInd);
				
				if(active == true && organismInd.length > 0) {
					var organismName = "";
					if(organismInd.length == 7)
					 	organismName = getOrgNameForCode(organismInd);
					else
						organismName = organismInd;

					//alert('The organismName retrieved is == ' + organismName);	
					if(organismName != "") {
						isolates.push(organismName);
						//alert('isolates.length = ' + isolates.length);
						active=false;
						found = true;
					}
					
				}	

			} //startswith			
		}
		found =false;
	}
	
	
	if(isolates.length >=1 && isolates[0] != isolateTrack1.value) {
		isolateTrack1.value= isolates[0];
	}	

	if(isolates.length >=2 && isolates[1] != isolateTrack2.value) {
	
		isolateTrack2.value= isolates[1];

	}	

	if(isolates.length >=3 && isolates[2] != isolateTrack3.value) {
		isolateTrack3.value= isolates[2];
	}
	
	
	if(isolates.length > 0) {
		
		callSaveData();
		callLoadData();
	}

	
}


//function called when code lookup happens

function deleteIsolateTracks() {

	
	var isolateTrack1=getElementByIdOrByName("FDD_Q_291");
	var track1=getElementByIdOrByName("FDD_Q_291C");
	var isolateTrack2=getElementByIdOrByName("FDD_Q_303");
	var track2=getElementByIdOrByName("FDD_Q_303C");
	var isolateTrack3=getElementByIdOrByName("FDD_Q_310");
	var track3=getElementByIdOrByName("FDD_Q_310C");
	var isolateTracking = getElementByIdOrByName("FDD_Q_375");

	if(isolateTracking!= null && isolateTrack1 != null && isolateTrack2 != null && isolateTrack3 != null) {
	
		isolateTrack1.value='';
		isolateTrack2.value='';
		isolateTrack3.value='';
		
		if(track1 != null && track2 != null && track3 != null) {
		
			track1.checked=false;
			track2.checked=false;
			track3.checked=false;
			
			track1.disabled=true;
			track2.disabled=true;
			track3.disabled=true;
		}
	
		isolateTracking.checked=false;

		callSaveData();
		callLoadData();	
	}

}

//deleteTrackingIsolate

function deleteTrackingIsolate(type, rtBatchEntry) {

	//alert('inside deleteIsolateCSF, rtBatchEntry is   \n\n' + rtBatchEntry);
	
	if(type != 'Test Result') {
		//alert('Batch Entry not inside a Lab Report !!!');
		return;
	
	}
	
	var isolateTracking = getElementByIdOrByName("FDD_Q_375");
	
	if((isolateTracking != null) && (isolateTracking.checked ==false)) {
		return;
	}
	
		
	var batchEntries = rtBatchEntry.split("|");	
	var count = 0;	
	var pathogens = new Array();
	
	var isolateSt = "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName";
	var isolateSearchSt = "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd";
	
	var statusCdEntry = "resultedTest[i].theSusceptibilityVO.theObservationDT.statusCd";
	var inactive = false;
	
	var isolateTrack1=getElementByIdOrByName("FDD_Q_291");
	var track1=getElementByIdOrByName("FDD_Q_291C");
	var isolateTrack2=getElementByIdOrByName("FDD_Q_303");
	var track2=getElementByIdOrByName("FDD_Q_303C");
	var isolateTrack3=getElementByIdOrByName("FDD_Q_310");
	var track3=getElementByIdOrByName("FDD_Q_310C");
	
	if(isolateTrack1 == null && isolateTrack2 == null && isolateTrack3 == null) return;

	//Step 2: Iterate through the RT Batch, find the organismName entry in the isolate & delete
	
	var found = false;

	for(var b=0; b<batchEntries.length-1; b++) {
		
		var nvPairs = batchEntries[b].split("^");
		
		for(var c=0; c<nvPairs.length-1; c++) {
			
			if(StartsWith(nvPairs[c], statusCdEntry)) {

				var temp = nvPairs[c].split("~");
				var statusCd = temp[1];
				//alert('The Status Ind selected is == ' + statusCd);
				if(statusCd == 'I') {
					inactive = true;					
				} else if(statusCd == 'A') {
					//alert('found active batch !!');
					count++;
				}
			}
			
			if((StartsWith(nvPairs[c], isolateSt) || StartsWith(nvPairs[c], isolateSearchSt))  && found == false) {
				
				var temp = nvPairs[c].split("~");
				var organismInd = temp[1];
				
				if(organismInd.length > 0) {
				
					//alert('The Organism Ind selected is == ' + organismInd);
					var organismName = "";
					if(organismInd.length == 7)
					 	organismName = getOrgNameForCode(organismInd);
					else
						organismName = organismInd;
					
					
					if(statusCd == 'A' && organismName!="") {
						//alert('organism Name before pushing ' + organismName);
					
						pathogens.push(organismName);
						found = true;
					}

					//alert('The organismName retrieved is == ' + organismName);
					//now try to find the equiv. entry in the isolate CSFs and delete it.

					if(statusCd != 'A' && inactive == true && organismName!="") {
						//alert('entered here');
						if(isolateTrack1.value==organismName) {
							//alert('found in isolateTrack1');
							isolateTrack1.value="";
							track1.checked=false;					
							track1.disabled=true;
							callSaveData();
							callLoadData();	
						} else if(isolateTrack2.value==organismName) {
							//alert('found in isolateTrack2');
							isolateTrack2.value="";
							track2.checked=false;
							track2.disabled=true;
							callSaveData();
							callLoadData();									
						} else if(isolateTrack3.value==organismName) {
							//alert('found in isolateTrack3');
							isolateTrack3.value="";
							track3.checked=false;					
							track3.disabled=true;
							callSaveData();
							callLoadData();					
						}
						inactive=false;
					}	
				
				
				}
				
			} //startswith
		}
		found = false;
	}
	
	//Step 3: Swapping  to fill the deleted isolates if more than 2 Pathogen exists
	
	//alert('count finally = ' + count);
	
	if(count == 0) return;
	
	var isolate1 = ['FDD_Q_291C','FDD_Q_291','FDD_Q_66','FDD_Q_67','FDD_Q_246','FDD_Q_68','FDD_Q_69','FDD_Q_70','FDD_Q_366','FDD_Q_49','FDD_Q_50','FDD_Q_51','FDD_Q_52','FDD_Q_53','FDD_Q_54','FDD_Q_55','FDD_Q_369','FDD_Q_47','FDD_Q_302','FDD_Q_298','FDD_Q_299','FDD_Q_300','FDD_Q_363','FDD_Q_247','FDD_Q_251','FDD_Q_252','FDD_Q_248','FDD_Q_249','FDD_Q_250','FDD_Q_290','FDD_Q_253','FDD_Q_254','FDD_Q_255','FDD_Q_256'];
	var isolate2 = ['FDD_Q_303C','FDD_Q_303','FDD_Q_304','FDD_Q_305','FDD_Q_306','FDD_Q_307','FDD_Q_308','FDD_Q_309','FDD_Q_367','FDD_Q_317','FDD_Q_318','FDD_Q_319','FDD_Q_320','FDD_Q_321','FDD_Q_322','FDD_Q_323','FDD_Q_370','FDD_Q_331','FDD_Q_332','FDD_Q_333','FDD_Q_334','FDD_Q_335','FDD_Q_364','FDD_Q_341','FDD_Q_342','FDD_Q_343','FDD_Q_344','FDD_Q_345','FDD_Q_346','FDD_Q_347','FDD_Q_348','FDD_Q_349','FDD_Q_350','FDD_Q_351'];
	var isolate3 = ['FDD_Q_310C','FDD_Q_310','FDD_Q_311','FDD_Q_312','FDD_Q_313','FDD_Q_314','FDD_Q_315','FDD_Q_316','FDD_Q_368','FDD_Q_324','FDD_Q_325','FDD_Q_326','FDD_Q_327','FDD_Q_328','FDD_Q_329','FDD_Q_330','FDD_Q_371','FDD_Q_336','FDD_Q_337','FDD_Q_338','FDD_Q_339','FDD_Q_340','FDD_Q_365','FDD_Q_352','FDD_Q_353','FDD_Q_354','FDD_Q_355','FDD_Q_356','FDD_Q_357','FDD_Q_358','FDD_Q_359','FDD_Q_360','FDD_Q_361','FDD_Q_362'];
	
	//alert('Number of pathogens: ' +  pathogens.length);
	//alert('Pathogens:' +  pathogens.toString());

	if(isolateTrack1.value=="" && isolateTrack2.value.length>0 && isolateTrack3.value.length > 0) {
		//move 2nd & 3rd isolates to 1st & 2nd positions and check for more to put in 3rd position
		
		//alert('Scenario1');
		
		moveIsolates(isolate2, isolate1);
		
		moveIsolates(isolate3, isolate2);

		getElementByIdOrByName(isolate3[1]).value="";
		getElementByIdOrByName(isolate3[0]).checked=false;
		getElementByIdOrByName(isolate3[0]).disabled=true;		
		
		if(pathogens.length >= 3)
			isolateTrack3.value=pathogens[2];
		
	} else if(isolateTrack1.value.length>0 && isolateTrack2.value=="" && isolateTrack3.value.length > 0) {

		//alert('Scenario2');

		moveIsolates(isolate3, isolate2);

		getElementByIdOrByName(isolate3[1]).value="";
		getElementByIdOrByName(isolate3[0]).checked=false;
		getElementByIdOrByName(isolate3[0]).disabled=true;		
		
		if(pathogens.length >= 3)
			isolateTrack3.value=pathogens[2];
	
	} else if(isolateTrack1.value.length>0 && isolateTrack2.value.length>0 && isolateTrack3.value=="") {
		
		//alert('Scenario3');
		
		if(pathogens.length >= 3)
			isolateTrack3.value=pathogens[2];		
	
	} else if(isolateTrack1.value=="" && isolateTrack2.value.length>0 && isolateTrack3.value=="") {
	
		//alert('Scenario4...');
		
		moveIsolates(isolate2, isolate1);

		getElementByIdOrByName(isolate2[1]).value="";
		getElementByIdOrByName(isolate2[0]).checked=false;
		getElementByIdOrByName(isolate2[0]).disabled=true;		

		if(pathogens.length >= 2)
			isolateTrack2.value=pathogens[1];

	
	}

	callSaveData();
	callLoadData();
}



function moveIsolates(from, to) {

	to[0].disabled=false;

	for(var i=0; i<from.length;i++) {
		
		var element = getElementByIdOrByName(from[i]);
		
		if(getCorrectAttribute(element,"type",element.type) == 'text' || getCorrectAttribute(element,"type",element.type) == 'textarea') {			
			
			data = getCorrectAttribute(element,"value",element.value);		

			if(data!=null && data.length>0) {
			
				getElementByIdOrByName(to[i]).value = data;
			} else {				
				getElementByIdOrByName(to[i]).value=" ";
				
			}
		
		} else if(getCorrectAttribute(element,"type",element.type) == 'select-one' || getCorrectAttribute(element,"type",element.type) == 'select-multiple') {
		
			//alert(i +' '+ getCorrectAttribute(element,"type",element.type));			
			data = element == null ? "" : getCorrectAttribute(element,"value",element.value).split('|');
			
			opts= getElementByIdOrByName(to[i]).options;

			if(data!=null && data.length>0) {
				cnt=0;
				for(j=0; j< data.length; j++) {
					if(data[j]!=null && data[j]!="") {
						for(x=0;x<opts.length; x++) {	
							if(opts[x]!=null && opts[x].value==data[j]) {
								opts[x].selected=true;						
							}
						}
					}		
				}

			} else {
				getElementByIdOrByName(to[i]).value=null;
			}
			
		
		} else if(getCorrectAttribute(element,"type",element.type) == 'checkbox') {
		
			if(getCorrectAttribute(element,"checked",element.checked)) {
				getElementByIdOrByName(to[i]).checked=true;
			} else {
				getElementByIdOrByName(to[i]).checked=false;

			}
		
		}
	
	}	
	
}


function getOrgNameForCode(orgCode) {

	var organismOpts = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName") == null ? "" : getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").options;

	for(i=0;i<organismOpts.length; i++) {	
		if(organismOpts[i]!=null && organismOpts[i].value==orgCode) {
			//alert('found option !!!');			
			return organismOpts[i].text;
		
		}
	}
	return "";
}

//Returns selected Options as string(used for MultiSelect Box)
function getSelectedOptsString(opts) {

   var st = new Array();
   for (var i= 0; i< opts.length; i++) {
    if (opts[i].selected) {
        st[i]=opts[i].value
    }
   }
   return st.toString();

}
//Returns '1' if substring found in main String, else '0'(used for MultiSelect Box)
function searchSFOption(main, sub) {
	
	var c = 0;
	opts = main.split(',');	
	
	for (var i=0;i<opts.length;i++) {
		if(opts[i] == sub) 
		c++;
	}
	return c;

}

//date validation
function validateSFTime(stValue, radio1, radio2, errNode) {
	msg='';
		
	tdNode=errNode.getElementsByTagName("td").item(0);
	
	if(stValue.search(/^((0[0-9]|1[0-2]|1[0-2]):(0[0-9]|[1-5][0-9]|59))?$/)<0) {
		
		msg = 'Time must be in hh:mm format (hh = 01-12 and mm = 00-59).';	
	} else {

		if(!radio1.checked && !radio2.checked && stValue.length>0) {

			msg = 'AM or PM designation is required if Time is entered.';
		}
	}

	
	if(msg!=null && msg.length>0) {	
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {	
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}	
}

function numericMask(pTextbox, pKey)
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
    var d = 0;

    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46, 190];
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
        
        if(isInteger(c) || c == '.')
        {
            s += c;
            d++;
        }
        if(d == 5)
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

function isSFNumeric(pString)
{
    
    var varPattern = /^\d{1,5}(\.\d\d?)?$/;
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
        return false;
    return true;
}

function validateSFTemp(stValue, radio1, radio2, errNode) {

	msg='';
		
	tdNode=errNode.getElementsByTagName("td").item(0);

	if(stValue.length > 0) {
	
		if(!isSFNumeric(stValue)) {
		
			msg = 'Temperature must have a numeric entry. Please correct the data and try again.';
		
		} else {
			if(!radio1.checked && !radio2.checked) {

				msg = 'If value entered, then \'Fahrenheit\' or \'Celcius\' designation required.';
			} else {
				if(radio1.checked) {
					if(stValue < 90 || stValue > 110) {

						msg = 'Value for Fahreinheit must be greater than or equal to 90.0 and less than or equal to 110.0.';
					}

				} else if(radio2.checked) {
					if(stValue < 30 || stValue > 50) {

						msg = 'Value for Celcius must be greater than or equal to 30.0 and less than or equal to 50.0.';
					}
				}
			}

		}		

	}	

	if(msg!=null && msg.length>0) {	
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {	
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}	
}

function validateSFWeight(stValue, radio1, radio2, errNode) {

	msg='';
		
	tdNode=errNode.getElementsByTagName("td").item(0);

	if(stValue.length > 0) {
	
		if(!isSFNumeric(stValue)) {
		
			msg = 'Weight must have a numeric entry. Please correct the data and try again.';
		
		} else {
			if(!radio1.checked && !radio2.checked) {

				msg = 'If value entered, then \'lbs\' or \'kg\' designation required.';
			} else {
				if(radio1.checked || radio2.checked) {
					if(stValue < 0 || stValue > 1000) {

						msg = 'Value for lbs or kg must be greater than 0.0 and less than 1000.0.';
					}

				}
			}

		}		

	}	

	if(msg!=null && msg.length>0) {	
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {	
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}	
}

function validateSFPercent(stValue, radio1, radio2, errNode) {

	msg='';
		
	tdNode=errNode.getElementsByTagName("td").item(0);

	if(stValue.length > 0) {
	
		if(!isSFNumeric(stValue)) {
		
			msg = 'Value must have a numeric entry. Please correct the data and try again.';
		
		} else {
			if(!radio1.checked && !radio2.checked) {

				msg = 'If value entered, then \'Percentage\' or \'Numeric\' designation required.';
			}
		}		

	}	

	if(msg!=null && msg.length>0) {	
		tdNode.innerText=msg;
		tdNode.className='error';
		return true;
	} else {	
		tdNode.innerText='';
		tdNode.className='none';
		return false;
	}	
}



function getSFSelectOptionCds(element) {

	opts='';
	if(gXSLType == 'input') {
		
		//both for select & multiselect
		opts= getSelectedOptsString(getElementByIdOrByName(element).options);
	} else {
		//for view pages get the span string
		opts=getSelectedOptions(element);
	}
	return opts.toString();

}

function getSelectedOptions(element) {

	var objValue  = 'obj_'+ element; 
	var objValue1 =objValue +'.value';
	
	data = getElementByIdOrByName(objValue1) == null ? "" : getElementByIdOrByName(objValue1).value.split('|');

	if(gXSLType == 'view')
		return data;
	
	opts= getElementByIdOrByName(element).options;
	copyFromTo(getElementByIdOrByName(objValue), getElementByIdOrByName(element));	
	selValues='';
	if(data!=null && data.length>0) {
		cnt=0;
		for(i=0; i< data.length; i++) {
			if(data[i]!=null && data[i]!="") {
				for(x=0;x<opts.length; x++) {	
					if(opts[x]!=null && opts[x].value==data[i]) {
						cnt++;
						selValues= selValues + opts[x].value;
						if(cnt < (data.length-1))
							selValues = selValues + ', ';
					}
				}
			}		
		}
	}
	//alert(element + ' s selValues are---' + selValues + '--- length: ' + selValues.length);
	return selValues;
}

//loads selected values into multiselect
function showSelectField(element) {
	
	var objValue  = 'obj_'+ element; 
	var objValue1 =objValue +'.value';
	
	data = getElementByIdOrByName(objValue1) == null ? "" : getElementByIdOrByName(objValue1).value.split('|');
	opts= getElementByIdOrByName(element).options;
	copyFromTo(getElementByIdOrByName(objValue), getElementByIdOrByName(element));	
	selValues='';
	if(data!=null && data.length>0) {
		cnt=0;
		for(i=0; i< data.length; i++) {
			if(data[i]!=null && data[i]!="") {
				for(x=0;x<opts.length; x++) {	
					if(opts[x]!=null && opts[x].value==data[i]) {
						if(gXSLType == 'view') {
							cnt++;
							selValues= selValues + opts[x].text;
							if(cnt < (data.length-1))
								selValues = selValues + ', ';
						} else {
							opts[x].selected=true;						
						}
					}
				}
			}		
		}
		if(gXSLType == 'view') {
			selValues = "  " + selValues;
			var newspan = document.createElement("span");
		   	var txtN = document.createTextNode(selValues);
			newspan.appendChild(txtN);
			//getElementByIdOrByName(element).replaceNode(newspan); //gst Feb2017 not supported in FF
			var replaceEle = getElementByIdOrByName(element);
			var parentEle = replaceEle.parentNode;
			parentEle.replaceChild(newspan,replaceEle);
		}
		
	}

}

//changes textbox to text
function showTextField(element,hilight) {
	var objValue  = 'obj_'+ element; 
	data = getElementByIdOrByName(objValue) == null ? "" : getElementByIdOrByName(objValue).value;
	//alert(element + 'data: ' + data);

	if(data!=null && data.length>0)
		getElementByIdOrByName(element).value = data;
	
	if(gXSLType == 'view') {
		var newspan = document.createElement("span");
		if(hilight == 'true') {
			newspan.style.background='#e4ebf8';
		}	
			data = "  " + data;

		txtN = document.createTextNode(data);
		
		newspan.appendChild(txtN);
		//getElementByIdOrByName(element).replaceNode(newspan); //gst Feb2017 not supported in FF
	
		var replaceEle = getElementByIdOrByName(element);
		var parentEle = replaceEle.parentNode;
		parentEle.replaceChild(newspan,replaceEle);
		
	}
		
}

function showTextAndRadio(element1,element2,element3) {
	
	var objValue1  = 'obj_'+ element1; 
	var objValue2  = 'obj_'+ element2; 
	var trNode = 'tr_'+ element1;
	
	data1 = getElementByIdOrByName(objValue1) == null ? "" : getElementByIdOrByName(objValue1).value;
	
	if(data1!=null && data1.length>0)
		getElementByIdOrByName(element1).value = data1;

	data2 = getElementByIdOrByName(objValue2) == null ? "" : getElementByIdOrByName(objValue2).value;
    	if(data2!=null && data2.length>0) {
		if(getElementByIdOrByName(element2).value == data2)
			getElementByIdOrByName(element2).checked=true;
		else
			getElementByIdOrByName(element3).checked=true;
	}
	
	
	if(gXSLType == 'view') {
		
		data = data1 +" " + data2;
		
		
		var oNewNode = document.createElement("td");
		oNewNode.innerText= data;
		//alert(eval(trNode).lastChild.innerText);
		//eval(trNode).lastChild.replaceNode(oNewNode); //gst Feb 2017 - replaceNode not supported in FF
		var replaceEle = eval(trNode).lastChild;
		var parentEle = replaceEle.parentNode;
		parentEle.replaceChild(oNewNode,replaceEle);
		

	}
		
}


function showRadio(element1, element2) {

	var objValue  = 'obj_'+ element1;
	var trNode = 'tr_'+ element1;
	
	data = getElementByIdOrByName(objValue) == null ? "" : getElementByIdOrByName(objValue).value;
	if(data!=null && data.length>0) {
		if(getElementByIdOrByName(element1).value == data)
			getElementByIdOrByName(element1).checked=true;
		else
			getElementByIdOrByName(element2).checked=true;
	}

	if(gXSLType == 'view') {
		
		var oNewNode = document.createElement("td");
		oNewNode.innerText= data;	
		//eval(trNode).lastChild.replaceNode(oNewNode); //gst Feb 2017 - not supported in FF
		
		var replaceEle = eval(trNode).lastChild;
		var parentEle = replaceEle.parentNode;
		parentEle.replaceChild(oNewNode,replaceEle);
		
		
	
	}
}



function hideQGRow(element1, element2) {
	
	var elementNode = getElementByIdOrByName(element1);	
	var trElement = 'tr_' + element1;
	if(elementNode!= null && gXSLType == 'input') {
		//alert(elementNode.type);
		if(elementNode.type == 'radio') {
		
			elementNode.checked=false;
			getElementByIdOrByName(element2).checked=false;
			
		} else if(elementNode.type == 'checkbox') {	
			
			elementNode.checked=false;
		
		} else if(elementNode.type == 'select-one' || elementNode.type == 'select-multiple') {
		
			elementNode.value=null;
		
		} else if(elementNode.type == 'text' || elementNode.type == 'textarea') {
		
			elementNode.value='';
		}	
	}	
	getElementByIdOrByName(trElement).className='none';

}

function showQGRow(element, type) {
	//alert(element);
	var elementNode = getElementByIdOrByName(element);	
	var trElement = 'tr_' + element;
	if(gXSLType == 'input') {
	
		getElementByIdOrByName(trElement).className='visible';
	} else if(gXSLType == 'view') {
		if(type != null && type == 'sub')
			getElementByIdOrByName(trElement).className='none';
	}

}


