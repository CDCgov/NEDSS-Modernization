/**
 * Title:        Batch Entry Control
 * Description:  does the client side presentation processing of batch data and struts conversion
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
	//popupBackupData = hiddenNode.value;
	var items = null;
	var innerBatchExist = false;
	if(hiddenNode.value.search(/\]\|/)==-1){// doesn't contain child delimiter

		items = hiddenNode.value.split("|");
	}
	else {	//does contain child delimiter
		items = hiddenNode.value.split("]|");
		innerBatchExist = true;
	}

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
					if(innerBatchExist)
						indexed+="]|";
				}
			}
		}
	hiddenNode.value=indexed;
	updateBatchEntryHistoryBox(type,true);
	// can't reset for everything because of initialization default when coming from search criteria page

	if(type=="Test Result" || type=="Lab Report" || type=="Susceptibility" || type=="Field" || type=="ID" || type=="Identification" || type=="Treatment")
		resetBatchEntryInputElements(type);
	if(getCorrectAttribute(hiddenNode,"selectedIndex",hiddenNode.selectedIndex)){
		
		editBatchEntry(type, hiddenNode.selectedIndex);
	}


}
function validateAttachmentSection(){
	getElementByIdOrByName("errorV").value = "";
	  getElementByIdOrByName("errorV").className = "none";
	  
	  var inputVisibleIndex=getFileInputVisible();
		var inputVisible="fileAttachment"+inputVisibleIndex;
		var filename;
		if(getElementByIdOrByName(inputVisible).files[0]!=undefined){
	    if(window.ActiveXObject){
	        var fso = new ActiveXObject("Scripting.FileSystemObject");
	        var filepath = getElementByIdOrByName(inputVisible).value;
	        var thefile = fso.getFile(filepath);
	        var sizeinbytes = thefile.size;
	        filename = thefile.name;
	    }else{
	        var sizeinbytes = getElementByIdOrByName(inputVisible).files[0].size;
	        filename = getElementByIdOrByName(inputVisible).files[0].name;
	    }

	    var fname = getElementByIdOrByName("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt").value;
	    //var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
	   // fSize = sizeinbytes; i=0;while(fSize>900){fSize/=1024;i++;}
	   var maxFileSizeInBytes=  getElementByIdOrByName("maxFileSizeInMB").value  * 1024 * 1024;
	    //var filesizeMessage = (Math.round(fSize*100)/100)+' '+fSExt[i];
	 //  allowedExtensions
//	    var _validFileExtensions = [".doc", ".docx", ".xls" , ".xlsx",".ppt", ".pptx", ".vsd" , ".jpeg",".jpg", "bmp", ".jfif" , ".png", ".pdf" , ".tiff",".tif", "xps", ".zip" , ".html" , ".txt"]; 
	   var _validFileExtensions =  getElementByIdOrByName("allowedExtensions").value;
	    var oInput = getElementByIdOrByName(inputVisible);
	    if (oInput.type == "file") {
	        var sFileName = filename;
	        if (sFileName.length > 0) {
	            var blnValid = false;
	            if(_validFileExtensions.trim() != ""){
	            var exItems = _validFileExtensions.split(",");
	            for (var j = 0; j < exItems.length; j++) {
	                var sCurExtension = exItems[j].trim();
	                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
	                    blnValid = true;
	                    break;
	                }
	            }
	            }
	            	
	            if (!blnValid) {
	            	errorText = "You are trying to upload a file with an extension that is not allowed. Please upload a different file. "; 
	            	//  errorText = makeErrorMsg('ERR193');
	            	// errorText = ("Please enter a valid SSN using this format:  '000-00-0000'.");
	            	getElementByIdOrByName("errorV").className = "error";
	            	setText(getElementByIdOrByName("errorV"), errorText);
	            	oInput.value = "";
	            	 document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt").value = "";
	            	return false;
	            }
	          
	        }
	       
	    }
	    if(sizeinbytes > maxFileSizeInBytes){
	    	errorText = "You are trying to upload a file that is greater than " +  getElementByIdOrByName("maxFileSizeInMB").value +"MB. This file is too large and cannot be uploaded. Please upload a different file that conforms to the file size above."; 
		  //  errorText = makeErrorMsg('ERR193');
			// errorText = ("Please enter a valid SSN using this format:  '000-00-0000'.");
		    getElementByIdOrByName("errorV").className = "error";
			setText(getElementByIdOrByName("errorV"), errorText);
			getElementByIdOrByName(inputVisible).value ="";
			document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt").value = "";
			return false;
	    }
		} 
	    	return true;
}
/*
getFileInputVisible: this method returns the index of the input file that is visible
*/
function getFileInputVisible(){
	
	
	var i=0;
	var visible=-1;
	
	for(var i=0; i<11 && visible == -1;i++){
		
		var style = document.getElementById("fileAttachment"+i).parentElement.getAttribute("style");
		if(style.indexOf("none")==-1)
			visible=i;
	}
	return visible;
}

/**
 * Description:	adds a new type to multiple data types
 *
 * param @type	name of of the nested type on the page
 */
function addNewBatchEntry(type) {
	
	var currentDate;
	var currentUser;
	var currentId;
	var file;
	var maxFileSize;
	//var errorChooseFile = false;
	var validateBatch = true;
	var attachmentUid;

		if(type=='Attachment'){
			var valida = validateAttachmentSection();
			if(!valida) return false; 
			var inputVisibleIndex=getFileInputVisible();
			var inputVisible="fileAttachment"+inputVisibleIndex;
			var totalCount = document.getElementById("nestedElementsHistoryBox|Attachment").getElementsByTagName("tr").length;
			
			if(totalCount>9){
				alert("The system doesn't allow to add more than 10 attachments");
				document.getElementById(inputVisible).value="";
				document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt").value="";
				document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.descTxt").value="";
				
				return ;
			}
			
			/*
			if(document.getElementById(inputVisible).value==""){
				document.getElementById("errorMessageInputFilefileAttachment"+inputVisibleIndex).setAttribute("style","display:");
				errorChooseFile=true;
			}else{
				document.getElementById("errorMessageInputFilefileAttachment"+inputVisibleIndex).setAttribute("style","display:none");
				errorChooseFile=false;
			}*/
			
			
			//if(errorChooseFile==false){
				document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.inputFile").value=inputVisible;
		
				file = document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.attachment");
				currentDate = document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeTime").value;
				currentUser = document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChgUserNm").value;
				currentId=document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeUserId").value;
				maxFileSize = getElementByIdOrByName("maxFileSizeInMB").value;
				attachmentUid = document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.nbsAttachmentUid").value; 
			//}
				
		}
		
		
	if(CheckForChildWindow()){

	var error2TD = getElementByIdOrByName("error2");
	if( error2TD ) error2TD.setAttribute("className", "none");

	var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);

	validateBatch = validateBatchEntry(type);
		if (validateBatch == false) {
			var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);

			var oldValues = hiddenNode.value;

			//create the string of data
			var newTypeInput = createDataString(nestedElementsTableNode, type).replace(/statusCd~\^/, "statusCd"+ nvDelimiter + "A^");
			increment = increment + 1;
			
			// this if for default, you may not have more than one default
			if(newTypeInput.search(/~default/) > 0){
				// we have a default in the edited string, now remove from the unused string
				oldValues = oldValues.replace(/~default/g,"~");

			}



			hiddenNode.value = oldValues + "index" + nvDelimiter + increment + pairDelimiter + newTypeInput + lineDelimiter;
			
			//populate the CSFs with Isolate Value
			addTrackingIsolate(type, hiddenNode.value);
			
			updateBatchEntryHistoryBox(type);			
			resetBatchEntryInputElements(type);
			newTypeInput = null;
			hiddenNode = null;
		}
	}
	

		
		if(type=='Attachment' && validateBatch==false){
			document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeTime").value=currentDate;
			document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChgUserNm").value=currentUser;
			document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeUserId").value=currentId;
			getElementByIdOrByName("maxFileSizeInMB").value = maxFileSize;
			document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.nbsAttachmentUid").value = attachmentUid;
			showNextInput(inputVisible);getElementByIdOrByName("maxFileSizeInMB").value = maxFileSize;
	}

	
}

/**
 * showNextInput: this method receives the name of the element to hide, show the next element to show and hide the current visible element
 */
function showNextInput(inputVisible){
	
	var nextVisible = -1;
	for(var i=0; i<11 && nextVisible==-1; i++){
		if(document.getElementById("fileAttachment"+i)!=null && document.getElementById("fileAttachment"+i)!=undefined)
			if(document.getElementById("fileAttachment"+i).parentElement.getAttribute("style").indexOf("none")!=-1
		&& document.getElementById("fileAttachment"+i).value=="")
				nextVisible=i;		
	}
	
	showInputFile(nextVisible);
	hideInputFile(inputVisible);
}

