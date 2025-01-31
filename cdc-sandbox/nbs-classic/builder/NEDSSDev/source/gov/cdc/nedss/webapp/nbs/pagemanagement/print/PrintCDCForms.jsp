
<%@ include file="/jsp/tags.jsp"%>
<%@ include file="/jsp/resources.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<html lang="en">
<head>
<meta http-equiv="MSThemeCompatible" content="yes" />
<div class="Title">
	<title class="Title">NBS: Print Forms</title>
</div>
<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
<script type='text/javascript' src='/nbs/dwr/util.js'></script>
<script type='text/javascript' src='/nbs/dwr/interface/JPageForm.js'></script>
<SCRIPT Language="JavaScript" Src="PageSpecific.js"></SCRIPT>

  <script language="JavaScript">
	blockEnterKey();
   </script>
</head>
<body onload="autocompTxtValuesForJSP();" onunload="closepopup();">


	<html:form style="width:100%;margin: 0em;" target="_blank">
	
		<div
			style="width: 100%; margin: 0 auto; margin-top: 3px; margin-bottom: 3px; text-align: left; font-size: 1.1em; font-weight: bold; color: white; background: #003470; padding: 3px 0px">NBS
			Print CDC Forms</div>
		<tr>
			<td style="padding-top: 5px;" colspan="2">
				<div class="infoBox errors" id="errorMessages"
					style="display: none;"></div>
			</td>
		</tr>
		<tr>
			<td style="padding-top: 5px;" colspan="2">
		<div id="topButtId" class="grayButtonBar" style="text-align: right;">
			<input type="button" class="Button" value="Submit"
				onclick="javascript:printPageForm();" /> <input type="button"
				class="Button" value="Cancel" onclick="closepopup();" />
		</div>
		</td>
		</tr>
		<div style="width: 100%; text-align: right;">
			<i> <font class="boldTenRed"> * </font><font class="boldTenBlack">Indicates
					a Required Field </font>
			</i>
		</div>
		<nedss:container id="sect_print" name=""
				classType="sect" displayImg="false" includeBackToTopLink="no"
				displayLink="no">
				<nedss:container id="subsect_print" name="" classType="subSect"
					displayImg="false" includeBackToTopLink="no">
					<tr>
						<td colspan="2"><bean:message key="print.info" /></td>
					</tr>
				</nedss:container>
		</nedss:container>
				
		<logic:equal name="BaseForm" property="actionMode" value="View">
			<nedss:container id="sect_print" name="Select Form/Content"
				classType="sect" displayImg="false" includeBackToTopLink="no"
				displayLink="no">
				<nedss:container id="subsect_print" name="" classType="subSect"
					displayImg="false" includeBackToTopLink="no">
					
					<logic:equal name="BaseForm" property="securityMap(printCDCFRForm)"
						value="true">
						<tr>
							<td class="InputFieldLabel" id="selform"><span
								style="color: #CC0000">*</span><span style="">Select
									Form:</span></td>
							<td style="white-space:nowrap" class="InputField">
							
										<logic:equal name="BaseForm" property="securityMap(printCDCFRFormType)" value="CongenitalSyphilis">
											<html:select title="Select Form" property="formName" styleId="formNameid">
												<html:optionsCollection property="codedValue(INV_CDC_FORMS)" value="key" label="value" />
											</html:select>
										</logic:equal>
										
										<logic:equal name="BaseForm" property="securityMap(printCDCFRFormType)" value="TB">
											<div id ="formNameidRVCT"><nedss:view name="PageForm" property="formName" /></div>
								     	</logic:equal>
								     	
								     	<logic:equal name="BaseForm" property="securityMap(printCDCFRFormType)" value="LTBI">
											<div id ="formNameidTBLISS"><nedss:view name="PageForm" property="formName" /></div>
								     	</logic:equal>
									
								</td>
						</tr>
						<tr>
							<td class="InputFieldLabel" id="selcon"><span style="">Select
									Content:</span></td>
							<td class="InputField"><html:radio title="<%=NEDSSConstants.CDC_BLANK%>" property="formContent"
									value="<%=NEDSSConstants.CDC_BLANK%>"><%=NEDSSConstants.CDC_BLANK%></html:radio>
								<html:radio title="<%=NEDSSConstants.CDC_FILLED%>" property="formContent"
									value="<%=NEDSSConstants.CDC_FILLED%>"><%=NEDSSConstants.CDC_FILLED_JSP%></html:radio>
							</td>
						</tr>
					</logic:equal>
				</nedss:container>
			</nedss:container>
			<logic:notEmpty name="PageForm" property="coinfectionCondMap">
				<nedss:container id="sect_coinf"
					name="Include Co-infection Condition" classType="sect"
					displayImg="false" includeBackToTopLink="no" displayLink="no">
					<nedss:container id="subsect_coinf" name="" classType="subSect"
						displayImg="false" includeBackToTopLink="no">
						<tr><td colspan="2">
						<bean:message key="print.coinf" /><br />
						</td>
						</tr>
						<logic:equal name="BaseForm"
							property="securityMap(printCDCFRForm)" value="true">
							<tr>
							<td class="InputFieldLabel" id="selform"><span
								style="color: #CC0000">*</span><span style="">Include another condition on the
									form?:</span></td>
							<td style="white-space:nowrap" class="InputField">
									<html:select property="coinfCondInvUid" styleId="coinfInv">
										<html:optionsCollection property="coinfectionCondMap"
											value="key" label="value" />
									</html:select></td>
						</tr>
								
						</logic:equal>
					</nedss:container>
				</nedss:container>
			</logic:notEmpty>
		</logic:equal>
		<div id="topButtId" class="grayButtonBar" style="text-align: right;">
			<input type="button" class="Button" value="Submit"
				onclick="return printPageForm();" /> <input type="button"
				class="Button" value="Cancel" onclick="closepopup();" />
		</div>
	</html:form>
</body>
</html>