/* Manage User Email Alert Starts here*/
function loadEmail()
{
    document.forms[0].action ="/nbs/EmailAlert.do?method=loadEmail";
    document.forms[0].submit();
}

function handleEmail()
{
    document.forms[0].action ="/nbs/EmailAlert.do?method=updateEmail";
}

function  Cancel()
{
    var message="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue";
    var ok = confirm(message);
    if(ok) 
    {
        getElementByIdOrByName("Msg") == null ? "" : getElementByIdOrByName("Msg").style.display ="none";
        getElementByIdOrByName("error1") == null ? "" : getElementByIdOrByName("Msg").style.display ="none";
        getElementByIdOrByName("usersList").value="";
        getElementByIdOrByName("emailId1").value ="";
        getElementByIdOrByName("emailId2").value ="";
        getElementByIdOrByName("emailId3").value ="";
        getElementByIdOrByName("updateBtn").disabled = true;
    }
    else {
        return false;
    }
}

function disableButtonsF1()
{
    if (getElementByIdOrByName("usersList").value=="" ){
        getElementByIdOrByName("updateBtn").disabled = true;
    }
    
	if(getElementByIdOrByName("usersList").value!="" ) {
        if((getElementByIdOrByName("emailId1").value =="")&&(getElementByIdOrByName("emailId2").value =="")&&(getElementByIdOrByName("emailId3").value=="" )) {
            getElementByIdOrByName("updateBtn").disabled = true;
        }
    }
}

function deleteCaseButton()
{
    if(getElementByIdOrByName("usersList").value!="" ) {
        var id1 = getElementByIdOrByName("emailId1").value.length;
        var id2 = getElementByIdOrByName("emailId2").value.length;
        var id3 = getElementByIdOrByName("emailId3").value.length;
        if(id1 > 0 || id2 > 0 || id3 >0 ) {
            var btn =getElementByIdOrByName("updateBtn");
            btn.disabled = false;
        }
    }
}

function deleteMsg()
{
    if(getElementByIdOrByName("usersList").value != null) {
        var s1 = trim(getElementByIdOrByName("emailId1").value);
        var s2 = trim(getElementByIdOrByName("emailId2").value);
        var s3 = trim(getElementByIdOrByName("emailId3").value);
        if(s1 == "" && s2 == "" && s3=="") {
            msg ="By deleting all associated email addresses, you will be removing this user from all existing alerts. Select OK to continue, or Cancel to not continue.";
            var ok=confirm(msg);
            if (ok){
                handleEmail();
            }
            else {
                loadEmail();
            }
        }
        else {
            handleEmail();
        }
    }
    else {
        handleEmail();
    }
}

function updateEmail()
{
    var emailvali=emailErrorMessage();
    if(emailvali) {
	    if((trim(getElementByIdOrByName("emailId1").value)) != "" && (isEmpty(getElementByIdOrByName("emailId1").value) == false))
	    {
	        handleEmail();
	    }
	    
	    if((trim(getElementByIdOrByName("emailId2").value)) != "" && (isEmpty(getElementByIdOrByName("emailId2").value) == false))
	    {
	        handleEmail();
	    }
	    
	    if((trim(getElementByIdOrByName("emailId3").value)) != "" && (isEmpty(getElementByIdOrByName("emailId3").value) == false))
	    {
	        handleEmail();
	    }
	}
	else {
        return false;
    }
}

