<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page import="gov.cdc.nedss.util.*"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants, gov.cdc.nedss.util.PropertyUtil" %>

<%
String []sectionNames = {"Investigation Information", "Reporting Information", "Clinical Information",
                           "Laboratory Information", "Vaccine Information", "Vaccination Record",
                            "Epidemiologic Information", "Investigation Comments", "Custom Fields"};
 int  sectionIndex = (new Integer(request.getParameter("param1").toString())).intValue();
 int  subSectionIndex= (new Integer(request.getParameter("param2").toString())).intValue();
 String sectionId = HTMLEncoder.encodeHtml(request.getParameter("param3").toString());
%> 
<!--Section:Epidemiologic Information-->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex] %>" classType="sect">
<!-- SUB_SECTION : Patient History: -->
 <nedss:container id='<%= (sectionNames[sectionIndex++].replaceAll(" ", "")) + (++subSectionIndex) %>' name="Patient History" classType="subSect" >
    <!--42. Has this patient ever been diagnosed with varicella before:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR150.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR150.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR150.tooltip}">${PamForm.formFieldMap.VAR150.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR150)" codeSetNm="${PamForm.formFieldMap.VAR150.codeSetNm}"/>	
	 	</td>
	 </tr>

<!--Age at Diagnosis:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR151.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR151.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR151.tooltip}">${PamForm.formFieldMap.VAR151.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR151)"/>
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR212)" codeSetNm="${PamForm.formFieldMap.VAR212.codeSetNm}"/>
	     </td>
	 </tr>
	 <!--43. Previous case diagnosed by:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR152.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR152.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR152.tooltip}">${PamForm.formFieldMap.VAR152.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR152)"  codeSetNm="${PamForm.formFieldMap.VAR152.codeSetNm}"/>	
	 	</td>
	 </tr>
	 <!--Specify Other:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR153.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR153.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR153.tooltip}">${PamForm.formFieldMap.VAR153.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR153)"/>		 	</td>
	 </tr>
	 <!--44. Where was the patient born::-->
		<tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR215.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR215.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR215.tooltip}">${PamForm.formFieldMap.VAR215.label}</span>  
		     </td>
		     <td> 
			     <nedss:view name="PamForm" property="pamClientVO.answer(VAR215)" codeSetNm="<%=NEDSSConstants.COUNTRY_LIST%>"/>
		 	</td>
		 </tr>
		 <!-- Patient History: -->
	     <%= request.getAttribute("2187") == null ? "" :  request.getAttribute("2187") %>  
</nedss:container>
<!-- SUB SECTION - Epi-Link-->
<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Epi-Link:" classType="subSect" >
	 <!--45. Is this case epi-linked to another confirmed or probable case:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR154.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR154.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR154.tooltip}">${PamForm.formFieldMap.VAR154.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR154)" codeSetNm="${PamForm.formFieldMap.VAR154.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--Type of case this case is epi-linked to:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR155.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR155.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR155.tooltip}">${PamForm.formFieldMap.VAR155.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR155)" codeSetNm="${PamForm.formFieldMap.VAR155.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--46. Transmission Setting (Setting of Exposure)::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR156.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR156.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR156.tooltip}">${PamForm.formFieldMap.VAR156.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR156)" codeSetNm="${PamForm.formFieldMap.VAR156.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--Specify Other Transmission Setting::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR157.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR157.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR157.tooltip}">${PamForm.formFieldMap.VAR157.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR157)"/>		 	</td>
	 </tr>
