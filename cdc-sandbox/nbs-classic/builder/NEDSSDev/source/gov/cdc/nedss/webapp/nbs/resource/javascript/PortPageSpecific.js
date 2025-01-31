//To disable submit button on Port Page Load.
function onLoadAction(){
       //getElementByIdOrByName("Submit").disabled=true;                        //button on top right of page
       //getElementByIdOrByName("Submit2").disabled=true;                      //button on bottom right of page

       $j("div#addAttachmentBlock").hide();

       var fileNm = $j("#fileName").attr("value");
       var filePath = $j("#filePath").attr("value");

       if(fileNm != null && fileNm != "null"){
              getElementByIdOrByName("mapName").value=fileNm.substring(0,fileNm.lastIndexOf(".xml"));    //prepopulating the name of Imported Map
             getElementByIdOrByName("fileSelected").innerHTML=fileNm+" selected.";
             //getElementByIdOrByName("importFile").value=filePath;
       }else{
             getElementByIdOrByName("fileSelected").innerHTML="";
       }

}

//To disable or enable "Map Answers" button on Question Page and "Review Mapping" button on Answer Page based on Mapping Needed.
function disableSubmit(){
       var mapNeedSize=$j("#mapReqCnt").attr("value");
       if(mapNeedSize!=0){
             getElementByIdOrByName("Submit").disabled=true;                        //button on top right of table
             getElementByIdOrByName("Submit2").disabled=true;                      //button on bottom right of table
       }
       else{
             getElementByIdOrByName("Submit").disabled=false;
             getElementByIdOrByName("Submit2").disabled=false;
       }
}

function viewFromPageFields(waUiMetadataUid,questionIdentifier,questionLabel,dataType,codeSetGroup,recordStatusCd, mapped, questionGroupSeqNbr, answerGroupSeqNbr, toQuestionGroupSeqNbr, questionEditFlag){

       mapFromPageFields(waUiMetadataUid,questionIdentifier,questionLabel,dataType,codeSetGroup,recordStatusCd, mapped, questionGroupSeqNbr,answerGroupSeqNbr);
       getElementByIdOrByName("questionMappingReq").disabled=true;
       getElementByIdOrByName("questionMappingReq_textbox").disabled=true;
       getElementByIdOrByName("answerMappingReq").disabled=true;
       getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
       getElementByIdOrByName("toQuestionList").disabled=true;
       getElementByIdOrByName("toQuestionList_textbox").disabled=true;
       getElementByIdOrByName("addBtn").style.display="none";
       getElementByIdOrByName("editBtn").style.display="";
       
       getElementByIdOrByName("fromQuestionGroupSeqNbr").value = questionGroupSeqNbr;
       getElementByIdOrByName("toQuestionGroupSeqNbr").value = toQuestionGroupSeqNbr;
       
       if(questionGroupSeqNbr!=null && questionGroupSeqNbr!="null"){
             getElementByIdOrByName("legacyBlockIDLable").innerHTML = "Legacy Block?:";
             if(toQuestionGroupSeqNbr!=null && toQuestionGroupSeqNbr!="null" && toQuestionGroupSeqNbr!="")
             	getElementByIdOrByName("legacyBlockID").innerHTML = "Yes";
             else
             	getElementByIdOrByName("legacyBlockID").innerHTML = "Yes       <a href=\"javascript:mapAnotherInstance('"+questionIdentifier+"','"+dataType+"','"+answerGroupSeqNbr+"')\">Map Another Instance</a>"; 
             
             //getElementByIdOrByName("addBtn").style.display="";
             //getElementByIdOrByName("editBtn").style.display="none";
       }else{
             getElementByIdOrByName("legacyBlockIDLable").innerHTML = "";
             getElementByIdOrByName("legacyBlockID").innerHTML = "";
       }
     
       if(questionEditFlag!=null && questionEditFlag == 'false'){
    	   getElementByIdOrByName("editBtn").style.display="none";
       }
       
       getElementByIdOrByName("repeatingBlockNumber").disabled=true;
       
}

//This function makes the question editable in view mode.
function editQuestion(){
       var questionIdentifier = getElementByIdOrByName("fromQuestionID").innerHTML;
       var questionLabel = getElementByIdOrByName("fromQuestionLabel").innerHTML;
       var dataType =getElementByIdOrByName("fromDataType").innerHTML;
       var codeSetGroup = getElementByIdOrByName("fromValueSet").innerHTML;
       var mapped = getElementByIdOrByName("questionMappingReq").value;
       var repeatingBlckNbr = getElementByIdOrByName("repeatingBlockNumber").innerHTML;
       if(repeatingBlckNbr==null||repeatingBlckNbr==""||repeatingBlckNbr<1){
             var questGrpSeqNbr = null;
       }else{
             questGrpSeqNbr=repeatingBlckNbr;
       }
       var answerGroupSeqNbr = getElementByIdOrByName("answerGroupSeqNbr").value;
       
       mapFromPageFields(null,questionIdentifier,questionLabel,dataType,codeSetGroup,null, mapped, questGrpSeqNbr,answerGroupSeqNbr);
       if(mapped!='N'){
             getElementByIdOrByName("questionMappingReq").disabled=false;
             getElementByIdOrByName("questionMappingReq_textbox").disabled=false;
             getElementByIdOrByName("answerMappingReq").disabled=false;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=false;
             getElementByIdOrByName("toQuestionList").disabled=false;
             getElementByIdOrByName("toQuestionList_textbox").disabled=false;
       }
       
       var fromQuestionGroupSeqNbr = getElementByIdOrByName("fromQuestionGroupSeqNbr").value;
       var toQuestionGroupSeqNbr = getElementByIdOrByName("toQuestionGroupSeqNbr").value;
       if (fromQuestionGroupSeqNbr!=null && fromQuestionGroupSeqNbr!= "null" && fromQuestionGroupSeqNbr!= "" && toQuestionGroupSeqNbr!=null && toQuestionGroupSeqNbr!="" && toQuestionGroupSeqNbr!="null"){
       		getElementByIdOrByName("repeatingBlockNumber").readOnly = true;
       }
}