/**
* showInputFile: this method receives the index of the next input to show
*/
function showInputFile(nextVisible){
	
		document.getElementById("fileAttachment"+nextVisible).parentElement.setAttribute("style","display:");
		document.getElementById("fileAttachment"+nextVisible).value="";
		document.getElementById("fileAttachment"+nextVisible).setAttribute("validate","required");
		
		document.getElementById("fileAttachmentLine"+nextVisible).setAttribute("style","display:");
		document.getElementById("fileAttachmentLine"+nextVisible).nextSibling.nextSibling.setAttribute("style","display:");	
		
		
}

/**
* hideInputFile(): this method receives the name of the element to hide, like fileAttachment0
*/

function hideInputFile(inputVisible){
	
		//var indexVisible = inputVisible.slice(-1); //this doesn't work if number is greater than 9 
		var indexVisible = inputVisible.replace(/\D/g,''); 
		//var inputVisibleWithoutIndex=inputVisible.substring(0,inputVisible.length-1); //this doesn't work if number is greater than 9 
		var inputVisibleWithoutIndex=inputVisible.replace(/[^a-z]/gi, "");
		
		document.getElementById(inputVisible).parentElement.setAttribute("style","display:none");
		document.getElementById(inputVisible).setAttribute("validate","");
		document.getElementById(inputVisibleWithoutIndex+"Line"+indexVisible).setAttribute("style","display:none");
		document.getElementById(inputVisibleWithoutIndex+"Line"+indexVisible).nextSibling.nextSibling.setAttribute("style","display:none");
}
/**
 * Description:	creates the data string stored in a hidden variable for each multiple data type element
 *
 * param @node	the tbody object that contains all the input types for that nested element
 */
function createDataString(node, type) {

	var tdNodes = node.getElementsByTagName("td");

	var data = "";
	var nestedData = null;
	for( var i=0; i < tdNodes.length; i++) {

		var element=getCorrectFirstChild(tdNodes.item(i));

		
		 while(element != null && (element.className!="none" || element.getAttribute("typeahead")=="true") && (getCorrectAttribute(element,"parent",element.parent)==type)){

			var temp = getCorrectAttribute(element,"nextSibling",element.nextSibling);
			while(temp && temp.nodeName!="INPUT" && temp.nodeName!="SELECT")
			{
				temp = temp.nextSibling;
				if(temp==null)
					break;
			}

			if(getCorrectAttribute(element,"name",element.name)=="index"){
				var temp=null;
			}
			else if (getCorrectAttribute(element,"nodeName",element.nodeName) == "SPAN" && getCorrectAttribute(element, "id", element.id)!==null &&(getCorrectAttribute(element,"type",element.type)=="text"||getCorrectAttribute(element,"type",element.type)=="select"||getCorrectAttribute(element,"type",element.type)=="textarea"))
			{
				if(getCorrectFirstChild(element)!=null)
				{
					data +=getCorrectAttribute(element, "id", element.id) + nvDelimiter + getCorrectAttribute(getCorrectFirstChild(element),"nodeValue",getCorrectFirstChild(element).nodeValue) + pairDelimiter;
					element.setAttribute("translated", "true");
				}
			}
			else if (getCorrectAttribute(element,"type",element.type) == "text") {
				//updateForIllegalChars(element);
				data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + BatchEntryRemoveIllegalChar(getCorrectAttribute(element,"value",element.value)) + pairDelimiter;
			}
			else if (getCorrectAttribute(element,"type",element.type) == "select-one") {
				  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
				}
			//else if (getCorrectAttribute(element,"type",element.type) == "file") {
			//	  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			//	}
			else if (getCorrectAttribute(element,"type",element.type) == "textarea") {
			  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + BatchEntryRemoveIllegalChar(getCorrectAttribute(element,"value",element.value)) + pairDelimiter;
			}
			// check if this isn't control hidden variable
			else if (getCorrectAttribute(element,"type",element.type) == "hidden" && getCorrectAttribute(element,"name",element.name)!=null && getCorrectAttribute(element,"name",element.name)!="" && getCorrectAttribute(element,"mode",element.mode)!="batch-entry") {
			  data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			}
			else if (getCorrectAttribute(element,"type",element.type) == "checkbox") {
				if(element.checked==true){
					data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
				}else{
					data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + "" + pairDelimiter;
				}
			}
			else if (getCorrectAttribute(element,"type",element.type) == "radio" && getCorrectAttribute(element,"mode",element.mode)==true) {
				data +=getCorrectAttribute(element,"name",element.name) + nvDelimiter + getCorrectAttribute(element,"value",element.value) + pairDelimiter;
			}
			else if (getCorrectAttribute(element,"type",element.type)=="hidden" && getCorrectAttribute(element,"mode",element.mode)=="batch-entry"){
				nestedData = getCorrectAttribute(element,"value",element.value);
			}
			element = temp;
		}
	}
	//need to append nested batch entry
	if(nestedData!=null){

		data = data + startChildDelimiter + nestedData + endChildDelimiter;

	}

	return data;
}

function BatchEntryRemoveIllegalChar(string){
	
	return string.replace(/[\~\^\|\[\]]/g, "");
	
}


/**
* editBatchEntry function applies the values for the selected row and initializes the button to update
*
* @param type identifies which batch entry
* @param identifier identifies the item in the batch entry
*
* @return
*/