<!-- Begin
    function emailCheck (emailStr) 
    {
        /* The following pattern is used to check if the entered e-mail address
        fits the user@domain format.  It also is used to separate the username
        from the domain. */
        var emailPat=/^(.+)@(.+)$/
        
        /* The following string represents the pattern for matching all special
        characters.  We don't want to allow special characters in the address.
        These characters include ( ) < @ , ; : \ " . [ ]  !#$%&'*+-/=?^_`.{|}~  */
        var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]"
        
        //var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]!#$%&'*+-/=?^_`.{|}~"
        /* The following string represents the range of characters allowed in a
        username or domainname.  It really states which chars aren't allowed. */
        var validChars="\[^\\s" + specialChars + "\]"
        
		/* The following pattern applies if the "user" is a quoted string (in
		   which case, there are no rules about which characters are allowed
		   and which aren't; anything goes).  E.g. "jiminy cricket"@disney.com
		   is a legal e-mail address. */
        var quotedUser="(\"[^\"]*\")"
        
		/* The following pattern applies for domains that are IP addresses,
		   rather than symbolic names.  E.g. joe@[123.124.233.4] is a legal
		   e-mail address. NOTE: The square brackets are required. */
		var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/
		
		/* The following string represents an atom (basically a series of
		   non-special characters.) */
        var atom=validChars + '+'
        
		/* The following string represents one word in the typical username.
		   For example, in john.doe@somewhere.com, john and doe are words.
		   Basically, a word is either an atom or quoted string. */
		var word="(" + atom + "|" + quotedUser + ")"
		
        // The following pattern describes the structure of the user
        var userPat=new RegExp("^" + word + "(\\." + word + ")*$")
        
		/* The following pattern describes the structure of a normal symbolic
		   domain, as opposed to ipDomainPat, shown above. */
		var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$")

		/* Finally, let's start trying to figure out if the supplied address is
		   valid. */
		   
		/* Begin with the coarse pattern to simply break up user@domain into
		   different pieces that are easy to analyze. */
		var matchArray=emailStr.match(emailPat)
		if (matchArray==null) {
		  /* Too many/few @'s or something; basically, this address doesn't
		     even fit the general mould of a valid e-mail address. */
			return false
		}
		
		var user=matchArray[1]
		var domain=matchArray[2]

		// See if "user" is valid
		if (user.match(userPat)==null) {
		    // user is not valid
		    return false
		}
		
		/* if the e-mail address is at an IP address (as opposed to a symbolic
		   host name) make sure the IP address is valid. */
		var IPArray=domain.match(ipDomainPat)
		if (IPArray!=null) {
		    // this is an IP address
			  for (var i=1;i<=4;i++) {
			    if (IPArray[i]>255) {
			        return false
			    }
		    }
		    return true
		}

		// Domain is symbolic name
		var domainArray=domain.match(domainPat)
		if (domainArray==null) {
			    return false
		}

		/* domain name seems valid, but now make sure that it ends in a
		   three-letter word (like com, edu, gov) or a two-letter word,
		   representing country (uk, nl), and that there's a hostname preceding
		   the domain or country. */
		
		/* Now we need to break up the domain to get a count of how many atoms
		   it consists of. */
		var atomPat=new RegExp(atom,"g")
		var domArr=domain.match(atomPat)
		var len=domArr.length
		if (domArr[domArr.length-1].length<2 ||
		    domArr[domArr.length-1].length>6) {
		   // the address must end in a two letter or other TLD including museum
		      return false
		}
		
		// Make sure there's a host name preceding the domain.
		if (len<2) {
		   var errStr="This address is missing a hostname!"
		
		   return false
		}
		
		// If we've got this far, everything's valid!
		return true;
    }
//  End -->

function trim(str)
{
    while (str.charAt(0) == " ") {
        // remove leading spaces
        str = str.substring(1);
    }
    
    while (str.charAt(str.length - 1) == " "){
        // remove trailing spaces
        str = str.substring(0,str.length - 1);
    } 
    return str;
}