function mapAnotherInstance(questionIdentifier, dataType, answerGroupSeqNbr){
       getElementByIdOrByName("questionMappingReq").disabled=false;
       getElementByIdOrByName("questionMappingReq_textbox").disabled=false;
       getElementByIdOrByName("answerMappingReq").disabled=false;
       getElementByIdOrByName("answerMappingReq_textbox").disabled=false;
       getElementByIdOrByName("toQuestionList").disabled=false;
       getElementByIdOrByName("toQuestionList_textbox").disabled=false;
       getElementByIdOrByName("addButton").disabled=false;

       getElementByIdOrByName("toQuestionList").value = "";
       getElementByIdOrByName("toQuestionList_textbox").value = "";
       getElementByIdOrByName("answerMappingReq").value = "";
       getElementByIdOrByName("answerMappingReq_textbox").value = "";
       getElementByIdOrByName("toQuestionLabel").innerHTML =  "";
       getElementByIdOrByName("toDataType").innerHTML =  "";
       getElementByIdOrByName("toValueSet").innerHTML =  "";

       getElementByIdOrByName("MapAnotherInstance").value = "Yes";
       getElementByIdOrByName('questionMappingReq').value = "Y";
       getElementByIdOrByName('questionMappingReq_textbox').value = "Yes";
       
       getElementByIdOrByName("addBtn").style.display="";
       getElementByIdOrByName("editBtn").style.display="none";
       
	JPortPage.getMappedToQuestionFields(questionIdentifier, answerGroupSeqNbr, dataType, function(data){
		var dropDownOptions = [];

		for(var i=7; i<data.length; i++)
		{
			var dropdown = data[i];
			dropDownOptions.push(dropdown);
		}	
		DWRUtil.removeAllOptions("toQuestionList");	
		getElementByIdOrByName("toQuestionList_textbox").value="";
		DWRUtil.addOptions("toQuestionList", dropDownOptions, "key", "value" );
	});

}

