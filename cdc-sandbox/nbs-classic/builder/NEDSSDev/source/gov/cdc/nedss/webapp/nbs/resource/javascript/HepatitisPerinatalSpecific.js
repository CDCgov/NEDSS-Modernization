//Date child received HBIG
function HEP251()
{

    var labelList = new Array();
    var errorMessage = "";
    var errorText = "";
    var HEP250node = getElementByIdOrByName("HEP251");
    var dobNode = getElementByIdOrByName("DEM115");
    var tdErrorCell = getTdErrorCell( HEP250node );

    if (isblank(HEP250node.value))
    {
                     return false;
    }
    else
    {
    
    // First check if it is in mm/dd/yyyy format
    	if (!isDate( HEP250node.value ) )
    	{
              
        	errorText = makeErrorMsg('ERR003', labelList.concat(HEP250node.fieldLabel));
             
        	tdErrorCell.innerText = errorText;
        	tdErrorCell.className = "error";
    
        	return true;
    
    	}
    	else
    	{
    	// no date of birth
    	    		
		if(dobNode.value=="")
	    	{
	        	if ((CompareDateStrings(HEP250node.value, "12/31/1875") == -1) ||
	                	   (CompareDateStringToToday(HEP250node.value) == 1))  
	        	{
	
	                	                
	                	errorText = makeErrorMsg('ERR004', labelList.concat(HEP250node.fieldLabel));
	                	tdErrorCell.innerText = errorText;
	
	                	tdErrorCell.className = "error";
	                	return true;
	        	}
	    	}
	    	//with date of birth
	    	else
	    	{
	        	if ((CompareDateStrings(HEP250node.value, dobNode.value) == -1) ||
	            	   (CompareDateStringToToday(HEP250node.value) == 1))  
	        	{
	            
	            		errorText = makeErrorMsg('ERR074', labelList.concat(HEP250node.fieldLabel).concat(dobNode.fieldLabel));
	            		tdErrorCell.innerText = errorText;
	
	            		tdErrorCell.className = "error";
	            		return true;
	        	}
	    	}
	      
	  }
     }
	
	    return false;
}//Date child received HBIG

//Date of dose 1
function HEP247()
{

    var labelList = new Array();
    var errorMessage = "";
    var errorText = "";
    var HEP247node = getElementByIdOrByName("HEP247");
    var dobNode = getElementByIdOrByName("DEM115");
    var tdErrorCell = getTdErrorCell( HEP247node );

    if (isblank(HEP247node.value))
    {
                 return false;
    }
    else
    {

       // First check if it is in mm/dd/yyyy format
       if (!isDate( HEP247node.value ) )
       {
          
          errorText = makeErrorMsg('ERR003', labelList.concat(HEP247node.fieldLabel));
         
          tdErrorCell.innerText = errorText;
          tdErrorCell.className = "error";

          return true;

        }
        else
        {


    		// no date of birth
    		if(dobNode.value=="")
    		{
        		if ((CompareDateStrings(HEP247node.value, "12/31/1875") == -1) ||
                	   (CompareDateStringToToday(HEP247node.value) == 1))  
        		{

                	                
                	errorText = makeErrorMsg('ERR004', labelList.concat(HEP247node.fieldLabel));
                	tdErrorCell.innerText = errorText;

                	tdErrorCell.className = "error";
                	return true;
        		}
    		}
    		//with date of birth
    		else
    		{
        		if ((CompareDateStrings(HEP247node.value, dobNode.value) == -1) ||
            		   (CompareDateStringToToday(HEP247node.value) == 1))  
        		{
            
            			errorText = makeErrorMsg('ERR074', labelList.concat(HEP247node.fieldLabel).concat(dobNode.fieldLabel));
            			tdErrorCell.innerText = errorText;

            			tdErrorCell.className = "error";
            			return true;
        		}
    		}
      
      	}
    }

    return false;
}//Date of dose 1

