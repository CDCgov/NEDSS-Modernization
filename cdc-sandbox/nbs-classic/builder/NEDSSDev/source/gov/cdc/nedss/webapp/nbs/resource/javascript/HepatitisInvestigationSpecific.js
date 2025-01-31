function DiagnosisConfirmation(pSelectNode,pFormAction){


    //save subform data entered
    callSaveData();
    //need to validate
    var error = false;
    //var hepDivNode =getElementByIdOrByName("tabControlHepatitis Case Report");
    //var hepDivNode =getElementByIdOrByName("tabControlHepatitis2");
    //alert("hepDivNode  " +hepDivNode);
    var errorTDNodeArray = document.getElementsByTagName("td");
    for(var i=0; i < errorTDNodeArray.length; i++) {
        var errorTDNode = errorTDNodeArray.item(i);
        if(getCorrectAttribute(errorTDNode, "rowID", errorTDNode.rowID)!=null)
        {
            errorTDNode.innerText="";
			errorTDNode.textContent="";

        }
    }

    var inputNodes = document.getElementsByTagName("input");
    var selectNodes = document.getElementsByTagName("select");
    var textareaNodes = document.getElementsByTagName("textarea");
    //check for nested
    for(var i=0; i < inputNodes.length; i++)    {
        if ( (getCorrectAttribute(inputNodes.item(i),"type",inputNodes.item(i).type) == "text") ) {
            if( !getCorrectAttribute(inputNodes.item(i),"isNested",inputNodes.item(i).isNested) &&  validate(inputNodes.item(i)) == false) {
                error = true;
            }
        }
    }
    for(var i=0; i < selectNodes.length; i++)   {
        if( !getCorrectAttribute(selectNodes.item(i),"isNested",selectNodes.item(i).isNested) && validate(selectNodes.item(i)) == false) {
            error = true;
        }
    }
    for(var i=0; i < textareaNodes.length; i++) {
        if(!getCorrectAttribute(textareaNodes.item(i),"isNested",textareaNodes.item(i).isNested) &&  validate(textareaNodes.item(i)) == false) {
            error = true;
        }
    }
    if(HEP121() |HEP122()|HEP123()|HEP124()| error)
    	error=true;

    //indicate visually which tab is in error condition
    // need to determine which div has error
    var divNodes = document.getElementsByTagName("div");
    var tabCount = 0;
    for(var i=0; i < divNodes.length; i++) {
      // consider only divs that are tab type
      if(getCorrectAttribute(divNodes.item(i),"type",divNodes.item(i).type) == "tab"){
      tabCount = tabCount + 1;
      // retrieve error tr for each tab and see if error messages exist
      var trNodes = divNodes.item(i).getElementsByTagName("tr");

      for(var j=0; j < trNodes.length; j++) {
        if(getCorrectAttribute(trNodes.item(j),"id",trNodes.item(j).id) == "error-message-tr"){
        var tdNode = getCorrectAttribute(trNodes.item(j),"firstChild",trNodes.item(j));
        if(tdNode.innerText!="") {
          //alert( "errorText: " + tdNode.innerText);
          //display the error visual on the tab
          //from the id of the div retrieve the node for the tab
          var tabName = divNodes.item(i).id.replace(/tabControl/,"");
          //alert(tabName);
          var tabNode = getElementByIdOrByName("tabTdBgColortop"+tabName);
          //get child to get <nobr> node
          if(tabNode!=null){
          tabNode = getCorrectAttribute(tabNode,"firstChild",tabNode.firstChild);
          var imgNodes = tabNode.getElementsByTagName("img");
          if(imgNodes.length >0){
            tabNode.removeChild(imgNodes.item(0));
          }
          //append the error icon

          var imgNode = document.createElement("img");
          imgNode.setAttribute("src", "tab_error.gif");
          imgNode.setAttribute("alt", "Error");
          imgNode.setAttribute("title", "Error");
          
          
          tabNode.appendChild(imgNode);
          break;
               }
             }
           }
         }
       }
     }


      var errorTD = getElementByIdOrByName("error1");
      if (error == true   && showErrorStatement==true)
      {
          if( errorTD )
          {
            var errorText=null;
            errorText = makeErrorMsg('ERR122');
            errorTD.innerText = errorText;
			errorTD.textContent = errorText;
            errorTD.setAttribute("className", "error");
			errorTD.setAttribute("class", "error");
			

          }
      }


      if( error )
      {
          document.location.href="#top";
          pSelectNode.value="";
          getElementByIdOrByName(pSelectNode.name+"_textbox").value="";
          return;
      }



    var option = pSelectNode.options[pSelectNode.selectedIndex];

    confirmMsg = "Are you sure you want to select " + option.text +"?";
    confirmMsgSupplemental1 = "Would you like to enter supplemental questions for this diagnosis?  ";
    confirmMsgSupplemental2 = "The diagnosis cannot be changed if the form has suplemental questions ";
    confirmMsgSupplemental3 = "and the form has been submitted.  Press OK to enter supplemental questions or press Cancel to not enter supplemental questions.";

    confirmMsgSupplemental = confirmMsgSupplemental1 + confirmMsgSupplemental2 + confirmMsgSupplemental3;
    var conditionCd = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd");
     //alert("conditionCd    " + conditionCd);
    if (confirm(confirmMsg))
    {
        document.forms['nedssForm'].action=pFormAction;
        selectedValue = getElementByIdOrByName("proxy.observationVO_s[30].obsValueCodedDT_s[0].code").value;

	//	alert( "the value selected is: " + selectedValue );
        document.forms['nedssForm'].action=pFormAction;
        getElementByIdOrByName("hiddenShowTabFlag").value = "Yes";
        //if( ( selectedValue == "10110" ) || ( selectedValue == "10100" )|| ( selectedValue == "10106" )|| ( selectedValue == "10104" )|| ( selectedValue == "10101" ))
       // {
          //  if (confirm(confirmMsgSupplemental))

          //  {
            //    document.forms['nedssForm'].action=pFormAction;
            //   getElementByIdOrByName("hiddenShowTabFlag").value = "Yes";
            //}
            //else
            //{

               //getElementByIdOrByName("hiddenShowTabFlag").value = "No";
               // document.forms['nedssForm'].action=pFormAction;
                //make pSelectNode value empty coz., when you click cancel, you need to go to Hep Generic, not Hep cond. specific !!
		//pSelectNode.value="";
           // }
       // }
        //else
        //{
           //getElementByIdOrByName("hiddenShowTabFlag").value = "No";
           // document.forms['nedssForm'].action=pFormAction;
        //}

        if(pSelectNode.value==""){
            conditionCd.value="999999";
            var cdDescTxt = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cdDescTxt");
            cdDescTxt.value="Hepatitis";
        }

        BatchEntryCreateHiddenInputs();
        document.forms['nedssForm'].submit();
        //submitForm();
    }
    else
    {
    // revert back to what was there previously
     pSelectNode.value = conditionCd.value;
     //alert("inside LAST ELSE confirm(confirmMsgSupplemental)" +getElementByIdOrByName("hiddenShowTabFlag").value);

    }
    return;

}