function mapFromPageFields(waUiMetadataUid,questionIdentifier,questionLabel,dataType,codeSetGroup,recordStatusCd, mapped, questionGroupSeqNbr, answerGroupSeqNbr)
{

	   //Before mapping the question if user clicks on Map Another instance link then set back to No.
	   getElementByIdOrByName("MapAnotherInstance").value = "No";
	
       if(codeSetGroup == null || codeSetGroup == "null"){
             codeSetGroup="";
       }
     //The toQuestionList_textbox is making blank because sometimes it is auto populating the previously mapped toQuestionFields for the next question which is ready to map next.
       getElementByIdOrByName("toQuestionList").value="";
       
       getElementByIdOrByName("pageMappingAttributes").style.display = "";
       getElementByIdOrByName("addBtn").style.display="";
       getElementByIdOrByName("editBtn").style.display="none";

       getElementByIdOrByName("fromQuestionID").innerHTML = questionIdentifier;
       getElementByIdOrByName("fromQuestionLabel").innerHTML = questionLabel;
       getElementByIdOrByName("fromDataType").innerHTML = dataType;
       getElementByIdOrByName("fromValueSet").innerHTML = codeSetGroup;
       //getElementByIdOrByName("active").innerHTML = recordStatusCd;

       getElementByIdOrByName("fromQuestionToMap").value = questionIdentifier;

       //if(recordStatusCd=="Active"){
       getElementByIdOrByName("questionMappingReq").value = "Y";
       getElementByIdOrByName("questionMappingReq_textbox").value = "Yes";
       /*}else{
             getElementByIdOrByName("questionMappingReq").value = "N";
             getElementByIdOrByName("questionMappingReq_textbox").value = "No";
       }*/

       getElementByIdOrByName("answerMappingReq").value = "";
       getElementByIdOrByName("answerMappingReq_textbox").value = "";

       disableEnableTOFields(mapped);

       //getMappedToQuestionFields call is calling this DWR call
       /*     //After selecting every question refresh the ToQuestion list
       JPortPage.getToQuestionDropDown(questionIdentifier,dataType,function(data){
             DWRUtil.removeAllOptions("toQuestionList");
       DWRUtil.addOptions("toQuestionList", data, "key", "value" );
       getElementByIdOrByName("toQuestionList_textbox").value="";
       });*/
       
       if(questionGroupSeqNbr!=null && questionGroupSeqNbr!="null"){
             getElementByIdOrByName("legacyBlockIDLable").innerHTML = "Legacy Block?:";
             getElementByIdOrByName("legacyBlockID").innerHTML = "Yes";
             getElementByIdOrByName("answerGroupSeqNbr").value = answerGroupSeqNbr;
       }else{
             getElementByIdOrByName("legacyBlockIDLable").innerHTML = "";
             getElementByIdOrByName("legacyBlockID").innerHTML = "";
       }

       // For mapped/auto mapped questions populate to fields
       JPortPage.getMappedToQuestionFields(questionIdentifier, answerGroupSeqNbr, dataType, function(data){
             var toQuestionID = data[0];
             var toLable=data[1];
             var toDataType=data[2];
             var toValueSet=data[3];
             var toAnswerMappingReq=data[4];
             var fieldMappingReq=data[5];
             var blockIdNbr=data[6];
             
             var dropDownOptions = [];

             for(var i=7; i<data.length; i++)
             {
                    var dropdown = data[i];
                    dropDownOptions.push(dropdown);

             }      
             //After selecting every question refresh the ToQuestion list
             DWRUtil.removeAllOptions("toQuestionList");    
             getElementByIdOrByName("toQuestionList_textbox").value="";
             DWRUtil.addOptions("toQuestionList", dropDownOptions, "key", "value" );

             if(toQuestionID!=null && toQuestionID.length>0){
                    //Adding selected ToQuestion in dropdown
                    var dData = [ { key:toQuestionID, value:toQuestionID+" : "+ toLable}];
                    //dropDownOptions.push(dData);
                    DWRUtil.addOptions("toQuestionList", dData, "key", "value");
             
             /*     getElementByIdOrByName("toQuestionList").value = toQuestionID;
                    getElementByIdOrByName("toQuestionList_textbox").value = toQuestionID+" - "+ toLable;*/
             
                    //setSelectedValue("toQuestionList",toQuestionID);
                    
             }
             
             sortSelectList("toQuestionList");
             
             if(toQuestionID!=null && toQuestionID.length>0){
                    getElementByIdOrByName("toQuestionList").value = toQuestionID;
                    getElementByIdOrByName("toQuestionList_textbox").value = toQuestionID+" : "+ toLable;
                    
                    getElementByIdOrByName("unmappedToQuestion").value = toQuestionID;
             }
             getElementByIdOrByName("toQuestionLabel").innerHTML = toLable;
             getElementByIdOrByName("toDataType").innerHTML = toDataType;
             getElementByIdOrByName("toValueSet").innerHTML = toValueSet;
             getElementByIdOrByName("repeatingBlockNumber").value=blockIdNbr;

             if(toAnswerMappingReq!=null && toAnswerMappingReq.length>0){
                    if(toAnswerMappingReq=="Y"){
                           getElementByIdOrByName("answerMappingReq").value = "Y";
                           getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
                    }else if(toAnswerMappingReq=="N"){
                           getElementByIdOrByName("answerMappingReq").value = "N";
                           getElementByIdOrByName("answerMappingReq_textbox").value = "No";
                    }
             }

             if(fieldMappingReq!=null && fieldMappingReq.length>0){
                    if(fieldMappingReq=="Y"){
                           getElementByIdOrByName("questionMappingReq").value = "Y";
                           getElementByIdOrByName("questionMappingReq_textbox").value = "Yes";
                    }else if(fieldMappingReq=="N"){
                           getElementByIdOrByName("questionMappingReq").value = "N";
                           getElementByIdOrByName("questionMappingReq_textbox").value = "No";
                           clearToFields();
                    }
             }

             
             if(getElementByIdOrByName("toQuestionList").value==""){
                    getElementByIdOrByName("addButton").disabled=true;
             }else{
                    getElementByIdOrByName("addButton").disabled=false;
             }
             var fromDataType = getElementByIdOrByName("fromDataType").innerHTML;
             var toDataType = getElementByIdOrByName("toDataType").innerHTML;
             var fromValueSet = getElementByIdOrByName("fromValueSet").innerHTML;             
             var toValueSet = getElementByIdOrByName("toValueSet").innerHTML;
             enableDisabletoFieldsOnToID(fromDataType,toDataType, fromValueSet, toValueSet);
            
             getElementByIdOrByName("questionMappingReq_textbox").focus();

             if(blockIdNbr == null || blockIdNbr == "" || blockIdNbr == "null"){
              	 getElementByIdOrByName("repeatingBlockNumber").disabled=true;
               }
       });
       
      // alert(getElementByIdOrByName("repeatingBlockNumber").value);

       getElementByIdOrByName("questionMappingReq").disabled=false;
       getElementByIdOrByName("questionMappingReq_textbox").disabled=false;
       //getElementByIdOrByName("addButton").disabled=false;



}
function sortSelectList(listId) {
       var lb = getElementByIdOrByName(listId);

       arrTexts = new Array();
       arrValues = new Array();
       for(i=0; i<lb.length; i++)  {
             arrTexts[i] = lb.options[i].text;
             
       }

       arrTexts.sort();

       for(i=0; i<lb.length; i++)  {
             lb.options[i].text = arrTexts[i];
             lb.options[i].value = arrTexts[i];

       }
       for(i=1; i<lb.length; i++)  {
             
             var tempValue = lb.options[i].value;
             var pos = tempValue.indexOf(":");
             lb.options[i].value = tempValue.substring(0,pos-1).trim();

       }
       //getElementByIdOrByName(listId).value = previousSelectedValue;
       //getElementByIdOrByName(listId+"_textbox").value = previousSelectedText;

}
function setSelectedValue(listId, valueToSet) {
       var lb = getElementByIdOrByName(listId);
       var indexS; 
    for (var i = 0; i < lb.options.length; i++) {
        if (lb.options[i].value== valueToSet) {
             lb.options[i].selected = true;
             indexS = i;
             break;
        }
    }
    
    lb.options[i].selected = true;
}

function viewAnswer(questionIdentifier, questionLabel,codeSetName,code, codeDescTxt,mapped){

       mapAnswers(questionIdentifier, questionLabel,codeSetName,code, codeDescTxt,mapped);
       getElementByIdOrByName("answerMappingReq").disabled=true;
       getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
       getElementByIdOrByName("toCodeList").disabled=true;
       getElementByIdOrByName("toCodeList_textbox").disabled=true;
       getElementByIdOrByName("toAnswerId").disabled=true;
       //getElementByIdOrByName("addButton").disabled=true;
       getElementByIdOrByName("addBtn").style.display="none";
       getElementByIdOrByName("editBtn").style.display="";

}

