<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Templates </title>
        <base target="_self">
        <%@ include file="/jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageImportExportLogForm.js"></SCRIPT>
        <script type="text/javaScript" src="pagemanagementSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>        
        
        <script type="text/javaScript">     
        var isFormSubmission = false;
        function closePopup()
		    {
			   
	                self.close();	
	                var opener = getDialogArgument();  	                 
	                var invest = null; 	                      
	                invest = getElementByIdOrByNameNode("pamview", opener.document.document)
	                if (invest == null) {
               			  invest = getElementByIdOrByNameNode("blockparent", opener.document.document);                   
         			 }
         			
	                invest.style.display = "none";  
	                
	                return true;               
	               
			   
			    }  
			    
       function retActLog()
		    {
			    
	               document.manageImportExportLogForm.action ="/nbs/ManageImpExpLog.do?method=manageImportExportLogLib&actionMode=Manage&initLoad=true"
                      document.manageImportExportLogForm.submit();       
	               
			       
			    } 
			    
 function printCodeSet() {
			            var abc = $j("div.exportlinks a:last").attr("href");
			            var abc1 = abc.replace("d-1338913-e=5", "d-446288-e=5");
			        	window.location.href = abc1 == null ? "#" :  abc1;
			        }
			        
function downloadCodeSet() {
			            var abc = $j("div.exportlinks a:first").attr("href");
			            var abc1 = abc.replace("d-1338913-e=1", "d-446288-e=1");
			        	window.location.href = abc1 == null ? "#" :  abc1;
			        }
			        
function printQuestion() {
			            var abc = $j("div.exportlinks a:last").attr("href");
			        	window.location.href = abc == null ? "#" :  abc;
			        }
			        