<!--47. Is this case a healthcare worker::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR158.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR158.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR158.tooltip}">${PamForm.formFieldMap.VAR158.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR158)" codeSetNm="${PamForm.formFieldMap.VAR158.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--48. Is this case part of an outbreak of 5 or more cases:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV150.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV150.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV150.tooltip}">${PamForm.formFieldMap.INV150.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(INV150)" codeSetNm="${PamForm.formFieldMap.INV150.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--Outbreak Name:-->
		<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV151.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV151.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV151.tooltip}">${PamForm.formFieldMap.INV151.label}</span>  
	     </td>
	     <td> 	  
		 <nedss:view name="PamForm" property="pamClientVO.answer(INV151)" codeSetNm="${PamForm.formFieldMap.INV151.codeSetNm}"/>	
	 	</td>
	 </tr>
	 <!-- Is this person associated with a day care facility:-->
	<tr class="${PamForm.formFieldMap.INV148.state.visibleString}">
		<td class="fieldName">
			<span style="${PamForm.formFieldMap.INV148.state.requiredIndClass}">*</span>
			<span style="${PamForm.formFieldMap.INV148.errorStyleClass}" title="${PamForm.formFieldMap.INV148.tooltip}">${PamForm.formFieldMap.INV148.label}</span>
		</td>
		<td>
			<nedss:view name="PamForm" property="pamClientVO.answer(INV148)" codeSetNm="${PamForm.formFieldMap.INV148.codeSetNm}"/>
		</td>
	</tr>
	 <!-- Is this person a food handler:-->
	<tr class="${PamForm.formFieldMap.INV149.state.visibleString}">
		<td class="fieldName">
			<span style="${PamForm.formFieldMap.INV149.state.requiredIndClass}">*</span>
			<span style="${PamForm.formFieldMap.INV149.errorStyleClass}" title="${PamForm.formFieldMap.INV149.tooltip}">${PamForm.formFieldMap.INV149.label}</span>
		</td>
		<td>
			<nedss:view name="PamForm" property="pamClientVO.answer(INV149)" codeSetNm="${PamForm.formFieldMap.INV149.codeSetNm}"/>
		</td>
	</tr>
	 <!-- Epi-Link:-->
     <%= request.getAttribute("2194") == null ? "" :  request.getAttribute("2194") %>  
     	</nedss:container>
	<!--SUB_SECTION : Disease Aquisition-->
	 <div class='${PamForm.formFieldMap["2322"].state.visibleString}'>
	<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Disease Acquisition:" classType="subSect" >
	<jsp:include page="/pam/ext/view/DiseaseAcquisition.jsp"/>
	<%= request.getAttribute("2322") == null ? "" :  request.getAttribute("2322") %>  
	</nedss:container>
	</div>
	<!-- SUB_SECTION : Case Status: -->
	<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Case Status" classType="subSect" >
	<jsp:include page="/pam/ext/view/CaseStatus.jsp"/>	
	<!--49. Case Status::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV163.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV163.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV163.tooltip}">${PamForm.formFieldMap.INV163.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(INV163)" codeSetNm="${PamForm.formFieldMap.INV163.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--50. MMWR Week:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV165.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV165.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV165.tooltip}">${PamForm.formFieldMap.INV165.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(INV165)"/>	 	</td>
	 </tr>
	  <!--51. MMWR Year:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV166.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV166.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV166.tooltip}">${PamForm.formFieldMap.INV166.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(INV166)"/>	
	 	</td>
	 </tr>
	 <!-- Case Status: -->
     <%= request.getAttribute("2202") == null ? "" :  request.getAttribute("2202") %>  
</nedss:container>
<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Pregnant Women" classType="subSect" >
	<!--52. Was the patient pregnant during this varicella illness:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV178.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV178.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV178.tooltip}">${PamForm.formFieldMap.INV178.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(INV178)" codeSetNm="${PamForm.formFieldMap.INV178.codeSetNm}"/>	
	 	</td>
	 </tr>
<!--Number of weeks gestation at onset of illness (1-45 weeks):-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR159.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR159.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR159.tooltip}">${PamForm.formFieldMap.VAR159.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR159)"/></td>
	 </tr>
	  <!--Trimester at Onset of Illness:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR160.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR160.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR160.tooltip}">${PamForm.formFieldMap.VAR160.label}</span>  
	     </td>
	     <td> 
	     <nedss:view name="PamForm" property="pamClientVO.answer(VAR160)" codeSetNm="${PamForm.formFieldMap.VAR160.codeSetNm}"/>	
	 	</td>
	 </tr>
	 <!-- Pregnant women: -->
     <%= request.getAttribute("2206") == null ? "" :  request.getAttribute("2206") %>  
</nedss:container>

</nedss:container>
<!--Investigation Comments-->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">

    <!-- SUB_SECTION : Comments: -->
    <nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Comments" classType="subSect" >
    		<tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV167.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV167.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV167.tooltip}">${PamForm.formFieldMap.INV167.label}                    
                </td>
                <td>
                <nedss:view name="PamForm" property="pamClientVO.answer(INV167)"/>	
                </td>
            </tr>
            <!--Comments: -->
            <%= request.getAttribute("2211") == null ? "" :  request.getAttribute("2211") %>  
    </nedss:container>
</nedss:container>

<!-- SECTION : Local Fields -->
<% if(request.getAttribute("2264") != null) { %>
 <nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
     <!-- SUB_SECTION :  Local Fields -->
     <nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
     		<%= request.getAttribute("2264") == null ? "" :  request.getAttribute("2264") %>
     </nedss:container>
</nedss:container>
 <%} %>


 
 <div class="tabNavLinks">
     <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
     <a href="javascript:navigateTab('next')"> Next </a>
 </div>    
</div> <!-- view -->