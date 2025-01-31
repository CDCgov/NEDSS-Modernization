    <?xml version="1.0" encoding="UTF-8"?>
	<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
	<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
	<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page isELIgnored ="false" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.Timestamp" %>
	<%@ page import="gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil" %>	
	<%@ page import="gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil" %>		
	<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>	
	<%@ page import="gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm" %>
	<%@ page import="gov.cdc.nedss.entity.person.vo.PatientSrchResultVO" %>
	<%@ page import="javax.servlet.http.HttpServletRequest" %>
	<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO" %>
	<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT" %>
	<%@ page import="gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT" %>
	<%@ page import="gov.cdc.nedss.locator.dt.PostalLocatorDT" %>
	<%@ page import="gov.cdc.nedss.locator.dt.TeleLocatorDT" %>
	<%@ page import="gov.cdc.nedss.entity.entityid.dt.EntityIdDT" %>
	<%@ page import="gov.cdc.nedss.entity.person.dt.PersonRaceDT" %>
	<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>
	<%@ page import="gov.cdc.nedss.util.StringUtils" %>
	<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
	
	   <% CompleteDemographicForm fform = (CompleteDemographicForm)request.getSession().getAttribute("DSPersonForm");
	   PatientSrchResultVO pVo = fform.getPatSrcResVO();
	   PersonVO vo = fform.getPerson();
	   SearchResultPersonUtil psutil = new SearchResultPersonUtil();
	   boolean flag = request.getAttribute("mode")!=null?false:true;
	   request.setAttribute("personAddProfile",pVo.getPersonAddressProfile());
	   request.setAttribute("personPhoneprofile",pVo.getPersonPhoneprofile());
	   request.setAttribute("personIds",pVo.getPersonIds());
	   request.setAttribute("profile",pVo.getProfile());
	   %>
	  <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JDSPersonForm.js"></SCRIPT>  
    <SCRIPT Language="JavaScript" Type="text/javascript" SRC="PersonSpecific.js"></SCRIPT> 
    
	     <script language="JavaScript"> 
	     
	       function handlebatchEntry(){
	           dwr.engine.beginBatch(); 
	            rewriteBatchIdHeader('addrTable');
	             dwr.engine.endBatch();
	            dwr.engine.beginBatch(); 
	            rewriteBatchIdHeader('phoneTable');
	             dwr.engine.endBatch();
	             dwr.engine.beginBatch();
	           rewriteBatchIdHeader('idenTable');
	            dwr.engine.endBatch();
	            dwr.engine.beginBatch();
	           rewriteBatchIdHeader('nameTable');
	            dwr.engine.endBatch();
	            dwr.engine.beginBatch();
	            rewriteBatchIdHeader('raceTable');          
	           dwr.engine.endBatch();  	
           }	
   function rewriteBatchIdHeader(tabNm) 
  	{
		      var patternrow="pattern"+tabNm+"Row";
		      var patternbody="pattern"+tabNm;
		      
		            //get all rows of data
			    JDSPersonForm.getAllBatchAnswer4Table(tabNm,function(answer) {
				// Delete all the rows except for the "pattern" row
				dwr.util.removeAllRows(patternbody, { filter:function(tr) {
						return (tr.id != patternrow);
				}});				
				if (answer.length == 0) {
					//no rows - display 'No Data has been entered'
				 //	$('nopattern' +subSectNm).style.display = "block";
				 	return;
				 }
		       
				for (var i = 0; i < answer.length; i++){
				//alert("i ="+i);
					ans = answer[i];
					id = ans.id;
					
					
					dwr.util.cloneNode(patternrow, { idSuffix:id }); 
				      //pull the data for each row
				      for (var key in ans.answerMap) {
				      	if ( key != null || key != '' ) {
				      		var val = ans.answerMap[key];
				      		//alert("node=table" + key + id + ' val='+val);
				      	if(key == 'addrStr1' || key == 'addrStr2'){
					      	if(ans.answerMap['addrStr2'] != null && ans.answerMap['addrStr2'] != '' ){
					        dwr.util.setValue("table" + "addrAddress" + id, ans.answerMap['addrStr1'] + ', '+ ans.answerMap['addrStr2']);
					        }else{
					        dwr.util.setValue("table" + "addrAddress" + id, ans.answerMap['addrStr1']);
					        }
				      	}else if(key == 'addrTypeDesc'){
				      	 dwr.util.setValue("table" + "addrTypeDesc" + id, ans.answerMap['addrTypeDesc'] + '/'+ ans.answerMap['addrUseDesc']);
				      	}else if(key == 'phTypeDesc'){
				      	 dwr.util.setValue("table" + "phTypeDesc" + id, ans.answerMap['phTypeDesc'] + '/'+ ans.answerMap['phUseDesc']);
				      	}else{
						dwr.util.setValue("table" + key + id, val);
						}
						 
					}
				     }
				     //clear display = 'none'
				     $(patternrow + id).style.display = ""; 
				     //hide No Data Entered
				     $('no' +patternrow).style.display = "none";
				}		
					
			    }); //all rows of data

    	}  
    	
    	 function viewClicked(entryId,tableId)
          {
        	//alert(" View Batch Entry Id=" +entryId + " tableId=" + tableId); 
			var beIdStr = entryId.match(/\d+/)[0];
			if (beIdStr == 'null' || beIdStr == "")
				return;		 
			    
		           //get all rows of data
			    JDSPersonForm.getAllBatchAnswer4Table(tableId,function(answer) {
				for (var i = 0; i < answer.length; i++){		
					if (answer.length > 0) {
					//var ans = answer[beIdStr-1];
					var ans = answer[i];
					var id = ans.id;
					//alert(id);
					if (id == beIdStr) {
					
		
					//gBatchEntryUpdateSeq = id;
					if(tableId == 'addrTable'){	
					 
					     $j("#addrTable").show();				
						dwr.util.setValue("addrNameDate",ans.answerMap['addrNameDate']);
					    dwr.util.setValue("addrType",ans.answerMap['addrTypeDesc']);
					    dwr.util.setValue("addrStr1",ans.answerMap['addrStr1']);
					    dwr.util.setValue("addrUse",ans.answerMap['addrUseCdDesc']); 
					    dwr.util.setValue("addrStr1",ans.answerMap['addrStr1']); 
					    dwr.util.setValue("addrStr2",ans.answerMap['addrStr2']); 
					    dwr.util.setValue("addrCity",ans.answerMap['addrCity']);
					    dwr.util.setValue("addrState",ans.answerMap['addrStateDesc']);
					    dwr.util.setValue("addrZip",ans.answerMap['addrZip']);
					    dwr.util.setValue("addrCounty",ans.answerMap['addrCountyDesc']);
					    dwr.util.setValue("addrCensusTract",ans.answerMap['addrCensusTract']);
					    dwr.util.setValue("addrCountry",ans.answerMap['addrCountryDesc']);
					    dwr.util.setValue("addrComments",ans.answerMap['addrComments']);    
					    			
					}
					if(tableId == 'nameTable'){			
					    $j("#nameTable").show();			
						dwr.util.setValue("nameDate",ans.answerMap['nameDate']);
					    dwr.util.setValue("nameType",ans.answerMap['nameTypeDesc']);
					    dwr.util.setValue("namePrefix",ans.answerMap['namePrefixDesc']);
					    dwr.util.setValue("nameLast",ans.answerMap['nameLast']); 
					    dwr.util.setValue("nameSecLast",ans.answerMap['nameSecLast']); 
					    dwr.util.setValue("nameFirst",ans.answerMap['nameFirst']); 
					    dwr.util.setValue("nameMiddle",ans.answerMap['nameMiddle']);
					    dwr.util.setValue("nameSecMiddle",ans.answerMap['nameSecMiddle']);
					    dwr.util.setValue("nameSuffix",ans.answerMap['nameSuffixDesc']);
					    dwr.util.setValue("nameDegree",ans.answerMap['nameDegreeDesc']);					   
					    			
					}
					if(tableId == 'phoneTable'){	
						 $j("#phoneTable").show();	
						dwr.util.setValue("phDate",ans.answerMap['phDate']);
					    dwr.util.setValue("phType",ans.answerMap['phTypeDesc']);
					    dwr.util.setValue("phUse",ans.answerMap['phUseDesc']);
					    dwr.util.setValue("phCntryCd",ans.answerMap['phCntryCd']); 
					    dwr.util.setValue("phNum",ans.answerMap['phNum']); 
					    dwr.util.setValue("phExt",ans.answerMap['phExt']); 
					    dwr.util.setValue("phEmail",ans.answerMap['phEmail']);
					    dwr.util.setValue("phUrl",ans.answerMap['phUrl']);
					    dwr.util.setValue("phComments",ans.answerMap['phComments']);			   		   
					    			
					}
					if(tableId == 'idenTable'){	
						//alert(ans.answerMap['nameDate']);	
						 $j("#idenTable").show();			
						dwr.util.setValue("idDate",ans.answerMap['idDate']);
					    dwr.util.setValue("idType",ans.answerMap['idTypeDesc']);
					    dwr.util.setValue("idAssgn",ans.answerMap['idAssgnDesc']);
					    dwr.util.setValue("idValue",ans.answerMap['idValue']);									   	   
					    			
					}
					if(tableId == 'raceTable'){						
					 $j("#raceTable").show();	
						//alert(ans.answerMap['nameDate']);			
						dwr.util.setValue("raceDate",ans.answerMap['raceDate']);
					    dwr.util.setValue("raceType",ans.answerMap['raceTypeDesc']);
					    dwr.util.setValue("raceDetailCat",ans.answerMap['raceDetailCatDesc']);			       
					  	
					}
				  }
			}
		 }	//for loop ends	
	   }); //all rows of data
     
     }  //viewClicked	   
	  
	     
     
     function displayCalcAge() {

	var dobNode = getElementByIdOrByName("patientDOB");
	var serverDate = getElementByIdOrByName("today") == null ? "" : getElementByIdOrByName("today").value;
	
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	var reportedAgeNode = getElementByIdOrByName("hiddenReportedAge");
	var reportedAgeUnit = getElementByIdOrByName("hiddenReportedAgeUnit");
		
	var currentAgeNode = getElementByIdOrByName("currentAge");	
	var currentAgeUnitsNode = getElementByIdOrByName("currentAgeUnits");
	
	var currentAgeFileSummaryNode = getElementByIdOrByName("currentAgeFileSummary");	
	var currentAgeUnitsFileSummaryNode = getElementByIdOrByName("currentAgeUnitsFileSummary");
	       
         //alert("displayCalcAge dobNode " + dobNode.value + "  serverDate : " + serverDate.value + " calcDOBNode : " + calcDOBNode.value   );

		if(dobNode != null &&  (dobNode.value.length > 0) && (isDate(dobNode.value))){	
			calcDOBNode.value = dobNode.value;
		} 

	
	//figure out the current age and units, don't show if calc dob is empty

	if(calcDOBNode.value.length > 0)	{

		//alert('calc dob node is not empty');		
		
		var asOfDate = new Date(serverDate);
		var calcDOBDate = new Date(calcDOBNode.value);
		

		var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
		//alert('reportedAgeMilliSec' + reportedAgeMilliSec);
		
		
		if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec > 0 && CompareDateStrings(calcDOBNode.value, "12/31/1875") != -1){
			
			
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30.41; 
			var reportedAgeYears = reportedAgeMonths/12;
			
			if(isLeapYear(calcDOBDate.getFullYear())) reportedAgeMonths = Math.floor(reportedAgeDays)/30.5;

               //alert("age" + reportedAgeMonths  + "   " + reportedAgeDays + "   " + reportedAgeYears );
			if(Math.ceil(reportedAgeDays)<=28){
				currentAgeNode.innerText=Math.floor(reportedAgeDays);
				currentAgeUnitsNode.innerText="Days";
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=Math.floor(reportedAgeDays);
					currentAgeUnitsFileSummaryNode.innerText="Days";
				}
				
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
				// As per lisa if days are  greater than 28 and lessthan 31 it should be one month 
				if(Math.ceil(reportedAgeDays) > 28 && Math.ceil(reportedAgeDays) < 31)
				reportedAgeMonths = reportedAgeMonths + 1;
				
				currentAgeNode.innerText=Math.floor(reportedAgeMonths);
				currentAgeUnitsNode.innerText="Months";	
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=Math.floor(reportedAgeMonths);
					currentAgeUnitsFileSummaryNode.innerText="Months";
				}
				
			} else	{
				// get rough estimated year age
				var yearDiff = asOfDate.getFullYear() - calcDOBDate.getFullYear();
				//need to determine whether birthday has happened 
				if(asOfDate.getMonth()<calcDOBDate.getMonth())
					yearDiff = yearDiff-1;
				else if(asOfDate.getMonth()==calcDOBDate.getMonth()){
					
					if(asOfDate.getDate()<calcDOBDate.getDate())
						yearDiff = yearDiff-1;
				}
				currentAgeNode.innerText=yearDiff;//Math.floor(reportedAgeYears);
				currentAgeUnitsNode.innerText="Years";
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=yearDiff;
					currentAgeUnitsFileSummaryNode.innerText="Years";
				}
                    
                    //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
				if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && reportedAgeYears > 1 && reportedAgeYears < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
				{
				    currentAgeNode.innerText="11";
				    currentAgeUnitsNode.innerText="Months";
				    if(currentAgeFileSummaryNode!=null){
						currentAgeFileSummaryNode.innerText="11";
						currentAgeUnitsFileSummaryNode.innerText="Months";
					}
				}				

			}
					
		} else {
			
			currentAgeNode.innerText="";
			currentAgeUnitsNode.innerText="";
		    if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec == 0)
		    {
		      	currentAgeNode.innerText="0";
		      	currentAgeUnitsNode.innerText="Days";	
		      	if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText="0";
					currentAgeUnitsFileSummaryNode.innerText="Days";
				}
		    }			
			
		}
	}

} //diaplayCalcAge()
   
   function EditPatient(){
   //alert("<%=request.getAttribute("editButtonHref")%>");
     document.DSPersonForm.action ="<%=(String)request.getAttribute("editButtonHref")%>";
    document.DSPersonForm.submit();
    return true;  
   }  
       
	     </script>
	<!-- ################### PAGE TAB ###################### - - -->
	  <style type="text/css">
	  table.bluebarsectionsToggler, table.bluebarsubSectionsToggler, table.bluebarsubSect {width:100%; margin:0 auto; border-width:0px; margin-top:0.0em; border-spacing:0px;}
	  table.bluebardtTable, table.bluebarprivateDtTable {width:100%; border:1px solid #666666; padding:0.0em; margin:0em auto; margin-top:0em;}
	   
	  </style>
	
	<%
	String tabId = "editCaseInfo1";
	tabId = tabId.replace("]","");
	tabId = tabId.replace("[","");
	tabId = tabId.replaceAll(" ", "");
	int subSectionIndex = 0;
	int sectionIndex = 0;
	String sectionId = "";
	String [] sectionNames  = {"Patient Summary","Administrative","Name","Address","Phone & Email","Identification","Race","Ethnicity","Sex & Birth","Mortality","General Patient Information"};
	
	
	%>
	  
	<tr><td width="100%">
	    <div class="view" id="patDemo" >
         	<div class="showup" style="text-align:right;">
			      		   <a  href="javascript:toggleAllSectionsDisplayWorkup('patDemo',2)"/><font class="hyperLink"> Expand All</font></a>
			      		   | <a  href="javascript:toggleAllSectionsDisplayWorkup('patDemo',1)"/><font class="hyperLink"> Collapse All</font></a>
					 </div>
				<div> 
			         <table role="presentation"><tr><td height="05 px"></td></tr></table>
         </div> 
         <div><table role="presentation"><tr><td height="05 px"></td></tr></table></div>
         <table role="presentation" class="showup" width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			
			<tr bgcolor="#003470">
				<td height="25 px" valign="center" align="left"><font class="boldTwelveYellow">
				&nbsp;Patient Demographics</font>
				</td>
				<td align="right">
				<% if(request.getAttribute(NEDSSConstants.EDITBUTTON)!=null && request.getAttribute(NEDSSConstants.EDITBUTTON).equals("true") ){ %>
						<input type="button" name="edit" value="  Edit  " onclick="EditPatient();"/>
				<% } %>
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2"></td>
			</tr>
	     </table>
	 <table role="presentation" class="sectionsToggler" style="width:100%;">
        <tr>
            <td>
                <ul class="horizontalList">
                    <li style="margin-right:5px;"><b>Go to: </b></li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch1')">Patient Summary</a></li>
                     <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch3')">Administrative</a> </li>
                     <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch5')">Name</a> </li>
                      <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch7')">Address</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch9')">Phone & Email</a> </li>
                      <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch11')">Identification</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch13')">Race</a> </li>
                     <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch15')">Ethnicity</a> </li>
                      <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch17')">Sex & Birth</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch19')">Mortality</a> </li>
                     <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch21')">General Patient Information</a> </li>                   
                     <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('LocalFieldsSection')">Custom Fields</a> </li>
                    <%}%>
                 </ul>
            </td>
        </tr>
        <tr>
            <td>
                <a class="toggleHref"></a>
            </td>
        </tr>
    </table>	
           <div> 
         <table role="presentation"><tr><td height="05 px"></td></tr></table></div>
	<div class="view" id="patientSearchByDetails" style="text-align:center;">
	
	<%  sectionIndex = 0; %>
	
					<%if(flag){ %>
	                  <!-- ################# SECTION ################  -->
						 <nedss:bluebar id="patSearch1" name="Patient Summary" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			
						<!-- ########### SUB SECTION ###########  -->
						 <nedss:bluebar id="patSearch2" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
								
			                    <tr>  
			                      <td valign="top" width="25%">             
			                         <%=HTMLEncoder.sanitizeHtml(pVo.getPersonAddressProfile())%>
			                       </td> 
			                        <td valign="top" width="20%">                    
			                          <%=HTMLEncoder.sanitizeHtml(pVo.getPersonPhoneprofile())%>
			                       </td>       
			                        <td valign="top" width="20%">                    
			                        <%=HTMLEncoder.sanitizeHtml(pVo.getPersonIds())%>
			                       </td>       
			                        <td valign="top" width="35%">                    
			                        <%=HTMLEncoder.sanitizeHtml(pVo.getProfile())%>
			                       </td>                    
			                               
			                    </tr>
			                    </nedss:bluebar>
			                    </nedss:bluebar>
			               <%} %>     
                                  <% if(request.getAttribute("asOfDateAdmin") != null && !((String)request.getAttribute("asOfDateAdmin")).trim().equals("")){%>
			                      <%String secstr = "Administrative (as of "+(String)request.getAttribute("asOfDateAdmin")+")";%>
			                        <nedss:bluebar id="patSearch3" name="<%=secstr%>" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch4" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                         <tr> <td class="fieldName">
									<span title="General comments pertaining to the patient." id="DEM196L">
									General Comments:</span>
									</td>
									<td>
									<span id="DEM196"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.description"  />
									
									</td> </tr>     
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                     <% }else{%>
			                        <nedss:bluebar id="patSearch3" name="Administrative" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
				                     <nedss:bluebar id="patSearch4" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" > 
				                      
				                        <tr> <td class="fieldName">
										<span title="General comments pertaining to the patient." id="DEM196L">
										General Comments:</span>
										</td>
										<td>
										<span id="DEM196"/>
										<nedss:view name="DSPersonForm" property="person.thePersonDT.description"  />
										</td> </tr>     
		            
		                             </nedss:bluebar>
				                    </nedss:bluebar>
			                    	<% }%> 
			                    
			                      <nedss:bluebar id="patSearch5" name="Name" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch6" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" > 
			                    		          
			                      <tr> <td colspan="2" width="100%">									
									<table  class="bluebardtTable" align="center" >
									<thead>
									<tr>  <%if(flag){
								            %>
									<td style = "background-color: #EFEFEF; border:1px solid #666666" width="6%">  </td>
									<%}%>
									<th width="15%"><b> As of </b> </th>
									<th width="15%"><b> Type </b></th>
									<th width="15%"><b> Last </b></th>
									<th width="15%"><b> First </b></th>
									<th width="15%"> <b>Middle </b></th>
									<th width="15%"> <b>Suffix </b> </th>
									</tr>							
									</thead>
									<tbody id="patternnameTable">
									        <tr id="patternnameTableRow" class="odd" style="display:none">
									        <%if(flag){
								            	%>
										    <td  width="6%"> 
										    <table role="presentation" width="100%">
										    <tr>
								            
											    <td style="text-align:left">
											     <a href id="viewIdSubSection" onclick="viewClicked(this.id,'nameTable');return false" style="cursor:pointer"> Details </a>
											
											 </td>
											 
 											</tr>
 											</table>
 											</td>
 										<%}%>					           
									            <td id="tablenameDate">
									            &nbsp;
									            </td>
									            <td id="tablenameTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tablenameLast">								            
									            &nbsp; 
									            </td >
									            <td id="tablenameFirst">
									             &nbsp;							            
									            </td >
									            <td id="tablenameMiddle">
									            &nbsp;								          
									            </td >
									            <td id="tablenameSuffixDesc">
									            &nbsp;								            
									            </td>									
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternnameTable">
									 <tr id="nopatternnameTableRow" class="odd">
									 <td colspan="100%"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									<%if(flag){
								            %>
									<tr><td colspan="100%">
									   <table role="presentation" id="nameTable" align="center" width="100%">
									      <tr>
									      <td class="fieldName" width="100%"> 
									      <span class=" InputFieldLabel" id="NmAsOfL" title="Name as of."> Name as of:
									      </span> </td>
									      <td id="nameDate" width="100%"> 
									         
									      </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="NmTypeL" title="Type of Name">  Type:</span> </td>
									      <td id="nameType"> 
									     </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmPrefixL" title="Name Prefix"> Prefix: </span></td>
									      <td id="namePrefix"> 
									     </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmLastL" title="Last Name "> Last:</span> </td>
									      <td id="nameLast">  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="NmSecLastL" title="Second Last Name ">  Second Last:</span> </td>
									      <td id="nameSecLast">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmFirstL" title="First Name "> First:</span> </td>
									      <td id="nameFirst">   </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmMiddleL" title="Middle Name "> Middle:</span> </td>
									      <td id="nameMiddle">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmSecMiddleL" title="Second Middle Name "> Second Middle: </span></td>
									      <td id="nameSecMiddle">  </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmSuffixL" title="Name Suffix "> Suffix: </span></td>
									      <td id="nameSuffix"> 
									       </td>	
									      </tr>
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmDegreeL" title="Name Degree "> Degree: </span></td>
									      <td id="nameDegree">
									       
 	                                         </td>	
									      </tr>	
									      </table>
									   </td></tr>    
									   <%}%>     
									                                    
	                             </nedss:bluebar>
			                    </nedss:bluebar>	
		                    
			                      <nedss:bluebar id="patSearch7" name="Address" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch8" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                     
			                     <tr> <td colspan="2" width="100%">									
									<table  class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<%if(flag){
								            %>
									<td style = "background-color: #EFEFEF; border:1px solid #666666" width="6%">  </td>
									<%} %>
									<th width="15%"><b> As of </b> </th>
									<th width="15%"><b> Type </b></th>
									<th width="15%"><b> Address </b></th>
									<th width="15%"><b> City </b></th>
									<th width="15%"> <b>State </b></th>
									<th width="15%"> <b>Zip </b></th>			
									</tr>							
									</thead>
									<tbody id="patternaddrTable">
									        <tr id="patternaddrTableRow" class="odd" style="display:none">
									          <%if(flag){
								            %>
								            <td  width="6%"> 
								            <table role="presentation" width="100%">
								            <tr>
								          
								            <td style="text-align:left;">
								            <a href id="viewIdSubSection" onclick="viewClicked(this.id,'addrTable');return false" style="cursor:pointer"> Details </a>
											
											 </td>												
 											</tr>
 											</table>
 											</td>
 											 <%} %>				           
									            <td id="tableaddrNameDate">
									            &nbsp;
									            </td>
									            <td id="tableaddrTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tableaddrAddress">								            
									            &nbsp; 
									            </td >
									            <td id="tableaddrCity">
									             &nbsp;							            
									            </td >
									            <td id="tableaddrStateDesc">
									            &nbsp;								          
									            </td >
									            <td id="tableaddrZip">
									            &nbsp;								            
									            </td>									
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternaddrTable">
									 <tr id="nopatternaddrTableRow" class="odd">
									 <td colspan="100%"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									<%if(flag){
								            %>
									<tr><td colspan="100%">
									 <table role="presentation" id="addrTable" align="center" width="100%">
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="AddrAsOfL" title="Address as of Date ">  Address as of: </span></td>
									      <td id="addrNameDate" width="100%">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="AddrTypeL" title="Address Type">  Type: </span>  </td>
									      <td id="addrType">    </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrUseL" title="Address Type"> Use: </span></td>
									      <td id="addrUse">    </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrStreet1L" title="Street Address 1 "> Street Address 1: </span></td>
									      <td id="addrStr1">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrStreet2L" title="Street Address 2 "> Street Address 2: </span></td>
									      <td id="addrStr2">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCityL" title="City "> City: </span></td>
									      <td id="addrCity">  </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrStateL" title="State">  State: </span> </td>
									      <td id="addrState">  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrZipL" title="ZipCode ">  Zip: </span></td>
									      <td id="addrZip">   </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCntyL" title="County "> County: </span> </td>
									      <td id="addrCounty">    </td>	
									      </tr>
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCensusTractL" title="Census Tract "> Census Tract: </span> </td>
									      <td id="addrCensusTract">    </td>	
									      </tr>
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCntryL" title="Country "> Country: </span> </td>
									      <td id="addrCountry">   </td>	
									      </tr>
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCommentsL" title="Comments "> Address Comments: </span> </td>
									      <td id="addrComments">   </td>	
									      </tr> 
			                     		 </table>     
									     </td></tr>  
									     <% } %>                   
                                  </nedss:bluebar>
			                    </nedss:bluebar>
			                    
			                     <nedss:bluebar id="patSearch9" name="Phone & Email" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                    <nedss:bluebar id="patSearch10" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                      <tr> <td colspan="2" width="100%">									
									<table  class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<%if(flag){
								            %>
									<td width="6%" style = "background-color: #EFEFEF; border:1px solid #666666">  </td>
									<% } %>
									<th width="15%"><b> As of </b> </th>
									<th width="15%"><b> Type </b></th>
									<th width="15%"><b> Phone Number </b></th>
									<th width="15%"><b> Email Address </b></th>
									<th width="30%"> <b>Comments </b></th>				
									</tr>							
									</thead>
									<tbody id="patternphoneTable">
									        <tr id="patternphoneTableRow" class="odd" style="display:none">
									        <%if(flag){
								            %>
								            <td  width="6%"> 
								            <table role="presentation" width="100%">
								            <tr>
								            
								            <td style="text-align:left;">
											  <a href id="viewIdSubSection" onclick="viewClicked(this.id,'phoneTable');return false" style="cursor:pointer"> Details </a>
											 </td>
											
 											</tr>
 											</table>
 											</td> 	
 										<% } %> 											
									            <td id="tablephDate">
									            &nbsp;
									            </td>
									            <td id="tablephTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tablephNum">								            
									            &nbsp; 
									            </td >
									            <td id="tablephEmail">
									             &nbsp;							            
									            </td >
									            <td id="tablephComments">
									            &nbsp;								          
									            </td >									           								
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternphoneTable">
									 <tr id="nopatternphoneTableRow" class="odd">
									 <td colspan="100%"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									<%if(flag){
								            %>
									<tr><td colspan="100%">
									 <table role="presentation" id="phoneTable" align="center" width="100%">
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="PhAsOfL" title="Phone as of Date ">  Phone & Email as of: </span></td>
									      <td id="phDate" width="100%">  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="PhTypeL" title="Phone Type">  Type: </span>  </td>
									      <td id="phType">    </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhUseL" title="Address Type"> Use: </span></td>
									      <td id="phUse">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhCntryCdL" title="Country Code "> Country Code: </span></td>
									      <td id="phCntryCd">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhNumL" title="Phone Number"> Phone Number: </span></td>
									      <td id="phNum">  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhExtL" title="Extension "> Extension: </span></td>
									      <td id="phExt">    </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhEmailL" title="Email">  Email: </span> </td>
									      <td id="phEmail">   </td>	
									      </tr>
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="phUrlL" title="URL">  URL: </span> </td>
									      <td id="phUrl">  </td>	
									      </tr>										      
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhCommentsL" title="Comments "> Phone & Email Comments:</span> </td>
									      <td id="phComments">  </td>	
									      </tr>
									 </table>     
									     </td></tr> 
									     <%} %>               
			                     
                                 </nedss:bluebar>
			                   </nedss:bluebar>
			                    
			                     <nedss:bluebar id="patSearch11" name="Identification" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch12" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                     
			                       <tr> <td colspan="2" width="100%">									
									<table  class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<%if(flag){
								            %>
									<td style = "background-color: #EFEFEF; border:1px solid #666666" width="6%">  </td>
									<% } %>
									<th width="15%"><b> As of </b> </th>
									<th width="25%"><b> Type </b></th>
									<th width="25%"><b> Authority </b></th>
									<th width="25%"><b> Value </b></th>			
									</tr>							
									</thead>
									<tbody id="patternidenTable">
									        <tr id="patternidenTableRow" class="odd" style="display:none">
 										<%if(flag){
								            	%>									        
								            <td  width="6%"> 
								            <table role="presentation" width="100%">
								            <tr>
								           
								            <td style="text-align:left;">
											  <a href id="viewIdSubSection" onclick="viewClicked(this.id,'idenTable');return false" style="cursor:pointer"> Details </a>
											
											  
											 </td>
											
 											</tr>
 											</table>
 											</td> 
 										    <%} %>	
									            <td id="tableidDate">
									            &nbsp;
									            </td>
									            <td id="tableidTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tableidAssgnDesc">								            
									            &nbsp; 
									            </td >
									            <td id="tableidValue">
									             &nbsp;							            
									            </td >									            								           								
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternidenTable">
									 <tr id="nopatternidenTableRow" class="odd">
									 <td colspan="100%"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									<%if(flag){
								            %>
									 <tr><td colspan="100%">
									 <table role="presentation" id="idenTable" align="center" width="100%">
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="IdAsOfL" title="Identification as of Date ">  Identification as of: </span></td>
									      <td id="idDate" width="100%">    </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="IdTypeL" title="Identification Type">  Type: </span>  </td>
									      <td id="idType">   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="IdAssgnL" title="Assigning Authority"> Assigning Authority: </span></td>
									      <td id="idAssgn">    </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="IdValueL" title="Country Code "> ID Value: </span></td>
									      <td id="idValue">   </td>	
									      </tr>										      							      
									    </table>     
									     </td></tr> 
									     <% } %>
			                  
                                 </nedss:bluebar>
			                    </nedss:bluebar>
			                    
			                     <nedss:bluebar id="patSearch13" name="Race" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch14" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                      <tr> <td colspan="2" width="100%">									
									<table  class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<%if(flag){
								            %>
									<td style = "background-color: #EFEFEF; border:1px solid #666666" width="6%">  </td>
									<% } %>
									<th width="15%"><b>  As of: </b> </th>
									<th width="35%"><b> Race </b></th>
									<th width="40%"><b> Detailed Race </b></th>
										
									</tr>							
									</thead>
									<tbody id="patternraceTable">
									        <tr id="patternraceTableRow" class="odd" style="display:none">
									        <%if(flag){
								            	%>
								            <td width="6%"> 
								            <table role="presentation" width="100%">
								            <tr>
								            
								            <td style="text-align:left;">
											<a href id="viewIdSubSection" onclick="viewClicked(this.id,'raceTable');return false" style="cursor:pointer"> Details </a>
											 </td>
											
 											</tr>
 											</table>
 											</td>
 										 <% } %>	
									            <td id="tableraceDate">
									            &nbsp;
									            </td>
									            <td id="tableraceTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tableraceDetailCatDesc">								            
									            &nbsp; 
									            </td >
									           							            								           								
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternraceTable">
									 <tr id="nopatternraceTableRow" class="odd">
									 <td colspan="100%"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									<%if(flag){
								            %>
									 <tr><td colspan="100%">
									 <table role="presentation" id="raceTable" align="center" width="100%">
									      <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="RaceAsOfL" title="Race as of Date ">  Identification as of: </span></td>
									      <td id="raceDate" width="100%">    </td>	
									      </tr>	
									       <tr>
									        <td class="fieldName"><span class=" InputFieldLabel" id="RaceTypeL" title="Race Type">  Race: </span>  </td>
									       <td id="raceType">		      
											</td>								     
									    
									        </tr>	
									        <tr>
									         <td class="fieldName"> <span class=" InputFieldLabel" id="RaceDetailCatL" title="Race Details"> Detailed Race: </span></td>
									          <td id="raceDetailCat"> 		     </td>	
									     
									         </tr>	
									  </table>								      
									 </td></tr>  
									 <% } %> 
									       
                                 </nedss:bluebar>
			                    </nedss:bluebar>
			                    
			                  
			                    
			                      <% if(request.getAttribute("asOfDateEthnicity") != null && !((String)request.getAttribute("asOfDateEthnicity")).trim().equals("")){%>
			                      <%String secstr = "Ethnicity (as of "+(String)request.getAttribute("asOfDateEthnicity")+")";%>
			                        <nedss:bluebar id="patSearch15" name="<%=secstr%>" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch16" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                         
									<tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Ethnicity:</span>
									</td><td>
									<span id="NBS108"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.ethnicGroupInd" codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Spanish Origin:</span>
									</td>
									<td>
									<span id="NBS109"/>
									<%if(request.getAttribute("parsedEthnicity")!= null){ %>
							         <% String ethcat= (String)request.getAttribute("parsedEthnicity");
							         String ethcatDesc="";
							            while(ethcat.length()>2){ 
							             String str1= ethcat.substring(0,ethcat.indexOf("|")); 
							             ethcat = ethcat.substring(ethcat.indexOf("|")+1,ethcat.length());
							             ethcatDesc = ethcatDesc + CachedDropDowns.getCodeDescTxtForCd(str1, "P_ETHN")+" | ";
							             }%>
								            <%if(!ethcatDesc.equals("") && ethcatDesc.length()>2){%>
								            <%=ethcatDesc.substring(0,ethcatDesc.length()-2)%>
								            <%} %>
								        <%} %>     
									</td></tr>
	                               <!--Reason Unknown Cd-->
									<tr><td class="fieldName">
											<span class=" InputFieldLabel" id="ReasonUnknownL" title="Indicates unknown reason code for ethnicity.">
											Reason Unknown:</span>
										</td>
										<td>
											<span id="ReasonUnknown"/>
											<nedss:view name="DSPersonForm" property="person.thePersonDT.ethnicUnkReasonCd" codeSetNm="P_ETHN_UNK_REASON"/>
										</td></tr>
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                     <% }else{%>
			                        <nedss:bluebar id="patSearch15" name="Ethnicity" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch16" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                      
			                        
									<tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Ethnicity:</span>
									</td><td>
									<span id="NBS108"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.ethnicGroupInd" codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Spanish Origin:</span>
									</td>
									<td>
									<span id="NBS109"/>
									<%if(request.getAttribute("parsedEthnicity")!= null){ %>
							         <% String ethcat= (String)request.getAttribute("parsedEthnicity");
							         String ethcatDesc="";
							            while(ethcat.length()>2){ 
							             String str1= ethcat.substring(0,ethcat.indexOf("|")); 
							             ethcat = ethcat.substring(ethcat.indexOf("|")+1,ethcat.length());
							             ethcatDesc = ethcatDesc + CachedDropDowns.getCodeDescTxtForCd(str1, "P_ETHN")+" | ";
							             }%>
								            <%if(!ethcatDesc.equals("") && ethcatDesc.length()>2){%>
								            <%=ethcatDesc.substring(0,ethcatDesc.length()-2)%>
								            <%} %>
								        <%} %>     
									</td></tr>
	                                <!--Reason Unknown Cd-->
									<tr><td class="fieldName">
											<span class=" InputFieldLabel" id="ReasonUnknownL" title="Indicates unknown reason code for ethnicity.">
											Reason Unknown:</span>
										</td>
										<td>
											<span id="ReasonUnknown"/>
											<nedss:view name="DSPersonForm" property="person.thePersonDT.ethnicUnkReasonCd" codeSetNm="P_ETHN_UNK_REASON"/>
										</td></tr>
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                    	<% }%>
			                      
	                               
	                             
			                      <% if(request.getAttribute("asOfDateSex") != null && !((String)request.getAttribute("asOfDateSex")).trim().equals("")){%>
			                      <%String secstr = "Sex and Birth (as of "+(String)request.getAttribute("asOfDateSex")+")";%>
			                       <nedss:bluebar id="patSearch17" name="<%=secstr%>" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch18" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	                               
	                               <tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="NBS104L">DOB:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.birthTime_s"  />								
							
									<html:hidden name="DSPersonForm" property="person.thePersonDT.birthTime_s" styleId="patientDOB"/>
									<html:hidden name="DSPersonForm" property="person.thePersonDT.birthTimeCalc_s" styleId="calcDOB"/>	
									<input id="today" type="hidden" name="" value="<%=PersonUtil.formatCurrentDate()%>">
									
									
									
									</td></tr>
									 <tr><td class="fieldName">Current Age:</td>
									 <td ><span id="currentAge"></span>
						              &nbsp;
						             <span id="currentAgeUnits"></span>
						             <SCRIPT Language="JavaScript" Type="text/javascript">displayCalcAge()</SCRIPT>
						             </td>
                                     </tr>
                                      <tr><td class="fieldName">Current Sex:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.currSexCd" codeSetNm="SEX"/>
						            
						             </td>
                                     </tr>
                                     </tr>
                                      <tr><td class="fieldName">Unknown Reason:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.sexUnkReasonCd" codeSetNm="SEX_UNK_REASON"/>
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Transgender Information:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.preferredGenderCd" codeSetNm="NBS_STD_GENDER_PARPT"/>
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Additional Gender:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.additionalGenderCd"/>
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Birth Sex:</td>
									 <td >
									  <nedss:view name="DSPersonForm" property="person.thePersonDT.birthGenderCd" codeSetNm="SEX"/>
						            
						             </td>
                                     </tr>
                                      <tr><td class="fieldName">Multiple Birth:</td>
									 <td >
									   <nedss:view name="DSPersonForm" property="person.thePersonDT.multipleBirthInd" codeSetNm="YNU"/>				   
						             
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Birth Order:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.birthOrderNbrStr"  />						            
						             </td>
                                     </tr>
                                      <tr><td class="fieldName">Birth City:</td>
									 <td >
									  <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cityDescTxt"  />	
									  
						             </td>
                                     </tr>
                                      <tr>                                       
                                      <td class="fieldName">Birth State:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.stateCd" codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
									 
                                     </tr>
                                      <tr><td class="fieldName">Birth County:</td>
									 <td >
									
									  <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cntyCd" methodNm="CountyCodes" methodParam="<%=(String)request.getAttribute(\"birthState\")%>"/>
																	 
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Birth Country:</td>
									 <td >
									  <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cntryCd" codeSetNm="PSL_CNTRY"/>
									
									
						             </td>
                                     </tr>	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                    <%}else{ %>
			                     <nedss:bluebar id="patSearch17" name="Sex and Birth" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch18" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	                               
	                                <tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="NBS104L">DOB:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.birthTime_s"  />
									
									<html:hidden name="DSPersonForm" property="person.thePersonDT.birthTime_s" styleId="patientDOB"/>
										<html:hidden name="DSPersonForm" property="person.thePersonDT.birthTime_s" styleId="calcDOB"/>	
									<input id="today" type="hidden" name="" value="<%=PersonUtil.formatCurrentDate()%>">
									
									
									
									</td></tr>
									 <tr><td class="fieldName">Current Age:</td>
									 <td ><span id="currentAge"></span>
						              &nbsp;
						             <span id="currentAgeUnits"></span>
						             <SCRIPT Language="JavaScript" Type="text/javascript">displayCalcAge()</SCRIPT>
						             </td>
                                     </tr>
                                      <tr><td class="fieldName">Current Sex:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.currSexCd" codeSetNm="SEX"/>
						            
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Birth Sex:</td>
									 <td >
									  <nedss:view name="DSPersonForm" property="person.thePersonDT.birthGenderCd" codeSetNm="SEX"/>
						            
						             </td>
                                     </tr>
                                      <tr><td class="fieldName">Multiple Birth:</td>
									 <td >
									   <nedss:view name="DSPersonForm" property="person.thePersonDT.multipleBirthInd" codeSetNm="YNU"/>				   
						             
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Birth Order:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.birthOrderNbrStr"  />						            
						             </td>
                                     </tr>
                                      <tr><td class="fieldName">Birth City:</td>
									 <td >
									  <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cityDescTxt"  />	
									  
						             </td>
                                     </tr>
                                      <tr>                                       
                                      <td class="fieldName">Birth State:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.stateCd" codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
									 
                                     </tr>
                                      <tr><td class="fieldName">Birth County:</td>
									 <td >
									
									  <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cntyCd" methodNm="CountyCodes" methodParam="<%=(String)request.getAttribute(\"birthState\")%>"/>
																	 
						             </td>
                                     </tr>
                                     <tr><td class="fieldName">Birth Country:</td>
									 <td >
									  <nedss:view name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cntryCd" codeSetNm="PSL_CNTRY"/>
									
									
						             </td>
                                     </tr>	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                     <%} %>
			                     
			                     <% if(request.getAttribute("asOfDateMorbidity") != null && !((String)request.getAttribute("asOfDateMorbidity")).trim().equals("")){%>
			                      <%String secstr = "Mortality (as of "+(String)request.getAttribute("asOfDateMorbidity")+")";%>
			                        <nedss:bluebar id="patSearch19" name="<%=secstr%>" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch20" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                       <tr><td class="fieldName">									
									<span title="Is the patient deceased" id="NBS104L">Is the patient Deceased:</span>
									</td><td>
									<span id="NBS106"/>		
									<nedss:view name="DSPersonForm" property="person.thePersonDT.deceasedIndCd" codeSetNm="YNU"/>							
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Date of Death" id="NBS104L">Date of Death:</span>
									</td><td>
									<span id="NBS186"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.deceasedTime_s"  />
																
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Death City" id="NBS104L">Death City:</span>
									</td><td>
									<span id="NBS196"/>
									<nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cityDescTxt"  />							
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Death State" id="NBS104L">Death State:</span>
									</td><td>
									<span id="NBS176"/>									
									 <nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.stateCd" codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Death County" id="NBS104L">Death County:</span>
									</td><td>
									<span id="NBS166"/>	
									  <nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cntyCd" methodNm="CountyCodes" methodParam="<%=(String)request.getAttribute(\"deathState\")%>"/>								
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Death Country" id="NBS104L">Death Country:</span>
									</td><td>
									<span id="NBS156"/>	
									 <nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cntryCd" codeSetNm="PSL_CNTRY"/>								
								
									</td></tr>                               
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                     <% }else{%>
			                        <nedss:bluebar id="patSearch19" name="Mortality" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch20" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                      
			                        <tr><td class="fieldName">									
									<span title="Is the patient deceased" id="NBS104L">Is the patient Deceased:</span>
									</td><td>
									<span id="NBS106"/>		
									<nedss:view name="DSPersonForm" property="person.thePersonDT.deceasedIndCd" codeSetNm="YNU"/>							
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Date of Death" id="NBS104L">Date of Death:</span>
									</td><td>
									<span id="NBS186"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.deceasedTime_s"  />
																
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Death City" id="NBS104L">Death City:</span>
									</td><td>
									<span id="NBS196"/>
									<nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cityDescTxt"  />							
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Death State" id="NBS104L">Death State:</span>
									</td><td>
									<span id="NBS176"/>									
									 <nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.stateCd" codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Death County" id="NBS104L">Death County:</span>
									</td><td>
									<span id="NBS166"/>	
									  <nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cntyCd" methodNm="CountyCodes" methodParam="<%=(String)request.getAttribute(\"deathState\")%>"/>								
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Death Country" id="NBS104L">Death Country:</span>
									</td><td>
									<span id="NBS156"/>	
									 <nedss:view name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cntryCd" codeSetNm="PSL_CNTRY"/>								
								
									</td></tr>                               
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                    	<% }%>
			                    	
			                    	   <% if(request.getAttribute("asOfDateGeneral") != null && !((String)request.getAttribute("asOfDateGeneral")).trim().equals("")){%>
			                      <%String secstr = "General Patient Information (as of "+(String)request.getAttribute("asOfDateGeneral")+")";%>
			                        <nedss:bluebar id="patSearch21" name="<%=secstr%>" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch22" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                       <tr><td class="fieldName">									
									<span title="Marital Status" id="NBS104L">Marital Status:</span>
									</td><td>
									<span id="NBS106"/>	
									<nedss:view name="DSPersonForm" property="person.thePersonDT.maritalStatusCd" codeSetNm="P_MARITAL"/>									
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Mother's Maiden Name" id="NBS104L">Mother's Maiden Name:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.mothersMaidenNm"  />
																	
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Number of Adults in Residence" id="NBS104L">Number of Adults in Residence:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.adultsInHouseNbrStr"  />							
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Number of Children in Residence" id="NBS104L">Number of Children in Residence:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.childrenInHouseNbr"  />
																	
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Primary Occupation" id="NBS104L">Primary Occupation:</span>
									</td><td>
									<span id="NBS106"/>	
																
									<%if(request.getAttribute("occupationCd")!= null){ %>						              
						               <%=psutil.getCodeDescTxtForCd((String)request.getAttribute("occupationCd"), "PRIMARY_OCCUPATION","")%>								            
						              <%} %>
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Highest Level of Education" id="NBS104L">Highest Level of Education:</span>
									</td><td>
									<span id="NBS106"/>	
									<nedss:view name="DSPersonForm" property="person.thePersonDT.educationLevelCd" codeSetNm="P_EDUC_LVL"/>									
									
									</td></tr> 
									 <tr><td class="fieldName">									
									<span title="Primary Language" id="NBS104L">Primary Language:</span>
									</td><td>
									<span id="NBS106"/>		
														
									<%if(request.getAttribute("primLangCd")!= null){ %>						              
						               <%=psutil.getCodeDescTxtForCd((String)request.getAttribute("primLangCd"), "PRIMARY_LANGUAGE","")%>								            
						              <%} %>
									</td></tr>
									<tr><td class="fieldName">Speaks English:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.speaksEnglishCd" codeSetNm="YNU"/>
						             </td>
                                     </tr>
	            					<logic:equal name="DSPersonForm" property="securityMap.hasHIVPermissions" value="T">
										<!-- State HIV Case ID -->
										<tr><td class="fieldName">
										<span class=" InputFieldLabel" id="eHARSIDL" title="State HIV Case ID.">
										State HIV Case ID:</span>
										</td>
										<td>
										  <nedss:view name="DSPersonForm" property="person.thePersonDT.eharsId"  />
										</td></tr>				
									</logic:equal>                       
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                     <% }else{%>
			                        <nedss:bluebar id="patSearch21" name="General Patient Information" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch22" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                      
			                         <tr><td class="fieldName">									
									<span title="Marital Status" id="NBS104L">Marital Status:</span>
									</td><td>
									<span id="NBS106"/>	
									<nedss:view name="DSPersonForm" property="person.thePersonDT.maritalStatusCd" codeSetNm="P_MARITAL"/>									
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Mother's Maiden Name" id="NBS104L">Mother's Maiden Name:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.mothersMaidenNm"  />
																	
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Number of Adults in Residence" id="NBS104L">Number of Adults in Residence:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.adultsInHouseNbrStr"  />							
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Number of Children in Residence" id="NBS104L">Number of Children in Residence:</span>
									</td><td>
									<span id="NBS106"/>
									<nedss:view name="DSPersonForm" property="person.thePersonDT.childrenInHouseNbr"  />
																	
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Primary Occupation" id="NBS104L">Primary Occupation:</span>
									</td><td>
									<span id="NBS106"/>	
																
									<%if(request.getAttribute("occupationCd")!= null){ %>						              
						               <%=psutil.getCodeDescTxtForCd((String)request.getAttribute("occupationCd"), "PRIMARY_OCCUPATION","")%>								            
						              <%} %>
									</td></tr>
									
									<tr><td class="fieldName">									
									<span title="Highest Level of Education" id="NBS104L">Highest Level of Education:</span>
									</td><td>
									<span id="NBS106"/>	
									<nedss:view name="DSPersonForm" property="person.thePersonDT.educationLevelCd" codeSetNm="P_EDUC_LVL"/>									
									
									</td></tr> 
									 <tr><td class="fieldName">									
									<span title="Primary Language" id="NBS104L">Primary Language:</span>
									</td><td>
									<span id="NBS106"/>		
														
									<%if(request.getAttribute("primLangCd")!= null){ %>						              
						               <%=psutil.getCodeDescTxtForCd((String)request.getAttribute("primLangCd"), "PRIMARY_LANGUAGE","")%>								            
						              <%} %>
									</td></tr>                       
	            					<tr><td class="fieldName">Speaks English:</td>
									 <td >
									 <nedss:view name="DSPersonForm" property="person.thePersonDT.speaksEnglishCd" codeSetNm="YNU"/>
						             </td>
                                     </tr>
	            					<logic:equal name="DSPersonForm" property="securityMap.hasHIVPermissions" value="T">
										<!-- State HIV Case ID -->
										<tr><td class="fieldName">
										<span class=" InputFieldLabel" id="eHARSIDL" title="State HIV Case ID.">
										State HIV Case ID:</span>
										</td>
										<td>
										  <nedss:view name="DSPersonForm" property="person.thePersonDT.eharsId"  />
										</td></tr>				
									</logic:equal>
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                    	<% }%>
			                    	
			                   
			               	   <!-- SECTION : Local Fields -->
							   <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
							    <nedss:bluebar id="LocalFieldsSection" name="Custom Fields" classType="bluebarsect">
							        <!-- SUB_SECTION :  Local Fields -->
							        <nedss:bluebar displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" id="LocalFieldsSubSection" classType="bluebarsubSect" >
							          	<c:out value="${NEWPAT_LDFS}"/>
							        </nedss:bluebar>
							   </nedss:bluebar>
							    <%} %> 
					                 <table role="presentation" class="showup" width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
									
									<tr bgcolor="#003470">
										<td height="25 px" valign="center" align="left">
										</td>
										<td align="right">
										<% if(request.getAttribute(NEDSSConstants.EDITBUTTON)!=null && request.getAttribute(NEDSSConstants.EDITBUTTON).equals("true") ){ %>
												<input type="button" name="edit" value="  Edit  " onclick="EditPatient();"/>
										<% } %>
										</td>
									</tr>
									<tr>
										<td align="right" colspan="2"></td>
									</tr>
							     </table>
						<div class="tabNavLinks">
						<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
						<a href="javascript:navigateTab('next')"> Next </a>
						<input name="endOfTab" type="hidden"/>
            					</div>
						</div> 
						</div></td></tr>

        
        
        
        
             