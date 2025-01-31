<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.*"%>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<%
String []sectionNames = {"Investigation Information", "Reporting Information", "Clinical Information",
                           "Laboratory Information", "Vaccine Information", "Vaccination Record",
                            "Epidemiologic Information", "Investigation Comments", "Custom Fields"};
 int  sectionIndex = (new Integer(request.getParameter("param1").toString())).intValue();
 int  subSectionIndex= (new Integer(request.getParameter("param2").toString())).intValue();
 String sectionId = HTMLEncoder.encodeHtml(request.getParameter("param3").toString());
%> 

<!-- SECTION : Vaccine Information --> 
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">

<!-- SUBSECTION :Varicella-Containing Vaccine Information: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Varicella-Containing Vaccine Information" classType="subSect" >
 <!--38. Did the patient receive Varicellacontaining vaccine::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR101.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR101.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR101.tooltip}">${PamForm.formFieldMap.VAR101.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR101.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR101.state.disabled}" property="pamClientVO.answer(VAR101)" styleId="VAR101" onchange="fireRule('VAR101',this)">
                 <html:optionsCollection property="codedValue(VAR101)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
 <!--Reason why patient did not receive varicella-containing vaccine:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR145.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR145.state.disabledString}"  id="VAR145L" style="${PamForm.formFieldMap.VAR145.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR145.tooltip}">${PamForm.formFieldMap.VAR145.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR145.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR145.state.disabled}" property="pamClientVO.answer(VAR145)" styleId="VAR145" onchange="fireRule('VAR145',this)">
                 <html:optionsCollection property="codedValue(VAR145)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other Reason::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR146.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR146.state.disabledString}"  id="VAR146L" style="${PamForm.formFieldMap.VAR146.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR146.tooltip}">${PamForm.formFieldMap.VAR146.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR146.state.disabled}" property="pamClientVO.answer(VAR146)" maxlength="100" size="25" styleId="VAR146" onblur="fireRule('VAR146',this)"
	     		title="${PamForm.formFieldMap.VAR146.label}"/> 
	 	</td>
	 </tr>
<!--39. Number of doses received on or after first birthday:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR147.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR147.state.disabledString}"  id="VAR147L" style="${PamForm.formFieldMap.VAR147.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR147.tooltip}">${PamForm.formFieldMap.VAR147.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR147.state.disabled}" property="pamClientVO.answer(VAR147)" maxlength="1" size="2" styleId="VAR147" onblur="fireRule('VAR147',this)"
	     		title="${PamForm.formFieldMap.VAR147.label}" onkeyup="isNumericCharacterEntered(this)"/> 
	 	</td>
	 </tr>
<!--40. Reason patient is >= 6 years old and received one dose on or after 6th birthday but never received second dose::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR162.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR162.state.disabledString}"  id="VAR162L" style="${PamForm.formFieldMap.VAR162.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR162.tooltip}">${PamForm.formFieldMap.VAR162.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR162.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR162.state.disabled}" property="pamClientVO.answer(VAR162)" styleId="VAR162" onchange="fireRule('VAR162',this)" >
                 <html:optionsCollection property="codedValue(VAR162)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other Reason:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR149.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR149.state.disabledString}"  id="VAR149L" style="${PamForm.formFieldMap.VAR149.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR149.tooltip}">${PamForm.formFieldMap.VAR149.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR149.state.disabled}" property="pamClientVO.answer(VAR149)" maxlength="100" size="25" styleId="VAR149" onblur="fireRule('VAR149',this)"
	     	      		title="${PamForm.formFieldMap.VAR149.label}"/></td>
	 </tr>
	 <!-- Varicella-Containing Vaccine Information: -->
     <%= request.getAttribute("2177") == null ? "" :  request.getAttribute("2177") %>  
</nedss:container>
</nedss:container>
<!-- SECTION : Vaccination Record: --> 
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">

<!-- SUBSECTION :Varicella-Containing Vaccine Information: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Vaccine 1" classType="subSect" >
 <!--Vaccination Date:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR216.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR216.state.disabledString}"  id="VAR216L" style="${PamForm.formFieldMap.VAR216.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR216.tooltip}">${PamForm.formFieldMap.VAR216.label}</span>  
	     </td>
	     <td> 
	     	  <html:text  name="PamForm" title="${PamForm.formFieldMap.VAR216.label}" disabled ="${PamForm.formFieldMap.VAR216.state.disabled}" property="pamClientVO.answer(VAR216)" styleId="VAR216" onblur="fireRule('VAR216',this)"
 maxlength="10" 
	                      onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR216',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR216','VAR216con'); return false;"  onkeypress ="showCalendarEnterKey('VAR216','VAR216con',event);" 
	                     styleId="VAR216con"></html:img>
	 	</td>
	 </tr>
 