function editBatchEntry(type, identifier, activateOnChange) {



	//resetBatchEntryInputElements(type); // causes race condition , don't do it

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);

	var innerBatchExist = false;
	//	split up the string
	//var items = hiddenNode.value.split("|");
	var items=null;
	if(hiddenNode.value.search(/\]\|/)==-1){// doesn't contain child delimiter

		items = hiddenNode.value.split("|");
	}
	else {	//does contain child delimiter
		items = hiddenNode.value.split("]|");
		innerBatchExist = true;
	}
	if (items.length > 1)	{
		var innerBatch = "";
		for (var i=0; i < items.length; i++) {
			if(items[i]!=""){


				//cut out the inner batch entry
				//if(items[i].search(/\^\|/)!=-1 ) {
				if(innerBatchExist){
					innerBatch=items[i].substr(items[i].search(/\^\[/)+2, items[i].length);
					items[i] = items[i].substr(0,items[i].search(/\^\[/)+1);
				}
				//}
				var pairs = items[i].split("^");
				//	check the first name value pair which is the type selectbox to determine which one we are editing
				var check = pairs[0].split(nvDelimiter);
				if (check[1] == identifier) {
					//innerBatch=items[i].substr(items[i].search(/\^\[/)+2, items[i].length);
					//need to initialize the inner batch entry
					//if(innerBatch!=""){
						//alert(innerBatch);
						//need to find the inner batch data node
						var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);

						var tdNodes = nestedElementsTableNode.getElementsByTagName("td");
						for( var j=0; j < tdNodes.length; j++) {
							var element = getCorrectFirstChild(tdNodes.item(j));
							while(element != null && getCorrectAttribute(element,"className",element.className)!="none" && (getCorrectAttribute(element,"parent",element.parent)==type)){
								var temp = getCorrectAttribute(element,"nextSibling",element.nextSibling);
								if (getCorrectAttribute(element,"type",element.type)=="hidden" && getCorrectAttribute(element, "mode", element.mode)=="batch-entry") {
									
									element.value=innerBatch;
									
									BatchEntryInitialize(getCorrectAttribute(element,"name",element.name));
									
								}
								element = temp;
							}
						}
					//}

					for (var j=0; j < pairs.length; j++) {
						if(pairs[j]!="") {
							var nameValue = pairs[j].split(nvDelimiter);
							var name = nameValue[0];
							var value = nameValue[1];

							var node = getElementByIdOrByName(name);
							if (node != null) {
										
								//do this specifically for lab///////////////////////////////////////////////////////////
								if(type=="Test Result")	{
									//show the entity search as readonly on edit resulted test name
									if(node.id=="resultedTest[i].theIsolateVO.theObservationDT.searchResultRT"||node.id=="resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt" ){
										if(value!="")
										{
											getElementByIdOrByName("ResultedDisplay").innerText=value;
											if(getElementByIdOrByName("ResultedDisplay").textContent!=undefined)
												getElementByIdOrByName("ResultedDisplay").textContent=value;
										}
										getElementByIdOrByName("ResultedDisplay").className="visible";
										//getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt").className="none";
										//_ac_tr is the tr that contains type ahead control
										getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt_ac_table").className="none";
									}
									//show the entity search as readonly on edit organism
									if(node.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd"&& value!=""){
										getElementByIdOrByName("OrganismDisplay").innerText=value;
										if(getElementByIdOrByName("OrganismDisplay").textContent!=undefined)
												getElementByIdOrByName("OrganismDisplay").textContent=value;
											
										getElementByIdOrByName("OrganismDisplay").className="visible";
										getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_ac_table").className="none";
										//getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_textbox").className="none";
									}
									if(node.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName"&& value!=""){
										getElementByIdOrByName("OrganismDisplay").className="none";
										getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_ac_table").className="visible";
										getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").className="none";
									}
									
									if((node.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName"|| node.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].searchResultRT" ) && value!=""){
									//lab organism name vs coded result value////////////////////////////////////////////////////
										if(getElementByIdOrByName("organismIndicator")){
											getElementByIdOrByName("organismIndicator").className="visible";
										}
										if(getElementByIdOrByName("codedResultIndicator")){
											getElementByIdOrByName("codedResultIndicator").className="none";
											if(getElementByIdOrByName("codedResultIndicatorMessage")){
													getElementByIdOrByName("codedResultIndicatorMessage").className="none";
												}	
										}
										if(getElementByIdOrByName("susceptiblityInd")){
											getElementByIdOrByName("susceptiblityInd").className="visible";
										}
										if(getElementByIdOrByName("isolatetrackind")){
											getElementByIdOrByName("isolatetrackind").classname="visible";
										}
									}

									if(node.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code"  && value!=""){
									//lab organism name vs coded result value////////////////////////////////////////////////////
										if(getElementByIdOrByName("organismIndicator")){
											getElementByIdOrByName("organismIndicator").className="none";
										}
										if(getElementByIdOrByName("codedResultIndicator")){
											getElementByIdOrByName("codedResultIndicator").className="visible";
												if(getElementByIdOrByName("codedResultIndicatorMessage")){
													getElementByIdOrByName("codedResultIndicatorMessage").className="visible";
												}	
										}
										if(getElementByIdOrByName("susceptiblityInd")){
											getElementByIdOrByName("susceptiblityInd").className="none";
										}
										if(getElementByIdOrByName("IsolateTrackInd")){
											getElementByIdOrByName("IsolateTrackInd").className="none";
										}
									}
									//for edit situation
									if(node.id=="resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1"  && value=="Y"){

										if(getElementByIdOrByName("organismIndicator")){
											getElementByIdOrByName("organismIndicator").className="visible";
										}
										if(getElementByIdOrByName("codedResultIndicator")){
											getElementByIdOrByName("codedResultIndicator").className="none";
											if(getElementByIdOrByName("codedResultIndicatorMessage")){
													getElementByIdOrByName("codedResultIndicatorMessage").className="none";
												}	
										}
										if(getElementByIdOrByName("susceptiblityInd")){
											getElementByIdOrByName("susceptiblityInd").className="visible";
										}
										if(getElementByIdOrByName("IsolateTrackInd")){
											getElementByIdOrByName("IsolateTrackInd").className="visible";
										}
									
									}
									if(node.id=="resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1"  && value=="N"){

										if(getElementByIdOrByName("organismIndicator")){
											getElementByIdOrByName("organismIndicator").className="none";
										}
										if(getElementByIdOrByName("codedResultIndicator")){
											getElementByIdOrByName("codedResultIndicator").className="visible";
											if(getElementByIdOrByName("codedResultIndicatorMessage")){
													getElementByIdOrByName("codedResultIndicatorMessage").className="visible";
												}	
										}
										if(getElementByIdOrByName("susceptiblityInd")){
											getElementByIdOrByName("susceptiblityInd").className="none";
										}
										if(getElementByIdOrByName("IsolateTrackInd")){
											getElementByIdOrByName("IsolateTrackInd").className="none";
										}
									}
								}

								if(type=="Susceptibility")	{
									if(node.id=="resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt"  && value!=""){
										getElementByIdOrByName("DrugDisplay").className="none";
										getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").className="none";
										getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_textbox").className="visible";
									}
									if(node.id=="resultedTest[i].susceptibility[j].theObservationDT.searchResultRT"  && value!=""){
										getElementByIdOrByName("DrugDisplay").innerText=value;
										if(getElementByIdOrByName("DrugDisplay").textContent!=undefined)
												getElementByIdOrByName("DrugDisplay").textContent=value;
										
										getElementByIdOrByName("DrugDisplay").className="visible";
										getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_ac_table").className="none";
									}
			                                               if(node.id=="resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd"){
                                                                                var numericValue = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue");
                                                                                if(numericValue.value == "")
                                                                                {
                                                                                  node.disabled=true;
                                                                                  node.value="";
                                                                                }
                                                                                else
                                                                                {
                                                                                  node.disabled=false;
                                                                                }
			                                                }
								}
								if(type=="Lab Report")	{

									//show the entity search as readonly on edit resulted test name
									if(node.id=="labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT"||node.id=="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt" ){
										if(value!=""){
											getElementByIdOrByName("ResultedDisplay").innerText=value;
											if(getElementByIdOrByName("ResultedDisplay").textContent!=undefined)
												getElementByIdOrByName("ResultedDisplay").textContent=value;
										
										}
										getElementByIdOrByName("ResultedDisplay").className="visible";
										getElementByIdOrByName("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt_ac_table").className="none";
									}
									//show the entity search as readonly on edit organism
									if(node.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd"&& value!=""){
										getElementByIdOrByName("OrganismDisplay").innerText=value;
										if(getElementByIdOrByName("OrganismDisplay").textContent!=undefined)
												getElementByIdOrByName("OrganismDisplay").textContent=value;
										getElementByIdOrByName("OrganismDisplay").className="visible";
										getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName_ac_table").className="none";
									}
									if(node.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName"&& value!=""){
										getElementByIdOrByName("OrganismDisplay").className="none";
										getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName_ac_table").className="visible";
										getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName").className="none";
									}
									//for edit situation

									if(node.id=="labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1"  && value=="Y"){

										if(getElementByIdOrByName("organismIndicator")){
											getElementByIdOrByName("organismIndicator").className="visible";
										}
										if(getElementByIdOrByName("codedResultIndicator")){
											getElementByIdOrByName("codedResultIndicator").className="none";
											if(getElementByIdOrByName("codedResultIndicatorMessage")){
												getElementByIdOrByName("codedResultIndicatorMessage").className="none";
											}
										}
										if(getElementByIdOrByName("IsolateTrackInd")){
											getElementByIdOrByName("IsolateTrackInd").className="visible";
										}
									

									}
									if(node.id=="labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1"  && value=="N"){

										if(getElementByIdOrByName("organismIndicator")){
											getElementByIdOrByName("organismIndicator").className="none";
										}
										if(getElementByIdOrByName("codedResultIndicator")){
											getElementByIdOrByName("codedResultIndicator").className="visible";
											if(getElementByIdOrByName("codedResultIndicatorMessage")){
												getElementByIdOrByName("codedResultIndicatorMessage").className="visible";
											}
										}

									}
								}
								/////////////////////////////////////////////////////////////////////////////////////////


								if((getCorrectAttribute(node,"type",node.type)=="text"||getCorrectAttribute(node,"type",node.type)=="textarea" || getCorrectAttribute(node,"type",node.type)=="hidden")){
									if(node.id=="resultedTest[i].theIsolateVO.theObservationDT.searchResultRT" && value!="")
										node.value = value;
									else if(node.id=="labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT" && value!="")
										node.value = value;
									else if(node.id!="resultedTest[i].theIsolateVO.theObservationDT.searchResultRT"&&node.id!="labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT")
										node.value = value;
									//this is for numeric value / units check in lab and morb
									if(node.onkeyup)
										node.onkeyup();
										
								}


								if(node.onchange && getCorrectAttribute(node,"type",node.type)=="select-one" && activateOnChange!=false&& !(getCorrectAttribute(node, "entitysearch", node.entitysearch))/* &&!(node.id=="resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")*/) {
									node.value = value;
									node.onchange();

								}
								
								if(node.onchange && getCorrectAttribute(node,"type",node.type)!="select-one"&& !(getCorrectAttribute(node, "entitysearch", node.entitysearch)) /* &&!(node.id=="resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")*/) {
									node.onchange();
								}
								if(getCorrectAttribute(node,"type",node.type)=="select-one"){
									//need to wait while the select is populated
									if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
									{

										//do this specifically for lab//////////////////////////////////////////////
										if(node.id=="resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt" ){
											
											if(value!=""){
												getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.searchResultRT").value=value;
												node.value=value;
												//this onchange getOrganismIndicator will cause the suscept to be purged
												//node.onchange();	//perform the labcodelookup for resultedtest
											}
										} else if(node.id=="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt" ){
											if(value!="")
												getElementByIdOrByName("labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT").value=value;
										}
										else
											node.value = value;
									}
									else{
										if(type=="Test Result"||type=="Susceptibility"||type=="Lab Report"){
											resetBatchEntryInputElements(type);
											return;
										}else
											editBatchEntryWait(type, identifier);
									}
								}
								if((getCorrectAttribute(node,"type",node.type)=="checkbox")&& value!="") {
									node.checked=true;
								}
								if((getCorrectAttribute(node,"type",node.type)=="checkbox")&& value=="")
									node.checked=false;
								if(getCorrectAttribute(node,"type",node.type)=="radio"){
									var radioNode = getElementByIdOrByName(value);
									if(radioNode!=null)
										radioNode.checked=true;

								}
								//activate typeahead 
								if(node.onclick)
									node.onclick();
							}
						}
					}
				}
			}
		}
		//	update the add information link with update information
		var nodeAddButton = getElementByIdOrByName("BatchEntryAddButton" + type);
		nodeAddButton.value = nodeAddButton.value.replace(/Add\s/, "Update ");
		nodeAddButton.onclick = function() { updateBatchEntry(type,identifier);};
	}

}

