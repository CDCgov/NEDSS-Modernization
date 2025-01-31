/**
 * getValueFromLabelQuestionIdentifier: this method returns the value from the element with identifier id
 * @param id
 * @returns {String}
 */

var multicheckboxes = false;
 
function getValueFromLabelQuestionIdentifier(id){
	
	var element1 = document.getElementById(id);
	var value="";
	
	if(element1!=null){
			if(element1.parentElement.nextElementSibling.nodeValue!=null && element1.parentElement.nextElementSibling.nodeValue.trim().length==0)
				value="";
			else{
				if(element1.parentElement.nextElementSibling.getElementsByTagName("span").length==0)
					element1=element1.parentElement.nextElementSibling.getElementsByTagName("div")[0];//race
				else
					element1=element1.parentElement.nextElementSibling.getElementsByTagName("span")[0];
				if(element1!=null && element1!=undefined)
					value=element1.textContent;
			
			}
	}
	return value;
	
}


function getValueFromLabelQuestionIdentifierMerge(id){
	
	var element1 = document.getElementById(id);
	var value="";
	multicheckboxes = false;
	
	if(element1!=null){
			if(element1.parentElement.nextElementSibling.nodeValue!=null && element1.parentElement.nextElementSibling.nodeValue.trim().length==0)
				value="";
			else{
				var nextSibling = element1.parentElement.nextElementSibling;
				//text and single select
				if(nextSibling.getElementsByTagName("input").length==1 && nextSibling.getElementsByTagName("input")[0].type=="text")
					value=nextSibling.getElementsByTagName("input")[0].value;
				else//text area
				if(nextSibling.getElementsByTagName("textarea").length==1)
					value=nextSibling.getElementsByTagName("textarea")[0].value;
				else//hidden
				if(nextSibling.getElementsByTagName("input").length==1 && nextSibling.getElementsByTagName("input")[0].type=="hidden")	
					value=nextSibling.textContent;

				else//multiselect
				if(nextSibling.getElementsByClassName("multiSelectBlock").length==1){
					value=nextSibling.getElementsByTagName("div")[0].getElementsByTagName("div")[0].textContent;
				}
				else{//checkboxes
				if(nextSibling.getElementsByTagName("input").length==1 && nextSibling.getElementsByTagName("input")[0].type=="checkbox"){
					//multi checkboxes
					var multipleCheckboxes=true;
					multicheckboxes=true;
					
						value=value+nextSibling.getElementsByTagName("input")[0].checked;
						if(!(element1.parentElement.parentElement.nextElementSibling==null || element1.parentElement.parentElement.nextElementSibling==undefined || element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[0].textContent.trim()!="")){
							//value=value+nextSibling.getElementsByTagName("input")[0].checked;
							element1=element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[0];
							nextSibling = element1.nextElementSibling;
						}
						else
						{
							multipleCheckboxes=false;
							
						}
						while(multipleCheckboxes){
							value=value+" "+nextSibling.getElementsByTagName("input")[0].checked;
						if(element1.parentElement.nextElementSibling==null || element1.parentElement.nextElementSibling==undefined || element1.parentElement.nextElementSibling.getElementsByTagName("td")[0].textContent.trim()!=""){
							multipleCheckboxes=false;
							
						}
						else{
							element1=element1.parentElement.nextElementSibling.getElementsByTagName("td")[0];
							nextSibling = element1.nextElementSibling;
						}
					}
				}
				else{//Participation lookup
					var spans = nextSibling.getElementsByTagName("span");
					
						if(spans!=null && spans[0]!=null && spans[0]!=undefined && spans[0].id.indexOf("clear")!=-1)
							value="QuickCode";
						else{
							//span
							if(nextSibling.getElementsByTagName("span")!=null && nextSibling.getElementsByTagName("span")!=undefined && nextSibling.getElementsByTagName("span").length==1)
								value=nextSibling.getElementsByTagName("span")[0].textContent;
						}
				}
				
				
				}
			}
	}	
	return value;
	
}