function defaultYesAnswer(){
       getElementByIdOrByName("answerMappingReq").value = "Y";
       getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
}
//This function makes the answer editable in view mode.
function editAnswer(){
       var questionIdentifier = getElementByIdOrByName("fromQuestionID").innerHTML;
       var questionLabel = getElementByIdOrByName("fromQuestionLabel").innerHTML;
       var codeSetName = getElementByIdOrByName("fromCodeSet").innerHTML;
       var code = getElementByIdOrByName("fromCode").innerHTML;
       var codeDescTxt = getElementByIdOrByName("fromCodeDesc").innerHTML;
       var mapped = getElementByIdOrByName("answerMappingReq").value;

       mapAnswers(questionIdentifier, questionLabel,codeSetName,code, codeDescTxt,mapped);
}

function mapAnswers(questionIdentifier, questionLabel,codeSetName,code, codeDescTxt,mapped){
       defaultYesAnswer();
       getElementByIdOrByName("pageMappingAttributes").style.display = "";
       getElementByIdOrByName("addBtn").style.display="";
       getElementByIdOrByName("editBtn").style.display="none";


       getElementByIdOrByName("fromQuestionID").innerHTML = questionIdentifier;
       getElementByIdOrByName("fromQuestionLabel").innerHTML = questionLabel;
       getElementByIdOrByName("fromCodeSet").innerHTML = codeSetName;
       getElementByIdOrByName("fromCode").innerHTML = code;
       getElementByIdOrByName("fromCodeDesc").innerHTML = codeDescTxt;

       getElementByIdOrByName("mappedFromQuestion").value = questionIdentifier;
       getElementByIdOrByName("mappedFromCode").value = code;

       disableEnableAnswerTOFields(mapped);

       /*JPortPage.getToCodeDropDown(questionIdentifier, function(data){
             DWRUtil.removeAllOptions("toCodeList");
       DWRUtil.addOptions("toCodeList", data, "key", "value" );
       getElementByIdOrByName("toCodeList_textbox").value="";
       });*/

       JPortPage.getToQuestionAndValuesets(questionIdentifier,code, function(data){
             var fromDataType=data[0];
             var toQuestionID=data[1];
             var toQuestionLabel=data[2];
             var toDataType=data[3];
             var toCodeSet=data[4];
             var toCode=data[5];
             var toCodeDesc=data[6];
             if(toCode == null || toCode == "null"){
                    toCode="";
             }
             if(toCodeDesc == null || toCodeDesc == "null"){
                    toCodeDesc="";
             }
             var dropDownOptions = data[7];
             //dropdowns
             /*var dropDownOptions = [];

             for(var i=7; i<data.length; i++)
             {
                 var dropdown = data[i];
                 dropDownOptions.push(dropdown);

             }*/
             DWRUtil.removeAllOptions("toCodeList");
             DWRUtil.addOptions("toCodeList", dropDownOptions, "key", "value" );
             getElementByIdOrByName("toCodeList_textbox").value="";


             getElementByIdOrByName("fromDataTypeValue").innerHTML = fromDataType;
             getElementByIdOrByName("toQuestionID").innerHTML = toQuestionID;
             getElementByIdOrByName("toQuestionLabel").innerHTML = toQuestionLabel;
             getElementByIdOrByName("toCodeSet").innerHTML = toCodeSet;
             getElementByIdOrByName("toDataTypeValue").innerHTML = toDataType;
             if(toDataType=='CODED')
                    getElementByIdOrByName("toCodeList").value = toCode;
             else if(toDataType!='CODED')
                    getElementByIdOrByName("toAnswerId").value = toCode;
             if(toCode!=null && toCode.length>0){
                    getElementByIdOrByName("toCodeList_textbox").value = toCode+" : "+toCodeDesc;
             }
             getElementByIdOrByName("toCodeDesc").innerHTML = toCodeDesc;

             getElementByIdOrByName("mappedToQuestion").value = toQuestionID;

             if(toDataType=='CODED'){
                    getElementByIdOrByName("toCodeSet").style.display = "";
                    getElementByIdOrByName("toCodeListL").style.display = "";
                    getElementByIdOrByName("toCode").style.display = "";
                    getElementByIdOrByName("toCodeDescLabel").style.display = "";
                    getElementByIdOrByName("toCodeDesc").style.display = "";
                    getElementByIdOrByName("toAnswer").style.display = "none";
                    getElementByIdOrByName("toCodeSetLabel").innerHTML = "To Value Set:";
             }else{
                    /*getElementByIdOrByName("toCodeSet").style.display = "none";
                    getElementByIdOrByName("toCodeListL").style.display = "none";
                    getElementByIdOrByName("toCode").style.display = "none";
                    getElementByIdOrByName("toCodeDescLabel").style.display = "none";
                    getElementByIdOrByName("toCodeDesc").style.display = "none";*/
                    getElementByIdOrByName("toAnswer").style.display = "";
                    getElementByIdOrByName("toAnswerId").focus();
                    getElementByIdOrByName("toCode").style.display = "none";

                    /*getElementByIdOrByName("toCodeSetLabel").innerHTML = "To Answer:";*/
             }

             if(fromDataType!='CODED'){
                    /*getElementByIdOrByName("fromCodeSetLabel").innerHTML = "";*/
                    getElementByIdOrByName("fromCodeSet").innerHTML = "";
                    /*getElementByIdOrByName("fromCodeDescLabel").innerHTML = "";*/
                    getElementByIdOrByName("fromCodeDesc").innerHTML = "";
             }else{
                    getElementByIdOrByName("fromCodeSetLabel").innerHTML = "From Value Set:";
                    getElementByIdOrByName("fromCodeSet").innerHTML = codeSetName;
                    getElementByIdOrByName("fromCodeDescLabel").innerHTML = "From Code Desc:";
                    getElementByIdOrByName("fromCodeDesc").innerHTML = codeDescTxt;
             }
             getElementByIdOrByName("answerMappingReq_textbox").focus();
       });




       if(mapped!=null && mapped.length>0){
             if(mapped=="Y"){
                    getElementByIdOrByName("answerMappingReq").value = "Y";
                    getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
             }else if(mapped=="N"){
                    getElementByIdOrByName("answerMappingReq").value = "N";
                    getElementByIdOrByName("answerMappingReq_textbox").value = "No";
             }
       }

       getElementByIdOrByName("answerMappingReq").disabled=false;
       getElementByIdOrByName("answerMappingReq_textbox").disabled=false;
       getElementByIdOrByName("addButton").disabled=false;
       getElementByIdOrByName("answerMappingReq_textbox").focus();
}
function enableDisabletoFieldsOnToID(fromDataType,toDataType, fromValueSet, toValueSet){
	
       if((fromDataType=='CODED' && toDataType=='NUMERIC') || (fromDataType=='NUMERIC' && toDataType=='CODED') || (fromDataType=='TEXT' && toDataType=='CODED') || (fromDataType=='TEXT' && toDataType=='NUMERIC')){
             getElementByIdOrByName("answerMappingReq").value = "Y";
             getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
             getElementByIdOrByName("codeMappingRequired").value = true; //Added: To persist value in the DT
             getElementByIdOrByName("answerMappingReq").disabled=true;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
       }else if((fromDataType=='NUMERIC' && toDataType=='NUMERIC') || (fromDataType=='TEXT' && toDataType=='TEXT') || (fromDataType=='NUMERIC' && toDataType=='TEXT') || (fromDataType=='CODED' && toDataType=='TEXT')  || (fromDataType=='DATE' && toDataType=='DATE')  || (fromDataType=='PART' && toDataType=='PART')){
             getElementByIdOrByName("answerMappingReq").value = "N";
             getElementByIdOrByName("answerMappingReq_textbox").value = "No";
             getElementByIdOrByName("codeMappingRequired").value = false;              
             getElementByIdOrByName("answerMappingReq").disabled=true;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=true;

       }else if(((fromDataType=='CODED' && toDataType=='CODED')) && !(fromValueSet==toValueSet)){
             getElementByIdOrByName("answerMappingReq").value = "Y";
             getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
             getElementByIdOrByName("codeMappingRequired").value = true;
             getElementByIdOrByName("answerMappingReq").disabled=true;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
       }
       else if(!getElementByIdOrByName("answerMappingReq").disabled){
    	   
             getElementByIdOrByName("answerMappingReq").disabled=false;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=false;
             getElementByIdOrByName("answerMappingReq_textbox").focus();
       }

}

