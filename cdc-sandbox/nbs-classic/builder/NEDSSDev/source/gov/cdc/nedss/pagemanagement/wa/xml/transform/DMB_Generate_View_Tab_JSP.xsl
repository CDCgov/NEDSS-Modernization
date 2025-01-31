<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:output method="xml" omit-xml-declaration="yes" version="1.0" encoding="UTF-8"/>
        <xsl:param name="bus_obj" select="'INV'"/>
        <xsl:param name="mult_tab" select="'Y'"/>
        <xsl:param name="the_form" select="'PageForm'"/>
     	<xsl:param name="the_prop" select="'pageClientVO'"/>
     	<xsl:variable name="theBusObj" select="$bus_obj" />
        <xsl:variable name="theMultTab" select="$mult_tab" />     	
	    <xsl:variable name="theForm" select="$the_form" />
      	<xsl:variable name="theProp" select="$the_prop" />
     	
     	
    <xsl:template match="PageTab" xml:space="preserve">
       <xsl:comment> ### DMB: BEGIN JSP VIEW PAGE GENERATE ###--</xsl:comment>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>]]></xsl:text>
        <xsl:text disable-output-escaping="yes"><![CDATA[<%@ page isELIgnored ="false" %>]]></xsl:text>
      	<xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="java.util.*" %>]]></xsl:text>
    	<xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>]]></xsl:text>
   <xsl:text disable-output-escaping="yes"><![CDATA[ 
     <% 
                   Map map = new HashMap();
                   if(request.getAttribute("SubSecStructureMap") != null){
                   
                 // String watemplateUid="1000879";
                 // map = util.getBatchMap(new Long(watemplateUid));
                     map =(Map)request.getAttribute("SubSecStructureMap");
            }%> 
       ]]> </xsl:text>      

    <xsl:text disable-output-escaping="yes"><![CDATA[<%   
     String tabId = "view]]></xsl:text><xsl:value-of select="translate(TabName,' ','')"/>";
    <xsl:text disable-output-escaping="yes"><![CDATA[
    
    
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
				<xsl:variable name="SubSectionId" select="QuestionUniqueName" />
             <xsl:choose>
                 <xsl:when test="$SubSectionVisible= 'T'">
                     <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:container id="]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" name="]]></xsl:text><xsl:value-of select="SubSectionName"/><xsl:text disable-output-escaping="yes"><![CDATA[" isHidden="F" classType="subSect" >]]></xsl:text>							
                 </xsl:when>
                 <xsl:otherwise>
                     <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:container id="]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" name="]]></xsl:text><xsl:value-of select="SubSectionName"/><xsl:text disable-output-escaping="yes"><![CDATA[" isHidden="T" classType="subSect" >]]></xsl:text>
                 </xsl:otherwise>
             </xsl:choose>  
             
             <xsl:variable name="SectionAllowBatchEntry" select="AllowBatchEntry" />
             <xsl:variable name="hasHIVQuestions" select="hasHIVQuestions" />
			 <xsl:choose>  
				<xsl:when test="$SectionAllowBatchEntry= 'Y'">       
					<xsl:if test="$hasHIVQuestions = 'Y'">
						<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="securityMap.hasHIVPermissions" value="T">]]></xsl:text>
					</xsl:if>
					<xsl:text disable-output-escaping="yes"><![CDATA[  <tr> <td colspan="2" width="100%">
		<table role="presentation" width="100%"  border="0" align="center">
		<tr><td width="5%">.</td>
		 <td   width="90%"> 
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
		     <td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
		    <% for(int i=0;i<batchrec.length;i++){%>  
		       <%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%> 
		                 
		                   <th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>   
		          <%} %>	   
		      <%} %>      
		     
		    </tr>
		  </thead>
		   <tbody id="questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
		      <tr id="pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="odd" style="display:none">
		       <td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
		      <td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">                            
		      
		          <% for(int i=0;i<batchrec.length;i++){%>  
		                  <% String validdisplay =batchrec[i][0]; %>     
		             <%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%> 
		                      <td width="<%=batchrec[i][4]%>%" align="left">
			      	             <span id="table<%=batchrec[i][0]%>"> </span> 	      	            
			      	             </td>    
		             <%} %> 
		          <%} %>      
		    </tr>
		    

		  </tbody> 
		  <tbody id="noquestionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
		  <tr id="nopattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" class="odd" style="display:none">
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
      
       <xsl:choose>  
					<xsl:when test="$SectionAllowBatchEntry= 'E'"> 
					      <xsl:comment> ########### EDITABLE SUB SECTION ON VIEW SCREEN###########  </xsl:comment>
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
		      <td style="width:3%;text-align:center;">&nbsp;</td>
		      <td style="width:3%;text-align:center;">
				<input id="view]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClickedForEditableBatch(this.id,']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
			  </td>                         
		      <td style="width:3%;text-align:center;">&nbsp;</td>
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
			<xsl:variable name="thisQuestionUniqueName" select="TextQuestion/QuestionUniqueName" />
			
            <xsl:choose>
				<xsl:when test="$thisVisible= 'F' and $SectionAllowBatchEntry= 'E'">
					<xsl:comment>processing Hidden Text Question</xsl:comment>
	                	<tr style="display:none"> 
		                	<td class="fieldName"> 
		                		<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
		                		<xsl:value-of select="TextQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
	                        </td>
	                        <td>
	                        	<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" size="]]></xsl:text><xsl:value-of select="TextQuestion/DisplayLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" maxlength="]]></xsl:text><xsl:value-of select="TextQuestion/MaxLength"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:if test="TextQuestion/OnKeyUp"><xsl:value-of select="TextQuestion/OnKeyUp"/></xsl:if><xsl:if test="TextQuestion/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextQuestion/OnKeyDown"/></xsl:if><xsl:if test="TextQuestion/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextQuestion/OnBlur"/></xsl:if><xsl:if test="TextQuestion/FieldStyleClass"><xsl:text disable-output-escaping="yes"><![CDATA[" styleClass="]]></xsl:text><xsl:value-of select="TextQuestion/FieldStyleClass"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
				   			</td> 
			   			</tr>
					</xsl:when>
					<xsl:when test="$thisVisible= 'F' and $thisQuestionUniqueName= 'NBS459'">
						<xsl:comment>Processing Hidden Text Question NBS459 in Lab</xsl:comment>
						
						<tr style="display:none"><td class="fieldName"> 
				
		                <xsl:if test="substring($thisControl,1,7) = 'Textbox'"> 
				   		<td>
							<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
				   		</td>
						</xsl:if>
						</td>
						</tr>
						
						
						
					</xsl:when>
					
					<xsl:when test="$thisVisible= 'F'">
						<xsl:comment>skipping Hidden Text Question</xsl:comment>
					</xsl:when>
					
            	<xsl:otherwise>  
                <xsl:comment>processing Text Question</xsl:comment>
                <tr> <td class="fieldName" valign="top"> 
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="TextQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
                <xsl:value-of select="TextQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                </td>
                <td>
                <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>

                <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" />]]></xsl:text>
                </td> </tr>
   		</xsl:otherwise>
		</xsl:choose>                
            </xsl:if>
            <xsl:if test="CodedQuestion">
            	<xsl:variable name="thisControl" select="CodedQuestion/ControlType" />
            	<xsl:variable name="thisReq" select="CodedQuestion/Required" />
            	<xsl:variable name="thisVisible" select="CodedQuestion/Visible" />
            	<xsl:choose>
            	
   		<xsl:when test="$thisVisible= 'F'">
            	     <xsl:comment>skipping Hidden Coded Question</xsl:comment>
            	     <tr style="display:none"><td class="fieldName" valign="top"> 
		 		       	
    			<xsl:text disable-output-escaping="yes">
				<![CDATA[<span class="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[InputDisabledLabel" id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
				</td><td><xsl:text disable-output-escaping="yes"><![CDATA[<html:select name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[<html:optionsCollection property="codedValue(]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="key" label="value" /> </html:select> ]]></xsl:text></td></tr>
            
            
            	</xsl:when>   
            	      	
		 <xsl:when test="substring($thisControl,1,8) = 'Checkbox'"> 
			<xsl:comment>processing Checkbox Coded Question</xsl:comment>				
			<xsl:variable name="thisValSet" select="CodedQuestion/ValueSetName" />
			    <xsl:choose>
				<xsl:when test="$thisValSet= 'P_RACE_CAT' or $thisValSet= 'PHVS_RACECATEGORY_CDC_NULLFLAVOR'">
					<tr>
					<td class="fieldName" valign="top"> 
                			<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                			<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
                			<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
               				 </td>
					<td>
					<div id="patientRacesViewContainer">
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.americanIndianAlskanRace" value="1"><bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.africanAmericanRace" value="1"><bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.whiteRace" value="1"><bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.asianRace" value="1"><bean:message bundle="RVCT" key="rvct.asian"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.hawaiianRace" value="1"><bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.otherRace" value="1"><bean:message bundle="RVCT" key="rvct.other"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.refusedToAnswer" value="1"><bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.notAsked" value="1"><bean:message bundle="RVCT" key="rvct.notAsked"/>,</logic:equal>]]></xsl:text>
					<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.unKnownRace" value="1"><bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>]]></xsl:text>
					</div> 
					</td> </tr>
               	 		</xsl:when>  <!-- P_RACE_CAT -->
               	 	   <xsl:otherwise> <!-- default is Yes/No checkbox -->
               	 			<tr> <td class="fieldName" valign="top"> 
					<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
               				<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                			<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                			</td>
                			<td>
                			<xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="1">]]></xsl:text>
                			<xsl:text disable-output-escaping="yes"><![CDATA[Yes</logic:equal>]]></xsl:text>
                			<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEqual name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" value="1">]]></xsl:text>
                			<xsl:text disable-output-escaping="yes"><![CDATA[No</logic:notEqual>]]></xsl:text>
                			</td> </tr>
                	   </xsl:otherwise>
                      </xsl:choose>
            	 </xsl:when> <!-- end CheckBox --> 
            	<xsl:when test="substring($thisControl,1,11) = 'MultiSelect' or substring($thisControl,1,19) = 'ReadOnlyMultiSelect'">
                	<xsl:comment>processing Multi Coded Question</xsl:comment>
                	<xsl:comment>processing Coded Question</xsl:comment>
               	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr>]]></xsl:text>       	 	
                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top"> ]]></xsl:text>
                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]></xsl:text>
                	  <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answerArray(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
                	<xsl:choose>
                    	   <xsl:when test="$thisCodeset = 'S_JURDIC_C'">                    	  
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>]]></xsl:text></xsl:when>
                               <xsl:when test="$thisCodeset = 'STATE_CCD'">                    	  
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>]]></xsl:text></xsl:when>
                               <xsl:when test="$thisCodeset = 'S_PROGRA_C'">                    	  
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>]]></xsl:text></xsl:when>
                               <xsl:when test="$thisCodeset = 'O_NAICS'">                    	  
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.O_NAICS%>"/>]]></xsl:text></xsl:when>
                               <xsl:when test="$thisCodeset = 'P_LANG'">                    	  
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.P_LANG%>"/>]]></xsl:text></xsl:when> 
                               <xsl:otherwise>
                            <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text></xsl:otherwise>  
                       </xsl:choose>
                       <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>
		       <xsl:if test="CodedQuestion/OtherEntryAllowed">
		      <xsl:comment>Other allowed for this Coded Question</xsl:comment> 
		      <xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName" valign="top">]]></xsl:text>
		      <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
<xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth" />]]></xsl:text> <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)"/></td></tr>]]></xsl:text>
		   </xsl:if>
                    </xsl:when> <!-- end MultiSelect --> 
                    
                    <xsl:when test="substring($thisControl,1,18) = 'SingleSelectSearch'">
                    
				                    <xsl:comment>processing Coded With Search Question</xsl:comment>
				               	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr>]]></xsl:text>
				                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top">]]></xsl:text>
				                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
				                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" >]]></xsl:text>
				                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]>
				                	</xsl:text>  <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
				                	
				                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
				              
		                           <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" ]]></xsl:text>
		                           <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
				                      
				                   <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>
				                  <!-- <xsl:if test="CodedQuestion/OtherEntryAllowed">
						      <xsl:comment>Other allowed for this Coded Question</xsl:comment> 
						      <xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName" valign="top">]]></xsl:text>
						      <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
						     <xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth" />]]></xsl:text> <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)"/></td></tr>]]></xsl:text>
						   </xsl:if>-->
                    
                    </xsl:when>
            	<xsl:otherwise>
                	<xsl:comment>processing Coded Question</xsl:comment>
               	 	<xsl:text disable-output-escaping="yes"><![CDATA[<tr>]]></xsl:text>
                	<xsl:text disable-output-escaping="yes"><![CDATA[<td class="fieldName" valign="top">]]></xsl:text>
                	<xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                	<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" >]]></xsl:text>
                	<xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td><td>]]>
                	</xsl:text>  <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
                	
                	<xsl:variable name="thisCodeset" select="CodedQuestion/ValueSetName" />
                	<xsl:choose>
                    	   <xsl:when test="$thisCodeset = 'S_JURDIC_C'">    
                    	      <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>]]></xsl:text>
                           </xsl:when>
                           <xsl:when test="$thisCodeset = 'STATE_CCD'">
                              <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>]]></xsl:text>
                           </xsl:when>
                           <xsl:when test="$thisCodeset = 'S_PROGRA_C'">
                              <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>]]></xsl:text>
                           </xsl:when>
                           <xsl:when test="$thisCodeset = 'COUNTY_CCD'">
								<xsl:choose>
									<xsl:when test="CodedQuestion/SourceField">
										<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)">]]></xsl:text>
										<xsl:text disable-output-escaping="yes"><![CDATA[<logic:notEmpty name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/SourceField" /><xsl:text disable-output-escaping="yes"><![CDATA[)">]]></xsl:text>
										<xsl:text disable-output-escaping="yes"><![CDATA[<bean:define id="value" name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/SourceField" /><xsl:text disable-output-escaping="yes"><![CDATA[)"/>]]></xsl:text>
										<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" methodNm="CountyCodes" methodParam="${]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_STATE}"/>]]></xsl:text>
										<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
										<xsl:text disable-output-escaping="yes"><![CDATA[</logic:notEmpty>]]></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp" /><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName" /><xsl:text disable-output-escaping="yes"><![CDATA[)" methodNm="CountyCodes" methodParam="${]]></xsl:text><xsl:copy-of select="$theForm" /><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[_STATE}"/>]]></xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
                           <xsl:when test="$thisCodeset = 'O_NAICS'">
                              <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.O_NAICS%>"/>]]></xsl:text>
                           </xsl:when>
                           <xsl:when test="$thisCodeset = 'P_LANG'">
                              <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"]]></xsl:text>
                              <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="<%=NEDSSConstants.P_LANG%>"/>]]></xsl:text>
                           </xsl:when>
                           <xsl:otherwise>
                           <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" ]]></xsl:text>
                           <xsl:text disable-output-escaping="yes"><![CDATA[codeSetNm="]]></xsl:text><xsl:value-of select="CodedQuestion/ValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text></xsl:otherwise>
                       </xsl:choose>
                   <xsl:text disable-output-escaping="yes"> <![CDATA[ </td> </tr> ]]></xsl:text>
                   <xsl:if test="CodedQuestion/OtherEntryAllowed">
		      <xsl:comment>Other allowed for this Coded Question</xsl:comment> 
		      <xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName" valign="top">]]></xsl:text>
		      <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="CodedQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[OthL">Other ]]></xsl:text><xsl:value-of select="CodedQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span></td>]]></xsl:text>
		     <xsl:text disable-output-escaping="yes"><![CDATA[<td> <span id="]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth" />]]></xsl:text> <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="CodedQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Oth)"/></td></tr>]]></xsl:text>
		   </xsl:if>
                </xsl:otherwise>
                </xsl:choose>
            </xsl:if>  <!-- end CodedQuestion -->
                <xsl:if test="NumericQuestion">
                <xsl:variable name="thisReq" select="NumericQuestion/Required" />
                <xsl:variable name="thisVisible" select="NumericQuestion/Visible" />
                <xsl:choose>
		<xsl:when test="$thisVisible= 'F'">
			<xsl:comment>skipping Hidden Numeric Question</xsl:comment>
		</xsl:when>
		<xsl:otherwise> 
		<xsl:comment>processing Numeric Question</xsl:comment>
                <xsl:text disable-output-escaping="yes"><![CDATA[<tr><td class="fieldName" valign="top">]]></xsl:text>
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="NumericQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                <xsl:value-of select="NumericQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[</td><td>]]></xsl:text>
                 <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
                <xsl:if test="NumericQuestion/RelatedUnitsValueSetName">
                <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[UNIT" />]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="NumericQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Unit)" codeSetNm="]]></xsl:text><xsl:value-of select="NumericQuestion/RelatedUnitsValueSetName"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
