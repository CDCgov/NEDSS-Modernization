function manageQuestionReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    // basic info required fields
    var questionType = getElementByIdOrByName("questionType");
    var quesIdent = getElementByIdOrByName("questionIdentifier");
    var quesNm= getElementByIdOrByName("questionNm");
    //var group= getElementByIdOrByName("group");
    var subGroup= getElementByIdOrByName("subGroup");
    var subGroupCon = getElementByIdOrByName("subGroupCon");
    var subGroupIxs = getElementByIdOrByName("subGroupIxs");
    var desc= getElementByIdOrByName("desc");
    var dataType= getElementByIdOrByName("dataType");
    var displayControl = getElementByIdOrByName("defaultDisplayControl").value; 
    var dataTypeVal = "";
    if (dataType != null) {
       dataTypeVal = dataType.value;
    }
    var defValue= getElementByIdOrByName("defaultValue");
    
    // user interface required fields
    var quesLab= getElementByIdOrByName("questionLabel");
    var quesToolTip= getElementByIdOrByName("questionToolTip");
   // var defDispCont= getElementByIdOrByName("defaultDisplayControl");
    
    // data mart required fields
    var defLabRpt = getElementByIdOrByName("defaultLabelReport");
    var dmTableNm = getElementByIdOrByName("rdbTableNm");
    var dmColumnNm = getElementByIdOrByName("rdbcolumnNm");
	var dataMartColumnNm = getElementByIdOrByName("dataMartNm");
	
	if(defLabRpt != null)
	if(defLabRpt.value.indexOf(",")!=-1){
		errors[index++]="Default Label in Report cannot contain the special character ,";
		isError=true;
	}

	if(dmColumnNm){
		if(dmColumnNm!=null && dmColumnNm!='undefined' && dmColumnNm.value!=null && dmColumnNm.value.match(/^\d/)){
			errors[index++]="RDB Column Name cannot start with a number";
			isError=true;
		}
	}
	if(dataMartColumnNm){
		if(dataMartColumnNm!=null && dataMartColumnNm!='undefined' && dataMartColumnNm.value!=null){
			if(dataMartColumnNm.value.match(/^\d/)){
				errors[index++]="Data Mart Column Name cannot start with a number";
				isError=true;
			}
			
			if(dataMartColumnNm.value.endsWith('_1') || dataMartColumnNm.value.endsWith('_2') || dataMartColumnNm.value.endsWith('_3')
					|| dataMartColumnNm.value.endsWith('_4') || dataMartColumnNm.value.endsWith('_5') || dataMartColumnNm.value.endsWith('_ALL')
					|| dataMartColumnNm.value.endsWith('_GT1_IND') || dataMartColumnNm.value.endsWith('_GT2_IND') || dataMartColumnNm.value.endsWith('_GT3_IND') || dataMartColumnNm.value.endsWith('_GT4_IND') || dataMartColumnNm.value.endsWith('_GT5_IND') || dataMartColumnNm.value.endsWith('_DETAIL') || dataMartColumnNm.value.endsWith('_QEC')){
						errors[index++]="Data mart column names ending in _1, _2, _3, _4, _5, _ALL, _GT1_IND, _GT2_IND, _GT3_IND, _GT4_IND, _GT5_IND, _DETAIL, _QEC are not allowed due to potential conflicts in dynamically created data marts";
				isError=true;
			}
			
			if(dataMartColumnNm.value=='PROGRAM_JURISDICTION_OID' || dataMartColumnNm.value=='INVESTIGATION_CREATE_DATE' || dataMartColumnNm.value=='INVESTIGATION_CREATED_BY' || dataMartColumnNm.value=='INVESTIGATION_LAST_UPDTD_DATE' || dataMartColumnNm.value=='INVESTIGATION_LAST_UPDTD_BY' || dataMartColumnNm.value=='EVENT_DATE' || dataMartColumnNm.value=='EVENT_DATE_TYPE' || dataMartColumnNm.value=='LABORATORY_INFORMATION' || dataMartColumnNm.value=='EARLIEST_SPECIMEN_COLLECT_DATE' || dataMartColumnNm.value=='NOTIFICATION_STATUS' || dataMartColumnNm.value=='DISEASE_CD' || dataMartColumnNm.value=='DISEASE' || dataMartColumnNm.value=='JURISDICTION_NM' || dataMartColumnNm.value=='PATIENT_COUNTY_CODE' || dataMartColumnNm.value=='PROGRAM_AREA' || dataMartColumnNm.value=='NOTIFICATION_LAST_UPDATED_DATE' ){
						errors[index++]="The following Data mart column names are not allowed due to conflicts in dynamically created data marts: PROGRAM_JURISDICTION_OID, INVESTIGATION_CREATE_DATE, INVESTIGATION_CREATED_BY, INVESTIGATION_LAST_UPDTD_DATE, INVESTIGATION_LAST_UPDTD_BY, EVENT_DATE,EVENT_DATE_TYPE, LABORATORY_INFORMATION, EARLIEST_SPECIMEN_COLLECT_DATE, NOTIFICATION_STATUS, DISEASE_CD, DISEASE,JURISDICTION_NM, PATIENT_COUNTY_CODE, PROGRAM_AREA, NOTIFICATION_LAST_UPDATED_DATE.";
				isError=true;
			}
		
		}
		
	}
	
    
    // messaging required fields
    var msgVariableId= getElementByIdOrByName("msgVariableId");
    var msgLabel= getElementByIdOrByName("msgLabel");
    var hl7Datatype= getElementByIdOrByName("hl7Datatype");
    var hl7Segment= getElementByIdOrByName("hl7Segment");
    var groupNbr= getElementByIdOrByName("groupNbr");
    var msgcodeSysName= getElementByIdOrByName("msgcodeSysName");
    
    // util props
    var acionMode = getElementByIdOrByName("actionId");
    var actionModeVal = acionMode.value;    
    var nndIndY = getElementByIdOrByName("nndIndY");
    
    // verify whether elements have data filled in
        
    // for create mode only    
    if(actionModeVal != null &&actionModeVal == 'Create')
    {
        // question type
        if (questionType != null  && questionType.value.length == 0) {
            errors[index++] = "Question Type is required";
            getElementByIdOrByName("questionTypeL").style.color="990000";
            isError = true;
        }
        else {
           getElementByIdOrByName("questionTypeL").style.color="black";
        }
         
        // unique identifier
        if (questionType != null  && questionType.value != 'LOCAL' && 
                (quesIdent != null && quesIdent.value.length == 0)) {
            errors[index++] = "Unique ID is required";
            getElementByIdOrByName("questionIdentifierL").style.color="990000";
            isError = true;
        }
        else {
           getElementByIdOrByName("questionIdentifierL").style.color="black";
        }
    }
    
    // unique name 
    if(quesNm != null && quesNm.value.length == 0) {
        errors[index++] = "Unique Name is required";
        getElementByIdOrByName("questionNmL").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("questionNmL").style.color="black";
    }
    
 // group
    /*if(group != null && group.value.length == 0) {
        errors[index++] = "Group is required";
        getElementByIdOrByName("groupL").style.color="990000";
        isError = true;
    }
    else {
       getElementByIdOrByName("groupL").style.color="black";
    }*/
    // subgroup
    if(subGroup != null && subGroup.value.length == 0) {
        errors[index++] = "Subgroup is required";
        getElementByIdOrByName("subGroupL").style.color="990000";
        isError = true;
    }
    else {
       getElementByIdOrByName("subGroupL").style.color="black";
    }
    
    // description
    if(desc != null && desc.value.length == 0) {
        errors[index++] = "Description is required";
        getElementByIdOrByName("descL").style.color="990000";
        isError = true;
    }
    else if (desc != null && desc.value.length > 2000) {
        errors[index++] = "Description cannot contain more than 2000 characters.";
        getElementByIdOrByName("descL").style.color="990000";
        isError = true;
    }
    else {
       getElementByIdOrByName("descL").style.color="black";
    }
    
    // data type
    if(dataType != null && dataType.value.length == 0) {
            errors[index++] = "Data Type is required";
            getElementByIdOrByName("dataTypeL").style.color="990000";
            isError = true;
        }
        else {
           getElementByIdOrByName("dataTypeL").style.color="black";
    }
    
    // datatype specific validations 
    var dataTypeElt = getElementByIdOrByName("dataType");
    var dataTypeVal = dataTypeElt.value;
    
    // for coded data type
    if(dataTypeVal == 'CODED'){
        var  valSet = getElementByIdOrByName("valSet");
        if(valSet != null && valSet.value.length == 0) {
            errors[index++] = "Value Set is required for the data type Coded";
            getElementByIdOrByName("valSetL").style.color="990000";
            isError = true;
        } else {
            getElementByIdOrByName("valSetL").style.color="black";
        }
        
        /*  FIXME: default value is not a required value for coded data type
	        var defaultValue = getElementByIdOrByName("defaultValueCoded");
	        if (defaultValue != null && defaultValue.value.length == 0) {
	            errors[index++] = "Default Value is required for the data type Coded";
	            getElementByIdOrByName("defaultValueCodedL").style.color="990000";
	            isError = true;
	        } else {
	            getElementByIdOrByName("defaultValueCodedL").style.color="black";
	        }
        */
        
    }
    
    // for date data type
    if(dataTypeVal == 'DATE'){
        var  maskD = getElementByIdOrByName("maskD");
        if(maskD != null && maskD.value.length == 0) {
            errors[index++] = "Mask is required for the data type Date";
            getElementByIdOrByName("maskDL").style.color="990000";
            isError = true;
        } else {
            getElementByIdOrByName("maskDL").style.color="black";
        }
    }
    
    // for date/time data type 
    if(dataTypeVal == 'DATETIME'){
        var  maskDT = getElementByIdOrByName("maskDT");
        if(maskDT != null && maskDT.value.length == 0){
            errors[index++] = "Mask is required for the data type Date/Time";
            getElementByIdOrByName("maskDTL").style.color="990000";
            isError = true;
        } else {
            getElementByIdOrByName("maskDTL").style.color="black";
        }
    }
    
    // for numeric data types
    if(dataTypeVal != null && dataTypeVal == 'NUMERIC'){
        var fieldLenNum = getElementByIdOrByName("fieldLenNum");
        var fieldLenNumVal = fieldLenNum.value;
        var maskN = getElementByIdOrByName("maskN");
        
        if(maskN != null && maskN.value.length == 0){
            errors[index++] = "Mask is required for the data type Numeric";
            getElementByIdOrByName("maskNL").style.color="990000";
            isError = true;
        }
        else {
            getElementByIdOrByName("maskNL").style.color="black";
        }

        // check for valid field length if the numeric mask is "NUM"., no defined mask
        if (maskN != null && maskN.value.length > 0 && maskN.value == "NUM") {
            if(fieldLenNum != null && fieldLenNum.value.length == 0){
                errors[index++] = "Field Length is required for the data type Numeric with no defined mask";
                getElementByIdOrByName("fieldLenNumL").style.color="990000";
                isError = true;
            }
            else if(fieldLenNum != null && fieldLenNumVal > 300) {
                errors[index++] = "Field Length cannot contain more than 300 characters";
                getElementByIdOrByName("fieldLenNumL").style.color="990000";
                isError = true;
            }
            else {
                getElementByIdOrByName("fieldLenNumL").style.color="#000";
            }
        }
        else {
	        getElementByIdOrByName("fieldLenNumL").style.color="#CCC";
	    }
	    
	    // check for required fields if related unit is present.
	    if ($j("input#relUnitY") != null && $j("input#relUnitN") != null) {
	       var relUnitY = $j("input#relUnitY").attr("checked");
	       var relUnitN = $j("input#relUnitN").attr("checked");
	        
	       if ($j("input#relUnitY") != null && $j("input#relUnitN") != null) {
		    	 var relUnitY = $j("input#relUnitY").attr("checked");
			     var relUnitN = $j("input#relUnitN").attr("checked");
			        
			     if (relUnitY == true && relUnitN == false){

			    	
			    	 // unit value set
		            var unitValSet = $j("select#unitsTypeN");
		            if ($j(unitValSet) != null && jQuery.trim($j(unitValSet).val()).length == 0) {
		                errors[index++] = "Units Type is required";
		                $j("span#unitsTypeL").css("color", "990000");
		                isError = true;
		            }
		            else {
		                $j("span#unitsTypeL").css("color", "#000");
		            }
		            
		            var unitsTypeN = getElementByIdOrByName("unitsTypeN");
		            if(unitsTypeN != null ){
		            	if(unitsTypeN.value == 'CODED'){
		            		var relatedUnitsV = getElementByIdOrByName("relatedUnitsV");
		            		if(relatedUnitsV != null && relatedUnitsV.value.length == 0)
		            		{
		            			 errors[index++] = "Related Units Value Set is required for the data type Numeric";
		                         getElementByIdOrByName("relatedUnitsVL").style.color="990000";
		                         isError = true;
		            		}else 
		            		{
		            			getElementByIdOrByName("relatedUnitsVL").style.color="black";
		            		}
		            		
		            	}else if(unitsTypeN.value == 'LITERAL'){
		            		var literalUnits = getElementByIdOrByName("literalUnits");
		            		if(literalUnits != null && literalUnits.value.length == 0)
		            		{
		            			 errors[index++] = "Literal Units Value is required for the data type Numeric";
		                         getElementByIdOrByName("literalUnitsL").style.color="990000";
		                         isError = true;
		            		}else 
		            		{
		            			getElementByIdOrByName("literalUnitsL").style.color="black";
		            		}
		            	}
		               
		            }
			     }
	       } 
	    }
    }
    
    // for data type of TEXT
    if(dataTypeVal == 'TEXT'){
        var  fieldLenTxt = getElementByIdOrByName("fieldLenTxt");
        var  fieldLenTxtVal = fieldLenTxt.value;
        var maskT = getElementByIdOrByName("maskT");
        
        if (maskT != null && maskT.value.length == 0){
            errors[index++] = "Mask is required for the data type Text";
            getElementByIdOrByName("maskTL").style.color="990000";
            isError = true;
        }
        else {
            getElementByIdOrByName("maskTL").style.color="black";
        }
        
        // check for valid field length if the text mask is "TXT"., no defined mask
        if (maskT != null && maskT.value.length > 0 && maskT.value == "TXT") {
            if(fieldLenTxt != null && fieldLenTxt.value.length == 0){
                errors[index++] = "Field Length is required for the data type Text with no defined mask";
                getElementByIdOrByName("fieldLenTxtL").style.color="990000";
                isError = true;
            }
            else if(fieldLenTxt != null && fieldLenTxtVal > 2000) {
                errors[index++] = "Field Length cannot contain more than 2000 characters";
                getElementByIdOrByName("fieldLenTxtL").style.color="990000";
                isError = true;
            }
            else {
                getElementByIdOrByName("fieldLenTxtL").style.color="#000";
            }
        }
        else {
            getElementByIdOrByName("fieldLenTxtL").style.color="#CCC";
        }
    }
    
    // default label on screen
    if(quesLab != null && quesLab.value.length == 0) {
        errors[index++] = "Label on Screen is required";
        getElementByIdOrByName("questionLabelL").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("questionLabelL").style.color="black";
    }
    
    // default tool tip
    if(quesToolTip != null && quesToolTip.value.length == 0) {
        errors[index++] = "Tool Tip is required";
        getElementByIdOrByName("questionToolTipL").style.color="990000";
        isError = true;
    }
    else if(quesToolTip != null && quesToolTip.value.length > 2000) {
        errors[index++] = "Tool Tip cannot be more than 2000 characters";
        getElementByIdOrByName("questionToolTipL").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("questionToolTipL").style.color="black";
    }
    
    if(displayControl == null || displayControl=='') {
        errors[index++] = "Display Control is required";
        getElementByIdOrByName("defaultDisplayControlL").style.color="990000";
        isError = true;
    }
    else {
       getElementByIdOrByName("defaultDisplayControlL").style.color="black";
    }
  
    
    if(!(displayControl>=1024 && displayControl<=1026) ){
    	  
        // default label in report
        if(defLabRpt != null && defLabRpt.value.length == 0)
        {
            errors[index++] = "Default Label in Report is required";
            getElementByIdOrByName("defaultLabelReportL").style.color="990000";
            isError = true;
        }
        else {
            getElementByIdOrByName("defaultLabelReportL").style.color="black";
        }
    	
    	// rdb table name
    	
	    if(dmTableNm != null && dmTableNm.value.length == 0)
	    {
	        errors[index++] = "Default RDB Table Name is required";
	        getElementByIdOrByName("rdbTableNmL").style.color="990000";
	        isError = true;
	    }
	    else {
	        getElementByIdOrByName("rdbTableNmL").style.color="black";
	    }
    
	    // rdb column name
	    if(dmColumnNm != null && dmColumnNm.value.length == 0)
	    {
	        errors[index++] = "RDB Column Name is required";
	        getElementByIdOrByName("rdbcolumnNmL").style.color="990000";
	        isError = true;
	    }
	    else {
	        getElementByIdOrByName("rdbcolumnNmL").style.color="black";
	    }
    
	    // messaging properties
	    var nndIndY = $j("input#nndIndY");
	    var nndIndN = $j("input#nndIndN");
	    if ($j(nndIndY).attr("checked") == true && $j(nndIndN).attr("checked") == false) {
	        if(msgVariableId != null && msgVariableId.value.length == 0)
	        {
	            errors[index++] = "Message Variable ID is required";
	            getElementByIdOrByName("msgVariableIdL").style.color="990000";
	            isError = true;
	        }else {
	            getElementByIdOrByName("msgVariableIdL").style.color="black";
	        }
	        if(msgLabel != null && msgLabel.value.length == 0)
	        {
	            errors[index++] = "Label in Message is required";
	            getElementByIdOrByName("msgLabelL").style.color="990000";
	            isError = true;
	        }
	        else {
	            getElementByIdOrByName("msgLabelL").style.color="black";
	        }
	        // added by jayasudha to validate the code system name 
	        if(msgcodeSysName != null && msgcodeSysName.value.length == 0)
	        {
	            errors[index++] = "Code System Name is required";
	            getElementByIdOrByName("msgCodeSysLable").style.color="990000";
	            isError = true;
	        }
	        else {
	            getElementByIdOrByName("msgCodeSysLable").style.color="black";
	        }
	        
	        
	        if(hl7Datatype != null && hl7Datatype.value.length == 0)
	        {
	            errors[index++] = "HL7 Data Type is required";
	            getElementByIdOrByName("hl7DatatypeL").style.color="990000";
	            isError = true;
	        }
	        else {
	            getElementByIdOrByName("hl7DatatypeL").style.color="black";
	        }
	        if(hl7Segment != null && hl7Segment.value.length == 0)
	        {
	            errors[index++] = "HL7 Segment is required";
	            getElementByIdOrByName("hl7SegmentL").style.color="990000";
	            isError = true;
	        }
	        else {
	            getElementByIdOrByName("hl7SegmentL").style.color="black";
	        }
	        if(groupNbr != null && groupNbr.value.length == 0)
	        {
	            errors[index++] = "Group Number(Order Group ID) is required";
	            getElementByIdOrByName("groupNbrL").style.color="990000";
	            isError = true;
	        }
	        else {
	            getElementByIdOrByName("groupNbrL").style.color="black";
	        }
	        
	        
	        
	        
	        
	        
	    }
    
    }
    // admin comment
    var adminComment =  getElementByIdOrByName("adminComment");
    if (adminComment != null && adminComment.value.length > 2000) {
        errors[index++] = "Administrative Comments cannot be more than 2000 characters";
        getElementByIdOrByName("adminCommentL").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("adminCommentL").style.color="black";
    }
    
    error = numValidate(errors,index,isError);  
    // finally, display the error messages if there are any.          
    if(isError || error){
        displayGlobalErrorMessage(errors);
        isError=true;
    }   

    return isError;
}