function populateToFields(toPageQuestionId)
{
	
       var fromId=getElementByIdOrByName("fromQuestionID").innerHTML;
       //var toID = getElementByIdOrByName("toQuestionList").value;
       JPortPage.getToQuestionFields(fromId,toPageQuestionId.value, function(data){
       //JPortPage.getToQuestionFields(fromId,toID, function(data){
             var toLable=data[0];
             var toDataType=data[1];
             var toValueSet=data[2];
             var toGrpSqNbr=data[3];
             var blockIdNbr=data[4];
             var mappedInd=data[5];      
             var fromQuestionGroupSeqNbr=data[6]; 


             getElementByIdOrByName("toQuestionLabel").innerHTML = toLable;
             getElementByIdOrByName("toDataType").innerHTML = toDataType;
             getElementByIdOrByName("toValueSet").innerHTML = toValueSet;
             getElementByIdOrByName("repeatingBlockNumber").value=blockIdNbr;

             // If toDataType CODED then show To Value Set label otherwise hide it. 
             //CHANGE:  Show To value set blank rather than hide it 
             if(toDataType=='CODED'){
                    /*getElementByIdOrByName("toValueSetLabel").style.display = "";*/
             }else{
                    /*     getElementByIdOrByName("toValueSetLabel").style.display = "none";*/
                    getElementByIdOrByName("toValueSet").innerHTML = "";
             }

             //disables the repeating block number field,when the to question is non repeating block question 
             //and when the to question is repeating block question when it is already mapped. 
             if(toGrpSqNbr==""){
                    getElementByIdOrByName("repeatingBlockNumber").disabled=true;
                    getElementByIdOrByName("RepeatingBlockQuestionLabel").innerHTML = "";
             	    getElementByIdOrByName("RepeatingBlockQuestionId").innerHTML = "";
             }else if(mappedInd=="true"){
                    getElementByIdOrByName("repeatingBlockNumber").disabled=false;
                    
                    getElementByIdOrByName("RepeatingBlockQuestionLabel").innerHTML = "Repeating Block Question:";
             	    getElementByIdOrByName("RepeatingBlockQuestionId").innerHTML = "Yes";
             }else{
                    getElementByIdOrByName("repeatingBlockNumber").disabled=false;
                    getElementByIdOrByName("repeatingBlockNumber").focus();
                    getElementByIdOrByName("RepeatingBlockQuestionLabel").innerHTML = "Repeating Block Question:";
             	    getElementByIdOrByName("RepeatingBlockQuestionId").innerHTML = "Yes";
             }

             //If both from and to questions are repeating then disable textbox
             if(toGrpSqNbr!="" && fromQuestionGroupSeqNbr!=""){
            	 getElementByIdOrByName("repeatingBlockNumber").readOnly = true; 
     		 }else{
     			 getElementByIdOrByName("repeatingBlockNumber").readOnly = false; 
     		 }
             
             var fromValueSet = getElementByIdOrByName("fromValueSet").innerHTML;             
             if(fromValueSet==toValueSet){
                    getElementByIdOrByName("answerMappingReq").value = "N";
                    getElementByIdOrByName("answerMappingReq_textbox").value = "No";
                    getElementByIdOrByName("codeMappingRequired").value = false; //Added: To persist value in the DT
             }else{
                    getElementByIdOrByName("answerMappingReq").value = "Y";
                    getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
                    getElementByIdOrByName("codeMappingRequired").value = true; //Added: To persist value in the DT

             }


             var fromDataType = getElementByIdOrByName("fromDataType").innerHTML;
             enableDisabletoFieldsOnToID(fromDataType,toDataType, fromValueSet, toValueSet);
             /*if((fromDataType=='CODED' && toDataType=='NUMERIC') || (fromDataType=='NUMERIC' && toDataType=='CODED') || (fromDataType=='TEXT' && toDataType=='CODED') || (fromDataType=='TEXT' && toDataType=='NUMERIC')){
                    getElementByIdOrByName("answerMappingReq").value = "Y";
                    getElementByIdOrByName("answerMappingReq_textbox").value = "Yes";
                    getElementByIdOrByName("codeMappingRequired").value = true; //Added: To persist value in the DT
                    getElementByIdOrByName("answerMappingReq").disabled=true;
                    getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
             }else if((fromDataType=='NUMERIC' && toDataType=='NUMERIC') || (fromDataType=='TEXT' && toDataType=='TEXT') || (fromDataType=='NUMERIC' && toDataType=='TEXT') || (fromDataType=='CODED' && toDataType=='TEXT')  || (fromDataType=='DATE' && toDataType=='DATE')  || (fromDataType=='PART' && toDataType=='PART')){
                    getElementByIdOrByName("answerMappingReq").value = "N";
                    getElementByIdOrByName("answerMappingReq_textbox").value = "No";
                    getElementByIdOrByName("codeMappingRequired").value = false;             
                    getElementByIdOrByName("answerMappingReq").disabled=true;
                    getElementByIdOrByName("answerMappingReq_textbox").disabled=true;

             }else if(((fromDataType=='CODED' && toDataType=='CODED')) && !(fromValueSet==toValueSet)){
                    getElementByIdOrByName("answerMappingReq").disabled=true;
                    getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
             }
             else{
                    getElementByIdOrByName("answerMappingReq").disabled=false;
                    getElementByIdOrByName("answerMappingReq_textbox").disabled=false;
             }*/
             
             
             if(toPageQuestionId.value!=""){
                    getElementByIdOrByName("addButton").disabled=false;
             }
             else{
                    getElementByIdOrByName("addButton").disabled=true;
             }
             if(!getElementByIdOrByName("answerMappingReq_textbox").disabled)
                    getElementByIdOrByName("answerMappingReq_textbox").focus();
             else if(!getElementByIdOrByName("repeatingBlockNumber").disabled && getElementByIdOrByName("repeatingBlockNumber").readOnly == false)
                    getElementByIdOrByName("repeatingBlockNumber").focus();
             else if(!getElementByIdOrByName("addButton").disabled)
                    getElementByIdOrByName("addButton").focus();
             
       });
}
//port page required fields validation
function portPageReqFlds(){

       $j(".infoBox").hide();
       var errors = new Array();
       var index = 0;
       var isError = false;
       var mapName= getElementByIdOrByName("mapName");
       var fromTemplate= getElementByIdOrByName("fromTemplate");
       var toTemplate= getElementByIdOrByName("toTemplate");
       var sChars = "~`@#$%^&*()+=[]\{}|;\':\",/<>?"
             var isPresent = false;

       // map name 
       if(mapName != null && mapName.value.trim().length == 0) {
             errors[index++] = "Map Name is required";
             getElementByIdOrByName("uniqueMapName").style.color="990000";
             isError = true;
             getElementByIdOrByName("mapName").focus();
       }
       else {
             getElementByIdOrByName("uniqueMapName").style.color="black";
       }
       //To check special characters 
       for (var i=0;i<mapName.value.length;i++){
             if(sChars.indexOf(mapName.value.charAt(i)) != -1){
                    isPresent=true;
             }
       }
       if(isPresent){
             isError=true;
             errors[index++]="No Special characters are allowed other than -,. and _ for Map Name.";
             getElementByIdOrByName("uniqueMapName").style.color="990000";
             getElementByIdOrByName("mapName").focus();
       }
       //From Page
       if(fromTemplate != null && fromTemplate.value.length == 0)
       {
             errors[index++] = "From Page is required.";
             getElementByIdOrByName("uniquePageNameL").style.color="990000";
             isError = true;
             getElementByIdOrByName("fromTemplate").focus();
       }
       else {
             getElementByIdOrByName("uniquePageNameL").style.color="black";
       }
       //To Page 
       if(toTemplate != null && toTemplate.value.length == 0)
       {
             errors[index++] = "To Page is required.";
             getElementByIdOrByName("existingTemplateL").style.color="990000";
             isError = true;
             getElementByIdOrByName("toTemplate").focus();
       }
       else {
             getElementByIdOrByName("existingTemplateL").style.color="black";
       }      

       if(isError){
             displayGlobalErrorMessage(errors);
       }

       return isError;
}


