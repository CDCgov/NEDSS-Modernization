//Date of transfusion
function HEP164()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";
        var HEP164node = getElementByIdOrByName("supplemental_s[34].obsValueDateDT_s[0].fromTime_s");
        var HEP102node = getElementByIdOrByName("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveFromTime_s");
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP164node );
        var HEP164nodeYear =  HEP164node.value .substring(6, 10);
	var sixWeekBefore = DaySubtract(new Date(HEP102node.value),42);
	var sixMonthsBefore = MonthSubtract(new Date(HEP102node.value),6);
        var currentYear = now.getFullYear();
       
        
        if (isblank(HEP164node.value))
	{
		return false;
		             
	}
	else
	{
		// First check if it is in mm/dd/yyyy format
		if (!(isDate( HEP164node.value )) &&(HEP164node.fieldLabel))
		{
		                      
		        errorText = makeErrorMsg('ERR003', labelList.concat(HEP164node.fieldLabel));
		   
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
	              if( (( (sixWeekBefore!="")&&(CompareDateStrings(sixWeekBefore, HEP164node.value)==-1) ) ||
		        ( (sixMonthsBefore!="")&&(CompareDateStrings(HEP164node.value, sixMonthsBefore)==-1) )) && HEP164nodeYear >999 )
		      {
		              
		                errorText = makeErrorMsg('ERR079', labelList.concat(HEP164node.fieldLabel).concat(HEP102node.fieldLabel));
		
		                tdErrorCell.innerText = errorText;
		
		                tdErrorCell.className = "error";
		                return true;
	             }
	        
	      	}//close if 'first validate against Symptom Onset Date if patient has Symptom Onset Date'
	      	//second validate against DOB if patient has DOB, and  does not have Symptom Onset Date
		if(!(dobNode.value==""))
		{
		     if ((CompareDateStrings(HEP164node.value, dobNode.value) == -1) ||
			 (CompareDateStringToToday(HEP164node.value) == 1))
				{
				      //check if there already is an error msg there
				      errorText = makeErrorMsg('ERR092', labelList.concat(HEP164node.fieldLabel).concat(dobNode.fieldLabel));
				                 
				      tdErrorCell.innerText = errorText;
				      tdErrorCell.className = "error";
				      return true;
				 }
		}//close if 'second validate against DOB if patient has DOB'
		       
		//last validate against 1875 and current date if DOB and Symptom Onset Date are blank
		if((dobNode.value=="") || (HEP102node.value==""))
		{
		      if ((CompareDateStrings(HEP164node.value, '12/31/1875') == -1) ||
		       	 (CompareDateStringToToday(HEP164node.value) == 1))
		      {
		       	   
		       	     errorText = makeErrorMsg('ERR004', labelList.concat(HEP164node.fieldLabel));
		       		                 
		       	     tdErrorCell.innerText = errorText;
			     tdErrorCell.className = "error";
		       	     return true;
		      }
		}
	      	
	    return false;
}//Date of transfusion


//In what year was the last shot received
function HEP189()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";			
        var HEP189node = getElementByIdOrByName("supplemental_s[40].obsValueNumericDT_s[0].numericValue1_s");
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP189node );

        var now=new Date();
        var currentYear = now.getFullYear();
        var matchYear = /^\d{4}$/;
        if( (HEP189node.value!="") && (HEP189node.value.match(matchYear) == null) )
        {
        	errorText = makeErrorMsg('ERR076', labelList.concat(HEP189node.fieldLabel));
		                    tdErrorCell.innerText = errorText;
		
		                    tdErrorCell.className = "error";
                    return true;
        // no match, it is not in yyyy format.
        //return true;
        }
        // no date of birth
        if(dobNode.value=="")
        {

            if (HEP189node.value!="" && window.isNaN(HEP189node.value)==false && (currentYear<HEP189node.value || HEP189node.value<1981))
            {

                    errorText = makeErrorMsg('ERR082', labelList.concat(HEP189node.fieldLabel).concat('1981'));
                    tdErrorCell.innerText = errorText;

                    tdErrorCell.className = "error";
                    return true;
            }
        }
        //with date of birth
            else
            {
                    var birthDate = new Date(dobNode.value);
                    var birthYear = birthDate.getFullYear();

                    if (HEP189node.value!="" && window.isNaN(HEP189node.value)==false && (currentYear<HEP189node.value || HEP189node.value<1981)&&(birthYear<1981))
                    {
                        errorText = makeErrorMsg('ERR082', labelList.concat(HEP189node.fieldLabel).concat('1981'));
                        tdErrorCell.innerText = errorText;
                        tdErrorCell.className = "error";
                        return true;
                        
                    } else if (HEP189node.value!="" && window.isNaN(HEP189node.value)==false && (currentYear<HEP189node.value  || HEP189node.value<birthYear)&&(birthYear>=1981))
                    {
                        errorText = makeErrorMsg('ERR082', labelList.concat(HEP189node.fieldLabel).concat(birthYear));
                        tdErrorCell.innerText = errorText;
                        tdErrorCell.className = "error";
                        return true;
                    }
            }
                    return false;
}//In what year was the last shot received

