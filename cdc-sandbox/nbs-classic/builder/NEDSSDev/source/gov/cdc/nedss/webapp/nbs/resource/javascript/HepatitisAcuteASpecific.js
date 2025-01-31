//Year last shot received
function HEP149()
{
        var labelList = new Array();
        var errorMessage = "";
        var errorText = "";
      //  var HEP149node = getElementByIdOrByName("supplemental_s[18].obsValueCodedDT_s[0].code");
        var HEP149node = getElementByIdOrByName("supplemental_s[20].obsValueNumericDT_s[0].numericValue1_s");
        var dobNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP149node );

        var now=new Date();
        var currentYear = now.getFullYear();

        var matchYear = /^\d{4}$/;


        if( (HEP149node.value!="") && (HEP149node.value.match(matchYear) == null) )
        {
		errorText = makeErrorMsg('ERR076', labelList.concat(HEP149node.fieldLabel));
		tdErrorCell.innerText = errorText;
		
		tdErrorCell.className = "error";
                   
        // no match, it is not in yyyy format.

        return true;
        }
        // no date of birth
        if(dobNode.value=="")
        {

            if ((HEP149node.value!="") && (window.isNaN(HEP149node.value)==false) && ((currentYear<HEP149node.value) || (HEP149node.value<1875)))
            {
                    
                    errorText = makeErrorMsg('ERR092', labelList.concat(HEP149node.fieldLabel).concat('1875'));
                    tdErrorCell.innerText = errorText;

                    tdErrorCell.className = "error";
                    return true;
            }
        }
        //with date of birth
        else
        {
            var birthYear = dobNode.value.substring(6,10);
            if (HEP149node.value!="" && window.isNaN(HEP149node.value)==false && (currentYear<HEP149node.value || HEP149node.value<birthYear))
            {
                
                errorText = makeErrorMsg('ERR082', labelList.concat(HEP149node.fieldLabel).concat(birthYear));
                tdErrorCell.innerText = errorText;

                tdErrorCell.className = "error";
                return true;
            }
        }
    return false;
}//Year last shot received


//Date last dose received
   function HEP151()
   {
    var errorMessage = "";
        var errorText = "";
        var labelList = new Array();
        var HEP151node = getElementByIdOrByName("supplemental_s[22].obsValueDateDT_s[0].fromTime_s");

        var dateOfBirthNode = getElementByIdOrByName("DEM115");
        var tdErrorCell = getTdErrorCell( HEP151node );
            if (isblank(HEP151node.value)){
                 return false;
            }
            else
            {
            var labelList = new Array();
                // First check if it is in mm/dd/yyyy format
                if (!isDate( HEP151node.value ) &&(HEP151node.fieldLabel))
                {

                errorText = makeErrorMsg('ERR003', labelList.concat(HEP151node.fieldLabel));

                tdErrorCell.innerText = errorText;
                tdErrorCell.className = "error";

            return true;
                }//if
             }//else

        // no date of birth
        if(dateOfBirthNode.value=="")
        {
            if ((CompareDateStrings(HEP151node.value, "12/31/1875") == -1) ||
                (CompareDateStringToToday(HEP151node.value) == 1))
            {
                
                errorText = makeErrorMsg('ERR004', labelList.concat(HEP151node.fieldLabel));
                tdErrorCell.innerText = errorText;
                tdErrorCell.className = "error";
                return true;
            }
         }
         //with date of birth
         else
         {
            if ((CompareDateStrings(HEP151node.value, dateOfBirthNode.value) == -1) ||
               (CompareDateStringToToday(HEP151node.value) == 1))
            {
              
              errorText = makeErrorMsg('ERR092', labelList.concat(HEP151node.fieldLabel).concat(dateOfBirthNode.fieldLabel) );
              tdErrorCell.innerText = errorText;
              tdErrorCell.className = "error";
              return true;
            }
          }


    return false;
   }//Date last dose received


validationArray[13] = HEP149;
validationArray[14] = HEP151;

/*
tabValidationArray[3] = HEP149;
tabValidationArray[4] = HEP150;
*/
