
function setFocusOnNameField(){

document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt").focus();

}

function setEnctype(){
	document.forms[0].setAttribute("enctype","multipart/form-data");
}

function getProgramAreaFromCondition(selectNode)
{
	var selectvalue = selectNode.options[selectNode.selectedIndex].value;
	var programAreaDescNode = getElementByIdOrByName("programAreaDesc");
	var programAreaCdNode = getElementByIdOrByName("morbidityReport.theObservationDT.progAreaCd");
	var conditionCodeHiddenNode = getElementByIdOrByName("morbidityReport.theObservationDT.cd");

	if(selectvalue != "")
	{
			var array = selectvalue.split("^");

			var sProgramAreaCd = array[0];
			var sProgramAreaDesc = array[1];
			var sConditionCode = array[2];

			 if(programAreaDescNode != null)
				setText(programAreaDescNode,sProgramAreaDesc);

		     if(programAreaCdNode != null)
			programAreaCdNode.value = sProgramAreaCd;

			 if(conditionCodeHiddenNode != null)
			conditionCodeHiddenNode.value = sConditionCode;
	}
	else
	{
			 if(programAreaDescNode != null)
				setText(programAreaDescNode,"");

		     if(programAreaCdNode != null)
			programAreaCdNode.value = "";

			 if(conditionCodeHiddenNode != null)
			conditionCodeHiddenNode.value = "";
	}

}


function getCondition()
{
    //alert(" in getCondtion");
     var conditionNode = getElementByIdOrByName("morbidityReport.theObservationDT.cd");

	var conditionCd = conditionNode.value;
	var items;

	//alert("conditionCd " + conditionCd );

	var DSConditionCdDescString= getElementByIdOrByName("DSConditionCdDescString");
	//alert("DSConditionCdDescString" + DSConditionCdDescString);

	 if(DSConditionCdDescString!= null)
	items = DSConditionCdDescString.value.split("|");

	if(items !=null && items.length > 0 && conditionCd != null && conditionCd.length > 0 )
	{
		for (var i=0; items.length > i; i++)
		{
			if (items[i]!=""  && items[i].indexOf(conditionCd) > 0)
			{
			 //alert("  conditionNode .value" +   conditionNode.value);
			  var cd =  items[i].split("$");
			  conditionNode.value =cd[0];
			  // alert("  conditionNode.value is item " +  cd[0]);
			  break;
			}
		}
	}

}


function morbJurisdictionCheck()
{

	//no error, return false
	return false;

}


function resultedTest(resultedTestName){


	var testNode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt");
	var codedNode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");

	var trNode = codedNode.parentNode;
	while(trNode.nodeName!="TR")
	{
			trNode = trNode.parentNode;
	}
	if(testNode.value=="059543"){
		//hide the entire tr
		setAttributeClass(trNode, "none");
	}else{
		//show the entire tr
		setAttributeClass(trNode, "visible");
	}

}

function submitAndCreateInvestigation()
{
     var ContextAction= getElementByIdOrByName("ContextAction");
      ContextAction.value = "SubmitAndCreateInvestigation";
      submitForm();
}

function submitMorb()
{
     var ContextAction= getElementByIdOrByName("ContextAction").value;
     if(ContextAction=="SubmitAndCreateInvestigation"){
    	 getElementByIdOrByName("ContextAction").value = "Submit";
     }
      submitForm();
}

function submitMorbAndCreateInv(currTask){
	getPage('/nbs/'+currTask+'.do?ContextAction=CreateInvestigationFromMorbidity');
}

function testMorbResultCodedCheck(){
	
	
	var labelList = new Array();
	
	var argumentsCaller;
	
	if(arguments.caller==undefined)
		argumentsCaller=testMorbResultCodedCheck.caller.arguments[0];
	else
		argumentsCaller=arguments.caller[0];
	
	
	
	if(argumentsCaller=="Lab Report"){
		var errorLabel=null;
		var codedNode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code_textbox");
		
		if(codedNode!=null && codedNode.offsetWidth!=0){
			if(codedNode.value!="")
				return false;
			
			var obsValueCodeVar = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");
			errorLabel = getCorrectAttribute(obsValueCodeVar,"fieldLabel",obsValueCodeVar.fieldLabel);
			
			
		}
		var orgNode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName_textbox");
		if(orgNode!=null && orgNode.offsetWidth!=0){
			if(orgNode.value!="")
				return false;
			var displayNameVar=getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName");
			
			errorLabel = getCorrectAttribute(displayNameVar,"fieldLabel",displayNameVar.fieldLabel);
		}
		var orgHiddenNode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd");
		if(getElementByIdOrByName("labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1").value=="Y"){
			if(orgHiddenNode.value!="")
				return false;
			
			var displayName2var = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName");
			errorLabel = getCorrectAttribute(displayName2var,"fieldLabel",displayName2var.fieldLabel);	
		}
		
			var NRVnode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue");

            if(NRVnode.value!="")
				return false;

			var TRVnode = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueTxtDT_s[0].valueTxt");
			if(TRVnode.value!="")
				return false;



			var tdErrorCell = getTdErrorCell(codedNode) ;
			
			if( tdErrorCell.innerText == "" )
				setText(tdErrorCell,makeErrorMsg('ERR113',labelList.concat(errorLabel.concat(", ")).concat(getCorrectAttribute(NRVnode, "fieldLabel",NRVnode.fieldLabel).concat(", ")).concat(getCorrectAttribute(TRVnode, "fieldLabel",TRVnode.fieldLabel))));
			else
				setText(tdErrorCell,tdErrorCell.innerText + "\n" + errorText);
			setAttributeClass(tdErrorCell, "error");
			return true;
		
	}
		return false;
}

