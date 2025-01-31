
function testDisplayOrderCheck(){

        var element  =getElementByIdOrByName("ldf[i].displayOrderNbr");
	var tdErrorCell = getTdErrorCell(element ) ;


		if (isblank(element.value) || (isNumeric(element.value) && element.value>=0)) {
                      	return false;
		} else {
                        errorText = "Please enter a whole number into Display Order that is  greater than or equal to 0.";
			//errorText = makeErrorMsg('ERR097', labelList.concat(element.fieldLabel));
			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.className = "error";


			return true;
		}
	

		return false;
}


function testDisplayWidthCheck(){
   
        var element  =getElementByIdOrByName("ldf[i].fieldSize");
	var tdErrorCell = getTdErrorCell(element ) ;
             
		if (isblank(element.value) || (isNumeric(element.value) && (element.value>=2 && element.value<=35))) {
                           return false;
		} else {

			errorText = "Display Width must be a whole number greater than or equal to 2 and less than or equal to 35. Please correct the data and try again.";
			//errorText = makeErrorMsg('ERR097', labelList.concat(element.fieldLabel));
			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.className = "error";


			return true;
		}
	

		return false;
}
 
  function LabelMaxLenthCheck()
  {
        var element  =getElementByIdOrByName("ldf[i].labelTxt");
	var tdErrorCell = getTdErrorCell(element ) ;


		var maxLength = 35;

		if(element.maxFieldLength)
			maxLength = element.maxFieldLength;

		//alert(maxLength);
		if(isLong(element.value, maxLength) == true) {

			errorText = "Label must be less than or equal to 35 characters. Please correct the data and try again.";
			//errorText = errorText.replace('(Maxlength)', maxLength);
			tdErrorCell.innerText = errorText;
			tdErrorCell.className = "error";
			return true;
		}
       
      return false;     

  }
  function requiredCheck()
  { 
   

  var element  =getElementByIdOrByName("ldf[i].dataType");
  var tdErrorCell = getTdErrorCell(element ) ;
  

  if (isblank(element.value)) {


	            	errorText = "Please select Type and try again";
			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else {
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			  }
			tdErrorCell.className = "error";
				return true;
		}
		else {
			return false;
		}
      }

  function requiredCheckSRTCode()
  { 
   

  var element  =getElementByIdOrByName("ldf[i].codeSetNm");
  var dataType =getElementByIdOrByName("ldf[i].dataType");
  //alert("dataType is :" + dataType.value +(dataType.value =="CV"));
  var tdErrorCell = getTdErrorCell(element ) ;
  if (dataType.value =="CV" && isblank(element.value)) {


	            	errorText = "Please select SRT Code Set and try again";
			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else {
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			  }
			tdErrorCell.className = "error";
				return true;
		}
		else {
			return false;
		}
      }
function requiredCheckForLabel()
  { 
   

  var element  =getElementByIdOrByName("ldf[i].labelTxt");
  var tdErrorCell = getTdErrorCell(element ) ;
  if (isblank(element.value)) {


	            	errorText = "Please select Label and try again";
			if( tdErrorCell.innerText == "" )
			  tdErrorCell.innerText = errorText;
			else {
			  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			  }
			tdErrorCell.className = "error";
				return true;
		}
		else {
			return false;
		}
      }




batchEntryValidationArray[0] = testDisplayOrderCheck;
batchEntryValidationArray[1] = testDisplayWidthCheck;
batchEntryValidationArray[2] = LabelMaxLenthCheck;
batchEntryValidationArray[3] = requiredCheck;
batchEntryValidationArray[4] = requiredCheckSRTCode; 
batchEntryValidationArray[5] = requiredCheckForLabel; 


//batchEntryValidationArray.concat(testResultCodedCheck);
//batchEntryValidationArray.concat(submittedSusceptFirstCheck);
//batchEntryValidationArray[1] = testResultOrganismCheck;




