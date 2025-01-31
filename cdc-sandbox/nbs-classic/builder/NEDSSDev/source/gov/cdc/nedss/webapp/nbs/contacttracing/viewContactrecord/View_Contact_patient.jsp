<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page isELIgnored ="false" %>

<tr> <td>
	<% 
	    int subSectionIndex = 0;
	    String tabId = "editPatient";
	    String []sectionNames = {"Contact Information"};
	    int sectionIndex = 0;
	%>
	<div class="view" id="<%= tabId %>" style="text-align:center;">    
	    <table role="presentation" class="sectionsToggler" style="width:100%;">
	        <tr>
	            <td>
	                <ul class="horizontalList">
	                    <li style="margin-right:5px;"><b>Go to: </b></li>
	                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
	                </ul>
	            </td>
	        </tr>
	        <tr>
	            <td style="padding-top:1em;">
	                <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
	            </td>
	        </tr>
	    </table>
	    <%
	        // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>
	
	    <!-- SECTION : Contact Information --> 
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect" includeBackToTopLink="no">
	        <!-- SUB_SECTION : General Information -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="General Information" classType="subSect" >
	            <!-- As of date - Create mode -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${contactTracingForm.formFieldMap.DEM215.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM215.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM215.tooltip}">
	                        ${contactTracingForm.formFieldMap.DEM215.label}
	                    </span> 
	                </td>
	                <td>
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM215)"/>   
	                </td>
	            </tr>
	            <!-- Comments -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM196.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM196.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM196.tooltip}">${contactTracingForm.formFieldMap.DEM196.label}</span> 
	                </td>
	                <td>  
	                	<nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM196)"/> 
	                </td>
	            </tr>
	        </nedss:container>
	        
	        <!-- SUB_SECTION : Name Information -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Name Information" classType="subSect">
	            <!-- Name Info as of date - Edit mode only -->
	            <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName"> 
	                        <span style="${contactTracingForm.formFieldMap.DEM206.state.requiredIndClass}">*</span>
	                        <span style="${contactTracingForm.formFieldMap.DEM206.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM206.tooltip}">${contactTracingForm.formFieldMap.DEM206.label}</span> 
	                    </td>
	                    <td> 
	                       <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM206)"/>
	                    </td>
	                </tr>
	              </logic:notEqual> 
	           </logic:notEqual>
	            <!-- First Name -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${contactTracingForm.formFieldMap.DEM104.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM104.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM104.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM104.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM104)"/>
	                </td>
	            </tr>
	            <!-- Middle Name -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM105.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM105.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM105.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM105.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM105)"/>
	                </td>
	            </tr>
	            <!-- Last Name -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM102.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM102.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM102.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM102.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM102)"/>
	                </td>
	            </tr>
	            <!-- Suffix -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM107.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM107.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM107.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM107.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM107)" codeSetNm="${contactTracingForm.formFieldMap.DEM107.codeSetNm}"/>
	                </td>
	            </tr>
	             <!-- Alias/Nickname -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM250.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM250.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM250.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM250.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM250)"/>
	                </td>
	            </tr>
	        </nedss:container>
	        
		    <!-- SUB_SECTION : Other Personal Details -->
		    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Other Personal Details" classType="subSect" >
		       <!-- Other details As of Date - Edit mode only -->
	            <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName"> 
	                        <span style="${contactTracingForm.formFieldMap.DEM207.state.requiredIndClass}">*</span>
	                        <span style="${contactTracingForm.formFieldMap.DEM207.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM207.tooltip}">
	                            ${contactTracingForm.formFieldMap.DEM207.label}
	                        </span>
	                    </td>
	                    <td>
	                        <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM207)"/>
	                    </td>
	                </tr>
	             </logic:notEqual> 
	           </logic:notEqual>
	            <!-- Date of Birth -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM115.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM115.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM115.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM115.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM115)"/>
	                </td>
	            </tr>
	            <!-- Reported Age -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM216.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM216.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM216.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM216.label} 
	                    </span>  
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM216)"/>
					    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM218)" codeSetNm="${contactTracingForm.formFieldMap.DEM218.codeSetNm}"/>					
	                </td>
	            </tr>
	           
	           
	            <!-- Current Sex -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM113.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM113.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM113.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM113.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM113)" codeSetNm="${contactTracingForm.formFieldMap.DEM113.codeSetNm}"/>
	                </td>
	            </tr>
	            <!-- Mortality Information As of Date - Edit mode only -->
	             <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName topBorder"> 
	                        <span style="${contactTracingForm.formFieldMap.DEM208.state.requiredIndClass}">*</span>
	                        <span style="${contactTracingForm.formFieldMap.DEM208.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM208.tooltip}">${contactTracingForm.formFieldMap.DEM208.label}</span>
	                    </td>
	                    <td class="topBorder">
	                       <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM208)" />
	                    </td>
	                </tr>
	            </logic:notEqual> 
	           </logic:notEqual>
	            <!-- Is the Patient Deceased -->
	            <tr>
	                <td class="fieldName" id="DEM127L"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM127.state.requiredIndClass}">*</span>
	                    <span   style="${contactTracingForm.formFieldMap.DEM127.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM127.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM127.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM127)" codeSetNm="${contactTracingForm.formFieldMap.DEM127.codeSetNm}"/>
	                </td>
	            </tr>
	            <!-- Deceased Date -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM128.state.requiredIndClass}">*</span>
	                    <span  id="DEM128L" style="${contactTracingForm.formFieldMap.DEM128.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM128.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM128.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM128)" />
	                </td>
	            </tr>
	            <!-- Marital Status as of date - Edit mode only -->
	             <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                 <tr>
	                    <td class="fieldName topBorder"> 
	                        <span style="${contactTracingForm.formFieldMap.DEM209.state.requiredIndClass}">*</span>
	                        <span style="${contactTracingForm.formFieldMap.DEM209.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM209.tooltip}">${contactTracingForm.formFieldMap.DEM209.label}</span>
	                    </td>
	                    <td class="topBorder">
	                        <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM209)" />
	                    </td>
	                </tr>
	           </logic:notEqual> 
	           </logic:notEqual>
	            <!-- Marital Status -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM140.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM140.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM140.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM140.label} 
	                    </span> 
	                </td>
	                <td>
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM140)" codeSetNm="${contactTracingForm.formFieldMap.DEM140.codeSetNm}"/>
	                </td>
	            </tr>
	            
	             <!-- Primary Occupation -->
	             <tr>
	                <td class="fieldName topBorder"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM139.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM139.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM139.tooltip}">${contactTracingForm.formFieldMap.DEM139.label}</span>
	                </td>
	                <td class="topBorder">
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM139)" codeSetNm="<%=NEDSSConstants.O_NAICS%>"/>
	                </td>
	            </tr>
	             <!-- Birth COuntry -->
	             <tr>
	                <td class="fieldName topBorder"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM126.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM126.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM126.tooltip}">${contactTracingForm.formFieldMap.DEM126.label}</span>
	                </td>
	                <td class="topBorder">
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM126)" codeSetNm="<%=NEDSSConstants.PSL_CNTRY%>"/>
	                </td>
	            </tr>
	            <!-- Primary Language -->
	             <tr>
	                <td class="fieldName topBorder"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM142.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM142.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM142.tooltip}">${contactTracingForm.formFieldMap.DEM142.label}</span>
	                </td>
	                <td class="topBorder">
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM142)" codeSetNm="<%=NEDSSConstants.P_LANG%>"/>
	                </td>
	            </tr>
		    </nedss:container>
		    
		    <!-- SUB_SECTION : 4. Reporting Address for Case Counting-->
		    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Address" classType="subSect" >
		       <!-- Contact Information As of date - Edit mode only -->
	            <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName topBorder">
	                         <span style="${contactTracingForm.formFieldMap.DEM213.state.requiredIndClass}">*</span>
	                         <span style="${contactTracingForm.formFieldMap.DEM213.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM213.tooltip}">${contactTracingForm.formFieldMap.DEM213.label}</span>
	                    </td>
	                    <td class="topBorder">
	                         <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM213)"/>
	                    </td>
	                </tr>
	         </logic:notEqual> 
	           </logic:notEqual>
	            <!-- Street Address 1 -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM159.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM159.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM159.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM159.label} 
	                    </span> 
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM159)"/>
	                </td>
	            </tr>
	            <!-- Street Address 2 -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM160.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM160.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM160.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM160.label} 
	                    </span>  
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM160)"/>
	                </td>
	            </tr>
	            <!-- City -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM161.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM161.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM161.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM161.label} 
	                    </span> 
	                </td>
	                <td> 
	                 <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM161)"/>
	                </td>
	            </tr>
	            <!-- State -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM162.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM162.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM162.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM162.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM162)" codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
	                </td>
	            </tr>
	            <!-- Zip -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM163.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM163.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM163.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM163.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM163)"/>
	                </td>
	            </tr>
	            <!-- County -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM165.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM165.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM165.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM165.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <logic:notEmpty name="contactTracingForm" property="cTContactClientVO.answerMap(DEM165)">
	                  	<logic:notEmpty name="contactTracingForm" property="cTContactClientVO.answerMap(DEM162)">
			                  <bean:define id="value" name="contactTracingForm" property="cTContactClientVO.answerMap(DEM162)"/>
			                  <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM165)" codeSetNm='<%=value.toString()+"county"%>'/>
			            </logic:notEmpty>
			          </logic:notEmpty>
	                </td>
	            </tr>
	            <!-- Country -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM167.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM167.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM167.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM167.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM167)" codeSetNm="<%=NEDSSConstants.COUNTRY_LIST%>"/>
	                </td>
	            </tr>
		    </nedss:container>
		    
		    <!-- SUB_SECTION : Telephone Information -->
		    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Telephone Information" classType="subSect" >
	            <!-- Telephone Information As of date - Edit mode only -->
	             <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName topBorder">
	                         <span style="${contactTracingForm.formFieldMap.DEM214.state.requiredIndClass}">*</span>
	                         <span style="${contactTracingForm.formFieldMap.DEM214.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM214.tooltip}">${contactTracingForm.formFieldMap.DEM214.label}</span>
	                    </td>
	                    <td class="topBorder">
	                        <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM214)"/>
	                    </td>
	                </tr>
	           </logic:notEqual> 
	           </logic:notEqual>
	            <!-- Home Phone -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM238.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM238.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM238.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM238.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM238)"/>
	                </td>
	            </tr>
	            
	            <!-- Work Phone -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM240.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM240.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM240.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM240.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM240)"/>
	                </td>
	            </tr>
	            <!-- Work Phone ext -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM241.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM241.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM241.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM241.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM241)"/>
	                </td>
	            </tr>	
	            <!-- Cell Phone -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM251.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM251.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM251.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM251.label} 
	                    </span> 
	                </td>
	                <td> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM251)"/>
	                </td>
	            </tr>  
	             <!-- Email -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM252.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM252.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM252.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM252.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM252)"/>
	                </td>
	            </tr>       
		    </nedss:container>
		    
		    <!-- SUB_SECTION : Ethnicity & Race Information -->
		    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Ethnicity & Race Information" classType="subSect" >
	            <!-- Ethnicity and Race Information As of date - Edit mode only -->
	           <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName topBorder">
	                         <span style="${contactTracingForm.formFieldMap.DEM211.state.requiredIndClass}">*</span>
	                         <span style="${contactTracingForm.formFieldMap.DEM211.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM211.tooltip}">${contactTracingForm.formFieldMap.DEM211.label}</span>
	                    </td>
	                    <td class="topBorder">
	                        <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM211)"/>
	                    </td>
	                </tr>
	           </logic:notEqual> 
	           </logic:notEqual>
	            
	            <!-- Ethnicity -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM155.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM155.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM155.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM155.label} 
	                    </span> 
	                </td>
	                <td> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM155)" codeSetNm="${contactTracingForm.formFieldMap.DEM155.codeSetNm}"/>
	                </td>
	            </tr>
	            <!-- Race -->
	        	<logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
				    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
	                <tr>
	                    <td class="fieldName topBorder">
	                         <span style="${contactTracingForm.formFieldMap.DEM212.state.requiredIndClass}">*</span>
	                         <span style="${contactTracingForm.formFieldMap.DEM212.errorStyleClass}" 
	                                title="${contactTracingForm.formFieldMap.DEM212.tooltip}">${contactTracingForm.formFieldMap.DEM212.label}</span>
	                    </td>
	                    <td class="topBorder">
	                        <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(DEM212)"/>
	                    </td>
	                </tr>
	               </logic:notEqual> 
	           </logic:notEqual>
	            <!-- Alaska & American Indian race -->
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.DEM152.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.DEM152.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.DEM152.label} 
	                    </span> 
	                </td>
	                <td>
	                    <!-- Note: The extra commas and surrounding white spaces (if any) are removed with the help of a 
	                        JS function, cleanupPatientRacesViewDisplay(), defined in the PamSpecific.js file -->   
	                   <div id="patientRacesViewContainer"> 
		                   <logic:equal name="contactTracingForm" property="cTContactClientVO.americanIndianAlskanRace" value="1">
		                        <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>
		                   
		                   <logic:equal name="contactTracingForm" property="cTContactClientVO.asianRace" value="1">
		                        <bean:message bundle="RVCT" key="rvct.asian"/><nedss:view name="contactTracingForm" property="cTContactClientVO.answerArray(DEM243)" 
		                                codeSetNm="2028-9" mutliSelectResultInParanthesis="yes"/>,</logic:equal> 
		                   
		                   <logic:equal name="contactTracingForm" property="cTContactClientVO.africanAmericanRace" value="1">
		                        <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>
		                   
		                   <logic:equal name="contactTracingForm" property="cTContactClientVO.hawaiianRace" value="1">
		                        <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/><nedss:view name="contactTracingForm" property="cTContactClientVO.answerArray(DEM245)" 
		                            codeSetNm="2076-8" mutliSelectResultInParanthesis="yes"/>,</logic:equal>
		                                      
		                   <logic:equal name="contactTracingForm" property="cTContactClientVO.whiteRace" value="1">
		                        <bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>
		                   
		                   <logic:equal name="contactTracingForm" property="cTContactClientVO.unKnownRace" value="1">
		                        <bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>
	                    </div>                        
	                </td>
	            </tr>
		    </nedss:container>
		</nedss:container>
		<div class="tabNavLinks">
            <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
            <a href="javascript:navigateTab('next')"> Next </a>
            <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
            <input type="hidden" name="endOfTab" />
    	</div>
		
	</div>
</td></tr>