function checkBeforeSubmitNextMappig(){

       $j(".infoBox").hide();

       var errors = new Array();
       var index = 0;
       var isError = false;

       var toQuestionSel= getElementByIdOrByName("toQuestionList");
       var questionMappingReq = getElementByIdOrByName("questionMappingReq");
       var answerMappingReq = getElementByIdOrByName("answerMappingReq");
       var repeatingBlock = getElementByIdOrByName("repeatingBlockNumber");
       if(questionMappingReq != null && questionMappingReq.value.length == 0){
             errors[index++] = "Select Map Question";
              getElementByIdOrByName("questionMappingReqLabel").style.color="990000";
             isError = true;
       }
       else{
              getElementByIdOrByName("questionMappingReqLabel").style.color="black";
       }

       if(questionMappingReq.value != "N"){ // If Question mapping required then select toQuestion and Code Mapping Required option.

             if(toQuestionSel != null && toQuestionSel.value.length == 0){
                    errors[index++] = "Select To ID";
                    getElementByIdOrByName("toQuestionListL").style.color="990000";
                    isError = true;
             }
             else{
                    getElementByIdOrByName("toQuestionListL").style.color="black";
             }

             if(answerMappingReq != null && answerMappingReq.value.length == 0){
                    errors[index++] = "Select Map Answers";
                    getElementByIdOrByName("answerMappingReqLabel").style.color="990000";
                    isError = true;
             }      else{
                    getElementByIdOrByName("answerMappingReqLabel").style.color="black";
             }

             if(repeatingBlock.disabled!= true && !isInteger(repeatingBlock.value)){
                    getElementByIdOrByName("RepeatingBlockNumberLabel").style.color="990000";
                    errors[index++] = "Numeric value is required for Repeating Block Number";
                    isError = true;
             }
             else{
                    getElementByIdOrByName("RepeatingBlockNumberLabel").style.color="black";
             }


              /*getElementByIdOrByName("questionMappingReqLabel").style.color="black";*/
       }/*else{
                    getElementByIdOrByName("questionMappingReqLabel").style.color="black";
           }
       */
       if(isError){
             displayGlobalErrorMessage(errors);
       }

       return isError;
}

