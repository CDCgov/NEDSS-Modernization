/**
	* Description   :  This function will restrict the number of checked checkboxes
	* param @field  :  String that contains the field name for checkboxes.
	*/
var showErrorStatement = true;
var maxChecked = 2;
var totalChecked = 0;
function CountChecked(field) {
	var labelList = new Array();

	if (field.checked) {

		totalChecked += 1;

	}
	else
		totalChecked -= 1;
	if (totalChecked > maxChecked) {
		var error = makeErrorMsg('ERR118', labelList.concat(""));
		//alert ("Only two patients can be selected to Compare. Uncheck one of the\n"+
		//"two previously selected patients before selecting another patient.");
		alert(error);
		field.checked = false;
		totalChecked = maxChecked;
	}
}

var maxSurvivorChecked = 1;
var totalSurvivorChecked = 0;
function CountSurvivorChecked(field) {
	var labelList = new Array();

	if (field.checked) {
		totalSurvivorChecked += 1;
	}
	else
		totalSurvivorChecked -= 1;
	if (totalSurvivorChecked > maxSurvivorChecked) {
		var error = makeErrorMsg('ERR192', labelList.concat(""));
		//alert ("Only one patient can be selected to Survive. Uncheck the\n"+
		//"previously selected survivor before selecting another patient.");
		alert(error);
		field.checked = false;
		totalSurvivorChecked = maxSurvivorChecked;
	}
	if (field.checked)
		findAndCheckSiblingWithMergeId(field);
}
function findAndCheckSiblingWithMergeId(ele) {
	//check the associated merge checkbox if unchecked
	var tdParent = ele.parentElement;
	var trParent = tdParent.parentElement;
	var siblingTrs = trParent.children;
	var mergeSiblingTr = siblingTrs[5]; //merge column
	var siblings = mergeSiblingTr.children;

	for (var i = siblings.length; i--;) {
		if (siblings[i].id == 'merge') {
			sibWithId = siblings[i];
			if (sibWithId.checked == false)
				sibWithId.checked = true;
			break;
		}
	}
}

function openWindowForCompare1() {
	var x = 100;	//screen.availWidth;
	var y = 100;    //screen.availHeight;
	var z = 0;
	var firstComapre = "";

	var errorTD = getElementByIdOrByName("errorRange");
	setAttributeClass(errorTD, "none");
	var errorText = "";
	var secondCompare = "";
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "compare" && inputs.item(i).checked == true) {
			z++;
			if (z == "1")
				firstCompare = inputs.item(i).value;
			if (z == "2")
				secondCompare = inputs.item(i).value;
		}
	}
	if (z == "0" || z == "1") {
		//errorText = "Please select two patients in order to compare the patient data.\n";
		errorText = makeErrorMsg('ERR116', labelList.concat(""));
		errorTD.innerText = errorText;
		setAttributeClass(errorTD, "error");
		document.location.href = "#top";
		return true;
	}
	else {

		window.open("/nbs/deduplication/compareForMergeSI", "getData", "left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		return false;
	}
}
function openWindowForCompare2() {
	var x = 100;	//screen.availWidth;
	var y = 100;    //screen.availHeight;
	var z = 0;
	var firstComapre = "";

	var errorTD = getElementByIdOrByName("errorRange");
	setAttributeClass(errorTD, "none");
	var errorText = "";
	var secondCompare = "";
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "compare" && inputs.item(i).checked == true) {
			z++;
			if (z == "1")
				firstCompare = inputs.item(i).value;
			if (z == "2")
				secondCompare = inputs.item(i).value;
		}
	}
	if (z == "0" || z == "1") {
		errorText = "Please select two patients in order to compare the patient data.\n";
		errorTD.innerText = errorText;
		setAttributeClass(errorTD, "error");
		document.location.href = "#top";
		return true;
	}
	else {

		window.open("/nbs/deduplication/compareForMergeSI1", "getData", "left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		return false;
	}
}
function mergePersons() {

	var z = 0;
	var errorTD = getElementByIdOrByName("errorRange");
	setAttributeClass(errorTD, "none");
	var errorText = "";
	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");
	var labelList = new Array();

	for (var j = 0; j < inputs.length; j++) {
		//var testj = inputs.item(j).id;
		if (inputs.item(j).id == "survivor" && inputs.item(j).checked == true
			&& inputs.item(j - 1).id == "merge"
			&& inputs.item(j - 1).checked == false) { // errorText = "You
			// selected a
			// PatientId as the
			// Survivor but you
			// unchecked the
			// associated Merge
			// checkbox. Please
			// recheck the merge
			// checkbox or
			// choose another
			// Survivor.\n";
			errorText = makeErrorMsg('ERR193', labelList.concat(""));
			errorTD.innerText = errorText;
			setAttributeClass(errorTD, "error");
			document.location.href = "#top";
			return true;
		}
	}



	//var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged\n"+
	//                 "into a single patient record. Select OK to continue, or Cancel to not continue."
	var confirmMsg = makeErrorMsg('ERR115', labelList.concat(""));
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "merge" && inputs.item(i).checked == true) {
			z++;
		}
	}
	if (z == "0" || z == "1") {
		//errorText = "Please select a minimum of two patients in order to merge the patient data.\n";
		errorText = makeErrorMsg('ERR117', labelList.concat(""));
		errorTD.innerText = errorText;
		setAttributeClass(errorTD, "error");
		document.location.href = "#top";
		return true;
	}
	else {
		if (confirm(confirmMsg)) {
			frm.action = '/nbs/MergeCandidateList1.do?ContextAction=Merge';
			submitForm();
			return false;
		}
		else {
			return true;
		}
	}
}
function mergePersonsForSI() {

	var z = 0;
	var errorTD = getElementByIdOrByName("errorRange");
	setAttributeClass(errorTD, "none");
	var errorText = "";
	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");
	var labelList = new Array();

	//var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged\n"+
	//"into a single patient record. Select OK to continue, or Cancel to not continue."
	var confirmMsg = makeErrorMsg('ERR115', labelList.concat(""));
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "merge" && inputs.item(i).checked == true) {
			z++;
		}
	}
	if (z == "0" || z == "1") {
		//errorText = "Please select a minimum of two patients in order to merge the patient data.\n";
		errorText = makeErrorMsg('ERR117', labelList.concat(""));
		setText(errorTD, errorText);
		setAttributeClass(errorTD, "error");
		document.location.href = "#top";
		return true;
	}
	else {
		if (confirm(confirmMsg)) {
			frm.action = '/nbs/MergeCandidateList2.do?ContextAction=Merge';
			submitForm();
			return false;
		}
		else {
			return true;
		}
	}
}