function customTreatmentRequired()
{
	
	
	var argumentsCaller;
	
	if(arguments.caller==undefined)
		argumentsCaller=customTreatmentRequired.caller.arguments[0];
	else
		argumentsCaller=arguments.caller[0];
		
	if(argumentsCaller=="Treatment"){
	
		var controllerNode = getElementByIdOrByName("treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt");
		
		var errorText = "";
		if(controllerNode != null)
		{
			if(controllerNode.offsetWidth!=0 && controllerNode.value==""){

				var labelList = new Array();
				var tdErrorCell = getTdErrorCell( controllerNode) ;
				labelList[0] = "Custom Treatment";
				errorText = makeErrorMsg('ERR023', labelList);
				setText(tdErrorCell,errorText);
				tdErrorCell.setAttribute("className", "error");
				return true;
			}
		}
	}
	else
		return false;
	
}

function hideObjectsOnMorbidityLoad(){
	var organismIndicator = getElementByIdOrByName("organismIndicator");
	if(organismIndicator!=null)	
		setAttributeClass(organismIndicator, "none");
}

function confirmDelete(){
	return confirm("You have indicated that you would like to delete this attachment. By doing so, this file will no longer be available in the system. Would you like to continue with this action?");
}

function handleNewFileSelection() {

	
		  var inputVisibleIndex=getFileInputVisible();
			var inputVisible="fileAttachment"+inputVisibleIndex;
			var filename;
		if(getElementByIdOrByName(inputVisible).files[0]!=undefined){
		    if(window.ActiveXObject){
		        var fso = new ActiveXObject("Scripting.FileSystemObject");
		        var filepath = getElementByIdOrByName(inputVisible).value;
		        var thefile = fso.getFile(filepath);
		        filename = thefile.name;
		    }else{
		    	filename = getElementByIdOrByName(inputVisible).files[0].name;
		    }
	}
	
	 document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt").value = filename;
	   // windows
	 /*  var fileName = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length);
	   
	   // linux
	   if (fileName.length == filePath.length) {
	       fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length);
	   } 
	  // document.fileUploadForm.fileName.value = fileName; 
	   //parent.resizeIFrame();
	   document.getElementById("attachment_s[i].attachmentVO_s[0].theAttachmentDT.attachmentPath").value = filePath;
	   */
	   
}

function changeComponent()
{
	getElementByIdOrByName("requiredAttachment").setAttribute("style","font-size:9pt");
	document.getElementById("ImportButton").setAttribute("type","file");
	document.getElementById("ImportButton").setAttribute("parent","Attachment");
	document.getElementById("ImportButton").setAttribute("fieldLabel","Choose File");
	document.getElementById("ImportButton").setAttribute("validate","required");
	document.getElementById("ImportButton").setAttribute("isnested","yes");
	document.getElementById("ImportButton").setAttribute("onload","this.value='yes'");
	
	document.getElementById("ImportButton").setAttribute("errorcode","");
	document.getElementById("ImportButton").setAttribute("size","52");
//	document.getElementById("ImportButton").setAttribute("onchange","handleNewFileSelection(this.value)");
	
	
	document.getElementById("ImportButton").setAttribute("name","attachment_s[i].attachmentVO_s[0].theAttachmentDT.attachment");
	document.getElementById("ImportButton").setAttribute("id","attachment_s[i].attachmentVO_s[0].theAttachmentDT.attachment");

}

/*
function storeFile2(file){

	alert(file);
	JMorbidityReport.alertMethod("", function(data){
	
	alert("hello");
});
}


function storeFile(file){

	JMorbidityReport.storeFileJava(function(data){
	
	alert("hello");
});
}*/

/**
 * hideErrorMessagesAndLines: this method hides the extra lines of the input file elements that are hidden for not showing extra spaces
 */

function hideErrorMessagesAndLines(){

document.getElementById("fileAttachmentLine1").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine2").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine3").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine4").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine5").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine6").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine7").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine8").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine9").setAttribute("style","display:none");
document.getElementById("fileAttachmentLine10").setAttribute("style","display:none");

//errors
document.getElementById("fileAttachmentLine1").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine2").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine3").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine4").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine5").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine6").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine7").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine8").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine9").nextSibling.nextSibling.setAttribute("style","display:none");
document.getElementById("fileAttachmentLine10").nextSibling.nextSibling.setAttribute("style","display:none");

}



validationArray[3] = morbJurisdictionCheck;
//labspecific is using 0,1 in the array

batchEntryValidationArray[3] = testMorbResultCodedCheck;
batchEntryValidationArray[0] = customTreatmentRequired;