<!--Vaccine Type::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR217.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR217.state.disabledString}"  id="VAR217L"
                   style="${PamForm.formFieldMap.VAR217.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR217.tooltip}">${PamForm.formFieldMap.VAR217.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR127.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR217.state.disabled}"  property="pamClientVO.answer(VAR217)" styleId="VAR217" onchange="fireRule('VAR217',this)"> 
                 <html:optionsCollection property="codedValue(VAR217)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Manufacturer:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR218.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR218.state.disabledString}"  id="VAR218L"
                  style="${PamForm.formFieldMap.VAR218.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR218.tooltip}">${PamForm.formFieldMap.VAR218.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR218.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR218.state.disabled}"  property="pamClientVO.answer(VAR218)" styleId="VAR218" onchange="fireRule('VAR218',this)">
                 <html:optionsCollection property="codedValue(VAR218)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Lot Number::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR219.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR219.state.disabledString}"  id="VAR219L"
                     style="${PamForm.formFieldMap.VAR219.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR219.tooltip}">${PamForm.formFieldMap.VAR219.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR219.state.disabled}" property="pamClientVO.answer(VAR219)" maxlength="50" styleId="VAR219" onblur="fireRule('VAR219',this)"
	     		title="${PamForm.formFieldMap.VAR219.label}"/></td>
	 </tr>
	 <!-- Vaccine 1: -->
     <%= request.getAttribute("2185") == null ? "" :  request.getAttribute("2185") %>  
</nedss:container>
<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Vaccine 2" classType="subSect" >
 <!--Vaccination Date:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR220.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR220.state.disabledString}"  id="VAR220L"
                     style="${PamForm.formFieldMap.VAR220.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR220.tooltip}">${PamForm.formFieldMap.VAR220.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR220.label}" disabled ="${PamForm.formFieldMap.VAR220.state.disabled}" property="pamClientVO.answer(VAR220)" maxlength="10" 
	                     styleId="VAR220" onblur="fireRule('VAR220',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR220',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR220','VAR220con'); return false;" onkeypress ="showCalendarEnterKey('VAR220','VAR220con',event);" 
	                     styleId="VAR220con"></html:img>
	 	</td>
	 </tr>
 
<!--Vaccine Type::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR221.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR221.state.disabledString}"  id="VAR221L"
                    style="${PamForm.formFieldMap.VAR221.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR221.tooltip}">${PamForm.formFieldMap.VAR221.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR221.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR221.state.disabled}" property="pamClientVO.answer(VAR221)" styleId="VAR221" onchange="fireRule('VAR221',this)"> 
                 <html:optionsCollection property="codedValue(VAR221)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Manufacturer:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR222.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR222.state.disabledString}"  id="VAR222L"
                     style="${PamForm.formFieldMap.VAR222.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR222.tooltip}">${PamForm.formFieldMap.VAR222.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR222.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR222.state.disabled}"  property="pamClientVO.answer(VAR222)" styleId="VAR222" onchange="fireRule('VAR222',this)" >
                 <html:optionsCollection property="codedValue(VAR222)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Lot Number::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR223.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR223.state.disabledString}"  id="VAR223L" style="${PamForm.formFieldMap.VAR223.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR223.tooltip}">${PamForm.formFieldMap.VAR223.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR223.state.disabled}" property="pamClientVO.answer(VAR223)" styleId="VAR223" onblur="fireRule('VAR223',this)" maxlength="50" 
	     		title="${PamForm.formFieldMap.VAR223.label}"/>	 	</td>
	 </tr>
	 <!-- Vaccine 2: -->
     <%= request.getAttribute("2231") == null ? "" :  request.getAttribute("2231") %>  
</nedss:container>
<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Vaccine 3" classType="subSect" >
 <!--Vaccination Date:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR224.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR224.state.disabledString}"  id="VAR224L"  style="${PamForm.formFieldMap.VAR224.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR224.tooltip}">${PamForm.formFieldMap.VAR224.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR224.label}" disabled ="${PamForm.formFieldMap.VAR224.state.disabled}" property="pamClientVO.answer(VAR224)" maxlength="10" 
	                     styleId="VAR224" onblur="fireRule('VAR224',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR224',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR224','VAR224con'); return false;" onkeypress ="showCalendarEnterKey('VAR224','VAR224con',event);" 
	                     styleId="VAR224con"></html:img>
	 	</td>
	 </tr>
 