function HEP133()
{

    var trHEP134node = getElementByIdOrByName("trHEP134");

    var HEP132node =  getElementByIdOrByName("supplemental_s[3].obsValueCodedDT_s[0].code");
    var HEP133node =  getElementByIdOrByName("supplemental_s[4].obsValueCodedDT_s[0].code");

    if(HEP132node.value == "Y" || HEP133node.value == "Y" )
    {

        trHEP134node.setAttribute("className", "visible");
        return false;
    }
    else
    {

        nestedElementsControllerClearInternalInputs(trHEP134node);
        trHEP134node.setAttribute("className", "none");
        return true;
    }

}

//Symptom Onset Date
function HEP102()
{

    var errorMessage = "";
    var errorText = "";

    var HEP102node = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveFromTime_s");
    var dobNode = getElementByIdOrByName("DEM115");
    var tdErrorCell = getTdErrorCell( HEP102node );
    var labelList = new Array();
    if (isblank(HEP102node.value)){
                     return false;
                }
                else
                {

                    // First check if it is in mm/dd/yyyy format
                    if (!isDate( HEP102node.value ) &&(HEP102node.fieldLabel))
                    {

                      //errorText = makeErrorMsg('ERR003', labelList.concat(labelList.concat(HEP102node.fieldLabel)));
                      errorText = makeErrorMsg('ERR003', labelList.concat(HEP102node.fieldLabel));
                      tdErrorCell.innerText = errorText;
                      tdErrorCell.className = "error";

                      return true;
                    }//if
                 }//else

        // no date of birth
    // no date of birth
    if(dobNode.value=="")
    {

            if ((CompareDateStrings(HEP102node.value, "12/31/1875") == -1) ||
                (CompareDateStringToToday(HEP102node.value) == 1))
        {
                //  check if there already is an error msg there

                var errorText = makeErrorMsg('ERR004', labelList.concat(HEP102node.fieldLabel));
                tdErrorCell.innerText = errorText;

                tdErrorCell.className = "error";
                return true;
        }
    }
    //with date of birth
    else
    {
        if ((CompareDateStrings(HEP102node.value, dobNode.value) == -1) ||
            (CompareDateStringToToday(HEP102node.value) == 1))  {
            //  check if there already is an error msg there

            errorText = makeErrorMsg('ERR092', labelList.concat(HEP102node.fieldLabel).concat(dobNode.fieldLabel));
            tdErrorCell.innerText = errorText;

            tdErrorCell.className = "error";
            return true;
        }
    }

    return false;
}//Symptom Onset Date