function editBatchEntryWait(type, identifier){


	var timeoutInt = window.setTimeout("editBatchEntry('"+ type +"','"+ identifier + "',false)", 1000);
}



//	when user has finished editing and wants to save changes
function updateBatchEntry(type, identifier) {
	if(CheckForChildWindow()){
	var error2TD = getElementByIdOrByName("error2");
	if( error2TD ) error2TD.setAttribute("className", "none");
	var bIdentified = false;

	var error = false;
	var innerBatchExist = false;
	var delimiter=null;
	
			
	error = validateBatchEntry(type, identifier);
	if (error == false) {
		var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
		//var unused = "";
		var prefix = "";
		var suffix="";
		var editedInput = "";
		var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);
		


		if(hiddenNode.value.search(/\]\|/)==-1){// doesn't contain child delimiter
			items = hiddenNode.value.split("|");
			delimiter=lineDelimiter;
		}
		else {	//does contain child delimiter
			items = hiddenNode.value.split("]|");
			innerBatchExist = true;
			delimiter=endChildDelimiter+lineDelimiter;
		}



		//var items = hiddenNode.value.split("|");

		if (items.length > 1)	{
			for (var i=0; i < items.length; i++) {
				if (items[i]!=""){


					var pairs = items[i].split("^");
					//	check the first name value pair which is the type selectbox to determine which one we are editing
					check = pairs[0].split(nvDelimiter);

					if (check[1] == identifier) {
						editedInput = "index" + nvDelimiter + identifier + pairDelimiter +createDataString(nestedElementsTableNode , type);
						//	need to change the type drop down value to what is being edited

						bIdentified=true;
					}
					else if(bIdentified==false)
						prefix += items[i] + delimiter;
					else if(bIdentified==true)
						suffix += items[i] + delimiter;
					/*else {
						unused += items[i] + delimiter;

					}*/
				}
			}

			//if the editedInput has default then remove the default from everywhere else

			if(editedInput.search(/~default/) > 0){
				// we have a default in the edited string, now remove from the unused string
				unused = unused.replace(/~default/g,"~");

			}
			

			hiddenNode.value = prefix + editedInput + "|" + suffix;

			//	need to enable  the type select box so that users cannot select to add info to a new type during edit mode

			//var SelectNode = nestedElementsTableNode.getElementsByTagName("select").item(0);
			//SelectNode.disabled = false;

			//update the CSFs with Isolate Value
			updateTrackingIsolate(type, hiddenNode.value);

			updateBatchEntryHistoryBox(type);

			resetBatchEntryInputElements(type);

		}

	}
	}
}