function mergePersons5() {

	var z = 0;
	var tdErrorCell = getElementByIdOrByName("errorRange");
	tdErrorCell.setAttribute("className", "none");
	var errorText = "";
	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");

	var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged\n" +
		"into a single patient record. Select OK to continue, or Cancel to not continue."
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "merge" && inputs.item(i).checked == true) {
			z++;
		}
	}
	if (z == "0" || z == "1") {
		errorText = "Please select a minimum of two patients in order to merge the patient data.\n";
		tdErrorCell.innerText = errorText;
		tdErrorCell.className = "error";
		//errorTD.setAttribute("className", "error");
		document.location.href = "#top";
		return true;
	}
	else {
		if (confirm(confirmMsg)) {
			frm.action = '/nbs/deduplication/mergeConfirmationSystemId';
			submitForm();
			return false;
		}
		else {
			return true;
		}
	}
}

function mergePersons6() {

	var z = 0;
	var errorTD = getElementByIdOrByName("errorRange");
	setAttributeClass(errorTD, "none");
	var errorText = "";
	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");
	var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged\n" +
		"into a single patient record. Select OK to continue, or Cancel to not continue."
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "merge" && inputs.item(i).checked == true) {
			z++;
		}
	}
	if (z == "0" || z == "1") {
		errorText = "Please select a minimum of two patients in order to merge the patient data.\n";
		errorTD.innerText = errorText;
		setAttributeClass(errorTD, "error");
		document.location.href = "#top";
		return true;
	}
	else {
		if (confirm(confirmMsg)) {
			frm.action = '/nbs/deduplication/mergeConfirmationSystemId0';
			submitForm();
			return false;
		}
		else {
			return true;
		}
	}
}

function callNoMergePerson() {
	var z = 0;

	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");
	var labelList = new Array();
	//var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged into a single patient record.Select OK to continue, or Cancel to not continue."
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "merge" && inputs.item(i).checked == true) {
			z++;
		}
	}
	if (z != "0") {
		var error = makeErrorMsg('ERR136', labelList.concat(""));
		alert(error);
		//alert("All Merge checkboxes must be deselected before selecting the \n"+
		//"No Merge button.Please deselect the checkboxes and try again.");
	}
	else {

		frm.action = '/nbs/MergeCandidateList2.do?ContextAction=NoMerge';
		submitForm();
	}

}

function callNoMergePersonNew() {
	var z = 0;

	$j('.selectCheckBoxMerge:checked').each(function() {
		z++;
	});
	if (z != "0") {
		// var error = makeErrorMsg('ERR136',labelList.concat(""));
		// alert(error);
		alert("All Merge checkboxes must be deselected before selecting the No Merge button. Please deselect the checkboxes and try again.");
	} else {
		document.forms[0].action = '/nbs/MergeCandidateList2.do?ContextAction=NoMerge';
		document.forms[0].submit();
	}

}

function callNoMergePerson6() {
	var z = 0;

	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");
	// var confirmMsg = "If you continue with the Merge action, the selected
	// patient records will be merged into a single patient record. Select OK to
	// continue, or Cancel to not continue."
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.item(i).id == "merge" && inputs.item(i).checked == true) {
			z++;
		}
	}
	if (z != "0") {
		alert("All Merge checkboxes must be deselected before selecting the \n"
			+ "No Merge button.Please deselect the checkboxes and try again.");

	} else {

		frm.action = '/nbs/deduplication/mergeCandidateListSi0';
		submitForm();
	}



}
function cancel() {

	if (CheckForChildWindow() && gSubmitOnce) {
		var target = "";
		var confirmMsg;
		var labelList = new Array();


		if (arguments.length > 0)
			target = arguments[0];
		if (arguments.length > 1) {
			confirmMsg = arguments[1];

		}

		else {
			confirmMsg = makeErrorMsg('ERR013', labelList.concat(""));
		}
		//	if (confirm(confirmMsg))
		//	{
		gSubmitOnce = false;
		window.location = target;
		//	}
		//	else
		//	{
		//		 gSubmitOnce = true;
		//		return;
		//	}
	}

}


function callSkip(value) {
	if(value==null || value==0){
		alert("There are no more merge candidate groups available for review.");
		return false;
	}else{
		document.forms[0].action = '/nbs/MergeCandidateList2.do?ContextAction=Skip';
		document.forms[0].submit();
	}
}