function downloadQuestion() {
			            var abc = $j("div.exportlinks a:first").attr("href");
			        	var abc1 = abc.replace( "d-446288-e=1","d-1338913-e=1");
			        	window.location.href = abc1 == null ? "#" :  abc1;
			        }
    	 
		

        </script>  
        <style type="text/css">
         div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
         div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
        
		.removefilter{
			background-color:#003470; width:100%; height:25px;
			line-height:25px;float:right;text-align:right;
			}
			removefilerLink {vertical-align:bottom;  }
			.hyperLink
			{
			    font-size : 10pt;
			    font-family : Geneva, Arial, Helvetica, sans-serif;
			    color : #FFFFFF;
				text-decoration: none;
			}
		</style>
    </head>
         <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode")); %>
     <% if (printMode.equals("print")) {%>
        <body onload="autocompTxtValuesForJSP();startCountdown();" onunload="return closePopup();" > 
       <% }else{ %>
        <body onload="autocompTxtValuesForJSP();startCountdown();"  > 
        <% } %>
        <div id="blockparent"></div>
         <html:form action="/ManageImpExpLog.do">
            <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
	                		 <!-- Top Nav Bar and top button bar -->
								<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
								 
							    <table role="presentation" style="width:100%;">
									<tr>
									    <td style="text-align:right"  id="srtLink"> 
									       <a href="#" onclick="return retActLog();" class="backToTopLink"> 									       
									            Return to Activity Log
									        </a>  
									        <input type="hidden" id="actionMode" value="${manageImportExportLogForm.actionMode}"/>
									    </td>
									</tr>
									
								</table>
								 <!-- top bar -->
								<tr><td>&nbsp;</td></tr>

							 <div class="infoBox nedssLightYellowBg">
								    <table role="presentation" align="center" style="width:100%;">
								        <tr>
								            <!-- Name, Sex, DOB, Patient Id -->
								            <td>
								                <table role="presentation" align="left" style="width:100%;">
								                    <tr>
								                        <td>
								                            <span class="label">Template Name: </span>
								                            <span class="value"> ${manageImportExportLogForm.attributeMap.templateName} </span>
								                        </td>
								                        <td>
								                            <span class="label"> Processed Time: </span>
								                            <span class="value"> ${manageImportExportLogForm.attributeMap.processedTime} </span>
								                        </td>
								                     </tr>
								                     <tr>
								                        <td>
								                            <span class="label"> Source: </span>
								                            <span class="value"> ${manageImportExportLogForm.attributeMap.source} </span>
								                        </td>
								                        <td>
								                            <span class="label"> Status: </span>
								                            <span class="value"> Success </span>
								                        </td>            
								                   </tr>
								                </table>
								            </td>
								        </tr>
								     </table>								
								</div>
						           
								 <!-- Container Div -->
	          				 
 							 
	                			 <!-- SECTION : Investigation Information --> 
	                			 
							   <nedss:container id="id1" name="Activity Details" classType="sect" displayImg ="false" includeBackToTopLink="false">
  								 <fieldset style="border-width:0px;" id="codeset">
  								  <!-- SUB_SECTION : Investigation Details -->  	
       
			                                <nedss:container id="subsec1" name="Value Sets" classType="subSect" displayLink="false">
	       		                        	<tr><td>
	       		                                <table role="presentation" width="98%" align="center">
	       		                                 <!-- Concept confirm message -->	
	       		                                 	  <tr>
										 <td align="right"> 							                   
								            	<input type="button"  style="width: 90px" value="Print" id=" " onclick="return printCodeSet();"/> 
							           	 	<input type="button"  style="width: 90px" value="Download" id=" " onclick="return downloadCodeSet();"/> 
							                </td>
							              </tr>
			       								          
	       		                                    <tr>
	       		                                        <td align="center">
	       		                                            <display:table name="manageVocabList" class="dtTable" pagesize="10"  id="parent" requestURI="/ManageImpExpLog.do?method=viewActivityLogDetails&sort=Vocab&existing=true" sort="external" export="true" excludedParams="answerArray(PROCESSEDTIME) answerArray(TYPE) answerArray(TEMPLATENAME) answerArray(SOURCE) answerArray(STATUS) sort method">
	       		                                                 <display:column property="recordId" title="Value Set Code"  sortable="true"  sortName="getRecordId" defaultorder="ascending" style="width:20%;" />
																	<display:column property="recordName" title="Value Set Name"   sortable="true"  sortName="getRecordName" defaultorder="ascending" style="width:22%;" />
																	<display:column property="recordType" title="Type" sortable="true" sortName="getRecordType" defaultorder="ascending" style="width:10%;"/>
																	<display:column property="comment" title="New/Exisiting"  sortable="true"  sortName="getComment" defaultorder="ascending" style="width:12%;" />
																	<display:setProperty name="basic.empty.showtable" value="true"  /> 
	       		                                               
	       		                                            </display:table>
	       		                                        </td>
	       		                                    </tr>
	       		                                   
	       		                                </table>
	       		                             </td></tr>
		                                    </nedss:container>
                    
			                              <nedss:container id="subsec2" name="Questions" classType="subSect" displayLink="false">
	       		                        	<tr><td>
	       		                                <table role="presentation" width="98%" align="center">
	       		                                <tr><td>
	       		                                <table role="presentation" width="98%" align="center">
	       		                                 <!-- Concept confirm message -->	
	       		                                 	  <tr>
										 <td align="right"> 							                   
								            	<input type="button" style="width: 90px" value="Print" id=" " onclick="return printQuestion();"/> 
							           	 	<input type="button"  style="width: 90px" value="Download" id=" " onclick="return downloadQuestion();"/> 
							                </td>
							              </tr>

	       		                                 <!-- Concept confirm message -->						       								          
	       		                                    <tr>
	       		                                        <td align="center">
	       		                                            <display:table name="manageQuesList" class="dtTable" pagesize="10"  id="parent1"  requestURI="/ManageImpExpLog.do?method=viewActivityLogDetails&sort=Ques&existing=true" sort="external" export="true" excludedParams="answerArray(PROCESSEDTIME) answerArray(TYPE) answerArray(TEMPLATENAME) answerArray(SOURCE) answerArray(STATUS) sort method">
	       		                                                 <display:column property="recordId" title="ID"  sortable="true"  sortName="getRecordId" defaultorder="ascending" style="width:15%;" />
																	<display:column property="recordName" title="Unique Name"   sortable="true"  sortName="getRecordName" defaultorder="ascending" style="width:22%;" />
																	<display:column property="recordType" title="Type" sortable="true" sortName="getRecordType" defaultorder="ascending" style="width:10%;"/>
																	<display:column property="comment" title="New/Exisiting"  sortable="true"  sortName="getComment" defaultorder="ascending" style="width:12%;" />
																	<display:setProperty name="basic.empty.showtable" value="true"  /> 
	       		                                               
	       		                                            </display:table>
	       		                                        </td>
	       		                                    </tr>
	       		                                   
	       		                                </table>
	       		                             </td></tr>
		                                    </nedss:container>	
                                   </nedss:container>                        
	
             

						</td>
					</tr>
               </div>               		
               </html:form>
    </body>
</html>