//	need to update the history box based on what the user has changed
function updateBatchEntryHistoryBox(type,initialize) {
	
	var counter = 0;
	var counterColIndex;
	var bCounterOn = false;

	var historyBoxNode = getElementByIdOrByName("nestedElementsHistoryBox|" + type);

	var removeTR = getCorrectFirstChild(historyBoxNode);
	var previousHeader=null;
	var treatmentOther=false;
	 while(removeTR != null){
		  var temp = removeTR.nextSibling;
		  historyBoxNode.removeChild(removeTR);
		  removeTR= temp;
	 }



	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);

	//	split up the rows
	var items=null;
	if(hiddenNode.value.search(/\]\|/)==-1)// doesn't contain child delimiter
		items = hiddenNode.value.split("|");
	else
		items = hiddenNode.value.split("]|");

	//var items = hiddenNode.value.split("|");

	//sort the array

	// sort the array based on the as of date
	items.sort(function(a,b){
					if(a.search(/asOfDate_s/)==-1)
						return 0;

					var aDateString = a.substr(a.search(/asOfDate_s/)+11, 10);
					var bDateString = b.substr(b.search(/asOfDate_s/)+11, 10);
					var aDate=null;
					var bDate=null;
					if(isDate(aDateString) && isDate(bDateString)){
							aDate = DateToSortableString(new Date(aDateString));
							bDate = DateToSortableString(new Date(bDateString));
						if(aDate > bDate)
						{
							return -1;
						}
						if(aDate < bDate)
						{
							return 1;
						}
					}
					return 0;

				});




	if(items.length > 1)	{
		var lineCounter = 0;
		for (var i=0; i < items.length; i++) {
			previousHeader=null;
			treatmentOther=false;
			//cut out the inner batch entry
			if(items[i].search(/\^\|/)!=-1 ){
				items[i] = items[i].substr(0,items[i].search(/\^\[/)+1);
			}


			if(items[i].search(/statusCd~I/)==-1 && items[i]!=""){

				lineCounter++;
				var newTR = document.createElement("tr");
				newTR.setAttribute("valign", "top");
				// initialize the reorder functionality
				//newTR.onclick = function() { reorderBatchEntry(this);};

				if (i % 2 == 0){
					newTR.setAttribute("class","NotShaded");
					newTR.setAttribute("className","NotShaded");
					
				}
				else{
					newTR.setAttribute("class","Shaded");
					newTR.setAttribute("className","Shaded");
				}


				var pairs = items[i].split("^");

				if (pairs.length > 1)	{
					
					identifier = pairs[0].split(nvDelimiter);

					//	the edit and delete actions
					var editDeleteTD = document.createElement("td");
					editDeleteTD.width=75;
					editDeleteTD.setAttribute("align", "left");
					var aEdit = document.createElement("a");
						aEdit.setAttribute("href", "javascript:editBatchEntry('" + type + "','" + identifier[1]+ "');");
					var editText = document.createTextNode("Edit");
					aEdit.appendChild(editText);



					var newDivideText = document.createTextNode(" | ");
					var aDelete = document.createElement("a");
					if(type=='Attachment')
						aDelete.setAttribute("href", "javascript:if(confirmDelete()) deleteBatchEntry('" + type + "','" + identifier[1] + "');");
					else
						aDelete.setAttribute("href", "javascript:deleteBatchEntry('" + type + "','" + identifier[1] + "');");
					var deleteText = document.createTextNode("Delete");
					aDelete.appendChild(deleteText);


					if(type!='Attachment')
						editDeleteTD.appendChild(aEdit);
					if(gBatchEntryDelete)
					{
						if(type!='Attachment')
							editDeleteTD.appendChild(newDivideText);
						editDeleteTD.appendChild(aDelete);
					}

					//check if permission exist to activate edit delete links
					if(items[i].search(/statusCd~R/)==-1){
						newTR.appendChild(editDeleteTD);
						
					}
					else {
						var blankTD = document.createElement("td");
						newTR.appendChild(blankTD);
					}

					// Display line numbers if needed.
					var tableNode = getElementByIdOrByName("nestedElementsTable|" + type);

					if( (tableNode!=null) && getCorrectAttribute(tableNode, "showLineNumbers", tableNode.showLineNumbers)=="true") {
						var lineNum = i+1;
						var aNumber = document.createTextNode( parseInt(lineCounter) );
						var lineNumberTD = document.createElement("td");
						lineNumberTD.width=50;
						lineNumberTD.appendChild( aNumber );
						newTR.appendChild( lineNumberTD );
					}





						for (var j=0;j < pairs.length; j++) {

							var displayText = "";

							if(pairs[j]!=""){
								var nameValue = pairs[j].split(nvDelimiter);
								var name = nameValue[0];
								var value = nameValue[1];
								//	have to find out if the element is text or select and choose the value or name
								var elementNode = getElementByIdOrByName(name);


								
								/////////////////////////////////for treatment
								if(name=="treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd" && value=="OTH"){
									treatmentOther=true;
								}
								/////////////////////////////////////////////////////

								if (elementNode != null)
								{
									// need to trigger the conditional to get data in header
									if(getCorrectAttribute(elementNode,"onchange",elementNode.onchange) && getCorrectAttribute(elementNode,"type", elementNode.type)=="select-one" && !(getCorrectAttribute(elementNode,"entitysearch",elementNode.entitysearch))) {
										
										elementNode.value = value;
										elementNode.onchange();
										if(getElementByIdOrByName('defaultStateFlagEx')!=null)
										{
										   getElementByIdOrByName('defaultStateFlagEx').value = "true";
										}
										elementNode.value="";
									}

									//if header element is not shown, need to account for type ahead hidden select
									if (previousHeader!=getCorrectAttribute(elementNode,"header",elementNode.header) && getCorrectAttribute(elementNode,"header",elementNode.header) && elementNode.offsetWidth==0 && getCorrectAttribute(elementNode,"typeahead",elementNode.typeahead)!="true"){
										
										//var bool = true
										//var typeAheadNode = getElementByIdOrByName(elementNode.id);
										//alert(elementNode.id);
										/*
										if(typeAheadNode!=null)
											if(typeAheadNode.offsetWidth!=0)
												alert(elementNode.id + "_textbox");
												bool = false;
										*/	
										//if(bool){
											
											var newTD = document.createElement("td");
											var newText =document.createTextNode(" ");
											newTD.appendChild(newText);
											newTR.appendChild(newTD);
											previousHeader = getCorrectAttribute(elementNode,"header",elementNode.header);
										//}
										
									}

									//do this specifically for lab///////////////////////////////////////////////////////////
										//explicitly do the conditional to make sure it is in proper state
										if(elementNode.id=="resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")
											elementNode.onchange();

										if(elementNode.id=="resultedTest[i].theIsolateVO.theObservationDT.searchResultRT" && value!=""){
											getElementByIdOrByName("ResultedDisplay").innerText=elementNode.value;
											getElementByIdOrByName("ResultedDisplay").className="visible";
											getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt").className="none";
										}
										//updated on Jan 28, 04
										else if(elementNode.id=="resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt" && value!=""){
											elementNode.className="visible";
											getElementByIdOrByName("ResultedDisplay").className="none";
										}
										/////////////////////////////////// morb
										if(elementNode.id=="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt" && value!=""){
											//elementNode.className="visible"; for type ahead
											getElementByIdOrByName("ResultedDisplay").className="none";
										}

										if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName"  && value!=""){
											if(getElementByIdOrByName("organismIndicator")){
												getElementByIdOrByName("organismIndicator").className="visible";
												getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName").className="visible"
												getElementByIdOrByName("OrganismDisplay").className="none"
											}
											if(getElementByIdOrByName("codedResultIndicator")){
												getElementByIdOrByName("codedResultIndicator").className="none";
											}
										}

										if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code"  && value!=""){
											if(getElementByIdOrByName("organismIndicator")){
												getElementByIdOrByName("organismIndicator").className="none";
											}
											if(getElementByIdOrByName("codedResultIndicator")){
												getElementByIdOrByName("codedResultIndicator").className="visible";
											}
										}
										//////////////////////////////////////////////////////////////////////

										//lab organism name vs coded result value////////////////////////////////////////////////////
										if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName"  && value!=""){
											if(getElementByIdOrByName("organismIndicator")){
												getElementByIdOrByName("organismIndicator").className="visible";
												getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").className="visible"
												getElementByIdOrByName("OrganismDisplay").className="none"
											}
											if(getElementByIdOrByName("codedResultIndicator")){
												getElementByIdOrByName("codedResultIndicator").className="none";
											}
										}
										
										if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code"  && value!=""){
											if(getElementByIdOrByName("organismIndicator")){
												getElementByIdOrByName("organismIndicator").className="none";
											}
											if(getElementByIdOrByName("codedResultIndicator")){
												getElementByIdOrByName("codedResultIndicator").className="visible";
											}
										}
										if(elementNode.id=="resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt"  && value!=""){
											getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").className="visible";
										}
									/////////////////////////////////////////////////////////////////////////////////////////

									//update history box with designated header fields
									//check if the element referenced in the header is visible
										if (getCorrectAttribute(elementNode,"header",elementNode.header) && (elementNode.offsetWidth!=0 || initialize || getCorrectAttribute(elementNode,"type",elementNode.type) == "hidden" || getCorrectAttribute(elementNode,"typeahead",elementNode.typeahead)=="true"))
										{



											var pattern = new RegExp("select");
											if (pattern.test(elementNode.type))	{
												
												for (var v=0; v < elementNode.options.length; v++)	{
													if (elementNode.options[v].value == value) {
														displayText = elementNode.options[v].text;
													}
												}
												if(displayText=="")
													displayText=value;
												
											}
											else if (getCorrectAttribute(elementNode,"type",elementNode.type).toLowerCase() == "text")
												displayText = value;
											else if (getCorrectAttribute(elementNode,"type",elementNode.type).toLowerCase()== "hidden")
												displayText = value;
											else if (getCorrectAttribute(elementNode,"type",elementNode.type).toLowerCase()== "textarea")
												displayText = value;
											else if (getCorrectAttribute(elementNode,"type",elementNode.type).toLowerCase() == "checkbox")
												displayText = value;
											




											//need to check for duplicate headers and combine them 		
											if(previousHeader==getCorrectAttribute(elementNode,"header",elementNode.header) || getCorrectAttribute(elementNode,"partner",elementNode.partner))	{
												var lastChild = newTR.lastChild;

													//do this specifically for lab//////////////////////////////////
													if(displayText=="")
														var nothing=null;
													//organism name drop down
													else if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName"){
														lastChild.innerHTML = lastChild.innerHTML + displayText + "<br>";
													}
													//organism name hidden
													else if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd" && value!=""){
														lastChild.innerHTML = displayText + "<br>";
													}
													//coded result drop down
													else if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code"){
														lastChild.innerHTML = lastChild.innerHTML + displayText + "<br>";
													}
													//text result value
													else if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt"){
														lastChild.innerHTML = lastChild.innerHTML +  "<br>" + displayText;
													}
													else if(elementNode.id=="resultedTest[i].theTrackIsolateVO.obsValueTxtDT_s[0].valueTxt"){
														lastChild.innerHTML = lastChild.innerHTML +  "<br>" + displayText;
													}
													//do this specifically for morb
													//organism name hidden
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd" && value!=""){
														lastChild.innerHTML = displayText + "<br>";
													}
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueTxtDT_s[0].valueTxt"){
														lastChild.innerHTML = lastChild.innerHTML +  "<br>" + displayText;
													}
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code"){

														newTD.innerHTML = displayText + "<br>";
													}
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT" && value!=""){
														lastChild.innerHTML = displayText + "<br>";
													}
													/////////////////////////////////////////////treatment
													else if(name=="treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt" && treatmentOther){
														lastChild.innerHTML = value;
													}
													else if(name=="treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt" && !treatmentOther){
														lastChild.innerHTML = lastChild.innerHTML;
													}
													/////////////////////////////////////////////////
													else
													{
														//updated on Jan 28, 04
														if(elementNode.id=="resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt")
														{
														}else if(elementNode.id=="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt")
														{
														}else{
															

															if(displayText.indexOf("(http") != -1) {
																var link_start = displayText.indexOf("(");
																var noLnkTxt = displayText.substring(0,link_start);															
																lastChild.innerHTML = lastChild.innerHTML + noLnkTxt + "&nbsp;";
															}
															else {
																lastChild.innerHTML = lastChild.innerHTML + displayText + "&nbsp;";
															}
														}
													}

												////////////////////////////////////////////////////////////////

											} else {
												var newTD = document.createElement("td");
												newTD.setAttribute("valign", "top");

												//do this specifically for lab//////////////////////////////////
													if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName"){
														newTD.innerHTML = displayText + "<br>";
													}
													//organism name hidden
													else if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd"){
														newTD.innerHTML = displayText + "<br>";
													}
													//coded result drop down
													else if(elementNode.id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code"){
														newTD.innerHTML = displayText + "<br>";
													}
													//suscept
													else if(elementNode.id=="resultedTest[i].susceptibility[j].obsValueCodedDT_s[0].code"){
														newTD.innerHTML = displayText + "<br>";
													}
													/////////////////////////////////for morb
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName"){
														newTD.innerHTML = displayText + "<br>";
													}
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd"){
														newTD.innerHTML = displayText + "<br>";
													}
													else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code"){

														newTD.innerHTML = displayText + "<br>";
													}
												////////////////////////////////////////////////////////////////
													else{
														if(elementNode.id=="resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt")
														{
														//updated on Jan 28, 04
														}else if(elementNode.id=="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt")
														{
															var newText =document.createTextNode(displayText+ " ");
															newTD.appendChild(newText);
														}else{
															if(displayText.indexOf("(http") != -1) {
																var link_start = displayText.indexOf("(");
																var noLnkTxt = displayText.substring(0,link_start);															
																var newText =document.createTextNode(noLnkTxt+ " ");
																newTD.appendChild(newText);
															}
															else {
																var newText =document.createTextNode(displayText+ " ");
																newTD.appendChild(newText);
																
															}
														}

													} 
													
												newTR.appendChild(newTD);
											}



											if(getCorrectAttribute(elementNode,"counter",elementNode.counter)=="on")
											{
												counterColIndex = j;
												if(!isNumeric1(value))
												  value = 0;
												if(!isNumeric1(counter))
												  counter = 0;

												counter = parseInt(counter) + parseInt(value);
												//if(isNaN(counter))
												   //counter = 0;
												bCounterOn = true;
											}

											previousHeader = getCorrectAttribute(elementNode,"header",elementNode.header);
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
/**
* reorderBatchEntry function initializes the variables required for reordering
*
* @param string row identifies which row has been selected
* @param
*
* @return
*/
function reorderBatchEntry(row){
	//save the previous bgcolor
	row.setAttribute("prevClassName", row.className);
	row.setAttribute("className","YellowShaded");
}