function showDataTypeSpecificFields(forceReset) {
    var dataType = getElementByIdOrByName("dataType");
    
    // css class assigned to all fields that are used to
    // gather data when a data type (coded, numeric etc...) is selected
    var cssClass = "questionTypeRelatedField";
    
    if (dataType != null && dataType != undefined) {
        // css class assigned to fields that are specific to a particular data type
        var dataTypeSpecificCssClass = dataType.value.toLowerCase() + "QuestionType";
        
        // initially disable and hide all data type fields 
        $j("tr." + cssClass).find(":input").attr("disabled", true);
        $j("tr." + cssClass).hide();
        
        // reset inputs for all data type fields if requested by user
        if (forceReset) {
            $j("tr." + cssClass).find(":input[type!=radio]").val("");

        }
        
        // now, only show fields that are specific to the data type selected.
        $j("tr." + dataTypeSpecificCssClass).find(":input").attr("disabled", false);
        $j("tr." + dataTypeSpecificCssClass).show();
        showAllowForEntry(this);
    }
    else {
        // initially hide fields that have 'questionTypeRelatedField' assigned to it
        $j("tr." + cssClass).hide();
    }
    
    // hide related units for non-coded data types
    if (dataType.value.toLowerCase() != 'numeric') {
        displayRelatedUnit(false);
    }
}