//clear to fields on the questions page when map question is selected as no. 
function clearToFields(){
		getElementByIdOrByName("unmappedToQuestion").value = getElementByIdOrByName("toQuestionList").value;
       getElementByIdOrByName("answerMappingReq_textbox").value= "";
       getElementByIdOrByName("toQuestionList_textbox").value="";
       getElementByIdOrByName("answerMappingReq").value= "";
       getElementByIdOrByName("toQuestionList").value= "";
       getElementByIdOrByName("toValueSet").innerHTML = "";
       getElementByIdOrByName("toDataType").innerHTML = "";
       getElementByIdOrByName("toQuestionLabel").innerHTML = "";
       getElementByIdOrByName("repeatingBlockNumber").value="";
}

function disableEnableTOFields(fieldMappingReq){
       if(fieldMappingReq=='N'){
             clearToFields();
             getElementByIdOrByName("toQuestionList").disabled=true;
             getElementByIdOrByName("toQuestionList_textbox").disabled=true;
             getElementByIdOrByName("answerMappingReq").disabled=true;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=true;
             getElementByIdOrByName("addButton").disabled=false;
             getElementByIdOrByName("repeatingBlockNumber").disabled=true;
             getElementByIdOrByName("addButton").focus();
       }else{
             getElementByIdOrByName("toQuestionList").disabled=false;
             getElementByIdOrByName("toQuestionList_textbox").disabled=false;
             getElementByIdOrByName("toQuestionList_textbox").focus();
             getElementByIdOrByName("answerMappingReq").disabled=false;
             getElementByIdOrByName("answerMappingReq_textbox").disabled=false;
             getElementByIdOrByName("repeatingBlockNumber").disabled=false;

             if(getElementByIdOrByName("toQuestionList").value==""){
                    getElementByIdOrByName("addButton").disabled=true;   
             }else{
                    getElementByIdOrByName("addButton").disabled=false;
                    getElementByIdOrByName("addButton").focus();
             }
       }
}


function disableEnableAnswerTOFields(fieldMappingReq){
       if(fieldMappingReq=='N'){
             getElementByIdOrByName("toCodeList").disabled=true;
             getElementByIdOrByName("toCodeList_textbox").disabled=true;
             getElementByIdOrByName("toAnswerId").disabled=true;
             getElementByIdOrByName("addButton").focus();
       }else{
             getElementByIdOrByName("toCodeList").disabled=false;
             getElementByIdOrByName("toCodeList_textbox").disabled=false;
             getElementByIdOrByName("toAnswerId").disabled=false;
             getElementByIdOrByName("toCodeList_textbox").focus();
             if(!getElementByIdOrByName("toAnswerId").disabled)
                    getElementByIdOrByName("toAnswerId").focus();
       }
}


function populateToCodeDesc(){
       var e = getElementByIdOrByName("toCodeList");
       var codevalue = e.options[e.selectedIndex].text;
       var toCodeDesc = codevalue.substring(codevalue.indexOf(": ")+1);
       getElementByIdOrByName("toCodeDesc").innerHTML = toCodeDesc;
       getElementByIdOrByName("addButton").focus();
}