//	clear the input elements after user creates a new one
function resetBatchEntryInputElements(type) {

	var nestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
	var selects = nestedElementsTableNode.getElementsByTagName("select");
	var inputs = nestedElementsTableNode.getElementsByTagName("input");
	var textareas = nestedElementsTableNode.getElementsByTagName("textarea");
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	
		for( var i=0; i < inputs.length; i++) {
	
			if(inputs.item(i).type == "text" && inputs.item(i).name.search("asOfDate")!=-1) {
	
	
				if(getElementByIdOrByName("today") != null && getElementByIdOrByName("today").value.length > 0)
	
				inputs.item(i).value= getElementByIdOrByName("today").value;
				else
				inputs.item(i).value= "";
	
			}
	
			if (inputs.item(i).type == "text" && inputs.item(i).name.search("asOfDate")==-1)
				inputs.item(i).value = "";
			if (inputs.item(i).type == "checkbox")
				inputs.item(i).checked=false;
			if (inputs.item(i).type == "radio"){
				inputs.item(i).checked=false;
				if(inputs.item(i).onclick)
					inputs.item(i).onclick();
			}
			if (inputs.item(i).type == "hidden" && inputs.item(i).name.search("theObservationDT.cdSystemCdRT")!=-1)
				inputs.item(i).value = "";
			
			
			if (inputs.item(i).type == "hidden" && inputs.item(i).name.search("theObservationDT.cd")==-1 && inputs.item(i).getAttribute("mode")!="batch-entry"){
				inputs.item(i).value = "";
			}
			if (inputs.item(i).type == "hidden"&& inputs.item(i).getAttribute("mode")=="batch-entry" && inputs.item(i).name!=type)
			{
				//clean up the history box of inner batch entries
				var historyBoxNode = getElementByIdOrByName("nestedElementsHistoryBox|"+ inputs.item(i).name);
				var removeTR = getCorrectFirstChild(historyBoxNode);
				while(removeTR != null){
				  var temp = removeTR.nextSibling;
				  historyBoxNode.removeChild(removeTR);
				  removeTR= temp;
				}
				inputs.item(i).value="";
			
			}
			
	}
	
	for( var i=0; i < selects.length; i++) {

		selects.item(i).selectedIndex = 0;
		//do some lab specific checks
                
		//do this specifically for lab///////////////////////////////////////////////////////////
		if(type=="Test Result")	{
			//lab resulted test name select vs hidden variable
			if(selects.item(i).id=="resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt"){
				//selects.item(i).className="visible";
				getElementByIdOrByName("ResultedDisplay").className="none";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt_ac_table").className="visible";
			}
			if(selects.item(i).id=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName"){
				//selects.item(i).className="visible";
				getElementByIdOrByName("OrganismDisplay").className="none";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_ac_table").className="visible";
			}
			if(selects.item(i).id=="resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd"){
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd_textbox").disabled=true;
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd_button").className="none";
				//selects.item(i).disabled=true;
			}
			
			


		}
		if(type=="Susceptibility")	{
			if(selects.item(i).id=="resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt"){
				selects.item(i).className="none";
				getElementByIdOrByName("DrugDisplay").className="none";
				getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_ac_table").className="visible";
			}
			if(selects.item(i).id=="resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd"){
				getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd_textbox").disabled=true;
				getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd_button").className="none";
                                //selects.item(i).disabled=true;
			}
		}
		if(type=="Lab Report")	{	// lab on morb report
			//lab resulted test name select vs hidden variable
			if(selects.item(i).id=="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt"){
				//selects.item(i).className="visible"; for type ahead
				getElementByIdOrByName("ResultedDisplay").className="none";
				getElementByIdOrByName("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt_ac_table").className="visible";
			}
			if(selects.item(i).id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName"){
				//selects.item(i).className="visible";
				getElementByIdOrByName("OrganismDisplay").className="none";
				getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName_ac_table").className="visible";
			}
			if(selects.item(i).id=="labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd"){
				getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd_textbox").disabled=true;
				getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd_button").className="none";
				//selects.item(i).disabled=true;
			}

		}
		if(type=="Address")	{
			
			if(selects.item(i).getAttribute("default")!=null && selects.item(i).getAttribute("default")!=""){
				selects.item(i).value = selects.item(i).getAttribute("default");
				var varOptions = selects.item(i).options;
				getElementByIdOrByName(selects.item(i).name + "_textbox").value = varOptions[selects.item(i).selectedIndex].text;
			}
			
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		
		

		if(selects.item(i).onchange && !(getCorrectAttribute(selects.item(i), "entitysearch",selects.item(i).entitysearch))/*&&!(selects.item(i).id=="resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")*/)
			selects.item(i).onchange();
		
	}

	//lab organism name vs coded result value////////////////////////////////////////////////////

	if(type=="Test Result" || type=="Lab Report")	{

		if(getElementByIdOrByName("organismIndicator")){
			getElementByIdOrByName("organismIndicator").className="none";
		}
		if(getElementByIdOrByName("IsolateTrackInd")){
			getElementByIdOrByName("IsolateTrackInd").className="none";
		}
		if(getElementByIdOrByName("codedResultIndicator")){
			getElementByIdOrByName("codedResultIndicator").className="visible";
		}
		if(getElementByIdOrByName("susceptiblityInd")){
			getElementByIdOrByName("susceptiblityInd").className="none";
		}
		if(getElementByIdOrByName("IsolateTrackInd")){
			getElementByIdOrByName("IsolateTrackInd").className="none";
		}

		if(getElementByIdOrByName("codedResultIndicatorMessage") != null){
			getElementByIdOrByName("codedResultIndicatorMessage").className="none";
		}
	}
	if(type=="Susceptibility")	{
		getElementByIdOrByName("codedResultVal").className="visible";
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
			nodeAddButton.onclick = function() { addNewBatchEntry(type);};


}
/**
 * Description:	update the status code to I
 *
 * param @type	name of of the nested type on the page
 * param @identifier name of the specific data element being deleted
 */
function deleteBatchEntry(type, identifier) {
	
	if(type=='Attachment'){
		currentDate = document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeTime").value;
		currentUser = document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChgUserNm").value;
		currentId=document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeUserId").value;
		
		var identifierDelete=identifier-1;
		var inputToDelete="fileAttachment"+identifierDelete;
		
	}
	
	
	if(type=="Field"){
		if (!confirm("If you continue with the Delete action, you will delete the information. Select OK to continue, or Cancel to not continue."))
			return;
	}
	
	var unused = "";
	var used = "";
	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);


	var items=null;
	var innerBatchExist = false;
	if(hiddenNode.value.search(/\]\|/)==-1)// doesn't contain child delimiter
		items = hiddenNode.value.split("|");
	else {
		items = hiddenNode.value.split("]|");
		innerBatchExist = true;
	}
	if (items.length > 1)	{
		for (var i=0; i < items.length; i++) {

			var pairs = items[i].split("^");
			//	check the first name value pair which is the type selectbox to determine which one we are editing
			check = pairs[0].split(nvDelimiter);
			if (check[1] == identifier) {
				//	need to update the status code of the string
				used = items[i].replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");
				if(type=='Attachment'){
					check1 = pairs[1].split(nvDelimiter);
					if(check1[1].indexOf("fileAttachment") != -1) //This works while creating report, doesn't work for edit functionality 
						document.getElementById(check1[1]).value = "";
					}
			}
			else {
				if(innerBatchExist)
					unused += items[i] + "]|";
				else
					unused += items[i] + "|";
			}
		}
		if(innerBatchExist)
			hiddenNode.value = used + "]|" + unused;
		else
			hiddenNode.value = used + "|" + unused;

		
		
		//delete the CSFs from the Isolates
		deleteTrackingIsolate(type, hiddenNode.value);			
			
		updateBatchEntryHistoryBox(type);
	}
	
	
	unused = null;
	hiddenNode = null;
	items = null;



	resetBatchEntryInputElements(type);
	
	if(type=='Attachment'){
		document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeTime").value=currentDate;
		document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChgUserNm").value=currentUser;
		document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeUserId").value=currentId;
		
		
	}
}


/**
 * Description:	function used in validation procedure, resets all the error msgs that may appear
 *
 * param @type	name of of the nested type on the page
 */

function resetBatchEntryErrorMessages(type)
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
			     (inputNodes.item(i).type == "checkbox") ||
			    (inputNodes.item(i).type == "hidden")	) {

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
			var removeTR = getCorrectFirstChild(spans.item(i));
				while(removeTR != null){
				  var temp = removeTR.nextSibling;
				  spans.item(i).removeChild(removeTR);
				  removeTR= temp;
				}
		}
	}
}