<!--Vaccine Type::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR225.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR225.state.disabledString}"  id="VAR225L" style="${PamForm.formFieldMap.VAR225.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR225.tooltip}">${PamForm.formFieldMap.VAR225.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR225.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR225.state.disabled}" property="pamClientVO.answer(VAR225)" styleId="VAR225" onchange="fireRule('VAR225',this)"> 
                 <html:optionsCollection property="codedValue(VAR225)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Manufacturer:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR226.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR226.state.disabledString}"  id="VAR226L"
 style="${PamForm.formFieldMap.VAR226.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR226.tooltip}">${PamForm.formFieldMap.VAR226.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR226.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR226.state.disabled}" property="pamClientVO.answer(VAR226)" styleId="VAR226" onchange="fireRule('VAR226',this)"> 
                 <html:optionsCollection property="codedValue(VAR226)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Lot Number::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR227.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR227.state.disabledString}"  id="VAR227L"
                style="${PamForm.formFieldMap.VAR227.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR227.tooltip}">${PamForm.formFieldMap.VAR227.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR227.state.disabled}" property="pamClientVO.answer(VAR227)" styleId="VAR227"  onblur="fireRule('VAR227',this)" maxlength="50" 
	     		title="${PamForm.formFieldMap.VAR227.label}"/></td>
	 </tr>
	 <!-- Vaccine 3: -->
     <%= request.getAttribute("2232") == null ? "" :  request.getAttribute("2232") %>  
</nedss:container>
<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Vaccine 4" classType="subSect" >
 <!--Vaccination Date:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR228.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR228.state.disabledString}"  id="VAR228L"
                     style="${PamForm.formFieldMap.VAR228.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR228.tooltip}">${PamForm.formFieldMap.VAR228.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR228.label}" disabled ="${PamForm.formFieldMap.VAR228.state.disabled}" property="pamClientVO.answer(VAR228)" maxlength="10" 
	                     styleId="VAR228" onblur="fireRule('VAR228',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR228',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR228','VAR228con'); return false;" onkeypress ="showCalendarEnterKey('VAR228','VAR228con',event);" 
	                     styleId="VAR228con"></html:img>
	 	</td>
	 </tr>
 
<!--Vaccine Type::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR229.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR229.state.disabledString}"  id="VAR229L"
                      style="${PamForm.formFieldMap.VAR229.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR229.tooltip}">${PamForm.formFieldMap.VAR229.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR229.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR229.state.disabled}" property="pamClientVO.answer(VAR229)" styleId="VAR229"  onchange="fireRule('VAR229',this)"> 
                 <html:optionsCollection property="codedValue(VAR229)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Manufacturer:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR230.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR230.state.disabledString}"  id="VAR230L"
                  style="${PamForm.formFieldMap.VAR230.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR230.tooltip}">${PamForm.formFieldMap.VAR230.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR230.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR230.state.disabled}" property="pamClientVO.answer(VAR230)" styleId="VAR230"  onchange="fireRule('VAR230',this)">
                 <html:optionsCollection property="codedValue(VAR230)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Lot Number::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR231.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR231.state.disabledString}"  id="VAR231L"
                        style="${PamForm.formFieldMap.VAR231.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR231.tooltip}">${PamForm.formFieldMap.VAR231.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR231.state.disabled}" property="pamClientVO.answer(VAR231)" styleId="VAR231"  maxlength="50" onblur="fireRule('VAR231',this)"
	     		title="${PamForm.formFieldMap.VAR231.label}"/>	 	</td>
	 </tr>
	 <!-- Vaccine 4: -->
     <%= request.getAttribute("2233") == null ? "" :  request.getAttribute("2233") %>  
</nedss:container>
<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Vaccine 5" classType="subSect" >
 <!--Vaccination Date:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR232.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR232.state.disabledString}"  id="VAR232L"
                   style="${PamForm.formFieldMap.VAR232.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR232.tooltip}">${PamForm.formFieldMap.VAR232.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR232.label}" disabled ="${PamForm.formFieldMap.VAR232.state.disabled}"  property="pamClientVO.answer(VAR232)" styleId="VAR232" onblur="fireRule('VAR232',this)" maxlength="10" 
	                    onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR232',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR232','VAR232con'); return false;"  onkeypress ="showCalendarEnterKey('VAR232','VAR232con',event);" 
	                     styleId="VAR232con"></html:img>
	 	</td>
	 </tr>
 