//Year of most recent incarceration
function HEP184()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";			
     
        var HEP184node = getElementByIdOrByName("supplemental_s[27].obsValueNumericDT_s[0].numericValue1_s");
    
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP184node );

        var pattern = /\d{4}/;
        var now=new Date();
        var currentYear = now.getFullYear();
        if(( HEP184node.value!="" ) && (pattern.exec(HEP184node.value) == null))
        {
            //errorText = "Year of most recent incarceration must be a 4-digit numeric entry inthe format of yyyy.";
            errorText = makeErrorMsg('ERR076', labelList.concat(HEP184node.fieldLabel));
            tdErrorCell.innerText = errorText;
            tdErrorCell.className = "error";

            return true;

        }

        // no date of birth
        if(dobNode.value=="")
        {

            if ((HEP184node.value!="" && window.isNaN(HEP184node.value)==false && (currentYear<HEP184node.value || HEP184node.value<1875)))
            {
                    //  check if there already is an error msg there
                    //errorText = "Year of most recent incarceration must be equal to or greater than " + 1875 + " and less than or equal to current year.  Please correct the data and try again.";
                    errorText = makeErrorMsg('ERR082', labelList.concat(HEP184node.fieldLabel).concat('1875'));
                    tdErrorCell.innerText = errorText;

                    tdErrorCell.className = "error";
                    return true;
            }
        }
        //with date of birth
        else
        {
            var birthYear = dobNode.value.substring(6,10);
            if ((HEP184node.value!="" && window.isNaN(HEP184node.value)==false && (currentYear<HEP184node.value || HEP184node.value<birthYear)))
            {
                //  check if there already is an error msg there
                //errorText = "Year of most recent incarceration must be equal to or greater than " + birthYear + " and less than or equal to current year.  Please correct the data and try again.";
                errorText = makeErrorMsg('ERR082', labelList.concat(HEP184node.fieldLabel).concat(birthYear));
                tdErrorCell.innerText = errorText;
                tdErrorCell.className = "error";
                return true;
            }
        }
           return false;
}//Year of most recent incarceration


//Length of most recent incarceration
function HEP185()
{

	var labelList = new Array();
        var errorMessage = "";
        var errorText = "";			
        var HEP185node = getElementByIdOrByName("supplemental_s[28].obsValueNumericDT_s[0].numericValue1_s");
        var HEP185nodeUnit = getElementByIdOrByName("supplemental_s[29].obsValueCodedDT_s[0].code");
									
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP185node );

      //  var pattern = /\d{4}/;
      //  var now=new Date();
      //  var currentYear = now.getFullYear();
        if( HEP185node.value!="" )
        {
                   if( HEP185nodeUnit.value=="" ) {
                    
                       errorText = makeErrorMsg('ERR002', labelList.concat(HEP185node.fieldLabel));


                   }
                   if( !isNumeric(HEP185node.value) ) 
                   {
                   
                    	errorText = makeErrorMsg('ERR087', labelList.concat(HEP185node.fieldLabel));

                   }
                   if( (HEP185nodeUnit.value=="M" && HEP185node.value<=6)||(HEP185nodeUnit.value=="Y" && HEP185node.value==0) )
                   {
                    
                    	errorText = makeErrorMsg('ERR087', labelList.concat(HEP185node.fieldLabel));


                   }

                   if( errorText!="" ) {
                    	tdErrorCell.innerText = errorText;
                    	tdErrorCell.className = "error";
                    	return true;
                   }
        }


    return false;
}//Length of most recent incarceration




//Year of most recent treatment
function HEP158()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";
        var HEP158node = getElementByIdOrByName("supplemental_s[36].obsValueNumericDT_s[0].numericValue1_s");
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP158node );

        var now=new Date();
        var currentYear = now.getFullYear();

        var matchYear = /^\d{4}$/;


        if( (HEP158node.value!="") && (HEP158node.value.match(matchYear) == null) )
        {
		errorText = makeErrorMsg('ERR076', labelList.concat(HEP158node.fieldLabel));
		tdErrorCell.innerText = errorText;
		
		tdErrorCell.className = "error";
                   
        // no match, it is not in yyyy format.

        return true;
        }
        // no date of birth
        if(dobNode.value=="")
        {

            if ((HEP158node.value!="") && (window.isNaN(HEP158node.value)==false) && ((currentYear<HEP158node.value) || (HEP158node.value<1875)))
            {

                    //  check if there already is an error msg there
                    //errorText = "Please enter Year of most recent treatment between 1875 and current year.";
                    errorText = makeErrorMsg('ERR092', labelList.concat(HEP158node.fieldLabel).concat('1875'));
                    tdErrorCell.innerText = errorText;

                    tdErrorCell.className = "error";
                    return true;
            }
        }
        //with date of birth
        else
        {
            var birthYear = dobNode.value.substring(6,10);
            if (HEP158node.value!="" && window.isNaN(HEP158node.value)==false && (currentYear<HEP158node.value || HEP158node.value<birthYear))
            {
                
                errorText = makeErrorMsg('ERR092', labelList.concat(HEP158node.fieldLabel).concat(birthYear));
                tdErrorCell.innerText = errorText;

                tdErrorCell.className = "error";
                return true;
            }
        }
    return false;
}//Year of most recent treatment

validationArray[13] = HEP164;
validationArray[14] = HEP189;
validationArray[15] = HEP184;
validationArray[16] = HEP185;
validationArray[17] = HEP158;

/*
tabValidationArray[3] = HEP163;
tabValidationArray[4] = HEP188;
tabValidationArray[5] = HEP183;
tabValidationArray[6] = HEP157;
*/
