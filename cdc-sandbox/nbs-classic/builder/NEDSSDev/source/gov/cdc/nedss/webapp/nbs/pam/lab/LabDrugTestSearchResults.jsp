<%@ include file="/jsp/tags.jsp" %>
<%@ page import="java.util.*" %>      
<%@ page import="gov.cdc.nedss.entity.person.dt.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>      
<%@ page isELIgnored="false"%>
<html lang="en">
	<%String searchType = (String) request.getAttribute("searchType");
	                      if(NEDSSConstants.TEST_TYPE_LOCAL.equals(searchType)
	                    		  || NEDSSConstants.TEST_TYPE_LOINC.equals(searchType)) {%>
    <title>Drug Test Search Results</title>
    <%} else {%>
    	<title>Coded Organism Search Results</title>
    <%}%>
	<head>
	<base target="_self">
		<%@ include file="/jsp/resources.jsp" %>
		<script type="text/Javascript" src="/nbs/dwr/interface/JDecisionSupport.js"></SCRIPT>	
 	    <SCRIPT LANGUAGE="JavaScript">
 	   function populateResultedTestName(description, testCode)
 	  {               
 		  var opener = getDialogArgument();
       	var parent = opener;
		var parentDoc = parent.document;
		var form = JDecisionSupport;
		var changeElr=true;

		if(parent.window.location.href.indexOf("MyTaskList")!=-1 || parent.window.location.href.indexOf("PatientSearchResults1")!=-1){
			form =JBaseForm;
			changeElr=false;
		}
		form.getDwrPopulateResultedTestByUid(description, testCode, function(data){
 	    	dwr.util.setValue(parentDoc.getElementById("testDescription"), data);
 	    	dwr.util.setValue(parentDoc.getElementById("testCodeId"), testCode);
 	    	dwr.util.setValue(parentDoc.getElementById("testDescriptionId"), description+" ("+testCode+")");

		if(changeElr)
    		changeElrSectionDarkBGColor(parentDoc);

 	  	   	self.close();
 	  	});
 	  }


 	  
 	  function populateCodedResultName(description, resultCode)
 	  {              
 		var opener = getDialogArgument();
       		var parent = opener;
		var parentDoc = parent.document;
		var form = JDecisionSupport;
		var changeElr=true;
		
		if(parent.window.location.href.indexOf("MyTaskList")!=-1 || parent.window.location.href.indexOf("PatientSearchResults1")!=-1){
			form =JBaseForm;
			changeElr=false;
		}
		
		form.getDwrPopulateCodeResultByUid(description, resultCode, function(data){
 	    	dwr.util.setValue(parentDoc.getElementById("resultDescription"), data);
 	    	dwr.util.setValue(parentDoc.getElementById("codeResultId"), resultCode);
 	    	dwr.util.setValue(parentDoc.getElementById("resultDescriptionId"), description+" ("+resultCode+")");
 	    	if(changeElr){
	    		changeElrSectionDarkBGColor(parentDoc);
	    		changeElrCodedResultSelection(parentDoc);
 	    	}
 	  	   	self.close();
 	  	});
 	  }
			
  		function closePopup()
	    {              
	        self.close();
	
	    } 

  		function changeElrCodedResultSelection(parentDoc)
  		{
			if(parentDoc.getElementById("codeResultId").value != null || parentDoc.getElementById("codeResultId").value !="")
			{	
				var numericResult_text = parentDoc.getElementById("numericResult_text");
				//var numericResultL = parentDoc.getElementById("numericResultL");
				$j(numericResult_text).parent().find(":input").attr("disabled", true);
				$j(numericResult_text).parent().find(":input").css("background-color", "#666666");
			 	//$j(numericResultL).parent().find("span[title]").css("color", "#666666");
			 	
			 	var numericResultTypeList = parentDoc.getElementById("numericResultTypeList");
			 	$j($j(numericResultTypeList).parent().parent()).find(":input").attr("disabled", true);
			 	$j($j(numericResultTypeList).parent().parent()).find(":input").css("background-color", "#666666");
			 	$j($j(numericResultTypeList).parent().parent()).find("img").attr("disabled", true);
			 	$j($j(numericResultTypeList).parent().parent()).find("img").attr("tabIndex", "-1");
			 	
				
				
				var textResult_text = parentDoc.getElementById("textResult_text");
				//var textResultL = parentDoc.getElementById("textResultL");
				$j(textResult_text).parent().find(":input").attr("disabled", true);
				$j(textResult_text).parent().find(":input").css("background-color", "#666666");
			 	//$j(textResultL).parent().find("span[title]").css("color", "#666666");

				var resultOperatorList = parentDoc.getElementById("resultOperatorList");
				$j($j(resultOperatorList).parent().parent()).find(":input").attr("disabled", true);
				$j($j(resultOperatorList).parent().parent()).find(":input").css("background-color", "#666666");
				$j($j(resultOperatorList).parent().parent()).find("img").attr("disabled", true);
			 	$j($j(resultOperatorList).parent().parent()).find("img").attr("tabIndex", "-1");

				var numericResultOperatorList = parentDoc.getElementById("numericResultOperatorList");
				$j($j(numericResultOperatorList).parent().parent()).find(":input").attr("disabled", true);
				$j($j(numericResultOperatorList).parent().parent()).find(":input").css("background-color", "#666666");
				$j($j(numericResultOperatorList).parent().parent()).find("img").attr("disabled", true);
				$j($j(numericResultOperatorList).parent().parent()).find("img").attr("tabIndex", "-1");
				
			}
  		}

  		function changeElrSectionDarkBGColor(parentDoc)
  		{
  			 var t = parentDoc.getElementById("ElrIdAdvancedSubSection");
  			 t.style.display = "";
  			 for (var i = 0; i < 4; i++){
  			 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
  			 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
  			}
  			 var numericResultTable = parentDoc.getElementById("numericResultTable");
  			 numericResultTable.style.display = "";
  			 $j(numericResultTable).find("tr").css("background-color","#BCD4F5");	 
  			 
  			 var textResultTable = parentDoc.getElementById("textResultTable");
  			 textResultTable.style.display = "";
  			 $j(textResultTable).find("tr").css("background-color","#BCD4F5");
  			
  		}  
		
	</SCRIPT>		
	</head>
	<body class="popup">
	<table role="presentation" id="container" height="500" width="760" >
	<tr>
	<td valign="top" style="padding-left:1%">
        <!-- Page title -->
    <%//String searchType = (String) request.getAttribute("searchType");
	                      if(NEDSSConstants.TEST_TYPE_LOCAL.equals(searchType)
	                    		  || NEDSSConstants.TEST_TYPE_LOINC.equals(searchType)) {%>
	        <div class="popupTitle">
	            Drug Test Search Results
        </div>
      <%} else {%>
    	<title>Coded Result Search Results</title>
    <%}%>
        
        <!-- Top button bar -->
        <%String queId = (String) request.getAttribute("identifier");%>
        <div class="popupButtonBar">
            <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="closePopup();"/>
        </div>
        
        <!-- Results block -->
        <div style="width:100%; text-align:center;">
            <div style="width:98%;">
	            <form method="post" id="nedssForm" action="">
	                <nedss:container id="section1" name="Search Results" classType="sect" 
	                        displayImg ="false" displayLink="false" includeBackToTopLink="no">
	                        
	                    <div style="width:100%; text-align:right;margin:4px 0px 4px 0px;">
	                        <a href="${fn:escapeXml(NewSearchLink)}">New Search</a>&nbsp;|&nbsp;
	                        <a href="${fn:escapeXml(RefineSearchLink)}">Refine Search</a>
	                    </div>
	                    
	                    <%//String searchType = (String) request.getAttribute("searchType");
	                      if(NEDSSConstants.TEST_TYPE_LOCAL.equals(searchType)
	                    		  || NEDSSConstants.TEST_TYPE_LOINC.equals(searchType)) {%>
						        <div class="infoBox messages">
			                        Your Search Criteria: Resulted Test contains ' <i> ${fn:escapeXml(SearchCriteria)}</i>
			                        ' resulted in <b>${fn:escapeXml(ResultsCount)}</b> possible matches.
			                    </div>
					      <%} else {%>
					    	<div class="infoBox messages">
		                        Your Search Criteria: Coded Result Description contains ' <i>${fn:escapeXml(SearchCriteria)}</i>
		                        ' resulted in <b> ${fn:escapeXml(ResultsCount)}</b> possible matches.
		                    </div>
					    <%}%>
	                    
	                    
	                    <%//String searchType = (String) request.getAttribute("searchType");
	                      if(NEDSSConstants.TEST_TYPE_LOCAL.equals(searchType)) {%>
	                    <table role="presentation" width="100%" border="0" cellspacing="0">
	                        <tr>
	                           <td align="center">              
	                               <display:table name="resultedTestList" class="dtTable" pagesize="20"  id="parent" requestURI="">
	                               	  <display:column property="recordStatusCd" title="Code" class="dstag"/> 
	                                  <display:column property="labTestDescription" title="Drug Name" class="dstag"/>
	                                  <display:setProperty name="basic.empty.showtable" value="true"/>
	                                </display:table> 
	                            </td>
	                        </tr>
	                    </table> 
	                    
	                    <% }else if(NEDSSConstants.TEST_TYPE_LOINC.equals(searchType)) {%>
	                    <table role="presentation" width="100%" border="0" cellspacing="0">
	                        <tr>
	                           <td align="center">              
	                               <display:table name="resultedTestList" class="dtTable" pagesize="20"  id="parent" requestURI="">
	                                  <display:column property="recordStatusCd" title="Code" class="dstag"/> 
	                                  <display:column property="loincComponentName" title="Drug Name" class="dstag"/>
	                                  <display:column property="loincMethod" title="Method" class="dstag"/>
	                                  <display:column property="loincSystem" title="System" class="dstag"/>
	                                  <display:setProperty name="basic.empty.showtable" value="true"/>
	                                </display:table> 
	                            </td>
	                        </tr>
	                    </table> 
	                    <% }else if(NEDSSConstants.RESULT_TYPE_LOCAL.equals(searchType)) {%>
	                    <table role="presentation" width="100%" border="0" cellspacing="0">
	                        <tr>
	                           <td align="center">              
	                               <display:table name="resultedTestList" class="dtTable" pagesize="20"  id="parent" requestURI="">
	                               	  <display:column property="recordStatusCd" title="<p style='display:none'>Action</p>" class="dstag"/>
	                                  <display:column property="labResultCd" title="Code" class="dstag"/> 
	                                  <display:column property="labResultDescription" title="Description" class="dstag"/>
	                                  <display:setProperty name="basic.empty.showtable" value="true"/>
	                                </display:table> 
	                            </td>
	                        </tr>
	                    </table> 
	                    <%}else if(NEDSSConstants.RESULT_TYPE_SNOMED.equals(searchType)) {%>
	                    <table role="presentation" width="100%" border="0" cellspacing="0">
	                        <tr>
	                           <td align="center">              
	                               <display:table name="resultedTestList" class="dtTable" pagesize="20"  id="parent" requestURI="">
	                               	<display:column property="recordStatusCd" title="<p style='display:none'>Action</p>" class="dstag"/>
	                                  <display:column property="snomedCd" title="Code" class="dstag"/> 
	                                  <display:column property="snomedDescTxt" title="Description" class="dstag"/>
	                            
	                                  <display:setProperty name="basic.empty.showtable" value="true"/>
	                                </display:table> 
	                            </td>
	                        </tr>
	                    </table> 
	                    <%} %>
	                </nedss:container>
	            </form>
	        </div>    
        </div>
        
        </td>
    </tr> 
    <tr>
    <td valign="bottom">  
        <!-- Bottom button bar -->
	    <div class="popupButtonBar">
	        <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="closePopup();"/>
	    </div>
    </td>
    </tr>
    </table>
    </body>
</html>