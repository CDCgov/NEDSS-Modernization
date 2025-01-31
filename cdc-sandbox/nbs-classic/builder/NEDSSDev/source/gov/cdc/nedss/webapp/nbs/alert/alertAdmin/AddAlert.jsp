               <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
               <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
               <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
               <%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
               <%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
               <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
               <%@ page import="java.util.*" %>      
            
               

	<html lang="en">
		<head>
			<meta http-equiv="MSThemeCompatible" content="yes"/>
    			<link rel="stylesheet" type="text/css" href="reference.css">
    			<link href="default.css" type="text/css" rel="stylesheet">
    			<link href="nedss.css" type="text/css" rel="stylesheet">
    			<link href="nedss2.css" type="text/css" rel="stylesheet">
    			<link href="displaytag.css" type="text/css" rel="stylesheet">
    			<link href="alternative.css" type="text/css" rel="stylesheet">	
			 	
		    <SCRIPT LANGUAGE="JavaScript">
						
			</SCRIPT>		
		</head>
		<body>
<table role="presentation" width="650" cellpadding="0" cellspacing="0" border="0">
	     <tr>
				<td width="650" height="25" bgcolor="#003470" colspan="4"></td>
	     </tr>
	      <tr >
	        <td class="boldTwelveBlack" bgcolor="#DCDCDC" style="WIDTH:2px;" height="25"  width="200"> </td>
	        <td class="boldTwelveBlack" bgcolor="#DCDCDC" >Add Alert </td>
	      </tr>
	      <tr>
	        <td colspan="2"><img alt="" border="0" height="9" width="650" src="dropshadow.gif" align="left"></td>
	      </tr>
	</table>

<html:form>
<table role="presentation" align="left" bgcolor="white" border="0" cellspacing="0" cellpadding="0"  >
		<tr>
				<td>
						<div id="buttonbartop">
							<table role="presentation" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td>
										<table role="presentation" cellpadding="0" cellspacing="0" border="0" width="650">

										<tr align="left">
											<td valign="top"><img alt="" border="0" height="54" width="25" src="task_button/left_corner.jpg"></td>
											<td alt ="" background="task_button/tb_cel_bak.jpg" valign="top" align="left" width="500"></td>
											<td valign="top" width="3"><img alt="" border="0" height="54" width="3" 								src="task_button/strip_spacer.jpg"></td>
											<td alt ="" background="task_button/tb_cel_bak.jpg" valign="top" align="left" width="250">
													<table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
															<tr>
																<td valign="top" align="center"><input type="image" src="task_button/fa_add.jpg" 										width="30" 	height="40" border="0" name="Add" id="Add" alt="Add button" title="Add button" 												class="cursorHand" 	onclick="self.close();"></td>
																<td >&nbsp; </td>
																<td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" 										width="30" height="40" border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" 										class="cursorHand" onclick="self.close();"></td>
															</tr>
															<tr>
																<td class="boldEightBlack" align="center" valign="top">
																	&nbsp;Submit&nbsp;
																</td>
																<td >&nbsp; </td>
																<td class="boldEightBlack" align="center" valign="top">&nbsp;Cancel&nbsp;</td>
															
															</tr>

													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
						</table>
					</div>
				</td>
			</tr>

<tr>
	<td>
		<table role="presentation" align="left" bgcolor="white" border="1" cellspacing="0" cellpadding="0" width="650">
     			  <tr >
     				 <td bgcolor="#003470"  align="left"><nobr><font class="boldTwelveWhite" > &nbsp;&nbsp; Alert Details </font></nobr></td>
					
     			</tr>
		</table>

	</td>
</tr>


<tr>
	<td>
			<table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td  class="InputFieldLabel"> Public HealthCase Event: </td> 	
					<td class="InputField"><html:text name="alertAdminForm" property="alertClientVO.event" /> </td>
					<td  class="InputFieldLabel"> Severity: </td> 	
					<td class="InputField"><html:text name="alertAdminForm" property="alertClientVO.event" /> </td>
              	</tr>
				<tr>
					<td  class="InputFieldLabel"> Condition: </td> 	
					<td class="InputField"><html:text name="alertAdminForm" property="alertClientVO.event" /> </td>
                   <td  class="InputFieldLabel"> Select User: </td> 	
					<td class="InputField"><html:text name="alertAdminForm" property="alertClientVO.event" /> </td>
    
				</tr>
				<tr>
					<td  class="InputFieldLabel"> Jurisdiction: </td> 	
					<td class="InputField"><html:text name="alertAdminForm" property="alertClientVO.event" /> </td>
                   
    
				</tr>
		</table>
	</td>		
</tr>    


<tr></tr> 
<tr></tr>
<tr></tr> 
<tr>
				<td>
					<div id="buttonbarbottom">
						<table role="presentation" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<table role="presentation" cellpadding="0" cellspacing="0" border="0" width="650">
										<tr align="left">
											<td valign="top"><img alt="" border="0" height="54" width="25" src="task_button/left_bottom_corner.jpg">	</td>
											<td alt ="" background="task_button/tb_cel_bak.jpg" valign="top" align="left" width="500"></td>
											<td valign="top" width="3"><img alt="" border="0" height="54" width="3" src="task_button/strip_spacer.jpg">	</td>
											<td alt ="" background="task_button/tb_cel_bak.jpg" valign="top" align="left" width="250">

												<table role="presentation" align="left" cellpadding="0" cellspacing="0" border="0" width="30">
													
										<tr>
											<td valign="top" align="center"><input type="image" src="task_button/fa_add.jpg" width="30" 	height="40" border="0" 											name="Add" 		id="Add" 			alt="Add 		button" class="cursorHand" 					 									onclick="self.close();">
										       </td>
									       	<td >&nbsp; </td>
											<td valign="top" align="center"><input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" 													name="Cancel" 			id="Cancel" 			alt="Cancel button" title="Cancel button" 	 
													class=" "="cursorHand" 																					onclick="self.close();"></td>
										</tr>
									<tr>
										<td class="boldEightBlack" align="center" valign="top">
											&nbsp;Submit&nbsp;
										</td>
									<td >&nbsp; </td>
									<td class="boldEightBlack" align="center" valign="top">&nbsp;Cancel&nbsp;</td>
								
									</tr>

											
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</tfoot>
		</table>
			</html:form>
		</body>
	</html>



