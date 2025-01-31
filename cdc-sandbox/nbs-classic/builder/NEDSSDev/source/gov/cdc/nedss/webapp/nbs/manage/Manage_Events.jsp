<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>

<html lang="en">
	<head>
	<title>NBS: Manage Associations</title>
	<%@ include file="../../jsp/resources.jsp" %>
	<script language="javascript">
	    function labAddButtn(){
	        var lab = '${fn:escapeXml(AddLab)}';
	        document.forms[0].action =lab.replaceAll("&amp;","&");
	        document.forms[0].submit();
	    }
	    function morbAddButtn(){
	        var morb='${fn:escapeXml(AddMorb)}';
	        document.forms[0].action =morb.replaceAll("&amp;","&");
	        document.forms[0].submit();
	    }
	    function cancleButn(){  	
        	var cancel ='${fn:escapeXml(cancelButtonHref)}';
	    	var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
            	document.forms[0].action =cancel.replaceAll("&amp;","&");
     	        document.forms[0].submit();
            } else {
                return false;
            }
	    }
	    function submitBtn(){
	        // call the JS function to block the UI while saving is on progress.
	        blockUIDuringFormSubmission();
	        var submitB='${fn:escapeXml(formHref)}';
	        // continue with the form submission
	        setCheckBoxValue();
	        document.forms[0].action =submitB.replaceAll("&amp;","&");
	        document.forms[0].submit();
	    }
	    function setCheckBoxValue(){
	        var sb = "";
	        var filler = getElementByIdOrByName("chkboxIds");
	        var input = document.getElementsByTagName("input");
	        for(var i = 0; i < input.length; i++)   {
	                if(input[i].type == 'checkbox') {
	                    if(input[i].checked == true)
	                        sb = sb + input[i].name + "|";
	                }
	        }
	        filler.value=sb;
	    }
	    function treatmentAddButtn()
	    {
	    	var treatment = '<%=request.getAttribute("AddTreatment")%>';
	        document.forms[0].action =treatment;
	        document.forms[0].submit();
	    }
	    function docAddButtn()
	    {
	    	var document = '<%=request.getAttribute("AddDocument")%>';
	        document.forms[0].action =document;
	        document.forms[0].submit();
	    }
	    function showPrintFriendlyPage()
        {
            var divElt = getElementByIdOrByName("pamview");
            divElt.style.display = "block";
            var o = new Object();
            o.opener = self;

            var URL = '<%=request.getAttribute("PrintPage")%>';
            var dialogFeatures = "dialogWidth:800px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
            

            var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
        	
            return false;
        }
	    function closePrinterFriendlyWindow()
        {
            self.close();
            var opener = getDialogArgument();        
            var pview = getElementByIdOrByNameNode("pamview", opener.document)
            pview.style.display = "none";
            
            return false;   
        }
        function handleInputElts() {
            // disable links
        	$j("a[href]:not([href^=#])").removeAttr('href');
        	
        	// remove i/p elts.
        	$j(":input").remove();	
        }
	</script>
	
	<style type="text/css">
	    /* In the common.css file, tr of a table of class 'subSect' is set to
	    have a gray colored background. Reset it to #FFF (white) here */
	    
	    table.subSect tbody tr {background:#FFF;}
	</style>
	</head>
	<% 
	int subSectionIndex = 0; 
	String tabId = "events";
	%>
	<% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
    if (printMode.equals("print")) { %>
    <body onload="startCountdown();handleInputElts();" onunload="return closePrinterFriendlyWindow();">
    <% } else { %>
     <body onload="startCountdown();">
    <% } %>
        <div id="pamview"></div>
        <div id="doc3">
            <div id="bd">
                <!-- Top Nav Bar -->
                 <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                
                 <!-- Top button bar -->
                 <table role="presentation" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;" 
                        class="bottomButtonBar">
                    <tr>
                        <!-- General page actions like create, edit, delete and print --> 
                        <td style="vertical-align:top; padding:0px;">
                            <table role="presentation" align="right">
                                <tr>
                                <td style="vertical-align:top; padding-top:0px;">
								   <input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
								                                         border="0" name="Delete" id="delete"
								                                         alt="Print page button" title="Print page button" class="cursorHand"
								                                         onclick="return showPrintFriendlyPage();" /><br/>Print
                     	</td>
                                    <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Submit" id="Submit" 
                                                alt="Submit button"  title="Submit button" class="cursorHand" onclick="submitBtn()"><br/>Submit
                                    </td>
                                    <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" 
                                                class="cursorHand" onclick="cancleButn();"><br/>Cancel
                                    </td>
                                </tr>            
                            </table>
                        </td>
                    </tr>
                </table>
                
                <!--  Printer icon -->
                <div class="printerIconBlock screenOnly">
				    <table role="presentation" style="width:98%; margin:3px;">
				        <tr>
				            <td style="text-align:right; font-weight:bold;"> 
				                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
				            </td>
				        </tr>
				    </table>
				</div>
                
                <!-- Association Summary table-->
		         <%@ include file="AssociationSummary.jsp" %>  
		         <div class="infoBox messages" align="left">
				    <logic:equal name="STDProgramAreaOpen" value="true">
				     	<p style="text-align:left">If a Lab Report or Morbidity Report initiated this investigation, Disassociate Initiating Event security permission is required to disassociate.</p>
				     </logic:equal>
				     <logic:equal name="STDProgramAreaClosed" value="true">
				     	<p style="text-align:left">This investigation's status is closed. Please associate STD and HIV Lab Reports and Morbidity Reports by using Association to Investigations
				     	option from the report from the Patient's File. This will allow you to select the processing decision for associating the report to closed investigation.</p>
				     </logic:equal>
				</div>
				<logic:equal name="vaccineImportSuccess" value="true">
					<div class="infoBox success" id="successMessages" align="left">
						<p style="text-align:left">${vaccinationImportSucessMessage}</p>
					</div>
				</logic:equal>
				<logic:equal name="vaccineImportSuccess" value="false">
					<div class="infoBox errors" id="errorMessages" align="left">
						<p style="text-align:left">${vaccinationImportFailureMessage}</p>
					</div>
				</logic:equal>
                <!-- Lab, Morbidity, Treatment, Vaccination and Document  reports-->
                <html:form>
                <div class="view"  id="<%= tabId %>" style="text-align:center;">  
                <nedss:container id="section1" name="Associations" classType="sect" displayImg="false" includeBackToTopLink="no">
                	<%if(((String)request.getAttribute("AssoLabPermission")).equals("true")||((String)request.getAttribute("AssoMorbPermission")).equals("true")){%>
		                <jsp:include page="observationManage/Manage_Observation.jsp">
			                <jsp:param name="param2" value="<%=subSectionIndex%>" />
			                <jsp:param name="param3" value="<%=tabId%>" />
			              </jsp:include>
			        <%}%>
			       
		            <%if(((String)request.getAttribute("AssoVaccPermission")).equals("true")){%>
		                <jsp:include page="vaccinationManage/Manage_Vaccination.jsp">
			                <jsp:param name="param2" value="<%=subSectionIndex+2%>" />
			                <jsp:param name="param3" value="<%=tabId%>" />
			              </jsp:include>
		            <%}%>
		            <%if(((String)request.getAttribute("AssoTreatPermission")).equals("true")){%>
		                <jsp:include page="treatmentManage/Manage_Treatment.jsp">
			                <jsp:param name="param2" value="<%=subSectionIndex+3%>" />
			                <jsp:param name="param3" value="<%=tabId%>" />
			              </jsp:include>
		            <%}%>
		            <%if(((String)request.getAttribute("AssoDocPermission")).equals("true")){%>
		                <jsp:include page="documentManage/Manage_Document.jsp">
			                <jsp:param name="param2" value="<%=subSectionIndex+4%>" />
			                <jsp:param name="param3" value="<%=tabId%>" />
			              </jsp:include>
		           <%}%>
		        </nedss:container>  
		        </div>
                </html:form>
                
                <!-- Bottom button bar -->
                <table role="presentation" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;" 
                    class="bottomButtonBar">
                    <tr>
                        <!-- General page actions like create, edit, delete and print --> 
                        <td style="vertical-align:top; padding:0px;">
                            <table role="presentation" align="right">
                                <tr>
                                <td style="vertical-align:top; padding-top:0px;">
								   <input type="image" src="task_button/fa_submit.jpg"  width="30" height="40"
								                                         border="0" name="Delete" id="delete"
								                                         alt="Print page button" title="Print page button" class="cursorHand"
								                                         onclick="return showPrintFriendlyPage();" /><br/>Print
                     	</td>
                                    <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Submit" id="Submit" 
                                                alt="Submit button" title="Submit button" class="cursorHand" onclick="submitBtn()"><br/>Submit
                                    </td>
                                    <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" 
                                                class="cursorHand" onclick="cancleButn();"><br/>Cancel
                                    </td>
                                </tr>            
                            </table>
                        </td>
                    </tr>
                </table>
                
            </div> <!-- div id = bd -->
        </div> <!-- div id = doc3 -->
    </body> 
</html>