<!--Vaccine Type::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR233.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR233.state.disabledString}"  id="VAR233L"
                     style="${PamForm.formFieldMap.VAR233.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR233.tooltip}">${PamForm.formFieldMap.VAR233.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR233.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR233.state.disabled}" property="pamClientVO.answer(VAR233)" styleId="VAR233" onchange="fireRule('VAR233',this)"> 
                 <html:optionsCollection property="codedValue(VAR233)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Manufacturer:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR234.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR234.state.disabledString}"  id="VAR234L"
                     style="${PamForm.formFieldMap.VAR234.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR234.tooltip}">${PamForm.formFieldMap.VAR234.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR234.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR234.state.disabled}" property="pamClientVO.answer(VAR234)" styleId="VAR234" onchange="fireRule('VAR234',this)">
                 <html:optionsCollection property="codedValue(VAR234)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Lot Number::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR235.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR235.state.disabledString}"  id="VAR235L"
                     style="${PamForm.formFieldMap.VAR235.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR235.tooltip}">${PamForm.formFieldMap.VAR235.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR235.state.disabled}" property="pamClientVO.answer(VAR235)" styleId="VAR235" onblur="fireRule('VAR235',this)" maxlength="50" 
	     		title="${PamForm.formFieldMap.VAR235.label}"/></td>
	 </tr>
	 <!-- Vaccine 5: -->
     <%= request.getAttribute("2234") == null ? "" :  request.getAttribute("2234") %>  
</nedss:container>
</nedss:container>

<!--Epidemiologic Information-->
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
	     	  <html:select title="${PamForm.formFieldMap.VAR150.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR150.state.disabled}" property="pamClientVO.answer(VAR150)" styleId="VAR150" onchange="fireRule('VAR150',this)"> 
                 <html:optionsCollection property="codedValue(VAR150)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Age at Diagnosis:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR151.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR151.state.disabledString}"  id="VAR151L" style="${PamForm.formFieldMap.VAR151.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR151.tooltip}">${PamForm.formFieldMap.VAR151.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR151.state.disabled}" property="pamClientVO.answer(VAR151)" maxlength="3" size="5" styleId="VAR151"  onblur="fireRule('VAR151',this)"
	     		title="${PamForm.formFieldMap.VAR151.label}" onkeyup="isNumericCharacterEntered(this)"/>	
	     	  <html:select title="${PamForm.formFieldMap.VAR151.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR212.state.disabled}" property="pamClientVO.answer(VAR212)" styleId="VAR212"  onchange="fireRule('VAR212',this)">
	              <html:optionsCollection property="codedValue(VAR212)" value="key" label="value"/>
	           </html:select>
	     </td>
	 </tr>
	 <!--43. Previous case diagnosed by:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR152.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR152.state.disabledString}"  id="VAR152L" style="${PamForm.formFieldMap.VAR152.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR152.tooltip}">${PamForm.formFieldMap.VAR152.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR152.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR152.state.disabled}" property="pamClientVO.answer(VAR152)" styleId="VAR152" onchange="fireRule('VAR152',this)">
                 <html:optionsCollection property="codedValue(VAR152)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Specify Other:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR153.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR153.state.disabledString}"  id="VAR153L" style="${PamForm.formFieldMap.VAR153.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR153.tooltip}">${PamForm.formFieldMap.VAR153.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR153.state.disabled}" property="pamClientVO.answer(VAR153)" maxlength="100" size="25" styleId="VAR153"  onblur="fireRule('VAR153',this)"
	     		title="${PamForm.formFieldMap.VAR153.label}"/>	 	</td>
	 </tr>
	 <!--44. Where was the patient born::-->
		<tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR215.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR215.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR215.tooltip}">${PamForm.formFieldMap.VAR215.label}</span>  
		     </td>
		     <td> 
		     	  <html:select title="${PamForm.formFieldMap.VAR215.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR215.state.disabled}" property="pamClientVO.answer(VAR215)" styleId="VAR215" >
	                 <html:optionsCollection property="countryList" value="key" label="value"/>
	              </html:select>
		 	</td>
		 </tr>
		 <!-- Patient History: -->
	     <%= request.getAttribute("2187") == null ? "" :  request.getAttribute("2187") %>  