function BatchEntryKeySubmit(tbody){
/*
	var key=event.keyCode
	if (key==13) {
		turnOffParentSubmit = true;

		buttonNode = getElementByIdOrByName("BatchEntryAddButton" + tbody.type);
		buttonNode.click();
	}
*/

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


							if(type=="Lab Report")	{

								if(name=="labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1"  && value=="Y"){

									if(getElementByIdOrByName("organismIndicator")){
										setAttributeClass(getElementByIdOrByName("organismIndicator"), "visible");
										//getElementByIdOrByName("organismIndicator").className="visible";
									}
									if(getElementByIdOrByName("codedResultIndicator")){
										setAttributeClass(getElementByIdOrByName("codedResultIndicator"), "none");
										//getElementByIdOrByName("codedResultIndicator").className="none";
									}
									if(getElementByIdOrByName("IsolateTrackInd")){
										setAttributeClass(getElementByIdOrByName("IsolateTrackInd"), "visible");
										//getElementByIdOrByName("IsolateTrackInd").className="visible";
									}
								
								}
								if(name=="labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1"  && value=="N"){

									if(getElementByIdOrByName("organismIndicator")){
										setAttributeClass(getElementByIdOrByName("organismIndicator"), "none");
										//getElementByIdOrByName("organismIndicator").className="none";
									}
									if(getElementByIdOrByName("codedResultIndicator")){
										setAttributeClass(getElementByIdOrByName("codedResultIndicator"), "visible");
										//getElementByIdOrByName("codedResultIndicator").className="visible";
									}

								}

							}


						var node = getElementByIdOrByName(name);
						////////////////////////// for morb

						if(name=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd" && value!=""){
							var orgNode=getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName");

							orgNode.innerHTML=value;
						}

						/////////////////////////////////////////////////
						
						if (node != null && getCorrectAttribute(node,"type",node.type)!="hidden")	{

							if (node.hasChildNodes())
							{
								var deleteNode = node.removeChild(getCorrectFirstChild(node));
								deleteNode = null;
								
								if(node.textContent.trim()=="")
									node.textContent="";
							}

							if (getCorrectAttribute(node, "conditional", node.conditional))
							{
								
								var payloadTbody = getElementByIdOrByName("nestedElementsControllerPayload" + getCorrectAttribute(node, "conditional", node.conditional));
		
								if(value==getCorrectAttribute(node, "trigger", node.trigger)){
									payloadTbody.setAttribute("className", "visible");
									payloadTbody.setAttribute("class", "visible");

}
								else{
									payloadTbody.setAttribute("className", "none");
									payloadTbody.setAttribute("class", "none");

}
								//accomodate line triggers	
								
								var trNodes = payloadTbody.getElementsByTagName("TR");
								for (var t=0; t < trNodes.length; t++) {
									if(getCorrectAttribute(trNodes.item(t), "trigger", trNodes.item(t).trigger)){
										
										if(getCorrectAttribute(trNodes.item(t), "trigger", trNodes.item(t).trigger)==value){
											trNodes.item(t).className="visible";
											payloadTbody.setAttribute("class", "visible");
											trNodes.item(t).className="visible";
											payloadTbody.setAttribute("class", "visible");

										}else{
											trNodes.item(t).className="none";
											trNodes.item(t).setAttribute("class","none");

										}
									}	
								}
								
							}

							if (getCorrectAttribute(node,"type",node.type) == "select"){
								var temp = findNameForValue("list"+ name, value);
								if(temp!="")
									value=temp;

							}

							if((getCorrectAttribute(node,"type",node.type)=="checkbox")&& value!="") {								
								node.checked=true;
							} else if((getCorrectAttribute(node,"type",node.type)=="checkbox")&& value=="") {
								node.checked=false;
							} else {
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


function BatchEntryInitializeForView(type){

	var hiddenNode = getElementByIdOrByName("nestedElementsHiddenField" + type);

	var items = null;
	var innerBatchExist = false;
	if(hiddenNode.value.search(/\]\|/)==-1){// doesn't contain child delimiter

		items = hiddenNode.value.split("|");
	}
	else {	//does contain child delimiter
		items = hiddenNode.value.split("|]|");
		innerBatchExist = true;
	}


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
					if(innerBatchExist)
						indexed+="]|";
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
	var items=null;
	var previousHeader=null;
	var treatmentOther=false;
	if(hiddenNode.value.search(/\]\|/)==-1)// doesn't contain child delimiter
		items = hiddenNode.value.split("|");
	else
		items = hiddenNode.value.split("]|");


	// sort the array based on the as of date
		items.sort(function(a,b){
						if(a.search(/asOfDate_s/)==-1)
							return 0;

						var aDateString = a.substr(a.search(/asOfDate_s/)+11, 10);
						var bDateString = b.substr(b.search(/asOfDate_s/)+11, 10);
						var aDate=null;
						var bDate=null;
						if(isDate(aDateString) && isDate(bDateString)){
								aDate = DateToSortableString(new Date(aDateString));
								bDate = DateToSortableString(new Date(bDateString));
							if(aDate > bDate)
							{
								return -1;
							}
							if(aDate < bDate)
							{
								return 1;
							}
						}
						return 0;

				});




	for (var i=0; i < items.length; i++) {
		previousHeader=null;
		treatmentOther=false;
		//cut out the inner batch entry
		if(items[i].search(/\^\|/)!=-1 ){
			items[i] = items[i].substr(0,items[i].search(/\^\[/)+1);
		}

		if(items[i].search(/statusCd~I/)==-1 && items[i]!=""){
						var newTR = document.createElement("tr");
						newTR.setAttribute("width","100%");


						if (i % 2 == 0){
							newTR.setAttribute("class","NotShaded");
							newTR.setAttribute("className","NotShaded");
							
						}
						else{
							newTR.setAttribute("class","Shaded");
							newTR.setAttribute("className","Shaded");
							
						}


						var pairs = items[i].split("^");
						identifier = pairs[0].split(nvDelimiter);

						var DetailsTD = document.createElement("td");
						DetailsTD.setAttribute("align", "left");
						var DownloadTD = document.createElement("td");
						DownloadTD.setAttribute("align", "left");
						if(type!='Attachment'){
							var DetailsLink = document.createElement("a");
							DetailsLink.setAttribute("href", "javascript:ViewNestedElementsType('" + type + "','" + identifier[1]+ "');");
							var DetailsText = document.createTextNode("Details");
							DetailsLink.appendChild(DetailsText);

							DetailsTD.appendChild(DetailsLink);
						}
						newTR.appendChild(DetailsTD);
						if(type=='Attachment'){
							var DownloadLink = document.createElement("a");
							var DownloadTextFile; 
							var attachmentUID ;
							for (var j=0; j < pairs.length; j++) {
								if(pairs[j]!=""){
									var nameValue = pairs[j].split(nvDelimiter);
									var name = nameValue[0];
									var value = nameValue[1];
									if(name == "attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt")
										DownloadTextFile = value;
									if(name == "attachment_s[i].attachmentVO_s[0].theAttachmentDT.nbsAttachmentUid")
										attachmentUID = value;
								}
							}
						//	if(pairs[7].split("~")[0] == "attachment_s[i].attachmentVO_s[0].theAttachmentDT.nbsAttachmentUid" && pairs[1].split("~")[0] == "attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt" )
							//DownloadLink.setAttribute("href", "/nbs/InvDownloadFile.do?ContextAction=doDownload&nbsAttachmentUid="+pairs[7].split("~")[1]+"&fileNmTxt="+pairs[1].split("~")[1]);
							DownloadLink.setAttribute("href", "/nbs/InvDownloadFile.do?ContextAction=doDownload&nbsAttachmentUid="+attachmentUID+"&fileNmTxt="+DownloadTextFile);
							//var DownloadText = document.createTextNode(pairs[1].split("~")[1]);
							var DownloadText = document.createTextNode(DownloadTextFile);
							DownloadLink.appendChild(DownloadText);
							pairs[1].split("~")[1] = DownloadLink;
							DownloadTD.appendChild(DownloadLink);
						}
								
						
						//newTR.appendChild(DownloadTD);
					


						for (var j=0; j < pairs.length; j++) {
							if(pairs[j]!=""){
								var nameValue = pairs[j].split(nvDelimiter);
								var name = nameValue[0];
								var value = nameValue[1];
								//	have to find out if the element is text or select and choose the value or name
								var elementNode = getElementByIdOrByName(name);



								/////////////////////////////////for treatment
								if(name=="treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd" && value=="OTH"){
									treatmentOther=true;
								}
								/////////////////////////////////////////////////////


								if (elementNode != null)
								{


									//update history box with designated header fields


									if (getCorrectAttribute(elementNode,"header",elementNode.header))	{

										if (getCorrectAttribute(elementNode,"type",elementNode.type) == "select"){
											var temp = findNameForValue("list" + name, value);
											if(temp!=""){
												value=temp;
											}
										}






										if(previousHeader==getCorrectAttribute(elementNode,"header",elementNode.header) || elementNode.partner)	{
											var lastChild = newTR.lastChild;

										/////////////////////////////////for morb
											if(value=="")
												var nothing=null;

											//morb
											else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueTxtDT_s[0].valueTxt"){
												lastChild.innerHTML = lastChild.innerHTML +  "<br>" + value;
											}
											else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code"){

												newTD.innerHTML = value + "<br>";
											}
											else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd"){
												newTD.innerHTML = value + "<br>";
											}
											//treatment
											else if(name=="treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt" && treatmentOther){
												lastChild.innerHTML = value;
											}
											else if(name=="treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt" && !treatmentOther){
												lastChild.innerHTML = lastChild.innerHTML;
											}
											else
												lastChild.innerHTML = lastChild.innerHTML + value + "&nbsp;";

										////////////////////////////////////////////////////////////////

										} else {
											var newTD = document.createElement("td");
											/////////////////////////////////for morb
											if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName"){
												newTD.innerHTML = value + "<br>";
											}
											else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd"){
												newTD.innerHTML = value + "<br>";
											}
											else if(elementNode.id=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code"){
												newTD.innerHTML = value + "<br>";
											}
											////File download link
											else if(elementNode.id == "attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt"){
												newTD.innerHTML = DownloadTD.innerHTML;
											}
												
											////////////////////////////////////////////////////////////////
											else{
												var newText =document.createTextNode(value+ " ");
												newTD.appendChild(newText);
											}



											newTR.appendChild(newTD);

										}
										previousHeader = getCorrectAttribute(elementNode,"header",elementNode.header);
									}
								}
							}

						}

							historyBoxNode.appendChild(newTR);

					}
		}
}