</xsl:if>
                <xsl:text disable-output-escaping="yes"><![CDATA[</td></tr>]]></xsl:text>
                </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:if test="DateQuestion">
              	<xsl:variable name="thisReq" select="DateQuestion/Required" />
				<xsl:variable name="thisDisabled" select="DateQuestion/Disabled" />
				<xsl:variable name="thisVisible" select="DateQuestion/Visible" />
				<xsl:variable name="thisFuture" select="DateQuestion/AllowFutureDate" />
				<xsl:variable name="thisControl" select="DateQuestion/ControlType" />
             <xsl:choose>
				<xsl:when test="$thisVisible= 'F' and $SectionAllowBatchEntry= 'E'">
						<tr style="display:none"><td class="fieldName"> 
								<xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
								<xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
			               		</td>
			              		<td>
			                      	<xsl:text disable-output-escaping="yes"><![CDATA[<html:text name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" maxlength="10" size="10" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
								<xsl:text disable-output-escaping="yes"><![CDATA[<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate(']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon');]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[return false;" styleId="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon"]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[ onkeypress="showCalendarEnterKey(']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[',']]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Icon',event);" ></html:img>]]></xsl:text>
								</td> 
						</tr>
						</xsl:when>
						<xsl:when test="$thisVisible= 'F'">
							<xsl:comment>skipping Hidden Date Question</xsl:comment>
						</xsl:when>
		<xsl:otherwise> 
		<xsl:comment>processing Date Question</xsl:comment>
                <tr><td class="fieldName" valign="top"> 
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="DateQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text><xsl:value-of select="DateQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                 </td><td>
                 <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="DateQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
                </td></tr> 
                </xsl:otherwise>
		</xsl:choose>  
            </xsl:if>
            <xsl:if test="ParticipationQuestion">
                <xsl:variable name="thisReq" select="ParticipationQuestion/Required" />
                <xsl:variable name="thisVisible" select="ParticipationQuestion/Visible" />
                <xsl:choose>
		<xsl:when test="$thisVisible= 'F'">
		        <xsl:comment>skipping Hidden Participation Question</xsl:comment>
                </xsl:when>			
		<xsl:otherwise> 
		<xsl:comment>processing Participation Question</xsl:comment>
                <tr>
                <td class="fieldName" valign="top"> 
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ParticipationQuestion/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text>
                <xsl:value-of select="ParticipationQuestion/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                   </td>
                   <td>
                   <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"/> ]]></xsl:text>
                   <xsl:choose>  
					<xsl:when test="$SectionAllowBatchEntry= 'Y'">
                   		<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><![CDATA[Disp"]]><xsl:text disable-output-escaping="yes"><![CDATA[/>]]></xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                    	<xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">${]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[.attributeMap.]]></xsl:text><xsl:value-of select="ParticipationQuestion/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[SearchResult}</span>]]></xsl:text>
                    </xsl:otherwise>
                   </xsl:choose>
                </td> </tr>
                </xsl:otherwise>
		</xsl:choose>  
            </xsl:if>
             <xsl:if test="TextArea">   <!-- Note that TextArea is similar to Text on the View Page -->
                <xsl:variable name="thisReq" select="TextArea/Required" />
                <xsl:variable name="thisVisible" select="TextArea/Visible" />
				<xsl:variable name="thisDisabled" select="TextArea/Disabled" />
				<xsl:variable name="thisControl" select="TextArea/ControlType" />
    <xsl:choose>
		<xsl:when test="$thisVisible= 'F'">
			<xsl:comment>skipping Hidden TextArea</xsl:comment>
        </xsl:when>
        <xsl:when test="$SectionAllowBatchEntry= 'E'">
        <xsl:comment>processing editable TextArea</xsl:comment>
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
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:textarea styleClass="requiredInputField]]></xsl:text><xsl:if test="$SectionAllowBatchEntry= 'E'"><xsl:value-of select="$SubSectionId"/></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" style="WIDTH: 500px; HEIGHT: 100px;" name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId ="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="TextArea/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextArea/OnKeyUp"/></xsl:if><xsl:if test="TextAea/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextArea/OnKeyDown"/></xsl:if><xsl:if test="TextArea/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextArea/OnBlur"/></xsl:if><xsl:if test="$thisControl='RollingNote' and $SectionAllowBatchEntry='E'"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="TextArea/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text></xsl:when>
				<xsl:otherwise>
					<xsl:text disable-output-escaping="yes"><![CDATA[<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)" styleId ="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:if test="$thisDisabled= 'disabled'"><xsl:text disable-output-escaping="yes"><![CDATA[" disabled="true]]></xsl:text></xsl:if><xsl:if test="TextArea/OnKeyUp"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeyup="]]></xsl:text><xsl:value-of select="TextArea/OnKeyUp"/></xsl:if><xsl:if test="TextAea/OnKeyDown"><xsl:text disable-output-escaping="yes"><![CDATA[" onkeydown="]]></xsl:text><xsl:value-of select="TextArea/OnKeyDown"/></xsl:if><xsl:if test="TextArea/OnBlur"><xsl:text disable-output-escaping="yes"><![CDATA[" onblur="]]></xsl:text><xsl:value-of select="TextArea/OnBlur"/></xsl:if><xsl:if test="$thisControl='RollingNote' and $SectionAllowBatchEntry='E'"><xsl:text disable-output-escaping="yes"><![CDATA[" onchange="]]></xsl:text><xsl:value-of select="TextArea/OnChange"/><xsl:text disable-output-escaping="yes"><![CDATA[;unhideBatchImg(']]></xsl:text><xsl:value-of select="$SubSectionId"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text></xsl:if><xsl:text disable-output-escaping="yes"><![CDATA[" title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
				</xsl:otherwise>
				</xsl:choose>
   				</td> </tr>
   			</xsl:when>
 		<xsl:otherwise>
 		<xsl:comment>processing TextArea</xsl:comment>
                <tr> <td class="fieldName" valign="top"> 
                <xsl:if test="$thisReq= 'T'"><xsl:text disable-output-escaping="yes"><![CDATA[<span style="color:#CC0000">*</span>]]></xsl:text></xsl:if> 
                <xsl:text disable-output-escaping="yes"><![CDATA[<span title="]]></xsl:text><xsl:value-of select="TextArea/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[" id="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L">]]></xsl:text>
                <xsl:value-of select="TextArea/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
                </td>
                <td>
                 <xsl:text disable-output-escaping="yes"><![CDATA[<span id="]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<nedss:view name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="]]></xsl:text><xsl:copy-of select="$theProp"/><xsl:text disable-output-escaping="yes"><![CDATA[.answer(]]></xsl:text><xsl:value-of select="TextArea/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[)"  />]]></xsl:text>
                </td> </tr> 
                </xsl:otherwise>
		</xsl:choose>  
			<xsl:if test="$thisControl= 'RollingNote' and $SectionAllowBatchEntry= 'E' ">			
				<xsl:comment>Adding Hidden Date and User fields for Batch Rolling Note</xsl:comment>
			</xsl:if>
            </xsl:if>
             <xsl:if test="StaticLink">  
                <xsl:comment>processing Hyperlink</xsl:comment>
                <tr><td align="left" colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;<a href="]]></xsl:text><xsl:value-of select="StaticLink/LinkValue"/><xsl:text disable-output-escaping="yes"><![CDATA[" TARGET="_blank">]]></xsl:text><xsl:value-of select="StaticLink/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text></td></tr>           
            </xsl:if>
            
            
            <xsl:if test="ActionButton">  
             <xsl:comment>processing Action Button</xsl:comment>
             <tr><td class="fieldName"> 
				<xsl:text disable-output-escaping="yes"><![CDATA[<span class="]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[InputFieldLabel" id="]]></xsl:text><xsl:text disable-output-escaping="yes"></xsl:text><xsl:value-of select="ActionButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[L" title="]]></xsl:text><xsl:value-of select="ActionButton/ToolTip"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text><xsl:value-of select="ActionButton/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[:</span> ]]></xsl:text>
          		</td>
          		<td align="left">
          		<xsl:text disable-output-escaping="yes"><![CDATA[<html:hidden property="attributeMap.]]></xsl:text><xsl:value-of select="ActionButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Uid"/>]]></xsl:text>
          		<xsl:text disable-output-escaping="yes"><![CDATA[<input disabled="" id="]]></xsl:text><xsl:value-of select="ActionButton/QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[Button" onclick="OpenForm(']]></xsl:text><xsl:value-of select="ActionButton/QuestionDefaultValue"/><xsl:text disable-output-escaping="yes"><![CDATA[',this);]]></xsl:text><xsl:value-of select="StaticLink/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA[" type="button" value="Manage ]]></xsl:text><xsl:value-of select="ActionButton/DisplayLabel"/><xsl:text disable-output-escaping="yes"><![CDATA["/>]]></xsl:text></td></tr>
             </xsl:if>
             
             
             <xsl:if test="StaticComment">  
                <xsl:comment>processing Static Comment</xsl:comment>
                <tr><td align="left" colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;]]></xsl:text><xsl:value-of select="StaticComment/CommentText"/></td></tr>           
            </xsl:if>
             <xsl:if test="StaticInfoBar">  
                <xsl:comment>processing Static Line Separator</xsl:comment>
                <tr><td align="left" colspan="2"><hr/></td></tr>           
            </xsl:if>
            <xsl:if test="StaticParticipantList">
            <xsl:comment>processing Static Participant List</xsl:comment>
           	<xsl:text disable-output-escaping="yes"><![CDATA[<tr><td style="padding:0.5em;" colspan="2"><display:table name="participantList" class="dtTable"><display:column property="title" title="Role" style="width:25%;" /><display:column property="detail" title="Detail" style="width:75%;"/><display:setProperty name="basic.empty.showtable" value="true"/></display:table></td></tr>]]></xsl:text>
            </xsl:if>
            <xsl:if test="OrigDocList">
            <xsl:comment>processing Original Electronic Document List</xsl:comment>
           	<xsl:text disable-output-escaping="yes"><![CDATA[<tr><td style="padding:0.5em;" colspan="2"><display:table name="origDocList" class="dtTable"><display:column property="viewLink" title="Add Time" style="width:50%;" /><display:column property="versionNbr" title="Version" style="width:50%;"/><display:setProperty name="basic.empty.showtable" value="true"/></display:table></td></tr>]]></xsl:text>
            </xsl:if>
            <xsl:if test="$isHIVElement = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[</logic:equal>]]></xsl:text>
			</xsl:if>
            </xsl:for-each> <!-- Page Element -->  
            <xsl:choose>
            <xsl:when test="$SectionAllowBatchEntry= 'E'">       
			<xsl:if test="$hasHIVQuestions = 'Y'">
			   <xsl:text disable-output-escaping="yes"><![CDATA[<logic:equal name="]]></xsl:text><xsl:copy-of select="$theForm"/><xsl:text disable-output-escaping="yes"><![CDATA[" property="securityMap.hasHIVPermissions" value="T">]]></xsl:text>
			</xsl:if>
           <xsl:text disable-output-escaping="yes"><![CDATA[  
			<tr id="AddButtonToggle]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[">
			<td colspan="2" align="right">
			<input type="button" value="     Add     "  onclick="if (pg]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[BatchAddFunction()) writeQuestion(']]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','pattern]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[','questionbody]]></xsl:text><xsl:value-of select="QuestionUniqueName"/><xsl:text disable-output-escaping="yes"><![CDATA[')"/>&nbsp;&nbsp;
			&nbsp;
			</td>
			
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
