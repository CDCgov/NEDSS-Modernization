<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:output method="xml" version="1.0" encoding="UTF-8"/>
    <xsl:param name="bus_obj" select="'INV'"/>
    <xsl:param name="mult_tab" select="'Y'"/>
    <xsl:param name="the_form" select="'PageForm'"/>
    <xsl:param name="the_prop" select="'pageClientVO'"/>
    <xsl:template match="PageTab" xml:space="preserve">
        <xsl:comment> ### DMB:BEGIN JSP PAGE GENERATE ###--</xsl:comment>
        <xsl:variable name="theBusObj" select="$bus_obj" />
        <xsl:variable name="theMultTab" select="$mult_tab" />
        <xsl:variable name="theForm" select="$the_form" />
        <xsl:variable name="theProp" select="$the_prop" />
        <xsl:variable name="guideVersion" select="TabDescription"/>
        
        <xsl:if test="$theBusObj='CON'">
			<xsl:comment>##Contract Record Business Object##</xsl:comment>
		</xsl:if>
	    <xsl:if test="$theBusObj='INV'">
			<xsl:comment>##Investigation Business Object##</xsl:comment>
	    </xsl:if>
       
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ page isELIgnored ="false" %>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="java.util.*" %>]]></xsl:text>
    	<xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>]]></xsl:text>
    	<xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl" %>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="javax.servlet.http.HttpServletRequest" %>]]></xsl:text>
        <!-- Tab container -->
        <!-- <layout:tabs width="100%" styleClass="tabsContainer"> -->
        <xsl:comment> ################### A PAGE TAB ###################### --</xsl:comment>
        <xsl:text disable-output-escaping="yes"><![CDATA[ 
           <%  
		     Map map = new HashMap();
             if(request.getAttribute("SubSecStructureMap") != null){
               map =(Map)request.getAttribute("SubSecStructureMap");
            }
		   %> 
          ]]> 
	   </xsl:text>      
     
           <!-- Set the Tab ID to edit{TabName} -->
		<xsl:text disable-output-escaping="yes"><![CDATA[<%            
     
     String tabId = "edit]]></xsl:text><xsl:value-of select="translate(TabName,' ','')"/><xsl:text disable-output-escaping="yes"><![CDATA[";
        tabId = tabId.replace("]","");
	tabId = tabId.replace("[","");
     	tabId = tabId.replaceAll(" ", "");
        int subSectionIndex = 0;
        int sectionIndex = 0;
    	String sectionId = "";     
    
         String [] sectionNames  = {]]></xsl:text><xsl:for-each select="descendant-or-self::*/SectionName"><xsl:text disable-output-escaping="yes">"</xsl:text><xsl:value-of select="."/><xsl:text disable-output-escaping="yes">"</xsl:text><xsl:if test="not(position() = last())"><xsl:text disable-output-escaping="yes">,</xsl:text></xsl:if></xsl:for-each><xsl:text disable-output-escaping="yes"><![CDATA[};]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[ ;

%> 	

<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;"> 
  
]]></xsl:text>
		<xsl:if test="count(Section)>1">
			<table role="presentation" class="sectionsToggler" style="width:100%;">
				<tr><td><ul class="horizontalList">
						<li style="margin-right:5px;"><b>Go to: </b></li>
			<xsl:for-each select="Section">
			<xsl:text disable-output-escaping="yes"><![CDATA[<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>]]></xsl:text>
				<xsl:if test="not(position() = last())"><li class="delimiter"> | </li></xsl:if>
			</xsl:for-each>	
			</ul> </td> </tr> 
			<tr>
			<td style="padding-top:1em;">
			 <xsl:text disable-output-escaping="yes"><![CDATA[<a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>]]></xsl:text>
			</td>
			</tr>
			</table>
		</xsl:if>
		 
		<xsl:text disable-output-escaping="yes"><![CDATA[<%  sectionIndex = 0; %> ]]></xsl:text>
		<xsl:for-each select="Section">
			<xsl:comment> ################# SECTION ################  </xsl:comment>

			<!-- Expand/Collapse Section Name -->
			<xsl:variable name="SectionVisible" select="Visible" />
			<xsl:choose>  
				<xsl:when test="$SectionVisible= 'T'">
					<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">]]></xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="T" classType="sect">]]></xsl:text>
				</xsl:otherwise>
			</xsl:choose>   
   
			<xsl:for-each select="SubSection">
				<xsl:comment> ########### SUB SECTION ###########  </xsl:comment>
				<xsl:variable name="SubSectionVisible" select="Visible" />
				<xsl:variable name="SectionAllowBatchEntry" select="AllowBatchEntry" />
				<xsl:variable name="SubSectionId" select="QuestionUniqueName" />
				<xsl:variable name="hasHIVQuestions" select="hasHIVQuestions"/>
				<xsl:choose>
					<xsl:when test="$SubSectionVisible= 'T'">
						<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:container id="]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" name="]]></xsl:text><xsl:value-of select="SubSectionName"/><xsl:text disable-output-escaping="yes"><![CDATA[" isHidden="F" classType="subSect" ]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[ addedClass="batchSubSection"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:container id="]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" name="]]></xsl:text><xsl:value-of select="SubSectionName"/><xsl:text disable-output-escaping="yes"><![CDATA[" isHidden="T" classType="subSect" ]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[ addedClass="batchSubSection"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
					</xsl:otherwise>
				</xsl:choose>
             
               
				<xsl:choose>  
					<xsl:when test="$SectionAllowBatchEntry= 'Y'"> 
						<xsl:if test="$hasHIVQuestions = 'Y'">
							<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="securityMap.hasHIVPermissions" value="T">]]></xsl:text>
						</xsl:if>     

						<xsl:text disable-output-escaping="yes"><![CDATA[  <tr> <td colspan="2" width="100%">
		<table role="presentation" width="100%"  border="0" align="center">
		<tr><td>.</td>
		 <td   width="100%"> 
		        <div class="infoBox errors" style="display: none;visibility: none;" id="]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[errorMessages">]]></xsl:text>
		 	<xsl:text disable-output-escaping="yes"><![CDATA[<b> <a name="]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>]]></xsl:text>
		 	<xsl:text disable-output-escaping="yes"><![CDATA[</div>
		 
        		
		<table role="presentation"  class="dtTable" align="center" >
		  <thead >
		    <tr> <%String subSecNm = "]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
		    while(mapIt .hasNext())
		    {
		    Map.Entry mappairs = (Map.Entry)mapIt .next();
		    if(mappairs.getKey().toString().equals(subSecNm)){
		    batchrec =(String[][]) mappairs.getValue();
		    break;
		    }
		    }%>
		    <%int wid =100/11; %>
		     <td style="background-color: #EFEFEF; border:1px solid #666666" width="9%" colspan="3"> &nbsp;</td>
		    <% for(int i=0;i<batchrec.length;i++){%>  
		       <%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%> 
		       <% String per = batchrec[i][4];
				int aInt = (Integer.parseInt(per)) *91/100;    
    					%>		                 
		                   <th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>   
		          <%} %>	   
		      <%} %>      
		     
		    </tr>
		  </thead>
		   <tbody id="questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
		      <tr id="pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="odd" style="display:none">
		      <td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
		      <td style="width:3%;text-align:center;">
			<input id="view]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">			
			<input id="edit]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit"> 			
</td><td style="width:3%;text-align:center;">
			<input id="delete]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
                         
		      
		          <% for(int i=0;i<batchrec.length;i++){%>  
		                  <% String validdisplay =batchrec[i][0]; %>     
		             <%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%> 
		              <% String per = batchrec[i][4];
				int aInt = (Integer.parseInt(per)) *91/100;    
    					%>	
		                      <td width="<%=aInt%>%" align="left">
			      	             <span id="table<%=batchrec[i][0]%>"> </span> 	      	            
			      	             </td>    
		             <%} %> 
		          <%} %>      
		    </tr>
		    </tbody> 
		     <tbody id="questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
		      <tr id="nopattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="odd" style="display:">
		    <td colspan="<%=batchrec.length+1%>"> <span>&nbsp; No Data has been entered.
		    </span>
		    </td>
		    </tr>
		   </tbody> 

		</table>
		 </td>
		 <td width="5%"> &nbsp; </td>
		</tr>
		 </Table>
		</td>
	  
]]></xsl:text>
		<xsl:if test="$hasHIVQuestions = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[</logic:equal>]]></xsl:text>
		   </xsl:if>
              </xsl:when> 
      </xsl:choose>
             
              <!-- #### ELEMENTS ###  -->
             <xsl:for-each select="PageElement">
             <xsl:variable name="isHIVElement" select="isHIVElement" />
             <xsl:if test="$isHIVElement = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="securityMap.hasHIVPermissions" value="T">]]></xsl:text>
			</xsl:if>
		<xsl:if test="TextQuestion">
			<xsl:variable name="thisReq" select="TextQuestion/Required" />
			<xsl:variable name="thisVisible" select="TextQuestion/Visible" />
			<xsl:variable name="thisDisabled" select="TextQuestion/Disabled" />
			<xsl:variable name="thisControl" select="TextQuestion/ControlType" />
			
	   		<xsl:choose>
			<xsl:when test="substring($thisControl,1,15) = 'ReadOnlyTextbox'">
						<xsl:choose>
							<xsl:when test="$thisVisible= 'F'">
								<xsl:comment>processing Hidden ReadOnly Textbox Text Question</xsl:comment>
								<tr style="display:none"><td class="fieldName"> 
                		<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                		<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
                		<xsl:value-of select="TextQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                		</td>
                		<td>
                		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S"/>]]></xsl:text>
                		<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" />]]></xsl:text>
                		</td> 
                		<xsl:if test="substring($thisControl,1,19) = 'ReadOnlyTextboxSave'"> 
		   		<td>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
		   		</td>
				</xsl:if> 
                
                		</tr>
							</xsl:when>
							<xsl:otherwise>
								<xsl:comment>processing ReadOnly Textbox Text Question</xsl:comment>
								<tr><td class="fieldName"> 
                		<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                		<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
                		<xsl:value-of select="TextQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                		</td>
                		<td>
                		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S"/>]]></xsl:text>
                		<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" />]]></xsl:text>
                		</td> 
                		<xsl:if test="substring($thisControl,1,19) = 'ReadOnlyTextboxSave'"> 
		   		<td>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
		   		</td>
				</xsl:if> 
                
                		</tr>
							</xsl:otherwise>
						</xsl:choose>
                		
         		</xsl:when>
			<xsl:otherwise>	 		
			<xsl:choose>
			    <xsl:when test="$thisVisible= 'F'">
				<xsl:comment>processing Hidden Text Question</xsl:comment>
	                	<tr style="display:none"> <td class="fieldName"> 
                		<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
                		<xsl:value-of select="TextQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                            	</td>
                            	<td>
                            	
                            	   <xsl:choose>  
                                                  <xsl:when test="$SectionAllowBatchEntry= 'Y'">
                                                   <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="TextQuestion/OnKeyUp"><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                                                  </xsl:when>
   			 		       	<xsl:otherwise>   			 		       	
        	     <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:if test="TextQuestion/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>		 		       	
   			 		       	</xsl:otherwise>
   						</xsl:choose>   			 		
             
			   	</td> </tr>			    
			    </xsl:when>			
			    <xsl:otherwise>    
				<xsl:comment>processing Text Question</xsl:comment>
				<tr> <td class="fieldName"> 
				<xsl:if test="$thisReq= 'T'">
				    <xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
				</xsl:if>
				<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
				<xsl:value-of select="TextQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    		</td>
                    		 <td>
				<xsl:choose>
				<xsl:when test="$thisReq= 'T'">
				   
                                            <xsl:choose>  
                                                  <xsl:when test="$SectionAllowBatchEntry= 'Y'">                                     