function emailErrorMessage()
{
    $j(".infoBox").hide();

    var errors = new Array();
    var index = 0;
    var isError = false;
    var atLeastOneSearchCriteria = false;
    var errorText="";
    var errorMsg="";

    if((trim(getElementByIdOrByName("emailId2").value)) == "" && ((trim(getElementByIdOrByName("emailId3").value)) != "")) {
        atLeastOneSearchCriteria = true;
        errors[index++] = "Please enter an Email Address for Email Address 1 and Email Address 2";
        getElementByIdOrByName("email1").style.color = "990000";
        getElementByIdOrByName("email2").style.color = "990000";
    }
    else if((trim(getElementByIdOrByName("emailId1").value)) == "" && ((trim(getElementByIdOrByName("emailId2").value)) != "")) {
        atLeastOneSearchCriteria = true;
        errors[index++] = "Please enter an Email Address for Email Address 1";
        getElementByIdOrByName("email1").style.color = "990000";
    }
    else if((trim(getElementByIdOrByName("emailId1").value)) == "" && ((trim(getElementByIdOrByName("emailId3").value)) != "")) {
        atLeastOneSearchCriteria = true;
        errors[index++] = "Please enter an Email Address for Email Address 1";
        getElementByIdOrByName("email1").style.color = "990000";
    }

	if((trim(getElementByIdOrByName("emailId1").value)) != "" && (isEmpty(getElementByIdOrByName("emailId1").value) == false))
	{
        var msg = emailCheck(getElementByIdOrByName("emailId1").value);
        if (!msg) {
            atLeastOneSearchCriteria = true;
            errors[index++] = "Email Address 1 is not valid";
            getElementByIdOrByName("email1").style.color = "990000";
        }
        else {
            getElementByIdOrByName("email1").style.color = "black";
        }
    }
    
	if((trim(getElementByIdOrByName("emailId2").value)) != "" && (isEmpty(getElementByIdOrByName("emailId2").value) == false))
	{
        var msg = emailCheck(getElementByIdOrByName("emailId2").value);
        if (!msg) {
            atLeastOneSearchCriteria = true;
            errors[index++] = "Email Address 2 is not valid";
            getElementByIdOrByName("email2").style.color = "990000";
        }
        else {
            getElementByIdOrByName("email2").style.color = "black";
        }
    }
    
	if((trim(getElementByIdOrByName("emailId3").value)) != "" && (isEmpty(getElementByIdOrByName("emailId3").value) == false))
	{
        var msg = emailCheck(getElementByIdOrByName("emailId3").value);
        if (!msg) {
            atLeastOneSearchCriteria = true;
            errors[index++] = "Email Address 3 is not valid";
            getElementByIdOrByName("email3").style.color = "990000";
        }
        else {
            getElementByIdOrByName("email3").style.color = "black";
        }
    }
    
    if (atLeastOneSearchCriteria == true ) {
        displayGlobalErrorMessage(errors);
        return false;
    }
    else if (!ChekDuplicates()) {
        var error = "Email Addresses entered are not unique";
        displayGlobalErrorMessage(error);
        isError = true;
        return false;
    }
    else {
        isError = false;
        return true;
    }
}

function ChekDuplicates()
{
    var emailcheck1=(trim(getElementByIdOrByName("emailId1").value));
    var emailcheck2=(trim(getElementByIdOrByName("emailId2").value));
    var emailcheck3=(trim(getElementByIdOrByName("emailId3").value));
    var email1 = getElementByIdOrByName("email1");
    var email2 = getElementByIdOrByName("email2");
    var email3 = getElementByIdOrByName("email3");
    
    if ((emailcheck1 != "") && (emailcheck2 != "") && (emailcheck3 != "")) {
        if (emailcheck1 == emailcheck2 && emailcheck2 == emailcheck3 && emailcheck1 == emailcheck3) {
            getElementByIdOrByName("email1").style.color = "990000";
            getElementByIdOrByName("email2").style.color = "990000";
            getElementByIdOrByName("email3").style.color = "990000";
            return false;
        }
    }
    else {
        getElementByIdOrByName("email1").style.color = "black";
        getElementByIdOrByName("email2").style.color = "black";
        getElementByIdOrByName("email3").style.color = "black";
    }
    
    if ((emailcheck1 != "") && (emailcheck2 != "")) {
        if (emailcheck1 == emailcheck2) {
            getElementByIdOrByName("email1").style.color = "990000";
            getElementByIdOrByName("email2").style.color = "990000";
            return false;
        }
    }
    else {
        getElementByIdOrByName("email1").style.color = "black";
        getElementByIdOrByName("email2").style.color = "black";
    }
    
    if (emailcheck2 != "" && emailcheck3 != "") {
        if (emailcheck2 == emailcheck3) {
            getElementByIdOrByName("email2").style.color = "990000";
            getElementByIdOrByName("email3").style.color = "990000";
            return false;
        }
    }
    else {
        getElementByIdOrByName("email2").style.color = "black";
        getElementByIdOrByName("email3").style.color = "black";
    }
    
    if (emailcheck1 != "" && emailcheck3 != "") {
        if (emailcheck1 == emailcheck3) {
            getElementByIdOrByName("email1").style.color = "990000";
            getElementByIdOrByName("email3").style.color = "990000";
            return false;
        }
    }
    else {
        getElementByIdOrByName("email1").style.color = "black";
        getElementByIdOrByName("email3").style.color = "black";
    }
    return true;
}