//Date of dose 2
function HEP248()
{

    var labelList = new Array();
    var errorMessage = "";
    var errorText = "";
    var HEP248node = getElementByIdOrByName("HEP248");
    var HEP247node = getElementByIdOrByName("HEP247");
    var dobNode = getElementByIdOrByName("DEM115");
    var tdErrorCell = getTdErrorCell( HEP248node );

    if (isblank(HEP248node.value))
    {
         return false;
    }
    else
    {

       // First check if it is in mm/dd/yyyy format
           if (!isDate( HEP248node.value ) )
           {

              errorText = makeErrorMsg('ERR003', labelList.concat(HEP248node.fieldLabel));
              
              tdErrorCell.innerText = errorText;
              tdErrorCell.className = "error";

              return true;

            }
    }
      
      //first validate against 'Date of Dose 1' if patient has 'Date of Dose 1'
      if(!(HEP247node.value==""))
      {
      		if ((CompareDateStrings(HEP248node.value, HEP247node.value) == -1) ||
                   (CompareDateStringToToday(HEP248node.value) == 1))
                {
                	errorText = makeErrorMsg('ERR092', labelList.concat(HEP248node.fieldLabel).concat(HEP247node.fieldLabel));
		                    
		        tdErrorCell.innerText = errorText;
	                tdErrorCell.className = "error";
	                return true;
               }
      }
      else
      {
      		//second validate against DOB if patient has DOB and  'Date of Dose 1' is blank
       		if((!(dobNode.value=="")) && (HEP247node.value==""))
       		{
       			if ((CompareDateStrings(HEP248node.value, dobNode.value) == -1) ||
			   (CompareDateStringToToday(HEP248node.value) == 1))
			{
				  errorText = makeErrorMsg('ERR092', labelList.concat(HEP248node.fieldLabel).concat(dobNode.fieldLabel));
					                 
				  tdErrorCell.innerText = errorText;
				  tdErrorCell.className = "error";
				  return true;
			}
		}
		//last validate against 1875 and current date if DOB and 'Date of Dose 1' are blank
		if((dobNode.value=="") && (HEP247node.value==""))
		{
		       	if ((CompareDateStrings(HEP248node.value, '12/31/1875') == -1) ||
		       	   (CompareDateStringToToday(HEP248node.value) == 1))
		       	{
		       	          errorText = makeErrorMsg('ERR004', labelList.concat(HEP248node.fieldLabel));
		       		                 
		       		  tdErrorCell.innerText = errorText;
		       		  tdErrorCell.className = "error";
		       		  return true;
			}
		}
               
      }
        
    return false;
}//Date of dose 2


