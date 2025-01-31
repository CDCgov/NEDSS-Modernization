
function ProviderEditSelect(){
	
var formNode = getElementByIdOrByName("nedssForm");
var inputNodes = formNode.getElementsByTagName("input");

for(var i=0; i< inputNodes.length; i++ ) {
   if(inputNodes.item(i).name == "tba"){        
//alert("inputNodes.item(i).name" +inputNodes.item(i).name);

        var element1 = getElementByIdOrByName("c");
	var element2 = getElementByIdOrByName("n");
        var element  = getElementByIdOrByName("tba");  
	var tdErrorCell = getTdErrorCell( element) ;
	var labelList = new Array();
        var errorMessage = "";
	var errorText = "";
        var quickCodehiden = getElementByIdOrByName("quickCodeHide");
        var quickCode = getElementByIdOrByName("quickCodeIdDT.rootExtensionTxt");	
//alert(" quickCodehiden  " + quickCodehiden.value );
//alert(" quickCode  " + quickCode.value );

        if(!(getCorrectAttribute(element1,"checked",element1.checked))  && !(getCorrectAttribute(element2,"checked",element2.checked)))
        {
          //alert(" inside false");
        	errorText = makeErrorMsg('ERR105', labelList.concat(getCorrectAttribute(element,"fieldLabel",element.fieldLabel)));           
	   if( tdErrorCell.innerText == "" ){
	   	tdErrorCell.innerText = errorText;
	   	
		if(errorText!="" && tdErrorCell.textContent=="")//if there is no changes
			tdErrorCell.textContent=errorText;//Firefox
	   }
	   else 
	        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
	   tdErrorCell.className = "error";
	
       	
         return true; 
        }

/* Commented out as per the workproduct defect */

	if(element2.checked)
   	  {
           if(quickCodehiden.value!= null && quickCodehiden.value != "" && quickCodehiden.value == quickCode.value)
           {  

           if(!confirm("If you continue with the Edit action, the quick entry will be cleared and you will have to enter a new unique quick Code. Click OK to continue or Cancel to not continue"))
             
        	return true;
             else {
                callFocus();
                 return true;
		}
   	   }
        }

      }
  }

       // submitForm();
	return false;
	
   }
function ProviderEditERR119()
{
   var editQuickCode = getElementByIdOrByName("n");
   alert("quickCoderadio = "+editQuickCode.value);
   if(editQuickCode.checked)
   {
     confirm("If you continue with the Edit action, the quick entry will be cleared and you will have to enter a new unique quick Code\n"+
              "Click OK to continue or Cancel to not continue");
   }
    
} 

validationArray[0] = ProviderEditSelect;
//validationArray[1]  = ProviderEditERR119;


function callFocus(){
  
	
	var formNode = getElementByIdOrByName("nedssForm");
	var inputNodes = formNode.getElementsByTagName("input");

		for(var i=0; i< inputNodes.length; i++ ) {
			if(inputNodes.item(i).name == "quickCodeIdDT.rootExtensionTxt"){
				inputNodes.item(i).value="";
				//inputNodes.focus();
			}


		}
}