<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="TextQuestion/OnKeyUp"><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>

                                                  </xsl:when>
   			 		       	<xsl:otherwise>   			 		       	
        	    <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:if test="TextQuestion/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
		 		       	
   			 		       	</xsl:otherwise>
   						</xsl:choose>   			 	

				</xsl:when>
				<xsl:otherwise>
				    

   <xsl:choose>  
                                                  <xsl:when test="$SectionAllowBatchEntry= 'Y'">                      

   <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="TextQuestion/OnKeyUp"><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>	

                                                  </xsl:when>
   			 		       	<xsl:otherwise>   			 		       	
        	    <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:if test="TextQuestion/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>		 		       	
   			 		       	</xsl:otherwise>
   						</xsl:choose>   			 	


				</xsl:otherwise>
			        </xsl:choose> 
   				 </td> </tr>
   			    </xsl:otherwise>
			</xsl:choose>
			
   	</xsl:otherwise>
		</xsl:choose>				
		</xsl:if>
		<xsl:if test="CodedQuestion">
			<xsl:variable name="thisControl" select="CodedQuestion/ControlType" />
			<xsl:variable name="thisCodeSet" select="CodedQuestion/ValueSetName" />
			<xsl:variable name="thisReq" select="CodedQuestion/Required" />
			<xsl:variable name="thisDisabled" select="CodedQuestion/Disabled" />
			<xsl:variable name="thisVisible" select="CodedQuestion/Visible" />
			<xsl:choose>			
				<xsl:when test="substring($thisControl,1,11) = 'MultiSelect'">
					<xsl:choose>
					<xsl:when test="$thisVisible= 'F'">
						<xsl:comment>processing Hidden MultiSelect Question</xsl:comment>
						<tr style="display:none"><td class="fieldName"> 
						<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
						<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                     				</td>
                    				<td>
                    				<div class="multiSelectBlock">
                    				<i> (Use Ctrl to select more than one) </i> <br/>
					     	<xsl:text disable-output-escaping="yes"><![CDATA[<html:select property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>
					     	<xsl:text disable-output-escaping="yes"><![CDATA[multiple="true" size="4" ]]></xsl:text>
					 	<xsl:text disable-output-escaping="yes"><![CDATA[onchange="]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[displaySelectedOptions(this, ']]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[-selectedValues')]]></xsl:text><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[;]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" >]]></xsl:text>
                    			      	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>	

		 			 	<xsl:text disable-output-escaping="yes"><![CDATA[ <div id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[-selectedValues" style="margin:0.25em;">]]></xsl:text>
					   	 <b> Selected Values: </b>
                                        	<xsl:text disable-output-escaping="yes"><![CDATA[   </div>  ]]></xsl:text>
		 				</div></td></tr>
   						<xsl:if test="CodedQuestion/OtherEntryAllowed">
   							<xsl:comment>Other entry allowed for this Coded Question</xsl:comment> 
   							<xsl:text disable-output-escaping="yes"><![CDATA[<tr style="display:none"><td class="fieldName">]]></xsl:text>
   							<xsl:text disable-output-escaping="yes"><![CDATA[<span class="InputDisabledLabel" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
                					<xsl:text disable-output-escaping="yes"><![CDATA[<td><html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)" size="40" maxlength="40" title="Other ]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[')]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth"/></td></tr>]]></xsl:text>
                				</xsl:if>
					</xsl:when>
					<xsl:otherwise>
			    	   	<xsl:comment>processing Multi-Select Coded Question</xsl:comment>
						<tr><td class="fieldName"> 
				 		<xsl:choose>
							<xsl:when test="$thisReq= 'T'">
							<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[<span class="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[ InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[ InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
						</xsl:otherwise>
						</xsl:choose>
						<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span>]]></xsl:text>
                     				</td>
                    				<td>
                    				<div class="multiSelectBlock">
                    				<i> (Use Ctrl to select more than one) </i> <br/>
					     	<xsl:text disable-output-escaping="yes"><![CDATA[<html:select property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>
					     	<xsl:text disable-output-escaping="yes"><![CDATA[multiple="true" size="4" ]]></xsl:text>
					    <xsl:text disable-output-escaping="yes"><![CDATA[onchange="]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[displaySelectedOptions(this, ']]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[-selectedValues')]]></xsl:text><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[;]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" >]]></xsl:text>
                    			      	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>	
		 			 	<xsl:text disable-output-escaping="yes"><![CDATA[ <div id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[-selectedValues" style="margin:0.25em;">]]></xsl:text>
					   	 <b> Selected Values: </b>
                                        	<xsl:text disable-output-escaping="yes"><![CDATA[   </div>  ]]></xsl:text>
		 				</div></td></tr>
   						<xsl:if test="CodedQuestion/OtherEntryAllowed">
   							<xsl:comment>Other entry allowed for this Coded Question</xsl:comment> 
   							<xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName">]]></xsl:text>
   							<xsl:text disable-output-escaping="yes"><![CDATA[<span class="InputDisabledLabel otherEntryField" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
                					<xsl:text disable-output-escaping="yes"><![CDATA[<td><html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)" size="40" maxlength="40" title="Other ]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth"/></td></tr>]]></xsl:text>
                				</xsl:if>
                			</xsl:otherwise>
                			</xsl:choose>
				   </xsl:when>
			 <xsl:when test="substring($thisControl,1,19) = 'ReadOnlyMultiSelect'">
				<xsl:choose>
				<xsl:when test="$thisVisible= 'F'">	
						<xsl:comment>processing Hidden ReadOnly MultiSelect Question</xsl:comment>
	              	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr style="display:none">]]></xsl:text>       	 	
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName"> ]]></xsl:text>
	                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
	                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]></xsl:text>
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S" />]]></xsl:text>
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
	                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
	                    <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
	                    <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>						
						<xsl:if test="substring($thisControl,1,23) = 'ReadOnlyMultiSelectSave'">
						  <xsl:comment> ReadOnly MultiSelect Save Value</xsl:comment>			
							<tr style="display:none"><td> 		
							<div class="multiSelectBlock">		
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:select property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[multiple="true" size="4" >]]></xsl:text>
	                    	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>			
							</div></td></tr>		
						</xsl:if>	
					</xsl:when>
					<xsl:otherwise>
					<xsl:comment>processing ReadOnly MultiSelect Question</xsl:comment>
	              	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr>]]></xsl:text>       	 	
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName"> ]]></xsl:text>
	                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
	                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]></xsl:text>
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S" />]]></xsl:text>
	                	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
	                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
	                    <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
	                    <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>						
						<xsl:if test="substring($thisControl,1,23) = 'ReadOnlyMultiSelectSave'">
						  <xsl:comment> ReadOnly MultiSelect Save Value</xsl:comment>			
							<tr style="display:none"><td> 		
							<div class="multiSelectBlock">		
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:select property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[multiple="true" size="4" >]]></xsl:text>
	                    	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>			
							</div></td></tr>		
						</xsl:if>
					</xsl:otherwise>	
					</xsl:choose>			
			</xsl:when>					   
				   <xsl:when test="substring($thisControl,1,8) = 'Checkbox'">
				   
					<xsl:choose>
					<xsl:when test="$thisVisible= 'F'">			   
			    	   		<xsl:comment>processing Hidden Checkbox Coded Question</xsl:comment>
                     				<xsl:choose>
					     	<xsl:when test="$thisCodeSet= 'P_RACE_CAT' or $thisCodeSet= 'PHVS_RACECATEGORY_CDC_NULLFLAVOR'">
					     	<tr style="display:none">
						 	<td class="fieldName"> 
						 	<xsl:if test="$thisReq= 'T'">
								<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
						 	</xsl:if>
						  	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
						 	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                     			         	</td>
                     			         	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.americanIndianAlskanRace" value="1"]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox> <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>]]></xsl:text>
					         	</td>
					      	</tr>
					      	<tr style="display:none">
							<td class="fieldName"> 
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							</td>
							<td>
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.asianRace" value="1"]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.asian"/>]]></xsl:text>
							</td>
					      	</tr>
					       	<tr style="display:none">
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.africanAmericanRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>   <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>]]></xsl:text>
						 	</td>
					      	</tr>
							<tr style="display:none">
							<td class="fieldName"> 
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							</td>
							<td>
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.hawaiianRace" value="1"]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>]]></xsl:text>
							</td>
                                                </tr>
					      	<tr style="display:none">
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.whiteRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.white"/>]]></xsl:text>
						 	</td>
					      	</tr>
					        
					        <tr style="display:none">
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.otherRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.other"/>]]></xsl:text>
						 	</td>
					      	</tr>
					      	
					      	<tr style="display:none">
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.refusedToAnswer" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>]]></xsl:text>
						 	</td>
					      	</tr>
					      	
					       	<tr style="display:none">
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.unKnownRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.unknown"/>]]></xsl:text>
						 	</td>
					      	</tr>
					     
					     	</xsl:when>
					     	<xsl:otherwise>
					       		<tr style="display:none">
					         	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
						 	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                     			         	</td>
					         	<td>
						       	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>]]></xsl:text>
					         	</td>
					       	</tr>
					      </xsl:otherwise>
					</xsl:choose>
				   
				   
					</xsl:when> 
					<xsl:otherwise>			   
			    	   		<xsl:comment>processing Checkbox Coded Question</xsl:comment>
                     				<xsl:choose>
					     	<xsl:when test="$thisCodeSet= 'P_RACE_CAT' or $thisCodeSet= 'PHVS_RACECATEGORY_CDC_NULLFLAVOR'">
					     	<tr>
						 	<td class="fieldName"> 
						 	<xsl:if test="$thisReq= 'T'">
								<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
						 	</xsl:if>
						  	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
						 	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                     			         	</td>
                     			         	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.americanIndianAlskanRace" value="1"]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox> <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>]]></xsl:text>
					         	</td>
					      	</tr>
					      	<tr>
							<td class="fieldName"> 
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							</td>
							<td>
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.asianRace" value="1"]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.asian"/>]]></xsl:text>
							</td>
					      	</tr>
					       	<tr>
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.africanAmericanRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>   <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>]]></xsl:text>
						 	</td>
					      	</tr>
					      	<tr>
							<td class="fieldName"> 
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							</td>
							<td>
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.hawaiianRace" value="1"]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>]]></xsl:text>
							</td>
					      	</tr>
					      	<tr>
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.whiteRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.white"/>]]></xsl:text>
						 	</td>
					      	</tr>
					      	<!-- For Generic Version 2 and non investigation pages -->
					      	<xsl:choose>
								<xsl:when test="contains($guideVersion,'v1.0') and not(contains($guideVersion,'STD'))">
									<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<tr>
									<td class="fieldName"> 
									<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
									</td>
									<td>
									<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.otherRace" value="1"]]></xsl:text>
									<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.otherRace"/>]]></xsl:text>
									</td>
							      	</tr>
									<tr>
									<td class="fieldName"> 
									<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
									</td>
									<td>
									<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.refusedToAnswer" value="1"]]></xsl:text>
									<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>]]></xsl:text>
									</td>
							      	</tr>
							      	<tr>
									<td class="fieldName"> 
									<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
									</td>
									<td>
									<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.notAsked" value="1"]]></xsl:text>
									<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.notAsked"/>]]></xsl:text>
									</td>
							      	</tr>	
								</xsl:otherwise>
							</xsl:choose>
					       	<tr>
						 	<td class="fieldName"> 
						  	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						  	</td>
						 	<td>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.unKnownRace" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>  <bean:message bundle="RVCT" key="rvct.unknown"/>]]></xsl:text>
						 	</td>
					      	</tr>
					     
					     	</xsl:when>
					     	<xsl:otherwise>
					       	<tr>
					         	<td class="fieldName"> 
						 	<xsl:if test="$thisReq= 'T'">
								<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
						 	</xsl:if>						  	
						  	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
						 	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                     			         	</td>
					         	<td>
							<xsl:choose>
						 	<xsl:when test="$thisReq= 'T'">
						      	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="1"]]></xsl:text>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>]]></xsl:text>
							</xsl:when>
							<xsl:otherwise>
						       	<xsl:text disable-output-escaping="yes"><![CDATA[<html:checkbox ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="1"]]></xsl:text>
						       	<xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text></xsl:if>
						 	<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["></html:checkbox>]]></xsl:text>
							</xsl:otherwise>
						  	</xsl:choose> 
					         	</td>
					       	</tr>
					      </xsl:otherwise>
					</xsl:choose>
					 </xsl:otherwise> 
                			</xsl:choose> 
				   </xsl:when>
			<xsl:when test="substring($thisControl,1,14) = 'ReadOnlySelect'">	
			<xsl:choose>
				<xsl:when test="$thisVisible= 'F'">	
                	<xsl:comment>processing Hidden ReadOnly Select Coded Question</xsl:comment>
               	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr style="display:none">]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName">]]></xsl:text>
                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" >]]></xsl:text>
                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]>
                	</xsl:text>  <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
  
                   <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" ]]></xsl:text>
                   <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                   	<xsl:if test="substring($thisControl,1,18) = 'ReadOnlySelectSave'">	
					   <xsl:text disable-output-escaping="yes"><![CDATA[</td><td><html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[cd" /> ]]></xsl:text>  
					</xsl:if>
                   <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>
                   </xsl:when>
                   <xsl:otherwise>
                   <xsl:comment>processing ReadOnly Select Coded Question</xsl:comment>
               	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr>]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName">]]></xsl:text>
                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" >]]></xsl:text>
                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]>
                	</xsl:text>  <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
  
                   <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" ]]></xsl:text>
                   <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                   	<xsl:if test="substring($thisControl,1,18) = 'ReadOnlySelectSave'">	
					   <xsl:text disable-output-escaping="yes"><![CDATA[</td><td><html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[cd" /> ]]></xsl:text>  
					</xsl:if>
                   <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>
                   
                   </xsl:otherwise></xsl:choose>
            </xsl:when> <!-- end ReadOnly Select --> 	
				
				
				<xsl:when test="substring($thisControl,1,18) = 'SingleSelectSearch'">	
				
				<xsl:comment>processing Coded with Search  </xsl:comment>
				
				
				<tr><td class="fieldName"> 
				 	<xsl:choose>
						<xsl:when test="$thisReq= 'T'">
						<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>
						<xsl:text disable-output-escaping="yes"><![CDATA[<span class="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[ InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[ InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					</xsl:otherwise>
					</xsl:choose>
					<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    			</td>
                    			<td>
                    			
						 <xsl:choose>   
				             <xsl:when test="$SectionAllowBatchEntry= 'Y'">  
				                
				                <xsl:text disable-output-escaping="yes"><![CDATA[<div style="float:left">]]></xsl:text>
				 		         <!-- <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');pgSelectNextFocus(this);]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>-->
						        <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="CodedQuestion/OnChange"><xsl:value-of select="CodedQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;pgSelectNextFocus(this);]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							     
						         <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value"/></html:select>]]></xsl:text>   			 		       	
						   		 <xsl:text disable-output-escaping="yes"><![CDATA[</div>]]></xsl:text>
						   		<!-- 2 hidden inputs for DescriptionId and CodeId and one visible span for Description -->
						   		<xsl:text disable-output-escaping="yes"><![CDATA[<span><input name="attributeMap.]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Code" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeId" type="hidden" value=""></input>]]></xsl:text>
						   		<xsl:text disable-output-escaping="yes"><![CDATA[<input name="attributeMap.]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[DescriptionWithCode" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[DescriptionId" type="hidden" value=""></input>]]></xsl:text>
						   		<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Description"></span></span>]]></xsl:text>
						   		
						   		 <!-- Search and Clear buttons -->
							    <xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Search" ]]></xsl:text>
	       						<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Search" style="margin-left:5px" onclick="searchFromSingleSelectWithSearch(']]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');" />]]></xsl:text>
								<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Clear" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[ClearButton" onclick="clearSingleSelectWithSearchButton(']]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>]]></xsl:text>						    
						   
				                
				                
				             </xsl:when>
						     <xsl:otherwise>   		 		       	
				 				 <xsl:text disable-output-escaping="yes"><![CDATA[<div style="float:left">]]></xsl:text>
				 		         <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;pgSelectNextFocus(this);]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
								 <!--<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="CodedQuestion/OnChange"><xsl:value-of select="CodedQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;pgSelectNextFocus(this);]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>-->
							     
						         <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value"/></html:select>]]></xsl:text>   			 		       	
						   		 <xsl:text disable-output-escaping="yes"><![CDATA[</div>]]></xsl:text>
						   		<!-- 2 hidden inputs for DescriptionId and CodeId and one visible span for Description -->
						   		<xsl:text disable-output-escaping="yes"><![CDATA[<span><input name="attributeMap.]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Code" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeId" type="hidden" value=""></input>]]></xsl:text>
						   		<xsl:text disable-output-escaping="yes"><![CDATA[<input name="attributeMap.]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[DescriptionWithCode" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[DescriptionId" type="hidden" value=""></input>]]></xsl:text>
						   		<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Description"></span></span>]]></xsl:text>
						   		
						   		 <!-- Search and Clear buttons -->
							    <xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Search" ]]></xsl:text>
	       						<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Search" style="margin-left:5px" onclick="searchFromSingleSelectWithSearch(']]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');" />]]></xsl:text>
								<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Clear" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[ClearButton" onclick="clearSingleSelectWithSearchButton(']]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>]]></xsl:text>						    
						   
						     </xsl:otherwise>
					      </xsl:choose> 	
					    </td></tr>	
					 
				</xsl:when>    			 	   
		 <xsl:otherwise> <!--  Select control --> 	

			    	   <xsl:choose>
				<xsl:when test="$thisVisible= 'F'">
			    	   		<xsl:comment>processing Hidden Coded Question  </xsl:comment>
						<tr style="display:none"><td class="fieldName"> 
						<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
						<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    				</td>
                    				<td>
					<xsl:choose>
					<xsl:when test="$thisCodeSet = 'S_JURDIC_C'">
			    	   		<xsl:comment>processing Jurisdistion Coded Question</xsl:comment>
						  	<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyJursdiction"><html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="jurisdictionList" value="key" label="value" /> </html:select></logic:empty>]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyJursdiction"><nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"/></logic:notEmpty>]]></xsl:text>
   					 	</xsl:when>
   					 <xsl:when test="$thisCodeSet = 'STATE_CCD'">
   					 	<xsl:comment>processing State Coded Question</xsl:comment>
                     					<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					 		<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="stateList" value="key" label="value" /> </html:select> ]]></xsl:text>
   					 	</xsl:when>
   						<xsl:when test="$thisCodeSet = 'COUNTY_CCD'">
   							<xsl:variable name="thisQuestion" select="CodedQuestion/QuestionUniqueName" />
					    		<xsl:comment>processing County Coded Question</xsl:comment>
					        	<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					        	<xsl:choose><xsl:when test="substring($thisQuestion,1,3) = 'INV'">
					 			<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="dwrImportedCounties" value="key" label="value" /> </html:select> ]]></xsl:text></xsl:when>
						 		<xsl:when test="substring($thisQuestion,1,3) = 'NOT'">
						 		<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="dwrDefaultStateCounties" value="key" label="value" /> </html:select> ]]></xsl:text></xsl:when>
					 		<xsl:otherwise>
					 			<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="dwrCounties" value="key" label="value" /> </html:select> ]]></xsl:text>
							</xsl:otherwise>
			                		</xsl:choose>
   					 	</xsl:when>	
   						<xsl:when test="$thisCodeSet = 'S_PROGRA_C'">
   					  	<xsl:comment>processing Program Area Coded Question</xsl:comment>   					  
                            					<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                        					<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="programAreaList" value="key" label="value" /> </html:select> ]]></xsl:text>			  
   						</xsl:when>
   						
   						
   						
   						
   						<xsl:when test="$thisCodeSet = 'O_NAICS'">
   					  	<xsl:comment>processing Primary Occupation Question</xsl:comment>   					  
                            					<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                        					<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="primaryOccupationCodes" value="key" label="value" /> </html:select> ]]></xsl:text>			  
   						</xsl:when>  
   						<xsl:when test="$thisCodeSet = 'P_LANG'">
   					  	<xsl:comment>processing Primary Language Question</xsl:comment>   					  
                            					<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                        					<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="languageCodes" value="key" label="value" /> </html:select> ]]></xsl:text>			  
   						</xsl:when>      						
   			 			<xsl:otherwise>
   			 			     <xsl:choose>  
                                                  <xsl:when test="$SectionAllowBatchEntry= 'Y'">  
                                                    <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="CodedQuestion/OnChange"><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>
                                                  </xsl:when>
   			 		       	<xsl:otherwise>   			 		       	
        <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>
   			 		       	
   			 		       	</xsl:otherwise>
   						</xsl:choose>  
					   </xsl:otherwise>
   						</xsl:choose>
   						</td></tr>
   						<xsl:if test="CodedQuestion/OtherEntryAllowed">
   							<xsl:comment>Other entry allowed for this Hidden Coded Question</xsl:comment> 
   							<xsl:text disable-output-escaping="yes"><![CDATA[<tr style="display:none"><td class="fieldName">]]></xsl:text>
   							<xsl:text disable-output-escaping="yes"><![CDATA[<span class="InputDisabledLabel otherEntryField" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
                					<xsl:text disable-output-escaping="yes"><![CDATA[<td><html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)" size="40" maxlength="40" title="Other ]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[')]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth"/></td></tr>]]></xsl:text>
                				</xsl:if>
			    	   		</xsl:when> 
				<xsl:otherwise>	
			    	   	<xsl:comment>processing Coded Question  </xsl:comment>
					<tr><td class="fieldName"> 
				 	<xsl:choose>
						<xsl:when test="$thisReq= 'T'">
						<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>
						<xsl:text disable-output-escaping="yes"><![CDATA[<span class="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[ InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[ InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					</xsl:otherwise>
					</xsl:choose>
					<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    			</td>
                    			<td>
					<xsl:choose>
					<xsl:when test="$thisCodeSet = 'S_JURDIC_C'">
			    	   		<xsl:comment>processing Jurisdistion Coded Question</xsl:comment>
			    	   		<xsl:choose>
							<xsl:when test="$theBusObj='LAB'">
							  <xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyJursdiction"><html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							  <xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="jurisdictionListWthUnknown" value="key" label="value" /> </html:select></logic:empty>]]></xsl:text>
							  <xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyJursdiction"><nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" /></logic:notEmpty>]]></xsl:text>
	   					 	</xsl:when>
							<xsl:otherwise>
							  <xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyJursdiction"><html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							  <xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="jurisdictionList" value="key" label="value" /> </html:select></logic:empty>]]></xsl:text>
							  <xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyJursdiction"><nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"/></logic:notEmpty>]]></xsl:text>
							</xsl:otherwise>
						</xsl:choose>
   					 </xsl:when>
   					<xsl:when test="$thisCodeSet = 'STATE_CCD'">
   					 	<xsl:comment>processing State Coded Question</xsl:comment>
                     				<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="stateList" value="key" label="value" /> </html:select> ]]></xsl:text>
   					 </xsl:when>
   					<xsl:when test="$thisCodeSet = 'COUNTY_CCD'">
   						<xsl:variable name="thisQuestion" select="CodedQuestion/QuestionUniqueName" />
					    	<xsl:comment>processing County Coded Question</xsl:comment>
					        <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					        <xsl:choose><xsl:when test="substring($thisQuestion,1,3) = 'INV'">
					 		<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="dwrImportedCounties" value="key" label="value" /> </html:select> ]]></xsl:text></xsl:when>
				 			<xsl:when test="substring($thisQuestion,1,3) = 'NOT'">
				 			<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="dwrDefaultStateCounties" value="key" label="value" /> </html:select> ]]></xsl:text></xsl:when>
					 	<xsl:otherwise>
					 		<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="dwrCounties" value="key" label="value" /> </html:select> ]]></xsl:text>
						</xsl:otherwise>
			                	</xsl:choose>
   					 </xsl:when>	
   					<xsl:when test="$thisCodeSet = 'S_PROGRA_C'">
   					    <xsl:choose>
							<xsl:when test="$theBusObj='LAB'">
								<xsl:comment>processing Program Area Coded Question</xsl:comment>
								<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyProgramArea"><html:select name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip" /><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange" /></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
								<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="labProgramAreaList" value="key" label="value" /> </html:select></logic:empty> ]]></xsl:text>
							    <xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.ReadOnlyProgramArea"><nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/><html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" /></logic:notEmpty>]]></xsl:text>
								
							</xsl:when>
							<xsl:otherwise>
								<xsl:comment>processing Program Area Coded Question - read only</xsl:comment>
								<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/><html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" />]]></xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
				
	
					
   					<xsl:when test="$thisCodeSet = 'O_NAICS'">
   					 	<xsl:comment>processing Primary Occupation Question</xsl:comment>
                     				<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="primaryOccupationCodes" value="key" label="value" /> </html:select> ]]></xsl:text>
   					 </xsl:when>
   					<xsl:when test="$thisCodeSet = 'P_LANG'">
   					 	<xsl:comment>processing Primary Language Question</xsl:comment>
                     				<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
					 	<xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="languageCodes" value="key" label="value" /> </html:select> ]]></xsl:text>
   					 </xsl:when>  					 
   			 		<xsl:otherwise>
   			 		   <xsl:choose>  
                            <xsl:when test="$SectionAllowBatchEntry= 'Y'">  
                                <xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="CodedQuestion/OnChange"><xsl:value-of select="CodedQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;pgSelectNextFocus(this);]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
							    <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text>
                            </xsl:when>
   			 		       	<xsl:otherwise>   			 		       	
        						<xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="CodedQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="CodedQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;pgSelectNextFocus(this);]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
								<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /></html:select>]]></xsl:text>   			 		       	
   			 		       
   			 		        <!-- <xsl:choose>  
   			 		       		<xsl:when test="$thisCodeSet = 'PHVS_TB_VERCRIT'">
   					 				<xsl:comment>processing Case Verification Coded Question - read only</xsl:comment>
									<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_Hidden" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" />]]></xsl:text>
								</xsl:when>
   			 		     	  </xsl:choose>-->
   			 		       		
   			 		       	</xsl:otherwise>
   						</xsl:choose>    			 		
					
   					</xsl:otherwise>
   					</xsl:choose>
   					</td></tr>
   					<xsl:if test="CodedQuestion/OtherEntryAllowed">
   						<xsl:comment>Other entry allowed for this Coded Question</xsl:comment> 
   						<xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName">]]></xsl:text>
   						<xsl:text disable-output-escaping="yes"><![CDATA[<span class="InputDisabledLabel otherEntryField" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
                				<xsl:text disable-output-escaping="yes"><![CDATA[<td><html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)" size="40" maxlength="40" title="Other ]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[')]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth"/></td></tr>]]></xsl:text>
                			</xsl:if>
				</xsl:otherwise>  
				</xsl:choose>
			</xsl:otherwise> 
                	</xsl:choose> 
		</xsl:if>
	<xsl:if test="NumericQuestion">
		<xsl:comment>processing Numeric Question</xsl:comment>
		<xsl:variable name="thisReq" select="NumericQuestion/Required" />
		<xsl:variable name="thisVisible" select="NumericQuestion/Visible" />
		<xsl:variable name="thisDisabled" select="NumericQuestion/Disabled" />
		<xsl:variable name="thisControl" select="NumericQuestion/ControlType" />
   		<xsl:choose>
			<xsl:when test="substring($thisControl,1,15) = 'ReadOnlyTextbox'">
			<xsl:choose>
		<xsl:when test="$thisVisible= 'F'">
			<xsl:comment>processing Hidden ReadOnly Numeric Question</xsl:comment>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<tr style="display:none"><td class="fieldName">]]></xsl:text>
                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                	<xsl:value-of select="NumericQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[</td><td>]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S"/>]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
	        	<xsl:text disable-output-escaping="yes"><![CDATA[</td></tr>]]></xsl:text>
               		<xsl:if test="substring($thisControl,1,19) = 'ReadOnlyTextboxSave'"> 
		   		<td>
				<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
		   		</td>
			</xsl:if> 
			<xsl:text disable-output-escaping="yes"><![CDATA[</tr>]]></xsl:text>
			</xsl:when>
			<xsl:otherwise><xsl:comment>processing ReadOnly Numeric Question</xsl:comment>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName">]]></xsl:text>
                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                	<xsl:value-of select="NumericQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[</td><td>]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S"/>]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
	        	<xsl:text disable-output-escaping="yes"><![CDATA[</td></tr>]]></xsl:text>
               		<xsl:if test="substring($thisControl,1,19) = 'ReadOnlyTextboxSave'"> 
		   		<td>
				<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
		   		</td>
			</xsl:if> 
			<xsl:text disable-output-escaping="yes"><![CDATA[</tr>]]></xsl:text>
			</xsl:otherwise>
			</xsl:choose>
			</xsl:when>
		<xsl:otherwise>	  		
		<xsl:choose>
		<xsl:when test="$thisVisible= 'F'">
			<tr style="display:none">
			<td class="fieldName"> 
			<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
			<xsl:value-of select="NumericQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    	</td>
                    	<td>
                    	
                 	   <xsl:choose>  
                                    <xsl:when test="$SectionAllowBatchEntry= 'Y'">                                 