//validate Admission Date
function HEP132()
{

    var errorMessage = "";
    var errorText = "";


    var HEP132node = getElementByIdOrByName("proxy.observationVO_s[2].obsValueDateDT_s[0].fromTime_s");

    var INV133node = getElementByIdOrByName("proxy.observationVO_s[3].obsValueDateDT_s[0].fromTime_s");
    var tdErrorCell = getTdErrorCell( HEP132node );
    var labelList = new Array();


    // First check if it is in mm/dd/yyyy format
    if (!isDate( HEP132node.value ) &&(HEP132node.fieldLabel)&&(HEP132node.value!="" ))
    {

            errorText = makeErrorMsg('ERR003', labelList.concat(HEP132node.fieldLabel));
            tdErrorCell.innerText = errorText;
            tdErrorCell.className = "error";

            return true;
    }

    // if Admission Date is valid date
    if( (HEP132node.value !="") && (isDate(HEP132node.value)))
    {
      if ((CompareDateStrings(HEP132node.value, "12/31/1875") == -1) ||(CompareDateStringToToday(HEP132node.value) >0))
      {
      	errorText = makeErrorMsg('ERR004', labelList.concat(HEP132node.fieldLabel));

      	if( tdErrorCell.innerText == "" )
	                    {
	                        tdErrorCell.innerText = errorText;
	                    }
	                    else{
	                        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
	                    }
	                    tdErrorCell.className = "error";

          return true;
      }
     }
     // if Discharge Date is not blank, then

     if ( (HEP132node.value !="") && (isDate(HEP132node.value)) && (INV133node.value!="")&& (isDate(INV133node.value)))
     {
     	if((CompareDateStrings(HEP132node.value,INV133node.value)>0)||(CompareDateStringToToday(HEP132node.value) >0))
     	{

        errorText = makeErrorMsg('ERR074', labelList.concat(INV133node.fieldLabel).concat(HEP132node.fieldLabel));
        if( tdErrorCell.innerText == "" )
	                    {
	                        tdErrorCell.innerText = errorText;
	                    }
	                    else{
	                        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
	                    }
	                    tdErrorCell.className = "error";

        return true;
     	}
      }

    return false;
}//validate Admission Date

//validate Discharge Date
function INV133()
{

    var errorMessage = "";
    var errorText = "";

    var INV133node = getElementByIdOrByName("proxy.observationVO_s[3].obsValueDateDT_s[0].fromTime_s");
    var HEP132node = getElementByIdOrByName("proxy.observationVO_s[2].obsValueDateDT_s[0].fromTime_s");

    var tdErrorCell = getTdErrorCell( INV133node );
    var labelList = new Array();

    // First check if it is in mm/dd/yyyy format
    if (!isDate( INV133node.value ) &&(INV133node.fieldLabel)&&(INV133node.value!=""))
    {

                errorText = makeErrorMsg('ERR003', labelList.concat(INV133node.fieldLabel));
                if( tdErrorCell.innerText == "" )
		                    {
		                        tdErrorCell.innerText = errorText;
		                    }
		                    else{
		                        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		                    }
		                    tdErrorCell.className = "error";

                    return true;
    }
    // if Discharge Date is  valid date
        if( (INV133node.value !="") && (isDate(INV133node.value)))
        {
          if ((CompareDateStrings(INV133node.value, "12/31/1875") == -1) ||(CompareDateStringToToday(INV133node.value) >0))
          {
          	errorText = makeErrorMsg('ERR004', labelList.concat(INV133node.fieldLabel));
               	if( tdErrorCell.innerText == "" )
		                    {
		                        tdErrorCell.innerText = errorText;
		                    }
		                    else{
		                        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		                    }
		                    tdErrorCell.className = "error";

                return true;
          }
         }

    return false;
}////validate Discharge Date