/* Manage User Email Alert Ends here*/

/*  Manage Alert Script starts here */
function cancelAlert()
{
    var confirmMsg="If you continue with the Cancel action,you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
    if (confirm(confirmMsg)) {
        document.forms[0].action ="/nbs/AlertAdministration.do?method=manageAlert&focus=systemAdmin1";
        document.forms[0].submit();
        return false;
    }else {
        return false;
    }
}

function removeSelectedOptions(from)
{
	for(var i=(from.options.length-1);i>=0;i--){
		var o=from.options[i];if(o.selected){from.options[i] = null;
	}
}
    from.selectedIndex = -1;
}

function copySelectedOptions(from,to)
{
    var options = new Object();
    for(var i=0;i<to.options.length;i++) 
    {
        options[to.options[i].value] = to.options[i].text;
    }
    
    for(var i=0;i<from.options.length;i++)
    {
        var o = from.options[i];
        if(o.selected){
            if(options[o.value] == null || options[o.value] == "undefinedx" || options[o.value]!=o.text){
                to.options[to.options.length] = new Option( o.text, o.value, false, false);
            }
        }
    }

    if((arguments.length<3) ||(arguments[2]==true)) {
        sortSelect(to);
    }
    from.selectedIndex = -1;to.selectedIndex = -1;
}

function setOptionValue()
{
    var x=document.forms[0].selectedAlertUserIds.length;
    for(var i=0;i<x;i++)
    {
        document.forms[0].selectedAlertUserIds.options[i].selected=true;
    }
}

function SearchAlertReqField() 
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError= false;

	var cond= getElementByIdOrByName("searchParamsVO.condition_CD");
	var jur= getElementByIdOrByName("searchParamsVO.jurisdiction_CD");
	var phe= getElementByIdOrByName("searchParamsVO.event_CD");

    if(cond != null && cond.value.length == 0) {
        errors[index++] = "Condition is required";
        getElementByIdOrByName("cond").style.color="990000";
        isError = true;
	}
	else{
		getElementByIdOrByName("cond").style.color="black";
	}
	
    if(jur != null && jur.value.length == 0) {
        errors[index++] = "Jurisdiction is required";
        getElementByIdOrByName("jur").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("jur").style.color="black";
    }
	
    if(phe != null && phe.value.length == 0) {
        errors[index++] = "Public Health Event is required";
        getElementByIdOrByName("phe").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("phe").style.color="black";
    }

	if (isError) {
		displayGlobalErrorMessage(errors);
    }

    return isError;
}

function validateAlert()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;

	var sev= getElementByIdOrByName("alertClientVO.severity_code");

	if( document.forms[0].selectedAlertUserIds.length == 0) {
        errors[index++] = "Select User is required";
        getElementByIdOrByName("user").style.color="990000";
        isError = true;
	}
	else{
		getElementByIdOrByName("user").style.color="black";
	}
	
	if(sev != null && sev.value.length == 0) {
        errors[index++] = "Severity is required";
        getElementByIdOrByName("sev").style.color="990000";
		isError = true;
	}
	else{
		getElementByIdOrByName("sev").style.color="black";
	}

    if(isError) {
        displayErrors("alertDetailsFormEntryErrors", errors);
	}
	return isError;
}