<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="NumericQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="NumericQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA["  title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="NumericQuestion/OnKeyUp"><xsl:value-of select="NumericQuestion/OnKeyUp"/></xsl:if><xsl:if test="NumericQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="NumericQuestion/OnBlur"/></xsl:if><xsl:if test="NumericQuestion/OnChange"><xsl:value-of select="NumericQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                    	<xsl:if test="NumericQuestion/RelatedUnitsQuestionUniqueName"><xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsQuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsQuestionUniqueName"/><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange=unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[')]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text></xsl:if>
			<xsl:if test="NumericQuestion/RelatedUnitsQuestionUniqueName"><xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsQuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text></xsl:if>		

                                       </xsl:when>
   			      	<xsl:otherwise>   			 		       	
        	<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="NumericQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="NumericQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA["  title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:if test="NumericQuestion/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="NumericQuestion/OnKeyUp"/></xsl:if><xsl:if test="NumericQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="NumericQuestion/OnBlur"/></xsl:if><xsl:if test="NumericQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="NumericQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                    	<xsl:if test="NumericQuestion/RelatedUnitsQuestionUniqueName"><xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsQuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsQuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text></xsl:if>
			<xsl:if test="NumericQuestion/RelatedUnitsQuestionUniqueName"><xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsQuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text></xsl:if>		 		       	
   			 		       	</xsl:otherwise>
   						</xsl:choose>    			 		
                    	
			
   			</td></tr>
   		</xsl:when>
 		<xsl:otherwise>
			<tr>
			<td class="fieldName"> 
			<xsl:if test="$thisReq= 'T'">
				<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
			</xsl:if>
			<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
			<xsl:value-of select="NumericQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    	</td>
                    	<td>
			<xsl:choose>
			<xsl:when test="$thisReq= 'T'">
			
			
			  	   <xsl:choose>  
                                    <xsl:when test="$SectionAllowBatchEntry= 'Y'">                                 

<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[disabled="true"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="NumericQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="NumericQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA["  title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="NumericQuestion/OnKeyUp"><xsl:value-of select="NumericQuestion/OnKeyUp"/></xsl:if><xsl:if test="NumericQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="NumericQuestion/OnBlur"/></xsl:if><xsl:if test="NumericQuestion/OnChange"><xsl:value-of select="NumericQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="NumericQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="NumericQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>				
                                       </xsl:when>
   			      	<xsl:otherwise>   			 		       	
        <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[disabled="true"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="NumericQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="NumericQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA["  title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:if test="NumericQuestion/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="NumericQuestion/OnKeyUp"/></xsl:if><xsl:if test="NumericQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="NumericQuestion/OnBlur"/></xsl:if><xsl:if test="NumericQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="NumericQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="NumericQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="NumericQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>				   			 		       	</xsl:otherwise>
   						</xsl:choose>    			 		


				
			</xsl:when>
			<xsl:otherwise>
			
				   <xsl:choose>  
                                    <xsl:when test="$SectionAllowBatchEntry= 'Y'"> 
                                     <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[disabled="true"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="NumericQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="NumericQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA["  title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="NumericQuestion/OnKeyUp"><xsl:value-of select="NumericQuestion/OnKeyUp"/></xsl:if><xsl:if test="NumericQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="NumericQuestion/OnBlur"/></xsl:if><xsl:if test="NumericQuestion/OnChange"><xsl:value-of select="NumericQuestion/OnChange"/></xsl:if><xsl:if test="NumericQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="NumericQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>			
                                       </xsl:when>
   			      	<xsl:otherwise>   			 		       	
    <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[disabled="true"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="NumericQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="NumericQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA["  title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:if test="NumericQuestion/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="NumericQuestion/OnKeyUp"/></xsl:if><xsl:if test="NumericQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="NumericQuestion/OnBlur"/></xsl:if><xsl:if test="NumericQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="NumericQuestion/OnChange"/></xsl:if><xsl:if test="NumericQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="NumericQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>				   			 		       	</xsl:otherwise>
   						</xsl:choose>    			 		

			
				
			</xsl:otherwise>
			</xsl:choose>
                    	<xsl:if test="NumericQuestion/RelatedUnitsValueSetName"><xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Unit)" styleId="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[UNIT]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[')]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[">]]>
</xsl:text>
</xsl:if>
			<xsl:if test="NumericQuestion/RelatedUnitsValueSetName"><xsl:text disable-output-escaping="yes"><![CDATA[<nedss:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select>]]></xsl:text></xsl:if>
   			</td></tr>
   		</xsl:otherwise>  
   		</xsl:choose>
 	    </xsl:otherwise>
	    </xsl:choose>   		
	</xsl:if>
	<xsl:if test="DateQuestion">
		<xsl:comment>processing Date Question</xsl:comment>
		<xsl:variable name="thisReq" select="DateQuestion/Required" />
		<xsl:variable name="thisDisabled" select="DateQuestion/Disabled" />
		<xsl:variable name="thisDateVisible" select="DateQuestion/Visible" />
		<xsl:variable name="thisFuture" select="DateQuestion/AllowFutureDate" />
		<xsl:variable name="thisControl" select="DateQuestion/ControlType" />
		<xsl:choose>
			<xsl:when test="substring($thisControl,1,15) = 'ReadOnlyTextbox'">
			<xsl:choose><xsl:when test="$thisDateVisible='F'">
			<xsl:comment>processing Hidden ReadOnly Date</xsl:comment>
			     <tr  style="display:none"><td class="fieldName"> 
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text><xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                 </td><td>
                 <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S"/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
                </td>
				<xsl:if test="substring($thisControl,1,19) = 'ReadOnlyTextboxSave'"> 
				     <td>
							   <xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
					</td>
				</xsl:if>            
                </tr>
                </xsl:when>
                <xsl:otherwise>
                <xsl:comment>processing ReadOnly Date</xsl:comment>
			     <tr><td class="fieldName"> 
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text><xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                 </td><td>
                 <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S"/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
                </td>
				<xsl:if test="substring($thisControl,1,19) = 'ReadOnlyTextboxSave'"> 
				     <td>
							   <xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
					</td>
				</xsl:if>            
                </tr>
                </xsl:otherwise> 
                </xsl:choose>
		</xsl:when>
			<xsl:otherwise>	
				<xsl:choose>
				<xsl:when test="$thisDateVisible='E'">
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEqual name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="actionMode" value="Create">]]></xsl:text>
					<tr><td class="fieldName"> 
					<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if>
					<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>				
					<xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
								</td>
								<td>
					<xsl:choose>
						<xsl:when test="$thisReq= 'T'">
				
							   <xsl:choose>  
										<xsl:when test="$SectionAllowBatchEntry= 'Y'">                    
										   <xsl:text disable-output-escaping="yes"><![CDATA[<html:text  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onChange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>			
                                       </xsl:when>
							<xsl:otherwise>   			 		       	
								  <xsl:text disable-output-escaping="yes"><![CDATA[<html:text  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>						   			 		       	</xsl:otherwise>
							</xsl:choose>    			 		
						</xsl:when>
				<xsl:otherwise>
				  <xsl:choose>  
					  <xsl:when test="$SectionAllowBatchEntry= 'Y'"> 
						   <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onChange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>			
                       </xsl:when>
   			      	<xsl:otherwise>   			 		       	
							   <xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>		
					</xsl:otherwise>
   				</xsl:choose>    			 		
				</xsl:otherwise>
				</xsl:choose>
				<xsl:text disable-output-escaping="yes"><![CDATA[<html:img src="calendar.gif" alt="Select a Date" ]]></xsl:text><xsl:choose><xsl:when test="$thisFuture= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[onclick="getCalDateFuture(']]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[onclick="getCalDate(']]></xsl:text></xsl:otherwise></xsl:choose><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon'); ]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[return false;" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon" ]]></xsl:text><xsl:choose><xsl:when test="$thisFuture= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[onkeypress ="showCalendarFutureEnterKey(']]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[onkeypress="showCalendarEnterKey(']]></xsl:text></xsl:otherwise></xsl:choose><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon',event)"></html:img>]]></xsl:text>
				</td> </tr>
				<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEqual> ]]></xsl:text>
				</xsl:when>
				<xsl:when test="$thisDateVisible='F'">
					<xsl:comment>Date Field Visible set to False</xsl:comment>
					<tr style="display:none"><td class="fieldName"> 
					<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
					<xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
               		</td>
              		<td>
                      	<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate(']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon');]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[return false;" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon"]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ onkeypress="showCalendarEnterKey(']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon',event);" ></html:img>]]></xsl:text>
					</td> </tr>
					</xsl:when> 
			<xsl:otherwise>
					<tr><td class="fieldName"> 
					<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if>
					<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text>
					<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[requiredInputField ]]></xsl:text></xsl:if>
					<xsl:choose>
					    <xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when>
						<xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>				
					<xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    	</td>
                    	<td>
					<xsl:choose>
					<xsl:when test="$thisReq= 'T'">
							<xsl:choose>  
						  <xsl:when test="$SectionAllowBatchEntry= 'Y'"> 	
						<xsl:text disable-output-escaping="yes"><![CDATA[<html:text  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onChange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>			
                                       </xsl:when>
						<xsl:otherwise>   			 		       	
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:text  name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>				   			 		       	</xsl:otherwise>
   						</xsl:choose>   				
						</xsl:when>
						<xsl:otherwise>			
						 <xsl:choose>  
					  <xsl:when test="$SectionAllowBatchEntry= 'Y'"> 	
								<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>			
                       </xsl:when>
							<xsl:otherwise>   			 		       	
								<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[ disabled="true" ]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="DateQuestion/OnKeyUp"/><xsl:if test="DateQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="DateQuestion/OnBlur"/></xsl:if><xsl:if test="DateQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="DateQuestion/OnChange"/></xsl:if><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>				   			 		       	</xsl:otherwise>
   						</xsl:choose>                     		
					</xsl:otherwise>
					</xsl:choose>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:img src="calendar.gif" alt="Select a Date" ]]></xsl:text><xsl:choose><xsl:when test="$thisFuture= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[onclick="getCalDateFuture(']]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[onclick="getCalDate(']]></xsl:text></xsl:otherwise></xsl:choose><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon'); ]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[return false;" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon" ]]></xsl:text><xsl:choose><xsl:when test="$thisFuture= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[onkeypress ="showCalendarFutureEnterKey(']]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[onkeypress="showCalendarEnterKey(']]></xsl:text></xsl:otherwise></xsl:choose><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon',event)"></html:img>]]></xsl:text>
					</td> </tr>
				</xsl:otherwise>
				</xsl:choose>
				
		   </xsl:otherwise>
		</xsl:choose>		
				
	</xsl:if>
	
	<xsl:if test="ParticipationQuestion">
		<xsl:variable name="thisParType" select="ParticipationQuestion/ParticipationType" />
		<xsl:variable name="thisReq" select="ParticipationQuestion/Required" />
		<xsl:variable name="thisDisabled" select="ParticipationQuestion/Disabled" />
		<xsl:variable name="thisVisible" select="ParticipationQuestion/Visible" />
		<xsl:choose>
	   <xsl:when test="$thisParType = 'PSN'">
		<xsl:comment>processing Provider Type Participation Question</xsl:comment>
		<xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName">]]></xsl:text>
		<xsl:if test="$thisReq= 'T'">
			<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
		<xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> </td>]]></xsl:text>    			
       		<xsl:text disable-output-escaping="yes"><![CDATA[<td>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="none">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Clear/Reassign" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeClearButton" onclick="clearProvider(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</span>]]></xsl:text> 
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchControls" ]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[class="none"]]></xsl:text> 
		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[><input type="button" class="Button" value="Search" ]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon" onclick="getProvider(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');" />&nbsp; - OR - &nbsp;]]></xsl:text>
		<xsl:choose>
		<xsl:when test="$thisReq= 'T'">
		<xsl:text disable-output-escaping="yes"><![CDATA[<html:text styleClass="requiredInputField" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text" ]]></xsl:text>					
		</xsl:when>
		<xsl:otherwise>
               	<xsl:text disable-output-escaping="yes"><![CDATA[<html:text property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text" ]]></xsl:text>
		</xsl:otherwise>
		</xsl:choose>	
      		<xsl:text disable-output-escaping="yes"><![CDATA[size="10" maxlength="10" onkeydown="genProviderAutocomplete(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text',']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list')" ]]></xsl:text><xsl:if test="ParticipationQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[onchange="]]></xsl:text><xsl:value-of select="ParticipationQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text></xsl:if>
       		<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Quick Code Lookup" ]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeLookupButton" onclick="getDWRProvider(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')" ]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[style="visibility:hidden"]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[/><div class="page_name_auto_complete" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list" style="background:#DCDCDC"></div>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</td> </tr>]]></xsl:text>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

		<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S">]]></xsl:text><xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[ Selected: </td>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
		<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="test2">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
		<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span class="test2">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid"/>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">${]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchResult}</span>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</span> </td>]]></xsl:text>   
		
       		<xsl:text disable-output-escaping="yes"><![CDATA[</tr>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

	      	<xsl:text disable-output-escaping="yes"><![CDATA[<td colspan="2" style="text-align:center;">]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Error"/>]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[</td></tr>]]></xsl:text>
	   </xsl:when> <!--##### ParType = Organization ##### -->
		<xsl:when test="$thisParType = 'ORG'">
		<xsl:comment>processing Organization Type Participation Question</xsl:comment>
                <xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
				<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName">]]></xsl:text>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEqual name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.readOnlyParticipant" value="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
			<xsl:if test="$thisReq= 'T'">
			<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
		<xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEqual>]]></xsl:text>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[</td>]]></xsl:text>	 
		   			
       		<xsl:text disable-output-escaping="yes"><![CDATA[<td>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="none">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
        		
        	<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEqual name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.readOnlyParticipant" value="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Clear/Reassign" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeClearButton" onclick="clearOrganization(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEqual>]]></xsl:text>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[</span>]]></xsl:text> 
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchControls" ]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[class="none"]]></xsl:text> 
		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[><input type="button" class="Button" value="Search" ]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon" onclick="getReportingOrg(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');" />&nbsp; - OR - &nbsp;]]></xsl:text>
                <xsl:choose>
                <xsl:when test="$thisReq= 'T'">
                <xsl:text disable-output-escaping="yes"><![CDATA[<html:text styleClass="requiredInputField" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text" ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[size="10" maxlength="10" onkeydown="genOrganizationAutocomplete(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text',']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list')" ]]></xsl:text><xsl:if test="ParticipationQuestion/OnChange"><xsl:text disable-output-escaping="yes"><![CDATA[onchange="]]></xsl:text><xsl:value-of select="ParticipationQuestion/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text></xsl:if>
                <xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Quick Code Lookup" ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeLookupButton" onclick="getDWROrganization(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
             	<xsl:text disable-output-escaping="yes"><![CDATA[style="visibility:hidden"]]></xsl:text>
             	<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>

                <xsl:text disable-output-escaping="yes"><![CDATA[/><div class="page_name_auto_complete" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list" style="background:#DCDCDC"></div>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text>                 
                </xsl:when>
                <xsl:otherwise>
                <xsl:text disable-output-escaping="yes"><![CDATA[<html:text property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text" ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[size="10" maxlength="10" onkeydown="genOrganizationAutocomplete(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text',']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list')" ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Quick Code Lookup" ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeLookupButton" onclick="getDWROrganization(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')" ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
             	<xsl:text disable-output-escaping="yes"><![CDATA[style="visibility:hidden"]]></xsl:text>
             	<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
                   
                <xsl:text disable-output-escaping="yes"><![CDATA[/><div class="page_name_auto_complete" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list" style="background:#DCDCDC"></div>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text>
                </xsl:otherwise>
               </xsl:choose>                    		
		<xsl:text disable-output-escaping="yes"><![CDATA[</td> </tr>]]></xsl:text>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

		<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S">]]></xsl:text><xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[ Selected: </td>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
		<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="test2">]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
		<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span class="test2">]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid"/>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">${]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchResult}</span>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</span> </td>]]></xsl:text>    			
               	<xsl:text disable-output-escaping="yes"><![CDATA[</tr>]]></xsl:text>
               	
                <xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

	      	<xsl:text disable-output-escaping="yes"><![CDATA[<td colspan="2" style="text-align:center;">]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Error"/>]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[</td> </tr>]]></xsl:text>
		</xsl:when>
		<!-- ParType = Place -->
		<xsl:when test="$thisParType = 'PLC'">
			<xsl:comment>processing Place Type Participation Question</xsl:comment>
            <xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName">]]></xsl:text>
			<xsl:if test="$thisReq= 'T'">
				<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
			</xsl:if>
			<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
			<xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> </td>]]></xsl:text>    			
       		<xsl:text disable-output-escaping="yes"><![CDATA[<td>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="none">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Clear/Reassign" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeClearButton" onclick="clearPlace(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[</span>]]></xsl:text> 
			<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchControls" ]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[class="none"]]></xsl:text> 
			<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[><input type="button" class="Button" value="Search" ]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon" onclick="getHangoutPlace(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');" />&nbsp; - OR - &nbsp;]]></xsl:text>

			<xsl:text disable-output-escaping="yes"><![CDATA[<html:text property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Text" ]]></xsl:text>                
			<xsl:text disable-output-escaping="yes"><![CDATA[size="10" maxlength="10" ]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Quick Code Lookup" ]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeLookupButton" onclick="getDWRPlace(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[style="visibility:hidden"]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>

			<xsl:text disable-output-escaping="yes"><![CDATA[/>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text>                 
                   		
			<xsl:text disable-output-escaping="yes"><![CDATA[</td> </tr>]]></xsl:text>
		
			<xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

			<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S">]]></xsl:text><xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[ Selected: </td>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
			<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="test2">]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
			<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span class="test2">]]></xsl:text>
			<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
			<xsl:choose>
				<xsl:when test="$thisReq= 'T'">
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Disp" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Disp" styleId="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
				</xsl:otherwise>
			</xsl:choose>		
			<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Disp">${]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchResult}</span>]]></xsl:text>
		    <xsl:text disable-output-escaping="yes"><![CDATA[</span> </td>]]></xsl:text>    			
            <xsl:text disable-output-escaping="yes"><![CDATA[</tr>]]></xsl:text>
               	
            <xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

	      	<xsl:text disable-output-escaping="yes"><![CDATA[<td colspan="2" style="text-align:center;">]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Error"/>]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[</td> </tr>]]></xsl:text>
		</xsl:when> 
		<!-- ParType = Place -->	
	   <xsl:when test="$thisParType = 'CON'">
		<xsl:comment>processing Contact Type Participation Question</xsl:comment>
		<xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName">]]></xsl:text>
		<xsl:if test="$thisReq= 'T'">
			<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
		<xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> </td>]]></xsl:text>    			
       		<xsl:text disable-output-escaping="yes"><![CDATA[<td>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="none">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="clear]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="Button" value="Clear/Reassign" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[CodeClearButton" onclick="clearOtherContact(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</span>]]></xsl:text> 
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchControls" ]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[class="none"]]></xsl:text> 
		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[><input type="button" class="Button" value="Search" ]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon" onclick="getOtherPersonPopUp(']]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');"&nbsp;]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[style="visibility:hidden"]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
      		<xsl:text disable-output-escaping="yes"><![CDATA[/><div class="page_name_auto_complete" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_qec_list" style="background:#DCDCDC"></div>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</td> </tr>]]></xsl:text>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

		<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top" id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[S">]]></xsl:text><xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[ Selected: </td>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:empty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
		<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="test2">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:empty>]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid">]]></xsl:text>			
		<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span class="test2">]]></xsl:text>
       		<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden property="attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid"/>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">${]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchResult}</span>]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</span> </td>]]></xsl:text>   
		
       		<xsl:text disable-output-escaping="yes"><![CDATA[</tr>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<tr]]></xsl:text><xsl:if test="$thisVisible= 'F'"><xsl:text disable-output-escaping="yes"><![CDATA[ style="display:none"]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

	      	<xsl:text disable-output-escaping="yes"><![CDATA[<td colspan="2" style="text-align:center;">]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Error"/>]]></xsl:text>
	      	<xsl:text disable-output-escaping="yes"><![CDATA[</td></tr>]]></xsl:text>
	   </xsl:when> <!--##### ParType = Contact ##### -->				
		<xsl:otherwise> <xsl:comment>JSP Generate Error: Unknown Participation Type in XML</xsl:comment></xsl:otherwise>
		</xsl:choose>
		</xsl:if>  <!-- ######## End of Participation ######### -->
		
		<xsl:if test="TextArea">
			
			<xsl:variable name="thisVisible" select="TextArea/Visible" />
			<xsl:variable name="thisReq" select="TextArea/Required" />
			<xsl:variable name="thisDisabled" select="TextArea/Disabled" />
			<xsl:variable name="thisControl" select="TextArea/ControlType" />
			<xsl:choose>
			<xsl:when test="$thisControl= 'RollingNote'">			
				<xsl:comment>processing Rolling Note</xsl:comment>
			</xsl:when>
			<xsl:otherwise>
				<xsl:comment>processing TextArea</xsl:comment>
   			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
			<xsl:when test="$thisVisible= 'F'">
				<tr style="display:none">
				<td class="fieldName"> 
				<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
				<xsl:value-of select="TextArea/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    		</td>
                    		<td>
				<xsl:text disable-output-escaping="yes"><![CDATA[<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId ="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="]]></xsl:text><xsl:value-of select="TextArea/Disabled"/><xsl:if test="TextArea/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextArea/OnKeyUp"/></xsl:if><xsl:if test="TextAea/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextArea/OnKeyDown"/></xsl:if><xsl:if test="TextArea/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextArea/OnBlur"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
   				</td> </tr>
			</xsl:when>
			<xsl:otherwise>
				<tr>
				<td class="fieldName"> 
				<xsl:if test="$thisReq= 'T'">
					<xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text>					
				</xsl:if>
				<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:choose><xsl:when test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text></xsl:when><xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text></xsl:otherwise></xsl:choose><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
				<xsl:value-of select="TextArea/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                    		</td>
                    		<td>
				<xsl:choose>
				<xsl:when test="$thisReq= 'T'">
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:textarea styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'Y'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" style="WIDTH: 500px; HEIGHT: 100px;" name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId ="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="TextArea/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextArea/OnKeyUp"/></xsl:if><xsl:if test="TextAea/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextArea/OnKeyDown"/></xsl:if><xsl:if test="TextArea/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextArea/OnBlur"/></xsl:if><xsl:if test="$thisControl='RollingNote' and $SectionAllowBatchEntry='Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="TextArea/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text></xsl:when>
				<xsl:otherwise>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId ="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="TextArea/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextArea/OnKeyUp"/></xsl:if><xsl:if test="TextAea/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextArea/OnKeyDown"/></xsl:if><xsl:if test="TextArea/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextArea/OnBlur"/></xsl:if><xsl:if test="$thisControl='RollingNote' and $SectionAllowBatchEntry='Y'"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="TextArea/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
				</xsl:otherwise>
				</xsl:choose>
   				</td> </tr>
   			</xsl:otherwise>
			</xsl:choose>
			
			<xsl:if test="$thisControl= 'RollingNote' and $SectionAllowBatchEntry= 'Y' ">			
				<xsl:comment>Adding Hidden Date and User fields for Batch Rolling Note</xsl:comment>
			</xsl:if>		
			
		</xsl:if> 
		
		 <xsl:if test="StaticLink">  
              <xsl:comment>processing Hyperlink</xsl:comment>
              	<tr><td align="left" colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="StaticLink/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;<a href="]]></xsl:text><xsl:value-of select="StaticLink/LinkValue"/><xsl:text disable-output-escaping="yes"><![CDATA[" TARGET="_blank">]]></xsl:text><xsl:value-of select="StaticLink/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text></td></tr>           
         </xsl:if>
         
         
          
        <xsl:if test="SetValuesButton">  
             <xsl:comment>processing SetValuesButton</xsl:comment>
             <tr>
			 <td class="fieldName"> </td>
			 
			 <td align="left"><xsl:text disable-output-escaping="yes">
			 <![CDATA[<input id="]]></xsl:text><xsl:value-of select="SetValuesButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["]]> </xsl:text><xsl:text disable-output-escaping="yes">		   <![CDATA[ onclick="populateSetValuesButton(']]></xsl:text><xsl:value-of select="SetValuesButton/DefaultValue"/><xsl:text disable-output-escaping="yes"> <![CDATA[',this)";  type="button"   style="width:14em;"  value="]]> </xsl:text><xsl:value-of select="SetValuesButton/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]> </xsl:text>
			
			
			 
			<xsl:text disable-output-escaping="yes">
			 <![CDATA[<input id="]]></xsl:text><xsl:value-of select="SetValuesButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Hidden"]]> </xsl:text><xsl:text disable-output-escaping="yes">		   <![CDATA[ value="]]></xsl:text><xsl:value-of select="SetValuesButton/DefaultValue"/><xsl:text disable-output-escaping="yes"> <![CDATA[";  type="button"   style="width:14em;display:none" />]]> </xsl:text>
						
				</td></tr>
	         </xsl:if>
             
             
		 <xsl:if test="PatientSearch">  
             <xsl:comment>processing Patient search Button</xsl:comment>
             <tr><td align="left"><xsl:text disable-output-escaping="yes"><![CDATA[<input id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="PatientSearch/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L"]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ onclick="getPatient('PatentSearch')" type="button" value="Search"/><img width="2" height="0" alt="" src="transparent.gif" border="0"/><input onclick="clearPatientData()" type="button" value="Clear"/>]]></xsl:text></td></tr>
             </xsl:if>
         	<xsl:if test="ActionButton">  
             <xsl:comment>processing Action Button</xsl:comment>
             <tr><td class="fieldName"> 
				<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="ActionButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ActionButton/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text><xsl:value-of select="ActionButton/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
          		</td>
          		<td align="left">
          		<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden property="attributeMap.]]></xsl:text><xsl:value-of select="ActionButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid"/>]]></xsl:text>
          		<xsl:text disable-output-escaping="yes"><![CDATA[<input id="]]></xsl:text><xsl:value-of select="ActionButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Button" onclick="OpenForm(']]></xsl:text><xsl:value-of select="ActionButton/QuestionDefaultValue"/><xsl:text disable-output-escaping="yes"><![CDATA[',this);]]></xsl:text><xsl:value-of select="StaticLink/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[" type="button" value="Manage ]]></xsl:text><xsl:value-of select="ActionButton/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text></td></tr>
             </xsl:if>
         	<xsl:if test="StaticComment">  
             	<xsl:comment>processing Static Comment</xsl:comment>
            		 <tr><td align="left" colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="StaticComment/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;]]></xsl:text><xsl:value-of select="StaticComment/CommentText"/><xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text></td></tr>           
         	</xsl:if>
          	<xsl:if test="StaticInfoBar">  
             	<xsl:comment>processing Static Line Separator</xsl:comment>
             	<tr><td align="left" colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="StaticInfoBar/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text><hr/><xsl:text disable-output-escaping="yes"><![CDATA[</span> ]]></xsl:text></td></tr>             
         	</xsl:if>
                        
			 <xsl:if test="$isHIVElement = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[</logic:equal>]]></xsl:text>
		</xsl:if>
	  </xsl:for-each> <!-- Page Element -->  
      <xsl:choose>  
       <xsl:when test="$SectionAllowBatchEntry= 'Y'">       
			<xsl:if test="$hasHIVQuestions = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="securityMap.hasHIVPermissions" value="T">]]></xsl:text>
			</xsl:if>
           <xsl:text disable-output-escaping="yes"><![CDATA[  
             <% String disableSubmitButton="no"; 
               if(request.getAttribute("disableSubmitButton") != null){
                    disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
                    }
                 %>    
                    
                  <%if(disableSubmitButton.equals("yes")) {%>      
	  		<tr id="AddButtonToggle]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
			<td colspan="2" align="right">
			<input type="button" value="     Add     "   disabled="disabled" onclick="if (pg]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[BatchAddFunction()) writeQuestion(']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>&nbsp;&nbsp;
			&nbsp;
			</td>
			</tr>
			<%} else {%>
			<tr id="AddButtonToggle]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
			<td colspan="2" align="right">
			<input type="button" value="     Add     "  onclick="if (pg]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[BatchAddFunction()) writeQuestion(']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');"/>&nbsp;&nbsp;
			&nbsp;
			</td>
			</tr>
                   <%} %>
			<tr id="UpdateButtonToggle]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["
        style="display:none">
			<td colspan="2" align="right">
			<input type="button" value="   Update   "    onclick="if (pg]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[BatchAddFunction()) writeQuestion(']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');"/>&nbsp;		&nbsp;
			&nbsp;
			</td>
			</tr>
			<tr id="AddNewButtonToggle]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["
     style="display:none">
			<td colspan="2" align="right">
			<input type="button" value="  Add New  "  onclick="clearClicked(']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>&nbsp;	&nbsp;&nbsp;

			</td>
	   </tr>
           ]]></xsl:text>
           <xsl:if test="$hasHIVQuestions = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[</logic:equal>]]></xsl:text>
			</xsl:if>
              </xsl:when> 
      </xsl:choose>	

			 
               <xsl:text disable-output-escaping="yes"><![CDATA[</nedss:container>]]></xsl:text>  <!-- End SubSection -->             
             
          </xsl:for-each>   <!-- SubSection -->         
	      <xsl:text disable-output-escaping="yes"><![CDATA[</nedss:container>]]></xsl:text>  <!-- End Section -->     	
           

          </xsl:for-each>   <!-- Section -->
          <xsl:if test="$theMultTab ='Y'">
         	<div class="tabNavLinks">
	         <a href="javascript:navigateTab('previous')"> Previous </a><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;&nbsp;]]></xsl:text>
	         <a href="javascript:navigateTab('next')"> Next </a>
	         <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS function to work properly -->
	         <input type="hidden" name="endOfTab" />
		</div>
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[</div> </td></tr>]]></xsl:text> 
	</xsl:template>
</xsl:stylesheet>