</nedss:container>
<!-- SUB SECTION - Epi-Link-->
<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Epi-Link" classType="subSect" >
	 <!--45. Is this case epi-linked to another confirmed or probable case:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR154.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR154.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR154.tooltip}">${PamForm.formFieldMap.VAR154.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR154.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR154.state.disabled}"  property="pamClientVO.answer(VAR154)" styleId="VAR154" onchange="fireRule('VAR154',this)" >
                 <html:optionsCollection property="codedValue(VAR154)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Type of case this case is epi-linked to:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR155.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR155.state.disabledString}"  id="VAR155L" style="${PamForm.formFieldMap.VAR155.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR155.tooltip}">${PamForm.formFieldMap.VAR155.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR155.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR155.state.disabled}" property="pamClientVO.answer(VAR155)" styleId="VAR155" onchange="fireRule('VAR155',this)" > 
                 <html:optionsCollection property="codedValue(VAR155)" value="key" label="value"/>
              </html:select>
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
	     	  <html:select title="${PamForm.formFieldMap.VAR156.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR156.state.disabled}" property="pamClientVO.answer(VAR156)" styleId="VAR156" onchange="fireRule('VAR156',this)"> 
                 <html:optionsCollection property="codedValue(VAR156)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other Transmission Setting::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR157.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR157.state.disabledString}"  id="VAR157L" style="${PamForm.formFieldMap.VAR157.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR157.tooltip}">${PamForm.formFieldMap.VAR157.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR157.state.disabled}" property="pamClientVO.answer(VAR157)" maxlength="100" size="25" styleId="VAR157" onblur="fireRule('VAR157',this)" 
	     		title="${PamForm.formFieldMap.VAR157.label}"/>	 	</td>
	 </tr>
<!--47. Is this case a healthcare worker::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR158.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR158.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR158.tooltip}">${PamForm.formFieldMap.VAR158.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR158.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR158.state.disabled}" property="pamClientVO.answer(VAR158)" styleId="VAR158" onchange="fireRule('VAR158',this)"  >
                 <html:optionsCollection property="codedValue(VAR158)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--48. Is this case part of an outbreak of 5 or more cases:-->
	<tr  class="${PamForm.formFieldMap.INV150.state.visibleString}">
	     <td class="${PamForm.formFieldMap.INV150.state.visibleString}"> 
	         <span style="${PamForm.formFieldMap.INV150.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV150.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV150.tooltip}">${PamForm.formFieldMap.INV150.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR150.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV150.state.disabled}" property="pamClientVO.answer(INV150)" styleId="INV150" onchange="fireRule('INV150', this)">
                 <html:optionsCollection property="codedValue(INV150)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Outbreak Name:-->
	<tr  class="${PamForm.formFieldMap.INV151.state.visibleString}">
	     <td class="${PamForm.formFieldMap.INV151.state.visibleString}"> 
	         <span style="${PamForm.formFieldMap.INV151.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.INV151.state.disabledString}"  id="INV151L" style="${PamForm.formFieldMap.INV151.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV151.tooltip}">${PamForm.formFieldMap.INV151.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR151.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV151.state.disabled}" property="pamClientVO.answer(INV151)" styleId="INV151" onchange="fireRule('INV151',this)" > 
                 <html:optionsCollection property="codedValue(INV151)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!-- Is this person associated with a day care facility:-->	 
	<tr  class="${PamForm.formFieldMap.INV148.state.visibleString}">
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV148.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.INV148.state.disabledString}"  id="INV148L" style="${PamForm.formFieldMap.INV148.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV148.tooltip}">${PamForm.formFieldMap.INV148.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.INV148.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV148.state.disabled}" property="pamClientVO.answer(INV148)" styleId="INV148"> 
                 <html:optionsCollection property="codedValue(INV148)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!-- Is this person a food handler:-->
	<tr  class="${PamForm.formFieldMap.INV149.state.visibleString}">
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV149.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.INV149.state.disabledString}"  id="INV149L" style="${PamForm.formFieldMap.INV149.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV149.tooltip}">${PamForm.formFieldMap.INV149.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR149.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV149.state.disabled}" property="pamClientVO.answer(INV149)" styleId="INV149"> 
                 <html:optionsCollection property="codedValue(INV149)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
     <%= request.getAttribute("2194") == null ? "" :  request.getAttribute("2194") %>  
     	</nedss:container>
	 <!-- End of SubSection - Epi-Link:-->
	<!--SUB_SECTION : Disease Aquisition-->
	 <div class='${PamForm.formFieldMap["2322"].state.visibleString}'>
	<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Disease Acquisition:" classType="subSect" >
	<jsp:include page="/pam/ext/DiseaseAcquisition.jsp"/>
	<%= request.getAttribute("2322") == null ? "" :  request.getAttribute("2322") %>  
       </nedss:container>
       </div>
	<!-- SUB_SECTION : Case Status: -->
	<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Case Status" classType="subSect" >
	<jsp:include page="/pam/ext/CaseStatus.jsp"/>      
	<!--49. Case Status::-->
	<tr>
	     <td class="${PamForm.formFieldMap.INV163.state.visibleString}"> 
	         <span style="${PamForm.formFieldMap.INV163.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV163.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV163.tooltip}">${PamForm.formFieldMap.INV163.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR163.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV163.state.disabled}" property="pamClientVO.answer(INV163)" styleId="INV163" >
                 <html:optionsCollection property="codedValue(INV163)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--50. MMWR Week:-->
	<tr>
	     <td class="${PamForm.formFieldMap.INV165.state.visibleString}"> 
	         <span style="${PamForm.formFieldMap.INV165.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV165.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV165.tooltip}">${PamForm.formFieldMap.INV165.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.INV165.state.disabled}" property="pamClientVO.answer(INV165)" maxlength="2" size="5" styleId="INV165" 
	     		title="${PamForm.formFieldMap.INV165.label}" onkeyup="isNumericCharacterEntered(this)"/>	 	</td>
	 </tr>
	  <!--51. MMWR Year:-->
	<tr>
	     <td class="${PamForm.formFieldMap.INV166.state.visibleString}"> 
	         <span style="${PamForm.formFieldMap.INV166.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV166.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV166.tooltip}">${PamForm.formFieldMap.INV166.label}</span>  
	     </td>
	     <td>
	         <html:text  maxlength="4"  size="4"  disabled ="${PamForm.formFieldMap.INV166.state.disabled}" property="pamClientVO.answer(INV166)" styleId="INV166"  title="${PamForm.formFieldMap.INV166.label}" onkeyup="isNumericCharacterEntered(this),YearMask(this)"></html:text>
	      </td>
	 </tr>
	 <!-- Case Status: -->
     <%= request.getAttribute("2202") == null ? "" :  request.getAttribute("2202") %>  