function showAllowForEntry(forceReset) {
    var defaultTypeValue = getElementByIdOrByName("defaultValueCoded");
     var defaultType = getElementByIdOrByName("dataType").value;
     var dataLocation = getElementByIdOrByName("dataLocation").value;
    var defaultTypeSpecificCssClass = "defaultType";
    $j("tr." + defaultTypeSpecificCssClass).hide();
    $j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", true);
    if(defaultType == 'CODED'){
   	if(getElementByIdOrByName("valSet").value !=''){
   	
   	    var options = $j("#defaultValueCoded").find("option");
	    for (var i = 0; i < options.length; i++) {
		if (options[i].value == "OTH" && !(dataLocation.indexOf('ANSWER_TXT')==-1)) {
		    $j("tr." + defaultTypeSpecificCssClass).show();
		    $j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", false);
		    break;
		}
	    }
       }
       else{
       	var options = $j("#defaultValueCoded").find("option");
	for (var i = 0; i < options.length; i++) {
		getElementByIdOrByName("defaultValueCoded").remove(options[i]);
    	 }
         $j("tr." + defaultTypeSpecificCssClass).hide();
	 $j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", true);
       }
	     
    }
    
}

function showUnitTypeSpecificFields(unitType){
	 var cssClass = "questionTypeRelatedField";
	 $j("#literalUnits").val("");
	 $j("#relatedUnitsV").val("");
	 if (unitType != null && unitType != undefined) {
	        // css class assigned to fields that are specific to a particular data type
	        var unitTypeSpecificCssClass = unitType.value.toLowerCase() + "QuestionType";
	        var val = unitType.value;
	        if (val == "CODED") {
	        	// now, only show fields that are specific to the data type selected.
	        	$j("#literalUnits").val("");
		        $j("tr." + "codedUnitType").find(":input").attr("disabled", false);
		        $j("tr." + "codedUnitType").show();
		        $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
		        $j("tr." + "literalUnitType").hide();
           }
           else {
        	   $j("#relatedUnitsV").val("");
           		$j("tr." + "codedUnitType").find(":input").attr("disabled", true);
		        $j("tr." + "codedUnitType").hide();
		        $j("tr." + "literalUnitType").find(":input").attr("disabled", false);
		        $j("tr." + "literalUnitType").show();
           }
	    }
	    else {
	        // initially hide fields that have 'questionTypeRelatedField' assigned to it
	        $j("tr." + cssClass).hide();
	    }
}

