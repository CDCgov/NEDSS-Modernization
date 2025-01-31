/**
 *  avr.save.js.
 *  JavaScript for save.xsp.
 *  Calls functions that are in sniffer.js and nedss.js.
 *  @author Ed Jenkins
 */

/**
 *  Validates the form.
 *  @return true if the form is valid or false if not.
 */
function isValid()
{
    HideErrors();
    var tdName = getElementByIdOrByName("id_name_error");
    var tdDesc = getElementByIdOrByName("id_desc_error");
    var tdSection = getElementByIdOrByName("id_R_S01_error");
    var s = "";
    var sectValue = "";
    var x = 0;
    document.frm.id_desc.value = trimNewLine(document.frm.id_desc.value);
    s = document.frm.id_name.value;
    sectValue = document.frm.id_R_S01.value;
    
    
    x = s.length;
    sectVallength = sectValue.length 
    if(x < 1)
    {
      s = "Please enter a name for this report.";
      ShowError(tdName, s);
                
    }
    if(sectVallength < 1)
    {
          sectValue = "Please enter a Section name for this report.";
          ShowError(tdSection, sectValue);
                    
    }
    if((x < 1) || (sectVallength < 1))
    {
        
      return false;
        
    }
    
    s = document.frm.id_desc.value;
    x = CheckLenGeneric(s, 255, tdDesc, "Description");
    if(x == 0)
    {
        return true;
    }
    return false;
}


function ReportSubmitOnKeyPressEnter(_this)
{
	
    var x = 0;    
    if(is_ie)
    {
    	
        x = window.event.keyCode;
    }
    else
    {	
        x = _this.which;
    }
    
    if (x!=13){
    	
    	return true;
    }
    if(x == 13)
    {
    
    	if(!isValid())
    	{
    		
    		return false;	
    	}    
    	else
    	{    	
    	document.frm.id_mode.value = "edit";
	document.frm.id_ObjectType.value = REPORT;
        document.frm.id_OperationType.value = SAVE_AS_NEW_REPORT;
        post();
        }
        
    }
 }
 function trimNewLine( str ) {
		return str.replace(/[\n\r]+/g, " ");
 } 
