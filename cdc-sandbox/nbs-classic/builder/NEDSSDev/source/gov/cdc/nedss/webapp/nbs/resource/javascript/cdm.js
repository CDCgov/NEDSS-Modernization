
					
function Toggle(obj) {
	var element;
	for( i=0; i < document.all.length; i++) {
		element = document.all[i].id
		if(element != "") {
			var s = element.search(/tab/);
			if(s >= 0) { 
				//need to strip out tab and just get the name of the tab
				var sTab = element.slice(3);
				document.all["td" + sTab].bgColor = "000000";
				document.all["font" + sTab].color="F5F5F5";				
				document.all[i].style.display = "none";	
				document.all["imgStart" + sTab].src = "images/menu_start_noselect.gif";
				document.all["imgEnd" + sTab].src = "images/menu_end_noselect.gif";
			}	
		}
	}
    var objSty = document.all["tab"+obj].style;
    var objName = document.all["tab"+obj].id;
    document.all["td" + obj].bgColor = "CCCCCC";
    document.all["font" + obj].color = "000000"; 
    document.all["imgStart" + obj].src = "images/menu_start_select.gif";
   						    document.all["imgEnd" + obj].src = "images/menu_end_select.gif";

    if (objSty.display != "none") {
        objSty.display = "none";
    } else {
        objSty.display = "";
    }
}

function verify(f) {
	var msg;
	var empty_fields = "";
	var errors = "";
	for(var i=0; i < f.length; i++) {
		var e = f.elements[i];
		//-------------------------date validation---------------------						
		if ((e.type == "text") && (e.validate == "date")) {
			var datePat = /^(.+)\/(.+)\/(.+)$/;
			var formvalue = e.value;    // this is where you will have to enter FormName and FieldName
			var dateArray = formvalue.match(datePat);
			if (dateArray == null) {
			  	alert("You entered invalid date. Please try again...");    // This alert will pop up if slashes are missing
				 return false;
			}
			var monthValue = dateArray[1];
			var dayValue = dateArray[2];
			var yearValue = dateArray[3];
			
			var generalPat = /\D/;        // each value must be a number. This pattern will check if there are other characters than digits
			
			var dayCheck = dayValue.match(generalPat);
			var monthCheck = monthValue.match(generalPat);
			var yearCheck = yearValue.match(generalPat);
			if ((dayCheck != null) || (monthCheck != null) || (yearCheck != null)) {
				alert("Please enter a valid date in form 'dd/mm/yyyy'");
				return false;        // return false if the date contained characters other than digits
			}
			if ((dayValue > 31) || (monthValue > 12) || (yearValue < 1900) || (yearValue > 2000)) {
				alert("You entered invalid date. Please try again...");    // This alert will pop up if numbers are too big
				return false;
			}
			alert("validating date - " + e.value );
		}
		//---------------------------------------------check if empty-----------------------------------------------
		if ((e.validate == "required") && ((e.type == "text") || (e.type == "textarea"))) {
			if ((e.value == null) || (e.value == "") || isblank(e.value)) {
				alert("empty value alert" );
			}
		}
	}
	return false;
}
				
			