//*********************		VALIDATION ON ELEMENT ADD
function validateBatchEntry(type, identifier) {
	var error = false;




		var NestedElementsTableNode = getElementByIdOrByName("nestedElementsTable|" + type);
		var inputNodes = NestedElementsTableNode.getElementsByTagName("input");
		var selectNodes = NestedElementsTableNode.getElementsByTagName("select");
		var textareaNodes = NestedElementsTableNode.getElementsByTagName("textarea");
		

		/* VL  Erase errors for all possible elements.  Errors that are displayed
		 *     in a separate line, <TR>, under the elements being validate.
		 */

		for(var i=0; i < inputNodes.length; i++) {
			if ( (inputNodes.item(i).type == "text") ||
			     (inputNodes.item(i).type == "checkbox")  ||
				 (inputNodes.item(i).type == "file")  ||
			    (inputNodes.item(i).type == "hidden")) {
				eraseErrorCellForElement(inputNodes.item(i));
			}
		}
		for(var i=0; i<selectNodes.length; i++ ) {
			eraseErrorCellForElement(selectNodes.item(i));
		}
		for(var i=0; i<textareaNodes.length; i++ ) {
			eraseErrorCellForElement(textareaNodes.item(i));
		}



		//check the function array for functions
		for (var i=0;i<batchEntryValidationArray.length;i++){
			if(batchEntryValidationArray[i]!=null){
				var temp = batchEntryValidationArray[i]();
				if(error!=true)
					error = temp;


			}
		}

		for(var i=0; i < inputNodes.length; i++)	{
			if ( (inputNodes.item(i).type == "text") ||
			     (inputNodes.item(i).type == "checkbox") ||
				 (inputNodes.item(i).type == "file") ||
			     (inputNodes.item(i).type == "hidden")) {
					
				if(((inputNodes.item(i).parent==type) || (getCorrectAttribute(inputNodes.item(i),"parent",inputNodes.item(i).parent)==type))  && validate(inputNodes.item(i)) == false) {
					//alert("error true 1");
					error = true;
				}
			}
		}


		for(var i=0; i < selectNodes.length; i++)	{

			
			if((getCorrectAttribute(selectNodes.item(i),"parent",selectNodes.item(i).parent)==type || getCorrectAttribute(selectNodes.item(i),"parent",selectNodes.item(i).parent)==type)  && (selectNodes.item(i).offsetWidth!=0 || (getElementByIdOrByName(selectNodes.item(i).name + "_textbox") != null && getElementByIdOrByName(selectNodes.item(i).name + "_textbox").offsetWidth!=0)) &&  validate(selectNodes.item(i)) == false) {
					error = true;

				errorNode = getTdErrorCell(selectNodes.item(i));
			}
		}

		for(var i=0; i < textareaNodes.length; i++)	{
			if((textareaNodes.item(i).parent==type || (selectNodes.item(i)!=null && getCorrectAttribute(selectNodes.item(i),"parent",selectNodes.item(i).parent)==type)) &&  validate(textareaNodes.item(i)) == false) {

				error = true;
			}
		}

		var errorTD = getElementByIdOrByName("nestedErrorMsg" + type);

		if (error == true){
			errorTD.setAttribute("className", "error");
			errorTD.setAttribute("class", "error");
		
		}else{
			errorTD.setAttribute("className", "none");
			errorTD.setAttribute("class", "none");
		}
		
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



	for (var i=0; i < inputNodes.length; i++) {
		if (inputNodes.item(i).type == "hidden") {
			var hiddenNode = inputNodes.item(i);
			//	! check for a specific pattern in the id of the hidden field, batch entry hidden field containing all the name value pairs
			var patternBatchEntry = new RegExp("nestedElementsHiddenField");
			if (patternBatchEntry.test(hiddenNode.id))	{
				// doesn't contain child delimiter
				if(hiddenNode.value.search(/\]\|/)==-1){
					singleBatchEntryConvertIntoStruts(hiddenNode.value);
				}else{
					nestedBatchEntryConvertIntoStruts(hiddenNode.value);
				}
			}//if
		}
	}
	//clear out the indexed elements because this causes struts to beanUtil error

	if(document.getElementById("nestedElementsTable|Attachment")!= null || document.getElementById("nestedElementsTable|Attachment")!= undefined){
	var file0 = document.getElementById("fileAttachment0");
	var file1 = document.getElementById("fileAttachment1");
	var file2 = document.getElementById("fileAttachment2");
	var file3 = document.getElementById("fileAttachment3");
	var file4 = document.getElementById("fileAttachment4");
	var file5 = document.getElementById("fileAttachment5");
	var file6 = document.getElementById("fileAttachment6");
	var file7 = document.getElementById("fileAttachment7");
	var file8 = document.getElementById("fileAttachment8");
	var file9 = document.getElementById("fileAttachment9");
	var file10 = document.getElementById("fileAttachment10");
	}
	
	
	
	var fieldsetNodes = document.getElementsByTagName("fieldset");
	for (var i=0; i < fieldsetNodes.length; i++) {
			if (fieldsetNodes.item(i).id == "batchEntryFieldset") {
				fieldsetNodes.item(i).innerText="";
				if(fieldsetNodes.item(i).textContent!=undefined)
					fieldsetNodes.item(i).textContent="";//error firefox
			}
	}

var a = 'file';
	
	
	
	for (var j=0; j < 11; j++) {
		var filenamestr = a+j;
		var filename = eval(filenamestr);
	if(filename!=null){
	
		filename.setAttribute("style","display:none");
		
		}
	}

	
	var element = findElementByAttributeValue("nid","Report Information");
	
	for (var j=0; j < 11; j++) {
		var filenamestr = a+j;
		var filename = eval(filenamestr);
	if(filename!=null ){
	
		
		element.appendChild(filename);
		}
	}
}

/**
 * findElementByAttributeValue: 
 * @param attribute
 * @param value
 * @returns
 */

function findElementByAttributeValue(attribute, value){
	
  var allElements = document.getElementsByTagName('*');
  
  for (var i = 0; i < allElements.length; i++)       {
    if (allElements[i].getAttribute(attribute) == value) {
		return allElements[i];
	}
  }
}


//*********************		THIS IS FOR STRUTS
/**
 * Description:need to create hidden elements with struts style naming convention for form to object translation
 *
 * param @type	name of of the nested type on the page
 * param @type	name of of the nested type on the page
 */
function singleBatchEntryConvertIntoStruts(data) {
	//need to reset the counter

	var formNode = getElementByIdOrByName("nedssForm");
					var elementCount=0;
					//	split up each entry
					var entries = data.split("|");
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
									//need to only create elements that exist stand alone outside the batch string
									var elementNode = getElementByIdOrByName(name);
									if(elementNode!=null){
										var strutsNode = document.createElement("input");
										strutsNode.setAttribute("type", "hidden");
										strutsNode.setAttribute("value", value);
										strutsNode.setAttribute("name", corrected);

										formNode.appendChild(strutsNode);
									}
								}
							}
							elementCount++;
						}//if
				}//for
}

function nestedBatchEntryConvertIntoStruts(data) {

	var parentCount=0;
	var childCount=0;
	var formNode = getElementByIdOrByName("nedssForm");

				var items = data.split("]|");
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


}



		function isNumeric1(str)
		// returns true if str is numeric
		// that is it contains only the digits 0-9
		// returns false otherwise
		// returns false if empty
		{

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

		
