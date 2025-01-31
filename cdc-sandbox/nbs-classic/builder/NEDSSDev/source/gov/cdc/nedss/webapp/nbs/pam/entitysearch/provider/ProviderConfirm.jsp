<%@ include file="/jsp/tags.jsp" %>
<%@ page import="java.util.*"%>
<%@ page isELIgnored ="false" %>
<html lang="en">
	<head>
	<base target="_self">
		<meta http-equiv="MSThemeCompatible" content="yes"/>
		<title>ProviderConfirm</title>
		<%@ include file="/jsp/resources.jsp" %>
		<script type='text/javascript' src='/nbs/dwr/interface/JPamForm.js'></script>	
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JCTContactForm.js"></SCRIPT>	
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="TBSpecific.js"></SCRIPT>					
		<script language="JavaScript">
			

	function populateInvestigator(uid, identifier)
				{
					//var parent = window.opener;
					//	                                   
		   var opener = getDialogArgument();
                     var pview = getElementByIdOrByNameNode("pamview", opener.document);

                     if(identifier == "INV180" || identifier == "INV181" || identifier == "INV182"){
                    	 pview = getElementByIdOrByNameNode("pageview", opener.document); 
                    		
                     }
			   
                    //var parent = window.opener;
                    var parent = opener;
                    var parentDoc = parent.document;
				  	dwr.util.setValue(identifier, "");
				  	if(identifier == "INV207" || identifier == "INV225"  || identifier == "INV247"){
					      JPamForm.getDwrPopulateInvestigatorByUid(uid,identifier, function(data) {
						   	dwr.util.setEscapeHtml(false);
						   	dwr.util.setValue(parentDoc.getElementById("pamClientVO.answer(INV207)"), "");
						   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
						   	dwr.util.setValue(parentDoc.getElementById("investigator.personUid"), uid);
						   	parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
							parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
							parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
							parentDoc.getElementById("clear"+identifier).className="";
							parentDoc.getElementById(identifier+"SearchControls").className="none";
							// show the date assigned to investigator field
	                        if (identifier == "INV207") {
	                            parent.enabledDateAssignedToInvestigation();
	                        }
					   	self.close();
					    });
				  	}else if(identifier == "CON137" || identifier == "CON147"  ){
				  		JCTContactForm.getDwrPopulateInvestigatorByUid(uid,identifier, function(data) {
						   	dwr.util.setEscapeHtml(false);
						   	dwr.util.setValue(parentDoc.getElementById("cTContactClientVO.answer(" +identifier + ")"), "");
						   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
						   	dwr.util.setValue(parentDoc.getElementById("investigator.personUid"), uid);
						   	parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
							parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
							parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
							parentDoc.getElementById("clear"+identifier).className="";
							parentDoc.getElementById(identifier+"SearchControls").className="none";
							// show the date assigned to investigator field
	                        if (identifier == "CON137") {
	                            parent.enabledDateAssignedToInvestigation();
	                        }
					   	self.close();
					    });
				  	}else if(identifier == "INV180" || identifier == "INV181" || identifier == "INV182")
				  	{
				  		JPageForm.getDwrPopulateInvestigatorByUid(uid,identifier, function(data) {
						   	dwr.util.setEscapeHtml(false);
						   	dwr.util.setValue(parentDoc.getElementById("cTContactClientVO.answer(INV180)"), "");
						   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
						   	dwr.util.setValue(parentDoc.getElementById("investigator.personUid"), uid);
						   	parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
							parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
							parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
							parentDoc.getElementById("clear"+identifier).className="";
							parentDoc.getElementById(identifier+"SearchControls").className="none";
							// show the date assigned to investigator field
	                        			if (identifier == "INV180") {
	                            				parent.enabledDateAssignedToInvestigation();
	                        				}
					   	self.close();
					    });
				  }else { //gst- could be an issue that is is not PageForm, i.e. Contact or Interview
			  			JPageForm.getDwrPopulateInvestigatorByUid(uid,identifier, function(data) {
					   	dwr.util.setEscapeHtml(false);
					   	dwr.util.setValue(parentDoc.getElementById("pageClientVO.answer(" +identifier + ")"), "");
					   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
					   	dwr.util.setValue(parentDoc.getElementById("investigator.personUid"), uid);
					   	parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
						parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
						parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
						parentDoc.getElementById("clear"+identifier).className="";
						parentDoc.getElementById(identifier+"SearchControls").className="none";
						//store UID in hidden field if present
						if (getElementByIdOrByNameNode("attributeMap."+identifier+"Uid", parentDoc) != null) {
							  getElementByIdOrByNameNode("attributeMap."+identifier+"Uid", parentDoc).value = uid;
							  if (identifier == "NBS139" || identifier == "NBS145" || identifier == "NBS161" || identifier == "NBS186" || identifier == "NBS197") {
							  	parent.stdUpdateCurrentProvider(identifier);
							  }
	        				}
					   	self.close();
					    });
				  	}
				  	 if (pview != null) {
			                pview.style.display = "none";                  
			              }
				}
	function closePopup()
    {              
            self.close();
            var opener = getDialogArgument();       
            var invest = getElementByIdOrByNameNode("pamview", opener.document);
            if (invest != null) {
                 invest.style.display = "none";  
            }                   
        
    }     		
	
		</script>

<%String queId = (String) request.getAttribute("identifier");%>

<body onload="populateInvestigator('${fn:escapeXml(providerUID)}','${fn:escapeXml(identifier)}')">
	
		
		<input type="hidden" name="uid" id="providerUID" value="${fn:escapeXml(providerUID)}"/>
</body>