function checkBeforeSubmitNextAnswerMapping(){

       $j(".infoBox").hide();

       var errors = new Array();
       var index = 0;
       var isError = false;

       var answerMappingReq = getElementByIdOrByName("answerMappingReq");
       var toCode = getElementByIdOrByName("toCodeList");
       var toDataType = getElementByIdOrByName("toDataTypeValue");
       var toAnswer = getElementByIdOrByName("toAnswerId");
       var toValueSet = getElementByIdOrByName("toCodeSet").innerHTML;

       if(answerMappingReq != null && answerMappingReq.value.length == 0){
             errors[index++] = "Select Map Answer";
             getElementByIdOrByName("answerMappingReqLabel").style.color="990000";
             isError = true;
       }
       else{
             getElementByIdOrByName("answerMappingReqLabel").style.color="black";
       }
       if(answerMappingReq.value != "N"){ // If Question mapping is yes or null then select to code option.

             if((toCode != null && toCode.value.length == 0) && (toAnswer != null && toAnswer.value.length == 0)){
                    errors[index++] = "Select To Answer";
                    getElementByIdOrByName("toCodeListL").style.color="990000";
                    isError = true;
                    /*       getElementByIdOrByName("toCodeSetLabel").style.color="990000"; */// label for To Code set and To Answer
             }

             /*
             if(!(toDataType.innerHTML=='NUMERIC') &&(toValueSet != null && toValueSet.length == 0))
             {
                    errors[index++] = "Select To Value Set";
                    getElementByIdOrByName("toCodeSetLabel").style.color="990000";
             }*/
             else if ((toDataType.innerHTML=='NUMERIC') && toDataType!=null && !isDecimal(toAnswer.value)){
                    errors[index++] = "Numeric value is required for To Answer";
                    getElementByIdOrByName("toCodeListL").style.color="990000";
                    isError = true;
             }else{
                    /*getElementByIdOrByName("toCodeListL").style.color="black";*/
                    getElementByIdOrByName("toCodeListL").style.color="black";
             }

              /*getElementByIdOrByName("answerMappingReqLabel").style.color="black";*/
       }/*else{
             getElementByIdOrByName("answerMappingReqLabel").style.color="black";
    }*/

       if(isError){
             displayGlobalErrorMessage(errors);
       }

       return isError;
}

function isDecimal(pString)
{
       //  Establish a pattern:  one or more digits.
       var varPattern = /^-{0,1}\d*\.{0,1}\d+$/;
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

function getCaseCount(){
       var index = 0;
       var errors = new Array();
       var e = getElementByIdOrByName("conditionListId");
       if(e!=null){
	       var selectedCondition = e.options[e.selectedIndex];
	       if((selectedCondition != null && selectedCondition.value.length == 0 ) || typeof(selectedCondition) == 'undefined' || selectedCondition==null){
	             errors[index++] = "You must select a condition prior to get a case count. Please select a condition and try again.";
	             getElementByIdOrByName("ConditionListL").style.color="990000";
	             getElementByIdOrByName("caseCount").innerHTML="";
	             displayGlobalErrorMessage(errors);
	             getElementByIdOrByName("conditionListId_textbox").focus();
	             return false;
	       }else{
	             getElementByIdOrByName("conditionListId").style.color="black";
	             getElementByIdOrByName("ConditionListL").style.color="black";
	             //Populate selected condition in case of page reload
	             getElementByIdOrByName("conditionListId_textbox").value = selectedCondition.text;
	
	             getElementByIdOrByName("globalFeedbackMessagesBar").innerHTML="";
	             JPortPage.getPublicHealthCaseCount(selectedCondition.value, function(data){
	                    getElementByIdOrByName("caseCount").innerHTML=data[0];
	                    getElementByIdOrByName("notificationReqApproval").innerHTML=data[1];
	                    getElementByIdOrByName("notificationToProcessCount").innerHTML=data[2];
	                    getElementByIdOrByName("totalCasesRemaining").innerHTML=data[3];
	                    if(data[1]==0 && data[2]==0 && data[3]>0)
	                           getElementByIdOrByName("btnPreRun").disabled=false;
	                    else
	                           getElementByIdOrByName("btnPreRun").disabled=true;
	             });
	
	             return true;
	       }
       }
}
//disable or enable Run Conversation and Enter No. of cases text box based on success or failure of PreRun. 
function disableConvBtn(){
       var remainingCases = getElementByIdOrByName("totalCasesRemaining").innerHTML;
       var isSuccess=$j("#preRunSuccess").attr("value");
       if(isSuccess=="Complete"){
             getElementByIdOrByName("btnRunConversion").disabled=false; 
             getElementByIdOrByName("numberOfCasesToMigrate").disabled=false;
       }else{
             getElementByIdOrByName("btnRunConversion").disabled=true;
             getElementByIdOrByName("numberOfCasesToMigrate").disabled=true;
       }

       var prodSuccess=$j("#prodRunSuccess").attr("value");
       if(prodSuccess=="Complete"){
             getElementByIdOrByName("success").innerHTML="Porting Complete";
             getElementByIdOrByName("btnRunConversion").disabled=true;
             getElementByIdOrByName("numberOfCasesToMigrate").disabled=true;
             getElementByIdOrByName("btnPreRun").disabled=true;
             if(getElementByIdOrByName("conditionListId")!=null)
            	 getElementByIdOrByName("conditionListId").disabled=true;
             if(getElementByIdOrByName("getCaseCnt")!=null)
            	 getElementByIdOrByName("getCaseCnt").disabled=true;

             var e = getElementByIdOrByName("conditionListId");
             if(e!=null){
	             var selectedCondition = e.options[e.selectedIndex];
	             getElementByIdOrByName("conditionListId_textbox").value = selectedCondition.text;
	             getElementByIdOrByName("conditionListId_textbox").disabled=true;
       		 }
       }
}

//disable PreRun Button , Enter No. of cases text box and Run Conversion button on change of condition.
function disableBtns(){

       getElementByIdOrByName("btnPreRun").disabled=true;
       getElementByIdOrByName("numberOfCasesToMigrate").disabled=true;
       getElementByIdOrByName("btnRunConversion").disabled=true;

}
