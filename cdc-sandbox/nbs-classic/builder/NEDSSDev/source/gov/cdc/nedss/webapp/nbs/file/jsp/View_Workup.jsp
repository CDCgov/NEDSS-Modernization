<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN JSP PAGE GENERATE ###- - -->
    <html lang="en">
    <head>
    <title>View Patient File</title>
    <%@ include file="/jsp/tags.jsp" %>
    <%@ include file="/jsp/resources.jsp" %>	  
   	<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %> 
   	<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%
	String viewEventsOnlyAttr = (String)request.getAttribute("viewEventsOnly");
	boolean viewEventsOnly = false;
	if (viewEventsOnlyAttr != null && viewEventsOnlyAttr.equals("true")) {
		viewEventsOnly = true;
	}
%>     
        <script language="JavaScript">
        		//Paul's global - delete soon
			//boolean viewEventOnly = false;
			//if (viewEventOnlyTst != null && viewEventOnlyTst == "true") {
			//	viewEventOnly = true;
			//}			
			
				 function addTabsToPatientSummary(){
	
	//Patient file, first header table
	$j("#Summary_summary td:not(:has(a)):not(:has(input[@type=checkbox])):not(:has(input[@type=button])):not(:has(input[@type=image])):not(:has(img))").attr("tabIndex","0");
	
	
}

           function  handlePatExtendedBE(){
        
				$j("#addrTable").hide();
			 	$j("#nameTable").hide(); 
			  	$j("#phoneTable").hide();
			   	$j("#idenTable").hide();
			    $j("#raceTable").hide();
           }  
           function startCountdown1() {
    			autocompTxtValuesForJSP();
    			var sessionTimeout = <%= request.getSession().getMaxInactiveInterval()%>
    			min = sessionTimeout / 60;
    			sec = 0;
    			
    			getTimerCountDown();
    		} 
      
     </script>
     <script language="JavaScript"> 
		  var minusIcon1 = "<img src=\"section_collapse.gif\" alt=\"Collapse\" title=\"Collapse Block\"\/>";
		  var plusIcon1 = "<img src=\"section_expand.gif\" alt=\"Expand\" title=\"Expand Block\"\/>";
		  /** 
		  * Control the hide/show of a section body.
		  * @param sectionId - unique Id of a section
		  */
		  function toggleSectionDisplay1(sectionId)
		  {
		      var sectionId = "#" + sectionId;
		      var sectionHead = $j(sectionId).find("table.bluebarsectHeader").get(0);
		      var sectionBody = $j(sectionId).find("div.sectBody").get(0);
		  
		      if ($j(sectionBody).css("display") != "none") {
		          $j(sectionBody).hide();
		          $j(sectionHead).find("a.toggleIconHref").html(plusIcon1);
		      } 
		      else {
		          $j(sectionBody).show();
		          $j(sectionHead).find("a.toggleIconHref").html(minusIcon1);
		      }
		      
		      updateSectionsTogglerHandleWorkup("#" + $j(
		                                          $j(
		                                              sectionId                                 
		                                          ).parent().get(0)
		                                        ).attr("id"));
		      return false;
		}
		
		function updateSectionsTogglerHandleWorkup(viewId) {
		    // get the parent that contains the sectionsToggler 
		    // and all the sections to toggle
		    //alert("viewId "+viewId);
		    var parentElt;
		    if (viewId != null) {
		        parentElt = $j(viewId);    
		    }
		    else {
		        parentElt = $j("body");
		    }
		    
		    // get all sections to be toggled.
		    var updateRequired = true;
		    var sections = $j(parentElt).find("div.bluebarsect");
		    //alert("sections "+sections);
		    var sectState = "";
		    for (var i = 0; i < sections.length; i++) {
		       //alert("Were are here finaly: "+$j(sections.get(i)).attr("id"));
		        if (i == 0) {
		            sectState = getSectionDisplayState("#" + $j(sections.get(i)).attr("id"));
		        }
		        
		        if (sectState != getSectionDisplayState("#" + $j(sections.get(i)).attr("id"))) {
		            updateRequired = false;
		            break;
		        }
		        //alert("sectState "+sectState);
		    }
		
		}
		function gotoSectionWorkup(sectionId)
		{
		    var sectionId = "#" + sectionId;
		    var sectionHead = $j(sectionId).find("table.bluebarsectHeader").get(0);
		    var sectionBody = $j(sectionId).find("div.sectBody").get(0);
		    //alert(sectionId);
		    //alert("sectionHead " +sectionHead);
		    //alert("sectionBody " +sectionBody);
		    // expand/open the section if it is currently closed 
		    if ($j(sectionBody).css("display") == "none") {
		        $j(sectionBody).show();
		        $j(sectionHead).find("a.toggleIconHref").html(minusIcon1);
		    }

		    // update the section toggler handle to reflect the state of the 
		    // sections contained in the view container.
		    updateSectionsTogglerHandleWorkup("#" + $j(
		                                        $j(
		                                            sectionId                                 
		                                        ).parent().get(0)
		                                      ).attr("id"));
		                                      
		    // jump to the section that was currently opened
		    window.location = sectionId;
		}
		
		function toggleAllSectionsDisplayWorkup(viewId, id)
		{
		    // get the parent that contains the sectionsToggler 
		    // and all the sections to toggle
		    var parentElt;
		    if (viewId != null) {
			viewId = "#" + viewId;
			parentElt = $j(viewId);    
		    }
		    else {
			parentElt = $j("body");
		    }
		    

		    
		    
		    var sections = $(parentElt).find("div.bluebarsect");

		    // hide/show all sections depending on the toggler handle value
		    if (id ==1) {			
			for (i = 0; i < sections.length; i++) {
			    var sbody = $j(sections[i]).find("div.sectBody").get(0);
			    $j(sbody).hide();

			    var shead = $j(sections[i]).find("table.bluebarsectHeader").get(0);
			    $j(shead).find("a.toggleIconHref").html(plusIcon1);
			}
		    }
		    else {
			for (i = 0; i < sections.length; i++) {
			    var sbody = $j(sections[i]).find("div.sectBody").get(0);
			    $j(sbody).show();

			    var shead = $j(sections[i]).find("table.bluebarsectHeader").get(0);
			    $j(shead).find("a.toggleIconHref").html(minusIcon1);
			}
		    }
		}

		/** Popup a child window and load the page that is currently being 
	       *   viewed on the parent window. The call to load the page includes an additional 
	       *   parameter called 'mode' that has a value of print. This value is used to load
	       *   a seperate css file named 'print.css' when the page loads in the child window.
	       */
	       function printPreview() {
	           var divElt = getElementByIdOrByName("pageview");
	           divElt.style.display = "block";
	           var o = new Object();
	           o.opener = self;
	           var URL = '/nbs/LoadViewFile1.do?method=ViewFile&ContextAction=print&uid=${fn:escapeXml(uid)}';
	           var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
	          
	           var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
	           return false;
	         }
	        function focusTab(){
	           if(getElementByIdOrByName("focusTab")!=null){
				var focusTab = getElementByIdOrByName("focusTab").value;
				$j("#"+focusTab).click();
				}
				
	        }
	        
	        function fieldFocus(){         
		     if(getElementByIdOrByName("returnLink") != null){
		      	 $j('#returnLink').focus();		      	  
		        }else {
		                 $j('#printId').focus();
		       }
	        }

        
    </script>
       
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
            div.grayButtonBar1 {width:100%; text-align:right; background:#EEE; }
            table.style{width:100%; margin:0 auto;border:1px solid #AFAFAF;}
            table.status{width:100%; margin:0 auto;}
			table.bluebarsectionsToggler, table.bluebarsubSectionsToggler, table.bluebarsubSect {width:100%; margin:0 auto; border-width:0px; margin-top:0.0em; border-spacing:0px;}
			table.bluebardtTable, table.bluebarprivateDtTable {width:100%; border:1px solid #666666; padding:0.0em; margin:0em auto; margin-top:0em;}
	</style>
     
     
    </head>
      
      
     <% 
    int subSectionIndex = 0;

    String tabId = "";
      String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Contact Investigation"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 

   <logic:equal name="viewEventsOnly" value="true">
        	<body onload="startCountdown1();handlePatExtendedBE();focusTab();autocompTxtValuesForJSP();tableCollapseEvent();fieldFocus();addRolePresentationToTabsAndSections();addRoleToPatientFile();UISorting();sortPatientFileTablesByDateColumn()">
</logic:equal>
<logic:notEqual name="viewEventsOnly" value="true">
    <logic:equal name="DSPersonForm" property="actionMode" value="Edit">  
    <body onload="enableOrDisable('INV2002','ReasonUnknown','UNK');enableOrDisable('CurrSex','UnknownSpecify','U');startCountdown1();handlePatExtendedBE();autocompTxtValuesForJSP();handlebatchEntry();showHideSpanishOrigin();addRolePresentationToTabsAndSections();addRoleToPatientFile();loadCounties();clearBatchEntryFields('nameTable');clearBatchEntryFields('addrTable');clearBatchEntryFields('phoneTable');clearBatchEntryFields('idenTable');clearBatchEntryFields('raceTable');document.location.href='#pageview';">
    </logic:equal>
      <logic:notEqual name="DSPersonForm" property="actionMode" value="Edit">
      	<body onload="startCountdown1();handlePatExtendedBE();focusTab();autocompTxtValuesForJSP();tableCollapseSummary();tableCollapseEvent();handlebatchEntry();fieldFocus();addRolePresentationToTabsAndSections();addRoleToPatientFile();UISorting();sortPatientFileTablesByDateColumn()">
         </logic:notEqual>
 </logic:notEqual>     
         
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/LoadViewFile1.do"> 
                <!-- Body div -->
                <div id="bd">
					<% if (!viewEventsOnly) { %>
                    	<!-- Top Nav Bar and top button bar -->
                    	<%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
                    <%}%>                                  
            
                    <!-- Page Errors -->
                    <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
                    
                     <!-- Error Messages using Action Messages-->
				    <div id="globalFeedbackMessagesBar" class="screenOnly">
				        <logic:messagesPresent name="error_messages">
				        <div class="infoBox errors" id="errorMessages">
				            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				            <ul>
				                <html:messages id="msg" name="error_messages">
				                    <li> <bean:write name="msg" /> </li>
				                </html:messages>
				            <ul>
				        </div>
				    </logic:messagesPresent>
				    <%if(request.getAttribute("returnLink") !=null ){%>
				   	 <div class="returnToPageLink">
            				<%=request.getAttribute("returnLink")%>            
        				</div>
        			<%}%>
				    </div> 
		         <div class="grayButtonBar1">
                         <table role="presentation" border="1" width="100%">
					   		 <tr >   		 
					   		 	<td colspan="100%" align="right" >
					   		 	  <logic:notEqual name="DSPersonForm" property="actionMode" value="Edit">
									<% 
										if(request.getAttribute(NEDSSConstants.DELETEBUTTON)!=null && request.getAttribute(NEDSSConstants.DELETEBUTTON).equals("true") ){ 
											 String eventCount = String.valueOf((request.getAttribute(NEDSSConstants.EVENTCOUNT)));
											 if(eventCount == null || eventCount.equals("0")){%>
											       &nbsp;<input type="button" name="delete" value="  Delete  " onclick="promptForm('<%=request.getAttribute("deleteButtonHref") %>','You have indicated that you would like to delete this patient file. \nSelect OK to continue or Cancel to return to patient file.');"/>
										       <% }else{ %> 
											   &nbsp;<input type="button" name="delete" value="  Delete  " onclick="window.alert('This patient file has associated event records. The file cannot be deleted until all associated event records have been deleted. If you are unable to see the associated event records due to your user permission settings, please contact your system administrator.');"/>
										       <%}} %>
										&nbsp;   
										<% if (viewEventsOnly) { %>
									   		<input id="printId" type="button" name="Print" value="  Print  " onclick="javascript:window.print();"/>
									   	<% } else { %>
									   		<input id="printId" type="button" name="Print" value="  Print  " onclick="return printPreview();"/>
									   	<% } %>
					   		 	  </logic:notEqual>
					   		 	  <logic:equal name="DSPersonForm" property="actionMode" value="Edit">
									<input type="button" name="Submit" value="  Submit  "  onclick="SubmitPatient();"  /> &nbsp;
									<input type="button" name="cancel" value="  Cancel  " onclick="cancelEdit();"/>
					   		 	  </logic:equal>
					   		 	
					   		 	</td>
					   		 </tr> 	

					   		
					   	</table>
					   	</div>
						 <logic:equal name="DSPersonForm" property="actionMode" value="Edit">
						 <table role="presentation">
						 <tr>
						   <td>&nbsp;</td>
						  </tr>	
						  </table>
						 </logic:equal>
				    <% if(request.getAttribute("personLocalID")!=null) {%>	 
				     <table role="presentation" class="style">
					   <tr class="cellColor">
					        <td class="border" colspan="2">
					            <%
					            String name = (String) request.getAttribute("patientFullLegalName") == null ? "---" :  (String) request.getAttribute("patientFullLegalName");
					            name = name.trim(); 
					            if (name.length() != 0) {
					            	name = name;
					            }else
					            	name="---";
					            
					            String suffix = (String) request.getAttribute("patientSuffixName") == null ? "" : (String) request.getAttribute("patientSuffixName");
					            if (suffix.trim().length() != 0)
					            {
					            	name = name + ", "+suffix;
					            }
					            String currentSex =  request.getAttribute("currSexCd") == null ? "---" :  CachedDropDowns.getCodeDescTxtForCd((String) request.getAttribute("currSexCd"),"SEX");
					            currentSex = currentSex.trim(); 
					            if(currentSex.length() !=0){
					            	currentSex = currentSex;
					            }else
					            	currentSex="---";
					            
					            String DOB = (String) request.getAttribute("birthTime") == null ? "---" :  (String) request.getAttribute("birthTime");
					            if(DOB.length() !=0){
					            	DOB = DOB;
					            }else
					            	DOB="---";
					            //DOB = currentsex.trim();
					           
					         %>
					            <span class="valueTopLine"> <%= name %> </span>
					            <span class="valueTopLine">|</span>
					            <span class="valueTopLine"> <%= currentSex %> </span>
					            <span class="valueTopLine">|</span>
					            <%if(request.getAttribute("currentAgeUnitCd") != null){%>
					            <span class="valueTopLine"> <%= DOB %> (<%=request.getAttribute("currentAge")%> <%=CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT")%>) </span>
					            <%}else{ %>
					            <span class="valueTopLine"> <%= DOB %>  </span>
					            <%} %>
					        </td>
					        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
							<a name=pageTop> </a>
					        	<span class="valueTopLine"> Patient ID: </span>
					            <span style="font:16px Arial; margin-left:0.2em;">${fn:escapeXml(personLocalID)}</span>
					            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
					        </td>
					    </tr>
					    </table>
					    <%}%>
					    
					   <table role="presentation" class="status">  <tr>
					   <% if(request.getAttribute(NEDSSConstants.RECORDSTATUSCD)!=null && !request.getAttribute(NEDSSConstants.RECORDSTATUSCD).equals("")) {%>
					     <td style="text-align:right"><%=request.getAttribute(NEDSSConstants.RECORDSTATUSCD)%></td>
					    <%}else{ %>
					      <td height="5 px"></td>
					    <%} %>
					   </tr> </table>
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
		<layout:tabs width="100%" styleClass="tabsContainer">    	 
			<logic:notEqual name="DSPersonForm" property="actionMode" value="Edit">	
			
			<% if (!viewEventsOnly) { %>
				<layout:tab key="Summary">     
					<jsp:include page="WorkUp_Summary.jsp"/>
				</layout:tab>        
			<% } %>
			    
			<layout:tab key="Events">     
				<jsp:include page="File_Events.jsp"/>
			</layout:tab>
				 
			</logic:notEqual>
			
			<% if (!viewEventsOnly) { %>              	
			<layout:tab key="Demographics"> 
				<logic:equal name="DSPersonForm" property="actionMode" value="Edit">  
					<jsp:include page="/person/jsp/Patient_Extended_Edit_File.jsp"/>
				</logic:equal>
				<logic:notEqual name="DSPersonForm" property="actionMode" value="Edit">
					<jsp:include page="/person/jsp/Patient_Extended_View_File.jsp"/>
				</logic:notEqual>
			</layout:tab>        
			<% } %>
		</layout:tabs>  
		
	  	  <input id="focusTab" type="hidden" name="focusTab" value="<%=request.getAttribute("focusTab")==null?"tabs0head0":request.getAttribute("focusTab")%>"/>
	       
      				 <div class="grayButtonBar1">
                       <table role="presentation" border="1" width="100%">
			   		   <tr >   		 
			   		 	<td colspan="100%" align="right" >
			   		 	<logic:notEqual name="DSPersonForm" property="actionMode" value="Edit">
				   		 	<% 
								if(request.getAttribute(NEDSSConstants.DELETEBUTTON)!=null && request.getAttribute(NEDSSConstants.DELETEBUTTON).equals("true") ){ 
								         String eventCount = String.valueOf((request.getAttribute(NEDSSConstants.EVENTCOUNT)));
								         if(eventCount == null || eventCount.equals("0")){%>
									       &nbsp;<input type="button" name="delete" value="  Delete  " onclick="promptForm('<%=request.getAttribute("deleteButtonHref") %>','You have indicated that you would like to delete this patient file. \nSelect OK to continue or Cancel to return to patient file.');"/>
								       <% }else{ %> 
								           &nbsp;<input type="button" name="delete" value="  Delete  " onclick="window.alert('This patient file has associated event records. The file cannot be deleted until all associated event records have been deleted. If you are unable to see the associated event records due to your user permission settings, please contact your system administrator.');"/>
								       <%}} %>
								&nbsp;         
								<% if (viewEventsOnly) { %>
							   		<input id="printId" type="button" name="Print" value="  Print  " onclick="javascript:window.print();"/>
							   	<% } else { %>
							   		<input id="printId" type="button" name="Print" value="  Print  " onclick="return printPreview();"/>
							   	<% } %>
			   		 	   </logic:notEqual>
			   		 	   <logic:equal name="DSPersonForm" property="actionMode" value="Edit">
							<input type="button" name="Submit" value="  Submit  "  onclick="SubmitPatient();"  /> &nbsp;
							<input type="button" name="cancel" value="  Cancel  " onclick="cancelEdit();"/>
					   	   </logic:equal>
			   		 	</td>
			   		 </tr> 				   		
			   	    </table>
			   	</div>	 		
	  	  	
	  	  </html:form>
          </div> <!-- Container Div -->
    </body>
     
</html>

		

	