function initializeDataTypeSpecificFields()
{
    var dataType = getElementByIdOrByName("dataType");

    // handle misc special cases in data types
    if (dataType.value.toLowerCase() == 'numeric') {
        handleNumericDataTypeSelection();
    }
    else if (dataType.value.toLowerCase() == 'text') {
        handleTextDataTypeSelection();
    }
    else if (dataType.value.toLowerCase() == 'date') {
        handleDateDataTypeSelection();
    }
    else if (dataType.value.toLowerCase() == 'datetime') {
        handleDateTimeDataTypeSelection();
    }
}

function handleNumericDataTypeSelection()
{
    // default the mask for this numeric field to 'Integer' 
    // i.e., no mask.
    var maskElt = $j("select#maskN");
    var maskEltTextBox = $j("input[name='maskN_textbox']");
    
    if (maskElt != null) {
        var options = $j(maskElt).find("option");
        for (var i = 0; i < options.length; i++) {
            if (options[i].value == "NUM") {
                $j(maskElt).val(options[i].value);
                $j(maskEltTextBox).val($j(options[i]).html());
                break;
            }
        }
    }
    // enable the field length element for this numeric field
    $j("input#fieldLenNum").attr("disabled", false);
    $j("span#fieldLenNumL").css("color", "#000");
    $j($j("span#fieldLenNumL").siblings().get(0)).css("color", "#F00");
}

function handleNumericMaskChange(numericMaskElt)
{
    // enable/disable the field length element for this numeric field
    if (numericMaskElt != null) {
        var val = numericMaskElt.value;
        if (val == "NUM") {
            $j("input#fieldLenNum").attr("disabled", false);
            $j("span#fieldLenNumL").css("color", "#000");
            $j($j("span#fieldLenNumL").siblings().get(0)).css("color", "#F00");
        }
        else {
            $j("input#fieldLenNum").val("");
            $j("input#fieldLenNum").attr("disabled", true);
            $j("span#fieldLenNumL").css("color", "#CCC");
            $j($j("span#fieldLenNumL").siblings().get(0)).css("color", "#CCC");
        }
    }
}

function handleTextDataTypeSelection()
{
    // default the mask for this numeric field to 'Integer' 
    // i.e., no mask.
    var maskElt = $j("select#maskT");
    var maskEltTextBox = $j("input[name='maskT_textbox']");
    
    if (maskElt != null) {
        var options = $j(maskElt).find("option");
        for (var i = 0; i < options.length; i++) {
            if (options[i].value == "TXT") {
                $j(maskElt).val(options[i].value);
                $j(maskEltTextBox).val($j(options[i]).html());
                break;
            }
        }
    }
    
    // enable the field length element for this numeric field
    $j("input#fieldLenTxt").attr("disabled", false);
}

function handleTextMaskChange(textMaskElt)
{
    // enable/disable the field length element for this text field
    if (textMaskElt != null) {
        var val = textMaskElt.value;
        if (val == "TXT") {
            $j("input#fieldLenTxt").attr("disabled", false);
            $j("span#fieldLenTxtL").css("color", "#000");
            $j($j("span#fieldLenTxtL").siblings().get(0)).css("color", "#F00");
        }
        else {
            $j("input#fieldLenTxt").attr("disabled", true);
            $j("span#fieldLenTxtL").css("color", "#CCC");
            $j($j("span#fieldLenTxtL").siblings().get(0)).css("color", "#CCC");
        }
    }
}

function handleDateDataTypeSelection()
{
    // default the mask for this numeric field to 'MM/DD/YYYY' 
    var maskElt = $j("select#maskD");
    var maskEltTextBox = $j("input[name='maskD_textbox']");
    
    if (maskElt != null) {
        var options = $j(maskElt).find("option");
        for (var i = 0; i < options.length; i++) {
            // select the only mask available.
            if (jQuery.trim(options[i].value).length != "") {
                $j(maskElt).val(options[i].value);
                $j(maskEltTextBox).val($j(options[i]).html());
                break;
            }
        }
    }
    getElementByIdOrByName('noFutureEntry').checked="checked";
}

function handleDateTimeDataTypeSelection()
{
    // default the mask for this numeric field to 'MM/DD/YYYY 00:00:00:00' 
    var maskElt = $j("select#maskDT");
    var maskEltTextBox = $j("input[name='maskDT_textbox']");
    
    if (maskElt != null) {
        var options = $j(maskElt).find("option");
        for (var i = 0; i < options.length; i++) {
            // select the only mask available.
            if (jQuery.trim(options[i].value).length != "") {
                $j(maskElt).val(options[i].value);
                $j(maskEltTextBox).val($j(options[i]).html());
                break;
            }
        }
    }
}


/**
 * Update the default values input element identifier by 'defaultValueEltId'
 *  with values corresponding to the valueSetName received.
 */
function updateDefaultValuesForValueSet(valueSetName, defaultValueEltId)
{
    var defaultValuesElt = getElementByIdOrByName(defaultValueEltId);
    if (defaultValuesElt != null) {
	    // remove existing values
	    dwr.util.removeAllOptions(defaultValuesElt);
	    getElementByIdOrByName('defaultValueCoded').value="";
	    // empty the corresponding text box
	    $j(defaultValuesElt).parent().parent().find(":input[type='text']").val("");
	    
        // insert new values
	    if (valueSetName != null && valueSetName != undefined && jQuery.trim(valueSetName) != "") {
	        JManageQuestionsForm.getDefaultValuesForValueSet(valueSetName, function(data) {
	            dwr.util.addOptions(defaultValuesElt, data, "key", "value");
	            flag = false;
	            for(x in data){
	             	var newArray = data[x];
	             	for(y in newArray){
	             	     if(newArray[y]=="OTH"){
	              		flag = true;	              		
	              		
	              	     }
	              	}	
	            }
	        var defaultTypeSpecificCssClass = "defaultType";    
	        getElementByIdOrByName("defaultValueHidden").value="reset";
	        var dataLocation = getElementByIdOrByName("dataLocation").value;
		    if(flag && !(dataLocation.indexOf('ANSWER_TXT')==-1)){
		    	$j("tr." + defaultTypeSpecificCssClass).show();
				$j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", false);
		    	//getElementByIdOrByName('yesAllowEntry').checked="checked";
				getElementByIdOrByName('noAllowEntry').checked="checked";
		    }
		    else{
		    	$j("tr." + defaultTypeSpecificCssClass).hide();
				$j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", true);
		    	getElementByIdOrByName('noAllowEntry').checked="checked";
		    }
	        });
	    }
    }
}