</nedss:container>
<nedss:container id="<%= sectionId + (++subSectionIndex) %>" name="Pregnant Women" classType="subSect" >
	<!--52. Was the patient pregnant during this varicella illness:-->
	<tr>
	     <td class="${PamForm.formFieldMap.INV178.state.visibleString}"> 
	         <span style="${PamForm.formFieldMap.INV178.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.INV178.state.disabledString}"  id="INV178L" style="${PamForm.formFieldMap.INV178.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV178.tooltip}">${PamForm.formFieldMap.INV178.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR178.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV178.state.disabled}" property="pamClientVO.answer(INV178)" styleId="INV178" onchange="fireRule('INV178',this)">
                 <html:optionsCollection property="codedValue(INV178)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Number of weeks gestation at onset of illness (1-45 weeks):-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR159.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR159.state.disabledString}"  id="VAR159L" style="${PamForm.formFieldMap.VAR159.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR159.tooltip}">${PamForm.formFieldMap.VAR159.label}</span>  
	     </td>
	     <td> 
	     	   <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR159.state.disabled}" property="pamClientVO.answer(VAR159)" maxlength="2" size="5" styleId="VAR159" onblur="fireRule('VAR159',this)"
	     		title="${PamForm.formFieldMap.VAR159.label}" onkeyup="isNumericCharacterEntered(this)"/></td>
	 </tr>
	  <!--Trimester at Onset of Illness:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR160.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR160.state.disabledString}"  id="VAR160L" style="${PamForm.formFieldMap.VAR160.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR160.tooltip}">${PamForm.formFieldMap.VAR160.label}</span>  
	     </td>
	       
	     <td> 
	     	    <html:select title="${PamForm.formFieldMap.VAR160.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR160.state.disabled}" property="pamClientVO.answer(VAR160)" styleId="VAR160" onchange="fireRule('VAR160',this)">
                 <html:optionsCollection property="codedValue(VAR160)" value="key" label="value"/>
              </html:select>
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
                    <html:textarea title="${PamForm.formFieldMap.INV167.label}" style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${PamForm.formFieldMap.INV167.state.disabled}"  styleId="INV167"
                        onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"    name="PamForm" property="pamClientVO.answer(INV167)"/>
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
	    <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
	</div>
