 <%@ include file="../jsp/tags.jsp" %>
 <%@ include file="/jsp/errors.jsp" %>
 	<%@page import= "java.util.*"%>
 <%@ page isELIgnored ="false" %>
 <html lang="en">
 <head>
 <title>Rule Administration </title>
 <%@ include file="../jsp/resources.jsp" %>
 <% int j=0;
	    ArrayList frmcodeList = new ArrayList();
		if(request.getAttribute("formcodeList") != null){         
            frmcodeList = (ArrayList)request.getAttribute("formcodeList");
           }

		
 %>
 <SCRIPT LANGUAGE="JavaScript">
 			
 		function searchRule() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=searchRule";
 			    document.forms[0].submit();
 		}	
 		
 		function addRuleIns() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=addRuleInstance";
 			    document.forms[0].submit();
 		}	
 		
 		function addSourceField() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=addSourceField";
 			    document.forms[0].submit();
 		}
 		
 		function addTargetField() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=addTargetField";
 			    document.forms[0].submit();
 		}
 		
 		function createRuleInstance() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=createRuleInstance";
 			    document.forms[0].submit();
 		}
 		
 		function createSrcTarF() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=createSrcTarF";
 			    document.forms[0].submit();
 		}	
 		
 		function addSourceValue() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=addSourceValue";
 			    document.forms[0].submit();
 		}
 		
 		function addTargetValue() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=addTargetValue";
 			    document.forms[0].submit();
 		}

		function updateSourceValue() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=updateSourceValue";
 			    document.forms[0].submit();
 		}

		function updateTargetValue() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=updateTargetValue";
 			    document.forms[0].submit();
 		}

		function addFormCode() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=addFormCode";
 			    document.forms[0].submit();
 		}

		function associateFormCode() {

 				document.forms[0].action ="/nbs/RuleAdmin.do?method=associateFormCode";
 			    document.forms[0].submit();
 		}
		
		function cancelForm(){
			var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
			if (confirm(confirmMsg)) {
			
				document.forms[0].action ="${RuleAdminForm.attributeMap.cancel}";
				document.forms[0].submit();
			} else {
				return false;
			}
	}	

		
 	      	
 </SCRIPT>      	
 </head> 
  <body   onload="autocompTxtValuesForJSP();">
   <table role="presentation">
		<tr><td style="WIDTH: 15px;"></td>
				<td class="none" id="error"></td>
		</tr>
		</table>
   <table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width: 750px;">
 <!-- Page starts below -->

 <tr>
       <td align="center" width="750"> 				
 <html:form action="/RuleAdmin.do">		
       <fieldset id="rulehome">
 	       <legend class="boldTenDkBlue">Rule List</legend>
    		    <table role="presentation" align="left" width="100%">
    		    <tr><td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(Select a Rule to continue)</i></td></tr>	
 			      	<tr>
		              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
		              <font class="boldTenRed" > * </font><span>Rules:</span></td>
		              <td class="InputField">
		                <html:select  property="searchCriteria(RULE)"   styleId="srchRule">
		                  <html:optionsCollection property="ruleList" value="key" label="value"/>
		                </html:select>              
		              </td>
					  <td> &nbsp; </td>
            	  </tr> 
            	  <tr>
		              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
		              <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
		              <td class="InputField">
		                <html:select  property="searchCriteria(PAMQ)"    styleId="srchPamQ">
		                  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
		                </html:select>  
		                </td>
		             </tr>  
				<tr>
					<td align="right"  class="InputFieldLabel">
					<input type="button" name="submit12" id="submit12" value="Search Rule Instances" onClick="searchRule();"/>
					</td>
		             	  </tr>              	  
        	</table>
        </fieldset>

         <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWRULES">  
              
	       <fieldset id="result">
	       	<legend class="boldTenDkBlue">Search Results</legend>
	       	<table role="presentation" width="732">
			     <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                         <display:column property="ruleInstanceUid" title="Rule InstanceUid" class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>
				         <display:column property="previewLink" title="<p style='display:none'>Preview</p" class="dstag"/>
						 <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>			       
			             <display:column property="deleteLink" title="<p style='display:none'>Delete</p" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 
				   </logic:notEmpty>	
				  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULT">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Rule Instance  exists for the Selected Rule . 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   </logic:notEmpty>
				  
				  <tr>
			      	<td class="InputButton" align="right">
			      		<input type="button" name="submit1" id="submit1" value="Add New Rule Instance" onClick="addRuleIns();"/>
			      	</td>
			      </tr>

	       	</table>
	      </fieldset>
		
		</logic:equal>	

		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWRULEINSTANCE">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="732">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				   <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
					    <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>
			             
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			            </display:table>
						 
			        </td>
			      </tr>
				    </logic:notEmpty>	
				  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTSF">
				   <tr>
				     <td align="center" class="InputLabel" width="100%"><b>
			        	<font color="brown">					
							No Source Field exists for the Selected Rule Instance. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   
				   </logic:notEmpty>
				   </table>
				  <table role="presentation" width="100%">
				  <tr>
				         <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
						  <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
						  <td class="InputField">
							<html:select  property="searchCriteria(PAMSFQ)"    styleId="srchSFPamQ">
							  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
							</html:select>              
						  </td>
				     </tr>  			     
					  <tr>
							<td> &nbsp; </td>
							<td class="InputField" align="right">	    										
								  <input type="button" name="submit19" id="submit19" value="Add New Source Field" onClick="return addSourceField();"/>	  
								  &nbsp;							    			
								</td>	  	
							</tr> 	  
					   <tr>				    
				   </tr>
				   </table>
                     <table role="presentation" width="100%">
                   <logic:notEmpty name="RuleAdminForm" property="tarFieldList"> 
				
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageTFList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
					    <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>
			           
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    </logic:notEmpty>
					  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTTF">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Target Field exists for the Selected Rule Instance. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				 
				   </logic:notEmpty>
				     </table>
				   <table role="presentation" width="100%">
				   <tr>
                      <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
		              <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
		              <td class="InputField">
		                <html:select  property="searchCriteria(PAMTFQ)"   styleId="srchTFPamQ">
		                  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
		                </html:select>              
		              </td>
			     </tr>  			     
			      <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">	    					
	    					 	
	    					  <input type="button" name="submit21" id="submit21" value="Add New Target Field" onClick="return addTargetField();"/>		  
	    					  &nbsp;		
							 
							</td>	  	
					  	</tr> 				 
				   </table>
				   <table role="presentation" width="100%">
				    <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageFrmList" class="its" pagesize="10"  id="parent" requestURI="">
                            <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
					        <display:column property="formCode" title="formCode" class="dstag"/>	             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 	
				  <tr>				  		
				  		<td class="InputField" align="right">	      						
							   <input type="button" name="submit22" id="submit22" value="AssociateFormCode" onClick="return addFormCode();"/>		  
	    					  &nbsp;	
							</td>	  	
					  	</tr> 		
				  </table>

				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit2" id="submit2" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>		       
	      </fieldset>

		</logic:equal>	

		 <logic:equal name="RuleAdminForm" property="actionMode" value="ADDFORMCODE">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="732">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				   <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
					    <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>
			             
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			            </display:table>
						 
			        </td>
			      </tr>
				    </logic:notEmpty>	
				  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTSF">
				   <tr>
				     <td align="center" class="InputLabel" width="100%"><b>
			        	<font color="brown">					
							No Source Field exists for the Selected Rule Instance. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   
				   </logic:notEmpty>
				   </table>			  
                  
                   <logic:notEmpty name="RuleAdminForm" property="tarFieldList"> 
				   <table role="presentation" width="100%">
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageTFList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
					    <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>      
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    </logic:notEmpty>
					  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTTF">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Target Field exists for the Selected Rule Instance. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   </table>
				   </logic:notEmpty>
				   <table role="presentation" width="100%">
				    <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageFrmList" class="its" pagesize="10"  id="parent" requestURI="">
                            <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
					        <display:column property="formCode" title="formCode" class="dstag"/>	             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 	
				  </table>
				   <table role="presentation" width="100%" >
                   
					 <% if(frmcodeList != null) { %>
					   <tr>
					 <td align="center" class="InputFieldLabel" style="WIDTH: 300 px;">
									Form Code :          
							   </td>
                      <td align="left" colspan="3"  class="InputField"> <SELECT NAME="cmbFrmCode" multiple>
						 <%   for(j=0;j<frmcodeList.size();j++) {  %>
						 <OPTION VALUE=<%=frmcodeList.get(j).toString()%>><%=frmcodeList.get(j).toString() %> </OPTION>
						  <% } %>
						 </td></tr>
						
						
						  <tr><td colspan=4 align="right"> <input type="button" name="submit2" id="submit2" value="Associate Form Code" onClick="return associateFormCode();"/>			  
	    					  &nbsp;</td></tr><%} else {%>
                           <tr> <td colspan=4 align="center" class="InputFieldLabel" >
						               Rule Instance cannot be associated to any existing form codes.
									   </td></tr> <% }%>
									   </table>
				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit2" id="submit2" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>		       
	      </fieldset>

		</logic:equal>	

		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWFORMCODE">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="732">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				   <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
					    <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>
			             
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			            </display:table>
						 
			        </td>
			      </tr>
				    </logic:notEmpty>	
				  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTSF">
				   <tr>
				     <td align="center" class="InputLabel" width="100%"><b>
			        	<font color="brown">					
							No Source Field exists for the Selected Rule Instance. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   
				   </logic:notEmpty>
				   </table>			  
                  
                   <logic:notEmpty name="RuleAdminForm" property="tarFieldList"> 
				   <table role="presentation" width="100%">
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageTFList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
					    <display:column property="viewLink" title="<p style='display:none'>View</p" class="dstag"/>      
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    </logic:notEmpty>
					  <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTTF">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Target Field exists for the Selected Rule Instance. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   </table>
				   </logic:notEmpty>
				   <table role="presentation" width="100%">
                   
					 <tr>
			        <td align="center" width="100%">
			        	<display:table name="frmCodes" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
					       <display:column property="formCode" title="formCode" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
					  </table>
				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit2" id="submit2" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>		       
	      </fieldset>

		</logic:equal>	
		
		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWSF">  
		         
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Source  Field</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>	             
						  <display:column property="deleteLink" title="Delete" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				  <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceValueUid" title="Source Value Uid" class="dstag"/>
						 <display:column property="sourceValue" title="Source Value" class="dstag"/>
					    <display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
			       </logic:notEmpty>		 
				    <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTSV">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Source Value exists for the Selected Source Field. 
	                    </font>
	                    </b>
				   </td>
				   </tr>
				   </logic:notEmpty>
				   </table>

				     <%if(request.getAttribute("manageSFDT") != null) { %>
				   <table role="presentation" width="100%">
				    <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value</span></td>
								 <td class="InputField" >
							   <html:text styleId="srcValue" property="searchCriteria(SRCSFVAL)" size="50" maxlength="50"/>  
							   </td>
							   </tr>
							   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							  <font class="boldTenRed" > * </font><span>Operator Type:</span></td>
							  <td class="InputField">
								<html:select  property="searchCriteria(opSFType)"    styleId="operType">
								  <html:optionsCollection property="operatorTypes" value="key" label="value"/>
								</html:select>              
							  </td>
							 </tr> 
							 <tr>
							<td> &nbsp; </td>
							<td class="InputField" align="right">	    					
									
								  <input type="button" name="submit11" id="submit11" value="Add New Source Value" onClick="return addSourceValue();"/>		  
								  &nbsp;							    			
								</td>	  	
							</tr> 
						</table>
						<% } %>
				   <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit3" id="submit3" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>	       
	      </fieldset>
		
		</logic:equal>

		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWNEWSRCVAL">  
		         
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Source  Field</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>	             
						  <display:column property="deleteLink" title="delete" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				  <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceValueUid" title="Source Value Uid" class="dstag"/>
						 <display:column property="sourceValue" title="Source Value" class="dstag"/>
					    <display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
			       </logic:notEmpty>		 
				    <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTSV">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Source Value exists for the Selected Source Field. 
	                    </font>
	                    </b>
				   </td>
				   </tr>
				   </logic:notEmpty>
				   </table>
				   <table role="presentation" width="100%">
				     <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value UID </span></td>
								 <td class="InputField" >									 	
							   <nedss:view name="RuleAdminForm" property="selection.sourceValueUid"/>
							   </td>
							   </tr>
						   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value</span></td>
								 <td class="InputField" >										 
							   <nedss:view name="RuleAdminForm" property="selection.sourceValue"/>
							   </td>
							   </tr>
							   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							  <font class="boldTenRed" > * </font><span>Operator Type:</span></td>
							  <td class="InputField">
								  <nedss:view name="RuleAdminForm" property="selection.operatorTypeDesc"/>            
							  </td>
						 </tr>  
						</table>
						
				   <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit3" id="submit3" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>	       
	      </fieldset>
		
		</logic:equal>
		
		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWTF">  
		       
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Target  Field</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                        <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>    
						  <display:column property="deleteLink" title="delete" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetValueUid" title="Target Value Uid" class="dstag"/>
						 <display:column property="targetValue" title="Target Value" class="dstag"/>
					     <display:column property="error_cd" title="Error Message" class="dstag"/>
					     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					      <display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
			       </logic:notEmpty>	
				   <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTTV">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Target Value exists for the Selected Target Field. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   </logic:notEmpty>
				   </table>

				  <% if(request.getAttribute("manageTFDT") != null){ %>
				   <table role="presentation" width="100%">
				   <tr>
			              <td class="InputFieldLabel" style="WIDTH: 150px;" id="tValue">
			              <span>Target Value</span></td>
              				 <td class="InputField" >
	         	           <html:text styleId="tarValue" property="searchCriteria(TARVAL)" size="50" maxlength="50"/>
	         	           </td>
	         	           </tr>
	         	           <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Error Message</span></td>
				              <td class="InputField">
				                <html:select  property="searchCriteria(errMessage)"    styleId="errorMessage">
				                  <html:optionsCollection property="errorMessages" value="key" label="value"/>
				                </html:select>              
				              </td>
            	          </tr> 
            	          <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Conseq Ind</span></td>
				              <td class="InputField">
				                <html:select  property="searchCriteria(conseqTVInd)"    styleId="conseqtvInd">
				                  <html:optionsCollection property="conseqInd" value="key" label="value"/>
				                </html:select>              
				              </td>
            	         </tr> 
            	         <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							 <span>Operator Type:</span></td>
							  <td class="InputField">
								<html:select  property="searchCriteria(opTFType)"    styleId="operTFType">
								  <html:optionsCollection property="operatorTypes" value="key" label="value"/>
								</html:select>              
							  </td>
							 </tr> 
            	          <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">	    					
	    					 	
	    					  <input type="button" name="submit12" id="submit12" value="Add Target Value" onClick="return addTargetValue();"/>		  
	    					  &nbsp;							    			
							</td>	  	
					  	</tr>  
						 </table>
						 <% } %>

				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit4" id="submit4" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>
	    
	      </fieldset>
		
		</logic:equal>	

			 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWNEWTARVAL">  
		       
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Target  Field</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                        <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>    
						  <display:column property="deleteLink" title="Delete" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetValueUid" title="Target Value Uid" class="dstag"/>
						 <display:column property="targetValue" title="Target Value" class="dstag"/>
					     <display:column property="error_cd" title="Error Message" class="dstag"/>
					     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					      <display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
			       </logic:notEmpty>	
				   <logic:notEmpty name="RuleAdminForm" property="attributeMap.NORESULTTV">
				   <tr>
				     <td align="center" class="InputLabel"><b>
			        	<font color="brown">					
							No Target Value exists for the Selected Target Field. 
	                    </font>
	                    </b>

				   </td>
				   </tr>
				   </logic:notEmpty>
				   </table>
				   <table role="presentation" width="100%">
				<tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Target Value UID </span></td>
								 <td class="InputField" >									 	
							   <nedss:view name="RuleAdminForm" property="selection.targetValueUid"/>
							   </td>
							   </tr>
				      <tr>
			              <td class="InputFieldLabel" style="WIDTH: 150px;" id="tValue">
			              <span>Target Value</span></td>
              				 <td class="InputField" >	         	         
						    <nedss:view name="RuleAdminForm" property="selection.targetValue"/>
	         	           </td>
	         	           </tr>
	         	           <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Error Message</span></td>
				              <td class="InputField">
				               <nedss:view name="RuleAdminForm" property="selection.error_cd"/>   
				              </td>
            	          </tr> 
            	          <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Conseq Ind</span></td>
				              <td class="InputField">
				                <nedss:view name="RuleAdminForm" property="selection.conseqIndtxt"/> 
				              </td>
            	         </tr> 
            	         <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Operator Type</span></td>
				              <td class="InputField">
				                <nedss:view name="RuleAdminForm" property="selection.operatorTypeDesc"/> 
				              </td>
            	         </tr> 
						 </table>

				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit4" id="submit4" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>
	    
	      </fieldset>
		
		</logic:equal>	


		 <logic:equal name="RuleAdminForm" property="actionMode" value="EDITSV">  
		         
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">Edit  Source  Value</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>	             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				 
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageSVList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceValueUid" title="Source Value Uid" class="dstag"/>
						 <display:column property="sourceValue" title="Source Value" class="dstag"/>
					    <display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
				</table>
				<table role="presentation" width="100%">
				          <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value UID </span></td>
								 <td class="InputField" >									 	
							   <nedss:view name="RuleAdminForm" property="selection.sourceValueUid"/>
							   </td>
							   </tr>
						   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value</span></td>
								 <td class="InputField" >		
								 	<html:text property="selection.sourceValue" size="50" maxlength="50"/>  							  
							   </td>
							   </tr>
							   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							  <font class="boldTenRed" > * </font><span>Operator Type:</span></td>
							  <td class="InputField">
								<html:select  property="selection.operatorTypeUid"    styleId="operTypeSV">
								  <html:optionsCollection property="operatorTypes" value="key" label="value"/>
								</html:select>              
							  </td>
						 </tr> 
						 </table>
				    
				  <table role="presentation" width="100%">
				  	<tr>
				  		<td class="InputField" align="right" width="100%">
	    					     <input type="button" name="submit13" id="submit13" value="Edit" onClick="return updateSourceValue();"/>		
	    					  <input type="button" name="submit13" id="submit13" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>
				      
	      </fieldset>
		
		</logic:equal>

		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWUPDATEDSV">  
		         
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View  Source  Value</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceFieldUid" title="Source Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>	 
						 <display:column property="deleteLink" title="Delete" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				 
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageSVList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="sourceValueUid" title="Source Value Uid" class="dstag"/>
						 <display:column property="sourceValue" title="Source Value" class="dstag"/>
					    <display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
				</table>
				<table role="presentation" width="100%">
				          <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value UID </span></td>
								 <td class="InputField" >									 	
							   <nedss:view name="RuleAdminForm" property="selection.sourceValueUid"/>
							   </td>
							   </tr>
						   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value</span></td>
								 <td class="InputField" >										 
							   <nedss:view name="RuleAdminForm" property="selection.sourceValue"/>
							   </td>
							   </tr>
							   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							  <font class="boldTenRed" > * </font><span>Operator Type:</span></td>
							  <td class="InputField">
								  <nedss:view name="RuleAdminForm" property="selection.operatorTypeDesc"/>            
							  </td>
						 </tr> 
						 </table>
				    
				  <table role="presentation" width="100%">
				  	<tr>
				  		<td class="InputField" align="right" width="100%">
	    					   <input type="button" name="submit13" id="submit13" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>
				 

	     
	      </fieldset>
		
		</logic:equal>


		 <logic:equal name="RuleAdminForm" property="actionMode" value="EDITTV">  
		       
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">Edit Target  Field</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                        <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetValueUid" title="Target Value Uid" class="dstag"/>
						 <display:column property="targetValue" title="Target Value" class="dstag"/>
					     <display:column property="error_cd" title="Error Message" class="dstag"/>
					     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
				  </table>
			       </logic:notEmpty>	
				     <table role="presentation" width="100%">
					 <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Target Value UID </span></td>
								 <td class="InputField" >									 	
							   <nedss:view name="RuleAdminForm" property="selection.targetValueUid"/>
							   </td>
							   </tr>
				      <tr>
			              <td class="InputFieldLabel" style="WIDTH: 150px;" id="tValue">
			              <span>Target Value</span></td>
              				 <td class="InputField" >
	         	           <html:text styleId="tarValue" property="selection.targetValue" size="50" maxlength="50"/>
	         	           </td>
	         	           </tr>
	         	           <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Error Message</span></td>
				              <td class="InputField">
				                <html:select  property="selection.errormessageUid"    styleId="errorMessage">
				                  <html:optionsCollection property="errorMessages" value="key" label="value"/>
				                </html:select>              
				              </td>
            	          </tr> 
            	          <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Conseq Ind</span></td>
				              <td class="InputField">
				                <html:select  property="selection.conseqIndUid"    styleId="conseqtvInd">
				                  <html:optionsCollection property="conseqInd" value="key" label="value"/>
				                </html:select>              
				              </td>
            	         </tr> 
            	   </table>
				 
				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					  <input type="button" name="submit15" id="submit15" value="Edit" onClick="return updateTargetValue();"/>	
	    					  <input type="button" name="submit14" id="submit14" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>
				 

	       
	      </fieldset>
		
		</logic:equal>	

		 <logic:equal name="RuleAdminForm" property="actionMode" value="VIEWUPDATEDTV">  
		       
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Target  Field</legend>
	       	<table role="presentation" width="732">
	       	      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageRList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                        <display:column property="targetFieldUid" title="Target Field Uid" class="dstag"/>
						 <display:column property="questionLabel" title="Question Label" class="dstag"/>
					    <display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>     
						 <display:column property="deleteLink" title="Delete" class="dstag"/>
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
				    <logic:notEmpty name="RuleAdminForm" property="manageList">  
			      <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageTVList" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="targetValueUid" title="Target Value Uid" class="dstag"/>
						 <display:column property="targetValue" title="Target Value" class="dstag"/>
					     <display:column property="error_cd" title="Error Message" class="dstag"/>
					     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					    <display:column property="editLink" title="<p style='display:none'>Edit</p" class="dstag"/>
				           
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr>	
				  </table>
			       </logic:notEmpty>	
				     <table role="presentation" width="100%">
					 <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Target Value UID </span></td>
								 <td class="InputField" >									 	
							   <nedss:view name="RuleAdminForm" property="selection.targetValueUid"/>
							   </td>
							   </tr>
				      <tr>
			              <td class="InputFieldLabel" style="WIDTH: 150px;" id="tValue">
			              <span>Target Value</span></td>
              				 <td class="InputField" >	         	         
						    <nedss:view name="RuleAdminForm" property="selection.targetValue"/>
	         	           </td>
	         	           </tr>
	         	           <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Error Message</span></td>
				              <td class="InputField">
				               <nedss:view name="RuleAdminForm" property="selection.error_cd"/>   
				              </td>
            	          </tr> 
            	          <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Conseq Ind</span></td>
				              <td class="InputField">
				                <nedss:view name="RuleAdminForm" property="selection.conseqIndtxt"/> 
				              </td>
            	         </tr> 
            	   </table>
				 
				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					 
	    					  <input type="button" name="submit16" id="submit16" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>
				 

	       	
	      </fieldset>
		
		</logic:equal>	
		
		<logic:equal name="RuleAdminForm" property="actionMode" value="ADDRULEINSTANCE">  
              
	       <fieldset id="result">
	       	<legend class="boldTenDkBlue">Add Rule Instance</legend>
	       	<table role="presentation" align="left" width="100%">
            <tr>
              <td class="InputFieldLabel" style="WIDTH: 150px;" >
               <span>Rule Name:</span></td>
			        <td class="InputField">
			        	<nedss:view name="RuleAdminForm" property="selection.ruleName"/>
			        </td>
			  </tr> 
			      <tr>
			           <td class="InputFieldLabel" style="WIDTH: 150px;" >
                        <span>Conseq Indicator:</span></td>
                        
			        	<td class="InputField">
		                <html:select  property="searchCriteria(CONSEQIND)" styleId="ConseqIndID" >
		                  <html:optionsCollection property="conseqInd" value="key" label="value"/>
		                </html:select>              
		                 </td>	    
			       
			      </tr> 
			    
			      <tr>
			          <td class="InputFieldLabel" style="WIDTH: 150px;" >
                        <span>Comments:</span></td>
			         <td class="InputField">
			        	<html:text property="selection.comments"  styleId="rcomments" size="60" maxlength="255"/>
			        </td>
			      </tr> 
			      <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">
	    					
	    					  <input type="button" name="submit5" id="submit5" value="Submit" onClick="return createRuleInstance();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  	  
				  

	       	</table>
	      </fieldset>
	      </logic:equal>	
	      <logic:equal name="RuleAdminForm" property="actionMode" value="ADDSRCTARS">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="100%">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 
			           <tr>				  		
				  		<td class="InputField" align="right">	    					
	    					 <input type="button" name="submit8" id="submit8" value="Add New Rule Instance" onClick="addRuleIns();"/>	    					  
	    					  &nbsp;							    			
							</td>	  	
					  	</tr> 
			 </table>
			 </fieldset> 
			 <fieldset id="sf">
	       	<legend class="boldTenDkBlue">Add Source Field</legend>
			 <table role="presentation" align="left" width="100%">
            <tr>
               <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
		              <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
		              <td class="InputField">
		                <html:select  property="searchCriteria(PAMSFQ)"    styleId="srchSFPamQ">
		                  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
		                </html:select>              
		              </td>
			  </tr>  			     
			      <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">	    					
	    					 	
	    					  <input type="button" name="submit7" id="submit7" value="Add Source Field" onClick="return addSourceField();"/>		  
	    					  &nbsp;							    			
							</td>	  	
					  	</tr> 	  

	         	</table>
	         	 </fieldset> 
			  <fieldset id="tf">
	       	<legend class="boldTenDkBlue">Add Target Field</legend>
			 <table role="presentation" align="left" width="100%">
            <tr>
               <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
		              <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
		              <td class="InputField">
		                <html:select  property="searchCriteria(PAMTFQ)"   styleId="srchTFPamQ">
		                  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
		                </html:select>              
		              </td>
			  </tr>  			     
			      <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">	    					
	    					 	
	    					  <input type="button" name="submit7" id="submit7" value="Add Target Field" onClick="return addTargetField();"/>		  
	    					  &nbsp;							    			
							</td>	  	
					  	</tr> 	  

	         	</table>
				
	         	 </fieldset> 	    
		
		</logic:equal>	
		
		 <logic:equal name="RuleAdminForm" property="actionMode" value="ADDSRCTARVALUES">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="732">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			        </tr>
			        </table>
					</fieldset>

			         <fieldset id="rule">
			       	<legend class="boldTenDkBlue">View Source Field/Values</legend>
			       
					    <%if(request.getAttribute("manageListSF") != null){ %>  
							<table role="presentation" width="732">
							<tr>
							<td align="center" width="100%">
								<display:table name="manageListSF" class="its" pagesize="10"  id="parent" requestURI="">
								  <display:column property="sourceFieldUid" title="Source Field Uid"  class="dstag"/>
								 <display:column property="questionLabel" title="Question Label" class="dstag"/>
								<display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>		             
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
							</tr>
							 </table>
			        <%} %>
				           <table role="presentation" width="100%">
								<tr>
								   <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
										  <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
										  <td class="InputField">
											<html:select  property="searchCriteria(PAMSFQ)"    styleId="srchSFPamQ">
											  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
											</html:select>              
										  </td>
								  </tr>  			     
								  <tr>
							  		<td> &nbsp; </td>
							  		<td class="InputField" align="right">	    					
				    					 	
				    					  <input type="button" name="submit7" id="submit7" value="Add Source Field" onClick="return addSourceField();"/>		  
				    					  &nbsp;							    			
										</td>	  	
								  	</tr> 	  
			               </table>
			      
			        <%if(request.getAttribute("manageListSV") != null){ %>  
					<table role="presentation" width="100%">
							<tr>
							  <td align="center" width="100%">
								<display:table name="manageListSV" class="its" pagesize="10"  id="parent" requestURI="">
                                <display:column property="sourceFieldUid" title="Source Field Uid"  class="dstag"/>
								  <display:column property="sourceValueUid" title="Source Value Uid"  class="dstag"/>
								 <display:column property="sourceValue" title="Source Value" class="dstag"/>
								<display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
						  </tr> 
						   </table>
				      <%}%>
					 
					   <table role="presentation" width="100%">
							 <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value</span></td>
								 <td class="InputField" >
							   <html:text styleId="srcValue" property="searchCriteria(SRCVAL)" size="50" maxlength="50"/>  
							   </td>
							   </tr>
							   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							  <font class="boldTenRed" > * </font><span>Operator Type:</span></td>
							  <td class="InputField">
								<html:select  property="searchCriteria(opType)"    styleId="operType">
								  <html:optionsCollection property="operatorTypes" value="key" label="value"/>
								</html:select>              
							  </td>
							 </tr> 
							 <tr>
							<td> &nbsp; </td>
							<td class="InputField" align="right">	    					
									
								  <input type="button" name="submit11" id="submit11" value="Add Source Value" onClick="return addSourceValue();"/>		  
								  &nbsp;							    			
								</td>	  	
							</tr> 
			        </table>
			        </fieldset>

			        <fieldset id="rule">
			       	<legend class="boldTenDkBlue">View Target Field/Values</legend>
			       
			        <%if(request.getAttribute("manageTFList") != null){ %>  
						<table role="presentation" width="732">
							<tr>
							  <td align="center" width="100%">
								<display:table name="manageTFList" class="its" pagesize="10"  id="parent" requestURI="">
								  <display:column property="targetFieldUid" title="Target Field Uid"  class="dstag"/>
								 <display:column property="questionLabel" title="Question Label" class="dstag"/>
								<display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
						  </tr> 
				    </table>
			      <%} %>
				       <table role="presentation" width="100%">
			               <tr>
			               <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
					              <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
					              <td class="InputField">
					                <html:select  property="searchCriteria(PAMTFQ)"   styleId="srchTFPamQ">
					                  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
					                </html:select>              
					              </td>
						  </tr>  			     
					      <tr>
						  		<td> &nbsp; </td>
						  		<td class="InputField" align="right">	    					
			    					 	
			    					  <input type="button" name="submit7" id="submit7" value="Add Target Field" onClick="return addTargetField();"/>		  
			    					  &nbsp;							    			
									</td>	  	
							  	</tr> 
			          </table>
			        
			         <%if(request.getAttribute("manageListTV") != null){ %>  
					<table role="presentation" width="100%">
				        <tr>
				          <td align="center" width="100%">
				        	<display:table name="manageListTV" class="its" pagesize="10"  id="parent" requestURI="">
							 <display:column property="targetFieldUid" title="Target Field Uid"  class="dstag"/>
	                          <display:column property="targetValueUid" title="Target Value Uid"  class="dstag"/>
							 <display:column property="targetValue" title="Target Value" class="dstag"/>
						     <display:column property="error_cd" title="Error Message Code" class="dstag"/>
						     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					          <display:setProperty name="basic.empty.showtable" value="true"/>
				          </display:table>
				        </td>
				      </tr> 
					    </table>
				      <%}%>
					
					  <table role="presentation" width="100%">
				      <tr>
			              <td class="InputFieldLabel" style="WIDTH: 150px;" id="tValue">
			              <span>Target Value</span></td>
              				 <td class="InputField" >
	         	           <html:text styleId="tarValue" property="searchCriteria(TARVAL)" size="50" maxlength="50"/>
	         	           </td>
	         	           </tr>
	         	           <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Error Message</span></td>
				              <td class="InputField">
				                <html:select  property="searchCriteria(errMessage)"    styleId="errorMessage">
				                  <html:optionsCollection property="errorMessages" value="key" label="value"/>
				                </html:select>              
				              </td>
            	          </tr> 
            	          <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Conseq Ind</span></td>
				              <td class="InputField">
				                <html:select  property="searchCriteria(conseqTVInd)"    styleId="conseqtvInd">
				                  <html:optionsCollection property="conseqInd" value="key" label="value"/>
				                </html:select>              
				              </td>
            	         </tr> 
            	          <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">	    					
	    					 	
	    					  <input type="button" name="submit12" id="submit12" value="Add Target Value" onClick="return addTargetValue();"/>		  
	    					  &nbsp;							    			
							</td>	  	
					  	</tr> 
				
			        </table>
					<table role="presentation" width="100%">					 
				    <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageFrmList" class="its" pagesize="10"  id="parent" requestURI="">
                            <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
					        <display:column property="formCode" title="formCode" class="dstag"/>	             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 					
				    <tr>				  		
				  		<td class="InputField" align="right">	      						
							   <input type="button" name="submit22" id="submit22" value="AssociateFormCode" onClick="return addFormCode();"/>		  
	    					  &nbsp;	
							</td>	  	
					  	</tr> 
						</table>
			        </fieldset>      			 		
		</logic:equal>	

			 <logic:equal name="RuleAdminForm" property="actionMode" value="ADDSRCTARFORMCODE">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="732">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			        </tr>
			        </table>
					</fieldset>

			         <fieldset id="rule">
			       	<legend class="boldTenDkBlue">View Source Field/Values</legend>
			       
					    <%if(request.getAttribute("manageListSF") != null){ %>  
							<table role="presentation" width="732">
							<tr>
							<td align="center" width="100%">
								<display:table name="manageListSF" class="its" pagesize="10"  id="parent" requestURI="">
								  <display:column property="sourceFieldUid" title="Source Field Uid"  class="dstag"/>
								 <display:column property="questionLabel" title="Question Label" class="dstag"/>
								<display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>		             
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
							</tr>
							 </table>
			        <%} %>
				        
			      
			        <%if(request.getAttribute("manageListSV") != null){ %>  
					<table role="presentation" width="100%">
							<tr>
							  <td align="center" width="100%">
								<display:table name="manageListSV" class="its" pagesize="10"  id="parent" requestURI="">
                                <display:column property="sourceFieldUid" title="Source Field Uid"  class="dstag"/>
								  <display:column property="sourceValueUid" title="Source Value Uid"  class="dstag"/>
								 <display:column property="sourceValue" title="Source Value" class="dstag"/>
								<display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
						  </tr> 
						   </table>
				      <%}%>				
					
			        </fieldset>

			        <fieldset id="rule">
			       	<legend class="boldTenDkBlue">View Target Field/Values</legend>
			       
			        <%if(request.getAttribute("manageTFList") != null){ %>  
						<table role="presentation" width="732">
							<tr>
							  <td align="center" width="100%">
								<display:table name="manageTFList" class="its" pagesize="10"  id="parent" requestURI="">
								  <display:column property="targetFieldUid" title="Target Field Uid"  class="dstag"/>
								 <display:column property="questionLabel" title="Question Label" class="dstag"/>
								<display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
						  </tr> 
				    </table>
			      <%} %>
				      
			        
			         <%if(request.getAttribute("manageListTV") != null){ %>  
					<table role="presentation" width="100%">
				        <tr>
				          <td align="center" width="100%">
				        	<display:table name="manageListTV" class="its" pagesize="10"  id="parent" requestURI="">
							 <display:column property="targetFieldUid" title="Target Field Uid"  class="dstag"/>
	                          <display:column property="targetValueUid" title="Target Value Uid"  class="dstag"/>
							 <display:column property="targetValue" title="Target Value" class="dstag"/>
						     <display:column property="error_cd" title="Error Message Code" class="dstag"/>
						     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					          <display:setProperty name="basic.empty.showtable" value="true"/>
				          </display:table>
				        </td>
				      </tr> 
					    </table>
				      <%}%>
					
				   <table role="presentation" width="100%">
				    <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageFrmList" class="its" pagesize="10"  id="parent" requestURI="">
                            <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
					        <display:column property="formCode" title="formCode" class="dstag"/>	             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 	
				  </table>
				   <table role="presentation" width="100%" >
                   
					 <% if(frmcodeList != null) { %>
					   <tr>
					 <td align="center" class="InputFieldLabel" style="WIDTH: 300 px;">
									Form Code :          
							   </td>
                      <td align="left" colspan="3"  class="InputField"> <SELECT NAME="cmbFrmCode" multiple>
						 <%   for(j=0;j<frmcodeList.size();j++) {  %>
						 <OPTION VALUE=<%=frmcodeList.get(j).toString()%>><%=frmcodeList.get(j).toString() %> </OPTION>
						 </td></tr>
						 <tr><td colspan=4 align="right"> <input type="button" name="submit2" id="submit2" value="Associate Form Code" onClick="return associateFormCode();"/>			  
	    					  &nbsp;</td></tr>
						 <% } } else {%>
                           <tr> <td colspan=4 align="center" class="InputFieldLabel" >
						               Rule Instance cannot be associated to any existing form codes.
									   </td></tr> <% }%>
					 </table>
				  <table role="presentation">
				  	<tr>
				  		<td class="InputField">
	    					
	    					  <input type="button" name="submit2" id="submit2" value="Cancel" onClick="return cancelForm();"/>			  
	    					  &nbsp;
							    			
							</td>	  	
					  	</tr>  
				  	</table>	
			        </fieldset> 
		     	</logic:equal>	

				<logic:equal name="RuleAdminForm" property="actionMode" value="VIEWSRCTARFORMCODE">  
		   
	       <fieldset id="rule">
	       	<legend class="boldTenDkBlue">View Rule Instance</legend>
	       	<table role="presentation" width="732">
		    	  <tr>
			        <td align="center" width="100%">
			        	<display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			        </tr>
			        </table>
					</fieldset>

			         <fieldset id="rule">
			       	<legend class="boldTenDkBlue">View Source Field/Values</legend>
			       
					    <%if(request.getAttribute("manageListSF") != null){ %>  
							<table role="presentation" width="732">
							<tr>
							<td align="center" width="100%">
								<display:table name="manageListSF" class="its" pagesize="10"  id="parent" requestURI="">
								  <display:column property="sourceFieldUid" title="Source Field Uid"  class="dstag"/>
								 <display:column property="questionLabel" title="Question Label" class="dstag"/>
								<display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>		             
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
							</tr>
							 </table>
			        <%} %>
				           <table role="presentation" width="100%">
								<tr>
								   <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
										  <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
										  <td class="InputField">
											<html:select  property="searchCriteria(PAMSFQ)"    styleId="srchSFPamQ">
											  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
											</html:select>              
										  </td>
								  </tr>  			     
								  <tr>
							  		<td> &nbsp; </td>
							  		<td class="InputField" align="right">	    					
				    					 	
				    					  <input type="button" name="submit7" id="submit7" value="Add Source Field" onClick="return addSourceField();"/>		  
				    					  &nbsp;							    			
										</td>	  	
								  	</tr> 	  
			               </table>
			      
			        <%if(request.getAttribute("manageListSV") != null){ %>  
					<table role="presentation" width="100%">
							<tr>
							  <td align="center" width="100%">
								<display:table name="manageListSV" class="its" pagesize="10"  id="parent" requestURI="">
                                <display:column property="sourceFieldUid" title="Source Field Uid"  class="dstag"/>
								  <display:column property="sourceValueUid" title="Source Value Uid"  class="dstag"/>
								 <display:column property="sourceValue" title="Source Value" class="dstag"/>
								<display:column property="operatorTypeDesc" title="Operator Type" class="dstag"/>
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
						  </tr> 
						   </table>
				      <%}%>
					 
					   <table role="presentation" width="100%">
							 <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150px;" id="sValue">
							  <span>Source Value</span></td>
								 <td class="InputField" >
							   <html:text styleId="srcValue" property="searchCriteria(SRCVAL)" size="50" maxlength="50"/>  
							   </td>
							   </tr>
							   <tr>
							  <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
							  <font class="boldTenRed" > * </font><span>Operator Type:</span></td>
							  <td class="InputField">
								<html:select  property="searchCriteria(opType)"    styleId="operType">
								  <html:optionsCollection property="operatorTypes" value="key" label="value"/>
								</html:select>              
							  </td>
							 </tr> 
							 <tr>
							<td> &nbsp; </td>
							<td class="InputField" align="right">	    					
									
								  <input type="button" name="submit11" id="submit11" value="Add Source Value" onClick="return addSourceValue();"/>		  
								  &nbsp;							    			
								</td>	  	
							</tr> 
			        </table>
			        </fieldset>

			        <fieldset id="rule">
			       	<legend class="boldTenDkBlue">View Target Field/Values</legend>
			       
			        <%if(request.getAttribute("manageTFList") != null){ %>  
						<table role="presentation" width="732">
							<tr>
							  <td align="center" width="100%">
								<display:table name="manageTFList" class="its" pagesize="10"  id="parent" requestURI="">
								  <display:column property="targetFieldUid" title="Target Field Uid"  class="dstag"/>
								 <display:column property="questionLabel" title="Question Label" class="dstag"/>
								<display:column property="questionIdentifier" title="Question Identifier" class="dstag"/>
								  <display:setProperty name="basic.empty.showtable" value="true"/>
							  </display:table>
							</td>
						  </tr> 
				    </table>
			      <%} %>
				       <table role="presentation" width="100%">
			               <tr>
			               <td class="InputFieldLabel" style="WIDTH: 150 px;" id="con">
					              <font class="boldTenRed" > * </font><span>NBS Questions:</span></td>
					              <td class="InputField">
					                <html:select  property="searchCriteria(PAMTFQ)"   styleId="srchTFPamQ">
					                  <html:optionsCollection property="PAMLabels" value="key" label="value"/>
					                </html:select>              
					              </td>
						  </tr>  			     
					      <tr>
						  		<td> &nbsp; </td>
						  		<td class="InputField" align="right">	    					
			    					 	
			    					  <input type="button" name="submit7" id="submit7" value="Add Target Field" onClick="return addTargetField();"/>		  
			    					  &nbsp;							    			
									</td>	  	
							  	</tr> 
			          </table>
			        
			         <%if(request.getAttribute("manageListTV") != null){ %>  
					<table role="presentation" width="100%">
				        <tr>
				          <td align="center" width="100%">
				        	<display:table name="manageListTV" class="its" pagesize="10"  id="parent" requestURI="">
							 <display:column property="targetFieldUid" title="Target Field Uid"  class="dstag"/>
	                          <display:column property="targetValueUid" title="Target Value Uid"  class="dstag"/>
							 <display:column property="targetValue" title="Target Value" class="dstag"/>
						     <display:column property="error_cd" title="Error Message Code" class="dstag"/>
						     <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
					          <display:setProperty name="basic.empty.showtable" value="true"/>
				          </display:table>
				        </td>
				      </tr> 
					    </table>
				      <%}%>
					
					  <table role="presentation" width="100%">
				      <tr>
			              <td class="InputFieldLabel" style="WIDTH: 150px;" id="tValue">
			              <span>Target Value</span></td>
              				 <td class="InputField" >
	         	           <html:text styleId="tarValue" property="searchCriteria(TARVAL)" size="50" maxlength="50"/>
	         	           </td>
	         	           </tr>
	         	           <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Error Message</span></td>
				              <td class="InputField">
				                <html:select  property="searchCriteria(errMessage)"    styleId="errorMessage">
				                  <html:optionsCollection property="errorMessages" value="key" label="value"/>
				                </html:select>              
				              </td>
            	          </tr> 
            	          <tr>
				              <td class="InputFieldLabel" style="WIDTH: 100 px;" id="con">
				              <span>Conseq Ind</span></td>
				              <td class="InputField">
				                <html:select  property="searchCriteria(conseqTVInd)"    styleId="conseqtvInd">
				                  <html:optionsCollection property="conseqInd" value="key" label="value"/>
				                </html:select>              
				              </td>
            	         </tr> 
            	          <tr>
				  		<td> &nbsp; </td>
				  		<td class="InputField" align="right">	    					
	    					 	
	    					  <input type="button" name="submit12" id="submit12" value="Add Target Value" onClick="return addTargetValue();"/>		  
	    					  &nbsp;							    			
							</td>	  	
					  	</tr> 
				
			        </table>
					<table role="presentation" width="100%">                   
					 <tr>
			        <td align="center" width="100%">
			        	<display:table name="frmCodes" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
					       <display:column property="formCode" title="formCode" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>
			        </td>
			      </tr> 		
					  </table>
			        </fieldset>      			 		
		</logic:equal>	

		
		<logic:equal name="RuleAdminForm" property="actionMode" value="PREVIEW">  
		    
           <%@ include file="PreviewRuleInstance.jsp" %> 
	       		
		</logic:equal>		
			
       
   </html:form>
   </td>
   </tr>
   </table>
   </body>