function updateRDBTableNames()
{
	//var takeselectbox = getElementByIdOrByName("rdbTableNm_button");
	// if(takeselectbox!== null && takeselectbox!== undefined) {

	//$(takeselectbox).remove();
	//}
    var rdbTables = getElementByIdOrByName("rdbTableNm");
    var rdbTablesHid = getElementByIdOrByName("rdbTableNmHid");
    var groupNm = getElementByIdOrByName("subGroup");
	var groupNmVal = "";
    if(groupNm != null){
    	groupNmVal = groupNm.value;
    }
	var selectedrdbTables = getElementByIdOrByName("rdbTableNmPrePopVal");
    var selectedrdbTablesVal = "";
	if(selectedrdbTables!=null){
		selectedrdbTablesVal = selectedrdbTables.value;
	}
    var valueSetName = "";
	//alert(rdbTables);
    if (rdbTables != null) {
	    // remove existing values
	    //dwr.util.removeAllOptions(rdbTables);
	    getElementByIdOrByName('rdbTableNm').value="";
	    // empty the corresponding text box
	    $j(rdbTables).parent().parent().find(":input[type='text']").val("");

	    /*if(groupNm!=null && groupNm=='GROUP_CON'){
	    	valueSetName="NBS_PH_DOMAINS_CR";
	    }
	    else if(groupNm!=null && groupNm=='GROUP_IXS'){
	    	valueSetName="NBS_PH_DOMAINS_IXS";
	    }
	    else{*/
	    	valueSetName="NBS_PH_DOMAINS";
	    //}
        // insert new values
	    if (valueSetName != null && valueSetName != undefined && jQuery.trim(valueSetName) != "") {
	        JManageQuestionsForm.getCodedValueBycodeAndDesc(groupNmVal,valueSetName, function(data) {
			//JManageQuestionsForm.getCodedValue(valueSetName, function(data) {
	            //dwr.util.addOptions(rdbTables, data, "key", "value");
	            //dwr.util.setValue(rdbTables, groupNmVal);
				//rdbTables.value=;
				for(key in data){
					if(data.hasOwnProperty(key)){
						
						if(data[key].value != null && data[key].value != ""){
							rdbTables.value = data[key].value;
							rdbTablesHid.value = data[key].value;
							rdbTables.disabled='disabled';
						} else {
							rdbTables.value = "";
							rdbTables.disabled="";
						}
					}
				}
				//alert(data[groupNmVal]);
	 			autocompTxtValuesForJSP();
	        });
		}
	   }
    }


function handleMessagingFields(doEnable) {
    if(doEnable == true){
        // enable all fields 
        $j("tr.messagingFieldTr").find(":input").attr("disabled", false);
        
        // update all fields
        $j("input#orderGroupIdValue").val("2"); $j("span#orderGroupIdSpan").html("2");
        
        var hl7SegmentValue = getElementByIdOrByName("hl7SegmentValue").value;
        if(hl7SegmentValue!=null && hl7SegmentValue.length ==0)
        	hl7SegmentValue = "OBX-3.0";

        $j("input#hl7SegmentValue").val(hl7SegmentValue);
        $j("span#hl7SegmentSpan").html(hl7SegmentValue);
        
        // update label/text colors 
        $j("tr.messagingFieldTr").find("span[title]").css("color", "#000");
        $j("tr.messagingFieldTr").find("td").css("color", "#000");
    }
    else {
        // reset all values
        
        $j("tr.messagingFieldTr").find(":input[type!=radio]").val("");
        $j("input#orderGroupIdValue").val(""); $j("span#orderGroupIdSpan").html("");
        $j("input#hl7SegmentValue").val(""); $j("span#hl7SegmentSpan").html("");
        $j("input#reqInMsgN").attr("checked", true);
        
        // disable all fields
        $j("tr.messagingFieldTr").find(":input").attr("disabled", true);
        
        // update label/text colors
        $j("tr.messagingFieldTr").find("span[title]").css("color", "#666666");
        $j("tr.messagingFieldTr").find("td").css("color", "#666666");
    }
}



function hidesec(elm){
	var ro = elm.value;
	if(ro>=1024 && ro<=1026 ){
 $j("table#subsect_dataMart").hide();
 $j("table#subsect_messaging").hide();
 }else{
	 $j("table#subsect_dataMart").show();
	 $j("table#subsect_messaging").show();
 }
 }
function handleEditCreateLoad() 
{
    var acionMode = getElementByIdOrByName("actionId");
    var actionModeVal = acionMode.value;
    
    // show/hide fields related to the dataType selected
    showDataTypeSpecificFields(false);
    
    // for create mode
    if(actionModeVal == 'Create')
    {
        // unique identifier
        var quesType = getElementByIdOrByName("questionType");
        if (quesType.value == 'LOCAL') {
            displayUniqueId(quesType);
        }
        else if (quesType.value =='PHIN') {
            displayUniqueId(quesType);
        }
        
	    if ($j("input#relUnitY").attr("checked") == true) {
	        //$j("table#subsect_relatedUnit").show();
	        displayRelatedUnit(true);
	    }
	    else if ($j("input#relUnitN").attr("checked") == true) {
	        $j("table#subsect_relatedUnit").hide();
	    }
	    
	    //$j("#questionIdvw").hide();
    }
    
    // for edit mode
    if(actionModeVal == 'Edit') {
        // update section name
        $j($j($j("div#sect_question").find("td.sectName").get(0)).find("a").get(0)).html("Edit Question");
        
        // handle related unit fields
        // note: since literal and coded unit type share the 
        // same field for transferring the value to the back end
        // following steps are necessary/
        var unitType = $j("select#unitsTypeN").val();
        if (unitType == "CODED") {
        	// clear the value of literal value field
        	$j("#literalUnits").val("");
        }
        else if (unitType == "LITERAL") {
        	// clear the value of coded value field
        	$j("#relatedUnitsV").val("");
        }
        
        var relUnitY = getElementByIdOrByName("relUnitY");
        if(relUnitY.checked == true)
        {
        	displayRelatedUnit(true);	
        }
        
        var dataType = getElementByIdOrByName("dataType");
        var dataTypeVal = dataType.value;
        var relatedUnitsSpecificCssClass = "relatedUnitsQuestionType";
        if(dataTypeVal == 'CODED'|| dataTypeVal == 'DATE' || dataTypeVal == 'TEXT')
        {
        	// clear the value of literal value field
        	$j("#unitsTypeN").val("");
        	$j("#relatedUnitsV").val("");
        	$j("#literalUnits").val("");
        	$j("tr." + relatedUnitsSpecificCssClass).find(":input").attr("disabled", true);
            $j("tr." + relatedUnitsSpecificCssClass).hide();
            $j("tr." + "codedUnitType").find(":input").attr("disabled", true);
	        $j("tr." + "codedUnitType").hide();
	        $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
	        $j("tr." + "literalUnitType").hide();	
	     
        }
        handleNumericMaskChange(getElementByIdOrByName("maskN"));
    }

    
    // messaging/nnd properties
    var nndIndY = $j("input#nndIndY");
    var nndIndN = $j("input#nndIndN");
    if ($j(nndIndY).attr("checked") == true && $j(nndIndN).attr("checked") == false) {
        handleMessagingFields(true);
    }
    else {
        handleMessagingFields(false);
    }
}

/**
 * Set the default values for related units block for a question with 
 * numeric date type.
 */