//validate ALT Result
function HEP121()
{
	return SupportingAltRange( "proxy.observationVO_s[12].obsValueNumericDT_s[0].numericValue1_s" );

}
//validate ALT Result

//validate ALT Result/Upper Limit Normal
function HEP122()
{

	return SupportingAltRange( "proxy.observationVO_s[13].obsValueNumericDT_s[0].numericValue1_s" );
}
//validate ALT Result/Upper Limit Normal

//validate AST Result
function HEP123()
{

	return SupportingAltRange( "proxy.observationVO_s[14].obsValueNumericDT_s[0].numericValue1_s" );
}
//validate AST Result

//validate AST Result/Upper Limit Normal
function HEP124()
{

	return SupportingAltRange( "proxy.observationVO_s[15].obsValueNumericDT_s[0].numericValue1_s" );
}
//validate AST Result/Upper Limit Normal

//range 0-10000
function SupportingAltRange ( elementName )
{

       	   var errorMessage = "";
           var errorText = "";
           var HEP121node = getElementByIdOrByName( elementName );

           var tdErrorCell = getTdErrorCell( HEP121node );
           var labelList = new Array();

                if ((isblank(HEP121node.value)) || ((isNumeric(HEP121node.value)) && (HEP121node.value>=0) && ((HEP121node.value)<=10000) ) )
                {
                        return false;

                } else {

                        errorText = makeErrorMsg('ERR086', labelList.concat(HEP121node.fieldLabel));

                    if( tdErrorCell.innerText == "" )
                    {
                        tdErrorCell.innerText = errorText;
                    }
                    else{
                        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
                    }
                    tdErrorCell.className = "error";

                    return true;

                }


                return false;

}//range 0-10000


   //Due Date
   function HEP107()
   {
    var errorMessage = "";
        var errorText = "";
        var labelList = new Array();

   	var HEP107node = getElementByIdOrByName("proxy.observationVO_s[9].obsValueDateDT_s[0].fromTime_s");
        var dobNode = getElementByIdOrByName("DEM115");

        var tdErrorCell = getTdErrorCell( HEP107node );
            if (isblank(HEP107node.value)){
                 return false;
            }
            else
            {

                // First check if it is in mm/dd/yyyy format
                if (!isDate( HEP107node.value ) &&(HEP107node.fieldLabel))
                {

                  errorText = makeErrorMsg('ERR003', labelList.concat(labelList.concat(HEP107node.fieldLabel)));

                  tdErrorCell.innerText = errorText;
                  tdErrorCell.className = "error";

                  return true;
                }//if
             }//else

        // no date of birth

       if((dobNode.value=="")|| (dobNode.value==null))
        {

            if (CompareDateStrings(HEP107node.value, "12/31/1875") == -1)

            {
                //check if there already is an error msg there
               	errorText = makeErrorMsg('ERR068', labelList.concat(HEP107node.fieldLabel));
                tdErrorCell.innerText = errorText;
                tdErrorCell.className = "error";
                return true;
            }
         }
         //with date of birth
         else
         {
            if (CompareDateStrings(HEP107node.value, dobNode.value) == -1)

            {
              //check if there already is an error msg there
              errorText = makeErrorMsg('ERR074', labelList.concat(HEP107node.fieldLabel).concat(dobNode.fieldLabel));

              tdErrorCell.innerText = errorText;

              tdErrorCell.className = "error";
              return true;
            }
          }


    return false;
   }//Due Date

   //validate Date of ALT Result
   function HEP125()
   {

   	return HepALTandASTdate("proxy.observationVO_s[16].obsValueDateDT_s[0].fromTime_s");
   }
   //validate Date of ALT Result

  //validate Date of AST Result
   function HEP126()
   {

      	return HepALTandASTdate("proxy.observationVO_s[17].obsValueDateDT_s[0].fromTime_s");
   }
   //validate Date of AST Result



   //Date of ALT/AST Result
   function HepALTandASTdate(elementName)
   {

   var errorMessage = "";
              var errorText = "";
              var errorMessage = "";
              var HEP125node = getElementByIdOrByName( elementName );

              var tdErrorCell = getTdErrorCell( HEP125node );
              var labelList = new Array();
   	      var dobNode = getElementByIdOrByName("DEM115");
    	      var HEP102node = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveFromTime_s");


             if (isblank(HEP125node.value))
             {
                    return false;

             }
             else
             {

             // First check if it is in mm/dd/yyyy format
             	if (!(isDate( HEP125node.value )) &&(HEP125node.fieldLabel))
             	{
                      errorText = makeErrorMsg('ERR003', labelList.concat(HEP125node.fieldLabel));

                      tdErrorCell.innerText = errorText;
                      tdErrorCell.className = "error";

               	      return true;
             	}//if
             }//else

           // first validate against Symptom Onset Date if patient has Symptom Onset Date
           if(!(HEP102node.value==""))
           {
   //   ----------------

	                       // First check if it is in mm/dd/yyyy format for Symptom Onset Date
	                       if (!isDate( HEP102node.value ) &&(HEP102node.fieldLabel))
	                       {

	                         //errorText = makeErrorMsg('ERR003', labelList.concat(HEP102node.fieldLabel));
	                         tdErrorCell.innerText = errorText;
	                         tdErrorCell.className = "error";

	                         return true;
	                       }//if


	       // no date of birth for Symptom Onset Date
	       if(dobNode.value=="")
	       {

	               if ((CompareDateStrings(HEP102node.value, "12/31/1875") == -1) ||
	                   (CompareDateStringToToday(HEP102node.value) == 1))
	           	{

	              	   //var errorText = makeErrorMsg('ERR004', labelList.concat(HEP102node.fieldLabel));
	                   tdErrorCell.innerText = errorText;

	                   tdErrorCell.className = "error";
	                   return true;
	           	}
	       }
	       //with date of birth for Symptom Onset Date
	       else
	       {
	           if ((CompareDateStrings(HEP102node.value, dobNode.value) == -1) ||
	               (CompareDateStringToToday(HEP102node.value) == 1))
	           {

	           //    errorText = makeErrorMsg('ERR092', labelList.concat(HEP102node.fieldLabel).concat(dobNode.fieldLabel));
	               tdErrorCell.innerText = errorText;

	               tdErrorCell.className = "error";
	               return true;
	           }
	       }


  //     -----------------
               if ((CompareDateStrings(HEP125node.value, HEP102node.value) == -1) ||
                   (CompareDateStringToToday(HEP125node.value) == 1))
               {
                   //check if there already is an error msg there
                   errorText = makeErrorMsg('ERR074', labelList.concat(HEP125node.fieldLabel).concat(HEP102node.fieldLabel));


                   tdErrorCell.innerText = errorText;
                   tdErrorCell.className = "error";
                   return true;
               }
       }//close if 'first validate against Symptom Onset Date if patient has Symptom Onset Date'


       //second validate against DOB if patient has DOB
       if(!(dobNode.value==""))
       {
       		if ((CompareDateStrings(HEP125node.value, dobNode.value) == -1) ||
		                   (CompareDateStringToToday(HEP125node.value) == 1))
		{
		                   //check if there already is an error msg there
		                   errorText = makeErrorMsg('ERR074', labelList.concat(HEP125node.fieldLabel).concat(dobNode.fieldLabel));

		                   tdErrorCell.innerText = errorText;
		                   tdErrorCell.className = "error";
		                   return true;
		 }
       }//close if 'second validate against DOB if patient has DOB'

       //last validate against 1875 and current date if DOB and Symptom Onset Date are blank
       if((dobNode.value=="") || (HEP102node.value==""))
       {
       		if ((CompareDateStrings(HEP125node.value, '12/31/1875') == -1) ||
       		                   (CompareDateStringToToday(HEP125node.value) == 1))
       		{
       		                   //check if there already is an error msg there
       		                   errorText = makeErrorMsg('ERR004', labelList.concat(HEP125node.fieldLabel));

       		                   tdErrorCell.innerText = errorText;
       		                   tdErrorCell.className = "error";
       		                   return true;
		 }

       }

       return false;
 }//Date of ALT/AST Result



validationArray[3] = HEP102;
validationArray[4] = HEP121;
validationArray[5] = HEP122;
validationArray[6] = HEP123;
validationArray[7] = HEP124;
validationArray[8] = HEP132;
validationArray[9] = INV133;
validationArray[10] = HEP107;
validationArray[11] = HEP125;
validationArray[12] = HEP126;

/*
tabValidationArray[0] = HEP102;
tabValidationArray[1] = HEP121;
tabValidationArray[2] = HEP132;
tabValidationArray[3] = INV133;
*/