/**
   * showAllShowDiffOnly: this method hides the questions which values are the same in both investigations
						if showHide = false, and show all the records if showHide = true
						compare=false, Merge = true
   */
  
  var arrayHidden = new Array();
	  
		function showAllShowDiffOnly(showHide, compareMerge){
			
			var array1 = getArrayInv();
			
			
			if(showHide){
				for(var i=0; i<arrayHidden.length;i++){
					arrayHidden[i].setAttribute("style","display:");
					
				}
				}		
			else{
				for(var i=0; i<array1.length;i++){
				
					var id = array1[i];
					var id2 = id+"_2";
					id2=id2.replace("L_2","_2L");
					var element1 = document.getElementById(id);
					var element2 = document.getElementById(id2);
					
					
						
					var elementQuickCode1="";
					var elementQuickCode2="";					
					var errorQuickCode1="";
					var errorQuickCode2="";
					
					var emptyNode=false;
					var value1, value2;
					
					if(!showHide){
						
						if(!compareMerge){//compare
							value1 = getValueFromLabelQuestionIdentifier(id);
							value2 = getValueFromLabelQuestionIdentifier(id2);
						}else{
							value1 = getValueFromLabelQuestionIdentifierMerge(id);
							value2 = getValueFromLabelQuestionIdentifierMerge(id2);
						}
						
						if(value1=="QuickCode"){
							var idQuickCode1 = id2.replace("_2L","");
							var idQuickCode2 = id2.replace("_2L","_2");
							value1=getElementByIdOrByName(idQuickCode1).textContent;
							if(getElementByIdOrByName(idQuickCode2)!=null && getElementByIdOrByName(idQuickCode2)!=undefined)
								value2=getElementByIdOrByName(idQuickCode2).textContent;
							
							elementQuickCode1 = document.getElementById(idQuickCode1+"S");
							elementQuickCode2 = document.getElementById(idQuickCode2+"S");
							errorQuickCode1 = document.getElementById(idQuickCode1+"Error");
							errorQuickCode2 = document.getElementById(idQuickCode2+"Error");
							
					
						}
							
						
						
						if(multicheckboxes ||(value1==value2) || (value1.trim()==value2.trim())){
							
								if(element1!=null && (element1.parentElement.parentElement.getAttribute("style")==null || element1.parentElement.parentElement.getAttribute("style").indexOf("none")==-1)){
									
									if(!multicheckboxes){
										element1.parentElement.parentElement.setAttribute("style","display:none");
										arrayHidden.push(element1.parentElement.parentElement);
									}else{//hide all checkboxes
										arrayHidden=hideMulticheckboxes(element1,arrayHidden, value1, value2);
									}
								
								if(!isNullOrUndefined(elementQuickCode1.parentElement)){
									elementQuickCode1.parentElement.setAttribute("style","display:none");
									arrayHidden.push(elementQuickCode1.parentElement);
									
								}
								
								if(!isNullOrUndefined(errorQuickCode1) && !isNullOrUndefined(errorQuickCode1.parentElement) && !isNullOrUndefined(errorQuickCode1.parentElement.parentElement)){
										errorQuickCode1.parentElement.parentElement.setAttribute("style","display:none");
										arrayHidden.push(errorQuickCode1.parentElement.parentElement);
									}
									
								}
								if(element2!=null && (element2.parentElement.parentElement.getAttribute("style")==null || element2.parentElement.parentElement.getAttribute("style").indexOf("none")==-1)){
									if(!multicheckboxes){
										element2.parentElement.parentElement.setAttribute("style","display:none");
										arrayHidden.push(element2.parentElement.parentElement);
									}else{//hide all checkboxes
										arrayHidden=hideMulticheckboxes(element2, arrayHidden, value1, value2);
									}
									if(!isNullOrUndefined(elementQuickCode2.parentElement)){
										elementQuickCode2.parentElement.setAttribute("style","display:none");
										arrayHidden.push(elementQuickCode2.parentElement);
										}
									if(!isNullOrUndefined(errorQuickCode2) && !isNullOrUndefined(errorQuickCode2.parentElement) &&!isNullOrUndefined(errorQuickCode2.parentElement.parentElement)){
										errorQuickCode2.parentElement.parentElement.setAttribute("style","display:none");
										arrayHidden.push(errorQuickCode2.parentElement.parentElement);
									}
									
									
									
								}
			
							}
							
					}

				}
			}
			hideShowBatchEntry(showHide);
			hideShowstaticCommentsLineSeparator(showHide);
			showHideSubSection();
			showHideSection();
			showstaticCommentsLineSeparator();
			showAllShowDiffOnlyCSS();
		}
		
		function hideMulticheckboxes(element1, arrayHidden, value1, value2){
			
			var row = element1.parentElement.parentElement;
			var continueVar = true;

			var value1Array = value1.split(" ");
			var value2Array = value2.split(" ");
			var index=0;
			
			do{
				if(value1Array[index]==value2Array[index]){
					row.setAttribute("style","display:none");
					arrayHidden.push(row);
				}
				row = row.nextElementSibling;
				if((row==null || row==undefined) || !(row.getElementsByClassName("fieldName").length>0 && row.getElementsByClassName("fieldName")[0].textContent.trim()==""))
					continueVar = false;
				
				index++;
			}
			while(continueVar);
				
		return arrayHidden;				
										
		}
		
		function isNullOrUndefined(element1){
			
			var isNull=false;
				
			if(element1==null || element1==undefined)
				isNull=true;
				
			return isNull;
		}
	/**
	 * getArrayInv: this method returns an array with all the ids from the left investigation
	 * @returns {Array}
	 */
		function getArrayInv(){
			
			var fieldLabels = document.getElementsByClassName("fieldName");
			var arrayInv = new Array();
			for(var i = 0; i< fieldLabels.length; i++){
			
				var spans = fieldLabels[i].getElementsByTagName("span");
				
				if(spans!=null && spans!=undefined && spans.length>0){
					var length = spans.length;
					var span = spans[length-1];
					var idLabel = span.id;
					//var id = idLabel.substring(0,idLabel.length-1);//remove the L
					var id = idLabel;
					var idTable = "table"+id.substring(0,id.length-1);
					if(document.getElementById(idTable)==null || document.getElementById(idTable)==undefined)//Part of batch entry
						if(document.getElementById(id)!=null && (id.endsWith("_2L")==false || (document.getElementById(id.replace("_2L","_2_2L"))!=null && document.getElementById(id.replace("_2L","_2_2L"))!=undefined))){
							arrayInv.push(id);

						}
				}
			}
			
			return arrayInv;
		}
		
	
	/**
	 * showHideSubSection(): if all questions are hidden, then hide the subsection, otherwise, show the subsection
	 */

	 var subsectionsArray = new Array();
	function showHideSubSection(){
		var subsections = document.getElementsByClassName("subSect");
		
		
		for(var i=0;i<subsections.length;i++){
			var show = false;
			var rows = subsections[i].getElementsByTagName("tbody")[0].getElementsByTagName("tr");
			
			for(var j = 0; j<rows.length && !show; j++){
			
				var style = rows[j].getAttribute("style");
				
				if(style==null || style==undefined || style.indexOf("none")==-1){
					//if it is not the span for error from quick code
					//if(!(rows[j].getElementsByTagName("span").length>0 && rows[j].getElementsByTagName("span")[0].id.indexOf("Error")!=-1))
						show=true;//there's at least one visible field.
				}
			}
			
			if(!show){//if it is not visible
				if(subsections[i].getAttribute("style")==null || subsections[i].getAttribute("style").indexOf("none")==-1){
					subsectionsArray.push(subsections[i]);
					subsections[i].setAttribute("style","display:none");
					
				
				}
				
			}
			else{
				if(subsectionsArray.indexOf(subsections[i])!=-1)//if it has been hidden previously
					subsections[i].setAttribute("style","display:");
				
			}
		}
	
	}

	/**
	 * showHideSection(): if all subsections are hidden, hide the section and the link, otherwise, show the section and the link.
	 */

	function showHideSection(){

	var sections = document.getElementsByClassName("sect");
	
	for(var i=0;i<sections.length;i++){
		var show = false;
		var subsects = sections[i].getElementsByClassName("subSect");
		
		for(var j = 0; j<subsects.length && !show; j++){
		
			var style = subsects[j].getAttribute("style");
			
			if(style==null || style==undefined || style.indexOf("none")==-1)
				show=true;//there's at least one visible field.
		}
		
		if(!show){
			sections[i].setAttribute("style","display:none");
			
			var idLink = sections[i].id;
			showHideLink(idLink, false);
		}
		else{
			sections[i].setAttribute("style","display:");
			var idLink = sections[i].id;
			showHideLink(idLink, true);
		}
	}

	}

	/**
	 * showHideLink(): show/hide the link if the section is shown/hidden respectively
	 * @param idLink
	 * @param show
	 */
	

	function showHideLink (idLink,show){
		
		if(document.getElementsByClassName("horizontalList")!=null && document.getElementsByClassName("horizontalList")!=undefined &&
		document.getElementsByClassName("horizontalList")[0]!=null && document.getElementsByClassName("horizontalList")[0]!=undefined){
				
			var links = document.getElementsByClassName("horizontalList")[0].getElementsByTagName("a");
			var delimiters = document.getElementsByClassName("horizontalList")[0].getElementsByClassName("delimiter");
			var found = false;
			
			for(var i=0; i< links.length && !found; i++){
				
				var content = links[i].textContent;
	
				content=content.split(' ').join('');
	
				if(content==idLink){
					if(!show){
						links[i].setAttribute("style","display:none");
						if(links.length-1==i)//In case it's the last link
							i--;
						if(delimiters[i]!=null && delimiters[i]!=undefined)
							delimiters[i].setAttribute("style","display:none");
					}else{
						links[i].setAttribute("style","display:");
						if(delimiters[i]!=null && delimiters[i]!=undefined)
							delimiters[i].setAttribute("style","display:");
					
					
					}
					found=true;
				}	
			}
		}
	}
	
	/**
	 * validateToMerge(): validates whether surviving record is selected or not
	 */
	
	
	function validateToMerge(){
		if(getElementByIdOrByName("survivingRecord").checked && getElementByIdOrByName("survivingRecord2").checked ){
			getElementByIdOrByName('errorBlock').setAttribute("style", "display:true;width:99%");
			getElementByIdOrByName('errorBlock').innerText = "You must select only one Surviving Record before merging investigations.";
			 getElementByIdOrByName("pageTop").scrollIntoView();
			return false;
		}
		else if(!getElementByIdOrByName("survivingRecord").checked && !getElementByIdOrByName("survivingRecord2").checked ){
			getElementByIdOrByName('errorBlock').setAttribute("style", "display:true;width:99%");
			getElementByIdOrByName('errorBlock').innerText = "You must select a Surviving Record before merging investigations.";
			 getElementByIdOrByName("pageTop").scrollIntoView();
			return false;
		}
		else if(getElementByIdOrByName("survivingRecord").checked || getElementByIdOrByName("survivingRecord2").checked ){
			return true;
		}
	}
	
	/**
	 * hideCollapseSectionLinksImages(): hide the collapse section links and images
	 */
	
	
	function hideCollapseSectionLinksImages(){
		//Expand logic if necessary 
		$j(".toggleIconHref").hide();
		$j(".toggleHref").hide();
	}
	
	var arrayBatch = new Array();

	function hideShowBatchEntry(showHide){

		if(!showHide){//Hide
			var batchEntries = document.getElementsByClassName("dtTable");

			for(var i=0; i< batchEntries.length; i++){
			
				var id = batchEntries[i].getElementsByTagName("tbody")[0].id.replace("questionbody","");
			if(!id.endsWith("_2") || (document.getElementById(id+"_2")!=null && (document.getElementById(id+"_2")!=undefined))) {
					var batchVisible1 = document.getElementById("nopattern"+id).getAttribute("style").indexOf("none")==-1;
					var batchVisible2 = document.getElementById("nopattern"+id+"_2").getAttribute("style").indexOf("none")==-1;
						
						var batchTR =  batchEntries[i].getElementsByTagName("tbody")[0].getElementsByTagName("tr");
						var batchTR_2id = batchEntries[i].getElementsByTagName("tbody")[0].id + "_2";
						var batchTR_2 = getElementByIdOrByName(batchTR_2id).getElementsByTagName("tr");
						
						var arrayBatchTR = new Array();;
						var arrayBatchTR_2 = new Array();;
						if(!isNullOrUndefined(batchTR) && batchTR.length > 1){
							for(var j =0; j<batchTR.length-1; j++){
								arrayBatchTR.push(batchTR[j].innerText.trim().toUpperCase());
							}
						}
						if(!isNullOrUndefined(batchTR_2) && batchTR_2.length > 1 ){
							for(var j =0; j<batchTR_2.length-1; j++){
								arrayBatchTR_2.push(batchTR_2[j].innerText.trim().toUpperCase());
							}
						}
						arrayBatchTR.sort();
						arrayBatchTR_2.sort();
					if(arraysEqual(arrayBatchTR, arrayBatchTR_2)){//check if both arrays have different values
					
						document.getElementById(id).setAttribute("style","display:none");
						document.getElementById(id+"_2").setAttribute("style","display:none");
						arrayBatch.push(id);
					}
					
					else {
						
						document.getElementById(id).setAttribute("style","display:");
						document.getElementById(id+"_2").setAttribute("style","display:");
						
					}
					
					
					
				}
			}
		}
		else{//Show
			for(var i=0; i<arrayBatch.length; i++){
				var id = arrayBatch[i];
				document.getElementById(id).setAttribute("style","display:");
				document.getElementById(id+"_2").setAttribute("style","display:");
				
			
				
			}
		}

	}
	
	/**
	 * hideShowstaticCommentsLineSeparator(showHide): hide or show static comment 
	 */
	
	function hideShowstaticCommentsLineSeparator(showHide){
		var staticComments = document.getElementsByClassName("staticComment");
		var lineSeparators = document.getElementsByTagName("hr");
		if(!showHide){//Hide
			for(var i=0; i< staticComments.length; i++){ 
				
				if (staticComments[i].getAttribute("style")==null||staticComments[i].getAttribute("style").indexOf("none")==-1){ 
					
						staticComments[i].parentElement.parentElement.setAttribute("style","display:none");
				}
					
			}
			for(var i=0; i< lineSeparators.length; i++){  
				if (lineSeparators[i].getAttribute("style")==null||lineSeparators[i].getAttribute("style").indexOf("none")==-1){ 
					lineSeparators[i].parentElement.parentElement.setAttribute("style","display:none");
				}
					
			}
			
		}
		
		else{//Show
			for(var i=0; i<staticComments.length; i++){
				staticComments[i].parentElement.parentElement.setAttribute("style","display:");
			}
			for(var i=0; i<lineSeparators.length; i++){
				lineSeparators[i].parentElement.parentElement.setAttribute("style","display:");
			}
		}

	}

	/**
	 * showstaticCommentsLineSeparator(): show static comment when their respective subsection is visible 
	 */
	
	function showstaticCommentsLineSeparator(){
		var staticComments = document.getElementsByClassName("staticComment");
		
		
			for(var i=0; i< staticComments.length; i++){ 
				
				if (!isNullOrUndefined(staticComments[i].parentElement.parentElement.parentElement.parentElement) && (staticComments[i].getAttribute("style")==null||staticComments[i].getAttribute("style").indexOf("none")==-1)){ 
					
						staticComments[i].parentElement.parentElement.setAttribute("style","display:");
				}
					
			}
	}
	
	function hideSurvivingRecordBox(){
		var labels = document.getElementsByTagName('label'), l = labels.length, label, i;
		for (i = 0; i < l; i++) {
		    label = labels[i];
		    if (label.htmlFor == "survivingRecord2") {
		        label.style.display = 'none'; // 'none' for hideEl, '' for showEl
		    }
		}
		
		getElementByIdOrByName("survivingRecord2").hide();
		getElementByIdOrByName("survivingRecordL2").hide();
		getElementByIdOrByName("survivingRecord").checked = true;
		getElementByIdOrByName("survivingRecord").disabled= true;
	}
	
	function disableSectionsAndSubSections(){

		var sections = document.getElementsByClassName("sect");
		
		for(var i=0;i<sections.length;i++){
				
				var id = sections[i].getAttribute("id");
				var imgs = sections[i].getElementsByTagName("img");
				var multiselects = sections[i].getElementsByClassName("multiSelectBlock");
				var textareas = sections[i].getElementsByTagName("textarea");
				if( id!=undefined && id.endsWith("_2"))
				{
					sections[i].disabled = true;
					sections[i].setAttribute("readonly", "readonly"); //to remove disabled from any element use removeAttribute("disabled") 
					var inputs = sections[i].getElementsByTagName("input");
				
					for(var j = 0; j< inputs.length;j++){
							inputs[j].setAttribute("style", "background-color:white; color:black;border:currentColor;");
							
							if(inputs[j].type == "button")
								inputs[j].setAttribute("style", "display:none;");
							
							//to resolve issue with firefox
							if(inputs[j].getAttribute("type") == "checkbox")
								inputs[j].setAttribute("disabled", "disabled");
							else
								inputs[j].setAttribute("readonly", "readonly");
					}
				
					for(var j = 0; j< imgs.length;j++){
						var imgid = imgs[j].getAttribute("id");
						var imgname = imgs[j].getAttribute("name");
						if((imgid!=undefined && imgid.endsWith("_2") ) || (imgname!=undefined && imgname.endsWith("_button"))){
							imgs[j].disabled = true;
							imgs[j].style.display = "none";
						}
					}
					
					//For firefox diabled multiselect boxes
					for(var j = 0; j< multiselects.length;j++){
						multiselects[j].getElementsByTagName("select")[0].setAttribute("disabled", "disabled"); //to resolve issue with firefox
						multiselects[j].getElementsByTagName("select")[0].setAttribute("style", "background-color:white; color:black;border:currentColor;");
					}
			
					for(var j = 0; j< textareas.length;j++){
						textareas[j].setAttribute("readonly", "readonly"); //to resolve issue with firefox
					}
				}
		}
		
		
	}
	
	function showAllShowDiffOnlyCSS(){
		
		var array1 = getArrayInv();
		
		for(var i=0; i<array1.length;i++){
				var id = array1[i];
				var id2 = id+"_2";
				id2=id2.replace("L_2","_2L");
				var element1 = document.getElementById(id);
				var element2 = document.getElementById(id2);
				
				var emptyNode=false;
				var value1, value2;
						value1 = getValueFromLabelQuestionIdentifierMerge(id);
						value2 = getValueFromLabelQuestionIdentifierMerge(id2);
						
						value3 = getvaluesfromParticipationQuestions(id);
						value4 = getvaluesfromParticipationQuestions(id2);
					if((value1!=value2) || (value1.trim()!=value2.trim()) ){
							if(element1!=null && (element1.parentElement.parentElement.getAttribute("style")==null || element1.parentElement.parentElement.getAttribute("style").indexOf("none")==-1)){
								element1.parentElement.nextElementSibling.setAttribute("style","background-color:#FDEBDD");
								handleMultiselectcheckboxes(id);
							}
					}else{
						element1.parentElement.nextElementSibling.setAttribute("style","background-color:#FFFFFF");
					}
					
					if((value3!=value4) || (value3.trim()!=value4.trim()) ){
						if(element1!=null && (element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[1].getAttribute("style")==null || element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[1].getAttribute("style").indexOf("none")==-1)){
							element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[1].setAttribute("style","background-color:#FDEBDD");
						}
					}
					
					
		}
		highlightRaceOnCompare();
		highlightBatchEntrySection();
		if(document.getElementsByClassName("sect")[0].getElementsByTagName("input").length!=0 && document.getElementsByClassName("sect")[0].getElementsByTagName("input")[0]!=undefined)
			document.getElementsByClassName("sect")[0].getElementsByTagName("input")[0].focus();
	}
	function getvaluesfromParticipationQuestions(id){
		var element1 = document.getElementById(id);
		var value="";
		
		if(element1!=null){
				if(element1.parentElement.nextElementSibling!= null && element1.parentElement.nextElementSibling.getElementsByTagName("span")[0]!= undefined &&
						element1.parentElement.nextElementSibling.getElementsByTagName("span")[0].getElementsByTagName("input")[0] != undefined
						&& (element1.parentElement.nextElementSibling.getElementsByTagName("span")[0].getElementsByTagName("input")[0].id.endsWith("CodeClearButton") || element1.parentElement.nextElementSibling.getElementsByTagName("span")[0].getElementsByTagName("input")[0].id.endsWith("CodeClearButton_2"))){
					value = element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[1].innerText;
					element1.parentElement.parentElement.nextElementSibling.getElementsByTagName("td")[1].setAttribute("style","background-color:#FFFFFF");
				}
				else{
					value = "";
				}
					
		}
		return value;
	}
	
	function handleMultiselectcheckboxes(id){
		var id = id.substring(0, id.length - 1);
		var checkboxid = "#"+id+"";
		var checkboxid2 = "#"+id+"_2";
		var valueCheckbox ="";
		var i =0;
		var nextcheckbox = true;
		while(nextcheckbox){
			var element = $j(checkboxid+"_"+i);
			var element2 = $j(checkboxid2+"_"+i);
			if(element.attr("type") == "checkbox"){
				
				valueCheckbox1 = element.attr("checked");
				valueCheckbox2 = element2.attr("checked");
				if(valueCheckbox1!= valueCheckbox2){
					$j(element.parent()).attr("style","background-color:#FDEBDD");
				}
				else
					$j(element.parent()).attr("style","background-color:#FFFFFF");
			
			}
			else{
				nextcheckbox = false;
			}
		i++;
		}
		
	}
	
	function highlightBatchEntrySection(){
		var batchentriesSubsections = document.getElementsByClassName("batchSubSection");
		
			var batchEntries = document.getElementsByClassName("dtTable");

			for(var i=0; i< batchEntries.length; i++){
			
				var id = batchEntries[i].getElementsByTagName("tbody")[0].id.replace("questionbody","");
			if(!id.endsWith("_2") || (document.getElementById(id+"_2")!=null && (document.getElementById(id+"_2")!=undefined))) {
				//	var batchVisible1 = document.getElementById("nopattern"+id).getAttribute("style").indexOf("none")==-1;
					//var batchVisible2 = document.getElementById("nopattern"+id+"_2").getAttribute("style").indexOf("none")==-1;
					var tds = document.getElementById(id).getElementsByTagName("td");
			
						var batchTR =  batchEntries[i].getElementsByTagName("tbody")[0].getElementsByTagName("tr");
						var batchTR_2id = batchEntries[i].getElementsByTagName("tbody")[0].id + "_2";
						var batchTR_2 = getElementByIdOrByName(batchTR_2id).getElementsByTagName("tr");
						
						var arrayBatchTR = new Array();;
						var arrayBatchTR_2 = new Array();;
						if(!isNullOrUndefined(batchTR) && batchTR.length > 1){
							for(var j =0; j<batchTR.length-1; j++){
								arrayBatchTR.push(batchTR[j].innerText.trim().toUpperCase());
							}
						}
						if(!isNullOrUndefined(batchTR_2) && batchTR_2.length > 1 ){
							for(var j =0; j<batchTR_2.length-1; j++){
								arrayBatchTR_2.push(batchTR_2[j].innerText.trim().toUpperCase());
							}
						}
						arrayBatchTR.sort();
						arrayBatchTR_2.sort();
					if(arraysEqual(arrayBatchTR, arrayBatchTR_2)){//check if both array has different values
						for(var j=0; j< tds.length; j++){
							tds[j].setAttribute("style","background-color:#FFFFFF");
						}
					}
					else {
						for(var j=0; j< tds.length; j++){
							tds[j].setAttribute("style","background-color:#FDEBDD");
						}
					}
					
					
				}
			
			}
		
		
	}
	
	function arraysEqual(arr1, arr2) {
	    if(arr1.length !== arr2.length)
	        return false;
	    for(var i = arr1.length; i--;) {
	        if(arr1[i] !== arr2[i])
	            return false;
	    }

	    return true;
	}
	
	
	function highlightRaceOnCompare(){
		if(!isNullOrUndefined(getElementByIdOrByName("patientRacesViewContainer"))){
			var race1 = getElementByIdOrByName("patientRacesViewContainer").innerText.trim();	
			var race2 = getElementByIdOrByName("patientRacesViewContainer2").innerText.trim();
			
			if(race1 == race2){
				getElementByIdOrByName("patientRacesViewContainer").parentElement.setAttribute("style","background-color:#FFFFFF");
			}
			else
				getElementByIdOrByName("patientRacesViewContainer").parentElement.setAttribute("style","background-color:#FDEBDD");
		}
	}
	
	/**
	* getFirstElement: returns 1 if the section is first, return 2 is the subsection is first
	*/
	
	function getFirstElement(top1Section, top1SubSection){
		
		var first = "1";//section
		
		if(top1SubSection < top1Section)
			first="2";//subsection
	
		return first;
		
	}
	
	/**
	 * alignElement: this method align element1 to element2. If it is a section, sectionSubsection="1", if it is a subsection, sectionSubsection="2"
	 * @param element1
	 * @param element2
	 * @param top1
	 * @param top2
	 */
	
		function alignElement(element1, element2, top1, top2, sectionSubsection){
		
		var top3Section=0;
		if(element2!=undefined && element2!=null && top1!=0 && top2!=0){
			if(top1>top2){
				var top3 = top1-top2;
				if(sectionSubsection=="1")//section
					top3+=12;
				else
					top3+=7;
				
				var marginTop = element2.style.marginTop;
				
				if(marginTop==null || marginTop=='undefined')
					marginTop=0;
				top3=marginTop+top3;
				element2.setAttribute("Style","margin-top:"+top3);
			}
			if(top2>top1){
				var top3 = top2-top1
				if(sectionSubsection=="1")//section
					top3+=12;
				else
					top3+=7;
				
				var marginTop = element1.style.marginTop;
				
				if(marginTop==null || marginTop=='undefined')
					marginTop=0;
				top3=marginTop+top3;
				
				element1.setAttribute("Style","margin-top:"+top3);
			}
		}
	}
	
	/**
	 * alignSectionsAndSubsections: this method aligns investigations sections and subsections on both sides of compare and merge screens.
	 */
	
	function alignSectionsAndSubsections(){
		
		
		var sections = document.getElementsByClassName("sect");
		var subsections = document.getElementsByClassName("subSect");
			
			
	
		var j=0;
		var indexSubsection =0;	
		var top1Section;
		var top2Section
		
		
		
		
			for(var i=0;i<sections.length;i++){
	
					var id = sections[i].id;

					var elementSection1 = document.getElementById(id);
					var elementSection2 = document.getElementById(id+"_2");

					if(elementSection2!=undefined && elementSection2 !=null){
						top1Section = elementSection1.getBoundingClientRect().top;
						top2Section = elementSection2.getBoundingClientRect().top;
					}
					if(top1Section!=top2Section)
						alignElement(elementSection1, elementSection2, top1Section, top2Section,"1");
					
				continueSubsection = true;
					
					var id1, elementSection1Next, top1SectionNext=null;
					if(i+1<sections.length){
						id1 = sections[i+1].id;
						elementSection1Next = document.getElementById(id1);
						top1SectionNext= elementSection1Next.getBoundingClientRect().top;
					}
					for(j=indexSubsection; j<subsections.length && continueSubsection; j++){
						var top1SubSection;
						var top2SubSection;
						
						var id = subsections[j].id;

						var elementSubSection1 = document.getElementById(id);
						var elementSubSection2 = document.getElementById(id+"_2");

						if(elementSubSection2!=undefined && elementSubSection2 !=null){
							top1SubSection = elementSubSection1.getBoundingClientRect().top;
							top2SubSection = elementSubSection2.getBoundingClientRect().top;
						}
						
						
						if(top1SectionNext!=null&&getFirstElement(top1SectionNext, top1SubSection)=="1"){//The section is firstChild
							continueSubsection = false;
						}if(getFirstElement(top1SectionNext, top1SubSection)=="2"){//The subsection is firstChild
							if(top1SubSection!=top2SubSection)
								alignElement(elementSubSection1, elementSubSection2, top1SubSection, top2SubSection,"2");
						}
					}
					indexSubsection = j;
			}

		}