function displayRelatedUnit(isTrue)
{
	// 
	var cssClass = "questionTypeRelatedField";
	var relatedUnitsSpecificCssClass = "relatedUnitsQuestionType";
	if(isTrue == true) {
		// now, only show fields that are specific to the related unit selected.
        $j("tr." + relatedUnitsSpecificCssClass).find(":input").attr("disabled", false);
        $j("tr." + relatedUnitsSpecificCssClass).show();
        unitType = getElementByIdOrByName("unitsTypeN")
        var val = unitType.value;
        if(val!= null && val != undefined){
	        if (val == "CODED") {
	        	// now, only show fields that are specific to the data type selected.
		        $j("tr." + "codedUnitType").find(":input").attr("disabled", false);
		        $j("tr." + "codedUnitType").show();
		        $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
		        $j("tr." + "literalUnitType").hide();
	        }
	        else if (val == "LITERAL"){
	        	$j("tr." + "codedUnitType").find(":input").attr("disabled", true);
			        $j("tr." + "codedUnitType").hide();
			        $j("tr." + "literalUnitType").find(":input").attr("disabled", false);
			        $j("tr." + "literalUnitType").show();
	        }
        }
        
	}else if(isTrue == false){
        $j("#unitsTypeN").val("");
     	$j("#relatedUnitsV").val("");
     	$j("#literalUnits").val("");
		$j("tr." + relatedUnitsSpecificCssClass).find(":input").attr("disabled", true);
        $j("tr." + relatedUnitsSpecificCssClass).hide();
        $j("tr." + "codedUnitType").find(":input").attr("disabled", true);
        $j("tr." + "codedUnitType").hide();
        $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
        $j("tr." + "literalUnitType").hide();
    }
}

function displayUniqueId(quest) {
    var type = quest.id;
    var typeVal = getElementByIdOrByName(type).value;
    JManageQuestionsForm.setQuestionType(typeVal, function(data) {
        dwr.util.setValue("phinindUnit",data);
    })
            
    // handle related unit for this question, if applicable
    var relUnitY = getElementByIdOrByName("relUnitY");
    if(typeVal == 'LOCAL'){
        JManageQuestionsForm.setStateQuestionIdentifier(function(data) {
            var id1 = data[0];
            var id2 = data[1];
            var questionIdvw = getElementByIdOrByName("questionIdvw");
            var questionUnitIdvw= getElementByIdOrByName("questionUnitIdvw");
            dwr.util.setValue(questionIdvw,id1);
            dwr.util.setValue(questionUnitIdvw,id2 );

            // set the value of unit Id hidden variable                
            $j("input#questionUnitId").val(id2);
        })
    	    		 
        $j("#questionIdcr").hide();				
        $j("#questionIdvw").show();			
		       
        if(relUnitY.checked == true){
            $j("#questionUnitIdcr").hide();
            $j("#questionUnitIdvw").show();
        }
    }
    
    if(typeVal == 'PHIN') {
        $j("#questionIdvw").hide();
        $j("#questionIdcr").show();
        if(relUnitY.checked == true){
            $j("#questionUnitIdcr").show();
            $j("#questionUnitIdvw").hide();
        }
    }		
}
	
function resetHiddenValue(){	
	getElementByIdOrByName("defaultValueHidden").value="";
 }



function updateDisplayControlsForDataType(elt)
{
    var dataType = elt.value;
    if(dataType =='DATE'){
     	getElementByIdOrByName("valSet").value="";
     	getElementByIdOrByName("defaultValueCoded").value="";
     	getElementByIdOrByName("yesAllowEntry").value="";
     	getElementByIdOrByName("noAllowEntry").value="";
     	getElementByIdOrByName("defaultValueHidden").value="reset";
    }
    JManageQuestionsForm.getDefaultDisplayContl(dataType, function(data) {
        var disConId = getElementByIdOrByName("defaultDisplayControl");
        dwr.util.removeAllOptions(disConId);
        dwr.util.addOptions(disConId,data,"key","value");

        // empty the text box that may have lingering values from previous selection             
        $j(disConId).parent().parent().find(":input[type='text']").val("");
        
        // values just set
        var assoArr = new Array();
        for (var i = 0; i < disConId.options.length; i++) {
            assoArr[disConId.options[i].value] = disConId.options[i].text;
        }
        
        // set the default display control
        switch (dataType.toLowerCase()) {
            case 'coded':
                // NOTE: 1007 is the nbs_ui_component_uid code for single-select
                $j(disConId).val("1007");
                $j(disConId).parent().parent().find(":input[type='text']").val(assoArr["1007"]);
                break;
                
            case 'date':
            case 'datetime':
            case 'numeric':
            case 'text':
                // NOTE: 1008 is the nbs_ui_component_uid code for single-line-textbox
                $j(disConId).val("1008");
                $j(disConId).parent().parent().find(":input[type='text']").val(assoArr["1008"]);
                break;
                    
            default:
                break;                
        }
    })
}
	
function getDefaultValue(rad)
{
    var type = rad.id;   
    var val = rad.value;
    JManageQuestionsForm.getCodedValue(val, function(data) {
        var defValueUnit= getElementByIdOrByName("defValueUnit");
        dwr.util.removeAllOptions("defValueUnit");
        dwr.util.addOptions(defValueUnit,data,"key","value");
    })
}

function validFieldLength()
{
    $j(".infoBox").hide();
    var errors = new Array();
    var index = 0;
    var isError = false;	
}


function cancelFilter(key)
{				  	
	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
	JManageConditionForm.getAnswerArray(key1, function(data) {			  			
		revertOldSelections(key, data);
	});		  	
}

function revertOldSelections(name, value) 
{  
	if (value == null) {
		$j("input[@name="+name+"][type='checkbox']").attr('checked', true);
		$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
		return;
	}

	//step1: clear all selections
	$j("input[@name="+name+"][type='checkbox']").attr('checked', false);
	$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', false);

	//step2: check previous selections from the form
   	for (var i=0; i<value.length; i++) {
        $j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr('checked', true);
    }
    
	//step3: if all are checked, automatically check the 'select all' checkbox
	if(value.length == $j("input[@name="+name+"][type='checkbox']").parent().length) {
	   $j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
	}
}

function selectfilterCriteria()
{
	document.forms[0].action ='/nbs/ManageCondition.do?method=filterConditionLibSubmit';
	document.forms[0].submit();
}

//the following code for Queue
function attachIcons() {
    $j("#parent thead tr th a").each(function(i) 
    {
    	if($j(this).html() == 'Condition') {
            $j(this).parent().append($j("#condition"));
        }
    	if($j(this).html() == 'Code') {
            $j(this).parent().append($j("#code"));
        }
    	
        if($j(this).html() == 'Program Area') {
            $j(this).parent().append($j("#pArea"));
        }
        if($j(this).html() == 'Investigation Page') {
            $j(this).parent().append($j("#associatedPage"));
        }
        if($j(this).html() == 'NND') {
            $j(this).parent().append($j("#nndCondition"));
        }
        if($j(this).html() == 'Status') {
            $j(this).parent().append($j("#status"));
        }
        if($j(this).html() == 'Condition Family') {
            $j(this).parent().append($j("#cFamily"));
        }
        if($j(this).html() == 'Coinfection Group') {
            $j(this).parent().append($j("#cInfGroup"));
        }
    });
    
    $j("#parent").before($j("#whitebar"));
    $j("#parent").before($j("#removeFilters"));
}

