<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*" %>      
<%@ page import="gov.cdc.nedss.entity.person.dt.*" %>      
<%@ page import="gov.cdc.nedss.entity.organization.dt.*" %>                  

<html lang="en">
    <title>Organization Search Results</title>
    <head>
    <base target="_self">
        <%@ include file="/jsp/resources.jsp" %>
        <script type='text/javascript' src='/nbs/dwr/interface/JPamForm.js'></script>		
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JCTContactForm.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>	
        
        <SCRIPT Language="JavaScript" Src="PamSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="VaricellaSpecific.js"></SCRIPT>
         <SCRIPT Language="JavaScript" Src="contactTracingSpecific.js"></SCRIPT>
         <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JDecisionSupport.js"></SCRIPT>	
        <SCRIPT LANGUAGE="JavaScript">
            function populateOrganization(uid,identifier)
            {
            	DWREngine.setAsync(false);
            	
            	var opener = getDialogArgument();
                 var pview = getElementByIdOrByNameNode("pamview", opener.document);

                 if(identifier == "INV183" || identifier == "INV184"){
                	 pview = getElementByIdOrByNameNode("pageview", opener.document); 
                 }   
                 
				//var parent = window.opener;
				var parent = opener;
				var parentDoc = parent.document;
				dwr.util.setValue(identifier, "");	
				if(identifier == "INV218" || identifier == "INV233"){				
				  	    JPamForm.getDwrOrganizationDetailsByUid(uid,identifier, function(data) {
					   	dwr.util.setEscapeHtml(false);
					   	dwr.util.setValue(parentDoc.getElementById("pamClientVO.answer(INV218)"), "");
					   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
					   	dwr.util.setValue(parentDoc.getElementById("organization.organizationUid"), uid);
						parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
						parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
						parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
						parentDoc.getElementById("clear"+identifier).className="";
						parentDoc.getElementById(identifier+"SearchControls").className="none";
				   	self.close();
				    });
				}else if(identifier == "CON106"){
					JCTContactForm.getDwrOrganizationDetailsByUid(uid,identifier, function(data) {
				   	dwr.util.setEscapeHtml(false);
				   	dwr.util.setValue(parentDoc.getElementById("cTContactClientVO.answer(CON106)"), "");
				   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
				   	dwr.util.setValue(parentDoc.getElementById("organization.organizationUid"), uid);
					parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
					parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
					parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
					parentDoc.getElementById("clear"+identifier).className="";
					parentDoc.getElementById(identifier+"SearchControls").className="none";
			   	self.close();
			    });
				}else if(identifier == "INV183" || identifier == "INV184")
				{
					JPageForm.getDwrOrganizationDetailsByUid(uid,identifier, function(data) {
					   	dwr.util.setEscapeHtml(false);
					   	if(identifier == "INV183")
					   		dwr.util.setValue(parentDoc.getElementById("pageClientVO.answer(INV183)"), "");
					   	else if(identifier == "INV184"){
					   		dwr.util.setValue(parentDoc.getElementById("pageClientVO.answer(INV184)"), "");
					   	}
					   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
					   	dwr.util.setValue(parentDoc.getElementById("organization.organizationUid"), uid);
						parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
						parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
						parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
						parentDoc.getElementById("clear"+identifier).className="";
						parentDoc.getElementById(identifier+"SearchControls").className="none";
				   	self.close();
				    });
				} 
				else if(identifier == "INV185" || identifier == "INV186" || identifier == "INV187")
								{
									JBaseForm.getDwrOrganizationDetailsByUid(uid,identifier, function(data) {
										dwr.util.setEscapeHtml(false);
							
											dwr.util.setValue(parentDoc.getElementById("pageClientVO.answer(INV185)"), "");
				
										
										dwr.util.setValue(parentDoc.getElementById(identifier), data);
										dwr.util.setValue(parentDoc.getElementById("organization.organizationUid"), uid);
										parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
										parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
										parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
										parentDoc.getElementById("clear"+identifier).className="";
										parentDoc.getElementById(identifier+"SearchControls").className="none";
										parentDoc.getElementsByName("attributeMap."+identifier+"Uid")[0].value=uid;
									self.close();
									});
								}
			else if(identifier == "PartPer"){
			  		JDecisionSupport.getDwrOrganizationDetailsByUid(uid, identifier,function(data) {
					   	dwr.util.setEscapeHtml(false);
					     $j("#PartPerSearchControls", parent.document.body).children().hide();   
					     dwr.util.setValue(parentDoc.getElementById(identifier), data);
					     dwr.util.setValue(parentDoc.getElementById(identifier+"Uid"), uid);
						// parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
						// parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
					 	    $j("#PartPerS",parent.document.body).children().show();
						   // $j("#PartPer",parent.document.body).val(data);
						   // $j("#PartPerUid",parent.document.body).val(uid);
					        //getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
					        //getElementByIdOrByName(identifier+"Icon").style.visibility="hidden";
					        //getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
					        //getElementByIdOrByName("clear"+identifier).className="";
					    
					        //getElementByIdOrByName(identifier+"SearchControls").className="none";
					     $j("#PartPerCodeClearButton",parent.document.body).val("Clear/Reassign");
					     $j("#PartPerCodeClearButton",parent.document.body).css("color", "#000");
					     $j("#PartPerCodeClearButton",parent.document.body).attr("disabled", false);
					     $j("#clearPartPer",parent.document.body).children().show();  
						  // parentDoc.getElementById("PartPer").style.display = "";	
						  // parentDoc.getElementById("PartPerText").style.visibility="hidden";
						  // parentDoc.getElementById("PartPerIcon").style.visibility="hidden";
						  // parentDoc.getElementById("PartPerCodeLookupButton").style.visibility="hidden";
						  // parentDoc.getElementById("clearPartPer").style.display = "";
						  // parentDoc.getElementById("clearPartPer").value="Clear/Reassign";
						  //getElementByIdOrByName("selectedPartPer").className="none";
				        	  //getElementByIdOrByName("PartPerSearchControls").className="none";
						  //getElementByIdOrByName("PartPerS").className="none"; //white line						 
						 			     
					     //$j("#PartPerCodeClearButton").val("Clear/Reassign");
					     //$j("#PartPerCodeClearButton").css("color", "#000");
					     //$j("#PartPerCodeClearButton").attr("disabled", false);
					     ///$j("#clearPartPer").children().show();   
				   	self.close();
				    });	
			  	}else {  //other such as STD facility
					JPageForm.getDwrOrganizationDetailsByUid(uid,identifier, function(data) {
					   	dwr.util.setEscapeHtml(false);
					   	dwr.util.setValue(parentDoc.getElementById('"pageClientVO.answer(' +identifier+ ')"'), "");
					   	dwr.util.setValue(parentDoc.getElementById(identifier), data);
					   	dwr.util.setValue(parentDoc.getElementById("organization.organizationUid"), uid);
						parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
						parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
						parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
						parentDoc.getElementById("clear"+identifier).className="";
						parentDoc.getElementById(identifier+"SearchControls").className="none";
						if (getElementByIdOrByNameNode("attributeMap."+identifier+"Uid", parentDoc) != null) {
							  getElementByIdOrByNameNode("attributeMap."+identifier+"Uid", parentDoc).value = uid;
	        			}		
						
						var programArea = "";
						
						if(getElementByIdOrByNameNode("INV108",parentDoc)!=null && getElementByIdOrByNameNode("INV108",parentDoc)!=undefined)
							programArea = getElementByIdOrByNameNode("INV108",parentDoc).value;

 


						//ND-25420//ND-23349
						if((identifier !="NBS_LAB365" &&  identifier != "NBS517") || (programArea=="" || programArea==null || programArea==undefined)){//If it is NBS_LAB365, it will be closed inside the codeLookupLaboratoryReport
							if(getElementByIdOrByNameNode("pageClientVO.answer(NBS_LAB267)", parentDoc)!=null && //ND-25972
									getElementByIdOrByNameNode("pageClientVO.answer(NBS_LAB267)", parentDoc)!= 'undefined' && //ND-25972
									getElementByIdOrByNameNode("pageClientVO.answer(NBS_LAB267)", parentDoc).checked)
								populateParticipationFromOne(getElementByIdOrByNameNode('pageClientVO.answer(NBS_LAB267)', parentDoc),'NBS_LAB365','NBS_LAB367', parentDoc);//ND-23349		
							self.close();
						}
				  
				    });
					
					
					if(identifier =="NBS_LAB365"){//If we are calling from Reporting Facility, the Resulted Test dropdown will be populated.
							
							codeLookupLaboratoryReport('entity-table-Org-ReportingOrganizationUID',parentDoc,"true");
						}
				}
				if (pview != null) {
		                pview.style.display = "none";                  
		           }
				   
				  
						
			}					
	 	    
	 	    function  selectReportingSource(id){
		  			
	 	    	    document.forms[0].action ="/nbs/PamAddOrganization.do?method=addOrganization&identifier="+id;
		  			document.forms[0].submit();
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
				
        </SCRIPT>		
    </head>
    <body class="popup" onload="addRolePresentationToTabsAndSections();" style="overflow-x:hidden">
        <!-- Page title -->
        <div class="popupTitle">
            Organization Search Results
        </div>
        
        <!-- Top button bar -->
        <%String queId = (String) request.getAttribute("identifier");%>
        <div class="popupButtonBar">
            <input type="button"  name="Add" style="${fn:escapeXml(addButton)}" value="Add Organization" id="Add" onclick="return selectReportingSource('${fn:escapeXml(identifier)}')"/>
            <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="closePopup();"/>
        </div>
        
        <!-- Results block -->
        <div style="width:100%; text-align:center;">
            <div style="width:98%;">
                <form method="post" id="orgForm" action="">
                    <nedss:container id="section1" name="Search Results" classType="sect" displayImg ="false" 
                        displayLink="false" includeBackToTopLink="no"> 
                            
                        <div style="width:100%; text-align:right;margin:4px 0px 4px 0px;">
							<a href="${fn:escapeXml(NewSearchLink)}">New Search</a>&nbsp;|&nbsp;
                            <a href="${fn:escapeXml(RefineSearchLink)}">Refine Search</a>
                        </div>
                        
                        <div class="infoBox messages">
                            Your Search Criteria: <i> <c:out value="${SearchCriteria}"/></i>
                            resulted in <b> <c:out value="${ResultsCount}"/></b> possible matches.
                        </div>
                        
                        <table role="presentation" width="100%" border="0" cellspacing="0">
                            <tr>
                               <td align="center">              
                                   <display:table name="organizationList" class="dtTable" pagesize="20"  id="parent" requestURI="">
			                          <display:column property="actionLink" title="<p style='display:none'>Action</p>" class="dstag"/> 
			                          <display:column property="name" title="Name" class="dstag"/>
			                          <display:column property="address" title="Address" class="dstag"/>
			                          <display:column property="telephone" title="Telephone" class="dstag"/>
			                          <display:column property="id" title="ID" class="dstag"/>
			                          <display:setProperty name="basic.empty.showtable" value="true"/>
			                     </display:table>  
                                </td>
                            </tr>
                        </table>
                    </nedss:container>
                </form>
            </div>    
        </div>
        
        <!-- Bottom button bar -->
        <div class="popupButtonBar">
            <input type="button"  name="Add" value="Add Organization" style="<c:out value="${addButton}" />" id="Add" onclick="return selectReportingSource('<c:out value="${identifier}"/>')"/>
            <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="closePopup();"/>
        </div>
    </body>
</html>