////Date of dose 3
function HEP249()
{

    var labelList = new Array();
    var errorMessage = "";
    var errorText = "";
    var HEP249node = getElementByIdOrByName("HEP249");
    var HEP248node = getElementByIdOrByName("HEP248");
    var HEP247node = getElementByIdOrByName("HEP247");
    var dobNode = getElementByIdOrByName("DEM115");
    var tdErrorCell = getTdErrorCell( HEP249node );

    if (isblank(HEP249node.value))
    {
                     return false;
    }
    else
    {

           // First check if it is in mm/dd/yyyy format
           if (!isDate( HEP249node.value ) )
           {
              
              errorText = makeErrorMsg('ERR003', labelList.concat(HEP249node.fieldLabel));
              tdErrorCell.innerText = errorText;
              tdErrorCell.className = "error";

              return true;

            }
      //first validate against 'Date of Dose 2' if patient has 'Date of Dose 2'
      if(!(HEP248node.value==""))
      {
      		if ((CompareDateStrings(HEP249node.value, HEP248node.value) == -1) ||
                   (CompareDateStringToToday(HEP249node.value) == 1))
                {
                	errorText = makeErrorMsg('ERR092', labelList.concat(HEP249node.fieldLabel).concat(HEP248node.fieldLabel));
		                    
		        tdErrorCell.innerText = errorText;
	                tdErrorCell.className = "error";
	                return true;
               }
      }
      else
      {
      		//second validate against DOB if patient has DOB, and  'Date of Dose 1', and  'Date of Dose 2' are blank
       		if((!(dobNode.value=="")) && (HEP248node.value=="") && (HEP247node.value==""))
       		{
       			if ((CompareDateStrings(HEP249node.value, dobNode.value) == -1) ||
			   (CompareDateStringToToday(HEP249node.value) == 1))
			{
				  errorText = makeErrorMsg('ERR092', labelList.concat(HEP249node.fieldLabel).concat(dobNode.fieldLabel));
					                 
				  tdErrorCell.innerText = errorText;
				  tdErrorCell.className = "error";
				  return true;
			}
		}
		//third validate against 1875 and current date if DOB, and  'Date of Dose 1', and 'Date of Dose 2' are blank
		if((dobNode.value=="") && (HEP248node.value=="") && (HEP247node.value==""))
		{
		       	if ((CompareDateStrings(HEP249node.value, '12/31/1875') == -1) ||
		       	   (CompareDateStringToToday(HEP249node.value) == 1))
		       	{
		       	          errorText = makeErrorMsg('ERR004', labelList.concat(HEP249node.fieldLabel));
		       		                 
		       		  tdErrorCell.innerText = errorText;
		       		  tdErrorCell.className = "error";
		       		  return true;
			}
		}
		
		//fourth validate against 'Date of Dose 1' if patient's  'Date of Dose 2' is blank, but 'Date of Dose 1' has value
		if((HEP248node.value=="") && (!(HEP247node.value=="")))
		{
		      if ((CompareDateStrings(HEP249node.value, HEP247node.value) == -1) ||
			 (CompareDateStringToToday(HEP249node.value) == 1))
		      {
						  errorText = makeErrorMsg('ERR092', labelList.concat(HEP249node.fieldLabel).concat(HEP247node.fieldLabel));
							                 
						  tdErrorCell.innerText = errorText;
						  tdErrorCell.className = "error";
						  return true;
		      }
		}
		
		//fifth validate against patient's DOB if 'Date of Dose 1','Date of Dose 2' are blank, but 'Date of Dose 3' has value
		if((HEP248node.value=="") && (HEP247node.value=="") && (!(dobNode.value=="")))
		{
			if ((CompareDateStrings(HEP249node.value, HEP247.value) == -1) ||
			 (CompareDateStringToToday(HEP249node.value) == 1))
			{
				  errorText = makeErrorMsg('ERR092', labelList.concat(HEP249node.fieldLabel).concat(dobNode.fieldLabel));
									                 
				  tdErrorCell.innerText = errorText;
				  tdErrorCell.className = "error";
				  return true;
			}
		}
		//sixth validate against 1875 and current date if DOB, and  'Date of Dose 1', and 'Date of Dose 2' are blank
		if((dobNode.value=="") && (HEP248node.value=="") && (HEP247node.value==""))
		{
		       	if ((CompareDateStrings(HEP249node.value, '12/31/1875') == -1) ||
			   (CompareDateStringToToday(HEP249node.value) == 1))
			{
			          errorText = makeErrorMsg('ERR004', labelList.concat(HEP249node.fieldLabel));
				       		                 
				  tdErrorCell.innerText = errorText;
				  tdErrorCell.className = "error";
				  return true;
			}
		}
               
      }
    }
    return false;
}//Date of dose 3

function HEP244()
{

    var trHEP245node = getElementByIdOrByName("trHEP245");
  
    var HEP244node =  getElementByIdOrByName("supplemental_s[4].obsValueCodedDT_s[0].code");
    var HEP243node =  getElementByIdOrByName("supplemental_s[3].obsValueCodedDT_s[0].code");

    if(HEP244node.value == "Y" || HEP243node.value == "Y" )
    {

        trHEP245node.setAttribute("className", "visible");
        return false;
    }
    else
    {

        nestedElementsControllerClearInternalInputs(trHEP245node);
        trHEP245node.setAttribute("className", "none");
        return true;
    }

}

validationArray[13] = HEP251;
validationArray[14] = HEP247;
validationArray[15] = HEP248;
validationArray[16] = HEP249;


/*
tabValidationArray[3] = HEP250;
tabValidationArray[4] = HEP247;
tabValidationArray[5] = HEP248;
tabValidationArray[6] = HEP249;
*/