function displayTooltips() 
{
	var INV111 = getElementByIdOrByName("INV111") == null ? "" : getElementByIdOrByName("INV111").value;
	var INV222 = getElementByIdOrByName("INV222") == null ? "" : getElementByIdOrByName("INV222").value;
	var INV333 = getElementByIdOrByName("INV333") == null ? "" : getElementByIdOrByName("INV333").value;
	var INV147 = getElementByIdOrByName("INV147") == null ? "" : getElementByIdOrByName("INV147").value;
	var INV163 = getElementByIdOrByName("INV163") == null ? "" : getElementByIdOrByName("INV163").value;
	var INV100 = getElementByIdOrByName("INV100") == null ? "" : getElementByIdOrByName("INV100").value;
	var NOT118 = getElementByIdOrByName("NOT118") == null ? "" : getElementByIdOrByName("NOT118").value;
	var STD111 = getElementByIdOrByName("STD111") == null ? "" : getElementByIdOrByName("STD111").value;
 	$j(".sortable a").each(function(i) {
	    var headerNm = $j(this).html();
	    if(headerNm == 'Condition') {
	    	_setAttributes(headerNm, $j(this), INV111);
	    }else if(headerNm == 'Code') {
	    	_setAttributes(headerNm, $j(this), INV222);
	    }else if(headerNm == 'Program Area') {
	        _setAttributes(headerNm, $j(this),INV147);
	    }else if(headerNm == 'NND') {
	        _setAttributes(headerNm, $j(this), INV163);
	    }else if(headerNm == 'Investigation Page') {
	        _setAttributes(headerNm, $j(this), INV100);
	    }  else if(headerNm == 'Status') {
	        _setAttributes(headerNm, $j(this),NOT118);
	    }else if(headerNm == 'Condition Family') {
	        _setAttributes(headerNm, $j(this),INV333);
	    }else if(headerNm == 'Coinfection Group') {
	        _setAttributes(headerNm, $j(this),STD111);
	    }
    });
 }

function _setAttributes(headerNm, link, colId) 
{
 	var imgObj = link.parent().find("img");
 	var toolTip = "";
 	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
 	var sortFirstStr =sortSt.substring(0,(sortSt.lastIndexOf(" in"))) ;
 	var orderCls = "SortAsc.gif";
 	var altOrderCls = "Sort Ascending";
 	var sortOrderCls = "FilterAndSortAsc.gif";
 	var altSortOrderCls = "Filter Applied with Sort Ascending";
 	if(sortSt != null && sortSt.indexOf("descending") != -1) {
 		orderCls = "SortDesc.gif";
 		altOrderCls = "Sort Descending";
 		sortOrderCls = "FilterAndSortDesc.gif";
 		altSortOrderCls = "Filter Applied with Sort Descending";
 	}
 	var filterCls = "Filter.gif";
 	var altFilterCls = "Filter Applied";
   //	toolTip = colId.html() == null ? "" : colId.html();
   	if(colId != null && colId.length > 0) {
 		link.attr("title", toolTip);
 		imgObj.attr("src", filterCls);
 		imgObj.attr("alt", altFilterCls);
 		if(sortSt != null &&  sortFirstStr==headerNm ) {
 		 imgObj.attr("src", sortOrderCls);
 		imgObj.attr("alt", altSortOrderCls);	
 		}
   	} else {
 		if(sortSt != null &&  sortFirstStr==headerNm ) {
 			imgObj.attr("src", orderCls);
 			imgObj.attr("alt", altOrderCls);
 		}
   	}
}

function _handleCondition(headerNm, link, colId)
{
 	var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
 	var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
 	
 	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
 	if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
 		if(sortSt != null && sortSt.indexOf("descending") != -1) {
 			link.after(htmlDesc);
 		} else {
 			link.after(htmlAsc);
 		}
 	}
}
 
function clearFilter()
{
	document.forms[0].action ='/nbs/ManageCondition.do?method=ViewConditionLib&initLoad=true';
	document.forms[0].submit();
}

function manageConditionReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;
	
	var cdSys = getElementByIdOrByName("cSysDn");
	var conCd= getElementByIdOrByName("cCodeFld");
	var cond= getElementByIdOrByName("condFld");
	//var conDesc= getElementByIdOrByName("cDescFld");
	var pArea= getElementByIdOrByName("pAreaFld");
	var nndInd= getElementByIdOrByName("nndInd1");
	var nndEntityId = getElementByIdOrByName("conditionForm").nndInd[0].checked;
	
	
	
	if(cdSys != null && cdSys.value.length == 0)
	{
		errors[index++] = "Coding System is required";
		getElementByIdOrByName("cSys").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("cSys").style.color="black";
	}
	
	if(conCd != null && conCd.value.trim().length == 0)
    {
        errors[index++] = "Condition Code is required";
        getElementByIdOrByName("cCode").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("cCode").style.color="black";
    }
	
	if(cond != null && cond.value.trim().length == 0)
	{
		errors[index++] = "Condition Name is required";
		getElementByIdOrByName("cond").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("cond").style.color="black";
	}
	
	/*if(conDesc != null && conDesc.value.length == 0)
	{
		errors[index++] = "Condition Description is required";
		getElementByIdOrByName("cDesc").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("cDesc").style.color="black";
	}*/
	
	if(pArea != null && pArea.value.length == 0)
	{
		errors[index++] = "Program Area is required";
		getElementByIdOrByName("parea").style.color="990000";
		isError = true;
	}
	else {
		getElementByIdOrByName("parea").style.color="black";
	}
	
	if(isError){
		displayErrors("srtDataFormEntryErrors", errors);
	}

	return isError;
}
function conditionOnLoad(){	
	
	autocompTxtValuesForJSP();
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	if(getElementByIdOrByName("actionMode") == 'null'){
		actionMode ="";
	}
	if(StartsWith(actionMode, "Create") || StartsWith(actionMode, "Edit")) {
		var fieldset=new Array(); 
		fieldset[0] = "search";
        fieldset[1] = "result";
		fieldset[2] = "srtLink";
        var count = 0;
		while(count < 3) {
			        if( getElementByIdOrByName(fieldset[count]) != null) {
									var searchFldSet = getElementByIdOrByName(fieldset[count]);  
									var input = searchFldSet.getElementsByTagName("input");				

									for(var i = 0; i < input.length; i++)	{
										input[i].setAttribute("disabled","true");
									}
									var select = searchFldSet.getElementsByTagName("select");
									for(var i = 0; i < select.length; i++)	{
										select[i].setAttribute("disabled","true");
									}
									var href = searchFldSet.getElementsByTagName("a");
									for(var i = 0; i < href.length; i++)	{
										href[i].setAttribute("disabled","true");
										href[i].removeAttribute("href");
									}	
									var imgs = searchFldSet.getElementsByTagName("img");
									for(var i = 0; i < imgs.length; i++)	{
										imgs[i].setAttribute("disabled","true");
									}											
									
					}	
					count = count +1 ;	
		       }

	}
}

//Add Page Required Fields Validation Function
function addPageReqFlds(){

	$j(".infoBox").hide();
    
    	var errors = new Array();
    	var index = 0;
	var isError = false;

	var busObjType= getElementByIdOrByName("busObjType");
	var mappingGuide= getElementByIdOrByName("mappingGuide");
	var existingTemplate= getElementByIdOrByName("existingTemplate");
	var uniquePageName= getElementByIdOrByName("uniquePageName");
	
	var dataMartNm = getElementByIdOrByName("dataMartNm");
	
	if(dataMartNm){
	if(dataMartNm!=null && dataMartNm!='undefined' && dataMartNm.value!=null && dataMartNm.value.endsWith(/^\d/)){
		errors[index++]="Data Mart Name cannot start with a number"; isError = true;
		}
	}
	
	if(busObjType != null && busObjType.value.length == 0)
		{
			errors[index++] = "Event Type is required";
			getElementByIdOrByName("busObjTypeL").style.color="990000";
			isError = true;
		}
		else {
		   getElementByIdOrByName("busObjTypeL").style.color="black";
	}

	if(existingTemplate != null && existingTemplate.value.length == 0)
				{
					errors[index++] = "Template is required";
					getElementByIdOrByName("existingTemplateL").style.color="990000";
					isError = true;
				}
				else {
				   getElementByIdOrByName("existingTemplateL").style.color="black";
	}	
	
	if(busObjType.value =='INV' && mappingGuide != null && mappingGuide.value.length == 0)
			{
				errors[index++] = "Message Mapping Guide is required";
				getElementByIdOrByName("mappingGuideL").style.color="990000";
				isError = true;
			}
			else {
			   getElementByIdOrByName("mappingGuideL").style.color="black";
	}

	if(uniquePageName != null) 
			{ 
				var pgName = uniquePageName.value;
				if (jQuery.trim(pgName).length == 0) {
					errors[index++] = "Page Name is required";
					getElementByIdOrByName("uniquePageNameL").style.color="990000";
					isError = true;
				}
			else {
			   getElementByIdOrByName("uniquePageNameL").style.color="black";
			}
	}

        if(isError){
     		displayGlobalErrorMessage(errors);
	}
	
	return isError;
}

/*
 * copyValueToDataMartColumnName: method called from add question to copy the RDB Column Name is entered to Data Mart Column Name while is the value is entered in RDB Column Name.
 * */
function copyValueToDataMartColumnName(){
	document.getElementById("dataMartNm").value=document.getElementById("rdbcolumnNm").value.toUpperCase();
}

function nospaces(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    
    for(x=0; x<y; x++) {
    	
    	// get a single char
    	c = varVal.substr(x, 1);
    	
    	// if position is 1, check for valid alphabet
    	if (x == 0) {
    		if (isAlphabet(c)) {
    			s += c;
    		}         
    	}
    	// else check for alphanumeric
    	else {
    		if (rdbColValidation(c)) {
    			s += c;
    		}
    	}
    	pTextbox.value = s;
    }
    
    pTextbox.value=pTextbox.value.toUpperCase();
}

function rdbColValidation(pString)
{
    var varPattern = /[^a-zA-Z0-9_]+/;
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
        return true;
    return false;
}


//Clone Page Required Fields Validation Function
function clonePageReqFlds(){

	$j(".infoBox").hide();
   
    	var errors = new Array();
    	var index = 0;
	var isError = false;

	var busObjType= getElementByIdOrByName("busObjType");
	var mappingGuide= getElementByIdOrByName("mappingGuide");
	var existingTemplate= getElementByIdOrByName("existingTemplate");
	var uniquePageName= getElementByIdOrByName("uniquePageName");
	
	var dataMartNm = getElementByIdOrByName("dataMartNm");
	if(dataMartNm){
			if(dataMartNm!=null && dataMartNm!='undefined' && dataMartNm.value!=null && dataMartNm.value.match(/^\d/)){
				errors[index++]="Data Mart Name cannot start with a number";
				isError = true;
				}
		}
	
	if(busObjType != null && busObjType.value.length == 0)
		{
			errors[index++] = "Event Type is required.";
			getElementByIdOrByName("busObjTypeL").style.color="990000";
			isError = true;
		}
		else {
		   getElementByIdOrByName("busObjTypeL").style.color="black";
	}

	if(existingTemplate != null && existingTemplate.value.length == 0)
				{
					errors[index++] = "Seed Page is required.";
					getElementByIdOrByName("existingTemplateL").style.color="990000";
					isError = true;
				}
				else {
				   getElementByIdOrByName("existingTemplateL").style.color="black";
	}	
	
	if(busObjType.value=='INV' && mappingGuide != null && mappingGuide.value.length == 0)
			{
				errors[index++] = "Message Mapping Guide is required.";
				getElementByIdOrByName("mappingGuideL").style.color="990000";
				isError = true;
			}
			else {
				if(getElementByIdOrByName("mappingGuideL")!=null && getElementByIdOrByName("mappingGuideL") != undefined)
					getElementByIdOrByName("mappingGuideL").style.color="black";
	}
	
	if(uniquePageName != null) 
			{ 
				var pgName = uniquePageName.value;
				
		
				if (jQuery.trim(pgName).length == 0) {
					errors[index++] = "Page Name is required.";
					getElementByIdOrByName("uniquePageNameL").style.color="990000";
					isError = true;
				}
			else {
			   getElementByIdOrByName("uniquePageNameL").style.color="black";
			}
	}
	var selectedList = getElementByIdOrByName("selectedConditions");
   	if(selectedList != null && selectedList.length == 0)
	{
		errors[index++] = "At least one condition is required to submit a cloned page.";
		getElementByIdOrByName("relatedConditionsL").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("relatedConditionsL").style.color="black";
}
	
	//condition not required until published
	//var selCount=document.forms[0].selectedConditionCodes.length;
	//if(selCount == 0)
	//		{
	//			errors[index++] = "At least one condition must be added.";
	//			getElementByIdOrByName("relatedCondL").style.color="990000";
	//			isError = true;
	//		}
	//		else {
	//		   getElementByIdOrByName("relatedCondL").style.color="black";	
	//}
	
	
        if(isError){
     		displayGlobalErrorMessage(errors);
	}
	
	return isError;
}

function isNoDoubleQuote(pTextbox)
{
	var varVal = pTextbox.value;
	var y = 0; var s = ""; var c = "";
	y = varVal.length;
	for(x=0; x<y; x++) {
	   c = varVal.substr(x, 1);
	   if(c != '"') s += c;
	   pTextbox.value = s;
	}
}

	function numValidate(errors,index,isError){
 	 if(getElementByIdOrByName("dataType").value == 'NUMERIC'){
       	  a = getElementByIdOrByName("maskN").value;       	 
		switch(a){
		  case "NUM_DD_1":

			break;
		  case "NUM_MM_1":
		  	y = getElementByIdOrByName("#defaultValue").val();
			if(!isNaN(y)){
			  if(getElementByIdOrByName("#defaultValue").val().length >2 || getElementByIdOrByName("#defaultValue").val() > 12)
				getElementByIdOrByName("#defaultValue").val("");
			  }else{
			   getElementByIdOrByName("#defaultValue").val("");
		 	  }
			break;
		  case "NUM_YYYY_1":
			y = $("#defaultValue").val();
			if(!isNaN(y)){
			   if($("#defaultValue").val().length == 4 &&  $("#defaultValue").val() < 1875 )
				 $("#defaultValue").val("");
			   }else{
				$("#defaultValue").val("");
			 }
			break;
		  case "NUM_1":
			y = $("#defaultValue").val();
			if(isNaN(y)){
			  $("#defaultValue").val("");
			}
			break;
		  case "NUM_EXT_1":
			y = $("#defaultValue").val();
			if(!isNaN(y)){
			   if($("#defaultValue").val().length >8 )
				$("#defaultValue").val("");
			   }else{
			    	$("#defaultValue").val("");
		 	   }
			break;
		 case "NUM_SN":
			 x = getElementByIdOrByName("defaultValueNumeric").value;
			 y = getElementByIdOrByName("minValue").value;
			 z = getElementByIdOrByName("maxValue").value;	
			 
			if((y.length>0 && isNaN(y) )||  (y.indexOf(".") > -1) || (z.length>0 && isNaN(z) ) ||  (z.indexOf(".") > -1)){			    
			 
			    errors[index++] = "Minimum Value and Maximum Value must be whole numbers. " + "\n";			    
			    getElementByIdOrByName("minValueL").style.color="990000";
			    getElementByIdOrByName("maxValueL").style.color="990000";		    	    
		    	    isError = true;
		    	    return true;
			}else{
			    getElementByIdOrByName("minValueL").style.color="black";
			    getElementByIdOrByName("maxValueL").style.color="black";
			}
			break;
                case "NUM_TEMP_1":
			y = $("#defaultValue").val();
			if(!isNaN(y)){
			  if($("#defaultValue").val().length >5  || $("#defaultValue").val() >999.9 )
				 $("#defaultValue").val("");
			  }else{
 			         $("#defaultValue").val("");
			  }
			break;


		  }		  

	      }	     
	      return false;
	   }